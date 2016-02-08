<%@page import="org.solcorp.etech.ImportProjectStatusType"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="main" />
<title>Job Project Detail</title>
<content tag="pageName">customQuartzPage</content>
<script>
function clearField(){
	$('#hhProjectId').val('');
	$('#status').val('');
	$('#hhCustId').val('');
	$('#name1').val('');
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
					<label for="hhProjectId" class="control-label"><g:message
							code="etech.customQuartz.project.detail.hhProjectId" default="Project Id" /></label>
					<g:textField name="hhProjectId" class="form-control" />
				</div>
			</div>
			
			<div class="col-sm-4 col-md-3">
				<div class="form-group">
					<label for="name1" class="control-label"><g:message
							code="etech.customQuartz.project.detail.name1" default="Project Name" /></label>
					<g:textField name="name1" class="form-control" />
				</div>
			</div>
			
			<div class="col-sm-4 col-md-3">
				<div class="form-group">
					<label for="hhCustId" class="control-label"><g:message
							code="etech.customQuartz.project.detail.hhCustId" default="Customer Id" /></label>
					<g:textField name="hhCustId" class="form-control" />
				</div>
			</div>

			<div class="col-sm-4 col-md-3">
				<div class="form-group">
					<label for="status" class="control-label"><g:message
							code="Status" default="Status" /></label>
					<g:select class="form-control" name="status" from="${ImportProjectStatusType.importProjectStatusTypeList()}" keys="${ImportProjectStatusType?.values()*.name()}" value="" noSelection="['': '--Select--']"/>
					
				</div>
			</div>
			
			<div class="col-sm-4 col-md-3" >
				<div class="form-group">

					<input type="button"
						onclick="${remoteFunction(action: 'getJobProjectList', controller: 'customQuartz', update: 'jobSummaryListGridDiv', 
								params: '\'hhProjectId=\'+document.getElementById(\'hhProjectId\').value+\'&hhCustId=\'+document.getElementById(\'hhCustId\').value+\'&status=\'+document.getElementById(\'status\').value+\'&name1=\'+document.getElementById(\'name1\').value+\'&id=\'+document.getElementById(\'jobRegisterId\').value+\'&detailType=\'+document.getElementById(\'detailType\').value+\'\'' )}"
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
					<g:render template="jobProjectDetailList" />
				</div>
				
			</div>
			
		</div>
		
	</div>

</body>
</html>