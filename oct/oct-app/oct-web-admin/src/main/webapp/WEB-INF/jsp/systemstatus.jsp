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
			<div class="systemstatus-title">
				<h2 class="page-title">
					<spring:message code="oct.s7.sys.status" />
				</h2>
			</div>
			<form:form method="post" modelAttribute="form" >
				<oct:requestToken/>
				<c:if test="${collectorConfirm==true}">
					<div id="mask"></div>
					<div id="question">
						<h2><spring:message code="oct.s8.sys.mode.msg.1" /></h2>
						<c:if test="${oct_system_state!='OPERATIONAL'}"><spring:message code="oct.s8.sys.mode.msg.2" /></c:if>
						<c:if test="${oct_system_state=='OPERATIONAL'}"><spring:message code="oct.s19.msg.2" /></c:if>
						<div id="buttons">
							<input type="submit" value="<spring:message code="oct.s8.confirm" />" name="confirmCollector" />
							<input type="submit" value="<spring:message code="oct.s8.cancel" />" name="resetCollector" />
						</div>
					</div>
				</c:if>
				<c:if test="${collectorConfirm==false}">
					<div id="mask"></div>
					<div id="question">
						<h2><spring:message code="oct.s9.sys.mode.msg.1" /></h2>
						<c:if test="${oct_system_state!='OPERATIONAL'}">
							<p><spring:message code="oct.s9.sys.mode.msg.2" /></p>
							<p><spring:message code="oct.s9.sys.mode.msg.3" /></p>
							<ul>
								<li><spring:message code="oct.s9.sys.mode.msg.4" /></li>
								<li><spring:message code="oct.s9.sys.mode.msg.5" /></li>
							</ul>
						</c:if>
						<c:if test="${oct_system_state=='OPERATIONAL'}">
							<p><spring:message code="oct.s18.msg.2" /></p>
							<p><spring:message code="oct.s18.msg.3" /></p>
							<ul>
								<li><spring:message code="oct.s18.msg.4" /></li>
								<li><spring:message code="oct.s18.msg.5" /></li>
							</ul>
						</c:if>
						<div id="buttons">
							<input type="submit" value="<spring:message code="oct.s8.confirm" />" name="confirmCollector" />
							<input type="submit" value="<spring:message code="oct.s8.cancel" />" name="resetCollector" />
						</div>
					</div>
				</c:if>
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
				
				
				<fieldset>
					<legend><spring:message code="oct.s7.collection.status" /></legend>
					
					<table cellspacing="0" cellpadding="0" class="fields">		
							
						<tr>
							<td>
								<spring:message code="oct.s7.collection.msg" />
							</td>
						</tr>
						<tr>							
							<td class="tab">
								<form:radiobutton path="collectorState" value="true"/> <spring:message code="oct.s7.on" />
							</td>
						</tr>
						<tr>							
							<td class="tab">
								<form:radiobutton path="collectorState" value="false"/> <spring:message code="oct.s7.off" />
							</td>
						</tr>	
						<tr>
							<td class="buttons">
								<input type="submit" value="<spring:message code="oct.s6.ok" /> &raquo;" name="collector" />
							</td>
						</tr>
					</table>
				</fieldset>
			</form:form>
		
			<form:form method="post" modelAttribute="form" enctype="multipart/form-data" >
				<oct:requestToken/>	
				<fieldset>
					<legend><spring:message code="oct.s7.certificate" /></legend>
					
					
					<table cellspacing="0" cellpadding="0" class="fields">		
							
						<c:if test="${oct_system_state!='OPERATIONAL'}">
						
						<tr>
							<td colspan="2">
								<spring:message code="oct.s7.upload.msg.1" /> <spring:message code="oct.s7.upload.msg.2" /> <spring:message code="oct.s7.upload.msg.3" />
							</td>
						</tr>
						<tr>							
							<td class="buttons">
								<input type="file" name="certFile"/>							
								<input type="submit" value="<spring:message code="oct.s7.upload" />" name="uploadFile" />
							</td>
						</tr>
						</c:if>	
						<c:if test="${oct_cert!=null}">							
						<tr>							
							<td class="buttons">
								<c:out value="${oct_cert.fileName}"/> &nbsp;	
								<a href="<c:url value="./certificate.do" />" target="_blank"><spring:message code="oct.s17.view" /></a>
								 &nbsp;
							</td>
						</tr>
						</c:if>	
					</table>
				</fieldset>
			
			</form:form>
			
				
			<form:form method="post" modelAttribute="form" >
				<oct:requestToken/>
				<c:if test="${productionConfirm==true}">
					<div id="mask"></div>
					<div id="question">
						<h2><spring:message code="oct.s10.sys.mode.msg.1" /></h2>
						<p><spring:message code="oct.s10.sys.mode.msg.2" /></p>						
						<ul>
							<li><spring:message code="oct.s10.sys.mode.msg.3" /></li>
							<li><spring:message code="oct.s10.sys.mode.msg.4" /></li>
							<li><spring:message code="oct.s10.sys.mode.msg.5" /></li>
							<li><spring:message code="oct.s10.sys.mode.msg.6" /></li>
						</ul>
						<p><spring:message code="oct.s10.sys.mode.msg.7" /></p>				
						<div id="buttons">
							<input type="submit" value="<spring:message code="oct.s8.confirm" />" name="confirmProduction" />
							<input type="submit" value="<spring:message code="oct.s8.cancel" />" name="resetProduction" />
						</div>
					</div>
				</c:if>
				
				<fieldset>
					<legend><spring:message code="oct.s7.sys.mode" /></legend>
					
					<table cellspacing="0" cellpadding="0" class="fields">		
					<c:if test="${oct_system_state!='OPERATIONAL'}">
					
						<tr>
							<td>
								<p><spring:message code="oct.s7.sys.mode.msg.1" /></p>
								<p><b><spring:message code="oct.s7.sys.mode.msg.2" /></b></p>
								<p><b><spring:message code="oct.s7.sys.mode.msg.3" /></b></p>
							</td>
						</tr>
						<tr>							
							<td class="tab">
								<form:checkbox path="goIntoProduction"/> <spring:message code="oct.s7.sys.mode.online" />							
							</td>
						</tr>	
						<tr>							
							<td class="tab buttons">
								<input type="submit" value="<spring:message code="oct.s7.continue" /> &raquo;" name="production" />
							</td>
						</tr>	
					</c:if>
					<c:if test="${oct_system_state=='OPERATIONAL'}">
						<tr>
							<td>
								<p><spring:message code="oct.s17.msg" /></p>								
							</td>
						</tr>
					</c:if>
					</table>
				</fieldset>
				
			</form:form>
			
		</div>

		<%@ include file="./tiles/footer.jsp"%>
	</div>
</body>

</html>