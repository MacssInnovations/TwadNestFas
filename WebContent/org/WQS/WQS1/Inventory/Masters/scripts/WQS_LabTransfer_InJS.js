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

function changeLab()
{
    var flab=document.transfer.flab.value;
    flab=flab.split('--');
    var lb=document.transfer.tlab.value;
    tlab=lb.split("--");
    var url="../../../../../../WQS_LabTransfer_InServ?command=changeLab&tlab="+tlab[0]+"&flab="+flab[0];
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        processResponse(req);
    }
    req.send(null);
}

function changeCat()
{
    checkvisibility();
    document.transfer.item.selectedIndex=0;
    var itemcombo=document.getElementById("item");
    var child=itemcombo.childNodes;
    for(var c=child.length-1;c>=0;c--)
    {
       itemcombo.removeChild(child[c]);
    }
    var option=document.createElement("option");
    var text=document.createTextNode("-- Select Item--");
    option.setAttribute("value","0");
    option.appendChild(text);
    itemcombo.appendChild(option);
    document.transfer.qty.value="";
    document.transfer.auom.value="";
    document.transfer.rdate.value="";
    document.transfer.adate.value="";
    
    var catcode=document.transfer.category.value;
    var category=catcode.split('--');    
    var lb=document.transfer.tlab.value;
    var tlab=lb.split("--");
    var flb=document.transfer.flab.value;
    var flab=flb.split("--");
    var url="../../../../../../WQS_LabTransfer_InServ?command=changeCat&CatDesc="+category[1]+"&tlab="+tlab[0]+"&flab="+flab[0];
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
              if(command=='changeLab')
              {
                    retriveCategory(baseresponse);
              }
              if(command=='changeCat')
              {
                    retriveItem(baseresponse);
              }
              if(command=='changeItem')
              {
                    retriveStock(baseresponse)
              }
          }
    }
}
function retriveCategory(baseresponse)
{
    var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=='Success')
    {
         var count=baseresponse.getElementsByTagName("count");
         var catcombo=document.getElementById("category");
         for(var i=0;i<count.length;i++)
         {
            var CatCode=baseresponse.getElementsByTagName("CatCode");
            var CatDesc=baseresponse.getElementsByTagName("CatDesc");
            var code=CatCode.item(i).firstChild.nodeValue;
            var desc=CatDesc.item(i).firstChild.nodeValue;
            var opt =document.createElement("option"); 
            var text=document.createTextNode(code+"--"+desc);
            opt.setAttribute("value",code+"--"+desc);
            opt.appendChild(text);
            catcombo.appendChild(opt);  
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
         var sdate=baseresponse.getElementsByTagName("sdate")[0].firstChild.nodeValue;
         var uom=baseresponse.getElementsByTagName("uom")[0].firstChild.nodeValue;
         document.transfer.qty.value=stock;
         document.transfer.rdate.value=sdate;
         if(uom=='null')
         uom="";
         document.transfer.auom.value=uom;
         document.transfer.auom.style.visibility="visible";
    }
}

function clearAll()
{
    
    document.transfer.category.selectedIndex=0;
    checkvisibility();
    var itemcombo=document.getElementById("item");
    var child=itemcombo.childNodes;
    for(var c=child.length-1;c>=0;c--)
    {
       itemcombo.removeChild(child[c]);
    }
    document.transfer.item.selectedIndex=0;
    
    var option=document.createElement("option");
    var text=document.createTextNode("-- Select Item--");
    option.setAttribute("value","0");
    option.appendChild(text);
    itemcombo.appendChild(option);
    
    document.transfer.qty.value="";
    document.transfer.auom.value="";
    document.transfer.rdate.value="";
    document.transfer.adate.value="";
}

function changeItem()
{
    checkvisibility();
    document.transfer.auom.value="";
    document.transfer.rdate.value="";
    document.transfer.adate.value="";
    var lb=document.transfer.tlab.value;
    var tlab=lb.split("--");
    var flb=document.transfer.flab.value;
    var flab=flb.split("--");
    var category=document.transfer.category.value;
    var cat=category.split("--");
    var itm=document.getElementById("item").value;
    var item=itm.split("--");
    url="../../../../../../WQS_LabTransfer_InServ?command=changeItem&tlab="+tlab[0]+"&flab="+flab[0]+"&CatCode="+cat[0]+"&CatDesc="+cat[1]+"&ItemCode="+item[0];
    var req=getTransport();
    req.open("GET",url,true);       
    req.onreadystatechange=function()
    {
            processResponse(req);
    }  
    req.send(null); 
}

function checkvisibility()
{
    document.transfer.auom.style.visibility="hidden";
}