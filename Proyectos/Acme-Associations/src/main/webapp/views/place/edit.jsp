<%--
 * edit.jsp
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
<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="place">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="longitude" value="" id="longitude" />
	<form:hidden path="latitude" value="" id="latitude" />
	<form:hidden path="address" value="" id="address" />

	<%-- <acme:textbox code="place.address" path="address"/><br />
	<acme:number max="180" min="-180" step="any" code="place.longitude" path="longitude"/><br />
	<acme:number max="85" min="-85" step="any" code="place.latitude" path="latitude"/><br /> --%>
	
	
	
	<input type="submit" name="save"
		value="<spring:message code="place.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="place.cancel" />"
		onclick="location.href = ('${cancelURI}');" />
	<br>
	


</form:form>


<div>
       <spring:message code="place.address"/> <input type="text" id="address-input">
       <button onclick="searchAddress();"><spring:message code="address.find"/></button>
    </div>
    <jstl:if test="${errorMessage2 != null}">
			<br />
			<span class="message"><spring:message code="${errorMessage2}" /></span> <br>   
		</jstl:if>	
	
		<style>
	       #map{
			  height: 400px;
			  width: 100%;
			}
	    </style>
	
		<div id="map"></div><br>
	    <script>
	    
	    var map;
	    var marker;

	    function initialize() {
	      var mapOptions = {
	        center: new google.maps.LatLng(40.680898,-8.684059),
	        zoom: 6,
	        mapTypeId: google.maps.MapTypeId.ROADMAP
	      };
	      map = new google.maps.Map(document.getElementById("map"), mapOptions);
	    }
	    google.maps.event.addDomListener(window, "load", initialize);
	    
	    function searchAddress() {

	    	  var addressInput = document.getElementById('address-input').value;

	    	  var geocoder = new google.maps.Geocoder();

	    	  geocoder.geocode({address: addressInput}, function(results, status) {

	    	    if (status == google.maps.GeocoderStatus.OK) {

	    	      var myResult = results[0].geometry.location; // reference LatLng value
	    	      
	    	      var lati = myResult.lat();
	    	      var longi = myResult.lng();

	    	      createMarker(myResult); // call the function that adds the marker

	    	      map.setCenter(myResult);

	    	      map.setZoom(9);
	    	      document.getElementById('latitude').value = lati;
	    	      document.getElementById('longitude').value = longi;
	    	      document.getElementById('address').value = document.getElementById('address-input').value;

	    	    }else { // if status value is not equal to "google.maps.GeocoderStatus.OK"

	    	        // warning message
	    	        alert("The Geocode was not successful for the following reason: " + status);

	    	      }
	    	  });
	    	}

	    	function createMarker(latlng) {

	    	   // If the user makes another search you must clear the marker variable
	    	   if(marker != undefined && marker != ''){
	    	    marker.setMap(null);
	    	    marker = '';
	    	   }

	    	   marker = new google.maps.Marker({
	    	      map: map,
	    	      position: latlng
	    	   });

	    	}
	    	
	    	
	    
	    
	    </script>
	    <script async defer
	    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC0HVq8CTYmEOaRdZ16y15RQBmo9u3A3EA&callback=initMap">
	    </script>
	    
	    