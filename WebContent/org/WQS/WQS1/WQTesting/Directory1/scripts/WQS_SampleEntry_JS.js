var req;
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

function servicepopup()
{
    var winemp;
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
        
    winemp= window.open("../../../../../../org/WQS/WQS1/WQTesting/Directory1/jsps/WQS_SampleEntry_popupJSP.jsp?command=SampleEntry","InvoiceNumberSearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}
function doParentEmp(ino)
{
   document.monitoring.ino.value=ino;
   changeInvoice();
}

function clearAll()
{
    
    document.getElementById("ctype").value="";
    document.getElementById("total").value="";
    var tb=document.getElementById("tbody");
    var t=tb.rows.length   
    for(var i=t-1;i>=0;i--)
    {
          tb.deleteRow(i);
    } 
    clearfun();
}
function clearfun()
{
	document.getElementById("update").disabled=true;
    document.getElementById("del").disabled=true;
	document.getElementById("sample").value="";
    document.getElementById("cdate").value="";
    document.getElementById("ctime").value="";
    document.monitoring.ct[0].checked=true;
    document.monitoring.ct[1].checked=false;
    document.getElementById("distcode").selectedIndex="";
    document.getElementById("ltype").selectedIndex="";
    document.getElementById("lbody").selectedIndex="";
    document.getElementById("blockcode").selectedIndex="";
    document.getElementById("pancode").selectedIndex="";
    document.getElementById("habitationcode").selectedIndex="";
    document.getElementById("schemetype").selectedIndex="";
    document.getElementById("sourcetype").selectedIndex="";
    document.getElementById("sourcecode").selectedIndex="";
    document.getElementById("programmecode").selectedIndex="";
    document.getElementById("spoint").selectedIndex="";
    document.getElementById("location").value="";
    document.getElementById("local").style.display='none';
    document.getElementById("village").style.display='none';
    document.getElementById("source_div").style.display='none';
    document.getElementById("source_code_div").style.display='none';
    document.getElementById("source_code_div1").style.display='none';
    document.getElementById("stype").value="";
    if(document.monitoring.test_purpose.length>1)
    {
            for(i=0;i<document.monitoring.test_purpose.length;i++)
            {
                        document.monitoring.test_purpose[i].checked=false;
                        document.monitoring.test_purpose[i].style.backgroudColor="white";
                        document.monitoring.test_purpose[i].disabled=false;
            }
    }
    clearCombo();
}
function clearCombo()
{
     var lbody=document.getElementById("lbody");
     var child=lbody.childNodes;
     for(var i=child.length-1;i>1;i--)
     {
        lbody.removeChild(child[i]);
     }    
     var blockcode=document.getElementById("blockcode");
     var child=blockcode.childNodes;
     for(var i=child.length-1;i>1;i--)
     {
        blockcode.removeChild(child[i]);
     } 
     clearCombo1();
}
function clearCombo1()
{
     var pancode=document.getElementById("pancode");
     var child=pancode.childNodes;
     for(var i=child.length-1;i>1;i--)
     {
        pancode.removeChild(child[i]);
     }
     var habitationcode=document.getElementById("habitationcode");
     var child=habitationcode.childNodes;
     for(var i=child.length-1;i>1;i--)
     {
        habitationcode.removeChild(child[i]);
     }
}
function loadTestPurpose()
{
        document.getElementById("divpurpose").style.display='block';
}

function load_labcode()
{
    clearAll(); 
    document.getElementById("divpurpose").style.display='none';
    document.getElementById("ino").disabled=false;
    document.getElementById("ino").value="";
    lab=document.getElementById("labcode").value;
    lab=lab.split("--");
    req=getTransport();
    var url="../../../../../../WQS_SampleEntry_Serv?option=load_labcode&lab="+lab[0];
    req.open("GET",url,true);
     req.onreadystatechange=function()
        {
            process_response(req);
        }
    req.send(null);
}
    
function process_response(req)
{
    if(req.readyState==4)
        {
            if(req.status==200)
            {
                var response=req.responseXML.getElementsByTagName("response")[0];
                var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
                if(command=="load_labcode")
                {
                    var dflag=response.getElementsByTagName("dflag")[0].firstChild.nodeValue;
                    if(dflag=="success")
                    {
                            var count=response.getElementsByTagName("count");                            
                            var distcode=document.getElementById("distcode");
                            for(var i=0;i<count.length;i++)
                            {
                                dname=response.getElementsByTagName("district_name")[i].firstChild.nodeValue;
                                dcode=response.getElementsByTagName("district_code")[i].firstChild.nodeValue;
                                var opt=document.createElement("option");
                                opt.setAttribute("value",dcode+"--"+dname);
                                var opttext=document.createTextNode(dname);
                                opt.appendChild(opttext);
                                distcode.appendChild(opt);
                            }
                    }
                    var sflag=response.getElementsByTagName("sflag")[0].firstChild.nodeValue;
                    if(sflag=="success")
                    {
                        var scheme_count=response.getElementsByTagName("scount");
                        var schemecode=document.getElementById("schemetype");                            
                        for(var j=0;j<scheme_count.length;j++)
                        {
                            sname=response.getElementsByTagName("stype_name")[j].firstChild.nodeValue;
                            scode=response.getElementsByTagName("stype_id")[j].firstChild.nodeValue;
                            var opt=document.createElement("option");
                            opt.setAttribute("value",scode+"--"+sname);
                            var opttext=document.createTextNode(sname);
                            opt.appendChild(opttext);
                            schemecode.appendChild(opt);
                        }
                    }
                    var s_flag=response.getElementsByTagName("s_flag")[0].firstChild.nodeValue;
                    if(s_flag=="success")
                    {
                        var source_count=response.getElementsByTagName("s_count");
                        var sourcecode=document.getElementById("sourcetype");                            
                        for(var j=0;j<source_count.length;j++)
                        {
                            s_name=response.getElementsByTagName("sourcetype")[j].firstChild.nodeValue;
                            s_code=response.getElementsByTagName("sourcetype_id")[j].firstChild.nodeValue;
                            var opt=document.createElement("option");
                            opt.setAttribute("value",s_code+"--"+s_name);
                            var opttext=document.createTextNode(s_name);
                            opt.appendChild(opttext);
                            sourcecode.appendChild(opt);
                        }
                    }
                    var pflag=response.getElementsByTagName("pflag")[0].firstChild.nodeValue;
                    if(pflag=="success")
                    {
                        var program_count=response.getElementsByTagName("pcount");
                        var programme=document.getElementById("programmecode");                            
                        for(var k=0;k<program_count.length;k++)
                        {
                            pname=response.getElementsByTagName("programmedesc")[k].firstChild.nodeValue;
                            pcode=response.getElementsByTagName("programmecode")[k].firstChild.nodeValue;
                            var opt=document.createElement("option");
                            opt.setAttribute("value",pcode+"--"+pname);
                            var opttext=document.createTextNode(pname);
                            opt.appendChild(opttext);
                            programme.appendChild(opt);
                        }
                    }                    
                    var samp_flag=response.getElementsByTagName("samp_flag")[0].firstChild.nodeValue;                    
                    if(samp_flag=="success")
                    {
                        var samp_count=response.getElementsByTagName("samp_count");
                        var spoint=document.getElementById("spoint");                            
                        for(var k=0;k<samp_count.length;k++)
                        {
                            sp=response.getElementsByTagName("sampling_point")[k].firstChild.nodeValue;
                            var opt=document.createElement("option");
                            opt.setAttribute("value",sp);
                            var opttext=document.createTextNode(sp);
                            opt.appendChild(opttext);
                            spoint.appendChild(opt);
                        }
                    }
            }
            else if(command=="loadblock")
            {
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="success");
                {
                    var blockcount=response.getElementsByTagName("blockcount")[0].firstChild.nodeValue;
                    var blockid=document.getElementById("blockcode");
                    for(var i=0;i<blockcount;i++)
                    {
                        var code=response.getElementsByTagName("block_code")[i].firstChild.nodeValue;
                        var desc=response.getElementsByTagName("block_name")[i].firstChild.nodeValue;
                        var opt=document.createElement("option");
                        opt.setAttribute("value",code+"--"+desc);
                        var text=document.createTextNode(desc);
                        opt.appendChild(text);
                        blockid.appendChild(opt);
                    }
                }
            }
            else if(command=="loadpanchayat")
            {
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="success")
                {
                 var panchayatcount=response.getElementsByTagName("panchayatcount")[0].firstChild.nodeValue;
                 var pancode=document.getElementById("pancode");
                 for(var j=0;j<panchayatcount;j++)
                 {
                    var panchayat_code=response.getElementsByTagName("panchayat_code")[j].firstChild.nodeValue;
                    var panchayat_name=response.getElementsByTagName("panchayat_name")[j].firstChild.nodeValue;
                    var opt=document.createElement("option");
                    opt.setAttribute("value",panchayat_code+"--"+panchayat_name);
                    var txt=document.createTextNode(panchayat_name);
                    opt.appendChild(txt);
                    pancode.appendChild(opt);
                 }
                }
            }
            else if(command=="loadhabitation")
            {
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="success")
                {
                 var habitationcount=response.getElementsByTagName("habitationcount")[0].firstChild.nodeValue;
                 var habitationcode=document.getElementById("habitationcode");
                 for(var j=0;j<habitationcount;j++)
                 {
                    var habcode=response.getElementsByTagName("habitation_code")[j].firstChild.nodeValue;
                    var habname=response.getElementsByTagName("habitation_name")[j].firstChild.nodeValue;
                    var opt=document.createElement("option");
                    opt.setAttribute("value",habcode+"--"+habname);
                    var txt=document.createTextNode(habname);
                    opt.appendChild(txt);
                    habitationcode.appendChild(opt);
                 }
                }
            }
            else if(command=="loadlocalbody")
            {
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="success")
                {
                 var tcount=response.getElementsByTagName("tcount")[0].firstChild.nodeValue;
                 var lbody=document.getElementById("lbody");
                 for(var j=0;j<tcount;j++)
                 {
                    var habcode=response.getElementsByTagName("code")[j].firstChild.nodeValue;
                    var habname=response.getElementsByTagName("name")[j].firstChild.nodeValue;
                    var opt=document.createElement("option");
                    opt.setAttribute("value",habcode+"--"+habname);
                    var txt=document.createTextNode(habname);
                    opt.appendChild(txt);
                    lbody.appendChild(opt);
                 }
                }
            }
            else if(command=="changeInvoice")
            {
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="success")
                {
                        var tot_samples=response.getElementsByTagName("tot_samples")[0].firstChild.nodeValue;
                        var ctype=response.getElementsByTagName("ctype")[0].firstChild.nodeValue;
                        var count=response.getElementsByTagName("count")[0].firstChild.nodeValue;
                        if(parseInt(count)<parseInt(tot_samples))
                        {   
                            document.getElementById("ctype").value=ctype;
                            document.getElementById("total").value=tot_samples;
                            var avl=parseInt(tot_samples)-parseInt(count);
                            document.getElementById("sn").value=avl;
                            if(avl>1 & avl<tot_samples)
                                alert("only "+avl+" sample entries are available,Remaining entry previously entered");
                            else if(avl==1 & count!=0)
                                alert("only "+avl+" sample entry is available,Remaining entry previously entered");
                            if(ctype=="Twad")
                            {
                                document.getElementById("source_div").style.display="block";
                                document.getElementById("source_code_div").style.display='block';
                                document.getElementById("source_code_div1").style.display='block';
                            }
                            else
                            {
                                document.getElementById("source_div").style.display="none";
                                document.getElementById("source_code_div").style.display='none';
                                document.getElementById("source_code_div1").style.display='none';
                            }
                        }
                        else
                        {
                            alert("Sample Entry already entered for this invoice number");
                            document.getElementById("ino").value="";
                            document.getElementById("ino").focus();
                        }
                }
                else
                {
                  alert("Invalid Invoice Number");
                  document.getElementById("ino").value="";
                  document.getElementById("ino").focus();
                }
            }
        }
    }
}     
    
