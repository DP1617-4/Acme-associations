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
<jstl:set var="likes" value="${chorbi}"/> 
<h2><spring:message code="chorbi" /></h2>
<p><spring:message code="chorbi.name"/>: <jstl:out value="${mask:mask(chorbi.name) }" /></p> 
<p><spring:message code="chorbi.surname"/>: <jstl:out value="${mask:mask(chorbi.surname) }" /></p> 
<jstl:if test="${chorbi.userAccount.id == loggedactor.id }">
	<p><spring:message code="chorbi.email"/>: <jstl:out value="${chorbi.email}" /></p> 
	<p><spring:message code="chorbi.phoneNumber"/>: <jstl:out value="${chorbi.phoneNumber}" /></p> 
</jstl:if>
<security:authorize access="hasRole('ADMIN')">
	<p><spring:message code="chorbi.email"/>: <jstl:out value="${chorbi.email}" /></p> 
	<p><spring:message code="chorbi.phoneNumber"/>: <jstl:out value="${chorbi.phoneNumber}" /></p> 
</security:authorize>

<img src="<jstl:out value='${chorbi.picture}'/>"  width="300"> 

<p><spring:message code="chorbi.description"/>: <jstl:out value="${mask:mask(chorbi.description) }" /></p> 
<p><spring:message code="chorbi.relationshipType"/>: <jstl:out value="${mask:mask(chorbi.relationshipType)}" /></p> 
<p><spring:message code="chorbi.birthDate"/>: <jstl:out value="${chorbi.birthDate}" /></p> 
<p><spring:message code="chorbi.genre"/>: <jstl:out value="${chorbi.genre}" /></p>  
<p><spring:message code="chorbi.country"/>: <jstl:out value="${mask:mask(chorbi.country) }" /></p> 
<p><spring:message code="chorbi.state"/>: <jstl:out value="${mask:mask(chorbi.state) }" /></p>  
<p><spring:message code="chorbi.province"/>: <jstl:out value="${mask:mask(chorbi.province) }" /></p> 
<p><spring:message code="chorbi.city"/>: <jstl:out value="${mask:mask(chorbi.city) }" /></p> 


<br/>

<security:authorize access="hasRole('CHORBI')">
	<a href="chorbi/chorbi/list.do?"> <spring:message code="chorbi.back" /></a>
	<jstl:if test="${chorbi.userAccount.username==loggedactor.username}">
		<a href="chorbi/chorbi/edit.do?"> <spring:message code="chorbi.edit" /></a>
	</jstl:if>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${chorbi.userAccount.username==loggedactor.username}">
	<a href="chorbi/administrator/list.do?"> <spring:message code="chorbi.back" /></a>
	</jstl:if>
</security:authorize>

<br/>




