<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="org.solcorp.etech.Constants"%>
<%@ page import="org.solcorp.etech.ProductCategory" %>
<%@ page import="org.solcorp.etech.ProductClassType" %>

<div class="col-sm-4 col-md-3">
	<div class="form-group ${productCategoryInstance?.id ? 'edit-code' : ''}">	 
			<label for="category" class="control-label">
				<g:message code="productCategory.category.label" default="Category" />
				<span class="required-indicator">*</span>
			</label>
			<g:if test="${productCategoryInstance?.id}">
				<g:textField class="form-control fieldcontain ${hasErrors(bean: productCategoryInstance, field: 'category', 'error')} required pull-left" name="category" onblur="keyFilter(this);" maxlength="${Constants.PRODUCTCATEGORY_CATEGORY_LENGTH}" disabled = "disabled" value="${productCategoryInstance?.category}"/>
				<a href="#" onClick="enableCodeField('category');" class="pull-left fa fa-pencil" data-toggle="tooltip" data-original-title="Edit : Category" data-placement="top"></a>
			</g:if>
			<g:else>
				<g:textField class="form-control fieldcontain ${hasErrors(bean: productCategoryInstance, field: 'category', 'error')} required" name="category" onblur="keyFilter(this);" maxlength="${Constants.PRODUCTCATEGORY_CATEGORY_LENGTH}" disabled = "${productCategoryInstance?.id?:disabled}" value="${productCategoryInstance?.category}"/>
			</g:else>
		
		</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">	 
			<label for="productClassType" class="control-label">
				<g:message code="productCategoryInstance.productClassType.label" default="Class" />
				<span class="required-indicator">*</span>
			</label>
			<g:hiddenField name="productType" value="${params?.productType?:productType}"/>
			<g:hiddenField name="disabledField" value="${params?.disabledField}"/>
			
			<g:if test="${productCategoryInstance?.id || params?.disabledField == 'true'}">
				<g:hiddenField name="productClassType" value="${productCategoryInstance?.productClassType?.name()}" />
				<g:select name="productClassType" from="${ProductClassType?.classTypelist()}" class="selectpicker form-control fieldcontain ${hasErrors(bean: productCategoryInstance, field: 'productClassType', 'error')}" disabled="disabled" keys="${ProductClassType.values()*.name()}" value="${productCategoryInstance?.productClassType?.name()}" />
			</g:if>
			<g:else>
				<g:select name="productClassType" from="${ProductClassType?.classTypelist()}"  class="selectpicker form-control fieldcontain ${hasErrors(bean: productCategoryInstance, field: 'productClassType', 'error')}" keys="${ProductClassType.values()*.name()}" value="${productCategoryInstance?.productClassType?.name()}" />
			</g:else>
		</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
		<label for="description" class="control-label">
			<g:message code="productCategoryInstance.description.label" default="Description" />
		</label>
		<g:textArea name="description" class="form-control fieldcontain ${hasErrors(bean: productCategoryInstance, field: 'description', 'error')} required" value="${productCategoryInstance?.description}" maxlength="${Constants.PRODUCTCATEGORY_DESCRIPTION_LENGTH}" />
	</div>
</div>