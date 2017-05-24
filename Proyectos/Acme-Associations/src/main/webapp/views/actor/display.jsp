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
            <h1><jstl:out value="${actor.completeName}" /></h1>
            <p><jstl:out value="${actor.email}" /></p>
          </div>
          <div><jstl:out value="${actor.phoneNumber}" /></div>
          <div><jstl:out value="${actor.postalAddress}" /></div>
          <div><jstl:out value="${actor.idNumber}"/></div>
          <jstl:if test="${loggedactor == actor.userAccount }">
          <div class="row">
            <div class="col-12 col-lg-6">
              <h2><spring:message code="actor.messages"/></h2>
              <display:table pagesize="5" class="displaytag" keepStatus="false"
					name="messages" requestURI="${requestURI}" id="row">
				
					<!--Attributes -->
					<spring:message code="actor.message.title" var="titleHeader" />
					<display:column property="title" title="${titleHeader}" sortable="true" />
				
					<spring:message code="actor.message.text" var="textHeader" />
					<display:column property="text" title="${textHeader}" sortable="true" />
				
					<spring:message code="actor.message.moment" var="momentHeader" />
					<display:column property="moment" title="${momentHeader}"  format="{0,date,dd/MM/yyyy HH:mm}" sortable="true"/>
					
					<spring:message code="actor.message.sender" var="userHeader"/>
					<display:column title="${userHeader}">
						<a href="actor/actor${row.sender.id}/display.do"> ${row.sender.name} ${row.sender.surname}</a>
					</display:column>
					
				</display:table>
            </div><!--/span-->
           

        <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar" align="right">
          <div class="list-group">
          <security:authorize access="hasRole('USER')">
            <a href="user/user/edit.do" class="list-group-item active"><spring:message code="actor.editProfile"/></a>
          </security:authorize>
            <a href="folder/actor/list.do" class="list-group-item"><spring:message code="actor.folders"/></a>
          </div>
        </div><!--/span-->
      </div><!--/row-->
       </jstl:if>
     </div>
    </div>
</div>