function changeLocation()
{
    clearCombo();
    var type=document.getElementById("ltype").value;
    if(type=="VP")
    {
        document.getElementById("local").style.display='none';
        document.getElementById("village").style.display='block';
        req=getTransport();
        var dist_code=document.getElementById("distcode").value;
        dist_code=dist_code.split("--");
        var url="../../../../../../WQS_SampleEntry_Serv?option=loadblock&dist_code="+dist_code[0];
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            process_response(req);
        }
        req.send(null);
    }
    else if(type!=0)
    {
        document.getElementById("local").style.display='block';
        document.getElementById("village").style.display='none';
        req=getTransport();
        var dist_code=document.getElementById("distcode").value;
        dist_code=dist_code.split("--");
        var url="../../../../../../WQS_SampleEntry_Serv?option=loadlocalbody&ltype="+type+"&dist_code="+dist_code[0];
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            process_response(req);
        }
        req.send(null);
    }
    else
    {        
        document.getElementById("local").style.display='none';
        document.getElementById("village").style.display='none';
    }
}
function load_panchayat()
{
    clearCombo1();
    req=getTransport();
    var dist_code=document.getElementById("distcode").value;
    dist_code=dist_code.split("--");
    var block_code=document.getElementById("blockcode").value;
    block_code=block_code.split("--");
    var url="../../../../../../WQS_SampleEntry_Serv?option=loadpanchayat&dist_code="+dist_code[0]+"&block_code="+block_code[0];
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        process_response(req);
    }
    req.send(null);
}
function load_habitation()
{
    var habitationcode=document.getElementById("habitationcode");
    var child=habitationcode.childNodes;
    for(var i=child.length-1;i>1;i--)
    {
        habitationcode.removeChild(child[i]);
    }
    req=getTransport();
    var dist_code=document.getElementById("distcode").value;
    dist_code=dist_code.split("--");
    var block_code=document.getElementById("blockcode").value;
    block_code=block_code.split("--");
    var panchayat_code=document.getElementById("pancode").value;
    panchayat_code=panchayat_code.split("--");
    
    var url="../../../../../../WQS_SampleEntry_Serv?option=loadhabitation&dist_code="+dist_code[0]+"&block_code="+block_code[0]+"&panchayat_code="+panchayat_code[0];
    req.open("GET",url,true);
     req.onreadystatechange=function()
    {
        process_response(req);
    }
    req.send(null);
}          

