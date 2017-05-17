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
        <span class="sr-only">Acme Associations</span> <span class="icon-bar"></span>
        <span class="icon-bar"></span> <span class="icon-bar"></span>
      </button>
      <a href="welcome/index.do" class="navbar-brand"><img onError="imgError(this);"
        src="https://i.imgur.com/i8YWDoL.png" height="50px" /></a>
    </div>
    <div class="collapse navbar-collapse"
      id="bs-example-navbar-collapse-1">
    <ul class="nav navbar-nav navbar-right">

	<security:authorize access="hasRole('ADMIN')">

         <li><a class="fNiv" href="association/administrator/list.do"><spring:message code="master.page.association.list" /></a></li>
		<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="dropdown"></li>
					<li><a href="systemConfiguration/administrator/edit.do"><spring:message code="master.page.system" /></a></li>
					<li><a href="systemConfiguration/administrator/dashboard.do"><spring:message code="master.page.dashboard" /></a></li>	
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>				
				</ul>
			</li>
		</security:authorize>

        <security:authorize access="hasRole('USER')">
			<li class="dropdown"></li>
			<li><a href="association/list.do"><spring:message code="master.page.association.list" /></a></li>
			<li><a href="user/association/listOwn.do"><spring:message code="master.page.association.list.own" /></a></li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="dropdown"></li>
					<li><a href="user/user/display.do"><spring:message code="master.page.user.display" /></a></li>
					<li><a href="user/user/edit.do"><spring:message code="master.page.user.edit" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
        </security:authorize>

        


      
      
        <security:authorize access="isAnonymous()">
        	<li><a class="fNiv" href="association/list.do"><spring:message
              code="master.page.association" /></a></li>
             <li><a class="fNiv" href="user/register.do"><spring:message code="master.page.register.as.user" /></a></li>
             <li class="Fniv"><a href="security/login.do" ><b>Login</b> </a></li>
              <div class="dropdown-menu">
  				<button class="dropbtn">Dropdown</button>
  				<div class="dropdown-content">
    				<a href="#">Link 1</a>
    				<a href="#">Link 2</a>
    				<a href="#">Link 3</a>
  				</div>
			  </div>

			 </ul>
        </security:authorize>
        <security:authorize access="isAuthenticated()">
        		
              <li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
        </security:authorize>
      </ul>
    </div>
  </div>
</nav> 

