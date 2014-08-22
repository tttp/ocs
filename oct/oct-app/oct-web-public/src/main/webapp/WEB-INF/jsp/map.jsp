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
	<title><spring:message code="oct.menu.map" /> - <spring:message code="oct.title" /></title>
<%@ include file="./tiles/head.jsp"%>
	<link href="./css/map/jqvmap.css" media="screen" rel="stylesheet" type="text/css" />
    <script src="./js/map/jquery.vmap.js" type="text/javascript"></script>
    <script src="./js/map/maps/jquery.vmap.europe.js" type="text/javascript"></script>
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
				<spring:message code="oct.menu.map" />
				<br />
				<span lang="<c:out value="${initiativeLang}" />"><c:out value="${oct_initiative_description.title}" /></span>
			</h1>
			
			 <div id="map"></div>
			 
			 <div id="definition">
			 	<p class="total"><spring:message code="oct.screen.map.total" />: <span id="total"></span></p>
			 	
			 	<spring:message code="oct.screen.map.threshold.defined" />
			 </div>
			 
			 <div id="legend">
			 	<p><spring:message code="oct.screen.map.legend.green" /> <span class="green">&nbsp;</span></p>
			 	<p><spring:message code="oct.screen.map.legend.blue" /> <span class="blue">&nbsp;</span></p>
			 	<p><spring:message code="oct.screen.map.legend.gray" /> <span class="grey">&nbsp;</span></p>
			 	<p><spring:message code="oct.screen.map.legend.rest" /> <span class="rest">&nbsp;</span></p>
			 </div>
			</c:if>
		</div>

		<%@ include file="./tiles/footer.jsp"%>
	</div>
</body>

</html>