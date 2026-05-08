var seq=0;
var slNo=1;
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


function checksan()
{

	var billdate_grid=document.getElementById("txtBillDate").value;
	var biisp_grid=billdate_grid.split("/");
	
	var txtScrutinyDate=document.getElementById("txtProceedingDate").value;
	 var passsplit=txtScrutinyDate.split("/");
	
	 if(biisp_grid[2]<passsplit[2])
	 {
		 alert("Bill Date Should not be less than Sanction Datesss");
		 document.getElementById("txtBillDate").value="";
		 return false;
		
	 }
	 else if(biisp_grid[2]==passsplit[2])
	 {
		
   	 if(biisp_grid[1]<passsplit[1])
   	 {
   		 alert("Bill Date Should not be less than Sanction Date");
		 document.getElementById("txtBillDate").value="";
		 return false;
   	 }
   	 else if(biisp_grid[1]==passsplit[1])
   	 {
   		 var billspl;
   		// alert(biisp_grid[0].length);
   		 if(biisp_grid[0].length==2)
   		 {
   			billspl=biisp_grid[0];
   		 }
   		 else
   		 {
   			billspl="0"+biisp_grid[0];
   		 }
   		 if(billspl<passsplit[0])
       	 {
   			 alert("Bill Date Should not be less then SanctionDate");
   			 document.getElementById("txtBillDate").value="";
   			 return false;
       	 } 
   	 }
	 }
	
}

function changecr()
{
	document.getElementById("txtCRDRIndicator").value="Credit";
}
function changedr()
{
	document.getElementById("txtCRDRIndicator").value="Debit";
}
function initialLoad(path) {
	// alert(path);

	var url = path + "/Memo_Of_Pay_Edit?command=gett";

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
			// alert("manipulate-command:--->>>"+command);

			if (command == "gett") {
				firstLoad(baseResponse);
			} else if (command == "saveFunc") {
				saveFunc(baseResponse);
			} else if (command == "BillNo") {
				forBillNo1(baseResponse);
			} else if (command == "getDetails") {
				getDetails1(baseResponse);
			}else if (command == "deleted") {
				deleteRow(baseResponse);
			}else if (command == "update") {
				updateRow(baseResponse);
			}else if (command == "addMst") {
				addMst1(baseResponse);
			}
		}
	}
}

function firstLoad(baseResponse) {
	var len1 = baseResponse.getElementsByTagName("subLedgerTypeCode").length;
	for ( var i = 0; i < len1; i++) {
		var subLedgerTypeCode = baseResponse
				.getElementsByTagName("subLedgerTypeCode")[i].firstChild.nodeValue;
		var subLedgerTypeDesc = baseResponse
				.getElementsByTagName("subLedgerTypeDesc")[i].firstChild.nodeValue;
		// alert(empName);
		var se = document.getElementById("cmbMas_SL_type");
		var op = document.createElement("OPTION");
		op.value = subLedgerTypeCode;
		var txt = document.createTextNode(subLedgerTypeDesc);
		op.appendChild(txt);
		se.appendChild(op);

	}
}


function forBillNo(path) {
	// alert(path);
	var sanctype;
	var sancidwith;
	
	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cboOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("cboCashBook_Year").value;
	var cboCashBook_Month = document.getElementById("cboCashBook_Month").value;
		if(document.frm_Memo_Of_Payment.sancidwith[0].checked==true)
		{
			sancidwith ="with_sanc"; 
		}
		else if(document.frm_Memo_Of_Payment.sancidwith[1].checked==true)
		{
			sancidwith ="with_out_sanc"; 
		}
		else if(document.frm_Memo_Of_Payment.sancidwith[2].checked==true)
		{
			sancidwith ="with_out_sanc_GPF"; 
		}

	var url = path + "/Memo_Of_Pay_Edit?command=BillNo&cboOffice_code="
			+ cboOffice_code + "&cboAcc_UnitCode=" + cboAcc_UnitCode
			+ "&cboCashBook_Year=" + cboCashBook_Year + "&cboCashBook_Month="
			+ cboCashBook_Month+"&sancidwith="+sancidwith;

	// alert(url);

	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}

function forBillNo1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == " NoData ") {
		refresh();
		alert("Record Does Not Exist");
	} else if (flag == " success ") {
		refresh1();
		var len55 = baseResponse.getElementsByTagName("billNo").length;
		for ( var i = 0; i < len55; i++) {
			var billNo = baseResponse.getElementsByTagName("billNo")[i].firstChild.nodeValue;
			var se = document.getElementById("cboBillNo");
			var op = document.createElement("OPTION");
			op.value = billNo;
			var txt = document.createTextNode(billNo);
			op.appendChild(txt);
			se.appendChild(op);
		}
	} else {
		alert("Failed To Load Values");
	}
}

function getDetails(path) {
	var sancidwith;
	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cboOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("cboCashBook_Year").value;
	var cboCashBook_Month = document.getElementById("cboCashBook_Month").value;
	var cboBillNo = document.getElementById("cboBillNo").value;
	if(document.frm_Memo_Of_Payment.sancidwith[0].checked==true)
	{
		sancidwith ="with_sanc"; 
	}
	else if(document.frm_Memo_Of_Payment.sancidwith[1].checked==true)
	{
		sancidwith ="with_out_sanc"; 
	}
	else if(document.frm_Memo_Of_Payment.sancidwith[2].checked==true)
	{
		sancidwith ="with_out_sanc_GPF"; 
	}
	var url = path + "/Memo_Of_Pay_Edit?command=getDetails&cboOffice_code="
			+ cboOffice_code + "&cboAcc_UnitCode=" + cboAcc_UnitCode
			+ "&cboCashBook_Year=" + cboCashBook_Year + "&cboCashBook_Month="
			+ cboCashBook_Month + "&cboBillNo=" + cboBillNo+"&sancidwith="+sancidwith;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}
