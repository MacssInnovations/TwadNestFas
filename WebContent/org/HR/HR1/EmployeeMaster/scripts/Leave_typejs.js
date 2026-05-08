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
function callServer(command,param)
{

alert("the command is"+ command);
var LType=document.LeaveType.ltype.value;
var TypeDesc=document.LeaveType.typedesc.value;
var ShortDesc=document.LeaveType.shortdesc.value;
var rm1=document.LeaveType.rm.value;
if(command=="load")
{
var code=param;
var req=getTransport();
var url="../../../../../Leave_typeserv?command=load&cmdltype="+code;
req.open("GET",url,true);
req.onreadystatechange=function()
    {
        loadResponse(req);   
    } 
req.send(null);    
}
else if(command=="Add")
{
var req=getTransport();
var url="../../../../../Leave_typeserv?command=Add&cmdltype="+LType+"&cmdtypedesc="+TypeDesc+"&cmdshortdesc="+ShortDesc+"&cmdrm="+rm1;
req.open("GET",url,true);
req.onreadystatechange=function()
    {
    loadResponse(req);   
    } 
req.send(null);    
}
else if(command=="Delete")
{
alert(" i m in delete");
var req=getTransport();
var url="../../../../../Leave_typeserv?command=Delete&cmdltype="+LType+"&cmdtypedesc="+TypeDesc+"&cmdshortdesc="+ShortDesc+"&cmdrm="+rm1;
req.open("GET",url,true);
req.onreadystatechange=function()
    {
    loadResponse(req);   
    } 
req.send(null);    
}
else if(command=="Update")
{
var req=getTransport();
var url="../../../../../Leave_typeserv?command=Update&cmdltype="+LType+"&cmdtypedesc="+TypeDesc+"&cmdshortdesc="+ShortDesc+"&cmdrm="+rm1;
req.open("GET",url,true);
req.onreadystatechange=function()
    {
    loadResponse(req);   
    } 
req.send(null);    
}
else
{
 document.LeaveType.ltype.value="";
 document.LeaveType.typedesc.value="";
 document.LeaveType.shortdesc.value="";
 document.LeaveType.rm.value="";
 }
}
function loadResponse(req)
{
if(req.readyState==4)
    {
    if(req.status==200)
    {
       var baseResponse=req.responseXML.getElementsByTagName("response1")[0]; 
       var cmd=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;

if(cmd=="load")
    {
    Details(baseResponse);
    }
else if(cmd=="Add")
    {
    AddRow(baseResponse);
    }
else if(cmd=="Delete")
    {
    DeleteRow(baseResponse);
    }
else 
    
    UpdateRow(baseResponse);
    
    }
}
}   
function Details(baseResponse)
{
var tcode=baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
if(tcode=="Success")
{
var first=baseResponse.getElementsByTagName("code")[0].firstChild.nodeValue;
var second=baseResponse.getElementsByTagName("desc")[0].firstChild.nodeValue;
var third=baseResponse.getElementsByTagName("sdesc")[0].firstChild.nodeValue;
var fourth=baseResponse.getElementsByTagName("remarks")[0].firstChild.nodeValue;

 document.LeaveType.ltype.value=first;
 document.LeaveType.typedesc.value=second;
 document.LeaveType.shortdesc.value=third;
 document.LeaveType.rm.value=fourth;
 }
else
{
    alert("Failed to Load");
    }
}

function DeleteRow(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
      if(flag=="Success")
      {
           alert("Record Deleted Successfully.");
           var code1=baseResponse.getElementsByTagName("code")[0].firstChild.nodeValue;
           var tb=document.getElementById("tbList"); 
           var r=document.getElementById(code1);  
           var ri=r.rowIndex; 
           tb.deleteRow(ri-1);
           clearAll();
      }
      else
      {
          alert("Unable to Delete The Record");
         
      }    
}
 function UpdateRow(baseResponse)
    {
     
      var flag=baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
       if(flag=="Success")
       {   
           alert("Record Updated Successfully.");
                     var items=new Array();
                     items[0]=document.LeaveType.ltype.value;
                     items[1]=document.LeaveType.typedesc.value;
                     items[2]=document.LeaveType.shortdesc.value;
                     items[3]=document.LeaveType.rm.value;
                      var r=document.getElementById(items[0]);      
                      var rcells=r.cells;
                      rcells.item(2).firstChild.nodeValue=items[1];
                      rcells.item(3).firstChild.nodeValue=items[2];
                      rcells.item(4).firstChild.nodeValue=items[3];
                     
       }
       else
       {
           alert("cant update values");
       }                                  
    } 

function AddRow(baseResponse)
{
alert("Data is added in database sucessfully");
var flag=baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
if(flag=="Success")
    {
    var items=new Array();
    items[0]=document.LeaveType.ltype.value;
    items[1]=document.LeaveType.typedesc.value;
    items[2]=document.LeaveType.shortdesc.value;
    items[3]=document.LeaveType.rm.value;
        var tbody=document.getElementById("tbList");
        var mycurrent_row=document.createElement("TR");
        mycurrent_row.setAttribute("align","left");
        mycurrent_row.id=items[0];
        mycurrent_row.setAttribute("id",items[0]);
   
        var cell=document.createElement("TD");
        cell.setAttribute("align","center");
        var anc=document.createElement("A");       
        var url="javascript:callServer('load','"+items[0] + "')";              
        anc.href=url;
        var txtedit=document.createTextNode("Edit");
          anc.appendChild(txtedit);
        cell.appendChild(anc);
        mycurrent_row.appendChild(cell);
        
         var ce=document.createElement("TD");
         ce.setAttribute("align","center");
         var txt=document.createTextNode(items[0]);
         ce.appendChild(txt);
         mycurrent_row.appendChild(ce);
         
        var i=0;
        var cell2;
        for(i=1;i<=3;i++)
            {                                           
               cell2=document.createElement("TD");
               cell2.setAttribute("align","center");
               var currenttext=document.createTextNode(items[i]);                         
               cell2.appendChild(currenttext);       
               mycurrent_row.appendChild(cell2);      
             }  
              tbody.appendChild(mycurrent_row); 
              document.LeaveType.ltype.value="";
              document.LeaveType.typedesc.value="";
              document.LeaveType.shortdesc.value="";
              document.LeaveType.rm.value="";
             document.LeaveType.ltype.focus();   
              }
     
     else
            {
                alert("Failed to Insert Values");
            }  
     } 

 function toLoadSlNo()
    {
    var url="../../../../../Leave_typeserv1?command=LoadSN";
    var req=getTransport();
    req.open("Get",url,true);
    req.onreadystatechange=function()
        {
        LoadSNo(req);
        }
    req.send(null);
    }
    
    function LoadSNo(req)
    {
     if(req.readyState==4)
        {       
        if(req.status==200)
            {
            var baseres=req.responseXML.getElementsByTagName("response1")[0];
            var tagcmd=baseres.getElementsByTagName("command")[0];
            var cmd=tagcmd.firstChild.nodeValue;
            if(cmd=="LoadSN")
                {
                   callFromDb(baseres);
                }
            }
        }
    }
    
   
    function callFromDb(baseres)
    {
        var fl=baseres.getElementsByTagName("flag1")[0].firstChild.nodeValue;
        if(fl=="Success")
            {
                var sn=baseres.getElementsByTagName("code")[0].firstChild.nodeValue;
                document.LeaveType.ltype.value=sn;
                document.LeaveType.ltype.disabled=false;                
                document.LeaveType.typedesc.focus();                
            }
        else
                alert("unable to load");
    }   