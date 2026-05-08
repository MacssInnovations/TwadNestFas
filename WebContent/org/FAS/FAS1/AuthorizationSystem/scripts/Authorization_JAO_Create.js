
/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////
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

function call_adj_memo()
{
	 var cmbSubSystemType=document.getElementById("cmbSubSystemType").value;
	 if(cmbSubSystemType=="ADM")
	 {
		 doFunction('load_Voucher_No','null');
	 }
	 else
	 {
		 var txtCrea_date=document.getElementById("txtCrea_date");
		 call_date(txtCrea_date);
	 }
}

function doFunction(Command,param)
{   
 
      if(Command=="load_Voucher_No")
        {
        
        
        var sub_type;
            cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            cmbOffice_code=document.getElementById("cmbOffice_code").value;
            cmbSubSystemType=document.getElementById("cmbSubSystemType").value;
            if(cmbSubSystemType=="CHQD")
            {
           
            document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled="true";
            }
            if(cmbSubSystemType=="CHQC")
            {
            	 document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled="true";
            	 document.frmAuthorization_JAO_Create.radAuth_MC[1].checked="true";
            }
            if(cmbSubSystemType=="TPAOC"|| cmbSubSystemType=="TPAOD" ||cmbSubSystemType=="TPAAC" ||cmbSubSystemType=="TPAAD"
            	||cmbSubSystemType=="TPAOCV"|| cmbSubSystemType=="TPAODV"||cmbSubSystemType=="TPAOCA"|| cmbSubSystemType=="TPAODA")
            {
           	 document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled="true";
           	document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled="false";
            }
            if(cmbSubSystemType=="ADM" || cmbSubSystemType=="ADM_A")
            {
            	document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled="true";
           	 document.frmAuthorization_JAO_Create.radAuth_MC[1].checked="true";
           	}
            
            
            txtCrea_date=document.getElementById("txtCrea_date").value;
            if(cmbSubSystemType=="")
            {
                alert("Select Sub-system type ");
                return false;
            }
            if( txtCrea_date.length==0)
            {
                alert("Enter the date");
                return false;
            }
                        
            /** Hide modify option when selecting Remittance Cancel */             
            HIDE_modify();
            
            
            
            
            if(cmbSubSystemType!="" && txtCrea_date.length!=0)
            {
                var url="../../../../../Authorization_JAO_Create.view?Command=load_Voucher_No&txtCrea_date="+txtCrea_date+"&cmbSubSystemType="+
                cmbSubSystemType+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
            }
        }
        else if(Command=="load_Voucher_details")
        {
             cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            cmbOffice_code=document.getElementById("cmbOffice_code").value;
            cmbSubSystemType=document.getElementById("cmbSubSystemType").value;
            txtCrea_date=document.getElementById("txtCrea_date").value;
            txtVoucher_No=document.getElementById("txtVoucher_No").value;
            if(cmbSubSystemType=="")
            {
                alert("Select Sub-system type ");
                return false;
            }
            if( txtCrea_date.length==0)
            {
                alert("Enter the date");
                return false;
            }
             if(cmbSubSystemType=="CHQD")
            {
           
            document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled="true";
            // document.getElementById("H_Modify").style.visibility="hidden";
            }
            
             if(cmbSubSystemType=="TPAOC"|| cmbSubSystemType=="TPAOD" ||cmbSubSystemType=="TPAAC" ||cmbSubSystemType=="TPAAD")
             {
            	 document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled="true";
             }
             if(cmbSubSystemType=="CHQC")
             {
            	
             	 document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled="true";
             	 document.frmAuthorization_JAO_Create.radAuth_MC[1].checked="true";
             }
             
             if(cmbSubSystemType=="ADM" || cmbSubSystemType=="ADM_A")
             {
             	document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled="true";
            	 document.frmAuthorization_JAO_Create.radAuth_MC[1].checked="true";
            	}
             
            if(cmbSubSystemType!="" && txtCrea_date.length!=0 && txtVoucher_No!="")
            {
                var url="../../../../../Authorization_JAO_Create.view?Command=load_Voucher_details&txtCrea_date="+txtCrea_date+"&cmbSubSystemType="+
                cmbSubSystemType+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtVoucher_No="+txtVoucher_No;
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
            }
        }
        
        
}
function handleResponse(req)
{  
 
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             var tagcommand=baseResponse.getElementsByTagName("command")[0];
             var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="load_Voucher_No")
            {
                load_Voucher_No(baseResponse);
            }
            else if(Command=="load_Voucher_details")
            {
                load_Voucher_details(baseResponse);
            }
        
        }
    }
}



