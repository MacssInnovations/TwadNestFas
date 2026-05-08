//  InterBankTransfer_MultipleBanks  //
var seq = 0;
var seq1 = 1;
var CatCode = new Array();
var CatDesc = new Array();
var items=new Array();
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

function manipulate(xmlrequest) {
	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;

			if (command == "add") {
				addRow(baseResponse);
			} else if (command == "deleted") {
				deleteRow(baseResponse)
			} else if (command == "update") {
				updateRow(baseResponse);
			} else if (command == "Edit") {
				Edit1(baseResponse);
			} else if (command == "load_bank_deatils") {
				load_bank_deatils(baseResponse);
			} else if (command == "LoadData") {
				LoadData_View(baseResponse);
			} else if (command == "AddRow") {
				AddRow1(baseResponse);
			} else if (command == "load_Voucher_No") {
				load_Voucher_No1(baseResponse);
			} else if (command == "load_Voucher_details") {
				load_Voucher_details1(baseResponse);
			}
		}
	}
}


///////////////////////////////////////////    TB_checking and Calender control return value handling

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
    if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
    {
            // call_clr();
             cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             cmbOffice_code=document.getElementById("cmbOffice_code").value;
             var TB_date=fromcal_dateCtrl.value;
             //alert(fromcal_dateCtrl.value+"b4url")
             if(fromcal_dateCtrl.value.length!=0)
             {
                 var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                        //alert(url);
                 var req=getTransport();
                 req.open("GET",url,true); 
                 req.onreadystatechange=function()
                 {
                   check_TB(req,fromcal_dateCtrl);
                 }   
                 req.send(null);
            }
    }
}
function doFunction(Command, param) {
	if (Command == "load_bank_deatils") {

		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cr_dr_indicator = param;
		var bank_acc_no = "";

		if (cr_dr_indicator == 'CR')
			bank_acc_no = document.getElementById("txtBankAccountNo").value;
		else if (cr_dr_indicator == 'DR')
			bank_acc_no = document.getElementById("txtSubBankAccountNo").value;

		if (bank_acc_no.length != 0) {
			var url = "../../../../../InterBankTransfer_MultipleBanks?Command=load_bank_deatils&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode
					+ "&bank_acc_no="
					+ bank_acc_no
					+ "&cr_dr_indicator=" + cr_dr_indicator + "&seq=0";
			var req = AjaxFunction();
			// alert(url)
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				manipulate(req);
			}
			req.send(null);
		}
	}
}
function doFunction1(seq) {
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cr_dr_indicator = "DR";
	var bank_acc_no = "";
	bank_acc_no = document.getElementById("txtSubBankAccountNo" + seq).value;
	if (bank_acc_no.length != 0) {
		var url = "../../../../../InterBankTransfer_MultipleBanks?Command=load_bank_deatils&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode
				+ "&bank_acc_no="
				+ bank_acc_no
				+ "&cr_dr_indicator=" + cr_dr_indicator + "&seq=" + seq;
		var req = AjaxFunction();
		// alert(url)
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			manipulate(req);
		}
		req.send(null);
	}

}
function call_date(dateCtrl)                        // TB_checking 
{
  //  call_clr();
    if(checkdt(dateCtrl))
    {
        //doFunction('check_TB',dateCtrl.value);
         var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
         var cmbOffice_code=document.getElementById("cmbOffice_code").value;
         var TB_date=dateCtrl.value;
       
         if(dateCtrl.value.length!=0)
         {
             var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
             var req=getTransport();
             req.open("GET",url,true); 
             req.onreadystatechange=function()
             {
               check_TB(req,dateCtrl);
             }   
             req.send(null);
       }
        //doFunction('load_Receipt_No','null');
    }
    else
    {
      document.getElementById("txtCrea_date").value="";
    }
}
function check_TB(req,dateCtrl)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
              {
                 //doFunction('load_Receipt_No','null');                 //return true;
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed*****");//return false;//
                    dateCtrl.focus();
                    document.getElementById("txtCrea_date").value="";
               }
             else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");//return false;//
                    dateCtrl.focus();
                    document.getElementById("txtCrea_date").value="";     
               }
            dateCheck(dateCtrl); 
        }
    }
}


///////////////////////////////////////////Date validation on 04-01-2018 //////////////////////

function dateCheck(datechk)
{
	//alert("WELCOME!.........");
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    //var txtCrea_date=document.getElementById("txtCrea_date").value;
    var txtCrea_date=datechk.value;
    
    if(datechk.value.length!=0)
    {
    var url="../../../../../Receipt_SL.view?Command=check_Date&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date;
    //alert("URL===>"+url);
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
      check_Date(req,datechk);
    } ;  
    req.send(null);
    }

}
function check_Date(req,datechk)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            //alert("Flag----->"+flag);
            if(flag=="success")
              {
                 //doFunction('load_Receipt_No','null');                 //return true;
            	document.getElementById("butSub").disabled=false;
              }
            else if(flag=="failure")
            {
            	datechk.value=""; 
            	alert("Document Date is Less than DATE_EFFECTIVE_FROM");
            	datechk.focus();
            	document.getElementById("butSub").disabled=true;
            	
            	document.getElementById("txtReceipt_No").value="";
                 
            }
            else if(flag=="success1")
            {
               //doFunction('load_Receipt_No','null');                 //return true;
            	document.getElementById("butSub").disabled=false;
            }
           else if(flag=="failure1")
           {
        	  alert("Document Date is Greater than DATE_OF_CLOSURE");
        	  datechk.value=""; 
          		//alert("Document Date is Less than DATE_ALLOWED_UPTO date");
          		datechk.focus();
          		document.getElementById("butSub").disabled=true;
          		document.getElementById("txtReceipt_No").value="";
           }
           else 
        	   {
        	    datechk.value=""; 
        	    alert("Date Value is Null");
           		datechk.focus();
           		document.getElementById("butSub").disabled=true;
           		document.getElementById("txtReceipt_No").value="";
        	   }
        }
    }
}



