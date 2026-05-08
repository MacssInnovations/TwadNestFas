//			Bills_Token_Register_with_SP_List			//

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

function initialLoad(path,unit) {
	// alert(unit);
	var url = path+ "/Bills_Token_Register_with_SP?command=loadGrid&unit="+unit;
	// alert(url);
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
			var ProceedingNo = baseResponse.getElementsByTagName("SanctionProceedingNo")[k].firstChild.nodeValue;
			var ProceedingDatee = baseResponse.getElementsByTagName("SanctionProceedingDatee")[k].firstChild.nodeValue;			
			
			var MTC70Required = baseResponse.getElementsByTagName("MTC70Required")[k].firstChild.nodeValue;
			var TotalBillAmount = baseResponse
					.getElementsByTagName("TotalBillAmount")[k].firstChild.nodeValue;
			var TotalSanctionedAmount = baseResponse
			.getElementsByTagName("TotalSanctionedAmount")[k].firstChild.nodeValue;
			//TotalDeductedAmount = TotalDeductedAmount+"0";
			var AccHeadCode = baseResponse.getElementsByTagName("AccHeadCode")[k].firstChild.nodeValue;
			var PayeeType = baseResponse.getElementsByTagName("PayeeType")[k].firstChild.nodeValue;
			var PayeeCode = baseResponse.getElementsByTagName("PayeeCode")[k].firstChild.nodeValue;
                        
            var PayableTo = baseResponse.getElementsByTagName("PayableTo")[k].firstChild.nodeValue;
            var BudgetProvision = baseResponse.getElementsByTagName("BudgetProvision")[k].firstChild.nodeValue;
			var BudgetSofarSpent = baseResponse.getElementsByTagName("BudgetSofarSpent")[k].firstChild.nodeValue;
                        
            var BillProcessingDoneBy = baseResponse.getElementsByTagName("BillProcessingDoneBy")[k].firstChild.nodeValue;
			var RefNo = baseResponse.getElementsByTagName("RefNo")[k].firstChild.nodeValue;
			var RefDate = baseResponse.getElementsByTagName("RefDate")[k].firstChild.nodeValue;
			var Remarks = baseResponse.getElementsByTagName("Remarks")[k].firstChild.nodeValue;

			var major_desc = baseResponse.getElementsByTagName("major_desc")[k].firstChild.nodeValue;
			var minor_desc = baseResponse.getElementsByTagName("minor_desc")[k].firstChild.nodeValue;
			var sub_desc = baseResponse.getElementsByTagName("sub_desc")[k].firstChild.nodeValue;
			var proc_id = baseResponse.getElementsByTagName("proc_id")[k].firstChild.nodeValue;
			
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

		/*	var cell2 = document.createElement("TD");
			var tnodeAccountingUnit = document.createTextNode(AccountingUnit);
			cell2.appendChild(tnodeAccountingUnit);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var tnodeAccountingForOffice = document
					.createTextNode(AccountingForOffice);
			cell3.appendChild(tnodeAccountingForOffice);
			mycurrent_row.appendChild(cell3);  */
			// --------------------------------------------------------
			
			 var cell2=document.createElement("TD");
	           
             var H_code=document.createElement("input");
             H_code.type="hidden";
             H_code.name="BillMajorType";
             H_code.value=BillMajorType+"-"+billMinorTypeCode+"-"+billSubTypeCode;
             cell2.appendChild(H_code);
             var currentText=document.createTextNode(major_desc+"/"+minor_desc+"/"+sub_desc);
             cell2.appendChild(currentText);
              mycurrent_row.appendChild(cell2);
			
		/*	var cell4 = document.createElement("TD");
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
			mycurrent_row.appendChild(cell6);  */

			var cell7 = document.createElement("TD");
			var BillNo = document.createTextNode(BillNo);
			cell7.appendChild(BillNo);
			mycurrent_row.appendChild(cell7);

			var cell8 = document.createElement("TD");
			var billDate = document.createTextNode(billDate);
			cell8.appendChild(billDate);
			mycurrent_row.appendChild(cell8);
			var cell2=document.createElement("TD");
	           
			var ceeee=document.createElement("TD");
			var Proceedingno=document.createElement("input");
			Proceedingno.type="hidden";
			Proceedingno.name="Proceeding_No";
			Proceedingno.value=ProceedingNo;
			ceeee.appendChild(Proceedingno);
            var currentText=document.createTextNode(proc_id);
            ceeee.appendChild(currentText);
             mycurrent_row.appendChild(ceeee);
			
			

			var cell88 = document.createElement("TD");
			var ProceedingDatee = document.createTextNode(ProceedingDatee);
			cell88.appendChild(ProceedingDatee);
			mycurrent_row.appendChild(cell88);   
                        
            var cel23 = document.createElement("TD");
            if(PayeeType==4)
            {
            	payee="Employee";
            }
			var PayeeType = document.createTextNode(payee);
			cel23.appendChild(PayeeType);
			mycurrent_row.appendChild(cel23);

			var cel24 = document.createElement("TD");
			var PayeeCode = document.createTextNode(PayeeCode);
			cel24.appendChild(PayeeCode);
			mycurrent_row.appendChild(cel24);

			var cel211 = document.createElement("TD");
			var TotalSanctionedAmount = document.createTextNode(TotalSanctionedAmount);
			cel211.appendChild(TotalSanctionedAmount);
			mycurrent_row.appendChild(cel211);
                        
                        var cel21 = document.createElement("TD");
			var TotalBillAmount = document.createTextNode(TotalBillAmount);
			cel21.appendChild(TotalBillAmount);
			mycurrent_row.appendChild(cel21);
                        
                        var cell124 = document.createElement("TD");
			var PayableTo = document.createTextNode(PayableTo);
			cell124.appendChild(PayableTo);
			mycurrent_row.appendChild(cell124);
                        
                        var cel25 = document.createElement("TD");
			var BillProcessingDoneBy = document
					.createTextNode(BillProcessingDoneBy);
			cel25.appendChild(BillProcessingDoneBy);
			mycurrent_row.appendChild(cel25);
                        
                        var cel200 = document.createElement("TD");
			var MTC70Required = document.createTextNode(MTC70Required);
			cel200.appendChild(MTC70Required);
			mycurrent_row.appendChild(cel200);
                        
                        var cel22 = document.createElement("TD");
			var AccHeadCode = document.createTextNode(AccHeadCode);
			cel22.appendChild(AccHeadCode);
			mycurrent_row.appendChild(cel22);

                        var cell125 = document.createElement("TD");
			var BudgetProvision = document.createTextNode(BudgetProvision);
			cell125.appendChild(BudgetProvision);
			mycurrent_row.appendChild(cell125);
                        
                        var cell126 = document.createElement("TD");
			var BudgetSofarSpent = document.createTextNode(BudgetSofarSpent);
			cell126.appendChild(BudgetSofarSpent);
			mycurrent_row.appendChild(cell126);
                        
			var cel26 = document.createElement("TD");
			var RefNo = document.createTextNode(RefNo);
			cel26.appendChild(RefNo);
			mycurrent_row.appendChild(cel26);

			var cel27 = document.createElement("TD");
			var RefDate = document.createTextNode(RefDate);
			cel27.appendChild(RefDate);
			mycurrent_row.appendChild(cel27);

			var cel28 = document.createElement("TD");
			var Remarks = document.createTextNode(Remarks);
			cel28.appendChild(Remarks);
			mycurrent_row.appendChild(cel28);
			
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
	var BillMajorType = rcells.item(3).firstChild.nodeValue;
	var billMinorTypeCode = rcells.item(4).firstChild.nodeValue;
	var billSubTypeCode = rcells.item(5).firstChild.nodeValue;
	var BillNo = rcells.item(6).firstChild.nodeValue;
	var billDate = rcells.item(7).firstChild.nodeValue;
	var ProceedingNo = rcells.item(8).firstChild.nodeValue;
	var ProceedingDate = rcells.item(9).firstChild.nodeValue;
	var PayeeType = rcells.item(10).firstChild.nodeValue;
	var PayeeCode = rcells.item(11).firstChild.nodeValue;
	var TotalSanctionedAmount = rcells.item(12).firstChild.nodeValue;
        var TotalBillAmount = rcells.item(13).firstChild.nodeValue;
        var PayableTo = rcells.item(14).firstChild.nodeValue;
        var BillProcessingDoneBy = rcells.item(15).firstChild.nodeValue;
        var MTC70Required = rcells.item(16).firstChild.nodeValue;	
	var AccHeadCode = rcells.item(17).firstChild.nodeValue;
	var BudgetProvided = rcells.item(18).firstChild.nodeValue;	
	var BudgetSoSpent = rcells.item(19).firstChild.nodeValue;
        var RefNo = rcells.item(20).firstChild.nodeValue;
	var RefDate = rcells.item(21).firstChild.nodeValue;
	var Remarks = rcells.item(22).firstChild.nodeValue;	
	exitfun();
	
	opener.ParentDrawing(AccountingUnit, AccountingForOffice, BillMajorType,
			billMinorTypeCode, billSubTypeCode, BillNo, billDate,
			ProceedingNo, ProceedingDate, PayeeType, PayeeCode,
			TotalSanctionedAmount, TotalBillAmount, PayableTo, BillProcessingDoneBy, MTC70Required,
			AccHeadCode, BudgetProvided, BudgetSoSpent, RefNo,RefDate,Remarks);

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