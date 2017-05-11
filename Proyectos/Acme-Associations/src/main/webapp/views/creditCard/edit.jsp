<%--
 * action-2.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


	 
	<jstl:choose>
		<jstl:when test="${creditCard.id==0}">
		
		<form:form action="${requestURI}" modelAttribute="creditCard">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="holder" />
   	 	<acme:textbox code="creditCard.holder.name" path="holderName"/>
    
		<div>
			<form:label path="brandName">
				<spring:message code="creditCard.brand.name" />
			</form:label>	
			<form:select path="brandName">		
				<form:option value="VISA" label="Visa" />
				<form:option value="MASTERCARD" label="MasterCard" />
				<form:option value="DISCOVER" label="Discover" />
				<form:option value="DINNERS" label="Dinners" />
				<form:option value="AMEX" label="Amex" />
			</form:select>
			<form:errors path="brandName" cssClass="error" />
		</div>
		
		
		<acme:textbox code="creditCard.number" path="creditCardNumber"/>
	
		<acme:textbox code="creditCard.expiration.month" path="expirationMonth"/>
	
		<acme:textbox code="creditCard.expiration.year" path="expirationYear"/>
	
		<acme:textbox code="creditCard.CVV" path="CVV"/>
		
		<button type="submit" name="save" class="btn btn-primary" id="save">
			<spring:message code="creditCard.save" />
		</button>
		
		</form:form>		
		</jstl:when>
		<jstl:otherwise>
			<h2><spring:message code="creditCard" /></h2>
			<p><spring:message code="creditCard.holder.name"/>: <jstl:out value="${creditCard.holderName}" /></p> 
			<p><spring:message code="creditCard.brand.name"/>: <jstl:out value="${creditCard.brandName}" /></p>
			<p><spring:message code="creditCard.number"/>: <jstl:out value="${creditCard.creditCardNumber}" /></p>
			<p><spring:message code="creditCard.expiration.month"/>: <jstl:out value="${creditCard.expirationMonth}" /></p>
			<p><spring:message code="creditCard.expiration.year"/>: <jstl:out value="${creditCard.expirationYear}" /></p>
			<p><spring:message code="creditCard.CVV"/>: <jstl:out value="${creditCard.CVV}" /></p>
		
			<security:authorize access="hasRole('CHORBI')">
				<input type="button" name="delete"
					value="<spring:message code="creditCard.delete" />"
					onclick="location.href = ('creditCard/chorbi/delete.do');" />
			</security:authorize>
			<security:authorize access="hasRole('MANAGER')">
				<input type="button" name="delete"
					value="<spring:message code="creditCard.delete" />"
					onclick="location.href = ('creditCard/manager/delete.do');" />
			</security:authorize>
			
		</jstl:otherwise>
	</jstl:choose>
	<br/>
	
	<input type="button" name="back"
		value="<spring:message code="creditCard.back" />"
			onclick="location.href = ('welcome/index.do');" />
	
	
