<%@page pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title><spring:message code="oct.errorpage.title" /></title>
	<link href="./css/default.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="errorpage">
		<div id="content">
			<h1>
				<spring:message code="oct.errorpage.title" />
			</h1>

            <br />
			<spring:message code="oct.errorpage.description" />
			<br /> 
			
			<strong>
			<c:choose>
				<c:when test="${token == 'session_expired'}">
					<spring:message code="oct.errorpage.sessionexpired" />
				</c:when>
				<c:when test="${token == 'forbidden'}">
					<spring:message code="oct.errorpage.forbidden" />
				</c:when>
				<c:when test="${token == 'not_found'}">
					<spring:message code="oct.errorpage.notfound" />
				</c:when>
				<c:otherwise>
					<c:out value="${token}" />
				</c:otherwise>
			</c:choose>
			</strong>
			
			<br /><br />
			<spring:message code="oct.errorpage.appologies" />
		</div>
	</div>
</body>
</html>