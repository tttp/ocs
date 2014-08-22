<%@page pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:fb="http://ogp.me/ns/fb#" lang="<c:out value="${currentLanguage}"/>">
<head>
	<title><spring:message code="oct.s3.submissionsuccessful" /> - <spring:message code="oct.title" /></title>
<%@ include file="../tiles/head.jsp"%>
</head>
<body>
	<div id="page">
		<%@ include file="../tiles/header.jsp"%>

		<div id="content">
			<h1>
				<spring:message code="oct.s3.info.1" arguments="${oct_initiative_description.title}" htmlEscape="true"/>
			</h1>
			<p>
				<spring:message code="oct.s3.submissionsuccessful" />
			</p>
			<div id="review">
				<p>			
					<spring:message code="oct.s3.submissiondate" />
					<c:out value="${reviewBean.date}" />
				</p>
				<p>
					<spring:message code="oct.s3.signatureid" />
					<c:out value="${reviewBean.uuid}" />
				</p>
			</div>			
			<p class="back">
				<c:if test="${oct_initiative_url!=null}">
					<a href="<c:url value="${oct_initiative_url}" />">&laquo; <spring:message code="oct.s3.backbutton" /></a>
				</c:if>
			</p>
		</div>

		<%@ include file="../tiles/footer.jsp"%>
	</div>
</body>

</html>