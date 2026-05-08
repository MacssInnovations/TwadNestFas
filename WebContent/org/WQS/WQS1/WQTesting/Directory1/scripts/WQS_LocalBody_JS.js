function getTransport()
{
    var req=false;
    try
    {
        req=new ActiveXObject("Msxml2.XMLHTTP");
    }catch(e1)
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
    if (!req && typeof XMLHttpRequest != 'undefined') 
    {
        req=new XMLHttpRequest();
    }
    return req;
   
}    
function callDistrict(val)
{
    if(document.LocalBody.ltype[val].checked==true)
    {
        document.LocalBody.district.disabled=false;
        document.LocalBody.lbody.disabled=true;
        document.LocalBody.design.disabled=true;
        document.LocalBody.district.selectedIndex=0;
        document.LocalBody.lbody.selectedIndex=0;
        document.LocalBody.design.selectedIndex=0;
        req=getTransport();
        var ltype="";
        if(val==0)
            ltype="Corporation";
        else if(val==1)
            ltype="Municipality";
        else if(val==2)
            ltype="UTP";
        else
            ltype="RTP";
        var url="../../../../../../WQS_Customer_MasterServ?command=changeType&ltype="+ltype;
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            process_response(req);
        }
        req.send(null);
    }
    else
    {
        document.LocalBody.district.disabled=true;
        document.LocalBody.lbody.disabled=false;
        document.LocalBody.design.disabled=false;
    }
}
function changeDistrict()
{
        document.LocalBody.lbody.disabled=false;
        req=getTransport();
        var ltype="";
        if(document.LocalBody.ltype[0].checked==true)
            ltype="Corporation";
        else if(document.LocalBody.ltype[1].checked==true)
            ltype="Municipality";
        else if(document.LocalBody.ltype[2].checked==true)
            ltype="UTP";
        else
            ltype="RTP";
        var dist_code=document.getElementById("district").value;
        var url="../../../../../../WQS_Customer_MasterServ?command=changeDistrict&ltype="+ltype+"&dist_code="+dist_code;
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            process_response(req);
        }
        req.send(null);
}
function changeLocalBody()
{
        document.LocalBody.design.disabled=false;
}
function process_response(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {
            var response=req.responseXML.getElementsByTagName("response")[0];
            var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            if(command=="changeDistrict")
            {
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="success")
                {
                     var tcount=response.getElementsByTagName("tcount")[0].firstChild.nodeValue;
                     var lbody=document.getElementById("lbody");
                     var child=lbody.childNodes;
                     for(var i=child.length-1;i>1;i--)
                     {
                        lbody.removeChild(child[i]);
                     }
                     for(var j=0;j<tcount;j++)
                     {
                        var habcode=response.getElementsByTagName("code")[j].firstChild.nodeValue;
                        var habname=response.getElementsByTagName("name")[j].firstChild.nodeValue;
                        var opt=document.createElement("option");
                        opt.setAttribute("value",habcode);
                        var txt=document.createTextNode(habname);
                        opt.appendChild(txt);
                        lbody.appendChild(opt);
                     }
                }
            }
            else if(command=="changeType")
            {
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="success")
                {
                     var tcount=response.getElementsByTagName("count");
                     var design=document.getElementById("design");
                     var child=design.childNodes;
                     for(var i=child.length-1;i>1;i--)
                     {
                        design.removeChild(child[i]);
                     }
                     for(var j=0;j<tcount.length;j++)
                     {
                        var designation=response.getElementsByTagName("name")[j].firstChild.nodeValue;
                        var opt=document.createElement("option");
                        opt.setAttribute("value",designation);
                        var txt=document.createTextNode(designation);
                        opt.appendChild(txt);
                        design.appendChild(opt);
                     }
                }
            }
        }
    }
}

function selectLocalBody()
{
    if(document.LocalBody.district.selectedIndex==0||document.LocalBody.district.selectedIndex=="")
    {
        alert("Select District");
    }
    else if(document.LocalBody.lbody.selectedIndex==0||document.LocalBody.lbody.selectedIndex=="")
    {
        alert("Select Local Body");
    }
    else if(document.LocalBody.design.selectedIndex==0||document.LocalBody.design.selectedIndex=="")
    {
        alert("Select Designation");
    }
    else
    {
            var dcode=document.LocalBody.district.value;
            var lbodycode=document.LocalBody.lbody.value;
            var designation=document.LocalBody.design.value;
            Minimize();
            opener.doParentLocal(lbodycode,designation);
            return true;
        
    }
}

function Minimize() 
{
    window.close();
    opener.window.focus();
}