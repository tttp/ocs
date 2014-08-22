<%@page pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="oct" uri="oct"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="./tiles/head.jsp"%>
</head>
<body>
	<div id="page">
		<%@ include file="./tiles/header.jsp"%>

		<div id="content">
			<div class="settings-title">
				<h2 class="page-title">
					<spring:message code="oct.settings.title" />
				</h2>
			</div>
			<p>
				<spring:message code="oct.settings.info.1" />
			</p>

            <form:form method="post" modelAttribute="form" enctype="multipart/form-data">
            	<oct:requestToken/>
            	<form:errors path="*" cssClass="error" element="div" />
            	
				<fieldset>
					<legend><spring:message code="oct.settings.customlogo.title" /></legend>
				
					<spring:message code="oct.settings.customlogo.current" />
					<c:if test="${form.customLogo != null}">
						<a href="./logo.do?uid=${uid}" target="_blank"><spring:message code="oct.settings.customlogo.viewlogo" /></a>
						&nbsp;
						<form:checkbox path="deleteLogo" id="deleteLogo" /> <form:label path="deleteLogo"><spring:message code="oct.settings.customlogo.deletelogo" /></form:label>
					</c:if>
					<c:if test="${form.customLogo == null}">
						<spring:message code="oct.settings.customlogo.nologo" />
						<br />
						<form:label path="logoFile"><spring:message code="oct.settings.customlogo.uploadlogo" /></form:label>
						<form:input path="logoFile" type="file"/>
					</c:if>
				</fieldset>

				<fieldset>
					<legend><spring:message code="oct.settings.map.title" /></legend>
					<form:checkbox path="displayMap" id="displayMap" /> <form:label path="displayMap"><spring:message code="oct.settings.map.enablemap" /></form:label>
				</fieldset>

				<fieldset>
					<legend><spring:message code="oct.settings.callback.title" /></legend>
					<form:label path="callbackUrl"><spring:message code="oct.settings.callback.url" /></form:label> <form:input path="callbackUrl" id="callbackUrl" cssClass="input-box"/>
				</fieldset>
				
				<div id="buttons">
					<input type="submit" value="<spring:message code="oct.settings.button.cancel" />" name="cancelSettings" />
					<input type="submit" value="<spring:message code="oct.settings.button.save" /> &raquo;" name="saveSettings" />
				</div>
			</form:form>
		</div>

		<%@ include file="./tiles/footer.jsp"%>
	</div>
</body>

</html>