<%@page pageEncoding="UTF-8"%>
<%@page contentType="application/json;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
{
	"messages": {
		"collected": "<spring:message code="oct.screen.map.collectedsignatures" />",
		"threshold": "<spring:message code="oct.screen.map.threshold" />",
		"percentage": "<spring:message code="oct.screen.map.percentage" />"
	},
	"total": "${total}",
	"countries": [
	<c:forEach items="${mapData}" var="row" varStatus="current">{
			"code": "${row.country.code}",
			"name": "<spring:message code="${row.country.name}"/>",
			"count": "${row.count}",
			"threshold": "${row.country.threshold}",
			"percentage": "<fmt:formatNumber value="${row.count / row.country.threshold * 100}" maxFractionDigits="2" />" 
		}<c:if test="${current.index < fn:length(mapData) - 1}">,</c:if>
	</c:forEach>
]}