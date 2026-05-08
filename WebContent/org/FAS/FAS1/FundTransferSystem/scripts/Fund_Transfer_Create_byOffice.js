
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

  /*  if(document.frmFundTrs_Create_byOffice.radRemitType[0].checked==true)
    var unspent_OR_col='OPR';
    else
    var unspent_OR_col='COL';
    */    
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
    var cmbAcc_UnitCode=5;          //// This is the Accounting Unit Id of Banking section ( Head Office )
    var txtModule_Type="MF015";
    var cr_dr_indi="DR";
    var radRemitType1=document.getElementById("radRemitType").value;
    //if(document.frmFundTrs_Create_byOffice.radRemitType[0].checked==true)
    if(radRemitType1=="U")
    var unspent_OR_col='OPR';
    else
    var unspent_OR_col='COL';
   
    
    
    
var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    var txtSubBankId=document.getElementById("txtSubBankId").value;
  //  alert("txtSubBankId::"+txtSubBankId);
     winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi+"&unspent_OR_col="+unspent_OR_col+"&Office_code="+cmbOffice_code+"&txtSubBankId="+txtSubBankId,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    
    //winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised.jsp?Office_code="+Office_code+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi+"&unspent_OR_col="+unspent_OR_col,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAcc_Bank_No.moveTo(250,250);  
    winAcc_Bank_No.focus();
    
}

function doParentAcc_NO(Acc_Head_Code,Bank_Acc_No,bankid,br_id,B_name)
{
//	alert("end");
   if(Bank_popup_flag==true)
   {
       document.getElementById("txtCash_Acc_code").value=Acc_Head_Code;
       document.getElementById("txtBankAccountNo").value=Bank_Acc_No;
       document.getElementById("txtBankId").value=bankid;
       document.getElementById("txtBranchId").value=br_id;
       document.getElementById("txtBankName").value=B_name;
       Bank_popup_flag="";
       doFunction('unspent_OR_col_based_bank',null);
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
  // doFunction('unspent_OR_col_based_bank',null);
}
 
window.onunload=function()
{
if (winAcc_Bank_No && winAcc_Bank_No.open && !winAcc_Bank_No.closed) winAcc_Bank_No.close();
}


function doFunction(Command,param)
{  
	
        if(Command=="unspent_OR_col_based_bank")
        {   
           // Load_Office_Bank_Details(); 
            
            document.getElementById("txtDebitAccCode").value="";
            document.getElementById("txtSubBankAccountNo").value="";
            document.getElementById("txtSubBankId").value="";
            document.getElementById("txtSubBranchId").value="";
            document.getElementById("txtSubBankName").value="";
            
            /**
             *  This is the Accounting Unit Id of Banking section ( Head Office )
             */
            var unitID=5;        
          // var unitID=document.getElementById("cmbAcc_UnitCode").value;
          // alert(unitID);
            var txtModule_Type="MF015";
            var cr_dr_indi="DR";
            
            if(document.getElementById("txtCash_Acc_code").value.length==0  || document.getElementById("txtCash_Acc_code").value==0)
            {
//                alert("Select the Credit A/c Code");
                document.getElementById("radRemitType").focus();
                return false;
            }
            
            var main_AHCODE=document.getElementById("txtCash_Acc_code").value;
            main_AHCODE=main_AHCODE.substring(0,4);          // Take the first 4 digit
            
            var radRemitType1=document.getElementById("radRemitType").value;
            //radRemitType1
            
            if(radRemitType1=="U")
            var unspent_OR_col='OPR';
            else if(radRemitType1=="C")
            var unspent_OR_col='COL';
            else if(radRemitType1=="NM")
            	var unspent_OR_col="NRDWP_Main";
            else if(radRemitType1=="NS")
            	var unspent_OR_col="NRDWP_Support";
            else if(radRemitType1=="UNM")
            	var unspent_OR_col="NRDWP_Main1";
            else if(radRemitType1=="UNS")
            	var unspent_OR_col="NRDWP_Support1";
            else if(radRemitType1=="UNC")
            	var unspent_OR_col="NRDWP_Calamity";
            else if(radRemitType1=="UNAEJE")
            	var unspent_OR_col="NRDWP_AEJE";
            else if(radRemitType1=="FDW")
            	var unspent_OR_col="FDW";
            else if(radRemitType1=="NRDWP-WQM-SP")
            	var unspent_OR_col="NRDWP-WQM-SP";
            else if(radRemitType1=="WATCHARGEREV_Hogenakkal")
            	var unspent_OR_col="WATCHARGEREV_Hogenakkal";            
            else if(radRemitType1=="WATCHARGEREV")
            	var unspent_OR_col="WATCHARGEREV";
            else if(radRemitType1=="NONWATCHARGEREV")
            	var unspent_OR_col="NONWATCHARGEREV";
            else if(radRemitType1=="LB100PCNTCONTRIB")
            	var unspent_OR_col="LB100PCNTCONTRIB";
            else if(radRemitType1=="UIDDSMT")
            	var unspent_OR_col="UIDDSMT";
            else if(radRemitType1=="JICA")
            	var unspent_OR_col="JICA";
            else if(radRemitType1=="KFW")
            	var unspent_OR_col="KFW";
            else if(radRemitType1=="FieldKit")
            	var unspent_OR_col="FieldKit";
            else if(radRemitType1=="FDW from Collection")
            	var unspent_OR_col="FDW from Collection";
            else if(radRemitType1=="Security Deposit")
            	var unspent_OR_col="Security Deposit";
            
            
           /* 
            if(document.frmFundTrs_Create_byOffice.radRemitType[0].checked==true)
            var unspent_OR_col='OPR';
            else if(document.frmFundTrs_Create_byOffice.radRemitType[1].checked==true)
            var unspent_OR_col='COL';
            else if(document.frmFundTrs_Create_byOffice.radRemitType[2].checked==true)
            	var unspent_OR_col="NRDWP_Main";
            else
            	var unspent_OR_col="NRDWP_Support";*/
            
            
            
            
            var url="../../../../../Fund_Transfer_Create_byOffice.view?Command=unspent_OR_col_based_bank&unitID="+unitID+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi+"&unspent_OR_col="+unspent_OR_col+"&main_AHCODE="+main_AHCODE;
           //alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            } ;  
              req.send(null);
        }
}

function goEntry()
{ 
	 cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode");
     cmbOffice_code=document.getElementById("cmbOffice_code");
    var unit_id=cmbAcc_UnitCode.options[cmbAcc_UnitCode.selectedIndex].text;
    var office_id=cmbOffice_code.options[cmbOffice_code.selectedIndex].text;
	winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/FundTransferSystem/jsps/Fund_Trnf_Crte_byOff_Entry.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode.value+"&cmbOffice_code="+cmbOffice_code.value+"&unit_id="+unit_id+"&office_id="+office_id,"Entry FDW DATA","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAcc_Bank_No.moveTo(250,250);  
    winAcc_Bank_No.focus();

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
           
            if(Command=="unspent_OR_col_based_bank")
            {
                unspent_OR_col_based_bank(baseResponse);
            }
            
        }
    }
}


