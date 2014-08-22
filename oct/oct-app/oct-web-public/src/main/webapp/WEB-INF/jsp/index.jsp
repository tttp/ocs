<%@page pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="oct" uri="oct"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="<c:out value="${currentLanguage}"/>">
<head>
	<title><spring:message code="oct.menu.home" /> - <spring:message code="oct.title" /></title>
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
			<h1>
				<spring:message code="oct.s1.intro" />
				<br />
				<span lang="<c:out value="${initiativeLang}" />"><c:out value="${oct_initiative_description.title}" /></span>
			</h1>
			
			<form:form method="post" modelAttribute="form" id="signupForm">
				<div class="buttons support">
					<c:if test="${!oct_system_preferences.collecting}">
						<spring:message code="oct.collection.off.error" />
					</c:if>
				
					<c:if test="${oct_system_preferences.collecting}">
						<spring:message code="oct.s1.supportbutton.description" />
						<br />
						<input type="submit" value="<spring:message code="oct.s1.supportbutton" />" name="_support" accesskey="s"/>
					</c:if>
				</div>
			
				<fieldset class="home">
					<oct:requestToken/>
				
					<p>
						<label><spring:message code="oct.s1.subject" /></label>
						<br />
						<strong lang="<c:out value="${initiativeLang}" />"><c:out value="${oct_initiative_description.subjectMatter}" /></strong>
					</p>
					
					<p>
						<label><spring:message code="oct.s1.objectives" /></label>
						<br />
						<strong lang="<c:out value="${initiativeLang}" />"><c:out value="${oct_initiative_description.objectives}" /></strong>
					</p>
					
					<p>
						<label><spring:message code="oct.s1.languages" /></label>
						<br />
						<c:forEach items="${initiativeLanguages}" var="language">
							<c:url value="./index.do" var="iUrl">
								<c:param name="initiativeLang" value="${language.code}" />
							</c:url>
							<a href="${iUrl}" lang="<c:out value="${language.code}" />"><spring:message code="${language.name}" /></a> &nbsp;
						</c:forEach>
					</p>
					
					<p>
						<label><spring:message code="oct.s1.registrationno" /></label>
						<br />
						<strong><c:out value="${oct_system_preferences.registrationNumber}" /></strong>
					</p>
					
					<p>
						<label><spring:message code="oct.s1.registrationdate" /></label>
						<br />
						<strong><fmt:formatDate value="${oct_system_preferences.registrationDate}" pattern="dd/MM/yyyy"/></strong>
					</p>

                    <p>
						<label><spring:message code="oct.s1.url" /></label>
						<br />
						<a href="<c:out value="${oct_system_preferences.commissionRegisterUrl}/${oct_initiative_description.language.code}" />" target="_blank"><c:out value="${oct_system_preferences.commissionRegisterUrl}/${oct_initiative_description.language.code}" /></a>
					</p>						
					
                    <p>
						<label><spring:message code="oct.s1.contactpersons" /></label>
						<br />
						<strong lang="<c:out value="${initiativeLang}" />"><c:out value="${contact.name}" /></strong>
					</p>
					
					<p>
						<label><spring:message code="oct.s1.contactemails" /></label>
						<br />
						<strong><c:out value="${contact.email}" /></strong>
					</p>

					<p>
						<label><spring:message code="oct.s1.organizers" /></label>
						<br />
						<strong lang="<c:out value="${initiativeLang}" />"><c:out value="${organizers}" /></strong>
					</p>
					
					<p>
						<label><spring:message code="oct.organiser.website.${currentLanguage}" /></label>
						<br />
						<a href="<c:out value="${oct_initiative_description.url}" />" target="_blank"><c:out value="${oct_initiative_description.url}" /></a>
					</p>
				</fieldset>

				<p id="lower-info">
					<spring:message code="oct.s1.support.info1" />
					<br />
					<spring:message code="oct.s1.support.info2" /> <a href="http://ec.europa.eu/citizens-initiative" target="_blank">http://ec.europa.eu/citizens-initiative</a>
				</p>
				
				<div class="buttons support">
					<c:if test="${!oct_system_preferences.collecting}">
						<spring:message code="oct.collection.off.error" />
					</c:if>
				
					<c:if test="${oct_system_preferences.collecting}">
						<spring:message code="oct.s1.supportbutton.description" />
						<br />
						<input type="submit" value="<spring:message code="oct.s1.supportbutton" />" name="_support" accesskey="s"/>
					</c:if>
				</div>
			</form:form>
			</c:if>
		</div>

		<%@ include file="./tiles/footer.jsp"%>
	</div>
</body>

</html>