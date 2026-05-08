/** Global Variable Declaration */
var Max_Amount="";



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


function doFunction_ImpReceipt(Command)
{   
   var  cmbAcc_UnitCode  = document.getElementById("cmbAcc_UnitCode").value;
   var  cmbOffice_code   = document.getElementById("cmbOffice_code").value;
   var  txtCB_Year       = "";   
   var  txtCB_Month      = "";
   var  txtCrea_date     = document.getElementById("txtCrea_date").value;
   var  cmbAdvance_type = document.getElementById("cmbAdvance_type").value;
   
   var  txtAmtType;
   
   if ( document.frm_Imp_Refund_Receipt.txtAmtType[0].checked == true  )
   {  
	   txtAmtType = 'CR';
   }      
   else if ( document.frm_Imp_Refund_Receipt.txtAmtType[1].checked == true  )
   { 
	   txtAmtType = 'BR';
   }
   
        if(Command=="LoadPayEmpMaxAmt")
        {
               /* Get Receipt Number */
               var txtReceipt_No = document.getElementById("txtReceipt_No").value;
               
               /* Get Payment Voucher Number and Date */
               var cmbPayVocNo   = document.getElementById("cmbPayVocNo").value;
               var txtPaymentVoc_Date =  document.getElementById("txtPaymentVoc_Date").value;
               
               /* Get Sub Ledger Type Code and Sub Ledger Code */
               var cmbSL_type =  document.getElementById("cmbSL_type").value;
               var cmbSL_Code =  document.getElementById("cmbSL_Code").value;
               
               
            	var url="../../../../../Imprest_Refund_Receipt_Edit.kv?Command=LoadPayEmpMaxAmt&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
                   "&txtReceipt_No="+txtReceipt_No+"&txtCrea_date="+txtCrea_date+
                   "&cmbPayVocNo="+cmbPayVocNo+"&txtPaymentVoc_Date="+txtPaymentVoc_Date+                   
                   "&cmbSL_type="+cmbSL_type+"&cmbSL_Code="+cmbSL_Code+"&txtMode_of_creat="+cmbAdvance_type; 
               	  
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse_ImpReceipt(req);
                }   
                req.send(null);
        }     
        
      /*  if(Command=="LoadPayVoucherDetails")
        {
        	var cmbPayVocNo =  document.getElementById("cmbPayVocNo").value;
        	
                var url="../../../../../Imprest_Refund_Receipt_Edit.kv?Command=LoadPayVoucherDetails&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
                        "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbPayVocNo="+cmbPayVocNo;
                
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse_ImpReceipt(req);
                }   
                req.send(null);

        } */     
        
        if(Command=="load_Receipt_No")
        {
              if(txtCrea_date.length!=0)
              {
                if (txtAmtType == 'CR') 
                {	 
                    var url="../../../../../Imprest_Refund_Receipt_Edit.kv?Command=load_Receipt_No&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
	                cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtAmtType="+txtAmtType+"&txtMode_of_creat="+cmbAdvance_type;                        
                }                
                else if (txtAmtType == 'BR')
                {
                    var url="../../../../../Imprest_Refund_Receipt_Edit.kv?Command=load_Receipt_No&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
    	                cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtAmtType="+txtAmtType+"&txtMode_of_creat="+cmbAdvance_type; 
                }        	   
                var req=getTransport();
	        req.open("GET",url,true); 
	        req.onreadystatechange=function()
	        {
	            handleResponse_ImpReceipt(req);
	        }   
	        req.send(null);
              }
        }        
        if(Command=="load_Receipt_Details")
        {  
            var txtReceipt_No=document.getElementById("txtReceipt_No").value;
           
            if(txtReceipt_No!="")
            {
	            var url="../../../../../Imprest_Refund_Receipt_Edit.kv?Command=load_Receipt_Details&txtReceipt_No="+txtReceipt_No+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
	            cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtAmtType="+txtAmtType+"&txtMode_of_creat="+cmbAdvance_type; 
	            
	            var req=getTransport();
	            req.open("GET",url,true); 
	            req.onreadystatechange=function()
	            {
	            	handleResponse_ImpReceipt(req);
	            }   
	                req.send(null);
	    }         
        }
}



