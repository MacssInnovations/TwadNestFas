var seq = 0;
var seqNT = 0;

var CatCode = new Array();
var CatDesc = new Array();

/** Get Browser Object */
function getTransport() {
	var req = false;
	try {
		req = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e) {
		try {
			req = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			req = false;
		}
	}
	if (!req && typeof XMLHttpRequest != 'undefined') {
		req = new XMLHttpRequest();
	}
	return req;
}

var Common_branchID = "";

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ XML req
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

var window_BankAccNumber;

function ListHeads() {

	if (window_BankAccNumber && window_BankAccNumber.open
			&& !window_BankAccNumber.closed) {
		window_BankAccNumber.resizeTo(500, 500);
		window_BankAccNumber.moveTo(250, 250);
		window_BankAccNumber.focus();
	} else {
		window_BankAccNumber = null
	}
	window_BankAccNumber = window.open("BRS_Reason_Catalogue_List.jsp",
			"mywindow1", "resizable=YES, scrollbars=yes");
	window_BankAccNumber.moveTo(250, 250);

}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   

window.onunload = function() {
	if (window_BankAccNumber && window_BankAccNumber.open
			&& !window_BankAccNumber.closed)
		window_BankAccNumber.close();
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function doParentBankAccNumbers(reason_code, reason_short_desc, reason_desc) {
	var d = document.getElementById("cmdUpdate");
	d.style.display = "block";
	var d2 = document.getElementById("cmdDelete");
	d2.style.display = "block";
	var d1 = document.getElementById("cmdAdd");
	d1.style.display = "none";

	document.getElementById("txtReasonCode").value = reason_code;
	document.getElementById("txtReasonShortDesc").value = reason_short_desc;
	document.getElementById("txtReasonDesc").value = reason_desc;
}

function callS()
{
alert("callS");
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
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

function countCheck(path)
{
 var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	
        var url = path+ "/BRS_Start_Month_and_Year?command=countCheck&cmbAcc_UnitCode="+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
}
/*function TBCheck(pp)
{
 var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
         var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	
        var url = pp+ "/BRS_Start_Month_and_Year?command=TBCheck&cmbAcc_UnitCode="+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
}  */

function closeBRS(path)
{
        var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
        var txtCB_Month = document.getElementById("txtCB_Month").value;
        var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
       
        if(cmbBankAccNo=="")
        {
        
        }
        else{
           
        var url = path+ "/BRS_Start_Month_and_Year?command=closeBRS&cmbAcc_UnitCode="+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
                }
}

function checkStatus(path)
{
        var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
        var txtCB_Month = document.getElementById("txtCB_Month").value;
        var url = path+ "/BRS_Start_Month_and_Year?command=checkStatus&cmbAcc_UnitCode="+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
}

function call_FAS_BRS_MASTER_table()
{

    
    var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	 var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	 var txtCB_Year = document.getElementById("txtCB_Year").value;
    var txtCB_Month = document.getElementById("txtCB_Month").value;
    var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
   
   var url ="/twadFas/BRS_Start_Month_and_Year?command=recinMaster&cmbAcc_UnitCode="+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo;

	// alert(url);
	var xmlrequest = AjaxFunction();

	xmlrequest.open("POST", url, true);

	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};
	xmlrequest.send(null);
}

function LoadMonthYear(path) {

	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
var url ="../../../../../BRS_Start_Month_and_Year?command=LoadMonthYear&cmbAcc_UnitCode="+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code+"&cmbBankAccNo="+cmbBankAccNo;

		 //alert(url);
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
			if (command == "LoadMonthYear") {
				LoadMonthYear1(baseResponse);
			}
                        else if (command == "checkStatus") {
				checkStatus_res(baseResponse);
			}
                         else if (command == "countCheck") {
				countCheck_one(baseResponse);
			}
                        
                        else if (command == "closeBRS") {
				closeBRS_one(baseResponse);
			}
                        else if (command == "passBal") {
				passBal_one(baseResponse);
			}
                        else if (command == "recinMaster") {
                        	recinMaster_one(baseResponse);
            			}
                        
		}
	}
}
function recinMaster_one(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert("ffff");
	     if(flag=="success")
	     {
	       var pb = baseResponse.getElementsByTagName("pb")[0].firstChild.nodeValue;
	          document.getElementById("txtPBBalance").value=pb;
	          
	     }
	     else
	     {
	     document.getElementById("txtPBBalance").value="";
	   
	     }
}
function passBal_one(baseResponse)
{
 var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
 //alert(flag);
     if(flag=="yesPass")
     {
       var passBalance = baseResponse.getElementsByTagName("passBalance")[0].firstChild.nodeValue;
          document.getElementById("pbReadonly").value=passBalance;
          call_FAS_BRS_MASTER_table();
     }
     else
     {
     document.getElementById("pbReadonly").value=0;
    // document.getElementById("txtPBBalance").value=0;
     }
}

function closeBRS_one(baseResponse)
{
	var freezedstatus = baseResponse.getElementsByTagName("freezedstatus")[0].firstChild.nodeValue;
	//alert(freezedstatus);
	if(freezedstatus=="Freezed")
	{
        var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
		     if(flag=="closeBRS")
		     {
		    	 //yes change Lachu
		       alert("BRS Freezed already:::::");
			    document.getElementById("txtCB_Month").value="s";
			    document.getElementById("cmbBankAccNo").value="";
		     }
		     else
		     {
		    	 var obpushed = baseResponse.getElementsByTagName("obpushed")[0].firstChild.nodeValue;
		    	 if(obpushed=="notpushed")
		    	 {
		    		 //yes
		    		 alert("Push OB To BRS Transaction");
			          document.getElementById("txtCB_Month").value="s";
			          document.getElementById("cmbBankAccNo").value=""; 
		    	 }
		    	 else{
					     var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
						 var cmbOffice_code = document.getElementById("cmbOffice_code").value;
						 var txtCB_Year = document.getElementById("txtCB_Year").value;
				         var txtCB_Month = document.getElementById("txtCB_Month").value;
				         var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
				        
				        var url ="/twadFas/BRS_Start_Month_and_Year?command=passBal&cmbAcc_UnitCode="+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo;
				
						// alert(url);
						var xmlrequest = AjaxFunction();
				
						xmlrequest.open("POST", url, true);
				
						xmlrequest.onreadystatechange = function() {
							manipulate(xmlrequest);
						}
						xmlrequest.send(null);
		    	 }
		     }
	}
	else
	{
		alert("Please Freeze BRS OB For This A/c No");
		document.getElementById("ChequeNoWise").disabled=true;
		document.getElementById("DocTypeWise").disabled=true;
		document.getElementById("DateWise").disabled=true;
		
	}
     
}

//function TBCheck_one(baseResponse)
//{
//var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
//     if(flag=="success")
//     {
//     
//         
//     }
//     else
//     {
//     alert("Trial Balance is not Freezed.");
//     document.getElementById("txtCB_Month").value="s";
//     }
// }

function countCheck_one(baseResponse)
{
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
 if(flag=="success")
 {
     var CB_Year = baseResponse.getElementsByTagName("CB_Year")[0].firstChild.nodeValue;
     var CB_Month = baseResponse.getElementsByTagName("CB_Month")[0].firstChild.nodeValue;
     var txtCB_Year = document.getElementById("txtCB_Year").value;
     var txtCB_Month = document.getElementById("txtCB_Month").value;
     var entryMonth=parseInt(txtCB_Month);
     if(CB_Year<=txtCB_Year)
     {
         if(CB_Year==txtCB_Year)
         {

             if(CB_Month>entryMonth)
             {
             alert("Month Should be Greater than or equal to Start Month and Year***");
              document.getElementById("txtCB_Month").value="s";
             return false;
             }
         }
 
     }
     
     else
     {
     alert("Year Should be Greater than Start Year");
     document.getElementById("txtCB_Month").value="s";
     return false;
     }
 }

}

function checkStatus_res(baseResponse)
{
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="NoData")
    {
    alert("Trial Balance is not Frozen");
     document.getElementById("txtCB_Month").value="s";
   document.getElementById("ChequeNoWise").disabled="true";
    
    }
    else  if(flag=="failure")
    {
    alert("Error in data::");
   document.getElementById("ChequeNoWise").disabled="true";
    
    }
    else
    {
    document.getElementById("ChequeNoWise").disabled=false;
    }
}

function LoadMonthYear1(baseResponse) {
	var txtCB_Year1 = document.getElementById("txtCB_Year").value;
	var txtCB_Month1 = document.getElementById("txtCB_Month").value;
	txtCB_Year = parseInt(txtCB_Year1);
	txtCB_Month = parseInt(txtCB_Month1);
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var CB_Year1 = baseResponse.getElementsByTagName("CB_Year")[0].firstChild.nodeValue;
		var CB_Month1 = baseResponse.getElementsByTagName("CB_Month")[0].firstChild.nodeValue;
		CB_Year = parseInt(CB_Year1);
		//yess
		CB_Month = parseInt(CB_Month1);
		
		document.getElementById("txtCB_Year").value=CB_Year;
		//document.getElementById("txtCB_Year").value=2012;
		document.getElementById("txtCB_Year").readOnly="true";
			//yes change  Lachu
			document.getElementById("txtCB_Month").value=CB_Month;
			// document.getElementById("txtCB_Month").value=2;
		
		document.getElementById("txtCB_Month").setAttribute('readonly','readonly');
		
      
       closeBRS("/twadFas");
		
	} else if (flag == "NoData") {
		alert("First Set BRS Initiation Month and Year");
		document.getElementById("txtCB_Year").value = "";
		document.getElementById("txtCB_Month").value = "s";
		document.getElementById("txtPBBalance").value = "";
		document.getElementById("pbReadonly").value = "";
	} else {
		alert("Failed to Load Month and Year");
		document.getElementById("txtCB_Year").value = "";
		document.getElementById("txtCB_Month").value = "s";
		document.getElementById("txtPBBalance").value = "";
		document.getElementById("pbReadonly").value = "";
	}
}  
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Main Function
 */

