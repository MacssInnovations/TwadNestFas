
var seq=0;
var sl_no=1;

var acc_head_code="";
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

function checkNull()
{
	if(document.getElementById("butSub").disabled==false)
	{ document.getElementById("butSub").disabled=true; }
	
	var tbody;
    if(document.getElementById("Proceeding_Year").value=="")
    {
            alert("Enter the Proceeding Year");
            if(document.getElementById("butSub").disabled==true)
        	{ document.getElementById("butSub").disabled=false; }
            return false;    
    }
    if(document.getElementById("Proceeding_Month").value=="s")
    {
            alert("Select the Proceeding Month"); 
            if(document.getElementById("butSub").disabled==true)
        	{ document.getElementById("butSub").disabled=false; }
            return false;
    }
    if(document.getElementById("txtBillDate").value.length==0)
    {
            alert("Enter the Bill Date");  
            if(document.getElementById("butSub").disabled==true)
        	{ document.getElementById("butSub").disabled=false; }
            return false;    
    }     
    if(document.getElementById("notgpfdetails").style.display=="block")
    {
    	tbody=document.getElementById("grid_body");
    	 if(tbody.rows.length==0)
    	    {
    	            alert("Enter the Details Part");   
    	            if(document.getElementById("butSub").disabled==true)
    	        	{ document.getElementById("butSub").disabled=false; }
    	            return false; 
    	    }
    }
    if(document.getElementById("gpfdetails").style.display=="block")
    {
    	tbody=document.getElementById("grid_body1");
    	 if(tbody.rows.length==0)
    	    {
    	            alert("Enter the Details Part");     
    	            if(document.getElementById("butSub").disabled==true)
    	        	{ document.getElementById("butSub").disabled=false; }
    	            return false; 
    	    }
    }
    
    
   
          

	var billdate_grid=document.getElementById("txtBillDate").value;
	var biisp_grid=billdate_grid.split("/");
	
	var txtScrutinyDate=document.getElementById("txtProceedingDate").value;
	 var passsplit=txtScrutinyDate.split("/");
	
	 if(biisp_grid[2]<passsplit[2])
	 {
		 alert("Bill Date Should not be less than Sanction Datesss");
		 document.getElementById("txtBillDate").value="";
		  if(document.getElementById("butSub").disabled==true)
      	{ document.getElementById("butSub").disabled=false; }
		 return false;
		
	 }
	 else if(biisp_grid[2]==passsplit[2])
	 {
		
   	 if(biisp_grid[1]<passsplit[1])
   	 {
   		 alert("Bill Date Should not be less than Sanction Date");
		 document.getElementById("txtBillDate").value="";
		  if(document.getElementById("butSub").disabled==true)
      	{ document.getElementById("butSub").disabled=false; }
		 return false;
   	 }
   	 else if(biisp_grid[1]==passsplit[1])
   	 {
   		 var billspl;
   		// alert(biisp_grid[0].length);
   		 if(biisp_grid[0].length==2)
   		 {
   			billspl=biisp_grid[0];
   		 }
   		 else
   		 {
   			billspl="0"+biisp_grid[0];
   		 }
   		 if(billspl<passsplit[0])
       	 {
   			 alert("Bill Date Should not be less then SanctionDate");
   			 document.getElementById("txtBillDate").value="";
   		  if(document.getElementById("butSub").disabled==true)
      	{ document.getElementById("butSub").disabled=false; }
   			 return false;
       	 } 
   	 }
	 }
	 
}


function checkNull1()
{

	var tbody;
	var t=0;
    if(document.getElementById("Proceeding_Year").value=="")
    {
            alert("Enter the Proceeding Year");
            //return false;  
            t=1;
    }
    if(document.getElementById("Proceeding_Month").value=="s")
    {
            alert("Select the Proceeding Month");           
            //return false;  
            t=1;
    }
    if(document.getElementById("txtBillDate").value.length==0)
    {
            alert("Enter the Bill Date");           
            //return false;  
            t=1;
    }     
    if(document.getElementById("notgpfdetails").style.display=="block")
    {
    	tbody=document.getElementById("grid_body");
    	 if(tbody.rows.length==0)
    	    {
    	            alert("Enter the Details Part");         
    	            //return false;  
    	            t=1;
    	    }
    }
    if(document.getElementById("gpfdetails").style.display=="block")
    {
    	tbody=document.getElementById("grid_body1");
    	 if(tbody.rows.length==0)
    	    {
    	            alert("Enter the Details Part");         
    	            //return false;  
    	            t=1;
    	    }
    }
    
    
   
          

	var billdate_grid=document.getElementById("txtBillDate").value;
	var biisp_grid=billdate_grid.split("/");
	
	var txtScrutinyDate=document.getElementById("txtProceedingDate").value;
	 var passsplit=txtScrutinyDate.split("/");
	
	 if(biisp_grid[2]<passsplit[2])
	 {
		 alert("Bill Date Should not be less than Sanction Datesss");
		 document.getElementById("txtBillDate").value="";
		  //return false;  
         t=1;
		
	 }
	 else if(biisp_grid[2]==passsplit[2])
	 {
		
   	 if(biisp_grid[1]<passsplit[1])
   	 {
   		 alert("Bill Date Should not be less than Sanction Date");
		 document.getElementById("txtBillDate").value="";
		  //return false;  
         t=1;
   	 }
   	 else if(biisp_grid[1]==passsplit[1])
   	 {
   		 var billspl;
   		// alert(biisp_grid[0].length);
   		 if(biisp_grid[0].length==2)
   		 {
   			billspl=biisp_grid[0];
   		 }
   		 else
   		 {
   			billspl="0"+biisp_grid[0];
   		 }
   		 if(billspl<passsplit[0])
       	 {
   			 alert("Bill Date Should not be less then SanctionDate");
   			 document.getElementById("txtBillDate").value="";
   		  //return false;  
             t=1;
       	 } 
   	 }
	 }
	// alert("t >> "+t);
	if(t==1) { return false;}  
	else {
		 if(document.getElementById("butSub").disabled==false)
	    	 document.getElementById("butSub").disabled=true;
		return true;
		
	}  
}
/*
function calJSP(jspname)
{    
 alert("calJSP---:"+jspname);  
 winemp= window.open(jspname,"calJSP","status=1,height=1100,width=1100,resizable=YES, scrollbars=yes");	
 winemp.moveTo(0,0);  
 winemp.focus(); onCond();
 self.close();

}
function onCond(){
	 
	 alert('****** testing1123');
	 document.frm_BillTokenRegisterEntry_WithProceeding.butSub.disabled=true ;
	  if(document.frm_BillTokenRegisterEntry_WithProceeding.butSub.disabled==true)
	 document.frm_BillTokenRegisterEntry_WithProceeding.butSub.disabled=false; 
}*/

function funcCall()
{
alert(document.getElementById("pay_amt").value);	
}
// joe Added Function
function loadProceedingNo1(path,cmd)
{
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	if(cboBillMajorType=="0")
		{
		alert("Choose Major Type");
		return false;
		}
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	if(cboBillMinorType=="0")
		{
		alert("Choose Minor Type");
		return false;
		}
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	if(cboBillSubType=="0")
		{
		alert("Choose Sub Type");
		return false;
		}

	var Proceeding_Year = document.getElementById("Proceeding_Year").value;
	var Proceeding_Month = document.getElementById("Proceeding_Month").value;
	var url = path+ "/Bills_Token_Register_with_SP?command="+cmd+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cboBillMajorType="+cboBillMajorType
	+"&cboBillMinorType="+cboBillMinorType+"&cboBillSubType="+cboBillSubType+"&Proceeding_Year="+Proceeding_Year+"&Proceeding_Month="+Proceeding_Month;;
//alert(url);

	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}



function ProNo_Details(path,cmd){

	//alert("%%%%%%");
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	if(cboBillMajorType=="0")
		{
		alert("Choose Major Type");
		return false;
		}
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	if(cboBillMinorType=="0")
		{
		alert("Choose Minor Type");
		return false;
		}
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	if(cboBillSubType=="0")
		{
		alert("Choose Sub Type");
		return false;
		}
	

	var Proceeding_Year = document.getElementById("Proceeding_Year").value;
	var Proceeding_Month = document.getElementById("Proceeding_Month").value;
    var combo1 = document.getElementById("txtProceedingNo");
    var txtProceedingNo = combo1.options[combo1.selectedIndex].text;

    var proValue = document.getElementById("txtProceedingNo").value;
	
	
	//var txtProceedingNo = document.getElementById("txtProceedingNo").value;
	//alert(txtProceedingNo);
	
	
	if(txtProceedingNo=="")
		{
		alert("Choose Proceeding No");
		return false;
		}
	
	var url = path+ "/Bills_Token_Register_with_SP?command="+cmd+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	"&cmbOffice_code="+cmbOffice_code+"&cboBillMajorType="+cboBillMajorType+"&cboBillMinorType="+cboBillMinorType+
	"&Proceeding_Year="+Proceeding_Year+"&Proceeding_Month="+Proceeding_Month+
	"&cboBillSubType="+cboBillSubType+"&txtProceedingNo="+txtProceedingNo+"&proValue="+proValue;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);	
	
}



function mtc_hide()
{
	var minortypedesc=document.getElementById("cboBillMinorType").
			options[document.getElementById("cboBillMinorType").selectedIndex].text;
	//alert(minortypedesc);
	if(minortypedesc=="GPF")
	{
	document.getElementById("yesid").style.display="block";
	document.getElementById("noid").style.display="none";
	}
	else
	{
		document.getElementById("yesid").style.display="block";
		document.getElementById("noid").style.display="block";
	}
}

function checksan()
{
	var billdate_grid=document.getElementById("txtBillDate").value;
	var biisp_grid=billdate_grid.split("/");
	
	var txtScrutinyDate=document.getElementById("txtProceedingDate").value;
	 var passsplit=txtScrutinyDate.split("/");
	
	 if(biisp_grid[2]<passsplit[2])
	 {
		 alert("Bill Date Should not be less than Sanction Datesss");
		 document.getElementById("txtBillDate").value="";
		 return false;
		
	 }
	 else if(biisp_grid[2]==passsplit[2])
	 {
		// alert(":::"+biisp_grid[1]);
		// alert(":ppp::"+passsplit[1]);
   	 if(biisp_grid[1]<passsplit[1])
   	 {
   		 alert("Bill Date Should not be less than Sanction Date");
		 document.getElementById("txtBillDate").value="";
		 return false;
   	 }
   	 else if(biisp_grid[1]==passsplit[1])
   	 {
   		 var billspl;
   		// alert(biisp_grid[0].length);
   		 if(biisp_grid[0].length==2)
   		 {
   			billspl=biisp_grid[0];
   		 }
   		 else
   		 {
   			billspl="0"+biisp_grid[0];
   		 }
   		 if(billspl<passsplit[0])
       	 {
   			 alert("Bill Date Should not be less then SanctionDate");
   			 document.getElementById("txtBillDate").value="";
   			 return false;
       	 } 
   	 }
	 }
	
}

function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			
			if (command == "getProceedingNo") {
				getProceedingNo_res(baseResponse);
			}
