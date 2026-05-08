function getTransport()
{
 var req = false;
 try 
 {
       req= new ActiveXObject("Msxml2.XMLHTTP");
 }
 catch (e) 
 {
       try 
       {
            req = new ActiveXObject("Microsoft.XMLHTTP");
       }
       catch (e2) 
       {
            req = false;
       }
 }
 if (!req && typeof XMLHttpRequest != 'undefined') 
 {
       req = new XMLHttpRequest();
 }   
 return req;
}

function callServer(date_rep,Act_Out)
{

var aco=document.getElementById('aco');
var date_r=document.getElementById('date_of_report');
var flag=true;
if(aco.value.length==0)
{
alert('Kindly Fill up the Activities Carried Out');
aco.focus();
flag=false;
}
else if(date_r.value.length==0)
{
alert('Kindly Fill up the Date Of Report');
date_r.focus();
flag=false;
}

if(flag)
{
    var url="";
    url="../../../daily_status_update?date_of_report="+date_rep+"&activities_carried_out="+Act_Out;
    var req=getTransport();
    

    req.onreadystatechange=function()
    {
    
    if(req.readyState==4)
    {
    if(req.status==200)
    {
    alert(req.responseTEXT);
    document.getElementById('aco').value='';
    document.getElementById('date_of_report').value='';
    }
    }
    
    }
    req.open("Get",url,false);
    req.send(null);
    }

    
}
