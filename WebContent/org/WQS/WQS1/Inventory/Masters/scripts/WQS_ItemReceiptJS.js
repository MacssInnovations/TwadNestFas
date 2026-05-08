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

function nullValue()
{
                 
                  if((document.getElementById("rdate").value=="") || (document.getElementById("rdate").value.length<=0))
                  {
                       alert("Dont leave Date Of Receipt  field is empty");
                       return false;
                  }
                  if((document.getElementById("received").value=="") || (document.getElementById("received").value.length<=0))
                  {
                       alert("Dont leave Qantity Received field is empty");
                       return false;
                  }
                  return true;
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
              if(command=='changeOrder')
              {
                    retriveCategory(baseresponse);
              }
              else if(command=='changeCat')
              {
                    retriveItem(baseresponse);
              }
         
          }
     }     
}

function changeOrder()
{    
    textHidden();
    document.ItemReceipt.category.selectedIndex=0;  
    clearAll1();    
    var lb=document.ItemReceipt.lab.value;
    var lab=lb.split('--');
    var order=document.ItemReceipt.orderno.value;
    var url="../../../../../../WQS_ItemReceipt?command=changeOrder&LabCode="+lab[0]+"&OrderNo="+order;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        processResponse(req);
    }
    req.send(null);
}

function retriveCategory(baseresponse)
{
    var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=='Success')
    {
         var count=baseresponse.getElementsByTagName("count");
         var itemcombo=document.getElementById("category");
                 
         for(var i=0;i<count.length;i++)
         {
            var cat=baseresponse.getElementsByTagName("CatCode");
            var desc=baseresponse.getElementsByTagName("CatDesc");
            var num=cat.item(i).firstChild.nodeValue;
            var numdesc=desc.item(i).firstChild.nodeValue;
            var opt =document.createElement("option"); 
            var text=document.createTextNode(num+"--"+numdesc);
            opt.setAttribute("value",num+"--"+numdesc);
            opt.appendChild(text);
            itemcombo.appendChild(opt);  
         }
    }
}

function retriveStock(baseresponse)
{
    var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=='Success')
    {
        var ordered=baseresponse.getElementsByTagName("QTY_ORDERED")[0].firstChild.nodeValue;
        var received=baseresponse.getElementsByTagName("QTY_RECEIVED_SOFAR")[0].firstChild.nodeValue;
        var ouom=baseresponse.getElementsByTagName("ORDERED_UOM_CODE")[0].firstChild.nodeValue;
        var ruom=baseresponse.getElementsByTagName("RECEIVED_UOM_CODE")[0].firstChild.nodeValue;
        var avail=baseresponse.getElementsByTagName("avail")[0].firstChild.nodeValue;
        document.ItemReceipt.qty.value=ordered;
        document.ItemReceipt.stock.value=received;
        if(ouom=='null')
        ouom="";
        document.ItemReceipt.ordereduom.style.visibility="visible";
        document.ItemReceipt.ordereduom.value=ouom;
        if(ruom=='null')
        ruom="";
        document.ItemReceipt.receiveduom.style.visibility="visible";
        document.ItemReceipt.receiveduom.value=ruom;
        document.ItemReceipt.uom.value=ouom;
        document.ItemReceipt.uom.style.visibility="visible";
        document.ItemReceipt.astock.value=avail;
        document.ItemReceipt.availuom.value=ouom;
        document.ItemReceipt.availuom.style.visibility="visible";
    }
    else
    {
       alert("This item has no master entry");
       textHidden()
    }
}

function changeCat()
{
    textHidden();
    clearAll2();
    
    var lb=document.ItemReceipt.lab.value;
    var lab=lb.split("--");
    var order=document.ItemReceipt.orderno.value;
    var catcode=document.ItemReceipt.category.value;
    var category=catcode.split('--');
    var disp=document.getElementById("divwork");
    if(category[1]=='Chemical'||category[1]=='chemical')
        disp.style.display='block';
    else
        disp.style.display='none';
    var url="../../../../../../WQS_ItemReceipt?command=changeCat&LabCode="+lab[0]+"&OrderNo="+order+"&CatCode="+category[0]+"&CatDesc="+category[1];
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        processResponse(req);
    }
    req.send(null);
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
}

