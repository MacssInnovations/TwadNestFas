
var Bank_popup_flag;


/**
 *  Browser Indentification 
 */
 
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



/**
 *  Debit Account Head Code Pop Up 
 */

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
        winAcc_Bank_No=null
    }
    //var Office_code=document.getElementById("cmbOffice_code").value;  
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var txtModule_Type="MF009";
    var cr_dr_indi="DR";
    if(document.frmFund_Receipt_Create_byHO.radRecType[0].checked==true)
    var unspent_OR_col='OPR';
    else
    var unspent_OR_col='COL';
    bankid=document.getElementById("txtSubBankId").value;
    var txtSub_Office_code=document.getElementById("txtSub_Office_code").value;
//alert("MainAccNopopup**************"+bankid);
    winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi+"&unspent_OR_col="+unspent_OR_col+"&txtSubBankId="+bankid+"&txtSub_Office_code="+txtSub_Office_code,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAcc_Bank_No.moveTo(250,250);  
    winAcc_Bank_No.focus();
}



/*
 * Credit Account Head Code POP up 
 */
function SubAccNopopup()
{
  //  alert("inside SubAccNopopup");
    Bank_popup_flag=false;
    if (winAcc_Bank_No && winAcc_Bank_No.open && !winAcc_Bank_No.closed) 
    {
       winAcc_Bank_No.resizeTo(500,500);
       winAcc_Bank_No.moveTo(250,250); 
       winAcc_Bank_No.focus();
    }
    else
    {
        winAcc_Bank_No=null
    }
    var Office_code=document.getElementById("txtSub_Office_code").value;  
    var txtModule_Type="MF009";
    var cr_dr_indi="CR";
    
    if(document.frmFund_Receipt_Create_byHO.radRecType[0].checked==true)
    var unspent_OR_col='OPR';
    else
    var unspent_OR_col='COL';
    
    var cmb_HO_acc_unitid;      // Extra field on 31stjan2007
    if(Office_code==5000)
    {
        Office_code=0;
        cmb_HO_acc_unitid=document.getElementById("cmb_HO_acc_unitid").value;
    }
    else
    {
        cmb_HO_acc_unitid=0;
    }
    //added on 17/01/2011
//    alert("sss**********"+Office_code);
//    alert("bbb**********"+cmb_HO_acc_unitid);
//    alert("ccc**********"+txtModule_Type);
//    alert("ddd**********"+cr_dr_indi);
//    alert("eee**********"+unspent_OR_col);
    bankid=document.getElementById("txtSubBankId").value;
    //alert("bankid********"+bankid);
    winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised.jsp?Office_code="+Office_code+"&cmbAcc_UnitCode="+cmb_HO_acc_unitid+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi+"&unspent_OR_col="+unspent_OR_col+"&txtSubBankId="+bankid,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAcc_Bank_No.moveTo(250,250);  
    winAcc_Bank_No.focus();    
}




/** 
 *  Get the final Result from POP up Menu 
 */

function doParentAcc_NO(Acc_Head_Code,Bank_Acc_No,bankid,br_id,B_name)
{
	//alert(Bank_popup_flag);
   /** Load Debit Account Head Code Details */
   if(Bank_popup_flag==true)
   {
       document.getElementById("txtCash_Acc_code").value=Acc_Head_Code;
       document.getElementById("txtBankAccountNo").value=Bank_Acc_No;
       document.getElementById("txtBankId").value=bankid;
       document.getElementById("txtBranchId").value=br_id;
       document.getElementById("txtBankName").value=B_name;
       Bank_popup_flag="";     
       
       Load_Account_Head_Code_Credit();       
       
     //  return true;              
   }   
  /** Load Credit Account Head Code Details */ 
   else if(Bank_popup_flag==false)
   {
      
      
       document.getElementById("txtCreditAccCode").value=Acc_Head_Code;
       document.getElementById("txtSubBankAccountNo").value=Bank_Acc_No;
       document.getElementById("txtSubBankId").value=bankid;
       document.getElementById("txtSubBranchId").value=br_id;
       document.getElementById("txtSubBankName").value=B_name;
       Bank_popup_flag="";     
     
       var offcode=document.getElementById("txtSub_Office_code").value;
       if(offcode!=6777)
       {
       Load_Account_Head_Code_Debit();
       }
     
    //  return true;
   }
}
 




