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





function Schedule_Account_Heads(COMMAND)
{
	//alert(COMMAND);
        command_for_office = COMMAND;
        var url="../../../../../../Com_Schedule_Account_Heads.kv?COMMAND="+COMMAND;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
          handle_Schedule_Account_Heads(req);
        }        
        req.send(null);    
}






function handle_Schedule_Account_Heads(req)
{
   
    if(req.readyState==4)
    {
   
     if(req.status==200)
     {   
       var baseresponse=req.responseXML.getElementsByTagName("response")[0];        
       var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        
       if(flag=="success")
        { 
            var cmbAcc_UnitCode = document.getElementById("txtAcc_HeadCode");         
                cmbAcc_UnitCode.length=0;
          
            var option_count=baseresponse.getElementsByTagName("option");                       
            var root = null;
            
            for(var i=0;i<option_count.length;i++)
            {  
                var option=document.createElement("OPTION");
                root = baseresponse.getElementsByTagName("option")[i];
                
                var account_head_code=root.getElementsByTagName("account_head_code")[0].firstChild.nodeValue;                
                var account_head_code_desc=root.getElementsByTagName("account_head_code_desc")[0].firstChild.nodeValue;
                
                if ( account_head_code == 0 )
                {
                  option.text=account_head_code_desc;
                }
                else
                {
                  option.text=account_head_code + " - " + account_head_code_desc;
                }
                
                option.value=account_head_code;
                try
                {   
                    cmbAcc_UnitCode.add(option);
                }
                catch(errorObject)
                {
                    cmbAcc_UnitCode.add(option,null);
                }   
            }                 
            
        }
        else
        {
          alert("Failed to Load Account Head Codes");
        }
                 
     }
   }
}


