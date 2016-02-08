<div class="clearfix"></div><hr>
 
<g:render template="/common/paginationCombo" model="['totalRecord':empMstListCount, 'searchAction': 'getEmployeeMstList', 'searchController': 'employee', 'divIdToUpdate': 'empCodelistGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive height-scroll">	

  		<table class="table table-striped">	
			<thead>
				<tr>
	
					<th><g:message code="etech.employee.code.label" default="Employee Code" /></th>
	
					<th><g:message code="etech.employee.firstName.label" default="First Name" /></th>
					
					<th><g:message code="etech.employee.lastName.label" default="Last Name" /></th>
	
				</tr>
			</thead>
			<g:if test="${employeeMasterList?.size() > 0}">
	    		<g:each in="${employeeMasterList}" status="i" var="employeeMstInstance">
	    			
	    			<g:if test="${employeeList.contains(employeeMstInstance?.employeeId)}" >
	    			
		    			<tr class="selected-row">
		    			
		    				<td><div>${fieldValue(bean: employeeMstInstance, field: "employeeId")}</div></td>
						
							<td><div>${fieldValue(bean: employeeMstInstance, field: "firstName")}</div></td>
							
							<td><div >${fieldValue(bean: employeeMstInstance, field: "lastName")}</div></td>
						</tr>
						
	    			</g:if>
	    			
	    			<g:else>
	    			
					<tr >			
							
						<td><div style="cursor: pointer;" onclick="setEmpCode('${employeeMstInstance?.employeeId}','${employeeMstInstance?.firstName}','${employeeMstInstance?.lastName}')" >${fieldValue(bean: employeeMstInstance, field: "employeeId")}</div></td>
					
						<td><div style="cursor: pointer;" onclick="setEmpCode('${employeeMstInstance?.employeeId}','${employeeMstInstance?.firstName}','${employeeMstInstance?.lastName}')" >${fieldValue(bean: employeeMstInstance, field: "firstName")}</div></td>
						
						<td><div style="cursor: pointer;" onclick="setEmpCode('${employeeMstInstance?.employeeId}','${employeeMstInstance?.firstName}','${employeeMstInstance?.lastName}')" >${fieldValue(bean: employeeMstInstance, field: "lastName")}</div></td>
					
					</tr>
						
					</g:else>
								
				</g:each>
			</g:if>
			<g:else>
	
				<tr><td colspan="3" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
	
			</g:else>
			
		</table>
</div>

	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getEmployeeMstList', 'searchController': 'employee', 'divIdToUpdate': 'empCodelistGridDiv', 'searchParams': 'true']" />			
	<div class="pagination margintopbottom15">
			
		<g:paginate total="${empMstListCount ?: 0}" maxsteps="5" />
				
	</div>			

 

