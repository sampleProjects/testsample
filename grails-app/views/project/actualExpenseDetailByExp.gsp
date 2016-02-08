<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
<%@page import="org.solcorp.etech.utils.MiscUtils"%>
<%@ page import="org.solcorp.etech.PermissionType" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<title><g:message code="etech.project.actual.labor.list.label" default="Actual Expense List" /></title>
	<content tag="pageName">projectPage</content>
</head>
<body>
	<div class="navigation">	
		<h3> <g:message code="etech.project.expense.label" default="Expense for" />  ${projectDetailInstance?.service?.product?.code} / ${projectDetailInstance?.service?.code} ( ${projectDetailInstance?.projectProductDetail?.project?.name} ) </h3>		
	</div>
	
	<div class="col-sm-12" role="main">			
		<div class="white-bg">		
			<div class="title">			
				<h2> <g:message code="etech.project.actual.labor.list.label" default="Actual Expense List" />	</h2>								
			</div>		          		                      
          	<div>          	
				<div class="col-xs-12">
					<div class="table-responsive">					
						<table class="table table-hover">							
							<thead>
								<th>Expense Code</th>
								<th>Date</th>							
								<th>Vendor ID</th>									
								<th>Vendor Name</th>								
								<th>Invoice#</th>
								<th>GL Acct</th>
								<th>Amount</th>								 
						</thead>						
						<tbody>
						<g:if test="${actualExpenseList?.size() > 0}">	
								<g:each in="${actualExpenseList}" status="i" var="hhExpenseMasterInstance">
									<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">	
										<td>${hhExpenseMasterInstance.expense?.code}</td>	
										<td>${DateFormatUtils.getStringFromDate(hhExpenseMasterInstance?.postedDate)}</td>																			
										<td>${hhExpenseMasterInstance.vendorId}</td>										
										<td>${hhExpenseMasterInstance.name1}</td>										
										<td>${hhExpenseMasterInstance.invoiceId}</td>										
										<td>${hhExpenseMasterInstance?.account}</td>
										<td>${hhExpenseMasterInstance?.monetaryAmount}</td>	
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
				<g:render template="/common/returnLink" model="['returnAction': 'getAllServiceActualExpenseList', 'returnController': 'project', 'messageCode':'etech.project.expense.list.return.screen', 'defaultMessage': 'Return to Project Expense Screen', 'linkId': projectDetailInstance?.id]"></g:render>				
			</div>		
			</div>						
		</div>
	</div>
</body>
</html>