function handleResponse_ImpReceipt(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            
          //  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;           
           
            if(Command=="LoadPayEmpMaxAmt")
            {
            	LoadPayEmpMaxAmt(baseResponse);
            }    
            
            
         /*
            else if(Command=="LoadPayVoucherDetails")
            {
            	LoadPayVoucherDetails(baseResponse);
            }
          */
            
            else if(Command=="load_Receipt_No")
            {
                load_Receipt_No(baseResponse);
            }
            else if(Command=="load_Receipt_Details")
            {
                load_Receipt_Details(baseResponse);
            }
                        
        }
    }
}



/* Load Voucher Number */
function load_Receipt_No(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
 var txtReceipt_No=document.getElementById("txtReceipt_No");
  if(flag=="success")
    {
           var items_id=new Array();
           var Rec_No=baseResponse.getElementsByTagName("Rec_No");
            
            for(var k=0;k<Rec_No.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("Rec_No")[k].firstChild.nodeValue;
                
            }
         
            txtReceipt_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Receipt Number--";
            option.value="";
            try
            {
                txtReceipt_No.add(option);
            }catch(errorObject)
            {
                txtReceipt_No.add(option,null);
            }
            
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_id[k];
                  option.value=items_id[k];
                   try
                  {
                      txtReceipt_No.add(option);
                  }
                  catch(errorObject)
                  {
                      txtReceipt_No.add(option,null);
                  }
            }
    }
    else if(flag=="failure")
    {
            txtReceipt_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Receipt Number--";
            option.value="";
            try
            {
                txtReceipt_No.add(option);
            }catch(errorObject)
            {
                txtReceipt_No.add(option,null);
            }
         alert("No Receipt Found");
         clrForm();
    }
}




