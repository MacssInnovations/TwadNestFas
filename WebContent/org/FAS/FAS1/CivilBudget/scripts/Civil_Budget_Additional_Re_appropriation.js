//alert("frmCivil_Budget_Additional_Re_appropriation ....");//	frmCivil_Budget_Additional_Re_appropriation	//
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
function savebutton(){
	document.frmCivil_Budget_Additional_Re_appropriation.butSub.disabled = false;
	//document.frmCivil_Budget_Additional_Re_appropriation.butDelete.disabled = true;
	
}



function callHead()
{
	
var cmbAcc_UnitCode=0;
var cmbOffice_code=document.getElementById("txtRegionId").value;
var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;

var cmbStatementName=document.getElementById("cmbStatementName").value;
var statementGp=document.getElementById("statementGp").value;
	
	var url ="../../../../../Civil_Budget_Additional_Re_appropriation?command=callHead" +
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

function loadTable()
{
	var txtRegionId=document.getElementById("txtRegionId").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	var cmbStatementName=document.getElementById("cmbStatementName").value;
	var statementGp=document.getElementById("statementGp").value;
	

	var fy1=cmbFinancialYear.split('-');
	var y1 = fy1[0];
	var y2 = "20"+fy1[1];
	
	
	
	var checkGrp;
	var head_code=0;
	if(document.frmCivil_Budget_Additional_Re_appropriation.groupId[0].checked==true){
		checkGrp=document.frmCivil_Budget_Additional_Re_appropriation.groupId[0].value;
		head_code=document.getElementById("head_code").value;
	}else{
		checkGrp=document.frmCivil_Budget_Additional_Re_appropriation.groupId[1].value;
		head_code=0;
	}
	
	
	
	
	if((txtRegionId=="")||(txtRegionId=="0")||(txtRegionId==0)){
		alert("select  Region Code");
		return false;
	}else{

	var url ="../../../../../Civil_Budget_Additional_Re_appropriation?command=load_table&txtRegionId="+txtRegionId+
	"&cmbFinancialYear="+cmbFinancialYear+"&cmbStatementName="+cmbStatementName+"&statementGp="+statementGp+"&checkGrp="
	+checkGrp+"&head_code="+head_code+"&y1="+y1+"&y2="+y2;
		
	//alert("LOAD table... IN JS  "+url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);
	}
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
			
			if (command == "getStatementName") {
				getStatementName1(baseResponse);
			} 
			else if (command == "groupch")
			{
				groupch_load(baseResponse);
			}
			
			else if (command == "callHead")
			{
				Range_Of(baseResponse);
			}
			
			else if (command=="load_table")
			{
				load_table_res(baseResponse);
			}
			
	}
}
}

function initialLoad(path) {
	/** Delete Existing Values from Grid */
	var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}

	document.frmCivil_Budget_Additional_Re_appropriation.butSub.disabled = false;
	//document.frmCivil_Budget_Additional_Re_appropriation.butDelete.disabled = true;
	

	var url = path + "/Civil_Budget_Additional_Re_appropriation?command=getStatementName";
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);
}

function CalculateTotalAmount() {
	var tbody = document.getElementById("grid_body");
	var rowcount = tbody.rows.length;
	var total_Amt_allotted=0;
		for ( var i = 0; i < rowcount; i++) {
			if (document.getElementById("office_allot_amt" + i).value != "") {
				total_Amt_allotted=parseFloat(total_Amt_allotted)+parseFloat(document.getElementById("office_allot_amt" + i).value);			
			}
			
			
			} 				
	
		
		document.getElementById("txtTotalAmount_allotted").value = total_Amt_allotted;
		document.getElementById("txtTotalAmount_allotted").style.textAlign = "right";
}

