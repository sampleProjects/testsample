/**
 * 
 */
	
var removedRecordId= [];

function activitySuccessSearch() {
	 $('#listGridDiv').show();
}
function reset() {	 
	$("#laborActivityCode").val('');
	$("#coe").val('');
}

function setClickVal(id, code) {
	$('#projLbrActScreeDialog').modal('hide');
	addLaborActivityCodeDetailsAjax(id, code);
}

function addLaborActivityCodeDetails(data,id,code){
	var tbl = document.getElementById("dataTable");
	var row = tbl.insertRow(tbl.rows.length);
	document.forms[0].maxRows.value = parseInt(document.forms[0].maxRows.value) + 1;
	var toIndx = 10;
	if(document.getElementById("isHoursOnly") != null && document.getElementById("isHoursOnly").value=="false"){
		toIndx = 10;
	} else {
		toIndx = 3;
	}
	for(i=0;i<toIndx;i++) {
		var cell1 = row.insertCell(i);
		if(i==0){
			cell1.innerHTML = "<span id='laborActivityCode_"+(row.rowIndex-1)+"'>"+data.laborActivityCode+"</span><br><span id='laborDesc_"+(row.rowIndex-1)+"'>"+data.laborActivityDesc+"</span>";
			createHiddenField("storedRowID_"+(row.rowIndex-1),"storedRowID_"+(row.rowIndex-1),"");
			createHiddenField("laborActivityCode_var_"+(row.rowIndex-1),"laborActivityCode_var_"+(row.rowIndex-1),data.id);
			createHiddenField("laborDesc_var_"+(row.rowIndex-1),"laborDesc_var_"+(row.rowIndex-1),data.laborActivityDesc);
		}else if(i==1){
			cell1.innerHTML = "<input type='text' id='laborHours_"+(row.rowIndex-1)+"' name='laborHours_"+(row.rowIndex-1)+"' value='0.00' class='form-control pull-right onlyDecimal' style='width: 120px;' onblur='calculateCost(this.value,"+(row.rowIndex-1)+");' />";
			createHiddenField("laborHours_var_"+(row.rowIndex-1),"laborHours_var_"+(row.rowIndex-1),0.00);
		}
		if(document.getElementById("isHoursOnly") != null && document.getElementById("isHoursOnly").value=="false"){
			if(i==2){
				cell1.className = "text-right";
				cell1.innerHTML = "<span id='laborStdRate_"+(row.rowIndex-1)+"' class='pull-right'>"+data.standardRate+"</span>";
				createHiddenField("laborStdRate_var_"+(row.rowIndex-1),"laborStdRate_var_"+(row.rowIndex-1),data.standardRate);
			}else if(i==3){
				cell1.className = "text-right";
				cell1.innerHTML = "<span id='laborStdCost_"+(row.rowIndex-1)+"' class='pull-right'>0.00</span>";
				createHiddenField("laborStdCost_var_"+(row.rowIndex-1),"laborStdCost_var_"+(row.rowIndex-1),0.00);
			}else if(i==4){
				cell1.className = "text-right";
				cell1.innerHTML = "<span id='laborOverHead_"+(row.rowIndex-1)+"' class='pull-right'>0.00</span>";
				createHiddenField("laborOverHead_var_"+(row.rowIndex-1),"laborOverHead_var_"+(row.rowIndex-1),data.overHeadPer);
			}else if(i==5){
				cell1.className = "text-right";
				cell1.innerHTML = "<span id='laborOverHeadCost_"+(row.rowIndex-1)+"' class='pull-right'>0.00</span>";
				createHiddenField("laborOverHeadCost_var_"+(row.rowIndex-1),"laborOverHeadCost_var_"+(row.rowIndex-1),0.00);
			}else if(i==6){
				cell1.className = "text-right";
				cell1.innerHTML = "<span id='laborTotalCost_"+(row.rowIndex-1)+"' class='pull-right'>0.00</span>";
				createHiddenField("laborTotalCost_var_"+(row.rowIndex-1),"laborTotalCost_var_"+(row.rowIndex-1),0.00);
			}else if(i==7){
				cell1.className = "text-right";
				cell1.innerHTML = "<span id='laborBillRate_"+(row.rowIndex-1)+"' class='pull-right'>0.00</span>";
				createHiddenField("laborBillRate_var_"+(row.rowIndex-1),"laborBillRate_var_"+(row.rowIndex-1),data.billRate);
			}else if(i==8){
				cell1.className = "text-right";
				cell1.innerHTML = "<span id='laborBillAmount_"+(row.rowIndex-1)+"' class='pull-right'>0.00</span>";
				createHiddenField("laborBillAmount_var_"+(row.rowIndex-1),"laborBillAmount_var_"+(row.rowIndex-1),0.00);
			}else if(i==9){
				cell1.className = "text-center";
				cell1.innerHTML = "<input type='button' class='remove-img marginleft5' onclick='javascript: deleteCurrentRow("+(row.rowIndex)+");' />";
				
			}
			onlyDecimal();
		} else {
			 if(i==2) {
				createHiddenField("laborStdRate_var_"+(row.rowIndex-1),"laborStdRate_var_"+(row.rowIndex-1),data.standardRate);							
				createHiddenField("laborStdCost_var_"+(row.rowIndex-1),"laborStdCost_var_"+(row.rowIndex-1),0.00);							
				createHiddenField("laborOverHead_var_"+(row.rowIndex-1),"laborOverHead_var_"+(row.rowIndex-1),data.overHeadPer);							
				createHiddenField("laborOverHeadCost_var_"+(row.rowIndex-1),"laborOverHeadCost_var_"+(row.rowIndex-1),0.00);							
				createHiddenField("laborTotalCost_var_"+(row.rowIndex-1),"laborTotalCost_var_"+(row.rowIndex-1),0.00);							
				createHiddenField("laborBillRate_var_"+(row.rowIndex-1),"laborBillRate_var_"+(row.rowIndex-1),data.billRate);							
				createHiddenField("laborBillAmount_var_"+(row.rowIndex-1),"laborBillAmount_var_"+(row.rowIndex-1),0.00);
			
				cell1.className = "text-center";
				cell1.innerHTML = "<input type='button' class='remove-img marginleft5' onclick='javascript: deleteCurrentRow("+(row.rowIndex)+");' />";
			}
			onlyDecimal();
		}
	}
}

