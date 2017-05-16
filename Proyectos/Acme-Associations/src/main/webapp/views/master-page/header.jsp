<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<nav class="navbar navbar-inverse navbar-fixed-top navbar-collapse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed"
        data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">MyLooks</span> <span class="icon-bar"></span>
        <span class="icon-bar"></span> <span class="icon-bar"></span>
      </button>
      <a href="#" class="navbar-brand"><img onError="imgError(this);"
        src="images/YTijeraBlanco.png" width="80px" height="25px" /></a>
    </div>
    <div class="collapse navbar-collapse"
      id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">

<security:authorize access="hasRole('ADMIN')">

          <li><a href="owner/register.do"><spring:message
                code="master.page.admin.registerOwner" /></a></li>
</security:authorize>

        <security:authorize access="hasRole('OWNER')">
          <li><a href="owner/edit.do"><spring:message
                code="master.page.owner.editOwner" /></a></li>
          <li><a href="worker/owner/register.do"><spring:message
                code="master.page.owner.createWorker" /></a></li>
          <li><a href="establishment/owner/listByOwner.do"><spring:message
                code="master.page.owner.list" /></a></li>
          <li><a class="fNiv" href="establishment/owner/0/edit.do"><spring:message
                code="master.page.establishments.create" /></a></li>
        </security:authorize>

        <li><a class="fNiv" href="establishment/list.do"><spring:message
              code="master.page.establishments" /></a></li>


      </ul>
      <ul class="nav navbar-nav navbar-right">
        <security:authorize access="isAnonymous()">
            <li class="dropdown"><a href="security/login.do" ><b>Login</b> </a>
              </li>
        </security:authorize>
        <security:authorize access="isAuthenticated()">
          <li class="dropdown"><a href="#" class="dropdown-toggle fNiv" data-toggle="dropdown" type="button">
              <div class="avatar">
                <img onError="imgError(this);" src="${actor.picture }" />
              </div>
          </a>
            <ul class="dropdown-menu">
              <li class="arrow"></li>

              
              <li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
            </ul></li>
        </security:authorize>
      </ul>
    </div>
  </div>
</nav>