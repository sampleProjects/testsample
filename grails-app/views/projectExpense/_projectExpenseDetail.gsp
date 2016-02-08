<tr class="addExpense">
	<td>
		<span id="span_expenseDetails[${i}].expenseCode.code" class="text-center">${expenseInstance?.code}</span>
		<g:hiddenField name="expenseDetails[${i}].expenseCode.code" id="expenseDetails[${i}].expenseCode.code" value="${expenseInstance?.code}" />
		<g:hiddenField name="expenseDetails[${i}].expenseCode.id" id="expenseDetails[${i}].expenseCode.id" value="${expenseInstance?.id}" />
	</td>
	<td class="text-right"><g:textField class="form-control onlyDecimal pull-right" style="width: 120px;" name="expenseDetails[${i}].qty" id="expenseDetails[${i}].qty" onblur="calculateCost(this.value,document.getElementById('expenseDetails[${i}].unitCost').value,${i});" value="1.00" /></td>
	<td class="text-right"><g:textField class="form-control onlyDecimal pull-right" style="width: 120px;" name="expenseDetails[${i}].unitCost" id="expenseDetails[${i}].unitCost" onblur="calculateCost(document.getElementById('expenseDetails[${i}].qty').value,this.value,${i});" value="${expenseInstance?.standardRate}"/></td>
	<td class="text-right">
		<span id="span_expenseDetails[${i}].totalCost" class="pull-right">0.00</span>
		<g:hiddenField name="expenseDetails[${i}].totalCost" id="expenseDetails[${i}].totalCost" value = "0.00" />
		<g:hiddenField name="expenseDetails[${i}].isFromActualExpense" id="expenseDetails[${i}].isFromActualExpense" value = "${isFromActualExpense}" />		
	</td>
	<td class="text-center">
		<g:if test="${isFromActualExpense=='false'}">
		<g:submitToRemote id="removeProjectExpenseDtl" class="remove-img marginleft5" url="[action: 'removeProjectExpenseDtl']" method="post"  before ="removeProjectExpenseDtlIndex('${i}')" update="addExpenseDiv" value="" />
		</g:if>
	</td>
</tr>
<script>onlyDecimal();</script>