function changeDist()
{
     var ltype=document.getElementById("ltype").selectedIndex="";
     clearCombo();
}
function changeInvoice()
{
    clearAll();
    req=getTransport();
    var lab=document.getElementById("labcode").value;
    lab=lab.split("--");
    var inum=document.getElementById("ino").value;
    var url="../../../../../../WQS_SampleEntry_Serv?option=changeInvoice&lab="+lab[0]+"&invoice_number="+inum;
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        process_response(req);
    }
    req.send(null);
}
function validInsert()
{
    var valid=checkValid();
    if(valid==true)
    {
        if(document.getElementById("cdate").value==0||document.getElementById("cdate").value=="")
        {
            alert("Select Date");
            return false;
        }
        else if(document.getElementById("ctime").value==0||document.getElementById("ctime").value=="")
        {
            alert("Enter Time");
            return false;
        }
        else if(document.getElementById("location").value==""||document.getElementById("location").value.length==0)
        {
            alert("Enter Location");
            return false;
        }
        else
        {
            return true;
        }
    }
}

function insert()
{
    var val=validInsert();
    if(val==true)
    {
            var blockcode=null;var pancode=null;var habitationcode=null;var lbody=null;
            var lab=document.getElementById("labcode").value;
            var labcode=lab.split("--");
            var ino=document.getElementById("ino").value;
            var sample=document.getElementById("sample").value;
            var stype=document.getElementById("stype").value;
            var test_purpose="";var count=0;var test_purpose_id="";
            if(document.monitoring.test_purpose.length>1)
            {
                for(i=0;i<document.monitoring.test_purpose.length;i++)
                {
                        if(document.monitoring.test_purpose[i].checked==true)
                        {
                                var test=document.monitoring.test_purpose[i].value.split("--");
                                if(count==0)
                                {
                                    test_purpose_id=test[0];
                                    test_purpose=test[1];
                                }
                                else
                                {
                                    test_purpose_id=test_purpose_id+","+test[0];
                                    test_purpose=test_purpose+","+test[1];
                                }
                                count++
                        }
                        
                }
            }
            var cdate=document.getElementById("cdate").value;
            var ctime=document.getElementById("ctime").value;
            var ct=document.getElementById("ct").value;
            cdate=cdate+" "+ctime+" "+ct;
            var distcode=document.getElementById("distcode").value;
            distcode=distcode.split("--");
            var ltype=document.getElementById("ltype").value;
            var schemetype=document.getElementById("schemetype").value;
            schemetype=schemetype.split("--");
            if(schemetype[0]=="0"||schemetype[0]=="")
            {
                schemetype[0]="";schemetype[1]="";
            }
            var sourcetype=document.getElementById("sourcetype").value;
            sourcetype=sourcetype.split("--");
            if(sourcetype[0]=="0"||sourcetype[0]=="")
            {
                sourcetype[0]="";sourcetype[1]="";
            }
            var sourcecode=document.getElementById("sourcecode").value;
            sourcecode=sourcecode.split("--");
            if(sourcecode[0]=="0"||sourcecode[0]=="")
            {
                sourcecode[0]="";sourcecode[1]="";
            }
            
            var programmecode=document.getElementById("programmecode").value;
            programmecode=programmecode.split("--");
            if(programmecode[0]=="0"||programmecode[0]=="")
            {
                programmecode[0]="";programmecode[1]="";
            }
            var spoint=document.getElementById("spoint").value;
            var location=document.getElementById("location").value;
            blockcode=document.getElementById("blockcode").value;
            if(blockcode==""||blockcode=="0")
            {
                blockcode="";blockname="";
            }
             else
             {
                bc=blockcode.split("--");
                blockcode=bc[0];blockname=bc[1];
             }
             pancode=document.getElementById("pancode").value;
             if(pancode==""||pancode=="0")
             {
                pancode="";panname="";
             }
             else
             {
                pc=pancode.split("--");
                pancode=pc[0];panname=pc[1];
             }
             habitationcode=document.getElementById("habitationcode").value;
             if(habitationcode==""||habitationcode=="0")
             {
                habitationcode="";habitationname="";
             }
             else
             {
                hc=habitationcode.split("--");
                habitationcode=hc[0];habitationname=hc[1];
             }
             lbody=document.getElementById("lbody").value;
             if(lbody==""||lbody=="0")
             {
                lbody="";lbodyname="";
             }
             else
             {
                lb=lbody.split("--");
                lbody=lb[0];lbodyname=lb[1];
             }
             if(ltype=="0"||document.getElementById("ltype").selectedIndex==0)
                ltype="";
             if(spoint=="0")
                spoint="";
             var source=sourcetype[0]+"--"+sourcecode[0]
             total=document.getElementById("sn").value;
             var tbody=document.getElementById("tbody");
             var fg=true;
             if(tbody.rows.length<total)
             {
                       
                        var tr=document.createElement("tr");
                        var rid=tbody.rows.length+1;
                        tr.id=rid
                        
                        var td1=document.createElement("td");
                        var anch=document.createElement("A");
                        var textnode1=document.createTextNode("Edit");
                        var url="javascript:LoadRecord('"+rid+"')";
                        anch.href=url;
                        anch.appendChild(textnode1);
                        pan2=document.createElement("input");
                        pan2.type="hidden";
                        pan2.value=rid;
                        pan2.readonly="readonly";
                        td1.appendChild(pan2);
                        td1.appendChild(anch);
                        tr.appendChild(td1);
                        
                        var td3=document.createElement("td");
                        var textnode3=document.createTextNode(sample);
                        td3.appendChild(textnode3);
                        tr.appendChild(td3);
                        
                        var td12=document.createElement("td");
                        var textnode12=document.createTextNode(stype);
                        td12.appendChild(textnode12);
                        tr.appendChild(td12);
                        
                        var td13=document.createElement("td");
                        var textnode13=document.createTextNode(test_purpose);
                        pan2=document.createElement("input");
                        pan2.type="hidden";
                        pan2.value=test_purpose_id;
                        pan2.readonly="readonly";
                        td13.appendChild(pan2);
                        td13.appendChild(textnode13);
                        tr.appendChild(td13);
                      
                        var td4=document.createElement("td");
                        var textnode4=document.createTextNode(cdate);
                        td4.appendChild(textnode4);
                        tr.appendChild(td4);
                        
                        var td5=document.createElement("td");
                        var textnode5=document.createTextNode(distcode[1]);
                        pan2=document.createElement("input");
                        pan2.type="hidden";
                        pan2.value=distcode[0];
                        pan2.readonly="readonly";
                        td5.appendChild(pan2);
                        td5.appendChild(textnode5);
                        tr.appendChild(td5);
                        
                        var td6=document.createElement("td");
                        var textnode6=document.createTextNode(ltype);
                        td6.appendChild(textnode6);
                        tr.appendChild(td6);
                        
                        var td7=document.createElement("td");
                        var textnode7=document.createTextNode(lbodyname);
                        pan2=document.createElement("input");
                        pan2.type="hidden";
                        pan2.value=lbody;
                        pan2.readonly="readonly";
                        td7.appendChild(pan2);
                        td7.appendChild(textnode7);
                        tr.appendChild(td7);
                        
                        var td8=document.createElement("td");
                        var textnode8=document.createTextNode(blockname);
                        pan2=document.createElement("input");
                        pan2.type="hidden";
                        pan2.value=blockcode;
                        pan2.readonly="readonly";
                        td8.appendChild(pan2);
                        td8.appendChild(textnode8);
                        tr.appendChild(td8);
                       
                        var td9=document.createElement("td");
                        var textnode9=document.createTextNode(panname);
                        pan2=document.createElement("input");
                        pan2.type="hidden";
                        pan2.value=pancode;
                        pan2.readonly="readonly";
                        td9.appendChild(pan2);
                        td9.appendChild(textnode9);
                        tr.appendChild(td9);
                         
                        var td10=document.createElement("td");
                        var textnode10=document.createTextNode(habitationname);
                        pan2=document.createElement("input");
                        pan2.type="hidden";
                        pan2.value=habitationcode;
                        pan2.readonly="readonly";
                        td10.appendChild(pan2);
                        td10.appendChild(textnode10);
                        tr.appendChild(td10);
                        
                        var td11=document.createElement("td");
                        var textnode11=document.createTextNode(schemetype[1]);
                        pan2=document.createElement("input");
                        pan2.type="hidden";
                        pan2.value=schemetype[0];
                        pan2.readonly="readonly";
                        td11.appendChild(pan2);
                        td11.appendChild(textnode11);
                        tr.appendChild(td11);                                    
                        
                        var td14=document.createElement("td");
                        var textnode14=document.createTextNode(programmecode[1]);
                        pan2=document.createElement("input");
                        pan2.type="hidden";
                        pan2.value=programmecode[0];
                        pan2.readonly="readonly";
                        td14.appendChild(pan2);
                        td14.appendChild(textnode14);
                        tr.appendChild(td14);
                        var td15=document.createElement("td");
                        var textnode15=document.createTextNode(spoint);
                        pan2=document.createElement("input");
                        pan2.type="hidden";
                        pan2.value=source;
                        pan2.readonly="readonly";
                        td15.appendChild(pan2);
                        td15.appendChild(textnode15);
                        tr.appendChild(td15);
                        
                        var td16=document.createElement("td");
                        var textnode16=document.createTextNode(location);
                        td16.appendChild(textnode16);
                        tr.appendChild(td16);
                        
                        tbody.appendChild(tr);
                        
                        var sno=tbody.rows.length;
                        if(tbody.rows.length==total)
                        {
                            document.getElementById("add").disabled=true;
                        }
                        else
                        {
                            document.getElementById("add").disabled=false;
                            document.getElementById("sample").focus();
                        }
                        
                    }
                    else
                    {
                        document.getElementById("add").disabled=true;
                    }
                    document.getElementById("sample").value="";
                    document.getElementById("location").value="";
                    document.getElementById("ino").disabled=true;
    }
                
              
}
function submitfun()
{
            var tb=document.getElementById("tbody");
            var t=tb.rows.length 
            if(t>0)
           {
                    var lab=document.getElementById("labcode").value;
                    var labcode=lab.split("--");
                    var ino=document.getElementById("ino").value;
                    var sample=document.getElementById("sample").value;
                    var stype=document.getElementById("stype").value;
                    var record=null;
                    var tbody=document.getElementById("tbody");
                    var table=document.getElementById("Existing");           
                    var rid=0;
                    record1=new Array();record8=new Array();
                    record2=new Array();record9=new Array();
                    record3=new Array();record10=new Array();
                    record4=new Array();record11=new Array();
                    record5=new Array();record12=new Array();
                    record6=new Array();record13=new Array();
                    record7=new Array();record14=new Array();
                    record15=new Array();record16=new Array();
                    for(var i=1;i<=tbody.rows.length;i++)
                    {
                            rid=i;
                            var r=document.getElementById(rid);
                            var rcells=r.cells;
                            record2[i]=rcells.item(1).firstChild.nodeValue;
                            record3[i]=rcells.item(2).firstChild.nodeValue;
                            record4[i]=rcells.item(3).firstChild.value;
                            record5[i]=rcells.item(4).firstChild.nodeValue;
                            var rec=new Array();
                            rec=record5[i].split(" ");
                            rec1=rec[0];rec2=rec[1]+" "+rec[2];
                            record6[i]=rcells.item(5).firstChild.value;
                            record7[i]=rcells.item(6).firstChild.nodeValue;
                            record8[i]=rcells.item(7).firstChild.value;
                            record9[i]=rcells.item(8).firstChild.value;
                            record10[i]=rcells.item(9).firstChild.value;
                            record11[i]=rcells.item(10).firstChild.value;
                            record12[i]=rcells.item(11).firstChild.value;
                            record13[i]=rcells.item(12).firstChild.value;
                            record16[i]=rcells.item(13).firstChild.value;
                            record14[i]=rcells.item(13).lastChild.nodeValue;
                            record15[i]=rcells.item(14).firstChild.nodeValue;
                            var source=record16[i].split("--");
                            if(i==1)
                            {
                                record=record2[i]+"~"+record4[i]+"~"+record3[i]+"~"+rec1+"~"+record6[i]+"~"+record7[i]+"~"+record8[i]+"~"+record9[i]+"~"+record10[i]+"~"+record11[i]+"~"+record12[i]+"~"+source[0]+"~"+source[1]+"~"+record13[i]+"~"+record14[i]+"~"+record15[i]+"~"+rec2;
                            }
                            else
                            {
                                record=record+",,"+record2[i]+"~"+record4[i]+"~"+record3[i]+"~"+rec1+"~"+record6[i]+"~"+record7[i]+"~"+record8[i]+"~"+record9[i]+"~"+record10[i]+"~"+record11[i]+"~"+record12[i]+"~"+source[0]+"~"+source[1]+"~"+record13[i]+"~"+record14[i]+"~"+record15[i]+"~"+rec2;
                            }
                    }
                    var url="../../../../../../WQS_SampleEntry_Serv?option=submit&lcode="+labcode[0]+"&ino="+ino+"&record="+record;
                    var req=getTransport();
                    req.open("POST",url,true);
                    req.onreadystatechange=function()
                    {
                        process_res(req);
                    }  
                    req.send(null);
            }
            else
            {
                alert("Enter Item Details");
            }
}

