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

function loadTable(scod)
{
var tbody=document.getElementById("grid_body");

        com_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
        clearall();
        if(tbody.rows.length==1)
        {
        if(document.getElementById("cmbMas_SL_type").value==82)
         {
         document.getElementById("txtAcc_HeadCode").disabled=true;
         document.getElementById("txtAcc_HeadDesc").disabled=true;
         document.frmJournal_General.rad_sub_CR_DR[1].checked=true;
          document.frmJournal_General.rad_sub_CR_DR[0].checked=false;
         //  document.frmJournal_General.rad_sub_CR_DR[1].disabled=true;
          document.frmJournal_General.rad_sub_CR_DR[0].disabled=true;
         
         }
         else
         {
         document.frmJournal_General.rad_sub_CR_DR[1].disabled=false;
          document.frmJournal_General.rad_sub_CR_DR[0].disabled=false;
          document.getElementById("txtAcc_HeadCode").disabled=false;
            document.getElementById("txtAcc_HeadDesc").disabled=false;
         }
        }
        else
        {
         document.frmJournal_General.rad_sub_CR_DR[1].disabled=false;
          document.frmJournal_General.rad_sub_CR_DR[0].disabled=false;
         document.getElementById("txtAcc_HeadCode").disabled=false;
            document.getElementById("txtAcc_HeadDesc").disabled=false;
        }
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
              //  doFunction('checkCode','null');
         doFunctionBLOCK('checkCode','null');
                doFunction('Load_SL_Code',common_cmbSL_type);
                
         if(rcells.item(2).firstChild.value=="CR")
         document.frmJournal_General.rad_sub_CR_DR[0].checked=true;
         else if(rcells.item(2).firstChild.value=="DR")
         document.frmJournal_General.rad_sub_CR_DR[1].checked=true;
         
      //try{document.getElementById("cmbSL_Code").value=rcells.item(4).firstChild.value;}catch(e){}
       
       //try{document.getElementById("txtsub_Recei_from").value=rcells.item(5).firstChild.value;}catch(e){}
      try{document.getElementById("txtBill_NO").value=rcells.item(5).firstChild.value;}catch(e){}
       try{document.getElementById("txtBill_date").value=rcells.item(6).firstChild.value;}catch(e){}
       
        try{document.getElementById("txtBill_type").value=rcells.item(7).firstChild.value;}catch(e){}
       
        var nex=rcells.item(7).firstChild.nextSibling;  
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
        try{document.getElementById("paymentreceipt").value=rcells.item(10).firstChild.value;}catch(e){}
       
        setTimeout("payreceipt()", 50); 
      //  payreceipt();
       
        setTimeout("callone('"+scod+"')", 450); 
        }else{
        	 try{document.getElementById("paymentreceipt1").value=rcells.item(10).firstChild.value;}catch(e){}
        	 try{document.getElementById("receiptno1").value=rcells.item(11).firstChild.value;}catch(e){} 
        }
        
    document.frmJournal_General.cmdupdate.style.display='block';
    document.frmJournal_General.cmddelete.disabled=false;
    document.frmJournal_General.cmdadd.style.display='none';
}

