/** Browser Identification */

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



var bank_no = new Array();
var bank_name  = new Array();


/** Main Function */
function LoadBankName()
{  
	//alert("LoadBankName"); 
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	 var url="../../../../../../Common_Bank_Name_Loading.kv?cmbAcc_UnitCode="+cmbAcc_UnitCode;
	 var req=getTransport();
     req.open("GET",url,true); 
     req.onreadystatechange=function()
     {
    	 LoadBankNameRes(req);
     }   
         req.send(null);	  
}



function LoadBankNameRes(req)
{  
  
    if(req.readyState==4)
    {
    	
    	
        if(req.status==200)
        {
        	
        	
             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             var tagcommand=baseResponse.getElementsByTagName("command")[0];
             var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="LoadBankName")
            {
            	
            	
            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	 
            	 if(flag=="success")
            	    {
            	           
            	           /** Bank Account Number Object to find length */ 
            	           var bank_id=baseResponse.getElementsByTagName("bank_id");
            	           
            	           /** Get Combo box Object */
            	           var cmbBankId = document.getElementById("cmbBankId");
            	           
            	            for(var k=0;k<bank_id.length;k++)
            	            {
            	            	bank_no[k]=baseResponse.getElementsByTagName("bank_id")[k].firstChild.nodeValue;
            	            	bank_name[k]=baseResponse.getElementsByTagName("bank_name")[k].firstChild.nodeValue;
            	            }
            	         
            	            cmbBankId.innerHTML="";
            	            var option=document.createElement("OPTION");
            	            option.text="--Select Bank Acc Number --";
            	            option.value="";
            	            try
            	            {
            	            	cmbBankId.add(option);
            	            }catch(errorObject)
            	            {
            	            	cmbBankId.add(option,null);
            	            }
            	            
            	            for(var k=0;k<bank_id.length;k++)
            	            {   
            	                  var option=document.createElement("OPTION");
            	                  option.text=bank_name[k];
            	                  option.value=bank_no[k];
            	                  try
            	                  {
            	                	  cmbBankId.add(option);
            	                  }
            	                  catch(errorObject)
            	                  {
            	                	  cmbBankId.add(option,null);
            	                  }
            	           }
            	    }
              }
        
        }
    }
}



