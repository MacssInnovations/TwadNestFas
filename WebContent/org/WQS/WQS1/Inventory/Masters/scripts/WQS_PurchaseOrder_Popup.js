var seq=0;
var com_id="";
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

function checkLoad()
{
    var c=document.PurchaseDetails.c.value;
    //alert(c);
    if(c<=0)
    {
        alert("this supplier has no items to supply");
    }
}
function changeCat()
{
   // alert(document.PurchaseDetails.category.value);
    document.PurchaseDetails.item.selectedIndex=0;
    document.PurchaseDetails.uom.selectedIndex=0;
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
        
    var lab=document.PurchaseDetails.labcode.value;
    var sup=document.PurchaseDetails.scode.value;
    var scode=sup.split("--");
    var catcode=document.PurchaseDetails.category.value;
    var category=catcode.split('--');
    var url="../../../../../../WQS_PurchaseOrder_Popup?command=changeCat&lab="+lab+"&CatDesc="+category[1]+"&Supcode="+scode[0];
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
                  else if(command=='checkuom')
                  {
                        retriveuom(baseresponse);
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
}

function checkuom()
{
    document.PurchaseDetails.uom.disabled=false;
    document.PurchaseDetails.uom.selectedIndex=0;
    var catcode=document.PurchaseDetails.category.value;
    var category=catcode.split('--');
    var item=document.PurchaseDetails.item.value;
    var icode=item.split("--");
    var url="../../../../../../WQS_PurchaseOrder_Popup?command=checkuom&Category="+category[1]+"&Item="+icode[1];
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        processResponse(req);
    }
    req.send(null);
}

function retriveuom(baseresponse)
{
    var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=='Success')
    {
         var uom=baseresponse.getElementsByTagName("uom")[0].firstChild.nodeValue;
         if(uom=='null')
         {
          uom="";
          document.PurchaseDetails.uom.disabled=false;
         }
         else
         {
            document.PurchaseDetails.uom.value=uom;
            document.PurchaseDetails.uom.disabled=true;
         }
    }
}

function nullCheck()
{
                 
                  if((document.getElementById("category").value=="") || (document.getElementById("category").value.length<=0))
                  {
                       alert("Dont leave Category field is empty");
                       return false;
                  }
                  if((document.getElementById("item").value=="") || (document.getElementById("item").value.length<=0))
                  {
                       alert("Dont leave Item field is empty");
                       return false;
                  }
                  if((document.getElementById("order").value=="") || (document.getElementById("order").value.length<=0))
                  {
                       alert("Dont leave Qantity Ordered field is empty");
                       return false;
                  }
                  if((document.getElementById("uom").value=="") || (document.getElementById("uom").value.length<=0))
                  {
                       alert("Dont leave Unit of Measurement field is empty");
                       return false;
                  }
                  return true;
}


function btnsubmit()
{
    var flag=nullCheck();
    if(flag==true)
    {
            var catcode=document.PurchaseDetails.category.value;
            var cat=catcode.split("--");
            var code=document.PurchaseDetails.item.value;
            var order=document.PurchaseDetails.order.value;
            var uom=document.PurchaseDetails.uom.value;
            var remarks=document.PurchaseDetails.remarks.value;
            if(remarks=="")
                remarks="-";
            var item=code.split("--");
            opener.doParentVal(cat[1],item[0],item[1],order,uom,remarks);
            Minimize();
            return true;
    }
}

function Minimize() 
{
    window.close();
    //window.screenX = screen.width;
    //window.screenY = screen.height;
    opener.window.focus();
}
function isNumberKey(evt,item)
{
    var charCode = (evt.which) ? evt.which : event.keyCode
    if(charCode > 31 && (charCode < 48 || charCode > 57))
     return false;
    return true;
}