function process_res(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {
            var response=req.responseXML.getElementsByTagName("response")[0];
            var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            if(command=="submit")
            {
                    var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                    if(flag=="success")
                    {
                         alert("Record inserted ");
                         document.getElementById("add").disabled=false;
                         document.getElementById("ino").disabled=false;
                         document.getElementById("ino").value="";
                         clearAll();
                         loadTestPurpose();
                    }
                    else
                    {
                         alert("Insertion failure");
                         document.getElementById("add").disabled=true;
                         document.getElementById("ino").disabled=true;
                    }
            }
            else if(command=="changeType") 
            {
                   var gflag=response.getElementsByTagName("gflag")[0].firstChild.nodeValue;
                   if(gflag=="success")
                   {
                       var sourcecodecount=response.getElementsByTagName("sourcecodecount")[0].firstChild.nodeValue;
                       var sourcetypeid=document.getElementById("sourcecode");                            
                       for(var i=0;i<sourcecodecount;i++)
                       {
                            var code=response.getElementsByTagName("sourcecode")[i].firstChild.nodeValue;;
                            var desc=response.getElementsByTagName("sourcetype")[i].firstChild.nodeValue;;
                           
                            var opt=document.createElement("option");
                            opt.setAttribute("value",code+"--"+desc);
                            var text=document.createTextNode(desc);
                            opt.appendChild(text);                            
                            sourcetypeid.appendChild(opt);
                       }
                    }
                }
            }
        }
}

function changeType()
{
    var type1=document.getElementById("sourcetype").value;
    type1=type1.split("--");
    var sourcecode=document.getElementById("sourcecode");
    var child=sourcecode.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
        sourcecode.removeChild(child[c]);
    }
                        
    req=getTransport();
    var url="../../../../../../WQS_SampleEntry_Serv?option=changeType&Type="+type1[0];
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        process_res(req);
    }
    req.send(null);
}

