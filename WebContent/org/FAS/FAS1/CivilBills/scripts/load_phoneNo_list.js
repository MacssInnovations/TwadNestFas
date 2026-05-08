/** Browser Identification */

function getTransport()
{
	//alert('fuction getTransport');
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



var phone_no =new Array();


/** Main Function */
function load_Phone_Number()
{  

	   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
	   var cmbAcc_OfficeCode=document.getElementById("cmbOffice_code").value;
	  // alert('Unit Code = '+ cmbAcc_UnitCode + "/n  Office Code : " + cmbAcc_OfficeCode);
	   var url="../../../../../phone_bill_servlet?command=LoadPhoneNumber&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbAcc_OfficeCode="+cmbAcc_OfficeCode;
	   var req=getTransport();
	   req.open("GET",url,true); 
	   req.onreadystatechange=function()
	   {
		   load_Phone_NumberRes(req);
	   }   
       req.send(null);
	  
}

function load_Phone_NumberRes(req)
{  
 
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             var tagcommand=baseResponse.getElementsByTagName("command")[0];
           //  alert("command : "+ tagcommand);
             var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="LoadPhoneNumber")
            {
            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	 
            	// alert('flag value :' + flag);
            	 if(flag=="success")
            	    {
            	          
            	         //  alert('servlet called successfully');
            	                    	           
            	           /** Get Phone No */
            	           var cmbPhoneNo = document.getElementById("cmb_Phone_No");
            	           var phoneNocount = baseResponse.getElementsByTagName("phoneNo");
            	           
            	            for(var k=0;k<phoneNocount.length;k++)
            	            {
            	            	phone_no[k]=baseResponse.getElementsByTagName("phoneNo")[k].firstChild.nodeValue;
            	            	     	            	
            	            }
            	         
            	            cmbPhoneNo.innerHTML="";
            	            var option=document.createElement("OPTION");
            	            option.text="--Select Phone Number--";
            	            option.value="";
            	            try
            	            {
            	            	cmbPhoneNo.add(option);
            	            }catch(errorObject)
            	            {
            	            	cmbPhoneNo.add(option,null);
            	            }
            	            if(phoneNocount.length>0)
            	            {
            	            for(var k=0;k<phoneNocount.length;k++)
            	            {   
            	                  var option=document.createElement("OPTION");
            	                  option.text=phone_no[k];
            	                  option.value=phone_no[k];
            	                  try
            	                  {
            	                	  cmbPhoneNo.add(option);
            	                  }
            	                  catch(errorObject)
            	                  {
            	                	  cmbPhoneNo.add(option,null);
            	                  }
            	            }
            	            }
            	            else
            	            {
            	            	cmbPhoneNo.add(option,null)
            	            	
            	            }
            	            
            	    }
            	 else if (flag = "NoData") 
            	 {
					var cmbPhoneNo = document.getElementById("cmb_Phone_No");
					cmbPhoneNo.innerHTML = "";
					var option = document.createElement("OPTION");
					option.text = "--Select Phone Number--";
					option.value = "";
					try {
						cmbPhoneNo.add(option);
					} catch (errorObject) {
						cmbPhoneNo.add(option, null);
					}

				}
              }
            
              else
              {
            	    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;             	 
             	    if(flag=="success")
             	    {
             	    	//alert('load no failed ');
             	    	//	var acc_head_code=baseResponse.getElementsByTagName("acc_head_code")[0].firstChild.nodeValue;
             	    	//	document.getElementById("txtOprCode").value=acc_head_code;
             	    }
              }
        
        }
    }
}

/** Database transaction function (form name : PhoneBill_MainForm.jsp) */ 


function getPhoneNo_Details()
{
	var phoneNo = document.frmPhoneBillMainForm.cmb_Phone_No.value;
	var url = '../../../../../phone_bill_servlet?'+'command=getNoDetails'+'&cmb_Phone_No=' + phoneNo; 
	//alert(url);
	 var req=getTransport();
	   req.open("GET",url,true); 
	   req.onreadystatechange=function()
	   {
		   getPhoneNo_DetailsRes(req);
	   }   
  req.send(null);
}

