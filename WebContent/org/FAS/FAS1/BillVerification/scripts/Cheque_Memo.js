var winemp;
var winemp1;

var checkemp=0;

function servicepopup11()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,500);
       winemp.moveTo(250,250); 
       winemp.focus();
       return;
    }
    else
    {
        winemp=null;
    }
    // startwaiting(document.frmEmployee) ;   
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}


function empty_all() {
	
	var tbody=document.getElementById("grid_body");
	  try{tbody.innerHTML="";}
  catch(e) {tbody.innerText="";}
	 document.getElementById("chequeno").value="";	
	 document.getElementById("chequedate").value="";	
	
	 document.getElementById("txtAmount").value="";	
	 document.getElementById("particulars").value="";	
	
}

function testfutureDate()
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
     
     var ctdate=day+"/"+month+"/"+year;
     var vouchdate=document.cheque_memo.vochardate.value;
     var vouchdate_grid=vouchdate.split("/");
 	
 	 if(year<vouchdate_grid[2])
 	 {
 		 document.getElementById("vochardate").value="";
 		 return false;
 		
 	 }
 	 else if(year==vouchdate_grid[2])
 	 {
 		
    	 if(month<vouchdate_grid[1])
    	 {
	    	 document.getElementById("vochardate").value="";
	 		 return false;
    	 }
    	 else if(month==vouchdate_grid[1])
    	 {
    		// var day;
    		
    		 
    		 if(day<vouchdate_grid[0])
        	 {
    			
    			 document.getElementById("vochardate").value="";
    			 return false;
        	 } 
    	 }
 	 }
 
 	call_maindate();
}

function callBDate()
{
	  var vdate=document.cheque_memo.vochardate.value;
      document.cheque_memo.txtCheque_DD_date.value=vdate;
   
      var s_billinc=0;
   
     var tbody=document.getElementById("grid_body");
 	if(tbody.rows.length==0)
 	{
 	    alert("Please Choose Details Part");
 	  	    return false; 
 	}
 	else{
      rows=tbody.getElementsByTagName("tr");
  
      for(i=0;i<rows.length;i++)
      {
          var cells=rows[i].cells;
         
                 var billdate_grid=cells.item(10).firstChild.value;
              	
              	 var biisp_grid=billdate_grid.split("/");
              	
              	 var vochardate_one=document.getElementById("vochardate").value;
              	 
              	 var vochardate_two=vochardate_one.split("/");
              //	 alert(i);
            //  alert(" 1111   "+biisp_grid[2]+vochardate_two[2]);
            //  alert(" 2222   "+biisp_grid[1]+vochardate_two[1]);
            //  alert(" 3333   "+biisp_grid[0]+vochardate_two[0]);
              	  if(cells.item(0).lastChild.checked==true)  { 
              	 if(biisp_grid[2]>vochardate_two[2])
              	 {
              		 
              		 s_billinc++;
              		
              	 }
              	 else if(biisp_grid[2]==vochardate_two[2])
              	 {
              		 if(biisp_grid[1]>vochardate_two[1])
	                	 { 
	                		 s_billinc++;
	                	 }
	                	 else if(biisp_grid[1]==vochardate_two[1])
	                	 {
	                		// alert(vochardate_two[0]);
	                		 if(biisp_grid[0]>vochardate_two[0])
		                	 { 
		                		 s_billinc++;
		                	 } 
	                	 }
              	 }
      }
      }

      if(s_billinc>0)
      {
      	 alert("Voucher Date should be Greater than MTC Date/Approval Date");
  		 document.getElementById("vochardate").value="";
  		 document.cheque_memo.txtCheque_DD_date.value="";
  		 return false;
      }
 	}
}

function doParentEmp(emp)
{
	
document.cheque_memo.txtEmpId.value=emp;
call('load');
	

}


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
	    var txtModule_Type="MF031";
	    var cr_dr_indi="CR";
	    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	    var txtSub_Office_code=0;
	    winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi+"&txtSub_Office_code="+txtSub_Office_code,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
	    winAcc_Bank_No.moveTo(250,250);  
	    winAcc_Bank_No.focus();
}

function doParentAcc_NO(Acc_Head_Code,Bank_Acc_No,bankid,br_id,B_name)
{
   
        document.getElementById("txtCash_Acc_code").value=Acc_Head_Code;
        document.getElementById("txtBankAccountNo").value=Bank_Acc_No;
        document.getElementById("txtBankID").value=bankid;
        document.getElementById("txtBranchID").value=br_id;
        document.getElementById("txtBankName").value=B_name;
        
}



var winAccHeadCode;
var seq = 0;

var com_id;