function unspent_OR_col_based_bank(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    if(flag=="success")
    {  
        var AC_HEAD_CODE=baseResponse.getElementsByTagName("AC_HEAD_CODE")[0].firstChild.nodeValue;
        var BANK_ID=baseResponse.getElementsByTagName("BANK_ID")[0].firstChild.nodeValue;
        var BRANCH_ID=baseResponse.getElementsByTagName("BRANCH_ID")[0].firstChild.nodeValue;
        var BANK_AC_NO=baseResponse.getElementsByTagName("BANK_AC_NO")[0].firstChild.nodeValue;
        var bk_br_city=baseResponse.getElementsByTagName("bk_br_city")[0].firstChild.nodeValue;
        
        document.getElementById("txtDebitAccCode").value=AC_HEAD_CODE;
        document.getElementById("txtSubBankAccountNo").value=BANK_AC_NO;
        document.getElementById("txtSubBankId").value=BANK_ID;
        document.getElementById("txtSubBranchId").value=BRANCH_ID;
        document.getElementById("txtSubBankName").value=bk_br_city;
       
    }
    else if(flag=="failure_bank")
    {
        alert("Bank details not found for Accounting Unit of Banking section");
        document.getElementById("txtDebitAccCode").value="";
        document.getElementById("txtSubBankId").value="";
        document.getElementById("txtSubBankAccountNo").value="";
        document.getElementById("txtSubBranchId").value="";
        document.getElementById("txtSubBankName").value="";
       
    }
    else if(flag=="failure")
    {
        alert("Failure to retrive values");
    }

   
}



/**
 *  Load Office Bank Details 
 */

