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

function retriveValues()
{
    var ctcode="";    
    var catdesc=document.PurchaseDetails.cat.value;
    var icode=document.PurchaseDetails.icode.value;
    var idesc=document.PurchaseDetails.idesc.value;
    var qty=document.PurchaseDetails.qty.value;
    var rem=document.PurchaseDetails.rem.value;
    var catcombo=document.getElementById("category");
    for(var c=catcombo.length-1;c>=0;c--)
    {
        var ct=catcombo.options[c].text;
        catc=ct.split("--");
        if(catdesc==catc[1])
        {
            ctcode=catc[0];
        }
    }
    document.PurchaseDetails.category.value=ctcode+"--"+catdesc;
    document.PurchaseDetails.order.value=qty;
    document.PurchaseDetails.remarks.value=rem;
    changeitem(icode,idesc);
    
}

function changeitem(a,b)
{
    var lcode=document.PurchaseDetails.lcode.value;
    var supcode=document.PurchaseDetails.sna.value;
    var catcode=document.PurchaseDetails.category.value;
    var category=catcode.split('--');
    var url="../../../../../../WQS_PurchaseOrder_Popup?command=changeCat&lab="+lcode+"&CatDesc="+category[1]+"&Supcode="+supcode;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
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
                             document.PurchaseDetails.item.selectedIndx=0;
                             document.PurchaseDetails.submit.focus();
                             document.PurchaseDetails.item.value=a+"--"+b;
                             //document.PurchaseDetails.submit.focus();
                             
                        }
                  }
              }
        }
    }
    req.send(null);
}


function changeCat()
{
    var lcode=document.PurchaseDetails.lcode.value;
    document.PurchaseDetails.item.selectedIndex=0;
    var itemcombo=document.getElementById("item");
    var child=itemcombo.childNodes;
    try{itemcombo.innerHTML="";}
    catch(e){itemcombo.innerText="";}
   /* for(var c=child.length-1;c>=0;c--)
    {
       itemcombo.removeChild(child[c]);
    }
    var option=document.createElement("option");
    var text=document.createTextNode("-- Select Item--");
    option.setAttribute("value","0");
    option.appendChild(text);
    itemcombo.appendChild(option);*/
    //alert(document.PurchaseDetails.uom.value);
    document.PurchaseDetails.item.value="";
    document.PurchaseDetails.order.value="";
    document.PurchaseDetails.remarks.value="";
    
    
    var Supcode=document.PurchaseDetails.sna.value;
    var catcode=document.PurchaseDetails.category.value;
    var category=catcode.split('--');
    var url="../../../../../../WQS_PurchaseOrder_Popup?command=changeCat&lab="+lcode+"&CatDesc="+category[1]+"&Supcode="+Supcode;
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
           
         var opt1=document.createElement("option"); 
         var text1=document.createTextNode("--Select Item--");
         opt1.setAttribute("value","0");
         opt1.appendChild(text1);
         itemcombo.appendChild(opt1);    
                 
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
            var sno=document.PurchaseDetails.sno.value;
            var catcode=document.PurchaseDetails.category.value;
            var cat=catcode.split("--");
            var code=document.PurchaseDetails.item.value;
            var order=document.PurchaseDetails.order.value;
            var uom=document.PurchaseDetails.uom.value;
            var remarks=document.PurchaseDetails.remarks.value;
            var item=code.split("--");
            Minimize();
            opener.doParentUpd(sno,cat[1],item[0],item[1],order,uom,remarks);
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