function load_bank_deatils(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	var cr_dr_indicator = baseResponse.getElementsByTagName("cr_dr_indicator")[0].firstChild.nodeValue;
	if (flag == "success") {
		if (cr_dr_indicator == 'CR') {
			document.getElementById("txtCash_Acc_code").value = baseResponse
					.getElementsByTagName("AC_HEAD_CODE")[0].firstChild.nodeValue;
			document.getElementById("txtBankId").value = baseResponse
					.getElementsByTagName("BANK_ID")[0].firstChild.nodeValue;
			document.getElementById("txtBankName").value = baseResponse
					.getElementsByTagName("bk_br_city")[0].firstChild.nodeValue;
			document.getElementById("txtBranchId").value = baseResponse
					.getElementsByTagName("BRANCH_ID")[0].firstChild.nodeValue;
		} else if (cr_dr_indicator == 'DR') {
			var seq = baseResponse.getElementsByTagName("seq")[0].firstChild.nodeValue;
			document.getElementById("txtDebitAccCode" + seq).value = baseResponse
					.getElementsByTagName("AC_HEAD_CODE")[0].firstChild.nodeValue;
			document.getElementById("txtSubBankId" + seq).value = baseResponse
					.getElementsByTagName("BANK_ID")[0].firstChild.nodeValue;
			document.getElementById("txtSubBankName" + seq).value = baseResponse
					.getElementsByTagName("bk_br_city")[0].firstChild.nodeValue;
			document.getElementById("txtSubBranchId" + seq).value = baseResponse
					.getElementsByTagName("BRANCH_ID")[0].firstChild.nodeValue;
		}
	} else if (flag == "failure") {
		alert("No Such Account number!");
		if (cr_dr_indicator == 'CR') {
			document.getElementById("txtCash_Acc_code").value = "";
			document.getElementById("txtBankId").value = "";
			document.getElementById("txtBankName").value = "";
			document.getElementById("txtBranchId").value = "";
		} else if (cr_dr_indicator == 'DR') {
			var seq = baseResponse.getElementsByTagName("seq")[0].firstChild.nodeValue;
			document.getElementById("txtDebitAccCode" + seq).value = "";
			document.getElementById("txtSubBankId" + seq).value = "";
			document.getElementById("txtSubBankName" + seq).value = "";
			document.getElementById("txtSubBranchId" + seq).value = "";
		}
	}
}

