<div class="clearfix"></div><hr>

<g:render template="/common/paginationCombo" model="['totalRecord':departmentListCount, 'searchAction': 'getDepartmentList', 'searchController': 'employee', 'divIdToUpdate': 'deptListGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive height-scroll">	

  		<table class="table table-striped">	
			<thead>
				<tr>
					<th>Department Code</th>
					<th>Description</th>
				</tr>
			</thead>
			<g:if test="${departmentList?.size() > 0}">
	    		<g:each in="${departmentList}" status="i" var="departmentInstance">
					<tr>			
						<td><div style="cursor: pointer;" onclick="setDeptCode('${departmentInstance?.id}','${departmentInstance?.code}')" >${fieldValue(bean: departmentInstance, field: "code")}</div></td>
						<td>${fieldValue(bean: departmentInstance, field: "description")}</td>
					</tr>
				</g:each>
			</g:if>
			<g:else>
				<tr><td colspan="3" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
			</g:else>
		</table>
</div>
	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getDepartmentList', 'searchController': 'employee', 'divIdToUpdate': 'deptListGridDiv', 'searchParams': 'true']" />			
	<div class="pagination margintopbottom15">
		<g:paginate total="${departmentListCount ?: 0}" maxsteps="5" />
	</div>