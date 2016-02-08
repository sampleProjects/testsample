<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
<%@ page import="org.solcorp.etech.PermissionType" %>
<%@page import="org.solcorp.etech.utils.MiscUtils"%>
<%@ page import="grails.util.Environment" %>
<!DOCTYPE html>
<html>
	<head>	
		<meta name="layout" content="main">				
		<title><g:message code="etech.report.emp.by.act.by.prj.title.label" default="Employee by Activity/Project Report"/></title>
		<content tag="pageName">employeeByActivityByProjectPage</content>
	</head>
	<body>			
		<div class="navigation"> <h3> <g:message code="etech.report.emp.by.act.by.prj.title.label" default="Employee by Activity/Project Report"/> </h3> </div>			
     	<div class="white-bg">     	
			<div class="title ">			
       		 	<div class="marginleftright15">       		 		       		
	       			<span class="pull-right">&nbsp;${currentDate}</span> &nbsp;&nbsp;<label class="control-label pull-right"><g:message code="etech.report.run.date.label" default="Run Date"/>:</label>				
				 	<label class="control-label pull-left"><g:message code="etech.report.from.label" default="From"/>:</label> <span class="pull-left">&nbsp;${params?.fromDate}</span>				
				 	<label  class="control-label pull-left marginleft5"><g:message code="etech.report.to.label" default="To"/>:</label> <span class="pull-left">&nbsp;${params?.toDate}</span>&nbsp;&nbsp;				
				 </div>				  
       		 </div>				
			<div class="col-xs-12">							 
				<div class="table-responsive">	
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
					<g:set value="" var="projectInstance"></g:set>
					<g:set value="" var="oldProjectInstance"></g:set>

					<table class="table without-border">	
						<g:if test="${projectActualLaborDtlInstanceList?.size() > 0}">
							<g:if test="${params?.includeDetail == 'true' }">	
								<g:render template="employeeByActivityByProject/ajaxActivityByProjectDetailList"></g:render>
							</g:if>	
							<g:else>
								<g:render template="employeeByActivityByProject/ajaxActivityByProjectList"></g:render>
							</g:else>
							<!-- Report Total End -->		
							<g:if test="${reportTotal}">
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
						</g:if>
						<g:else>
							<tr><td colspan="10" style="color: red;" ><g:message code="default.record.not.found" /></td></tr>
						</g:else>						
					</table>
					<div class="pagination margintopbottom15">
						<g:paginate total="${projectActivityTotalCount ?: 0}"  params="${params}"/>
					</div>	
				</div>	
						
				<!-- Export Report Start -->				
				<g:if test="${projectActualLaborDtlInstanceList?.size() > 0}">
								<g:form controller="report"  action = "exportReport" method="post">
									<g:each var="reportParamsVar" in = "${params}">
										<g:if test="${reportParamsVar.key != 'action' && reportParamsVar.key != 'action'  && reportParamsVar.key !='controller'  && reportParamsVar.key !='action'}">
											<g:hiddenField  name="${reportParamsVar.key}" value="${reportParamsVar.value}"/>
										</g:if>
									</g:each>
									<g:hiddenField name="reportName" value="${org.solcorp.etech.Constants.ACTIVITY_BY_PROJECT_REPORT_NAME}"/>							
								   <a  class="btn btn-default btn-sm" onclick="exportExcel(this)"><i class="fa fa-file-excel-o" ></i> Export Excel</a>
								</g:form>
								<br/>						
						</g:if> 
				<!-- Export Report Start -->					
				<g:render template="/common/returnLink" model="['returnAction': 'employeeByActivityByProjectReport', 'returnController': 'report', 'messageCode':'etech.report.return.filter', 'defaultMessage': 'Return to Report Filter']"></g:render>
			</div>			
			<script type="text/javascript">	
			function exportExcel(link){
		        link.parentNode.submit();
		        return false;
			}
		</script>
		</div>		
	</body>
</html>