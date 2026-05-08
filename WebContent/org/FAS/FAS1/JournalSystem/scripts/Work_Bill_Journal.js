//Work_Bill_Journal		//
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
			//alert('baseResponse '+baseResponse);
			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			// alert("manipulate-command:--->>>"+command);

			if (command == "gettGrid") {
				loadGrid1(baseResponse);
			} else if (command == "getVoucherDetails") {
				getVoucherDetails1(baseResponse);
			}
		}
	}
}

function check_withinCB()
{
	//alert("WELCOME!....");
	var mon ="";
		var jrnl_date = document.getElementById("txtCrea_date").value;
		//alert("jrnl_date--->"+jrnl_date);
		var CB_Year = document.frmWork_Bill_Journal.cboCashBook_Year.value;
		//alert("CB_Year--->"+CB_Year);
		var CB_month = document.frmWork_Bill_Journal.cboCashBook_Month.value;
		//alert("CB_month--->"+CB_month);
		var jrdate = jrnl_date.split('/');
		var entered_month = jrdate[1];
		var enteedr_year = jrdate[2];
		if(CB_month<10)
			{
		 mon = "0"+CB_month;
			}
		else{
			mon=CB_month;
		}
		//alert(entered_month +" ==  "+ mon+" == "+enteedr_year +" ==" + CB_Year)
		if((entered_month == mon) && (enteedr_year == CB_Year))
			{
			
			}
		else
			{
			
			alert("select the Journal Date with in the Cash Book Year and Month");
			document.getElementById("txtCrea_date").value="";
			document.getElementById("butSub").disabled=true;
			
			}
}




function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
    if(blr_flag==1)             // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
    {
           //  call_clr();
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
             var TB_date=fromcal_dateCtrl.value;
             //alert(fromcal_dateCtrl.value+"b4url")
//             alert("TB_date===>"+TB_date);
             
             var Tb_dt=TB_date.split("/");
//             alert(Tb_dt[2]);
//             alert(Tb_dt[1]);
             
             
             if(Tb_dt[2]>=2018)
            	 {
            	 if(Tb_dt[1]>=11)
            		 {
            		 document.getElementById("butSub").disabled=false;
            		 }
            	 else
            		 {
            		 document.getElementById("butSub").disabled=true;
            		 }
            	 }
             else
            	 {
            	 document.getElementById("butSub").disabled=true;
            	 }
             
             
//             if(Tb_dt[2]>=2018)          
//            	 {
//            	 if(Tb_dt[1]>=11)
//            	 {
////            	 alert("inside TB_date");
//            	 document.getElementById("butSub").disabled=false;
//            	 }
//            	 else
//            		 {
//            		 document.getElementById("butSub").disabled=false;
//            		 }
//            	 }
//             else if(Tb_dt[2]<=2018)
//            	 {
//            	 if(Tb_dt[1]<11)
//            		 {
////            	alert("else part inside!......");
//            	 document.getElementById("butSub").disabled=true;
//            		 }
//            	 }
             
             
             if(fromcal_dateCtrl.value.length!=0)
             {
                 var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                        //alert(url);
                 var req=getTransport();
                 req.open("GET",url,true); 
                 req.onreadystatechange=function()
                 {
                   check_TB(req,fromcal_dateCtrl);
                 }  ; 
                 req.send(null);
            }
           
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
                 //doFunction('load_Receipt_No','null');  
            	check_withinCB();//return true;
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
                    dateCtrl.focus();
                    //document.getElementById("txtReceipt_No").value="";
               }
             else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");//return false;//
                    dateCtrl.focus();
                   // document.getElementById("txtReceipt_No").value="";     
               }
            dateCheck(dateCtrl);  
        }
    }
}


function loadGrid(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value
	var cmbOffice_code = document.getElementById("cmbOffice_code").value
	var txtCB_Year = document.getElementById("cboCashBook_Year").value;
	var txtCB_Month = document.getElementById("cboCashBook_Month").value;
	if (txtCB_Year == "") {
		alert("Enter Cashbook Year in the Field");
	} else if (txtCB_Month == "") {
		alert("Enter Cashbook Month in the Field");
	} else {
		var url = path + "/Work_Bill_Journal?command=gettGrid&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month;
	}
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}

