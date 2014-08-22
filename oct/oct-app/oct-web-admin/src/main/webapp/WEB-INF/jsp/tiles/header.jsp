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
		<c:choose>
			<c:when test="${oct_path == '/index.do'}">
				<c:set var="itemClassIndex" value="itemActive" />
				<c:set var="itemClassPrefs" value="" />
				<c:set var="itemClassExport" value="" />
				<c:set var="itemClassStatus" value="" />								
				<c:set var="itemClassSettings" value="" />
			</c:when>
			<c:when test="${oct_path == '/systemprefs.do'}">
				<c:set var="itemClassIndex" value="" />
				<c:set var="itemClassPrefs" value="itemActive" />
				<c:set var="itemClassExport" value="" />
				<c:set var="itemClassStatus" value="" />
				<c:set var="itemClassSettings" value="" />								
			</c:when>
			<c:when test="${oct_path == '/export.do'}">
				<c:set var="itemClassIndex" value="" />
				<c:set var="itemClassPrefs" value="" />
				<c:set var="itemClassExport" value="itemActive" />
				<c:set var="itemClassStatus" value="" />
				<c:set var="itemClassSettings" value="" />								
			</c:when>
			<c:when test="${oct_path == '/systemstatus.do'}">
				<c:set var="itemClassIndex" value="" />
				<c:set var="itemClassPrefs" value="" />
				<c:set var="itemClassExport" value="" />
				<c:set var="itemClassStatus" value="itemActive" />
				<c:set var="itemClassSettings" value="" />								
			</c:when>
			<c:when test="${oct_path == '/settings.do'}">
				<c:set var="itemClassIndex" value="" />
				<c:set var="itemClassPrefs" value="" />
				<c:set var="itemClassExport" value="" />
				<c:set var="itemClassSettings" value="itemActive" />
				<c:set var="itemClassStatus" value="" />								
			</c:when>			
		</c:choose>
		<div id="menu-container">
			<ul id="menu">
				<li class="first home <c:out value="${itemClassIndex}"/>"><a href="<c:url value="./index.do"/>"><spring:message code="oct.menu.home" /></a></li>
				<li class="setup <c:out value="${itemClassPrefs}"/>"><a href="<c:url value="./systemprefs.do"/>"><spring:message code="oct.menu.setup" /></a></li>
				<li class="export <c:out value="${itemClassExport}"/>"><a href="<c:url value="./export.do"/>"><spring:message code="oct.menu.monitor.export" /></a></li>
				<li class="status <c:out value="${itemClassStatus}"/>"><a href="<c:url value="./systemstatus.do"/>"><spring:message code="oct.menu.system.status" /></a></li>
				<li class="settings <c:out value="${itemClassSettings}"/>"><a href="<c:url value="./settings.do"/>"><spring:message code="oct.menu.settings" /></a></li>
				<li class="manual "><a href="<c:url value="./manual.do"/>"  target="_blank"><spring:message code="oct.menu.manual" /></a></li>
				<li class="last logout"><a href="./j_spring_security_logout"><spring:message code="oct.menu.logout"/></a></li>
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
	
	
