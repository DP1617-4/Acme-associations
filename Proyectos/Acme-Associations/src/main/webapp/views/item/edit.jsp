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


<form:form action="item/user/edit.do" modelAttribute="item">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="section" />
	<form:hidden path="identifier" />

    
	<acme:textbox code="item.name" path="name"/>
	
	<acme:textarea code="item.description" path="description"/>
	
	<form:label path="itemCondition">
	<spring:message code="item.condition" />:
	</form:label>
	<form:select id="condition" path="itemCondition">
		<form:option value="EXCELENT" label="EXCELENT" />
		<form:option value="GOOD" label="GOOD" />
		<form:option value="MODERATE" label="MODERATE" />
		<form:option value="BAD" label="BAD" />
	</form:select>
	<form:errors cssClass="error" path="itemCondition" />
	
	<acme:textbox code="item.picture" path="picture"/>
	
	<br/>
	
	<button type="submit" name="save" class="btn btn-primary" id="save">
		<spring:message code="user.save" />
	</button>
	
	<input type="button" name="cancel"
		value="<spring:message code="user.cancel" />"
		onclick="location.href = ('association/${item.section.association.id}/display.do');" />	
	
</form:form>
