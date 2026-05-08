
var seq=0;
var com_id;
var balance;
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
             // alert("enter.....");
			var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
			
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			var command = tagCommand.firstChild.nodeValue;
		

			if (command == "loadmajortype") {
				loadMajor(baseResponse);
			}
			if (command == "loadminortype") {
				loadMinor(baseResponse);
			}
		
			if (command == "loadsubType") {
				loadSub(baseResponse);
			}
			 else if(command=="loadempdetails")
             {
                   LoadEmpDetails(baseResponse);
             }
		
			 else if(command=="add")
             {
                   addResult(baseResponse);
             }
			 else if(command=="getAmount")
             {
                   getResultAmount(baseResponse);
             }
			 else if(command=="loaddes")
             {
                   comboloadDes(baseResponse);
             }
			 else if(command=="checkpayment")
			 {
				 
				 LoadPaymentOption(baseResponse);
			 }
}
}
}
function LoadPaymentOption(baseResponse)
{
	

var type = baseResponse.getElementsByTagName("type")[0].firstChild.nodeValue;
	if(type=="rent")
	{
		var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var option= baseResponse.getElementsByTagName("option")[0].firstChild.nodeValue;
		var payment1= baseResponse.getElementsByTagName("payment1")[0].firstChild.nodeValue;
		var payment2= baseResponse.getElementsByTagName("payment2")[0].firstChild.nodeValue;
		var payment3= baseResponse.getElementsByTagName("payment3")[0].firstChild.nodeValue;
		var payment4= baseResponse.getElementsByTagName("payment4")[0].firstChild.nodeValue;
		if(payment1<=9 && payment1>=1)
			payment1="0"+payment1;
		if(payment2<=9 && payment2>=1)
			payment2="0"+payment2;
		if(payment3<=9 && payment3>=1)
			payment3="0"+payment3;
		if(payment4<=9 && payment4>=1)
			payment4="0"+payment4;
		//alert(payment3);
		//alert(payment4);
		document.getElementById("cmbPaymentOption").value=option;
		document.getElementById("txtPaymentPeriod1").value=payment1;
    	document.getElementById("txtPaymentPeriod2").value=payment2;
    	document.getElementById("txtPaymentPeriod3").value=payment3;
    	document.getElementById("txtPaymentPeriod4").value=payment4;	
    	document.getElementById("cmbPaymentOption").disabled=true;
		document.getElementById("txtPaymentPeriod1").disabled=true;
    	document.getElementById("txtPaymentPeriod2").disabled=true;
    	document.getElementById("txtPaymentPeriod3").disabled=true;
    	document.getElementById("txtPaymentPeriod4").disabled=true;
	}
	else if(flag=="nodata")
		
	{
		alert("No Record For payment Option");
	}
	}
	else
	{
		
		document.getElementById("txtPaymentPeriod1").value='0';
    	document.getElementById("txtPaymentPeriod2").value='0';
    	document.getElementById("txtPaymentPeriod3").value='0';
    	document.getElementById("txtPaymentPeriod4").value='0';
		
	}
}
function  addResult(baseResponse)
{
	
	if (flag == "success") {
		alert("Insert Successfully");
	}
	else
	{
		alert("Have an Error in Insert");
	}	
	
}
function  comboloadDes(baseResponse)
{
	
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
	
		//alert( baseResponse.getElementsByTagName("date3")[0].firstChild.nodeValue);
		
	    var len =baseResponse.getElementsByTagName("desid").length;
	
	    for ( var i = 0; i < len; i++) {
			var com1id = baseResponse.getElementsByTagName("desid")[i].firstChild.nodeValue;
			var com1name = baseResponse.getElementsByTagName("desname")[i].firstChild.nodeValue;
			var se = document.getElementById("cmbSanctionAutrority");
			var op = document.createElement("OPTION");
			op.value = com1id;
			var txt = document.createTextNode(com1name);
			op.appendChild(txt);
			se.appendChild(op);

		}
	} else {
		alert("Fail to Load");
	}
	
}

