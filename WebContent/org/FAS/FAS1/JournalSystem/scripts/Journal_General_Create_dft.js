var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;
var job_flag;
var common_AHead_code_flag="";
var emp_flag;

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


// /////////////////////////////////////////// XML req
// /////////////////////////////////////////////////////
window.onunload=function()
{
    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
    if (winjob && winjob.open && !winjob.closed) winjob.close();
    if (winemp && winemp.open && !winemp.closed) winemp.close();

}

function pending()
{
	document.getElementById("num").value="";
        document.getElementById("date1").value="";
	var cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;// alert(cmbMas_SL_type);
	if(cmbMas_SL_type==61)		
	  document.getElementById('yr_mon').style.display='block';
	else
        {
	  document.getElementById('yr_mon').style.display='none';
          document.getElementById("num").value="";
          document.getElementById("date1").value="";
          }
}

var winPendingbills;
function call_pendingbills()
{
     if(document.getElementById("txtCash_Month").value=="")
     {
         alert("Enter Month");
         return false;
     }
      if(document.getElementById("txtCash_year").value=="")
      {
        alert("Enter Year");
        return false;
      }
        if(winPendingbills && winPendingbills.open && !winPendingbills.closed) 
        {
           winPendingbills.resizeTo(500,500);
           winPendingbills.moveTo(250,250); 
           winPendingbills.focus();
        }
        else
        {
            winPendingbills=null;
        }
      
      var unitcode=document.getElementById("cmbAcc_UnitCode").value;    
      var offid=document.getElementById("cmbOffice_code").value;
      var month=document.getElementById("txtCash_Month").value;    // used to
																	// get cash
																	// book
																	// month and
																	// year in
																	// "PendingBills.jsp"
      var year=document.getElementById("txtCash_year").value;    
      var cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;    
              
    winPendingbills= window.open("../../../../../org/FAS/FAS1/JournalSystem/jsps/Journal_General_Pending.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&month="+month+"&year="+year+"&cmbMas_SL_type="+cmbMas_SL_type,"PendingBillsSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winPendingbills.moveTo(250,250);  
    winPendingbills.focus();
}

// /////////////////////////////////////////// load Table()
// /////////////////////////////////////////////////////
var benfiflag=0;
var bensub=0;
function loadTable(scod)
{
	benfiflag=1;
        com_id=scod;                                    // to identify in
														// UPDATE_GRID ,which
														// row loaded
        clearall();
       // document.FasAcc_Headform.cmdadd.disabled=true;
       // document.getElementById("txtAcc_HeadCode").readOnly=true; // do not
		// change the Account Head
       // text_field.readOnly=true;
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
         
         if(com_cmbSL_type==14)
         {
        	 bensub=com_cmbSL_Code;
        	 document.getElementById("benifici").style.display='block';
        	 doFunction('checkCode','null');
        	 call('benifi',com_cmbSL_Code);
         }else{        
        	 document.getElementById("benifici").style.display='none';
                //doFunction('checkCode','null');
               doFunction('Load_SL_Code',com_cmbSL_type);
         } 
         
                    
        setTimeout('document.getElementById("cmbSL_type").value=com_cmbSL_type',900); 
        setTimeout('document.getElementById("cmbSL_Code").value=com_cmbSL_Code',900); 
         if(rcells.item(2).firstChild.value=="CR")
         document.frmJournal_General.rad_sub_CR_DR[0].checked=true;
         else if(rcells.item(2).firstChild.value=="DR")
         document.frmJournal_General.rad_sub_CR_DR[1].checked=true;
         
      // try{document.getElementById("cmbSL_Code").value=rcells.item(4).firstChild.value;}catch(e){}
       
       // try{document.getElementById("txtsub_Recei_from").value=rcells.item(5).firstChild.value;}catch(e){}
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
        
    document.frmJournal_General.cmdupdate.style.display='block';
    document.frmJournal_General.cmddelete.disabled=false;
    document.frmJournal_General.cmdadd.style.display='none';
}


/////////////////////////////////////////////   ADD & UPDATE & DELETE // ///////////////////////////////////////////////////

function ADD_GRID()
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
      // alert(isMan.account_head_status);
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
     //  alert("cmbMas_SL_type:"+cmbMas_SL_type);
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
       
          /* else
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
            } */
           
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
        if(document.frmJournal_General.rad_sub_CR_DR[0].checked==true)
          items[2]=document.frmJournal_General.rad_sub_CR_DR[0].value;
        else if(document.frmJournal_General.rad_sub_CR_DR[1].checked==true)
          items[2]=document.frmJournal_General.rad_sub_CR_DR[1].value;
        
        items[3]=document.getElementById("cmbSL_type").value;
        if(document.getElementById("cmbSL_type").value=="")
        {
        //items[4]="Not Available";
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
         
         document.frmJournal_General.rad_sub_CR_DR[0].checked = true;
                 document.frmJournal_General.rad_sub_CR_DR[1].disabled = false;
}  

function clear_main_fields()
{ 
document.frmJournal_General.rad_sub_CR_DR[0].disabled = false;
//document.getElementById("acchdcd").value=""; 	
     document.getElementById("offlist_div_trans").style.display='none';
     document.getElementById("emplist_div_trans").style.display='none';
     
    document.getElementById("txtAcc_HeadCode").value="";
    // document.getElementById("txtAcc_HeadCode").readOnly=false;
    document.getElementById("txtAcc_HeadDesc").value="";
    document.frmJournal_General.rad_sub_CR_DR[0].checked=true;
    document.getElementById("cmbSL_type").value="";
    document.getElementById("cmbSL_Code").value="";
 /*
	 * document.getElementById("txtBill_NO").value="";
	 * document.getElementById("txtBill_date").value="";
	 * document.getElementById("txtBill_type").value="";
	 * document.getElementById("txtAgree_No").value="";
	 * document.getElementById("txtAgree_Date").value="";
	 */
    // document.getElementById("txtsub_Recei_from").value="";
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
            
     document.frmJournal_General.cmdadd.style.display='block';
     document.frmJournal_General.cmdupdate.style.display='none';
     document.frmJournal_General.cmddelete.disabled=true;
}

function update_GRID()
{      
var acchdcd2 = document.getElementById("acchdcd2").value;
var flagg1;

var acchdcd22=new Array();
acchdcd22=acchdcd2.split(",")
var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;
if(document.frmJournal_General.rad_sub_CR_DR[0].checked==true)
{
var rad_sub_CR_DR = document.frmJournal_General.rad_sub_CR_DR[0].value;
}else
{
var rad_sub_CR_DR = document.frmJournal_General.rad_sub_CR_DR[1].value;
}
var Acc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
var Acc_HeadCode1=document.getElementById("txtAcc_HeadCode").value;
              var digit=parseInt(Acc_HeadCode1.substr(0, 2));   
              var digit1=parseInt(Acc_HeadCode1.substr(4, 6));     


if((digit==82) && (cmbMas_SL_type==86) && (digit1==02))
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
               if(rad_sub_CR_DR=="DR")
					{
					alert("CR/DR Indicator Should be Credit only..");
					return false;
					}
              }else{
              alert("This Account Head Code cannot be updated here:::");
              document.getElementById("txtAcc_HeadCode").value="";
                 document.getElementById("txtAcc_HeadDesc").value="";
              document.getElementById("txtAcc_HeadCode").focus();
              }
              } 



