function getTransport()
{
    var req=false;
    try
    {
        req=new ActiveXObject("Msxml2.XMLHTTP");
    }
    catch(e1)
    {
        try
        {
            req=new ActiveXObject("Microsoft.XMLHTTP");
        }
        catch(e2)
        {
            req=false;    
        }   
    }
    if(!req && typeof XMLHttpRequest !='undefined')
    {
        req=new XMLHttpRequest();
    }
    return req;
}    

function changeCat()
{
    checkvisibility();
    document.transfer.item.selectedIndex=0;
    var itemcombo=document.getElementById("item");
    var child=itemcombo.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
       itemcombo.removeChild(child[c]);
    }
   
    document.transfer.avail.value="";
    document.transfer.auom.value="";
    document.transfer.uom.value="";
    
    var catcode=document.transfer.category.value;
    var category=catcode.split('--');
    var lb=document.transfer.flab.value;
    var lab=lb.split("--");
    var disp=document.getElementById("divwork");
    if(category[1]=='Chemical'||category[1]=='chemical')
        disp.style.display='block';
    else
        disp.style.display='none';
    var url="../../../../../../WQS_LabTransferServ?command=changeCat&CatDesc="+category[1]+"&LabCode="+lab[0];
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
              var baseresponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseresponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue;
              if(command=='changeCat')
              {
                    retriveItem(baseresponse);
              }
              if(command=='changeItem')
              {
                    retriveStock(baseresponse);
              }
              if(command=='validate')
              {
                    checkAvailability(baseresponse);
              }
          }
    }
}

function retriveItem(baseresponse)
{
    var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=='Success')
    {
         var count=baseresponse.getElementsByTagName("count");
         var itemcombo=document.getElementById("item");
                 
         for(var i=0;i<count.length;i++)
         {
            var ItemCode=baseresponse.getElementsByTagName("ItemCode");
            var ItemDesc=baseresponse.getElementsByTagName("ItemDesc");
            var code=ItemCode.item(i).firstChild.nodeValue;
            var desc=ItemDesc.item(i).firstChild.nodeValue;
            var opt =document.createElement("option"); 
            var text=document.createTextNode(code+"--"+desc);
            opt.setAttribute("value",code+"--"+desc);
            opt.appendChild(text);
            itemcombo.appendChild(opt);  
         }
    }
    else
    {
        alert("This category has no items");
    }
}
function retriveStock(baseresponse)
{
    var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=='Success')
    {
         var stock=baseresponse.getElementsByTagName("stock")[0].firstChild.nodeValue;
         var uom=baseresponse.getElementsByTagName("uom")[0].firstChild.nodeValue;
         document.transfer.avail.value=stock;
         if(uom=='null')
         uom="";
         document.transfer.auom.value=uom;
         document.transfer.uom.value=uom;
         document.transfer.auom.style.visibility="visible";
         document.transfer.uom.style.visibility="visible";
    }
}

function checkAvailability(baseresponse)
{
    var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var flab=document.transfer.flab.value;
    var tlab=document.transfer.tlab.value;
    var category=document.transfer.category.value;
    var item=document.getElementById("item").value;
    var bcode=document.getElementById("bcode").value;
    var rdate=document.getElementById("rdate").value;
    var avail=document.getElementById("avail").value;
    var issued=document.getElementById("issued").value;
    var uom=document.getElementById("uom").value;    
    var remarks=document.getElementById("remarks").value;
    if(flag=='Avail')
    {
        var r=confirm("Entry is already available, Do you want Update it again");
        if(r==true)
        {
            var qty=baseresponse.getElementsByTagName("qty")[0].firstChild.nodeValue;
            var stock=parseInt(qty)+parseInt(issued);
            var url1="../../../../../../WQS_LabTransferServ?command=Update&flab="+flab+"&tlab="+tlab+"&category="+category+"&item="+item+"&bcode="+bcode+"&rdate="+rdate+"&avail="+avail+"&issued="+issued+"&stock="+stock+"&uom="+uom;
            document.transfer.action=url1;
            document.transfer.method="post";
            document.transfer.submit();
        }
    }
    else if(flag=='NotAvail')
    {
        var url1="../../../../../../WQS_LabTransferServ?command=submit&flab="+flab+"&tlab="+tlab+"&category="+category+"&item="+item+"&bcode="+bcode+"&rdate="+rdate+"&avail="+avail+"&issued="+issued+"&uom="+uom+"&remarks="+remarks;
        document.transfer.action=url1;
        document.transfer.method="post";
        document.transfer.submit();
    }
}

