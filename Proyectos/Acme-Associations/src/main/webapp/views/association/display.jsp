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

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Off Canvas Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="../../dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="offcanvas.css" rel="stylesheet">
  </head>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="/WEB-INF/tags/functions" prefix="mask" %>

<body>
<security:authentication property="principal" var ="loggedactor"/>
    <div class="container">

      <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
          <p class="float-right hidden-md-up">
            <button type="button" class="btn btn-primary btn-sm" data-toggle="offcanvas">Toggle nav</button>
          </p>
          <div class="jumbotron">
            <%-- <h1><jstl:out value="${association.name}" /></h1> --%>
            <h1><jstl:out value="Titulo" /></h1>
            <%-- <p><jstl:out value="${association.description}" /></p> --%>
            <p><jstl:out value="Descripcion" /></p>
          </div>
         <%--  <div><jstl:out value="${association.address}" /></div> --%>
          <div><jstl:out value="Address" /></div>
          <%-- <div><jstl:out value="${association.creationDate}" /></div> --%>
          <div><jstl:out value="Creation Date" /></div>
          <%-- <div><a href="${association.statutes}"><spring:message code="association.statutes"/></a></div> --%>
          <div><a href="www.google.es"><spring:message code="association.statutes"/></a></div>
          <%-- <img src="<jstl:out value='${association.picture}'/>"  width="300"> --%>
          <img src="<jstl:out value=Foto/>"  width="300"> 
          <div class="row">
            <div class="col-6 col-lg-4">
              <h2><spring:message code="association.announcements"/></h2>
              <%-- <p><jstl:out value="${association.announcements}" /></p> --%>
              <p><jstl:out value="Announcements}" /></p>
            </div><!--/span-->
            <div class="col-6 col-lg-4">
              <h2><spring:message code="association.comments"/></h2>
              <%-- <p><jstl:out value="${association.comments}" /></p> --%>
              <p><jstl:out value="Comments" /></p>
            </div><!--/span-->
          </div><!--/row-->
        </div><!--/span-->

        <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
          <div class="list-group">
            <a href="section/user/list.do" class="list-group-item active"><spring:message code="association.section"/></a>
            <a href="item/user/list.do" class="list-group-item"><spring:message code="association.item"/></a>
            <a href="sanction/user/list.do" class="list-group-item"><spring:message code="association.sanction"/></a>
            <a href="loan/user/list.do" class="list-group-item"><spring:message code="association.loan"/></a>
            <a href="activity/user/list.do" class="list-group-item"><spring:message code="association.activity"/></a>
          </div>
        </div><!--/span-->
      </div><!--/row-->
</div>

 <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
    <script src="../../dist/js/bootstrap.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
    <script src="offcanvas.js"></script>
  </body>
  
  </html>



<%-- <security:authentication property="principal" var ="loggedactor"/>
    <div class="container">

      <div class="row">

        <div class="col-12 col-md-9">
          <div class="jumbotron">
            <h1><jstl:out value="${association.name}" /></h1>
            <h1><jstl:out value="Titulo" /></h1>
            <p><jstl:out value="${association.description}" /></p>
            <p><jstl:out value="Descripcion" /></p>
          </div>
          <div><jstl:out value="${association.address}" /></div>
          <div>Address</div>
          <div><jstl:out value="${association.creationDate}" /></div>
          <div>Creation date</div>
          <div><a href="${association.statutes}"><spring:message code="association.statutes"/></a></div>
          <div>Google<spring:message code="association.statutes"/></div>
          <img src="<jstl:out value='${association.picture}'/>"  width="300">
          <div class="row">
            <div class="col-6 col-lg-4">
              <h2>Announcements</h2>
              <p><jstl:out value="${association.announcements}" /></p>
              <p>Announcements</p>
            </div><!--/span-->
            <div class="col-6 col-lg-4">
              <h2>Comments</h2>
              <p><jstl:out value="${association.comments}" /></p>
              <p>Comments</p>
            </div><!--/span-->
          </div><!--/row-->
        </div><!--/span-->

        <div class="col-6 col-md-3" id="sidebar">
          <div class="list-group">
            <a href="section/user/list.do" class="list-group-item active"><spring:message code="association.section"/></a>
            <a href="item/user/list.do" class="list-group-item"><spring:message code="association.item"/></a>
            <a href="sanction/user/list.do" class="list-group-item"><spring:message code="association.sanction"/></a>
            <a href="loan/user/list.do" class="list-group-item"><spring:message code="association.loan"/></a>
            <a href="activity/user/list.do" class="list-group-item"><spring:message code="association.activity"/></a>
          </div>
        </div><!--/span-->
      </div><!--/row-->
</div> --%>