function  Load_Office_Bank_Details()
{  
	//alert(" Load_Office_Bank_Details ");
            /** Clear All the Fields */
            document.getElementById("txtCash_Acc_code").value="";            
            document.getElementById("txtBankAccountNo").value="";
            document.getElementById("txtBankId").value="";
            document.getElementById("txtBranchId").value="";
            document.getElementById("txtBankName").value="";
            
            /** Get Login Accouting Unit ID */
            var unitID=document.getElementById("cmbAcc_UnitCode").value;    
            
            /** To Check whether Unspent or collection has been selected */
           /* if(document.frmFundTrs_Create_byOffice.radRemitType[0].checked==true)
            var unspent_OR_col='OPR';
            else if(document.frmFundTrs_Create_byOffice.radRemitType[1].checked==true)
            var unspent_OR_col='COL';
            else  if(document.frmFundTrs_Create_byOffice.radRemitType[2].checked==true)
            	 var unspent_OR_col='NRDWP-Main';
            else
            	var unspent_OR_col='NRDWP-Support';*/
            
            
            var radRemitType1=document.getElementById("radRemitType").value;
            //radRemitType1
           // alert(" radRemitType1 "+radRemitType1);
            if(radRemitType1=="U")
            var unspent_OR_col='OPR';
            else if(radRemitType1=="C")
            var unspent_OR_col='COL';
            else if(radRemitType1=="NM")
            	var unspent_OR_col="NRDWP-Main";
            else if(radRemitType1=="NS")
            	var unspent_OR_col="NRDWP-Support";
            else if(radRemitType1=="UNM")
            	var unspent_OR_col="NRDWP-Main";
            else if(radRemitType1=="UNS")
            	var unspent_OR_col="NRDWP-Support";
            else if(radRemitType1=="UNC")
            	var unspent_OR_col="NRDWP-Calamity";
            else if(radRemitType1=="UNAEJE")
            	var unspent_OR_col="NRDWP-AEJE";
            else if(radRemitType1=="FDW")
            	var unspent_OR_col="FDW";
            else if(radRemitType1=="NRDWP-WQM-SP")
            	var unspent_OR_col="NRDWP-WQM-SP";
            else if(radRemitType1=="WATCHARGEREV_Hogenakkal")
            	var unspent_OR_col="WATCHARGEREV_Hogenakkal";
            else if(radRemitType1=="WATCHARGEREV")
            	var unspent_OR_col="WATCHARGEREV";
            else if(radRemitType1=="NONWATCHARGEREV")
            	var unspent_OR_col="NONWATCHARGEREV";
            else if(radRemitType1=="LB100PCNTCONTRIB")
            	var unspent_OR_col="LB100PCNTCONTRIB";
            else if(radRemitType1=="UIDDSMT")
            	var unspent_OR_col="UIDDSMT";
            else if(radRemitType1=="JICA")
            	var unspent_OR_col="JICA";
            else if(radRemitType1=="KFW")
            	var unspent_OR_col="KFW";
            else if(radRemitType1=="FieldKit")
            	var unspent_OR_col="FieldKit";
            else if(radRemitType1=="FDW from Collection")
            	var unspent_OR_col="FDW from Collection";
            else if(radRemitType1=="Security Deposit")
            	var unspent_OR_col="Security Deposit";
            
            
            var url="../../../../../Fund_Transfer_Create_byOffice.view?Command=Load_Office_Bank_Details&unitID="+unitID+"&unspent_OR_col="+unspent_OR_col;
            alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse_office(req);
            };   
            req.send(null);

               
}


function handleResponse_office(req)
{  
	
    if(req.readyState==4)
    {
        if(req.status==200)
        { 
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           //alert(Command);
               
            
            if(Command=="unspent_OR_col_based_office")
            {
                unspent_OR_col_based_office(baseResponse);
            }else if(Command=="Load_Scheme"){
            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	 if(flag=="success"){
         	   var leng=baseResponse.getElementsByTagName("AC_HEAD_CODE");
         	   var fdwcmb=document.getElementById("txtFDW");
         	   var option=document.createElement("option");
         	   option.value=0;
         	   option.text='--Select--';
         	   fdwcmb.appendChild(option);
         	   for(var j=0;j<leng.length;j++){
         		   var AC_HEAD_CODE=baseResponse.getElementsByTagName("AC_HEAD_CODE")[j].firstChild.nodeValue;
         	        var BANK_ID=baseResponse.getElementsByTagName("BANK_ID")[j].firstChild.nodeValue;
         	      
         	        
         	        var option=document.createElement("option");
         	        option.value=0;
         	 	   option.value=BANK_AC_NO+"-"+BANK_ID+"-"+BRANCH_ID+"-"+AC_HEAD_CODE+"-"+bk_br_city;
         	 	  option.text=BANK_AC_NO+"-"+bk_br_city;
         	 	  fdwcmb.appendChild(option);
         	   }
         	   }
         	    
            }
            
        }
    }
}

