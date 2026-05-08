var com_id="";
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

function loadTable(scod)
{
        com_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
        clearall();
        var r=document.getElementById(scod);
        var rcells=r.cells;
        try {document.getElementById("cmbvoc_type").value=rcells.item(1).firstChild.value;}catch(e){}
                
         if(rcells.item(2).firstChild.value=="CR")
         document.Ledger_System_Form.rad_sub_CR_DB[0].checked=true;
         else if(rcells.item(2).firstChild.value=="DB")
         document.Ledger_System_Form.rad_sub_CR_DB[1].checked=true;
         
       //try{document.getElementById("cmbSL_Code").value=rcells.item(4).firstChild.value;}catch(e){}
       //try{document.getElementById("txtsub_Recei_from").value=rcells.item(5).firstChild.value;}catch(e){}
       try{document.getElementById("txtvoc_no").value=rcells.item(3).firstChild.value;}catch(e){}
       try{document.getElementById("txtvoc_date").value=rcells.item(4).firstChild.value;}catch(e){}
       try{document.getElementById("txtsub_Amount").value=rcells.item(5).firstChild.value;}catch(e){}
       try{document.getElementById("txtRemarks").value=rcells.item(6).firstChild.value;}catch(e){}
       
    document.Ledger_System_Form.cmdupdate.style.display='block';
    document.Ledger_System_Form.cmddelete.disabled=false;
    document.Ledger_System_Form.cmdadd.style.display='none';
}


