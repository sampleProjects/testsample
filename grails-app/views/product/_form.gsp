<%@ page import="org.solcorp.etech.Product" %>
<%@ page import="org.solcorp.etech.Constants" %>
<%@ page import="org.solcorp.etech.ProductClassType" %>

<div class="col-sm-4 col-md-3">
	<div class="form-group ${productInstance?.id ? 'edit-code' : ''}">	 
			<label for="code" class="control-label">
				<g:message code="product.code.label" default="Code" />
				<span class="required-indicator">*</span>
			</label>
			
			<g:if test="${productInstance?.id}">
				<g:if test="${Constants.DEFAULT_PRODUCT != productInstance?.code && 
					  Constants.DEFAULT_SERVICE != productInstance?.code}">
					  <g:textField class="form-control fieldcontain ${hasErrors(bean: cmd?:productInstance, field: 'code', 'error')} required pull-left" name="code" onblur="keyFilter(this);" maxlength="${Constants.PRODUCT_CODE_LENGTH}" disabled = "disabled" value="${productInstance?.code}"/>
					  <a href="#" onClick="enableCodeField('code');" class="pull-left fa fa-pencil" data-toggle="tooltip" 	data-original-title="Edit : Code" data-placement="top"></a>
				</g:if>
				<g:else>
					<div>${productInstance?.code}</div>
					<g:hiddenField name="code" value="${productInstance?.code}"/>
				</g:else>
			</g:if>
			<g:else>
				<g:textField class="form-control fieldcontain ${hasErrors(bean: cmd?:productInstance, field: 'code', 'error')} required" name="code" onblur="keyFilter(this);" maxlength="${Constants.PRODUCT_CODE_LENGTH}" disabled = "${productInstance?.id?:disabled}" value="${productInstance?.code}"/>
			</g:else>
		
		</div>
</div>
		
<div class="col-sm-4 col-md-3">
	<div class="form-group">	 
		
		
			<label for="productClassType" class="control-label">
				<g:message code="product.productClassType.label" default="Class" />
				<span class="required-indicator">*</span>
			</label>
			<g:hiddenField name="productType" value="${params?.productType?:productType}"/>
			<g:hiddenField name="disabledField" value="${params?.disabledField}"/>
			
			<g:if test="${productInstance?.id || params?.disabledField == 'true'}">
				<g:hiddenField id="productClassType" name="productClassType" value="${productInstance?.productClassType?.name()}" />
				<g:select name="productClassType" from="${ProductClassType?.classTypelist()}" class="selectpicker form-control fieldcontain ${hasErrors(bean: productInstance, field: 'productClassType', 'error')}" disabled="disabled" keys="${ProductClassType.values()*.name()}" value="${productInstance?.productClassType?.name()}"  onchange='showControls(this.value);' />
			</g:if>
			<g:else>
				<g:select id="productClassType" name="productClassType" from="${ProductClassType?.classTypelist()}"  class="selectpicker form-control fieldcontain ${hasErrors(bean: productInstance, field: 'productClassType', 'error')}" keys="${ProductClassType.values()*.name()}" value="${productInstance?.productClassType?.name()}"  onchange='showControls(this.value);' />
			</g:else>
			
		</div>
</div>
	

	
	
<g:if test="${(productInstance?.productClassType?.name() == 'SERVICES')}">
	<div class="col-sm-4 col-md-3" id="laborActivityGroupDiv">
		<div class="form-group">
				<label for="laborActivityGroup" class="control-label">
					<g:message code="product.laborActivityGroup.label" default="Labor Department" />
					<span class="required-indicator">*</span>
				</label>
				<g:select id="laborActivityGroup" class="selectpicker form-control  fieldcontain ${hasErrors(bean: cmd, field: 'laborActivityGroup', 'error')}" name="laborActivityGroup" from="${laborActGrpList}" optionKey="id" optionValue="code" value="${params?.laborActivityGroupId}" noSelection="['': '--Select--']" />
		</div>
	</div>
			
	<div class="col-sm-4 col-md-3" id="projectProductDiv">
		<div class="form-group">
				<label for="laborActivityGroup" class="control-label">
					<g:message code="product.projectproduct.label" default="Product (Project)" />
					<span class="required-indicator">*</span>
				</label>
				<g:select id="product" class="selectpicker form-control  fieldcontain ${hasErrors(bean: cmd, field: 'product', 'error')}" name="product" from="${projectProductList}" optionKey="id" optionValue="code" value="${params?.projectProductId}" noSelection="['': '--Select--']" />
		</div>
	</div>
	<div class="clearfix"></div>