function doFunction_brs(Command,ListSeq,Filter) {
	/*alert("111");
	alert(ListSeq);
	alert(Filter);*/
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	var indexaccno=document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].text;
	var splaccno=indexaccno.split("-");

	if (Filter == "ChequeNoWise") {
		FilterValue = document.getElementById("txtChequeNumber").value;
	} else if (Filter == "DocTypeWise") {
		FilterValue = document.getElementById("txtdoctype").value;
		//cwise="challansame";
	} else if (Filter == "DateWise") {
		FilterValue = document.getElementById("DateWise").value;
		txtFromDate = document.getElementById("txtFromDate").value;
		txtToDate = document.getElementById("txtToDate").value;
	} else {
		FilterValue = "NoFilterValue";
	}

	if (Command == "LoadTWADTransactions") {
		if (Filter == "DateWise") {
			var fromDate = document.getElementById("txtFromDate").value;
			var toDate = document.getElementById("txtToDate").value;
			var browser = navigator.appName;

			if (browser == "Netscape") {
				var dd1 = fromDate.split('/');
				fromDate = dd1[1] + "/" + dd1[0] + "/" + dd1[2];
				var dd2 = toDate.split('/');
				toDate = dd2[1] + "/" + dd2[0] + "/" + dd2[2];
			}
			var a = fromDate.split('/');
			var b = toDate.split('/');

			var fromDate1 = new Date(a[2], a[0] - 1, a[1]);
			var toDate1 = new Date(b[2], b[0] - 1, b[1]);
			var txtCB_Month1 = ("0" + txtCB_Month);

			if (document.getElementById("txtFromDate").value == "") {
				alert("Enter From Date in the Field");
			} else if (document.getElementById("txtToDate").value == "") {
				alert("Enter To Date in the Field");
			} else if (fromDate1 >= toDate1) {
				alert("To Date Should Be Greater Than From Date");
			} else {
				var url = "../../../../../BRS_MainForm.kv?Command=LoadTWADTransactions&cmbAcc_UnitCode="
						+ cmbAcc_UnitCode
						+ "&cmbOffice_code="
						+ cmbOffice_code
						+ "&txtCB_Year="
						+ txtCB_Year
						+ "&txtCB_Month="
						+ txtCB_Month
						+ "&cmbBankAccNo="
						+ cmbBankAccNo
						+ "&ListSeq="
						+ ListSeq
						+ "&Filter="
						+ Filter
						+ "&FilterValue="
						+ FilterValue
						+ "&txtFromDate="
						+ txtFromDate + "&txtToDate=" + txtToDate+"&splAccNum="+splaccno[0];
			}
		}
		else {
			
			var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
			if(cmbAcc_UnitCode=="")
			{
				alert("Loading.... ");
				return false;
			}
			var cmbOffice_code=document.getElementById("cmbOffice_code").value;
			if(cmbOffice_code=="")
			{
				alert("Loading.... ");
				return false;
			}
			
			var txtCB_Year=document.getElementById("txtCB_Year").value;
			if(txtCB_Year=="")
			{
				alert("Choose CashBookYear");
				return false;
			}
			var txtCB_Month=document.getElementById("txtCB_Month").value;
			if(txtCB_Month=="s")
			{
				alert("Choose CashBookMonth");
				return false;
			}
			var cmbBankAccNo=document.getElementById("cmbBankAccNo").value;
			if(cmbBankAccNo=="")
			{
				alert("Choose AccountNo");
				return false;
			}

			var url = "../../../../../BRS_MainForm.kv?Command=LoadTWADTransactions&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode
					+ "&cmbOffice_code="
					+ cmbOffice_code
					+ "&txtCB_Year="
					+ txtCB_Year
					+ "&txtCB_Month="
					+ txtCB_Month
					+ "&cmbBankAccNo="
					+ cmbBankAccNo
					+ "&ListSeq="
					+ ListSeq
					+ "&Filter="
					+ Filter
					+ "&FilterValue=" + FilterValue+"&splAccNum="+splaccno[0];
			//alert(url);
		}
		
		
		
			var req = getTransport();
			req.open("GET", url, true);
			document.getElementById("imgfld").style.visibility = "visible"
			req.onreadystatechange = function() {
				handleResponse_BRS(req);
			
			}
			req.send(null);
			
	}

	if (Command == "TotalTransactions") {
		var url = "../../../../../BRS_MainForm.kv?Command=TotalTransactions&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode
				+ "&cmbOffice_code="
				+ cmbOffice_code
				+ "&txtCB_Year="
				+ txtCB_Year
				+ "&txtCB_Month="
				+ txtCB_Month
				+ "&cmbBankAccNo="
				+ cmbBankAccNo
				+ "&Filter="
				+ Filter
				+ "&FilterValue=" + FilterValue+"&splAccNum="+splaccno[0];

		var req = getTransport();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			handleResponse_BRS(req);
		}
		req.send(null);

	}

	if (Command == "LoadNONTWADTransactions") {
		
		//alert("hhh");
		var txtCB_Year=document.getElementById("txtCB_Year").value;
		if(txtCB_Year=="")
		{
			alert("Choose CashBookYear");
			return false;
		}
		var txtCB_Month=document.getElementById("txtCB_Month").value;
		if(txtCB_Month=="s")
		{
			alert("Choose CashBookMonth");
			return false;
		}
		var cmbBankAccNo=document.getElementById("cmbBankAccNo").value;
		if(cmbBankAccNo=="")
		{
			alert("Choose AccountNo");
			return false;
		}
		
		var url = "../../../../../BRS_MainForm.kv?Command=LoadNONTWADTransactions&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode
				+ "&cmbOffice_code="
				+ cmbOffice_code
				+ "&txtCB_Year="
				+ txtCB_Year
				+ "&txtCB_Month="
				+ txtCB_Month
				+ "&cmbBankAccNo=" + cmbBankAccNo + "&ListSeq=" + ListSeq+"&splAccNum="+splaccno[0];

		var req = getTransport();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			handleResponse_BRS(req);
		}
		req.send(null);
	}

}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   handleResponse()   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function handleResponse_BRS(req) {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var tagcommand = baseResponse.getElementsByTagName("command")[0];
			var Command = tagcommand.firstChild.nodeValue;

			if (Command == "LoadTWADTransactions") {
				LoadTWADTransactions(baseResponse);
			}
			if (Command == "TotalTransactions") {
				TotalTransactions(baseResponse);
			}
			if (Command == "LoadNONTWADTransactions") {
				LoadNONTWADTransactions(baseResponse);
			}
			
		}
	}
}

