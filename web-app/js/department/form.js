$(document).ready(function() {			     
			    
	$('#searchbtn').click(function() {
	   $('#createForm').attr('action', "list").submit();
	});
	 		
	$('#deptLaborActGroupDialog').on('show.bs.modal', function (e) { 
		resetDepartmentCode();
		 $('#laborActivityGroupListGridDiv').hide();
	})
	
	$('#deptCodeDialog').on('show.bs.modal', function (e) { 
		resetDeptCode();
		 $('#deptListGridDiv').hide();
	})
	
});	
function deptLbrActGroupSuccessSearch(data) {
	$('#laborActivityGroupListGridDiv').show();
}
function deptSuccessSearch(data) {
	 $('#deptListGridDiv').show();
}
function confirmDelete() {
	var cd= confirm("This will delete the record.\n Are you sure?");
	if(!cd) {
		alert("The record is not deleted");
		return false;
	}
}
function resetDepartmentCode(){
	$('#dialogLaborActGrpCode').val('');
}

function setLaborActGroup(laborActivityGroupId, laborActivityGroup){
	$('#deptLaborActGroupDialog').modal('hide')
	$('#laborActivityGroup').val(laborActivityGroupId);
	$('#laborActivityGrouptxt').val(laborActivityGroup);	
}

function setDeptCode(id, code, laborActivityGroupId, laborActivityGroup, description){
	$('#deptCodeDialog').modal('hide')
	$('#id').val(id);
	$('#code').val(code);
	$('#laborActivityGroup').val(laborActivityGroupId);
	$('#laborActivityGrouptxt').val(laborActivityGroup);
	$('#description').val(description);
}

function resetDeptCode(){
	$('#dialogDepartmentCode').val('');
	$('#dialogLaborActivityGroup').val('');
	$('#dialogDescription').val('');
}

function clearLaborActivityGroup(){
	var cd= confirm("Are you sure? You want to clear Labor Department?");
	if(cd) {
		$('#laborActivityGroup').val('');
		$('#laborActivityGrouptxt').val('');
		alert("Labor Department is cleared. Click on save button to save record.");
	}
	
}