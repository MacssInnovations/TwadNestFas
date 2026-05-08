/** To handle Errors **/
onerror=handleErr;
var txt="";
function handleErr(msg,url,l)
{
	txt="There was an error on this page. \n\n";
	txt+="Error: " + msg + " \n";
	txt+="URL: " + url + " \n";
	txt+="Line: " + l + " \n\n";
	txt+="Click OK to continue.\n\n";
	alert(txt);
	return true;
}

/** To create javascript request object **/
function getTransport()
{
 var req = false;
  try 
 {
	 
       req= new ActiveXObject("Msxml2.XMLHTTP");
 }
 catch (e) 
 {
       try 
       {
    	  
            req = new ActiveXObject("Microsoft.XMLHTTP");
       }
       catch (e2) 
       {
    	 
            req = false;
       }
 }
 if (!req && typeof XMLHttpRequest!='undefined') 
 {
	
       req = new XMLHttpRequest();
 }  
 
 return req;
 
}


function Add(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;	
        var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;	
		
	if (txtCB_Year == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBRS_Monthly_Closure_Freeze_Nil.txtCB_Year.focus();
	} else if (txtCB_Month == "") {
		alert("Enter Cash Book Month in the Field");
		document.frmBRS_Monthly_Closure_Freeze_Nil.txtCB_Month.focus();
	}
 else if (cmbBankAccNo == "s") {
		alert("Choose Bank Acc/No");
		document.frmBRS_Monthly_Closure_Freeze_Nil.cmbBankAccNo.focus();
	}
        
        else {

		var url = path+ "/Freeze_Nil_BRS_Monthly_Closure?command=Add&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo;

		//alert(url);
		var xmlrequest = getTransport();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}
function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			if (command == "Add") {
				Add1(baseResponse);
			}
			else if (command=="bankDetailsLoad")
				{
				loadBank(baseResponse);					
				}
		}
	}
}
function loadBank(baseResponse)
{
	 var i = 0;
		var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
		var count = baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;

	    if(flag=="success"){
	 
	    	var len4 = baseResponse.getElementsByTagName("bank_Detail").length;
			var se = document.getElementById("cmbBankAccNo");
			se.innerHTML="";
			var option=document.createElement("OPTION");
			option.text="-- Select Bank A/C No ---";
			option.value="s";
			try
			{
				se.add(option);
			}catch(errorObject)
			{
				se.add(option,null);
			}

			for (i=0 ; i < len4; i++) 
			{   
				var desc = baseResponse.getElementsByTagName("bank_Detail")[i].firstChild.nodeValue;
			      var option=document.createElement("OPTION");
			      option.text=desc;
			      option.value=desc;
			      try
			      {
			    	  se.add(option);
			      }
			      catch(errorObject)
			      {
			    	  se.add(option,null);
			      }
			}			
	        }
	        else
	        {
	        alert("No Record Exist");
	        }	
	}

function Add1(baseResponse) {

	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "success") {
		alert("BRS Freezing Done Successfully");
		refresh();
	} else if (flag == "Exist") {
		alert("Freezing Already Done");
		refresh();
	} 
        else if (flag == "NoRecord")
        {
                alert("BRS Details Are Not Entered for This CashBookYear and Month");
		refresh();
        }
        else {
		alert("Freezing Failed");
		refresh();
	}
  
}
function loadBankDetails(){
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		var url ="../../../../../Freeze_Nil_BRS_Monthly_Closure?command=loadBankDetails&cmbAcc_UnitCode="+cmbAcc_UnitCode ;				
		var xmlrequest = getTransport();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);	
}

function numbersonly1(e, t) {
	
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode == 13) {
		try {
			t.blur();
		} catch (e) {
		}
		return true;

	}
	if (unicode != 8 && unicode != 9) {
		if (unicode < 48 || unicode > 57)
			return false
	}
}

function refresh() {
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	LoadAccountingUnitID('LIST_ALL_UNITS');
	document.frmBRS_Monthly_Closure_Freeze_Nil.txtCB_Year.value = year;
	document.frmBRS_Monthly_Closure_Freeze_Nil.txtCB_Month.value = month;	
	document.frmBRS_Monthly_Closure_Freeze_Nil.cmbBankAccNo.value = "s";
	document.frmBRS_Monthly_Closure_Freeze_Nil.cmbBankAccNo.length=1;
	
}

function exitfun() {
	window.close();
}
