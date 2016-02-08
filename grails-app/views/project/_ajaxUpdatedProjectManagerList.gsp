<div class="clearfix"></div><hr>
 
<g:render template="/common/paginationCombo" model="['totalRecord':employeeInstanceCount, 'searchAction': 'getProjectManagerList', 'searchController': 'project', 'divIdToUpdate': 'projectManagerListGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive height-scroll">	

  		<table class="table table-striped">	
			<thead>
				<tr>
	
					<th><g:message code="etech.report.acctExecutive.code.label" default="Code" /></th>
	
					<th><g:message code="etech.report.acctExecutive.name.label" default="Name" /></th>
					
				</tr>
			</thead>
			<g:if test="${employeeInstanceList?.size() > 0}">
	    		<g:each in="${employeeInstanceList}" status="i" var="employeeInstance">
					<tr>			
						<td><div class="text-underline" onclick="projectMangerSetInSession('${employeeInstance?.id}','${params?.projectId}')" >${employeeInstance?.code}</div></td>
							
						<td>${employeeInstance?.getEmployeeName()}</td>
						
					</tr>
				</g:each>
			</g:if>
			<g:else>
	
				<tr><td colspan="3" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
	
			</g:else>
			
		</table>
</div>

	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'employeeInstanceCount', 'searchController': 'project', 'divIdToUpdate': 'projectManagerListGridDiv', 'searchParams': 'true']" />			
	<div class="pagination margintopbottom15">
			
		<g:paginate total="${employeeInstanceCount ?: 0}" maxsteps="5" />
				
	</div>