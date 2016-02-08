<%@ page import="org.solcorp.etech.Constants" %>
<%@ page import="org.solcorp.etech.PermissionType" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">		
		<title><g:message code="etech.report.project.performance.view.title.label" default="Project Performance View" /></title>
		<content tag="pageName">projectPerformanceReportPage</content>		
	</head>
	<body>		
		<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_ACTUAL_RATE.name()}">
			<g:set value="true" var="isActualRateView"></g:set>
		</shiro:hasPermission>
		<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_STANDARD_COST.name()}">
			<g:set value="true" var="isStdCostView"></g:set>
		</shiro:hasPermission>
	<div class="navigation"> <h3> <g:message code="etech.report.project.performance.view.title.label" default="Project Performance View" /> </h3> </div>		
		
	<div class="white-bg">		 	
	
		<div class="title text-left"> <h2> <g:message code="etech.report.project.performance.view.title.label" default="Project Performance View" /> </h2> </div>		
					
			<div class="col-xs-12">
							 
				<div class="table-responsive">
					
					<table class="table">
								
						<tr>
							<td class="performance-table"><g:message code="etech.report.project.perf.project.label" default="Project"/> #</td>
							<td>${projectPerfViewDTOInstance?.projectCode}</td>
									
							<td class="performance-table"><g:message code="etech.report.project.perf.customer.label" default="Customer"/></td>
							<td>${projectPerfViewDTOInstance?.customer.name}</td>					
						</tr>
						<tr>
							<td class="performance-table"><g:message code="etech.report.project.perf.project.name.label" default="Project Name"/></td>
							<td>${projectPerfViewDTOInstance?.projectName}</td>
									
							<td class="performance-table"><g:message code="etech.report.project.perf.account.executive.label" default="Account Executive"/></td>
							<td>${projectPerfViewDTOInstance?.accExecutive?.code}</td>					
						</tr>
						<tr>
							<td class="performance-table"><g:message code="etech.report.project.perf.order.label" default="Order"/> #</td>
							<td></td>
									
							<td class="performance-table"><g:message code="etech.report.project.perf.status.label" default="Status"/></td>
							<td>${projectPerfViewDTOInstance?.status}</td>
						</tr>
					</table>
				</div>
				  <g:if test="${session['logedInUser']?.hoursOnly == false}">
					<div class="labor-sum clearfix"><h2 class="pull-left"><g:message code="etech.report.project.perf.project.summary.label" default="Project Summary"/></h2></div>
			
					<div class="table-responsive">
					<table class="table">									
						<thead>
						<tr>
							<th></th>
							<th class="text-center"><g:message code="etech.report.project.perf.plan.label" default="Plan"/>($$)</th>
							<g:if test="${isActualRateView || isStdCostView}"> 
								<th class="text-center"><g:message code="etech.report.project.perf.actual.label" default="Actual"/>($$)</th>
								<th class="text-center"><g:message code="etech.report.project.perf.variance.label" default="Variance"/>($$)</th>
							</g:if>
						</tr>
						</thead>
						<tr>
							<td><strong><g:message code="etech.report.project.perf.billing.label" default="Billing"/></strong></td>
						
							<td class="text-right">${projectPerfViewDTOInstance?.planBilling ?:0.00}</td>
							<g:if test="${isActualRateView || isStdCostView}">
								<td class="text-right">${projectPerfViewDTOInstance?.actualBilling ?:0.0 }</td>
								<td class="text-right ${projectPerfViewDTOInstance?.varianceBilling > 0.00 == false ?:'red-text'}">${projectPerfViewDTOInstance?.varianceBilling ?:0.0}</td>
							</g:if>
						</tr>
						<tr>
							<td><strong><g:message code="etech.report.project.perf.expenses.label" default="Expenses"/></strong></td>
						
							<td class="text-right">${projectPerfViewDTOInstance?.planExpenses ?:0.0 }</td>
							<g:if test="${isActualRateView || isStdCostView}">
								<td class="text-right">
								<g:if test="${projectPerfViewDTOInstance?.actualExpenses > 0.00}">
									<g:link action="getAllActualExpenseList" controller="report" id="${projectPerfViewDTOInstance?.projectId}"  params="['costOption': params?.costOption]" class="text-underline">${projectPerfViewDTOInstance?.actualExpenses ?:0.0 }</g:link>
								</g:if>
								<g:else>0.00
								</g:else>
								</td>
								
								<td class="text-right ${projectPerfViewDTOInstance?.varianceExpenses > 0.00 == false ?:'red-text'}">${projectPerfViewDTOInstance?.varianceExpenses?:0.0 }</td>
							</g:if>
						</tr>	
						<tr>
							<td><strong><g:message code="etech.report.project.perf.labor.label" default="Labor"/></strong></td>
						
							<td class="text-right">${projectPerfViewDTOInstance?.planLabor ?:0.0 }</td>
							<g:if test="${isActualRateView || isStdCostView}">
								<td class="text-right">
									<g:if test="${projectPerfViewDTOInstance?.actualLabor > 0.00}">
										<g:link action="getAllActualLaborEmployeeListPage" controller="report" id="${projectPerfViewDTOInstance?.projectId}"  params="['costOption': params?.costOption]" class="text-underline">${projectPerfViewDTOInstance?.actualLabor ?:0.0 }</g:link>
									</g:if><g:else>0.00</g:else>
								</td>
								<td class="text-right ${projectPerfViewDTOInstance?.varianceLabor > 0.00 == false ?:'red-text'}">${projectPerfViewDTOInstance?.varianceLabor?:0.0 }</td>
							</g:if>
						</tr>	
						<tr>
							<td><strong><g:message code="etech.report.project.perf.overhead.label" default="Overhead"/></strong></td>
						
							<td class="text-right">${projectPerfViewDTOInstance?.planOverhead ?:0.0 }</td>
							<g:if test="${isActualRateView || isStdCostView}">
								<td class="text-right">${projectPerfViewDTOInstance?.actualOverhead ?:0.0}</td>
								<td class="text-right ${projectPerfViewDTOInstance?.varianceOverhead > 0.00 == false ?:'red-text'}">${projectPerfViewDTOInstance?.varianceOverhead ?:0.0}</td>
							</g:if>
						</tr>
						<tr>
							<td><strong><g:message code="etech.report.project.perf.total.cost.label" default="Total Cost"/></strong></td>
						
							<td class="text-right">${projectPerfViewDTOInstance?.planTotalCost?:0.0 }</td>
							<g:if test="${isActualRateView || isStdCostView}">
								<td class="text-right">${projectPerfViewDTOInstance?.actualTotalCost?:0.0 }</td>
								<td class="text-right ${projectPerfViewDTOInstance?.varianceTotalCost > 0.00 == false ?:'red-text'}">${projectPerfViewDTOInstance?.varianceTotalCost?:0.0 }</td>
							</g:if>
						</tr>
						<tr>
							<td><strong><g:message code="etech.report.project.revenue.label" default="Revenue"/></strong></td>
						
							<td class="text-right">${projectPerfViewDTOInstance?.plannedRevenueTotal ?:0.0 }</td>
							<g:if test="${isActualRateView || isStdCostView}">
								<td class="text-right">${projectPerfViewDTOInstance?.actualRevenueTotal ?:0.0 }</td>
								<td class="text-right ${projectPerfViewDTOInstance?.varianceRevenueTotal > 0.00 == false ?:'red-text'}">${projectPerfViewDTOInstance?.varianceRevenueTotal ?:0.0 }</td>
							</g:if>
						</tr>						
						<tr>
							<td><strong><g:message code="etech.report.project.perf.profit.label" default="Profit"/></strong></td>
						
							<td class="text-right">${projectPerfViewDTOInstance?.planProfit ?:0.0 }</td>
							<g:if test="${isActualRateView || isStdCostView}">
								<td class="text-right">${projectPerfViewDTOInstance?.actualProfit ?:0.0 }</td>
								<td class="text-right ${projectPerfViewDTOInstance?.varianceProfit > 0.00 == false ?:'red-text'}">${projectPerfViewDTOInstance?.varianceProfit ?:0.0 }</td>
							</g:if>
						</tr>	
						<tr>
							<td><strong><g:message code="etech.report.project.perf.profit.label" default="Profit"/>%</strong></td>
						
							<td class="text-right">${projectPerfViewDTOInstance?.planProfitPercentage ?:0.0}</td>
							<g:if test="${isActualRateView || isStdCostView}">
								<td class="text-right">${projectPerfViewDTOInstance?.actualProfitPercentage ?:0.0}</td>
								<td class="text-right ${projectPerfViewDTOInstance?.varianceProfitPercentage > 0.00 == false ?:'red-text'}">${projectPerfViewDTOInstance?.varianceProfitPercentage}</td>
							</g:if>
						</tr>						
					</table>
					</div>
					</g:if>
					<g:link action="getProjectExpenseSummary" id="${projectPerfViewDTOInstance?.projectId}" params="['costOption': params?.costOption]" class="addProjDfltLink btn btn-success">
						<g:message code="etech.report.project.perf.expenses.details.label" default="Expenses Details"/>
					</g:link>
					<div class="labor-sum clearfix">
						<h2 class="pull-left">
							<g:message code="etech.report.project.perf.labor.summary.label" default="Labor Summary"/>
						</h2>
						<g:if  test="${projectPerfViewDTOInstance?.reportTotalBy == Constants.REPORT_TOTAL_BY_ACTIVITY}">
							<g:link action="getProjectPerformanceDetail" id="${projectPerfViewDTOInstance?.projectId}" params="['by': "${Constants.REPORT_TOTAL_BY_GROUP}", 'costOption': params?.costOption]" class="addProjDfltLink pull-right btn btn-success">
								<g:message code="etech.report.project.perf.group.total.label" default="Dept Total"/>
							</g:link>
							<g:link action="getProjectPerformanceDetail" id="${projectPerfViewDTOInstance?.projectId}" params="['by': "${Constants.REPORT_BY_COE}", 'costOption': params?.costOption]" class="addProjDfltLink pull-right btn btn-success marginright5">
								<g:message code="etech.report.project.perf.coe.total.label" default="COE Total"/>
							</g:link>
						</g:if>
						<g:elseif test="${projectPerfViewDTOInstance?.reportTotalBy == Constants.REPORT_TOTAL_BY_GROUP}">
							<g:link action="getProjectPerformanceDetail" id="${projectPerfViewDTOInstance?.projectId}" params="['by': "${Constants.REPORT_TOTAL_BY_ACTIVITY}", 'costOption': params?.costOption]" class="addProjDfltLink pull-right btn btn-success">
								<g:message code="etech.report.project.perf.activity.total.label" default="Activity Total"/>
							</g:link>
							<g:link action="getProjectPerformanceDetail" id="${projectPerfViewDTOInstance?.projectId}" params="['by': "${Constants.REPORT_BY_COE}", 'costOption': params?.costOption]" class="addProjDfltLink pull-right btn btn-success marginright5">
								<g:message code="etech.report.project.perf.coe.total.label" default="COE Total"/>
							</g:link>
						</g:elseif>
						<g:elseif test="${projectPerfViewDTOInstance?.reportTotalBy == Constants.REPORT_BY_COE}">
							<g:link action="getProjectPerformanceDetail" id="${projectPerfViewDTOInstance?.projectId}" params="['by': "${Constants.REPORT_TOTAL_BY_ACTIVITY}", 'costOption': params?.costOption]" class="addProjDfltLink pull-right btn btn-success">
								<g:message code="etech.report.project.perf.activity.total.label" default="Activity Total"/>
							</g:link>
							<g:link action="getProjectPerformanceDetail" id="${projectPerfViewDTOInstance?.projectId}" params="['by': "${Constants.REPORT_TOTAL_BY_GROUP}", 'costOption': params?.costOption]" class="addProjDfltLink pull-right btn btn-success marginright5">
								<g:message code="etech.report.project.perf.group.total.label" default="Dept Total"/>
							</g:link>
						</g:elseif>
	  				</div>
					
					<div class="table-responsive">								
					<table class="table table-striped">
					<thead>
						<tr>
							<g:if  test="${projectPerfViewDTOInstance?.reportTotalBy != Constants.REPORT_BY_COE}">
								<th><g:message code="etech.report.project.perf.group.label" default="Dept"/></th>
							</g:if>
							<g:if  test="${projectPerfViewDTOInstance?.reportTotalBy == Constants.REPORT_TOTAL_BY_ACTIVITY}">							
								<th><g:message code="etech.report.project.perf.activity.label" default="Activity"/> </th>
							</g:if>
							<g:if  test="${projectPerfViewDTOInstance?.reportTotalBy == Constants.REPORT_BY_COE}">							
								<th><g:message code="etech.report.project.perf.coe.label" default="COE"/> </th>
							</g:if>
							<th><g:message code="etech.report.project.perf.planned.hrs.label" default="Planned Hrs"/> </th>
							<th><g:message code="etech.report.project.perf.actual.hrs.label" default="Actual Hrs"/> </th>
							<th><g:message code="etech.report.project.perf.Variance.hrs.label" default="Variance Hrs"/> </th>
							<g:if test="${session['logedInUser']?.hoursOnly == false}">
								<g:if test="${isActualRateView || isStdCostView}">
									<th><g:message code="etech.report.project.perf.Planned.label" default="Planned"/>($$)</th>
									<th><g:message code="etech.report.project.perf.actual.label" default="Actual"/>($$)</th>
									<th><g:message code="etech.report.project.perf.Variance.label" default="Variance"/>($$)</th>
								</g:if>
							</g:if>
						</tr>
					</thead>
					<tbody>					
						<g:if test="${projectPerfViewDTOInstance?.laborSummaryDTOList?.size() > 0}">	
							<g:each in="${projectPerfViewDTOInstance?.laborSummaryDTOList}" status="i" var="laborSummaryDTOInstance">
								<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
								
									<g:if  test="${projectPerfViewDTOInstance?.reportTotalBy != Constants.REPORT_BY_COE}">
										<td>${laborSummaryDTOInstance?.laborActivityGroup?.code}</td>
									</g:if>
									<g:if  test="${projectPerfViewDTOInstance?.reportTotalBy == Constants.REPORT_TOTAL_BY_ACTIVITY}">	
										<td>
											<g:if test="${isActualRateView || isStdCostView}">
												<g:link action="getEmployeeByProjectAndActivity" params="['projectId': projectPerfViewDTOInstance?.projectId , 'activity': laborSummaryDTOInstance?.laborActivityCode, 'costOption': params?.costOption]">${laborSummaryDTOInstance?.laborActivityCode}</g:link>
											</g:if>
											<g:else>
												${laborSummaryDTOInstance?.laborActivityCode}
											</g:else>	
										</td>
									</g:if>
									<g:if  test="${projectPerfViewDTOInstance?.reportTotalBy == Constants.REPORT_BY_COE}">	
									<td>${laborSummaryDTOInstance?.coe?.code}</td>
									</g:if>
									<td class="text-right">${laborSummaryDTOInstance?.plannedHours}</td>
									<td class="text-right">${laborSummaryDTOInstance?.actualHours}</td>
									<td class="${laborSummaryDTOInstance?.varianceHours > 0.00 == false ?:'red-text'} text-right">${laborSummaryDTOInstance?.varianceHours}</td>
									<g:if test="${session['logedInUser']?.hoursOnly == false}">
										<g:if test="${isActualRateView || isStdCostView}">
											<td class="text-right">${laborSummaryDTOInstance?.plannedTotal}</td>
											<td class="text-right">${laborSummaryDTOInstance?.actualTotal}</td>
											<td class="${laborSummaryDTOInstance?.varianceTotal > 0.00 == false ?:'red-text'} text-right">${laborSummaryDTOInstance?.varianceTotal}</td>
										</g:if>
									</g:if>
								</tr>
							</g:each>
						</g:if>
						<tr class="report-labor-summary">
							<g:if  test="${projectPerfViewDTOInstance?.reportTotalBy != Constants.REPORT_TOTAL_BY_ACTIVITY}">
								<td><g:message code="etech.report.project.perf.labor.totals" default="Labor Totals"/> </td>
							</g:if>
							<g:if  test="${projectPerfViewDTOInstance?.reportTotalBy == Constants.REPORT_TOTAL_BY_ACTIVITY}">	
								<td colspan="2"><g:message code="etech.report.project.perf.labor.totals" default="Labor Totals"/> </td>
							</g:if>							 
							<td class="text-right">${projectPerfViewDTOInstance?.laborPlanHoursTotal}</td>
							<td class="text-right">${projectPerfViewDTOInstance?.laborActualHoursTotal}</td>
							<td class="text-right">${projectPerfViewDTOInstance?.laborVarianceHoursTotal}</td>
							<g:if test="${session['logedInUser']?.hoursOnly == false}">
								<g:if test="${isActualRateView || isStdCostView}">
									<td class="text-right">${projectPerfViewDTOInstance?.laborPlanTotal}</td>
									<td class="text-right">${projectPerfViewDTOInstance?.laborActualTotal}</td>
									<td class="text-right">${projectPerfViewDTOInstance?.laborVarianceTotal}</td>
								</g:if>
							</g:if>
						</tr>
					
					</tbody>
					</table>					
				</div>						
				<g:render template="/common/returnLink" model="['returnAction': 'projectPerformanceReport', 'returnController': 'report', 'messageCode':'etech.report.return.filter', 'defaultMessage': 'Return to Report Filter']"></g:render>
			</div>
		</div>		
	</body>
</html>