function loadAcc_det(val){
	  
    var AC_HEAD_CODE=val.split("-")[3];
    var BANK_ID=val.split("-")[1];
    var BRANCH_ID=val.split("-")[2];
    var BANK_AC_NO=val.split("-")[0];
    var bk_br_city=val.split("-")[4];
   
    document.getElementById("txtCash_Acc_code").value=AC_HEAD_CODE;
    document.getElementById("txtBankAccountNo").value=BANK_AC_NO;
    document.getElementById("txtBankId").value=BANK_ID;
    document.getElementById("txtBranchId").value=BRANCH_ID;
    document.getElementById("txtBankName").value=bk_br_city;
    doFunction('unspent_OR_col_based_bank','null');


}
function unspent_OR_col_based_office(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var type=baseResponse.getElementsByTagName("type")[0].firstChild.nodeValue;
 //alert("type **"+type);
    var unitID=document.getElementById("cmbAcc_UnitCode").value; 
    
   var txtdate=document.frmFundTrs_Create_byOffice.txtCrea_date.value;
   var txtdate1=txtdate.split("/");
//    alert("txtdate1[1]"+txtdate1[1]);
//    alert("txtdate1[2]"+txtdate1[2]);
   
    if(type=="WATCHARGEREV_Hogenakkal")
    	{
    	if(unitID==390||unitID==391||unitID==392)
    		{
    	if ((txtdate1[2]>=2018 && txtdate1[1]>=8)||txtdate1[2]>=2019)
 	   {
    		
    	// alert("coming here"+document.getElementById("txtFDW").style.display);
   	 // if( document.getElementById("txtFDW").style.display=="block" &&  document.getElementById("Go_Enter").style.display=="block"){
   		   if( document.getElementById("txtFDW").style.display=="block" ){
   		   document.getElementById("txtFDW").style.display="none";
         ///	 document.getElementById("Go_Enter").style.display="none";
          }
       if(flag=="success")
       {  
           var AC_HEAD_CODE=baseResponse.getElementsByTagName("AC_HEAD_CODE")[0].firstChild.nodeValue;
           var BANK_ID=baseResponse.getElementsByTagName("BANK_ID")[0].firstChild.nodeValue;
           var BRANCH_ID=baseResponse.getElementsByTagName("BRANCH_ID")[0].firstChild.nodeValue;
           var BANK_AC_NO=baseResponse.getElementsByTagName("BANK_AC_NO")[0].firstChild.nodeValue;
           var bk_br_city=baseResponse.getElementsByTagName("bk_br_city")[0].firstChild.nodeValue;
           
           document.getElementById("txtCash_Acc_code").value=AC_HEAD_CODE;
           document.getElementById("txtBankAccountNo").value=BANK_AC_NO;
           document.getElementById("txtBankId").value=BANK_ID;
           document.getElementById("txtBranchId").value=BRANCH_ID;
           document.getElementById("txtBankName").value=bk_br_city;
           document.getElementById("img").style.display='block';        //alert("all values set");
       }
       else if(flag=="failure_office")
       {
           alert("Bank details not found for Accounting Unit of office");
           document.getElementById("txtCash_Acc_code").value="";
           document.getElementById("txtBankId").value="";
           document.getElementById("txtBankAccountNo").value="";
           document.getElementById("txtBranchId").value="";
           document.getElementById("txtBankName").value="";
           document.frmFundTrs_Create_byOffice.radRemitType.value="select";
           document.getElementById("img").style.display='none';
       }
       else if(flag=="failure")
       {
           alert("Failure to retrive values");
       }
       doFunction('unspent_OR_col_based_bank','null');
 	   }
    	   else
    		   {
    		   alert("Create date after Aug-2018 only allowed!!!!!!");
    		   return false;
    		   }
    		}
    	else
    		{
    		alert("Bank details not found for Accounting Unit of office");
    		return false;
    		}
    	}
	  
    
    if(type=="FDW"){
    	 var fdwcmb=document.getElementById("txtFDW");
    	 fdwcmb.length=0;
    	 
    	// if( document.getElementById("txtFDW").style.display=="none" && document.getElementById("Go_Enter").style.display=="none"){
    	 if( document.getElementById("txtFDW").style.display=="none"){
        	 document.getElementById("txtFDW").style.display="block";
        //	 document.getElementById("Go_Enter").style.display="block";
        	 
         }else{
        	 document.getElementById("txtFDW").style.display="none";
        	// document.getElementById("Go_Enter").style.display="none";
         }
	   if(flag=="success")
	    {  
	   var leng=baseResponse.getElementsByTagName("AC_HEAD_CODE");
	   var fdwcmb=document.getElementById("txtFDW");
	   var option=document.createElement("option");
	   option.value=0;
	   option.text='--Select--';
	   fdwcmb.appendChild(option);
	   for(var j=0;j<leng.length;j++){
		   var AC_HEAD_CODE=baseResponse.getElementsByTagName("AC_HEAD_CODE")[j].firstChild.nodeValue;
	        var BANK_ID=baseResponse.getElementsByTagName("BANK_ID")[j].firstChild.nodeValue;
	        var BRANCH_ID=baseResponse.getElementsByTagName("BRANCH_ID")[j].firstChild.nodeValue;
	        var BANK_AC_NO=baseResponse.getElementsByTagName("BANK_AC_NO")[j].firstChild.nodeValue;
	        var bk_br_city=baseResponse.getElementsByTagName("bk_br_city")[j].firstChild.nodeValue;
	        
	        var option=document.createElement("option");
	        option.value=0;
	 	   option.value=BANK_AC_NO+"-"+BANK_ID+"-"+BRANCH_ID+"-"+AC_HEAD_CODE+"-"+bk_br_city;
	 	  option.text=BANK_AC_NO+"-"+bk_br_city;
	 	  fdwcmb.appendChild(option);
	 	 document.getElementById("img").style.display='block';
	   }
	    } else if(flag=="failure_office")
	    {
	        alert("Bank details not found for Accounting Unit of office");
	        document.getElementById("txtCash_Acc_code").value="";
	        document.getElementById("txtBankId").value="";
	        document.getElementById("txtBankAccountNo").value="";
	        document.getElementById("txtBranchId").value="";
	        document.getElementById("txtBankName").value="";
	        document.frmFundTrs_Create_byOffice.radRemitType.value="select";
	        document.getElementById("img").style.display='none';
	        
	        
	    }
	    else if(flag=="failure")
	    {
	        alert("Failure to retrive values");
	    }
	   //alert('est');
	  
   }
    
    
    else{
	  // alert("coming here"+document.getElementById("txtFDW").style.display);
	 // if( document.getElementById("txtFDW").style.display=="block" &&  document.getElementById("Go_Enter").style.display=="block"){
		   if( document.getElementById("txtFDW").style.display=="block" ){
		   document.getElementById("txtFDW").style.display="none";
      ///	 document.getElementById("Go_Enter").style.display="none";
       }
    if(flag=="success")
    {  
        var AC_HEAD_CODE=baseResponse.getElementsByTagName("AC_HEAD_CODE")[0].firstChild.nodeValue;
        var BANK_ID=baseResponse.getElementsByTagName("BANK_ID")[0].firstChild.nodeValue;
        var BRANCH_ID=baseResponse.getElementsByTagName("BRANCH_ID")[0].firstChild.nodeValue;
        var BANK_AC_NO=baseResponse.getElementsByTagName("BANK_AC_NO")[0].firstChild.nodeValue;
        var bk_br_city=baseResponse.getElementsByTagName("bk_br_city")[0].firstChild.nodeValue;
        
        document.getElementById("txtCash_Acc_code").value=AC_HEAD_CODE;
        document.getElementById("txtBankAccountNo").value=BANK_AC_NO;
        document.getElementById("txtBankId").value=BANK_ID;
        document.getElementById("txtBranchId").value=BRANCH_ID;
        document.getElementById("txtBankName").value=bk_br_city;
        document.getElementById("img").style.display='block';        //alert("all values set");
    }
    else if(flag=="failure_office")
    {
        alert("Bank details not found for Accounting Unit of office");
        document.getElementById("txtCash_Acc_code").value="";
        document.getElementById("txtBankId").value="";
        document.getElementById("txtBankAccountNo").value="";
        document.getElementById("txtBranchId").value="";
        document.getElementById("txtBankName").value="";
        document.frmFundTrs_Create_byOffice.radRemitType.value="select";
        document.getElementById("img").style.display='none';
    }
    else if(flag=="failure")
    {
        alert("Failure to retrive values");
    }
    doFunction('unspent_OR_col_based_bank','null');
   }
   
}


