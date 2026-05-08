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

function changeSupplier()
{
    var catcombo=document.getElementById("cat");
    var child=catcombo.childNodes;
    for(var c=child.length-1;c>=0;c--)
    {
       catcombo.removeChild(child[c]);
    }
    var option=document.createElement("option");
    var text=document.createTextNode("--Select--");
    option.setAttribute("value","0");
    option.appendChild(text);
    catcombo.appendChild(option);
    
    var supplier=document.SupplierItem.sid.value;
    var sup=supplier.split("--");
    var req=getTransport();
    var url="../../../../../../WQS_SupplierItem_Repserv?command=changeSupplier&sup="+sup[0];
    req.open("GET",url,"true");
    req.onreadystatechange=function()
    {
    manipulate(req);
    }
    req.send(null);
}

function manipulate(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {
            var response=req.responseXML.getElementsByTagName("response")[0];
            var cmd=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            if(cmd=='changeSupplier')
            {
                retCategory(response);
            }
        }
    }
}

function retCategory(response)
{
        var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=='Success')
        {
             var count=response.getElementsByTagName("count");
             var catcombo=document.getElementById("cat");
                     
             for(var i=0;i<count.length;i++)
             {
                var CatCode=response.getElementsByTagName("catcode");
                var CatDesc=response.getElementsByTagName("catdesc");
                var code=CatCode.item(i).firstChild.nodeValue;
                var desc=CatDesc.item(i).firstChild.nodeValue;
                var opt =document.createElement("option"); 
                var text=document.createTextNode(code+"--"+desc);
                opt.setAttribute("value",code+"--"+desc);
                opt.appendChild(text);
                catcombo.appendChild(opt);  
             }
        }
        else
        {
            alert("This supplier has no order");
        }
}

function gen_rep()
{
            document.SupplierItem.action="../../../../../../WQS_SupplierItem_Repserv";
            document.SupplierItem.method="POST";
            document.SupplierItem.submit();
            return true;
}