function fordcb(val)
{
	if(document.getElementById("cmbSL_type").value==14)
	{
		document.getElementById("benifici").style.display='block';
	
	
	}else{
		document.getElementById("benifici").style.display='none';
		//doFunction('Load_SL_Code',val);
                doFunction('Load_SL_Code',val)
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



function Acc_HeadCodeValidation()
{
	 var Acc_HeadCode=parseInt(document.getElementById("txtAcc_HeadCode").value);
	if((Acc_HeadCode==900108) || (Acc_HeadCode==900109) || (Acc_HeadCode==901001)|| (Acc_HeadCode==901002)) 
    {
		 alert("TDA/TCA A/C Heads are not Allowed");
		 document.getElementById("txtAcc_HeadCode").value="";
         document.getElementById("txtAcc_HeadDesc").value="";
         document.getElementById("txtAcc_HeadCode").focus();
    }else if((Acc_HeadCode==390302) ||(Acc_HeadCode==390303) || (Acc_HeadCode==390305) || (Acc_HeadCode==391002) ||(Acc_HeadCode==391003) ||(Acc_HeadCode==391302) || (Acc_HeadCode==391303) ||(Acc_HeadCode==391502) ||(Acc_HeadCode==391503) )
 		
    {			
          	  alert("GPF Account Head Code cannot be used here***");
                document.getElementById("txtAcc_HeadCode").value="";
                document.getElementById("txtAcc_HeadDesc").value="";
                document.getElementById("txtAcc_HeadCode").focus();
    }
	
	if((Acc_HeadCode==610101) || (Acc_HeadCode==610102) || (Acc_HeadCode==900301)|| (Acc_HeadCode==620101)) //Added on 05-01-2018 (after KT discussion changes)
    {
		 alert("610101,610102,900301,620101 HeadCodes Are Not Allowed");
		 document.getElementById("txtAcc_HeadCode").value="";
         document.getElementById("txtAcc_HeadDesc").value="";
         document.getElementById("txtAcc_HeadCode").focus();
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
            //alert(flag);
            if(command=="get")
            {
            	            	
                if(flag=='success')
                {
                	try{
                		
                		  var len=response.getElementsByTagName("beneficiarysno").length;
                		 
                		 var cmb_SL_Code=document.getElementById("cmbSL_Code");
                		 
                		// alert("cmb_SL_Code"+cmb_SL_Code);
                         
                         var items_id=new Array();
                         var items_name=new Array();
                       
                            for(var i=0;i<len;i++)
                            {
                          	 
                          	items_id[i]=response.getElementsByTagName("beneficiarysno")[i].firstChild.nodeValue;
                           
                          	items_name[i]=response.getElementsByTagName("beneficiaryname")[i].firstChild.nodeValue;
                            
                            }
                            
                                   
                          clear_Combo(cmb_SL_Code);
                          // clear_Combo1(cmb_SL_Code);
                         
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

function callone(scod)
{
	
	 var r=document.getElementById(scod);
     var rcells=r.cells;
     try{document.getElementById("receiptno").value=rcells.item(11).firstChild.value; }catch(e){}
}

//Lakshmi

function Mas_SL_Type()
{
	//alert(document.getElementById("cmbMas_SL_type").value);
	
	
	if(document.getElementById("cmbMas_SL_type").value==113)
    {
    	var paymentreceipt=document.getElementById("paymentreceipt"); 
    	paymentreceipt.innerHTML=""; 
        var option=document.createElement("OPTION");
        option.text="--Select Type--";
        option.value="";
        try
        {
        	paymentreceipt.add(option);
        }catch(errorObject)
        {
        	paymentreceipt.add(option,null);
        }
        var option=document.createElement("OPTION");
        option.text="Journal";
        option.value="J";
    	try
        {
        	paymentreceipt.add(option);
        }catch(errorObject)
        {
        	paymentreceipt.add(option,null);
        }	
    	
    }
	else
		{
		var paymentreceipt=document.getElementById("paymentreceipt"); 
    	paymentreceipt.innerHTML=""; 
        var option=document.createElement("OPTION");
        option.text="--Select Type--";
        option.value="";
        try
        {
        	paymentreceipt.add(option);
        }catch(errorObject)
        {
        	paymentreceipt.add(option,null);
        }
        var option=document.createElement("OPTION");
        option.text="Receipt";
        option.value="R";
        document.getElementById("paymentreceipt").options.add(option);
        
        var option=document.createElement("OPTION");
        option.text="Payment";
        option.value="P";
        document.getElementById("paymentreceipt").options.add(option);
        
        var option=document.createElement("OPTION");
        option.text="Journal";
        option.value="J";
        document.getElementById("paymentreceipt").options.add(option);
        
        var option=document.createElement("OPTION");
        option.text="Fund Receipt";
        option.value="FR";
        document.getElementById("paymentreceipt").options.add(option);
        
        var option=document.createElement("OPTION");
        option.text="Fund Transfer";
        option.value="FT";
        document.getElementById("paymentreceipt").options.add(option);
        
        var option=document.createElement("OPTION");
        option.text="IBT";
        option.value="IBT";
        document.getElementById("paymentreceipt").options.add(option);
    	
		}

}



function setAdjYrMnth()

{
	//Lakshmi
   
    if(document.getElementById("cmbMas_SL_type").value!="" && (document.getElementById("cmbMas_SL_type").value==109))
    {
    	
    var datt=document.getElementById("txtCrea_date").value;
    	

    	var dd1 = datt.split('/');
    	//datt = dd1[1] + "/" + dd1[0] + "/" + dd1[2];
    				
    				document.getElementById("adjyear").value=dd1[2];
    				
    				document.getElementById("adjyear").readOnly = true;
    				document.getElementById("adjmonth").value=parseInt(dd1[1]);
    				
    				document.getElementById("adjmonth").disabled = true;
    				
    	
      
    }
    
    //alert(document.getElementById("cmbMas_SL_type").value);
    
   
    
    else{
    	document.getElementById("adjyear").value="";
    	document.getElementById("adjyear").readOnly = false;
    	document.getElementById("adjmonth").disabled = false;
		document.getElementById("adjmonth").value="";
    }
}
/////////////////////////////////////////////   ADD & UPDATE & DELETE /////////////////////////////////////////////////////

function ADD_GRID()
{

       
        
        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
        alert("Enter A/c Head Code");
        return false;
        }
         if(document.getElementById("txtAcc_HeadDesc").value=="")
           {
                alert("Please Wait Account Head is Loading .......................");            
                return false;        
           }
        
      if ( isMan.account_head_status) 
       {
        
        if(document.getElementById("cmbSL_type").value=="")
        {
            alert("Select  Sub Ledger Type")       ;
            document.getElementById("cmbSL_type").focus();
            return false;        
        }
        
        if(document.getElementById("cmbSL_Code").value=="")
        {
            alert("Select  Sub Ledger Code")       ;
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
                                alert("Select  Sub-Ledger Type");
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
                        alert("Select Sub Ledger Code")       ;
                        document.getElementById("cmbSL_Code").focus();
                        return false;
                    }
            }       
        }
       
        
       
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            //document.getElementById("txtAmount").focus();
            return false;    
        }
       
        var w = document.frmJournal_General.receiptno.selectedIndex;
        var selected_text = document.frmJournal_General.receiptno.options[w].text;
        
        var tbody=document.getElementById("grid_body");
        var t=0;
              
        var items=new Array();
         
          var exist=document.getElementById("txtAcc_HeadCode").value;
          items[0]=document.getElementById("txtAcc_HeadCode").value;
          items[1]=document.getElementById("txtAcc_HeadDesc").value;
       
       
        if(document.frmJournal_General.rad_sub_CR_DR[0].checked==true)
          items[2]=document.frmJournal_General.rad_sub_CR_DR[0].value;
        else if(document.frmJournal_General.rad_sub_CR_DR[1].checked==true)
          items[2]=document.frmJournal_General.rad_sub_CR_DR[1].value;
        
        items[3]=document.getElementById("cmbSL_type").value;
      //  alert("type"+items[3]);
        if(document.getElementById("cmbSL_type").value=="")
        {
        //items[4]="Not Available";
        items[4]="";//document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        //alert("code"+items[4]+"ff");
        }
        else
        items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        
        items[5]=document.getElementById("cmbSL_Code").value;
      //   alert("code:::"+items[5]);
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
     
     document.frmJournal_General.rad_sub_CR_DR[0].disabled=false;
     document.frmJournal_General.rad_sub_CR_DR[1].disabled=false;
     
    document.getElementById("txtAcc_HeadCode").value="";
    //document.getElementById("txtAcc_HeadCode").readOnly=false;
    document.getElementById("txtAcc_HeadDesc").value="";
    document.frmJournal_General.rad_sub_CR_DR[0].checked=true;
    document.getElementById("cmbSL_type").value="";
    document.getElementById("cmbSL_Code").value="";
    document.getElementById("txtBill_NO").value="";
    document.getElementById("txtBill_date").value="";
    document.getElementById("txtBill_type").value="";
  /*  //Lakshmi
   if(document.getElementById("cmbMas_SL_type").value==109){
	   
    document.getElementById("adjyear").value="";
    document.getElementById("adjmonth").value="";
    document.getElementById("adjyear").readOnly = true;
	document.getElementById("adjmonth").disabled = true;
   } */
    // joan Modfied on 16 mar 2015
   if(document.getElementById("cmbMas_SL_type").value==109){
	   
	    //document.getElementById("adjyear").value="";
	    //document.getElementById("adjmonth").value="";
	    document.getElementById("adjyear").readOnly = true;
		document.getElementById("adjmonth").disabled = true;
	   }else{
	   document.getElementById("adjyear").value="";
	    document.getElementById("adjmonth").value="";
   }
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

     document.frmJournal_General.cmdadd.style.display='block';
     document.frmJournal_General.cmdupdate.style.display='none';
     document.frmJournal_General.cmddelete.disabled=true;
     
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
       /* if(document.getElementById("txtsub_Recei_from").value.length==0)
        {
        alert("Enter the value in Received From Field");
        document.getElementById("txtsub_Recei_from").focus();
        return false;    
        }*/

        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            document.getElementById("txtsub_Amount").focus();
            return false;    
        }
        
       
        
    	var w = document.frmJournal_General.receiptno.selectedIndex;
        var selected_text = document.frmJournal_General.receiptno.options[w].text;
    	
        
        
      
        
        
       
        var items=new Array();
      
         var exist=document.getElementById("txtAcc_HeadCode").value;
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
        //items[10]=document.getElementById("txtAgree_No").value;
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
        //items[17]=selected_text;
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
var check=document.getElementById("grid_body");
if(check.rows.length==1)
{
alert("Since this is the Payment Head (OPR) to be Adjusted Please Add again if you Delete it");
}
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
     document.getElementById("txtAcc_HeadCode").disabled=false;
     document.getElementById("txtAcc_HeadDesc").disabled=false;
      document.frmJournal_General.rad_sub_CR_DR[1].disabled=false;
          document.frmJournal_General.rad_sub_CR_DR[0].disabled=false;
     
    document.getElementById("txtAcc_HeadCode").value="";
    //document.getElementById("txtAcc_HeadCode").readOnly=false;
    document.getElementById("txtAcc_HeadDesc").value="";
    document.frmJournal_General.rad_sub_CR_DR[0].checked=true;
    document.getElementById("cmbSL_type").value="";
    document.getElementById("cmbSL_Code").value="";
    //document.getElementById("txtsub_Recei_from").value="";
     document.getElementById("txtBill_NO").value="";
    document.getElementById("txtBill_date").value="";
    document.getElementById("txtBill_type").value="";
    document.getElementById("adjyear").value="";
    document.getElementById("adjmonth").value=1;
    
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

 document.frmJournal_General.cmdadd.style.display='block';
 document.frmJournal_General.cmdupdate.style.display='none';
 document.frmJournal_General.cmddelete.disabled=true;
 
 document.getElementById("since").style.display='block';	
	document.getElementById("prior").style.display='none';	
	document.getElementById("since2007").style.display='block';	
	document.getElementById("prior2007").style.display='none';	
	priorsince=1;
 
 
}
function call_clr()
{
   // document.getElementById("txtAmount").value="";
    document.getElementById("txtCheque_NO").value="";
    document.getElementById("txtCheque_date").value="";
    document.getElementById("txtRemarks").value="";
    
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
if (document.getElementById("txtRemarks").value== "") 
{
		alert("Enter Remarks");
		// document.getElementById("txtCrea_date").focus();
		return false;
}
/*if(document.getElementById("txtCash_Acc_code").value.length==0)
{
    alert("Enter the Cash A/c Code");
    //document.getElementById("txtCash_Acc_code").focus();
    return false;
}*/

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
if(document.getElementById("cmbMas_SL_type").value!="" && (document.getElementById("cmbMas_SL_type").value==72))
{
    if(document.getElementById("cashyear").value.length==0 || document.getElementById("cashmonth").value.length==0)
    {
    alert("Enter Both Adjusted Cash book Year and Adjusted Cash book Month");
    return false;
    }else 
    { v=new Date();
	 mn=v.getMonth();
	 mn=parseInt(mn)+1;
	 yr=v.getFullYear();
	
    	var cashyear=document.getElementById("cashyear").value;	
    	var cashmonth=document.getElementById("cashmonth").value;
    	
    	
    	if(cashyear.length<4)
    	{
    		 alert("Give Correct format(YYYY) of Adjusted Cash book Year");
 		    return false;
    	}
    	if(parseInt(cashmonth)>12 || parseInt(cashmonth)<1 )
    	{
    		 alert("Give Correct format(MM) of Adjusted Cash book Month");
 		    return false;
    	}
    	
    	if(parseInt(cashyear)>parseInt(yr))
    	{
    		 alert("Adjusted Cash book Year should not be greater than current year");
    		    return false;
    	}
    	 if(parseInt(cashyear)==parseInt(yr))
    	 {
    		 if(parseInt(cashmonth)>parseInt(mn))
    		 {
    			 alert("Adjusted Cash book month should not be greater than current month");
     		    return false; 
    		 }
    	 }
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
        var check_amt=0,month_flag=0,type_flag=0,year_flag=0,centage_head=0;
        var cr_amt=0;
        var db_amt=0;
        var flag=0;
        var fla=0;
        var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;
        rows=tbody.getElementsByTagName("tr");
        
        for(i=0;i<rows.length;i++)
        {
        	
        	/*//Lakshmi
            if(document.getElementById("cmbMas_SL_type").value==109)
             {
         	  // alert("inside cond follow calling");
                 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
                 xmlhttp=getxmlhttpObject();
                  var url="../../../../../Rectificational_Journal?command=chooseHead1&cmbAcc_UnitCode="+cmbAcc_UnitCode;
              //alert(url);
                  url=url+"&sid="+Math.random();
              xmlhttp.open("GET",url,true);
             // xmlhttp.onreadystatechange=stateChanged;
              xmlhttp.onreadystatechange=function()
              {
            	 // alert("rteyun ");
            	  var flag11,command11,response11;
            	   
            	   
	            response11=xmlhttp.responseXML.getElementsByTagName("response")[0];
	          
	            command11=response11.getElementsByTagName("command")[0].firstChild.nodeValue;
	            flag11=response11.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            //alert("dddd");
              //Lakshmi
              if(command11=="chooseHead1")
              {
            	//  alert("command "+command11);
              if(flag11=='success')
                   {
            	 // alert("inside success ");
                      // var count_code=0;
              		var hcode=response11.getElementsByTagName("hCode")[0].firstChild.nodeValue;
                     
                       // var headdd="";
                     
                        
                          for(var j=0; j<hcode.length; j++)
                             {
                            //  var tt=hcode[j].firstChild.nodeValue;
                            
                                  	 var ac_code11=cells.item(1).lastChild.nodeValue; 
                                     
                                       var cs=cells.item(1).lastChild.nodeValue.split("-");
                                     
                                       alert("out if "+cs[0]+"===== "+hcode);
                                           if(parseInt(cs[0])==parseInt(hcode))
                                           {
                                        	   alert("if "+cs[0]+"===== "+hcode);
                                          	// headdd++;
                                          	 fla=334;
                                           }
                              alert("fla value "+fla);    
                            
                   }
              } 

              };
              xmlhttp.send(null); 
             }*/
            var cells=rows[i].cells;
            //alert(cells.item(7).lastChild.nodeValue);
            if(cells.item(2).lastChild.nodeValue=='CR')
                 cr_amt=parseFloat(cr_amt) + parseFloat(cells.item(7).lastChild.nodeValue);
            else
            {
            	 var ac_code=cells.item(1).lastChild.nodeValue;
                 var code1= ac_code.split("-");
                 db_amt=parseFloat(db_amt) + parseFloat(cells.item(7).lastChild.nodeValue);
                 if(document.getElementById("cmbMas_SL_type").value==91)
                 {
                // alert(cells.item(1).lastChild.nodeValue);
                 var cs=cells.item(1).lastChild.nodeValue.split("-");
                     if(cs[0]==100101)
                     {
                     centage_head++;
                     }
                 }
                 else if( (code1[0]==222112) && (cmbMas_SL_type==104) )
                 {
                 flag=616;
                 }
            }
            
        }
       
    //setTimeout("checkNull()", 1000);
        if(cmbMas_SL_type==104)
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
        //Lakshmi
       /* else if(cmbMas_SL_type==109)
        {
        	//alert("100009");
        	//alert("fla"+fla);
        	if(fla==334)
        	{
        		//alert("if   --->ffla"+fla);
        		 return true;
        	}
        	else
        	{
        		//alert("flaelse"+fla);
        		 alert("For Journal for the omitted Bank Receipt Acc Head Code '820701' Should be Added ");
        	        return false;
        	}
        }*/
        if(parseFloat(cr_amt)!=parseFloat(db_amt))      // Either CR or DR must equal in total
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
if(document.getElementById("cmbMas_SL_type").value==91)
{
    if(centage_head==0)
    {
    alert("This Journal Type Should Have atleast One Centage Head(100101)Debit ");
    return false;
    }
}
return true;
}


function enable_cheque(Jr_type)
{

        document.getElementById("txtCheque_NO").value="";
        document.getElementById("txtCheque_date").value="";
        document.getElementById("CHD").style.display='none';
    
}

function testCent()
{
if(document.getElementById("cmbMas_SL_type").value==91)
{
    if(document.getElementById("txtAcc_HeadCode").value==100101)
    {
    document.frmJournal_General.rad_sub_CR_DR[1].checked=true;
    document.frmJournal_General.rad_sub_CR_DR[0].checked=false;
    
    document.frmJournal_General.rad_sub_CR_DR[1].disabled=false;
    document.frmJournal_General.rad_sub_CR_DR[0].disabled=true;
    }
    else
    {
    document.frmJournal_General.rad_sub_CR_DR[1].disabled=false;
    document.frmJournal_General.rad_sub_CR_DR[0].disabled=false;
    }

}
else
{
document.frmJournal_General.rad_sub_CR_DR[1].disabled=false;
document.frmJournal_General.rad_sub_CR_DR[0].disabled=false;
}
//doFunction('checkCode','null');
doFunctionBLOCK('checkCode','null');
}

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
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
                    document.getElementById("txtCrea_date").value="";
               }
             else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");//return false;//
                    dateCtrl.focus();
                    document.getElementById("txtCrea_date").value="";     
               }
            
            dateCheck(dateCtrl);  
        }
    }
}