function blockSanNo(){
	if(document.frm_Memo_Of_Payment.sancidwith[0].checked==true)
	{
		document.getElementById("head_div1").style.display="block";	
	}else
	{
		document.getElementById("head_div1").style.display="none";
		
	}
}
function getDetails1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else if (flag == "success") {
		
		 var tbody=document.getElementById("tblList");
		    var t=0;
		    for(t=tbody.rows.length-1;t>=0;t--)
		    {
		       tbody.deleteRow(0);
		    }
		
		var txtInvoiceNos=0,txtAgreementNo=0;
		var InvoiceDates=0,AgreementDate=0;
		var txtInvoiceAmounts=0,txtAgreementAmount=0;
		var rdoIfFirstParty="Y";
		
		var billMajorType = baseResponse.getElementsByTagName("major_type")[0].firstChild.nodeValue;
		var major_type_desc = baseResponse.getElementsByTagName("major_type_desc")[0].firstChild.nodeValue;
		var billDate = baseResponse.getElementsByTagName("billDate")[0].firstChild.nodeValue;
		
		var proceedingNo = baseResponse.getElementsByTagName("proceedingNo")[0].firstChild.nodeValue;
		
		//var proceedingid = baseResponse.getElementsByTagName("proceedingid")[0].firstChild.nodeValue;
		var proceedingDate = baseResponse.getElementsByTagName("proceedingDate")[0].firstChild.nodeValue;
		
		
		var debit_amt = baseResponse.getElementsByTagName("debit_amt")[0].firstChild.nodeValue;
		var indicator = baseResponse.getElementsByTagName("indicator")[0].firstChild.nodeValue;
		
		var Particulars = baseResponse.getElementsByTagName("Particulars")[0].firstChild.nodeValue;
		
		var sanctionedAmount = baseResponse.getElementsByTagName("sancamt")[0].firstChild.nodeValue;
		
		var debitAccHead = baseResponse.getElementsByTagName("debitAccHead")[0].firstChild.nodeValue;
		
		document.frm_Memo_Of_Payment.txtBillMajorType.value = billMajorType;
		document.frm_Memo_Of_Payment.majorDesc.value = major_type_desc;
	//	alert("billMajorType"+billMajorType);
		if(major_type_desc=="Miscellaneous")
		{//txtBillMajorType
			//alert("dd");
			var d1 = document.getElementById("gridbtnid");
			d1.style.display = "none";
		}
		else
		{
			var d1 = document.getElementById("gridbtnid");
			d1.style.display = "block";
		}
		
		document.frm_Memo_Of_Payment.txtBillDate.value = billDate;
		document.frm_Memo_Of_Payment.txtSanctionProceedingNo.value = proceedingNo;
		document.frm_Memo_Of_Payment.txtSanctionProceedingDate.value = proceedingDate;
		document.frm_Memo_Of_Payment.txtDebitAccountHead.value = debitAccHead;
		
	//	document.frm_Memo_Of_Payment.txtSanctionProceedingid.value = proceedingid;
		document.frm_Memo_Of_Payment.txtSanctionedAmount.value = sanctionedAmount;
	
		var cmbMas_SL_type = document.getElementById("cmbMas_SL_type");
		cmbMas_SL_type.length=0;
		var cmbMas_SL_Code=document.getElementById("cmbMas_SL_Code");
		cmbMas_SL_Code.length=0;
         var Payee_Type_Code_le = baseResponse.getElementsByTagName("Payee_Type_Code");
        
       /*  for(var i=0; i<Payee_Type_Code_le.length; i++)
             {
             
        	 var se = document.getElementById("cmbMas_SL_type");
     		var op = document.createElement("OPTION");
     		op.value = Payee_Type_Code;
     		var txt = document.createTextNode(payee_desc);
     		op.appendChild(txt);
     		se.appendChild(op);
     		
     		 var se_code = document.getElementById("cmbMas_SL_Code");
      		var opt = document.createElement("OPTION");
      		opt.value = Payee_Code;
      		var txtx = document.createTextNode(code_desc);
      		opt.appendChild(txtx);
      		se_code.appendChild(opt);
     		
             } */
         
         document.frm_Memo_Of_Payment.txtDebitAmount.value = debit_amt;
		
		var headcode = baseResponse.getElementsByTagName("Account_Head_Code");
		  var tbody=document.getElementById("tblList");
		  for(var i=0;i<headcode.length;i++)
          {
			  var Account_Head_Code = baseResponse.getElementsByTagName("Account_Head_Code")[i].firstChild.nodeValue;
			  var head_desc = baseResponse.getElementsByTagName("head_desc")[i].firstChild.nodeValue;
			  var Payee_Type_Code = baseResponse.getElementsByTagName("Payee_Type_Code")[i].firstChild.nodeValue;
			  var amount = baseResponse.getElementsByTagName("amount")[i].firstChild.nodeValue;
			  var Payee_Type_Code = baseResponse.getElementsByTagName("Payee_Type_Code")[i].firstChild.nodeValue;
				var payee_desc = baseResponse.getElementsByTagName("payee_desc")[i].firstChild.nodeValue;
				
				var Payee_Code = baseResponse.getElementsByTagName("Payee_Code")[i].firstChild.nodeValue;
				var code_desc = baseResponse.getElementsByTagName("code_desc")[i].firstChild.nodeValue;
			  var mycurrent_row = document.createElement("TR");
				mycurrent_row.id = seq;
				var cell = document.createElement("TD");
				var anc = document.createElement("A");
				var url = "javascript:loadValuesFromTable('" + seq + "')";

				anc.href = url;
				//alert(billMajorType);
				if(billMajorType==2)
				{
					var txtedit = document.createTextNode("-");
					//anc.appendChild(txtedit);
					cell.appendChild(txtedit);
					mycurrent_row.appendChild(cell);
				} 
//				if(billMajorType==2)
//				{
//					var txtedit = document.createTextNode("Edit");
//					anc.appendChild(txtedit);
//					cell.appendChild(anc);
//					mycurrent_row.appendChild(cell);
//				}
				else{
				var txtedit = document.createTextNode("Edit");
				anc.appendChild(txtedit);
				cell.appendChild(anc);
				mycurrent_row.appendChild(cell);
				}
				

				 var cell2=document.createElement("TD");       
                 var txtAccountHeadCode=document.createElement("input");
                 txtAccountHeadCode.type="hidden";
                 txtAccountHeadCode.name="txtAccountHeadCode";
                 txtAccountHeadCode.value=Account_Head_Code;
                 cell2.appendChild(txtAccountHeadCode);
                 var currentText1=document.createTextNode(Account_Head_Code+"("+head_desc+")");
                 cell2.appendChild(currentText1);
                 mycurrent_row.appendChild(cell2);
               
                 var cell9=document.createElement("TD");       
 				var cmbSL_type=document.createElement("input");
 				cmbSL_type.type="hidden";
 				cmbSL_type.name="cmbSL_type";
 				cmbSL_type.value=Payee_Type_Code;
 				cell9.appendChild(cmbSL_type);
 				var currentText1=document.createTextNode(payee_desc);
 				cell9.appendChild(currentText1);
 				mycurrent_row.appendChild(cell9);
 				
 				var cell10=document.createElement("TD");       
 				var cmbSL_Code=document.createElement("input");
 				cmbSL_Code.type="hidden";
 				cmbSL_Code.name="cmbSL_Code";
 				cmbSL_Code.value=Payee_Code;
 				cell10.appendChild(cmbSL_Code);
 				var currentText1=document.createTextNode(code_desc);
 				cell10.appendChild(currentText1);
 				mycurrent_row.appendChild(cell10); 
                 
				var cell3 = document.createElement("TD");
				 var txtInvoiceNo=document.createElement("input");
				 txtInvoiceNo.type="hidden";
				 txtInvoiceNo.name="txtInvoiceNo";
				 txtInvoiceNo.value=txtInvoiceNos;
                 cell3.appendChild(txtInvoiceNo);
				var txtInvoiceNo = document.createTextNode(txtInvoiceNos);
				cell3.appendChild(txtInvoiceNo);
				mycurrent_row.appendChild(cell3);
				

				var cell4 = document.createElement("TD");
				 var InvoiceDate=document.createElement("input");
				 InvoiceDate.type="hidden";
				 InvoiceDate.name="InvoiceDate";
				 InvoiceDate.value=InvoiceDates;
				 cell4.appendChild(InvoiceDate);
				var InvoiceDate = document.createTextNode("-");
				cell4.appendChild(InvoiceDate);
				mycurrent_row.appendChild(cell4);

				var cell5 = document.createElement("TD");
				 var txtInvoiceAmount=document.createElement("input");
				 txtInvoiceAmount.type="hidden";
				 txtInvoiceAmount.name="txtInvoiceAmount";
				 txtInvoiceAmount.value=txtInvoiceAmounts;
				 cell5.appendChild(txtInvoiceAmount);
				var txtInvoiceAmount = document.createTextNode(txtInvoiceAmounts);
				cell5.appendChild(txtInvoiceAmount);
				mycurrent_row.appendChild(cell5);
				agree_number=0;
				var cell6 = document.createElement("TD");
				var agree_no=document.createElement("input");
				agree_no.type="hidden";
				agree_no.name="agree_no";
				agree_no.value=agree_number;
				 cell6.appendChild(agree_no);
				var txtAgreementNo = document.createTextNode(agree_number);
				cell6.appendChild(txtAgreementNo);
				mycurrent_row.appendChild(cell6);
				AgreementDate=0;
				var cell7 = document.createElement("TD");
				var agree_date=document.createElement("input");
				agree_date.type="hidden";
				agree_date.name="agree_date";
				agree_date.value=AgreementDate;
				cell7.appendChild(agree_date);
				var AgreementDate = document.createTextNode("-");
				cell7.appendChild(AgreementDate);
				mycurrent_row.appendChild(cell7);
				agree_amount=0;
				var cell8 = document.createElement("TD");
				var agree_amt=document.createElement("input");
				agree_amt.type="hidden";
				agree_amt.name="agree_amt";
				agree_amt.value=agree_amount;
				cell8.appendChild(agree_amt);
				var txtAgreementAmount = document.createTextNode(agree_amount);
				cell8.appendChild(txtAgreementAmount);
				mycurrent_row.appendChild(cell8);
				
				
			
				var cell11 = document.createElement("TD");
				var cr_dr=document.createElement("input");
				cr_dr.type="hidden";
				cr_dr.name="cr_dr";
				cr_dr.value=indicator;
				cell11.appendChild(cr_dr);
				var txtCRDRIndicator = document.createTextNode(indicator);
				cell11.appendChild(txtCRDRIndicator);
				mycurrent_row.appendChild(cell11);
				
				var cell12 = document.createElement("TD");
				var text_amt=document.createElement("input");
				text_amt.type="hidden";
				text_amt.name="text_amt";
				text_amt.value=amount;
				//text_amt.value=debit_amt;
				cell12.appendChild(text_amt);
				var txtAmount = document.createTextNode(amount);
				cell12.appendChild(txtAmount);
				mycurrent_row.appendChild(cell12);
				rdoIfFirstParty="Y";
				var cell13 = document.createElement("TD");
				var rad_id=document.createElement("input");
				rad_id.type="hidden";
				rad_id.name="rad_id";
				rad_id.value=rdoIfFirstParty;
				cell13.appendChild(rad_id);
				var rdoIfFirstParty = document.createTextNode(rdoIfFirstParty);
				cell13.appendChild(rdoIfFirstParty);
				mycurrent_row.appendChild(cell13);

				var cell14 = document.createElement("TD");
				var pay_cod=document.createElement("input");
				pay_cod.type="hidden";
				pay_cod.name="pay_cod";
				pay_cod.value=Payee_Type_Code;
				cell14.appendChild(pay_cod);
				var txtPayeeType = document.createTextNode(Payee_Type_Code);
				cell14.appendChild(txtPayeeType);
				mycurrent_row.appendChild(cell14);

				var cell15 = document.createElement("TD");
				var code_val=document.createElement("input");
				code_val.type="hidden";
				code_val.name="code_val";
				code_val.value=Payee_Code;
				cell15.appendChild(code_val);
				var txtPayeeCode = document.createTextNode(Payee_Code);
				cell15.appendChild(txtPayeeCode);
				mycurrent_row.appendChild(cell15);
				
				var cell16 = document.createElement("TD");
				var rem_text=document.createElement("input");
				rem_text.type="hidden";
				rem_text.name="rem_text";
				rem_text.value=Particulars;
				cell16.appendChild(rem_text);
				var mtxtRemarks1 = document.createTextNode(Particulars);
				cell16.appendChild(mtxtRemarks1);
				mycurrent_row.appendChild(cell16);
				
				tbody.appendChild(mycurrent_row);
				seq++;
			  
			     
			//  alert("ll::::"+headcode.length);
		/*	  var debitAccHead = baseResponse.getElementsByTagName("Account_Head_Code")[i].firstChild.nodeValue;
			  var head_desc = baseResponse.getElementsByTagName("head_desc")[i].firstChild.nodeValue;
                  var mycurrent_row=document.createElement("TR");
                  mycurrent_row.id=seq;
               
                 var cell2=document.createElement("TD");       
                  var H_code=document.createElement("input");
                  H_code.type="hidden";
                  H_code.name="H_code";
                  H_code.value=debitAccHead;
                  cell2.appendChild(H_code);
                  var currentText1=document.createTextNode(debitAccHead+"("+head_desc+")");
                  cell2.appendChild(currentText1);
                  mycurrent_row.appendChild(cell2);
                  
                  var cell3=document.createElement("TD");       
                  var s_type=document.createElement("input");
                  s_type.type="hidden";
                  s_type.name="s_type";
                  s_type.value=Payee_Type_Code;
                  cell3.appendChild(s_type);
                  var currentText1=document.createTextNode(payee_desc);
                  cell3.appendChild(currentText1);
                  mycurrent_row.appendChild(cell3);
                  
                  var cell4=document.createElement("TD");       
                  var s_code=document.createElement("input");
                  s_code.type="hidden";
                  s_code.name="s_code";
                  s_code.value=Payee_Code;
                  cell4.appendChild(s_code);
                  var currentText1=document.createTextNode(code_desc);
                  cell4.appendChild(currentText1);
                  mycurrent_row.appendChild(cell4);
                  
                  var cell5=document.createElement("TD");       
                  var indicator_val=document.createElement("input");
                  indicator_val.type="hidden";
                  indicator_val.name="indicator_val";
                  indicator_val.value=indicator;
                  cell5.appendChild(indicator_val);
                  var currentText1=document.createTextNode(indicator);
                  cell5.appendChild(currentText1);
                  mycurrent_row.appendChild(cell5);
                  
                  var cell6=document.createElement("TD");       
                  var f_amount=document.createElement("input");
                  f_amount.type="hidden";
                  f_amount.name="f_amount";
                  f_amount.value=amount;
                  cell6.appendChild(f_amount);
                  var currentText1=document.createTextNode(amount);
                  cell6.appendChild(currentText1);
                  mycurrent_row.appendChild(cell6);
                  
                  var cell7=document.createElement("TD");       
                  var inv_no=document.createElement("input");
                  inv_no.type="hidden";
                  inv_no.name="inv_no";
                  inv_no.value=0;
                  cell7.appendChild(inv_no);
                  var currentText1=document.createTextNode("-");
                  cell7.appendChild(currentText1);
                  mycurrent_row.appendChild(cell7);
                  
                  var cell8=document.createElement("TD");       
                  var inv_date=document.createElement("input");
                  inv_date.type="hidden";
                  inv_date.name="inv_date";
                  inv_date.value=null;
                  cell8.appendChild(inv_date);
                  var currentText1=document.createTextNode("-");
                  cell8.appendChild(currentText1);
                  mycurrent_row.appendChild(cell8);
                  
                  var cell9=document.createElement("TD");       
                  var inv_amt=document.createElement("input");
                  inv_amt.type="hidden";
                  inv_amt.name="inv_amt";
                  inv_amt.value=0;
                  cell9.appendChild(inv_amt);
                  var currentText1=document.createTextNode("0");
                  cell9.appendChild(currentText1);
                  mycurrent_row.appendChild(cell9);
                  
                  var cell10=document.createElement("TD");       
                  var agr_no=document.createElement("input");
                  agr_no.type="hidden";
                  agr_no.name="agr_no";
                  agr_no.value=0;
                  cell10.appendChild(agr_no);
                  var currentText1=document.createTextNode("-");
                  cell10.appendChild(currentText1);
                  mycurrent_row.appendChild(cell10);
                  
                  var cell11=document.createElement("TD");       
                  var agr_date=document.createElement("input");
                  agr_date.type="hidden";
                  agr_date.name="agr_date";
                  agr_date.value=null;
                  cell11.appendChild(agr_date);
                  var currentText1=document.createTextNode("-");
                  cell11.appendChild(currentText1);
                  mycurrent_row.appendChild(cell11);
                  
                  var cell12=document.createElement("TD");       
                  var agr_amt=document.createElement("input");
                  agr_amt.type="hidden";
                  agr_amt.name="agr_amt";
                  agr_amt.value=0;
                  cell12.appendChild(agr_amt);
                  var currentText1=document.createTextNode("0");
                  cell12.appendChild(currentText1);
                  mycurrent_row.appendChild(cell12);
                  
                  cell13=document.createElement("TD");
                  cell13.style.textAlign='center'; 
                  var chcksel="";
                  checkparam=seq;
                 if(window.navigator.appName.toLowerCase().indexOf("netscape")==-1)
                 {
                 	chcksel=document.createElement("<input type='checkbox' name='chckparameter' id='chckparameter' value='"+checkparam+"' />");
                 }
                 else
                 {
                        var chcksel=document.createElement("input");
                        chcksel.type="checkbox";
                        chcksel.checked='checked';
                        chcksel.name="chckparameter";
                        chcksel.value= checkparam;
                 }
                 cell13.appendChild(chcksel);
                 mycurrent_row.appendChild(cell13);
                  
                  var cell14=document.createElement("TD");       
                  var s_type=document.createElement("input");
                  s_type.type="hidden";
                  s_type.name="s_type";
                  s_type.value=Payee_Type_Code;
                  cell14.appendChild(s_type);
                  var currentText1=document.createTextNode(payee_desc);
                  cell14.appendChild(currentText1);
                  mycurrent_row.appendChild(cell14);
                  
                  var cell15=document.createElement("TD");       
                  var s_code=document.createElement("input");
                  s_code.type="hidden";
                  s_code.name="s_code";
                  s_code.value=Payee_Code;
                  cell15.appendChild(s_code);
                  var currentText1=document.createTextNode(code_desc);
                  cell15.appendChild(currentText1);
                  mycurrent_row.appendChild(cell15);
                  
                  var cell16=document.createElement("TD");       
                  var particulars_val=document.createElement("input");
                  particulars_val.type="hidden";
                  particulars_val.name="particulars_val";
                  particulars_val.value=Particulars;
                  cell16.appendChild(particulars_val);
                  var currentText1=document.createTextNode(Particulars);
                  cell16.appendChild(currentText1);
                  mycurrent_row.appendChild(cell16);
                  
                  tbody.appendChild(mycurrent_row);
                  seq++;   */
          }
		
		
			
	} else {
		alert("Failed To Load Values");
	}
}