function handleResponse_trans(req,seq) {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var tagcommand = baseResponse.getElementsByTagName("command")[0];
			var Command = tagcommand.firstChild.nodeValue;
			if(Command == "LoadNONTWADTransactions_Type")
			{
			LoadTransactionsType(baseResponse,seq);
			}
		}
	}
}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function LoadTWADTransactions(baseResponse) {

	var filterFlag = baseResponse.getElementsByTagName("filterFlag")[0].firstChild.nodeValue;
	// alert("filterFlag"+filterFlag);
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert(flag);
	
	
	
	if (flag == "success") {

		
		document.getElementById("butSub").disabled=false;
		
		/* Load TWAD Trasactions  Values */

		/** Delete Existing Values from Grid */
		var tbody = document.getElementById("grid_body_TWAD");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		/** Get DD Number Object for finding Total Number of Records */
		var r_no = baseResponse.getElementsByTagName("r_no");
		//alert("rno===>"+r_no.length);
		seq = 0;
		var item = new Array();
		for ( var k = 0; k < r_no.length; k++) {

			//alert("Inside rno==*********==>"+r_no.length);
			//alert("Inside seq==*********==>"+seq);
			
			item[0] = baseResponse.getElementsByTagName("r_challan_no")[k].firstChild.nodeValue;
			item[1] = baseResponse.getElementsByTagName("r_challan_date")[k].firstChild.nodeValue;
			if (item[1] == 'null' || item[1] == '0') {
				item[1] = "";
			}

			item[2] = baseResponse.getElementsByTagName("r_no")[k].firstChild.nodeValue;
			//alert(item[2]);
			if (item[2] == 'null' || item[2] == '0') {
				item[2] = "";
			}
			item[3] = baseResponse.getElementsByTagName("r_date")[k].firstChild.nodeValue;
			if (item[3] == 'null' || item[3] == '0') {
				item[3] = "";
			}
			item[4] = baseResponse.getElementsByTagName("r_cheque_or_dd")[k].firstChild.nodeValue;
			if (item[4] == 'null' || item[4] == '0') {
				item[4] = "";
			}
			item[5] = baseResponse.getElementsByTagName("r_cheque_dd_no")[k].firstChild.nodeValue;
			if (item[5] == 'null' || item[5] == '0') {
				item[5] = "";
			}
			item[6] = baseResponse.getElementsByTagName("r_cheque_dd_date")[k].firstChild.nodeValue;
			item[7] = baseResponse.getElementsByTagName("cr_amount")[k].firstChild.nodeValue;
			if (item[7] == 'null' || item[7] == '0') {
				item[7] = "";
			}
			item[8] = baseResponse.getElementsByTagName("r_particulars")[k].firstChild.nodeValue;

			item[9] = baseResponse.getElementsByTagName("w_challan_no")[k].firstChild.nodeValue;
			item[10] = baseResponse.getElementsByTagName("w_challan_date")[k].firstChild.nodeValue;
			item[11] = baseResponse.getElementsByTagName("w_no")[k].firstChild.nodeValue;
			if (item[11] == 'null' || item[11] == '0') {
				item[11] = "";
			}
			item[12] = baseResponse.getElementsByTagName("w_date")[k].firstChild.nodeValue;
			if (item[12] == 'null' || item[12] == '0') {
				item[12] = "";
			}
			item[13] = baseResponse.getElementsByTagName("w_cheque_or_dd")[k].firstChild.nodeValue;
			if (item[13] == 'null' || item[13] == '0') {
				item[13] = "";
			}

			item[14] = baseResponse.getElementsByTagName("w_cheque_dd_no")[k].firstChild.nodeValue;
			if (item[14] == 'null' || item[14] == '0') {
				item[14] = "";
			}
			item[15] = baseResponse.getElementsByTagName("w_cheque_dd_date")[k].firstChild.nodeValue;
			item[16] = baseResponse.getElementsByTagName("dr_amount")[k].firstChild.nodeValue;
			if (item[16] == 'null' || item[16] == '0') {
				item[16] = "";
			}
			item[17] = baseResponse.getElementsByTagName("w_particulars")[k].firstChild.nodeValue;

			item[18] = baseResponse.getElementsByTagName("doc_type")[k].firstChild.nodeValue;

			item[19] = baseResponse.getElementsByTagName("com_doc_no")[k].firstChild.nodeValue;
			if (item[19] == 'null' || item[19] == '0') {
				item[19] = "";
			}
			item[20] = baseResponse.getElementsByTagName("com_doc_date")[k].firstChild.nodeValue;
			// alert("item[20]"+item[20]);
			if (item[20] == 'null' || item[20] == '0') {
				item[20] = "";
			}

			item[21] = baseResponse.getElementsByTagName("com_cheque_dd_no")[k].firstChild.nodeValue;
			if (item[21] == 'null' || item[21] == '0') {
				item[21] = "";
			}
			
			

			/** Create Table Row */
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq;

			/** Sl No */
			var cell0 = document.createElement("TD");
			var slno = document.createTextNode(seq + 1);
			cell0.appendChild(slno);
			mycurrent_row.appendChild(cell0);

			/** Remittance Date Creation */
			var cell2 = document.createElement("TD");
			
			var r_date = document.createElement("input");
			r_date.type = "hidden";
			//r_date.size="35";
			//r_date.type.maxlength="100";
			r_date.name = "r_date" + seq;
			r_date.id = "r_date" + seq;
			r_date.value = item[1];
			cell2.appendChild(r_date);
			var currentText = document.createTextNode(item[1]);
			cell2.appendChild(currentText);
			mycurrent_row.appendChild(cell2);

			/** WithDrawl Date */
			var cell3 = document.createElement("TD");
			var w_date = document.createElement("input");
			w_date.type = "hidden";
			w_date.name = "w_date" + seq;
			w_date.id = "w_date" + seq;
			w_date.value = item[12];
			cell3.appendChild(w_date);
			var currentText = document.createTextNode(item[12]);
			cell3.appendChild(currentText);
			mycurrent_row.appendChild(cell3);

			/** Challan or Voucher Number Creation */
			var cell4 = document.createElement("TD");
			var r_w_no = document.createElement("input");
			r_w_no.type = "hidden";
			r_w_no.name = "r_w_no" + seq;
			r_w_no.id = "r_w_no" + seq;
			r_w_no.value = item[19];
			cell4.appendChild(r_w_no);

			/** r_no and w_no */
			var doc_no = document.createElement("input");
			doc_no.type = "hidden";
			doc_no.name = "doc_no" + seq;
			doc_no.id = "doc_no" + seq;
			doc_no.value = item[2] + item[11];
			cell4.appendChild(doc_no);

			/** doc type */
			var doc_type = document.createElement("input");
			doc_type.type = "hidden";
			doc_type.name = "doc_type" + seq;
			doc_type.id = "doc_type" + seq;
			doc_type.value = item[18];
			cell4.appendChild(doc_type);

			if (item[18] == 'BR' || item[18] == 'CR') {
				item[18] = item[18] + "(" + item[2] + ")";
			}

			var currentText = document.createTextNode(item[19] + " -"
					+ item[18] + " ");
			cell4.appendChild(currentText);
			mycurrent_row.appendChild(cell4);

			/** Cheque / DD Number */
			var cell5 = document.createElement("TD");
			var ccdd_no = document.createElement("input");
			ccdd_no.type = "hidden";
			ccdd_no.name = "ccdd_no" + seq;
			ccdd_no.id = "ccdd_no" + seq;
			ccdd_no.value = item[21];
			cell5.appendChild(ccdd_no);
			var currentText = document.createTextNode(item[21] + " -" + item[4]
					+ item[13]);
			cell5.appendChild(currentText);
			mycurrent_row.appendChild(cell5);

			/** CR Amount */
			var cell6 = document.createElement("TD");
			var cr_amount = document.createElement("input");
			cr_amount.type = "hidden";
			cr_amount.name = "cr_amount" + seq;
			cr_amount.id = "cr_amount" + seq;
			cr_amount.value = item[7];
			cell6.appendChild(cr_amount);
			var currentText = document.createTextNode(item[7]);
			cell6.appendChild(currentText);
			mycurrent_row.appendChild(cell6);

			/** DR Amount */
			var cell7 = document.createElement("TD");
			var dr_amount = document.createElement("input");
			dr_amount.type = "hidden";
			dr_amount.name = "dr_amount" + seq;
			dr_amount.id = "dr_amount" + seq;
			dr_amount.value = item[16];
			cell7.appendChild(dr_amount);
			var currentText = document.createTextNode(item[16]);
			cell7.appendChild(currentText);
			mycurrent_row.appendChild(cell7);
			
			
			
			/** Entry Found in Pass Book ? */
			var cell8 = document.createElement("TD");
			cell8.style.textAlign = 'center';
			var sel = "";
			if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) {
				var sel = document
						.createElement("<INPUT type='checkbox' maxlength=10 name='EntryFoundInPassBook"
								+ seq
								+ "' id='EntryFoundInPassBook"
								+ seq
								+ "' value='Y' onclick='callme("
								+ seq
								+ ")' />");
			} else {
				sel = document.createElement("input");
				sel.type = "checkbox";
				sel.name = "EntryFoundInPassBook" + seq;
				sel.id = "EntryFoundInPassBook" + seq;
				sel.setAttribute('onclick', "callme(" + seq + ")");
				sel.value = "Y";
			}
			cell8.appendChild(sel);
			mycurrent_row.appendChild(cell8);
		
			/** Entry Date */
			var cell9 = document.createElement("TD");
			var Entry_Date = document.createElement("input");
			Entry_Date.type = "Text";
			Entry_Date.name = "Entry_Date" + seq;
			Entry_Date.id = "Entry_Date" + seq;
			Entry_Date.value = "";
			Entry_Date.maxLength = "10";
			Entry_Date.setAttribute('onblur', "dateValidation(" + seq + ")");
			Entry_Date.size = "8";
			cell9.appendChild(Entry_Date);
			mycurrent_row.appendChild(cell9);

			/** Amount in Pass Book */
			var cell10 = document.createElement("TD");
			var Amt_in_PassBk = document.createElement("input");
			Amt_in_PassBk.type = "Text";
			Amt_in_PassBk.name = "Amt_in_PassBk" + seq;
			Amt_in_PassBk.id = "Amt_in_PassBk" + seq;
			Amt_in_PassBk.value = "";
			Amt_in_PassBk.setAttribute('onblur', "callDifference(" + seq
					+ "),reason("+ seq + "),callme1(" + seq + ")");
			Amt_in_PassBk.size = "10";
			cell10.appendChild(Amt_in_PassBk);
			mycurrent_row.appendChild(cell10);

			/** Amount Difference */
			var cell11 = document.createElement("TD");
			var Amt_Diff = document.createElement("input");
			Amt_Diff.type = "Text";
			Amt_Diff.name = "Amt_Diff" + seq;
			Amt_Diff.id = "Amt_Diff" + seq;
			Amt_Diff.value = "";
			Amt_Diff.setAttribute('onchange', "reason("+ seq + "),callme1(" + seq + ")");
			Amt_Diff.size = "10";
			cell11.appendChild(Amt_Diff);
			mycurrent_row.appendChild(cell11);

			/**
			 * Dynamic Combo Creation and Loading
			 */

			/* Reason for Difference */
//			cell12 = document.createElement("TD");
//			cell12.style.textAlign = 'center';
//			var cmbCategoryCode;
//
//			if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) {
//				try {
//					cmbCategoryCode = document
//							.createElement("<select name='cmbReason4Diff" + seq
//									+ "' id='cmbReason4Diff" + seq + "' >");
//				} catch (e) {
//					alert(e.description)
//				}
//			} else {
//				cmbCategoryCode = document.createElement("select");
//				// cmbCategoryCode.id="cmbReason4Diff";
//
//				// cmbCategoryCode="cmbReason4Diff";
//				cmbCategoryCode.id = "cmbReason4Diff" + seq;
//				
//
//				cmbCategoryCode.name = "cmbReason4Diff" + seq;
//			}
//			
//			
//			
//			
//
//			var cmbCategoryCodeObj = baseResponse
//					.getElementsByTagName("reason_pair");
//			var option11 = document.createElement("option");
//			option11.value = "";
//			option11.text = "--Select--";
//
//			try {
//				cmbCategoryCode.add(option11);
//			} catch (e) {
//				cmbCategoryCode.add(option11, null);
//			}
//
//			for ( var y = 0; y < cmbCategoryCodeObj.length; y++) {
//                                //twad dhana
//				CatCode[y] = cmbCategoryCodeObj[y]
//						.getElementsByTagName("reason_code")[0].firstChild.nodeValue;
//				CatDesc[y] = cmbCategoryCodeObj[y]
//						.getElementsByTagName("reason_desc")[0].firstChild.nodeValue;
//
//				var option11 = document.createElement("option");
//                            
//				option11.value = CatDesc[y];
//				option11.text = CatDesc[y];
//
//				try {
//					cmbCategoryCode.add(option11);
//				} catch (e) {
//					cmbCategoryCode.add(option11, null);
//				}
//
//			}
//
//			cell12.appendChild(cmbCategoryCode);
//			mycurrent_row.appendChild(cell12);
			
			
			
			cell12 = document.createElement("TD");
			cell12.style.textAlign = 'center';
			var cmbReason4Diff;

			if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) {
				try {
					cmbReason4Diff = document
							.createElement("<select name='cmbReason4Diff" + seq+ "' id='cmbReason4Diff'"+seq+"onchange='reasonchg("+seq+")'>");
				} catch (e) {
					alert(e.description)
				}
			} else {
				cmbReason4Diff = document.createElement("select");
				cmbReason4Diff.id = "cmbReason4Diff"+seq;
				cmbReason4Diff.name = "cmbReason4Diff" + seq;
				cmbReason4Diff.setAttribute('onchange', "reasonchg(" + seq + ")");
			}

			var cmbCategoryCodeObj = baseResponse
					.getElementsByTagName("reason_pair");
			var option11 = document.createElement("option");
			option11.value = "";
			option11.text = "--Select--";

			try {
				cmbReason4Diff.add(option11);
			} catch (e) {
				cmbReason4Diff.add(option11, null);
			}
	                // for nontwad
			for ( var y = 0; y < cmbCategoryCodeObj.length; y++) {

				CatCode[y] = cmbCategoryCodeObj[y]
						.getElementsByTagName("reason_code")[0].firstChild.nodeValue;
				CatDesc[y] = cmbCategoryCodeObj[y]
						.getElementsByTagName("reason_desc")[0].firstChild.nodeValue;

				var option11 = document.createElement("option");

				option11.value = CatCode[y];
				option11.text = CatDesc[y];

				try {
					cmbReason4Diff.add(option11);
				} catch (e) {
					cmbReason4Diff.add(option11, null);
				}
			}

			cell12.appendChild(cmbReason4Diff);
			mycurrent_row.appendChild(cell12);
			
			

			/* Follow up Action Required */
			var cell13 = document.createElement("TD");
			cell13.style.textAlign = 'center';
			var sel = "";
			if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) {
				sel = document
						.createElement("<INPUT type='checkbox' name='FollowUpAction"
								+ seq
								+ "' id='FollowUpAction"
								+ seq
								+ "' value='Y' onclick='disableCE("
								+ seq
								+ ")' />");
			} else {
				sel = document.createElement("input");
				sel.type = "checkbox";
				sel.name = "FollowUpAction" + seq;
				sel.id = "FollowUpAction" + seq;
				sel.checked = false;
				sel.value = "Y";
				sel.setAttribute('onClick', "disableCE(" + seq + ")");
			}
			cell13.appendChild(sel);
			mycurrent_row.appendChild(cell13);

			/* Is it a clearance entry based on follow-up */
			var cell14 = document.createElement("TD");
			cell14.style.textAlign = 'center';
			var sel = "";
			if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) {
				sel = document
						.createElement("<INPUT type='checkbox' name='ClearanceEntry"
								+ seq
								+ "' id='ClearanceEntry"
								+ seq
								+ "' value='Y' onclick='list_new(" + seq + ")'/>");
			} else {
				sel = document.createElement("input");
				sel.type = "checkbox";
				sel.name = "ClearanceEntry" + seq;
				sel.id = "ClearanceEntry" + seq;
				sel.checked = false;
				sel.value = "Y";
				sel.setAttribute('onclick', "list_new(" + seq + ")");
			}
			cell14.appendChild(sel);
			mycurrent_row.appendChild(cell14);

			/** doc Date */
			var doc_date = document.createElement("input");
			doc_date.setAttribute("type", "hidden");
			doc_date.setAttribute("value", item[20]);
			doc_date.setAttribute("name", "doc_date" + seq);
			doc_date.setAttribute("id", "doc_date" + seq);
			document.getElementById("frmBRSMainForm").appendChild(doc_date);

			/*
			 * var cell33=document.createElement("TD"); var
			 * doc_date=document.createElement("input"); doc_date.type="hidden";
			 * doc_date.name="doc_date"+seq; doc_date.id="doc_date"+seq;
			 * doc_date.value=item[20]; cell33.appendChild(doc_date); var
			 * currentText=document.createTextNode(item[20]);
			 * cell33.appendChild(currentText); cell33.style.visibility =
			 * "hidden" mycurrent_row.appendChild(cell33);
			 */

			tbody.appendChild(mycurrent_row);
			// clearall();

			/** Increment Sequence Number */
			seq = seq + 1;
			document.getElementById('RecordCount').value = seq;
		}
		document.getElementById("imgfld").style.visibility = "hidden";
		document.getElementById('txtNoofRecords').value = seq;
		
		//changes on 9thjuly2012
		var vc = baseResponse.getElementsByTagName("vmcount")[0].firstChild.nodeValue;
	//	alert("vc:::"+vc);
		var crAmount = baseResponse.getElementsByTagName("crAmount")[0].firstChild.nodeValue;
		//alert("crAmount:::;"+crAmount);
		var drAmount = baseResponse.getElementsByTagName("drAmount")[0].firstChild.nodeValue;
		
		
	//	alert("drAmount:::;"+drAmount);
		document.getElementById("div1").style.visibility='visible';
		document.getElementById('crAmount').value=crAmount;
		document.getElementById('drAmount').value=drAmount;
		
		
		document.getElementById('vmAmount').value =vc ;
		//alert("::::"+filterFlag);
		if (filterFlag == "yes") {
			document.getElementById('filterFlag').value = "yes";
			var r_no1 = baseResponse.getElementsByTagName("r_no1");
			seqF = 0;
			var itemF = new Array();
			for ( var k = 0; k < r_no1.length; k++) {

				itemF[0] = baseResponse.getElementsByTagName("r_challan_no1")[k].firstChild.nodeValue;
				itemF[1] = baseResponse.getElementsByTagName("r_challan_date1")[k].firstChild.nodeValue;
				if (itemF[1] == 'null' || itemF[1] == '0') {
					itemF[1] = "";
				}

				itemF[2] = baseResponse.getElementsByTagName("r_no1")[k].firstChild.nodeValue;
				if (itemF[2] == 'null' || itemF[2] == '0') {
					itemF[2] = "";
				}
				itemF[3] = baseResponse.getElementsByTagName("r_date1")[k].firstChild.nodeValue;
				if (itemF[3] == 'null' || itemF[3] == '0') {
					itemF[3] = "";
				}
				itemF[4] = baseResponse.getElementsByTagName("r_cheque_or_dd1")[k].firstChild.nodeValue;
				if (itemF[4] == 'null' || itemF[4] == '0') {
					itemF[4] = "";
				}
				itemF[5] = baseResponse.getElementsByTagName("r_cheque_dd_no1")[k].firstChild.nodeValue;
				if (itemF[5] == 'null' || itemF[5] == '0') {
					itemF[5] = "";
				}
				itemF[6] = baseResponse
						.getElementsByTagName("r_cheque_dd_date1")[k].firstChild.nodeValue;
				itemF[7] = baseResponse.getElementsByTagName("cr_amount1")[k].firstChild.nodeValue;
				if (itemF[7] == 'null' || itemF[7] == '0') {
					itemF[7] = "";
				}
				itemF[8] = baseResponse.getElementsByTagName("r_particulars1")[k].firstChild.nodeValue;

				itemF[9] = baseResponse.getElementsByTagName("w_challan_no1")[k].firstChild.nodeValue;
				itemF[10] = baseResponse
						.getElementsByTagName("w_challan_date1")[k].firstChild.nodeValue;
				itemF[11] = baseResponse.getElementsByTagName("w_no1")[k].firstChild.nodeValue;
				if (itemF[11] == 'null' || itemF[11] == '0') {
					itemF[11] = "";
				}
				itemF[12] = baseResponse.getElementsByTagName("w_date1")[k].firstChild.nodeValue;
				if (itemF[12] == 'null' || itemF[12] == '0') {
					itemF[12] = "";
				}
				itemF[13] = baseResponse
						.getElementsByTagName("w_cheque_or_dd1")[k].firstChild.nodeValue;
				if (itemF[13] == 'null' || itemF[13] == '0') {
					itemF[13] = "";
				}

				itemF[14] = baseResponse
						.getElementsByTagName("w_cheque_dd_no1")[k].firstChild.nodeValue;
				if (itemF[14] == 'null' || itemF[14] == '0') {
					itemF[14] = "";
				}
				itemF[15] = baseResponse
						.getElementsByTagName("w_cheque_dd_date1")[k].firstChild.nodeValue;
				itemF[16] = baseResponse.getElementsByTagName("dr_amount1")[k].firstChild.nodeValue;
				if (itemF[16] == 'null' || itemF[16] == '0') {
					itemF[16] = "";
				}
				itemF[17] = baseResponse.getElementsByTagName("w_particulars1")[k].firstChild.nodeValue;

				itemF[18] = baseResponse.getElementsByTagName("doc_type1")[k].firstChild.nodeValue;

				itemF[19] = baseResponse.getElementsByTagName("com_doc_no1")[k].firstChild.nodeValue;
				if (itemF[19] == 'null' || itemF[19] == '0') {
					itemF[19] = "";
				}
				itemF[20] = baseResponse.getElementsByTagName("com_doc_date1")[k].firstChild.nodeValue;
				if (itemF[20] == 'null' || itemF[20] == '0') {
					itemF[20] = "";
				}

				itemF[21] = baseResponse
						.getElementsByTagName("com_cheque_dd_no1")[k].firstChild.nodeValue;
				if (itemF[21] == 'null' || itemF[21] == '0') {
					itemF[21] = "";
				}

				//Create Table Row 
				var mycurrent_row = document.createElement("TR");
				mycurrent_row.id = seqF;

				// Remittance Date Creation 
				var r_date1 = document.createElement("input");
				r_date1.setAttribute("type", "hidden");
				r_date1.setAttribute("value", itemF[1]);
				r_date1.setAttribute("name", "r_date1" + seqF);
				r_date1.setAttribute("id", "r_date1" + seqF);
				document.getElementById("frmBRSMainForm").appendChild(r_date1);

				// alert(itemF[1]);

				// WithDrawl Date 
				var w_date1 = document.createElement("input");
				w_date1.setAttribute("type", "hidden");
				w_date1.setAttribute("value", itemF[12]);
				w_date1.setAttribute("name", "w_date1" + seqF);
				w_date1.setAttribute("id", "w_date1" + seqF);
				document.getElementById("frmBRSMainForm").appendChild(w_date1);

				// Challan or Voucher Number Creation 
				var r_w_no1 = document.createElement("input");
				r_w_no1.setAttribute("type", "hidden");
				r_w_no1.setAttribute("value", itemF[19]);
				r_w_no1.setAttribute("name", "r_w_no1" + seqF);
				r_w_no1.setAttribute("id", "r_w_no1" + seqF);
				document.getElementById("frmBRSMainForm").appendChild(r_w_no1);

				// r_no and w_no 
				var doc_no1 = document.createElement("input");
				doc_no1.setAttribute("type", "hidden");
				doc_no1.setAttribute("value", itemF[2] + itemF[11]);
				doc_no1.setAttribute("name", "doc_no1" + seqF);
				doc_no1.setAttribute("id", "doc_no1" + seqF);
				document.getElementById("frmBRSMainForm").appendChild(doc_no1);

				// doc Date 
				var doc_date1 = document.createElement("input");
				doc_date1.setAttribute("type", "hidden");
				doc_date1.setAttribute("value", itemF[20]);
				doc_date1.setAttribute("name", "doc_date1" + seqF);
				doc_date1.setAttribute("id", "doc_date1" + seqF);
				document.getElementById("frmBRSMainForm")
						.appendChild(doc_date1);

				// doc type 
				var doc_type1 = document.createElement("input");
				doc_type1.setAttribute("type", "hidden");
				doc_type1.setAttribute("value", itemF[18]);
				doc_type1.setAttribute("name", "doc_type1" + seqF);
				doc_type1.setAttribute("id", "doc_type1" + seqF);
				document.getElementById("frmBRSMainForm")
						.appendChild(doc_type1);

				// Cheque / DD Number 
				var ccdd_no1 = document.createElement("input");
				ccdd_no1.setAttribute("type", "hidden");
				ccdd_no1.setAttribute("value", itemF[21]);
				ccdd_no1.setAttribute("name", "ccdd_no1" + seqF);
				ccdd_no1.setAttribute("id", "ccdd_no1" + seqF);
				document.getElementById("frmBRSMainForm").appendChild(ccdd_no1);

				//
				var cr_amount1 = document.createElement("input");
				cr_amount1.maxlength="3";
				cr_amount1.setAttribute("type", "hidden");
				cr_amount1.setAttribute("value", itemF[7]);
				cr_amount1.setAttribute("name", "cr_amount1" + seqF);
				cr_amount1.setAttribute("id", "cr_amount1" + seqF);
				document.getElementById("frmBRSMainForm").appendChild(
						cr_amount1);

				// DR Amount 

				// Increment Sequence Number 
				seqF = seqF + 1;

			}
			//alert(seqF);            
			document.getElementById('RecordCountF').value = seqF;
		} 
		
	} 
	else if(flag=="AlreadyFreezed")
	{
	var indexaccno=document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].text;
	var splaccno=indexaccno.split("-");
	
	//alert("splaccno==>"+splaccno[2])
	if(splaccno[2]=="OPR")
		{
		//alert("Welcome");
		alert("BRS Part-2A ,Part-2B ,Part-2C Reports are Already Freezed!......");
		document.getElementById("butSub").disabled=true;
		}
	else if(splaccno[2]=="COL")
		{
		alert("BRS Part-1  Report Already Freezed!......");
		document.getElementById("butSub").disabled=true;
		}
	else
		{
		alert("BRS Part-1,Part-2A ,Part-2B ,Part-2C Reports are Already Freezed!......");
		document.getElementById("butSub").disabled=true;
		}
	
	}
	else {
		alert("No Record Exits");
	}
	document.getElementById("imgfld").style.visibility = "hidden";
}
var winemp;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
function list_new(val) {
	if (document.getElementById("ClearanceEntry" + val).checked == true) {
		document.getElementById("FollowUpAction" + val).disabled = true;

		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var txtCB_Year = document.getElementById("txtCB_Year").value;
		var txtCB_Month = document.getElementById("txtCB_Month").value;
		var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
		var Amt_in_pasbook = document.getElementById("Amt_in_PassBk" + val).value;
		
		
		if(document.getElementById("Amt_Diff" + val).value!=0)
		{
			var Trans_Type_NT0 = document.getElementById("cmbReason4Diff0").value;
		}
		else
			{
			Trans_Type_NT0=0;
			}
//		alert(Trans_Type_NT0);
		
		
		//alert(document.getElementById("cmbReason4Diff0").value);
		//alert("Trans_Type_NT0===>"+Trans_Type_NT0);
		
		
		if(document.getElementById("dr_amount"+val).value!=0)
		   {
			   ind="CR";//fetch cr datas in popup
			  
		   }
		   else
		   {
			   ind="DR";//fetch dr datas in popup
			 
		   }
		 var doctype=document.getElementById("doc_type"+val).value;
		 
		   var docno=document.getElementById("doc_no"+val).value;
		   var docdate=document.getElementById("Entry_Date"+val).value;
		  
		if (document.getElementById("EntryFoundInPassBook" + val).checked == true) {
			
			if (winemp && winemp.open && !winemp.closed) 
		    {
		       winemp.resizeTo(500,500);
		       winemp.moveTo(250,250); 
		       winemp.focus();
		    }
		    else
		    {
		    	winemp=null;
		    }
			
			winemp = window.open("BRS_Clearance_Entry.jsp?cmbAcc_UnitCode="
							+ cmbAcc_UnitCode + "&cmbOffice_code="
							+ cmbOffice_code + "&txtCB_Year=" + txtCB_Year
							+ "&txtCB_Month=" + txtCB_Month + "&cmbBankAccNo="
							+ cmbBankAccNo + "&Amt_in_pasbook="
							+ Amt_in_pasbook+"&doctype="+doctype+"&docno="+docno+"&docdate="+docdate+"&indicator="+ind+"&Trans_Type_NT0="+Trans_Type_NT0, "list",
							"status=1,height=450,width=800,resizable=YES, scrollbars=yes");
			
			alert("BRS_Clearance_Entry.jsp?cmbAcc_UnitCode="
							+ cmbAcc_UnitCode + "&cmbOffice_code="
							+ cmbOffice_code + "&txtCB_Year=" + txtCB_Year
							+ "&txtCB_Month=" + txtCB_Month + "&cmbBankAccNo="
							+ cmbBankAccNo + "&Amt_in_pasbook="
							+ Amt_in_pasbook+"&doctype="+doctype+"&docno="+docno+"&docdate="+docdate+"&indicator="+ind+"&Trans_Type_NT0="+Trans_Type_NT0);
			winemp.moveTo(200, 200);
			winemp.focus();
		} else {
			alert("Select the Correspondence 'Entry Found in Passbook' CheckBox");
		}
	} else {
		document.getElementById("FollowUpAction" + val).disabled = false;
	}

}

