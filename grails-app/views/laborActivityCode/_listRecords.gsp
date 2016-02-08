<div class="col-xs-12">
<div class="table-responsive">

	<table class="table table-hover">
	
		<thead>
		
		<tr>
		
			<g:sortableColumn params="${params}" property="code" title="Code" defaultOrder="desc" />
			
			<th>Description</th>
			
			<th>Labor Activity Group</th>
			
			<th class="text-right">Standard Rate</th>
			
			<th class="text-right">Overhead %</th>
			
			<th>Operations</th>
			
			<th><g:message code="laborActivityCode.pay.department.label" default="Pay Dept" /></th>
			
		</tr>
		
		</thead>
		
		<tbody>
			<g:if test="${laborActivityCodeInstanceList?.size() > 0}">
			
				<g:each in="${laborActivityCodeInstanceList}" status="i" var="laborActivityCodeInstance">
				
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="edit" id="${laborActivityCodeInstance.id}">${fieldValue(bean: laborActivityCodeInstance, field: "code")}</g:link></td>
						
						<td>${fieldValue(bean: laborActivityCodeInstance, field: "description")}</td>
						
						<td>${laborActivityCodeInstance?.laborActivityGroup?.code}</td>
						
						<td class="text-right">${laborActivityCodeInstance?.standardRate}</td>
											
						<td class="text-right">${laborActivityCodeInstance?.overHead}</td>
						
						<td>
							<g:if test="${laborActivityCodeInstance?.operations.equals('Y')}">
								Yes
							</g:if>
							<g:elseif test="${laborActivityCodeInstance?.operations.equals('N')}">
								No
							</g:elseif>
						</td>
						<td>${grailsApplication.config.departmentMap.get(laborActivityCodeInstance?.laborActivityGroup?.code)}</td>
					</tr>
					
				</g:each>
				
			</g:if>
			
			<g:else>
			
				<tr><td colspan="6"  class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
				
			</g:else>
			
		</tbody>
		
	</table>
	
</div>

	
	<g:render template="/common/returnLink" model="['returnAction': 'create', 'returnController': 'LaborActivityCode', 'messageCode':'etech.LaborActivityCode.return.screen', 'defaultMessage': 'Return to Labor Activity Code Screen']"></g:render>
	<div class="pagination">
		<g:paginate total="${laborActivityCodeInstanceCount ?: 0}"  params="${params}"/>
	</div>
</div>
