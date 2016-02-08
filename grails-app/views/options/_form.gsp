<%@page import="org.solcorp.etech.BillCodeType"%>
<%@page import="org.solcorp.etech.Constants"%>
<%@ page import="org.solcorp.etech.Options" %>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
			
		<label for="name" class="control-label">
			<g:message code="options.name.label" default="Name" />
			<span class="required-indicator">*</span>
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'name', 'error')} " name="name" value="${optionsInstance?.name}" maxlength="35"/>
		
	</div>
</div>
	
<div class="col-sm-4 col-md-3">
	<div class="form-group">
		
		<label for="addressLine1" class="control-label">
			<g:message code="options.addressLine1.label" default="Address Line 1" />
			<span class="required-indicator">*</span>	
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'addressLine1', 'error')} " name="addressLine1" value="${optionsInstance?.addressLine1}" maxlength="30"/>

	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">

		<label for="addressLine2" class="control-label">
			<g:message code="options.addressLine2.label" default="Address Line 2" />	
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'addressLine2', 'error')}" name="addressLine2" value="${optionsInstance?.addressLine2}" maxlength="30" />
		
	</div>
</div>


<div class="col-sm-4 col-md-3">
	<div class="form-group">	
		
		<label for="city" class="control-label">
			<g:message code="options.city.label" default="City" />
			<span class="required-indicator">*</span>
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'city', 'error')} " name="city" value="${optionsInstance?.city}" maxlength="25"/>
		
	</div>
</div>

<div class="clearfix"></div>
		
<div class="col-sm-4 col-md-3">
	<div class="form-group">

		<label for="state" class="control-label">
			<g:message code="options.state.label" default="State" />
		</label>
		<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'state', 'error')}" name="state" from="${org.solcorp.etech.StateType?.values()}" keys="${org.solcorp.etech.StateType.values()*.name()}" value="${optionsInstance?.state?.name()}" noSelection="['': '--Select--']"/>
		
	</div>
</div>
	
<div class="col-sm-4 col-md-3">
	<div class="form-group">

		<label for="zip1" class="control-label">
			<g:message code="options.zip1.label" default="Zip 1" />
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'zip1', 'error')} onlyInteger " name="zip1" value="${optionsInstance?.zip1}" maxlength="15" />
		
	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">

		<label for="zip2" class="control-label">
			<g:message code="options.zip2.label" default="Zip 2" />
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'zip2', 'error')} onlyInteger" name="zip2" value="${optionsInstance?.zip2}" maxlength="15" />
	
	</div>
</div>
	
<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label for="county" class="control-label">
			<g:message code="options.county.label" default="County" />
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'county', 'error')} " name="county" value="${optionsInstance?.county}" maxlength="25"/>
	
	</div>
</div>

<div class="clearfix"></div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label for="country" class="control-label">
			<g:message code="options.country.label" default="Country" />
			<span class="required-indicator">*</span>
		</label>
		<g:textField class="form-control  fieldcontain ${hasErrors(bean: optionsInstance, field: 'country', 'error')} " name="country" value="${optionsInstance?.country}" maxlength="25"/>
	
	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">

		<label for="phone" class="control-label">
			<g:message code="options.phone.label" default="Phone" />
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'phone', 'error')} phoneNumber" name="phone" value="${optionsInstance?.phone}" maxlength="15"/>
	
	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label for="fax" class="control-label">
			<g:message code="options.fax.label" default="Fax" />
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'fax', 'error')} faxNumber" name="fax" value="${optionsInstance?.fax}" maxlength="15"/>
		
	</div>
</div>
	
<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label for="emailAddress" class="control-label">
			<g:message code="options.emailAddress.label" default="Email Address" />
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'emailAddress', 'error')} " name="emailAddress" value="${optionsInstance?.emailAddress}" maxlength="255"/>
	
	</div>
</div>

<div class="clearfix"></div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
		
		<label for="emailDownload" class="control-label">
			<g:message code="options.emailDownload.label" default="Email Download" />
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'emailDownload', 'error')} " name="emailDownload" value="${optionsInstance?.emailDownload}" maxlength="255"/>
		
	</div>
</div>
		
<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label for="emailSupport" class="control-label">
			<g:message code="options.emailSupport.label" default="Email Support" />	
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'emailSupport', 'error')} " name="emailSupport" value="${optionsInstance?.emailSupport}" maxlength="255"/>
		
	</div>
