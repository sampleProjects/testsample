<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.solcorp.etech.ProductClassType" %>
<%@ page import="org.solcorp.etech.ProductCategory" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="layout" content="main"/>
<title><g:message code="etech.productCategory.label" default="Product Category" /></title>
<content tag="pageName">${productCategoryInstance?.productClassType?.name()}_Category</content>
</head>
<body>
  	<div class="navigation">
		<g:set var="action" value="${message(code: 'productCategory.edit.label', default: 'Edit')}" />
		<h3>
			<g:if test="${productCategoryInstance?.productClassType == null }">
				Product Category (Category: ${productCategoryInstance?.category})
			</g:if>
			<g:elseif test="${productCategoryInstance?.productClassType == ProductClassType.PROJECT}">
				Project Product Category (Category: ${productCategoryInstance?.category})
			</g:elseif>
			<g:else>
				${productCategoryInstance.productClassType} Category (Category: ${productCategoryInstance?.category})
			</g:else>
		</h3>
	</div>
	
	<div class="white-bg">
		<div class="title">
			<h2><g:message code="productCategory.edit.label" default="Edit" /></h2>
		</div>
		
		<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
		</g:if>
			
		<g:hasErrors bean="${productCategoryInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${productCategoryInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
		</g:hasErrors>
		
		<g:form  method="PUT" >
			<g:hiddenField name="version" value="${productCategoryInstance?.version}" />
			<fieldset class="form">
				<g:render template="form"/>
			</fieldset>
			
			<div class="clearfix"></div>
			
			<div class="col-sm-12 text-center btn-space">
				<g:actionSubmit class="btn btn-success" controller="productCategory" action="update" value="${message(code: 'default.button.save.label', default: 'Save')}" />
        		<g:actionSubmit class="btn btn-success"  action="newRecord" name="newRecord" value="${message(code: 'default.new.link.label', default: 'New')}"/>
        		<g:actionSubmit class="btn btn-success"  action="clear" name="clear" value="${message(code: 'default.clear.label', default: 'Clear')}"/>
        		<g:link class="btn btn-success" action="delete" id="${productCategoryInstance?.id}" params="[productType: params?.productType?:productType]" onclick="return confirmDelete()"><g:message code="default.delete.label" default="Delete" /></g:link>
        	</div>
        	<g:hiddenField name="productCategoryInstanceId" value="${productCategoryInstance?.id}" />
        	<g:hiddenField name="productCategoryInstanceCategory" value="${productCategoryInstance?.category}" />
		</g:form>
	</div>
</body>
</html>