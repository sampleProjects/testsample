<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">		
		<title><g:message code="etech.report.project.expenses.by.expense.code.title.label" default="Project expenses by expense code"/></title>
		<r:require module="report"/>
		<content tag="pageName">projectExpensesByExpenseCode</content>
	</head>
	<body>		
		<div class="navigation">			
			<h3><g:message code="etech.report.project.expenses.by.expense.code.title.label" default="Project expenses by expense code Report Selection" /></h3>			
		</div>		
		
     			<div class="white-bg">		 	
					
					<g:form method="post" name="reportForm"  controller="report" action="getProjectExpensesByExpenseCodeReportData" >					
						<fieldset class="form">
							<g:render template="expensesSearchPage"></g:render>
						</fieldset>
						<div class="clearfix"></div>
						<div class="col-sm-12 text-center btn-space">						
							<g:submitButton  name="create" class="btn btn-success" value="${message(code: 'etech.report.generate.report.label', default: 'Generate Report')}"/>						
							<g:link class="btn btn-success"  action="projectExpensesByExpenseCodeClear"><g:message code="default.clear.label" default="Clear" /></g:link>
												 
						</div>
					</g:form>
					<g:render template="../common/searchDialog/projectSearchDialog"></g:render>
					<g:render template="../common/searchDialog/customerSearchDialog"></g:render>					
					<g:render template="../common/searchDialog/expenseSearchDialog"></g:render>
					<g:render template="../common/searchDialog/acctExecutiveSearchDialog"></g:render>
					<g:render template="../common/searchDialog/projectManagerSearchDialog"></g:render>
				</div>		
	</body>
</html>