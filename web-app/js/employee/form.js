$(document).ready(function() {			     
			    
	$('#searchbtn').click(function() {
	   $('#createForm').attr('action', "list").submit();
	});
	 		
	$('#myModal').on('show.bs.modal', function (e) { 
		 reset();
		 $('#laborListGridDiv').hide();
	})	 	
	$('#empCodeDialog').on('show.bs.modal', function (e) {
		resetEmpCodeDialog();		
		$('#empCodelistGridDiv').hide();	  					
	})
	$('#deptCodeDialog').on('show.bs.modal', function (e) {	
		resetDepartmentCode();
		$('#deptListGridDiv').hide();	  					
	})
	
});	
function empSuccessSearch(data) {
	 $('#empCodelistGridDiv').show();
}
function laborGrpSuccessSearch(data) {
	 $('#laborListGridDiv').show();
}
function deptSuccessSearch(data) {
	 $('#deptListGridDiv').show();
}


function resetEmpCodeDialog(){
	$("#dialogEmpId").val('');
	$("#dialogFirstName").val('');
	$("#dialoglastName").val('');	 
	$('#isAssignedEmpShow').attr('checked', false);
}

function setClickVal(id, code) {
	$("#laborActDept").val(code);
	$('#myModal').modal('hide')
	$("#laborActivityGroup").val(id);
	
	fillLaborActCodeCombo(id);
}

function confirmDelete() {
	var cd= confirm("This will delete the record.\n Are you sure?");
	if(!cd) {
		alert("The record is not deleted");
		return false;
	}
}

function reset() {	 
	$("#laborActGrpNmae").val('');
	$("#laborActGrpDesc").val('');
}

function setEmpCode(code, firstName, lastName) {
	$('#empCodeDialog').modal('hide')
	$("#code").val(code);	
	$("#firstName").val(firstName);
	$("#lastName").val(lastName);
	
}

function resetDepartmentCode(){
	$('#dialogDepartmentCode').val('');
	$('#dialogDescription').val('');
}

function setDeptCode(id, code){
	$('#deptCodeDialog').modal('hide')
	$('#payDept').val(id);
	$('#payDeptCode').val(code);
}