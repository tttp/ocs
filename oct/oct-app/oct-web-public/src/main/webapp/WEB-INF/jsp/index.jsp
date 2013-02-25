<%@page pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
			<c:if test="${oct_system_state == 'DEPLOYED'}">
				<spring:message code="oct.error.systemnotinitialised" />
			</c:if>
		
			<c:if test="${oct_system_state != 'DEPLOYED'}">
			<h2>
				<spring:message code="oct.s1.intro" />
				<br />
				<c:out value="${oct_initiative_description.title}" />
			</h2>
			
			<form:form method="post" modelAttribute="form" name="signupForm" id="signupForm">
				<oct:requestToken/>
			<fieldset class="home">
			<table cellspacing="0" cellpadding="0" class="fields">
			<tr>
				<td class="k">
					<spring:message code="oct.s1.subject" />
				</td>
				<td class="v">
					<c:out value="${oct_initiative_description.subjectMatter}" />
				</td>
			</tr>

			<tr>
				<td class="k">
					<spring:message code="oct.s1.objectives" />
				</td>
				<td class="v">
					<c:out value="${oct_initiative_description.objectives}" />
				</td>
			</tr>
			
			<tr>
				<td class="k">
					<spring:message code="oct.s1.languages" />
				</td>
				<td class="v">
					<c:forEach items="${initiativeLanguages}" var="language">
						<a href="./index.do?initiativeLang=<c:out value="${language.code}" />"><spring:message code="${language.name}" /></a>
						&nbsp;
					</c:forEach>
				</td>
			</tr>

			<tr>
				<td class="k">
					<spring:message code="oct.s1.registrationno" />
				</td>
				<td class="v">
					<c:out value="${oct_system_preferences.registrationNumber}" />
				</td>
			</tr>

			<tr>
				<td class="k">
					<spring:message code="oct.s1.registrationdate" />
				</td>
				<td class="v">
					<fmt:formatDate value="${oct_system_preferences.registrationDate}" pattern="dd/MM/yyyy"/>
				</td>
			</tr>

			<tr>
				<td class="k">
					<spring:message code="oct.s1.url" />
				</td>
				<td class="v">
					<a href="<c:out value="${oct_system_preferences.commissionRegisterUrl}/${oct_initiative_description.language.code}" />" target="_blank"><c:out value="${oct_system_preferences.commissionRegisterUrl}/${oct_initiative_description.language.code}" /></a>
				</td>
			</tr>

			<tr>
				<td class="k">
					<spring:message code="oct.s1.organizers" />
				</td>
				<td class="v">
					<c:out value="${contact.organizers}" />
				</td>
			</tr>

			<tr>
				<td class="k">
					<spring:message code="oct.s1.contactpersons" />
				</td>
				<td class="v">
					<c:out value="${contact.name}" />
				</td>
			</tr>

			<tr>
				<td class="k">
					<spring:message code="oct.s1.contactemails" />
				</td>
				<td class="v">
					<c:out value="${contact.email}" />
				</td>
			</tr>

			<tr>
				<td class="k">
					<spring:message code="oct.organiser.website.${currentLanguage}" />
				</td>
				<td class="v">
					<a href="<c:out value="${oct_initiative_description.url}" />" target="_blank"><c:out value="${oct_initiative_description.url}" /></a>
				</td>
			</tr>

			</table>
			</fieldset>

			<div id="lower-info">
				<spring:message code="oct.s1.support.info1" />
				<br />
				<spring:message code="oct.s1.support.info2" /> <a href="http://ec.europa.eu/citizens-initiative" target="_blank">http://ec.europa.eu/citizens-initiative</a>
			</div>
			<div id="buttons">
				<c:if test="${!oct_system_preferences.collecting}">
					<spring:message code="oct.collection.off.error" />
				</c:if>
			
				<c:if test="${oct_system_preferences.collecting}">
					<input type="submit" value="<spring:message code="oct.s1.supportbutton" /> &raquo;" name="_support" tabindex="3"/>
				</c:if>
			</div>
			</form:form>
			</c:if>
		</div>

		<%@ include file="./tiles/footer.jsp"%>
	</div>
</body>

</html>