function AddRow() {
	//alert("RK");
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
        var txtBankAccountNo = document.getElementById("txtBankAccountNo").value;
     //   alert("txtBankAccountNo::"+txtBankAccountNo);
	var url = "../../../../../InterBankTransfer_MultipleBanks?Command=AddRow&cmbAcc_UnitCode="+ cmbAcc_UnitCode+"&txtBankAccountNo="+txtBankAccountNo;
	 //alert(url);
	var req = AjaxFunction();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		manipulate(req);
	}
	req.send(null);

}
var browserName=navigator.appName;
var brow="";
if (browserName=="Netscape")
{ 	brow="nets";}
else if (browserName=="Microsoft Internet Explorer")
{ 	brow="iex";}
function AddRow1(baseResponse) {
   
        var exist_bank=document.getElementById("txtBankAccountNo").value;
        
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		try{

		/** Get TBody Object */
		var tbody = document.getElementById("tblList");

		/** Create Table Row */
		var mycurrent_row = document.createElement("TR");
		mycurrent_row.id = seq;

		var cell = document.createElement("TD");
		var anc = document.createElement("input");
		anc.type = "checkbox";
		anc.id = "check" + seq;
		anc.name = "check" + seq;
		anc.checked = true;
		anc.disabled = true;
		anc.value = seq;
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);
		/** To Bank Account Number */
		var cell12 = document.createElement("TD");
		cell12.style.textAlign = 'center';
		var txtSubBankAccountNo;

		if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) {
               
			try {
				txtSubBankAccountNo = document.createElement("<select name='txtSubBankAccountNo"
								+ seq + "' id='txtSubBankAccountNo" + seq + "' >");
                                                                
                                                               
			txtSubBankAccountNo.setAttribute('onChange', "doFunction1(" + seq + ");");
                             
				
			} catch (e) {
				alert(e.description);
			}
		} else {
			txtSubBankAccountNo = document.createElement("select");
			txtSubBankAccountNo.id = "txtSubBankAccountNo" + seq;
			txtSubBankAccountNo.name = "txtSubBankAccountNo" + seq;
			txtSubBankAccountNo.setAttribute('onChange', "doFunction1(" + seq
					+ ")");
		}
		var cmbCategoryCodeObj = baseResponse.getElementsByTagName("trans_pair");
		var option11 = document.createElement("option");
		option11.value = "";
		option11.text = "--Select--";
		for ( var y = 0; y < cmbCategoryCodeObj.length; y++) {
			if( CatCode[y]!=exist_bank){
            	var option11 = document.createElement("option");
                option11.value = cmbCategoryCodeObj[y].getElementsByTagName("trans_code")[0].firstChild.nodeValue;
                option11.text = cmbCategoryCodeObj[y].getElementsByTagName("trans_desc")[0].firstChild.nodeValue;
            }
			try {				
				txtSubBankAccountNo.add(option11);
			} catch (e) {				
				txtSubBankAccountNo.add(option11, null);
			}
		}				
		cell12.appendChild(txtSubBankAccountNo);
		mycurrent_row.appendChild(cell12);
		/** Debit A/c Code */
		var cell4 = document.createElement("TD");
		var txtDebitAccCode = document.createElement("input");
		txtDebitAccCode.type = "text";
		txtDebitAccCode.id = "txtDebitAccCode" + seq;
		txtDebitAccCode.name = "txtDebitAccCode" + seq;
		txtDebitAccCode.value = "";
		txtDebitAccCode.size = "8";
		txtDebitAccCode.readOnly = true;
		cell4.appendChild(txtDebitAccCode);
		mycurrent_row.appendChild(cell4);
		/** To Bank Name */
		var cell6 = document.createElement("TD");
		var txtSubBankName = document.createElement("input");
		txtSubBankName.type = "text";
		txtSubBankName.name = "txtSubBankName" + seq;
		txtSubBankName.id = "txtSubBankName" + seq;
		txtSubBankName.value = "";
		txtSubBankName.size = "25";
		txtSubBankName.readOnly = true;
		cell6.appendChild(txtSubBankName);
		mycurrent_row.appendChild(cell6);
		/* Cheque/DD */
        
           
                
		var cell7 = document.createElement("TD");
		cell7.style.textAlign = 'right';
		var sel = "";
		var sel1 = "";
		if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) {
			try{
                       
			sel = document
					.createElement("<INPUT type='radio' checked='checked' name='txtCheque_DD"
							+ seq + "' id='txtCheque_DD" + seq+ "' value='C' />");

			var newLabel = document.createElement("label");
			newLabel.appendChild(document.createTextNode('C'));
			
			sel1 = document
					.createElement("<INPUT type='radio' name='txtCheque_DD"
							+ seq + "' id='txtCheque_DD" + seq
							+ "' value='D' />");
			var newLabel1 = document.createElement("label");
			newLabel1.appendChild(document.createTextNode('D'));
                        
                        sel2 = document
					.createElement("<INPUT type='radio' name='txtCheque_DD"
							+ seq + "' id='txtCheque_DD" + seq
							+ "' value='E' />");
			var newLabel2 = document.createElement("label");
			newLabel2.appendChild(document.createTextNode('E'));
                        
			
		} catch (e) {
			alert(e.description);
		}

		} else { 
			sel = document.createElement("input");
			sel.type = "radio";
			sel.name = "txtCheque_DD" + seq;
			sel.id = "txtCheque_DD" + seq;
			sel.checked = true;
			sel.value = "C";
			var newLabel = document.createElement("label");
			newLabel.appendChild(document.createTextNode('C'));

			sel1 = document.createElement("input");
			sel1.type = "radio";
			sel1.name = "txtCheque_DD" + seq;
			sel1.id = "txtCheque_DD" + seq;
			sel1.checked = false;
			sel1.value = "D";
			var newLabel1 = document.createElement("label");
			newLabel1.appendChild(document.createTextNode('D'));
                        
                        sel2 = document.createElement("input");
			sel2.type = "radio";
			sel2.name = "txtCheque_DD" + seq;
			sel2.id = "txtCheque_DD" + seq;
			sel2.checked = false;
			sel2.value = "E";
			var newLabel2 = document.createElement("label");
			newLabel2.appendChild(document.createTextNode('E'));
                        
                   
		}
		cell7.appendChild(newLabel);
		cell7.appendChild(sel);
		cell7.appendChild(newLabel1);
		cell7.appendChild(sel1);
                cell7.appendChild(newLabel2);
		cell7.appendChild(sel2);
		mycurrent_row.appendChild(cell7);  
		/** Cheque/DD Number */
                
                
                
		var cell8 = document.createElement("TD");
		var txtCheque_DD_NO = document.createElement("input");
		txtCheque_DD_NO.type = "text";
		txtCheque_DD_NO.name = "txtCheque_DD_NO" + seq;
		txtCheque_DD_NO.id = "txtCheque_DD_NO" + seq;
                
                var tbody = document.getElementById("tblList");
                var rowcount = tbody.rows.length;
               // alert("rowcount:::"+rowcount);
                if(rowcount>0)
                {
                 rows=tbody.getElementsByTagName("tr");
                 var cells=rows[rowcount-1].cells;
                 var ac_code1=cells.item(5).firstChild.value;
                 txtCheque_DD_NO.value =ac_code1;
                }
                else{
                txtCheque_DD_NO.value = "";
                }
         	
		txtCheque_DD_NO.size = "10";
		cell8.appendChild(txtCheque_DD_NO);
		mycurrent_row.appendChild(cell8);
		/** Cheque/DD Date */
                
                var date1=document.getElementById("txtCrea_date").value;
               var cell9 = document.createElement("TD");
		var txtCheque_DD_date = document.createElement("input");
		txtCheque_DD_date.type = "text";
		txtCheque_DD_date.name = "txtCheque_DD_date" + seq;
		txtCheque_DD_date.id = "txtCheque_DD_date" + seq;
                
                var tbody = document.getElementById("tblList");
                var rowcount = tbody.rows.length;
                if(rowcount>0)
                {
                 rows=tbody.getElementsByTagName("tr");
                 var cells=rows[rowcount-1].cells;
                 var ac_code1=cells.item(6).firstChild.value;
                 txtCheque_DD_date.value =ac_code1;
                }
                else{
                txtCheque_DD_date.value = date1;
                }
                
		//txtCheque_DD_date.value = date1;
		txtCheque_DD_date.size = "10";
		cell9.appendChild(txtCheque_DD_date);
		mycurrent_row.appendChild(cell9);
		/** Amount (Rs. P.) */
		var cell10 = document.createElement("TD");
		var txtAmount = document.createElement("input");
		txtAmount.type = "text";
		txtAmount.name = "txtAmount" + seq;
		txtAmount.id = "txtAmount" + seq;
		txtAmount.value = "";
		txtAmount.size = "10";
		cell10.appendChild(txtAmount);
		mycurrent_row.appendChild(cell10);
		/** Remarks */
		var cell2 = document.createElement("TD");
		var Remarks = document.createElement('TEXTAREA', 'option1');
		Remarks.name = "Remarks" + seq;
		Remarks.id = "Remarks" + seq;
		Remarks.value = "";
		Remarks.setAttribute("cols", "5");
		Remarks.style.height = "60px";
		Remarks.style.width = "200px";
		cell2.appendChild(Remarks);
		mycurrent_row.appendChild(cell2);
		/** txtSubBankId */
		var txtSubBankId = document.createElement("input");
		txtSubBankId.setAttribute("type", "hidden");
		txtSubBankId.setAttribute("value", "");
		txtSubBankId.setAttribute("name", "txtSubBankId" + seq);
		txtSubBankId.setAttribute("id", "txtSubBankId" + seq);
		document.getElementById("frm_InterBankTransfer_MultipleBanks")
				.appendChild(txtSubBankId);
		/** txtSubBranchId */
		var txtSubBranchId = document.createElement("input");
		txtSubBranchId.setAttribute("type", "hidden");
		txtSubBranchId.setAttribute("value", "");
		txtSubBranchId.setAttribute("name", "txtSubBranchId" + seq);
		txtSubBranchId.setAttribute("id", "txtSubBranchId" + seq);
		document.getElementById("frm_InterBankTransfer_MultipleBanks")
				.appendChild(txtSubBranchId);
		if(brow=="iex"){
			var vartab = tbody.insertRow(-1);			
			vartab.appendChild(cell);
			//vartab.appendChild(txtSubBankAccountNo);
			vartab.appendChild(cell12);
			vartab.appendChild(cell4);
			vartab.appendChild(cell6);			
			vartab.appendChild(cell7);
			vartab.appendChild(cell8);
			vartab.appendChild(cell9);
			vartab.appendChild(cell10);
			vartab.appendChild(cell2);
                      //  vartab.appendChild(cell88);
		}else{			
			tbody.appendChild(mycurrent_row);
		}
		/** Increment Sequence Number */
		seq = seq + 1;
		document.getElementById("RecordCount").value = seq;
	}catch(e){
		alert("exc "+e.description);
	}
	}
}

