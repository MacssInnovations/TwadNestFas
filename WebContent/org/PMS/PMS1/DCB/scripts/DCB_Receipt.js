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
    var txtModule_Type="MF004";
    var cr_dr_indi="DR";
    var unspent_OR_col="COL";
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi+"&unspent_OR_col="+unspent_OR_col,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
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




window.onunload=function()
{
if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (winAcc_Bank_No && winAcc_Bank_No.open && !winAcc_Bank_No.closed) winAcc_Bank_No.close();
}

/////////////////////////////////////////////   load Table() /////////////////////////////////////////////////////

function loadTable(scod)
{
        com_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
        clearall();
        var r=document.getElementById(scod);
        var rcells=r.cells;
        //
        try {
                  	
        	var achead=rcells.item(1).firstChild.value;
        	 var len=document.DCB_Receipt_Form.acheadcode.length;
 			for(i=0;i<len;i++)
 			{
 				if(document.DCB_Receipt_Form.acheadcode.options[i].value==achead)
 				{
 					document.DCB_Receipt_Form.acheadcode.options[i].selected='selected';
 					
 					}	
 			}

        
        }catch(e){}
        try{
        	common_cmbSL_type=rcells.item(3).firstChild.value;
              
   	 var len=document.DCB_Receipt_Form.cmbSL_type.length;
		for(i=0;i<len;i++)
		{
			if(document.DCB_Receipt_Form.cmbSL_type.options[i].value==common_cmbSL_type)
			{
				document.DCB_Receipt_Form.cmbSL_type.options[i].selected='selected';
				
				}	
		}
        
        
        } catch(e){common_cmbSL_type="";
      
             
        }
        detailssubled();
        //alert("U"+common_cmbSL_type+"U")
        idle();
        
        try{common_cmbSL_Code=rcells.item(4).firstChild.value;
       
        var len=document.DCB_Receipt_Form.cmbSL_Code.length;
		for(i=0;i<len;i++)
		{
			if(document.DCB_Receipt_Form.cmbSL_Code.options[i].value==common_cmbSL_Code)
			{
				document.DCB_Receipt_Form.cmbSL_Code.options[i].selected='selected';
				
				}	
		}
        
        } catch(e){common_cmbSL_Code="";} 
        
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
             //   doFunction('checkCode','null');
               // doFunction('Load_SL_Code',common_cmbSL_type);
        
         if(rcells.item(2).firstChild.value=="CR")
         document.DCB_Receipt_Form.rad_sub_CR_DR[0].checked=true;
         else if(rcells.item(2).firstChild.value=="DR")
         document.DCB_Receipt_Form.rad_sub_CR_DR[1].checked=true;
         
        //alert("U"+rcells.item(7).firstChild.nextSibling.value)                // This gives the value of 2nd hidden box
        // var nex=rcells.item(7).firstChild.nextSibling                        // get the 2nd hidden box in nex varialble
        // alert(nex.value)                                                  // gives the value of 2nd hidden box
        // nex=nex.nextSibling                                              // to get 3rd hidden field
        
        
        if(rcells.item(5).firstChild.value=="C")
           document.DCB_Receipt_Form.txtCheque_DD[0].checked=true;
        else if(rcells.item(5).firstChild.value=="D")
          document.DCB_Receipt_Form.txtCheque_DD[1].checked=true;
        else if(rcells.item(5).firstChild.value=="E")
          document.DCB_Receipt_Form.txtCheque_DD[2].checked=true;
         
         
         
         
        // alert("U"+"U"+rcells.item(5).nextChild.value)
        
       
        
       try{
             document.getElementById("txtCheque_DD_NO").value=rcells.item(5).firstChild.nextSibling.value;              
        }
        catch(e)
        { alert('erro1'+e);}
        
        
        
       try{
             if (rcells.item(6).firstChild.value != "null" )
             {
                document.getElementById("txtCheque_DD_date").value=rcells.item(6).firstChild.value;
             }   
             else
             {
                document.getElementById("txtCheque_DD_date").value="";
             }
             
          }catch(e){ alert('error2'+e);}
       
       
       
       
       try{document.getElementById("txtBank_Name").value=rcells.item(7).firstChild.value;}catch(e){}
       var nex=rcells.item(7).firstChild.nextSibling  
       
        try{document.getElementById("txtDraw_BR").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
       try{document.getElementById("txtBank_M_Code").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
      //  try{document.getElementById("txtsub_Recei_from").value=nex.value;}catch(e){}
      //   nex=nex.nextSibling;
       try{document.getElementById("txtsub_Amount").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
       try{document.getElementById("txtParticular").value=nex.value;}catch(e){}
       
    document.DCB_Receipt_Form.cmdupdate.style.display='block';
    document.DCB_Receipt_Form.cmddelete.disabled=false;
    document.DCB_Receipt_Form.cmdadd.style.display='none';
}


/////////////////////////////////////////////   ADD & UPDATE & DELETE /////////////////////////////////////////////////////
function ADD_GRID()
{
       if(document.getElementById("acheadcode").value.length==0)
        {
        alert("Please Select A/c Head Code");
        
        return false;
        }
        
        
     //  var achead=document.getElementById("acheadcode").options[document.getElementById("acheadcode").selectedIndex].text;  
       // alert("achead"+achead);
        /*if(document.Bank_Receipt_Form.rad_sub_CR_DR[1].checked==true)
        {
            alert("Debit amount not allowed");
            return false;
        }*/
        
        
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
      
      
      //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      
      if(document.getElementById("acheadcode").value=="")
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
      
         
  if(document.DCB_Receipt_Form.txtCheque_DD[2].checked==false)
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
              
        var t=0;
        var exist=document.getElementById("acheadcode").value;
       
        var items=new Array();
        
        var achead=document.getElementById("acheadcode").options[document.getElementById("acheadcode").selectedIndex].text;  
        
        
        
        items[0]=document.getElementById("acheadcode").value;
        items[1]=document.getElementById("acheadcode").options[document.getElementById("acheadcode").selectedIndex].text; 
        if(document.DCB_Receipt_Form.rad_sub_CR_DR[0].checked==true)
          items[2]=document.DCB_Receipt_Form.rad_sub_CR_DR[0].value;
        else if(document.DCB_Receipt_Form.rad_sub_CR_DR[1].checked==true)
          items[2]=document.DCB_Receipt_Form.rad_sub_CR_DR[1].value;
        
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
        
        if(document.DCB_Receipt_Form.txtCheque_DD[0].checked==true)
          items[7]=document.DCB_Receipt_Form.txtCheque_DD[0].value;
        else if(document.DCB_Receipt_Form.txtCheque_DD[1].checked==true)
          items[7]=document.DCB_Receipt_Form.txtCheque_DD[1].value;
        else if(document.DCB_Receipt_Form.txtCheque_DD[2].checked==true)
          items[7]=document.DCB_Receipt_Form.txtCheque_DD[2].value;  
          
          
          
        items[8]=document.getElementById("txtCheque_DD_NO").value;
        items[9]=document.getElementById("txtCheque_DD_date").value;
        
        
        items[10]=document.getElementById("txtBank_Name").value;
        items[11]=document.getElementById("txtDraw_BR").value;
        items[12]=document.getElementById("txtBank_M_Code").value;
        
       // items[13]=document.getElementById("txtsub_Recei_from").value;
        items[14]=document.getElementById("txtsub_Amount").value;
        items[15]=document.getElementById("txtParticular").value;
        items[16]=document.getElementById("projectid").value;
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
                  
                  var SL_code_one=document.createElement("input");
                  SL_code_one.type="hidden";
                  SL_code_one.name="SL_code_one";
                  SL_code_one.value=items[16];
                  cell2.appendChild(SL_code_one);
                  
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
               
           /*     var rec_from=document.createElement("input");
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

                  var currentText=document.createTextNode(items[14]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
                  
                  
                 // cell2=document.createElement("TD");
                  //cell2.style.display="hidden";
                
                  
                mycurrent_row.appendChild(cell2);
                  
                  
       
        tbody.appendChild(mycurrent_row);
        clear_main_fields();  
}

function clear_main_fields()
{
  
  document.getElementById("offlist_div_trans").style.display='none';
  document.getElementById("emplist_div_trans").style.display='none';
     

//document.getElementById("txtAcc_HeadCode").readOnly=false;
//document.getElementById("txtAcc_HeadDesc").value="";
document.DCB_Receipt_Form.rad_sub_CR_DR[0].checked=true;
//document.getElementById("cmbSL_type").value="";
//document.getElementById("cmbSL_Code").value="";

document.DCB_Receipt_Form.cmbSL_Code.options[0].selected='selected';
document.DCB_Receipt_Form.cmbSL_type.options[0].selected='selected';
//document.DCB_Receipt_Form.txtCheque_DD[0].checked=true;
document.getElementById("txtCheque_DD_NO").value="";
document.getElementById("txtCheque_DD_date").value="";
document.getElementById("txtBank_Name").value="";
document.getElementById("txtDraw_BR").value="";
document.getElementById("txtBank_M_Code").value="";
//document.getElementById("txtsub_Recei_from").value="";


document.getElementById("txtsub_Amount").value="";
document.getElementById("txtParticular").value="";
/*var cmbSL_type=document.getElementById("cmbSL_type"); 
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
           */
        var cmbSL_Code=document.getElementById("cmbSL_Code");   
        clear_Combo(cmbSL_Code);   


 document.DCB_Receipt_Form.cmdadd.style.display='block';
 document.DCB_Receipt_Form.cmdupdate.style.display='none';
 document.DCB_Receipt_Form.cmddelete.disabled=true;
}


function update_GRID()
{      
       if(document.getElementById("acheadcode").value.length==0)
        {
        alert("Please Select A/c Head Code");
       
        return false;
        }
       /* if(document.DCB_Receipt_Form.rad_sub_CR_DR[1].checked==true)
        {
            alert("Debit amount not allowed");
            return false;
        }*/
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
    
    
        
if(document.DCB_Receipt_Form.txtCheque_DD[2].checked==false)
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
       /*  if(document.getElementById("txtBank_M_Code").value.length==0)
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
        var exist=document.getElementById("acheadcode").value;
       
        var items=new Array();
        items[0]=document.getElementById("acheadcode").value;
        items[1]=document.getElementById("acheadcode").options[document.getElementById("acheadcode").selectedIndex].text; 
        if(document.DCB_Receipt_Form.rad_sub_CR_DR[0].checked==true)
          items[2]=document.DCB_Receipt_Form.rad_sub_CR_DR[0].value;
        else if(document.DCB_Receipt_Form.rad_sub_CR_DR[1].checked==true)
          items[2]=document.DCB_Receipt_Form.rad_sub_CR_DR[1].value;
        
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
        
        
        
        if(document.DCB_Receipt_Form.txtCheque_DD[0].checked==true)
          items[7]=document.DCB_Receipt_Form.txtCheque_DD[0].value;
        else if(document.DCB_Receipt_Form.txtCheque_DD[1].checked==true)
          items[7]=document.DCB_Receipt_Form.txtCheque_DD[1].value;          
        else if(document.DCB_Receipt_Form.txtCheque_DD[2].checked==true)
          items[7]=document.DCB_Receipt_Form.txtCheque_DD[2].value;
        
          
        items[8]=document.getElementById("txtCheque_DD_NO").value;
        items[9]=document.getElementById("txtCheque_DD_date").value;
        items[10]=document.getElementById("txtBank_Name").value;
        items[11]=document.getElementById("txtDraw_BR").value;
        items[12]=document.getElementById("txtBank_M_Code").value;
        
      //  items[13]=document.getElementById("txtsub_Recei_from").value;
        items[14]=document.getElementById("txtsub_Amount").value;
        items[15]=document.getElementById("txtParticular").value;
       
        items[16]=document.getElementById("projectid").value;
       // items[16]=252;
        var r=document.getElementById(com_id);
        var rcells=r.cells;
        
        rcells.item(1).firstChild.value=items[0];
        rcells.item(1).lastChild.nodeValue=items[0]+"-"+items[1];
        rcells.item(2).firstChild.value=items[2];
        rcells.item(2).lastChild.nodeValue=items[2];
        rcells.item(3).firstChild.value=items[3];
        rcells.item(3).lastChild.nodeValue=items[4];
        
        rcells.item(4).firstChild.value=items[5];
        var nex_cell=rcells.item(4).firstChild.nextSibling;
        nex_cell.value=items[16];
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
        //var nex_cell=nex_cell.nextSibling;
        //nex_cell.value=items[13];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[14];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[15];
        
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
     

//document.getElementById("txtAcc_HeadCode").readOnly=false;

document.DCB_Receipt_Form.rad_sub_CR_DR[0].checked=true;
//document.getElementById("cmbSL_type").value="";
//document.getElementById("cmbSL_Code").value="";

document.DCB_Receipt_Form.cmbSL_Code.options[0].selected='selected';
document.DCB_Receipt_Form.cmbSL_type.options[0].selected='selected';


document.DCB_Receipt_Form.txtCheque_DD[1].checked=true;
document.getElementById("txtCheque_DD_NO").value="";
document.getElementById("txtCheque_DD_date").value="";
document.getElementById("txtBank_Name").value="";
document.getElementById("txtDraw_BR").value="";
document.getElementById("txtBank_M_Code").value="";
//document.getElementById("txtsub_Recei_from").value="";
document.getElementById("txtsub_Amount").value="";
document.getElementById("txtParticular").value="";
/*var cmbSL_type=document.getElementById("cmbSL_type"); 
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
           */
        var cmbSL_Code=document.getElementById("cmbSL_Code");   
        clear_Combo(cmbSL_Code);   


 document.DCB_Receipt_Form.cmdadd.style.display='block';
 document.DCB_Receipt_Form.cmdupdate.style.display='none';
 document.DCB_Receipt_Form.cmddelete.disabled=true;
}


function call_clr()
{
    /*
    document.getElementById("txtBankAccountNo").value="";
    document.getElementById("txtBankName").value="";
    document.getElementById("txtBankId").value="";
    document.getElementById("txtBranchId").value="";
    document.getElementById("txtCash_Acc_code").value="";
    */
	 document.getElementById("dcb_ben_type").value="";
    document.getElementById("txtRecei_from").value="";
    document.getElementById("txtAmount").value="";
    document.getElementById("txtRef_no").value="";
    document.getElementById("txtRef_date").value="";
    document.getElementById("txtRemarks").value="";
    clear_Combo(document.getElementById("cmbMas_SL_Code"));
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
	        
	        rnum = Math.round( parseFloat(check_amt) * Math.pow(10,2) )  /   Math.pow(10,2) ;
	        alert(rnum);
	        
	        
	        if( parseFloat(document.getElementById("txtAmount").value) != parseFloat(rnum)  )
	        {
	           alert("Amount doesn't Tally.. Difference " +(parseFloat(document.getElementById("txtAmount").value)-parseFloat(check_amt)))
	           return false;
	        }
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

// Joan Added on 29 jan 2015

function call_DCB_fze(dateCtrl1)
{
	 call_clr();
	    if(checkdt(dateCtrl1))
	    {
	    	   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	         var TB_date1=dateCtrl1.value;
	         if(dateCtrl1.value.length!=0)
	         {
	             var url="../../../../../Receipt_SL.view?Command=check_DCB_Frz&TB_date="+TB_date1+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
	             var req=getTransport();
	             req.open("GET",url,true); 
	             req.onreadystatechange=function()
	             {
	               check_DCB_Frz(req,dateCtrl1);
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
      document.getElementById("txtReceipt_No").value="";
    }
}


function check_DCB_Frz(req,dateCtrl1){

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
	            	 dateCtrl1.value="";
	                    alert("DCB Freezed");//return false;//
	                    dateCtrl1.focus();
	                    document.getElementById("txtReceipt_No").value="";
	               }
	               else if(flag=="finyear")
	               {
	                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
	            	   dateCtrl1.value="";
	                    alert("Cash Book Control Not Found ");//return false;//
	                    dateCtrl1.focus();
	                    document.getElementById("txtReceipt_No").value="";     
	               }
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


function loadName_Mas(value)
{
 if(document.getElementById("cmbMas_SL_Code").value=="")
    return;
value=document.getElementById("cmbMas_SL_Code").options[document.getElementById("cmbMas_SL_Code").selectedIndex].text; 
s=document.getElementById("cmbMas_SL_type").value;
if(s=="7" )
value=value.substring(0,value.indexOf("-"));

document.getElementById("txtRecei_from").value=value;

loadheadcode();

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
//-----------------------------------------------------------------------------------------------------------------------------------------------------
//~~~~~~~~~~~~~~~~~~~~~~~~~~~do function from com_function_sl_case.js~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/////////////////////////////////////////////   doFunction()  /////////////////////////////////////////////////////

isMan={
             account_head_status : false
          }
 
var xmlhttp;
function doFunction(Command,param)
{   
   try
   {
    var addtional_field_value;
    
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    
    
    
        if(Command=="checkCode")
        {  
             //Reset isMan.account_head_status
             isMan.account_head_status = false;
    
             var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
             var cmbSL_Code=document.getElementById("cmbSL_Code");   
             clear_Combo(cmbSL_Code);
             
             document.getElementById("txtAcc_HeadDesc").value="";
             
             
            try
            {
            // Sub Ledger Mandatory Check               
             Sub_Ledger_Mandatory(txtAcc_HeadCode);             
           }
           catch(e)
           {
             alert(e.description);
           }
           
           
            if(txtAcc_HeadCode.length>=6)
            {
                var url="../../../../../Receipt_SL.view?Command=checkCode&txtAcc_HeadCode="+txtAcc_HeadCode;                
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
            }         
        }
        else if(Command=="Load_MasterSL_Code")
        {  
            var cmbSL_type=param;    
            document.getElementById("offlist_div_master").style.display='none';
            document.getElementById("emplist_div_master").style.display='none';
            
            if(cmbSL_type==5)
              {
                  document.getElementById("offlist_div_master").style.display='block';
                  addtional_field_value=document.getElementById("txtOfficeID_mas").value;
                  
                  if(addtional_field_value=="")
                  {
                        clear_Combo(document.getElementById("cmbMas_SL_Code")); 
                        alert("Enter or select the office code");
                        return true;
                  }
              }
            else
              {
                  document.getElementById("txtOfficeID_mas").value="";
              }
            
            if(cmbSL_type==7)
              {
                  document.getElementById("emplist_div_master").style.display='block';
                  //clear_Combo(document.getElementById("cmbMas_SL_Code"));
                  //document.getElementById("txtOfficeID_mas").value="";
                  addtional_field_value=document.getElementById("txtEmpID_mas").value;
                  //alert("USE search ICON to select the office");
                  if(addtional_field_value=="")
                  {
                        clear_Combo(document.getElementById("cmbMas_SL_Code")); 
                        alert("Enter or select the employee code");
                        return true;
                  }
              }
            else
              {
                  document.getElementById("txtEmpID_mas").value="";
              }
            if(cmbSL_type==14)                              // called only not equal to null and 5 is for office
            {
            	var d1=document.getElementById("dcb_ben_type");
  	       	  d1.style.display="block";
  	         	       
            }
            else if(cmbSL_type!=14){
               clear_Combo(document.getElementById("cmbMas_SL_Code"));
               var d1=document.getElementById("dcb_ben_type");
   	       	  d1.style.display="none";
            }
        }
        else if(Command=="Load_SL_Code")
        {  
        	  var beneficerytypeid=document.getElementById("dcb_ben_type").value;
 	       	  var officeid=document.getElementById("cmbOffice_code").value;
 	       	  var block_sno=0;  
	 	         try
	 	         {
	 	        	block_sno=document.getElementById("Block_sno").value;
	 	         }catch(e) 
	 	         {
	 	        	block_sno=0;
	 	         } 
			 	        xmlhttp=getxmlhttpObject();
			 	       	var url="../../../../../DCB_Receipt?command=get&beneficerytypeid="+beneficerytypeid+"&block_sno="+block_sno+"&officeid="+officeid+"";
			 	       	url=url+"&sid="+Math.random();
			 	       	xmlhttp.open("GET",url,true);
			 	       	xmlhttp.onreadystatechange=stateChanged;
			 	       	xmlhttp.send(null);    	
        }
        
    }
     catch (e) 
     {
       alert(e.description);
       return false;
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
           
            if(Command=="checkCode")
            {
                loadcheckCode(baseResponse);
            }
            else if(Command=="Load_SL_Code")
            {
                Load_SL_Code(baseResponse);
            }
            else if(Command=="Load_MasterSL_Code")
            {
                Load_MasterSL_Code(baseResponse);
            }
        }
    }
}

/////////////////////////////////////////////////// account head code function  ------------------------------
function loadcheckCode(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
         var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
         document.getElementById("txtAcc_HeadCode").value=hid;
         var hdesc=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
        // var BalType=baseResponse.getElementsByTagName("BalType")[0].firstChild.nodeValue;
         var SL_YN =baseResponse.getElementsByTagName("SL_YN")[0].firstChild.nodeValue;
         
         var sl_man = baseResponse.getElementsByTagName("sl_man")[0].firstChild.nodeValue;
         
         
         document.getElementById("txtAcc_HeadCode").value=hid;
         document.getElementById("txtAcc_HeadDesc").value=hdesc;
      
       var cmbSL_type=document.getElementById("cmbSL_type");   
     try{   
     
       
       if(SL_YN=="Y")
       {
            
            
            if(sl_man == "Y" ) 
            {
              isMan.account_head_status = true;     
            }
            
            var items_SLcode=new Array();
            var items_SLdesc=new Array();
            var SLCODE=baseResponse.getElementsByTagName("SLCODE");
            var SLDESC=baseResponse.getElementsByTagName("SLDESC");
            for(var k=0;k<SLCODE.length;k++)
            {
                items_SLcode[k]=baseResponse.getElementsByTagName("SLCODE")[k].firstChild.nodeValue;
                items_SLdesc[k]=baseResponse.getElementsByTagName("SLDESC")[k].firstChild.nodeValue;
            }
            
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
            for(var k=0;k<SLCODE.length;k++)
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
            
            if(common_cmbSL_type=="")
                document.getElementById("cmbSL_type").value="";
            else
                document.getElementById("cmbSL_type").value=common_cmbSL_type;    //set from grid
       }
        
     }catch(e)
     {  
      // alert(e.description);
       return false;
     }   


        if(SL_YN=="N" || SL_YN=="null")
           {    
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
            }
          
    }
     else if(flag=="failure")
     {
         alert("Account Head Code '"+document.getElementById("txtAcc_HeadCode").value+"' doesn't Exist");
         document.getElementById("txtAcc_HeadCode").value="";
         document.getElementById("txtAcc_HeadCode").focus();
     }
     
        //common_AHead_code_flag="";
        common_cmbSL_type="";
}
/////////////////////////////////////////////   Load_SL_Code() by User /////////////////////////////////////////////////////

function Load_SL_Code(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

    if(flag=="success")
    {
         var cmbSL_Code=document.getElementById("cmbSL_Code");
         
         var items_id=new Array();
         var items_name=new Array();
        // var items_init=new Array();
        
            var cid=baseResponse.getElementsByTagName("cid");
            var cname=baseResponse.getElementsByTagName("cname");
            for(var k=0;k<cid.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
                //alert(items_id[k]);
                items_name[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
             //   items_init[k]=baseResponse.getElementsByTagName("init")[k].firstChild.nodeValue;
            }
           clear_Combo(cmbSL_Code);
            //alert('here second');
            for(var k=0;k<cid.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_name[k];
                  option.value=items_id[k];
                   try
                  {
                      cmbSL_Code.add(option);
                  }
                  catch(errorObject)
                  {
                      cmbSL_Code.add(option,null);
                  }
            }
             document.getElementById("cmbSL_Code").value=items_id[0];
             //alert(items_id[0]);
           if(document.getElementById("cmbSL_type").value==5)
           {
                var state=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
                if(state!="CR")
                alert("Office is not in working status");
           }
           
           if(document.getElementById("cmbMas_SL_type").value!=9 && document.getElementById("cmbSL_type").value==7)
           {
                var state=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
                if(state=="DPN")
                alert("Employee in Deputation");
           } 
           
          // document.getElementById("cmbSL_Code").value=common_cmbSL_Code;
         
    }
    else if(flag=="failure")
    {
        alert("No data found");
        var cmbSL_Code=document.getElementById("cmbSL_Code");
        clear_Combo(cmbSL_Code);
    }
    
    common_cmbSL_Code="";
}




/////////////////////////////////////////////  For MASTER Combo SL Code //////////////////////////////////




function Load_MasterSL_Code(baseResponse)
{
 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

    if(flag=="success")
    {
         var cmbSL_Code=document.getElementById("cmbMas_SL_Code");      // value assigned to same local variable name
         
         var items_id=new Array();
         var items_name=new Array();
        
            var cid=baseResponse.getElementsByTagName("cid");
            var cname=baseResponse.getElementsByTagName("cname");
            for(var k=0;k<cid.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
                items_name[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
               
            }
           
           clear_Combo(cmbSL_Code);
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_name[k];
                  option.value=items_id[k];
                   try
                  {
                      cmbSL_Code.add(option);
                  }
                  catch(errorObject)
                  {
                      cmbSL_Code.add(option,null);
                  }
            }
            
            document.getElementById("cmbMas_SL_Code").value=items_id[0];
            
           try
           {           
            loadName_Mas(items_name[0]);
           }
           catch(e)
           {
             // alert("Error in Com_Function_SL_Case --->loadName_Mas");
           }
            
            
          if(document.getElementById("cmbMas_SL_type").value==5)
           {
                var state=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
                if(state!="CR")
                alert("Office is not in working status");
           }
           
          if(document.getElementById("cmbMas_SL_type").value==7)
           {
                var state=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
                if(state=="DPN")
                alert("Employee in Deputation");
           }
    }
    else if(flag=="failure")
    {
        alert("No data found");
        var cmbSL_Code=document.getElementById("cmbMas_SL_Code");   // value assigned to same local variable name
        clear_Combo(cmbSL_Code);
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

            if(command=="get")
            {
            	var flag1=0;
            	
                if(flag=='success')
                {
                	try{
                		
                		  var len=response.getElementsByTagName("benefi_sno").length;
                 		  var cmbMAS_SL_Code=document.getElementById("cmbMas_SL_Code");
                          var items_id=new Array();
                          var items_name=new Array();
                            for(var i=0;i<len;i++)
                            {
                          	items_id[i]=response.getElementsByTagName("benefi_sno")[i].firstChild.nodeValue;
                          	items_name[i]=response.getElementsByTagName("benefi_name")[i].firstChild.nodeValue;
                            }
                            clear_Combo(cmbMAS_SL_Code);
                            //alert('here second');
                            for(var k=0;k<len;k++)
                            {   
                            	//alert(items_name[k]);
                                  var option=document.createElement("OPTION");
                                  option.text=items_name[k];
                                  option.value=items_id[k];
                                   try
                                  {
                                	   cmbMAS_SL_Code.add(option);
                                	  
                                  }
                                  catch(errorObject)
                                  {
                                	  cmbMAS_SL_Code.add(option,null);
                                	 
                                     // alert('error');
                                  }
                            }
                             
                             var beneficerytypeid=document.getElementById("dcb_ben_type").value;
                             
                             if (beneficerytypeid==6)
                 	       	  {
                            	 try 
                            	 {
                            		 document.getElementById("Block_sno").disabled=false; 
                            		 document.getElementById("Block_sno").focus();        
                            	 }catch(e) {}
                            	 
                            	 
                 	       	  }   
                             else
                             {
                            	 try 
                            	 {
                            		  document.getElementById(" cmbMas_SL_Code").focus();  
                            	 }catch(e) {}
                            	 	document.getElementById("Block_sno").disabled=true;
                             }
                            
                                
                            
                            
                	}catch(e){alert("Error in lat"+e);}
                }
            } 
            if(command=="Getsl")
            {
            	var flag1=0;
            	
                if(flag=='success')
                {
                	try{
                		
                		  var len=response.getElementsByTagName("benefi_sno").length;
                		 var cmbSL_Code=document.getElementById("cmbSL_Code");
                         var items_id=new Array();
                         var items_name=new Array();
                       
                            for(var i=0;i<len;i++)
                            {
                          	items_id[i]=response.getElementsByTagName("benefi_sno")[i].firstChild.nodeValue;
                          	items_name[i]=response.getElementsByTagName("benefi_name")[i].firstChild.nodeValue;
                            }
                            
                                                       
                          clear_Combo(cmbSL_Code);
                            //alert('here second');
                            for(var k=0;k<len;k++)
                            {   
                            	//alert(items_name[k]);
                                  var option=document.createElement("OPTION");
                                  option.text=items_name[k];
                                  option.value=items_id[k];
                                   try
                                  {
                                	   cmbSL_Code.add(option);
                                	  
                                  }
                                  catch(errorObject)
                                  {
                                	  cmbSL_Code.add(option,null);
                                	 
                                     // alert('error');
                                  }
                            }
                		
                		
                		
                		
                	}catch(e){alert("Error in lat"+e);}
                }
            } 
            if(command=="scheme")
            {
            	var flag1=0;
            	
                if(flag=='success')
                {
                	try{
                		
                		  var len=response.getElementsByTagName("scheme_sno").length;
                		  
                		 var cmbSL_Code=document.getElementById("cmbSL_Code");
                		 
                		
                         
                         var items_id=new Array();
                         var items_name=new Array();
                       
                            for(var i=0;i<len;i++)
                            {
                          	 
                          	items_id[i]=response.getElementsByTagName("scheme_sno")[i].firstChild.nodeValue;
                           
                          	items_name[i]=response.getElementsByTagName("scheme_name")[i].firstChild.nodeValue;
                            
                            }
                            
                                                       
                          clear_Combo(cmbSL_Code);
                            //alert('here second');
                            for(var k=0;k<len;k++)
                            {   
                            	//alert(items_name[k]);
                                  var option=document.createElement("OPTION");
                                  option.text=items_name[k];
                                  option.value=items_id[k];
                                   try
                                  {
                                	   cmbSL_Code.add(option);
                                	  
                                  }
                                  catch(errorObject)
                                  {
                                	  cmbSL_Code.add(option,null);
                                	 
                                     // alert('error');
                                  }
                            }
                		
                		
                		
                		
                	}catch(e){alert("Error in lat"+e);}
                }
            } 
            
            
            if(command=="getachead")
            {
            	var flag1=0;
            	
                if(flag=='success')
                {
                	try{
                		
                		  var len=response.getElementsByTagName("acheadcode").length;
                		  
                		 var achead_Code=document.getElementById("acheadcode");
                         
                         var items_id=new Array();
                         var items_desc=new Array();
                       
                            for(var i=0;i<len;i++)
                            {
                          	 
                          	items_id[i]=response.getElementsByTagName("acheadcode")[i].firstChild.nodeValue;
                          	items_desc[i]=response.getElementsByTagName("acheaddesc")[i].firstChild.nodeValue;                                                   
                            }
                            
                                                       
                           clear_Combo(achead_Code);
                          // clear_Combo(cmbSL_Code);
                            //alert('here second');
                            for(var k=0;k<len;k++)
                            {   
                            	//alert(items_name[k]);
                                  var option=document.createElement("OPTION");
                                  option.text=items_desc[k]+" ("+items_id[k]+")";
                                  option.value=items_id[k];
                                   try
                                  {
                                	   achead_Code.add(option);
                                  }
                                  catch(errorObject)
                                  {
                                	  achead_Code.add(option,null);
                                     // alert('error');
                                  }
                            }
                		
                		
                		
                		
                	}catch(e){alert("Error in lat"+e);}
                }
            }
            
            if(command=="getproject")
            {
            
                if(flag=='success')
                {
                	try{
                		document.getElementById("projectid").value =response.getElementsByTagName("projectid")[0].firstChild.nodeValue; 
                		//alert(document.getElementById("projectid").value);
                    	
                	}catch(e){alert("Error in lat"+e);}
                }else if(flag=='failure')
                {
                	alert('Project Mapping is not done.Please Do Mapping');
                	document.getElementById("cmbSL_Code").value="";
                }
            } 
            
            
            
            
       }
    }
}
                	
function loadheadcode()
{
	
	 xmlhttp=getxmlhttpObject();
	 
	 var beneficerytypeid=document.getElementById("dcb_ben_type").value;
   	  var officeid=document.getElementById("cmbOffice_code").value;
   	 // alert('officeid'+officeid);
   //	officeid=5082;
	 
   	var schemesno=document.getElementById("cmbMas_SL_Code").value;
	
     	var url="../../../../../DCB_Receipt?command=getachead&beneficerytypeid="+beneficerytypeid+"&officeid="+officeid+"&schemesno="+schemesno+"";
     	url=url+"&sid="+Math.random();
     	xmlhttp.open("GET",url,true);
     	xmlhttp.onreadystatechange=stateChanged;
     	xmlhttp.send(null);    	

}
function detailssubled()
{
	var slType=document.getElementById("cmbSL_type").value;
	//alert(slType);
	if(slType==14){
	/*	var beneficerytypeid=document.getElementById("dcb_ben_type").value;
  	  var officeid=document.getElementById("cmbOffice_code").value;
  //	officeid=5082;
  
  	
  	  xmlhttp=getxmlhttpObject();
  	var url="../../../../../DCB_Receipt?command=Getsl&beneficerytypeid="+beneficerytypeid+"&officeid="+officeid+"";
  	url=url+"&sid="+Math.random();
  	xmlhttp.open("GET",url,true);
  	xmlhttp.onreadystatechange=stateChanged;
  	xmlhttp.send(null);   
  	*/
		var sl=document.getElementById("cmbSL_Code");
		 clear_Combo(sl);
		 
		 var slcod=document.getElementById("cmbMas_SL_Code");
		
		 
		    var slcod_val=slcod.options[slcod.selectedIndex].text;
		
		
		 var option=document.createElement("OPTION");
         option.text=slcod_val;
         option.value=document.getElementById("cmbMas_SL_Code").value;
          try
         {
        	  sl.add(option);
         }
         catch(errorObject)
         {
        	 sl.add(option,null);
            // alert('error');
         }
		
		
	}
	else if(slType==10)
	{
  	var acheadcode=document.getElementById("acheadcode").value;
  	 var officeid=document.getElementById("cmbOffice_code").value;
 	var beneficeryid=document.getElementById("cmbMas_SL_Code").value;
  	 
xmlhttp=getxmlhttpObject();
	var url="../../../../../DCB_Receipt?command=scheme&acheadcode="+acheadcode+"&officeid="+officeid+"&beneficeryid="+beneficeryid+"";
	url=url+"&sid="+Math.random();
	xmlhttp.open("GET",url,true);
	xmlhttp.onreadystatechange=stateChanged;
	xmlhttp.send(null);  
  	
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
//***********************************************************************************************************
/////////////////////////////////////////////   Check Date() by User /////////////////////////////////////////////////////

function getCurrentYear() {
    var year = new Date().getYear();
    if(year < 1900) year += 1900;
    return year;
  }

  function getCurrentMonth() {
    return new Date().getMonth() + 1;
  } 

  function getCurrentDay() {
    return new Date().getDate();
  }

function isValidDate(dateStr) {
	  
	  // Checks for the following valid date formats:
	  // MM/DD/YYYY
	  // Also separates date into month, day, and year variables
	  var datePat = /^(\d{2,2})(\/)(\d{2,2})\2(\d{4}|\d{4})$/;
	  
	  var matchArray = dateStr.match(datePat); // is the format ok?
	  if (matchArray == null) {
	   alert("Date must be in MM/DD/YYYY format")
	   return false;
	  }
	  
	  month = matchArray[3]; // parse date into variables
	  day = matchArray[1];
	  year = matchArray[4];
	  if (month < 1 || month > 12) { // check month range
	   alert("Month must be between 1 and 12");
	   return false;
	  }
	  if (day < 1 || day > 31) {
	   alert("Day must be between 1 and 31");
	   return false;
	  }
	  if ((month==4 || month==6 || month==9 || month==11) && day==31) {
	   alert("Month "+month+" doesn't have 31 days!")
	   return false;
	  }
	  if (month == 2) { // check for february 29th
	   var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
	   if (day>29 || (day==29 && !isleap)) {
	    alert("February " + year + " doesn't have " + day + " days!");
	    return false;
	     }
	  }
	  return true;  // date is valid
	 }
  
function checkdt(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
//        try{
//        var f=DateFormat(t,c,event,true,'3');
//        }catch(e){
         
       ///New code implemented on 28-03-2019  for year 2019 wrongly displayed 201 
         try{
             var f=isValidDate(c);
            }
        catch(e){
         
        //exception  start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            if(currentYear<_Service_Period_Beg_Year)
            {
            alert('Entered date should be greater than or equal to '+_Service_Period_Beg_Year);
            t.value="";
            t.focus();
            return false;
            }
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear())
            {
            
                    alert('Entered date should be less than current date ');
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than current date');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date ');
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
             if(err!=0)
                {
                    t.value="";
                    return false;
                }
            return true;
        
        
        //exception end
        
        }
        if( f==true)
        {
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
             if(currentYear<_Service_Period_Beg_Year)
            {
            alert('Entered date should be greater than or equal to '+_Service_Period_Beg_Year);
            t.value="";
            t.focus();
            return false;
            }
            if(currentYear > getCurrentYear())
            {
            
                    alert('Entered date should be less than current date');
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than current date');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date ');
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
           
            return true;
            
        }
        else
        {
                if(err!=0)
                {
                    t.value="";
                    return false;
                }
        }
            
    }
    else
    {
            alert('Date format  should be (dd/mm/yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    document.getElementById("dcb_ben_type").focus();
    
}

function foc_new()
{
	var ben=document.getElementById("dcb_ben_type");
	ben.focus();

}

///////////////////////////////////////////// calender input 
function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
       
        if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39  )
        {
                 
            if (unicode<48||unicode>57 ) 
                return false 
        }
}

///////////////////////////////////////  Numbers only fields
function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          //t.blur();
          //return true;-------------------- for taking action when press ENTER
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48 || unicode>57 ) 
                return false 
        }
     }
     
/////////////////////////////////////////////////////  Amount limitation 
function limit_amt(field,e)
{
      var unicode=e.charCode? e.charCode : e.keyCode;
      if(field.value.length<17)
      {
        if(field.value.length==14 && field.value.indexOf('.')==-1  )
        field.value=field.value+'.';
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<46 || unicode==47 || unicode>57   ) 
                return false 
        }
      }
      else 
         return false;
      
}
/*
  var x= field.value.indexOf('.');//==-1  )
        if(x!=-1)
        {
        var len=field.value.length;
        field.value=field.value.substring(0,x+2)
        }*/
