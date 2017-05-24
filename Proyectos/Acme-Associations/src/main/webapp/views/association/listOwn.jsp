<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<jstl:set var="full" value="font-weight: grey; color:grey; background-color:white;" />
<jstl:set var="Inminent" value="color:white; font-weight:bold; background-color:black;" />
<jstl:set var="passed" value="background-color:red; color: black; font-weight:bold;" />
<jstl:set var="available" value="background-color:green; color: black; font-weight:bold;" />


<display:table pagesize="5" keepStatus="false"
	name="roles" requestURI="${requestURI}" id="row">
	<security:authentication property="principal" var ="loggedactor"/>
	
	<spring:message code="association.name" var="nameHeader" />
	<spring:message code="association.description" var="descriptionHeader" />
	<spring:message code="association.creationDate" var="creationDateHeader" />
	<spring:message code="association.address" var="addressHeader" />
	<spring:message code="association.role" var="roleHeader" />
	

	<display:column title="${titleHeader}">
		<a href="association/${row.association.id}/display.do"> <jstl:out value="${row.association.name }"/></a>
	</display:column>
	<display:column property="association.description" title="${descriptionHeader}" />
	<display:column property="association.creationDate" title="${creationDateHeader}" />
	<display:column property="association.address" title="${addressHeader}"/>
	<display:column property="type" title="${roleHeader}" sortable="true"/>
			
			
			
			
	
</display:table>

<br/>
	