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

<security:authentication property="principal" var ="loggedactor"/>
<form:form action="${requestURI}" modelAttribute="sanction">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="association"/>
	<form:hidden path="user"/>
	
	<jstl:choose>
		<jstl:when test="${not empty role && (role.type eq 'COLLABORATOR' || role.type eq 'MANAGER') && sanction.user.userAccount != loggedactor}">
			<acme:datepicker code="sanction.end.date" path="endDate"/><br />
			<acme:textarea code="sanction.motiff" path="motiff"/><br />
			<br/>
		
			<input type="submit" name="save"
				value="<spring:message code="sanction.save" />" />&nbsp;
				
		</jstl:when>
		<jstl:otherwise>
			<acme:datepicker code="sanction.end.date" path="endDate" readonly="true"/><br />
			<acme:textarea code="sanction.motiff" path="motiff" readonly="true"/><br />
			<br/>
		</jstl:otherwise>
	</jstl:choose>
	<security:authorize access="hasRole('ADMIN')">
		<acme:datepicker code="sanction.end.date" path="endDate"/><br />
		<acme:textarea code="sanction.motiff" path="motiff"/><br />
		<br/>
	
		<input type="submit" name="save"
			value="<spring:message code="sanction.save" />" />&nbsp;				
	</security:authorize>
	
	<input type="button" name="cancel"
		value="<spring:message code="sanction.cancel" />"
		onclick="location.href = ('${cancelURI}');" />
	<br />
</form:form>