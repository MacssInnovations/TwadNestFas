
function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
   if(unicode==13)
    {
      //t.blur();
      //return true;-------------------- for taking action when press ENTER
    
    }
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48 || unicode>57 ) 
            return false 
    }
 }
 
 
function btncancel()
{

 self.close();
}

function checknull()
{
    
    if((document.getElementById("txtCB_Year").value.length!=4)|| (document.getElementById("txtCB_Year").value.length<=0) ||(document.getElementById("txtCB_Year").value==""))
    {
        alert("Enter the year");
        document.getElementById("txtCB_Year").focus();
        return false;
    }
    
    
    if(document.getElementById("txtCB_Month").value=="")
    {
        alert("Select a month");
        return false;
    }        
    
    if(document.getElementById("cmbBankAccNo").value=="")
    {
        alert("Select Bank Account Number");
        return false;
    }        
    
    return true;
 
}

function loadyear_month()
         {
//       alert("on loading");
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
//        document.frmReceivedRegister.txtCB_Year.value=year
//        document.frmReceivedRegister.txtCB_Month.value=month;
         document.frmReceivedRegister.txtCB_Year_from.value=year
       document.frmReceivedRegister.txtCB_Month_from.value=month;
         document.frmReceivedRegister.txtCB_Year_to.value=year
       document.frmReceivedRegister.txtCB_Month_to.value=month;
         
        
         }
         
         
         
         
         
         

         
         
function LoadBankAccountNumber()
{  
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	 var url="../../../../../../YourSelfCheque_Create.kv?command=LoadAccNumber&cmbAcc_UnitCode="+cmbAcc_UnitCode;
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
            	 
            	 if(flag=="success")
            	    {
            	           var bank_ac_no =new Array();
            	           var acc_desc   =new Array();
            	           
            	           /** Bank Account Number Object to find length */ 
            	           var acc_no=baseResponse.getElementsByTagName("acc_no");
            	           
            	           /** Get Combo box Object */
            	           var cmbBankAccNo = document.getElementById("cmbBankAccNo");
            	           
            	           
            	            for(var k=0;k<acc_no.length;k++)
            	            {
            	            	bank_ac_no[k]=baseResponse.getElementsByTagName("acc_no")[k].firstChild.nodeValue;
            	            	acc_desc[k]=baseResponse.getElementsByTagName("acc_desc")[k].firstChild.nodeValue;
            	            }
            	         
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
                            
                            var option=document.createElement("OPTION");
            	            option.text="------- All -------";
            	            option.value="0";                            
                            try
            	            {
            	               cmbBankAccNo.add(option);
            	            }catch(errorObject)
            	            {
            	               cmbBankAccNo.add(option,null);
            	            }                            
            	            
                            
            	            for(var k=0;k<acc_no.length;k++)
            	            {   
            	                  var option=document.createElement("OPTION");
            	                  option.text=acc_desc[k];
            	                  option.value=bank_ac_no[k];
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

