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

function servicepopup()
{
    var winemp;
    var my_window;
    var wininterval;
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }
    else
    {
        winemp=null
    }
        
    winemp= window.open("../../../../../../org/WQS/WQS1/WQTesting/Directory1/jsps/WQS_ResultEntry_popupJSP.jsp?command=create","InvoiceNumberSearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}
function doParentEmp(ino,sno)
{
   
   document.ResultEntry.ino.value=ino;
   document.ResultEntry.sample.value=sno;
   changeSample();
}
function changeInvoice()
{
    document.ResultEntry.sample.value="";
    clearAll1();
    var ino=document.ResultEntry.ino.value;
    var lab=document.ResultEntry.labcode.value;
    lab=lab.split("--");
    var url="../../../../../../WQS_ResultEntry_Serv?command=changeInvoice&lab="+lab[0]+"&invoice="+ino;
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
function checkInvoice()
{
    if(document.ResultEntry.ino.value==""||document.ResultEntry.ino.value.length==0)
    {
        alert("Enter Invoice Number");
        document.ResultEntry.sample.value="";
        document.ResultEntry.ino.focus();
    }
}
function changeSample()
{
    clearAll1();
    var ino=document.ResultEntry.ino.value;
    var sno=document.ResultEntry.sample.value;
    var lab=document.ResultEntry.labcode.value;
    lab=lab.split("--");
    var url="../../../../../../WQS_ResultEntry_Serv?command=changeSample&lab="+lab[0]+"&invoice="+ino+"&sample="+sno;
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
               if(cmd=="changeInvoice")
               {
                   var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                   if(flag!="Success")
                   {
                        alert("Invalid Invoice Number");
                        document.ResultEntry.ino.value="";
                        document.ResultEntry.ino.focus();
                    }
                }
                else if(cmd=="changeSample")
                {
                    var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                    if(flag=="success")
                    {
                            
                                var cdate=response.getElementsByTagName("cdate")[0].firstChild.nodeValue;
                                var ctime=response.getElementsByTagName("ctime")[0].firstChild.nodeValue;
                                var test_purpose_id=response.getElementsByTagName("test_purpose_id")[0].firstChild.nodeValue;
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
                                cdate=cdate+" "+ctime;
                                document.ResultEntry.rdate.disabled=false;
                                document.ResultEntry.cdate.value=cdate;
                                var tp=document.getElementById("test_purpose");
                                var purpose_id=test_purpose_id.split(",");
                                var purpose=test_purpose.split(",");
                                for(var i=0;i<purpose.length;i++)
                                {
                                    var opt=document.createElement("option");
                                    opt.setAttribute("value",purpose_id[i]+"--"+purpose[i]);
                                    var text=document.createTextNode(purpose[i]);
                                    opt.appendChild(text);
                                    tp.appendChild(opt);
                                }
                                document.getElementById("test_purpose").selectedIndex=0;
                                document.ResultEntry.distcode.value=dcode;
                                if(ltype==""||ltype=='-')
                                    ltype="";
                                document.ResultEntry.ltype.value=ltype;
                                if(lcode==""||lcode=='-')
                                    lcode="";
                                document.ResultEntry.lbody.value=lcode;
                                if(bcode==""||bcode=='-')
                                    bcode="";
                                document.ResultEntry.blockcode.value=bcode;
                                if(pcode==""||pcode=='-')
                                    pcode="";
                                document.ResultEntry.pancode.value=pcode;
                                if(hcode==""||hcode=='-')
                                    hcode="";
                                document.ResultEntry.habitationcode.value=hcode;
                                if(stype==""||stype=='-')
                                    stype="";
                                document.ResultEntry.schemetype.value=stype;
                                if(s_type==""||s_type=='-')
                                    s_type="";
                                document.ResultEntry.sourcetype.value=s_type;
                                if(scode==""||scode=='-')
                                    scode="";
                                document.ResultEntry.sourcecode.value=scode;
                                if(programme==""||programme=='-')
                                    programme="";
                                document.ResultEntry.programmecode.value=programme;
                                if(spoint==""||spoint=='-')
                                    spoint="";
                                document.ResultEntry.spoint.value=spoint;
                                if(location==""||location=='-')
                                    location="";
                                document.ResultEntry.location.value=location;
                    }
                    else
                        alert("Invalid Sample Number");
                    document.ResultEntry.add.disabled=false;
                }
                else if(cmd=="Add")
                {
                    var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                    if(flag=="success")
                    {
                            alert("Record Added Successfully");
                            var value=document.ResultEntry.test_purpose.value.split("--");
                            var test_purpose=document.getElementById("test_purpose");
                            var child=test_purpose.childNodes;
                            if(child.length-1>2)
                            {
                                for(var i=child.length-1;i>1;i--)
                                {
                                    var test_val=child[i].value.split("--");
                                    if(value[0]==test_val[0])
                                    {
                                        test_purpose.removeChild(child[i])
                                    }
                                }   
                                document.ResultEntry.test_purpose.selectedIndex="";
                                var iframe=document.getElementById("diviframeregion");
                                iframe.innerHTML="";
                                iframe.style.visibility='hidden';
                                document.getElementById("ParameterDiv").style.display="none";
                                document.ResultEntry.cnc[0].checked=false;
                                document.ResultEntry.cnc[1].checked=false;
                                document.ResultEntry.pnp[0].checked=false;
                                document.ResultEntry.pnp[1].checked=false;
                                document.ResultEntry.reason.value="";
                            }
                            else
                                clearAll();
                    }
                    else if(flag=="failure")
                    {
                        alert("failed to add");                    
                    }
                    else 
                        alert(response.getElementsByTagName("exception")[0].firstChild.nodeValue);
                }
          }
    }
}

