<%@page import="org.solcorp.etech.CustomerController"%>
<%@ page import="org.solcorp.etech.Customer" %>
<div class="col-xs-12">
          		 <div class="table-responsive">
				   <table class="table table-hover"> 
						<thead>
								<tr>					
									<g:sortableColumn property="code" params="${params}" title="${message(code: 'customer.code.label', default: 'Code')}" defaultOrder="desc" />									
									
									<th>Name</th>
									
									<th>Contact</th>
									
									<th>Country</th>
									
									<th><g:message code="etech.customer.imported.by.job.label" default= "Imported By Job"/></th>
									
								</tr>
							</thead>
							<tbody>
							<g:if test="${customerInstanceList?.size() > 0}">	
								<g:each in="${customerInstanceList}" status="i" var="customerInstance">
									<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
										
										<g:set var="billingRecord" value="${new CustomerController().getBillingRecords(customerInstance.id)}" />
										
										<td><g:link action="edit" id="${customerInstance.id}">${fieldValue(bean: customerInstance, field: "code")}</g:link></td>
											
										<td>${customerInstance.name}</td>
										
										<td>${billingRecord.contact}</td>
										
										<td>${billingRecord.address}</td>
										
										<td>${customerInstance?.isSystemJobUser() ? 'Yes' : 'No' }</td>
										
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
								<g:render template="/common/returnLink" model="['returnAction': 'create', 'returnController': 'Customer', 'messageCode':'etech.Customer.return.screen', 'defaultMessage': 'Return to Customer Screen']"></g:render>
								<div class="pagination">
									<g:paginate total="${customerInstanceCount ?: 0}"  params="${params}"/>
								</div>
						</div>