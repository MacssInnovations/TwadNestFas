
var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;
var job_flag;
var common_AHead_code_flag="";
var emp_flag;
/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////

window.onunload=function()
{
if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (winemp && winemp.open && !winemp.closed) winemp.close();
}

/////////////////////////////////////////////   load Table() /////////////////////////////////////////////////////

function DCBloadTable(scod)
{
        com_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
        clearall();
        var r=document.getElementById(scod);
        var rcells=r.cells;
        try {document.getElementById("txtAcc_HeadCode").value=rcells.item(1).firstChild.value;}catch(e){}
        try{common_cmbSL_type=rcells.item(3).firstChild.value;} catch(e){common_cmbSL_type=""}
        
        try{common_cmbSL_Code=rcells.item(4).firstChild.value;} catch(e){common_cmbSL_Code=""} 
         if(common_cmbSL_type==5)   // Office
         {
              document.getElementById("txtOfficeID_trs").value=common_cmbSL_Code;
              job_flag=false;
              //doFunction('office',common_cmbSL_Code);
              //doFunction('Load_SL_Code',);
         }
         if(common_cmbSL_type==7)       // Employee
         {
              document.getElementById("txtEmpID_trs").value=common_cmbSL_Code;
              emp_flag=false;
              //doFunction('office',common_cmbSL_Code);
              //doFunction('Load_SL_Code',);
         }
                doFunction('checkCode','null');
                doFunction('Load_SL_Code',common_cmbSL_type);
        
         if(rcells.item(2).firstChild.value=="CR")
         document.DCB_Receipt_Form.rad_sub_CR_DR[0].checked=true;
         else if(rcells.item(2).firstChild.value=="DR")
         document.DCB_Receipt_Form.rad_sub_CR_DR[1].checked=true;
         
       //try{document.getElementById("cmbSL_Code").value=rcells.item(4).firstChild.value;}catch(e){}
       //try{document.getElementById("txtsub_Recei_from").value=rcells.item(5).firstChild.value;}catch(e){}
       try{document.getElementById("txtsub_Amount").value=rcells.item(5).firstChild.value;}catch(e){}
       try{document.getElementById("txtParticular").value=rcells.item(6).firstChild.value;}catch(e){}
       
    document.DCB_Receipt_Form.cmdupdate.style.display='block';
    document.DCB_Receipt_Form.cmddelete.disabled=false;
    document.DCB_Receipt_Form.cmdadd.style.display='none';
}


