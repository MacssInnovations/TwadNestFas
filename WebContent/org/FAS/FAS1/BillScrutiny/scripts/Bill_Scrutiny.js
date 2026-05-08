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
var check_memo_list;
function viewDetails(path)
{
	var unitcode = document.getElementById("cmbAcc_UnitCode").value;
    var offid = document.getElementById("cmbOffice_code").value;
    var yr = document.getElementById("cboCashBook_Year").value;
    var cboCashBook_Month = document.getElementById("cboCashBook_Month").value;
    var cboBillNo = document.getElementById("cboBillNo").value;
	check_memo_list= window.open("../../../../../org/FAS/FAS1/BillScrutiny/jsps/Bill_Scrutiny_list.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&cashbook_yr="+yr+"&cboCashBook_Month="+cboCashBook_Month+"&cboBillNo="+cboBillNo,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
 	     
	check_memo_list.moveTo(250,250);  
	check_memo_list.focus();
}

function initialLoads(path) {
	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
    var cboOffice_code = document.getElementById("cmbOffice_code").value;
    var cboCashBook_Year = document.getElementById("cboCashBook_Year").value;
    var cboCashBook_Month = document.getElementById("cboCashBook_Month").value; 
	var url = path + "/Bill_Scrutiny?command=gett&cmbAcc_UnitCode="
				+ cboAcc_UnitCode + "&cmbOffice_code=" + cboOffice_code
				+ "&cboCashBook_Year=" + cboCashBook_Year
				+ "&cboCashBook_Month=" + cboCashBook_Month	;
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
			
			if (command == "gett") {
			
				firstLoad(baseResponse);
			} else if (command == "saveFunc") {
			
				saveFunc1(baseResponse);
			} else if (command == "getDetails") {
				getDetails1(baseResponse);
			}
			else if (command == "loadDetails") {
				loadDetails1(baseResponse);
			}
		}
	}
}

function loadDetails(path) {
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;

	var url = path
			+ "/Bill_Scrutiny_Details.bs?command=loadDetails&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code;
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}


