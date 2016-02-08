$(document).ready(function() {	
	var hourseOnly = false;
	var employeeTxtError = false;
	
	if(employeeTxtError == true) {
		$('#employeeTxt').attr("disabled", false);
	}
	
	if($("#roles").val() == "ROLE_PROJECT_MANAGER"){
		$('#hoursOnlyDiv').show();
		if ($('#hoursOnly').is(":checked"))
		{
			hourseOnly = true;
		}	
	}
	
	$("#tsApprove").change(function() {
		if($("#roles").val() == "ROLE_TIMESHEET" ) {
			if($(this).val() == "Y") {
				alert("Timesheet User can not approve timesheets");	
				$(this).val("N");
			}
		}
	});
	$("#roles").change(function() {
		
		if($(this).val() == "ROLE_TIMESHEET" ) {			
				$("#tsApprove").val("N");			
		}
		
		if($(this).val() == "ROLE_PROJECT_MANAGER" ) {			
			$('#hoursOnlyDiv').show();
			if(hourseOnly == false){
				$('#hoursOnly').attr('checked', false);
			}
						
		} else {
			$('#hoursOnlyDiv').hide();
			$('#hoursOnly').attr('checked', false);
		}
		
	});
	
});

function employeePopup() {
	resetEmployeeDialogData();
	$('#employeeSearchDialog').modal('show');
	$('#employeeListGridDiv').hide();
}

function resetEmployeeDialogData() {
	$('#dialogEmployeeCode').val('');
	$('#dialogEmployeeLastName').val('');
	$('#dialogEmployeeFirstName').val('');
	$('#dialogEmployeePayDept').val('');
}
function employeeSuccessSearch(data) {
	 $('#employeeListGridDiv').show();
} 
function setEmployeeCode(id, code){
	$('#employeeSearchDialog').modal('hide');	
	$('#employee').val(id);
	$('#employeeTxt').val(code);	
}
function clearEmployee() {
	var cd= confirm("Are you sure? You want to clear Employee ID?");
	if(cd) {
		$('#employee').val('');
		$('#employeeTxt').val('');
		alert("Employee ID is cleared. Click on save button to save record.");
	}
}
function setEmployee(data) {
	if(data !='null'){
		$('#employee').val(data);
	} else {
		$('#employee').val('');
	}
}