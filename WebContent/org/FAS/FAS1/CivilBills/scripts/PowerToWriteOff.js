
var seq=0;
clearAll=function(){
	document.getElementById('cmbFinancialYear').selectedIndex=0;
	document.getElementById('cmbFinancialYear').disabled=false;
	document.getElementById('cmbLevel').selectedIndex=0;
	document.FAS_PowerToOffice.r1[0].checked="checked";
	document.FAS_PowerToOffice.r1[0].disabled=false;
	document.FAS_PowerToOffice.r1[1].disabled=false;
	document.getElementById('txtValueUpto').value="";
	document.getElementById('mtxtRemarks').value="";
	document.getElementById('cmbFinancialYear').disable=false;
	document.getElementById('cmbLevel').disabled=false;
	document.getElementById('onsubmit').disabled=false;
	document.getElementById('ondelete').disabled=true;
	document.getElementById('onupdate').disabled=true;
};
function AjaxFunction() {
	
	
	var xmlrequest = false;
	try {
		xmlrequest = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e1) {
		try {
			xmlrequest = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			xmlrequest = false;
		}
	}
	if (!xmlrequest && typeof XMLHttpRequest != 'undefined') {
		xmlrequest = new XMLHttpRequest();
	}
	return xmlrequest;
}


function listPopup(){      
     
        winemp= window.open("PowerToWriteList.jsp","list","width=600,resizable=YES, scrollbars=yes"); 
        winemp.moveTo(250,250);  
        winemp.focus();
}

function delete1()
{
	  var cmbLevel=document.getElementById("cmbLevel").value;
	  var radio="";
	     if(document.FAS_PowerToOffice.r1[0].checked==true){
	    	 radio=document.FAS_PowerToOffice.r1[0].value;
	     }else{
	    	 radio=document.FAS_PowerToOffice.r1[1].value;
	     }
	     alert("radio "+radio);
      var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	
	var r = confirm("Are U Sure?");
	if (r == true) {
		
		var url="../../../../../PowerToWriteOff?command=deleted&cmbLevel="+cmbLevel+"&radio="+radio+"&cmbFinancialYear="+cmbFinancialYear;
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);
	}
}

function add()
{
	
	
//alert("enter into add");
	 var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;

	 var cmbLevel=document.getElementById("cmbLevel").value;
     var radio="";
     if(document.FAS_PowerToOffice.r1[0].checked==true){
    	 radio=document.FAS_PowerToOffice.r1[0].value;
     }else{
    	 radio=document.FAS_PowerToOffice.r1[1].value;
     }
     var txtValueUpto =document.getElementById("txtValueUpto").value;
     var mtxtRemarks=document.getElementById("mtxtRemarks").value;
     if(cmbLevel=="select")
	 {
	 alert("should be select level");
	 document.getElementById("cmbLevel").focus();
	 return true;
	 }
 if(txtValueUpto=="")
	 {
	 alert("enter value");
	 document.getElementById("txtValueUpto").focus();
	 return true;
	 }
 
 if(mtxtRemarks=="")
 {
	 alert("enter remarks");
 document.getElementById("mtxtRemarks").focus();
 return true;
 }
	var url="../../../../../PowerToWriteOff?command=add&cmbLevel="+cmbLevel+"&radio="+radio+"&txtValueUpto="+txtValueUpto+"&mtxtRemarks="+mtxtRemarks+"&cmbFinancialYear="+cmbFinancialYear;
	var xmlrequest=AjaxFunction();
	xmlrequest.open("POST",url,true);
	xmlrequest.onreadystatechange=function(){
		
		manipulate(xmlrequest);
	};
	xmlrequest.send(null);
}

function update()
{
	//alert("enter");
	 var cmbLevel=document.getElementById("cmbLevel").value;
	 var radio="";
     if(document.FAS_PowerToOffice.r1[0].checked==true){
    	 radio=document.FAS_PowerToOffice.r1[0].value;
     }else{
    	 radio=document.FAS_PowerToOffice.r1[1].value;
     }
     var txtValueUpto =document.getElementById("txtValueUpto").value;
     var mtxtRemarks=document.getElementById("mtxtRemarks").value;
     var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
     if(cmbLevel=="select")
	 {
	 alert("should be select level");
	 document.getElementById("cmbLevel").focus();
	 return true;
	 }
 if(txtValueUpto=="")
	 {
	 alert("enter value");
	 document.getElementById("txtValueUpto").focus();
	 return true;
	 }
 
 if(mtxtRemarks=="")
 {
	 alert("enter remarks");
 document.getElementById("mtxtRemarks").focus();
 return true;
 }
	var url="../../../../../PowerToWriteOff?command=update&cmbLevel="+cmbLevel+"&radio="+radio+"&txtValueUpto="+txtValueUpto+"&mtxtRemarks="+mtxtRemarks+"&cmbFinancialYear="+cmbFinancialYear;
	//alert(url);
	var xmlrequest=AjaxFunction();
	xmlrequest.open("POST",url,true);
	xmlrequest.onreadystatechange=function(){
		
		manipulate(xmlrequest);
	};
	xmlrequest.send(null);
	
	
}
function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
			
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			var command = tagCommand.firstChild.nodeValue;
		

			if (command == "add") {
				addRow(baseResponse);
			} else if (command == "deleted") {
				deleteRow(baseResponse);
			} else if (command == "update") {
				updateRow(baseResponse);
			} 

	}

}
}