function load_Parameter()
{
    var ino=document.ResultEntry.ino.value;
    var sno=document.ResultEntry.sample.value;
    var lab=document.ResultEntry.labcode.value;
    var test_purpose=document.ResultEntry.test_purpose.value;
    test_purpose=test_purpose.split("--");
    if(test_purpose[0]=="DRI"||test_purpose[0]=="SEW"||test_purpose[0]=="EFF")
        document.getElementById("potability_div").style.display="block";
    else
        document.getElementById("potability_div").style.display="none";
    var ParameterDiv=document.getElementById("ParameterDiv");
    ParameterDiv.style.display='block';
    lab=lab.split("--");
    var url="../../../../../../WQS_ResultEntry_Serv?command=load_Parameter&lab="+lab[0]+"&invoice="+ino+"&sample="+sno+"&test_purpose_id="+test_purpose[0]+"&test_purpose="+test_purpose[1];
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        if(req.readyState==4)
        { 
              if(req.status==200)
              {  
                 if(req.responseText!="Records Available")
                 {
                     document.getElementById("ParameterDiv").style.display="block";
                     var iframe=document.getElementById("diviframeregion");
                     iframe.style.visibility='visible';
                     iframe.focus();
                     iframe.innerHTML=req.responseText;
                 }
                 else
                    alert("Parameter's already entered");
              }
        }
    }
    if(window.XMLHttpRequest)
                req.send(null);
    else req.send();
}

function loadParameter()
{
        var iframe=document.getElementById("diviframeregion");
        iframe.innerHTML="";
        iframe.style.visibility='hidden';
        document.getElementById("ParameterDiv").style.display="none";
}

