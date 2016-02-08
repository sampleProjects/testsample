$(window).scroll(function(){
	if($(window).scrollTop()<=window.innerHeight){
		$(".icon-top-to-bottom").show();
		$(".icon-back-to-top").hide();
	}
	$(window).scrollTop()+window.innerHeight==$(document).height()&&($(".icon-back-to-top").slideDown(),$(".icon-top-to-bottom").slideUp());
	$(window).scrollTop()<=0&&($(".icon-back-to-top").slideUp(),$(".icon-top-to-bottom").slideDown())
});

// JavaScript Document
$(document).ready(function() {
	$(".icon-top-to-bottom").hide();
	
	$(".icon-back-to-top").click(function(){
		return $("html, body").stop().animate({scrollTop:0},700)
	});
	
	$(".icon-top-to-bottom").click(function(){
		$("html, body").stop().animate({scrollTop:$(document).height()},700);
	})
	
/*	$('.selectpicker').selectpicker();*/
/*	  $('.selectpicker').selectpicker({		
		    size: 10
		  });*/
	$('form').areYouSure();
	
	$("#sidebar-wrapper").mCustomScrollbar({
		theme:"dark-3"		
	});
	
	$('[data-toggle="tooltip"]').tooltip();   
	
	$(document).bind("ajaxSend", function() {		 
		$("#spinner").show();
	//	$("#spinner").removeClass('invisible')
			
	}).bind("ajaxStop", function() {
			$("#spinner").hide();		
	}).bind("ajaxError", function() {
		$("#spinner").hide();		
	});
	
	//use the scriptbreaker.com multiple accordion menu
    $(".topnav").accordion({
        accordion:true,
        speed: 500,
        closedSign: '<i class="fa fa-down"></i>',
		openedSign: '<i class="fa fa-up"></i>'
    });
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
        
        var checkStatus = getCookie('leftPanelStatus');
        if(checkStatus==null) {
        	setCookie('leftPanelStatus','active');
    	} else {
    		deleteCookie('leftPanelStatus');
    	}
        
    });
    
    $(".logoutLink").click(function(e) {
    	deleteCookie('leftPanelStatus');
    });
    
    $(window).resize(function(){
    	 if($(window).width() < 979){
    	        $("#wrapper").addClass("toggled");
    	    }else {
    	    	$("#wrapper").removeClass("toggled");    	
    	    }
   	 	
   });
    $('#searchbtn').click(function() {
 	   $('#createForm').attr('action', "list").submit();
 	});
   
});
function confirmDelete() {
	var cd= confirm("This will delete the record.\n Are you sure?");
	if(!cd) {
		alert("The record is not deleted");
		return false;
	}
}



$( document ).ready(function() {
	
	//called when key is pressed in textbox
	$('.onlyInteger').keypress(function (e) {
		//if the letter is not digit then display error and don't type anything
		if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
		    	return false;
		 }
	});	
});


$( document ).ready(function() {
	
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
changeHeight();

var defaulttext = $('.defualt-text').text();

$('.selectDefault').text(defaulttext);

$('.selectBox').on('change',function(){
   var defaulttext2 = $('.selectBox').find(":selected").text(); 
    $('.selectDefault').text(defaulttext2);
});

}); 


function changeHeight() {
    if ($("#sidebar-wrapper").innerHeight() < window.innerHeight ) {

        $(".main-container").css("min-height", window.innerHeight );
    } else {
        $(".main-container").css("min-height", $("#sidebar-wrapper").innerHeight());
    }
}

function setCookie(name, value) {
	document.cookie = name +'='+ value +'; Path=/;';
}
function getCookie(name) {
	var re = new RegExp(name + "=([^;]+)");
	var value = re.exec(document.cookie);
	return (value != null) ? unescape(value[1]) : null;
}
function deleteCookie(name) {
	document.cookie = name +'=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

function enableCodeField(obj){
	var objName = obj.charAt(0).toUpperCase() + obj.slice(1);
	var result = confirm("Are you sure you want to edit "+objName+" ?");
	if(result){
		if (typeof obj !== 'undefined') {
			$("#"+obj).prop("disabled", false);
			document.forms[0][obj].focus();
		}
	}
}

function keyFilter (obj) {
	if (typeof obj !== 'undefined') {
		var s = obj.value;
		var objName = obj.name.charAt(0).toUpperCase() + obj.name.slice(1);
		filteredValues = " <>%\'\"$^*&";     // Characters stripped out
		var i;
		for (i = 0; i < s.length; i++) {  // Search through string
			var c = s.charAt(i);
			if (filteredValues.indexOf(c) > -1) {
				alert("Permitted values of the "+objName+" field are\nA-Z\na-z\n0-9\nand special characters other than <, >, %, \', \", $, ^, *, &");
				setTimeout(function() {
					document.forms[0][obj.name].focus();
		    	}, 1);
				return false;
			}
		}
		return true;
	}
}