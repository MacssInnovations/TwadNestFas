////////////////////////////////////////////////////////////////////////////
//  Varailbes Declaration
///////////////////////////////////////////////////////////////////////////
var flag="";
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
    var txtModule_Type="MF005";
    var cr_dr_indi="CR";
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
     
    winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
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
   //   alert(url);
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

/////////////////////////////////////////   "Pending Bills" /////



var winPendingbills;
function call_pendingbills()
{
     if(document.getElementById("txtCrea_date").value.length==0)
     {
         alert("Enter the Payment date");
         return false;
     }
     if(document.getElementById("frmDate").value.length==0)
     {
         alert("Enter the from date");
         return false;
     }
      if(document.getElementById("cmbMas_SL_type").value=="" || document.getElementById("cmbMas_SL_Code").value=="")
      {
        alert("Select Both Sub-Ledger Type and Code");
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
          var dateval=document.getElementById("txtCrea_date").value;    // used to get cash book month and year in "PendingBills.jsp"
             var type_MasSL=document.getElementById("cmbMas_SL_type").value;    
               var code_MasSL=document.getElementById("cmbMas_SL_Code").value;    
               var frmDate=document.getElementById("frmDate").value;   
               var toDate=document.getElementById("toDate").value;   
              
               
              
    winPendingbills= window.open("../../../../../org/FAS/FAS1/PaymentSystem/jsps/PendingBills_Revised1.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&dateval="+dateval+"&frmDate="+frmDate+"&toDate="+toDate+"&type_MasSL="+type_MasSL+"&code_MasSL="+code_MasSL,"PendingBillsSearch","status=1,height=1000,width=1000,resizable=YES, scrollbars=yes"); 
    winPendingbills.moveTo(250,250);  
    winPendingbills.focus();
}
function doParentPendingbills(vouNO,vouSL_NO,vouDATE,vouTYPE,AcHeadCode,AcHeadName,CR_DB_indic,subLed_type,subLed_typeName,subLed_code,subLed_codeName,billNO,billdate,billtype,agree_NO,agree_date,paid_to,subamount,txt_particular,vrAmt,MULTIPLE_PVR_DETAILS,MVR_no,seloff,j,flag)
{
	/*document.frmBankPay_PendingBill_create.selOff.value="";
	document.frmBankPay_PendingBill_create.selOff.value=seloff;*/
	 /*document.getElementById("selOff").value=seloff;
	 var sel=document.getElementById("selOff").value
	 alert(sel);*/
	 
	 var off = new Array(seloff);
	// alert('"'+off+'"')
	 var test='"'+off+'"';
	// var str = test;
	   var offArray = test.split(',');
	   for(var i = 0; i < offArray.length; i++) {
	      // Trim the excess whitespace.
		   offArray[i] = offArray[i].replace(/^\s*/, "").replace(/\s*$/, "").replace(/^"?(.+?)"?$/,'$1');
	      // Add additional code here, such as:
	    	   }

	  var newArray = new Array();
    var items=new Array(30);
    var tbody=document.getElementById("grid_body");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
    var tot_amount=0;
    var cr_amt=0;
    var dr_amt=0;
   //alert("first "+j);
   var jj=new Array();
    jj=vouNO;
   j=jj.length;
   
  
   
   //alert("sec "+j);
    for(var i=0;i<j;i++)
    {
     
    	// alert("hjh "+i+"  len  "+MVR_no[i]);
    	items[0]=AcHeadCode[i];
                    items[1]=AcHeadName[i];
                    items[2]=CR_DB_indic[i];
                    //off[i];
                   // alert(off[i].split(,));
                  // alert(l);
                    //items[24]= off[i];
                    items[3]=subLed_type[i];
                    items[4]=subLed_typeName[i];
                    items[5]=subLed_code[i];
                    items[6]=subLed_codeName[i];
                  
               /*  if(items[3]=="undefined")
                     items[3]="";
                   if(items[4]=="undefined")
                    items[4]="";
                    if(items[5]=="undefined")
                    items[5]="";
                     if(items[6]=="undefined")
                    items[6]="";*/
                    
                    
                    //items[7]=baseResponse.getElementsByTagName("sub_rec_frm")[k].firstChild.nodeValue;
                    //if(items[7]=="null")
                    //items[7]="";
                   
                    //items[7]="";
                    //items[8]="";
                    //items[9]="";
                    
                    items[10]=billNO[i];
                    items[11]=billdate[i];
                    items[12]=billtype[i];
                    items[13]=agree_NO[i];
                    items[14]=agree_date[i];
                    items[15]="";
                    items[16]=subamount[i];
                    
                  // alert(subamount[i]);
                    
                    items[17]=txt_particular[i];               //baseResponse.getElementsByTagName("sub_part")[k].firstChild.nodeValue;
                    items[18]=vouNO[i];
                    items[19]=vouDATE[i];
                    items[20]=vouTYPE[i];
                    items[21]=vouSL_NO[i];
                    
                    items[22]=MULTIPLE_PVR_DETAILS[i];
                    items[23]=vrAmt[i];
                   
                  //  alert(subamount[i]+" == "+vrAmt[i]);
                            //  if(subamount[i]==vrAmt[i])   {    
                 //   alert("cc 11 cr_amt "+cr_amt+"dr_amt"+dr_amt+"tot_amt"+items[23])
            if(items[2]=="DR") {
            	dr_amt=parseFloat(dr_amt)+parseFloat(items[23]);
            	//alert(dr_amt);
            }
            else if(items[2]=="CR") {
            	cr_amt=parseFloat(cr_amt)+parseFloat(items[23]);
            	//alert(cr_amt);
            }
          
            tot_amount=parseFloat(dr_amt)-parseFloat(cr_amt); 
         //  alert("cr_amt "+cr_amt+"dr_amt"+dr_amt+"tot_amt"+tot_amount)
            
                  /*  if(vouTYPE[i]=="LJV")
                    {
                      tot_amount=parseFloat(tot_amount)+parseFloat(subamount[i]);
                    }
                    else if (vouTYPE[i]=="GJV")
                    {
                      tot_amount=parseFloat(tot_amount)-parseFloat(subamount[i]); 
                    }*/
                   
                   // alert   ("tesa total value "+tot_amount);
                    /* }
                              else{
                            	  tot_amount=vrAmt[i];
                              }*/
                                       
                  
                 
               /*    alert("first "+MVR_no[i]);
               if(MVR_no[i]){
                	 items[23] =newArray.push(MVR_no[i]);
                
               }*/
                 //  items[23]=MVR_no[i];
               // alert("secound "+items[23]);  
                 
             /*      if(items[10]=="undefined")
                    items[10]="";
                    if(items[11]=="undefined")
                    items[11]="";
                     if(items[12]=="undefined")
                    items[12]="";
                     if(items[13]=="undefined")
                    items[13]="";
                     if(items[14]=="undefined")
                    items[14]="";
                     if(items[15]=="undefined")
                    items[15]="";
                     if(items[17]=="undefined")
                    items[17]="";*/
                    
                     tbody=document.getElementById("grid_body");
                   var mycurrent_row=document.createElement("TR");
                    seq=seq+1;
                    mycurrent_row.id=seq;
                 /*  var cell=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadTable('"+mycurrent_row.id+"')";
                    anc.href=url;
                    var txtedit=document.createTextNode("EDIT");
                    anc.appendChild(txtedit);
                    cell.appendChild(anc);
                    mycurrent_row.appendChild(cell);
                    
                */
                 
                    var cell2;
                    
                   cell2=document.createElement("TD");
                        cell2.align="CENTER";
                               var vou_type=document.createElement("input");
                              vou_type.type="hidden";
                              vou_type.name="vou_type";
                              vou_type.value=items[20];
                             // cell2.style.display="none";
                              cell2.appendChild(vou_type);
                               var currentText=document.createTextNode(items[20]);
                              cell2.appendChild(currentText);
                               mycurrent_row.appendChild(cell2);
                    
                  cell2=document.createElement("TD");
                              var vou_date=document.createElement("input");
                              vou_date.type="hidden";
                              vou_date.name="vou_date";
                              vou_date.value=items[19];
                              cell2.appendChild(vou_date);
                               var currentText=document.createTextNode(items[19]);
                              cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                            
                    cell2=document.createElement("TD");
                        cell2.align="CENTER";
                             var Voucher_no=document.createElement("input");
                              Voucher_no.type="hidden";
                              Voucher_no.name="Voucher_no";
                              Voucher_no.value=items[18];
                              cell2.appendChild(Voucher_no);
                              var currentText=document.createTextNode(items[18]);
                              cell2.appendChild(currentText);
                               mycurrent_row.appendChild(cell2);
                               
                       cell2=document.createElement("TD");                             // added on 27th April 07
                          cell2.align="CENTER";
                               var vouSL_NO_new=document.createElement("input");
                              vouSL_NO_new.type="hidden";
                              vouSL_NO_new.name="vouSL_NO_new";
                              vouSL_NO_new.value=items[21];
                              cell2.style.display="none";
                              cell2.appendChild(vouSL_NO_new);
                               var currentText=document.createTextNode(items[21]);
                              cell2.appendChild(currentText);
                               mycurrent_row.appendChild(cell2);
                               
                        
                            
                        
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
                              cell2.style.display="block";
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
                            var seloff=document.createElement("input");
                            seloff.type="hidden";
                            seloff.name="seloff";
                            seloff.value=offArray[i];
                            cell2.appendChild(seloff);
                             var currentText=document.createTextNode(offArray[i]);
                            cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                            
                          /*  var seloff=document.createElement("input");           // Particulars Added to grid b4 the Amount Text Node but after  amount text box    
                            seloff.type="hidden";
                            seloff.name="seloff";
                            seloff.value=items[24];
                            cell2.appendChild(seloff);   */
                            
                /*        cell2=document.createElement("TD"); 
                             var Cheque_DD=document.createElement("input");
                              Cheque_DD.type="hidden";
                              Cheque_DD.name="Cheque_DD";
                              Cheque_DD.value=items[7];
                              cell2.appendChild(Cheque_DD);
                    /*                                                              within the star indicates to avoid the column added in b/w two cells
                            //var currentText=document.createTextNode(items[7]);          
                            //cell2.appendChild(currentText);
                            //mycurrent_row.appendChild(cell2);
                         //cell2=document.createElement("TD");  
                    
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
                            mycurrent_row.appendChild(cell2);     */
                            
                         cell2=document.createElement("TD");
                              var Bill_NO=document.createElement("input");
                              Bill_NO.type="hidden";
                              Bill_NO.name="Bill_NO";
                              Bill_NO.value=items[10];
                              cell2.appendChild(Bill_NO);
                                                                                                   //    var currentText=document.createTextNode(items[7]);
                                                                                                   //    cell2.appendChild(currentText);
                                                                                                    //mycurrent_row.appendChild(cell2);
                        
                                                                                                                 //cell2=document.createElement("TD");
                              var Bill_date=document.createElement("input");
                              Bill_date.type="hidden";
                              Bill_date.name="Bill_date";
                              Bill_date.value=items[11];
                              cell2.appendChild(Bill_date);
                                                                                                              // var currentText=document.createTextNode(items[8]);
                                                                                                              //cell2.appendChild(currentText);
                                                                                                                // mycurrent_row.appendChild(cell2);
                            
                            
                                                                                                            // cell2=document.createElement("TD"); 
                              var Bill_type=document.createElement("input");
                              Bill_type.type="hidden";
                              Bill_type.name="Bill_type";
                              Bill_type.value=items[12];
                              cell2.appendChild(Bill_type);
                              
                                                                                                            //  cell2=document.createElement("TD");
                              var Agree_No=document.createElement("input");
                              Agree_No.type="hidden";
                              Agree_No.name="Agree_No";
                              Agree_No.value=items[13];
                              cell2.appendChild(Agree_No);
                                
                              var Agree_date=document.createElement("input");
                              Agree_date.type="hidden";
                              Agree_date.name="Agree_date";
                              Agree_date.value=items[14];
                              cell2.appendChild(Agree_date);
                              
                              
                                         
                           /*   var sub_paid=document.createElement("input");
                              sub_paid.type="hidden";
                              sub_paid.name="sub_paid";
                              sub_paid.value=items[15];
                              cell2.appendChild(sub_paid);
                            */
                            //  alert("ss"+items[16]);
                            //  alert("ss  hh >>  "+items[16].split("-")[1]);
                              var sl_amt=document.createElement("input");
                              sl_amt.type="hidden";
                              sl_amt.name="sl_amt";
                              sl_amt.value=items[23];
                              cell2.appendChild(sl_amt);
            
                              var particular=document.createElement("input");           // Particulars Added to grid b4 the Amount Text Node but after  amount text box    
                              particular.type="hidden";
                              particular.name="particular";
                              particular.value=items[17];
                              cell2.appendChild(particular);
                              
                              var PVR_DETAILS=document.createElement("input");           // Particulars Added to grid b4 the Amount Text Node but after  amount text box    
                              PVR_DETAILS.type="hidden";
                              PVR_DETAILS.name="PVR_DETAILS";
                              PVR_DETAILS.value=items[22];
                              cell2.appendChild(PVR_DETAILS);
                             // alert("final "+items[16].split("-")[1]);
                            //  alert(items[22]);
                           
                              var MVR_no=document.createElement("input");           // Particulars Added to grid b4 the Amount Text Node but after  amount text box    
                              MVR_no.type="hidden";
                              MVR_no.name="MVR_no";
                              MVR_no.value=items[16].split("-")[1];
                              cell2.appendChild(MVR_no);
                              
            
                              var currentText=document.createTextNode(items[23]);
                              cell2.appendChild(currentText);
                              mycurrent_row.appendChild(cell2);
                            
                         
                   
                                tbody.appendChild(mycurrent_row);
            
    }
   
    document.getElementById("txtAmount").value=tot_amount;
  //  document.getElementById("seloff").value=seloff;
     //alert(" flag123 >> "+flag);
   // alert(tot_amount+"*********");                    
    if(tot_amount<=0)
    	{
    	document.getElementById("butSub").disabled=true;
    	}
    else
    	{
    	document.getElementById("butSub").disabled=false;
    	}
    
    document.getElementById("hid_flag").value=flag;
    
  
}



 
window.onunload=function()
{
    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
    if (winemp && winemp.open && !winemp.closed) winemp.close();
    if (winjob && winjob.open && !winjob.closed) winjob.close();
    if (winPendingbills && winPendingbills.open && !winPendingbills.closed) winPendingbills.close();
    if (winAcc_Bank_No && winAcc_Bank_No.open && !winAcc_Bank_No.closed) winAcc_Bank_No.close();
};



function loadName_Mas(value)
{
    if(document.getElementById("cmbMas_SL_Code").value=="")
    return;
    value=document.getElementById("cmbMas_SL_Code").options[document.getElementById("cmbMas_SL_Code").selectedIndex].text; 
    s=document.getElementById("cmbMas_SL_type").value;
    if(s=="7" )
    value=value.substring(0,value.indexOf("-"));
    
    document.getElementById("txtPaid_to").value=value;
    //document.getElementById("txtPay_Vou_No").value="";
    //document.getElementById("txtPay_Vou_date").value="";
    //document.getElementById("txtJournal_code").value="";
}



function enable_partamount(val)
{
    
    if(val=="Y")
     {
     document.getElementById("txtPart_Amount").disabled=false;
     }
    else
    {
        document.getElementById("txtPart_Amount").disabled=true;
        document.getElementById("txtPart_Amount").value="";
    }
}


 
function call_clr()
{
    /*
    document.getElementById("txtCash_Acc_code").value="";
    document.getElementById("txtBankAccountNo").value="";
    document.getElementById("txtBankName").value="";
    document.getElementById("txtBankId").value="";
    document.getElementById("txtBranchId").value="";
    */
    document.getElementById("cmbMas_SL_type").value="";
    clear_Combo(document.getElementById("cmbMas_SL_Code"));
    //document.getElementById("txtPay_Vou_No").value="";
   // document.getElementById("txtPay_Vou_date").value="";
   // document.getElementById("txtJournal_code").value="";
    document.frmBankPay_PendingBill_create.radPart_Amt[1].checked=true;
    document.getElementById("txtPart_Amount").value="";
    document.getElementById("txtAmount").value="";
    document.getElementById("txtRemarks").value="";
    document.getElementById("txtPaid_to").value="";
    document.frmBankPay_PendingBill_create.txtCheque_DD[0].checked=true;
    document.getElementById("txtCheque_DD_NO").value="";
    document.getElementById("txtCheque_DD_date").value="";
   
    //clearall();
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
    alert("Enter the Operation  A/c Code");
    //document.getElementById("txtCash_Acc_code").focus();
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

if(document.getElementById("txtBankAccountNo").value.length==0 || document.getElementById("txtBankAccountNo").value==0)
{
    alert("Enter the Account Number");
    //document.getElementById("txtRecei_from").focus();
    return false;    
}

if(document.getElementById("cmbMas_SL_type").value=="")
{
    //if(document.getElementById("cmbMas_SL_Code").value=="")
    //{
    alert("Select The Sub Ledger Type in General");
    return false;
    //}
}
if(document.getElementById("cmbMas_SL_type").value!="")
{
    if(document.getElementById("cmbMas_SL_Code").value=="")
    {
    alert("Select The Sub Ledger Code in General");
    return false;
    }
}
if(document.frmBankPay_PendingBill_create.radPart_Amt[0].checked==true)
{
 if(document.getElementById("txtPart_Amount").value.length==0)
 {
   alert("Enter the Part Amount ");
   return false;
 }
}
if(document.getElementById("txtPaid_to").value.length==0)
{
    alert("Enter the value in Paid to Field in General");
    //document.getElementById("txtPaid_to").focus();
    return false;    
}
   
if(document.getElementById("txtRemarks").value.length==0)
{
    alert("Enter the Remarks in General");
    //document.getElementById("txtPaid_to").focus();
    return false;    
}

/*if(document.getElementById("txtPay_Vou_No").value.length==0)
            {
                alert("Select Journal Voucher Number from Pending Bills");
                //document.getElementById("txtPaid_to").focus();
                return false;    
            }
            if(document.getElementById("txtPay_Vou_date").value.length==0)
            {
                alert("Select Journal Voucher Date from Pending Bills");
                //document.getElementById("txtPaid_to").focus();
                return false;    
            }
            */
if(document.getElementById("txtAmount").value.length==0)
{
    alert("Enter the Total Amount in General");
    //document.getElementById("txtAmount").focus();
    return false;    
}

   
if(document.frmBankPay_PendingBill_create.txtCheque_DD[2].checked == false)
 { 
 
    if(document.getElementById("txtCheque_DD_NO").value.length==0)
    {
         alert("Enter the Chequ/DD number");
         //document.getElementById("txtCheque_DD_NO").focus();
      return false;
    }
    if(document.getElementById("txtCheque_DD_date").value.length==0)
    {
         alert("Enter the Cheque/DD Date");
         //document.getElementById("txtCheque_DD_date").focus();
        return false;
    }    
}    
    
    
    
if(tbody.rows.length==0)
{
    alert("Enter the Details Part");
    
    //document.getElementById("txtAmount").focus();
    return false; 
}

/// if(tbody.rows.length>0)
///{
///        var check_amt=0;
///        var chkCR_DB=0;
///        rows=tbody.getElementsByTagName("tr");
///        for(i=0;i<rows.length;i++)
///        {
///            var cells=rows[i].cells;
           // if(cells.item(4).lastChild.nodeValue=='CR')
             // chkCR_DB=parseFloat(chkCR_DB) + parseFloat(cells.item(7).lastChild.nodeValue); //check_amt=check_amt;      //check_amt=parseFloat(check_amt) - parseFloat(cells.item(7).lastChild.nodeValue);  // ** removed bcoz just we need to check CR & DR equal or not
           // else
///            check_amt=parseFloat(check_amt) + parseFloat(cells.item(7).lastChild.nodeValue);
            
           //alert(cells.item(9).lastChild.nodeValue)
           
           /* if(cells.item(5).lastChild.nodeValue.length==0)
            {
            alert("Cheque/DD number missing in Details part Entries");
            return false;
            }*/
           // alert(cells.item(2).firstChild.value)
///        }
       /* if(parseFloat(chkCR_DB)!=parseFloat(check_amt))
        {
            alert("Credit and Debit Amount doesn't Tally in details.. Difference " +(parseFloat(chkCR_DB)-parseFloat(check_amt)));
            return false;
        }*/
        //if(parseFloat(document.getElementById("txtAmount").value)!=parseFloat(chkCR_DB))
        
///        if(parseFloat(document.getElementById("txtAmount").value)!=parseFloat(check_amt))
///        {
///        alert("Amount doesn't Tally.. Difference " +(parseFloat(document.getElementById("txtAmount").value)-parseFloat(check_amt)))
///        return false;
///        }
        
///  }
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

//function call_date(dateCtrl)                        // TB_checking 
//{
//   // call_clr();
//	//alert('testing');
//    if(checkdt(dateCtrl))
//    {
//        //doFunction('check_TB',dateCtrl.value);
//         var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
//         var cmbOffice_code=document.getElementById("cmbOffice_code").value;
//         var TB_date=dateCtrl.value;
//       
//         if(dateCtrl.value.length!=0)
//         {
//             var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
//             var req=getTransport();
//             req.open("GET",url,true); 
//             req.onreadystatechange=function()
//             {
//               check_TB(req,dateCtrl);
//             };   
//             req.send(null);
//       }
//        //doFunction('load_Voucher_No','null');
//    }
//    else
//    {
//      document.getElementById("txtVoucher_No").value="";
//    }
//}
//
//
//
//function check_TB(req,dateCtrl)
//{
// if(req.readyState==4)
//    {
//        if(req.status==200)
//        {  
//            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
//            var tagcommand=baseResponse.getElementsByTagName("command")[0];
//            var Command=tagcommand.firstChild.nodeValue;
//            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
//            
//            if(flag=="success")
//              {
//                 //doFunction('load_Voucher_No','null');                 //return true;
//              }
//             else if(flag=="failure")
//               {
//                    dateCtrl.value="";
//                    alert("Trial Balance Closed");//return false;//
//                    dateCtrl.focus();
//                    document.getElementById("txtVoucher_No").value="";
//               }
//            else if(flag=="finyear")
//               {
//                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
//                    dateCtrl.value="";
//                    alert("Cash Book Control Not Found ");//return false;//
//                    dateCtrl.focus();
//                    document.getElementById("txtVoucher_No").value="";     
//               }
//        }
//    }
//}


function call_date(dateCtrl)                        // TB_checking 
{
	     call_clr();
	     if(checkdt(dateCtrl))
	     {
		         var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		         var cmbOffice_code=document.getElementById("cmbOffice_code").value;
		         var TB_date=dateCtrl.value;
		         if(dateCtrl.value.length!=0)
		         {
			             var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
			          //   alert(url);
			             var req=getTransport();
			             req.open("GET",url,true); 
			             req.onreadystatechange=function()
			             {
			            	 	check_TB(req,dateCtrl);
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
								alert("test")
								
			                    // ('load_Voucher_No','null');                 //return true;
			            }
			            else if(flag=="failure")
				        {
				                dateCtrl.value="";
				                alert("Trial Balance Closed");//return false;//
				                dateCtrl.focus();
				                // document.getElementById("txtVoucher_No").value="";
				        }
			            else if(flag=="finyear")
			            {
			                    // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
				                dateCtrl.value="";
				                alert("Cash Book Control Not Found ");//return false;//
				                dateCtrl.focus();
				                //document.getElementById("txtVoucher_No").value="";     
			            }
			            dateCheck(dateCtrl);  
		        }
		 }
}



//////////////////////////////////////////////////////////////////////////////////////
// Remarks Length Check
//////////////////////////////////////////////////////////////////////////////////////

function Remarks_Lenght_Check(param, value)
{
   if(param=='remarks')
   {
     if (value.length >= 240 )
       {
         alert("Remarks should be less than 250 Characters ");
       }
   }
}


chequeRange=function(){	
	if((document.frmBankPay_PendingBill_create.txtCheque_DD[0].checked==true)&&(document.getElementById('txtCheque_DD_NO').value!="")){
		//alert("test "+document.frmBankPay_PendingBill_create.txtCheque_DD[0].checked);
		var accunitId=document.getElementById('cmbAcc_UnitCode').value;
		var officeId=document.getElementById('cmbOffice_code').value;
		var chequeNo=document.getElementById('txtCheque_DD_NO').value;
		var accountNo=document.getElementById('txtBankAccountNo').value;
                var txtCrea_date=document.getElementById('txtCrea_date').value;
                var dated=txtCrea_date.split("/");
                if(dated[2]==2011 || dated[2]>2011){
               // if(dated[1]==11 || dated[1]>11){
              
       
                        var url="../../../../../BankPay_PendingBill_Adj_memo_Create.view?Command=chequeRange&chequeNo="+chequeNo+"&accunitId="+accunitId+
                                        "&officeId="+officeId+"&accountNo="+accountNo;
                        var req=getTransport();
                        req.open("POST",url,true); 
                        alert("test->" ,+url)
                        req.onreadystatechange=function(){
                                processResponse(req);
                        };   
                        req.send(null);
		//}
                }
	}	
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
       // al
	if(flag=="fail"){
		alert("Cheque No does not exist Please Fill the Cheque No Details in the ChequeBook Master");
		document.getElementById('txtCheque_DD_NO').value="";
	}
}

function call_bankUpdate()
{
	var accunitId=document.getElementById('cmbAcc_UnitCode').value;
	var url="../../../../../BankPay_PendingBill_Adj_memo_Create.view?Command=bankBalUpdate&accunitId="+accunitId;
		
		var req=getTransport();
		req.open("POST",url,true); 
		alert("test->" ,+url)
		req.onreadystatechange=function(){
		processResponse_bk(req);
		};   
		req.send(null);
}

function call_a52()
{
	var accunitId=document.getElementById('cmbAcc_UnitCode').value;
	var cmbOffice_code=document.getElementById('cmbOffice_code').value;
	if(accunitId!=3){
	var url="../../../../../BankPay_PendingBill_Adj_memo_Create.view?Command=a52_verify&accunitId="+accunitId+"&cmbOffice_code="+cmbOffice_code;
		
		var req=getTransport();
		req.open("POST",url,true); 
		alert("test->" ,+url)
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
       document.frmBankPay_PendingBill_create.txtCrea_date.value=day+"/"+month+"/"+year;
       call_date(document.frmBankPay_PendingBill_create.txtCrea_date);
       document.getElementById("newDiv").style.display="block";
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