function getPhoneNo_DetailsRes(req)
{
	 if(req.readyState==4)
	    {
	        if(req.status==200)
	        {  
	             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	             var tagcommand=baseResponse.getElementsByTagName("command")[0];
	           //  alert("command : "+ tagcommand);
	             var Command=tagcommand.firstChild.nodeValue;
	           
	            if(Command=="getNoDetails")
	            {
	            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            	 
	            	 	var txtConnectionType = document.frmPhoneBillMainForm.txtConnectionType;
            	    	var txtPurpose = document.frmPhoneBillMainForm.txtPurpose;
            	    	var txtProviderName = document.frmPhoneBillMainForm.txtProviderName;
            	    	var txtProviderType = document.frmPhoneBillMainForm.txtProviderType;
            	    	var txtCustodianName =document.frmPhoneBillMainForm.txtCustodianName;
            	    	var txtDesignation  = document.frmPhoneBillMainForm.txtDesignation;
	            	 
	            	 //alert('flag value :' + flag);
	            	 if(flag=="success")
	            	    {
	            	          var connectionType = baseResponse.getElementsByTagName("connectionType")[0].firstChild.nodeValue;
	            	          var purpose = baseResponse.getElementsByTagName("purpose")[0].firstChild.nodeValue;
	            	          var serviceProviderName = baseResponse.getElementsByTagName("serviceProviderName")[0].firstChild.nodeValue;
	            	          var serviceType = baseResponse.getElementsByTagName("serviceType")[0].firstChild.nodeValue;
	            	          var emplyeeName = baseResponse.getElementsByTagName("emplyeeName")[0].firstChild.nodeValue;
	            	          var designation = baseResponse.getElementsByTagName("designation")[0].firstChild.nodeValue;
	            	          txtConnectionType.value = connectionType;
	            	          txtPurpose.value = purpose;
	            	          txtProviderName.value = serviceProviderName;
	            	          txtProviderType.value = serviceType;
	            	          txtCustodianName.value = emplyeeName;
	            	          txtDesignation.value = designation;
	            	    }
	            	 else if(flag=='NoData')
	            	 {
	            		// alert('Number Details not Available from master table');
	            		 txtConnectionType.value = "";
           	          txtPurpose.value = "";
           	          txtProviderName.value = "";
           	          txtProviderType.value = "";
           	          txtCustodianName.value = "";
           	          txtDesignation.value = "";
	            	 }
	            	 
	            	           /** Get Phone No */
	            	       
	              }
	              else
	              {
	            	    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;             	 
	             	    if(flag=="success")
	             	    {
	             	    	alert('Some error occured ');

	             	    }
	              }
	        
	        }
	    }
}

function phone_Insert()
{
	var val = checkNull('insert');
	if(val==true)
	{
	/** tab - General values */
	var accountingUnitId =  document.frmPhoneBillMainForm.cmbAcc_UnitCode.value;
	var accountingOfficeId = document.frmPhoneBillMainForm.cmbOffice_code.value;
	var billDate = document.frmPhoneBillMainForm.txtBillDate.value;
	var totalBillAmount = document.frmPhoneBillMainForm.txtTotalBillAmount.value;
	var genRemarks = document.frmPhoneBillMainForm.txtGeneralRemarks.value;

	/** tab - Details values */
	
	var phoneNo = document.frmPhoneBillMainForm.cmb_Phone_No.value;
	var invoiceNo  = document.frmPhoneBillMainForm.txtInvoiceNo.value;
	var invoiceDate = document.frmPhoneBillMainForm.txtDate.value;
	var billYear=document.frmPhoneBillMainForm.txtBillYear.value;
	var billMonth = document.frmPhoneBillMainForm.txtBillMonth.value;
	var currentBillAmount  = document.frmPhoneBillMainForm.txtCurrentBillAmount.value;
	var detRemarks = document.frmPhoneBillMainForm.txtDetailRemarks.value;
	
	var bookMonthYear = billDate.split("/");
	 
	var url = '../../../../../phone_bill_servlet?'+'command=INSERT'
	+ '&cmbAcc_UnitCode=' + accountingUnitId 
	+ '&cmbOffice_code='+ accountingOfficeId 
	+ '&txtBillDate=' + billDate
	+ '&bookYear=' + bookMonthYear[2]
	+ '&bookMonth=' + bookMonthYear[1]
	+ '&txtTotalBillAmount=' + totalBillAmount
	+ '&txtGeneralRemarks='+ genRemarks 
	+ '&cmb_Phone_No=' + phoneNo 
	+ '&txtInvoiceNo=' + invoiceNo
	+ '&txtDate=' + invoiceDate 
	+ '&txtBillYear=' + billYear
	+ '&txtBillMonth=' + billMonth 
	+ '&txtCurrentBillAmount='+ currentBillAmount 
	+ '&txtDetailRemarks=' + detRemarks;  
	  	   
	alert(url);
	   
	  
	   var req=getTransport();
	   req.open("GET",url,true); 
	   req.onreadystatechange=function()
	   {
		   insertRes(req);
	   }   
    req.send(null);
	  
	}	
}