window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
}

function disableCE(val) {
	if (document.getElementById("FollowUpAction" + val).checked == true) {
		document.getElementById("ClearanceEntry" + val).disabled = true;
	} else {
		document.getElementById("ClearanceEntry" + val).disabled = false;
	}
}

function reasonchg(val)
{
 if(document.getElementById("EntryFoundInPassBook" + val).checked == false)
	{	
	var cmbReason4Diff0 = document.getElementById("cmbReason4Diff" + val).value;
			if(cmbReason4Diff0==45)
			{			
			document.getElementById("FollowUpAction" + val).checked = true;
			document.getElementById("ClearanceEntry" + val).disabled = true;
			return true;
			}
			
	}	
}

function list1(val) {
	if (document.getElementById("ClearanceEntry_NT" + val).checked == true) 
	{
		document.getElementById("FollowUpAction_NT" + val).disabled = true;
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var txtCB_Year = document.getElementById("txtCB_Year").value;
		var txtCB_Month = document.getElementById("txtCB_Month").value;
		var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
		var Trans_Type_NT0 = document.getElementById("Trans_Type_NT0").value;
		var Trans_Type_NT_New0 = document.getElementById("Trans_Type_NT_New0").value;
		//alert(Trans_Type_NT_New0);
		var Particualrs_NT = document.getElementById("Particualrs_NT"+val).value;
		//alert("Particualrs_NT==>"+Particualrs_NT)
		/*var e = document.getElementById("Trans_Type_NT");
		var strUser = e.options[e.selectedIndex].value;
		alert(strUser);*/
		
		var Amt_in_pasbook=0;
		
		if(document.getElementById("dr_amount_NT"+val).value!=0)
		   {
			   ind="CR";//fetch cr datas in popup
			   Amt_in_pasbook=document.getElementById("dr_amount_NT"+val).value;
		   }
		   else
		   {
			   ind="DR";//fetch dr datas in popup
			   Amt_in_pasbook=document.getElementById("cr_amount_NT"+val).value;
		   }
		
		
			var doctype="NT"; 
		 
//			var docno=document.getElementById("doc_no"+val).value;
//			   alert("Document Number===>"+docno);
			
//		   var docdate=document.getElementById("Entry_Date"+val).value;
		
		 
		 var docno=0;
		 var docdate=document.getElementById("Entry_Date_NT"+val).value;
		 if (winemp && winemp.open && !winemp.closed) 
		    {
		       winemp.resizeTo(500,500);
		       winemp.moveTo(250,250); 
		       winemp.focus();
		    }
		    else
		    {
		    	winemp=null;
		    }
		
		// alert("url====>"+url);
		 
		winemp = window.open("BRS_Clearance_Entry.jsp?cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
				+ "&cmbBankAccNo=" + cmbBankAccNo+"&Amt_in_pasbook="+Amt_in_pasbook+"&doctype="+doctype+"&docno="+docno+"&docdate="+docdate+"&indicator="+ind+"&Trans_Type_NT0="+Trans_Type_NT0+"&Trans_Type_NT_New0="+Trans_Type_NT_New0+"&Particualrs_NT="+Particualrs_NT, "list",
				"status=1,height=450,width=800,resizable=YES, scrollbars=yes");
		winemp.moveTo(200, 200);
		winemp.focus();
	} 
	else 
	{
		document.getElementById("FollowUpAction_NT" + val).disabled = false;
	}

}



