<%--
 * list.jsp
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


<jstl:set var="full" value="font-weight: grey; color:grey; background-color:white;" />
<jstl:set var="Inminent" value="color:white; font-weight:bold; background-color:black;" />
<jstl:set var="passed" value="background-color:red; color: black; font-weight:bold;" />
<jstl:set var="available" value="background-color:green; color: black; font-weight:bold;" />



	<security:authentication property="principal" var ="loggedactor"/>
	<jstl:if test="${requestURI.contains('Active')}">
		<jstl:choose>
			<jstl:when test="${not requestURI.contains('actor')}">
				<div><a href="sanction/mySanctions.do"><spring:message code="sanction.all"/></a></div>
			</jstl:when>
			<jstl:when test="${requestURI.contains('my')}">
				<div><a href="sanction/actor/${association.id}/mySanctions.do"><spring:message code="sanction.all"/></a></div>
			</jstl:when>
			<jstl:otherwise>
				<div><a href="sanction/actor/${association.id}/listByUser.do?userId=${userId}"><spring:message code="sanction.all"/></a></div>
			</jstl:otherwise>
		</jstl:choose>
	</jstl:if>
<display:table pagesize="5" keepStatus="true" name="sanctions" requestURI="${requestURI}" id="row">
	
	<jstl:if test="${association == null}">
		<spring:message code="sanction.association" var="associationHeader"/>	
	</jstl:if>
	<spring:message code="sanction.user" var="userHeader" />
	<spring:message code="sanction.end.date" var="endDateHeader"/>
	<spring:message code="sanction.edit" var="editHeader"/>
	
	<jstl:if test="${association == null}">
		<display:column title="${associationHeader}">
			<a href="association/${row.association.id}/display.do"> <jstl:out value="${row.association.name }"/></a>
		</display:column>	
	</jstl:if>
	<display:column title="${userHeader}">
		<a href="sanction/display.do?sanctionId=${row.id}"><jstl:out value="${row.user.name}"/><jstl:out value="${row.user.surname}"/></a>
	</display:column>
	<display:column title="${endDateHeader}"><jstl:out value="${row.endDate}"/></display:column>
	<jstl:if test="${not empty role && (role.type eq 'COLLABORATOR' || role.type eq 'MANAGER')}">
		<display:column title="${editHeader}">
			<jstl:if test="${row.user.userAccount != loggedactor}">
				<a href="sanction/actor/${association.id}/edit.do?sanctionId=${row.id}"><spring:message code="sanction.edit"/></a>
			</jstl:if>
		</display:column>
	</jstl:if>
	
</display:table>
<br><br/>
	<jstl:if test="${not empty role && (role.type eq 'COLLABORATOR' || role.type eq 'MANAGER') && not requestURI.contains('my')}">
		<div><a href="sanction/actor/${association.id}/create.do?userId=${userId}"><spring:message code="sanction.create"/></a></div>
	</jstl:if>
<br/>

		