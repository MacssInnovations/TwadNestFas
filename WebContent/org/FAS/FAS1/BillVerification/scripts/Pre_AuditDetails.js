
var seq=0;
var com_id;

//function validate()
//{
//	var cmbCashBookYear=document.getElementById("cmbCashBookYear").value
//	var cmbCashBookMonth= document.getElementById("cmbCashBookMonth").value
//	var cmbMtcRegisterNo=document.getElementById("cmbMtcRegisterNo").value
//	var mtxtRemarks=document.getElementById("mtxtRemarks").value
//	var cmbPassOrderNO=document.getElementById("cmbPassOrderNO").value
//	var cmdProceedinnNo=document.getElementById("cmdProceedinnNo").value
//	var cmbBillNO=document.getElementById("cmbBillNO").value
//	var txtParticulars=document.getElementById("txtParticulars").value
//	var txtNetAmount=document.getElementById("txtNetAmount").value
//	
//	var mtxtRemarks1=document.getElementById("mtxtRemarks1").value
//	
//	if(cmbCashBookYear=='--Select--')
//	{
//		alert("Should Be Select Year");
//		document.getElementById("cmbCashBookYear").focus();
//		return true;
//		
//	}
//	if(cmbCashBookMonth=='--Select--')
//	{
//		alert("Should Be Select Month");
//		document.getElementById("cmbCashBookMonth").focus();
//		 return true;
//	}
//	if(cmbMtcRegisterNo=='--Select--')
//	{
//		alert("Should Be Select RegisteNO");
//		document.getElementById("cmbMtcRegisterNo").focus();
//		 return true;
//	}
//	if(mtxtRemarks=='')
//	{
//		alert("Remarks Should Not Be Blank");
//		document.getElementById("mtxtRemarks").focus();
//		 return true;
//	}
//	
//	if(cmbPassOrderNO=='--Select--')
//	{
//		alert("Should Be Select PassOrederNo");
//		document.getElementById("cmbPassOrderNO").focus();
//		 return true;
//	}
//	
//	if(cmdProceedinnNo=='--Select--')
//	{
//		alert("Should Be Select  ProceedingNo");
//		document.getElementById("cmdProceedinnNo").focus();
//		 return true;
//	}
//	
//	if(cmbBillNO=='--Select--')
//	{
//		alert("Should Be Select  BillNo");
//		document.getElementById("cmbBillNO").focus();
//		 return true;
//	}
//	
//	if(txtParticulars=='')
//	{
//		alert("Particulars Should Not Be Blank");
//		document.getElementById("txtParticulars").focus();
//		 return true;
//	}
//	if(txtNetAmount=='')
//	{
//		alert("Amount Should Not Be Blank");
//		document.getElementById("txtNetAmount").focus();
//		 return true;
//	}
//
//	 
//	
//	if(mtxtRemarks1=='')
//	{
//		alert("Remark Should Not Be Blank");
//		document.getElementById("mtxtRemarks1").value
//		 return true;
//	}
//
//	 
//	
//	
//	
//	
//}



function loadcombo() {
	
	//alert("start");
	  var unit_id=document.getElementById("cmbAcc_UnitCode").value;
      var office_id=document.getElementById("cmbOffice_code").value;
	  var cyear=document.getElementById("cmbCashBookYear").value;
	  var cmonth=document.getElementById("cmbCashBookMonth").value;
	  alert(cmonth);
	var url = "../../../../../Pre_AuditDetails?command=getValue&unit_id="+unit_id+"&office_id="+office_id+"&cyear="+cyear+"&cmonth="+cmonth;
	//alert(url);
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);

}


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

			var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
			
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			var command = tagCommand.firstChild.nodeValue;
		

			if (command == "getValue") {
				combo1load(baseResponse);
			}
			else if(command=="getDate")
				{
				loadDate1(baseResponse);
				}
			else if(command=="getDate1")
			{
			loadDate2(baseResponse);
			}
			else if(command=="getDate2")
			{
			loadDate3(baseResponse);
			}
			else if(command=="getDate3")
			{
			loadDate4(baseResponse);
			}
			else if(command=="add")
			{
			getValues(baseResponse);
			}
			else if(command=="getEmpName")
			{
				getempname(baseResponse);
			}
			  else if(command=="loadempdetails")
              {
                    LoadEmpDetails(baseResponse);
              }
}
}
}