//			
			else if (command == "Acc_Head_Code") {
				 //alert("manipulate");
				Acc_Head_Code11(baseResponse);
			}
			else if(command == "AddNew"){

				var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

				var BillNo1 = baseResponse.getElementsByTagName("BillNo")[0].firstChild.nodeValue;
				
				if (flag == "success") {
					alert("Record Inserted Successfully");
					document.getElementById("butSub").disabled=false;
					document.getElementById("imgfld").style.visibility = "hidden";
					//refresh(); 18-05-2018
					
					window.location.reload(true);
					
				} else {
					document.getElementById("butSub").disabled=false;
					document.getElementById("imgfld").style.visibility = "hidden";
					alert("Record Insertion Failed");
				}

			}
			else if (command == "getempname_off") {
				 //alert("manipulate");
				getempname_re(baseResponse);
			}
			
				else if (command == "getSLSProDetails") {
				 //alert("manipulate");
				jourDetLoad(baseResponse);
			} 
			
			else if (command == "getBillMajorType") {
				 //alert("manipulate");
				firstLoad(baseResponse);
			} else if (command == "getBillMinorType") {
				// alert("manipulate");
				getBillMinorType1(baseResponse);
			} else if (command == "getBillsubType") {
				// getEstimateSanctionDate
				// alert("manipulate");
				getBillsubType1(baseResponse);
			} else if (command == "calculateBudget") {
				// alert("manipulate");
				calculateBudget1(baseResponse);
			} else if (command == "getOffice") {
				// alert("manipulate");
				getOffice1(baseResponse);
			} else if (command == "saveFunc") {
				 //alert("manipulate saveFunc");
				saveFunc1(baseResponse);
			} else if (command == "Edit") {
				// alert("manipulate");
				Edit1(baseResponse);
			} else if (command == "deleted") {
				// alert("manipulate");
				deleteRow(baseResponse);
			} else if (command == "update") {
				updateRow(baseResponse);
			} else if (command == "ClearAll") {
				ClearAll1(baseResponse);
			} else if (command == "IVno") {
				IVno1(baseResponse);
			} else if (command == "InvoiceDetails") {
				InvoiceDetails1(baseResponse);
			}
			
			else if (command == "getProceedingDetails") {
				getProceedingDetails_res(baseResponse);
			}
			else if (command == "loadHead") {
				loadHead_res(baseResponse);
			}
			else if (command == "getAccDesc") {
				getAccDesc_res(baseResponse);
			}
			else if (command == "budgetProv") {
				budgetProv_res(baseResponse);
			}
			
			
		}
	}
}


function jourDetLoad(baseResponse)
{
var name_emp="";
var common_name="";
	var procdate = baseResponse.getElementsByTagName("procdate");
	
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="success"){
		
		var tbody=document.getElementById("tblList");
	   var t=0;
       for(t=tbody.rows.length-1;t>=0;t--)
           {
              tbody.deleteRow(0);
           }   
       var s_no=1;
       var crtotal = baseResponse.getElementsByTagName("tot_cr")[0].firstChild.nodeValue;
   	var tot_dr = baseResponse.getElementsByTagName("tot_dr")[0].firstChild.nodeValue;
   //alert(crtotal);
   document.getElementById("crtotal").value=crtotal;
   document.getElementById("drtotal").value=tot_dr;  
  // document.getElementById("coutn_sanc").value=procdate.length;
	var PROCESS_FLOW_ID=baseResponse.getElementsByTagName("PROCESS_FLOW_ID");
     /// alert(bill_no);
	document.getElementById("coutn_sanc").value=PROCESS_FLOW_ID.length;
	

	for(var i=0;i<PROCESS_FLOW_ID.length;i++){
		var empid = baseResponse.getElementsByTagName("empid")[i].firstChild.nodeValue;
		var EMPLOYEE_NAME = baseResponse.getElementsByTagName("EMPLOYEE_NAME")[i].firstChild.nodeValue;
		var bill_no=baseResponse.getElementsByTagName("bill_no")[i].firstChild.nodeValue;
		var BILL_DATE=baseResponse.getElementsByTagName("BILL_DATE")[i].firstChild.nodeValue;
	var procdate = baseResponse.getElementsByTagName("procdate")[i].firstChild.nodeValue;
	var ACCOUNT_HEAD_CODE=baseResponse.getElementsByTagName("PAYMENT_HEAD_OF_AC")[i].firstChild.nodeValue;
	var ACCOUNT_HEAD_DESC=baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[i].firstChild.nodeValue;
	var cr_amt=baseResponse.getElementsByTagName("cr_amt")[i].firstChild.nodeValue;
	var dr_amt=baseResponse.getElementsByTagName("dr_amt")[i].firstChild.nodeValue;
	var cr_dr=baseResponse.getElementsByTagName("cr_dr")[i].firstChild.nodeValue;
	
//	var sanAmt = baseResponse.getElementsByTagName("sanAmt")[i].firstChild.nodeValue;
	var PAYMENT_AMOUNT = baseResponse.getElementsByTagName("PAYMENT_AMOUNT")[i].firstChild.nodeValue;
	
	var sanc_id=baseResponse.getElementsByTagName("HRMS_SANCTION_ID")[i].firstChild.nodeValue;
	var Office_Id=baseResponse.getElementsByTagName("Office_Id")[i].firstChild.nodeValue;
	//var off_name=baseResponse.getElementsByTagName("off_name")[i].firstChild.nodeValue;
	
	var name = baseResponse.getElementsByTagName("EMPLOYEE_NAME")[i].firstChild.nodeValue;
	if(name==name_emp)
	{
		common_name="";
	}
	else
	{
		common_name=name;
		name_emp=name;
	}
	
	
	var tbody=document.getElementById("tblList");
	var tr_ele=document.createElement("TR");
	
	var cell1=document.createElement("TD");
	
	var text=document.createTextNode(s_no);
	cell1.appendChild(text);
	tr_ele.appendChild(cell1);
	
	var cell1=document.createElement("TD");
	cell1.setAttribute('align','left');
	var empname=  document.createElement("input");
	empname.type="hidden";
	empname.id="employeeid";
	empname.name="employeeid";
	empname.value=empid;
	var text=document.createTextNode(common_name);
	cell1.appendChild(text);
	cell1.appendChild(empname);
	tr_ele.appendChild(cell1);
	
	var cell1=document.createElement("TD");
	cell1.setAttribute('align','left');
	var head=  document.createElement("input");
	head.type="hidden";
	head.id="head";
	head.name="head";
	head.value=ACCOUNT_HEAD_CODE;
	var text=document.createTextNode(ACCOUNT_HEAD_CODE+"-"+ACCOUNT_HEAD_DESC);
	cell1.appendChild(text);
	cell1.appendChild(head);
	tr_ele.appendChild(cell1);
	
	var cell1=document.createElement("TD");
	cell1.setAttribute('align','Right');
	var dr_amt1=  document.createElement("input");
	dr_amt1.type="hidden";
	dr_amt1.id="dr";
	dr_amt1.name="dr";
	dr_amt1.value=dr_amt;
	var text_dr_amt=document.createTextNode(dr_amt);
	cell1.appendChild(text_dr_amt);
	cell1.appendChild(dr_amt1);
	tr_ele.appendChild(cell1);
	
	var cell1=document.createElement("TD");
	cell1.setAttribute('align','Right');
	var cr_amt1=  document.createElement("input");
	cr_amt1.type="hidden";
	cr_amt1.id="cr";
	cr_amt1.name="cr";
	cr_amt1.value=cr_amt;
	var text_cr_amt=document.createTextNode(cr_amt);
	cell1.appendChild(text_cr_amt);
	cell1.appendChild(cr_amt1);
	tr_ele.appendChild(cell1);
	
	
	
	var pay_amt=  document.createElement("input");
	pay_amt.type="hidden";
	pay_amt.id="payment";
	pay_amt.name="payment";
	pay_amt.value=PAYMENT_AMOUNT;	
	cell1.appendChild(pay_amt);
	

	var cr_dr_val=  document.createElement("input");
	cr_dr_val.type="hidden";
	cr_dr_val.id="cr_dr_val";
	cr_dr_val.name="cr_dr_val";
	cr_dr_val.value=cr_dr;	
	cell1.appendChild(cr_dr_val);
	
	var emp_id_val=  document.createElement("input");
	emp_id_val.type="hidden";
	emp_id_val.id="emp_id";
	emp_id_val.name="emp_id";
	emp_id_val.value=empid;	
	cell1.appendChild(emp_id_val);
	
	var bill_no_val=  document.createElement("input");
	bill_no_val.type="hidden";
	bill_no_val.id="bill_no";
	bill_no_val.name="bill_no";
	bill_no_val.value=bill_no;	
	cell1.appendChild(bill_no_val);

	var BILL_DATE_val=  document.createElement("input");
	BILL_DATE_val.type="hidden";
	BILL_DATE_val.id="BILL_DATE";
	BILL_DATE_val.name="BILL_DATE";
	BILL_DATE_val.value=BILL_DATE;	
	cell1.appendChild(BILL_DATE_val);
	
	tr_ele.appendChild(cell1);
	tbody.appendChild(tr_ele);
	s_no++;
}
	}else if(flag=="nodata1"){
		alert('Already SLS Jounal Created ..... ');
	}

	}


/*

function jourDetLoad(baseResponse)
{
	
	var procdate = baseResponse.getElementsByTagName("procdate");
	
	
	var tbody=document.getElementById("tblList");
	   var t=0;
       for(t=tbody.rows.length-1;t>=0;t--)
           {
              tbody.deleteRow(0);
           }   
       var s_no=1;
       var crtotal = baseResponse.getElementsByTagName("tot_cr")[0].firstChild.nodeValue;
   	var tot_dr = baseResponse.getElementsByTagName("tot_dr")[0].firstChild.nodeValue;
   //alert(crtotal);
   document.getElementById("crtotal").value=crtotal;
   document.getElementById("drtotal").value=tot_dr;  
   document.getElementById("coutn_sanc").value=procdate.length;
   
       
for(var i=0;i<procdate.length;i++){
	var procdate = baseResponse.getElementsByTagName("procdate")[i].firstChild.nodeValue;
	var sanAmt = baseResponse.getElementsByTagName("sanAmt")[i].firstChild.nodeValue;
	var PAYMENT_AMOUNT = baseResponse.getElementsByTagName("PAYMENT_AMOUNT")[i].firstChild.nodeValue;
	var empid = baseResponse.getElementsByTagName("empid")[i].firstChild.nodeValue;
	var EMPLOYEE_NAME = baseResponse.getElementsByTagName("EMPLOYEE_NAME")[i].firstChild.nodeValue;
	var sanc_id=baseResponse.getElementsByTagName("HRMS_SANCTION_ID")[i].firstChild.nodeValue;
	var Office_Id=baseResponse.getElementsByTagName("Office_Id")[i].firstChild.nodeValue;
	var off_name=baseResponse.getElementsByTagName("off_name")[i].firstChild.nodeValue;
	var cr_amt=baseResponse.getElementsByTagName("cr_amt")[i].firstChild.nodeValue;
	var dr_amt=baseResponse.getElementsByTagName("dr_amt")[i].firstChild.nodeValue;
	var cr_dr=baseResponse.getElementsByTagName("cr_dr")[i].firstChild.nodeValue;
	var ACCOUNT_HEAD_CODE=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[i].firstChild.nodeValue;
	var bill_no=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[i].firstChild.nodeValue;
	var ACCOUNT_HEAD_DESC=baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[i].firstChild.nodeValue;
	var tbody=document.getElementById("tblList");
	var tr_ele=document.createElement("TR");
	
	var cell1=document.createElement("TD");
	
	var text=document.createTextNode(s_no);
	cell1.appendChild(text);
	
	tr_ele.appendChild(cell1);
	
	var cell1=document.createElement("TD");
	cell1.setAttribute('align','left');
	var head=  document.createElement("input");
	head.type="hidden";
	head.id="head";
	head.name="head";
	head.value=ACCOUNT_HEAD_CODE;
	var text=document.createTextNode(ACCOUNT_HEAD_CODE+"-"+ACCOUNT_HEAD_DESC);
	cell1.appendChild(text);
	cell1.appendChild(head);
	tr_ele.appendChild(cell1);
	
	var cell1=document.createElement("TD");
	cell1.setAttribute('align','Right');
	var cr_amt1=  document.createElement("input");
	cr_amt1.type="hidden";
	cr_amt1.id="cr";
	cr_amt1.name="cr";
	cr_amt1.value=cr_amt;
	var text_cr_amt=document.createTextNode(cr_amt);
	cell1.appendChild(text_cr_amt);
	cell1.appendChild(cr_amt1);
	tr_ele.appendChild(cell1);
	
	var cell1=document.createElement("TD");
	cell1.setAttribute('align','Right');
	var dr_amt1=  document.createElement("input");
	dr_amt1.type="hidden";
	dr_amt1.id="dr";
	dr_amt1.name="dr";
	dr_amt1.value=dr_amt;
	var text_dr_amt=document.createTextNode(dr_amt);
	cell1.appendChild(text_dr_amt);
	cell1.appendChild(dr_amt1);
	
	var pay_amt=  document.createElement("input");
	pay_amt.type="hidden";
	pay_amt.id="payment";
	pay_amt.name="payment";
	pay_amt.value=PAYMENT_AMOUNT;	
	cell1.appendChild(pay_amt);
	
	
	
	var emp_d_val=  document.createElement("input");
	emp_d_val.type="hidden";
	emp_d_val.id="emp_id";
	emp_d_val.name="emp_id";
	emp_d_val.value=empid;	
	cell1.appendChild(emp_d_val);
	

	var cr_dr_val=  document.createElement("input");
	cr_dr_val.type="hidden";
	cr_dr_val.id="cr_dr_val";
	cr_dr_val.name="cr_dr_val";
	cr_dr_val.value=cr_dr;	
	cell1.appendChild(cr_dr_val);
	
	
	var BilNo_val=  document.createElement("input");
	BilNo_val.type="hidden";
	BilNo_val.id="BilNo";
	BilNo_val.name="BilNo_val";
	BilNo_val.value=bill_no;	
	cell1.appendChild(BilNo_val);
	
		
	cell1.appendChild(pay_amt);
	
	tr_ele.appendChild(cell1);
	tbody.appendChild(tr_ele);
	s_no++;
}

	}

*/

