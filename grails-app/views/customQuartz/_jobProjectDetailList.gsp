<g:render template="/common/returnLink" model="['returnAction': 'getJobHistory', 'returnController': 'customQuartz', 'messageCode':'etech.customQuartz.return.screen', 'defaultMessage': 'Return to Job History', 'linkParam': [className:jobRegisterInstance?.className?:'org.solcorp.etech.ImportProjectsJob']]"></g:render>
<div class="clearfix"></div>
<g:if test="${params?.detailType.equals('cumulative')}">
	<g:link action="downloadJobProjectExcelSheet" id="${params?.id}" class="fa fa-file-excel-o fa-2x" style="position: absolute;margin-top: 20px;" data-toggle="tooltip" data-original-title="Download to Excel" data-placement="left"></g:link>
	<g:link action="downloadJobProjectExcelSheet" id="${params?.id}" class=""style="position: absolute;margin-left:30px;margin-top: 30px;" data-toggle="tooltip" data-original-title="Download to Excel" data-placement="right">Full Status Export</g:link>
</g:if>
<g:render template="/common/paginationCombo" model="['totalRecord':importProjectJobLineRecordCount, 'searchAction': 'getJobProjectList', 'searchController': 'customQuartz', 'divIdToUpdate': 'jobSummaryListGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive">
	

		<div class="table-responsive no-padding">
			<table class="table table-hover">
				<thead>
					<tr>
						<th nowrap>HH Project Id</th>
						<th nowrap>Project Name</th>
						<th nowrap>Status</th>
						<th nowrap>HH Customer Id</th>
						<th nowrap>Batch #</th>
						<th nowrap>Business Unit</th>
						<th nowrap>Description</th>
						<th nowrap>Customer ID</th>
						<th nowrap>Effective Status</th>
						<th nowrap>Start Date</th>
						<th nowrap>End Date</th>
						<th nowrap>Sales Person</th>
						<th nowrap>Name 2</th>
						<th nowrap>Descr Short</th>
						<th nowrap>Dttm Stamp</th>
						<th nowrap>Project Id</th>
						<th nowrap>Message</th>
					</tr>
				</thead>
				<tbody>
					<g:if test="${importProjectJobLineRecordList?.size() > 0}">
						<g:each in="${importProjectJobLineRecordList}" status="i" var="importProjectJobLineRecordInstance">
							<tr>
								<td nowrap>${importProjectJobLineRecordInstance?.hhProjectId}</td>
								<td>${importProjectJobLineRecordInstance?.name1}</td>
								<td nowrap>${importProjectJobLineRecordInstance?.status}</td>
								<td>${importProjectJobLineRecordInstance?.hhCustId}</td>
								<td nowrap>${importProjectJobLineRecordInstance?.jobRegister?.id}</td>
								<td>${importProjectJobLineRecordInstance?.businessUnit}</td>
								<td>${importProjectJobLineRecordInstance?.descr}</td>
								<td>${importProjectJobLineRecordInstance?.custId}</td>
								<td>${importProjectJobLineRecordInstance?.effStatus}</td>
								<td nowrap><g:formatDate format="yyyy-MM-dd" date="${importProjectJobLineRecordInstance?.startDt}"/></td>
								<td nowrap><g:formatDate format="yyyy-MM-dd" date="${importProjectJobLineRecordInstance?.endDt}"/></td>
								<td nowrap>${importProjectJobLineRecordInstance?.salesPerson}</td>
								<td nowrap>${importProjectJobLineRecordInstance?.name2}</td>
								<td nowrap>${importProjectJobLineRecordInstance?.descrshort}</td>
								<td nowrap><g:formatDate format="yyyy-MM-dd" date="${importProjectJobLineRecordInstance?.dttmStamp}"/></td>
								<td nowrap>${importProjectJobLineRecordInstance?.projectId}</td>
								<td nowrap>${importProjectJobLineRecordInstance?.message}</td>
							</tr>
						</g:each>
					</g:if>
					<g:else>
						<tr><td colspan="17" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
					</g:else>
				</tbody>
			</table>
		</div>
		
</div>


<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getJobProjectList', 'searchController': 'customQuartz', 'divIdToUpdate': 'jobSummaryListGridDiv', 'searchParams': 'true']" />			

<div class="pagination">
	<g:paginate total="${importProjectJobLineRecordCount ?: 0}"  params="${params}" />
</div>	

<div class="clearfix"></div>

 <g:render template="/common/returnLink" model="['returnAction': 'getJobHistory', 'returnController': 'customQuartz', 'messageCode':'etech.customQuartz.return.screen', 'defaultMessage': 'Return to Job History', 'linkParam': [className:jobRegisterInstance?.className?:'org.solcorp.etech.ImportProjectsJob']]"></g:render>