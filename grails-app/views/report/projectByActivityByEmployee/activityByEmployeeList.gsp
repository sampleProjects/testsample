<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
<%@ page import="org.solcorp.etech.PermissionType" %>
<%@ page import="grails.util.Environment" %>
<!DOCTYPE html>
<html>
	<head>	
		<meta name="layout" content="main">				
		<title><g:message code="etech.report.project.by.activity.emp.title.label" default="Project by Activity/Employee Report"/></title>
		<content tag="pageName">activityByEmployeePage</content>
	</head>	
	<body>	
		<div class="navigation"> <h3> <g:message code="etech.report.project.by.activity.emp.title.label" default="Project by Activity/Employee Report" /> </h3> </div>				
     	<div class="white-bg">     	
			<div class="title ">			
       		 	<div class="marginleftright15">       		 		       		
	       			<span class="pull-right">&nbsp;${currentDate}</span> &nbsp;&nbsp;<label class="control-label pull-right"><g:message code="etech.report.run.date.label" default="Run Date"/>:</label>				
				 	<label class="control-label pull-left"><g:message code="etech.report.from.label" default="From"/>:</label> <span class="pull-left">&nbsp;${params?.fromDate}</span>				
				 	<label  class="control-label pull-left marginleft5"><g:message code="etech.report.to.label" default="To"/>:</label> <span class="pull-left">&nbsp;${params?.toDate}</span>&nbsp;&nbsp;				
				 </div>				  
       		 </div>				
			<div class="col-xs-12">							 
				<div class="table-responsive" id="listRecordsDiv">					
					<g:render template="projectByActivityByEmployee/ajaxActivityByEmployeeList"></g:render>					
				</div>
					<g:if test="${projectActualLaborDtlInstanceList?.size() > 0}">		
							<g:form controller="report"  action = "exportReport" method="post">
								<g:each var="reportParamsVar" in = "${params}">
									<g:if test="${reportParamsVar.key != 'action' && reportParamsVar.key != 'action'  && reportParamsVar.key !='controller'  && reportParamsVar.key !='action'}">
										<g:hiddenField  name="${reportParamsVar.key}" value="${reportParamsVar.value}"/>
									</g:if>
								</g:each>
								<g:hiddenField name="reportName" value="${org.solcorp.etech.Constants.ACTIVITY_BY_EMPLOYEE_REPORT_NAME}"/>
								<a  class="btn btn-default btn-sm" onclick="exportExcel(this)"><i class="fa fa-file-excel-o" ></i> Export Excel</a>
							</g:form>							 
							<br/>
					</g:if>							
					<g:render template="/common/returnLink" model="['returnAction': 'activityByEmployeeReport', 'returnController': 'report', 'messageCode':'etech.report.return.filter', 'defaultMessage': 'Return to Report Filter']"></g:render>
				</div>
			</div>	
			
			<script type="text/javascript">

			function exportExcel(link){
	
				//link.parentNode._format.value = link.title;
		        link.parentNode.submit();
		        return false;
			}
		</script>		
		</body>
</html>