/* 
 *  Office Code Selection POP UP    
 */
/*
var winjob;
function jobpopup()
{
    if(winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/FAS/FAS1/FundReceiptSystem/jsps/JobPopupJSP.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
}




//--------------------------------------------------------------------------------

function forChildOption()
{
      if (winjob && winjob.open && !winjob.closed) 
          winjob.officeSelection(true,true,true,false,true);
}

//--------------------------------------------------------------------------------




//--------------------------------------------------------------------------------

function doParentJob(jobid,deptid)
{
       if(deptid=='TWAD')
        {
            document.getElementById("txtSub_Office_code").value=jobid;
            doFunction('office_with_bank_betails','null');
        }
        else
        {
                alert('Please select an Office ');
                if (winjob && winjob.open && !winjob.closed) 
                {
                   winjob.resizeTo(500,500);
                   winjob.moveTo(250,250); 
                   winjob.focus();
                }
                return false
        }
   
    return true
}

window.onunload=function()
{
    if (winAcc_Bank_No && winAcc_Bank_No.open && !winAcc_Bank_No.closed) winAcc_Bank_No.close();
    if (winjob && winjob.open && !winjob.closed) winjob.close();
}
*/
//--------------------------------------------------------------------------------


//////////////   FOR  JOB POPUP WINDOW //////////////////////


var winjob;
function jobpopup()
{
    if(winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
}
function forChildOption()
{
      if (winjob && winjob.open && !winjob.closed) 
             winjob.officeSelection(true,true,true,false);
}
function doParentJob(jobid,deptid)
{
       if(deptid=='TWAD')
        {
            document.getElementById("txtSub_Office_code").value=jobid;
            doFunction('office_with_bank_betails','null');
        }
        else
        {
                alert('Please select an Office ');
                if (winjob && winjob.open && !winjob.closed) 
                {
                   winjob.resizeTo(500,500);
                   winjob.moveTo(250,250); 
                   winjob.focus();
                }
                return false
        }
   
    return true
}

window.onunload=function()
{
if (winAcc_Bank_No && winAcc_Bank_No.open && !winAcc_Bank_No.closed) winAcc_Bank_No.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
}




/**
 *   It is the main function which handles two operations
 *    1. office_with_bank_betails and 
 *    2. unspent_OR_col_based_bank
 */

function doFunction(Command,param)
{   
        
        if(Command=="office_with_bank_betails")
        {   
                                  
            var oid=document.getElementById("txtSub_Office_code").value;
            var txtModule_Type="MF009";
            var cr_dr_indi="CR";
            var unspent_OR_col;
            if(document.frmFund_Receipt_Create_byHO.radRecType[0].checked==true)
                unspent_OR_col='OPR';
            else
                unspent_OR_col='COL';
            if(oid!="")
            {
                if(oid==5000)
                {
                    cmb_HO_acc_unitid=document.getElementById("cmb_HO_acc_unitid").value;
                    
                    if(cmb_HO_acc_unitid=="")
                    {
                        alert("Select the A/c Unit of Head office ");
                        return;
                    }
                    
                }
                else
                {
                    cmb_HO_acc_unitid="";
                    document.getElementById("cmb_HO_acc_unitid").value="";
                }
                
                var url="../../../../../Fund_Receipt_Create_byHO.view?Command=office_with_bank_betails&oid="+oid+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi+"&unspent_OR_col="+unspent_OR_col;
                url=url+"&cmb_HO_acc_unitid="+cmb_HO_acc_unitid;
                
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req,param);
                }   
                        req.send(null);
             }
             else 
                alert("Enter the office");
        }
        
        
        else if(Command=="unspent_OR_col_based_bank")
        {   
            // Clear the Office details fields when you change the unspent or Collection type        
            document.getElementById("txtSub_Office_code").value="";
            document.getElementById("txtOfficeName").value="";
            document.getElementById("txtCreditAccCode").value="";
            document.getElementById("txtSubBankAccountNo").value="";
            document.getElementById("txtSubBankId").value="";
            document.getElementById("txtSubBranchId").value="";
            document.getElementById("txtSubBankName").value="";
            
            var unitID=document.getElementById("cmbAcc_UnitCode").value;
            var txtModule_Type="MF009";
            var cr_dr_indi="DR";
            
            if(document.frmFund_Receipt_Create_byHO.radRecType[0].checked==true)
            var unspent_OR_col='OPR';
            else
            var unspent_OR_col='COL';
            
            var url="../../../../../Fund_Receipt_Create_byHO.view?Command=unspent_OR_col_based_bank&unitID="+unitID+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi+"&unspent_OR_col="+unspent_OR_col;
           // alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req,param);
            }   
                    req.send(null);
        }
}







