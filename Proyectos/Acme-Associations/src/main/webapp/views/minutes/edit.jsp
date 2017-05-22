<%--
 * action-2.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="${requestURI}" modelAttribute="minutes">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="meeting"/>

    <acme:textbox code="minutes.document" path="document"/>
	<br/>
	
	<button type="submit" name="save" class="btn btn-primary" id="save">
		<spring:message code="minutes.save" />
	</button>
	
	<input type="button" name="cancel"
		value="<spring:message code="minutes.cancel" />"
		onclick="javascript: window.location.replace('${cancelURI}');" />&nbsp;
	<br />
	
</form:form>
