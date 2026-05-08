
/** Global Declaration */
 var item_slcode=new Array();
 var item_amt=new Array();
 var item_ename=new Array();
 var Disp_AMT = new Array();


 
/** Get Browser Object */
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

function financialYear()
{
   // alert("financialYear");
    var mont=document.getElementById("txtCB_Month").value;
    if(mont==4)
    {
   // alert("444");
    var yea=document.getElementById("txtCB_Year").value;
    document.getElementById("fin_year1").value=mont+"/"+yea;
    }
    else if(mont>4)
    {
     var yea=document.getElementById("txtCB_Year").value;
    document.getElementById("fin_year1").value="4/"+yea;
    }
    else if(mont==1 || mont==2 || mont==3)
    {
     var yea=document.getElementById("txtCB_Year").value;
     var year1_one=yea-1;
    document.getElementById("fin_year1").value="4/"+year1_one;
    }
    doFunction_ImpReceipt('LoadPayVoucherNo');
}

function checkTtlAmt()
{
        if(document.frm_Imp_Refund_Receipt.finalPayment[0].checked==true)
        {
        if(document.frm_Imp_Refund_Receipt.cmbMas_SL_Code.value=="")
        {
                alert("Enter SubLedger Code");
                document.frm_Imp_Refund_Receipt.finalPayment[0].checked=false;
                document.frm_Imp_Refund_Receipt.finalPayment[1].checked=false;
                return false;
        }
           var  cmbAcc_UnitCode  = document.getElementById("cmbAcc_UnitCode").value;
           var  cmbOffice_code   = document.getElementById("cmbOffice_code").value; 
           var yr=document.frm_Imp_Refund_Receipt.txtCB_Year.value;
           var mon=document.frm_Imp_Refund_Receipt.txtCB_Month.value;
           var type1=document.frm_Imp_Refund_Receipt.cmbAdvance_type.value;
           var cmbSL_Code=document.frm_Imp_Refund_Receipt.cmbMas_SL_Code.value;
           
             var url="../../../../../Imprest_Refund_Receipt_Create.kv?Command=finalPay&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
                        "&txtCB_Year="+yr+"&txtCB_Month="+mon+"&txtMode_of_creat="+type1+"&cmbSL_Code="+cmbSL_Code;            
            //  alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse_ImpReceipt(req);
                }   
                   req.send(null);
       
       
        }
        else
        {
       //  alert("noooooo");
         doFunction_ImpReceipt('LoadPayVoucherDetails');
        }
}

/** Main Function for Loading Voucher Number and its Corresponding Details */
function doFunction_ImpReceipt(Command)
{   
   var  cmbAcc_UnitCode  = document.getElementById("cmbAcc_UnitCode").value;
   var  cmbOffice_code   = document.getElementById("cmbOffice_code").value;
   var  txtCB_Year       = document.getElementById("txtCB_Year").value;   
   var  txtCB_Month      = document.getElementById("txtCB_Month").value;
   var  cmbAdvance_type = document.getElementById("cmbAdvance_type").value;
 
        if(txtCB_Month=="")
        {
                alert("Select Month");
                return false;
        }
        if(txtCB_Year=="")
        {
                alert("Enter Year");
                return false;
        }
        var fin_year1=document.getElementById("fin_year1").value;
        var f1=fin_year1.split("/");  
        if(Command=="LoadPayVoucherNo")
        {
                var url="../../../../../Imprest_Refund_Receipt_Create.kv?Command=LoadPayVoucherNo&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
                        "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtMode_of_creat="+cmbAdvance_type+"&fyear1="+f1[1]+"&fmonth="+f1[0];            
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse_ImpReceipt(req);
                }   
                   req.send(null);

        }     
        if(Command=="LoadPayVoucherDetails")
        {
        	 //   var cmbPayVocNo =  document.getElementById("cmbPayVocNo").value;
        	    var cmbPayVocNo= document.getElementById("cmbPayVocNo").options[document.getElementById("cmbPayVocNo").selectedIndex].text;
        	    
                var url="../../../../../Imprest_Refund_Receipt_Create.kv?Command=LoadPayVoucherDetails&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
                        "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbPayVocNo="+cmbPayVocNo+"&txtMode_of_creat="+cmbAdvance_type+"&fyear1="+f1[1]+"&fmonth="+f1[0]; 
                
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse_ImpReceipt(req);
                }   
                   req.send(null);

        }     
}