function changeSample()
{
    var lab=document.getElementById("labcode").value;
    var labcode=lab.split("--");
    var ino=document.getElementById("ino").value;
    var sample=document.getElementById("sample").value;
   
        var tbody=document.getElementById("tbody");
        for(var i=1;i<=tbody.rows.length;i++)
        {
            rid=i;
            var r=document.getElementById(rid);
            var rcells=r.cells;
            if(sample==rcells.item(1).firstChild.nodeValue)
            {
                alert("Sample Number Already Entered");
                document.getElementById("sample").value="";
                document.monitoring.sample.focus();
                return false;
            }
        } 
        req=getTransport();
        var url="../../../../../../WQS_SampleEntry_Serv?option=changeSample&lab="+labcode[0]+"&invoice_number="+ino+"&sample_no="+sample;
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                    var response=req.responseXML.getElementsByTagName("response")[0];
                    var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                    if(flag=="success")
                        return true;
                    else
                    {
                        alert("This Sample Already Stored for This Invoice");
                        document.getElementById("sample").value="";
                        document.monitoring.sample.focus();
                        return false;
                    }
                }
            }
                
        }
        req.send(null);
}

function LoadRecord(rid)
{
    com_id=rid
    if(document.monitoring.test_purpose.length>1)
    {
            for(i=0;i<document.monitoring.test_purpose.length;i++)
            {
                        document.monitoring.test_purpose[i].checked=false;
            }
    }
    document.getElementById("update").disabled=false;
    document.getElementById("del").disabled=false;
    document.getElementById("add").disabled=true;
    
    var r=document.getElementById(com_id);
    var rcells=r.cells;
   
    record1=rcells.item(1).firstChild.nodeValue;
    record2=rcells.item(2).firstChild.nodeValue;   
    record3=rcells.item(3).firstChild.value;
    record33=rcells.item(3).lastChild.nodeValue;
    record4=rcells.item(4).firstChild.nodeValue;
    var rec=new Array();
    rec=record4.split(" ");
    record5=rcells.item(5).firstChild.value;
    record55=rcells.item(5).lastChild.nodeValue;
    record6=rcells.item(6).firstChild.nodeValue;
    record7=rcells.item(7).firstChild.value;
    record8=rcells.item(8).firstChild.value;
    record9=rcells.item(9).firstChild.value;
    record10=rcells.item(10).firstChild.value;
    record11=rcells.item(11).firstChild.value;
    record111=rcells.item(11).lastChild.nodeValue;
    record12=rcells.item(12).firstChild.value;
    record121=rcells.item(12).lastChild.nodeValue;
    record13=rcells.item(13).lastChild.nodeValue;
    record131=rcells.item(13).firstChild.value;
    record14=rcells.item(14).firstChild.nodeValue;
    document.getElementById("sample").value=record1;
    document.getElementById("stype").value=record2;
    var purpose_id=record3.split(",");
    var purpose=record33.split(",");
    if(document.monitoring.test_purpose.length>1)
    {
        for(j=0;j<purpose.length;j++)
        {
            for(i=0;i<document.monitoring.test_purpose.length;i++)
            {
                    if(document.monitoring.test_purpose[i].value==purpose_id[j]+"--"+purpose[j])
                        document.monitoring.test_purpose[i].checked=true;
            }
        }
    }
    document.getElementById("cdate").value=rec[0];
    document.getElementById("ctime").value=rec[1];
    if(rec[2]=="AM")
        document.monitoring.ct[0].checked=true;
    else if(rec[2]=="PM")
        document.monitoring.ct[1].checked=true;
    document.getElementById("distcode").value=record5+"--"+record55;
    if(record6!=""||record6!=0)
        document.getElementById("ltype").value=record6;
    else
        document.getElementById("ltype").selectedIndex="";
        
    if(record6=="VP")
    {
        document.getElementById("local").style.display='none';
        document.getElementById("village").style.display='block';
    }
    else if(record6=="")
    {
        document.getElementById("local").style.display='none';
        document.getElementById("village").style.display='none';
    }
    else
    {
        document.getElementById("local").style.display='block';
        document.getElementById("village").style.display='none';
    }
    var scheme=record11.split("--");
    var source=record131.split("--");
    var stype=document.getElementById("sourcetype");
    var stypedesc="";
    for(var c=stype.length-1;c>=0;c--)
    {
            var cat=new Array();
            var ct=stype.options[c].value;
            cat=ct.split("--");
            if(source[0]==cat[0])
            {
                stypedesc=cat[1];
            }
    }
    if(scheme[0]==""||scheme[0].length==0)
        document.getElementById("schemetype").selectedIndex="";
    else
        document.getElementById("schemetype").value=record11+"--"+record111;
    if(source[0].length==0||source[0]=="")
        document.getElementById("sourcetype").selectedIndex="";
    else
        document.getElementById("sourcetype").value=source[0]+"--"+stypedesc;
    if(record12==""||record12.length==0)
        document.getElementById("programmecode").selectedIndex="";
    else
        document.getElementById("programmecode").value=record12+"--"+record121;
    if(record13==""||record13.length==0)
        document.getElementById("spoint").selectedIndex="";
    else
        document.getElementById("spoint").value=record13;
    document.getElementById("location").value=record14;
   
    var req=getTransport();
    if(record6!="VP" && record6!="")
    {
        var url="../../../../../../WQS_SampleEntry_Serv?option=LoadRecord&type=others&distcode="+record5+"&ltype="+record6+"&sourcetype="+source[0];
    }
    else if(record6!="")
    {
        var url="../../../../../../WQS_SampleEntry_Serv?option=LoadRecord&type=VP&distcode="+record5+"&blockcode="+record8+"&pancode="+record9+"&sourcetype="+source[0];
    }
    else
    {
        var url="../../../../../../WQS_SampleEntry_Serv?option=LoadRecord&type=empty&sourcetype="+source[0];
    }
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        if(req.readyState==4)
        {
            if(req.status==200)
            {
                var response=req.responseXML.getElementsByTagName("response")[0];
                var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
                var type=response.getElementsByTagName("type")[0].firstChild.nodeValue;
                clearCombo();
                if(type=="others")
                {
                        var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                        var lbody=document.getElementById("lbody");
                        if(flag=="success")
                        {
                             var tcount=response.getElementsByTagName("tcount")[0].firstChild.nodeValue;                             
                             for(var j=0;j<tcount;j++)
                             {
                                var lcode=response.getElementsByTagName("code")[j].firstChild.nodeValue;
                                var lname=response.getElementsByTagName("name")[j].firstChild.nodeValue;
                                var opt=document.createElement("option");
                                opt.setAttribute("value",lcode+"--"+lname);
                                var txt=document.createTextNode(lname);
                                opt.appendChild(txt);
                                lbody.appendChild(opt);
                             }
                        }
                       if(record7!=""||record7!=0)
                       {
                            var lbody=document.getElementById("lbody");
                            var lb="";
                            for(var c=lbody.length-1;c>=0;c--)
                            {
                                    var cat=new Array();
                                    var ct=lbody.options[c].value;
                                    cat=ct.split("--");
                                    if(record7==cat[0])
                                    {
                                        lb=cat[1];
                                    }
                            }
                            document.getElementById("lbody").value=record7+"--"+lb;
                        }
                        else
                            document.getElementById("lbody").selectedIndex="";
                        document.getElementById("blockcode").selectedIndex="";
                        document.getElementById("pancode").selectedIndex="";
                        document.getElementById("habitationcode").selectedIndex="";
                        document.getElementById("village").style.display="none";
                }
                else if(type=="VP")
                {
                    document.getElementById("lbody").selectedIndex="";    
                    var blockid=document.getElementById("blockcode");
                    var blockcount=response.getElementsByTagName("blockcount")[0].firstChild.nodeValue;
                    var flag1=response.getElementsByTagName("flag1")[0].firstChild.nodeValue;
                    if(flag1=="success");
                    {                        
                        for(var i=0;i<blockcount;i++)
                        {
                            var code=response.getElementsByTagName("block_code")[i].firstChild.nodeValue;
                            var desc=response.getElementsByTagName("block_name")[i].firstChild.nodeValue;
                            var opt=document.createElement("option");
                            opt.setAttribute("value",code+"--"+desc);
                            var text=document.createTextNode(desc);
                            opt.appendChild(text);
                            blockid.appendChild(opt);
                        }
                        var bc="";
                        if(record8!="")
                        {
                            for(var c=blockid.length-1;c>=0;c--)
                            {
                                    var cat=new Array();
                                    var ct=blockid.options[c].value;
                                    cat=ct.split("--");
                                    if(record8==cat[0])
                                    {
                                        bc=cat[1];
                                    }
                            }
                            document.getElementById("blockcode").value=record8+"--"+bc;
                        }
                        else
                            document.getElementById("blockcode").selectedIndex="";
                    }                    
                    var pancode=document.getElementById("pancode");
                    var panchayatcount=response.getElementsByTagName("pcount");
                    var flag2=response.getElementsByTagName("flag2")[0].firstChild.nodeValue;
                    if(flag2=="success")
                    {
                         
                         for(var j=0;j<panchayatcount.length;j++)
                         {
                            var panchayat_code=response.getElementsByTagName("panchayat_code")[j].firstChild.nodeValue;
                            var panchayat_name=response.getElementsByTagName("panchayat_name")[j].firstChild.nodeValue;
                            var opt=document.createElement("option");
                            opt.setAttribute("value",panchayat_code+"--"+panchayat_name);
                            var txt=document.createTextNode(panchayat_name);
                            opt.appendChild(txt);
                            pancode.appendChild(opt);
                         }
                         var pc="";
                         if(record9!="")
                         {
                             for(var c=pancode.length-1;c>=0;c--)
                             {
                                    var cat=new Array();
                                    var ct=pancode.options[c].value;
                                    cat=ct.split("--");
                                    if(record9==cat[0])
                                    {
                                        pc=cat[1];
                                    }
                             }
                             document.getElementById("pancode").value=record9+"--"+pc;
                         }
                         else
                            document.getElementById("pancode").selectedIndex="";
                    }
                    else
                        document.getElementById("pancode").selectedIndex="";
                    var habitationcode=document.getElementById("habitationcode");
                    var habitationcount=response.getElementsByTagName("hcount");                    
                    var flag3=response.getElementsByTagName("flag3")[0].firstChild.nodeValue;
                    if(flag3=="success")
                    {                         
                         for(var j=0;j<habitationcount.length;j++)
                         {
                            var habcode=response.getElementsByTagName("habitation_code")[j].firstChild.nodeValue;
                            var habname=response.getElementsByTagName("habitation_name")[j].firstChild.nodeValue;
                            var opt=document.createElement("option");
                            opt.setAttribute("value",habcode+"--"+habname);
                            var txt=document.createTextNode(habname);
                            opt.appendChild(txt);
                            habitationcode.appendChild(opt);
                         }
                    
                        var hc="";
                        if(record10!="")
                        {
                            for(var c=habitationcode.length-1;c>=0;c--)
                            {
                                    var cat=new Array();
                                    var ct=habitationcode.options[c].value;
                                    cat=ct.split("--");
                                    if(record10==cat[0])
                                    {
                                        hc=cat[1];
                                    }
                            }                        
                            document.getElementById("habitationcode").value=record10+"--"+hc;
                        }
                        else
                            document.getElementById("habitationcode").selectedIndex="";
                    }
                    else
                        document.getElementById("habitationcode").selectedIndex="";
                    document.getElementById("village").style.display="block";
                }
                var gflag=response.getElementsByTagName("gflag")[0].firstChild.nodeValue;
                if(gflag=="success")
                {
                       var sourcecodecount=response.getElementsByTagName("source_count");
                       var sourcetypeid=document.getElementById("sourcecode");   
                       var child=sourcetypeid.childNodes;
                       for(var c=child.length-1;c>1;c--)
                       {
                            sourcetypeid.removeChild(child[c]);
                       }
                       for(var i=0;i<sourcecodecount.length;i++)
                       {
                            var code=response.getElementsByTagName("sourcecode")[i].firstChild.nodeValue;;
                            var desc=response.getElementsByTagName("sourcetype")[i].firstChild.nodeValue;;
                           
                            var opt=document.createElement("option");
                            opt.setAttribute("value",code+"--"+desc);
                            var text=document.createTextNode(desc);
                            opt.appendChild(text);                            
                            sourcetypeid.appendChild(opt);
                       }
                 }
                 
                var sc="";
                if(source[1]!="")
                {
                    for(var c=sourcetypeid.length-1;c>=0;c--)
                    {
                            var cat=new Array();
                            var ct=sourcetypeid.options[c].value;
                            cat=ct.split("--");
                            if(source[1]==cat[0])
                            {
                                sc=cat[1];
                            }
                    }
                    document.getElementById("sourcecode").value=source[1]+"--"+sc;
                }
                else
                    document.getElementById("sourcecode").selectedIndex="";
                 
            }
        }
    }
    req.send(null);
}
function update_record()
{
    //sn=document.getElementById("sn").value;
    var sample=document.getElementById("sample").value;
    var sample_type=document.getElementById("stype").value;
    var test_purpose="";var count=0;var test_purpose_id="";
    if(document.monitoring.test_purpose.length>1)
    {
        for(i=0;i<document.monitoring.test_purpose.length;i++)
        {
                if(document.monitoring.test_purpose[i].checked==true)
                {
                        var test=document.monitoring.test_purpose[i].value.split("--");
                        if(count==0)
                        {
                            test_purpose_id=test[0];
                            test_purpose=test[1];
                        }
                        else
                        {
                            test_purpose_id=test_purpose_id+","+test[0];
                            test_purpose=test_purpose+","+test[1];
                        }
                        count++
                }
                
        }
    }
    cdate=document.getElementById("cdate").value;
    ctime=document.getElementById("ctime").value;
    var ct="";
    if(document.monitoring.ct[0].checked==true)
        ct="AM";
    else if(document.monitoring.ct[1].checked==true)
        ct="PM";
    cdate=cdate+" "+ctime+" "+ct;
    distcode=document.getElementById("distcode").value;
    dcode=distcode.split("--");
    ltype=document.getElementById("ltype").value;
    schemetype=document.getElementById("schemetype").value;
    sctype=schemetype.split("--");
    if(sctype[0]==0||sctype[0]=="")
    {
        sctype[0]="";sctype[1]="";
    }
    sourcetype=document.getElementById("sourcetype").value;
    stype=sourcetype.split("--");
    if(stype[0]==0||stype[0]=="")
    {
        stype[0]="";stype[1]="";
    }
    sourcecode=document.getElementById("sourcecode").value;
    scode=sourcecode.split("--");
    if(scode[0]==0||scode[0]=="")
    {
        scode[0]="";scode[1]="";
    }
    var source=stype[0]+"--"+scode[0];
    programmecode=document.getElementById("programmecode").value;
    pgcode=programmecode.split("--");
    if(pgcode[0]==0||pgcode[0]=="")
    {
        pgcode[0]="";pgcode[1]="";
    }
    spoint=document.getElementById("spoint").value;
    loc=document.getElementById("location").value;
    blockcode=document.getElementById("blockcode").value;
    if(blockcode==""||blockcode=="0")
    {
        blockcode="";blockname="";
    }
     else
     {
        bc=blockcode.split("--");
        blockcode=bc[0];blockname=bc[1];
     }
     pancode=document.getElementById("pancode").value;
     if(pancode==""||pancode=="0")
     {
        pancode="";panname="";
     }
     else
     {
        pc=pancode.split("--");
        pancode=pc[0];panname=pc[1];
     }
     habitationcode=document.getElementById("habitationcode").value;
     if(habitationcode==""||habitationcode=="0")
     {
        habitationcode="";habitationname="";
     }
     else
     {
        hc=habitationcode.split("--");
        habitationcode=hc[0];habitationname=hc[1];
     }
     lbody=document.getElementById("lbody").value;
     if(lbody==""||lbody=="0")
     {
        lbody="";lbodyname="";
     }
     else
     {
        lb=lbody.split("--");
        lbody=lb[0];lbodyname=lb[1];
     }
     if(ltype=="0"||document.getElementById("ltype").selectedIndex==0)
        ltype="";
     if(spoint=="0")
        spoint="";
    var r=document.getElementById(com_id); 
    var rcells=r.cells;
    rcells.item(1).firstChild.nodeValue=sample;
    rcells.item(2).firstChild.nodeValue=sample_type;
    rcells.item(3).firstChild.value=test_purpose_id;
    rcells.item(3).lastChild.nodeValue=test_purpose;
    rcells.item(4).firstChild.nodeValue=cdate;
    rcells.item(5).lastChild.nodeValue=dcode[1];
    rcells.item(5).firstChild.value=dcode[0];
    rcells.item(6).firstChild.nodeValue=ltype;
    if(ltype!="VP")
    {
        if(ltype=="")
        {
            rcells.item(7).firstChild.value="";
            rcells.item(7).lastChild.nodeValue="";
            rcells.item(8).firstChild.value="";
            rcells.item(8).lastChild.nodeValue="";
            rcells.item(9).firstChild.value="";
            rcells.item(9).lastChild.nodeValue="";
            rcells.item(10).firstChild.value="";
            rcells.item(10).lastChild.nodeValue="";
        }
        else
        {
            
            rcells.item(7).firstChild.value=lbody;   
            rcells.item(7).lastChild.nodeValue=lbodyname;  
            rcells.item(8).firstChild.value="";
            rcells.item(8).lastChild.nodeValue="";
            rcells.item(9).firstChild.value="";
            rcells.item(9).lastChild.nodeValue="";
            rcells.item(10).firstChild.value="";
            rcells.item(10).lastChild.nodeValue="";
        }
    }
    else
    {
        rcells.item(7).firstChild.value="";
        rcells.item(7).lastChild.nodeValue=""; 
        rcells.item(8).firstChild.value=blockcode;
        rcells.item(8).lastChild.nodeValue=blockname; 
        rcells.item(9).firstChild.value=pancode;
        rcells.item(9).lastChild.nodeValue=panname; 
        rcells.item(10).firstChild.value=habitationcode;
        rcells.item(10).lastChild.nodeValue=habitationname; 
    }
    rcells.item(11).firstChild.value=sctype[0];
    rcells.item(11).lastChild.nodeValue=sctype[1]; 
    rcells.item(12).firstChild.value=pgcode[0];
    rcells.item(12).lastChild.nodeValue=pgcode[1]; 
    rcells.item(13).firstChild.value=source;
    rcells.item(13).lastChild.nodeValue=spoint;
    rcells.item(14).firstChild.nodeValue=loc;
    
    
    document.getElementById("sample").value="";
    document.getElementById("location").value="";
    document.getElementById("update").disabled=true;
    document.getElementById("del").disabled=true;
    var total=document.getElementById("sn").value;
    var tbody=document.getElementById("tbody");
    if(tbody.rows.length==total)
    {
        document.getElementById("add").disabled=true;
    }
    else
    {
        document.getElementById("add").disabled=false;
    }
                        
}

