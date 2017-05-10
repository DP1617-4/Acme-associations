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

<form:form action="${requestURI}" modelAttribute="likes">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="chorbi" />
	<form:hidden path="liked" />
	
	<p><spring:message code="likes.liked"/>: <jstl:out value="${liked.name}" /> <jstl:out value="${liked.surname}" /></p> 
	
	<p><spring:message code="likes.moment"/>: <jstl:out value="${likes.moment}" /></p> 

    <acme:textbox code="likes.comment" path="comment"/>
    
    <form:label path="stars">
		<spring:message code="likes.stars" />:
	</form:label>
	<form:select id="stars" path="stars">
		<form:option value="0" label="0"/>
		<form:option value="1" label="1"/>
		<form:option value="2" label="2"/>
		<form:option value="3" label="3"/>
	</form:select>
	<form:errors cssClass="error" path="stars" />
    
	<br/>
	
	<button type="submit" name="save" class="btn btn-primary" id="save">
		<spring:message code="chorbi.save" />
	</button>
	
	<acme:cancel url="likes/chorbi/list.do" code="chorbi.cancel"/>
	
	
	
	
</form:form>
