<%@ page import="org.solcorp.etech.RoleType"%>
<%@ page import="org.solcorp.etech.Constants"%>
<%@ page import="org.solcorp.etech.PermissionType" %>
<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>

	<div class="title text-left"><h2><g:message code="etech.report.filter.by.label" default= "Filter By" /></h2></div>										
	<div class="filter-parts clearfix">		
		
		
		<div class="col-sm-3 col-md-2">					
			<div class="form-group">							
				<label for="project" class="control-label text-underline" onclick="projectPopup()"><g:message code="etech.report.project.label" default="Project" /></label>
				<g:textField name="project" value="" maxlength="${Constants.COE_CODE_LENGTH}" class="form-control"/>
			</div>
		</div>
		
		<div class="col-sm-3 col-md-2">					
			<div class="form-group">		
				<label for="expense" class="control-label text-underline" onclick="expensePopup()"><g:message code="etech.report.expense.code.label" default="Expense" /></label>
				<g:textField name="expense" value="" maxlength="${Constants.COE_CODE_LENGTH}" class="form-control" />
			</div>
		</div>
		
		<div class="col-sm-3 col-md-2">					
			<div class="form-group">		
				<label for="customer" class="control-label text-underline" onclick="customerPopup()"><g:message code="etech.report.customer.label" default="Customer" /></label>
				<g:textField name="customer" value="" maxlength="${Constants.COE_CODE_LENGTH}" class="form-control"/>
			</div>
		</div>
		
		<div class="col-sm-3 col-md-2">					
			<div class="form-group">		
				<g:if test="${userInstance?.roles*.name.contains(RoleType.ROLE_ACCOUNT_DIRECTOR.name())}">
					<label for="acctExecutive" class="control-label"><g:message code="app.accountDirector.label" default="Account Director" /></label>
					<g:textField name="acctExecutive" value="${userInstance?.employee?.code}" maxlength="${Constants.COE_CODE_LENGTH}" class="form-control"  readOnly="true"/>
				</g:if>
				<g:else>
					<label for="acctExecutive" class="control-label text-underline" onclick="accExecutivePopup()"><g:message code="app.accountDirector.label" default="Account Director" /></label>
					<g:textField name="acctExecutive" value="" maxlength="${Constants.COE_CODE_LENGTH}" class="form-control"/>
				</g:else>
			</div>
		</div> 
		<div class="col-sm-3 col-md-2">					
				<div class="form-group">		
					<g:if test="${userInstance?.roles*.name.contains(RoleType.ROLE_PROJECT_MANAGER.name())}">
						<label for="projectManager" class="control-label"><g:message code="etech.report.project.manager.label" default="Project Manager" /></label>
						<g:textField name="projectManager" value="${userInstance?.employee?.code}" maxlength="${Constants.COE_CODE_LENGTH}" class="form-control" readOnly="true"/>
					</g:if>	
					<g:else>
						<label for="projectManager" class="control-label text-underline" onclick="projectManagerPopup()"><g:message code="etech.report.project.manager.label" default="Project Manager" /></label>
						<g:textField name="projectManager" value="" maxlength="${Constants.COE_CODE_LENGTH}" class="form-control"/>
					</g:else>
				</div>
			</div>
		<div class="col-sm-3 col-md-2">					
			<div class="form-group">		
				<label for="glaccount" class="control-label"><g:message code="etech.report.expense.vendorId.label" default="GL Account" /></label>
				<g:textField name="glaccount" value="" maxlength="${Constants.COE_CODE_LENGTH}" class="form-control" />
			</div>
		</div>
		
		<div class="col-sm-3 col-md-2">					
			<div class="form-group">		
				<label for="vendorId" class="control-label"><g:message code="etech.report.expense.vendorId.label" default="Vendor Id" /></label>
				<g:textField name="vendorId" value="" maxlength="${Constants.COE_CODE_LENGTH}" class="form-control" />
			</div>
		</div>
		<div class="col-sm-3 col-md-2">					
			<div class="form-group">		
				<label for="invoiceId" class="control-label"><g:message code="etech.report.expense.invoiceId.label" default="Invoice Id" /></label>
				<g:textField name="invoiceId" value="" maxlength="${Constants.COE_CODE_LENGTH}" class="form-control" />
			</div>
		</div>
		
		
	</div>
		
	<div class="clearfix"></div>
							
	<!-- <div class="title text-left"><h2><g:message code="etech.report.date.selection.label" default="Date Selection" /></h2></div> -->
	<div class="row">
	<div class="col-sm-12 col-md-12">
	<div class="title text-left"><h2><g:message code="etech.report.print.options.label" default="Date Selection" /></h2></div>
	<div class="col-sm-4 col-md-4">					
		<div class="form-group">		
			<label for="predefined" class="control-label"><g:message code="etech.report.predefined.label" default="Predefined" /></label>							
			<g:select name="predefined" from="${org.solcorp.etech.PredefinedReport.list()}" keys="${org.solcorp.etech.PredefinedReport.list()*.name()}" onChange="return setDate(this.options[selectedIndex].value)" value="" noSelection="['': '--Select--']" class="selectpicker form-control " />		
		</div>
	</div>
	
	<g:each var="dateInstance" in="${DateFormatUtils.getPredefinedDates()}">
		<g:hiddenField name="${dateInstance.key}" value="${dateInstance.value}" />
	</g:each>
	
	<div class="col-sm-4 col-md-2">					
		<div class="form-group">		
			<label for="fromDate" class="control-label"><g:message code="etech.report.from.label" default="From Date" /></label>							
			 <g:render template="/common/dateComponent" model="['fieldName': 'fromDate', 'fieldValue': '']"></g:render>		
		</div>
	</div>
					
	<div class="col-sm-4 col-md-2">					
		<div class="form-group">		
			<label for="toDate" class="control-label"><g:message code="etech.report.to.label" default="To Date" /></label>							
			<g:render template="/common/dateComponent" model="['fieldName': 'toDate', 'fieldValue': '']"></g:render>	
		</div>
	</div>
</div>
<div class="col-sm-12 col-md-12">
	<div class="title text-left"><h2><g:message code="etech.report.print.options.label" default="Print Options" /></h2></div>
	<div class="col-sm-4 col-md-4">					
		<div class="form-group">		
			<g:checkBox name="includeDetail" value="true" checked="false"/>	<label for="includeDetail" class="control-label"><g:message code="etech.report.include.detail.label" default="Include Detail" /></label>							
				
		</div>
	</div>
	</div>	
</div>	
	<div class="clearfix"></div>