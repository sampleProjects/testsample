<div class="col-xs-12">
          			<div class="table-responsive">
						<table class="table table-hover">
							<thead>
								<tr>
									<g:sortableColumn params="${params}" property="code" title="${message(code: 'laborActivityGroup.code.label', default: 'Code')} (Activity Code Count)" defaultOrder="desc"/>
									<th><g:message code="laborActivityGroup.description.label" default= "Description"></g:message>
									<th><g:message code="laborActivityGroup.associatedLaborActivityCodes.label" default="Activity Codes" /></th>
									<th><g:message code="laborActivityGroup.pay.department.label" default="Pay Dept" /></th>
								</tr>
							</thead>
							<tbody>
								<g:if test="${laborActivityGroupInstanceList?.size() > 0}">
									<g:each in="${laborActivityGroupInstanceList}" status="i" var="laborActivityGroupInstance">
										<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
											<td>
												<g:link action="edit" id="${laborActivityGroupInstance.id}">${fieldValue(bean: laborActivityGroupInstance, field: "code")}</g:link>
												<span class="small-text">(${laborActivityGroupInstance?.laborActivityCodes?.size()})</span>
											</td>
											<td>${fieldValue(bean: laborActivityGroupInstance, field: "description")}</td>
											<td>
												${laborActivityGroupInstance?.laborActivityCodes*.code.join(", ")}
											</td>
											<td>${grailsApplication.config.departmentMap.get(laborActivityGroupInstance?.code)}</td>
										</tr>
									</g:each>
								</g:if>
								<g:else>
									<tr>
										<td colspan="2" class="no-record-found" ><g:message code="default.record.not.found" /></td>
									</tr>
								</g:else>
							</tbody>
						</table>
					</div>
					<g:render template="/common/returnLink" model="['returnAction': 'create', 'returnController': 'LaborActivityGroup', 'messageCode':'etech.LaborActivityGroup.return.screen', 'defaultMessage': 'Return to Labor Activity Department Screen']"></g:render>
							
							<div class="pagination">
									<g:paginate total="${laborActivityGroupInstanceCount ?: 0}" params="${params}"/>
							</div>	
				</div>