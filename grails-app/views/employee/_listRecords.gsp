<div class="col-xs-12">
          		 <div class="table-responsive">
				   <table class="table table-hover">
					<thead>
						<tr>					
							<g:sortableColumn property="code" params="${params}" title="${message(code: 'etech.user.employee.id.label', default: 'ID')}" defaultOrder="desc" />
														
							<th><g:message code="etech.employee.firstName.label" default= "First Name"/></th>
									
							<th><g:message code="etech.employee.lastName.label" default= "Last Name"/></th>		
							
							<th><g:message code="etech.employee.payDept.label" default="Pay Departmen" /></th>
							
							<th><g:message code="etech.employee.LaborActivityGroup.label" default="Labor Dept" /></th>
							
							<th><g:message code="etech.employee.laborActivityCode.label" default="Labor act code" /></th>

							<th><g:message code="etech.employee.status.label" default="Status" /></th>
					   </tr>
					</thead>
					<tbody>
						<g:if test="${employeeInstanceList?.size() > 0}">	
							<g:each in="${employeeInstanceList}" status="i" var="employeeInstance">
								<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
								
									<td><g:link action="edit" id="${employeeInstance.id}">${fieldValue(bean: employeeInstance, field: "code")}</g:link></td>
								
									<td>${fieldValue(bean: employeeInstance, field: "firstName")}</td>
								
									<td>${fieldValue(bean: employeeInstance, field: "lastName")}</td>
									
									<td>
										${employeeInstance?.laborActivityGroup?.code}
									</td>
	
									<td>
										${employeeInstance?.laborActivityCode?.code}
									</td>
									
									<td>
										${employeeInstance?.payDept?.code}
									</td>
									
									<td>
										${fieldValue(bean: employeeInstance, field: "status")}
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
				<g:render template="/common/returnLink" model="['returnAction': 'create', 'returnController': 'employee', 'messageCode':'etech.employee.return.screen', 'defaultMessage': 'Return to Employee Screen']"></g:render>
				<div class="pagination">
					<g:paginate total="${employeeInstanceCount ?: 0}"  params="${params}"/>
				</div>
			</div>