/** Response Handle Block */
function handleResponse_ImpReceipt(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;           
           
            if(Command=="LoadPayVoucherNo")
            {
            	LoadPayVoucherNo(baseResponse);
            }    
            else if(Command=="LoadPayVoucherDetails")
            {
            	LoadPayVoucherDetails(baseResponse);
            }
            else if(Command=="finalPay")
            {
            	finalPayDetails(baseResponse);
            }      
        }
    }
}

function finalPayDetails(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;  
  // alert("flag"+flag);
    if(flag=="Success")
    {
     document.getElementById("txtAmount").value="";
     var ttlAmt=baseResponse.getElementsByTagName("ttl")[0].firstChild.nodeValue;  
     document.getElementById("txtAmount").value=ttlAmt;
     document.getElementById("txtsub_Amount").value=ttlAmt;
   
    }
}

/** Load voucher Number */
function LoadPayVoucherNo(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;  
    var cmbPayVocNo=document.getElementById("cmbPayVocNo");
   
    if(flag=="Success")
    {
    	  var items_id=new Array();
          var voc_no=baseResponse.getElementsByTagName("voucher_no");
          document.forms[0].cmbPayVocNo.length=0;
         
          var sl_code=baseResponse.getElementsByTagName("sl_code");
          
          cmbPayVocNo.innerHTML="";
          var option=document.createElement("OPTION");
          option.text="--Select Voucher Number--";
          option.value="";
          try
          {
        	  cmbPayVocNo.add(option);
          }catch(errorObject)
          {
        	  cmbPayVocNo.add(option,null);
          }
          
          
          for(var i=0; i<voc_no.length; i++)
          {
        	  
//                  if(voc_no.length==1)
//                         {
//                              var opt1 = document.createElement('option');
//                               opt1.value = 0;
//                               opt1.innerHTML ="select Voucher"; 
//                               cmbPayVocNo.appendChild(opt1);
//                                 
//                         }
                  var opt1 = document.createElement('option');
                  opt1.value = voc_no[i].firstChild.nodeValue;
                  opt1.innerHTML = voc_no[i].firstChild.nodeValue+"-"+sl_code[i].firstChild.nodeValue; 
                  cmbPayVocNo.appendChild(opt1);
          }
    }
    else if(flag=="NoRecords")
    {
    	cmbPayVocNo.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select Voucher Number--";
        option.value="";
        try
        {
        	cmbPayVocNo.add(option);
        }catch(errorObject)
        {
        	cmbPayVocNo.add(option,null);
        }        
    	alert("No Voucher Found")
    }
    else if(flag=="Failure")
    {
    	alert("Error Loading Voucher Number")
    }		 
}




