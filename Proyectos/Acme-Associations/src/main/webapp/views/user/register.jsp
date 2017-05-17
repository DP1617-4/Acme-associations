<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="${requestURI}" modelAttribute="registerUser">

	<acme:textbox code="user.useraccount.username" path="username"/>
	
    <acme:password code="user.useraccount.password" path="password"/>
    
	<acme:textbox code="user.name" path="name"/>
	
	<acme:textbox code="user.surname" path="surname"/>
	
	<acme:textbox code="user.email" path="email"/>
	
	<acme:textbox code="user.phoneNumber" path="phoneNumber"/>
	
	<acme:textbox code="user.postalAddress" path="postalAddress"/>
	
	<acme:textbox code="user.idNumber" path="idNumber"/>
		
	<form:label path="accept" >
		<spring:message code="user.terms" />
	</form:label>
	<form:checkbox path="accept" id="terms" onchange="javascript: toggleSubmit()"/>
	<form:errors path="accept" cssClass="error" />
	
	<br/>
	
	<button type="submit" name="save" class="btn btn-primary" id="save" disabled onload="javascript: toggleSubmit()">
		<spring:message code="user.save" />
	</button>
	
	<acme:cancel url="index.do" code="user.cancel"/>
	
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