/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
function handleResponse(req,param)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="office_with_bank_betails")
            {
                office_with_bank_betails(baseResponse,param);
            }
            else if(Command=="unspent_OR_col_based_bank")
            {
                unspent_OR_col_based_bank(baseResponse);
            }
            
        }
    }
}






function unspent_OR_col_based_bank(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {  
        var AC_HEAD_CODE=baseResponse.getElementsByTagName("AC_HEAD_CODE")[0].firstChild.nodeValue;
        var BANK_ID=baseResponse.getElementsByTagName("BANK_ID")[0].firstChild.nodeValue;
        var BRANCH_ID=baseResponse.getElementsByTagName("BRANCH_ID")[0].firstChild.nodeValue;
        var BANK_AC_NO=baseResponse.getElementsByTagName("BANK_AC_NO")[0].firstChild.nodeValue;
        var bk_br_city=baseResponse.getElementsByTagName("bk_br_city")[0].firstChild.nodeValue;
        
        document.getElementById("txtCash_Acc_code").value=AC_HEAD_CODE;
        document.getElementById("txtBankAccountNo").value=BANK_AC_NO;
        document.getElementById("txtBankId").value=BANK_ID;
        document.getElementById("txtBranchId").value=BRANCH_ID;
        document.getElementById("txtBankName").value=bk_br_city;
    }
    else if(flag=="failure_bank")
    {
        alert("Bank details not found for Accounting Unit");
        document.getElementById("txtCash_Acc_code").value="";
        document.getElementById("txtBankId").value="";
        document.getElementById("txtBankAccountNo").value="";
        document.getElementById("txtBranchId").value="";
        document.getElementById("txtBankName").value="";
    }
    else if(flag=="failure")
    {
        alert("Failure to retrive values");
    } 
   
}





function chkOffceID()
{
    if(document.getElementById("txtSub_Office_code").value.length==0)
    {
     alert("Enter the Office id ");
     document.getElementById("txtSub_Office_code").focus();
    return false;
    }
    else
    return true;
}





