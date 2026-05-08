
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

function LoadMonthYear(path) {
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	var url = path
			+ "/BRS_Start_Month_and_Year?command=LoadMonthYear_unit&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbBankAccNo="+cmbBankAccNo;

	
	var xmlrequest = AjaxFunction();

	xmlrequest.open("POST", url, true);

	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
	
}
function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			
			if (command == "LoadMonthYear_unit") {
				LoadMonthYear1(baseResponse);
			}
			
		}
	}
}
function LoadMonthYear1(baseResponse) {
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
	if (flag == "success") {
		var CB_Year1 = baseResponse.getElementsByTagName("CB_Year")[0].firstChild.nodeValue;
		var CB_Month1 = baseResponse.getElementsByTagName("CB_Month")[0].firstChild.nodeValue;
		//lakshmi
		
		var mn;
		var cbyear;
		if(CB_Month1==1){
			cbyear=parseInt(CB_Year1)-1;
			mn=12;
		}else{
			cbyear=CB_Year1;
		mn=parseInt(CB_Month1)-1;
		}
		document.getElementById("txtCB_Year").value=cbyear;
		document.getElementById("txtCB_Month").value=mn;
		/*
		document.getElementById("txtCB_Year").value=CB_Year1;
		document.getElementById("txtCB_Month").value=(CB_Month1-1);*/
		
		var txtCB_Year1 = document.getElementById("txtCB_Year").value;
		var txtCB_Month1 = document.getElementById("txtCB_Month").value;
		txtCB_Year = parseInt(txtCB_Year1);
		txtCB_Month = parseInt(txtCB_Month1);
		 if(CB_Year1<txtCB_Year1)
                {
                alert("Cashook Year should be less than start month and year");
                document.getElementById("txtCB_Year").value="";
                }
                else if(CB_Year1>txtCB_Year1)
                {
    
                	
                }
                else if(CB_Year1==txtCB_Year1)
                {
            // var txtCB_Month_text = document.getElementById("txtCB_Month").value;
               //alert("txtCB_Month_text"+txtCB_Month_text+"txtCB_Month"+txtCB_Month);
                    if(CB_Month1<=txtCB_Month)
                    {
                    alert("Cashook Month should be less than start month and year");
                     document.getElementById("txtCB_Month").value="s";
                    }
                    
                }
	} else if (flag == "NoData") {
		alert("First Set BRS Initiation Month and Year");		
	} else {
		alert("Failed to Load Month and Year");		
	}
}