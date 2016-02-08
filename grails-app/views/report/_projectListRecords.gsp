<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
	<div class="col-xs-12">
          		 <div class="table-responsive">
				   <table class="table table-hover">		
						<thead>
					<tr>
					
						<g:sortableColumn property="code" params="${params}" defaultOrder="desc" title="${message(code: 'etech.report.project.project.label', default: 'Project')}" />
					
						<th><g:message code="etech.report.project.description.label" default="Description" /></th>
						
						<th><g:message code="etech.report.project.customer.label" default="Customer" /></th>
																		
					</tr>
				</thead>
				<tbody>
				<g:if test="${projectInstanceList?.size() > 0}">	
				<g:each in="${projectInstanceList}" status="i" var="projectInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="getProjectPerformanceDetail" id="${projectInstance.id}" params="['costOption': params?.costOption]">${fieldValue(bean: projectInstance, field: "code")}</g:link></td>
					
						<td>${fieldValue(bean: projectInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: projectInstance, field: "customer.name")}</td>
											
					</tr>
				</g:each>
				</g:if>
				 <g:else>	
							<tr><td colspan="4" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
						</g:else>
				</tbody>
			</table>
			</div>
				<g:render template="/common/returnLink" model="['returnAction': 'projectPerformanceReport', 'returnController': 'report', 'messageCode':'etech.report.return.filter', 'defaultMessage': 'Return to Report Filter']"></g:render>
				<div class="pagination">
					<g:paginate total="${projectInstanceCount ?: 0}"  params="${params}"/>
				</div>
			</div>