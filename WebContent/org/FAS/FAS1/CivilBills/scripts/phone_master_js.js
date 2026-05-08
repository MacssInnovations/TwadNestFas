/********************javascript*******************/

var com_id;
var seq=0;
var winemp1="";
/***************** ajax CONCEPT***************/
function getTransport()
{
	var req=false;
	try
	{
		req=new ActiveXObject("Msxml2.XMLHTTP");
	}
	catch(e1)
	{
		try
		{
			req=new ActiveXObject("Microsoft.XMLHTTP");
		}
		catch(e2)
		{
			req=false;
		}
	}
	if (!req && typeof XMLHttpRequest != 'undefined')
	{
		req=new XMLHttpRequest();
	}
	return req;
}

function exitmethod()
{
	window.close();
}

function numbersonly(e,t)
{
	var unicode=e.charCode? e.charCode : e.keyCode;
	if(unicode==13)
	{
		try{t.blur(); }catch(e){}
		return true;

	}
	if (unicode!=8 && unicode !=9)
	{
		if (unicode<48||unicode>57 ) 
		{
			return false; 
		}
	}
}  
function filter_real(evt,item,n,pre)
{
	var charCode = (evt.which) ? evt.which : event.keyCode
			// allow "." for one time 
			if(charCode==46)
			{
				//	alert("Position of . "+item.value.indexOf("."));
				if(item.value.indexOf(".")<0)	return (item.value.length>0)?true:false;
				else return false;
			}
	if (!(charCode > 31 && (charCode < 48 || charCode > 57)))
	{
		// to avoid over flow
		if(item.value.indexOf(".")<0)
		{
			//			alert("Length without . ="+item.value.length);
			return (item.value.length<n)?true:false;
		}
		// dont allow more than 2 precision no's after the point
		if(item.value.indexOf(".")>0)
		{
			//	alert("precision count ="+item.value.split(".")[1].length);
			if(item.value.split(".")[1].length<pre) return true;
			else return false;
		}
		return false;
	}else
	{
		return false;
	}
}
function check_leng(param,val)
{	 
	if((val.length)>=190)
	{
		if(param=='remarks')			  
			alert("Please Enter Remarks below 200 characters");			           			  
		else			  
			alert("Please Enter Paticulars below 200 characters");				  	  

	}

}
function enableceilamt()
{
	if(document.formPhone_Master.rad_ceiling_type[0].checked)
	{
		document.getElementById("txtceil_Limit_amt").disabled=false;
	}
	else 
	{
		document.getElementById("txtceil_Limit_amt").disabled=true;
	}
}
/*******************clear all the fields *******************************/
function clearall()
{
	document.getElementById("cmb_purpose").value=0;
	document.getElementById("cmb_connection_type").value=0;
	document.getElementById("txtSTD_code").value="";
	document.getElementById("txtphone_no").value=""; 
	document.getElementById("txtSerProName").value=0;
	document.getElementById("txtSerProType").value=0;
	document.formPhone_Master.rad_ceiling_type[0].checked=true;
	document.getElementById("txtceil_Limit_amt").value="";
	document.getElementById("txtParticulars").value="";
	document.formPhone_Master.rad_usage_det[0].checked=true;

	document.formPhone_Master.cmdadd.style.display='block';
	document.formPhone_Master.cmdupdate.style.display='none';
	document.formPhone_Master.cmddelete.disabled=true;
}

function valid_amt(field)
{

	amt=field.value;
	if(amt.indexOf(".")!=amt.lastIndexOf("."))
	{
		alert("Enter a Valid Amount");
		field.value="";
		field.focus();
	}
	if(amt < 0 ) 
	{
		alert("Negative Amount Not Allowed");
		field.value="";
		field.focus();    
	}	   
	else
		return true;

}
function call_clr()
{
	document.getElementById("cmbAcc_UnitCode").selectedIndex=0;
	document.getElementById("cmbOffice_code").selectedIndex=0;
	document.getElementById("cmbOffice_code").length=1;
	document.formPhone_Master.rad_cust_type[0].checked=true;
	document.getElementById("txtEmpId").value="";
	document.getElementById("txtemp_name").value="";
	document.getElementById("txtemp_desig").value="";
	document.getElementById("officeId").value="";
	document.getElementById("txtemp_office").value="";
	document.getElementById("txtRemarks").value="";
	document.getElementById("cmb_purpose").value=0;
	document.getElementById("cmb_connection_type").value=0;
	document.formPhone_Master.rad_usage_det[0].checked=true;
	document.getElementById("txtSTD_code").value="";
	document.getElementById("txtphone_no").value=""; 
	document.getElementById("txtSerProName").value=0;
	document.getElementById("txtSerProType").value=0;
	document.formPhone_Master.rad_ceiling_type[0].checked=true;
	document.getElementById("txtceil_Limit_amt").value="";
	document.getElementById("txtParticulars").value="";
	var tbody=document.getElementById("grid_body");
	var t=0;
	for(t=tbody.rows.length-1;t>=0;t--)
	{
		tbody.deleteRow(0);
	}
}
function clrForm()
{
	if(window.confirm("Do you want to clear ALL fields ?"))
	{
		call_clr();
	}
}


var Phone_list_SL;

