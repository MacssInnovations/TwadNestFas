var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;
var job_flag;
var common_AHead_code_flag="";
var emp_flag;

var winAcc_Bank_No;     // Fteching Account Head No and Bank  No

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
    var txtModule_Type="MF004";
    var cr_dr_indi="DR";
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var txtSub_Office_code=0;
    winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi+"&txtSub_Office_code="+txtSub_Office_code,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAcc_Bank_No.moveTo(250,250);  
    winAcc_Bank_No.focus();
}

function checkACC()
{
    var date1=document.getElementById("txtCrea_date").value;
    var spl=date1.split("/");
    if(spl[2]>=2011)
    {
     var hcode_one=document.getElementById("txtAcc_HeadCode").value;
             if(hcode_one==900108 || hcode_one==900109 ||hcode_one==901002)
             {
                         alert(hcode_one+" AccountHeadCode cannot be used Here");
                         document.getElementById("txtAcc_HeadCode").value="";
                         document.getElementById("txtAcc_HeadDesc").value="";
                         return false;
             }else if((hcode_one==390302) ||(hcode_one==390303) || (hcode_one==390305) || (hcode_one==391002) ||(hcode_one==391003) ||(hcode_one==391302) || (hcode_one==391303) ||(hcode_one==391502) ||(hcode_one==391503) )
     		
             {			
                   	  alert("GPF Account Head Code cannot be used here***");
                         document.getElementById("txtAcc_HeadCode").value="";
                         document.getElementById("txtAcc_HeadCode").focus();
                         return false;
                     }
            else{
                    if(spl[0]>01 || spl[0]==01){
                        var hcode=parseInt(document.getElementById("txtAcc_HeadCode").value)/10000;
                        var spObj=hcode.toString().split(".");
                        if(spObj[0]=="82")
                        {
                         alert("This Account HeadCode can not be used Here");
                         document.getElementById("txtAcc_HeadCode").value="";
                         document.getElementById("txtAcc_HeadDesc").value="";
                        }
                        else
                        {
                       // doFunction('checkCode','null');
                            doFunctionBLOCK('checkCode','null');
                        }
                    }
    }
    }
    else
    {
    var hcode=document.getElementById("txtAcc_HeadCode").value;
     if(hcode==900108 || hcode==900109 ||hcode==901002)
     {
     alert(hcode+" AccountHeadCode cannot be used Here");
     document.getElementById("txtAcc_HeadCode").value="";
     document.getElementById("txtAcc_HeadDesc").value="";
     return false;
     }else if((hcode==390302) ||(hcode==390303) || (hcode==390305) || (hcode==391002) ||(hcode==391003) ||(hcode==391302) || (hcode==391303) ||(hcode==391502) ||(hcode==391503) )
     {			
      	  alert("GPF Account Head Code cannot be used here***");
            document.getElementById("txtAcc_HeadCode").value="";
            document.getElementById("txtAcc_HeadCode").focus();
            return false;
        }
   // doFunction('checkCode','null');
     doFunctionBLOCK('checkCode','null');
    }
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
if (winAcc_Bank_No && winAcc_Bank_No.open && !winAcc_Bank_No.closed) winAcc_Bank_No.close();
if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
}


function byUnitAndOfficeChange()
{
    doFunction_voucher('load_Receipt_No','null');
}