var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;


        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
        alert("Enter A/c Head Code");
        return false;
        }
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
        
     	   var sl1_type = document.getElementById("sl1_type").value;
     	   if(sl1_type==7)
     		   {
     		   
     		   }else{
     			   if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
     	            {
     	                
     	                                alert("Select a Sub-Ledger Type");
     	                                return false;
     	                            
     	                       
     	            }
     		   }
            
             }else
             {
             
             if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
             {
                 
                                 alert("Select a Sub-Ledger Type");
                                 return false;
                             
                        
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
       /*
		 * if(document.getElementById("txtsub_Recei_from").value.length==0) {
		 * alert("Enter the value in Received From Field");
		 * document.getElementById("txtsub_Recei_from").focus(); return false; }
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
        if(document.frmJournal_General.rad_sub_CR_DR[0].checked==true)
          items[2]=document.frmJournal_General.rad_sub_CR_DR[0].value;
        else if(document.frmJournal_General.rad_sub_CR_DR[1].checked==true)
          items[2]=document.frmJournal_General.rad_sub_CR_DR[1].value;
        items[3]=document.getElementById("cmbSL_type").value;
        if(document.getElementById("cmbSL_type").value=="")
        {
        //items[4]="Not Available";
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

/////////////////////////////////////////////   clearall() by User // ///////////////////////////////////////////////////

function clearall()
{
     document.getElementById("offlist_div_trans").style.display='none';
     document.getElementById("emplist_div_trans").style.display='none';
     
    document.getElementById("txtAcc_HeadCode").value="";
    // document.getElementById("txtAcc_HeadCode").readOnly=false;
    document.getElementById("txtAcc_HeadDesc").value="";
    document.frmJournal_General.rad_sub_CR_DR[0].checked=true;
    document.getElementById("cmbSL_type").value="";
    document.getElementById("cmbSL_Code").value="";
    // document.getElementById("txtsub_Recei_from").value="";
     document.getElementById("txtBill_NO").value="";
    document.getElementById("txtBill_date").value="";
    document.getElementById("txtBill_type").value="";
    document.getElementById("txtAgree_No").value="";
    document.getElementById("txtAgree_Date").value="";
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
            
 document.frmJournal_General.cmdadd.style.display='block';
 document.frmJournal_General.cmdupdate.style.display='none';
 document.frmJournal_General.cmddelete.disabled=true;
}
function call_clr()
{
//alert("tet");
   // document.getElementById("txtAmount").value="";
    document.getElementById("txtCheque_NO").value="";
    document.getElementById("txtCheque_date").value="";
    document.getElementById("txtRemarks").value="";
    
    document.getElementById("cmbMas_SL_type").value="";
    // clear_Combo(document.getElementById("cmbMas_SL_Code"));
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


/////////////////////////////////////////////   checkNull() by User // ///////////////////////////////////////////////////

function checkNull()
{
var tbody=document.getElementById("grid_body");
   // alert("tbody.rows.length :"+tbody.rows.length);
if(document.getElementById("cmbAcc_UnitCode").value=="")
{
    alert("Select the Account Unit code");
    // document.getElementById("txtAcc_HeadDesc").focus();
    return false;    
}
if(document.getElementById("cmbOffice_code").value=="")
{
    alert("Select the Office Code");
    // document.getElementById("cmbOffice_code").focus();
    return false;
}
if(document.getElementById("txtCrea_date").value.length==0)
{
    alert("Enter the Date of Creation");
    // document.getElementById("txtCrea_date").focus();
    return false;    
}
	var date_one=document.getElementById("txtCrea_date").value;
	var spl_it=date_one.split("/");
	//alert("yr::::"+spl_it[2]);
	//alert(spl_it[2].length);
	if(spl_it[2].length!=4)
	{
	alert("Please verify Date in General");
	}
if (document.getElementById("txtRemarks").value== "") {
		alert("Enter Remarks");
		// document.getElementById("txtCrea_date").focus();
		return false;
	}


if(document.getElementById("cmbMas_SL_type").value=="")
{
    //if(document.getElementById("cmbMas_SL_Code").value=="")
    // {
    alert("Select The Journal Type in General");
    return false;
    // }
}
if(document.getElementById("cmbMas_SL_type").value==61)
{
    if(document.getElementById("num").value=="" || document.getElementById("date1").value=="")
    {
    alert("Choose Pending Vouchers for Adjustment Journal In General");
    return false;
    }
   
}


if(document.getElementById("cmbMas_SL_type").value!="" && (document.getElementById("cmbMas_SL_type").value==6 || document.getElementById("cmbMas_SL_type").value==7 ))
{
    if(document.getElementById("txtCheque_NO").value.length==0 || document.getElementById("txtCheque_date").value.length==0)
    {
    alert("Enter Both Cheque Number and Date in General");
    return false;
    }
}



if(tbody.rows.length==0)
{
    alert("Enter the Details Part");
    // document.getElementById("txtAmount").focus();
    return false; 
}

if(tbody.rows.length>0)
{

var flag=0,flagss=0;
var flagg=0;
var flagg95=0,flagg98=0;
var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;
var acchdcd1 = document.getElementById("acchdcd1").value;
        var check_amt=0;
        var cr_amt=0;
        var db_amt=0;
        var ttGrid =new Array();
        rows=tbody.getElementsByTagName("tr");
      
        for(i=0;i<rows.length;i++)
        {
            var cells=rows[i].cells;
           
           
            if(cells.item(2).lastChild.nodeValue=='CR')
            {
                    var ac_code=cells.item(1).lastChild.nodeValue;
                 var code1= ac_code.split("-");
               
              
                         cr_amt=parseFloat(cr_amt) + parseFloat(cells.item(7).lastChild.nodeValue);
                    // alert();    
                if( (code1[0]==100101) && (cmbMas_SL_type==58) )
                {
               flag=1;
                }
                else if( (code1[0]==120201) && (cmbMas_SL_type==59) )
                {
               flag=2;
                }
                else if( ((code1[0]==550310) || (code1[0]==792141) || (code1[0]==792041) || (code1[0]==100101) || (code1[0]==390602) ) && (cmbMas_SL_type==54) )
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
                else if(cmbMas_SL_type==98)
                {
                	var ac_codee=cells.item(1).lastChild.nodeValue;
                    var code11= ac_codee.split("-");	
                	if(code11[0]==acchdcd1)
                    {
                		flagg98=flagg98+1;
                    }
                }
           /*     else if(cmbMas_SL_type==91)
                {
//             sl_adjyear  11  12
//             sl_adjmonth
               // var tt=tbody.rows[0].cells.length;
                 var ac_code=cells.item(8).lastChild.nodeValue;
                 //var cells=tt.rows[i].cells;
                alert(ac_code);
                
                    if(document.getElementById("txtCB_Year").value!=""||document.getElementById("txtCB_Month").value!="")
                    {
                   
                        flag=7;
                    }
                }  */
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
                else if(cmbMas_SL_type==94)
                {
                	if(code1[0]==370304)
                	{
                		flag=999;
                	}
                }
                else if(cmbMas_SL_type==95)
                {
                	var ac_codee=cells.item(1).lastChild.nodeValue;
                    var code11= ac_codee.split("-");	
                	if(code11[0]==acchdcd1)
                    {
                		flagg95=flagg95+1;
                    }
                }
                else if( (code1[0]==100101) && (cmbMas_SL_type==100) )
                {
                	flagss=100;
                }
                
                
             
                 db_amt=parseFloat(db_amt) + parseFloat(cells.item(7).lastChild.nodeValue);
            }
    
        }

        

        for(i=0;i<rows.length;i++)
        {
        
        var cells=rows[i].cells;
        
        var ac_codee=cells.item(1).lastChild.nodeValue;
         var code11= ac_codee.split("-");
         
         
         if(code11[0]==acchdcd1)
       {
       flagg=flagg+1;
       }
        }

        //alert(cr_amt+" "+db_amt);
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
        }else  if( (flagg==0) && (cmbMas_SL_type==82) ) 
       {
       alert("You Should Enter Atleast one Detail for "+acchdcd1);
        return false;
       }
      
      
        if(cmbMas_SL_type==58)
        {
        if(flag==1) {
        return true;
        }else{
        alert("For Centage Journal Type Acc Head Code '100101' Should be Added");
        return false;
        }
        }else if(cmbMas_SL_type==59)
        {
        if(flag==2) {
        return true;
        }else{
        alert("For Interest Adjustment Journal Type Acc Head Code '120201' Should be Added");
        return false;
        }
        }
        else if(cmbMas_SL_type==54)
        {
        if(flag==3) {
        return true;
       
        }
        else{
      //  alert("flag"+flag);
        alert("For Payroll Journal Type one of Acc Head Codes ('550310','792141','792041',390602,212301) Should be Added");
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
                   /*  if(flag==7)
                    {
                     return true;
                    }
                    else
                    {
                      alert("Atleast One Credit Head Should have Adjustment Year and Month");
                    return false;
                   
                    }   */
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
        else if( (cmbMas_SL_type==95) && (flagg95==0))
        {
      
        	 alert("You Should Enter Atleast one OPR Bank Head in Debit For "+acchdcd1);
             return false;
        }
        else if( (cmbMas_SL_type==98) && (flagg98==0))
        {
      
        	 alert("You Should Enter Atleast one OPR Bank Head in Credit For "+acchdcd1);
             return false;
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
        
//         else if(cmbMas_SL_type==91)
//        {
//        if(flag==7)
//        {
//         return true;
//        }
//        else
//        {
//          alert("Atleast One Credit Head Should have Adjustment Year and Month");
//        return false;
//       
//        }
//        }
        
       
}
return true;
}


function enable_cheque(Jr_type)
{
document.getElementById("txtAcc_HeadCode").value="";
document.getElementById("txtAcc_HeadDesc").value="";
    if(Jr_type==56 || Jr_type==57)
        document.getElementById("CHD").style.display='block';
    else
    {
        
        document.getElementById("txtCheque_NO").value="";
        document.getElementById("txtCheque_date").value="";
        document.getElementById("CHD").style.display='none';
    }
}



function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     // ///////Calender
															// control return
															// value handling
{
    if(blr_flag==1)             // which is used to find the receipt or voucher
								// or payment (creation) date calling field,if
								// so check trial balance
    {
             call_clr();
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
             var TB_date=fromcal_dateCtrl.value;
             // alert(fromcal_dateCtrl.value+"b4url")
             if(fromcal_dateCtrl.value.length!=0)
             {
                 var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                        // alert(url);
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
                 //doFunction('load_Receipt_No','null');                 // return
																			// true;
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");// return false;//
                    dateCtrl.focus();
                    document.getElementById("txtCrea_date").value="";
               }
             else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");// return false;//
                    dateCtrl.focus();
                    document.getElementById("txtCrea_date").value="";     
               }
        }
    }
}




