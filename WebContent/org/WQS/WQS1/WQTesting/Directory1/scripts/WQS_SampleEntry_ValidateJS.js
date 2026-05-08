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

function clearAll()
{
    //document.getElementById("stype").value="";
    document.getElementById("ctype").value="";
    document.getElementById("total").value="";
    var tb=document.getElementById("tbody");
    var t=tb.rows.length   
    for(var i=t-1;i>=0;i--)
    {
          tb.deleteRow(i);
    } 
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
function load_labcode()
{
    clearAll();   
    document.getElementById("ino").disabled=false;
    document.getElementById("ino").value="";
    lab=document.getElementById("labcode").value;
    lab=lab.split("--");
    req=getTransport();
    var url="../../../../../../WQS_SampleEntry_ValidateServ?option=load_labcode&lab="+lab[0];
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
                else if(command=="changeInvoice")
                {
                        var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                       if(flag=="success")
                       {
                                var tbody=document.getElementById("tbody");
                                var count=response.getElementsByTagName("row_count");
                                var total_samples=response.getElementsByTagName("total_samples")[0].firstChild.nodeValue;                                
                                var ctype=response.getElementsByTagName("ctype")[0].firstChild.nodeValue;
                                document.getElementById("total").value=total_samples;
                                document.getElementById("ctype").value=ctype;
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
                                for(var i=0;i<count.length;i++)
                                {
                                        var sample_number=count[i].getElementsByTagName("sample_number")[0].firstChild.nodeValue;
                                        var date_of_collection=count[i].getElementsByTagName("date_of_collection")[0].firstChild.nodeValue;
                                        var collection_time=count[i].getElementsByTagName("collection_time")[0].firstChild.nodeValue;
                                        if(collection_time==""||collection_time==null)
                                            collection_time="";
                                        var district_code=count[i].getElementsByTagName("district_code")[0].firstChild.nodeValue;
                                        var district_name=count[i].getElementsByTagName("district_name")[0].firstChild.nodeValue;
                                        var location_type=count[i].getElementsByTagName("location_type")[0].firstChild.nodeValue;
                                        var local_body_code=count[i].getElementsByTagName("local_body_code")[0].firstChild.nodeValue;
                                        var lname=count[i].getElementsByTagName("lbody")[0].firstChild.nodeValue;
                                        var block_code=count[i].getElementsByTagName("block_code")[0].firstChild.nodeValue;
                                        var bname=count[i].getElementsByTagName("bname")[0].firstChild.nodeValue;
                                        var panchayat_code=count[i].getElementsByTagName("panchayat_code")[0].firstChild.nodeValue;
                                        var pname=count[i].getElementsByTagName("pname")[0].firstChild.nodeValue;
                                        var habitation_code=count[i].getElementsByTagName("habitation_code")[0].firstChild.nodeValue;
                                        var hname=count[i].getElementsByTagName("hname")[0].firstChild.nodeValue;
                                        var scheme_type=count[i].getElementsByTagName("scheme_type")[0].firstChild.nodeValue;
                                        if(scheme_type==""||scheme_type=="-")
                                            scheme_type="";
                                        var scheme_type_name=count[i].getElementsByTagName("scheme_type_name")[0].firstChild.nodeValue;
                                        if(scheme_type_name==""||scheme_type_name=="-")
                                            scheme_type_name="";
                                        var source_type=count[i].getElementsByTagName("source_type")[0].firstChild.nodeValue;
                                        if(source_type==""||source_type=="-")
                                            source_type="";
                                        var source_code=count[i].getElementsByTagName("source_code")[0].firstChild.nodeValue;
                                        if(source_code==""||source_code=="-")
                                            source_code="";
                                        var source=source_type+"--"+source_code;
                                        var programme=count[i].getElementsByTagName("programme")[0].firstChild.nodeValue;
                                        if(programme==""||programme=="-")
                                            programme="";
                                        var programme_desc=count[i].getElementsByTagName("programme_desc")[0].firstChild.nodeValue;
                                        if(programme_desc==""||programme_desc=="-")
                                            programme_desc="";
                                        var sampling_point=count[i].getElementsByTagName("sampling_point")[0].firstChild.nodeValue;
                                        if(sampling_point==""||sampling_point=="-")
                                            sampling_point="";
                                        if(location_type==""||location_type=="-")
                                            location_type="";                                        
                                        if(local_body_code==""||local_body_code=="-")
                                        {
                                            local_body_code="";lname=" ";
                                        }
                                        if(block_code==""||block_code=="-")
                                        {
                                            block_code="";bname="";
                                        }
                                        if(panchayat_code==""||panchayat_code=="-")
                                        {
                                            panchayat_code="";pname="";
                                        }
                                        if(habitation_code==""||habitation_code=="-")
                                        {
                                            habitation_code="";hname="";
                                        }
                                        var location=count[i].getElementsByTagName("location")[0].firstChild.nodeValue;
                                        var sample_type=count[i].getElementsByTagName("sample_type")[0].firstChild.nodeValue;
                                        var test_purpose_id=count[i].getElementsByTagName("test_purpose_id")[0].firstChild.nodeValue;
                                        var test_purpose=count[i].getElementsByTagName("test_purpose")[0].firstChild.nodeValue;
                                        date_of_collection=date_of_collection+" "+collection_time;
                                        var tr=document.createElement("tr");
                                        tr.id=sample_number
                                        
                                        var td1=document.createElement("td");
                                        var anch=document.createElement("A");
                                        var textnode1=document.createTextNode("Edit");
                                        var url="javascript:LoadRecord('"+sample_number+"')";
                                        anch.href=url;
                                        anch.appendChild(textnode1);
                                        pan2=document.createElement("input");
                                        pan2.type="hidden";
                                        pan2.value=sample_number;
                                        pan2.readonly="readonly";
                                        td1.appendChild(pan2);
                                        td1.appendChild(anch);
                                        tr.appendChild(td1);
                                        
                                        var td3=document.createElement("td");
                                        var textnode3=document.createTextNode(sample_number);
                                        td3.appendChild(textnode3);
                                        tr.appendChild(td3);
                                        
                                        var td12=document.createElement("td");
                                        var textnode12=document.createTextNode(sample_type);
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
                                        var textnode4=document.createTextNode(date_of_collection);
                                        td4.appendChild(textnode4);
                                        tr.appendChild(td4);
                                        
                                        var td5=document.createElement("td");
                                        var textnode5=document.createTextNode(district_name);
                                        pan2=document.createElement("input");
                                        pan2.type="hidden";
                                        pan2.value=district_code;
                                        pan2.readonly="readonly";
                                        td5.appendChild(pan2);
                                        td5.appendChild(textnode5);
                                        tr.appendChild(td5);
                                        
                                        var td6=document.createElement("td");
                                        var textnode6=document.createTextNode(location_type);
                                        td6.appendChild(textnode6);
                                        tr.appendChild(td6);
                                        
                                        var td7=document.createElement("td");
                                        var textnode7=document.createTextNode(lname);
                                        pan2=document.createElement("input");
                                        pan2.type="hidden";
                                        pan2.value=local_body_code;
                                        pan2.readonly="readonly";
                                        td7.appendChild(pan2);
                                        td7.appendChild(textnode7);
                                        tr.appendChild(td7);
                                        
                                        var td8=document.createElement("td");
                                        var textnode8=document.createTextNode(bname);
                                        pan2=document.createElement("input");
                                        pan2.type="hidden";
                                        pan2.value=block_code;
                                        pan2.readonly="readonly";
                                        td8.appendChild(pan2);
                                        td8.appendChild(textnode8);
                                        tr.appendChild(td8);
                                       
                                        var td9=document.createElement("td");
                                        var textnode9=document.createTextNode(pname);
                                        pan2=document.createElement("input");
                                        pan2.type="hidden";
                                        pan2.value=panchayat_code;
                                        pan2.readonly="readonly";
                                        td9.appendChild(pan2);
                                        td9.appendChild(textnode9);
                                        tr.appendChild(td9);
                                         
                                        var td10=document.createElement("td");
                                        var textnode10=document.createTextNode(hname);
                                        pan2=document.createElement("input");
                                        pan2.type="hidden";
                                        pan2.value=habitation_code;
                                        pan2.readonly="readonly";
                                        td10.appendChild(pan2);
                                        td10.appendChild(textnode10);
                                        tr.appendChild(td10);
                                        
                                        var td11=document.createElement("td");
                                        var textnode11=document.createTextNode(scheme_type_name);
                                        pan2=document.createElement("input");
                                        pan2.type="hidden";
                                        pan2.value=scheme_type;
                                        pan2.readonly="readonly";
                                        td11.appendChild(pan2);
                                        td11.appendChild(textnode11);
                                        tr.appendChild(td11);                                     
                                                                              
                                        var td14=document.createElement("td");
                                        var textnode14=document.createTextNode(programme_desc);
                                        pan2=document.createElement("input");
                                        pan2.type="hidden";
                                        pan2.value=programme;
                                        pan2.readonly="readonly";
                                        td14.appendChild(pan2);
                                        td14.appendChild(textnode14);
                                        tr.appendChild(td14);
                                        
                                        var td15=document.createElement("td");
                                        var textnode15=document.createTextNode(sampling_point);
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
                                }
                        }
                        else
                        {
                            alert("Invalid Invoice Number");
                            document.getElementById("ino").value="";
                            document.getElementById("ino").focus();
                        }
                    }
                    else if(command=="validate_record")
                    {
                        var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                        if(flag=="success")
                        {
                            alert("Record validated successfully");
                            document.getElementById("ino").value="";
                            clearAll();
                        }
                        else
                        {
                            alert("Record is not validated");
                        }
                    }
            }
        }
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
        
    winemp= window.open("../../../../../../org/WQS/WQS1/WQTesting/Directory1/jsps/WQS_SampleEntry_popupJSP.jsp?command=SampleValidation","InvoiceNumberSearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}
function doParentEmp(ino)
{
   document.monitoring.ino.value=ino;
   changeInvoice();
}

function loadTestPurpose()
{
        document.getElementById("divpurpose").style.display='block';
}

function changeInvoice()
{
    clearAll();
    req=getTransport();
    var lab=document.getElementById("labcode").value;
    lab=lab.split("--");
    var inum=document.getElementById("ino").value;
    var url="../../../../../../WQS_SampleEntry_EditServ?option=changeInvoice&lab="+lab[0]+"&invoice_number="+inum;
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        process_response(req);
    }
    req.send(null);
}