function checkparam(id)
{
    if(document.ResultEntry.chkparameter.length>1)
    {
        if(document.ResultEntry.chkparameter[id].checked==true)
        {
            document.ResultEntry.paramval[id].style.backgroundColor="white";
            document.ResultEntry.paramval[id].disabled=false;
        }
        else 
        {
            document.ResultEntry.paramval[id].style.backgroundColor="rgb(214,214,214)";
            document.ResultEntry.paramval[id].value="";
            document.ResultEntry.paramval[id].disabled=true;
        }
    }
    else
    {
        if(document.ResultEntry.chkparameter.checked==true)
        {
            document.ResultEntry.paramval.style.backgroundColor="white";
            document.ResultEntry.paramval.disabled=false;
        }
        else 
        {
            document.ResultEntry.paramval.style.backgroundColor="rgb(214,214,214)";
            document.ResultEntry.paramval.value="";
            document.ResultEntry.paramval.disabled=true;
        }
    }
}

function callServer(command,param)
{
    var valid=validatefun();
    if(valid==true)
    {
            var lab=document.ResultEntry.labcode.value;
            lab=lab.split("--");
            var ino=document.ResultEntry.ino.value;
            var sno=document.ResultEntry.sample.value;
            var rdate=document.ResultEntry.rdate.value;
            var rtime=document.ResultEntry.rtime.value;
            var rtx="";
            if(document.ResultEntry.rt[0].checked==true)
                rtx="AM";
            else
                rtx="PM";
            rtime=rtime+" "+rtx;
            var cdate=document.ResultEntry.cdate.value;
            var test_purpose=document.ResultEntry.test_purpose.value;
            test_purpose=test_purpose.split("--");
            var dcode=document.ResultEntry.distcode.value;
            var ltype=document.ResultEntry.ltype.value;
            var lbody=document.ResultEntry.lbody.value;
            var bcode=document.ResultEntry.blockcode.value;
            var pcode=document.ResultEntry.pancode.value;
            var hcode=document.ResultEntry.habitationcode.value;
            var scheme_type=document.ResultEntry.schemetype.value;
            var source_type=document.ResultEntry.sourcetype.value;
            var scode=document.ResultEntry.sourcecode.value;
            var p_code=document.ResultEntry.programmecode.value;
            var spoint=document.ResultEntry.spoint.value;
            var location=document.ResultEntry.location.value;
            
            var eval="";var result="";var percent="";
            if(document.ResultEntry.chkparameter.length>1)
            {
                for(i=0;i<document.ResultEntry.chkparameter.length;i++)
                {
                        if(document.ResultEntry.chkparameter[i].checked==true && document.ResultEntry.paramval[i].value!="")
                        {
                                eval= eval+document.ResultEntry.chkparameter[i].value +",";
                                result=result+document.ResultEntry.paramval[i].value +",";                        
                                if(result.search("%")!=-1)
                                {
                                    percent=percent+"Y"+",";
                                    result=result.replace("%","");
                                }
                                else
                                    percent=percent+"N"+",";

                        }               
                }
                eval=eval.substring(0,eval.length-1);
                result=result.substring(0,result.length-1);
                percent=percent.substring(0,percent.length-1);
            }
            else
            {
                if(document.ResultEntry.chkparameter.checked==true && document.ResultEntry.paramval.value!="")
                {
                    eval=document.ResultEntry.chkparameter.value;
                    result=document.ResultEntry.paramval.value;
                    if(result.search("%")!=-1)
                    {
                        percent=percent+"Y";
                        result=result.replace("%","");
                    }
                    else
                        percent=percent+"N";
                }
            }
            var algae="";var algae_res="";var algae_percent="";  
            if(document.ResultEntry.algae)
            {
                if(document.ResultEntry.algae.length>1)
                {
                    for(i=0;i<document.ResultEntry.algae.length;i++)
                    {
                            if(document.ResultEntry.algae[i].value!="")
                            {
                                    algae+=document.ResultEntry.algae[i].value+",";     
                                    algae_res+="-,";
                                    algae_percent+="-,";
                            }
                            
                    }
                    algae=algae.substring(0,algae.length-1);
                    algae_res=algae_res.substring(0,algae_res.length-1);
                    algae_percent=algae_percent.substring(0,algae_percent.length-1);
                }
                else
                {
                    if(document.ResultEntry.algae.value!="")
                    {
                        algae=document.ResultEntry.algae.value;
                        algae_res="-";
                        algae_percent="-";
                    }
                }
            }
            if(eval=="" && algae=="")
            {
                alert("Select Parameter");
                return false;
            }
            else
            {
                if(eval!="" && algae!="")
                {
                    eval=eval+","+algae;
                    result=result+","+algae_res;
                    percent=percent+","+algae_percent;
                }
                else
                {
                    if(eval=="" && algae!="")
                    {
                        eval=algae;
                        result=algae_res;
                        percent=algae_percent;
                    }
                }
            }
            var cnc="";
            if(document.ResultEntry.cnc[0].checked==true)
                cnc=document.ResultEntry.cnc[0].value;
            else if(document.ResultEntry.cnc[1].checked==true)
                cnc=document.ResultEntry.cnc[1].value
                
            var pnp="";
            if(document.ResultEntry.pnp[0].checked==true)
                pnp=document.ResultEntry.pnp[0].value;
            else if(document.ResultEntry.pnp[1].checked==true)
                pnp=document.ResultEntry.pnp[1].value;
            var reason=document.ResultEntry.reason.value;
            var req=getTransport();
  
            if(command=="Add")
            {
                    var url="../../../../../../WQS_ResultEntry_Serv?command=Add&lab="+lab[0]+"&invoice_no="+ino+"&sample_no="+sno+"&rdate="+rdate+"&e_val="+eval+"&e_result="+result+"&cnc="+cnc+"&pnp="+pnp+"&reason="+reason+"&rtime="+rtime+"&test_purpose="+test_purpose[0]+"&percent="+percent;
                    req.open("GET",url,true);
                    req.onreadystatechange=function()
                    {
                        manipulate(req);
                    }
                    if(window.XMLHttpRequest)
                        req.send(null);
                    else req.send();
            }
    }
}
function clearAll()
{
        document.ResultEntry.ino.value="";
        document.ResultEntry.sample.value="";
        document.ResultEntry.ino.focus();
        clearAll1();
}
function clearAll1()
{
        document.getElementById("potability_div").style.display="none";
        document.ResultEntry.cdate.value="";
        var test_purpose=document.getElementById("test_purpose");
        var child=test_purpose.childNodes;
        for(var i=child.length-1;i>1;i--)
        {
            test_purpose.removeChild(child[i]);
        }
        document.ResultEntry.distcode.value="";
        document.ResultEntry.ltype.value="";
        document.ResultEntry.lbody.value="";
        document.ResultEntry.blockcode.value="";
        document.ResultEntry.pancode.value="";
        document.ResultEntry.habitationcode.value="";
        document.ResultEntry.schemetype.value="";
        document.ResultEntry.sourcetype.value="";
        document.ResultEntry.sourcecode.value="";
        document.ResultEntry.programmecode.value="";
        document.ResultEntry.spoint.value="";
        document.ResultEntry.location.value="";
        document.ResultEntry.cnc[0].checked=false;
        document.ResultEntry.cnc[1].checked=false;
        document.ResultEntry.pnp[0].checked=false;
        document.ResultEntry.pnp[1].checked=false;
        document.ResultEntry.reason.value="";
        
        document.ResultEntry.add.disabled=false
        loadParameter();
}

