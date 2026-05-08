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
function changeLocation()
{
    if(document.SampleResult.ltype.value=="VP")
    {
       document.getElementById("villagepanchayat").style.display="block";
       document.getElementById("lb").style.display="none";
       document.getElementById("lbody").style.display="none";
    }
    else
    {
       document.getElementById("villagepanchayat").style.display="none";
       document.getElementById("lb").style.display="block";
       document.getElementById("lbody").style.display="block";
    }
    var dname=document.getElementById("dname");
    var child=dname.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
       dname.removeChild(child[c]);
    }
    var ctype=document.SampleResult.ctype.value
    var req=getTransport();
    var url="../../../../../../WQS_Periodic_TwoParameters?command=loadDistrict&lab="+lab[0]+"&ctype="+ctype;
    req.open("GET",url,"true");
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);
    /*var ctype=document.SampleResult.ctype.value;
    var dname=document.SampleResult.dname.value;
    dname=dname.split("--");
    var ltype=document.SampleResult.ltype.value;
    var req=getTransport();
    var url="../../../../../../WQS_Periodic_TwoParameters?command=changeLocation&ctype="+ctype+"&dcode="+dname[0]+"&ltype="+ltype;
    req.open("GET",url,"true");
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);*/
    
}
function changeDistrict()
{
    clearAll1();clearAll2();
    var res=checkType();
    if(res==true)
    {
        var lab=document.SampleResult.lab.value;
        lab=lab.split("--");
        var ctype=document.SampleResult.ctype.value;
        var dname=document.SampleResult.dname.value; 
        var ltype="";
        if(document.SampleResult.ltype[0].checked==true)
            ltype=document.SampleResult.ltype[0].value;
        else if(document.SampleResult.ltype[1].checked==true)
            ltype=document.SampleResult.ltype[1].value;
        else if(document.SampleResult.ltype[2].checked==true)
            ltype=document.SampleResult.ltype[2].value;
        else if(document.SampleResult.ltype[3].checked==true)
            ltype=document.SampleResult.ltype[3].value;
        else if(document.SampleResult.ltype[4].checked==true)
            ltype=document.SampleResult.ltype[4].value;
        dname=dname.split("--");
        if(ctype!="Twad")
        {
            var req=getTransport();
            var url="../../../../../../WQS_Periodic_TwoParameters?command=changeDistrict&lab="+lab[0]+"&ctype="+ctype+"&dcode="+dname[0];
            req.open("GET",url,"true");
            req.onreadystatechange=function()
            {
                manipulate(req);
            }
            req.send(null);
        }
        else
        {            
            var url="../../../../../../WQS_Periodic_TwoParameters?command=changeLocationType&lab="+lab[0]+"&ctype="+ctype+"&dcode="+dname[0]+"&ltype="+ltype;
            var req=getTransport();
            req.open("GET",url,true);
            req.onreadystatechange=function()
            {
                manipulate(req);
            }
            req.send(null);
        }
    }
}
function changeLocationType(val)
{    
    clearAll1();clearAll2();
    var lab=document.SampleResult.lab.value;
    lab=lab.split("--");
    var ltype=val;
    if(ltype=="VP")
    {
        document.getElementById("lb").style.display="none";
        document.getElementById("lbody").style.display="none";
        document.getElementById("villagepanchayat").style.display="block";
    }
    else
    {
        document.getElementById("lb").style.display="block";
        document.getElementById("lbody").style.display="block";
        document.getElementById("villagepanchayat").style.display="none";
    }
    var ctype=document.SampleResult.ctype.value
    var dname=document.getElementById("dname");
    var child=dname.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
       dname.removeChild(child[c]);
    }       
    var req=getTransport();
    var url="../../../../../../WQS_Periodic_TwoParameters?command=loadDistrict&lab="+lab[0]+"&ctype="+ctype+"&ltype="+val;
    req.open("GET",url,"true");
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);
}

