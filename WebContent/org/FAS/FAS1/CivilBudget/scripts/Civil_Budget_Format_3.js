//		Civil_Budget_Format_3		//
var seq = 0;
var seq1 = 1;
var slno=1;
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
				deleteRow(baseResponse);
			} else if (command == "update") {
				updateRow(baseResponse);
			} else if (command == "Edit") {
				Edit1(baseResponse);
			} else if (command == "get") {
				firstLoad(baseResponse);
			} else if (command == "LoadData") {
				LoadData_View(baseResponse);
			}
			else if (command == "load_hrm") {
				load_hrm_View(baseResponse);
			}
			
		}
	}
}

function initialLoad() {
	/** Delete Existing Values from Grid */
	var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
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
/*
 * var financialyear1 = 0; var financialyear2 = 0;
 */
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
	/*
	 * financialyear1 = (parseInt(year1)-1) + "-" + (parseInt(year)-1);
	 * financialyear2 = (parseInt(year1)-2) + "-" + (parseInt(year)-2);
	 */	
	} else {
		financialyear0=(parseInt(year)+1)+ "-" + (parseInt(year1)+1);
		financialyear = year + "-" + year1;


		var ssyr1=financialyear0.substring(0,5);
		var ssyr2=financialyear0.substring(7,9);
			fin1=ssyr1+ssyr2;
		var ssyr3=financialyear.substring(0,5);
		var ssyr4=financialyear.substring(7,9);
				fin2=ssyr3+ssyr4;
		/*
		 * financialyear1 = (parseInt(year)-1) + "-" + (parseInt(year1)-1);
		 * financialyear2 = (parseInt(year)-2) + "-" + (parseInt(year1)-2);
		 */	
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
//	  		op.value = fin1;
//	  		var txt = document.createTextNode(fin1);
	  		op.value = fin2;
	  		var txt = document.createTextNode(fin2);
	  		op.appendChild(txt);
	  		se.appendChild(op);
	  		
		}/*
			 * else if(k==2) { var se =
			 * document.getElementById("cmbFinancialYear"); var op =
			 * document.createElement("OPTION"); op.value = financialyear; var
			 * txt = document.createTextNode(financialyear);
			 * op.appendChild(txt); se.appendChild(op);
			 *  } else if(k==3) { var se =
			 * document.getElementById("cmbFinancialYear"); var op =
			 * document.createElement("OPTION"); op.value = financialyear0; var
			 * txt = document.createTextNode(financialyear0);
			 * op.appendChild(txt); se.appendChild(op);
			 *  }
			 */                          
	}    
	document.getElementById("cmbFinancialYear").value=fin1;

	document.frmCivil_Budget_Format_3.butView.disabled = false;
	document.frmCivil_Budget_Format_3.butSub.disabled = false;
	document.frmCivil_Budget_Format_3.butDelete.disabled = true;
	document.frmCivil_Budget_Format_3.butUpdate.disabled = true;
}

