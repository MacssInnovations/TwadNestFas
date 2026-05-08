/*
 *    Null Values Checking 
 */

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
function LoadMonthYear() {
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	var url ="../../../../../BRS_Start_Month_and_Year?command=LoadMonthYear_unit&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbBankAccNo="+cmbBankAccNo;

	
	var xmlrequest = AjaxFunction();

	xmlrequest.open("POST", url, true);

	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
	
}

function LoadMonthYear1(baseResponse) {
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
	if (flag == "success") {
		var CB_Year1 = baseResponse.getElementsByTagName("CB_Year")[0].firstChild.nodeValue;
		var CB_Month1 = baseResponse.getElementsByTagName("CB_Month")[0].firstChild.nodeValue;
		if(CB_Month1==1)
		{
			document.getElementById("txtCB_Year").value=(CB_Year1-1);	
			document.getElementById("txtCB_Month").value=12;
		}
		else{
		document.getElementById("txtCB_Year").value=CB_Year1;
		
		document.getElementById("txtCB_Month").value=(CB_Month1-1);
		}
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
function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
   
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48||unicode>57 ) 
            return false 
    }
 }
 
 
 function confirmation()
 {
            if((document.getElementById("txtCB_Year").value.length!=4)|| (document.getElementById("txtCB_Year").value.length<=0) ||(document.getElementById("txtCB_Year").value==""))
            {
                alert("Enter the correct year");
                document.getElementById("txtCB_Year").focus();
                return false;
            }
            if(document.getElementById("txtCB_Month").value=="")
            {
                alert("Select a month");
                return false;
            }
            if(document.getElementById("cmbBankAccNo").value=="t")
            {
                alert("Choose Bank Account No::");
                return false;
            }
//            if(document.getElementById("nill_ob").checked==false)
//        	{
//            	alert("Please Choose Freeze Nill OB");
//        		return false;
//        	}
            
            
          if(confirm("Are you sure do you want to Freeze BRS ?"))
          {
             var conf=document.getElementById("brsobstatus");
             conf.action="../../../../../brs_ob_status_servlet";
             conf.submit();
             return true;
          
          } 
          else 
          {
              return false;
          }  

 }
 function manipulate(xmlrequest) {

		if (xmlrequest.readyState == 4) {
			if (xmlrequest.status == 200) {

				var baseResponse = xmlrequest.responseXML
						.getElementsByTagName("response")[0];

				var tagCommand = baseResponse.getElementsByTagName("command")[0];

				var command = tagCommand.firstChild.nodeValue;
				
				if (command == "LoadMonthYear") {
					LoadMonthYear1(baseResponse);
				}
				if (command == "LoadMonthYear_unit") {
					LoadMonthYear1(baseResponse);
				}
			}
		}
	}