/////////////////////////////////////////////   ADD & UPDATE & DELETE /////////////////////////////////////////////////////
function ADD_GRID_DCB()
{
	
	 
        if(document.getElementById("acheadcode").value.length==0)
        {
        alert("Enter A/c Head Code");
        return false;
        }
      
      
        
        var acc=document.getElementById("acheadcode").value;
        var kk=acc.charAt(0)+acc.charAt(1);
       
        if(kk=="82")
        {
          if(acc !="820102"  && acc !="820103")
          {
          //  alert("You are not allow to use this Account Head Code "+acc);
            alert("This A/C code can not be used here ");
            document.getElementById("txtAcc_HeadCode").value="";
            document.getElementById("txtAcc_HeadDesc").value="";
            return false;
          }  
        }
        
        
        
      
     //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
         
       if(document.getElementById("acheadcode").value=="")
       {
            alert("Please Wait Account Head is Loading .......................");            
            return false;        
       }       
      
     
      
       if ( isMan.account_head_status ) 
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
      
      
     
        
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            //document.getElementById("txtAmount").focus();
            return false;    
        }
        var tbody=document.getElementById("grid_body");
                             
        var t=0;
        var exist=document.getElementById("acheadcode").value;
     
        var items=new Array();
        items[0]=document.getElementById("acheadcode").value;
        items[1]=document.getElementById("acheadcode").value;
        if(document.DCB_Receipt_Form.rad_sub_CR_DR[0].checked==true)
          items[2]=document.DCB_Receipt_Form.rad_sub_CR_DR[0].value;
        else if(document.DCB_Receipt_Form.rad_sub_CR_DR[1].checked==true)
          items[2]=document.DCB_Receipt_Form.rad_sub_CR_DR[1].value;
        
        items[3]=document.getElementById("cmbSL_type").value;
        if(document.getElementById("cmbSL_type").value=="")
        {
        //items[4]="Not Available";
        items[4]="";//document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        }
        else
        items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        
        items[5]=document.getElementById("cmbSL_Code").value;
        if(document.getElementById("cmbSL_Code").value=="")
        {
        items[6]="";//"Not Available";
        }
        else
        items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
        
        //items[7]=document.getElementById("txtsub_Recei_from").value;
        items[7]=document.getElementById("txtsub_Amount").value;
        items[8]=document.getElementById("txtParticular").value;
        
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
                  var sl_amt=document.createElement("input");
                  sl_amt.type="hidden";
                  sl_amt.name="sl_amt";
                  sl_amt.value=items[7];
                  cell2.appendChild(sl_amt);
                  var currentText=document.createTextNode(items[7]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
              
              cell2=document.createElement("TD");
             
                  var particular=document.createElement("input");
                  particular.type="hidden";
                  particular.name="particular";
                  particular.value=items[8];
                  cell2.appendChild(particular);
                  var currentText=document.createTextNode(items[8]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);

        tbody.appendChild(mycurrent_row);
         clearall();
}


function DCBupdate_GRID()
{      
        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
        alert("Enter A/c Head Code");
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
            alert("Select The Sub Ledger Code");
            return false;
            }
        }
        
      
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            document.getElementById("txtsub_Amount").focus();
            return false;    
        }
        var exist=document.getElementById("txtAcc_HeadCode").value;
        var items=new Array();
       
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;
        if(document.DCB_Receipt_Form.rad_sub_CR_DR[0].checked==true)
          items[2]=document.DCB_Receipt_Form.rad_sub_CR_DR[0].value;
        else if(document.DCB_Receipt_Form.rad_sub_CR_DR[1].checked==true)
          items[2]=document.DCB_Receipt_Form.rad_sub_CR_DR[1].value;
        items[3]=document.getElementById("cmbSL_type").value;
        if(document.getElementById("cmbSL_type").value=="")
        {
        //items[4]="Not Available";
        items[4]="";//document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        //alert("code"+items[4]+"ff");
        }
        else
        items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        
        items[5]=document.getElementById("cmbSL_Code").value;
        if(document.getElementById("cmbSL_Code").value=="")
        {
        items[6]="";//"Not Available";
        }
        else
        items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
        //items[7]=document.getElementById("txtsub_Recei_from").value;
        items[7]=document.getElementById("txtsub_Amount").value;
        items[8]=document.getElementById("txtParticular").value;
        
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
            
              //  try{rcells.item(5).firstChild.value=items[7];}catch(e){}
               // try{rcells.item(5).lastChild.nodeValue=items[7];}catch(e){}
            
                try{rcells.item(5).firstChild.value=items[7];}catch(e){}
                try{rcells.item(5).lastChild.nodeValue=items[7];}catch(e){}
             
                try{rcells.item(6).firstChild.value=items[8];}catch(e){}
                try{rcells.item(6).lastChild.nodeValue=items[8];}catch(e){}
            
            
        alert("Record Updated");
        clearall();
  }

function DCBdelete_GRID()
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

function DCBclearall()
{
     document.getElementById("offlist_div_trans").style.display='none';
     document.getElementById("emplist_div_trans").style.display='none';
     
    document.getElementById("txtAcc_HeadCode").value="";
    //document.getElementById("txtAcc_HeadCode").readOnly=false;
    document.getElementById("txtAcc_HeadDesc").value="";
    document.DCB_Receipt_Form.rad_sub_CR_DR[0].checked=true;
    document.getElementById("cmbSL_type").value="";
    document.getElementById("cmbSL_Code").value="";
    //document.getElementById("txtsub_Recei_from").value="";
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

 document.DCB_Receipt_Form.cmdadd.style.display='block';
 document.DCB_Receipt_Form.cmdupdate.style.display='none';
 document.Cash_Receipt_Form.cmddelete.disabled=true;
}

