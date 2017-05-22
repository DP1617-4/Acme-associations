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
            <h1><jstl:out value="${activity.name}" /></h1>
            <p><jstl:out value="${activity.description}" /></p>
          </div>
          <div><jstl:out value="${activity.startMoment}" /></div>
          <div><jstl:out value="${activity.endMoment}" /></div>
          <div><jstl:out value="${activity.maximumAttendants}" /></div>
          <div><jstl:out value="${activity.place.address}" /></div>
          <div><jstl:out value="${activity.place.latitude}" /></div>
          <div><jstl:out value="${activity.place.longitude}" /></div>
          <div><jstl:out value="${activity.prize}" /></div>
          <div><jstl:out value="${activity.winner}" /></div>
        </div><!--/span-->

        <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
          <div class="list-group">
            <a href="association/${association.id}/display.do" class="list-group-item"><spring:message code="activity.associationDisplay"/></a>
        </div><!--/span-->
      </div><!--/row-->
     </div>
</div>

