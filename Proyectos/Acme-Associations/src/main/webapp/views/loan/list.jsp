<%--
 * list.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="/WEB-INF/tags/functions" prefix="mask" %>

<div class="row row-offcanvas row-offcanvas-right">
	<div class="col-12 col-md-9">
		<display:table pagesize="5" class="displaytag" keepStatus="true"
			name="loans" requestURI="${requestURI}" id="row" >
			
			<spring:message code="loan.item" var="itemHeader"/>
			<display:column title="${itemHeader}">
				<a href="item/actor/${row.item.id}/display.do">${mask:mask(row.item.name) }</a>
			</display:column>
			
			<spring:message code="loan.startDate" var="startHeader" />	
			<display:column property="startDate" title="${startHeader}" sortable="true" format="{0,date,dd/MM/yyyy}" />
			
			<spring:message code="loan.expectedDate" var="expectedDate" />	
			<display:column property="expectedDate" title="${expectedDate}" sortable="true" format="{0,date,dd/MM/yyyy}" />
			
			<spring:message code="loan.finalDate" var="finalDate" />	
			<display:column property="finalDate" title="${finalDate}" sortable="true" format="{0,date,dd/MM/yyyy}" />
		
			<spring:message code="loan.borrower" var="borrowerHeader"/>
			<display:column title="${borrowerHeader}">
				<a href="actor/actor/${row.borrower.id}/display.do">${mask:mask(row.borrower.completeName) }</a>
			</display:column>
			
			<spring:message code="loan.lender" var="lenderHeader"/>
			<display:column title="${lenderHeader}">
				<a href="actor/actor/${row.lender.id}/display.do">${mask:mask(row.lender.completeName) }</a>
			</display:column>
			
			<jstl:if test="${not empty role && (role eq 'COLLABORATOR' || role eq 'MANAGER')}">
				<display:column>
					<a href="loan/user/${row.id}/end.do"><spring:message code="loan.end" /></a>
				</display:column>
				
				<spring:message code="loan.sanction" var="sanctionHeader"/>
				<display:column title="${sanctionHeader}">
					<a href="user/sanction/${row.borrower.id}/create.do"><spring:message code="loan.sanction.user" /></a>
				</display:column>
			</jstl:if>
			
		</display:table>
	</div>
	<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
          <div class="list-group">
            <a href="section/user/${association.id}/list.do" class="list-group-item active"><spring:message code="association.section"/></a>
            <a href="item/user/${association.id}/list.do" class="list-group-item"><spring:message code="association.item"/></a>
            <jstl:if test="${role eq 'MANAGER' || role eq 'COLLABORATOR'}">
            <a href="sanction/user/${association.id}/list.do" class="list-group-item"><spring:message code="association.sanction"/></a>
            <a href="loan/user/${association.id}/listPending.do" class="list-group-item"><spring:message code="association.loan"/></a>
            </jstl:if>
            <jstl:if test="${role eq 'MANAGER'}">
            <a href="user/request/${association.id}/list.do" class="list-group-item"><spring:message code="association.request.list"/></a>
            </jstl:if>
            <a href="activity/user/${association.id}/list.do" class="list-group-item"><spring:message code="association.activity"/></a>
          </div>
        </div><!--/span-->
</div>