/** Load voucher Details */
function LoadPayVoucherDetails(baseResponse)
{
	    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;  
	   
         
	    if(flag=="Success")
	    {
	    	var pay_date = baseResponse.getElementsByTagName("pay_date")[0].firstChild.nodeValue;
	    	
	        var tot_amt=baseResponse.getElementsByTagName("tot_amt")[0].firstChild.nodeValue;
	        var paid_to=baseResponse.getElementsByTagName("paid_to")[0].firstChild.nodeValue;
	        var bal_amt=baseResponse.getElementsByTagName("detail_amt")[0].firstChild.nodeValue;
	       
	        document.getElementById("txtPaymentVoc_Date").value=pay_date;
	        document.getElementById("txtAmtPaid").value=tot_amt;
	        document.getElementById("txtRecei_from").value=paid_to;
	        document.getElementById("txtAmount").value=bal_amt;
	        document.getElementById("txtsub_Amount").value=bal_amt;
	        
	        
	        var payment_details=baseResponse.getElementsByTagName("payment_details");
	        
	        /** Get SL Type Code Combo box Object */ 
	        var cmbSL_type=document.getElementById("cmbSL_type");
	        
	        /** SL Type Combo Clear */
	        cmbSL_type.innerHTML="";
	        var option=document.createElement("OPTION");
	        option.text="Employees";
	        option.value="7";
	        try
	        {
	        	cmbSL_type.add(option);
	        }catch(errorObject)
	        {
	        	cmbSL_type.add(option,null);
	        }
	        
	        /** Get Received from Combo box Object */ 
	        var txtRecei_from=document.getElementById("txtRecei_from");
	  
	        /** SL Type Combo Clear */
	        txtRecei_from.innerHTML="";
	        var option=document.createElement("OPTION");
	        option.text="-- Select Employee --";
	        option.value="";
	        try
	        {
	        	txtRecei_from.add(option);
	        }catch(errorObject)
	        {
	        	txtRecei_from.add(option,null);
	        }
	        
	        /** Get SL Code Combo box Object */ 
	        var cmbSL_Code=document.getElementById("cmbSL_Code");
	        
	        /** SL Type Combo Clear */
	        cmbSL_Code.innerHTML="";
	        var option=document.createElement("OPTION");
	        option.text="-- Select Sub Ledger Code --";
	        option.value="";
	        try
	        {
	        	cmbSL_Code.add(option);
	        }catch(errorObject)
	        {
	        	cmbSL_Code.add(option,null);
	        }
	        
	        var t;
	        
	        for(var k=0;k<payment_details.length;k++)
	        {
	        	item_slcode[k]=baseResponse.getElementsByTagName("sl_code")[k].firstChild.nodeValue;
	        	item_amt[k]=baseResponse.getElementsByTagName("detail_amt")[k].firstChild.nodeValue;
	        	item_ename[k]=baseResponse.getElementsByTagName("ename")[k].firstChild.nodeValue;	            	
	            
	        	t = item_slcode[k];
	        	Disp_AMT[t] = item_amt[k];
	        	
	        	
	        	/** SL Code */
	            var option=document.createElement("OPTION");
                option.text=item_ename[k];
                option.value=item_slcode[k];
                try
                {
                	cmbSL_Code.add(option);
                }
                catch(errorObject)
                {
                	cmbSL_Code.add(option,null);
                }
                
                
                /** Received From */
                var option=document.createElement("OPTION");
                option.text=item_ename[k];
                option.value=item_slcode[k];
               
                try
                {
                	txtRecei_from.add(option);
                }
                catch(errorObject)
                {
                	txtRecei_from.add(option,null);
                }
                                
	        }
	        
	        /** Enable ShowPaymenyDetials Button **/ 
		    document.getElementById("PaymentDetails").style.display="block";
		    
	    }
	    else
	    { 	
	       /** Disable ShowPaymenyDetials Button **/ 
	       document.getElementById("PaymentDetails").style.display="none";
	    }   
	    
}



/** Payment Amt - Journal Amt = Receipt Amt */ 
function Receipt_Amt_Check(curr_amt)
{
   var temp_amt;
   var sl_code= document.getElementById("cmbSL_Code").value;   
   
   for (var i=0 ;i<item_slcode.length;i++ ) 
   {   
       if ( item_slcode[i] == sl_code ) 
       {
         temp_amt = item_amt[i];
       }
   }      
   
   if ( parseInt(curr_amt) > parseInt(temp_amt) ) 
   {
      alert("Amount Should be less than or equal to "+temp_amt);
      document.getElementById("txtsub_Amount").value="";
   }
   
}




/** Display Amount */ //display amt changed on 22.9.2010


/* function Dip_AMT(sl_code)
{alert("amt"+Disp_AMT[sl_code]);
	document.getElementById("txtsub_Amount").value=Disp_AMT[sl_code];
}
*/





function grid_ImpReceipt(Cash_r_Bank)
{
   
   var grid_obj_Gen;
   var grid_obj_Det;
   var grid_ImpReceipt_Torch;
   
   grid_obj_Gen = document.getElementById("grid_ImpReceipt_Gen");   
   grid_obj_Det = document.getElementById("grid_ImpReceipt_Det");
   
   if(Cash_r_Bank == 'CR')
   {	    
        grid_obj_Gen.style.display="none";
	    grid_obj_Det.style.display="none";
	    document.getElementById("grid_ImpReceipt_Torch_Cash").style.display="block";
	    document.getElementById("grid_ImpReceipt_Torch_Bank").style.display="none";
	    
	    /** Load Cash Account Details */
	    document.getElementById("txtCash_Acc_code_cash").value="820101";
	    
   }
   else if ( Cash_r_Bank == 'BR')
   {
        grid_obj_Gen.style.display="block";	   
        grid_obj_Det.style.display="block";
        document.getElementById("grid_ImpReceipt_Torch_Cash").style.display="none";
        document.getElementById("grid_ImpReceipt_Torch_Bank").style.display="block";
        document.getElementById("txtCash_Acc_code_cash").value="";
      
        /** Load Bank Account Details */
        LoadOprAccountHead("DR","MF031","COL");
        
   }   
}




var Voucher_list_SL;