function load_Voucher_details(baseResponse)
{
     var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     //var autos=baseResponse.getElementsByTagName("autos")[0].firstChild.nodeValue;
     
     if(baseResponse.getElementsByTagName("autos")[0] == undefined){
		 var autos = "";
	 }else{
		 var autos=baseResponse.getElementsByTagName("autos")[0].firstChild.nodeValue;
	 }
	 
     
     if(autos=="yes"){
      if(flag=="success")
      {
         document.getElementById("com_value").value=baseResponse.getElementsByTagName("com_value")[0].firstChild.nodeValue;
         document.getElementById("amt").value=baseResponse.getElementsByTagName("amt")[0].firstChild.nodeValue;
         document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=true;
         alert("only Modify option is allowed");
      }
      else
      {
        document.getElementById("com_value").value="";  
        document.getElementById("amt").value="";  
        alert("Details not found");
      }
      }
      else
      {
          if(flag=="success")
          {
             document.getElementById("com_value").value=baseResponse.getElementsByTagName("com_value")[0].firstChild.nodeValue;
             document.getElementById("amt").value=baseResponse.getElementsByTagName("amt")[0].firstChild.nodeValue;
            var cmbSubSystemType1=document.getElementById("cmbSubSystemType").value;
            alert(cmbSubSystemType1);
            if (cmbSubSystemType1 == "CR") {
            	
            	document.getElementById("modifyauthID").style.display = 'block';
				document.getElementById("cancelauthID").style.display = 'block';
				document.getElementById("com_value").value = baseResponse
						.getElementsByTagName("com_value")[0].firstChild.nodeValue;
				document.getElementById("amt").value = baseResponse
						.getElementsByTagName("amt")[0].firstChild.nodeValue;
            	
            	var edit=baseResponse. getElementsByTagName("Edit")[0].firstChild.nodeValue;
            	alert("Edit****"+edit);
				if (edit=='true') {
					alert("Inside Edit True****");					
//					document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled=false;
//					document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=true;
//					document.frmAuthorization_JAO_Create.radAuth_MC[0].checked=true;
					document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled=false;
					// changed [28_11_2023]
					document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=true;
					//
					document.frmAuthorization_JAO_Create.radAuth_MC[0].checked=true;
					
				} else {
					alert("Inside Edit False****");
					document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled=false;
			        document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=false;
			        document.frmAuthorization_JAO_Create.radAuth_MC[0].checked=true;
					
				}
            }
            
            else if(cmbSubSystemType1=="CHQD")
            {
           
            document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled="true";
            document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=false;
            document.frmAuthorization_JAO_Create.radAuth_MC[1].checked=true;
            }
            else if(cmbSubSystemType=="CHQC")
            {
            	 document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled="true";
                 document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=false;
                 document.frmAuthorization_JAO_Create.radAuth_MC[1].checked=true;
            }
            else if(cmbSubSystemType=="ADM" || cmbSubSystemType=="ADM_A")
             {
            	document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled="true";
                document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=false;
                document.frmAuthorization_JAO_Create.radAuth_MC[1].checked=true;
             }
            else if(cmbSubSystemType1=="BPP")
            {
            	
            	document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled="true";
                document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=false;
                document.frmAuthorization_JAO_Create.radAuth_MC[1].checked=true;
            }
            else  if(cmbSubSystemType=="TPAOC"|| cmbSubSystemType=="TPAOD" ||cmbSubSystemType=="TPAAC" ||cmbSubSystemType=="TPAAD")
            {
           	 document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled="true";
           	 document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=false;
             document.frmAuthorization_JAO_Create.radAuth_MC[1].checked=true;
            }
            else{
             document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=false;
             document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled=false;
             }
          }
          else
          {
            document.getElementById("com_value").value="";  
            document.getElementById("amt").value="";  
            alert("Details not found");
          }
      }
    
}


////////////////////////////////////////////// load Voucher Number           /////////////////////