function clearone()
{
	var tbody=document.getElementById("tblList");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
            tbody.deleteRow(0);
    }
    
    document.frm_Memo_Of_Payment.cboCashBook_Year.value = "";
	document.frm_Memo_Of_Payment.cboCashBook_Month.value = "s";
	document.frm_Memo_Of_Payment.cboBillNo.length=1;
	
    document.frm_Memo_Of_Payment.txtBillMajorType.value = "";
	document.frm_Memo_Of_Payment.majorDesc.value = "";
	
	document.frm_Memo_Of_Payment.txtBillDate.value ="";
	document.frm_Memo_Of_Payment.txtSanctionProceedingNo.value ="";
	document.frm_Memo_Of_Payment.txtSanctionProceedingDate.value ="";
	document.frm_Memo_Of_Payment.txtDebitAccountHead.value ="";
	document.frm_Memo_Of_Payment.txtSanctionProceedingid.value ="";
	document.frm_Memo_Of_Payment.txtSanctionedAmount.value ="";
}

function addMst(path) {
    //alert(path);
	var ljv="noljv";
	var t;
	var k = 0;
	 var cr_amt=0;
     var db_amt=0;
	var tbody = document.getElementById("tblList");
	var rowcount = tbody.rows.length;
	var al2 = new Array();
	
	for ( var i = 0; i < rowcount; i++) {
		var r = tbody.rows[i];
		var s = r.cells.length;
		for ( var j = 1; j < s; j++) {
			al2[k] = r.cells[j].firstChild.value;
		//	alert(al2[k]);
			k++;
		}
	//alert(r.cells[12].firstChild.value);
	/* if(r.cells[12].firstChild.value=="N")
	{
		ljv="yesljv";
	}  */
	
	}  
	if(tbody.rows.length>0)
	{
    rows=tbody.getElementsByTagName("tr");
	 for(i=0;i<rows.length;i++)
     {
         var cells=rows[i].cells;
         db_amt=parseFloat(db_amt) + parseFloat(cells.item(10).lastChild.nodeValue);
		/* if(cells.item(2).lastChild.nodeValue=='CR')
	     {
	             cr_amt=parseFloat(cr_amt) + parseFloat(cells.item(10).lastChild.nodeValue);
	     }
		 else
		 {
			 db_amt=parseFloat(db_amt) + parseFloat(cells.item(10).lastChild.nodeValue);
		 } */
		 if(cells.item(12).firstChild.value=="N")
		 {
			 ljv="yesljv"; 
		 }
     }
	}
     
	
	var sancidwith;
	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
    var cboOffice_code = document.getElementById("cmbOffice_code").value;
    var cboCashBook_Year = document.getElementById("cboCashBook_Year").value;
    var cboCashBook_Month = document.getElementById("cboCashBook_Month").value;    
    var cboBillNo = document.getElementById("cboBillNo").value;
    
    var txtBillMajorType = document.getElementById("txtBillMajorType").value;
    var txtBillDate = document.getElementById("txtBillDate").value;
    var txtSanctionProceedingNo = document.getElementById("txtSanctionProceedingNo").value;
    var txtSanctionProceedingid = document.getElementById("txtSanctionProceedingid").value;
    
    var txtSanctionProceedingDate = document.getElementById("txtSanctionProceedingDate").value;
    var txtSanctionedAmount = document.getElementById("txtSanctionedAmount").value;
    var txtDebitAccountHead = document.getElementById("txtDebitAccountHead").value;
    var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;
    //alert("cmbMas_SL_type::::"+cmbMas_SL_type);if
    var cmbMas_SL_Code = document.getElementById("cmbMas_SL_Code").value;
    var txtDebitAmount = document.getElementById("txtDebitAmount").value;
    var mtxtRemarks = document.getElementById("mtxtRemarks").value;
    if(document.frm_Memo_Of_Payment.sancidwith[0].checked==true)
	{
		sancidwith ="with_sanc"; 
	}
	else if(document.frm_Memo_Of_Payment.sancidwith[1].checked==true)
	{
		sancidwith ="with_out_sanc"; 
	}
	else if(document.frm_Memo_Of_Payment.sancidwith[2].checked==true)
	{
		sancidwith ="with_out_sanc_GPF"; 
	}
    var bs = parseInt(txtSanctionedAmount);
    //alert(bs);
	var t;
	var k = 0
	var g = 0;
	var tbody = document.getElementById("tblList");
	var rowcount = tbody.rows.length;
	
	var al = new Array();
	for ( var i = 0; i < rowcount; i++) {
		var r = tbody.rows[i];
		var s = r.cells.length;
		
			g = g + parseInt(r.cells[11].firstChild.value);			
	
	}
	
    if ((document.frm_Memo_Of_Payment.cboCashBook_Month.value == "")
			|| (document.frm_Memo_Of_Payment.cboCashBook_Month.value.length <= 0)
			|| (document.frm_Memo_Of_Payment.cboCashBook_Month.value == "s")) {
		alert("Select CashBook Month in the Field");
		document.frm_Memo_Of_Payment.cboCashBook_Month.focus();
	} else if (txtBillMajorType == "") {
		alert("Enter Bill Major Type in the Field");
		document.frm_Memo_Of_Payment.txtBillMajorType.focus();
	}else if (txtBillDate == "") {
		alert("Enter BillDate in the Field");
		document.frm_Memo_Of_Payment.txtBillDate.focus();
	}else if (txtSanctionProceedingNo == "") {
		alert("Enter Sanction Proceeding No in the Field");
		document.frm_Memo_Of_Payment.txtSanctionProceedingNo.focus();
	}else if (txtSanctionProceedingDate == "") {
		alert("Enter Sanction Proceeding Date in the Field");
		document.frm_Memo_Of_Payment.txtSanctionProceedingDate.focus();
	}else if (txtSanctionedAmount == "") {
		alert("Enter Sanctioned Amount in the Field");
		document.frm_Memo_Of_Payment.txtSanctionedAmount.focus();
	}else if (txtDebitAccountHead == "") {
		alert("Enter Debit Accoun tHead in the Field");
		document.frm_Memo_Of_Payment.txtDebitAccountHead.focus();
	}
	/*else if ((document.frm_Memo_Of_Payment.cmbMas_SL_type.value == "")
			|| (document.frm_Memo_Of_Payment.cmbMas_SL_type.value.length <= 0)
			|| (document.frm_Memo_Of_Payment.cmbMas_SL_type.value == "s")) {
		alert("Select Sub-Ledger Type in the Field");
		document.frm_Memo_Of_Payment.cmbMas_SL_type.focus();
	} else if ((document.frm_Memo_Of_Payment.cmbMas_SL_Code.value == "")
			|| (document.frm_Memo_Of_Payment.cmbMas_SL_Code.value.length <= 0)
			|| (document.frm_Memo_Of_Payment.cmbMas_SL_Code.value == "s")) {
		alert("Select Sub-Ledger Code in the Field");
		dbaseResponse.getElementsByEDITTagName("id")[0].firstChild.nodeValue;ocument.frm_Memo_Of_Payment.cmbMas_SL_Code.focus();
	} */
	else if (txtDebitAmount == "") {
		alert("Enter Debit Amount in the Field");
		document.frm_Memo_Of_Payment.txtDebitAmount.focus();
	}
    
	else {
		if(ljv=="yesljv")
		{
			if(txtDebitAmount!=parseFloat(db_amt))      // Either CR or DR must
				// equal in total
				{
				alert("Amount doesn't Tally With General BillAmount.. Difference " +(parseFloat(cr_amt)-parseFloat(db_amt)));
				return false;
				}
		}
		
		var url = path + "/Memo_Of_Pay_Edit?command=addMst&cmbAcc_UnitCode="
				+ cboAcc_UnitCode + "&cmbOffice_code=" + cboOffice_code
				+ "&cboCashBook_Year=" + cboCashBook_Year
				+ "&cboCashBook_Month=" + cboCashBook_Month + "&cboBillNo="
				+ cboBillNo + "&txtBillMajorType=" + txtBillMajorType
				+ "&txtBillDate=" + txtBillDate +"&txtSanctionProceedingid="+txtSanctionProceedingid+"&txtSanctionProceedingNo="
				+ txtSanctionProceedingNo + "&txtSanctionProceedingDate=" + txtSanctionProceedingDate
				+ "&txtSanctionedAmount=" + txtSanctionedAmount + "&txtDebitAccountHead="
				+ txtDebitAccountHead + "&cmbMas_SL_type="
				+ cmbMas_SL_type + "&cmbMas_SL_Code=" + cmbMas_SL_Code
				+ "&txtDebitAmount=" + txtDebitAmount + "&mtxtRemarks=" + mtxtRemarks + "&al=" + al2
				+ "&rowcount=" + rowcount+"&ljv="+ljv+"&sancidwith="+sancidwith; 

		//alert("addMst"+url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function addMst1(baseResponse) {
	var flagE = baseResponse.getElementsByTagName("flagE")[0].firstChild.nodeValue;	
	if (flagE == "Exist") {
		var tbody = document.getElementById("tblList");
		  var rowcount=tbody.rows.length;
		    for(var i=0;i<rowcount;i++)
		        {
		    	 var r=i.rowIndex;	   
		           tbody.deleteRow(r);
		        }
		alert("Record Already Exist.");
	} else {
		//alert("rer");
		var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
		
		if (flag == "success") {
			var tbody = document.getElementById("tblList");
			  var rowcount=tbody.rows.length;
			    for(var i=0;i<rowcount;i++)
			        {
			    	 var r=i.rowIndex;	   
			           tbody.deleteRow(r);
			        }
			alert("Record Inserted Into Database successfully.");
		} else {
			alert("Failed to Add values");
		}
	}
	refresh();
}


function add(path) {
	//alert("add ");
	var txtAccountHeadCode = document.getElementById("txtAcc_HeadCode").value;
	var txtAcc_HeadDesc = document.getElementById("txtAcc_HeadDesc").value;
    var cmbSL_type = document.getElementById("cmbSL_type").value;    
    var cmbSL_Code = document.getElementById("cmbSL_Code").value;
   // alert("cmbSL_type "+cmbSL_type);
    if(document.getElementById("cmbSL_type").length<1 && document.getElementById("cmbSL_type").value=="")
    {
			                alert("Select a Sub-Ledger Type");
			                return false;
	}
    if(document.getElementById("cmbSL_type").value!="")
    {
          	if(document.getElementById("cmbSL_Code").value=="")
            {
		             alert("Select The Sub Ledger Code");
		             return false;
            }
    }
    if(cmbSL_type=="")
    {
    	typedesc="-";
    	cmbSL_type=0;
    }
    else
    {
    	typedesc=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text;
    }
    if(cmbSL_Code=="")
    {
    	codedesc="-";
    	cmbSL_Code=0;
    }
    else
    {
    	codedesc=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text;
    }
    var txtInvoiceNo = document.getElementById("txtInvoiceNo").value;
    if(txtInvoiceNo=="")
    {
    	txtInvoiceNo=0;
    }
    var txtInvoiceDate = document.getElementById("txtInvoiceDate").value;
    if(txtInvoiceDate=="")
    {
    	txtInvoiceDate=0;
    }
    var txtInvoiceAmount = document.getElementById("txtInvoiceAmount").value;
    if(txtInvoiceAmount=="")
    {
    	txtInvoiceAmount=0;
    }
    var txtAgreementNo = document.getElementById("txtAgreementNo").value;
    if(txtAgreementNo=="")
    {
    	txtAgreementNo=0;
    }
    var txtAgreementDate = document.getElementById("txtAgreementDate").value;
    if(txtAgreementDate=="")
    {
    	txtAgreementDate=0;
    }
    var txtAgreementAmount = document.getElementById("txtAgreementAmount").value;
    if(txtAgreementAmount=="")
    {
    	txtAgreementAmount=0;
    }
    var txtCRDRIndicator = document.getElementById("txtCRDRIndicator").value;
    var txtAmount = document.getElementById("txtAmount").value;
    if(document.frm_Memo_Of_Payment.rdoIfFirstParty[0].checked==true)
    {
    	party="Y";
    	indic="CR";
    }
    else
    {
    	party="N";
    	indic="CR";
    }
    var txtPayeeType = document.getElementById("txtPayeeType").value;
    if(txtPayeeType=="")
    {
    	txtPayeeType=0;
    }
    var txtPayeeCode = document.getElementById("txtPayeeCode").value;
    if(txtPayeeCode=="")
    {
    	txtPayeeCode=0;
    }
    var mtxtRemarks1 = document.getElementById("mtxtRemarks1").value;
    if(mtxtRemarks1=="")
    {
    	mtxtRemarks1=0;
    }
    	
		var tbody = document.getElementById("tblList");
		var table = document.getElementById("Existing");
		var mycurrent_row = document.createElement("TR");

		mycurrent_row.id = slNo;
		var cell = document.createElement("TD");
		var anc = document.createElement("A");
		var url = "javascript:loadValuesFromTable('" + slNo + "')";
		anc.href = url;
		var txtedit = document.createTextNode("Edit");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);

		 var cell2=document.createElement("TD");       
         var H_code=document.createElement("input");
         H_code.type="hidden";
         H_code.name="H_code";
         H_code.value=txtAccountHeadCode;
         cell2.appendChild(H_code);
         var currentText=document.createTextNode(txtAccountHeadCode+"("+txtAcc_HeadDesc+")");
         cell2.appendChild(currentText);
         mycurrent_row.appendChild(cell2);
          
         cell2=document.createElement("TD");
         var SL_type=document.createElement("input");
         SL_type.type="hidden";
         SL_type.name="SL_type";
         SL_type.value=cmbSL_type;
         cell2.appendChild(SL_type);
         var currentText=document.createTextNode(typedesc);
         cell2.appendChild(currentText);
         mycurrent_row.appendChild(cell2);
         
         cell2=document.createElement("TD");
         var SL_code=document.createElement("input");
         SL_code.type="hidden";
         SL_code.name="SL_code";
         SL_code.value=cmbSL_Code;
         cell2.appendChild(SL_code);
         var currentText=document.createTextNode(codedesc);
         cell2.appendChild(currentText);
         mycurrent_row.appendChild(cell2);

		 var cell3 = document.createElement("TD");
		 var invno=document.createElement("input");
		 invno.type="hidden";
		 invno.name="invno";
		 invno.value=txtInvoiceNo;
		 cell3.appendChild(invno);
		 var txtInvoiceNo = document.createTextNode(txtInvoiceNo);
		 cell3.appendChild(txtInvoiceNo);
		 mycurrent_row.appendChild(cell3);
		

		 var cell4 = document.createElement("TD");
		 var invdate=document.createElement("input");
		 invdate.type="hidden";
		 invdate.name="invdate";
		 invdate.value=txtInvoiceDate;
		 cell4.appendChild(invdate);
		 var InvoiceDate = document.createTextNode("-");
		 cell4.appendChild(InvoiceDate);
		 mycurrent_row.appendChild(cell4);

		 var cell5 = document.createElement("TD");
		 var invamt=document.createElement("input");
		 invamt.type="hidden";
		 invamt.name="invamt";
		 invamt.value=txtInvoiceAmount;
		 cell5.appendChild(invamt);
		 var txtInvoiceAmount = document.createTextNode(txtInvoiceAmount);
		 cell5.appendChild(txtInvoiceAmount);
		 mycurrent_row.appendChild(cell5);

		var cell6 = document.createElement("TD");
		var agno=document.createElement("input");
		agno.type="hidden";
		agno.name="agno";
		agno.value=txtAgreementNo;
		cell6.appendChild(agno);
		var txtAgreementNo = document.createTextNode(txtAgreementNo);
		cell6.appendChild(txtAgreementNo);
		mycurrent_row.appendChild(cell6);
		
		var cell7 = document.createElement("TD");
		var agdate=document.createElement("input");
		agdate.type="hidden";
		agdate.name="agdate";
		agdate.value=txtAgreementDate;
		cell7.appendChild(agdate);
		var AgreementDate = document.createTextNode("-");
		cell7.appendChild(AgreementDate);
		mycurrent_row.appendChild(cell7);

		var cell8 = document.createElement("TD");
		var agamt=document.createElement("input");
		agamt.type="hidden";
		agamt.name="agamt";
		agamt.value=txtAgreementAmount;
		cell8.appendChild(agamt);
		var txtAgreementAmount = document.createTextNode(txtAgreementAmount);
		cell8.appendChild(txtAgreementAmount);
		mycurrent_row.appendChild(cell8);
		
		var cell11 = document.createElement("TD");
		var ind=document.createElement("input");
		ind.type="hidden";
		ind.name="ind";
		ind.value=indic;
		cell11.appendChild(ind);
		var txtCRDRIndicator = document.createTextNode(indic);
		cell11.appendChild(txtCRDRIndicator);
		mycurrent_row.appendChild(cell11);
		
		var cell12 = document.createElement("TD");
		var detamt=document.createElement("input");
		detamt.type="hidden";
		detamt.name="detamt";
		detamt.value=txtAmount;
		cell12.appendChild(detamt);
		var txtAmount = document.createTextNode(txtAmount);
		cell12.appendChild(txtAmount);
		mycurrent_row.appendChild(cell12);

		var cell13 = document.createElement("TD");
		var fparty=document.createElement("input");
		fparty.type="hidden";
		fparty.name="fparty";
		fparty.value=party;
		cell13.appendChild(fparty);
		var rdoIfFirstParty = document.createTextNode(party);
		cell13.appendChild(rdoIfFirstParty);
		mycurrent_row.appendChild(cell13);
		

		var cell14 = document.createElement("TD");
		var paytype=document.createElement("input");
		paytype.type="hidden";
		paytype.name="paytype";
		paytype.value=txtPayeeType;
		cell14.appendChild(paytype);
		var txtPayeeType = document.createTextNode(txtPayeeType);
		cell14.appendChild(txtPayeeType);
		mycurrent_row.appendChild(cell14);

		var cell15 = document.createElement("TD");
		var paycode=document.createElement("input");
		paycode.type="hidden";
		paycode.name="paycode";
		paycode.value=txtPayeeCode;
		cell15.appendChild(paycode);
		var txtPayeeCode = document.createTextNode(txtPayeeCode);
		cell15.appendChild(txtPayeeCode);
		mycurrent_row.appendChild(cell15);

		var cell16 = document.createElement("TD");
		var remarks=document.createElement("input");
		remarks.type="hidden";
		remarks.name="remarks";
		remarks.value=mtxtRemarks1;
		cell16.appendChild(remarks);
		var mtxtRemarks1 = document.createTextNode(mtxtRemarks1);
		cell16.appendChild(mtxtRemarks1);
		mycurrent_row.appendChild(cell16);
		
		tbody.appendChild(mycurrent_row);
	
}

