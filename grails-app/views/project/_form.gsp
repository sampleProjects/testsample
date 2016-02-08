<%@ page import="org.solcorp.etech.RoleType"%>
<%@ page import="org.solcorp.etech.Project" %>
<%@ page import="org.solcorp.etech.Constants" %>
<%@page import="org.solcorp.etech.utils.MiscUtils"%>
<%@ page import="org.solcorp.etech.PermissionType" %>
<%@ page import="org.solcorp.etech.ProjectStatusType" %>
<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>

<g:hiddenField name="businessUnit" value="${projectInstance?.businessUnit}"/>
<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_PRICING.name()}">
<g:set value="true" var="canViewPricing"></g:set>
</shiro:hasPermission>
<div class="col-sm-4 col-md-2">
	<div class="form-group clearfix ${projectInstance?.id ? 'edit-code' : ''}">
	    <div>
		<label for="code" class="control-label"><g:message code="etech.project.id.label" default="ID" /><span class="required-indicator">*</span></label>
	    </div>   
	    <g:textField name="code" onblur="keyFilter(this);" value="${projectInstance?.code}" maxlength="${Constants.PROJECT_CODE_LENGTH}" class="form-control fieldcontain ${hasErrors(bean: projectInstance, field: 'code', 'error')} pull-left" disabled="${editMode ?: disabled}"/>
	    <g:if test="${projectInstance?.id}"> <span onClick="enableCodeField('code');" class="pull-left fa fa-pencil" data-toggle="tooltip" data-original-title="Edit : Code" data-placement="top"></span> </g:if>
	</div>
</div>

<div class="col-sm-4 col-md-4">
  <div class="form-group">
    <label for="name" class="control-label">
      <g:message code="project.name.label" default="Name" />
      <span class="required-indicator">*</span> </label>
    <g:textField name="name" value="${projectInstance?.name}" class="form-control fieldcontain ${hasErrors(bean: projectInstance, field: 'name', 'error')} required" maxlength="${Constants.PROJECT_NAME_LENGTH}"/>
  </div>
</div>

<div class="bg-color col-md-4">
  <div class="row">
    <div class="col-sm-4 col-md-6">
      <div class="form-group">
        <label for="hiddenStartDt" class="control-label">
          <g:message code="project.start.date.label" default="Start Date" />
        </label>
        <g:render template="/common/dateComponent" model="['fieldName': 'startDate', 'fieldValue': projectInstance?.startDate]"></g:render>
      </div>
    </div>
    <div class="col-sm-4 col-md-6">
      <div class="form-group">
        <label for="hiddenEndDate" class="control-label">
          <g:message code="project.end.date.label" default="End Date" />
        </label>
        <g:render template="/common/dateComponent" model="['fieldName': 'endDate', 'fieldValue': projectInstance?.endDate]"></g:render>
      </div>
    </div>
  </div>
</div>

<g:if test="${projectInstance?.lastTrx}">
	<div class="col-sm-4 col-md-2">
	  <div class="form-group">
	    <label for="projectCategory" class="control-label">
			<g:message code="etech.project.end.date.label" default="Last Trx" />
	    </label>
	    <div class="clearfix"></div>    
	    ${DateFormatUtils.getStringFromDate(projectInstance?.lastTrx)}    
	  </div>
	</div>
</g:if>

<div class="clearfix"></div>
<hr/>

<div class="col-sm-4 col-md-2">
  <div class="form-group">
    <label for="projectCategory" class="control-label">
      <g:message code="project.projectCategory.label" default="Project Category" />
    </label>
    <g:select id="projectCategory" name="projectCategory" from="${org.solcorp.etech.ProjectCategory.list()}" optionKey="id" optionValue="category" value="${projectInstance?.projectCategory?.id}" class="selectpicker form-control many-to-one  fieldcontain ${hasErrors(bean: projectInstance, field: 'projectCategory', 'error')} " noSelection="['': '--Select--']" />
  </div>
</div>

<div class="col-sm-4 col-md-2">
  <div class="form-group">
    <label for="projectType" class="control-label">
      <g:message code="project.projectType.label" default="Project Type" />
      <span class="required-indicator">*</span> </label>
    <g:select name="projectType" from="${org.solcorp.etech.ProjectType?.values()}" keys="${org.solcorp.etech.ProjectType.values()*.name()}" value="${projectInstance?.projectType?.name()}" noSelection="['': '--Select--']" class="selectpicker form-control fieldcontain ${hasErrors(bean: projectInstance, field: 'projectType', 'error')} " />
  </div>
</div>