function gridPhoneList()   
{
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var url="../../../../../phone_master_servlet?command=GridPhoneDet&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
var req=getTransport();
req.open("GET",url,true);        
req.onreadystatechange=function()
{
           processResponse(req);
}   
 req.send(null);
}


function callphoneList()
{
	if (Phone_list_SL && Phone_list_SL.open && !Phone_list_SL.closed) 
	{
		Phone_list_SL.resizeTo(500,500);
		Phone_list_SL.moveTo(250,250); 
		Phone_list_SL.focus();
	}
	else
	{
		Phone_list_SL=null
	}
	Phone_list_SL= window.open("../jsps/phone_master_list_jsp.jsp","PhoneDetails","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
	Phone_list_SL.moveTo(250,250);  
	Phone_list_SL.focus();

}

window.onunload=function()
{
	if (Phone_list_SL && Phone_list_SL.open && !Phone_list_SL.closed) 
		Phone_list_SL.close();
}
/******************Load Employee details*******************************/
function Load_emp_details()
{
	//alert("inside loadempdetails");
	var emp_id=document.getElementById("txtEmpId").value;
	if(document.formPhone_Master.rad_cust_type[0].checked)
	{
		
		alert(document.getElementById("rad_cust_type").value);
		var url="";
		url="../../../../../phone_master_servlet?command=loadempdetails&emp_id="+emp_id;
		//alert(url);
		var req=getTransport();
		req.open("GET",url,true);        
		req.onreadystatechange=function()
		{
			processResponse(req);
		}   
		req.send(null);
	}
	/*else
        {
            alert("No data found to Load for Privileged Users");
        }*/
}

function enableSTDcode(t)
{
	//alert(t);
	if(t=="L")
	{
		//alert("Lanline chosen");
		//alert("Enter the STD code");
		document.formPhone_Master.txtSTD_code.disabled=false;
	}
	else
	{
		//alert("Mobile chosen");
		document.formPhone_Master.txtSTD_code.disabled=true;
	}
}

//********************************* CallServer Response Coding ***************************************//

function processResponse(req)
{   
	if(req.readyState==4)   // Completed
	{
		
		if(req.status==200)   // No error
		{   
			//alert(req.responseText);
			var baseResponse=req.responseXML.getElementsByTagName("response")[0];
			//alert(baseResponse);
			var tagCommand=baseResponse.getElementsByTagName("command")[0];
			var command=tagCommand.firstChild.nodeValue; 
			
			if(command=="loadempdetails")
			{
				LoadEmpDetails(baseResponse);
			}
			if(command=="GridPhone")
			{
				//alert(command);
				LoadPhoneDetails(baseResponse);
			}
		}    
	}
}

function LoadPhoneDetails(baseResponse){
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 

	
	if(flag=="success")
	{   
		//var REMARKS=baseResponse.getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
		var c_len=baseResponse.getElementsByTagName("SL_NO");
	//	alert(">>> "+ACCOUNTING_UNIT_ID.length);
		var t = 0;
		var tbody=document.getElementById("grid_body");
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}
		for(var i=0;i<c_len.length;i++){
			//alert(i+"   "+c_len.length);
		var ACCOUNTING_UNIT_ID=baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[i].firstChild.nodeValue;
	//alert(ACCOUNTING_UNIT_ID);
		 var ACCOUNTING_UNIT_OFFICE_ID=baseResponse.getElementsByTagName("ACCOUNTING_UNIT_OFFICE_ID")[i].firstChild.nodeValue;
		var EMPLOYEE_ID=baseResponse.getElementsByTagName("EMPLOYEE_ID")[i].firstChild.nodeValue;
		var USER_CATEGORY_ID=baseResponse.getElementsByTagName("USER_CATEGORY_ID")[i].firstChild.nodeValue;
		var REMARKS=baseResponse.getElementsByTagName("REMARKS")[i].firstChild.nodeValue;
		var SL_NO=baseResponse.getElementsByTagName("SL_NO")[i].firstChild.nodeValue;
		var PURPOSE=baseResponse.getElementsByTagName("PURPOSE")[i].firstChild.nodeValue;
		var CONNECTION_TYPE=baseResponse.getElementsByTagName("CONNECTION_TYPE")[i].firstChild.nodeValue;
		var STD_CODE=baseResponse.getElementsByTagName("STD_CODE")[i].firstChild.nodeValue;
		var PHONE_NO=baseResponse.getElementsByTagName("PHONE_NO")[i].firstChild.nodeValue;		
		var purpose_name=baseResponse.getElementsByTagName("purpose_name")[i].firstChild.nodeValue;
		var connection_Name=baseResponse.getElementsByTagName("connection_Name")[i].firstChild.nodeValue;
		var SERVICE_PROVIDER_NAME=baseResponse.getElementsByTagName("SERVICE_PROVIDER_NAME")[i].firstChild.nodeValue;
		var SERVICE_TYPE=baseResponse.getElementsByTagName("SERVICE_TYPE")[i].firstChild.nodeValue;
		var CEILING_TYPE=baseResponse.getElementsByTagName("CEILING_TYPE")[i].firstChild.nodeValue;
		var CEILING_LIMIT_AMOUNT=baseResponse.getElementsByTagName("CEILING_LIMIT_AMOUNT")[i].firstChild.nodeValue;
		var PARTICULARS=baseResponse.getElementsByTagName("PARTICULARS")[i].firstChild.nodeValue;
		var USAGE_DETAILS=baseResponse.getElementsByTagName("USAGE_DETAILS")[i].firstChild.nodeValue;
		var ceil_desc=baseResponse.getElementsByTagName("ceil_desc")[i].firstChild.nodeValue;
		var Usage_desc=baseResponse.getElementsByTagName("Usage_desc")[i].firstChild.nodeValue;
		var tbody=document.getElementById("grid_body");
		var t=0;
		var mycurrent_row=document.createElement("TR");
		seq=seq+1;
		mycurrent_row.id=seq;

		var cell=document.createElement("TD");
		var anc=document.createElement("A");
		var url="javascript:loadTable('"+mycurrent_row.id+"')";
		anc.href=url;
		var txtedit=document.createTextNode("EDIT");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);
		//var i=0;		
		var cell2=document.createElement("TD");

		var H_seqno=document.createElement("input");
		H_seqno.type="hidden";
		H_seqno.name="H_seqno";
		H_seqno.value=tbody.rows.length+1;
		cell2.appendChild(H_seqno);

		cell2=document.createElement("TD");

		var H_purposeOR=document.createElement("input");
		H_purposeOR.type="hidden";
		H_purposeOR.name="H_purposeOR";
		H_purposeOR.value=PURPOSE;
		cell2.appendChild(H_purposeOR);
		var currentText=document.createTextNode(purpose_name);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD"); 

		var H_connection_type=document.createElement("input");
		H_connection_type.type="hidden";
		H_connection_type.name="H_connection_type";
		H_connection_type.value=CONNECTION_TYPE;
		cell2.appendChild(H_connection_type);
		var currentText=document.createTextNode(connection_Name);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD"); 

		var H_usage_details=document.createElement("input");
		H_usage_details.type="hidden";
		H_usage_details.name="H_usage_details";
		H_usage_details.value=USAGE_DETAILS;
		cell2.appendChild(H_usage_details);
		var currentText=document.createTextNode(Usage_desc);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD");

		var H_STD_code=document.createElement("input");
		H_STD_code.type="hidden";
		H_STD_code.name="H_STD_code";
		H_STD_code.value=STD_CODE;
		cell2.appendChild(H_STD_code);
		var currentText=document.createTextNode(STD_CODE);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD");

		var H_phone_no=document.createElement("input");
		H_phone_no.type="hidden";
		H_phone_no.name="H_phone_no";
		H_phone_no.value=PHONE_NO;
		cell2.appendChild(H_phone_no);
		var currentText=document.createTextNode(PHONE_NO);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD");

		var H_SerProName=document.createElement("input");
		H_SerProName.type="hidden";
		H_SerProName.name="H_SerProName";
		H_SerProName.value=SERVICE_PROVIDER_NAME;
		cell2.appendChild(H_SerProName);
		var currentText=document.createTextNode(SERVICE_PROVIDER_NAME);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD");

		var H_SerProType=document.createElement("input");
		H_SerProType.type="hidden";
		H_SerProType.name="H_SerProType";
		H_SerProType.value=SERVICE_TYPE;
		cell2.appendChild(H_SerProType);
		var currentText=document.createTextNode(SERVICE_TYPE);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD");

		var H_ceiling_type=document.createElement("input");
		H_ceiling_type.type="hidden";
		H_ceiling_type.name="H_ceiling_type";
		H_ceiling_type.value=CEILING_TYPE;
		cell2.appendChild(H_ceiling_type);
		var currentText=document.createTextNode(ceil_desc);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD");

		var H_ceil_Limit_amt=document.createElement("input");
		H_ceil_Limit_amt.type="hidden";
		H_ceil_Limit_amt.name="H_ceil_Limit_amt";
		H_ceil_Limit_amt.value=CEILING_LIMIT_AMOUNT;
		cell2.appendChild(H_ceil_Limit_amt);
		var currentText=document.createTextNode(CEILING_LIMIT_AMOUNT);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD");

		var H_Particulars=document.createElement("input");
		H_Particulars.type="hidden";
		H_Particulars.name="H_Particulars";
		H_Particulars.value=PARTICULARS;
		cell2.appendChild(H_Particulars);
		var currentText=document.createTextNode(PARTICULARS);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);
		
		cell2=document.createElement("TD");
		var div_text1=document.createElement("div");
		cell2.style.display = 'none';
		var hid_Empid=document.createElement("input");
		hid_Empid.type="hidden";
		hid_Empid.name="hid_Empid";
		hid_Empid.value=EMPLOYEE_ID;
		div_text1.appendChild(hid_Empid);
		cell2.appendChild(div_text1);
		mycurrent_row.appendChild(cell2);
	
		
		cell2=document.createElement("TD");
		var div_text2=document.createElement("div");
		cell2.style.display = 'none';
		var hid_remarks=document.createElement("input");
		hid_remarks.type="hidden";
		hid_remarks.name="Gridremark_id";
		hid_remarks.value=REMARKS;
		div_text2.appendChild(hid_remarks);
		cell2.appendChild(div_text2);		
	
		mycurrent_row.appendChild(cell2);
		
		
		tbody.appendChild(mycurrent_row);
		clear_main_fields();
	}
	}else if(flag=="failure") {
		alert('No Records ... ');
	}
		
}
function   LoadEmpDetails(baseResponse)
{
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	if(flag=="success")
	{                       
		var emp_name=baseResponse.getElementsByTagName("emp_name")[0].firstChild.nodeValue;
		//alert(emp_name);
		var desig_name=baseResponse.getElementsByTagName("desig_name")[0].firstChild.nodeValue;
		//alert(desig_name);
		var OFFICE_ID=baseResponse.getElementsByTagName("OFFICE_ID")[0].firstChild.nodeValue;
		//alert(OFFICE_ID);
		var office_name=baseResponse.getElementsByTagName("office_name")[0].firstChild.nodeValue;
		//alert(office_name);
		document.getElementById("officeId").value=OFFICE_ID;
		document.getElementById("txtemp_desig").value=desig_name;
		document.getElementById("txtemp_office").value=office_name;
		document.getElementById("txtemp_name").value=emp_name;
	}
	else if(flag=="nodata")
	{
		alert("Invalid Employee Id");
	}
	else
	{
		alert("Failed to load");
	}
}

