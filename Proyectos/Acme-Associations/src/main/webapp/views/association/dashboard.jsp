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
            <h2><spring:message code="association.dashboard.head"/> <jstl:out value="${association.name}" /></h2>
          </div>
          <div class="row">
            <div class="col-6 col-md-6 col-lg-4">
            	<fieldset>
					<legend><spring:message code="association.user.most.sanctions" /></legend>
					<b><a href="actor/actor/${mostSanctioned.id}/display.do"> <jstl:out value="${mostSanctioned.name }"/> <jstl:out value="${mostSanctioned.surname}"/></a></b>
					<br><b><spring:message code="association.sanctions"/>: </b> <jstl:out value="${mostSanctionedSanctions}"/>
				</fieldset>	
				<br>
				<fieldset>
					<legend><spring:message code="association.section.most.loans" /></legend>
					<b><jstl:out value="${mostLoans.name}"/></b>
					<br><b><spring:message code="association.loans"/>: </b> <jstl:out value="${mostLoansNumber}"/>
				</fieldset>	
            </div><!--/span-->
            <div class="col-6 col-md-6 col-lg-4">
            <fieldset>
					<legend><spring:message code="association.user.most.loans" /></legend>
					<b><a href="actor/actor/${mostLoansUser.id}/display.do"> <jstl:out value="${mostLoansUser.name }"/> <jstl:out value="${mostLoansUser.surname}"/></a></b>
					<br><b><spring:message code="association.loans"/>: </b> <jstl:out value="${mostLoansUserNumber}"/>
				</fieldset>	
				<br>
				<fieldset>
					<legend><spring:message code="association.user.least.loans" /></legend>
					<b><a href="actor/actor/${leastLoansUser.id}/display.do"> <jstl:out value="${leastLoansUser.name }"/> <jstl:out value="${leastLoansUser.surname}"/></a></b>
					<br><b><spring:message code="association.loans"/>: </b> <jstl:out value="${leastLoansUserNumber}"/>
				</fieldset>	
            </div><!--/span-->
            <div class="col-6 col-md-6 col-lg-4">
            <fieldset>
					<legend><spring:message code="association.item.most.loans" /></legend>
					<b><a href="item/user/${association.id}/display.do?itemId=${item.id}"> <jstl:out value="${item.name }"/></a></b>
					<br><b><spring:message code="association.loans"/>: </b> <jstl:out value="${itemLoans}"/>
				</fieldset>	
				<br>
				<fieldset>
					<legend><spring:message code="association.activity.most.attendants" /></legend>
					<b><a href="activity/${association.id}/${mostAttendants.id }/display.do"> <jstl:out value="${mostAttendants.name}"/></a></b>
					<br><b><spring:message code="association.attendants"/>: </b> <jstl:out value="${attendants}"/>
				</fieldset>	
            </div><!--/span-->
          </div><!--/row-->
        </div><!--/span-->
    

	<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
		<a class="btn btn-primary" href="association/${association.id}/display.do">&larr; <jstl:out value="${association.name}"/></a>
      </div>
      </div>
</div>

