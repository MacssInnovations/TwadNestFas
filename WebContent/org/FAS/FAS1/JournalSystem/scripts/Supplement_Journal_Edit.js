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
function Acc_HeadCodeValidation()
{
	
	var headcode=document.getElementById("txtAcc_HeadCode").value;
	//alert("headcode==>"+headcode);
	
	
	Jrnl_type=document.getElementById("cmbMas_SL_type").value;
	//alert("Jrnl_type===>"+Jrnl_type);
	
	var kk=headcode.charAt(0)+headcode.charAt(1)+headcode.charAt(2)+headcode.charAt(3)+headcode.charAt(4);
	//alert(kk);
	if(Jrnl_type==112)
		{
		//alert("After type checking...");
		
		if(document.frmJournal_General_Edit.rad_sub_CR_DR[0].checked==true)
		{
		//	alert("After indicator checking...");
			
			if(kk != "16050")
				{
				alert("This Account Headcode not Allowed!........");
				document.getElementById("txtAcc_HeadCode").value="";
                document.getElementById("txtAcc_HeadCode").focus();
				document.getElementById("txtAcc_HeadDesc").value="";
				return false;
				}
			
			
		}
		else if(document.frmJournal_General_Edit.rad_sub_CR_DR[1].checked==true)
			{
			if(kk != "78040")
				{
				alert("This Account Headcode not Allowed!........");
				document.getElementById("txtAcc_HeadCode").value="";
                document.getElementById("txtAcc_HeadCode").focus();
				document.getElementById("txtAcc_HeadDesc").value="";
				return false;
				}
			}
		else
		{
			//alert("Comes Else Part");
		doFunction('checkCode','null');
		}
		
		}
	
	
	
	var hc=document.getElementById("txtAcc_HeadCode").value;
	//lakshmi
	//(hc==820102) ||(hc==820103) ||
	if((hc==620101) ||(hc==610101) ||(hc==610102) || (hc==900301) || (hc==900108) || (hc==900109) || (hc==901001) || (hc==901002) )
	{
		alert("Account Head Codes like 620101,610101,610102,900301,900108,900109,901001,901002 Are Not Allowed ");
		document.getElementById("txtAcc_HeadCode").value="";
		return false;
	}else if((hc==390302) ||(hc==390303) || (hc==390305) || (hc==391002) ||(hc==391003) ||(hc==391302) || (hc==391303) ||(hc==391502) ||(hc==391503) )
    {			
   	  alert("GPF Account Head Code cannot be used here***");
         document.getElementById("txtAcc_HeadCode").value="";
         document.getElementById("txtAcc_HeadCode").focus();
         return false;
     }
	else
	{
		doFunction('checkCode','null');
	}
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
            var url="../../../../../Supplement_Journal_Edit.kv?Command=load_Voucher_No&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
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
            var url="../../../../../Supplement_Journal_Edit.kv?Command=load_Voucher_Details&txtJournalVou_No="+txtJournalVou_No+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
            cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
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
                doFunction('checkCode','null');
                doFunction('Load_SL_Code',common_cmbSL_type);
                
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
       
    document.frmJournal_General_Edit.cmdupdate.style.display='block';
    document.frmJournal_General_Edit.cmddelete.disabled=false;
    document.frmJournal_General_Edit.cmdadd.style.display='none';
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
       
    /*   if ( isMan.account_head_status) 
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
        
       // items[7]=document.getElementById("txtsub_Recei_from").value;
        items[7]=document.getElementById("txtBill_NO").value;
        items[8]=document.getElementById("txtBill_date").value;
        items[9]=document.getElementById("txtBill_type").value;
        items[10]=document.getElementById("txtAgree_No").value;
        items[11]=document.getElementById("txtAgree_Date").value;
       
       
        items[12]=document.getElementById("txtsub_Amount").value;
        items[13]=document.getElementById("txtParticular").value;
        
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
       if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
        {
          
                alert("Select a Sub-Ledger Type");
                return false;
      
          
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
        var check_amt=0;
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
		if(baseResponse.getElementsByTagName("cheq_No")[0].firstChild!=null)
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
        	//alert('test');
        items[0]=baseResponse.getElementsByTagName("AHcode")[k].firstChild.nodeValue; 
        //alert(items[0]);
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
        //alert( items[6]);
        if(items[6]=="null" ||items[6]=="" )
        items[6]="";
        
        if(baseResponse.getElementsByTagName("Bill_NO")[k].firstChild!=null){
         items[7]=baseResponse.getElementsByTagName("Bill_NO")[k].firstChild.nodeValue;
         }
        items[8]=baseResponse.getElementsByTagName("Bill_date")[k].firstChild.nodeValue;
        if(baseResponse.getElementsByTagName("Bill_type")[k].firstChild!=null){
        items[9]=baseResponse.getElementsByTagName("Bill_type")[k].firstChild.nodeValue;
        }
        if(baseResponse.getElementsByTagName("Agree_No")[k].firstChild!=null)
        {
        items[10]=baseResponse.getElementsByTagName("Agree_No")[k].firstChild.nodeValue;
        }
        items[11]=baseResponse.getElementsByTagName("Agree_date")[k].firstChild.nodeValue;
        
        items[12]=baseResponse.getElementsByTagName("sub_amount")[k].firstChild.nodeValue;
        items[13]=baseResponse.getElementsByTagName("sub_part")[k].firstChild.nodeValue;
        items[14]=baseResponse.getElementsByTagName("CB_REF_NO")[k].firstChild.nodeValue;// added on 25-jul-2018
       
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
        else
        	{
        	var cell=document.createElement("TD");
            var anc=document.createElement("A");
            var url="javascript:loadTable('"+mycurrent_row.id+"')";

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
                 var url="../../../../../Receipt_SL.view?Command=check_TB_Supp_Jrnl&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
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



function checkLiveSub()
{
var txtCrea_date=document.getElementById("txtCrea_date").value;
var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
var ssno=document.getElementById("supNo").value;
 var url="../../../../../Supplement_Journal_Create.kv?Command=solveSupNo&ssno="+ssno+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
     var req=getTransport();
       req.open("GET",url,true); 
       req.onreadystatechange=function()
       {
           supplementNumber(req);
       }   
       req.send(null);
}
 
function supplementNumber(req)
{
  if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
           
            if(flag=="supsuccess")
              {
                var supnumber=baseResponse.getElementsByTagName("supnumber");
             //   alert(supnumber);
                var supNo= document.getElementById("supNo").value;
               // document.forms[0].supNo.value="";
//               for(var i=0;i<supnumber.length;i++)
//               {alert("length");
                    var s1=supnumber[0].firstChild.nodeValue;
                   // alert("s1"+s1);
                    if(s1<supNo)
                    {
                    alert("choose supplement Number:"+s1);
                    document.forms[0].supNo.value="";
                    }
                    
             //  }
              }
              else  if(flag=="TBfailure")
              {
                var supnumber=baseResponse.getElementsByTagName("supnumber")[0].firstChild.nodeValue;
                alert(supnumber);
                document.forms[0].supNo.value="";
              }

       }
  }
}


function Check_Supplement_No()
{
      var txtCrea_date=document.getElementById("txtCrea_date").value;
     
       var url="../../../../../Supplement_Journal_Create.kv?Command=Check_Supplement_No&txtCrea_date="+txtCrea_date;
      // alert(url);
       var req=getTransport();
       req.open("GET",url,true); 
       req.onreadystatechange=function()
       {
           Check_Supplement_No_Response(req);
       }   
       req.send(null);
}

function Check_Supplement_No_Response(req)
{
  if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
           
//            if(Command=="get")
//            {
//                Load_SL_Code1(baseResponse);
//               
//            }
         if(flag=="failure")
              {
                var suppl_error=baseResponse.getElementsByTagName("suppl_error")[0].firstChild.nodeValue;
                alert(suppl_error);  
                //document.getElementById("txtCrea_date").value="";
              }
              else if(flag=="success")
              {
             
//              var supno=baseResponse.getElementsByTagName("supno")[0].firstChild.nodeValue;
           //   alert("supno"+supno);
              
                 var supNo1 = document.forms[0].supNo;
                 var supno = baseResponse.getElementsByTagName("supno"); 
                 for(var i=0; i<supno.length; i++)
                     {
                         var opt = document.createElement('option');
                         opt.value = supno[i].firstChild.nodeValue;
                         opt.innerHTML = supno[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                         supNo1.appendChild(opt);
                     }
              
              
              }

       }
  }
}

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
             //var url="../../../../../Rectificational_Journal?command=paymentreceipt_centage&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&adjyear="+adjyear+"&adjmonth="+adjmonth+"&txtAcc_HeadCode="+hh;
          var url="../../../../../Rectificational_Journal?command=paymentreceipt_centage&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&adjyear="+adjyear+"&adjmonth="+adjmonth+"&txtAcc_HeadCode="+hh;
         //alert(url);
          
             url=url+"&sid="+Math.random();
             
            var req = getTransport();
           	req.open("GET", url, true);
           	req.onreadystatechange = function() {
           		stateChanged(req);
        	};
           	req.send(null);
            
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
   	          //  xmlhttp=getxmlhttpObject();
	            var url="../../../../../Rectificational_Journal?command=paymentreceipt_Rectify&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&adjyear="+adjyear+"&adjmonth="+adjmonth+"&txtAcc_HeadCode="+txtAcc_HeadCode;
	             url=url+"&sid="+Math.random();
	             var req = getTransport();
	            	req.open("GET", url, true);
	            	req.onreadystatechange = function() {
	            		stateChanged(req);
	         	};
	            	req.send(null);
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
	//xmlhttp=getxmlhttpObject();
	 var url="../../../../../Rectificational_Journal?command=paymentreceipt&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&adjyear="+adjyear+"&adjmonth="+adjmonth+"&doctype="+doctype+"&txtAcc_HeadCode="+txtAcc_HeadCode;
	 //var url="../../../../../Rectificational_Journal?command=paymentreceipt&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&adjyear="+adjyear+"&adjmonth="+adjmonth+"&doctype="+doctype+"&txtAcc_HeadCode="+txtAcc_HeadCode;
     //alert(url);
	 url=url+"&sid="+Math.random();
	 var req = getTransport();
    	req.open("GET", url, true);
    	req.onreadystatechange = function() {
    		stateChanged(req);
 	};
    	req.send(null);
     }
	
}


function stateChanged(req)
{
    var flag,command,response;
   
    if(req.readyState==4)
    {
    	
       if(req.status==200)
       {
            response=req.responseXML.getElementsByTagName("response")[0];
          
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            alert(flag);
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
                                alert('minor'+items_desc[i]);
                         }
                     clear_Combo(billno);
                    
                          alert('here second');
                          for(var k=0;k<len;k++)
                          {   
                          	alert(items_name[k]);
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
        
	//xmlhttp=getxmlhttpObject();
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
	 var req = getTransport();
    	req.open("GET", url, true);
    	req.onreadystatechange = function() {
    		stateChanged(req);
 	};
    	req.send(null);
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
