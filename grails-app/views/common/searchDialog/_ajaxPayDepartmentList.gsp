<div class="clearfix"></div><hr>
<g:render template="/common/paginationCombo" model="['totalRecord':departmentListCount, 'searchAction': 'getPayDepartmentList', 'searchController': 'report', 'divIdToUpdate': 'payDeptListGridDiv']"></g:render>
	<div id="ajaxListView" class="table-responsive height-scroll">	
  		<table class="table table-striped">	
			<thead>
				<tr>
					<th>Department Code</th>
				</tr>
			</thead>
			<g:if test="${departmentList?.size() > 0}">
	    		<g:each in="${departmentList}" status="i" var="departmentInstance">
					<tr>			
						<td><div style="cursor: pointer;" onclick="setPayDepartmentCode('${departmentInstance?.id}','${departmentInstance?.code}')" >${fieldValue(bean: departmentInstance, field: "code")}</div></td>
					</tr>
				</g:each>
			</g:if>
			<g:else>
				<tr><td colspan="3" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
			</g:else>
		</table>
</div>
	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getPayDepartmentList', 'searchController': 'report', 'divIdToUpdate': 'payDeptListGridDiv', 'searchParams': 'true']" />			
	<div class="pagination margintopbottom15">
		<g:paginate total="${departmentListCount ?: 0}" maxsteps="5" />
	</div>