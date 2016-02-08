<%@ page import="org.solcorp.etech.RoleType"%>
<%@ page import="org.solcorp.etech.Constants"%>
<%@ page import="org.solcorp.etech.PermissionType" %>
<%@ page import="org.solcorp.etech.ProjectStatusType" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">		
		<title><g:message code="etech.report.project.performance.view.report.selection.screen.title.label" default="Project Performance View Report Selection Screen" /></title>
		<r:require module="report"/>
		<content tag="pageName">projectPerformanceReportPage</content>
	</head>
	<body>		
		
		<div class="navigation">			
			<h3><g:message code="etech.report.project.performance.view.report.selection.screen.title.label" default="Project Performance View Report Selection Screen" /></h3>			
		</div>				
     	<div class="white-bg">		 	
		<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_ACTUAL_RATE.name()}">
			<g:set value="true" var="isActualRateView"></g:set>
		</shiro:hasPermission>
		<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_STANDARD_COST.name()}">
			<g:set value="true" var="isStdCostView"></g:set>
		</shiro:hasPermission>
		<g:form method="post" name="reportForm"  controller="report" action="getAllProjectList" >					
			<fieldset class="form">
				<div class="title text-left"><h2><g:message code="etech.report.filter.by.label" default= "Filter By" /></h2></div>										
					
				<div class="col-sm-3 col-md-2">					
					<div class="form-group">							
						<label for="project" class="control-label text-underline" onclick="projectPopup()"><g:message code="etech.report.project.label" default="Project" /></label>
						<g:textField name="project" value="" maxlength="${Constants.DEFAULT_CODE_LENGTH}" class="form-control"/>
					</div>
				</div>
							
				<div class="col-sm-3 col-md-2">					
					<div class="form-group">		
						<label for="customer" class="control-label text-underline" onclick="customerPopup()"><g:message code="etech.report.customer.label" default="Customer" /></label>
						<g:textField name="customer" value="" maxlength="${Constants.DEFAULT_CODE_LENGTH}" class="form-control"/>
					</div>
				</div>
											
				<div class="col-sm-3 col-md-2">					
					<div class="form-group">	
						<g:if test="${userInstance?.roles*.name.contains(RoleType.ROLE_ACCOUNT_DIRECTOR.name())}">	
							<label for="acctExecutive" class="control-label text-underline" onclick="accExecutivePopup()"><g:message code="app.accountDirector.label" default="Account Director" /></label>
							<g:textField name="acctExecutive" value="${userInstance?.employee?.code}" maxlength="${Constants.DEFAULT_CODE_LENGTH}" class="form-control" readOnly="true"/>
						</g:if>
						<g:else>
							<label for="acctExecutive" class="control-label text-underline" onclick="accExecutivePopup()"><g:message code="app.accountDirector.label" default="Account Director" /></label>
							<g:textField name="acctExecutive" value="" maxlength="${Constants.DEFAULT_CODE_LENGTH}" class="form-control"/>
						</g:else>
					</div>
				</div>
									
				<div class="col-sm-3 col-md-2">					
					<div class="form-group">		
						<g:if test="${userInstance?.roles*.name.contains(RoleType.ROLE_PROJECT_MANAGER.name())}">
							<label for="projectManager" class="control-label text-underline" onclick="projectManagerPopup()"><g:message code="etech.report.project.manager.label" default="Project Manager" /></label>
							<g:textField name="projectManager" value="${userInstance?.employee?.code}" maxlength="${Constants.DEFAULT_CODE_LENGTH}" class="form-control" readOnly="true"/>
						</g:if>
						<g:else>
							<label for="projectManager" class="control-label text-underline" onclick="projectManagerPopup()"><g:message code="etech.report.project.manager.label" default="Project Manager" /></label>
							<g:textField name="projectManager" value="" maxlength="${Constants.DEFAULT_CODE_LENGTH}" class="form-control"/>
						</g:else>
					</div>
				</div>
												
				<div class="col-sm-3 col-md-2">					
					<div class="form-group">		
						<label for="projectCategory" class="control-label"><g:message code="etech.report.project.category.label" default="Project Category" /></label>
						<g:select id="projectCategory" name="projectCategory" from="${org.solcorp.etech.ProjectCategory.list()}" optionKey="category" optionValue="category" value="" class="selectpicker form-control many-to-one" noSelection="['': '--Select--']" />
					</div>
				</div>
											
				<div class="col-sm-3 col-md-2">					
					<div class="form-group">		
						<label for="projectType" class="control-label"><g:message code="etech.report.project.type.label" default="Project Type" /></label>							
						<g:select name="projectType" from="${org.solcorp.etech.ProjectType?.values()}" keys="${org.solcorp.etech.ProjectType.values()*.name()}" value="" noSelection="['': '--Select--']" class="selectpicker form-control" />
					</div>
				</div>
							
				<div class="col-sm-3 col-md-2">					
					<div class="form-group">		
						<label for="billCodeType" class="control-label"><g:message code="etech.report.bill.code.label" default="Bill Code" /></label>							
						<g:select name="billCodeType" from="${org.solcorp.etech.BillCodeType.ProjectBillCodeTypeList()}" keys="${org.solcorp.etech.BillCodeType.ProjectBillCodeTypeList()*.name()}" value="" noSelection="['': '--Select--']" class="selectpicker form-control " />		
					</div>
				</div>
				
				<div class="col-sm-3 col-md-2">
  					<div class="form-group">
    					<label for="status" class="control-label"><g:message code="project.status.label" default="Status" /></label>
    					<g:select name="status" from="${ProjectStatusType.projectStatusTypeList()}" keys="${ProjectStatusType.values()*.name()}" value="" class="selectpicker form-control" noSelection="['': '--Select--']"/>
  					</div>
				</div>
				<g:if test="${session['logedInUser']?.hoursOnly == false}">
					<div class="col-sm-3 col-md-2">					
						<div class="form-group">		
							<label for="costOption" class="control-label"><g:message code="etech.report.cost.option.label" default="Cost Option" /></label>	
							
							<g:if test="${isActualRateView && isStdCostView}">
								<g:select name="costOption" from="['Standard':'Standard','Actual':'Actual']" optionKey="key" optionValue="value" value="" class="selectpicker form-control " />
							</g:if>
							<g:elseif test="${isStdCostView}">
								<g:select name="costOption" from="['Standard':'Standard']" optionKey="key" optionValue="value" value="" class="selectpicker form-control " />
							</g:elseif>
							<g:elseif test="${isActualRateView}">
								<g:select name="costOption" from="['Actual':'Actual']" optionKey="key" optionValue="value" value="" class="selectpicker form-control " />
							</g:elseif>
									
						</div>
					</div>
				</g:if>			
				</fieldset>
				
				<div class="clearfix"></div>
				
				<div class="col-sm-12 text-center btn-space">						
					<g:submitButton  name="create" class="btn btn-success" value="${message(code: 'etech.report.generate.report.label', default: 'Generate Report')}"/>						
					<g:link class="btn btn-success"  action="projectPerClear"><g:message code="default.clear.label" default="Clear" /></g:link>
				</div>
		</g:form>
		
		<g:render template="../common/searchDialog/projectSearchDialog"></g:render>
		<g:render template="../common/searchDialog/customerSearchDialog"></g:render>
		<g:render template="../common/searchDialog/acctExecutiveSearchDialog"></g:render>
		<g:render template="../common/searchDialog/projectManagerSearchDialog"></g:render>
	</div>		
	</body>
</html>
