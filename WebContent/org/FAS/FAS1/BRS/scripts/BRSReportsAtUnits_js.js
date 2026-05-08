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

function printFunctions(path) {

	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("txtCB_Year").value;
	var cboCashBook_Month = document.getElementById("txtCB_Month").value;
	var txtBankAccountNo = document.getElementById("txtBankAccountNo").value;
	var text = document.getElementById("txtBankAccountNo").options[document
			.getElementById("txtBankAccountNo").selectedIndex].text;
	// alert(text);
	var trnType = document.getElementById("trnType").value;

	if (document.getElementById("txtCB_Year").value == "") {
		alert("Enter Cash Book Year in the Field");

	} else if (cboCashBook_Month == "" || cboCashBook_Month == "s") {
		alert("Select Cash Book Month in the Field");

	} else if (txtBankAccountNo == "" || txtBankAccountNo == "s") {
		alert("Select Bank Account No in the Field");

	} else {
		var url = path
				+ "/BRSReportsAtUnits_servlet?command=printFunc&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&cboCashBook_Year=" + cboCashBook_Year
				+ "&cboCashBook_Month=" + cboCashBook_Month
				+ "&txtBankAccountNo=" + txtBankAccountNo + "&trntype="
				+ trnType + "&bkid=" + text;
		// alert(url);
		document.BRSUnitsReport.action = url;
		document.BRSUnitsReport.submit();

	}
}
function load_acc(path, unit_id) {
	if(unit_id==1){
		unit_id=document.getElementById("cmbAcc_UnitCode").value;}
	var url = path
			+ "/BRSReportsAtUnits_servlet?command=load_Acc&cmbAcc_UnitCode="
			+ unit_id;
	var req = AjaxFunction();
	req.open("GET", url, true);
	//req.setRequestHeader('Content-Type', 'text/xml');
	req.onreadystatechange=function() {
		load_acc1(req);
	};

	req.send(null);
}
function load_acc1(req)
{

	
    if(req.readyState==4)
    {
        if(req.status==200)
        {   
        	
        	var baseResponse = req.responseXML
			.getElementsByTagName("response")[0];
           //  alert(baseResponse);
             var tagcommand=baseResponse.getElementsByTagName("command")[0];
             var Command=tagcommand.firstChild.nodeValue;
           
         //  alert(Command);
            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	 
            	 if(flag=="success")
            	    {
            	          
            	           
            	           /** Bank Account Number Object to find length */ 
            	           var acc_no=baseResponse.getElementsByTagName("acc_no");
            	           
            	           /** Get Combo box Object */
            	           var cmbBankAccNo = document.getElementById("txtBankAccountNo");
            	        //   alert(acc_no.length);
            	            
            	         
            	            cmbBankAccNo.innerHTML="";
            	            var option=document.createElement("OPTION");
            	            option.text="--Select Bank Acc. Number--";
            	            option.value="";
            	            try
            	            {
            	            	cmbBankAccNo.add(option);
            	            }catch(errorObject)
            	            {
            	            	cmbBankAccNo.add(option,null);
            	            }
            	            
            	            for(var k=0;k<acc_no.length;k++)
            	            {   
            	            var	bank_ac_no1=baseResponse.getElementsByTagName("acc_no")[k].firstChild.nodeValue;
            	            var	acc_desc1=baseResponse.getElementsByTagName("acc_desc")[k].firstChild.nodeValue;
            	                 var option=document.createElement("OPTION");
            	                  option.text=acc_desc1;
            	                  option.value=bank_ac_no1;
            	                  try
            	                  {
            	                	  cmbBankAccNo.add(option);
            	                  }
            	                  catch(errorObject)
            	                  {
            	                	  cmbBankAccNo.add(option,null);
            	                  }
            	            }
            	    }
              
        
        }
    }
}

function exitfun(path) {
	window.close();
}
function numbersonly(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode == 13) {
		// t.blur();
		// return true;-------------------- for taking action when press ENTER

	}
	if (unicode != 8 && unicode != 9) {
		if (unicode < 48 || unicode > 57)
			return false
	}
}