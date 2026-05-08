//			Bills_Token_Register_without_SP_List			//

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

			if (command == "loadGrid") {
				// alert("manipulate");
				loadGrid1(baseResponse);
			}
		}
	}
}

function initialLoad(path) {
	// alert(path);
	
	  var unitid=document.FrmBillTokenRegisterEntry_WithoutProceeding_List.unit_id.value;
      var officeid=document.FrmBillTokenRegisterEntry_WithoutProceeding_List.office_id.value;
      //alert("unitid "+unitid+" officeid  "+officeid);
	var url = path
			+ "/Bills_Token_Register_without_SP?command=loadGrid&unitid1="+unitid+"&officeid1="+officeid;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}

function loadGrid1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var tbody = document.getElementById("tblList");
		var table = document.getElementById("Existing");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		var len = baseResponse.getElementsByTagName("BillNo").length;
		//alert(len);
		for ( var k = 0; k < len; k++) {
			var AccountingUnit = baseResponse
					.getElementsByTagName("AccUnitCode")[k].firstChild.nodeValue;
			var AccountingForOffice = baseResponse
					.getElementsByTagName("AccForOfficeCode")[k].firstChild.nodeValue;
			var BillMajorType = baseResponse
					.getElementsByTagName("BillMajorType")[k].firstChild.nodeValue;
			var billMinorTypeCode = baseResponse
					.getElementsByTagName("billMinorTypeCode")[k].firstChild.nodeValue;
			var billSubTypeCode = baseResponse
					.getElementsByTagName("billSubTypeCode")[k].firstChild.nodeValue;
			var BillNo = baseResponse.getElementsByTagName("BillNo")[k].firstChild.nodeValue;
			var billDate = baseResponse.getElementsByTagName("billDate")[k].firstChild.nodeValue;			
			var ManualProceedingNo = baseResponse.getElementsByTagName("ManualProceedingNo")[k].firstChild.nodeValue;
			var ManualProceedingDatee = baseResponse.getElementsByTagName("ManualProceedingDatee")[k].firstChild.nodeValue;			
			var InvoiceReceivedDate = baseResponse
					.getElementsByTagName("InvoiceReceivedDate")[k].firstChild.nodeValue;
			var InvoiceReceivedDatee;
			if((InvoiceReceivedDate==null)||(InvoiceReceivedDate=="")||(InvoiceReceivedDate=="-")){
				InvoiceReceivedDatee="-";
			}else{
				InvoiceReceivedDatee=InvoiceReceivedDate;
			}
			var NoOfInvoies = baseResponse.getElementsByTagName("NoOfInvoies")[k].firstChild.nodeValue;
			var MTC70Required = baseResponse.getElementsByTagName("MTC70Required")[k].firstChild.nodeValue;
			var TotalSanctionAmount = baseResponse.getElementsByTagName("TotalSanctionAmount")[k].firstChild.nodeValue;
			var TotalBillAmount = baseResponse.getElementsByTagName("TotalBillAmount")[k].firstChild.nodeValue;
			var TotalDeductedAmount = baseResponse
			.getElementsByTagName("TotalDeductedAmount")[k].firstChild.nodeValue;
			TotalDeductedAmount = TotalDeductedAmount+"0";
			var AccHeadCode = baseResponse.getElementsByTagName("AccHeadCode")[k].firstChild.nodeValue;
			var PayeeType = baseResponse.getElementsByTagName("PayeeType")[k].firstChild.nodeValue;
			var PayeeCode = baseResponse.getElementsByTagName("PayeeCode")[k].firstChild.nodeValue;
			var Payeedesc = baseResponse.getElementsByTagName("paydesc")[k].firstChild.nodeValue;
			var BillProcessingDoneBy = baseResponse
					.getElementsByTagName("BillProcessingDoneBy")[k].firstChild.nodeValue;
			var RefNo = baseResponse.getElementsByTagName("RefNo")[k].firstChild.nodeValue;
			var RefDate = baseResponse.getElementsByTagName("RefDate")[k].firstChild.nodeValue;
			var Remarks = baseResponse.getElementsByTagName("Remarks")[k].firstChild.nodeValue;
			
			var bill_minor_type_desc1 = baseResponse.getElementsByTagName("bill_minor_type_desc1")[k].firstChild.nodeValue;
			var bill_major_type_desc = baseResponse.getElementsByTagName("bill_major_type_desc")[k].firstChild.nodeValue;
			var sub_type_desc = baseResponse.getElementsByTagName("sub_type_desc")[k].firstChild.nodeValue;
			

			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");
			
			mycurrent_row.id = BillNo+BillMajorType;
			
			var cell = document.createElement("TD");
			var anc = document.createElement("A");
			var url = "javascript:loadValuesFromTable('" + BillNo + "','" + BillMajorType + "')";

			anc.href = url;
			var txtedit = document.createTextNode("Edit");
			anc.appendChild(txtedit);
			cell.appendChild(anc);
			mycurrent_row.appendChild(cell);

			var cell1 = document.createElement("TD");
			cell1.style.display="none";
			var tnodeAccountingUnit = document.createTextNode(AccountingUnit);
			tnodeAccountingUnit.type="hidden";
			cell1.appendChild(tnodeAccountingUnit);
			mycurrent_row.appendChild(cell1);

			var cell2 = document.createElement("TD");
			cell2.style.display="none";
			var tnodeAccountingForOffice = document.createTextNode(AccountingForOffice);
			tnodeAccountingForOffice.type="hidden";
			cell2.appendChild(tnodeAccountingForOffice);
			mycurrent_row.appendChild(cell2);
			
			
			// --------------------------------------------------------
			/*var cell4 = document.createElement("TD");
			var BillMajorType = document.createTextNode(BillMajorType);
			cell4.appendChild(BillMajorType);
			mycurrent_row.appendChild(cell4);

			var cell5 = document.createElement("TD");
			var billMinorTypeCode = document.createTextNode(billMinorTypeCode);
			cell5.appendChild(billMinorTypeCode);
			mycurrent_row.appendChild(cell5);

			var cell6 = document.createElement("TD");
			var billSubTypeCode = document.createTextNode(billSubTypeCode);
			cell6.appendChild(billSubTypeCode);
			mycurrent_row.appendChild(cell6);
*/
			
			var cell3 = document.createElement("TD");
			var BillMajorType1 = document.createTextNode(bill_major_type_desc);
			BillMajorType1.size=6;
			cell3.appendChild(BillMajorType1);
			var billMinorTypeCode1 = document.createTextNode(' - ');
			billMinorTypeCode1.size=6;
			cell3.appendChild(billMinorTypeCode1);
			var billMinorTypeCode1 = document.createTextNode(bill_minor_type_desc1);
			billMinorTypeCode1.size=6;
			cell3.appendChild(billMinorTypeCode1);
			mycurrent_row.appendChild(cell3);
			
			var cell4 = document.createElement("TD");
			var billSubTypeCode1 = document.createTextNode(sub_type_desc);
			billSubTypeCode1.size=6;
			cell4.appendChild(billSubTypeCode1);
			mycurrent_row.appendChild(cell4);
			
			
			var cell5 = document.createElement("TD");
			var BillNo1 = document.createTextNode(BillNo);
			BillNo1.size=6;
			cell5.appendChild(BillNo1);
			var billMinorTypeCode1 = document.createTextNode(' - ');
			billMinorTypeCode1.size=6;
			cell5.appendChild(billMinorTypeCode1);
			var billDate1 = document.createTextNode(billDate);
			billDate1.size=6;
			cell5.appendChild(billDate1);
			mycurrent_row.appendChild(cell5);

			var cell6 = document.createElement("TD");
			var ManualProceedingNo1 = document.createTextNode(ManualProceedingNo);
			ManualProceedingNo1.size=6;
			cell6.appendChild(ManualProceedingNo1);
			var billMinorTypeCode1 = document.createTextNode(' - ');
			billMinorTypeCode1.size=6;
			cell6.appendChild(billMinorTypeCode1);
			var ManualProceedingDatee1 = document.createTextNode(ManualProceedingDatee);
			ManualProceedingDatee1.size=6;
			cell6.appendChild(ManualProceedingDatee1);
			mycurrent_row.appendChild(cell6);
			
			var cell7 = document.createElement("TD");			
			var InvoiceReceivedDate1 = document.createTextNode(InvoiceReceivedDatee);
			InvoiceReceivedDate1.size=6;
			cell7.appendChild(InvoiceReceivedDate1);
			
			var billMinorTypeCode1 = document.createTextNode(' - ');
			billMinorTypeCode1.size=6;
			cell7.appendChild(billMinorTypeCode1);
			var NoOfInvoies1 = document.createTextNode(NoOfInvoies);
			NoOfInvoies1.size=6;
			cell7.appendChild(NoOfInvoies1);
			mycurrent_row.appendChild(cell7);

			var cel8 = document.createElement("TD");
			var TotalSanctionAmount = document.createTextNode(TotalSanctionAmount);
			TotalSanctionAmount.size=7;
			cel8.appendChild(TotalSanctionAmount);
			mycurrent_row.appendChild(cel8);
			
			var cel8 = document.createElement("TD");
			var TotalBillAmount = document.createTextNode(TotalBillAmount);
			TotalBillAmount.size=7;
			cel8.appendChild(TotalBillAmount);
			mycurrent_row.appendChild(cel8);
			
			var cel9= document.createElement("TD");
			var TotalDeductedAmount = document.createTextNode(TotalDeductedAmount);
			TotalDeductedAmount.size=6;
			cel9.appendChild(TotalDeductedAmount);
			mycurrent_row.appendChild(cel9);						

			var cel10 = document.createElement("TD");
			var AccHeadCode = document.createTextNode(AccHeadCode);
			AccHeadCode.size=6;
			cel10.appendChild(AccHeadCode);
			mycurrent_row.appendChild(cel10);
			
			var cell11 = document.createElement("TD");
			var PayeeType1 = document.createTextNode(PayeeType);
			PayeeType1.size=6;
			cell11.appendChild(PayeeType1);
			var billMinorTypeCode1 = document.createTextNode(' - ');
			billMinorTypeCode1.size=6;
			cell11.appendChild(billMinorTypeCode1);
			var PayeeCode1 = document.createTextNode(PayeeCode);
			PayeeCode1.size=6;
			cell11.appendChild(PayeeCode1);
			mycurrent_row.appendChild(cell11);
			
			
			/*
			var cell4 = document.createElement("TD");
			cell4.style.display="none";
			var BillMajorType1 = document.createTextNode(bill_major_type_desc);
			BillMajorType1.size=6;
			BillMajorType1.type="hidden";
			cell4.appendChild(BillMajorType1);
			mycurrent_row.appendChild(cell4);

			var cell5 = document.createElement("TD");
			cell5.style.display="none";
			var billMinorTypeCode1 = document.createTextNode(bill_minor_type_desc1);
			billMinorTypeCode1.type="hidden";
			billMinorTypeCode1.size=6;
			cell5.appendChild(billMinorTypeCode1);
			mycurrent_row.appendChild(cell5);*/

			
			
			/*var cell12 = document.createElement("TD");
			cell12.style.display="none";
			var BillNo = document.createTextNode(BillNo);
			BillNo.type="hidden";
			BillNo.size=6;
			cell12.appendChild(BillNo);
			mycurrent_row.appendChild(cell12);

			*/

			var cell12 = document.createElement("TD");
   			cell12.style.display="none";
   			var officeid2 = document.createTextNode(BillNo);
   			officeid2.size=6;
   			officeid2.type="hidden";
   			cell12.appendChild(officeid2);
   			mycurrent_row.appendChild(cell12);

			
			var cell13 = document.createElement("TD");
			cell13.style.display="none";
			var billDate = document.createTextNode(billDate);
			billDate.size=6;
			billDate.type="hidden";
			cell13.appendChild(billDate);
			mycurrent_row.appendChild(cell13);
			
			var cell14 = document.createElement("TD");
			cell14.style.display="none";
			var ManualProceedingNo = document.createTextNode(ManualProceedingNo);
			ManualProceedingNo.size=6;
			ManualProceedingNo.type="hidden";
			cell14.appendChild(ManualProceedingNo);
			mycurrent_row.appendChild(cell14);

			var cell15 = document.createElement("TD");
			cell15.style.display="none";
			var ManualProceedingDatee = document.createTextNode(ManualProceedingDatee);
			ManualProceedingDatee.size=6;
			ManualProceedingDatee.type="hidden";
			cell15.appendChild(ManualProceedingDatee);
			mycurrent_row.appendChild(cell15);

			var cell16 = document.createElement("TD");
			cell16.style.display="none";
			var InvoiceReceivedDate = document.createTextNode(InvoiceReceivedDate);
			InvoiceReceivedDate.size=6;
			InvoiceReceivedDate.type="hidden";
			cell16.appendChild(InvoiceReceivedDate);
			mycurrent_row.appendChild(cell16);

			var cel17 = document.createElement("TD");
			cel17.style.display="none";
			var NoOfInvoies = document.createTextNode(NoOfInvoies);
			NoOfInvoies.size=6;
			NoOfInvoies.type="hidden";
			cel17.appendChild(NoOfInvoies);
			mycurrent_row.appendChild(cel17);

			var cel18 = document.createElement("TD");
			cel18.style.display="none";
			var MTC70Required = document.createTextNode(MTC70Required);
			MTC70Required.size=6;
			MTC70Required.type="hidden";
			cel18.appendChild(MTC70Required);
			mycurrent_row.appendChild(cel18);

			var cel19 = document.createElement("TD");
			cel19.style.display="none";
			var PayeeType1 = document.createTextNode(PayeeType);
			PayeeType1.size=6;
			PayeeType1.type="hidden";
			cel19.appendChild(PayeeType1);
			mycurrent_row.appendChild(cel19);

			var cel20 = document.createElement("TD");
			cel20.style.display="none";
			var PayeeCode = document.createTextNode(PayeeCode);
			PayeeCode.size=6;
			PayeeCode.type="hidden";
			cel20.appendChild(PayeeCode);
			mycurrent_row.appendChild(cel20);

			var cel21 = document.createElement("TD");
			cel21.style.display="none";
			var BillProcessingDoneBy = document.createTextNode(BillProcessingDoneBy);
			BillProcessingDoneBy.size=6;
			BillProcessingDoneBy.type="hidden";
			cel21.appendChild(BillProcessingDoneBy);
			mycurrent_row.appendChild(cel21);

			/*var cel26 = document.createElement("TD");
			var RefNo = document.createTextNode(RefNo);
			cel26.appendChild(RefNo);
			mycurrent_row.appendChild(cel26);

			var cel27 = document.createElement("TD");
			var RefDate = document.createTextNode(RefDate);
			cel27.appendChild(RefDate);
			mycurrent_row.appendChild(cel27);*/
			
			
			var cel22 = document.createElement("TD");
			cel22.style.display="none";
			var RefNo = document.createTextNode(RefNo);
			RefNo.type="hidden";
			cel22.appendChild(RefNo);
			mycurrent_row.appendChild(cel22);

			var cel23 = document.createElement("TD");
			cel23.style.display="none";
			var RefDate = document.createTextNode(RefDate);
			RefDate.type="hidden";
			cel23.appendChild(RefDate);
			mycurrent_row.appendChild(cel23);

			var cel24 = document.createElement("TD");
			var Remarks = document.createTextNode(Remarks);
			cel24.appendChild(Remarks);
			mycurrent_row.appendChild(cel24);
			
			var cell25 = document.createElement("TD");
   			cell25.style.display="none";
   			var officeid2 = document.createTextNode(BillMajorType);
   			officeid2.size=6;
   			officeid2.type="hidden";
   			cell25.appendChild(officeid2);
   			mycurrent_row.appendChild(cell25);

   			
   			var cell26 = document.createElement("TD");
   			cell26.style.display="none";
   			var officeid21 = document.createTextNode(billMinorTypeCode);
   			officeid21.size=6;
   			officeid21.type="hidden";
   			cell26.appendChild(officeid21);
   			mycurrent_row.appendChild(cell26);

   			
   			var cell27 = document.createElement("TD");
   			cell27.style.display="none";
   			var officeid23 = document.createTextNode(billSubTypeCode);
   			officeid23.size=6;
   			officeid23.type="hidden";
   			cell27.appendChild(officeid23);
   			mycurrent_row.appendChild(cell27);

			tbody.appendChild(mycurrent_row);
		}

	}else if(flag == "NoData")
		{
		alert("Record Does Not Exist");
		}else {
		alert("Fail to Load");
	}

}

