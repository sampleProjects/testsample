<div class="col-sm-4 col-md-3">
	<div class="form-group"> 

		<label for="laborActivityCode" class="control-label"><g:message code="etech.employee.laborActCode.label" default="Labor Activity Code" /></label>
		<g:select name="laborActivityCode" class="selectpicker form-control  fieldcontain ${hasErrors(bean: employeeInstance, field: 'laborActivityCode', 'error')} "  from="${laborActCodeList}"  optionKey="id" optionValue="code" value="${employeeInstance?.laborActivityCode?.id}" noSelection="['': '--Select--']" />			

	</div>
</div>