function check()
{
	var tbody=document.getElementById("grid_body");
	var t=0;
	if(tbody.rows.length==0)
	{
		alert("Enter the Details Part");
		return false; 
	}
	if(document.getElementById("txtRemarks").value!="")
	{
		if((document.getElementById("txtRemarks").value.length)>=190)
		{
			alert("Please Enter Remarks below 200 characters");
			document.getElementById("txtRemarks").value="";
			return false;
		}
	}        
	return true;
}

function nullfieldcheck()
{
	var Purp=document.getElementById("cmb_purpose").value;
	//alert("Purpose chosen***"+Purp);        
	if(Purp=="R")
	{ 
		if(document.getElementById("txtEmpId").value=="")
		{
			alert("Please Enter or Choose the Employee ID");
			document.getElementById("txtEmpId").focus();
			return false;

		}

	}
	if(Purp=="O")
	{
		if(document.getElementById("officeId").value=="")
		{
			alert("Please Choose the officeId");
			document.getElementById("officeId").focus();
			return false;

		}

	}
	if(document.getElementById("txtParticulars").value!="")
	{
		if((document.getElementById("txtParticulars").value.length)>=190)
		{
			alert("Please Enter Particulars below 200 characters");
			document.getElementById("txtParticulars").value="";
			return false;
		}
	}
	
	if(document.getElementById("cmb_purpose").value==0)
	{
		alert("Select the purpose of connection");
		document.getElementById("cmb_purpose").focus();
		return false;
	}  
	if(document.getElementById("cmb_connection_type").value==0)
	{
		alert("Select the connection type");  
		document.getElementById("cmb_connection_type").focus();
		return false;        
	}  
	if(document.getElementById("cmb_connection_type").value=="L")
	{
		if(document.getElementById("txtSTD_code").value=="")
		{
			alert("Enter the STD code");
			document.getElementById("txtSTD_code").focus();
			return false;
		} 
	}
	if(document.getElementById("txtphone_no").value=="")
	{
		alert("Enter the Phone Number");
		document.getElementById("txtphone_no").focus();
		return false;
	}
	if(document.getElementById("txtSerProName").value==0)
	{
		alert("Enter the Service Provider Name");
		document.getElementById("txtSerProName").focus();
		return false;    
	}
	if(document.getElementById("txtSerProType").value==0)
	{
		alert("Enter the Service Provider Type");
		document.getElementById("txtSerProType").focus();
		return false;    
	}
	if(document.formPhone_Master.rad_ceiling_type[0].checked==true) 
	{
		if(document.getElementById("txtceil_Limit_amt").value=="")
		{
			alert("Enter the Ceiling amount");
			//document.getElementById("txtceil_Limit_amt").focus();
			return false;   
		}
		else if((document.formPhone_Master.rad_ceiling_type[1].checked==true)||(document.formPhone_Master.rad_ceiling_type[2].checked==true))
		{
			if(document.getElementById("txtceil_Limit_amt").value!=="")
			{
				alert("No Need to enter the ceil amount");
			}
		}
	}
	return true;
}
/////////////////////////////////////////////   ADD & UPDATE & DELETE /////////////////////////////////////////////////////
function ADD_GRID()
{
	var flag=nullfieldcheck();
	if(flag)
	{
		var items=new Array();
		items[0]=document.getElementById("cmb_purpose").value;
		items[1]=document.getElementById("cmb_purpose").options[document.getElementById("cmb_purpose").selectedIndex].text;
		items[2]=document.getElementById("cmb_connection_type").value;
		items[3]=document.getElementById("cmb_connection_type").options[document.getElementById("cmb_connection_type").selectedIndex].text;
		if(document.formPhone_Master.rad_usage_det[0].checked==true)
		{
			items[12]="In Use";
			items[13]=document.formPhone_Master.rad_usage_det[0].value;
		}
		else if(document.formPhone_Master.rad_usage_det[1].checked==true)
		{
			items[12]="Ex Dir";
			items[13]=document.formPhone_Master.rad_usage_det[1].value;
		}
		else if(document.formPhone_Master.rad_usage_det[2].checked==true)
		{
			items[12]="Safe Custody";
			items[13]=document.formPhone_Master.rad_usage_det[2].value;
		}
		else if(document.formPhone_Master.rad_usage_det[3].checked==true)
		{
			items[12]="Disconnected";
			items[13]=document.formPhone_Master.rad_usage_det[3].value;
		}
		if(document.getElementById("txtSTD_code").value=="")
		{
			items[4]="";   
		} 
		else
		{
			items[4]=document.getElementById("txtSTD_code").value;   
		}
		items[5]=document.getElementById("txtphone_no").value;   
		items[6]=document.getElementById("txtSerProName").options[document.getElementById("txtSerProName").selectedIndex].text;
		items[7]=document.getElementById("txtSerProType").options[document.getElementById("txtSerProType").selectedIndex].text;

		if(document.formPhone_Master.rad_ceiling_type[0].checked==true)
		{
			items[8]=document.formPhone_Master.rad_ceiling_type[0].value;
			//items[9]=document.formPhone_Master.rad_ceiling_type[0].text;
			items[9]="Limited";
		}
		else if(document.formPhone_Master.rad_ceiling_type[1].checked==true)
		{
			items[8]=document.formPhone_Master.rad_ceiling_type[1].value;
			//items[9]=document.formPhone_Master.rad_ceiling_type[1].text;
			items[9]="UnLimited";
		}
		else if(document.formPhone_Master.rad_ceiling_type[2].checked==true)
		{
			items[8]=document.formPhone_Master.rad_ceiling_type[2].value;
			//items[9]=document.formPhone_Master.rad_ceiling_type[2].text;
			items[9]="Not Applicable";
		}
		items[10]=document.getElementById("txtceil_Limit_amt").value; 
		//alert(" >>> ....."+items[10]);
		var ceil_amt=items[10];
		 if (items[10]==""){items[10]="0";}
		items[11]=document.getElementById("txtParticulars").value;    
        items[14]=document.getElementById("txtEmpId").value;
       // alert(items[12]);
		 if (items[14]==""){items[14]="0";}
			items[15]=document.getElementById("txtRemarks").value; 

			//alert(items[12]+"  emp....value...."+items[13]);
			//alert("remark....name....."+items[14])
		var tbody=document.getElementById("grid_body");
		var t=0;
		var mycurrent_row=document.createElement("TR");
		seq=seq+1;
		mycurrent_row.id=seq;

		var cell=document.createElement("TD");
		var anc=document.createElement("A");
		var url="javascript:loadTable('"+mycurrent_row.id+"')";
		anc.href=url;
		var txtedit=document.createTextNode("EDIT");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);
		var i=0;
		var cell2;
		cell2=document.createElement("TD");

		var H_seqno=document.createElement("input");
		H_seqno.type="hidden";
		H_seqno.name="H_seqno";
		H_seqno.value=tbody.rows.length+1;
		cell2.appendChild(H_seqno);

		cell2=document.createElement("TD");

		var H_purposeOR=document.createElement("input");
		H_purposeOR.type="hidden";
		H_purposeOR.name="H_purposeOR";
		H_purposeOR.value=items[0];
		cell2.appendChild(H_purposeOR);
		var currentText=document.createTextNode(items[1]);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD"); 

		var H_connection_type=document.createElement("input");
		H_connection_type.type="hidden";
		H_connection_type.name="H_connection_type";
		H_connection_type.value=items[2];
		cell2.appendChild(H_connection_type);
		var currentText=document.createTextNode(items[3]);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD"); 

		var H_usage_details=document.createElement("input");
		H_usage_details.type="hidden";
		H_usage_details.name="H_usage_details";
		H_usage_details.value=items[13];
		cell2.appendChild(H_usage_details);
		var currentText=document.createTextNode(items[12]);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD");

		var H_STD_code=document.createElement("input");
		H_STD_code.type="hidden";
		H_STD_code.name="H_STD_code";
		H_STD_code.value=items[4];
		cell2.appendChild(H_STD_code);
		var currentText=document.createTextNode(items[4]);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD");

		var H_phone_no=document.createElement("input");
		H_phone_no.type="hidden";
		H_phone_no.name="H_phone_no";
		H_phone_no.value=items[5];
		cell2.appendChild(H_phone_no);
		var currentText=document.createTextNode(items[5]);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD");

		var H_SerProName=document.createElement("input");
		H_SerProName.type="hidden";
		H_SerProName.name="H_SerProName";
		H_SerProName.value=items[6];
		cell2.appendChild(H_SerProName);
		var currentText=document.createTextNode(items[6]);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD");

		var H_SerProType=document.createElement("input");
		H_SerProType.type="hidden";
		H_SerProType.name="H_SerProType";
		H_SerProType.value=items[7];
		cell2.appendChild(H_SerProType);
		var currentText=document.createTextNode(items[7]);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD");

		var H_ceiling_type=document.createElement("input");
		H_ceiling_type.type="hidden";
		H_ceiling_type.name="H_ceiling_type";
		H_ceiling_type.value=items[8];
		cell2.appendChild(H_ceiling_type);
		var currentText=document.createTextNode(items[9]);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD");

		var H_ceil_Limit_amt=document.createElement("input");
		H_ceil_Limit_amt.type="hidden";
		H_ceil_Limit_amt.name="H_ceil_Limit_amt";
		H_ceil_Limit_amt.value=items[10];
		cell2.appendChild(H_ceil_Limit_amt);
		var currentText=document.createTextNode(ceil_amt);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD");

		var H_Particulars=document.createElement("input");
		H_Particulars.type="hidden";
		H_Particulars.name="H_Particulars";
		H_Particulars.value=items[11];
		cell2.appendChild(H_Particulars);
		var currentText=document.createTextNode(items[11]);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);
		
	
		
		cell2=document.createElement("TD");
		var div_text1=document.createElement("div");
		cell2.style.display = 'none';
		var hid_Empid=document.createElement("input");
		hid_Empid.type="hidden";
		hid_Empid.name="hid_Empid";
		hid_Empid.value=items[14];
		div_text1.appendChild(hid_Empid);
		cell2.appendChild(div_text1);		
		mycurrent_row.appendChild(cell2);
		
		cell2=document.createElement("TD");
		var div_text2=document.createElement("div");
		cell2.style.display = 'none';
		var hid_remarks=document.createElement("input");
		hid_remarks.type="hidden";
		hid_remarks.name="Gridremark_id";
		hid_remarks.value=items[15];
		div_text2.appendChild(hid_remarks);
		cell2.appendChild(div_text2);		
		mycurrent_row.appendChild(cell2);
		tbody.appendChild(mycurrent_row);
		
		clear_main_fields();
	}
}

