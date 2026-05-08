//	Civil_Budget_Additional_consolidate	//
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
	
	var url = path + "/Civil_Budget_Additional_Consolid?command=loadAmt" +
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
	
	
	var url = path + "/Civil_Budget_Additional_Consolid?command=head_test" +
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
		if(cmbStatementName==""){
			alert("Select StatementName");
			return false;
		}else if(statementGp==""){
			alert("select statement Group");
			return false;
		}else {
		var url = path + "/Civil_Budget_Additional_Consolid?command=callstatement" +
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
}  

function callHead()
{
	
var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
var cmbOffice_code=document.getElementById("cmbOffice_code").value;
var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;

var cmbStatementName=document.getElementById("cmbStatementName").value;
var statementGp=document.getElementById("statementGp").value;
	
	var url ="../../../../../Civil_Budget_Additional_Consolid?command=callHead" +
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

function loadGrid()
{
	//alert("load grid ");
	
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	var cmbStatementName=document.getElementById("cmbStatementName").value;
	var statementGp=document.getElementById("statementGp").value;
	var head_code;
	if(document.frmCivil_Budget_Additional_Consolid.groupId[0].checked==true)
	{
		head_code=document.getElementById("head_code").value;
	}
	else
	{
		head_code=0;
	}
	
	var url = "../../../../../Civil_Budget_Additional_Consolid?command=load_grid&cmbOffice_code="+cmbOffice_code+
	"&cmbFinancialYear="+cmbFinancialYear+"&cmbStatementName="+cmbStatementName+
	"&statementGp="+statementGp+"&head_code="+head_code;

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
	
	var url = path + "/Civil_Budget_Additional_Consolid?command=groupch&statement="+statement;
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
			
			if (command == "getStatementName") {
				getStatementName1(baseResponse);
			} 
			else if (command == "groupch")
			{
				groupch_load(baseResponse);
			}
			else if (command=="callstatement")
			{
				callstatement_res(baseResponse);
			}
			else if (command == "callHead")
			{
				Range_Of(baseResponse);
			}
			else if (command == "loadAmt")
			{
				loadAmt_load(baseResponse);
			}
			else if (command=="head_test")
			{
				head_test_res(baseResponse);
			}
			else if (command=="load_grid")
			{
				load_grid_res(baseResponse);
			}
			else if (command == "deleteFn") {
				deleteFn1(baseResponse);
			}else if (command == "checkFreeze") {
		    	checkFreeze1(baseResponse);
			}

		}
	}
}
function checkFreeze1(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   if(flag=="Freezed")
       {   
           alert("Freezed,So Cant modified");
           document.frmCivil_Budget_Additional_Consolid.butSub.disabled = true;
   		   document.frmCivil_Budget_Additional_Consolid.butDelete.disabled = true;
   		   //document.frmCivil_Budget_Additional_Consolid.butUpdate.disabled = true;
   		   return false;
           //clearAll();
       }
   else if(flag=="NotFreezed")
       {
          // alert("Not Freezed");
           return true;
       } else{
    	   //alert("Fail");
    	   return true;
       } 
   return true;
}

function deleteFn1(baseResponse)
{
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="deletesuccess")
	{
		alert("Record Deleted Successfully");
	}else if(flag=="nodelete"){
		alert("Record not delete");
	}else if(flag=="failure"){
		alert("Fail in delete");
		
	}
}
/*function update_row(baseResponse)
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
}*/

function callstatement_res(baseResponse)
{
	//var re_by_region=0;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="already")
				{
					//var Allocation_Type=baseResponse.getElementsByTagName("Allocation_Type")[0].firstChild.nodeValue;
					var reqAmt=baseResponse.getElementsByTagName("reqAmt")[0].firstChild.nodeValue;
					document.getElementById("reallocation").value=reqAmt;

	}else{
		document.getElementById("reallocation").value="";
		document.frmCivil_Budget_Additional_Consolid.groupId[0].checked=false;
		document.frmCivil_Budget_Additional_Consolid.groupId[1].checked=false;
		
		document.frmCivil_Budget_Additional_Consolid.groupId[0].disabled=false;
		document.frmCivil_Budget_Additional_Consolid.groupId[1].disabled=false;
	
		document.getElementById("head_div1").style.display="block";
		document.getElementById("head_div2").style.display="block";
	}
}