function insertRes(req)
{
	 if(req.readyState==4)
	    {
	        if(req.status==200)
	        {  
	             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	             var tagcommand=baseResponse.getElementsByTagName("command")[0];
	            // alert("command : "+ tagcommand);
	             var Command=tagcommand.firstChild.nodeValue;
	           
	            if(Command=="INSERT")
	            {
	            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            	 
	            	// alert('flag value :' + flag);
	            	 if(flag=="success")
	            	    {
	            	          
	            	           alert('Record Inserted Successfully.....!');
	            	           clearValues();
	            	    }              	           
	            	           /** Get Phone No */
	            	       
	              }
	        }
	    }
}


/** Function For Record Update */

function phone_Edit() 
{
	var val = checkNull('edit');
	if(val==true)
	{
	/** tab - General values */

	var accountingUnitId = document.frmPhoneBillMainForm.cmbAcc_UnitCode.value;
	var accountingOfficeId = document.frmPhoneBillMainForm.cmbOffice_code.value;
	var billDate = document.frmPhoneBillMainForm.txtBillDate.value;
	var totalBillAmount = document.frmPhoneBillMainForm.txtTotalBillAmount.value;
	var genRemarks = document.frmPhoneBillMainForm.txtGeneralRemarks.value;

	/** tab - Details values */

	var phoneNo = document.frmPhoneBillMainForm.cmb_Phone_No.value;
	var invoiceNo = document.frmPhoneBillMainForm.txtInvoiceNo.value;
	var invoiceDate = document.frmPhoneBillMainForm.txtDate.value;
	var billYear = document.frmPhoneBillMainForm.txtBillYear.value;
	var billMonth = document.frmPhoneBillMainForm.txtBillMonth.value;
	var currentBillAmount = document.frmPhoneBillMainForm.txtCurrentBillAmount.value;
	var detRemarks = document.frmPhoneBillMainForm.txtDetailRemarks.value;

	var bookMonthYear = billDate.split("/");
	
	var url = '../../../../../phone_bill_servlet?' + 'command=UPDATE'
			+ '&cmbAcc_UnitCode=' + accountingUnitId + '&cmbOffice_code='
			+ accountingOfficeId + '&txtBillDate=' + billDate
			+ '&bookYear=' + bookMonthYear[2]
			+ '&bookMonth=' + bookMonthYear[1]
			+ '&txtTotalBillAmount=' + totalBillAmount + '&txtGeneralRemarks='
			+ genRemarks + '&cmb_Phone_No=' + phoneNo + '&txtInvoiceNo='
			+ invoiceNo + '&txtDate=' + invoiceDate + '&txtBillYear='
			+ billYear + '&txtBillMonth=' + billMonth
			+ '&txtCurrentBillAmount=' + currentBillAmount
			+ '&txtDetailRemarks=' + detRemarks;

	alert(url);

	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		phone_EditRes(req);
	}
	req.send(null);
	}

}



