<div class="col-xs-12">
          		 <div class="table-responsive">
				   <table class="table table-hover">
					<thead>
						<tr>					
							<g:sortableColumn property="code" params="${params}" title="${message(code: 'etech.department.code.label', default: 'Code')}" defaultOrder="desc" />
							
							<th>Labor Department</th>
							
							<th>Description</th>
																				
					   </tr>
					</thead>
					<tbody>
						<g:if test="${departmentInstanceList?.size() > 0}">	
							<g:each in="${departmentInstanceList}" status="i" var="departmentInstance">
								<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
								
									<td><g:link action="edit" id="${departmentInstance.id}">${fieldValue(bean: departmentInstance, field: "code")}</g:link></td>
									
									<td>${fieldValue(bean: departmentInstance?.laborActivityGroup, field: "code")}</td>
									
									<td>${fieldValue(bean: departmentInstance, field: "description")}</td>
												
								</tr>
							</g:each>
						</g:if>
						<g:else>	
							<tr><td colspan="2" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
						</g:else>
					</tbody>
				</table>			
				</div>
				
				<g:render template="/common/returnLink" model="['returnAction': 'create', 'returnController': 'department', 'messageCode':'etech.department.return.screen', 'defaultMessage': 'Return to Pay Department Screen']"></g:render>
				
				<div class="pagination">
					<g:paginate total="${departmentInstanceCount ?: 0}"  params="${params}"/>
				</div>
				
				
			</div>