function initialLoad(path) {
	//alert(path);

	var url = path
			+ "/Bills_Token_Register_with_SP?command=getBillMajorType";
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}

function getOffice(path) {
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	var url = path
			+ "/Bills_Token_Register_with_SP?command=getOffice&txtEmpID_mas="
			+ txtEmpID_mas;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}

function loadProceedingNo(path)
{
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	if(cboBillMajorType=="0")
		{
		alert("Choose Major Type");
		return false;
		}
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	if(cboBillMinorType=="0")
		{
		alert("Choose Minor Type");
		return false;
		}
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	if(cboBillSubType=="0")
		{
		alert("Choose Sub Type");
		return false;
		}
	var len = document.frm_BillTokenRegisterEntry_WithProceeding.Up_Month.length;
	

		
		if ( document.frm_BillTokenRegisterEntry_WithProceeding.Up_Month[0].checked==true ) 
			Up_Month="particular_cb";
		else if ( document.frm_BillTokenRegisterEntry_WithProceeding.Up_Month[1].checked =true ) 
			Up_Month="more_cb";
		


	//var Up_Month = document.getElementById("Up_Month").value;
	var Proceeding_Year = document.getElementById("Proceeding_Year").value;
	var Proceeding_Month = document.getElementById("Proceeding_Month").value;
	var url = path+ "/Bills_Token_Register_with_SP?command=getProceedingNo&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cboBillMajorType="+cboBillMajorType
	+"&cboBillMinorType="+cboBillMinorType+"&cboBillSubType="+cboBillSubType+"&Proceeding_Year="+Proceeding_Year+"&Proceeding_Month="+Proceeding_Month+"&Up_Month="+Up_Month;


	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};

	xmlrequest.send(null);
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
function calins(e,t) {
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
         
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false; 
        }
}

function loadAccountHeadDesc(path)
{
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	if(txtAcc_HeadCode=="0")
		{
		alert("Select Account HeadCode");
		return false;
		}
	var url = path+ "/Bills_Token_Register_with_SP?command=getAccDesc&txtAcc_HeadCode="+txtAcc_HeadCode;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}

function checkdt1(t)
{

   if(t.value.length==0)
       return false;
   if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
   {


       // var c=t.value.replace(/-/g,'/');
        var c=t.value;
       try{
       var f=DateFormat(t,c,event,true,'3');
       }catch(e){
       //exception  start

        t.value=c;
           var sc=t.value.split('/');
           var currenDay =sc[0];
           var currentMonth=sc[1];
           var currentYear=sc[2];
           //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
           if(currentYear<1970)
           {

                   alert('Entered date should be greater than or equal to 1970');
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


       //exception end

       }
       if( f==true)
       {
           //alert(f);
           //t.value=c.replace(/\//g,'-');
           t.value=c;
           var sc=t.value.split('/');
           var currenDay =sc[0];
           var currentMonth=sc[1];
           var currentYear=sc[2];
           //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());

           if(currentYear<1970)
           {

                   alert('Entered date should be greater than or equal to 1970');
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
           //t.focus();
           return false
   }

}
function loadprocDetails(path)
{
	//alert("%%%%%%");
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	if(cboBillMajorType=="0")
		{
		alert("Choose Major Type");
		return false;
		}
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	if(cboBillMinorType=="0")
		{
		alert("Choose Minor Type");
		return false;
		}
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	if(cboBillSubType=="0")
		{
		alert("Choose Sub Type");
		return false;
		}
	


    var combo1 = document.getElementById("txtProceedingNo");
    var txtProceedingNo = combo1.options[combo1.selectedIndex].text;

    var proValue = document.getElementById("txtProceedingNo").value;
	
	
	//var txtProceedingNo = document.getElementById("txtProceedingNo").value;
	//alert(txtProceedingNo);
	
	
	if(txtProceedingNo=="")
		{
		alert("Choose Proceeding No");
		return false;
		}
	
	var url = path+ "/Bills_Token_Register_with_SP?command=getProceedingDetails&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	"&cmbOffice_code="+cmbOffice_code+"&cboBillMajorType="+cboBillMajorType+"&cboBillMinorType="+cboBillMinorType+
	"&cboBillSubType="+cboBillSubType+"&txtProceedingNo="+txtProceedingNo+"&proValue="+proValue;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);	
}

function getOffice1(baseResponse) {
	document.getElementById("cboOffice").length = 0;
	document.getElementById("cmbMas_SL_Code").length=0;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len6 = baseResponse.getElementsByTagName("OfficeID").length;
		for ( var i = 0; i < len6; i++)
		{
			var OfficeID = baseResponse.getElementsByTagName("OfficeID")[i].firstChild.nodeValue;
			var OfficeName = baseResponse.getElementsByTagName("OfficeName")[i].firstChild.nodeValue;
			var se = document.getElementById("cboOffice");
			var op = document.createElement("OPTION");
			op.value = OfficeID;
			var txt = document.createTextNode(OfficeName);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithProceeding.cboOffice.value = OfficeID;
		}
		
		var lenths = baseResponse.getElementsByTagName("EMPLOYEE_ID").length;
		for ( var i = 0; i < lenths; i++)
		{
			var empid = baseResponse.getElementsByTagName("EMPLOYEE_ID")[i].firstChild.nodeValue;
			var empname = baseResponse.getElementsByTagName("EMPLOYEE_NAME")[i].firstChild.nodeValue;
			var se = document.getElementById("cmbMas_SL_Code");
			var op = document.createElement("OPTION");
			op.value = empid;
			var txt = document.createTextNode(empname);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithProceeding.cmbMas_SL_Code.value = empid;
		}
		
	} else {
		alert("Fail to Load Bill Major Type");
	}
}


function Acc_Head_Code11(baseResponse)
{
	
	var count = baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert(flag);
	if(flag=="success")
		{
	//	alert(count);
		for ( var i = 0; i < count; i++) {
			
			var Acc_Head_Code = baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[i].firstChild.nodeValue;
			//alert("*****/////==== "+Acc_Head_Code);
			var combo = document.getElementById("txtAcc_HeadCode");
			var option = document.createElement("OPTION");
			option.text = Acc_Head_Code;
		    option.value = Acc_Head_Code;
		    try {
		        combo.add(option, null); //Standard
		    }catch(error) {
		        combo.add(option); // IE only
		    }
			
		}
		}
}
function getProceedingNo_res(baseResponse)
{
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="success")
	{
	
			var k_count=0;
			document.getElementById("txtProceedingNo").length = 1;
			var len1 = baseResponse.getElementsByTagName("procNo").length;
		
			for ( var i = 0; i < len1; i++) {
			//	alert("ttt");
				var procNo = baseResponse.getElementsByTagName("procNo")[i].firstChild.nodeValue;
				var procId = baseResponse.getElementsByTagName("sanc_id")[i].firstChild.nodeValue;
				var se = document.getElementById("txtProceedingNo");
				var op = document.createElement("OPTION");
				op.value = procId;
				var txt = document.createTextNode(procNo);
				op.appendChild(txt);
				se.appendChild(op);
				k_count++;
			}
			if(k_count>0)
			{
				//document.getElementById("sanc_id").value="";
				
				var cboBillMajorType = document.getElementById("cboBillMajorType").value;
				if(cboBillMajorType=="0")
					{
					alert("Choose Major Type");
					return false;
					}
				var cboBillMinorType = document.getElementById("cboBillMinorType").value;
				if(cboBillMinorType=="0")
					{
					alert("Choose Minor Type");
					return false;
					}
				var cboBillSubType = document.getElementById("cboBillSubType").value;
				if(cboBillSubType=="0")
					{
					alert("Choose Sub Type");
					return false;
					}
		  	}
	}
	else
	{
		alert("No Data Found");
		return false;
	}
}

function loadHead_res(baseResponse)
{
	document.getElementById("txtAcc_HeadCode").length=0;
	var len1 = baseResponse.getElementsByTagName("acchead").length;
	for ( var i = 0; i < len1; i++) {
		var empName = baseResponse.getElementsByTagName("acchead")[i].firstChild.nodeValue;
		 //alert(empName);
		var se = document.getElementById("txtAcc_HeadCode");
		var op = document.createElement("OPTION");
		op.value = empName;
		var txt = document.createTextNode(empName);
		op.appendChild(txt);
		se.appendChild(op);

	}
	var budgetAllo = baseResponse.getElementsByTagName("budgetAllo")[0].firstChild.nodeValue;
	var spent = baseResponse.getElementsByTagName("spent")[0].firstChild.nodeValue;
	var finttl = baseResponse.getElementsByTagName("finttl")[0].firstChild.nodeValue;
	var head_desc = baseResponse.getElementsByTagName("head_desc")[0].firstChild.nodeValue;
	
	
	//document.getElementById("txtBudgetProvision").value=budgetAllo;
	//document.getElementById("txtBudgetSpent").value=spent;
	document.getElementById("budAvail").value=finttl;
	document.getElementById("txtAcc_HeadDesc").value=head_desc;
}

function getAccDesc_res(baseResponse)
{
	
		var headdesc = baseResponse.getElementsByTagName("headdesc")[0].firstChild.nodeValue;
		document.getElementById("txtAcc_HeadDesc").value=headdesc;
		
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		
		var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
		
		var url ="../../../../../Bills_Token_Register_with_SP?command=budgetProv&txtAcc_HeadCode="+txtAcc_HeadCode+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};

		xmlrequest.send(null);
		
}

