<%@ page import="org.solcorp.etech.Address" %>
	<div class="col-xs-12">
          		 <div class="table-responsive">
				   <table class="table table-hover">
						<thead>
								<tr>					
									<g:sortableColumn params="['customer.id': params.('customer.id')]" property="line1" title="${message(code: 'address.line1.label', default: 'Address 1')}"  defaultOrder="desc"/>
								
									<th>Address 2</th>
													
									<th>City</th>
													
									<th>State</th>
									
									<th>Zip code</th>									
								</tr>
							</thead>
							<tbody>
							<g:if test="${addressInstanceList?.size() > 0}">	
								<g:each in="${addressInstanceList}" status="i" var="AddressInstance">
									<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
									
										<td><g:link action="edit" id="${AddressInstance.id}">${fieldValue(bean: AddressInstance, field: "line1")}</g:link></td>
									
										<td>${fieldValue(bean: AddressInstance, field: "line2")}</td>
									
										<td>${fieldValue(bean: AddressInstance, field: "city")}</td>
										
										<td>${fieldValue(bean: AddressInstance, field: "state")}</td>
										
										<td>${fieldValue(bean: AddressInstance, field: "zip")}</td>					
									</tr>
								</g:each>
							</g:if>
							<g:else>
								<tr>
									<td colspan="4"  ><g:message code="default.record.not.found" /></td>
								</tr>
							</g:else>
							</tbody>
						</table>
					</div>
					<g:render template="/common/returnLink" model="['returnAction': 'create', 'returnController': 'address', 'messageCode':'etech.address.return.screen', 'defaultMessage': 'Return to Address Screen', 'linkParam': ['customer.id': params.('customer.id')]]"></g:render>

						<div class="pagination">
							<g:paginate total="${addressInstanceCount ?: 0}"  params="${params}"/>
						</div>
					</div>