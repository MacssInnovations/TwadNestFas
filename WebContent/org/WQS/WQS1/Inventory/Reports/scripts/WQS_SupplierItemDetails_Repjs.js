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
        var lab=document.getElementById("lab").value;
        lab=lab.split("--");
        var cat=document.getElementById("cat").value;
        document.ItemDetails.action="../../../../../../WQS_SupplierItemDetails_Repserv?lab="+lab[0]+"&cat="+cat;
        document.ItemDetails.method="POST";
        document.ItemDetails.submit();
        return true;
}
