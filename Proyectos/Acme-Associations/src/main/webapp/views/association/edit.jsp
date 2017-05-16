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

<form:form action="${requestURI}" modelAttribute="event">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="closedAssociation"/>
	<form:hidden path="adminClosed"/>

	<acme:textbox code="association.name" path="name"/><br />
	
	<acme:textbox code="association.description" path="description"/><br />
	
	<acme:textbox code="association.address" path="address"/><br />
	
	<div>
		<form:label path="creationDate">
			<spring:message code="association.creationDate" />:
		</form:label>
		<form:input placeholder="dd/MM/yyyy HH:mm" path="creationDate" />
		<form:errors cssClass="error" path="creationDate" />
	</div> </br>
	
	<acme:textbox code="association.statutes" path="statutes"/><br />
	
	<acme:textbox code="association.announcements" path="announcements"/><br />
	
	<acme:textbox code="association.picture" path="picture"/><br />
	
	<input type="submit" name="save"
		value="<spring:message code="association.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="association.cancel" />"
		onclick="location.href = ('association/list.do');" />
	<br />

	

</form:form>