function ADD_GRID() {
	
	var tbody = document.getElementById("grid_body");
	var t = 0;
	var items = new Array();
	
	var tbody2=document.getElementById("grid_body");
	var leng=tbody2.rows.length;
	var i;
	var flag=0;
	
	
	if(document.getElementById("billno").value=="")
	{
	alert('Please Select Bill Number');
	return false;
	}
	
	
	for(i=0;i<leng;i++)
	{
	billno=document.getElementById("grid_body").rows[i].cells.item(1).lastChild.nodevalue;

		if(billno==document.getElementById("billno").value)
		{
			alert("This Bill No " +billno +" Already Entered");
			flag=1;
			return false;
		}
	}
	
	
	if(document.getElementById("txtAcc_HeadCode").value=="")
	{
	alert('Please Enter Account Head Code');
	return false;
	}
	if(document.getElementById("dramount").value=="")
	{
	alert('Please Enter DR Amount');
	return false;
	}
	
	if(flag==0){
	items[0] = document.getElementById("billno").value;	
	items[1] = document.getElementById("billdate").value;
	items[2] = document.getElementById("passorderamount").value;
	items[3] = document.getElementById("chequeno").value+"-"+document.getElementById("chequedate").value;
	//items[4] = document.getElementById("chequedate").value;
	//items[4] = document.getElementById("chequeamount").value;	
	//items[6] = document.getElementById("cmbSL_type").value;	
	//items[7] = document.getElementById("cmbSL_Code").value;	
	
	  items[5]=document.getElementById("txtAcc_HeadCode").value;
      items[6]=document.getElementById("txtAcc_HeadDesc").value;
	
      items[7]=document.getElementById("cmbSL_type").value;
      
      if(document.getElementById("cmbSL_type").value=="")
      {
      //items[4]="Not Available";
      items[8]="";                //document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
      }
      else
      items[8]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
      
      items[9]=document.getElementById("cmbSL_Code").value;
      if(document.getElementById("cmbSL_Code").value=="")
      {
      items[10]="";                //"Not Available";
      }
      else
      items[10]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
      
      items[11] = document.getElementById("dramount").value;	
  	items[12] = document.getElementById("remarks").value; 
   
  	if(document.cheque_memo.Indi_CR_DR[0].checked == true)
	{
  		items[13]='CR';
	}else{
		items[13]='DR';
	}
  	
  	
  
	
  	
	
	tbody = document.getElementById("grid_body");
	var mycurrent_row = document.createElement("TR");
	seq = seq + 1;
	mycurrent_row.id = seq;
	var cell = document.createElement("TD");
//	var anc = document.createElement("A");
//	var url = "javascript:loadTable('" + mycurrent_row.id + "')";
//	anc.href = url;
	var txtedit = document.createTextNode("-");
	//anc.appendChild(txtedit);
	cell.appendChild(txtedit);
	mycurrent_row.appendChild(cell);
	var i = 0;
	var cell2;
	cell2 = document.createElement("TD");
	var billno=document.createElement("input");
	billno.type="hidden";
	billno.name="bill_no";
	//billno.id="invoice_no";
	billno.value=items[0];
    cell2.appendChild(billno);
	var currentText = document.createTextNode(items[0]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var billdate=document.createElement("input");
	billdate.type="hidden";
	billdate.name="bill_date";
	billdate.value=items[1];
    cell2.appendChild(billdate);
	var currentText = document.createTextNode(items[1]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var passamont=document.createElement("input");
	passamont.type="hidden";
	passamont.name="pass_amount";
	passamont.value=items[2];
    cell2.appendChild(passamont);
	var currentText = document.createTextNode(items[2]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	

	cell2 = document.createElement("TD");
	var headcode=document.createElement("input");
	headcode.type="hidden";
	headcode.name="head_code";
	headcode.value=items[5];
    cell2.appendChild(headcode);
	var currentText = document.createTextNode(items[5]+"-"+items[6]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var sltype=document.createElement("input");
	sltype.type="hidden";
	sltype.name="sl_type";
	sltype.value=items[7];
    cell2.appendChild(sltype);
	var currentText = document.createTextNode(items[8]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);
	
	cell2 = document.createElement("TD");
	var slcode=document.createElement("input");
	slcode.type="hidden";
	slcode.name="sl_code";
	slcode.value=items[9];
    cell2.appendChild(slcode);
	var currentText = document.createTextNode(items[10]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);
	
	cell2 = document.createElement("TD");
	var dramount=document.createElement("input");
	dramount.type="hidden";
	dramount.name="crdrindicator";
	dramount.value=items[13];
    cell2.appendChild(dramount);
	var currentText = document.createTextNode(items[13]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);
	
	
	cell2 = document.createElement("TD");
	var dramount=document.createElement("input");
	dramount.type="hidden";
	dramount.name="dr_amount";
	dramount.value=items[11];
    cell2.appendChild(dramount);
	var currentText = document.createTextNode(items[11]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);
	
		
	cell2 = document.createElement("TD");
	var remark=document.createElement("input");
	remark.type="hidden";
	remark.name="remarks12";
	remark.value=items[12];
    cell2.appendChild(remark);
	var currentText = document.createTextNode(items[12]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);
	
	cell2 = document.createElement("TD");
	var cheque_no_date=document.createElement("input");
	cheque_no_date.type="hidden";
	cheque_no_date.name="cheque_no_dates";
	cheque_no_date.value=items[3];
    cell2.appendChild(cheque_no_date);
	var currentText = document.createTextNode(items[3]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);
	
	/* cell2 = document.createElement("TD");
	var cheque_amt=document.createElement("input");
	cheque_amt.type="hidden";
	cheque_amt.name="cheque_amount";
	cheque_amt.value=items[4];
    cell2.appendChild(cheque_amt);
	var currentText = document.createTextNode(items[4]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);  */
	
	tbody.appendChild(mycurrent_row);
	clear();
	}
}


function loadTable(scod) {
	com_id = scod; // to identify in UPDATE_GRID ,which row loaded
	clear();
	scod=parseInt(scod)-1;
	var tbody = document.getElementById("grid_body");
	//var r = document.getElementById(scod);
	//var rcells = r.cells;
	//alert(document.cheque_memo.invoice_no[2].value);
	
	if(parseInt(tbody.rows.length)==1)
     {
	
		 try {document.getElementById("txtAcc_HeadCode").value=document.cheque_memo.head_code.value;}catch(e){}
	        try{common_cmbSL_type=document.cheque_memo.sl_type.value;} catch(e){common_cmbSL_type="";}
	        //alert("U"+common_cmbSL_type+"U")
	        try{common_cmbSL_Code=document.cheque_memo.sl_code.value;} catch(e){common_cmbSL_Code="";} 
	       // alert(document.cheque_memo.sl_type.value);
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
	           
	         document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].value=common_cmbSL_type;
	       
	 	     document.getElementById("billno").value=document.cheque_memo.bill_no.value;
			 document.getElementById("billdate").value=document.cheque_memo.bill_date.value;
			 document.getElementById("passorderamount").value=document.cheque_memo.pass_amount.value;
			 //alert(":::"+document.cheque_memo.cheque_no_dates.value);
			 var splchq=(document.cheque_memo.cheque_no_dates.value.split("-"));
			 //alert(splchq[0]+":::"+splchq[1]);
			 
			 document.getElementById("chequeno").value= splchq[0];
			 document.getElementById("chequedate").value= splchq[1];
			// document.getElementById("chequeamount").value= document.cheque_memo.cheque_amount.value;
			 document.getElementById("dramount").value=document.cheque_memo.dr_amount.value;	
			 document.getElementById("remarks").value= document.cheque_memo.remarks12.value;
			//alert(document.cheque_memo.crdrindicator.value);
			 if(document.cheque_memo.crdrindicator.value=="CR")
			{
				document.cheque_memo.Indi_CR_DR[0].checked='checked';
			}else{
				document.cheque_memo.Indi_CR_DR[1].checked='checked';
			}
			 
			 
     }else{
    	 
    	 try {document.getElementById("txtAcc_HeadCode").value=document.cheque_memo.head_code[scod].value;}catch(e){}
	        try{common_cmbSL_type=document.cheque_memo.sl_type[scod].value;} catch(e){common_cmbSL_type="";}
	        //alert("U"+common_cmbSL_type+"U")
	        try{common_cmbSL_Code=document.cheque_memo.sl_code[scod].value;} catch(e){common_cmbSL_Code="";} 
	        
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
    	 
    	 
    	     document.getElementById("billno").value=document.cheque_memo.bill_no[scod].value;
			 document.getElementById("billdate").value=document.cheque_memo.bill_date[scod].value;
			 document.getElementById("passorderamount").value=document.cheque_memo.pass_amount[scod].value;
			 
			 var splchq=(document.cheque_memo.cheque_no_dates.value.split("-"));
			// alert(splchq[0]+":::"+splchq[1]);
			 
			 document.getElementById("chequeno").value= splchq[0];
			 document.getElementById("chequedate").value= splchq[1];
			// document.getElementById("chequeamount").value= document.cheque_memo.cheque_amount.value;	
			// document.getElementById("sltype").value=document.cheque_memo.sl_type[scod].value;;	
			// document.getElementById("slcode").value=document.cheque_memo.sl_code[scod].value;;	
			 document.getElementById("dramount").value=document.cheque_memo.dr_amount[scod].value;	
			 document.getElementById("remarks").value= document.cheque_memo.remarks12[scod].value;
			 
			 if(document.cheque_memo.crdrindicator[scod].value=="CR")
				{
					document.cheque_memo.Indi_CR_DR[0].checked='checked';
				}else{
					document.cheque_memo.Indi_CR_DR[1].checked='checked';
				}
	
     }
	
	document.cheque_memo.cmdupdate.style.display = 'block';
	document.cheque_memo.cmddelete.disabled = false;
	document.cheque_memo.cmdadd.style.display = 'none';
}

function update_GRID()
{
	var tbody = document.getElementById("grid_body");
	var t = 0;
	var items = new Array();
	var com_id1=parseInt(com_id)-1;
		
	items[0] = document.getElementById("billno").value;	
	items[1] = document.getElementById("billdate").value;
	items[2] = document.getElementById("passorderamount").value;
	//if
	items[3] = document.getElementById("chequeno").value+"-"+document.getElementById("chequedate").value;	
	//items[4] = document.getElementById("chequeamount").value;
	
	 items[5]=document.getElementById("txtAcc_HeadCode").value;
     items[6]=document.getElementById("txtAcc_HeadDesc").value;
	
     items[7]=document.getElementById("cmbSL_type").value;
     if(document.getElementById("cmbSL_type").value=="")
     {
    alert("Select Subledger Type");
    return false;
     items[8]="";                //document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
     }
     else
     items[8]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
     
     items[9]=document.getElementById("cmbSL_Code").value;
     if(document.getElementById("cmbSL_Code").value=="")
     {
     items[10]="";                //"Not Available";
     }
     else
     items[10]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
     
     items[11] = document.getElementById("dramount").value;	
 	items[12] = document.getElementById("remarks").value; 
     
 	
	
	if(document.cheque_memo.Indi_CR_DR[0].checked == true)
	{
  		items[13]='CR';
	}else{
		items[13]='DR';
	}
	
	
	var r=document.getElementById(com_id);
	var rcells=r.cells;
       
	 if(tbody.rows.length==1)
     {
		 
		
		
		 document.cheque_memo.bill_no.value=items[0];
		 document.cheque_memo.bill_date.value=items[1];
		 document.cheque_memo.pass_amount.value=items[2];
		 
		 document.cheque_memo.cheque_no_dates.value=items[3];
	//	 document.cheque_memo.cheque_amount.value=items[4];
		
		 
		 document.cheque_memo.head_code.value=items[5];
		 document.cheque_memo.sl_type.value=items[7];
		 document.cheque_memo.sl_code.value=items[9];
		 document.cheque_memo.dr_amount.value=items[11];
		 document.cheque_memo.remarks12.value=items[12];
		 
		 document.cheque_memo.crdrindicator.value=items[13];
		 
     }else{
	 
		 
		 document.cheque_memo.bill_no[com_id1].value=items[0];
		 document.cheque_memo.bill_date[com_id1].value=items[1];
		 document.cheque_memo.pass_amount[com_id1].value=items[2];
		 document.cheque_memo.cheque_no_dates.value=items[3];
		// document.cheque_memo.cheque_amount.value=items[4];
		 document.cheque_memo.head_code[com_id1].value=items[5];
		 document.cheque_memo.sl_type[com_id1].value=items[7];
		 document.cheque_memo.sl_code[com_id1].value=items[9];
		 document.cheque_memo.dr_amount[com_id1].value=items[11];
		 document.cheque_memo.remarks12[com_id1].value=items[12];
		 document.cheque_memo.crdrindicator[com_id1].value=items[13];
    	 
     }
	
		try{rcells.item(1).lastChild.nodeValue=items[0];}catch(e){}
            
        try{rcells.item(2).lastChild.nodeValue=items[1];}catch(e){}
             
        try{rcells.item(3).lastChild.nodeValue=items[2];}catch(e){}
      
     
            
        try{rcells.item(4).lastChild.nodeValue=items[5]+"-"+items[6];}catch(e){}
        
        try{rcells.item(5).lastChild.nodeValue=items[8];}catch(e){}
        
        try{rcells.item(6).lastChild.nodeValue=items[10];}catch(e){}
        
        try{rcells.item(7).lastChild.nodeValue=items[13];}catch(e){}
        
        try{rcells.item(8).lastChild.nodeValue=items[11];}catch(e){}
        
        try{rcells.item(9).lastChild.nodeValue=items[12];}catch(e){}
        
        try{rcells.item(10).lastChild.nodeValue=items[3];}catch(e){}
        
        try{rcells.item(11).lastChild.nodeValue=items[4];}catch(e){}
      
      
 
      alert("Record Updated");
      clear();

}

function delete_GRID()
{
        if(confirm("Do you want to delete ?"))
        {
        var tbody=document.getElementById("mytable");
        var r=document.getElementById(com_id);
        var ri=r.rowIndex;
        tbody.deleteRow(ri);
        clear();
        }
}

function clear() {
	
	//alert("inside the clear");
	
	document.getElementById("billno").value="";	
	 document.getElementById("billdate").value="";	
	 document.getElementById("chequeno").value="";	
	 document.getElementById("chequedate").value="";	
	
	 
	// document.getElementById("voucherdatetrn").value="";	
	 document.getElementById("txtAcc_HeadCode").value="";	
	 document.getElementById("txtAcc_HeadDesc").value="";	
	 document.getElementById("cmbSL_type").value="";		
	document.getElementById("cmbSL_Code").value="";		
	 document.getElementById("dramount").value="";	
	 document.getElementById("remarks").value="";	
	 document.cheque_memo.Indi_CR_DR[1].checked='checked';
	 
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

	 
	 
	 
	
	document.cheque_memo.cmdupdate.style.display = 'none';
	document.cheque_memo.cmddelete.disabled = true;
	document.cheque_memo.cmdadd.style.display = 'block';
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

function new_year()
{
	 var tbody=document.getElementById("grid_body");
	 var t=0;
	 for(t=tbody.rows.length-1;t>=0;t--)
	 {
	 	  tbody.deleteRow(0);
	 }
   /*  document.getElementById("check_memo_Month").value="s";
     document.getElementById("vochardate").value="";
     document.getElementById("txtCash_Acc_code").value="";
     document.getElementById("txtBankAccountNo").value="";
     document.getElementById("txtBankName").value="";
     document.getElementById("payeetype").value="";
     //document.getElementById("txtEmpId").length=0;//payyecode
     document.getElementById("txtEmpId").value="";
     document.getElementById("txtCheque_DD_NO").value="";
  
     document.getElementById("txtCheque_DD_date").value="";
     document.getElementById("particulars").value="";
     */
    
	call('get');
}

function call(command)
{
	
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }  

 if(command=="get")
{ 
	
	 
	
	 
	 
	 
	 var tbody=document.getElementById("grid_body");
	  try{tbody.innerHTML="";}
    catch(e) {tbody.innerText="";}
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var check_memo_Year=document.getElementById("check_memo_Year").value;
	var check_memo_Month=document.getElementById("check_memo_Month").value;
	
	
	
	 var today= new Date(); 
     var day=today.getDate();
     var month=today.getMonth();
     month=month+1;
     
     if(day<=9 && day>=1)
     day="0"+day;
   //  if(month<=9 && month>=1)
     //month="0"+month;
     var year=today.getYear();
     if(year < 1900) year += 1900;
     var monthArray =new Array("January", "February", "March", 
               "April", "May", "June", "July", "August",
               "September", "October", "November", "December");
     
   //alert(month+"   "+check_memo_Month)
 	
 	 if(year<parseInt(check_memo_Year))
 	 {
 		 document.getElementById("check_memo_Year").value="";
 		 document.getElementById("check_memo_Year").focus();
 		 alert('Enter Correct Memo Year ..');
 		 return false;
 		
 	 }
 	 else if(year==parseInt(check_memo_Year))
 	 {
 		
    	 if(month<parseInt(check_memo_Month))
    	 {
	    	 document.getElementById("check_memo_Month").value="s";
	    	 document.getElementById("check_memo_Month").focus();
	    	 alert('Enter Correct Memo Month ..');
	 		 return false;
    	 }
	
 	 }	
	
	
        //alert("chequedate:::::::::::"+chequedate);
		
	  var url="../../../../../Cheque_Memo?command=get&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&check_memo_Month="+check_memo_Month+"&check_memo_Year="+check_memo_Year;
	 
	  url=url+"&sid="+Math.random();
      xmlhttp.open("GET",url,true);
      xmlhttp.onreadystatechange=stateChanged;
      xmlhttp.send(null);  
    
	
} 
 else if(command=="getdet")
 { 
	 var tbody=document.getElementById("grid_body");
	  try{tbody.innerHTML="";}
   catch(e) {tbody.innerText="";}
   
   document.getElementById("txtCheque_DD_NO").value="";	
	// document.getElementById("txtCheque_DD_date").value="";	
	
	 document.getElementById("txtAmount").value="";	
	 document.getElementById("particulars").value="";	
   
 	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var check_memo_Year=document.getElementById("check_memo_Year").value;
	var memotype=document.getElementById("memotype").value;
	var majortype=document.getElementById("majortype").value;
    var minortype=document.getElementById("minortype").value;
	var subtype=document.getElementById("subtype").value;
	
	if(check_memo_Year=="")
	{
		alert("Please Enter CashBook Year");
		return false;
	}
	var check_memo_Month=document.getElementById("check_memo_Month").value;
	if(check_memo_Month=="s")
	{
		alert("Please Enter CashBook Month");
		return false;
	}
 	var txtEmpId=document.getElementById("txtEmpId").value;
 		
 	  var url="../../../../../Cheque_Memo?command=getdet&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="
 	  +cmbOffice_code+"&check_memo_Year="+check_memo_Year+"&txtEmpId="+txtEmpId+"&check_memo_Month="+check_memo_Month+"&majortype="+majortype+"&minortype="+minortype+"&subtype="+subtype+"&memotype="+memotype;
 	 
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  
     
 	
 } 
 
 /*
 else if(command=="getdata")
 { 
 	
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
     var chequedate=document.getElementById("memodate").value;
	
     var billno=document.getElementById("billno").value;
     
  var url="../../../../../Cheque_Memo?command=getdata&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&chequedate="+chequedate+"&billno="+billno+"";
 
  url=url+"&sid="+Math.random();
  xmlhttp.open("GET",url,true);
  xmlhttp.onreadystatechange=stateChanged;
  xmlhttp.send(null);  
     
 	
 } */
 else if(command=="getcode")
 { 
	 var tbody=document.getElementById("grid_body");
	  try{tbody.innerHTML="";}
	  catch(e) {tbody.innerText="";}
	 if(document.getElementById("memotype").value=="")
		{
			alert('Please Select Payee Type');
			return false;
		}
		
		var memotype=document.getElementById("memotype").value;
		
 	  var url="../../../../../Cheque_Memo?command=getcode&memotype="+memotype+"";
 	 
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  
     
 	
 } 
 else if(command=="checkemp")
 { 
 	
	 if(document.getElementById("sanctionedby").value=="")
		{
			alert('Please Enter Sanctioned By');
			return false;
		}
		
		var empid=document.getElementById("sanctionedby").value;
		
 	  var url="../../../../../cheque_memo?command=checkemp&empid="+empid+"";
 	 
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  
     
       
 	
 } 
 else if(command=="headcode")
 { 
	 if(document.getElementById("headcode").value=="")
		{
			alert('Please Enter Account Head Code');
			return false;
		}
	 
		var hcode=document.getElementById("headcode").value;
 	  var url="../../../../../Cheque_Memo?command=headcode&hcode="+hcode+"";
 	 
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  
     
 	
 } 
 else if(command=="budget")
 { 
 	
	 if(document.getElementById("cmbAcHeadCode").value=="")
		{
			alert('Please Enter Account Head Code');
			return false;
		}
	 if(document.getElementById("prodate").value=="")
		{
			alert('Please EnterProceeding Date');
			return false;
		}
		var hcode=document.getElementById("cmbAcHeadCode").value;
		var unitid=document.getElementById("cmbAcc_UnitCode").value;
		var officeid=document.getElementById("cmbOffice_code").value;
		var prodate=document.getElementById("prodate").value;
		
		
		
 	  var url="../../../../../cheque_memo?command=budget&hcode="+hcode+"&unitid="+unitid+"&officeid="+officeid+"&prodate="+prodate+"";
 	 
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  
     
 	
 } 
 else if(command=="load")
 { 
 	
	 if(document.getElementById("txtEmpId").value=="")
		{
			alert('Please Enter Payee Code');
			return false;
		}
		
		var empid=document.getElementById("txtEmpId").value;
		
 	  var url="../../../../../cheque_memo?command=load&empid="+empid+"";
 	 
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  
     
 	
 } 
 
 
}

/*function totSum()
{
	
	  var memotype=document.getElementById("memotype").value;
	  
	  var YourselAmt=0;
	  alert('memotype >> '+memotype);
	  var len=document.getElementsByTagName("YourselfChk").length();

	  alert(len);
	  if(document.getElementById("memotype").value==2){
		for(var h=0;h<len;h++){
			
		}  
	  }
	  var memotype=document.getElementById("memotype").value;
	  
	 return false; 
	}*/

function stateChanged()
{
    var flag,command,response;
   
    if(xmlhttp.readyState==4)
    {
    	//alert("xmlhttp.readyState >> "+xmlhttp.readyState);
       if(xmlhttp.status==200)
       {//alert("xmlhttp.status >> "+xmlhttp.status);
            response=xmlhttp.responseXML.getElementsByTagName("response")[0];
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
          
            if(command=="get")
            {
            	
                if(flag=='success')
                {
            	
                	try{
                		//document.getElementById("txtAmount").value=response.getElementsByTagName("sanctionedamount")[0].firstChild.nodeValue;
              		  var len=response.getElementsByTagName("billno").length;
                 	var billno=document.getElementById("billno");
                 	var majortype=response.getElementsByTagName("majortype")[0].firstChild.nodeValue;
                 	var minortype=response.getElementsByTagName("minortype")[0].firstChild.nodeValue;
                 	var subtype=response.getElementsByTagName("subtype")[0].firstChild.nodeValue;
                 	document.getElementById("majortype").value=majortype;
                 	document.getElementById("minortype").value=minortype;
                 	document.getElementById("subtype").value=subtype;
                 	// change on 05/05/2014
                 	// *** var payeetypecombo = document.forms[0].payeetype;
                 	// *** payeetypecombo.length=0;
                  
                     for(var i=0; i<1; i++)
                         {
                             var opt = document.createElement('option');
                             opt.value = response.getElementsByTagName("typecode")[0].firstChild.nodeValue;
                             opt.innerHTML = response.getElementsByTagName("type_desc")[0].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                            // ***  payeetypecombo.appendChild(opt);
                         }
                  
                 	/// joe change on 30 Apr 2014
                 /*
	                 	 var txtEmpIdcombo = document.forms[0].txtEmpId;
	                 	 txtEmpIdcombo.length=0;
	                 	var opt = document.createElement('option');
                        opt.value ="";
                        opt.innerHTML ="Select";
                        txtEmpIdcombo.appendChild(opt);
	                 	 for(var i=0; i<len; i++)
                         {
                             var opt = document.createElement('option');
                             opt.value = response.getElementsByTagName("ledgercode")[i].firstChild.nodeValue;
                             opt.innerHTML = response.getElementsByTagName("codedesc")[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                             txtEmpIdcombo.appendChild(opt);
                         }*/
                
                     var memotype=document.getElementById("memotype").value;
                  //   alert("memotype >> "+memotype);
                     if(memotype==2){
                    	// call('getdet');
                    	
                    	 
                    	// alert('testdd');  
                    	 loadBAnkAccCombo();
                    	  
                     }else if(memotype==7){
                    	 loadLicCombo();
                     }
                    	 else if(memotype!=2 && memotype!=7){
                    		
                    	 loadEmpIDCombo();
                     }
              	}catch(e){alert("Error in grid:"+e);}      
                
                }else if(flag=="TB"){
                	document.getElementById("check_memo_Year").value="";
                	document.getElementById("check_memo_Month").value="s";
                	alert('TB Closed For the Selected Month & Year ... ');
                }
                else
                    {
                	 var txtEmpIdcombo = document.forms[0].txtEmpId;
                 	 txtEmpIdcombo.length=0;
                	 var tbody=document.getElementById("grid_body");
                	  try{tbody.innerHTML="";}
                     catch(e) {tbody.innerText="";}
                	alert('No Data Found');
                                     
                    }
                 }
            else if(command=="loadLicCombo"){


        	
            if(flag=='success')
            {
            	
            	try{
            		var len=response.getElementsByTagName("cid").length;
            		//alert(len);
            		 var txtEmpIdcombo = document.forms[0].txtEmpId;
                 	 txtEmpIdcombo.length=0;
                 	var opt = document.createElement('option');
                    opt.value ="";
                    opt.innerHTML ="Select";
                    
                    txtEmpIdcombo.appendChild(opt);
                 	 for(var i=0; i<len; i++)
                     {
                         var opt = document.createElement('option');
                         opt.value = response.getElementsByTagName("cid")[i].firstChild.nodeValue;
                         opt.innerHTML = response.getElementsByTagName("cname")[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                         txtEmpIdcombo.appendChild(opt);
            		
                     }
            	}catch(e){
            		alert(e);
            }
        }
        
        }
            else  if(command=="getBankIDCombo"){
            	//alert("command >> "+command);
            	 if(flag=='success')
                 {
                 	
                 	try{
                 		var len=response.getElementsByTagName("bank_ac_no").length;
                 		//alert(len);
                 		 var txtEmpIdcombo = document.forms[0].txtEmpId;
                      	 txtEmpIdcombo.length=0;
                      	var opt = document.createElement('option');
                         opt.value ="";
                         opt.innerHTML ="Select";
                         
                         txtEmpIdcombo.appendChild(opt);
                      	 for(var i=0; i<len; i++)
                          {
                              var opt = document.createElement('option');
                              opt.value = response.getElementsByTagName("bank_ac_no")[i].firstChild.nodeValue;
                              opt.innerHTML = response.getElementsByTagName("bank_ac_no")[i].firstChild.nodeValue+" - ("+response.getElementsByTagName("ac_operational_mode_id")[i].firstChild.nodeValue+")"; //instead of using textnode ,we use innerhtml
                              txtEmpIdcombo.appendChild(opt);
                 		
                          }
                 	}catch(e){
                 		alert(e);
                 }
             }
            }
            else    if(command=="getdet")
            {
//alert("command  "+command);
            	
                if(flag=='success')
                {
                	 var memo_type=document.getElementById("memotype").value;
                	var totAmt=0;
                		var len=response.getElementsByTagName("billno").length;
                 	var billno=document.getElementById("billno");
                 	  var tbody=document.getElementById("grid_body");
                 	  try{tbody.innerHTML="";}
                      catch(e) {tbody.innerText="";}
                      try{
                  		//document.getElementById("txtAmount").value=response.getElementsByTagName("sanctionedamount")[0].firstChild.nodeValue;
                  //	alert("memotype >> "+memo_type);
                  		if(memo_type!=2 && memo_type!=1 && memo_type!=7){
                  		//document.getElementById("txtAmount").value=response.getElementsByTagName("sanctionedamount")[0].firstChild.nodeValue;
                  			 for(hk=0;hk<len;hk++){ totAmt+=parseFloat(response.getElementsByTagName("passamount")[hk].firstChild.nodeValue);
                  			 }document.getElementById("txtAmount").value=totAmt;
                  		}	else if(memo_type==2){
                  			
                  			 for(hk=0;hk<len;hk++){
                  				// alert(parseFloat(response.getElementsByTagName("passamount")[hk].firstChild.nodeValue));
                  				 totAmt+=parseFloat(response.getElementsByTagName("passamount")[hk].firstChild.nodeValue);
                  			 }
                  			 //alert("totAmt"+totAmt);
                  			document.getElementById("txtAmount").value=totAmt;
                  			
                  		}else if(memo_type==1){
                  			
                 			 for(hk=0;hk<len;hk++){
                 				// alert(parseFloat(response.getElementsByTagName("passamount")[hk].firstChild.nodeValue));
                 				 totAmt+=parseFloat(response.getElementsByTagName("passamount")[hk].firstChild.nodeValue);
                 			 }
                 			// alert("totAmt"+totAmt);
                 			document.getElementById("txtAmount").value=totAmt;
                 			
                 		}else if(memo_type==7){
                  			
                			 for(hk=0;hk<len;hk++){
                				// alert(parseFloat(response.getElementsByTagName("passamount")[hk].firstChild.nodeValue));
                				 totAmt+=parseFloat(response.getElementsByTagName("passamount")[hk].firstChild.nodeValue);
                			 }
                			// alert("totAmt"+totAmt);
                			document.getElementById("txtAmount").value=totAmt;
                			
                		}
                  		
                  		
                  		else{
                  			alert('Error');
                  		}
                      
                 	      for(var k=0;k<len;k++)
                          {   
                        	  var items=new Array();
                        	  items[0]=response.getElementsByTagName("billno")[k].firstChild.nodeValue;
                        	  items[1]=response.getElementsByTagName("billdate")[k].firstChild.nodeValue;
                        	  items[2]=response.getElementsByTagName("sanctionedamount")[k].firstChild.nodeValue;
                        	  
                        	  items[3]=response.getElementsByTagName("passamount")[k].firstChild.nodeValue;
                        	  items[4]=response.getElementsByTagName("achead")[k].firstChild.nodeValue;
                        	  items[5]=response.getElementsByTagName("head_desc")[k].firstChild.nodeValue;
                        	  
                        	  items[6]=response.getElementsByTagName("typecode")[k].firstChild.nodeValue;
                        	  items[7]=response.getElementsByTagName("type_desc")[k].firstChild.nodeValue;
                        	  
                        	  items[8]=response.getElementsByTagName("ledgercode")[k].firstChild.nodeValue;
                        	  items[9]=response.getElementsByTagName("codedesc")[k].firstChild.nodeValue;
                        	  
                        	  items[10]=response.getElementsByTagName("indicator")[k].firstChild.nodeValue;
                        	  items[11]=response.getElementsByTagName("particulars")[k].firstChild.nodeValue;
                        	  items[12]=response.getElementsByTagName("mtcdate")[k].firstChild.nodeValue;
                        	  
                        	  items[13]=response.getElementsByTagName("minor_desc")[k].firstChild.nodeValue;
                        	  items[14]=response.getElementsByTagName("sanction_desc")[k].firstChild.nodeValue;
                        	  items[15]=response.getElementsByTagName("sub_desc")[k].firstChild.nodeValue;
                        	  items[16]=response.getElementsByTagName("sanction_date")[k].firstChild.nodeValue;
                        	 var date_val=items[16].split("-");
                        	 if(memo_type==3){
                        	// alert(items[16]+" >>>>> "+date_val[2]+"/"+date_val[1]+"/"+date_val[0]);
                        	  document.getElementById("particulars").value=items[13]+" payment ("+items[15]+") made to Bill No:"+items[0]+" and Bill Date:"+items[1]+" to the Employee "+items[9]+" proc .No "+ items[14]+" Dated "+date_val[2]+"/"+date_val[1]+"/"+date_val[0];
                        	 } else if(memo_type==1){
                             	//  alert(items[16]+" >>>>> "+date_val[2]+"/"+date_val[1]+"/"+date_val[0]);
                           	  document.getElementById("particulars").value=items[13]+" payment ("+items[15]+") made to Bill No:"+items[0]+" and Bill Date:"+items[1]+" to the Drawing Officer "+items[9]+" proc .No "+ items[14]+" Dated "+date_val[2]+"/"+date_val[1]+"/"+date_val[0];
                           	 }else if(memo_type==7){
                              	//  alert(items[16]+" >>>>> "+date_val[2]+"/"+date_val[1]+"/"+date_val[0]);
                           	  document.getElementById("particulars").value="Paid to Other Departments Cheque No.____________, Dated "+document.getElementById("txtCheque_DD_date").value;
                              	 }else{
                        		  document.getElementById("particulars").value="Paid to Bank by Yourself Cheque No.____________, Dated "+document.getElementById("txtCheque_DD_date").value;
                        	 }
                        	  var mycurrent_row=document.createElement("TR");
                              mycurrent_row.id=seq;
                         
                         // alert("memo_type >> "+memo_type);
//alert("tems[0]w"+items[0]);
if(memo_type!=2 && memo_type!=1 && memo_type!=7){
	  document.getElementById("selDiv").style.display="block";
 	  document.getElementById("selDiv1").style.display="none";
                            var cell=document.createElement("TD");
                              var txtedit=document.createTextNode("-");
                              cell.appendChild(txtedit);
                              mycurrent_row.appendChild(cell);}
                 	      else{
                 	    	  document.getElementById("selDiv1").style.display="block";
                 	    	  document.getElementById("selDiv").style.display="none";
                 	    	  //  <th>  <div id="selDiv" style="display: block;">    Select</div></th>
                 	    	 var cell=document.createElement("TD");
                 	    	 var chk=document.createElement("input");
                 	    	 chk.type="checkbox";
                 	    	 chk.id="YourselfChk"+seq;
                 	   	 chk.name="YourselfChk";
                 	    	// chk.value="CHECKED"; 
                 	    	 chk.checked="checked";
                 	    	
                 	    	chk.setAttribute('onclick', "checkBillNo(" + seq + ")"); 
                 	    	  cell.appendChild(chk);
                 	    	 mycurrent_row.appendChild(cell);
                 	    	// alert("chk "+chk);
                 	      
                 	      }
                 	      
                           if(memo_type!=2 && memo_type!=1 && memo_type!=7){  
                             var cell2=document.createElement("TD");
                             cell2.setAttribute('align','right');
                             var v_billno=document.createElement("input");
                             v_billno.type="hidden";
                             v_billno.name="billno";
                             v_billno.id="billno"+seq;
                             v_billno.value=items[0];
                             cell2.appendChild(v_billno);
                             var currentText=document.createTextNode(items[0]);
                             cell2.appendChild(currentText);
                             mycurrent_row.appendChild(cell2);
                           }else{
                        	   var cell2=document.createElement("TD");
                               cell2.setAttribute('align','right');
                               var v_billno=document.createElement("input");
                               v_billno.type="hidden";
                               v_billno.name="billno";
                               v_billno.id="billno"+seq;
                               v_billno.value=items[0];
                               cell2.appendChild(v_billno);
                               var currentText=document.createTextNode(items[0]);
                               cell2.appendChild(currentText);
                               
                               
                        	   var anc1=document.createElement("input");
                               anc1.type="hidden";
                               anc1.id="verify_select_status";
                               anc1.name="verify_select_status"; 
                               anc1.value="CHECKED";
                              
                               cell2.appendChild(anc1);
                               mycurrent_row.appendChild(cell2);
                           }
                             
                             
                             
                           var cell2=document.createElement("TD");
                       //    var cell22=document.createElement("TD");
                
                           var v_chk_list=document.createElement("input");
                           cell2.setAttribute('align','right');
                           v_chk_list.type="hidden";
                           v_chk_list.name="chk_list";
                           v_chk_list.id="chk_list"+seq;
                           v_chk_list.value=1;
                           cell2.appendChild(v_chk_list);
                          
                         //  mycurrent_row.appendChild(cell2);
                             
                            
                        
                             var v_billdate=document.createElement("input");
                             v_billdate.type="hidden";
                             v_billdate.name="billdate";
                             v_billdate.value=items[1];
                             cell2.appendChild(v_billdate);
                             var currentText=document.createTextNode(items[1]);
                             cell2.appendChild(currentText);
                             mycurrent_row.appendChild(cell2);
                             
                             
                             var cell2=document.createElement("TD");
                             cell2.setAttribute('align','center');
                             var v_passamt=document.createElement("input");
                             v_passamt.type="hidden";
                             v_passamt.name="passamt";
                             v_passamt.value=items[3];
                             cell2.appendChild(v_passamt);
                             var currentText=document.createTextNode(items[3]);
                             cell2.appendChild(currentText);
                             mycurrent_row.appendChild(cell2);
                             
                             var cell2=document.createElement("TD");
                             cell2.setAttribute('align','right');
                             var head_code=document.createElement("input");
                             head_code.type="hidden";
                             head_code.name="headcode";
                             head_code.value=items[4];
                             cell2.appendChild(head_code);
                             var currentText=document.createTextNode(items[4]+"-"+items[5]);
                             cell2.appendChild(currentText);
                             mycurrent_row.appendChild(cell2);
                             
                             var cell2=document.createElement("TD");
                             cell2.setAttribute('align','center');
                             var v_sltype=document.createElement("input");
                             v_sltype.type="hidden";
                             v_sltype.name="sltype";
                             v_sltype.value=items[6];
                             cell2.appendChild(v_sltype);
                             var currentText=document.createTextNode(items[7]);
                             cell2.appendChild(currentText);
                             mycurrent_row.appendChild(cell2);


                             var cell2=document.createElement("TD");
                             cell2.setAttribute('align','center');
                             var v_typecode=document.createElement("input");
                             v_typecode.type="hidden";
                             v_typecode.name="slcode";
                             v_typecode.value=items[8];
                             cell2.appendChild(v_typecode);
                             var currentText=document.createTextNode(items[9]);
                             cell2.appendChild(currentText);
                             mycurrent_row.appendChild(cell2);
                              
                             var cell2=document.createElement("TD");
                             cell2.setAttribute('align','center');
                             var v_indic=document.createElement("input");
                             v_indic.type="hidden";
                             v_indic.name="indicator";
                             v_indic.value=items[10];
                             cell2.appendChild(v_indic);
                             var currentText=document.createTextNode(items[10]);
                             cell2.appendChild(currentText);
                             mycurrent_row.appendChild(cell2);
                             
                             var cell2=document.createElement("TD");
                             cell2.setAttribute('align','center');
                             var v_dramt=document.createElement("input");
                             v_dramt.type="hidden";
                             v_dramt.name="dramt";
                             v_dramt.value=items[3];
                             cell2.appendChild(v_dramt);
                             var currentText=document.createTextNode(items[3]);
                             cell2.appendChild(currentText);
                             mycurrent_row.appendChild(cell2);
                             
                             var cell2=document.createElement("TD");
                             cell2.setAttribute('align','center');
                             var v_rem=document.createElement("input");
                             v_rem.type="hidden";
                             v_rem.name="remarks";
                             v_rem.value="Cheque Memo From Original Bills";
                             cell2.appendChild(v_rem);
                             var currentText=document.createTextNode("Cheque Memo From Original Bills");
                             cell2.appendChild(currentText);
                             mycurrent_row.appendChild(cell2);
                             
                             
                             var cell2=document.createElement("TD");
                             cell2.setAttribute('align','right');
                             var v_mtcdate=document.createElement("input");
                             v_mtcdate.type="hidden";
                             v_mtcdate.name="mtcentrydate";
                             v_mtcdate.value=items[12];
                             cell2.appendChild(v_mtcdate);
                             var currentText=document.createTextNode(items[12]);
                             cell2.appendChild(currentText);
                             mycurrent_row.appendChild(cell2);
                             
                              tbody.appendChild(mycurrent_row);
                              seq++;
                        	  
                        	 
                        	  
                               
                          }
              	
              	}catch(e){alert("Error in grid:"+e);}      
                
                }
                else
                    {
                	alert('No Data Found');
                                     
                    }
                 	
            }
            
            else if(command=="getdata")
            {
            	
                if(flag=='success')
                {

                	try{
                		
                		
                		document.getElementById("billdate").value =response.getElementsByTagName("billdate")[0].firstChild.nodeValue;
                		document.getElementById("passorderamount").value=response.getElementsByTagName("passamount")[0].firstChild.nodeValue;  
                		//document.getElementById("txtAcc_HeadCode").value=response.getElementsByTagName("achead")[0].firstChild.nodeValue;
                	//	document.getElementById("txtAcc_HeadDesc").value=response.getElementsByTagName("head_desc")[0].firstChild.nodeValue;
                		
                		document.getElementById("txtAmount").value=response.getElementsByTagName("passamount")[0].firstChild.nodeValue;
                		
                		
                		doFunction('checkCode','null');
              	}catch(e){alert("Error in lat"+e);}      
                
                }
                else
                    {
                    }
                 }
            
            else  if(command=="Add")
            {
            	if(flag=='success')
                {
                  
                	alert("Record Inserted into database");
                	
                	ClearAll();
                }
                else
                    {
                    
                    }
            	
            }
            else  if(command=="update")
            {
            	if(flag=='success')
                {
                  
                	alert("Record Updated Successfully");
                	
                	ClearAll();
                }
                else
                    {
                    
                    }
            	
            }
            
            else  if(command=="Delete")
            {
            	if(flag=='success')
                {
                  
                	alert("Record Deleted Successfully");
                	
                	ClearAll();
                }
                else
                    {
                    
                    }
            	
            }
            
            else if(command=="getcode")
            {
            	if(flag=='success')
                {
            		 var len=response.getElementsByTagName("paycode").length;
                  	var payeetype=document.getElementById("payeetype");
                  	payeetype.value=0;
               	 var items_id=new Array();
               	 var items_desc=new Array();                        
                           for(var i=0;i<len;i++)
                           {
                        	 items_id[i]=response.getElementsByTagName("paycode")[i].firstChild.nodeValue;
                        	items_desc[i]=response.getElementsByTagName("paydesc")[i].firstChild.nodeValue;  
                       // alert('minor'+items_desc[i]);
                           }
                      clear_Combo(payeetype);
                      
                  	payeetype.length=0;
                  	var txtEmpId=document.getElementById("txtEmpId");
                  	txtEmpId.length=0;
                  	document.getElementById("check_memo_Month").value="s";
                           //alert('here second');
                           for(var k=0;k<len;k++)
                           {   
                           	//alert(items_name[k]);
                                 var option=document.createElement("OPTION");
                                 option.text=items_desc[k];
                                 option.value=items_id[k];
                              
                                  try
                                 {
                                	  payeetype.add(option);
                                 	
                                 }
                                 catch(errorObject)
                                 {
                                	 payeetype.add(option,null);
                                 	
                                    // alert('error');
                                 }
                           }
                }
                else
                    {
                    alert('Payee Type Not Found');
                    }
            	
            }
            else  if(command=="checkemp")
            {
            	if(flag=='success')
                {
            		document.getElementById("sanname").value=response.getElementsByTagName("empname")[0].firstChild.nodeValue;
            		document.getElementById("sandesignation").value=response.getElementsByTagName("designation")[0].firstChild.nodeValue;
                }
                else
                    {
                    alert('Employee Not found');
                    }
            	
            }
            else  if(command=="headcode")
            {
            	if(flag=='success')
                {
            		document.getElementById("headdesc").value=response.getElementsByTagName("headname")[0].firstChild.nodeValue;
            		call('budget');
                }
                else
                    {
                    alert('Head Code Not found');
                    }
            	
            }
            
            else  if(command=="budget")
            {
            	if(flag=='success')
                {
            		document.getElementById("budgetprovided").value=response.getElementsByTagName("budgetalotted")[0].firstChild.nodeValue;
            		document.getElementById("budgetspent").value=response.getElementsByTagName("budgetspent")[0].firstChild.nodeValue;
            		 document.sanction_proceedings.cmdAdd.disabled=false;
                }
                else
                    {
                    alert('Budget Data Not found');
                    document.sanction_proceedings.cmdAdd.disabled=true;
                    }
            	
            }
            
       }
    }
}

function loadBAnkAccCombo()
{
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var url="../../../../../Cheque_Memo?command=getBankIDCombo&cmbAcc_UnitCode="+cmbAcc_UnitCode;

	//alert(url);
	xmlhttp.open("GET",url,true);
	xmlhttp.onreadystatechange=stateChanged;
	xmlhttp.send(null); 

}function loadLicCombo()
{
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	
	var url="../../../../../Cheque_Memo?command=loadLicCombo&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;

	//alert(url);
	xmlhttp.open("GET",url,true);
	xmlhttp.onreadystatechange=stateChanged;
	xmlhttp.send(null); 

}
function  loadEmpIDCombo(){
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
var check_memo_Year=document.getElementById("check_memo_Year").value;
var check_memo_Month=document.getElementById("check_memo_Month").value;
var memotype=document.getElementById("memotype").value;
var url="../../../../../Cheque_Memo?command=getEmpIDCombo&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&check_memo_Month="+check_memo_Month+"&check_memo_Year="+check_memo_Year+"&memotype="+memotype;

//alert(url);
xmlhttp.open("GET",url,true);
xmlhttp.onreadystatechange=stateChanged1;
xmlhttp.send(null); 

}
function stateChanged1(){
	var flag,command,response;
	   
    if(xmlhttp.readyState==4)
    {
    	
       if(xmlhttp.status==200)
       {
            response=xmlhttp.responseXML.getElementsByTagName("response")[0];
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
            	
    if(command=="getEmpIDCombo")
    {

    	
        if(flag=='success')
        {
        	
        	try{
        		var len=response.getElementsByTagName("leng").length;
        		//alert(len);
        		 var txtEmpIdcombo = document.forms[0].txtEmpId;
             	 txtEmpIdcombo.length=0;
             	var opt = document.createElement('option');
                opt.value ="";
                opt.innerHTML ="Select";
                
                txtEmpIdcombo.appendChild(opt);
             	 for(var i=0; i<len; i++)
                 {
                     var opt = document.createElement('option');
                     opt.value = response.getElementsByTagName("Empid")[i].firstChild.nodeValue;
                   var name=response.getElementsByTagName("EmpName")[i].firstChild.nodeValue
                   if (name=='null') {
                	  // opt.innerHTML = response.getElementsByTagName("EmpName")[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                	   opt.innerHTML ="";
                	   
				} else {
					opt.innerHTML = name; 
				}
                     
                     txtEmpIdcombo.appendChild(opt);
        		
                 }
        	}catch(e){
        		alert(e);
        }
    }
    }
       }
    }
    
}
function nullcheck()
{
	/*820602 @NK  on 25jul20*/
	var acchead=document.getElementById("txtCash_Acc_code").value;
	var begvalue=acchead.substring(0,2);
	var endvalue=acchead.substring(4);
	if((begvalue!=82)&&(endvalue!=02))
	{
		alert('Operation A/C Code must start with 82 and end with 02');
		return false;
	}
	if((begvalue==82)&&(endvalue!=02))
	{
		alert('Operation A/C Code must end with 02');
		return false;
	}
	if((begvalue!=82)&&(endvalue==02))
	{
		alert('Operation A/C Code must start with 82');
		return false;
	}
	if(document.getElementById("memotype").value=="")
	{
		alert('Please Select Memo Type');
		return false;
	}
	if(document.getElementById("check_memo_Month").value=="s")
	{
		alert('Please Enter Check Memo Month');
		return false;
	}
	if(document.getElementById("check_memo_Year").value=="")
	{
		alert('Please Enter checkmemoYear');
		return false;
	}
	if(document.getElementById("vochardate").value=="")
	{
		alert('Please Enter Voucher Date');
		return false;
	}
	
	
	if(document.getElementById("txtCash_Acc_code").value=="")
	{
		alert('Please Enter Operation A/C Code');
		return false;
	}
	if(document.getElementById("payeetype").value=="")
	{
		alert('Please Select Payee Type');
		return false;
	}
	if(document.getElementById("memotype").value==2 && document.getElementById("memotype").value==1){
		
	}else{
	if(document.getElementById("txtEmpId").value=="")
	{
		alert('Please Enter Payee Code');
		return false;
	}
	}
	if(document.getElementById("txtCheque_DD_NO").value=="")
	{
		alert('Please Enter Cheque Number');
		return false;
	}
	 
	if(document.getElementById("txtCheque_DD_date").value=="")
	{
		alert('Please Enter Cheque Date');
		return false;
	}
	var tbody=document.getElementById("grid_body");
	if(tbody.rows.length==0)
	{
	    alert("Please Enter Details Part");
	  	    return false; 
	}
	
	       
	else
	{
		   if(document.getElementById("memotype").value!=2  && document.getElementById("memotype").value!=7  && document.getElementById("memotype").value!=1){
		var cr_amt=0;
		        var db_amt=0;
		        var s_billinc=0;
		       
		        rows=tbody.getElementsByTagName("tr");
		        cr_amt=document.getElementById("txtAmount").value;
		        for(i=0;i<rows.length;i++)
		        {
		            var cells=rows[i].cells;
		           
		                   db_amt=parseFloat(db_amt) + parseFloat(cells.item(3).lastChild.nodeValue);
		                   
		                   
		                   var billdate_grid=cells.item(10).firstChild.value;
		                	//alert(billdate_grid);
		                	 var biisp_grid=billdate_grid.split("/");
		                	// alert(biisp_grid[2]);
		                	 var vochardate_one=document.getElementById("vochardate").value;
		                	 
		                	 var vochardate_two=vochardate_one.split("/");
		                	// alert(vochardate_two[2]);
		                	 if(biisp_grid[2]>vochardate_two[2])
		                	 {
		                		 s_billinc++;
		                		
		                	 }
		                	 else if(biisp_grid[2]==vochardate_two[2])
		                	 {
		                		 if(biisp_grid[1]>vochardate_two[1])
			                	 {
			                		 s_billinc++;
			                	 }
			                	 else if(biisp_grid[1]==vochardate_two[1])
			                	 {
			                		// alert(vochardate_two[0]);
			                		 if(biisp_grid[0]>vochardate_two[0])
				                	 {
				                		 s_billinc++;
				                	 } 
			                	 }
		                	 }
		         
		        }
		     

		        if(parseFloat(cr_amt)!=parseFloat(db_amt))      // Either CR or DR must
																// equal in total
		        {
		        alert("Amount doesn't Tally.. Difference " +(parseFloat(cr_amt)-parseFloat(db_amt)));
		        return false;
		        }
		        else if(document.getElementById("txtAmount").value==""){
					//   alert('test');
					    return false; 
				   }    
		      
		        }
		   
		 
		   else if(document.getElementById("memotype").value==2 || document.getElementById("memotype").value==1 || document.getElementById("memotype").value==7){
			  // alert('test');
			 
			 //  var checkbox = document.getElementsByName('YourselfChk');
			//   alert()
			    var chvalue=document.getElementsByName("verify_select_status");

				var cr_amt=0;
				        var db_amt=0;
				        var s_billinc=0;
				       
				        rows=tbody.getElementsByTagName("tr");
				        cr_amt=document.getElementById("txtAmount").value;
				    	//alert("rows.length  "+rows.length);
				        for(var i=0;i<rows.length;i++)
				        {
				        	try{
				            var cells=rows[i].cells;
				          //  alert("cells"+i);
				           // alert("cells.item(0).lastChild.checked >>  "+cells.item(0).lastChild.checked);
				         	//if(cells.item(0).lastChild.checked==true)  { 
				          //  alert(cells.item(0).lastChild.checked);
				            if(cells.item(0).lastChild.checked==true)  { 
				         		//alert(db_amt);
				         	//	alert(parseFloat(cells.item(3).lastChild.nodeValue));
				         		db_amt=parseFloat(db_amt)+ parseFloat(cells.item(3).lastChild.nodeValue);
				         	//	checkbox[i].value="CHECKED";
					    		chvalue[i].value="CHECKED";
				         	}
				             
				            else if(cells.item(0).lastChild.checked==false) {
				         	//	checkbox[i].value="UNCHECKED";
					    		chvalue[i].value="UNCHECKED";
				         	}  else{
				         		alert('Error');
				         	} 		
				        }catch (e) {
							alert(e);
						}
				        }
				        document.getElementById("txtAmount").value=db_amt;
				      //  alert('check '+checkbox.value);
				      //  alert('Status '+chvalue.value);
				        alert('Please Check the Total Cheque Amount Rs '+db_amt);
				       
				           
		   }
/*		   if(document.getElementById("memotype").value==2) 
		   {
			   

		    	var checkbox = document.getElementsByName('YourselfChk');
		    var chvalue=document.getElementsByName("verify_select_status");

		    var ln = checkbox.length;
alert("ln"+ln);
		    var count_chk=0;
		    for(var i=0;i<ln;i++)
		    {
		    	
		    	try{
		    	if(checkbox[i].checked==true){
		    	
		    		count_chk++;
		    		checkbox[i].value="CHECKED";
		    		chvalue[i].value="CHECKED";
		    	}else{
		    		//alert("unchecked");
		    		checkbox[i].value="UNCHECKED";
		    		chvalue[i].value="UNCHECKED";
		    	}
		    	}catch(e){
		    		alert(e);
		    	}		
		    }

		   }*/
		   
		   
		   if(s_billinc>0)
	            {
		        	
	            	 alert("Voucher Date should be Greater than MTC Date/Approval Date");
	        		 document.getElementById("vochardate").value="";
	        		 return false;
	            }
			if(document.getElementById("txtCheque_DD_NO").value=="")
			{
				alert('Please Enter Cheque Number');
				return false;
			}
		 
	}
	
	document.getElementById("firid").style.display="none";
	document.getElementById("secid").style.display="block";
	if(document.getElementById("memotype").value==2){
		   document.getElementById("particulars").value="Paid to Bank by Yourself Cheque No. " +document.getElementById("txtCheque_DD_NO").value+" , Dated "+document.getElementById("txtCheque_DD_date").value;
	 
	}else if(document.getElementById("memotype").value==7){
		 //  document.getElementById("particulars").value="Paid to Other Departments Cheque No. " +document.getElementById("txtCheque_DD_NO").value+" , Dated "+document.getElementById("txtCheque_DD_date").value;
		   document.getElementById("particulars").value="Paid to "+document.getElementById("txtEmpId").options[document.getElementById("txtEmpId").selectedIndex].text+" Cheque No. " +document.getElementById("txtCheque_DD_NO").value+" , Dated "+document.getElementById("txtCheque_DD_date").value;
	}
return true;	

}
function checkBillNo(id){
	
	var tbody=document.getElementById("grid_body");
	 rows=tbody.getElementsByTagName("tr");	
	 var chvalue=document.getElementsByName("verify_select_status");
     for(i=0;i<rows.length;i++)
    	 {
    	var bill_No_val= document.getElementById("billno"+id).value;
    	//alert("bill_No_val "+bill_No_val);
    	  var cells=rows[i].cells;
    	  if(document.getElementsByName('billno')[i].value==bill_No_val && document.getElementsByName("chk_list")[i].value==0){
    		  document.getElementById('YourselfChk'+i).checked=true;
    		  document.getElementsByName("chk_list")[i].value=1;
    		  chvalue[i].value="CHECKED";
    	  } else if(document.getElementsByName('billno')[i].value==bill_No_val && document.getElementsByName("chk_list")[i].value==1){
    		  document.getElementById('YourselfChk'+i).checked=false;
    		  document.getElementsByName("chk_list")[i].value=0; 
    		  chvalue[i].value="UNCHECKED";
    	 }
}
}
function selectall()
{
	  var checkbox = document.getElementsByName('YourselfChk');
		//   alert()
		    var chvalue=document.getElementsByName("verify_select_status");

			var cr_amt=0;
			        var db_amt=0;
			        var s_billinc=0;
			       
			        rows=tbody.getElementsByTagName("tr");
			        cr_amt=document.getElementById("txtAmount").value;
			    
			        for(i=0;i<rows.length;i++)
			        {try{
			            var cells=rows[i].cells;
			   
			            if(cells.item(0).lastChild.checked==true)  { 
			         
			         		db_amt=parseFloat(db_amt)+ parseFloat(cells.item(3).lastChild.nodeValue);
			      
				    		chvalue[i].value="CHECKED";
			         	}
			             
			            else if(cells.item(0).lastChild.checked==false) {
			         
				    		chvalue[i].value="UNCHECKED";
			         	}  else{
			         		alert('Error');
			         	} 		
			        }catch (e) {
						alert(e);
					}
			        }
	}
function unselect()
{
	 
		    var chvalue=document.getElementsByName("verify_select_status");
			var tbody=document.getElementById("grid_body");
			        var rows=tbody.getElementsByTagName("tr");
			        for(var i=0;i<rows.length;i++)
			        {try{
			        	
			            var cells=rows[i].cells;
			            var checkbox=document.getElementById("YourselfChk"+i);
				    		chvalue[i].value="UNCHECKED";
				    		//alert("checkbox.checked "+checkbox.checked);
				    		checkbox.checked = false;
				    		  document.getElementsByName("chk_list")[i].value=0;
			        }catch (e) {
						alert(e);
					}
			        }document.getElementById("txtAmount").value="";
	}
function filter_real(evt,item,n,pre)
{
         var charCode = (evt.which) ? evt.which : event.keyCode
// allow "." for one time 
         if(charCode==46){
                        //    alert("Position of . "+item.value.indexOf("."));
                                if(item.value.indexOf(".")<0)    return (item.value.length>0)?true:false;
                                else return false;
          }
         if (!(charCode > 31 && (charCode < 48 || charCode > 57))){
                // to avoid over flow
                        if(item.value.indexOf(".")<0){
        //            alert("Length without . ="+item.value.length);
                                return (item.value.length<n)?true:false;
                        }
                // dont allow more than 2 precision no's after the point
                        if(item.value.indexOf(".")>0){
                        //    alert("precision count ="+item.value.split(".")[1].length);
                                if(item.value.split(".")[1].length<pre) return true;
                                else return false;
                        }
                        return false;
        }else{
                        return false;
        }
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



/*
 * Check Remarks Field Length 
*/ 

function check_leng(param,remarks)
{
		if(param=='remarks')
		{
			if((remarks.length)>=240)
		    {
				alert("Please Enter Remarks below 250 characters");
		    }
		}
	  
	/*	if(param=='received_from') 
		{
		    if((remarks.length)>=45)
		    {
		    	alert("Please Enter Received From name below 50 characters");
		    }
		}
	  */
		if(param=='particulars') 
		{
		    if((remarks.length)>=190)
		    {
		    	alert("Please Enter Paticulars below 200 characters");
		    }
		}
}
function exit()
{
    self.close();
}

function call_clr()
    {
            //alert("callling clear");
            document.getElementById("cmbAcc_UnitCode").value=0;
            document.getElementById("cmbOffice_code").value=0;
            document.getElementById("memotype").value="";
            document.getElementById("check_memo_Year").value="";
            document.getElementById("check_memo_Month").value="s";
            document.getElementById("vochardate").value="";
            document.getElementById("txtCash_Acc_code").value="";
            document.getElementById("txtBankAccountNo").value="";
            document.getElementById("txtBankName").value="";
            document.getElementById("payeetype").value="";
            document.getElementById("txtEmpId").value="";
            document.getElementById("chequeno").value="";
            document.getElementById("chequedate").value="";
           // document.getElementById("chequeamount").value="";
            document.getElementById("particulars").value="";
            
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
                         clear();
	   }
}

chequeRange=function(){	
	//alert("asdasf");
		var accunitId=document.getElementById('cmbAcc_UnitCode').value;
               
		var officeId=document.getElementById('cmbOffice_code').value;
               var chequeNo=document.getElementById('txtCheque_DD_NO').value;
		var accountNo=document.getElementById('txtBankAccountNo').value;
		if(accountNo=="")
		{
			alert("Enter Account No");
			document.getElementById('txtCheque_DD_NO').value="";
			return false;
		}
        if(chequeNo=="0"){
        	alert("Enter Valid Cheque No");
			document.getElementById('txtCheque_DD_NO').value="";
			return false;
        }
		var url="../../../../../BankPay_PendingBill_Create.view?Command=chequeRange&chequeNo="+chequeNo+"&accunitId="+accunitId+"&accountNo="+accountNo;
        var req=getTransport();
        req.open("POST",url,true); 
        req.onreadystatechange=function(){
        	processResponse(req);
        };   
        req.send(null);
	
       
		
};
function processResponse(req){
	 if(req.readyState==4)
    { 
        if(req.status==200)
        {  
       	 var rangeResponse=req.responseXML.getElementsByTagName("response")[0];
     	   chequeRangeResponse(rangeResponse);
        }
    }
}
function chequeRangeResponse(response){
	var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if(flag=="fail"){
		alert("Cheque No does not exist Please Fill the Cheque No Details in the ChequeBook Master");
		 if(window.confirm("Do you want to Continue the Cheque No ..."))
		   {
			   	 //call_clr();
	                        // clear();
		   }else{
		document.getElementById('txtCheque_DD_NO').value="";
		   }
	}
	else
	{
		return true;
	}
}



function call_maindate()     
																													// value handling
{
            
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
             var TB_date=document.getElementById("vochardate").value;
             // alert(fromcal_dateCtrl.value+"b4url")
             if(TB_date.length!=0)
             {
                 var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                        // alert(url);
                 var req=getxmlhttpObject();
                 req.open("GET",url,true); 
                 req.onreadystatechange=function()
                 {
                   check_TB(req);
                 }   
                 req.send(null);
            }
    
}



function check_TB(req)
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
            	callBDate();
              }
             else if(flag=="failure")
               {
            	
                    alert("Trial Balance Closed");// return false;//
                    document.getElementById("vochardate").focus();
                    document.getElementById("vochardate").value="";
               }
             else if(flag=="finyear")
               {
            	 alert("Trial Balance Closed");// return false;//
                 document.getElementById("vochardate").focus();
                 document.getElementById("vochardate").value="";   
               }
            dateCheck(dateCtrl); 
        }
    }
}

function dateCheck(datechk)
{
	//alert("WELCOME!........."+datechk);
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	//alert("cmbAcc_UnitCode>>>"+cmbAcc_UnitCode);
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    //alert("cmbOffice_code>>>"+cmbOffice_code);
    //var txtCrea_date=document.getElementById("txtCrea_date").value;
    var txtCrea_date=datechk.value;
   // alert("txtCrea_date>>>>"+txtCrea_date);
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