function loadDetails1(baseResponse) {
	var seq =0;
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var count=baseResponse.getElementsByTagName("slno")[0].firstChild.nodeValue;
	
         var tbody=document.getElementById("grid_body");
         var t=0;
         for(t=tbody.rows.length-1;t>=0;t--)
             {
                tbody.deleteRow(0);
             } 
                                            
         for(var i=0;i< count;i++)
         {          
        	
                 var cmbAcc_UnitCode=baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[i].firstChild.nodeValue;
        	    var cmbOffice_code=baseResponse.getElementsByTagName("ACCOUNTING_FOR_OFFICE_ID")[i].firstChild.nodeValue;
                 var slno1=baseResponse.getElementsByTagName("slno1")[i].firstChild.nodeValue;
                 var manda=baseResponse.getElementsByTagName("mandate")[i].firstChild.nodeValue;
                 var noappp=baseResponse.getElementsByTagName("noapp")[i].firstChild.nodeValue;
                 var checkdes=baseResponse.getElementsByTagName("checkdesc")[i].firstChild.nodeValue;
                 var checkcodee=baseResponse.getElementsByTagName("checkcode")[i].firstChild.nodeValue;
               
                var mycurrent_row=document.createElement("TR");
                 mycurrent_row.id=seq;

                 var cell2=document.createElement("TD");
                 var sno1=document.createElement("input");
	     			sno1.type="hidden";
	     			sno1.name="checkdes1";
	     			sno1.id="checkdes1";
	     			sno1.value=checkdes; 
                 var currentText1=document.createTextNode(checkdes);
                 cell2.appendChild(currentText1);
                 cell2.appendChild(sno1);
                 cell2.style.textAlign="left";
                 mycurrent_row.appendChild(cell2); 
            
                 var cell2=document.createElement("TD"); 
                 var sno1=document.createElement("input");
	     			sno1.type="hidden";
	     			sno1.name="manda1";
	     			sno1.id="manda1";
	     			sno1.value=manda; 
                 var currentText1=document.createTextNode(manda);
                 cell2.appendChild(currentText1);
                 cell2.appendChild(sno1);
                 cell2.style.textAlign="center";
                 mycurrent_row.appendChild(cell2); 
     
                 var cell2=document.createElement("TD");   
                 var sno1=document.createElement("input");
	     			sno1.type="hidden";
	     			sno1.name="noappp1";
	     			sno1.id="noappp1";
	     			sno1.value=noappp; 
                 var currentText1=document.createTextNode(noappp);
                 cell2.appendChild(currentText1);
                 cell2.appendChild(sno1);
                 cell2.style.textAlign="center";
                 mycurrent_row.appendChild(cell2);
                 
                 var cell=document.createElement("TD");
                 cell.align='CENTER';
                 var anc=document.createElement("input");
                 anc.type="checkbox";
                 anc.value="CHECKED";
                 anc.id="verify_select";
                 anc.name="verify_select"; 
                 cell.appendChild(anc);
                 
                 var anc1=document.createElement("input");
                 anc1.type="hidden";
                 anc1.id="verify_select_status";
                 anc1.name="verify_select_status"; 
                 cell.appendChild(anc1);
                 mycurrent_row.appendChild(cell);
                 
                    var cell2=document.createElement("TD"); 
                    cell2.style.display="none";
                    var checkcodee1=document.createElement("input");
                    checkcodee1.type="hidden";
                    checkcodee1.name="checkcodee";
                    checkcodee1.id="checkcodee";
                    checkcodee1.value=checkcodee;
                    var currentText1=document.createTextNode(checkcodee);
                    cell2.appendChild(currentText1);
                    cell2.appendChild(checkcodee1);
                    mycurrent_row.appendChild(cell2);
       
                    var cell2=document.createElement("TD"); 
                    cell2.style.display="none";
                    var checkcodee1=document.createElement("input");
                    checkcodee1.type="hidden";
                    checkcodee1.name="cmbAcc_UnitCode";
                    checkcodee1.id="cmbAcc_UnitCode";
                    checkcodee1.value=cmbAcc_UnitCode;
                    var currentText1=document.createTextNode(cmbAcc_UnitCode);
                    cell2.appendChild(currentText1);
                    cell2.appendChild(checkcodee1);
                    mycurrent_row.appendChild(cell2);
                    
                    var cell2=document.createElement("TD"); 
                    cell2.style.display="none";
                    var checkcodee1=document.createElement("input");
                    checkcodee1.type="hidden";
                    checkcodee1.name="cmbOffice_code";
                    checkcodee1.id="cmbOffice_code";
                    checkcodee1.value=cmbOffice_code;
                    var currentText1=document.createTextNode(cmbOffice_code);
                    cell2.appendChild(currentText1);
                    cell2.appendChild(checkcodee1);
                    mycurrent_row.appendChild(cell2);
                
                 tbody.appendChild(mycurrent_row);
                 seq++;	
         		}
         
	}
	else if(flag == "NoData") {
		var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
            {
               tbody.deleteRow(0);
            } 
		alert("Record Does Not Exist");
	}
	else
	{
		var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
            {
               tbody.deleteRow(0);
            } 
	alert("Failed to Load");
}
	
}

function firstLoad(baseResponse) {
	//alert("firstLoad");
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	if (month >= 10) {
		var date = (day + "/" + month + "/" + year);
	} else {
		var date = (day + "/0" + month + "/" + year);
	}
	document.frm_Bill_Scrutiny.txtScrutinyDate.value = date;
	//alert("check");	
	var flag1 = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert(flag1);
	if(flag1 == "success")
	{
		var memo = baseResponse.getElementsByTagName("memo")[0].firstChild.nodeValue;
		if(memo=="yes")
		{
			var len1 = baseResponse.getElementsByTagName("billNo").length;
			//alert(len1);
			for ( var i = 0; i < len1; i++) {
				var billNo = baseResponse
						.getElementsByTagName("billNo")[i].firstChild.nodeValue;
				//alert(billNo);
				var se = document.getElementById("cboBillNo");
				var op = document.createElement("OPTION");
				op.value = billNo;
				var txt = document.createTextNode(billNo);
				op.appendChild(txt);
				se.appendChild(op);
		
			}
		}
		else
		{
			alert("Memo Of Payment Entry is Not Done");
			return false;
		}
	}
	else if(flag1 == "failure")
	{
		var b_value="s";
		alert("NO record found for Bill NO");
		document.getElementById("cboBillNo").length=0;
		var se = document.getElementById("cboBillNo");
		var op = document.createElement("OPTION");
		op.value =b_value;
		var txt = document.createTextNode("Select");
		op.appendChild(txt);
		se.appendChild(op);

		//document.getElementById("cboBillNo").options[document.getElementById("cboBillNo").selectedIndex].value=s;
		//document.getElementById("cboBillNo").options[document.getElementById("cboBillNo").selectedIndex].text="Select";
		
	}
}

