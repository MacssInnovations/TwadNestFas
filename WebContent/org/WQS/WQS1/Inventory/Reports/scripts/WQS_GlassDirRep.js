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
    if (!req && typeof XMLHttpRequest != 'undefined') 
        {
        req=new XMLHttpRequest();
        }
   return req;
   
} 
function close_win()
{
window.close();
}

function gen_rep()
{
            document.GlassDirReport.action="../../../../../../WQS_GlassDirReport";
            document.GlassDirReport.method="GET";
            document.GlassDirReport.submit();
            return true;
}