function delete_record()
{
    var current_id=com_id;
    var tbody=document.getElementById("tbody");
    document.getElementById("update").disabled=true;
    document.getElementById("del").disabled=true;
    document.getElementById("add").disabled=false;
    var item1=new Array();var item2=new Array();
    var item3=new Array();var item4=new Array();
    var item5=new Array();var item6=new Array();
    var item7=new Array();var item8=new Array();
    var item9=new Array();var item10=new Array();
    var item11=new Array();var item12=new Array();
    var item13=new Array();var item14=new Array();
    var item30=new Array();var item50=new Array();
    var item70=new Array();var item80=new Array();
    var item90=new Array();var item100=new Array();
    var item110=new Array();var item120=new Array();
    var item130=new Array();
    var j=0;
    for(var i=1;i<=tbody.rows.length;i++)
    {
            rid=i;
            var val=document.getElementById(rid);
            var rcells=val.cells;
            if(current_id!=rcells.item(0).firstChild.value)
            {
                j++;
                item1[j]=rcells.item(1).firstChild.nodeValue;
                item2[j]=rcells.item(2).firstChild.nodeValue;
                item3[j]=rcells.item(3).lastChild.nodeValue;
                item30[j]=rcells.item(3).firstChild.value;
                item4[j]=rcells.item(4).firstChild.nodeValue;
                item5[j]=rcells.item(5).lastChild.nodeValue;
                item50[j]=rcells.item(5).firstChild.value;
                item6[j]=rcells.item(6).firstChild.nodeValue;                
                item7[j]=rcells.item(7).lastChild.nodeValue;
                item70[j]=rcells.item(7).firstChild.value;
                item8[j]=rcells.item(8).lastChild.nodeValue;
                item80[j]=rcells.item(8).firstChild.value;
                item9[j]=rcells.item(9).lastChild.nodeValue;
                item90[j]=rcells.item(9).firstChild.value;
                item10[j]=rcells.item(10).lastChild.nodeValue;
                item100[j]=rcells.item(10).firstChild.value;
                item11[j]=rcells.item(11).lastChild.nodeValue;
                item110[j]=rcells.item(11).firstChild.value;
                item12[j]=rcells.item(12).lastChild.nodeValue;
                item120[j]=rcells.item(12).firstChild.value;
                item13[j]=rcells.item(13).lastChild.nodeValue;
                item130[j]=rcells.item(13).firstChild.value;
                item14[j]=rcells.item(14).firstChild.nodeValue;
                
            }
    }
    var tb=document.getElementById("tbody");
    var t=tb.rows.length   
    for(var i=t-1;i>=0;i--)
    {
          tb.deleteRow(i);
    } 
    for(var i=1;i<=j;i++)
    {
                var tr=document.createElement("tr");
                var rid=i
                tr.id=i;
                
                var td1=document.createElement("td");
                var anch=document.createElement("A");
                var textnode1=document.createTextNode("Edit");
                var url="javascript:LoadRecord('"+i+"')";
                anch.href=url;
                anch.appendChild(textnode1);
                pan2=document.createElement("input");
                pan2.type="hidden";
                pan2.value=rid;
                pan2.readonly="readonly";
                td1.appendChild(pan2);
                td1.appendChild(anch);
                tr.appendChild(td1);
                
                var td3=document.createElement("td");
                var textnode3=document.createTextNode(item1[i]);
                td3.appendChild(textnode3);
                tr.appendChild(td3);
              
                var td4=document.createElement("td");
                var textnode4=document.createTextNode(item2[i]);
                td4.appendChild(textnode4);
                tr.appendChild(td4);
                
                var td5=document.createElement("td");
                var textnode5=document.createTextNode(item3[i]);
                pan2=document.createElement("input");
                pan2.type="hidden";
                pan2.value=item30[i];
                pan2.readonly="readonly";
                td5.appendChild(pan2);
                td5.appendChild(textnode5);
                tr.appendChild(td5);
                
                var td6=document.createElement("td");
                var textnode6=document.createTextNode(item4[i]);
                td6.appendChild(textnode6);
                tr.appendChild(td6);
                
                var td7=document.createElement("td");
                var textnode7=document.createTextNode(item5[i]);
                pan2=document.createElement("input");
                pan2.type="hidden";
                pan2.value=item50[i];
                pan2.readonly="readonly";
                td7.appendChild(pan2);
                td7.appendChild(textnode7);
                tr.appendChild(td7);
                
                var td8=document.createElement("td");
                var textnode8=document.createTextNode(item6[i]);
                td8.appendChild(textnode8);
                tr.appendChild(td8);
               
                var td9=document.createElement("td");
                var textnode9=document.createTextNode(item7[i]);
                pan2=document.createElement("input");
                pan2.type="hidden";
                pan2.value=item70[i];
                pan2.readonly="readonly";
                td9.appendChild(pan2);
                td9.appendChild(textnode9);
                tr.appendChild(td9);
                 
                var td10=document.createElement("td");
                var textnode10=document.createTextNode(item8[i]);
                pan2=document.createElement("input");
                pan2.type="hidden";
                pan2.value=item80[i];
                pan2.readonly="readonly";
                td10.appendChild(pan2);
                td10.appendChild(textnode10);
                tr.appendChild(td10);
                
                var td11=document.createElement("td");
                var textnode11=document.createTextNode(item9[i]);
                pan2=document.createElement("input");
                pan2.type="hidden";
                pan2.value=item90[i];
                pan2.readonly="readonly";
                td11.appendChild(pan2);
                td11.appendChild(textnode11);
                tr.appendChild(td11);
                
                var td12=document.createElement("td");
                var textnode12=document.createTextNode(item10[i]);
                pan2=document.createElement("input");
                pan2.type="hidden";
                pan2.value=item100[i];
                pan2.readonly="readonly";
                td12.appendChild(pan2);
                td12.appendChild(textnode12);
                tr.appendChild(td12);
                
                var td13=document.createElement("td");
                var textnode13=document.createTextNode(item11[i]);
                pan2=document.createElement("input");
                pan2.type="hidden";
                pan2.value=item110[i];
                pan2.readonly="readonly";
                td13.appendChild(pan2);
                td13.appendChild(textnode13);
                tr.appendChild(td13);
                
                var td14=document.createElement("td");
                var textnode14=document.createTextNode(item12[i]);
                pan2=document.createElement("input");
                pan2.type="hidden";
                pan2.value=item120[i];
                pan2.readonly="readonly";
                td14.appendChild(pan2);
                td14.appendChild(textnode14);
                tr.appendChild(td14);
                
                var td15=document.createElement("td");
                var textnode15=document.createTextNode(item13[i]);
                pan2=document.createElement("input");
                pan2.type="hidden";
                pan2.value=item130[i];
                pan2.readonly="readonly";
                td15.appendChild(pan2);
                td15.appendChild(textnode15);
                tr.appendChild(td15);
                
                var td16=document.createElement("td");
                var textnode16=document.createTextNode(item14[i]);
                td16.appendChild(textnode16);
                tr.appendChild(td16);
                
                tbody.appendChild(tr);
    }
    document.getElementById("update").disabled=true;
    document.getElementById("del").disabled=true;
    document.getElementById("add").disabled=false;
    document.getElementById("sample").value="";
    document.getElementById("location").value="";    
}

