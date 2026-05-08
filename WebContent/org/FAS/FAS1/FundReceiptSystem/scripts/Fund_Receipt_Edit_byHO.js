var Bank_popup_flag;
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

function byUnitAndOfficeChange()
{
    doFunction('load_Voucher_No','null');
}

//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;

function servicepopup()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,500);
       winemp.moveTo(250,250); 
       winemp.focus();
    }
    else
    {
        winemp=null
    }
        
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
document.getElementById("txtAuth_By").value=emp;
doFunction('loademp','null');
}


var winAcc_Bank_No;     // Fteching Account Head No and Bank  No

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
    if(document.frmFund_Receipt_Edit_byHO.radRecType[0].checked==true)
    var unspent_OR_col='OPR';
    else
    var unspent_OR_col='COL';
    var bankid=document.getElementById("txtBankId").value;
   // alert("bankid:::"+bankid);
    winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi+"&unspent_OR_col="+unspent_OR_col+"&txtSubBankId="+bankid,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAcc_Bank_No.moveTo(250,250);  
    winAcc_Bank_No.focus();
}
function SubAccNopopup()
{
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
    
    if(document.frmFund_Receipt_Edit_byHO.radRecType[0].checked==true)
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
    var bankid=document.getElementById("txtSubBankId").value;
   // alert("bankid********"+bankid);
    //winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised.jsp?Office_code="+Office_code+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi+"&unspent_OR_col="+unspent_OR_col,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
     // Extra one field unit ID, in case of Head office
    // here if office_id is 5000, we need to pass unit id, else we pass office id, so URL has both unit id and office id
    winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised.jsp?Office_code="+Office_code+"&cmbAcc_UnitCode="+cmb_HO_acc_unitid+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi+"&unspent_OR_col="+unspent_OR_col,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAcc_Bank_No.moveTo(250,250);  
    winAcc_Bank_No.focus();
}
function doParentAcc_NO(Acc_Head_Code,Bank_Acc_No,bankid,br_id,B_name)
{
	//alert("edit"+Bank_popup_flag);
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
  else if(Bank_popup_flag==false)
  {
       document.getElementById("txtCreditAccCode").value=Acc_Head_Code;
       document.getElementById("txtSubBankAccountNo").value=Bank_Acc_No;
       document.getElementById("txtSubBankId").value=bankid;
       document.getElementById("txtSubBranchId").value=br_id;
       document.getElementById("txtSubBankName").value=B_name;
       Bank_popup_flag="";
      // return true;
       Load_Account_Head_Code_Debit();
   }
}

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
    if (winemp && winemp.open && !winemp.closed) winemp.close();

}

