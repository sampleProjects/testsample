function confirmDelete() {
	var cd= confirm("This will delete the record.\n Are you sure?");
	if(!cd) {
		alert("The record is not deleted");
		return false;
	}
}


function isNumber(field) {
	try{
		if (isNaN(field.value)) {
			alert("Invalid Number, enter again.");
			field.select();
		    field.focus();
		    return false;
		}
		else if(field.value.toString().trim().length > 0){
			var x = parseFloat(field.value.toString());
			x = x.toFixed(2);
			field.value = x;
		}
	}
	catch(e){
		alert("Invalid Value, enter again.");
		field.select();
		field.focus();
		return false;
	}
}

function isNumberRound(field) {
	try{
		if (isNaN(field.value)) {
			alert("Invalid Number, enter again.");
			field.select();
		    field.focus();
		    return false;
		}
		else if(field.value.toString().trim().length > 0){
			var x = parseFloat(field.value.toString());
			x = Math.round(x);
			field.value = x;
		}
	}
	catch(e){
		alert("Invalid Value, enter again.");
		field.select();
		field.focus();
		return false;
	}
}


function showControls(objValue){	
	if(objValue == "SERVICES"){
		document.getElementById("laborActivityGroupDiv").style.display = "block";
		document.getElementById("projectProductDiv").style.display = "block";
		document.getElementById("glAccountNumberDiv").style.display = "none";
	}else if(objValue == "EXPENSES"){
		document.getElementById("laborActivityGroupDiv").style.display = "none";
		document.getElementById("projectProductDiv").style.display = "none";
		document.getElementById("glAccountNumberDiv").style.display = "block";
	}else{
		document.getElementById("laborActivityGroupDiv").style.display = "none";
		document.getElementById("projectProductDiv").style.display = "none";
		document.getElementById("glAccountNumberDiv").style.display = "none";
	}
	clearProductCategoryWithoutAlert();
}

$(document).ready(function() {	
	$('#productCategoryDialog').on('show.bs.modal', function (e) {
		resetProductCategoryDialog();		
		$('#productCategorylistGridDiv').hide();
	})
	
});

function resetProductCategoryDialog(){
	$('#dialogProductCategory').val('');
	$('#dialogProductCategoryDescription').val('');
}

function productCategorySuccessSearch(){
	 $('#productCategorylistGridDiv').show();	 
}

function setProductCategoryCode(productCategoryId, productCategory){
	$('#productCategoryDialog').modal('hide');
	$('#productCategory').val(productCategoryId);
	$('#productCategoryTxt').val(productCategory);
}


function clearProductCategory() {
	var cd= confirm("Are you sure? You want to clear Category?");
	if(cd) {
		$('#productCategory').val('');
		$('#productCategoryTxt').val('');
		alert("Category is cleared. Click on save button to save record.");
	}
}

function clearProductCategoryWithoutAlert(){
	$('#productCategory').val('');
	$('#productCategoryTxt').val('');
}