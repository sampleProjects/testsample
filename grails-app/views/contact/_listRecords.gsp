<div class="col-xs-12">
          		 <div class="table-responsive">
				   <table class="table table-hover">
						<thead>
								<tr>					
									<g:sortableColumn params="['customer.id': params.('customer.id')]" property="name" title="${message(code: 'contact.name.label', default: 'Name')}" defaultOrder="desc"/>
								
									<th>Type</th>
													
									<th>Phone</th>
													
									<th>Fax</th>
									
									<th>Cellular</th>
									
									<th>Email</th>							
								</tr>
							</thead>
							<tbody>
							<g:if test="${contactInstanceList?.size() > 0}">	
								<g:each in="${contactInstanceList}" status="i" var="contactInstance">
									<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
									
										<td><g:link action="edit" id="${contactInstance.id}">${fieldValue(bean: contactInstance, field: "name")}</g:link></td>
									
										<td>${fieldValue(bean: contactInstance, field: "type")}</td>
									
										<td>${fieldValue(bean: contactInstance, field: "phoneNumber")}</td>
										
										<td>${fieldValue(bean: contactInstance, field: "faxNumber")}</td>
										
										<td>${fieldValue(bean: contactInstance, field: "cellular")}</td>		
										
										<td>${fieldValue(bean: contactInstance, field: "emailAddress")}</td>			
									</tr>
								</g:each>
							</g:if>
							<g:else>
								<tr>
									<td colspan="4"><g:message code="default.record.not.found" /></td>
								</tr>
							</g:else>
							</tbody>
						</table>
						</div>
						<g:render template="/common/returnLink" model="['returnAction': 'create', 'returnController': 'contact', 'messageCode':'etech.contact.return.screen', 'defaultMessage': 'Return to Contact Screen', 'linkParam': ['customer.id': params.('customer.id')]]"></g:render>
								
								<div class="pagination">
									<g:paginate total="${contactInstanceCount ?: 0}"  params="${params}"/>
								</div>
						</div>