function ClearRow() {
	var tbody = document.getElementById("tblList");
	var rowcount = tbody.rows.length;
	tbody.deleteRow(rowcount - 1);
}

function clrForm() {

	document.getElementById("butUpdate").disabled = true;
	document.getElementById("butCancel").disabled = true;
	document.getElementById("butSub").disabled = false;
	document.getElementById("txtVoucher_No").style.visibility = "hidden"
	var tbody = document.getElementById("tblList");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}

	document.getElementById("txtCrea_date").value = "";
	document.getElementById("txtBankAccountNo").value = "";
	document.getElementById("txtCash_Acc_code").value = "";
	document.getElementById("txtBankName").value = "";
	document.getElementById("txtTotalAmount").value = "";
	document.getElementById("txtReferenceNo").value = "";
	document.getElementById("txtReferenceDate").value = "";
	document.getElementById("txtRemarks").value = "";

}


function load_Voucher_No() {
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCrea_date = document.getElementById("txtCrea_date").value;
	var cmbSubSystemType = "IBTM";
	if (txtCrea_date == "") {
		alert("Enter the date");
		document.getElementById("txtCrea_date").focus();
	} else {
		var url = "../../../../../Authorization_JAO_Create.view?Command=load_Voucher_No&txtCrea_date="
				+ txtCrea_date
				+ "&cmbSubSystemType="
				+ cmbSubSystemType
				+ "&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode
				+ "&cmbOffice_code="
				+ cmbOffice_code;
		// alert(url);
		cmbSubSystemType + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode
				+ "&cmbOffice_code=" + cmbOffice_code;
		var req = AjaxFunction();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			manipulate(req);
		}
		req.send(null);
	}
}

function load_Voucher_details() {
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCrea_date = document.getElementById("txtCrea_date").value;
	var txtVoucher_No = document.getElementById("txtVoucher_No").value;
	if (txtCrea_date == "") {
		alert("Enter the date");
		document.getElementById("txtCrea_date").focus();
	} else {

		var url = "../../../../../InterBankTransfer_MultipleBanks?Command=load_Voucher_details&txtCrea_date="
				+ txtCrea_date
				+ "&txtVoucher_No="
				+ txtVoucher_No
				+ "&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode
				+ "&cmbOffice_code="
				+ cmbOffice_code;
		// alert(url);
		var req = AjaxFunction();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			manipulate(req);
		}
		req.send(null);
	}

}


