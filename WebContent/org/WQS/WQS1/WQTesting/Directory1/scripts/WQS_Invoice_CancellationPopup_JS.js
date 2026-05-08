var req;
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

function loadfun()
{
    var lab=document.invoice.labcode.value;
    lab=lab.split("--");
    var req=getResponse();
    var url="../../../../../WQS_Invoice_CancellationServ?command=load_invoice?lab_code="+lab[0];
    req.open("GET",url,true);
    req.onreadystatechange=function
    {
      alert(req.readyState);
    }
    req.send(null);
}