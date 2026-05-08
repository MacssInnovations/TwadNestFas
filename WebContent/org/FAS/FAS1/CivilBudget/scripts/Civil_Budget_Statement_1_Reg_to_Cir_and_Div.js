//	Civil_Budget_Statement_1_Reg_to_Cir_and_Div	//
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

function callAmt(path)
{
var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
var cmbOffice_code=document.getElementById("cmbOffice_code").value;
var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;

var cmbStatementName=document.getElementById("cmbStatementName").value;
var statementGp=document.getElementById("statementGp").value;
	
	var url = path + "/Civil_Budget_Statement_1?command=loadAmt" +
			"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"" +
			"&cmbFinancialYear="+cmbFinancialYear+"" +
			"&cmbStatementName="+cmbStatementName+"&statementGp="+statementGp;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);	
}

function reallocation_fn(path)
{
	
	var cmbStatementName=document.getElementById("cmbStatementName").value;
	var statementGp=document.getElementById("statementGp").value;
	var head_code=document.getElementById("head_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	
	
	var url = path + "/Civil_Budget_Statement_1_Reg_to_Cir_and_Div?command=head_test" +
	"&cmbStatementName="+cmbStatementName+"&statementGp="+statementGp+"" +
	"&head_code="+head_code+"&cmbFinancialYear="+cmbFinancialYear+"&cmbOffice_code="+cmbOffice_code;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
	manipulate1(xmlrequest);
	};
	xmlrequest.send(null);	
}

function callStatement(path)
{

	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;

	var cmbStatementName=document.getElementById("cmbStatementName").value;
	var statementGp=document.getElementById("statementGp").value;
		
		var url = path + "/Civil_Budget_Statement_1_Reg_to_Cir_and_Div?command=callstatement" +
				"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"" +
				"&cmbFinancialYear="+cmbFinancialYear+"" +
				"&cmbStatementName="+cmbStatementName+"&statementGp="+statementGp;
		//alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("GET", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate1(xmlrequest);
		};
		xmlrequest.send(null);
}  

function callHead()
{
	
var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
var cmbOffice_code=document.getElementById("cmbOffice_code").value;
var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;

var cmbStatementName=document.getElementById("cmbStatementName").value;
var statementGp=document.getElementById("statementGp").value;
	
	var url ="../../../../../Civil_Budget_Statement_1?command=callHead" +
			"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"" +
			"&cmbFinancialYear="+cmbFinancialYear+""+
			"&cmbStatementName="+cmbStatementName+"&statementGp="+statementGp;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);	
}
function CheckFreeze()
{
	
var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
var cmbOffice_code=document.getElementById("cmbOffice_code").value;
var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;

var cmbStatementName=document.getElementById("cmbStatementName").value;
//var statementGp=document.getElementById("statementGp").value;
	
	var url ="../../../../../Civil_Budget_Statement_1_Reg_to_Cir_and_Div?command=CheckFreeze" +
			"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"" +
			"&cmbFinancialYear="+cmbFinancialYear+""+
			"&cmbStatementName="+cmbStatementName;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);	
}

function loadGrid(path)
{
	var head_code;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	var cmbStatementName=document.getElementById("cmbStatementName").value;
	var statementGp=document.getElementById("statementGp").value;
	
	if(document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[0].checked==true)
	{
		head_code=document.getElementById("head_code").value;
	}
	else
	{
		head_code=0;
	}
	
	
	
	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.reserveid[0].checked=false;
	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.reserveid[1].checked=true;
	
	var url = path + "/Civil_Budget_Statement_1_Reg_to_Cir_and_Div?command=load_grid&cmbOffice_code="
	+cmbOffice_code+"&cmbFinancialYear="+cmbFinancialYear+"&cmbStatementName="
	+cmbStatementName+"&statementGp="+statementGp+"&head_code="+head_code;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);	
}

function chooseGroup(path)
{

	var statement=document.getElementById("cmbStatementName").value;
	
	var url = path + "/Civil_Budget_Statement_1?command=groupch&statement="+statement;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);	
}

function manipulate1(xmlrequest) {
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
				
				update_row(baseResponse);
			} else if (command == "Edit") {
				Edit1(baseResponse);
			} else if (command == "get") {
				LoadGrid1(baseResponse);
			} else if (command == "LoadData") {
				LoadData_View1(baseResponse);
			} else if (command == "getStatementName") {
				getStatementName1(baseResponse);
			} else if (command == "LoadGrid_Head") {
				LoadGrid_Head1(baseResponse);
			} else if (command == "HOState") {
				loadHeadState(baseResponse);
			}
			else if (command == "groupch")
			{
				groupch_load(baseResponse);
			}
			else if (command == "loadAmt")
			{
				loadAmt_load(baseResponse);
			}
			else if (command == "callHead")
			{
				Range_Of(baseResponse);
			}
			else if (command=="head_test")
			{
				head_test_res(baseResponse);
			}
			else if (command=="load_grid")
			{
				load_grid_res(baseResponse);
			}
			else if (command=="callstatement")
			{
				callstatement_res(baseResponse);
			}
			else if (command=="CheckFreeze")
			{
				CheckFreeze_res(baseResponse);
			}
			
		}
	}
}
function CheckFreeze_res(baseResponse)
{
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="Freeze")
	{
		
		alert("Allocation is already Frozen ");
		document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butSub.disabled = true;
		document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butDelete.disabled = true;
		document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butUpdate.disabled = true;
	}
	else if(flag=="NotFreeze"){
		//alert("Not Freeze");
	}else{
		alert("Error ");
	}
}
function update_row(baseResponse)
{
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="success")
	{
		alert("Record Updated Successfully");
		var tbody = document.getElementById("grid_body");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}
	}
	else
	{
		alert("Record Not Updated");
		return false;
	}
}