function head_test_res(baseResponse)
{
	document.frmCivil_Budget_Additional_Consolid.groupDesc.value="";
	document.frmCivil_Budget_Additional_Consolid.groupType.value="";
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

/*function addRow(baseResponse){
	var flag = baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue;
	if(flag=="success"){
		alert("Records add successfully");
	}else{
		alert("Records not Add");
	}
	clrForm1();
}
*/
function initialLoad(path) {
	/** Delete Existing Values from Grid */
	var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
	document.frmCivil_Budget_Additional_Consolid.butSub.disabled = false;
	document.frmCivil_Budget_Additional_Consolid.butDelete.disabled = true;
	//document.frmCivil_Budget_Additional_Consolid.butUpdate.disabled = true;

	var url = path + "/Civil_Budget_Additional_Consolid?command=getStatementName";
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
		document.frmCivil_Budget_Additional_Consolid.cmbStatementName
				.focus();
	} else {
		var url = path
				+ "/Civil_Budget_Additional_Consolid?command=LoadGrid_Head&cmbStatementName="
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

function load_grid_res(baseResponse) 
{
	seq=0;
	//var re_by_region=0;
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
		var item = new Array();
		var mycount=0;
		var ll=1;
		for ( var k = 0; k < r_no.length; k++) {
			item[0] = baseResponse.getElementsByTagName("off_id")[k].firstChild.nodeValue;
			item[1] = baseResponse.getElementsByTagName("Office_Name")[k].firstChild.nodeValue;
			item[2] = baseResponse.getElementsByTagName("ADDL_BUDGET_REQ")[k].firstChild.nodeValue;
			item[3] = baseResponse.getElementsByTagName("h_code")[k].firstChild.nodeValue;
			
			/** Create Table Row */
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq;

			/** Sl No */
			var cell0 = document.createElement("TD");
			var slno = document.createTextNode(ll);
			cell0.appendChild(slno);
			mycurrent_row.appendChild(cell0);

			 var cell1=document.createElement("TD");       
             var office_name=document.createElement("input");
             office_name.type="hidden";
             office_name.name="office_name"+seq;
             office_name.id="office_name"+seq;
 			 office_name.value=item[0];
             cell1.appendChild(office_name); 
             var currentText=document.createTextNode(item[1]);
             cell1.appendChild(currentText);
             cell1.style.textAlign="left";
             mycurrent_row.appendChild(cell1);
			
			
			/** Amount */
			var cell2 = document.createElement("TD");
			var Amount1 = document.createElement("input");
			Amount1.type = "hidden";
			Amount1.name = "Amount_grid"+seq;
			Amount1.id = "Amount_grid"+seq;
			Amount1.value =item[2];
		//	Amount1.style.textAlign = "right";
			//Amount1.setAttribute('onfocus', 'colorChange('+ seq +')');
			Amount1.setAttribute('align','right');
			cell2.appendChild(Amount1); 
			 var currentText2=document.createTextNode(item[2]);
			// cell2.style.textAlign="right";
             cell2.appendChild(currentText2);
			mycurrent_row.appendChild(cell2);

			

			tbody.appendChild(mycurrent_row);

			/** Increment Sequence Number */
			ll++;
			seq = seq + 1;
		}
		
		//if
		document.frmCivil_Budget_Additional_Consolid.butSub.disabled = false;
		document.frmCivil_Budget_Additional_Consolid.butDelete.disabled = false;
		//document.frmCivil_Budget_Additional_Consolid.butUpdate.disabled = true;
		
		
	}else{
		alert("Record not exists");
		var tbody = document.getElementById("grid_body");
		var t = 0;
		
		
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}
	}
	/*		else{
			var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			if (flag == "success") {
				//alert(flag);
				*//** Delete Existing Values from Grid *//*
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
					
					*//** Create Table Row *//*
					var mycurrent_row = document.createElement("TR");
					mycurrent_row.id = seq;
		
					*//** Sl No *//*
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
					
					
					*//** Amount *//*
					var cell20 = document.createElement("TD");
					var Amount = document.createElement("input");
					Amount.type = "Text";
					Amount.name = "Amount_grid"+seq;
					Amount.id = "Amount_grid"+seq;
					Amount.value ="0";
					Amount.style.textAlign = "right";
					//Amount.setAttribute('onfocus', 'colorChange('+ seq +')');
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
		
					*//** Increment Sequence Number *//*
					seq = seq + 1;
				}
				document.frmCivil_Budget_Additional_Consolid.butSub.disabled = true;
				document.frmCivil_Budget_Additional_Consolid.butDelete.disabled = false;
				//document.frmCivil_Budget_Additional_Consolid.butUpdate.disabled = false;
				
			} else {
				alert("Failed to Load Grid Head");
			}
}*/
	//alert("seq:::"+seq);
	document.getElementById('RecordCount').value = seq;
	
}