</div>
	
<div class="col-sm-4 col-md-3">
	<div class="form-group">
		
		<label for="website" class="control-label">
			<g:message code="options.website.label" default="Website" />
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'website', 'error')}" name="website" value="${optionsInstance?.website}" maxlength="255"/>

	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
		
		<label for="federalId" class="control-label">
			<g:message code="options.federalId.label" default="Federal Id" />	
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'federalId', 'error')}" name="federalId" value="${optionsInstance?.federalId}" maxlength="10"/>

	</div>
</div>
	
<div class="clearfix"></div>
	
<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label for="stateId" class="control-label">
			<g:message code="options.stateId.label" default="State Id" />
		</label>
		<g:textField class="form-control  fieldcontain ${hasErrors(bean: optionsInstance, field: 'stateId', 'error')}" name="stateId" value="${optionsInstance?.stateId}" maxlength="13"/>
	
	</div>
</div>	
		

<div class="col-sm-12">

	<div class="title">
		<h2>Time Clock Options</h2>
	</div>
		
	<div class="col-sm-4 col-md-3">
		<div class="form-group">
		
			<label for="usage" class="control-label">
				<g:message code="options.usage.label" default="Usage" />
			</label>
			<g:select class="selectpicker form-control  fieldcontain ${hasErrors(bean: optionsInstance, field: 'usage', 'error')}" name="usage" from="${org.solcorp.etech.OptionsUsageType?.values()}" keys="${org.solcorp.etech.OptionsUsageType.values()*.name()}" value="${optionsInstance?.usage?.name()}"/>
		
		</div>
	</div>
		
	<div class="col-sm-4 col-md-3">
		<div class="form-group">
		
			<label for="inOutTrackingMethod" class="control-label">
				<g:message code="options.inOutTrackingMethod.label" default="In Out Tracking Method" />
			</label>
			<g:select class="selectpicker form-control  fieldcontain ${hasErrors(bean: optionsInstance, field: 'inOutTrackingMethod', 'error')}" name="inOutTrackingMethod" from="${org.solcorp.etech.InOutTrackingMethodType?.values()}" keys="${org.solcorp.etech.InOutTrackingMethodType.values()*.name()}" value="${optionsInstance?.inOutTrackingMethod?.name()}" noSelection="['': '--Select--']"/>
		
		</div>
	</div>
	
</div>
	

		
<div class="col-sm-12">

	<div class="title">
		<h2>Expense Options</h2>
	</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="billDefault" class="control-label">
					<g:message code="options.billDefault.label" default="Bill Default" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'billDefault', 'error')}" name="billDefault" from="${Constants.BILL_DEFAULT}" optionKey="key" optionValue="value" value="${optionsInstance?.billDefault}" noSelection="['': '--Select--']" />
	
			</div>
		</div>
		
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
			
				<label for="reimburseDefault" class="control-label"> 
					<g:message code="options.reimburseDefault.label" default="Reimburse Default" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'reimburseDefault', 'error')} " name="reimburseDefault" from="${Constants.REIMBURSE_DEFAULT}" optionKey="key" optionValue="value" value="${optionsInstance?.reimburseDefault}" noSelection="['': '--Select--']" />
		
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="allowedPastMonths" class="control-label">
					<g:message code="options.allowedPastMonths.label" default="Allowed Past Months" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'allowedPastMonths', 'error')}"  name="allowedPastMonths" from="${Constants.ALLOWED_PAST_MONTHS}" optionKey="key" optionValue="value" value="${optionsInstance?.allowedPastMonths}" noSelection="['': '--Select--']" />
		
			</div>
		</div>
		
</div>
		