function callstatement_res(baseResponse)
{
	var re_by_region=0;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="already")
				{
					var Allocation_Type=baseResponse.getElementsByTagName("Allocation_Type")[0].firstChild.nodeValue;
					var regAmt=baseResponse.getElementsByTagName("regAmt")[0].firstChild.nodeValue;
					if(Allocation_Type=="G")
					{
						
						//uncheck radio button
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[1].checked=true;
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[0].checked=false;
						
						//disable
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[0].disabled=true;
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[1].disabled=false;
						
						document.getElementById("head_div1").style.display="none";
						document.getElementById("head_div2").style.display="none";
						
					}
					else if(Allocation_Type=="H")
					{
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[0].checked=true;
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[1].checked=false;
						callHead();
						//disable
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[0].disabled=false;
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[1].disabled=true;
						
						document.getElementById("head_div1").style.display="block";
						document.getElementById("head_div2").style.display="block";
					}
					var unit_allocation=baseResponse.getElementsByTagName("unit_allocation")[0].firstChild.nodeValue;
					if(unit_allocation=="L")
					{
						
						//re_by_region=(parseInt(regAmt)*100000);
						//Lakshmi
						re_by_region=(Math.round(regAmt)*100000);
						document.getElementById("reallocation").value=re_by_region;
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[0].checked=false;
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[1].checked=false;
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[2].checked=true;
						
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[0].disabled=true;
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[1].disabled=true;
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[2].disabled=false;
						changeHeadingL();
					}
					else if(unit_allocation=="T")
					{
						//re_by_region=(parseInt(regAmt)*1000);
						//Lakshmi
						re_by_region=(Math.round(regAmt)*1000);
						document.getElementById("reallocation").value=re_by_region;
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[0].checked=false;
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[1].checked=true;
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[2].checked=false;
						
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[0].disabled=true;
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[1].disabled=false;
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[2].disabled=true;
						changeHeadingT();
					}
					else if(unit_allocation=="R")
					{
						//re_by_region=(parseInt(regAmt)*1);
						//Lakshmi
						re_by_region=(Math.round(regAmt)*1);
						document.getElementById("reallocation").value=re_by_region;
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[0].checked=true;
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[1].checked=false;
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[2].checked=false;
						
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[0].disabled=false;
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[1].disabled=true;
						document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[2].disabled=true;
						changeHeadingR();
					}
					// Total Allocation By HO in Rupees
					// var Allocation_By_HO_in_rs=document.getElementById("hoamountinrs").value;
					// Balance Available
					// document.getElementById("balanceavail").value=(parseInt(Allocation_By_HO_in_rs))-(parseInt(re_by_region));
				
			
		
	}else{
		document.getElementById("reallocation").value="";
		document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[0].checked=false;
		document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[1].checked=false;
		
		document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[0].disabled=false;
		document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[1].disabled=false;
		
		document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[0].disabled=false;
		document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[1].disabled=false;
		document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[2].disabled=false;
		
		document.getElementById("head_div1").style.display="block";
		document.getElementById("head_div2").style.display="block";
	}
}

function head_test_res(baseResponse)
{
	//document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupDesc.value="";
	//document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupType.value="";
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="success")
	{
		 var tt=baseResponse.getElementsByTagName("grouptype")[0].firstChild.nodeValue;
		document.getElementById("groupType").value=tt;
		 var tdesc=baseResponse.getElementsByTagName("groupdesc")[0].firstChild.nodeValue;
			document.getElementById("groupDesc").value=tdesc;
	}else{
		alert("Error in choosing Group Type");
	}
}

function addRow(baseResponse){
	var flag = baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue;
	if(flag=="success"){
		alert("Records add successfully");
	}else{
		alert("Records not Add");
	}
	clrForm1();
}

function initialLoad(path) {
	/** Delete Existing Values from Grid */
	var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
	

	//document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butView.disabled = false;
	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butSub.disabled = false;
	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butDelete.disabled = true;
	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butUpdate.disabled = true;

	var url = path + "/Civil_Budget_Statement_1?command=getStatementName";
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);
}

