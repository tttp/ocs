<%@page pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
						<tr>
							<td class="k">
								<label for="title"><spring:message code="oct.s4.title" /></label>
							</td>
							<td class="v">
							<c:choose>
									<c:when test="${editing&&!draftUploaded}">								
										<form:textarea path="title" id="title" cols="50" rows="2" readonly="${!editing}"/>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${oct_preview_lang_id!=null}">
												<c:out value="${form.lvTitle}"/>
											</c:when>
											<c:otherwise>
												<c:out value="${form.title}"/>	
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td class="k">
								<label for="subjectMatter"><spring:message code="oct.s4.subject" /></label>
							</td>
							<td class="v">
								<c:choose>
									<c:when test="${editing&&!draftUploaded}">
										<form:textarea path="subjectMatter" id="subjectMatter" cols="50" rows="4" readonly="${!editing}"/>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${oct_preview_lang_id!=null}">
												<c:out value="${form.lvSubjectMatter}"/>
											</c:when>
											<c:otherwise>
												<c:out value="${form.subjectMatter}"/>	
											</c:otherwise>
										</c:choose>						
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td class="k">
								<label for="objectives"><spring:message code="oct.s4.desc" /></label>
							</td>
							<td class="v">
								<c:choose>
									<c:when test="${editing&&!draftUploaded}">
										<form:textarea path="objectives" id="objectives" cols="50" rows="10" readonly="${!editing}"/>								
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${oct_preview_lang_id!=null}">
												<c:out value="${form.lvObjectives}"/>
											</c:when>
											<c:otherwise>
												<c:out value="${form.objectives}"/>	
											</c:otherwise>
										</c:choose>	
									</c:otherwise>
								</c:choose>
							</td>
						</tr>	
						<tr>
							<td class="k">
								<label for="website"><spring:message code="oct.s4.website" /></label></td>
							<td class="v">
									<c:choose>
										<c:when test="${editing&&!draftUploaded}">
											<form:input path="website" id="website"
												disabled="${!editing}" size="50"/>
										</c:when>
										<c:otherwise>
											<c:choose>
											<c:when test="${oct_preview_lang_id!=null}">
												<a href="<c:url value="${form.lvWebsite}" />"><c:out value="${form.lvWebsite}"/></a>
											</c:when>
											<c:otherwise>
												<a href="<c:url value="${form.website}" />" target="_blank"><c:out value="${form.website}" /></a>	
											</c:otherwise>
										</c:choose>	
																						
										</c:otherwise>
									</c:choose></td>
						</tr>
						