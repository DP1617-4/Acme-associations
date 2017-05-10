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

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="chorbis" requestURI="${requestURI}" id="row">
	
	
	<security:authorize access="hasRole('ADMIN')">
	<spring:message code="chorbi.bans" var="banHeader" />
	<display:column title="${banHeader}">
		<a href="chorbi/administrator/ban.do?chorbiId=${row.id}">
		<jstl:choose>
			<jstl:when test="${row.banned}">
				<spring:message code="chorbi.unban" />
			</jstl:when>
			<jstl:otherwise>
				<spring:message code="chorbi.ban" />
			</jstl:otherwise>
		</jstl:choose>
		</a>
	</display:column>
		
</security:authorize>

	<!-- Attributes -->
	<spring:message code="chorbi.name" var="nameHeader" />
	<display:column title="${nameHeader}" sortable="true" >
		${mask:mask(row.name) }
	</display:column>

	<spring:message code="chorbi.surname" var="chorbiHeader" />
	<display:column title="${chorbiHeader}" sortable="true">
		${mask:mask(row.surname) }
	</display:column>
	
	<spring:message code="chorbi.description" var="descriptionHeader" />
	<display:column title="${descriptionHeader}" sortable="true">
		${mask:mask(row.description) }
	</display:column>

	<spring:message code="chorbi" var="chorbiHeader" />
	<display:column title="${chorbiHeader}">
		<a href="actor/actor/display.do?actorId=${row.id}"><jstl:out value="${mask:mask(row.name) }" /> <jstl:out value="${mask:mask(row.surname) }" /> </a>
	</display:column>
	
	<security:authorize access="hasAnyRole('CHORBI')">
		<spring:message code="chorbi.like" var="likeHeader" />
		<display:column title="${likeHeader}">
		<jstl:forEach items="${likes}" var="likes">
			<jstl:choose>
				<jstl:when test="${row.id == likes.liked.id}">
					<a href="likes/chorbi/delete.do?likesId=${likes.id}">
		 			<spring:message code="chorbi.dislike" />
		 			</a>
				</jstl:when>
				<jstl:otherwise>
					<a href="likes/chorbi/create.do?likedId=${row.id}">
		 			<spring:message code="chorbi.like" />
		 			</a>
				</jstl:otherwise>
			</jstl:choose>
		</jstl:forEach>
		</display:column>
	</security:authorize>
	
</display:table>
<br/>