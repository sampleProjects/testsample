<%@ page import="org.solcorp.etech.ProductClassType" %>

<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:message code="etech.product.label" default="Product" /></title>
		<r:require module="product"/>
		  <r:script>	
			showControls($("#productClassType").val());
		</r:script>
		<content tag="pageName">${params?.productClassType ?: params?.productType}</content>
	</head>
	<body>
		<div class="navigation">
			<h3>
				<g:if test="${productInstance?.productClassType == null }">
					Products
				</g:if>
				<g:elseif test="${productInstance?.productClassType == ProductClassType.PROJECT}">
					Project Product
				</g:elseif>
				<g:else>
					${productInstance.productClassType}
				</g:else>
			</h3>
		</div>
		
	<div class="white-bg">
		<div class="title">
			<h2><g:if test="${params?.previousAction == 'newRecord' || (productInstance?.hasErrors() || cmd?.hasErrors()) }"><g:message code="default.sub.title.create.label" default= "Create" /></g:if><g:else><g:message code="default.sub.title.search.label" default= "Search" /></g:else></h2>
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

			
			
			<g:form method="post" name="createForm"  controller="product">
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
			
			<g:render template="productCategoryDialog"/>
		</div>
	
	</body>
</html>
