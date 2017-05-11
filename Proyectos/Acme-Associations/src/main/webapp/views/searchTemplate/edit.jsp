<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form modelAttribute="searchTemplate" action="searchTemplate/chorbi/edit.do">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<div>
		<form:label path="age">
			<spring:message code="searchTemplate.age" />:
		</form:label>
		<form:input type="number" path="age" />
		<form:errors cssClass="error" path="age" />
	</div>
 
  <acme:textarea code="searchTemplate.country" path="country"/>
  <br/>
  <acme:textarea code="searchTemplate.state" path="state"/>
  <br/>
  <acme:textarea code="searchTemplate.keyword" path="keyword"/>
  <br/>
  <acme:textarea code="searchTemplate.province" path="province"/>
  <br/>
  <acme:textarea code="searchTemplate.city" path="city"/>
  
  <br/>
  
  <form:label path="relationshipType">
		<spring:message code="searchTemplate.relationshipType" />:
	</form:label>
	<form:select path="relationshipType">
				<form:option value="">----</form:option>
                <form:option value="ACTIVITIES"><spring:message code="searchTemplate.activities" /></form:option>
                <form:option value="FRIENDSHIP"><spring:message code="searchTemplate.friendship" /></form:option>
                <form:option value="LOVE"><spring:message code="searchTemplate.love" /></form:option>
            </form:select>
	<form:errors cssClass="error" path="relationshipType" />
		
	<br/>
  
  <form:label path="genre">
		<spring:message code="searchTemplate.genre" />:
	</form:label>
	<form:select path="genre">
				<form:option value="">------</form:option>
                <form:option value="MAN"><spring:message code="searchTemplate.man" /></form:option>
                <form:option value="WOMAN"><spring:message code="searchTemplate.woman" /></form:option>
            </form:select>
	<form:errors cssClass="error" path="genre" />
		
	<br/>
  
  <acme:submit name="save" code="searchTemplate.save" />
  <acme:cancel url="welcome/index.do" code="searchTemplate.cancel" />

</form:form>