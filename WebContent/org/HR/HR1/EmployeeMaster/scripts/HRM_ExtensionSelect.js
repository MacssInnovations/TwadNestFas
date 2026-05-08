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
    if(!req && typeof XMLHttpRequest != undefined)
        {
        req=new XMLHttpRequest();
        }
   return req;
   } 
   
  function empdet()
  {
  var id=document.LeaveSelection.eid.value;
  var req=getTransport();
  var url="../../../../../HRM_LeaveExtensionServ?command=selection&empid="+id;
  req.open("GET",url,true);
  req.onreadystatechange=function()
    {
     loadResponse(req);   
    } 
  req.send(null);    
  }
  
  function loadResponse(req)
  {
  if(req.readyState==4)
  {
    if(req.status==200)
    {
       var baseResponse=req.responseXML.getElementsByTagName("response1")[0];
       //alert(req.responseXML.getElementsByTagName("response1")[0]);
       var cmd=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
       details(baseResponse);
    }
  }
  }
function details(baseResponse)
{
  var tcode=baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
  if(tcode=="Success")
  {
  var name=baseResponse.getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue;
  document.LeaveSelection.ename.value=name;
  var count=baseResponse.getElementsByTagName("count1")[0].firstChild.nodeValue;
  var service=baseResponse.getElementsByTagName("count");
  if(service)
    {
    items1=new Array(); 
    var trow=document.getElementById("tb");
    try{
             trow.innerHTML="";
       }
    catch(e)
       {
             trow.innerText="";
       }      
    for(i=0;i<count;i++)
    {
        
        var items=new Array();
        var items2=new Array();
        var items3=new Array();
        var items4=new Array();
        var items5=new Array();
        var ids=new Array();
          
         ids[i]=service[i].getElementsByTagName("REQUEST_SLNO")[0].firstChild.nodeValue;
         items[i]=service[i].getElementsByTagName("REQUEST_SLNO")[0].firstChild.nodeValue;
         items2[i]=service[i].getElementsByTagName("LEAVE_FROM_DATE")[0].firstChild.nodeValue;
         items3[i]=service[i].getElementsByTagName("LEAVE_TO_DATE")[0].firstChild.nodeValue;
         items4[i]=service[i].getElementsByTagName("LEAVE_TYPE_DESC_SHORT")[0].firstChild.nodeValue;
         items5[i]=service[i].getElementsByTagName("LEAVE_REQUEST_DATE")[0].firstChild.nodeValue;
         
         
         var tbody=document.getElementById("tb");
         var mycurrent_row=document.createElement("TR");
         mycurrent_row.id=items[i];
        
         var cell1=document.createElement("TD");
         var cell2=document.createElement("TD");
         var cell3=document.createElement("TD");
         var cell4=document.createElement("TD");
         var cell5=document.createElement("TD");
         var cell6=document.createElement("TD");
             
     var hidden1=document.createElement("input");
     hidden1.type="radio";
     hidden1.name="sel";
     hidden1.id='sel' +i;
     hidden1.setAttribute("onclick","pick(this)");
     hidden1.value=ids[i];
     cell1.appendChild(hidden1);
     cell1.setAttribute("align","center");
     mycurrent_row.appendChild(cell1);
   
     var reqid=document.createTextNode(items[i]);
     cell2.appendChild(reqid);
                      
     var hidden2=document.createElement("input");
     hidden2.type="hidden";
     hidden2.name="reqid1";
     hidden2.value=items[i];
     cell2.appendChild(hidden2);
     cell2.setAttribute("align","center");
     mycurrent_row.appendChild(cell2); 
            
     var fdate=document.createTextNode(items2[i]);
     cell3.appendChild(fdate);
            
     var hidden3=document.createElement("input");
     hidden3.type="hidden";
     hidden3.name="fdate1";
     hidden3.value=items2[i];
     cell3.appendChild(hidden3);
     cell3.setAttribute("align","center");
     mycurrent_row.appendChild(cell3);
            
     var tdate=document.createTextNode(items3[i]);
     cell4.appendChild(tdate);
            
     var hidden4=document.createElement("input");
     hidden4.type="hidden";
     hidden4.name="tdate1";
     hidden4.value=items3[i];
     cell4.appendChild(hidden4);
     cell4.setAttribute("align","center");
     mycurrent_row.appendChild(cell4);
            
     var tdesc=document.createTextNode(items4[i]);
     cell5.appendChild(tdesc);
            
     var hidden5=document.createElement("input");
     hidden5.type="hidden";
     hidden5.name="tdesc1";
     hidden5.value=items3[i];
     cell5.appendChild(hidden5);
     cell5.setAttribute("align","center");
     mycurrent_row.appendChild(cell5);
     
     var rdate=document.createTextNode(items5[i]);
     cell6.appendChild(rdate);
            
     var hidden6=document.createElement("input");
     hidden6.type="hidden";
     hidden6.name="rdate1";
     hidden6.value=items5[i];
     cell6.appendChild(hidden5);
     cell6.setAttribute("align","center");
     mycurrent_row.appendChild(cell6);
                                
     tbody.appendChild(mycurrent_row);                 
     }
    }
   }
 }
     
function btsubmit()
{
var v=document.getElementsByName("sel");
if(v)
{
    for(i=0;i<v.length;i++)
    {
        if(v[i].checked==true)
        {
            var rid=v[i].value;
            var eid=document.LeaveSelection.eid.value;
            var req=getTransport();
            var url="../../../../../HRM_LeaveExtensionServ?command=radio&cmdid="+eid+"&cmdrid="+rid;
            req.open("GET",url,true);
            req.send(null); 
            
            Minimize();
            opener.doParentRid(v[i].value);
            return true;
        }
       }
    }
}
function Minimize() 
{
window.resizeTo(0,0);
window.screenX = screen.width;
window.screenY = screen.height;
opener.window.focus(); 

}
function pick(t)
{
    s=t.value;
}

