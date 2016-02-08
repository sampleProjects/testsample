<g:if test="${isLaborExpenseAdded == 'true' || isLaborExpenseAdded == true}">
	<g:select id="projectProductDetails${i}_projectServiceDetails${j}_service"
		name="projectProductDetails[${i}].projectServiceDetails[${j}].service.id" from="${serviceList}" onChange="serviceChange(this);"
		optionKey="id" optionValue="code"
		value=""
		class="form-control serviceValid" noSelection="['': '--Select--']" disabled="disabled"/>
</g:if>
<g:else>
	<g:select id="projectProductDetails${i}_projectServiceDetails${j}_service"
			name="projectProductDetails[${i}].projectServiceDetails[${j}].service.id" from="${serviceList}" onChange="serviceChange(this);"
			optionKey="id" optionValue="code"
			value=""
			class="form-control serviceValid" noSelection="['': '--Select--']" />
</g:else>