<div class="col-sm-4 col-md-2">
  <div class="form-group">
    <label for="billCodeType" class="control-label">
      <g:message code="project.billCodeType.label" default="Bill Code Type" />
      <span class="required-indicator">*</span> </label>
    <g:select name="billCodeType" from="${org.solcorp.etech.BillCodeType.ProjectBillCodeTypeList()}" keys="${org.solcorp.etech.BillCodeType.ProjectBillCodeTypeList()*.name()}" value="${projectInstance?.billCodeType?.name()}" noSelection="['': '--Select--']" class="selectpicker form-control fieldcontain ${hasErrors(bean: projectInstance, field: 'billCodeType', 'error')} " />
  </div>
</div>

<div class="col-sm-4 col-md-2">
  <div class="form-group">
    <label for="status" class="control-label">
      <g:message code="project.status.label" default="Status" />
      <span class="required-indicator">*</span> </label>
    <g:select name="status" from="${ProjectStatusType.projectStatusTypeList()}" keys="${ProjectStatusType.values()*.name()}" value="${projectInstance?.status?.name()}" class="selectpicker form-control fieldcontain ${hasErrors(bean: projectInstance, field: 'status', 'error')} required" noSelection="['': '--Select--']"/>
  </div>
</div>

<div class="col-sm-4 ${editMode ? 'col-md-3': 'col-md-3' }">
	<div class="form-group edit-code ${editMode ? 'parent-project' :'parent-project' }  "> 
    	<label for="parentProjectTxt" class="control-label" style="cursor: pointer; text-decoration: underline;" data-toggle="modal" data-target="#parentProjectCodeDialog">
			<g:message code="project.parentProject.label" default="Parent Project" />
    	</label>
    	
    	<g:hiddenField name="parentProject" id="parentProject" value="${projectInstance?.parentProject?.id}" />
    	
    	<g:textField name="parentProjectTxt" id="parentProjectTxt" value="${projectInstance?.parentProject?.code?:projectInstance?.parentProjectTxt}" class="form-control fieldcontain ${hasErrors(bean: projectInstance, field: 'parentProjectTxt', 'error')} pull-left required" disabled="disabled"/>
    	
    	<span onClick="clearParentProjectCode();" class="pull-left fa fa-remove cursor-pointer" data-toggle="tooltip" data-original-title="Clear: Parent Project" data-placement="top"></span>
    	
    	<g:if test="${!projectInstance?.id}">
    		<span onClick="enableCodeField('parentProjectTxt');" class="pull-left fa fa-pencil cursor-pointer" data-toggle="tooltip" data-original-title="Edit: Parent Project" data-placement="top"></span>
    	</g:if>    
    	
    	<g:if test="${projectInstance?.id}">
			<g:link class="pull-left fa fa-tree cursor-pointer isDirtyCheck" action="treeView"  params="[code: projectInstance?.code]" data-toggle="tooltip" data-original-title="Tree View" data-placement="top" />    	
    	</g:if> 
   </div>
</div>

