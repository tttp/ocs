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
							<td class="selectedcountry">
								<label for="country"><spring:message code="oct.s2.selectedcountry" /></label>
							</td>
							<td class="v">
								<strong><spring:message code="${form.countryToSignFor.name}" /></strong>
								&nbsp;
								<input type="submit" value="<spring:message code="oct.s2.changelangbutton" />" name="_target0"/>
							</td>
						</tr>
						
						<c:if test="${form.countryToSignFor != null and form.countryToSignFor.id != -1}">
							<c:set var="countryInfoKey" value="oct.property.form.header.${form.countryToSignFor.code}"/>
							<tr>
								<td colspan="2" class="left">
									<p id="regulation-info">
										<spring:message code="oct.property.regulation.link" var="regulationText" htmlEscape="true"/>
										<c:set var="regulationUrl" value="<a href='http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=OJ:L:2011:065:0001:0022:${currentLanguage}:PDF' target='_blank'>${regulationText}</a>"/>
										<spring:message code="${countryInfoKey}" arguments="${regulationUrl}" htmlEscape="false"/>									</p>
								</td>
							</tr>

							<c:forEach items="${form.groups}" var="groupValue">
								<c:if test="${groupValue.multichoice}">
									<c:forEach items="${form.properties}" var="propertyValue" varStatus="row">
										<c:if test="${groupValue.id == propertyValue.property.property.group.id}">
											<tr>
												<td class="k">
													<label for="<c:out value="${status.expression}"/>"><spring:message code="oct.property.idtype.label" />
													<span class="required">*</span></label>
												</td>
												<spring:bind path="form.multichoiceSelections[${propertyValue.property.property.group.id}]">
												<td class="v">
													<div class="improvedDropDownContainer">
														<select id="<c:out value="${status.expression}"/>" name="<c:out value="${status.expression}"/>" class="improvedDropDown" onBlur="this.className='improvedDropDown';" onMouseDown="this.className='improvedDropDownClick';" onChange="this.className='improvedDropDown';">
															<c:forEach items="${form.groupItems[propertyValue.property.property.group.id]}" var="item">
																<c:if test="${status.value == item.key}">
																	<c:set var="isSelected" value="selected" />
																</c:if>
																<c:if test="${status.value != item.key}">
																	<c:set var="isSelected" value="" />
																</c:if>
															
																<option value="<c:out value="${item.key}"/>" <c:out value="${isSelected}"/>><c:out value="${form.translatedProperties[item.value]}"/></option>
															</c:forEach>
													</select>
													</div>
												</td>
												</spring:bind>
											</tr>
											<tr>
												<td class="k">
													<label for="<c:out value="${status.expression}"/>"><spring:message code="oct.property.idnumber.label" /><span class="required">*</span></label>												
												</td>
												<spring:bind path="form.properties[${row.index}].value">
												<td class="v">
													<input type="text" id="<c:out value="${status.expression}"/>" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" />
													<form:errors path="properties[${row.index}].value" cssClass="error" element="span" />
												</td>
												</spring:bind>
											</tr>
										</c:if>
									</c:forEach>
								</c:if>
							</c:forEach>
								
							<!-- START crap -->
							<c:forEach items="${form.groups}" var="groupValue">
								<c:if test="${!groupValue.multichoice}">
									<c:forEach items="${form.properties}" var="propertyValue" varStatus="row">
										<c:if test="${groupValue.id == propertyValue.property.property.group.id and propertyValue.property.property.name == 'oct.property.issuing.authority'}">
											<tr>
												<spring:bind path="form.properties[${row.index}].value">
												<td class="k">
													<label for="<c:out value="${status.expression}"/>">
														<spring:message code="${propertyValue.property.property.name}" /><c:if test="${propertyValue.property.required}"><span class="required">*</span></c:if>
													</label>
												</td>
												<td class="v">
													<input type="text" id="<c:out value="${status.expression}"/>" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" <c:if test="${propertyValue.property.property.type == 'DATE'}">rel="date"</c:if>/>
													<form:errors path="properties[${row.index}].value" cssClass="error" element="span" />
												</td>
												</spring:bind>
											</tr>
										</c:if>
									</c:forEach>
								</c:if>
							</c:forEach>
							<!-- STOP crap -->
						</c:if>
					</table>
				</fieldset>
			
				<c:if test="${form.countryToSignFor != null and form.countryToSignFor.id != -1}">
				<c:set var="countryInfoKey" value="oct.property.form.header.${form.countryToSignFor.code}"/>
				<c:forEach items="${form.groups}" var="groupValue">
					<c:if test="${!groupValue.multichoice}">
					<fieldset>
						<legend><spring:message code="${groupValue.name}" /></legend>
						<table cellspacing="0" cellpadding="0" class="fields">
							<c:forEach items="${form.properties}" var="propertyValue" varStatus="row">
								<c:if test="${groupValue.id == propertyValue.property.property.group.id and propertyValue.property.property.name != 'oct.property.issuing.authority'}">
										<tr>
											<spring:bind path="form.properties[${row.index}].value">
												<td class="k">
													<label for="<c:out value="${status.expression}"/>">
														<spring:message code="${propertyValue.property.property.name}" /><c:if test="${propertyValue.property.required}">
															<c:if test="${propertyValue.property.property.type == 'DATE'}">
																<spring:message code="oct.error.invaliddateformat" />
															</c:if>
														<span class="required">*</span></c:if>
													</label>
												</td>
												<td class="v">
													<c:choose>
														<c:when test="${propertyValue.property.property.type == 'NATIONALITY' or propertyValue.property.property.type == 'COUNTRY'}">
															<c:choose>
																<c:when test="${propertyValue.property.property.type == 'NATIONALITY'}">
																	<c:set var="sufix" value=".nationality" />
																	<c:set var="cls" value="" />
																	<c:set var="id" value="${status.expression}" />
																</c:when>
																<c:otherwise>
																	<c:set var="sufix" value="" />
																	<c:set var="cls" value="autocomplete-select" />
																	<c:set var="id" value="countrySelect" />
																</c:otherwise>
															</c:choose>
															
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
																			<c:set var="somethingSelected" value="selected" />
																		</c:when>
																		<c:otherwise>
																			<c:set var="isSelected" value="" />
																		</c:otherwise>
																	</c:choose>
																	<oct:item>
																		<option value="<c:out value="${country.code}" />" <c:out value="${isSelected}"/>>
																			<oct:property><spring:message code="${country.name}${sufix}"/></oct:property>
																		</option>
																	</oct:item>
																</c:forEach>
																</oct:items>
																<c:if test="${propertyValue.property.property.type == 'COUNTRY'}">
																	<c:choose>
																		<c:when test="${status.value != null && status.value != '' && somethingSelected == ''}">
																			<c:set var="defaultSelected" value="selected" />
																		</c:when>
																		<c:otherwise>
																			<c:set var="defaultSelected" value="" />
																		</c:otherwise>
																	</c:choose>
																
																	<option value="other" <c:out value="${defaultSelected}"/>><spring:message code="oct.other.${currentLanguage}"/></option>
																</c:if>
															</select>
															<script type="text/javascript">
																__default = "<c:out value="${defaultSelected}" />"; 
																__code = "<c:out value="${status.value}" />";
															</script>


															
																
															</c:when>
														<c:when test="${propertyValue.property.property.type == 'LARGETEXT'}">
															<textarea id="<c:out value="${status.expression}"/>" name="<c:out value="${status.expression}"/>"><c:out value="${status.value}"/></textarea>
														</c:when>
														<c:otherwise>
															<input type="text" id="<c:out value="${status.expression}"/>" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" <c:if test="${propertyValue.property.property.type == 'DATE'}">rel="date"</c:if>/>
														</c:otherwise>
													</c:choose>
													<form:errors path="properties[${row.index}].value" cssClass="error" element="span" />
												</td>
											</spring:bind>
										</tr>
								</c:if>
							</c:forEach>
						</table>
					</fieldset>
					</c:if>
				</c:forEach>

				<fieldset>
					<form:checkbox path="accepted1" /> <spring:message code="oct.s2.confirmation.1" />
					<br />
					<form:errors path="accepted1" cssClass="error" element="div" />
					<br />
					<spring:message code="oct.s2.confirmation.3" var="privacyUrl"/>
					<form:checkbox path="accepted2" /> <spring:message code="oct.s2.confirmation.2" arguments="<a href='./privacy.do' target='_blank'>${privacyUrl}</a>"/>
					<br />
					<form:errors path="accepted2" cssClass="error" element="div" />
				</fieldset>
				
				<fieldset>
					<legend><spring:message code="oct.s2.entercaptcha" /></legend>
					<table cellspacing="0" cellpadding="0" class="fields">
					<tr>
						<td class="k">
							<img src="./captcha.do?uuid=<c:out value="${form.uniqueToken}" />" class="captcha" /><span class="required">*</span>
						</td>
						
						<td class="v">
							<spring:bind path="captcha">
								<input id="captcha" name="captcha" value="" />
								<form:errors path="captcha" cssClass="error" element="span" />
							</spring:bind>
						</td>
					</tr>
					</table>
				</fieldset>
				
				</c:if>
			
				<div id="buttons">
					<input type="hidden" value="1" name="_page" />
					<input type="submit" value="&laquo; <spring:message code="oct.s2.backbutton" />" name="_target0"/>
					<input type="submit" value="<spring:message code="oct.s2.submitbutton" /> &raquo;" name="_finish"/>
				</div>
			</form:form>

		</div>

		<%@ include file="../tiles/footer.jsp"%>
	</div>
</body>

</html>