function getempname(baseResponse)
{
	
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
	
		var len = baseResponse.getElementsByTagName("empname").length;
	
		//alert(len);

		
			var com1id = baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue;
		
			var se = document.getElementById("cmbMas_SL_Code1");
			var op = document.createElement("OPTION");
			document.getElementById("cmbMas_SL_Code1").length=0;
			op.value = com1id;
			var txt = document.createTextNode(com1id);
			op.appendChild(txt);
			se.appendChild(op);

		
	
	
	
}
	else
	{
		alert("Employee Id does not exists");
	}
}



function getValues(baseResponse)
{
	
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
		alert("Record insert Successfully");
		refreash();
	}
	else if(flag=="exists")
	{
		alert("Record Exists");
		refreash();
	}
	
}
function combo1load(baseResponse)
{
	
	//alert("enter combo1load");

var flag= baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
var flag1= baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
var len = baseResponse.getElementsByTagName("id").length;
var len1 = baseResponse.getElementsByTagName("id1").length;

var se = document.getElementById("cmbMtcRegisterNo");
	se.length=1;
	var se1 = document.getElementById("cmbPassOrderNO");
	se1.length=1;
	if (flag == "success") {
		//document.getElementById("cmbMtcRegisterNo").length='0';
		for ( var i = 0; i < len; i++) {
			var com1id = baseResponse.getElementsByTagName("id")[i].firstChild.nodeValue;
		
			
			var op = document.createElement("OPTION");
			op.value = com1id;
			var txt = document.createTextNode(com1id);
			op.appendChild(txt);
			se.appendChild(op);

		}
	}
	else if(flag=="Nodata")
	{
		alert("No Data for MTCRegisterNO ");
	}
	else
	{
		alert("Fail to Load");
	}
	if(flag1=="success")
	{
		//document.getElementById("cmbPassOrderNO").length='0';
		
		alert("length"+se1.length);
	for ( var i = 0; i < len1; i++) {
		///alert("enter into for");
		var com1id = baseResponse.getElementsByTagName("id1")[i].firstChild.nodeValue;
	
		
		var op = document.createElement("OPTION");
		op.value = com1id;
		var txt = document.createTextNode(com1id);
		op.appendChild(txt);
		se1.appendChild(op);

	}
}
	else if(flag1=="Nodata1")
	{
		
		alert("Nodata for Pass Order Number");
	}
	
	
	else {
		alert("Fail to Load");
	
}
	
	
	
	
}
function getDate()
{
	
	var mtcno=document.getElementById("cmbMtcRegisterNo").value;
    var url = "../../../../../Pre_AuditDetails?command=getDate&mtcno="+mtcno;
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);

	
	
	
}

function getDate1()
{
	
	var passno=document.getElementById("cmbPassOrderNO").value;
	//alert(passno);
    var url = "../../../../../Pre_AuditDetails?command=getDate1&passno="+passno;
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);

	
	
	
}

function getDate2()
{
	
	var prono=document.getElementById("cmdProceedinnNo").value;

    var url = "../../../../../Pre_AuditDetails?command=getDate2&prono="+prono;
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);

	
	
	
	
	
	
}

function getDate3()
{
	
	var billno=document.getElementById("cmbBillNO").value;

    var url = "../../../../../Pre_AuditDetails?command=getDate3&billno="+billno;
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);	
}

function loadDate1(baseResponse)
{
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
	
		document.getElementById("txtRegisterDate").value = baseResponse.getElementsByTagName("date1")[0].firstChild.nodeValue;
		
	
	}
	else if(flag == "Nodata")
			{
		alert("NO data for MTCRegister Number");
			}
	
}
function loadDate2(baseResponse)
{
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
	
		//alert( baseResponse.getElementsByTagName("date2")[0].firstChild.nodeValue);
		document.getElementById("txtPassOrderDate").value= baseResponse.getElementsByTagName("date2")[0].firstChild.nodeValue;
	    var len =baseResponse.getElementsByTagName("pNo").length;
	    var se = document.getElementById("cmdProceedinnNo");
	    se.length=1;
	    for ( var i = 0; i < len; i++) {
			var com1id = baseResponse.getElementsByTagName("pNo")[i].firstChild.nodeValue;
		
			
			var op = document.createElement("OPTION");
			op.value = com1id;
			var txt = document.createTextNode(com1id);
			op.appendChild(txt);
			se.appendChild(op);

		}
	}
	else if(flag=="Nodata") 
	{
			alert("No Data For ProceedingNO");
	}
		
	
	else {
		alert("Fail to Load");
	}
		
	}
