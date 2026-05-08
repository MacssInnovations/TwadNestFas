var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;
var job_flag;
var common_AHead_code_flag="";
var emp_flag;
//-------------------------------------------------------------------

function byUnitAndOfficeChange()
{
    doFunction_voucher('load_Voucher_No','null');
}

window.onunload=function()
{
if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
//if (Receipt_list && Receipt_list.open && !Receipt_list.closed) Receipt_list.close();
}
function Acc_HeadCodeValidation()
{
	 var Acc_HeadCode=parseInt(document.getElementById("txtAcc_HeadCode").value);
	if((Acc_HeadCode==900108) || (Acc_HeadCode==900109) || (Acc_HeadCode==901001)|| (Acc_HeadCode==901002)) 
    {
		 alert("TDA/TCA A/C Heads are not Allowed");
		 document.getElementById("txtAcc_HeadCode").value="";
         document.getElementById("txtAcc_HeadDesc").value="";
         document.getElementById("txtAcc_HeadCode").focus();
    }
}
function doFunction_voucher(Command,param)
{   
        if(Command=="load_Voucher_No")
        {  
           clearGeneral_Detail();
           var txtCrea_date= document.frmJournal_General_Edit_Deft.txtCrea_date.value
           var  cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            
            if(txtCrea_date.length!=0)
            {
            var url="../../../../../Rectificational_Journal_Edit?Command=load_Voucher_No&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
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
            var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            var txtCrea_date= document.frmJournal_General_Edit_Deft.txtCrea_date.value
            var  txtJournalVou_No=document.getElementById("txtJournalVou_No").value;
            if(txtJournalVou_No!="")
            {
            var url="../../../../../Rectificational_Journal_Edit?Command=load_Voucher_Details&txtJournalVou_No="+txtJournalVou_No+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
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
          doFunction('checkCode','null');
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
                doFunction('checkCode','null');
                doFunction('Load_SL_Code',com_cmbSL_type);
     setTimeout('document.getElementById("cmbSL_type").value=com_cmbSL_type',900); 
        setTimeout('document.getElementById("cmbSL_Code").value=com_cmbSL_Code',900); 
                
         if(rcells.item(2).firstChild.value=="CR")
         document.frmJournal_General_Edit_Deft.rad_sub_CR_DR[0].checked=true;
         else if(rcells.item(2).firstChild.value=="DR")
         document.frmJournal_General_Edit_Deft.rad_sub_CR_DR[1].checked=true;
         
      try{document.getElementById("cmbSL_Code").value=rcells.item(4).firstChild.value;}catch(e){}
       
       //try{document.getElementById("txtsub_Recei_from").value=rcells.item(5).firstChild.value;}catch(e){}
       try{document.getElementById("txtBill_NO").value=rcells.item(5).firstChild.value;}catch(e){}
       try{document.getElementById("txtBill_date").value=rcells.item(6).firstChild.value;}catch(e){}
       
        try{document.getElementById("txtBill_type").value=rcells.item(7).firstChild.value;}catch(e){}
       
        var nex=rcells.item(7).firstChild.nextSibling  
        try{document.getElementById("txtAgree_No").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
        try{document.getElementById("txtAgree_Date").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
        try{document.getElementById("txtsub_Amount").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
        try{document.getElementById("txtParticular").value=nex.value;}catch(e){}
        
        try{document.getElementById("adjyear").value=rcells.item(8).firstChild.value;}catch(e){}
        
        try{document.getElementById("adjmonth").value=rcells.item(9).firstChild.value;}catch(e){}
        
        tohidedoc();
        
        if(priorsince==1)
        {
       // try{document.getElementById("paymentreceipt").value=rcells.item(10).firstChild.value; }catch(e){alert(e);}
       // payreceipt();
       var val=Trim(rcells.item(10).firstChild.value);
        	
        	for(i=0;i<document.frmJournal_General_Edit_Deft.paymentreceipt.length;i++)
        	{
        	if(document.frmJournal_General_Edit_Deft.paymentreceipt.options[i].value==val)
        	{
     		document.frmJournal_General_Edit_Deft.paymentreceipt.selectedIndex=i;
        	}
        	}
      	        	
        setTimeout(" payreceipt()", 50); 
        
        setTimeout("callone('"+scod+"')", 450); 
        }else{
        	 try{document.getElementById("paymentreceipt1").value=rcells.item(10).firstChild.value;}catch(e){}
        	 try{document.getElementById("receiptno1").value=rcells.item(11).firstChild.value;}catch(e){} 
        }
        
        
       
    document.frmJournal_General_Edit_Deft.cmdupdate.style.display='block';
    document.frmJournal_General_Edit_Deft.cmddelete.disabled=false;
    document.frmJournal_General_Edit_Deft.cmdadd.style.display='none';
}

function Trim(str)

{

    while (str.substring(0,1) == ' ') // check for white spaces from beginning

    {

        str = str.substring(1, str.length);

    }

    while (str.substring(str.length-1, str.length) == ' ') // check white space from end

    {

        str = str.substring(0,str.length-1);

    }

   

    return str;

}


function callone(scod)
{
	 var r=document.getElementById(scod);
     var rcells=r.cells;
     try{document.getElementById("receiptno").value=rcells.item(11).firstChild.value;}catch(e){}
}

/////////////////////////////////////////////   ADD & UPDATE & DELETE /////////////////////////////////////////////////////
function ADD_GRID()
{


        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
        alert("Enter A/c Head Code");
        return false;
        }
                              
        
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
        }  */
       
     //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      
          
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
            alert("Select The Sub Ledger Code");
            return false;
            }
        }
       */ 
       /*  cmbSL_type=document.getElementById("cmbSL_type").value;
         if(cmbSL_type==1 || cmbSL_type==2 || cmbSL_type==11)
            {
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
            }
          if(cmbSL_type==1 || cmbSL_type==2 || cmbSL_type==10 || cmbSL_type==11)
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
            */
            
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            //document.getElementById("txtAmount").focus();
            return false;    
        }
        
        var w = document.frmJournal_General_Edit_Deft.receiptno.selectedIndex;
        var selected_text = document.frmJournal_General_Edit_Deft.receiptno.options[w].text;
    	
        
        
      //  if(document.getElementById("receiptno").value!=document.getElementById("txtAcc_HeadCode").value)
       // {
      //  	 alert('Head Code is not found in Receipt/Voucher ');
        //	 return false;
      //  }
        
        
        var tbody=document.getElementById("grid_body");
                                //alert("CODE"+document.getElementById("txtSL_Desc").value);
                                //alert("TEXT"+document.getElementById("txtSL_Desc").options[document.getElementById("txtSL_Desc").selectedIndex].text);
                                //alert("AGA"+document.getElementById("txtSL_Desc").text)
        var t=0;
        var exist=document.getElementById("txtAcc_HeadCode").value;
      //  if(checkForRedundancy(exist))
      //  {
        var items=new Array();
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;
        if(document.frmJournal_General_Edit_Deft.rad_sub_CR_DR[0].checked==true)
          items[2]=document.frmJournal_General_Edit_Deft.rad_sub_CR_DR[0].value;
        else if(document.frmJournal_General_Edit_Deft.rad_sub_CR_DR[1].checked==true)
          items[2]=document.frmJournal_General_Edit_Deft.rad_sub_CR_DR[1].value;
        
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
        //alert("code"+items[6]+"ff");
        }
        else
        items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
        
       // items[7]=document.getElementById("txtsub_Recei_from").value;
        items[7]=document.getElementById("txtBill_NO").value;
        items[8]=document.getElementById("txtBill_date").value;
        items[9]=document.getElementById("txtBill_type").value;
       // items[10]=document.getElementById("txtAgree_No").value;
        //items[11]=document.getElementById("txtAgree_Date").value;
       
        items[10]="";
        items[11]="";
       
        items[12]=document.getElementById("txtsub_Amount").value;
        items[13]=document.getElementById("txtParticular").value;
        
        items[14]=document.getElementById("adjyear").value;
        items[15]=document.getElementById("adjmonth").value;
        if(priorsince==1){
        items[16]=document.getElementById("paymentreceipt").value;
        items[17]=document.getElementById("receiptno").value;
       // items[17]=selected_text;
        
        }else{
        	items[16]=document.getElementById("paymentreceipt1").value;
            items[17]=document.getElementById("receiptno1").value;
        }
        
        
       
       
        
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

              var sl_amt=document.createElement("input");
                  sl_amt.type="hidden";
                  sl_amt.name="sl_amt";
                  sl_amt.value=items[12];
                  cell2.appendChild(sl_amt);

              var particular=document.createElement("input");           // Particulars Added to grid b4 the Amount Text Node but after  amount hidden box    
                  particular.type="hidden";
                  particular.name="particular";
                  particular.value=items[13];
            //    particular.style.display='none';
                  cell2.appendChild(particular);

                  var currentText=document.createTextNode(items[12]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);

                  
                  cell2=document.createElement("TD");
                  var adj_year=document.createElement("input");
                  adj_year.type="hidden";
                  adj_year.name="adj_year";
                  adj_year.value=items[14];
                  cell2.appendChild(adj_year);
                   var currentText=document.createTextNode(items[14]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD");
                var adj_month=document.createElement("input");
                adj_month.type="hidden";
                adj_month.name="adj_month";
                adj_month.value=items[15];
                cell2.appendChild(adj_month);
                 var currentText=document.createTextNode(items[15]);
                cell2.appendChild(currentText);
              mycurrent_row.appendChild(cell2);
               
              cell2=document.createElement("TD");
              var doc_type=document.createElement("input");
              doc_type.type="hidden";
              doc_type.name="doc_type";
              doc_type.value=items[16];
              cell2.appendChild(doc_type);
               var currentText=document.createTextNode(items[16]);
              cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2);
            
            cell2=document.createElement("TD");
            var doc_no=document.createElement("input");
            doc_no.type="hidden";
            doc_no.name="doc_no";
            doc_no.value=items[17];
            cell2.appendChild(doc_no);
             var currentText=document.createTextNode(items[17]);
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
    document.frmJournal_General_Edit_Deft.rad_sub_CR_DR[0].checked=true;
    document.getElementById("cmbSL_type").value="";
    document.getElementById("cmbSL_Code").value="";
    document.getElementById("txtBill_NO").value="";
    document.getElementById("txtBill_date").value="";
    document.getElementById("txtBill_type").value="";
   
    document.getElementById("adjyear").value="";
    document.getElementById("adjmonth").value="";
    
    document.getElementById("paymentreceipt").value="";
    document.getElementById("receiptno").value="";
    
    document.getElementById("paymentreceipt1").value="";
    document.getElementById("receiptno1").value="";
    
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

     document.frmJournal_General_Edit_Deft.cmdadd.style.display='block';
     document.frmJournal_General_Edit_Deft.cmdupdate.style.display='none';
     document.frmJournal_General_Edit_Deft.cmddelete.disabled=true;
     
     
     document.getElementById("since").style.display='block';	
 	document.getElementById("prior").style.display='none';	
 	document.getElementById("since2007").style.display='block';	
 	document.getElementById("prior2007").style.display='none';	
 	priorsince=1;
}