function loadGrid1(baseResponse) {
	var seq = 0;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		/** Delete Existing Values from Grid */
		var tbody = document.getElementById("grid_body");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		var len6 = baseResponse.getElementsByTagName("JournalVoucherNo").length;
		// alert(len6);
		if (len6 != 0) {
			for ( var k = 0; k <= len6; k++) {
				//alert("kk=== "+k);
				var JournalVoucherNo1 = baseResponse
						.getElementsByTagName("JournalVoucherNo")[k].firstChild.nodeValue;
				var SubLedgerCode1 = baseResponse
						.getElementsByTagName("SubLedgerCode")[k].firstChild.nodeValue;
                                var SubLedgerCodeDesc = baseResponse
						.getElementsByTagName("SubLedgerCodeDesc")[k].firstChild.nodeValue;
                var Billdate1 = baseResponse.getElementsByTagName("Billdate")[k].firstChild.nodeValue;                
				var Remarks1 = baseResponse.getElementsByTagName("Remarks")[k].firstChild.nodeValue;
				if (Remarks1 == 'null') {
					Remarks1 = "";
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
				cell.appendChild(anc);
				cell.style.textAlign = "center";
				mycurrent_row.appendChild(cell);
				
	
				/** JournalVoucherNo */
				var cell4 = document.createElement("TD");
				var JournalVoucherNo = document.createElement("input");
				JournalVoucherNo.type = "hidden";
				JournalVoucherNo.name = "JournalVoucherNo" + seq;
				JournalVoucherNo.id = "JournalVoucherNo" + seq;
				JournalVoucherNo.value = JournalVoucherNo1;
				cell4.appendChild(JournalVoucherNo);
				cell4.style.textAlign = "center";
				var currentText = document.createTextNode(JournalVoucherNo1);
				cell4.appendChild(currentText);
				mycurrent_row.appendChild(cell4);

				/** Bill Date */
				var cell2 = document.createElement("TD");
				var Billdate = document.createElement("input");
				Billdate.type = "hidden";
				Billdate.name = "Billdate" + seq;
				Billdate.id = "Billdate" + seq;
				Billdate.value = Billdate1;
				cell2.appendChild(Billdate);
				cell2.style.textAlign = "center";
				var currentText = document.createTextNode(Billdate1);
				cell2.appendChild(currentText);
				mycurrent_row.appendChild(cell2);
				
				/** SubLedgerCode */
				var cell5 = document.createElement("TD");
				var SubLedgerCode = document.createElement("input");
				SubLedgerCode.type = "hidden";
				SubLedgerCode.name = "SubLedgerCode" + seq;
				SubLedgerCode.id = "SubLedgerCode" + seq;
				SubLedgerCode.value = SubLedgerCode1;
				cell5.appendChild(SubLedgerCode);
				cell5.style.textAlign = "center";
				var currentText = document.createTextNode(SubLedgerCodeDesc);
				cell5.appendChild(currentText);
				mycurrent_row.appendChild(cell5);

				/** Remarks1 */
				var cell2 = document.createElement("TD");
				var Remarks = document.createElement('TEXTAREA', 'option1');
				Remarks.name = "Remarks" + seq;
				Remarks.id = "Remarks" + seq;
				Remarks.value = Remarks1;
				Remarks.readOnly = true;
				Remarks.setAttribute("cols", "5");
				Remarks.style.height = "60px"
				Remarks.style.width = "350px";
				cell2.appendChild(Remarks);
				mycurrent_row.appendChild(cell2);

				var cell6 = document.createElement("TD");
				var anc = document.createElement("A");
				var url = "javascript:list('" + JournalVoucherNo1 + "')";

				anc.href = url;
				var txtedit = document.createTextNode("DETAILS");
				anc.appendChild(txtedit);
				cell6.appendChild(anc);
				cell6.style.textAlign = "center";
				mycurrent_row.appendChild(cell6);

				tbody.appendChild(mycurrent_row);
				seq = seq + 1;
				document.getElementById("RecordCount").value = seq;
			}
		} else {
			alert("Record Does Not Exist");
			document.getElementById("butSub").disabled=true;
		}
	} else {

		alert("Failed to Load");
	}
}

function list(JournalVoucherNo) {
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("cboCashBook_Year").value;
	var txtCB_Month = document.getElementById("cboCashBook_Month").value;

	winemp = window.open(
			"Work_Bill_Journal_Voucher_Details.jsp?cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month="
					+ txtCB_Month + "&JournalVoucherNo=" + JournalVoucherNo,
			"list",
			"status=1,height=500,width=600,resizable=YES, scrollbars=yes");
	winemp.moveTo(250, 250);
	winemp.focus();
}

function initialLoad(path, cboAcc_UnitCode, cboOffice_code, cboCashBook_Year,
		cboCashBook_Month, JournalVoucherNo) {
	//alert(path);
	var url = path
			+ "/Work_Bill_Journal?command=getVoucherDetails&JournalVoucherNo="
			+ JournalVoucherNo + "&cboOffice_code=" + cboOffice_code
			+ "&cboAcc_UnitCode=" + cboAcc_UnitCode + "&cboCashBook_Year="
			+ cboCashBook_Year + "&cboCashBook_Month=" + cboCashBook_Month;

	 //alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
	
		manipulate(xmlrequest);
	};

	xmlrequest.send(null);

}

