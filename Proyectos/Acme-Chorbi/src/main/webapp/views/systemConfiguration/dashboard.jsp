


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>




<h3><spring:message code="systemConfiguration.countCity"/></h3> 

<table>
<thead>
	<tr>
		<th><spring:message code="systemConfiguration.number"/></th>
		<th><spring:message code="systemConfiguration.city"/></th>
	</tr>
</thead>
<tbody>
<jstl:forEach items="${cities}" var="item">
	<tr>
		<jstl:forEach items="${item}" var="out" varStatus= "i">
			<td><jstl:out value="${out}" /></td>
		</jstl:forEach>
	</tr>
</jstl:forEach>
</tbody>
</table>
<br/>


	
		
			
	


<h3><spring:message code="systemConfiguration.countCountry"/></h3>


<table>
<thead>
	<tr>
		<th><spring:message code="systemConfiguration.number"/></th>
		<th><spring:message code="systemConfiguration.country"/></th>
	</tr>
</thead>
<tbody>
<jstl:forEach items="${countries}" var="item">
	<tr>
		<jstl:forEach items="${item}" var="out" varStatus= "i">
			<td><jstl:out value="${out}" /></td>
		</jstl:forEach>
	</tr>
</jstl:forEach>
</tbody>
</table>
<br/>

<table>
<thead>
	<tr>
		<th colspan = "3"><spring:message	code="systemConfiguration.age.stats" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.age.min" /></th>
		<th><spring:message	code="systemConfiguration.age.avg" /></th>
		<th><spring:message	code="systemConfiguration.age.max" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${minAgePerActor}</td>
		<td>${avgAgePerActor}</td>
		<td>${maxAgePerActor}</td>
	</tr>
</tbody>
</table>
<br/>


<table>
<thead>
	<tr>
		<th colspan = "1"><spring:message	code="systemConfiguration.ratio.creditCard" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.ratio.creditCard" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${ratioCreditCard}</td>
	</tr>
</tbody>
</table>
<br/>


<table>
<thead>
	<tr>
		<th colspan = "3"><spring:message	code="systemConfiguration.ratio.relationshipType" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.ratio.love" /></th>
		<th><spring:message	code="systemConfiguration.ratio.friendship" /></th>
		<th><spring:message	code="systemConfiguration.ratio.activities" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${ratioLove}</td>
		<td>${ratioFriendship}</td>
		<td>${ratioActivities}</td>
	</tr>
</tbody>
</table>
<br/>


<spring:message	code="systemConfiguration.actor.order.likes" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="chorbiesOrderLikes" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phoneNumber" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>


<table>
<thead>
	<tr>
		<th colspan = "3"><spring:message	code="systemConfiguration.likes.stats" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.likes.min" /></th>
		<th><spring:message	code="systemConfiguration.likes.avg" /></th>
		<th><spring:message	code="systemConfiguration.likes.max" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${minLikesPerActor}</td>
		<td>${avgLikesPerActor}</td>
		<td>${maxLikesPerActor}</td>
	</tr>
</tbody>
</table>
<br/>



<table>
<thead>
	<tr>
		<th colspan = "3"><spring:message	code="systemConfiguration.messages.sent.stats" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.messages.sent.min" /></th>
		<th><spring:message	code="systemConfiguration.messages.sent.avg" /></th>
		<th><spring:message	code="systemConfiguration.messages.sent.max" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${minSentMessagesPerActor}</td>
		<td>${avgSentMessagesPerActor}</td>
		<td>${maxSentMessagesPerActor}</td>
	</tr>
</tbody>
</table>
<br/>

<table>
<thead>
	<tr>
		<th colspan = "3"><spring:message	code="systemConfiguration.messages.received.stats" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.messages.received.min" /></th>
		<th><spring:message	code="systemConfiguration.messages.received.avg" /></th>
		<th><spring:message	code="systemConfiguration.messages.received.max" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${minReceivedMessagesPerActor}</td>
		<td>${avgReceivedMessagesPerActor}</td>
		<td>${maxReceivedMessagesPerActor}</td>
	</tr>
</tbody>
</table>
<br/>



<spring:message	code="systemConfiguration.actor.more.sent.messages" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="actorWithMoreSentMessages" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phoneNumber" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>

<spring:message	code="systemConfiguration.actor.more.received.messages" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="actorWithMoreReceivedMessages" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phoneNumber" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>


<spring:message	code="systemConfiguration.manager.order.events" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="managerWithMoreEvents" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phoneNumber" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>


<spring:message	code="systemConfiguration.manager.with.fee" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="managerWithFee" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phoneNumber" title="${phoneHeader}" sortable="true"/>
	
	<spring:message code="systemConfiguration.actor.fee" var="feeHeader" />
	<display:column property="cumulatedFee" title="${feeHeader}" sortable="true"/>
	
</display:table>
<br/>

<spring:message	code="systemConfiguration.chorbi.order.events" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="chorbiWithMoreEvents" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phoneNumber" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>


<spring:message	code="systemConfiguration.chorbi.with.fee" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="chorbiWithFee" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phoneNumber" title="${phoneHeader}" sortable="true"/>
	
	<spring:message code="systemConfiguration.actor.fee" var="feeHeader" />
	<display:column property="cumulatedFee" title="${feeHeader}" sortable="true"/>
	
</display:table>
<br/>


<table>
<thead>
	<tr>
		<th colspan = "3"><spring:message	code="systemConfiguration.stars.stats" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.stars.min" /></th>
		<th><spring:message	code="systemConfiguration.stars.avg" /></th>
		<th><spring:message	code="systemConfiguration.stars.max" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${minStarsPerActor}</td>
		<td>${avgStarsPerActor}</td>
		<td>${maxStarsPerActor}</td>
	</tr>
</tbody>
</table>
<br/>


<spring:message	code="systemConfiguration.chorbi.order.stars" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="chorbiWithMoreStars" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phoneNumber" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>