function changeLocalBody()
{
     var Location=document.getElementById("Location");
    var lc=Location.childNodes;
    for(var c=lc.length-1;c>1;c--)
    {
       Location.removeChild(lc[c]);
    }
    var lab=document.SampleResult.lab.value;
    lab=lab.split("--");
    var ctype=document.SampleResult.ctype.value;
    var ltype="";
    if(document.SampleResult.ltype[0].checked==true)
        ltype=document.SampleResult.ltype[0].value;
    else if(document.SampleResult.ltype[1].checked==true)
        ltype=document.SampleResult.ltype[1].value;
    else if(document.SampleResult.ltype[2].checked==true)
        ltype=document.SampleResult.ltype[2].value;
    else if(document.SampleResult.ltype[3].checked==true)
        ltype=document.SampleResult.ltype[3].value;
    else if(document.SampleResult.ltype[4].checked==true)
        ltype=document.SampleResult.ltype[4].value;
    var dname=document.SampleResult.dname.value; 
    dname=dname.split("--");
    var lbody=document.SampleResult.lbody.value; 
    lbody=lbody.split("--");
    var url="../../../../../../WQS_Periodic_TwoParameters?command=changeLocalBody&lab="+lab[0]+"&ctype="+ctype+"&dcode="+dname[0]+"&ltype="+ltype+"&lbody="+lbody[0];
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);
}
function changeBlock()
{
    clearAll3();
    var lab=document.SampleResult.lab.value;
    lab=lab.split("--");
    var ctype=document.SampleResult.ctype.value;
    var ltype="";
    if(document.SampleResult.ltype[0].checked==true)
        ltype=document.SampleResult.ltype[0].value;
    else if(document.SampleResult.ltype[1].checked==true)
        ltype=document.SampleResult.ltype[1].value;
    else if(document.SampleResult.ltype[2].checked==true)
        ltype=document.SampleResult.ltype[2].value;
    else if(document.SampleResult.ltype[3].checked==true)
        ltype=document.SampleResult.ltype[3].value;
    else if(document.SampleResult.ltype[4].checked==true)
        ltype=document.SampleResult.ltype[4].value;
    var dname=document.SampleResult.dname.value; 
    dname=dname.split("--");
    var block=document.SampleResult.block.value; 
    block=block.split("--");    
    var req=getTransport();
    var url="../../../../../../WQS_Periodic_TwoParameters?command=changeBlock&lab="+lab[0]+"&ctype="+ctype+"&ltype="+ltype+"&dcode="+dname[0]+"&bcode="+block[0];
    req.open("GET",url,"true");
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);
}
function changePanchayat()
{
    clearAll4();
    var lab=document.SampleResult.lab.value;
    lab=lab.split("--");
    var ctype=document.SampleResult.ctype.value;
    var ltype="";
    if(document.SampleResult.ltype[0].checked==true)
        ltype=document.SampleResult.ltype[0].value;
    else if(document.SampleResult.ltype[1].checked==true)
        ltype=document.SampleResult.ltype[1].value;
    else if(document.SampleResult.ltype[2].checked==true)
        ltype=document.SampleResult.ltype[2].value;
    else if(document.SampleResult.ltype[3].checked==true)
        ltype=document.SampleResult.ltype[3].value;
    else if(document.SampleResult.ltype[4].checked==true)
        ltype=document.SampleResult.ltype[4].value;
    var dname=document.SampleResult.dname.value; 
    dname=dname.split("--");
    var block=document.SampleResult.block.value; 
    block=block.split("--");    
    var panchayat=document.SampleResult.Panchayat.value;
    panchayat=panchayat.split("--");
    var req=getTransport();
    var url="../../../../../../WQS_Periodic_TwoParameters?command=changePanchayat&lab="+lab[0]+"&ctype="+ctype+"&ltype="+ltype+"&dcode="+dname[0]+"&bcode="+block[0]+"&pcode="+panchayat[0];
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
    var lab=document.SampleResult.lab.value;
    lab=lab.split("--");
    var ctype=document.SampleResult.ctype.value;
    var ltype="";
    if(document.SampleResult.ltype[0].checked==true)
        ltype=document.SampleResult.ltype[0].value;
    else if(document.SampleResult.ltype[1].checked==true)
        ltype=document.SampleResult.ltype[1].value;
    else if(document.SampleResult.ltype[2].checked==true)
        ltype=document.SampleResult.ltype[2].value;
    else if(document.SampleResult.ltype[3].checked==true)
        ltype=document.SampleResult.ltype[3].value;
    else if(document.SampleResult.ltype[4].checked==true)
        ltype=document.SampleResult.ltype[4].value;
    var dname=document.SampleResult.dname.value; 
    dname=dname.split("--");
    var block=document.SampleResult.block.value; 
    block=block.split("--");    
    var panchayat=document.SampleResult.Panchayat.value;
    panchayat=panchayat.split("--");
    var habitation=document.SampleResult.Habitation.value;
    habitation=habitation.split("--");
    var req=getTransport();
    var url="../../../../../../WQS_Periodic_TwoParameters?command=changeHabitation&lab="+lab[0]+"&ctype="+ctype+"&ltype="+ltype+"&dcode="+dname[0]+"&bcode="+block[0]+"&pcode="+panchayat[0]+"&hcode="+habitation[0];
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
            if(cmd=="loadData")
            {
                retriveData(response);
            }
            else if(cmd=="loadDistrict")
            {
                retriveDistrictData(response);
            }
            else if(cmd=="changeLocation")
            {
                retriveLocationData(response);
            }
            else if(cmd=="changeDistrict")
            {
                loadLocation(response);
            }
            else if(cmd=="changeLocationType")
            {
                loadLocalBody(response);
            }
            else if(cmd=="changeLocalBody")
            {
                loadLocation(response);
            }
            else if(cmd=="changeBlock")
            {
                loadPanchayat(response);
            }
            else if(cmd=="changePanchayat")
            {
                loadHabitation(response);
            }
            else if(cmd=="changeHabitation")
            {
                loadLocation(response);
            }
            else if(cmd=="loadCustomerType")
            {
                getCustomerType(response);
            }
        }
    }
}
function retriveData(response)
{
    var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="Success")
    {
        var count=response.getElementsByTagName("count");
        var combo=document.getElementById("ctype");
        for(var i=0;i<count.length;i++)
        {
            var ctype=response.getElementsByTagName("ctype")[i].firstChild.nodeValue;
            var opt =document.createElement("option"); 
            var text=document.createTextNode(ctype);
            opt.setAttribute("value",ctype);
            opt.appendChild(text);
            combo.appendChild(opt);  
        }
    }
}
function retriveDistrictData(response)
{
    flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        count=response.getElementsByTagName("count");
        var combo=document.getElementById("dname");
        for(var i=0;i<count.length;i++)
        {
            var dcode=response.getElementsByTagName("dcode")[i].firstChild.nodeValue;
            var dname=response.getElementsByTagName("dname")[i].firstChild.nodeValue;
            var opt =document.createElement("option"); 
            var text=document.createTextNode(dname);
            opt.setAttribute("value",dcode+"--"+dname);
            opt.appendChild(text);
            combo.appendChild(opt);  
        }
    }
}
function loadLocation(response)
{
    flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        count=response.getElementsByTagName("count");
        var combo=document.getElementById("Location");
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
    else
    {
        alert("record not exist under this selection");
    }
}
function getCustomerType(response)
{
    flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        count=response.getElementsByTagName("count");
        var combo=document.getElementById("ctype");
        for(var i=0;i<count.length;i++)
        {
            var ctype=response.getElementsByTagName("ctype")[i].firstChild.nodeValue;
            var opt =document.createElement("option"); 
            var text=document.createTextNode(ctype);
            opt.setAttribute("value",ctype);
            opt.appendChild(text);
            combo.appendChild(opt);  
        }
    }
}
function loadLocalBody(response)
{
        
        var ltype=response.getElementsByTagName("ltype")[0].firstChild.nodeValue;
        var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(ltype=="VP")
        {
            if(flag=="success")
            {
                        var count=response.getElementsByTagName("count");
                        var combo=document.getElementById("block");
                        var child=combo.childNodes;
                        for(var c=child.length-1;c>1;c--)
                        {
                           combo.removeChild(child[c]);
                        }
                        for(var i=0;i<count.length;i++)
                        {
                            var bcode=response.getElementsByTagName("bcode")[i].firstChild.nodeValue;
                            var bname=response.getElementsByTagName("bname")[i].firstChild.nodeValue;
                            var opt =document.createElement("option"); 
                            var text=document.createTextNode(bname);
                            opt.setAttribute("value",bcode+"--"+bname);
                            opt.appendChild(text);
                            combo.appendChild(opt);  
                        }
            }
            else
            {
                alert("No block entry is available under this selection");
            }
        }
        else
        {
            if(flag=="success")
            {
                        var count=response.getElementsByTagName("count");
                        var combo=document.getElementById("lbody");
                        var child=combo.childNodes;
                        for(var c=child.length-1;c>1;c--)
                        {
                           combo.removeChild(child[c]);
                        }
                        for(var i=0;i<count.length;i++)
                        {
                            var lcode=response.getElementsByTagName("local_body_code")[i].firstChild.nodeValue;
                            var lname=response.getElementsByTagName("local_body_name")[i].firstChild.nodeValue;
                            var opt =document.createElement("option"); 
                            var text=document.createTextNode(lname);
                            opt.setAttribute("value",lcode+"--"+lname);
                            opt.appendChild(text);
                            combo.appendChild(opt);  
                        }
            }
            else
            {
                alert("No Local body entry is available under this selection");
            }
        }
}

