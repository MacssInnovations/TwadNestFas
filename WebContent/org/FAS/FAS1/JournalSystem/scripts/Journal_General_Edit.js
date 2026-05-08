//alert("welcome");
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

function doFunction_voucher(Command,param)
{   
        if(Command=="load_Voucher_No")
        {  
           clearGeneral_Detail();
           var txtCrea_date= document.frmJournal_General_Edit.txtCrea_date.value
           var  cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            
            if(txtCrea_date.length!=0)
            {
            var url="../../../../../Journal_General_Edit.view?Command=load_Voucher_No&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
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
            var txtCrea_date= document.frmJournal_General_Edit.txtCrea_date.value
            var  txtJournalVou_No=document.getElementById("txtJournalVou_No").value;
            if(txtJournalVou_No!="")
            {
            var url="../../../../../Journal_General_Edit.view?Command=load_Voucher_Details&txtJournalVou_No="+txtJournalVou_No+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
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
var benfiflag=0;
var bensub=0;
function loadTable(scod)
{
	benfiflag=1;
        com_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
        clearall();
       // document.FasAcc_Headform.cmdadd.disabled=true;
       //document.getElementById("txtAcc_HeadCode").readOnly=true;                // do not change the Account Head 
       //text_field.readOnly=true;
        var r=document.getElementById(scod);
        var rcells=r.cells;
        try {document.getElementById("txtAcc_HeadCode").value=rcells.item(1).firstChild.value;}catch(e){}
        
        var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value
        var cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;
//        if(cmbMas_SL_type==54 && txtAcc_HeadCode==901001)
//        	{
//        	document.getElementById("txtAcc_HeadCode").disabled=true;
//        	}
//        else{document.getElementById("txtAcc_HeadCode").disable=false;}
        
     //  doFunction('checkCode','null');
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
         if(com_cmbSL_type==14)
         {
        	 bensub=com_cmbSL_Code;
        	 document.getElementById("benifici").style.display='block';
        	// doFunction('checkCode','null');
        	 doFunctionBLOCK('checkCode','null');
        	 call('benifi',com_cmbSL_Code);
         }else{        
        	 document.getElementById("benifici").style.display='none';
               // doFunction('checkCode','null');
               doFunction('Load_SL_Code',com_cmbSL_type);
         } 
      //   alert(com_cmbSL_Code);
          setTimeout('document.getElementById("cmbSL_type").value=com_cmbSL_type',900); 
        setTimeout('document.getElementById("cmbSL_Code").value=com_cmbSL_Code',900); 
                
         if(rcells.item(2).firstChild.value=="CR")
         document.frmJournal_General_Edit.rad_sub_CR_DR[0].checked=true;
         else if(rcells.item(2).firstChild.value=="DR")
         document.frmJournal_General_Edit.rad_sub_CR_DR[1].checked=true;
         
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
        
        nex=nex.nextSibling;
        try{document.getElementById("txtCB_Year").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
        try{document.getElementById("txtCB_Month").value=nex.value;}catch(e){}
       
     //  setTimeout("setcmbSL_Code("+common_cmbSL_Code+")", 900);
    document.frmJournal_General_Edit.cmdupdate.style.display='block';
    document.frmJournal_General_Edit.cmddelete.disabled=false;
    document.frmJournal_General_Edit.cmdadd.style.display='none';
}
function setcmbSL_Code(common_cmbSL_Code)
{
	document.getElementById("cmbSL_Code").value = common_cmbSL_Code;
}

/////////////////////////////////////////////   ADD & UPDATE & DELETE /////////////////////////////////////////////////////

function ADD_GRID(val)
{
var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;
        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
        alert("Enter A/c Head Code");
        return false;
        }
        
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
       if(document.getElementById("txtAcc_HeadDesc").value=="")
       {
            alert("Please Wait Account Head is Loading .......................");            
            return false;        
       }       
      // alert("isMan.account_head_status :: >"+isMan.account_head_status);
       if ( isMan.account_head_status) 
       {
    	   if(cmbMas_SL_type!=54){
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
    	   //Lakshmi 8Nov13
    	 else if(cmbMas_SL_type==54){
    		 if(val=="Y"){
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
       }
        else
       {
        	 //Lakshmi 8Nov13
        	 if(cmbMas_SL_type==54){
        		// alert(val);
        		 if(val=="Y"){
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
        	   }
        	 else{
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
        }
     // alert("cmbMas_SL_type:"+cmbMas_SL_type);
       if(cmbMas_SL_type==54)
       {
       
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
                  // alert("units"+units);
                    var code1= ac_code.split("-");   
                 // alert(code1[0]);
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
       
         
           
       }
      
      
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            // document.getElementById("txtAmount").focus();
            return false;    
        }
        var tbody=document.getElementById("grid_body");
                                // alert("CODE"+document.getElementById("txtSL_Desc").value);
                                // alert("TEXT"+document.getElementById("txtSL_Desc").options[document.getElementById("txtSL_Desc").selectedIndex].text);
                                // alert("AGA"+document.getElementById("txtSL_Desc").text)
        var t=0;
        var exist=document.getElementById("txtAcc_HeadCode").value;
        var items=new Array();
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;
        if(document.frmJournal_General_Edit.rad_sub_CR_DR[0].checked==true)
          items[2]=document.frmJournal_General_Edit.rad_sub_CR_DR[0].value;
        else if(document.frmJournal_General_Edit.rad_sub_CR_DR[1].checked==true)
          items[2]=document.frmJournal_General_Edit.rad_sub_CR_DR[1].value;
        
        items[3]=document.getElementById("cmbSL_type").value;
        if(document.getElementById("cmbSL_type").value=="")
        {
        // items[4]="Not Available";
        items[4]="";// document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text;
        // alert("code"+items[4]+"ff");
        }
        else
        items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        
        items[5]=document.getElementById("cmbSL_Code").value;
        if(document.getElementById("cmbSL_Code").value=="")
        {
        items[6]="";// "Not Available";
        // alert("code"+items[6]+"ff");
        }
        else
        items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
        
       // items[7]=document.getElementById("txtsub_Recei_from").value;
        items[7]=document.getElementById("txtBill_NO").value;
        items[8]=document.getElementById("txtBill_date").value;
        items[9]=document.getElementById("txtBill_type").value;
        items[10]=document.getElementById("txtAgree_No").value;
        items[11]=document.getElementById("txtAgree_Date").value;
       
       
        items[12]=document.getElementById("txtsub_Amount").value;
        items[13]=document.getElementById("txtParticular").value;
        
        items[14]=document.getElementById("txtCB_Year").value;
        items[15]=document.getElementById("txtCB_Month").value;
        
        
        
        // items[0]=document.getElementById("txtSL_code").value;
        // items[1]=document.getElementById("txtSL_Desc").options[document.getElementById("txtSL_Desc").selectedIndex].text;
        tbody=document.getElementById("grid_body");
        var mycurrent_row=document.createElement("TR");
        seq=seq+1;
        mycurrent_row.id=seq;
        // alert("row ID"+mycurrent_row.id);
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

              var particular=document.createElement("input");           // Particulars
																		// Added
																		// to
																		// grid
																		// b4
																		// the
																		// Amount
																		// Text
																		// Node
																		// but
																		// after
																		// amount
																		// hidden
																		// box
                  particular.type="hidden";
                  particular.name="particular";
                  particular.value=items[13];
            // particular.style.display='none';
                  cell2.appendChild(particular);
                  
                  var sl_adjyear=document.createElement("input");
                  sl_adjyear.type="hidden";
                  sl_adjyear.name="sl_adjyear";
                  sl_adjyear.value=items[14];
                  cell2.appendChild(sl_adjyear);
                  
                  var sl_adjmonth=document.createElement("input");
                  sl_adjmonth.type="hidden";
                  sl_adjmonth.name="sl_adjmonth";
                  sl_adjmonth.value=items[15];
                  cell2.appendChild(sl_adjmonth);
                  
                  var currentText=document.createTextNode(items[12]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);

        tbody.appendChild(mycurrent_row);
         clear_main_fields();
         document.getElementById("sl1_type").value="0";
         
         document.frmJournal_General_Edit.rad_sub_CR_DR[0].checked = true;
                 document.frmJournal_General_Edit.rad_sub_CR_DR[1].disabled = false;
}  


/////////////////////////////////////////////on 12-11-2019//////////////////////////////////////////
//function ADD_GRID()
//{
//var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;
//        if(document.getElementById("txtAcc_HeadCode").value.length==0)
//        {
//        alert("Enter A/c Head Code");
//        return false;
//        }
//        
//      //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//      
//     if(document.getElementById("txtAcc_HeadDesc").value=="")
//       {
//            alert("Please Wait Account Head is Loading .......................");            
//            return false;        
//       }       
//       
//      
//             if(cmbMas_SL_type==54)
//       {
//       
//       var tbody=document.getElementById("grid_body");
//           if(tbody.rows.length>0)
//            {
//             rows=tbody.getElementsByTagName("tr");
//              for(i=0;i<rows.length;i++)
//               {
//                    var cells=rows[i].cells;
//                  
//                
//                    var ac_code=cells.item(1).lastChild.nodeValue;
//                    var units=cells.item(3).lastChild.nodeValue;
//                  //  alert("units"+units);
//                    var code1= ac_code.split("-");   
//                   
//                    if(code1[0]==901001 && units=="Accounting Units")
//                    {
//                    var offcode=cells.item(4).lastChild.nodeValue;
//                   var chkslcode= document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text;
//               
//                   if(offcode==chkslcode)
//                    {
//                    alert("Already Added For this A/c unit. Choose Different Unit For 901001");
//                     return false;
//                    }
//                    }
//                }
//             }
//       
//    	   var sl1_type = document.getElementById("sl1_type").value;
//    	   if(sl1_type==7)
//    		   {
//                   
//    		   
//    		   }else{
//    			   if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
//    	            {
//    	                
//    	                                alert("Select a Sub-Ledger Type");
//    	                                return false;
//    	                            
//    	                       
//    	            }
//    		   }
//           
//            }else
//            {
//            
//            if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
//            {
//                
//                                alert("Select a Sub-Ledger Type");
//                                return false;
//                            
//                       
//            }
//            }
//            
//        
//       //document.getElementById("sl1_type").value="0";
//     //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//      
//          
//      /*  
//        if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
//        {
//          if(window.confirm("You have not selected Sub-Ledger Type \n Do you want to select it,click 'OK'?"))
//          {
//             if(document.getElementById("cmbSL_type").value=="")
//              {
//                alert("Select a Sub-Ledger Type");
//                return false;
//               } 
//          }
//          else
//          {
//             
//          }
//          
//        }
//        if(document.getElementById("cmbSL_type").value!="")
//        {
//          if(document.getElementById("cmbSL_Code").value=="")
//            {
//            alert("Select The Sub Ledger Code");
//            return false;
//            }
//        }
//       */ 
//       /*  cmbSL_type=document.getElementById("cmbSL_type").value;
//         if(cmbSL_type==1 || cmbSL_type==2 || cmbSL_type==11)
//            {
//                if(document.getElementById("txtBill_NO").value.length==0)
//                {
//                alert("Enter the Bill number");
//                 document.getElementById("txtBill_NO").focus();
//                return false;
//                }
//                if(document.getElementById("txtBill_date").value.length==0)
//                {
//                alert("Enter the Bill Date");
//                 document.getElementById("txtBill_date").focus();
//                return false;
//                }
//                if(document.getElementById("txtBill_type").value.length==0)
//                {
//                alert("Enter the Bill Type");
//                 document.getElementById("txtBill_type").focus();
//                return false;
//                }
//            }
//          if(cmbSL_type==1 || cmbSL_type==2 || cmbSL_type==10 || cmbSL_type==11)
//            {
//                if(document.getElementById("txtAgree_No").value.length==0)
//                {
//                alert("Enter the Agreement Number");
//                 document.getElementById("txtAgree_No").focus();
//                return false;
//                }
//                if(document.getElementById("txtAgree_Date").value.length==0)
//                {
//                alert("Enter the Agreement Date");
//                 document.getElementById("txtAgree_Date").focus();
//                return false;
//                }
//            }
//            */
//            
//        if(document.getElementById("txtsub_Amount").value.length==0)
//        {
//            alert("Enter the Amount ");
//            //document.getElementById("txtAmount").focus();
//            return false;    
//        }
//        var tbody=document.getElementById("grid_body");
//                                //alert("CODE"+document.getElementById("txtSL_Desc").value);
//                                //alert("TEXT"+document.getElementById("txtSL_Desc").options[document.getElementById("txtSL_Desc").selectedIndex].text);
//                                //alert("AGA"+document.getElementById("txtSL_Desc").text)
//        var t=0;
//        var exist=document.getElementById("txtAcc_HeadCode").value;
//      //  if(checkForRedundancy(exist))
//      //  {
//        var items=new Array();
//        items[0]=document.getElementById("txtAcc_HeadCode").value;
//        items[1]=document.getElementById("txtAcc_HeadDesc").value;
//        if(document.frmJournal_General_Edit.rad_sub_CR_DR[0].checked==true)
//          items[2]=document.frmJournal_General_Edit.rad_sub_CR_DR[0].value;
//        else if(document.frmJournal_General_Edit.rad_sub_CR_DR[1].checked==true)
//          items[2]=document.frmJournal_General_Edit.rad_sub_CR_DR[1].value;
//        
//        items[3]=document.getElementById("cmbSL_type").value;
//        if(document.getElementById("cmbSL_type").value=="")
//        {
//        //items[4]="Not Available";
//        items[4]="";//document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
//        //alert("code"+items[4]+"ff");
//        }
//        else
//        items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
//        
//        items[5]=document.getElementById("cmbSL_Code").value;
//        if(document.getElementById("cmbSL_Code").value=="")
//        {
//        items[6]="";//"Not Available";
//        //alert("code"+items[6]+"ff");
//        }
//        else
//        items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
//        
//       // items[7]=document.getElementById("txtsub_Recei_from").value;
//        items[7]=document.getElementById("txtBill_NO").value;
//        items[8]=document.getElementById("txtBill_date").value;
//        items[9]=document.getElementById("txtBill_type").value;
//        items[10]=document.getElementById("txtAgree_No").value;
//        items[11]=document.getElementById("txtAgree_Date").value;
//       
//       
//        items[12]=document.getElementById("txtsub_Amount").value;
//        items[13]=document.getElementById("txtParticular").value;
//        
//        items[14]=document.getElementById("txtCB_Year").value;
//        items[15]=document.getElementById("txtCB_Month").value;
//        
//        alert("adjustment Year Entered :::"+items[14]+"month :::"+items[15]);
//        //items[0]=document.getElementById("txtSL_code").value;
//        //items[1]=document.getElementById("txtSL_Desc").options[document.getElementById("txtSL_Desc").selectedIndex].text;                
//        tbody=document.getElementById("grid_body");
//        var mycurrent_row=document.createElement("TR");
//        seq=seq+1;
//        mycurrent_row.id=seq;
//        //alert("row ID"+mycurrent_row.id);
//        var cell=document.createElement("TD");
//        var anc=document.createElement("A");
//        var url="javascript:loadTable('"+mycurrent_row.id+"')";
//        anc.href=url;
//        var txtedit=document.createTextNode("EDIT");
//        anc.appendChild(txtedit);
//        cell.appendChild(anc);
//        mycurrent_row.appendChild(cell);
//        var i=0;
//        var cell2;
//        
//       
//            cell2=document.createElement("TD");
//           
//                  var H_code=document.createElement("input");
//                  H_code.type="hidden";
//                  H_code.name="H_code";
//                  H_code.value=items[0];
//                  cell2.appendChild(H_code);
//                  var currentText=document.createTextNode(items[0]+"-"+items[1]);
//                  cell2.appendChild(currentText);
//                   mycurrent_row.appendChild(cell2);
//             cell2=document.createElement("TD"); 
//                  var CR_DR_type=document.createElement("input");
//                  CR_DR_type.type="hidden";
//                  CR_DR_type.name="CR_DR_type";
//                  CR_DR_type.value=items[2];
//                  cell2.appendChild(CR_DR_type);
//                   var currentText=document.createTextNode(items[2]);
//                  cell2.appendChild(currentText);
//                mycurrent_row.appendChild(cell2);
//             
//             cell2=document.createElement("TD");
//                  var SL_type=document.createElement("input");
//                  SL_type.type="hidden";
//                  SL_type.name="SL_type";
//                  SL_type.value=items[3];
//                  cell2.appendChild(SL_type);
//                   var currentText=document.createTextNode(items[4]);
//                  cell2.appendChild(currentText);
//                   mycurrent_row.appendChild(cell2);
//            
//             cell2=document.createElement("TD");
//                  var SL_code=document.createElement("input");
//                  SL_code.type="hidden";
//                  SL_code.name="SL_code";
//                  SL_code.value=items[5];
//                  cell2.appendChild(SL_code);
//                   var currentText=document.createTextNode(items[6]);
//                  cell2.appendChild(currentText);
//                mycurrent_row.appendChild(cell2);
//            
//        cell2=document.createElement("TD");
//                  var Bill_NO=document.createElement("input");
//                  Bill_NO.type="hidden";
//                  Bill_NO.name="Bill_NO";
//                  Bill_NO.value=items[7];
//                  cell2.appendChild(Bill_NO);
//                   var currentText=document.createTextNode(items[7]);
//                  cell2.appendChild(currentText);
//                   mycurrent_row.appendChild(cell2);
//            
//             cell2=document.createElement("TD");
//                  var Bill_date=document.createElement("input");
//                  Bill_date.type="hidden";
//                  Bill_date.name="Bill_date";
//                  Bill_date.value=items[8];
//                  cell2.appendChild(Bill_date);
//                   var currentText=document.createTextNode(items[8]);
//                  cell2.appendChild(currentText);
//                mycurrent_row.appendChild(cell2);
//                
//                
//             cell2=document.createElement("TD"); 
//               var Bill_type=document.createElement("input");
//                  Bill_type.type="hidden";
//                  Bill_type.name="Bill_type";
//                  Bill_type.value=items[9];
//                  cell2.appendChild(Bill_type);
//                  
//               var Agree_No=document.createElement("input");
//                  Agree_No.type="hidden";
//                  Agree_No.name="Agree_No";
//                  Agree_No.value=items[10];
//           // Agree_No.style.display='none';
//                  cell2.appendChild(Agree_No);
//                    
//              var Agree_date=document.createElement("input");
//                  Agree_date.type="hidden";
//                  Agree_date.name="Agree_date";
//                  Agree_date.value=items[11];
//        // Agree_date.style.display='none';
//                  cell2.appendChild(Agree_date);
//
//              var sl_amt=document.createElement("input");
//                  sl_amt.type="hidden";
//                  sl_amt.name="sl_amt";
//                  sl_amt.value=items[12];
//                  cell2.appendChild(sl_amt);
//
//              var particular=document.createElement("input");           // Particulars Added to grid b4 the Amount Text Node but after  amount hidden box    
//                  particular.type="hidden";
//                  particular.name="particular";
//                  particular.value=items[13];
//            //    particular.style.display='none';
//                  cell2.appendChild(particular);
//                  
//                  var sl_adjyear=document.createElement("input");
//                  sl_adjyear.type="hidden";
//                  sl_adjyear.name="sl_adjyear";
//                  sl_adjyear.value=items[14];
//                  cell2.appendChild(sl_adjyear);
//                  
//                  var sl_adjmonth=document.createElement("input");
//                  sl_adjmonth.type="hidden";
//                  sl_adjmonth.name="sl_adjmonth";
//                  sl_adjmonth.value=items[15];
//                  cell2.appendChild(sl_adjmonth);
//                  
//                  var currentText=document.createTextNode(items[12]);
//                  cell2.appendChild(currentText);
//                  mycurrent_row.appendChild(cell2);
//
//        tbody.appendChild(mycurrent_row);
//         clear_main_fields();
//         document.getElementById("sl1_type").value="0";
//}

function clear_main_fields()
{
     document.getElementById("offlist_div_trans").style.display='none';
     document.getElementById("emplist_div_trans").style.display='none';
     
    document.getElementById("txtAcc_HeadCode").value="";
    //document.getElementById("txtAcc_HeadCode").readOnly=false;
    document.getElementById("txtAcc_HeadDesc").value="";
    document.frmJournal_General_Edit.rad_sub_CR_DR[0].checked=true;
    document.getElementById("cmbSL_type").value="";
    document.getElementById("cmbSL_Code").value="";
 /*   document.getElementById("txtBill_NO").value="";
    document.getElementById("txtBill_date").value="";
    document.getElementById("txtBill_type").value="";
    document.getElementById("txtAgree_No").value="";
    document.getElementById("txtAgree_Date").value="";*/
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
            
            document.getElementById("benifici").style.display='none';
            
     document.frmJournal_General_Edit.cmdadd.style.display='block';
     document.frmJournal_General_Edit.cmdupdate.style.display='none';
     document.frmJournal_General_Edit.cmddelete.disabled=true;
}

function update_GRID()
{    
        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
        alert("Enter A/c Head Code");
        return false;
        }
      
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
        var exist=document.getElementById("txtAcc_HeadCode").value;
        var items=new Array();
       
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;
        if(document.frmJournal_General_Edit.rad_sub_CR_DR[0].checked==true)
          items[2]=document.frmJournal_General_Edit.rad_sub_CR_DR[0].value;
        else if(document.frmJournal_General_Edit.rad_sub_CR_DR[1].checked==true)
          items[2]=document.frmJournal_General_Edit.rad_sub_CR_DR[1].value;
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
        items[10]=document.getElementById("txtAgree_No").value;
        items[11]=document.getElementById("txtAgree_Date").value;
        
        items[12]=document.getElementById("txtsub_Amount").value;
        items[13]=document.getElementById("txtParticular").value;
        
        items[14]=document.getElementById("txtCB_Year").value;
        items[15]=document.getElementById("txtCB_Month").value;
        
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
        
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[14];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[15];
        
        rcells.item(7).lastChild.nodeValue=items[12];
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
    document.frmJournal_General_Edit.rad_sub_CR_DR[0].checked=true;
    document.getElementById("cmbSL_type").value="";
    document.getElementById("cmbSL_Code").value="";
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
            
            document.getElementById("benifici").style.display='none';
            
 document.frmJournal_General_Edit.cmdadd.style.display='block';
 document.frmJournal_General_Edit.cmdupdate.style.display='none';
 document.frmJournal_General_Edit.cmddelete.disabled=true;
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
var flag_six=0;
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
if (document.getElementById("txtRemarks").value== "") {
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
var flag;
var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;
        var check_amt=0;
        var cr_amt=0;
        var db_amt=0;
        var unitiid=document.getElementById("cmbAcc_UnitCode").value;
        
        rows=tbody.getElementsByTagName("tr");
        for(i=0;i<rows.length;i++)
        {
            var cells=rows[i].cells;
            // alert(cells.item(7).lastChild.nodeValue);
            if(cells.item(2).lastChild.nodeValue=='CR')
            {
            var ac_code=cells.item(1).lastChild.nodeValue;
         var code1= ac_code.split("-");
        // alert(code1[0]);
                 cr_amt=parseFloat(cr_amt) + parseFloat(cells.item(7).lastChild.nodeValue);
                 
                 //------Lakshmi-----
                 
                 if(unitiid!=5)
              	  {
                         if( (code1[0]==120201) && (cmbMas_SL_type==59) )
                         {
                        flag=2;
                         }
              	  }
              	  else
              	  {
              		if((cmbMas_SL_type==59))
	               	 {
	              			if((code1[0]==610101))
	                   		flag=2222;
	               	 }
              	  }
                 
                 if( (code1[0]==100101) && (cmbMas_SL_type==58) )
			        {
			       flag=1;
			        }
               /*  else if( ((code1[0]==221201) || (code1[0]==221202) || (code1[0]==221203) || (code1[0]==221204)) && (cmbMas_SL_type==102) )
                 {
                     flag=7;
                 }*/
                 ///Joan changed on 04/05/2015 Vasanthi mam requirement
                 else if( cmbMas_SL_type==102 )
                 {
               	  if(code1[0]==782406 )
                     flag=7;
                 }
       //sep7_2012
         else if( (code1[0]==150401) && (cmbMas_SL_type==101) )
         {
        flag=101;
         }         
       else if( ((code1[0]==550310) || (code1[0]==792141) || (code1[0]==792041) || (code1[0]==100101) ) && (cmbMas_SL_type==54) )
        {
       flag=3;
        }
        else if( (code1[0]==150101) && (cmbMas_SL_type==85) )
        {
     
        flag=4;
        }
        else if( ((code1[0]==110101) && (cmbMas_SL_type==88)) || ((code1[0]==110102) && (cmbMas_SL_type==88)) || (code1[0]==110103) && (cmbMas_SL_type==88) || (code1[0]==110104) && (cmbMas_SL_type==88) ||(code1[0]==110601) && (cmbMas_SL_type==88))
        {
     
        flag=5;
        }
        else if( ((code1[0]==110201) && (cmbMas_SL_type==88)) || ((code1[0]==110202) && (cmbMas_SL_type==88)) || (code1[0]==110203) && (cmbMas_SL_type==88) || (code1[0]==110204) && (cmbMas_SL_type==88))
                {
             
                flag=55;
                }
        else if( ((code1[0]==550310) || (code1[0]==792141) || (code1[0]==792041) || (code1[0]==100101)) && (cmbMas_SL_type==53) )
        {
           alert("For General Adjustment Journal Type one of Acc Head Codes ('550310','792141','792041','100101') Not to be Added");
          return false;
        }
        else if( (code1[0]==130106) && (cmbMas_SL_type==97) )
        {
       flag=121;
        }
        else if( (code1[0]==130301) && (cmbMas_SL_type==111) )
        {
       flag=130301;
        }
        
 
            }
            else
            {
                var ac_code=cells.item(1).lastChild.nodeValue;
                     var code1= ac_code.split("-");
                
                 if(cmbMas_SL_type==54 )
                    {
                    if(code1[0]==212301)
                            {
                                flag=3;
                            }   
                    }
                 else if( (code1[0]==100101) && (cmbMas_SL_type==91) )
                 {
                 flag=6;
                 }
                 else if( (code1[0]==222112) && (cmbMas_SL_type==104) )
                 {
                 flag=616;
                 }
                 else if(cmbMas_SL_type==94)
                 {
                 	if(code1[0]==370304)
                 	{
                 		flag=999;
                 	}
                 }
                 else if(cmbMas_SL_type==95)
                 {
                	 var sltype1=cells.item(3).firstChild.value;
                    if(sltype1==6)
                    {
                    	flag_six=66;
                    }
                 }
                 else if( (code1[0]==100101) && (cmbMas_SL_type==100) )
                 {
                 	flagss=100;
                 }
                     db_amt=parseFloat(db_amt) + parseFloat(cells.item(7).lastChild.nodeValue);
            }
       
        } //alert(cr_amt+" "+db_amt);
        // alert(document.getElementById("txtAmount").value+" "+check_amt);
        /*
		 * for the case of entries of debit must if(parseFloat(db_amt)<=0) {
		 * alert("Debit amount must be specified"); return false; }
		 */
          // alert("creditAmount"+cr_amt);      
           // alert("debitAmount"+db_amt);
                 
        if(parseFloat(cr_amt)!=parseFloat(db_amt))      // Either CR or DR must
														// equal in total
        {
        alert("Amount doesn't Tally.. Difference " +(parseFloat(cr_amt)-parseFloat(db_amt)));
        return false;
        }
        
        
 // -----Lakshmi----
        
        
        
        if(unitiid==5){
        	 if(cmbMas_SL_type==59)
             {
             if(flag==2222){
            	// alert("inside if "+flag);
            	 return true;
             }
             else{
            	 alert ("For Bank Interest Credit Jnl Account Head Code should 610101");
            	 return false;
             }
             }
        	
        }
        
        
        
          if(cmbMas_SL_type==58)
        {
        if(flag==1) {
        return true;
        }else{
        alert("For Centage Journal Type Acc Head Code '100101' Should be Added");
        return false;
        }
        }
          else if(cmbMas_SL_type==101)
          {
          	if(flag==101)
          	{
          		 return true;
          	}
          	else
          	{
          		 alert("For NRDWP Schemes Type Acc Head Code '150401' Should be Added");
          	        return false;
          	}
          }
          else if(cmbMas_SL_type==102)
          {
          	if(flag==7)
          	{
          		 return true;
          	}
          	else
          	{
          		 alert("For EB Adjustment Charges Acc Head Codes like 221201,221202,221203,221204 Should be Added in Credit Side");
          	        return false;
          	}
          }
          else if(cmbMas_SL_type==104)
          {
          	if(flag==616)
          	{
          		 return true;
          	}
          	else
          	{
          		 alert("For Diesel to Genset Adjustment Charges Acc Head Code '222112' Should be Added in Debit Side");
          	        return false;
          	}
          }
         else if(cmbMas_SL_type==59)
        {
        if(flag==2) {
        return true;
        }else{
        alert("For Interest Adjustment Journal Type Acc Head Code '120201' Should be Added");
        return false;
        }
        }else if(cmbMas_SL_type==54)
        {
        if(flag==3) {
        return true;
        }else{
        alert("For Payroll Journal Type one of Acc Head Codes ('550310','792141','792041') Should be Added");
        return false;
        }
        }
        
        else if(cmbMas_SL_type==85)
        {
        if(flag==4)
        {
         return true;
        }
        else
        {
          alert("For Recovery of Investigation charges Journal Type Acc Head Code '150101' Should be Added");
        return false;
       
        }
        }
         else if(cmbMas_SL_type==88)
        {
        if(flag==5)
        {
         return true;
        }
        else if(flag==55)
        {
         return true;
        }
        else
        {
          alert("For Propulsion Charges Journal Type one of A/c Heads(110101,110102,110103,110104,110201,110202,110203,110204,110601) Should be Added");
        return false;
       
        }
        }
         else if(cmbMas_SL_type==91)
         {
                 if(flag==6)
                 {
                      return true;
                  
                 }
                 else
                 {
                   alert("A/c Head 100101 Should be Added as Debit Head for Centage Of Credit Works Outlay");
                 return false;
                
                 }
         }
         
         else if(cmbMas_SL_type==94)
         {
         	//alert("flag:::"+flag);
         	if(flag==999)
         	{
         		 return true;
         	}
         	else
         	{
         		alert("A/c Head 370304 Should be Added as Debit Head for Apportionment of Gift");
                 return false;
         	}
         }
         else if(cmbMas_SL_type==95)
         {
        	 if(flag_six==66)
          	{
          		 return true;
          	}
          	else
          	{
          		alert("You Should Enter Atleast one OPR Bank Head in Debit");
                return false;
          	}
         }
         else if(cmbMas_SL_type==97)
         {
         	if(flag==121)
         	{
         		 return true;
         	}
         	else
         	{
         		alert("A/c Head 130106 Should be Added as Credit Head for Transfer of Water Testing Charges");
                 return false;
         	}
         } else if(cmbMas_SL_type==111)
         {
          	if(flag==130301)
          	{
          		 return true;
          	}
          	else
          	{
          		alert("A/c Head 130301 Should be Added as Credit Head for Transfer of Water Testing Charges");
                  return false;
          	}
          }
         else if(cmbMas_SL_type==100)
         {
         	if(flagss==100)
         	{
         		 return true;
         	}
         	else
         	{
         		alert("A/c Head 100101 Should be Added as Debit Head for Bank Guarantee Maturity");
                 return false;
         	}
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
document.getElementById("txtAcc_HeadCode").value="";
document.getElementById("txtAcc_HeadDesc").value="";

//if(Jr_type==54 && txtAcc_HeadCode==900101)
//	{	
//	document.getElementById("txtAcc_HeadCode").disabled=true;
//	}
//else{document.getElementById("txtAcc_HeadCode").disable=false;}
	

//---------
    if(Jr_type==56 || Jr_type==57)
        document.getElementById("CHD").style.display='block';
    else
    {
        
        document.getElementById("txtCheque_NO").value="";
        document.getElementById("txtCheque_date").value="";
        document.getElementById("CHD").style.display='none';
    }
}

//function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
//{
//    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
//    if(blr_flag==1)             // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
//    {
//             call_clr();
//             doFunction_voucher('load_Voucher_No','null');
//    }
//}

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
         alert("No Journal Found");
    }
}



////////////////////

function load_Voucher_Details(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    //alert("FF");
    if(flag=="success")
    {
		if ( cheq_No=baseResponse.getElementsByTagName("cheq_No")[0]=null)
       var cheq_No=baseResponse.getElementsByTagName("cheq_No")[0].firstChild.nodeValue;         // here i assigned 
       var cheq_Date=baseResponse.getElementsByTagName("cheq_Date")[0].firstChild.nodeValue;
       var Remak=baseResponse.getElementsByTagName("Remak")[0].firstChild.nodeValue;
      
       var Mas_SL_type=baseResponse.getElementsByTagName("Mas_SL_type")[0].firstChild.nodeValue;
       var Mas_SL_code=baseResponse.getElementsByTagName("Mas_SL_code")[0].firstChild.nodeValue;  // uncommanded on 05-Sep-19
       
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
         
     // alert("testing"+AHcode.length);
         
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
        if(items[4]=="null" || items[4]==null)
        items[4]="--";
       
        items[5]=baseResponse.getElementsByTagName("SL_Code")[k].firstChild.nodeValue;
        if(items[5]==0)
        items[5]="";
        //alert("SL code **"+items[5]);
        
        items[6]=baseResponse.getElementsByTagName("desc_type")[k].firstChild.nodeValue;
        if(items[6]=="null" || items[6]==null)
        items[6]="--";
        //alert("SL code description**"+items[6]);
        if(items[7]=baseResponse.getElementsByTagName("Bill_NO")[k]=null)
         items[7]=baseResponse.getElementsByTagName("Bill_NO")[k].firstChild.nodeValue; 
        items[8]=baseResponse.getElementsByTagName("Bill_date")[k].firstChild.nodeValue;
        if(items[9]=baseResponse.getElementsByTagName("Bill_type")[k]=null)
        items[9]=baseResponse.getElementsByTagName("Bill_type")[k].firstChild.nodeValue;
        if(items[10]=baseResponse.getElementsByTagName("Agree_No")[k]=null)
        items[10]=baseResponse.getElementsByTagName("Agree_No")[k].firstChild.nodeValue;
        items[11]=baseResponse.getElementsByTagName("Agree_date")[k].firstChild.nodeValue;
        if(items[12]=baseResponse.getElementsByTagName("sub_amount")[k])
        items[12]=baseResponse.getElementsByTagName("sub_amount")[k].firstChild.nodeValue;
     //    alert(":::K:::"+ items[12]);  
        items[13]=baseResponse.getElementsByTagName("sub_part")[k].firstChild.nodeValue;
     //    alert("::sub_part:::"+ items[13]);  
        items[14]=baseResponse.getElementsByTagName("adjyear")[k].firstChild.nodeValue;
        items[15]=baseResponse.getElementsByTagName("adjmonth")[k].firstChild.nodeValue;
        items[16]=baseResponse.getElementsByTagName("CB_REF_NO")[k].firstChild.nodeValue;
       // alert("16>>CB_REF_NO>>"+k+">>"+ baseResponse.getElementsByTagName("CB_REF_NO")[k].firstChild.nodeValue);
        
        items[17]=baseResponse.getElementsByTagName("CB_REF_DATE")[k].firstChild.nodeValue;
        
        //items[18]=baseResponse.getElementsByTagName("MULTIPLE_PVRS")[k].firstChild.nodeValue;
       // items[19]=baseResponse.getElementsByTagName("MULTIPLE_PVR_DETAILS")[k].firstChild.nodeValue;
        
        if(baseResponse.getElementsByTagName("MULTIPLE_PVRS")[k].firstChild == null){
			items[18]="";
		}else{
			items[18]=baseResponse.getElementsByTagName("MULTIPLE_PVRS")[k].firstChild.nodeValue;
		}
		
		if(baseResponse.getElementsByTagName("MULTIPLE_PVR_DETAILS")[k].firstChild == null){
			items[19]="";
		}else{
			items[19]=baseResponse.getElementsByTagName("MULTIPLE_PVR_DETAILS")[k].firstChild.nodeValue;
		}
        
        items[20]=baseResponse.getElementsByTagName("CB_TDCA_REF_NO")[k].firstChild.nodeValue;
        items[21]=baseResponse.getElementsByTagName("CB_TDCA_REF_DATE")[k].firstChild.nodeValue;
        items[22]=baseResponse.getElementsByTagName("CB_TPA_REF_NO")[k].firstChild.nodeValue;
        items[23]=baseResponse.getElementsByTagName("CB_TPA_REF_DATE")[k].firstChild.nodeValue; 
        
        //items[24]=baseResponse.getElementsByTagName("VERIFIED")[k].firstChild.nodeValue;
        
        if(baseResponse.getElementsByTagName("VERIFIED")[k].firstChild == null){
			items[24]="";
		}else{
			items[24]=baseResponse.getElementsByTagName("VERIFIED")[k].firstChild.nodeValue;
		}
        
        
        items[25]=baseResponse.getElementsByTagName("VERIFIED_DATE")[k].firstChild.nodeValue;
        items[26]=baseResponse.getElementsByTagName("VERIFIED_AUTHORITY")[k].firstChild.nodeValue;
        
      
        
        document.getElementById("CB_REF_NO").value=items[16];
       // alert(document.getElementById("CB_REF_NO").value);
        
        document.getElementById("CB_REF_DATE").value=items[17];
        
        document.getElementById("MULTIPLE_PVRS").value=items[18];
      //  alert("MULTIPLE_PVRS>>>"+items[16]);
        document.getElementById("MULTIPLE_PVR_DETAILS").value=items[19];
      // alert("MULTIPLE_PVR_DETAILS>>>"+items[17]);
        document.getElementById("CB_TDCA_REF_NO").value=items[20];
       // alert("CB_TDCA_REF_NO>>>"+items[18]);
        document.getElementById("CB_TDCA_REF_DATE").value=items[21];
       // alert("CB_TDCA_REF_DATE>>>"+items[19]);
        document.getElementById("CB_TPA_REF_NO").value=items[22];
       // alert("CB_TPA_REF_NO>>>"+items[20]);
        document.getElementById("CB_TPA_REF_DATE").value=items[23];
        
        document.getElementById("VERIFIED").value=items[24];
        // alert("CB_TDCA_REF_DATE>>>"+items[19]);
         document.getElementById("VERIFIED_DATE").value=items[25];
        // alert("CB_TPA_REF_NO>>>"+items[20]);
         document.getElementById("VERIFIED_AUTHORITY").value=items[26];
        
        
        
        
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
        if(items[14]==0)
            items[14]="";
        if(items[15]==0)
            items[15]="";
        
        
        if(items[17]=="null")
            items[17]="";
        if(items[18]=="null")
            items[18]="";
        if(items[19]=="null")
            items[19]="";
        if(items[20]=="null")
            items[20]="";
        if(items[21]=="null")
            items[21]="";
        if(items[22]=="null")
            items[22]="";
        if(items[23]=="null")
            items[23]="";
        
        if(items[24]=="null")
            items[24]="";
        if(items[25]=="null")
            items[25]="";
        if(items[26]=="null")
            items[26]="";
        
        
       
        
        
         tbody=document.getElementById("grid_body");
        var mycurrent_row=document.createElement("TR");
        seq=seq+1;
        mycurrent_row.id=seq;
        //alert("row ID"+mycurrent_row.id);
       // alert(items[16]);
        // *siva changed on 2016-02-08  edit hide  the already process
        if (items[16]==0) {
          	 var cell=document.createElement("TD");
               var anc=document.createElement("A");
               var url="javascript:loadTable('"+mycurrent_row.id+"')";
               anc.href=url;
               var txtedit=document.createTextNode("EDIT");
               anc.appendChild(txtedit);
               cell.appendChild(anc);
               mycurrent_row.appendChild(cell);
               
   			
   		} else if(items[16]!=0){
   			 var cell=document.createElement("TD");
   		        var anc=document.createElement("A");
   		        var url="javascript:loadTable('"+mycurrent_row.id+"')";
   		       
   		        var txtedit=document.createTextNode("EDIT");
   		        anc.appendChild(txtedit);
   		        cell.appendChild(anc);
   		        mycurrent_row.appendChild(cell);   		        

   		}
        //*End 
        
       /* var cell=document.createElement("TD");
        var anc=document.createElement("A");
        var url="javascript:loadTable('"+mycurrent_row.id+"')";
        anc.href=url;
        var txtedit=document.createTextNode("EDIT");
        anc.appendChild(txtedit);
        cell.appendChild(anc);
        mycurrent_row.appendChild(cell);*/
        
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
                  
                  var sl_adjyear=document.createElement("input");
                  sl_adjyear.type="hidden";
                  sl_adjyear.name="sl_adjyear";
                  sl_adjyear.value=items[14];
                  cell2.appendChild(sl_adjyear);
                  
                  var sl_adjmonth=document.createElement("input");
                  sl_adjmonth.type="hidden";
                  sl_adjmonth.name="sl_adjmonth";
                  sl_adjmonth.value=items[15];
                  cell2.appendChild(sl_adjmonth);
                  
                  
                  var CB_REF_NO=document.createElement("input");
                  CB_REF_NO.type="hidden";
                  CB_REF_NO.name="CB_REF_NO";
                  CB_REF_NO.value=items[16];
                  cell2.appendChild(CB_REF_NO);
                 // alert("CB_REF_NO=**********=>"+CB_REF_NO.value);
//                   
                   //cell2=document.createElement("TD");
                   var CB_REF_DATE=document.createElement("input");
                   CB_REF_DATE.type="hidden";
                   CB_REF_DATE.name="CB_REF_DATE";
                   CB_REF_DATE.value=items[17];
                   cell2.appendChild(CB_REF_DATE);
                  // alert("CB_REF_DATE=**********=>"+items[15]);
                  
                   //cell2=document.createElement("TD");
                   var MULTIPLE_PVRS=document.createElement("input");
                   MULTIPLE_PVRS.type="hidden";
                   MULTIPLE_PVRS.name="MULTIPLE_PVRS";
                   MULTIPLE_PVRS.value=items[18];
                   cell2.appendChild(MULTIPLE_PVRS);
                   
                   //cell2=document.createElement("TD");
                   var MULTIPLE_PVR_DETAILS=document.createElement("input");
                   MULTIPLE_PVR_DETAILS.type="hidden";
                   MULTIPLE_PVR_DETAILS.name="MULTIPLE_PVR_DETAILS";
                   MULTIPLE_PVR_DETAILS.value=items[19];
                   cell2.appendChild(MULTIPLE_PVR_DETAILS);

                   var CB_TDCA_REF_NO=document.createElement("input");
                   CB_TDCA_REF_NO.type="hidden";
                   CB_TDCA_REF_NO.name="CB_TDCA_REF_NO";
                   CB_TDCA_REF_NO.value=items[20];
                   cell2.appendChild(CB_TDCA_REF_NO);
                   
                   var CB_TDCA_REF_DATE=document.createElement("input");
                   CB_TDCA_REF_DATE.type="hidden";
                   CB_TDCA_REF_DATE.name="CB_TDCA_REF_DATE";
                   CB_TDCA_REF_DATE.value=items[21];
                   cell2.appendChild(CB_TDCA_REF_DATE);
                   
                   var CB_TPA_REF_NO=document.createElement("input");
                   CB_TPA_REF_NO.type="hidden";
                   CB_TPA_REF_NO.name="CB_TPA_REF_NO";
                   CB_TPA_REF_NO.value=items[22];
                   cell2.appendChild(CB_TPA_REF_NO);
                   
                   var CB_TPA_REF_DATE=document.createElement("input");
                   CB_TPA_REF_DATE.type="hidden";
                   CB_TPA_REF_DATE.name="CB_TPA_REF_DATE";
                   CB_TPA_REF_DATE.value=items[23];
                   cell2.appendChild(CB_TPA_REF_DATE);
                   
                   
                   var VERIFIED=document.createElement("input");
                   VERIFIED.type="hidden";
                   VERIFIED.name="VERIFIED";
                   VERIFIED.value=items[24];
                   cell2.appendChild(VERIFIED);
                   
                   var VERIFIED_DATE=document.createElement("input");
                   VERIFIED_DATE.type="hidden";
                   VERIFIED_DATE.name="VERIFIED_DATE";
                   VERIFIED_DATE.value=items[25];
                   cell2.appendChild(VERIFIED_DATE);
                   
                   var VERIFIED_AUTHORITY=document.createElement("input");
                   VERIFIED_AUTHORITY.type="hidden";
                   VERIFIED_AUTHORITY.name="VERIFIED_AUTHORITY";
                   VERIFIED_AUTHORITY.value=items[26];
                   cell2.appendChild(VERIFIED_AUTHORITY);
                  
                  
                  

                  var currentText=document.createTextNode(items[12]);
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





function fordcb(val)
{
	if(document.getElementById("cmbSL_type").value==14)
	{
		document.getElementById("benifici").style.display='block';
	
	
	}else{
		document.getElementById("benifici").style.display='none';
		doFunction('Load_SL_Code',val);
	}
}

function call(command,param)
{
	if(command=="get")
	{
		var cmbOffice_code=document.getElementById("cmbOffice_code").value;
		var bentypeid=document.getElementById("dcb_ben_type").value;
	    var url="../../../../../Journal_General_Create.view?Command=get&bentypeid="+bentypeid+"&cmbOffice_code="+cmbOffice_code;
	           //alert(url);
	    var req=getTransport();
	    req.open("GET",url,true); 
	    req.onreadystatechange=function()
	    {
	      check_benifi(req);
	    }  ; 
	    req.send(null);
		
	}
	else if(command=="benifi"){
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	
    var url="../../../../../Journal_General_Create.view?Command=benifi&benficierysno="+param+"&cmbOffice_code="+cmbOffice_code;
           //alert(url);
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
      check_benifi(req);
    }  ; 
    req.send(null);
	}
}



function check_benifi(req)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
        	 response=req.responseXML.getElementsByTagName("response")[0];
             command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
             flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;

            if(command=="get")
            {
            	            	
                if(flag=='success')
                {
                	try{
                		
                		  var len=response.getElementsByTagName("beneficiarysno").length;
                		  
                		 var cmb_SL_Code=document.getElementById("cmbSL_Code");
                		 
                		
                         
                         var items_id=new Array();
                         var items_name=new Array();
                       
                            for(var i=0;i<len;i++)
                            {
                          	 
                          	items_id[i]=response.getElementsByTagName("beneficiarysno")[i].firstChild.nodeValue;
                           
                          	items_name[i]=response.getElementsByTagName("beneficiaryname")[i].firstChild.nodeValue;
                            
                            }
                            
                                                       
                          clear_Combo1(cmb_SL_Code);
                            //alert('here second');
                            for(var k=0;k<len;k++)
                            {   
                            	//alert(items_name[k]);
                                  var option=document.createElement("OPTION");
                                  option.text=items_name[k];
                                  option.value=items_id[k];
                                   try
                                  {
                                	   cmb_SL_Code.add(option);
                                	  
                                  }
                                  catch(errorObject)
                                  {
                                	  cmb_SL_Code.add(option,null);
                                	 
                                     // alert('error');
                                  }
                            }
                		
                		
                		
                		
                	}catch(e){alert("Error in lat"+e);}
                	
                	if( benfiflag==1)
                	{
                		benfiflag=0;            	
                	document.getElementById("cmbSL_Code").value=bensub;
                	
                	}
                	
                }else{
                	alert('Subledger Code Not Found');
                }
            }
                if(command=="benifi")
                {
                	            	
                    if(flag=='success')
                    {
                    	try{
                    		
                    		document.getElementById("dcb_ben_type").value=response.getElementsByTagName("bentypeid")[0].firstChild.nodeValue;
                    		  
                    		call('get','null');	
                    	}catch(e){alert(e);}
                    }
                }
             
        }
    }
}

function clear_Combo1(combo)
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

function headcode()
{
	document.getElementById("benifici").style.display='none';

//doFunction('checkCode','null');
	doFunctionBLOCK('checkCode','null');

}

function Acc_HeadCodeValidation()
{
	var hc=document.getElementById("txtAcc_HeadCode").value;
	if(parseInt(document.getElementById("cmbAcc_UnitCode"))!=5){
		if (Acc_HeadCode==610101 || (Acc_HeadCode==920320) ) {
			alert("610101,920320 HeadCodes Are Not Allowed ");
	        document.getElementById("txtAcc_HeadCode").value="";
	        document.getElementById("txtAcc_HeadCode").focus();
		}
		}
	else if((hc==780401) ||(hc==780402) ||(hc==780403) ||(hc==780405) ||(hc==780406) || (hc==160502))
    {
  	  alert("DCB Head Codes like 780401,780402,780403,780405,780406,160502 Are Not Allowed ");
        document.getElementById("txtAcc_HeadCode").value="";
        document.getElementById("txtAcc_HeadCode").focus();
    }
	else if((hc==620101)||(hc==620102))
	{
		alert("620101,620102 HeadCodes Are Not Allowed ");
        document.getElementById("txtAcc_HeadCode").value="";
        document.getElementById("txtAcc_HeadCode").focus();
	}else if((hc==391002) || (hc==390305) || (hc==391003) ||(hc==391302) || (hc==391303) ||(hc==391502) ||(hc==391503) )
    {			
  	  alert("GPF Account Head Code cannot be used here***");
        document.getElementById("txtAcc_HeadCode").value="";
        document.getElementById("txtAcc_HeadCode").focus();
    }
	
	
	var unitid=document.getElementById("cmbAcc_UnitCode").value;
	
	if(unitid!=5){
		if(unitid!=999){
	document.frmJournal_General_Edit.rad_sub_CR_DR[0].checked = true;
	document.frmJournal_General_Edit.rad_sub_CR_DR[1].disabled = false;
	                 
	var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;
	
	if(cmbMas_SL_type=="")
	{
	alert("Choose Journal Type in General");
	document.getElementById("txtAcc_HeadCode").value="";
	document.getElementById("txtAcc_HeadDesc").value="";
	//document.getElementById("cmbMas_SL_type").focus();
	return false;
	}
	var acchdcd = document.getElementById("acchdcd").value;
	var acchdcd2 = document.getElementById("acchdcd2").value;
	var flagg1;
	//alert("acchdcd2__________________"+acchdcd2);
	var acchdcd22=new Array();
	acchdcd22=acchdcd2.split(",");
	//alert("length------------"+acchdcd22.length);
	var date1=document.getElementById("txtCrea_date").value;
    var spl=date1.split("/");
   // if(spl[2]==2011 && spl[1]>03) updated by dhana on 1st feb
    if(spl[2]>=2011)
    {
    	
          if(spl[0]> 0 || spl[0]==0)
          {
              var Acc_HeadCode=parseInt(document.getElementById("txtAcc_HeadCode").value);
              var Acc_HeadCode1=document.getElementById("txtAcc_HeadCode").value;
             var digit=parseInt(Acc_HeadCode1.substr(0, 2));   
              var digit1=parseInt(Acc_HeadCode1.substr(4, 6));   
               if(digit==82){
            	  alert("This Account Head Code cannot be used here****");
            	   document.getElementById("txtAcc_HeadCode").value="";
                   document.getElementById("txtAcc_HeadDesc").value="";
                  document.getElementById("txtAcc_HeadCode").focus();
              }
             
              
              if(cmbMas_SL_type!=54)              {
            	   if((Acc_HeadCode==390302) || (Acc_HeadCode==390303) || (Acc_HeadCode==390305) || (Acc_HeadCode==391002) ||(Acc_HeadCode==391003) ||(Acc_HeadCode==391302) || (Acc_HeadCode==391303) ||(Acc_HeadCode==391502) ||(Acc_HeadCode==391503) )
                  {			
                  	  alert("GPF Account Head Code cannot be used here***");
                        document.getElementById("txtAcc_HeadCode").value="";
                        document.getElementById("txtAcc_HeadCode").focus();
                    }else {
              if(cmbMas_SL_type!=58){
              
             // if( (Acc_HeadCode==901001)|| (Acc_HeadCode==901002)||  (Acc_HeadCode==610101)|| (Acc_HeadCode==792141)|| (Acc_HeadCode==792041)) 
               if((Acc_HeadCode==900108) || (Acc_HeadCode==900109) || (Acc_HeadCode==901001)|| (Acc_HeadCode==901002)|| (Acc_HeadCode==100101)|| (Acc_HeadCode==792141)|| (Acc_HeadCode==792041) || (Acc_HeadCode==900301) || (Acc_HeadCode==620101) || (Acc_HeadCode==390602) || (Acc_HeadCode==780401) ||(Acc_HeadCode==780402) 
            		   ||(Acc_HeadCode==780403) ||(Acc_HeadCode==780405) ||(Acc_HeadCode==780406) || (Acc_HeadCode==160502) ||(Acc_HeadCode==782401)||(Acc_HeadCode==782402) ||(Acc_HeadCode==782403) ||(Acc_HeadCode==782404) ||(Acc_HeadCode==782405)||(Acc_HeadCode==782406)) 
              {
            	  
              if(cmbMas_SL_type!=88 && cmbMas_SL_type!=91 && cmbMas_SL_type!=100 && cmbMas_SL_type!=102)
              	{
                  alert("Account Head Code  780401,780402,780403,780405,780406,782401,782402,782403,782404,782405,782406,160502,900108,900109,901001,901002,610101, 792141 ,792041,620101,100101,390602,900301,782401,782402,782403,782404,782405 and 782406 Not Allowed ");
                  document.getElementById("txtAcc_HeadCode").value="";
                  document.getElementById("txtAcc_HeadDesc").value="";
                  document.getElementById("txtAcc_HeadCode").focus();
                  }
                  else if(cmbMas_SL_type==88)
                  {
                      if((Acc_HeadCode==900108) || (Acc_HeadCode==900109) || (Acc_HeadCode==901001)|| (Acc_HeadCode==901002)||  (Acc_HeadCode==610101)|| (Acc_HeadCode==792141)|| (Acc_HeadCode==792041) || (Acc_HeadCode==900301) || (Acc_HeadCode==620101) || (Acc_HeadCode==390602) || (Acc_HeadCode==780401) ||(Acc_HeadCode==780402) ||(Acc_HeadCode==780403) ||(Acc_HeadCode==780405) ||(Acc_HeadCode==780406) || (Acc_HeadCode==160502))
                      {
                          alert("Account Head Code  780401,780402,780403,780405,780406,160502,900108,900109,901001,901002,610101, 792141 ,792041,620101,390602 and 900301 Not Allowed ");
                          document.getElementById("txtAcc_HeadCode").value="";
                          document.getElementById("txtAcc_HeadDesc").value="";
                          document.getElementById("txtAcc_HeadCode").focus();
                      }
                      if((Acc_HeadCode==782401)||(Acc_HeadCode==782402)||(Acc_HeadCode==782403)||(Acc_HeadCode==782404)||(Acc_HeadCode==782405)||(Acc_HeadCode==782406))
                      {
                    	  alert("Account Head Code  782401,782402,782403,782405 and 782406 Not Allowed ");
                          document.getElementById("txtAcc_HeadCode").value="";
                          document.getElementById("txtAcc_HeadDesc").value="";
                          document.getElementById("txtAcc_HeadCode").focus();
                      }
                  }
                  else if(cmbMas_SL_type==100)
                  {
                	  if((Acc_HeadCode==900108) || (Acc_HeadCode==900109) || (Acc_HeadCode==901001)|| (Acc_HeadCode==901002)||  (Acc_HeadCode==610101)|| (Acc_HeadCode==792141)|| (Acc_HeadCode==792041) || (Acc_HeadCode==900301) || (Acc_HeadCode==620101) || (Acc_HeadCode==390602) || (Acc_HeadCode==780401) ||(Acc_HeadCode==780402) ||(Acc_HeadCode==780403) ||(Acc_HeadCode==780405) ||(Acc_HeadCode==780406) || (Acc_HeadCode==160502))
                      {
                          alert("Account Head Code  780401,780402,780403,780405,780406,160502,900108,900109,901001,901002,610101, 792141 ,792041,620101,390602 and 900301 Not Allowed ");
                          document.getElementById("txtAcc_HeadCode").value="";
                          document.getElementById("txtAcc_HeadDesc").value="";
                          document.getElementById("txtAcc_HeadCode").focus();
                      }
                	  if((Acc_HeadCode==782401)||(Acc_HeadCode==782402)||(Acc_HeadCode==782403)||(Acc_HeadCode==782404)||(Acc_HeadCode==782405)||(Acc_HeadCode==782406))
                      {
                    	  alert("Account Head Code  782401,782402,782403,782405 and 782406 Not Allowed ");
                          document.getElementById("txtAcc_HeadCode").value="";
                          document.getElementById("txtAcc_HeadDesc").value="";
                          document.getElementById("txtAcc_HeadCode").focus();
                      }
                  }
                  else if(cmbMas_SL_type==102)
                  {
                	  if((Acc_HeadCode==900108) || (Acc_HeadCode==900109) || (Acc_HeadCode==901001)|| (Acc_HeadCode==901002)||  (Acc_HeadCode==610101)|| (Acc_HeadCode==792141)|| (Acc_HeadCode==792041) || (Acc_HeadCode==900301) || (Acc_HeadCode==620101) || (Acc_HeadCode==390602) || (Acc_HeadCode==780401) ||(Acc_HeadCode==780402) ||(Acc_HeadCode==780403) ||(Acc_HeadCode==780405) ||(Acc_HeadCode==780406) || (Acc_HeadCode==160502))
                      {
                          alert("Account Head Code  780401,780402,780403,780405,780406,160502,900108,900109,901001,901002,610101, 792141 ,792041,620101,390602 and 900301 Not Allowed ");
                          document.getElementById("txtAcc_HeadCode").value="";
                          document.getElementById("txtAcc_HeadDesc").value="";
                          document.getElementById("txtAcc_HeadCode").focus();
                      }
                  }
              
              }
               
               
              else if((Acc_HeadCode==120201) && (cmbMas_SL_type!=59))
              {
              alert("Account Head Code  780401,780402,780403,780405,780406,160502,900108,900109,901001,901002,610101, 792141 ,792041,620101,100101,120201,782401,782402,782403,782404,782405,782406 and 900301 Not Allowed ");
              document.getElementById("txtAcc_HeadCode").value="";
                 document.getElementById("txtAcc_HeadDesc").value="";
              document.getElementById("txtAcc_HeadCode").focus();
              }
               /// Joe   *****  change for head start with 82
             /* else if((digit==82) && (cmbMas_SL_type==59))
              {
              }
              
              else if((digit==82) && (cmbMas_SL_type==86) && (digit1==02))
              {
              for(i=0;i<=acchdcd22.length;i++)
              {
              if(Acc_HeadCode==acchdcd22[i])
              {
              flagg1="86yes"              
              }
              }
              if(flagg1=="86yes")
              {
              document.frmJournal_General_Edit.rad_sub_CR_DR[0].checked = true;
                 document.frmJournal_General_Edit.rad_sub_CR_DR[1].disabled = true;
              }else{
              alert("This Account Head Code cannot be used here:::");
              document.getElementById("txtAcc_HeadCode").value="";
                 document.getElementById("txtAcc_HeadDesc").value="";
              document.getElementById("txtAcc_HeadCode").focus();
              }
              }
              
            else if((digit==82) && (cmbMas_SL_type!=82) )
              {
               if(cmbMas_SL_type!=98)
               {
            	   if(cmbMas_SL_type!=100){
		             alert("Tis Account Head Code cannot be used here:::");
		              document.getElementById("txtAcc_HeadCode").value="";
		                 document.getElementById("txtAcc_HeadDesc").value="";
		              document.getElementById("txtAcc_HeadCode").focus();
		              
		              } 
               }
              }
              else if(digit==82 )
              {              
              if(cmbMas_SL_type!=82)
              {                            
                if(acchdcd!=Acc_HeadCode)
                {
                 alert("This Account Head Code cannot be used here***");
              document.getElementById("txtAcc_HeadCode").value="";
               document.getElementById("txtAcc_HeadDesc").value="";
              document.getElementById("txtAcc_HeadCode").focus();
                }else{
                 document.frmJournal_General_Edit.rad_sub_CR_DR[0].disabled = true;
                 document.frmJournal_General_Edit.rad_sub_CR_DR[1].checked = true;
                }
              
              }
              }
              */
            /*  else if(digit==82){
            	  alert("This Account Head Code cannot be used here****");
            	   document.getElementById("txtAcc_HeadCode").value="";
                   document.getElementById("txtAcc_HeadDesc").value="";
                  document.getElementById("txtAcc_HeadCode").focus();
              }*/
              }
              
              else if(cmbMas_SL_type==54)
        	  {
        	  if((Acc_HeadCode==390302) || (Acc_HeadCode==390303))
        		  {
        		   document.frmJournal_General_Edit.rad_sub_CR_DR[1].disabled = true;
                   document.frmJournal_General_Edit.rad_sub_CR_DR[0].checked = true;
        		
        		  }
        	  else if ((Acc_HeadCode==390304) || (Acc_HeadCode==390305)){
        		  alert("Account Head Code  390304 and 390305 Not Allowed ");
                  document.getElementById("txtAcc_HeadCode").value="";
                     document.getElementById("txtAcc_HeadDesc").value="";
                  document.getElementById("txtAcc_HeadCode").focus();
        	  }
        	  }
              
              
                  else if(cmbMas_SL_type==58)
                  {
                   if((Acc_HeadCode==550102) || (Acc_HeadCode==900108) ||  (Acc_HeadCode==900109) || (Acc_HeadCode==901001)|| (Acc_HeadCode==901002)||  (Acc_HeadCode==610101)|| (Acc_HeadCode==792141)|| (Acc_HeadCode==792041) || (Acc_HeadCode==900301) 
                		   || (Acc_HeadCode==620101) || (Acc_HeadCode==120201) || (Acc_HeadCode==390602) || (Acc_HeadCode==780401) ||(Acc_HeadCode==780402) ||(Acc_HeadCode==780403) ||(Acc_HeadCode==780405) ||(Acc_HeadCode==780406) || (Acc_HeadCode==160502) ||(Acc_HeadCode==782401)||(Acc_HeadCode==782402)||(Acc_HeadCode==782403)(Acc_HeadCode==782404)||(Acc_HeadCode==782405)||(Acc_HeadCode==782406))  
                  {
                  alert("Account Head Code  550102,780401,780402,780403,780405,780406,160502,900108,900109,901001,901002,610101, 792141 ,792041,620101,120201,390602,782401,782402,782403,782404,782405,782406 and 900301 Not Allowed ");
                  document.getElementById("txtAcc_HeadCode").value="";
                     document.getElementById("txtAcc_HeadDesc").value="";
                  document.getElementById("txtAcc_HeadCode").focus();
                  }
                /*   else if((digit==82) && (cmbMas_SL_type==59))
                  {
                  }
                else if((digit==82) && (cmbMas_SL_type!=82))
                  {
                   
                 alert("This Account Head Code cannot be used here>>>");
                  document.getElementById("txtAcc_HeadCode").value="";
                     document.getElementById("txtAcc_HeadDesc").value="";
                  document.getElementById("txtAcc_HeadCode").focus();
                  
                  }else if(digit==82)
                  {              
                  if(cmbMas_SL_type==82)
                  {                            
                    if(acchdcd!=Acc_HeadCode)
                    {
                     alert("This Account Head Code cannot be used here....");
                  document.getElementById("txtAcc_HeadCode").value="";
                   document.getElementById("txtAcc_HeadDesc").value="";
                  document.getElementById("txtAcc_HeadCode").focus();
                    }else{
                     document.frmJournal_General_Edit.rad_sub_CR_DR[0].disabled = true;
                     document.frmJournal_General_Edit.rad_sub_CR_DR[1].checked = true;
                    }
                                                     
                  }else
                  {             
                 alert("This Account Head Code cannot be used here???");
                  document.getElementById("txtAcc_HeadCode").value="";
                   document.getElementById("txtAcc_HeadDesc").value="";
                  document.getElementById("txtAcc_HeadCode").focus();
                  }
                  }*/
               /*    else if(digit==82){
                 	  alert("This Account Head Code cannot be used here****");
                 	   document.getElementById("txtAcc_HeadCode").value="";
                        document.getElementById("txtAcc_HeadDesc").value="";
                       document.getElementById("txtAcc_HeadCode").focus();
                   }*/
                  }
              }
              } else if(cmbMas_SL_type==54)
            	  {
            	  if((Acc_HeadCode==390302) || (Acc_HeadCode==390303))
            		  {
            		   document.frmJournal_General.rad_sub_CR_DR[1].disabled = true;
                       document.frmJournal_General.rad_sub_CR_DR[0].checked = true;
            		
            		  }
            	  else if ((Acc_HeadCode==390304) || (Acc_HeadCode==390305)){
            		  alert("Account Head Code  390304 and 390305 Not Allowed ");
                      document.getElementById("txtAcc_HeadCode").value="";
                         document.getElementById("txtAcc_HeadDesc").value="";
                      document.getElementById("txtAcc_HeadCode").focus();
            	  }else if((Acc_HeadCode==391002) || (Acc_HeadCode==390305) ||(Acc_HeadCode==391003) ||(Acc_HeadCode==391302) || (Acc_HeadCode==391303) ||(Acc_HeadCode==391502) ||(Acc_HeadCode==391503) )
                  {			
                  	  alert("GPF Account Head Code cannot be used here***");
                        document.getElementById("txtAcc_HeadCode").value="";
                        document.getElementById("txtAcc_HeadCode").focus();
                    }
            	  }

              else{            
              if((Acc_HeadCode==900108) || (Acc_HeadCode==900109) || (Acc_HeadCode==901002)|| (Acc_HeadCode==610101) || (Acc_HeadCode==100101) || (Acc_HeadCode==120201) || (Acc_HeadCode==780401) ||(Acc_HeadCode==780402) ||(Acc_HeadCode==780403) ||(Acc_HeadCode==780405) ||(Acc_HeadCode==780406) || (Acc_HeadCode==160502))
              {
              alert("Account Head Code  780401,780402,780403,780405,780406,160502,900108,900109,901002, 610101,100101 and 120201 Not Allowed ");
              document.getElementById("txtAcc_HeadCode").value="";
              document.getElementById("txtAcc_HeadCode").focus();
              }
              else if((Acc_HeadCode==782401)||(Acc_HeadCode==782402)||(Acc_HeadCode==782403)||(Acc_HeadCode==782404)||(Acc_HeadCode==782405)||(Acc_HeadCode==782406))
              {
            	  alert("Account Head Code  782401,782402,782403,782405,782406 and 782404 Not Allowed ");
                  document.getElementById("txtAcc_HeadCode").value="";
                  document.getElementById("txtAcc_HeadCode").focus();
              }else if((Acc_HeadCode==390302) || (Acc_HeadCode==390303) || (Acc_HeadCode==390305) || (Acc_HeadCode==391002) ||(Acc_HeadCode==391003) ||(Acc_HeadCode==391302) || (Acc_HeadCode==391303) ||(Acc_HeadCode==391502) ||(Acc_HeadCode==391503) )
              {			
              	  alert("GPF Account Head Code cannot be used here***");
                    document.getElementById("txtAcc_HeadCode").value="";
                    document.getElementById("txtAcc_HeadCode").focus();
                }
            /*  else if(digit==82)
              {             
              if(cmbMas_SL_type==82)
              {                          
                if(acchdcd!=Acc_HeadCode)
                {
                 alert("This Account Head Code cannot be used here:");
              document.getElementById("txtAcc_HeadCode").value="";
               document.getElementById("txtAcc_HeadDesc").value="";
              document.getElementById("txtAcc_HeadCode").focus();
                }else{
                 document.frmJournal_General_Edit.rad_sub_CR_DR[0].disabled = true;
                 document.frmJournal_General_Edit.rad_sub_CR_DR[1].checked = true;
                }
                                                 
              }
              }*/
            /*  else if(digit==82){
            	  alert("This Account Head Code cannot be used here****");
            	   document.getElementById("txtAcc_HeadCode").value="";
                   document.getElementById("txtAcc_HeadDesc").value="";
                  document.getElementById("txtAcc_HeadCode").focus();
              }*/
              }
              
         }
         
       
         
  }
	  if(cmbMas_SL_type==53)
	  {
	  var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	  
	     url = "../../../../../Journal_General_Create.view?Command=checkLiability&txtAcc_HeadCode="+ txtAcc_HeadCode;    
	    // alert(url);
		var req = getTransport();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			Mandatory(req);
		}
		req.send(null); 
  
	}
	}
	}
	
}

function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;

			if (command == "HeadCodeValidation1") {
				HeadCodeValidation11(baseResponse);
			} 
                        else if (command == "checkLiability") {
                        var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                       
                        if (flag == "success") {
                            alert("Cannot Enter Liability Heads in GJV ");
                            document.getElementById("txtAcc_HeadCode").value="";
                            document.getElementById("txtAcc_HeadDesc").value="";
                            
                        }
                        }
		}
	}
}
function HeadCodeValidation1(path)
{
	//alert(path);
	var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;
	if(cmbMas_SL_type==54)
		{
	var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
	 var url=path + "/Journal_General_Create.view?Command=HeadCodeValidation1&txtAcc_HeadCode="+txtAcc_HeadCode;
	// alert(url);
	 var req=getTransport();
     req.open("GET",url,true); 
     req.onreadystatechange=function()
     {
    	 manipulate(req);
     }   
     req.send(null);
		}
}

function HeadCodeValidation11(baseResponse) {
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
if (flag == "success") {
	var len4 = baseResponse.getElementsByTagName("subLedgerType").length;	
	for ( var i = 0; i < len4; i++) {
		var subLedgerType = baseResponse
				.getElementsByTagName("subLedgerType")[i].firstChild.nodeValue;		
		if(subLedgerType=="7")
			{
			document.getElementById("sl1_type").value="7";
                       
			}				
	}
} 
}



//function checkSubLedgerMandatory()
//{
//var Option="test";
//var txtUnitId=0;
//var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
//	url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
//			+ txtAcc_HeadCode + "&Option="+ Option + "&txtUnitId="+ txtUnitId;                      
//	var req = getTransport();
//	req.open("GET", url, true);
//	req.onreadystatechange = function() {
//		Mandatory(req);
//	}
//	req.send(null);
//
//}


function checkSubLedgerMandatory()
{
var Option="test";
var txtUnitId=0;
var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory_payroll&txtAcc_HeadCode="
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
			if (Command == "checkSubLedgerMandatory_payroll") {
				if (flag == "success") {
					var M_flag = baseResponse.getElementsByTagName("M_flag")[0].firstChild.nodeValue;
                                       
					if(M_flag=="Madatory")
                                        {
										  var sl1_type = document.getElementById("sl1_type").value;
				                           if(sl1_type==7)
				                           {
				                          	
				                           ADD_GRID("N");
				                           }
				                           else
				                           {
	                                       ADD_GRID("Y");
				                           }
                                        }
                                        else if(M_flag=="Not_Madatory")
                                        {
                                        var sl1_type = document.getElementById("sl1_type").value;
                                      //  alert("sl1_type::"+sl1_type);
                                         if(sl1_type==7)
                                         {
                                        	// alert("77777");
                                         ADD_GRID("N");
                                         }
                                        else
                                        {
                                        //	alert("not 77777");
                                       ADD_GRID("N");
                                        }
                                        }
				}
			}
                        else if (Command == "checkLiability") {
                        
                        if (flag == "success") {
                            alert("Cannot Enter Liability Heads in GJV ");
                            document.getElementById("txtAcc_HeadCode").value="";
                            document.getElementById("txtAcc_HeadDesc").value="";
                            
                        }
                        }
		}
	}

	///////////////on 12-11-2019////////////////