function load_Voucher_No(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
 var txtVoucher_No=document.getElementById("txtVoucher_No");
  if(flag=="success")
    {
           var items_id=new Array();
           var Rec_No=baseResponse.getElementsByTagName("Rec_No");
            
            for(var k=0;k<Rec_No.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("Rec_No")[k].firstChild.nodeValue;
                
            }
         
            txtVoucher_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Voucher Number--";
            option.value="";
            try
            {
                txtVoucher_No.add(option);
            }catch(errorObject)
            {
                txtVoucher_No.add(option,null);
            }
            
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_id[k];
                  option.value=items_id[k];
                   try
                  {
                      txtVoucher_No.add(option);
                  }
                  catch(errorObject)
                  {
                      txtVoucher_No.add(option,null);
                  }
            }
    }
    else if(flag=="failure")
    {
            txtVoucher_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Voucher Number--";
            option.value="";
            try
            {
                txtVoucher_No.add(option);
            }catch(errorObject)
            {
                txtVoucher_No.add(option,null);
            }
         alert("No Voucher Found");
    }
}



function checkNull()
{
    
    if(document.getElementById("cmbAcc_UnitCode").value=="")
    {
        alert("Select the Account Unit code");
        document.getElementById("txtAcc_HeadDesc").focus();
        return false;    
    }
    if(document.getElementById("cmbOffice_code").value=="")
    {
        alert("Select the Office Code");
        document.getElementById("cmbOffice_code").focus();
        return false;
    }
    if(document.getElementById("txtCrea_date").value.length==0)
    {
        alert("Enter the  Date");
        document.getElementById("txtCrea_date").focus();
        return false;    
    }
    
      if(document.getElementById("cmbSubSystemType").value=="")
    {
        alert("Select the Sub-System type");
        document.getElementById("cmbSubSystemType").focus();
        return false;    
    }
    if(document.getElementById("txtVoucher_No").value=="")
    {
        alert("Select the Voucher Number");
        document.getElementById("txtVoucher_No").focus();
        return false;    
    }
    /*
    if(document.getElementById("txtReferNO_edit").value.length==0)
    {
        alert("Enter the Reference Number");
        document.getElementById("txtReferNO_edit").focus();
        return false;    
    }
    if(document.getElementById("txtReferDate_edit").value.length==0)
    {
        alert("Enter the Reference Date ");
        document.getElementById("txtReferDate_edit").focus();
        return false;
    }
    */
     if(document.getElementById("txtRemak_edit").value.length==0)
    {
        alert("Enter the Remarks ");
        document.getElementById("txtRemak_edit").focus();
        return false;
    }

return true;
}




///////////////////////////////////////////    TB_checking and Calender control return value handling

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
    if(blr_flag==1)             // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
    {
             call_clr();
             cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             cmbOffice_code=document.getElementById("cmbOffice_code").value;
             var TB_date=fromcal_dateCtrl.value;
             //alert(fromcal_dateCtrl.value+"b4url")
             if(fromcal_dateCtrl.value.length!=0)
             {
                 var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                        //alert(url);
                 var req=getTransport();
                 req.open("GET",url,true); 
                 req.onreadystatechange=function()
                 {
                   check_TB(req,fromcal_dateCtrl);
                 }   
                 req.send(null);
            }
    }
}




function call_date(dateCtrl)                        // TB_checking 
{
    call_clr();
    if(checkdt(dateCtrl))
    {
        //doFunction('check_TB',dateCtrl.value);
         cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
         cmbOffice_code=document.getElementById("cmbOffice_code").value;
         var TB_date=dateCtrl.value;
       
         if(dateCtrl.value.length!=0)
         {
             var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
             var req=getTransport();
             req.open("GET",url,true); 
             req.onreadystatechange=function()
             {
               check_TB(req,dateCtrl);
             }   
             req.send(null);
       }
        //doFunction('load_Voucher_No','null');
    }
    else
    {
        var cmbSL_Code=document.getElementById("txtVoucher_No");   
        cmbSL_Code.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="-- Select Voucher Number --";
        option.value="";
        try
        {
            cmbSL_Code.add(option);
        }catch(errorObject)
        {
            cmbSL_Code.add(option,null);
        }
    }
}

