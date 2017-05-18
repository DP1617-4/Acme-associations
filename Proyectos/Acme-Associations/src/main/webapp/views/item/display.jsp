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
<jstl:set var="likes" value="${likes}"/> 

<h2><spring:message code="likes" /></h2>
<p><spring:message code="likes.liked"/>: <jstl:out value="${mask:mask(likes.liked.name) }" /> <jstl:out value="${likes.liked.surname}" /></p>
<p><spring:message code="likes.moment"/>: <jstl:out value="${likes.moment }" /></p> 
<p><spring:message code="likes.comment"/>: <jstl:out value="${mask:mask(likes.comment) }" /></p> 

<br/>

<a href="likes/chorbi/list.do?"> <spring:message code="likes.back" /></a>
<jstl:if test="${likes.chorbi.userAccount.username==loggedactor.username}">
	<a href="likes/chorbi/edit.do?"> <spring:message code="likes.edit" /></a>
</jstl:if>


