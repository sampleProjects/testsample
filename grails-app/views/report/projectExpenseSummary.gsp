<%@ page import="org.solcorp.etech.Constants" %>
<%@ page import="org.solcorp.etech.PermissionType" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">		
		<title><g:message code="etech.report.project.performance.expense.summary.title.label" default="Project Expense Summary" /></title>
		<content tag="pageName">projectPerformanceReportPage</content>		
	</head>
	<body>		
	<div class="navigation"> <h3> <g:message code="etech.report.project.performance.expense.summary.title.label" default="Project Expense Summary" /> </h3> </div>		
	<div class="white-bg">		 	
		<div class="title text-left"> <h2> <g:message code="etech.report.project.performance.expense.summary.title.label" default="Project Expense Summary" /> </h2> </div>		
			<div class="col-xs-12">
				<div class="table-responsive">
					<table class="table">
						<tr>
							<td class="performance-table"><g:message code="etech.report.project.perf.project.label" default="Project"/> #</td>
							<td>${projectInstance?.code}</td>						
							<td class="performance-table"><g:message code="etech.report.project.perf.project.name.label" default="Project Name"/></td>
							<td>${projectInstance?.name}</td>
						</tr>
					</table>
				</div>
					<div class="table-responsive">								
					<table class="table table-striped">
					<thead>
						<tr>
							<th><g:message code="etech.report.project.perf.expense.code.label" default="Expense Code"/> </th>
							<th><g:message code="etech.report.project.perf.planned.qty.label" default="Planned Qty"/> </th>
							<th><g:message code="etech.report.project.perf.actual.qty.label" default="Actual Qty"/> </th>
							<th><g:message code="etech.report.project.perf.Variance.qty.label" default="Variance Qty"/> </th>
							<th><g:message code="etech.report.project.perf.Planned.label" default="Planned"/>($$)</th>
							<th><g:message code="etech.report.project.perf.actual.label" default="Actual"/>($$)</th>
							<th><g:message code="etech.report.project.perf.Variance.label" default="Variance"/>($$)</th>
						</tr>
					</thead>
					<tbody>					
						<g:if test="${plannedExpenseInstacenList?.size() > 0}">	
							<g:each in="${plannedExpenseInstacenList}" status="i" var="ProjectExpenseSummaryDTOInst">
								<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
									<g:if test="${ProjectExpenseSummaryDTOInst?.actualTotal > 0.00 }">
										<td> <g:link action="getAllActualExpenseDetailByExpenseCode" controller="report" params="[prodId: projectInstance?.id, expId: ProjectExpenseSummaryDTOInst.expenseId,'view': Constants.REPORT_VIEW_EXP, 'costOption': params?.costOption]">${ProjectExpenseSummaryDTOInst?.expenseCode}</g:link></td>
									</g:if>
									<g:else>
										<td>${ProjectExpenseSummaryDTOInst?.expenseCode}</td>
									</g:else>
									<td class="text-right">${ProjectExpenseSummaryDTOInst?.plannedQty}</td>
									<td class="text-right">${ProjectExpenseSummaryDTOInst?.actualQty}</td>
									<td class="${ProjectExpenseSummaryDTOInst?.varianceQty > 0.00 == false ?:'red-text'} text-right">${ProjectExpenseSummaryDTOInst?.varianceQty}</td>
									<td class="text-right">${ProjectExpenseSummaryDTOInst?.plannedTotal}</td>
									<td class="text-right">${ProjectExpenseSummaryDTOInst?.actualTotal}</td>
									<td class="${ProjectExpenseSummaryDTOInst?.varianceTotal > 0.00 == false ?:'red-text'} text-right">${ProjectExpenseSummaryDTOInst?.varianceTotal}</td>
								</tr>
							</g:each>
						</g:if>
						<tr class="report-labor-summary">
							<td><g:message code="etech.report.project.perf.totals" default="Totals"/> </td>
							<td class="text-right">${projectExpenseTotalMap.plannedTotalQty}</td>
							<td class="text-right">${projectExpenseTotalMap.actualTotalQty}</td>
							<td class="${projectExpenseTotalMap.varianceTotalQty > 0.00 == false ?:'red-text'} text-right">${projectExpenseTotalMap.varianceTotalQty}</td>
							<td class="text-right">${projectExpenseTotalMap.plannedTtotalCost}</td>
							<td class="text-right">${projectExpenseTotalMap.actualTtotalCost}</td>
							<td class="${projectExpenseTotalMap.varianceTotalCost > 0.00 == false ?:'red-text'} text-right">${projectExpenseTotalMap.varianceTotalCost}</td>								
						</tr>
					</tbody>
					</table>					
				</div>			
				<g:render template="/common/returnLink" model="['returnAction': 'getProjectPerformanceDetail', 'returnController': 'report', 'messageCode':'etech.project.performance.return.screen', 'defaultMessage': 'Return to Project Performance Screen', 'linkId': params?.id,'linkParam': ['costOption': params?.costOption]]"></g:render>
			</div>
		</div>		
	</body>
</html>