<div class="clearfix"></div><hr>
 
<g:render template="/common/paginationCombo" model="['totalRecord':industryInstanceCount, 'searchAction': 'getIndustryList', 'searchController': 'customer', 'divIdToUpdate': 'industryListGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive height-scroll">	

  		<table class="table table-striped">	
			<thead>
				<tr>
	
					<th><g:message code="industry.code.label" default="Code" /></th>
	
					<th><g:message code="industry.name.label" default="Name" /></th>
					
				</tr>
			</thead>
			<g:if test="${industryInstanceList?.size() > 0}">
	    		<g:each in="${industryInstanceList}" status="i" var="industryInstance">
					<tr>			
						<td><div style="cursor: pointer;" onclick="setIndustryCode('${industryInstance?.id}','${industryInstance?.code}')" >${fieldValue(bean: industryInstance, field: "code")}</div></td>
							
						<td>${fieldValue(bean: industryInstance, field: "name")}</td>
					</tr>
				</g:each>
			</g:if>
			<g:else>
	
				<tr><td colspan="3" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
	
			</g:else>
			
		</table>
</div>

	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getIndustryList', 'searchController': 'customer', 'divIdToUpdate': 'industryListGridDiv', 'searchParams': 'true']" />			
	<div class="pagination margintopbottom15">
			
		<g:paginate total="${industryInstanceCount ?: 0}" maxsteps="5" />
				
	</div>