function dateCheck(datechk)
{
	//alert("WELCOME!.........");
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    //var txtCrea_date=document.getElementById("txtCrea_date").value;
    var txtCrea_date=datechk.value;
    
    if(datechk.value.length!=0)
    {
    var url="../../../../../Receipt_SL.view?Command=check_Date&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date;
    //alert("URL===>"+url);
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
      check_Date(req,datechk);
    } ;  
    req.send(null);
    }

}
function check_Date(req,datechk)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="success")
            {
               //doFunction('load_Receipt_No','null');                 //return true;
          	document.getElementById("butSub").disabled=false;
            }
          else if(flag=="failure")
          {
          	datechk.value=""; 
          	alert("Document Date is Less than DATE_EFFECTIVE_FROM");
          	datechk.focus();
          	document.getElementById("butSub").disabled=true;
          	
          	document.getElementById("txtReceipt_No").value="";
               
          }
          else if(flag=="success1")
          {
             //doFunction('load_Receipt_No','null');                 //return true;
          	document.getElementById("butSub").disabled=false;
          }
         else if(flag=="failure1")
         {
      	  alert("Document Date is Greater than DATE_OF_CLOSURE");
      	  datechk.value=""; 
        		//alert("Document Date is Less than DATE_ALLOWED_UPTO date");
        		datechk.focus();
        		document.getElementById("butSub").disabled=true;
        		document.getElementById("txtReceipt_No").value="";
         }
         else 
      	   {
      	    datechk.value=""; 
      	    alert("Date Value is Null");
         		datechk.focus();
         		document.getElementById("butSub").disabled=true;
         		document.getElementById("txtReceipt_No").value="";
      	   }
        }
    }
}

function call_clr()
{
        //document.getElementById("txtVoucher_No").value="";  
        var cmbSL_Code=document.getElementById("txtVoucher_No");   
        cmbSL_Code.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="-- Select Voucher Number --";
        option.value="";
        try
        {
            cmbSL_Code.add(option);
        }catch(errorObject)
        {
            cmbSL_Code.add(option,null);
        }
        
       // document.getElementById("cmbSubSystemType").value="";  
        document.getElementById("com_value").value="";  
        document.getElementById("amt").value="";  
        document.getElementById("txtReferNO_edit").value="";
        document.getElementById("txtReferDate_edit").value="";
        document.getElementById("txtRemak_edit").value="";
        
}




function check_TB(req,dateCtrl)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
              {
               
                 doFunction('load_Voucher_No','null');
              }
             else if(flag=="failure")
               {
            	 
            	 var cmbSubSystemType=document.getElementById("cmbSubSystemType").value;
            	// alert(cmbSubSystemType);
            	 if(cmbSubSystemType=="ADM")
            	 {
            		 doFunction('load_Voucher_No','null');
            	 }
            	 else{
                    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
                    dateCtrl.focus();
                    var cmbSL_Code=document.getElementById("txtVoucher_No");   
                    cmbSL_Code.innerHTML="";
                    var option=document.createElement("OPTION");
                    option.text="-- Select Voucher Number --";
                    option.value="";
                    try
                    {
                        cmbSL_Code.add(option);
                    }catch(errorObject)
                    {
                        cmbSL_Code.add(option,null);
                    }
            	 }
               }
            
        }
    }
}



function clrForm()
{
        document.getElementById("cmbSubSystemType").value="";  
        var cmbSL_Code=document.getElementById("txtVoucher_No");   
        cmbSL_Code.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="-- Select Voucher Number --";
        option.value="";
        try
        {
            cmbSL_Code.add(option);
        }catch(errorObject)
        {
            cmbSL_Code.add(option,null);
        }
        document.getElementById("com_value").value="";  
        document.getElementById("amt").value="";  
        document.getElementById("txtReferNO_edit").value="";
        document.getElementById("txtReferDate_edit").value="";
        document.getElementById("txtRemak_edit").value="";
}


/**
 *  Hide Modify option when selecting Remittance option 
 */
 
function HIDE_modify()
{
   var Sub_System=document.getElementById("cmbSubSystemType").value;
   if (Sub_System=="CRM" || Sub_System=="BRM" || Sub_System=="FRM" )
    {
      document.getElementById("H_Modify").style.visibility="hidden";
    
    }
    else 
    {
     document.getElementById("H_Modify").style.visibility="visible";
    }
    

        document.getElementById("txtRemak_edit").value="";
}


/**
 *  Hide Modify option when selecting Remittance option 
 */
 
function HIDE_modify()
{
   var Sub_System=document.getElementById("cmbSubSystemType").value;
if (Sub_System=="CRM" || Sub_System=="BRM" || Sub_System=="FRM" )
    {
      document.getElementById("H_Modify").style.visibility="hidden";
    
    }
    else 
    {
     document.getElementById("H_Modify").style.visibility="visible";
    }
    
 }