<%--
 * edit.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="chirp/_manager/edit.do" modelAttribute="chirpBroadcast">

	<form:hidden path="attachments"/>
	


	<acme:textbox code="message.title" path="subject"/>
	
	<acme:textarea code="message.body" path="text"/>
	
	<form:label path="event">
		<spring:message code="chirp.event" />:
	</form:label>
	<form:select id="events" path="event">
		<form:option value="0" label="----"/>
		<jstl:forEach items="${events}" var="event">
			<form:option value="${event.id}" label="${event.title}" />
		</jstl:forEach>
	</form:select>
	<form:errors cssClass="error" path="event" />
	
	<br /><br />
	
	<acme:textbox  code="chirp.attachment.add" path="attachment"/>
		
		<input type="submit" name="attach"
		value="<spring:message code="chirp.attachment.add" />" />&nbsp; 
		<br/>
		<jstl:if test="${urlError != null}">
			<br />
			<span class="message"><spring:message code="${urlError}" /></span>
		</jstl:if>	
	<br />
	

	<input type="submit" name="save"
		value="<spring:message code="message.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="message.cancel" />"
		onclick="location.href = ('welcome/index.do');" />
	<br />

	

</form:form>
	
	<ul>
	<jstl:forEach var="row" varStatus="i" items="${chirpBroadcast.attachments}">
	
		<li><a href="${row}"><jstl:out value="${row}" /></a>
	
    </jstl:forEach>
    </ul>
