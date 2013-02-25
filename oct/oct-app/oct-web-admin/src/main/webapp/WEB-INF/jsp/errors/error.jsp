<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head></head>
<body>
	<div id="page">
		<div id="content">
			<h2>
				<spring:message code="oct.errorpage.title" />
			</h2>

			<spring:message code="oct.errorpage.description" /> <c:out value="${token}" />
			<br />
			<spring:message code="oct.errorpage.appologies" />
		</div>
	</div>
</body>
</html>