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

function changeDistrict()
{
    var block=document.getElementById("block");
    var child=block.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
       block.removeChild(child[c]);
    }
    var dname=document.SampleResult.dname.value;
    var dc=dname.split("--");
    var req=getTransport();
    var url="../../../../../../WQS_SampleResult_CustomerServ?command=changeDistrict&dcode="+dc[0];
    req.open("GET",url,"true");
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);
}

function changeBlock()
{
    var Panchayat=document.getElementById("Panchayat");
    var child=Panchayat.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
       Panchayat.removeChild(child[c]);
    }
    var dname=document.SampleResult.dname.value;
    var dc=dname.split("--");
    var bname=document.SampleResult.block.value;
    var bc=bname.split("--");
    var req=getTransport();
    var url="../../../../../../WQS_SampleResult_CustomerServ?command=changeBlock&dcode="+dc[0]+"&bcode="+bc[0];
    req.open("GET",url,"true");
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);
}

function changePanchayat()
{
    var Habitation=document.getElementById("Habitation");
    var child=Habitation.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
       Habitation.removeChild(child[c]);
    }
    var dname=document.SampleResult.dname.value;
    var dc=dname.split("--");
    var bname=document.SampleResult.block.value;
    var bc=bname.split("--");
    var pname=document.SampleResult.Panchayat.value;
    var pc=pname.split("--");
    var req=getTransport();
    var url="../../../../../../WQS_SampleResult_CustomerServ?command=changePanchayat&dcode="+dc[0]+"&bcode="+bc[0]+"&pcode="+pc[0];
    req.open("GET",url,"true");
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);
}

function changeHabitation()
{
    var location=document.getElementById("location");
    var child=location.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
       location.removeChild(child[c]);
    }
    var dname=document.SampleResult.dname.value;
    var dc=dname.split("--");
    var bname=document.SampleResult.block.value;
    var bc=bname.split("--");
    var pname=document.SampleResult.Panchayat.value;
    var pc=pname.split("--");
    var hname=document.SampleResult.Habitation.value;
    var hc=hname.split("--");
    var req=getTransport();
    var url="../../../../../../WQS_SampleResult_CustomerServ?command=changeHabitation&dcode="+dc[0]+"&bcode="+bc[0]+"&pcode="+pc[0]+"&hcode="+hc[0];
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
            if(cmd=="changeDistrict")
            {
                loadBlock(response);
            }
            else if(cmd=="changeBlock")
            {
                loadPan(response);
            }
            else if(cmd=="changePanchayat")
            {
                loadHab(response);
            }
            else if(cmd=="changeHabitation")
            {
                loadLocation(response);
            }
        }
    }
}

function loadBlock(response)
{
        flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="Success")
        {
                    count=response.getElementsByTagName("count");
                    var combo=document.getElementById("block");
                    for(var i=0;i<count.length;i++)
                    {
                        var bcode=response.getElementsByTagName("bcode")[i].firstChild.nodeValue;
                        var bdesc=response.getElementsByTagName("bdesc")[i].firstChild.nodeValue;
                        var opt =document.createElement("option"); 
                        var text=document.createTextNode(bcode+"--"+bdesc);
                        opt.setAttribute("value",bcode+"--"+bdesc);
                        opt.appendChild(text);
                        combo.appendChild(opt);  
                    }
        }
}

function loadPan(response)
{
        flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="Success")
        {
                    count=response.getElementsByTagName("count");
                    var Panchayat=document.getElementById("Panchayat");
                    for(var i=0;i<count.length;i++)
                    {
                        var pcode=response.getElementsByTagName("pcode")[i].firstChild.nodeValue;
                        var pdesc=response.getElementsByTagName("pdesc")[i].firstChild.nodeValue;
                        var opt =document.createElement("option"); 
                        var text=document.createTextNode(pcode+"--"+pdesc);
                        opt.setAttribute("value",pcode+"--"+pdesc);
                        opt.appendChild(text);
                        Panchayat.appendChild(opt);  
                    }
        }
}
function loadHab(response)
{
        flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="Success")
        {
                    count=response.getElementsByTagName("count");
                    var Habitation=document.getElementById("Habitation");
                    for(var i=0;i<count.length;i++)
                    {
                        var hcode=response.getElementsByTagName("hcode")[i].firstChild.nodeValue;
                        var hdesc=response.getElementsByTagName("hdesc")[i].firstChild.nodeValue;
                        var opt =document.createElement("option"); 
                        var text=document.createTextNode(hcode+"--"+hdesc);
                        opt.setAttribute("value",hcode+"--"+hdesc);
                        opt.appendChild(text);
                        Habitation.appendChild(opt);  
                    }
        }
}

function loadLocation(response)
{
        flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="Success")
        {
                    count=response.getElementsByTagName("count");
                    var Location=document.getElementById("Location");
                    for(var i=0;i<count.length;i++)
                    {
                        var lcode=response.getElementsByTagName("lcode")[i].firstChild.nodeValue;
                        var opt =document.createElement("option"); 
                        var text=document.createTextNode(lcode);
                        opt.setAttribute("value",lcode);
                        opt.appendChild(text);
                        Location.appendChild(opt);  
                    }
        }
}