function loadValuesFromTable(idd) {
	alert(idd);
	var r = document.getElementById(idd);
	var rcells = r.cells;
	//alert(rcells.item(1).firstChild.value);
	var tesplit=(rcells.item(1).lastChild.nodeValue).split("(");
	
	var splhdesc=tesplit[1].split(")");
	document.frm_Memo_Of_Payment.txtAcc_HeadCode.value = rcells.item(1).firstChild.value;
	document.frm_Memo_Of_Payment.txtAcc_HeadDesc.value = splhdesc[0];
	
     document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].value=rcells.item(2).firstChild.value;
     document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text=rcells.item(2).lastChild.nodeValue;
	
 
     document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].value=rcells.item(3).firstChild.value;
     document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text=rcells.item(3).lastChild.nodeValue;
	

	document.frm_Memo_Of_Payment.rdoIfFirstParty.value = rcells.item(12).firstChild.value;
	document.frm_Memo_Of_Payment.txtAmount.value = rcells.item(11).firstChild.value;
	document.frm_Memo_Of_Payment.txtPayeeType.value = rcells.item(13).firstChild.value;
	document.frm_Memo_Of_Payment.txtPayeeCode.value = rcells.item(14).firstChild.value;
	document.frm_Memo_Of_Payment.mtxtRemarks1.value = rcells.item(15).firstChild.value;
	//alert(":"+rcells.item(4).lastChild.nodeValue);
	document.frm_Memo_Of_Payment.txtInvoiceNo.value = rcells.item(4).lastChild.nodeValue;
	//alert(":"+rcells.item(4).firstChild.value);
	document.frm_Memo_Of_Payment.txtInvoiceDate.value = rcells.item(5).firstChild.value;
	document.frm_Memo_Of_Payment.txtInvoiceAmount.value = rcells.item(6).firstChild.value;
	document.frm_Memo_Of_Payment.txtAgreementNo.value = rcells.item(7).firstChild.value;
	document.frm_Memo_Of_Payment.txtAgreementDate.value = rcells.item(8).firstChild.value;
	document.frm_Memo_Of_Payment.txtAgreementAmount.value = rcells.item(9).firstChild.value;
	document.frm_Memo_Of_Payment.slNo.value = idd;
	if(idd==0){
	document.frm_Memo_Of_Payment.txtCRDRIndicator.value="DEBIT"; 
	document.getElementById("txtPayeeType").readOnly="true";
	document.getElementById("txtPayeeCode").readOnly="true";
	document.getElementById("cmbSL_type").readOnly="true";
	document.getElementById("cmbSL_Code").readOnly="true";
	document.frm_Memo_Of_Payment.txtAcc_HeadCode.readOnly="true";
	
	}
	else
	{
		document.frm_Memo_Of_Payment.txtCRDRIndicator.value="CREDIT"; 
		document.getElementById("txtPayeeType").readOnly="false";
		document.getElementById("txtPayeeCode").readOnly="false";
		document.getElementById("cmbSL_type").readOnly="false";
		document.getElementById("cmbSL_Code").readOnly="false";
		document.frm_Memo_Of_Payment.txtAcc_HeadCode.readOnly="false";
	}
	
	document.frm_Memo_Of_Payment.onsubmit.disabled = true;
	document.frm_Memo_Of_Payment.ondelete.disabled = false;
	document.frm_Memo_Of_Payment.onupdate.disabled = false;
}