<div class="col-sm-12">

	<div class="title">
		<h2>Allocation Parameters</h2>
	</div>
			
		<div class="col-sm-4 col-md-3">
			<div class="form-group">

				<label for="capacityFactor" class="control-label">
					<g:message code="options.capacityFactor.label" default="Capacity Factor" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'capacityFactor', 'error')} " name="capacityFactor" from="${Constants.CAPACITY_FACTOR}" optionKey="key" optionValue="value" value="${optionsInstance?.capacityFactor}" noSelection="['': '--Select--']" />
				
			</div>
		</div>
		
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="entryMethod" class="control-label">
					<g:message code="options.entryMethod.label" default="Entry Method" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'entryMethod', 'error')}" name="entryMethod" from="${Constants.ENTRY_METHOD}" optionKey="key" optionValue="value" value="${optionsInstance?.entryMethod}" noSelection="['': '--Select--']" />
			
			</div>
		</div>
	
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
	
				<label for="capacity" class="control-label">
					<g:message code="options.capacity.label" default="Capacity" />
				</label>
				<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'capacity', 'error')}" name="capacity" value="${optionsInstance?.capacity}" maxlength="16"/>
		
			</div>
		</div>
		
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="capacityType" class="control-label">
					<g:message code="options.capacityType.label" default="Capacity Type" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'capacityType', 'error')}" name="capacityType" from="${Constants.CAPACITY_TYPE}" optionKey="key" optionValue="value" value="${optionsInstance?.capacityType}" noSelection="['': '--Select--']" />
			
			</div>
		</div>
	
	<div class="clearfix"></div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="overAllocation" class="control-label">
					<g:message code="options.overAllocation.label" default="Over Allocation" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'overAllocation', 'error')}" name="overAllocation" from="${Constants.OVER_ALLOCATION}" optionKey="key" optionValue="value" value="${optionsInstance?.overAllocation}" noSelection="['': '--Select--']" />
			
			</div>
		</div>
		
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="editable" class="control-label">
					<g:message code="options.editable.label" default="Editable" />
				</label>
				<g:select class="selectpicker form-control  fieldcontain ${hasErrors(bean: optionsInstance, field: 'editable', 'error')}" name="editable" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.editable}" noSelection="['': '--Select--']" />
		
			</div>
		</div>
	
</div>	
			
<div class="col-sm-12">

	<div class="title">			
		<h2>General</h2>
	</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
	
				<label for="delimiterForExport" class="control-label">
					<g:message code="options.delimiterForExport.label" default="Delimiter For Export" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'delimiterForExport', 'error')} " name="delimiterForExport" from="${Constants.DELIMITER_FOR_EXPORT}" optionKey="key" optionValue="value" value="${optionsInstance?.delimiterForExport}" noSelection="['': '--Select--']" />
			
			</div>
		</div>
		
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="trackProductCode" class="control-label">
					<g:message code="options.trackProductCode.label" default="Track Product Code" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'trackProductCode', 'error')} " name="trackProductCode" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.trackProductCode}" noSelection="['': '--Select--']" />
			
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
		
				<label for="costReporting" class="control-label">
					<g:message code="options.costReporting.label" default="Cost Reporting" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'costReporting', 'error')} " name="costReporting" from="${Constants.COST_REPORTING}" optionKey="key" optionValue="value" value="${optionsInstance?.costReporting}" noSelection="['': '--Select--']" />
			
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
			
				<label for="projectFile" class="control-label" >
					<g:message code="options.projectFile.label" default="Project File" />	
				</label>
				<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'projectFile', 'error')} " name="projectFile" value="${optionsInstance?.projectFile}" maxlength="40"/>
		
			</div>
		</div>
	
		<div class="clearfix"></div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="budgetBy" class="control-label">
					<g:message code="options.budgetBy.label" default="Budget By" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'budgetBy', 'error')}" name="budgetBy" from="${Constants.BUDGET_BY}" optionKey="key" optionValue="value" value="${optionsInstance?.budgetBy}" noSelection="['': '--Select--']" />
			
			</div>
		</div>
		
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="payCode" class="control-label">
					<g:message code="options.payCode.label" default="Pay Code" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'payCode', 'error')}"  name="payCode" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.payCode}" noSelection="['': '--Select--']" />
		
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
			
				<label for="restrictOperationByProjectType" class="control-label">
					<g:message code="options.restrictOperationByProjectType.label" default="Restrict Operation By Project Type" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'restrictOperationByProjectType', 'error')} " name="restrictOperationByProjectType" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.restrictOperationByProjectType}" noSelection="['': '--Select--']" />
			
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="hoursPerMonth" class="control-label">
					<g:message code="options.hoursPerMonth.label" default="Hours Per Month" />
				</label>
				<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'hoursPerMonth', 'error')}" name="hoursPerMonth" value="${optionsInstance?.hoursPerMonth}" maxlength="40"/>
		
			</div>
		</div>
	
</div>
			