function loadDate3(baseResponse)
{
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
	
		//alert( baseResponse.getElementsByTagName("date3")[0].firstChild.nodeValue);
		document.getElementById("txtProceedingDate").value= baseResponse.getElementsByTagName("date3")[0].firstChild.nodeValue;
	    var len =baseResponse.getElementsByTagName("proNo").length;
		var se = document.getElementById("cmbBillNO");
		se.length=1;
	    for ( var i = 0; i < len; i++) {
			var com1id = baseResponse.getElementsByTagName("proNo")[i].firstChild.nodeValue;
		
		
			var op = document.createElement("OPTION");
			op.value = com1id;
			var txt = document.createTextNode(com1id);
			op.appendChild(txt);
			se.appendChild(op);

		}
	}
	else if(flag=="Nodata")
	{
		alert("NO Data For BillNumber");
	}
	
	else {
		alert("Fail to Load");
	}
		
	}

function loadDate4(baseResponse)
{
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
	
		//alert( baseResponse.getElementsByTagName("date4")[0].firstChild.nodeValue);
		document.getElementById("txtBillDate").value= baseResponse.getElementsByTagName("date4")[0].firstChild.nodeValue;
		document.getElementById("txtAmount").value= baseResponse.getElementsByTagName("amount")[0].firstChild.nodeValue;
	    
		
	}

}

function add()
{
	var k=0;
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value
	var cmbOffice_code=document.getElementById("cmbOffice_code").value
	var cmbCashBookYear=document.getElementById("cmbCashBookYear").value
	var cmbCashBookMonth= document.getElementById("cmbCashBookMonth").value
	var cmbMtcRegisterNo=document.getElementById("cmbMtcRegisterNo").value
	var txtRegisterDate=document.getElementById("txtRegisterDate").value
	var txtDateOfReceipt=document.getElementById("txtDateOfReceipt").value
	var txtEmpID_mas=document.getElementById("txtEmpID_mas").value
	var emp1=document.getElementById("txtEmpID_mas1").value
	var mtxtRemarks=document.getElementById("mtxtRemarks").value
	
	
	
	
	///alert(cmbMtcRegisterNo);
	
	if(cmbCashBookYear=='s')
	{
		alert("Should Be Select Year");
		document.getElementById("cmbCashBookYear").focus();
		return true;
		
	}
	if(cmbCashBookMonth=='s')
	{
		alert("Should Be Select Month");
		document.getElementById("cmbCashBookMonth").focus();
		 return true;
	}
	if(cmbMtcRegisterNo=='s')
	{
		alert("Should Be Select RegisteNO");
		document.getElementById("cmbMtcRegisterNo").focus();
		 return true;
	}
	if(mtxtRemarks=='')
	{
		alert("Remarks Should Not Be Blank");
		document.getElementById("mtxtRemarks").focus();
		 return true;
	}
	
	/*if(cmbPassOrderNO=='0')
	{
		alert("Should Be Select PassOrederNo");
		document.getElementById("cmbPassOrderNO").focus();
		 return true;
	}
	
	if(cmdProceedinnNo=='0')
	{
		alert("Should Be Select  ProceedingNo");
		document.getElementById("cmdProceedinnNo").focus();
		 return true;
	}
	
	if(cmbBillNO=='0')
	{
		alert("Should Be Select  BillNo");
		document.getElementById("cmbBillNO").focus();
		 return true;
	}
	
	if(txtParticulars=='')
	{
		alert("Particulars Should Not Be Blank");
		document.getElementById("txtParticulars").focus();
		 return true;
	}
	if(txtNetAmount=='')
	{
		alert("Amount Should Not Be Blank");
		document.getElementById("txtNetAmount").focus();
		 return true;
	}

	 
	
	if(mtxtRemarks1=='')
	{
		alert("Remark Should Not Be Blank");
		document.getElementById("mtxtRemarks1").value
		 return true;
	}


	if(emp1=='')
	{
		alert("audited by Should Not Be Blank");
		document.getElementById("txtEmpID_mas1").value
		 return true;
	}

	 if(document.frmPre_AuditDetails.r1[0].checked==true)
	 {
		 var r1=document.frmPre_AuditDetails.r1[0].value;
	 }
	 else if(document.frmPre_AuditDetails.r1[1].checked==true)
	 {
		 var r1=document.frmPre_AuditDetails.r1[1].value; 
	 }
	
	 if(document.frmPre_AuditDetails.r2[0].checked==true)
	 {
		 alert("if");
		 var r2=document.frmPre_AuditDetails.r2[0].value;
	 }
	 else if(document.frmPre_AuditDetails.r2[1].checked==true)
	 {
		 alert("else");
		 var r2=document.frmPre_AuditDetails.r2[1].value; 
	 }
	 else
	 {
		 var r2=null;
	 }
	 if(document.frmPre_AuditDetails.r3[0].checked==true)
	 {
		 var r3=document.frmPre_AuditDetails.r3[0].value;
	 }
	 else if(document.frmPre_AuditDetails.r3[1].checked==true)
	 {
		 var r3=document.frmPre_AuditDetails.r3[1].value; 
	 }*/
	
	 var tbody = document.getElementById("tblList");
		var rowcount=tbody.rows.length;
		
		//alert(rowcount);
		var al= new Array() ;
	   
	    for(var i=0;i<rowcount;i++)
	    	{
	    	   var r=tbody.rows[i];
	    	   var s=r.cells.length;
	    	  
		   
	    	   for(var j=2;j<s;j++)
	    		   {
	    		   if(r.cells[0].firstChild.checked)
	    		   {
	    		 
	    		   
	    		   al[k]=r.cells[j].firstChild.nodeValue;
	    		  // alert( al[i]);
	    		    k++; 
	    		     //alert(r.cells[j].firstChild.nodeValue);
	    		   }
	    		   }
	    	
	    	}
	alert(al);
    var url = "../../../../../Pre_AuditDetails?command=add&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cmbCashBookYear="+cmbCashBookYear+"&cmbCashBookMonth="+cmbCashBookMonth+"&cmbMtcRegisterNo="+cmbMtcRegisterNo+"&txtRegisterDate="+txtRegisterDate+"&txtDateOfReceipt="
       +txtDateOfReceipt+"&txtEmpID_mas="+txtEmpID_mas+"&emp1="+emp1+"&mtxtRemarks="+mtxtRemarks+"&grid="+al;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);	
	
	
	
}

