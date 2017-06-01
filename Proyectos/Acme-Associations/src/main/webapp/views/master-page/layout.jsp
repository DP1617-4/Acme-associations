<%--
 * layout.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>



<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="shortcut icon" href="favicon.ico"/> 

<script type="text/javascript" src="scripts/jquery1.12.4.min.js"></script>
<script type="text/javascript" src="scripts/jquery-ui.js"></script>
<script type="text/javascript" src="scripts/jmenu.js"></script>

<link rel="stylesheet" href="styles/common.css" type="text/css">
<link rel="stylesheet" href="styles/jmenu.css" media="screen" type="text/css" />
<link rel="stylesheet" href="styles/displaytag.css" type="text/css">

<!-- Trasteado por Andres -->

<script type="text/javascript" src="scripts/datepick-js/jquery.plugin.js"></script>
<script type="text/javascript" src="scripts/datepick-js/jquery.datepick.js"></script>
<jstl:if test="${pageContext.response.locale.language == 'es'}">
	<script type="text/javascript" src="scripts/datepick-js/jquery.datepick-es.js"></script>
</jstl:if>
<jstl:if test="${pageContext.response.locale.language == 'en'}">
	<script type="text/javascript" src="scripts/datepick-js/jquery.datepick-en-GB.js"></script>
</jstl:if>
<link rel="stylesheet" href="styles/datepick-css/jquery.datepick.css" type="text/css">
<link rel="stylesheet" href="styles/datepick-css/ui.datepick.css" type="text/css">

<!-- hasta aqui -->

	 
	<script type="text/javascript" src="js/bootstrap.js"></script>
	<!-- 
	<script type="text/javascript" src="js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="js/locales/bootstrap-datepicker.en-GB.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="js/locales/bootstrap-datepicker.es.min.js" charset="UTF-8"></script> -->
	<script type="text/javascript" src="js/tether.js"></script> 
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="css/bootstrap.css" rel="stylesheet" type="text/css">
   <!--  <link href="css/bootstrap-grid.min.css" rel="stylesheet" type="text/css">
    <link href="css/bootstrap-reboot.css" rel="stylesheet" type="text/css">
    <link href="css/bootstrap-reboot.min.css" rel="stylesheet" type="text/css">-->
    <link href="css/bootstrap-grid.css" rel="stylesheet" type="text/css"> 
	<link href="css/tether.css" rel="stylesheet" type="text/css">
	<!--<link href="css/bootstrap-datepicker3.standalone.css" rel="stylesheet" type="text/css">
    <!-- Custom styles for this template -->

<title><tiles:insertAttribute name="title"/></title>

<script type="text/javascript">
	function askSubmission(msg, form) {
		if (confirm(msg))
			form.submit();
	}
</script>

<script type="text/javascript">
		function relativeRedir(loc) {	
			var b = document.getElementsByTagName('base');
			if (b && b[0] && b[0].href) {
	  			if (b[0].href.substr(b[0].href.length - 1) == '/' && loc.charAt(0) == '/')
	    		loc = loc.substr(1);
	  			loc = b[0].href + loc;
			}
			window.location.replace(loc);
		}
	</script>
	<script type="text/javascript">
		function changeLocale(lang){
			$.fn.datepicker.defaults.language	= lang;
		}
	</script>
	
	<script type="text/javascript">
		function alertMessage(str) {
			alert(str);
		}
	</script>
	
	<!-- maps -->
	<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
    <script type="text/javascript" src="js/map.js"></script>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC0HVq8CTYmEOaRdZ16y15RQBmo9u3A3EA&callback=initMap">
    </script>
	
</head>
<br>
<body>


	<div class="container-nav">
		<tiles:insertAttribute name="header" />
	</div>
	<div class= "container">
		<h1>
			<tiles:insertAttribute name="title" />
		</h1>
		<tiles:insertAttribute name="body" />	
		<jstl:if test="${errorMessage != null}">
			<br />
			<span class="message"><spring:message code="${errorMessage}" /></span>
		</jstl:if>	
	</div>
	<hr>
	<div class = "container">
		<tiles:insertAttribute name="footer" />
	</div>


<jstl:if test="${not empty flashMessage}">
	<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title"><spring:message code="warning"/></h4>
      </div>
      <div class="modal-body">
        <p><spring:message code="${flashMessage}"/></p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="close"/> </button>
      </div>
    </div>

  </div>
</div>
<script>
$(document).ready(function(){
	$("#myModal").modal();
});
</script>
</jstl:if>

</body>
</html>