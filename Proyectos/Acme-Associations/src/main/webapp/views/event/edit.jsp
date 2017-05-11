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
<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="event">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="organiser"/>
	<form:hidden path="registered"/>

	<acme:textbox code="event.title" path="title"/><br />
	<acme:textbox code="event.description" path="description"/><br />
	<div>
		<form:label path="moment">
			<spring:message code="event.moment" />:
		</form:label>
		<form:input placeholder="dd/MM/yyyy HH:mm" path="moment" />
		<form:errors cssClass="error" path="moment" />
	</div> </br>
	<acme:textbox code="event.numberSeat" path="numberSeat"/><br />
	<acme:textbox code="event.picture" path="picture"/><br />


	<input type="submit" name="save"
		value="<spring:message code="event.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="event.cancel" />"
		onclick="location.href = ('event/manager/list.do');" />
	<br />

	

</form:form>