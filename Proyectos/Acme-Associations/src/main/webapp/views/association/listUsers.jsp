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
	<spring:message code="association.role" var="roleHeader" />
	<spring:message code="association.sanction" var="sanctionsHeader" />
	<spring:message code="association.kick" var="kickHeader" />
	

	<display:column title="${nameHeader}">
		<a href="actor/actor/${row.user.id}/display.do"> <jstl:out value="${row.user.name }"/> <jstl:out value="${row.user.surname}"/></a>
	</display:column>
	<display:column property="type" title="${roleHeader}" sortable="true"/>
	
	<jstl:if test="${not empty role && (role.type eq 'COLLABORATOR' || role.type eq 'MANAGER')}">
		<display:column title="${sanctionsHeader}">
			<jstl:if test="${row.user.userAccount != loggedactor}">
				<a href="sanction/user/${association.id}/listByUserActive.do?userId=${row.user.id}"><spring:message code="association.sanction"/></a>
			</jstl:if>
		</display:column>
	</jstl:if>
	<jstl:if test="${not empty role &&(role.type eq 'MANAGER')}">
	<display:column title="${kickHeader}">
		<jstl:if test="${row.user.userAccount != loggedactor}">
			
				<a href="association/user/${association.id}/kick.do?roleId=${row.id}"><spring:message code="association.kick"/></a>
		
		</jstl:if>
	</display:column>
	</jstl:if>
	<security:authorize access="hasRole('ADMIN')">
		<display:column title="${sanctionsHeader}">
			<jstl:if test=" ${row.user.userAccount != loggedactor}">
				<a href="sanction/user/${association.id}/listByUserActive.do?userId=${row.user.id}"><spring:message code="association.sanction"/></a>
			</jstl:if>
		</display:column>
	</security:authorize>
			
			
			
			
	
</display:table>
<br/>
	