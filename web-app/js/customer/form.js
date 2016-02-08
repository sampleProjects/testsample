var acctExecutiveError = false;
var salesExecutiveError = false;
var industryError = false;
$(document).ready(function() {	
	$('#customerImportDialog').on('show.bs.modal', function (e) {
		resetCustomerImportDialog();		
		$('#custImportListGridDiv').hide();	  					
	})
	
	$('#salesExecutiveSearchDialog').on('show.bs.modal', function (e) {
		resetSalesExecutiveDialogData();		
		$('#salesListGridDiv').hide();	  					
	})
	
	$('#industrySearchDialog').on('show.bs.modal', function (e) {
		resetIndustryDialogData();		
		$('#industryListGridDiv').hide();	  					
	})
	
	if(acctExecutiveError == true) {
		$('#acctExecutiveTxt').attr("disabled", false);
	}
	
	if(salesExecutiveError == true) {
		$('#salesExecutiveTxt').attr("disabled", false);
	}
	
	if(industryError == true) {
		$('#industryTxt').attr("disabled", false);
	}
	
});

function clearAccountExecutive() {
	var cd= confirm("Are you sure? You want to clear Account Executive?");
	if(cd) {
		$('#acctExecutive').val('');
		$('#acctExecutiveTxt').val('');
		alert("Account Executive is cleared. Click on save button to save record.");
	}
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
	$('#acctExecutive').val(id);
	$('#acctExecutiveTxt').val(code);
}


function salesExecutivePopup() {	
	resetSalesExecutiveDialogData();
	$('#salesExecutiveSearchDialog').modal('show');
	$('#salesExecutiveListGridDiv').hide();
}

function resetSalesExecutiveDialogData() {
	$('#dialogSalesExecutiveCode').val('');
	$('#dialogSalesExecutiveLastName').val('');
	$('#dialogSalesExecutiveFirstName').val('');
}

function salesExecutiveSuccessSearch(data) {
	 $('#salesExecutiveListGridDiv').show();
}

function setSalesExecutiveCode(id, code) {
	$('#salesExecutiveSearchDialog').modal('hide');	
	$('#salesExecutive').val(id);
	$('#salesExecutiveTxt').val(code);
}

function clearSalesExecutive() {
	var cd= confirm("Are you sure? You want to clear Sales Executive?");
	if(cd) {
		$('#salesExecutive').val('');
		$('#salesExecutiveTxt').val('');
		alert("Sales Executive is cleared. Click on save button to save record.");
	}
}
function confirmDelete() {
	var cd= confirm("This will delete the record.\n Are you sure?");
	if(!cd) {
		alert("The record is not deleted");
		return false;
	}
}

/* Industry Start */
function industryPopup() {	
	resetIndustryDialogData();
	$('#industrySearchDialog').modal('show');
	$('#industryListGridDiv').hide();
}

function resetIndustryDialogData() {
	$('#dialogIndustryCode').val('');
	$('#dialogIndustryName').val('');
}

function industrySuccessSearch(data) {
	 $('#industryListGridDiv').show();
}

function setIndustryCode(id, code) {
	$('#industrySearchDialog').modal('hide');	
	$('#industry').val(id);
	$('#industryTxt').val(code);
}

function clearIndustryExecutive() {
	var cd= confirm("Are you sure? You want to clear Industry?");
	if(cd) {
		$('#industry').val('');
		$('#industryTxt').val('');
		alert("Industry is cleared. Click on save button to save record.");
	}
}
/* Industry Start */

function validatePhone(_phone) {
   	var fldVal = _phone;
   	var i = 0;
   	var nphone = "";
	   
	for (i = 0; i < fldVal.value.length; i++) {
   		if (fldVal.value != "") {
   			if(!isNaN(fldVal.value.charAt(i))) {
				nphone = nphone + fldVal.value.charAt(i);
			}
		 }
	}

	if(fldVal == null) return true;
	if(fldVal.value == "") return true;
	
	if(isNaN(nphone) || (nphone.length != 10)) {
		fldVal.focus();
		fldVal.select();
		alert("Enter a valid 10-digit phone number");
		return false;
	}
	else {
		return false;
	}
}

function validateFax(_phone) {
   	var fldVal = _phone;
   	var i = 0;
   	var nphone = "";
	   
	for (i = 0; i < fldVal.value.length; i++) {
   		if (fldVal.value != "") {
   			if(!isNaN(fldVal.value.charAt(i))) {
				nphone = nphone + fldVal.value.charAt(i);
			}
		 }
	}

	if(fldVal == null) return true;
	if(fldVal.value == "") return true;
	
	if(isNaN(nphone) || (nphone.length != 10)) {
		fldVal.focus();
		fldVal.select();
		alert("Enter a valid 10-digit Fax number");
		return false;
	}
	else {
		return false;
	}
}

function isNumber(field) {
    if (isNaN(field.value)) {
        alert("Invalid Number, enter again.");
	   field.select();
        field.focus();
        return false;
    }
    else {
        return true;
    }
}

function custSuccessSearch(data) {
	 $('#custImportListGridDiv').show();
}

function setCustomerDtl(code, index) {
	$('#customerImportDialog').modal('hide')
	$("#code").val(code);	
	$("#name").val($("#customerName_"+index).val());		
}

function resetCustomerImportDialog() {
	$("#dialogCustomerId").val('');
	$("#dialogName").val('');		 
	$('#isAssignedCustShow').attr('checked', false);
}