function limit_amt_journal(field,e)
{

      var Journal_Creation_date=document.getElementById("txtCrea_date").value.split("/");;
        //  alert(Journal_Creation_date[1]);
    
      var unicode=e.charCode? e.charCode : e.keyCode;

      if(field.value.length<17)
      {
        if(field.value.length==14 && field.value.indexOf('.')==-1  )
        field.value=field.value+'.';
        if (unicode!=8 && unicode !=9  )
        {
                            
            if ( Journal_Creation_date[1] <=8 && Journal_Creation_date[2]<=2007 )     
            {
             
              if (unicode<45 || unicode==47 || unicode>57   )        // It  allow the negative amount
                  return false  
             }
             else  
             {
             
              if (unicode<45 || unicode==47 || unicode>57   )       // It won't allow the negative amount   
                return false
             }     
                  
        }
      }
      else   
      return false;  
}

function checkamount(field)
{
	 amt=field.value;
	    if(amt.indexOf(".")!=amt.lastIndexOf("."))
	    {
	        alert("Enter a Valid Amount");
	        field.value="";
	        field.focus();
	    }
	    
	    var GetDate=document.getElementById("txtCrea_date").value;
	    var mon = GetDate.substr(3,2);
	    var yr = GetDate.substr(6,4);
	   
	    
	   if ( mon == "03" && ( yr == "2009" || yr== "2008")  )  
	   {
	    
	   }
	   else
	   {  
	    if(amt < 0 ) 
	    {
	        alert("Negative Amount Not Allowed");
	        field.value="";
	        field.focus();    
	    }     
	    } 	
	   
	   if(document.getElementById("cmbMas_SL_type").value!="" && (document.getElementById("cmbMas_SL_type").value==72))
	   {
		   if(window.confirm("If this Amount is for more than one employee?,please give the break up by select sub-ledger type and sub-ledger code \n Do you want to select it,click 'OK'?"))
	          {
	             if(document.getElementById("cmbSL_type").value=="")
	              {
	                alert("Select a Sub-Ledger Type");
	                return false;
	               }
	             if(document.getElementById("cmbSL_Code").value=="")
	              {
	                alert("Select a Sub-Ledger Type");
	                return false;
	               } 
	             
	          }
		  
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
	
	if(document.getElementById("cmbMas_SL_type").value==91)
        {
        var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
        var paymentreceipt=document.getElementById("paymentreceipt").value;
            if(paymentreceipt=="R")
            {
            var tt=txtAcc_HeadCode.substr(0,5);
          //  alert(tt);
            var hh=tt+1;
          //  alert("hh::::::"+hh);
          var adjyear=document.getElementById("adjyear").value;
            var adjmonth=document.getElementById("adjmonth").value;
            var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
        //    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
          xmlhttp=getxmlhttpObject();
             //var url="../../../../../Rectificational_Journal?command=paymentreceipt_centage&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&adjyear="+adjyear+"&adjmonth="+adjmonth+"&txtAcc_HeadCode="+hh;
          var url="../../../../../Rectificational_Journal?command=paymentreceipt_centage&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&adjyear="+adjyear+"&adjmonth="+adjmonth+"&txtAcc_HeadCode="+hh;
         //alert(url);
             url=url+"&sid="+Math.random();
         xmlhttp.open("GET",url,true);
         xmlhttp.onreadystatechange=stateChanged;
         xmlhttp.send(null); 
            
            }
            else
            {
            alert("This Doc Type is not Allowed");
            document.getElementById("paymentreceipt").value="";
            }
        }
	if(document.getElementById("cmbMas_SL_type").value==113)
    {
		
		 var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
	        var paymentreceipt=document.getElementById("paymentreceipt").value;
	            if(paymentreceipt=="J")
	            {
	            
	            var adjyear=document.getElementById("adjyear").value;
	            var adjmonth=document.getElementById("adjmonth").value;
	            var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
   	            xmlhttp=getxmlhttpObject();
	            var url="../../../../../Rectificational_Journal?command=paymentreceipt_Rectify&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&adjyear="+adjyear+"&adjmonth="+adjmonth+"&txtAcc_HeadCode="+txtAcc_HeadCode;
	             url=url+"&sid="+Math.random();
	             xmlhttp.open("GET",url,true);
	             xmlhttp.onreadystatechange=stateChanged;
	             xmlhttp.send(null); 
	            }
	            else
	            {
	            alert("This Doc Type is not Allowed");
	            document.getElementById("paymentreceipt").value="";
	            }
		
		
    }
	
	
        else{
	var adjyear=document.getElementById("adjyear").value;
	var adjmonth=document.getElementById("adjmonth").value;
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var doctype=document.getElementById("paymentreceipt").value;
        var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
	xmlhttp=getxmlhttpObject();
	 var url="../../../../../Rectificational_Journal?command=paymentreceipt&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&adjyear="+adjyear+"&adjmonth="+adjmonth+"&doctype="+doctype+"&txtAcc_HeadCode="+txtAcc_HeadCode;
	 //var url="../../../../../Rectificational_Journal?command=paymentreceipt&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&adjyear="+adjyear+"&adjmonth="+adjmonth+"&doctype="+doctype+"&txtAcc_HeadCode="+txtAcc_HeadCode;
     //alert(url);
	 url=url+"&sid="+Math.random();
     xmlhttp.open("GET",url,true);
     xmlhttp.onreadystatechange=stateChanged;
     xmlhttp.send(null);  
     }
	
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
           // alert(flag);
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
                                 var cflag=response.getElementsByTagName("commonFlag")[0].firstChild.nodeValue;
                                 if(cflag=="yes")
                                 {
                                     var r1=response.getElementsByTagName("receiptno")[i].firstChild.nodeValue;  
                                     var r2=response.getElementsByTagName("slno")[i].firstChild.nodeValue;  
                                    items_desc[i]=r1+'-'+r2;  
                                 }
                                 else
                                 {
                                 items_desc[i]=response.getElementsByTagName("receiptno")[i].firstChild.nodeValue;
                                 }
                             //   alert('minor'+items_desc[i]);
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
                	var billno=document.getElementById("receiptno");
                	clear_Combo(billno);                 
                    }
                 }
            
            else if(command=="paymentreceipt_Rectify")
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
                                 var cflag=response.getElementsByTagName("commonFlag")[0].firstChild.nodeValue;
                                 if(cflag=="yes")
                                 {
                                     var r1=response.getElementsByTagName("receiptno")[i].firstChild.nodeValue;  
                                     var r2=response.getElementsByTagName("slno")[i].firstChild.nodeValue;  
                                    items_desc[i]=r1+'-'+r2;  
                                 }
                                 else
                                 {
                                 items_desc[i]=response.getElementsByTagName("receiptno")[i].firstChild.nodeValue;
                                 }
                             //   alert('minor'+items_desc[i]);
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
                	var billno=document.getElementById("receiptno");
                	clear_Combo(billno);                 
                    }
                 }
            
            
            
                 else if(command=="paymentreceipt_centage")
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
                                 var cflag=response.getElementsByTagName("commonFlag")[0].firstChild.nodeValue;
                               //  alert(cflag);
                                 if(cflag=="yes")
                                 {
                                     var r1=response.getElementsByTagName("receiptno")[i].firstChild.nodeValue;  
                                     var r2=response.getElementsByTagName("slno")[i].firstChild.nodeValue;  
                                    items_desc[i]=r1+'-'+r2;  
                                 }
                                 else
                                 {
                                 items_desc[i]=response.getElementsByTagName("receiptno")[i].firstChild.nodeValue;
                                 }
                             //   alert('minor'+items_desc[i]);
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
                 
                 }
            else if(command=="headcheck")
            { 
        //    alert("command"+flag);
            	 if(flag=='success')
                 {
            		if(addorcheck==1)
            		 ADD_GRID();
            		else if(addorcheck==2)
            			update_GRID();
                 }else if(flag=="rjvupdated")
                 {
                	 alert('Rectification Journal Voucher already Posted');
                   //      document.getElementById("receiptno").value="";
                	// return true;
                	 if(addorcheck==1)
                		 ADD_GRID();
                		else if(addorcheck==2)
                			update_GRID();
                 }
            	 else 
                 {
                	 alert('Account Head is not found in the Selected Voucher *');
                	 document.getElementById("receiptno").value="";
                	 return false;
                 }
            	
            }
          else if(command=="chooseHead")
            {
            if(flag=='success')
                 {
                     var count_code=0;
            		var hcode=response.getElementsByTagName("hCode");
                      var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
                        for(var i=0; i<hcode.length; i++)
                           {
                            var tt=hcode[i].firstChild.nodeValue;
                            if(tt==txtAcc_HeadCode)
                            {
                            count_code++;
                            }
                                  
                           }
                           if(count_code==0)
                           {
                           alert("The First Account Head Should be Operation Account(82--02)");
                           document.getElementById("txtAcc_HeadCode").value="";
                           document.getElementById("txtAcc_HeadDesc").value="";
                           return false;
                           }
                           else
                           {
                           if(document.getElementById("adjyear").value=="" || document.getElementById("adjmonth").value=="")
                           {
                           alert("Enter Adjustment Year and Month");
                           return false;
                           }
                           else
                           {
                           document.frmJournal_General.rad_sub_CR_DR[1].checked=true;
                           document.frmJournal_General.rad_sub_CR_DR[0].checked=false;
                            acheadcodecheck('1');
                           }
                           }
                 }
            } 
            //Lakshmi
          else if(command=="chooseHead1")
          {
          if(flag=='success')
               {
                   var count_code=0;
          		var hcode=response.getElementsByTagName("hCode");
                    var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
                    
                      for(var i=0; i<hcode.length; i++)
                         {
                          var tt=hcode[i].firstChild.nodeValue;
                          if(parseInt(tt)==parseInt(txtAcc_HeadCode))
                          {
                          count_code++;
                          }
                                
                         }
                         if(count_code==0)
                         {
                         alert("The First Account Head Should be  "+tt);
                         document.getElementById("txtAcc_HeadCode").value="";
                         document.getElementById("txtAcc_HeadDesc").value="";
                         return false;
                         }else{
                        	 document.frmJournal_General.rad_sub_CR_DR[1].checked=true;
                             document.frmJournal_General.rad_sub_CR_DR[0].checked=false;
                              acheadcodecheck('1');
                         }
                         
               }
          }
           
       }
    }
}