function addResult(baseResponse)
{
	
	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
      if(flag=="success")
      {
    	  alert("Record Inserted Successfully");
    	  refreash();
      }
    
	
}
function  getResultAmount(baseResponse)
{
	
	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	  //alert("flag.........................................................................."+flag);
      if(flag=="success")
      {
    document.getElementById("txtBudgetProvided").value=baseResponse.getElementsByTagName("provided")[0].firstChild.nodeValue;
    document.getElementById("txtBudgetSoFar").value=baseResponse.getElementsByTagName("spend")[0].firstChild.nodeValue;
    balance=baseResponse.getElementsByTagName("balance")[0].firstChild.nodeValue;  
    	  
      }
      else if(flag=="nodata")
      {
    	  alert("NO Record in Buget Table");
      }
	
//    var payment1= parseInt(document.getElementById("txtPaymentPeriod1").value);
//  	var payment2= parseInt(document.getElementById("txtPaymentPeriod2").value);
//  	var payment3= parseInt(document.getElementById("txtPaymentPeriod3").value);
//  	var payment4= parseInt(document.getElementById("txtPaymentPeriod4").value);
  	//var amt=payment1+payment2+payment3+payment4;
  	//alert(amt);
  	//document.getElementById("txtAmount").value=amt;
}

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
                document.frmSanctionProceedingsPeriod.txtEmpID_mas.value=emp;
                Load_emp_details();
        }
        else if(emp_flag==2)
        {
                document.frmSanctionProceedingsPeriod.txtEmpID_mas1.value=emp;
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
         //alert("emp_flag+++++++++++++++++++++++++++"+emp_flag);
         //alert(document.frmSanctionProceedingsPeriod.r1[1].checked);
        
          if(emp_flag==2 &&  document.frmSanctionProceedingsPeriod.r1[1].checked==true)
          {
        	  
        	  
        	  var url="";
              //  url="../../../../../phone_master_servlet?command=loadempdetails&emp_id="+emp_id;
                url = "../../../../../SanctionProceeding?command=pensioner&empid="+emp_id;
                //alert(url);
                var req=getTransport();
                 req.open("GET",url,true);        
                 req.onreadystatechange=function()
                 {
               	  manipulate(req);
                 }   
                 req.send(null);
        	  
        	  
        	  
          }
  	  
          else
          {
  	  
  	  
        
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
                                
                                document.frmSanctionProceedingsPeriod.txtSanction_Estimate_PreparedBy.value=emp_name;
                         }
                         else if(emp_flag==2)
                         {
                        	 
                                document.frmSanctionProceedingsPeriod.txtPayeeName.value=emp_name;
                                
                               
                         }
                }
                else if(flag=="nodata")
                {
                        alert("Invalid Employee Id");
                }
                else if(flag=="pension")
                {
                	var emp_name=baseResponse.getElementsByTagName("name")[0].firstChild.nodeValue;
                	
                	var emp_id=baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue;
                	document.frmSanctionProceedingsPeriod.txtEmpID_mas1.value=emp_id;
                	document.frmSanctionProceedingsPeriod.txtPayeeName.value=emp_name;
                	
                	
                	
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




function txt_empty(txt) 
{
	var k=0;
	var s=txt.split('|');
//alert(s);
	for(var i=0;i<s.length;i++)
	{
		//alert(document.getElementById(s[i]).value);
	if(document.getElementById(s[i]).value == "") 
	{ 
		
		var a=s[i].split('txt');
		
	alert(a[1]+"   Should Not Be Blank");
	document.getElementById(s[i]).focus();
	return false;
	} 
	else if(document.getElementById(s[i]).value == "s")
	{

		var a=s[i].split('cmb');
		alert(a[1]+"   Should Not Be Blank");
		document.getElementById(s[i]).focus();
	   return false;
		
	}
	
	}
    return true;
}
function numbersonly(myfield,e,dec)
{
	//alert("hai");
	var key;
	var keychar;
	if (window.event)
	   key = window.event.keyCode;
	else if (e)
	   key = e.which;
	else
	   return true;
	keychar = String.fromCharCode(key);
// control keys
	if ((key==null) || (key==0) || (key==8) || 
		(key==9) || (key==13) || (key==27)  || (key==43) || (key==45))
	   return true;
	// numbers
	else if ((("0123456789").indexOf(keychar) > -1))
	   return true;
	// decimal point jump
	else if (dec && (keychar == ".")) {
	   myfield.form.elements[dec].focus();
	   return false;
	} else
	   return false;
}
function loadmajortype()
{
	//alert("enter");
	  var url = "../../../../../SanctionProceeding?command=loadmajortype";
    
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);	
	
	
	
	
}