function call_clr()
{
    /*
    document.getElementById("txtCash_Acc_code").value="";
    document.getElementById("txtBankId").value="";
    document.getElementById("txtBankAccountNo").value="";
    document.getElementById("txtBranchId").value="";
    document.getElementById("txtBankName").value="";
    
    document.getElementById("txtDebitAccCode").value="";
    document.getElementById("txtSubBankAccountNo").value="";
    document.getElementById("txtSubBankId").value="";
    document.getElementById("txtSubBranchId").value="";
    document.getElementById("txtSubBankName").value="";
    */
    document.getElementById("txtAmount").value="";
    document.getElementById("txtRemarks").value="";
    document.getElementById("txtCheque_DD_NO").value="";
    document.getElementById("txtCheque_DD_date").value="";
    document.frmFundTrs_Create_byOffice.txtCheque_DD[0].checked=true;
   // document.frmFundTrs_Create_byOffice.radRemitType[0].checked=true;
    document.frmFundTrs_Create_byOffice.radRemitType.value="select";
    document.getElementById("txtReferenceNo").value="";
    document.getElementById("txtReferenceDate").value="";
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
    if(document.getElementById("cmbAcc_UnitCode").value=="")
    {
        alert("Select the Account Unit code");
        document.getElementById("cmbAcc_UnitCode").focus();
        return false;    
    }
    if(document.getElementById("cmbOffice_code").value=="")
    {
        alert("Select the Office Code");
        document.getElementById("cmbOffice_code").focus();
        return false;
    }
    if(document.getElementById("txtCrea_date").value.length==0)
    {
        alert("Enter the Date of Creation");
        document.getElementById("txtCrea_date").focus();
        return false;    
    }

    if(document.getElementById("txtCash_Acc_code").value.length==0  || document.getElementById("txtCash_Acc_code").value==0)
    {
        alert("Select the Credit A/c Code");
        document.getElementById("txtCash_Acc_code").focus();
        return false;
    }
    
    if(document.getElementById("txtBankId").value.length==0  || document.getElementById("txtBankId").value==0)
    {
        alert("Bank Id not populated  for Selected Office");
        //document.getElementById("txtAmount").focus();
        return false;    
    }
    if(document.getElementById("txtBankAccountNo").value.length==0  || document.getElementById("txtBankAccountNo").value==0)
    {
        alert("Bank Account Number not populated for Selected Office");
        //document.getElementById("txtAmount").focus();
        return false;    
    }
    if(document.getElementById("txtBranchId").value.length==0  || document.getElementById("txtBranchId").value==0)
    {
        alert("Branch Id not populated  for Selected Office");
        //document.getElementById("txtAmount").focus();
        return false;    
    }
    
    
 if(document.frmFundTrs_Create_byOffice.txtCheque_DD[2].checked == false)
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
    
    
    if(document.getElementById("txtAmount").value.length==0)
    {
        alert("Enter the Total Amount");
        document.getElementById("txtAmount").focus();
        return false;    
    }
   
    if(document.getElementById("txtDebitAccCode").value.length==0  || document.getElementById("txtDebitAccCode").value==0)
    {
    alert("Select the Debit A/C code");
    document.getElementById("txtDebitAccCode").focus();
    return false;
    }
    if(document.getElementById("txtSubBankAccountNo").value.length==0  || document.getElementById("txtSubBankAccountNo").value==0)
    {
    alert("Enter the Bank Account Number for Head Office");
    document.getElementById("txtSubBankAccountNo").focus();
    return false;
    }
    if(document.getElementById("txtSubBankId").value.length==0  || document.getElementById("txtSubBankId").value==0)
    {
    alert("Enter the Bank Id has not populated for Head Office");
    document.getElementById("txtSubBankId").focus();
    return false;
    }
    if(document.getElementById("txtSubBranchId").value.length==0  || document.getElementById("txtSubBranchId").value==0)
    {
    alert("Enter the Branch Id has not populated for Head Office");
    document.getElementById("txtSubBranchId").focus();
    return false;
    }
    
    var cr_acc_hcode = document.getElementById("txtCash_Acc_code").value;
    var dr_acc_hcode = document.getElementById("txtDebitAccCode").value;
  // *************************** joe change on 30 Apr 2014  
   /* if ( cr_acc_hcode.substring(0,4) !=  dr_acc_hcode.substring(0,4) )
    {
      alert("Credit Account Head Code and Debit Account Head Code does not correspond to the same bank ");
      return false;
    }
    */
    
   //***************************/
    
    
    
    
    
   /*
   if(document.getElementById("txtReferenceNo").value.length==0)
    {
        alert("Enter the Reference Number");
        document.getElementById("txtReferenceNo").focus();
        return false;    
    }
    if(document.getElementById("txtReferenceDate").value.length==0)
    {
        alert("Enter the Reference Date");
        document.getElementById("txtReferenceDate").focus();
        return false;    
    }
    */
    
    
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
chequeRange=function(){	
	if((document.frmFundTrs_Create_byOffice.txtCheque_DD[0].checked==true)&&(document.getElementById('txtCheque_DD_NO').value!="")){
		//alert("test "+document.frmBankPay_PendingBill_create.txtCheque_DD[0].checked);
		var accunitId=document.getElementById('cmbAcc_UnitCode').value;
	//	var officeId=document.getElementById('cmbOffice_code').value;
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

function call_bankUpdate()
{
	var accunitId=document.getElementById('cmbAcc_UnitCode').value;
	var url="../../../../../BankPay_PendingBill_Create.view?Command=bankBalUpdate&accunitId="+accunitId;
		
		var req=getTransport();
		req.open("POST",url,true); 
		req.onreadystatechange=function(){
		processResponse_bk(req);
		};   
		req.send(null);
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
		alert("Cashbook Transactions for this Month Cannot be Entered unless " +
				"the Bank Balance For Each AccountNo is Updated By Using The Menu (Bank Balance Update) " +
				"under Unit Operation-Bank Reconcillation System ");
		document.getElementById("newDiv").style.display="none";
		document.frmFundTrs_Create_byOffice.txtCrea_date.value="";
		return false;
	}
	else
	{
		var counted_bank_bal=response.getElementsByTagName("counted_bank_bal")[0].firstChild.nodeValue;
		if(counted_bank_bal>0)
		{
			alert("Cashbook Transactions for this Month Cannot be Entered unless " +
					"the Bank Balance For Each AccountNo is Updated By Using The Menu (Bank Balance Update) " +
					"under Unit Operation-Bank Reconcillation System ");
			document.getElementById("newDiv").style.display="none";
			document.frmFundTrs_Create_byOffice.txtCrea_date.value="";
			return false;
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
            document.frmFundTrs_Create_byOffice.txtCrea_date.value=day+"/"+month+"/"+year;
            call_date(document.frmFundTrs_Create_byOffice.txtCrea_date);
            document.getElementById("newDiv").style.display="block";
		}
		
	}
}


function LoadOffice(unitID_val)
{
	//alert(unitID_val);
    if(unitID_val!="")
    {
        var cmbAcc_UnitCode=unitID_val;
        var url="../../../../../Receipt_SL.view?Command=LoadUnitWise_Office&cmbAcc_UnitCode="+cmbAcc_UnitCode;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
        	
            DroploadOffice(req);
        };
        req.send(null);
        return true;
    }
}




function DroploadOffice(req)
{
  
    if(req.readyState==4)
    {
     
     if(req.status==200)
     {
      
        
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
       
        var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       // alert(flag);
       
        if(flag=="success")
        { 
           
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
          
            var offidvalues=baseresponse.getElementsByTagName("offid");
          //  alert(offidvalues.length);
            for(i=0;i<offidvalues.length;i++)
            {  
                var option=document.createElement("OPTION");
                var offid=baseresponse.getElementsByTagName("offid")[i].firstChild.nodeValue;
                var offname=baseresponse.getElementsByTagName("offname")[i].firstChild.nodeValue;
                option.text=offname;
                option.value=offid;
                try
                {
                    cmboffice.add(option);
                }
                catch(errorObject )
                {
                    cmboffice.add(option,null);
                }   
            }
            
        }
        else
        {
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--select office--";
            option.value="";
            try
            {
                cmboffice.add(option);
            }
            catch(errorObject )
            {
                cmboffice.add(option,null);
            }
        }
            
        
     }
    }
}
function sendVa()
{
	
	var tblList=document.getElementById("tblList");
	
	var l=1;
	var amount;
	var slTypeNew;
	var slCodeNew;
	var vr_NoNew;
	var vr_DateNew;
	var txtAmountNew;
	if(tblList.rows.length>0)
		{
		if(tblList.rows.length>0){
			 slTypeNew=document.getElementById("slType"+l).value;
			 slCodeNew=document.getElementById("slCode"+l).value;
			 vr_NoNew=document.getElementById("vr_No"+l).value;
			 vr_DateNew=document.getElementById("vr_Date"+l).value;
			 txtAmountNew=document.getElementById("txtAmount"+l).value;
		}
		else{
		for(var k=0;k<tblList.rows.length;k++){
			l=l+k;	
			
		 slTypeNew=slTypeNew+document.getElementById("slType"+l).value+",";
		 slCodeNew=slCodeNew+document.getElementById("slCode"+l).value+",";
		 vr_NoNew=vr_NoNew+document.getElementById("vr_No"+l).value+",";
		 vr_DateNew=vr_DateNew+document.getElementById("vr_Date"+l).value+",";
		 txtAmountNew=txtAmountNew+document.getElementById("txtAmount"+l).value+",";
		}
		}
		}
	var tblListNew=document.frmFundTrs_Create_byOffice.radRemitType.value;
	alert(tblListNew);	
	/*var tblListNew=document.frmFundTrs_Create_byOffice.tblListNew;
		alert('NIC >> '+tblList.rows.length);
		if(tblListNew.rows.length<1)
			len=1;
			else
		    len=tblListNew.rows.length+1;


		
		var myRow=document.createElement("TR");
		var cell1=document.createElement("TD");
		var hid_slno=document.createElement("input");
		hid_slno.type="hidden";
		hid_slno.id="sl_noNew"+l;
		hid_slno.name="sl_noNew"+l;
		hid_slno.value=l;
		cell1.appendChild(hid_slno);
		var text_slno=document.createTextNode(l);
		cell1.appendChild(text_slno);
		myRow.appendChild(cell1);

		var cell1=document.createElement("TD");
		var hid_slType=document.createElement("input");
		hid_slType.type="hidden";
		hid_slType.id="slTypeNew"+l;
		hid_slType.name="slTypeNew"+l;
		hid_slType.value=slTypeNew;
		cell1.appendChild(hid_slType);
		myRow.appendChild(cell1);

		var cell1=document.createElement("TD");
		var hid_slCode=document.createElement("input");
		hid_slCode.type="hidden";
		hid_slCode.id="slCodeNew"+l;
		hid_slCode.name="slCodeNew"+l;
		hid_slCode.value=slCodeNew;
		cell1.appendChild(hid_slCode);
		myRow.appendChild(cell1);

		var cell1=document.createElement("TD");
		var hid_No=document.createElement("input");
		hid_No.type="hidden";
		hid_No.id="vr_NoNew"+l;
		hid_No.name="vr_NoNew"+l;
		hid_No.value=vr_NoNew;
		cell1.appendChild(hid_No);
		myRow.appendChild(cell1);


		var cell1=document.createElement("TD");
		var hid_date=document.createElement("input");
		hid_date.type="hidden";
		hid_date.id="vr_DateNew"+l;
		hid_date.name="vr_DateNew"+l;
		hid_date.value=vr_DateNew;
		cell1.appendChild(hid_date);
		myRow.appendChild(cell1);

		var cell1=document.createElement("TD");
		var hid_Amount=document.createElement("input");
		hid_Amount.type="hidden";
		hid_Amount.id="txtAmountNew"+l;
		hid_Amount.name="txtAmountNew"+l;
		hid_Amount.value=txtAmountNew;
		cell1.appendChild(hid_Amount);
		myRow.appendChild(cell1);

		tblListNew.appendChild(myRow);
		//clrscrn();

*/
		

	
}
function Addvalu()
{
var tblList=document.getElementById("tblList");
if(tblList.rows.length<1)
	len=1;
	else
    len=tblList.rows.length+1;


var txtCrea_date1=document.getElementById("txtCrea_date1").value;
var txtVoucher_No1=document.getElementById("txtVoucher_No1").value;
var txtScheme_id=document.getElementById("txtScheme_id");
var txtAmount=document.getElementById("txtAmount").value;
var myRow=document.createElement("TR");
var cell1=document.createElement("TD");
var hid_slno=document.createElement("input");
hid_slno.type="hidden";
hid_slno.id="sl_no"+len;
hid_slno.name="sl_no"+len;
hid_slno.value=len;
cell1.appendChild(hid_slno);
var text_slno=document.createTextNode(len);
cell1.appendChild(text_slno);
myRow.appendChild(cell1);

var cell1=document.createElement("TD");
var hid_slType=document.createElement("input");
hid_slType.type="hidden";
hid_slType.id="slType"+len;
hid_slType.name="slType"+len;
hid_slType.value=10;
cell1.appendChild(hid_slType);
var text_slType=document.createTextNode("Project");
cell1.appendChild(text_slType);
myRow.appendChild(cell1);

var cell1=document.createElement("TD");
var hid_slCode=document.createElement("input");
hid_slCode.type="hidden";
hid_slCode.id="slCode"+len;
hid_slCode.name="slCode"+len;
hid_slCode.value=txtScheme_id.value;
cell1.appendChild(hid_slCode);
var text_slCode=document.createTextNode(txtScheme_id.options[txtScheme_id.selectedIndex].text);
cell1.appendChild(text_slCode);
myRow.appendChild(cell1);

var cell1=document.createElement("TD");
var hid_No=document.createElement("input");
hid_No.type="hidden";
hid_No.id="vr_No"+len;
hid_No.name="vr_No"+len;
hid_No.value=txtVoucher_No1;
cell1.appendChild(hid_No);
var text_No=document.createTextNode(txtVoucher_No1);
cell1.appendChild(text_No);
myRow.appendChild(cell1);


var cell1=document.createElement("TD");
var hid_date=document.createElement("input");
hid_date.type="hidden";
hid_date.id="vr_Date"+len;
hid_date.name="vr_Date"+len;
hid_date.value=txtCrea_date1;
cell1.appendChild(hid_date);
var text_date=document.createTextNode(txtCrea_date1);
cell1.appendChild(text_date);
myRow.appendChild(cell1);

var cell1=document.createElement("TD");
var hid_Amount=document.createElement("input");
hid_Amount.type="hidden";
hid_Amount.id="txtAmount"+len;
hid_Amount.name="txtAmount"+len;
hid_Amount.value=txtAmount;
cell1.appendChild(hid_Amount);
var text_Amount=document.createTextNode(txtAmount);
cell1.appendChild(text_Amount);
myRow.appendChild(cell1);

tblList.appendChild(myRow);
clrscrn();

}

function clrscrn()
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
   
	document.getElementById("txtCrea_date1").value=day+"/"+month+"/"+year;
	document.getElementById("txtVoucher_No1").value="";
	document.getElementById("txtScheme_id").value="";
	document.getElementById("txtAmount").value="";
}
function loadschme()
{
    var url="../../../../../Fund_Transfer_Create_byOffice.view?Command=Load_Scheme";
    
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
       handleResponse_office(req);
    };   
    req.send(null);


}

