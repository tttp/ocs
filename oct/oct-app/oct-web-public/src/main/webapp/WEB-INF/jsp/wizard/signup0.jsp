<%@page pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="oct" uri="oct"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
			<h2>
				<spring:message code="oct.s2.intro" />
				<br />
				<c:out value="${oct_initiative_description.title}" />
			</h2>
			<h3><spring:message code="oct.s2.subtitle" /></h3>
			
			<form:form method="post" modelAttribute="form" name="signupForm" id="signupForm">
				<oct:requestToken/>
				<p>
					<spring:message code="oct.s2.pleaseselectcountry" />
				</p>
				<fieldset class="signup">
					<p><spring:message code="oct.s2.info.1" /></p>
					<p><spring:message code="oct.s2.info.conditions" />
					<br /> 
					<a href="http://ec.europa.eu/citizens-initiative/files/requirements-${currentLanguage}.pdf" target="_blank">http://ec.europa.eu/citizens-initiative/files/requirements-${currentLanguage}.pdf</a></p>
					<p><spring:message code="oct.s2.info.2" /></p>
					<p>
						<spring:message code="oct.s2.info.3" />
						<br />
						<a href="http://ec.europa.eu/citizens-initiative/public/how-to-signup" target="_blank">http://ec.europa.eu/citizens-initiative/public/how-to-signup</a>
					</p>
				</fieldset>

				<fieldset>
					<table cellspacing="0" cellpadding="0" class="fields">
						<tr>
							<td class="k">
								<label for="countryCode"><spring:message code="oct.s2.selectcountry" /></label>
							</td>
							<td class="v">
																
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
								<input type="submit" id="submitBtn" value="<spring:message code="oct.s2.selectcountrybutton" /> &raquo;" name="_target1" tabindex="1"/>
								<form:errors path="countryCode" cssClass="error" element="span" />
							</td>
						</tr>
					</table>
				</fieldset>
			
			</form:form>
			</c:if>
		</div>

		<%@ include file="../tiles/footer.jsp"%>
	</div>
</body>

</html>