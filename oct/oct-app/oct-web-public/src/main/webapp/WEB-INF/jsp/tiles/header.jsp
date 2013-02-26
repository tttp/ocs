<%@page pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<div id="header">
		<div id="header-init-title">
			<div id="logo">
				<p id="h1" lang="<c:out value="${initiativeLang}" />"><c:out value="${oct_initiative_description.title}" /></p>
				<p id="header-title">
					<spring:message code="oct.title" />
				</p>
			</div>
			
			<div id="lang">
				<label for="langChange"><spring:message code="oct.header.selectlanguage" /></label>
				<br />
				<select id="langChange">
					<c:forEach items="${languages}" var="language">
						<c:if test="${language.code == currentLanguage}">
							<c:set var="isSelected" value="selected=\"selected\"" />
						</c:if>
						<c:if test="${language.code != currentLanguage}">
							<c:set var="isSelected" value="" />
						</c:if>
						<option value="<c:out value="${language.code}"/>" ${isSelected} lang="<c:out value="${language.code}"/>"><spring:message code="${language.name}"/> <c:out value="(${language.code}"/>)</option>
					</c:forEach>
				</select>
				<noscript>
					<p id="noscript-lang">
					<c:forEach items="${languages}" var="language" varStatus="idx">
						<c:if test="${language.code == currentLanguage}">
							<c:set var="isSelected" value="selected" />
						</c:if>
						<c:if test="${language.code != currentLanguage}">
							<c:set var="isSelected" value="" />
						</c:if>
						<c:url value="" var="lUrl">
							<c:param name="lang" value="${language.code}" />
						</c:url>
						<a href="<c:out value="${lUrl}"/>" class="${isSelected}" title="<spring:message code="${language.name}"/>" lang="<c:out value="${language.code}"/>"><c:out value="${language.code}"/></a>
					</c:forEach>
					</p>
				</noscript>
			</div>
		</div>
		
		<div class="clear"></div>
		
		<ul id="menu">
			<li class="first home">
				<a href="<c:url value="./" />" accesskey="h"><spring:message code="oct.menu.home" /></a>
			</li>
			<li class="conformity">
				<c:choose>
					<c:when test="${oct_cert != null}">
						<a href="<c:url value="./certificate.do" />" accesskey="c"><spring:message code="oct.menu.conformity" /></a>
					</c:when>
					<c:otherwise>
						<a href="#" onclick="javascript:alert('<spring:message code="oct.error.missingcertificate" />');"><spring:message code="oct.menu.conformity" /></a>
					</c:otherwise>
				</c:choose>
			</li>
			<li class="last privacy">
				<a href="<c:url value="./privacy.do" />" accesskey="p"><spring:message code="oct.menu.privacy" /></a>
			</li>
		</ul>
	</div>
	