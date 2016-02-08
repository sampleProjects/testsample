<%@page import="org.solcorp.etech.CustomerController"%>
<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
<%@ page import="org.solcorp.etech.Customer" %>
<div class="col-xs-12">
          		 <div class="table-responsive">
				   <table class="table table-hover"> 
						<thead>
								<tr>					
									<g:sortableColumn property="postedDate" params="${params}" title="${message(code: 'etech.hh.expense.posted.date.label', default: 'Date')}" defaultOrder="asc" />									
									
									<th>Vendor ID</th>
									
									<th>Vendor Name</th>
								
									<th>Invoice#</th>
									
									<th>Amount</th>
									
									<th>Expense Code</th>
									
									<th>GL Acct</th>
									
								</tr>
							</thead>
							<tbody>
							<g:if test="${hhExpenseMasterInstanceList?.size() > 0}">	
								<g:each in="${hhExpenseMasterInstanceList}" status="i" var="hhExpenseMasterInstance">
									<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
										
										<td>
											${DateFormatUtils.getStringFromDate(hhExpenseMasterInstance?.postedDate)}
										</td>
											
										<td>${hhExpenseMasterInstance.vendorId}</td>
										
										<td>${hhExpenseMasterInstance.name1}</td>
										
										<td>${hhExpenseMasterInstance.invoiceId}</td>
										
										<td>${hhExpenseMasterInstance?.monetaryAmount}</td>
										
										<td>${hhExpenseMasterInstance?.descr}</td>
										
										<td>${hhExpenseMasterInstance?.account}</td>
	
									</tr>
								</g:each>
							</g:if>
							<g:else>
								<tr>
									<td colspan="7"><g:message code="default.record.not.found" /></td>
								</tr>
							</g:else>
							</tbody>
						</table>
						</div>
								<g:render template="/common/returnLink" model="['returnAction': 'edit', 'returnController': 'project', 'messageCode':'etech.Project.return.screen', 'defaultMessage': 'Return to Project Screen', 'linkId': params.id]"></g:render>
								<div class="pagination">
									<g:paginate total="${hhExpenseMasterInstanceCount ?: 0}"  params="${params}"/>
								</div>
						</div>