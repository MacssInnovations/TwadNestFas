//alert("gokul")
var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;
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
    var txtModule_Type="MF005";
    var cr_dr_indi="CR";
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
     
    winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAcc_Bank_No.moveTo(250,250);  
    winAcc_Bank_No.focus();
}

function doParentAcc_NO(Acc_Head_Code,Bank_Acc_No,bankid,br_id,B_name)
{
   if(Bank_popup_flag==true)
   {
       document.getElementById("txtCash_Acc_code").value=Acc_Head_Code;
       document.getElementById("txtBankAccountNo").value=Bank_Acc_No;
       document.getElementById("txtBankId").value=bankid;
       document.getElementById("txtBranchId").value=br_id;
       document.getElementById("txtBankName").value=B_name;
       Bank_popup_flag="";
       return true;
   }
  else if(Bank_popup_flag==false)
  {
      
       Bank_popup_flag="";
       return true;
   }
}
window.onunload=function()
{
    if (winAcc_Bank_No && winAcc_Bank_No.open && !winAcc_Bank_No.closed) winAcc_Bank_No.close();
}

/////////////////////////////////////////////   doFunction()  /////////////////////////////////////////////////////

function doFunction(Command,param)
{   
        if(Command=="load_Voucher_No")
        {  
           clearGeneral_Detail();
           var txtCrea_date= document.frmBankPay_PendingBill_Edit.txtCrea_date.value
           var  cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            
            if(txtCrea_date.length!=0)
            {
            var url="../../../../../BankPay_PendingBill_Edit.view?Command=load_Voucher_No&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
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
            var txtCrea_date= document.frmBankPay_PendingBill_Edit.txtCrea_date.value
            var  txtVoucher_No=document.getElementById("txtVoucher_No").value;
            if(txtVoucher_No!="")
            {
            var url="../../../../../BankPay_PendingBill_Edit.view?Command=load_Voucher_Details&txtVoucher_No="+txtVoucher_No+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
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
        {       //alert(req.responseText)
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
             if(Command=="load_Voucher_No")
            {
                load_Voucher_No(baseResponse);
            }
            else if(Command=="load_Voucher_Details")
            {
                load_Voucher_Details(baseResponse);
            }
            
        }
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
    document.getElementById("txtBankAccountNo").value="";
    document.getElementById("txtBankName").value="";
    document.getElementById("txtBankId").value="";
    document.getElementById("txtBranchId").value="";
    document.getElementById("cmbMas_SL_type").value="";
    document.getElementById("cmbMas_SL_Code").value="";                                                    //  "OPTION "  clear_Combo(document.getElementById("cmbMas_SL_Code"));
//    alert('hi');
    document.getElementById("cmbMas_SL_typeDuplicate").value="";        //-- remove if OPTION enabled--
    document.getElementById("cmbMas_SL_CodeDuplicate").value="";        //-- remove if OPTION enabled--
    
    
    //document.getElementById("txtPay_Vou_No").value="";
    //document.getElementById("txtPay_Vou_date").value="";
    //document.getElementById("txtJournal_code").value="";
    document.frmBankPay_PendingBill_Edit.radPart_Amt[1].checked=true;
    document.getElementById("txtPart_Amount").value="";
    document.getElementById("txtAmount").value="";
    document.getElementById("txtRemarks").value="";
    document.getElementById("txtPaid_to").value="";
    
    /*
    document.getElementById("txtAuth_By").value="";
    document.getElementById("Auth_By").value="";
    document.getElementById("txtReferNO_edit").value="";
    document.getElementById("txtReferDate_edit").value="";
    document.getElementById("txtRemak_edit").value=""; 
    */
    //clearall();
    document.getElementById("txtCheque_DD_NO").value="";
    document.getElementById("txtCheque_DD_date").value="";
   
    
    
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

/////////////////////////////////////////////   checkNull() by User /////////////////////////////////////////////////////

function checkNull()
{
   var tbody=document.getElementById("grid_body");
   
if(document.getElementById("cmbAcc_UnitCode").value=="")
{
    alert("Select the Account Unit code");
    //document.getElementById("txtAcc_HeadDesc").focus();
    return false;    
}
if(document.getElementById("cmbOffice_code").value=="")
{
    alert("Select the Office Code");
    //document.getElementById("cmbOffice_code").focus();
    return false;
}
if(document.getElementById("txtCrea_date").value.length==0)
{
    alert("Enter the Date of Creation");
    //document.getElementById("txtCrea_date").focus();
    return false;    
}
 if(document.getElementById("txtVoucher_No").value=="")
{
    alert("Select the Voucher Number");
    //document.getElementById("txtVoucher_No").focus();
    return false;    
}
if(document.getElementById("txtCash_Acc_code").value.length==0 || document.getElementById("txtCash_Acc_code").value==0)
{
    alert("Enter the Operation  A/c Code");
    //document.getElementById("txtCash_Acc_code").focus();
    return false;
}

if(document.getElementById("txtBankId").value.length==0 || document.getElementById("txtBankId").value==0)
{
    alert("Bank Id not populated in General");
    //document.getElementById("txtAmount").focus();
    return false;    
}

if(document.getElementById("txtBranchId").value.length==0 || document.getElementById("txtBranchId").value==0)
{
    alert("Branch Id not populated in General");
    //document.getElementById("txtAmount").focus();
    return false;    
}

if(document.getElementById("txtBankAccountNo").value.length==0 || document.getElementById("txtBankAccountNo").value==0)
{
    alert("Enter the Account Number");
    //document.getElementById("txtRecei_from").focus();
    return false;    
}
if(document.getElementById("cmbMas_SL_type").value=="")
{
    //if(document.getElementById("cmbMas_SL_Code").value=="")
    //{
    alert("Select The Sub Ledger Type in General");
    return false;
    //}
}
if(document.getElementById("cmbMas_SL_type").value!="")
{
    if(document.getElementById("cmbMas_SL_Code").value=="")
    {
    alert("Select The Sub Ledger Code in General");
    return false;
    }
}
if(document.frmBankPay_PendingBill_Edit.radPart_Amt[0].checked==true)
{
 if(document.getElementById("txtPart_Amount").value.length==0)
 {
   alert("Enter the Part Amount ");
   return false;
 }
}
if(document.getElementById("txtPaid_to").value.length==0)
{
    alert("Enter the value in Paid to Field in General");
    //document.getElementById("txtPaid_to").focus();
    return false;    
}
    /*
    if(document.getElementById("txtPay_Vou_No").value.length==0)
    {
        alert("Select Journal Voucher Number from Pending Bills");
        //document.getElementById("txtPaid_to").focus();
        return false;    
    }
    if(document.getElementById("txtPay_Vou_date").value.length==0)
    {
        alert("Select Journal Voucher Date from Pending Bills");
        //document.getElementById("txtPaid_to").focus();
        return false;    
    }
    */

if(document.getElementById("txtAmount").value.length==0)
{
    alert("Enter the Total Amount in General");
    //document.getElementById("txtAmount").focus();
    return false;    
}





if(document.frmBankPay_PendingBill_Edit.txtCheque_DD[2].checked == false)
 { 
    if(document.getElementById("txtCheque_DD_NO").value.length==0)
    {
         alert("Enter the Cheque/DD number");         
      return false;
    }
    if(document.getElementById("txtCheque_DD_date").value.length==0)
    {
         alert("Enter the Cheque/DD Date");         
        return false;
    }    
}    
    
    
    /*
    if(document.getElementById("txtAuth_By").value.length==0)
    {
         alert("Enter Name of the Authorized person under Modification Details");
        //document.getElementById("txtReferNO_edit").focus();
        return false;    
    }
    */
if(tbody.rows.length==0)
{
    alert("Enter the Details Part");
    //document.getElementById("txtAmount").focus();
    return false; 
}
if(tbody.rows.length>0)
{
        var check_amt=0;
        var chkCR_DB=0;
        rows=tbody.getElementsByTagName("tr");
        for(i=0;i<rows.length;i++)
        {
            var cells=rows[i].cells;
            //if(cells.item(4).lastChild.nodeValue=='CR')
              //chkCR_DB=parseFloat(chkCR_DB) + parseFloat(cells.item(7).lastChild.nodeValue); //check_amt=check_amt;      //check_amt=parseFloat(check_amt) - parseFloat(cells.item(7).lastChild.nodeValue);  // ** removed bcoz just we need to check CR & DR equal or not
           // else
           //alert('hi');
           //alert(parseFloat(cells.item(5).lastChild.nodeValue));
           //alert(parseFloat(cells.item(7).lastChild.nodeValue))
            check_amt=parseFloat(check_amt) + parseFloat(cells.item(5).lastChild.nodeValue);
           
           /* if(cells.item(5).lastChild.nodeValue.length==0)
            {
            alert("Cheque/DD number missing in Details part Entries");
            return false;
            }*/
            //alert(cells.item(2).firstChild.value)
        }
       /* if(parseFloat(chkCR_DB)!=parseFloat(check_amt))
        {
            alert("Credit and Debit Amount doesn't Tally in details.. Difference " +(parseFloat(chkCR_DB)-parseFloat(check_amt)));
            return false;
        }*/
        //if(parseFloat(document.getElementById("txtAmount").value)!=parseFloat(chkCR_DB))
        if(parseFloat(document.getElementById("txtAmount").value)!=parseFloat(check_amt))
        {
        alert("Amount doesn't Tally.. Difference " +(parseFloat(document.getElementById("txtAmount").value)-parseFloat(check_amt)))
        return false;
        }
}
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
       var MasHeadCode=baseResponse.getElementsByTagName("MasHeadCode")[0].firstChild.nodeValue;
       var accNo=baseResponse.getElementsByTagName("accNo")[0].firstChild.nodeValue;
   //    alert("bk_name:::"+baseResponse.getElementsByTagName("bk_name")[0].firstChild.nodeValue);
       var bk_name=baseResponse.getElementsByTagName("bk_name")[0].firstChild.nodeValue;

       var bk_id=baseResponse.getElementsByTagName("bk_id")[0].firstChild.nodeValue;
       var br_id=baseResponse.getElementsByTagName("br_id")[0].firstChild.nodeValue;
       var Total_amt=baseResponse.getElementsByTagName("Total_amt")[0].firstChild.nodeValue;
       var Mas_paid=baseResponse.getElementsByTagName("Mas_paid")[0].firstChild.nodeValue;
       var Remak=baseResponse.getElementsByTagName("Remak")[0].firstChild.nodeValue;
       
        var partPay=baseResponse.getElementsByTagName("partPay")[0].firstChild.nodeValue;
       var partAmt=baseResponse.getElementsByTagName("partAmt")[0].firstChild.nodeValue;
      // alert("tttttt:::");
     
      if(partPay=="Y")
      document.frmBankPay_PendingBill_Edit.radPart_Amt[0].checked=true;
      else if(partPay=="N")
      document.frmBankPay_PendingBill_Edit.radPart_Amt[1].checked=true;
      
      if(partPay=="Y")
      {
        document.getElementById("txtPart_Amount").disabled=false;
        document.getElementById("txtPart_Amount").value=partAmt;
      }
       //document.getElementById("txtPay_Vou_No").value=pay_vou_no;
       //document.getElementById("txtPay_Vou_date").value=pay_vou_date;
       //document.getElementById("txtJournal_code").value=jour_code;
      alert("MasHeadCode:::"+MasHeadCode);
      document.getElementById("txtCash_Acc_code").value=MasHeadCode
      document.getElementById("txtBankAccountNo").value=accNo;
      document.getElementById("txtBankName").value=bk_name;
      document.getElementById("txtBankId").value=bk_id;
      document.getElementById("txtBranchId").value=br_id;
      
       document.getElementById("txtAmount").value=Total_amt;
      if(Mas_paid!="null")
      document.getElementById("txtPaid_to").value=Mas_paid;
      else
      document.getElementById("txtPaid_to").value="";
      
           
        var sltype =new Array(4);
        sltype[11]="Contractors";
        sltype[1]="Supplier";
        sltype[2]="Firms";
        sltype[9]="Others departments";
        
      
       var Mas_SL_type=baseResponse.getElementsByTagName("Mas_SL_type")[0].firstChild.nodeValue;
       var Mas_SL_code=baseResponse.getElementsByTagName("Mas_SL_code")[0].firstChild.nodeValue;
       
       if(Mas_SL_type!=0)
       {
           for(i=1;i<=13;i++)
           {
               if(Mas_SL_type==i)
               {
               
               document.getElementById("cmbMas_SL_type").value=i;
               document.getElementById("cmbMas_SL_typeDuplicate").value=sltype[i];      //-- remove if OPTION enabled--
               break;
               }
           }
            var items_SLcode=new Array();
            var items_SLdesc=new Array();
            var Mas_SLCODE=baseResponse.getElementsByTagName("cid");
            
            for(var k=0;k<Mas_SLCODE.length;k++)
            {
                items_SLcode[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
                items_SLdesc[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
                if(items_SLcode[k]==Mas_SL_code)
                {
                
                 document.getElementById("cmbMas_SL_Code").value=items_SLcode[k];
                 document.getElementById("cmbMas_SL_CodeDuplicate").value=items_SLdesc[k];      //-- remove if OPTION enabled--
                 break;
                }
            }
       }
      
      /*  // "OPTION" Start here  
      
       var Mas_SL_type=baseResponse.getElementsByTagName("Mas_SL_type")[0].firstChild.nodeValue;
       var Mas_SL_code=baseResponse.getElementsByTagName("Mas_SL_code")[0].firstChild.nodeValue;
       if(Mas_SL_type!=0)
       document.getElementById("cmbMas_SL_type").value=Mas_SL_type;
       
       if(Mas_SL_type!=0)
      {
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
          
            document.getElementById("cmbMas_SL_Code").value=Mas_SL_code;    //set from getting from servlet
            
      }
      */  // "OPTION " End here
      
       if(Remak!="null")
         document.getElementById("txtRemarks").value=Remak;
        else
        document.getElementById("txtRemarks").value="";
       
       
       var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
        
        var AHcode=baseResponse.getElementsByTagName("AHcode");
        var items=new Array();
        
        for(var k=0;k<AHcode.length;k++)
        {
        items[0]=baseResponse.getElementsByTagName("AHcode")[k].firstChild.nodeValue;   
        items[1]=baseResponse.getElementsByTagName("AHdesc")[k].firstChild.nodeValue;   
        
        items[2]=baseResponse.getElementsByTagName("CR_DR_ind")[k].firstChild.nodeValue;
        items[3]=baseResponse.getElementsByTagName("SL_Type")[k].firstChild.nodeValue;
        if(items[3]==0)
        items[3]="";
         
        items[4]=baseResponse.getElementsByTagName("SL_Desc")[k].firstChild.nodeValue;
        if(items[4]=="null")
        items[4]="";
        
        items[5]=baseResponse.getElementsByTagName("SL_Code")[k].firstChild.nodeValue;
        if(items[5]==0)
        items[5]="";
        
        items[6]=baseResponse.getElementsByTagName("desc_type")[k].firstChild.nodeValue;
        if(items[6]=="null")
        items[6]="";
        
        items[7]=baseResponse.getElementsByTagName("che_or_DD")[k].firstChild.nodeValue;
        items[8]=baseResponse.getElementsByTagName("che_DD_no")[k].firstChild.nodeValue;
        if(items[8]=="null")
        {
          items[8]="";
        }        
        
        items[9]=baseResponse.getElementsByTagName("che_DD_date")[k].firstChild.nodeValue;
        
        if(items[9]=="null")
        {
          items[9]="";
        }        
        
        
       if(items[7]=="C")
       {
            document.frmBankPay_PendingBill_Edit.txtCheque_DD[0].checked=true;
       }
       else if (items[7]=="D")
       {
         document.frmBankPay_PendingBill_Edit.txtCheque_DD[1].checked=true;       
       }
       else if (items[7]=="E")  
       {
         document.frmBankPay_PendingBill_Edit.txtCheque_DD[2].checked=true;
       }
       
       
       
        document.getElementById("txtCheque_DD_NO").value=items[8];
        document.getElementById("txtCheque_DD_date").value=items[9];
       
        
        
        
        items[10]=baseResponse.getElementsByTagName("billno")[k].firstChild.nodeValue;//document.getElementById("txtBill_NO").value;
        items[11]=baseResponse.getElementsByTagName("billdate")[k].firstChild.nodeValue;//document.getElementById("txtBill_date").value;
        items[12]=baseResponse.getElementsByTagName("billtype")[k].firstChild.nodeValue;//document.getElementById("txtBill_type").value;
        
        items[13]=baseResponse.getElementsByTagName("Agree_no")[k].firstChild.nodeValue;
        items[14]=baseResponse.getElementsByTagName("Agree_date")[k].firstChild.nodeValue;
        
        items[15]=baseResponse.getElementsByTagName("sub_paidto")[k].firstChild.nodeValue;
        items[16]=baseResponse.getElementsByTagName("sub_amount")[k].firstChild.nodeValue;
        items[17]=baseResponse.getElementsByTagName("sub_part")[k].firstChild.nodeValue;
        
       items[18]=baseResponse.getElementsByTagName("pay_vou_no")[k].firstChild.nodeValue;
       items[19]=baseResponse.getElementsByTagName("pay_vou_date")[k].firstChild.nodeValue;
       //items[20]=baseResponse.getElementsByTagName("jour_code")[k].firstChild.nodeValue;
     
        
        
        if(items[8]=="null")
        items[8]="";
        if(items[9]=="null")
        items[9]="";
        if(items[10]=="null")
        items[10]="";
        if(items[11]=="null")
        items[11]="";
         if(items[12]=="null")
        items[12]="";
         if(items[13]=="null")
        items[13]="";
         if(items[14]=="null")
        items[14]="";

        if(items[15]=="null")
        items[15]="";
         if(items[17]=="null")
        items[17]="";
        
        tbody=document.getElementById("grid_body");
        var mycurrent_row=document.createElement("TR");
        seq=seq+1;
        mycurrent_row.id=seq;
        //alert("row ID"+mycurrent_row.id);
      /*  var cell=document.createElement("TD");
        var anc=document.createElement("A");
        var url="javascript:loadTable('"+mycurrent_row.id+"')";
        anc.href=url;
        var txtedit=document.createTextNode("EDIT");
        anc.appendChild(txtedit);
        cell.appendChild(anc);
        mycurrent_row.appendChild(cell);
        var i=0;*/
        var cell2;
        
         /*  cell2=document.createElement("TD");
                        cell2.align="CENTER";
                               var vou_type=document.createElement("input");
                              vou_type.type="hidden";
                              vou_type.name="vou_type";
                              vou_type.value=items[20];
                             // cell2.style.display="none";
                              cell2.appendChild(vou_type);
                               var currentText=document.createTextNode(items[20]);
                              cell2.appendChild(currentText);
                               mycurrent_row.appendChild(cell2);
              */      
                  cell2=document.createElement("TD");
                              var vou_date=document.createElement("input");
                              vou_date.type="hidden";
                              vou_date.name="vou_date";
                              vou_date.value=items[19];
                              cell2.appendChild(vou_date);
                               var currentText=document.createTextNode(items[19]);
                              cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                            
                    cell2=document.createElement("TD");
                        cell2.align="CENTER";
                             var Voucher_no=document.createElement("input");
                              Voucher_no.type="hidden";
                              Voucher_no.name="Voucher_no";
                              Voucher_no.value=items[18];
                              cell2.appendChild(Voucher_no);
                              var currentText=document.createTextNode(items[18]);
                              cell2.appendChild(currentText);
                               mycurrent_row.appendChild(cell2);
                               
                   /*    cell2=document.createElement("TD");                             // added on 27th April 07
                          cell2.align="CENTER";
                               var vouSL_NO_new=document.createElement("input");
                              vouSL_NO_new.type="hidden";
                              vouSL_NO_new.name="vouSL_NO_new";
                              vouSL_NO_new.value=items[21];
                              cell2.style.display="none";
                              cell2.appendChild(vouSL_NO_new);
                               var currentText=document.createTextNode(items[21]);
                              cell2.appendChild(currentText);
                               mycurrent_row.appendChild(cell2);
                               
                        */
                            
           
                cell2=document.createElement("TD");
    
                     var H_code=document.createElement("input");
                      H_code.type="hidden";
                      H_code.name="H_code";
                      H_code.value=items[0];
                      cell2.appendChild(H_code);
                      var currentText=document.createTextNode(items[0]+"-"+items[1]);
                      cell2.appendChild(currentText);
                       mycurrent_row.appendChild(cell2);
                       
                /* cell2=document.createElement("TD");
                      var CR_DR_type=document.createElement("input");
                      CR_DR_type.type="hidden";
                      CR_DR_type.name="CR_DR_type";
                      CR_DR_type.value=items[2];
                      cell2.style.display="none";
                      cell2.appendChild(CR_DR_type);
                       var currentText=document.createTextNode(items[2]);
                      cell2.appendChild(currentText);
                    mycurrent_row.appendChild(cell2);
                 */           
             cell2=document.createElement("TD");
                   var SL_type=document.createElement("input");
                  SL_type.type="hidden";
                  SL_type.name="SL_type";
                  SL_type.value=items[3];
                  cell2.appendChild(SL_type);
                   var currentText=document.createTextNode(items[4]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
                   
            cell2=document.createElement("TD");
                  var SL_code=document.createElement("input");
                  SL_code.type="hidden";
                  SL_code.name="SL_code";
                  SL_code.value=items[5];
                  cell2.appendChild(SL_code);
                   var currentText=document.createTextNode(items[6]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
         /*   cell2=document.createElement("TD"); 
                  var Cheque_DD=document.createElement("input");
                  Cheque_DD.type="hidden";
                  Cheque_DD.name="Cheque_DD";
                  Cheque_DD.value=items[7];
                  cell2.appendChild(Cheque_DD);
                                                                     within the star indicates to avoid the column added in b/w two cells
                //var currentText=document.createTextNode(items[7]);          
                //cell2.appendChild(currentText);
                //mycurrent_row.appendChild(cell2);
             //cell2=document.createElement("TD");  
       
                 var Cheque_DD_NO=document.createElement("input");
                  Cheque_DD_NO.type="hidden";
                  Cheque_DD_NO.name="Cheque_DD_NO";
                  Cheque_DD_NO.value=items[8];
                  cell2.appendChild(Cheque_DD_NO);
                  var currentText=document.createTextNode(items[8]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
            cell2=document.createElement("TD");
                 var Cheque_DD_date=document.createElement("input");
                  Cheque_DD_date.type="hidden";
                  Cheque_DD_date.name="Cheque_DD_date";
                  Cheque_DD_date.value=items[9];
                  cell2.appendChild(Cheque_DD_date);
                  var currentText=document.createTextNode(items[9]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
        */         
             cell2=document.createElement("TD");
                  var Bill_NO=document.createElement("input");
                  Bill_NO.type="hidden";
                  Bill_NO.name="Bill_NO";
                  Bill_NO.value=items[10];
                  cell2.appendChild(Bill_NO);
                                                                                       //    var currentText=document.createTextNode(items[7]);
                                                                                       //    cell2.appendChild(currentText);
                                                                                        //mycurrent_row.appendChild(cell2);
            
                                                                                                     //cell2=document.createElement("TD");
                  var Bill_date=document.createElement("input");
                  Bill_date.type="hidden";
                  Bill_date.name="Bill_date";
                  Bill_date.value=items[11];
                  cell2.appendChild(Bill_date);
                                                                                                  // var currentText=document.createTextNode(items[8]);
                                                                                                  //cell2.appendChild(currentText);
                                                                                                    // mycurrent_row.appendChild(cell2);
                
                
                                                                                                // cell2=document.createElement("TD"); 
                  var Bill_type=document.createElement("input");
                  Bill_type.type="hidden";
                  Bill_type.name="Bill_type";
                  Bill_type.value=items[12];
                  cell2.appendChild(Bill_type);
                  
                                                                                                //  cell2=document.createElement("TD");
                  var Agree_No=document.createElement("input");
                  Agree_No.type="hidden";
                  Agree_No.name="Agree_No";
                  Agree_No.value=items[13];
                  cell2.appendChild(Agree_No);
                    
                  var Agree_date=document.createElement("input");
                  Agree_date.type="hidden";
                  Agree_date.name="Agree_date";
                  Agree_date.value=items[14];
                  cell2.appendChild(Agree_date);
                             
                             
                /*  var sub_paid=document.createElement("input");
                  sub_paid.type="hidden";
                  sub_paid.name="sub_paid";
                  sub_paid.value=items[15];
                  cell2.appendChild(sub_paid);
                */
                  var sl_amt=document.createElement("input");
                  sl_amt.type="hidden";
                  sl_amt.name="sl_amt";
                  sl_amt.value=items[16];
                  cell2.appendChild(sl_amt);

                  var particular=document.createElement("input");           // Particulars Added to grid b4 the Amount Text Node but after  amount text box    
                  particular.type="hidden";
                  particular.name="particular";
                  particular.value=items[17];
                  cell2.appendChild(particular);

                  var currentText=document.createTextNode(items[16]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
       
        tbody.appendChild(mycurrent_row);
        
        }
    }
    
}

function clear_Combo(combo)
{
        //alert(combo.id)
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


///////////////////////////////////////////    TB_checking and Calender control return value handling



function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
    if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
    {
             call_clr();
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
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
                    document.getElementById("txtVoucher_No").value="";
               }
        }
    }
}


function enable_partamount(val)
{
   if(val=="Y")
     {
     document.getElementById("txtPart_Amount").disabled=false;
     }
    else
    {
        document.getElementById("txtPart_Amount").disabled=true;
        document.getElementById("txtPart_Amount").value="";
    }
}
 

///////////////////////////////////////////////////////////////////////////////// 
// Checking Cheque/DD Number. Whether already exits or not  
////////////////////////////////////////////////////////////////////////////////

 function check_dd_cheque()
 {
      var cheque_no= document.getElementById("txtCheque_DD_NO").value;
      var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
      var url="../../../../../BankPay_PendingBill_Create.view?Command=check_cheque_no&cheque_no="+cheque_no+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
      var req=getTransport();
      req.open("GET",url,true); 
      req.onreadystatechange=function()
         {
            handleResponse_cheque_no(req);
         }   
      req.send(null); 
 }
 
function handleResponse_cheque_no(req) 
{ 
   
    if(req.readyState==4)
    {
       
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="Success")
              {
                 var cheque_no = baseResponse.getElementsByTagName("cheq_no");   
                 var max=cheque_no.length;
                 if(max > 1 )
                   max--;
                   
                   
                 my_window= window.open ("","mywindow1","height=150,width=420,scrollbars=1,resizable=1");
                 my_window.document.write('<html><head><title>Cheque Number Checking</title><body>');
                 my_window.document.write('<table border=1 width=380><tr><th align=center colspan=5>Cheque Number '+baseResponse.getElementsByTagName("cheq_no")[0].firstChild.nodeValue+' already exits '); 
                 my_window.document.write('<tr> <td align=center>Voucher No.</td> <td align=center>Amount</td>  <td align=center>Created by Module</td>  <td align=center>CB Year</td>  <td align=center>CB Month</td> </tr>');
                 my_window.document.write('<tr>');
                          
                 for(var k=0;k<max;k++)
                 {
                 my_window.document.write('<tr><td align=center>'+baseResponse.getElementsByTagName("vo_no")[k].firstChild.nodeValue); 
                 my_window.document.write('<td align=center>'+baseResponse.getElementsByTagName("amount")[k].firstChild.nodeValue); 
                 my_window.document.write('<td align=center>'+baseResponse.getElementsByTagName("crm")[k].firstChild.nodeValue); 
                 my_window.document.write('<td align=center>'+baseResponse.getElementsByTagName("cbyear")[k].firstChild.nodeValue); 
                 my_window.document.write('<td align=center>'+baseResponse.getElementsByTagName("cbmonth")[k].firstChild.nodeValue); 
                 
                 }
                 my_window.document.write('</table></body></html>');
                 
                 my_window.moveTo(200,250);
                 document.getElementById("txtCheque_DD_NO").value="";
                 
              }
       }
   }    
}

//////////////////////////////////////////////////////////////////////////////////////
// Remarks Length Check
//////////////////////////////////////////////////////////////////////////////////////

function Remarks_Lenght_Check(param, value)
{
   if(param=='remarks')
   {
     if (value.length >= 240 )
       {
         alert("Remarks should be less than 250 Characters ");
       }
   }
}
chequeRange=function(){	
	if((document.frmBankPay_PendingBill_Edit.txtCheque_DD[0].checked==true)&&(document.getElementById('txtCheque_DD_NO').value!="")){
		//alert("test "+document.frmBankPay_PendingBill_create.txtCheque_DD[0].checked);
		var accunitId=document.getElementById('cmbAcc_UnitCode').value;
		var officeId=document.getElementById('cmbOffice_code').value;
		var chequeNo=document.getElementById('txtCheque_DD_NO').value;
		var accountNo=document.getElementById('txtBankAccountNo').value;
                var txtCrea_date=document.getElementById('txtCrea_date').value;
                var dated=txtCrea_date.split("/");
                if(dated[2]==2011 || dated[2]>2011){
               // if(dated[1]==11 || dated[1]>11){
		var url="../../../../../BankPay_PendingBill_Create.view?Command=chequeRange&chequeNo="+chequeNo+"&accunitId="+accunitId+
				"&officeId="+officeId+"&accountNo="+accountNo;
        var req=getTransport();
        req.open("POST",url,true); 
        req.onreadystatechange=function(){
        	processResponse(req);
        };   
        req.send(null);
	//}
        }
	}	
};
function processResponse(req){
	 if(req.readyState==4)
    { 
        if(req.status==200)
        {  
       	 var rangeResponse=req.responseXML.getElementsByTagName("response")[0];
     	   chequeRangeResponse(rangeResponse);
        }
    }
}
function chequeRangeResponse(response){
	var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="fail"){
		alert("Cheque No does not exist Please Fill the Cheque No Details in the ChequeBook Master");
		document.getElementById('txtCheque_DD_NO').value="";
	}
}
 