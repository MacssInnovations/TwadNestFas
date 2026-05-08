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
function changeSample()
{
    document.ResultEntry.ino.value=document.ResultEntry.inv.value;
    document.ResultEntry.sample.value=document.ResultEntry.smp.value;
    var ino=document.ResultEntry.ino.value;
    var sno=document.ResultEntry.sample.value;
    var test_purpose=document.ResultEntry.test_purpose.value;
    var lab=document.ResultEntry.labcode.value;
    lab=lab.split("--");
    var url="../../../../../../WQS_ResultEntry_ValidateServ?command=changeSample&lab="+lab[0]+"&invoice="+ino+"&sample="+sno+"&test_purpose="+test_purpose;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    if(window.XMLHttpRequest)
                req.send(null);
    else req.send();
}
function manipulate(req)
{
    if(req.readyState==4)
    {
          if(req.status==200)
          {
               var response=req.responseXML.getElementsByTagName("response")[0];
               var cmd=response.getElementsByTagName("command")[0].firstChild.nodeValue;
               if(cmd=="changeSample")
               {
                    var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                    if(flag=="success")
                    {
                            var rdate=response.getElementsByTagName("rdate")[0].firstChild.nodeValue;
                             var rtime=response.getElementsByTagName("rtime")[0].firstChild.nodeValue;
                            var cdate=response.getElementsByTagName("cdate")[0].firstChild.nodeValue;
                            var ctime=response.getElementsByTagName("ctime")[0].firstChild.nodeValue;
                            var test_purpose=response.getElementsByTagName("test_purpose")[0].firstChild.nodeValue;
                            var dcode=response.getElementsByTagName("dcode")[0].firstChild.nodeValue;
                            var ltype=response.getElementsByTagName("ltype")[0].firstChild.nodeValue;
                            var lcode=response.getElementsByTagName("lcode")[0].firstChild.nodeValue;
                            var bcode=response.getElementsByTagName("bcode")[0].firstChild.nodeValue;
                            var pcode=response.getElementsByTagName("pcode")[0].firstChild.nodeValue;
                            var hcode=response.getElementsByTagName("hcode")[0].firstChild.nodeValue;
                            var stype=response.getElementsByTagName("stype")[0].firstChild.nodeValue;
                            var s_type=response.getElementsByTagName("s_type")[0].firstChild.nodeValue;
                            var scode=response.getElementsByTagName("scode")[0].firstChild.nodeValue;
                            var programme=response.getElementsByTagName("programme")[0].firstChild.nodeValue;
                            var spoint=response.getElementsByTagName("spoint")[0].firstChild.nodeValue;
                            var location=response.getElementsByTagName("location")[0].firstChild.nodeValue;
                            var cnc=response.getElementsByTagName("cnc")[0].firstChild.nodeValue;
                            var pnp=response.getElementsByTagName("pnp")[0].firstChild.nodeValue;
                            var reason=response.getElementsByTagName("reason")[0].firstChild.nodeValue;
                            cdate=cdate+" "+ctime;
                            var rt2=rtime.split(" ");;
                            document.ResultEntry.rdate.disabled=true;
                            document.ResultEntry.rdate.value=rdate;
                            document.ResultEntry.rtime.value=rt2[0];
                            if(rt2[1]=="AM")
                                document.ResultEntry.rt[0].checked=true;
                            else 
                                document.ResultEntry.rt[1].checked=true;
                            
                            document.ResultEntry.cdate.value=cdate;
                            document.ResultEntry.purpose.value=test_purpose;
                            document.ResultEntry.distcode.value=dcode;
                            if(ltype==" "||ltype=='-')
                                ltype="";
                            document.ResultEntry.ltype.value=ltype;
                            if(lcode==" "||lcode=='-')
                                lcode="";
                            document.ResultEntry.lbody.value=lcode;
                            if(bcode==" "||bcode=='-')
                                bcode="";
                            document.ResultEntry.blockcode.value=bcode;
                            if(pcode==" "||pcode=='-')
                                pcode="";
                            document.ResultEntry.pancode.value=pcode;
                            if(hcode==" "||hcode=='-')
                                hcode="";
                            document.ResultEntry.habitationcode.value=hcode;
                            if(stype==" "||stype=='-')
                                stype="";
                            document.ResultEntry.schemetype.value=stype;
                            if(s_type==" "||s_type=='-')
                                s_type="";
                            document.ResultEntry.sourcetype.value=s_type;
                            if(scode==" "||scode=='-')
                                scode="";
                            document.ResultEntry.sourcecode.value=scode;
                            if(programme==" "||programme=='-')
                                programme="";
                            document.ResultEntry.programmecode.value=programme;
                            if(spoint==" "||spoint=='-')
                                spoint="";
                            document.ResultEntry.spoint.value=spoint;
                            if(location==" "||location=='-')
                                location="";
                            document.ResultEntry.location.value=location;
                            if(reason==" "||reason=="-")
                            reason="";
                            if(cnc=="C")
                                document.ResultEntry.cnc[0].checked=true;
                            else if(cnc=="NC")
                                document.ResultEntry.cnc[1].checked=true;
                            if(pnp=="P")
                                document.ResultEntry.pnp[0].checked=true;
                            else if(pnp=="NP")
                                document.ResultEntry.pnp[1].checked=true;
                            document.ResultEntry.reason.value=reason;
                           
                            var ino=document.ResultEntry.ino.value;
                            var sno=document.ResultEntry.sample.value;
                            var test_purpose_id=document.ResultEntry.test_purpose.value;
                            var test_purpose=document.ResultEntry.purpose.value;
                            var lab=document.ResultEntry.labcode.value;
                            lab=lab.split("--");
                            var url="../../../../../../WQS_ResultEntry_ValidateServ?command=updateParameter&lab="+lab[0]+"&invoice="+ino+"&sample="+sno+"&test_purpose_id="+test_purpose_id+"&test_purpose="+test_purpose;
                            var req=getTransport();
                            req.open("GET",url,true);        
                            req.onreadystatechange=function()
                            {
                                if(req.readyState==4)
                                { 
                                      if(req.status==200)
                                      {  
                                         var paramdiv=document.getElementById("ParameterDiv");
                                         paramdiv.style.disply="block";
                                         var iframe=document.getElementById("diviframeregion");
                                         iframe.style.visibility="visible";
                                         iframe.innerHTML=req.responseText;
                                         var count=0;
                                         if(document.ResultEntry.algae)
                                         {
                                            var item=document.ResultEntry.algae.value;
                                            item=item.split("//");
                                            var mycurrent_row=document.getElementById("algaeDiv");
                                            try
                                            {
                                                mycurrent_row.innerHTML="";
                                            }
                                            catch(e)
                                            {
                                                mycurrent_row.innerText="";
                                            }
                                            for(var j=0;j<item.length;j++)
                                            {
                                                if(j%3==0 && j!=0)
                                                {
                                                    var txtnew=document.createElement("BR");
                                                    mycurrent_row.appendChild(txtnew);
                                                }
                                                
                                                var check="";
                                                var txtedit=document.createTextNode("       ");
                                                mycurrent_row.appendChild(txtedit);
                                                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                                                {
                                                    check=document.createElement("<INPUT type='text' name='algae' id='algae' value='"+item[j]+"' size='25' maxlength=50>");
                                                }
                                                else
                                                {  
                                                       check=document.createElement("input");
                                                       check.type="text";
                                                       check.name="algae";
                                                       check.id="algae";
                                                       check.value=item[j];
                                                       check.size=25;
                                                       check.maxlength=50;
                                                       check.disabled=true;
                                                }
                                                mycurrent_row.appendChild(check);
                                                mycurrent_row.style.display="block";
                                            }
                                           
                                        }
                                       
                                      }
                                }
                            }
                            req.send(null);
                    }
                    else
                            alert("Invalid Sample Number");
                }
                else if(cmd=="Validate")
                {
                    var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                    if(flag=="success")
                    {
                        alert("Record Validated Successfully");
                        clearAll();
                    }
                    else 
                        alert(response.getElementsByTagName("exception")[0].firstChild.nodeValue);
                }
          }
    }
}


