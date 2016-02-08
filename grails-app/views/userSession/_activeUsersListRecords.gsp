			<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>	
			<div class="col-xs-12">
			<div class="table-responsive">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>User Name</th>
								<th>First Name</th>
								<th>Last Name</th>
								<th>Start Date</th>
							</tr>
						</thead>
						<tbody>
						<g:if test="${userSessionList?.size() > 0}">
							<g:each in="${userSessionList}" status="i" var="userSessionInstance">
								<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">									
									<td>${userSessionInstance[0]?.username}</td>
									<td>${userSessionInstance[0]?.employee?.firstName}</td>
									<td>${userSessionInstance[0]?.employee?.lastName}</td>
									<td>${DateFormatUtils.getStringFromDate(userSessionInstance[1])}</td>

								</tr>
							</g:each>
						</g:if>
						<g:else>	
							<tr><td colspan="4" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
						</g:else>
						</tbody>
					</table>
				</div>
				<g:render template="/common/returnLink" model="['returnAction': 'dashboard', 'returnController': 'Auth', 'messageCode':'etech.COE.return.screen', 'defaultMessage': 'Return to Dashboard']"></g:render>
				<div class="pagination">
					<g:paginate total="${userSessionListCount ?: 0}" params="${params}" />
				</div>
			</div>