function load_Voucher_details1(baseResponse) {

	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;

	if (flag1 == "success") {

		var FROM_ACCOUNT_NO = baseResponse
				.getElementsByTagName("FROM_ACCOUNT_NO")[0].firstChild.nodeValue;
		var CR_ACCOUNT_HEAD_CODE = baseResponse
				.getElementsByTagName("CR_ACCOUNT_HEAD_CODE")[0].firstChild.nodeValue;
		var bk_br_city = baseResponse.getElementsByTagName("bk_br_city")[0].firstChild.nodeValue;
		var TOTAL_AMOUNT = baseResponse.getElementsByTagName("TOTAL_AMOUNT")[0].firstChild.nodeValue;
		var REF_NO = baseResponse.getElementsByTagName("REF_NO")[0].firstChild.nodeValue;
		var REF_DATE = baseResponse.getElementsByTagName("REF_DATE")[0].firstChild.nodeValue;
		var PARTICULARS = baseResponse.getElementsByTagName("PARTICULARS")[0].firstChild.nodeValue;
		var FROM_BANK_ID = baseResponse.getElementsByTagName("FROM_BANK_ID")[0].firstChild.nodeValue;
		var FROM_BRANCH_ID = baseResponse
				.getElementsByTagName("FROM_BRANCH_ID")[0].firstChild.nodeValue;

		document.getElementById("txtBankAccountNo").value = FROM_ACCOUNT_NO;
		document.getElementById("txtCash_Acc_code").value = CR_ACCOUNT_HEAD_CODE;
		document.getElementById("txtBankName").value = bk_br_city;
		document.getElementById("txtTotalAmount").value = TOTAL_AMOUNT;
		document.getElementById("txtReferenceNo").value = REF_NO;
		document.getElementById("txtReferenceDate").value = REF_DATE;
		document.getElementById("txtRemarks").value = PARTICULARS;
		document.getElementById("txtBankId").value = FROM_BANK_ID;
		document.getElementById("txtBranchId").value = FROM_BRANCH_ID;

		/** Delete Existing Values from Grid */
		var tbody = document.getElementById("tblList");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		var r_no = baseResponse.getElementsByTagName("SL_NO");
		var len = baseResponse.getElementsByTagName("SL_NO").length;

		seq = 0;
		if (len != 0) {
			var item = new Array();
			for ( var k = 0; k < r_no.length; k++) {
				item[0] = baseResponse.getElementsByTagName("TO_ACCOUNT_NO")[k].firstChild.nodeValue;

				item[1] = baseResponse
						.getElementsByTagName("DR_ACCOUNT_HEAD_CODE")[k].firstChild.nodeValue;
				if (item[1] == 'null') {
					item[1] = "";
				}
				item[2] = baseResponse.getElementsByTagName("bk_br_city1")[k].firstChild.nodeValue;
				if (item[2] == 'null') {
					item[2] = "";
				}
				item[3] = baseResponse.getElementsByTagName("CHEQUE_OR_DD")[k].firstChild.nodeValue;
				if (item[3] == 'null') {
					item[3] = "";
				}
				item[4] = baseResponse.getElementsByTagName("CHEQUE_DD_NO")[k].firstChild.nodeValue;
				if (item[4] == 'null') {
					item[4] = "";
				}
				item[5] = baseResponse.getElementsByTagName("CHEQUE_DD_DATE")[k].firstChild.nodeValue;
				if (item[5] == 'null') {
					item[5] = "";
				}
				item[6] = baseResponse.getElementsByTagName("TOTAL_AMOUNT1")[k].firstChild.nodeValue;
				if (item[6] == 'null') {
					item[6] = "";
				}
				item[7] = baseResponse.getElementsByTagName("REMARKS")[k].firstChild.nodeValue;
				if (item[7] == 'null') {
					item[7] = "";
				}
				item[8] = baseResponse.getElementsByTagName("TO_BANK_ID")[k].firstChild.nodeValue;
				if (item[8] == 'null') {
					item[8] = "0";
				}
				item[9] = baseResponse.getElementsByTagName("TO_BRANCH_ID")[k].firstChild.nodeValue;
				if (item[9] == 'null') {
					item[9] = "0";
				}
				item[10] = baseResponse.getElementsByTagName("SL_NO")[k].firstChild.nodeValue;
				if (item[10] == 'null') {
					item[10] = "0";
				}

				/** Create Table Row */
				var mycurrent_row = document.createElement("TR");
				mycurrent_row.id = seq;
				var cell = document.createElement("TD");
				var anc = document.createElement("input");
				anc.type = "checkbox";
				anc.id = "check" + seq;
				anc.name = "check" + seq;
				anc.checked = true;
				anc.value = seq;
				cell.appendChild(anc);
				mycurrent_row.appendChild(cell);

				/** To Bank Account Number */
				cell12 = document.createElement("TD");
				cell12.style.textAlign = 'center';
				var txtSubBankAccountNo;

				if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) {
					try {
						txtSubBankAccountNo = document
								.createElement("<select name='txtSubBankAccountNo"
										+ seq + "' id='txtSubBankAccountNo" + seq
										+ "' value='"+item[0]+"'>");
						txtSubBankAccountNo.setAttribute('onChange', "doFunction1(" + seq
								+ ")");
						
					} catch (e) {
						alert(e.description)
					}
				}else {
					txtSubBankAccountNo = document.createElement("select");
					txtSubBankAccountNo.id = "txtSubBankAccountNo" + seq;
					txtSubBankAccountNo.name = "txtSubBankAccountNo" + seq;
					txtSubBankAccountNo.setAttribute('onChange', "doFunction1("
							+ seq + ")");
				}

				var cmbCategoryCodeObj = baseResponse
						.getElementsByTagName("trans_pair");
				var option11 = document.createElement("option");
				option11.value = "";
				option11.text = "--Select--";

				try {
					txtSubBankAccountNo.add(option11);
				} catch (e) {
					txtSubBankAccountNo.add(option11, null);
				}
				for ( var y = 0; y < cmbCategoryCodeObj.length; y++) {
					CatCode[y] = cmbCategoryCodeObj[y]
							.getElementsByTagName("trans_code")[0].firstChild.nodeValue;

					CatDesc[y] = cmbCategoryCodeObj[y]
							.getElementsByTagName("trans_desc")[0].firstChild.nodeValue;

					var option11 = document.createElement("option");
					if (CatCode[y] == item[0]) {
						option11.selected = true;
					}
					option11.value = CatCode[y];
					option11.text = CatDesc[y];

					try {
						txtSubBankAccountNo.add(option11);
					} catch (e) {
						txtSubBankAccountNo.add(option11, null);
					}
				}

				cell12.appendChild(txtSubBankAccountNo);
				mycurrent_row.appendChild(cell12);

				/** Debit A/c Code */
				var cell4 = document.createElement("TD");
				var txtDebitAccCode = document.createElement("input");
				txtDebitAccCode.type = "text";
				txtDebitAccCode.id = "txtDebitAccCode" + seq;
				txtDebitAccCode.name = "txtDebitAccCode" + seq;
				txtDebitAccCode.value = item[1];
				txtDebitAccCode.size = "10";
				txtDebitAccCode.readOnly = true;
				cell4.appendChild(txtDebitAccCode);
				mycurrent_row.appendChild(cell4);

				/** To Bank Name */
				var cell6 = document.createElement("TD");
				var txtSubBankName = document.createElement("input");
				txtSubBankName.type = "text";
				txtSubBankName.name = "txtSubBankName" + seq;
				txtSubBankName.id = "txtSubBankName" + seq;
				txtSubBankName.value = item[2];
				txtSubBankName.size = "50";
				txtSubBankName.readOnly = true;
				cell6.appendChild(txtSubBankName);
				mycurrent_row.appendChild(cell6);

				/* Cheque/DD */
                                
                               
                                                
		         		var cell7 = document.createElement("TD");
				cell7.style.textAlign = 'right';
				var sel = "";
				var sel1 = "";
				if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) {
					try{
					sel = document
							.createElement("<INPUT type='radio' name='txtCheque_DD"
									+ seq + "' id='txtCheque_DD" + seq
									+ "' value='C' />");

					if (item[3] == "C") {
						sel.checked = true;
					} else {
						sel.checked = false;
					}
					var newLabel = document.createElement("label");
					newLabel.appendChild(document.createTextNode('C'));
					
					sel1 = document
							.createElement("<INPUT type='radio' name='txtCheque_DD"
									+ seq + "' id='txtCheque_DD" + seq
									+ "' value='D' />");
					if (item[3] == "DD") {
						sel1.checked = true;
					} else {
						sel1.checked = false;
					}
					var newLabel1 = document.createElement("label");
					newLabel1.appendChild(document.createTextNode('D'));
                                        
                                        sel2 = document
							.createElement("<INPUT type='radio' name='txtCheque_DD"
									+ seq + "' id='txtCheque_DD" + seq
									+ "' value='E' />");
					if (item[3] == "E") {
						sel1.checked = true;
					} else {
						sel1.checked = false;
					}
					var newLabel2 = document.createElement("label");
					newLabel2.appendChild(document.createTextNode('E'));
					
				} catch (e) {
					alert(e.description)
				}

				} else {
                              //  alert(item[3]);

					sel = document.createElement("input");
					sel.type = "radio";
					sel.name = "txtCheque_DD" + seq;
					sel.id = "txtCheque_DD" + seq;
					if (item[3] == "C") {
						sel.checked = true;
					} else {
						sel.checked = false;
					}
					sel.value = "C";
					var newLabel = document.createElement("label");
					newLabel.appendChild(document.createTextNode('C'));

					sel1 = document.createElement("input");
					sel1.type = "radio";
					sel1.name = "txtCheque_DD" + seq;
					sel1.id = "txtCheque_DD" + seq;
					if (item[3] == "DD") {
						sel1.checked = true;
					} else {
						sel1.checked = false;
					}
					sel1.value = "D";
					var newLabel1 = document.createElement("label");
					newLabel1.appendChild(document.createTextNode('D'));
                                        
                                        sel2 = document.createElement("input");
					sel2.type = "radio";
					sel2.name = "txtCheque_DD" + seq;
					sel2.id = "txtCheque_DD" + seq;
					if (item[3] == "E") {
						sel2.checked = true;
					} else {
						sel2.checked = false;
					}
					sel2.value = "E";
					var newLabel2 = document.createElement("label");
					newLabel2.appendChild(document.createTextNode('E'));

				}
				cell7.appendChild(newLabel);
				cell7.appendChild(sel);
				cell7.appendChild(newLabel1);
				cell7.appendChild(sel1);
                                cell7.appendChild(newLabel2);
				cell7.appendChild(sel2);
				mycurrent_row.appendChild(cell7);  

				/** Cheque/DD Number */
				var cell8 = document.createElement("TD");
				var txtCheque_DD_NO = document.createElement("input");
				txtCheque_DD_NO.type = "text";
				txtCheque_DD_NO.name = "txtCheque_DD_NO" + seq;
				txtCheque_DD_NO.id = "txtCheque_DD_NO" + seq;
				txtCheque_DD_NO.value = item[4];
				txtCheque_DD_NO.size = "10";
				cell8.appendChild(txtCheque_DD_NO);
				mycurrent_row.appendChild(cell8);

				/** Cheque/DD Date */
				var cell9 = document.createElement("TD");
				var txtCheque_DD_date = document.createElement("input");
				txtCheque_DD_date.type = "text";
				txtCheque_DD_date.name = "txtCheque_DD_date" + seq;
				txtCheque_DD_date.id = "txtCheque_DD_date" + seq;
				txtCheque_DD_date.value = item[5];
				txtCheque_DD_date.size = "10";
				cell9.appendChild(txtCheque_DD_date);
				mycurrent_row.appendChild(cell9);

				/** Amount (Rs. P.) */
				var cell10 = document.createElement("TD");
				var txtAmount = document.createElement("input");
				txtAmount.type = "text";
				txtAmount.name = "txtAmount" + seq;
				txtAmount.id = "txtAmount" + seq;
				txtAmount.value = item[6];
				txtAmount.size = "10";
				cell10.appendChild(txtAmount);
				mycurrent_row.appendChild(cell10);

				/** Remarks */
				var cell2 = document.createElement("TD");
				var Remarks = document.createElement('TEXTAREA', 'option1');
				Remarks.name = "Remarks" + seq;
				Remarks.id = "Remarks" + seq;
				Remarks.value = item[7];
				Remarks.setAttribute("cols", "5");
				Remarks.style.height = "60px"
				Remarks.style.width = "200px";
				cell2.appendChild(Remarks);
				mycurrent_row.appendChild(cell2);

				/** txtSubBankId */
				var txtSubBankId = document.createElement("input");
				txtSubBankId.setAttribute("type", "hidden");
				txtSubBankId.setAttribute("value", item[8]);
				txtSubBankId.setAttribute("name", "txtSubBankId" + seq);
				txtSubBankId.setAttribute("id", "txtSubBankId" + seq);
				document.getElementById("frm_InterBankTransfer_MultipleBanks")
						.appendChild(txtSubBankId);

				/** txtSubBranchId */
				var txtSubBranchId = document.createElement("input");
				txtSubBranchId.setAttribute("type", "hidden");
				txtSubBranchId.setAttribute("value", item[9]);
				txtSubBranchId.setAttribute("name", "txtSubBranchId" + seq);
				txtSubBranchId.setAttribute("id", "txtSubBranchId" + seq);
				document.getElementById("frm_InterBankTransfer_MultipleBanks")
						.appendChild(txtSubBranchId);

				/** Sl No */
				var slno_db = document.createElement("input");
				slno_db.setAttribute("type", "hidden");
				slno_db.setAttribute("value", item[10]);
				slno_db.setAttribute("name", "slno_db" + seq);
				slno_db.setAttribute("id", "slno_db" + seq);
				document.getElementById("frm_InterBankTransfer_MultipleBanks")
						.appendChild(slno_db);
                                                
                               
                

				tbody.appendChild(mycurrent_row);

				/** Increment Sequence Number */
				seq = seq + 1;
				document.getElementById("RecordCount").value = seq;
			}
		}

	} else {
		alert("Failed to Load Data");
	}
}

