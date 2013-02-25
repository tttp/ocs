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

		<div id="content-login">
			
			
			<form action="<c:url value="/j_spring_security_check" />" method="POST">
				<oct:requestToken/>
				<c:if test="${not empty param.error}">
					<div class="error">
						<spring:message code="oct.login.form.error" />
					</div>
				</c:if>
				
				<fieldset class="home">					
					<table cellspacing="0" cellpadding="0" class="fields">
						<tr>
							<td class="k">
								<label for="j_username"><spring:message code="oct.s1.user.name" /></label>
							</td>
							<td class="v">
								<input type="text" name="j_username" id="j_username" value=""/>
							</td>
						</tr>
						<tr>
							<td class="k">
								<label for="j_password"><spring:message code="oct.s1.password" /></label>
							</td>
							<td class="v">
								<input type="password" name="j_password" id="j_password" value=""/>
							</td>
						</tr>
						<tr>
							<td class="k">
								<label for="challenge"><spring:message code="oct.s1.msg.decrypt" /></label>
							</td>
							<td class="v">
								<textarea rows="7" cols="62" name="challenge" readonly="readonly"><c:out value="${challenge}"></c:out></textarea>
							</td>
						</tr>
						<tr>
							<td class="k">
								<label for="j_challange_response"><spring:message code="oct.s1.result" /></label>
							</td>
							<td class="v small-textarea">
								<textarea rows="3" cols="50" name="j_challange_response" id="j_challange_response"></textarea>
							</td>
						</tr>
					</table>
				</fieldset>
					
				<div id="buttons">
					<input type="submit" value="<spring:message code="oct.s1.login" />" />
				</div>
			</form>
		</div>

		<%@ include file="./tiles/footer.jsp"%>
	</div>
</body>

</html>