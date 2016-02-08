<div class="clearfix"></div><hr>
 
<g:render template="/common/paginationCombo" model="['totalRecord':expenseInstanceCount, 'searchAction': 'getExpenseList', 'searchController': 'report', 'divIdToUpdate': 'expenseListGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive height-scroll">	

  		<table class="table table-striped">	
			<thead>
				<tr>
	
					<th><g:message code="etech.report.expense.code.label" default="Code" /></th>
	
					<th><g:message code="etech.report.expense.description.label" default="Description" /></th>
					
				</tr>
			</thead>
			<g:if test="${expenseInstanceList?.size() > 0}">
	    		<g:each in="${expenseInstanceList}" status="i" var="expenseInstance">
					<tr>			
						<td><div style="cursor: pointer;" onclick="setExpenseCode('${expenseInstance?.code}')" >${fieldValue(bean: expenseInstance, field: "code")}</div></td>
							
						<td>${fieldValue(bean: expenseInstance, field: "description")}</td>
						
					</tr>
				</g:each>
			</g:if>
			<g:else>
	
				<tr><td colspan="3" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
	
			</g:else>
			
		</table>
</div>

	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getExpenseList', 'searchController': 'report', 'divIdToUpdate': 'expenseListGridDiv', 'searchParams': 'true']" />			
	<div class="pagination margintopbottom15">
			
		<g:paginate total="${expenseInstanceCount ?: 0}" maxsteps="5" />
				
	</div>