function update_GRID()
{      
        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
        alert("Enter A/c Head Code");
        return false;
        }
     /*  if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
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
        }  */
         /*  cmbSL_type=document.getElementById("cmbSL_type").value;
         if(cmbSL_type==1 || cmbSL_type==2 || cmbSL_type==11)
            {
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
            }
          if(cmbSL_type==1 || cmbSL_type==2 || cmbSL_type==10 || cmbSL_type==11)
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
            */

        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            document.getElementById("txtsub_Amount").focus();
            return false;    
        }
        
        var w = document.frmJournal_General_Edit_Deft.receiptno.selectedIndex;
        var selected_text = document.frmJournal_General_Edit_Deft.receiptno.options[w].text;
    	
       
        var exist=document.getElementById("txtAcc_HeadCode").value;
        var items=new Array();
       
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;
        if(document.frmJournal_General_Edit_Deft.rad_sub_CR_DR[0].checked==true)
          items[2]=document.frmJournal_General_Edit_Deft.rad_sub_CR_DR[0].value;
        else if(document.frmJournal_General_Edit_Deft.rad_sub_CR_DR[1].checked==true)
          items[2]=document.frmJournal_General_Edit_Deft.rad_sub_CR_DR[1].value;
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
        //alert("code"+items[6]+"ff");
        }
        else
        items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
      //  items[7]=document.getElementById("txtsub_Recei_from").value;
        items[7]=document.getElementById("txtBill_NO").value;
        items[8]=document.getElementById("txtBill_date").value;
        items[9]=document.getElementById("txtBill_type").value;
       // items[10]=document.getElementById("txtAgree_No").value;
       // items[11]=document.getElementById("txtAgree_Date").value;
        
        
        
        items[10]="";
        items[11]="";
        
        items[12]=document.getElementById("txtsub_Amount").value;
        items[13]=document.getElementById("txtParticular").value;
        
                
        items[14]=document.getElementById("adjyear").value;
        items[15]=document.getElementById("adjmonth").value;
        if(priorsince==1){
        items[16]=document.getElementById("paymentreceipt").value;
        items[17]=document.getElementById("receiptno").value;
       // items[17]=selected_text;
        
        }else{
        	items[16]=document.getElementById("paymentreceipt1").value;
            items[17]=document.getElementById("receiptno1").value;
        }
        
        
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
        nex_cell.value=items[10];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[11];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[12];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[13];
        rcells.item(7).lastChild.nodeValue=items[12];
        
        try{rcells.item(8).firstChild.value=items[14];}catch(e){}
        try{rcells.item(8).lastChild.nodeValue=items[14];}catch(e){}
        
        try{rcells.item(9).firstChild.value=items[15];}catch(e){}
        try{rcells.item(9).lastChild.nodeValue=items[15];}catch(e){}
        
        try{rcells.item(10).firstChild.value=items[16];}catch(e){}
        try{rcells.item(10).lastChild.nodeValue=items[16];}catch(e){}
        
        try{rcells.item(11).firstChild.value=items[17];}catch(e){}
        try{rcells.item(11).lastChild.nodeValue=items[17];}catch(e){}
        
        
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
    document.frmJournal_General_Edit_Deft.rad_sub_CR_DR[0].checked=true;
    document.getElementById("cmbSL_type").value="";
    document.getElementById("cmbSL_Code").value="";
    document.getElementById("txtBill_NO").value="";
    document.getElementById("txtBill_date").value="";
    document.getElementById("txtBill_type").value="";
    
    document.getElementById("adjyear").value="";
    document.getElementById("adjmonth").value="";
    
    document.getElementById("paymentreceipt").value="";
    document.getElementById("receiptno").value="";
    
    document.getElementById("paymentreceipt1").value="";
    document.getElementById("receiptno1").value="";
    
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

 document.frmJournal_General_Edit_Deft.cmdadd.style.display='block';
 document.frmJournal_General_Edit_Deft.cmdupdate.style.display='none';
 document.frmJournal_General_Edit_Deft.cmddelete.disabled=true;
 
 document.getElementById("since").style.display='block';	
	document.getElementById("prior").style.display='none';	
	document.getElementById("since2007").style.display='block';	
	document.getElementById("prior2007").style.display='none';	
	priorsince=1;
 
}

