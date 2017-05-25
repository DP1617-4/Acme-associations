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
<display:table pagesize="5" keepStatus="false" name="requests" requestURI="${requestURI}" id="row">
	
	<spring:message code="request.user.name" var="nameHeader" />
	<spring:message code="request.accept" var="acceptHeader" />
	<spring:message code="request.deny" var="denyDateHeader" />
	<spring:message code="request.cancel" var="cancelDateHeader" />
	

	<display:column title="${nameHeader}">
		<a href="actor/actor/display.do?userId=${row.user.id}"><jstl:out value="${row.user.name}"/> <jstl:out value="${row.user.surname}"/></a>
	</display:column>
	
	<jstl:if test="${association != null }">
		<display:column title="${acceptHeader}">
			<a href="request/user/${row.id}/accept.do"> <spring:message code="request.accept" /></a>
		</display:column>
		
		<display:column title="${denyHeader}">
			<a href="request/user/${row.id }/deny.do"><spring:message code="request.deny" /></a>
		</display:column>
	</jstl:if>
	<jstl:if test="${association == null }">
		<display:column title="${acceptHeader}">
			<a href="request/user/${row.id}/cancel.do"><spring:message code="request.cancel" /></a>
		</display:column>
	</jstl:if>
	
</display:table>



<br/>

		