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

function changeLab()
{
    var combo=document.getElementById("loc");
    if(combo.innerText !='undefined'  && combo.innerText !=null  )
        combo.innerText='';
    else 
        combo.innerHTML='';
    var opt =document.createElement("option"); 
    var text=document.createTextNode("Select");
    opt.setAttribute("value","");
    opt.appendChild(text);
    combo.appendChild(opt);  
    
    var lab=document.SampleResult.lab.value;
    var lb=lab.split("--");
    var req=getTransport();
    var url="../../../../../../WQS_SampleResult_RepServ?command=changeLab&lab="+lb[0];
    req.open("GET",url,"true");
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
            if(cmd=="changeLab")
            {
                flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="Success")
                {
                    count=response.getElementsByTagName("count");
                    var combo=document.getElementById("loc");
                    for(var i=0;i<count.length;i++)
                    {
                        var location=response.getElementsByTagName("location")[i].firstChild.nodeValue;
                        var opt =document.createElement("option"); 
                        var text=document.createTextNode(location);
                        opt.setAttribute("value",location);
                        opt.appendChild(text);
                        combo.appendChild(opt);  
                    }
                }
            }
        }
    }
}