function ParentDrawing(r5,r2,r3,r4){
	//alert("parent");
	//alert(r5);
	//alert(r2);
	//alert(r3);
	//alert(r4);
	if(r2=='L'){
		document.FAS_PowerToOffice.r1[0].checked=true;
		document.FAS_PowerToOffice.r1[0].disabled=true;
		document.FAS_PowerToOffice.r1[1].disabled=true;
	}else{
		document.FAS_PowerToOffice.r1[1].checked=true;
		document.FAS_PowerToOffice.r1[0].disabled=true;
		document.FAS_PowerToOffice.r1[1].disabled=true;
	}
	document.FAS_PowerToOffice.cmbLevel.value=r5;
	document.FAS_PowerToOffice.txtValueUpto.value=r3;
	document.FAS_PowerToOffice.mtxtRemarks.value=r4;
	document.FAS_PowerToOffice.cmbLevel.disabled=true;	
	document.FAS_PowerToOffice.cmbFinancialYear.disabled=true;
	
	
	
	
	document.FAS_PowerToOffice.onsubmit.disabled = true;
	document.FAS_PowerToOffice.ondelete.disabled = false;
	document.FAS_PowerToOffice.onupdate.disabled = false;

	
	}


function updateRow(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
	alert("Update successfully");
	refresh();
	}
	
	else {
		alert("Failed to update values");
	}
}
function deleteRow(baseResponse) {
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
	alert("Cancel successfully");
	refresh();
	}
	else
		{
		alert("Fail To Delete");
		}
}
function addRow(baseResponse) {
	
	refresh();
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Inserted Into Database successfully.");
	}
	else
		{
		alert("Record exists");
		}
}
function refresh()
{
	
	
	
	document.FAS_PowerToOffice.cmbLevel.value='select';
	document.FAS_PowerToOffice.r1[0].checked=false;
	document.FAS_PowerToOffice.r1[1].checked=false;
	document.FAS_PowerToOffice.txtValueUpto.value="";
	document.FAS_PowerToOffice.mtxtRemarks.value="";
	

}


function exitfun() {
	alert("exit ");
	window.close();
}


function initialLoad1() {
	 
    var today = new Date();
    var day = today.getDate();
    var month = today.getMonth();
    month = month + 1;
    var year = today.getYear();
    var year1 = 0;
    var financialyear = 0;
    var financialyear1 = 0;
    var financialyear2 = 0;
    if (year < 1900)
        year += 1900;
    if (month < 4) {
        year1 = year - 1;
    } else {
        year1 = year + 1;
    }

    if (month < 4) {
        financialyear = year1 + "-" + year;
        financialyear1 = (parseInt(year1) - 1) + "-" + (parseInt(year) - 1);
        financialyear2 = (parseInt(year1) - 2) + "-" + (parseInt(year) - 2);
    } else {
        financialyear = year + "-" + year1;
        financialyear1 = (parseInt(year) - 1) + "-" + (parseInt(year1) - 1);
        financialyear2 = (parseInt(year) - 2) + "-" + (parseInt(year1) - 2);
    }

    for ( var k = 0; k < 3; k++) {
        if (k == 0) {
            var se = document.getElementById("cmbFinancialYear");
            var op = document.createElement("OPTION");
            op.value = financialyear2;
            var txt = document.createTextNode(financialyear2);
            op.appendChild(txt);
            se.appendChild(op);
        } else if (k == 1) {
            var se = document.getElementById("cmbFinancialYear");
            var op = document.createElement("OPTION");
            op.value = financialyear1;
            var txt = document.createTextNode(financialyear1);
            op.appendChild(txt);
            se.appendChild(op);

        } else if (k == 2) {
            var se = document.getElementById("cmbFinancialYear");
            var op = document.createElement("OPTION");
            op.value = financialyear;
            var txt = document.createTextNode(financialyear);
            op.appendChild(txt);
            se.appendChild(op);

        }
    }
    document.getElementById("cmbFinancialYear").value = financialyear;

 
} 
