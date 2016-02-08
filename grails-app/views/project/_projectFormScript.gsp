
<script type="text/javascript">		

/**
 * This Function used to add Product Combo
 * I : Parent Index
 */
function addProductCombo(){
				
	var validate = false;

	$( ".prodCombo" ).each(function( index ) {
		  if($(this ).val() == ''){
			  validate = true;					
		  }					  				  
	});

	var i = $(".productDetailsDiv").length;	
			
	if(validate == true) {
							
		alert("Please select Product");	
		$("#projectProductDetails"+i+"_projectProduct").focus();				
		return false;
			  		
	} else {			
		<g:remoteFunction controller="project" action="addProductComboAjax" params="'i='+i"  onSuccess='addProductComboInTemplate(data)'/>	
	}
}
			
function addProjectServiceAjax(productId, i) {	
									
	if($("#projectProductDetails"+i+"_projectProduct").val() == '') {
					
		$("#projectDtlDivLst #serviceTbl"+i+" tbody").html("");
		return false;
	}

	if(productChange(productId) == true) {	
					
		<g:remoteFunction controller="project" action="addProjectServiceAjax" params="'productId='+productId+'&i='+i+'&j='+0"  onSuccess='addServiceDetailTemplate(data, i, productId)'/>
	} else {
		alert($("#projectProductDetails"+i+"_projectProduct").find('option:selected').text()+" already selected");							
		//$("#projectProductDetails"+i+"_projectProduct").val($("#projectProductDetails"+i+"_projectProduct").find('option:selected').val());
										
	}
}
			
function addServiceDetailRow(index, serviceIndex) {
	
	var serviceComboSize = $("#serviceTbl"+index +" .serviceValid").length;
	var serviceComboOpnSize = $("#projectProductDetails"+index+"_projectServiceDetails"+serviceIndex+"_service option").length - 1;
				
	if($("#projectProductDetails"+index+"_projectServiceDetails"+serviceIndex+"_service").val()=='') {
					
		alert("Please select Service");
		$("#projectProductDetails"+index+"_projectServiceDetails"+serviceIndex+"_service").focus();
		return false;
	}

	if(serviceComboSize == serviceComboOpnSize) {		
		alert("No more services available");		
	} else {							
		var nextServiceIndex = $(".serviceDetailsDiv_"+index).length;
		
		var productId = $("#projectProductDetails"+index+"_projectProduct").val();
		
		<g:remoteFunction controller="project" action="addServiceDetailTemplate" params="'productId='+productId+'&i='+index+'&j='+nextServiceIndex" onSuccess='addServiceRowInTemplate(data, index, serviceIndex, productId)'/>
					
	}
}

function isCustomerExistInDB(code, name, hhCustId, startDt, endDt, businessUnit) {
	<g:remoteFunction controller="project" action="isCustomerExistInDB" params="'customerCode='+hhCustId" onSuccess='customerCheckMessage(data, code, name, hhCustId, startDt, endDt, businessUnit)'/>
}
						
function productCostingPopup(projectProductDtlId) {	
	<g:remoteFunction controller="project" action="productCostingPopup" update="productCostingPopupDiv" params="'projectProductDtlId='+projectProductDtlId" onSuccess='showProductPopup(data)'/>
}
/* */
function projectMangerSetInSession(employeeId, projectId) {	
	<g:remoteFunction controller="project" action="addProjectMangerInSession" update="managerDiv" params="'employeeId='+employeeId+'&projectId='+projectId" />
	$('#projectScreenPMSearchDialog').modal('hide')	
}

function removeProjectManager(employeeId, projectId) {	
	<g:remoteFunction controller="project" action="removeProjectMangerFromSession" update="managerDiv" params="'employeeId='+employeeId+'&projectId='+projectId" />	
}

function setDefaultAccountExecutive(customerId){
	<g:remoteFunction controller="project" action="setDefaultAccountExecutive" params="'customerId='+customerId"  onSuccess='populateDefaultAccountExecutive(data)'/>
}
function checkAccountExecutive(accountExecutiveCode){
	if(accountExecutiveCode.value.trim().length > 0 && accountExecutiveCode.value != null){
		<g:remoteFunction controller="project" action="checkAccountExecutive" params="'accountExecutiveCode='+accountExecutiveCode.value"  onSuccess='setAccountExecutive(data)'/>
	}else {
		$('#accExecutive').val('');
	}
}
</script>