<div class="clearfix"></div>
<hr/>


	<div class="col-sm-4 col-md-3">
		<div class="form-group edit-code customer-input ${editMode ? 'customer-input-edit-code' : '' }">
			
			<label for="customerTxt" class="control-label" style="cursor: pointer; text-decoration: underline;" data-toggle="modal" data-target="#customerCodeDialog">
				<g:message code="project.customer.label" default="Customer" />
	  		</label>
		  		
	  		<label for="customerTxt" class="control-label"><span class="required-indicator">*</span></label>		  
	  		<g:hiddenField name="customer" id="customer" value="${projectInstance?.customer?.id}" />
		  		
	  		<g:textField name="customerTxt" id="customerTxt" value="${projectInstance?.customer?.code ?: projectInstance?.customerTxt}" class="form-control fieldcontain pull-left ${hasErrors(bean: projectInstance, field: 'customer', 'error')} required" disabled = "disabled"/>
		  		
	  		<span onClick="clearCustomerText();" class="pull-left fa fa-remove cursor-pointer" data-toggle="tooltip" data-original-title="Clear: Customer" data-placement="top"></span>
		  		
	  		<g:if test="${!projectInstance?.id}">
    			<span onClick="enableCodeField('customerTxt');" class="pull-left fa fa-pencil cursor-pointer" data-toggle="tooltip" data-original-title="Edit: Customer" data-placement="top"></span>
    		</g:if> 
		</div>
	</div>
		
	<div class="col-sm-4 col-md-3">
		<div class="form-group edit-code">
			<label for="accExecutive" class="control-label text-underline" onclick="accExecutivePopup()"><g:message code="app.accountDirector.label" default="Account Director" /></label>	  		
	  		<label for="code" class="control-label"><span class="required-indicator">*</span></label>
	  		<g:hiddenField name="accExecutive" value="${projectInstance?.accExecutive?.id}" />
	  		<g:textField name="accExecutiveTxt" value="${projectInstance?.accExecutive?.code ?: projectInstance?.accExecutiveTxt}"  class="form-control fieldcontain ${hasErrors(bean: projectInstance, field: 'accExecutive', 'error')} pull-left" disabled="${editMode ?: disabled}"/>
	  		<g:if test="${!session['logedInUser']?.roles*.name.contains(RoleType.ROLE_ACCOUNT_DIRECTOR.name())}">
	  			<span onClick="clearAccountExecutive();" class="pull-left fa fa-remove cursor-pointer" data-toggle="tooltip" data-original-title="Clear: Account Director" data-placement="top"></span>
	  		</g:if>
		</div>
		<div id="divAccountExecutiveName" style="margin-top: 37px; font-size: 12px;">
			${projectInstance?.accExecutive?.getEmployeeName()?: '' }
		</div>
	</div>
	  	
	<div class="col-sm-4 col-md-6">
		<div class="form-group">
			<label class="control-label text-underline" onclick="projectManagerDialog();"><g:message code="etech.project.project.manager.label" default="Project Manager" /></label>		  	
	  		<label class="control-label"><span class="required-indicator">*</span></label>
	  		<div id="managerDiv" class="project-manager-div ${hasErrors(bean: projectInstance, field: 'projectEmployees', 'error')}"><g:render template="ajaxManagerSessionDiv"></g:render></div>		  		
		</div>
	</div>
	  	
	<div class="clearfix"></div>	  
  	<hr/>
  	
  	<g:if test="${projectInstance?.id}">
	<div class="col-sm-12">    
		<h3 class="table-heading clearfix"><span class="pull-left marginright10">Product Summary</span>				          
				<span data-toggle="tooltip" data-placement="left" title="Project Detail" class="pull-left prod-costing-action cursor-pointer" onclick="projectCostingPopup();"><span class="text-left fa fa-info table-icon"></span></span>			
		</h3>	    
	    <table class="table table-striped text-center">
	      <thead>
	        <tr>
	          <th class="text-center"><g:set value="${projectInstance?.getProductSummary()}" var="getProductsummaryCall"></g:set></th>
	          <g:if test="${session['logedInUser']?.hoursOnly == false}">	          
		          <th class="text-right"><g:message code="etech.project.labor.label" default="Labor" /></th>
		          <th class="text-right"><g:message code="etech.project.expense.label" default="Expense" /></th>
		          <th class="text-right"><g:message code="etech.project.overhead.label" default="O/H" /></th>
		          <th class="text-right"><g:message code="etech.project.total.label" default="Total" /></th>
		          <g:if test="${canViewPricing}">		          
			          <th class="text-right"><g:message code="etech.project.revenue.label" default="Revenue" /></th>
			          <th class="text-right"><g:message code="etech.project.profit.label" default="Profit" /></th>
			          <th class="text-right"><g:message code="etech.project.profit.percentage.label" default="Profit %" /></th>
			      </g:if>	   
	          </g:if>       
	          <th class="text-right"><g:message code="etech.project.hours.label" default="Hours" /></th>	          
	        </tr>
	      </thead>
	      <tbody>
	        <tr>
	          <td><g:message code="etech.project.planned.label" default="Planned" /></td>
	          <g:if test="${session['logedInUser']?.hoursOnly == false}">	          	          
		          <td class="text-right">${projectInstance?.plannedLaborTotal}</td>
		          <td class="text-right">${projectInstance?.plannedExpenseTotal}</td>
		          <td class="text-right">${projectInstance?.plannedStdOHCostTotal}</td>
		          <td class="text-right">${projectInstance?.plannedTotal}</td>
		          <g:if test="${canViewPricing}">
			          <td class="text-right">${projectInstance?.plannedRevenueTotal}</td>
			          <td class="text-right">${projectInstance?.plannedProfit}</td>
			          <td class="text-right">${MiscUtils.removePrecision(projectInstance?.plannedProfitPercentage)}</td>
			      </g:if>
		       </g:if>
	          <td class="text-right">${projectInstance?.plannedHoursTotal}</td>
	        </tr>
	       
		        <tr>
		          <td><g:message code="etech.project.actual.label" default="Actual" /></td>
		          <g:if test="${session['logedInUser']?.hoursOnly == false}">		          
			          <td class="text-right">${MiscUtils.removePrecision(projectInstance?.actualLaborTotal)}</td>
			          <td class="text-right">
			          <g:if test="${projectInstance?.actualExpenseTotal && projectInstance?.actualExpenseTotal > 0.00}">				          			          
				          	${projectInstance?.actualExpenseTotal}				          
			          </g:if>
			          <g:else>${projectInstance?.actualExpenseTotal}</g:else>
			          </td>
			          <td class="text-right">${MiscUtils.removePrecision(projectInstance?.actualStdOHCostTotal)}</td>
			          <td class="text-right">${MiscUtils.removePrecision(projectInstance?.actualTotal)}</td>
			          <g:if test="${canViewPricing}">
				          <td class="text-right">${projectInstance?.actualRevenueTotal}</td>
				          <td class="text-right">${MiscUtils.removePrecision(projectInstance?.actualProfit)}</td>
			          	  <td class="text-right">${MiscUtils.removePrecision(projectInstance?.actualProfitPercentage)}</td>
			         </g:if>
		          </g:if>
	          	  <td class="text-right">${MiscUtils.removePrecision(projectInstance?.actualHoursTotal)}</td>	          
		        </tr>
		        <tr>
		          <td><g:message code="etech.project.variance.label" default="Variance" /></td>		
		          <g:if test="${session['logedInUser']?.hoursOnly == false}">          
			          <td class="text-right ${projectInstance?.varianceLaborTotal > 0.00 == false ?:'red-text'}">${MiscUtils.removePrecision(projectInstance?.varianceLaborTotal)}</td>
			          <td class="text-right ${projectInstance?.varianceExpenseTotal > 0.00 == false ?:'red-text'}">${projectInstance?.varianceExpenseTotal}</td>
			          <td class="text-right ${projectInstance?.varianceOHCostTotal > 0.00 == false ?:'red-text'}">${MiscUtils.removePrecision(projectInstance?.varianceOHCostTotal)}</td>
			          <td class="text-right ${projectInstance?.varianceTotal > 0.00 == false ?:'red-text'}">${MiscUtils.removePrecision(projectInstance?.varianceTotal)}</td>
			          <g:if test="${canViewPricing}">
				          <td class="text-right ${projectInstance?.varianceRevenueTotal > 0.00 == false ?:'red-text'}"">${projectInstance?.varianceRevenueTotal}</td>
				          <td class="text-right ${projectInstance?.varianceProfit > 0.00 == false ?:'red-text'}">${MiscUtils.removePrecision(projectInstance?.varianceProfit)}</td>
				          <td class="text-right ${projectInstance?.varianceProfitPercentage > 0.00 == false ?:'red-text'}">${MiscUtils.removePrecision(projectInstance?.varianceProfitPercentage)}</td>
				      </g:if>
			      </g:if>
		          <td class="text-right ${projectInstance?.varianceHoursTotal > 0.00 == false ?:'red-text'}">${MiscUtils.removePrecision(projectInstance?.varianceHoursTotal)}</td>
		        </tr>		    
	      </tbody>
	    </table>	   
	</div>
	</g:if>
	<div class="col-sm-12 col-md-6">
		<div class="form-group">
			<label for="manager" class="control-label"><g:message code="etech.project.comments.label" default="Comments" /></label>
			<g:textArea name="comments" value="${projectInstance?.comments}" class="form-control"></g:textArea>
		</div>
	</div>
	<div class="col-sm-12 col-md-6">
		<div class="form-group">
			<g:if test="${editMode}">		
				<div onclick="hhProjectMasterDetailsDialog();" class="pull-right addProjDfltLink btn btn-success">
				      <i class="fa fa-files-o marginright5"></i> <g:message code="etech.project.ps.project.master.details" default="PS Project Master"/>
				</div>
			</g:if>
		</div>
	</div>
		
  	<div class="clearfix"></div>
  	<hr/>

	<div class="col-sm-12">
  	
	  	<h3 class="page-heading pull-left">
	    	<g:message code="default.project.product.details.label" default= "Product Details" />
	  	</h3>
	  	
	  	<a class="addProjDfltLink pull-right btn btn-success" id="addProjDfltLink" onclick="addProductCombo();"><i class="glyphicon glyphicon-plus"></i>
	  		<g:message code="project.product.label" default="Add Product" />
	  	</a>
	  	
	  	<div class="clearfix"></div>
		<g:set value="${projectInstance?.projectProductDetails}" var="projectProductDetailsList"></g:set>  	
	  	<g:hiddenField name="removeServiceIndex" id="removeServiceIndex" value="" />
	 	<g:hiddenField name="removeParentIndex" id="removeParentIndex" value="" />
	  	
	  	<div id="projectDtlDivLst">
	    	<g:render template="addProjectDetailList"/>
	  	</div>	  	
	</div>
	
<g:render template="projectCostingPopup"/>
<g:render template="projectFormScript"/>