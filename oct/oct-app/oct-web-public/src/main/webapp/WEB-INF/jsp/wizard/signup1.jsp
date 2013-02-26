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
			<h1>
				<spring:message code="oct.s2.intro" />
				<br />
				<span lang="<c:out value="${initiativeLang}" />"><c:out value="${oct_initiative_description.title}" /></span>
			</h1>
			
			<h2><spring:message code="oct.s2.subtitle" /></h2>
			
			<form:form method="post" modelAttribute="form" id="signupForm">
				<p>
					<spring:message code="oct.s2.pleaseselectcountry" />
				</p>
			
				<fieldset class="signup">
					<legend></legend>
					<oct:requestToken/>
					
					<p>
						<spring:message code="oct.s2.info.1" />
					</p>
					
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
						<label><spring:message code="oct.s2.selectedcountry" /></label>
						<br />
						<strong><spring:message code="${form.countryToSignFor.name}" /></strong>
						&nbsp;
						<input type="submit" value="<spring:message code="oct.s2.changelangbutton" />" name="_target0"/>
					</p>
						
					<c:if test="${form.countryToSignFor != null and form.countryToSignFor.id != -1}">
						<c:set var="countryInfoKey" value="oct.property.form.header.${form.countryToSignFor.code}"/>
						<p id="regulation-info">
							<spring:message code="oct.property.regulation.link" var="regulationText" htmlEscape="true"/>
							<c:set var="regulationUrl" value="<a href='http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=OJ:L:2011:065:0001:0022:${currentLanguage}:PDF'>${regulationText}</a>"/>
							<spring:message code="${countryInfoKey}" arguments="${regulationUrl}" htmlEscape="false"/>								
						</p>

						<c:forEach items="${form.groups}" var="groupValue">
							<c:if test="${groupValue.multichoice}">
								<c:forEach items="${form.properties}" var="propertyValue" varStatus="row">
									<c:if test="${groupValue.id == propertyValue.property.property.group.id}">
										<c:set var="id" value="multi${row.index}" />
										<p>
											<label for="${id}"><spring:message code="oct.property.idtype.label" /></label>
											<span class="required">*</span>
											<br />
											<spring:bind path="form.multichoiceSelections[${propertyValue.property.property.group.id}]">
												<select id="${id}" name="<c:out value="${status.expression}"/>" class="improvedDropDown">
													<c:forEach items="${form.groupItems[propertyValue.property.property.group.id]}" var="item">
														<c:if test="${status.value == item.key}">
															<c:set var="isSelected" value="selected=\"selected\"" />
														</c:if>
														<c:if test="${status.value != item.key}">
															<c:set var="isSelected" value="" />
														</c:if>
														<option value="<c:out value="${item.key}"/>" ${isSelected}><c:out value="${form.translatedProperties[item.value]}"/></option>
													</c:forEach>
												</select>
											</spring:bind>
										</p>

										<p>
											<label for="p${id}"><spring:message code="oct.property.idnumber.label" /></label>
											<span class="required">*</span>
											<br />												
											<spring:bind path="form.properties[${row.index}].value">
												<input type="text" id="p${id}" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" />
												<form:errors path="properties[${row.index}].value" cssClass="error" element="span" />
											</spring:bind>
										</p>
									</c:if>
								</c:forEach>
							</c:if>
						</c:forEach>
								
						<!-- START crap -->
						<c:forEach items="${form.groups}" var="groupValue">
							<c:if test="${!groupValue.multichoice}">
								<c:forEach items="${form.properties}" var="propertyValue" varStatus="row">
									<c:set var="id" value="item${row.index}" />
									<c:if test="${groupValue.id == propertyValue.property.property.group.id and propertyValue.property.property.name == 'oct.property.issuing.authority'}">
										<p>
											<spring:bind path="form.properties[${row.index}].value">
												<label for="${id}"><spring:message code="${propertyValue.property.property.name}" /></label>
												<c:if test="${propertyValue.property.required}"><span class="required">*</span></c:if>
												<br />
												<input type="text" id="${id}" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" <c:if test="${propertyValue.property.property.type == 'DATE'}">class="date"</c:if>/>
												<form:errors path="properties[${row.index}].value" cssClass="error" element="span" />
											</spring:bind>
										</p>
									</c:if>
								</c:forEach>
							</c:if>
						</c:forEach>
						<!-- STOP crap -->
					</c:if>
				</fieldset>
			
				<c:if test="${form.countryToSignFor != null and form.countryToSignFor.id != -1}">
				<c:set var="countryInfoKey" value="oct.property.form.header.${form.countryToSignFor.code}"/>
				<c:forEach items="${form.groups}" var="groupValue">
					<c:if test="${!groupValue.multichoice}">
						<fieldset>
							<legend><spring:message code="${groupValue.name}" /></legend>
							<c:forEach items="${form.properties}" var="propertyValue" varStatus="row">
								<c:if test="${groupValue.id == propertyValue.property.property.group.id and propertyValue.property.property.name != 'oct.property.issuing.authority'}">
									<spring:bind path="form.properties[${row.index}].value">
										<c:choose>
											<c:when test="${propertyValue.property.property.type == 'NATIONALITY'}">
												<c:set var="sufix" value=".nationality" />
												<c:set var="cls" value="" />
												<c:set var="id" value="item${row.index}" />
											</c:when>
											<c:when test="${propertyValue.property.property.type == 'COUNTRY'}">
												<c:set var="sufix" value="" />
												<c:set var="cls" value="autocomplete-select" />
												<c:set var="id" value="countrySelect" />
											</c:when>
											<c:otherwise>
												<c:set var="id" value="item${row.index}" />
											</c:otherwise>
										</c:choose>
										
										<p>
											<label for="<c:out value="${id}"/>"><spring:message code="${propertyValue.property.property.name}" />
												<c:if test="${propertyValue.property.property.type == 'DATE'}">
													<spring:message code="oct.error.invaliddateformat" />
												</c:if>
											</label>
											<c:if test="${propertyValue.property.required}">
												<span class="required">*</span>
											</c:if>											
											<br />
											<c:choose>
												<c:when test="${propertyValue.property.property.type == 'NATIONALITY' or propertyValue.property.property.type == 'COUNTRY'}">
													<c:set var="somethingSelected" value="" />
													<select id="<c:out value="${id}"/>" name="<c:out value="${status.expression}"/>" class="<c:out value="${cls}"/>">
													<!-- add option select/select country -->
														<c:choose>
															<c:when test="${propertyValue.property.property.type == 'NATIONALITY'}">
																<option value=""><spring:message code="oct.s2.selectcountrybutton"/></option>
															</c:when>
															<c:otherwise>
															<option value=""><spring:message code="oct.s2.selectcountry"/></option>
															</c:otherwise>
														</c:choose>
														<oct:items>
														<c:forEach items="${countries}" var="country">
															<c:choose>
																<c:when test="${country.code == status.value}">
																	<c:set var="isSelected" value="selected" />
																	<c:set var="somethingSelected" value="selected=\"selected\"" />
																</c:when>
																<c:otherwise>
																	<c:set var="isSelected" value="" />
																</c:otherwise>
															</c:choose>
															<oct:item>
																<option value="<c:out value="${country.code}" />" ${isSelected}>
																	<oct:property><spring:message code="${country.name}${sufix}"/></oct:property>
																</option>
															</oct:item>
														</c:forEach>
														</oct:items>
														<c:if test="${propertyValue.property.property.type == 'COUNTRY'}">
															<c:choose>
																<c:when test="${status.value != null && status.value != '' && somethingSelected == ''}">
																	<c:set var="defaultSelected" value="selected=\"selected\"" />
																</c:when>
																<c:otherwise>
																	<c:set var="defaultSelected" value="" />
																</c:otherwise>
															</c:choose>
														
															<option value="other" ${defaultSelected}><spring:message code="oct.other.${currentLanguage}"/></option>
														</c:if>
													</select>
													<script type="text/javascript">
														__default = "<c:out value="${defaultSelected}" />"; 
														__code = "<c:out value="${status.value}" />";
													</script>
												</c:when>
												<c:when test="${propertyValue.property.property.type == 'LARGETEXT'}">
													<textarea id="<c:out value="${id}"/>" name="<c:out value="${status.expression}"/>" rows="5" cols="40"><c:out value="${status.value}"/></textarea>
												</c:when>
												<c:otherwise>
													<input type="text" id="<c:out value="${id}"/>" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" <c:if test="${propertyValue.property.property.type == 'DATE'}">class="date"</c:if>/>
												</c:otherwise>
											</c:choose>
											<form:errors path="properties[${row.index}].value" cssClass="error" element="span" />
										</p>
									</spring:bind>
								</c:if>
							</c:forEach>
					</fieldset>
					</c:if>
				</c:forEach>

				<fieldset>
					<legend></legend>
					
					<form:checkbox path="accepted1" /> <label for="accepted11"><spring:message code="oct.s2.confirmation.1" /></label>
					<form:errors path="accepted1" cssClass="error" element="div" />
					<br />
					<br />
					<spring:message code="oct.s2.confirmation.3" var="privacyUrl"/>
					<c:url value="./privacy.do" var="pUrl" />
					<form:checkbox path="accepted2" /> <label for="accepted21"><spring:message code="oct.s2.confirmation.2" arguments="<a href='${pUrl}'>${privacyUrl}</a>"/></label>
					<form:errors path="accepted2" cssClass="error" element="div" />
				</fieldset>
				
				<fieldset>
					<legend></legend>
					
					<p>
						<label for="captcha">
						<c:choose>
							<c:when test="${form.captchaType == 'audio'}">
								<spring:message code="oct.s2.entercaptcha.audio" />
							</c:when>
							<c:otherwise>
								<spring:message code="oct.s2.entercaptcha" />
							</c:otherwise>
						</c:choose>
						</label>
						
						<span class="required">*</span>
						<br />

						<c:choose>
							<c:when test="${form.captchaType == 'audio'}">
								<c:url value="./captcha.do" var="cUrl">
									<c:param name="uuid" value="${form.uniqueToken}" />
									<c:param name="type" value="a" />
								</c:url>
								<a href="<c:out value="${cUrl}" />" title="<spring:message code="oct.s2.entercaptcha.audio" />"><img height="60px" width="200px" src="./images/speaker.png" alt="<spring:message code="oct.s2.entercaptcha.audio" />" /></a>
								<input type="image" src="./images/smallcharacters.png" title="<spring:message code="oct.s2.entercaptcha.back.image" />" name="_changeCaptchaToImage" />							
							</c:when>
							<c:otherwise>
								<c:url value="./captcha.do" var="cUrl">
									<c:param name="uuid" value="${form.uniqueToken}" />
									<c:param name="type" value="i" />
								</c:url>
								<img src="<c:out value="${cUrl}" />" class="captcha" alt="<spring:message code="oct.s2.entercaptcha" />"/>
								<input type="image" src="./images/smallspeaker.png" title="<spring:message code="oct.s2.entercaptcha.back.audio" />" name="_changeCaptchaToAudio" />
							</c:otherwise>
						</c:choose>
						
						<br />
						<spring:bind path="captcha">
							<input type="text" id="captcha" name="captcha" value="" />
							<form:errors path="captcha" cssClass="error" element="span" />
						</spring:bind>
					</p>
				</fieldset>
				
				</c:if>
			
				<div id="buttons">
					<input type="hidden" value="1" name="_page" />
					<input type="submit" value="&laquo; <spring:message code="oct.s2.backbutton" />" name="_target0"/>
					<input type="submit" value="<spring:message code="oct.s2.submitbutton" /> &raquo;" name="_finish" accesskey="s"/>
				</div>
			</form:form>

		</div>

		<%@ include file="../tiles/footer.jsp"%>
	</div>
</body>

</html>