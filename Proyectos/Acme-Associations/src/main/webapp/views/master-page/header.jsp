<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="welcome/index.do"><img src="images/logo.jpg" height="200" alt="Acme Associations Co., Inc." /></a> <a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->	
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv" href="association/list.do"><spring:message code="master.page.association.list" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="systemConfiguration/administrator/edit.do"><spring:message code="master.page.system" /></a></li>
					<li><a href="systemConfiguration/administrator/dashboard.do"><spring:message code="master.page.dashboard" /></a></li>	
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>				
				</ul>
			</li>
			
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="association/list.do"><spring:message code="master.page.association.list" /></a></li>
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="user/register.do"><spring:message	code="master.page.register.as.user" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv" href="association/list.do"><spring:message code="master.page.association.list" /></a></li>
			<li><a class="fNiv" href="association/user/listOwn.do"><spring:message code="master.page.association.list.own" /></a></li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="user/user/display.do"><spring:message code="master.page.user.display" /></a></li>
					<li><a href="user/user/edit.do"><spring:message code="master.page.user.edit" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
			
		</security:authorize>
		
	</ul>
</div>
