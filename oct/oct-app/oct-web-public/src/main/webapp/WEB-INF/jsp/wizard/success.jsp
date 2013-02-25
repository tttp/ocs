<%@page pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../tiles/head.jsp"%>
</head>
<body>
	<div id="page">
		<%@ include file="../tiles/header.jsp"%>

		<div id="content">
			<h2>
				<spring:message code="oct.s3.info.1" arguments="${oct_initiative_description.title}" htmlEscape="true"/>
			</h2>
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
			<p>
				<c:if test="${oct_initiative_url!=null}">
				<a href="<c:url value="${oct_initiative_url}" />">&laquo; <spring:message code="oct.s3.backbutton" /></a>
				</c:if>
			</p>
		</div>

		<%@ include file="../tiles/footer.jsp"%>
	</div>
</body>

</html>