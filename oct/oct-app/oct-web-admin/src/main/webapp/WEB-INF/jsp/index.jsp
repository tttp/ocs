<%@page pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="./tiles/head.jsp"%>
</head>
<body>
	<div id="page">
		<%@ include file="./tiles/header.jsp"%>

		<div id="content">
			<p>
				<spring:message code="oct.s2.welcome" />
			</p>
			
			<table id="home-nav">
				<tr>
					<td>
						<h1 class="setup"><a href="./systemprefs.do"><spring:message code="oct.menu.setup" /></a></h1>
						<spring:message code="oct.s2.intro.2" />
					</td>
					<td>
						<h1 class="export"><a href="./export.do"><spring:message code="oct.menu.monitor.export" /></a></h1>
						<spring:message code="oct.s2.intro.4" />
					</td>
					<td>
						<h1 class="systemstatus"><a href="./systemstatus.do"><spring:message code="oct.menu.system.status" /></a></h1>
						<spring:message code="oct.s2.intro.5" />
					</td>
				</tr>
			</table>
			<p>
				<spring:message code="oct.s2.msg.1" />
			</p>
			<p>
				<spring:message code="oct.s2.msg.3" arguments="<a href=./systemprefs.do>${sysPrefs}</a>"/>				
			</p>
			<p>
				<spring:message code="oct.s2.msg.4" arguments="<a href=./systemstatus.do>${state}</a>"/>				
			</p>
		</div>
		<%@ include file="./tiles/footer.jsp"%>
	</div>
</body>

</html>