function disableCE1(val) {
	if (document.getElementById("FollowUpAction_NT" + val).checked == true) {
		document.getElementById("ClearanceEntry_NT" + val).disabled = true;
	} else {
		document.getElementById("ClearanceEntry_NT" + val).disabled = false;
	}
}
function TotalTransactions(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "success") {
		TotalTrans = baseResponse.getElementsByTagName("TotalTrans")[0].firstChild.nodeValue;
		FilterType = baseResponse.getElementsByTagName("FilterType")[0].firstChild.nodeValue;

		var tbody = document.getElementById("grid_body_TotTrans");
		var t = 0;

		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		/** Create Table Row */
		var mycurrent_row = document.createElement("TR");
		mycurrent_row.id = seq;

		var Pagination = Math.floor(TotalTrans / 10);

		if ((TotalTrans / 10) > 0) {
			Pagination = Pagination + 1;
		}

		/** Number Link Creation */
		for ( var i = 0; i < Pagination; i++) {
			var cell1 = document.createElement("TD");

			var anc = document.createElement("A");
			var url = "javascript:loadTable('" + i + 1 + "','" + FilterType
					+ "')";
			anc.href = url;

			var txtedit = document.createTextNode(i + 1);
			anc.appendChild(txtedit);

			cell1.appendChild(anc);
			mycurrent_row.appendChild(cell1);

			var cell2 = document.createElement("TD");
			cell2.innerHTML = "&nbsp;&nbsp;&nbsp;";
			mycurrent_row.appendChild(cell2);
		}

		tbody.appendChild(mycurrent_row);

	}
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function loadTable(RecordsNum, FilterType) {
	doFunction_brs('LoadTWADTransactions', RecordsNum, FilterType);
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
function ClearAll() {
	document.getElementById("txtReasonCode").value = "";
	document.getElementById("txtReasonShortDesc").value = "";
	document.getElementById("txtReasonDesc").value = "";

	var d = document.getElementById("cmdAdd");
	d.style.display = "block";
	var d1 = document.getElementById("cmdUpdate");
	d1.style.display = "none";
	var d3 = document.getElementById("cmdDelete");
	d3.style.display = "none";
}

function clr() {
	document.getElementById("txtPBBalance").value = "";
	document.getElementById("txtChequeNumber").value = "";
	document.getElementById("txtFromDate").value = "";
	document.getElementById("txtToDate").value = "";
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function nullcheck(nccommand) {
	if (document.getElementById("txtReasonShortDesc").value.length == 0) {
		alert("Enter Reason Short Description ");
		document.getElementById("txtReasonShortDesc").focus();
		return false;
	}
	if (document.getElementById("txtReasonDesc").value.length == 0) {
		alert("Enter Reason Description ");
		document.getElementById("txtReasonDesc").focus();
		return false;
	}
	return true;
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function SubmitDisabled()
{
	//alert("Welcome Submit Function ");
	
	var twad=document.getElementById("gd");
	var nontwad=document.getElementById("gd1");
	//alert("Twad==>"+twad);
	//alert("nontwad==>"+nontwad);
	if(twad)
	{
		document.getElementById("butSub").disabled=false;
	}
	if(nontwad)
	{
		document.getElementById("butSub").disabled=true;	
	}

}


function LoadNONTWADTransactions(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "success") {

		/* Load NON TWAD Trasactions Values */

		/** Get TBody Object */
		var tbody = document.getElementById("grid_body_NONTWAD");

		/** Create Table Row */
		var mycurrent_row = document.createElement("TR");
		mycurrent_row.id = seqNT;

		/** Pass Book Date */
		var cell2 = document.createElement("TD");
		var Entry_Date_NT = document.createElement("input");
		Entry_Date_NT.type = "text";
		
		Entry_Date_NT.name = "Entry_Date_NT" + seqNT;
		Entry_Date_NT.id = "Entry_Date_NT" + seqNT;
		Entry_Date_NT.value = "";
		Entry_Date_NT.maxLength = "10";
		Entry_Date_NT
				.setAttribute('onblur', "dateValidation_NT(" + seqNT + ")");
		Entry_Date_NT.size = "10";
		cell2.appendChild(Entry_Date_NT);
		mycurrent_row.appendChild(cell2);

		/** Particulars */
		var cell3 = document.createElement("TD");
		var Particualrs_NT = document.createElement("input");
		Particualrs_NT.type = "textarea";
		Particualrs_NT.id = "Particualrs_NT" + seqNT;
		Particualrs_NT.name = "Particualrs_NT" + seqNT;
		Particualrs_NT.value = "";
		cell3.appendChild(Particualrs_NT);
		mycurrent_row.appendChild(cell3);

		/** Cheque No. */
		var cell4 = document.createElement("TD");
		var ChequeNo_NT = document.createElement("input");
		ChequeNo_NT.type = "text";
		ChequeNo_NT.name = "ChequeNo_NT" + seqNT;
		ChequeNo_NT.value = "";
		ChequeNo_NT.size = "5";
		cell4.appendChild(ChequeNo_NT);
		mycurrent_row.appendChild(cell4);

		/** Details */
		var cell5 = document.createElement("TD");
		var Details_NT = document.createElement("input");
		Details_NT.type = "text";
		Details_NT.name = "Details_NT" + seqNT;
		Details_NT.value = "";
		Details_NT.size = "5";
		cell5.appendChild(Details_NT);
		mycurrent_row.appendChild(cell5);

		/** CR Amount */
		var cell6 = document.createElement("TD");
		var cr_amount_NT = document.createElement("input");
		cr_amount_NT.type = "text";
		cr_amount_NT.name = "cr_amount_NT" + seqNT;
		cr_amount_NT.id = "cr_amount_NT" + seqNT;
		cr_amount_NT.setAttribute('onchange', "CRAmount_Check(" + seqNT + ");reason(" + seqNT + ")");
		cr_amount_NT.value = "";
		cr_amount_NT.size = "5";
		cr_amount_NT.maxlength="4";
		cell6.appendChild(cr_amount_NT);
		mycurrent_row.appendChild(cell6);

		/** DR Amount */
		var cell7 = document.createElement("TD");
		var dr_amount_NT = document.createElement("input");
		dr_amount_NT.type = "text";
		dr_amount_NT.name = "dr_amount_NT" + seqNT;
		dr_amount_NT.id = "dr_amount_NT" + seqNT;
		dr_amount_NT.setAttribute('onchange', "DRAmount_Check(" + seqNT + ");reason(" + seqNT + ")");

		dr_amount_NT.value = "";
		dr_amount_NT.size = "5";
		dr_amount_NT.maxlength="4";
		cell7.appendChild(dr_amount_NT);
		mycurrent_row.appendChild(cell7);

		/**
		 * Dynamic Combo Creation and Loading
		 */

		
		cell11 = document.createElement("TD");
		cell11.style.textAlign = 'center';
		var Trans_Type_NT_New;

		if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) {
			try {
				Trans_Type_NT_New = document
						.createElement("<select name='Trans_Type_NT_New" + seqNT+ "' id='Trans_Type_NT_New'"+seqNT+" onchange=jrnl_check("+seqNT+");disbtype("+ seqNT +");reason(" + seqNT + ");>");
			} catch (e) {
				alert(e.description)
			}
		} else {
			Trans_Type_NT_New = document.createElement("select");
			Trans_Type_NT_New.id = "Trans_Type_NT_New"+seqNT;
			Trans_Type_NT_New.setAttribute('onChange',"jrnl_check("+seqNT+");disbtype("+ seqNT +");reason(" + seqNT + ")");
			Trans_Type_NT_New.name = "Trans_Type_NT_New" + seqNT;
			}

		var cmbCategoryCodeObj = baseResponse
				.getElementsByTagName("trans_pair");
		var option11 = document.createElement("option");
		option11.value = "";
		option11.text = "--Select--";

		try {
			Trans_Type_NT_New.add(option11);
		} catch (e) {
			Trans_Type_NT_New.add(option11, null);
		}
                // for nontwad
		for ( var y = 0; y < cmbCategoryCodeObj.length; y++) {

			CatCode[y] = cmbCategoryCodeObj[y]
					.getElementsByTagName("trans_code")[0].firstChild.nodeValue;
			CatDesc[y] = cmbCategoryCodeObj[y]
					.getElementsByTagName("trans_desc")[0].firstChild.nodeValue;

			var option11 = document.createElement("option");

			option11.value = CatCode[y];
			option11.text = CatDesc[y];

			try {
				Trans_Type_NT_New.add(option11);
			} catch (e) {
				Trans_Type_NT_New.add(option11, null);
			}
		}

		cell11.appendChild(Trans_Type_NT_New);
		mycurrent_row.appendChild(cell11);
		
		
		
		/* Reason for Difference */
		cell12 = document.createElement("TD");
		cell12.style.textAlign = 'center';
		var Trans_Type_NT;

		if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) {
			try {
				Trans_Type_NT = document
						.createElement("<select name='Trans_Type_NT" + seqNT+ "' id='Trans_Type_NT'"+seqNT+" onchange=jrnl_check("+seqNT+");reason(" + seqNT + ");list1(" + seqNT + ")>");
			} catch (e) {
				alert(e.description)
			}
		} else {
			Trans_Type_NT = document.createElement("select");
			Trans_Type_NT.id = "Trans_Type_NT"+seqNT;
			Trans_Type_NT.setAttribute('onChange',"jrnl_check("+seqNT+");reason(" + seqNT + ");list1(" + seqNT + ")");
			Trans_Type_NT.name = "Trans_Type_NT" + seqNT;
		}

		var cmbCategoryCodeObj = baseResponse
				.getElementsByTagName("trans_pair1");
		var option12 = document.createElement("option");
		option12.value = "";
		option12.text = "--Select--";

		try {
			Trans_Type_NT.add(option12);
		} catch (e) {
			Trans_Type_NT.add(option12, null);
		}
                // for nontwad
		for ( var y = 0; y < cmbCategoryCodeObj.length; y++) {

			CatCode[y] = cmbCategoryCodeObj[y]
					.getElementsByTagName("trans_code1")[0].firstChild.nodeValue;
			CatDesc[y] = cmbCategoryCodeObj[y]
					.getElementsByTagName("trans_desc1")[0].firstChild.nodeValue;

			var option12 = document.createElement("option");

			option12.value = CatCode[y];
			option12.text = CatDesc[y];

			try {
				Trans_Type_NT.add(option12);
			} catch (e) {
				Trans_Type_NT.add(option12, null);
			}
		}

		cell12.appendChild(Trans_Type_NT);
		mycurrent_row.appendChild(cell12);

		/* Follow up Action Required */
		var cell13 = document.createElement("TD");
		cell13.style.textAlign = 'center';
		var sel = "";
		if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) {
			sel = document
					.createElement("<INPUT type='checkbox' name='FollowUpAction_NT"
							+ seqNT + "' id='FollowUpAction_NT' value='' />");
		} else {
			sel = document.createElement("input");
			sel.type = "checkbox";
			sel.name = "FollowUpAction_NT" + seqNT;
			sel.id = "FollowUpAction_NT" + seqNT;
			sel.checked = false;
			sel.value = "Y";
			sel.setAttribute('onclick', "disableCE1(" + seqNT + ")");
		}
		cell13.appendChild(sel);
		mycurrent_row.appendChild(cell13);

		/* Is it a clearance entry based on follow-up */
		var cell14 = document.createElement("TD");
		cell14.style.textAlign = 'center';
		var sel = "";
		if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) {
			sel = document
					.createElement("<INPUT type='checkbox' name='ClearanceEntry_NT"
							+ seqNT + "' id='ClearanceEntry_NT' value=''/>");
		} else {
			sel = document.createElement("input");
			sel.type = "checkbox";
			sel.name = "ClearanceEntry_NT" + seqNT;
			sel.id = "ClearanceEntry_NT" + seqNT;
			sel.checked = false;
			sel.value = "Y";
			sel.setAttribute('onclick', "disbtype("+ seqNT +");LoadTrans("+seqNT+");");
		}
		cell14.appendChild(sel);
		mycurrent_row.appendChild(cell14);

		/* To Be Journalized */
		var cell15 = document.createElement("TD");
		cell15.style.textAlign = 'center';
		var sel = "";
		if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) {
			sel = document
					.createElement("<INPUT type='checkbox' name='Journalized readonly='readonly' onclick=click_jrnl("+seqNT+")"  
							+ seqNT + "' id='Journalized' value=''/>");
		} else {
			sel = document.createElement("input");
			sel.type = "checkbox";
			sel.name = "Journalized" + seqNT;
			sel.id = "Journalized"+seqNT;
			sel.setAttribute('onclick',"click_jrnl("+seqNT+")");
			sel.setAttribute('readOnly','readOnly');
			sel.checked = false;
			sel.value = "Y";
		}
		cell15.appendChild(sel);
		mycurrent_row.appendChild(cell15);

		tbody.appendChild(mycurrent_row);

		/** Increment Sequence Number */
		seqNT = seqNT + 1;
	//	alert("seqNT "+seqNT);
