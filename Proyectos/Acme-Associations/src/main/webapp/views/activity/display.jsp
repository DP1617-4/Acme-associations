<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="/WEB-INF/tags/functions" prefix="mask" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<body>
<security:authentication property="principal" var ="loggedactor"/>
    <div class="container">
      <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
          <div class="jumbotron">
            <h1><jstl:out value="${activity.name}" /></h1>
            <p><jstl:out value="${activity.description}" /></p>
          </div>
          <div class="col-6 col-md-6 col-lg-6">
          <div><b><spring:message code="activity.startMoment"/>: </b><jstl:out value="${activity.startMoment}" /></div>
          <div><b><spring:message code="activity.endMoment"/>: </b><jstl:out value="${activity.endMoment}" /></div>
          <div><b><spring:message code="activity.maximumAttendants"/>: </b><jstl:out value="${activity.maximumAttendants}" /></div>
          <jstl:if test="${role eq 'MANAGER'|| role eq 'COLLABORATOR'}">
				<jstl:if test="${activity.winner == null}">
						<form:form action="activity/user/addWinner.do" modelAttribute="addWinner">
						<form:hidden path="activity"/>
							<form:label path="user">
							<spring:message code="activity.winner.title"/>
							</form:label>
							<form:select id="user" path="user">
								<jstl:forEach items="${users}" var="thisUser">
									<form:option value="${thisUser.id}" label="${thisUser.completeName}" />
								</jstl:forEach>
							</form:select>
							</br>
							<acme:submit name="save" code="activity.winner.add"/>
						</form:form>
				</jstl:if>
			</jstl:if>
			<jstl:if test="${activity.winner != null}">
			<spring:message code="activity.winner"/>: <jstl:out value="${activity.winner.completeName}" />
			</jstl:if>
			
			<br>
				<style>
			       #map {
			        height: 400px;
			        width: 100%;
			       }
			       
			    </style>
			<display:table pagesize="5" keepStatus="false" name="${activity.attendants }" requestURI="${requestURI}" id="row">
				<display:column title="${nameHeader}">
					<a href="actor/actor/${row.id}/display.do"> <jstl:out value="${row.name }"/> <jstl:out value="${row.surname}"/></a>
				</display:column>
			</display:table>
         </div>
          
          <div class="col-6 col-md-6 col-lg-6">
			<h2><spring:message code="activity.place.description"/></h2>
			<jstl:if test="${activity.place==null}">
				<spring:message code="activity.noPlace"/>
				<jstl:if test="${role eq 'MANAGER' || role eq 'COLLABORATOR'}">
					<div><a class="btn btn-primary" href="place/user/${activity.id}/create.do"><spring:message code="activity.place.create"/></a></div>
				</jstl:if>
			</jstl:if>
			<jstl:if test="${activity.place!=null}">
				<div><b><spring:message code="activity.place.address"/>: </b><jstl:out value="${activity.place.address}" /></div>
				<br>
				
    <div id="map"></div>
    <script>
      function initMap() {
    	var lati = '${latitude}';
    	var longi = '${longitude}';
        var uluru = {lat: parseFloat(lati), lng: parseFloat(longi)};
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 4,
          center: uluru
        });
        var marker = new google.maps.Marker({
          position: uluru,
          map: map
        });
      }
    </script>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC0HVq8CTYmEOaRdZ16y15RQBmo9u3A3EA&callback=initMap">
    </script>
			</jstl:if>
        </div><!--/span-->
      </div><!--/row-->
      <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
   <acme:lateralMenu/>
</div>
     </div>
</div><!--/span-->