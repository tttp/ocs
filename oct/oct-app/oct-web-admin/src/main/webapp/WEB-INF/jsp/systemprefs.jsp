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
			<div class="systemprefs-title">
				<h2 class="page-title">
					<spring:message code="oct.s3.title" />
				</h2>
			</div>
			<c:if test="${oct_system_state=='OPERATIONAL'}">
			<p>
				<spring:message code="oct.s12.info"/>
			</p>
			</c:if>
			<c:if test="${oct_system_state!='OPERATIONAL'}">
			<p>
				<spring:message code="oct.s3.msg.1" />
			</p>
			<ul>
				<li><spring:message code="oct.s3.msg.2" /></li>
			</ul>
			<p>
				<spring:message code="oct.s3.msg.3" />
				<spring:message code="oct.s3.msg.4" />
				<spring:message code="oct.s3.msg.5" />
			</p>
			<p>
				<spring:message code="oct.s3.msg.6" />
				<spring:message code="oct.s3.msg.7" />
			</p>
			<ul>
				<li><spring:message code="oct.s3.msg.8" /></li>
			</ul>
			</c:if>
			<c:choose>
				<c:when test="${uploading&&!draftUploaded}">
					<c:set var="enctype" value="multipart/form-data"/>				
				</c:when>
				<c:otherwise>
					<c:set var="enctype" value=""/>				
				</c:otherwise>
			</c:choose>
			<form:form method="post" modelAttribute="form" enctype="${enctype}">
				<oct:requestToken/>
				<form:errors path="*" cssClass="error" element="div" />
				
				<c:if test="${!uploading && !editing && !draftUploaded}">
					<div id="buttons">
						<input type="submit"
							value="<spring:message code="oct.s3.upload" />" name="upload" />
						<c:if test="${oct_system_state!='OPERATIONAL'}">
							<input type="submit" value="<spring:message code="oct.s3.edit" /> &raquo;" name="edit" />
						</c:if>
					</div>
				</c:if>


				<fieldset>
					<c:choose>
						<c:when test="${!editing || draftUploaded}">
							<c:set var="tCss" value="preview"/>				
						</c:when>
						<c:otherwise>
							<c:set var="tCss" value="fields"/>				
						</c:otherwise>
					</c:choose>
				
					<a name="initdetails"></a>
				
					<table cellspacing="0" cellpadding="0" class="<c:out value="${tCss}" />">
						<c:if test="${uploading&&!draftUploaded}">
							<tr>
								<td>
								<label for="xmlFile"><spring:message code="oct.s4.xml.upload" /></label></td>								
								<td class="tab buttons">
								<input type="file" name="xmlFile"/>
								</td>
							</tr>
							<tr>
								<td class="tab buttons" colspan="2">								
								<input type="submit" value="<spring:message code="oct.s7.upload" /> &raquo;" name="doUpload" />
								<input type="submit" value="<spring:message code="oct.s4.cancel" />" name="resetUpload" />
								</td>
							</tr>
						</c:if>
						<c:if test="${!uploading||draftUploaded}">
							<%@ include file="./tiles/initiativeDesc.jsp"%>
							<tr>
								<td class="k">
								<label for="language"><spring:message code="oct.s11.reg.language" /></label>
								</td>
								<td class="v">
									<c:if test="${!editing}">
										<c:if test="${form.language!=null}">
											<a href="<c:url value="${oct_preview_url}#initdetails" />"><spring:message code="${form.language.name}" /></a>
										</c:if>
										<c:if test="${form.language==null}">
											&nbsp;
										</c:if>
										
									</c:if> 
									<c:if test="${editing}">
										<form:select path="language" items="${form.languages}" itemValue="code" itemLabel="label"/>
									</c:if>
								</td>
							</tr>
							
							<tr>
								<td class="k">
								<label for="registrationNumber"><spring:message	code="oct.s4.reg.nr" /></label></td>
								<td class="v">
								
								<c:choose>
										<c:when test="${editing&&!draftUploaded}">
											<form:input path="registrationNumber" id="registrationNumber" readonly="${!editing||draftUploaded}" size="50" />
										</c:when>
										<c:otherwise>
											<c:out value="${form.registrationNumber}"/>										
										</c:otherwise>
								</c:choose>

								</td>
							</tr>
							<tr>
								<td class="k">
								<label for="registrationDate"><spring:message code="oct.s4.reg.date" /></label></td>
								<td class="v">
								<form:input path="registrationDate"	id="registrationDate" readonly="${!editing||draftUploaded}" size="10" rel="date" /></td>
							</tr>
							<tr>
								<td class="k">
								<label for="registerUrl"><spring:message code="oct.s4.initiative.url" /></label></td>
								<td class="v">
									<c:choose>
										<c:when test="${editing&&!draftUploaded}">
											<form:input path="registerUrl" id="registerUrl"
												disabled="${!editing}" size="50" />
										</c:when>
										<c:otherwise>
											<a href="<c:out value="${form.registerUrl}" />" target="_blank"><c:out value="${form.registerUrl}" /></a>											
										</c:otherwise>
									</c:choose></td>
							</tr>
							<tr>
								<td class="k">
								<label for="organizers"><spring:message code="oct.s4.organisers" /></label></td>
								<td class="v">
								
								<c:choose>
										<c:when test="${editing&&!draftUploaded}">
											<form:input path="organizers" id="organizers" readonly="${!editing||draftUploaded}" size="50" />								
										</c:when>
										<c:otherwise>
											<c:out value="${form.organizers}"/>
										</c:otherwise>
									</c:choose>
								
								</td>
							</tr>
							<tr>
								<td class="k">
								<label for="contactPerson"><spring:message code="oct.s4.contacts" /></label></td>
								<td class="v">	
									<c:choose>
										<c:when test="${editing&&!draftUploaded}">
											<form:input path="contactPerson" id="contactPerson" readonly="${!editing||draftUploaded}" size="50" />
										</c:when>
										<c:otherwise>
											<c:out value="${form.contactPerson}"/>	
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<td class="k">
								<label for="contactEmail"><spring:message code="oct.s4.email" /></label></td>
								<td class="v">
									<c:choose>
										<c:when test="${editing&&!draftUploaded}">
											<form:input path="contactEmail" id="contactEmail"
												disabled="${!editing}" size="50"/>
										</c:when>
										<c:otherwise>
											<a href="mailto:<c:out value="${form.contactEmail}" />" target="_blank"><c:out value="${form.contactEmail}"/></a>
											<input type="hidden" value="<c:out value="${form.contactEmail}" />" name="contactEmail" />
										</c:otherwise>
									</c:choose></td>
							</tr>
							
						</c:if>
					</table>
				</fieldset>
				<c:if test="${(!editing&&!uploading)||draftUploaded}">
				<fieldset>
					<legend><spring:message code="oct.s5.available.languages" /></legend>
							
					<%@ include file="./tiles/langVersions.jsp" %>	
				</fieldset>
				</c:if>
						

				<c:if test="${editing}">
					<div id="buttons">
						<input type="submit"
							value="<spring:message code="oct.s4.cancel" />" name="resetEdit" />
						<input type="submit"
							value="<spring:message code="oct.s4.save" /> &raquo;" name="confirmEdit" />
					</div>
				</c:if>
				
				<c:if test="${draftUploaded}">
					<div id="buttons">
						<input type="submit"
							value="<spring:message code="oct.s4.cancel" />" name="resetUpload" />
						<input type="submit"
							value="<spring:message code="oct.s5.confirm" /> &raquo;" name="confirmUpload" />
					</div>
				</c:if>

			</form:form>
		</div>

		<%@ include file="./tiles/footer.jsp"%>
	</div>
</body>

</html>