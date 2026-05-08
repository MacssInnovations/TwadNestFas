var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;
var job_flag;
var common_AHead_code_flag="";
var emp_flag;


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
        winAcc_Bank_No=null;
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
    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
    if (winemp && winemp.open && !winemp.closed) winemp.close();
    if (winjob && winjob.open && !winjob.closed) winjob.close();
    if (winAcc_Bank_No && winAcc_Bank_No.open && !winAcc_Bank_No.closed) winAcc_Bank_No.close();
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
            //alert("Flag----->"+flag);
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






/////////////////////////////////////////////   load Table() /////////////////////////////////////////////////////

function loadTable(scod)
{
        com_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
        clearall();
       // document.FasAcc_Headform.cmdadd.disabled=true;
       //document.getElementById("txtAcc_HeadCode").readOnly=true;                // do not change the Account Head 
       //text_field.readOnly=true;
        var r=document.getElementById(scod);
        var rcells=r.cells;
        try {document.getElementById("txtAcc_HeadCode").value=rcells.item(1).firstChild.value;}catch(e){}
        try{common_cmbSL_type=rcells.item(3).firstChild.value;} catch(e){common_cmbSL_type=""}
        //alert("U"+common_cmbSL_type+"U")
        try{common_cmbSL_Code=rcells.item(4).firstChild.value;} catch(e){common_cmbSL_Code=""} 
       
         if(common_cmbSL_type==5)
         {
              document.getElementById("txtOfficeID_trs").value=common_cmbSL_Code;
              job_flag=false;
              //doFunction('office',common_cmbSL_Code);
              //doFunction('Load_SL_Code',);
         }
         if(common_cmbSL_type==7)
         {
              document.getElementById("txtEmpID_trs").value=common_cmbSL_Code;
              emp_flag=false;
              //doFunction('office',common_cmbSL_Code);
              //doFunction('Load_SL_Code',);
         }
                doFunction('checkCode','null');
                doFunction('Load_SL_Code',common_cmbSL_type);
        
         if(rcells.item(2).firstChild.value=="CR")
         document.frmBankPay_NilPayment_Create.rad_sub_CR_DR[0].checked=true;
         else if(rcells.item(2).firstChild.value=="DR")
         document.frmBankPay_NilPayment_Create.rad_sub_CR_DR[1].checked=true;
         
        
      try{document.getElementById("txtBill_NO").value=rcells.item(5).firstChild.value;}catch(e){}
       try{document.getElementById("txtBill_date").value=rcells.item(6).firstChild.value;}catch(e){}
       
        try{document.getElementById("txtBill_type").value=rcells.item(7).firstChild.value;}catch(e){}
        var nex=rcells.item(7).firstChild.nextSibling  
       
      //  try{document.getElementById("txtsub_Paid_to").value=nex.value;}catch(e){}
       // nex=nex.nextSibling;
       try{document.getElementById("txtReferenceNo").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
       try{document.getElementById("txtParticular").value=nex.value;}catch(e){}
       
       
    document.frmBankPay_NilPayment_Create.cmdupdate.style.display='block';
    document.frmBankPay_NilPayment_Create.cmddelete.disabled=false;
    document.frmBankPay_NilPayment_Create.cmdadd.style.display='none';
}


/////////////////////////////////////////////   ADD & UPDATE & DELETE /////////////////////////////////////////////////////
function ADD_GRID()
{
       if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
        alert("Enter A/c Head Code");
        document.getElementById("txtAcc_HeadCode").focus();
        return false;
        }
         if(document.frmBankPay_NilPayment_Create.rad_sub_CR_DR[0].checked==true)
        {
            alert("Credit amount not allowed");
            return false;
        }
        
       
       /* 
        if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
        {
          if(window.confirm("You have not selected Sub-Ledger Type \n Do you want to select it,click 'OK'?"))
          {
             if(document.getElementById("cmbSL_type").value=="")
              {
                alert("Select a Sub-Ledger Type");
                return false;
               } 
          }
          else
          {
             
          }
          
        }
        if(document.getElementById("cmbSL_type").value!="")
        {
          if(document.getElementById("cmbSL_Code").value=="")
            {
            alert("Select The Sub Ledger Code")       ;
            document.getElementById("cmbSL_Code").focus();
            return false;
            }
        }
       */
       
       //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      
       if(document.getElementById("txtAcc_HeadDesc").value=="")
       {
            alert("Please Wait Account Head is Loading .......................");            
            return false;        
       }       
       
       if ( isMan.account_head_status) 
       {
        
        if(document.getElementById("cmbSL_type").value=="")
        {
            alert("Select The Sub Ledger Type")       ;
            document.getElementById("cmbSL_type").focus();
            return false;        
        }
        
        if(document.getElementById("cmbSL_Code").value=="")
        {
            alert("Select The Sub Ledger Code")       ;
            document.getElementById("cmbSL_Code").focus();
            return false;        
        }        
       }
       else
       {
            if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
            {
                if(window.confirm("You have not selected Sub-Ledger Type \n Do you want to select it,click 'OK'?"))
                    {
                        if(document.getElementById("cmbSL_type").value=="")
                            {
                                alert("Select a Sub-Ledger Type");
                                return false;
                            } 
                    }
                    else
                    {
                     
                    }          
            }
            if(document.getElementById("cmbSL_type").value!="")
            {
                if(document.getElementById("cmbSL_Code").value=="")
                    {
                        alert("Select The Sub Ledger Code")       ;
                        document.getElementById("cmbSL_Code").focus();
                        return false;
                    }
            }       
        }
       
       
     //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      
         
        /*
        if(document.getElementById("txtBill_NO").value.length==0)
        {
            alert("Enter the Bill number");
             document.getElementById("txtBill_NO").focus();
            return false;
        }
        if(document.getElementById("txtBill_date").value.length==0)
        {
            alert("Enter the Bill Date");
             document.getElementById("txtBill_date").focus();
            return false;
        }
        if(document.getElementById("txtBill_type").value.length==0)
        {
            alert("Enter the Bill Type");
             document.getElementById("txtBill_type").focus();
            return false;
        }
       if(document.getElementById("txtsub_Paid_to").value.length==0)
        {
            alert("Enter the value in Paid to Field ");
            document.getElementById("txtsub_Paid_to").focus();
            return false;    
        }
        */
        if(document.getElementById("txtReferenceNo").value.length==0)
        {
            alert("Enter the Reference Number ");
            document.getElementById("txtReferenceNo").focus();
            return false;    
        }
        
        var items=new Array();
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;
        if(document.frmBankPay_NilPayment_Create.rad_sub_CR_DR[0].checked==true)
          items[2]=document.frmBankPay_NilPayment_Create.rad_sub_CR_DR[0].value;
        else if(document.frmBankPay_NilPayment_Create.rad_sub_CR_DR[1].checked==true)
          items[2]=document.frmBankPay_NilPayment_Create.rad_sub_CR_DR[1].value;
        
        items[3]=document.getElementById("cmbSL_type").value;
        if(document.getElementById("cmbSL_type").value=="")
        {
        //items[4]="Not Available";
        items[4]="";                //document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        }
        else
        items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        
        items[5]=document.getElementById("cmbSL_Code").value;
        if(document.getElementById("cmbSL_Code").value=="")
        {
        items[6]="";                //"Not Available";
        }
        else
        items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
        
        items[7]=document.getElementById("txtBill_NO").value;
        items[8]=document.getElementById("txtBill_date").value;
        items[9]=document.getElementById("txtBill_type").value;
        
       // items[10]=document.getElementById("txtsub_Paid_to").value;
        
        items[11]=document.getElementById("txtReferenceNo").value;
        items[12]=document.getElementById("txtParticular").value;
        
        tbody=document.getElementById("grid_body");
        var mycurrent_row=document.createElement("TR");
        seq=seq+1;
        mycurrent_row.id=seq;
        //alert("row ID"+mycurrent_row.id);
        var cell=document.createElement("TD");
        var anc=document.createElement("A");
        var url="javascript:loadTable('"+mycurrent_row.id+"')";
        anc.href=url;
        var txtedit=document.createTextNode("EDIT");
        anc.appendChild(txtedit);
        cell.appendChild(anc);
        mycurrent_row.appendChild(cell);
        var i=0;
        var cell2;
        
            cell2=document.createElement("TD");

                  var H_code=document.createElement("input");
                  H_code.type="hidden";
                  H_code.name="H_code";
                  H_code.value=items[0];
                  cell2.appendChild(H_code);
                  var currentText=document.createTextNode(items[0]+"-"+items[1]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
                   
             cell2=document.createElement("TD");
                  var CR_DR_type=document.createElement("input");
                  CR_DR_type.type="hidden";
                  CR_DR_type.name="CR_DR_type";
                  CR_DR_type.value=items[2];
                  cell2.appendChild(CR_DR_type);
                   var currentText=document.createTextNode(items[2]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
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
                
            cell2=document.createElement("TD");
                  var Bill_NO=document.createElement("input");
                  Bill_NO.type="hidden";
                  Bill_NO.name="Bill_NO";
                  Bill_NO.value=items[7];
                  cell2.appendChild(Bill_NO);
                   var currentText=document.createTextNode(items[7]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
            
             cell2=document.createElement("TD");
                  var Bill_date=document.createElement("input");
                  Bill_date.type="hidden";
                  Bill_date.name="Bill_date";
                  Bill_date.value=items[8];
                  cell2.appendChild(Bill_date);
                   var currentText=document.createTextNode(items[8]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                
             cell2=document.createElement("TD"); 
               var Bill_type=document.createElement("input");
                  Bill_type.type="hidden";
                  Bill_type.name="Bill_type";
                  Bill_type.value=items[9];
                  cell2.appendChild(Bill_type);
                             
                             
             /*  
                  var sub_paid=document.createElement("input");
                  sub_paid.type="hidden";
                  sub_paid.name="sub_paid";
                  sub_paid.value=items[10];
                  cell2.appendChild(sub_paid);
            */
                 var referenceNo=document.createElement("input");
                  referenceNo.type="hidden";
                  referenceNo.name="referenceNo";
                  referenceNo.value=items[11];
                  cell2.appendChild(referenceNo);

                  var particular=document.createElement("input");           // Particulars Added to grid b4 the Amount Text Node but after  amount hidden box    
                  particular.type="hidden";
                  particular.name="particular";
                  particular.value=items[12];
            //    particular.style.display='none';
                  cell2.appendChild(particular);

                  var currentText=document.createTextNode(items[11]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
       
        tbody.appendChild(mycurrent_row);
        clear_main_fields();
}

function clear_main_fields()
{
     document.getElementById("offlist_div_trans").style.display='none';
     document.getElementById("emplist_div_trans").style.display='none';
    
    document.getElementById("txtAcc_HeadCode").value="";
    document.getElementById("txtAcc_HeadDesc").value="";
    document.frmBankPay_NilPayment_Create.rad_sub_CR_DR[1].checked=true;
    document.getElementById("cmbSL_type").value="";
    document.getElementById("cmbSL_Code").value="";
   // document.getElementById("txtsub_Paid_to").value="";
    //document.getElementById("txtReferenceNo").value="";
    document.getElementById("txtParticular").value="";
    var cmbSL_type=document.getElementById("cmbSL_type"); 
    cmbSL_type.innerHTML=""; 
                var option=document.createElement("OPTION");
                option.text="--Select Type--";
                option.value="";
                try
                {
                    cmbSL_type.add(option);
                }catch(errorObject)
                {
                    cmbSL_type.add(option,null);
                }
       document.getElementById("offlist_div_trans").style.display='none';
           
        var cmbSL_Code=document.getElementById("cmbSL_Code");   
        clear_Combo(cmbSL_Code);   

     document.frmBankPay_NilPayment_Create.cmdadd.style.display='block';
     document.frmBankPay_NilPayment_Create.cmdupdate.style.display='none';
     document.frmBankPay_NilPayment_Create.cmddelete.disabled=true;
}
function update_GRID()
{      
        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
        alert("Enter A/c Head Code");
        document.getElementById("txtAcc_HeadCode").focus();
        return false;
        }
         if(document.frmBankPay_NilPayment_Create.rad_sub_CR_DR[0].checked==true)
        {
            alert("Credit amount not allowed");
            return false;
        }
        if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
        {
          if(window.confirm("You have not selected Sub-Ledger Type \n Do you want to select it,click 'OK'?"))
          {
             if(document.getElementById("cmbSL_type").value=="")
              {
                alert("Select a Sub-Ledger Type");
                return false;
               } 
          }
          else
          {
             
          }
          
        }
        if(document.getElementById("cmbSL_type").value!="")
        {
          if(document.getElementById("cmbSL_Code").value=="")
            {
            alert("Select The Sub Ledger Code")       ;
            document.getElementById("cmbSL_Code").focus();
            return false;
            }
        }
      /*
      if(document.getElementById("txtBill_NO").value.length==0)
        {
            alert("Enter the Bill number");
             document.getElementById("txtBill_NO").focus();
            return false;
        }
        if(document.getElementById("txtBill_date").value.length==0)
        {
            alert("Enter the Bill Date");
             document.getElementById("txtBill_date").focus();
            return false;
        }
        if(document.getElementById("txtBill_type").value.length==0)
        {
            alert("Enter the Bill Type");
             document.getElementById("txtBill_type").focus();
            return false;
        }
        if(document.getElementById("txtsub_Paid_to").value.length==0)
        {
            alert("Enter the value in Paid to Field ");
            document.getElementById("txtsub_Paid_to").focus();
            return false;    
        }*/
        if(document.getElementById("txtReferenceNo").value.length==0)
        {
            alert("Enter the Reference Number ");
            document.getElementById("txtReferenceNo").focus();
            return false;    
        }
        
        var items=new Array();
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;
        if(document.frmBankPay_NilPayment_Create.rad_sub_CR_DR[0].checked==true)
          items[2]=document.frmBankPay_NilPayment_Create.rad_sub_CR_DR[0].value;
        else if(document.frmBankPay_NilPayment_Create.rad_sub_CR_DR[1].checked==true)
          items[2]=document.frmBankPay_NilPayment_Create.rad_sub_CR_DR[1].value;
        
        items[3]=document.getElementById("cmbSL_type").value;
        if(document.getElementById("cmbSL_type").value=="")
        {
        //items[4]="Not Available";
        items[4]="";                //document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        }
        else
        items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        
        items[5]=document.getElementById("cmbSL_Code").value;
        if(document.getElementById("cmbSL_Code").value=="")
        {
        items[6]="";                //"Not Available";
        }
        else
        items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
        
        items[7]=document.getElementById("txtBill_NO").value;
        items[8]=document.getElementById("txtBill_date").value;
        items[9]=document.getElementById("txtBill_type").value;
        
       // items[10]=document.getElementById("txtsub_Paid_to").value;
        
        items[11]=document.getElementById("txtReferenceNo").value;
        items[12]=document.getElementById("txtParticular").value;
        
       
        var r=document.getElementById(com_id);
        var rcells=r.cells;
        
         try{rcells.item(1).firstChild.value=items[0];}catch(e){}
         try{rcells.item(1).lastChild.nodeValue=items[0]+"-"+items[1];}catch(e){}
     
         try{rcells.item(2).firstChild.value=items[2];}catch(e){}
         try{rcells.item(2).lastChild.nodeValue=items[2];}catch(e){}
      
         try{rcells.item(3).firstChild.value=items[3];}catch(e){}
         try{rcells.item(3).lastChild.nodeValue=items[4];}catch(e){}
    
         try{rcells.item(4).firstChild.value=items[5];}catch(e){}
         try{rcells.item(4).lastChild.nodeValue=items[6];}catch(e){}
    
         try{rcells.item(5).firstChild.value=items[7];}catch(e){}
         try{rcells.item(5).lastChild.nodeValue=items[7];}catch(e){}
     
         try{rcells.item(6).firstChild.value=items[8];}catch(e){}
         try{rcells.item(6).lastChild.nodeValue=items[8];}catch(e){}
        
        rcells.item(7).firstChild.value=items[9];
        var nex_cell=rcells.item(7).firstChild.nextSibling;
        //nex_cell.value=items[10];
        //var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[11];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[12];
       
        rcells.item(7).lastChild.nodeValue=items[11];
        alert("Record Updated");
        clearall();
  }

function delete_GRID()
{
        if(confirm("Do you want to delete ?"))
        {
        var tbody=document.getElementById("mytable");
        var r=document.getElementById(com_id);
        var ri=r.rowIndex;
        tbody.deleteRow(ri);
        clearall();
        }
}
/////////////////////////////////////////////   clearall() by User /////////////////////////////////////////////////////

function clearall()
{
     document.getElementById("offlist_div_trans").style.display='none';
     document.getElementById("emplist_div_trans").style.display='none';
    
    document.getElementById("txtAcc_HeadCode").value="";
    document.getElementById("txtAcc_HeadDesc").value="";
    document.frmBankPay_NilPayment_Create.rad_sub_CR_DR[1].checked=true;
    document.getElementById("cmbSL_type").value="";
    document.getElementById("cmbSL_Code").value="";
    document.getElementById("txtBill_NO").value="";
    document.getElementById("txtBill_date").value="";
    document.getElementById("txtBill_type").value="";
    //document.getElementById("txtsub_Paid_to").value="";
    document.getElementById("txtReferenceNo").value="";
    document.getElementById("txtParticular").value="";
    var cmbSL_type=document.getElementById("cmbSL_type"); 
    cmbSL_type.innerHTML=""; 
                var option=document.createElement("OPTION");
                option.text="--Select Type--";
                option.value="";
                try
                {
                    cmbSL_type.add(option);
                }catch(errorObject)
                {
                    cmbSL_type.add(option,null);
                }
       document.getElementById("offlist_div_trans").style.display='none';
           
        var cmbSL_Code=document.getElementById("cmbSL_Code");   
        clear_Combo(cmbSL_Code);   

     document.frmBankPay_NilPayment_Create.cmdadd.style.display='block';
     document.frmBankPay_NilPayment_Create.cmdupdate.style.display='none';
     document.frmBankPay_NilPayment_Create.cmddelete.disabled=true;
}


function loadName_Mas(value)
{
if(document.getElementById("cmbMas_SL_Code").value=="")
return;
value=document.getElementById("cmbMas_SL_Code").options[document.getElementById("cmbMas_SL_Code").selectedIndex].text; 
s=document.getElementById("cmbMas_SL_type").value;
if(s=="7" )
value=value.substring(0,value.indexOf("-"));

document.getElementById("txtPaid_to").value=value;
}

function call_clr()
{
    /*
    document.getElementById("txtCash_Acc_code").value="";
      document.getElementById("txtBankAccountNo").value="";
    document.getElementById("txtBankName").value="";
    document.getElementById("txtBankId").value="";
    document.getElementById("txtBranchId").value="";
    */
    document.getElementById("cmbMas_SL_type").value="";
    clear_Combo(document.getElementById("cmbMas_SL_Code"));
    //document.getElementById("txtAmount").value="";
    document.getElementById("txtRemarks").value="";
    document.getElementById("txtPaid_to").value="";
    //clearall();
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

if(document.getElementById("cmbMas_SL_type").value!="")
{
    if(document.getElementById("cmbMas_SL_Code").value=="")
    {
    alert("Select The Sub Ledger Code in General");
    return false;
    }
}
if(document.getElementById("txtPaid_to").value.length==0)
{
    alert("Enter the value in ( Paid to Field ) in General");
    //document.getElementById("txtPaid_to").focus();
    return false;    
}

if(tbody.rows.length==0)
{
    alert("Enter the Details Part");
    //document.getElementById("txtAmount").focus();
    return false; 
}
return true;
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
            dateCheck(dateCtrl);  
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



function Acc_HeadCodeValidation()
{
var date1=document.getElementById("txtCrea_date").value;
    var spl=date1.split("/");
    if(spl[2]>=2011)
    {
          if(spl[0]>01 || spl[0]==01)
          {
              var Acc_HeadCode1=document.getElementById("txtAcc_HeadCode").value;
              var digit=parseInt(Acc_HeadCode1.substr(0, 2));  
              if(digit==82)
              {
              alert("This Account Head Code cannot be used here");
              document.getElementById("txtAcc_HeadCode").value="";
              document.getElementById("txtAcc_HeadCode").focus();
              }
              else if(document.getElementById("txtAcc_HeadCode").value==901001)
              {
            	  alert("TCA Head Code cannot be used here");
                  document.getElementById("txtAcc_HeadCode").value="";
                  document.getElementById("txtAcc_HeadCode").focus();
              }
              else if((Acc_HeadCode1==390302) ||(Acc_HeadCode1==390303) || (Acc_HeadCode1==391002) ||(Acc_HeadCode1==391003) ||(Acc_HeadCode1==391302) || (Acc_HeadCode1==391303) ||(Acc_HeadCode1==391502) ||(Acc_HeadCode1==391503) )
              {			
              	  alert("GPF Account Head Code cannot be used here***");
                    document.getElementById("txtAcc_HeadCode").value="";
                    document.getElementById("txtAcc_HeadCode").focus();
                    return false;
                }
              else
              {
            	  doFunction('checkCode','null');
              }
          }
    }
}

function call_bankUpdate()
{
	var accunitId=document.getElementById('cmbAcc_UnitCode').value;
	var url="../../../../../BankPay_PendingBill_Create.view?Command=bankBalUpdate&accunitId="+accunitId;
		
		var req=getTransport();
		req.open("POST",url,true); 
		req.onreadystatechange=function(){
		processResponse_bk(req);
		};   
		req.send(null);
}
function processResponse_bk(req){
	 if(req.readyState==4)
   { 
       if(req.status==200)
       {  
      	 var rangeResponse=req.responseXML.getElementsByTagName("response")[0];
    	   update_bank_bal(rangeResponse);
       }
   }
}
function update_bank_bal(response){
	var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
     
	if(flag=="fail"){
		alert("Cashbook Transactions for this Month Cannot be Entered unless " +
				"the Bank Balance For Each AccountNo is Updated By Using The Menu (Bank Balance Update) " +
				"under Unit Operation-Bank Reconcillation System");
		document.getElementById("newDiv").style.display="none";
		document.frmBankPay_NilPayment_Create.txtCrea_date.value="";
		return false;
	}
	else
	{
		var counted_bank_bal=response.getElementsByTagName("counted_bank_bal")[0].firstChild.nodeValue;
		if(counted_bank_bal>0)
		{
			alert("Cashbook Transactions for this Month Cannot be Entered unless " +
					"the Bank Balance For Each AccountNo is Updated By Using The Menu (Bank Balance Update) " +
					"under Unit Operation-Bank Reconcillation System");
			document.getElementById("newDiv").style.display="none";
			document.frmBankPay_NilPayment_Create.txtCrea_date.value="";
			return false;
		}
		else
		{
			 var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             
             if(day<=9 && day>=1)
             day="0"+day;
             if(month<=9 && month>=1)
             month="0"+month;
             var year=today.getYear();
             if(year < 1900) year += 1900;
             var monthArray =new Array("January", "February", "March", 
                       "April", "May", "June", "July", "August",
                       "September", "October", "November", "December");
            document.frmBankPay_NilPayment_Create.txtCrea_date.value=day+"/"+month+"/"+year;
            call_date(document.frmBankPay_NilPayment_Create.txtCrea_date);
            document.getElementById("newDiv").style.display="block";
		}
		
	}
}