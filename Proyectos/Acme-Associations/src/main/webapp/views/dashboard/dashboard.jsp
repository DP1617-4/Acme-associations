<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<spring:message code="dashboard.display" var="titleHeader"/>
<table>
<thead>
	<tr>
		<th colspan = "3"><spring:message	code="dashboard.member.statistics" /></th>
	</tr>
	<tr>
		<th><spring:message	code="dashboard.member.min" /></th>
		<th><spring:message	code="dashboard.member.avg" /></th>
		<th><spring:message	code="dashboard.member.max" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${minMembers}</td>
		<td>${avgMembers}</td>
		<td>${maxMembers}</td>
	</tr>
</tbody>
</table>
<br/>

<h3><spring:message code="dashboard.association.average"/></h3>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="assoAVG" requestURI="dashboard/admin/dashboard.do" id="row">
	
	<spring:message code="association.name" var="nameHeader" />
	<spring:message code="association.description" var="descriptionHeader" />
	<spring:message code="association.creationDate" var="creationDateHeader" />
	<spring:message code="association.address" var="addressHeader" />
	

	<display:column title="${titleHeader}">
			<a href="association/${row.id}/display.do"> <spring:message code="dashboard.display" /></a>
	</display:column>
	<display:column property="name" title="${titleHeader}"/>
	<display:column property="description" title="${descriptionHeader}" />
	<display:column property="creationDate" title="${creationDateHeader}" />
	<display:column property="address" title="${addressHeader}"/>	

</display:table>


<h3><spring:message code="dashboard.association.loan"/></h3>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="assoLoan" requestURI="dashboard/admin/dashboard.do" id="row">
	
	<spring:message code="association.name" var="nameHeader" />
	<spring:message code="association.description" var="descriptionHeader" />
	<spring:message code="association.creationDate" var="creationDateHeader" />
	<spring:message code="association.address" var="addressHeader" />
	

	<display:column title="${titleHeader}">
			<a href="association/${row.id}/display.do"> <spring:message code="dashboard.display" /></a>
	</display:column>
	<display:column property="name" title="${titleHeader}"/>
	<display:column property="description" title="${descriptionHeader}" />
	<display:column property="creationDate" title="${creationDateHeader}" />
	<display:column property="address" title="${addressHeader}"/>	

</display:table>


<table>
<thead>
	<tr>
		<th colspan = "3"><spring:message	code="dashboard.loan.statistics" /></th>
	</tr>
	<tr>
		<th><spring:message	code="dashboard.loan.min" /></th>
		<th><spring:message	code="dashboard.loan.avg" /></th>
		<th><spring:message	code="dashboard.loan.max" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${minLoans}</td>
		<td>${avgLoans}</td>
		<td>${maxLoans}</td>
	</tr>
</tbody>
</table>
<br/>


<h3><spring:message code="dashboard.association.sanctions"/></h3>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="sanctionator" requestURI="dashboard/admin/dashboard.do" id="row">
	
	<spring:message code="association.name" var="nameHeader" />
	<spring:message code="association.description" var="descriptionHeader" />
	<spring:message code="association.creationDate" var="creationDateHeader" />
	<spring:message code="association.address" var="addressHeader" />
	

	<display:column title="${titleHeader}">
			<a href="association/${row.id}/display.do"> <spring:message code="dashboard.display" /></a>
	</display:column>
	<display:column property="name" title="${titleHeader}"/>
	<display:column property="description" title="${descriptionHeader}" />
	<display:column property="creationDate" title="${creationDateHeader}" />
	<display:column property="address" title="${addressHeader}"/>	

</display:table>


<h3><spring:message code="dashboard.association.inactive"/></h3>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="assoInac" requestURI="dashboard/admin/dashboard.do" id="row">
	
	<spring:message code="association.name" var="nameHeader" />
	<spring:message code="association.description" var="descriptionHeader" />
	<spring:message code="association.creationDate" var="creationDateHeader" />
	<spring:message code="association.address" var="addressHeader" />
	

	<display:column title="${titleHeader}">
			<a href="association/${row.id}/display.do"> <spring:message code="dashboard.display" /></a>
	</display:column>
	<display:column property="name" title="${titleHeader}"/>
	<display:column property="description" title="${descriptionHeader}" />
	<display:column property="creationDate" title="${creationDateHeader}" />
	<display:column property="address" title="${addressHeader}"/>	

</display:table>

<h3><spring:message code="dashboard.user.sanctioned"/></h3>
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="sanctioned" requestURI="dashboard/admin/dashboard.do" id="row">

	<spring:message code="user.completeName" var="nameHeader" />
	<spring:message code="user.email" var ="emailHeader" />
	<spring:message code="user.postalAddress" var ="postalHeader"/>
	
	<display:column title="${titleHeader}">
			<a href="actor/actor/${row.id}/display.do"><spring:message code="dashboard.display" /></a>
	</display:column>
	<display:column property="completeName" title="${nameHeader}"/>
	<display:column property="email" title="${emailHeader}" />
	<display:column property="postalAddress" title="${postalHeader}" />
</display:table>

<h3><spring:message code="dashboard.activity.active"/></h3>

<display:table pagesize="5" keepStatus="true"
	name="activities" requestURI="${requestURI}" id="row">
	
	<spring:message code="activity.name" var="nameHeader" />
	<spring:message code="activity.description" var="descriptionHeader" />
	<spring:message code="activity.startMoment" var="startMomentHeader" />
	<spring:message code="activity.endMoment" var="endMomentHeader" />
	<spring:message code="activity.maximumAttendants" var="maximumAttendantsHeader" />
	<spring:message code="activity.public" var="publicHeader"/>
	
	
	<display:column property="name" title="${nameHeader}" sortable="true" />
	<display:column property="description" title="${descriptionHeader}" sortable="true"  />
	<display:column property="startMoment" title="${startMomentHeader}" sortable="true"  />
	<display:column property="endMoment" title="${endMomentHeader}" sortable="true"  />
	<display:column property="maximumAttendants" title="${maximumAttendantsHeader}" sortable="true" />
	<display:column property="publicActivity" title="${publicHeader }" sortable="true" />
	
	
	
</display:table>