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
<display:table pagesize="5" keepStatus="false" name="sections" requestURI="${requestURI}" id="row">
	
	<spring:message code="section.name" var="nameHeader" />
	<spring:message code="section.user" var="userHeader" />
	<spring:message code="section.addItem" var="newItemHeader"/>
	<spring:message code="section.items" var="itemsHeader"/>
	
	<display:column property="name" title="${nameHeader}"/>
	<security:authorize access="isAuthenticated()">
	<display:column title="${userHeader}">
						<a href="actor/actor/${row.user.id}/display.do"> ${row.user.name} ${row.user.surname}</a>
	</display:column>
	<display:column> <a href="item/user/${association.id}/${row.id}/listSection.do">${itemsHeader}</a> </display:column>	
	<display:column>
	<jstl:if test="${loggedactor == row.user.userAccount }"> <a href="item/user/${association.id}/${row.id}/create.do">${newItemHeader}</a> 	
	</jstl:if>
	</display:column>
	</security:authorize>
	
</display:table>
<br><br/>
<div><a class="btn btn-primary" href="section/user/${association.id}/create.do"><spring:message code="section.create"/></a></div>
<br/>
</div>
<jstl:if test="${association != null}">
	<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
	<a class="btn btn-primary" href="association/${association.id}/display.do">&larr; <jstl:out value="${association.name}"/></a>
	   </div>
	</jstl:if>
	</div>


		