function DCBcall_clr()
{
    document.getElementById("txtAmount").value="";
    document.getElementById("txtRef_no").value="";
    document.getElementById("txtRef_date").value="";
    document.getElementById("txtRemarks").value="";
    document.getElementById("cmbMas_SL_type").value="";
    clear_Combo(document.getElementById("cmbMas_SL_Code"));
    document.getElementById("txtRecei_from").value="";
    var tbody=document.getElementById("grid_body");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
}
function DCBclrForm()
{
   if(window.confirm("Do you want to clear ALL fields ?"))
 {
    call_clr();
 }
}

/////////////////////////////////////////////   checkNull() by User /////////////////////////////////////////////////////


function DCBcheckNull()
{
        var tbody=document.getElementById("grid_body");
           //alert("tbody.rows.length :"+tbody.rows.length);   
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
        if(document.getElementById("txtCash_Acc_code").value.length==0)
        {
            alert("Enter the Cash A/c Code");
            //document.getElementById("txtCash_Acc_code").focus();
            return false;
        }
        
        
        
        if(document.getElementById("txtRecei_from").value.length==0)
        {
            alert("Enter the value in ' Received From Field ' in General part");
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
            alert("Enter the Details Part");
            //document.getElementById("txtAmount").focus();
            return false; 
        }
        
               
        if(tbody.rows.length>0)
        {
                var check_amt=0;
                rows=tbody.getElementsByTagName("tr");
                for(i=0;i<rows.length;i++)
                {
                    var cells=rows[i].cells;
                    //alert(cells.item(2).lastChild.nodeValue);
                  if(cells.item(2).lastChild.nodeValue=='DR')
                  {
                      check_amt=parseFloat(check_amt) - parseFloat(cells.item(5).firstChild.value);
                    // alert("i am in if part "+check_amt);
                  }  
                  else
                  {
                    check_amt=parseFloat(check_amt) + parseFloat(cells.item(5).firstChild.value);
                   // alert(" im in else part "+check_amt);               
                  }
                  
                }
                  
              //  check_amt=Math.abs(check_amt);
                
              //  alert("after math operation"+check_amt);
                 
               // alert(document.getElementById("txtAmount").value+"  "+check_amt);
                if(parseFloat(document.getElementById("txtAmount").value)!=parseFloat(check_amt))
                {
                 alert("Amount doesn't Tally.. Difference " +(parseFloat(document.getElementById("txtAmount").value)-parseFloat(check_amt)))
                 return false; 
                }
        }
        return true;
}



///////////////////////////////////////////    TB_checking and Calender control return value handling



function DCBcall_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
    if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
    {
             call_clr();
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


function DCBcall_date(dateCtrl)                        // TB_checking 
{
	DCBcall_clr();
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
      document.getElementById("txtReceipt_No").value="";
    }
}



function DCBcheck_TB(req,dateCtrl)
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
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
                    dateCtrl.focus();
                    document.getElementById("txtReceipt_No").value="";
               }
             else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");//return false;//
                    dateCtrl.focus();
                    document.getElementById("txtReceipt_No").value="";     
               }
            
            dateCheck(dateCtrl);  
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





function DCBloadName_Mas(value)
{
	 
 if(document.getElementById("cmbMas_SL_Code").value=="")
    return;
value=document.getElementById("cmbMas_SL_Code").options[document.getElementById("cmbMas_SL_Code").selectedIndex].text; 
s=document.getElementById("cmbMas_SL_type").value;
if(s=="7" )
value=value.substring(0,value.indexOf("-"));

document.getElementById("txtRecei_from").value=value;
}


/* 
 * Check Remarks Field Length 
 */ 

function DCBcheck_leng(remarks,param)
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


/* 
 * Check Account Head Code and Bank Account Number 
 */
 
 function DCBaccount_head_code()
 {
    var acc_head=document.getElementById("txtAcc_HeadCode").value;
    var url="../../../../../DCBCash_Receipt_Creation?Command=Acc_Head_Check&acc_head="+acc_head;
    var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           Account_Headcode_Check(req);
        }   
        req.send(null);
 }
 
function DCBAccount_Headcode_Check(req)
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
               alert("You are not allow to use this Account Head Code");
               document.getElementById("txtAcc_HeadCode").value="";
               document.getElementById("txtAcc_HeadDesc").value="";
               
              }
       }
    }    
 }
 