<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.solcorp.etech.ProductCategory" %>
<%@ page import="org.solcorp.etech.ProductClassType" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="layout" content="main"/>
<title><g:message code="etech.productCategory.search.label" default="Product Category Search" /></title>
<content tag="pageName">${params.productClassType}_Category</content>
</head>
<body>
	<div class="navigation">
		<h3>
			<g:if test="${params?.productClassType == ProductClassType.PROJECT.name()}">
				Project Product Category
			</g:if>
			<g:else>
				${ProductClassType?.valueOf(params?.productClassType)} Category
			</g:else>
		</h3>
	</div>
	
	<div id="list-product" class="col-sm-12" role="main">
		<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
		</g:if>
			
		<div class="white-bg">
			<div class="title">
				<h2>List</h2>
        	</div>
        	
        	<g:render template="/common/paginationCombo" model="['totalRecord':productCategoryInstanceCount, 'searchAction': 'list', 'searchController': 'productCategory', 'divIdToUpdate': 'listRecordsDiv']"></g:render>
                      
          	<div id="listRecordsDiv">
          		<g:render template="listRecords"></g:render>
			</div>	
        </div>
	</div>
</body>
</html>