function LoadGrid_Head(path) {
	var cmbStatementName = document.getElementById("cmbStatementName").value;
	if (cmbStatementName == "") {
		alert("Select Statement Name in the Field");
		document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.cmbStatementName
				.focus();
	} else {
		var url = path
				+ "/Civil_Budget_Statement_1_Reg_to_Cir_and_Div?command=LoadGrid_Head&cmbStatementName="
				+ cmbStatementName;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("GET", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate1(xmlrequest);
		};

		xmlrequest.send(null);
	}

}
var item1 = new Array();
var acchead1;
var acchead_value1;
function LoadGrid_Head1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		/** Delete Existing Values from Grid */
		var tbody = document.getElementById("grid_Head");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		var tbody1 = document.getElementById("grid_body");
		var t1 = 0;
		for (t1 = tbody1.rows.length - 1; t1 >= 0; t1--) {
			tbody1.deleteRow(0);
		}

		var r_no = baseResponse.getElementsByTagName("STATEMENT_GROUP_NO");
		seq = 0;
		var rc = 0;
		var coloumn;
		var column1;
		var Acc_Hd_Code;
		var acc_Hd_Code_Value;
		var item = new Array();
		var mycount=0;
		for ( var k = 0; k < r_no.length; k++) {

			item[0] = baseResponse.getElementsByTagName("STATEMENT_GROUP_NO")[k].firstChild.nodeValue;
			item[1] = baseResponse.getElementsByTagName("STATEMENT_GROUP_DESC")[k].firstChild.nodeValue;
			item[2] = baseResponse.getElementsByTagName("ACC_HD_CODE")[k].firstChild.nodeValue;
			item[3] = baseResponse.getElementsByTagName("ACC_HD_CODE_VALUE")[k].firstChild.nodeValue;
			if (k == 0) {
				column1=item[0];
				coloumn = item[1];			
				Acc_Hd_Code = item[2];
				acc_Hd_Code_Value=item[3];
			} else {
				column1 = column1 + ",," + item[0];
				coloumn = coloumn + ",," + item[1];
				Acc_Hd_Code = Acc_Hd_Code + ",," + item[2];				
				acc_Hd_Code_Value=acc_Hd_Code_Value + ",," + item[3];
			}
			/** Increment Sequence Number */
			seq = seq + 1;
		}
		var column_name1 = column1.split(',,');
		var coloumn_name = coloumn.split(',,');
		var Acc_Hd_Code1 = Acc_Hd_Code.split(',,');
		var acc_Hd_Code_Value1 = acc_Hd_Code_Value.split(',,');
		var accHeadCode="";
		var accHeadCodeValue="";
		/** Create Table Row */

		for ( var k = 0; k < 1; k++) {
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq;

			if (k == 0) {
				/** Sl No */
				var cell0 = document.createElement("TD");
				var test = document.createTextNode("Sl_No");
				cell0.appendChild(test);
				mycurrent_row.appendChild(cell0);

				/** Name of Office */
				var cell2 = document.createElement("TD");
				var test = document.createTextNode("Name_of_Office");
				cell2.appendChild(test);
				mycurrent_row.appendChild(cell2);
				for ( var k1 = 0; k1 < coloumn_name.length; k1++) {
					accHeadCode+=Acc_Hd_Code1[k1]+"/";
					accHeadCodeValue+=acc_Hd_Code_Value1[k1]+"/";
					if (coloumn_name[k1] != coloumn_name[k1 + 1]) {
						var cell3 = document.createElement("TD");
						var test = document.createTextNode(coloumn_name[k1]);
						var stategroup = document.createElement("input");
						stategroup.type = "hidden";
						stategroup.name = "state_group"+mycount;
						stategroup.id = "state_group"+mycount;
						stategroup.value =column_name1[k1];
						cell3.appendChild(stategroup);
						cell3.appendChild(test);
						mycurrent_row.appendChild(cell3);						
						rc = rc+1;						
						//alert("item[2] "+Acc_Hd_Code1[k1]);
						accHeadCode+="-";
						accHeadCodeValue+="-";
						mycount++;
					}
				}
			}
			
			tbody.appendChild(mycurrent_row);
			seq = seq + 1;
		}		
		document.getElementById("RecordCount").value = rc;
		acchead1=accHeadCode.split("-");
		acchead1.pop();
		acchead_value1=accHeadCodeValue.split("-");
		acchead_value1.pop();
	} else {
		alert("Failed to Load Grid Head");
	}
}