/////////////////////////////////////////////   ADD & UPDATE & DELETE /////////////////////////////////////////////////////
function ADD_GRID()
{
    
        if(document.getElementById("cmbvoc_type").value!="")
        {
          if(document.getElementById("cmbvoc_type").value=="")
            {
            alert("Select The Voucher Type");
            return false;
            }
        }
        
        if(document.getElementById("cmbvoc_type").value=='R')
        {
          if(document.Ledger_System_Form.rad_sub_CR_DB[1].checked==true)
          {
            alert("Debit amount not allowed");
            return false;
          }
        }
        
        if(document.getElementById("cmbvoc_type").value=='P')
        {
          if(document.Ledger_System_Form.rad_sub_CR_DB[0].checked==true)
          {
            alert("Credit amount not allowed");
            return false;
          }
        }
        
         
       /* if(document.Ledger_System_Form.rad_sub_CR_DB[1].checked==true)
        {
            alert("Debit amount not allowed");
            return false;
        }*/
        
        if(document.getElementById("txtvoc_no").value.length==0)
        {
        alert("Enter Voucher No.");
        return false;
        }
        
        //------------
               var vno=document.getElementById("txtvoc_no").value;
               
            
               var tbody=document.getElementById("grid_body");
                var rows=tbody.getElementsByTagName("tr");
               // alert(rows.length);
                
                for(i=0;i<rows.length;i++)
                {
                //alert("inside");
                  var cells=rows[i].cells;
                  
                  //alert(document.getElementById("cmbvoc_type").value);
                  //alert(cells.item(1).firstChild.value);
                
                if((document.getElementById("cmbvoc_type").value)==(cells.item(1).firstChild.value))
                {
                  //alert(vno);
                 // alert(cells.item(3).firstChild.value);
                  if((vno)==(cells.item(3).firstChild.value))
                  {
                   alert("Voucher No already exists");
                   return false;
                  }
                } 
             }
        //---------
        
        
       /* if(document.getElementById("txtvoc_date").value.length==0)
        {
        alert("Enter Voucher Date");
        return false;
        }*/
        
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            //document.getElementById("txtAmount").focus();
            return false;    
        }
        
       /*  if(document.getElementById("txtRemarks").value.length==0)
        {
            alert("Enter the Remark ");
            return false;    
        }*/
             
        var tbody=document.getElementById("grid_body");
                             
        var t=0;
        var exist=document.getElementById("cmbvoc_type").value;
     
        var items=new Array();
        
        if(document.getElementById("cmbvoc_type").value=="")
        {
         items[0]=""; 
        }
        else
        items[0]=document.getElementById("cmbvoc_type").options[document.getElementById("cmbvoc_type").selectedIndex].value; 
        
        if(document.Ledger_System_Form.rad_sub_CR_DB[0].checked==true)
          items[1]=document.Ledger_System_Form.rad_sub_CR_DB[0].value;
        else if(document.Ledger_System_Form.rad_sub_CR_DB[1].checked==true)
          items[1]=document.Ledger_System_Form.rad_sub_CR_DB[1].value;
        
        items[2]=document.getElementById("txtvoc_no").value;
        
        items[3]=document.getElementById("txtvoc_date").value;
        
        items[4]=document.getElementById("txtsub_Amount").value;
        
        items[5]=document.getElementById("txtRemarks").value;
        
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
         var V_type=document.createElement("input");
          V_type.type="hidden";
          V_type.name="V_type";
                  V_type.value=items[0];
                  cell2.appendChild(V_type);
                  var currentText=document.createTextNode(items[0]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
                   
             cell2=document.createElement("TD"); 
                  var CR_DR_type=document.createElement("input");
                  CR_DR_type.type="hidden";
                  CR_DR_type.name="CR_DR_type";
                  CR_DR_type.value=items[1];
                  cell2.appendChild(CR_DR_type);
                   var currentText=document.createTextNode(items[1]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
             
             cell2=document.createElement("TD");
                  var V_no=document.createElement("input");
                  V_no.type="hidden";
                  V_no.name="V_no";
                  V_no.value=items[2];
                  cell2.appendChild(V_no);
                   var currentText=document.createTextNode(items[2]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
            
             cell2=document.createElement("TD");
                  var V_date=document.createElement("input");
                  V_date.type="hidden";
                  V_date.name="V_date";
                  V_date.value=items[3];
                  cell2.appendChild(V_date);
                   var currentText=document.createTextNode(items[3]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
            
              cell2=document.createElement("TD"); 
                  var sl_amt=document.createElement("input");
                  sl_amt.type="hidden";
                  sl_amt.name="sl_amt";
                  sl_amt.value=items[4];
                  cell2.appendChild(sl_amt);
                  var currentText=document.createTextNode(items[4]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
              
              cell2=document.createElement("TD");             
                  var remarks=document.createElement("input");
                  remarks.type="hidden";
                  remarks.name="remarks";
                  remarks.value=items[5];
                  cell2.appendChild(remarks);
                  var currentText=document.createTextNode(items[5]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);

        tbody.appendChild(mycurrent_row);
         clearall();
}

function update_GRID()
{      
        if(document.getElementById("cmbvoc_type").length>1 && document.getElementById("cmbvoc_type").value=="")
        {
          if(window.confirm("You have not selected Voucher Type \n Do you want to select it,click 'OK'?"))
          {
             if(document.getElementById("cmbvoc_type").value=="")
              {
                alert("Select a Voucher Type");
                return false;
               } 
          }
          else
          {
             
          }
          
        }
        
        if(document.getElementById("cmbvoc_type").value=='R')
        {
          if(document.Ledger_System_Form.rad_sub_CR_DB[1].checked==true)
          {
            alert("Debit amount not allowed");
            return false;
          }
        }
        
        if(document.getElementById("cmbvoc_type").value=='P')
        {
          if(document.Ledger_System_Form.rad_sub_CR_DB[0].checked==true)
          {
            alert("Credit amount not allowed");
            return false;
          }
        }

       /* if(document.Ledger_System_Form.rad_sub_CR_DB[1].checked==true)
        {
            alert("Debit amount not allowed");
            return false;
        }
                
        if(document.getElementById("txtvoc_no").value.length==0)
        {
        alert("Enter Voucher Number");
        return false;
        }*/
        
        if(document.getElementById("txtvoc_date").value.length==0)
        {
        alert("Enter Voucher Date");
        return false;
        }
        
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            document.getElementById("txtsub_Amount").focus();
            return false;    
        }   
        
        
                
       // var exist=document.getElementById("cmbvoc_type").value;
        var items=new Array();
        
        items[0]=document.getElementById("cmbvoc_type").value;
        if(document.getElementById("cmbvoc_type").value=="")
        {
         items[0]=""; 
        }
        else
        items[0]=document.getElementById("cmbvoc_type").options[document.getElementById("cmbvoc_type").selectedIndex].value;
        
        if(document.Ledger_System_Form.rad_sub_CR_DB[0].checked==true)
          items[1]=document.Ledger_System_Form.rad_sub_CR_DB[0].value;
        else if(document.Ledger_System_Form.rad_sub_CR_DB[1].checked==true)
          items[1]=document.Ledger_System_Form.rad_sub_CR_DB[1].value;
          
        items[2]=document.getElementById("txtvoc_no").value;  
        
        items[3]=document.getElementById("txtvoc_date").value;
        
        items[4]=document.getElementById("txtsub_Amount").value;
        
        items[5]=document.getElementById("txtRemarks").value;  
       
               
        var r=document.getElementById(com_id);
        var rcells=r.cells;
        
               try{rcells.item(1).firstChild.value=items[0];}catch(e){}
               try{rcells.item(1).lastChild.nodeValue=items[0];}catch(e){}
             
                try{rcells.item(2).firstChild.value=items[1];}catch(e){}
                try{rcells.item(2).lastChild.nodeValue=items[1];}catch(e){}
              
                try{rcells.item(3).firstChild.value=items[2];}catch(e){}
                try{rcells.item(3).lastChild.nodeValue=items[2];}catch(e){}
            
                try{rcells.item(4).firstChild.value=items[3];}catch(e){}
                try{rcells.item(4).lastChild.nodeValue=items[3];}catch(e){}
            
                try{rcells.item(5).firstChild.value=items[4];}catch(e){}
                try{rcells.item(5).lastChild.nodeValue=items[4];}catch(e){}
             
                try{rcells.item(6).firstChild.value=items[5];}catch(e){}
                try{rcells.item(6).lastChild.nodeValue=items[5];}catch(e){}
            
            
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
     /*document.getElementById("offlist_div_trans").style.display='none';
     document.getElementById("emplist_div_trans").style.display='none';*/
     
    document.getElementById("cmbvoc_type").value="";
    document.Ledger_System_Form.rad_sub_CR_DB[0].checked=true;
    document.getElementById("txtvoc_no").value="";
    document.getElementById("txtvoc_date").value="";
    document.getElementById("txtsub_Amount").value="";
    document.getElementById("txtRemarks").value="";
               

 document.Ledger_System_Form.cmdadd.style.display='block';
 document.Ledger_System_Form.cmdupdate.style.display='none';
 document.Ledger_System_Form.cmddelete.disabled=true;
}

function call_clr()
{
    //document.getElementById("cmbAcc_UnitCode").value="";
    document.getElementById("txtyear_update").value="";
    document.getElementById("txtmon_update").value="";
    document.getElementById("txtAcc_HeadCode").value="";
    document.getElementById("txtAcc_HeadDesc").value="";
    document.getElementById("txtprj_id").value="";
    document.getElementById("txtcl_bal").value="";
    document.getElementById("cmbSL_type").value="";
    clear_Combo(document.getElementById("cmbSL_Code"));
    document.getElementById("txtRef_date").value="";
    document.getElementById("txtvoc_no").value="";
    document.getElementById("txtvoc_date").value="";
    document.getElementById("txtsub_Amount").value="";
    document.getElementById("txtRemarks").value="";
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
           //alert("tbody.rows.length :"+tbody.rows.length);   
        if(document.getElementById("cmbAcc_UnitCode").value=="")
        {
            alert("Select the Account Unit code");
            //document.getElementById("cmbAcc_UnitCode").focus();
            return false;    
        }
        /*if(document.getElementById("cmbOffice_code").value=="")
        {
            alert("Select the Office Code");
            //document.getElementById("cmbOffice_code").focus();
            return false;
        }*/
        if(document.getElementById("txtyear_update").value.length==0)
        {
            alert("Enter the Cashbook Year");
            //document.getElementById("txtCrea_date").focus();
            return false;    
        }
        if(document.getElementById("txtmon_update").value.length==0)
        {
            alert("Enter the Cashbook Month");
            //document.getElementById("txtCash_Acc_code").focus();
            return false;
        }
        
        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
            alert("Enter the Account Code");
            //document.getElementById("txtRecei_from").focus();
            return false;    
        }      
        
        
        if(document.getElementById("cmbSL_type").value!="")
        {
            if(document.getElementById("cmbSL_Code").value=="")
            {
            alert("Select The Sub Ledger Code in General");
            return false;
            }
        }
        if(document.getElementById("txtprj_id").value.length==0)
        {
            alert("Enter the Project ID");
            //document.getElementById("txtAmount").focus();
            return false;    
        }
        if(document.getElementById("txtcl_bal").value.length==0)
        {
            alert("Enter the Closing Balance");
            //document.getElementById("txtAmount").focus();
            return false;    
        }
        if(document.getElementById("txtRef_date").value.length==0)
        {
            alert("Enter the last date of updation");
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
                                        
                     var rtot=0;
                     var ptot=0;
                     var jtot_cr=0;
                     var jtot_dr=0;
                     var cr_tot=0;
                     var dr_tot=0;
                     var total=0;
                     
                     rows=tbody.getElementsByTagName("tr");
                     
                     for(i=0;i<rows.length;i++)
                     {
                      
                       var cells=rows[i].cells;
                       
                                          
                       if(cells.item(1).firstChild.value=='R')
                       {
                         
                         rtot=rtot+parseFloat(cells.item(5).firstChild.value);
                        
                       }
                       
                       
                       if(cells.item(1).firstChild.value=='P')
                       {
                         ptot=ptot+parseFloat(cells.item(5).firstChild.value);
                         
                       }
                       
                       
                       if(cells.item(1).firstChild.value=='J')
                       {
                         if(cells.item(2).firstChild.value=='CR')
                         {
                         jtot_cr=jtot_cr+parseFloat(cells.item(5).firstChild.value);
                         
                         }
                         else if(cells.item(2).firstChild.value=='DB')
                         {
                           jtot_dr=jtot_dr+parseFloat(cells.item(5).firstChild.value);
                           
                         }
                       }                   
                       
                     }
                     
                     cr_tot=rtot+jtot_cr;
                     
                     
                     dr_tot=ptot+jtot_dr;
                     
                     
                     total=(cr_tot-dr_tot);
                     
                     
                }
                
                
               if(parseFloat(document.getElementById("txtcl_bal").value)!=parseFloat(total))
              {
                alert("Amount doesn't Tally.. Difference " +(parseFloat(document.getElementById("txtcl_bal").value)-parseFloat(total)));
                return false;
                }
                
                return true;           
                
                
}



///////////////////////////////////////////    TB_checking and Calender control return value handling



function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
   
}

function call_date(dateCtrl)                        // TB_checking 
{
    checkdt(dateCtrl);
}


////////////////////////////////////////////      For sub-list form  //////////////////////////////////////

var listPopupwindow;
function ListAll()
{
        if(document.Ledger_System_Form.txtyear_update.value.length==0)
        {
            alert("Enter the Cashbook year correctly");
            return false;
        }
        if(document.Ledger_System_Form.txtmon_update.value.length==0)
        {
            alert("Enter the Cashbook month correctly");
            return false;
        }
        if((document.Ledger_System_Form.cmbSL_type.value=="0") || (document.Ledger_System_Form.cmbSL_type.value.length<=0))
        {
        alert("Select Sub-Ledger Type");
        document.Ledger_System_Form.cmbSL_type.focus();
        return false;
        }
        else if((document.Ledger_System_Form.cmbSL_Code.value=="0") || (document.Ledger_System_Form.cmbSL_Code.value.length<=0))
        {
        alert("Select Sub-Ledger Code");
        document.Ledger_System_Form.cmbSL_Code.focus();
        return false;
        }
        //alert(" the details of Selected Financial year");
        if(confirm("Do you want to view the Selected Cashbook year and Cashbook Month details"))
        {
        var Acc_UnitCode=document.Ledger_System_Form.cmbAcc_UnitCode.value;
        var OffCode=document.Ledger_System_Form.cmbOffice_code.value;
        var CashbookYear=document.Ledger_System_Form.txtyear_update.value;
        var CashbookMonth=document.Ledger_System_Form.txtmon_update.value;
        var cmbSL_Type=document.getElementById("cmbSL_type").value;
        var cmbMas_SL_Code=document.getElementById("cmbSL_Code").value;
        
        listPopupwindow= window.open("listofsubsidiary_ledger_form.jsp?cmbAcc_UnitCode="+Acc_UnitCode+"&cmbOffice_code="+OffCode+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth+"&SL_type="+cmbSL_Type+"&Type_Code="+cmbMas_SL_Code,"mywindow1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
        listPopupwindow.moveTo(250,250);    
        }
}

//////////////////////////////////////////////////////////////////////////////////////////

function callServer(Command,param)
{  
       //alert(Command);
       
       if(Command=="Update")
       {  
         
         document.Ledger_System_Form.action="../../../../../subsidiary_ledger_system.view?Command=Update";
         document.Ledger_System_Form.method="POST";
         document.Ledger_System_Form.submit();
       }
       
       else if(Command=="Delete")
       {
         document.Ledger_System_Form.action="../../../../../subsidiary_ledger_system.view?Command=Delete";
         document.Ledger_System_Form.method="POST";
         document.Ledger_System_Form.submit();
       }

}

    
        
function parentdetail(AcHeadCode)
{
    //alert("parent");
    var head=AcHeadCode;
    //alert(head);
    
    document.Ledger_System_Form.butSub.style.display='none';
    document.Ledger_System_Form.butupdate.style.display='block';
    document.Ledger_System_Form.butdelete.style.display='block';
    
    
    var Acc_UnitCode=document.Ledger_System_Form.cmbAcc_UnitCode.value;   //Accounting Unit Code
    var OffCode=document.Ledger_System_Form.cmbOffice_code.value;         //Accounting For Office Code
    var CashbookYear=document.Ledger_System_Form.txtyear_update.value;    //Cashbook Year
    var CashbookMonth=document.Ledger_System_Form.txtmon_update.value;    //Cashbook Month   
    //var Acc_Headcode=document.Ledger_System_Form.txtAcc_HeadCode.value;   //Account head code    
    var cmbSL_Type=document.getElementById("cmbSL_type").value;           //Sub-Ledger Type
    var cmbMas_SL_Code=document.getElementById("cmbSL_Code").value;       //Sub-Ledger Code
    
    var url="../../../../../subsidiarylistserv.con?command=fetch&ac_hd="+head+"&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth+"&SL_type="+cmbSL_Type+"&Type_Code="+cmbMas_SL_Code;
    //alert(url);    
    
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
     {
       handleResponse1(req);
     }   
     req.send(null); 

}

function handleResponse1(req)
{
  
  
  if(req.readyState==4)
  {
   if(req.status==200)
    {
     
      var baseResponse=req.responseXML.getElementsByTagName("response")[0];
      var tagCommand=baseResponse.getElementsByTagName("command")[0];
      var Command=tagCommand.firstChild.nodeValue; 
      
      
      if(Command=="fetch")
      {
      
        getDetails(baseResponse);
      }
    }
   }
}

function getDetails(baseResponse)
{
  
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
  
  
  if(flag=="success")
  {
  
    var aheadcode=baseResponse.getElementsByTagName("accountHeadCode")[0].firstChild.nodeValue;
    var pid=baseResponse.getElementsByTagName("ProjectId")[0].firstChild.nodeValue;
    var cl_bal=baseResponse.getElementsByTagName("CloseBal")[0].firstChild.nodeValue;
    var cr=baseResponse.getElementsByTagName("Dc_Cr")[0].firstChild.nodeValue;
    var date=baseResponse.getElementsByTagName("Ldate")[0].firstChild.nodeValue;
    
        
    document.Ledger_System_Form.txtAcc_HeadCode.value=aheadcode;
    document.Ledger_System_Form.txtprj_id.value=pid;
    document.Ledger_System_Form.txtcl_bal.value=cl_bal;
    
     if(cr=="CR")
        document.Ledger_System_Form.rad_CR_DB[0].checked=true;
     else if(cr=="DB")
         document.Ledger_System_Form.rad_CR_DB[1].checked=true;
    document.Ledger_System_Form.txtRef_date.value=date;  
  
  
    
    var tbody=document.getElementById("grid_body");
    var t=0;
       for(t=tbody.rows.length-1;t>=0;t--)
       {
          tbody.deleteRow(0);
       }
       
     var acchead=baseResponse.getElementsByTagName("AccountHead");
     var items=new Array();
     
    for(var k=0;k<acchead.length;k++)
    {
      //alert(acchead.length);
       items[0]=baseResponse.getElementsByTagName("VOUCHER_TYPE")[k].firstChild.nodeValue;
       items[1]=baseResponse.getElementsByTagName("CR_DR_INDICATOR")[k].firstChild.nodeValue;
       items[2]=baseResponse.getElementsByTagName("VOUCHER_NO")[k].firstChild.nodeValue;
       items[3]=baseResponse.getElementsByTagName("VOUCHER_DATE")[k].firstChild.nodeValue;
       items[4]=baseResponse.getElementsByTagName("AMOUNT")[k].firstChild.nodeValue;
       items[5]=baseResponse.getElementsByTagName("REMARK")[k].firstChild.nodeValue;
       
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
            var type_vou=document.createElement("input");
                  type_vou.type="hidden";
                  type_vou.name="type_vou";
                  type_vou.value=items[0];
                  cell2.appendChild(type_vou);
                  var currentText=document.createTextNode(items[0]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
         
         cell2=document.createElement("TD"); 
             var rad_CR_DR=document.createElement("input");
                  rad_CR_DR.type="hidden";
                  rad_CR_DR.name="rad_CR_DR";
                  rad_CR_DR.value=items[1];
                  cell2.appendChild(rad_CR_DR);
                   var currentText=document.createTextNode(items[1]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
                
         cell2=document.createElement("TD");
               var no_vou=document.createElement("input");
                  no_vou.type="hidden";
                  no_vou.name="no_vou";
                  no_vou.value=items[2];
                  cell2.appendChild(no_vou);
                   var currentText=document.createTextNode(items[2]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
                  
          cell2=document.createElement("TD");
               var date_vou=document.createElement("input");
                  date_vou.type="hidden";
                  date_vou.name="date_vou";
                  date_vou.value=items[3];
                  cell2.appendChild(date_vou);
                   var currentText=document.createTextNode(items[3]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
                  
           cell2=document.createElement("TD");
               var amount=document.createElement("input");
                  amount.type="hidden";
                  amount.name="amount";
                  amount.value=items[4];
                  cell2.appendChild(amount);
                   var currentText=document.createTextNode(items[4]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
                  
             cell2=document.createElement("TD");
               var remar=document.createElement("input");
                  remar.type="hidden";
                  remar.name="remar";
                  remar.value=items[5];
                  cell2.appendChild(remar);
                   var currentText=document.createTextNode(items[5]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);    
                  
                  tbody.appendChild(mycurrent_row);
         
       
        }
  }
  
  else if(flag=="failure")
  {
   alert("Failed to load Data");
  }
}









