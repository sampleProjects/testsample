<div class="clearfix"></div><hr>

<g:render template="/common/paginationCombo" model="['totalRecord':laborActCodeListCount, 'searchAction': 'getLaborActCodeList', 'searchController': 'department', 'divIdToUpdate': 'laborActivityCodeListGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive height-scroll">	

  		<table class="table table-striped">	
			<thead>
				<tr>
					<th>Code</th>
					
					<th>Labor Activity Group</th>
				</tr>
			</thead>
			<g:if test="${laborActCodeInstanceList?.size() > 0}">
	    		<g:each in="${laborActCodeInstanceList}" status="i" var="deptLbrActCodeInstance">
					<tr>			
						<td><div style="cursor: pointer;" onclick="setLaborActCode('${deptLbrActCodeInstance?.id}','${deptLbrActCodeInstance?.code}')" >${fieldValue(bean: deptLbrActCodeInstance, field: "code")}</div></td>
						
						<td><div>${fieldValue(bean: deptLbrActCodeInstance?.laborActivityGroup, field: "code")}</div></td>
						
					</tr>
				</g:each>
			</g:if>
			<g:else>
				<tr><td colspan="3" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
			</g:else>
		</table>
</div>
	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getLaborActCodeList', 'searchController': 'department', 'divIdToUpdate': 'laborActivityCodeListGridDiv', 'searchParams': 'true']" />			
	<div class="pagination margintopbottom15">
		<g:paginate total="${laborActCodeListCount ?: 0}" maxsteps="5" />
	</div>