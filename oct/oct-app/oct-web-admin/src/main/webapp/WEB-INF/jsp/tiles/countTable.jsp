<%@page pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>

<div id="contry-distribution">
						<ul>
							<c:forEach items="${countsPerCountry}" var="countryCount">
								<li>
									<p class="country"><spring:message code="${countryCount.country.name}" /></p>
									<p class="count"><c:out value="${countryCount.count}" /></p>
								</li>
							</c:forEach>
						</ul>
					</div>