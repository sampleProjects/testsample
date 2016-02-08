
	
			<div class="col-xs-12">
			<div class="table-responsive">
					<table class="table table-hover">
						<thead>
							<tr>

								<g:sortableColumn property="code" params="${params}"
									title="${message(code: 'etech.COE.code.label', default: 'Code')}" defaultOrder="desc" />

								<th><g:message code="etech.COE.description.label"
										default="Description" /></th>

							</tr>
						</thead>
						<tbody>
						<g:if test="${COEInstanceList?.size() > 0}">	
							<g:each in="${COEInstanceList}" status="i" var="COEInstance">
								<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

									<td><g:link action="edit" id="${COEInstance.id}">
											${fieldValue(bean: COEInstance, field: "code")}
										</g:link></td>

									<td>
										${fieldValue(bean: COEInstance, field: "description")}
									</td>

								</tr>
							</g:each>
						</g:if>
						<g:else>	
							<tr><td colspan="4" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
						</g:else>
						</tbody>
					</table>
				</div>
				<g:render template="/common/returnLink" model="['returnAction': 'create', 'returnController': 'COE', 'messageCode':'etech.COE.return.screen', 'defaultMessage': 'Return to COE Screen']"></g:render>
				<div class="pagination">
					<g:paginate total="${COEInstanceCount ?: 0}" params="${params}" />
				</div>
			</div>