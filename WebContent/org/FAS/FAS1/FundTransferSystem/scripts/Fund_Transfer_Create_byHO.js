var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;
var job_flag;
var Bank_popup_flag;
/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////
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

//////////////////////////////////////////////  Revised AccHeadpopup ///////////////


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
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var txtModule_Type="MF015";
    var cr_dr_indi="CR";
    winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAcc_Bank_No.moveTo(250,250);  
    winAcc_Bank_No.focus();
}
function SubAccNopopup()
{
    Bank_popup_flag=false;
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
    var Office_code=document.getElementById("txtSub_Office_code").value;  
    var txtModule_Type="MF015";
    var cr_dr_indi="DR";
    
    var cmb_HO_acc_unitid;      // Extra field on 31stjan2007
    if(Office_code==5000)
    {
        Office_code=0;
        cmb_HO_acc_unitid=document.getElementById("cmb_HO_acc_unitid").value;
    }
    else
    {
        cmb_HO_acc_unitid=0;
    }
    //winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised.jsp?Office_code="+Office_code+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    // Extra one field unit ID, in case of Head office
    // here if office_id is 5000, we need to pass unit id, else we pass office id, so URL has both unit id and office id
    if(Office_code!=5110){
    winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised.jsp?Office_code="+Office_code+"&cmbAcc_UnitCode="+cmb_HO_acc_unitid+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
    }
    else
    {
    	winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised_5110.jsp?Office_code="+Office_code+"&cmbAcc_UnitCode="+cmb_HO_acc_unitid+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
    }
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
       
       Set_Default_ACHead(Acc_Head_Code);
       
       
       return true;
   }
  else if(Bank_popup_flag==false)
  {
       document.getElementById("txtDebitAccCode").value=Acc_Head_Code;
       document.getElementById("txtSubBankAccountNo").value=Bank_Acc_No;
       document.getElementById("txtSubBankId").value=bankid;
       document.getElementById("txtSubBranchId").value=br_id;
       document.getElementById("txtSubBankName").value=B_name;
       Bank_popup_flag="";
       return true;
   }
}

//////////////   FOR  JOB POPUP WINDOW //////////////////////


var winjob;
function jobpopup()
{
    if(winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
    }
    else
    {
        winjob=null;
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
}
function forChildOption()
{
      if (winjob && winjob.open && !winjob.closed) 
             winjob.officeSelection(true,true,true,false);
}
function doParentJob(jobid,deptid)
{
       if(deptid=='TWAD')
        {
            document.getElementById("txtSub_Office_code").value=jobid;
            doFunction('office_with_bank_betails','null');
        }
        else
        {
                alert('Please select an Office ');
                if (winjob && winjob.open && !winjob.closed) 
                {
                   winjob.resizeTo(500,500);
                   winjob.moveTo(250,250); 
                   winjob.focus();
                }
                return false;
        }
   
    return true;
}

window.onunload=function()
{
if (winAcc_Bank_No && winAcc_Bank_No.open && !winAcc_Bank_No.closed) winAcc_Bank_No.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
}


/////////////////////////////////////////////   doFunction()  /////////////////////////////////////////////////////

function doFunction(Command,param)
{   
        if(Command=="office_with_bank_betails")
        {   
            var oid=document.getElementById("txtSub_Office_code").value;
            var cmb_HO_acc_unitid;
            var txtModule_Type="MF015";
            var cr_dr_indi="DR";
            var cr_date=document.getElementById("txtCrea_date").value;
            
            if(oid!="")
            {
                if(oid==5000)
                {
                    cmb_HO_acc_unitid=document.getElementById("cmb_HO_acc_unitid").value;
                    
                    if(cmb_HO_acc_unitid=="")
                    {
                        alert("Select the A/c Unit of Head office ");
                        return;
                    }
                    
                }
                else
                {
                    cmb_HO_acc_unitid="";
                    document.getElementById("cmb_HO_acc_unitid").value="";
                }
                
              
                var url="../../../../../Fund_Transfer_Create_byHO.view?Command=office_with_bank_betails&oid="+oid+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi;
                url=url+"&cmb_HO_acc_unitid="+cmb_HO_acc_unitid+"&txtCrea_date="+cr_date;
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
          }
          else 
            alert("Enter the office");
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
           
            if(Command=="office_with_bank_betails")
            {
                office_with_bank_betails(baseResponse);
            }
        }
    }
}