function checkTime()
{
    var cval=document.getElementById("ctime").value;
    var sp=cval.split(":");
    if(sp.length==1)
    {
        if(sp[0]>12)
        {
            alert("check time");
            document.getElementById("ctime").value="";
            document.getElementById("ctime").focus();
        }
    }
    else if(sp.length>1)
    {
        if(sp[0]>12)
        {
            alert("check time");
            document.getElementById("ctime").value="";
            document.getElementById("ctime").focus();
        }
        else
        {
            if(sp[1]>60)
            {
                alert("check time");
                document.getElementById("ctime").value="";
                document.getElementById("ctime").focus();
            }
        }
    }
}

function checkInvoice()
{
    if(document.getElementById("ino").value==""||document.getElementById("ino").value.length==0)
    {
        alert("Enter Invoice Number");
        document.getElementById("ino").focus();
        return false;
    }
    else if(document.getElementById("stype").value==""||document.getElementById("stype").selectedIndex==0)
    {
        alert("Select Sample Type");
        document.getElementById("stype").focus();
    }
    else
    {
        count=0;
        if(document.monitoring.test_purpose.length>1)
        {
            for(i=0;i<document.monitoring.test_purpose.length;i++)
            {
                    if(document.monitoring.test_purpose[i].checked==true)
                    {
                            count++
                    }
                    
            }
        }
        if(count==0)
        {
            alert("Select Test Purpose");
            return false;
        }
        else
            return true;
    }
}

