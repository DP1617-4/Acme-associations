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

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="items" requestURI="${requestURI}" id="row">
	

	<!-- Attributes -->
	<spring:message code="item.name" var="nameHeader" />
	<spring:message code="master.page.association" var="associationHeader" />
	<display:column title="${nameHeader}">
		<jstl:if test="${ association!=null}">
		<a href="item/user/${association.id}/display.do?itemId=${row.id}"><jstl:out value="${row.name}"/></a>
		</jstl:if>
		<jstl:if test="${ association==null}">
		<jstl:out value="${row.name}"/>
		</jstl:if>
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