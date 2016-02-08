<%@page import="org.solcorp.etech.LaborActivityCodeController"%>
<%@ page import="org.solcorp.etech.LaborActivityCode" %>
<%@ page import="org.solcorp.etech.Constants" %>

<div class="col-sm-4 col-md-3">
	<div class="form-group ${laborActivityCodeInstance?.id ? 'edit-code' : ''}">
		
		<label for="code" class="control-label">
			<g:message code="laborActivityCode.code.label" default="Labor Activity Code" />
			<span class="required-indicator">*</span>
		</label>
		
		<g:if test="${laborActivityCodeInstance?.id}">
			<g:if test="${laborActivityCodeInstance?.code?.equals(Constants.DEFAULT_LABOR_ACTIVITY_CODE) == true}">
				<div>${laborActivityCodeInstance?.code}</div>
				<g:hiddenField name="code" value="${laborActivityCodeInstance?.code}"/>
			</g:if>
			<g:else>
				<g:textField class="form-control  fieldcontain ${hasErrors(bean: laborActivityCodeInstance, field: 'code', 'error')} required pull-left" onblur="keyFilter(this);" name="code" maxlength="${Constants.LABORACTIVITYCODE_CODE_LENGTH}" disabled = "disabled" value="${laborActivityCodeInstance?.code}"/>
				<a href="#" onClick="enableCodeField('code');" class="pull-left fa fa-pencil" data-toggle="tooltip" data-original-title="Edit : Code" data-placement="top"></a>
			</g:else>
		</g:if>
		<g:else>
				<g:textField class="form-control  fieldcontain ${hasErrors(bean: laborActivityCodeInstance, field: 'code', 'error')} required" name="code" onblur="keyFilter(this);" maxlength="${Constants.LABORACTIVITYCODE_CODE_LENGTH}" disabled = "${laborActivityCodeInstance?.id?:disabled}" value="${laborActivityCodeInstance?.code}"/>
		</g:else> 
	
	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label for="description" class="control-label">
			<g:message code="laborActivityCode.description.label" default="Description" />		
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: laborActivityCodeInstance, field: 'description', 'error')}" name="description" maxlength="${Constants.LABORACTIVITYCODE_DESCRIPTION_LENGTH}" value="${laborActivityCodeInstance?.description}"/>
			
	</div>
</div>
		
<div class="col-sm-4 col-md-3">
	<div class="form-group">	
		
		<label for="laborActivityGroup" class="control-label">
			<g:message code="laborActivityCode.laborActivityGroup.label" default="Labor Activity Department" />
			<span class="required-indicator">*</span>
		</label>
		<g:select id="laborActivityGroup" class="selectpicker form-control  fieldcontain ${hasErrors(bean: laborActivityCodeInstance, field: 'laborActivityGroup', 'error')} required" name="laborActivityGroup.id" from="${activityDepartmentList}" optionKey="id" optionValue="code" value="${laborActivityCodeInstance?.laborActivityGroup?.id}" noSelection="['': '--Select--']" />
		
	</div>
</div>
		
<div class="col-sm-4 col-md-3">
	<div class="form-group">	
		
		<label for="standardRate" class="control-label">
			<g:message code="laborActivityCode.standardRate.label" default="Standard Rate" />
			<span class="required-indicator">*</span>
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: laborActivityCodeInstance, field: 'standardRate', 'error')} required onlyDecimal" name="standardRate" value="${laborActivityCodeInstance?.standardRate}" style="text-align: right"/>

	</div>
</div>
		
<div class="clearfix"></div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">			

		<label for="overHead" class="control-label">
			<g:message code="laborActivityCode.overHead.label" default="Overhead %" />
		</label>
		<g:textField name="overHead" class="form-control  fieldcontain ${hasErrors(bean: laborActivityCodeInstance, field: 'overHead', 'error')}  onlyDecimal"  value="${laborActivityCodeInstance?.overHead}" style="text-align: right"/>
		
	</div>
</div>
		
<div class="col-sm-4 col-md-3">
	<div class="form-group">	
	
		<label for="billRate" class="control-label">
			<g:message code="laborActivityCode.billRate.label" default="Bill Rate" />
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: laborActivityCodeInstance, field: 'billRate', 'error')} onlyDecimal" name="billRate"  value="${laborActivityCodeInstance?.billRate}" style="text-align: right" />

	</div>
</div>	
			
<div class="col-sm-4 col-md-3">
	<div class="form-group">	

		<label for="qcFlag" class="control-label">
			<g:message code="laborActivityCode.qcFlag.label" default="QC Flag" />
		</label>
		<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: laborActivityCodeInstance, field: 'qcFlag', 'error')} required" name="qcFlag" from="${Constants.YES_NO_MAP}" optionKey="key" optionValue="value" value="${laborActivityCodeInstance?.qcFlag}" noSelection="['': '--Select--']" />

	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">	
			
		<label for="countPoint" class="control-label">
			<g:message code="laborActivityCode.countPoint.label" default="Count Point" />
		</label>
		<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: laborActivityCodeInstance, field: 'countPoint', 'error')} required" name="countPoint" from="${Constants.YES_NO_MAP}" optionKey="key" optionValue="value" value="${laborActivityCodeInstance?.countPoint}" noSelection="['': '--Select--']" />
	
	</div>
</div>
	
<div class="clearfix"></div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">			

		<label for="operations" class="control-label">
			<g:message code="laborActivityCode.operations.label" default="Operations" />
		</label>
		<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: laborActivityCodeInstance, field: 'operations', 'error')} required" name="operations" from="${Constants.YES_NO_MAP}" optionKey="key" optionValue="value" value="${laborActivityCodeInstance?.operations}" noSelection="['': '--Select--']" />

	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">	
	
		<label for="active" class="control-label">
			<g:message code="laborActivityCode.active.label" default="Status" />
		</label>
		<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: laborActivityCodeInstance, field: 'active', 'error')} required" name="active" from="${Constants.LABORACTIVITYCODE_STATUS}" optionKey="key" optionValue="value" value="${laborActivityCodeInstance?.active}" noSelection="['': '--Select--']" />
	
	</div>
</div>

<g:if test="${laborActivityCodeInstance?.id}">
<div class="col-sm-4 col-md-3">
	<div class="form-group">			
		<label for="description" class="control-label"><g:message code="laborActivityCode.pay.department.label" default="Pay Department" /></label></br>
		${grailsApplication.config.departmentMap.get(laborActivityCodeInstance?.laborActivityGroup?.code)}	
	</div>
</div>
</g:if>