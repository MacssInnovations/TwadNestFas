function getTransport()
{
 var req = false;
 try 
 {
       req= new ActiveXObject("Msxml2.XMLHTTP");
 }
 catch (e) 
 {
       try 
       {
            req = new ActiveXObject("Microsoft.XMLHTTP");
       }
       catch (e2) 
       {
            req = false;
       }
 }
 if (!req && typeof XMLHttpRequest != 'undefined') 
 {
       req = new XMLHttpRequest();
 }   
 return req;
}


//Check Financial year ----> No
function Chk_finyr() {
	//alert('test');
	if (document.getElementById("hid_text").value == "Full") {
		document.getElementById("hid_text").value = "notFull";
	}
	if (document.frmSchedule_Det.rad_fin_year[0].checked == true) {
		document.frmSchedule_Det.rad_fin_year[0].checked = false;
	}
	
	 if(document.getElementById("full_fin").style.display=="block"){
	if(document.getElementById("full_fin_Cmb").style.display=="block"){
	  document.getElementById("full_fin").style.display="none";
	 document.getElementById("full_fin_Cmb").style.display="none";} }
	 
	if (document.getElementById("more_id").style.display == "none"||document.getElementById("more_id1").style.display == "none") {
		if (document.getElementById("more").style.display == "none"||document.getElementById("more1").style.display == "none"||document.getElementById("more2").style.display == "none"||document.getElementById("more3").style.display == "none") {
			document.getElementById("more_id").style.display = "block";
			document.getElementById("more_id1").style.display = "block";
			document.getElementById("more").style.display = "block";
			document.getElementById("more1").style.display = "block";
			document.getElementById("more2").style.display = "block";
			document.getElementById("more3").style.display = "block";
		}
	}
}

//Check Monthwise ----> YES
function Chk_Mnthwise()
{
	if (document.getElementById("hid_text").value == "notFull") {
		document.getElementById("hid_text").value = "Full";

	}
	if (document.frmSchedule_Det.rad_fin_year[1].checked == true) {
		document.frmSchedule_Det.rad_fin_year[1].checked = false;
	}
	if (document.getElementById("full_fin").style.display == "none") {
		if (document.getElementById("full_fin_Cmb").style.display == "none") {
			document.getElementById("full_fin").style.display = "block";
			document.getElementById("full_fin_Cmb").style.display = "block";
		}
	}
	if (document.getElementById("more_id").style.display == "block"||document.getElementById("more_id1").style.display == "block") {
		if (document.getElementById("more").style.display == "block"||document.getElementById("more1").style.display == "block"||document.getElementById("more2").style.display == "block"||document.getElementById("more3").style.display == "block") {
			document.getElementById("more_id").style.display = "none";
			document.getElementById("more_id1").style.display = "none";
			document.getElementById("more").style.display = "none";
			document.getElementById("more1").style.display = "none";
			document.getElementById("more2").style.display = "none";
			document.getElementById("more3").style.display = "none";
		}
	}

}