function exitfun()
{
	alert("exit");
	window.close();
	}
function refreash1()
{
	
	document.getElementById("cmbPassOrderNO").value='s';
	document.getElementById("txtPassOrderDate").value="";
	document.getElementById("cmdProceedinnNo").value='s';
    document.getElementById("txtProceedingDate").value="";
	document.getElementById("cmbBillNO").value="s";
	document.getElementById("txtBillDate").value="";
	document.getElementById("txtAmount").value="";
	
	document.getElementById("txtParticulars").value="";
	document.getElementById("txtDate").value="";
	document.getElementById("txtNetAmount").value="";
	
	document.getElementById("mtxtRemarks1").value="";
	document.frmPre_AuditDetails.r1[0].checked=false;
	document.frmPre_AuditDetails.r1[1].checked=false;
	document.frmPre_AuditDetails.r2[0].checked=false;
	document.frmPre_AuditDetails.r2[1].checked=false;
	document.frmPre_AuditDetails.r3[0].checked=false;
	document.frmPre_AuditDetails.r3[1].checked=false;
	
	
	
	
}
function refreash()
{
	
	
var k=0;
	document.getElementById("cmbCashBookYear").value='s';
	document.getElementById("cmbCashBookMonth").value='s';
	document.getElementById("cmbMtcRegisterNo").value='s';
	document.getElementById("txtRegisterDate").value="";
	document.getElementById("txtDateOfReceipt").value=""
	document.getElementById("txtEmpID_mas").value="";
	document.getElementById("txtEmpID_mas1").value="";
	document.getElementById("txtSanction_Estimate_PreparedBy").value="";
	document.getElementById("txtSanction_Estimate_ApprovedBy").value="";
	document.getElementById("mtxtRemarks").value="";
	
	
	document.getElementById("cmbPassOrderNO").value='s';
	document.getElementById("txtPassOrderDate").value="";
	document.getElementById("cmdProceedinnNo").value='s';
    document.getElementById("txtProceedingDate").value="";
	document.getElementById("cmbBillNO").value="s";
	document.getElementById("txtBillDate").value="";
	document.getElementById("txtAmount").value="";
	
	document.getElementById("txtParticulars").value="";
	document.getElementById("txtDate").value="";
	document.getElementById("txtNetAmount").value="";
	
	document.getElementById("mtxtRemarks1").value="";
	document.frmPre_AuditDetails.r1[0].checked=false;
	document.frmPre_AuditDetails.r1[1].checked=false;
	document.frmPre_AuditDetails.r2[0].checked=false;
	document.frmPre_AuditDetails.r2[1].checked=false;
	document.frmPre_AuditDetails.r3[0].checked=false;
	document.frmPre_AuditDetails.r3[1].checked=false;
	var tbody = document.getElementById("tblList");
	var rowcount=tbody.rows.length;
	for(var i=0;i<rowcount;i++)
	{
	   var r=tbody.rows[i];
	   var s=r.cells.length;
	  
   
	   for(var j=1;j<s;j++)
		   {
		   
		 
		   
		   r.cells[j].firstChild.nodeValue='';
		  // alert( al[i]);
		   k++; 
		     //alert(r.cells[j].firstChild.nodeValue);
		   
		   }

}
	
	
}
function empname(id)
{
	//alert(id);
   
	

    var url = "../../../../../Pre_AuditDetails?command=getEmpName&empid="+id;
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);	
	
	
	
	
}
///////////////////////////////employeeee