function load_grid_res(baseResponse) 
{
	seq=0;
	var re_by_region=0;
	var intial_load = baseResponse.getElementsByTagName("intial_load")[0].firstChild.nodeValue;
	if(intial_load=="no")
	{
		var tbody = document.getElementById("grid_body");
		var t = 0;

		
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}
		var r_no = baseResponse.getElementsByTagName("off_id");
		var ttl=baseResponse.getElementsByTagName("total_amount")[0].firstChild.nodeValue;
		var u_allocation=baseResponse.getElementsByTagName("u_allocation")[0].firstChild.nodeValue;
		if(u_allocation=="T")
		{
			//re_by_region=(parseInt(ttl)*1000);
			//Lakshmi
			re_by_region=(Math.round(ttl)*1000);
			document.getElementById("reallocation").value=re_by_region;
		}
		if(u_allocation=="L")
		{
			//re_by_region=(parseInt(ttl)*100000);
			//Lakshmi
			re_by_region=(Math.round(ttl)*100000);
			document.getElementById("reallocation").value=re_by_region;
		}
		if(u_allocation=="R")
		{
			//re_by_region=(parseInt(ttl)*1);
			//Lakshmi
			re_by_region=(Math.round(ttl)*1);
			document.getElementById("reallocation").value=re_by_region;
		}
		 //Total Allocation By HO in Rupees
		 var Allocation_By_HO_in_rs=document.getElementById("hoamountinrs").value;
		 //Balance Available
		// document.getElementById("balanceavail").value=(parseInt(Allocation_By_HO_in_rs))-(parseInt(re_by_region));
		 //Lakshmi
		 document.getElementById("balanceavail").value=(parseInt(Allocation_By_HO_in_rs))-(parseInt(re_by_region));
		// dhana
		 var reserved=baseResponse.getElementsByTagName("reserved")[0].firstChild.nodeValue;
		 if(reserved=="Y")
			{
				document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.reserveid[0].checked=true;
			}
		 if(reserved=="N")
			{
				
			 document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.reserveid[1].checked=true;
			}
		var item = new Array();
		var mycount=0;
		for ( var k = 0; k < r_no.length; k++) {

			item[0] = baseResponse.getElementsByTagName("off_id")[k].firstChild.nodeValue;
			item[1] = baseResponse.getElementsByTagName("Office_Name")[k].firstChild.nodeValue;
			item[2] = baseResponse.getElementsByTagName("AMOUNT")[k].firstChild.nodeValue;
			item[3] = baseResponse.getElementsByTagName("h_code")[k].firstChild.nodeValue;
			
			/** Create Table Row */
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq;

			/** Sl No */
			var cell0 = document.createElement("TD");
			var slno = document.createTextNode(seq + 1);
			cell0.appendChild(slno);
			mycurrent_row.appendChild(cell0);

			 var cell4=document.createElement("TD");       
             var office_name=document.createElement("input");
             office_name.type="hidden";
             office_name.name="office_name"+seq;
             office_name.id="office_name"+seq;
            // office_name.value=item[0];
            // office_name.value="R";
             var interfacehc=document.getElementById("head_code").value;
 			var reservedHead=interfacehc+"-R";
 			if(item[3]==reservedHead)
 			{
 				office_name.value="R";
 			
 			}
 			else
 			{
 				 office_name.value=item[0];
 				
 			}
             cell4.appendChild(office_name);
            var currentText=document.createTextNode(item[1]);
             cell4.appendChild(currentText);
             cell4.style.textAlign="left";
             mycurrent_row.appendChild(cell4);
			
			
			/** Amount */
			var cell20 = document.createElement("TD");
			var Amount = document.createElement("input");
			Amount.type = "Text";
			Amount.name = "Amount_grid"+seq;
			Amount.id = "Amount_grid"+seq;
			Amount.value =item[2];
			Amount.style.textAlign = "right";
			Amount.setAttribute('onfocus', 'colorChange('+ seq +')');
			Amount.setAttribute('align','right');
			//Amount.maxLength = "10";
			//Amount.size = "10";
			cell20.appendChild(Amount);
			var interfacehc=document.getElementById("head_code").value;
			var reservedHead=interfacehc+"-R";
			if(item[3]==reservedHead)
			{
			var currentText = document.createTextNode("Reserved Amount");
			}
			else
			{
				var currentText = document.createTextNode("");
			}
			cell20.appendChild(currentText);
			//cell20.style.textAlign="right";
			mycurrent_row.appendChild(cell20);

			

			tbody.appendChild(mycurrent_row);

			/** Increment Sequence Number */
			seq = seq + 1;
		}
		
		//if
		document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butSub.disabled = true;
		document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butDelete.disabled = false;
		document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butUpdate.disabled = false;
		
		
	}
			else{
			var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			if (flag == "success") {
				/** Delete Existing Values from Grid */
				var tbody = document.getElementById("grid_body");
				var t = 0;
				
				
					for (t = tbody.rows.length - 1; t >= 0; t--) {
						tbody.deleteRow(0);
					}
				
		
				var r_no = baseResponse.getElementsByTagName("off_id");
				
				var item = new Array();
				var mycount=0;
				for ( var k = 0; k < r_no.length; k++) {
		
					item[0] = baseResponse.getElementsByTagName("off_id")[k].firstChild.nodeValue;
					item[1] = baseResponse.getElementsByTagName("off_name")[k].firstChild.nodeValue;
					
					/** Create Table Row */
					var mycurrent_row = document.createElement("TR");
					mycurrent_row.id = seq;
		
					/** Sl No */
					var cell0 = document.createElement("TD");
					var slno = document.createTextNode(seq + 1);
					cell0.appendChild(slno);
					mycurrent_row.appendChild(cell0);
		
					 var cell4=document.createElement("TD");       
		             var office_name=document.createElement("input");
		             office_name.type="hidden";
		             office_name.name="office_name"+seq;
		             office_name.id="office_name"+seq;
		             office_name.value=item[0];
		            // office_name.style.color="red";
		             cell4.appendChild(office_name);
		            
		             var currentText=document.createTextNode(item[1]);
		             cell4.appendChild(currentText);
		             cell4.style.textAlign="left";
		             mycurrent_row.appendChild(cell4);
					
					
					/** Amount */
					var cell20 = document.createElement("TD");
					var Amount = document.createElement("input");
					Amount.type = "Text";
					Amount.name = "Amount_grid"+seq;
					Amount.id = "Amount_grid"+seq;
					Amount.value ="0";
					Amount.style.textAlign = "right";
					Amount.setAttribute('onfocus', 'colorChange('+ seq +')');
					//Amount.setAttribute('onfocus', 'hi('+seq+')');
					Amount.setAttribute('align','right');
					//Amount.maxLength = "10";
					//Amount.size = "10";
					cell20.appendChild(Amount);
					var currentText = document.createTextNode("");
					cell20.appendChild(currentText);
					//cell20.style.textAlign="right";
					mycurrent_row.appendChild(cell20);
		
					
		
					tbody.appendChild(mycurrent_row);
		
					/** Increment Sequence Number */
					seq = seq + 1;
				}
				document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butSub.disabled = false;
				document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butDelete.disabled = true;
				document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butUpdate.disabled = true;
				
			} else {
				alert("Failed to Load Grid Head");
			}
}
	//alert("seq:::"+seq);
	document.getElementById('RecordCount').value = seq;
	
}

function getStatementName1(baseResponse) {

	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.cmbStatementName.length = 1;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len4 = baseResponse.getElementsByTagName("STATEMENT_NO").length;
		for ( var i = 0; i < len4; i++) {
			var STATEMENT_NO = baseResponse
					.getElementsByTagName("STATEMENT_NO")[i].firstChild.nodeValue;
			var STATEMENT_DESC = baseResponse
					.getElementsByTagName("STATEMENT_DESC")[i].firstChild.nodeValue;

			var se = document.getElementById("cmbStatementName");
			var op = document.createElement("OPTION");
			op.value = STATEMENT_NO;
			var txt = document.createTextNode(STATEMENT_DESC);
			op.appendChild(txt);
			se.appendChild(op);
		}
	} else {
		alert("Failed to Load Statement Name");
	}
}

function delete_empty()
{
	
	var tbody = document.getElementById("grid_body");
	var rows=tbody.getElementsByTagName("tr");
	 for(i=0;i<rows.length;i++)
     {
         var cells=rows[i].cells;
       //  alert("::::"+cells.item(1).firstChild.value);
         if(cells.item(1).firstChild.value=='R')
         {
        		var rowcount = tbody.rows.length;
        		tbody.deleteRow(rowcount - 1);
        		seq=seq-1;
         }
        
     }
}

