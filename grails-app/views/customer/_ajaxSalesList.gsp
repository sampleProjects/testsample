<div class="clearfix"></div><hr>
 
<g:render template="/common/paginationCombo" model="['totalRecord':salesExecutiveInstanceCount, 'searchAction': 'getSalesExecutiveList', 'searchController': 'customer', 'divIdToUpdate': 'salesExecutiveListGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive height-scroll">	

  		<table class="table table-striped">	
			<thead>
				<tr>
	
					<th><g:message code="etech.report.employee.code.label" default="Code" /></th>
	
					<th><g:message code="etech.report.employee.last.name.label" default="Last Name" /></th>
					
					<th><g:message code="etech.report.employee.first.name.label" default="First Name" /></th>
					
				</tr>
			</thead>
			<g:if test="${salesExecutiveInstanceList?.size() > 0}">
	    		<g:each in="${salesExecutiveInstanceList}" status="i" var="employeeInstance">
					<tr>			
						<td><div style="cursor: pointer;" onclick="setSalesExecutiveCode('${employeeInstance?.id}','${employeeInstance?.code}')" >${fieldValue(bean: employeeInstance, field: "code")}</div></td>
							
						<td>${fieldValue(bean: employeeInstance, field: "lastName")}</td>
						
						<td>${fieldValue(bean: employeeInstance, field: "firstName")}</td>
						
					</tr>
				</g:each>
			</g:if>
			<g:else>
	
				<tr><td colspan="3" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
	
			</g:else>
			
		</table>
</div>

	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getSalesExecutiveList', 'searchController': 'customer', 'divIdToUpdate': 'salesExecutiveListGridDiv', 'searchParams': 'true']" />			
	<div class="pagination margintopbottom15">
			
		<g:paginate total="${salesExecutiveInstanceCount ?: 0}" maxsteps="5" />
				
	</div>