///////////////////////////////////    account head limitation /////////////

function sixdigit()
{
 if( document.getElementById("txtAcc_HeadCode").value!=0)
    {
        if(( document.getElementById("txtAcc_HeadCode").value).length<6)
        {
        alert("Account Head Code Shouldn't be less than 6 digit number");
        document.getElementById("txtAcc_HeadCode").focus();
        return false;
        }
    }
}

/////////////////////// exit  function

function exit()
{
//var w=window.open(window.location.href,"_self");
//w.close();
self.close();
}

///////////////////////////////////////////  valid amount or not
function valid_amt(field)
{
    
    amt=field.value;
    if(amt.indexOf(".")!=amt.lastIndexOf("."))
    {
        alert("Enter a Valid Amount");
        field.value="";
        field.focus();
    }
    if(amt < 0 ) 
    {
        alert("Negative Amount Not Allowed");
        field.value="";
        field.focus();    
    }
    
    
}
//////////////////////////////// to show the amount value in words
function valid_amt_InWords(field)
{
    
    amt=field.value;
    if(amt.indexOf(".")!=amt.lastIndexOf("."))
    {
        alert("Enter a Valid Amount");
        field.value="";
        field.focus();
    }
    else
    {
        if(amt.length!=0)
        {
            //alert("gokul");
            var url_amt="../../../../../Receipt_SL.view?Command=FetchInWords&amt="+amt;
            var req_amt=getTransport();
            req_amt.open("GET",url_amt,true); 
            req_amt.onreadystatechange=function()
            {
               fetchAmount(req_amt,field);
            }   
                    req_amt.send(null);
         } 
    }
}
function fetchAmount(req_amt,field)
{
    if(req_amt.readyState==4)
    { 
        if(req_amt.status==200)
        {  
            givenAmt=field.value;
            var baseResponse_amt=req_amt.responseXML.getElementsByTagName("response")[0];
            var tagcommand_amt=baseResponse_amt.getElementsByTagName("command")[0];
            var Command_amt=tagcommand_amt.firstChild.nodeValue;
            alert("check...");
             var flag=baseResponse_amt.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
                    if(flag=="success")
                    {  
                        var amt_words=baseResponse_amt.getElementsByTagName("amt_words")[0].firstChild.nodeValue;
                        alert("Entered Amount "+givenAmt+"..:"+amt_words)
                    }
                    else
                    {
                        alert("Invalid Amount");
                        field.value="";
                        field.focus();
                    }
         }
    }
    
}
//////////////////////////////////////////  length check for remarks amd particulars

