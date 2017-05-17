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


<form:form action="${requestURI}" modelAttribute="user">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount.authorities" />
	<form:hidden path="userAccount.username" />
	
	<p><spring:message code="user.useraccount.username"/>: <jstl:out value="${user.userAccount.username}" /></p> 

    <acme:password code="user.useraccount.password" path="userAccount.password"/>
    
	<acme:textbox code="user.name" path="name"/>
	
	<acme:textbox code="user.surname" path="surname"/>
	
	<acme:textbox code="user.email" path="email"/>
	
	<acme:textbox code="user.phoneNumber" path="phoneNumber"/>
	
	<acme:textbox code="user.postalAddress" path="postalAddress"/>
	
	<acme:textbox code="user.idNumber" path="idNumber"/>
	
	<br/>
	
	<button type="submit" name="save" class="btn btn-primary" id="save">
		<spring:message code="user.save" />
	</button>
	
	<input type="button" name="cancel"
		value="<spring:message code="user.cancel" />"
		onclick="location.href = ('welcome/index.do');" />	
	
</form:form>