function clear_main_fields()
{
	document.getElementById("cmb_purpose").value=0;
	document.getElementById("txtEmpId").value="";
	document.getElementById("txtemp_name").value="";
	document.getElementById("txtemp_desig").value="";
	document.getElementById("officeId").value="";
	document.getElementById("txtemp_office").value="";
	document.getElementById("cmb_connection_type").value=0;
	document.getElementById("txtSTD_code").value="";
	document.getElementById("txtphone_no").value=""; 
	document.getElementById("txtSerProName").value=0;
	document.getElementById("txtSerProType").value=0;
	document.formPhone_Master.rad_ceiling_type[0].checked=true;
	document.getElementById("txtceil_Limit_amt").value="";
	document.getElementById("txtParticulars").value="";

	document.formPhone_Master.cmdadd.style.display='block';
	document.formPhone_Master.cmdupdate.style.display='none';
	document.formPhone_Master.cmddelete.disabled=true;
}
function Load_Details(){
	var  unit_id=document.getElementById("cmbAcc_UnitCode").value;
	var  office_id=document.getElementById("cmbOffice_code").value;
	var url="../../../../../";
	//var cmb_purpose=document.getElementById("cmb_purpose").value;
}

function update_GRID()
{      
	var items=new Array();
	//alert("inside update");
	items[0]=document.getElementById("cmb_purpose").value;
	items[1]=document.getElementById("cmb_purpose").options[document.getElementById("cmb_purpose").selectedIndex].text;
	items[2]=document.getElementById("cmb_connection_type").value;
	items[3]=document.getElementById("cmb_connection_type").options[document.getElementById("cmb_purpose").selectedIndex].text;
	if(document.getElementById("txtSTD_code").value=="")
	{
		items[4]="";   
	} 
	else
	{
		items[4]=document.getElementById("txtSTD_code").value;   
	}
	items[5]=document.getElementById("txtphone_no").value;   
	items[6]=document.getElementById("txtSerProName").value;
	items[7]=document.getElementById("txtSerProType").value;

	if(document.formPhone_Master.rad_ceiling_type[0].checked==true)
	{
		items[8]=document.formPhone_Master.rad_ceiling_type[0].value;
		//items[9]=document.formPhone_Master.rad_ceiling_type[0].text;
		items[9]="Limited";
	}
	else if(document.formPhone_Master.rad_ceiling_type[1].checked==true)
	{
		items[8]=document.formPhone_Master.rad_ceiling_type[1].value;
		//items[9]=document.formPhone_Master.rad_ceiling_type[1].text;
		items[9]="UnLimited";
	}
	else if(document.formPhone_Master.rad_ceiling_type[2].checked==true)
	{
		items[8]=document.formPhone_Master.rad_ceiling_type[2].value;
		//items[9]=document.formPhone_Master.rad_ceiling_type[2].text;
		items[9]="Not Applicable";
	}

	if(document.formPhone_Master.rad_usage_det[0].checked==true)
	{
		items[12]="In Use";
		items[13]=document.formPhone_Master.rad_usage_det[0].value;
	}
	else if(document.formPhone_Master.rad_usage_det[1].checked==true)
	{
		items[12]="Ex Dir";
		items[13]=document.formPhone_Master.rad_usage_det[1].value;
	}
	else if(document.formPhone_Master.rad_usage_det[2].checked==true)
	{
		items[12]="Safe Custody";
		items[13]=document.formPhone_Master.rad_usage_det[2].value;
	}
	else if(document.formPhone_Master.rad_usage_det[3].checked==true)
	{
		items[12]="Disconnected";
		items[13]=document.formPhone_Master.rad_usage_det[3].value;
	}

	items[10]=document.getElementById("txtceil_Limit_amt").value;  
	items[11]=document.getElementById("txtParticulars").value; 
	
    items[14]=document.getElementById("txtEmpId").value;
   // alert(items[12]);
	 if (items[14]==""){items[14]="0";}
		items[15]=document.getElementById("txtRemarks").value; 

		//alert(">>>>> "+items[12]+"  emp....value...."+items[13]);
		//alert("......     ?????????? remark....name....."+items[14]);
	//alert("values assigned");
	var r=document.getElementById(com_id);
	var rcells=r.cells;

	try{rcells.item(1).firstChild.value=items[0];}catch(e){}
	try{rcells.item(1).lastChild.nodeValue=items[1];}catch(e){}

	try{rcells.item(2).firstChild.value=items[2];}catch(e){}
	try{rcells.item(2).lastChild.nodeValue=items[3];}catch(e){}

	try{rcells.item(4).firstChild.value=items[4];}catch(e){}
	try{rcells.item(4).lastChild.nodeValue=items[4];}catch(e){}

	try{rcells.item(5).firstChild.value=items[5];}catch(e){}
	try{rcells.item(5).lastChild.nodeValue=items[5];}catch(e){}

	try{rcells.item(6).firstChild.value=items[6];}catch(e){}
	try{rcells.item(6).lastChild.nodeValue=items[6];}catch(e){}

	try{rcells.item(7).firstChild.value=items[7];}catch(e){}
	try{rcells.item(7).lastChild.nodeValue=items[7];}catch(e){}

	try{rcells.item(8).firstChild.value=items[8];}catch(e){}
	try{rcells.item(8).lastChild.nodeValue=items[9];}catch(e){}

	try{rcells.item(9).firstChild.value=items[10];}catch(e){}
	try{rcells.item(9).lastChild.nodeValue=items[10];}catch(e){}

	try{rcells.item(10).firstChild.value=items[11];}catch(e){}
	try{rcells.item(10).lastChild.nodeValue=items[11];}catch(e){}

	try{rcells.item(3).firstChild.value=items[13];}catch(e){}
	try{rcells.item(3).lastChild.nodeValue=items[12];}catch(e){}
	
	try{rcells.item(11).firstChild.value=items[11];}catch(e){}
	try{rcells.item(11).lastChild.nodeValue=items[11];}catch(e){}
	
	try{rcells.item(14).firstChild.value=items[14];}catch(e){}
	try{rcells.item(14).lastChild.nodeValue=items[14];}catch(e){}
	
	try{rcells.item(15).firstChild.value=items[15];}catch(e){}
	try{rcells.item(15).lastChild.nodeValue=items[15];}catch(e){}
	

	alert("Record Updated");
	clearall();
}

