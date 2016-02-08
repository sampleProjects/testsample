<g:render template="/common/returnLink" model="['returnAction': 'getJobHistory', 'returnController': 'customQuartz', 'messageCode':'etech.customQuartz.return.screen', 'defaultMessage': 'Return to Job History', 'linkParam': [className:jobRegisterInstance?.className?:'org.solcorp.etech.ImportCustomersJob']]"></g:render>
<div class="clearfix"></div>
<g:if test="${params?.detailType.equals('cumulative')}">
	<g:link action="downloadJobCustomerExcelSheet" id="${params?.id}" class="fa fa-file-excel-o fa-2x" style="position: absolute;margin-top: 20px;" data-toggle="tooltip" data-original-title="Download to Excel" data-placement="left"></g:link>
	<g:link action="downloadJobCustomerExcelSheet" id="${params?.id}" class=""style="position: absolute;margin-left:30px;margin-top: 30px;" data-toggle="tooltip" data-original-title="Download to Excel" data-placement="right">Full Status Export</g:link>
</g:if>
<g:render template="/common/paginationCombo" model="['totalRecord':importCustomerJobLineRecordCount, 'searchAction': 'getJobCustomerList', 'searchController': 'customQuartz', 'divIdToUpdate': 'jobSummaryListGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive">
	

		<div class="table-responsive no-padding">
			<table class="table table-hover">
				<thead>
					<tr>
						<th nowrap><div>HH Customer Id</div></th>
						<th nowrap><div>Customer Name</div></th>
						<th nowrap><div>Status</div></th>
						<th nowrap><div>HH Master Customer Id</div></th>
						<th nowrap><div>Customer Status</div></th>
						<th nowrap><div>Batch #</div></th>
						<th nowrap><div>Date Added</div></th>
						<th nowrap><div>Last Maint Dttm</div></th>
						<th nowrap><div>Dttm Stamp</div></th>
						<th nowrap><div>Descr Short</div></th>
						<th nowrap><div>Customer Id</div></th>
						<th nowrap><div>Message</div></th>
					</tr>
				</thead>
				<tbody>
					<g:if test="${importCustomerJobLineRecordList?.size() > 0}">
						<g:each in="${importCustomerJobLineRecordList}" status="i" var="importCustomerJobLineRecordInstance">
							<tr>
								<td nowrap>${importCustomerJobLineRecordInstance?.hhCustId}</td>
								<td>${importCustomerJobLineRecordInstance?.name1}</td>
								<td nowrap>${importCustomerJobLineRecordInstance?.status}</td>
								<td>${importCustomerJobLineRecordInstance?.hhMasterCustId}</td>
								<td>${importCustomerJobLineRecordInstance?.custStatus}</td>
								<td nowrap>${importCustomerJobLineRecordInstance?.jobRegister?.id}</td>
								<td nowrap><g:formatDate format="yyyy-MM-dd" date="${importCustomerJobLineRecordInstance?.dateAdded}"/></td>
								<td nowrap><g:formatDate format="yyyy-MM-dd" date="${importCustomerJobLineRecordInstance?.lastMaintDttm}"/></td>
								<td nowrap><g:formatDate format="yyyy-MM-dd" date="${importCustomerJobLineRecordInstance?.dttm_stamp}"/></td>
								<td nowrap>${importCustomerJobLineRecordInstance?.descrshort}</td>
								<td nowrap>${importCustomerJobLineRecordInstance?.customerId}</td>
								<td nowrap>${importCustomerJobLineRecordInstance?.message}</td>
							</tr>
						</g:each>
					</g:if>
					<g:else>
						<tr><td colspan="12" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
					</g:else>
				</tbody>
			</table>
		</div>
		
</div>


<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getJobCustomerList', 'searchController': 'customQuartz', 'divIdToUpdate': 'jobSummaryListGridDiv', 'searchParams': 'true']" />			

<div class="pagination">
	<g:paginate total="${importCustomerJobLineRecordCount ?: 0}"  params="${params}" />
</div>	

<div class="clearfix"></div>

 <g:render template="/common/returnLink" model="['returnAction': 'getJobHistory', 'returnController': 'customQuartz', 'messageCode':'etech.customQuartz.return.screen', 'defaultMessage': 'Return to Job History', 'linkParam': [className:jobRegisterInstance?.className?:'org.solcorp.etech.ImportCustomersJob']]"></g:render>
 