function getVoucherDetails1(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var tbody = document.getElementById("tblList");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		var len6 = baseResponse
				.getElementsByTagName("Journal_Voucher_DR_CR_SNO").length;
	
		for ( var k = 0; k < len6; k++) {
			var Journal_Voucher_DR_CR_SNO = baseResponse
					.getElementsByTagName("Journal_Voucher_DR_CR_SNO")[k].firstChild.nodeValue;
			var Acc_Hd_Code = baseResponse.getElementsByTagName("Acc_Hd_Code")[k].firstChild.nodeValue;
			var Sub_Ledger_Type = baseResponse
					.getElementsByTagName("Sub_Ledger_Type_desc")[k].firstChild.nodeValue;
			
			if(Sub_Ledger_Type=="null")Sub_Ledger_Type="";
			
			var Sub_Ledger_Code = baseResponse
					.getElementsByTagName("Sub_Ledger_Code_desc")[k].firstChild.nodeValue;
			if(Sub_Ledger_Code=="null")Sub_Ledger_Code="";
			
			var CR_DR_Indicator = baseResponse
					.getElementsByTagName("CR_DR_Indicator")[k].firstChild.nodeValue;
			var Bill_No = baseResponse.getElementsByTagName("Bill_No")[k].firstChild.nodeValue;
			var Bill_Type = baseResponse.getElementsByTagName("Bill_Type")[k].firstChild.nodeValue;
			var Agreement_No = baseResponse
					.getElementsByTagName("Agreement_No")[k].firstChild.nodeValue;
			var Agreement_Date = baseResponse
					.getElementsByTagName("Agreement_Date")[k].firstChild.nodeValue;
			var Bill_Date = baseResponse.getElementsByTagName("Bill_Date")[k].firstChild.nodeValue;
			var Amount = baseResponse.getElementsByTagName("Amount")[k].firstChild.nodeValue;

			var tbody = document.getElementById("tblList");
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = Journal_Voucher_DR_CR_SNO;

			var cell = document.createElement("TD");
			var Journal_Voucher_DR_CR_SNO = document
					.createTextNode(Journal_Voucher_DR_CR_SNO);
			cell.appendChild(Journal_Voucher_DR_CR_SNO);
			mycurrent_row.appendChild(cell);

			var cell2 = document.createElement("TD");
			var Acc_Hd_Code = document.createTextNode(Acc_Hd_Code);
			cell2.appendChild(Acc_Hd_Code);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var Sub_Ledger_Type = document.createTextNode(Sub_Ledger_Type);
			cell3.appendChild(Sub_Ledger_Type);
			mycurrent_row.appendChild(cell3);

			var cell4 = document.createElement("TD");
			var Sub_Ledger_Code = document.createTextNode(Sub_Ledger_Code);
			cell4.appendChild(Sub_Ledger_Code);
			mycurrent_row.appendChild(cell4);

			var cell5 = document.createElement("TD");
			var CR_DR_Indicator = document.createTextNode(CR_DR_Indicator);
			cell5.appendChild(CR_DR_Indicator);
			mycurrent_row.appendChild(cell5);

			var cell6 = document.createElement("TD");
			var Bill_No = document.createTextNode(Bill_No);
			cell6.appendChild(Bill_No);
			mycurrent_row.appendChild(cell6);

			var cell7 = document.createElement("TD");
			var Bill_Type = document.createTextNode(Bill_Type);
			cell7.appendChild(Bill_Type);
			mycurrent_row.appendChild(cell7);

			var cell8 = document.createElement("TD");
			var Agreement_No = document.createTextNode(Agreement_No);
			cell8.appendChild(Agreement_No);
			mycurrent_row.appendChild(cell8);

			var cell9 = document.createElement("TD");
			var Agreement_Date = document.createTextNode(Agreement_Date);
			cell9.appendChild(Agreement_Date);
			mycurrent_row.appendChild(cell9);

			var cel20 = document.createElement("TD");
			var Bill_Date = document.createTextNode(Bill_Date);
			cel20.appendChild(Bill_Date);
			mycurrent_row.appendChild(cel20);

			var cel21 = document.createElement("TD");
			var Amount = document.createTextNode(Amount);
			cel21.appendChild(Amount);
                        cel21.style.textAlign = "right";
			mycurrent_row.appendChild(cel21);

			tbody.appendChild(mycurrent_row);
		}
	}
}

function checkk() {
	var tbody = document.getElementById("grid_body");
	var rowcount = tbody.rows.length;
	if (rowcount != 0) {
		for ( var i = 0; i < rowcount; i++) {
			if (document.getElementById("check" + i).checked == true) {
				var slno_db1 = document.createElement("input");
				slno_db1.setAttribute("type", "hidden");
				slno_db1.setAttribute("value", i);
				slno_db1.setAttribute("name", "slno_db1" + i);
				slno_db1.setAttribute("id", "slno_db1" + i);
				document.getElementById("frmWork_Bill_Journal").appendChild(
						slno_db1);
			} else {
				var slno_db1 = document.createElement("input");
				slno_db1.setAttribute("type", "hidden");
				slno_db1.setAttribute("value", "-1");
				slno_db1.setAttribute("name", "slno_db1" + i);
				slno_db1.setAttribute("id", "slno_db1" + i);
				document.getElementById("frmWork_Bill_Journal").appendChild(
						slno_db1);
			}
		}
		return true;
	} else {
		alert("No Records Found to Submit...");
		return false;
	}
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

function exit(path) {
	window.close();
}
