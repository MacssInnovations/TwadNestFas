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


function byUnitAndOfficeChange()
{
    doFunction_voucher('load_Voucher_No','null');
}
/////////////////////////////////////////////   doFunction()  /////////////////////////////////////////////////////

function doFunction_voucher(Command,param)
{   
       var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
       var cmbOffice_code=document.getElementById("cmbOffice_code").value;
       var txtCrea_date= document.getElementById("txtCrea_date").value
       
       if(Command=="load_Voucher_No")
        {  
           clearGeneral_Detail();
            if(txtCrea_date.length!=0)
            {
            var url="../../../../../BankPay_NonBill_Edit.view?Command=load_Voucher_No&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
            cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
            //alert(url)
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse_voucher(req);
            }   
                    req.send(null);
            }         
        }
        else if(Command=="load_Voucher_Details")
        {  
            clearGeneral_Detail();
            var  txtVoucher_No=document.getElementById("txtVoucher_No").value;
           
            if(txtVoucher_No!="")
            {
            var url="../../../../../BankPay_NonBill_Edit.view?Command=load_Voucher_Details&txtVoucher_No="+txtVoucher_No+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
            cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;;
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse_voucher(req);
            }   
                    req.send(null);
            }         
        }
        

}
/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
function handleResponse_voucher(req)
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
            else if(Command=="load_Voucher_Details")
            {
                load_Voucher_Details(baseResponse);
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
         // doFunction('checkCode','null');
        doFunctionBLOCK('checkCode','null');
        try{com_cmbSL_type=rcells.item(3).firstChild.value;} catch(e){com_cmbSL_type=""}
        try{com_cmbSL_Code=rcells.item(4).firstChild.value;} catch(e){com_cmbSL_Code=""}     
        
          if(com_cmbSL_type==5)
        {         
                document.getElementById("txtOfficeID_trs").value=com_cmbSL_Code;                
                job_flag=false;             
        }
        if(com_cmbSL_type==7)
        {
                document.getElementById("txtEmpID_trs").value=com_cmbSL_Code;
                emp_flag=false;            
        } 
              
                doFunction('Load_SL_Code',com_cmbSL_type);
                
        setTimeout('document.getElementById("cmbSL_type").value=com_cmbSL_type',900); 
        setTimeout('document.getElementById("cmbSL_Code").value=com_cmbSL_Code',900); 
         if(rcells.item(2).firstChild.value=="CR")
         document.frmBankPay_FinalBill.rad_sub_CR_DR[0].checked=true;
         else if(rcells.item(2).firstChild.value=="DR")
         document.frmBankPay_FinalBill.rad_sub_CR_DR[1].checked=true;
         
       
       //try{document.getElementById("txtPaid_to").value=rcells.item(5).firstChild.value;}catch(e){}
       
        if(rcells.item(5).firstChild.value=="C")
         document.frmBankPay_FinalBill.txtCheque_DD[0].checked=true;
         else if(rcells.item(5).firstChild.value=="D")
         document.frmBankPay_FinalBill.txtCheque_DD[1].checked=true;
         else if(rcells.item(5).firstChild.value=="E")
         document.frmBankPay_FinalBill.txtCheque_DD[2].checked=true;
        
        
        
       try{
             if (rcells.item(5).firstChild.nextSibling.value != "null" ) 
             {
                document.getElementById("txtCheque_DD_NO").value=rcells.item(5).firstChild.nextSibling.value;
             }
             else
             {
                document.getElementById("txtCheque_DD_NO").value="";
             }
          }catch(e){}
          
          
       try{
             if (rcells.item(6).firstChild.value != "null") 
             {
               document.getElementById("txtCheque_DD_date").value=rcells.item(6).firstChild.value;
             }
             else
             {
               document.getElementById("txtCheque_DD_date").value="";
             }             
          }catch(e){}
       
       
       try{document.getElementById("txtAgree_No").value=rcells.item(7).firstChild.value;}catch(e){}
       var nex=rcells.item(7).firstChild.nextSibling  
       
        try{document.getElementById("txtAgree_Date").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
         //try{document.getElementById("txtsub_Paid_to").value=nex.value;}catch(e){}
        //nex=nex.nextSibling;
       try{document.getElementById("txtsub_Amount").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
       try{document.getElementById("txtParticular").value=nex.value;}catch(e){}
       
       
    document.frmBankPay_FinalBill.cmdupdate.style.display='block';
    document.frmBankPay_FinalBill.cmddelete.disabled=false;
    document.frmBankPay_FinalBill.cmdadd.style.display='none';
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
        
        var tbody=document.getElementById("grid_body");
          if(tbody.rows.length>0)
            {
             rows=tbody.getElementsByTagName("tr");
             var txtAcc_HeadCode= document.getElementById("txtAcc_HeadCode").value;
              for(i=0;i<rows.length;i++)
               {
                    var cells=rows[i].cells;
                  
                
                    var ac_code=cells.item(1).lastChild.nodeValue;
                    var units=cells.item(3).lastChild.nodeValue;
                  //  alert("units"+units);
                    var code1= ac_code.split("-");   
                 //  alert(code1[0]);
                    if(code1[0]==900108 ||code1[0]==901001)
                    {
                        if(units=="Accounting Units")
                        {
                        if(code1[0]==txtAcc_HeadCode){
                            var offcode=cells.item(4).lastChild.nodeValue;
                           var chkslcode= document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text;
                       
                           if(offcode==chkslcode)
                            {
                            alert("Already Added For this A/c unit. Choose Different Unit for "+txtAcc_HeadCode);
                             return false;
                            }
                            }
                        }
                    }
                }
             }
        
         var acc=document.getElementById("txtAcc_HeadCode").value;
        var kk=acc.charAt(0)+acc.charAt(1);
       
        if(kk=="82")
        {
            if(acc !="820102"  && acc !="820103")
            {
               alert("This Account Head Code is not allowed here."+acc);
               document.getElementById("txtAcc_HeadCode").value="";
               document.getElementById("txtAcc_HeadDesc").value="";
               return false;
            }   
        }
    /* 
       if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
        alert("Enter A/c Head Code");
        document.getElementById("txtAcc_HeadCode").focus();
        return false;
        }*/
     /*   if(document.frmBankPay_FinalBill.rad_sub_CR_DR[0].checked==true)
        {
            alert("Credit amount not allowed");
            return false;
        }*/
        
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
       
     /*  if ( isMan.account_head_status) 
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
       } else
       { */
      /*  if( (document.getElementById("txtAcc_HeadCode").value==550310) || (document.getElementById("txtAcc_HeadCode").value==550301) )
       {
       }else
       {
            if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
            {
                
                                alert("Select a Sub-Ledger Type");
                                return false;
                           
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
            }*/
        
     //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      
        
 if(document.frmBankPay_FinalBill.txtCheque_DD[2].checked == false)
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
        
        
     cmbSL_type=document.getElementById("cmbSL_type").value;
      /*  if(cmbSL_type==1 || cmbSL_type==2 || cmbSL_type==10 || cmbSL_type==11)
        {
            if(document.getElementById("txtAgree_No").value.length==0)
            {
            alert("Enter the Agreement Number");
             document.getElementById("txtAgree_No").focus();
            return false;
            }
            if(document.getElementById("txtAgree_Date").value.length==0)
            {
            alert("Enter the Agreement Date");
             document.getElementById("txtAgree_Date").focus();
            return false;
            }
        }
      if(document.getElementById("txtsub_Paid_to").value.length==0)
        {
            alert("Enter the value in Paid to Field ");
            //document.getElementById("txtPaid_to").focus();
            return false;    
        }*/
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            document.getElementById("txtsub_Amount").focus();
            return false;    
        }
        var tbody=document.getElementById("grid_body");
              
        var t=0;
        var exist=document.getElementById("txtAcc_HeadCode").value;
       
        var items=new Array();
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;
        if(document.frmBankPay_FinalBill.rad_sub_CR_DR[0].checked==true)
          items[2]=document.frmBankPay_FinalBill.rad_sub_CR_DR[0].value;
        else if(document.frmBankPay_FinalBill.rad_sub_CR_DR[1].checked==true)
          items[2]=document.frmBankPay_FinalBill.rad_sub_CR_DR[1].value;
        
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
        
        if(document.frmBankPay_FinalBill.txtCheque_DD[0].checked==true)
          items[7]=document.frmBankPay_FinalBill.txtCheque_DD[0].value;
        else if(document.frmBankPay_FinalBill.txtCheque_DD[1].checked==true)
          items[7]=document.frmBankPay_FinalBill.txtCheque_DD[1].value;
        else if(document.frmBankPay_FinalBill.txtCheque_DD[2].checked==true)
          items[7]=document.frmBankPay_FinalBill.txtCheque_DD[2].value;
        
        
        
          
        items[8]=document.getElementById("txtCheque_DD_NO").value;
        if (items[8]=="null" ) 
        {
          items[8]="";
        }
        
        items[9]=document.getElementById("txtCheque_DD_date").value;
        if (items[9]=="null" ) 
        {
          items[9]="";
        }
        
        
        items[10]=document.getElementById("txtAgree_No").value;
        items[11]=document.getElementById("txtAgree_Date").value;
        
        //items[12]=document.getElementById("txtAgree_No").value;
        
        //items[12]=document.getElementById("txtsub_Paid_to").value;
        
        items[13]=document.getElementById("txtsub_Amount").value;
        items[14]=document.getElementById("txtParticular").value;
        
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
                 var Cheque_DD=document.createElement("input");
                  Cheque_DD.type="hidden";
                  Cheque_DD.name="Cheque_DD";
                  Cheque_DD.value=items[7];
                  //Cheque_DD.style.display='none';
                  cell2.appendChild(Cheque_DD);
        /*                                                              within the star indicates to avoid the column added in b/w two cells
                //var currentText=document.createTextNode(items[7]);          
                //cell2.appendChild(currentText);
                //mycurrent_row.appendChild(cell2);
             //cell2=document.createElement("TD");  
        */
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
                
              cell2=document.createElement("TD");
                  var Bank_Name=document.createElement("input");
                  Bank_Name.type="hidden";
                  Bank_Name.name="Agree_No";
                  Bank_Name.value=items[10];
           // Bank_Name.style.display='none';
                  cell2.appendChild(Bank_Name);
                    
              var Draw_BR=document.createElement("input");
                  Draw_BR.type="hidden";
                  Draw_BR.name="Agree_date";
                  Draw_BR.value=items[11];
        // Draw_BR.style.display='none';
                  cell2.appendChild(Draw_BR);
                             
              /*   var sub_paid=document.createElement("input");
                  sub_paid.type="hidden";
                  sub_paid.name="sub_paid";
                  sub_paid.value=items[12];
                  cell2.appendChild(sub_paid);
             */
                 var sl_amt=document.createElement("input");
                  sl_amt.type="hidden";
                  sl_amt.name="sl_amt";
                  sl_amt.value=items[13];
                  cell2.appendChild(sl_amt);

                  var particular=document.createElement("input");           // Particulars Added to grid b4 the Amount Text Node but after  amount hidden box    
                  particular.type="hidden";
                  particular.name="particular";
                  particular.value=items[14];
            //    particular.style.display='none';
                  cell2.appendChild(particular);

                  var currentText=document.createTextNode(items[13]);
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
    //document.getElementById("txtAcc_HeadCode").readOnly=false;
    document.getElementById("txtAcc_HeadDesc").value="";
    document.frmBankPay_FinalBill.rad_sub_CR_DR[1].checked=true;
    document.getElementById("cmbSL_type").value="";
    document.getElementById("cmbSL_Code").value="";
    //document.getElementById("txtPaid_to").value="";
    
    // document.getElementById("txtsub_Paid_to").value="";
    document.getElementById("txtsub_Amount").value="";
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

     document.frmBankPay_FinalBill.cmdadd.style.display='block';
     document.frmBankPay_FinalBill.cmdupdate.style.display='none';
     document.frmBankPay_FinalBill.cmddelete.disabled=true;
}

function update_GRID()
{      
       if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
        alert("Enter A/c Head Code");
        document.getElementById("txtAcc_HeadCode").focus();
        return false;
        }
        
        var tbody=document.getElementById("grid_body");
          if(tbody.rows.length>0)
            {
             rows=tbody.getElementsByTagName("tr");
             var txtAcc_HeadCode= document.getElementById("txtAcc_HeadCode").value;
              for(i=0;i<rows.length;i++)
               {
                    var cells=rows[i].cells;
                  
                
                    var ac_code=cells.item(1).lastChild.nodeValue;
                    var units=cells.item(3).lastChild.nodeValue;
                  //  alert("units"+units);
                    var code1= ac_code.split("-");   
                 //  alert(code1[0]);
                    if(code1[0]==900108 ||code1[0]==901001)
                    {
                        if(units=="Accounting Units")
                        {
                        if(code1[0]==txtAcc_HeadCode){
                            var offcode=cells.item(4).lastChild.nodeValue;
                           var chkslcode= document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text;
                       
                           if(offcode==chkslcode)
                            {
                            alert("Already Added For this A/c unit. Choose Different Unit for "+txtAcc_HeadCode);
                             return false;
                            }
                            }
                        }
                    }
                }
             }
        
       /* if(document.frmBankPay_FinalBill.rad_sub_CR_DR[0].checked==true)
        {
            alert("Credit amount not allowed");
            return false;
        }*/
     /*   if( (document.getElementById("txtAcc_HeadCode").value==550310) || (document.getElementById("txtAcc_HeadCode").value==550301) )
       {
       }else
       {
        if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
        {
          
                alert("Select a Sub-Ledger Type");
                return false;
       
          
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
        }  */
        
         
        
 if(document.frmBankPay_FinalBill.txtCheque_DD[2].checked == false)
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
        
        
         cmbSL_type=document.getElementById("cmbSL_type").value;
    /* if(cmbSL_type==1 || cmbSL_type==2 || cmbSL_type==10 || cmbSL_type==11)
        {
            if(document.getElementById("txtAgree_No").value.length==0)
            {
            alert("Enter the Agreement Number");
             document.getElementById("txtAgree_No").focus();
            return false;
            }
            if(document.getElementById("txtAgree_Date").value.length==0)
            {
            alert("Enter the Agreement Date");
             document.getElementById("txtAgree_Date").focus();
            return false;
            }
        }
        if(document.getElementById("txtsub_Paid_to").value.length==0)
        {
            alert("Enter the value in Paid to Field ");
            //document.getElementById("txtPaid_to").focus();
            return false;    
        }*/
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            //document.getElementById("txtAmount").focus();
            return false;    
        }
        var exist=document.getElementById("txtAcc_HeadCode").value;
        var exist=document.getElementById("txtAcc_HeadCode").value;
       
        var items=new Array();
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;
        if(document.frmBankPay_FinalBill.rad_sub_CR_DR[0].checked==true)
          items[2]=document.frmBankPay_FinalBill.rad_sub_CR_DR[0].value;
        else if(document.frmBankPay_FinalBill.rad_sub_CR_DR[1].checked==true)
          items[2]=document.frmBankPay_FinalBill.rad_sub_CR_DR[1].value;
        
        items[3]=document.getElementById("cmbSL_type").value;
        if(document.getElementById("cmbSL_type").value=="")
        {
        //items[4]="Not Available";
        items[4]="";           
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
        
        if(document.frmBankPay_FinalBill.txtCheque_DD[0].checked==true)
          items[7]=document.frmBankPay_FinalBill.txtCheque_DD[0].value;
        else if(document.frmBankPay_FinalBill.txtCheque_DD[1].checked==true)
          items[7]=document.frmBankPay_FinalBill.txtCheque_DD[1].value;
        else if(document.frmBankPay_FinalBill.txtCheque_DD[2].checked==true)
          items[7]=document.frmBankPay_FinalBill.txtCheque_DD[2].value;
        
        
          
        items[8]=document.getElementById("txtCheque_DD_NO").value;
        if (items[8]=="null" ) 
        {
          items[8]="";
        }
        
        items[9]=document.getElementById("txtCheque_DD_date").value;
        if (items[9]=="null" ) 
        {
          items[9]="";
        } 
       
        items[10]=document.getElementById("txtAgree_No").value;
        items[11]=document.getElementById("txtAgree_Date").value;
        
       // items[12]=document.getElementById("txtBank_M_Code").value;
        
     // items[12]=document.getElementById("txtsub_Paid_to").value;
        
        items[13]=document.getElementById("txtsub_Amount").value;
        items[14]=document.getElementById("txtParticular").value;
       
        var r=document.getElementById(com_id);
        var rcells=r.cells;
        
        rcells.item(1).firstChild.value=items[0];
        rcells.item(1).lastChild.nodeValue=items[0]+"-"+items[1];
        rcells.item(2).firstChild.value=items[2];
        rcells.item(2).lastChild.nodeValue=items[2];
        rcells.item(3).firstChild.value=items[3];
        rcells.item(3).lastChild.nodeValue=items[4];
        rcells.item(4).firstChild.value=items[5];
        rcells.item(4).lastChild.nodeValue=items[6];
        
        rcells.item(5).firstChild.value=items[7];
        rcells.item(5).firstChild.nextSibling.value=items[8];
        rcells.item(5).lastChild.nodeValue=items[8];
        
        rcells.item(6).firstChild.value=items[9];
        rcells.item(6).lastChild.nodeValue=items[9];
        
        rcells.item(7).firstChild.value=items[10];
         var nex_cell=rcells.item(7).firstChild.nextSibling;
        nex_cell.value=items[11];
        //var nex_cell=nex_cell.nextSibling;
       // nex_cell.value=items[12];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[13];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[14];
        rcells.item(7).lastChild.nodeValue=items[13];
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
    //document.getElementById("txtAcc_HeadCode").readOnly=false;
    document.getElementById("txtAcc_HeadDesc").value="";
    document.frmBankPay_FinalBill.rad_sub_CR_DR[0].checked=true;
    document.getElementById("cmbSL_type").value="";
    document.getElementById("cmbSL_Code").value="";
    //document.getElementById("txtsub_Paid_to").value="";
    document.frmBankPay_FinalBill.txtCheque_DD[1].checked=true;
    document.getElementById("txtCheque_DD_NO").value="";
    document.getElementById("txtCheque_DD_date").value="";
       
    document.getElementById("txtAgree_No").value="";
    document.getElementById("txtAgree_Date").value="";
    document.getElementById("txtsub_Amount").value="";
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
    

     document.frmBankPay_FinalBill.cmdadd.style.display='block';
     document.frmBankPay_FinalBill.cmdupdate.style.display='none';
     document.frmBankPay_FinalBill.cmddelete.disabled=true;
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
    document.getElementById("txtAmount").value="";
    document.getElementById("txtRemarks").value="";
    
    /*
    document.getElementById("txtAuth_By").value="";
    document.getElementById("Auth_By").value="";
    
    document.getElementById("txtReferNO_edit").value="";
    document.getElementById("txtReferDate_edit").value="";
    document.getElementById("txtRemak_edit").value=""; 
    */
    document.getElementById("cmbMas_SL_type").value=""; 
    clear_Combo(document.getElementById("cmbMas_SL_Code"));
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
if(document.getElementById("txtPaid_to").value.length==0)
{
    alert("Enter the value in Paid to Field in General");
    //document.getElementById("txtPaid_to").focus();
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
if(document.getElementById("txtAmount").value.length==0)
{
    alert("Enter the Total Amount in General");
    //document.getElementById("txtAmount").focus();
    return false;    
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
            if(cells.item(2).lastChild.nodeValue=='DR')
            {
//                alert('amount in cell^^^^^^^'+parseFloat(cells.item(7).lastChild.nodeValue));
                check_amt=parseFloat(check_amt) + parseFloat(cells.item(7).lastChild.nodeValue);  
               
//                alert('Check amount********'+check_amt);
            }
            else if(cells.item(2).lastChild.nodeValue=='CR')
            {
                check_amt=parseFloat(check_amt) - parseFloat(cells.item(7).lastChild.nodeValue);
                
                }
        }
     /*
     if(parseFloat(chkCR_DB)!=parseFloat(check_amt))
        {
            alert("Credit and Debit Amount doesn't Tally in details.. Difference " +(parseFloat(chkCR_DB)-parseFloat(check_amt)));
            return false;
        }
     */        
    
   //   var txtAmounted=document.getElementById("txtAmount").value;
 //  alert("check_amt:::be44444::"+check_amt);
      //  check_amt=Math.abs(check_amt);
    
        if(parseFloat(document.getElementById("txtAmount").value)!=check_amt)
       // if(parseDouble(document.getElementById("txtAmount").value)!=parseDouble(check_amt))
        {
              //  alert("%%%");
                alert("Amount doesn't Tally.. Difference " +(parseFloat(document.getElementById("txtAmount").value)-check_amt))
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
       var bk_name=baseResponse.getElementsByTagName("bk_name")[0].firstChild.nodeValue;
       var bk_id=baseResponse.getElementsByTagName("bk_id")[0].firstChild.nodeValue;
       var br_id=baseResponse.getElementsByTagName("br_id")[0].firstChild.nodeValue;
       var Total_amt=baseResponse.getElementsByTagName("Total_amt")[0].firstChild.nodeValue;
      
       var Mas_paid=baseResponse.getElementsByTagName("Mas_paid")[0].firstChild.nodeValue;
       if(baseResponse.getElementsByTagName("Remak")[0].firstChild!=undefined)
       var Remak=baseResponse.getElementsByTagName("Remak")[0].firstChild.nodeValue;

      document.getElementById("txtCash_Acc_code").value=MasHeadCode;
      document.getElementById("txtBankAccountNo").value=accNo;
      document.getElementById("txtBankName").value=bk_name;
      document.getElementById("txtBankId").value=bk_id;
      document.getElementById("txtBranchId").value=br_id;
      
       document.getElementById("txtAmount").value=Total_amt;
      if(Mas_paid!="null")
      document.getElementById("txtPaid_to").value=Mas_paid;
      else
      document.getElementById("txtPaid_to").value="";
      
            // This block is needed in case of sl type come in General also********
       var Mas_SL_type=baseResponse.getElementsByTagName("Mas_SL_type")[0].firstChild.nodeValue;
       var Mas_SL_code=baseResponse.getElementsByTagName("Mas_SL_code")[0].firstChild.nodeValue;
       if(Mas_SL_type!=0)
       document.getElementById("cmbMas_SL_type").value=Mas_SL_type;
       
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
                  //alert("USE search ICON to select the office");
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
          
            document.getElementById("cmbMas_SL_Code").value=Mas_SL_code;    //set from getting from servlet
            
      }
      
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
       // alert("AHcode::"+AHcode.length);
        var items=new Array();
        for(var k=0;k<AHcode.length;k++)
        {
        	//alert("AHcode::"+AHcode[k].firstChild.nodeValue);
        items[0]=baseResponse.getElementsByTagName("AHcode")[k].firstChild.nodeValue;   
        items[1]=baseResponse.getElementsByTagName("AHdesc")[k].firstChild.nodeValue;   
        
        items[2]=baseResponse.getElementsByTagName("CR_DR_ind")[k].firstChild.nodeValue;
        items[3]=baseResponse.getElementsByTagName("SL_Type")[k].firstChild.nodeValue;
      //  alert(items[3]);
        if(items[3]==0)
        items[3]="";
         
        items[4]=baseResponse.getElementsByTagName("SL_Desc")[k].firstChild.nodeValue;
        if(items[4]=="null")
        items[4]="";
        
        items[5]=baseResponse.getElementsByTagName("SL_Code")[k].firstChild.nodeValue;
        if(items[5]==0)
        items[5]="";
        
        items[6]=baseResponse.getElementsByTagName("desc_type")[k].firstChild.nodeValue;
       // alert("desc"+items[6]);
        if(items[6]=="null")
        items[6]="";
        
        items[7]=baseResponse.getElementsByTagName("che_or_DD")[k].firstChild.nodeValue;
       
        items[8]=baseResponse.getElementsByTagName("che_DD_no")[k].firstChild.nodeValue;
        if (items[8]=="null")
        {
         items[8]="";
        }
        
        
        items[9]=baseResponse.getElementsByTagName("che_DD_date")[k].firstChild.nodeValue;
        
         if (items[9]=="null")
        {
         items[9]="";
        }
        items[10]="";
        if(baseResponse.getElementsByTagName("Agree_no")[k].firstChild!=null)
        	items[10]=baseResponse.getElementsByTagName("Agree_no")[k].firstChild.nodeValue;
        items[11]=baseResponse.getElementsByTagName("Agree_date")[k].firstChild.nodeValue;
        if(baseResponse.getElementsByTagName("sub_paidto")[k].firstChild!=null)
        	items[12]=baseResponse.getElementsByTagName("sub_paidto")[k].firstChild.nodeValue;
        items[13]=baseResponse.getElementsByTagName("sub_amount")[k].firstChild.nodeValue;
        if(items[14]=baseResponse.getElementsByTagName("sub_part")[k]=null)
        items[14]=baseResponse.getElementsByTagName("sub_part")[k].firstChild.nodeValue;
        
        
        if(items[10]=="null")
        items[10]="";
        if(items[11]=="null")
        items[11]="";
        if(items[12]=="null")
        items[12]="";

        if(items[14]=="null")
        items[14]="";
        tbody=document.getElementById("grid_body");
        var mycurrent_row=document.createElement("TR");
        seq=seq+1;
        mycurrent_row.id=seq;
        //alert("row ID"+mycurrent_row.id);
        
        if (items[14]==0) {
          	 var cell=document.createElement("TD");
               var anc=document.createElement("A");
               var url="javascript:loadTable('"+mycurrent_row.id+"')";
               anc.href=url;
               var txtedit=document.createTextNode("EDIT");
               anc.appendChild(txtedit);
               cell.appendChild(anc);
               mycurrent_row.appendChild(cell);
               
   			
   		}
        
        else{        
        var cell=document.createElement("TD");
        var anc=document.createElement("A");
        var url="javascript:loadTable('"+mycurrent_row.id+"')";
        anc.href=url;
        var txtedit=document.createTextNode("EDIT");
        anc.appendChild(txtedit);
        cell.appendChild(anc);
        mycurrent_row.appendChild(cell);
        }
        
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
                   
                //    alert("seq"+seq+"type"+items[5]+"desc"+items[6]);
                   
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
                 var Cheque_DD=document.createElement("input");
                  Cheque_DD.type="hidden";
                  Cheque_DD.name="Cheque_DD";
                  Cheque_DD.value=items[7];
                  //Cheque_DD.style.display='none';
                  cell2.appendChild(Cheque_DD);
        /*                                                              within the star indicates to avoid the column added in b/w two cells
                //var currentText=document.createTextNode(items[7]);          
                //cell2.appendChild(currentText);
                //mycurrent_row.appendChild(cell2);
             //cell2=document.createElement("TD");  
        */
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
                
              cell2=document.createElement("TD");
                  var Agree_No=document.createElement("input");
                  Agree_No.type="hidden";
                  Agree_No.name="Agree_No";
                  Agree_No.value=items[10];
           // Agree_No.style.display='none';
                  cell2.appendChild(Agree_No);
                    
              var Agree_date=document.createElement("input");
                  Agree_date.type="hidden";
                  Agree_date.name="Agree_date";
                  Agree_date.value=items[11];
        // Agree_date.style.display='none';
                  cell2.appendChild(Agree_date);
                             
                /*  var sub_paid=document.createElement("input");
                  sub_paid.type="hidden";
                  sub_paid.name="sub_paid";
                  sub_paid.value=items[12];
                  cell2.appendChild(sub_paid);
                */
                 var sl_amt=document.createElement("input");
                  sl_amt.type="hidden";
                  sl_amt.name="sl_amt";
                  sl_amt.value=items[13];
                  cell2.appendChild(sl_amt);

                  var particular=document.createElement("input");           // Particulars Added to grid b4 the Amount Text Node but after  amount hidden box    
                  particular.type="hidden";
                  particular.name="particular";
                  particular.value=items[14];
            //    particular.style.display='none';
                  cell2.appendChild(particular);

                  var currentText=document.createTextNode(items[13]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
       
       
        tbody.appendChild(mycurrent_row);
        
        }
    }
    
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
                 doFunction_voucher('load_Voucher_No','null');                 //return true;
              }
             else if(flag=="failure")
               {
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
              if(document.getElementById("txtAcc_HeadCode").value==901001)
              {
            	  alert("TCA Head Code cannot be used here");
                  document.getElementById("txtAcc_HeadCode").value="";
                  document.getElementById("txtAcc_HeadCode").focus();
              }
          }
    }
    // joan modify on 22 Sep 2014
    /*if(document.getElementById("txtAcc_HeadCode").value==550102)
    {
  	  alert("This Account Head Code cannot be used here - Enter in Payment against pending bill");
        document.getElementById("txtAcc_HeadCode").value="";
        document.getElementById("txtAcc_HeadCode").focus();
    }*/
    //Lakshmi
    var toDate="31/03/2014";
	//var dd1 = ddate.split('/');
	var fromDate = spl[1] + "/" + spl[0] + "/" + spl[2];
	var dd2 = toDate.split('/');
	var toDate = dd2[1] + "/" + dd2[0] + "/" + dd2[2];
	
	var a = fromDate.split('/');
	var b = toDate.split('/');

	var fromDate1 = new Date(a[2], a[0] - 1, a[1]);
	var toDate1 = new Date(b[2], b[0] - 1, b[1]);
	/*if (fromDate1 > toDate1){
		var hcode=document.getElementById("txtAcc_HeadCode").value;//||(hcode==390305)
		if((hcode==390302) ||(hcode==390303) || (hcode==391002) ||(hcode==391003) ||(hcode==391302) || (hcode==391303) ||(hcode==391502) ||(hcode==391503) )
		*/
		//alert(document.getElementById("txtAcc_HeadCode").value);
		//alert(hcode);
		//if(document.getElementById("txtAcc_HeadCode").value.match(/^39*/))
		/*	
        {
			
      	  alert("This Account Head Code cannot be used here***");
            document.getElementById("txtAcc_HeadCode").value="";
            document.getElementById("txtAcc_HeadCode").focus();
        }
	}*/
	var hcode=document.getElementById("txtAcc_HeadCode").value;
	 if((hcode==390302) ||(hcode==390303)|| (hcode==390305) || (hcode==391002) ||(hcode==391003) ||(hcode==391302) || (hcode==391303) ||(hcode==391502) ||(hcode==391503) )
    {			
    	  alert("GPF Account Head Code cannot be used here***");
          document.getElementById("txtAcc_HeadCode").value="";
          document.getElementById("txtAcc_HeadCode").focus();
          return false;
      }
	//Sundry Creditors head not alloed in final head payment.allow in payment thru pending bill
		//changed by Sathya on 23Apr2015
		var headcode = document.getElementById("txtAcc_HeadCode").value;
		if(spl[2]>2014)
			{
			if((spl[1]>3))
				{
				if((spl[0]>01) || (spl[0]==01))
				{
					if((headcode==550102) ||(headcode==550103) || (headcode==550112) || (headcode==550113) ||(headcode==550114) ||(headcode==550115) || (headcode==550116) ||(headcode==550301) ||(headcode==550302) ||
					(headcode==550303)||(headcode==550304)|| (headcode==550308)||(headcode==550309)||(headcode==550401)||(headcode==550402)||
					(headcode==550403)||(headcode==550404)|| (headcode==550520)||(headcode==550521)||(headcode==550901)||(headcode==550902)||
					(headcode==550601)||(headcode==550701)|| (headcode==550602)||(headcode==550702)||(headcode==550603)||(headcode==550703)||(headcode==550501) || (headcode==550522))
					{
						alert("Account Head Code cannot be used here***");
						document.getElementById("txtAcc_HeadCode").value="";
						document.getElementById("txtAcc_HeadCode").focus();
					}
				}
			  }
			}
}
chequeRange=function(){	
	if((document.frmBankPay_FinalBill.txtCheque_DD[0].checked==true)&&(document.getElementById('txtCheque_DD_NO').value!="")){
		//alert("test "+document.frmBankPay_PendingBill_create.txtCheque_DD[0].checked);
		var accunitId=document.getElementById('cmbAcc_UnitCode').value;
	//	var officeId=document.getElementById('cmbOffice_code').value;
		var chequeNo=document.getElementById('txtCheque_DD_NO').value;
		var accountNo=document.getElementById('txtBankAccountNo').value;
                var txtCrea_date=document.getElementById('txtCrea_date').value;
                var dated=txtCrea_date.split("/");
                if(dated[2]==2011 || dated[2]>2011){
             //   if(dated[1]==11 || dated[1]>11){
		var url="../../../../../BankPay_PendingBill_Create.view?Command=chequeRange&chequeNo="+chequeNo+"&accunitId="+accunitId+"&accountNo="+accountNo;
      // alert("hai");
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


function checkSubLedgerMandatory1()
{
var Option="test";
var txtUnitId=0;
var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
			+ txtAcc_HeadCode + "&Option="+ Option + "&txtUnitId="+ txtUnitId;
	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		Mandatory1(req);
	}
	req.send(null);

}


function Mandatory1(req) {

	if (req.readyState == 4) {
		if (req.status == 200) {
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var tagcommand = baseResponse.getElementsByTagName("command")[0];
			var Command = tagcommand.firstChild.nodeValue;
			var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			if (Command == "checkSubLedgerMandatory") {
				if (flag == "success") {
					var M_flag = baseResponse.getElementsByTagName("M_flag")[0].firstChild.nodeValue;
                                       
					if(M_flag=="Madatory")
                                        {
                                      
                                        update_GRID();
                                        //acheadcodecheck('2');
					}
                                        else if(M_flag=="Not_Madatory")
                                        {
                                        if(document.getElementById("cmbSL_type").value=="")
                                        {
                                        alert("Select a Sub-Ledger Type");
		                        return false;
                                        }
                                        if(document.getElementById("cmbSL_Code").value=="")
                                        {
                                        alert("Select a Sub-Ledger Code");
		                        return false;
                                        }
                                        else
                                        {
                                       update_GRID();
                                        }
                                        }
				}
			}
		}
	}
}

function checkSubLedgerMandatory()
{
var Option="test";
var txtUnitId=0;
var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
			+ txtAcc_HeadCode + "&Option="+ Option + "&txtUnitId="+ txtUnitId;                      
	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		Mandatory(req);
	}
	req.send(null);

}


function Mandatory(req) {

	if (req.readyState == 4) {
		if (req.status == 200) {
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var tagcommand = baseResponse.getElementsByTagName("command")[0];
			var Command = tagcommand.firstChild.nodeValue;
			var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			if (Command == "checkSubLedgerMandatory") {
				if (flag == "success") {
					var M_flag = baseResponse.getElementsByTagName("M_flag")[0].firstChild.nodeValue;
                                       
					if(M_flag=="Madatory")
                                        {
                                      
                                       ADD_GRID();
					}
                                        else if(M_flag=="Not_Madatory")
                                        {
                                         if(document.getElementById("cmbSL_type").value=="")
                                        {
                                        alert("Select a Sub-Ledger Type");
		                        return false;
                                        }
                                        if(document.getElementById("cmbSL_Code").value=="")
                                        {
                                        alert("Select a Sub-Ledger Code");
		                        return false;
                                        }
                                        else
                                        {
                                       ADD_GRID();
                                        }
                                        }
				}
			}
		}
	}
}