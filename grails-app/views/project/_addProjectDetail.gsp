<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
	<tr id="projectDetails${i}" class="serviceDetailsDiv_${i}">	
		<td rowspan="3" class="services-combo">	
			<span id="serviceComboDiv_${i}">						
				<g:select id="projectProductDetails${i}_projectServiceDetails${j}_service" onChange="serviceChange(this);" name="projectProductDetails[${i}].projectServiceDetails[${j}].service.id" from="${serviceList}" optionKey="id" optionValue="code" value="${projectDetails?.service?.id }" class="form-control serviceValid" noSelection="['': '--Select--']" />
			</span>
			
			<div class="col-sm-12 text-center margintopbottom15">
				<div class="display-block">
					<a onclick="addServiceDetailRow('${i}','${j}');"  class="fa fa-plus project-icons pull-left action-plus"></a>
					<g:submitToRemote id="removeProjectDtl" class="remove-img pull-left marginleft5" url="[action: 'removeProjectDtl']" method="post"  before ="removeServiceDetailIndex('${i}', '${j}')" update="projectDtlDivLst" value="" />
				</div>
			</div>
		</td>
		
		<td><span class="planned-color">Plan</span></td>
		<td class="text-right">0.00</td>
		<g:if test="${session['logedInUser']?.hoursOnly == false}">	
		<td class="text-right">0.00</td>
		
		<td class="text-right">0.00</td>
		
		<td class="text-right">0.00</td>
		
		<td class="text-right">0.00</td>
		</g:if>
		<td><span class="planned-color">Start</span></td>
		
		<td>
			<g:render template="/common/dateComponent" model="['fieldName': 'projectProductDetails['+i+'].projectServiceDetails['+j+'].scheduleStartDate', 'fieldValue': projectDetails?.scheduleStartDate, 'cssClass': 'project-datepicker']"></g:render>		
		</td>
		
		<td>
			<g:render template="/common/dateComponent"	model="['fieldName': 'projectProductDetails['+i+'].projectServiceDetails['+j+'].reviseStartDate', 'fieldValue': projectDetails?.reviseStartDate, 'cssClass': 'project-datepicker']"></g:render>			
		</td>
		
		<td>
			<g:render template="/common/dateComponent" model="['fieldName': 'projectProductDetails['+i+'].projectServiceDetails['+j+'].actualStartDate', 'fieldValue': projectDetails?.actualStartDate, 'cssClass': 'project-datepicker']"></g:render>			
		</td> 
	</tr>	
	<tr>
			<td><span class="planned-color">Act</span></td>
			<td class="text-right">0.00</td>
			<g:if test="${session['logedInUser']?.hoursOnly == false}">
				<td class="text-right">0.00</td>
				
				<td class="text-right">0.00</td>
				
				<td class="text-right">0.00</td>
				
				<td class="text-right">0.00</td>
			</g:if>
			
			<td><span class="planned-color">End</span></td>
			
			<td>
				<g:render template="/common/dateComponent" model="['fieldName': 'projectProductDetails['+i+'].projectServiceDetails['+j+'].scheduleCompDate', 'fieldValue': projectDetails?.scheduleCompDate, 'cssClass': 'project-datepicker']"></g:render>		
			</td>
		
			<td>
				<g:render template="/common/dateComponent"	model="['fieldName': 'projectProductDetails['+i+'].projectServiceDetails['+j+'].reviseCompDate', 'fieldValue': projectDetails?.reviseCompDate, 'cssClass': 'project-datepicker']"></g:render>		
			</td>
		
			<td>
				<g:render template="/common/dateComponent"	model="['fieldName': 'projectProductDetails['+i+'].projectServiceDetails['+j+'].actualCompDate', 'fieldValue': projectDetails?.actualCompDate, 'cssClass': 'project-datepicker']"></g:render>			
			</td>
		</tr>
		<tr>	
			
			<td><span class="planned-color">Var</span></td>
			<td class="text-right">0.00</td>
			<g:if test="${session['logedInUser']?.hoursOnly == false}">
				<td class="text-right">0.00</td>
				
				<td class="text-right">0.00</td>
				
				<td class="text-right">0.00</td>
				
				<td class="text-right">0.00</td>
			</g:if>
			<td></td>
		
			<td></td>	
		
			<td></td>
		</tr>	
	