///////////////////////////////////// loadoffice ///////
function office_with_bank_betails(baseResponse,param)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   // alert("flag:"+flag);
    if(flag=="success")
    {  
        var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
        var oname=baseResponse.getElementsByTagName("oname")[0].firstChild.nodeValue;
        var AC_HEAD_CODE=baseResponse.getElementsByTagName("AC_HEAD_CODE")[0].firstChild.nodeValue;
        var BANK_ID=baseResponse.getElementsByTagName("BANK_ID")[0].firstChild.nodeValue;
        var BRANCH_ID=baseResponse.getElementsByTagName("BRANCH_ID")[0].firstChild.nodeValue;
        var BANK_AC_NO=baseResponse.getElementsByTagName("BANK_AC_NO")[0].firstChild.nodeValue;
        var bk_br_city=baseResponse.getElementsByTagName("bk_br_city")[0].firstChild.nodeValue;
        
        document.getElementById("txtSub_Office_code").value=oid;
        document.getElementById("txtOfficeName").value=oname;
        document.getElementById("txtCreditAccCode").value=AC_HEAD_CODE;
        document.getElementById("txtSubBankAccountNo").value=BANK_AC_NO;
        document.getElementById("txtSubBankId").value=BANK_ID;
        document.getElementById("txtSubBranchId").value=BRANCH_ID;
        document.getElementById("txtSubBankName").value=bk_br_city;   
        
         Load_Account_Head_Code_Debit();       
        
    }
    else if(flag=="failure_bank")
    {
        var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
        var oname=baseResponse.getElementsByTagName("oname")[0].firstChild.nodeValue;
        alert("Bank details not found for Accounting unit of the given office");
        
        document.getElementById("txtSub_Office_code").value="";
        document.getElementById("txtOfficeName").value="";
        document.getElementById("txtCreditAccCode").value="";
        document.getElementById("txtSubBankAccountNo").value="";
        document.getElementById("txtSubBankId").value="";
        document.getElementById("txtSubBranchId").value="";
        document.getElementById("txtSubBankName").value="";
    }
    else if(flag=="failure_office")
    {
        var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
        alert("Office Id '"+oid+"' doesn't Exist");
        document.getElementById("txtSub_Office_code").value="";        
        document.getElementById("txtOfficeName").value="";
        
        document.getElementById("txtCreditAccCode").value="";
        document.getElementById("txtSubBankAccountNo").value="";
        document.getElementById("txtSubBankId").value="";
        document.getElementById("txtSubBranchId").value="";
        document.getElementById("txtSubBankName").value="";
    }
}




/**
 *  Clear Function 
 */

function call_clr()
{
    document.getElementById("txtCreditAccCode").value="";
    document.getElementById("txtSubBankAccountNo").value="";
    document.getElementById("txtSubBankId").value="";
    document.getElementById("txtSubBranchId").value="";
    document.getElementById("txtSubBankName").value="";
    document.getElementById("txtAmount").value="";
    document.getElementById("txtRemarks").value="";
    document.getElementById("txtCheque_DD_NO").value="";
    document.getElementById("txtCheque_DD_date").value="";
    document.frmFund_Receipt_Create_byHO.txtCheque_DD[0].checked=true;
    document.frmFund_Receipt_Create_byHO.radRecType[0].checked=true;
    document.getElementById("txtReferenceNo").value="";
    document.getElementById("txtReferenceDate").value="";    
    document.getElementById("txtSub_Office_code").value="";
    document.getElementById("txtOfficeName").value="";

}


function clrForm()
{
 if(window.confirm("Do you want to clear ALL fields ?"))
 {
   call_clr();
 }
}




/** 
 *  Null Check Function 
 */