function loadPanchayat(response)
{
        flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="Success")
        {
                    count=response.getElementsByTagName("count");
                    var Panchayat=document.getElementById("Panchayat");
                    for(var i=0;i<count.length;i++)
                    {
                        var panch_code=response.getElementsByTagName("panch_code")[i].firstChild.nodeValue;
                        var panch_name=response.getElementsByTagName("panch_name")[i].firstChild.nodeValue;
                        var opt =document.createElement("option"); 
                        var text=document.createTextNode(panch_name);
                        opt.setAttribute("value",panch_code+"--"+panch_name);
                        opt.appendChild(text);
                        Panchayat.appendChild(opt);  
                    }
        }
}

function loadHabitation(response)
{
        flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="Success")
        {
                    count=response.getElementsByTagName("count");
                    var Habitation=document.getElementById("Habitation");
                    for(var i=0;i<count.length;i++)
                    {
                        var hcode=response.getElementsByTagName("hab_code")[i].firstChild.nodeValue;
                        var hdesc=response.getElementsByTagName("hab_name")[i].firstChild.nodeValue;
                        var opt =document.createElement("option"); 
                        var text=document.createTextNode(hdesc);
                        opt.setAttribute("value",hcode+"--"+hdesc);
                        opt.appendChild(text);
                        Habitation.appendChild(opt);  
                    }
        }
}
/*
function changeHabitation(response)
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
function getElement()
{
    
        var url="WQS_Periodic_TwoParameters?command=element" ;
        document.SampleResult.es.focus();
        var req=getTransport();
        
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            if(req.readyState==4)
            { 
                  if(req.status==200)
                  {  
                     alert(req.responseText);
                     var iframe=document.getElementById("diviframeregion");
                     iframe.style.visibility='visible';
                     iframe.focus();
                     iframe.innerHTML=req.responseText;
                  }
            }
        }
        req.send(null);
}
*/   

