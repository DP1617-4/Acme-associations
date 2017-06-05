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
<%@taglib uri="/WEB-INF/tags/functions" prefix="mask" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authentication property="principal" var ="loggedactor"/>
<div class="row row-offcanvas row-offcanvas-right">

  	<div class="col-12 col-md-9">
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="items" requestURI="${requestURI}" id="row">
	

	<!-- Attributes -->
	<spring:message code="item.name" var="nameHeader" />
	<spring:message code="master.page.association" var="associationHeader" />
	<display:column title="${nameHeader}">
		<security:authorize access="hasRole('USER')"> 
		<a href="item/user/${row.section.association.id}/display.do?itemId=${row.id}"><jstl:out value="${row.name}"/></a>
		</security:authorize>
		<security:authorize access="isAnonymous()"> 
		<jstl:out value="${row.name}"/>
		</security:authorize>
		<security:authorize access="hasRole('ADMIN')"> 
		<jstl:out value="${row.name}"/>
		</security:authorize>
	</display:column>
	<jstl:if test="${ association==null}">
	<display:column title="${associationHeader }">
		<a href="association/${row.section.association.id}/display.do"><jstl:out value="${row.section.association.name}"/></a>
	</display:column>
	<display:column title="${associationHeader }">
		<jstl:out value="${row.section.name}"/>
	</display:column>
	</jstl:if>

	<spring:message code="item.condition" var="conditionHeader" />
	<display:column property="itemCondition" title="${conditionHeader}" sortable="true" />
	
	
</display:table>
<br/>

</div>
<jstl:if test="${association != null}">
	<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
	<a class="btn btn-primary" href="association/${association.id}/display.do">&larr; <jstl:out value="${association.name}"/></a>
	   <br><br><acme:lateralMenu/>
	   </div>
	</jstl:if>