function loaddes()
{
	//alert("enter");
	  var url = "../../../../../SanctionProceeding?command=loaddes";
    
	//alert(url);
	  var xmlrequest = AjaxFunction();
     	xmlrequest.open("POST", url, true);
     	xmlrequest.onreadystatechange = function() {
     		manipulate(xmlrequest);
     		}

     	xmlrequest.send(null);

	
	
	
}



function loadMinorType()
{
    var cmbMajorType=document.getElementById("cmbMajorType").value;
	  var url = "../../../../../SanctionProceeding?command=loadminortype&cmbMajorType="+cmbMajorType;
  
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);	
	
	
	
	
}
function loadsubType()
{
	 var cmbMajorType=document.getElementById("cmbMajorType").value;
	 
	 var cmbMinorType=document.getElementById("cmbMinorType").value;
	 
	  var url = "../../../../../SanctionProceeding?command=loadsubType&cmbMajorType="+cmbMajorType+"&cmbMinorType="+cmbMinorType;
 
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);	
}

function loadMajor(baseResponse)
{
	
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
	
		//alert( baseResponse.getElementsByTagName("date3")[0].firstChild.nodeValue);
		
	    var len =baseResponse.getElementsByTagName("majorid").length;
	    var se = document.getElementById("cmbMajorType");
	    se.length=1;
	    for ( var i = 0; i < len; i++) {
			var com1id = baseResponse.getElementsByTagName("majorid")[i].firstChild.nodeValue;
			var com1name = baseResponse.getElementsByTagName("majorname")[i].firstChild.nodeValue;
			
			var op = document.createElement("OPTION");
			op.value = com1id;
			var txt = document.createTextNode(com1name);
			op.appendChild(txt);
			se.appendChild(op);

		}
	}
	else if(flag=="NoData")
	{
		
		alert("No Record is Found");
	}
	
	
	
	else {
		alert("Fail to Load");
	}
		

	
	
}

function loadMinor(baseResponse)
{
	//alert("enter........................................................");
	
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

//alert("flag............"+flag);	
	if (flag == "success") {
	
		//alert( baseResponse.getElementsByTagName("date3")[0].firstChild.nodeValue);
		
	    var len =baseResponse.getElementsByTagName("miorid").length;
	   se= document.getElementById("cmbMinorType");
	    se.length=1;
		
	
	    for ( var i = 0; i < len; i++) {
			var com1id = baseResponse.getElementsByTagName("miorid")[i].firstChild.nodeValue;
			var com1name = baseResponse.getElementsByTagName("miorname")[i].firstChild.nodeValue;
//			if(se.length==0)
//			{
//				var op = document.createElement("OPTION");
//				op.value ='0';
//				var txt = document.createTextNode("select");
//				op.appendChild(txt);
//				se.appendChild(op);
//				
//			}
			
			var op = document.createElement("OPTION");
			op.value = com1id;
			var txt = document.createTextNode(com1name);
			op.appendChild(txt);
			se.appendChild(op);

		}
	}
	else if(flag=="NoData")
	{
		
		alert("No Record is Found");
	}
	else  {
		alert("Fail to Load");
	}	
}
function loadSub(baseResponse)
{
	//alert("enter........................................................");
	
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
	
		//alert( baseResponse.getElementsByTagName("date3")[0].firstChild.nodeValue);
		
	    var len =baseResponse.getElementsByTagName("subid").length;
	    var se = document.getElementById("cmbBillSubType");
	   se.length=1;
	    for ( var i = 0; i < len; i++) {
			var com1id = baseResponse.getElementsByTagName("subid")[i].firstChild.nodeValue;
			var com1name = baseResponse.getElementsByTagName("subname")[i].firstChild.nodeValue;
			
			var op = document.createElement("OPTION");
			op.value = com1id;
			var txt = document.createTextNode(com1name);
			op.appendChild(txt);
			se.appendChild(op);

		}
	}
	else if(flag=="NoData")
	{
		
		alert("No Record is Found");
	}
	
	else {
		alert("Record Dose Not Exists");
	}
		

	
	
}


