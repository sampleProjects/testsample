<%@page import="org.solcorp.etech.CustomerController"%>
<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
<%@ page import="org.solcorp.etech.Customer" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<title><g:message code="etech.project.expense.list.label" default="Expense List" /></title>
	<content tag="pageName">projectPerformanceReportPage</content>
</head>
<body>
	<div class="navigation">	
		<h3> <g:message code="etech.project.actual.expense.employee.label" default="Actual Expense By Expense for" /> ${projectInstance?.name} (${projectInstance?.code})</h3>		
	</div>
	
	<div class="col-sm-12" role="main">			
		<div class="white-bg">		
			<div class="title">			
				<h2> <g:message code="etech.project.expense.list.label" default="Expense List" /></h2>			
			</div>    
          	<div>          	
				<div class="col-xs-12">				
					<div class="table-responsive">						
						<table class="table table-hover">						
							<thead>							
								<tr>		
									<th>Expense Code</th>									
									<th>Amount</th>
								</tr>
						</thead>						
						<tbody>						
						<g:if test="${actualExpenseInstacenList?.size() > 0}">	
								<g:each in="${actualExpenseInstacenList}" status="i" var="hhExpenseMasterInstance">
									<tr class="${(i % 2) == 0 ? 'even' : 'odd'}"> 
										<td> <g:link action="getAllActualExpenseDetailByExpenseCode" controller="report" params="[prodId: projectInstance?.id, expId: hhExpenseMasterInstance.expenseId,costOption: params?.costOption]">${hhExpenseMasterInstance.expenseCode}</g:link></td>
																											
										<td>${hhExpenseMasterInstance?.amount}</td>	
									</tr>
								</g:each>
								<tr class="report-labor-summary">
									<td><g:message code="etech.report.project.perf.totals" default="Totals"/> </td>
									<td>${totalAmount ?: 0.00}</td>
								</tr>
							</g:if>
							<g:else>
								<tr>
									<td colspan="2"><g:message code="default.record.not.found" /></td>
								</tr>
							</g:else>						
						</tbody>						
					</table>					
				</div>				
				<g:render template="/common/returnLink" model="['returnAction': 'getProjectPerformanceDetail', 'returnController': 'report', 'messageCode':'etech.project.performance.return.screen', 'defaultMessage': 'Return to Project Performance Screen', 'linkId': params?.id,'linkParam': ['costOption': params?.costOption]]"></g:render>				
			</div>		
			</div>						
		</div>
	</div>
</body>
</html>