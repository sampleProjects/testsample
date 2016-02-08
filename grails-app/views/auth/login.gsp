<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<title>ETP Login</title>
		<meta name="layout" content="main" />
	</head>
<body>
	<div class="login-page">
		<div class="container">
		    <div class="row">
		    	<div class="login-form col-xs-12 col-sm-offset-3 col-sm-6 col-md-offset-4 col-md-4">
		        	<h1>Harte Hanks Project Costing System</h1>	
		        	<g:if test="${grails.util.Environment.current.name != 'hh_prod'}"> 
    					<h2>Test Server</h2>
    				</g:if>	        		 
		        		 <g:if test="${flash.message}">
		    				<div class="message">${flash.message}</div>
		  				 </g:if>		  				 
		  				<g:form action="signIn" class="clearfix">
			    			<input type="hidden" name="targetUri" value="${targetUri}" />            
			            	<div class="form-group">
			                	<label>Username</label>
			                    <input type="text" name="username" value="${username}" class="form-control"/>
			                </div>
			                <div class="form-group">
			                	<label>Password </label>
			                   <input type="password" name="password" value="" class="form-control" />
			                </div>
			                <div class="checkbox">
			                    <label>
			                      <g:checkBox name="rememberMe" value="${rememberMe}" /> Remember me
			                    </label>
			                </div>
			                <div class="form-group">
			                	<input type="submit" value="Sign in" class="btn btn-primary" />                    
			                </div>                 
		            	</g:form>       
						<p class="copyright text-center">&copy; Copyright SolutionCorp.com, Inc.</br>Support: <a href="mailto:costsystem@hartehanks.com">costsystem@hartehanks.com</a></p>
		    	</div> <!-- Login form -->
		    </div>
		</div>
	</div>
</body>
</html>

