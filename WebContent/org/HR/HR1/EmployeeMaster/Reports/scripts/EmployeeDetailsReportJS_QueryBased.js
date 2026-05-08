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

function loadServiceGroup()
{
    var iframe=document.getElementById("divframegroup");
    iframe.innerHTML="";
    iframe.style.visibility="hidden";
    var url="../../../../../../EmployeeDetailsReportServ_QueryBased?command=loadServiceGroup";
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        if(req.readyState==4)
        { 
              if(req.status==200)
              {  
                var iframe=document.getElementById("divframegroup");
                iframe.style.visibility='visible';
                iframe.focus();
                iframe.innerHTML=req.responseText;
              }
        }
    }
    req.send(null);
}
function loadDesignation()
{
    var iframe=document.getElementById("divframedesign");
    iframe.innerHTML="";
    iframe.style.visibility="hidden";
    var servicegroup="";
    if(document.frmEmployee.service_group.length>1)
    {
        for(i=0;i<document.frmEmployee.service_group.length;i++)
        {
                if(document.frmEmployee.service_group[i].checked==true)
                        servicegroup= servicegroup+document.frmEmployee.service_group[i].value +",";
                
        }
    }
    else 
        servicegroup=document.frmEmployee.service_group.value;
    var url="../../../../../../EmployeeDetailsReportServ_QueryBased?command=loadDesignation&servicegroup="+servicegroup;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        if(req.readyState==4)
        { 
              if(req.status==200)
              {  
                var iframe=document.getElementById("divframedesign");
                iframe.style.visibility='visible';
                iframe.focus();
                iframe.innerHTML=req.responseText;
              }
        }
    }
    req.send(null);
}

function elementSelectAll(id)
{
  if(id=="ServiceGroup")
  {
      if(document.frmEmployee.service_group)
      {
            for(i=0;i<document.frmEmployee.service_group.length;i++)
            {
                    document.frmEmployee.service_group[i].checked=true;
                    
            }
        }
        document.frmEmployee.designation.disabled=true;
    }
    else
    {
        if(document.frmEmployee.design)
        {
            if(document.frmEmployee.design.length>1)
            {
                for(i=0;i<document.frmEmployee.design.length;i++)
                {
                        document.frmEmployee.design[i].checked=true;
                        
                }
            }
            else
            {
                document.frmEmployee.design.checked=true;
            }
        }
    }
}
function elementclose(id)
{
    if(id=="ServiceGroup")
    {
        var iframe=document.getElementById("divframegroup");
        document.frmEmployee.designation.disabled=true;
    }
    else
        var iframe=document.getElementById("divframedesign");
    iframe.style.visibility='hidden';
}
function checkDesignation()
{
        var count=0;
        for(i=0;i<document.frmEmployee.service_group.length;i++)
        {
                if(document.frmEmployee.service_group[i].checked==true)
                    count++;    
        }
        if(count==document.frmEmployee.service_group.length)
            document.frmEmployee.designation.disabled=true;
        else if(count>0)
            document.frmEmployee.designation.disabled=false;
        else
            document.frmEmployee.designation.disabled=true;
}
function callFunction()
{
    var servicegroup=""
    if(document.frmEmployee.service_group)
    {
        if(document.frmEmployee.service_group.length>1)
        {
            for(i=0;i<document.frmEmployee.service_group.length;i++)
            {
                    if(document.frmEmployee.service_group[i].checked==true)
                            servicegroup= servicegroup+document.frmEmployee.service_group[i].value +",";
                    
            }
        }
        else 
            servicegroup=document.frmEmployee.service_group.value;
    }
    var designation="";
    if(document.frmEmployee.design)
    {
        if(document.frmEmployee.design.length>1)
        {
            for(i=0;i<document.frmEmployee.design.length;i++)
            {
                    if(document.frmEmployee.design[i].checked==true)
                            designation= designation+document.frmEmployee.design[i].value +",";
                    
            }
        }
        else 
            designation=document.frmEmployee.design.value;
    }
    var community=document.frmEmployee.Community.value;
    var district=document.frmEmployee.Native_District.value;
    var qualification=document.frmEmployee.Qualification.value;
    var specialisation=document.frmEmployee.Specialisation.value;
    var url="../../../../../../EmployeeDetailsReportServ_QueryBased?servicegroup="+servicegroup+"&designation="+designation+"&community="+community+"&district="+district+"&qualification="+qualification+"&specialisation="+specialisation;
    document.frmEmployee.action=url;
    document.frmEmployee.method="post";
    document.frmEmployee.submit();
}