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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div class="row row-offcanvas row-offcanvas-right">
	<div class="col-12 col-md-9">
		<display:table pagesize="5" class="displaytag" keepStatus="false"
			name="loans" requestURI="${requestURI}" id="row" >
			
			<spring:message code="loan.item" var="itemHeader"/>
			<display:column title="${itemHeader}">
				<a href="item/user/${row.item.section.association.id }/display.do?itemId=${row.item.id}">${mask:mask(row.item.name) }</a>
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
					<a href="loan/user/${association.id}/${row.id}/end.do"><spring:message code="loan.end" /></a>
				</display:column>
				
				<spring:message code="loan.sanction" var="sanctionHeader"/>
				<display:column title="${sanctionHeader}">
					<a href="sanction/user/${row.item.section.association.id}/create.do?userId=${row.borrower.id}"><spring:message code="loan.sanction.user" /></a>
				</display:column>
			</jstl:if>
			
		</display:table>
	</div>

	<jstl:if test="${not empty association}">
		<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
	          <a class="btn btn-primary" href="association/${association.id}/display.do">&larr; <jstl:out value="${association.name}"/></a>
	   <br><br><acme:lateralMenu/>
	    </div><!--/span-->
     </jstl:if>

</div>