function update_new() {
//alert("Update");
	
		var items = new Array();
		//if
		
		items[0] = document.frm_Memo_Of_Payment.txtAcc_HeadCode.value;
		items[1] = document.getElementById("txtInvoiceNo").value;
		
		items[2] =document.getElementById("txtInvoiceDate").value;
		items[3] = document.getElementById("txtInvoiceAmount").value;
		items[4] = document.getElementById("txtAgreementNo").value;
		items[5] =  document.getElementById("txtAgreementDate").value;
		items[6] = document.getElementById("txtAgreementAmount").value;
		items[7] =document.getElementById("cmbSL_type").value;
		items[8] = document.getElementById("cmbSL_Code").value;
		items[9] =  document.getElementById("txtCRDRIndicator").value;
		items[10] =document.getElementById("txtAmount").value;
		items[11] = document.getElementById("rdoIfFirstParty").value;
		items[12] = document.getElementById("txtPayeeType").value;
		items[13] =document.getElementById("txtPayeeCode").value;
		items[14] = document.getElementById("mtxtRemarks1").value;
		items[15] =document.getElementById("slNo").value;
		
		items[16] = document.frm_Memo_Of_Payment.txtAcc_HeadDesc.value;
		items[17]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text;
		items[18]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text;
	
		var r = document.getElementById(items[15]);
		var rcells = r.cells;
		
		rcells.item(1).firstChild.value = items[0];
		 try{rcells.item(1).lastChild.nodeValue=items[0]+"("+items[16]+")";}catch(e){}
		
		rcells.item(2).firstChild.value = items[7];
		try{rcells.item(2).lastChild.nodeValue=items[17];}catch(e){}
		
		rcells.item(3).firstChild.value = items[8];
		try{rcells.item(3).lastChild.nodeValue=items[18];}catch(e){}
		
		rcells.item(4).firstChild.value = items[1];
		rcells.item(4).lastChild.nodeValue=items[1];
		
		rcells.item(5).firstChild.value = items[2];	
		rcells.item(5).lastChild.nodeValue = items[2];
		
		rcells.item(6).firstChild.value = items[3];
		rcells.item(6).lastChild.nodeValue = items[3];
		
		rcells.item(7).firstChild.value = items[4];
		rcells.item(7).lastChild.nodeValue = items[4];
		
		rcells.item(8).firstChild.value = items[5];
		rcells.item(8).lastChild.nodeValue = items[5];
		
		rcells.item(9).firstChild.value = items[6];
		rcells.item(9).lastChild.nodeValue = items[6];
		if(items[9]=="DEBIT")
		{
			rcells.item(10).firstChild.value = "DR";
			rcells.item(10).lastChild.nodeValue = items[9];
		}
		else
		{
			rcells.item(10).firstChild.value ="CR";
			rcells.item(10).lastChild.nodeValue = items[9];
		}
		
		
		rcells.item(11).firstChild.value = items[10];
		rcells.item(11).lastChild.nodeValue = items[10];
		
		rcells.item(12).firstChild.value = items[11];
		rcells.item(12).lastChild.nodeValue = items[11];
		
		rcells.item(13).firstChild.value = items[12];
		rcells.item(13).lastChild.nodeValue = items[12];
		
		rcells.item(14).firstChild.value = items[13];
		rcells.item(14).lastChild.nodeValue = items[13];
		
		rcells.item(15).firstChild.value = items[14];	
		rcells.item(15).lastChild.nodeValue = items[14];
		clear_first();
		
		 alert("Record Updated");
}

