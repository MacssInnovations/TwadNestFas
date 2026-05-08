var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;
var job_flag;
var common_AHead_code_flag="";
var emp_flag;
//-------------------------------------------------------------------

function doFunction_voucher(Command,param)
{   
		if(document.getElementById("txtCrea_date").value=="")
		{
				alert("Select Journal Date");
				return false;
		}
		if(document.getElementById("txtJournalVou_No").value!="")
				document.getElementById("linkId").style.visibility="visible";
		else
				document.getElementById("linkId").style.visibility="hidden";
		
        if(Command=="load_Voucher_No")
        { 
				clearGeneral_Detail();
				var txtCrea_date= document.frmJournal_Imprest_Edit.txtCrea_date.value;
				var  cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
				var cmbOffice_code=document.getElementById("cmbOffice_code").value;
				var cmbMas_SL_type= document.getElementById("cmbMas_SL_type").value;
			//	var text1= document.getElementById("cmbMas_SL_type").options[document.getElementById("cmbMas_SL_type").selectedIndex].text;
			
				if(txtCrea_date.length!=0)
				{
						var url="../../../../../Imprest_Journal_Edit?Command=load_Voucher_No&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
						cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtMode_of_creat="+cmbMas_SL_type;
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
				var txtCrea_date= document.frmJournal_Imprest_Edit.txtCrea_date.value;
				var  txtJournalVou_No=document.getElementById("txtJournalVou_No").value;
				var cmbMas_SL_type= document.getElementById("cmbMas_SL_type").value;
				var text1= document.getElementById("cmbMas_SL_type").options[document.getElementById("cmbMas_SL_type").selectedIndex].text;
			
				if(txtJournalVou_No!="")
				{
						var url="../../../../../Imprest_Journal_Edit?Command=load_Voucher_Details&txtJournalVou_No="+txtJournalVou_No+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
						cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtMode_of_creat="+cmbMas_SL_type+"&text1="+text1;
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

/////////////////////////////////////////////Load Account Head Based on the Journal Type Selection /////////////////////

function loadAccountHead()
{
		 var tbody=document.getElementById("grid_body");		    
		 for(t=tbody.rows.length-1;t>=0;t--)
		 {
		       tbody.deleteRow(0);
		 }
		 
		 var txtMode_of_creat=document.getElementById("cmbMas_SL_type").value;		 
		 if(txtMode_of_creat==68)		
			   document.getElementById("txtAcc_HeadCode").value=820103;			 
		 else		
			   document.getElementById("txtAcc_HeadCode").value=820102;			 
		 doFunction('checkCode','null');
			 
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function clearGeneral_Detail()
{ 
   
		document.getElementById("txtCheque_NO").value="";
		document.getElementById("txtCheque_date").value="";
		document.getElementById("txtRemarks").value="";	
		document.getElementById("total_remaining_amt").value=0;
		var tbody=document.getElementById("grid_body");
		var t=0;
		for(t=tbody.rows.length-1;t>=0;t--)
		{
				tbody.deleteRow(0);
		}
}


function byUnitAndOfficeChange()
{
		doFunction_voucher('load_Voucher_No','null');
}

window.onunload=function()
{
		if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
		if (winemp && winemp.open && !winemp.closed) winemp.close();
		if (winjob && winjob.open && !winjob.closed) winjob.close();
		
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
		com_id=scod;                                    
	    clearall();
	    var r=document.getElementById(scod);
	    var rcells=r.cells;
	   
	    var cmbSL_type=document.getElementById("cmbSL_type");
	    
	    clear_Combo(cmbSL_type);   
	    try {document.getElementById("txtAcc_HeadCode").value=rcells.item(1).firstChild.value;}catch(e){}
	    doFunction('checkCode','null');
	
	    try{com_cmbSL_type=rcells.item(3).firstChild.value; } catch(e){com_cmbSL_type="";}
	    try{com_cmbSL_Code=rcells.item(4).firstChild.value;} catch(e){com_cmbSL_Code="";} 	
	    setTimeout('document.getElementById("cmbSL_type").value=com_cmbSL_type',500); 
	    //loadSLCodeText(com_cmbSL_Code);
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
	   
	    if((document.getElementById("txtAcc_HeadCode").value==820103 || document.getElementById("txtAcc_HeadCode").value==820102)&& com_cmbSL_type==7)   
	    {        	  
	    	 loadSLType(com_cmbSL_Code,com_cmbSL_type);
	    }
	    else
	    	  doFunction('Load_SL_Code',com_cmbSL_type);
	            
	    if(rcells.item(2).firstChild.value=="CR")
	    	 document.frmJournal_Imprest_Edit.rad_sub_CR_DR[0].checked=true;
	    else if(rcells.item(2).firstChild.value=="DR")
	    	 document.frmJournal_Imprest_Edit.rad_sub_CR_DR[1].checked=true;
	     
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
	   
	   document.frmJournal_Imprest_Edit.cmdupdate.style.display='block';
	   document.frmJournal_Imprest_Edit.cmddelete.disabled=false;
	   document.frmJournal_Imprest_Edit.cmdadd.style.display='none';
	   setTimeout('document.getElementById("cmbSL_Code").value=com_cmbSL_Code',900); 
	   //document.getElementById("cmbSL_Code").value=com_cmbSL_Code;   
	 //  setTimeout('document.getElementById("cmbSL_type").value=com_cmbSL_type',900); 
}

function chkHeadcode()
{
	var tbody=document.getElementById("grid_body");
	
	var HeadCode=document.getElementById("txtAcc_HeadCode").value;
//	alert("HeadCode value==>"+HeadCode);
	
	for(var i=0;i<tbody.rows.length;i++)

	{
		//alert(tbody.rows.length);
//		alert(tbody.rows[i].cells[1].textContent);
		var headck=tbody.rows[i].cells[1].textContent;
		headck=headck.substring(0,headck.indexOf("-"));
//		alert("headck===>"+headck);
		if (HeadCode == headck )
			{
			alert("This HeadCode already present");
			document.frmJournal_Imprest.cmdadd.disabled=true;
			 document.frmJournal_Imprest.cmdupdate.disabled=true;
			 return false;

			}
		else
			{
			
			 document.frmJournal_Imprest.cmdadd.disabled=false;
			 document.frmJournal_Imprest.cmdupdate.disabled=false;
			}
		
		
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
	            return false;    
        }
        var tbody=document.getElementById("grid_body");                               
        var t=0;
        var exist=document.getElementById("txtAcc_HeadCode").value;     
        var items=new Array();
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;
        if(document.frmJournal_Imprest_Edit.rad_sub_CR_DR[0].checked==true)
        		items[2]=document.frmJournal_Imprest_Edit.rad_sub_CR_DR[0].value;
        else if(document.frmJournal_Imprest_Edit.rad_sub_CR_DR[1].checked==true)
        		items[2]=document.frmJournal_Imprest_Edit.rad_sub_CR_DR[1].value;
        
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
        
       
        items[7]=document.getElementById("txtBill_NO").value;
        items[8]=document.getElementById("txtBill_date").value;
        items[9]=document.getElementById("txtBill_type").value;
        items[10]=document.getElementById("txtAgree_No").value;
        items[11]=document.getElementById("txtAgree_Date").value;
       
       
        items[12]=document.getElementById("txtsub_Amount").value;
        items[13]=document.getElementById("txtParticular").value;
                               
        tbody=document.getElementById("grid_body");
        var mycurrent_row=document.createElement("TR");
        seq=seq+1;
        mycurrent_row.id=seq;
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
		cell2.appendChild(Agree_No);
		        
		var Agree_date=document.createElement("input");
		Agree_date.type="hidden";
		Agree_date.name="Agree_date";
		Agree_date.value=items[11];
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
     
	    document.getElementById("txtEmpID_trs").value=""; 
	    document.frmJournal_Imprest_Edit.rad_sub_CR_DR[0].checked=true;
	    document.getElementById("cmbSL_type").value="";
	    document.getElementById("cmbSL_Code").value="";
 
	    document.getElementById("txtsub_Amount").value="";
	    document.getElementById("txtParticular").value="";
	             
	    document.getElementById("offlist_div_trans").style.display='none';       
        var cmbSL_Code=document.getElementById("cmbSL_Code");   
        clear_Combo(cmbSL_Code);   
	
        document.frmJournal_Imprest_Edit.cmdadd.style.display='block';
        document.frmJournal_Imprest_Edit.cmdupdate.style.display='none';
        document.frmJournal_Imprest_Edit.cmddelete.disabled=true;
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
		        if(window.confirm("You have not selected Sub-Ledger Type \n Do you want to select it,click 'OK'?"))
		        {
			             if(document.getElementById("cmbSL_type").value=="")
			             {
				                alert("Select a Sub-Ledger Type");
				                return false;
			             } 
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
        if(document.frmJournal_Imprest_Edit.rad_sub_CR_DR[0].checked==true)
        		items[2]=document.frmJournal_Imprest_Edit.rad_sub_CR_DR[0].value;
        else if(document.frmJournal_Imprest_Edit.rad_sub_CR_DR[1].checked==true)
        		items[2]=document.frmJournal_Imprest_Edit.rad_sub_CR_DR[1].value;
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
	     
	   
	    document.frmJournal_Imprest_Edit.rad_sub_CR_DR[0].checked=true;
	    document.getElementById("cmbSL_type").value="";
	    document.getElementById("cmbSL_Code").value="";
		document.getElementById("txtEmpID_trs").value=""; 
	    document.getElementById("txtsub_Amount").value="";
	    document.getElementById("txtParticular").value="";
	               
	    document.getElementById("offlist_div_trans").style.display='none';
               
        var cmbSL_Code=document.getElementById("cmbSL_Code");   
        clear_Combo(cmbSL_Code);   

		document.frmJournal_Imprest_Edit.cmdadd.style.display='block';
		document.frmJournal_Imprest_Edit.cmdupdate.style.display='none';
		document.frmJournal_Imprest_Edit.cmddelete.disabled=true;
}

function call_clr()
{		
		document.getElementById("txtJournalVou_No").value="";  
		document.getElementById("cmbPayVocNo").value="";  
		document.getElementById("txtPaymentVoc_Date").value="";  
		clearGeneral_Detail();
}


function clrForm(param)
{
		 document.getElementById("txtCrea_date").value="";		 
		 if(param=="cancel")
		 {
			     if(window.confirm("Do you want to clear ALL fields ?"))
				 {
			    	 	
			    	 	call_clr();
				 }
		 }
		 else
			 	 call_clr();
}



/////////////////////////////////////////////   checkNull() by User /////////////////////////////////////////////////////
function callHeadCodeCP(parameter)
{
	var hCode=document.getElementById("txtAcc_HeadCode").value;
	 if((hCode==390302) ||(hCode==390303) || (hCode==390305) || (hCode==391002) ||(hCode==391003) ||(hCode==391302) || (hCode==391303) ||(hCode==391502) ||(hCode==391503) )
    {			
    	  alert("GPF Account Head Code cannot be used here***");
          document.getElementById("txtAcc_HeadCode").value="";
          document.getElementById("txtAcc_HeadCode").focus();
         // return false;
      }
	
	
}
function checkNull()
{
		
		var tbody=document.getElementById("grid_body");
		if(document.getElementById("cmbAcc_UnitCode").value=="")
		{
			    alert("Select the Account Unit code");
			    return false;    
		}
		if(document.getElementById("cmbOffice_code").value=="")
		{
			    alert("Select the Office Code");
			    return false;
		}
		if(document.getElementById("txtCrea_date").value.length==0)
		{
			    alert("Enter the Date of Creation");
			    return false;    
		}
		if(document.getElementById("txtJournalVou_No").value.length==0)
		{
			    alert("Select Voucher Number");
			    return false;
		}
		if(document.getElementById("cmbMas_SL_type").value=="")
		{
			  
			    alert("Select The Journal Type in General");
			    return false;
			  
		}
                if (document.getElementById("txtRemarks").value== "") {
		alert("Enter Remarks");
		return false;
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
			    return false; 
		}		
	    if(tbody.rows.length>0)
	    {
	        	var dr_check_amt=0;var cr_check_amt=0;var count=0;
	        	var counting=0;
	        	rows=tbody.getElementsByTagName("tr");
	        	for(i=0;i<rows.length;i++)
	        	{
		                var cells=rows[i].cells;                
		                if(cells.item(2).lastChild.nodeValue=='DR')
		                {
		                	  dr_check_amt=parseFloat(dr_check_amt) + parseFloat(cells.item(7).lastChild.nodeValue);
		                }  
		                else
		                {
		                	  cr_check_amt=parseFloat(cr_check_amt) + parseFloat(cells.item(7).lastChild.nodeValue);
		                	    
		                }
		                if(document.getElementById("cmbMas_SL_type").value==68)
	                	  {
		                	  if(cells.item(1).firstChild.value==820103)
		                		  counting++;
	                	  }
	                	  else
	                	  {
	                		  if(cells.item(1).firstChild.value==820102)
	                			  counting++;
	                	  }
	              
	        	}      
	        	if(dr_check_amt!=cr_check_amt)
	        	{
		            	alert("Total Amount of DR & CR should be equal");
		                return false; 
	        	}
	        	else
	        	{
		            	if(counting==0)
		            	{
			            		if(document.getElementById("cmbMas_SL_type").value==68)
		            				alert("Detail should have at least one Account Head 820103 ");
		            			else
			            			alert("Detail should have at least one Account Head 820102 ");
			            		return false;
		            	}
	        	}
	        	var total_remaining_amt=document.getElementById("total_remaining_amt").value;
	        	if(cr_check_amt>total_remaining_amt && total_remaining_amt>0)
	        	{
		        		alert("Amount should not exceed "+total_remaining_amt);
		        		return false;
	        	}
	            
	    }
	    return true;
}


//function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
//{
//    
//	    if(blr_flag==1)             // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
//	    {
//	            call_clr();
//	            doFunction_voucher('load_Voucher_No','null');
//	    }
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
	    if(flag=="success")
	    {
			    var cheq_No=baseResponse.getElementsByTagName("cheq_No")[0].firstChild.nodeValue;         // here i assigned 
			    var cheq_Date=baseResponse.getElementsByTagName("cheq_Date")[0].firstChild.nodeValue;
			    var Remak=baseResponse.getElementsByTagName("Remak")[0].firstChild.nodeValue;			      
			    var Mas_SL_type=baseResponse.getElementsByTagName("Mas_SL_type")[0].firstChild.nodeValue;
			    var CB_Ref_No=baseResponse.getElementsByTagName("CB_Ref_No")[0].firstChild.nodeValue;
			    var CB_Ref_Date=baseResponse.getElementsByTagName("CB_Ref_Date")[0].firstChild.nodeValue;
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
			      
			    if(Remak!="null")
			            document.getElementById("txtRemarks").value=Remak;
			    else
			        	document.getElementById("txtRemarks").value="";
			    document.getElementById("cmbPayVocNo").value=CB_Ref_No;
			    document.getElementById("txtPaymentVoc_Date").value=CB_Ref_Date;
			    var tbody=document.getElementById("grid_body");
                var t=0;
                for(t=tbody.rows.length-1;t>=0;t--)
                {
                		tbody.deleteRow(0);
                }
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
			        
				        items[7]=baseResponse.getElementsByTagName("Bill_NO")[k].firstChild.nodeValue;
				        items[8]=baseResponse.getElementsByTagName("Bill_date")[k].firstChild.nodeValue;
				        items[9]=baseResponse.getElementsByTagName("Bill_type")[k].firstChild.nodeValue;
				        items[10]=baseResponse.getElementsByTagName("Agree_No")[k].firstChild.nodeValue;
				        items[11]=baseResponse.getElementsByTagName("Agree_date")[k].firstChild.nodeValue;
				        
				        items[12]=baseResponse.getElementsByTagName("sub_amount")[k].firstChild.nodeValue;
				        items[13]=baseResponse.getElementsByTagName("sub_part")[k].firstChild.nodeValue;
			       
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
			            cell2.appendChild(Agree_No);
			                    
			            var Agree_date=document.createElement("input");
			            Agree_date.type="hidden";
			            Agree_date.name="Agree_date";
			            Agree_date.value=items[11];
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

      var Journal_Creation_date=document.getElementById("txtCrea_date").value.split("/");;      
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
	     else
	    	 	return true;
  
}
/*
* Check Account Head Code and Bank Account Number 
*/

function account_head_code()
{
	    var acc_head=document.getElementById("txtAcc_HeadCode").value;
	    var url="../../../../../Cash_Receipt_Creation.view?Command=Acc_Head_Check&acc_head="+acc_head;
	    var req=getTransport();
	    req.open("GET",url,true); 
	    req.onreadystatechange=function()
	    {
	    		Account_Headcode_Check(req);
	    }   
	    req.send(null);
}

function Account_Headcode_Check(req)
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


function check_leng(param,val)
{	 
		if((val.length)>=190)
		{
			  if(param=='remarks')			  
				  	   alert("Please Enter Remarks below 200 characters");			           			  
			  else			  
				  	   alert("Please Enter Paticulars below 200 characters");				  	  
			  
		}
		
}




function checkVoucherNo()
{
	   if(document.getElementById("txtJournalVou_No").value=="")
	   {
		    	alert("Select Voucher Number");
		    
	   }
	   else
	   {
		   		var UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
		   		var office_code=document.getElementById("cmbOffice_code").value    
		   		var dt=document.getElementById("txtCrea_date").value.split("/"); 		   	
		   		var txtCB_Year=dt[2];   
		   		var txtCB_Month=dt[1];
		   		var txtVou_no= document.getElementById("txtJournalVou_No").value;
		   		
		   		var Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/JournalSystem/jsps/Journal_General_ListAll_SL.jsp?cmbAcc_UnitCode="+UnitCode+"&cmbOffice_code="+office_code+"&yr="+txtCB_Year+"&mon="+txtCB_Month+"&recNo="+txtVou_no,"VoucherList","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
		   	    Voucher_list_SL.moveTo(250,250);  
		   	    Voucher_list_SL.focus();
	   }
		
}


function loadSLType(SLCode,SLType)
{
	
		var UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
		var office_code=document.getElementById("cmbOffice_code").value;  
		var txtCrea_date=document.getElementById("txtCrea_date").value.split("/");   		
		var txtCB_Year=txtCrea_date[2];
		var txtCB_Month=txtCrea_date[1];
		var txtVou_no= document.getElementById("txtJournalVou_No").value;
		var ac_head_code=document.getElementById("txtAcc_HeadCode").value;
		var cmbMas_SL_type= document.getElementById("cmbMas_SL_type").value;
		var text1= document.getElementById("cmbMas_SL_type").options[document.getElementById("cmbMas_SL_type").selectedIndex].text;
		if((parseInt(ac_head_code)==820103 || parseInt(ac_head_code)==820102)&& SLType==7)
		{			
				if(txtVou_no=="")
				{
						alert("Select Voucher Number");
						document.getElementById("cmbSL_type").value="";
						return false;
				}
				
		   		var url="../../../../../Imprest_Journal_Edit?Command=loadSLType&cmbAcc_UnitCode="+UnitCode+"&cmbOffice_code="+office_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtJournalVou_No="+txtVou_no+"&txtMode_of_creat="+cmbMas_SL_type+"&text1="+text1;   		   		   		
		   		var req=getTransport();
		   		req.open("GET",url,true); 
		   		req.onreadystatechange=function()
		   		{
		   				loadProcess_Response(req,SLCode);
		   		}   
		   		req.send(null);
		}
		else
				doFunction('Load_SL_Code',SLType);
}
/*
function loadSLCodeText(sl_code)
{
		var cmbSL_Code=sl_code;
		document.getElementById("txtEmpID_trs").value=cmbSL_Code;	
		var ac_head_code=document.getElementById("txtAcc_HeadCode").value;
		if(parseInt(ac_head_code)==820103)
		{			
				var UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
				var office_code=document.getElementById("cmbOffice_code").value;
				var txtCrea_date=document.getElementById("txtCrea_date").value.split("/");   		
				var txtCB_Year=txtCrea_date[2];
				var txtCB_Month=txtCrea_date[1];				
				var txtVou_no= document.getElementById("txtJournalVou_No").value;
				var url="../../../../../Imprest_Journal_Edit?Command=loadPaymentTotal&cmbAcc_UnitCode="+UnitCode+"&cmbOffice_code="+office_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtJournalVou_No="+txtVou_no+"&cmbSL_Code="+cmbSL_Code;	   		
		   		var req=getTransport();
		   		req.open("GET",url,true); 
		   		req.onreadystatechange=function()
		   		{
		   				loadProcess_Response(req);
		   		}   
		   		req.send(null);
		}
}
*/
function loadProcess_Response(req,com_cmbSL_Code)
{
	    if(req.readyState==4)
	    {
		       if(req.status==200)
		       { 		    	   
		           var baseResponse=req.responseXML.getElementsByTagName("response")[0];
		           var tagcommand=baseResponse.getElementsByTagName("command")[0];
		           var Command=tagcommand.firstChild.nodeValue;
		           var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;		           
			       if(Command=="loadVoucher")
			       { 
				   	 		if(flag=="success")
				   	 		{
				   	 				 var txtBankAccountNo=document.getElementById("txtJournalVou_No");
					        	     var child=txtBankAccountNo.childNodes;
					        	     for(var i=child.length-1;i>1;i--)
					        	     {
					        	    	 
					        	    	 txtBankAccountNo.removeChild(child[i]);
					        	     } 
					        	     var count=baseResponse.getElementsByTagName("count");  
					                 var mode="";var acc_no="";var vouch_no="";			                
					                 for(var i=0;i<count.length;i++)
					                 {
					                	 vouch_no=baseResponse.getElementsByTagName("voucher_no")[i].firstChild.nodeValue;
					                     var opt=document.createElement("option");
					                     opt.setAttribute("value",vouch_no);
					                     var opttext=document.createTextNode(vouch_no);
					                     opt.appendChild(opttext);
					                     txtBankAccountNo.appendChild(opt);
					                 }
				   	 		}
				   	 		else
				   	 		{
				   	 				 alert("No Voucher Found");
				   	 		}
				   }
			       else if(Command=="loadSLType")
			       { 
				     
			    	    	if(flag=="success")
				   	 		{
			    	    		 
			    	    			 var cmbSL_Code=document.getElementById("cmbSL_Code");
					        	     var child=cmbSL_Code.childNodes;
					        	     for(var i=child.length-1;i>1;i--)
					        	     {
					        	    	 
					        	    	 cmbSL_Code.removeChild(child[i]);
					        	     } 
					        	       
					        	     var count=baseResponse.getElementsByTagName("SUB_LEDGER_CODE");  					        	     
					                 var sl_code="";var sl_desc="";var total_remaining_amt="";		                
					                 for(var i=0;i<count.length;i++)
					                 {
					                	 sl_code=baseResponse.getElementsByTagName("SUB_LEDGER_CODE")[i].firstChild.nodeValue;
					                	 sl_desc=baseResponse.getElementsByTagName("ENAME")[i].firstChild.nodeValue;
					                	 total_remaining_amt=baseResponse.getElementsByTagName("Balance")[i].firstChild.nodeValue;
					                	 document.getElementById("total_remaining_amt").value=total_remaining_amt;
					                     var opt=document.createElement("option");
					                     opt.setAttribute("value",sl_code);
					                     var opttext=document.createTextNode(sl_desc);
					                     opt.appendChild(opttext);
					                     cmbSL_Code.appendChild(opt);
					                 }					                 
					                 if(com_cmbSL_Code!=null)
					                	 document.getElementById("cmbSL_Code").value=com_cmbSL_Code;
					                 else					                 					                	
					                	 document.getElementById("cmbSL_Code").value="";					                 
				   	 		}
				   	 		else
				   	 		{
				   	 				 alert("No Sub Ledger Type Found");
				   	 		}
			       }
			       /*else if(Command=="loadPaymentTotal")
			       {
			    	        
				    	    if(flag=="success")
				   	 		{		    	    			 		    	    		
				                	 var pay_amt=baseResponse.getElementsByTagName("pay_amt")[0].firstChild.nodeValue;
				                	 var jour_amt=baseResponse.getElementsByTagName("jour_amt")[0].firstChild.nodeValue;
				                	 var rec_amt=baseResponse.getElementsByTagName("rec_amt")[0].firstChild.nodeValue;					                	 
				                	 var total_remaining_amt=pay_amt-rec_amt;
				                	 document.getElementById("total_remaining_amt").value=total_remaining_amt;
				   	 		}
				   	 		
			    	   
			       }*/
		           
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
