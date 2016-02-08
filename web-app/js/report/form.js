function projectPopup() {
	resetProjectDialogData();
	$('#projectSearchDialog').modal('show');
	$('#projectListGridDiv').hide();
}
function resetProjectDialogData() {
	$('#dialogProjectCode').val('');
	$('#dialogProjectName').val('');
}
function projectSuccessSearch(data) {
	 $('#projectListGridDiv').show();
}
function setProjectCode(id, code) {
	$('#projectSearchDialog').modal('hide');	
	$('#project').val(code);	
}

function customerPopup() {
	resetCustomerDialogData();
	$('#customerSearchDialog').modal('show');
	$('#customerListGridDiv').hide();
}
function resetCustomerDialogData() {
	$('#dialogCustomerCode').val('');
	$('#dialogCustomerName').val('');
}
function customerSuccessSearch(data) {
	 $('#customerListGridDiv').show();
}
function setCustomerCode(id, code) {
	$('#customerSearchDialog').modal('hide')	
	$('#customer').val(code);	
}

function coePopup() {
	resetCOEDialogData();
	$('#coeSearchDialog').modal('show');
	$('#coeListGridDiv').hide();
}
function resetCOEDialogData() {
	$('#dialogCOECode').val('');
	$('#dialogCOEDesc').val('');
}
function coeSuccessSearch(data) {
	 $('#coeListGridDiv').show();
}
function setCOECode(id, code) {
	$('#coeSearchDialog').modal('hide')	
	$('#coeCode').val(code);	
}

function laborActCodePopup() {
	resetLaborActCodeDialogData();
	$('#laborActCodeSearchDialog').modal('show');
	$('#laborActCodeListGridDiv').hide();
}
function resetLaborActCodeDialogData() {
	$('#laborActivityCode').val('');
	$('#dialoglaborActCodeDesc').val('');
}
function laborActCodeSuccessSearch(data) {
	 $('#laborActCodeListGridDiv').show();
}
function setLaborActCodeCode(id, code) {
	$('#laborActCodeSearchDialog').modal('hide')	
	$('#laborActCode').val(code);	
}


function laborActGroupPopup() {
	resetLaborActGroupDialogData();
	$('#laborActGroupSearchDialog').modal('show');
	$('#laborActGroupListGridDiv').hide();
}
function resetLaborActGroupDialogData() {
	$('#dialogLaborActivityGrpCode').val('');
	$('#dialogLaborActGrpDesc').val('');
}
function laborActGroupSuccessSearch(data) {
	 $('#laborActGroupListGridDiv').show();
}
function setLaborActGroupCode(id, code) {
	$('#laborActGroupSearchDialog').modal('hide')	
	$('#laborActGroupCode').val(code);	
}

function payDepartmentPopup() {
	resetPayDepartmentCode();
	$('#payDeptCodeDialog').modal('show');
	$('#payDeptListGridDiv').hide();
}
function resetPayDepartmentCode() {
	$('#dialogPayDepartmentCode').val('');	
}
function payDeptSuccessSearch(data) {
	 $('#payDeptListGridDiv').show();
}
function setPayDepartmentCode(id, code) {
	$('#payDeptCodeDialog').modal('hide')	
	$('#payDepartment').val(code);	
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
	$('#acctExecutive').val(code);	
}

function projectManagerPopup() {	
	resetProjectManagerDialogData();
	$('#projectManagerSearchDialog').modal('show');
	$('#projectManagerListGridDiv').hide();
}

function resetProjectManagerDialogData() {
	$('#dialogManagerCode').val('');
	$('#dialogManagerLastName').val('');
	$('#dialogManagerFirstName').val('');
	$('#dialogPayDept').val('');
}
function projectManagerSuccessSearch(data) {
	 $('#projectManagerListGridDiv').show();
}
function setProjectManagerCode(code){
	$('#projectManagerSearchDialog').modal('hide');	
	$('#projectManager').val(code);	
}

function supervisorPopup() {
	resetSupervisorDialogData();
	$('#supervisorSearchDialog').modal('show');
	$('#supervisorListGridDiv').hide();
}
function resetSupervisorDialogData() {
	$('#dialogSupervisorCode').val('');
	$('#dialogSupervisorLastName').val('');
	$('#dialogSupervisorFirstName').val('');
	$('#dialogSupervisorPayDept').val('');
}
function supervisorSuccessSearch(data) {
	 $('#supervisorListGridDiv').show();
}
function setSupervisorCode(code) {
	$('#supervisorSearchDialog').modal('hide');	
	$('#supervisor').val(code);	
}

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
function setEmployeeCode(code){
	$('#employeeSearchDialog').modal('hide');	
	$('#employee').val(code);	
}

function setDate(_period){
    if (_period=="YESTERDAY"){
    	$('#fromDate').val($('#yesterday1').val());
    	$('#toDate').val($('#yesterday2').val());
    }
    if (_period=="TODAY"){
    	$('#fromDate').val($('#today1').val());
    	$('#toDate').val($('#today2').val());
    }
    if (_period=="CURRENT_WEEK"){
    	$('#fromDate').val($('#currentweek1').val());
    	$('#toDate').val($('#currentweek2').val());
    }
    if (_period=="CURRENT_MONTH"){
    	$('#fromDate').val($('#currentmonth1').val());
    	$('#toDate').val($('#currentmonth2').val());
    }
    if (_period=="CURRENT_QTR"){
    	$('#fromDate').val($('#currentqtr1').val());
    	$('#toDate').val($('#currentqtr2').val());
    }
    if (_period=="CURRENT_YEAR"){
    	$('#fromDate').val($('#currentyear1').val());
    	$('#toDate').val($('#currentyear2').val());
    }
    if (_period=="LAST_WEEK"){
    	$('#fromDate').val($('#lastweek1').val());
    	$('#toDate').val($('#lastweek2').val());
    }
    if (_period=="LAST_MONTH"){
    	$('#fromDate').val($('#lastmonth1').val());
    	$('#toDate').val($('#lastmonth2').val());
    }
    if (_period=="LAST_QTR"){
    	$('#fromDate').val($('#lastqtr1').val());
    	$('#toDate').val($('#lastqtr2').val());
    }
    if (_period=="LAST_YEAR"){
    	$('#fromDate').val($('#lastyear1').val());
    	$('#toDate').val($('#lastyear2').val());
    }
}

$(document).ready(function() {	
	if($('#fromDate').val() == null || $('#fromDate').val() == ""){
		$("#predefined").val("CURRENT_MONTH");
		$('#fromDate').val($('#currentmonth1').val());
		$('#toDate').val($('#currentmonth2').val());
	}
});

function expensePopup() {
	resetExpenseDialogData();
	$('#expenseSearchDialog').modal('show');
	$('#expenseListGridDiv').hide();
}

function resetExpenseDialogData() {
	$('#dialogExpenseCode').val('');
	$('#dialogDescription').val('');
}

function expenseSuccessSearch(data) {
	 $('#expenseListGridDiv').show();
}

function setExpenseCode(code){
	$('#expenseSearchDialog').modal('hide');	
	$('#expense').val(code);	
}