function doFunction(Command,param)
{   
        if(Command=="office_with_bank_betails")
        {   
            var oid=document.getElementById("txtSub_Office_code").value;
            var txtModule_Type="MF009";
            var cr_dr_indi="CR";
            var unspent_OR_col;
            if(document.frmFund_Receipt_Edit_byHO.radRecType[0].checked==true)
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
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
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
            
            if(document.frmFund_Receipt_Edit_byHO.radRecType[0].checked==true)
            var unspent_OR_col='OPR';
            else
            var unspent_OR_col='COL';
            
            var url="../../../../../Fund_Receipt_Create_byHO.view?Command=unspent_OR_col_based_bank&unitID="+unitID+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi+"&unspent_OR_col="+unspent_OR_col;
            //alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
        }
        else if(Command=="load_Voucher_No")
        {  
           clearGeneral_Detail();
           var txtCrea_date= document.frmFund_Receipt_Edit_byHO.txtCrea_date.value
           var  cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
           var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            
            if(txtCrea_date.length!=0)
            {
            var url="../../../../../Fund_Receipt_Edit_byHO.view?Command=load_Voucher_No&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
            cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
            //alert(url)
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
            }         
        }
        else if(Command=="load_Voucher_Details")
        {  
            clearGeneral_Detail();
            var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            var txtCrea_date= document.frmFund_Receipt_Edit_byHO.txtCrea_date.value
            var  txtVoucher_No=document.getElementById("txtVoucher_No").value;
            if(txtVoucher_No!="")
            {
            var url="../../../../../Fund_Receipt_Edit_byHO.view?Command=load_Voucher_Details&txtVoucher_No="+txtVoucher_No+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
            cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;;
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


/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
function handleResponse(req)
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
                office_with_bank_betails(baseResponse);
            }
            else if(Command=="unspent_OR_col_based_bank")
            {
                unspent_OR_col_based_bank(baseResponse);
            }
            else if(Command=="load_Voucher_No")
            {
                load_Voucher_No(baseResponse);
            }
            else if(Command=="load_Voucher_Details")
            {
                load_Voucher_Details(baseResponse);
            }
            else if(Command=="loademp")
            {
                loadEmployee(baseResponse);
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
function loadEmployee(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {  //alert("success");
        var eid=baseResponse.getElementsByTagName("eid")[0].firstChild.nodeValue;
        var ename=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
        var desig=baseResponse.getElementsByTagName("desig")[0].firstChild.nodeValue;
        
        document.getElementById("txtAuth_By").value=eid;
        document.getElementById("Auth_By").value=ename+" - "+desig;
    }
    else 
    {
        var eid=document.getElementById("txtAuth_By").value;
        alert("Employee Id '"+eid+"' doesn't Exists under this office");
        document.getElementById("txtAuth_By").value="";
        document.getElementById("Auth_By").value="";
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
function office_with_bank_betails(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
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
function call_clr()
{
    document.getElementById("txtVoucher_No").value="";  
    clearGeneral_Detail();
}

function clearGeneral_Detail()
{
    document.getElementById("txtCash_Acc_code").value="";
    document.getElementById("txtBankId").value="";
    document.getElementById("txtBankAccountNo").value="";
    document.getElementById("txtBranchId").value="";
    document.getElementById("txtBankName").value="";
    document.getElementById("txtCreditAccCode").value="";
    document.getElementById("txtSubBankAccountNo").value="";
    document.getElementById("txtSubBankId").value="";
    document.getElementById("txtSubBranchId").value="";
    document.getElementById("txtSubBankName").value="";
    document.getElementById("txtAmount").value="";
    document.getElementById("txtRemarks").value="";
    document.getElementById("txtCheque_DD_NO").value="";
    document.getElementById("txtCheque_DD_date").value="";
    document.frmFund_Receipt_Edit_byHO.txtCheque_DD[0].checked=true;
    document.frmFund_Receipt_Edit_byHO.radRecType[0].checked=true;
    document.getElementById("txtReferenceNo").value="";
    document.getElementById("txtReferenceDate").value="";
    document.getElementById("txtSub_Office_code").value="";
    document.getElementById("txtOfficeName").value="";
    
    /*
    document.getElementById("txtAuth_By").value="";
    document.getElementById("Auth_By").value="";
    document.getElementById("txtReferNO_edit").value="";
    document.getElementById("txtReferDate_edit").value="";
    document.getElementById("txtRemak_edit").value=""; 
    */
}
function clrForm()
{
 if(window.confirm("Do you want to clear ALL fields ?"))
 {
   call_clr();
 }
}

/////////////////////////////////////////////   checkNull() by User /////////////////////////////////////////////////////

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
        
    if(document.getElementById("txtVoucher_No").value=="")
    {
        alert("Select the Voucher Number");
        document.getElementById("txtVoucher_No").focus();
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
    
    
 if(document.frmFund_Receipt_Edit_byHO.txtCheque_DD[2].checked == false )
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
    if(document.getElementById("txtSubBranchId").value.length==0 || document.getElementById("txtSubBranchId").value==0)
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
    /*
    if(document.getElementById("txtAuth_By").value.length==0)
    {
        alert("Enter Name of the Authorized person under Modification Details");
        document.getElementById("txtAuth_By").focus();
        return false;    
    }*/
    
return true;
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
function load_Voucher_Details(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
       var R_type=baseResponse.getElementsByTagName("R_type")[0].firstChild.nodeValue;
       var MasHeadCode=baseResponse.getElementsByTagName("MasHeadCode")[0].firstChild.nodeValue;
       var accNo=baseResponse.getElementsByTagName("accNo")[0].firstChild.nodeValue;
       var bk_name=baseResponse.getElementsByTagName("bk_name")[0].firstChild.nodeValue;
       var bk_id=baseResponse.getElementsByTagName("bk_id")[0].firstChild.nodeValue;
       var br_id=baseResponse.getElementsByTagName("br_id")[0].firstChild.nodeValue;
       var Total_amt=baseResponse.getElementsByTagName("Total_amt")[0].firstChild.nodeValue;
       var REF_NO=baseResponse.getElementsByTagName("REF_NO")[0].firstChild.nodeValue;
       var referDate=baseResponse.getElementsByTagName("referDate")[0].firstChild.nodeValue;
       var Remak=baseResponse.getElementsByTagName("Remak")[0].firstChild.nodeValue;
       var Received_offid=baseResponse.getElementsByTagName("Received_offid")[0].firstChild.nodeValue;
       var Received_offName=baseResponse.getElementsByTagName("Received_offName")[0].firstChild.nodeValue;
       var HO_unit_ID=baseResponse.getElementsByTagName("received_frm_HO_unitID")[0].firstChild.nodeValue;
        
       
      document.getElementById("txtCash_Acc_code").value=MasHeadCode;
      document.getElementById("txtBankAccountNo").value=accNo;
      document.getElementById("txtBankName").value=bk_name;
      document.getElementById("txtBankId").value=bk_id;
      document.getElementById("txtBranchId").value=br_id;
       
         
        if(R_type=="U")
            document.frmFund_Receipt_Edit_byHO.radRecType[0].checked=true;
        else
            document.frmFund_Receipt_Edit_byHO.radRecType[1].checked=true;
        
       subAHcode=baseResponse.getElementsByTagName("subAHcode")[0].firstChild.nodeValue;   
       sub_bank_id=baseResponse.getElementsByTagName("sub_bank_id")[0].firstChild.nodeValue;   
       sub_branch_id=baseResponse.getElementsByTagName("sub_branch_id")[0].firstChild.nodeValue;   
       subbankAccNo=baseResponse.getElementsByTagName("subbankAccNo")[0].firstChild.nodeValue;   
       sub_bank_name=baseResponse.getElementsByTagName("sub_bank_name")[0].firstChild.nodeValue;   
       che_or_DD=baseResponse.getElementsByTagName("che_or_DD")[0].firstChild.nodeValue;
       che_DD_no =baseResponse.getElementsByTagName("che_DD_no")[0].firstChild.nodeValue;
       che_DD_date=baseResponse.getElementsByTagName("che_DD_date")[0].firstChild.nodeValue;
        
        if(che_or_DD=="C")
            document.frmFund_Receipt_Edit_byHO.txtCheque_DD[0].checked=true;
        else if (che_or_DD=="D")
            document.frmFund_Receipt_Edit_byHO.txtCheque_DD[1].checked=true;
        else if (che_or_DD=="E")   
            document.frmFund_Receipt_Edit_byHO.txtCheque_DD[2].checked=true;
            
            
        document.getElementById("txtSub_Office_code").value=Received_offid;
        document.getElementById("txtOfficeName").value=Received_offName;
        
        if(HO_unit_ID==0)
        {
            document.getElementById("cmb_HO_acc_unitid").value="";    
        }
        else
            document.getElementById("cmb_HO_acc_unitid").value=HO_unit_ID;
        
             
        if (che_DD_no == 'null' )
        { 
           document.getElementById("txtCheque_DD_NO").value="";
        }
        else        
        {
            document.getElementById("txtCheque_DD_NO").value=che_DD_no;
        }
        
        
        if (che_DD_date == 'null') 
        {
           document.getElementById("txtCheque_DD_date").value="";
        }
        else
        {
           document.getElementById("txtCheque_DD_date").value=che_DD_date;
        }
        
        
        
        document.getElementById("txtCreditAccCode").value=subAHcode;
        document.getElementById("txtSubBankAccountNo").value=subbankAccNo;
        document.getElementById("txtSubBankName").value=sub_bank_name;
        document.getElementById("txtSubBankId").value=sub_bank_id;
        document.getElementById("txtSubBranchId").value=sub_branch_id;
      
      if(REF_NO!="null")
        document.getElementById("txtReferenceNo").value=REF_NO;
      else
        document.getElementById("txtReferenceNo").value="";
      if(referDate!="null")
        document.getElementById("txtReferenceDate").value=referDate;
      else
         document.getElementById("txtReferenceDate").value="";
      document.getElementById("txtAmount").value=Total_amt;
       if(Remak!="null")
         document.getElementById("txtRemarks").value=Remak;
       else
         document.getElementById("txtRemarks").value="";
        
    }
    else
      alert("Failed to retrieve values");
    
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
                 doFunction('load_Voucher_No','null');                 //return true;
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
                    dateCtrl.focus();
                    var cmbSL_Code=document.getElementById("txtVoucher_No");   
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

//edited on 4july2012
function Load_Account_Head_Code_Debit()
{
   
    var AC_Head=document.getElementById("txtCreditAccCode").value;
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
   // alert("ttt");
    AC_Head_Sub=AC_Head.substring(2,4);
        
    if(document.frmFund_Receipt_Edit_byHO.radRecType[0].checked==true)
    var unspent_OR_col='OPR';
    else
    var unspent_OR_col='COL';
  
    var CR_DR_Type="Debit_Load";
  
    var url="../../../../../Load_Bank_Details_for_FR_ByHO.kv?Command=LoadBankDetails&AC_Head_Sub="+AC_Head_Sub+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&unspent_OR_col="+unspent_OR_col+"&CR_DR_Type="+CR_DR_Type;
   //alert(url);
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
          //  alert("flag:"+flag);
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
        
    if(document.frmFund_Receipt_Edit_byHO.radRecType[0].checked==true)
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





