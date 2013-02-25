<%@page pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>

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

			<form:form method="post" modelAttribute="form">
				<oct:requestToken/>
								
				<fieldset>
					<legend><spring:message code="oct.s11.info" arguments="${today}"/></legend>
					<%@ include file="./tiles/countTable.jsp" %>
					<div class="clear"></div>
					<div id="total">
						<spring:message code="oct.s6.total" />: <span><c:out value="${totalCount}" /></span>						
					</div>
				</fieldset>
				
				<fieldset>
					<legend><spring:message code="oct.s11.lv" /></legend>
					<p>
						<spring:message code="oct.s11.reg.language" />
						<a href="<c:url value="${oct_preview_url}?preview=true&langId=${defaultLanguageVersion.language.id}${draftParam}" />">
							<spring:message code="${defaultLanguageVersion.language.name}"/>
						</a>
					</p>
					<p><spring:message code="oct.s11.other.languages" /></p>
					<%@ include file="./tiles/langVersions.jsp" %>
					
				</fieldset>
			</form:form>
		</div>

		<%@ include file="./tiles/footer.jsp"%>
	</div>
</body>

</html>