function ShowPaymentDetails()
{
    var  cmbAcc_UnitCode  = document.getElementById("cmbAcc_UnitCode").value;
	var  cmbOffice_code   = document.getElementById("cmbOffice_code").value;
	var  txtCB_Year       = document.getElementById("txtCB_Year").value;   
	var  txtCB_Month      = document.getElementById("txtCB_Month").value;
	var  cmbPayVocNo       = document.getElementById("cmbPayVocNo").value;  
	
    if (Voucher_list_SL && Voucher_list_SL.open && !Voucher_list_SL.closed) 
    {
       Voucher_list_SL.resizeTo(500,500);
       Voucher_list_SL.moveTo(250,250); 
       Voucher_list_SL.focus();
    }
    else
    {
        Voucher_list_SL=null
    }    
    
    Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/Imprest/jsps/Imprest_Account_ListAll_SL.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&yr="+txtCB_Year+"&mon="+txtCB_Month+"&recNo="+cmbPayVocNo,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    Voucher_list_SL.moveTo(250,250);  
    Voucher_list_SL.focus();
    
}





function checkNull()
{

	/** Payment Voucher Number */
	if(document.getElementById("cmbPayVocNo").value=="")
    {
        alert("Select Payment Voucher Number");       
        return false;    
    }

	/** Accounting Unit ID */
	if(document.getElementById("cmbAcc_UnitCode").value=="")
    {
        alert("Select Account Unit code");       
        return false;    
    }
	
	/** Accounting Office ID */
    if(document.getElementById("cmbOffice_code").value=="")
    {
        alert("Select Office Code");
        return false;
    }  	
    
    /** Receipt Date */
    if(document.getElementById("txtCrea_date").value.length==0)
    {
        alert("Enter Receipt Date");        
        return false;    
    }
    
    
    
    /** Debit Account Head code */    
    if ( document.frm_Imp_Refund_Receipt.txtAmtType[0].checked == true )
    {   
	    if(document.getElementById("txtCash_Acc_code_cash").value.length==0)
	    {
	        alert("Enter Debit A/c Code");     
	        return false;
	    }
    }
    else
    {
    	if(document.getElementById("txtCash_Acc_code").value.length==0)
 	    {
 	        alert("Enter Debit A/c Code");     
 	        return false;
 	    }
    }
    
    
    
    /** Sub Ledger Type Code - Master */
   /* if(document.getElementById("cmbMas_SL_Code").value=="")
    {
        alert("Select Sub Ledger Type Code in General");
        return false;
    }
   */ 
    
    /** Sub Ledger Code - Master */
   /* if(document.getElementById("cmbMas_SL_Code").value=="")
    {
       alert("Select Sub Ledger Code in General");
       return false;
    }
   */ 
    
    
    /** Amount Received From */
    if(document.getElementById("txtRecei_from").value=="")
    {
       alert("Select ' Received From Field ' in General part");
       return false;
    }
    
    
    /** Total Amount In General */
    if(document.getElementById("txtAmount").value.length==0)
    {
        alert("Enter Total Amount in General");       
        return false;    
    }
    
    //--------------------Detail Part -----------------//
    
    
    /** Account Head Code */
    if(document.getElementById("txtAcc_HeadCode").value.length==0)
    {
        alert("Enter Account Head Code in Detail");       
        return false;    
    }
    
    /** Account Head Code Description */
    if(document.getElementById("txtAcc_HeadCode").value.length==0)
    {
        alert("Enter Account Head Code in Detail");       
        return false;    
    }
    
    /** Sub Ledger Type Code - Detail */
    if(document.getElementById("cmbSL_type").value=="")
    {
        alert("Select Sub Ledger Type Code in Detail");
        return false;
    }    
    
    /** Sub Ledger Code - Detail */
    if(document.getElementById("cmbSL_Code").value=="")
    {
       alert("Select Sub Ledger Code in Detail");
       return false;
    }
    
    /** Amount */
    if(document.getElementById("txtsub_Amount").value.length==0)
    {
        alert("Enter Amount in Detail");       
        return false;    
    }
    
   
   /** If bank option is selected, check the follwing conditions */ 
   if (document.frm_Imp_Refund_Receipt.txtAmtType[1].checked==true)
   {
    
        /** if ecs option is selected , check the following condition */ 
        if(document.frm_Imp_Refund_Receipt.txtCheque_DD[2].checked==false)
        {      
            /* cheque / dd Number */
            if(document.getElementById("txtCheque_DD_NO").value.length==0)
            {
                alert("Enter the Cheque/DD number");             
                return false;
            }
            
            /* cheque /  dd date */
            if(document.getElementById("txtCheque_DD_date").value.length==0)
            {
                alert("Enter the Cheque/DD Date");            
                return false;
            }
        }       
            
        /* Bank name */    
        if(document.getElementById("txtBank_Name").value.length==0)
        {
             alert("Enter the Bank Name");
             document.getElementById("txtBank_Name").focus();
             return false;
        }
        
        /* Drawee branch */
        if(document.getElementById("txtDraw_BR").value.length==0)
        {
            alert("Enter the Drawee Branch");
            document.getElementById("txtDraw_BR").focus();
            return false;
        }
  }       
    
    
    //--------------  Additional Conditions ---------------------//
    if ( document.getElementById("txtAmount").value != document.getElementById("txtsub_Amount").value  )
    { 	
      alert("Amount in General and Detail not Tally");
      return false;
    }
    
    if(document.getElementById("cmbMas_SL_type").value !="")
    {
        if(document.getElementById("cmbMas_SL_Code").value!=document.getElementById("cmbSL_Code").value)
        {
            alert("Sub Ledger Code in General and Detail should be same");
            return false;
        }
    }
    
    if(document.getElementById("txtRecei_from").value!=document.getElementById("cmbSL_Code").value)
    {
        alert("Received From in General and Sub ledger code in Detail should be same");
        return false;
    }
    
     /** Check Payment Data and Receipt Date 
      * fromdt = Payment Date  
      * todt   = Receipt Date
      */
    
     var fromdt=document.getElementById("txtPaymentVoc_Date").value;
     var todt=document.getElementById("txtCrea_date").value;
            
     var frm=fromdt.split('/');
     var to=todt.split('/');
            
     var fday=frm[0];
     var fmon=frm[1];
     var fyear=frm[2];
            
     var tday=to[0];
     var tmon=to[1];
     var tyear=to[2];
            
           if(fyear>tyear)
            {
                alert('Receipt Date should be greater than Payment Date');              
                return false;
            }
            else if(fyear==tyear)
            {
                    if(fmon>tmon)
                    {
                    	alert('Receipt Date should be greater than Payment Date');                       
                        return false;
                    }
                    else if(fmon==tmon)
                    {
                            if(fday>tday)
                            {
                            	alert('Receipt Date should be greater than Payment Date');                            
                                 return false;
                            }                            
                    }                  
            }
            
            
            
           
  return true; 
        
}