function delete_GRID()
{
	if(confirm("Do you want to delete ?"))
	{
		var tbody=document.getElementById("mytable");
		var r=document.getElementById(com_id);
		var ri=r.rowIndex;
		tbody.deleteRow(ri);
		clearall();
	}
}


function loadTable(scod)
{
	com_id=scod;                                    
	//clearall();
	//alert(com_id);
	var r=document.getElementById(com_id);
	var rcells=r.cells;

	try {
		
		document.getElementById("cmb_purpose").value=rcells.item(1).firstChild.value;}catch(e){}
	//alert(rcells.item(1).firstChild.value);
	//alert(document.getElementById("cmb_purpose").value);
	try{
		var cmb=document.getElementById("cmb_connection_type");
		cmb.value=rcells.item(2).firstChild.value; 
		
	
		
		} catch(e){}
	//alert(document.getElementById("cmb_connection_type").value);
	if(rcells.item(3).firstChild.value=="IU") 
		document.formPhone_Master.rad_usage_det[0].checked=true;
	else if(rcells.item(3).firstChild.value=="ED")
		document.formPhone_Master.rad_usage_det[1].checked=true;
	else if(rcells.item(3).firstChild.value=="SC")
		document.formPhone_Master.rad_usage_det[2].checked=true;
	else if(rcells.item(3).firstChild.value=="DC")
		document.formPhone_Master.rad_usage_det[3].checked=true;
	//alert(rcells.item(2).firstChild.value);

	try{document.getElementById("txtSTD_code").value=rcells.item(4).firstChild.value;} catch(e){} 
	//alert(document.getElementById("txtSTD_code").value);
	try{document.getElementById("txtphone_no").value=rcells.item(5).firstChild.value;} catch(e){} 
	//alert(rcells.item(7).firstChild.value);
	try{document.getElementById("txtSerProName").value=rcells.item(6).firstChild.value;} catch(e){} 
	//alert(document.getElementById("txtSerProName").value);
	try{document.getElementById("txtSerProType").value=rcells.item(7).firstChild.value;} catch(e){} 
	//alert(document.getElementById("txtSerProType").value);
	try{document.getElementById("txtceil_Limit_amt").value=rcells.item(9).firstChild.value;} catch(e){} 
	//alert(document.getElementById("txtceil_Limit_amt").value);
	if(rcells.item(8).firstChild.value=="L")
		document.formPhone_Master.rad_ceiling_type[0].checked=true;
	else if(rcells.item(8).firstChild.value=="U")
		document.formPhone_Master.rad_ceiling_type[1].checked=true;
	else if(rcells.item(8).firstChild.value=="N")
		document.formPhone_Master.rad_ceiling_type[2].checked=true;
	try{document.getElementById("txtEmpId").value=rcells.item(12).firstChild.value;} catch(e){} 
	try{document.getElementById("txtRemarks").value=rcells.item(13).firstChild.value;} catch(e){}
	try{document.getElementById("txtParticulars").value=rcells.item(10).firstChild.value;} catch(e){} 
	//alert(document.getElementById("txtParticulars").value);

	document.formPhone_Master.cmdadd.style.display='none';
	document.formPhone_Master.cmdupdate.style.display='block';
	document.formPhone_Master.cmddelete.disabled=false;
}

