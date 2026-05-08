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
function checkAvail()
{
    var lab=document.ReCancellation.lab.value;
    lab=lab.split("--");
    var pno=document.ReCancellation.orderno.value;
    
    var cat=document.getElementById("cat");
    var child=cat.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
       cat.removeChild(child[c]);
    }    
    var url="../../../../../../WQS_PurchaseOrder_ReconciliationServ?command=checkAvail&lab="+lab[0]+"&pno="+pno;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        processResponse(req);
    }  
    req.send(null);
}

function processResponse(req)
{
     if(req.readyState==4)
    {
        if(req.status==200)
        {
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var val=baseResponse.getElementsByTagName("command")[0];
            var cmd=val.firstChild.nodeValue;
            if(cmd=="checkAvail")
            {
                retriveDetails(baseResponse);
            }
            else if(cmd=="changeCat")
            {
                retriveItems(baseResponse);
            }
            else
            {
                getDetails(baseResponse);
            }
        }
    }
}

function retriveDetails(baseResponse)
{
    fg=baseResponse.getElementsByTagName("fg")[0].firstChild.nodeValue;
    flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(fg=="Success"&& flag=="Success")
    {
        odate=baseResponse.getElementsByTagName("odate")[0].firstChild.nodeValue;
        scode=baseResponse.getElementsByTagName("scode")[0].firstChild.nodeValue;
        sname=baseResponse.getElementsByTagName("sname")[0].firstChild.nodeValue;
        document.ReCancellation.orderdate.value=odate;
        document.ReCancellation.sid.value=scode;
        document.ReCancellation.sname.value=sname;
        count=baseResponse.getElementsByTagName("count");
        var cat=document.getElementById("cat");
        for(var i=0;i<count.length;i++)
         {
            var catcode=baseResponse.getElementsByTagName("catcode");
            var catdesc=baseResponse.getElementsByTagName("catdesc");
            var code=catcode.item(i).firstChild.nodeValue;
            var desc=catdesc.item(i).firstChild.nodeValue;
            var opt =document.createElement("option"); 
            var text=document.createTextNode(code+"--"+desc);
            opt.setAttribute("value",code+"--"+desc);
            opt.appendChild(text);
            cat.appendChild(opt);  
         }
    }
}

function changeCat()
{
    var lab=document.ReCancellation.lab.value;
    lab=lab.split("--");
    var pno=document.ReCancellation.orderno.value;
    var cat=document.ReCancellation.cat.value;
    cat=cat.split("--");
    
    var item=document.getElementById("item");
    var child=item.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
       item.removeChild(child[c]);
    }
    
    var url="../../../../../../WQS_PurchaseOrder_ReconciliationServ?command=changeCat&lab="+lab[0]+"&pno="+pno+"&catcode="+cat[0]+"&catdesc="+cat[1];
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        processResponse(req);
    }  
    req.send(null);
}

function retriveItems(baseResponse)
{
    flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="Success")
    {
        count=baseResponse.getElementsByTagName("count");
        var item=document.getElementById("item");
        for(var i=0;i<count.length;i++)
         {
            var icode=baseResponse.getElementsByTagName("icode")[i].firstChild.nodeValue;
            var idesc=baseResponse.getElementsByTagName("idesc")[i].firstChild.nodeValue;
             var opt =document.createElement("option"); 
            var text=document.createTextNode(icode+"--"+idesc);
            opt.setAttribute("value",icode+"--"+idesc);
            opt.appendChild(text);
            item.appendChild(opt);  
         }
    }
}

function changeItem()
{
    var lab=document.ReCancellation.lab.value;
    lab=lab.split("--");
    var pno=document.ReCancellation.orderno.value;
    var cat=document.ReCancellation.cat.value;
    cat=cat.split("--");
    var item=document.ReCancellation.item.value;
    item=item.split("--");
    var url="../../../../../../WQS_PurchaseOrder_ReconciliationServ?command=changeItem&lab="+lab[0]+"&pno="+pno+"&catcode="+cat[0]+"&catdesc="+cat[1]+"&icode="+item[0]+"&idesc="+item[1];
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        processResponse(req);
    }  
    req.send(null);
}

