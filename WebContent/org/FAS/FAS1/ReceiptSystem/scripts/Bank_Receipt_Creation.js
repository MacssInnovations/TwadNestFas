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
        winAcc_Bank_No=null
    }
    //var Office_code=document.getElementById("cmbOffice_code").value;  
    var txtModule_Type="MF004";
    var cr_dr_indi="DR";
    var unspent_OR_col="COL";
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var txtSub_Office_code=0;
    winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi+"&txtSub_Office_code="+txtSub_Office_code+"&unspent_OR_col="+unspent_OR_col,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAcc_Bank_No.moveTo(250,250);  
    winAcc_Bank_No.focus();
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


function checkECS(value)
{
	
	//alert("Value===>"+value);
	
	
	
	if(value=="E")
	  { 
		document.getElementById("ecs_hide").style.display='block';
		document.getElementById("ecs_hide1").style.display='block';
		document.getElementById("ecs_hide2").style.display='block';
		document.getElementById("ecs_hide3").style.display='block';
		
		document.getElementById("dd_hide").style.display='none';
		document.getElementById("dd_hide1").style.display='none';
		document.getElementById("dd_hide2").style.display='none';
		document.getElementById("dd_hide3").style.display='none';
		

	  }
	else
		{
		document.getElementById("ecs_hide").style.display='none';
		document.getElementById("ecs_hide1").style.display='none';
		document.getElementById("ecs_hide2").style.display='none';
		document.getElementById("ecs_hide3").style.display='none';
		
		document.getElementById("dd_hide").style.display='block';
		document.getElementById("dd_hide1").style.display='block';
		document.getElementById("dd_hide2").style.display='block';
		document.getElementById("dd_hide3").style.display='block';
		}

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
             }else if((hcode_one==390302) ||(hcode_one==390303)|| (hcode_one==390305) || (hcode_one==391002) ||(hcode_one==391003) ||(hcode_one==391302) || (hcode_one==391303) ||(hcode_one==391502) ||(hcode_one==391503) )
             {			
             	  alert("GPF Account Head Code cannot be used here***");
                   document.getElementById("txtAcc_HeadCode").value="";
                   document.getElementById("txtAcc_HeadCode").focus();
                   return false;
               }
            else{
                        if(spl[0]>01 || spl[0]==01)
                        {
                            var hcode=parseInt(document.getElementById("txtAcc_HeadCode").value)/10000;
                            var spObj=hcode.toString().split(".");
                            if(spObj[0]=="82")
                            {
                             alert("This Account HeadCode cannot be used Here");
                             document.getElementById("txtAcc_HeadCode").value="";
                             document.getElementById("txtAcc_HeadDesc").value="";
                            }
                            else
                            {
                            //doFunction('checkCode','null');
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
  //  doFunction('checkCode','null');
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
if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (winAcc_Bank_No && winAcc_Bank_No.open && !winAcc_Bank_No.closed) winAcc_Bank_No.close();
}

/////////////////////////////////////////////   load Table() /////////////////////////////////////////////////////

function loadTable(scod)
{alert(scod);
        com_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
        clearall();
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
               // alert(com_cmbSL_type);
        setTimeout('document.getElementById("cmbSL_type").value=com_cmbSL_type',900); 
        setTimeout('document.getElementById("cmbSL_Code").value=com_cmbSL_Code',900); 
        
         if(rcells.item(2).firstChild.value=="CR")
         document.Bank_Receipt_Form.rad_sub_CR_DR[0].checked=true;
         else if(rcells.item(2).firstChild.value=="DR")
         document.Bank_Receipt_Form.rad_sub_CR_DR[1].checked=true;
         
        //alert("U"+rcells.item(7).firstChild.nextSibling.value)                // This gives the value of 2nd hidden box
        // var nex=rcells.item(7).firstChild.nextSibling                        // get the 2nd hidden box in nex varialble
        // alert(nex.value)                                                  // gives the value of 2nd hidden box
        // nex=nex.nextSibling                                              // to get 3rd hidden field
        
        
        if(rcells.item(5).firstChild.value=="C")
           document.Bank_Receipt_Form.txtCheque_DD[0].checked=true;
        else if(rcells.item(5).firstChild.value=="D")
          document.Bank_Receipt_Form.txtCheque_DD[1].checked=true;
        else if(rcells.item(5).firstChild.value=="E")
          document.Bank_Receipt_Form.txtCheque_DD[2].checked=true;
         
         
         
         
        // alert("U"+"U"+rcells.item(5).nextChild.value)
        
        if(rcells.item(5).firstChild.value!="E")
    	{
        
        	document.getElementById("ecs_hide").style.display='none';
    		document.getElementById("ecs_hide1").style.display='none';
    		document.getElementById("ecs_hide2").style.display='none';
    		document.getElementById("ecs_hide3").style.display='none';
    		
    		document.getElementById("dd_hide").style.display='block';
    		document.getElementById("dd_hide1").style.display='block';
    		document.getElementById("dd_hide2").style.display='block';
    		document.getElementById("dd_hide3").style.display='block';
        	
        	
        	
        	try{
             document.getElementById("txtCheque_DD_NO").value=rcells.item(5).firstChild.nextSibling.value;              
        }
        catch(e)
        { System.out.println(e);}
        
        
        
       try{
             if (rcells.item(6).firstChild.value != "null" )
             {
                document.getElementById("txtCheque_DD_date").value=rcells.item(6).firstChild.value;
             }   
             else
             {
                document.getElementById("txtCheque_DD_date").value="";
             }
             
          }catch(e){System.out.println(e);}
       
        	}
        
        else
        	{
        	
        	document.getElementById("ecs_hide").style.display='block';
    		document.getElementById("ecs_hide1").style.display='block';
    		document.getElementById("ecs_hide2").style.display='block';
    		document.getElementById("ecs_hide3").style.display='block';
    		
    		document.getElementById("dd_hide").style.display='none';
    		document.getElementById("dd_hide1").style.display='none';
    		document.getElementById("dd_hide2").style.display='none';
    		document.getElementById("dd_hide3").style.display='none';
        	
        	try{
                if (rcells.item(6).firstChild.value != "null" )
                {
                   document.getElementById("ECS_date").value=rcells.item(6).firstChild.value;
                }   
                else
                {
                   document.getElementById("ECS_date").value="";
                }
                
             }catch(e){System.out.println(e);}
          
           	
        	}
       
       
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
       nex=nex.nextSibling;
       try{document.getElementById("txtCB_Year").value=nex.value;}catch(e){}
       nex=nex.nextSibling;
       try{document.getElementById("txtCB_Month").value=nex.value;}catch(e){}
       
       
       
    document.Bank_Receipt_Form.cmdupdate.style.display='block';
    document.Bank_Receipt_Form.cmddelete.disabled=false;
    document.Bank_Receipt_Form.cmdadd.style.display='none';
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
        
        
        var acc=document.getElementById("txtAcc_HeadCode").value;
        var kk=acc.charAt(0)+acc.charAt(1);
       
        if(kk=="82")
        {
            if(acc !="820102"  && acc !="820103")
            {
              // alert("You are not allow to use this Account Head Code "+acc);
               alert("This A/C code can not be used here ");
               document.getElementById("txtAcc_HeadCode").value="";
               document.getElementById("txtAcc_HeadDesc").value="";
               return false;
            }   
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
        
        /*if(document.Bank_Receipt_Form.rad_sub_CR_DR[1].checked==true)
        {
            alert("Debit amount not allowed");
            return false;
        }*/
        
        
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
//       } else
//       {
//            if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
//            {
//                
//                                alert("Select a Sub-Ledger Type");
//                                return false;
//                            
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
      
         
  if(document.Bank_Receipt_Form.txtCheque_DD[2].checked==false)
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
  else
	  {
	       
	        
	        if(document.getElementById("ECS_date").value.length==0)
	        {
	            alert("Enter the ECS Date");
	             document.getElementById("ECS_date").focus();
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
        var exist=document.getElementById("txtAcc_HeadCode").value;
       
        var items=new Array();
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;
        if(document.Bank_Receipt_Form.rad_sub_CR_DR[0].checked==true)
          items[2]=document.Bank_Receipt_Form.rad_sub_CR_DR[0].value;
        else if(document.Bank_Receipt_Form.rad_sub_CR_DR[1].checked==true)
          items[2]=document.Bank_Receipt_Form.rad_sub_CR_DR[1].value;
        
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
        
        if(document.Bank_Receipt_Form.txtCheque_DD[0].checked==true)
          items[7]=document.Bank_Receipt_Form.txtCheque_DD[0].value;
        else if(document.Bank_Receipt_Form.txtCheque_DD[1].checked==true)
          items[7]=document.Bank_Receipt_Form.txtCheque_DD[1].value;
        else if(document.Bank_Receipt_Form.txtCheque_DD[2].checked==true)
          items[7]=document.Bank_Receipt_Form.txtCheque_DD[2].value;  
          
          
          if(items[7]!="E")
        	  {
        	  	items[8]=document.getElementById("txtCheque_DD_NO").value;
        	  	items[9]=document.getElementById("txtCheque_DD_date").value;
        	  }
          else
        	  {
        	  items[8]=document.getElementById("ECS_NO").value;
      	  	  items[9]=document.getElementById("ECS_date").value;
        	  }
        
        items[10]=document.getElementById("txtBank_Name").value;
        items[11]=document.getElementById("txtDraw_BR").value;
        items[12]=document.getElementById("txtBank_M_Code").value;
        
       // items[13]=document.getElementById("txtsub_Recei_from").value;
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
                  
                // alert(items[7]) ;
                  if(items[7]=="E")
            	  {
                	  var ECS_NO=document.createElement("input");
                	  ECS_NO.type="hidden";
                	  ECS_NO.name="ECS_NO";
                	  ECS_NO.value=items[8];
                      cell2.appendChild(ECS_NO);
                      var currentText=document.createTextNode(items[8]);
                    cell2.appendChild(currentText);
                    mycurrent_row.appendChild(cell2);
                    
                cell2=document.createElement("TD");
                     var ECS_date=document.createElement("input");
                     ECS_date.type="hidden";
                     ECS_date.name="ECS_date";
                     ECS_date.value=items[9];
                      cell2.appendChild(ECS_date);
                      var currentText=document.createTextNode(items[9]);
                    cell2.appendChild(currentText);
                    mycurrent_row.appendChild(cell2);
                 
            	  }
                  else
                	  {
                	 
                    
                    
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
                	  }
                
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
document.Bank_Receipt_Form.rad_sub_CR_DR[0].checked=true;
document.getElementById("cmbSL_type").value="";
document.getElementById("cmbSL_Code").value="";


//document.Bank_Receipt_Form.txtCheque_DD[0].checked=true;
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


 document.Bank_Receipt_Form.cmdadd.style.display='block';
 document.Bank_Receipt_Form.cmdupdate.style.display='none';
 document.Bank_Receipt_Form.cmddelete.disabled=true;
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
        
       /* if(document.Bank_Receipt_Form.rad_sub_CR_DR[1].checked==true)
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
        
if(document.Bank_Receipt_Form.txtCheque_DD[2].checked==false)
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
else
	{
	if(document.getElementById("ECS_date").value.length==0)
    {
        alert("Enter the ECS Date");
         document.getElementById("ECS_date").focus();
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
        var exist=document.getElementById("txtAcc_HeadCode").value;
       
        var items=new Array();
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;
        if(document.Bank_Receipt_Form.rad_sub_CR_DR[0].checked==true)
          items[2]=document.Bank_Receipt_Form.rad_sub_CR_DR[0].value;
        else if(document.Bank_Receipt_Form.rad_sub_CR_DR[1].checked==true)
          items[2]=document.Bank_Receipt_Form.rad_sub_CR_DR[1].value;
        
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
        
        
        
        if(document.Bank_Receipt_Form.txtCheque_DD[0].checked==true)
          items[7]=document.Bank_Receipt_Form.txtCheque_DD[0].value;
        else if(document.Bank_Receipt_Form.txtCheque_DD[1].checked==true)
          items[7]=document.Bank_Receipt_Form.txtCheque_DD[1].value;          
        else if(document.Bank_Receipt_Form.txtCheque_DD[2].checked==true)
          items[7]=document.Bank_Receipt_Form.txtCheque_DD[2].value;
        
        if(items[7]!="E")
  	  {
  	  	items[8]=document.getElementById("txtCheque_DD_NO").value;
  	  	items[9]=document.getElementById("txtCheque_DD_date").value;
  	  }
    else
  	  {
  	  items[8]=document.getElementById("ECS_NO").value;
	  	  items[9]=document.getElementById("ECS_date").value;
  	  }
        
        
        
          
//        items[8]=document.getElementById("txtCheque_DD_NO").value;
//        items[9]=document.getElementById("txtCheque_DD_date").value;
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
        //var nex_cell=nex_cell.nextSibling;
        //nex_cell.value=items[13];
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
document.Bank_Receipt_Form.rad_sub_CR_DR[0].checked=true;
document.getElementById("cmbSL_type").value="";
document.getElementById("cmbSL_Code").value="";
document.Bank_Receipt_Form.txtCheque_DD[1].checked=true;
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


 document.Bank_Receipt_Form.cmdadd.style.display='block';
 document.Bank_Receipt_Form.cmdupdate.style.display='none';
 document.Bank_Receipt_Form.cmddelete.disabled=true;
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
        //alert(document.getElementById("txtAmount").value+"  "+check_amt);
        //check_amt=Math.abs(check_amt);

        if(parseFloat(document.getElementById("txtAmount").value)!=parseFloat(check_amt))
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

//Added on 20/04/2011 Veni code 
function doFunctions(Command)
{   

    var addtional_field_value;
    
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    
if(Command=="checkCode1")
{  
	var date=document.getElementById("txtCrea_date").value;
	var b=date.split("/");
	var day=b[0];
	var mon=b[1];
	var yr=b[2];
	
	if(parseInt(day)>30 || parseInt(mon)>11||parseInt(yr)>2010)
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
        var url="../../../../../Receipt_SL_New?Command=checkCodes&txtAcc_HeadCode="+txtAcc_HeadCode;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse(req);
        }   
                req.send(null);
    }
	}
	else
	{
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
     }
   }

function CheckDcbHeads()
{
var head=document.getElementById("txtAcc_HeadCode").value;
//alert(head);
var url="../../../../../Bank_Receipt_Create.view?Command=loadDCBhead&acchead="+head;
            
            //alert(url)
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse_voucher(req);
            }   
                    req.send(null);


}

function handleResponse_voucher(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
             //alert(Command);
            if(Command=="loadDCBhead")
            {
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
              // alert("flag"+flag);
                if(flag=="success")
                {
                alert(" DCB HeadCodes Should Not Be Entered Here");
                document.getElementById("txtAcc_HeadCode").value=""; 
                document.getElementById("txtAcc_HeadDesc").value=""; 
                document.getElementById("txtAcc_HeadCode").focus();
                return false;
                }
                else
                {
                 
                
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

function call_a52()
{
	var accunitId=document.getElementById('cmbAcc_UnitCode').value;
	var cmbOffice_code=document.getElementById('cmbOffice_code').value;
	if(accunitId!=3){
	var url="../../../../../BankPay_PendingBill_Create.view?Command=a52_verify&accunitId="+accunitId+"&cmbOffice_code="+cmbOffice_code;
		
		var req=getTransport();
		req.open("POST",url,true); 
		req.onreadystatechange=function(){
		processResponse_bk(req);
		};   
		req.send(null);
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
       document.Bank_Receipt_Form.txtCrea_date.value=day+"/"+month+"/"+year; 
	}
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
		alert("Please Verify A52 and AA52");
		document.getElementById("newDiv").style.display="none";
		document.frmBankPay_PendingBill_create.txtCrea_date.value="";
		return false;
	}
	else
	{
		
		var asset_cleared=response.getElementsByTagName("asset_cleared")[0].firstChild.nodeValue;
		if(asset_cleared>0)
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
            document.frmBankPay_PendingBill_create.txtCrea_date.value=day+"/"+month+"/"+year;
            call_date(document.frmBankPay_PendingBill_create.txtCrea_date);
            document.getElementById("newDiv").style.display="block";
		}
		else
		{
			alert("Please Check A52 and AA52 Register Entry");
			document.getElementById("newDiv").style.display="none";
			document.frmBankPay_PendingBill_create.txtCrea_date.value="";
			return false;
		}
		
	}
}