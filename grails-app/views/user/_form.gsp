<%@ page import="org.solcorp.etech.User" %>
<%@ page import="org.solcorp.etech.Constants" %>
<%@ page import="org.solcorp.etech.StatusType" %>
<%@ page import="org.solcorp.etech.EmployeeReportType" %>
<%@ page import="org.solcorp.etech.RoleType" %>
<%@ page import="org.solcorp.etech.PermissionType" %>

<div class="col-sm-4 col-md-3">
	<div class="form-group">		
		
		<label class="control-label" for="userId"><g:message code="etech.user.userid.label" default="User ID" /><span class="required-indicator">*</span></label>
		<g:textField name="username" value="${userInstance?.username}" maxlength="${Constants.USER_NAME_LENGTH}"  disabled = "${editMode?:disabled}" class="form-control ${hasErrors(bean: userInstance, field: 'username', 'error')} required"/>
		
	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
		
		<label class="control-label" for="status"><g:message code="etech.user.status.label" default="User Status" /><span class="required-indicator">*</span></label>
		<g:select name="status" from="${StatusType?.userStatusTypeList()}" keys="${StatusType.values()*.name()}" class="selectpicker form-control fieldcontain ${hasErrors(bean: userInstance, field: 'status', 'error')} " value="${userInstance?.status?.name()}" noSelection="['': '--Select--']"/>
	
	</div>
</div>
	
<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label class="control-label" for="password"><g:message code="etech.user.password.label" default="Password" /><span class="required-indicator">*</span></label>
		<g:passwordField name="password" value="" maxlength="${Constants.MAX_PASSWORD_LENGTH}" class="form-control fieldcontain ${hasErrors(bean: userInstance, field: 'passwordHash', 'error')} required"/>
	
	</div>
</div>
	
<div class="col-sm-4 col-md-3">
	<div class="form-group">
			
		<label class="control-label" for="confirmPassword"><g:message code="etech.user.confirm.password.label" default="Confirm Password" /></label>
		<g:passwordField name="confirmPassword" value="" maxlength="${Constants.MAX_PASSWORD_LENGTH}" class="form-control medium-input"/>
		
	</div>				
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
		<label class="control-label" for="roles"><g:message code="etech.user.emp.roles.label" default="Role" />
			<span class="required-indicator">*</span>
		</label>
		<g:if test="${userInstance?.roles}">
			<g:select name="roles" class="selectpicker form-control fieldcontain ${hasErrors(bean: userInstance, field: 'roles', 'error')} required" from="${RoleType?.roleList()}" keys="${RoleType.roleList()*.name()}" value="${userInstance?.roles[0]?.name}" noSelection="['': '--Select--']"/>			
		</g:if>
		<g:else>
			<g:select name="roles" class="selectpicker form-control fieldcontain ${hasErrors(bean: userInstance, field: 'roles', 'error')} required" from="${RoleType?.roleList()}" keys="${RoleType.roleList()*.name()}" noSelection="['': '--Select--']"/>			
		</g:else>
	</div>		
</div>
	 	
<div class="col-sm-4 col-md-3">
	<div class="form-group">			
		
		<label class="control-label" for="tsApprove"><g:message code="etech.user.emp.supervisor.tm.label" default="Employee Supervisor"/>
			<span class="required-indicator">*</span>
		</label>
		<g:select name="tsApprove" class="selectpicker form-control fieldcontain ${hasErrors(bean: userInstance, field: 'tsApprove', 'error')} required" from="${Constants.YES_NO}" optionKey="key" optionValue="value" value="${userInstance?.tsApprove}" noSelection="['': '--Select--']" /> 
	
	</div>
</div>		
	
<div class="col-sm-4 col-md-3">
	<div class="form-group ${userInstance?.employee?.code && editMode ? '' :'edit-code' }">
		  	<g:hiddenField name="employee" value="${userInstance?.employee?.id}" />
		  	<g:if test="${userInstance?.employee?.code && editMode}">
		  		<label class="control-label" for="employee"><g:message code="etech.user.employeeid.label" default="Employee ID" ></g:message></label>
		  		<label><span class="required-indicator">*</span></label>
		  		<g:textField name="employeetxt" value="${userInstance?.employee?.code}"  disabled = "${userInstance?.employee?.code?:disabled}" class="form-control fieldcontain ${hasErrors(bean: userInstance, field: 'employee', 'error')} required"/>
		  	</g:if>
		  	<g:else>
		  		<label class="control-label text-underline" onclick="employeePopup()" for="employee"><g:message code="etech.user.employeeid.label" default="Employee ID" ></g:message></label>
		  		<label><span class="required-indicator">*</span></label>
		  		<g:textField name="employeeTxt" value="${userInstance?.employee?.code ?: userInstance?.employeeTxt}"  class="form-control fieldcontain ${hasErrors(bean: userInstance, field: 'employee', 'error')} pull-left" disabled="${editMode ?: disabled}" onblur="checkEmployee(this);"/>
		  		<span onClick="clearEmployee();" class="pull-left fa fa-remove cursor-pointer" data-toggle="tooltip" data-original-title="Clear: Employee ID" data-placement="top"></span>
		  	</g:else>
	</div>
</div>
	  	
	  	
	  	
<div class="col-sm-4 col-md-3">
	<div class="form-group">
				
		<label class="control-label" for="empReportType"><g:message code="etech.user.emp.reports.label" default="Employee Reports" />
			<span class="required-indicator">*</span>
		</label>
		<g:select name="empReportType" class="selectpicker form-control fieldcontain ${hasErrors(bean: userInstance, field: 'empReportType', 'error')} required" from="${EmployeeReportType?.employeeReportTypeList()}" keys="${EmployeeReportType.values()*.name()}" value="${userInstance?.empReportType?.name()}" noSelection="['': '--Select--']"/>
	
	</div>		
</div>

<div class="clearfix"></div>


<div class="col-sm-4 col-md-3" style="display:none;" id="hoursOnlyDiv">
	<div class="form-group form-control checkbox-border-none">			
		
		<label class="control-label" for="hoursOnly">
			<g:message code="etech.user.emp.hoursOnly.label" default="Hours Only"/>
		</label><br>
		<g:checkBox name="hoursOnly" value="${userInstance?.hoursOnly}" class="fieldcontain ${hasErrors(bean: userInstance, field: 'hoursOnly', 'error')} "/> 
	
	</div>
</div>	

		
<div class="col-sm-4 col-md-3">
	<div class="form-group form-control checkbox-border-none">
	
		<label for="isPasswordChangeRequired" class="control-label">
			<g:message code="etech.user.change.pswd.label" default="User must change password at next login" />		
		</label>
		<g:checkBox name="isPasswordChangeRequired" value="${userInstance?.isPasswordChangeRequired}" class="fieldcontain ${hasErrors(bean: userInstance, field: 'isPasswordChangeRequired', 'error')} "/>

	</div>
</div> 

<script>
function checkEmployee(employeeCode){
	if(employeeCode.value.trim().length > 0 && employeeCode.value != null){
		<g:remoteFunction controller="user" action="checkEmployee" params="'employeeCode='+employeeCode.value"  onSuccess='setEmployee(data)'/>
	}else {
		$('#employee').val('');
	}
}
</script>