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
		<h2><jstl:out value="${meeting.issue}" /></h2>
	</div>
          
  	<div class="row">
  	
		<div class="col-6 col-md-6 col-lg-6">
    		<h2><spring:message code="meeting.description"/></h2>
	        	<div><b><spring:message code="meeting.agenda"/>:</b> <a href=${meeting.agenda}><jstl:out value="${meeting.agenda}" /></a></div>
	        	<div><b><spring:message code="meeting.address"/>:</b> <jstl:out value="${meeting.address}" /></div>
	  			<div><b><spring:message code="meeting.moment"/>:</b> <jstl:out value="${meeting.moment}" /></div>
	  			
	  		<h2><spring:message code="meeting.comments"/></h2>
				
				<display:table pagesize="5" class="displaytag" keepStatus="false" name="comments" requestURI="${requestURI}" id="row">
					<!--Attributes -->
					<spring:message code="comment.title" var="titleHeader" />
					<spring:message code="comment.text" var="textHeader" />
					<spring:message code="comment.moment" var="momentHeader" />
					<spring:message code="comment.user" var="userHeader"/>
					
					<display:column property="title" title="${titleHeader}" sortable="true" />
					<display:column property="text" title="${textHeader}" sortable="true" />
					<display:column property="moment" title="${momentHeader}"  format="{0,date,dd/MM/yyyy HH:mm}"/>
					<display:column title="${userHeader}">
						<a href="actor/actor/${row.user.id}/display.do"> ${row.user.name} ${row.user.surname}</a>
					</display:column>
					
				</display:table>
				
				<jstl:if test="${role eq 'MANAGER' || role eq 'COLLABORATOR'}">
					<form:form action="comment/user/${meeting.id}/edit.do" modelAttribute="comment">
	            		<form:hidden path="commentable"/>
		            	<acme:textbox code="comment.title" path="title"/><br />
	            		<acme:textarea code="comment.text" path="text"/><br />
	            		<acme:submit name="save" code="comment.new.save"/>
	            	</form:form>
	            </jstl:if>
	            
    	</div><!--/span-->
    
    	<div class="col-6 col-md-6 col-lg-6">
			<h2><spring:message code="meeting.minute.description"/></h2>
			<jstl:if test="${minutes != null}">
				<div><b><spring:message code="meeting.minute.document"/>: </b><a href="${minutes.document}" ><jstl:out value="${minutes.document}" /></a></div>
				</br>
				<display:table pagesize="5" class="displaytag" keepStatus="false" name="participants" requestURI="${requestURI}" id="row">
					
					<spring:message code="meeting.minute.users" var="userHeader"/>
					
					<display:column title="${userHeader}">
						<a href="actor/actor/${row.id}/display.do"> ${row.name} ${row.surname}</a>
					</display:column>
					
				</display:table>
			
				<jstl:if test="${role eq 'MANAGER'}">
					<form:form action="minutes/user/addParticipants.do" modelAttribute="addParticipant">
						<form:hidden path="minute"/>
						<form:label path="user">
							<spring:message code="meeting.addParticipant" />:
						</form:label>
						<form:select id="user" path="user">
							<jstl:forEach items="${users}" var="thisUser">
								<form:option value="${thisUser.id}" label="${thisUser.completeName}" />
							</jstl:forEach>
							<spring:message code="meeting.addParticipant"/></a>
						</form:select>
						</br>
						<acme:submit name="save" code="meeting.addParticipant"/>
					</form:form>
				</jstl:if>
				
				<h2><spring:message code="meeting.comments"/></h2>
				
				<display:table pagesize="5" class="displaytag" keepStatus="false" name="commentsSecond" requestURI="${requestURI}" id="row">
					<!--Attributes -->
					<spring:message code="comment.title" var="titleHeader" />
					<spring:message code="comment.text" var="textHeader" />
					<spring:message code="comment.moment" var="momentHeader" />
					<spring:message code="comment.user" var="userHeader"/>
					
					<display:column property="title" title="${titleHeader}" sortable="true" />
					<display:column property="text" title="${textHeader}" sortable="true" />
					<display:column property="moment" title="${momentHeader}"  format="{0,date,dd/MM/yyyy HH:mm}"/>
					<display:column title="${userHeader}">
						<a href="actor/actor/${row.user.id}/display.do"> ${row.user.name} ${row.user.surname}</a>
					</display:column>
				</display:table>
				
					<form:form action="comment/user/${meeting.id}/edit.do" modelAttribute="commentSecond">
	            		<form:hidden path="commentable"/>
		            	<acme:textbox code="comment.title" path="title"/><br />
	            		<acme:textarea code="comment.text" path="text"/><br />
	            		<acme:submit name="save" code="comment.new.save"/>
	            	</form:form>
			</jstl:if>

			<jstl:if test="${esAnterior == true}">
				<jstl:if test="${minutes == null}">
					<spring:message code="meeting.noMinute"/>
					<jstl:if test="${role eq 'MANAGER'}">
						<div><a class="btn btn-primary" href="minutes/user/${association.id}/${meeting.id}/create.do"><spring:message code="minute.create"/></a></div>
					</jstl:if>
				</jstl:if>
			</jstl:if>
			
			<jstl:if test="${esAnterior == false}">
				<jstl:if test="${minutes == null}">
					<spring:message code="meeting.minutes.cannot.create"/>
				</jstl:if>
			</jstl:if>
		</div><!--/span-->
    
	</div>
	
</body>