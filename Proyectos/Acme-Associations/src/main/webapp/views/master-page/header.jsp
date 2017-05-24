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

<nav class="navbar navbar-inverse navbar-fixed-top">

  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed"
        data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Acme Associations</span> <span class="icon-bar"></span>
        <span class="icon-bar"></span> <span class="icon-bar"></span>
      </button>
      <a href="welcome/index.do" class="navbar-brand"><img onError="imgError(this);"
        src="http://i.imgur.com/El7I1xd.png" height="65px" /></a>
    </div>
    <div class="navbar-collapse collapse">
    
    
		
   	 <ul class="nav navbar-nav navbar-right">
	
	<form id="form1" method="get" action="item/filter.do" class = "navbar-nav mr-auto" >
		<div class="col-sm-3 col-md-3">
			<button class="btn navbar-btn" type="submit" form="form1" value="filter"><spring:message code="header.filter"/></button>
		</div>
		<div class="col-sm-8 col-md-8">
			<input class="form-control mr-sm-2" placeholder="<spring:message code="header.search"/>" type="text" name="filter"/>
		</div>
	</form>
	
	<li><a class="fNiv" href="activity/list.do"><spring:message
              code="association.activity" /></a></li>
	
	<security:authorize access="hasRole('ADMIN')">
	
		

        <li><a class="fNiv" href="association/administrator/list.do"><spring:message code="master.page.association.list" /></a></li>
				<li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><spring:message	code="master.page.administrator" /><span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li><a href="systemConfiguration/administrator/edit.do"><spring:message code="master.page.system" /></a></li>
					<li><a href="dashboard/admin/dashboard.do"><spring:message code="master.page.dashboard" /></a></li>
              </ul>
            </li>
		</security:authorize>

        <security:authorize access="hasRole('USER')">
        	
        	<li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><spring:message code="master.page.association" /> <span class="caret"></span></a>
              <ul class="dropdown-menu">
               
			<li><a href="association/list.do"><spring:message code="master.page.association.list" /></a></li>
			<li><a href="association/user/listOwn.do"><spring:message code="master.page.association.list.own" /></a></li>
			 <li><a href="loan/user/listOwn.do"><spring:message code="master.page.loan.own"/></a></li>
			<li><a href="sanction/myActiveSanctions.do"><spring:message code="master.page.sanction.active.list.own" /></a></li>
              </ul>
        	
			 <li>
				<li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><spring:message code="master.page.profile" /> <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li><a href="actor/actor/displayOwn.do"><spring:message code="master.page.user.display" /></a></li>
                <li><a href="user/user/edit.do"><spring:message code="master.page.user.edit" /></a></li>
              </ul>
              
        		
            </li>
           </security:authorize>
      
      
        <security:authorize access="isAnonymous()">
        	<li><a class="fNiv" href="association/list.do"><spring:message
              code="master.page.association" /></a></li>
             <li><a class="fNiv" href="user/register.do"><spring:message code="master.page.register.as.user" /></a></li>
             <li class="Fniv"><a href="security/login.do" ><b><spring:message code="master.page.login"/></b> </a></li>
			</security:authorize>
        
        <security:authorize access="isAuthenticated()">
        		
              <li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
        </security:authorize>
      </ul>
    </div>
  </div>
</nav> 