function emp_sanction_preparedby()
{
        emp_flag=1;    
        Load_emp_details();
}
function emp_popup_sanction_preparedby()
{
        
        emp_flag=1;    
        servicepopup();
        
}
function emp_sanction_approvedby()
{
        emp_flag=2;  
        Load_emp_details();
}
function emp_popup_sanction_approvedby()
{
        emp_flag=2;    
        servicepopup();
}


var winemp;
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
        winemp=null
    }
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}
function doParentEmp(emp)
{
        if(emp_flag==1)
        {
                document.frmPre_AuditDetails.txtEmpID_mas.value=emp;
                Load_emp_details();
        }
        else if(emp_flag==2)
        {
                document.frmPre_AuditDetails.txtEmpID_mas1.value=emp;
                Load_emp_details();
        }
}
function Load_emp_details()
{
       
        if(emp_flag==1)
        {
                var emp_id=document.getElementById("txtEmpID_mas").value;
              
        }
        else if(emp_flag==2)
        {
                var emp_id=document.getElementById("txtEmpID_mas1").value;
              
        }

             var url="";
           //  url="../../../../../phone_master_servlet?command=loadempdetails&emp_id="+emp_id;
             url = "../../../../../Pre_AuditDetails?command=getEmpName&empid="+emp_id;
             //alert(url);
             var req=getTransport();
              req.open("GET",url,true);        
              req.onreadystatechange=function()
              {
            	  manipulate(req);
              }   
              req.send(null);
     
}

function LoadEmpDetails(baseResponse)
{
                 
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                  if(flag=="success")
                  {                       
                         var emp_name=baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue;
                        // var desig_name=baseResponse.getElementsByTagName("desig_name")[0].firstChild.nodeValue;
                        // var office_name=baseResponse.getElementsByTagName("office_name")[0].firstChild.nodeValue;
                         if(emp_flag==1)
                         {
                                
                                document.frmPre_AuditDetails.txtSanction_Estimate_PreparedBy.value=emp_name;
                         }
                         else if(emp_flag==2)
                         {
                                document.frmPre_AuditDetails.txtSanction_Estimate_ApprovedBy.value=emp_name;
                               
                         }
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
	                return false 
	          }
         }
}