////////////////////////////////////////////////////////////////////////////////   FUNCTION FOR ADD FUNCTION WITH SERVLET

function add()
{
	var valid=txt_empty('cmbCashBookYear|cmbCashBookMonth|cmbMajorType|cmbMinorType|cmbBillSubType|txtEmpID_mas1|txtRefNo|txtRefDate|txtEmpID_mas|txtSanctionProceedingFromDate|txtSanctionProceedingToDate|txtSanctionProceedingDate|cmbPaymentOption|cmbSanctionAutrority|txtPaymentPeriod1|txtSanctionAmount|txtAcc_HeadCode|txtBudgetProvided|txtBudgetSoFar|txtMade|mtxtRemarks|txtSanction_Estimate_PreparedBy');
			if(valid)
			{
	  var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value
	  var cmbOffice_code=document.getElementById("cmbOffice_code").value
	  var cmbCashBookYear=document.getElementById("cmbCashBookYear").value
	  var cmbCashBookMonth= document.getElementById("cmbCashBookMonth").value
	  var cmbMajorType=document.getElementById("cmbMajorType").value;
      var cmbMinorType= document.getElementById("cmbMinorType").value;  
      var cmbBillSubType =  document.getElementById("cmbBillSubType").value;
      var txtEmpID_mas1= document.getElementById("txtEmpID_mas1").value
      var txtRefNo = document.getElementById("txtRefNo").value;
      var txtRefDate = document.getElementById("txtRefDate").value;
      var txtSanctionProceedingDate= document.getElementById("txtSanctionProceedingDate").value
      var txtEmpID_mas=document.getElementById("txtEmpID_mas").value
      var cmbSanctionAutrority = document.getElementById("cmbSanctionAutrority").value;  
      var cmbSanctionBy = document.getElementById("txtEmpID_mas").value;
      var txtSanctionProceedingFromDate= document.getElementById("txtSanctionProceedingFromDate").value;  
      var txtSanctionProceedingToDate= document.getElementById("txtSanctionProceedingToDate").value
      var cmbPaymentOption=document.getElementById("cmbPaymentOption").value
      
      var txtPaymentPeriod1=document.getElementById("txtPaymentPeriod1").value
      var txtPaymentPeriod2=document.getElementById("txtPaymentPeriod2").value
      var txtPaymentPeriod3=document.getElementById("txtPaymentPeriod3").value
      var txtPaymentPeriod4=document.getElementById("txtPaymentPeriod4").value
      var txtSanctionAmount = document.getElementById("txtSanctionAmount").value;
      var cmbAccHeadCode =document.getElementById("txtAcc_HeadCode").value;
      var  txtBudgetProvided  = document.getElementById("txtBudgetProvided").value;
      var  txtBudgetSoFar  = document.getElementById("txtBudgetSoFar").value;
      var   txtAmount = document.getElementById("txtAmount").value;  
      var   txtMade = document.getElementById("txtMade").value;
      var  mtxtRemarks = document.getElementById("mtxtRemarks").value;
     
     if(document.frmSanctionProceedingsPeriod.r1[0].checked==true)
     {
    	 var r1=document.frmSanctionProceedingsPeriod.r1[0].value;
     }
     else
     {
    	 var r1=document.frmSanctionProceedingsPeriod.r1[1].value;
     }
	
//	var amount=parseInt(txtAmount);
//	alert(balance);
//	alert(amount);
//	 if(balance<amount)
//	 {
//		 
//		 alert("Total Sanction Amount is Greater Than  Buget Amount ... Plz Check the payment Period");
//		 document.getElementById("txtPaymentPeriod1").focus();
//		 return true;
//		 
//		 
//	 }
//	 
//	 if(txtSanctionAmount!=total_amt)
//	 {
//		
//		 alert("Details Sanction Amount is Not Equal To Total Amount  ... Plz Check the  Details");
//		 document.getElementById("txtSanctionAmount").focus();
//		 return true;
//		 
//	 }
	
  var url = "../../../../../SanctionProceedingPeriod?command=add&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cmbCashBookYear="+cmbCashBookYear+"&cmbCashBookMonth="+cmbCashBookMonth+"&cmbMajorType="+cmbMajorType+"&cmbMinorType="+cmbMinorType+"&cmbBillSubType="
     +cmbBillSubType+"&payeecode="+txtEmpID_mas1+"&payeetype="+r1+"&txtRefNo="+txtRefNo+"&txtRefDate="+txtRefDate+"&txtSanctionProceedingDate="+txtSanctionProceedingDate+"&cmbSanctionAutrority="+cmbSanctionAutrority+"&cmbSanctionBy="+cmbSanctionBy+"&txtSanctionProceedingFromDate="+txtSanctionProceedingFromDate+"&txtSanctionProceedingToDate="+txtSanctionProceedingToDate+
     "&cmbPaymentOption="+cmbPaymentOption+"&txtPaymentPeriod1="+txtPaymentPeriod1+"&txtPaymentPeriod2="+txtPaymentPeriod2+"&txtPaymentPeriod3="+txtPaymentPeriod3+"&txtPaymentPeriod4="+txtPaymentPeriod4+"&txtSanctionAmount="+txtSanctionAmount+"&cmbAccHeadCode="+cmbAccHeadCode+"&txtBudgetProvided="+txtBudgetProvided+"&txtBudgetSoFar="
             +txtBudgetSoFar+"&txtAmount="+txtAmount+"&txtMade="+txtMade+"&mtxtRemarks="+mtxtRemarks;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);	
	 }
	 else
	 {
		 
		 
		 
	 }
	 
	 
}

