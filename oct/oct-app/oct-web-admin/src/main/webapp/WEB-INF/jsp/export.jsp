<%@page pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="oct" uri="oct"%>

<%@ page import="eu.europa.ec.eci.oct.admin.model.SignatureId.DeletionStatus" %>
<%@ page import="eu.europa.ec.eci.oct.admin.model.SignatureId" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="./tiles/head.jsp"%>
</head>
<body>
	<div id="page">
		<%@ include file="./tiles/header.jsp"%>

		<div id="content">
			<div class="export-title">
				<h2 class="page-title">
					<spring:message code="oct.s6.title" />
				</h2>
			</div>
			<p>
				<spring:message code="oct.s6.info.1" />
			</p>

			<form:form method="post" modelAttribute="form">
				<oct:requestToken/>
				<form:errors path="*" cssClass="error" element="div" />
				
				<c:if test="${oct_success_message!=null}">
					<div id="msgContainer">
					<c:forEach var="message" begin="0" items="${oct_success_message}"> 
						<div class="msg">
							<c:out value="${message}"/>
						</div>
  					</c:forEach>
					</div>
				</c:if>
				<c:if test="${afterDeletion}">
					<div id="msgContainer">						
							<% 
							java.util.List<SignatureId> sigDeletionResult = (java.util.List<SignatureId>)request.getAttribute("sigDeletionResult");
							for(SignatureId sigId: sigDeletionResult){							
								
							  if (sigId.getDeletionStatus() == DeletionStatus.SUCCES) { %>
									<div class="msg">
										<spring:message code="oct.export.delete.success" arguments="<%=sigId.getToken()%>"/>
									</div>  
							<% } else if (sigId.getDeletionStatus() == DeletionStatus.TOKEN_NOT_PROVIDED) { 
								String date = new java.text.SimpleDateFormat("yyyy/MM/dd").format(sigId.getDate());%>							
									<div class="error">
										<spring:message code="oct.export.delete.tokennotprovided" arguments="<%=date%>"/>
									</div>  
							<% } else if (sigId.getDeletionStatus() == DeletionStatus.FAILURE) { %>
									<div class="error">
										<spring:message code="oct.export.delete.failure" arguments="<%=sigId.getToken()%>"/>
									</div>  
							<% } 
							}%>														
					</div>
				</c:if>
				
				<c:if test="${oct_system_state!='OPERATIONAL'}"><spring:message code="oct.s6.info.2" var="countLegend"/></c:if>
				<c:if test="${oct_system_state=='OPERATIONAL'}"><spring:message code="oct.s16.info.2" var="countLegend"/></c:if>
				<fieldset>
					<legend>${countLegend}</legend>
					<%@ include file="./tiles/countTable.jsp" %>
					<div class="clear"></div>
					<div id="total">
						<spring:message code="oct.s6.total" />: <span><c:out value="${totalCount}" /></span>
						<c:choose>
							<c:when test="${totalCount==0}">
								<c:set var="exportAllDisabled" value="disabled" />
							</c:when>
							<c:otherwise>
								<c:set var="exportAllDisabled" value=""/>
							</c:otherwise>
						</c:choose>						
						<input type="submit" class="btn" ${exportAllDisabled} value="<spring:message code="oct.s6.export.all" /> &raquo;" name="exportAllAction" />
					</div>
				</fieldset>
				
				<c:if test="${oct_system_state!='OPERATIONAL'}"><spring:message code="oct.s6.report.period.country" var="reportLegend"/></c:if>
				<c:if test="${oct_system_state=='OPERATIONAL'}"><spring:message code="oct.s17.report.period.country" var="reportLegend"/></c:if>				
				<fieldset>
					<legend>${reportLegend}</legend> 					
					
					<table cellspacing="0" cellpadding="0" class="fields full mini">
						<tr>
							<td>
								<label for="startDate"><spring:message code="oct.s6.stms.1" /></label>
							</td>
							<td>
								<form:input path="startDate" id="startDate" rel="date"/> 
							</td>
							<td>
								<label for="endDate"><spring:message code="oct.s6.and" /></label>
							</td>
							<td>
								<form:input path="endDate" id="endDate" rel="date"/> 
							</td>						
							<td>
								<label for="country"><spring:message code="oct.s6.in" /></label>
							</td>
							<td>
								<spring:bind path="country">
								<select id="country" name="country">
									<option value="-1"><spring:message code="oct.export.allcountries"/></option>
									<c:forEach items="${countries}" var="country">	
										<option value="<c:out value="${country.code}" />"><spring:message code="${country.name}"/></option>
									</c:forEach>
								</select>
								</spring:bind>
							</td>
							<td class="right">
								<input type="submit" class="btn" value="<spring:message code="oct.s6.count" /> &raquo;" name="countAction" />						
							</td>
						</tr>
					</table>
					
					<c:if test="${count != null}">
						<fieldset class="results">
							<legend><spring:message code="oct.s6.result" /></legend>
							
							<table cellspacing="0" cellpadding="0" class="fields full">
								<tr>
									<td>
										<c:if test="${form.country.id != null}">
											<spring:message code="${form.country.name}" var="countrName" />
										</c:if>
										<c:if test="${form.country.id == null}">
											<spring:message code="oct.export.allcountries" var="countrName" />
										</c:if>
										<c:if test="${form.startDate != null}">				
											<spring:message code="oct.s6.result.3" arguments="${count}, ${countrName}, ${startDate}, ${endDate}" />
										</c:if>
										<c:if test="${form.startDate == null}">
											<spring:message code="oct.s6.result.1" arguments="${count}, ${countrName}"/>
										</c:if>
									</td>		
									<td class="right">
										<c:choose>
											<c:when test="${count==0}">
												<c:set var="exportDisabled" value="disabled" />
											</c:when>
											<c:otherwise>
												<c:set var="exportlDisabled" value=""/>
											</c:otherwise>
										</c:choose>
										<input type="submit" class="btn" ${exportDisabled} value="<spring:message code="oct.s6.export" /> &raquo;" name="exportAction" />						
									</td>					
								</tr>
							</table>
						</fieldset>
					</c:if>
				</fieldset>
				
				<c:if test="${delete}">
					<div id="mask"></div>
					<div id="question">
						<spring:message code="oct.s6.delete.confirm" />
						<div id="buttons">
							<input type="submit" value="<spring:message code="oct.s6.ok" />" name="confirmDeleteAction" />
							<input type="submit" value="<spring:message code="oct.s6.cancel" />" name="resetDelete" />
						</div>
					</div>
				</c:if>

				<c:if test="${deleteAll}">
					<div id="mask"></div>
					<div id="question">
						<spring:message code="oct.s6.delete.all.confirm" />
						<div id="buttons">
							<input type="submit" value="<spring:message code="oct.s6.ok" />" name="confirmDeleteAllAction" />
							<input type="submit" value="<spring:message code="oct.s6.cancel" />" name="resetDelete" />
						</div>
					</div>
				</c:if>
				
				<fieldset>
					<legend><spring:message code="oct.s6.delete" /></legend>
					
					<table cellspacing="0" cellpadding="0" class="fields full">
							<c:forEach items="${form.signatureIds}" var="sigId" varStatus="row">
							<tr>
								<spring:bind path="form.signatureIds[${row.index}].token">
									<td class="k">
										<label for="<c:out value="${status.expression}"/>"><spring:message code="oct.s6.signatory.number" /></label>
										<span class="required">*</span>
									</td>
									<td class="v">
										<input type="text" id="<c:out value="${status.expression}"/>" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
									</td>
								</spring:bind>
								<spring:bind path="form.signatureIds[${row.index}].date">
									<td class="k">
										<label for="<c:out value="${status.expression}"/>"><spring:message code="oct.s6.reg.date" /></label>
									</td>
									<td class="v">
										<input type="text" id="<c:out value="${status.expression}"/>" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" rel="date"/>
									</td>
								</spring:bind>
							</tr>								
							</c:forEach>
						
						
						<tr>							
							<td colspan="4" class="right">
								<input type="submit" class="btn" value="<spring:message code="oct.s6.delete" /> &raquo;" name="deleteSignatureAction" />						
							</td>
							
						</tr>
					</table>
				</fieldset>				
				
			</form:form>
		</div>

		<%@ include file="./tiles/footer.jsp"%>
	</div>
</body>

</html>