//			Civil_Budget_Format_9			//

var seq = 0;
var seq1 = 1;
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
			} else if (command == "get") {
				firstLoad1(baseResponse);
			}else if (command == "get1") {
				firstLoad(baseResponse);
			}  
			else if (command == "LoadData") {
				LoadData_View(baseResponse);
			}
		}
	}
}
function initialLoad1() {	
	var fy = document.getElementById("cmbFinancialYear").value;
	var fy1=fy.split('-');
	var y1 = fy1[0];
	var y2 = "20"+fy1[1];
	
	//document.frmCivil_Budget_Format_9.butView.disabled = false;
	document.frmCivil_Budget_Format_9.butSub.disabled = false;
	document.frmCivil_Budget_Format_9.butDelete.disabled = true;
	document.frmCivil_Budget_Format_9.butUpdate.disabled = true;

	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;

	var url = "../../../../../Civil_Budget_Format_9?command=get&y1=" + y1
			+ "&y2=" + y2 + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode
			+ "&cmbOffice_code=" + cmbOffice_code;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	document.getElementById("imgfld").style.visibility = "visible";
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}
function initialLoad2() {	
	var fy = document.getElementById("cmbFinancialYear").value;
	var fy1=fy.split('-');
	var y1 = fy1[0];
	var y2 = "20"+fy1[1];
	
	//document.frmCivil_Budget_Format_9.butView.disabled = false;
	document.frmCivil_Budget_Format_9.butSub.disabled = true;
	document.frmCivil_Budget_Format_9.butDelete.disabled = true;
	document.frmCivil_Budget_Format_9.butUpdate.disabled = true;
	document.frmCivil_Budget_Format_9.butCan.disabled = true;
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;

	var url = "../../../../../Civil_Budget_Format_9?command=get1&y1=" + y1
			+ "&y2=" + y2 + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode
			+ "&cmbOffice_code=" + cmbOffice_code;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	document.getElementById("imgfld").style.visibility = "visible";
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}
function initialLoad() {
	/** Delete Existing Values from Grid */
	var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	var year1 = 0;
	var financialyear0 = 0;
	var financialyear = 0;
	var fin1=0;
	var fin2=0;
	/*var financialyear1 = 0;
	var financialyear2 = 0;*/
	if (year < 1900)
		year += 1900;
	if (month < 4) {
		year1 = year - 1;
	} else {
		year1 = year + 1;
	}

	if (month < 4) {
		financialyear0= (parseInt(year1)+1)+ "-" + (parseInt(year)+1);
		financialyear = year1 + "-" + year;
		var ssyr1=financialyear0.substring(0,5);
		var ssyr2=financialyear0.substring(7,9);
			fin1=ssyr1+ssyr2;
			
		var ssyr3=financialyear.substring(0,5);
		var ssyr4=financialyear.substring(7,9);
			fin2=ssyr3+ssyr4;
		
		/*financialyear1 = (parseInt(year1)-1) + "-" + (parseInt(year)-1);
		financialyear2 = (parseInt(year1)-2) + "-" + (parseInt(year)-2);
		document.getElementById("l1").innerHTML='<font color="#FF0000">'+ " ("+ (parseInt(year1)-3) + "-" + (parseInt(year)-3) +")"+ '</font>';		
		document.getElementById("l2").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year1)-2) + "-" + (parseInt(year)-2) +")"+ '</font>';
		document.getElementById("l3").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year1)-2) +")"+ '</font>';
		document.getElementById("l4").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year1)-2) + "-" + (parseInt(year)-2) +")"+ '</font>';
		document.getElementById("l5").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year1)-2) +")"+ '</font>';
		document.getElementById("l6").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year1)-2) +")"+ '</font>';
		document.getElementById("l7").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year1)-1) + "-" + (parseInt(year)-1) +")"+ '</font>';*/
	} else {
		financialyear0=(parseInt(year)+1)+ "-" + (parseInt(year1)+1);
		financialyear = year + "-" + year1;
		var ssyr1=financialyear0.substring(0,5);
		var ssyr2=financialyear0.substring(7,9);
			fin1=ssyr1+ssyr2;
			
		var ssyr3=financialyear.substring(0,5);
		var ssyr4=financialyear.substring(7,9);
			fin2=ssyr3+ssyr4;
		
		/*financialyear1 = (parseInt(year)-1) + "-" + (parseInt(year1)-1);
		financialyear2 = (parseInt(year)-2) + "-" + (parseInt(year1)-2);
		document.getElementById("l1").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year)-2) + "-" + (parseInt(year1)-2) +")"+ '</font>';
		document.getElementById("l2").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year)-1) + "-" + (parseInt(year1)-1) +")"+ '</font>';
		document.getElementById("l3").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year)-1) +")"+ '</font>';
		document.getElementById("l4").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year)-1) + "-" + (parseInt(year1)-1) +")"+ '</font>';
		document.getElementById("l5").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year)-1) +"-" + (parseInt(year1)-1)+")"+ '</font>';
		document.getElementById("l6").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year)-1) +"-" + (parseInt(year1)-1)+")"+ '</font>';
		document.getElementById("l7").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year)) + "-" + (parseInt(year1)) +")"+ '</font>';*/
	}

	for(var k=1;k<=1;k++)
		{
			/*if(k==0)
			{
				var se = document.getElementById("cmbFinancialYear");
		  		var op = document.createElement("OPTION");
		  		op.value = financialyear;
		  		var txt = document.createTextNode(financialyear);
		  		op.appendChild(txt);
		  		se.appendChild(op);
			}else*/ if(k==1)
			{
				var se = document.getElementById("cmbFinancialYear");
		  		var op = document.createElement("OPTION");
//		  		op.value = fin1;
//		  		var txt = document.createTextNode(fin1);
		  		op.value = fin2;
		  		var txt = document.createTextNode(fin2);
		  		op.appendChild(txt);
		  		se.appendChild(op);
		  		
			}/*else if(k==2)
			{
				var se = document.getElementById("cmbFinancialYear");
		  		var op = document.createElement("OPTION");
		  		op.value = financialyear;
		  		var txt = document.createTextNode(financialyear);
		  		op.appendChild(txt);
		  		se.appendChild(op);
		  		
			}else if(k==3)
			{
				var se = document.getElementById("cmbFinancialYear");
		  		var op = document.createElement("OPTION");
		  		op.value = financialyear0;
		  		var txt = document.createTextNode(financialyear0);
		  		op.appendChild(txt);
		  		se.appendChild(op);
		  		
			}                        */             
		}    
		document.getElementById("cmbFinancialYear").value=fin1;

	//document.frmCivil_Budget_Format_9.butView.disabled = false;
	document.frmCivil_Budget_Format_9.butSub.disabled = false;
	document.frmCivil_Budget_Format_9.butDelete.disabled = true;
	document.frmCivil_Budget_Format_9.butUpdate.disabled = true;
}

