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
                       alert("Dont leave Date Of Issue  field is empty");
                       return false;
                  }
                  if((document.getElementById("issued").value=="") || (document.getElementById("issued").value.length<=0))
                  {
                       alert("Dont leave Qantity Issued field is empty");
                       return false;
                  }
                  return true;
}
function changeCat()
{
    checkVibility();
    document.ItemIssue.item.selectedIndex=0;
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
    document.ItemIssue.avail.value="";
    document.ItemIssue.auom.value="";
    document.ItemIssue.uom.value="";
    
    var catcode=document.ItemIssue.category.value;
    var category=catcode.split('--');
    var lb=document.ItemIssue.lab.value;
    var lab=lb.split("--");
    var disp=document.getElementById("divwork");
    if(category[1]=='Chemical'||category[1]=='chemical')
        disp.style.display='block';
    else
        disp.style.display='none';
    var url="../../../../../../WQS_ItemIssue?command=changeCat&CatDesc="+category[1]+"&LabCode="+lab[0];
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
                    retriveStock(baseresponse)
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
         document.ItemIssue.avail.value=stock;
         if(uom=='null')
         uom="";
         document.ItemIssue.auom.value=uom;
         document.ItemIssue.uom.value=uom;
         document.ItemIssue.auom.style.visibility="visible";
         document.ItemIssue.uom.style.visibility="visible";
    }
}

function clearAll()
{
    
    document.ItemIssue.category.selectedIndex=0;
    
    var itemcombo=document.getElementById("item");
    var child=itemcombo.childNodes;
    for(var c=child.length-1;c>=0;c--)
    {
       itemcombo.removeChild(child[c]);
    }
    document.ItemIssue.item.selectedIndex=0;
    
    var option=document.createElement("option");
    var text=document.createTextNode("-- Select Item--");
    option.setAttribute("value","0");
    option.appendChild(text);
    itemcombo.appendChild(option);
    
    document.ItemIssue.avail.value="";
    document.ItemIssue.auom.value="";
    document.ItemIssue.rdate.value="";
    document.ItemIssue.issued.value="";
    document.ItemIssue.uom.value="";
    document.ItemIssue.remarks.value="";   
}

function changeItem(command,param)
{
    checkVibility();
    document.ItemIssue.auom.value="";
    document.ItemIssue.uom.value="";
    document.ItemIssue.issued.value="";
    var lcode=document.ItemIssue.lab.value;
    var lab=lcode.split("--");
    var category=document.ItemIssue.category.value;
    var cat=category.split("--");
    var itm=document.getElementById("item").value;
    var item=itm.split("--");
    if(command=='item')
    { 
        if(cat[1]!='Chemical'&& cat[1]!='chemical')
        {
            url="../../../../../../WQS_ItemIssue?command=changeItem&subcmd=item&LabCode="+lab[0]+"&CatCode="+cat[0]+"&CatDesc="+cat[1]+"&ItemCode="+item[0];
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
        var url="../../../../../../WQS_ItemIssue?command=changeItem&subcmd=brand&LabCode="+lab[0]+"&CatCode="+cat[0]+"&CatDesc="+cat[1]+"&ItemCode="+item[0]+"&bcode="+bcode;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            processResponse(req);
        }
        req.send(null);
   }
}

function isNumberKey(evt,item)
{
    var charCode = (evt.which) ? evt.which : event.keyCode
    if(charCode > 31 && (charCode < 48 || charCode > 57))
     return false;
    return true;
}

function checkVibility()
{
    document.ItemIssue.auom.style.visibility="hidden";
    document.ItemIssue.uom.style.visibility="hidden";
}

function checklength(evt,item)
{
    var charCode = (evt.which) ? evt.which : event.keyCode
    if(charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    var maxqty=document.ItemIssue.avail.value.length;
    var text = document.ItemIssue.issued.value.length;
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
    var qty=parseInt(document.ItemIssue.avail.value);
    var stock=parseInt(document.ItemIssue.issued.value); 
    if(qty<stock)
    {
        alert("Out of stock value");
        document.ItemIssue.issued.value="";
        document.ItemIssue.issued.focus();        
    }
}