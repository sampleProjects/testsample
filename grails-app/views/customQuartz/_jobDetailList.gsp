<div class="clearfix"></div>
<g:if test="${params?.detailType.equals('cumulative')}">
	<g:link action="downloadExcelSheet" id="${params?.id}" class="fa fa-file-excel-o fa-2x" style="position: absolute;margin-top: 10px;" data-toggle="tooltip" data-original-title="Download to Excel" data-placement="left"></g:link>
	<g:link action="downloadExcelSheet" id="${params?.id}" class="" style="position: absolute;margin-left:30px;margin-top: 20px;" data-toggle="tooltip" data-original-title="Download to Excel" data-placement="right">Full Status Export</g:link>
</g:if>
<g:render template="/common/paginationCombo" model="['totalRecord':importLaborTransactionJobLineRecordCount, 'searchAction': 'getJobSummaryList', 'searchController': 'customQuartz', 'divIdToUpdate': 'jobSummaryListGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive height-scroll900">
	

		<div class="table-responsive no-padding">
			<table class="table table-hover">
				<thead>
					<tr>
						<th nowrap>Source Txn Id</th> 
						<th nowrap>Source Txn Date</th>
						<th nowrap>Txn Id</th> 
						<th nowrap>Status</th> 
						<th nowrap>Employee Id</th> 
						<th nowrap>Project Id</th>  
						<th nowrap>Project Name</th>
						<th nowrap>Project Unit</th> 
						<th nowrap>Customer Id</th> 
						<th nowrap>Hours</th>
						<th nowrap>Dollar Amount</th>
					</tr>
				</thead>
				<tbody>
					<g:if test="${importLaborTransactionJobLineRecordList?.size() > 0}">
						<g:each in="${importLaborTransactionJobLineRecordList}" status="i" var="importLaborTransactionJobLineRecordInstance">
							<tr>
								<td>${importLaborTransactionJobLineRecordInstance?.sourceTransactionId}</td>
								<td><g:formatDate format="yyyy-MM-dd" date="${new Date().parse("yyyy-MM-dd HH:mm:ss.S",importLaborTransactionJobLineRecordInstance?.importStatusDate)}"/></td>
								<td>${importLaborTransactionJobLineRecordInstance?.transactionId}</td>
								<td nowrap>${importLaborTransactionJobLineRecordInstance?.status}</td>
								<td>${importLaborTransactionJobLineRecordInstance?.employeeId}</td>
								<td>${importLaborTransactionJobLineRecordInstance?.projectId}</td>
								<td>${importLaborTransactionJobLineRecordInstance?.projectFullName}</td>
								<td>${importLaborTransactionJobLineRecordInstance?.projectUnit}</td>
								<td>${importLaborTransactionJobLineRecordInstance?.projectCustRevId}</td>
								<td class="text-right">${importLaborTransactionJobLineRecordInstance?.hours}</td>
								<td class="text-right">${importLaborTransactionJobLineRecordInstance?.dollarAmount}</td>
							</tr>
						</g:each>
					</g:if>
					<g:else>
						<tr><td colspan="11" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
					</g:else>
				</tbody>
			</table>
		</div>
		
</div>


<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getJobSummaryList', 'searchController': 'customQuartz', 'divIdToUpdate': 'jobSummaryListGridDiv', 'searchParams': 'true']" />			

<div class="pagination">
	<g:paginate total="${importLaborTransactionJobLineRecordCount ?: 0}"  params="${params}" />
</div>	

<div class="clearfix"></div>

 <g:render template="/common/returnLink" model="['returnAction': 'getJobHistory', 'returnController': 'customQuartz', 'messageCode':'etech.customQuartz.return.screen', 'defaultMessage': 'Return to Job History', 'linkParam': [className: jobRegisterInstance?.className?:'org.solcorp.etech.ImportLaborTransactionsJob']]"></g:render>