document.getElementById("test").value=seqNT;
	}
}

function disbtype(seqNT)
{
//alert("Welcome Load Trans Type!....");
	
//	alert(document.getElementById("ClearanceEntry_NT" + seqNT).checked);
	
	if(document.getElementById("ClearanceEntry_NT" + seqNT).checked == true)
		{
		alert("Please Select Type of Transaction!.....");
		document.getElementById("Trans_Type_NT" + seqNT).disabled=false;
		document.getElementById("FollowUpAction_NT" + seqNT).disabled = true;
		document.getElementById("FollowUpAction_NT" + seqNT).checked = false;
		
		return false;
	
		}
	else
		{
		//alert("Else Part Executed!....");
		document.getElementById("Trans_Type_NT" + seqNT ).disabled=true;
		document.getElementById("FollowUpAction_NT" + seqNT).disabled = false;
		document.getElementById("FollowUpAction_NT" + seqNT).checked = true;
		return true;
		}

}

function LoadTrans(seq)
{
		//alert("Sequence==>"+seq);
	var value=document.getElementById("Trans_Type_NT_New"+seq).value;
	//alert("Value==>"+value);
	var url = "../../../../../BRS_MainForm.kv?Command=LoadNONTWADTransactions_Type&type="+value;

	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		handleResponse_trans(req,seq);
	}
	req.send(null);

}

function LoadTransactionsType(baseResponse,seq)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
//alert("Flag--->"+flag);
//alert("Sequence--->"+seq);
	if (flag == "success") {
		
		
		var Trans_Type_NT=document.getElementById("Trans_Type_NT"+seq);  
        var child=Trans_Type_NT.childNodes;
        for(var i=child.length-1;i>1;i--)
        {
        	Trans_Type_NT.removeChild(child[i]);
        }                                              
        var items_id=new Array();
        var items_name=new Array(); 
        var oid=baseResponse.getElementsByTagName("trans_pair1");
        for(var k=0;k<oid.length;k++)
        {
                 items_id[k]=baseResponse.getElementsByTagName("trans_code1")[k].firstChild.nodeValue;
                 items_name[k]=baseResponse.getElementsByTagName("trans_desc1")[k].firstChild.nodeValue;				       	                                                  
                //document.getElementById("myDiv").innerHTML=txt;
                 var option=document.createElement("OPTION");
                 option.text=items_name[k];
                 option.value=items_id[k];
                 try
                 {
                	 Trans_Type_NT.add(option);
                 }
                 catch(errorObject)
                 {
                	 Trans_Type_NT.add(option,null);
                 }
        }
//        
//		cell12.appendChild(Trans_Type_NT);
//		mycurrent_row.appendChild(cell12);
	}
	
	
}



function jrnl_check(seq)
{
	var value=document.getElementById("Trans_Type_NT"+seq).value;
	
	if(value==5)
	{
		document.getElementById("Journalized"+seq).checked=true;
		document.getElementById("Journalized"+seq).setAttribute('readOnly','readOnly');
	}
	else if(value==31)
	{
		document.getElementById("Journalized"+seq).checked=true;
		document.getElementById("Journalized"+seq).setAttribute('readOnly','readOnly');
	}
	else
	{
		document.getElementById("Journalized"+seq).checked=false;
		document.getElementById("Journalized"+seq).setAttribute('readOnly','readOnly');
	}

}

function click_jrnl(seq)
{
	var value=document.getElementById("Trans_Type_NT"+seq).value;
	if(value==5)
	{
		document.getElementById("Journalized"+seq).checked=true;
		document.getElementById("Journalized"+seq).setAttribute('readOnly','readOnly');
	}
	else if(value==31)
	{
		document.getElementById("Journalized"+seq).checked=true;
		document.getElementById("Journalized"+seq).setAttribute('readOnly','readOnly');
	}
	else
	{
		document.getElementById("Journalized"+seq).checked=false;
		document.getElementById("Journalized"+seq).setAttribute('readOnly','readOnly');
	}
}

function CRAmount_Check(val) {
	var CRAmount1C = document.getElementById("cr_amount_NT" + val).value;
	if ((CRAmount1C == "") || (CRAmount1C == "0") || (CRAmount1C == "0.0")
			|| (CRAmount1C == "0.00") || (CRAmount1C == "00")
			|| (CRAmount1C == "00.00") || (CRAmount1C == "00.0")
			|| (CRAmount1C == "0 ") || (CRAmount1C == "0.0 ")
			|| (CRAmount1C == "0.00 ") || (CRAmount1C == "00 ")
			|| (CRAmount1C == "00.00 ") || (CRAmount1C == "00.0")) {
		document.getElementById("dr_amount_NT" + val).disabled = false;

	} else {
		document.getElementById("dr_amount_NT" + val).value = "";
		document.getElementById("dr_amount_NT" + val).disabled = true;
	}
}

function DRAmount_Check(val) {
	var DRAmount1C = document.getElementById("dr_amount_NT" + val).value;
	if ((DRAmount1C == "") || (DRAmount1C == "0") || (DRAmount1C == "0.0")
			|| (DRAmount1C == "0.00") || (DRAmount1C == "00")
			|| (DRAmount1C == "00.00") || (DRAmount1C == "00.0")
			|| (DRAmount1C == "0 ") || (DRAmount1C == "0.0 ")
			|| (DRAmount1C == "0.00 ") || (DRAmount1C == "00 ")
			|| (DRAmount1C == "00.00 ") || (DRAmount1C == "00.0 ")) {
		document.getElementById("cr_amount_NT" + val).disabled = false;

	} else {
		document.getElementById("cr_amount_NT" + val).value = "";
		document.getElementById("cr_amount_NT" + val).disabled = true;
	}
	var dr_amount_NT1 = parseInt(document.getElementById("dr_amount_NT" + val).value);
	if (dr_amount_NT1 != 0) {
		document.getElementById("FollowUpAction_NT" + val).checked = true;
		document.getElementById("FollowUpAction_NT" + val).readOnly = true;
	} else {
		document.getElementById("FollowUpAction_NT" + val).checked = false;
		document.getElementById("FollowUpAction_NT" + val).readOnly = false;
	}
}

function reason()
{
	var tbody = document.getElementById("grid_body_TWAD");
	var rowcount = tbody.rows.length;
          
	for ( var i = 0; i < rowcount; i++) {
	if(document.getElementById("Amt_Diff" + i).value!=0)
	{
	alert("Please Select Reason for Difference");
	return false;
	}
	}
	
	var tbody_bank = document.getElementById("grid_body_NONTWAD");
	var rowcount_bank = tbody_bank.rows.length;
          
	for (var i = 0; i < rowcount_bank; i++) {
		
		if(document.getElementById("Trans_Type_NT_New" + i).value==0)
		{
		alert("Please Select Reason for Difference");
		return false;
		}
		
//		if(document.getElementById("ClearanceEntry" + i).checked == true)
//		{
//	   if(document.getElementById("Trans_Type_NT" + i).value==0)
//		{
//		
//		   if(document.getElementById("ClearanceEntry_NT" + i).checked == true)
//			   {
//			   	  alert("Please Select Type of Transaction");
//			   	  return false;
//			   }
//		}
//		}
		
		
	}
	
	
	
}

function checkNull() {
        var count_two=0;
        var passamtZero=0;
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;

	if (txtCB_Year == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBRSMainForm.txtCB_Year.focus();
		return false;
	} else if (txtCB_Month == "s") {
		alert("Enter Cash Book Month in the Field");
		document.frmBRSMainForm.txtCB_Month.focus();
		return false;
	}
	var tbody = document.getElementById("grid_body_TWAD");
	var rowcount = tbody.rows.length;
   alert("rowcount---->"+rowcount) ;      
	for ( var i = 0; i < rowcount; i++) {
            if (document.getElementById("EntryFoundInPassBook" + i).checked == true) {
            	if (document.getElementById("Amt_in_PassBk" + i).value == 0) {
            		passamtZero++;
            	}
            	
            	if(document.getElementById("EntryFoundInPassBook" + val).checked == false)
            	{	
            	var cmbReason4Diff0 = document.getElementById("cmbReason4Diff" + val).value;
            			if(cmbReason4Diff0==45)
            			{			
            			document.getElementById("FollowUpAction" + val).checked = true;
            			document.getElementById("ClearanceEntry" + val).disabled = true;
            			return true;
            			}
            			else
            			{
            				alert("Please select Entry found in book option");
            				return false;
            			}
            	}	
            	
		if (document.getElementById("ccdd_no" + i).value != "") {
                   
			
                                          
				var txtCB_Year=document.getElementById("txtCB_Year").value;
                                    var txtCB_Month=document.getElementById("txtCB_Month").value;
                                    
                                    var entry_check=document.getElementById("Entry_Date" + i).value;
                                    var test_entry_one=entry_check.split("/");
                                   // alert("entry_check::::"+test_entry_one[1]);
                                   // alert("txtCB_Month::::"+txtCB_Month);
                                    var kk=test_entry_one[1];
                                    var test=kk.substr(0,1);
                                   // alert("hhhhhh"+kk.size());
                                    if(test==0)
                                    {
                                    	 // alert("tt:"+kk.substr(1,2));
                                    	 var tt=kk.substr(1,2);
                                    	 if(tt!=txtCB_Month)
                                    	 {
                                    		 count_two++; 
                                    	 }
                                    }
                                    else
                                    {
	                                        if(test_entry_one[1]!=txtCB_Month)
	                                        {
	                                        	
	                                        count_two++;
	                                        }
	                                        else if(test_entry_one[2]!=txtCB_Year)
	                                        {
	                                        	//alert("year");
	                                         count_two++;
	                                         }
                                    }
				
			
		} 
                    }//me checkbox closed
	}
	
	
	
	
	
	
	
	
		//Bank Transactions
		  var txtCB_Year=document.getElementById("txtCB_Year").value;
		  var txtCB_Month=document.getElementById("txtCB_Month").value;
			var txtCB_Month1 = ("0" + txtCB_Month);
		var count_bank=0;
		
		var tbody_bank = document.getElementById("grid_body_NONTWAD");
		var rowcount_bank = tbody_bank.rows.length;
              
		for (var i = 0; i < rowcount_bank; i++) {
			 var entry_check=document.getElementById("Entry_Date_NT" + i).value;
			var spldate_bank=entry_check.split("/");
			if(spldate_bank[2]==txtCB_Year)
			{
				if(spldate_bank[1]==txtCB_Month)
				{
					
				}
				else if(spldate_bank[1]==txtCB_Month1)
				{
					
				}
				else
				{
					count_bank++;
				}
			}
			else
			{
				count_bank++;
			}
			
			if(document.getElementById("ClearanceEntry_NT" + i).checked == true)
			{
			if(document.getElementById("Trans_Type_NT" + i).value==0)
                		{
                		alert("Please Select Type of Transaction");
                		return false;
                		}
			}
			
			
			if(document.getElementById("Trans_Type_NT_New" + i).value==0)
			{
			alert("Please Select Reason for Difference");
			return false;
			}
			
		}
		//alert(count_bank+"count_bank"+count_two+"count_two"+passamtZero+"passamtZero")
		if(count_bank>0)
		{
			 alert("Bank Transactions PassBookDate Should be equal to CashBook Month and Year");
             return false;
		}
		
		if(count_two>0)
                {
                alert("PassBookDate Should be equal to CashBook Month and Year");
                 return false;
                }
		if(passamtZero>0)
		{
			alert("Amount in PassBook should not be Zero");
            return false;
		}
	   else {
		   
		document.getElementById('RecordCount').value = seq;
		alert(seq);
		document.getElementById('RecordCountNT').value = seqNT;
		alert(seqNT);
		return true;
	
	}
		
//Twad Transaction
		
		
		var tbody_bank1 = document.getElementById("grid_body_TWAD");
		var rowcount_bank = tbody_bank1.rows.length;
		var count_bank1=0; 
		for (var i = 0; i < rowcount_bank; i++) {
			 var entry_check=document.getElementById("Entry_Date" + i).value;
			var spldate_bank=entry_check.split("/");
			
			//alert("YEAR==>"+spldate_bank[2]);
			//alert("CB_YEAR===>"+txtCB_Year);
			//alert("MONTH===>"+spldate_bank[1]);
			//alert("CB_MONTH===>"+txtCB_Month);
			
			if(spldate_bank[2]==txtCB_Year)
			{
				if(spldate_bank[1]==txtCB_Month)
				{
					
				}
				else if(spldate_bank[1]==txtCB_Month1)
				{
					
				}
				else
				{
					count_bank1++;
				}
			}
			else
			{
				count_bank1++;
			}
			
			
			
		}
		//alert(count_bank+"count_bank"+count_two+"count_two"+passamtZero+"passamtZero")
		if(count_bank1>0)
		{
			 alert("Bank Transactions PassBookDate Should be equal to CashBook Month and Year");
             return false;
		}
		
		if(count_two>0)
                {
                alert("PassBookDate Should be equal to CashBook Month and Year");
                 return false;
                }
		if(passamtZero>0)
		{
			alert("Amount in PassBook should not be Zero");
            return false;
		}
	   else {
		   
		document.getElementById('RecordCount').value = seq;
		//alert(seqNT);
		document.getElementById('RecordCountNT').value = seqNT;
		return true;
	
	}
		
		
 dateValidation1();	
		
		
}