function caldiv()
{
	
	
	if(document.frmPre_AuditDetails.r1[1].checked==true)
	{
		document.getElementById("div1").style.display='none';
		document.getElementById("div2").style.display='none';
	}
	if(document.frmPre_AuditDetails.r1[0].checked==true)
	{
		document.getElementById("div1").style.display='block';
		document.getElementById("div2").style.display='block';
		
	}
	
	
}
function add1()
{
 //  alert("enter into add1");
	var cmbPassOrderNO=document.getElementById("cmbPassOrderNO").value
	var txtPassOrderDate=document.getElementById("txtPassOrderDate").value
	var cmdProceedinnNo=document.getElementById("cmdProceedinnNo").value
	var txtProceedingDate=document.getElementById("txtProceedingDate").value
	var cmbBillNO=document.getElementById("cmbBillNO").value
	var txtBillDate=document.getElementById("txtBillDate").value
	var txtAmount=document.getElementById("txtAmount").value
	
	
	var txtParticulars=document.getElementById("txtParticulars").value
	var txtDate=document.getElementById("txtDate").value
	var txtNetAmount=document.getElementById("txtNetAmount").value
	
	var mtxtRemarks1=document.getElementById("mtxtRemarks1").value
	
	if(cmbPassOrderNO=='s')
	{
		alert("Should Be Select PassOrederNo");
		document.getElementById("cmbPassOrderNO").focus();
		 return true;
	}
	
	if(cmdProceedinnNo=='s')
	{
		alert("Should Be Select  ProceedingNo");
		document.getElementById("cmdProceedinnNo").focus();
		 return true;
	}
	
	if(cmbBillNO=='0')
	{
		alert("Should Be Select  BillNo");
		document.getElementById("cmbBillNO").focus();
		 return true;
	}
	
	if(txtParticulars=='')
	{
		alert("Particulars Should Not Be Blank");
		document.getElementById("txtParticulars").focus();
		 return true;
	}
	if(txtNetAmount=='')
	{
		alert("Amount Should Not Be Blank");
		document.getElementById("txtNetAmount").focus();
		 return true;
	}

	 
	
	if(mtxtRemarks1=='')
	{
		alert("Remark Should Not Be Blank");
		document.getElementById("mtxtRemarks1").value
		 return true;
	}


	

	 if(document.frmPre_AuditDetails.r1[0].checked==true)
	 {
		 var r1=document.frmPre_AuditDetails.r1[0].value;
	 }
	 else if(document.frmPre_AuditDetails.r1[1].checked==true)
	 {
		 var r1=document.frmPre_AuditDetails.r1[1].value; 
	 }
	
	 if(document.frmPre_AuditDetails.r2[0].checked==true)
	 {
		 //alert("if");
		 var r2=document.frmPre_AuditDetails.r2[0].value;
	 }
	 else if(document.frmPre_AuditDetails.r2[1].checked==true)
	 {
		// alert("else");
		 var r2=document.frmPre_AuditDetails.r2[1].value; 
	 }
	 else
	 {
		 var r2=null;
	 }
	 if(document.frmPre_AuditDetails.r3[0].checked==true)
	 {
		 var r3=document.frmPre_AuditDetails.r3[0].value;
	 }
	 else if(document.frmPre_AuditDetails.r3[1].checked==true)
	 {
		 var r3=document.frmPre_AuditDetails.r3[1].value; 
	 }
	
   var cmbPassOrderNO=document.getElementById("cmbPassOrderNO").value
   //alert(cmbPassOrderNO);
	var txtPassOrderDate=document.getElementById("txtPassOrderDate").value
	var cmdProceedinnNo=document.getElementById("cmdProceedinnNo").value
	var txtProceedingDate=document.getElementById("txtProceedingDate").value
	var cmbBillNO=document.getElementById("cmbBillNO").value
	var txtBillDate=document.getElementById("txtBillDate").value
	var txtAmount=document.getElementById("txtAmount").value
	
	
	var txtParticulars=document.getElementById("txtParticulars").value
	var txtDate=document.getElementById("txtDate").value
	var txtNetAmount=document.getElementById("txtNetAmount").value
	
	var mtxtRemarks1=document.getElementById("mtxtRemarks1").value
	 if(document.frmPre_AuditDetails.r1[0].checked==true)
	 {
		 var r1=document.frmPre_AuditDetails.r1[0].value;
	 }
	 else if(document.frmPre_AuditDetails.r1[1].checked==true)
	 {
		 var r1=document.frmPre_AuditDetails.r1[1].value; 
	 }
	
	 if(document.frmPre_AuditDetails.r2[0].checked==true)
	 {
		
		 var r2=document.frmPre_AuditDetails.r2[0].value;
	 }
	 else if(document.frmPre_AuditDetails.r2[1].checked==true)
	 {
		
		 var r2=document.frmPre_AuditDetails.r2[1].value; 
	 }
	 else
	 {
		 var r2=null;
	 }
	 if(document.frmPre_AuditDetails.r3[0].checked==true)
	 {
		 var r3=document.frmPre_AuditDetails.r3[0].value;
	 }
	 else if(document.frmPre_AuditDetails.r3[1].checked==true)
	 {
		 var r3=document.frmPre_AuditDetails.r3[1].value; 
	 }
	try
	{
	   if(cmbBillNO!=null)
	   {
		//alert("enter into try ");
		var tbody = document.getElementById("tblList");
	    var table = document.getElementById("Existing");
		var mycurrent_row = document.createElement("TR");
		mycurrent_row.id = seq;
		var cell = document.createElement("TD");
		var anc = document.createElement("input");
		anc.type = "checkbox";
		anc.id=seq;
		anc.name="check";
		anc.checked=true;
		anc.value=seq;
		
		
		
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);
		//alert("one"+cmbBillNO);

		var cell1 = document.createElement("TD");
		var anc1 = document.createElement("A");
		var url="javascript:loadValuesFromTable('"+seq+"')";
		anc1.href = url;
		var txtedit = document.createTextNode("Edit");
		anc1.appendChild(txtedit);
		cell1.appendChild(anc1);
		mycurrent_row.appendChild(cell1);
		//alert(url);

		var cell2 = document.createElement("TD");
		var cmbPassOrderNO = document.createTextNode(cmbPassOrderNO);
		cell2.appendChild(cmbPassOrderNO);
		mycurrent_row.appendChild(cell2);
        
		//alert("2"+cmbPassOrderNO);
		
		var cell3 = document.createElement("TD");
		var txtPassOrderDate = document.createTextNode(txtPassOrderDate);
		cell3.appendChild(txtPassOrderDate);
		mycurrent_row.appendChild(cell3);

		//alert("3"+txtPassOrderDate);
		
		//--------------------------------------------------------		
		var cell4 = document.createElement("TD");
		var cmdProceedinnNo = document.createTextNode(cmdProceedinnNo);
		cell4.appendChild(cmdProceedinnNo);
		mycurrent_row.appendChild(cell4);
		
		//alert("4"+cmdProceedinnNo);
		
		var cell5 = document.createElement("TD");
		var txtProceedingDate = document.createTextNode(txtProceedingDate);
		cell5.appendChild(txtProceedingDate);
		mycurrent_row.appendChild(cell5);
		
		//alert("5"+txtProceedingDate);
		
		var cell6 = document.createElement("TD");
		var cmbBillNO = document.createTextNode(cmbBillNO);
		cell6.appendChild(cmbBillNO);
		mycurrent_row.appendChild(cell6);
		
		//alert("6"+cmbBillNO);
		
		var cell7= document.createElement("TD");
		var txtBillDate = document.createTextNode(txtBillDate);
		cell7.appendChild(txtBillDate);
		mycurrent_row.appendChild(cell7);
		
		//alert("7"+txtBillDate);
		
		
		
		var cell8= document.createElement("TD");
		var txtAmount = document.createTextNode(txtAmount);
		cell8.appendChild(txtAmount);
		mycurrent_row.appendChild(cell8);
		
		//alert("8"+txtAmount);
		
		//alert(r1);
		var cell9= document.createElement("TD");
		var r1 = document.createTextNode(r1);
		cell9.appendChild(r1);
		mycurrent_row.appendChild(cell9);
		
		//alert("9"+r1);
		
		
		
		var cell10= document.createElement("TD");
		var r2 = document.createTextNode(r2);
		cell10.appendChild(r2);
		mycurrent_row.appendChild(cell10);
		
		//alert("10"+r2);
		
		
		
		var cell11= document.createElement("TD");
		var txtParticulars = document.createTextNode(txtParticulars);
		cell11.appendChild(txtParticulars);
		mycurrent_row.appendChild(cell11);
		
		
		//alert("11"+txtParticulars);
		
		var cell12= document.createElement("TD");
		var txtDate = document.createTextNode(txtDate);
		cell12.appendChild(txtDate);
		mycurrent_row.appendChild(cell12);
		
		
		//alert("12"+txtDate);
		
		
		
		var cell13= document.createElement("TD");
		var txtNetAmount = document.createTextNode(txtNetAmount);
		cell13.appendChild(txtNetAmount);
		mycurrent_row.appendChild(cell13);
		
		
		//alert("13"+txtNetAmount);
		
		var cell14= document.createElement("TD");
		var r3 = document.createTextNode(r3);
		cell14.appendChild(r3);
		mycurrent_row.appendChild(cell14);
		
		//alert("14"+r3);
		
		
		var cell15= document.createElement("TD");
		var mtxtRemarks1 = document.createTextNode(mtxtRemarks1);
		cell15.appendChild(mtxtRemarks1);
		mycurrent_row.appendChild(cell15);
		
		
		//alert("15"+mtxtRemarks1);
		
		
		tbody.appendChild(mycurrent_row);
	    seq++;
	 
	}
		else
		{
			alert("load fail");
		}
		
	}
	catch(errorObject)
	{
		
	}

	refreash1();
}