function clear_Combo(combo)
{
        //alert(combo.id)
        var cmbSL_Code=document.getElementById(combo.id);   
        cmbSL_Code.innerHTML="";
       var option=document.createElement("OPTION");
        option.text="--Select--";
        option.value="";
        try
        {
            cmbSL_Code.add(option);
        }catch(errorObject)
        {
            cmbSL_Code.add(option,null);
        } 
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
	
	clear();

}

function clear()
{
	
	document.getElementById("paymentreceipt").value="";
	document.getElementById("receiptno").value="";
}

var payreceipt;

function payreceiptdetails()
{
	
	//var w = document.frmJournal_General.receiptno.selectedIndex;
//    var selected_text = document.frmJournal_General.receiptno.options[w].text;
   // alert("ttttt");
    var selected_text = document.frmJournal_General.receiptno.value;
 	
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

function editTest()
{
 acheadcodecheck('2');
}

function testCode()
{
var tbody=document.getElementById("grid_body");
    if(tbody.rows.length>0)
    {
     acheadcodecheck('1');
    }
else{

        if(document.getElementById("cmbMas_SL_type").value==82)
        {
            var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            xmlhttp=getxmlhttpObject();
             var url="../../../../../Rectificational_Journal?command=chooseHead&cmbAcc_UnitCode="+cmbAcc_UnitCode;
        // alert(url);
             url=url+"&sid="+Math.random();
         xmlhttp.open("GET",url,true);
         xmlhttp.onreadystatechange=stateChanged;
         xmlhttp.send(null); 
        }
       //Lakshmi check at addrow
        else if(document.getElementById("cmbMas_SL_type").value==109)
        {
            var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            xmlhttp=getxmlhttpObject();
             var url="../../../../../Rectificational_Journal?command=chooseHead1&cmbAcc_UnitCode="+cmbAcc_UnitCode;
        // alert(url);
             url=url+"&sid="+Math.random();
         xmlhttp.open("GET",url,true);
         xmlhttp.onreadystatechange=stateChanged;
         xmlhttp.send(null); 
        }
        else
        {
        acheadcodecheck('1');
        }
    }
}

var addorcheck=0;

function acheadcodecheck(chec)
{

	if(chec==1)
	addorcheck=1;
	else if(chec==2)
		addorcheck=2;
	
	//var w = document.frmJournal_General.receiptno.selectedIndex;
  //  var selected_text = document.frmJournal_General.receiptno.options[w].text;
  var selected_text = document.frmJournal_General.receiptno.value;
	var adjyear=document.getElementById("adjyear").value;
	var adjmonth=document.getElementById("adjmonth").value;
	//Lakshmi
	if((adjyear!="" && document.getElementById("cmbMas_SL_type").value ==109)){
		if(addorcheck==1)
	   		 ADD_GRID();
	   		else if(addorcheck==2)
	   			update_GRID();
	}
	else if((adjyear!="" && document.getElementById("cmbMas_SL_type").value !=90)){
       //alert("inside if ");
       //alert("priorsince "+priorsince);
		if(priorsince!=2){
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
     var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var doctype=document.getElementById("paymentreceipt").value;
	var receiptno=selected_text;
	var headcode=document.getElementById("txtAcc_HeadCode").value;
        var cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;
        
	xmlhttp=getxmlhttpObject();
        if(cmbMas_SL_type==91)
        {
         var tt=headcode.substr(0,5);
          //  alert(tt);
            var hh=tt+1;
            headcode=hh;
        }
        //alert("headcode  "+headcode);
	 var url="../../../../../Rectificational_Journal?command=headcheck&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&adjyear="+adjyear+"&adjmonth="+adjmonth+"&doctype="+doctype+"&headcode="+headcode+"&receiptno="+receiptno+"";
     //alert(url);
	 url=url+"&sid="+Math.random();
     xmlhttp.open("GET",url,true);
     xmlhttp.onreadystatechange=stateChanged;
     xmlhttp.send(null); 
		}else{
			if(addorcheck==1)
		   		 ADD_GRID();
		   		else if(addorcheck==2)
		   			update_GRID();
		}
	}
	
	else{
		if(addorcheck==1)
   		 ADD_GRID();
   		else if(addorcheck==2)
   			update_GRID();
	}

}

function summa()
{
	 if(document.getElementById("adjyear").value.length==0)
     {
         alert("Enter Adjustment Year");
         //document.getElementById("txtAmount").focus();
         return false;    
     }
     if(priorsince==1){
     if(document.getElementById("paymentreceipt").value=="")
     {
         alert("Select Doc Type");
         //document.getElementById("txtAmount").focus();
         return false;    
     }
     if(document.getElementById("receiptno").value=="")
     {
         alert("Select Receipt/Voucher No");
         //document.getElementById("txtAmount").focus();
         return false;    
     }
     }else{
     	if(document.getElementById("paymentreceipt1").value=="")
         {
             alert("Select Doc Type");
             //document.getElementById("txtAmount").focus();
             return false;    
         }
         if(document.getElementById("receiptno1").value.length=="")
         {
             alert("Enter Adjustment Year");
             //document.getElementById("txtAmount").focus();
             return false;    
         }
     }
   	
}
function call_date(dateCtrl)                        // TB_checking 
{
   //alert("Inside Call_Date!!!!!!");
	
	call_clr();
    if(checkdt(dateCtrl))
    {
        //doFunction('check_TB',dateCtrl.value);
         var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
         var cmbOffice_code=document.getElementById("cmbOffice_code").value;
         var TB_date=dateCtrl.value;
       
         if(dateCtrl.value.length!=0)
         {
             var url="../../../../../Receipt_SL.view?Command=check_TB_Jrnl&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
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
                 //doFunction('load_Receipt_No','null');                 //return true;
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
