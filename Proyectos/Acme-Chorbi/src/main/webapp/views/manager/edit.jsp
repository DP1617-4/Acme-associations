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


<form:form action="${requestURI}" modelAttribute="manager">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	
	<acme:textbox code="manager.name" path="name"/>
	
	<acme:textbox code="manager.surname" path="surname"/>
	
	<acme:textbox code="manager.email" path="email"/>
	
	<acme:textbox code="manager.phoneNumber" path="phoneNumber"/>
	
	
	<acme:textbox code="manager.company.name" path="companyName"/>
	
	<acme:textbox code="manager.VAT.number" path="VATNumber"/>
	
	<button type="submit" name="save" class="btn btn-primary" id="save">
		<spring:message code="manager.save" />
	</button>
	
	<input type="button" name="cancel"
		value="<spring:message code="manager.cancel" />"
		onclick="location.href = ('welcome/index.do');" />
	
</form:form>
