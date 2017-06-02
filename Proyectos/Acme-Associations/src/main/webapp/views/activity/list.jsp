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


<style>
a{color: #251EEB}
</style>

<jstl:set var="full" value="font-weight: grey; color:grey; background-color:white;" />
<jstl:set var="Inminent" value="color:white; font-weight:bold; background-color:black;" />
<jstl:set var="passed" value="background-color:#E68181; color: black; font-weight:bold;" />
<jstl:set var="available" value="background-color:  #81E685; color: black; font-weight:bold;" />


<div class="row row-offcanvas row-offcanvas-right">

 <div class="col-12 col-md-9">
<display:table pagesize="5" keepStatus="false"
	name="activities" requestURI="${requestURI}" id="row">
	
	<spring:message code="activity.name" var="nameHeader" />
	<spring:message code="activity.description" var="descriptionHeader" />
	<spring:message code="activity.startMoment" var="startMomentHeader" />
	<spring:message code="activity.endMoment" var="endMomentHeader" />
	<spring:message code="activity.maximumAttendants" var="maximumAttendantsHeader" />
	<spring:message code="master.page.association" var="associationHeader" />
	
	<jstl:choose>
		<jstl:when test="${row.attendants.size() == row.maximumAttendants}">
			<display:column title="${nameHeader}" sortable="true" style="${full}">
				<a href="activity/${row.association.id}/${row.id}/display.do"> <jstl:out value="${row.name }"/></a>
			</display:column>
			<display:column property="description" title="${descriptionHeader}" sortable="true" style="${full}" />
			<display:column property="startMoment" title="${startMomentHeader}" sortable="true" style="${full}" />
			<display:column property="endMoment" title="${endMomentHeader}" sortable="true" style="${full}" />
			<display:column property="maximumAttendants" title="${maximumAttendantsHeader}" sortable="true" style="${full}" />
			<jstl:if test="${association == null }">
				<display:column title="${associationHeader}" sortable="true" style="${full}">
					<a  href="association/${row.association.id}/display.do"><jstl:out value="${row.association.name}"/></a>
				</display:column>
			</jstl:if>
		</jstl:when>
		<jstl:otherwise>
			<jsp:useBean id="dateValue" class="java.util.Date" />
			<jstl:set var="now" value="${dateValue.time}" />
			<jstl:set var="oneMonth" value="${dateValue.time + 2628000000}"/>
			<jstl:choose>
				<jstl:when test="${row.startMoment.time > now && row.startMoment.time < oneMonth}">
					<display:column title="${nameHeader}" sortable="true" style="${Inminent}">
						<a href="activity/${row.association.id}/${row.id}/display.do"> <jstl:out value="${row.name }"/></a>
					</display:column>
					<display:column property="description" title="${descriptionHeader}" sortable="true" style="${Inminent}" />
					<display:column property="startMoment" title="${startMomentHeader}" sortable="true" style="${Inminent}" />
					<display:column property="endMoment" title="${endMomentHeader}" sortable="true" style="${Inminent}" />
					<display:column property="maximumAttendants" title="${maximumAttendantsHeader}" sortable="true" style="${Inminent}" />
					<jstl:if test="${association == null }">
					<display:column title="${associationHeader}" sortable="true" style="${Inminent}">
						<a  href="association/${row.association.id}/display.do"><jstl:out value="${row.association.name}"/></a>
					</display:column>
					</jstl:if>
				</jstl:when>
				<jstl:when test="${row.startMoment.time < now}">
					<display:column title="${nameHeader}" sortable="true" style="${passed}">
						<a href="activity/${row.association.id}/${row.id}/display.do"> <jstl:out value="${row.name }"/></a>
					</display:column>
					<display:column property="description" title="${descriptionHeader}" sortable="true" style="${passed}" />
					<display:column property="startMoment" title="${startMomentHeader}" sortable="true" style="${passed}" />
					<display:column property="endMoment" title="${endMomentHeader}" sortable="true" style="${passed}" />
					<display:column property="maximumAttendants" title="${maximumAttendantsHeader}" sortable="true" style="${passed}" />
					<jstl:if test="${association == null }">
						<display:column title="${associationHeader}" sortable="true" style="${passed}">
							<a  href="association/${row.association.id}/display.do"><jstl:out value="${row.association.name}"/></a>
						</display:column>
					</jstl:if>
				</jstl:when>
				<jstl:otherwise>
					<display:column title="${nameHeader}" sortable="true" style="${available}">
						<a href="activity/${row.association.id}/${row.id}/display.do"> <jstl:out value="${row.name }"/></a>
					</display:column>
					<display:column property="description" title="${descriptionHeader}" sortable="true" style="${available}" />
					<display:column property="startMoment" title="${startMomentHeader}" sortable="true" style="${available}" />
					<display:column property="endMoment" title="${endMomentHeader}" sortable="true" style="${available}" />
					<display:column property="maximumAttendants" title="${maximumAttendantsHeader}" sortable="true" style="${available}" />
					<jstl:if test="${association == null }">
						<display:column title="${associationHeader}" sortable="true" style="${available}">
							<a  href="association/${row.association.id}/display.do"><jstl:out value="${row.association.name}"/></a>
						</display:column>
					</jstl:if>
				</jstl:otherwise>
			</jstl:choose>
		</jstl:otherwise>
	</jstl:choose>
	
	
	<jstl:if test="${role eq 'MANAGER' || role eq 'COLLABORATOR'}">
		<spring:message code="activity.edit" var="editHeader" />
		<display:column title="${editHeader}">
			<a href="activity/user/${row.association.id}/${row.id}/edit.do"><spring:message code="activity.edit" /> </a>
		</display:column>
	
	
		<spring:message code="activity.delete" var="deleteHeader" />
		<display:column title="${deleteHeader}">
			<a href="activity/user/${row.id}/delete.do"><spring:message code="activity.delete" /> </a>
		</display:column>
	</jstl:if>
	
	<jstl:if test="${row.publicActivity}">
		<security:authorize access="hasRole('USER')">
			<spring:message code="activity.register" var="registerHeader" />
			<display:column title="${registerHeader}">
			<jstl:if test="${row.attendants.size() < row.maximumAttendants}">
				<a href="activity/user/${row.id}/register.do">
				<jstl:set var="registered" value="false" />
					<jstl:if test="${row.attendants.contains(actPrincipal)}">
						<jstl:set var="registered" value="true" />
					</jstl:if>
				<jstl:choose>
					<jstl:when test="${registered}">
						<spring:message code="activity.unregister" />
					</jstl:when>
					<jstl:otherwise>	
						<spring:message code="activity.register" />				
					</jstl:otherwise>
				</jstl:choose>
				 </a>
			</jstl:if>
			</display:column>
		</security:authorize>
	</jstl:if>
	
	<jstl:if test="${row.publicActivity == false && (role eq 'MANAGER' || role eq 'COLLABORATOR' || role eq 'ASSOCIATE')}">
		<spring:message code="activity.register" var="registerHeader" />
		<display:column title="${registerHeader}">
		<jstl:if test="${row.attendants.size() < row.maximumAttendants}">
			<a href="activity/user/${row.id}/register.do">
			<jstl:set var="registered" value="false" />
			<jstl:forEach var="att" items="${row.attendants}">
				<jstl:if test="${row.attendants.contains(actPrincipal)}">
					<jstl:set var="registered" value="true" />
				</jstl:if>
			</jstl:forEach>
			<jstl:choose>
				<jstl:when test="${registered}">
					<spring:message code="activity.unregister" />
				</jstl:when>
				<jstl:otherwise>	
					<spring:message code="activity.register" />				
				</jstl:otherwise>
			</jstl:choose>
			 </a>
		</jstl:if>
		</display:column>
	</jstl:if>
	
</display:table>

<ul class="legend">
    <li><span style="${full}"></span> <spring:message code= "activity.full" /></li>
    <li><span style="${passed}"></span> <spring:message code= "activity.passed" /></li>
    <li><span style="${Inminent}"></span> <spring:message code= "activity.inminent" /></li>
    <li><span style="${available}"></span> <spring:message code= "activity.available" /></li>
</ul>
<br>
<jstl:if test="${association !=null }">
<jstl:if test="${role eq 'MANAGER' || role eq 'COLLABORATOR'}">
	<br><br/>
<div><a class="btn btn-primary" href="activity/user/${association.id}/create.do"><spring:message code="activity.create"/></a></div>
<br/>
</jstl:if>
</jstl:if>
<br/>
</div>
<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
<jstl:if test="${association != null }">
<a class="btn btn-primary" href="association/${association.id}/display.do">&larr; <jstl:out value="${association.name}"/></a>
   <br><br><acme:lateralMenu/>
   </jstl:if>
</div>
</div>
