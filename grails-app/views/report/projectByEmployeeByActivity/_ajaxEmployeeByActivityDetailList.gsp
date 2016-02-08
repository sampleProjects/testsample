<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
<%@ page import="org.solcorp.etech.PermissionType" %>	
<%@page import="org.solcorp.etech.utils.MiscUtils"%>
<g:if test="${projectActualLaborDtlInstanceList?.size() > 0}">		
	<g:each in="${projectActualLaborDtlInstanceList}" status="i" var="actualLaborInstance">
		<g:set value="${actualLaborInstance?.hhProjectId }" var="lproj"></g:set>
		
		<g:if test="${!old_Proj.equals(lproj)}" >
			<g:if test="${!old_Proj.equals("")}">
				
				<!-- Operation total when Project is null Start-->
				<g:if test="${oldActivityInstance}">
				<tr style="background-color: #f8f8f8;"> 
							<g:if test="${params?.includeDetail == 'true' }">	
								<td class="report-green-txt"><strong><g:message code="etech.report.activity.label" default="Activity"/>:</strong></td>
							<td>${oldActivityInstance[1]}</td>
						</g:if>
						<g:else>
							<td>${oldActivityInstance[1]}</td>
							<td></td>
						</g:else>
						<td></td>
						<td class="text-right">${MiscUtils.removePrecision(oldActivityInstance[3])}</td>
					
						<g:if test="${isHoursOnly == false}">
							<g:if test="${isStdCostView}">
								<td></td>
								<td class="text-right"><g:formatNumber number="${oldActivityInstance[4]}" format="#,##0.00" /></td>
								<td class="text-right"><g:formatNumber number="${oldActivityInstance[5]}" format="#,##0.00" /></td>
								<td class="text-right"><g:formatNumber number="${oldActivityInstance[6]}" format="#,##0.00" /></td>
							</g:if>										
							<g:if test="${isActualRateView}">
								<g:if test="${params?.printActualCost == 'true' }">																
									<g:if test="${params?.includeDetail == 'true' }">	
										<td></td>
									</g:if>
									<td class="text-right"><g:formatNumber number="${oldActivityInstance[7]}" format="#,##0.00" /></td>
								</g:if>									
							</g:if>
						</g:if>										
					</tr>	
					</g:if>	
				<!-- Operation total when Project is null End-->
					<g:set value="${projectByEmployeeActivityMap.get(old_Proj+'_'+old_Emp)}" var="employeeInstance"></g:set>
					<tr style="background-color: #eaeaea;"> 
							<g:if test="${params?.includeDetail == 'true' }">	
								<td ><strong><g:message code="etech.report.employee.label" default="Employee"/>:</strong></td>	
							</g:if>
							<g:else> <td></td> </g:else>
							<td>${employeeInstance[7] ?: ''} ${employeeInstance[8] ?: ''}</td>
							<td></td>
							<td class="text-right">${MiscUtils.removePrecision(employeeInstance[2])}</td>
				
							<g:if test="${isHoursOnly == false}">
								<g:if test="${isStdCostView}">
									<td></td>											
									<td class="text-right"><g:formatNumber number="${employeeInstance[3]}" format="#,##0.00" /></td>
									<td class="text-right"><g:formatNumber number="${employeeInstance[4]}" format="#,##0.00" /></td>
									<td class="text-right"><g:formatNumber number="${employeeInstance[5]}" format="#,##0.00" /></td>											
								</g:if>
								<g:if test="${isActualRateView}">
									<g:if test="${params?.printActualCost == 'true' }">
										<g:if test="${params?.includeDetail == 'true' }">	
											<td></td>
										</g:if>										
										 <td class="text-right"><g:formatNumber number="${employeeInstance[6]}" format="#,##0.00" /></td>
									</g:if>										
								</g:if>
							</g:if>
					</tr>	 
					
					<!-- Project total -->
					<g:set value="${projectTotalMap.get(old_Proj)}" var="projectTotalInstance"></g:set> 
					<tr style="background-color: #cdcdcd;">								
						<td class="report-green-txt"><strong><g:message code="etech.report.project.label" default="Project:"/></strong></td>
						<td>${projectTotalInstance[0]}</td>	
						<td></td>
						<td class="text-right">${MiscUtils.removePrecision(projectTotalInstance[1])}</td>
						<g:if test="${isHoursOnly == false}">
							<g:if test="${isStdCostView}">
								<td></td>
								<td class="text-right"><g:formatNumber number="${projectTotalInstance[2] }" format="#,##0.00" /></td>
								<td class="text-right"><g:formatNumber number="${projectTotalInstance[3]}" format="#,##0.00" /></td>
								<td class="text-right"><g:formatNumber number="${projectTotalInstance[4]}" format="#,##0.00" /></td>
							</g:if>
							<g:if test="${isActualRateView}">
								<g:if test="${params?.printActualCost == 'true' }">	
									<g:if test="${params?.includeDetail == 'true' }">					
										<td></td>
									</g:if>											
									 <td class="text-right"><g:formatNumber number="${projectTotalInstance[5]}" format="#,##0.00" /></td>
								</g:if>
							</g:if>
						</g:if>
					</tr>	
				<tr><td colspan="10" class="border-left-right-none" height="30"></td></tr>											
			</g:if>
				
			<!-- Project Header Start -->
			<tr class="report-tr">										
				<td colspan="10">											
					<span class="marginright20"><strong><g:message code="etech.report.project.label" default="Project"/>:</strong>											
					${actualLaborInstance?.hhProjectId}</span>
					<span class="marginright20">${actualLaborInstance?.project?.name}</span>
					<span class="marginright20"><strong><g:message code="etech.report.customer.label" default="Customer"/>:</strong>
					${actualLaborInstance?.project?.customer?.code ?: '' }</span>					
				</td>												
			</tr>
			<!-- Project Header End -->
												
    	   	<!-- Header Start-->
			<tr class="report-dark">							
				<td><g:message code="etech.report.employee.label" default="Employee"/></td>
				<td><g:message code="etech.report.activity.label" default="Activity"/></td>
				<g:if test="${params?.includeDetail == 'true' }">
					<td ><g:message code="etech.report.date.label" default="Date"/></td>
				</g:if>
				<g:else> <td></td></g:else>	
				<td class="text-right"><g:message code="etech.report.hours.label" default="Hours"/></td>
						
				<g:if test="${isHoursOnly == false}">
					<g:if test="${isStdCostView}">
						<g:if test="${params?.includeDetail == 'true' }">
							<td class="text-right"><g:message code="etech.report.std.rate.label" default="Std Rate"/></td>
						</g:if>
						<g:else> <td></td></g:else>
						<td class="text-right"><g:message code="etech.report.std.cost.label" default="Std Cost"/></td>
						<td class="text-right"><g:message code="etech.report.o/h.label" default="O/H"/></td>
						<td class="text-right"><g:message code="etech.report.total.cost.label" default="Total Cost"/></td>
					</g:if>	
					<g:if test="${isActualRateView}">
						<g:if test="${params?.printActualCost == 'true' }">
							<g:if test="${params?.includeDetail == 'true' }">									
								<td class="text-right"><g:message code="etech.report.actual.rate.label" default="Actual Rate"/></td>
							</g:if>
							<td class="text-right"><g:message code="etech.report.actual.wage.label" default="Actual Wage"/></td>								 
						</g:if>
					</g:if>
				</g:if>
			</tr>			
			<!--   Header End-->	    
		</g:if> <!-- else  !old_Proj.equals(lproj) -->
		<g:set value="${actualLaborInstance?.employee?.code}" var="lempno"></g:set>
		
			<g:if test="${old_Emp != lempno && old_Proj == lproj}">
				<!-- Operation total 2 Start-->					
					<tr style="background-color: #f8f8f8;"> 
							<g:if test="${params?.includeDetail == 'true' }">	
								<td class="report-green-txt"><strong><g:message code="etech.report.activity.label" default="Activity"/>:</strong></td>
							<td>${oldActivityInstance[1]}</td>
						</g:if>
						<g:else>
							<td>${oldActivityInstance[1]}</td>
							<td></td>
						</g:else>
						<td></td>
						<td class="text-right">${MiscUtils.removePrecision(oldActivityInstance[3])}</td>
					
						<g:if test="${isHoursOnly == false}">
							<g:if test="${isStdCostView}">
								<td></td>
								<td class="text-right"><g:formatNumber number="${oldActivityInstance[4]}" format="#,##0.00" /></td>
								<td class="text-right"><g:formatNumber number="${oldActivityInstance[5]}" format="#,##0.00" /></td>
								<td class="text-right"><g:formatNumber number="${oldActivityInstance[6]}" format="#,##0.00" /></td>
							</g:if>										
							<g:if test="${isActualRateView}">
								<g:if test="${params?.printActualCost == 'true' }">																
									<g:if test="${params?.includeDetail == 'true' }">	
										<td></td>
									</g:if>
									<td class="text-right"><g:formatNumber number="${oldActivityInstance[7]}" format="#,##0.00" /></td>
								</g:if>									
							</g:if>
						</g:if>										
					</tr>				
					
					<!-- Operation total 2 End-->
					<g:set value="${projectByEmployeeActivityMap.get(old_Proj+'_'+old_Emp)}" var="employeeInstance"></g:set>
					<tr style="background-color: #eaeaea;"> 
							<g:if test="${params?.includeDetail == 'true' }">	
								<td ><strong><g:message code="etech.report.employee.label" default="Employee"/>:</strong></td>	
							</g:if>
							<g:else> <td></td> </g:else>
							<td>${employeeInstance[7] ?: ''} ${employeeInstance[8] ?: ''}</td>
							<td></td>
							<td class="text-right">${MiscUtils.removePrecision(employeeInstance[2])}</td>
				
							<g:if test="${isHoursOnly == false}">
								<g:if test="${isStdCostView}">
									<td></td>											
									<td class="text-right"><g:formatNumber number="${employeeInstance[3]}" format="#,##0.00" /></td>
									<td class="text-right"><g:formatNumber number="${employeeInstance[4]}" format="#,##0.00" /></td>
									<td class="text-right"><g:formatNumber number="${employeeInstance[5]}" format="#,##0.00" /></td>											
								</g:if>
								<g:if test="${isActualRateView}">
									<g:if test="${params?.printActualCost == 'true' }">
										<g:if test="${params?.includeDetail == 'true' }">	
											<td></td>
										</g:if>										
										 <td class="text-right"><g:formatNumber number="${employeeInstance[6]}" format="#,##0.00" /></td>
									</g:if>										
								</g:if>
							</g:if>
						</tr>
			</g:if>
			
				<g:set value="${actualLaborInstance?.laborActivityCode?.code}" var="loper"></g:set>
				
				<g:set value="${projectByActByEmployeeMap?.get(old_Proj+'_'+old_Oper+'_'+old_Emp)}" var="employeeInstance"></g:set> 
				
				<g:if test="${old_Oper != loper && old_Emp == lempno && old_Proj == lproj}" >
					<tr style="background-color: #f8f8f8;"> 
							<g:if test="${params?.includeDetail == 'true' }">	
								<td class="report-green-txt"><strong><g:message code="etech.report.activity.label" default="Activity"/>:</strong></td>
							<td>${oldActivityInstance[1]}</td>
						</g:if>
						<g:else>
							<td>${oldActivityInstance[1]}</td>
							<td></td>
						</g:else>
						<td></td>
						<td class="text-right">${MiscUtils.removePrecision(oldActivityInstance[3])}</td>
					
						<g:if test="${isHoursOnly == false}">
							<g:if test="${isStdCostView}">
								<td></td>
								<td class="text-right"><g:formatNumber number="${oldActivityInstance[4]}" format="#,##0.00" /></td>
								<td class="text-right"><g:formatNumber number="${oldActivityInstance[5]}" format="#,##0.00" /></td>
								<td class="text-right"><g:formatNumber number="${oldActivityInstance[6]}" format="#,##0.00" /></td>
							</g:if>										
							<g:if test="${isActualRateView}">
								<g:if test="${params?.printActualCost == 'true' }">																
									<g:if test="${params?.includeDetail == 'true' }">	
										<td></td>
									</g:if>
									<td class="text-right"><g:formatNumber number="${oldActivityInstance[7]}" format="#,##0.00" /></td>
								</g:if>									
							</g:if>
						</g:if>										
					</tr>			
				
				
				</g:if>
		<!-- Include Detail (True) Start-->		
		<g:if test="${params?.includeDetail == 'true' }">	
			<tr>
				<td>${actualLaborInstance?.employee?.code}</td>
				<td>${actualLaborInstance?.laborActivityCode?.code}</td>
				<td>${DateFormatUtils.getStringFromDate(actualLaborInstance?.transactionDate)}</td>
				<td class="text-right">${MiscUtils.removePrecision(actualLaborInstance?.hours)}</td>
				<g:if test="${isHoursOnly == false}">
					<g:if test="${isStdCostView}">
						<td class="text-right"><g:formatNumber number="${actualLaborInstance?.standardRate}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${actualLaborInstance?.standardCost}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${actualLaborInstance?.standardOverheadCost}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${actualLaborInstance?.standardTotalCost}" format="#,##0.00" /></td>
					</g:if>														
					<g:if test="${isActualRateView}">
						<g:if test="${params?.printActualCost == 'true' }">
							<g:if test="${params?.includeDetail == 'true' }">	
								<td class="text-right"><g:formatNumber number="${actualLaborInstance?.actualRate}" format="#,##0.00" /></td>
							</g:if>										
							<td class="text-right"><g:formatNumber number="${actualLaborInstance?.actualCost}" format="#,##0.00" /></td>
						</g:if>
					</g:if>	
				</g:if>
			 </tr>
		</g:if>
		<!-- Include Detail (True) End-->	
		
		<g:set value="${lproj }" var="old_Proj"></g:set>
		<g:set value="${loper}" var="old_Oper"></g:set>
		<g:set value="${lempno}" var="old_Emp"></g:set>
		<g:set value="${projectByActByEmployeeMap?.get(old_Proj+'_'+old_Oper+'_'+old_Emp)}" var="activityInstance"></g:set> 
		<g:set value="${activityInstance}" var="oldActivityInstance"></g:set>
		
	</g:each>
	
	<tr style="background-color: #f8f8f8;"> 
							<g:if test="${params?.includeDetail == 'true' }">	
								<td class="report-green-txt"><strong><g:message code="etech.report.activity.label" default="Activity"/>:</strong></td>
							<td>${oldActivityInstance[1]}</td>
						</g:if>
						<g:else>
							<td>${oldActivityInstance[1]}</td>
							<td></td>
						</g:else>
						<td></td>
						<td class="text-right">${MiscUtils.removePrecision(oldActivityInstance[3])}</td>
					
						<g:if test="${isHoursOnly == false}">
							<g:if test="${isStdCostView}">
								<td></td>
								<td class="text-right"><g:formatNumber number="${oldActivityInstance[4]}" format="#,##0.00" /></td>
								<td class="text-right"><g:formatNumber number="${oldActivityInstance[5]}" format="#,##0.00" /></td>
								<td class="text-right"><g:formatNumber number="${oldActivityInstance[6]}" format="#,##0.00" /></td>
							</g:if>										
							<g:if test="${isActualRateView}">
								<g:if test="${params?.printActualCost == 'true' }">																
									<g:if test="${params?.includeDetail == 'true' }">	
										<td></td>
									</g:if>
									<td class="text-right"><g:formatNumber number="${oldActivityInstance[7]}" format="#,##0.00" /></td>
								</g:if>									
							</g:if>
						</g:if>										
					</tr>
					
					<g:set value="${projectByEmployeeActivityMap.get(old_Proj+'_'+old_Emp)}" var="employeeInstance"></g:set>			
				 	<tr style="background-color: #eaeaea;">
							<g:if test="${params?.includeDetail == 'true' }">	
								<td><strong><g:message code="etech.report.employee.label" default="Employee"/>:</strong></td>	
							</g:if>
							<g:else> <td></td> </g:else>
							<td>${employeeInstance[7] ?: ''} ${employeeInstance[8] ?: ''}</td>
							<td></td>
							<td class="text-right">${MiscUtils.removePrecision(employeeInstance[2])}</td>
				
							<g:if test="${isHoursOnly == false}">
								<g:if test="${isStdCostView}">
									<td></td>											
									<td class="text-right"><g:formatNumber number="${employeeInstance[3]}" format="#,##0.00" /></td>
									<td class="text-right"><g:formatNumber number="${employeeInstance[4]}" format="#,##0.00" /></td>
									<td class="text-right"><g:formatNumber number="${employeeInstance[5]}" format="#,##0.00" /></td>											
								</g:if>
								<g:if test="${isActualRateView}">
									<g:if test="${params?.printActualCost == 'true' }">
										<g:if test="${params?.includeDetail == 'true' }">	
											<td></td>
										</g:if>										
										 <td class="text-right"><g:formatNumber number="${employeeInstance[6]}" format="#,##0.00" /></td>
									</g:if>										
								</g:if>
							</g:if>
						</tr>
						  <!-- Project Total -->
        <g:set value="${projectTotalMap.get(old_Proj)}" var="projectTotalInstance"></g:set> 
		<tr style="background-color: #cdcdcd;">								
						<td class="report-green-txt"><strong><g:message code="etech.report.project.label" default="Project:"/></strong></td>
						<td>${projectTotalInstance[0]}</td>	
						<td></td>
						<td class="text-right">${MiscUtils.removePrecision(projectTotalInstance[1]) }</td>
						<g:if test="${isHoursOnly == false}">
							<g:if test="${isStdCostView}">
								<td></td>
								<td class="text-right"><g:formatNumber number="${projectTotalInstance[2] }" format="#,##0.00" /></td>
								<td class="text-right"><g:formatNumber number="${projectTotalInstance[3]}" format="#,##0.00" /></td>
								<td class="text-right"><g:formatNumber number="${projectTotalInstance[4]}" format="#,##0.00" /></td>
							</g:if>
							<g:if test="${isActualRateView}">
								<g:if test="${params?.printActualCost == 'true' }">	
									<g:if test="${params?.includeDetail == 'true' }">					
										<td></td>
									</g:if>											
									 <td class="text-right"><g:formatNumber number="${projectTotalInstance[5]}" format="#,##0.00" /></td>
								</g:if>
							</g:if>
						</g:if>
					</tr>	
					
</g:if>	 
<g:else>
	<tr><td colspan="10" style="color: red;" ><g:message code="default.record.not.found" /></td></tr>
</g:else>				