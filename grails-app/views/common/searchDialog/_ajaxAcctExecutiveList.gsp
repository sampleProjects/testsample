<div class="clearfix"></div><hr>
 
<g:render template="/common/paginationCombo" model="['totalRecord':acctExecutiveInstanceCount, 'searchAction': 'getAcctExecutiveList', 'searchController': 'report', 'divIdToUpdate': 'acctExecutiveListGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive height-scroll">	

  		<table class="table table-striped">	
			<thead>
				<tr>
	
					<th><g:message code="etech.report.acctExecutive.code.label" default="Code" /></th>
	
					<th><g:message code="etech.report.acctExecutive.name.label" default="Name" /></th>
					
				</tr>
			</thead>
			<g:if test="${acctExecutiveInstanceList?.size() > 0}">
	    		<g:each in="${acctExecutiveInstanceList}" status="i" var="acctExecutiveInstance">
					<tr>			
						<td><div style="cursor: pointer;" onclick="setAcctExecutiveCode('${acctExecutiveInstance?.id}','${acctExecutiveInstance?.code}','${acctExecutiveInstance?.getEmployeeName()}')" >${fieldValue(bean: acctExecutiveInstance, field: "code")}</div></td>
							
						<td>${acctExecutiveInstance?.getEmployeeName()}</td>
						
					</tr>
				</g:each>
			</g:if>
			<g:else>
	
				<tr><td colspan="3" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
	
			</g:else>
			
		</table>
</div>

	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getAcctExecutiveList', 'searchController': 'report', 'divIdToUpdate': 'acctExecutiveListGridDiv', 'searchParams': 'true']" />			
	<div class="pagination margintopbottom15">
			
		<g:paginate total="${acctExecutiveInstanceCount ?: 0}" maxsteps="5" />
				
	</div>