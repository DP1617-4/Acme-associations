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

<form:form action="${requestURI}" modelAttribute="activity">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="association"/>
	<form:hidden path="attendants"/>

	<acme:textbox code="activity.name" path="name"/><br />
	<acme:textbox code="activity.description" path="description"/><br />
	<div>
		<form:label path="startMoment">
			<spring:message code="activity.startMoment" />:
		</form:label>
		<form:input placeholder="dd/MM/yyyy HH:mm" path="startMoment" />
		<form:errors cssClass="error" path="startMoment" />
	</div> <br />
	<div>
		<form:label path="endMoment">
			<spring:message code="activity.endMoment" />:
		</form:label>
		<form:input placeholder="dd/MM/yyyy HH:mm" path="endMoment" />
		<form:errors cssClass="error" path="endMoment" />
	</div>
	<form:errors cssClass="error" path="" />
	<acme:textbox code="activity.maximumAttendants" path="maximumAttendants"/><br />
	<form:label path="publicActivity" >
		<b><spring:message code="activity.public" /> :</b>
	</form:label>
	<form:checkbox path="publicActivity" id="publicActivity" onchange="javascript: toggleSubmit()"/>
	<form:errors path="publicActivity" cssClass="error" />
  	<br /><br />
	<form:label path="item">
		<spring:message code="activity.item" />:
	</form:label>
	<form:select id="item" path="item">
		<form:option value="0" label="-------" />
		<jstl:forEach items="${item}" var="thisItem">
			<form:option value="${thisItem}" label="${thisItem.name}" />
		</jstl:forEach>
	</form:select>
	<form:errors cssClass="error" path="item" /> <br />

	<br/>
	<input type="submit" name="save"
		value="<spring:message code="activity.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="activity.cancel" />"
		onclick="location.href = ('activity/${activity.id}/display.do');" />
	<br />

	

</form:form>