///////////////////////////////////// loadoffice ///////
function office_with_bank_betails(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    //alert("Flag===>"+flag);
    
    if(flag=="success")
    {  
        var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
        var oname=baseResponse.getElementsByTagName("oname")[0].firstChild.nodeValue;
        var AC_HEAD_CODE=baseResponse.getElementsByTagName("AC_HEAD_CODE")[0].firstChild.nodeValue;
        var BANK_ID=baseResponse.getElementsByTagName("BANK_ID")[0].firstChild.nodeValue;
        var BRANCH_ID=baseResponse.getElementsByTagName("BRANCH_ID")[0].firstChild.nodeValue;
        var BANK_AC_NO=baseResponse.getElementsByTagName("BANK_AC_NO")[0].firstChild.nodeValue;
        var bk_br_city=baseResponse.getElementsByTagName("bk_br_city")[0].firstChild.nodeValue;
        
        document.getElementById("txtSub_Office_code").value=oid;
        document.getElementById("txtOfficeName").value=oname;
        document.getElementById("txtDebitAccCode").value=AC_HEAD_CODE;
        document.getElementById("txtSubBankAccountNo").value=BANK_AC_NO;
        document.getElementById("txtSubBankId").value=BANK_ID;
        document.getElementById("txtSubBranchId").value=BRANCH_ID;
        document.getElementById("txtSubBankName").value=bk_br_city;
    }
    else if(flag=="failure_bank")
    {
        var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
        var oname=baseResponse.getElementsByTagName("oname")[0].firstChild.nodeValue;
        alert("Bank details not found for Accounting unit of the given office");
        
        document.getElementById("txtSub_Office_code").value="";
        document.getElementById("txtOfficeName").value="";
        document.getElementById("txtDebitAccCode").value="";
        document.getElementById("txtSubBankAccountNo").value="";
        document.getElementById("txtSubBankId").value="";
        document.getElementById("txtSubBranchId").value="";
        document.getElementById("txtSubBankName").value="";
    }
    else if(flag=="failure_office")
    {
        var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
        alert("Office Id '"+oid+"' doesn't Exist");
        document.getElementById("txtSub_Office_code").value="";
        document.getElementById("txtOfficeName").value="";
        document.getElementById("txtDebitAccCode").value="";
        document.getElementById("txtSubBankAccountNo").value="";
        document.getElementById("txtSubBankId").value="";
        document.getElementById("txtSubBranchId").value="";
        document.getElementById("txtSubBankName").value="";
    }
    else if(flag=="failure_load")
    	{
    	var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
        alert("Date of Closure is not found");
        document.getElementById("txtSub_Office_code").value="";
        document.getElementById("txtOfficeName").value="";
        document.getElementById("txtDebitAccCode").value="";
        document.getElementById("txtSubBankAccountNo").value="";
        document.getElementById("txtSubBankId").value="";
        document.getElementById("txtSubBranchId").value="";
        document.getElementById("txtSubBankName").value="";
    	}
    else if(flag=="failure_date")
    	{
    	var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
        alert("Fund Transfer Creation Date is Greater than Date of Closure");
        document.getElementById("txtSub_Office_code").value="";
        document.getElementById("txtOfficeName").value="";
        document.getElementById("txtDebitAccCode").value="";
        document.getElementById("txtSubBankAccountNo").value="";
        document.getElementById("txtSubBankId").value="";
        document.getElementById("txtSubBranchId").value="";
        document.getElementById("txtSubBankName").value="";
    	}
    	
}
/////////////////////////////////////////////   load Table() /////////////////////////////////////////////////////