function check_leng(val)
{
if(val.length>=250)
return false;
}
function chk_part_len(val)
{
if(val.length>=200)
return false;
}

function loadName(value)
{

if(document.getElementById("cmbSL_Code").value=="")
return;
value=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
s=document.getElementById("cmbSL_type").value;
if(s=="7" )
value=value.substring(0,value.indexOf("-"));

//document.Cash_Receipt_Form.txtsub_Recei_from.value=value;
}


function  idle()
{
	
}

function projectmapping()
{
 xmlhttp=getxmlhttpObject();
	 
	 var schemeno=document.getElementById("cmbSL_Code").value;
   	  var officeid=document.getElementById("cmbOffice_code").value;
   	 // alert('officeid'+officeid);
   //	officeid=5082;
	  
	
     	var url="../../../../../DCB_Receipt?command=getproject&officeid="+officeid+"&schemeno="+schemeno+"";
     	url=url+"&sid="+Math.random();
     	xmlhttp.open("GET",url,true);
     	xmlhttp.onreadystatechange=stateChanged;
     	xmlhttp.send(null);   	

}


// Date : 07/ 12 /2020 
// Date : 08/ 12 /2012 
// By Panneer Selvam.K

function tab_move(to)
{
	document.getElementById(to).focus();  
}
function block_list()
{
		
	    var officeid=document.getElementById("cmbOffice_code").value;
     	var xmlhttp=getxmlhttpObject();
    	var url="../../../../../DCB_Receipt?command=Block_load&officeid="+officeid+"";
    	url=url+"&sid="+Math.random();
    	xmlhttp.open("GET",url,true);
    	xmlhttp.onreadystatechange=function()
    	{
    		block_load(xmlhttp);  
    	}
    	xmlhttp.send(null);   
    	// document.getElementById("Block_sno").focus();
}