// Validation for Financial year and Month
function valid_finyear()
{   
	var fin_year=document.getElementById("fin_year").value;
	var txtCB_Year_from=document.getElementById("txtCB_Year_from").value;
	var txtCB_Month_from=document.getElementById("txtCB_Month_from").value;
	var txtCB_Year_to=document.getElementById("txtCB_Year_to").value;
	var txtCB_Month_to=document.getElementById("txtCB_Month_to").value;
	var year=fin_year.split("-");
	var year_from=parseInt(year[0]);
	var year_to=parseInt(year[1]);	

	// From Year and Month
	if(txtCB_Year_from==txtCB_Year_to){
		if(txtCB_Year_from==year_from){
			if(txtCB_Year_to==year_from){
				if((txtCB_Month_from>=4)||(txtCB_Month_to>=4))
					{
					document.getElementById("hid_cmd").value="noSupp";
						}else if(txtCB_Month_to>=3)
							{
						alert('Enter Valid Month Value ....');
							}
					}			
				}
			else if(txtCB_Year_from==year_to)
				{
			 if (txtCB_Year_to==year_to) {
				if((txtCB_Month_from==3)||(txtCB_Month_to==3))
					{
						document.getElementById("hid_cmd").value="Supp";
					}
				else if((txtCB_Month_from<3)||(txtCB_Month_to<3)){
					document.getElementById("hid_cmd").value="noSupp";
				}
				else
					{
					alert('Invalid Month !!!!!');
					}
					}
			}
				}
	
	
	else if(txtCB_Year_from!=txtCB_Year_to)
		{
		if(txtCB_Year_from>txtCB_Year_to)
		{
		alert('Enter Correct Year...');
		//document.getElementById("cmbMinor_Head").disabled=true;
		//document.getElementById("sub_btn").disabled=true;
		}
		
		else if (txtCB_Year_from == year_from) {
		if (txtCB_Month_from < 4) {
			alert('Enter Valid From Month value ....');
		}else if(txtCB_Month_from >= 4) 
		{
			document.getElementById("hid_cmd").value="noSupp";	
		}
		
	} else if (txtCB_Year_from == year_to) {
		if (txtCB_Month_from > 3) {
			alert('Enter Valid From Month value ....');
		}
		if (txtCB_Month_from == 3) {
			document.getElementById("hid_cmd").value="Supp";
		}
		if(txtCB_Month_from < 3)
			{
			document.getElementById("hid_cmd").value="noSupp";
			}
	} else {
		alert('Enter Valid From Year value....');
		document.getElementById("txtCB_Year_from").value="";
		document.getElementById("txtCB_Year_from").focus();
	}
	// To Year and Month
	if (txtCB_Year_to == year_from) {
		if (txtCB_Month_to < 4) {
			alert('Enter Valid To Month value ....');
		}else if(txtCB_Month_to >= 4) 
		{
			document.getElementById("hid_cmd").value="noSupp";	
		}
	} else if (txtCB_Year_to == year_to) {
		if (txtCB_Month_to > 3) {
			alert('Enter Valid To Month value ....');
		}
		if (txtCB_Month_to == 3) {
			document.getElementById("hid_cmd").value="Supp";
		}
		if(txtCB_Month_to < 3)
			{
			document.getElementById("hid_cmd").value="noSupp";
			}
	} else {
		alert('Enter Valid To Year value....');
		document.getElementById("txtCB_Year_to").value="";
		document.getElementById("txtCB_Year_to").focus();
	}
			  
		}
		 
}
 function chkCombo() {
	var cmb_fin = document.getElementById("fin_year").value;
	if (cmb_fin == "") {
		alert('Select Financial Year ....');
		document.getElementById("fin_year").focus();
	}
}

function valid_finyear1()
{   
var fin_year=document.getElementById("fin_year").value;
var txtCB_Year_from=document.getElementById("txtCB_Year_from").value;
var txtCB_Month_from=document.getElementById("txtCB_Month_from").value;
var txtCB_Year_to=document.getElementById("txtCB_Year_to").value;
var txtCB_Month_to=document.getElementById("txtCB_Month_to").value;
var year=fin_year.split("-");
var year_from=parseInt(year[0]);
var year_to=parseInt(year[1]);
      if(txtCB_Year_from==year_from){
    	  txtCB_Month_from>=4;
    	  
      }else{
    	  alert('Enter Valid From Month ... ');
    	  document.getElementById("txtCB_Month_from").value="";
    	  document.getElementById("txtCB_Month_from").focus();
      }
      if(txtCB_Year_from==year_to){
    	  txtCB_Month_from<=3; 
      }
      else{
    	  alert('Enter Valid From Month ... ');
    	  document.getElementById("txtCB_Month_from").value="";
    	  document.getElementById("txtCB_Month_from").focus();
      }
    
      if(txtCB_Year_to==year_from)
    	  {
    	  txtCB_Month_to>=4;
    	  }
      else{
    	  alert('Enter Valid To Month ... ');
    	  document.getElementById("txtCB_Month_to").value="";
    	  document.getElementById("txtCB_Month_to").focus();
      }
      if(txtCB_Year_to==year_to)
	  {
	  txtCB_Month_to<=3;
	  if(txtCB_Year_to==year_from){
		  alert('Enter valid To Year !!!');
	  }
	  }
      else{
    	  alert('Enter Valid To Month ... ');
    	  document.getElementById("txtCB_Month_to").value="";
    	  document.getElementById("txtCB_Month_to").focus();
      }
      if((txtCB_Year_from!=year_from) && (txtCB_Year_from!=year_to))
    	  {
    	  alert('Enter Valid From Year ... ');
    	  document.getElementById("txtCB_Year_from").value="";
    	  document.getElementById("txtCB_Year_from").focus();
    	  }
      if((txtCB_Year_to!=year_from) && (txtCB_Year_to!=year_to))
	  {
	  alert('Enter Valid To Year ... ');
	  document.getElementById("txtCB_Year_to").value="";
	  document.getElementById("txtCB_Year_to").focus();
	  }
      
}
function fun_yes(){
	document.getElementById("rad_head").value="Yes";
	document.getElementById("yes_hid").value="Yes";
if((document.getElementById("yes_hid").value=="")&& (document.getElementById("yes_hid").value=="No")){
		document.getElementById("yes_hid").value="Yes";
	}
}
function fun_No(){
	document.getElementById("rad_head").value="No";
	document.getElementById("yes_hid").value="No";
if((document.getElementById("yes_hid").value=="")&& (document.getElementById("yes_hid").value=="Yes")){
		document.getElementById("yes_hid").value="No";
	}
}