function exit() {
	self.close();
}

/** Allows Number only */
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


/** Move Dr/Cr amount to Amount in passbook */
function callme(sam) {
	
	var amt = 0;
	var fg = 0;
	var fg1 = 0;
	var ii = 0;
	//alert(document.getElementById("EntryFoundInPassBook" + sam).checked);
	if (document.getElementById("EntryFoundInPassBook" + sam).checked == true) {
		
		var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
              
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
			//r.bgColor = "rgb(255,255,225)";
			
			var doctype_one=document.getElementById("doc_type" + sam).value;
		
			if(doctype_one!='CR'){
			if (document.getElementById("ccdd_no" + sam).value != "") {
                       
				if ((document.getElementById("ccdd_no" + i).value) == (document
						.getElementById("ccdd_no" + sam).value)) {
                                              
					fg1 = fg1 + 1;
					if (fg1 == 1) {
						ii = i;
					}
					r.bgColor = "#FFCCCC";
					document.getElementById("EntryFoundInPassBook" + i).checked = true;
					document.getElementById("ClearanceEntry" + i).checked = false;
					document.getElementById("Amt_in_PassBk" + i).value = (document.getElementById("dr_amount" + i).value + document.getElementById("cr_amount" + i).value);
//					var tt=(document.getElementById("w_date" + i).value + document.getElementById("r_date" + i).value);
//                                        alert("tt::"+tt);
                                        document.getElementById("Entry_Date" + i).value = (document.getElementById("w_date" + i).value + document
							.getElementById("r_date" + i).value);
					document.getElementById("Amt_Diff" + i).value = "0";
					document.getElementById("cmbReason4Diff" + i).disabled = true;
					if (fg1 > 1) {
						fg = 1;
						amt = amt+ parseInt(document.getElementById("Amt_in_PassBk" + i).value);
					}
				}
			} else {
				//ravi
				document.getElementById("EntryFoundInPassBook" + sam).checked = true;
				document.getElementById("ClearanceEntry" + sam).checked = false;
				document.getElementById("Amt_in_PassBk" + sam).value = (document
						.getElementById("dr_amount" + sam).value + document
						.getElementById("cr_amount" + sam).value);
				document.getElementById("Entry_Date" + sam).value = (document
						.getElementById("w_date" + sam).value + document
						.getElementById("r_date" + sam).value);
				document.getElementById("Amt_Diff" + sam).value = "0";
				document.getElementById("cmbReason4Diff" + sam).disabled = true;
			}
			
			
		}//cheque no wise box is clicked
			
			else 
				{
				if (document.getElementById("r_w_no" + sam).value != "") {
				//	alert("loop");
					if ((document.getElementById("r_w_no" + i).value) == (document
							.getElementById("r_w_no" + sam).value)) {
					//	alert("myscr");
	                                              
						fg1 = fg1 + 1;
						if (fg1 == 1) {
							ii = i;
						}
						r.bgColor = "#FFCCCC";
						document.getElementById("EntryFoundInPassBook" + i).checked = true;
						document.getElementById("ClearanceEntry" + i).checked = false;
						document.getElementById("Amt_in_PassBk" + i).value = (document.getElementById("dr_amount" + i).value + document.getElementById("cr_amount" + i).value);
//						var tt=(document.getElementById("w_date" + i).value + document.getElementById("r_date" + i).value);
//	                                        alert("tt::"+tt);
	                                        document.getElementById("Entry_Date" + i).value = (document.getElementById("w_date" + i).value + document
								.getElementById("r_date" + i).value);
						document.getElementById("Amt_Diff" + i).value = "0";
						document.getElementById("cmbReason4Diff" + i).disabled = true;
						if (fg1 > 1) {
							fg = 1;
							amt = amt+ parseInt(document.getElementById("Amt_in_PassBk" + i).value);
						}
					}
				} else {
					//ravi
					document.getElementById("EntryFoundInPassBook" + sam).checked = true;
					document.getElementById("ClearanceEntry" + sam).checked = false;
					document.getElementById("Amt_in_PassBk" + sam).value = (document
							.getElementById("dr_amount" + sam).value + document
							.getElementById("cr_amount" + sam).value);
					document.getElementById("Entry_Date" + sam).value = (document
							.getElementById("w_date" + sam).value + document
							.getElementById("r_date" + sam).value);
					document.getElementById("Amt_Diff" + sam).value = "0";
					document.getElementById("cmbReason4Diff" + sam).disabled = true;
				}
				
				}
		}
		if (fg == 1) {
			//alert(amt)
			amt = amt+ parseInt(document.getElementById("Amt_in_PassBk" + ii).value);
			alert("The Total Amount of the Cheque No"
					+ document.getElementById("ccdd_no" + sam).value + " is "
					+ amt);
		}
		/*document.getElementById("ClearanceEntry"+sam).checked = false;
		document.getElementById("Amt_in_PassBk"+sam).value = ( document.getElementById("dr_amount"+sam).value +  document.getElementById("cr_amount"+sam).value );
		document.getElementById("Entry_Date"+sam).value = ( document.getElementById("w_date"+sam).value +  document.getElementById("r_date"+sam).value );   
		document.getElementById("Amt_Diff"+sam).value = "0";  */
	/*	var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
		var r = tbody.rows[sam];*/
	} else if (document.getElementById("EntryFoundInPassBook" + sam).checked == false) 
	{
		var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
		//var r1 = tbody.rows;    
		for ( var i = 0; i < rowcount; i++) {
			if ((document.getElementById("ccdd_no" + i).value) == (document
					.getElementById("ccdd_no" + sam).value)) {
				document.getElementById("EntryFoundInPassBook" + i).checked = false;
				var r1 = tbody.rows[i];
				r1.bgColor="#FFFFFF";
			}
			}
		var r = tbody.rows[sam];
		r.bgColor="#FFFFFF";
		
		}
	else
	{
		document.getElementById("Amt_in_PassBk" + sam).value = "";
		document.getElementById("Entry_Date" + sam).value = "";
		document.getElementById("Amt_Diff" + sam).value = "";
		document.getElementById("cmbReason4Diff" + sam).disabled = false;
		document.getElementById("EntryFoundInPassBook" + sam).checked = false;
		document.getElementById("FollowUpAction" + sam).checked = false;
		document.getElementById("FollowUpAction" + sam).readOnly = false;
	}
	/* else {
		
		var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
			var doctype_one=document.getElementById("doc_type" + sam).value;
			
			if(doctype_one!='CR'){
			
			if (document.getElementById("ccdd_no" + sam).value != "") {
				if ((document.getElementById("ccdd_no" + i).value) == (document
						.getElementById("ccdd_no" + sam).value)) {
					
					document.getElementById("Amt_in_PassBk" + i).value = "";
					document.getElementById("Entry_Date" + i).value = "";
					document.getElementById("Amt_Diff" + i).value = "";
					document.getElementById("cmbReason4Diff" + i).disabled = false;
					document.getElementById("EntryFoundInPassBook" + i).checked = false;
					document.getElementById("FollowUpAction" + i).checked = false;
					document.getElementById("FollowUpAction" + i).readOnly = false;
				}
			} else {
				document.getElementById("Amt_in_PassBk" + sam).value = "";
				document.getElementById("Entry_Date" + sam).value = "";
				document.getElementById("Amt_Diff" + sam).value = "";
				document.getElementById("cmbReason4Diff" + sam).disabled = false;
				document.getElementById("EntryFoundInPassBook" + sam).checked = false;
				document.getElementById("FollowUpAction" + sam).checked = false;
				document.getElementById("FollowUpAction" + sam).readOnly = false;
			}
		}
			
			else
			{
				if (document.getElementById("r_w_no" + sam).value != "")
				{
				
					if ((document.getElementById("r_w_no" + i).value) == (document
							.getElementById("r_w_no" + sam).value)) 
					{
						document.getElementById("Amt_in_PassBk" + i).value = "";
						document.getElementById("Entry_Date" + i).value = "";
						document.getElementById("Amt_Diff" + i).value = "";
						document.getElementById("cmbReason4Diff" + i).disabled = false;
						document.getElementById("EntryFoundInPassBook" + i).checked = false;
						document.getElementById("FollowUpAction" + i).checked = false;
						document.getElementById("FollowUpAction" + i).readOnly = false;
					}
				}
				else
				{
					document.getElementById("Amt_in_PassBk" + i).value = "";
					document.getElementById("Entry_Date" + i).value = "";
					document.getElementById("Amt_Diff" + i).value = "";
					document.getElementById("cmbReason4Diff" + i).disabled = false;
					document.getElementById("EntryFoundInPassBook" + i).checked = false;
					document.getElementById("FollowUpAction" + i).checked = false;
					document.getElementById("FollowUpAction" + i).readOnly = false;
				}
			}
			
			
		}

		
	}  */
	var f = parseInt(document.getElementById("Amt_Diff" + sam).value);
	if (f < 0) {
		document.getElementById("cmbReason4Diff" + sam).disabled = false;
	} else if (f > 0) {
		document.getElementById("cmbReason4Diff" + sam).disabled = false;
	} else {
		document.getElementById("cmbReason4Diff" + sam).disabled = true;
	}
}

