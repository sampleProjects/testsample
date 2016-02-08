<%@ page import="org.solcorp.etech.PermissionType" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<title><g:message code="etech.project.actual.labor.employee.label" default="Actual Labor Employee Details" /></title>
	<content tag="pageName">projectPage</content>
</head>
<body>

	<div class="navigation">
	
		<h3> <g:message code="etech.project.actual.labor.employee.label" default="Actual Labor By Employee for" /> ${projectDetailInstance?.service?.product?.code} / ${projectDetailInstance?.service?.code} ( ${projectDetailInstance?.projectProductDetail?.project?.name} )</h3>
		
	</div>
	
	<div class="col-sm-12" role="main">
			
		<div class="white-bg">
		
			<div class="title">
			
				<h2> <g:message code="etech.project.actual.labor.list.label" default="Actual Labor List" />	</h2>
				
			</div>		          		
                      
          	<div>
          	
				<div class="col-xs-12">
				
					<div class="table-responsive">
						
						<table class="table table-hover">
						
							<thead>
							
								<tr>							

									<th> <g:message code="etech.project.employee.code.label" default="Employee Name" /> </th>
																										
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
						
						<g:if test="${employeeDetailDTOList?.size() > 0}">
							
							<g:each in="${employeeDetailDTOList}" status="i" var="employeeDetailDTOInstance">
							
								<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

									<td> <g:link action="getActualLaborActivityDetail" params="[prodId: projectDetailInstance?.id, empId: employeeDetailDTOInstance?.employyeeId]">${employeeDetailDTOInstance?.employeeName}</g:link></td>

									<td class="text-right" > ${employeeDetailDTOInstance?.hours}</td>
									<g:if test="${session['logedInUser']?.hoursOnly == false}">	
										<td class="text-right"> ${employeeDetailDTOInstance?.standardCost}</td>
										
										<td class="text-right"> ${employeeDetailDTOInstance?.standardOverheadCost}</td>
										
										<td class="text-right"> ${employeeDetailDTOInstance?.standardTotalCost}</td>
										
										<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_ACTUAL_RATE.name()}">
											<td class="text-right"> ${employeeDetailDTOInstance?.actualCost}</td>
										</shiro:hasPermission>
									</g:if>
								</tr>
								
							</g:each>
							
						</g:if>
						
						<g:else>
							
							<tr> <td colspan="6" class="no-record-found"> <g:message code="default.record.not.found" /> </td> </tr>
							
						</g:else>
						
						</tbody>
						
					</table>
					
				</div>
				
				<g:render template="/common/returnLink" model="['returnAction': 'edit', 'returnController': 'project', 'messageCode':'etech.project.return.screen', 'defaultMessage': 'Return to Project Screen', 'linkId': projectDetailInstance?.projectProductDetail?.project?.id]"></g:render>
				
			</div>		
			</div>
						
		</div>
	</div>

</body>
</html>
