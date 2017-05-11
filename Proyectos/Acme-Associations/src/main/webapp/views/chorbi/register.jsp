<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="${requestURI}" modelAttribute="registerChorbi">

	<acme:textbox code="chorbi.useraccount.username" path="username"/>
	
    <acme:password code="chorbi.useraccount.password" path="password"/>
    
	<acme:textbox code="chorbi.name" path="name"/>
	
	<acme:textbox code="chorbi.surname" path="surname"/>
	
	<acme:textbox code="chorbi.email" path="email"/>
	
	<acme:textbox code="chorbi.phoneNumber" path="phoneNumber"/>
	
	<acme:textbox code="chorbi.picture" path="picture"/>
	
	<acme:textbox code="chorbi.description" path="description"/>
	
	<form:select path="relationshipType">
		<option value="ACTIVITIES" <jstl:if test="${chorbi.relationshipType == 'ACTIVITIES'}">selected = "selected"</jstl:if>>
			<spring:message code="chorbi.activities" />
		</option>
		<option value="FRIENDSHIP" <jstl:if test="${chorbi.relationshipType == 'FRIENDSHIP'}">selected = "selected"</jstl:if>>
			<spring:message code="chorbi.friendship" />
		</option>
		<option value="LOVE" <jstl:if test="${chorbi.relationshipType == 'LOVE'}">selected = "selected"</jstl:if>>
			<spring:message code="chorbi.love" />
		</option>
	</form:select>
	
	<div>
	<form:label path="birthDate">
			<spring:message code="chorbi.birthDate" />:
		</form:label>
		<form:input placeholder="dd/MM/yyyy" path="birthDate" />
		<form:errors cssClass="error" path="birthDate" />
	</div>
	<form:select path="genre">
		<option value="MAN" <jstl:if test="${chorbi.genre == 'MAN'}">selected = "selected"</jstl:if>>
			<spring:message code="chorbi.man" />
		</option>
		<option value="WOMAN" <jstl:if test="${chorbi.genre == 'WOMAN'}">selected = "selected"</jstl:if>>
			<spring:message code="chorbi.woman" />
		</option>
	</form:select>
	
	<acme:textbox code="chorbi.country" path="country"/>
	
	<acme:textbox code="chorbi.state" path="state"/>
	
	<acme:textbox code="chorbi.province" path="province"/>
	
	<acme:textbox code="chorbi.city" path="city"/>
	
	<form:label path="accept" >
		<spring:message code="chorbi.terms" />
	</form:label>
	<form:checkbox path="accept" id="terms" onchange="javascript: toggleSubmit()"/>
	<form:errors path="accept" cssClass="error" />
	
	<br/>
	
	<button type="submit" name="save" class="btn btn-primary" id="save" disabled onload="javascript: toggleSubmit()">
		<spring:message code="chorbi.save" />
	</button>

		
	
	<acme:cancel url="index.do" code="chorbi.cancel"/>
	
	
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