function clearAll()
{
    
    document.transfer.category.selectedIndex=0;
    checkvisibility();
    var itemcombo=document.getElementById("item");
    var child=itemcombo.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
       itemcombo.removeChild(child[c]);
    }
    
  /*  document.transfer.item.selectedIndex=0;   
    var option=document.createElement("option");
    var text=document.createTextNode("-- Select Item--");
    option.setAttribute("value","0");
    option.appendChild(text);
    itemcombo.appendChild(option);*/
    document.transfer.bcode.value="";
    document.transfer.avail.value="";
    document.transfer.auom.value="";
    document.transfer.rdate.value="";
    document.transfer.issued.value="";
    document.transfer.uom.value="";
    document.transfer.remarks.value="";   
}

function changeItem(command,param)
{
    checkvisibility();
    document.transfer.auom.value="";
    document.transfer.uom.value="";
    document.transfer.issued.value="";
    var lcode=document.transfer.flab.value;
    var lab=lcode.split("--");
    var category=document.transfer.category.value;
    var cat=category.split("--");
    var itm=document.getElementById("item").value;
    var item=itm.split("--");
    if(command=='item')
    { 
        if(cat[1]!='Chemical'&& cat[1]!='chemical')
        {
            url="../../../../../../WQS_LabTransferServ?command=changeItem&subcmd=item&LabCode="+lab[0]+"&CatCode="+cat[0]+"&CatDesc="+cat[1]+"&ItemCode="+item[0];
            var req=getTransport();
            req.open("GET",url,true);       
            req.onreadystatechange=function()
            {
                    processResponse(req);
            }  
            req.send(null); 
         }
   }
   else if(command=='brand')
   {
        var bcode=document.getElementById("bcode").value;
        var url="../../../../../../WQS_LabTransferServ?command=changeItem&subcmd=brand&LabCode="+lab[0]+"&CatCode="+cat[0]+"&CatDesc="+cat[1]+"&ItemCode="+item[0]+"&bcode="+bcode;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            processResponse(req);
        }
        req.send(null);
   }
}
 
function checkvisibility()
{
    document.transfer.auom.style.visibility="hidden";
    document.transfer.uom.style.visibility="hidden";
}
function checklength(evt,item)
{
    var charCode = (evt.which) ? evt.which : event.keyCode
    if(charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    var maxqty=document.transfer.avail.value.length;
    var text = document.transfer.issued.value.length;
    var result = true;
    if(text >= maxqty)
    {
        result = false;	
    }
    return result;
}
function checkqty()
{
    var qty=parseInt(document.transfer.avail.value);
    var stock=parseInt(document.transfer.issued.value); 
    if(qty<stock)
    {
        alert("Out of stock value");
        document.transfer.issued.value="";
        document.transfer.issued.focus();        
    }
}

function InsertVal()
{
    var lcode=document.transfer.flab.value;
    var lab=lcode.split("--");
    var tlab=document.transfer.tlab.value;
    tlab=tlab.split("--");
    var category=document.transfer.category.value;
    var cat=category.split("--");
    var itm=document.getElementById("item").value;
    var item=itm.split("--");
    var rdate=document.getElementById("rdate").value;
    var url="../../../../../../WQS_LabTransferServ?command=validate&LabCode="+lab[0]+"&tlab="+tlab[0]+"&CatCode="+cat[0]+"&ItemCode="+item[0]+"&rdate="+rdate;
    var req=getTransport();
    req.open("GET",url,true);       
    req.onreadystatechange=function()
    {
            processResponse(req);
    }  
    req.send(null); 
}
function checkremlength(evt,item)
{
    var maxqty=document.transfer.remarks.value.length;
    var text =100;
    var result = true;
    if(maxqty >= text)
    {
        result = false;	
    }  
    return result;
}