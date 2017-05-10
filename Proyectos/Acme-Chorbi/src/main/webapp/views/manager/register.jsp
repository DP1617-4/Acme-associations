<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="${requestURI}" modelAttribute="registerManager">

	<acme:textbox code="manager.useraccount.username" path="username"/>
	
    <acme:password code="manager.useraccount.password" path="password"/>
    
	<acme:textbox code="manager.name" path="name"/>
	
	<acme:textbox code="manager.surname" path="surname"/>
	
	<acme:textbox code="manager.email" path="email"/>
	
	<acme:textbox code="manager.phoneNumber" path="phoneNumber"/>
	
	
	<acme:textbox code="manager.company.name" path="companyName"/>
	
	<acme:textbox code="manager.VAT.number" path="VATNumber"/>
	
	<acme:textbox code="manager.holder.name" path="holderName"/>
	
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
	
	<acme:textbox code="manager.credit.card.number" path="creditCardNumber"/>
	
	<acme:textbox code="manager.expiration.month" path="expirationMonth"/>
	
	<acme:textbox code="manager.expiration.year" path="expirationYear"/>
	
	<acme:textbox code="manager.CVV" path="CVV"/>
	
	
	
	<form:label path="accept" >
		<spring:message code="manager.terms" />
	</form:label>
	<form:checkbox path="accept" id="terms" onchange="javascript: toggleSubmit()"/>
	<form:errors path="accept" cssClass="error" />
	
	<br/>
	
	<button type="submit" name="save" class="btn btn-primary" id="save" disabled onload="javascript: toggleSubmit()">
		<spring:message code="manager.save" />
	</button>

		
	
	<acme:cancel url="index.do" code="manager.cancel"/>
	
	
	<script type="text/javascript">
		function toggleSubmit() {
			var accepted = document.getElementById("terms");
			if(accepted.checked){
				document.getElementById("save").disabled = false;
			} else{
				document.getElementById("save").disabled = true;
			}
		}
	</script>
	
</form:form>