function validatefun()
{
    if(document.ResultEntry.ino.value==""||document.ResultEntry.ino.value.length==0)
    {
        alert("Enter Invoice Number");
        return false;
    }
    else if(document.ResultEntry.sample.value==""||document.ResultEntry.sample.value.length==0)
    {
        alert("Enter Sample Number");
        return false;
    }
    else if(document.ResultEntry.test_purpose.value==""||document.ResultEntry.test_purpose.selectedIndex=="")
    {
        alert("Enter Test Purpose");
        return false;
    }
    else if(document.ResultEntry.rdate.value==""||document.ResultEntry.rdate.value.length==0)
    {
        alert("Enter Date of Receipt");
        return false
    }
    else if(document.ResultEntry.rtime.value==""||document.ResultEntry.rtime.value.length==0)
    {
        alert("Enter Time");
        return false
    }
    else if(document.ResultEntry.test_purpose.value=="")
    {
        alert("Select Test Purpose");
        return false;
    }
    else 
    {
            var count=0;var algae_len=0;
            if(document.ResultEntry.test_purpose.value!="BIO")
            {
                if(document.ResultEntry.chkparameter)
                {
                    if(document.ResultEntry.chkparameter.length>1)
                    {                        
                        for(i=0;i<document.ResultEntry.chkparameter.length;i++)
                        {
                                if(document.ResultEntry.paramval[i].value!="")
                                    count++;        
                        }                       
                    }
                    else
                    {                        
                        if(document.ResultEntry.paramval.value!="")
                           count++;
                    }
                    if(count==0)
                    {
                        alert("Enter Parameter Value");
                        return false;
                    }
                    else
                        return true;
                }
                else
                {
                    alert("Select Parameter");
                    return false;
                }
            }
            else
            {                
                if(document.ResultEntry.chkparameter)
                {
                    if(document.ResultEntry.chkparameter.length>1)
                    {
                        for(i=0;i<document.ResultEntry.chkparameter.length;i++)
                        {
                                if(document.ResultEntry.paramval[i].value!="")
                                        count++;        
                        }                        
                    }
                    else
                    {
                        if(document.ResultEntry.paramval.value!="")
                            count++;
                    }
                }
                if(document.ResultEntry.algae)
                {
                    if(document.ResultEntry.algae.length>1)
                    {
                        for(i=0;i<document.ResultEntry.algae.length;i++)
                        {                                
                                if(document.ResultEntry.algae[i].value!="")
                                    algae_len++;
                        }
                    }
                    else
                    {
                         if(document.ResultEntry.algae.value!="")
                                    algae_len++;
                    }
                }
                if(count==0 && algae_len==0)
                {
                    alert("Enter Parameter Value");
                    return false;
                }
                else
                    return true;
            }
    }
}

