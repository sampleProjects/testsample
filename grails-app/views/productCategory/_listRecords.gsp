<%@ page import="org.solcorp.etech.ProductCategory" %>
<%@ page import="org.solcorp.etech.ProductClassType" %>
<div class="col-xs-12">
	<div class="table-responsive">
		<table class="table table-hover">
			<thead>
				<tr>
					<g:sortableColumn params="${params}" property="category" title="${message(code: 'productCategory.code.label', default: 'Category')}" defaultOrder="desc" />
					<th>Type</th>
					<th>Description</th>
				</tr>
			</thead>
			<tbody>
				<g:if test="${productCategoryInstanceList?.size() > 0}">
					<g:each in="${productCategoryInstanceList}" status="i" var="productCategoryInstance">
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							<g:if test="${params?.productType}">
								<td><g:link action="edit" id="${productCategoryInstance.id}" params="['productType': params.('productType')]">${fieldValue(bean: productCategoryInstance, field: "category") }</g:link></td>
							</g:if>
							<g:else>
								<td><g:link action="edit" id="${productCategoryInstance.id}">${fieldValue(bean: productCategoryInstance, field: "category") }</g:link></td>
							</g:else>
							<td>${productCategoryInstance.getProductClassType()}</td>
							<td>${fieldValue(bean: productCategoryInstance, field: "description")}</td>
						</tr>
					</g:each>
				</g:if>
				<g:else>
						<tr><td colspan="3" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
				</g:else>
			</tbody>
		</table>
	</div>
	
	<g:if test="${params?.productType}"> 
		<g:render template="/common/returnLink" model="['returnAction': 'create', 'returnController': 'productCategory', 'messageCode':'etech.productCategory.return.screen', 'defaultMessage': 'Return to Product Category Screen', 'linkParam': ['productType': params?.productType]]"></g:render>
	</g:if>
	<g:else>
		<g:if test="${params?.productClassType == ProductClassType.PROJECT.name()}">												
			<g:set value="Project Product" var="productName"></g:set>
		</g:if>
		<g:else>
			<g:set value="${ProductClassType?.valueOf(params?.productClassType)}" var="productName"></g:set>
		</g:else>
		<g:render template="/common/returnLink" model="['returnAction': 'create', 'returnController': 'productCategory', 'messageCode':'etech.product.'+productName+'.return.screen', 'defaultMessage': 'Return to '+ productName +' Category Screen', 'linkParam': ['productClassType': params.('productClassType')]]"></g:render>
	</g:else>
	
	<div class="pagination">
		<g:paginate total="${productCategoryInstanceCount ?: 0}" params="${params}"/>
	</div>
</div>