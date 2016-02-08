<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
<%@page import="org.solcorp.etech.utils.MiscUtils"%>
<%@ page import="org.solcorp.etech.Constants" %>
<%@ page import="org.solcorp.etech.PermissionType" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<title><g:message code="etech.project.actual.labor.list.label" default="Actual Expense List" /></title>
	<content tag="pageName">projectPerformanceReportPage</content>
</head>
<body>
	<div class="navigation">	
		<h3> <g:message code="etech.project.expense.label" default="Expense for" /> ${projectInstance?.name} (${projectInstance?.code})</h3>		
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
								<g:set value="0.00" var="totalCost"></g:set>	
								<g:each in="${actualExpenseList}" status="i" var="hhExpenseMasterInstance">
									<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">	
										<td>${hhExpenseMasterInstance.expense?.code}</td>	
										<td>${DateFormatUtils.getStringFromDate(hhExpenseMasterInstance?.postedDate)}</td>																			
										<td>${hhExpenseMasterInstance.vendorId}</td>										
										<td>${hhExpenseMasterInstance.name1}</td>										
										<td>${hhExpenseMasterInstance.invoiceId}</td>										
										<td>${hhExpenseMasterInstance?.account}</td>
										<td>${hhExpenseMasterInstance?.monetaryAmount}</td>
										<g:set value="${hhExpenseMasterInstance?.monetaryAmount + totalCost.toBigDecimal()}" var="totalCost"></g:set>
									</tr>
								</g:each>
								<tr class="report-labor-summary">
									<td colspan="6"><g:message code="etech.report.project.perf.totals" default="Totals"/> </td>
									<td>${totalCost}</td>
								</tr>
							</g:if>
							<g:else>
								<tr>
									<td colspan="7"><g:message code="default.record.not.found" /></td>
								</tr>
							</g:else>
						</tbody>
					</table>
				</div>
				<g:if test="${Constants.REPORT_VIEW_EXP == params?.view}">
					<g:render template="/common/returnLink" model="['returnAction': 'getProjectExpenseSummary', 'returnController': 'report', 'messageCode':'etech.project.expense.summary.return.screen', 'defaultMessage': 'Return to Project Expense Summary', 'linkId': projectInstance?.id, 'linkParam': ['costOption': params?.costOption]]"></g:render>
				</g:if>
				<g:else>
					<g:render template="/common/returnLink" model="['returnAction': 'getAllActualExpenseList', 'returnController': 'report', 'messageCode':'etech.project.expense.list.return.screen', 'defaultMessage': 'Return to Project Expense Screen', 'linkId': projectInstance?.id, 'linkParam': ['costOption': params?.costOption]]"></g:render>
				</g:else>				
			</div>		
			</div>						
		</div>
	</div>
</body>
</html>