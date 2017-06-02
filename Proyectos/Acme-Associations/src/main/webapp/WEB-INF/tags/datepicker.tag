<%--
 * textbox.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
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
 
<%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>
<%@ attribute name="hour" required="false" %>

<%@ attribute name="readonly" required="false" %>

<jstl:if test="${readonly == null}">
	<jstl:set var="readonly" value="false" />
</jstl:if>
<jstl:if test="${hour == null}">
	<jstl:set var="hour" value="false" />
</jstl:if>

<%-- Definition --%>

<div class="datepicker">
	<form:label path="${path}">
		<spring:message code="${code}" />
	</form:label>	
	<input id="${path}" class="form-control" type="text" name="${path}" readonly="${readonly}"/>
	<jstl:if test="${hour}">
		<form:label path="${path}">
			<spring:message code="layout.hour" />
		</form:label>
		<input id="${path}hour" class="form-control" type="text" name="${path}hour" onKeyUp="addHour(this)"/>
	</jstl:if>
	<form:errors path="${path}" cssClass="error" />
</div>	

<script>
	$(document).ready(function generarDatePicker(){
		var $j = jQuery.noConflict();
		var elem = document.getElementById("${path}")
		$(elem).datepick({
			dateFormat: 'dd/mm/yyyy',
			altField: $(elem),
			changeYear: true,
			changeMonth: true
		}).keyup(function(e) {
		    if(e.keyCode == 8 || e.keyCode == 46) {
		        $.datepicker._clearDate(this);
		    }
		});
	})
</script>
<jstl:if test="${hour}">
	<script>
		function addHour(hour){
			var elem = document.getElementById("${path}")
			elem.value = elem.value.split(" ")[0] +" "+ hour.value			
		}
	</script>
</jstl:if>