function addEmpty(val)
{
	
	var tbody = document.getElementById("grid_body");
	//alert(val);
	var mycurrent_row = document.createElement("TR");
	mycurrent_row.id = seq;

	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var off_text=document.getElementById("cmbOffice_code").options[document.getElementById("cmbOffice_code").selectedIndex].text;
	/** Sl No */
	var cell0 = document.createElement("TD");
	var slno = document.createTextNode(seq + 1);
	cell0.appendChild(slno);
	mycurrent_row.appendChild(cell0);

	 var cell4=document.createElement("TD");       
     var office_name=document.createElement("input");
     office_name.type="hidden";
     office_name.name="office_name"+seq;
     office_name.id="office_name"+seq;
     office_name.value="R";
     cell4.appendChild(office_name);
     var currentText=document.createTextNode(off_text+"-Reserved");
     cell4.appendChild(currentText);
     cell4.style.textAlign="left";
     mycurrent_row.appendChild(cell4);
	
	
	/** Amount */
	var cell20 = document.createElement("TD");
	var Amount = document.createElement("input");
	Amount.type = "Text";
	Amount.name = "Amount_grid"+seq;
	Amount.id = "Amount_grid"+seq;
	Amount.value ="0";
	Amount.style.textAlign = "right";
	Amount.setAttribute('align','right');
	//Amount.setAttribute('onchange', 'hi("addEmpty")');
	cell20.appendChild(Amount);
	var currentText = document.createTextNode("");
	cell20.appendChild(currentText);
	var read_text = document.createTextNode("Reserved Amount");
	//read_text.setAttribute('style',"color:red");
	cell20.appendChild(read_text);
	mycurrent_row.appendChild(cell20);

	

	tbody.appendChild(mycurrent_row);

	/** Increment Sequence Number */
	seq = seq + 1;
	
	document.getElementById('RecordCount').value = seq;
}
/*
function LoadGrid() {

	var fy = document.getElementById("cmbFinancialYear").value;
	var fy1 = fy.split('-');
	var y1 = fy1[0];
	var y2 = fy1[1];

	//document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butView.disabled = false;
	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butSub.disabled = false;
	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butDelete.disabled = true;
	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butUpdate.disabled = true;

	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbStatementName = document.getElementById("cmbStatementName").value;
	if (cmbStatementName == "") {
		alert("Select Statement Name in the Field");
		document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.cmbStatementName
				.focus();
	} else {
		var url = "../../../../../Civil_Budget_Statement_1_Reg_to_Cir_and_Div?command=get&y1="
				+ y1
				+ "&y2="
				+ y2
				+ "&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode
				+ "&cmbOffice_code="
				+ cmbOffice_code
				+ "&cmbStatementName="
				+ cmbStatementName;
		//alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("GET", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate1(xmlrequest);
		};

		xmlrequest.send(null);
	}

}  */


function LoadData(path) {
	//alert(path);
	//document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butView.disabled = false;
	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butSub.disabled = true;
	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butDelete.disabled = false;
	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butUpdate.disabled = false;

	document.getElementById("filter").value = "view";
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var cmbStatementName = document.getElementById("cmbStatementName").value;
	var RecordCount = 0;
	if (cmbStatementName == "") {
		alert("Select Statement Name in the Field");
		document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.cmbStatementName
				.focus();
	} else {
		var url = path
				+ "/Civil_Budget_Statement_1_Reg_to_Cir_and_Div?filter=view&cmbFinancialYear="
				+ cmbFinancialYear + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode
				+ "&cmbOffice_code=" + cmbOffice_code + "&RecordCount="
				+ RecordCount + "&cmbStatementName=" + cmbStatementName;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate1(xmlrequest);
		}

		xmlrequest.send(null);
	}

}

function LoadData_View1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	// alert(flag);
	if (flag == "success") {
		/** Delete Existing Values from Grid */
		var tbody = document.getElementById("grid_body");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}
		var total_Amt = 0;
		var r_no = baseResponse.getElementsByTagName("STATEMENT_GROUP_NO");
		var len = baseResponse.getElementsByTagName("STATEMENT_GROUP_NO").length;
		seq = 0;
		if (len != 0) {
			var item = new Array();
			for ( var k = 0; k < r_no.length; k++) {

				item[0] = baseResponse
						.getElementsByTagName("STATEMENT_GROUP_NO")[k].firstChild.nodeValue;
				item[1] = baseResponse
						.getElementsByTagName("STATEMENT_GROUP_DESC")[k].firstChild.nodeValue;
				if ((item[1] == 0) || (item[1] == 'null')) {
					item[1] = "";
				}
				item[2] = baseResponse.getElementsByTagName("AMOUNT")[k].firstChild.nodeValue;
				if ((item[2] == 0) || (item[2] == 'null')) {
					item[2] = "0";
				}

				total_Amt = parseFloat(total_Amt) + parseFloat(item[2]);

				/** Create Table Row */
				var mycurrent_row = document.createElement("TR");
				mycurrent_row.id = seq;

				/** Sl No */
				var cell0 = document.createElement("TD");
				var slno = document.createTextNode(seq + 1);
				cell0.appendChild(slno);
				mycurrent_row.appendChild(cell0);

				/** Head of Account */
				var cell2 = document.createElement("TD");
				var Head_of_Account = document.createElement('TEXTAREA',
						'option1');
				Head_of_Account.name = "Head_of_Account" + seq;
				Head_of_Account.id = "Head_of_Account" + seq;
				Head_of_Account.value = item[1];
				Head_of_Account.readOnly = true;
				Head_of_Account.setAttribute("cols", "5");
				Head_of_Account.style.height = "60px";
				Head_of_Account.style.width = "350px";
				cell2.appendChild(Head_of_Account);
				mycurrent_row.appendChild(cell2);

				/** Amount */
				var cell20 = document.createElement("TD");
				var Amount = document.createElement("input");
				Amount.type = "Text";
				Amount.name = "Amount" + seq;
				Amount.id = "Amount" + seq;
				Amount.value = item[2] + ".00";
				Amount.style.textAlign = "right";
				//Amount.setAttribute("onchange", 'Head_of_Account.style.color = "Red" ');
				// Amount.maxLength = "10";
				// Amount.size = "10";
				cell20.appendChild(Amount);
				var currentText = document.createTextNode("");
				cell20.appendChild(currentText);
				mycurrent_row.appendChild(cell20);

				/** Statement_Group_No */
				var Statement_Group_No = document.createElement("input");
				Statement_Group_No.setAttribute("type", "hidden");
				Statement_Group_No.setAttribute("value", item[0]);
				Statement_Group_No.setAttribute("name", "Statement_Group_No"
						+ seq);
				Statement_Group_No.setAttribute("id", "Statement_Group_No"
						+ seq);
				document.getElementById(
						"frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div")
						.appendChild(Statement_Group_No);

				tbody.appendChild(mycurrent_row);

				/** Increment Sequence Number */
				seq = seq + 1;
				document.getElementById("txtTotalAmount").value = total_Amt;
			}
		} else {
			alert("Record Does Not Exist");
		}
		document.getElementById("RecordCount").value = seq;
	} else if (flag == "Exist") {
		alert("Statement have Already Freezed");
		clrForm1();
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
			if (document.getElementById("Amount" + i).value == "") {
				alert("Enter Amount in the Field");
				document.getElementById("Amount" + i).focus();
				return false;
			}
		}
	} else {
		alert("No Records Found to Insert...");
		return false;
	}
	return true;
}