function loadValuesFromTable(idd,idd1) {

	r = document.getElementById(idd+idd1);
	rcells = r.cells;
	var AccountingUnit = rcells.item(1).firstChild.nodeValue;
	var AccountingForOffice = rcells.item(2).firstChild.nodeValue;
	var Ac = rcells.item(3).firstChild.nodeValue;
	var BillMajorType = rcells.item(26).firstChild.nodeValue;
	var billMinorTypeCode = rcells.item(27).firstChild.nodeValue;
	var billSubTypeCode = rcells.item(28).firstChild.nodeValue;
	var BillNo = rcells.item(13).firstChild.nodeValue;
	var billDate = rcells.item(14).firstChild.nodeValue;
	var ManualProceedingNo = rcells.item(15).firstChild.nodeValue;
	var ManualProceedingDate = rcells.item(16).firstChild.nodeValue;
	var InvoiceReceivedDate = rcells.item(17).firstChild.nodeValue;
	var NoOfInvoies = rcells.item(18).firstChild.nodeValue;
	var MTC70Required = rcells.item(19).firstChild.nodeValue;
	var TotalSanctionAmount = rcells.item(8).firstChild.nodeValue;
	var TotalBillAmount = rcells.item(9).firstChild.nodeValue;
	var TotalDeductedAmount = rcells.item(10).firstChild.nodeValue;
	var AccHeadCode = rcells.item(11).firstChild.nodeValue;
	var PayeeType = rcells.item(20).firstChild.nodeValue;
	var PayeeCode = rcells.item(21).firstChild.nodeValue;
	var BillProcessingDoneBy = rcells.item(22).firstChild.nodeValue;
	var RefNo = rcells.item(23).firstChild.nodeValue;
	var RefDate = rcells.item(24).firstChild.nodeValue;
	var Remarks = rcells.item(25).firstChild.nodeValue;	
	exitfun();
	if((InvoiceReceivedDate=="-")||(InvoiceReceivedDate=="")){
		InvoiceReceivedDate="";
	}
	//alert("acc "+Ac+" "+AccountingUnit+" AccountingForOffice ="+AccountingForOffice+" BillMajorType == "+BillMajorType+" billMinorTypeCode "+
		//	billMinorTypeCode+" billSubTypeCode== "+ billSubTypeCode+" BillNo== "+BillNo+"billDate == "+billDate+" InvoiceReceivedDate=="+
		//	InvoiceReceivedDate+" NoOfInvoies == "+NoOfInvoies+" TotalBillAmount== "+TotalBillAmount+" AccHeadCode== "+AccHeadCode+""+
		//	PayeeType+"PayeeCode "+ PayeeCode+""+ BillProcessingDoneBy+""+ RefNo+""+ RefDate+""+
		//	Remarks+""+ ManualProceedingNo+""+ ManualProceedingDate+""+ MTC70Required+""+TotalDeductedAmount);
	
	opener.ParentDrawing(AccountingUnit,AccountingForOffice,BillMajorType,
			billMinorTypeCode, billSubTypeCode, BillNo, billDate,
			InvoiceReceivedDate, NoOfInvoies,TotalSanctionAmount, TotalBillAmount, AccHeadCode,
			PayeeType, PayeeCode, BillProcessingDoneBy, RefNo, RefDate,
			Remarks, ManualProceedingNo, ManualProceedingDate, MTC70Required,TotalDeductedAmount);

}
function Minimize() {
	window.resizeTo(0, 0);
	window.screenX = screen.width;
	window.screenY = screen.height;
	opener.window.focus();
}

function exitfun() {
	window.close();
}