function getStatementName1(baseResponse) {

	document.frmCivil_Budget_Additional_Consolid.cmbStatementName.length = 1;
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
	if(document.frmCivil_Budget_Additional_Consolid.groupId[0].checked==true ||document.frmCivil_Budget_Additional_Consolid.groupId[1].checked==true)
	{
		gp_count++;
	}
	
	if(gp_count==0)
	{
		alert("Choose the Type of Allocation");
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
    	var hoamountinlak=document.getElementById("hoamountinlak").value;
    	//checkNull
	}else{
		alert("Click Go Button");
		return false;
	}
	if(checkFreeze()){
		return true;	
	}else{
		return false;
	}
	
	return true;
}
function checkFreeze(){
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	var cmbStatementName=document.getElementById("cmbStatementName").value;
	//var statementGp=document.getElementById("statementGp").value;
	
	if(cmbOffice_code==""){
		alert("select  Office Code");
		return false;
	}else{

	var url ="../../../../../Civil_Budget_Additional_Consolid?command=checkFreeze&cmbOffice_code="+cmbOffice_code+
	"&cmbFinancialYear="+cmbFinancialYear+"&cmbStatementName="+cmbStatementName+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;	
	//alert("checkFreeze... IN JS  "+url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);
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
function deleteFn(){
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var cmbStatementName = document.getElementById("cmbStatementName").value;
	var statementGp=document.getElementById("statementGp").value;

	if(checkFreeze()){
	if(funcDelete()){
		var url ="../../../../../Civil_Budget_Additional_Consolid?command=deleteFn&cmbFinancialYear="
		+ cmbFinancialYear + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode
		+ "&cmbOffice_code=" + cmbOffice_code +
		 "&cmbStatementName=" + cmbStatementName+"&statementGp="+statementGp;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("GET", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate1(xmlrequest);
		};
		
		xmlrequest.send(null);
	}
	}
}

/*function CalculateTotalAmount(id,pl) {
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
	
}*/

function blockHead()
{
	if(document.frmCivil_Budget_Additional_Consolid.groupId[0].checked==true)
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

	document.frmCivil_Budget_Additional_Consolid.hoamountinlakhs.value=0;
	document.frmCivil_Budget_Additional_Consolid.reallocation.value=0;
	document.frmCivil_Budget_Additional_Consolid.groupDesc.value="";
	document.frmCivil_Budget_Additional_Consolid.groupType.value="";
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
   
    document.frmCivil_Budget_Additional_Consolid.groupId[0].checked=false;
    document.frmCivil_Budget_Additional_Consolid.groupId[1].checked=false;

    
    var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
	//document.frmCivil_Budget_Additional_Consolid.butView.disabled = false;
	document.frmCivil_Budget_Additional_Consolid.butSub.disabled = false;
	document.frmCivil_Budget_Additional_Consolid.butDelete.disabled = true;
//	document.frmCivil_Budget_Additional_Consolid.butUpdate.disabled = true;
	
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

function groupch_load(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	// document.frmCivil_Budget_Additional_Consolid.balanceavail.value="";
	 
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
	            	  document.frmCivil_Budget_Additional_Consolid.groupId[0].checked=true;
	            	  document.frmCivil_Budget_Additional_Consolid.groupId[1].checked=false; 
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
       // document.frmCivil_Budget_Additional_Consolid.budgetId.value=0;
    	document.frmCivil_Budget_Additional_Consolid.hoamountinlakhs.value=0;
    	document.frmCivil_Budget_Additional_Consolid.reallocation.value=0;
    	//document.frmCivil_Budget_Additional_Consolid.balanceavail.value="";
    	document.frmCivil_Budget_Additional_Consolid.groupDesc.value="";
    	document.frmCivil_Budget_Additional_Consolid.groupType.value="";
        var headc=document.getElementById("head_code");
        headc.length=0;
        var op = document.createElement("OPTION");
    	op.value = "";
    	var txt = document.createTextNode("---Choose A/c---");
    	op.appendChild(txt);
    	headc.appendChild(op);   
        document.frmCivil_Budget_Additional_Consolid.groupId[0].checked=false;
        document.frmCivil_Budget_Additional_Consolid.groupId[1].checked=false;  
        /*document.frmCivil_Budget_Additional_Consolid.reserveid[0].checked=false;
        document.frmCivil_Budget_Additional_Consolid.reserveid[1].checked=true;
        document.frmCivil_Budget_Additional_Consolid.unitallocation[0].checked=false;
        document.frmCivil_Budget_Additional_Consolid.unitallocation[1].checked=false;
        document.frmCivil_Budget_Additional_Consolid.unitallocation[2].checked=true;*/
       
        var tbody = document.getElementById("grid_body");
    	var t = 0;
    	for (t = tbody.rows.length - 1; t >= 0; t--) {
    		tbody.deleteRow(0);
    	}
    
        
        }
}

function loadAmt_load(baseResponse)
{
	
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
//document.getElementById("budgetId").value="";
//document.frmCivil_Budget_Additional_Consolid.balanceavail.value="";
document.frmCivil_Budget_Additional_Consolid.groupDesc.value="";
document.frmCivil_Budget_Additional_Consolid.groupType.value="";
var headc=document.getElementById("head_code");
headc.length=0;
/*var op = document.createElement("OPTION");
op.value = "";
var txt = document.createTextNode("---Choose A/c---");
op.appendChild(txt);
headc.appendChild(op);*/
    if(flag=="success"){
    	 var amt = baseResponse.getElementsByTagName("amt")[0].firstChild.nodeValue;
    	 
         document.getElementById("hoamountinlakhs").value=amt;
                   
    }
    else
    {
    	
    	 document.getElementById("hoamountinlakhs").value=0;
    }
}
/*function colorChange(rownos) {
	
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

*/