function clear_first()
{
	
	document.frm_Memo_Of_Payment.txtAcc_HeadCode.value="";
	document.getElementById("txtInvoiceNo").value="";
	
	document.getElementById("txtInvoiceDate").value="";
	document.getElementById("txtInvoiceAmount").value="";
	document.getElementById("txtAgreementNo").value="";
	document.getElementById("txtAgreementDate").value="";
	document.getElementById("txtAgreementAmount").value="";
//	document.getElementById("cmbSL_type").value;
//	document.getElementById("cmbSL_Code").value;
	document.getElementById("txtCRDRIndicator").value="CREDIT";
	document.getElementById("txtAmount").value="";
//	document.getElementById("rdoIfFirstParty").value;
	document.getElementById("txtPayeeType").value="";
	document.getElementById("txtPayeeCode").value="";
	document.getElementById("mtxtRemarks1").value="";
	document.getElementById("slNo").value="";
}

function updateRow(baseResponse) {
//alert("Update");
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Updated Successfully.");
		refresh2();
		//alert("refresh2");
		var items = new Array();
		//if
		
		items[0] = document.frm_Memo_Of_Payment.txtAcc_HeadCode.value;
		items[1] = baseResponse.getElementsByTagName("txtInvoiceNo")[0].firstChild.nodeValue;
		items[2] = baseResponse.getElementsByTagName("InvoiceDate")[0].firstChild.nodeValue;
		items[3] = baseResponse.getElementsByTagName("txtInvoiceAmount")[0].firstChild.nodeValue;
		items[4] = baseResponse.getElementsByTagName("txtAgreementNo")[0].firstChild.nodeValue;
		items[5] = baseResponse.getElementsByTagName("AgreementDate")[0].firstChild.nodeValue;
		items[6] = baseResponse.getElementsByTagName("txtAgreementAmount")[0].firstChild.nodeValue;
		items[7] = baseResponse.getElementsByTagName("cmbSL_type")[0].firstChild.nodeValue;
		items[8] = baseResponse.getElementsByTagName("cmbSL_Code")[0].firstChild.nodeValue;
		items[9] = baseResponse.getElementsByTagName("txtCRDRIndicator")[0].firstChild.nodeValue;
		items[10] = baseResponse.getElementsByTagName("txtAmount")[0].firstChild.nodeValue;
		items[11] = baseResponse.getElementsByTagName("rdoIfFirstParty")[0].firstChild.nodeValue;
		items[12] = baseResponse.getElementsByTagName("txtPayeeType")[0].firstChild.nodeValue;
		items[13] = baseResponse.getElementsByTagName("txtPayeeCode")[0].firstChild.nodeValue;
		items[14] = baseResponse.getElementsByTagName("mtxtRemarks1")[0].firstChild.nodeValue;
		items[15] = baseResponse.getElementsByTagName("slNo")[0].firstChild.nodeValue;
		
		var r = document.getElementById(items[15]);
		var rcells = r.cells;
		rcells.item(1).firstChild.nodeValue = items[0];
		rcells.item(2).firstChild.nodeValue = items[1];
		rcells.item(3).firstChild.nodeValue = items[2];
		rcells.item(4).firstChild.nodeValue = items[3];
		rcells.item(5).firstChild.nodeValue = items[4];		
		rcells.item(6).firstChild.nodeValue = items[5];
		rcells.item(7).firstChild.nodeValue = items[6];
		rcells.item(8).firstChild.nodeValue = items[7];
		rcells.item(9).firstChild.nodeValue = items[8];
		rcells.item(10).firstChild.nodeValue = items[9];		
		rcells.item(11).firstChild.nodeValue = items[10];
		rcells.item(12).firstChild.nodeValue = items[11];
		rcells.item(13).firstChild.nodeValue = items[12];
		rcells.item(14).firstChild.nodeValue = items[13];
		rcells.item(15).firstChild.nodeValue = items[14];		
	} else {
		alert("Failed to update values");
	}
}
function deletee(path) {
	//alert(path);
	var slNo = document.getElementById("slNo").value;
	//alert("DELETE:========>>>>>>>"+slNo);
	var r = confirm("Are U Sure?");
	if (r == true) {
		var url = path + "/Memo_Of_Pay_Edit?command=deleted&slNo=" + slNo;
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function deleteRow(baseResponse) {	
		var ApportCode =baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue; 			
		//alert(ApportCode);
		var tbody = document.getElementById("Existing");
		var r = document.getElementById(ApportCode);
		var ri = r.rowIndex;
		tbody.deleteRow(ri);
		alert("Deleted Successfully");
		refresh2();	
}

function refresh() {

	var xmlrequest = AjaxFunction();

	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	document.frm_Memo_Of_Payment.cboCashBook_Year.value = year
	document.frm_Memo_Of_Payment.cboCashBook_Month.value = "s";
	document.frm_Memo_Of_Payment.cboBillNo.length = 1;
	document.frm_Memo_Of_Payment.txtBillMajorType.value = "";
	document.frm_Memo_Of_Payment.txtBillDate.value = "";
	document.frm_Memo_Of_Payment.txtSanctionProceedingNo.value = "";
	document.frm_Memo_Of_Payment.txtSanctionProceedingDate.value = "";
	document.frm_Memo_Of_Payment.txtSanctionedAmount.value = "";
	document.frm_Memo_Of_Payment.txtDebitAccountHead.value = "";
	document.frm_Memo_Of_Payment.cmbMas_SL_type.value = "s";
	document.frm_Memo_Of_Payment.cmbMas_SL_Code.value = "s";
	document.frm_Memo_Of_Payment.txtDebitAmount.value = "";
	document.frm_Memo_Of_Payment.mtxtRemarks.value = "";

	document.frm_Memo_Of_Payment.txtAcc_HeadCode.value = "";
	document.frm_Memo_Of_Payment.txtAcc_HeadDesc.value = "";
	//alert("test");
	document.frm_Memo_Of_Payment.txtInvoiceNo.value = "";
	document.frm_Memo_Of_Payment.txtInvoiceDate.value = "";
	document.frm_Memo_Of_Payment.txtInvoiceAmount.value = "";
	document.frm_Memo_Of_Payment.txtAgreementNo.value = "";
	//alert("test1");
	document.frm_Memo_Of_Payment.txtAgreementDate.value = "";
	document.frm_Memo_Of_Payment.txtAgreementAmount.value = "";
	document.frm_Memo_Of_Payment.cmbSL_type.value = "s";
	document.frm_Memo_Of_Payment.cmbSL_Code.value = "s";
	//document.frm_Memo_Of_Payment.txtCRDRIndicator.value = "";
	//alert("test2");
	document.frm_Memo_Of_Payment.txtAmount.value = "";
	document.frm_Memo_Of_Payment.txtPayeeType.value = "";
	document.frm_Memo_Of_Payment.txtPayeeCode.value = "";
	document.frm_Memo_Of_Payment.mtxtRemarks1.value = "";
	//alert("test3");
	document.frm_Memo_Of_Payment.onsubmit.disabled = false;
	document.frm_Memo_Of_Payment.ondelete.disabled = true;
	document.frm_Memo_Of_Payment.onupdate.disabled = true;
}

function refresh_year()
{
	var tbody=document.getElementById("tblList");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
    document.frm_Memo_Of_Payment.cboCashBook_Month.value="s";
    document.frm_Memo_Of_Payment.cboBillNo.length = 1;
	document.frm_Memo_Of_Payment.txtBillMajorType.value = "";
	document.frm_Memo_Of_Payment.txtBillDate.value = "";
	document.frm_Memo_Of_Payment.txtSanctionProceedingNo.value = "";
	document.frm_Memo_Of_Payment.txtSanctionProceedingDate.value = "";
	document.frm_Memo_Of_Payment.txtSanctionedAmount.value = "";
	document.frm_Memo_Of_Payment.txtDebitAccountHead.value = "";
	document.frm_Memo_Of_Payment.cmbMas_SL_type.value = "";
	document.frm_Memo_Of_Payment.cmbMas_SL_Code.value = "";
	document.frm_Memo_Of_Payment.txtDebitAmount.value = "";
	document.frm_Memo_Of_Payment.mtxtRemarks.value = "";

	document.frm_Memo_Of_Payment.txtAcc_HeadCode.value = "";
	document.frm_Memo_Of_Payment.txtAcc_HeadDesc.value = "";
	document.frm_Memo_Of_Payment.txtInvoiceNo.value = "";
	document.frm_Memo_Of_Payment.txtInvoiceDate.value = "";
	document.frm_Memo_Of_Payment.txtInvoiceAmount.value = "";
	document.frm_Memo_Of_Payment.txtAgreementNo.value = "";
	document.frm_Memo_Of_Payment.txtAgreementDate.value = "";
	document.frm_Memo_Of_Payment.txtAgreementAmount.value = "";
	document.frm_Memo_Of_Payment.cmbSL_type.value = "";
	document.frm_Memo_Of_Payment.cmbSL_Code.value = "";

	document.frm_Memo_Of_Payment.txtAmount.value = "";
	document.frm_Memo_Of_Payment.txtPayeeType.value = "";
	document.frm_Memo_Of_Payment.txtPayeeCode.value = "";
	document.frm_Memo_Of_Payment.mtxtRemarks1.value = "";
}

function refresh1() {

	var xmlrequest = AjaxFunction();

	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	document.frm_Memo_Of_Payment.cboCashBook_Year.value = year
	// document.frm_Memo_Of_Payment.cboCashBook_Month.value = "s";
	document.frm_Memo_Of_Payment.cboBillNo.length = 1;
	document.frm_Memo_Of_Payment.txtBillMajorType.value = "";
	document.frm_Memo_Of_Payment.txtBillDate.value = "";
	document.frm_Memo_Of_Payment.txtSanctionProceedingNo.value = "";
	document.frm_Memo_Of_Payment.txtSanctionProceedingDate.value = "";
	document.frm_Memo_Of_Payment.txtSanctionedAmount.value = "";
	document.frm_Memo_Of_Payment.txtDebitAccountHead.value = "";
	document.frm_Memo_Of_Payment.cmbMas_SL_type.value = "";
	document.frm_Memo_Of_Payment.cmbMas_SL_Code.value = "";
	document.frm_Memo_Of_Payment.txtDebitAmount.value = "";
	document.frm_Memo_Of_Payment.mtxtRemarks.value = "";

	document.frm_Memo_Of_Payment.txtAcc_HeadCode.value = "";
	document.frm_Memo_Of_Payment.txtAcc_HeadDesc.value = "";
	document.frm_Memo_Of_Payment.txtInvoiceNo.value = "";
	document.frm_Memo_Of_Payment.txtInvoiceDate.value = "";
	document.frm_Memo_Of_Payment.txtInvoiceAmount.value = "";
	document.frm_Memo_Of_Payment.txtAgreementNo.value = "";
	document.frm_Memo_Of_Payment.txtAgreementDate.value = "";
	document.frm_Memo_Of_Payment.txtAgreementAmount.value = "";
	document.frm_Memo_Of_Payment.cmbSL_type.value = "";
	document.frm_Memo_Of_Payment.cmbSL_Code.value = "";
	//document.frm_Memo_Of_Payment.txtCRDRIndicator.value = "";
	document.frm_Memo_Of_Payment.txtAmount.value = "";
	document.frm_Memo_Of_Payment.txtPayeeType.value = "";
	document.frm_Memo_Of_Payment.txtPayeeCode.value = "";
	document.frm_Memo_Of_Payment.mtxtRemarks1.value = "";
	// alert("refresh");
}

function refresh2() {


	document.frm_Memo_Of_Payment.txtAcc_HeadCode.value = "";
	document.frm_Memo_Of_Payment.txtAcc_HeadDesc.value = "";	
	document.frm_Memo_Of_Payment.cmbSL_type.value = "";
	document.frm_Memo_Of_Payment.cmbSL_Code.value = "";
	//document.frm_Memo_Of_Payment.txtCRDRIndicator.value = "";
	document.frm_Memo_Of_Payment.txtAmount.value = "";
	document.frm_Memo_Of_Payment.txtPayeeType.value = "";
	document.frm_Memo_Of_Payment.txtPayeeCode.value = "";
	document.frm_Memo_Of_Payment.mtxtRemarks1.value = "";	     	

	document.frm_Memo_Of_Payment.onsubmit.disabled = false;
	document.frm_Memo_Of_Payment.ondelete.disabled = true;
	document.frm_Memo_Of_Payment.onupdate.disabled = true;

	
	document.frm_Memo_Of_Payment.txtAcc_HeadCode.readOnly="false";
	document.frm_Memo_Of_Payment.txtPayeeType.readOnly="false";
	document.frm_Memo_Of_Payment.txtPayeeCode.readOnly="false";
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