function load_Voucher_No1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	var txtVoucher_No = document.getElementById("txtVoucher_No");
	if (flag == "success") {
		document.getElementById("butUpdate").disabled = false;
		document.getElementById("butCancel").disabled = false;
		document.getElementById("butSub").disabled = true;
		document.getElementById("txtVoucher_No").style.visibility = "visible";
		var items_id = new Array();
		var Rec_No = baseResponse.getElementsByTagName("Rec_No");

		for ( var k = 0; k < Rec_No.length; k++) {
			items_id[k] = baseResponse.getElementsByTagName("Rec_No")[k].firstChild.nodeValue;

		}

		txtVoucher_No.innerHTML = "";
		var option = document.createElement("OPTION");
		option.text = "--Select Voucher Number--";
		option.value = "";
		try {
			txtVoucher_No.add(option);
		} catch (errorObject) {
			txtVoucher_No.add(option, null);
		}

		for ( var k = 0; k < items_id.length; k++) {
			var option = document.createElement("OPTION");
			option.text = items_id[k];
			option.value = items_id[k];
			try {
				txtVoucher_No.add(option);
			} catch (errorObject) {
				txtVoucher_No.add(option, null);
			}
		}
	} else if (flag == "failure") {
		txtVoucher_No.innerHTML = "";
		var option = document.createElement("OPTION");
		option.text = "--Select Voucher Number--";
		option.value = "";
		try {
			txtVoucher_No.add(option);
		} catch (errorObject) {
			txtVoucher_No.add(option, null);
		}
		alert("No Voucher Found");
	}
}