function changeItem(command,param)
{
    textHidden();
    document.ItemReceipt.uom.value="";
    document.ItemReceipt.qty.value="";
    document.ItemReceipt.stock.value="";
    document.ItemReceipt.receiveduom.value="";
    document.ItemReceipt.ordereduom.value="";
    document.ItemReceipt.astock.value="";
    document.ItemReceipt.availuom.value="";
    //document.ItemReceipt.bcode.value="";
    var lab=document.ItemReceipt.lab.value;
    var lb=lab.split("--");
    var order=document.ItemReceipt.orderno.value;
    var cat=document.ItemReceipt.category.value;
    var category=cat.split("--");
    var item=document.ItemReceipt.item.value;
    var items=item.split("--"); 
    if(command=='item')
    { 
        if(category[1]!='Chemical'&& category[1]!='chemical')
        {
            var url="../../../../../../WQS_ItemReceipt?command=changeItem&subcmd=item&LabCode="+lb[0]+"&OrderNo="+order+"&CatCode="+category[0]+"&CatDesc="+category[1]+"&ItemCode="+items[0];
            var req=getTransport();
            req.open("GET",url,true);
            req.onreadystatechange=function()
            {
                Response(req);
            }
            req.send(null);
        }
   }
   else if(command=='brand')
   {
        var bcode=document.getElementById("bcode").value;
        var url="../../../../../../WQS_ItemReceipt?command=changeItem&subcmd=brand&LabCode="+lb[0]+"&OrderNo="+order+"&CatCode="+category[0]+"&CatDesc="+category[1]+"&ItemCode="+items[0]+"&bcode="+bcode;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            Response(req);
        }
        req.send(null);
   }
}

function clearAll()
{
    document.ItemReceipt.orderno.selectedIndex=0; 
    document.ItemReceipt.category.selectedIndex=0;     
    clearAll1();
}
function clearAll1()
{
    var itemcombo=document.getElementById("category");
    var child=itemcombo.childNodes;
    for(var c=2;c<=child.length-1;c++)
    {
       itemcombo.removeChild(child[c]);
    }
    clearAll2()
}
function clearAll2()
{
    document.ItemReceipt.item.selectedIndex=0;
    var itemcombo=document.getElementById("item");
    var child=itemcombo.childNodes;
    for(var c=2;c<=child.length-1;c++)
    {
       itemcombo.removeChild(child[c]);
    }
    document.ItemReceipt.bcode.selectedIndex=0; 
    document.ItemReceipt.qty.value="";
    document.ItemReceipt.stock.value="";
    document.ItemReceipt.ordereduom.value="";
    document.ItemReceipt.receiveduom.value="";   
    document.ItemReceipt.astock.value="";
    document.ItemReceipt.availuom.value="";
    
    document.ItemReceipt.rdate.value="";
    document.ItemReceipt.received.value="";
    document.ItemReceipt.uom.value="";
    document.ItemReceipt.remarks.value="";   
}

function Response(req)
{
    if(req.readyState==4)
    {
         if(req.status==200)
          {    
              var baseresponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseresponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue;
              retriveStock(baseresponse);
          }
    }
}

function isNumberKey(evt,item)
{
    var charCode = (evt.which) ? evt.which : event.keyCode
    if(charCode > 31 && (charCode < 48 || charCode > 57))
     return false;
    return true;
}

function textHidden()
{
    document.ItemReceipt.ordereduom.style.visibility="hidden";
    document.ItemReceipt.receiveduom.style.visibility="hidden";
    document.ItemReceipt.uom.style.visibility="hidden";
    document.ItemReceipt.availuom.style.visibility="hidden";
}

function checklength(evt,item)
{
    var charCode = (evt.which) ? evt.which : event.keyCode
    if(charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    var maxqty=document.ItemReceipt.qty.value.length;
    var text = document.ItemReceipt.received.value.length;
    var result = true;
    if(text >= maxqty)
    {
        result = false;	
    }
    if (window.event)
    {
        window.event.returnValue = result;
        return result;
    }
    return true;
}
function checkqty()
{
    var qty=parseInt(document.ItemReceipt.qty.value);
    var stock=document.ItemReceipt.stock.value; 
    var received=document.ItemReceipt.received.value;
    var rec=parseInt(stock)+parseInt(received);
    if(qty<rec)
    {
        alert("Received Quantity is higher than the Ordered Quantity");
        document.ItemReceipt.received.value="";
        document.ItemReceipt.received.focus();        
    }
}