function ChangeYearDuration()
{
	var fy = document.getElementById("cmbFinancialYear").value;
	var fy1=fy.split('-');
	var year1 = fy1[0];
	var year = "20"+fy1[1];
	
		document.getElementById("l1").innerHTML='<font color="#FF0000">'+ " ("+ (parseInt(year1)-2) + "-" + (parseInt(year)-2) +")"+ '</font>';		
		document.getElementById("l2").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year1)-1) + "-" + (parseInt(year)-1) +")"+ '</font>';
		document.getElementById("l3").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year1)-1) + ")"+ '</font>';
		document.getElementById("l30").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year1)-1) + ")"+ '</font>';
		document.getElementById("l4").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year1)-1) +")"+ '</font>';
		document.getElementById("l40").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year)-1) +")"+ '</font>';
		document.getElementById("l41").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year1)-1) +")"+ '</font>';
		document.getElementById("l42").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year)-1) +")"+ '</font>';
		document.getElementById("l5").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year1)-1) + "-" + (parseInt(year)-1) +")"+ '</font>';
		document.getElementById("l6").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year1)-1) + "-" + (parseInt(year)-1) +")"+ '</font>';
		document.getElementById("l7").innerHTML='<font color="#FF0000">'+" ("+ (parseInt(year1)) + "-" + (parseInt(year)) +")"+ '</font>';
	
		var tbody = document.getElementById("grid_body");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}
}
function firstLoad1(baseResponse)
{

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		/** Delete Existing Values from Grid */
		var tbody = document.getElementById("grid_body");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		var r_no = baseResponse.getElementsByTagName("BudgetGroupMajor");
		seq = 0;

		var item = new Array();
		//alert("r_no"+r_no.length);
		for ( var k = 0; k < r_no.length; k++) {
			//alert("111");
			item[0] = baseResponse.getElementsByTagName("BudgetGroupMajor")[k].firstChild.nodeValue;
			
			item[1] = baseResponse.getElementsByTagName("Ac_fr_lst_yr")[k].firstChild.nodeValue;
			
			item[2] = baseResponse.getElementsByTagName("BE_fr_Yr")[k].firstChild.nodeValue;
			
			item[3] = baseResponse
					.getElementsByTagName("Actual_fr_Period_Apr_Nov")[k].firstChild.nodeValue;
			
			item[4] = baseResponse.getElementsByTagName("account_head")[k].firstChild.nodeValue;
			
			item[5] = baseResponse.getElementsByTagName("anticipated")[k].firstChild.nodeValue;
		
			item[6] = baseResponse.getElementsByTagName("RE_FOR_YEAR")[k].firstChild.nodeValue;
			
			item[7] = baseResponse.getElementsByTagName("BE_FOR_NEXT_YEAR")[k].firstChild.nodeValue;
			
			item[8] = baseResponse.getElementsByTagName("variation")[k].firstChild.nodeValue;
			item[9] = baseResponse.getElementsByTagName("reason_variation")[k].firstChild.nodeValue;
			if(item[9]=="-")
			{
				item[9]="";
			}
			else if(item[9]=="null")
			{
				item[9]="";
			}
			item[10] = baseResponse.getElementsByTagName("variation_btwn")[k].firstChild.nodeValue;
			if(item[10]=="-")
			{
				item[10]="";
			}
			else if(item[10]=="null")
			{
				item[10]="";
			}
			var one=item[1];
			item[1]=one.replace("-","");
			var two=item[2];
			item[2]=two.replace("-","");
			var three=item[3];
			item[3]=three.replace("-","");
			var four=item[5];
			item[5]=four.replace("-","");
			
			var seven=item[7];
			item[7]=seven.replace("-","");
			
			/** Create Table Row */
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq;
			
			/** Sl No */
			var cell0 = document.createElement("TD");
			var slno = document.createTextNode(seq + 1);
			cell0.appendChild(slno);
			mycurrent_row.appendChild(cell0);

			/** Budget group */
			var cell2 = document.createElement("TD");
			var Head_of_Account = document.createElement('TEXTAREA', 'option1');
			Head_of_Account.name = "Head_of_Account" + seq;
			Head_of_Account.id = "Head_of_Account" + seq;
			Head_of_Account.value = item[0];
			Head_of_Account.readOnly = true;
			Head_of_Account.setAttribute("cols", "5");
			Head_of_Account.style.height = "30px";
			Head_of_Account.style.width = "150px";
			cell2.appendChild(Head_of_Account);
			mycurrent_row.appendChild(cell2);
			
			
			
			
			/** Head of Account     */
			var cell33 = document.createElement("TD");
			var head_account = document.createElement("input");
			head_account.type = "hidden";
			head_account.name = "head_account" + seq;
			head_account.id = "head_account" + seq;
			head_account.value = item[4];
			cell33.appendChild(head_account);
			cell33.style.textAlign="right";
			cell33.style.fontSize="16px";
			var currentText = document.createTextNode(item[4]);
			cell33.appendChild(currentText);
			mycurrent_row.appendChild(cell33);


			/** Actuals for the Last Year */
			var cell3 = document.createElement("TD");
			var Actuals_for_Last_Year = document.createElement("input");
			Actuals_for_Last_Year.type = "hidden";
			Actuals_for_Last_Year.name = "Actuals_for_Last_Year" + seq;
			Actuals_for_Last_Year.id = "Actuals_for_Last_Year" + seq;
			Actuals_for_Last_Year.value = item[1] + ".00";
			cell3.appendChild(Actuals_for_Last_Year);
			cell3.style.textAlign="right";
			cell3.style.fontSize="16px";
			cell3.style.color="#A52A2A";
			var currentText = document.createTextNode(item[1] + ".00");
			cell3.appendChild(currentText);
			mycurrent_row.appendChild(cell3);

			/** BE for the Year */
			var cell4 = document.createElement("TD");
			var BE_for_the_Year = document.createElement("input");
			BE_for_the_Year.type = "hidden";
			BE_for_the_Year.name = "BE_for_the_Year" + seq;
			BE_for_the_Year.id = "BE_for_the_Year" + seq;
			BE_for_the_Year.value = item[2] + ".00";
			cell4.appendChild(BE_for_the_Year);
			cell4.style.textAlign="right";
			cell4.style.fontSize="16px";
			cell4.style.color="#00008B";
			var currentText = document.createTextNode(item[2] + ".00");
			cell4.appendChild(currentText);
			mycurrent_row.appendChild(cell4);
			
			
			
			
			
			

			/** Actuals for the Period Apr to Nov */
			var cell5 = document.createElement("TD");
			var Actuals_for_Period_Apr_to_Nov = document.createElement("input");
			Actuals_for_Period_Apr_to_Nov.type = "hidden";
			Actuals_for_Period_Apr_to_Nov.name = "Actuals_for_Period_Apr_to_Nov"
					+ seq;
			Actuals_for_Period_Apr_to_Nov.id = "Actuals_for_Period_Apr_to_Nov"
					+ seq;
			Actuals_for_Period_Apr_to_Nov.value = item[3] + ".00";
			cell5.appendChild(Actuals_for_Period_Apr_to_Nov);
			cell5.style.textAlign="right";
			cell5.style.fontSize="16px";
			cell5.style.color="#006400";
			var currentText = document.createTextNode(item[3] + ".00");
			cell5.appendChild(currentText);
			mycurrent_row.appendChild(cell5);

			
			/** Anticipated Dec to Mar */
			var cell6 = document.createElement("TD");
			var Anticipated_Dec_to_Mar = document
					.createElement("input");
			Anticipated_Dec_to_Mar.type = "Text";
			Anticipated_Dec_to_Mar.name = "Anticipated_Dec_to_Mar"
					+ seq;
			Anticipated_Dec_to_Mar.id = "Anticipated_Dec_to_Mar"
					+ seq;
			Anticipated_Dec_to_Mar.value = item[5] + ".00";
			//Anticipated_Dec_to_Mar.maxLength = "10";
			Anticipated_Dec_to_Mar.size = "8";
			//Anticipated_Dec_to_Mar.style.textAlign="right";
			Anticipated_Dec_to_Mar.readOnly = true;
			cell6.appendChild(Anticipated_Dec_to_Mar);
			var currentText = document.createTextNode("");
			cell6.appendChild(currentText);
			mycurrent_row.appendChild(cell6);
			
			/** Anticipated for the Period Dec to Mar */
			//alert("inside");
			var cell6 = document.createElement("TD");
			var Anticipated_for_Period_Dec_to_Mar = document
					.createElement("input");
			Anticipated_for_Period_Dec_to_Mar.type = "Text";
			Anticipated_for_Period_Dec_to_Mar.name = "Anticipated_for_Period_Dec_to_Mar"
					+ seq;
			Anticipated_for_Period_Dec_to_Mar.id = "Anticipated_for_Period_Dec_to_Mar"
					+ seq;
			Anticipated_for_Period_Dec_to_Mar.value =0 + ".00";
			//Anticipated_for_Period_Dec_to_Mar.maxLength = "10";
			Anticipated_for_Period_Dec_to_Mar.size = "8";
			cell6.appendChild(Anticipated_for_Period_Dec_to_Mar);
			var currentText = document.createTextNode("");
			cell6.appendChild(currentText);
			mycurrent_row.appendChild(cell6);
			/** RE for the Year */
			var cell7 = document.createElement("TD");
			var RE_for_Year = document.createElement("input");
			RE_for_Year.type = "Text";
			RE_for_Year.name = "RE_FOR_YEAR" + seq;
			RE_for_Year.id = "RE_FOR_YEAR" + seq;
			RE_for_Year.value =item[6] + ".00";
			RE_for_Year.setAttribute('onblur', "Variation_bt_BE_RE(" + seq
					+ ")");
			//RE_for_Year.maxLength = "10";
			RE_for_Year.size = "8";
			cell7.appendChild(RE_for_Year);
			var currentText = document.createTextNode("");
			cell7.appendChild(currentText);
			mycurrent_row.appendChild(cell7);

			/** Variation betwen BE and RE */
			var cell8 = document.createElement("TD");
			var Variation_betwen_BE_and_RE = document.createElement("input");
			Variation_betwen_BE_and_RE.type = "Text";//
			Variation_betwen_BE_and_RE.name = "Variation_betwen_BE_and_RE"
					+ seq;
			Variation_betwen_BE_and_RE.id = "Variation_betwen_BE_and_RE" + seq;
			Variation_betwen_BE_and_RE.value = item[8];
			Variation_betwen_BE_and_RE.readOnly = true;
			//Variation_betwen_BE_and_RE.maxLength = "10";
			Variation_betwen_BE_and_RE.size = "8";
			cell8.appendChild(Variation_betwen_BE_and_RE);
			var currentText = document.createTextNode("");
			cell8.appendChild(currentText);
			mycurrent_row.appendChild(cell8);

			/** Reason for Variation */
			var cell9 = document.createElement("TD");
			var Reason_for_Variation = document.createElement('TEXTAREA',
					'option1');
			Reason_for_Variation.name = "Reason_for_Variation" + seq;
			Reason_for_Variation.id = "Reason_for_Variation" + seq;
			Reason_for_Variation.value = item[9];
			Reason_for_Variation.setAttribute("cols", "5");
			Reason_for_Variation.style.height = "30px";
			Reason_for_Variation.style.width = "150px";
			cell9.appendChild(Reason_for_Variation);
			mycurrent_row.appendChild(cell9);

			/** BE for Next Year */
			var cell20 = document.createElement("TD");
			var BE_for_Next_Year = document.createElement("input");
			BE_for_Next_Year.type = "Text";
			BE_for_Next_Year.name = "BE_FOR_NEXT_YEAR" + seq;
			BE_for_Next_Year.id = "BE_FOR_NEXT_YEAR" + seq;
			BE_for_Next_Year.value = item[7] + ".00";
			//BE_for_Next_Year.maxLength = "10";
			BE_for_Next_Year.size = "8";
			cell20.appendChild(BE_for_Next_Year);
			var currentText = document.createTextNode("");
			cell20.appendChild(currentText);
			mycurrent_row.appendChild(cell20);

			/**
			 * Reason for Variation if any between RE for the Year and the next
			 * Year
			 */
			var cell21 = document.createElement("TD");
			var Variation_btwn_REyr_and_NXTyr = document.createElement(
					'TEXTAREA', 'option1');
			Variation_btwn_REyr_and_NXTyr.name = "Variation_btwn_REyr_and_NXTyr"
					+ seq;
			Variation_btwn_REyr_and_NXTyr.id = "Variation_btwn_REyr_and_NXTyr"
					+ seq;
			Variation_btwn_REyr_and_NXTyr.value = item[10];
			Variation_btwn_REyr_and_NXTyr.setAttribute("cols", "5");
			Variation_btwn_REyr_and_NXTyr.style.height = "40px";
			Variation_btwn_REyr_and_NXTyr.style.width = "180px";
			cell21.appendChild(Variation_btwn_REyr_and_NXTyr);
			mycurrent_row.appendChild(cell21);

			tbody.appendChild(mycurrent_row);

			/** Increment Sequence Number */
			seq = seq + 1;
		}
		document.getElementById("imgfld").style.visibility = "hidden";
		document.getElementById("RecordCount").value = seq;
	}else if(flag == "Exist"){
		alert("Format-9 have Already Freezed");
		clrForm();
	}
	else if(flag == "nodata")
	{
		alert("No Data Found");
	}
	else {	
		alert("Failed to Load Data");
	}

}



