<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
<%@page import="org.solcorp.etech.utils.MiscUtils"%>
<g:if test="${projectActualLaborDtlInstanceList?.size() > 0}">		
	<g:each in="${projectActualLaborDtlInstanceList}" status="i" var="actualLaborInstance">
	
		<g:set value="${actualLaborInstance[0]}" var="lempno"></g:set>		
		<g:if test="${!old_Emp.equals(lempno)}" >
			<g:if test="${!old_Emp.equals("")}">
				
				<!-- Project total when Project is null Start-->
				<g:if test="${oldProjectInstance}">
				<tr style="background-color: #f8f8f8;">					
					<td></td>
					<td>${oldProjectInstance[8]}</td>
					<td class="text-right">${MiscUtils.removePrecision(oldProjectInstance[3])}</td>
					<g:if test="${isHoursOnly == false}">
						<g:if test="${isStdCostView}">
							<td class="text-right"><g:formatNumber number="${oldProjectInstance[4]}" format="#,##0.00" /></td>
							<td class="text-right"><g:formatNumber number="${oldProjectInstance[5]}" format="#,##0.00" /></td>
							<td class="text-right"><g:formatNumber number="${oldProjectInstance[6]}" format="#,##0.00" /></td>
						</g:if>										
						<g:if test="${isActualRateView}">
							<g:if test="${params?.printActualCost == 'true' }">																
								<td class="text-right"><g:formatNumber number="${oldProjectInstance[7]}" format="#,##0.00" /></td>
							</g:if>									
						</g:if>
					</g:if>										
				</tr>	
				</g:if>	
				<!-- Project total when Project is null End-->
				
				<!-- Activity total when Project is null Start-->
				<g:set value="${employeeByActivityMap.get(old_Emp+'_'+old_Oper)}" var="activityInstance"></g:set>
				<tr style="background-color: #eaeaea;">
					<td>${activityInstance[1] ?: ''}</td>
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
					<span class="marginright20">${actualLaborInstance[9]?: ''}&nbsp;${actualLaborInstance[10] ?: ''}</span>	 				
				</td>												
			</tr>
			<!-- Employee Header End -->
												
    	   	<!-- Header Start-->
			<tr class="report-dark">							
				<td><g:message code="etech.report.activity.label" default="Activity"/></td>
				<td><g:message code="etech.report.employee.label" default="Project"/></td>
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
		</g:if> <!-- else  !old_Emp.equals(lempno) -->
		
		<g:set value="${actualLaborInstance[2]}" var="loper"></g:set>		
		<g:if test="${old_Oper != loper && old_Emp == lempno}">
		   
		    <!-- Project total 2 Start-->					
			<tr style="background-color: #f8f8f8;"> 
				<td></td>
				<td>${oldProjectInstance[8]}</td>
				<td class="text-right">${MiscUtils.removePrecision(oldProjectInstance[3])}</td>					
				<g:if test="${isHoursOnly == false}">
					<g:if test="${isStdCostView}">
						<td class="text-right"><g:formatNumber number="${oldProjectInstance[4]}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${oldProjectInstance[5]}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${oldProjectInstance[6]}" format="#,##0.00" /></td>
					</g:if>										
					<g:if test="${isActualRateView}">
						<g:if test="${params?.printActualCost == 'true' }">																
							<td class="text-right"><g:formatNumber number="${oldProjectInstance[7]}" format="#,##0.00" /></td>
						</g:if>									
					</g:if>
				</g:if>										
			</tr>				
			<!-- Project total 2 End-->
			
			<!-- Activity total 2 Start-->
			<g:set value="${employeeByActivityMap.get(old_Emp+'_'+old_Oper)}" var="activityInstance"></g:set>
			<tr style="background-color: #eaeaea;">
					<td>${activityInstance[1] ?: ''}</td>
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
			<!-- Activity total 2 End-->					
		</g:if> <!-- old_Oper != loper && old_Emp == lempno -->
			
		<!-- Project  old_Proj !=lproj && old_Oper == loper && old_Emp == lempno == Start-->
				
		<g:set value="${actualLaborInstance[1]}" var="lproj"></g:set>
		<g:if test="${old_Proj !=lproj && old_Oper == loper && old_Emp == lempno }" >
			<tr style="background-color: #f8f8f8;"> 
				<td></td>
				<td>${oldProjectInstance[8]}</td>
				<td class="text-right">${MiscUtils.removePrecision(oldProjectInstance[3])}</td>
				<g:if test="${isHoursOnly == false}">
					<g:if test="${isStdCostView}">
						<td class="text-right"><g:formatNumber number="${oldProjectInstance[4]}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${oldProjectInstance[5]}" format="#,##0.00" /></td>
						<td class="text-right"><g:formatNumber number="${oldProjectInstance[6]}" format="#,##0.00" /></td>
					</g:if>										
					<g:if test="${isActualRateView}">
						<g:if test="${params?.printActualCost == 'true' }">																
							<td class="text-right"><g:formatNumber number="${oldProjectInstance[7]}" format="#,##0.00" /></td>
						</g:if>									
					</g:if>
				</g:if>										
			</tr>					
		</g:if>
		<!-- Project  old_Proj !=lproj && old_Oper == loper && old_Emp == lempno == End-->
		<g:set value="${lproj }" var="old_Proj"></g:set>
		<g:set value="${loper}" var="old_Oper"></g:set>
		<g:set value="${lempno}" var="old_Emp"></g:set>		 
		<g:set value="${actualLaborInstance}" var="oldProjectInstance"></g:set>
		
	</g:each>
	
		<!-- Activity  After For loop Start-->
		<tr style="background-color: #f8f8f8;"> 
			<td></td>
			<td>${oldProjectInstance[8]}</td>
			<td class="text-right">${MiscUtils.removePrecision(oldProjectInstance[3])}</td>
			<g:if test="${isHoursOnly == false}">
				<g:if test="${isStdCostView}">
					<td class="text-right"><g:formatNumber number="${oldProjectInstance[4]}" format="#,##0.00" /></td>
					<td class="text-right"><g:formatNumber number="${oldProjectInstance[5]}" format="#,##0.00" /></td>
					<td class="text-right"><g:formatNumber number="${oldProjectInstance[6]}" format="#,##0.00" /></td>
				</g:if>										
				<g:if test="${isActualRateView}">
					<g:if test="${params?.printActualCost == 'true' }">																
						<td class="text-right"><g:formatNumber number="${oldProjectInstance[7]}" format="#,##0.00" /></td>
					</g:if>									
				</g:if>
			</g:if>										
		</tr>				
		<!-- Activity  After For loop End-->			
		
		<!-- Activity After For loop Start-->
		<g:set value="${employeeByActivityMap.get(old_Emp+'_'+old_Oper)}" var="activityInstance"></g:set>
		<tr style="background-color: #eaeaea;">
			<td>${activityInstance[1] ?: ''}</td>
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
		<!-- Activity After For loop End-->	
		
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