<%@page import="org.solcorp.etech.utils.MiscUtils"%>
<g:if test="${projectActualLaborDtlInstanceList?.size() > 0}">		
	<g:each in="${projectActualLaborDtlInstanceList}" status="i" var="actualLaborInstance">
	
		<g:set value="${actualLaborInstance[0] }" var="lempno"></g:set>		
		<g:if test="${!old_Emp.equals(lempno)}" >
			<g:if test="${!old_Emp.equals("")}">
				
			<!-- Operation total when Project is null Start-->
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
			<!-- Operation total when Project is null End-->
				
			<!-- Project total when Project is null Start-->
			<g:set value="${employeeByProjectActivityMap.get(old_Emp+'_'+old_Proj)}" var="projectInstance"></g:set>
			<tr style="background-color: #eaeaea;">
				<td>${projectInstance[7] ?: ''}</td>
				<td></td>
				<td class="text-right">${MiscUtils.removePrecision(projectInstance[2])}</td>
				<g:if test="${isHoursOnly == false}">
					<g:if test="${isStdCostView}">
						<td class="text-right"><g:formatNumber number="${projectInstance[3]}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${projectInstance[4]}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${projectInstance[5]}" format="#,##0.00" /></td>											
					</g:if>
					<g:if test="${isActualRateView}">
						<g:if test="${params?.printActualCost == 'true' }">
							 <td class="text-right"><g:formatNumber number="${projectInstance[6]}" format="#,##0.00" /></td>
						</g:if>										
					</g:if>
				</g:if>
			</tr>	 
			<!-- Project total when Project is null End-->  			
				
			<!-- Employee total when Project is null Start-->
			<g:set value="${employeeTotalMap.get(old_Emp)}" var="employeeInstance"></g:set> 
			<tr style="background-color: #cdcdcd;">
				<td class="report-green-txt"><strong><g:message code="etech.report.employee.label" default="Employee"/>:</strong></td>	
				<td>${employeeInstance[0] ?: ''}</td>
				<td class="text-right">${MiscUtils.removePrecision(employeeInstance[1])}</td>
				<g:if test="${isHoursOnly == false}">
					<g:if test="${isStdCostView}">
						<td class="text-right"><g:formatNumber number="${employeeInstance[2]}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${employeeInstance[3]}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${employeeInstance[4]}" format="#,##0.00" /></td>											
					</g:if>
					<g:if test="${isActualRateView}">
						<g:if test="${params?.printActualCost == 'true' }">
							 <td class="text-right"><g:formatNumber number="${employeeInstance[5]}" format="#,##0.00" /></td>
						</g:if>										
					</g:if>
				</g:if>
			</tr>	 
			<!-- Employee total when Project is null End-->
				
			<tr><td colspan="10" class="border-left-right-none" height="30"></td></tr>											
			</g:if> <!-- !old_Emp.equals("") -->
					
			<!-- Employee Header Start -->
			<tr class="report-tr">										
				<td colspan="10">											
					<span class="marginright20"><strong><g:message code="etech.report.emp.label" default="Employee"/>:</strong>
					${actualLaborInstance[0]}</span>
					<span class="marginright20">${actualLaborInstance[8]?: ''}&nbsp;${actualLaborInstance[9] ?: ''}</span>	 				
				</td>												
			</tr>
			<!-- Employee Header End -->
													
	       	<!-- Header Start-->
			<tr class="report-dark">							
				<td><g:message code="etech.report.employee.label" default="Project"/></td>
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
		
		<g:set value="${actualLaborInstance[2]}" var="lproj"></g:set>
			
		<g:if test="${old_Proj != lproj && old_Emp == lempno}">
			   
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
				
			<!-- Project total 2 Start-->
			<g:set value="${employeeByProjectActivityMap.get(old_Emp+'_'+old_Proj)}" var="projectInstance"></g:set>
			<tr style="background-color: #eaeaea;">
				<td>${projectInstance[7] ?: ''}</td>
				<td></td>
				<td class="text-right">${MiscUtils.removePrecision(projectInstance[2])}</td>
				<g:if test="${isHoursOnly == false}">
					<g:if test="${isStdCostView}">
						<td class="text-right"><g:formatNumber number="${projectInstance[3]}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${projectInstance[4]}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${projectInstance[5]}" format="#,##0.00" /></td>											
					</g:if>
					<g:if test="${isActualRateView}">
						<g:if test="${params?.printActualCost == 'true' }">
					 		<td class="text-right"><g:formatNumber number="${projectInstance[6]}" format="#,##0.00" /></td>
						</g:if>										
					</g:if>
				</g:if>
			</tr>	 
			<!-- Project total 2 End-->					
		</g:if> <!-- old_Proj != lproj && old_Emp == lempno -->
				
		<!-- Activity  old_Oper != loper && old_Proj == lproj && old_Emp == lempno == Start-->
					
		<g:set value="${actualLaborInstance[1]}" var="loper"></g:set>
		<g:if test="${old_Oper != loper && old_Proj == lproj && old_Emp == lempno }" >
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
		<!-- Activity  old_Oper != loper && old_Proj == lproj && old_Emp == lempno == End-->
				
		<g:set value="${lproj }" var="old_Proj"></g:set>
		<g:set value="${loper}" var="old_Oper"></g:set>
		<g:set value="${lempno}" var="old_Emp"></g:set>		 
		<g:set value="${actualLaborInstance}" var="oldActivityInstance"></g:set>
	
	</g:each>
	
	<!-- Activity  After For loop Start-->
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
	<!-- Activity  After For loop End-->			
		
	<!-- Project After For loop Start-->
	<g:set value="${employeeByProjectActivityMap.get(old_Emp+'_'+old_Proj)}" var="projectInstance"></g:set>
	<tr style="background-color: #eaeaea;">
		<td>${projectInstance[7] ?: ''}</td>
		<td></td>
		<td class="text-right">${MiscUtils.removePrecision(projectInstance[2])}</td>
		<g:if test="${isHoursOnly == false}">
			<g:if test="${isStdCostView}">
				<td class="text-right"><g:formatNumber number="${projectInstance[3]}" format="#,##0.00" /></td>
				<td class="text-right"><g:formatNumber number="${projectInstance[4]}" format="#,##0.00" /></td>
				<td class="text-right"><g:formatNumber number="${projectInstance[5]}" format="#,##0.00" /></td>											
			</g:if>
			<g:if test="${isActualRateView}">
				<g:if test="${params?.printActualCost == 'true' }">
				 	<td class="text-right"><g:formatNumber number="${projectInstance[6]}" format="#,##0.00" /></td>
				</g:if>										
			</g:if>
		</g:if>
	</tr>	 
	<!-- Project After For loop End-->	
		
	<!-- Employee After For loop Start-->
	<g:set value="${employeeTotalMap.get(old_Emp)}" var="employeeInstance"></g:set> 
	<tr style="background-color: #cdcdcd;">
		<td class="report-green-txt"><strong><g:message code="etech.report.employee.label" default="Employee"/>:</strong></td>	
		<td>${employeeInstance[0] ?: ''}</td>
		<td class="text-right">${MiscUtils.removePrecision(employeeInstance[1])}</td>
		<g:if test="${isHoursOnly == false}">
			<g:if test="${isStdCostView}">
				<td class="text-right"><g:formatNumber number="${employeeInstance[2]}" format="#,##0.00" /></td>
				<td class="text-right"><g:formatNumber number="${employeeInstance[3]}" format="#,##0.00" /></td>
				<td class="text-right"><g:formatNumber number="${employeeInstance[4]}" format="#,##0.00" /></td>											
			</g:if>
			<g:if test="${isActualRateView}">
				<g:if test="${params?.printActualCost == 'true' }">
					 <td class="text-right"><g:formatNumber number="${employeeInstance[5]}" format="#,##0.00" /></td>
				</g:if>										
			</g:if>
		</g:if>
	</tr>	 
	<!-- Employee After For loop End-->				
</g:if>