function checkNull()
{
    if(document.getElementById("cmbAcc_UnitCode").value=="")
    {
        alert("Select the Account Unit code");
        document.getElementById("cmbAcc_UnitCode").focus();
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
        alert("Enter the Date of Creation");
        document.getElementById("txtCrea_date").focus();
        return false;    
    }
    if(document.getElementById("txtCash_Acc_code").value.length==0 || document.getElementById("txtCash_Acc_code").value==0)
    {
        alert("Select the Debit A/c Code");
        document.getElementById("txtCash_Acc_code").focus();
        return false;
    }
    
    if(document.getElementById("txtBankId").value.length==0 || document.getElementById("txtBankId").value==0)
    {
        alert("Bank Id not populated  for Selected Office");
        //document.getElementById("txtAmount").focus();
        return false;    
    }
    if(document.getElementById("txtBankAccountNo").value.length==0 || document.getElementById("txtBankAccountNo").value==0)
    {
        alert("Bank Account Number not populated for Selected Office");
        //document.getElementById("txtAmount").focus();
        return false;    
    }
    if(document.getElementById("txtBranchId").value.length==0 || document.getElementById("txtBranchId").value==0)
    {
        alert("Branch Id not populated  for Selected Office");
        //document.getElementById("txtAmount").focus();
        return false;    
    }
    
    
    
 if(document.frmFund_Receipt_Create_byHO.txtCheque_DD[2].checked != true)
 {       
    if(document.getElementById("txtCheque_DD_NO").value.length==0)
    {
     alert("Enter the Cheque/DD number");
     document.getElementById("txtCheque_DD_NO").focus();
    return false;
    }
    if(document.getElementById("txtCheque_DD_date").value.length==0)
    {
     alert("Enter the Cheque/DD Date");
     document.getElementById("txtCheque_DD_date").focus();
    return false;
    }
    
  }  
        
    
    
    if(document.getElementById("txtAmount").value.length==0)
    {
        alert("Enter the Total Amount");
        document.getElementById("txtAmount").focus();
        return false;    
    }
   
    if(document.getElementById("txtSub_Office_code").value.length==0 || document.getElementById("txtSub_Office_code").value==0)
    {
    alert("Enter the Office Id from which Amount received");
    document.getElementById("txtSub_Office_code").focus();
    return false;
    }    
    if(document.getElementById("txtSub_Office_code").value==5000 && document.getElementById("cmb_HO_acc_unitid").value=="")
    {
        alert("Select the Unit Id of Head office");
        return false;
    }    
    if(document.getElementById("txtCreditAccCode").value.length==0 || document.getElementById("txtCreditAccCode").value==0)
    {
    alert("Select the Credit A/C code");
    document.getElementById("txtCreditAccCode").focus();
    return false;
    }
    if(document.getElementById("txtSubBankAccountNo").value.length==0 || document.getElementById("txtSubBankAccountNo").value==0)
    {
    alert("Enter the Bank Account Number for Head Office");
    document.getElementById("txtSubBankAccountNo").focus();
    return false;
    }
    if(document.getElementById("txtSubBankId").value.length==0 || document.getElementById("txtSubBankId").value==0)
    {
    alert("Enter the Bank Id has not populated for Head Office");
    document.getElementById("txtSubBankId").focus();
    return false;
    }
    if(document.getElementById("txtSubBranchId").value.length==0)
    {
    alert("Enter the Branch Id has not populated for Head Office");
    document.getElementById("txtSubBranchId").focus();
    return false;
    }
    
    
    /*
    if(document.getElementById("txtReferenceNo").value.length==0)
    {
        alert("Enter the Reference Number");
        document.getElementById("txtReferenceNo").focus();
        return false;    
    }
    if(document.getElementById("txtReferenceDate").value.length==0)
    {
        alert("Enter the Reference Date");
        document.getElementById("txtReferenceDate").focus();
        return false;    
    }
    */
    
    
return true;
}








///////////////////////////////////////////    TB_checking and Calender control return value handling

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
    if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
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
      document.getElementById("txtVoucher_No").value="";
    }
}





/**
 *   Trail Balance Checking 
 */

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
                 //doFunction('load_Voucher_No','null');                 //return true;
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
                    dateCtrl.focus();
                    document.getElementById("txtVoucher_No").value="";
               }
               else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");//return false;//
                    dateCtrl.focus();
                    document.getElementById("txtVoucher_No").value="";     
               }
        }
    }
}





//--------------------------------------------------------------------------------------------------------------

 /** If any changes in Credit Account Head Code POP up menu 
  *  Corresponding Debit Account Head Code should be loaded 
  */
  
function Load_Account_Head_Code_Debit()
{
   
    var AC_Head=document.getElementById("txtCreditAccCode").value;
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    
    AC_Head_Sub=AC_Head.substring(2,4);
        
    if(document.frmFund_Receipt_Create_byHO.radRecType[0].checked==true)
    var unspent_OR_col='OPR';
    else
    var unspent_OR_col='COL';
  
    var CR_DR_Type="Debit_Load";
  
    var url="../../../../../Load_Bank_Details_for_FR_ByHO.kv?Command=LoadBankDetails&AC_Head_Sub="+AC_Head_Sub+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&unspent_OR_col="+unspent_OR_col+"&CR_DR_Type="+CR_DR_Type;
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
       LoadBankDetails_Debit(req);
    }   
    req.send(null); 
}