/* Load Voucher Details */
function load_Receipt_Details(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
	{
    	   
    	             /* Load General Details */
		      var MasHeadCode=baseResponse.getElementsByTagName("MasHeadCode")[0].firstChild.nodeValue;
		      var Ref_No=baseResponse.getElementsByTagName("Ref_No")[0].firstChild.nodeValue;
		      var Ref_Date=baseResponse.getElementsByTagName("Ref_Date")[0].firstChild.nodeValue;
		      
		       var accNo=baseResponse.getElementsByTagName("accNo")[0].firstChild.nodeValue;
		       var bk_name=baseResponse.getElementsByTagName("bk_name")[0].firstChild.nodeValue;
		       var bk_id=baseResponse.getElementsByTagName("bk_id")[0].firstChild.nodeValue;
		       var br_id=baseResponse.getElementsByTagName("br_id")[0].firstChild.nodeValue;
		      
		       var Total_amt=baseResponse.getElementsByTagName("Total_amt")[0].firstChild.nodeValue;
		       
		       var Rec_From=baseResponse.getElementsByTagName("Rec_From")[0].firstChild.nodeValue;
		       var Remak=baseResponse.getElementsByTagName("Remak")[0].firstChild.nodeValue;
		      
		       var Mas_SL_type=baseResponse.getElementsByTagName("Mas_SL_type")[0].firstChild.nodeValue;
		       var Mas_SL_code=baseResponse.getElementsByTagName("Mas_SL_code")[0].firstChild.nodeValue;
                       
                      // Max_Amount=baseResponse.getElementsByTagName("Max_Amount")[0].firstChild.nodeValue;                       
                      // alert(Max_Amount);
                       
                    
                      /* sub Ledger type (G) */
		      if(Mas_SL_type!=0)
		      document.getElementById("cmbMas_SL_type").value=Mas_SL_type;
		       
                      /* sub ledger code (G) */
		      if(Mas_SL_type!=0)
		      {
		            if(Mas_SL_type==5)
		             {
		                  document.getElementById("offlist_div_master").style.display='block';
		                  document.getElementById("txtOfficeID_mas").value="";
		                  alert("USE search ICON to select the office");
		             }
		             else
		              document.getElementById("offlist_div_master").style.display='none';
		           
		             if(Mas_SL_type==7)
		             {
		                  document.getElementById("emplist_div_master").style.display='block';
		                  document.getElementById("txtEmpID_mas").value=Mas_SL_code;		                 
		             }
		             else
		              document.getElementById("emplist_div_master").style.display='none';  
		              
		            var items_SLcode=new Array();
		            var items_SLdesc=new Array();
		            var Mas_SLCODE=baseResponse.getElementsByTagName("cid");
		            
		            for(var k=0;k<Mas_SLCODE.length;k++)
		            {
		                items_SLcode[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
		                items_SLdesc[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
		            }
                            
		            cmbSL_type=document.getElementById("cmbMas_SL_Code")
		            cmbSL_type.innerHTML="";
		            var option=document.createElement("OPTION");
		            option.text="--Select Code--";
		            option.value="";
		            try
		            {
		                cmbSL_type.add(option);
		            }catch(errorObject)
		            {
		                cmbSL_type.add(option,null);
		            }
                            
                            
		            for(var k=0;k<Mas_SLCODE.length;k++)
		            {   
		              var option=document.createElement("OPTION");
		              option.text=items_SLdesc[k];
		              option.value=items_SLcode[k];
		               try
		              {
		                  cmbSL_type.add(option);
		              }
		              catch(errorObject)
		              {
		                  cmbSL_type.add(option,null);
		              }
		            }
		          
		            document.getElementById("cmbMas_SL_Code").value=Mas_SL_code;  
		            
		      }
		   
		      
                      
                      
		      /* payment voucher number (G) */
		      if(Ref_No!="null")
		      document.getElementById("cmbPayVocNo").value=Ref_No;
		      else
		      document.getElementById("cmbPayVocNo").value="";
		      
		      
                      /* payment voucher date (G) */
		      if(Ref_Date!="null")
		       document.getElementById("txtPaymentVoc_Date").value=Ref_Date;
		      else
		      document.getElementById("txtPaymentVoc_Date").value="";
		      
		      /* bank details + account Head code */
		      document.getElementById("txtCash_Acc_code").value=MasHeadCode
		      document.getElementById("txtBankAccountNo").value=accNo;
		      document.getElementById("txtBankName").value=bk_name;
		      document.getElementById("txtBankID").value=bk_id;
		      document.getElementById("txtBranchID").value=br_id;
                      
		      /* amount */
		      document.getElementById("txtAmount").value=Total_amt;
		       
                       
		      var Rec_From=baseResponse.getElementsByTagName("Rec_From")[0].firstChild.nodeValue;
		      var MO_creation=baseResponse.getElementsByTagName("MO_creation")[0].firstChild.nodeValue;
		                   
		       
		      if(Rec_From!="null")
		         document.getElementById("txtRecei_from").value=Rec_From;
		      else
		         document.getElementById("txtRecei_from").value="";
		     
		      /* remarks */
		      if(Remak!="null")
		         document.getElementById("txtRemarks").value=Remak;
     	              else
		         document.getElementById("txtRemarks").value="";
		       

		        
		       
		        
		        /* Load Detail TAB */
		       
		       
		        var items=new Array();
		        
                        /* account Head code (D) */
		        items[0]=baseResponse.getElementsByTagName("AHcode")[0].firstChild.nodeValue;   
		        document.getElementById("txtAcc_HeadCode").value=items[0];
		        
		        /* account Head code desc (D) */
		        items[1]=baseResponse.getElementsByTagName("AHdesc")[0].firstChild.nodeValue;
		        document.getElementById("txtAcc_HeadDesc").value=items[1];
		        
		        
                        /* cr / dr indicator (D) */
		        items[2]=baseResponse.getElementsByTagName("CR_DR_ind")[0].firstChild.nodeValue;
		        if (items[2] =='CR')
		        {
		           	document.frm_Imp_Refund_Receipt.rad_sub_CR_DR[0].checked=true;
		        } 	
		        else if (items[2] =='DR')
		        { 	
		        	document.frm_Imp_Refund_Receipt.rad_sub_CR_DR[1].checked=true;
		        }
		        
		        
		        
		        
		        items[3]=baseResponse.getElementsByTagName("SL_Type")[0].firstChild.nodeValue;
		        if(items[3]==0)
		        items[3]="";
		        
		        
		        
		        items[4]=baseResponse.getElementsByTagName("SL_Desc")[0].firstChild.nodeValue;
		        if(items[4]=="null")
		        items[4]="";
		        
		        
		        
		        items[5]=baseResponse.getElementsByTagName("SL_Code")[0].firstChild.nodeValue;
		        if(items[5]==0)
		        items[5]="";
		        
		        
		        items[6]=baseResponse.getElementsByTagName("desc_type")[0].firstChild.nodeValue;
		        if(items[6]=="null")
		        items[6]="";
		        
		        
		        
		        /* Sub Ledger Type Code (D) */
		        if(items[3]!=0)
			{
			             if(items[3]==7)
			             {
			                  document.getElementById("emplist_div_trans").style.display='block';
			                  document.getElementById("txtEmpID_trs").value=items[5];
			                  document.getElementById("cmbSL_type").value=items[3];
			             }
			             else
			             { 	 
			                  document.getElementById("emplist_div_trans").style.display='none';  
			             }
			             
			            
			            cmbSL_type=document.getElementById("cmbSL_Code")
			            cmbSL_type.innerHTML="";
			            var option=document.createElement("OPTION");
			            option.text="--Select Code--";
			            option.value="";
			            try
			            {
			                cmbSL_type.add(option);
			            }catch(errorObject)
			            {
			                cmbSL_type.add(option,null);
			            }
			            
			            var option=document.createElement("OPTION");
			            option.text=items[6];
			            option.value=items[5];
			            
			            try
			            {
			                cmbSL_type.add(option);
			            }
			            catch(errorObject)
			            {
			                cmbSL_type.add(option,null);
			            }			            
			            document.getElementById("cmbSL_Code").value=items[5];  
			 }
			   
		        
		        
		        
		        
		        /* Mode of payment (D) cheque or dd or ecs */
		        items[7]=baseResponse.getElementsByTagName("che_or_DD")[0].firstChild.nodeValue;
		        if ( items[7] == "C") 
                        {
                           document.frm_Imp_Refund_Receipt.txtCheque_DD[0].checked==true;  
                        }
                        else if ( items[7] == "D") 
                        {
                          document.frm_Imp_Refund_Receipt.txtCheque_DD[1].checked==true;  
                        }
		        else if  ( items[7] == "E") 
                        {
                           document.frm_Imp_Refund_Receipt.txtCheque_DD[2].checked==true;  
                        }
                        
		     
                        /* cheque / dd number */
                        items[8]=baseResponse.getElementsByTagName("che_DD_no")[0].firstChild.nodeValue;
		        if ( items[8] == "null") 
		        items[8]="";
		        document.getElementById("txtCheque_DD_NO").value=items[8];
		        
                        
		        /* cheque / dd date */   
		        items[9]=baseResponse.getElementsByTagName("che_DD_date")[0].firstChild.nodeValue;
		        if ( items[9] == "null") 
		        items[9]="";
		        document.getElementById("txtCheque_DD_date").value=items[9];
		        
		        /* bank name */
		        items[10]=baseResponse.getElementsByTagName("bank_na")[0].firstChild.nodeValue;
		        document.getElementById("txtBank_Name").value=items[10];
		        
		        /* drawee */
		        items[11]=baseResponse.getElementsByTagName("drawee")[0].firstChild.nodeValue;
		        document.getElementById("txtDraw_BR").value=items[11];
		        
		        /* micr code */
		        items[12]=baseResponse.getElementsByTagName("micr")[0].firstChild.nodeValue;
		        if(items[12]=="null")
		        items[12]="";
		        document.getElementById("txtBank_M_Code").value=items[12];
		        
		        /* received from */
		        items[13]=baseResponse.getElementsByTagName("sub_rec_frm")[0].firstChild.nodeValue;
		        if(items[13]=="null")
		        items[13]="";
                        
                        txtRecei_from=document.getElementById("txtRecei_from")
			txtRecei_from.innerHTML="";
			var option=document.createElement("OPTION");
			        /*  try
			            {
			                txtRecei_from.add(option);
			            }catch(errorObject)
			            {
			                txtRecei_from.add(option,null);
			            }
			         */   
			            var option=document.createElement("OPTION");
			            option.text=items[6];
			            option.value=items[5];
			            
			            try
			            {
			                txtRecei_from.add(option);
			            }
			            catch(errorObject)
			            {
			                txtRecei_from.add(option,null);
			            }
                        
		        
		        
		        /* amount (D) */
		        items[14]=baseResponse.getElementsByTagName("sub_amount")[0].firstChild.nodeValue;
		        document.getElementById("txtsub_Amount").value=items[14];
		        
		        /* particulars (D) */
		        items[15]=baseResponse.getElementsByTagName("sub_part")[0].firstChild.nodeValue;
		        if(items[15]=="null")
		        items[15]="";
		        document.getElementById("txtParticular").value=items[15];         
                        
                        /** Calculate Maximum Amount for particular Employee */
                        doFunction_ImpReceipt("LoadPayEmpMaxAmt");
	 }
    
}



/* Load Payment - Emp Maximum Amount */

function LoadPayEmpMaxAmt(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;      
   
    if(flag=="success")
    {
    	Max_Amount = baseResponse.getElementsByTagName("Max_Amount")[0].firstChild.nodeValue;       
    }
    else if(flag=="NoRecords")
    {
    	alert("No Amount for Employee")
    }
    else if(flag=="failure")
    {
    	alert("Error Loading Employee Amount")
    }
    		 
}





/* Load PaymentVoucher Details */
/*
function LoadPayVoucherDetails(baseResponse)
{
	    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;  
	   
	    if(flag=="Success")
	    {
	    	var pay_date = baseResponse.getElementsByTagName("pay_date")[0].firstChild.nodeValue;
	        var tot_amt=baseResponse.getElementsByTagName("tot_amt")[0].firstChild.nodeValue;
	        var paid_to=baseResponse.getElementsByTagName("paid_to")[0].firstChild.nodeValue;
	        document.getElementById("txtPaymentVoc_Date").value=pay_date;
	        document.getElementById("txtAmtPaid").value=tot_amt;
	        document.getElementById("txtRecei_from").value=paid_to;
	    }
	    
}
*/


/** Check Amount while Checking - Entered Amount should not go beyond the Max Amount */ 
function AmountTest(Ent_Amt)
{
  if ( parseInt(Ent_Amt) >  parseInt(Max_Amount) ) 
  {
    alert("Amount Should be less than or equal to "+Max_Amount); 
    document.getElementById("txtsub_Amount").value="";
  }  
}


function grid_ImpReceipt(Cash_r_Bank)
{
   
   var grid_obj_Gen;
   var grid_obj_Det;
   var grid_ImpReceipt_Torch;
   
   grid_obj_Gen = document.getElementById("grid_ImpReceipt_Gen");   
   grid_obj_Det = document.getElementById("grid_ImpReceipt_Det");
   document.getElementById("txtCrea_date").value="";
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






function call_mainJSP_script(fromcal_dateCtrl,blr_flag)    
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
        //doFunction('load_Receipt_No','null');
    }
    else
    {
        var cmbSL_Code=document.getElementById("txtReceipt_No");   
        cmbSL_Code.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="-- Select Receipt Number --";
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
              doFunction_ImpReceipt('load_Receipt_No');             
             }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");
                    dateCtrl.focus();
                    var cmbSL_Code=document.getElementById("txtReceipt_No");   
                    cmbSL_Code.innerHTML="";
                    var option=document.createElement("OPTION");
                    option.text="-- Select Receipt Number --";
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






function loadName_Mas(value)
{
 if(document.getElementById("cmbMas_SL_Code").value=="")
    return;
value=document.getElementById("cmbMas_SL_Code").options[document.getElementById("cmbMas_SL_Code").selectedIndex].text; 
s=document.getElementById("cmbMas_SL_type").value;
if(s=="7" )
value=value.substring(0,value.indexOf("-"));

document.getElementById("txtRecei_from").value=value;
}





//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function check_leng(remarks,param)
{
  if(param=='remarks')
  {
    if((remarks.length)>=240)
    {
    alert("Please Enter Remarks below 250 characters");
    }
  }
  
  if(param=='received_from') 
  {
    if((remarks.length)>=45)
    {
    alert("Please Enter Received From name below 50 characters");
    }
  }
  
  if(param=='particulars') 
  {
    if((remarks.length)>=190)
    {
    alert("Please Enter Paticulars below 200 characters");
    }
  }
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
}


function clrForm()
{
    /* Voucher number */
    document.getElementById("cmbPayVocNo").value="";
    /* payment Date */
    document.getElementById("txtPaymentVoc_Date").value="";        
    /* amount Paid */
 
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

 
    /** Load Default Account Head code */
    document.getElementById("txtAcc_HeadCode").value="820103";             
   // doFunction('checkCode','null');
    doFunctionBLOCK('checkCode','null');


}

function loadReceivedFrom()
{
       document.getElementById("txtRecei_from").value=document.getElementById("txtEmpID_mas").value;
}

/////////////////////////////////////////////Load Account Head Based on the Journal Type Selection /////////////////////

function loadAccountHead()
{		 
		 var cmbAdvance_type=document.getElementById("cmbAdvance_type").value;		 
		 if(cmbAdvance_type=='I')		
             document.getElementById("txtAcc_HeadCode").value=820103;	
		 else if(cmbAdvance_type=='T')		
			document.getElementById("txtAcc_HeadCode").value=820102;
				 
		// doFunction('checkCode','null');
		 doFunctionBLOCK('checkCode','null');
			 
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////