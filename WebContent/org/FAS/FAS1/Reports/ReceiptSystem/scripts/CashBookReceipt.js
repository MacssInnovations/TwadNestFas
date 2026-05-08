CashBookReceipt.jspCashBookReceipt.jsp// added from 08/02/2012 ... by Sathya NIC
var bank_ac_no =new Array();
var acc_desc   =new Array();
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


//Null check Validation
function nullcheck()
{
	var txtCB_Year=document.getElementById("txtCB_Year").value;
    var txtCB_Month=document.getElementById("txtCB_Month").value;
    
   
    if((document.frmReport.cmbAcc_UnitCode.value=="") || (document.frmReport.cmbAcc_UnitCode.value.length<=0) || (document.frmReport.cmbAcc_UnitCode.value=="0"))
    {
        alert("Please Select Accounting Unit");
        document.frmReport.cmbAcc_UnitCode.focus();
        return false;
    }
   /* if((document.frmReport.cmbOffice_code.value=="") || (document.frmReport.cmbOffice_code.value.length<=0) || (document.frmReport.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.frmReport.cmbOffice_code.focus();
        return false;
    
    }
    if((document.frmReport.cmbAccHeadCode.value=="") || (document.frmReport.cmbAccHeadCode.value.length<=0) || (document.frmReport.cmbAccHeadCode.value=="0"))
    {
        alert("Please Select Account Head Code");
        document.frmReport.cmbAccHeadCode.focus();
        return false;
    }
    
    if((document.frmReport.txtfromdate.value=="") || (document.frmReport.txtfromdate.value.length<=0))
    {
        alert("Please Enter From Date");
        document.frmReport.txtfromdate.focus();
        return false;
    }
    if((document.frmReport.txttodate.value=="") || (document.frmReport.txttodate.value.length<=0))
    {
        alert("Please Enter To Date");
        document.frmReport.txttodate.focus();
        return false;
    }
    */
    
     
    if(txtCB_Year.length!=4 || txtCB_Month.length==0)
    {
        alert("Specify the year(4 digit) and month");
        return false;
    }
  
    
    if (document.frmReport.DisMode[0].checked == false && document.frmReport.DisMode[1].checked == false ) 
    {
    	 alert("Select either the  Bank or Account Number");	     
	     return false;
    }
    
    
    //modifications done by Sathya on Jan2013 choose either bank wise or account number wise 
    // Both the bank name and the account number must be selected  
  /*
    if ( document.frmReport.DisMode[0].checked==true )
    {
    	
        if((document.frmReport.cmbBankId.value=="") || (document.frmReport.cmbBankId.value.length<=0))
	    {
	        alert("Select the bank");
	        document.frmReport.cmbBankId.focus();
	        return false;
	    }
        if((document.frmReport.cmbBankAccNo.value=="") || (document.frmReport.cmbBankAccNo.value.length<=0))
	    {
	        alert("Select the Bank Account Number  ");
	        document.frmReport.cmbBankAccNo.focus();
	        return false;
	    }
    }
    else if ( document.frmReport.DisMode[1].checked==true )
    {
    	if((document.frmReport.cmbBankId.value=="") || (document.frmReport.cmbBankId.value.length<=0))
	    {
	        alert("Select the bank");
	        document.frmReport.cmbBankId.focus();
	        return false;
	    }
    	if((document.frmReport.cmbBankAccNo.value=="") || (document.frmReport.cmbBankAccNo.value.length<=0))
	    {
	        alert("Select the Bank Account Number  ");
	        document.frmReport.cmbBankAccNo.focus();
	        return false;
	    }
    } 	 */

    
return true;

}




function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48 || unicode>57 ) 
                return false ;
        }
     }

//As the loading of bank account no from common js is shared by BRS form the loading bank account no function is wriiten here.....Sathya 08/Feb2012
function LoadBankAccountNumber()
{ 
	   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	 
//	   alert("cmbAcc_UnitCode:::"+cmbAcc_UnitCode);
           var url;
               
            url="../../../../../../Common_Bank_Account_Number_Loading.kv?command=LoadBankAccountNumber&cmbAcc_UnitCode="+cmbAcc_UnitCode;
//	    alert(url);
            var req=getTransport();
	   req.open("GET",url,true); 
	   req.onreadystatechange=function()
	   {
	    	 LoadBankAccountNumberRes(req);
	   }   
       req.send(null);
	  }

function LoadBankAccountNumberRes(req)
{  
 
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             var tagcommand=baseResponse.getElementsByTagName("command")[0];
             var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="LoadBankAccountNumber")
            {
            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
//            	 alert(flag);
            	 if(flag=="success")
            	    {
            	          
            	           
            	           /** Bank Account Number Object to find length */ 
            	           var acc_no=baseResponse.getElementsByTagName("acc_no");
           	         //  alert(acc_no.length);
            	        
            	           
            	           /* for(var k=0;k<acc_no.length;k++)
            	            {
            	            	
            	            	var bank_ac_nok=baseResponse.getElementsByTagName("acc_no")[k].firstChild.nodeValue;
            	            //	alert(bank_ac_nok);
            	            	var acc_desck=baseResponse.getElementsByTagName("acc_desc")[k].firstChild.nodeValue;
            	            //	alert(acc_desck);
            	            	
//            	            	bank_name[k]=baseResponse.getElementsByTagName("bank_name")[k].firstChild.nodeValue;
//            	            	branch_name[k]=baseResponse.getElementsByTagName("branch_name")[k].firstChild.nodeValue;
//            	            	bank_id[k] =baseResponse.getElementsByTagName("bank_id")[k].firstChild.nodeValue;
//            	            	branch_id[k] =baseResponse.getElementsByTagName("branch_id")[k].firstChild.nodeValue;
//            	            	opr_mode[k] =baseResponse.getElementsByTagName("opr_mode")[k].firstChild.nodeValue;            	            	
            	            }*/
            	            /** Get Combo box Object */
              	          
            	            var cmbBankAccNo = document.getElementById("cmbBankAccNo");
            	            cmbBankAccNo.innerHTML="";
            	            var option=document.createElement("OPTION");
            	            option.text="--Select Bank Acc. Number--";
            	            option.value="";
            	            try
            	            {
            	            	cmbBankAccNo.add(option);
            	            }catch(errorObject)
            	            {
            	            	cmbBankAccNo.add(option,null);
            	            }
            	            
            	            for(var kk=0;kk<acc_no.length;kk++)
            	            {   
            	            	var bank_ac_nok=baseResponse.getElementsByTagName("acc_no")[kk].firstChild.nodeValue;
                	          
                	            	var acc_desck=baseResponse.getElementsByTagName("acc_desc")[kk].firstChild.nodeValue;
            	                	var option=document.createElement("OPTION");
            	                  option.text=acc_desck;
            	                  option.value=bank_ac_nok+"-"+acc_desck.split("-")[2];
            	                  try
            	                  {
            	                	  cmbBankAccNo.add(option);
            	                  }
            	                  catch(errorObject)
            	                  {
            	                	  cmbBankAccNo.add(option,null);
            	                  }
            	            }
            	    }
              }

        
        }
    }
}
