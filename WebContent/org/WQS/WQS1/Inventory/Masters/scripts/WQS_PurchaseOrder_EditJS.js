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
    var lab=document.PurchaseEdit.lab.value;
    lab=lab.split("--");
    var pno=document.PurchaseEdit.orderno.value;
    
    var cat=document.getElementById("cat");
    var child=cat.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
       cat.removeChild(child[c]);
    }
    
    var url="../../../../../../WQS_PurchaseOrder_EditServ?command=checkAvail&lab="+lab[0]+"&pno="+pno;
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
             else if(cmd=="callSup")
            {
                SupItems(baseResponse);
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
        document.PurchaseEdit.orderdate.value=odate;
        document.PurchaseEdit.sid.value=scode;
        document.PurchaseEdit.sname.value=sname;
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
    else if(flag=="Failure")
    {
        alert("Enter Valid Purchase Order Number");
        document.PurchaseEdit.orderno.value="";
        
    }
}

function changeCat()
{
    var lab=document.PurchaseEdit.lab.value;
    lab=lab.split("--");
    var pno=document.PurchaseEdit.orderno.value;
    var cat=document.PurchaseEdit.cat.value;
    cat=cat.split("--");
    
    var item=document.getElementById("item");
    var child=item.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
       item.removeChild(child[c]);
    }
    
    var url="../../../../../../WQS_PurchaseOrder_EditServ?command=changeCat&lab="+lab[0]+"&pno="+pno+"&catcode="+cat[0]+"&catdesc="+cat[1];
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
    var lab=document.PurchaseEdit.lab.value;
    lab=lab.split("--");
    var pno=document.PurchaseEdit.orderno.value;
    var cat=document.PurchaseEdit.cat.value;
    cat=cat.split("--");
    var item=document.PurchaseEdit.item.value;
    item=item.split("--");
    var url="../../../../../../WQS_PurchaseOrder_EditServ?command=changeItem&lab="+lab[0]+"&pno="+pno+"&catcode="+cat[0]+"&catdesc="+cat[1]+"&icode="+item[0]+"&idesc="+item[1];
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
            var uom=baseResponse.getElementsByTagName("uom")[0].firstChild.nodeValue;
            var remarks=baseResponse.getElementsByTagName("remarks")[0].firstChild.nodeValue;
            var sid=baseResponse.getElementsByTagName("statusid")[0].firstChild.nodeValue;
            if(sid=="CR" || sid=="MD")
            {
                var id=document.getElementById("divwork");
                id.style.display="block";
                document.PurchaseEdit.oqty.value=qtyordered;
                document.PurchaseEdit.uom.value=uom;
                document.PurchaseEdit.rem.value=remarks;
                document.PurchaseEdit.orderdate.disabled=false;
                document.PurchaseEdit.sid.disabled=false;
                document.PurchaseEdit.oqty.disabled=false;
                document.PurchaseEdit.uom.disabled=false;
                document.PurchaseEdit.rem.disabled=false;
                document.PurchaseEdit.update.disabled=false;
            }
            else if(sid=="CL")
            {
                alert("Record is already Cancelled, u can not update it");
                var id=document.getElementById("divwork");
                id.style.display="block";
                document.PurchaseEdit.oqty.value=qtyordered;
                document.PurchaseEdit.uom.value=uom;
                document.PurchaseEdit.rem.value=remarks;
                
                document.PurchaseEdit.orderdate.disabled=true;
                document.PurchaseEdit.sid.disabled=true;
                document.PurchaseEdit.oqty.disabled=true;
                document.PurchaseEdit.uom.disabled=true;
                document.PurchaseEdit.rem.disabled=true;
                document.PurchaseEdit.update.disabled=true;
            }
            else if(sid=="FR")
            {
                alert("Record is already Freezed, u can not update it");
                var id=document.getElementById("divwork");
                id.style.display="block";
                
                document.PurchaseEdit.oqty.value=qtyordered;
                document.PurchaseEdit.uom.value=uom;
                document.PurchaseEdit.rem.value=remarks;
                
                document.PurchaseEdit.orderdate.disabled=true;
                document.PurchaseEdit.sid.disabled=true;
                document.PurchaseEdit.oqty.disabled=true;
                document.PurchaseEdit.uom.disabled=true;
                document.PurchaseEdit.rem.disabled=true;
                document.PurchaseEdit.update.disabled=true;
            }
    }
}

function updatefun()
{
    
    var ono=document.PurchaseEdit.orderno.value;
    if(ono)
    {   
            var r=confirm("Are You Sure?")
            if (r==true)
            {
               document.PurchaseEdit.action="../../../../../../WQS_PurchaseOrder_EditServ";
               document.PurchaseEdit.method="POST";
               document.PurchaseEdit.submit();
            }
    }
    else
    {
        alert("Enter Order Number");
    }
}

function clearfun()
{
    document.PurchaseEdit.orderno.value="";
    document.PurchaseEdit.orderdate.value="";
    document.PurchaseEdit.sid.value="";
    document.PurchaseEdit.sname.value="";
    document.PurchaseEdit.cat.selectedIndex="";
    document.PurchaseEdit.item.selectedIndex="";
    var id=document.getElementById("divwork");
    id.style.display="none";
    var cat=document.getElementById("cat");
    var child=cat.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
       cat.removeChild(child[c]);
    }
    var item=document.getElementById("item");
    var child1=item.childNodes;
    for(var c=child1.length-1;c>1;c--)
    {
       item.removeChild(child1[c]);
    }
}

function callSup()
{
    var lab=document.PurchaseEdit.lab.value;
    lab=lab.split("--");
    var sid=document.PurchaseEdit.sid.value;
    var url="../../../../../../WQS_PurchaseOrder_EditServ?command=callSup&lab="+lab[0]+"&supid="+sid;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        processResponse(req);
    }  
    req.send(null);
}

function SupItems(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="Success")
    {
            var supname=baseResponse.getElementsByTagName("supname")[0].firstChild.nodeValue;
            document.PurchaseEdit.sname.value=supname;
    }
    else
    {
            alert("Enter a Valid Supplier Id");
            document.PurchaseEdit.sid.value="";
            document.PurchaseEdit.sname.value="";
    }
}

function clearAll()
{
    document.PurchaseEdit.orderno.value="";
    document.PurchaseEdit.orderdate.value="";
    document.PurchaseEdit.sid.value="";
    document.PurchaseEdit.sname.value="";
    document.PurchaseEdit.cat.selectedIndex="";
    document.PurchaseEdit.item.selectedIndex="";
    document.PurchaseEdit.oqty.value="";
    document.PurchaseEdit.rem.value="";
}
function capitalise()
{
    var orderno=document.getElementById("orderno").value;
    caporder=orderno.toUpperCase();
    document.getElementById("orderno").value=caporder;
}