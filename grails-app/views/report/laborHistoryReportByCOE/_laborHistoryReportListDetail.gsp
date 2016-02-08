<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
<%@ page import="org.solcorp.etech.PermissionType" %>	
<%@page import="org.solcorp.etech.utils.MiscUtils"%>
 
<g:if test="${projectActualLaborDtlInstanceList?.size() > 0}">		
	<g:each in="${projectActualLaborDtlInstanceList}" status="i" var="actualLaborInstance">
		
		<g:set value="${actualLaborInstance[0]}" var="lCoe"></g:set>		
		<g:set value="${actualLaborInstance[1]}" var="lproj"></g:set>	
		<g:if test="${!old_Proj.equals(lproj)}" >
			<g:if test="${!old_Proj.equals("")}">							
							
				<!-- Employee total Start-->
				<g:if test="${oldEmployeeInstance}">
					<tr style="background-color: #f8f8f8;"> 
						<td></td>
						<td>${oldEmployeeInstance[11] ?: ''} ${oldEmployeeInstance[12] ?: ''}</td>
						<td class="text-right">${MiscUtils.removePrecision(oldEmployeeInstance[4])}</td>
						<g:if test="${isHoursOnly == false}">
							<g:if test="${isStdCostView}">
								<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[5]}" format="#,##0.00" /></td>
								<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[6]}" format="#,##0.00" /></td>
								<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[7]}" format="#,##0.00" /></td>											
							</g:if>
							<g:if test="${isActualRateView}">
								<g:if test="${params?.printActualCost == 'true' }">
									 <td class="text-right"><g:formatNumber number="${oldEmployeeInstance[8]}" format="#,##0.00" /></td>
								</g:if>										
							</g:if>
						</g:if>
					</tr>
				</g:if>
				<!-- Employee total End-->
							
				<!-- Operation total  Start-->
				<g:set value="${projectByActivityMap.get(old_coe+'_'+old_Proj+'_'+old_Oper)}" var="activityInstance"></g:set>
				<tr style="background-color: #eaeaea;">
					<td>${activityInstance[1]}</td>
					<td></td>
					<td class="text-right">${MiscUtils.removePrecision(activityInstance[2])}</td>
					<g:if test="${isHoursOnly == false}">
						<g:if test="${isStdCostView}">
							<td class="text-right"><g:formatNumber number="${activityInstance[3]}" format="#,##0.00" /></td>
							<td class="text-right"><g:formatNumber number="${activityInstance[4]}" format="#,##0.00" /></td>
							<td class="text-right"><g:formatNumber number="${activityInstance[5]}" format="#,##0.00" /></td>
						</g:if>										
						<g:if test="${isActualRateView}">
							<g:if test="${params?.printActualCost == 'true' }">																
								<td class="text-right"><g:formatNumber number="${activityInstance[6]}" format="#,##0.00" /></td>
							</g:if>									
						</g:if>
					</g:if>										
				</tr>
				<!-- Operation total  End-->
						
				<!-- Project total Start-->
				<g:set value="${projectTotalMap.get(old_coe+'_'+old_Proj)}" var="projectTotalInstance"></g:set> 
				<tr style="background-color: #cdcdcd;">	
					<td class="report-green-txt"><strong><g:message code="etech.report.project.label" default="Project:"/></strong></td>
					<td>${projectTotalInstance[1]}</td>	
					<td class="text-right">${MiscUtils.removePrecision(projectTotalInstance[2])}</td>
					<g:if test="${isHoursOnly == false}">
						<g:if test="${isStdCostView}">
							<td class="text-right"><g:formatNumber number="${projectTotalInstance[3] }" format="#,##0.00" /></td>
							<td class="text-right"><g:formatNumber number="${projectTotalInstance[4]}" format="#,##0.00" /></td>
							<td class="text-right"><g:formatNumber number="${projectTotalInstance[5]}" format="#,##0.00" /></td>
						</g:if>
						<g:if test="${isActualRateView}">
							<g:if test="${params?.printActualCost == 'true' }">	
								 <td class="text-right"><g:formatNumber number="${projectTotalInstance[6]}" format="#,##0.00" /></td>
							</g:if>
						</g:if>
					</g:if>
				</tr>	
				<!-- Project total End-->													
			</g:if>
			
			<g:if test="${!old_coe.equals(lCoe)}" >
				<g:if test="${!old_coe.equals("")}" >
					<g:set value="${coeTotalMap.get(old_coe)}" var="coeInstance"></g:set>
					<tr style="background-color: #b5b5b5;">										
						<td class="report-green-txt"><strong><g:message code="etech.report.coe.total.label" default="COE Total:"/></strong></td>
						<td>${coeInstance[0]}</td>						
						<td class="text-right">${MiscUtils.removePrecision(coeInstance[1])}</td>
						<g:if test="${isHoursOnly == false}">
							<g:if test="${isStdCostView}">
								<td class="text-right"><g:formatNumber number="${coeInstance[2]}" format="#,##0.00" /></td>
								<td class="text-right"><g:formatNumber number="${coeInstance[3]}" format="#,##0.00" /></td>
								<td class="text-right"><g:formatNumber number="${coeInstance[4]}" format="#,##0.00" /></td>
							</g:if>										
							<g:if test="${isActualRateView}">
								<g:if test="${params?.printActualCost == 'true' }">																
									<td class="text-right"><g:formatNumber number="${coeInstance[5]}" format="#,##0.00" /></td>
								</g:if>									
							</g:if>
						</g:if>										
					</tr>
				</g:if>				 
				<tr><td colspan="10" class="border-left-right-none" height="30"></td></tr>		
				<tr class="report-tr">
					<td  colspan="10">
					<span class="marginright20"><strong>
					<g:message code="etech.report.coe.labor.history.coe.code.label" default="COE Code"/>:</strong>
					${lCoe}</span>					
					</td>
				</tr>
			</g:if>
		
			<!-- Project Header Start -->
			<tr class="light-gray">									
				<td colspan="10">											
					<span class="marginright20"><strong><g:message code="etech.report.project.label" default="Project"/>:</strong>											
					${actualLaborInstance[1]}</span>
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
			<!-- Operation Header End-->	    
		</g:if> <!-- else  !old_Proj.equals(lproj) -->
				
		<g:set value="${actualLaborInstance[3] }" var="loper" ></g:set>				
		<g:if test="${old_Oper != loper && old_Proj == lproj}">
						 
			<!-- Employee total 2 Start-->		
			<g:if test="${oldEmployeeInstance}">
				<tr style="background-color: #f8f8f8;"> 
					<td></td>
					<td>${oldEmployeeInstance[11] ?: ''} ${oldEmployeeInstance[12] ?: ''}</td>
					<td class="text-right">${MiscUtils.removePrecision(oldEmployeeInstance[4])}</td>
					<g:if test="${isHoursOnly == false}">
						<g:if test="${isStdCostView}">
							<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[5]}" format="#,##0.00" /></td>
							<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[6]}" format="#,##0.00" /></td>
							<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[7]}" format="#,##0.00" /></td>											
						</g:if>
						<g:if test="${isActualRateView}">
							<g:if test="${params?.printActualCost == 'true' }">
								 <td class="text-right"><g:formatNumber number="${oldEmployeeInstance[8]}" format="#,##0.00" /></td>
							</g:if>										
						</g:if>
					</g:if>
				</tr>
			</g:if>
			<!-- Employee total 2 End-->
								
			<!-- Operation total 2 Start-->
			<g:set value="${projectByActivityMap.get(old_coe+'_'+old_Proj+'_'+old_Oper)}" var="activityInstance"></g:set>
			<tr style="background-color: #eaeaea;">
				<td>${activityInstance[1]}</td>
				<td></td>
				<td class="text-right">${MiscUtils.removePrecision(activityInstance[2])}</td>
				<g:if test="${isHoursOnly == false}">
					<g:if test="${isStdCostView}">
						<td class="text-right"><g:formatNumber number="${activityInstance[3]}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${activityInstance[4]}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${activityInstance[5]}" format="#,##0.00" /></td>
					</g:if>										
					<g:if test="${isActualRateView}">
						<g:if test="${params?.printActualCost == 'true' }">																
							<td class="text-right"><g:formatNumber number="${activityInstance[6]}" format="#,##0.00" /></td>
						</g:if>									
					</g:if>
				</g:if>										
			</tr>
			<!-- Operation total 2 End-->
		</g:if>							
		<g:set value="${actualLaborInstance[2]}" var="lempno"></g:set>
					
		<g:if test="${old_Emp != lempno && old_Oper == loper && old_Proj == lproj}" >
			<!-- Employee total Start-->		
			<g:if test="${oldEmployeeInstance}">
				<tr style="background-color: #f8f8f8;"> 
					<td></td>
					<td>${oldEmployeeInstance[11] ?: ''} ${oldEmployeeInstance[12] ?: ''}</td>
					<td class="text-right">${MiscUtils.removePrecision(oldEmployeeInstance[4])}</td>
					<g:if test="${isHoursOnly == false}">
						<g:if test="${isStdCostView}">
							<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[5]}" format="#,##0.00" /></td>
							<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[6]}" format="#,##0.00" /></td>
							<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[7]}" format="#,##0.00" /></td>											
						</g:if>
						<g:if test="${isActualRateView}">
							<g:if test="${params?.printActualCost == 'true' }">
								 <td class="text-right"><g:formatNumber number="${oldEmployeeInstance[8]}" format="#,##0.00" /></td>
							</g:if>										
						</g:if>
					</g:if>
				</tr>
			</g:if>
			<!-- Employee total Start-->
		</g:if>
		
		<g:if test="${!old_coe.equals(lCoe) && old_Proj.equals(lproj)}" >
				<g:if test="${!old_coe.equals("")}" >
					<g:set value="${coeTotalMap.get(old_coe)}" var="coeInstance"></g:set>
					<!-- Project total Start-->
				<g:set value="${projectTotalMap.get(old_coe+'_'+old_Proj)}" var="projectTotalInstance"></g:set> 
				<tr style="background-color: #cdcdcd;">	
					<td class="report-green-txt"><strong><g:message code="etech.report.project.label" default="Project:"/></strong></td>
					<td>${projectTotalInstance[1]}</td>	
					<td class="text-right">${MiscUtils.removePrecision(projectTotalInstance[2])}</td>
					<g:if test="${isHoursOnly == false}">
						<g:if test="${isStdCostView}">
							<td class="text-right"><g:formatNumber number="${projectTotalInstance[3] }" format="#,##0.00" /></td>
							<td class="text-right"><g:formatNumber number="${projectTotalInstance[4]}" format="#,##0.00" /></td>
							<td class="text-right"><g:formatNumber number="${projectTotalInstance[5]}" format="#,##0.00" /></td>
						</g:if>
						<g:if test="${isActualRateView}">
							<g:if test="${params?.printActualCost == 'true' }">	
								 <td class="text-right"><g:formatNumber number="${projectTotalInstance[6]}" format="#,##0.00" /></td>
							</g:if>
						</g:if>
					</g:if>
				</tr>	
				<!-- Project total End-->				
					<tr style="background-color: #b5b5b5;">										
						<td class="report-green-txt"><strong><g:message code="etech.report.coe.total.label" default="COE Total:"/></strong></td>
						<td>${coeInstance[0]}</td>						
						<td class="text-right">${MiscUtils.removePrecision(coeInstance[1])}</td>
						<g:if test="${isHoursOnly == false}">
							<g:if test="${isStdCostView}">
								<td class="text-right"><g:formatNumber number="${coeInstance[2]}" format="#,##0.00" /></td>
								<td class="text-right"><g:formatNumber number="${coeInstance[3]}" format="#,##0.00" /></td>
								<td class="text-right"><g:formatNumber number="${coeInstance[4]}" format="#,##0.00" /></td>
							</g:if>										
							<g:if test="${isActualRateView}">
								<g:if test="${params?.printActualCost == 'true' }">																
									<td class="text-right"><g:formatNumber number="${coeInstance[5]}" format="#,##0.00" /></td>
								</g:if>									
							</g:if>
						</g:if>										
					</tr>
				</g:if>				 
				<tr><td colspan="10" class="border-left-right-none" height="30"></td></tr>		
				<tr class="report-tr">
					<td  colspan="10">
					<span class="marginright20"><strong>
					<g:message code="etech.report.coe.labor.history.coe.code.label" default="COE Code"/>:</strong>
					${lCoe}</span>					
					</td>
				</tr>
				<!-- Project Header Start -->
				<tr class="light-gray">									
					<td colspan="10">											
						<span class="marginright20"><strong><g:message code="etech.report.project.label" default="Project"/>:</strong>											
						${actualLaborInstance[1]}</span>
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
			<!-- Operation Header End-->	
		</g:if>						 	 
		<g:set value="${lCoe}" var="old_coe"></g:set>			
		<g:set value="${lproj }" var="old_Proj"></g:set>
		<g:set value="${loper}" var="old_Oper"></g:set>
		<g:set value="${lempno}" var="old_Emp"></g:set>		 
		<g:set value="${actualLaborInstance}" var="oldEmployeeInstance"></g:set>					
	</g:each>			 
		 	 
		<!-- Employee total After For Loop Start-->												
		<g:if test="${oldEmployeeInstance}">
			<tr style="background-color: #f8f8f8;"> 
				<td></td>
				<td>${oldEmployeeInstance[11] ?: ''} ${oldEmployeeInstance[12] ?: ''}</td>
				<td class="text-right">${MiscUtils.removePrecision(oldEmployeeInstance[4])}</td>
				<g:if test="${isHoursOnly == false}">
					<g:if test="${isStdCostView}">
						<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[5]}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[6]}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${oldEmployeeInstance[7]}" format="#,##0.00" /></td>											
					</g:if>
					<g:if test="${isActualRateView}">
						<g:if test="${params?.printActualCost == 'true' }">
							 <td class="text-right"><g:formatNumber number="${oldEmployeeInstance[8]}" format="#,##0.00" /></td>
						</g:if>										
					</g:if>
				</g:if>
			</tr>
		</g:if>					
		
		<g:set value="${projectByActivityMap.get(old_coe+'_'+old_Proj+'_'+old_Oper)}" var="activityInstance"></g:set>
		<tr style="background-color: #eaeaea;">
			<td>${activityInstance[1]}</td>
			<td></td>
			<td class="text-right">${MiscUtils.removePrecision(activityInstance[2])}</td>
			<g:if test="${isHoursOnly == false}">
				<g:if test="${isStdCostView}">
					<td class="text-right"><g:formatNumber number="${activityInstance[3]}" format="#,##0.00" /></td>
					<td class="text-right"><g:formatNumber number="${activityInstance[4]}" format="#,##0.00" /></td>
					<td class="text-right"><g:formatNumber number="${activityInstance[5]}" format="#,##0.00" /></td>
				</g:if>										
				<g:if test="${isActualRateView}">
					<g:if test="${params?.printActualCost == 'true' }">																
						<td class="text-right"><g:formatNumber number="${activityInstance[6]}" format="#,##0.00" /></td>
					</g:if>									
				</g:if>
			</g:if>										
		</tr>	
		
        <g:set value="${projectTotalMap.get(old_coe+'_'+old_Proj)}" var="projectTotalInstance"></g:set> 
		<tr style="background-color: #cdcdcd;">								
			<td class="report-green-txt"><strong><g:message code="etech.report.project.label" default="Project:"/></strong></td>
			<td>${projectTotalInstance[1]}</td>	
			<td class="text-right">${MiscUtils.removePrecision(projectTotalInstance[2]) }</td>
			<g:if test="${isHoursOnly == false}">
				<g:if test="${isStdCostView}">
					<td class="text-right"><g:formatNumber number="${projectTotalInstance[3] }" format="#,##0.00" /></td>
					<td class="text-right"><g:formatNumber number="${projectTotalInstance[4]}" format="#,##0.00" /></td>
					<td class="text-right"><g:formatNumber number="${projectTotalInstance[5]}" format="#,##0.00" /></td>
				</g:if>
				<g:if test="${isActualRateView}">
					<g:if test="${params?.printActualCost == 'true' }">	
						 <td class="text-right"><g:formatNumber number="${projectTotalInstance[6]}" format="#,##0.00" /></td>
					</g:if>
				</g:if>
			</g:if>
		</tr>
		<!-- Project total After For Loop End-->
		
		<!-- COE total After For Loop Start-->
		<g:set value="${coeTotalMap.get(old_coe)}" var="coeInstance"></g:set>
		<tr style="background-color: #b5b5b5;">										
			<td class="report-green-txt"><strong><g:message code="etech.report.coe.total.label" default="COE Total:"/></strong></td>
			<td>${coeInstance[0]}</td>						
			<td class="text-right">${MiscUtils.removePrecision(coeInstance[1])}</td>
			<g:if test="${isHoursOnly == false}">
				<g:if test="${isStdCostView}">
					<td class="text-right"><g:formatNumber number="${coeInstance[2]}" format="#,##0.00" /></td>
					<td class="text-right"><g:formatNumber number="${coeInstance[3]}" format="#,##0.00" /></td>
					<td class="text-right"><g:formatNumber number="${coeInstance[4]}" format="#,##0.00" /></td>
				</g:if>										
				<g:if test="${isActualRateView}">
					<g:if test="${params?.printActualCost == 'true' }">																
						<td class="text-right"><g:formatNumber number="${coeInstance[5]}" format="#,##0.00" /></td>
					</g:if>									
				</g:if>
			</g:if>										
		</tr>
		<!-- COE total After For Loop End-->		
	
		<!-- Report Total End -->		
		<g:if test="${reportTotal}">
			<tr><td colspan="10" class="border-left-right-none" height="30"></td></tr>		
			<tr  style="background-color: #e0e0e0;">								
				<td><strong><g:message code="etech.report.report.grand.total.label" default="Report Grand Total"/>:</strong></td>
				<td></td>
				<td class="text-right">${MiscUtils.removePrecision(reportTotal[0][0]?:0.00)}</td>
				<g:if test="${session['logedInUser']?.hoursOnly == false}">
					<g:if test="${isStdCostView}">	
						<td class="text-right"><g:formatNumber number="${reportTotal[0][1]?:0.00}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${reportTotal[0][2]?:0.00}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${reportTotal[0][3]?:0.00}" format="#,##0.00" /></td>
					</g:if>
					<g:if test="${isActualRateView}">
						<g:if test="${params?.printActualCost == 'true' }">	
							<td class="text-right"><g:formatNumber number="${reportTotal[0][4]?:0.00}" format="#,##0.00" /></td>
						</g:if>
					</g:if>
				</g:if>										
			</tr>
		</g:if>					
		<!-- Report Total End --> 	
</g:if>	
<g:else>
	<tr>
		<td colspan="10" style="color: red;" ><g:message code="default.record.not.found" /></td>
	</tr>
</g:else>			 		