function getDetails(path) {
	//alert(path);
	document.getElementById("txtBillDate").value="";
	document.getElementById("txtBillAmount").value="";
	document.getElementById("txtSanctionProceedingNo").value="";
	document.getElementById("txtSanctionProceedingDate").value="";
	document.getElementById("txtAccountHeadCode").value="";
	document.getElementById("txtAccountHeadCodeDesc").value="";
	document.getElementById("txtSubLedgerType").value="";
	document.getElementById("txtSubLedgerCode").value="";
	document.getElementById("txtDeductedAmount").value="";
	document.getElementById("txtNetAmount").value="";	
	document.getElementById("txtScrutinyDate").value="";
	document.getElementById("mtxtRemarks").value="";
	
	document.getElementById("txtDeductedAmount").value="";
	document.getElementById("txtNetAmount").value="";	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("cboCashBook_Year").value;
	var cboCashBook_Month = document.getElementById("cboCashBook_Month").value;
	var cboBillNo = document.getElementById("cboBillNo").value;
	
	if (cboBillNo == "" || cboBillNo=="s") {
		alert("Select Bill No in the Field");
		document.frm_Bill_Scrutiny.cboBillNo.focus();
	} else
	{
	var url = path
			+ "/Bill_Scrutiny?command=getDetails&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code + "&cboCashBook_Year="
			+ cboCashBook_Year + "&cboCashBook_Month=" + cboCashBook_Month + "&cboBillNo="
			+ cboBillNo;
	}
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}

function getDetails1(baseResponse) {
	//alert("getDetails1(baseResponse)");
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {				
	var billDate = baseResponse.getElementsByTagName("billDate")[0].firstChild.nodeValue;
	var billAmount = baseResponse.getElementsByTagName("billAmount")[0].firstChild.nodeValue;
	var totalsancamt = baseResponse.getElementsByTagName("totalsancamt")[0].firstChild.nodeValue;
	
	var proceedingNo = baseResponse.getElementsByTagName("proceedingNo")[0].firstChild.nodeValue;
	var proceedingDate = baseResponse.getElementsByTagName("proceedingDate")[0].firstChild.nodeValue;
	var AccHeadCode = baseResponse.getElementsByTagName("AccHeadCode")[0].firstChild.nodeValue;
	var AccHeadCodeDesc = baseResponse.getElementsByTagName("AccHeadCodeDesc")[0].firstChild.nodeValue;
	var subLedgerTypeCodeDesc = baseResponse.getElementsByTagName("subLedgerTypeCodeDesc")[0].firstChild.nodeValue;
	var remarks = baseResponse.getElementsByTagName("remarks")[0].firstChild.nodeValue;
	var subLedgerCodeDesc1 = baseResponse.getElementsByTagName("subLedgerCodeDesc1")[0].firstChild.nodeValue;

	document.frm_Bill_Scrutiny.txtBillDate.value = billDate;
	document.frm_Bill_Scrutiny.txtBillAmount.value = totalsancamt;
	document.frm_Bill_Scrutiny.txtDeductedAmount.value = parseInt(totalsancamt)-parseInt(billAmount);
	document.frm_Bill_Scrutiny.txtSanctionProceedingNo.value = proceedingNo;
	document.frm_Bill_Scrutiny.txtSanctionProceedingDate.value = proceedingDate;
	document.frm_Bill_Scrutiny.txtAccountHeadCode.value = AccHeadCode;
	document.frm_Bill_Scrutiny.txtAccountHeadCodeDesc.value = AccHeadCodeDesc;
	document.frm_Bill_Scrutiny.txtSubLedgerType.value = subLedgerTypeCodeDesc;
	document.frm_Bill_Scrutiny.mtxtRemarks.value = remarks;
	document.frm_Bill_Scrutiny.txtSubLedgerCode.value = subLedgerCodeDesc1;
	document.frm_Bill_Scrutiny.txtNetAmount.value =billAmount;
	}
	else if(flag == "NoData") {
		alert("Record Does Not Exist");
	}
	else
	{
	alert("Failed to Load");
}
	
}
function calculateNetAmount()
{
	var txtBillAmount = document.getElementById("txtBillAmount").value;
	var txtDeductedAmount = document.getElementById("txtDeductedAmount").value;
	
	var BillAmount1 = parseInt(txtBillAmount);
	var DeductedAmount1 = parseInt(txtDeductedAmount);
	if(DeductedAmount1 > BillAmount1)
	{
		alert("Deducted Amount Should be Less than Bill Amount");
	}
	var BillAmount = parseInt(txtBillAmount);
	var DeductedAmount = parseInt(txtDeductedAmount);
	var NetAmount = BillAmount - DeductedAmount;	
	document.frm_Bill_Scrutiny.txtNetAmount.value = NetAmount;
	
}
function saveFunc(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("cboCashBook_Year").value;
	var cboCashBook_Month = document.getElementById("cboCashBook_Month").value;
	var cboBillNo = document.getElementById("cboBillNo").value;
	var BillScrunityDate = document.getElementById("txtScrutinyDate").value;
	var txtBillAmount = document.getElementById("txtBillAmount").value;
	var txtDeductedAmount = document.getElementById("txtDeductedAmount").value;
	var DeductedAmount = document.getElementById("txtDeductedAmount").value;
	var NetAmount = document.getElementById("txtNetAmount").value;
	
	if(document.frm_Bill_Scrutiny.rdoScrutinyDone[0].checked==true)
	{
		BillScrunityDone=document.frm_Bill_Scrutiny.rdoScrutinyDone[0].value;
	}
   else
	{
	   BillScrunityDone=document.frm_Bill_Scrutiny.rdoScrutinyDone[1].value;
	}

	var BillAmount1 = parseInt(txtBillAmount);
	var DeductedAmount1 = parseInt(txtDeductedAmount);		
	
	if (cboBillNo == "" || cboBillNo=="s") {
		alert("Select Bill No in the Field");
		document.frm_Bill_Scrutiny.cboBillNo.focus();
	}else if (DeductedAmount == "") {
		alert("Enter Deducted Amount in the Field");
		document.frm_Bill_Scrutiny.txtDeductedAmount.focus();		
	}else if(DeductedAmount1 > BillAmount1)
	{
		alert("Deducted Amount Should be Less than Bill Amount");
	}else if (NetAmount == "") {
		alert("Enter Net Amount in the Field");
		document.frm_Bill_Scrutiny.txtNetAmount.focus();
	}else if (BillScrunityDate == "") {
		alert("Enter Bill Scrunity Date in the Field");
		document.frm_Bill_Scrutiny.txtScrutinyDate.focus();
	}

	else {
	var url = path
	+ "/Bill_Scrutiny?command=saveFunc&cmbAcc_UnitCode="
	+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code + "&cboCashBook_Year="
	+ cboCashBook_Year + "&cboCashBook_Month=" + cboCashBook_Month + "&cboBillNo="
	+ cboBillNo + "&BillScrunityDate=" + BillScrunityDate + "&BillScrunityDone="
	+ BillScrunityDone+ "&DeductedAmount=" + DeductedAmount + "&NetAmount="
	+ NetAmount;

	//alert(url);

	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}
}
function saveFunc1(baseResponse){
	var flag = baseResponse
	.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if(flag == "success")
	{
		alert("Record Updated Successfully");
		refresh();
	}
	else
	{
		alert("Record Updation Failed");
	}
}

