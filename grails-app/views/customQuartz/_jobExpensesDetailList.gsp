<g:render template="/common/returnLink" model="['returnAction': 'getJobHistory', 'returnController': 'customQuartz', 'messageCode':'etech.customQuartz.return.screen', 'defaultMessage': 'Return to Job History', 'linkParam': [className:jobRegisterInstance?.className?:'org.solcorp.etech.ImportExpensesJob']]"></g:render>
<div class="clearfix"></div>
<g:if test="${params?.detailType.equals('cumulative')}">
	<g:link action="downloadJobExpensesExcelSheet" id="${params?.id}" class="fa fa-file-excel-o fa-2x" style="position: absolute;margin-top: 10px;" data-toggle="tooltip" data-original-title="Download to Excel" data-placement="left"></g:link>
	<g:link action="downloadJobExpensesExcelSheet" id="${params?.id}" class=""style="position: absolute;margin-left:30px;margin-top: 20px;" data-toggle="tooltip" data-original-title="Download to Excel" data-placement="right">Full Status Export</g:link>
</g:if>
<g:render template="/common/paginationCombo" model="['totalRecord':importExpenseJobLineRecordCount, 'searchAction': 'getJobExpensesList', 'searchController': 'customQuartz', 'divIdToUpdate': 'jobSummaryListGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive">
	

		<div class="table-responsive no-padding">
			<table class="table table-hover">
				<thead>
					<tr>
						<th nowrap>Project Id</th>
						<th nowrap>Expense Id</th>
						<th nowrap>Status</th>
						<th nowrap>Batch #</th>
						<th nowrap>Business Unit</th> 
						<th nowrap>Journal Id</th>
						<th nowrap>Journal Date</th>
						<th nowrap>Unpost Seq</th>
						<th nowrap>Financial Year</th>
						<th nowrap>Accounting Period</th>
						<th nowrap>Source</th>
						<th nowrap>Journal Hdr Status</th>
						<th nowrap>Posted Date</th>
						<th nowrap>Description</th>
						<th nowrap>Currency Code</th>
						<th nowrap>Journal Create Dttm</th>
						<th nowrap>Journal Date Orig</th>
						<th nowrap>Journal Line</th>
						<th nowrap>Account</th>
						<th nowrap>Department Id</th>
						<th nowrap>Operating Unit</th>
						<th nowrap>Product</th>
						<th nowrap>Monetary Amount</th>
						<th nowrap>Foreign Currency</th>
						<th nowrap>Foreign Amount</th>
						<th nowrap>Voucher Id</th>
						<th nowrap>Invoice Id</th>
						<th nowrap>Vendor Id</th>
						<th nowrap>Name</th>
						<th nowrap>Dttm Stamp</th>
						<th nowrap>Descr Short</th>
						<th nowrap>Message</th>
					</tr>
				</thead>
				<tbody>
					<g:if test="${importExpenseJobLineRecordList?.size() > 0}">
						<g:each in="${importExpenseJobLineRecordList}" status="i" var="importExpenseJobLineRecordInstance">
							<tr>
								<td nowrap>${importExpenseJobLineRecordInstance?.hhProjectId}</td>
								<td nowrap>${importExpenseJobLineRecordInstance?.expenseId}</td>
								<td nowrap>${importExpenseJobLineRecordInstance?.status}</td>
								<td nowrap>${importExpenseJobLineRecordInstance?.jobRegister?.id}</td>
								<td>${importExpenseJobLineRecordInstance?.businessUnit}</td>
								<td>${importExpenseJobLineRecordInstance?.journalId}</td>
								<td nowrap><g:formatDate format="yyyy-MM-dd" date="${importExpenseJobLineRecordInstance?.journalDate}"/></td>
								<td nowrap>${importExpenseJobLineRecordInstance?.unpostSeq}</td>
								<td nowrap>${importExpenseJobLineRecordInstance?.fiscalYear}</td>
								<td nowrap>${importExpenseJobLineRecordInstance?.accountingPeriod}</td>
								<td nowrap>${importExpenseJobLineRecordInstance?.source}</td>
								<td nowrap>${importExpenseJobLineRecordInstance?.jrnlHdrStatus}</td>
								<td nowrap><g:formatDate format="yyyy-MM-dd" date="${importExpenseJobLineRecordInstance?.postedDate}"/></td>
								<td nowrap>${importExpenseJobLineRecordInstance?.descr}</td>
								<td nowrap>${importExpenseJobLineRecordInstance?.currencyCd}</td>
								<td nowrap><g:formatDate format="yyyy-MM-dd" date="${importExpenseJobLineRecordInstance?.jrnlCreateDttm}"/></td>
								<td nowrap><g:formatDate format="yyyy-MM-dd" date="${importExpenseJobLineRecordInstance?.journalDateOrig}"/></td>
								<td>${importExpenseJobLineRecordInstance?.journalLine}</td>
								<td>${importExpenseJobLineRecordInstance?.account}</td>
								<td nowrap>${importExpenseJobLineRecordInstance?.deptid}</td>
								<td nowrap>${importExpenseJobLineRecordInstance?.operatingUnit}</td>
								<td nowrap>${importExpenseJobLineRecordInstance?.product}</td>
								<td nowrap>${importExpenseJobLineRecordInstance?.monetaryAmount}</td>
								<td nowrap>${importExpenseJobLineRecordInstance?.foreignCurrency}</td>
								<td nowrap>${importExpenseJobLineRecordInstance?.foreignAmount}</td>
								<td nowrap>${importExpenseJobLineRecordInstance?.voucherId}</td>
								<td nowrap>${importExpenseJobLineRecordInstance?.invoiceId}</td>
								<td nowrap>${importExpenseJobLineRecordInstance?.vendorId}</td>
								<td nowrap>${importExpenseJobLineRecordInstance?.name1}</td>
								<td nowrap><g:formatDate format="yyyy-MM-dd" date="${importExpenseJobLineRecordInstance?.dttm_stamp}"/></td>
								<td nowrap>${importExpenseJobLineRecordInstance?.descrshort}</td>
								<td nowrap>${importExpenseJobLineRecordInstance?.message}</td>
							</tr>
						</g:each>
					</g:if>
					<g:else>
						<tr><td colspan="32" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
					</g:else>
				</tbody>
			</table>
		</div>
		
</div>


<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getJobExpensesList', 'searchController': 'customQuartz', 'divIdToUpdate': 'jobSummaryListGridDiv', 'searchParams': 'true']" />			

<div class="pagination">
	<g:paginate total="${importExpenseJobLineRecordCount ?: 0}"  params="${params}" />
</div>	

<div class="clearfix"></div>

 <g:render template="/common/returnLink" model="['returnAction': 'getJobHistory', 'returnController': 'customQuartz', 'messageCode':'etech.customQuartz.return.screen', 'defaultMessage': 'Return to Job History', 'linkParam': [className:jobRegisterInstance?.className?:'org.solcorp.etech.ImportExpensesJob']]"></g:render>