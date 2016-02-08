<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
<%@page import="org.solcorp.etech.utils.MiscUtils"%>
<%@ page import="org.solcorp.etech.PermissionType" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<title><g:message code="etech.project.employee.activity.label" default="Employee Activity Details" /></title>
	<content tag="pageName">projectPage</content>
</head>
<body>

	<div class="navigation">
	
		<h3> <g:message code="etech.project.employee.activity.label" default="Employee Activity for" />  ${projectDetailInstance?.service?.product?.code} / ${projectDetailInstance?.service?.code} ( ${projectDetailInstance?.projectProductDetail?.project?.name} ) </h3>
		
	</div>
	
	<div class="col-sm-12" role="main">
			
		<div class="white-bg">
		
			<div class="title">
			
				<h2> <g:message code="etech.project.employee.activity.list.label" default="Employee Activity List" /></h2>
				
			</div>		          		
                      
          	<div>
          	
				<div class="col-xs-12">
				
					<div>
					
						<label><g:message code="etech.project.emp.name.label" default="Employee Name"/></label>: ${actualLaborActivityCriteriaList[0]?.employee?.getEmployeeName()} &nbsp;&nbsp;
						
						<label><g:message code="etech.project.emp.code.label" default="Employee Code"/></label>: ${actualLaborActivityCriteriaList[0]?.employee?.code}
					
						
						
					</div>
					
					<div class="table-responsive">
					
						<table class="table table-hover">
							
							<thead>
							
								<tr>							

									<th> <g:message code="etech.project.activity.label" default="Activity" /> </th>
									
									<th> <g:message code="etech.project.date.label" default="Date" /> </th>
									
									<th class="text-right"> <g:message code="etech.project.hours.label" default="Hours" /> </th>
									<g:if test="${session['logedInUser']?.hoursOnly == false}">	
										<th class="text-right"> <g:message code="etech.project.std.cost.label" default="Std Cost" /> </th>
										
										<th class="text-right"> <g:message code="etech.project.oh.label" default="O/H" /> </th>
										<th class="text-right"> <g:message code="etech.project.total.std.cost.label" default="Total Std Cost" /> </th>										
										<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_ACTUAL_RATE.name()}">											
											<th class="text-right"> <g:message code="etech.project.total.act.cost.label" default="Total Act Cost" /> </th>
										</shiro:hasPermission>
									</g:if>
								</tr>
						</thead>
						
						<tbody>
						
						<g:if test="${actualLaborActivityCriteriaList?.size() > 0}">
							
							<g:each in="${actualLaborActivityCriteriaList}" status="i" var="actualLaborDetailInstance">
							
								<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
									
									<td > ${fieldValue(bean: actualLaborDetailInstance?.laborActivityCode, field: "code")}</td>
									
									<td>${DateFormatUtils.getStringFromDate(actualLaborDetailInstance?.transactionDate)}</td>
									
									<td class="text-right"> ${MiscUtils.removePrecision(actualLaborDetailInstance?.hours)}</td>
									<g:if test="${session['logedInUser']?.hoursOnly == false}">	
										<td class="text-right"> ${MiscUtils.removePrecision(actualLaborDetailInstance?.standardCost)}</td>
										
										<td class="text-right"> ${MiscUtils.removePrecision(actualLaborDetailInstance?.standardOverheadCost)}</td>
										
										<td class="text-right"> ${MiscUtils.removePrecision(actualLaborDetailInstance?.standardTotalCost)}</td>
										<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_ACTUAL_RATE.name()}">
											<td class="text-right"> ${MiscUtils.removePrecision(actualLaborDetailInstance?.actualCost)}</td>
										</shiro:hasPermission>
									</g:if>
								</tr>
								
							</g:each>
							<tr style="background-color: #cdcdcd;">							
								
								<td><label><g:message code="etech.project.total.label" default="Total"/> </label></td>
								
								<td></td>
								
								<td class="text-right">${activityTotalMap?.activityHours }</td>
								<g:if test="${session['logedInUser']?.hoursOnly == false}">
									<td class="text-right">${activityTotalMap?.activityStandardCost }</td>
									
									<td class="text-right">${activityTotalMap?.activityStandardOverheadCost }</td>
									
									<td class="text-right">${activityTotalMap?.activityStandardTotalCost }</td>
									
									<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_ACTUAL_RATE.name()}">
									
										<td class="text-right">${activityTotalMap?.activityActualCost }</td>
									
									</shiro:hasPermission>
								</g:if>
							</tr>
						</g:if>
						
						<g:else>
							
							<tr> <td colspan="4" class="no-record-found"> <g:message code="default.record.not.found" /> </td> </tr>
							
						</g:else>
						
						</tbody>
						
					</table>
					
				</div>
				
				<g:render template="/common/returnLink" model="['returnAction': 'getAllActualLaborEmployeeList', 'returnController': 'project', 'messageCode':'etech.project.employee.return.screen', 'defaultMessage': 'Return to Project Employee Screen', 'linkId': projectDetailInstance?.id]"></g:render>
				
			</div>		
			</div>
						
		</div>
	</div>

</body>
</html>