<div class="col-sm-12">

	<div class="title">	
		<h2>Project Defaults</h2>
	</div>
		
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="billCode" class="control-label">
					<g:message code="options.billCode.label" default="Bill Code" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'billCode', 'error')}" name="billCode" from="${BillCodeType?.values()}" keys="${BillCodeType.values()*.name()}" value="${optionsInstance?.billCode?.name()}" noSelection="['': '--Select--']"/>
		
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
			
				<label for="phases" class="control-label">
					<g:message code="options.phases.label" default="phases" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'phases', 'error')}" name="phases" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.phases}" noSelection="['': '--Select--']" />
			
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="trackFormat" class="control-label">
					<g:message code="options.trackFormat.label" default="Track Format" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'trackFormat', 'error')}"  name="trackFormat" from="${Constants.TRACK_FORMAT}" optionKey="key" optionValue="value" value="${optionsInstance?.trackFormat}" noSelection="['': '--Select--']" />
		
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">

				<label for="schCompDateRequred" class="control-label">
					<g:message code="options.schCompdDateRequred.label" default="Sch Comp Date Requred" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'schCompDateRequred', 'error')} " name="schCompDateRequred" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.schCompDateRequred}" noSelection="['': '--Select--']" />
		
			</div>
		</div>
	
		<div class="clearfix"></div>	
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
	
				<label for="schStartDateRequired" class="control-label">
					<g:message code="options.schStartDateRequired.label" default="Sch Start Date Required" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'schStartDateRequired', 'error')} " name="schStartDateRequired" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.schStartDateRequired}" noSelection="['': '--Select--']" />
		
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="estimationTimeByTask" class="control-label">
					<g:message code="options.estimationTimeByTask.label" default="Estimation Time By Task" />
				</label>
				<g:select class="selectpicker form-control  fieldcontain ${hasErrors(bean: optionsInstance, field: 'estimationTimeByTask', 'error')}" name="estimationTimeByTask" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.estimationTimeByTask}" noSelection="['': '--Select--']" />
			
			</div>
		</div>
	
</div>

<div class="col-sm-12">

	<div class="title">	
		<h2>Task</h2>
	</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
	
				<label for="taskNumber" class="control-label">
					<g:message code="options.taskNumber.label" default="Number" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'taskNumber', 'error')} "  name="taskNumber" from="${Constants.TASK_NUMBER}" optionKey="key" optionValue="value" value="${optionsInstance?.taskNumber}" noSelection="['': '--Select--']" />
		
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="taskUnique" class="control-label">
					<g:message code="options.taskUnique.label" default="Unique" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'taskUnique', 'error')} " name="taskUnique" from="${Constants.TASK_UNIQUE}" optionKey="key" optionValue="value" value="${optionsInstance?.taskUnique}" noSelection="['': '--Select--']" />
		
			</div>
		</div>

		<div class="col-sm-4 col-md-3">
			<div class="form-group">
	
				<label for="taskMaster" class="control-label">
					<g:message code="options.taskMaster.label" default="Master" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'taskMaster', 'error')}" name="taskMaster" from="${Constants.TASK_MASTER}" optionKey="key" optionValue="value" value="${optionsInstance?.taskMaster}" noSelection="['': '--Select--']" />
		
			</div>
		</div>	
		
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="emplNonAssignedTaskAccess" class="control-label">
					<g:message code="options.emplNonAssignedTaskAccess.label" default="Empl/Non Assigned Task Access" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'emplNonAssignedTaskAccess', 'error')}" name="emplNonAssignedTaskAccess" from="${Constants.EMPL_NONE_ASSIGNED_TASK_ACCESS}" optionKey="key" optionValue="value" value="${optionsInstance?.emplNonAssignedTaskAccess}" noSelection="['': '--Select--']" />
			
			</div>
		</div>
		
		<div class="clearfix"></div>	
		
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="taskNotesVisibility" class="control-label">
					<g:message code="options.taskNotesVisibility.label" default="Notes Visibility" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'taskNotesVisibility', 'error')} " name="taskNotesVisibility" from="${Constants.TASK_NOTES_VISIBILITY}" optionKey="key" optionValue="value" value="${optionsInstance?.taskNotesVisibility}" noSelection="['': '--Select--']" />
		
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="payrollFlag" class="control-label">
					<g:message code="options.payrollFlag.label" default="Payroll Flag" />
				</label>
				<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'payrollFlag', 'error')} " name="payrollFlag" value="${optionsInstance?.payrollFlag}" maxlength="6"/>
			
			</div>
		</div>
	
