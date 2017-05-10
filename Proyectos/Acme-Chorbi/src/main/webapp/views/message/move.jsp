<%--
 * move.jsp
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


<form:form action="message/actor/move.do" modelAttribute="message">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sender"/>
	<form:hidden path="recipient"/>
	<form:hidden path="title"/>	
	<form:hidden path="body"/>
	<form:hidden path="moment"/>
	<form:hidden path="priority"/>

	<form:label path="folder">
		<spring:message code="message.folder" />:
	</form:label>
	<form:select id="folders" path="folder" >
		<form:option value="0" label="----"/>
		<form:options items="${folders}" itemValue="id" itemLabel="name"/>
	</form:select>
	<form:errors cssClass="error" path="priority" />
	<br />


	<input type="submit" name="save"
		value="<spring:message code="message.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="message.cancel" />"
		onclick="javascript: relativeRedir('message/list.do?folderId=${message.folder.id}');" />
	<br />

	

</form:form>