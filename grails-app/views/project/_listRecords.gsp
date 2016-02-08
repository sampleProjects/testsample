<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
	<div class="col-xs-12">
          		 <div class="table-responsive">
				   <table class="table table-hover">		
						<thead>
					<tr>
					
						<g:sortableColumn property="code" params="${params}" defaultOrder="desc" title="${message(code: 'etech.project.id.label', default: 'ID')}" />
					
						<th><g:message code="etech.project.name.label" default="Name" /></th>
						
						<th><g:message code="etech.project.customer.label" default="Customer" /></th>
						
						<th><g:message code="etech.project.status.label" default="Status" /></th>
					
						<th><g:message code="etech.project.imported.by.job.label" default= "Imported By Job"/></th>
						
						<th><g:message code="etech.project.start.date.label" default="Start Date" /></th>
						
						<th><g:message code="etech.project.end.date.label" default="End Date" /></th>
						
						<th><g:message code="etech.project.end.date.label" default="Last Trx" /></th>
						
					</tr>
				</thead>
				<tbody>
				<g:if test="${projectInstanceList?.size() > 0}">	
				<g:each in="${projectInstanceList}" status="i" var="projectInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="edit" id="${projectInstance.id}">${fieldValue(bean: projectInstance, field: "code")}</g:link></td>
					
						<td>${fieldValue(bean: projectInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: projectInstance, field: "customer.name")}</td>
						
						<td>${fieldValue(bean: projectInstance, field: "status")}</td>
					
						<td>${projectInstance?.isSystemJobUser() ? 'Yes' : 'No' }</td>
						
						<td>${DateFormatUtils.getStringFromDate(projectInstance?.startDate)}</td>
						
						<td>${DateFormatUtils.getStringFromDate(projectInstance?.endDate)}</td>
						
						<td>${DateFormatUtils.getStringFromDate(projectInstance?.lastTrx)}</td>
						
					</tr>
				</g:each>
				</g:if>
				 <g:else>	
							<tr><td colspan="8" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
						</g:else>
				</tbody>
			</table>
			</div>
				<g:render template="/common/returnLink" model="['returnAction': 'create', 'returnController': 'project', 'messageCode':'etech.project.return.screen', 'defaultMessage': 'Return to Project Screen']"></g:render>
				<div class="pagination">
					<g:paginate total="${projectInstanceCount ?: 0}"  params="${params}"/>
				</div>
			</div>