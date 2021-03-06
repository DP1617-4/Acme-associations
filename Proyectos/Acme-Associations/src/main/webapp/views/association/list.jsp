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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<jstl:set var="full" value="font-weight: grey; color:grey; background-color:white;" />
<jstl:set var="Inminent" value="color:white; font-weight:bold; background-color:black;" />
<jstl:set var="passed" value="background-color:red; color: black; font-weight:bold;" />
<jstl:set var="available" value="background-color:green; color: black; font-weight:bold;" />



<div class="row row-offcanvas row-offcanvas-right">
<div class="col-12 col-md-9">

<security:authentication property="principal" var ="loggedactor"/>
<display:table pagesize="5" keepStatus="false" name="associations" requestURI="${requestURI}" id="row">
	
	<spring:message code="association.name" var="nameHeader" />
	<spring:message code="association.description" var="descriptionHeader" />
	<spring:message code="association.creationDate" var="creationDateHeader" />
	<spring:message code="association.address" var="addressHeader" />
	

	<display:column title="${titleHeader}">
			<a href="association/${row.id}/display.do"> <jstl:out value="${row.name }"/></a>
	</display:column>
	<display:column property="name" title="${titleHeader}"/>
	<display:column property="description" title="${descriptionHeader}" />
	<display:column property="creationDate" title="${creationDateHeader}" />
	<display:column property="address" title="${addressHeader}"/>	
	
</display:table>
<security:authorize access="hasRole('USER')">
<br><br/>
<div><a class="btn btn-primary" href="association/user/create.do"><spring:message code="association.create"/></a></div>
<br/>
</security:authorize>
</div>
<jstl:if test="${association != null}">
<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
	<a class="btn btn-primary" href="association/${association.id}/display.do">&larr; <jstl:out value="${association.name}"/></a>
	   <br><br><acme:lateralMenu/>
	   </div>
	   </jstl:if>
	   </div>

		