function deleteCurrentRow(index){
	var tbl = document.getElementById("dataTable");
	tbl.deleteRow(index);
	document.forms[0].maxRows.value = parseInt(document.forms[0].maxRows.value) - 1;
	
	$("#storedRowID_"+(index-1)).remove();
	$("#laborActivityCode_var_"+(index-1)).remove();
	$("#laborDesc_var_"+(index-1)).remove();
	$("#laborHours_var_"+(index-1)).remove();
	$("#laborStdRate_var_"+(index-1)).remove();
	$("#laborStdCost_var_"+(index-1)).remove();
	$("#laborOverHead_var_"+(index-1)).remove();
	$("#laborOverHeadCost_var_"+(index-1)).remove();
	$("#laborTotalCost_var_"+(index-1)).remove();
	$("#laborBillRate_var_"+(index-1)).remove();
	$("#laborBillAmount_var_"+(index-1)).remove();
	
	for(i=index;i<tbl.rows.length;i++){
		for(j=0;j<tbl.rows[i].cells.length;j++){
			if(j==0){
				tbl.rows[i].cells[j].childNodes[0].id = "laborActivityCode_"+(i-1);
				tbl.rows[i].cells[j].childNodes[2].id = "laborDesc_"+(i-1);
				
				changeHiddenFieldId("storedRowID_"+(i-1),"storedRowID_"+(i-1),"storedRowID_"+(i));
				changeHiddenFieldId("laborActivityCode_var_"+(i-1),"laborActivityCode_var_"+(i-1),"laborActivityCode_var_"+(i));
				changeHiddenFieldId("laborDesc_var_"+(i-1),"laborDesc_var_"+(i-1),"laborDesc_var_"+(i));
				
			}else if(j==1){
				//tbl.rows[i].cells[j].childNodes[0].id = "laborHours_"+(i-1);
				//tbl.rows[i].cells[j].childNodes[0].name = "laborHours_"+(i-1);
				tbl.rows[i].cells[j].innerHTML = "<input type='text' id='laborHours_"+(i-1)+"' name='laborHours_"+(i-1)+"' value='"+tbl.rows[i].cells[j].childNodes[0].value+"' class='form-control pull-right onlyDecimal' style='width: 120px;' onblur='calculateCost(this.value,"+(i-1)+");' />";
				changeHiddenFieldId("laborHours_var_"+(i-1),"laborHours_var_"+(i-1),"laborHours_var_"+(i));
			}
			if(document.getElementById("isHoursOnly") != null && document.getElementById("isHoursOnly").value=="false"){
				if(j==2){
					tbl.rows[i].cells[j].childNodes[0].id = "laborStdRate_"+(i-1);
					changeHiddenFieldId("laborStdRate_var_"+(i-1),"laborStdRate_var_"+(i-1),"laborStdRate_var_"+(i));
				}else if(j==3){
					tbl.rows[i].cells[j].childNodes[0].id = "laborStdCost_"+(i-1);
					changeHiddenFieldId("laborStdCost_var_"+(i-1),"laborStdCost_var_"+(i-1),"laborStdCost_var_"+(i));
				}else if(j==4){
					tbl.rows[i].cells[j].childNodes[0].id = "laborOverHead_"+(i-1);
					changeHiddenFieldId("laborOverHead_var_"+(i-1),"laborOverHead_var_"+(i-1),"laborOverHead_var_"+(i));
				}else if(j==5){
					tbl.rows[i].cells[j].childNodes[0].id = "laborOverHeadCost_"+(i-1);
					changeHiddenFieldId("laborOverHeadCost_var_"+(i-1),"laborOverHeadCost_var_"+(i-1),"laborOverHeadCost_var_"+(i));
				}else if(j==6){
					tbl.rows[i].cells[j].childNodes[0].id = "laborTotalCost_"+(i-1);
					changeHiddenFieldId("laborTotalCost_var_"+(i-1),"laborTotalCost_var_"+(i-1),"laborTotalCost_var_"+(i));
				}else if(j==7){
					tbl.rows[i].cells[j].childNodes[0].id = "laborBillRate_"+(i-1);
					changeHiddenFieldId("laborBillRate_var_"+(i-1),"laborBillRate_var_"+(i-1),"laborBillRate_var_"+(i));
				}else if(j==8){
					tbl.rows[i].cells[j].childNodes[0].id = "laborBillAmount_"+(i-1);
					changeHiddenFieldId("laborBillAmount_var_"+(i-1),"laborBillAmount_var_"+(i-1),"laborBillAmount_var_"+(i));
				}else if(j==9){
					tbl.rows[i].cells[j].innerHTML = "<input type='button' class='remove-img marginleft5' onclick='javascript: deleteCurrentRow("+(i)+");' />";
				}
				
			}
			
		}
	}
	onlyDecimal();
}
 