function phone_EditRes(req)
{
	 if(req.readyState==4)
	    {
	        if(req.status==200)
	        {  
	             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	             var tagcommand=baseResponse.getElementsByTagName("command")[0];
	          //   alert("command : "+ tagcommand);
	             var Command=tagcommand.firstChild.nodeValue;
	           
	            if(Command=="UPDATE")
	            {
	            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            	 
	            //	 alert('flag value :' + flag);
	            	 if(flag=="success")
	            	    {
	            	          
	            	           alert('Record Updated Successfully.....!');
	            	           clearValues();
	            	    }              	           
	            	           /** Get Phone No */
	            	       
	              }
	              else
	              {
	            	    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;             	 
	             	    if(flag=="success")
	             	    {
	             	    	alert('load no failed ');

	             	    }
	              }
	        
	        }
	    }
	
}

/** Function Record Delete */

function phone_Delete()
{
	var val = checkNull('delete');
	if(val==true)
	{
	/** tab - General values */

	var accountingUnitId = document.frmPhoneBillMainForm.cmbAcc_UnitCode.value;
	var accountingOfficeId = document.frmPhoneBillMainForm.cmbOffice_code.value;
	var billDate = document.frmPhoneBillMainForm.txtBillDate.value;
	var totalBillAmount = document.frmPhoneBillMainForm.txtTotalBillAmount.value;
	var genRemarks = document.frmPhoneBillMainForm.txtGeneralRemarks.value;
	//cmb_Phone_No
	/** tab - Details values */

	var phoneNo = document.frmPhoneBillMainForm.cmb_Phone_No.value;
	var invoiceNo = document.frmPhoneBillMainForm.txtInvoiceNo.value;
	var invoiceDate = document.frmPhoneBillMainForm.txtDate.value;
	var billYear = document.frmPhoneBillMainForm.txtBillYear.value;
	var billMonth = document.frmPhoneBillMainForm.txtBillMonth.value;
	var currentBillAmount = document.frmPhoneBillMainForm.txtCurrentBillAmount.value;
	var detRemarks = document.frmPhoneBillMainForm.txtDetailRemarks.value;

	var url = '../../../../../phone_bill_servlet?' + 'command=DELETE'
			+ '&cmbAcc_UnitCode=' + accountingUnitId + '&cmbOffice_code='
			+ accountingOfficeId + '&txtBillDate=' + billDate
			+ '&txtTotalBillAmount=' + totalBillAmount + '&txtGeneralRemarks='
			+ genRemarks + '&cmb_Phone_No=' + phoneNo + '&txtInvoiceNo='
			+ invoiceNo + '&txtDate=' + invoiceDate + '&txtBillYear='
			+ billYear + '&txtBillMonth=' + billMonth
			+ '&txtCurrentBillAmount=' + currentBillAmount
			+ '&txtDetailRemarks=' + detRemarks;

	//alert(url);

	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		phone_DeleteRes(req);
	}
	req.send(null);
	}

}

function phone_DeleteRes(req)
{
	 if(req.readyState==4)
	    {
	        if(req.status==200)
	        {  
	             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	             var tagcommand=baseResponse.getElementsByTagName("command")[0];
	            // alert("command : "+ tagcommand);
	             var Command=tagcommand.firstChild.nodeValue;
	           
	            if(Command=="DELETE")
	            {
	            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            	 
	            //	 alert('flag value :' + flag);
	            	 if(flag=="success")
	            	    {
	            	          
	            	           alert('Record Deleted.....!');
	            	           clearValues();
	            	    }              	           
	            	           /** Get Phone No */
	            	       
	              }
	              else
	              {
	            	    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;             	 
	             	    if(flag=="success")
	             	    {
	             	    	alert('load no failed ');

	             	    }
	              }
	        
	        }
	    }
	
	
}


//////////////   FOR EMPLOYEE POPUP WINDOW //////cmb_Phone_No////////////////
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
        winemp=null
    }
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}