function AddRow() {
	/* Add Row */

	/** Get TBody Object */
	var tbody = document.getElementById("grid_body");

	/** Create Table Row */
	var mycurrent_row = document.createElement("TR");
	mycurrent_row.id = seq;

	var cell = document.createElement("TD");
	var anc = document.createElement("input");
	anc.type = "checkbox";
	anc.id = "check" + seq;
	anc.name = "check" + seq;
	anc.checked = true;
	anc.disabled = true
	anc.value = seq;
	cell.appendChild(anc);
	mycurrent_row.appendChild(cell);

	/** Name of Category */
	var cell2 = document.createElement("TD");
	var Name_of_Category = document.createElement("input");
	Name_of_Category.type = "text";
	Name_of_Category.name = "Name_of_Category" + seq;
	Name_of_Category.id = "Name_of_Category" + seq;
	Name_of_Category.value = "";	
	Name_of_Category.size="6";
	cell2.appendChild(Name_of_Category);
	mycurrent_row.appendChild(cell2);

	/** Time Scale of Pay (Specify O.G/S.G/Spl.Gr */
	var cell3 = document.createElement("TD");
	var Time_Scale_of_Pay = document.createElement("input");
	Time_Scale_of_Pay.type = "text";
	Time_Scale_of_Pay.name = "Time_Scale_of_Pay" + seq;
	Time_Scale_of_Pay.id = "Time_Scale_of_Pay" + seq;
	Time_Scale_of_Pay.value = "";
	Time_Scale_of_Pay.size= "7";
	cell3.appendChild(Time_Scale_of_Pay);
	mycurrent_row.appendChild(cell3);

	/** No of Sanctioned Posts */
	var cell4 = document.createElement("TD");
	var No_of_Sanctioned_Posts  = document.createElement("input");
	No_of_Sanctioned_Posts.type = "text";
	No_of_Sanctioned_Posts.name = "No_of_Sanctioned_Posts" + seq;
	No_of_Sanctioned_Posts.id = "No_of_Sanctioned_Posts" + seq;
	No_of_Sanctioned_Posts.value = "";
	No_of_Sanctioned_Posts.size = "6";
	cell4.appendChild(No_of_Sanctioned_Posts );
	mycurrent_row.appendChild(cell4);

	/** No of Incumbents in Roll */
	var cell5 = document.createElement("TD");
	var No_of_Incumbents_in_Roll = document.createElement("input");
	No_of_Incumbents_in_Roll.type = "text";
	No_of_Incumbents_in_Roll.name = "No_of_Incumbents_in_Roll" + seq;
	No_of_Incumbents_in_Roll.id = "No_of_Incumbents_in_Roll" + seq;
	No_of_Incumbents_in_Roll.value = "";
	No_of_Incumbents_in_Roll.size = "6";
	cell5.appendChild(No_of_Incumbents_in_Roll);
	mycurrent_row.appendChild(cell5);

	/** NO of Vacant Posts */
	var cell6 = document.createElement("TD");
	var No_of_Vacant_Posts = document.createElement("input");
	No_of_Vacant_Posts.type = "text";
	No_of_Vacant_Posts.name = "No_of_Vacant_Posts" + seq;
	No_of_Vacant_Posts.id = "No_of_Vacant_Posts" + seq;
	No_of_Vacant_Posts.value = "";
	No_of_Vacant_Posts.size = "6";
	cell6.appendChild(No_of_Vacant_Posts);
	mycurrent_row.appendChild(cell6);

	/** As on Begining of the Year - Basic Pay */
	var cell7 = document.createElement("TD");
	var Begining_of_the_Year_Basic_Pay = document.createElement("input");
	Begining_of_the_Year_Basic_Pay.type = "text";
	Begining_of_the_Year_Basic_Pay.name = "Begining_of_the_Year_Basic_Pay" + seq;
	Begining_of_the_Year_Basic_Pay.id = "Begining_of_the_Year_Basic_Pay" + seq;
	Begining_of_the_Year_Basic_Pay.value = "";
	Begining_of_the_Year_Basic_Pay.size = "6";
	cell7.appendChild(Begining_of_the_Year_Basic_Pay);
	mycurrent_row.appendChild(cell7);

	/** As on Begining of the Year - 50% B.P */
	var cell8 = document.createElement("TD");
	var Begining_of_the_Year_50_BP = document.createElement("input");
	Begining_of_the_Year_50_BP.type = "text";
	Begining_of_the_Year_50_BP.name = "Begining_of_the_Year_50_BP" + seq;
	Begining_of_the_Year_50_BP.id = "Begining_of_the_Year_50_BP" + seq;
	Begining_of_the_Year_50_BP.value = "";
	Begining_of_the_Year_50_BP.size = "6";	
	cell8.appendChild(Begining_of_the_Year_50_BP);
	mycurrent_row.appendChild(cell8);

	/** Increment Date */
	var cell9 = document.createElement("TD");
	var Increment_Date = document.createElement("input");
	Increment_Date.type = "text";
	Increment_Date.name = "Increment_Date" + seq;
	Increment_Date.id = "Increment_Date" + seq;
	Increment_Date.value = "";
	Increment_Date.size = "6";	
	cell9.appendChild(Increment_Date);
	mycurrent_row.appendChild(cell9);
	
	/** Increment Amount */
	var cell99 = document.createElement("TD");
	var Increment_Amount = document.createElement("input");
	Increment_Amount.type = "text";
	Increment_Amount.name = "Increment_Amount" + seq;
	Increment_Amount.id = "Increment_Amount" + seq;
	Increment_Amount.value = "";
	Increment_Amount.size = "6";		
	cell99.appendChild(Increment_Amount);
	mycurrent_row.appendChild(cell99);

	/** Pay After Increment - Basic Pay */
	var cell20 = document.createElement("TD");
	var After_Increment_Basic_Pay = document.createElement("input");
	After_Increment_Basic_Pay.type = "text";
	After_Increment_Basic_Pay.name = "After_Increment_Basic_Pay" + seq;
	After_Increment_Basic_Pay.id = "After_Increment_Basic_Pay" + seq;
	After_Increment_Basic_Pay.value = "";	
	After_Increment_Basic_Pay.size = "6";
	cell20.appendChild(After_Increment_Basic_Pay);
	mycurrent_row.appendChild(cell20);

	/** Pay After Increment - 50% B.P */
	var cel21 = document.createElement("TD");
	var After_Increment_50_BP = document.createElement("input");
	After_Increment_50_BP.type = "text";
	After_Increment_50_BP.name = "After_Increment_50_BP" + seq;
	After_Increment_50_BP.id = "After_Increment_50_BP" + seq;
	After_Increment_50_BP.value = "";
	After_Increment_50_BP.size = "6";
	cel21.appendChild(After_Increment_50_BP);
	mycurrent_row.appendChild(cel21);

	/** Basic Pay After Increment */
	var cel22 = document.createElement("TD");
	var Basic_Pay_After_Increment  = document.createElement("input");
	Basic_Pay_After_Increment.type = "text";
	Basic_Pay_After_Increment.name = "Basic_Pay_After_Increment" + seq;
	Basic_Pay_After_Increment.id = "Basic_Pay_After_Increment" + seq;
	Basic_Pay_After_Increment.value = "";
	Basic_Pay_After_Increment.size = "6";
	cel22.appendChild(Basic_Pay_After_Increment );
	mycurrent_row.appendChild(cel22);

	/** Total Basic Pay plus 50% Dearness pay for 12 Months During the Year */
	var cel23 = document.createElement("TD");
	var Basic_Pay_50_Dearness_pay  = document.createElement("input");
	Basic_Pay_50_Dearness_pay.type = "text";
	Basic_Pay_50_Dearness_pay.name = "Basic_Pay_50_Dearness_pay" + seq;
	Basic_Pay_50_Dearness_pay.id = "Basic_Pay_50_Dearness_pay" + seq;
	Basic_Pay_50_Dearness_pay.value = "";
	Basic_Pay_50_Dearness_pay.size = "6";
	cel23.appendChild(Basic_Pay_50_Dearness_pay );
	mycurrent_row.appendChild(cel23);
	tbody.appendChild(mycurrent_row);

	/** D.A @ 32% on Col.No.13 */
	var cel24 = document.createElement("TD");
	var DA_32_on_Col_No_13 = document.createElement("input");
	DA_32_on_Col_No_13.type = "text";
	DA_32_on_Col_No_13.name = "DA_32_on_Col_No_13" + seq;
	DA_32_on_Col_No_13.id = "DA_32_on_Col_No_13" + seq;
	DA_32_on_Col_No_13.value = "";
	DA_32_on_Col_No_13.size = "6";
	cel24.appendChild(DA_32_on_Col_No_13);
	mycurrent_row.appendChild(cel24);

	/** Other Allowances for 12 Months */
	var cell25 = document.createElement("TD");
	var Other_Allowances = document.createElement("input");
	Other_Allowances.type = "text";
	Other_Allowances.name = "Other_Allowances" + seq;
	Other_Allowances.id = "Other_Allowances" + seq;
	Other_Allowances.value = "";
	Other_Allowances.size = "6";
	cell25.appendChild(Other_Allowances);
	mycurrent_row.appendChild(cell25);

	/** Total (9+10+11) */
	var cel26 = document.createElement("TD");
	var Total  = document.createElement("input");
	Total.type = "text";
	Total.name = "Total" + seq;
	Total.id = "Total" + seq;
	Total.value = "";
	Total.size = "6";
	cel26.appendChild(Total );
	mycurrent_row.appendChild(cel26);

	/** Date of Retirement During the Year */
	var cel27 = document.createElement("TD");
	var Date_of_Retirement  = document.createElement("input");
	Date_of_Retirement.type = "text";
	Date_of_Retirement.name = "Date_of_Retirement" + seq;
	Date_of_Retirement.id = "Date_of_Retirement" + seq;
	Date_of_Retirement.value = "";
	Date_of_Retirement.size = "6";
	cel27.appendChild(Date_of_Retirement);
	mycurrent_row.appendChild(cel27);

	tbody.appendChild(mycurrent_row);
	/** Increment Sequence Number */
	seq = seq + 1;

}

function ClearRow() {
	var tbody = document.getElementById("grid_body");
	var rowcount = tbody.rows.length;
	tbody.deleteRow(rowcount - 1);
	seq = seq - 1;
}

function funcSave() {
	document.getElementById("filter").value = "save";
	document.getElementById("RecordCount").value = seq;
	var tbody = document.getElementById("grid_body");
	var rowcount = tbody.rows.length;
	if (rowcount != 0) {
		return true;
	} else {
		alert("No Records Found to Insert...");
		return false;
	}	
}

function load_hrm(path) {
	// alert(path);
	document.frmCivil_Budget_Format_3.butView.disabled = false;
	document.frmCivil_Budget_Format_3.butSub.disabled = false;
	document.frmCivil_Budget_Format_3.butDelete.disabled = true;
	document.frmCivil_Budget_Format_3.butUpdate.disabled = true;

	document.getElementById("filter").value = "view";
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var RecordCount = 0;

	var url = path + "/Civil_Budget_Format_3?filter=load_hrm&cmbFinancialYear="
			+ cmbFinancialYear + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode
			+ "&cmbOffice_code=" + cmbOffice_code + "&RecordCount="
			+ RecordCount;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	document.getElementById("imgfld").style.visibility = "visible";
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}

function LoadData(path) {
	// alert(path);
	document.frmCivil_Budget_Format_3.butView.disabled = false;
	document.frmCivil_Budget_Format_3.butSub.disabled = true;
	document.frmCivil_Budget_Format_3.butDelete.disabled = false;
	document.frmCivil_Budget_Format_3.butUpdate.disabled = false;

	document.getElementById("filter").value = "view";
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var RecordCount = 0;

	var url = path + "/Civil_Budget_Format_3?filter=view&cmbFinancialYear="
			+ cmbFinancialYear + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode
			+ "&cmbOffice_code=" + cmbOffice_code + "&RecordCount="
			+ RecordCount;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	document.getElementById("imgfld").style.visibility = "visible";
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}