function firstLoad(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
		/** Delete Existing Values from Grid */
		var tbody = document.getElementById("grid_body");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		var r_no = baseResponse.getElementsByTagName("BudgetGroupMajor");
		seq = 0;

		var item = new Array();
		for ( var k = 0; k < r_no.length; k++) {

			item[0] = baseResponse.getElementsByTagName("BudgetGroupMajor")[k].firstChild.nodeValue;
		
			item[1] = baseResponse.getElementsByTagName("Ac_fr_lst_yr")[k].firstChild.nodeValue;
			item[2] = baseResponse.getElementsByTagName("BE_fr_Yr")[k].firstChild.nodeValue;
			item[3] = baseResponse.getElementsByTagName("actuals_for_period_apr_to_nov")[k].firstChild.nodeValue;
			
			item[4] = baseResponse.getElementsByTagName("account_head")[k].firstChild.nodeValue;
			
			item[5] = baseResponse.getElementsByTagName("ANTICIPATED_FR_PERIOD_DEC_MAR")[k].firstChild.nodeValue;
			item[6] = baseResponse.getElementsByTagName("RE_FOR_YEAR")[k].firstChild.nodeValue;
			item[7] = baseResponse.getElementsByTagName("BE_FOR_NEXT_YEAR")[k].firstChild.nodeValue;
			item[8] = baseResponse.getElementsByTagName("variation")[k].firstChild.nodeValue;
			item[9] = baseResponse.getElementsByTagName("reason_variation")[k].firstChild.nodeValue;
			if(item[9]=="-")
			{
				item[9]="";
			}
			else if(item[9]=="null")
			{
				item[9]="";
			}
			
			item[10] = baseResponse.getElementsByTagName("variation_btwn")[k].firstChild.nodeValue;
			if(item[10]=="-")
			{
				item[10]="";
			}
			else if(item[10]=="null")
			{
				item[10]="";
			}
			
		//	alert(item[1].length);
		//	alert(":::"+item[1].charAt(0));
			
				var one=item[1];
				item[1]=one.replace("-","");
				var two=item[2];
				item[2]=two.replace("-","");
				var three=item[3];
				item[3]=three.replace("-","");
				var four=item[5];
				item[5]=four.replace("-","");
				
				var seven=item[7];
				item[7]=seven.replace("-","");
			
			/** Create Table Row */
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq;
			
			/** Sl No */
			var cell0 = document.createElement("TD");
			var slno = document.createTextNode(seq + 1);
			cell0.appendChild(slno);
			mycurrent_row.appendChild(cell0);

			/** Budget group */
			var cell2 = document.createElement("TD");
			var Head_of_Account = document.createElement('TEXTAREA', 'option1');
			Head_of_Account.name = "Head_of_Account" + seq;
			Head_of_Account.id = "Head_of_Account" + seq;
			Head_of_Account.value = item[0];
			Head_of_Account.readOnly = true;
			Head_of_Account.setAttribute("cols", "5");
			Head_of_Account.style.height = "30px";
			Head_of_Account.style.width = "150px";
			cell2.appendChild(Head_of_Account);
			mycurrent_row.appendChild(cell2);
			
			
			
			
			/** Head of Account     */
			var cell33 = document.createElement("TD");
			var head_account = document.createElement("input");
			head_account.type = "hidden";
			head_account.name = "head_account" + seq;
			head_account.id = "head_account" + seq;
			head_account.value = item[4];
			cell33.appendChild(head_account);
			cell33.style.textAlign="right";
			cell33.style.fontSize="16px";
			var currentText = document.createTextNode(item[4]);
			cell33.appendChild(currentText);
			mycurrent_row.appendChild(cell33);


			/** Actuals for the Last Year */
			var cell3 = document.createElement("TD");
			var Actuals_for_Last_Year = document.createElement("input");
			Actuals_for_Last_Year.type = "hidden";
			Actuals_for_Last_Year.name = "Actuals_for_Last_Year" + seq;
			Actuals_for_Last_Year.id = "Actuals_for_Last_Year" + seq;
			Actuals_for_Last_Year.value = item[1] + ".00";
			cell3.appendChild(Actuals_for_Last_Year);
			cell3.style.textAlign="right";
			cell3.style.fontSize="16px";
			cell3.style.color="#A52A2A";
			var currentText = document.createTextNode(item[1] + ".00");
			cell3.appendChild(currentText);
			mycurrent_row.appendChild(cell3);

			/** BE for the Year */
			var cell4 = document.createElement("TD");
			var BE_for_the_Year = document.createElement("input");
			BE_for_the_Year.type = "hidden";
			BE_for_the_Year.name = "BE_for_the_Year" + seq;
			BE_for_the_Year.id = "BE_for_the_Year" + seq;
			BE_for_the_Year.value = item[2] + ".00";
			cell4.appendChild(BE_for_the_Year);
			cell4.style.textAlign="right";
			cell4.style.fontSize="16px";
			cell4.style.color="#00008B";
			var currentText = document.createTextNode(item[2] + ".00");
			cell4.appendChild(currentText);
			mycurrent_row.appendChild(cell4);

			/** Actuals for the Period Apr to Nov */
			var cell5 = document.createElement("TD");
			var Actuals_for_Period_Apr_to_Nov = document.createElement("input");
			Actuals_for_Period_Apr_to_Nov.type = "hidden";
			Actuals_for_Period_Apr_to_Nov.name = "Actuals_for_Period_Apr_to_Nov"
					+ seq;
			Actuals_for_Period_Apr_to_Nov.id = "Actuals_for_Period_Apr_to_Nov"
					+ seq;
			Actuals_for_Period_Apr_to_Nov.value = item[3] + ".00";
			cell5.appendChild(Actuals_for_Period_Apr_to_Nov);
			cell5.style.textAlign="right";
			cell5.style.fontSize="16px";
			cell5.style.color="#006400";
			var currentText = document.createTextNode(item[3] + ".00");
			cell5.appendChild(currentText);
			mycurrent_row.appendChild(cell5);
			
			
			/** Anticipated Dec to Mar */
			var cell6 = document.createElement("TD");
			var Anticipated_Dec_to_Mar = document
					.createElement("input");
			Anticipated_Dec_to_Mar.type = "Text";
			Anticipated_Dec_to_Mar.name = "Anticipated_Dec_to_Mar"
					+ seq;
			Anticipated_Dec_to_Mar.id = "Anticipated_Dec_to_Mar"
					+ seq;
			Anticipated_Dec_to_Mar.value = item[5] + ".00";
			//Anticipated_Dec_to_Mar.maxLength = "10";
			Anticipated_Dec_to_Mar.size = "8";
			//Anticipated_Dec_to_Mar.style.textAlign="right";
			Anticipated_Dec_to_Mar.readOnly = true;
			cell6.appendChild(Anticipated_Dec_to_Mar);
			var currentText = document.createTextNode("");
			cell6.appendChild(currentText);
			mycurrent_row.appendChild(cell6);

			/** Anticipated for the Period Dec to Mar */
			
			var cell6 = document.createElement("TD");
			var Anticipated_for_Period_Dec_to_Mar = document
					.createElement("input");
			Anticipated_for_Period_Dec_to_Mar.type = "text";
			Anticipated_for_Period_Dec_to_Mar.readOnly = true;
			Anticipated_for_Period_Dec_to_Mar.name = "Anticipated_for_Period_Dec_to_Mar"
					+ seq;
			Anticipated_for_Period_Dec_to_Mar.id = "Anticipated_for_Period_Dec_to_Mar"
					+ seq;
			Anticipated_for_Period_Dec_to_Mar.value = 0 + ".00";
			//Anticipated_for_Period_Dec_to_Mar.maxLength = "10";
			Anticipated_for_Period_Dec_to_Mar.size = "8";
			cell6.appendChild(Anticipated_for_Period_Dec_to_Mar);
			var currentText = document.createTextNode("");
			cell6.appendChild(currentText);
			mycurrent_row.appendChild(cell6);

			/** RE for the Year */
			var cell7 = document.createElement("TD");
			var RE_for_Year = document.createElement("input");
			RE_for_Year.type = "Text";
			RE_for_Year.readOnly = true;
			RE_for_Year.name = "RE_FOR_YEAR" + seq;
			RE_for_Year.id = "RE_FOR_YEAR" + seq;
			RE_for_Year.value =item[6] + ".00";
			RE_for_Year.setAttribute('onblur', "Variation_bt_BE_RE(" + seq
					+ ")");
			//RE_for_Year.maxLength = "10";
			RE_for_Year.size = "8";
			cell7.appendChild(RE_for_Year);
			var currentText = document.createTextNode("");
			cell7.appendChild(currentText);
			mycurrent_row.appendChild(cell7);

			/** Variation betwen BE and RE */
			var cell8 = document.createElement("TD");
			var Variation_betwen_BE_and_RE = document.createElement("input");
			Variation_betwen_BE_and_RE.type = "Text";
		
			Variation_betwen_BE_and_RE.name = "Variation_betwen_BE_and_RE"
					+ seq;
			Variation_betwen_BE_and_RE.id = "Variation_betwen_BE_and_RE" + seq;
			Variation_betwen_BE_and_RE.value = item[8];
			Variation_betwen_BE_and_RE.readOnly = true;
			//Variation_betwen_BE_and_RE.maxLength = "10";
			Variation_betwen_BE_and_RE.size = "8";
			cell8.appendChild(Variation_betwen_BE_and_RE);
			var currentText = document.createTextNode("");
			cell8.appendChild(currentText);
			mycurrent_row.appendChild(cell8);

			/** Reason for Variation */
			var cell9 = document.createElement("TD");
			var Reason_for_Variation = document.createElement('TEXTAREA',
					'option1');
			Reason_for_Variation.name = "Reason_for_Variation" + seq;
			Reason_for_Variation.readOnly = true;
			Reason_for_Variation.id = "Reason_for_Variation" + seq;
			Reason_for_Variation.value = item[9];
			Reason_for_Variation.setAttribute("cols", "5");
			Reason_for_Variation.style.height = "30px"
			Reason_for_Variation.style.width = "150px";
			cell9.appendChild(Reason_for_Variation);
			mycurrent_row.appendChild(cell9);

			/** BE for Next Year */
			var cell20 = document.createElement("TD");
			var BE_for_Next_Year = document.createElement("input");
			BE_for_Next_Year.type = "Text";
			BE_for_Next_Year.readOnly = true;
			BE_for_Next_Year.name = "BE_FOR_NEXT_YEAR" + seq;
			BE_for_Next_Year.id = "BE_FOR_NEXT_YEAR" + seq;
			BE_for_Next_Year.value = item[7] + ".00";
			//BE_for_Next_Year.maxLength = "10";
			BE_for_Next_Year.size = "8";
			cell20.appendChild(BE_for_Next_Year);
			var currentText = document.createTextNode("");
			cell20.appendChild(currentText);
			mycurrent_row.appendChild(cell20);

			/**
			 * Reason for Variation if any between RE for the Year and the next
			 * Year
			 */
			var cell21 = document.createElement("TD");
			var Variation_btwn_REyr_and_NXTyr = document.createElement(
					'TEXTAREA', 'option1');
			Variation_btwn_REyr_and_NXTyr.name = "Variation_btwn_REyr_and_NXTyr"
					+ seq;
			Variation_btwn_REyr_and_NXTyr.id = "Variation_btwn_REyr_and_NXTyr"
					+ seq;
			Variation_btwn_REyr_and_NXTyr.value = item[10];
			Variation_btwn_REyr_and_NXTyr.readOnly = true;
			Variation_btwn_REyr_and_NXTyr.setAttribute("cols", "5");
			Variation_btwn_REyr_and_NXTyr.style.height = "30px";
			Variation_btwn_REyr_and_NXTyr.style.width = "150px";
			cell21.appendChild(Variation_btwn_REyr_and_NXTyr);
			mycurrent_row.appendChild(cell21);

			tbody.appendChild(mycurrent_row);

			/** Increment Sequence Number */
			seq = seq + 1;
		}
		document.getElementById("imgfld").style.visibility = "hidden";
		document.getElementById("RecordCount").value = seq;
	}else if(flag == "Exist"){
		alert("Format-2 have Already Freezed");
		clrForm();
	}
	else if(flag == "nodata")
	{
		alert("No Data Found");
	}
	else {	
		alert("Failed to Load Data");
	}
}

