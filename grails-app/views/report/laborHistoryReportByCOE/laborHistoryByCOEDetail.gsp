<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
<%@ page import="org.solcorp.etech.PermissionType" %>
<%@ page import="grails.util.Environment" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:message code="etech.report.coe.labor.history.report.title.label" default="Labor History Report By COE" /></title>
		<content tag="pageName">laborHistoryByCOEPage</content>
	</head>
	<body>
		<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_ACTUAL_RATE.name()}">
			<g:set value="true" var="isActualRateView"></g:set>
		</shiro:hasPermission>
		<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_STANDARD_COST.name()}">
			<g:set value="true" var="isStdCostView"></g:set>
		</shiro:hasPermission>

		<div class="navigation"> <h3> <g:message code="etech.report.coe.labor.history.report.title.label" default="Labor History Report By COE" /> </h3> </div>
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
					<table class="table without-border">
						<g:set value="${session['logedInUser']?.hoursOnly}" var="isHoursOnly"> </g:set>
	 					<g:set value="" var="old_Oper"></g:set>
						<g:set value="" var="old_Proj"></g:set>
						<g:set value="" var="old_Emp"></g:set>
						<g:set value="" var="lproj"></g:set>
						<g:set value="" var="projectInstance"></g:set>
						<g:set value="" var="oldProjectInstance"></g:set>
						<g:set value="" var="old_coe"></g:set>
					
						<g:render template="laborHistoryReportByCOE/laborHistoryReportListDetail"></g:render>
						
					</table>
						<div class="pagination margintopbottom15">
						<g:paginate total="${projectActivityTotalCount ?: 0}"  params="${params}"/>
					</div>	
					</div>					
					<g:if test="${projectActualLaborDtlInstanceList?.size() > 0}">
						<g:form controller="report"  action = "exportReport" method="post">
							<g:each var="reportParamsVar" in = "${params}">
								<g:if test="${reportParamsVar.key != 'action' && reportParamsVar.key != 'action'  && reportParamsVar.key !='controller'  && reportParamsVar.key !='action'}">
									<g:hiddenField  name="${reportParamsVar.key}" value="${reportParamsVar.value}"/>
								</g:if>
							</g:each>
							<g:hiddenField name="reportName" value="${org.solcorp.etech.Constants.LABOR_ACTIVITY_BY_COE_REPORT_NAME}"/>
							<a  class="btn btn-default btn-sm" onclick="exportExcel(this)"><i class="fa fa-file-excel-o" ></i> Export Excel</a>
						</g:form>
						<br/>
					</g:if>								
					<g:render template="/common/returnLink" model="['returnAction': 'laborHistoryByCOEReport', 'returnController': 'report', 'messageCode':'etech.report.return.filter', 'defaultMessage': 'Return to Report Filter']"></g:render>
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