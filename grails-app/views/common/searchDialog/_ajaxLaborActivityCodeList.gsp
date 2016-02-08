<div class="clearfix"></div><hr> 
<g:render template="/common/paginationCombo" model="['totalRecord':laborActCodeInstanceCount, 'searchAction': 'getLaborActivityCodeList', 'searchController': 'report', 'divIdToUpdate': 'laborActCodeListGridDiv']"></g:render>
	<div id="ajaxListView" class="table-responsive height-scroll">	
  		<table class="table table-striped">	
			<thead>
				<tr>
					<th><g:message code="etech.report.coe.code.label" default="Code" /></th>
					<th><g:message code="etech.report.coe.description.label" default="Description" /></th>
				</tr>
			</thead>
			<g:if test="${laborActCodeInstanceList?.size() > 0}">
	    		<g:each in="${laborActCodeInstanceList}" status="i" var="laborActCodeInstance">
					<tr>			
						<td><div style="cursor: pointer;" onclick="setLaborActCodeCode('${laborActCodeInstance?.id}','${laborActCodeInstance?.code}')" >${fieldValue(bean: laborActCodeInstance, field: "code")}</div></td>
						<td>${fieldValue(bean: laborActCodeInstance, field: "description")}</td>
					</tr>
				</g:each>
			</g:if>
			<g:else>
				<tr><td colspan="3" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
			</g:else>
		</table>
	</div>
	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getLaborActivityCodeList', 'searchController': 'report', 'divIdToUpdate': 'laborActCodeListGridDiv', 'searchParams': 'true']" />			
	<div class="pagination margintopbottom15">
		<g:paginate total="${laborActCodeInstanceCount ?: 0}" maxsteps="5" />
	</div>