function doParentEmp(emp)
{
//document.formPhone_Master.txtEmpId.value=emp;
//Load_emp_details();
}
function clearValues()
{
//alert('clear funtion called');
      var today= new Date(); 
      var day=today.getDate();
      var month=today.getMonth();
      month=month+1;
      var year=today.getFullYear();
      var cur_Date = day + '/'+ month + '/'+ year;
      if(year < 1900) year += 1900;

     document.frmPhoneBillMainForm.txtBillDate.value= cur_Date;
     document.frmPhoneBillMainForm.txtDate.value= cur_Date;
     document.frmPhoneBillMainForm.txtBillYear.value= year
     document.frmPhoneBillMainForm.txtBillMonth.value= month;
     
     document.frmPhoneBillMainForm.txtTotalBillAmount.value="";
     document.frmPhoneBillMainForm.txtGeneralRemarks.value="";
     
     document.frmPhoneBillMainForm.txtConnectionType.value="";
     document.frmPhoneBillMainForm.txtPurpose.value="";
     document.frmPhoneBillMainForm.txtProviderName.value="";
     document.frmPhoneBillMainForm.txtProviderType.value="";
     document.frmPhoneBillMainForm.txtCustodianName.value="";

     document.frmPhoneBillMainForm.txtDesignation.value="";
     document.frmPhoneBillMainForm.txtInvoiceNo.value="";
     
     document.frmPhoneBillMainForm.txtCurrentBillAmount.value="";
     document.frmPhoneBillMainForm.txtDetailRemarks.value="";

     document.frmPhoneBillMainForm.btnAdd.disabled = false;
     document.frmPhoneBillMainForm.btnDelete.disabled = false;
     document.frmPhoneBillMainForm.txtBillDate.disabled = true;
     document.frmPhoneBillMainForm.txtDate.disabled = true;
     document.frmPhoneBillMainForm.txtBillYear.disabled = true;
     document.frmPhoneBillMainForm.txtBillMonth.disabled = true;

}
/** Allows Number only */
function numbersonly1(e,t)
{
   var unicode=e.charCode? e.charCode : e.keyCode;
   if(unicode==13)
    {
      try{t.blur();}catch(e){}
      return true;
    
    }
   if(unicode==46)
   {
	   var value1 = t.value;
	   var value2 = value1.split(".");
	   if(value2.length>1)
	   return false;
   }
    if (unicode!=8 && unicode !=9  && unicode!=46)
    {
        if (unicode<48||unicode>57 ) 
            return false; 
    }
}
function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
   
   if ( unicode!=8 && unicode !=9  )
    {
        if ((unicode<48||unicode>57 ) && (unicode<35||unicode>40 ) && unicode!=46 )
            return false ;
    }
 }
function btncancel()
{

 self.close();
}

function get_array_Values() {
	alert("get_array_Values function called");
	var vInvoiceNo = document.getElementsByName("hdnInvoiceNo");

	for (j = 0; j < vInvoiceNo.length; j++) {
		alert(vInvoiceNo[j].value);
	}

	var url = '../../../../../chidambaram_test?' + 'command=test' + '&txtInvoiceNo=' + vInvoiceNo;

	// alert(url);

	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		get_array_ValuesRes(req);
	}
	req.send(null);

}
function get_array_ValuesRes(req)
{
	
}
function getBillNo()
{
	//alert('function called');

	
	var url = '../../../../../phone_bill_servlet?' + 'command=getBillNo';

	// alert(url);

	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		getBillNoRes(req);
	}
	req.send(null);
	
}
function getBillNoRes(req) 
{
	if (req.readyState == 4) 
	{
		if (req.status == 200) 
		{
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var tagcommand = baseResponse.getElementsByTagName("command")[0];
			// alert("command : "+ tagcommand);
			var Command = tagcommand.firstChild.nodeValue;

			if (Command == "getBillNo") 
			{
				var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

				//	 alert('flag value :' + flag);
				if (flag == "success") 
				{
					var billNo = baseResponse.getElementsByTagName("BillNo")[0].firstChild.nodeValue;
					setBillNo(billNo);
				}
				else 
				{
					alert('error in get bill no');
				}
			
			}

		}
	}

}
function setBillNo(billNo)
{
	 alert(billNo);
	var txtBillNo = document.getElementById("txtBillNo");
	//var txtDetBillNumber = document.getElementById("txtDetBillNo");
	txtBillNo.value = parseInt(billNo);
	//txtDetBillNumber.value = parseInt(billNo);
	alert('Bill No: ' + txtBillNo.value);
}


