<div class="clearfix"></div><hr>
 
<g:render template="/common/paginationCombo" model="['totalRecord':managerInstanceListCount, 'searchAction': 'getProjectManagerList', 'searchController': 'report', 'divIdToUpdate': 'projectManagerListGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive height-scroll">	

  		<table class="table table-striped">	
			<thead>
				<tr>
	
					<th><g:message code="etech.report.projectManager.code.label" default="Code" /></th>
	
					<th><g:message code="etech.report.projectManager.last.name.label" default="Last Name" /></th>
					
					<th><g:message code="etech.report.projectManager.first.name.label" default="First Name" /></th>
					
					<th><g:message code="etech.report.projectManager.pay.dept.label" default="Pay Dept" /></th>
					
				</tr>
			</thead>
			<g:if test="${managerInstanceList?.size() > 0}">
	    		<g:each in="${managerInstanceList}" status="i" var="managerInstance">
					<tr>			
						<td><div style="cursor: pointer;" onclick="setProjectManagerCode('${managerInstance?.employee?.code}')" >${managerInstance?.employee?.code}</div></td>
							
						<td>${managerInstance?.employee?.lastName}</td>
						
						<td>${managerInstance?.employee?.firstName}</td>
						
						<td>${managerInstance?.employee?.payDept?.code}</td>
						
					</tr>
				</g:each>
			</g:if>
			<g:else>
	
				<tr><td colspan="4" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
	
			</g:else>
			
		</table>
</div>

	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getProjectManagerList', 'searchController': 'report', 'divIdToUpdate': 'projectManagerListGridDiv', 'searchParams': 'true']" />			
	<div class="pagination margintopbottom15">
			
		<g:paginate total="${managerInstanceListCount ?: 0}" maxsteps="5" />
				
	</div>