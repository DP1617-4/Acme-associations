<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="/WEB-INF/tags/functions" prefix="mask" %>



<security:authentication property="principal" var ="loggedactor"/>
<h2><spring:message code="manager" /></h2>
<p><spring:message code="manager.name"/>: <jstl:out value="${mask:mask(manager.name) }" /></p> 
<p><spring:message code="manager.surname"/>: <jstl:out value="${mask:mask(manager.surname) }" /></p> 
<jstl:if test="${manager.userAccount.id == loggedactor.id }">
	<p><spring:message code="manager.email"/>: <jstl:out value="${manager.email}" /></p> 
	<p><spring:message code="manager.phoneNumber"/>: <jstl:out value="${manager.phoneNumber}" /></p> 
</jstl:if>
<p><spring:message code="manager.company.name"/>: <jstl:out value="${mask:mask(manager.companyName) }" /></p> 
<p><spring:message code="manager.VAT.number"/>: <jstl:out value="${mask:mask(manager.VATNumber) }" /></p>
<br/>

<security:authorize access="hasRole('MANAGER')">
	<a href="welcome/index.do?"> <spring:message code="manager.back" /></a>
	<jstl:if test="${manager.userAccount.username==loggedactor.username}">
		<a href="_manager/_manager/edit.do?"> <spring:message code="manager.edit" /></a>
	</jstl:if>
</security:authorize>

<br/>




