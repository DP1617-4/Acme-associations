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

	<spring:message code="meeting.meetings" var="meetingHeader"/>
	<spring:message code="meeting.issue" var="issueHeader" />
	
	<display:column title="${meetingHeader}">
			<a href="meeting/user/${row.association.id}/${row.id}/display.do"> 
			<spring:message code="meeting.meeting"></spring:message></a>
	</display:column>
	<display:column property="issue" title="${issueHeader}"/>
	
</display:table>

<jstl:if test="${role eq 'MANAGER'}">
	<div><a class="btn btn-primary" href="meeting/user/${association.id}/create.do"><spring:message code="meeting.create"/></a></div>
</jstl:if>

<br/>