function funcSave() {
	document.getElementById("filter").value = "save";
	var Amt = 0,debitCode=0;
        var count_db=0;
	var acc_No;
	var txtCrea_date = document.getElementById("txtCrea_date").value;
	var txtBankAccountNo = document.getElementById("txtBankAccountNo").value;
	var txtTotalAmount = document.getElementById("txtTotalAmount").value;
	var txtReferenceNo = document.getElementById("txtReferenceNo").value;
	var txtReferenceDate = document.getElementById("txtReferenceDate").value;

	if (txtCrea_date == "") {
		alert("Enter Date in the field");
		document.getElementById("txtCrea_date").focus();
		return false;
	} else if (txtBankAccountNo == "") {
		alert("Enter Bank Account No in the field");
		document.getElementById("txtBankAccountNo").focus();
		return false;
	} else if (txtTotalAmount == "") {
		alert("Enter Total Amount in the field");
		document.getElementById("txtTotalAmount").focus();
		return false;
	}
//        else if (txtReferenceNo == "") {
//		alert("Enter Reference No in the field");
//		document.getElementById("txtReferenceNo").focus();
//		return false;
//	} else if (txtReferenceDate == "") {
//		alert("Enter Reference Date in the field");
//		document.getElementById("txtReferenceDate").focus();
//		return false;
//	} 
        else {

		var tbody = document.getElementById("tblList");
		var rowcount = tbody.rows.length;
		if (rowcount != 0) {
			for ( var i = 0; i < rowcount; i++) {
                        //dhana
                                debitCode=document.getElementById("txtDebitAccCode" + i).value;
                              //  alert("debitCode::::"+debitCode+"ttt:::");
                                if(debitCode=="")
                                {
                              //  alert("inside");
                                count_db++;
                                }
				Amt = Amt+ parseFloat(document.getElementById("txtAmount" + i).value);
				acc_No = document.getElementById("txtSubBankAccountNo" + i).value;
			}
			if (acc_No == txtBankAccountNo) {
				alert("From Bank Account No and To Bank Account No Should Not Same");
				return false;
			} else if (Amt != txtTotalAmount) {
				alert("Amount Does Not Tally");
				return false;
			}
                       else if(count_db>0)
                        {
                        alert("Enter Debit A/c Code");
                        return false;
                        }
                        else {
				return true;
			}
		} else {
			alert("Add Records in Details Part");
			return false;
		}
	}
        
}