function checkNull()
{
	var gp_count=0;
	var reserveid_count=0;
	var final_ttl=0;
	var cmbStatementName=document.getElementById("cmbStatementName").value;
	if(cmbStatementName=="")
	{
	alert("Choose Statement Name");
	return false;
	}
	var statementGp=document.getElementById("statementGp").value;
	if(statementGp=="")
	{
	alert("Choose Statement Group Name");
	return false;
	}
	if(document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[0].checked==true ||document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[1].checked==true)
	{
		gp_count++;
	}
	
	if(gp_count==0)
	{
		alert("Choose the Type of Allocation");
		return false;
	}
	if(document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.reserveid[0].checked==true ||document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.reserveid[1].checked==true)
	{
		reserveid_count++;
	}
	
	if(reserveid_count==0)
	{
		alert("Choose Reserved Option Button");
		return false;
	}
	var tbody = document.getElementById("grid_body");
	var rowcount = tbody.rows.length;
	if (rowcount != 0) 
	{
		rows=tbody.getElementsByTagName("tr");
	   //   alert("l::::"+rows.length);
        for(i=0;i<rows.length;i++)
        {
            var cells=rows[i].cells;
         final_ttl=parseFloat(final_ttl) + parseFloat(cells.item(2).firstChild.value);
			
		}
        if(document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[0].checked==true)
    	{
    		final_ttl=parseFloat(final_ttl*1);
    	}
        if(document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[1].checked==true)
    	{
    		final_ttl=parseFloat(final_ttl*1000);
    	}
        if(document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[2].checked==true)
    	{
    		final_ttl=parseFloat(final_ttl*100000);
    	}
    	var hoamountinrs=document.getElementById("hoamountinrs").value;
    	alert("hoamountinrs::"+hoamountinrs);
    	alert("Region Amount::"+final_ttl);
    	document.getElementById("reallocation").value=final_ttl;
		if(hoamountinrs<final_ttl)
		{
			var diff=parseInt(final_ttl)-parseInt(hoamountinrs);
			document.getElementById("reallocation").value="";
			alert("Amount Exceeds The Allocation Amount By HO "+" The Differences is "+diff);
			return false;
		}
		
	}
	
	return true;
}