function budgetProv_res(baseResponse)
{
	//alert("in response...");
	
	var budgetAllo = baseResponse.getElementsByTagName("budgetAllo")[0].firstChild.nodeValue;
	var spent = baseResponse.getElementsByTagName("spent")[0].firstChild.nodeValue;
	var finttl = baseResponse.getElementsByTagName("finttl")[0].firstChild.nodeValue;
	
	var REF_NO = baseResponse.getElementsByTagName("REF_NO")[0].firstChild.nodeValue;
	var REF_DATE = baseResponse.getElementsByTagName("REF_DATE")[0].firstChild.nodeValue;
	if(REF_NO=='null')
		{
			REF_NO="";
		}
	if(REF_DATE=='null')
	{
		REF_DATE="";
	}
	
//	document.getElementById("txtBudgetProvision").value=budgetAllo;
//	document.getElementById("txtBudgetSpent").value=spent;
	document.getElementById("budAvail").value=finttl;
	
	document.getElementById("txtRefNo").value=REF_NO;
	document.getElementById("txtRefDate").value=REF_DATE;
	
	
}
function getProceedingDetails_res(baseResponse)
{
seq=1;	
	acc_head_code="";
	var PAYMENT_AMOUNT="";
	var gpf = baseResponse.getElementsByTagName("gpf")[0].firstChild.nodeValue;
	var count=baseResponse.getElementsByTagName("Pro_no")[0].firstChild.nodeValue;
	//alert("000:"+gpf);
	document.getElementById("hid_sanc").value=count;
	//alert("ghg   "+gpf);
	var procdate = baseResponse.getElementsByTagName("procdate")[0].firstChild.nodeValue;
	var sanAmt = baseResponse.getElementsByTagName("sanAmt")[0].firstChild.nodeValue;
	
	var EMPLOYEE_NAME = baseResponse.getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue;
//alert(EMPLOYEE_NAME);
document.getElementById("txtPayableTo").value=EMPLOYEE_NAME;
	document.getElementById("txtTotalSanctionedAmount").value=sanAmt;
	document.getElementById("txtTotalBillAmount").value=sanAmt;
	document.getElementById("txtProceedingDate").value=procdate;
	if(gpf=="gpf")
	{
		newtab(baseResponse);
	}
	
	else{
	

	
	
	var empid = baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;

	var sanc_id=baseResponse.getElementsByTagName("HRMS_SANCTION_ID")[0].firstChild.nodeValue;
	var Office_Id=baseResponse.getElementsByTagName("Office_Id")[0].firstChild.nodeValue;
	var off_name=baseResponse.getElementsByTagName("off_name")[0].firstChild.nodeValue;
	
		//document.getElementById("txtTotalSanctionedAmount").value=sanAmt;
		//document.getElementById("txtTotalBillAmount").value=sanAmt;
	
	
	//document.getElementById("txtPayableTo").value=EMPLOYEE_NAME;
	//document.getElementById("hid_payTo").value=EMPLOYEE_NAME;
	
	//document.getElementById("txtProceedingDate").value=procdate;
	
	
//	document.getElementById("txtPayeeCode").value=empid;
	
	document.getElementById("sanc_id").value=sanc_id;
	document.getElementById("cboOffice").length=0;
	var cboOffice = document.getElementById("cboOffice");
	var option = document.createElement("OPTION");
	option.text = off_name;
    option.value = Office_Id;
    try {
    	cboOffice.add(option, null); //Standard//////////////////   ////
    }catch(error) {
    	cboOffice.add(option); // IE only
    }
	
    var count=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
    var tbody=document.getElementById("grid_body");
    var items=new Array();  
    
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
           tbody.deleteRow(0);
           sl_no=1;
    }
   // alert(count);
   // alert("::"+count.length);
    for(var i=0;i<count;i++)
    {        
    	 
           var ACCOUNT_HEAD_CODE=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[i].firstChild.nodeValue;
           var ACCOUNT_HEAD_DESC=baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[i].firstChild.nodeValue;
           var budget_alloted=baseResponse.getElementsByTagName("CURRENT_YEAR_BUDGET_ALLOTTED")[i].firstChild.nodeValue;
         //if(budget_alloted=="-")budget_alloted=0; 
       //  alert(budget_alloted);
           var PAYMENT_AMOUNT=baseResponse.getElementsByTagName("PAYMENT_AMOUNT")[i].firstChild.nodeValue;
           var spent=baseResponse.getElementsByTagName("BUDGET_SOFAR_SPENT")[i].firstChild.nodeValue;
           var ref_number=baseResponse.getElementsByTagName("REF_NO")[i].firstChild.nodeValue;
           var refe_date=baseResponse.getElementsByTagName("REF_DATE")[i].firstChild.nodeValue;
           if(refe_date=="null")
           {
        	   refe_date='-';
           }
            var mycurrent_row=document.createElement("TR");
            mycurrent_row.id=seq;
       
            
            var cell=document.createElement("TD");
            var slno=document.createElement("input");
            slno.type="hidden";
            slno.name="slno";
            slno.id="slno";
            slno.value=sl_no;
            cell.appendChild(slno);
            var currentText=document.createTextNode(sl_no);
            cell.appendChild(currentText);
            mycurrent_row.appendChild(cell);
          
            var cell22=document.createElement("TD");
            var H_code=document.createElement("input");
            H_code.type="hidden";
            H_code.name="H_code";
            H_code.id="H_code";
            H_code.value=ACCOUNT_HEAD_CODE;
            cell22.appendChild(H_code);
            var currentText=document.createTextNode(ACCOUNT_HEAD_CODE+"-"+ACCOUNT_HEAD_DESC);
            cell22.appendChild(currentText);
            mycurrent_row.appendChild(cell22);
            
            
            cell2=document.createElement("TD");
            cell2.style.textAlign='right';
            var pay_amt=document.createElement("input");
            pay_amt.type="hidden";
            pay_amt.name="pay_amt";
            pay_amt.id="pay_amt";
            pay_amt.value=PAYMENT_AMOUNT;
            cell2.appendChild(pay_amt);
            var currentText=document.createTextNode(PAYMENT_AMOUNT);
            cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2);
            
            cell2=document.createElement("TD");
            cell2.style.textAlign='right';
            var b_alloted=document.createElement("input");
            b_alloted.type="hidden";
            b_alloted.name="b_alloted";
            b_alloted.id="b_alloted";
            b_alloted.value=budget_alloted;
            cell2.appendChild(b_alloted);
            var currentText=document.createTextNode(budget_alloted);
            cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2);

	          cell3=document.createElement("TD");
	          cell3.style.textAlign='right';
	          var budget_spent=document.createElement("input");
	          budget_spent.type="hidden";
	          budget_spent.name="budget_spent";
	          budget_spent.id="budget_spent";
	          budget_spent.setAttribute('align',"right");
	          budget_spent.value=spent;
	          cell3.appendChild(budget_spent);
	          var currentText=document.createTextNode(spent);
	          cell3.appendChild(currentText);
	          mycurrent_row.appendChild(cell3);
	          
	          cell4=document.createElement("TD");
	          cell4.style.textAlign='right';
	          var ref_no=document.createElement("input");
	          ref_no.type="hidden";
	          ref_no.name="ref_no";
	          ref_no.id="ref_no";
	          ref_no.value=ref_number;
	          cell4.appendChild(ref_no);
	          var currentText=document.createTextNode(ref_number);
	          cell4.appendChild(currentText);
	          mycurrent_row.appendChild(cell4);
	          
	          cell5=document.createElement("TD");
	          var ref_date=document.createElement("input");
	          ref_date.type="hidden";
	          ref_date.name="ref_date";
	          ref_date.id="ref_date";
	          ref_date.value=refe_date;
	          cell5.appendChild(ref_date);
	          var currentText=document.createTextNode(refe_date);
	          cell5.appendChild(currentText);
	          mycurrent_row.appendChild(cell5);
            
            tbody.appendChild(mycurrent_row);
            seq++;
            sl_no++;
    }
	
	}
}

function newtab(baseResponse)
{
	seq=1;
	document.getElementById("notgpfdetails").style.display="none";
	document.getElementById("gpfdetails").style.display="block";
	
	var procdate = baseResponse.getElementsByTagName("procdate")[0].firstChild.nodeValue;
	var sanAmt = baseResponse.getElementsByTagName("sanAmt")[0].firstChild.nodeValue;
	//var gpfno = baseResponse.getElementsByTagName("gpfno")[0].firstChild.nodeValue;
	var empid = baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
	var EMPLOYEE_NAME = baseResponse.getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue;
	var sanc_id=baseResponse.getElementsByTagName("HRMS_SANCTION_ID")[0].firstChild.nodeValue;
	var Office_Id=baseResponse.getElementsByTagName("Office_Id")[0].firstChild.nodeValue;
	var off_name=baseResponse.getElementsByTagName("off_name")[0].firstChild.nodeValue;
	
	document.getElementById("txtPayableTo").value=0;
//document.getElementById("txtPayableTo").value=EMPLOYEE_NAME;
	document.getElementById("txtProceedingDate").value=procdate;
	
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	
	if(cboBillMajorType==2)
	{
		if(cboBillMinorType==2)
		{
			if(cboBillSubType==1)
			{
				
			var total_amt = baseResponse.getElementsByTagName("total_amt")[0].firstChild.nodeValue;
			document.getElementById("txtTotalSanctionedAmount").value=total_amt;
			document.getElementById("txtTotalBillAmount").value=total_amt;
			}
		}
	}
	else{
	document.getElementById("txtTotalSanctionedAmount").value=sanAmt;
	document.getElementById("txtTotalBillAmount").value=sanAmt;
	}
	//document.getElementById("txtPayeeCode").value=empid;
	//document.getElementById("txtPayeeCode").value=0;
	
	document.getElementById("sanc_id").value=sanc_id;
	document.getElementById("cboOffice").length=0;
	var cboOffice = document.getElementById("cboOffice");
	var option = document.createElement("OPTION");
	option.text = off_name;
    option.value = Office_Id;
    try {
    	cboOffice.add(option, null); //Standard//////////////////   ////
    }catch(error) {
    	cboOffice.add(option); // IE only
    }
	var minor=document.getElementById("cboBillMinorType").options[document.getElementById("cboBillMinorType").selectedIndex].text;
	var sub=document.getElementById("cboBillSubType").options[document.getElementById("cboBillSubType").selectedIndex].text;
	
	var prono=document.getElementById("txtProceedingNo").value;
	var prodate=document.getElementById("txtProceedingDate").value;
	var txtPayableTo=document.getElementById("txtPayableTo").value;
	if(txtPayableTo==0){
    document.getElementById("mtxtRemarks").value=minor+"-"+sub+"-"+prono+"-"+prodate;
	}else{
		document.getElementById("mtxtRemarks").value=minor+"-"+sub+"-"+prono+"-"+prodate+"-"+txtPayableTo;
	}
    var count=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
    var tbody1=document.getElementById("grid_body1");
   
    
    var t=0;
    for(t=tbody1.rows.length-1;t>=0;t--)
    {
           tbody1.deleteRow(0);
           sl_no=1;
    }
   // alert(count);
   // alert("::"+count.length);
    for(var i=0;i<count;i++)
    {        
    	 
           var ACCOUNT_HEAD_CODE=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[i].firstChild.nodeValue;
           var ACCOUNT_HEAD_DESC=baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[i].firstChild.nodeValue;
          //alert("ACCOUNT_HEAD_CODE >> "+ACCOUNT_HEAD_CODE);
           var PAYMENT_AMOUNT=baseResponse.getElementsByTagName("PAYMENT_AMOUNT")[i].firstChild.nodeValue;
           var EMPLOYEE_ID=baseResponse.getElementsByTagName("EMPLOYEE_ID")[i].firstChild.nodeValue;
           var EMPLOYEE_NAME=baseResponse.getElementsByTagName("EMPLOYEE_NAME")[i].firstChild.nodeValue;
           
            var mycurrent_row=document.createElement("TR");
            mycurrent_row.id=seq;
       
            
            var cell=document.createElement("TD");
            var slno=document.createElement("input");
            slno.type="hidden";
            slno.name="slno";
            slno.id="slno";
            slno.value=seq;
            cell.appendChild(slno);
            var currentText=document.createTextNode(seq);
            cell.appendChild(currentText);
            mycurrent_row.appendChild(cell);
          
            var cell22=document.createElement("TD");
            var H_code=document.createElement("input");
            H_code.type="hidden";
            H_code.name="H_code";
            H_code.id="H_code";
            H_code.value=ACCOUNT_HEAD_CODE;
            cell22.appendChild(H_code);
            var currentText=document.createTextNode(ACCOUNT_HEAD_CODE+"-"+ACCOUNT_HEAD_DESC);
            cell22.appendChild(currentText);
            mycurrent_row.appendChild(cell22);
            
            
            cell2=document.createElement("TD");
            cell2.style.textAlign='right';
            var pay_amt=document.createElement("input");
            pay_amt.type="hidden";
            pay_amt.name="pay_amt";
            pay_amt.id="pay_amt";
            pay_amt.value=PAYMENT_AMOUNT;
            cell2.appendChild(pay_amt);
            var currentText=document.createTextNode(PAYMENT_AMOUNT);
            cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2);
            
            if(document.getElementById("cboBillMajorType").value=="2")
            {
            if(document.getElementById("cboBillMinorType").value=="1" || document.getElementById("cboBillMinorType").value=="2")
    		{
            	var cell22=document.createElement("TD");
                var pay_type=document.createElement("input");
                pay_type.type="hidden";
                pay_type.name="pay_type";
                pay_type.id="pay_type";
                pay_type.value=7;
                cell22.appendChild(pay_type);
                var currentText=document.createTextNode("Employees");
                cell22.appendChild(currentText);
                mycurrent_row.appendChild(cell22);
                
                var cell22=document.createElement("TD");
                var pay_code=document.createElement("input");
                pay_code.type="hidden";
                pay_code.name="pay_code";
                pay_code.id="pay_code";
                pay_code.value=EMPLOYEE_ID;
                cell22.appendChild(pay_code);
                var currentText=document.createTextNode(EMPLOYEE_NAME);
                cell22.appendChild(currentText);
                mycurrent_row.appendChild(cell22);
                
    		}
            }
            
            cell2=document.createElement("TD");
            cell2.style.textAlign='right';
            cell2.style.display='none';
            var b_alloted=document.createElement("input");
            b_alloted.type="hidden";
            b_alloted.name="b_alloted";
            b_alloted.id="b_alloted";
            b_alloted.value=0;
            cell2.appendChild(b_alloted);
            mycurrent_row.appendChild(cell2);

	          cell3=document.createElement("TD");
	          cell3.style.textAlign='right';
	          cell3.style.display='none';
	          var budget_spent=document.createElement("input");
	          budget_spent.type="hidden";
	          budget_spent.name="budget_spent";
	          budget_spent.id="budget_spent";
	          budget_spent.setAttribute('align',"right");
	          budget_spent.value=0;
	          cell3.appendChild(budget_spent);
	         
	          mycurrent_row.appendChild(cell3);
	          
	          cell4=document.createElement("TD");
	          cell4.style.textAlign='right';
	          cell4.style.display='none';
	          var ref_no=document.createElement("input");
	          ref_no.type="hidden";
	          ref_no.name="ref_no";
	          ref_no.id="ref_no";
	          ref_no.value=0;
	          cell4.appendChild(ref_no);
	         
	          mycurrent_row.appendChild(cell4);
	          
	          cell5=document.createElement("TD");
	          cell5.style.display='none';
	          var ref_date=document.createElement("input");
	         ref_date.type="hidden";
	          ref_date.name="ref_date";
	          ref_date.id="ref_date";
	          ref_date.value="-";
	          cell5.appendChild(ref_date);
	          
	          mycurrent_row.appendChild(cell5);
           
            
            tbody1.appendChild(mycurrent_row);
            seq++;
            sl_no++;
    }
	
	

}

