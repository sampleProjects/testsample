
<%@ page import="org.solcorp.etech.ProductClassType" %>
<%@ page import="org.solcorp.etech.Product" %>

<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:message code="etech.product.label" default="Product" /></title>
		<r:require module="product"/>
		<content tag="pageName">${productInstance?.productClassType?.name()}</content>
	</head>
	<body>
		<div class="navigation">
			<g:set var="action" value="${message(code: 'product.edit.label', default: 'Edit')}" />
			<h3>
				<g:if test="${productInstance?.productClassType == null }">
					Products (Code: ${productInstance?.code})
				</g:if>
				<g:elseif test="${productInstance?.productClassType == ProductClassType.PROJECT}">
					Project Product (Code: ${productInstance?.code})
				</g:elseif>
				<g:else>
					${productInstance.productClassType} (Code: ${productInstance?.code})
				</g:else>
			</h3>
		</div>


	<div class="white-bg">
		<div class="title">
			<h2><g:message code="product.edit.label" default="Edit" /></h2>
		</div>
		
		<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
		</g:if>
			
		<g:hasErrors bean="${productInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${productInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
		</g:hasErrors>
		
		<g:hasErrors bean="${cmd}">
			<ul class="errors" role="alert">
    			<g:eachError var="err" bean="${cmd}">
					<li><g:message error='${err}' /></li>
    			</g:eachError>
    		</ul>
		</g:hasErrors>
			
		<g:form  method="PUT" >
			<g:hiddenField name="version" value="${productInstance?.version}" />
			<fieldset class="form">
				<g:render template="form"/>
			</fieldset>
			
			<div class="clearfix"></div>
			
			<div class="col-sm-12 text-center btn-space">
				<g:actionSubmit class="btn btn-success" controller="product" action="update" value="${message(code: 'default.button.save.label', default: 'Save')}" />
        		<g:actionSubmit class="btn btn-success"  action="newRecord" name="newRecord" value="${message(code: 'default.new.link.label', default: 'New')}"/>
        		<g:actionSubmit class="btn btn-success"  action="clear" name="clear" value="${message(code: 'default.clear.label', default: 'Clear')}"/>
        		<g:link class="btn btn-success" action="delete" id="${productInstance?.id}" params="[productType: params?.productType?:productType]" onclick="return confirmDelete()"><g:message code="default.delete.label" default="Delete" /></g:link>
        	</div>
        	<g:hiddenField name="productInstanceId" value="${productInstance?.id}" />
        	<g:hiddenField name="productInstanceCode" value="${productInstance?.code}" />
		</g:form>
		
		<g:render template="productCategoryDialog"/>
	</div>
		
	</body>
</html>
