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
function changeCat()
{
    var item=document.getElementById("item");
    var catit=item.childNodes;
    for(var c=catit.length-1;c>=0;c--)
    {
       item.removeChild(catit[c]);
    }
    var option=document.createElement("option");
    var text=document.createTextNode("--Select--");
    option.setAttribute("value","0");
    option.appendChild(text);
    item.appendChild(option);
    
    var cat=document.PurchaseOrder.cat.value;
    alert(cat);
    var req=getTransport();
    var url="WQS_PurchaseOrder_CatPendingServ?command=changeCat&cat="+cat;
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
            if(cmd=='changeCat')
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
             var itemcombo=document.getElementById("item");
             for(var i=0;i<count.length;i++)
             {
                var icode=response.getElementsByTagName("itemcode");
                var idesc=response.getElementsByTagName("itemdesc");
                var code=icode.item(i).firstChild.nodeValue;
                var desc=idesc.item(i).firstChild.nodeValue;
                var opt =document.createElement("option"); 
                var text=document.createTextNode(code+"--"+desc);
                opt.setAttribute("value",code+"--"+desc);
                opt.appendChild(text);
                itemcombo.appendChild(opt);  
             }
        }
        else
        {
            alert("This supplier has no order");
        }
}