function clrForm() {
	/** Delete Existing Values from Grid */
	var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}

	LoadAccountingUnitID('LIST_ALL_UNITS');
	document.getElementById("imgfld").style.visibility = "hidden";
	//document.frmCivil_Budget_Format_9.butView.disabled = false;
	document.frmCivil_Budget_Format_9.butSub.disabled = false;
	document.frmCivil_Budget_Format_9.butDelete.disabled = true;
	document.frmCivil_Budget_Format_9.butUpdate.disabled = true;

}

function LoadData(path) {
	//alert(path);
	//document.frmCivil_Budget_Format_9.butView.disabled = false;
	document.frmCivil_Budget_Format_9.butSub.disabled = true;
	document.frmCivil_Budget_Format_9.butDelete.disabled = false;
	document.frmCivil_Budget_Format_9.butUpdate.disabled = false;

	document.getElementById("filter").value = "view";
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var RecordCount = 0;
	var url = path + "/Civil_Budget_Format_9?filter=view&cmbFinancialYear="
			+ cmbFinancialYear + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode
			+ "&cmbOffice_code=" + cmbOffice_code + "&RecordCount="
			+ RecordCount;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}

function LoadData_View(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		/** Delete Existing Values from Grid */
		var tbody = document.getElementById("grid_body");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		var r_no = baseResponse.getElementsByTagName("Head_of_Account");
		var len = baseResponse.getElementsByTagName("Head_of_Account").length;
		seq = 0;
		if (len != 0) {
			var item = new Array();
			for ( var k = 0; k < r_no.length; k++) {

				item[0] = baseResponse.getElementsByTagName("Head_of_Account")[k].firstChild.nodeValue;
				item[1] = baseResponse
						.getElementsByTagName("Actuals_for_Last_Year")[k].firstChild.nodeValue;
				if((item[1]==0) || (item[1]=='null'))
					{
					item[1]="0";
					}
				item[2] = baseResponse.getElementsByTagName("BE_for_the_Year")[k].firstChild.nodeValue;
				if((item[2]==0) || (item[2]=='null'))
				{
				item[2]="0";
				}
				item[3] = baseResponse
						.getElementsByTagName("Actuals_for_Period_Apr_to_Nov")[k].firstChild.nodeValue;
				if((item[3]==0) || (item[3]=='null'))
				{
				item[3]="0";
				}
				item[4] = baseResponse
						.getElementsByTagName("Anticipated_for_Period_Dec_to_Mar")[k].firstChild.nodeValue;
				if((item[4]==0) || (item[4]=='null'))
				{
				item[4]="0";
				}
				item[5] = baseResponse.getElementsByTagName("RE_for_Year")[k].firstChild.nodeValue;
				if((item[5]==0) || (item[5]=='null'))
				{
				item[5]="0";
				}
				item[6] = baseResponse
						.getElementsByTagName("Variation_betwen_BE_and_RE")[k].firstChild.nodeValue;
				if((item[6]==0) || (item[6]=='null'))
				{
				item[6]="0";
				}
				item[7] = baseResponse
						.getElementsByTagName("Reason_for_Variation")[k].firstChild.nodeValue;
				if (item[7] == 'null') {
					item[7] = "";
				}
				item[8] = baseResponse.getElementsByTagName("BE_for_Next_Year")[k].firstChild.nodeValue;
				if((item[8]==0) || (item[8]=='null'))
				{
				item[8]="0";
				}
				item[9] = baseResponse
						.getElementsByTagName("Variation_btwn_REyr_and_NXTyr")[k].firstChild.nodeValue;
				if (item[9] == 'null') {
					item[9] = "";
				}

				/** Create Table Row */
				var mycurrent_row = document.createElement("TR");
				mycurrent_row.id = seq;

				/** Sl No */
				var cell0 = document.createElement("TD");
				var slno = document.createTextNode(seq + 1);
				cell0.appendChild(slno);
				mycurrent_row.appendChild(cell0);

				/** Budget group */
				var cell2 = document.createElement("TD");
				var Head_of_Account = document.createElement('TEXTAREA', 'option1');
				Head_of_Account.name = "Head_of_Account" + seq;
				Head_of_Account.id = "Head_of_Account" + seq;
				Head_of_Account.value = item[0];
				Head_of_Account.readOnly = true;
				Head_of_Account.setAttribute("cols", "5");
				Head_of_Account.style.height = "60px";
				Head_of_Account.style.width = "350px";
				cell2.appendChild(Head_of_Account);
				mycurrent_row.appendChild(cell2);
				
				/** Head of Account     */
				var cell33 = document.createElement("TD");
				var head_account = document.createElement("input");
				head_account.type = "hidden";
				head_account.name = "head_account" + seq;
				head_account.id = "head_account" + seq;
				head_account.value = item[4];
				cell33.appendChild(head_account);
				cell33.style.textAlign="right";
				cell33.style.fontSize="16px";
				var currentText = document.createTextNode(item[4]);
				cell33.appendChild(currentText);
				mycurrent_row.appendChild(cell33);

				
				
				

				/** Actuals for the Last Year */
				var cell3 = document.createElement("TD");
				var Actuals_for_Last_Year = document.createElement("input");
				Actuals_for_Last_Year.type = "hidden";
				Actuals_for_Last_Year.name = "Actuals_for_Last_Year" + seq;
				Actuals_for_Last_Year.id = "Actuals_for_Last_Year" + seq;
				Actuals_for_Last_Year.value = item[1] + ".00";				
				cell3.appendChild(Actuals_for_Last_Year);
				cell3.style.textAlign="right";
				cell3.style.fontSize="16px";
				cell3.style.color="#A52A2A";
				var currentText = document.createTextNode(item[1] + ".00");
				cell3.appendChild(currentText);
				mycurrent_row.appendChild(cell3);

				/** BE for the Year */
				var cell4 = document.createElement("TD");
				var BE_for_the_Year = document.createElement("input");
				BE_for_the_Year.type = "hidden";
				BE_for_the_Year.name = "BE_for_the_Year" + seq;
				BE_for_the_Year.id = "BE_for_the_Year" + seq;
				BE_for_the_Year.value = item[2] + ".00";				
				cell4.appendChild(BE_for_the_Year);
				cell4.style.textAlign="right";
				cell4.style.fontSize="16px";
				cell4.style.color="#00008B";
				var currentText = document.createTextNode(item[2] + ".00");
				cell4.appendChild(currentText);
				mycurrent_row.appendChild(cell4);

				/** Actuals for the Period Apr to Nov */
				var cell5 = document.createElement("TD");
				var Actuals_for_Period_Apr_to_Nov = document
						.createElement("input");
				Actuals_for_Period_Apr_to_Nov.type = "hidden";
				Actuals_for_Period_Apr_to_Nov.name = "Actuals_for_Period_Apr_to_Nov"
						+ seq;
				Actuals_for_Period_Apr_to_Nov.id = "Actuals_for_Period_Apr_to_Nov"
						+ seq;
				Actuals_for_Period_Apr_to_Nov.value = item[3] + ".00";				
				cell5.appendChild(Actuals_for_Period_Apr_to_Nov);
				cell5.style.textAlign="right";
				cell5.style.fontSize="16px";
				cell5.style.color="#006400";
				var currentText = document.createTextNode(item[3] + ".00");
				cell5.appendChild(currentText);
				mycurrent_row.appendChild(cell5);

				/** Anticipated Dec to Mar */
				var cell6 = document.createElement("TD");
				var Anticipated_Dec_to_Mar = document
						.createElement("input");
				Anticipated_Dec_to_Mar.type = "Text";
				Anticipated_Dec_to_Mar.name = "Anticipated_Dec_to_Mar"
						+ seq;
				Anticipated_Dec_to_Mar.id = "Anticipated_Dec_to_Mar"
						+ seq;
				Anticipated_Dec_to_Mar.value = item[4] + ".00";
				//Anticipated_Dec_to_Mar.maxLength = "10";
				Anticipated_Dec_to_Mar.size = "8";
				//Anticipated_Dec_to_Mar.style.textAlign="right";
				Anticipated_Dec_to_Mar.readOnly = true;
				cell6.appendChild(Anticipated_Dec_to_Mar);
				var currentText = document.createTextNode("");
				cell6.appendChild(currentText);
				mycurrent_row.appendChild(cell6);
				
				/** Anticipated for the Period Dec to Mar */
				var cell6 = document.createElement("TD");
				var Anticipated_for_Period_Dec_to_Mar = document
						.createElement("input");
				Anticipated_for_Period_Dec_to_Mar.type = "Text";
				Anticipated_for_Period_Dec_to_Mar.name = "Anticipated_for_Period_Dec_to_Mar"
						+ seq;
				Anticipated_for_Period_Dec_to_Mar.id = "Anticipated_for_Period_Dec_to_Mar"
						+ seq;
				Anticipated_for_Period_Dec_to_Mar.value = 0 + ".00";
				//Anticipated_for_Period_Dec_to_Mar.maxLength = "10";
				Anticipated_for_Period_Dec_to_Mar.size = "8";
				Anticipated_for_Period_Dec_to_Mar.style.textAlign="right";
				cell6.appendChild(Anticipated_for_Period_Dec_to_Mar);
				var currentText = document.createTextNode("");
				cell6.appendChild(currentText);
				mycurrent_row.appendChild(cell6);

				/** RE for the Year */
				var cell7 = document.createElement("TD");
				var RE_for_Year = document.createElement("input");
				RE_for_Year.type = "Text";
				RE_for_Year.name = "RE_for_Year" + seq;
				RE_for_Year.id = "RE_for_Year" + seq;
				RE_for_Year.value = item[5] + ".00";
				RE_for_Year.setAttribute('onblur', "Variation_bt_BE_RE("
						+ seq + ")");
				RE_for_Year.style.textAlign="right";
				//RE_for_Year.maxLength = "10";
				RE_for_Year.size = "8";
				cell7.appendChild(RE_for_Year);
				var currentText = document.createTextNode("");
				cell7.appendChild(currentText);
				mycurrent_row.appendChild(cell7);

				/** Variation betwen BE and RE */
				var cell8 = document.createElement("TD");
				var Variation_betwen_BE_and_RE = document
						.createElement("input");
				Variation_betwen_BE_and_RE.type = "Text";
				Variation_betwen_BE_and_RE.name = "Variation_betwen_BE_and_RE"
						+ seq;
				Variation_betwen_BE_and_RE.id = "Variation_betwen_BE_and_RE"
						+ seq;
				Variation_betwen_BE_and_RE.value = item[6] + ".00";
				Variation_betwen_BE_and_RE.style.textAlign="right";
				Variation_betwen_BE_and_RE.readOnly = true;
				//Variation_betwen_BE_and_RE.maxLength = "10";
				Variation_betwen_BE_and_RE.size = "8";
				cell8.appendChild(Variation_betwen_BE_and_RE);
				var currentText = document.createTextNode("");
				cell8.appendChild(currentText);
				mycurrent_row.appendChild(cell8);

				/** Reason for Variation */
				var cell9 = document.createElement("TD");
				var Reason_for_Variation = document.createElement('TEXTAREA',
						'option1');
				Reason_for_Variation.name = "Reason_for_Variation" + seq;
				Reason_for_Variation.id = "Reason_for_Variation" + seq;
				Reason_for_Variation.value = item[7];
				Reason_for_Variation.setAttribute("cols", "5");
				Reason_for_Variation.style.height = "60px";
				Reason_for_Variation.style.width = "150px";
				cell9.appendChild(Reason_for_Variation);
				mycurrent_row.appendChild(cell9);

				/** BE for Next Year */
				var cell20 = document.createElement("TD");
				var BE_for_Next_Year = document.createElement("input");
				BE_for_Next_Year.type = "Text";
				BE_for_Next_Year.name = "BE_FOR_NEXT_YEAR" + seq;
				BE_for_Next_Year.id = "BE_FOR_NEXT_YEAR" + seq;
				BE_for_Next_Year.value = item[8] + ".00";
				BE_for_Next_Year.style.textAlign="right";
				//BE_for_Next_Year.maxLength = "10";
				BE_for_Next_Year.size = "8";
				cell20.appendChild(BE_for_Next_Year);
				var currentText = document.createTextNode("");
				cell20.appendChild(currentText);
				mycurrent_row.appendChild(cell20);

				/**
				 * Reason for Variation if any between RE for the Year and the
				 * next Year
				 */
				var cell21 = document.createElement("TD");
				var Variation_btwn_REyr_and_NXTyr = document.createElement(
						'TEXTAREA', 'option1');
				Variation_btwn_REyr_and_NXTyr.name = "Variation_btwn_REyr_and_NXTyr"
						+ seq;
				Variation_btwn_REyr_and_NXTyr.id = "Variation_btwn_REyr_and_NXTyr"
						+ seq;
				Variation_btwn_REyr_and_NXTyr.value = item[9];
				Variation_btwn_REyr_and_NXTyr.setAttribute("cols", "5");
				Variation_btwn_REyr_and_NXTyr.style.height = "30px";
				Variation_btwn_REyr_and_NXTyr.style.width = "150px";
				cell21.appendChild(Variation_btwn_REyr_and_NXTyr);
				mycurrent_row.appendChild(cell21);

				tbody.appendChild(mycurrent_row);

				/** Increment Sequence Number */
				seq = seq + 1;
			}
			document.getElementById("imgfld").style.visibility = "hidden";
		} else {
			document.getElementById("imgfld").style.visibility = "hidden";
			alert("Record Does Not Exist");
		}
		document.getElementById("RecordCount").value = seq;
	}else if(flag == "Exist"){
		alert("Format-2 have Already Freezed");
		clrForm();
	} else {
		alert("Failed to Load Data");
	}
}