function load_hrm_View(baseResponse)
{
	var other_all=0;
	var last_total=0;
	var da_per=0;
	var incdate;
	var fintotal=0;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		/** Delete Existing Values from Grid */
		var tbody = document.getElementById("grid_body");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		var rankid = baseResponse.getElementsByTagName("rankid");
		var da_rate = baseResponse.getElementsByTagName("DA_RATE")[0].firstChild.nodeValue;
		// D.A @ 32% on Col.No.13
		document.getElementById("da_rate_id").innerHTML='<font color="red">'+"D.A @ "+ da_rate +"% on Col.No.13"+ '</font>';
		document.getElementById("da_rate").value=da_rate;
		
		// alert(da_rate);
		seq = 0;
		if (rankid.length != 0) {
			var item = new Array();
			var ranknamee=null;
			var dispname=null;
			var nopost=null;
			for ( var k = 0; k < rankid.length; k++) {
				item[0] = baseResponse.getElementsByTagName("rankid")[k].firstChild.nodeValue;
				if(item[0]=='null'){
					item[0]="";
				}
				item[1] = baseResponse.getElementsByTagName("rankname")[k].firstChild.nodeValue;
				if(item[1]=='null'){
					item[1]="";
				}
				item[2] = baseResponse.getElementsByTagName("PAY_BAND")[k].firstChild.nodeValue;
				if(item[2]=='null'){
					item[2]="0";
				}
				item[3] = baseResponse
						.getElementsByTagName("sanpost")[k].firstChild.nodeValue;
				if(item[3]=='null'){
					item[3]="0";
				}
				item[4] = baseResponse.getElementsByTagName("Incdate")[k].firstChild.nodeValue;
				if(item[4]=='null'){
					item[4]="0";
				}
				item[5] = baseResponse.getElementsByTagName("Increment_Amt")[k].firstChild.nodeValue;
				if(item[5]=='null'){
					item[5]="0";
				}
				item[6] = baseResponse.getElementsByTagName("Basic_Pay")[k].firstChild.nodeValue;
				if(item[6]=='null'){
					item[6]="0";
				}
				item[7] = baseResponse.getElementsByTagName("GRADEPAY")[k].firstChild.nodeValue;
				if(item[7]=='null'){
					item[7]="0";
				}
				item[8] = baseResponse.getElementsByTagName("HRA")[k].firstChild.nodeValue;
				if(item[8]=='null'){
					item[8]="0";
				}
				else
				{
					item[8]=(parseInt(item[8])*12);
				}
				item[9] = baseResponse.getElementsByTagName("MA")[k].firstChild.nodeValue;
				if(item[9]=='null'){
					item[9]="0";
				}
				else
				{
					item[9]=(parseInt(item[9])*12);
				}
				item[10] = baseResponse.getElementsByTagName("WA")[k].firstChild.nodeValue;
				if(item[10]=='null'){
					item[10]="0";
				}
				else
				{
					item[10]=(parseInt(item[10])*12);
				}
				item[11] = baseResponse.getElementsByTagName("DA_RATE")[k].firstChild.nodeValue;
				if(item[11]=='null'){
					item[11]="0";
				}
				
				item[12] = baseResponse.getElementsByTagName("rankname")[k].firstChild.nodeValue;
				
				
				if(ranknamee==item[12])
				{
					dispname="";
					ranknamee=item[12];
					nopost="";
				}
				else
				{
					ranknamee=item[12];
					dispname=item[12];
					nopost=item[3];
				}
				
				
				// document.getElementById("da_rate_id").value="";
				// document.getElementById("da_rate_id").value=item[11];
			
				// alert(item[11]);
				/** Create Table Row */
				var mycurrent_row = document.createElement("TR");
				mycurrent_row.id = seq;

				var cell2 = document.createElement("TD");
				var serialno = document.createElement("input");
				serialno.type = "hidden";
				serialno.name = "serialno" + seq;
				serialno.id = "serialno" + seq;
				serialno.value = slno;	
				cell2.appendChild(serialno);
				 var currentText=document.createTextNode(slno);
                 cell2.appendChild(currentText);
				mycurrent_row.appendChild(cell2);

				
				/** Name of Category */
				var cell2 = document.createElement("TD");
				var Name_of_Category = document.createElement("input");
				Name_of_Category.type = "hidden";
				Name_of_Category.name = "Name_of_Category" + seq;
				Name_of_Category.id = "Name_of_Category" + seq;
				Name_of_Category.value = item[0];
				Name_of_Category.size="7";
				Name_of_Category.setAttribute('readonly','readonly');
				cell2.appendChild(Name_of_Category);
				// var currentText=document.createTextNode(item[1]);
				var currentText=document.createTextNode(dispname);
				 currentText.size="10";
                 cell2.appendChild(currentText);
				mycurrent_row.appendChild(cell2);

				/** Time Scale of Pay (Specify O.G/S.G/Spl.Gr */
				var cell3 = document.createElement("TD");
				var Time_Scale_of_Pay = document.createElement("input");
				Time_Scale_of_Pay.type = "text";
				Time_Scale_of_Pay.name = "Time_Scale_of_Pay" + seq;
				Time_Scale_of_Pay.id = "Time_Scale_of_Pay" + seq;
				Time_Scale_of_Pay.value = item[2];
				Time_Scale_of_Pay.size="16";
				Time_Scale_of_Pay.setAttribute('readonly','readonly');
				cell3.appendChild(Time_Scale_of_Pay);
				mycurrent_row.appendChild(cell3);

				
				/** No of Sanctioned Posts */
				var cell2 = document.createElement("TD");
				var No_of_Sanctioned_Posts = document.createElement("input");
				No_of_Sanctioned_Posts.type = "hidden";
				No_of_Sanctioned_Posts.name = "No_of_Sanctioned_Posts" + seq;
				No_of_Sanctioned_Posts.id = "No_of_Sanctioned_Posts" + seq;
				No_of_Sanctioned_Posts.value = item[3];
				No_of_Sanctioned_Posts.size="4";
				No_of_Sanctioned_Posts.setAttribute('readonly','readonly');
				cell2.appendChild(No_of_Sanctioned_Posts);
				// var currentText=document.createTextNode(item[1]);
				var currentText=document.createTextNode(nopost);
				 currentText.size="5";
                 cell2.appendChild(currentText);
				mycurrent_row.appendChild(cell2);

				
				/** No of Sanctioned Posts */
//				var cell4 = document.createElement("TD");
//				var No_of_Sanctioned_Posts  = document.createElement("input");
//				No_of_Sanctioned_Posts.type = "text";
//				No_of_Sanctioned_Posts.name = "No_of_Sanctioned_Posts" + seq;
//				No_of_Sanctioned_Posts.id = "No_of_Sanctioned_Posts" + seq;
//				No_of_Sanctioned_Posts.value = item[3];
//				// No_of_Sanctioned_Posts.setAttribute('readonly','readonly');
//				No_of_Sanctioned_Posts.size = "6";
//				cell4.appendChild(No_of_Sanctioned_Posts );
//				mycurrent_row.appendChild(cell4);

				/** No of Incumbents in Roll */
				var cell5 = document.createElement("TD");
				var No_of_Incumbents_in_Roll = document.createElement("input");
				No_of_Incumbents_in_Roll.type = "text";
				No_of_Incumbents_in_Roll.name = "No_of_Incumbents_in_Roll" + seq;
				No_of_Incumbents_in_Roll.id = "No_of_Incumbents_in_Roll" + seq;
				No_of_Incumbents_in_Roll.value = "";
				No_of_Incumbents_in_Roll.size = "6";
				cell5.appendChild(No_of_Incumbents_in_Roll);
				mycurrent_row.appendChild(cell5);

				/** NO of Vacant Posts */
				var cell6 = document.createElement("TD");
				var No_of_Vacant_Posts = document.createElement("input");
				No_of_Vacant_Posts.type = "text";
				No_of_Vacant_Posts.name = "No_of_Vacant_Posts" + seq;
				No_of_Vacant_Posts.id = "No_of_Vacant_Posts" + seq;
				No_of_Vacant_Posts.value = "";
				No_of_Vacant_Posts.size = "6";
				cell6.appendChild(No_of_Vacant_Posts);
				mycurrent_row.appendChild(cell6);

				/** As on Begining of the Year - Basic Pay */
				var cell7 = document.createElement("TD");
				var Begining_of_the_Year_Basic_Pay = document.createElement("input");
				Begining_of_the_Year_Basic_Pay.type = "text";
				Begining_of_the_Year_Basic_Pay.name = "Begining_of_the_Year_Basic_Pay" + seq;
				Begining_of_the_Year_Basic_Pay.id = "Begining_of_the_Year_Basic_Pay" + seq;
				Begining_of_the_Year_Basic_Pay.value =item[6];
				// Begining_of_the_Year_Basic_Pay.setAttribute('readonly','readonly');
				Begining_of_the_Year_Basic_Pay.size = "6";
				Begining_of_the_Year_Basic_Pay.style.textAlign="right";
				cell7.appendChild(Begining_of_the_Year_Basic_Pay);
				mycurrent_row.appendChild(cell7);

				/** As on Begining of the Year - 50% B.P */
				var cell8 = document.createElement("TD");
				var Begining_of_the_Year_50_BP = document.createElement("input");
				Begining_of_the_Year_50_BP.type = "text";
				Begining_of_the_Year_50_BP.name = "Begining_of_the_Year_50_BP" + seq;
				Begining_of_the_Year_50_BP.id = "Begining_of_the_Year_50_BP" + seq;
				Begining_of_the_Year_50_BP.value =item[7];
				// Begining_of_the_Year_50_BP.setAttribute('readonly','readonly');
				Begining_of_the_Year_50_BP.size = "6";	
				Begining_of_the_Year_50_BP.style.textAlign="right";
				cell8.appendChild(Begining_of_the_Year_50_BP);
				mycurrent_row.appendChild(cell8);

				/** Increment Date */
				var cell9 = document.createElement("TD");
				var Increment_Date = document.createElement("input");
				Increment_Date.type = "text";
				Increment_Date.name = "Increment_Date" + seq;
				Increment_Date.id = "Increment_Date" + seq;
				Increment_Date.value =  item[4];
				Increment_Date.setAttribute('onblur',"addBoth("+seq+")");
				Increment_Date.size = "8";					
				cell9.appendChild(Increment_Date);
				mycurrent_row.appendChild(cell9);
				
				/** Increment Amount */
				var cell99 = document.createElement("TD");
				var Increment_Amount = document.createElement("input");
				Increment_Amount.type = "text";
				Increment_Amount.name = "Increment_Amount" + seq;
				Increment_Amount.id = "Increment_Amount" + seq;
				Increment_Amount.value =item[5];
				// Increment_Amount.setAttribute('readonly','readonly');
				Increment_Amount.size = "6";		
				Increment_Amount.style.textAlign="right";
				cell99.appendChild(Increment_Amount);
				mycurrent_row.appendChild(cell99);

				/** Pay After Increment - Basic Pay */
				var cell20 = document.createElement("TD");
				var After_Increment_Basic_Pay = document.createElement("input");
				After_Increment_Basic_Pay.type = "text";
				After_Increment_Basic_Pay.name = "After_Increment_Basic_Pay" + seq;
				After_Increment_Basic_Pay.id = "After_Increment_Basic_Pay" + seq;
				var afterBP=0;
				afterBP=parseInt(item[5])+parseInt(item[6]);
				After_Increment_Basic_Pay.value =afterBP;
				// After_Increment_Basic_Pay.setAttribute('readonly','readonly');
				After_Increment_Basic_Pay.size = "6";
				After_Increment_Basic_Pay.style.textAlign="right";
				cell20.appendChild(After_Increment_Basic_Pay);
				mycurrent_row.appendChild(cell20);

				/** Pay After Increment - 50% B.P */
				var cel21 = document.createElement("TD");
				var After_Increment_50_BP = document.createElement("input");
				After_Increment_50_BP.type = "text";
				After_Increment_50_BP.name = "After_Increment_50_BP" + seq;
				After_Increment_50_BP.id = "After_Increment_50_BP" + seq;
				After_Increment_50_BP.value =item[7];
				// After_Increment_50_BP.setAttribute('readonly','readonly');
				After_Increment_50_BP.size = "6";
				After_Increment_50_BP.style.textAlign="right";
				cel21.appendChild(After_Increment_50_BP);
				mycurrent_row.appendChild(cel21);

				

				/**
				 * Total Basic Pay plus 50% Dearness pay for 12 Months During
				 * the Year
				 */
				var cel23 = document.createElement("TD");
				var Basic_Pay_50_Dearness_pay  = document.createElement("input");
				Basic_Pay_50_Dearness_pay.type = "text";
				Basic_Pay_50_Dearness_pay.name = "Basic_Pay_50_Dearness_pay" + seq;
				Basic_Pay_50_Dearness_pay.id = "Basic_Pay_50_Dearness_pay" + seq;
				fintotal=0;
				if(item[4]!=0)
				{
					incdate=item[4].split("/");
					if(incdate[1]==4)
					{
						var be4ttl=(parseInt(item[6])+parseInt(item[7]));
						var afterttl=(parseInt(afterBP)+parseInt(item[7]));
						fintotal=(parseInt(be4ttl)+parseInt(afterttl));
					}
					if(incdate[1]==7)
					{
						var be4ttl=(parseInt(item[6])+parseInt(item[7]))*3;
						var afterttl=(parseInt(afterBP)+parseInt(item[7]))*9;
						fintotal=(parseInt(be4ttl)+parseInt(afterttl));
					}
					if(incdate[1]==10)
					{
						var be4ttl=(parseInt(item[6])+parseInt(item[7]))*6;
						var afterttl=(parseInt(afterBP)+parseInt(item[7]))*6;
						fintotal=(parseInt(be4ttl)+parseInt(afterttl));
					}
					if(incdate[1]==1)
					{
						var be4ttl=(parseInt(item[6])+parseInt(item[7]))*9;
						var afterttl=(parseInt(afterBP)+parseInt(item[7]))*3;
						fintotal=(parseInt(be4ttl)+parseInt(afterttl));
					}
				}
				Basic_Pay_50_Dearness_pay.value =fintotal;
				Basic_Pay_50_Dearness_pay.size = "6";
				Basic_Pay_50_Dearness_pay.style.textAlign="right";
				Basic_Pay_50_Dearness_pay.setAttribute('onblur',"addDA("+seq+")");
				cel23.appendChild(Basic_Pay_50_Dearness_pay );
				mycurrent_row.appendChild(cel23);
				tbody.appendChild(mycurrent_row);

				/** D.A @ 32% on Col.No.13 */
				var cel24 = document.createElement("TD");
				var DA_32_on_Col_No_13 = document.createElement("input");
				DA_32_on_Col_No_13.type = "text";
				DA_32_on_Col_No_13.name = "DA_32_on_Col_No_13" + seq;
				DA_32_on_Col_No_13.id = "DA_32_on_Col_No_13" + seq;
				da_per=0;
				da_per=((parseInt(item[11])*parseInt(fintotal))/100);
				
				
				DA_32_on_Col_No_13.value = da_per;
				DA_32_on_Col_No_13.style.textAlign="right";
				DA_32_on_Col_No_13.size = "6";
				cel24.appendChild(DA_32_on_Col_No_13);
				mycurrent_row.appendChild(cel24);
				/** HRA for 12 Months */
				var cell25 = document.createElement("TD");
				var HRA_Allowances = document.createElement("input");
				HRA_Allowances.type = "text";
				HRA_Allowances.name = "HRA_Allowances" + seq;
				HRA_Allowances.id = "HRA_Allowances" + seq;
				HRA_Allowances.value = item[8];
				HRA_Allowances.style.textAlign="right";
				HRA_Allowances.size = "6";
				cell25.appendChild(HRA_Allowances);
				mycurrent_row.appendChild(cell25);
				
				var cell25 = document.createElement("TD");
				var MA_Allowances = document.createElement("input");
				MA_Allowances.type = "text";
				MA_Allowances.name = "MA_Allowances" + seq;
				MA_Allowances.id = "MA_Allowances" + seq;
				MA_Allowances.value = item[9];
				MA_Allowances.style.textAlign="right";
				MA_Allowances.size = "6";
				cell25.appendChild(MA_Allowances);
				mycurrent_row.appendChild(cell25);
				
				var cell25 = document.createElement("TD");
				var WA_Allowances = document.createElement("input");
				WA_Allowances.type = "text";
				WA_Allowances.name = "WA_Allowances" + seq;
				WA_Allowances.id = "WA_Allowances" + seq;
				WA_Allowances.value = item[10];
				WA_Allowances.style.textAlign="right";
				WA_Allowances.size = "6";
				cell25.appendChild(WA_Allowances);
				mycurrent_row.appendChild(cell25);
				
				/** Other Allowances for 12 Months */
				var cell25 = document.createElement("TD");
				var Other_Allowances = document.createElement("input");
				Other_Allowances.type = "text";
				Other_Allowances.name = "Other_Allowances" + seq;
				Other_Allowances.id = "Other_Allowances" + seq;
				other_all=0;
				other_all=parseInt(item[8])+parseInt(item[9])+parseInt(item[10]);
				Other_Allowances.value =other_all;
				Other_Allowances.style.textAlign="right";
				Other_Allowances.size = "6";
				cell25.appendChild(Other_Allowances);
				mycurrent_row.appendChild(cell25);

				/** Total (9+10+11) */
				var cel26 = document.createElement("TD");
				var Total  = document.createElement("input");
				Total.type = "text";
				Total.name = "Total" + seq;
				Total.id = "Total" + seq;
				last_total=0;
				last_total=(parseInt(fintotal)+parseInt(da_per)+parseInt(other_all));
				Total.value = last_total;
				Total.style.textAlign="right";
				Total.size = "6";
				cel26.appendChild(Total);
				mycurrent_row.appendChild(cel26);

				/** Date of Retirement During the Year */
				var cel27 = document.createElement("TD");
				var Date_of_Retirement  = document.createElement("input");
				Date_of_Retirement.type = "text";
				Date_of_Retirement.name = "Date_of_Retirement" + seq;
				Date_of_Retirement.id = "Date_of_Retirement" + seq;
				Date_of_Retirement.value = "";
				Date_of_Retirement.size = "6";
				cel27.appendChild(Date_of_Retirement);
				mycurrent_row.appendChild(cel27);
				
				
				/** No of Sanctioned Posts */
//				var cell44 = document.createElement("TD");
//				var No_of_San_Posts_hidden  = document.createElement("input");
//				No_of_San_Posts_hidden.type = "text";
//				No_of_San_Posts_hidden.name = "No_of_SancPosts_hid" + seq;
//				No_of_San_Posts_hidden.id = "No_of_SancPosts_hid" + seq;
//			//	No_of_San_Posts_hidden.value = dispname;
//				No_of_San_Posts_hidden.value = item[1];
//				
//				// No_of_Sanctioned_Posts.setAttribute('readonly','readonly');
//				No_of_San_Posts_hidden.size = "6";
//				cell44.appendChild(No_of_San_Posts_hidden );
//				mycurrent_row.appendChild(cell44);
//				
				//if
				tbody.appendChild(mycurrent_row);

				/** Increment Sequence Number */
				seq = seq + 1;
				slno=slno+1;
			}
			document.getElementById("imgfld").style.visibility = "hidden";
		} else {
			document.getElementById("imgfld").style.visibility = "hidden";
			alert("Record Does Not Exist");
		}
		document.getElementById("RecordCount").value = seq;
	}else if(flag=="Freezed"){
		alert("Civil Budget Format-3 have Already Freezed");
	}  else {
		alert("Failed to Load Data");
	}
}

