function close_win()
{
window.close();
}
function check()
{
    var lcode=document.getElementById("LabCode").value;
    var LabCode=lcode.split("--");
    var ChemCode=document.getElementById("ChemCode").value;
    var bcode=document.getElementById("bcode").value; 
    
    var url="../../../../../../masterentry?command=check&LabCode="+LabCode[0]+"&ChemCode="+ChemCode+"&bcode="+bcode;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
    manipulate(req);
    }
    req.send(null);

}
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
                 
                  if((document.getElementById("ChemCode").value=="") || (document.getElementById("ChemCode").value.length<=0))
                  {
                       alert("Dont leave Chemical Description field is empty");
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
                       document.SupplierForm.txtdname.focus();
                       return false;
                  }
                  if((document.getElementById("sdate").value=="") && (document.getElementById("sdate").value.length<=0))
                  {
                       alert("Dont leave Stock as on Date field is empty");
                       //document.SupplierForm.txtPro.focus();
                       return false;
                  }
                  
                  if((document.getElementById("reorder").value=="") ||(document.getElementById("reorder").value.length<=0))
                  {
                    alert("Dont leave Reorder Level field is empty");
                    //document.SupplierForm.txtMainId.focus();
                    return false;
                  }
                  return true;
}

function added()
{
var lcode=document.getElementById("LabCode").value;
var LabCode=lcode.split("--");
var ChemCode=document.getElementById("ChemCode").value;
var bcode=document.getElementById("bcode").value;
var qty=document.getElementById("qty").value;
var uom=document.getElementById("uom").value;
var sdate=document.getElementById("sdate").value;
var reorder=document.getElementById("reorder").value;
var rem=document.getElementById("remarks").value;
    var flag=nullCheck();
    if(flag==true)
    {
        var url="../../../../../../masterentry?command=add&LabCode="+LabCode[0]+"&ChemCode="+ChemCode+"&bcode="+bcode+"&qty="+qty+"&uom="+uom+"&sdate="+sdate+"&Reorder="+reorder+"&remarks="+rem;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            manipulate(req);
        }
        req.send(null);
    }
}
function upd()
{
var lcode=document.getElementById("LabCode").value;
var LabCode=lcode.split("--");
var ChemCode=document.getElementById("ChemCode").value;
var bcode=document.getElementById("bcode").value;
var qty=document.getElementById("qty").value;
var uom=document.getElementById("uom").value;
var sdate=document.getElementById("sdate").value;
var reorder=document.getElementById("reorder").value;
var rem=document.getElementById("remarks").value;
    var flag=nullCheck();
    if(flag==true)
    {
        var url="../../../../../../masterentry?command=upd&LabCode="+LabCode[0]+"&ChemCode="+ChemCode+"&bcode="+bcode+"&qty="+qty+"&uom="+uom+"&sdate="+sdate+"&Reorder="+reorder+"&remarks="+rem;
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
function del()
{
var lcode=document.getElementById("LabCode").value;
var LabCode=lcode.split("--");
var ChemCode=document.getElementById("ChemCode").value;
var bcode=document.getElementById("bcode").value;

    var url="../../../../../../masterentry?command=del&LabCode="+LabCode[0]+"&ChemCode="+ChemCode+"&bcode="+bcode;
    var req=getTransport();
    req.open("GET",url,true);
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
               if(cmd=="check")
               {
               var available=response.getElementsByTagName("available");
               if(available.length>0)
               {
               var qty=response.getElementsByTagName("qty")[0].firstChild.nodeValue;
               var udesc=response.getElementsByTagName("udesc")[0].firstChild.nodeValue;
               var sdate=response.getElementsByTagName("sdate")[0].firstChild.nodeValue;
               var reorder=response.getElementsByTagName("reorder")[0].firstChild.nodeValue;
               var remarks=response.getElementsByTagName("remarks")[0].firstChild.nodeValue;
               if(remarks==""||remarks=="null")
                    remarks="";
               document.getElementById("qty").value=qty;
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
               if(reorder=='null')
               reorder="";
               document.getElementById("reorder").value=reorder;
               document.getElementById("remarks").value=remarks;
               document.getElementById("add").disabled=true;
               document.getElementById("update").disabled=false;
               document.getElementById("delet").disabled=false;
               document.getElementById("ChemCode").disabled=true;
               }
               else
               {
               alert("This item has no entry");
               document.getElementById("qty").value="";
               document.getElementById("uom").value="";
               document.getElementById("sdate").value="";
               document.getElementById("reorder").value="";
               document.getElementById("remarks").value="";
               document.getElementById("add").disabled=false;               
               document.getElementById("update").disabled=true;                                             
               document.getElementById("delet").disabled=true;                              
               }                          
               }
               else
               {
               var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
               if(flag=="success")
               {
                    if(cmd=="add")
                    {
                    alert("record added");
                    }
                    if(cmd=="upd")
                    {
                    alert("record updated");
                    }
                    if(cmd=="del")
                    {
                    alert("record deleted");
                    }
               clr();
               }
               else if(flag=="none")
               {
               alert("record exists")
               }
               else
               {
               alert("attempt failed")
               } 
            }
          }
        }  
}


function clr()
{
document.getElementById("ChemCode").disabled=false;
document.MasterChem.ChemCode.value="select";
document.MasterChem.bcode.value="";
document.MasterChem.remarks.value="";
document.MasterChem.qty.value=""; 
document.MasterChem.uom.value=""; 
document.MasterChem.sdate.value=""; 
document.MasterChem.reorder.value=""; 
document.getElementById("add").disabled=false;
document.getElementById("update").disabled=false;
document.getElementById("delet").disabled=false;
document.getElementById("ChemCode").focus();
}

function isNumberKey(evt,item)
{
    var charCode = (evt.which) ? evt.which : event.keyCode
    if(charCode > 31 && (charCode < 48 || charCode > 57))
     return false;
    return true;
}