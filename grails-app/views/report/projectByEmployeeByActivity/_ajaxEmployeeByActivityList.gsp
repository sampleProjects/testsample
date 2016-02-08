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
<g:set value="" var="activityInstance"></g:set>
<g:set value="" var="oldActivityInstance"></g:set>
		
<table class="table without-border">	
	<g:if test="${params?.includeDetail == 'true' }">	
		<g:render template="projectByEmployeeByActivity/ajaxEmployeeByActivityDetailList"></g:render>
	</g:if>	
	<g:else>		
		<g:if test="${projectActualLaborDtlInstanceList?.size() > 0}">
				
		<g:each in="${projectActualLaborDtlInstanceList}" status="i" var="actualLaborInstance">
		
		<g:set value="${actualLaborInstance[0] }" var="lproj"></g:set>		
		<g:if test="${!old_Proj.equals(lproj)}" >
			<g:if test="${!old_Proj.equals("")}">
				
				<!-- Operation Row when Project is null Start-->
				<g:if test="${oldActivityInstance}">
					<tr style="background-color: #f8f8f8;">							
					<td></td>
					<td>${oldActivityInstance[1]}</td>					
					<td class="text-right">${MiscUtils.removePrecision(oldActivityInstance[3])}</td>					
						<g:if test="${isHoursOnly == false}">
							<g:if test="${isStdCostView}">								
								<td class="text-right"><g:formatNumber number="${oldActivityInstance[4]}" format="#,##0.00" /></td>
								<td class="text-right"><g:formatNumber number="${oldActivityInstance[5]}" format="#,##0.00" /></td>
								<td class="text-right"><g:formatNumber number="${oldActivityInstance[6]}" format="#,##0.00" /></td>
							</g:if>										
							<g:if test="${isActualRateView}">
								<g:if test="${params?.printActualCost == 'true' }">
									<td class="text-right"><g:formatNumber number="${oldActivityInstance[7]}" format="#,##0.00" /></td>
								</g:if>									
							</g:if>
						</g:if>										
					</tr>	
				</g:if>
					
				<!-- Operation Row when Project is null End-->
				
				<g:set value="${projectByEmployeeActivityMap.get(old_Proj+'_'+old_Emp)}" var="employeeInstance"></g:set>
				<!-- Employee Row when Project is null Start-->
				<tr style="background-color: #eaeaea;"> 
					<td>${employeeInstance[7] ?: ''} ${employeeInstance[8] ?: ''}</td>
					<td></td>
					<td class="text-right">${MiscUtils.removePrecision(employeeInstance[2])}</td>				
					<g:if test="${isHoursOnly == false}">
						<g:if test="${isStdCostView}">
							<td class="text-right"><g:formatNumber number="${employeeInstance[3]}" format="#,##0.00" /></td>
							<td class="text-right"><g:formatNumber number="${employeeInstance[4]}" format="#,##0.00" /></td>
							<td class="text-right"><g:formatNumber number="${employeeInstance[5]}" format="#,##0.00" /></td>											
						</g:if>
						<g:if test="${isActualRateView}">
							<g:if test="${params?.printActualCost == 'true' }">										 									
								 <td class="text-right"><g:formatNumber number="${employeeInstance[6]}" format="#,##0.00" /></td>
							</g:if>										
						</g:if>
					</g:if>
				</tr>	 
				<!-- Employee Row when Project is null End-->
				
				<!-- Project total Row Start-->
				<g:set value="${projectTotalMap.get(old_Proj)}" var="projectTotalInstance"></g:set> 
				<tr style="background-color: #cdcdcd;">								
					<td class="report-green-txt"><strong><g:message code="etech.report.project.label" default="Project:"/></strong></td>
					<td>${projectTotalInstance[0]}</td>					
					<td class="text-right">${MiscUtils.removePrecision(projectTotalInstance[1])}</td>
					<g:if test="${isHoursOnly == false}">
						<g:if test="${isStdCostView}">							
							<td class="text-right"><g:formatNumber number="${projectTotalInstance[2] }" format="#,##0.00" /></td>
							<td class="text-right"><g:formatNumber number="${projectTotalInstance[3]}" format="#,##0.00" /></td>
							<td class="text-right"><g:formatNumber number="${projectTotalInstance[4]}" format="#,##0.00" /></td>
						</g:if>
						<g:if test="${isActualRateView}">
							<g:if test="${params?.printActualCost == 'true' }">																			
								 <td class="text-right"><g:formatNumber number="${projectTotalInstance[5]}" format="#,##0.00" /></td>
							</g:if>
						</g:if>
					</g:if>
				</tr>	
				<!-- Project total Row End-->
				<tr><td colspan="10" class="border-left-right-none" height="30"></td></tr>											
			</g:if>
				
			<!-- Project Header Start -->
			<tr class="report-tr">										
				<td colspan="7">											
					<span class="marginright20"><strong><g:message code="etech.report.project.label" default="Project"/>:</strong>											
					${actualLaborInstance[0]}</span>
					<span class="marginright20">${actualLaborInstance[9]}</span>
					<span class="marginright20"><strong><g:message code="etech.report.customer.label" default="Customer"/>:</strong>
					${actualLaborInstance[8] ?: '' }</span>					
				</td>												
			</tr>
			<!-- Project Header End -->
												
    	   	<!-- Header Start-->
			<tr class="report-dark">							
				<td><g:message code="etech.report.employee.label" default="Employee"/></td>
				<td><g:message code="etech.report.activity.label" default="Activity"/></td>
				<td class="text-right"><g:message code="etech.report.hours.label" default="Hours"/></td>
				<g:if test="${isHoursOnly == false}">
					<g:if test="${isStdCostView}">
						<td class="text-right"><g:message code="etech.report.std.cost.label" default="Std Cost"/></td>
						<td class="text-right"><g:message code="etech.report.o/h.label" default="O/H"/></td>
						<td class="text-right"><g:message code="etech.report.total.cost.label" default="Total Cost"/></td>
					</g:if>	
					<g:if test="${isActualRateView}">
						<g:if test="${params?.printActualCost == 'true' }">
							<td class="text-right"><g:message code="etech.report.actual.wage.label" default="Actual Wage"/></td>								 
						</g:if>
					</g:if>
				</g:if>
			</tr>			
			<!--   Header End-->	    
		</g:if> <!-- else  !old_Proj.equals(lproj) -->
	
		<g:set value="${actualLaborInstance[2]}" var="lempno"></g:set>
		
		<g:if test="${old_Emp != lempno && old_Proj == lproj}">
			<!-- Operation total 2 Start-->					
			<tr style="background-color: #f8f8f8;"> 
				<td></td>
				<td>${oldActivityInstance[1]}</td>		
				<td class="text-right">${MiscUtils.removePrecision(oldActivityInstance[3])}</td>
				<g:if test="${isHoursOnly == false}">
					<g:if test="${isStdCostView}">
						<td class="text-right"><g:formatNumber number="${oldActivityInstance[4]}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${oldActivityInstance[5]}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${oldActivityInstance[6]}" format="#,##0.00" /></td>
					</g:if>										
					<g:if test="${isActualRateView}">
						<g:if test="${params?.printActualCost == 'true' }">
							<td class="text-right"><g:formatNumber number="${oldActivityInstance[7]}" format="#,##0.00" /></td>
						</g:if>									
					</g:if>
				</g:if>										
			</tr>			
			<!-- Operation total 2 End-->
			
			<g:set value="${projectByEmployeeActivityMap.get(old_Proj+'_'+old_Emp)}" var="employeeInstance"></g:set>
			<!-- Employee Row total 2 Start-->
			<tr style="background-color: #eaeaea;">
				<td>${employeeInstance[7] ?: ''} ${employeeInstance[8] ?: ''}</td>
				<td></td>
				<td class="text-right">${MiscUtils.removePrecision(employeeInstance[2])}</td>
				<g:if test="${isHoursOnly == false}">
					<g:if test="${isStdCostView}">
						<td class="text-right"><g:formatNumber number="${employeeInstance[3]}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${employeeInstance[4]}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${employeeInstance[5]}" format="#,##0.00" /></td>											
					</g:if>
					<g:if test="${isActualRateView}">
						<g:if test="${params?.printActualCost == 'true' }">																			
							 <td class="text-right"><g:formatNumber number="${employeeInstance[6]}" format="#,##0.00" /></td>
						</g:if>										
					</g:if>
				</g:if>
			</tr>
			<!-- Employee Row total 2 End-->
		</g:if>			
		
		<g:set value="${actualLaborInstance[1]}" var="loper"></g:set>				
				
		<g:if test="${old_Oper != loper && old_Emp == lempno && old_Proj == lproj}" >
			<tr style="background-color: #f8f8f8;"> 
				<td></td>
				<td>${oldActivityInstance[1]}</td>
				<td class="text-right">${MiscUtils.removePrecision(oldActivityInstance[3])}</td>
				<g:if test="${isHoursOnly == false}">
					<g:if test="${isStdCostView}">			
						<td class="text-right"><g:formatNumber number="${oldActivityInstance[4]}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${oldActivityInstance[5]}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${oldActivityInstance[6]}" format="#,##0.00" /></td>
					</g:if>										
					<g:if test="${isActualRateView}">
						<g:if test="${params?.printActualCost == 'true' }">																
							<td class="text-right"><g:formatNumber number="${oldActivityInstance[7]}" format="#,##0.00" /></td>
						</g:if>									
					</g:if>
				</g:if>										
			</tr>	
		</g:if>
		
		
		<g:set value="${lproj }" var="old_Proj"></g:set>
		<g:set value="${loper}" var="old_Oper"></g:set>
		<g:set value="${lempno}" var="old_Emp"></g:set>		 
		<g:set value="${actualLaborInstance}" var="oldActivityInstance"></g:set>		
	</g:each>
	
		<tr style="background-color: #f8f8f8;"> 
			<td></td>
			<td>${oldActivityInstance[1]}</td>
			<td class="text-right">${MiscUtils.removePrecision(oldActivityInstance[3])}</td>
			<g:if test="${isHoursOnly == false}">
				<g:if test="${isStdCostView}">
					<td class="text-right"><g:formatNumber number="${oldActivityInstance[4]}" format="#,##0.00" /></td>
					<td class="text-right"><g:formatNumber number="${oldActivityInstance[5]}" format="#,##0.00" /></td>
					<td class="text-right"><g:formatNumber number="${oldActivityInstance[6]}" format="#,##0.00" /></td>
				</g:if>										
				<g:if test="${isActualRateView}">
					<g:if test="${params?.printActualCost == 'true' }">																
						<td class="text-right"><g:formatNumber number="${oldActivityInstance[7]}" format="#,##0.00" /></td>
					</g:if>									
				</g:if>
			</g:if>										
		</tr>
						
		<g:set value="${projectByEmployeeActivityMap.get(old_Proj+'_'+old_Emp)}" var="employeeInstance"></g:set>			
		<tr style="background-color: #eaeaea;">
			<td>${employeeInstance[7] ?: ''} ${employeeInstance[8] ?: ''}</td>
			<td></td>
			<td class="text-right">${MiscUtils.removePrecision(employeeInstance[2])}</td>
			<g:if test="${isHoursOnly == false}">
				<g:if test="${isStdCostView}">
					<td class="text-right"><g:formatNumber number="${employeeInstance[3]}" format="#,##0.00" /></td>
					<td class="text-right"><g:formatNumber number="${employeeInstance[4]}" format="#,##0.00" /></td>
					<td class="text-right"><g:formatNumber number="${employeeInstance[5]}" format="#,##0.00" /></td>											
				</g:if>
				<g:if test="${isActualRateView}">
					<g:if test="${params?.printActualCost == 'true' }">
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
			<td class="text-right">${MiscUtils.removePrecision(projectTotalInstance[1]) }</td>
			<g:if test="${isHoursOnly == false}">
				<g:if test="${isStdCostView}">
					<td class="text-right"><g:formatNumber number="${projectTotalInstance[2] }" format="#,##0.00" /></td>
					<td class="text-right"><g:formatNumber number="${projectTotalInstance[3]}" format="#,##0.00" /></td>
					<td class="text-right"><g:formatNumber number="${projectTotalInstance[4]}" format="#,##0.00" /></td>
				</g:if>
				<g:if test="${isActualRateView}">
					<g:if test="${params?.printActualCost == 'true' }">	
						 <td class="text-right"><g:formatNumber number="${projectTotalInstance[5]}" format="#,##0.00" /></td>
					</g:if>
				</g:if>
			</g:if>
		</tr>					
	</g:if>	
	<g:else>
		<tr><td colspan="10" style="color: red;" ><g:message code="default.record.not.found" /></td></tr>
	</g:else>
</g:else>	
	 <g:if test="${projectActualLaborDtlInstanceList?.size() > 0}">	
		<tr><td colspan="10" class="border-left-right-none" height="30"></td></tr>		
		<tr  style="background-color: #e0e0e0;">								
			<td><strong><g:message code="etech.report.report.grand.total.label" default="Report Grand Total"/>:</strong></td>
			<g:if test="${params?.includeDetail == 'true' }">
				<td></td>
			</g:if>
			<td></td>
			<td class="text-right">${MiscUtils.removePrecision(reportTotal[0][0]?:0.00)}</td>
			<g:if test="${session['logedInUser']?.hoursOnly == false}">
				<g:if test="${isStdCostView}">	
					<g:if test="${params?.includeDetail == 'true' }">
						<td></td>
					</g:if>
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
	<!-- Report Total End -->
</table>		
<div class="pagination margintopbottom15">
	<g:paginate total="${projectActivityTotalCount ?: 0}"  params="${params}"/>
</div>