function getAmount()
{
	
    var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
	 
	 var cmbCashBookYear=document.getElementById("cmbCashBookYear").value;
	 
	 var cmbCashBookMonth=document.getElementById("cmbCashBookMonth").value;
	 
	  var url = "../../../../../SanctionProceeding?command=getAmount&txtAcc_HeadCode="+txtAcc_HeadCode+"&cmbCashBookYear="+cmbCashBookYear+"&cmbCashBookMonth="+cmbCashBookMonth;
 
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
	alert("Exit");
	window.close();
}

function refreash()
{
	
	
	document.getElementById("cmbCashBookYear").value='0';
    document.getElementById("cmbCashBookMonth").value='0'; 
	 document.getElementById("cmbMajorType").value='s';
     document.getElementById("cmbMinorType").value='s'; 
      document.getElementById("cmbBillSubType").value='s';
      document.getElementById("txtRefNo").value='';
       document.getElementById("txtRefDate").value='';
       
       document.getElementById("txtEmpID_mas").value='';  
       document.getElementById("txtEmpID_mas1").value='';  
       document.getElementById("txtSanctionProceedingFromDate").value='';  
       document.getElementById("txtSanctionProceedingToDate").value='';  
       
       
      document.getElementById("txtSanctionProceedingDate").value='';
      document.getElementById("cmbPaymentOption").value='s';
     document.getElementById("cmbSanctionAutrority").value='s';
     document.getElementById("txtPaymentPeriod1").value='0';
     document.getElementById("txtPaymentPeriod2").value='0';
     document.getElementById("txtPaymentPeriod3").value='0';
     document.getElementById("txtPaymentPeriod4").value='0';
     document.getElementById("txtSanctionAmount").value='';
     
     
     document.getElementById("txtAcc_HeadCode").value='';
     document.getElementById("txtBudgetProvided").value='';
     document.getElementById("txtBudgetSoFar").value='';
     document.getElementById("txtAmount").value='';  
     document.getElementById("txtMade").value='';
     document.getElementById("mtxtRemarks").value='';
     document.getElementById("txtPayeeName").value='';
     document.getElementById("txtSanction_Estimate_PreparedBy").value='';
     document.getElementById("txtAcc_HeadDesc").value='';
      
     document.frmSanctionProceedingsPeriod.r1[0].checked=false;
     document.frmSanctionProceedingsPeriod.r1[1].checked=false;
     document.getElementById("cmbPaymentOption").disabled=false;
		document.getElementById("txtPaymentPeriod1").disabled=false;
 	document.getElementById("txtPaymentPeriod2").disabled=false;
 	document.getElementById("txtPaymentPeriod3").disabled=false;
 	document.getElementById("txtPaymentPeriod4").disabled=false;
	
}
function CheckPayment()
{
	  var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value
	  var cmbOffice_code=document.getElementById("cmbOffice_code").value
	  var cmbMajorType=document.getElementById("cmbMajorType").value;
      var cmbMinorType= document.getElementById("cmbMinorType").value;  
      var cmbBillSubType =  document.getElementById("cmbBillSubType").value;
      var txtEmpID_mas1= document.getElementById("txtEmpID_mas1").value
      var valid=txt_empty('cmbMajorType|cmbMinorType|txtEmpID_mas1');
		if(valid)
		{
      var url = "../../../../../SanctionProceedingPeriod?command=checkpayment&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cmbMajorType="+cmbMajorType+"&cmbMinorType="+cmbMinorType+"&cmbBillSubType="+cmbBillSubType+"&payeecode="+txtEmpID_mas1;
    	//alert(url);
    	var xmlrequest = AjaxFunction();
    	xmlrequest.open("POST", url, true);
    	xmlrequest.onreadystatechange = function() {
    		manipulate(xmlrequest);
    		}

    	xmlrequest.send(null);	
    	 }
}
      
	
//	var payment1= document.getElementById("txtPaymentPeriod1").value;
//	var payment2=document.getElementById("txtPaymentPeriod2").value;
//	var payment3= document.getElementById("txtPaymentPeriod3").value;
//	var payment4= document.getElementById("txtPaymentPeriod4").value;
//	var payment5=document.getElementById("txtSanctionAmount").value;	
//	var option=document.getElementById("cmbPaymentOption").value;
//	alert("option"+option);
//    if(option=="M")
//    {
//    	document.getElementById("txtPaymentPeriod1").readOnly=false; 
//    	document.getElementById("txtPaymentPeriod2").readOnly=true; 
//    	document.getElementById("txtPaymentPeriod3").readOnly=true; 
//    	document.getElementById("txtPaymentPeriod4").readOnly=true; 
//    	document.getElementById("txtPaymentPeriod3").value=0;
//    	document.getElementById("txtPaymentPeriod4").value=0;
//    	document.getElementById("txtPaymentPeriod2").value=0;
//    	document.getElementById("txtPaymentPeriod1").value='';
//    	
//    }
//    else if(option=="H")
//    {
//    	document.getElementById("txtPaymentPeriod1").readOnly=false; 
//    	document.getElementById("txtPaymentPeriod2").readOnly=false; 
//    	document.getElementById("txtPaymentPeriod3").readOnly=true; 
//    	document.getElementById("txtPaymentPeriod4").readOnly=true; 
//    	document.getElementById("txtPaymentPeriod3").value=0;
//    	document.getElementById("txtPaymentPeriod4").value=0;
//    	document.getElementById("txtPaymentPeriod2").value='';
//    	document.getElementById("txtPaymentPeriod1").value='';
//    	
//    }
//    else if(option=="A")
//    {
//    	document.getElementById("txtPaymentPeriod1").readOnly=false; 
//    	document.getElementById("txtPaymentPeriod2").readOnly=true; 
//    	document.getElementById("txtPaymentPeriod3").readOnly=true; 
//    	document.getElementById("txtPaymentPeriod4").readOnly=true; 
//    	document.getElementById("txtPaymentPeriod3").value=0;
//    	document.getElementById("txtPaymentPeriod4").value=0;
//    	document.getElementById("txtPaymentPeriod2").value=0;
//    	document.getElementById("txtPaymentPeriod1").value='';
//    
//    	
//    }
//    else if(option=="Q")
//    {
//    	document.getElementById("txtPaymentPeriod1").readOnly=false; 
//    	document.getElementById("txtPaymentPeriod2").readOnly=false; 
//    	document.getElementById("txtPaymentPeriod3").readOnly=false; 
//    	document.getElementById("txtPaymentPeriod4").readOnly=false; 
//    	document.getElementById("txtPaymentPeriod3").value='';
//    	document.getElementById("txtPaymentPeriod4").value='';
//    	document.getElementById("txtPaymentPeriod2").value='';
//    	document.getElementById("txtPaymentPeriod1").value='';
//    	
//    	
//    }

