<%@ page import="org.solcorp.etech.Product" %>
<%@ page import="org.solcorp.etech.ProductClassType" %>

<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:message code="etech.product.search.label" default="Product Search"	/></title>
		<content tag="pageName">${params.productClassType}</content>
	</head>
	<body>
		<div class="navigation">
			<h3>
				<g:if test="${params?.productClassType == ProductClassType.PROJECT.name()}">
					Project Product
				</g:if>
				<g:else>
					${ProductClassType?.valueOf(params?.productClassType)}
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
          		
          	<g:render template="/common/paginationCombo" model="['totalRecord':productInstanceCount, 'searchAction': 'list', 'searchController': 'product', 'divIdToUpdate': 'listRecordsDiv']"></g:render>
                      
          		<div id="listRecordsDiv">
          			<g:render template="listRecords"></g:render>
				</div>			
		</div>
	</div>
	</body>
</html>
