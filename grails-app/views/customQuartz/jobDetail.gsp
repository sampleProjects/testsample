<%@page import="org.solcorp.etech.ImportLaborTransactionStatusType"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="main" />
<title>Job Summary Detail</title>
<content tag="pageName">customQuartzPage</content>
<script>
function clearField(){
	$('#sourceTransactionId').val('');
	$('#status').val('');
	$('#employeeId').val('');
	$('#projectId').val('');
	$('#projectFullName').val('');
	$('#projectCustRevId').val('');
}
</script>
</head>
<body>
	<div class="navigation">
		<h3>Job Detail: ${jobRegisterInstance?.name ?: jobName}</h3>
	</div>

	<div id="list-customQUartz" class="col-sm-12" role="main">

		<div class="white-bg">
			<div style="margin-top: 20px;"></div>
			<div class="col-sm-4 col-md-3">
				<div class="form-group">
					<label for="sourceTransactionId" class="control-label"><g:message
							code="Source Transaction Id" default="Source Transaction Id" /></label>
					<g:textField name="sourceTransactionId" class="form-control" />
				</div>
			</div>

			<div class="col-sm-4 col-md-3">
				<div class="form-group">
					<label for="status" class="control-label"><g:message
							code="Status" default="Status" /></label>
					<g:select class="form-control" name="status" from="${ImportLaborTransactionStatusType.importLaborTransactionStatusTypeList()}" keys="${ImportLaborTransactionStatusType?.values()*.name()}" value="" noSelection="['': '--Select--']"/>
					
				</div>
			</div>

			<div class="col-sm-4 col-md-3">
				<div class="form-group">
					<label for="employeeId" class="control-label"><g:message
							code="EmployeeId" default="Employee Id" /></label>
					<g:textField name="employeeId" class="form-control" />
				</div>
			</div>
			
			<div class="col-sm-4 col-md-3">
				<div class="form-group">
					<label for="projectId" class="control-label"><g:message
							code="ProjectId" default="Project Id" /></label>
					<g:textField name="projectId" class="form-control" />
				</div>
			</div>
			
			<div class="col-sm-4 col-md-3">
				<div class="form-group">
					<label for="projectFullName" class="control-label"><g:message
							code="ProjectName" default="Project Name" /></label>
					<g:textField name="projectFullName" class="form-control" />
				</div>
			</div>
			
			<div class="col-sm-4 col-md-3">
				<div class="form-group">
					<label for="projectCustRevId" class="control-label"><g:message
							code="CustomerId" default="Customer Id" /></label>
					<g:textField name="projectCustRevId" class="form-control" />
				</div>
			</div>
			
			<div class="col-sm-4 col-md-4 margintop25">
				<div class="form-group">

					<input type="button"
						onclick="${remoteFunction(action: 'getJobSummaryList', controller: 'customQuartz', update: 'jobSummaryListGridDiv', 
								params: '\'sourceTransactionId=\'+document.getElementById(\'sourceTransactionId\').value+\'&status=\'+document.getElementById(\'status\').value+\'&employeeId=\'+document.getElementById(\'employeeId\').value+\'&projectId=\'+document.getElementById(\'projectId\').value+\'&projectFullName=\'+document.getElementById(\'projectFullName\').value+\'&projectCustRevId=\'+document.getElementById(\'projectCustRevId\').value+\'&id=\'+document.getElementById(\'jobRegisterId\').value+\'&detailType=\'+document.getElementById(\'detailType\').value+\'\'' )}"
						value="${message(code: 'default.button.search.label', default: 'Search')}"
						id="search" class="btn btn-success search-btn" /> <input
						type="button" class="btn btn-success search-btn"
						onclick="clearField()" value="Reset" />
				</div>
			</div>

			<g:hiddenField name="jobRegisterId" value="${params?.id}"/>

			<div id="list-projectLabor" class="col-sm-12" role="main">
			
				<div id="jobSummaryListGridDiv">
					<g:render template="jobDetailList" />
				</div>
				
			</div>
			
		</div>
		
	</div>

</body>
</html>