function dateValidation1() {

	document.getElementById("butSub").disabled=true;
	var g = 0;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var txtCB_Month1 = ("0" + txtCB_Month);

	var tbody = document.getElementById("grid_body_TWAD");
	var rowcount = tbody.rows.length;
	var tbody1 = document.getElementById("grid_body_NONTWAD");
	var rowcount1 = tbody1.rows.length;
	for ( var i = 0; i < rowcount; i++) {
		if (document.getElementById("EntryFoundInPassBook" + g).checked == true) {
			var Entry_Date = document.getElementById("Entry_Date" + g).value;
			var r_date = document.getElementById("r_date" + g).value;
			//Lakshmi 30oct13
			var w_date = document.getElementById("w_date" + g).value;
			var browser = navigator.appName;

			if (browser == "Netscape") {
				var dd1 = Entry_Date.split('/');
				Entry_Date = dd1[1] + "/" + dd1[0] + "/" + dd1[2];
				var dd2 = r_date.split('/');
				r_date = dd2[1] + "/" + dd2[0] + "/" + dd2[2];
				var dd3 = w_date.split('/');
				w_date = dd3[1] + "/" + dd3[0] + "/" + dd3[2];
				
			}
			var a = Entry_Date.split('/');
			var b = r_date.split('/');
			var c = w_date.split('/');
			
			var Entry_Date1 = new Date(a[2], a[0] - 1, a[1]);
			var r_date1 = new Date(b[2], b[0] - 1, b[1]);
			var w_date1 = new Date(c[2], c[0] - 1, c[1]);

			if (document.getElementById("Entry_Date" + g).value == "") {
				alert("Enter PassBook Date in the Field");
				document.getElementById("Entry_Date" + g).focus();
				document.getElementById("butSub").disabled=false;
				return false;
			} else if (Entry_Date1 < r_date1) {
				alert("PassBook Date Should Be Greater Than Remitance Date");
				document.getElementById("Entry_Date" + g).focus();
				document.getElementById("butSub").disabled=false;
				return false;
			}else if (Entry_Date1 < w_date1) {
				alert("PassBook Date Should Be Greater Than WithDrawal Date...");
				document.getElementById("Entry_Date" + g).value="";
				document.getElementById("Entry_Date" + g).focus();
				document.getElementById("butSub").disabled=false;
				return false;
			}
			
			
		}
		g = g + 1;
	}
	g = 0;

	for ( var i = 0; i < rowcount1; i++) {
		var Entry_Date_NT = document.getElementById("Entry_Date_NT" + g).value;
		var browser1 = navigator.appName;

		if (browser1 == "Netscape") {
			var dd1 = Entry_Date_NT.split('/');
			Entry_Date_NT = dd1[1] + "/" + dd1[0] + "/" + dd1[2];
		}
		var a = Entry_Date_NT.split('/');
		if (document.getElementById("Entry_Date_NT" + g).value == "") {
			alert("Enter Pass book Date in the Field...");
			document.getElementById("Entry_Date_NT" + g).focus();
			document.getElementById("butSub").disabled=false;
			return false;
		}if (document.getElementById("Particualrs_NT" + g).value == "") {
			alert("Enter Particulars in the Field...");
			document.getElementById("Particualrs_NT" + g).focus();
			document.getElementById("butSub").disabled=false;
			return false;
		}
		if (document.getElementById("ClearanceEntry" + g).checked == true) {
		
		if (document.getElementById("Trans_Type_NT" + g).value == "") {
			alert("Select Transaction Type...");
			document.getElementById("Trans_Type_NT" + g).focus();
			document.getElementById("butSub").disabled=false;
			return false;
		}
		}
		
		if ((document.getElementById("cr_amount_NT" + g).value == "") && (document.getElementById("dr_amount_NT" + g).value == "")) {
			alert("Enter CR/DR amount in the Field...");
			document.getElementById("cr_amount_NT" + g).focus();
			document.getElementById("dr_amount_NT" + g).focus();
			document.getElementById("butSub").disabled=false;
			return false;
		}
//		if (document.getElementById("dr_amount_NT" + g).value == "") {
//			alert("Enter DR Amount in the Field...");
//			document.getElementById("dr_amount_NT" + g).focus();
//			document.getElementById("butSub").disabled=false;
//			return false;
//		}
	
		if (document.getElementById("dr_amount_NT" + g).value == 0 && document.getElementById("cr_amount_NT" + g).value ==0)
		{
			alert("Both DR and CR amount 0 shoud not be allowed .. ");
		}if (document.getElementById("dr_amount_NT" + g).value > 0 && document.getElementById("cr_amount_NT" + g).value > 0)
		{
			alert("Anyone of DR and CR amount  shoud  be zero .. ");
		}
		g = g + 1;
	}
	g = 0;
	return true;
	
}

function search() {
	var txtSearch = document.getElementById("txtSearch").value;
	if (txtSearch != "") {
		var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
			//r.bgColor = "rgb(255,255,225)";
			if ((document.getElementById("ccdd_no" + i).value) == (txtSearch)) {
				r.bgColor = "#FFCCCC";
				document.getElementById("EntryFoundInPassBook" + (i - 1))
						.focus();
				document.getElementById("EntryFoundInPassBook" + i).focus();
				document.getElementById("txtSearch").value = "";
			}
		}
	} else {
		alert("Enter Cheque / DD No in the Field");
	}
}

function ClearRow() {
	var tbody = document.getElementById("grid_body_NONTWAD");
	var rowcount = tbody.rows.length;
	document.getElementById("test").value=rowcount-1;
	tbody.deleteRow(rowcount - 1);
}
//Lakshmi 30oct13
function dateValidation(seq) {
	
	

	var Entry_Date = document.getElementById("Entry_Date" + seq).value;
	var r_date = document.getElementById("r_date" + seq).value;
	
	//Lakshmi 30oct13
	var w_date = document.getElementById("w_date" + seq).value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var txtCB_Month1 = ("0" + txtCB_Month);

	var browser = navigator.appName;

	if (browser == "Netscape") {
		var dd1 = Entry_Date.split('/');
		Entry_Date = dd1[1] + "/" + dd1[0] + "/" + dd1[2];
		var dd2 = r_date.split('/');
		r_date = dd2[1] + "/" + dd2[0] + "/" + dd2[2];
		var dd3 = w_date.split('/');
		w_date = dd3[1] + "/" + dd3[0] + "/" + dd3[2];
	}
	var a = Entry_Date.split('/');
	var b = r_date.split('/');
	var c = w_date.split('/');

	var Entry_Date1 = new Date(a[2], a[0] - 1, a[1]);
	var r_date1 = new Date(b[2], b[0] - 1, b[1]);
	var w_date1 = new Date(c[2], c[0] - 1,c[1]);
	
	
	if ((document.getElementById("EntryFoundInPassBook" + seq).checked == true)
			&& (document.getElementById("Entry_Date" + seq).value == "")) {
		alert("Enter PassBook Date in the Field");
		document.getElementById("Entry_Date" + seq).focus();
		return false;
	} else if ((document.getElementById("EntryFoundInPassBook" + seq).checked == true)
			&& (Entry_Date1 < r_date1)) {
		alert("PassBook Date Should Be Greater Than Remitance Date");
		document.getElementById("Entry_Date" + seq).focus();
		return false;
	} 
	else if ((document.getElementById("EntryFoundInPassBook" + seq).checked == true)
			&& (Entry_Date1 < w_date1)) {
		alert("PassBook Date Should Be Greater Than WithDrawal Date...");
		document.getElementById("Entry_Date" + seq).value="";
		document.getElementById("Entry_Date" + seq).focus();
		return false;
	}else {
		if (document.getElementById("EntryFoundInPassBook" + seq).checked == true) {
			var Entry_Datee1 = document.getElementById("Entry_Date" + seq).value;
			var tbody = document.getElementById("grid_body_TWAD");
			var rowcount = tbody.rows.length;
			for ( var i = 0; i < rowcount; i++) {
				var r = tbody.rows[i];
				//if
				var doctype_one=document.getElementById("doc_type" + seq).value;
				if(doctype_one!='CR')
				 {
				
							if (document.getElementById("ccdd_no" + seq).value != "") {
								if ((document.getElementById("ccdd_no" + i).value) == (document
										.getElementById("ccdd_no" + seq).value)) {
									r.bgColor = "#FFCCCC";
									document.getElementById("Entry_Date" + i).value = Entry_Datee1;
								}
							}
				  }
				else
				  {
							if (document.getElementById("r_w_no" + seq).value != "") 
							{
								
									if ((document.getElementById("r_w_no" + i).value) == (document
											.getElementById("r_w_no" + seq).value))
								{
									r.bgColor = "#FFCCCC";
									document.getElementById("Entry_Date" + i).value = Entry_Datee1;
								}
								
							}
					
				  }
			}
		}
		return true;
	}
}

function dateValidation_NT(seqNT) {
	//alert("hhh");
	var Entry_Date_NT = document.getElementById("Entry_Date_NT" + seqNT).value;

	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var txtCB_Month1 = ("0" + txtCB_Month);

/*	var browser = navigator.appName;
	alert(browser);
	if (browser == "Netscape") {
		var dd1 = Entry_Date_NT.split('/');
		Entry_Date_NT = dd1[1] + "/" + dd1[0] + "/" + dd1[2];
	}
	var a = Entry_Date_NT.split('/');
	alert(Entry_Date_NT);  */
var spldate=Entry_Date_NT.split("/");
if(spldate[2]==txtCB_Year)
{
	if(spldate[1]==txtCB_Month1)
	{
		
	}
	else if(spldate[1]==txtCB_Month)
	{
		
	}
	else
	{
		alert("PassBook Date should be equal to Cashbook Year and Month");
		document.getElementById("Entry_Date_NT" + seqNT).value="";
	}
}
else
{
	alert("PassBook Date should be equal to Cashbook Year & Month");
	document.getElementById("Entry_Date_NT" + seqNT).value="";
}


	if (document.getElementById("Entry_Date_NT" + seqNT).value == "") {
		alert("Enter Pass book Date in the Field");
		document.getElementById("Entry_Date_NT" + seqNT).focus();
		return false;
	}
	
	else {
		return true;
	}
}

function callme1(sam) {
	var f = parseInt(document.getElementById("Amt_Diff" + sam).value)
	if (f < 0) {
		document.getElementById("cmbReason4Diff" + sam).disabled = false;
	} else if (f > 0) {
		document.getElementById("cmbReason4Diff" + sam).disabled = false;
	} else {
		document.getElementById("cmbReason4Diff" + sam).disabled = true;
	}
}

/** Difference Calculate */
function callDifference(indexID) {
	if (document.getElementById("dr_amount" + indexID).value == "") {
		document.getElementById("dr_amount" + indexID).value == 0;
	}
	var RWamt = (document.getElementById("dr_amount" + indexID).value + document
			.getElementById("cr_amount" + indexID).value);
	var Passamt = document.getElementById("Amt_in_PassBk" + indexID).value;
	document.getElementById("Amt_Diff" + indexID).value = parseInt(RWamt)
			- parseInt(Passamt);

	if ((parseInt(RWamt) - parseInt(Passamt)) != 0) {
		document.getElementById("FollowUpAction" + indexID).checked = true;
		document.getElementById("FollowUpAction" + indexID).readOnly = true;
	}
        
        if(document.getElementById("Amt_in_PassBk" + indexID).value>document.getElementById("dr_amount" + indexID).value)
        {
      //  alert("3");
      //  document.getElementById("cmbReason4Diff" + indexID).length="0";
        //document.getElementById("cmbReason4Diff"+indexID).options[document.getElementById("cmbReason4Diff"+indexID).selectedIndex].text="EXCESS DEBIT MADE IN THE PASS BOOK";
        document.getElementById("cmbReason4Diff" + indexID).value="EXCESS DEBIT MADE IN THE PASS BOOK";
       // document.getElementById("cmbReason4Diff" + indexID).readonly="true";
        }
}

function FollwUpCheck(indexID) {
	var dr_amount_NT1 = parseInt(dr_amount_NT);
	if (dr_amount_NT1 != 0) {
		document.getElementById("FollowUpAction_NT" + indexID).checked = true;
		document.getElementById("FollowUpAction_NT" + indexID).readOnly = true;
	} else {
		document.getElementById("FollowUpAction_NT" + indexID).checked = false;
		document.getElementById("FollowUpAction_NT" + indexID).readOnly = false;
	}
}