function block_load(xmlhttp)
{
	
    var flag,command,response;
    
    if(xmlhttp.readyState==4)
    {
    	
       if(xmlhttp.status==200)
       {
    		var bR=xmlhttp.responseXML.getElementsByTagName("result")[0];
    		var len=bR.getElementsByTagName("sno").length;
    		document.getElementById("Block_sno").options.length = 0    
    		var sno ="0";
		    var name ="Select Union";
		    addOption(document.getElementById("Block_sno"),name,sno);
    		for (i=0;i<len;i++)
			{
				var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
			    var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
			    addOption(document.getElementById("Block_sno"),name,sno)
		    }  
       }
       	 
  
    }
}        
function block_wise_ben_list(block_sno)
{
		var beneficerytypeid=document.getElementById("dcb_ben_type").value;
	    var officeid=document.getElementById("cmbOffice_code").value;    
     	var  xmlhttp=getxmlhttpObject();
	       	var url="../../../../../DCB_Receipt?command=Get_Ben_Block_load&beneficerytypeid="+beneficerytypeid+"&block_sno="+block_sno+"&officeid="+officeid+"";
    	url=url+"&sid="+Math.random();
    	xmlhttp.open("GET",url,true);  
    	xmlhttp.onreadystatechange=function()
    	{
    		block_wise_ben_list_load(xmlhttp);     
    	}  
    	xmlhttp.send(null);  
    
}
function block_wise_ben_list_load(xmlhttp)
{
	
    var flag,command,response;
    
    try
    {
		    if(xmlhttp.readyState==4)
		    {
		    	
		       if(xmlhttp.status==200)
		       {
		    	   
		    		var bR=xmlhttp.responseXML.getElementsByTagName("result")[0];
		    		var len=bR.getElementsByTagName("sno").length;
		    		document.getElementById("cmbMas_SL_Code").options.length = 1 
		    		
		    	
		    		for (i=0;i<len;i++)
					{
						var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
					    var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
					    addOption(document.getElementById("cmbMas_SL_Code"),name,sno)
				    }  
		       }  
		    }
       
   }catch(e) {} 
}
 
 
function focus_from_type()
{
	
	block_list();
	 
}
    