function gen_rep()
{
    var val=checkValid();
    if(val==true)
    {
        var lab=document.SampleResult.lab.value;
        lab=lab.split("--");
        var fdate=document.SampleResult.fdate.value;
        var tdate=document.SampleResult.tdate.value;
        var ctype=document.SampleResult.ctype.value;
        var dname=document.SampleResult.dname.value;
        var ltype="";
        if(document.SampleResult.ltype[0].checked==true)
            ltype=document.SampleResult.ltype[0].value;
        else if(document.SampleResult.ltype[1].checked==true)
            ltype=document.SampleResult.ltype[1].value;
        else if(document.SampleResult.ltype[2].checked==true)
            ltype=document.SampleResult.ltype[2].value;
        else if(document.SampleResult.ltype[3].checked==true)
            ltype=document.SampleResult.ltype[3].value;
        else if(document.SampleResult.ltype[4].checked==true)
            ltype=document.SampleResult.ltype[4].value;
        var lbody=document.SampleResult.lbody.value;
        var block=document.SampleResult.block.value;
        var panchayat=document.SampleResult.Panchayat.value;
        var habitation=document.SampleResult.Habitation.value;
        var location=document.SampleResult.Location.value;
        var eval=new Array();var result=new Array();
        if(document.SampleResult.es.length>1)
        {
            for(i=0;i<document.SampleResult.es.length;i++)
            {
                    if(document.SampleResult.es[i].checked==true)
                    {
                            eval= eval+document.SampleResult.es[i].value +",";
                            result=result+document.SampleResult.esval[i].value +",";
                    }
                    
            }
        }
        else
        {
            if(document.SampleResult.es.checked==true)
            {
                eval=document.SampleResult.es.value;
                result=document.SampleResult.esval.value;
            }
        }
        var url="../../../../../../WQS_Periodic_TwoParameters?lab="+lab[0]+"&fdate="+fdate+"&tdate="+tdate+"&ctype="+ctype+"&ltype="+ltype+"&dname="+dname+"&lbody="+lbody+"&block="+block+"&panchayat="+panchayat+"&habitation="+habitation+"&location="+location+"&eval="+eval+"&result="+result;
        document.SampleResult.action=url
        document.SampleResult.method="post";
        document.SampleResult.submit();
    }
}