function loadValuesFromTable(id)
{
	com_id=id;
	  r=document.getElementById(id);
	//r=id;
	 // alert(id);
      rcells=r.cells;
      alert(rcells.item(2).firstChild.nodeValue);
      document.getElementById("cmbPassOrderNO").Value= rcells.item(2).firstChild.nodeValue;
      document.getElementById("cmbPassOrderNO").Text= rcells.item(2).firstChild.nodeValue;
      
      document.getElementById("txtPassOrderDate").value= rcells.item(3).firstChild.nodeValue;
      document.getElementById("cmdProceedinnNo").value=rcells.item(4).firstChild.nodeValue;
      document.getElementById("cmdProceedinnNo").Text=rcells.item(4).firstChild.nodeValue;
      document.getElementById("txtProceedingDate").value=rcells.item(5).firstChild.nodeValue;
      document.getElementById("cmbBillNO").value=rcells.item(6).firstChild.nodeValue;
      document.getElementById("cmbBillNO").Text=rcells.item(6).firstChild.nodeValue;
      document.getElementById("txtBillDate").value=rcells.item(7).firstChild.nodeValue;
      document.getElementById("txtAmount").value=rcells.item(8).firstChild.nodeValue;
      var s1=rcells.item(9).firstChild.nodeValue;
      if(s1=='Y')
      {
    	  document.frmPre_AuditDetails.r1[0].checked=true;
      }
      else
      {
    	  document.frmPre_AuditDetails.r1[1].checked=true;
      }
      
      var s2=rcells.item(10).firstChild.nodeValue;
      if(s2=='Y')
      {
    	  document.frmPre_AuditDetails.r2[0].checked=true;
      }
      else
      {
    	 
    	  document.frmPre_AuditDetails.r2[1].checked=true;
      }
      document.getElementById("txtParticulars").value=rcells.item(11).firstChild.nodeValue;
      document.getElementById("txtDate").value=rcells.item(12).firstChild.nodeValue;
      document.getElementById("txtNetAmount").value=rcells.item(13).firstChild.nodeValue;
      var s3=rcells.item(14).firstChild.nodeValue;
      if(s3=='Y')
      {
    	  document.frmPre_AuditDetails.r3[0].checked=true;
      }
      else
      {
    	  document.frmPre_AuditDetails.r3[1].checked=true;
      }
      document.getElementById("mtxtRemarks1").value=rcells.item(15).firstChild.nodeValue;
}
function update1()
{
//alert("enter ----------------------");
	var items = new Array();
	items[0] =  document.getElementById("cmbPassOrderNO").value;
	items[1] =  document.getElementById("txtPassOrderDate").value;
	items[2] =  document.getElementById("cmdProceedinnNo").value;
	items[3] =  document.getElementById("txtProceedingDate").value;
	items[4] =  document.getElementById("cmbBillNO").value;
	items[5] =  document.getElementById("txtBillDate").value;
	
	 if(document.frmPre_AuditDetails.r1[0].checked==true)
	 {
		 items[6] =document.frmPre_AuditDetails.r1[0].value;
	 }
	 else if(document.frmPre_AuditDetails.r1[1].checked==true)
	 {
		 items[6]=document.frmPre_AuditDetails.r1[1].value; 
	 }
	
	 if(document.frmPre_AuditDetails.r2[0].checked==true)
	 {
		
		 items[7]=document.frmPre_AuditDetails.r2[0].value;
	 }
	 else if(document.frmPre_AuditDetails.r2[1].checked==true)
	 {
		
		 items[7]=document.frmPre_AuditDetails.r2[1].value; 
	 }
	 else
	 {
		 items[7]=null;
	 }
	
	 items[8] =  document.getElementById("txtParticulars").value;
	 items[9] =  document.getElementById("txtDate").value;
	 items[10] =  document.getElementById("txtNetAmount").value;
	 if(document.frmPre_AuditDetails.r3[0].checked==true)
	 {
		 items[11] =document.frmPre_AuditDetails.r3[0].value;
	 }
	 else if(document.frmPre_AuditDetails.r3[1].checked==true)
	 {
		 items[11]=document.frmPre_AuditDetails.r3[1].value; 
	 }
	
	 items[12] =  document.getElementById("mtxtRemarks1").value;
	 items[13] =  document.getElementById("txtAmount").value;
	 
	 var r = document.getElementById(com_id);
		var rcells = r.cells;
		//alert(r);
		rcells.item(2).firstChild.nodeValue = items[0];
		rcells.item(3).firstChild.nodeValue = items[1];
		rcells.item(4).firstChild.nodeValue = items[2];
		rcells.item(5).firstChild.nodeValue = items[3];
		rcells.item(6).firstChild.nodeValue = items[4];
		rcells.item(7).firstChild.nodeValue = items[5];
		rcells.item(8).firstChild.nodeValue = items[13];
		rcells.item(9).firstChild.nodeValue = items[6];
		rcells.item(10).firstChild.nodeValue = items[7];
		rcells.item(11).firstChild.nodeValue = items[8];
		rcells.item(12).firstChild.nodeValue = items[9];
		rcells.item(13).firstChild.nodeValue = items[10];
		rcells.item(14).firstChild.nodeValue = items[11];
		rcells.item(15).firstChild.nodeValue = items[12];
	 
	 
		refreash1();
	 
}

function delete_GRID()
{
        if(confirm("Do you want to delete ?"))
        {
        var tbody=document.getElementById("Existing");
        var r=document.getElementById(com_id);
        var ri=r.rowIndex;
        tbody.deleteRow(ri);
        refreash1();
        }
}

