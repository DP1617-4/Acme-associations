<%--
 * list.jsp
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
<%@taglib uri="/WEB-INF/tags/functions" prefix="mask" %>


<h1> ${folder.name}</h1>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="messages" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->

	
	<display:column>
		<a href="chirp/chorbi/delete.do?messageId=${row.id}" onclick="return confirm('<spring:message code="message.confirm.delete" />') ">
			<spring:message code="message.delete" />
		</a>
	</display:column>
	
	<!-- Attributes -->
	
	<spring:message code="message.title" var="titleHeader" />
	<display:column title="${titleHeader}" sortable="true" >
		${mask:mask(row.subject) } 
	</display:column>

	<spring:message code="message.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

	<spring:message code="message.body" var="bodyHeader" />
	<display:column title="${bodyHeader}" sortable="false" >
		${mask:mask(row.text) }
	</display:column>
	
	<spring:message code="message.attachment" var="attachmentHeader"/>
	<display:column title="${attachmentHeader}">
	<ul>
	<jstl:forEach items="${row.attachments}" var="thisAttachment">
		<li><a href="${thisAttachment}" target="_blank">${thisAttachment}</a>
	</jstl:forEach>
	</ul>
	</display:column> 
	
	
	
	<spring:message code="message.sender" var="senderHeader"/>
	<display:column title="${senderHeader}">
		<a href="actor/actor/display.do?actorId=${row.sender.id}"> ${mask:mask(row.sender.name) } ${mask:mask(row.sender.surname) }</a>
	</display:column>
	
	
	<spring:message code="message.recipient" var="recipientHeader"/>
	<display:column title="${recipientHeader}">
		<a href="actor/actor/display.do?actorId=${row.recipient.id }">${row.recipient.name } ${row.recipient.surname }</a>
	</display:column> 
	
	<jstl:if test="${folder.name eq 'Sent'}">
		<spring:message code="chirp.resend" var="resendHeader"/>
		<display:column title="${resendHeader}">
			<form:form action="chirp/chorbi/resend.do"
				modelAttribute="resendChirp" >

				<form:hidden path="chirpId" value="${row.id}" />

				<form:label path="recipientId">
					<spring:message code="chirp.resend.recipient" />:
				</form:label>
				<form:select id="chorbies" path="recipientId">
					<form:option value="0" label="----" />
					<form:options items="${chorbies}" itemValue="id" itemLabel="completeName" />
				</form:select>
				<form:errors cssClass="error" path="recipientId" />
				<input type="submit" name="save"
					value="<spring:message code="chirp.resend" />" />&nbsp;
			</form:form>
		</display:column>
	</jstl:if>
	<jstl:if test="${folder.name eq 'Received' }">
		<spring:message code="chirp.reply" var="replyHeader"/>
		<display:column title="${replyHeader}">
			<a href="chirp/chorbi/reply.do?chirpId=${row.id }"><spring:message code="chirp.reply"/></a>
		</display:column>
	</jstl:if>
	

</display:table>

<!-- Action links -->

<div>
	<a href="chirp/chorbi/create.do"> <spring:message
			code="message.create" />
	</a>
</div>
