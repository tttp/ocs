<%@page pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
			<h2><spring:message code="oct.wizard.duplicate.title" /></h2>
			<p>
				<spring:message code="oct.wizard.duplicate.description" />
			</p>
		</div>

		<%@ include file="../tiles/footer.jsp"%>
	</div>
</body>

</html>