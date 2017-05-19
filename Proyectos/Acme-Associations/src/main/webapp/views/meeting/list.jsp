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

<security:authentication property="principal" var ="loggedactor"/>
<display:table pagesize="5" keepStatus="true" name="meetings" requestURI="${requestURI}" id="row">

	<spring:message code="meeting.moment" var="momentHeader" />
	<spring:message code="meeting.address" var="addressHeader" />
	<spring:message code="meeting.agenda" var="agendaHeader" />
	<spring:message code="meeting.issue" var="issueHeader" />
	
	<display:column title="${minutesHeader}">
			<a href="minutes/user/${row.association.id}/${row.id}/display.do"> 
			<spring:message code="meeting.minutes"></spring:message></a>
	</display:column>
	
	<display:column property="moment" title="${momentHeader}"/>
	<display:column property="address" title="${addressHeader}" />
	<display:column property="agenda" title="${agendaHeader}" />
	<display:column property="issue" title="${issueHeader}"/>

</display:table>
<br><br/>
<div><a class="btn btn-primary" href="user/meeting/create.do"><spring:message code="meeting.create"/></a></div>
<br/>