function deleteSaveRow(index){	
	var table = document.getElementById("dataTable");	
	var tableRowSize=table.rows.length-1;
	 
	removedRecordId.push($("#storedRowID_"+(index)).val());
	
	$("#removedRecordId").val(removedRecordId);
	table.deleteRow(index+1);
	reIndexRow(index, tableRowSize);
	 
}

function changeFieldIndex(oldIndex, newIndex, fieldName) {
	
	var oldFieldId, obj, newFieldId, newFieldName;
	
	oldFieldId = "#"+ fieldName + "_" + oldIndex
	obj = $(oldFieldId);
	
	newFieldId = fieldName + "_" + newIndex;
	newFieldName = fieldName + "_" + newIndex;
	obj.attr("id", newFieldId);
	obj.attr("name", newFieldName);	
}
function reIndexRow(removedId, tableRowSize) {
	
		var startIndex = parseInt(removedId)+1;
		var oldId;
		var obj;
		var newId;
		var newName;
		for(var i=startIndex; i < tableRowSize; i++) {			
			 
			//changeFieldIndex(i, i-1, 'laborHours');
			
			changeFieldIndex(i, i-1, 'laborActivityCode');
			
			changeFieldIndex(i, i-1, 'laborDesc');
			
			changeFieldIndex(i, i-1, 'storedRowID');
			
			changeFieldIndex(i, i-1, 'laborActivityCode_var');
			
			changeFieldIndex(i, i-1, 'laborDesc_var');
			
			changeFieldIndex(i, i-1, 'laborHours_var');
			
			changeFieldIndex(i, i-1, 'laborStdRate');
			
			changeFieldIndex(i, i-1, 'laborStdRate_var');
			
			changeFieldIndex(i, i-1, 'laborStdCost');
			
			changeFieldIndex(i, i-1, 'laborStdCost_var');
			
			changeFieldIndex(i, i-1, 'laborOverHead');
			changeFieldIndex(i, i-1, 'laborOverHead_var');
			
			changeFieldIndex(i, i-1, 'laborOverHeadCost');			
			changeFieldIndex(i, i-1, 'laborOverHeadCost_var');
			
			changeFieldIndex(i, i-1, 'laborTotalCost');			
			changeFieldIndex(i, i-1, 'laborTotalCost_var');
			
			changeFieldIndex(i, i-1, 'laborBillRate');			
			changeFieldIndex(i, i-1, 'laborBillRate_var');
			
			changeFieldIndex(i, i-1, 'laborBillAmount');			
			changeFieldIndex(i, i-1, 'laborBillAmount_var');
					
			var obj = $("#deleteBtn_"+i);
			obj.attr("id", "deleteBtn_"+(i-1));
			obj.attr("onclick", "deleteSaveRow(" + (i-1) + ");");
			
			var obj = $("#laborHours_"+i);			
			obj.attr("id", "laborHours_"+(i-1));
			obj.attr("name", "laborHours_"+(i-1));	
			obj.attr("onblur", "calculateCost(this.value," + (i-1) + ");");
						
			onlyDecimal();			
	}
} 
function createHiddenField(name, id, value){
	var input = document.createElement("input");
	input.setAttribute("type", "hidden");
	input.setAttribute("name", name);
	input.setAttribute("id", id);
	input.setAttribute("value", value);
	document.forms[0].appendChild(input);
}