/*function checkPotability()
{
    if(document.ResultEntry.chkparameter.length>1)
    {
        for(i=0;i<document.ResultEntry.chkparameter.length;i++)
        {
                if(document.ResultEntry.chkparameter[i].checked==true)
                {
                        eval= eval+document.ResultEntry.chkparameter[i].value +",";
                        result=result+document.ResultEntry.paramval[i].value +",";
                }
        }
    }
    else
    {
        if(document.ResultEntry.chkparameter.checked==true)
        {
            eval=document.ResultEntry.chkparameter.value;
            result=document.ResultEntry.paramval.value;
        }
    }
    var url="../../../../../../WQS_ResultEntry_Serv?command=Validate&lab="+lab[0]+"&invoice_no="+ino+"&sample_no="+sno+"&rdate="+rdate+"&e_val="+eval+"&e_result="+result;
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    if(window.XMLHttpRequest)
        req.send(null);
    else req.send();
}*/

function checklength(evt,item)
{
    var maxqty=document.ResultEntry.reason.value.length;
    var text =100;
    var result = true;
    if(maxqty >= text)
    {
        result = false;	
    }  
    return result;
}
function changeTime()
{
    var val=document.ResultEntry.rtime.value;
    var ct=val.length;
    var tot="";
    for(var i=0;i<ct;i++)
    {
        var chara=val.charAt(i);
        if(i==2)
        {
            tot=tot+":";
        }
        else
             tot=tot+chara;
    }
    document.ResultEntry.rtime.value=tot;
}
function checkTime()
{
    var cval=document.getElementById("rtime").value;
    var sp=cval.split(":");
    if(sp.length==1)
    {
        if(sp[0]>12)
        {
            alert("check time");
            document.getElementById("rtime").value="";
            document.getElementById("rtime").focus();
        }
    }
    else if(sp.length>1)
    {
        if(sp[0]>12)
        {
            alert("check time");
            document.getElementById("rtime").value="";
            document.getElementById("rtime").focus();
        }
        else
        {
            if(sp[1]>60)
            {
                alert("check time");
                document.getElementById("rtime").value="";
                document.getElementById("rtime").focus();
            }
        }
    }
}