function funcSave() {
	
	document.getElementById("filter").value = "save";
	
	var tbody = document.getElementById("grid_body");
	
	var rowcount = tbody.rows.length;
	
	if (rowcount != 0) {
		
		for ( var i = 0; i < rowcount; i++) {
			if (document.getElementById("Anticipated_for_Period_Dec_to_Mar" + i).value == "") {
				alert("Enter Anticipated for the Period Dec to Mar in the Field");
				document.getElementById("Anticipated_for_Period_Dec_to_Mar" + i).focus();
				return false;
			}else if(document.getElementById("RE_for_Year" + i).value == ""){
				alert("Enter RE for the Year in the Field");
				document.getElementById("RE_for_Year" + i).focus();
				return false;
			}else if(document.getElementById("BE_for_Next_Year" + i).value == ""){
				alert("Enter BE for Next Year in the Field");
				document.getElementById("BE_for_Next_Year" + i).focus();
				return false;
			}else {
				return true;
			}
		}
	} else {
		alert("No Records Found to Insert...");
		return false;
	}	
}
function funcUpdate() {
	document.getElementById("filter").value = "update";
	var tbody = document.getElementById("grid_body");
	var rowcount = tbody.rows.length;
	if (rowcount != 0) {
		for ( var i = 0; i < rowcount; i++) {
			if (document.getElementById("Anticipated_for_Period_Dec_to_Mar" + i).value == "") {
				alert("Enter Anticipated for the Period Dec to Mar in the Field");
				document.getElementById("Anticipated_for_Period_Dec_to_Mar" + i).focus();
				return false;
			}else if(document.getElementById("RE_for_Year" + i).value == ""){
				alert("Enter RE for the Year in the Field");
				document.getElementById("RE_for_Year" + i).focus();
				return false;
			}else if(document.getElementById("BE_for_Next_Year" + i).value == ""){
				alert("Enter BE for Next Year in the Field");
				document.getElementById("BE_for_Next_Year" + i).focus();
				return false;
			}else {
				return true;
			}
		}
	} else {
		alert("No Records Found to Insert...");
		return false;
	}	
}

function funcDelete() {
	var r = confirm("Are U Sure to Continue?");
	if (r == true) {
		document.getElementById("filter").value = "delete";
		return true;
	} else {
		return false;
	}
}
function Variation_bt_BE_RE(sam) {
	//alert("sam:::"+sam);
	//alert(document.getElementById("RE_FOR_YEAR" + sam).value);
	var RE_for_Year = parseInt(document.getElementById("RE_FOR_YEAR" + sam).value);
	var BE_for_the_Year = parseInt(document.getElementById("BE_for_the_Year"
			+ sam).value);
	if (RE_for_Year > BE_for_the_Year) {
		var diff = RE_for_Year - BE_for_the_Year;
	} else {
		var diff = BE_for_the_Year - RE_for_Year;
	}
	document.getElementById("Variation_betwen_BE_and_RE" + sam).value = diff;
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
			return false;
	}
}

function exitfun() {
	window.close();
}
