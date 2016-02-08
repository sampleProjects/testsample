<g:render template="/common/returnLink" model="['returnAction': 'getJobHistory', 'returnController': 'customQuartz', 'messageCode':'etech.customQuartz.return.screen', 'defaultMessage': 'Return to Job History', 'linkParam': [className:jobRegisterInstance?.className?:'org.solcorp.etech.ImportEmployeesJob']]"></g:render>
<div class="clearfix"></div>
<g:if test="${params?.detailType.equals('cumulative')}">
	<g:link action="downloadJobEmployeeExcelSheet" id="${params?.id}" class="fa fa-file-excel-o fa-2x" style="position: absolute;margin-top: 20px;" data-toggle="tooltip" data-original-title="Download to Excel" data-placement="left"></g:link>
	<g:link action="downloadJobEmployeeExcelSheet" id="${params?.id}" class=""style="position: absolute;margin-left:30px;margin-top: 30px;" data-toggle="tooltip" data-original-title="Download to Excel" data-placement="right">Full Status Export</g:link>
</g:if>
<g:render template="/common/paginationCombo" model="['totalRecord':importEmployeeJobLineRecordCount, 'searchAction': 'getJobEmployeeList', 'searchController': 'customQuartz', 'divIdToUpdate': 'jobSummaryListGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive">
	 <div class="table-responsive no-padding " >
			<table id="myTable" class="table table-hover scroll" >
				<thead>
					<tr>
						<th nowrap >HH Employee Id</th>
						<th nowrap >First Name</th>
						<th nowrap>Last Name</th>
						<th nowrap>Status</th>
						<th nowrap >Batch #</th>
						<th nowrap>Type</th>
						<th nowrap>Department</th>
						<th nowrap>Last Hire Date</th>
						<th nowrap>Create Date</th>
						<th nowrap>Modify Date</th>
						<th nowrap>Import Status</th>
						<th nowrap>Import Status Date</th>
						<th nowrap>Employee Id</th>
						<th nowrap>Message</th>
					</tr>
				</thead>
				<tbody>
					<g:if test="${importEmployeeJobLineRecordList?.size() > 0}">
						<g:each in="${importEmployeeJobLineRecordList}" status="i" var="importEmployeeJobLineRecordInstance">
							<tr>
								<td nowrap >${importEmployeeJobLineRecordInstance?.hhEmployeeId}</td>
								<td nowrap >${importEmployeeJobLineRecordInstance?.firstName}</td>
								<td nowrap>${importEmployeeJobLineRecordInstance?.lastName}</td>
								<td nowrap>${importEmployeeJobLineRecordInstance?.status}</td>
								<td nowrap >${importEmployeeJobLineRecordInstance?.jobRegister?.id}</td>
								<td nowrap>${importEmployeeJobLineRecordInstance?.type}</td>
								<td nowrap>${importEmployeeJobLineRecordInstance?.department}</td>
								<td nowrap><g:formatDate format="yyyy-MM-dd" date="${importEmployeeJobLineRecordInstance?.lastHireDate}"/></td>
								<td nowrap><g:formatDate format="yyyy-MM-dd" date="${importEmployeeJobLineRecordInstance?.createDate}"/></td>
								<td nowrap><g:formatDate format="yyyy-MM-dd" date="${importEmployeeJobLineRecordInstance?.modifyDate}"/></td>
								<td nowrap>${importEmployeeJobLineRecordInstance?.importStatus}</td>
								<td nowrap><g:formatDate format="yyyy-MM-dd" date="${importEmployeeJobLineRecordInstance?.importStatusDate}"/></td>
								<td nowrap>${importEmployeeJobLineRecordInstance?.employeeId}</td>
								<td nowrap>${importEmployeeJobLineRecordInstance?.message}</td>
							</tr>
						</g:each>
					</g:if>
					<g:else>
						<tr><td colspan="14" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
					</g:else>
				</tbody>
			</table>
		</div>
		
</div>


<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getJobEmployeeList', 'searchController': 'customQuartz', 'divIdToUpdate': 'jobSummaryListGridDiv', 'searchParams': 'true']" />			

<div class="pagination">
	<g:paginate total="${importEmployeeJobLineRecordCount ?: 0}"  params="${params}" />
</div>	

<div class="clearfix"></div>

 <g:render template="/common/returnLink" model="['returnAction': 'getJobHistory', 'returnController': 'customQuartz', 'messageCode':'etech.customQuartz.return.screen', 'defaultMessage': 'Return to Job History', 'linkParam': [className:jobRegisterInstance?.className?:'org.solcorp.etech.ImportEmployeesJob']]"></g:render>
 <script>
 var lastScrollLeft=0;
 $(document).ready(function() {
		/* zebra stripe the tables (not necessary for scrolling) */
     var tbl = $("table.scroll").attr('id');
     console.log("ID:::"+tbl)
		
		/* make the table scrollable with a fixed header */
     $("table.scroll").createScrollableTable({
         height: '400px'
     });
		$("#"+tbl+'_body_wrap').scroll(function(){
			var documentScrollLeft = $("#"+tbl+'_body_wrap').scrollLeft();
			if (lastScrollLeft != documentScrollLeft) {
				console.log('scroll x');
				$("#"+tbl+'_head_wrap').scrollLeft(documentScrollLeft)
				lastScrollLeft = documentScrollLeft;
			}
		})

 });
 </script>