</g:if>
<g:else>
	<div class="col-sm-4 col-md-3" id="laborActivityGroupDiv" style="display: none;">
		<div class="form-group">
				<label for="laborActivityGroup" class="control-label">
					<g:message code="product.laborActivityGroup.label" default="Labor Department" />
					<span class="required-indicator">*</span>
				</label>
				<g:select id="laborActivityGroup" class="selectpicker form-control fieldcontain ${hasErrors(bean: cmd, field: 'laborActivityGroup', 'error')}" name="laborActivityGroup" from="${laborActGrpList}" optionKey="id" optionValue="code" value="${params?.laborActivityGroupId}" noSelection="['': '--Select--']" />
		</div>
	</div>
		
	<div class="col-sm-4 col-md-3" id="projectProductDiv" style="display: none;">
		<div class="form-group">
				<label for="laborActivityGroup" class="control-label">
					<g:message code="product.projectproduct.label" default="Product (Project)" />
					<span class="required-indicator">*</span>
				</label>
				<g:select id="product" class="selectpicker form-control fieldcontain ${hasErrors(bean: cmd, field: 'product', 'error')}" name="product" from="${projectProductList}" optionKey="id" optionValue="code" value="${params?.projectProductId}" noSelection="['': '--Select--']" />
		</div>
	</div>
</g:else>


<div class="col-sm-4 col-md-3">
	<div class="form-group edit-code">	
			<label for="code" class="control-label" style="cursor: pointer; text-decoration: underline;" data-toggle="modal" data-target="#productCategoryDialog">
				<g:message code="product.category.label" default="Category" />
			</label>
			<g:hiddenField name="productCategory" id="productCategory" value="${productInstance?.productCategory?.id}" />
			<g:textField class="form-control fieldcontain pull-left ${hasErrors(bean: productInstance, field: 'productCategory', 'error')}" id="productCategoryTxt" name="productCategoryTxt" maxlength="${Constants.PRODUCT_CATEGORY_LENGTH}" value="${productInstance?.productCategory?.category}" disabled = "disabled" />
			<a href="#" onClick="clearProductCategory();" class="pull-left fa fa-remove" data-toggle="tooltip" data-original-title="Clear: Category" data-placement="top"></a>
		</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
			<label for="standardRate" class="control-label">
				<g:message code="product.standardRate.label" default="Standard Rate" />
			</label>
			<g:textField class="form-control fieldcontain ${hasErrors(bean: cmd?:productInstance, field: 'standardRate', 'error')} required onlyDecimal" name="standardRate" value="${productInstance?.standardRate}"/>
	</div>
</div>

<g:if test="${(productInstance?.productClassType?.name() == 'EXPENSES')}">
	<div class="col-sm-4 col-md-3" id="glAccountNumberDiv">
		<div class="form-group">
				<label for="glAccountNumber" class="control-label">
					<g:message code="product.glAccountNumber.label" default="GL A/C Number" />
				</label>
				<g:textField class="form-control fieldcontain ${hasErrors(bean: cmd?:productInstance, field: 'glAccountNumber', 'error')} required onlyInteger" name="glAccountNumber" maxlength="${Constants.PRODUCT_EXPENSE_CATEGORY_GLACCOUNT_LENGTH}" value="${productInstance?.glAccountNumber}"/>
		</div>
	</div>
</g:if>
<g:else>
	<div class="col-sm-4 col-md-3" id="glAccountNumberDiv" style="display: none;">
		<div class="form-group">
				<label for="glAccountNumber" class="control-label">
					<g:message code="product.glAccountNumber.label" default="GL A/C Number" />
				</label>
				<g:textField class="form-control fieldcontain ${hasErrors(bean: cmd?:productInstance, field: 'glAccountNumber', 'error')} required onlyInteger" name="glAccountNumber" maxlength="${Constants.PRODUCT_EXPENSE_CATEGORY_GLACCOUNT_LENGTH}" value=""/>
		</div>
	</div>
</g:else>

<div class="col-sm-4 col-md-3">
	<div class="form-group">			 
			<label for="description" class="control-label">
				<g:message code="product.description.label" default="Description" />		
			</label>
			<g:textArea class="form-control  fieldcontain ${hasErrors(bean: productInstance, field: 'description', 'error')} " name="description" maxlength="${Constants.PRODUCT_DESCRIPTION_LENGTH}" value="${productInstance?.description}"/>
	</div>
</div>

