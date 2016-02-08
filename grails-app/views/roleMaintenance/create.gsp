<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">		
		<title><g:message code="etech.roleMaintenance.label" default="Role Maintenance" /></title>
		<style type="text/css">
			.rotate270 {
			  /* FF3.5+ */
			  -moz-transform: rotate(-270.0deg);
			  /* Opera 10.5 */
			  -o-transform: rotate(-270.0deg);
			  /* Saf3.1+, Chrome */
			  -webkit-transform: rotate(-270.0deg);
			  /* IE6,IE7 */
			  filter: progid: DXImageTransform.Microsoft.BasicImage(rotation=3);
			  /* IE8 */
			  -ms-filter: "progid:DXImageTransform.Microsoft.BasicImage(rotation=3)";
			  /* Standard */
			  transform: rotate(-270.0deg);
			}
			
			.rotate90 {
			  /* FF3.5+ */
			  -moz-transform: rotate(-90.0deg);
			  /* Opera 10.5 */
			  -o-transform: rotate(-90.0deg);
			  /* Saf3.1+, Chrome */
			  -webkit-transform: rotate(-90.0deg);
			  /* IE6,IE7 */
			  filter: progid: DXImageTransform.Microsoft.BasicImage(rotation=1);
			  /* IE8 */
			  -ms-filter: "progid:DXImageTransform.Microsoft.BasicImage(rotation=1)";
			  /* Standard */
			  transform: rotate(-90.0deg);
			}
		</style>
		<content tag="pageName">roleMaintenance</content>
	</head>
<body>
  	<div class="navigation">			
		<h3><g:message code="etech.roleMaintenance.label" default="Role Maintenance" /></h3>
	</div>
	<div class="white-bg">
       	<br />	
		<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
		</g:if>
		<g:hasErrors bean="${roleMaintenanceInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${roleMaintenanceInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
		</g:hasErrors>
		<div class="col-sm-12">
		<g:form method="post" name="createForm"  controller="roleMaintenance" action="save">	
			<div class="col-sm-12 table-responsive marginbtm15">
			<fieldset class="form">
				<g:render template="form"/>
			</fieldset>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-12 text-center">
			<g:actionSubmit  class="btn btn-success" action="save"  name="create" value="${message(code: 'default.button.save.label', default: 'Save')}" />
			<g:actionSubmit class="btn btn-success"  action="clear" name="clear" value="${message(code: 'default.reset.label', default: 'Reset')}"/>
			</div>
		</g:form>
		</div>
	</div>
	<script>
	//Rotate text by 90 degree
		$(document).ready(function() {
		  $('.rotate270').css('height', $('.rotate270').width());
		  $('.rotate90').css('height', $('.rotate90').width());
		});
	</script>
</body>
</html>