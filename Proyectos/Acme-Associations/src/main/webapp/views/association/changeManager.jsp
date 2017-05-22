<%--
 * edit.jsp
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
<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>

<form:form action="association/user/changeManager.do" modelAttribute="changeManager">

	<form:hidden path="association" />

	<form:label path="user">
		<spring:message code="association.manager.choose" />:
	</form:label>
	<form:select id="user" path="user">
		<jstl:forEach items="${users}" var="thisUser">
			<form:option value="${thisUser}" label="${thisUser.completeName}" />
		</jstl:forEach>
	</form:select>
<form:errors cssClass="error" path="user" />
</br>

	<form:label path="role">
		<spring:message code="association.role" />:
	</form:label>
	<form:select id="role" path="role">
			<form:option value="MANAGER" label="MANAGER" />
			<form:option value="COLLABORATOR" label="COLLABORATOR" />
			<form:option value="ASSOCIATE" label="ASSOCIATE" />
	</form:select>
<form:errors cssClass="error" path="role" />
</br>
	
	<input type="submit" name="save"
		value="<spring:message code="association.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="association.cancel" />"
		onclick="location.href = ('association/${association.id}/display.do');" />
	<br />

	

</form:form>