function load_table_res(baseResponse) 
{
	seq=0;
	var re_by_region=0;
	
		var tbody = document.getElementById("grid_body");
		var t = 0;

		
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}
		var r_no = baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
		//alert("count  --->"+r_no);
	
		var item = new Array();
		//var st_name, st_group,head_acct,amt_allotted,budget_req,reason;
		var mycount=0;
		var ll=1;
		//alert("welcome...");
		
		if(r_no==0){
			alert("No Records ");
		}else{
				
		for(var k = 0; k < r_no; k++) {
			//alert("hi ---"+k);
		
			
			var st_name =baseResponse.getElementsByTagName("st_name")[k].firstChild.nodeValue;
			var st_group=baseResponse.getElementsByTagName("st_Group")[k].firstChild.nodeValue;
			var st_name_no =baseResponse.getElementsByTagName("st_name_no")[k].firstChild.nodeValue;
			var st_group_no=baseResponse.getElementsByTagName("st_Group_no")[k].firstChild.nodeValue;			
			var head_acct=baseResponse.getElementsByTagName("headofaccount")[k].firstChild.nodeValue;
			var amt_allotted=baseResponse.getElementsByTagName("amt_alloted")[k].firstChild.nodeValue;
			var budget_req=baseResponse.getElementsByTagName("amt_req")[k].firstChild.nodeValue;
			var amtexpsofar=baseResponse.getElementsByTagName("amt_exp_sofar")[k].firstChild.nodeValue;
			
			var balance =baseResponse.getElementsByTagName("balance")[k].firstChild.nodeValue;
			var officeid=baseResponse.getElementsByTagName("officeid")[k].firstChild.nodeValue;
			var officename=baseResponse.getElementsByTagName("office_name")[k].firstChild.nodeValue;
			//alert(officeid);
			/** Create Table Row */
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq;
            //alert(st_name+st_group+head_acct+amt_allotted+budget_req+reason);
			/** Sl No */

			var cell0 = document.createElement("TD");
			var slno = document.createTextNode(ll);
			cell0.appendChild(slno);
			mycurrent_row.appendChild(cell0);

			
			var cell1 = document.createElement("TD");
			var officeid1=document.createElement("input");
			officeid1.type="hidden";
			officeid1.name="officeid"+seq;
			officeid1.id="officeid"+seq;
			officeid1.value=officeid;
			var officename1=document.createElement("input");
			officename1.type="hidden";
			officename1.name="officename"+seq;
			officename1.id="officename"+seq;
			officename1.value=officename;
			var offname = document.createTextNode(officename);
			cell1.appendChild(offname);
			cell1.appendChild(officeid1);
			cell1.appendChild(officename1);
			mycurrent_row.appendChild(cell1);
			
			var cell2 = document.createElement("TD");
			var st_group1=document.createElement("input");
			st_group1.type="hidden";
			st_group1.name="st_group"+seq;
			st_group1.id="st_group"+seq;
			st_group1.value=st_group;
			var st_group_no1=document.createElement("input");
			st_group_no1.type="hidden";
			st_group_no1.name="st_group_no"+seq;
			st_group_no1.id="st_group_no"+seq;
			st_group_no1.value=st_group_no;
			var stgroup = document.createTextNode(st_group);
			cell2.appendChild(stgroup);
			cell2.appendChild(st_group1);
			cell2.appendChild(st_group_no1);
			mycurrent_row.appendChild(cell2);
			
			var cell3 = document.createElement("TD");
			var head_acct1=document.createElement("input");
			head_acct1.type="hidden";
			head_acct1.name="head_acct"+seq;
			head_acct1.id="head_acct"+seq;
			head_acct1.value=head_acct;
			var head_account = document.createTextNode(head_acct);
			cell3.appendChild(head_account);
			cell3.appendChild(head_acct1);
			mycurrent_row.appendChild(cell3);
			
			var cell4 = document.createElement("TD");
			var amt_allotted1=document.createElement("input");
			amt_allotted1.type="hidden";
			amt_allotted1.name="amt_allotted"+seq;
			amt_allotted1.id="amt_allotted"+seq;
			amt_allotted1.value=amt_allotted;
			var amt_allotted2 = document.createTextNode(amt_allotted);
		    cell4.style.textAlign="right";
			cell4.appendChild(amt_allotted2);
			cell4.appendChild(amt_allotted1);
			mycurrent_row.appendChild(cell4);
			
			var cell5 = document.createElement("TD");
			var amtexpsofar1=document.createElement("input");
			amtexpsofar1.type="hidden";
			amtexpsofar1.name="amtexpsofar"+seq;
			amtexpsofar1.id="amtexpsofar"+seq;
			amtexpsofar1.value=amtexpsofar;			
			var amtexpsofar2 = document.createTextNode(amtexpsofar);
			cell5.appendChild(amtexpsofar2);
			cell5.appendChild(amtexpsofar1);
			mycurrent_row.appendChild(cell5);
			
			
			
			var cell6 = document.createElement("TD");
			var balance1=document.createElement("input");
			balance1.type="hidden";
			balance1.name="balance"+seq;
			balance1.id="balance"+seq;
			balance1.value=balance;			
			var balance2 = document.createTextNode(balance);
			cell6.appendChild(balance2);
			cell6.appendChild(balance1);
			mycurrent_row.appendChild(cell6);
			
			
			var cell7 = document.createElement("TD");
			var budget_req1=document.createElement("input");
			budget_req1.type="hidden";
			budget_req1.name="budget_req"+seq;
			budget_req1.id="budget_req"+seq;
			budget_req1.value=budget_req;
			var budget_req2 = document.createTextNode(budget_req);
			cell7.appendChild(budget_req2);
			cell7.appendChild(budget_req1);
			mycurrent_row.appendChild(cell7);
			
 
			var cell8 = document.createElement("TD");
			var office_allot_amt1 = document.createElement("input");
			office_allot_amt1.type = "Text";
			office_allot_amt1.name = "office_allot_amt" + seq;
			office_allot_amt1.id = "office_allot_amt" + seq;
			office_allot_amt1.setAttribute('onblur', "CalculateTotalAmount()");
			office_allot_amt1.size = "10";
			cell8.appendChild(office_allot_amt1);
			var currentText = document.createTextNode("");
			cell8.appendChild(currentText);
			//cell8.style.textAlign="right";
			mycurrent_row.appendChild(cell8);
			
			
			tbody.appendChild(mycurrent_row);

			/** Increment Sequence Number */
			seq = seq + 1;
			ll++;
		}
}
		//if
		document.getElementById("RecordCount").value = ll;
		document.frmCivil_Budget_Additional_Re_appropriation.butSub.disabled = false;
		//document.frmCivil_Budget_Additional_Re_appropriation.butDelete.disabled = false;
		
		
		
