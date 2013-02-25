<%@page pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table cellspacing="2" cellpadding="0" class="preview">
	<c:forEach items="${languageVersions}" var="description" varStatus="rowCounter">
			<c:if test="${rowCounter.count % 4 == 1}">
			<tr>
			</c:if>
				<c:choose>
					<c:when test="${description.language.id==oct_preview_lang_id}">
						<c:set var="tdClass" value="active" />
					</c:when>
					<c:otherwise>
						<c:set var="tdClass" value="inactive" />
					</c:otherwise>
				</c:choose>
				<td class="<c:out value="${tdClass}" />">
					<c:choose>
						<c:when test="${draftUploaded}">
							<c:set var="draftParam" value="&draft=true" />
						</c:when>
						<c:otherwise>
							<c:set var="draftParam" value="" />
						</c:otherwise>
					</c:choose>
					<a	href="<c:url value="${oct_preview_url}?preview=true&langId=${description.language.id}${draftParam}#initdetails" />"><spring:message code="${description.language.name}"/></a>
				</td>
			<c:if test="${rowCounter.count % 4 == 0}">
			</tr>
			</c:if>
		</c:forEach>

	<%-- empty cells in order to complete the table --%>
	<c:set var="remainingCells" value="${fn:length(languageVersions) % 4}" />
	<c:if test="${remainingCells ne 0}">
		<c:forEach begin="${remainingCells+1}" end="4">
			<td>&nbsp;</td>
		</c:forEach>
		</tr>
	</c:if>

</table>