//}
function getFinalAmount()
{
	
   var	option=document.getElementById("cmbPaymentOption").value
 	var amount =document.getElementById("txtSanctionAmount").value
 	if(option=="M")
 	{
 		var tot_amount=parseFloat(amount)*12;
 		document.getElementById("txtAmount").value=tot_amount;
 	}
 	else if(option=="Q")
 	{
 		var tot_amount=parseFloat(amount)*4;
 		document.getElementById("txtAmount").value=tot_amount;
 	}
 	else if(option=="A")
 	{
 		document.getElementById("txtAmount").value=amount;
 	}
 	else if(option=="H")
 	{
 		var tot_amount=parseFloat(amount)*2;
 		document.getElementById("txtAmount").value=tot_amount;
 	}

}
function setVisible()
{
	var	option=document.getElementById("cmbPaymentOption").value
	 if(option=="M")
	    {
	    	document.getElementById("txtPaymentPeriod1").disabled=false; 
	    	document.getElementById("txtPaymentPeriod2").disabled=true; 
	    	document.getElementById("txtPaymentPeriod3").disabled=true; 
	    	document.getElementById("txtPaymentPeriod4").disabled=true; 
	    	
	    	
	    }
	    else if(option=="H")
	    {
	    	document.getElementById("txtPaymentPeriod1").disabled=false; 
	    	document.getElementById("txtPaymentPeriod2").disabled=false; 
	    	document.getElementById("txtPaymentPeriod3").disabled=true; 
	    	document.getElementById("txtPaymentPeriod4").disabled=true; 
	    	document.getElementById("txtPaymentPeriod3").value=0;
	    	document.getElementById("txtPaymentPeriod4").value=0;
	    	document.getElementById("txtPaymentPeriod2").value='';
	    	document.getElementById("txtPaymentPeriod1").value='';
	    	
	    }
	    else if(option=="A")
	    {
	    	document.getElementById("txtPaymentPeriod1").disabled=false; 
	    	document.getElementById("txtPaymentPeriod2").disabled=true; 
	    	document.getElementById("txtPaymentPeriod3").disabled=true; 
	    	document.getElementById("txtPaymentPeriod4").disabled=true; 
	    	
	    
	    	
	    }
	    else if(option=="Q")
	    {
	    	document.getElementById("txtPaymentPeriod1").disabled=false; 
	    	document.getElementById("txtPaymentPeriod2").disabled=false; 
	    	document.getElementById("txtPaymentPeriod3").disabled=false; 
	    	document.getElementById("txtPaymentPeriod4").disabled=false; 
	    	
	    	
	    	
	    }
}


