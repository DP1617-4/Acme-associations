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

<form:form action="${requestURI}" modelAttribute="place">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="place.address" path="address"/><br />
	<acme:number max="180" min="-180" step="any" code="place.longitude" path="longitude"/><br />
	<acme:number max="85" min="-85" step="any" code="place.latitude" path="latitude"/><br />
	
	<input type="submit" name="save"
		value="<spring:message code="place.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="place.cancel" />"
		onclick="location.href = ('${cancelURI}');" />
	<br />

	

</form:form>