function funcCancel() {
	document.getElementById("filter").value = "cancel";
}

function funcUpdate() {
	var flag = 0;
	document.getElementById("filter").value = "update";
	var Amt = 0;
	var acc_No;
	var txtCrea_date = document.getElementById("txtCrea_date").value;
	var txtBankAccountNo = document.getElementById("txtBankAccountNo").value;
	var txtTotalAmount = document.getElementById("txtTotalAmount").value;
	var txtReferenceNo = document.getElementById("txtReferenceNo").value;
	var txtReferenceDate = document.getElementById("txtReferenceDate").value;

	if (txtCrea_date == "") {
		alert("Enter Date in the field");
		document.getElementById("txtCrea_date").focus();
		return false;
	} else if (txtBankAccountNo == "") {
		alert("Enter Bank Account No in the field");
		document.getElementById("txtBankAccountNo").focus();
		return false;
	} else if (txtTotalAmount == "") {
		alert("Enter Total Amount in the field");
		document.getElementById("txtTotalAmount").focus();
		return false;
	} else if (txtReferenceNo == "") {
		alert("Enter Reference No in the field****");
		document.getElementById("txtReferenceNo").focus();
		return false;
	} else if (txtReferenceDate == "") {
		alert("Enter Reference Date in the field");
		document.getElementById("txtReferenceDate").focus();
		return false;
	} else {

		var tbody = document.getElementById("tblList");
		var rowcount = tbody.rows.length;
		if (rowcount != 0) {
			for ( var i = 0; i < rowcount; i++) {
				Amt = Amt
						+ parseFloat(document.getElementById("txtAmount" + i).value);
				acc_No = document.getElementById("txtSubBankAccountNo" + i).value;
				if (document.getElementById("check" + i).checked == true) {
					var slno_db1 = document.createElement("input");
					slno_db1.setAttribute("type", "hidden");
					slno_db1.setAttribute("value", i);
					slno_db1.setAttribute("name", "slno_db1" + i);
					slno_db1.setAttribute("id", "slno_db1" + i);
					document.getElementById(
							"frm_InterBankTransfer_MultipleBanks").appendChild(
							slno_db1);
				} else {
					var slno_db1 = document.createElement("input");
					slno_db1.setAttribute("type", "hidden");
					slno_db1.setAttribute("value", "-1");
					slno_db1.setAttribute("name", "slno_db1" + i);
					slno_db1.setAttribute("id", "slno_db1" + i);
					document.getElementById(
							"frm_InterBankTransfer_MultipleBanks").appendChild(
							slno_db1);
					flag = flag + 1
				}

			}
			if (flag == rowcount) {
				alert("To Remove All the Transacion Click 'CANCEL' Button");
				return false;
			} else if (acc_No == txtBankAccountNo) {
				alert("From Bank Account No and To Bank Account No Should Not Same");
				return false;
			} else if (Amt != txtTotalAmount) {
				alert("Amount Does Not Tally");
				return false;
			} else {
				return true;
			}
		} else {
			alert("Add Records in Details Part");
			return false;
		}
	}
}