</div>
			
<div class="col-sm-12">

	<div class="title">	
		<h2>Timesheet</h2>
	</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">

				<label for="startWorkWeek" class="control-label">
					<g:message code="options.startWorkWeek.label" default="Start Work Week" />
				</label>
				<g:select  class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'startWorkWeek', 'error')}" name="startWorkWeek" from="${Constants.START_WORK_WEEK}" optionKey="key" optionValue="value" value="${optionsInstance?.startWorkWeek}" noSelection="['': '--Select--']" />
			
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="manualTimesheetPostBy" class="control-label">
					<g:message code="options.manualTimesheetPostBy.label" default="Manual Timesheet Post By" />	
				</label>
				<g:select  class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'manualTimesheetPostBy', 'error')} " name="manualTimesheetPostBy" from="${Constants.MANUAL_TIMESHEET_POST_BY}" optionKey="key" optionValue="value" value="${optionsInstance?.manualTimesheetPostBy}" noSelection="['': '--Select--']" />
		
			</div>
		</div>

		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="permitOnlyDefaultOperation" class="control-label">
					<g:message code="options.permitOnlyDefaultOperation.label" default="Permit Only Default Operation" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'permitOnlyDefaultOperation', 'error')}" name="permitOnlyDefaultOperation" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.permitOnlyDefaultOperation}" noSelection="['': '--Select--']" />
			
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="useDropdowns" class="control-label">
					<g:message code="options.useDropdowns.label" default="Use Dropdowns" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'useDropdowns', 'error')} " name="useDropdowns" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.useDropdowns}" noSelection="['': '--Select--']" />
			
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="trackQuantity" class="control-label">
					<g:message code="options.trackQuantity.label" default="Track Quantity" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'trackQuantity', 'error')} " name="trackQuantity" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.trackQuantity}" noSelection="['': '--Select--']" />
			
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">	
		
				<label for="dateFormat" class="control-label">
					<g:message code="options.dateFormat.label" default="Date Format" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'dateFormat', 'error')}" name="dateFormat" from="${Constants.DATE_FORMAT_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.dateFormat}" noSelection="['': '--Select--']" />
		
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">	
		
				<label for="mgrEmailonRejectedTS" class="control-label">
					<g:message code="options.mgrEmailonRejectedTS.label" default="Manger Email on Rejected TS" />
				</label>
				<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'mgrEmailonRejectedTS', 'error')} " name="mgrEmailonRejectedTS" value="${optionsInstance?.mgrEmailonRejectedTS}" maxlength="255"/>
			
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">	
	
				<label for="projectFilter" class="control-label">
					<g:message code="options.projectFilter.label" default="Project Filter" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'projectFilter', 'error')}" name="projectFilter" from="${Constants.PROJECT_FILTER}" optionKey="key" optionValue="value" value="${optionsInstance?.projectFilter}" noSelection="['': '--Select--']" />
			
			</div>
		</div>
	
		<div class="clearfix"></div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
	
				<label for="allocateEmployees" class="control-label">
					<g:message code="options.allocateEmployees.label" default="Allocate Employees" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'allocateEmployees', 'error')}" name="allocateEmployees" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.allocateEmployees}" noSelection="['': '--Select--']" />
			
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="overTimeProcessing" class="control-label">
					<g:message code="options.overTimeProcessing.label" default="Over Time Processing" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'overTimeProcessing', 'error')}"  name="overTimeProcessing" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.overTimeProcessing}" noSelection="['': '--Select--']" />
		
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="timeSheetFormatFile" class="control-label">
					<g:message code="options.timeSheetFormatFile.label" default="Time Sheet Format File" />
				</label>
				<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'timeSheetFormatFile', 'error')}" name="timeSheetFormatFile" value="${optionsInstance?.timeSheetFormatFile}" maxlength="40"/>
		
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="timesheetSummary" class="control-label">
					<g:message code="options.timesheetSummary.label" default="Timesheet Summary" />			
				</label>
				<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'timesheetSummary', 'error')}" name="timesheetSummary" value="${optionsInstance?.timesheetSummary}" maxlength="40"/>
			
			</div>
		</div>
	
		<div class="clearfix"></div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="timesheetApproval" class="control-label">
					<g:message code="options.timesheetApproval.label" default="Timesheet Approval" />
				</label>
				<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'timesheetApproval', 'error')}" name="timesheetApproval" value="${optionsInstance?.timesheetApproval}" maxlength="40"/>
		
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
			
				<label for="payrollExport" class="control-label">
					<g:message code="options.payrollExport.label" default="Payroll Export" />
				</label>
				<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'payrollExport', 'error')} " name="payrollExport" value="${optionsInstance?.payrollExport}" maxlength="40"/>
		
			</div>
		</div>

		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="phaseTimeSheet" class="control-label">
					<g:message code="options.phaseTimeSheet.label" default="Phases" />
				</label>
				<g:select class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'phaseTimeSheet', 'error')}" name="phaseTimeSheet" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.phaseTimeSheet}" noSelection="['': '--Select--']" />
		
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">	
		
				<label for="operation" class="control-label">
					<g:message code="options.operation.label" default="Operation" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'operation', 'error')} " name="operation" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.operation}" noSelection="['': '--Select--']" onChange = "checkDefOnlyOption();" />
		
			</div>
		</div>
	
		<div class="clearfix"></div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">	
		
				<label for="task" class="control-label">
					<g:message code="options.task.label" default="Task" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'task', 'error')} "  name="task" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.task}" noSelection="['': '--Select--']" />
			
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="billCodeTimeSheet" class="control-label">
					<g:message code="options.billCodeTimeSheet.label" default="Bill Code Time Sheet" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'billCodeTimeSheet', 'error')}" name="billCodeTimeSheet" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.billCodeTimeSheet}" noSelection="['': '--Select--']" />
		
			</div>
		</div>

