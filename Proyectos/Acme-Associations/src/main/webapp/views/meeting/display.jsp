<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="/WEB-INF/tags/functions" prefix="mask" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<body>
<security:authentication property="principal" var ="loggedactor"/>

	<div class="jumbotron">
		<p><jstl:out value="${meeting.issue}" /></p>
	</div>
  
	<div><jstl:out value="${meeting.agenda}" /></div>
  	<div><jstl:out value="${meeting.address}" /></div>
  	<div><jstl:out value="${meeting.moment}" /></div>
          
  	<div class="row">
  	
		<div class="col-6 col-md-6 col-lg-8">
    		<h2><spring:message code="meeting.description"/></h2>
	        	<div><jstl:out value="${meeting.agenda}" /></div>
	        	<div><jstl:out value="${meeting.address}" /></div>
	  			<div><jstl:out value="${meeting.moment}" /></div>
	  			
	  		<h2><spring:message code="meeting.comments"/></h2>
				
				<display:table pagesize="5" class="displaytag" keepStatus="true" name="comments" requestURI="${requestURI}" id="row">
					<!--Attributes -->
					<spring:message code="comment.title" var="titleHeader" />
					<spring:message code="comment.text" var="textHeader" />
					<spring:message code="comment.moment" var="momentHeader" />
					<spring:message code="comment.user" var="userHeader"/>
					
					<display:column property="title" title="${titleHeader}" sortable="true" />
					<display:column property="text" title="${textHeader}" sortable="true" />
					<display:column property="moment" title="${momentHeader}"  format="{0,date,dd/MM/yyyy HH:mm}"/>
					<display:column title="${userHeader}">
						<a href="actor/user/${row.user.id}/display.do"> ${row.user.name} ${row.user.surname}</a>
					</display:column>
					
				</display:table>
				
				<form:form action="comment/user/${association.id}/${meeting.id}/edit.do" modelAttribute="comment">
	            	<form:hidden path="commentable"/>
	            	<form:input path="title" /> </br>
	            	<form:textarea path="text"/> </br>
	            	<acme:submit name="save" code="comment.new.save"/>
	            </form:form>
	            
    	</div><!--/span-->
    
    	<div class="col-6 col-md-6 col-lg-8">
			<h2><spring:message code="meeting.minute.description"/></h2>
			
			<jstl:if test="${minute != null}">
				<div><jstl:out value="${minute.document}" /></div>
				<display:table pagesize="5" class="displaytag" keepStatus="true" name="participants" requestURI="${requestURI}" id="row">
					
					<spring:message code="meeting.minute.user" var="userHeader"/>
					<spring:message code="meeting.minutes.user.role" var="roleHeader" />
					
					<display:column title="${userHeader}">
						<a href="actor/user/${row.user.id}/display.do"> ${row.user.name} ${row.user.surname}</a>
					</display:column>
					<display:column property="type" title="${roleHeader}" sortable="true"/>
					
				</display:table>
			
				<jstl:if test="${role eq 'MANAGER'}">
					<form:label path="user">
						<spring:message code="meeting.addParticipant" />:
					</form:label>
					<form:select id="user" path="user">
						<jstl:forEach items="${users}" var="thisUser">
							<form:option value="${thisUser.id}" label="${thisUser.completeName}" />
						</jstl:forEach>
						<a class="btn btn-primary" href="minutes/user/${association.id}/${meeting.id}/addParticipant.do">
						<spring:message code="meeting.addParticipant"/></a>
					</form:select>
				</jstl:if>
			</jstl:if>
			
			<jstl:if test="${minute == null}">
				<jstl:if test="${role eq 'MANAGER'}">
					<div><a class="btn btn-primary" href="minutes/user/${association.id}/${meeting.id}/create.do"><spring:message code="minute.create"/></a></div>
				</jstl:if>
			</jstl:if>
		</div><!--/span-->
    
	</div>
	
</body>