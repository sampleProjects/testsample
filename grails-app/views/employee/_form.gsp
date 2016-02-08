<%@ page import="org.solcorp.etech.Employee" %>
<%@ page import="org.solcorp.etech.Constants" %>
<%@ page import="org.solcorp.etech.StatusType" %>
<%@ page import="org.solcorp.etech.EmployeeType" %>

<div class="col-sm-4 col-md-3">
	<div class="form-group ${employeeInstance?.id ? 'edit-code' : ''}">			
		
		<div ><label for="code" class="control-label">			
		<g:message code="etech.user.employee.id.label" default="ID" /></label><label class="control-label"><span class="required-indicator" >*</span></label>
		</div>					
		<g:textField name="code" onblur="keyFilter(this);" id="code" value="${employeeInstance?.code}" maxlength="${Constants.EMPLOYEE_CODE_LENGTH}" class="form-control  fieldcontain ${hasErrors(bean: employeeInstance, field: 'code', 'error')} required pull-left" disabled="${editMode?:disabled}"/>
		<g:if test="${employeeInstance?.id}">
			<a href="#" onClick="enableCodeField('code');" class="pull-left fa fa-pencil" data-toggle="tooltip" data-original-title="Edit : Code" data-placement="top"></a>
		</g:if>		
	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
	  
		<label for="lastName" class="control-label"><g:message code="etech.employee.lastName.label" default="Last Name" /><span class="required-indicator">*</span></label>
		<g:textField name="lastName" value="${employeeInstance?.lastName}" maxlength="${Constants.EMPLOYEE_LAST_NAME_LENGTH}" class="form-control  fieldcontain ${hasErrors(bean: employeeInstance, field: 'lastName', 'error')}"/>
	
	</div>	      
</div>
	
	
<div class="col-sm-4 col-md-3">
	<div class="form-group">
			 
		<label for="firstName" class="control-label"><g:message code="etech.employee.firstName.label" default="First Name" /><span class="required-indicator">*</span></label>
		<g:textField name="firstName" value="${employeeInstance?.firstName}" maxlength="${Constants.EMPLOYEE_FIRST_NAME_LENGTH}" class="form-control fieldcontain ${hasErrors(bean: employeeInstance, field: 'firstName', 'error')}"/>
	
	</div>
</div>
		
<div class="col-sm-4 col-md-3">
	<div class="form-group">
		
		<label for="middleName" class="control-label"><g:message code="etech.employee.middleName.label" default="Middle Name"/></label>
		<g:textField name="middleName" value="${employeeInstance?.middleName}" maxlength="${Constants.EMPLOYEE_MIDDLE_NAME_LENGTH}" class="form-control  fieldcontain ${hasErrors(bean: employeeInstance, field: 'middleName', 'error')}"/>
	
	</div>	
</div>
	
<div class="clearfix"></div>
			
<div class="col-sm-4 col-md-3">
	<div class="form-group"> 
				
		<label for="laborActivityGroup" style="cursor: pointer;text-decoration: underline;" data-toggle="modal" data-target="#myModal" class="control-label"><g:message code="etech.employee.laborActGrp.label" default="Labor Activity Group" /></label>
		<g:hiddenField name="laborActivityGroup" value="${employeeInstance?.laborActivityGroup?.id}"/>
		<g:textField name="laborActDept" value="${employeeInstance?.laborActivityGroup?.code }" maxlength="${Constants.EMPLOYEE_LABER_DEPT_LENGTH}" class="form-control fieldcontain ${hasErrors(bean: employeeInstance, field: 'laborActivityGroup', 'error')} "  disabled="disabled"/>
	
	</div>
</div>

	<div id="laborActCodeDiv">
		<g:render template="laborActCodeAjaxCombo"></g:render>
	</div>			 