function call_clr()
{
    
 document.getElementById("txtJournalVou_No").value="";  
 clearGeneral_Detail();
}
function clearGeneral_Detail()
{ 
   // document.getElementById("txtAmount").value="";
    document.getElementById("txtCheque_NO").value="";
    document.getElementById("txtCheque_date").value="";
    document.getElementById("txtRemarks").value="";
    /*
    
    document.getElementById("txtAuth_By").value="";
    document.getElementById("Auth_By").value="";
    document.getElementById("txtReferNO_edit").value="";
    document.getElementById("txtReferDate_edit").value="";
    document.getElementById("txtRemak_edit").value=""; 
    */
    document.getElementById("cmbMas_SL_type").value="";
    //clear_Combo(document.getElementById("cmbMas_SL_Code"));
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
if(document.getElementById("txtJournalVou_No").value.length==0)
{
    alert("Select Voucher Number");
    //document.getElementById("txtJournalVou_No").focus();
    return false;
}
if (document.getElementById("txtRemarks").value== "") 
{
		alert("Enter Remarks");
		// document.getElementById("txtCrea_date").focus();
		return false;
}

/*if(document.getElementById("txtRecei_from").value.length==0)
{
    alert("Enter the value in Received From Field");
    //document.getElementById("txtRecei_from").focus();
    return false;    
}*/

if(document.getElementById("cmbMas_SL_type").value=="")
{
    //if(document.getElementById("cmbMas_SL_Code").value=="")
    //{
    alert("Select The Journal Type in General");
    return false;
    //}
}
if(document.getElementById("cmbMas_SL_type").value!="" && (document.getElementById("cmbMas_SL_type").value==6 || document.getElementById("cmbMas_SL_type").value==7 ))
{
    if(document.getElementById("txtCheque_NO").value.length==0 || document.getElementById("txtCheque_date").value.length==0)
    {
    alert("Enter Both Cheque Number and Date in General");
    return false;
    }
}

/*if(document.getElementById("txtAmount").value.length==0)
{
    alert("Enter the Total Amount in General");
    //document.getElementById("txtAmount").focus();
    return false;    
}*/
if(tbody.rows.length==0)
{
    alert("Enter the Details Part");
    //document.getElementById("txtAmount").focus();
    return false; 
}

if(tbody.rows.length>0)
{
        var check_amt=0,month_flag=0,type_flag=0,year_flag=0;
        var cr_amt=0;
        var db_amt=0;
        rows=tbody.getElementsByTagName("tr");
        for(i=0;i<rows.length;i++)
        {
            var cells=rows[i].cells;
            //alert(cells.item(2).lastChild.nodeValue);
            if(cells.item(2).lastChild.nodeValue=='CR')
             cr_amt=parseFloat(cr_amt) + parseFloat(cells.item(7).lastChild.nodeValue);
            else
            db_amt=parseFloat(db_amt) + parseFloat(cells.item(7).lastChild.nodeValue);
        } //alert(cr_amt+" "+db_amt);
        //alert(document.getElementById("txtAmount").value+"  "+check_amt);
      /*
        if(parseFloat(db_amt)<=0)
        {
            alert("Debit amount must be specified");
            return false;
        }
      */
        if(parseFloat(cr_amt)!=parseFloat(db_amt))
        {
        alert("Amount doesn't Tally.. Difference " +(parseFloat(cr_amt)-parseFloat(db_amt)));
        return false;
        }
        
        
        var flaf=0;
        
        for(i=0;i<rows.length;i++)
        {
            var cells=rows[i].cells;
          //  alert(cells.item(8).lastChild.nodeValue);
            if(cells.item(8).lastChild.nodeValue!='')
            {
            	flaf=1;
            }
                 
       
        }
        
        if(flaf==0 && document.getElementById("cmbMas_SL_type").value!=90)
        {
        	alert('Atleast One Detail Entry should have Adjustment Voucher Details');
        	return false; 
        }
      for(i=0;i<rows.length;i++)
        {
            var cells=rows[i].cells;
            
            if(cells.item(8).lastChild.nodeValue!='')
            {
            
            	year_flag=1;
            }
            if(cells.item(9).lastChild.nodeValue!='')
                 {
                 month_flag=1;
                 }
            if(cells.item(10).lastChild.nodeValue!='')
            {
             type_flag=1;
            }
        }
        
         if(year_flag==0 && document.getElementById("cmbMas_SL_type").value==90)
        {
        	alert('Atleast One Detail Entry should have Adjustment year*');
        	return false; 
        }
        if(month_flag==0 && document.getElementById("cmbMas_SL_type").value==90)
        {
        	alert('Atleast One Detail Entry should have Adjustment Month ');
        	return false; 
        }
        if(type_flag==0 && document.getElementById("cmbMas_SL_type").value==90)
        {
        	alert('Atleast One Detail Entry should have  DocType ');
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
return true;
}


function enable_cheque(Jr_type)
{
    if(Jr_type==56 || Jr_type==57)
        document.getElementById("CHD").style.display='block';
    else
    {
        
        document.getElementById("txtCheque_NO").value="";
        document.getElementById("txtCheque_date").value="";
        document.getElementById("CHD").style.display='none';
    }
}

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
    if(blr_flag==1)             // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
    {
             call_clr();
             doFunction_voucher('load_Voucher_No','null');
    }
}

function call_date(dateCtrl)                        // TB_checking not needed
{
    call_clr();
    if(checkdt(dateCtrl))
    {
        doFunction_voucher('load_Voucher_No','null');
    }
    else
    {
        var cmbSL_Code=document.getElementById("txtJournalVou_No");   
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

function load_Voucher_No(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
 var txtJournalVou_No=document.getElementById("txtJournalVou_No");
  if(flag=="success")
    {
           var items_id=new Array();
           var Rec_No=baseResponse.getElementsByTagName("Rec_No");
            
            for(var k=0;k<Rec_No.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("Rec_No")[k].firstChild.nodeValue;
                
            }
         
            txtJournalVou_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Voucher Number--";
            option.value="";
            try
            {
                txtJournalVou_No.add(option);
            }catch(errorObject)
            {
                txtJournalVou_No.add(option,null);
            }
            
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_id[k];
                  option.value=items_id[k];
                   try
                  {
                      txtJournalVou_No.add(option);
                  }
                  catch(errorObject)
                  {
                      txtJournalVou_No.add(option,null);
                  }
            }
    }
    else if(flag=="failure")
    {
            txtJournalVou_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Voucher Number--";
            option.value="";
            try
            {
                txtJournalVou_No.add(option);
            }catch(errorObject)
            {
                txtJournalVou_No.add(option,null);
            }
         alert("No Receipt Found");
    }
}



////////////////////

function load_Voucher_Details(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    //alert("FF");
    if(flag=="success")
    {
       var cheq_No=baseResponse.getElementsByTagName("cheq_No")[0].firstChild.nodeValue;         // here i assigned 
       var cheq_Date=baseResponse.getElementsByTagName("cheq_Date")[0].firstChild.nodeValue;
       var Remak=baseResponse.getElementsByTagName("Remak")[0].firstChild.nodeValue;
      
       var Mas_SL_type=baseResponse.getElementsByTagName("Mas_SL_type")[0].firstChild.nodeValue;
       //var Mas_SL_code=baseResponse.getElementsByTagName("Mas_SL_code")[0].firstChild.nodeValue;
       
       if(Mas_SL_type!=0)
       document.getElementById("cmbMas_SL_type").value=Mas_SL_type;
       
      if(cheq_No!="null")
      {
      document.getElementById("txtCheque_NO").value=cheq_No;
      document.getElementById("CHD").style.display='block';
      }
      else
      {
      document.getElementById("txtCheque_NO").value="";
      document.getElementById("CHD").style.display='none';
      }
      
       if(cheq_Date!="null")
       document.getElementById("txtCheque_date").value=cheq_Date;
       else
       document.getElementById("txtCheque_date").value="";
      
       //document.getElementById("txtAmount").value=Total_amt;
      /* if(Rec_From!="null")
      document.getElementById("txtRecei_from").value=Rec_From;
      else
      document.getElementById("txtRecei_from").value="";*/
      
      
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
        {
        items[5]="";
        }
        items[6]=baseResponse.getElementsByTagName("desc_type")[k].firstChild.nodeValue;
      //  alert("666666::;"+items[6]);
        if(items[6]=="null")
        {
        items[6]="";
        }
     //   alert("last");
         items[7]=baseResponse.getElementsByTagName("Bill_NO")[k].firstChild.nodeValue;
        items[8]=baseResponse.getElementsByTagName("Bill_date")[k].firstChild.nodeValue;
        items[9]=baseResponse.getElementsByTagName("Bill_type")[k].firstChild.nodeValue;
        items[10]=baseResponse.getElementsByTagName("Agree_No")[k].firstChild.nodeValue;
        items[11]=baseResponse.getElementsByTagName("Agree_date")[k].firstChild.nodeValue;
        
        items[12]=baseResponse.getElementsByTagName("sub_amount")[k].firstChild.nodeValue;
        items[13]=baseResponse.getElementsByTagName("sub_part")[k].firstChild.nodeValue;
        
        
        items[14]=baseResponse.getElementsByTagName("adjyear")[k].firstChild.nodeValue;
        if(items[14]==0)
            items[14]="";
        items[15]=baseResponse.getElementsByTagName("adjmonth")[k].firstChild.nodeValue;
        if(items[15]==0)
            items[15]="";
        items[16]=baseResponse.getElementsByTagName("doctype")[k].firstChild.nodeValue;
        if(items[16]=="null")
            items[16]="";
        items[17]=baseResponse.getElementsByTagName("docno")[k].firstChild.nodeValue;
        if(items[17]==0)
            items[17]="";
        
       
          if(items[7]=="null")
        items[7]="";
         if(items[8]=="null")
        items[8]="";
         if(items[9]=="null")
        items[9]="";
         if(items[10]=="null")
        items[10]="";
         if(items[11]=="null")
        items[11]="";
        if(items[13]=="null")
        items[13]="";
        
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

              var sl_amt=document.createElement("input");
                  sl_amt.type="hidden";
                  sl_amt.name="sl_amt";
                  sl_amt.value=items[12];
                  cell2.appendChild(sl_amt);

              var particular=document.createElement("input");           // Particulars Added to grid b4 the Amount Text Node but after  amount hidden box    
                  particular.type="hidden";
                  particular.name="particular";
                  particular.value=items[13];
            //    particular.style.display='none';
                  cell2.appendChild(particular);

                  var currentText=document.createTextNode(items[12]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
                  
                  cell2=document.createElement("TD");
                  var adj_year=document.createElement("input");
                  adj_year.type="hidden";
                  adj_year.name="adj_year";
                  adj_year.value=items[14];
                  cell2.appendChild(adj_year);
                   var currentText=document.createTextNode(items[14]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD");
                var adj_month=document.createElement("input");
                adj_month.type="hidden";
                adj_month.name="adj_month";
                adj_month.value=items[15];
                cell2.appendChild(adj_month);
                 var currentText=document.createTextNode(items[15]);
                cell2.appendChild(currentText);
              mycurrent_row.appendChild(cell2);
               
              cell2=document.createElement("TD");
              var doc_type=document.createElement("input");
              doc_type.type="hidden";
              doc_type.name="doc_type";
              doc_type.value=items[16];
              cell2.appendChild(doc_type);
               var currentText=document.createTextNode(items[16]);
              cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2);
            
            cell2=document.createElement("TD");
            var doc_no=document.createElement("input");
            doc_no.type="hidden";
            doc_no.name="doc_no";
            doc_no.value=items[17];
            cell2.appendChild(doc_no);
             var currentText=document.createTextNode(items[17]);
            cell2.appendChild(currentText);
          mycurrent_row.appendChild(cell2);
                  
                  
                  
                  

        tbody.appendChild(mycurrent_row);
        }
    }
    else if(flag=="failure")
     alert("Failed to load data");
}


function limit_amt_journal(field,e)
{
 var unicode=e.charCode? e.charCode : e.keyCode;
 //alert(unicode);
      if(field.value.length<17)
      {
        if(field.value.length==14 && field.value.indexOf('.')==-1  )
        field.value=field.value+'.';
        if (unicode!=8 && unicode !=9  )
        {
            //if (unicode<46 || unicode==47 || unicode>57   )       // It won't allow the negative amount
            if (unicode<45 || unicode==47 || unicode>57   )        // It  allow the negative amount
                return false ;
        }
      }
      else 
      return false;
}



var priorsince=1;
function tohidedoc()
{
	var adjyear=document.getElementById("adjyear").value;
	if(adjyear=="")
	{
		alert('Please Enter Adjustment year');
		return false;
	}
	
	var adjmonth=document.getElementById("adjmonth").value;	
	if(adjyear>2007)
	{
		document.getElementById("since").style.display='block';	
		document.getElementById("prior").style.display='none';	
		document.getElementById("since2007").style.display='block';	
		document.getElementById("prior2007").style.display='none';	
		priorsince=1;
		
	}else if(adjyear==2007)
	{
		if(adjmonth<9)
		{
			document.getElementById("since").style.display='none';	
			document.getElementById("prior").style.display='block';	
			document.getElementById("since2007").style.display='none';	
			document.getElementById("prior2007").style.display='block';	
			priorsince=2;
		}else{
			document.getElementById("since").style.display='block';	
			document.getElementById("prior").style.display='none';	
			document.getElementById("since2007").style.display='block';	
			document.getElementById("prior2007").style.display='none';	
			priorsince=1;
		}
	}else if(adjyear<2007)
	{
		document.getElementById("since").style.display='none';	
		document.getElementById("prior").style.display='block';	
		document.getElementById("since2007").style.display='none';	
		document.getElementById("prior2007").style.display='block';
		priorsince=2;
	}

}

function getxmlhttpObject()
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
function payreceipt()
{
	var adjyear=document.getElementById("adjyear").value;
	var adjmonth=document.getElementById("adjmonth").value;
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
     var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var doctype=document.getElementById("paymentreceipt").value;
	xmlhttp=getxmlhttpObject();
	 var url="../../../../../Rectificational_Journal_Deft?command=paymentreceipt&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&adjyear="+adjyear+"&adjmonth="+adjmonth+"&doctype="+doctype+"";
     //alert(url);
	 url=url+"&sid="+Math.random();
     xmlhttp.open("GET",url,true);
     xmlhttp.onreadystatechange=stateChanged;
     xmlhttp.send(null);  
	
}


function stateChanged()
{
    var flag,command,response;
   
    if(xmlhttp.readyState==4)
    {
    	
       if(xmlhttp.status==200)
       {
            response=xmlhttp.responseXML.getElementsByTagName("response")[0];
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(command=="paymentreceipt")
            {
            	
                if(flag=='success')
                {
                	
                	try{
                		
              		  var len=response.getElementsByTagName("receiptno").length;
                 	var billno=document.getElementById("receiptno");
                 
              	 var items_id=new Array();
              	 var items_desc=new Array();                        
                          for(var i=0;i<len;i++)
                          {
                       	 items_id[i]=response.getElementsByTagName("receiptno")[i].firstChild.nodeValue;
                       	items_desc[i]=response.getElementsByTagName("receiptno")[i].firstChild.nodeValue;  
                      // alert('minor'+items_desc[i]);
                          }
                     clear_Combo(billno);
                    
                          //alert('here second');
                          for(var k=0;k<len;k++)
                          {   
                          	//alert(items_name[k]);
                                var option=document.createElement("OPTION");
                                option.text=items_desc[k];
                                option.value=items_id[k];
                             
                                 try
                                {
                                	 billno.add(option);
                                	
                                }
                                catch(errorObject)
                                {
                                	billno.add(option,null);
                                	
                                   // alert('error');
                                }
                          }
              	
              	}catch(e){alert("Error in lat"+e);}      
                
                }
                else
                    {
                    
                                     
                    }
                 }
            else if(command=="headcheck")
            {
            	
            	 if(flag=='success')
                 {
            		if(addorcheck==1)
            		 ADD_GRID();
            		else if(addorcheck==2)
            			update_GRID();
                 }
            	 else if(flag=="rjvupdated")
                 {
            		 alert('Rectification Journal Voucher already Posted');
                	 if(addorcheck==1)
                		 ADD_GRID();
                		else if(addorcheck==2)
                			update_GRID();
                 }
            	 else
                 {
                	 alert('Account Head is not found in the Selected Voucher ');
                	// document.getElementById("receiptno").value="";
                	 return false;
                 }
            	
            }
       }
    }
}

var payreceipt;

function payreceiptdetails()
{
	
	var w = document.frmJournal_General_Edit_Deft.receiptno.selectedIndex;
    var selected_text = document.frmJournal_General_Edit_Deft.receiptno.options[w].text;
	
	var adjyear=document.getElementById("adjyear").value;
	var adjmonth=document.getElementById("adjmonth").value;
	
	var type=document.getElementById("paymentreceipt").value;
	//var docno=document.getElementById("receiptno").value;
	
	var docno=selected_text;
	
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
     var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	
     
     
	if(adjyear=="")
	{
		alert('Please Enter Adjustment year');
		return false;
	}
	if(type=="")
	{
		alert('Please Select Doc.Type');
		return false;
	}
	if(docno=="")
	{
		alert('Please Select Doc.No');
		return false;
	}
	
	
	if (payreceipt && payreceipt.open && !payreceipt.closed) 
	    {
		 payreceipt.resizeTo(500,500);
		 payreceipt.moveTo(250,250); 
		 payreceipt.focus();
	    }
	    else
	    {
	    	payreceipt=null;
	    }

	 payreceipt= window.open("Rectification_Pay_Receipt_Details.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&yr="+adjyear+"&mon="+adjmonth+"&recNo="+docno+"&type="+type,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
	 payreceipt.moveTo(250,250);  
	 payreceipt.focus();
	
}


var addorcheck=0;

function acheadcodecheck(chec)
{
	if(chec==1)
	addorcheck=1;
	else if(chec==2)
		addorcheck=2;
	
	var w = document.frmJournal_General_Edit_Deft.receiptno.selectedIndex;
    var selected_text = document.frmJournal_General_Edit_Deft.receiptno.options[w].text;
	var adjyear=document.getElementById("adjyear").value;
	var adjmonth=document.getElementById("adjmonth").value;
	if(adjyear!="" && document.getElementById("cmbMas_SL_type").value !=90) {
		if(priorsince!=2){
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
     var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var doctype=document.getElementById("paymentreceipt").value;
	var receiptno=selected_text;
	var headcode=document.getElementById("txtAcc_HeadCode").value;
	xmlhttp=getxmlhttpObject();
	 var url="../../../../../Rectificational_Journal_Deft?command=headcheck&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&adjyear="+adjyear+"&adjmonth="+adjmonth+"&doctype="+doctype+"&headcode="+headcode+"&receiptno="+receiptno+"";
     //alert(url);
	 url=url+"&sid="+Math.random();
     xmlhttp.open("GET",url,true);
     xmlhttp.onreadystatechange=stateChanged;
     xmlhttp.send(null);
		}else{
			if(addorcheck==1)
		   		 ADD_GRID();
                               // checkSubLedgerMandatory();
		   		else if(addorcheck==2)
                                update_GRID();
		   		//	checkSubLedgerMandatory1();
                                
		}
	}else{
		if(addorcheck==1)
                ADD_GRID();
	   		// checkSubLedgerMandatory();
	   		else if(addorcheck==2)
                         update_GRID();
	   			//checkSubLedgerMandatory1();
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
                                      
                                        acheadcodecheck('1');
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
                                        }else
                                        {
                                       acheadcodecheck('1');
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
                                      
                                        acheadcodecheck('2');
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
                                        }else
                                        {
                                        acheadcodecheck('2');
                                        }
                                        }
				}
			}
		}
	}
}
