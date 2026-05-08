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
            document.PurchaseOrder.action="../../../../../../WQS_PurchaseOrderRep";
            document.PurchaseOrder.method="GET";
            document.PurchaseOrder.submit();
            return true;
}