<%@page pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="<c:out value="${currentLanguage}"/>">
<head>
	<title><spring:message code="oct.wizard.duplicate.title" /> - <spring:message code="oct.title" /></title>
<%@ include file="../tiles/head.jsp"%>
</head>
<body>
	<div id="page" class="duplicate">
		<%@ include file="../tiles/header.jsp"%>

		<div id="content">
			<h2><spring:message code="oct.wizard.duplicate.title" /></h2>
			<p>
				<spring:message code="oct.wizard.duplicate.description" />
			</p>
		</div>

		<%@ include file="../tiles/footer.jsp"%>
	</div>
</body>

</html>