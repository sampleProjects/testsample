<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
<!-- Modal -->
<div class="modal fade dialog-box" id="hhProjectMasterDetailsDialog" role="dialog">

	<div class="modal-dialog modal-md">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"><g:message code="etech.project.ps.master.details.label" default="PS Project Master Details"/></h4>
			</div>
			<!-- Modal Body-->
			<div class="modal-body">
				<div class="row">					
				</div>
				<div class="clearfix"></div>
				
				<div class="table-responsive">

					<table class="table table-striped">
						<thead>
							<tr>
								<th><g:message code="etech.project.business.unit.label" default="Business Unit" /></th>
								
								<th><g:message code="etech.project.projectid.label" default="Project Id" /></th>

								<th><g:message code="etech.project.description.label" default="Description" /></th>
																
								<th><g:message code="etech.project.customer.label" default="Customer Id" /></th>

								<th><g:message code="etech.project.hhcustid.label" default="HH Customer Id" /></th>

								<th><g:message code="etech.project.name1.label" default="Name1" /></th>
								
								<th><g:message code="etech.project.eff.status.label" default="Eff Status" /></th>
								
								<th><g:message code="etech.project.start.date.label" default="Start Date" /></th>
								
								<th><g:message code="etech.project.end.date.label" default="End Date" /></th>
								
								<th><g:message code="etech.project.sales.person.label" default="Sales Person" /></th>
								
								<th><g:message code="etech.project.name2.label" default="Name2" /></th>
							</tr>
						</thead>
						<tbody>
							<g:if test="${hhProjectMstDetailList?.size() > 0}">
							<g:each in="${hhProjectMstDetailList}" status="i" var="hhProjectMstDtlInstance">				
								<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
																 
									<td>${hhProjectMstDtlInstance?.businessUnit}</td>
									
									<td>${hhProjectMstDtlInstance?.projectId}</td>

									<td>${hhProjectMstDtlInstance?.descr}</td>

									<td>${hhProjectMstDtlInstance?.custId}</td>

									<td>${hhProjectMstDtlInstance?.hhCustId}</td>
									
									<td>${hhProjectMstDtlInstance?.name1}</td>
									
									<td>${hhProjectMstDtlInstance?.effStatus == 'A'? 'Open' : 'Closed' }</td>
									
									<td>${DateFormatUtils.dateConvertFromString(hhProjectMstDtlInstance?.startDt)}</td>
									
									<td>${DateFormatUtils.dateConvertFromString(hhProjectMstDtlInstance?.endDt)}</td>
									
									<td>${hhProjectMstDtlInstance?.salesPerson?:'-'}</td>
									
									<td>${hhProjectMstDtlInstance?.name2?:'-'}</td>

								</tr>
							 </g:each>
							</g:if>
							<g:else>
								<tr>
									<td colspan="11" class="no-record-found"><g:message code="default.record.not.found" /></td>
								</tr>
							</g:else>
						</tbody>
					</table>
				</div>
			</div>
			
			<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
			
		</div>
	</div>
</div>