//	if (req.readyState == 4) {
//		if (req.status == 200) {
//			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
//			var tagcommand = baseResponse.getElementsByTagName("command")[0];
//			var Command = tagcommand.firstChild.nodeValue;
//			var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
//			if (Command == "checkSubLedgerMandatory") {
//				if (flag == "success") {
//					var M_flag = baseResponse.getElementsByTagName("M_flag")[0].firstChild.nodeValue;
//                                       
//					if(M_flag=="Madatory")
//                                        {
//                                      
//                                       ADD_GRID();
//					}
//                                        else if(M_flag=="Not_Madatory")
//                                        {
//                                        	
//                                        var sl1_type = document.getElementById("sl1_type").value;
//                                         if(sl1_type==7)
//                                         {
//                                         ADD_GRID();
//                                         }
//                                         else if(document.getElementById("cmbSL_type").value=="")
//                                        {
//                                        alert("Select a Sub-Ledger Type");
//		                        return false;
//                                        }
//                                        if(document.getElementById("cmbSL_Code").value=="")
//                                        {
//                                        alert("Select a Sub-Ledger Code");
//		                        return false;
//                                        }
//                                        else
//                                        {
//                                       ADD_GRID();
//                                        }
//                                        
//                                        
//                                        }
//				}
//			}
//		}
//	}
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
                                        	  //Lakshmi 11Nov13  Lakshmi
                                        	var cmbMas_SL_type1 = document.getElementById("cmbMas_SL_type").value;	
                                       if(cmbMas_SL_type1==54){
                                    	   update_GRID();
                                       }else {
                                        var sl1_type = document.getElementById("sl1_type").value;
                                         if(sl1_type==7)
                                         {
                                         update_GRID();
                                         }
                                         else if(document.getElementById("cmbSL_type").value=="")
                                        {
                                        alert("Select a Sub-Ledger Type");
                                        return false;
                                        }
                                       
                                        else if(document.getElementById("cmbSL_Code").value=="")
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
}
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
                 var url="../../../../../Receipt_SL.view?Command=check_TB_Jrnl&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
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
            	
            	call_clr();
            	doFunction_voucher('load_Voucher_No','null');
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
                    dateCtrl.focus();
                    //document.getElementById("txtReceipt_No").value="";
               }
             else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");//return false;//
                    dateCtrl.focus();
                   // document.getElementById("txtReceipt_No").value="";     
               }
             else if(flag=="finyearLJVN")
             {
                        // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                  dateCtrl.value="";
                  alert("Cash Book Control Not Found for Journal");//return false;//
                  dateCtrl.focus();
                 // document.getElementById("txtReceipt_No").value="";     
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