<%@page pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>

<div id="contry-distribution">
						<ul>
							<oct:items>																
							<c:forEach items="${countsPerCountry}" var="countryCount">
								<oct:item>
								<li>
									<p class="country"><oct:property><spring:message code="${countryCount.country.name}" /></oct:property></p>
									<p class="count"><c:out value="${countryCount.count}" /></p>
								</li>
								</oct:item>
							</c:forEach>
							</oct:items>																
						</ul>
					</div>