function LoadData_View(baseResponse) {
	// alert("RK");update
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		/** Delete Existing Values from Grid */
		var tbody = document.getElementById("grid_body");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		var r_no = baseResponse.getElementsByTagName("slno");
		var len = baseResponse.getElementsByTagName("slno").length;
		var da_rate = baseResponse.getElementsByTagName("DA_RATE")[0].firstChild.nodeValue;
		// D.A @ 32% on Col.No.13
		document.getElementById("da_rate_id").innerHTML='<font color="red">'+"D.A @ "+ da_rate +"% on Col.No.13"+ '</font>';
		
		document.getElementById("da_rate").value=da_rate;
		seq = 0;
		if (len != 0) {
			var item = new Array();
			var ranknamee=null;
			var dispname=null;
			var nopost=null;
			for ( var k = 0; k < r_no.length; k++) {
				item[0] = baseResponse.getElementsByTagName("Name_of_Category")[k].firstChild.nodeValue;
				if(item[0]=='null'){
					item[0]="";
				}
				item[1] = baseResponse.getElementsByTagName("Time_Scale_of_Pay")[k].firstChild.nodeValue;
				if(item[1]=='null'){
					item[1]="";
				}
				item[2] = baseResponse.getElementsByTagName("No_of_Sanctioned_Posts")[k].firstChild.nodeValue;
				if(item[2]=='null'){
					item[2]="0";
				}
				item[3] = baseResponse
						.getElementsByTagName("No_of_Incumbents_in_Roll")[k].firstChild.nodeValue;
				if(item[3]=='null'){
					item[3]="0";
				}
				item[4] = baseResponse.getElementsByTagName("No_of_Vacant_Posts")[k].firstChild.nodeValue;
				if(item[4]=='null'){
					item[4]="0";
				}
				item[5] = baseResponse.getElementsByTagName("Begining_of_the_Year_Basic_Pay")[k].firstChild.nodeValue;
				if(item[5]=='null'){
					item[5]="0";
				}
				item[6] = baseResponse
						.getElementsByTagName("Begining_of_the_Year_50_BP")[k].firstChild.nodeValue;
				if(item[6]=='null'){
					item[6]="0";
				}
				item[7] = baseResponse
						.getElementsByTagName("Increment_Date")[k].firstChild.nodeValue;
				if(item[7]=='null'){
					item[7]="";
				}
				item[8] = baseResponse.getElementsByTagName("Increment_Amount")[k].firstChild.nodeValue;
				if(item[8]=='null'){
					item[8]="0";
				}
				item[9] = baseResponse.getElementsByTagName("After_Increment_Basic_Pay")[k].firstChild.nodeValue;
				if(item[9]=='null'){
					item[9]="0";
				}
				item[10] = baseResponse
						.getElementsByTagName("After_Increment_50_BP")[k].firstChild.nodeValue;
				if(item[10]=='null'){
					item[10]="0";
				}
				item[11] = baseResponse
						.getElementsByTagName("DA_RATE")[k].firstChild.nodeValue;
				if(item[11]=='null'){
					item[11]="0";
				}
				item[12] = baseResponse.getElementsByTagName("Basic_Pay_50_Dearness_pay")[k].firstChild.nodeValue;
				if(item[12]=='null'){
					item[12]="0";
				}
				item[13] = baseResponse.getElementsByTagName("DA_32_on_Col_No_13")[k].firstChild.nodeValue;
				if(item[13]=='null'){
					item[13]="0";
				}
				item[14] = baseResponse
						.getElementsByTagName("Other_Allowances")[k].firstChild.nodeValue;
				if(item[14]=='null'){
					item[14]="0";
				}
				item[15] = baseResponse
						.getElementsByTagName("Total")[k].firstChild.nodeValue;
				if(item[15]=='null'){
					item[15]="0";
				}
				item[16] = baseResponse
						.getElementsByTagName("Date_of_Retirement")[k].firstChild.nodeValue;
				if(item[16]=='null'){
					item[16]="";
				}				
				item[18] = baseResponse.getElementsByTagName("slno")[k].firstChild.nodeValue;
				if(item[18]=='null'){
					item[18]="";
				}
				item[19] = baseResponse.getElementsByTagName("cat_desc")[k].firstChild.nodeValue;
				if(item[19]=='null'){
					item[19]="";
				}
				item[20] = baseResponse.getElementsByTagName("hra")[k].firstChild.nodeValue;
				if(item[20]=='null'){
					item[20]="";
				}
				item[21] = baseResponse.getElementsByTagName("ma")[k].firstChild.nodeValue;
				if(item[21]=='null'){
					item[21]="";
				}
				item[22] = baseResponse.getElementsByTagName("wa")[k].firstChild.nodeValue;
				if(item[22]=='null'){
					item[22]="";
				}
				item[23] = baseResponse.getElementsByTagName("DA_RATE")[k].firstChild.nodeValue;
				if(item[23]=='null'){
					item[23]="";
				}
				
				
				//item[24] = baseResponse.getElementsByTagName("cat_desc")[k].firstChild.nodeValue;
				if(ranknamee==item[19])
				{
					dispname="";
					ranknamee=item[19];
					nopost="";
				}
				else
				{
					ranknamee=item[19];
					dispname=item[19];
					nopost=item[2];
				}
				// update
				
				/** Create Table Row */
				var mycurrent_row = document.createElement("TR");
				mycurrent_row.id = seq;
				
				var cell2 = document.createElement("TD");
				var serialno = document.createElement("input");
				serialno.type = "hidden";
				serialno.name = "serialno" + seq;
				serialno.id = "serialno" + seq;
				serialno.value = item[18];	
				cell2.appendChild(serialno);
				 var currentText=document.createTextNode(item[18]);
				 cell2.appendChild(currentText);
				mycurrent_row.appendChild(cell2);


				var cell2 = document.createElement("TD");
				var Name_of_Category = document.createElement("input");
				Name_of_Category.type = "hidden";
				Name_of_Category.name = "Name_of_Category" + seq;
				Name_of_Category.id = "Name_of_Category" + seq;
				Name_of_Category.value = item[0];
				Name_of_Category.size="6";
				// Name_of_Category.setAttribute('readonly','readonly');
				cell2.appendChild(Name_of_Category);
				// var currentText=document.createTextNode(item[19]);
				 var currentText=document.createTextNode(dispname);
				 cell2.appendChild(currentText);
				mycurrent_row.appendChild(cell2);

				/** Time Scale of Pay (Specify O.G/S.G/Spl.Gr */
				var cell3 = document.createElement("TD");
				var Time_Scale_of_Pay = document.createElement("input");
				Time_Scale_of_Pay.type = "text";
				Time_Scale_of_Pay.name = "Time_Scale_of_Pay" + seq;
				Time_Scale_of_Pay.id = "Time_Scale_of_Pay" + seq;
				Time_Scale_of_Pay.value = item[1];
				Time_Scale_of_Pay.size="7";
				cell3.appendChild(Time_Scale_of_Pay);
				mycurrent_row.appendChild(cell3);

				
				
				/** No of Sanctioned Posts */
				var cell2 = document.createElement("TD");
				var No_of_Sanctioned_Posts = document.createElement("input");
				No_of_Sanctioned_Posts.type = "hidden";
				No_of_Sanctioned_Posts.name = "No_of_Sanctioned_Posts" + seq;
				No_of_Sanctioned_Posts.id = "No_of_Sanctioned_Posts" + seq;
				No_of_Sanctioned_Posts.value = item[2];
				No_of_Sanctioned_Posts.size="4";
				No_of_Sanctioned_Posts.setAttribute('readonly','readonly');
				cell2.appendChild(No_of_Sanctioned_Posts);
				// var currentText=document.createTextNode(item[1]);
				var currentText=document.createTextNode(nopost);
				
				 currentText.size="5";
                 cell2.appendChild(currentText);
				mycurrent_row.appendChild(cell2);
				
				/** No of Sanctioned Posts *//*
				var cell4 = document.createElement("TD");
				var No_of_Sanctioned_Posts  = document.createElement("input");
				No_of_Sanctioned_Posts.type = "text";
				No_of_Sanctioned_Posts.name = "No_of_Sanctioned_Posts" + seq;
				No_of_Sanctioned_Posts.id = "No_of_Sanctioned_Posts" + seq;
				No_of_Sanctioned_Posts.value = item[2];
				No_of_Sanctioned_Posts.size = "6";
				cell4.appendChild(No_of_Sanctioned_Posts );
				mycurrent_row.appendChild(cell4);*/

				/** No of Incumbents in Roll */
				var cell5 = document.createElement("TD");
				var No_of_Incumbents_in_Roll = document.createElement("input");
				No_of_Incumbents_in_Roll.type = "text";
				No_of_Incumbents_in_Roll.name = "No_of_Incumbents_in_Roll" + seq;
				No_of_Incumbents_in_Roll.id = "No_of_Incumbents_in_Roll" + seq;
				No_of_Incumbents_in_Roll.value = item[3];
				No_of_Incumbents_in_Roll.size = "6";
				cell5.appendChild(No_of_Incumbents_in_Roll);
				mycurrent_row.appendChild(cell5);
				// update
				/** NO of Vacant Posts */
				var cell6 = document.createElement("TD");
				var No_of_Vacant_Posts = document.createElement("input");
				No_of_Vacant_Posts.type = "text";
				No_of_Vacant_Posts.name = "No_of_Vacant_Posts" + seq;
				No_of_Vacant_Posts.id = "No_of_Vacant_Posts" + seq;
				No_of_Vacant_Posts.value = item[4];
				No_of_Vacant_Posts.size = "6";
				cell6.appendChild(No_of_Vacant_Posts);
				mycurrent_row.appendChild(cell6);

				/** As on Begining of the Year - Basic Pay */
				var cell7 = document.createElement("TD");
				var Begining_of_the_Year_Basic_Pay = document.createElement("input");
				Begining_of_the_Year_Basic_Pay.type = "text";
				Begining_of_the_Year_Basic_Pay.name = "Begining_of_the_Year_Basic_Pay" + seq;
				Begining_of_the_Year_Basic_Pay.id = "Begining_of_the_Year_Basic_Pay" + seq;
				Begining_of_the_Year_Basic_Pay.value = item[5]+"0";
				Begining_of_the_Year_Basic_Pay.size = "6";
				Begining_of_the_Year_Basic_Pay.style.textAlign="right";
				cell7.appendChild(Begining_of_the_Year_Basic_Pay);
				mycurrent_row.appendChild(cell7);

				/** As on Begining of the Year - 50% B.P */
				var cell8 = document.createElement("TD");
				var Begining_of_the_Year_50_BP = document.createElement("input");
				Begining_of_the_Year_50_BP.type = "text";
				Begining_of_the_Year_50_BP.name = "Begining_of_the_Year_50_BP" + seq;
				Begining_of_the_Year_50_BP.id = "Begining_of_the_Year_50_BP" + seq;
				Begining_of_the_Year_50_BP.value = item[6]+"0";
				Begining_of_the_Year_50_BP.size = "6";	
				Begining_of_the_Year_50_BP.style.textAlign="right";
				cell8.appendChild(Begining_of_the_Year_50_BP);
				mycurrent_row.appendChild(cell8);

				/** Increment Date */
				var cell9 = document.createElement("TD");
				var Increment_Date = document.createElement("input");
				Increment_Date.type = "text";
				Increment_Date.name = "Increment_Date" + seq;
				Increment_Date.id = "Increment_Date" + seq;
				Increment_Date.value = item[7];
				Increment_Date.size = "6";					
				cell9.appendChild(Increment_Date);
				mycurrent_row.appendChild(cell9);
				
				/** Increment Amount */
				var cell99 = document.createElement("TD");
				var Increment_Amount = document.createElement("input");
				Increment_Amount.type = "text";
				Increment_Amount.name = "Increment_Amount" + seq;
				Increment_Amount.id = "Increment_Amount" + seq;
				Increment_Amount.value = item[8]+"0";
				Increment_Amount.size = "6";		
				Increment_Amount.style.textAlign="right";
				cell99.appendChild(Increment_Amount);
				mycurrent_row.appendChild(cell99);

				/** Pay After Increment - Basic Pay */
				var cell20 = document.createElement("TD");
				var After_Increment_Basic_Pay = document.createElement("input");
				After_Increment_Basic_Pay.type = "text";
				After_Increment_Basic_Pay.name = "After_Increment_Basic_Pay" + seq;
				After_Increment_Basic_Pay.id = "After_Increment_Basic_Pay" + seq;
				After_Increment_Basic_Pay.value = item[9]+"0";	
				After_Increment_Basic_Pay.size = "6";
				After_Increment_Basic_Pay.style.textAlign="right";
				cell20.appendChild(After_Increment_Basic_Pay);
				mycurrent_row.appendChild(cell20);

				/** Pay After Increment - 50% B.P */
				var cel21 = document.createElement("TD");
				var After_Increment_50_BP = document.createElement("input");
				After_Increment_50_BP.type = "text";
				After_Increment_50_BP.name = "After_Increment_50_BP" + seq;
				After_Increment_50_BP.id = "After_Increment_50_BP" + seq;
				After_Increment_50_BP.value = item[10]+"0";
				After_Increment_50_BP.size = "6";
				After_Increment_50_BP.style.textAlign="right";
				cel21.appendChild(After_Increment_50_BP);
				mycurrent_row.appendChild(cel21);

				

				/**
				 * Total Basic Pay plus 50% Dearness pay for 12 Months During
				 * the Year
				 */
				var cel23 = document.createElement("TD");
				var Basic_Pay_50_Dearness_pay  = document.createElement("input");
				Basic_Pay_50_Dearness_pay.type = "text";
				Basic_Pay_50_Dearness_pay.name = "Basic_Pay_50_Dearness_pay" + seq;
				Basic_Pay_50_Dearness_pay.id = "Basic_Pay_50_Dearness_pay" + seq;
				Basic_Pay_50_Dearness_pay.value = item[12]+"0";
				Basic_Pay_50_Dearness_pay.size = "6";
				Basic_Pay_50_Dearness_pay.style.textAlign="right";
				cel23.appendChild(Basic_Pay_50_Dearness_pay );
				mycurrent_row.appendChild(cel23);
				tbody.appendChild(mycurrent_row);

				/** D.A @ 32% on Col.No.13 */
				var cel24 = document.createElement("TD");
				var DA_32_on_Col_No_13 = document.createElement("input");
				DA_32_on_Col_No_13.type = "text";
				DA_32_on_Col_No_13.name = "DA_32_on_Col_No_13" + seq;
				DA_32_on_Col_No_13.id = "DA_32_on_Col_No_13" + seq;
				DA_32_on_Col_No_13.value = item[13]+"0";
				DA_32_on_Col_No_13.style.textAlign="right";
				DA_32_on_Col_No_13.size = "6";
				cel24.appendChild(DA_32_on_Col_No_13);
				mycurrent_row.appendChild(cel24);

				var cel24 = document.createElement("TD");
				var hra_Allowances = document.createElement("input");
				hra_Allowances.type = "text";
				hra_Allowances.name = "hra_Allowances" + seq;
				hra_Allowances.id = "hra_Allowances" + seq;
				hra_Allowances.value = item[20];
				hra_Allowances.style.textAlign="right";
				hra_Allowances.size = "6";
				cel24.appendChild(hra_Allowances);
				mycurrent_row.appendChild(cel24);
				
				var cel24 = document.createElement("TD");
				var ma_Allowances = document.createElement("input");
				ma_Allowances.type = "text";
				ma_Allowances.name = "ma_Allowances" + seq;
				ma_Allowances.id = "ma_Allowances" + seq;
				ma_Allowances.value = item[21];
				ma_Allowances.style.textAlign="right";
				ma_Allowances.size = "6";
				cel24.appendChild(ma_Allowances);
				mycurrent_row.appendChild(cel24);
				
				var cel24 = document.createElement("TD");
				var wa_Allowances = document.createElement("input");
				wa_Allowances.type = "text";
				wa_Allowances.name = "wa_Allowances" + seq;
				wa_Allowances.id = "wa_Allowances" + seq;
				wa_Allowances.value = item[22];
				wa_Allowances.style.textAlign="right";
				wa_Allowances.size = "6";
				cel24.appendChild(wa_Allowances);
				mycurrent_row.appendChild(cel24);
				
				/** Other Allowances for 12 Months */
				var cell25 = document.createElement("TD");
				var Other_Allowances = document.createElement("input");
				Other_Allowances.type = "text";
				Other_Allowances.name = "Other_Allowances" + seq;
				Other_Allowances.id = "Other_Allowances" + seq;
				Other_Allowances.value = item[14]+"0";
				Other_Allowances.style.textAlign="right";
				Other_Allowances.size = "6";
				cell25.appendChild(Other_Allowances);
				mycurrent_row.appendChild(cell25);

				/** Total (9+10+11) */
				var cel26 = document.createElement("TD");
				var Total  = document.createElement("input");
				Total.type = "text";
				Total.name = "Total" + seq;
				Total.id = "Total" + seq;
				Total.value = item[15];
				Total.style.textAlign="right";
				Total.size = "6";
				cel26.appendChild(Total );
				mycurrent_row.appendChild(cel26);

				/** Date of Retirement During the Year */
				var cel27 = document.createElement("TD");
				var Date_of_Retirement  = document.createElement("input");
				Date_of_Retirement.type = "text";
				Date_of_Retirement.name = "Date_of_Retirement" + seq;
				Date_of_Retirement.id = "Date_of_Retirement" + seq;
				Date_of_Retirement.value = item[16];
				Date_of_Retirement.size = "6";
				cel27.appendChild(Date_of_Retirement);
				mycurrent_row.appendChild(cel27);
				
				/** Sl No */
				var slno_db = document.createElement("input");
				slno_db.setAttribute("type", "hidden");
				slno_db.setAttribute("value", item[18]);
				slno_db.setAttribute("name", "slno_db" + seq);
				slno_db.setAttribute("id", "slno_db" + seq);
				document.getElementById("frmCivil_Budget_Format_3").appendChild(slno_db);
				
				

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
	}else if(flag=="Freezed"){
		alert("Civil Budget Format-3 have Already Freezed");
	}  else {
		alert("Failed to Load Data");
	}
}
function funcUpdate() {
	document.getElementById("filter").value = "update";
	document.getElementById("RecordCount").value = seq;
	return true;
}

function funcDelete() {
	var r = confirm("Are U Sure to Continue?");
	if (r == true) {
		document.getElementById("filter").value = "delete";
		var tbody = document.getElementById("grid_body");
		var rowcount = tbody.rows.length;
		if (rowcount != 0) {
			for ( var i = 0; i < rowcount; i++) {
				
					var slno_db1 = document.createElement("input");
					slno_db1.setAttribute("type", "hidden");
					slno_db1.setAttribute("value", i);
					slno_db1.setAttribute("name", "slno_db1" + i);
					slno_db1.setAttribute("id", "slno_db1" + i);
					document.getElementById("frmCivil_Budget_Format_3")
							.appendChild(slno_db1);
				
// else {
// var slno_db1 = document.createElement("input");
// slno_db1.setAttribute("type", "hidden");
// slno_db1.setAttribute("value", "-1");
// slno_db1.setAttribute("name", "slno_db1" + i);
// slno_db1.setAttribute("id", "slno_db1" + i);
// document.getElementById("frmCivil_Budget_Format_3")
// .appendChild(slno_db1);
// }
				// alert(document.getElementById("slno_db1" + i).value);
			}
			return true;
		} else {
			alert("No Records Found to Delete...");
			return false;
		}

	} else {
		return false;
	}
}
function clrForm() {
	seq=0;
	/** Delete Existing Values from Grid */
	var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}

	LoadAccountingUnitID('LIST_ALL_UNITS');	
	document.getElementById("imgfld").style.visibility = "hidden";
	document.frmCivil_Budget_Format_3.butView.disabled = false;
	document.frmCivil_Budget_Format_3.butSub.disabled = false;
	document.frmCivil_Budget_Format_3.butDelete.disabled = true;
	document.frmCivil_Budget_Format_3.butUpdate.disabled = true;
}
function addBoth(par)
{
	
	var date_one=document.getElementById("Increment_Date"+par).value;
	checkdt(date_one);
	var Be_Basic_Pay=document.getElementById("Begining_of_the_Year_Basic_Pay"+par).value;
	var fifty_bp=document.getElementById("Begining_of_the_Year_50_BP"+par).value;
	
	var afterBP=document.getElementById("After_Increment_Basic_Pay"+par).value;
	
	
	if(date_one!=0)
	{
		var incdate=date_one.split("/");
		if(incdate[1]==4)
		{
			var be4ttl=(parseInt(Be_Basic_Pay)+parseInt(fifty_bp));
			var afterttl=(parseInt(afterBP)+parseInt(fifty_bp));
			fintotal=(parseInt(be4ttl)+parseInt(afterttl));
		}
		if(incdate[1]==7)
		{
			var be4ttl=(parseInt(Be_Basic_Pay)+parseInt(fifty_bp))*3;
			var afterttl=(parseInt(afterBP)+parseInt(fifty_bp))*9;
			fintotal=(parseInt(be4ttl)+parseInt(afterttl));
		}
		if(incdate[1]==10)
		{
			
			var be4ttl=(parseInt(Be_Basic_Pay)+parseInt(fifty_bp))*6;
			var afterttl=(parseInt(afterBP)+parseInt(fifty_bp))*6;
			fintotal=(parseInt(be4ttl)+parseInt(afterttl));
			
		}
		if(incdate[1]==1)
		{
			var be4ttl=(parseInt(Be_Basic_Pay)+parseInt(fifty_bp))*9;
			var afterttl=(parseInt(afterBP)+parseInt(fifty_bp))*3;
			fintotal=(parseInt(be4ttl)+parseInt(afterttl));
		}
		document.getElementById("Basic_Pay_50_Dearness_pay"+par).value=fintotal;
		addDA(par);
	}
	
}

