
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



function Check_Date(_date)
{
    var doc_date;
    doc_date=document.getElementById("txtCrea_date").value; 
    
    
    if( _date != "")
    {
        var url="../../../../../Date_Check.kv?Command=Date_Check&_date="+_date+"&doc_date="+doc_date;       
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
           handle_loadOffice(req);
        }
        req.send(null); 
    } 
}




function handle_loadOffice(req)
{
   if(req.readyState==4)
    {
    
     if(req.status==200)
     {
             
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
        var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;       
        if(flag=="success")
        { 
        	
           var check=baseresponse.getElementsByTagName("check")[0].firstChild.nodeValue;   
            if (check=="invalid")
             {
                alert("Invalid Cheque Date"); 
                document.getElementById("txtCheque_DD_date").value="";                
             }
            else if (check=="valid")
            {
                
            }  
            
        }
            
      }       
             
   }
}

