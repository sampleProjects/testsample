
<div class="col-xs-12" style="margin-top: 25px;">
	<div class="table-responsive">
		<table class="table table-hover">
			<thead>
				<tr>
					<th class="text-left">User</th>
					<th class="text-left" nowrap>Batch #</th>
					<g:if test="${jobName.equals('ImportLaborTransactionsJob') || jobName.equals('ImportExpensesJob') }">
						<th class="text-left">Data From</th>
						<th class="text-left">Data To</th>
					</g:if>
					<th class="text-left" nowrap>Started On</th>
					<th class="text-left" nowrap>Completed On</th>
					<g:if test="${params?.showSummary?.equals('true')}">
						<th class="text-left">Message</th>
						<th class="text-center">Summary</th>
						<th class="text-center">Trace</th>
					</g:if>
				</tr>
			</thead>
			<tbody>
				<g:if test="${result != null && result.jobHistoryList != null}">
					<g:each in="${result.jobHistoryList}" status="i" var="job">
								<g:if test="${job?.status?.toString()?.trim()?.equals('Fail')}">
                        			<g:set var="fontColor" value="red-text"/>
                        		</g:if>
                        		<g:elseif test="${job?.status?.toString()?.trim()?.equals('Running')}">
                        			<g:set var="fontColor" value="blue-text" />
                        		</g:elseif>
                        		<g:else>
                        			<g:set var="fontColor" value="black-text" />
                        		</g:else>
						<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
							<td class="text-left ${fontColor}">${job?.initiatedBy?.username?:'N/A'}
								<g:if test="${job?.initiatedBy?.employee}">
									(${job?.initiatedBy?.employee?.getEmployeeName()})
								</g:if>
							</td>
							<td class="text-left ${fontColor}">
								<g:if test="${jobName != null && jobName.equals('ImportExpensesJob')}">
									<g:link data-placement="right" data-original-title="Job Detail" data-toggle="tooltip" action="jobExpensesDetail" controller="customQuartz" id="${job.id}" params="[detailType: 'cumulative']" style="color:blue;text-decoration:underline">${job?.id?:'N/A'}</g:link>
								</g:if>
								<g:elseif test="${jobName != null && jobName.equals('ImportLaborTransactionsJob')}">
									<g:link data-placement="right" data-original-title="Job Detail" data-toggle="tooltip" action="jobDetail" controller="customQuartz" id="${job.id}" params="[detailType: 'cumulative']" style="color:blue;text-decoration:underline">${job?.id?:'N/A'}</g:link>
								</g:elseif>
								<g:elseif test="${jobName != null && jobName.equals('ImportProjectsJob')}">
									<g:link data-placement="right" data-original-title="Job Detail" data-toggle="tooltip" action="jobProjectDetail" controller="customQuartz" id="${job.id}" params="[detailType: 'cumulative']" style="color:blue;text-decoration:underline">${job?.id?:'N/A'}</g:link>
								</g:elseif>
								<g:elseif test="${jobName != null && jobName.equals('ImportCustomersJob')}">
									<g:link data-placement="right" data-original-title="Job Detail" data-toggle="tooltip" action="jobCustomerDetail" controller="customQuartz" id="${job.id}" params="[detailType: 'cumulative']" style="color:blue;text-decoration:underline">${job?.id?:'N/A'}</g:link>
								</g:elseif>
								<g:elseif test="${jobName != null && jobName.equals('ImportEmployeesJob')}">
									<g:link data-placement="right" data-original-title="Job Detail" data-toggle="tooltip" action="jobEmployeeDetail" controller="customQuartz" id="${job.id}" params="[detailType: 'cumulative']" style="color:blue;text-decoration:underline">${job?.id?:'N/A'}</g:link>
								</g:elseif>
								<g:else>
									${job?.id?:'N/A'}
								</g:else>
							</td>
							
							<g:if test="${jobName.equals('ImportLaborTransactionsJob') || jobName.equals('ImportExpensesJob')}">
								<td class="text-left ${fontColor}">
									<g:if test="${job?.dataRangeFrom}">
										<g:formatDate format="MM/dd/yyyy" date="${job?.dataRangeFrom}"/>
									</g:if>
									<g:else>
										N/A
									</g:else>
								</td>
								<td class="text-left ${fontColor}">
									<g:if test="${job?.dataRangeTo}">
										<g:formatDate format="MM/dd/yyyy" date="${job?.dataRangeTo}"/>
									</g:if>
									<g:else>
										N/A
									</g:else>
								</td>
							</g:if>
							
							<td class="text-left ${fontColor}">
                        		<g:if test="${job?.startDate}">
                        			<g:formatDate format="MM/dd/yyyy HH:mm:ss" date="${job?.startDate}"/>
                        		</g:if>
                        		<g:else>
                        			N/A
                        		</g:else>
                        	</td>
							<td class="text-left ${fontColor}">
                        		<g:if test="${job?.endDate}">
                        			<g:formatDate format="MM/dd/yyyy HH:mm:ss" date="${job?.endDate}"/>
                        		</g:if>
                        		<g:else>
                        			N/A
                        		</g:else>
                        	</td>
						
							<g:if test="${params?.showSummary?.equals('true')}">
								<td class="text-left ${fontColor}">
									<g:if test="${job?.hasMessage != null && job?.hasMessage == true}">
										${job?.jobRegisterSummary?.message?:'N/A'}
									</g:if>
									<g:else>
										N/A
									</g:else>
								</td>
								<td nowrap="nowrap" class="text-center ${fontColor}">
									<g:if test="${job?.hasSummary != null && job?.hasSummary == true}">
										<div class="action-width">
											<a data-placement="right" data-original-title="Summary" data-toggle="tooltip" class="fa fa-info table-icon" onClick="summaryDialog('${job?.id}');" ></a>
										</div>
									</g:if>
									<g:else>
										N/A
									</g:else>
								</td>
								<td nowrap="nowrap" class="text-center ${fontColor}">
									<g:if test="${job?.hasStacktrace != null && job?.hasStacktrace == true}">
										<div class="action-width">
											<a data-placement="right" data-original-title="Stack Trace" data-toggle="tooltip" class="fa fa-info table-icon" onClick="stacktraceDialog('${job?.id}');" ></a>
										</div>
									</g:if>
									<g:else>
										N/A
									</g:else>
								</td>
							</g:if>
						</tr>
					</g:each>
				</g:if>
				<g:else>
					<tr>
						<td colspan="7" class="no-record-found" ><g:message code="default.record.not.found" /></td>
					</tr>
				</g:else>
			</tbody>
		</table>
	</div>
	
	<g:render template="/common/returnLink" model="['returnAction': 'list', 'returnController': 'CustomQuartz', 'messageCode':'etech.CustomQuartz.return.screen', 'defaultMessage': 'Return to Job Maintenance']"></g:render>
						
	<div class="pagination">
		<g:paginate total="${result.jobListCount ?: 0}" params="${result.params}"/>
	</div>
</div>