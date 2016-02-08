<g:each var="expenseDetails" in="${expenseDetailsList}" status="i">

<tr class="addExpense">
	<td>
	<g:if test="${expenseDetails?.id}">
		<g:hiddenField name="expenseDetails[${i}].id" value="${expenseDetails?.id}"/>
	</g:if>
		<span id="span_expenseDetails[${i}].expenseCode.code" class="text-center">${expenseDetails?.expenseCode?.code}</span>
		<g:hiddenField name="expenseDetails[${i}].expenseCode.code" id="expenseDetails[${i}].expenseCode.code" value="${expenseDetails?.expenseCode?.code}" />
		<g:hiddenField name="expenseDetails[${i}].expenseCode.id" id="expenseDetails[${i}].expenseCode.id" value="${expenseDetails?.expenseCode?.id}" />
	</td>
	<td class="text-right"><g:textField class="form-control onlyDecimal pull-right" style="width: 120px;" name="expenseDetails[${i}].qty" id="expenseDetails[${i}].qty" onblur="calculateCost(this.value,document.getElementById('expenseDetails[${i}].unitCost').value,${i});" value="${expenseDetails?.qty}" /></td>
	<td class="text-right"><g:textField class="form-control onlyDecimal pull-right" style="width: 120px;" name="expenseDetails[${i}].unitCost" id="expenseDetails[${i}].unitCost" onblur="calculateCost(document.getElementById('expenseDetails[${i}].qty').value,this.value,${i});" value="${expenseDetails?.unitCost}" /></td>
	<td class="text-right">
		<span id="span_expenseDetails[${i}].totalCost" class="pull-right">${expenseDetails?.totalCost}</span>
		<g:hiddenField name="expenseDetails[${i}].totalCost" id="expenseDetails[${i}].totalCost" value="${expenseDetails?.totalCost}" />
		<g:hiddenField name="expenseDetails[${i}].isFromActualExpense" id="expenseDetails[${i}].isFromActualExpense" value = "${expenseDetails?.isFromActualExpense}" />
	</td>
	<td class="text-center">
		<g:if test="${!expenseDetails?.isFromActualExpense}">
			<g:submitToRemote id="removeProjectExpenseDtl" class="remove-img marginleft5" url="[action: 'removeProjectExpenseDtl']" method="post"  before ="removeProjectExpenseDtlIndex('${i}')" update="addExpenseDiv" value="" />
		</g:if>
	</td>
</tr>
</g:each> 		