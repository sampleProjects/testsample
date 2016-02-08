<%@ page import="grails.util.Environment" %> 
<div id="page-content-wrapper" class="clearfix"> <a id="menu-toggle" class="btn btn-default" href="#menu-toggle"> 
	<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </a>
	<g:if test="${grails.util.Environment.current.name != 'hh_prod'}"> 
    	<div class="top-navigation-rht col-sm-6 col-md-8 col-lg-7 pull-right text-left">Test Server</div>
    </g:if>
    <div class="top-navigation-rht col-sm-6 col-md-8 col-lg-6 pull-right  text-right">
      <div class="row">
        <div class="dropdown hidden-md hidden-sm hidden-lg">
          <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">             
          </button>
          
        </div>
        <div class="col-sm-8 col-md-12 pull-right">
            <div class="welcome-txt hidden-xs"><span>Welcome  ${session['logedInUser']?.employee?.getEmployeeName()?:session['logedInUser']?.username}!<span class="profile-txt"></span></span> </div>
            <div class="notification" style="display: none;"> <span class="message"><i class="fa fa-fw"></i><span class="badge">12</span></span> <span class="bell"><i class="fa fa-fw"></i><span class="badge">2</span></span> </div>
            <div class="logout"> <span><a class="logout-txt"><i class="fa fa-fw"></i><shiro:user><g:link controller="auth" action="signOut" class="logoutLink">Logout</g:link></shiro:user></a></span> </div>
        </div>
      </div>
    </div>