<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
<%@ page import="org.solcorp.etech.PermissionType" %>	
<%@page import="org.solcorp.etech.utils.MiscUtils"%>

<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_ACTUAL_RATE.name()}">
	<g:set value="true" var="isActualRateView"></g:set>
</shiro:hasPermission>
<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_STANDARD_COST.name()}">
	<g:set value="true" var="isStdCostView"></g:set>
</shiro:hasPermission>

<g:set value="${session['logedInUser']?.hoursOnly}" var="isHoursOnly"> </g:set>

<g:set value="" var="old_Oper"></g:set>
<g:set value="" var="old_Proj"></g:set>
<g:set value="" var="old_Emp"></g:set>
<g:set value="" var="lproj"></g:set>		
<g:set value="" var="employeeInstance"></g:set>
<g:set value="" var="oldEmployeeInstance"></g:set>
		
<table class="table without-border">
	
	<g:if test="${params?.includeDetail == 'true' }">	
		<g:render template="projectByActivityByEmployee/ajaxActivityByEmployeeListDetail"></g:render>
	</g:if>
	
	<g:else>
		<g:if test="${projectActualLaborDtlInstanceList?.size() > 0}">				
			<g:each in="${projectActualLaborDtlInstanceList}" status="i" var="actualLaborInstance">
				<g:set value="${actualLaborInstance[0] }" var="lproj"></g:set>
		
				<g:if test="${!old_Proj.equals(lproj)}" >
						<g:if test="${!old_Proj.equals("")}">							
						<!-- Totals for a project -->
								
						<!-- Employee total Start-->		
													
						<g:if test="${oldEmployeeInstance}">
							<tr style="background-color: #f8f8f8;"> 
								<g:if test="${params?.includeDetail == 'true' }">	
									<td class="report-green-txt"><strong><g:message code="etech.report.employee.label" default="Employee"/>:</strong></td>	
								</g:if>
								<g:else> <td></td> </g:else>
								<td>${oldEmployeeInstance[11] ?: ''} ${oldEmployeeInstance[12] ?: ''}</td>
								<td></td>
								<td class="text-right">${MiscUtils.removePrecision(oldEmployeeInstance[3])}</td>
					
								<g:if test="${isHoursOnly == false}">
									<g:if test="${isStdCostView}">
										<td></td>											
										<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[5]}" format="#,##0.00" /></td>
										<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[6]}" format="#,##0.00" /></td>
										<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[7]}" format="#,##0.00" /></td>											
									</g:if>
									<g:if test="${isActualRateView}">
										<g:if test="${params?.printActualCost == 'true' }">
											<g:if test="${params?.includeDetail == 'true' }">	
												<td></td>
											</g:if>										
											 <td class="text-right"><g:formatNumber number="${oldEmployeeInstance[8]}" format="#,##0.00" /></td>
										</g:if>										
									</g:if>
								</g:if>
							</tr>
						</g:if>
						<!-- Employee total End-->
								
						<!-- Operation total  Start-->
						<g:set value="${projectByActivityMap.get(old_Proj+'_'+old_Oper)}" var="activityInstance"></g:set>
						<tr style="background-color: #eaeaea;">
							<g:if test="${params?.includeDetail == 'true' }">	
								<td ><strong><g:message code="etech.report.activity.label" default="Activity"/>:</strong></td>
								<td>${activityInstance[1]}</td>
							</g:if>
							<g:else>
								<td>${activityInstance[1]}</td>
								<td></td>
							</g:else>
							<td></td>
							<td class="text-right">${MiscUtils.removePrecision(activityInstance[2])}</td>
						
							<g:if test="${isHoursOnly == false}">
								<g:if test="${isStdCostView}">
									<td></td>
									<td class="text-right"><g:formatNumber number="${activityInstance[3]}" format="#,##0.00" /></td>
									<td class="text-right"><g:formatNumber number="${activityInstance[4]}" format="#,##0.00" /></td>
									<td class="text-right"><g:formatNumber number="${activityInstance[5]}" format="#,##0.00" /></td>
								</g:if>										
								<g:if test="${isActualRateView}">
									<g:if test="${params?.printActualCost == 'true' }">																
										<g:if test="${params?.includeDetail == 'true' }">	
											<td></td>
										</g:if>
										<td class="text-right"><g:formatNumber number="${activityInstance[6]}" format="#,##0.00" /></td>
									</g:if>									
								</g:if>
							</g:if>										
						</tr>
						<!-- Operation total  End-->
							
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
							${actualLaborInstance[0]}</span>
							<span class="marginright20">${actualLaborInstance[10]}</span>
							<span class="marginright20"><strong><g:message code="etech.report.customer.label" default="Customer"/>:</strong>
							${actualLaborInstance[9] ?: '' }</span>						 
						</td>												
					</tr>
					<!-- Project Header End -->
													
	    	   		<!-- Operation Header Start-->
					<tr class="report-dark">							
						<td><g:message code="etech.report.activity.label" default="Activity"/></td>
						<td><g:message code="etech.report.employee.label" default="Employee"/></td>
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
						<!-- Operation Header End-->	    
					</g:if> <!-- else  !old_Proj.equals(lproj) -->
					
					<g:set value="${actualLaborInstance[2] }" var="loper" ></g:set>				
					<g:if test="${old_Oper != loper && old_Proj == lproj}">
						 
						<!-- Employee total 2 Start-->		
						<g:if test="${oldEmployeeInstance}">
							<tr style="background-color: #f8f8f8;"> 
								<g:if test="${params?.includeDetail == 'true' }">	
									<td class="report-green-txt"><strong><g:message code="etech.report.employee.label" default="Employee"/>:</strong></td>	
								</g:if>
								<g:else> <td></td> </g:else>
								<td>${oldEmployeeInstance[11] ?: ''} ${oldEmployeeInstance[12] ?: ''}</td>
								<td></td>
								<td class="text-right">${MiscUtils.removePrecision(employeeInstance[3])}</td>
					
								<g:if test="${isHoursOnly == false}">
									<g:if test="${isStdCostView}">
										<td></td>											
										<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[5]}" format="#,##0.00" /></td>
										<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[6]}" format="#,##0.00" /></td>
										<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[7]}" format="#,##0.00" /></td>											
									</g:if>
									<g:if test="${isActualRateView}">
										<g:if test="${params?.printActualCost == 'true' }">
											<g:if test="${params?.includeDetail == 'true' }">	
												<td></td>
											</g:if>										
											 <td class="text-right"><g:formatNumber number="${oldEmployeeInstance[8]}" format="#,##0.00" /></td>
										</g:if>										
									</g:if>
								</g:if>
							</tr>
						</g:if>
						<!-- Employee total 2 End-->
								
						<!-- Operation total 2 Start-->
						<g:set value="${projectByActivityMap.get(old_Proj+'_'+old_Oper)}" var="activityInstance"></g:set>
						<tr style="background-color: #eaeaea;">
							<g:if test="${params?.includeDetail == 'true' }">	
								<td ><strong><g:message code="etech.report.activity.label" default="Activity"/>:</strong></td>
								<td>${activityInstance[1]}</td>
							</g:if>
							<g:else>
								<td>${activityInstance[1]}</td>
								<td></td>
							</g:else>
							<td></td>
							<td class="text-right">${MiscUtils.removePrecision(activityInstance[2])}</td>
						
							<g:if test="${isHoursOnly == false}">
								<g:if test="${isStdCostView}">
									<td></td>
									<td class="text-right"><g:formatNumber number="${activityInstance[3]}" format="#,##0.00" /></td>
									<td class="text-right"><g:formatNumber number="${activityInstance[4]}" format="#,##0.00" /></td>
									<td class="text-right"><g:formatNumber number="${activityInstance[5]}" format="#,##0.00" /></td>
								</g:if>										
								<g:if test="${isActualRateView}">
									<g:if test="${params?.printActualCost == 'true' }">																
										<g:if test="${params?.includeDetail == 'true' }">	
											<td></td>
										</g:if>
										<td class="text-right"><g:formatNumber number="${activityInstance[6]}" format="#,##0.00" /></td>
									</g:if>									
								</g:if>
							</g:if>										
						</tr>
						<!-- Operation total 2 End-->
					</g:if>
							
					<g:set value="${actualLaborInstance[1]}" var="lempno"></g:set>
					<g:set value="${projectByEmployeeMap?.get(old_Proj+'_'+old_Emp+'_'+old_Oper)}" var="employeeInstance"></g:set> 
					
					<g:if test="${old_Emp != lempno && old_Oper == loper && old_Proj == lproj}" >
					
						<g:if test="${oldEmployeeInstance}">
							<tr style="background-color: #f8f8f8;"> 
								<g:if test="${params?.includeDetail == 'true' }">	
									<td class="report-green-txt"><strong><g:message code="etech.report.employee.label" default="Employee"/>:</strong></td>	
								</g:if>
								<g:else> <td></td> </g:else>
								<td>${oldEmployeeInstance[11] ?: ''} ${oldEmployeeInstance[12] ?: ''}</td>
								<td></td>
								<td class="text-right">${MiscUtils.removePrecision(oldEmployeeInstance[3])}</td>
								
								<g:if test="${isHoursOnly == false}">
									<g:if test="${isStdCostView}">
										<td></td>											
										<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[5]}" format="#,##0.00" /></td>
										<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[6]}" format="#,##0.00" /></td>
										<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[7]}" format="#,##0.00" /></td>											
									</g:if>
									<g:if test="${isActualRateView}">
										<g:if test="${params?.printActualCost == 'true' }">
											<g:if test="${params?.includeDetail == 'true' }">	
												<td></td>
											</g:if>										
											 <td class="text-right"><g:formatNumber number="${oldEmployeeInstance[8]}" format="#,##0.00" /></td>
										</g:if>										
									</g:if>
								</g:if>
							</tr>
						</g:if>
					</g:if>
						 
					
					<g:set value="${lproj }" var="old_Proj"></g:set>
					<g:set value="${loper}" var="old_Oper"></g:set>
					<g:set value="${lempno}" var="old_Emp"></g:set>
					<g:set value="${projectByEmployeeMap?.get(old_Proj+'_'+old_Emp+'_'+old_Oper)}" var="employeeInstance"></g:set> 
					<g:set value="${employeeInstance}" var="oldEmployeeInstance"></g:set>
								
			</g:each>
				 
			 <g:if test="false">
			<!-- Employee total After For Loop Start-->		
													
						<g:if test="${oldEmployeeInstance}">
							<tr style="background-color: #f8f8f8;"> 
								<g:if test="${params?.includeDetail == 'true' }">	
									<td class="report-green-txt"><strong><g:message code="etech.report.employee.label" default="Employee"/>:</strong></td>	
								</g:if>
								<g:else> <td></td> </g:else>
								<td>${oldEmployeeInstance[11] ?: ''} ${oldEmployeeInstance[12] ?: ''}</td>
								<td></td>
								<td class="text-right">${MiscUtils.removePrecision(employeeInstance[3])}</td>
					
								<g:if test="${isHoursOnly == false}">
									<g:if test="${isStdCostView}">
										<td></td>											
										<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[5]}" format="#,##0.00" /></td>
										<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[6]}" format="#,##0.00" /></td>
										<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[7]}" format="#,##0.00" /></td>											
									</g:if>
									<g:if test="${isActualRateView}">
										<g:if test="${params?.printActualCost == 'true' }">
											<g:if test="${params?.includeDetail == 'true' }">	
												<td></td>
											</g:if>										
											 <td class="text-right"><g:formatNumber number="${oldEmployeeInstance[8]}" format="#,##0.00" /></td>
										</g:if>										
									</g:if>
								</g:if>
							</tr>
						</g:if>
						
			<!-- Employee total After For Loop End-->
			
	        <!-- Operation total After For Loop Start-->
						<g:set value="${projectByActivityMap.get(old_Proj+'_'+old_Oper)}" var="activityInstance"></g:set>
						<tr style="background-color: #eaeaea;">
							<g:if test="${params?.includeDetail == 'true' }">	
								<td ><strong><g:message code="etech.report.activity.label" default="Activity"/>:</strong></td>
								<td>${activityInstance[1]}</td>
							</g:if>
							<g:else>
								<td>${activityInstance[1]}</td>
								<td></td>
							</g:else>
							<td></td>
							<td class="text-right">${MiscUtils.removePrecision(activityInstance[2])}</td>
						
							<g:if test="${isHoursOnly == false}">
								<g:if test="${isStdCostView}">
									<td></td>
									<td class="text-right"><g:formatNumber number="${activityInstance[3]}" format="#,##0.00" /></td>
									<td class="text-right"><g:formatNumber number="${activityInstance[4]}" format="#,##0.00" /></td>
									<td class="text-right"><g:formatNumber number="${activityInstance[5]}" format="#,##0.00" /></td>
								</g:if>										
								<g:if test="${isActualRateView}">
									<g:if test="${params?.printActualCost == 'true' }">																
										<g:if test="${params?.includeDetail == 'true' }">	
											<td></td>
										</g:if>
										<td class="text-right"><g:formatNumber number="${activityInstance[6]}" format="#,##0.00" /></td>
									</g:if>									
								</g:if>
							</g:if>										
						</tr>
			<!-- Operation total After For Loop End-->
			
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
		</g:if>		
		<g:else>
			<tr><td colspan="10" style="color: red;" ><g:message code="default.record.not.found" /></td></tr>
		</g:else>
	</g:else>	
	<g:if test="${projectActualLaborDtlInstanceList?.size() > 0}">	
		<tr><td colspan="10" class="border-left-right-none" height="30"></td></tr>		
		<tr  style="background-color: #e0e0e0;">								
			<td><strong><g:message code="etech.report.report.grand.total.label" default="Report Grand Total"/>:</strong></td>
			<td></td><td></td>
			<td class="text-right">${MiscUtils.removePrecision(reportTotal[0][0]?:0.00)}</td>
			<g:if test="${session['logedInUser']?.hoursOnly == false}">
				<g:if test="${isStdCostView}">		
					<td></td>
					<td class="text-right"><g:formatNumber number="${reportTotal[0][1]?:0.00}" format="#,##0.00" /></td>
					<td class="text-right"><g:formatNumber number="${reportTotal[0][2]?:0.00}" format="#,##0.00" /></td>
					<td class="text-right"><g:formatNumber number="${reportTotal[0][3]?:0.00}" format="#,##0.00" /></td>
				</g:if>
				<g:if test="${isActualRateView}">
					<g:if test="${params?.printActualCost == 'true' }">	
						<g:if test="${params?.includeDetail == 'true' }">							
							<td></td>
						</g:if> 
						<td class="text-right"><g:formatNumber number="${reportTotal[0][4]?:0.00}" format="#,##0.00" /></td>
					</g:if>
				</g:if>
			</g:if>										
		</tr>			
	</g:if>
</table>		
<div class="pagination margintopbottom15">
	<g:paginate total="${projectActivityTotalCount ?: 0}"  params="${params}"/>
</div>				
		
		