<%@page import="org.solcorp.etech.ImportExpenseStatusType"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="main" />
<title>Job Expenses Detail</title>
<content tag="pageName">customQuartzPage</content>
<script>
function clearField(){
	$('#hhProjectId').val('');
	$('#status').val('');
	$('#expenseId').val('');
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
							code="etech.customQuartz.expenses.detail.hhProjectId" default="Project Id" /></label>
					<g:textField name="hhProjectId" class="form-control" />
				</div>
			</div>
			
			<div class="col-sm-4 col-md-3">
				<div class="form-group">
					<label for="expenseId" class="control-label"><g:message
							code="etech.customQuartz.expenses.detail.expenseId" default="Expense Id" /></label>
					<g:textField name="expenseId" class="form-control onlyInteger" />
				</div>
			</div>

			<div class="col-sm-4 col-md-3">
				<div class="form-group">
					<label for="status" class="control-label"><g:message
							code="Status" default="Status" /></label>
					<g:select class="form-control" name="status" from="${ImportExpenseStatusType.importExpenseStatusTypeList()}" keys="${ImportExpenseStatusType?.values()*.name()}" value="" noSelection="['': '--Select--']"/>
					
				</div>
			</div>
			
			<div class="col-sm-4 col-md-3" style="top:25px;">
				<div class="form-group">

					<input type="button"
						onclick="${remoteFunction(action: 'getJobExpensesList', controller: 'customQuartz', update: 'jobSummaryListGridDiv', 
								params: '\'hhProjectId=\'+document.getElementById(\'hhProjectId\').value+\'&expenseId=\'+document.getElementById(\'expenseId\').value+\'&status=\'+document.getElementById(\'status\').value+\'&id=\'+document.getElementById(\'jobRegisterId\').value+\'&detailType=\'+document.getElementById(\'detailType\').value+\'\'' )}"
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
					<g:render template="jobExpensesDetailList" />
				</div>
				
			</div>
			
		</div>
		
	</div>

</body>
</html>