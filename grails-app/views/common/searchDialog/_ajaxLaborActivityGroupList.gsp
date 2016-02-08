<div class="clearfix"></div><hr> 
<g:render template="/common/paginationCombo" model="['totalRecord':laborActGroupInstanceCount, 'searchAction': 'getLaborActivityGroupList', 'searchController': 'report', 'divIdToUpdate': 'laborActGroupListGridDiv']"></g:render>
	<div id="ajaxListView" class="table-responsive height-scroll">	
  		<table class="table table-striped">	
			<thead>
				<tr>
					<th><g:message code="etech.report.coe.code.label" default="Code" /></th>
					<th><g:message code="etech.report.coe.description.label" default="Description" /></th>
				</tr>
			</thead>
			<g:if test="${laborActGroupInstanceList?.size() > 0}">
	    		<g:each in="${laborActGroupInstanceList}" status="i" var="laborActGroupInstance">
					<tr>			
						<td><div style="cursor: pointer;" onclick="setLaborActGroupCode('${laborActGroupInstance?.id}','${laborActGroupInstance?.code}')" >${fieldValue(bean: laborActGroupInstance, field: "code")}</div></td>
						<td>${fieldValue(bean: laborActGroupInstance, field: "description")}</td>
					</tr>
				</g:each>
			</g:if>
			<g:else>
				<tr><td colspan="3" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
			</g:else>
		</table>
	</div>
	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getLaborActivityGroupList', 'searchController': 'report', 'divIdToUpdate': 'laborActGroupListGridDiv', 'searchParams': 'true']" />			
	<div class="pagination margintopbottom15">
		<g:paginate total="${laborActGroupInstanceCount ?: 0}" maxsteps="5" />
	</div>