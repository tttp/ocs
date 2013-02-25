<%@page pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<div id="header">
		<div id="header-init-title">
			<c:out value="${oct_initiative_description.title}" />
			<div id="header-title">
				<spring:message code="oct.title" />
			</div>
			
			<div class="lang">
				<select id="langChange">
					<c:forEach items="${languages}" var="language">
						
						<c:if test="${language.code == currentLanguage}">
							<c:set var="isSelected" value="selected" />
						</c:if>
						<c:if test="${language.code != currentLanguage}">
							<c:set var="isSelected" value="" />
						</c:if>
						
						<option value="<c:out value="${language.code}"/>" <c:out value="${isSelected}"/>><spring:message code="${language.name}"/> <c:out value="(${language.code}"/>)</option>
					</c:forEach>
				</select>
			</div>
			
		</div>
		<ul id="menu">
			<li class="first home"><a href="./"><spring:message code="oct.menu.home" /></a></li>
			<li class="conformity">
				<c:choose>
					<c:when test="${oct_cert != null}">
						<a href="./certificate.do" target="_blank"><spring:message code="oct.menu.conformity" /></a>
					</c:when>
					<c:otherwise>
						<a href="#" onclick="javascript:alert('<spring:message code="oct.error.missingcertificate" />');"><spring:message code="oct.menu.conformity" /></a>
					</c:otherwise>
				</c:choose>
			</li>
			<li class="last privacy"><a href="./privacy.do" target="_blank"><spring:message code="oct.menu.privacy" /></a></li>
<!-- 			<li class="lang"> -->
<!-- 				<select id="langChange"> -->
<%-- 					<c:forEach items="${languages}" var="language"> --%>
						
<%-- 						<c:if test="${language.code == currentLanguage}"> --%>
<%-- 							<c:set var="isSelected" value="selected" /> --%>
<%-- 						</c:if> --%>
<%-- 						<c:if test="${language.code != currentLanguage}"> --%>
<%-- 							<c:set var="isSelected" value="" /> --%>
<%-- 						</c:if> --%>
						
<%-- 						<option value="<c:out value="${language.code}"/>" <c:out value="${isSelected}"/>><spring:message code="${language.name}"/> <c:out value="(${language.code}"/>)</option> --%>
<%-- 					</c:forEach> --%>
<!-- 				</select> -->
<!-- 			</li> -->
		</ul>
	</div>