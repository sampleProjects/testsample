<div class="col-sm-12">
	<div class="panel-group accordion-panel-top" id="accordion">
    	<div class="panel panel-default">
        	<div class="panel-heading">
        		<h4 class="panel-title">
        			<a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">	
        			<span class="icon-section" style="display: none;">
        			<i class="fa fa-plus-circle"></i>
        			<i class="fa fa-minus-circle"></i> </span>
        	  Assigned Projects</a>
        	   <span class="small-text">(${assignedProjects?.size()})</span>
                </h4>
            </div>
	        <div id="collapseOne" class="panel-collapse collapse">
	        	<div class="panel-body">   
					<table class="table table-hover">
						<thead>
							<tr>
								<th><g:message code="etech.laborActivityGroup.code.label" default="Code" /></th>

								<th><g:message code="etech.laborActivityGroup.description.label" default="Name" /></th>
								
							</tr> 
						</thead>
						<tbody>
							<g:if test="${assignedProjects?.size() > 0}">
							<g:each in="${assignedProjects}" status="i" var="projectInstance">
								<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

									<td>${fieldValue(bean: projectInstance, field: "code")}</td>

									<td>${fieldValue(bean: projectInstance, field: "name")}</td>

								</tr>
							</g:each>
							</g:if>
							<g:else>	
							<tr><td colspan="2" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
						</g:else>
						</tbody>
					</table>           			
				</div>
			</div>
		</div>
	</div>
</div>