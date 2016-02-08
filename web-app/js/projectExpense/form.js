function expenseCodeSuccessSearch() {
	 $('#listGridDiv').show();
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

function calculateCost(obj,obj1,i){
	if((obj != null && obj.toString().trim().length > 0 && isNaN(obj) == false) && (obj1 != null && obj1.toString().trim().length > 0 && isNaN(obj1) == false)){
		var qty = parseFloat(obj);
		var unitCost = parseFloat(obj1);
		var totalCost = parseFloat(qty*unitCost);
		
		document.getElementById("span_expenseDetails["+i+"].totalCost").innerHTML=totalCost.toFixed(2);
		document.getElementById("expenseDetails["+i+"].totalCost").value=totalCost.toFixed(2);
	}else if(obj == null || obj.toString().trim().length <= 0 || isNaN(obj)){
		document.getElementById("expenseDetails["+i+"].qty").value="1.00";
		var qty = parseFloat(1.00);
		var unitCost = parseFloat(obj1);
		var totalCost = parseFloat(qty*unitCost);
		
		document.getElementById("span_expenseDetails["+i+"].totalCost").innerHTML=totalCost.toFixed(2);
		document.getElementById("expenseDetails["+i+"].totalCost").value=totalCost.toFixed(2);
	}else if(obj1 == null || obj1.toString().trim().length <= 0 || isNaN(obj1)){
		document.getElementById("expenseDetails["+i+"].unitCost").value="0.00";
		var qty = parseFloat(obj);
		var unitCost = parseFloat(0.00);
		var totalCost = parseFloat(qty*unitCost);
		
		document.getElementById("span_expenseDetails["+i+"].totalCost").innerHTML=totalCost.toFixed(2);
		document.getElementById("expenseDetails["+i+"].totalCost").value=totalCost.toFixed(2);
	}
}

function removeProjectExpenseDtlIndex(index) {
	$("#removeIndex").val(index);
}

function reset() {	 
	$("#expenseCode").val('');
	$("#expenseDesc").val('');
}

function setClickVal(id, code, isFromActualExpense) {	
	addProjectFieldAjax(id,code,isFromActualExpense);
	$('#myModal').modal('hide')
}

function addProjectExpenseFieldInTemplate(data, i, id, code, isFromActualExpense){
	$("#addExpenseDiv").append(data);
	calculateCost(document.getElementById("expenseDetails["+i+"].qty").value,document.getElementById("expenseDetails["+i+"].unitCost").value,i);
	$('form').trigger('rescan.areYouSure');
}

function confirmDelete() {
	var cd= confirm("This will delete the record.\n Are you sure?");
	if(!cd) {
		alert("The record is not deleted");
		return false;
	}
}