function limit_amt_journal(field,e)
{

      var Journal_Creation_date=document.getElementById("txtCrea_date").value.split("/");;
        // alert(Journal_Creation_date[1]);
    
      var unicode=e.charCode? e.charCode : e.keyCode;

      if(field.value.length<17)
      {
        if(field.value.length==14 && field.value.indexOf('.')==-1  )
        field.value=field.value+'.';
        if (unicode!=8 && unicode !=9  )
        {
                            
            if ( Journal_Creation_date[1] <=8 && Journal_Creation_date[2]<=2007 )     
            {
             
              if (unicode<45 || unicode==47 || unicode>57   )        // It
																		// allow
																		// the
																		// negative
																		// amount
                  return false  
             }
             else  
             {
             
              if (unicode<45 || unicode==47 || unicode>57   )       // It won't
																	// allow the
																	// negative
																	// amount
                return false
             }     
                  
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
                // alert('bentypeid::::'+bentypeid);
	    var url="../../../../../Journal_General_Create.view?Command=get&bentypeid="+bentypeid+"&cmbOffice_code="+cmbOffice_code;
	           // alert(url);
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
           // alert(url);
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
                            // alert('here second');
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
                	alert('Subledger Code Not Found*****');
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
	doFunction('checkCode','null');

}
// dhana changes
function doParentPendingbills(vno,vdate)
{
document.frmJournal_General.num.value=vno;
document.frmJournal_General.date1.value=vdate;

}

function doParentJournal()
{
alert("choose another Journal type");
document.getElementById("cmbMas_SL_type").value="";
 document.getElementById('yr_mon').style.display='none';
}

//end 
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
     // document.getElementById("txtReceipt_No").value="";
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
                 //doFunction('load_Receipt_No','null');                 // return
																			// true;
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");// return false;//
                    dateCtrl.focus();
                    // document.getElementById("txtReceipt_No").value="";
               }
             else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");// return false;//
                    dateCtrl.focus();
                   // document.getElementById("txtReceipt_No").value="";
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
			} else if (command == "fixAccHdCode") {
				fixAccHdCode1(baseResponse);
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
//dhana
function ClearGrid()
{
//alert("ffff");
var tbody=document.getElementById("grid_body");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }

//var tbody=document.getElementById("grid_body");
//if(tbody.rows.length>0)
//{
// tbody.deleteRow(0);
//}

}

