var parentProjectError = false;
var customerError = false;
var accExecutiveError = false;
$(document).ready(function() {
	
	 $('.searchbutton').click(function() {
	 	   $('#createForm').attr('action', "list").submit();
	 	});	
	var navigationPositionTop = $('.navigation').offset().top;
	$('#hhProjectImportDialog').on('show.bs.modal', function (e) {
		resetProjectImportDialog();		
		$('#projectImportListGridDiv').hide();
		$("#customerExistMessageDiv").hide();
	})
	
	$('#customerCodeDialog').on('show.bs.modal', function (e) {
		resetCustomerCodeDialog();		
		$('#customerCodelistGridDiv').hide();
	})
	
	$('#parentProjectCodeDialog').on('show.bs.modal', function (e) {
		resetparentProjectCodeDialog();		
		$('#parentProjectCodelistGridDiv').hide();
	})
	if(parentProjectError == true) {
		$('#parentProjectTxt').attr("disabled", false);
	}
	if(customerError == true) {
		$('#customerTxt').attr("disabled", false);
	}
	if(accExecutiveError == true) {
		$('#accExecutiveTxt').attr("disabled", false);
	}
	
	$(window).scroll(function () {
		if( $(window).scrollTop() >= navigationPositionTop  && !($('.navigation').hasClass('navigation-scroll'))){
			$('.navigation').addClass('navigation-scroll');
		} else if ($(window).scrollTop() < navigationPositionTop && $('.navigation').hasClass('navigation-scroll')){
			$('.navigation').removeClass('navigation-scroll');
		}
	});
});

function addProductComboInTemplate(data) {	
	$("#projectDtlDivLst").append(data);
	$('form').trigger('rescan.areYouSure');
}
function addServiceDetailTemplate(data, index, productId) {
	//$("#projectDtlDivLst #serviceTbl"+index+" tbody > tr").remove();
	$("#projectDtlDivLst #serviceTbl"+index+" tbody").html("");
	$("#projectDtlDivLst #serviceTbl"+index+" tbody").append(data);	
	$("#projectDetails"+index+"_projectProduct").val(productId);
}

function addServiceRowInTemplate(data, index, serviceIndex, productId) {	
	$( "#serviceTbl"+index +" tbody" ).append(data); 
}



function removeServiceDetailIndex(productIndex, serviceIndex) {	
	$("#removeParentIndex").val(productIndex);
	$("#removeServiceIndex").val(serviceIndex);
}

function removeDfltLink() {
	 var i = $(".projectDetailsDiv").length;
	 if(i<0){
			$("#addProjDfltLink").hide()					
		}		
}
function serviceChange(serviceObj) {
	var cnt = 0;
	var isDublicateRec = false;				
	
	$( ".serviceValid" ).each(function( index ) {				
		  if($(this ).val() == serviceObj.value){
			  cnt ++;
			  if(cnt == 2){
				  isDublicateRec = true	  
				}						  					
		  }					  				  
	});
	if(isDublicateRec) {
		alert($(serviceObj).find('option:selected').text() +" already selected");
		serviceObj.value = "";
	}	
}

function productChange(productId) {
	var isDublicateRec = false;
	var cnt =0;
	$( ".prodCombo" ).each(function( index ) {				
		  if($(this ).val() == productId){
			  cnt ++;
			  if(cnt == 2){
				  isDublicateRec = true	  
				}						  					
		  }					  				  
	});
	if(isDublicateRec) {		
		return false;	
	}
	return true;	
}

function showProductPopup(data) {	
	$('#productCostingPopup').modal('show');
}

function projectSuccessSearch(data) {
	 $('#projectImportListGridDiv').show();	 
}
function setHhProjectDetails(code, name, hhCustId, startDt, endDt, businessUnit) {
	isCustomerExistInDB(code, name, hhCustId, startDt, endDt, businessUnit);
}
function customerCheckMessage(data, code, name, hhCustId, startDt, endDt, businessUnit){	
	if(data) {
		$('#hhProjectImportDialog').modal('hide');
		$("#code").val(code);		
		$("#name").val($("#desc_"+name).val());	
		$("#customer").val(data);
		$("#customerTxt").val(hhCustId);		
		$("#startDate").val(startDt);
		$("#endDate").val(endDt);
		$("#hiddenStartDate").val(startDt);
		$("#hiddenEndDate").val(endDt);
		$("#businessUnit").val(businessUnit);
		$("#customerExistMessageDiv").hide();
	} else {
		$("#custMessageDiv").text("Customer: "+hhCustId+" for selected project: "+code+" does not exist in the system. Please create customer: "+hhCustId+" first.");
		$("#customerExistMessageDiv").show();
	}		
}
function resetProjectImportDialog() {
	$("#dialogProjectId").val('');
	$("#dialogName").val('');
	$("#dialogCustCode").val('');	
	$('#isAssigned').attr('checked', false);
}

function resetCustomerCodeDialog(){
	$('#dialogCustomerCode').val('');
	$('#dialogCustomerName').val('');
}

function customerSuccessSearch(){
	 $('#customerCodelistGridDiv').show();	 
}

function setCustomerCode(customerId, customerCode){
	$('#customerCodeDialog').modal('hide');
	$('#customer').val(customerId);
	$('#customerTxt').val(customerCode);
	setDefaultAccountExecutive(customerId);
}