function chkCashyr_mn()
{
	 var today= new Date(); 
	    var day=today.getDate();
	    var month=today.getMonth();
	    month=month+1;
	    var year=today.getYear();
	    if(year < 1900) year += 1900;
	var year_val = document.getElementById("txtCB_Year_from").value;
	var month_val = document.getElementById("txtCB_Month_from").value;
	if(year_val>year){
		alert('Enter valid Year value');
		document.getElementById("txtCB_Year_from").value="";
		document.getElementById("txtCB_Year_from").focus();
	}
	if(year_val==year){
	if(month_val>month){
		alert('Enter valid Month value');
		document.getElementById("txtCB_Month_from").value=1;
		document.getElementById("txtCB_Month_from").focus();
}}
	
}

//Load Combo for Major & Minor Group


function getMinorBudgetHeadDesc(path) {
	var cmbBudgetGroupMajor = document.getElementById("cmbBudgetGroupMajor").value;
	if (cmbBudgetGroupMajor == "") {
		alert("Select Budget Group Major in the Field");
		document.frmSchedule_Det.cmbBudgetGroupMajor.focus();
	} else {

		var url = path
				+ "/Annual_Account_Sub_Group?command=getMinorHeadDesc&cmbBudgetGroupMajor="
				+ cmbBudgetGroupMajor;
              //alert(url);
		var xmlrequest = getTransport();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);
	}
}

function manipulate(xmlrequest) {
	
	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {
			
			  var baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];
	          var tagCommand=baseResponse.getElementsByTagName("command")[0];
	          var command=tagCommand.firstChild.nodeValue; 
			 if (command == "getMinorHeadDesc") {
				LoadMinor(baseResponse);
			} 
		}
	}
}

function LoadMinor(baseResponse) {
	document.getElementById("cmbMinor_Head").length = 1;	
	var flag4 = baseResponse.getElementsByTagName("flag4")[0].firstChild.nodeValue;
	if (flag4 == "success") {
		var len45 = baseResponse.getElementsByTagName("cmbGroup_Head_Code").length;
		if (len45 != 0) {
			for ( var i = 0; i < len45; i++) {
				var cmbGroup_Head_Code = baseResponse
						.getElementsByTagName("cmbGroup_Head_Code")[i].firstChild.nodeValue;
				var cmbGroup_Head_Desc = baseResponse
						.getElementsByTagName("cmbGroup_Head_Desc")[i].firstChild.nodeValue;

				var se = document.getElementById("cmbMinor_Head");
				var op = document.createElement("OPTION");
				op.value = cmbGroup_Head_Code;
				var txt = document.createTextNode(cmbGroup_Head_Desc);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Group Head Code Does Not Exist");
		}
	} else {
		alert("Fail to Load Group Head Code");
	}
}

