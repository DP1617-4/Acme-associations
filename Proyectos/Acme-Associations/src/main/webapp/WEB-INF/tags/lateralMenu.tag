<%--
 * lateralMenu.tag
 --%>	
 
 <%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 
 

<%-- Definition --%>
		
         <div class="list-group">
         	
         	<a href="activity/${association.id}/list.do" class="list-group-item"><spring:message code="association.activity"/></a>
         	<a href="section/${association.id}/list.do" class="list-group-item"><spring:message code="association.section"/></a>
         	<security:authorize access="isAuthenticated()"> 
         		<a href="association/user/${association.id}/listUsers.do" class="list-group-item"><spring:message code="association.user.list"/></a>
         	</security:authorize>
         	<security:authorize access="hasRole('USER')"> 
         		<a href="sanction/user/${association.id}/mySanctions.do" class="list-group-item"><spring:message code="association.mySanctions"/></a>
         	</security:authorize>
         	<hr style="margin-top: 10px; margin-bottom: 10px;">
           <jstl:if test="${role eq 'MANAGER' || role eq 'COLLABORATOR'}">
           <a href="sanction/user/${association.id}/list.do" class="list-group-item"><spring:message code="association.sanction.association"/></a>
           <a href="loan/user/${association.id}/listPending.do" class="list-group-item"><spring:message code="association.loanPending"/></a>
           <a href="meeting/user/${association.id}/list.do" class="list-group-item"><spring:message code="association.meeting"/></a>
             <hr style="margin-top: 10px; margin-bottom: 10px;">
           </jstl:if>
           <jstl:if test="${role eq 'MANAGER'}">
           <a href="request/user/${association.id}/list.do" class="list-group-item"><spring:message code="association.request.list"/></a>
           <a href="association/user/${association.id}/changeManager.do" class="list-group-item"><spring:message code="association.manager.change"/></a>
           <a href="association/user/${association.id}/dashboard.do" class="list-group-item"><spring:message code="association.dashboard"/></a>
             <hr style="margin-top: 10px; margin-bottom: 10px;">
           </jstl:if>
           <jstl:if test="${role eq 'ASSOCIATE' || role eq 'COLLABORATOR'}">
           <a href="association/user/${association.id}/leave.do" class="list-group-item"><spring:message code="association.leave"/></a>
           </jstl:if>
         </div>
         
         
         
         
        <%--    <%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<div class="row row-offcanvas row-offcanvas-right">

  	<div class="col-12 col-md-9">

	<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
	   <acme:lateralMenu/>


<a class="btn btn-primary" href="association/${association.id}/display.do">&larr; <jstl:out value="${association.name}"/></a>
          --%>