/////////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;
var my_window;
function servicepopup()
{
	if (winemp && winemp.open && !winemp.closed) 
	{
		winemp.resizeTo(500,600);
		winemp.moveTo(200,200); 
		winemp.focus();
		return ;
	}
	else
	{
		winemp=null;
	}
	winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
	winemp.moveTo(250,250);  
	winemp.focus();
}
function doParentEmp(emp)
{
	document.formPhone_Master.txtEmpId.value=emp;
	Load_emp_details();
}
var winjob;
var txtid1="";
var txtid2="";
function jobpopup(s1,s2) {
	txtid1=s1;
	txtid2=s2;
	if (winjob && winjob.open && !winjob.closed) {
		winjob.resizeTo(500, 500);
		winjob.moveTo(250, 250);
		winjob.focus();
		return;
	} else {
		winjob = null;
	}

	winjob = window.open(
			"../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp",
			"JobSearch",
	"status=1,height=500,width=500,resizable=YES, scrollbars=yes");
	winjob.moveTo(250, 250);
	winjob.focus();	
	document.formPhone_Master.officeId.focus();
}
function doParentJob(jobid, deptid) {
	//document.leave_unavail.dept_id.value = deptid;	
	document.getElementById('officeId').value=jobid;
	//loadOffice(jobid);
	return true;
}
function loadOffice(id) {
	if (id == "" || id == null) {
		alert("Enter or (Select An Office..Then Click choose..)");
		document.formPhone_Master.officeId.focus();

	} else {		
		var dept_id = document.formPhone_Master.dept_id.value;		
		if (dept_id == "")
			dept_id = "TWAD";
		if (dept_id != "TWAD") {
			var url = "../../../../ServletLoadOfficeDetails.con?command=OtherOffice&ID="
				+ id + "&DeptId=" + dept_id;
		} else {
			var url = "../../../../ServletLoadOfficeDetails.con?command=TwadOffice&ID="
				+ id;
		}		
		var req = getTransport();		
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			LoadOfficeDetails(req);
		};
		req.send(null);
	}
}
function LoadOfficeDetails(req) {
	if (req.readyState == 4) {
		if (req.status == 200) {			
			var response = req.responseXML.getElementsByTagName("response")[0];
			var flag = response.getElementsByTagName("flag")[0].firstChild.nodeValue;
			if (flag == "success") {				
				var off_name = response.getElementsByTagName("name")[0];
				document.getElementById(txtid2).value = off_name.firstChild.nodeValue;
			}
		} else {
			var mess = response.getElementsByTagName("message")[0].firstChild.nodeValue;
			alert("Error Occured : \n" + mess);
			document.leave_unavail.txtOfficeName.value = "";
		}
	}
}

