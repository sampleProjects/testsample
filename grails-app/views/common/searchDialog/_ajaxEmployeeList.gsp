<div class="clearfix"></div><hr>
 
<g:render template="/common/paginationCombo" model="['totalRecord':employeeInstanceCount, 'searchAction': 'getEmployeeList', 'searchController': 'report', 'divIdToUpdate': 'employeeListGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive height-scroll">	

  		<table class="table table-striped">	
			<thead>
				<tr>
	
					<th><g:message code="etech.report.employee.code.label" default="Code" /></th>
	
					<th><g:message code="etech.report.employee.last.name.label" default="Last Name" /></th>
					
					<th><g:message code="etech.report.employee.first.name.label" default="First Name" /></th>
					
					<th><g:message code="etech.report.employee.pay.dept.label" default="Pay Dept" /></th>
					
				</tr>
			</thead>
			<g:if test="${employeeInstanceList?.size() > 0}">
	    		<g:each in="${employeeInstanceList}" status="i" var="employeeInstance">
					<tr>			
						<td><div style="cursor: pointer;" onclick="setEmployeeCode('${employeeInstance?.code}')" >${fieldValue(bean: employeeInstance, field: "code")}</div></td>
							
						<td>${fieldValue(bean: employeeInstance, field: "lastName")}</td>
						
						<td>${fieldValue(bean: employeeInstance, field: "firstName")}</td>
						
						<td>${fieldValue(bean: employeeInstance?.payDept, field: "code")}</td>
						
					</tr>
				</g:each>
			</g:if>
			<g:else>
	
				<tr><td colspan="3" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
	
			</g:else>
			
		</table>
</div>

	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getEmployeeList', 'searchController': 'report', 'divIdToUpdate': 'employeeListGridDiv', 'searchParams': 'true']" />			
	<div class="pagination margintopbottom15">
			
		<g:paginate total="${employeeInstanceCount ?: 0}" maxsteps="5" />
				
	</div>