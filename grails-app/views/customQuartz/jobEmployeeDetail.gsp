<%@page import="org.solcorp.etech.ImportEmployeeStatusType"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="main" />
<title>Job Employee Detail</title>
<content tag="pageName">customQuartzPage</content>
<script>
function clearField(){
	$('#hhEmployeeId').val('');
	$('#status').val('');
	$('#firstName').val('');
	$('#lastName').val('');
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
					<label for="hhEmployeeId" class="control-label"><g:message
							code="etech.customQuartz.employee.detail.hhEmployeeId" default="Employee Id" /></label>
					<g:textField name="hhEmployeeId" class="form-control" />
				</div>
			</div>
			
			<div class="col-sm-4 col-md-3">
				<div class="form-group">
					<label for="firstName" class="control-label"><g:message
							code="etech.customQuartz.employee.detail.firstName" default="First Name" /></label>
					<g:textField name="firstName" class="form-control" />
				</div>
			</div>
			
			<div class="col-sm-4 col-md-3">
				<div class="form-group">
					<label for="lastName" class="control-label"><g:message
							code="etech.customQuartz.employee.detail.lastName" default="Last Name" /></label>
					<g:textField name="lastName" class="form-control" />
				</div>
			</div>

			<div class="col-sm-4 col-md-3">
				<div class="form-group">
					<label for="status" class="control-label"><g:message
							code="Status" default="Status" /></label>
					<g:select class="form-control" name="status" from="${ImportEmployeeStatusType.importEmployeeStatusTypeList()}" keys="${ImportEmployeeStatusType?.values()*.name()}" value="" noSelection="['': '--Select--']"/>
					
				</div>
			</div>
			
			<div class="col-sm-4 col-md-3" >
				<div class="form-group">

					<input type="button"
						onclick="${remoteFunction(action: 'getJobEmployeeList', controller: 'customQuartz', update: 'jobSummaryListGridDiv', 
								params: '\'hhEmployeeId=\'+document.getElementById(\'hhEmployeeId\').value+\'&firstName=\'+document.getElementById(\'firstName\').value+\'&status=\'+document.getElementById(\'status\').value+\'&lastName=\'+document.getElementById(\'lastName\').value+\'&id=\'+document.getElementById(\'jobRegisterId\').value+\'&detailType=\'+document.getElementById(\'detailType\').value+\'\'' )}"
						value="${message(code: 'default.button.search.label', default: 'Search')}"
						id="search" class="btn btn-success search-btn" /> <input
						type="button" class="btn btn-success search-btn"
						onclick="clearField()" value="Reset" />
				</div>
			</div>
			
			<g:hiddenField name="jobRegisterId" value="${params?.id}"/>
			
			<g:hiddenField name="detailType" value="${params?.detailType}" id="detailType"/>

			<div id="list-projectLabor" class="col-sm-12" role="main">
			
				<div id="jobSummaryListGridDiv">
					<g:render template="jobEmployeeDetailList" />
				</div>
				
			</div>
			
		</div>
		
	</div>

</body>
</html>