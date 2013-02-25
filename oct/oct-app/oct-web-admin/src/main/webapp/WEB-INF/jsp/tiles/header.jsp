<%@page pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<div id="header">
		<div id="title">
			<h1>
				<spring:message code="oct.header.title" />			
			</h1>
		</div>
		<div id="langPicker">
		<select id="langChange">
					<c:forEach items="${languages}" var="language">
						
						<c:choose>
							<c:when test="${language.code == currentLanguage}">
								<c:set var="isSelected" value="selected" />				
							</c:when>
							<c:otherwise>
								<c:set var="isSelected" value="" />				
							</c:otherwise>
						</c:choose>
						
						<option value="<c:out value="${language.code}" />" <c:out value="${isSelected}"/>><c:out value="${language.label} (${language.code})"/></option>
					</c:forEach>
		</select>
		</div>
	</div>
	<security:authorize ifAnyGranted="ROLE_ADMIN">
		<div id="menu-container">
			<ul id="menu">
				<li class="first home"><a href="./index.do"><spring:message code="oct.menu.home" /></a></li>
				<li class="setup"><a href="./systemprefs.do"><spring:message code="oct.menu.setup" /></a></li>
				<li class="export"><a href="./export.do"><spring:message code="oct.menu.monitor.export" /></a></li>
				<li class="status"><a href="./systemstatus.do"><spring:message code="oct.menu.system.status" /></a></li>
				<li class="last logout"><a href="<c:url value="/j_spring_security_logout"/>"><spring:message code="oct.menu.logout"/></a></li>
			</ul>
		</div>
	<div id="state">
		<div id="sysstate">
			<c:if test="${oct_system_state!='OPERATIONAL'}"><spring:message code="oct.header.mode" /></c:if>
			<c:if test="${oct_system_state=='OPERATIONAL'}"><spring:message code="oct.s11.sys.mode" /></c:if>
		</div>
		<div id="collstate">
			<c:if test="${oct_collector_state==true}"><spring:message code="oct.s11.sys.collection.status" /></c:if>
			<c:if test="${oct_collector_state==false}"><spring:message code="oct.header.collection.status" /></c:if>
		</div>
	</div>
	</security:authorize>
	<security:authorize ifNotGranted="ROLE_ADMIN">
	<div id="nostate">		
	</div>
	</security:authorize>
	
	
