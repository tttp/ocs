<%@page pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="oct" uri="oct"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="<c:out value="${currentLanguage}"/>">
<head>
	<title><spring:message code="oct.s2.subtitle" /> - <spring:message code="oct.title" /></title>
<%@ include file="../tiles/head.jsp"%>
</head>
<body>
	<div id="page">
		<%@ include file="../tiles/header.jsp"%>

		<div id="content">
			<c:if test="${oct_system_state == 'DEPLOYED'}">
				<spring:message code="oct.error.systemnotinitialised" />
			</c:if>

			<c:if test="${!oct_system_preferences.collecting}">
				<spring:message code="oct.collection.off.error" />
			</c:if>

			<c:if test="${oct_system_state != 'DEPLOYED' and oct_system_preferences.collecting}">
			<h1>
				<spring:message code="oct.s2.intro" />
				<br />
				<span lang="<c:out value="${initiativeLang}" />"><c:out value="${oct_initiative_description.title}" /></span>
			</h1>
			
			<form:form method="post" modelAttribute="form" id="signupForm">
				<h2><spring:message code="oct.s2.subtitle" /></h2>
				
				<p>
					<spring:message code="oct.s2.pleaseselectcountry" />
				</p>
				
				<fieldset class="signup">
					<oct:requestToken/>
					
					<legend></legend>
				
					<p><spring:message code="oct.s2.info.1" /></p>
					
					<p>
						<spring:message code="oct.s2.info.conditions" />
						<br />
						<a href="http://ec.europa.eu/citizens-initiative/files/requirements-${currentLanguage}.pdf">http://ec.europa.eu/citizens-initiative/files/requirements-${currentLanguage}.pdf</a>
					</p>
					
					<p>
						<spring:message code="oct.s2.info.3" />
						<br />
						<a href="http://ec.europa.eu/citizens-initiative/public/how-to-signup">http://ec.europa.eu/citizens-initiative/public/how-to-signup</a>
					</p>
				</fieldset>

				<fieldset>
					<legend></legend>
					<p>
						<label for="countryCode"><spring:message code="oct.s2.selectcountry" /></label>
						<br />
						<form:select path="countryCode" id="countryCode">
							<form:option value=""><spring:message code="oct.s2.selectcountry"/></form:option>										
							<oct:items>
								<c:forEach items="${countries}" var="country">
									<oct:item>
										<form:option value="${country.code}">
											<oct:property><spring:message code="${country.name}"/></oct:property>
										</form:option>
									</oct:item>
								</c:forEach>
							</oct:items>
						</form:select>
						
						<input type="hidden" value="0" name="_page" />
						<input type="submit" id="submitBtn" value="<spring:message code="oct.s2.selectcountrybutton" /> &raquo;" name="_target1" accesskey="s"/>
						<form:errors path="countryCode" cssClass="error" element="span" />
					</p>

				
				</fieldset>
			
			</form:form>
			</c:if>
		</div>

		<%@ include file="../tiles/footer.jsp"%>
	</div>
</body>

</html>