function fixAccHdCode(path)
{       
var txtAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;

var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;
if((cmbMas_SL_type==82) || (cmbMas_SL_type==86) || (cmbMas_SL_type==95) || (cmbMas_SL_type==98)){
                	var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
	 var url=path + "/Journal_General_Create.view?Command=fixAccHdCode&txtAcc_UnitCode="+txtAcc_UnitCode;
	//alert(url);
	 var req=getTransport();
     req.open("GET",url,true); 
     req.onreadystatechange=function()
     {
    	 manipulate(req);
     }   
     req.send(null);
                }
}

function fixAccHdCode1(baseResponse) {
var items=new Array();
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
if (flag == "success") {
	
		var acc_Hd_Code = baseResponse
				.getElementsByTagName("acc_Hd_Code")[0].firstChild.nodeValue;		
		
                document.getElementById("acchdcd").value=acc_Hd_Code; 	
                document.getElementById("acchdcd1").value=acc_Hd_Code; 	
               
               var len4 = baseResponse.getElementsByTagName("acc_Hd_Code").length;	              
	for ( var i = 0; i < len4; i++) {
        var acc_Hd_Code = baseResponse
				.getElementsByTagName("acc_Hd_Code")[i].firstChild.nodeValue;	
        items[i]= acc_Hd_Code;                      
                                
        }
      //alert("items:::"+items);
       document.getElementById("acchdcd2").value=items; 	
	
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

function Acc_HeadCodeValidation()
{
document.frmJournal_General.rad_sub_CR_DR[0].checked = true;
document.frmJournal_General.rad_sub_CR_DR[1].disabled = false;
                 
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
    	
          if(spl[0]>01 || spl[0]==01)
          {
              var Acc_HeadCode=parseInt(document.getElementById("txtAcc_HeadCode").value);
              var Acc_HeadCode1=document.getElementById("txtAcc_HeadCode").value;
              var digit=parseInt(Acc_HeadCode1.substr(0, 2));   
              var digit1=parseInt(Acc_HeadCode1.substr(4, 6));   
             // alert("yyyy"+cmbMas_SL_type);
              if(cmbMas_SL_type!=54)              {
              if(cmbMas_SL_type!=58){
              
             // if( (Acc_HeadCode==901001)|| (Acc_HeadCode==901002)||  (Acc_HeadCode==610101)|| (Acc_HeadCode==792141)|| (Acc_HeadCode==792041)) 
               if((Acc_HeadCode==900108) || (Acc_HeadCode==900109) || (Acc_HeadCode==901001)|| (Acc_HeadCode==901002)||  (Acc_HeadCode==610101)|| (Acc_HeadCode==100101)|| (Acc_HeadCode==792141)|| (Acc_HeadCode==792041) || (Acc_HeadCode==900301) || (Acc_HeadCode==620101) || (Acc_HeadCode==390602) || (Acc_HeadCode==780401) ||(Acc_HeadCode==780402) ||(Acc_HeadCode==780403) ||(Acc_HeadCode==780405) ||(Acc_HeadCode==780406) || (Acc_HeadCode==160502)) 
              {
              if(cmbMas_SL_type!=88 && cmbMas_SL_type!=91 && cmbMas_SL_type!=100)
              	{
                  alert("Account Head Code  780401,780402,780403,780405,780406,160502,900108,900109,901001,901002,610101, 792141 ,792041,620101,100101,390602 and 900301 Not Allowed ");
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
                  }
                  
              }else if((Acc_HeadCode==120201) && (cmbMas_SL_type!=59))
              {
              alert("Account Head Code  780401,780402,780403,780405,780406,160502,900108,900109,901001,901002,610101, 792141 ,792041,620101,100101,120201 and 900301 Not Allowed ");
              document.getElementById("txtAcc_HeadCode").value="";
                 document.getElementById("txtAcc_HeadDesc").value="";
              document.getElementById("txtAcc_HeadCode").focus();
              }
              else if((digit==82) && (cmbMas_SL_type==59))
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
              document.frmJournal_General.rad_sub_CR_DR[0].checked = true;
                 document.frmJournal_General.rad_sub_CR_DR[1].disabled = true;
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
		             alert("Tis Account Head Code cannot be used here:::");
		              document.getElementById("txtAcc_HeadCode").value="";
		                 document.getElementById("txtAcc_HeadDesc").value="";
		              document.getElementById("txtAcc_HeadCode").focus();
		              
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
                 document.frmJournal_General.rad_sub_CR_DR[0].disabled = true;
                 document.frmJournal_General.rad_sub_CR_DR[1].checked = true;
                }
              
              }
              }
              
              }
                  else if(cmbMas_SL_type==58)
                  {
                   if((Acc_HeadCode==900108) || (Acc_HeadCode==900109) || (Acc_HeadCode==901001)|| (Acc_HeadCode==901002)||  (Acc_HeadCode==610101)|| (Acc_HeadCode==792141)|| (Acc_HeadCode==792041) || (Acc_HeadCode==900301) || (Acc_HeadCode==620101) || (Acc_HeadCode==120201) || (Acc_HeadCode==390602) || (Acc_HeadCode==780401) ||(Acc_HeadCode==780402) ||(Acc_HeadCode==780403) ||(Acc_HeadCode==780405) ||(Acc_HeadCode==780406) || (Acc_HeadCode==160502))  
                  {
                  alert("Account Head Code  780401,780402,780403,780405,780406,160502,900108,900109,901001,901002,610101, 792141 ,792041,620101,120201,390602 and 900301 Not Allowed ");
                  document.getElementById("txtAcc_HeadCode").value="";
                     document.getElementById("txtAcc_HeadDesc").value="";
                  document.getElementById("txtAcc_HeadCode").focus();
                  }else if((digit==82) && (cmbMas_SL_type==59))
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
                     document.frmJournal_General.rad_sub_CR_DR[0].disabled = true;
                     document.frmJournal_General.rad_sub_CR_DR[1].checked = true;
                    }
                                                     
                  }else
                  {             
                 alert("This Account Head Code cannot be used here???");
                  document.getElementById("txtAcc_HeadCode").value="";
                   document.getElementById("txtAcc_HeadDesc").value="";
                  document.getElementById("txtAcc_HeadCode").focus();
                  }
                  }
                  } 
              }

              else{    
            	  	if(Acc_HeadCode==620101 || Acc_HeadCode==900301)
            		  {
            		  var dat=document.getElementById("txtCrea_date").value;
            		  var t_date=dat.split("/");
            		  if(t_date[2]>2012)
            			  {
            			  alert("Account Head Code 900301,620101 Not Allowed ");
    		              document.getElementById("txtAcc_HeadCode").value="";
    		              document.getElementById("txtAcc_HeadCode").focus();
            			  }
            		  else if(t_date[2]==2012)
            			  {
            			  if(t_date[1]>05)
            				  {
            				  alert("Account Head Code 900301,620101 Not Allowed ");
        		              document.getElementById("txtAcc_HeadCode").value="";
        		              document.getElementById("txtAcc_HeadCode").focus();
            				  }
            			  }
            		  
            		  
            		  }
		              if((Acc_HeadCode==900108) || (Acc_HeadCode==900109) || (Acc_HeadCode==901002)|| (Acc_HeadCode==610101) || (Acc_HeadCode==100101) || (Acc_HeadCode==120201) || (Acc_HeadCode==780401) ||(Acc_HeadCode==780402) ||(Acc_HeadCode==780403) ||(Acc_HeadCode==780405) ||(Acc_HeadCode==780406) || (Acc_HeadCode==160502))
		              {
		              alert("Account Head Code  780401,780402,780403,780405,780406,160502,900108,900109,901002, 610101,100101 and 120201 Not Allowed ");
		              document.getElementById("txtAcc_HeadCode").value="";
		              document.getElementById("txtAcc_HeadCode").focus();
		              }else if(digit==82)
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
		                 document.frmJournal_General.rad_sub_CR_DR[0].disabled = true;
		                 document.frmJournal_General.rad_sub_CR_DR[1].checked = true;
		                }
		                                                 
		              }
		              }
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
                                        var sl1_type = document.getElementById("sl1_type").value;
                                         if(sl1_type==7)
                                         {
                                         ADD_GRID();
                                         }
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
                                        else
                                        {
                                       ADD_GRID();
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