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
          	<img src="<jstl:out value="${item.picture }"/>" align="right"  height="150" >
            <h1><jstl:out value="${item.name}" /></h1>
            <p><jstl:out value="${item.identifier}" /></p>
          </div>
          <div><jstl:out value="${item.description}" /></div>
          <div><b><spring:message code="item.condition"/>: </b>
          <jstl:out value="${item.itemCondition}" /></div>
          <jstl:if test="${item.itemCondition != 'LOAN' || item.itemCondition != 'PRIZE' || item.itemCondition != 'BAD' }">
          <jstl:if test="${role == 'MANAGER' || role == 'COLLABORATOR' }">
	          </br>
	           <a class="btn btn-primary" href="loan/user/${association.id}/${item.id }/create.do"><spring:message code="item.loan"/></a>
	           </br>
	       </jstl:if>
           </jstl:if>
          
          <jstl:if test="${isCharge == true }">
          <div>
          	<form:form action="item/user/${association.id}/${item.id}/changeCondition.do" modelAttribute="changeCondition">

				<form:hidden path="item" />
			
				<form:label path="condition">
					<spring:message code="item.condition.change" />:
				</form:label>
				<form:select id="condition" path="condition">
					<form:option value="EXCELENT" label="EXCELENT" />
					<form:option value="GOOD" label="GOOD" />
					<form:option value="MODERATE" label="MODERATE" />
					<form:option value="BAD" label="BAD" />
				</form:select>
			<form:errors cssClass="error" path="condition" />
			</br>
				
				<acme:submit name="save" code="item.change"/>&nbsp; 
				<br />
			
				
			
			</form:form>
          </div>
          </jstl:if>
           
     </div>
    </div>
</div>