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

      <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
          <div class="jumbotron">
            <h1><jstl:out value="${minutes.meeting.issue}" /></h1>
            <p><jstl:out value="${minutes.meeting.moment}" /></p>
          </div>
          <div><jstl:out value="${minutes.meeting.address}" /></div>
          <div><jstl:out value="${minutes.meeting.agenda}" /></div>
          <div><jstl:out value="${minutes.document}" /></div>

          <jstl:if test="${role eq 'MANAGER'}">
            <div class="col-6 col-lg-4">
	            <form:form action="minutes/user/$(association.id)/$(meeting.id)/addParticipants.do" modelAttribute="addParticipants">
	            
	            	<form:hidden path="minutes"/>

					<form:label path="user">
						<spring:message code="minutes.addParticipants" />:
					</form:label>
					<form:select id="user" path="user">
						<jstl:forEach items="${users}" var="thisUser">
							<form:option value="${thisUser}" label="${thisUser.completeName}" />
						</jstl:forEach>
					</form:select>
					<input type="submit" name="add" value="<spring:message code="minutes.add" />" />&nbsp; 
	            	
	            </form:form>
	            <jstl:if test="${addError != null}">
					<span class="message"><spring:message code="${addError}" /></span>
				</jstl:if>	
            </div>
          </jstl:if>
            
          <div class="row">
            <div class="col-12 col-lg-6">
              <h2><spring:message code="minutes.users"/></h2>
              <display:table pagesize="5" class="displaytag" keepStatus="false"
					name="users" requestURI="${requestURI}" id="row">
				
					<!--Attributes -->
					<spring:message code="minutes.user" var="userHeader"/>
					<display:column title="${userHeader}">
						<a href="actor/actor/${row.user.id}/display.do"> ${row.user.name} ${row.user.surname}</a>
					</display:column>
					
			   </display:table>
            </div><!--/span-->
           
      </div><!--/row-->
     </div>
    </div>
</div>