function changeParam(id)
{
    if(document.SampleResult.es.length>1)
    {
        if(document.SampleResult.es[id].checked==true)
        {
            document.SampleResult.esval[id].style.backgroundColor="white";
            document.SampleResult.esval[id].disabled=false;
        }
        else 
        {
            document.SampleResult.esval[id].style.backgroundColor="rgb(214,214,214)";
            document.SampleResult.esval[id].value="";
            document.SampleResult.esval[id].disabled=true;
        }
    }
    else
    {
        if(document.SampleResult.es.checked==true)
        {
            document.SampleResult.esval.style.backgroundColor="white";
            document.SampleResult.esval.disabled=false;
        }
        else 
        {
            document.SampleResult.esval.style.backgroundColor="rgb(214,214,214)";
            document.SampleResult.esval.value="";
            document.SampleResult.esval.disabled=true;
        }
    }
}

function displayfun()
{       
    clearAll();
    var res=checkDate();
    if(res==true)
    {
        if(document.SampleResult.ctype.value=="Twad")
        {
            document.getElementById("lt").style.display="block";
            document.getElementById("ltb").style.display="block";
            var type=document.getElementById("twaddt");
            type.style.display="block";
            document.getElementById("villagepanchayat").style.display="none";
            document.getElementById("lb").style.display="none";            
        }
        else 
        {
            document.getElementById("lt").style.display="none";
            document.getElementById("ltb").style.display="none";
            var type=document.getElementById("twaddt");
            type.style.display="block";
            document.getElementById("villagepanchayat").style.display="none";
            document.getElementById("lb").style.display="none";
            var lab=document.SampleResult.lab.value;
            lab=lab.split("--");
            var dname=document.getElementById("dname");
            var child=dname.childNodes;
            for(var c=child.length-1;c>1;c--)
            {
               dname.removeChild(child[c]);
            }
            var ctype=document.SampleResult.ctype.value
            var req=getTransport();
            var url="../../../../../../WQS_Periodic_TwoParameters?command=loadDistrict&lab="+lab[0]+"&ctype="+ctype;
            req.open("GET",url,"true");
            req.onreadystatechange=function()
            {
                manipulate(req);
            }
            req.send(null);
        }        
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
    var dname=document.SampleResult.dist.value;
    var req=getTransport();
    var url="../../../../../../WQS_Periodic_TwoParameters?command=changeDist&dcode="+dname;
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
function checkDate()
{
    if(document.SampleResult.fdate.value==""||document.SampleResult.fdate.value.length==0)
    {
        alert("Enter Period");
        document.SampleResult.fdate.focus();
        return false;
    }
    else if(document.SampleResult.tdate.value==""||document.SampleResult.tdate.value.length==0)
    {
        alert("Enter Period");
        document.SampleResult.tdate.focus();
        return false;
    }
    else
        return true;
}
function checkType()
{
    var val=checkDate();
    if(val==true)
    {
        if(document.SampleResult.ctype.value==""||document.SampleResult.ctype.value==0)
        {
            alert("Select Customer Type");
            document.SampleResult.ctype.focus();
            return false;
        }
        return true;
    }
}
function loadData()
{
    if(document.SampleResult.es.length>1)
    {
        for(var id=0;id<document.SampleResult.es.length;id++)
        {
            document.SampleResult.es[id].checked=false;
            document.SampleResult.esval[id].style.backgroundColor="rgb(214,214,214)";
            document.SampleResult.esval[id].value="";
            document.SampleResult.esval[id].disabled=true;
        }
    }
    else
    {
        document.SampleResult.es.checked=false;
        document.SampleResult.esval.style.backgroundColor="rgb(214,214,214)";
        document.SampleResult.esval.value="";
        document.SampleResult.esval.disabled=true;
    }
    var lab=document.getElementById("lab").value;    
    lab=lab.split("--");
    document.getElementById("dname").disabled=false;
    document.getElementById("dname").selectedIndex=""
    var req=getTransport();
    var url="../../../../../../WQS_Periodic_TwoParameters?command=loadData&lab="+lab[0];
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);
}
function clearAll()
{
    document.SampleResult.ltype[0].checked=false;
    document.SampleResult.ltype[1].checked=false;
    document.SampleResult.ltype[2].checked=false;
    document.SampleResult.ltype[3].checked=false;
    document.SampleResult.ltype[4].checked=false;
    var dname=document.getElementById("dname");
    var child=dname.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
       dname.removeChild(child[c]);
    }    
    clearAll1();clearAll2();
}
function clearAll1()
{
    var block=document.getElementById("block");
    var bchild=block.childNodes;
    for(var c=bchild.length-1;c>1;c--)
    {
       block.removeChild(bchild[c]);
    }
    clearAll3();
   
}
function clearAll2()
{
    var lbody=document.getElementById("lbody");
    var lchild=lbody.childNodes;
    for(var c=lchild.length-1;c>1;c--)
    {
       lbody.removeChild(lchild[c]);
    }
    var Location=document.getElementById("Location");
    var lc=Location.childNodes;
    for(var c=lc.length-1;c>1;c--)
    {
       Location.removeChild(lc[c]);
    }
}
function clearAll3()
{
    var Panchayat=document.getElementById("Panchayat");
    var pchild=Panchayat.childNodes;
    for(var c=pchild.length-1;c>1;c--)
    {
       Panchayat.removeChild(pchild[c]);
    }
    clearAll4();
}
function clearAll4()
{
    var Habitation=document.getElementById("Habitation");
    var hchild=Habitation.childNodes;
    for(var c=hchild.length-1;c>1;c--)
    {
       Habitation.removeChild(hchild[c]);
    }
    var Location=document.getElementById("Location");
    var lc=Location.childNodes;
    for(var c=lc.length-1;c>1;c--)
    {
       Location.removeChild(lc[c]);
    }
}