</div>
		

<div class="col-sm-12">

	<div class="title">
		<h2>Invoice</h2>
	</div>
				
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
	
				<label for="billTrxLessStartDate" class="control-label">
					<g:message code="options.billTrxLessStartDate.label" default="Bill Trx Less Start Date" />	
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'billTrxLessStartDate', 'error')}" name="billTrxLessStartDate" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.billTrxLessStartDate}" noSelection="['': '--Select--']" />
		
			</div>
		</div>	
		
		<div class="col-sm-4 col-md-3">
			<div class="form-group">	
		
				<label for="invoicePrintOptions" class="control-label">
					<g:message code="options.invoicePrintOptions.label" default="Invoice Print Options" />	
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'invoicePrintOptions', 'error')}" name="invoicePrintOptions" from="${Constants.INVOICE_PRINT_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.invoicePrintOptions}" noSelection="['': '--Select--']" />
		
			</div>
		</div>

		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="invoiceFromEmail" class="control-label">
					<g:message code="options.invoiceFromEmail.label" default="Invoice From Email" />
				</label>
				<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'invoiceFromEmail', 'error')} " name="invoiceFromEmail" value="${optionsInstance?.invoiceFromEmail}" maxlength="255"/>
			
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
			
				<label for="invoiceReportFile" class="control-label">
					<g:message code="options.invoiceReportFile.label" default="Invoice Report File" />
				</label>
				<g:textField class="form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'invoiceReportFile', 'error')}" name="invoiceReportFile" value="${optionsInstance?.invoiceReportFile}" maxlength="40"/>
		
			</div>
		</div>

</div>
	
<div class="col-sm-12">

	<div class="title">			
		<h2>Main Menu</h2>
	</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">
		
				<label for="myProjects" class="control-label">
					<g:message code="options.myProjects.label" default="Display My Projects" />	
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'myProjects', 'error')} " name="myProjects" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.myProjects}" noSelection="['': '--Select--']" />
		
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">

				<label for="myEmployees" class="control-label">
					<g:message code="options.myEmployees.label" default="Display My Employees" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'myEmployees', 'error')} " name="myEmployees" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.myEmployees}" noSelection="['': '--Select--']" />
		
			</div>
		</div>
	
		<div class="col-sm-4 col-md-3">
			<div class="form-group">

				<label for="myTasks" class="control-label">
					<g:message code="options.myTasks.label" default="Display My Tasks" />
				</label>
				<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: optionsInstance, field: 'myTasks', 'error')} " name="myTasks" from="${Constants.YES_NO_OPTIONS}" optionKey="key" optionValue="value" value="${optionsInstance?.myTasks}" noSelection="['': '--Select--']" />
		
			</div>
		</div>
	
</div>