function refresh()
{
	
	document.getElementById("cboBillNo").value="s";
	document.getElementById("txtBillDate").value="";
	document.getElementById("txtBillAmount").value="";
	document.getElementById("txtSanctionProceedingNo").value="";
	document.getElementById("txtSanctionProceedingDate").value="";
	document.getElementById("txtAccountHeadCode").value="";
	document.getElementById("txtAccountHeadCodeDesc").value="";
	document.getElementById("txtSubLedgerType").value="";
	document.getElementById("txtSubLedgerCode").value="";
	document.getElementById("txtDeductedAmount").value="";
	document.getElementById("txtNetAmount").value="";	
	document.getElementById("txtScrutinyDate").value="";
	document.getElementById("mtxtRemarks").value="";
	
}
function clearBillNo()
{
	document.getElementById("txtBillDate").value="";
	document.getElementById("txtBillAmount").value="";
	document.getElementById("txtSanctionProceedingNo").value="";
	document.getElementById("txtSanctionProceedingDate").value="";
	document.getElementById("txtAccountHeadCode").value="";
	document.getElementById("txtAccountHeadCodeDesc").value="";
	document.getElementById("txtSubLedgerType").value="";
	document.getElementById("txtSubLedgerCode").value="";
	document.getElementById("txtDeductedAmount").value="";
	document.getElementById("txtNetAmount").value="";	
	document.getElementById("txtScrutinyDate").value="";
	document.getElementById("mtxtRemarks").value="";
	document.getElementById("cboBillNo").value="s";
	
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

function exitfun(path) {
	window.close();
}