function getDetails(baseResponse)
{
    flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="Success")
    {
            var qtyordered=baseResponse.getElementsByTagName("qtyordered")[0].firstChild.nodeValue;
            var qtyreceived=baseResponse.getElementsByTagName("qtyreceived")[0].firstChild.nodeValue;
            var uom=baseResponse.getElementsByTagName("uom")[0].firstChild.nodeValue;
            //var remarks=baseResponse.getElementsByTagName("remarks")[0].firstChild.nodeValue;
            var sid=baseResponse.getElementsByTagName("statusid")[0].firstChild.nodeValue;
            document.getElementById("divwork1").style.display="block";
            document.getElementById("divwork2").style.display="block";
            //document.getElementById("divwork3").style.display="block";                                                      
            document.ReCancellation.oqty.value=qtyordered;
            document.ReCancellation.rqty.value=qtyreceived;
            document.ReCancellation.uom.value=uom;
            document.ReCancellation.ruom.value=uom;
            //document.ReCancellation.rem.value=remarks;
    }
}

function validatefun()
{
    
    var lab=document.ReCancellation.lab.value;
    var ono=document.ReCancellation.orderno.value;
    var cat=document.ReCancellation.cat.value;
    var item=document.ReCancellation.item.value;
    var rem=document.ReCancellation.rem.value;
    if(document.ReCancellation.sel[0].checked==true)
    {   
            var r=confirm("Are you sure to cancel all items under this purchase order")
            if (r==true)
            {
               document.ReCancellation.action="../../../../../../WQS_PurchaseOrder_ReconciliationServ?command=purchaseorder&lab="+lab+"&orderno="+ono+"&remarks="+rem;
               document.ReCancellation.method="POST";
               document.ReCancellation.submit();
            }
    }
    else if(document.ReCancellation.sel[1].checked==true)
    {   
            var oqty=document.ReCancellation.oqty.value;
            var rqty=document.ReCancellation.rqty.value;
            var r=confirm("Are You Sure?")
            if (r==true)
            {
               document.ReCancellation.action="../../../../../../WQS_PurchaseOrder_ReconciliationServ?command=item&lab="+lab+"&orderno="+ono+"&category="+cat+"&item="+item+"&oqty="+oqty+"&rqty="+rqty+"&remarks="+rem;
               document.ReCancellation.method="POST";
               document.ReCancellation.submit();
               //clearfun();
            }
    }
}

 function clearfun()
 {
    document.ReCancellation.orderno.value="";
    document.ReCancellation.orderdate.value="";
    document.ReCancellation.sid.value="";
    document.ReCancellation.sname.value="";
    document.ReCancellation.cat.selectedIndex="";
    document.ReCancellation.item.selectedIndex="";
    document.ReCancellation.oqty.value="";
    document.ReCancellation.rem.value="";
    document.ReCancellation.sel[0].checked=false
    document.ReCancellation.sel[1].checked=false
    document.getElementById("divorder").style.display="none";
    document.getElementById("divcat").style.display="none";
    document.getElementById("divwork1").style.display="none";
    document.getElementById("divwork2").style.display="none";
    document.getElementById("divwork3").style.display="none";
    document.ReCancellation.validate.disabled=true;
 }

function ItemDisplay()
{
    if(document.ReCancellation.sel[0].checked==true)
    {
        document.getElementById("divorder").style.display="block";
        document.getElementById("divcat").style.display="none";
        document.getElementById("divwork1").style.display="none";
        document.getElementById("divwork2").style.display="none";
        document.getElementById("divwork3").style.display="block";
        document.ReCancellation.validate.disabled=false;
    }
    else
    {
        document.getElementById("divorder").style.display="block";
        document.getElementById("divcat").style.display="block";
        document.getElementById("divwork1").style.display="none";
        document.getElementById("divwork2").style.display="none";
        document.getElementById("divwork3").style.display="block";
        document.ReCancellation.validate.disabled=false;
    }
}