function callemp(path)
{
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	
		var url = path+ "/Bills_Token_Register_with_SP?command=getempname_off&txtEmpID_mas="+ txtEmpID_mas+"&cmbOffice_code="+cmbOffice_code;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);

}

function mas_employee(emp_id)
{
     emp_flag=true;
     doFunction('Load_MasterSL_Code',document.getElementById("cmbMas_SL_type").value);
}

function getempname_re(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
	var empname = baseResponse.getElementsByTagName("empname")[0].firstChild.nodeValue;
	
	document.getElementById("cmbMas_SL_Code").length = 0;
	var len1 = baseResponse.getElementsByTagName("empname").length;
	for ( var i = 0; i < len1; i++) {
		var empid = baseResponse.getElementsByTagName("empid")[i].firstChild.nodeValue;
		var empname = baseResponse.getElementsByTagName("empname")[i].firstChild.nodeValue;
		var se = document.getElementById("cmbMas_SL_Code");
		var op = document.createElement("OPTION");
		op.value = empid;
		var txt = document.createTextNode(empname);
		op.appendChild(txt);
		se.appendChild(op);
	
	}
	}
	else
	{
		alert("Enter Relevant EmployeeId For This Office");
		document.getElementById("cmbMas_SL_Code").length = 0;
		document.getElementById("txtEmpID_mas").value="";
	}
}
function firstLoad(baseResponse) {
	 //alert("RKsbg");
	

	

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len4 = baseResponse.getElementsByTagName("billMajorTypeCode").length;
		for ( var i = 0; i < len4; i++) {
			var billMajorTypeCode = baseResponse
					.getElementsByTagName("billMajorTypeCode")[i].firstChild.nodeValue;
			var billMajorTypeDesc = baseResponse
					.getElementsByTagName("billMajorTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillMajorType");
			var op = document.createElement("OPTION");
			op.value = billMajorTypeCode;
			var txt = document.createTextNode(billMajorTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);
		}

		var len6 = baseResponse.getElementsByTagName("OfficeID").length;
		for ( var i = 0; i < len6; i++) {
			var OfficeID = baseResponse.getElementsByTagName("OfficeID")[i].firstChild.nodeValue;
			var OfficeName = baseResponse.getElementsByTagName("OfficeName")[i].firstChild.nodeValue;
			var se = document.getElementById("cboOffice");
			var op = document.createElement("OPTION");
			op.value = OfficeID;
			var txt = document.createTextNode(OfficeName);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithProceeding.cboOffice.value = OfficeID;
		}
		
		
		document.getElementById("txtEmpID_mas").value= baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
		for ( var i = 0; i < len6; i++) {
			var empid = baseResponse.getElementsByTagName("empid")[i].firstChild.nodeValue;
			var empname = baseResponse.getElementsByTagName("empname")[i].firstChild.nodeValue;
			var se = document.getElementById("cmbMas_SL_Code");
			var op = document.createElement("OPTION");
			op.value = empid;
			var txt = document.createTextNode(empname);
			op.appendChild(txt);
			se.appendChild(op);
		//	document.frm_BillTokenRegisterEntry_WithProceeding.cboOffice.value = OfficeID;
		}

	} else {
		alert("Fail to Load Bill Major Type");
	}
}

