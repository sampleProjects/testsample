<g:if test="${expenseList?.size() > 0}">
<g:render template="/common/paginationCombo" model="['totalRecord':expenseListCount, 'searchAction': 'getExpenseList', 'searchController': 'projectExpense', 'divIdToUpdate': 'listGridDiv']"></g:render>
<div id="ajaxListView" class="title-content">
	<div class="table-responsive height-scroll">
	
  		<table class="table table-striped">	
			<thead>
				<tr>
	
					<th><g:message code="etech.projectExpense.code.label" default="Code" /></th>
	
					<th><g:message code="etech.projectExpense.description.label" default="Description" /></th>
	
				</tr>
			</thead>
			<g:each in="${expenseList}" status="i" var="expenseInstance">	
				<tr>
						
					<td><div style="cursor: pointer;" onclick="setClickVal('${expenseInstance?.id}','${expenseInstance?.code}', 'false')" >${fieldValue(bean: expenseInstance, field: "code")}</div></td>
				
					<td><div style="cursor: pointer;" onclick="setClickVal('${expenseInstance?.id}','${expenseInstance?.code}', 'false')" >${fieldValue(bean: expenseInstance, field: "description")}</div></td>
				
				</tr>				
			</g:each>				
		</table>
	</div>
	
	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getExpenseList', 'searchController': 'projectExpense', 'divIdToUpdate': 'listGridDiv',  'searchParams': 'true']" />
	
	<div class="pagination margintopbottom15">
		<g:paginate total="${expenseListCount ?: 0}" />
	</div>
</div>
</g:if>
 