// Fteching Account Head No and Bank  No
var winAcc_Bank_No;    
function MainAccNopopup()
{
	    Bank_popup_flag=true;
	    if (winAcc_Bank_No && winAcc_Bank_No.open && !winAcc_Bank_No.closed) 
	    {
			winAcc_Bank_No.resizeTo(500,500);
			winAcc_Bank_No.moveTo(250,250); 
			winAcc_Bank_No.focus();
	    }
	    else
	    {
	       winAcc_Bank_No=null;
	    }
	    var txtModule_Type="MF031";
	    var cr_dr_indi="DR";
	    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	     
	    winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
	    winAcc_Bank_No.moveTo(250,250);  
	    winAcc_Bank_No.focus();
}






function doParentAcc_NO(Acc_Head_Code,Bank_Acc_No,bankid,br_id,B_name)
{
        document.getElementById("txtCash_Acc_code").value=Acc_Head_Code;
        document.getElementById("txtBankAccountNo").value=Bank_Acc_No;
        document.getElementById("txtBankName").value=B_name;        
}






function clrForm()
{
    /* cashbook Month */
    document.getElementById("txtCB_Month").value="";
    /* Voucher number */
    document.getElementById("cmbPayVocNo").value="";
    /* payment Date */
    document.getElementById("txtPaymentVoc_Date").value="";        
    /* amount Paid */
    document.getElementById("txtAmtPaid").value="";        
    /* sub ledger type */
    document.getElementById("cmbMas_SL_type").value="";        
    /* sub Ledger code */
    document.getElementById("cmbMas_SL_Code").value="";        
    /* remarks */
    document.getElementById("txtRemarks").value="";        
    /* received from */
    document.getElementById("txtRecei_from").value="";   
    /* amount in gen */
    document.getElementById("txtAmount").value="";    
    
    /* ---- Detail --- */
    
    /* account head code */
    document.getElementById("txtAcc_HeadCode").value="";        
    /* account Head code desc */
    document.getElementById("txtAcc_HeadDesc").value="";      
    /* sub ledger type */
    document.getElementById("cmbSL_type").value="";        
    /* sub ledger code */
    document.getElementById("cmbSL_Code").value="";      
    /* part amount */
    document.getElementById("txtsub_Amount").value="";  
    /* particulars */
    document.getElementById("txtParticular").value="";  
  
    /** Disable ShowPaymenyDetials Button **/ 
    document.getElementById("PaymentDetails").style.display="none";
 
    /** Load Default Account Head code */
    document.getElementById("txtAcc_HeadCode").value="820103";             
    doFunction('checkCode','null');


}