function checkValid()
{
    if(document.SampleResult.fdate.value==""||document.SampleResult.tdate.value=="")
    {
        alert("Enter Date");
        return false;
    }
    else if(document.SampleResult.ctype.value==""||document.SampleResult.ctype.value=="0")
    {
        alert("Customer Type");
        return false;
    }
    else 
    {
        if(document.SampleResult.ctype.value=="Twad")
        {
            if(document.SampleResult.ltype[0].checked==false && document.SampleResult.ltype[1].checked==false && document.SampleResult.ltype[2].checked==false && document.SampleResult.ltype[3].checked==false && document.SampleResult.ltype[4].checked==false)
            {
                alert("Select Location Type");
                return false;
            }
        }
        if(document.SampleResult.dname.value==""||document.SampleResult.dname.value=="0")
        {
            alert("Select District");
            return false;
        }
    }
    if(document.SampleResult.es.length>1)
    {
        var count=0;
        for(var i=0;i<document.SampleResult.es.length;i++)
        {
            if(document.SampleResult.es[i].checked==true)
            {
                count++;
                if(document.SampleResult.esval[i].value==""||document.SampleResult.esval[i].value.length==0)
                {
                    alert("Enter Comparing value");
                    return false;
                }
            }              
        }
        if(count==0)
        {
                alert("Select Parameter");
                return false;
        }
        else
        {
            return true;
        }
    }   
}