//alert("Civil budget test....");//	Civil_Budget_Additional_Division	//
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
	document.frmCivil_Budget_Additional_Division.butSub.disabled = false;
	document.frmCivil_Budget_Additional_Division.butDelete.disabled = true;
	document.frmCivil_Budget_Additional_Division.butUpdate.disabled = true;
}

function callAmt(path)
{
	//alert("callamt"+path);
var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
var cmbOffice_code=document.getElementById("cmbOffice_code").value;
var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;

var cmbStatementName=document.getElementById("cmbStatementName").value;
var statementGp=document.getElementById("statementGp").value;
	
	var url = path + "/Civil_Budget_Additional_Division?command=loadAmt" +
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
	
	
	var url = path + "/Civil_Budget_Additional_Division?command=head_test" +
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
		
		var url = path + "/Civil_Budget_Additional_Division?command=callstatement" +
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
function callHeadupdate(id)
{
	//alert("update head"+id);
/*var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
var cmbOffice_code=document.getElementById("cmbOffice_code").value;
var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;*/

var cmbStatementName=document.getElementById("cmbStatementName").value;
var statementGp=document.getElementById("statementGp").value;
	
	var url ="../../../../../Civil_Budget_Additional_Division?command=callHeadupdate" +
			"&cmbStatementName="+cmbStatementName+"&statementGp="+statementGp+"&id="+id;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);	
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

	var url ="../../../../../Civil_Budget_Additional_Division?command=checkFreeze&cmbOffice_code="+cmbOffice_code+
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
function loadTable()
{
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	var cmbStatementName=document.getElementById("cmbStatementName").value;
	var statementGp=document.getElementById("statementGp").value;
	
	if(cmbOffice_code==""){
		alert("select  Office Code");
		return false;
	}else{

	var url ="../../../../../Civil_Budget_Additional_Division?command=load_table&cmbOffice_code="+cmbOffice_code+
	"&cmbFinancialYear="+cmbFinancialYear+"&cmbStatementName="+cmbStatementName+"&statementGp="+statementGp;
		
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
function chooseGroupUpdate(id)
{
//alert("chooseupdategroup"+id);
	var statement=document.getElementById("cmbStatementName").value;
	
	var url =  "../../../../../Civil_Budget_Additional_Division?command=groupchfind&statement="+statement+"&id="+id;
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
			}  else if (command == "updated"){
				updatechecking(baseResponse);
			} else if(command == "deleteRecord"){
				deleteRecordChecking(baseResponse);
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
			else if (command=="load_table")
			{
				load_table_res(baseResponse);
			}
			else if (command == "groupchupdate")
			{
				groupch_load_update(baseResponse);
			}
			else if(command == "callHeadUpdate"){
				code_update(baseResponse);
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
           document.frmCivil_Budget_Additional_Division.butSub.disabled = true;
   		   document.frmCivil_Budget_Additional_Division.butDelete.disabled = true;
   		   document.frmCivil_Budget_Additional_Division.butUpdate.disabled = true;
   		   return false;
           //clearAll();
       }
   else if(flag=="NotFreezed")
       {
          // alert("Not Freezed");
           return true;
       } else{
    	  // alert("Fail");
    	   return true;
       } 
   return true;
}

function deleteRecordChecking(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   if(flag=="success")
       {   
           alert("Record deleted Successfully.");
           clearAll();
       }
   else
       {
           alert("Failed to delete values");
       }                                  
}
function updatechecking(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   if(flag=="success")
       {   
           alert("Record Updated Successfully.");
           loadTable();
           clearAll();
       }
   else
       {
           alert("Failed to update values");
       }                                  
}

	function code_update(baseResponse)
	{
				
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	   var head_code = document.getElementById("head_code");
	    if(flag=="success"){
	    //	document.frmCivil_Budget_Additional_Division.groupId[0].checked=true;
	    //	document.getElementById("groupId")[0].checked="true";
	    	
	        
	           head_code.length=0;
	         //  savebutton();
	            var codeHeads = baseResponse.getElementsByTagName("codeHeads");
	           
	          //  for(var i=0; i<codeHeads.length; i++)
	            //    {
	               // alert("in code update javas "+codeHeads);
	                    var opt = document.createElement('option');
	                    opt.value = codeHeads[0].firstChild.nodeValue;
	                    opt.innerHTML = codeHeads[0].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
	                    head_code.appendChild(opt);
	             //   }
	        }
	        else
	        {
	        	 head_code.length=0;
	        alert("Head Code Doesn't Exist");
	         
	       //  document.forms[0].advnumber.value="0";
	        
	        }
	}

function callstatement_res(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="already")
				{
					var Allocation_Type=baseResponse.getElementsByTagName("Allocation_Type")[0].firstChild.nodeValue;
					if(Allocation_Type=="G")
					{
						
						//uncheck radio button
						document.frmCivil_Budget_Additional_Division.groupId[1].checked=true;
						document.frmCivil_Budget_Additional_Division.groupId[0].checked=false;
						
						//disable
						document.frmCivil_Budget_Additional_Division.groupId[0].disabled=true;
						document.frmCivil_Budget_Additional_Division.groupId[1].disabled=false;
						
						document.getElementById("head_div1").style.display="none";
						document.getElementById("head_div2").style.display="none";
						
					}
					else if(Allocation_Type=="H")
					{
						document.frmCivil_Budget_Additional_Division.groupId[0].checked=true;
						document.frmCivil_Budget_Additional_Division.groupId[1].checked=false;
						
						//disable
						document.frmCivil_Budget_Additional_Division.groupId[0].disabled=false;
						document.frmCivil_Budget_Additional_Division.groupId[1].disabled=true;
						
						document.getElementById("head_div1").style.display="block";
						document.getElementById("head_div2").style.display="block";
					}
					
			
		
	}else{
		document.frmCivil_Budget_Additional_Division.groupId[0].checked=false;
		document.frmCivil_Budget_Additional_Division.groupId[1].checked=false;
		
		document.frmCivil_Budget_Additional_Division.groupId[0].disabled=false;
		document.frmCivil_Budget_Additional_Division.groupId[1].disabled=false;
		
		document.getElementById("head_div1").style.display="block";
		document.getElementById("head_div2").style.display="block";
	}
}

function head_test_res(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="success")
	{
		 var tt=baseResponse.getElementsByTagName("grouptype")[0].firstChild.nodeValue;
		document.getElementById("groupType").value=tt;
		
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

	document.frmCivil_Budget_Additional_Division.butSub.disabled = false;
	document.frmCivil_Budget_Additional_Division.butDelete.disabled = true;
	document.frmCivil_Budget_Additional_Division.butUpdate.disabled = true;

	var url = path + "/Civil_Budget_Additional_Division?command=getStatementName";
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
		document.frmCivil_Budget_Additional_Division.cmbStatementName
				.focus();
	} else {
		var url = path
				+ "/Civil_Budget_Additional_Division?command=LoadGrid_Head&cmbStatementName="
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

function loadPage(id){
	var valueid=id;
	//alert(id);

	   var stname=document.getElementById("st_name_no"+id).value; 
	   document.getElementById("cmbStatementName").value=stname;

	   var stgroup=document.getElementById("st_group_no"+id).value;
	 
	   chooseGroupUpdate(stgroup);
	 
	   var headcode=document.getElementById("head_acct"+id).value;
	   document.getElementById("head_code").value=headcode;	 
	   document.getElementById("head_code").text=headcode;
	   callHeadupdate(headcode);
	   document.frmCivil_Budget_Additional_Division.groupId[0].disabled=true;
		document.frmCivil_Budget_Additional_Division.groupId[1].disabled=true;
	 //  alert("headcode  "+headcode);
	   var amt=document.getElementById("amt_allotted"+id).value;
	   document.getElementById("hoamountinrs").value=amt;
	   
	   var budreq=document.getElementById("budget_req"+id).value;
	  // alert("budgetrequired"+budreq);
	   document.getElementById("budgetrequired").value=budreq;
	   
	   var rea=document.getElementById("reason"+id).value;
	   document.getElementById("txtReason").value=rea;
	   var offid=document.getElementById("officeid"+id).value;
	   document.getElementById("cmbOffice_code").value=offid;
	   
	   
	    document.frmCivil_Budget_Additional_Division.butSub.disabled = true;
		document.frmCivil_Budget_Additional_Division.butDelete.disabled = false;
		document.frmCivil_Budget_Additional_Division.butUpdate.disabled = false;
}
function load_table_res(baseResponse) 
{
	seq=0;
	var re_by_region=0;
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//var intial_load = baseResponse.getElementsByTagName("intial_load")[0].firstChild.nodeValue;
	//if(intial_load=="no")
	//{
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
		//alert("welcome...");
		if(flag=="success"){
		for(var k = 0; k < r_no; k++) {
			//alert("hi ---"+k);

		/*	item[0] = baseResponse.getElementsByTagName("st_name")[k].firstChild.nodeValue;
		  item[1] = baseResponse.getElementsByTagName("st_Group")[k].firstChild.nodeValue;
			item[2] = baseResponse.getElementsByTagName("headofaccount")[k].firstChild.nodeValue;
			item[3] = baseResponse.getElementsByTagName("amt_alloted")[k].firstChild.nodeValue;
			item[4] = baseResponse.getElementsByTagName("budget_req")[k].firstChild.nodeValue;
			item[5] = baseResponse.getElementsByTagName("reason")[k].firstChild.nodeValue;*/
			var st_name =baseResponse.getElementsByTagName("st_name")[k].firstChild.nodeValue;
			var st_group=baseResponse.getElementsByTagName("st_Group")[k].firstChild.nodeValue;
			var st_name_no =baseResponse.getElementsByTagName("st_name_no")[k].firstChild.nodeValue;
			var st_group_no=baseResponse.getElementsByTagName("st_Group_no")[k].firstChild.nodeValue;			
			var head_acct=baseResponse.getElementsByTagName("headofaccount")[k].firstChild.nodeValue;
			var amt_allotted=baseResponse.getElementsByTagName("amt_alloted")[k].firstChild.nodeValue;
			var budget_req=baseResponse.getElementsByTagName("budget_req")[k].firstChild.nodeValue;
			var reason=baseResponse.getElementsByTagName("reason")[k].firstChild.nodeValue;
			var officeid=baseResponse.getElementsByTagName("officeid")[k].firstChild.nodeValue;
			//alert(officeid);
			/** Create Table Row */
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq;
            //alert(st_name+st_group+head_acct+amt_allotted+budget_req+reason);
			/** Sl No */
			var cell0 = document.createElement("A");
			var url="javascript:loadPage("+mycurrent_row.id+")";
			cell0.href=url;
			var slno = document.createTextNode("EDIT");
			
			cell0.appendChild(slno);
			
			mycurrent_row.appendChild(cell0);
			
			
			
			/*var url = "javascript:viewDetails('" + seq + "')";			
			anc.href = url;
			var edit = document.createTextNode("Edit");
			anc.appendChild(edit);
			td.appendChild(anc);
			var sch_id=document.createElement("TEXT");
        	sch_id.type="hidden";
        	sch_id.name="name"+seq;
        	sch_id.id="id"+seq;
        	sch_id.value="&majorCode="+majorCode;	       
        	td.appendChild(sch_id);
        	mycurrent_row.appendChild(td);	*/
			
			//var cell=document.getElementById("Text");
           // cell.style.display="block";
			
			/*
            try{cell.innerHTML="";}
              catch(e) {cell.innerText="";}
             var anc=document.createElement("A");
            var url="javascript:loadPage("+(k)+")";
            anc.href=url;
            //anc.setAttribute('style','text-decoratin:none');
            var txtedit=document.createTextNode("EDIT");
            anc.appendChild(txtedit);
            cell.appendChild(anc);
            mycurrent_row.appendChild(cell);*/


			var cell1 = document.createElement("TD");
			var st_name1=document.createElement("input");
			st_name1.type="hidden";
			st_name1.name="st_name"+seq;
			st_name1.id="st_name"+seq;
			st_name1.value=st_name;
			var st_name_no1=document.createElement("input");
			st_name_no1.type="hidden";
			st_name_no1.name="st_name_no"+seq;
			st_name_no1.id="st_name_no"+seq;
			st_name_no1.value=st_name_no;
			var stname = document.createTextNode(st_name);
			cell1.appendChild(stname);
			cell1.appendChild(st_name1);
			cell1.appendChild(st_name_no1);
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
			var budget_req1=document.createElement("input");
			budget_req1.type="hidden";
			budget_req1.name="budget_req"+seq;
			budget_req1.id="budget_req"+seq;
			budget_req1.value=budget_req;
			var budget_req2 = document.createTextNode(budget_req);
			cell5.appendChild(budget_req2);
			cell5.appendChild(budget_req1);
			mycurrent_row.appendChild(cell5);
			
			var cell6 = document.createElement("TD");
			var reason1=document.createElement("input");
			reason1.type="hidden";
			reason1.name="reason"+seq;
			reason1.id="reason"+seq;
			reason1.value=reason;			
			var reason2 = document.createTextNode(reason);
			cell6.appendChild(reason2);
			cell6.appendChild(reason1);
			mycurrent_row.appendChild(cell6);
			
			
			var cell7 = document.createElement("TD");
			cell7.style.display="none";
			var officeid1=document.createElement("input");
			officeid1.type="hidden";
			officeid1.name="officeid"+seq;
			officeid1.id="officeid"+seq;
			officeid1.value=officeid;			
			cell7.appendChild(officeid1);
			mycurrent_row.appendChild(cell7);
			
			tbody.appendChild(mycurrent_row);

			/** Increment Sequence Number */
			seq = seq + 1;
			document.frmCivil_Budget_Additional_Division.butSub.disabled = true;
			document.frmCivil_Budget_Additional_Division.butDelete.disabled = false;
			document.frmCivil_Budget_Additional_Division.butUpdate.disabled = false;
		}
}else if (flag=="NoData"){
	document.frmCivil_Budget_Additional_Division.butSub.disabled = false;
	document.frmCivil_Budget_Additional_Division.butDelete.disabled = true;
	document.frmCivil_Budget_Additional_Division.butUpdate.disabled = true;
}
		//if
	
		
		
//	}
		
	//alert("seq:::"+seq);
	document.getElementById('RecordCount').value = seq;
	
}

/*function load_grid_res(baseResponse) 
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
			re_by_region=(parseInt(ttl)*1000);
			document.getElementById("reallocation").value=re_by_region;
		}
		if(u_allocation=="L")
		{
			re_by_region=(parseInt(ttl)*100000);
			document.getElementById("reallocation").value=re_by_region;
		}
		if(u_allocation=="R")
		{
			re_by_region=(parseInt(ttl)*1);
			document.getElementById("reallocation").value=re_by_region;
		}
		 //Total Allocation By HO in Rupees
		 var Allocation_By_HO_in_rs=document.getElementById("hoamountinrs").value;
		 //Balance Available
		 //document.getElementById("balanceavail").value=(parseInt(Allocation_By_HO_in_rs))-(parseInt(re_by_region));
		// lakshmi
		var reserved=baseResponse.getElementsByTagName("reserved")[0].firstChild.nodeValue;
		if(reserved=="Y")
			{
				document.frmCivil_Budget_Additional_Division.reserveid[0].checked=true;
			}
		 if(reserved=="N")
			{
				
			 document.frmCivil_Budget_Additional_Division.reserveid[1].checked=true;
			}
		var item = new Array();
		var mycount=0;
		for ( var k = 0; k < r_no.length; k++) {

			item[0] = baseResponse.getElementsByTagName("off_id")[k].firstChild.nodeValue;
			item[1] = baseResponse.getElementsByTagName("Office_Name")[k].firstChild.nodeValue;
			item[2] = baseResponse.getElementsByTagName("AMOUNT")[k].firstChild.nodeValue;
			item[3] = baseResponse.getElementsByTagName("h_code")[k].firstChild.nodeValue;
			
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
			
			
			*//** Amount *//*
			var cell20 = document.createElement("TD");
			var Amount = document.createElement("input");
			Amount.type = "Text";
			Amount.name = "Amount_grid"+seq;
			Amount.id = "Amount_grid"+seq;
			Amount.value =item[2];
			Amount.style.textAlign = "right";
			//Amount.setAttribute('onchange', "CalculateTotalAmount()");
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

			*//** Increment Sequence Number *//*
			seq = seq + 1;
		}
		
		//if
		document.frmCivil_Budget_Additional_Division.butSub.disabled = true;
		document.frmCivil_Budget_Additional_Division.butDelete.disabled = false;
		document.frmCivil_Budget_Additional_Division.butUpdate.disabled = false;
		
		
	}
			else{
			var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			if (flag == "success") {
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
					//Amount.setAttribute('onchange', "CalculateTotalAmount()");
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
				document.frmCivil_Budget_Additional_Division.butSub.disabled = false;
				document.frmCivil_Budget_Additional_Division.butDelete.disabled = true;
				document.frmCivil_Budget_Additional_Division.butUpdate.disabled = true;
				
			} else {
				alert("Failed to Load Grid Head");
			}
}
	//alert("seq:::"+seq);
	document.getElementById('RecordCount').value = seq;
	
}*/

function getStatementName1(baseResponse) {

	document.frmCivil_Budget_Additional_Division.cmbStatementName.length = 1;
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

function LoadData(path) {
	//alert(path);
	//document.frmCivil_Budget_Additional_Division.butView.disabled = false;
	document.frmCivil_Budget_Additional_Division.butSub.disabled = true;
	document.frmCivil_Budget_Additional_Division.butDelete.disabled = false;
	document.frmCivil_Budget_Additional_Division.butUpdate.disabled = false;

	document.getElementById("filter").value = "view";
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var cmbStatementName = document.getElementById("cmbStatementName").value;
	var RecordCount = 0;
	if (cmbStatementName == "") {
		alert("Select Statement Name in the Field");
		document.frmCivil_Budget_Additional_Division.cmbStatementName
				.focus();
	} else {
		var url = path
				+ "/Civil_Budget_Additional_Division?filter=view&cmbFinancialYear="
				+ cmbFinancialYear + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode
				+ "&cmbOffice_code=" + cmbOffice_code + "&RecordCount="
				+ RecordCount + "&cmbStatementName=" + cmbStatementName;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function()
		{
			manipulate1(xmlrequest);
		}

		xmlrequest.send(null);
	}

}
function checkNull()
{
	var gp_count=0;
	//var reserveid_count=0;
	//var final_ttl=0;
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
	if(document.frmCivil_Budget_Additional_Division.groupId[0].checked==true ||document.frmCivil_Budget_Additional_Division.groupId[1].checked==true)
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

function checkFreezecp(url_value,cmbAcc_UnitCode,cmbOffice_code,cmbFinancialYear,cmbStatementName){
		
	var url ="../../../../../Civil_Budget_Additional_Division?command=checkFreeze&cmbOffice_code="+cmbOffice_code+
	"&cmbFinancialYear="+cmbFinancialYear+"&cmbStatementName="+cmbStatementName+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;	

	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		if (xmlrequest.readyState == 4) {alert(xmlrequest.readyState);
			if (xmlrequest.status == 200) {alert(xmlrequest.status);

				var baseResponse = xmlrequest.responseXML
						.getElementsByTagName("response")[0];

				var tagCommand = baseResponse.getElementsByTagName("command")[0];

				var command = tagCommand.firstChild.nodeValue;
		 if (command == "checkFreeze") {

			  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			 // alert(flag); 
			
			    if(flag=="NotFreezed")
			       {
			          // alert("Not Freezed");
			         //  return true;
				   var url1=url_value;
				   //("'url "+url1);
					var xmlrequest1 = AjaxFunction();
					xmlrequest1.open("GET", url1, true);
					xmlrequest1.onreadystatechange = function() {
						manipulate1(xmlrequest1);
					};
					xmlrequest1.send(null);
			       } else{
			    	   alert("Fail");
			    	
			       } 
			

			}
			}
		}
	
	};
	xmlrequest.send(null);
	
}
function funcUpdate(path) {
	//var amountArr=new Array();
	//var final_ttl=0.0;
	//var office_name_arr=new Array();
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	var cmbStatementName=document.getElementById("cmbStatementName").value;
	var statementGp=document.getElementById("statementGp").value;
	var head_code=document.getElementById("head_code").value;
	var hoamountinrs=document.getElementById("hoamountinrs").value;
	var budgetrequired=document.getElementById("budgetrequired").value;
	var txtReason=document.getElementById("txtReason").value;
	
	document.getElementById("filter").value = "update";
	
			
				//var RecordCount=document.getElementById("RecordCount").value;

		
	
				var url = path + "/Civil_Budget_Additional_Division?command=updated" +
				"&cmbOffice_code="+cmbOffice_code+"&cmbFinancialYear="+cmbFinancialYear+"" +
				"&cmbStatementName="+cmbStatementName+"&statementGp="+statementGp+
				"&head_code="+head_code+"&hoamountinrs="+hoamountinrs+"&budgetrequired="+budgetrequired+"&txtReason="+txtReason;
				checkFreezecp(url,cmbAcc_UnitCode,cmbOffice_code,cmbFinancialYear,cmbStatementName);	
			
				
	

}
function funcDelete1(path) {
	
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	var cmbStatementName=document.getElementById("cmbStatementName").value;
	var statementGp=document.getElementById("statementGp").value;
	var head_code=document.getElementById("head_code").value;
	var hoamountinrs=document.getElementById("hoamountinrs").value;
	var budgetrequired=document.getElementById("budgetrequired").value;
	var txtReason=document.getElementById("txtReason").value;
	var r = confirm("Are U Sure to Continue?");
	if (r == true) {
	document.getElementById("filter").value = "delete";

		
				var url = path + "/Civil_Budget_Additional_Division?command=deleteRecord" +
				"&cmbOffice_code="+cmbOffice_code+"&cmbFinancialYear="+cmbFinancialYear+"" +
				"&cmbStatementName="+cmbStatementName+"&statementGp="+statementGp+
				"&head_code="+head_code+"&hoamountinrs="+hoamountinrs+"&budgetrequired="+budgetrequired+"&txtReason="+txtReason;
				checkFreezecp(url,cmbAcc_UnitCode,cmbOffice_code,cmbFinancialYear,cmbStatementName);
	
	}else{
		document.getElementById("filter").value = "no";
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
function clearAll(){
	  document.frmCivil_Budget_Additional_Division.cmbAcc_UnitCode.selectedIndex="0";
      document.frmCivil_Budget_Additional_Division.cmbOffice_code.selectedIndex="0";
     // document.frmCivil_Budget_Additional_Division.cmbFinancialYear.selectedIndex="0";
      document.frmCivil_Budget_Additional_Division.cmbStatementName.selectedIndex="0";
      document.getElementById("statementGp").value="0";
      document.frmCivil_Budget_Additional_Division.statementGp.length=0;
      //document.frmCivil_Budget_Additional_Division.statementGp.selectedIndex="0";
      document.frmCivil_Budget_Additional_Division.groupId[0].checked=false;
      document.frmCivil_Budget_Additional_Division.groupId[1].checked=false;
      
      document.frmCivil_Budget_Additional_Division.hoamountinrs.value=0;
      document.frmCivil_Budget_Additional_Division.head_code.length=0;
      document.getElementById("head_code").value="";
      document.frmCivil_Budget_Additional_Division.budgetrequired.value="";
      document.frmCivil_Budget_Additional_Division.txtReason.value="";
      document.getElementById("butSub").disabled=false;
      
      
     /* var d=document.getElementById("cmdAdd");
      d.style.display="block";
  
      var d1=document.getElementById("cmdUpdate");
      d1.style.display="none";
      
      var d3=document.getElementById("cmdDelete");
      d3.style.display="none";*/
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
	if(document.frmCivil_Budget_Additional_Division.groupId[0].checked==true)
	{
		document.getElementById("head_div1").style.display="block";
		document.getElementById("head_div2").style.display="block";
		callHead();
	}
	else
	{
		document.getElementById("head_div1").style.display="none";
		document.getElementById("head_div2").style.display="none";
		document.getElementById("head_code").value="";
		
		
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

	//document.frmCivil_Budget_Additional_Division.butView.disabled = false;
	document.frmCivil_Budget_Additional_Division.butSub.disabled = false;
	document.frmCivil_Budget_Additional_Division.butDelete.disabled = true;
	document.frmCivil_Budget_Additional_Division.butUpdate.disabled = true;
	
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

function loadHoStatement(){
		var satatement=document.getElementById("cmbStatementName").value;
		var accId=document.getElementById("cmbAcc_UnitCode").value;
		var officeId=document.getElementById("cmbOffice_code").value;
		var financialyear=document.getElementById("cmbFinancialYear").value;
		var url="";
		url="../../../../../Civil_Budget_Additional_Division?command=hostatement&cmbFinancialYear="
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
	
    if(flag=="success"){
    
           var statementgp = document.getElementById("statementGp");
           statementgp.value=0;
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
         statementgp.value=0;
         
       //  document.forms[0].advnumber.value="0";
        
        }
}
function groupch_load_update(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
    if(flag=="success"){
    
           var statementgp = document.getElementById("statementGp");
           
           statementgp.length=0;
            var gp_no = baseResponse.getElementsByTagName("gp_no");
            var gp_desc = baseResponse.getElementsByTagName("gp_desc");
          
                
                    var opt = document.createElement('option');
                    opt.value = gp_no[0].firstChild.nodeValue;
                    opt.innerHTML = gp_desc[0].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                    statementgp.appendChild(opt);
                    
               
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
    	//document.frmCivil_Budget_Additional_Division.groupId[0].checked=true;
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
        	document.getElementById("head_code").value="";
       //  head_code.length=0;
        alert("HeadCode doesn't Exist");
         
       //  document.forms[0].advnumber.value="0";
        
        }
}

/*function changeHeadingR()
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
}  */
function loadAmt_load(baseResponse)
{
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success"){
    	 var amt = baseResponse.getElementsByTagName("amt")[0].firstChild.nodeValue;
          var rupeAmt=parseInt(amt*100000);
          document.getElementById("hoamountinrs").value=rupeAmt;     
    }
    else
    {
    	 document.getElementById("hoamountinrs").value=0;
    }
}
