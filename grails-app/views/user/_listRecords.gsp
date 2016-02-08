<%@ page import="org.solcorp.etech.RoleType" %>
<div class="col-xs-12">
          		 <div class="table-responsive">
				   <table class="table table-hover">			
					<thead>
						<tr>
							<g:sortableColumn property="username" params="${params}" title="${message(code: 'etech.user.userid.label', default: 'User ID')}" defaultOrder="desc" />					
							 					
							<th><g:message code= "etech.user.status.label"  default= "Status" /></th>
							
							<th><g:message code= "etech.user.firstName.label"  default= "First Name" /></th>
							
							<th><g:message code= "etech.user.lastName.label"  default= "Last Name" /></th>
							
							<th><g:message code= "etech.user.roles.label"  default= "Role" /></th>
																					
						</tr>
					</thead>
					<tbody>
					<g:if test="${userInstanceList?.size() > 0}">	
						<g:each in="${userInstanceList}" status="i" var="userInstance">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">						
								<td><g:link action="edit" id="${userInstance.id}">${fieldValue(bean: userInstance, field: "username")}</g:link></td>						
								<td>${fieldValue(bean: userInstance, field: "status")}</td>	
								<td>${fieldValue(bean: userInstance?.employee, field: "firstName")}</td>
								<td>${fieldValue(bean: userInstance?.employee, field: "lastName")}</td>
								<td>${RoleType.valueOf(userInstance?.roles[0]?.name)}</td>
							</tr>
						</g:each>
					</g:if>
					<g:else>
						<tr>
							<td colspan="5"  class="no-record-found"><g:message code="default.record.not.found" /></td>
						</tr>
					</g:else>
					</tbody>
				</table>
			</div>
			<g:render template="/common/returnLink" model="['returnAction': 'create', 'returnController': 'user', 'messageCode':'etech.user.return.screen', 'defaultMessage': 'Return to User Screen']"></g:render>
			<div class="pagination">
				<g:paginate total="${userInstanceCount ?: 0}"  params="${params}"/>
			</div>
		</div>	