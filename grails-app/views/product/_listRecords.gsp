<%@ page import="org.solcorp.etech.Product" %>
<%@ page import="org.solcorp.etech.ProductClassType" %>
<div class="col-xs-12">
          			<div class="table-responsive">
						<table class="table table-hover">
							<thead>
								<tr>
									<g:sortableColumn params="${params}" property="code" title="${message(code: 'product.code.label', default: 'Code')}" defaultOrder="desc" />
									<g:if test="${params?.productClassType == ProductClassType.EXPENSES.name()}">
										<th>GL A/C Number</th>
									</g:if>
									<th>Description</th>
									<th>Type</th>
									<th>Category</th>
									<%-- 
									<g:sortableColumn params="${params}" property="description" title="${message(code: 'product.description.label', default: 'Description')}" defaultOrder="desc" />
									<g:sortableColumn params="${params}" property="productClassType" title="${message(code: 'product.productClassType.label', default: 'Type')}" defaultOrder="desc" />
									<g:sortableColumn params="${params}" property="category" title="${message(code: 'product.category.label', default: 'Category')}" defaultOrder="desc" />
									--%>
								</tr>
							</thead>
							<tbody>
								<g:if test="${productInstanceList?.size() > 0}">
									<g:each in="${productInstanceList}" status="i" var="productInstance">
										<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
											<g:if test="${params?.productType}">
												<td><g:link action="edit" id="${productInstance.id}" params="['productType': params.('productType')]">${fieldValue(bean: productInstance, field: "code") }</g:link></td>
											</g:if>
											<g:else>
												<td><g:link action="edit" id="${productInstance.id}">${fieldValue(bean: productInstance, field: "code") }</g:link></td>
											</g:else>
											<g:if test="${params?.productClassType == ProductClassType.EXPENSES.name()}">
												<td>${productInstance?.glAccountNumber}</td>
											</g:if>
											<td>${fieldValue(bean: productInstance, field: "description")}</td>
											<td>${productInstance.getProductClassType()}</td>
											<td>${productInstance?.productCategory?.category}</td>
										</tr>
									</g:each>
								</g:if>
								<g:else>
										<g:if test="${params?.productClassType == ProductClassType.EXPENSES.name()}">
											<tr><td colspan="5" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
										</g:if>
										<g:else>
											<tr><td colspan="4" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
										</g:else>
								</g:else>
							</tbody>
						</table>
					</div>
								<g:if test="${params?.productType}"> 
									<g:render template="/common/returnLink" model="['returnAction': 'create', 'returnController': 'product', 'messageCode':'etech.product.return.screen', 'defaultMessage': 'Return to Product Screen', 'linkParam': ['productType': params.('productType')]]"></g:render>
								</g:if>
								<g:else>
										<g:if test="${params?.productClassType == ProductClassType.PROJECT.name()}">												
												<g:set value="Project Product" var="productName"></g:set>
										</g:if>
										<g:else>
											<g:set value="${ProductClassType?.valueOf(params?.productClassType)}" var="productName"></g:set>
										</g:else>
	 									<g:render template="/common/returnLink" model="['returnAction': 'create', 'returnController': 'product', 'messageCode':'etech.product.'+productName+'.return.screen', 'defaultMessage': 'Return to '+ productName +' Screen', 'linkParam': ['productClassType': params.('productClassType')]]"></g:render>
								</g:else>
							
							<div class="pagination">
									<g:paginate total="${productInstanceCount ?: 0}" params="${params}"/>
							</div>
							
						</div>