function LoadRecord(sample_number)
{
    com_id=sample_number;
    document.getElementById("sample").disabled=true;
    if(document.monitoring.test_purpose.length>1)
    {
            for(i=0;i<document.monitoring.test_purpose.length;i++)
            {
                        document.monitoring.test_purpose[i].style.backgroudColor="rgb(214,214,214)";
                        document.monitoring.test_purpose[i].checked=false;
                        document.monitoring.test_purpose[i].disabled=true;
            }
    }

    document.getElementById("divpurpose").style.display='block';
    var ino=document.getElementById("ino").value;
    lab=document.getElementById("labcode").value;
    lab=lab.split("--");
    //document.getElementById("update").disabled=false;
    //document.getElementById("delete").disabled=false;
    //document.getElementById("add").disabled=true;
    
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
                    {
                        document.monitoring.test_purpose[i].checked=true;
                    }
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
    document.getElementById("ltype").value=record6;
    if(record6=="VP")
    {
        document.getElementById("local").style.display='none';
        document.getElementById("village").style.display='block';
    }
    else if(record6=="-")
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
    if(scheme[0]=="")
        document.getElementById("schemetype").selectedIndex="";
    else
        document.getElementById("schemetype").value=record11+"--"+record111;
    if(source[0]=="")
        document.getElementById("sourcetype").selectedIndex="";
    else
        document.getElementById("sourcetype").value=source[0]+"--"+stypedesc;
    if(record12=="")
        document.getElementById("programmecode").selectedIndex="";
    else
        document.getElementById("programmecode").value=record12+"--"+record121;
    if(record13=="")
        document.getElementById("spoint").selectedIndex="";
    else
        document.getElementById("spoint").value=record13;
    document.getElementById("location").value=record14;
   
    var req=getTransport();
    if(record6!="VP" && record6!="")
    {
        var url="../../../../../../WQS_SampleEntry_ValidateServ?option=LoadRecord&type=others&lab="+lab[0]+"&ino="+ino+"&sample="+sample_number+"&distcode="+record5+"&ltype="+record6+"&sourcetype="+source[0];
    }
    else if(record6!="")
    {
        var url="../../../../../../WQS_SampleEntry_ValidateServ?option=LoadRecord&type=VP&lab="+lab[0]+"&ino="+ino+"&sample="+sample_number+"&distcode="+record5+"&blockcode="+record8+"&pancode="+record9+"&sourcetype="+source[0];
    }
    else
    {
        var url="../../../../../../WQS_SampleEntry_ValidateServ?option=LoadRecord&type=empty&lab="+lab[0]+"&ino="+ino+"&sample="+sample_number+"&sourcetype="+source[0];
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
                clearCombo();               
                var type=response.getElementsByTagName("type")[0].firstChild.nodeValue;
                if(type=="others")
                {
                        var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                        if(flag=="success")
                        {
                             var tcount=response.getElementsByTagName("tcount")[0].firstChild.nodeValue;
                             var lbody=document.getElementById("lbody");
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
                    var flag1=response.getElementsByTagName("flag1")[0].firstChild.nodeValue;
                    if(flag1=="success");
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
                    
                    var flag2=response.getElementsByTagName("flag2")[0].firstChild.nodeValue;
                    if(flag2=="success")
                    {
                         var panchayatcount=response.getElementsByTagName("pcount");
                         var pancode=document.getElementById("pancode");
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
                    var flag3=response.getElementsByTagName("flag3")[0].firstChild.nodeValue;
                    if(flag3=="success")
                    {
                         var habitationcount=response.getElementsByTagName("hcount");
                         var habitationcode=document.getElementById("habitationcode");
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
function validate_record()
{
     var ino=document.getElementById("ino").value;
     var lab=document.getElementById("labcode").value;
     lab=lab.split("--");
     req=getTransport();
     var url="../../../../../../WQS_SampleEntry_ValidateServ?option=validate_record&lab="+lab[0]+"&invoice_number="+ino;
     req.open("GET",url,true);
     req.onreadystatechange=function()
     {
            process_response(req);
     }
     req.send(null);
}
