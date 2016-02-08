<%@ page import="org.solcorp.etech.Constants"%>
<%@ page import="org.solcorp.etech.PermissionType" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">		
		<title><g:message code="etech.report.project.performance.view.report.project.act.emp.total.title.label" default="Project Activity Employee Total" /></title>
		<r:require module="report"/>
		<content tag="pageName">projectPerformanceReportPage</content>
	</head>
	<body>		
		<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_ACTUAL_RATE.name()}">
			<g:set value="true" var="isActualRateView"></g:set>
		</shiro:hasPermission>
		<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_STANDARD_COST.name()}">
			<g:set value="true" var="isStdCostView"></g:set>
		</shiro:hasPermission>		
		<div class="navigation">			
			<h3><g:message code="etech.report.project.performance.view.report.project.act.emp.total.title.label" default="Project Activity Employee Total" /></h3>			
		</div>				
		<div class="white-bg">		 	
		
		 <div class="title text-left"> <h2><g:message code="etech.report.project.performance.view.report.project.act.emp.total.title.label" default="Project Activity Employee Total" /> </h2> </div>
			<div class="col-xs-12">
				<div class="table-responsive">
					<table class="table">
										
						<thead>
						
						<tr class="report-tr">
										
							<td colspan="7">
											
							<span class="marginright20"><strong><g:message code="etech.report.project.perf.view.project.label" default="Project"/>:</strong>
											
							${reportTotalMap?.projectCode}</span>
							<span class="marginright30"><strong><g:message code="etech.report.project.perf.view.activity.label" default="Activity"/>:</strong>
												
							${params?.activity}</span>											
										 
							</td>
												
						</tr>						 
						
						<tr class="report-dark">
							<td><g:message code="etech.report.project.perf.view.employee.id.label" default="Employee ID"/></td>
							<td><g:message code="etech.report.project.perf.view.name.label" default="Name"/></td>							 
							<td class="text-right"><g:message code="etech.report.project.perf.view.hours.label" default="Hours"/></td>
							<g:if test="${session['logedInUser']?.hoursOnly == false}">
								<g:if test="${isActualRateView || isStdCostView}">
									<g:if test="${params?.costOption && params?.costOption?.equalsIgnoreCase(Constants.REPORT_COST_OPTION_ACTUAL)}" >
										<td class="text-right"><g:message code="etech.report.project.perf.view.std.cost.label" default="Act Cost"/></td>
									</g:if>
									<g:else>
										<td class="text-right"><g:message code="etech.report.project.perf.view.std.cost.label" default="Std Cost"/></td>
									</g:else>
									<td class="text-right"><g:message code="etech.report.project.perf.view.oh.cost.label" default="O/H Cost"/></td>
									<td class="text-right"><g:message code="etech.report.project.perf.view.total.cost.label" default="Total Cost"/></td>
								</g:if>
							</g:if>
						</tr>
						</thead>
						<g:if test="${employeeByProjectAndActivityList?.size() > 0}">	
						<g:each in="${employeeByProjectAndActivityList}" status="i" var="employeeByProjectAndActivityInst">
							<tr>
								<td>${employeeByProjectAndActivityInst?.employeeCode}</td>
								<td>
									<g:link action="getEmployeeDtlByProjectActivityAndEmpCode" params="['projectId': params?.projectId, 'activity': params?.activity, 'costOption': params?.costOption, 'empCode': employeeByProjectAndActivityInst?.employeeCode]">${employeeByProjectAndActivityInst?.employeeName}</g:link>
								</td>
								<td class="text-right">${employeeByProjectAndActivityInst?.hours}</td>
								<g:if test="${session['logedInUser']?.hoursOnly == false}">
									<g:if test="${isActualRateView || isStdCostView}">
										<td class="text-right">${employeeByProjectAndActivityInst?.cost}</td>
										<td class="text-right">${employeeByProjectAndActivityInst?.overheadCost}</td>
										<td class="text-right">${employeeByProjectAndActivityInst?.totalCost}</td>
									</g:if>
								</g:if>
							</tr>
						</g:each>
						</g:if>
						<tr class="report-labor-summary">
						   	<td colspan="2"><g:message code="etech.report.project.perf.view.emp.proj.act.total.label" default="Total"/></td>
						   	<td class="text-right">${reportTotalMap?.reportHoursTotal}</td>
						   	<g:if test="${session['logedInUser']?.hoursOnly == false}">
								<g:if test="${isActualRateView || isStdCostView}">
									<td class="text-right">${reportTotalMap?.reportCost}</td>
									<td class="text-right">${reportTotalMap?.reportdOverheadCost}</td>
									<td class="text-right">${reportTotalMap?.reportTotalCost}</td>
								</g:if>
							</g:if>
						</tr>
					</table>
				</div>
				
				<g:render template="/common/returnLink" model="['returnAction': 'getProjectPerformanceDetail', 'returnController': 'report', 'messageCode':'etech.report.return.to.project.view', 'defaultMessage': 'Return to Project View', 'linkParam': ['id':params?.projectId ,'costOption': params?.costOption]]"></g:render>
			</div>
		 
		</div>		
	</body>
</html>