function addDA(par)
{
	
	var da_per=0;
	var fintotal=document.getElementById("Basic_Pay_50_Dearness_pay"+par).value;
	var da_rates=document.getElementById("da_rate").value;
	da_per=((parseInt(da_rates)*parseInt(fintotal))/100);
	document.getElementById("DA_32_on_Col_No_13"+par).value=da_per;
	
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

function isValidDate(dateStr) {
	  
	  // Checks for the following valid date formats:
	  // MM/DD/YYYY
	  // Also separates date into month, day, and year variables
	  var datePat = /^(\d{2,2})(\/)(\d{2,2})\2(\d{4}|\d{4})$/;
	  
	  var matchArray = dateStr.match(datePat); // is the format ok?
	  if (matchArray == null) {
	   alert("Date must be in MM/DD/YYYY format")
	   return false;
	  }
	  
	  month = matchArray[3]; // parse date into variables
	  day = matchArray[1];
	  year = matchArray[4];
	  if (month < 1 || month > 12) { // check month range
	   alert("Month must be between 1 and 12");
	   return false;
	  }
	  if (day < 1 || day > 31) {
	   alert("Day must be between 1 and 31");
	   return false;
	  }
	  if ((month==4 || month==6 || month==9 || month==11) && day==31) {
	   alert("Month "+month+" doesn't have 31 days!")
	   return false;
	  }
	  if (month == 2) { // check for february 29th
	   var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
	   if (day>29 || (day==29 && !isleap)) {
	    alert("February " + year + " doesn't have " + day + " days!");
	    return false;
	     }
	  }
	  return true;  // date is valid
	 }




function checkdt(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
//        try{
//        var f=DateFormat(t,c,event,true,'3');
//        }catch(e){
         
       ///New code implemented on 28-03-2019  for year 2019 wrongly displayed 201 
         try{
             var f=isValidDate(c);
            }
        catch(e){
         
         
        // exception start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            // alert(currentYear == getCurrentYear() && currentMonth ==
			// getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear<1970)
            {
            
                    alert('Entered date should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
          
            t.value=c;
             if(err!=0)
                {
                    t.value="";
                    return false;
                }
            return true;
        
        
        // exception end
        
        }
        if( f==true)
        {
            // alert(f);
            // t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            // alert(currentYear == getCurrentYear() && currentMonth ==
			// getCurrentMonth() && currenDay > getCurrentDay());
         
            if(currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
                      
            t.value=c;
           
            return true;
            
        }
        else
        {
                if(err!=0)
                {
                    t.value="";
                    return false;
                }
        }
            
    }
    else
    {
            alert('Date format  should be (dd/mm/yyyy)');
            t.value="";
            // t.focus();
            return false
    }
    
}


function exitfun() {
	window.close();
}