function changeHiddenFieldId(name, id, currentId){
	var input = document.getElementById(currentId);
	if(input != null){
		input.id = id;
		input.name = name;
	}
}

function calculateCost(obj,i){
	if(obj != null && obj.toString().trim().length > 0 && isNaN(obj) == false){
		document.getElementById("laborHours_var_"+i).value = parseFloat(document.getElementById("laborHours_"+i).value);

		var hours = parseFloat(document.getElementById("laborHours_"+i).value);
		var stdRate = parseFloat(document.getElementById("laborStdRate_var_"+i).value);
		var stdCost = parseFloat(hours*stdRate);
		var billRate = parseFloat(document.getElementById("laborBillRate_var_"+i).value);
		
		if (document.getElementById("laborStdCost_"+i) != null) {
			document.getElementById("laborStdCost_"+i).innerHTML = stdCost.toFixed(2);
		}
		document.getElementById("laborStdCost_var_"+i).value = stdCost.toFixed(2);

		if (document.getElementById("laborBillAmount_"+i) != null) {
			document.getElementById("laborBillAmount_"+i).innerHTML = (hours * billRate).toFixed(2);
		}
		document.getElementById("laborBillAmount_var_"+i).value = (hours * billRate).toFixed(2);
		
		if(stdCost != null && stdCost.toString().trim().length > 0){
			//var stdRate = parseFloat(document.getElementById("laborStdRate_var_"+i).value);
			var ohPer = parseFloat(document.getElementById("laborOverHead_var_"+i).value);
			
			if(document.getElementById("laborOverHeadCost_"+i) != null){
				document.getElementById("laborOverHeadCost_"+i).innerHTML = ((parseFloat(stdCost)) * (ohPer/100)).toFixed(2);
			}
			document.getElementById("laborOverHeadCost_var_"+i).value = ((parseFloat(stdCost)) * (ohPer/100)).toFixed(2);

			if(document.getElementById("laborTotalCost_"+i) != null){
				document.getElementById("laborTotalCost_"+i).innerHTML = ((parseFloat(document.getElementById("laborStdCost_var_"+i).value)) + parseFloat((document.getElementById("laborOverHeadCost_var_"+i).value))).toFixed(2);
			}
			document.getElementById("laborTotalCost_var_"+i).value = ((parseFloat(document.getElementById("laborStdCost_var_"+i).value)) + parseFloat((document.getElementById("laborOverHeadCost_var_"+i).value))).toFixed(2);
		}
	}else if(isNaN(obj)){
		document.getElementById("laborHours_"+i).value = "0.00";
		document.getElementById("laborHours_var_"+i).value = "0.00";

		if(document.getElementById("laborStdCost_"+i) != null){
			document.getElementById("laborStdCost_"+i).innerHTML = "0.00";
		}
		document.getElementById("laborStdCost_var_"+i).value = "0.00";

		if(document.getElementById("laborOverHeadCost_"+i) != null){
			document.getElementById("laborOverHeadCost_"+i).innerHTML = "0.00";	
		}
		document.getElementById("laborOverHeadCost_var_"+i).value = "0.00";

		if(document.getElementById("laborTotalCost_"+i) != null){
			document.getElementById("laborTotalCost_"+i).innerHTML = "0.00";
		}
		document.getElementById("laborTotalCost_var_"+i).value = "0.00";

		if(document.getElementById("laborBillAmount_"+i) != null){
			document.getElementById("laborBillAmount_"+i).innerHTML = "0.00";
		}
		
		document.getElementById("laborBillAmount_var_"+i).value = "0.00";
	}else if(obj != null && obj.toString().trim().length == 0){
		document.getElementById("laborHours_"+i).value = "0.00";
		document.getElementById("laborHours_var_"+i).value = "0.00";

		if(document.getElementById("laborStdCost_"+i) != null){
			document.getElementById("laborStdCost_"+i).innerHTML = "0.00";
		}
		document.getElementById("laborStdCost_var_"+i).value = "0.00";

		if(document.getElementById("laborOverHeadCost_"+i) != null){
			document.getElementById("laborOverHeadCost_"+i).innerHTML = "0.00";
		}
		document.getElementById("laborOverHeadCost_var_"+i).value = "0.00";

		if(document.getElementById("laborTotalCost_"+i) != null){
			document.getElementById("laborTotalCost_"+i).innerHTML = "0.00";
		}
		document.getElementById("laborTotalCost_var_"+i).value = "0.00";

		if(document.getElementById("laborBillAmount_"+i) != null){
			document.getElementById("laborBillAmount_"+i).innerHTML = "0.00";		
		}
		document.getElementById("laborBillAmount_var_"+i).value = "0.00";
	}
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