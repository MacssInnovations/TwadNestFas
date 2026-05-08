

/**
 *  Find Browser Type
 */
var isMan={
             account_head_status : false  
          };
 
 
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



/**
 *   Main Function 
 */
function Sub_Ledger_Mandatory(Account_Head_Code)
{
	//alert("aaaa");
    if(Account_Head_Code!="")
    {
        var url="../../../../../Sub_Ledger_Type_Applicable.kv?Account_Head_Code="+Account_Head_Code;
        //alert(url);
        
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            Hangle_Response_Sub_Ledger_Mandatory(req);
        }
        req.send(null);
    }
}




function Hangle_Response_Sub_Ledger_Mandatory(req)
{
   
   if(req.readyState==4)
    {
     if(req.status==200)
     {
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];       
        var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       //alert("flag==>"+flag);
        if(flag=="Success")
        {   
           isMan.account_head_status = true;                  
        }
        else
        {
           isMan.account_head_status = false;            
        }    
     }
   }
}