<div class="col-sm-4 col-md-3">
	<div class="form-group"> 	 
		
		<label for="payDept" class="control-label" style="cursor: pointer; text-decoration: underline;" data-toggle="modal" data-target="#deptCodeDialog"><g:message code="etech.employee.payDept.label" default="Pay Dept" /></label><label class="control-label"><span class="required-indicator" >*</span></label>
		<%--<g:select name="payDeptCode" id="payDeptCode" from="${Constants.PAY_DEPT_MAP}" class="selectpicker form-control fieldcontain ${hasErrors(bean: employeeInstance, field: 'payDept', 'error')}" optionKey="key" optionValue="value" value="${employeeInstance?.payDept}" noSelection="['': '--Select--']" /> 
		--%>
		<g:textField name="payDeptCode" id="payDeptCode" value="${employeeInstance?.payDept?.code}" class="selectpicker form-control fieldcontain ${hasErrors(bean: employeeInstance, field: 'payDept', 'error')}" disabled="disabled"/>
		<g:hiddenField name="payDept" id="payDept" value="${employeeInstance?.payDept?.id}"/>
	</div> 
</div>
	
<div class="col-sm-4 col-md-3">
	<div class="form-group"> 
	
		<label for="supervisor" class="control-label"><g:message code="etech.employee.supervisor.label" default="Supervisor" /></label>
		<g:select name="supervisor" from="${supervisorList}" class="selectpicker form-control fieldcontain ${hasErrors(bean: employeeInstance, field: 'supervisor', 'error')}" optionKey="id" optionValue="employeeName"  value="${employeeInstance?.supervisor?.id}" noSelection="['': '--Select--']"/>	
	
	</div>	 		
</div>

<div class="clearfix"></div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
	 
		<label for="status" class="control-label"><g:message code="etech.employee.status.label" default="Status" /><span class="required-indicator">*</span></label>
		<g:select name="status" from="${StatusType?.employeeStatusTypeList()}" class="selectpicker form-control fieldcontain ${hasErrors(bean: employeeInstance, field: 'status', 'error')}" keys="${StatusType.values()*.name()}" value="${employeeInstance?.status?.name()}" noSelection="['': '--Select--']" />
	
	</div>
</div>
	
<div class="col-sm-4 col-md-3">
	<div class="form-group">            
        
		<label for="employeeType" class="control-label"><g:message code="etech.employee.employeeType.label" default="Employee Type" /><span class="required-indicator">*</span></label>
		<g:select name="employeeType" from="${EmployeeType?.employeeTypelist()}" class="selectpicker form-control fieldcontain ${hasErrors(bean: employeeInstance, field: 'employeeType', 'error')} " keys="${EmployeeType.values()*.name()}" value="${employeeInstance?.employeeType?.name()}" noSelection="['': '--Select--']"/>
		
	</div>
</div>
     

<div class="col-sm-4 col-md-3">
	<div class="form-group">                 
       
		<label for="email" class="control-label"><g:message code="etech.employee.email.label" default="Email Address"/></label>
		<g:textField name="email" value="${employeeInstance?.email}" maxlength="${Constants.EMPLOYEE_EMAIL_LENGTH}" class="form-control fieldcontain ${hasErrors(bean: employeeInstance, field: 'email', 'error')} "/>
			         
   </div>
</div>

<%-- 
 <div class="col-sm-4 col-md-3">
	<div class="form-group checkbox-border-none">     
		<label for="isAccExecutive" class="control-label"><g:message code="app.accountDirector.label" default="Account Director" /></label>
		<br/>
		<g:checkBox name="isAccExecutive" value="${employeeInstance?.isAccExecutive}" class="fieldcontain ${hasErrors(bean: employeeInstance, field: 'isAccExecutive', 'error')} "/>		 
     </div> 
 </div>
     
--%>


<script type="text/javascript">
function fillLaborActCodeCombo(laborGrpId) { 
	<g:remoteFunction controller="employee" action="getLaborActCodeList" update="laborActCodeDiv" params="'laborActivityGroup='+laborGrpId"/>
}
</script>