function checkValidParam(val,evt,item,n,pre)
{
         if(val!=0&&val!=2)
         {
             var charCode = (evt.which) ? evt.which : event.keyCode
             if(charCode==46)
             {
                    if(item.value.indexOf(".")<0)	return (item.value.length>0)?true:false;
                    else return false;
              }
             if (!(charCode > 31 && (charCode < 48 || charCode > 57)))
             {
                    if(item.value.indexOf(".")<0){
                            return (item.value.length<n)?true:false;
                    }
                    if(item.value.indexOf(".")>0){
                            if(item.value.split(".")[1].length<pre) return true;
                            else return false;
                    }
                    return false;
            }
            else
            {
                    return false;
            }
       }
}
function AddAlgae()
{
            
            var mycurrent_row=document.getElementById("algaeDiv");
            var check="";var algae_length=0;
            if(document.ResultEntry.algae)
            {
                if(document.ResultEntry.algae.length>1)
                    algae_length=document.ResultEntry.algae.length;
                else
                    algae_length=1;
                if(algae_length%3==0)
                {
                    var txtnew=document.createElement("BR");
                    mycurrent_row.appendChild(txtnew);
                }
            }
           
           
            var txtedit=document.createTextNode("       ");
            mycurrent_row.appendChild(txtedit);
            if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
            {
                var txtimg=document.createElement("<img src='../../../../../../images/icon_delete.gif' width=10 height=10 alt='delete' onclick='deleteAlgae("+algae_length+")'>")
            }
            else
            {
                txtimg=document.createElement("img");
                txtimg.src="../../../../../../images/icon_delete.gif";
                txtimg.width=10;
                txtimg.height=10;
                txtimg.alt="delete";
                txtimg.setAttribute('onclick','deleteAlgae('+algae_length+')');
            }
            mycurrent_row.appendChild(txtimg);
            var txtedit=document.createTextNode(" ");
            mycurrent_row.appendChild(txtedit);
            if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
            {
                check=document.createElement("<INPUT type='text' name='algae' id='algae' size='25' maxlength=50>");
            }
            else
            {  
                   check=document.createElement("input");
                   check.type="text";
                   check.name="algae";
                   check.id="algae";
                   check.size=25;
                   check.maxlength=50;
            }
            mycurrent_row.appendChild(check);
            
}

function deleteAlgae(id)
{
    var item=new Array();var count=0;
    if(document.ResultEntry.algae)
    {
        if(document.ResultEntry.algae.length>1)
        {
            for(var i=0;i<document.ResultEntry.algae.length;i++)
            {
                if(i!=id)
                {
                    item[count]=document.ResultEntry.algae[i].value;
                    count++;
                }
            }
        }
       
    }
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
            var txtimg=document.createElement("<img src='../../../../../../images/icon_delete.gif' width=10 height=10 alt='delete' onclick='deleteAlgae("+j+")'>")
        }
        else
        {
            txtimg=document.createElement("img");
            txtimg.src="../../../../../../images/icon_delete.gif";
            txtimg.width=10;
            txtimg.height=10;
            txtimg.alt="delete";
            txtimg.setAttribute('onclick','deleteAlgae('+j+')');
        }
        mycurrent_row.appendChild(txtimg);
        var txtedit=document.createTextNode(" ");
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
        }
        mycurrent_row.appendChild(check);
    }
}