function resetparentProjectCodeDialog(){
	$('#dialogProjectCode').val('');
	$('#dialogProjectName').val('');
}

function parentProjectSuccessSearch(){
	 $('#parentProjectCodelistGridDiv').show();	 
}

function setParentProjectCode(parentProjectId, parentProjectCode){
	$('#parentProjectCodeDialog').modal('hide');
	$('#parentProject').val(parentProjectId);
	$('#parentProjectTxt').val(parentProjectCode);
}


function clearParentProjectCode() {
	var cd= confirm("Are you sure? You want to clear Parent Project?");
	if(cd) {
		$('#parentProject').val('');
		$('#parentProjectTxt').val('');
		alert("Parent Project is cleared. Click on save button to save record.");
	}
}
function clearCustomerText() {
	var cd= confirm("Are you sure? You want to clear Customer?");
	if(cd) {
		$('#customer').val('');
		$('#customerTxt').val('');
		 $("#accExecutive").val('');
		alert("Customer is cleared.");
	}
}
function hhProjectMasterDetailsDialog() {
	$('#hhProjectMasterDetailsDialog').modal("show");	
}
function projectCostingPopup() {
	$('#projectCostingPopup').modal("show");
}
function onlyDecimal(){
	
	$('.onlyDecimal').css("text-align","right");
	
	$('.onlyDecimal').keypress(function(event) {
		  if ((event.which != 46 || $(this).val().indexOf('.') != -1) &&
		    ((event.which < 48 || event.which > 57) &&
		      (event.which != 0 && event.which != 8))) {
		    event.preventDefault();
		  }
		});
	
	
		$('.onlyDecimal').keyup(function(event) {
		if($(this).val().indexOf('.')!=-1){         
	    	if($(this).val().split(".")[1].length > 2){                
	            if( isNaN( parseFloat( this.value ) ) ) return;
	            this.value = parseFloat(this.value).toFixed(2);
	        }  
	     }   
		});

		$('.onlyDecimal').blur(function() {       
		var value = this.value;
		 
		if(isNaN(value)) { 
			this.value = "0.00";
		} else if(value != '') {
			this.value = parseFloat(value).toFixed(2);
		} 
	});

}
/**
 * Project Manager Dialog Script Start
 */
function projectManagerDialog() {
	resetProjectManagerDialogData();
	$('#projectScreenPMSearchDialog').modal('show');
	$('#projectManagerListGridDiv').hide();
}
function resetProjectManagerDialogData() {
	$('#dialogEmpCode').val('');
	$('#dialogEmpFirstName').val('');
	$('#dialogEmpLastName').val('');
}
function pmSuccessSearch(data) {
	 $('#projectManagerListGridDiv').show();
}
function addProjectMangerIdInDiv(userId) {
	$('#projectScreenPMSearchDialog').modal('hide')	
	$("#managerDiv").append(userId);
}
/**
 * Project Manager Dialog Script End
 */

/**
 * Account Executive Dialog Script Start
 */
function populateDefaultAccountExecutive(data){
	var JSONObj = data;
	$("#accExecutive").val(JSONObj.acctExecutiveId);
	$("#accExecutiveTxt").val(JSONObj.acctExecutiveCode);
	$("#divAccountExecutiveName").html("");
	$('<p>'+JSONObj.acctExecutiveName+'</p>').appendTo("#divAccountExecutiveName");
}

function accExecutivePopup() {	
	resetAcctExecutiveDialogData();
	$('#acctExecutiveSearchDialog').modal('show');
	$('#acctExecutiveListGridDiv').hide();
}

function resetAcctExecutiveDialogData() {
	$('#dialogAcctExecutiveCode').val('');
	$('#dialogAcctExecutiveName').val('');
}

function acctExecutiveSuccessSearch(data) {
	 $('#acctExecutiveListGridDiv').show();
}

function setAcctExecutiveCode(id, code, name) {
	$('#acctExecutiveSearchDialog').modal('hide');	
	$('#accExecutive').val(id);
	$('#accExecutiveTxt').val(code);
	$("#divAccountExecutiveName").html("");
	$('<p>'+name+'</p>').appendTo('#divAccountExecutiveName');
}

function clearAccountExecutive() {
	var cd= confirm("Are you sure? You want to clear Account Executive?");
	if(cd) {
		$('#accExecutive').val('');
		$('#accExecutiveTxt').val('');
		$("#divAccountExecutiveName").html("");
		alert("Account Executive is cleared. Click on save button to save record.");
	}
}
/**
 * Account Executive Dialog Script End
 */
function setAccountExecutive(data) {
	if(data !='null'){
		$('#accExecutive').val(data);
	} else {
		$('#accExecutive').val('');
	}
}

function resetAcctExecutiveDialogDataWithCheckBox(){
	$('#dialogAcctExecutiveCode').val('');
	$('#dialogAcctExecutiveName').val('');
	$('#projectManagerChecked').attr('checked', false);
}
function revenueVarianceCal(index) {
	var plannedRevenue = $("#projectProductDetails"+index+"_plannedRevenue").val();
	var actualRevenue = $("#projectProductDetails"+index+"_actualRevenue").val();
	$("#projectProductDetails"+index+"_varianceRevenue").val(actualRevenue-plannedRevenue);
}