function doFunction_voucher(Command,param)
{   
       var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
       var cmbOffice_code=document.getElementById("cmbOffice_code").value;
       var txtCrea_date= document.getElementById("txtCrea_date").value
       
       if(Command=="load_Receipt_No")
        {  
           clearGeneral_Detail();
            if(txtCrea_date.length!=0)
            {
            var url="../../../../../Bank_Receipt_Edition.view?Command=load_Receipt_No&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
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
        else if(Command=="load_Receipt_Details")
        {  
            clearGeneral_Detail();
            var  txtReceipt_No=document.getElementById("txtReceipt_No").value;
           
            if(txtReceipt_No!="")
            {
            var url="../../../../../Bank_Receipt_Edition.view?Command=load_Receipt_Details&txtReceipt_No="+txtReceipt_No+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
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
             
            if(Command=="load_Receipt_No")
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

/////////////////////////////////////////////   load Table() /////////////////////////////////////////////////////

function loadTable(scod)
{
          com_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
        clearall();
       // document.FasAcc_Headform.cmdadd.disabled=true;
       //document.getElementById("txtAcc_HeadCode").readOnly=true;//=true;
       //text_field.readOnly=true;
        var r=document.getElementById(scod);
        var rcells=r.cells;
        //
        try {document.getElementById("txtAcc_HeadCode").value=rcells.item(1).firstChild.value;}catch(e){}
      //   doFunction('checkCode','null');
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
              //  alert(com_cmbSL_type);
        setTimeout('document.getElementById("cmbSL_type").value=com_cmbSL_type',900); 
        setTimeout('document.getElementById("cmbSL_Code").value=com_cmbSL_Code',900); 
         if(rcells.item(2).firstChild.value=="CR")
         document.Bank_Receipt_Edit_Form.rad_sub_CR_DR[0].checked=true;
         else if(rcells.item(2).firstChild.value=="DR")
         document.Bank_Receipt_Edit_Form.rad_sub_CR_DR[1].checked=true;
         
       
        if(rcells.item(5).firstChild.value=="C")
         document.Bank_Receipt_Edit_Form.txtCheque_DD[0].checked=true;
        else if(rcells.item(5).firstChild.value=="D")
         document.Bank_Receipt_Edit_Form.txtCheque_DD[1].checked=true;
        else if(rcells.item(5).firstChild.value=="E")
         document.Bank_Receipt_Edit_Form.txtCheque_DD[2].checked=true;
         
         
         
        // alert("U"+"U"+rcells.item(5).nextChild.value)
        
       try{document.getElementById("txtCheque_DD_NO").value=rcells.item(5).firstChild.nextSibling.value;}catch(e){}
       try{document.getElementById("txtCheque_DD_date").value=rcells.item(6).firstChild.value;}catch(e){}
       
       
       try{document.getElementById("txtBank_Name").value=rcells.item(7).firstChild.value;}catch(e){}
       var nex=rcells.item(7).firstChild.nextSibling  
       
        try{document.getElementById("txtDraw_BR").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
       try{document.getElementById("txtBank_M_Code").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
        //try{document.getElementById("txtsub_Recei_from").value=nex.value;}catch(e){}
         //nex=nex.nextSibling;
       try{document.getElementById("txtsub_Amount").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
       try{document.getElementById("txtParticular").value=nex.value;}catch(e){}
       nex=nex.nextSibling;
       try{document.getElementById("txtCB_Year").value=nex.value;}catch(e){}
       nex=nex.nextSibling;
       try{document.getElementById("txtCB_Month").value=nex.value;}catch(e){}
       
    document.Bank_Receipt_Edit_Form.cmdupdate.style.display='block';
    document.Bank_Receipt_Edit_Form.cmddelete.disabled=false;
    document.Bank_Receipt_Edit_Form.cmdadd.style.display='none';
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
                    if(code1[0]==900108)
                    {
                        if(units=="Accounting Units")
                        {
                        if(code1[0]==txtAcc_HeadCode){
                            var offcode=cells.item(4).lastChild.nodeValue;
                           var chkslcode= document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text;
                       
                           if(offcode==chkslcode)
                            {
                            alert("Already Added For this A/c unit. Choose Different Unit For 900108");
                             return false;
                            }
                            }
                        }
                    }
                }
       }
        
         /*if(document.Bank_Receipt_Edit_Form.rad_sub_CR_DR[1].checked==true)
        {
            alert("Debit amount not allowed");
            return false;
        }*/
       /* if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
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
       
//       if ( isMan.account_head_status) 
//       {
//        
//        if(document.getElementById("cmbSL_type").value=="")
//        {
//            alert("Select The Sub Ledger Type")       ;
//            document.getElementById("cmbSL_type").focus();
//            return false;        
//        }
//        
//        if(document.getElementById("cmbSL_Code").value=="")
//        {
//            alert("Select The Sub Ledger Code")       ;
//            document.getElementById("cmbSL_Code").focus();
//            return false;        
//        }        
//       } 
//       else
//       {
//            if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
//            {
//                if(window.confirm("You have not selected Sub-Ledger Type \n Do you want to select it,click 'OK'?"))
//                    {
//                        if(document.getElementById("cmbSL_type").value=="")
//                            {
//                                alert("Select a Sub-Ledger Type");
//                                return false;
//                            } 
//                    }
//                    else
//                    {
//                     
//                    }          
//            }
//            if(document.getElementById("cmbSL_type").value!="")
//            {
//                if(document.getElementById("cmbSL_Code").value=="")
//                    {
//                        alert("Select The Sub Ledger Code")       ;
//                        document.getElementById("cmbSL_Code").focus();
//                        return false;
//                    }
//            }       
//        }
     
     //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      
         
 if(document.Bank_Receipt_Edit_Form.txtCheque_DD[2].checked==false)
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
        
        
        
        if(document.getElementById("txtBank_Name").value.length==0)
        {
        alert("Enter the Bank Name");
         document.getElementById("txtBank_Name").focus();
        return false;
        }
        if(document.getElementById("txtDraw_BR").value.length==0)
        {
        alert("Enter the Drawee Branch");
         document.getElementById("txtDraw_BR").focus();
        return false;
        }
        /* if(document.getElementById("txtBank_M_Code").value.length==0)
        {
        alert("Enter the Bank MICR Code");
         document.getElementById("txtBank_M_Code").focus();
        return false;
        }
       
        if(document.getElementById("txtsub_Recei_from").value.length==0)
        {
            alert("Enter the value in  ' Received From Field ' ");
            document.getElementById("txtsub_Recei_from").focus();
            return false;    
        }
        */
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            document.getElementById("txtsub_Amount").focus();
            return false;    
        }
        var tbody=document.getElementById("grid_body");
                //alert("CODE"+document.getElementById("txtSL_Desc").value);
                //alert("TEXT"+document.getElementById("txtSL_Desc").options[document.getElementById("txtSL_Desc").selectedIndex].text);
               //alert("AGA"+document.getElementById("txtSL_Desc").text)
        var t=0;
        var exist=document.getElementById("txtAcc_HeadCode").value;
       
        var items=new Array();
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;
        if(document.Bank_Receipt_Edit_Form.rad_sub_CR_DR[0].checked==true)
          items[2]=document.Bank_Receipt_Edit_Form.rad_sub_CR_DR[0].value;
        else if(document.Bank_Receipt_Edit_Form.rad_sub_CR_DR[1].checked==true)
          items[2]=document.Bank_Receipt_Edit_Form.rad_sub_CR_DR[1].value;
        
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
        
        
        if(document.Bank_Receipt_Edit_Form.txtCheque_DD[0].checked==true)
          items[7]=document.Bank_Receipt_Edit_Form.txtCheque_DD[0].value;
        else if(document.Bank_Receipt_Edit_Form.txtCheque_DD[1].checked==true)
          items[7]=document.Bank_Receipt_Edit_Form.txtCheque_DD[1].value;
        else if(document.Bank_Receipt_Edit_Form.txtCheque_DD[2].checked==true)
          items[7]=document.Bank_Receipt_Edit_Form.txtCheque_DD[2].value;   
          
          
        items[8]=document.getElementById("txtCheque_DD_NO").value;
        items[9]=document.getElementById("txtCheque_DD_date").value;
        
        items[10]=document.getElementById("txtBank_Name").value;
        items[11]=document.getElementById("txtDraw_BR").value;
        items[12]=document.getElementById("txtBank_M_Code").value;
        
      //  items[13]=document.getElementById("txtsub_Recei_from").value;
        items[14]=document.getElementById("txtsub_Amount").value;
        items[15]=document.getElementById("txtParticular").value;
        
        items[16]=document.getElementById("txtCB_Year").value;
        items[17]=document.getElementById("txtCB_Month").value;
        
        //items[0]=document.getElementById("txtSL_code").value;
        //items[1]=document.getElementById("txtSL_Desc").options[document.getElementById("txtSL_Desc").selectedIndex].text;                
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

            //alert("hello "+i+"  " + items[i])
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
                  Bank_Name.name="Bank_Name";
                  Bank_Name.value=items[10];
           // Bank_Name.style.display='none';
                  cell2.appendChild(Bank_Name);
                    
              var Draw_BR=document.createElement("input");
                  Draw_BR.type="hidden";
                  Draw_BR.name="Draw_BR";
                  Draw_BR.value=items[11];
        // Draw_BR.style.display='none';
                  cell2.appendChild(Draw_BR);
              
                var Bank_M_Code=document.createElement("input");
                  Bank_M_Code.type="hidden";
                  Bank_M_Code.name="Bank_M_Code";
                  Bank_M_Code.value=items[12];
       // Bank_M_Code.style.display='none';
                  cell2.appendChild(Bank_M_Code);
               
           /*
            var rec_from=document.createElement("input");
                  rec_from.type="hidden";
                  rec_from.name="rec_from";
                  rec_from.value=items[13];
                 rec_from.style.display='none';
                  cell2.appendChild(rec_from);
              
            */    
                
                 var sl_amt=document.createElement("input");
                  sl_amt.type="hidden";
                  sl_amt.name="sl_amt";
                  sl_amt.value=items[14];
                  cell2.appendChild(sl_amt);

                  var particular=document.createElement("input");           // Particulars Added to grid b4 the Amount Text Node but after  amount hidden box    
                  particular.type="hidden";
                  particular.name="particular";
                  particular.value=items[15];
            //    particular.style.display='none';
                  cell2.appendChild(particular);
                  
                  var sl_adjyear=document.createElement("input");
                  sl_adjyear.type="hidden";
                  sl_adjyear.name="adjyear";
                  sl_adjyear.value=items[16];
                  cell2.appendChild(sl_adjyear);
                  
                  var sl_adjmonth=document.createElement("input");
                  sl_adjmonth.type="hidden";
                  sl_adjmonth.name="adjmonth";
                  sl_adjmonth.value=items[17];
                  cell2.appendChild(sl_adjmonth);

                  var currentText=document.createTextNode(items[14]);
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
document.Bank_Receipt_Edit_Form.rad_sub_CR_DR[0].checked=true;
document.getElementById("cmbSL_type").value="";
document.getElementById("cmbSL_Code").value="";
//document.Bank_Receipt_Edit_Form.txtCheque_DD[0].checked=true;
//document.getElementById("txtCheque_DD_NO").value="";
//document.getElementById("txtCheque_DD_date").value="";
//document.getElementById("txtBank_Name").value="";
//document.getElementById("txtDraw_BR").value="";
//document.getElementById("txtBank_M_Code").value="";
//document.getElementById("txtsub_Recei_from").value="";
document.getElementById("txtsub_Amount").value="";
document.getElementById("txtParticular").value="";


document.getElementById("txtCB_Year").value="";
document.getElementById("txtCB_Month").value="";

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


 document.Bank_Receipt_Edit_Form.cmdadd.style.display='block';
 document.Bank_Receipt_Edit_Form.cmdupdate.style.display='none';
 document.Bank_Receipt_Edit_Form.cmddelete.disabled=true;
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
                    if(code1[0]==900108)
                    {
                        if(units=="Accounting Units")
                        {
                        if(code1[0]==txtAcc_HeadCode){
                            var offcode=cells.item(4).lastChild.nodeValue;
                           var chkslcode= document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text;
                       
                           if(offcode==chkslcode)
                            {
                            alert("Already Added For this A/c unit. Choose Different Unit For 900108");
                             return false;
                            }
                            }
                        }
                    }
                }
       }
        
        /* if(document.Bank_Receipt_Edit_Form.rad_sub_CR_DR[1].checked==true)
        {
            alert("Debit amount not allowed");
            return false;
        }
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
        
        */
     
        
if(document.Bank_Receipt_Edit_Form.txtCheque_DD[2].checked==false)
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
        
        
        
        if(document.getElementById("txtBank_Name").value.length==0)
        {
        alert("Enter the Bank Name");
         document.getElementById("txtBank_Name").focus();
        return false;
        }
        if(document.getElementById("txtDraw_BR").value.length==0)
        {
        alert("Enter the Drawee Branch");
         document.getElementById("txtDraw_BR").focus();
        return false;
        }
        /* if(document.getElementById("txtBank_M_Code").value.length==0)
        {
        alert("Enter the Bank MICR Code");
         document.getElementById("txtBank_M_Code").focus();
        return false;
        }
        
        if(document.getElementById("txtsub_Recei_from").value.length==0)
        {
            alert("Enter the value in  ' Received From Field ' ");
            document.getElementById("txtsub_Recei_from").focus();
            return false;    
        }
        */
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
        if(document.Bank_Receipt_Edit_Form.rad_sub_CR_DR[0].checked==true)
          items[2]=document.Bank_Receipt_Edit_Form.rad_sub_CR_DR[0].value;
        else if(document.Bank_Receipt_Edit_Form.rad_sub_CR_DR[1].checked==true)
          items[2]=document.Bank_Receipt_Edit_Form.rad_sub_CR_DR[1].value;
        
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
        
        if(document.Bank_Receipt_Edit_Form.txtCheque_DD[0].checked==true)
          items[7]=document.Bank_Receipt_Edit_Form.txtCheque_DD[0].value;
        else if(document.Bank_Receipt_Edit_Form.txtCheque_DD[1].checked==true)
          items[7]=document.Bank_Receipt_Edit_Form.txtCheque_DD[1].value;
        else if(document.Bank_Receipt_Edit_Form.txtCheque_DD[2].checked==true)
          items[7]=document.Bank_Receipt_Edit_Form.txtCheque_DD[2].value;
          
          
          
        items[8]=document.getElementById("txtCheque_DD_NO").value;
        items[9]=document.getElementById("txtCheque_DD_date").value;
        
        
        
        items[10]=document.getElementById("txtBank_Name").value;
        items[11]=document.getElementById("txtDraw_BR").value;
        items[12]=document.getElementById("txtBank_M_Code").value;
        
      //  items[13]=document.getElementById("txtsub_Recei_from").value;
        items[14]=document.getElementById("txtsub_Amount").value;
        items[15]=document.getElementById("txtParticular").value;
       
        items[16]=document.getElementById("txtCB_Year").value;
        items[17]=document.getElementById("txtCB_Month").value;
        
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
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[12];
       // var nex_cell=nex_cell.nextSibling;
       // nex_cell.value=items[13];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[14];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[15];
        
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[16];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[17];
        
        rcells.item(7).lastChild.nodeValue=items[14];
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
document.Bank_Receipt_Edit_Form.rad_sub_CR_DR[0].checked=true;
document.getElementById("cmbSL_type").value="";
document.getElementById("cmbSL_Code").value="";
document.Bank_Receipt_Edit_Form.txtCheque_DD[1].checked=true;
document.getElementById("txtCheque_DD_NO").value="";
document.getElementById("txtCheque_DD_date").value="";
document.getElementById("txtBank_Name").value="";
document.getElementById("txtDraw_BR").value="";
document.getElementById("txtBank_M_Code").value="";
//document.getElementById("txtsub_Recei_from").value="";
document.getElementById("txtsub_Amount").value="";
document.getElementById("txtParticular").value="";


document.getElementById("txtCB_Year").value="";
document.getElementById("txtCB_Month").value="";

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
    

 document.Bank_Receipt_Edit_Form.cmdadd.style.display='block';
 document.Bank_Receipt_Edit_Form.cmdupdate.style.display='none';
 document.Bank_Receipt_Edit_Form.cmddelete.disabled=true;
}


function clearGeneral_Detail()
{ 
    //document.getElementById("txtReceipt_No").value="";  
    document.getElementById("txtBankAccountNo").value="";
    document.getElementById("txtBankName").value="";
    document.getElementById("txtBankId").value="";
    document.getElementById("txtBranchId").value="";
    
    document.getElementById("txtAmount").value="";
    document.getElementById("txtRef_no").value="";
    document.getElementById("txtRef_date").value="";
    document.getElementById("txtRemarks").value="";
        document.getElementById("txtRecei_from").value="";

   /*
    document.getElementById("txtAuth_By").value="";
    document.getElementById("Auth_By").value="";
    document.getElementById("txtReferNO_edit").value="";
    document.getElementById("txtReferDate_edit").value="";
    document.getElementById("txtRemak_edit").value="";
    */
    document.getElementById("cmbMas_SL_type").value=""; 
    clear_Combo(document.getElementById("cmbMas_SL_Code"));
    var tbody=document.getElementById("grid_body");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }

}
function call_clr()
{
   document.getElementById("txtReceipt_No").value="";  
   clearGeneral_Detail();
}


function clrForm()
{
 if(window.confirm("Do you want to clear ALL fields ?"))
 {
  call_clr();
 }
}


function checkNull()
{
var tbody=document.getElementById("grid_body");
   //alert("tbody.rows.length :"+tbody.rows.length);   
//alert(document.getElementById("txtReferNO_edit").value.length)
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

if(document.getElementById("txtReceipt_No").value=="")
{
    alert("Select the Receipt Number");
    //document.getElementById("txtReceipt_No").focus();
    return false;    
}
if(document.getElementById("txtCash_Acc_code").value.length==0 || document.getElementById("txtCash_Acc_code").value==0)
{
    alert("Enter the Collection A/c Code");
    //document.getElementById("txtCash_Acc_code").focus();
    return false;
}

if(document.getElementById("txtBankAccountNo").value.length==0 || document.getElementById("txtBankAccountNo").value==0)
{
    alert("Enter the Account Number");
    //document.getElementById("txtRecei_from").focus();
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


 if(document.getElementById("txtRecei_from").value.length==0)
{
    alert("Enter the value in  ' Received From Field ' in General part");
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
if(document.getElementById("txtAmount").value.length==0)
{
    alert("Enter the Total Amount in General");
    //document.getElementById("txtAmount").focus();
    return false;    
}
if(tbody.rows.length==0)
{
    alert("Enter the Amount Details under Details");
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
//alert(document.getElementById("txtReferNO_edit").value.length);
/*if(document.getElementById("txtReferNO_edit").value.length==0)
{
    alert("Enter the Reference Number in Modification Details");
    //document.getElementById("txtReferNO_edit").focus();
    return false;    
}
if(document.getElementById("txtReferDate_edit").value.length==0)
{
    alert("Enter the Reference Date in Modification Details");
    //document.getElementById("txtReferDate_edit").focus();
    return false;
}

 if(document.getElementById("txtRemak_edit").value.length==0)
{
    alert("Enter the Remarks in Modification Details");
   // document.getElementById("txtRemak_edit").focus();
    return false;
}*/
if(tbody.rows.length>0)
{
        var check_amt=0;
        rows=tbody.getElementsByTagName("tr");
        for(i=0;i<rows.length;i++)
        {
            var cells=rows[i].cells;
        if(cells.item(2).lastChild.nodeValue=='DR')
           check_amt=parseFloat(check_amt) - parseFloat(cells.item(7).lastChild.nodeValue);
        else
            check_amt=parseFloat(check_amt) + parseFloat(cells.item(7).lastChild.nodeValue);
        }
        //alert(document.getElementById("txtAmount").value+"  "+check_amt);
          check_amt=Math.abs(check_amt);
        if(parseFloat(document.getElementById("txtAmount").value)!=parseFloat(check_amt))
        {
        alert("Amount doesn't Tally.. Difference " +(parseFloat(document.getElementById("txtAmount").value)-parseFloat(check_amt)))
        return false;
        }
}
return true;
}




//////////////////////////////////////////////////  For listing  Receipt Date and Number for Edit
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
    }
}
function load_Receipt_Details(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    //alert("FF");
    if(flag=="success")
    {
      var MasHeadCode=baseResponse.getElementsByTagName("MasHeadCode")[0].firstChild.nodeValue;
      if(baseResponse.getElementsByTagName("Ref_No")[0].firstChild!=null)
      var Ref_No=baseResponse.getElementsByTagName("Ref_No")[0].firstChild.nodeValue;
      if(baseResponse.getElementsByTagName("Ref_Date")[0].firstChild!=null)
      var Ref_Date=baseResponse.getElementsByTagName("Ref_Date")[0].firstChild.nodeValue;
      if(baseResponse.getElementsByTagName("accNo")[0]!=undefined)
       var accNo=baseResponse.getElementsByTagName("accNo")[0].firstChild.nodeValue;
        if(baseResponse.getElementsByTagName("bk_name")[0]!=undefined)
       var bk_name=baseResponse.getElementsByTagName("bk_name")[0].firstChild.nodeValue;
       if(baseResponse.getElementsByTagName("bk_id")[0]!=undefined)
       var bk_id=baseResponse.getElementsByTagName("bk_id")[0].firstChild.nodeValue;
       if(baseResponse.getElementsByTagName("br_id")[0]!=undefined)
       var br_id=baseResponse.getElementsByTagName("br_id")[0].firstChild.nodeValue;
      if(baseResponse.getElementsByTagName("Total_amt")[0]!=undefined)
       var Total_amt=baseResponse.getElementsByTagName("Total_amt")[0].firstChild.nodeValue;
        if(baseResponse.getElementsByTagName("Rec_From")[0]!=undefined)
       var Rec_From=baseResponse.getElementsByTagName("Rec_From")[0].firstChild.nodeValue;
        if(baseResponse.getElementsByTagName("Remak")[0].firstChild!=null)
       var Remak=baseResponse.getElementsByTagName("Remak")[0].firstChild.nodeValue;
      if(baseResponse.getElementsByTagName("Mas_SL_type")[0]!=undefined)
       var Mas_SL_type=baseResponse.getElementsByTagName("Mas_SL_type")[0].firstChild.nodeValue;
       if(baseResponse.getElementsByTagName("Mas_SL_code")[0]!=undefined)
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
       
      if(Ref_No!="null")
      document.getElementById("txtRef_no").value=Ref_No;
      else
      document.getElementById("txtRef_no").value="";
      
       if(Ref_Date!="null")
       document.getElementById("txtRef_date").value=Ref_Date;
       else
      document.getElementById("txtRef_date").value="";
      document.getElementById("txtCash_Acc_code").value=MasHeadCode
      document.getElementById("txtBankAccountNo").value=accNo;
      document.getElementById("txtBankName").value=bk_name;
      document.getElementById("txtBankId").value=bk_id;
      document.getElementById("txtBranchId").value=br_id;
      
       document.getElementById("txtAmount").value=Total_amt;
       if(baseResponse.getElementsByTagName("Rec_From")[0]!=undefined)
       var Rec_From=baseResponse.getElementsByTagName("Rec_From")[0].firstChild.nodeValue;
       if(baseResponse.getElementsByTagName("MO_creation")[0]!=undefined)
       var MO_creation=baseResponse.getElementsByTagName("MO_creation")[0].firstChild.nodeValue;
                   
     /*---------------------------------------------------------------------------------*/        
//       if(MO_creation=='S')
//       {
//        document.getElementById("txtAmount").readOnly=true;
//        document.Bank_Receipt_Edit_Form.rad_ReClass[0].checked=true;
//       }
//       else
//       {
//        document.getElementById("txtAmount").readOnly=false;
//        document.Bank_Receipt_Edit_Form.rad_ReClass[1].checked=true;
//       } 
       
        /*
        *  Remittance in Current Month
        */
        if(baseResponse.getElementsByTagName("rem_in_curr_month")[0]!=undefined)
       var rem_in_curr_month=baseResponse.getElementsByTagName("rem_in_curr_month")[0].firstChild.nodeValue;
       
       if(rem_in_curr_month=='Y')
        document.Bank_Receipt_Edit_Form.rem_current_month[0].checked=true;
       else
        document.Bank_Receipt_Edit_Form.rem_current_month[1].checked=true;
                  
    /*---------------------------------------------------------------------------------*/          
       
       if(Rec_From!="null")
      document.getElementById("txtRecei_from").value=Rec_From;
      else
      document.getElementById("txtRecei_from").value="";
     
      
       if(Remak!="null")
         document.getElementById("txtRemarks").value=Remak;
        else
        document.getElementById("txtRemarks").value="";
       
       //var miHC =baseResponse.getElementsByTagName("miHC")[0].firstChild.nodeValue;
       
       var tbody=document.getElementById("grid_body");
                        var t=0;
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
                        
         //var SLCODE=baseResponse.getElementsByTagName("SLCODE");
        
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
        if ( items[7] == "null") 
        items[7]="";
        
        
        items[8]=baseResponse.getElementsByTagName("che_DD_no")[k].firstChild.nodeValue;
        if ( items[8] == "null") 
        items[8]="";
        
           
        items[9]=baseResponse.getElementsByTagName("che_DD_date")[k].firstChild.nodeValue;
        if ( items[9] == "null") 
        items[9]="";
        
        
        
        
        items[10]=baseResponse.getElementsByTagName("bank_na")[k].firstChild.nodeValue;
        items[11]=baseResponse.getElementsByTagName("drawee")[k].firstChild.nodeValue;
        if(baseResponse.getElementsByTagName("micr")[k].firstChild!=null)
        items[12]=baseResponse.getElementsByTagName("micr")[k].firstChild.nodeValue;
        
        if(items[12]=="null")
        items[12]="";
        
        //items[13]=baseResponse.getElementsByTagName("sub_rec_frm")[k].firstChild.nodeValue;
        
        if(baseResponse.getElementsByTagName("sub_rec_frm")[k].firstChild == null){
			items[13]="";
		}else{
			items[13]=baseResponse.getElementsByTagName("sub_rec_frm")[k].firstChild.nodeValue;
		}
		
        
        
        
       // if(items[13]=="null")
       // items[13]="";
        items[14]=baseResponse.getElementsByTagName("sub_amount")[k].firstChild.nodeValue;
        if(items[15]=baseResponse.getElementsByTagName("sub_part")[k]=null)
        items[15]=baseResponse.getElementsByTagName("sub_part")[k].firstChild.nodeValue;
        
        items[16]=baseResponse.getElementsByTagName("adjyear")[k].firstChild.nodeValue;
        items[17]=baseResponse.getElementsByTagName("adjmonth")[k].firstChild.nodeValue;
        
        if(items[16]==0)
            items[16]="";
        if(items[17]==0)
            items[17]="";
        
        if(items[15]=="null")
        items[15]="";
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

            //alert("hello "+i+"  " + items[i])
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
                  Bank_Name.name="Bank_Name";
                  Bank_Name.value=items[10];
           // Bank_Name.style.display='none';
                  cell2.appendChild(Bank_Name);
                    
              var Draw_BR=document.createElement("input");
                  Draw_BR.type="hidden";
                  Draw_BR.name="Draw_BR";
                  Draw_BR.value=items[11];
        // Draw_BR.style.display='none';
                  cell2.appendChild(Draw_BR);
              
                var Bank_M_Code=document.createElement("input");
                  Bank_M_Code.type="hidden";
                  Bank_M_Code.name="Bank_M_Code";
                  Bank_M_Code.value=items[12];
       // Bank_M_Code.style.display='none';
                  cell2.appendChild(Bank_M_Code);
              /* 
                var rec_from=document.createElement("input");
                  rec_from.type="hidden";
                  rec_from.name="rec_from";
                  rec_from.value=items[13];
                rec_from.style.display='none';
                  cell2.appendChild(rec_from);
              */
                
                
                 var sl_amt=document.createElement("input");
                  sl_amt.type="hidden";
                  sl_amt.name="sl_amt";
                  sl_amt.value=items[14];
                  cell2.appendChild(sl_amt);

                  var particular=document.createElement("input");           // Particulars Added to grid b4 the Amount Text Node but after  amount hidden box    
                  particular.type="hidden";
                  particular.name="particular";
                  particular.value=items[15];
            //    particular.style.display='none';
                  cell2.appendChild(particular);
                  
                  var sl_adjyear=document.createElement("input");
                  sl_adjyear.type="hidden";
                  sl_adjyear.name="adjyear";
                  sl_adjyear.value=items[16];
                  cell2.appendChild(sl_adjyear);
                  
                  var sl_adjmonth=document.createElement("input");
                  sl_adjmonth.type="hidden";
                  sl_adjmonth.name="adjmonth";
                  sl_adjmonth.value=items[17];
                  cell2.appendChild(sl_adjmonth);
                  
                  var currentText=document.createTextNode(items[14]);
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
                 doFunction_voucher('load_Receipt_No','null');                 //return true;
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
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

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


function checkdate()
{
	var vdate=document.getElementById("txtCrea_date").value;
	var mytool_array=vdate.split("/");
	
	
	
    var txtCB_Year=document.getElementById("txtCB_Year").value;
    var txtCB_Month=document.getElementById("txtCB_Month").value;
   var fl=0; 
   
   if(txtCB_Year!="")
   {
    if(parseInt(txtCB_Year)>parseInt(mytool_array[2]))
    {
    	alert('Receipt Date should greater than or equal to Relative Cash Book Month and year ');
		return false;
		fl=1;
    }
    if(parseInt(txtCB_Year)==parseInt(mytool_array[2]))
    {
    	if(parseInt(mytool_array[1])<parseInt(txtCB_Month))
    	{
    		alert('Receipt Date should greater than or equal to Relative Cash Book Month and year');
    		return false;
    		fl=1;
    	}
    }
   }else{
	   alert('Please Enter Relative Year');
	   document.getElementById("txtCB_Month").value="";
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
                                        	var unitcode=document.getElementById("cmbAcc_UnitCode").value;
                                        	if(unitcode!=5)
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
                                       
                                        }
                                        else if(M_flag=="Not_Madatory")
                                        {
                                        	var unitcode=document.getElementById("cmbAcc_UnitCode").value;
                                        	if(unitcode!=5)
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