function loadTable(scod)
{
        com_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
        clearall();
        
        var r=document.getElementById(scod);
        var rcells=r.cells;
        
        try {document.getElementById("txtSub_Office_code").value=rcells.item(1).firstChild.value;}catch(e){}
        var nex=rcells.item(1).firstChild.nextSibling;
        if(nex.value=="")
        {
            document.getElementById("cmb_HO_acc_unitid").value="";
        }
        else
        {
            document.getElementById("cmb_HO_acc_unitid").value=nex.value;
        }
    
         
        try {document.getElementById("txtOfficeName").value=rcells.item(1).lastChild.nodeValue;}catch(e){}
       try{document.getElementById("txtDebitAccCode").value=rcells.item(2).firstChild.value;}catch(e){}
       var nex=rcells.item(2).firstChild.nextSibling  
       
        try{document.getElementById("txtSubBankAccountNo").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
        try{document.getElementById("txtSubBankId").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
        try{document.getElementById("txtSubBranchId").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
        try{document.getElementById("txtSubBankName").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
        if(nex.value=="C")
        document.frmFundTrs_Create_byHO.txtCheque_DD[0].checked=true;
        else if(nex.value=="D")
         document.frmFundTrs_Create_byHO.txtCheque_DD[1].checked=true;

        nex=nex.nextSibling;
        try{document.getElementById("txtCheque_DD_NO").value=nex.value;}catch(e){}
        try{document.getElementById("txtCheque_DD_date").value=rcells.item(3).firstChild.value;}catch(e){}
       try{document.getElementById("txtsub_Amount").value=rcells.item(4).firstChild.value;}catch(e){}
       var nex=rcells.item(4).firstChild.nextSibling  
       try{document.getElementById("txtParticular").value=nex.value;}catch(e){}
       
    document.frmFundTrs_Create_byHO.cmdupdate.style.display='block';
    document.frmFundTrs_Create_byHO.cmddelete.disabled=false;
    document.frmFundTrs_Create_byHO.cmdadd.style.display='none';
}

function chkOffceID()
{
    if(document.getElementById("txtSub_Office_code").value.length==0)
    {
    alert("Enter the Office id ");
    document.getElementById("txtSub_Office_code").focus();
    return false;
    }
    else
    return true;
}




/////////////////////////////////////////////   ADD & UPDATE & DELETE /////////////////////////////////////////////////////
function ADD_GRID()
{
    if(document.getElementById("txtSub_Office_code").value.length==0 || document.getElementById("txtSub_Office_code").value==0)
        {
        alert("Enter the Office id");
        document.getElementById("txtSub_Office_code").focus();
        return false;
        }
    if(document.getElementById("txtSub_Office_code").value==5000)
    {
        if(document.getElementById("cmb_HO_acc_unitid").value=="")
        {
            alert("select the A/c unit ID");
            return false;
        }
    }
    else
        document.getElementById("cmb_HO_acc_unitid").value="";
        
    if(document.getElementById("txtDebitAccCode").value.length==0 || document.getElementById("txtDebitAccCode").value==0)
        {
        alert("Select the Debit A/C code");
        document.getElementById("txtDebitAccCode").focus();
        return false;
        }
    if(document.getElementById("txtSubBankAccountNo").value.length==0 || document.getElementById("txtSubBankAccountNo").value==0)
    {
    alert("Enter the Bank Account Number");
    document.getElementById("txtSubBankAccountNo").focus();
    return false;
    }
     if(document.getElementById("txtSubBankId").value.length==0 || document.getElementById("txtSubBankId").value==0)
    {
    alert("Enter the Bank Id has not populated");
    document.getElementById("txtSubBankId").focus();
    return false;
    }
    if(document.getElementById("txtSubBranchId").value.length==0 || document.getElementById("txtSubBranchId").value==0)
    {
    alert("Enter the Branch Id has not populated");
    document.getElementById("txtSubBranchId").focus();
    return false;
    }
    
    if(document.getElementById("txtSub_Office_code").value!=6777)
    {
    	if(document.getElementById("cmb_HO_acc_unitid").value!=6)
    	{
    	if(document.getElementById("txtSub_Office_code").value!=5110)
    		{
    		if(document.getElementById("txtSub_Office_code").value!=5984)
    		{
        		if(document.getElementById("txtSub_Office_code").value!=5983)
        		{
        			if(document.getElementById("txtSub_Office_code").value!=5042)
            		{
        				if(document.getElementById("txtSub_Office_code").value!=5101)
                		{
        					if(document.getElementById("txtSub_Office_code").value!=5091)
                    		{
					    		if(document.getElementById("txtCash_Acc_code").value!=822111)
					    		{
					    			if(document.getElementById("txtCash_Acc_code").value!=820611)
					    			{
					    				if(document.getElementById("txtCash_Acc_code").value!=822113)
						    			{
					    					if(document.getElementById("txtCash_Acc_code").value!=820651)
							    			{ 					    						
							    			
					    				   if(document.getElementById("txtBankAccountNo").value!=800710210000006)
						    		       {
						    				if(document.getElementById("txtBankAccountNo").value!=800710210000012)
						    				{
												//MNP - SNA account [19-02-2024]
												if(document.getElementById("txtBankAccountNo").value!=42057477791){
												//NABARD 
												if(document.getElementById("txtBankAccountNo").value!=110128662919){
												//NIDA
												if(document.getElementById("txtBankAccountNo").value!=110128675743){
													
						    					if(document.getElementById("txtBankAccountNo").value!=950279626)
							    				{
						    					if(document.getElementById("txtBankAccountNo").value!=332502010091046)
							    				{
							    					var ccode=document.getElementById("txtCash_Acc_code").value;
							    					var credit_code=ccode.substr(0,4);
							    					var credit_code1=ccode.substr(4,6);
							    					var dcode=document.getElementById("txtDebitAccCode").value;
							    					var debit_code=dcode.substr(0,4);
							    					var debit_code1=dcode.substr(4,6);
							    				/*	alert("CCode>1>>"+credit_code);
							    					alert("DCode>2>>"+debit_code);*/
							    					const headCodes = [
820309,820310,820311,820312,820313,820314,820315,820316,820317,
820318,820319,820320,820321,820322,820323,820324,820325,820326,
820328,820327,820303,820302,820329,820330,820609,820610,820611,
820613,820614,820615,820616,820617,820619,820714,820602,820603,
822109,822110,822111,822113,822114,822102,822103,820209,820210,
820211,820212,820213,820203,820202,820709,820710,820711,820712,
820713,820715,820716,820717,820718,820719,820721,820722,820740,
820723,820703,820702,820802,820804,820803,821110,821101,821209,
821109,820612,820618
];
	
							    						/*if((ccode==820618 || ccode==820612 || ccode==821109 || ccode==822112)){
															
														}	*/		
														if (headCodes.includes(Number(ccode))) {
    // do nothing
}
							
												  else if(((credit_code==debit_code)  )  || (credit_code1==19 && debit_code1==51 ))
									//				if(true)					
												   {
//													   if(document.getElementById("txtBankAccountNo").value==10640563006)
//														   {
//														   if(document.getElementById("txtBankAccountNo").value==0911201006039)
//															   {
//															   if(document.getElementById("txtBankAccountNo").value==332501010044032)
//																   {
//																   if(document.getElementById("txtBankAccountNo").value==443380146)
//																   {
//																	   if(document.getElementById("txtBankAccountNo").value==800720100003570)
//																	   {   
//																   
//																		   if(credit_code1==02 && debit_code1==05)
//																		   {
//														   
//																		   }
//																		   else
//																			   {
//																			   alert("Debit A/c Doesn't match with Credit A/c");
//																			   document.getElementById("txtSub_Office_code").value="";
//																			   document.getElementById("txtDebitAccCode").value="";
//																			   document.getElementById("txtSubBankAccountNo").value="";
//																			   document.getElementById("txtSubBankName").value="";
//																			   return false;
//																			   }
//																	   }
//																   }
//														   }
//															   }
//														   }
												   }
												   else
												   {
												   alert("Debit A/c Doesn't match with Credit A/c1");
												   document.getElementById("txtSub_Office_code").value="";
												   document.getElementById("txtDebitAccCode").value="";
												   document.getElementById("txtSubBankAccountNo").value="";
												   document.getElementById("txtSubBankName").value="";
												   return false;
												   }
							    				}
						    				}
						    			}
						    		  }
						    		  }
					    			}
					    		}}
						    	}
					    	  }
                    		}
                		}
            		}
        		}
    		}
    	}
	}
  }
    
    }
//    alert("*****"+document.getElementById("txtSub_Office_code").value);
//    if(document.getElementById("txtSub_Office_code").value!=6777)
//    {
//    	var cr_code=document.getElementById("txtCash_Acc_code").value;
//    	alert("cr_code>>>>>"+cr_code);
//    	var crcode=cr_code.substr(0,2);
//		var credit_code1=cr_code.substr(4,6);
//    	alert("CR_Code>>1>>"+crcode);
//    	alert("CR_Code>>2>>"+credit_code1);
//    }
    

    
    
if(document.frmFundTrs_Create_byHO.txtCheque_DD[2].checked == false)
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
        
        
        
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            document.getElementById("txtsub_Amount").focus();
            return false;    
        }
      
      
        var t=0;
        var temp;
        var items=new Array();
        
        items[0]=document.getElementById("txtSub_Office_code").value;
        items[1]=document.getElementById("cmb_HO_acc_unitid").value;
        
        if(document.getElementById("cmb_HO_acc_unitid").value!="")
        {
            var x=document.getElementById("cmb_HO_acc_unitid");
            temp=x.options[x.selectedIndex].text; 
            items[2]=document.getElementById("txtOfficeName").value+"-"+temp;
        }
        else
        {
            temp="";
            items[2]=document.getElementById("txtOfficeName").value;
        }

        
        items[3]=document.getElementById("txtDebitAccCode").value;
        items[4]=document.getElementById("txtSubBankAccountNo").value;
        items[5]=document.getElementById("txtSubBankId").value;
        items[6]=document.getElementById("txtSubBranchId").value;
        items[7]=document.getElementById("txtSubBankName").value;
        
        if(document.frmFundTrs_Create_byHO.txtCheque_DD[0].checked==true)
          items[8]=document.frmFundTrs_Create_byHO.txtCheque_DD[0].value;
        else if(document.frmFundTrs_Create_byHO.txtCheque_DD[1].checked==true)
          items[8]=document.frmFundTrs_Create_byHO.txtCheque_DD[1].value;
        else if(document.frmFundTrs_Create_byHO.txtCheque_DD[2].checked==true)   
          items[8]=document.frmFundTrs_Create_byHO.txtCheque_DD[2].value;
          
        items[9]=document.getElementById("txtCheque_DD_NO").value;
        items[10]=document.getElementById("txtCheque_DD_date").value;
        items[11]=document.getElementById("txtsub_Amount").value;
        items[12]=document.getElementById("txtParticular").value;
        
        var tbody=document.getElementById("grid_body");
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

                  var Sub_Office_code=document.createElement("input");
                  Sub_Office_code.type="hidden";
                  Sub_Office_code.name="Sub_Office_code";
                  Sub_Office_code.value=items[0];
                  cell2.appendChild(Sub_Office_code);
                  
                  var HO_Unit_ID=document.createElement("input");
                  HO_Unit_ID.type="hidden";
                  HO_Unit_ID.name="HO_Unit_ID";
                  HO_Unit_ID.value=items[1];
                  cell2.appendChild(HO_Unit_ID);
                  
                  var currentText=document.createTextNode(items[2]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
                   
             cell2=document.createElement("TD");
                  var Sub_Acc_Head_Code=document.createElement("input");
                  Sub_Acc_Head_Code.type="hidden";
                  Sub_Acc_Head_Code.name="Sub_Acc_Head_Code";
                  Sub_Acc_Head_Code.value=items[3];
                  cell2.appendChild(Sub_Acc_Head_Code);
                  
                   var Sub_Bank_Acc_No=document.createElement("input");
                  Sub_Bank_Acc_No.type="hidden";
                  Sub_Bank_Acc_No.name="Sub_Bank_Acc_No";
                  Sub_Bank_Acc_No.value=items[4];
                  cell2.appendChild(Sub_Bank_Acc_No);
                  
                  var Sub_Bank_Id=document.createElement("input");
                  Sub_Bank_Id.type="hidden";
                  Sub_Bank_Id.name="Sub_Bank_Id";
                  Sub_Bank_Id.value=items[5];
                  cell2.appendChild(Sub_Bank_Id);
                  
                  var Sub_Branch_Id=document.createElement("input");
                  Sub_Branch_Id.type="hidden";
                  Sub_Branch_Id.name="Sub_Branch_Id";
                  Sub_Branch_Id.value=items[6];
                  cell2.appendChild(Sub_Branch_Id);
                  
                  var Sub_Bank_Name=document.createElement("input");
                  Sub_Bank_Name.type="hidden";
                  Sub_Bank_Name.name="Sub_Bank_Name";
                  Sub_Bank_Name.value=items[7];
                  cell2.appendChild(Sub_Bank_Name);
                  
                 var Cheque_DD=document.createElement("input");
                  Cheque_DD.type="hidden";
                  Cheque_DD.name="Cheque_DD";
                  
              //    alert(items[8]);
                  
                  Cheque_DD.value=items[8];
                  cell2.appendChild(Cheque_DD);


                 var Cheque_DD_NO=document.createElement("input");
                  Cheque_DD_NO.type="hidden";
                  Cheque_DD_NO.name="Cheque_DD_NO";
                  
               //   alert(items[9]);
                  
                  Cheque_DD_NO.value=items[9];
                  cell2.appendChild(Cheque_DD_NO);
                  var currentText=document.createTextNode(items[9]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
            cell2=document.createElement("TD");
                 var Cheque_DD_date=document.createElement("input");
                  Cheque_DD_date.type="hidden";
                  Cheque_DD_date.name="Cheque_DD_date";
                   
               //   alert(items[10]);  
                  
                  Cheque_DD_date.value=items[10];
                  cell2.appendChild(Cheque_DD_date);
                  var currentText=document.createTextNode(items[10]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
              cell2=document.createElement("TD");
                 var sl_amt=document.createElement("input");
                  sl_amt.type="hidden";
                  sl_amt.name="sl_amt";
                  sl_amt.value=items[11];
                  cell2.appendChild(sl_amt);

                  var particular=document.createElement("input");           // Particulars Added to grid b4 the Amount hidden Node but after  amount hidden box    
                  particular.type="hidden";
                  particular.name="particular";
                  particular.value=items[12];
            //    particular.style.display='none';
                  cell2.appendChild(particular);

                  var currentText=document.createTextNode(items[11]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
       
        tbody.appendChild(mycurrent_row);
        //clear_main_fields();
}
function clear_main_fields()
{
        document.getElementById("txtSub_Office_code").value=oid;
        document.getElementById("txtOfficeName").value=oname;
        document.getElementById("txtDebitAccCode").value="";
        document.getElementById("txtSubBankAccountNo").value="";
        document.getElementById("txtSubBankId").value="";
        document.getElementById("txtSubBranchId").value="";
        document.getElementById("txtSubBankName").value="";
}


function update_GRID()
{      
   if(document.getElementById("txtSub_Office_code").value.length==0 || document.getElementById("txtSub_Office_code").value==0)
    {
    alert("Enter the Office id");
    document.getElementById("txtSub_Office_code").focus();
    return false;
    }
    if(document.getElementById("txtSub_Office_code").value==5000)
    {
        if(document.getElementById("cmb_HO_acc_unitid").value=="")
        {
            alert("select the A/c unit ID");
            return false;
        }
    }
    else
        document.getElementById("cmb_HO_acc_unitid").value="";
        
    if(document.getElementById("txtDebitAccCode").value.length==0 || document.getElementById("txtDebitAccCode").value==0)
        {
        alert("Select the Debit A/C code");
        document.getElementById("txtDebitAccCode").focus();
        return false;
        }
    if(document.getElementById("txtSubBankAccountNo").value.length==0 || document.getElementById("txtSubBankAccountNo").value==0)
    {
    alert("Enter the Bank Account Number");
    document.getElementById("txtSubBankAccountNo").focus();
    return false;
    }
     if(document.getElementById("txtSubBankId").value.length==0 || document.getElementById("txtSubBankId").value==0)
    {
    alert("Enter the Bank Id has not populated");
    document.getElementById("txtSubBankId").focus();
    return false;
    }
    if(document.getElementById("txtSubBranchId").value.length==0 || document.getElementById("txtSubBranchId").value==0)
    {
    alert("Enter the Branch Id has not populated");
    document.getElementById("txtSubBranchId").focus();
    return false;
    }
    
    var ccode=document.getElementById("txtCash_Acc_code").value;
    var credit_code=ccode.substr(0,4);
    var dcode=document.getElementById("txtDebitAccCode").value;
    var debit_code=dcode.substr(0,4);
   if(credit_code==debit_code)
   {
   }
   else
   {
   alert("Debit A/c Doesn't match with Credit A/c");
   
   document.getElementById("txtSub_Office_code").value="";
   document.getElementById("txtDebitAccCode").value="";
   document.getElementById("txtSubBankAccountNo").value="";
   document.getElementById("txtSubBankName").value="";
   return false;
   } 
   
if(document.frmFundTrs_Create_byHO.txtCheque_DD[2].checked == false)
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
        
        
        
        
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            document.getElementById("txtsub_Amount").focus();
            return false;    
        }
       
              
        var t=0;
        var temp;
        var items=new Array();
        items[0]=document.getElementById("txtSub_Office_code").value;
        items[1]=document.getElementById("cmb_HO_acc_unitid").value;
        
        if(document.getElementById("cmb_HO_acc_unitid").value!="")
        {
            var x=document.getElementById("cmb_HO_acc_unitid");
            temp=x.options[x.selectedIndex].text; 
            items[2]=document.getElementById("txtOfficeName").value+"-"+temp;
        }
        else
        {
            temp="";
            items[2]=document.getElementById("txtOfficeName").value;
        }
        
        
        items[3]=document.getElementById("txtDebitAccCode").value;
        items[4]=document.getElementById("txtSubBankAccountNo").value;
        items[5]=document.getElementById("txtSubBankId").value;
        items[6]=document.getElementById("txtSubBranchId").value;
        items[7]=document.getElementById("txtSubBankName").value;
        if(document.frmFundTrs_Create_byHO.txtCheque_DD[0].checked==true)
          items[8]=document.frmFundTrs_Create_byHO.txtCheque_DD[0].value;
        else if(document.frmFundTrs_Create_byHO.txtCheque_DD[1].checked==true)
          items[8]=document.frmFundTrs_Create_byHO.txtCheque_DD[1].value;
          
        items[9]=document.getElementById("txtCheque_DD_NO").value;
        items[10]=document.getElementById("txtCheque_DD_date").value;
        items[11]=document.getElementById("txtsub_Amount").value;
        items[12]=document.getElementById("txtParticular").value;
        
       
        var r=document.getElementById(com_id);
        var rcells=r.cells;
        
        rcells.item(1).firstChild.value=items[0];
        var nex_cell=rcells.item(1).firstChild.nextSibling;
        nex_cell.value=items[1];
        rcells.item(1).lastChild.nodeValue=items[2];
        rcells.item(2).firstChild.value=items[3];
        var nex_cell=rcells.item(2).firstChild.nextSibling;
        nex_cell.value=items[4];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[5];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[6];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[7];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[8];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[9];
        rcells.item(2).lastChild.nodeValue=items[9];
        rcells.item(3).firstChild.value=items[10];
        rcells.item(3).lastChild.nodeValue=items[10];
        rcells.item(4).firstChild.value=items[11];
        var nex_cell=rcells.item(4).firstChild.nextSibling;
        nex_cell.value=items[12];
        rcells.item(4).lastChild.nodeValue=items[11];
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
    
        document.getElementById("txtSub_Office_code").value="";
        document.getElementById("txtOfficeName").value="";
        document.getElementById("txtDebitAccCode").value="";
        document.getElementById("txtSubBankAccountNo").value="";
        document.getElementById("txtSubBankId").value="";
        document.getElementById("txtSubBranchId").value="";
        document.getElementById("txtSubBankName").value="";
        document.frmFundTrs_Create_byHO.txtCheque_DD[0].checked==true
        document.getElementById("txtCheque_DD_NO").value="";
        document.getElementById("txtCheque_DD_date").value="";
        document.getElementById("txtsub_Amount").value="";
        document.getElementById("txtParticular").value=""; 
        
 document.frmFundTrs_Create_byHO.cmdadd.style.display='block';
 document.frmFundTrs_Create_byHO.cmdupdate.style.display='none';
 document.frmFundTrs_Create_byHO.cmddelete.disabled=true;
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
    document.getElementById("txtReferenceNo").value="";
    document.getElementById("txtReferenceDate").value="";
    document.getElementById("txtAmount").value="";
    document.getElementById("txtRemarks").value="";
    
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
if(document.getElementById("txtCash_Acc_code").value.length==0  || document.getElementById("txtCash_Acc_code").value==0)
{
    alert("Enter the Cash A/c Code");
    //document.getElementById("txtCash_Acc_code").focus();
    return false;
}

if(document.getElementById("txtBankId").value.length==0  || document.getElementById("txtBankId").value==0)
{
    alert("Bank Id not populated in General Details");
    //document.getElementById("txtAmount").focus();
    return false;    
}

if(document.getElementById("txtBranchId").value.length==0  || document.getElementById("txtBranchId").value==0)
{
    alert("Branch Id not populated in General Details");
    //document.getElementById("txtAmount").focus();
    return false;    
}
if(document.getElementById("txtBankAccountNo").value.length==0  || document.getElementById("txtBankAccountNo").value==0)
{
    alert("Bank Account Number not populated for Selected Office");
    //document.getElementById("txtAmount").focus();
    return false;    
}
if(document.getElementById("txtAmount").value.length==0)
{
    alert("Enter the Total Amount in General Details");
    //document.getElementById("txtAmount").focus();
    return false;    
}

        /*
        if(document.getElementById("txtReferenceNo").value.length==0)
        {
            alert("Enter the Reference Number");
            //document.getElementById("txtAmount").focus();
            return false;    
        }
        if(document.getElementById("txtReferenceDate").value.length==0)
        {
            alert("Enter the Reference Date");
            //document.getElementById("txtAmount").focus();
            return false;    
        }
         */
if(tbody.rows.length==0)
{
    alert("Enter the Details Part");
    //document.getElementById("txtAmount").focus();
    return false; 
}
if(tbody.rows.length>0)
{
        var check_amt=0;
        var chkCR_DB=0;
        rows=tbody.getElementsByTagName("tr");
        for(i=0;i<rows.length;i++)
        {
            var cells=rows[i].cells;
           // if(cells.item(2).lastChild.nodeValue=='CR')
           //chkCR_DB=parseFloat(chkCR_DB) + parseFloat(cells.item(7).lastChild.nodeValue);    //check_amt=parseFloat(check_amt) - parseFloat(cells.item(7).lastChild.nodeValue);  // ** removed bcoz just we need to check CR & DR equal or not
           // else
            check_amt=parseFloat(check_amt) + parseFloat(cells.item(4).lastChild.nodeValue);
           //alert(check_amt);
        }
      /*  
      if(parseFloat(chkCR_DB)!=parseFloat(check_amt))
        {
            alert("Credit and Debit Amount doesn't Tally in details.. Difference " +(parseFloat(chkCR_DB)-parseFloat(check_amt)));
            return false;
        }
        */
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
             cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             cmbOffice_code=document.getElementById("cmbOffice_code").value;
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
         cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
         cmbOffice_code=document.getElementById("cmbOffice_code").value;
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
        //doFunction('load_Voucher_No','null');
    }
    else
    {
      document.getElementById("txtVoucher_No").value="";
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
                 //doFunction('load_Voucher_No','null');                 //return true;
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
                    dateCtrl.focus();
                    document.getElementById("txtVoucher_No").value="";
               }
                else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");//return false;//
                    dateCtrl.focus();
                    document.getElementById("txtVoucher_No").value="";     
               }
            dateCheck(dateCtrl); 
        }
    }
}

///////////////////////////////////////////Date validation on 04-01-2018 //////////////////////

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
//------------------------------------------------------------------------------------------------------


function Set_Default_ACHead(Acc_Head_Code)
{
       
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
        var url="../../../../../Set_Default_ACHead_for_FT_ByHO.kv?Acc_UnitCode="+cmbAcc_UnitCode+"&Acc_Head_Code="+Acc_Head_Code;
        var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
                 handleResponse_Set_Acc(req);
            }   
            req.send(null);
       
        
}
chequeRange=function(){	
	if((document.frmFundTrs_Create_byHO.txtCheque_DD[0].checked==true)&&(document.getElementById('txtCheque_DD_NO').value!="")){
		//alert("test "+document.frmBankPay_PendingBill_create.txtCheque_DD[0].checked);
		var accunitId=document.getElementById('cmbAcc_UnitCode').value;
		//var officeId=document.getElementById('cmbOffice_code').value;
		var chequeNo=document.getElementById('txtCheque_DD_NO').value;
		var accountNo=document.getElementById('txtBankAccountNo').value;
		var url="../../../../../BankPay_PendingBill_Create.view?Command=chequeRange&chequeNo="+chequeNo+"&accunitId="+accunitId+
				"&accountNo="+accountNo;
        var req=getTransport();
        req.open("POST",url,true); 
        req.onreadystatechange=function(){
        	processResponse(req);
        };   
        req.send(null);
		
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
function chequeRangeResponse(responses){
	var flag=responses.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="fail"){
		alert("Cheque No does not exist ");
		document.getElementById('txtCheque_DD_NO').value="";
	}
}