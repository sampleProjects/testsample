<div class="col-xs-12">
	<div class="table-responsive">
		<table class="table table-hover">
			<thead>
				<tr>
					<g:sortableColumn property="code" params="${params}" title="${message(code: 'industry.code.label', default: 'Code')}" defaultOrder="desc" />
					<th><g:message code="industry.name.label" default="Name" /></th>
					<th><g:message code="industry.description.label" default="Description" /></th>
				</tr>
			</thead>
			<tbody>
				<g:if test="${industryInstanceList?.size() > 0}">	
					<g:each in="${industryInstanceList}" status="i" var="industryInstance">
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							<td><g:link action="edit" id="${industryInstance.id}">${fieldValue(bean: industryInstance, field: "code")}</g:link></td>
							<td>${fieldValue(bean: industryInstance, field: "name")}</td>
							<td>${fieldValue(bean: industryInstance, field: "description")}</td>
						</tr>
					</g:each>
				</g:if>
				<g:else>	
					<tr>
						<td colspan="3" class="no-record-found"><g:message code="default.record.not.found" /></td>
					</tr>
				</g:else>
			</tbody>
		</table>
	</div>
	
	<g:render template="/common/returnLink" model="['returnAction': 'create', 'returnController': 'industry', 'messageCode': 'industry.return.screen', 'defaultMessage': 'Return to Industry Screen']"></g:render>
	<div class="pagination">
		<g:paginate total="${industryInstanceCount ?: 0}" params="${params}" />
	</div>
</div>