function LoadBankDetails_Debit(req)
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
                var BANK_ID=baseResponse.getElementsByTagName("BANK_ID")[0].firstChild.nodeValue;
                var BRANCH_ID=baseResponse.getElementsByTagName("BRANCH_ID")[0].firstChild.nodeValue;
                var BANK_AC_NO=baseResponse.getElementsByTagName("BANK_AC_NO")[0].firstChild.nodeValue;
                var AC_HEAD_CODE=baseResponse.getElementsByTagName("AC_HEAD_CODE")[0].firstChild.nodeValue;
                var bk_br_city=baseResponse.getElementsByTagName("bk_br_city")[0].firstChild.nodeValue;
                        
                document.getElementById("txtCash_Acc_code").value=AC_HEAD_CODE;
                document.getElementById("txtBankAccountNo").value=BANK_AC_NO;
                document.getElementById("txtBankName").value=bk_br_city;
                document.getElementById("txtBankId").value=BANK_ID ;
                document.getElementById("txtBranchId").value=BRANCH_ID;                         
                
              }
              else if(flag=="failure")
              {
                 alert("Error in Loading Bank Details");
              }
              else if(flag=="InvalidBank")
              {
                 alert("Invalid Bank");
              }
        }

    }

}





//--------------------------------------------------------------------------------------------------------------


 /** If any changes in Debit Account Head Code POP up menu 
  *  Corresponding Credit Account Head Code should be loaded 
  */
  
function Load_Account_Head_Code_Credit()
{
   
    var AC_Head=document.getElementById("txtCash_Acc_code").value;
    var cmbAcc_UnitCode=document.getElementById("txtSub_Office_code").value;
    
    AC_Head_Sub=AC_Head.substring(2,4);
        
    if(document.frmFund_Receipt_Create_byHO.radRecType[0].checked==true)
    var unspent_OR_col='OPR';
    else
    var unspent_OR_col='COL';
  
    var CR_DR_Type="Credit_Load";
    
    var url="../../../../../Load_Bank_Details_for_FR_ByHO.kv?Command=LoadBankDetails&AC_Head_Sub="+AC_Head_Sub+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&unspent_OR_col="+unspent_OR_col+"&CR_DR_Type="+CR_DR_Type;
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
       LoadBankDetails_Credit(req);
    }   
    req.send(null); 
}



function LoadBankDetails_Credit(req)
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
                var BANK_ID=baseResponse.getElementsByTagName("BANK_ID")[0].firstChild.nodeValue;
                var BRANCH_ID=baseResponse.getElementsByTagName("BRANCH_ID")[0].firstChild.nodeValue;
                var BANK_AC_NO=baseResponse.getElementsByTagName("BANK_AC_NO")[0].firstChild.nodeValue;
                var AC_HEAD_CODE=baseResponse.getElementsByTagName("AC_HEAD_CODE")[0].firstChild.nodeValue;
                var bk_br_city=baseResponse.getElementsByTagName("bk_br_city")[0].firstChild.nodeValue;
                        
                document.getElementById("txtCreditAccCode").value=AC_HEAD_CODE;
                document.getElementById("txtSubBankAccountNo").value=BANK_AC_NO;
                document.getElementById("txtSubBankName").value=bk_br_city;                        
                document.getElementById("txtSubBankId").value=BANK_ID;
                document.getElementById("txtSubBranchId").value=BRANCH_ID;                
                        
              }
              else if(flag=="failure")
              {
                 alert("Error in Loading Bank Details");
              }
              else if(flag=="InvalidBank")
              {
                 alert("Invalid Bank");
              }
              
      
        }

    }

}










//--------------------------------------------------------------------------------------------------------------




function ecs_check()
{
 
 /* 
 
 if(document.frmFund_Receipt_Create_byHO.txtCheque_DD[2].checked==true)
  {
     document.getElementById("ecs_hide").style.display="none";         
     document.getElementById("ecs_hide1").style.display="none";   
  }
  else
  {
     document.getElementById("ecs_hide").style.display="block";            
     document.getElementById("ecs_hide1").style.display="block";            
  }
  
 */  
  
}
