<div class="col-xs-12">
          			<div class="table-responsive">
						<table class="table table-hover">
							<thead>
								<tr>
									<g:sortableColumn params="${params}" property="category" title="${message(code: 'projectCategory.category.label', default: 'Category')}" defaultOrder="desc" />
									<th> <g:message code="projectCategory.description.label" default="Description"></g:message>
								</tr>
							</thead>
							<tbody>
								<g:if test="${projectCategoryInstanceList?.size() > 0}">
									<g:each in="${projectCategoryInstanceList}" status="i" var="projectCategoryInstance">
										<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
											<td><g:link action="edit" id="${projectCategoryInstance.id}">${fieldValue(bean: projectCategoryInstance, field: "category")}</g:link></td>						
											<td>${fieldValue(bean: projectCategoryInstance, field: "description")}</td>
										</tr>
									</g:each>
								</g:if>
								<g:else>
									<td colspan="4" class="no-record-found" ><g:message code="default.record.not.found" /></td>
								</g:else>
							</tbody>
						</table>
					</div>
					<g:render template="/common/returnLink" model="['returnAction': 'create', 'returnController': 'ProjectCategory', 'messageCode':'etech.ProjectCategory.return.screen', 'defaultMessage': 'Return to Project Category Screen']"></g:render>
					<div class="pagination">
						<g:paginate total="${projectCategoryInstanceCount ?: 0}"  params="${params}"/>
					</div>
				</div>