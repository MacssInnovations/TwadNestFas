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
function loadParameter()
{
        var url="../../../../../../WQS_PollutionParamServ?command=loadParameter" ;
       // document.PollutionRep.es.focus();
        var req=getTransport();
        
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            if(req.readyState==4)
            { 
                  if(req.status==200)
                  {  
                     // document.frames("iframregion").document.body.innerHTML=req.responseText;
                     //(document.frames("iframregion").document.body.innerText);
                     var iframe=document.getElementById("pid");
                     iframe.style.visibility='visible';
                     iframe.focus();
                     /* alert(navigator.appName);
                      alert(navigator.appName.indexOf('Microsoft'));
                      if(navigator.appName.indexOf('Microsoft')!=-1)*/
                         iframe.innerHTML=req.responseText;
                  }
            }
        }
        req.send(null);
}

function checkParam(id)
{
    if(document.PollutionRep.chkelement[id].checked==true)
    {
        document.PollutionRep.maxvalue[id].disabled=false;
        document.PollutionRep.minvalue[id].disabled=false;
    }
    else
    {
        document.PollutionRep.maxvalue[id].value="";
        document.PollutionRep.maxvalue[id].disabled=true;
        document.PollutionRep.minvalue[id].value="";
        document.PollutionRep.minvalue[id].disabled=true;
    }
}
function filter_real(evt,item,n,pre)
{
         var charCode = (evt.which) ? evt.which : event.keyCode
         // allow "." for one time 
         if(charCode==46){
                        //	alert("Position of . "+item.value.indexOf("."));
                                if(item.value.indexOf(".")<0)	return (item.value.length>0)?true:false;
                                else return false;
          }
         if (!(charCode > 31 && (charCode < 48 || charCode > 57))){
                // to avoid over flow
                        if(item.value.indexOf(".")<0){
        //			alert("Length without . ="+item.value.length);
                                return (item.value.length<n)?true:false;
                        }
                // dont allow more than 2 precision no's after the point
                        if(item.value.indexOf(".")>0){
                        //	alert("precision count ="+item.value.split(".")[1].length);
                                if(item.value.split(".")[1].length<pre) return true;
                                else return false;
                        }
                        return false;
        }else{
                        return false;
        }
}

function changeDistrict()
{
    var block=document.getElementById("block");
    var child=block.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
       block.removeChild(child[c]);
    }
    var dname=document.PollutionRep.dname.value;
    var dc=dname.split("--");
    var req=getTransport();
    var url="../../../../../../WQS_PollutionParamServ?command=changeDistrict&dcode="+dc[0];
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
    var dname=document.PollutionRep.dname.value;
    var dc=dname.split("--");
    var bname=document.PollutionRep.block.value;
    var bc=bname.split("--");
    var req=getTransport();
    var url="../../../../../../WQS_PollutionParamServ?command=changeBlock&dcode="+dc[0]+"&bcode="+bc[0];
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
    var dname=document.PollutionRep.dname.value;
    var dc=dname.split("--");
    var bname=document.PollutionRep.block.value;
    var bc=bname.split("--");
    var pname=document.PollutionRep.Panchayat.value;
    var pc=pname.split("--");
    var req=getTransport();
    var url="../../../../../../WQS_PollutionParamServ?command=changePanchayat&dcode="+dc[0]+"&bcode="+bc[0]+"&pcode="+pc[0];
    req.open("GET",url,"true");
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);
}
function changeHabitation()
{
    var location=document.getElementById("Location");
    var child=location.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
       location.removeChild(child[c]);
    }
    var dname=document.PollutionRep.dname.value;
    var dc=dname.split("--");
    var bname=document.PollutionRep.block.value;
    var bc=bname.split("--");
    var pname=document.PollutionRep.Panchayat.value;
    var pc=pname.split("--");
    var hname=document.PollutionRep.Habitation.value;
    var hc=hname.split("--");
    var req=getTransport();
    var url="../../../../../../WQS_PollutionParamServ?command=changeHabitation&dcode="+dc[0]+"&bcode="+bc[0]+"&pcode="+pc[0]+"&hcode="+hc[0];
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