//	}
		
	//alert("seq:::"+seq);
	document.getElementById('RecordCount').value = seq;
	
}


function getStatementName1(baseResponse) {

	document.frmCivil_Budget_Additional_Re_appropriation.cmbStatementName.length = 1;
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
	if(document.frmCivil_Budget_Additional_Re_appropriation.groupId[0].checked==true ||document.frmCivil_Budget_Additional_Re_appropriation.groupId[1].checked==true)
	{
		gp_count++;
	}
	
	if(gp_count==0)
	{
		alert("Choose the Type of Allocation");
		return false;
	}		
	return true;
}

function clearAll(){
      document.frmCivil_Budget_Additional_Re_appropriation.txtRegionId.selectedIndex="0";
     // document.frmCivil_Budget_Additional_Re_appropriation.cmbFinancialYear.selectedIndex="0";
      document.frmCivil_Budget_Additional_Re_appropriation.cmbStatementName.selectedIndex="0";
      document.getElementById("statementGp").value="0";
      document.frmCivil_Budget_Additional_Re_appropriation.statementGp.length=0;
      document.frmCivil_Budget_Additional_Re_appropriation.groupId[0].checked=false;
      document.frmCivil_Budget_Additional_Re_appropriation.groupId[1].checked=false;
      
      document.frmCivil_Budget_Additional_Re_appropriation.txtTotalAmount_allotted.value=0;
      document.frmCivil_Budget_Additional_Re_appropriation.head_code.length=0;
      document.getElementById("head_code").value="";
      
     
      document.getElementById("butSub").disabled=true;
      
    
}

function blockHead()
{
	if(document.frmCivil_Budget_Additional_Re_appropriation.groupId[0].checked==true)
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
	/** Delete Existing Values from Grid */
	//clearGrid();
	var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
	document.getElementById("cmbStatementName").value = "";
	//document.getElementById("txtTotalAmount").value = "";
	document.frmCivil_Budget_Additional_Re_appropriation.butSub.disabled = false;
	//document.frmCivil_Budget_Additional_Re_appropriation.butDelete.disabled = true;
	
	
}

function numbersonly(e,t) {
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
	
    if(flag=="success"){
    
           var statementgp = document.getElementById("statementGp");
           statementgp.length=0;
            var gp_no = baseResponse.getElementsByTagName("gp_no");
            var gp_desc = baseResponse.getElementsByTagName("gp_desc");
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
    if(flag=="success"){
    	//document.frmCivil_Budget_Additional_Re_appropriation.groupId[0].checked=true;
    	//document.getElementById("groupId")[0].checked="true";
          
         //  Lakshmi
          //  advnumber.length=0;
           head_code.length=0;
         //  savebutton();
            var Range_Of_Heads = baseResponse.getElementsByTagName("Range_Of_Heads");
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
         head_code.length=0;
        alert("HeadCode doesn't Exist");
         
       //  document.forms[0].advnumber.value="0";
        
        }
}

