function getTransport()
{
var req=false;
try
{
req=new ActiveXObject("Msxml2.XMLHTTP");
}catch(e1)
 {
    try{
    req=new ActiveXObject("Microsoft.XMLHTTP");
    }
    catch(e2)
    {
    req=false;
    }
 }
    if(!req && typeof XMLHttpRequest!='undefined')
        {
        req=new XMLHttpRequest();
        }
   return req;
}    

function gen_rep()
{
        document.ItemReceipt.action="../../../../../../WQS_DateWise_ItemReceipt";
        document.ItemReceipt.method="POST";
        document.ItemReceipt.submit();
        return true;
}

