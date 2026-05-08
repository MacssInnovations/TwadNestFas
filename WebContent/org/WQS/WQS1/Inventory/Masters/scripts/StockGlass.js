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
    if (!req && typeof XMLHttpRequest != 'undefined') 
        {
        req=new XMLHttpRequest();
        }
   return req;
   
}  

function nullCheck()
{
                 
                  if((document.getElementById("GlassCode").value=="") || (document.getElementById("GlassCode").value.length<=0))
                  {
                       alert("Dont leave Glassware Description field is empty");
                       return false;
                  }
                  if((document.getElementById("qty").value=="") || (document.getElementById("qty").value.length<=0))
                  {
                       alert("Dont leave Quantity Available field is empty");
                       return false;
                  }
                  if((document.getElementById("uom").value=="") || (document.getElementById("uom").value.length<=0))
                  {
                       alert("Dont leave Unit of Measurement field is empty");
                       return false;
                  }
                  if((document.getElementById("sdate").value=="") && (document.getElementById("sdate").value.length<=0))
                  {
                       alert("Dont leave Stock as on Date field is empty");
                       //document.SupplierForm.txtPro.focus();
                       return false;
                  }
                  return true;
}

function load()
{
document.getElementById("LabCode").focus();
}
function close_win()
{
window.close();
}
function getNosAvailable()
{
var lcode=document.getElementById("LabCode").value;
var LabCode=lcode.split("--");
var GlassCode=document.getElementById("GlassCode").value;
    var url="../../../../../../StockEntryGlass?command=getNos&LabCode="+LabCode[0]+"&GlassCode="+GlassCode;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
    manipulate(req);
    }
    req.send(null);
}

function added()
{
var lcode=document.getElementById("LabCode").value;
var LabCode=lcode.split("--");
var GlassCode=document.getElementById("GlassCode").value;
var qty=document.getElementById("qty").value;
var uom=document.getElementById("uom").value;
var sdate=document.getElementById("sdate").value;
var remarks=document.getElementById("remarks").value;
    var flag=nullCheck();
    if(flag==true)
    {
        var url="../../../../../../StockEntryGlass?command=add&LabCode="+LabCode[0]+"&GlassCode="+GlassCode+"&nos="+qty+"&uom="+uom+"&sdate="+sdate+"&remarks="+remarks;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
        manipulate(req);
        }
        req.send(null);
    }
}
function load()
{
document.getElementById("LabCode").focus();
}
function upd()
{
var lcode=document.getElementById("LabCode").value;
var LabCode=lcode.split("--");
var GlassCode=document.getElementById("GlassCode").value;
var qty=document.getElementById("qty").value;
var uom=document.getElementById("uom").value;
var sdate=document.getElementById("sdate").value;
var remarks=document.getElementById("remarks").value;
    var flag=nullCheck();
    if(flag==true)
    {
        var url="../../../../../../StockEntryGlass?command=upd&LabCode="+LabCode[0]+"&GlassCode="+GlassCode+"&nos="+qty+"&uom="+uom+"&sdate="+sdate+"&remarks="+remarks;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
        manipulate(req);
        }
        req.send(null);
    }
}

function del()
{
var lcode=document.getElementById("LabCode").value;
var LabCode=lcode.split("--");
var GlassCode=document.getElementById("GlassCode").value;

    var url="../../../../../../StockEntryGlass?command=del&LabCode="+LabCode[0]+"&GlassCode="+GlassCode;

    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
    manipulate(req);
    }
    req.send(null);

}
function close_win()
{
window.close();
}
function clr()
{
               document.getElementById("GlassCode").value="select";
               document.getElementById("GlassCode").disabled=false;               
               document.getElementById("qty").value="";
               document.getElementById("uom").value="";
               document.getElementById("sdate").value="";
               document.getElementById("remarks").value="";
               document.getElementById("add").disabled=false;
               document.getElementById("update").disabled=false;
               document.getElementById("delet").disabled=false;               
}
function manipulate(req)
{
        if(req.readyState==4)
        {
          if(req.status==200)
          {
               var response=req.responseXML.getElementsByTagName("response")[0];
               //alert(response);
               var cmd=response.getElementsByTagName("command")[0].firstChild.nodeValue;
               if(cmd=="getNos")
               {    
                   var available=response.getElementsByTagName("available");
                   if(available.length>0)
                   {
                       var nos=response.getElementsByTagName("Numbers")[0].firstChild.nodeValue;
                       var udesc=response.getElementsByTagName("udesc")[0].firstChild.nodeValue;
                       var sdate=response.getElementsByTagName("sdate")[0].firstChild.nodeValue;
                       var rem=response.getElementsByTagName("Remarks")[0].firstChild.nodeValue;
                       if(rem==""||rem=='null')
                            rem="";
                       document.getElementById("qty").value=nos;
                       document.getElementById("uom").value=udesc;
                       if(sdate=='null')
                       {
                            sdate="";
                       }
                       else
                       {
                            sdate=sdate.split("-");
                            sdate=sdate[2]+"/"+sdate[1]+"/"+sdate[0];
                       }
                       document.getElementById("sdate").value=sdate;
                       document.getElementById("remarks").value=rem;
                       document.getElementById("add").disabled=true;
                       document.getElementById("update").disabled=false;
                       document.getElementById("delet").disabled=false;
                       document.getElementById("GlassCode").disabled=true;
                   }
                   else
                   {
                       alert("This item has no entry");
                       document.getElementById("qty").value="";
                       document.getElementById("uom").value="";
                       document.getElementById("sdate").value="";
                       document.getElementById("remarks").value="";
                       //document.getElementById("qty").focus();
                       document.getElementById("add").disabled=false;               
                       document.getElementById("update").disabled=true;                                             
                       document.getElementById("delet").disabled=true;                           
                   }
               }
               var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
               if(flag=="success")
               {
                 if(cmd=="add")
                    {
                    alert("record added");
                    clr();
                    }
                 else if(cmd=="upd")
                    {
                    alert("record updated");
                    clr();
                    }
                else if(cmd=="del")
                    {
                    alert("record deleted")
                    clr();
                    }
                    document.getElementById("LabCode").focus();
               }
               else
               {
               if(cmd=="add")
                    {
                    alert("failed to add values");
                    }
                 else if(cmd=="upd")
                    {
                    alert("failed to update values");
                    }
                else if(cmd=="del")
                    {
                    alert("failed to delete values");
                    }
                }
                clr();
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