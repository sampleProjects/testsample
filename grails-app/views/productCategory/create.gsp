<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.solcorp.etech.ProductClassType" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="layout" content="main"/>
<title><g:message code="etech.productCategory.label" default="Product Category" /></title>
<content tag="pageName">${params?.productClassType ?: params?.productType}_Category</content>
</head>
<body>
	<div class="navigation">
		<h3>
			<g:if test="${productCategoryInstance?.productClassType == null }">
				Product Category
			</g:if>
			<g:elseif test="${productCategoryInstance?.productClassType == ProductClassType.PROJECT}">
				Project Product Category
			</g:elseif>
			<g:else>
				${productCategoryInstance.productClassType} Category
			</g:else>
		</h3>
	</div>
	
	<div class="white-bg">
		<div class="title">
			<h2><g:if test="${params?.previousAction == 'newRecord' || productCategoryInstance?.hasErrors() }"><g:message code="default.sub.title.create.label" default= "Create" /></g:if><g:else><g:message code="default.sub.title.search.label" default= "Search" /></g:else></h2>
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
		
		<g:form method="post" name="createForm"  controller="productCategory">
			<fieldset class="form">
				<g:render template="form"/>
			</fieldset>
				
			<div class="clearfix"></div>
				
        	<div class="col-sm-12 text-center btn-space">
        		<g:actionSubmit action="save"  name="create" class="btn btn-success" value="${message(code: 'default.button.save.label', default: 'Save')}" />
        		<g:actionSubmit class="btn btn-success"  action="newRecord" name="newRecord" value="${message(code: 'default.new.link.label', default: 'New')}"/>
        		<g:actionSubmit class="btn btn-success"  action="clear" name="clear" value="${message(code: 'default.clear.label', default: 'Clear')}"/>
        		<g:actionSubmit class="btn btn-success"  action="list" name="search" value="${message(code: 'default.button.search.label', default: 'Search')}"/>
        	</div>
		</g:form>
	</div>
</body>
</html>