function checkSample()
{
    var val=checkInvoice()
    if(val==true)
    {
        if(document.getElementById("sample").value==""||document.getElementById("sample").value.length==0)
        {
            alert("Select Sample Number");
            return false;
        }
        else
            return true;
    }
   
}

function checkDistrict()
{
    var inv=checkSample();
    if(inv==true)
    {
        if(document.getElementById("distcode").value=="0"||document.getElementById("distcode").selectedIndex==0)
        {
            alert("Select District");
            return false;
        }
        else
            return true;
    }
}
function checkValid()
{
    var loc=checkDistrict();
    if(loc==true)
    {
        if(document.getElementById("ctype").value=='Twad')
        {
            if(document.getElementById("ltype").value==0||document.getElementById("ltype").value=="")
            {
                alert("Select Location Type");
                document.getElementById("location").value="";
                return false;
            }              
            else
            {
                    if(document.getElementById("ltype").value=="VP")
                    {  
                        if(document.getElementById("blockcode").value==0||document.getElementById("blockcode").value=="")
                        {
                            alert("Select Block");
                        }
                        else if(document.getElementById("pancode").value==0||document.getElementById("pancode").value=="")
                        {
                            alert("Select Panchayat");
                        }
                        else if(document.getElementById("habitationcode").value==0||document.getElementById("habitationcode").value=="")
                        {
                            alert("Select Habitation");
                        }
                        else
                            return true;
                    }
                    else
                    {
                        if(document.getElementById("lbody").value==0||document.getElementById("lbody").value=="")
                        {
                            alert("Select Local Body");
                            document.getElementById("location").value="";
                            return false;
                        }
                        else
                            return true;
                    }
            }
        }
        else
            return true;
    }
   
}
function changeTime()
{
    var val=document.monitoring.ctime.value;
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
    document.monitoring.ctime.value=tot;
}
function numbersonly(e,t)
{
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur(); }catch(e){}
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
            {
                return false 
            }
        }
}  
