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
    <div class="container">
    
    
    <jstl:if test="${association.closedAssociation}">
    
    	<spring:message code="association.closed"/>
    	<br/>
        <jstl:if test="${role eq 'MANAGER'}">
      	 	<a class="btn btn-primary" href="user/association/${association.id}/open.do"><spring:message code="association.open"/></a>
     	 </jstl:if>
    	
    
    </jstl:if>
    
    <jstl:if test="${association.adminClosed}">
    
    	<spring:message code="association.banned"/>
    	<br/>
        <security:authorize access="hasRole('ADMIN')">
      		<a class="btn btn-primary" href="association/administrator/${association.id}/ban.do"><spring:message code="association.unban"/></a>
        </security:authorize>
    	
    
    </jstl:if>

	<jstl:if test="${!association.closedAssociation && !association.adminClosed}">
      <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
          <div class="jumbotron">
          <img src="<jstl:out value="${association.picture }"/>" align="right"  height="150" >
            <h1><jstl:out value="${association.name}" /></h1>
            <p><jstl:out value="${association.description}" /></p>
          </div>
          <div><jstl:out value="${association.address}" /></div>
          <div><jstl:out value="${association.creationDate}" /></div>
          <div><a href="${association.statutes}"><spring:message code="association.statutes"/></a></div>
          <div class="row">
            <div class="col-6 col-md-6 col-lg-4">
              <h2><spring:message code="association.announcements"/></h2>
              <p><jstl:out value="${association.announcements}" /></p>
            </div><!--/span-->
            <div class="col-6 col-md-6 col-lg-8">
              <h2><spring:message code="association.comments"/></h2>
              <%-- <display:table pagesize="5" class="displaytag" keepStatus="true" name="associationComments" requestURI="${requestURI}" id="row"> </display:table> --%>
              <display:table pagesize="5" class="displaytag" keepStatus="true"
					name="comments" requestURI="${requestURI }" id="row">
				
					<!--Attributes -->
					<spring:message code="comment.title" var="titleHeader" />
					<display:column property="title" title="${titleHeader}" sortable="true" />
				
					<spring:message code="comment.text" var="textHeader" />
					<display:column property="text" title="${textHeader}" sortable="true" />
				
					<spring:message code="comment.moment" var="momentHeader" />
					<display:column property="moment" title="${momentHeader}"  format="{0,date,dd/MM/yyyy HH:mm}"/>
					
					<spring:message code="comment.user" var="userHeader"/>
					<display:column title="${userHeader}">
						<a href="actor/user/${row.user.id}/display.do"> ${row.user.name} ${row.user.surname}</a>
					</display:column>
					
				</display:table>
				<form:form action="comment/user/${association.id }/edit.do" modelAttribute="comment">
	            	<form:hidden path="commentable"/>
	            	<form:input path="title" /></br>
	            	<form:textarea path="text"/></br>
	            	<acme:submit name="save" code="comment.new.save"/>
	            </form:form>
            </div><!--/span-->
          </div><!--/row-->
        </div><!--/span-->

        <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
          <div class="list-group">
          	<security:authorize access="isAuthenticated()"> 
          		<a href="user/association/${association.id}/listUsers.do" class="list-group-item"><spring:message code="association.user.list"/></a>
          	</security:authorize>
            <a href="section/${association.id}/list.do" class="list-group-item"><spring:message code="association.section"/></a>
            <a href="item/user/${association.id}/list.do" class="list-group-item"><spring:message code="association.item"/></a>
            <jstl:if test="${role eq 'MANAGER' || role eq 'COLLABORATOR'}">
            <a href="sanction/user/${association.id}/list.do" class="list-group-item"><spring:message code="association.sanction"/></a>
            <a href="loan/user/${association.id}/listPending.do" class="list-group-item"><spring:message code="association.loanPending"/></a>
            <a href="meeting/user/${association.id}/list.do" class="list-group-item"><spring:message code="association.meeting"/></a>
            </jstl:if>
            <jstl:if test="${role eq 'MANAGER'}">
            <a href="user/request/${association.id}/list.do" class="list-group-item"><spring:message code="association.request.list"/></a>
            <a href="user/association/${association.id}/changeManager.do" class="list-group-item"><spring:message code="association.manager.change"/></a>
            </jstl:if>
            <jstl:if test="${role eq 'ASSOCIATE' || role eq 'COLLABORATOR'}">
            <a href="user/association/${association.id}/leave.do" class="list-group-item"><spring:message code="association.leave"/></a>
            </jstl:if>
            <a href="activity/${association.id}/list.do" class="list-group-item"><spring:message code="association.activity"/></a>
          </div>
           <jstl:if test="${role eq 'MANAGER'}">
            <div>
	            <form:form action="message/actor/broadcast.do" modelAttribute="messageBroad">
	            	<form:hidden path="association"/>
	            	<acme:textarea code="association.message.broadcast" path="text"/>
	            	<acme:submit name="broadcast" code="association.message.post.broadcast"/>
	            </form:form>
	            <jstl:if test="${broadError != null}">
					<span class="message"><spring:message code="${broadError}" /></span>
				</jstl:if>	
            </div>
            </jstl:if>
            </br></br></br>
            <jstl:if test="${role eq 'MANAGER'}">
      	 <a class="btn btn-primary" href="user/association/${association.id}/close.do"><spring:message code="association.close"/></a>
      	 <br>
      	 </br>
      	 <a class="btn btn-primary" href="user/association/edit.do?associationId=${association.id}"><spring:message code="association.edit"/></a>
      </jstl:if>
      
      <security:authorize access="hasRole('ADMIN')">
      	<a class="btn btn-primary" href="association/administrator/${association.id}/ban.do"><spring:message code="association.ban"/></a>
      </security:authorize>
            
             <jstl:if test="${role == null && application == false}">
             <div>
	            <a class="btn btn-primary" href="user/request/${association.id}/apply.do"><spring:message code="association.request.apply"/></a>
	            
            </div>
            </jstl:if>
        </div><!--/span-->
      </div><!--/row-->
      
      
      </jstl:if>
</div>