/*********************function for chosen purpose ********************/
function chooseCType()
{
	var Purp=document.getElementById("cmb_purpose").value;
	//alert("Purpose chosen***"+Purp);        
	if((Purp=="O")||(Purp=="R"))
	{        	
		if(Purp=="R"){
			document.getElementById('txtEmpId').disabled=false;
			document.getElementById('empImg').style.display='inline';
		}else{
			document.getElementById('txtEmpId').disabled=true;
			document.getElementById('empImg').style.display='none';
		}
		document.formPhone_Master.cmb_connection_type.selectedIndex=0;
		document.formPhone_Master.cmb_connection_type.options[0].disabled=false;
		document.formPhone_Master.cmb_connection_type.options[1].disabled=false;
		document.formPhone_Master.cmb_connection_type.options[2].disabled=false;
	}
	else if(Purp="F")
	{
		document.getElementById('txtEmpId').disabled=false;
		document.getElementById('empImg').style.display='inline';
		document.formPhone_Master.cmb_connection_type.selectedIndex=1;
		document.formPhone_Master.cmb_connection_type.options[2].disabled=true;
		document.getElementById('txtSTD_code').disabled=false;
	}
}
function numbersonly1(e, t) {
	//alert("t.length "+t.value.length);
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
function callServer(command, param) {
	if (command == "Check") {
		var txtOffice_Id = document.formPhone_Master.officeId.value;
		var flag = document.formPhone_Master.officeId.value;
		if (flag.length > 0) {
			url="../../../../../phone_master_servlet?command=check&txtOffice_Id=" + txtOffice_Id;
			var req = getTransport();
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				officeprocessResponse(req);
			};
			req.send(null);
		}
	}
}
function officeprocessResponse(req) {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			updateResponse(baseResponse);
		}
	}
}
function updateResponse(response) {
	var res = response.getElementsByTagName("status")[0].firstChild.nodeValue;
	if (res == "success"){
		if (response.getElementsByTagName("command")[0].firstChild.nodeValue == "existing") {
			document.formPhone_Master.txtemp_office.value=response.getElementsByTagName("officename")[0].firstChild.nodeValue;
		}else if (response.getElementsByTagName("command")[0].firstChild.nodeValue == "Notexisting") {
			alert("The given office Id do not Exist");
			document.formPhone_Master.officeId.value="";
			document.formPhone_Master.officeId.focus();
		}else if (response.getElementsByTagName("command")[0].firstChild.nodeValue == "Notexisting_1") {
			alert("The given office Id do not Exist");
			document.formPhone_Master.officeId.value="";
			document.formPhone_Master.officeId.focus();
		}
	} else {

		alert("Process failure");
	}

}