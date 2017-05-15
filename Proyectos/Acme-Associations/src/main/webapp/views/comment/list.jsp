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

<security:authentication property="principal" var ="loggedactor"/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="likes" requestURI="${requestURI}" id="row">
	
	<jstl:set var="chorbi" value="${row.chorbi}"/>

	<!-- Attributes -->
	<spring:message code="likes.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="true" />

	<spring:message code="likes.comment" var="commentHeader" />
	<display:column property="comment" title="${commentHeader}" sortable="true" />
	
	<spring:message code="likes.view" var="likesHeader" />
	<display:column title="${likesHeader}">
		<a href="likes/chorbi/display.do?likesId=${row.id}"><spring:message code="likes.view" /></a>
	</display:column>
	
	<security:authorize access="hasRole('CHORBI')">
		<jstl:if test="${row.chorbi.userAccount.username==loggedactor.username}">
			<spring:message code="likes.unlike" var="unlikeHeader" />
			<display:column title="${unlikeHeader}">
				<a href="likes/chorbi/delete.do?likesId=${row.liked.id}"><spring:message code="likes.unlike" /></a>
			</display:column>
		</jstl:if>
	</security:authorize>
	
</display:table>
<br/>