function funcUpdate(path) {
	var amountArr=new Array();
	var final_ttl=0;
	var office_name_arr=new Array();
	var groupId;
	var head_code;
	var unitallocation;
	var reserveid;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	var cmbStatementName=document.getElementById("cmbStatementName").value;
	var statementGp=document.getElementById("statementGp").value;
	
	if(document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.reserveid[0].checked==true)
	{
		reserveid="Y";
	}
	else
	{
		reserveid="N";
	}
	if(document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[0].checked==true)
	{
		groupId="H";
		head_code=document.getElementById("head_code").value;
	}
	else
	{
		groupId="G";
		head_code=0;
	}
	if(document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[0].checked==true)
	{
		unitallocation="R";
	}
	if(document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[1].checked==true)
	{
		unitallocation="T";
	}
	else
	{
		unitallocation="L";
	}
	document.getElementById("filter").value = "update";
	var tbody = document.getElementById("grid_body");
	var rowcount = tbody.rows.length;
	if (rowcount != 0) 
	{
		rows=tbody.getElementsByTagName("tr");
	    //  alert("l::::"+rows.length);
	      for(i=0;i<rows.length;i++)
	      {
	    	  var cells=rows[i].cells;
	    	  final_ttl=parseFloat(final_ttl) + parseFloat(cells.item(2).firstChild.value);
	    	  amountArr[i]=document.getElementById("Amount_grid"+[i]).value;
				office_name_arr[i]=document.getElementById("office_name"+[i]).value;
		  }
	     
	    
		if(document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[0].checked==true)
    	{
    		final_ttl=parseFloat(final_ttl*1);
    	}
        if(document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[1].checked==true)
    	{
    		final_ttl=parseFloat(final_ttl*1000);
    	}
        if(document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[2].checked==true)
    	{
    		final_ttl=parseFloat(final_ttl*100000);
    	}
     //   alert("afteryyyy:::"+parseInt(final_ttl));
        
        var hoamountinrs=document.getElementById("hoamountinrs").value;
        final_ttl=parseInt(final_ttl);
    	alert("hoamountinrs::"+hoamountinrs);
    	alert("Region Amounttotal***::"+parseInt(final_ttl));
    	document.getElementById("reallocation").value=parseInt(final_ttl);
    	
		if(hoamountinrs<final_ttl)
		{
			var diff=parseInt(final_ttl)-parseInt(hoamountinrs);
			document.getElementById("reallocation").value="";
			alert("Amount Exceeds The Allocation Amount By HO "+" The Differences is "+diff);
			//alert("Differences  is"+diff);
			return false;
		}
		else{	
			document.getElementById("balanceavail").value=parseFloat(hoamountinrs)-parseFloat(final_ttl);
				var RecordCount=document.getElementById("RecordCount").value;
				var url = path + "/Civil_Budget_Statement_1_Reg_to_Cir_and_Div?command=update_fn" +
				"&cmbOffice_code="+cmbOffice_code+"&cmbFinancialYear="+cmbFinancialYear+"" +
				"&cmbStatementName="+cmbStatementName+"&statementGp="+statementGp+
				"&head_code="+head_code+"&amtarr="+amountArr+
				"&office_name_arr="+office_name_arr+"&RecordCount="+RecordCount+
				"&groupId="+groupId+"&reserveid="+reserveid+"&unitallocation="+unitallocation;
				//	alert(url);
					var xmlrequest = AjaxFunction();
					xmlrequest.open("POST", url, true);
					xmlrequest.onreadystatechange = function() {
						manipulate1(xmlrequest);
					};
					xmlrequest.send(null);	
		}
		
	} 
	else {
		alert("No Records Found to Insert...");
		return false;
	}
	//return true;
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

function CalculateTotalAmount(id,pl) {
	document.getElementById("filter").value = "update";	
	var txtId="";
	var tbody = document.getElementById("grid_body");
	var rowcount = tbody.rows.length;
	var total_Amt = 0;
	for ( var i = 0; i < rowcount; i++) {		
		if (document.getElementById("Amount" + i + pl).value != "") {
			//alert(document.getElementById("Amount" + i + pl).name);
			total_Amt = parseFloat(total_Amt)
					+ parseFloat(document.getElementById("Amount" + i + pl).value);
			if(document.getElementById("total"+pl).value<total_Amt){
				document.getElementById("Amount" + i + pl).value="0";
				alert("value exceed");
				break;
			}
		}
	}
	
}

function blockHead()
{
	if(document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[0].checked==true)
	{
		document.getElementById("head_div1").style.display="block";
		document.getElementById("head_div2").style.display="block";
		callHead();
	}
	else
	{
		document.getElementById("head_div1").style.display="none";
		document.getElementById("head_div2").style.display="none";
		
	}
}

function clrForm1() {
	/** clear Existing Values from Grid */
	//clearGrid();
	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.budgetId.value=0;
	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.hoamountinrs.value=0;
	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.reallocation.value=0;
	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.balanceavail.value="";
	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupDesc.value="";
	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupType.value="";
	var stName=document.getElementById("cmbStatementName");
   // stName.length=1;
    stName.value="";
    var headc=document.getElementById("head_code");
   // headc.value="";
    headc.length=0;
    
    var op = document.createElement("OPTION");
	op.value = "";
	var txt = document.createTextNode("---Choose A/c---");
	op.appendChild(txt);
	headc.appendChild(op);
	
    var stGp=document.getElementById("statementGp");
    stGp.length=1;
    stGp.value="";
   
    document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[0].checked=false;
    document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[1].checked=false;
    
    document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.reserveid[0].checked=false;
    document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.reserveid[1].checked=true;
    document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[0].checked=false;
    document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[1].checked=false;
    document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[2].checked=true;
    
    var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
	//document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butView.disabled = false;
	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butSub.disabled = false;
	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butDelete.disabled = true;
	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.butUpdate.disabled = true;
	
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

function loadHoStatement(){
		var satatement=document.getElementById("cmbStatementName").value;
		var accId=document.getElementById("cmbAcc_UnitCode").value;
		var officeId=document.getElementById("cmbOffice_code").value;
		var financialyear=document.getElementById("cmbFinancialYear").value;
		var url="";
		url="../../../../../Civil_Budget_Statement_1_Reg_to_Cir_and_Div?command=hostatement&cmbFinancialYear="
		+ financialyear + "&cmbAcc_UnitCode=" + accId
		+ "&cmbOffice_code=" + officeId + "&cmbStatementName=" + satatement;
		var xmlrequest = AjaxFunction();
		xmlrequest.open("GET", url, true);
		xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
		};
		xmlrequest.send(null);
}
function loadHeadState(baseResponse){
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(document.getElementById('cmbStatementName').value!="1"){
	}else{
		var div=document.getElementById("mydiv");		
		var mycurrent_row = document.createElement("TR");
		mycurrent_row.id = 1;
		var len=document.getElementById("RecordCount").value;		
		if (flag == "success") {
			var dum1 = document.createElement("TD");	
			mycurrent_row.appendChild(dum1);
			var dum2 = document.createElement("TD");	
			mycurrent_row.appendChild(dum2);
			for(var i=0; i<len; i++){
				//alert("check amt "+baseResponse.getElementsByTagName("AMOUNT")[i].firstChild.nodeValue);
				var cell3 = document.createElement("TD");
				var totalAmt= document.createElement("input");
				totalAmt.type="text";
				totalAmt.name="total"+i;
				totalAmt.id="total"+i;
				totalAmt.disabled=true;
				//totalAmt.style.width="5px;";				
				totalAmt.value=baseResponse.getElementsByTagName("AMOUNT")[i].firstChild.nodeValue;
				totalAmt.size="10";				
				cell3.appendChild(totalAmt);
				cell3.style.textAlign="left";				
				mycurrent_row.appendChild(cell3);
			}
			div.appendChild(mycurrent_row);			
		} else {
			try{
				document.getElementById("mydiv").innerHTML="";
			}catch(e){
				document.getElementById("mydiv").innerText="";
			}			
		}
	}	
}

function groupch_load(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	 document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.balanceavail.value="";
	 
	var tbody=document.getElementById("grid_body");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
           tbody.deleteRow(0);
    }
	
    if(flag=="success"){
    
           var statementgp = document.getElementById("statementGp");
           statementgp.length=0;
            var gp_no = baseResponse.getElementsByTagName("gp_no");
            var gp_desc = baseResponse.getElementsByTagName("gp_desc");
            var opt = document.createElement('option');
            opt.value = "";
            opt.innerHTML ="Select"; //instead of using textnode ,we use innerhtml
            statementgp.appendChild(opt);
            for(var i=0; i<gp_no.length; i++)
                {
                
                    var opt = document.createElement('option');
                    opt.value = gp_no[i].firstChild.nodeValue;
                    opt.innerHTML = gp_desc[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                    statementgp.appendChild(opt);
                }
        }
        else
        {
        alert("No Record Exist");
         var statementgp = document.forms[0].statementGp;
         statementgp.length=0;
       //  document.forms[0].advnumber.value="0";
        
        }
}

function Range_Of(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	var head_code = document.getElementById("head_code");
	head_code.length=0;
    if(flag=="success"){ 
            var Range_Of_Heads = baseResponse.getElementsByTagName("Range_Of_Heads");
            var lengthRange=Range_Of_Heads.length;
            //alert("lengthRange  ="+lengthRange);
	            if(parseInt(lengthRange)==1){
	            	//alert("inside length equals one");
	            	  document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[0].checked=true;
	            	  document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[1].checked=false; 
	            }
            for(var i=0; i<Range_Of_Heads.length; i++)
                {
                    var opt = document.createElement('option');
                    opt.value = Range_Of_Heads[i].firstChild.nodeValue;
                    opt.innerHTML = Range_Of_Heads[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                    head_code.appendChild(opt);
                }
        }
        else
        {
        alert("Head Code Does Not Exists");
        document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.budgetId.value=0;
    	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.hoamountinrs.value=0;
    	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.reallocation.value=0;
    	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.balanceavail.value="";
    	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupDesc.value="";
    	document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupType.value="";
        var headc=document.getElementById("head_code");
        headc.length=0;
        var op = document.createElement("OPTION");
    	op.value = "";
    	var txt = document.createTextNode("---Choose A/c---");
    	op.appendChild(txt);
    	headc.appendChild(op);   
        document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[0].checked=false;
        document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupId[1].checked=false;  
        document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.reserveid[0].checked=false;
        document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.reserveid[1].checked=true;
        document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[0].checked=false;
        document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[1].checked=false;
        document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.unitallocation[2].checked=true;
       
        var tbody = document.getElementById("grid_body");
    	var t = 0;
    	for (t = tbody.rows.length - 1; t >= 0; t--) {
    		tbody.deleteRow(0);
    	}
    
        
        }
}

function changeHeadingR()
{
	document.getElementById("l1").innerHTML="";
	document.getElementById("l1").innerHTML='<font color="#FF0000">'+ " (In Rupees)"+'</font>';
}  

function changeHeadingT()
{
	document.getElementById("l1").innerHTML="";
	document.getElementById("l1").innerHTML='<font color="#FF0000">'+ " (In Thousands)"+'</font>';
}

function changeHeadingL()
{
	document.getElementById("l1").innerHTML="";
	document.getElementById("l1").innerHTML='<font color="#FF0000">'+ " (In Lakhs)"+'</font>';
}  
function loadAmt_load(baseResponse)
{
	
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
document.getElementById("budgetId").value="";
document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.balanceavail.value="";
document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupDesc.value="";
document.frmCivil_Budget_Statement_1_Reg_to_Cir_and_Div.groupType.value="";
var headc=document.getElementById("head_code");
headc.length=0;
/*var op = document.createElement("OPTION");
op.value = "";
var txt = document.createTextNode("---Choose A/c---");
op.appendChild(txt);
headc.appendChild(op);*/
    if(flag=="success"){
    	 var amt = baseResponse.getElementsByTagName("amt")[0].firstChild.nodeValue;
           document.getElementById("budgetId").value=amt;
          
        // var rupeAmt=parseInt(amt*100000);
           //Lakshmi
         var rupeAmt=Math.round(amt*100000);
         document.getElementById("hoamountinrs").value=rupeAmt;
         alert("parseInt(rupeAmt) "+parseInt(rupeAmt));
		if(document.getElementById("reallocation").value!=0)
		{
		//	document.getElementById("balanceavail").value=(parseInt(rupeAmt)-parseInt(document.getElementById("reallocation").value));
			document.getElementById("balanceavail").value=(parseInt(rupeAmt)-parseInt(document.getElementById("reallocation").value));
		}
		else
		{
			document.getElementById("balanceavail").value="";
		}                  
    }
    else
    {
    	 document.getElementById("budgetId").value=0;
    	 document.getElementById("hoamountinrs").value=0;
    }
}
function colorChange(rownos) {
	
		var tbody = document.getElementById("grid_body");
		var rowcount = tbody.rows.length;       
		for ( var i = 0; i < rowcount; i++) {		
			var r = tbody.rows[i];
			
			if(r.bgColor=="#FFCCCC"){
				
				r.bgColor = "#FFFFFF";	
				
			}
			if(parseInt(rownos)==parseInt(i)){
				r.bgColor = "#FFCCCC";	
			}
			else {
				r.bgColor = "#FFFFFF";	
			}
			
		}			
}