function gen_rep()
{
   // var lab=document.PollutionRep.lab.value;
    var fdate=document.PollutionRep.fdate.value;
    var tdate=document.PollutionRep.tdate.value;
    var customer_type=document.PollutionRep.ctype.value;
    var es="",max="",min="";
    for(i=0;i<document.PollutionRep.chkelement.length;i++)
    {
            if(document.PollutionRep.chkelement[i].checked==true)
            {
                    es= es+document.PollutionRep.chkelement[i].value +",";
                    max=max+document.PollutionRep.maxvalue[i].value+",";
                    min=min+document.PollutionRep.minvalue[i].value+",";
            }
    }
    if(customer_type=="Twad")
    {
        var dname=document.PollutionRep.dname.value;
        var block=document.PollutionRep.block.value;
        var Panchayat=document.PollutionRep.Panchayat.value;
        var Habitation=document.PollutionRep.Habitation.value;
        var Location=document.PollutionRep.Location.value;
        if(document.PollutionRep.block.value==""||document.PollutionRep.block.selectedIndex==0)
        {
            var url="../../../../../../WQS_PollutionParamServ?command=District&fdate="+fdate+"&tdate="+tdate+"&dname="+dname+"&es="+es+"&max="+max+"&min="+min;
            document.PollutionRep.action=url
            document.PollutionRep.method="post";
            document.PollutionRep.submit();
        }
        else if(block!=""&&Panchayat=="")
        {
            var url="../../../../../../WQS_PollutionParamServ?command=Block&fdate="+fdate+"&tdate="+tdate+"&dname="+dname+"&block="+block+"&es="+es+"&max="+max+"&min="+min;
            document.PollutionRep.action=url
            document.PollutionRep.method="post";
            document.PollutionRep.submit();
        }
        else if(Panchayat!=""&&Habitation=="")
        {
            var url="../../../../../../WQS_PollutionParamServ?command=Panchayat&fdate="+fdate+"&tdate="+tdate+"&dname="+dname+"&block="+block+"&Panchayat="+Panchayat+"&es="+es+"&max="+max+"&min="+min;
            document.PollutionRep.action=url
            document.PollutionRep.method="post";
            document.PollutionRep.submit();
        }
        else if(Habitation!=""&&Location=="")
        {
            var url="../../../../../../WQS_PollutionParamServ?command=Habitation&fdate="+fdate+"&tdate="+tdate+"&dname="+dname+"&block="+block+"&Panchayat="+Panchayat+"&Habitation="+Habitation+"&es="+es+"&max="+max+"&min="+min;
            document.PollutionRep.action=url
            document.PollutionRep.method="post";
            document.PollutionRep.submit();
        }
        else if(Location!="")
        {
            var url="../../../../../../WQS_PollutionParamServ?command=Location&fdate="+fdate+"&tdate="+tdate+"&dname="+dname+"&block="+block+"&Panchayat="+Panchayat+"&Habitation="+Habitation+"&Location="+Location+"&es="+es+"&max="+max+"&min="+min;
            document.PollutionRep.action=url
            document.PollutionRep.method="post";
            document.PollutionRep.submit();
        }
    }
    else if(customer_type=="Private"||customer_type=="Govt")
    {
            var district=document.PollutionRep.dist.value;
            var location=document.PollutionRep.Location.value;
            if(document.PollutionRep.Location.value==""||document.PollutionRep.Location.selectedIndex==0)
            {
                //alert(" district wise report");
                var url="../../../../../../WQS_PollutionParamServ?command=SourceDistrict&fdate="+fdate+"&tdate="+tdate+"&dname="+district+"&es="+es+"&max="+max+"&min="+min;
                document.PollutionRep.action=url
                document.PollutionRep.method="post";
                document.PollutionRep.submit();
            }
            else if(Location!="")
            {
                var url="../../../../../../WQS_PollutionParamServ?command=SourceLocation&fdate="+fdate+"&tdate="+tdate+"&dname="+district+"&Location="+location+"&es="+es+"&max="+max+"&min="+min;
                document.PollutionRep.action=url
                document.PollutionRep.method="post";
                document.PollutionRep.submit();
            }
            else
            {
                alert("Select District");
            }
    }
    else
    {
        alert("Select Customer Type");
    }
}

function displayfun()
{
    if(document.PollutionRep.ctype.value=="Twad")
    {
        var type=document.getElementById("twaddt");
        type.style.display="block";
        var other=document.getElementById("otherdt");
        other.style.display="none";
        var loc=document.getElementById("loc");
        loc.style.display="block";
    }
    else if(document.PollutionRep.ctype.value=="Private"||document.PollutionRep.ctype.value=="Govt")
    {
        var type=document.getElementById("twaddt");
        type.style.display="none";
        var other=document.getElementById("otherdt");
        other.style.display="block";
        var loc=document.getElementById("loc");
        loc.style.display="block";
    }
}

function changeDist()
{
    var location=document.getElementById("Location");
    var child=location.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
       location.removeChild(child[c]);
    }
    var dname=document.PollutionRep.dist.value;
    var req=getTransport();
    var url="../../../../../../WQS_PollutionParamServ?command=changeDist&dcode="+dname;
    req.open("GET",url,"true");
    req.onreadystatechange=function()
    {
        manip(req);
    }
    req.send(null);
}

function manip(req)
{
     if(req.readyState==4)
    {
        if(req.status==200)
        {
            var response=req.responseXML.getElementsByTagName("response")[0];
            var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="Success")
            {
                var count=response.getElementsByTagName("count");
                var loc=document.getElementById("Location");
                for(var i=0;i<count.length;i++)
                {
                        var lcode=response.getElementsByTagName("lcode")[i].firstChild.nodeValue;
                        var opt =document.createElement("option"); 
                        var text=document.createTextNode(lcode);
                        opt.setAttribute("value",lcode);
                        opt.appendChild(text);
                        loc.appendChild(opt);  
                }
            }
        }
    }
}