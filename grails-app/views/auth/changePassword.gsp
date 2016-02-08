<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Change Password</title>
		<meta name="layout" content="changePasswordTemplate" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
	</head>
<body>
	<div class="login-page">
		<div class="container">
		    <div class="row">
		    	<div class="login-form col-xs-12 col-sm-offset-3 col-sm-6 col-md-offset-4 col-md-4">
		        	<h1>Change Password</h1>		        		 
		        		 <g:if test="${flash.message}">
		    				<div class="message">${flash.message}</div>
		  				 </g:if>
		  				 <g:if test="${flash.error}">
  							<div class="alert alert-error" style="display: block">${flash.error}</div>
						</g:if>		  				 
		  				<g:form action="saveChangePassword" class="clearfix">
			    			<input type="hidden" name="targetUri" value="${targetUri}" />            
			            	<div class="form-group">
			                	<label>Old Password *</label>
			                    <input type="password" name=oldPassword class="form-control"/>
			                </div>
			                <div class="form-group">
			                	<label>New Password *</label>
			                   <input type="password" name="newPassword" value="" class="form-control" />
			                </div>
			                <div class="form-group">
			                	<label>Confirm Password *</label>
			                   <input type="password" name="confirmPassword" value="" class="form-control" />
			                </div>
			                <div class="form-group text-center">
			                	<input type="submit" value="Change" class="btn btn-primary" /> 
			                	<input type="reset" value="Reset" class="btn btn-primary" />                    
			                </div>                 
		            	</g:form>       
						<p class="copyright text-center">&copy; Copyright SolutionCorp.com, Inc.</p>
		    	</div> <!-- Login form -->
		    </div>
		</div>
	</div>
</body>
</html>