function call_mainJSP_script(fromcal_dateCtrl,blr_flag)    
{
    var dtval=datefun();
    
    if(dtval==true)
    {
            if(blr_flag==1)             // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
            {
        
                     var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
                     var cmbOffice_code=document.getElementById("cmbOffice_code").value;
                     var TB_date=fromcal_dateCtrl.value;
          
                     if(fromcal_dateCtrl.value.length!=0)
                     {
                         var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
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
}




function call_date(dateCtrl)                   
{
    if(checkdt(dateCtrl))
    {
        
         var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
         var cmbOffice_code=document.getElementById("cmbOffice_code").value;
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
    }
    else
    {
       document.getElementById("txtCrea_date").value="";
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
              
             }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");                   

                 /* var cmbSL_Code=document.getElementById("cmbPayVocNo");   
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
                    } */
                    
                    document.getElementById("txtCrea_date").value="";
                    
               }
            dateCheck(dateCtrl);  
        }
    }
}

function valid_amt(amt)
{
document.getElementById("txtsub_Amount").value=amt;
}


function checkWithPayment()
{
         var  Receipt_Amt = document.getElementById("txtAmount").value;
         var  Paid_Amt = document.getElementById("txtAmtPaid").value;
         if(parseInt(Receipt_Amt)>parseInt(Paid_Amt))
         {
                alert("Receipt amount should not be greater than Payment amount");
                document.getElementById("txtAmount").value="";
                document.getElementById("txtAmount").focus();
         }
         document.getElementById("txtsub_Amount").value=Receipt_Amt;
}

/*function callDetailAmt()
{
	
	 var  Receipt_Amt = document.getElementById("txtAmount").value;
	 document.getElementById("txtsub_Amount").value=Receipt_Amt;
}
*/

function datefun()
{
        var fromdt=document.frm_Imp_Refund_Receipt.txtPaymentVoc_Date.value;
        var todt=document.frm_Imp_Refund_Receipt.txtCrea_date.value;
        
        var frm=fromdt.split('/');
        var to=todt.split('/');
        
        var fday=frm[0];
        var fmon=frm[1];
        var fyear=frm[2];
        
        var tday=to[0];
        var tmon=to[1];
        var tyear=to[2];
        
        if(fyear>tyear)
        {
            alert('Receipt Date should be greater than Payment Date');
            document.frm_Imp_Refund_Receipt.txtCrea_date.value="";
            document.frm_Imp_Refund_Receipt.txtCrea_date.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('Receipt Date should be greater than Payment Date');
                    document.frm_Imp_Refund_Receipt.txtCrea_date.value="";
                    document.frm_Imp_Refund_Receipt.txtCrea_date.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('Receipt Date should be greater than Payment Date');
                             document.frm_Imp_Refund_Receipt.txtCrea_date.value="";
                             document.frm_Imp_Refund_Receipt.txtCrea_date.focus();
                             return false;
                        }
                        
                }
               
        }
        else       
            return true;        
}


function loadReceivedFrom()
{
       document.getElementById("txtRecei_from").value=document.getElementById("txtEmpID_mas").value;
}

/////////////////////////////////////////////Load Account Head Based on the Journal Type Selection /////////////////////

function loadAccountHead()
{		 
		 var cmbAdvance_type=document.getElementById("cmbAdvance_type").value;		
		 
		 if(cmbAdvance_type=='I1')		
                       document.getElementById("txtAcc_HeadCode").value=820103;	
		 else if(cmbAdvance_type=='I2')		
             	document.getElementById("txtAcc_HeadCode").value=820103;	
		 else if(cmbAdvance_type=='T1')		
          	document.getElementById("txtAcc_HeadCode").value=820102;
		 else if(cmbAdvance_type=='T2')		
	          	document.getElementById("txtAcc_HeadCode").value=820102;		 
		 doFunction('checkCode','null');
			 
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////