<%@ page import="org.solcorp.etech.RoleType"%>
<%@ page import="org.solcorp.etech.Constants"%>
<%@ page import="org.solcorp.etech.PermissionType" %>
<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>

	<div class="title text-left"><h2><g:message code="etech.report.filter.by.label" default= "Filter By" /></h2></div>										
	<div class="filter-parts clearfix">		
		
		<div class="col-sm-3 col-md-2">					
			<div class="form-group">		
				<label for="coeCode" class="control-label text-underline" onclick="coePopup()"><g:message code="etech.report.coe.label" default="COE" /></label>						
				<g:textField name="coeCode" value="" maxlength="${Constants.DEFAULT_CODE_LENGTH}" class="form-control"/>
			</div>
		</div>	 
		
		<div class="col-sm-3 col-md-2">					
			<div class="form-group">							
				<label for="project" class="control-label text-underline" onclick="projectPopup()"><g:message code="etech.report.project.label" default="Project" /></label>
				<g:textField name="project" value="" maxlength="${Constants.COE_CODE_LENGTH}" class="form-control"/>
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
				<g:if test="${session['logedInUser']?.roles*.name.contains(RoleType.ROLE_ACCOUNT_DIRECTOR.name())}">
					<label for="acctExecutive" class="control-label"><g:message code="app.accountDirector.label" default="Account Director" /></label>
					<g:textField name="acctExecutive" value="${session['logedInUser']?.employee?.code}" maxlength="${Constants.COE_CODE_LENGTH}" class="form-control"  readOnly="true"/>
				</g:if>
				<g:else>
					<label for="acctExecutive" class="control-label text-underline" onclick="accExecutivePopup()"><g:message code="app.accountDirector.label" default="Account Director" /></label>
					<g:textField name="acctExecutive" value="" maxlength="${Constants.COE_CODE_LENGTH}" class="form-control"/>
				</g:else>
			</div>
		</div>
		
			<div class="col-sm-3 col-md-2">					
				<div class="form-group">		
					<g:if test="${session['logedInUser']?.roles*.name.contains(RoleType.ROLE_PROJECT_MANAGER.name())}">
						<label for="projectManager" class="control-label"><g:message code="etech.report.project.manager.label" default="Project Manager" /></label>
						<g:textField name="projectManager" value="${session['logedInUser']?.employee?.code}" maxlength="${Constants.COE_CODE_LENGTH}" class="form-control" readOnly="true"/>
					</g:if>	
					<g:else>
						<label for="projectManager" class="control-label text-underline" onclick="projectManagerPopup()"><g:message code="etech.report.project.manager.label" default="Project Manager" /></label>
						<g:textField name="projectManager" value="" maxlength="${Constants.COE_CODE_LENGTH}" class="form-control"/>
					</g:else>
				</div>
			</div>
		<div class="col-sm-3 col-md-2">					
			<div class="form-group">		
				<label for="supervisor" class="control-label text-underline" onclick="supervisorPopup()"><g:message code="etech.report.employee.supervisor.label" default="Supervisor" /></label>
				<g:textField name="supervisor" value="" maxlength="${Constants.COE_CODE_LENGTH}" class="form-control" />
			</div>
		</div>
						
		
	</div>
	<div class="filter-parts clearfix">	
	<div class="col-sm-3 col-md-2">					
		<div class="form-group">		
			<label for="employee" class="control-label text-underline" onclick="employeePopup()"><g:message code="etech.report.employee.label" default="Employee" /></label>
			<g:textField name="employee" value="" maxlength="${Constants.COE_CODE_LENGTH}" class="form-control" />
		</div>
	</div>
	
	<div class="col-sm-3 col-md-2">					
		<div class="form-group">		
			<label for="laborActGroupCode" class="control-label text-underline" onclick="laborActGroupPopup()" ><g:message code="etech.report.labor.department.label" default="Labor Dept" /></label>
			<g:textField name="laborActGroupCode" value="" maxlength="${Constants.COE_CODE_LENGTH}" class="form-control" />
		</div>
	</div>
	
	<div class="col-sm-3 col-md-2">					
		<div class="form-group">		
			<label for="laborActCode" class="control-label text-underline" onclick="laborActCodePopup()" ><g:message code="etech.report.labor.activity.code.label" default="Labor Act Code" /></label>
			<g:textField name="laborActCode" value="" maxlength="${Constants.COE_CODE_LENGTH}" class="form-control" />
		</div>
	</div>
					
	<div class="col-sm-3 col-md-2">					
		<div class="form-group">		
			<label for="payDepartment" class="control-label text-underline" onclick="payDepartmentPopup();" ><g:message code="etech.report.pay.department.label" default="Pay Department" /></label>
			<g:textField name="payDepartment" value="" maxlength="${Constants.COE_CODE_LENGTH}" class="form-control" />
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
					
	
	</div>

	<div class="filter-parts clearfix">		
		<div class="col-sm-3 col-md-2">					
		<div class="form-group">		
			<label for="billableTime" class="control-label"><g:message code="etech.report.billable.time.label" default="Billable Time" /></label>							
			<g:select name="billableTime" class="selectpicker form-control" from="${Constants.YES_NO}" optionKey="key" optionValue="value" value="" noSelection="['': '--Select--']" />
		</div>
	</div>			
		<div class="col-sm-3 col-md-2">					
			<div class="form-group">		
				<label for="billCodeType" class="control-label"><g:message code="etech.report.project.bill.code.label" default="Project Bill Code" /></label>							
				<g:select name="billCodeType" from="${org.solcorp.etech.BillCodeType.ProjectBillCodeTypeList()}" keys="${org.solcorp.etech.BillCodeType.ProjectBillCodeTypeList()*.name()}" value="" noSelection="['': '--Select--']" class="selectpicker form-control " />		
			</div>
		</div>
		
		<div class="col-sm-3 col-md-2">					
			<div class="form-group">
				<label for="operations" class="control-label"><g:message code="laborActivityCode.operations.label" default="Operations" /></label>
				<g:select class="selectpicker form-control" name="operations" from="${Constants.YES_NO_MAP}" optionKey="key" optionValue="value" value="" noSelection="['': '--Select--']" />
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
	
	<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_ACTUAL_RATE.name()}">
			<g:set value="true" var="isActualRateView"></g:set>
	</shiro:hasPermission>
	
	<div class="col-sm-12 col-md-12">
	<div class="title text-left"><h2><g:message code="etech.report.print.options.label" default="Print Options" /></h2></div>
		<g:if test="${isActualRateView}">
		  <g:if test="${session['logedInUser']?.hoursOnly == false}">
			<div class="col-sm-4 col-md-4">					
				<div class="form-group">		
					<g:checkBox name="printActualCost" value="true" checked="false"/>	<label for="printActualCost" class="control-label"><g:message code="etech.report.print.actual.cost.label" default="Print Actual Cost" /></label>							
						
				</div>
			</div>
		</g:if>
	</g:if>
					
	
	</div>	

</div>	
	<div class="clearfix"></div>