function calculateBudget(path) {
	// alert(path);
	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cboOffice_code = document.getElementById("cmbOffice_code").value;
	var txtaccountheadcode = document.getElementById("txtAcc_HeadCode").value;

	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	if (month <= 3) {
		var year1 = year - 1;
	} else {
		var year1 = year + 1;
	}

	if (txtaccountheadcode == "") {
		alert("Enter Account Head Code in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtAcc_HeadCode
				.focus();
	} else {
		var url = path
				+ "/Bills_Token_Register_with_SP?command=calculateBudget&cboAcc_UnitCode="
				+ cboAcc_UnitCode + "&cboOffice_code=" + cboOffice_code
				+ "&year=" + year + "&year1=" + year1 + "&txtaccountheadcode="
				+ txtaccountheadcode;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}

function calculateBudget1(baseResponse) {
	// alert("RKsbg");
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var BudgetProvided = baseResponse
				.getElementsByTagName("BudgetProvided")[0].firstChild.nodeValue;
		var BudgetSoFarSpent = baseResponse
				.getElementsByTagName("BudgetSoFarSpent")[0].firstChild.nodeValue;

	//	document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetProvision.value = BudgetProvided;
	//	document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetSpent.value = BudgetSoFarSpent;

	} else if (flag == "NoData") {
		alert("Budget Does not Alloted for Current Year");
	} else {
		alert("Fail to Load Budget Details");
	}
}

function getBillMinorType(path) {
//	if
	callemp(path);
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	if ((document.getElementById("cboBillMajorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value == "s")) {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMajorType
				.focus();
	} else {
		var url = path
				+ "/Bills_Token_Register_with_SP?command=getBillMinorType&cboBillMajorType="
				+ cboBillMajorType;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}

}

function getBillMinorType1(baseResponse) {
      	document.getElementById("txtBillNo").value = "";
	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	        if (flag1 == "success") {
		var BillNo = baseResponse.getElementsByTagName("BillNo")[0].firstChild.nodeValue;
		document.frm_BillTokenRegisterEntry_WithProceeding.txtBillNo.value = BillNo;
	} else {
		alert("Failed to Generate Bill No");
	}
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMinorType.length = 1;
	if (flag == "success") {
		var len4 = baseResponse.getElementsByTagName("billMinorTypeCode").length;
		for ( var i = 0; i < len4; i++) {
			var billMinorTypeCode = baseResponse
					.getElementsByTagName("billMinorTypeCode")[i].firstChild.nodeValue;
			var billMinorTypeDesc = baseResponse
					.getElementsByTagName("billMinorTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillMinorType");
			var op = document.createElement("OPTION");
			op.value = billMinorTypeCode;
			var txt = document.createTextNode(billMinorTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);

		}

		var len5 = baseResponse.getElementsByTagName("EstimateSanctionNo").length;
		// alert(len5);
		var se = document.getElementById("cboEstimateSanctionNumber");
		//se.length=0;
		for ( var i = 0; i < len5; i++) {
			var EstimateSanctionNo = baseResponse
					.getElementsByTagName("EstimateSanctionNo")[i].firstChild.nodeValue;
			// alert(EstimateSanctionNo);
			
			var op = document.createElement("OPTION");
			op.value = EstimateSanctionNo;
			var txt = document.createTextNode(EstimateSanctionNo);
			op.appendChild(txt);
			se.appendChild(op);
		}

	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Bill Minor Type");
	}
}

function getBillsubType(path) {
	// alert(path);
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;

	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	//alert(document.getElementById("cboBillMinorType").value);
	if ((document.getElementById("cboBillMajorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value == "s")) {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMajorType
				.focus();
	} else if ((document.getElementById("cboBillMinorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMinorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMinorType").value == "s")) {
		alert("Select Bill Minor Type in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMinorType
				.focus();
	} 
	
	
	else {
		
	/*	if(document.getElementById("cboBillMinorType").value=="1" || document.getElementById("cboBillMinorType").value=="2")
		{
			//alert(document.getElementById("cboBillMinorType").value);
			document.getElementById("txtPayeeType").length=0;
		var se = document.getElementById("txtPayeeType");
		var op = document.createElement("OPTION");
		op.value = 7;
		var txt = document.createTextNode("Employee");
		op.appendChild(txt);
		se.appendChild(op);
		}
	
		else{
			alert('No Payee Type');
		}  */
			var url = path
					+ "/Bills_Token_Register_with_SP?command=getBillsubType&cboBillMajorType="
					+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType;
			// alert(url);
			var xmlrequest = AjaxFunction();
			xmlrequest.open("POST", url, true);
			xmlrequest.onreadystatechange = function() {
				manipulate(xmlrequest);
			}
	
			xmlrequest.send(null);
	}

}

function getBillsubType1(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	document.frm_BillTokenRegisterEntry_WithProceeding.cboBillSubType.length = 1;
	if (flag == "success") {
		var len4 = baseResponse.getElementsByTagName("billSubTypeCode").length;
		for ( var i = 0; i < len4; i++) {
			var billSubTypeCode = baseResponse
					.getElementsByTagName("billSubTypeCode")[i].firstChild.nodeValue;
			var billsubTypeDesc = baseResponse
					.getElementsByTagName("billsubTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillSubType");
			var op = document.createElement("OPTION");
			op.value = billSubTypeCode;
			var txt = document.createTextNode(billsubTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);

		}
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Bill Minor Type");
	}
}
function IVno(path) {
	if (document.frm_BillTokenRegisterEntry_WithProceeding.rdoInvoiceEntryOption[0].checked == true) {
		rdoInvoiceEntryOption = document.frm_BillTokenRegisterEntry_WithProceeding.rdoInvoiceEntryOption[0].value;
	} else {
		rdoInvoiceEntryOption = document.frm_BillTokenRegisterEntry_WithProceeding.rdoInvoiceEntryOption[1].value;
	}

	if (rdoInvoiceEntryOption == "Entry") {
		document.frm_BillTokenRegisterEntry_WithProceeding.txtIfSelectfromList.disabled = true;
		document.frm_BillTokenRegisterEntry_WithProceeding.txtInvoiceNo.disabled = false;
		document.frm_BillTokenRegisterEntry_WithProceeding.txtInvoiceNo.value = "";
		document.frm_BillTokenRegisterEntry_WithProceeding.txtInvoiceDate.value = "";
		document.frm_BillTokenRegisterEntry_WithProceeding.txtInvoiceAmount.value = "";
	} else {
		document.frm_BillTokenRegisterEntry_WithProceeding.txtInvoiceNo.disabled = true;
		document.frm_BillTokenRegisterEntry_WithProceeding.txtIfSelectfromList.disabled = false;

		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var today = new Date();
		var day = today.getDate();
		var month = today.getMonth();
		month = month + 1;
		var year = today.getYear();
		if (year < 1900)
			year += 1900;

		var url = path
				+ "/Bills_Token_Register_with_SP?command=IVno&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&month=" + month + "&year=" + year;

		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);

	}
}

function IVno1(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	document.frm_BillTokenRegisterEntry_WithProceeding.txtIfSelectfromList.length = 1;
	if (flag == "success1") {
		var len4 = baseResponse.getElementsByTagName("InvoiceNo").length;
		for ( var i = 0; i < len4; i++) {
			var InvoiceNo = baseResponse.getElementsByTagName("InvoiceNo")[i].firstChild.nodeValue;
			var se = document.getElementById("txtIfSelectfromList");
			var op = document.createElement("OPTION");
			op.value = InvoiceNo;
			var txt = document.createTextNode(InvoiceNo);
			op.appendChild(txt);
			se.appendChild(op);

		}
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Invoice No");
	}
}

function InvoiceDetails(path) {
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtIfSelectfromList = document.getElementById("txtIfSelectfromList").value;
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	if (txtIfSelectfromList == "" || txtIfSelectfromList == "s") {
		alert("Select Invoice No in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtIfSelectfromList
				.focus();
	} else {
		var url = path
				+ "/Bills_Token_Register_with_SP?command=InvoiceDetails&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&month=" + month + "&year=" + year + "&txtIfSelectfromList="
				+ txtIfSelectfromList;

		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}

function InvoiceDetails1(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag == "success1") {
		var InvoiceDate = baseResponse.getElementsByTagName("InvoiceDate")[0].firstChild.nodeValue;
		var InvoiceAmount = baseResponse.getElementsByTagName("InvoiceAmount")[0].firstChild.nodeValue;
		document.frm_BillTokenRegisterEntry_WithProceeding.txtInvoiceDate.value = InvoiceDate;
		document.frm_BillTokenRegisterEntry_WithProceeding.txtInvoiceAmount.value = InvoiceAmount;
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Invoice No");
	}
}


function saveFunc1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	var BillNo1 = baseResponse.getElementsByTagName("BillNo")[0].firstChild.nodeValue;
	var BillNo = parseInt(BillNo1);
	document.frm_BillTokenRegisterEntry_WithProceeding.txtBillNo.value = BillNo + 1;

	if (flag == "success") {
		alert("Record Inserted Successfully");
		window.location.reload();
		
		//refresh();
	} else {
		alert("Record Insertion Failed");
	}
}

function forList(path) {
	// alert(path);
	var unitcode=document.getElementById("cmbAcc_UnitCode").value;
	winemp = window.open("Bills_Token_Register_with_SP_List.jsp?cmbAcc_UnitCode="+unitcode, "list",
			"status=1,height=550,width=1200,resizable=YES, scrollbars=yes");
	winemp.moveTo(30, 150);
	winemp.focus();
}

function ParentDrawing(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13,
		v14, v15, v16, v17, v18, v19, v20 , v21, v22) {
	 //alert(v3);
                 
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	if (month <= 3) {
		var year1 = year - 1;
	} else {
		var year1 = year + 1;
	}

	document.frm_BillTokenRegisterEntry_WithProceeding.cmbAcc_UnitCode.value = v1;
	document.frm_BillTokenRegisterEntry_WithProceeding.cmbOffice_code.value = v2;
	document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMajorType.value = v3;
	document.frm_BillTokenRegisterEntry_WithProceeding.txtBillNo.value = v6;
	document.frm_BillTokenRegisterEntry_WithProceeding.txtBillDate.value = v7;
//	document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingNo.value = v8;
	document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingDate.value = v9;
//	document.getElementById("txtPayeeType").length=0;
//	if(v10==4)
//	{
//		
//		var paytype=document.getElementById("txtPayeeType");
//		var opt=document.createElement("OPTION");
//		opt.value=4;
//		opt.text="Employee";
//		paytype.appendChild(opt);
//		
//	}
	//document.frm_BillTokenRegisterEntry_WithProceeding.txtPayeeType.value = v10;
	
//	document.frm_BillTokenRegisterEntry_WithProceeding.txtPayeeCode.value = v11;
	document.frm_BillTokenRegisterEntry_WithProceeding.txtTotalSanctionedAmount.value = v12;
	document.frm_BillTokenRegisterEntry_WithProceeding.txtTotalBillAmount.value = v13;
	document.frm_BillTokenRegisterEntry_WithProceeding.txtPayableTo.value = v14;
        document.frm_BillTokenRegisterEntry_WithProceeding.txtEmpID_mas.value = v15;
	document.frm_BillTokenRegisterEntry_WithProceeding.txtAcc_HeadCode.value = v17;
        //document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetProvision.value = v18;
        //document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetSpent.value = v19;
        document.frm_BillTokenRegisterEntry_WithProceeding.txtRefNo.value = v20;
	document.frm_BillTokenRegisterEntry_WithProceeding.txtRefDate.value = v21;
	document.frm_BillTokenRegisterEntry_WithProceeding.mtxtRemarks.value = v22;
	
	if (v16 == "Y") {
		document.frm_BillTokenRegisterEntry_WithProceeding.rdoMTC_70_Register[0].checked = true;
	} else {
		document.frm_BillTokenRegisterEntry_WithProceeding.rdoMTC_70_Register[1].checked = true;
	}
       	document.frm_BillTokenRegisterEntry_WithProceeding.onsubmit.disabled = true;
	document.frm_BillTokenRegisterEntry_WithProceeding.ondelete.disabled = false;
	document.frm_BillTokenRegisterEntry_WithProceeding.onupdate.disabled = false;

	var url = "../../../../../Bills_Token_Register_with_SP?command=Edit&txtBillNo="
			+ v6
			+ "&txtEmpID_mas="
			+ v15
			+ "&cmbAcc_UnitCode="
			+ v1
			+ "&cmbOffice_code="
			+ v2
			+ "&year="
			+ year
			+ "&year1="
			+ year1
			+ "&txtAcc_HeadCode=" + v17+"&procNo="+v8;
	 //alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);

}

function Edit1(baseResponse) {
	document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMinorType.length = "1";
	document.frm_BillTokenRegisterEntry_WithProceeding.cboBillSubType.length = "1";

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var len4 = baseResponse.getElementsByTagName("billMinorTypeCode").length;
		var len = baseResponse.getElementsByTagName("BillProcessingDoneBy").length;

		for ( var i = 0; i < len4; i++) {
			var billMinorTypeCode = baseResponse
					.getElementsByTagName("billMinorTypeCode")[i].firstChild.nodeValue;
			var billMinorTypeDesc = baseResponse
					.getElementsByTagName("billMinorTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillMinorType");
			var op = document.createElement("OPTION");
			op.value = billMinorTypeCode;
			var txt = document.createTextNode(billMinorTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMinorType.value = billMinorTypeCode;
		}
		var len5 = baseResponse.getElementsByTagName("billSubTypeCode").length;
		// alert(len5);
		for ( var i = 0; i < len5; i++) {
			var billSubTypeCode = baseResponse
					.getElementsByTagName("billSubTypeCode")[i].firstChild.nodeValue;
			var billsubTypeDesc = baseResponse
					.getElementsByTagName("billsubTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillSubType");
			var op = document.createElement("OPTION");
			op.value = billSubTypeCode;
			var txt = document.createTextNode(billsubTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithProceeding.cboBillSubType.value = billSubTypeCode;
		}

		var empid = baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
		document.frm_BillTokenRegisterEntry_WithProceeding.cmbMas_SL_Code.length = "1";
		for ( var i = 0; i < len; i++) {
			var BillProcessingDoneBy = baseResponse
					.getElementsByTagName("BillProcessingDoneBy")[0].firstChild.nodeValue;
			var se = document.getElementById("cmbMas_SL_Code");
			var op = document.createElement("OPTION");
			op.value = empid;
			var txt = document.createTextNode(BillProcessingDoneBy);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithProceeding.cmbMas_SL_Code.value = empid;
		}
	}

	var flagg = baseResponse.getElementsByTagName("flagg")[0].firstChild.nodeValue;
	if (flagg == "success") {

		var BudgetProvided = baseResponse
				.getElementsByTagName("BudgetProvided")[0].firstChild.nodeValue;
		var BudgetSoFarSpent = baseResponse
				.getElementsByTagName("BudgetSoFarSpent")[0].firstChild.nodeValue;

	//	document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetProvision.value = BudgetProvided;
	//	document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetSpent.value = BudgetSoFarSpent;

	} else if (flagg == "NoData") {
		alert("Budget Does not Alloted for Current Year");
	} else {
		alert("Fail to Load Budget Details");
	}

	var flag2 = baseResponse.getElementsByTagName("flag2")[0].firstChild.nodeValue;
	if (flag2 == "success") {

		var len6 = baseResponse.getElementsByTagName("OfficeID").length;
		for ( var i = 0; i < len6; i++) {
			var OfficeID = baseResponse.getElementsByTagName("OfficeID")[i].firstChild.nodeValue;
			var OfficeName = baseResponse.getElementsByTagName("OfficeName")[i].firstChild.nodeValue;
			var se = document.getElementById("cboOffice");
			var op = document.createElement("OPTION");
			op.value = OfficeID;
			var txt = document.createTextNode(OfficeName);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithProceeding.cboOffice.value = OfficeID;
		}
	} else {
		alert("Fail to Load Bill Major Type");
	}
	
	var flag_no = baseResponse.getElementsByTagName("flag_no")[0].firstChild.nodeValue;
	if (flag_no == "success") {
		document.getElementById("txtProceedingNo").length=0;
		for ( var i = 0; i <1; i++) {
			var sancid = baseResponse.getElementsByTagName("sancid")[i].firstChild.nodeValue;
			var sancno = baseResponse.getElementsByTagName("sancno")[i].firstChild.nodeValue;
			var se = document.getElementById("txtProceedingNo");
			var op = document.createElement("OPTION");
			op.value = sancid;
			var txt = document.createTextNode(sancno);
			op.appendChild(txt);
			se.appendChild(op);
		}
	} else {
		alert("Fail to Load Bill Major Type");
	}
	
}

function deleteeee(path) {
	// alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;

	var txtBillNo = document.getElementById("txtBillNo").value;
	var r = confirm("Are U Sure?");
	if (r == true) {
		var url = path
				+ "/Bills_Token_Register_with_SP?command=deleted&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtBillNo=" + txtBillNo + "&cboBillMajorType="
				+ cboBillMajorType;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);
	}
}

function Acc_Head_Code(path)
{
	//alert("*****");
	var url =  "../../../../../Bills_Token_Register_with_SP?command=Acc_Head_Code";
 //alert(url);
var xmlrequest = AjaxFunction();
xmlrequest.open("POST", url, true);
xmlrequest.onreadystatechange = function() {
manipulate(xmlrequest);
};
xmlrequest.send(null);
}

function jasper() {
	// alert("******");
	
	//alert(acc_head_code);
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cmbReportType = document.getElementById("cmbReportType").value;
	var txtBillNo = document.getElementById("txtBillNo").value;
	//var r = confirm("Are U Sure?");
	
		var url = "../../../../../Bills_Token_Register_with_SP?command=Report&cmbReportType="+cmbReportType+"&head_code="+head_code;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("GET", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	
}




function deleteRow(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Deleted Successfully");
		refresh();
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
		refresh();
	} else {
		alert("Unable to Delete");
		refresh();
	}
}

function update(path) {
//	 alert("update");
	
	
	
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var today = new Date();
	var day = today.getDate();
	var month = document.getElementById("cash_month").value;
	
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	var txtBillNo = document.getElementById("txtBillNo").value;
	var txtBillDate = document.getElementById("txtBillDate").value;
	
	
	var selectnews=document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingNo;
	var txtProceedingNo=selectnews.options[selectnews.selectedIndex].text;
	
	//var txtProceedingNo =document.getElementByID("txtProceedingNo").options[document.getElementByID("txtProceedingNo").selectedIndex].text;
	//var txtProceedingNo = document
	//		.getElementById("txtProceedingNo").value;
	var txtProceedingDate = document
			.getElementById("txtProceedingDate").value;
	
	if (document.frm_BillTokenRegisterEntry_WithProceeding.rdoMTC_70_Register[0].checked == true) {
		rdoMTC_70_Register = document.frm_BillTokenRegisterEntry_WithProceeding.rdoMTC_70_Register[0].value;
	} else if (document.frm_BillTokenRegisterEntry_WithProceeding.rdoMTC_70_Register[1].checked == true) {
		rdoMTC_70_Register = document.frm_BillTokenRegisterEntry_WithProceeding.rdoMTC_70_Register[1].value;
	}
	//var txtPayeeType = document.getElementById("txtPayeeType").value;
	//var txtPayeeCode = document.getElementById("txtPayeeCode").value;
        var txtTotalSanctionedAmount= document.getElementById("txtTotalSanctionedAmount").value;
	var txtTotalBillAmount = document.getElementById("txtTotalBillAmount").value;
        var txtPayableTo=document.getElementById("txtPayableTo").value;
      	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	var cboOffice = document.getElementById("cboOffice").value;
//	var txtBudgetProvision = document.getElementById("txtBudgetProvision").value;
	//var txtBudgetSpent = document.getElementById("txtBudgetSpent").value;
	var txtRefNo = document.getElementById("txtRefNo").value;
	var txtRefDate = document.getElementById("txtRefDate").value;
	var mtxtRemarks = document.getElementById("mtxtRemarks").value;
	
	var sanction_id=document.getElementById("sanc_id").value;
	
	if (cboBillMajorType == "" || cboBillMajorType == 0) 
        {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMajorType
				.focus();
	} 
       
         if((document.getElementById("cboBillMinorType").length > 1) && (cboBillMinorType == "" || cboBillMinorType == 0))
        {
		alert("Select Bill Minor Type in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMinorType
				.focus();
	} 
         if((document.getElementById("cboBillSubType").length >1) && (cboBillSubType == "" || cboBillSubType == 0))
        {
               alert("Select Bill Sub Type in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboBillSubType
				.focus();
        } else if (document.getElementById("txtBillNo").value == "") {
		alert("Enter Bill No in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtBillNo.focus();
	} else if (document.getElementById("txtBillDate").value == "") {
		alert("Enter Bill Date in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtBillDate
				.focus();
	}else if (document.getElementById("txtProceedingNo").value == "") {
		alert("Enter Proceeding No in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingNo
				.focus();
	} else if (document.getElementById("txtProceedingDate").value == "") {
		alert("Enter Proceeding Date in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingDate
				.focus();
	}
        else if (document.getElementById("txtTotalSanctionedAmount").value == "") {
		alert("Enter Total Sanctioned Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtTotalSanctionedAmount
				.focus();
	} else if (document.getElementById("txtTotalBillAmount").value == "") {
		alert("Enter Total Bill Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtTotalBillAmount
				.focus();
	}  else if (document.getElementById("txtAcc_HeadCode").value == "") {
		alert("Enter Account Head code in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtAcc_HeadCode
				.focus();
	} 
//	else if (document.getElementById("txtPayeeType").value == "") {
//		alert("Enter Payee Type in the Field");
//		document.frm_BillTokenRegisterEntry_WithProceeding.txtPayeeType
//				.focus();
//	}
//	else if (document.getElementById("txtPayeeCode").value == "") {
//		alert("Enter Payee Code in the Field");
//		document.frm_BillTokenRegisterEntry_WithProceeding.txtPayeeCode
//				.focus();
//	}
	else if (document.getElementById("txtPayableTo").value == "") {
		alert("Enter Payable To in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtPayableTo
				.focus();
	} 
        else if (document.getElementById("txtEmpID_mas").value == "") {
		alert("Enter Bill Processing Done By in the field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtEmpID_mas
				.focus();
	} /*else if (cboOffice == "" || cboOffice == "s") {
		alert("Select Office in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboOffice.focus();
	} else if (document.getElementById("txtBudgetProvision").value == "") {
		alert("Enter Budget Provision in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetProvision
				.focus();
	} else if (document.getElementById("txtBudgetSpent").value == "") {
		alert("Enter BudgetSpent in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetSpent
				.focus();
	} else if (document.getElementById("txtRefNo").value == "") {
		alert("Enter Ref No in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtRefNo.focus();
	} else if (document.getElementById("txtRefDate").value == "") {
		alert("Enter Ref Date in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtRefDate
				.focus();
	} else if (parseInt(txtTotalBillAmount) > parseInt(BalanceAmount)) {
		alert("Total Bill Amount is greater than Balance Amount in the Current Year");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtTotalBillAmount
				.focus();
	}*/ else {

		var url = path
				+ "/Bills_Token_Register_with_SP?command=update&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&year=" + year + "&month=" + month + "&cboBillMajorType="
				+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
				+ "&cboBillSubType=" + cboBillSubType + "&txtBillNo="
				+ txtBillNo + "&txtBillDate=" + txtBillDate
				+ "&txtProceedingDate=" + txtProceedingDate
				+ "&txtProceedingNo=" + txtProceedingNo
				+ "&rdoMTC_70_Register=" + rdoMTC_70_Register
				+ "&txtPayableTo=" +txtPayableTo
				+ "&txtTotalBillAmount=" + txtTotalBillAmount
				+ "&txtTotalSanctionedAmount=" + txtTotalSanctionedAmount
				+ "&txtAcc_HeadCode=" + txtAcc_HeadCode
				+ "&txtEmpID_mas=" + txtEmpID_mas + "&cboOffice=" + cboOffice
				+ "&txtRefNo=" + txtRefNo
				+ "&txtRefDate=" + txtRefDate + "&mtxtRemarks=" + mtxtRemarks+"&sanction_id="+sanction_id;
				var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}

function updateRow(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;	
	if (flag == "success") {
		alert("Record Updated Successfully.");
		document.frm_BillTokenRegisterEntry_WithProceeding.onsubmit.disabled=true;
		document.frm_BillTokenRegisterEntry_WithProceeding.Print.disabled=false;
		//refresh();
	} else {
		alert("Failed to update values");
	}
}

function refresh() 
        {
	document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMajorType.value = "0";
	document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMinorType.length = "1";
	document.frm_BillTokenRegisterEntry_WithProceeding.cboBillSubType.length = "1";
	document.frm_BillTokenRegisterEntry_WithProceeding.txtBillNo.value = "";
	//document.frm_BillTokenRegisterEntry_WithProceeding.txtBillDate.value = "";
	document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingNo.value = "";
	document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingDate.value = "";
	
	document.frm_BillTokenRegisterEntry_WithProceeding.sanc_id.value = "";
	
	document.frm_BillTokenRegisterEntry_WithProceeding.txtTotalBillAmount.value = "";
	document.frm_BillTokenRegisterEntry_WithProceeding.txtTotalSanctionedAmount.value = "";
	//document.frm_BillTokenRegisterEntry_WithProceeding.txtAcc_HeadCode.value = "";
	//document.frm_BillTokenRegisterEntry_WithProceeding.txtAcc_HeadDesc.value = "";
	//document.frm_BillTokenRegisterEntry_WithProceeding.txtPayeeType.value = "";
	//document.frm_BillTokenRegisterEntry_WithProceeding.txtPayeeCode.value = "";
        document.frm_BillTokenRegisterEntry_WithProceeding.txtPayableTo.value = "";
        
	document.frm_BillTokenRegisterEntry_WithProceeding.cmbMas_SL_Code.length = "1";
	//document.frm_BillTokenRegisterEntry_WithProceeding.txtEmpID_mas.value = "";
	//document.frm_BillTokenRegisterEntry_WithProceeding.cboOffice.length = "1";
	document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetSpent.value = "";
	//document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetProvision.value = "";
//	document.frm_BillTokenRegisterEntry_WithProceeding.txtRefNo.value = "";
//	document.frm_BillTokenRegisterEntry_WithProceeding.txtRefDate.value = "";
	document.frm_BillTokenRegisterEntry_WithProceeding.mtxtRemarks.value = "";
        
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

function exitfun(path) {
	window.close();
}














function LoadAccountingUnitID(COMMAND)
{
	
        command_for_office = COMMAND;
        var url="../../../../../Load_Accounting_Unit_ID.kv?COMMAND="+COMMAND;
       // alert("command_for_office&&&&&&&&"+url+"ssssss"+command_for_office);
        var req=AjaxFunction();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
        	//alert("gsfg");
          handle_loadAccountingUnitID(req);
        };        
        req.send(null);
   // alert("ebd");
}


function handle_loadAccountingUnitID(req)
{
   
    if(req.readyState==4)
    {
   
     if(req.status==200)
     {
    	 //alert("200");
   
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
        
       var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        
   
      
        if(flag=="success")
        { 
            var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode");         
                cmbAcc_UnitCode.length=0;
          
            var option_count=baseresponse.getElementsByTagName("option");                       
            var root = null;
            for(var i=0;i<option_count.length;i++)
            {  
                var option=document.createElement("OPTION");
                root = baseresponse.getElementsByTagName("option")[i];
                var accounting_unit_id=root.getElementsByTagName("accounting_unit_id")[0].firstChild.nodeValue;
                
                var accounting_unit_name=root.getElementsByTagName("accounting_unit_name")[0].firstChild.nodeValue;
                
                option.text=accounting_unit_name+"("+accounting_unit_id+")";
                option.value=accounting_unit_id;
                try
                {   
                    cmbAcc_UnitCode.add(option);
                }
                catch(errorObject)
                {
                    cmbAcc_UnitCode.add(option,null);
                }   
            }            
                       
        
            /** Load Accounting Office ID */ 
            if ( (command_for_office == "ONLY_UNITS") || (command_for_office=="LIST_ALL_UNITS_ONLY") || (command_for_office=="FOR_LIST_0" ) )
            {
            
            }
            else
            {
               common_LoadOffice();            
            }         
            
            
        }
        else
        {
          alert("Failed to Load Accounting Unit");
        }
                 
     }
    }
}

function sixdigit()
{
 if( document.getElementById("txtAcc_HeadCode").value!=0)
    {
        if(( document.getElementById("txtAcc_HeadCode").value).length<6)
        {
        alert("Account Head Code Shouldn't be less than 6 digit number");
        document.getElementById("txtAcc_HeadCode").value="";
        document.getElementById("txtAcc_HeadCode").focus();
        return false;
        }
    }
}
function clear_Combo(combo)
{       	    
        
         var cmbSL_Code=document.getElementById(combo.id);   
        
         cmbSL_Code.innerHTML="";
         var option=document.createElement("OPTION");
         option.text="--Select Code--";
         option.value="";
         try
         {
        	
        	 cmbSL_Code.add(option);
         }catch(errorObject)
         {
        	 cmbSL_Code.add(option,null);
         } 
}
/*
function common_LoadOffice()
{
	//alert("comes");
    var unitID_val=document.getElementById("cmbAcc_UnitCode").value;     
   // alert("unitID_val"+unitID_val);
    if(unitID_val!="")
    {
        var cmbAcc_UnitCode=unitID_val;
        var url="../../../../../Bills_Token_Register_with_SP?command=LoadUnitWise_Office&cmbAcc_UnitCode="+cmbAcc_UnitCode;
       //alert(url);
        var req=AjaxFunction();
        req.open("POST",url,true);
        req.onreadystatechange=function()
        {
            handle_loadOffice(req);
        }
        req.send(null);
    }     
} */




function handle_loadOffice(req)
{
  //alert("handle_loadOffice");
    if(req.readyState==4)
    {
     
     if(req.status==200)
     {
    	 //alert("20000");
      
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
        var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="success")
        { 
         
         try
         {
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
            var offidvalues=baseresponse.getElementsByTagName("offid");
       
            for(i=0;i<offidvalues.length;i++)
            {  //alert("i"+offidvalues.length);
                var option=document.createElement("OPTION");
                var offid=baseresponse.getElementsByTagName("offid")[i].firstChild.nodeValue;
                var offname=baseresponse.getElementsByTagName("offname")[i].firstChild.nodeValue;
                option.text=offname+"("+offid+")";
                option.value=offid;
                try
                {
                    cmboffice.add(option);
                }
                catch(errorObject )
                {
                    cmboffice.add(option,null);
                }   
            }
            
         }
         catch(err)
         {
            alert("Problem in Loading Office code ");
         }
            
        }
        else
        {
          
         try
         {
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--select office--";
            option.value="";
            try
            {
                cmboffice.add(option);
            }
            catch(errorObject )
            {
                cmboffice.add(option,null);
            }
         }
         catch(err)
         {
            alert("Problem in Loading Office code ");
         }         
            
            
        }
            
             
     }
    }
}

function funAddNew(path){

	 //alert("AddNEW...");
	var detail_amt=0;
	
	
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	/*var today = new Date();
	var day = today.getDate();
	//var month = document.getElementById("cash_month").value;
	
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
*/
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	var txtBillNo = document.getElementById("txtBillNo").value;
	var txtBillDate = document.getElementById("txtBillDate").value;
/*	if(txtBillDate.split("/")[1]==5 && txtBillDate.split("/")[2]==2015)
	{
		document.getElementById("txtBillDate").value='';
		alert('Try with May month entry Tomorrow...');
		document.getElementById("txtBillDate").focus();
	}*/
	/*var selectnews=document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingNo;
	var txtProceedingNo=selectnews.options[selectnews.selectedIndex].text;*/
	var txtProceedingNo=document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingNo.value;
	//var txtProceedingNo =document.getElementByID("txtProceedingNo").options[document.getElementByID("txtProceedingNo").selectedIndex].text;
	//var txtProceedingNo = document
	//		.getElementById("txtProceedingNo").value;
	var txtProceedingDate = document
			.getElementById("txtProceedingDate").value;
	
	if (document.frm_BillTokenRegisterEntry_WithProceeding.rdoMTC_70_Register[0].checked == true) {
		rdoMTC_70_Register = document.frm_BillTokenRegisterEntry_WithProceeding.rdoMTC_70_Register[0].value;
	} else if (document.frm_BillTokenRegisterEntry_WithProceeding.rdoMTC_70_Register[1].checked == true) {
		rdoMTC_70_Register = document.frm_BillTokenRegisterEntry_WithProceeding.rdoMTC_70_Register[1].value;
	}
	//var txtPayeeType = document.getElementById("txtPayeeType").value;
	//var txtPayeeCode = document.getElementById("txtPayeeCode").value;
       var txtTotalSanctionedAmount= document.getElementById("txtTotalSanctionedAmount").value;
	var txtTotalBillAmount = document.getElementById("txtTotalBillAmount").value;
       var txtPayableTo=document.getElementById("txtPayableTo").value;
   //  	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	var cboOffice = document.getElementById("cboOffice").value;
//	var txtBudgetProvision = document.getElementById("txtBudgetProvision").value;
	//var txtBudgetSpent = document.getElementById("txtBudgetSpent").value;
	//var txtRefNo = document.getElementById("txtRefNo").value;
//	var txtRefDate = document.getElementById("txtRefDate").value;
	var mtxtRemarks = document.getElementById("mtxtRemarks").value;
	
	var sanction_id=document.getElementById("sanc_id").value;
	
	if (cboBillMajorType == "" || cboBillMajorType == 0) 
       {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMajorType
				.focus();
	} 
      
        if((document.getElementById("cboBillMinorType").length > 1) && (cboBillMinorType == "" || cboBillMinorType == 0))
       {
		alert("Select Bill Minor Type in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMinorType
				.focus();
	} 
        if((document.getElementById("cboBillSubType").length >1) && (cboBillSubType == "" || cboBillSubType == 0))
       {
              alert("Select Bill Sub Type in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboBillSubType
				.focus();
       } else if (document.getElementById("txtBillNo").value == "") {
		alert("Enter Bill No in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtBillNo.focus();
	} else if (document.getElementById("txtBillDate").value == "") {
		alert("Enter Bill Date in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtBillDate
				.focus();
	}else if (document.getElementById("txtProceedingNo").value == "") {
		alert("Enter Proceeding No in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingNo
				.focus();
	} else if (document.getElementById("txtProceedingDate").value == "") {
		alert("Enter Proceeding Date in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingDate
				.focus();
	}
       else if (document.getElementById("txtTotalSanctionedAmount").value == "") {
		alert("Enter Total Sanctioned Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtTotalSanctionedAmount
				.focus();
	} else if (document.getElementById("txtTotalBillAmount").value == "") {
		alert("Enter Total Bill Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtTotalBillAmount
				.focus();
	} /* else if (document.getElementById("txtAcc_HeadCode").value == "") {
		alert("Enter Account Head code in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtAcc_HeadCode
				.focus();
	} */
//	else if (document.getElementById("txtPayeeType").value == "") {
//		alert("Enter Payee Type in the Field");
//		document.frm_BillTokenRegisterEntry_WithProceeding.txtPayeeType
//				.focus();
//	}
//	else if (document.getElementById("txtPayeeCode").value == "") {
//		alert("Enter Payee Code in the Field");
//		document.frm_BillTokenRegisterEntry_WithProceeding.txtPayeeCode
//				.focus();
//	}
	else if (document.getElementById("txtPayableTo").value == "") {
		alert("Enter Payable To in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtPayableTo
				.focus();
	} 
       else if (document.getElementById("txtEmpID_mas").value == "") {
		alert("Enter Bill Processing Done By in the field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtEmpID_mas
				.focus();
	}  else {

		var url = path
				+ "/Bills_Token_Register_with_SP?command=AddNew&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
			//	+ "&year=" + year + "&month=" + month 
				+ "&cboBillMajorType="
				+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
				+ "&cboBillSubType=" + cboBillSubType + "&txtBillNo="
				+ txtBillNo + "&txtBillDate=" + txtBillDate
				+ "&txtProceedingDate=" + txtProceedingDate
				+ "&txtProceedingNo=" + txtProceedingNo
				+ "&rdoMTC_70_Register=" + rdoMTC_70_Register
				+ "&txtPayableTo=" +txtPayableTo
				+ "&txtTotalBillAmount=" + txtTotalBillAmount
				+ "&txtTotalSanctionedAmount=" + txtTotalSanctionedAmount
		//		+ "&txtAcc_HeadCode=" + txtAcc_HeadCode
				+ "&txtEmpID_mas=" + txtEmpID_mas + "&cboOffice=" + cboOffice
			//	+ "&txtRefNo=" + txtRefNo
			//	+ "&txtRefDate=" + txtRefDate 
				+ "&mtxtRemarks=" + mtxtRemarks+"&sanc_id="+sanction_id;
	
		var t;
		if(document.getElementById("notgpfdetails").style.display=="none")
			 t=document.getElementById("grid_body1").rows.length;
		else if(document.getElementById("gpfdetails").style.display=="none")
		 t=document.getElementById("grid_body").rows.length;
		
		var slno,H_code,b_alloted,budget_spent,ref_no,ref_date,pay_type,pay_code,pay_amt;
		  slno=new Array();
		  H_code=new Array();
		  b_alloted=new Array();
		  budget_spent=new Array();
		  ref_no=new Array();
		  ref_date=new Array();
		  pay_type=new Array();
		  pay_code=new Array();
		  pay_amt=new Array();
		
		  
		for(var k=0;k<t;k++){
			
			var j=k+1;
		
			var rcells=document.getElementById(j).cells;
	           //  alert(rcells.item(2).firstChild.value);
				
				slno[k]=rcells.item(0).firstChild.value;
				H_code[k]=rcells.item(1).firstChild.value;
				pay_amt[k]=rcells.item(2).firstChild.value;
				pay_type[k]=rcells.item(3).firstChild.value;
				pay_code[k]=rcells.item(4).firstChild.value;
				b_alloted[k]=rcells.item(5).firstChild.value;
				budget_spent[k]=rcells.item(6).firstChild.value;
				ref_no[k]=rcells.item(7).firstChild.value;
				ref_date[k]=rcells.item(8).firstChild.value;
				
		}
		
		url=url+"&slno="+slno+"&H_code="+H_code+"&pay_amt="+pay_amt+"&pay_type="+pay_type
		+"&pay_code="+pay_code
		+"&b_alloted="+b_alloted
		+"&budget_spent="+budget_spent
		+"&ref_no="+ref_no
		+"&ref_date="+ref_date;
		//alert("NEW >>> "+url);
		var xmlrequest = AjaxFunction();
	
		xmlrequest.open("POST", url, true);
		document.getElementById("butSub").disabled=true;
		document.getElementById("imgfld").style.visibility = "visible";
	
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};

		xmlrequest.send(null);
	}	
}
function cash_monthss()
{
	//alert("******");
	var combo1 = document.getElementById("cash_month");
    var txtProceedingNo = combo1.options[combo1.selectedIndex].text;
    document.getElementById("cash_mont").value=txtProceedingNo;
}
