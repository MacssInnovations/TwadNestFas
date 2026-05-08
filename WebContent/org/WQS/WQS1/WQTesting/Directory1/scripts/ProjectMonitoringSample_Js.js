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
    function changeCustomer()
    {
        var cid=document.getElementById("cid").value;
        var option="changeCustomer";
        var url="../../../../../../ProjectMonitoringServlet?option="+option+"&cid="+cid;
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
                process_customer(req);
        }
        req.send(null);
        
    }
    function process_customer(req)
    {
        if(req.readyState==4)
        {
            if(req.status==200)
            {
                var response=req.responseXML.getElementsByTagName("response")[0];
                var ctype=response.getElementsByTagName("ctype")[0].firstChild.nodeValue;
                if(ctype=="Others")
                {
                    alert("Select Twad Customer");
                    document.getElementById("cid").value="";
                    document.getElementById("cid").focus();
                }
                else if(ctype=="NoMatch")
                {
                    alert("Select Correct Customer Id");
                    document.getElementById("cid").value="";
                    document.getElementById("cid").focus();
                }
            }
        }
    }
    
    function load_labcode()
    {
        //alert(document.getElementById("labcode").value);
        document.getElementById("update").disabled=true;
        document.getElementById("delete").disabled=true;
        document.getElementById("add").disabled=false;
        lab=document.getElementById("labcode").value;
        lab=lab.split("--");
        req=getTransport();
        var option="loadlabcode";
       
        var url="../../../../../../ProjectMonitoringServlet?option="+option+"&lab="+lab[0];
        req.open("GET",url,true);
         req.onreadystatechange=function()
            {
                process_labcode(req);
            }
        req.send(null);
    
    }
    
    
    function process_labcode(req)
    {
        if(req.readyState==4)
            {
                if(req.status==200)
                {
                    var response=req.responseXML.getElementsByTagName("response")[0];
                    var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
                    if(command=="loadlabcode")  
                    {
                           //load district 
                           var dist_name=response.getElementsByTagName("district_name")[0].firstChild.nodeValue;                           
                           if(dist_name=='chennai'||dist_name=='CHENNAI'||dist_name=='Chennai')
                           {
                                    var district=response.getElementsByTagName("district");
                                    var distcode=document.getElementById("distcode");
                                    var option1=document.createElement("option");
                                    var text=document.createTextNode("-- Select District--");
                                    option1.setAttribute("value","0");
                                    option1.appendChild(text);
                                    distcode.appendChild(option1);
                                    
                                    for(var i=0;i<district.length;i++)
                                    {
                                        var discode=response.getElementsByTagName("code")[i].firstChild.nodeValue;
                                        var dname=response.getElementsByTagName("dname")[i].firstChild.nodeValue;
                                        var opt=document.createElement("option");
                                        opt.setAttribute("value",discode);
                                        var text=document.createTextNode(discode+"--"+dname);
                                        opt.appendChild(text);
                                        distcode.appendChild(opt);
                                    }
                                     load_sampledata(); 
                           }
                           else
                           {
                                    var distcode=document.getElementById("distcode");
                                    var discode=response.getElementsByTagName("code")[0].firstChild.nodeValue;
                                    var dname=response.getElementsByTagName("dname")[0].firstChild.nodeValue;
                                    var opt=document.createElement("option");
                                    opt.setAttribute("value",discode);
                                    var text=document.createTextNode(discode+"--"+dname);
                                    opt.appendChild(text);
                                    distcode.appendChild(opt);
                                    document.getElementById("distcode").disabled=true;
                                    
                                    var blockid=document.getElementById("blockcode");
                                    var bcount=response.getElementsByTagName("bcount")
                                    for(var i=0;i<bcount.length;i++)
                                    {
                                    var blockcode=response.getElementsByTagName("block_code");
                                    var blockname=response.getElementsByTagName("block_name");
                                    var code=blockcode.item(i).firstChild.nodeValue;
                                    var desc=blockname.item(i).firstChild.nodeValue;                                  
                                    var opt=document.createElement("option");
                                    opt.setAttribute("value",code);
                                    var text=document.createTextNode(code+"--"+desc);
                                    opt.appendChild(text);
                                    blockid.appendChild(opt);
                                    
                                    }
                                    load_sampledata(); 
                           }
                           var stcount=response.getElementsByTagName("stcount");
                           var schemetype=document.getElementById("schemetype");
                                for(var i=0;i<stcount.length;i++)
                                {
                                        var stypecode=response.getElementsByTagName("stype_id");
                                        var stypename=response.getElementsByTagName("stype_name");
                                        var code=stypecode.item(i).firstChild.nodeValue;
                                        var desc=stypename.item(i).firstChild.nodeValue;                                  
                                        
                                        var opt=document.createElement("option");
                                        opt.setAttribute("value",code);
                                        var text=document.createTextNode(code+"--"+desc);
                                        opt.appendChild(text);
                                        schemetype.appendChild(opt);
                                }
                            
                                //load source type
                                var s_count=response.getElementsByTagName("s_count");
                                var sourcetypeid=document.getElementById("sourcetype");
                                
                                for(var i=0;i<s_count.length;i++)
                                {
                                        var sourcetypecode=response.getElementsByTagName("sourcetype_id");
                                        var sourcetype=response.getElementsByTagName("sourcetype");
                                        var code=sourcetypecode.item(i).firstChild.nodeValue;
                                        var desc=sourcetype.item(i).firstChild.nodeValue;                                  
                                        
                                        var opt=document.createElement("option");
                                        opt.setAttribute("value",code);
                                        var text=document.createTextNode(code+"--"+desc);
                                        opt.appendChild(text);
                                        
                                        sourcetypeid.appendChild(opt);
                                }
                                //load progrrame code
                            
                                var pcodecount=response.getElementsByTagName("pcount");
                                var programme=document.getElementById("programmecode");
                                
                                for(var i=0;i<pcodecount.length;i++)
                                {
                           
                                        var programmecode=response.getElementsByTagName("programmecode");
                                        var programmedesc=response.getElementsByTagName("programmedesc");
                                        var code=programmecode.item(i).firstChild.nodeValue;
                                        var desc=programmedesc.item(i).firstChild.nodeValue;                                  
                                   
                                        var opt=document.createElement("option");
                                        opt.setAttribute("value",code);
                                        var text=document.createTextNode(code+"--"+desc);
                                        opt.appendChild(text);
                                        programme.appendChild(opt);
                                }
                    }    
                   
                    
                }
            }
            
            
    
    }
    
    
    //load block
   
    
     function load_block()
    {
        req=getTransport();
        var option="loadblock";
        var dist_code=document.getElementById("distcode").value;
        
        var url="../../../../../../ProjectMonitoringServlet?option="+option+"&dist_code="+dist_code;
        req.open("GET",url,true);
         req.onreadystatechange=function()
            {
                process_block(req);
            }
        req.send(null);
    
    }
    
    
     function process_block(req)
    {
        //var req=get
        
        if(req.readyState==4)
            {
                if(req.status==200)
                {
                    var response=req.responseXML.getElementsByTagName("response")[0];
                    //alert("r  :"+response);
                    var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
                    if(command=="loadblock")  
                    {
                        var blockcount=response.getElementsByTagName("blockcount")[0].firstChild.nodeValue;
                        var blockid=document.getElementById("blockcode");
                            blockid.innerHtml="";
                            var child=blockid.childNodes;
                       
                            for(var c=child.length-1;c>=0;c--)
                            {
                                blockid.removeChild(child[c]);
                                
                            }
                            var opt=document.createElement("option");
                            opt.text="--Select Block--";
                            opt.value="0";
                            try
                            {
                                blockid.add(opt);
                            }
                            catch(errorObject)
                            {
                                blockid.add(opt,null);
                            }
                        if(blockcount>0)
                        {
                            var bcount=response.getElementsByTagName("bcount");
                            
                           
                            for(var i=0;i<bcount.length;i++)
                            {
                            var blockcode=response.getElementsByTagName("block_code");
                            var blockname=response.getElementsByTagName("block_name");
                            var code=blockcode.item(i).firstChild.nodeValue;
                            var desc=blockname.item(i).firstChild.nodeValue;                                  
                            var opt=document.createElement("option");
                            opt.setAttribute("value",code);
                            var text=document.createTextNode(code+"--"+desc);
                            opt.appendChild(text);
                            blockid.appendChild(opt);
                            
                            }
                        }
                     }
                    
                }
            }
    }
    
    
    
    //load panchayat
    
     function load_panchayat()
    {
        req=getTransport();
        var option="loadpanchayat";
        var dist_code=document.getElementById("distcode").value;
        dist_code=dist_code.split("--");
        var block_code=document.getElementById("blockcode").value;
        //block_code=block_code.split("--");
        var url="../../../../../../ProjectMonitoringServlet?option="+option+"&dist_code="+dist_code[0]+"&block_code="+block_code;
        req.open("GET",url,true);
            req.onreadystatechange=function()
            {
                process_panchayat(req);
            }

        req.send(null);
    
    }
    
    function process_panchayat(req)
    {
     if(req.readyState==4)
            {
                if(req.status==200)
                {
                    var response=req.responseXML.getElementsByTagName("response")[0];
                    //alert("r  :"+response);
                    var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
                    if(command=="loadpanchayat")  
                    {
                    //
                     var panchayatcount=response.getElementsByTagName("panchayatcount")[0].firstChild.nodeValue;
                     var panid=document.getElementById("pancode");
                            panid.innerHtml="";
                            var child=panid.childNodes;
                       
                            for(var c=child.length-1;c>=0;c--)
                            {
                                panid.removeChild(child[c]);
                                
                            }
                            var opt=document.createElement("option");
                            opt.text="--Select Panchayat--";
                            opt.value="0";
                            try
                            {
                                panid.add(opt);
                            }
                            catch(errorObject)
                            {
                                panid.add(opt,null);
                            }
                        if(panchayatcount>0)
                        {
                            var pcount=response.getElementsByTagName("pcount");
                            
                           
                            for(var i=0;i<pcount.length;i++)
                            {
                            var panchayatcode=response.getElementsByTagName("panchayat_code");
                            var panchayatname=response.getElementsByTagName("panchayat_name");
                            var code=panchayatcode.item(i).firstChild.nodeValue;
                            var desc=panchayatname.item(i).firstChild.nodeValue;                                  
                            var opt=document.createElement("option");
                            opt.setAttribute("value",code);
                            var text=document.createTextNode(code+"--"+desc);
                            opt.appendChild(text);
                            panid.appendChild(opt);
                            
                            }
                        }
                    
                    //
                    }
                }
            }
    }
    
    
    // load habitation
    
     function load_habitation()
    {
        req=getTransport();
        var option="loadhabitation";
        var dist_code=document.getElementById("distcode").value;
        dist_code=dist_code.split("--");
        var block_code=document.getElementById("blockcode").value;
        var panchayat_code=document.getElementById("pancode").value;
        
        var url="../../../../../../ProjectMonitoringServlet?option="+option+"&dist_code="+dist_code[0]+"&block_code="+block_code+"&panchayat_code="+panchayat_code;
        req.open("GET",url,true);
         req.onreadystatechange=function()
            {
                process_habitation(req);
            }
        req.send(null);
    
    }
    
    function process_habitation(req)
    {
     if(req.readyState==4)
            {
                if(req.status==200)
                {
                    var response=req.responseXML.getElementsByTagName("response")[0];
                    //alert("r  :"+response);
                    var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
                    
                    if(command=="loadhabitation")  
                    {
                    //
                     var habitationcount=response.getElementsByTagName("habitationcount")[0].firstChild.nodeValue;
                     var habitationid=document.getElementById("habitationcode");
                            habitationid.innerHtml="";
                            var child=habitationid.childNodes;
                       
                            for(var c=child.length-1;c>=0;c--)
                            {
                                habitationid.removeChild(child[c]);
                                
                            }
                            var opt=document.createElement("option");
                            opt.text="--Select Habitation--";
                            opt.value="0";
                            try
                            {
                                habitationid.add(opt);
                            }
                            catch(errorObject)
                            {
                                habitationid.add(opt,null);
                                
                            }
                        if(habitationcount>0)
                        {
                            var hcount=response.getElementsByTagName("hcount");
                            
                           
                            for(var i=0;i<hcount.length;i++)
                            {
                            var habitationcode=response.getElementsByTagName("habitation_code");
                            var habitationname=response.getElementsByTagName("habitation_name");
                            var code=habitationcode.item(i).firstChild.nodeValue;
                            var desc=habitationname.item(i).firstChild.nodeValue;                                  
                            var opt=document.createElement("option");
                            opt.setAttribute("value",code);
                            var text=document.createTextNode(code+"--"+desc);
                            opt.appendChild(text);
                            habitationid.appendChild(opt);
                            
                            }
                        }
                    
                    //
                    }  
                }
            }
    }
    
    
    
    function insert()
    {
            req=getTransport();
            var lab=document.getElementById("labcode").value;
            var labcode=lab.split("--");
            var cid=document.getElementById("cid").value;
            if(cid=="")
            {
            alert("Enter Customer Id");
            document.getElementById("cid").focus();
            return false;
            }
            var refno=document.getElementById("refno").value;
            if(refno=="")
            {
            alert("Enter Customer Reference no");
            document.getElementById("refno").focus();
            return false;
            }
            var distcode=document.getElementById("distcode").value;
            distcode=distcode.split("--");
            var blockcode=document.getElementById("blockcode").value;
            var pancode=document.getElementById("pancode").value;
            var habitationcode=document.getElementById("habitationcode").value;
            var schemetype=document.getElementById("schemetype").value;
            var sourcetype=document.getElementById("sourcetype").value;
            var sourcecode=document.getElementById("sourcecode").value;
            var programmecode=document.getElementById("programmecode").value;
            
            var location=document.getElementById("location").value;
            var date=document.getElementById("date").value;
            var option="add";
            var url="../../../../../../ProjectMonitoringServlet?option="+option+"&labcode="+labcode[0]+"&cid="+cid+"&distcode="+distcode[0]+"&blockcode="+blockcode+"&pancode="+pancode+"&habitationcode="+habitationcode+"&schemetype="+schemetype+"&sourcetype="+sourcetype+"&sourcecode="+sourcecode+"&programmecode="+programmecode+"&refno="+refno+"&location="+location+"&date="+date;
                       
            
            req.open("GET",url,true);
            req.onreadystatechange=function()
            {
                process_add(req);
            }
            req.send(null);     
    }
    
    
    function process_add()
    {
    if(req.readyState==4)
            {
                if(req.status==200)
                {
                    var response=req.responseXML.getElementsByTagName("response")[0];
                    //alert("r  :"+response);
                    var command=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                    if(command=="success")
                    alert("Record inserted ");
                    else
                    alert("Insertion failure");
                    document.getElementById("distcode").value="";
                    document.getElementById("blockcode").value="";
                    document.getElementById("pancode").value="";
                    document.getElementById("habitationcode").value="";
                    document.getElementById("schemetype").value="";
                    document.getElementById("sourcetype").value="";
                    document.getElementById("sourcecode").value="";
                    document.getElementById("programmecode").value="";
                    document.getElementById("cid").value="";
                    document.getElementById("refno").value="";
                    document.getElementById("location").value="";
                    document.getElementById("date").value="";
            
                    load_sampledata();
                }
            }
    }
    
    
    
    //update
    
    
    function update_record()
    {
            req=getTransport();
            var lab=document.getElementById("labcode").value;
            var labcode=lab.split("--");   
            var cid=document.getElementById("cid").value;
            if(cid=="")
            {
            alert("Enter Customer Reference no");
            document.getElementById("cid").focus();
            return false;
            }
            var refno=document.getElementById("refno").value;
            if(refno=="")
            {
            alert("Enter Customer Reference no");
            document.getElementById("refno").focus();
            return false;
            }
            var distcode=document.getElementById("distcode").value;
            distcode=distcode.split("--");
            var blockcode=document.getElementById("blockcode").value;
            var pancode=document.getElementById("pancode").value;
            var habitationcode=document.getElementById("habitationcode").value;
            var schemetype=document.getElementById("schemetype").value;
            var sourcetype=document.getElementById("sourcetype").value;
            var sourcecode=document.getElementById("sourcecode").value;
            var programmecode=document.getElementById("programmecode").value;
            
            var location=document.getElementById("location").value;
            var date=document.getElementById("date").value;
            
            var option="update";
            var url="../../../../../../ProjectMonitoringServlet?option="+option+"&labcode="+labcode[0]+"&cid="+cid+"&distcode="+distcode[0]+"&blockcode="+blockcode+"&pancode="+pancode+"&habitationcode="+habitationcode+"&schemetype="+schemetype+"&sourcetype="+sourcetype+"&sourcecode="+sourcecode+"&programmecode="+programmecode+"&refno="+refno+"&location="+location+"&date="+date;
            req.open("GET",url,true);
            req.onreadystatechange=function()
            {
                process_update(req);
            }
            req.send(null);     
    }
    
    
    function process_update(req)
    {
    if(req.readyState==4)
            {
                if(req.status==200)
                {
                    var response=req.responseXML.getElementsByTagName("response")[0];
                    //alert("r  :"+response);
                    var command=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                    if(command=="success")
                    {
                         alert("Record Updated ");
                         document.getElementById("add").disabled=false;
                         document.getElementById("update").disabled=true;
                         document.getElementById("delete").disabled=true;
                     }
                    else
                    alert("Updation failure");
                    //document.getElementById("labcode").value="";
                    document.getElementById("cid").value="";
                    document.getElementById("distcode").value="";
                    document.getElementById("blockcode").value="";
                    document.getElementById("pancode").value="";
                    document.getElementById("habitationcode").value="";
                    document.getElementById("schemetype").value="";
                    document.getElementById("sourcetype").value="";
                    document.getElementById("sourcecode").value="";
                    document.getElementById("programmecode").value="";
                    document.getElementById("refno").value="";
                    document.getElementById("location").value="";
                    document.getElementById("date").value="";
                    load_sampledata();
                    
                }
            }
    }
    
  
    
    // delete record
    
     function delete_record()
    {
            req=getTransport();
            
            var lab=document.getElementById("labcode").value;
            var labcode=lab.split("--");
            var refno=document.getElementById("refno").value;
            if(refno=="")
            {
            alert("Enter Customer Reference no");
            document.getElementById("refno").focus();
            return false;
            }
            var distcode=document.getElementById("distcode").value;
            distcode=distcode.split("--");
            var blockcode=document.getElementById("blockcode").value;
            var pancode=document.getElementById("pancode").value;
            var habitationcode=document.getElementById("habitationcode").value;
            var schemetype=document.getElementById("schemetype").value;
            var sourcetype=document.getElementById("sourcetype").value;
            var sourcecode=document.getElementById("sourcecode").value;
            var programmecode=document.getElementById("programmecode").value;
            
            var location=document.getElementById("location").value;
            
            var option="delete";
            var url="../../../../../../ProjectMonitoringServlet?option="+option+"&labcode="+labcode[0]+"&distcode="+distcode[0]+"&blockcode="+blockcode+"&pancode="+pancode+"&habitationcode="+habitationcode+"&schemetype="+schemetype+"&sourcetype="+sourcetype+"&sourcecode="+sourcecode+"&programmecode="+programmecode+"&refno="+refno+"&location="+location;
                       
            
            req.open("GET",url,true);
            req.onreadystatechange=function()
            {
                process_delete(req);
            }
            req.send(null);     
    }
    
    
    function process_delete(req)
    {
    if(req.readyState==4)
            {
                if(req.status==200)
                {
                    var response=req.responseXML.getElementsByTagName("response")[0];
                    //alert("r  :"+response);
                    var command=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                    if(command=="success")
                    {
                         alert("Record Deleted ");
                         document.getElementById("add").disabled=false;
                         document.getElementById("update").disabled=true;
                         document.getElementById("delete").disabled=true;
                    }
                    else
                    alert("Record not Deleted");
                    document.getElementById("distcode").selectedIndex="0";
                    document.getElementById("blockcode").selectedIndex="0";
                    document.getElementById("pancode").selectedIndex="0";
                    document.getElementById("habitationcode").selectedIndex="0";
                    document.getElementById("schemetype").selectedIndex="0";
                    document.getElementById("sourcetype").selectedIndex="0";
                    document.getElementById("sourcecode").selectedIndex="0";
                    document.getElementById("programmecode").selectedIndex="0";
                    document.getElementById("refno").value="";
                    document.getElementById("cid").value="";
                    document.getElementById("location").value="";
                    document.getElementById("date").value="";
            
                    load_sampledata();
                }
            }
    }
    
    
    
    function load_sampledata()
    {
        req=getTransport();
        var lab=document.getElementById("labcode").value;
        var labcode=lab.split("--");   
        var option="load_pmssample";
        var url="../../../../../../ProjectMonitoringServlet?option="+option+"&lab="+labcode[0];
        req.open("GET",url,true);
            req.onreadystatechange=function()
            {
                process_sampledata(req);
            }
        req.send(null);
    
    }
    
    function process_sampledata(req)
    {
     
     if(req.readyState==4)
            {
                if(req.status==200)
                {
                 var response=req.responseXML.getElementsByTagName("response")[0];
                 var recordcount=response.getElementsByTagName("recordcount")[0].firstChild.nodeValue;
                        if(recordcount>0)
                        {
                            var record=response.getElementsByTagName("record");
                            var tbody=document.getElementById("tbody");
                            var child=tbody.childNodes;
                            for(var c=child.length-1;c>=0;c--)
                            {
                                    tbody.removeChild(child[c]);
                            }
                            var row=0;
                            for(var i=0;i<record.length;i++)
                            {
                               var labcode=response.getElementsByTagName("labcode")[i].firstChild.nodeValue;
                               var labdesc=response.getElementsByTagName("labdesc")[i].firstChild.nodeValue;
                               var cid=response.getElementsByTagName("cid")[i].firstChild.nodeValue;    
                               var refno=response.getElementsByTagName("refno")[i].firstChild.nodeValue;
                               var programmecode=response.getElementsByTagName("programmecode")[i].firstChild.nodeValue;
                               var distcode=response.getElementsByTagName("distcode")[i].firstChild.nodeValue;
                               var blockcode=response.getElementsByTagName("blockcode")[i].firstChild.nodeValue;
                               var pancode=response.getElementsByTagName("pancode")[i].firstChild.nodeValue;
                               var habcode=response.getElementsByTagName("habcode")[i].firstChild.nodeValue;
                               var scmtype=response.getElementsByTagName("scmtype")[i].firstChild.nodeValue;
                               var sourcetype=response.getElementsByTagName("sourcetype")[i].firstChild.nodeValue;
                               var sourcecode=response.getElementsByTagName("sourcecode")[i].firstChild.nodeValue;
                               var location=response.getElementsByTagName("location")[i].firstChild.nodeValue;
                               var date=response.getElementsByTagName("date")[i].firstChild.nodeValue;
                               
                               
                               
                               var tr=document.createElement("tr");
                               var td = document.createElement("td");
                               var anch=document.createElement("A");
                               var text=document.createTextNode("Edit");
                               var url="javascript:loadrecord('"+labcode+"','"+labdesc+"','"+cid+"','"+refno+"','"+date+"','"+programmecode+"','"+distcode+"','"+blockcode+"','"+pancode+"','"+habcode+"','"+scmtype+"','"+sourcetype+"','"+sourcecode+"','"+location+"')";
                               anch.href=url;
                               anch.appendChild(text);
                               td.appendChild(anch);
                               tr.appendChild(td);
                               
                               
                               var td = document.createElement("td");
                               var text=document.createTextNode(labcode);
                               td.appendChild(text);
                               tr.appendChild(td);
                               
                               var td = document.createElement("td");
                               var text=document.createTextNode(cid);
                               td.appendChild(text);
                               tr.appendChild(td);
                               
                               
                               var td = document.createElement("td");
                               var text=document.createTextNode(refno);
                               td.appendChild(text);
                               tr.appendChild(td);
                               
                               var td = document.createElement("td");
                               var text=document.createTextNode(date);
                               td.appendChild(text);
                               tr.appendChild(td);
                               
                               var td = document.createElement("td");
                               var text=document.createTextNode(programmecode);
                               td.appendChild(text);
                               tr.appendChild(td);
                              
                               var td = document.createElement("td");
                               var text=document.createTextNode(distcode);
                               td.appendChild(text);
                               tr.appendChild(td);
                               
                               var td = document.createElement("td");
                               var text=document.createTextNode(blockcode);
                               td.appendChild(text);
                               tr.appendChild(td);
                               
                               var td = document.createElement("td");
                               var text=document.createTextNode(pancode);
                               td.appendChild(text);
                               tr.appendChild(td);
                               
                               var td = document.createElement("td");
                               var text=document.createTextNode(habcode);
                               td.appendChild(text);
                               tr.appendChild(td);
                               
                               var td = document.createElement("td");
                               var text=document.createTextNode(scmtype);
                               td.appendChild(text);
                               tr.appendChild(td);
                               
                               
                               var td = document.createElement("td");
                               var text=document.createTextNode(sourcetype);
                               td.appendChild(text);
                               tr.appendChild(td);
                               
                               var td = document.createElement("td");
                               var text=document.createTextNode(sourcecode);
                               td.appendChild(text);
                               tr.appendChild(td);
                               
                               var td = document.createElement("td");
                               var text=document.createTextNode(location);
                               td.appendChild(text);
                               tr.appendChild(td);
                               
                               
                               tbody.appendChild(tr);                                                     
                                                            
                            }     
                        }
                }
            }
    }
    
    function loadrecord(lcode,labdesc,cid,refno,date,pcode,dcode,bcode,pancode,habcode,scmtype,sourcetype,sourcecode,location)
    {
            
           // alert("Lab code  :"+lcode+"Reference Number  :"+refno+"Date  :"+date+"panchayat  :"+pcode+"habitation   :"+habcode+"scheme type   "+scmtype+"Source Type  :"+sourcetype+"Source Code  :"+sourcecode+"Location  :"+location);
            document.getElementById("add").disabled=true;
            document.getElementById("update").disabled=false;
            document.getElementById("delete").disabled=false;
           
            
            document.getElementById("labcode").value=lcode+"--"+labdesc;
            document.getElementById("cid").value=cid;
            document.getElementById("distcode").value=dcode;
            document.getElementById("date").value=date;
            
            var option="loadpanchayatblock";
            var dist_code=dcode;
            var block_code=bcode;
            var panchayat_code=pancode;
            var stype=sourcetype;
            
            var url="../../../../../../ProjectMonitoringServlet?option="+option+"&dist_code="+dist_code+"&block_code="+block_code+"&panchayat_code="+panchayat_code+"&stype="+stype;
            req.open("GET",url,true);
            req.onreadystatechange=function()
            {
                       
                 if(req.readyState==4)
                {
                    if(req.status==200)
                        { 
                            var response=req.responseXML.getElementsByTagName("response")[0];
                            //alert("r  :"+response);
                                  
                                var blockcount=response.getElementsByTagName("blockcount")[0].firstChild.nodeValue;
                                var blockid=document.getElementById("blockcode");
                                
                                var child=blockid.childNodes;
            
                                 for(var c=child.length-1;c>=0;c--)
                                 {
                                    blockid.removeChild(child[c]);
                                
                                }
                                var opt=document.createElement("option");
                                opt.text="--Select Block--";
                                opt.value="0";
                                try
                                {
                                     blockid.add(opt);
                                }
                                catch(errorObject)
                                {
                                    blockid.add(opt,null);
                                }
                        if(blockcount>0)
                        {
                                var bcount=response.getElementsByTagName("bcount");
                            
                           
                                 for(var i=0;i<bcount.length;i++)
                                 {
                                      var blockcode=response.getElementsByTagName("block_code");
                                      var blockname=response.getElementsByTagName("block_name");
                                      var code=blockcode.item(i).firstChild.nodeValue;
                                      var desc=blockname.item(i).firstChild.nodeValue;                                  
                                      var opt=document.createElement("option");
                                      opt.setAttribute("value",code);
                                      
                                      if(code==bcode)
                                      {
                                      
                                      opt.setAttribute("selected","true");
                                      }
                                                        
                                      
                                      var text=document.createTextNode(code+"--"+desc);
                                      opt.appendChild(text);
                                      blockid.appendChild(opt);
                            
                                }
                                
                                
                        }
                        //load panchayat
                        
                        var panchayatcount=response.getElementsByTagName("panchayatcount")[0].firstChild.nodeValue;
                        var panid=document.getElementById("pancode");
                            panid.innerHtml="";
                            var child=panid.childNodes;
                       
                            for(var c=child.length-1;c>=0;c--)
                            {
                                panid.removeChild(child[c]);
                                
                            }
                            var opt=document.createElement("option");
                            opt.text="--Select Panchayat--";
                            opt.value="0";
                            try
                            {
                                panid.add(opt);
                            }
                            catch(errorObject)
                            {
                                panid.add(opt,null);
                            }
                        if(panchayatcount>0)
                        {
                            var pcount=response.getElementsByTagName("pcount");
                            
                           
                            for(var i=0;i<pcount.length;i++)
                            {
                            var panchayatcode=response.getElementsByTagName("panchayat_code");
                            var panchayatname=response.getElementsByTagName("panchayat_name");
                            var code=panchayatcode.item(i).firstChild.nodeValue;
                            var desc=panchayatname.item(i).firstChild.nodeValue;                                  
                            var opt=document.createElement("option");
                            opt.setAttribute("value",code);
                            if(code==pancode)
                            {
                            opt.setAttribute("selected","true");
                            }
                            
                            var text=document.createTextNode(code+"--"+desc);
                            opt.appendChild(text);
                            panid.appendChild(opt);
                            
                            }
                        }                       
                     //
                     
                     //load habitation
                     var habitationcount=response.getElementsByTagName("habitationcount")[0].firstChild.nodeValue;
                     var habitationid=document.getElementById("habitationcode");
                            habitationid.innerHtml="";
                            var child=habitationid.childNodes;
                       
                            for(var c=child.length-1;c>=0;c--)
                            {
                                habitationid.removeChild(child[c]);
                                
                            }
                            var opt=document.createElement("option");
                            opt.text="--Select Habitation--";
                            opt.value="0";
                            try
                            {
                                habitationid.add(opt);
                            }
                            catch(errorObject)
                            {
                                habitationid.add(opt,null);
                                
                            }
                        if(habitationcount>0)
                        {
                            var hcount=response.getElementsByTagName("hcount");
                            
                           
                            for(var i=0;i<hcount.length;i++)
                            {
                            var habitationcode=response.getElementsByTagName("habitation_code");
                            var habitationname=response.getElementsByTagName("habitation_name");
                            var code=habitationcode.item(i).firstChild.nodeValue;
                            var desc=habitationname.item(i).firstChild.nodeValue;                                  
                            var opt=document.createElement("option");
                            opt.setAttribute("value",code);
                            if(code==habcode)
                            {
                            opt.setAttribute("selected","true");
                            }
                            
                            var text=document.createTextNode(code+"--"+desc);
                            opt.appendChild(text);
                            habitationid.appendChild(opt);
                            
                            }
                        }
                     
                     
                        var codecount=response.getElementsByTagName("codecount")[0].firstChild.nodeValue;
                        var code=document.getElementById("sourcecode");
                            code.innerHtml="";
                            var child=code.childNodes;
                       
                            for(var c=child.length-1;c>=0;c--)
                            {
                                code.removeChild(child[c]);
                                
                            }
                            var opt=document.createElement("option");
                            opt.text="--Select Panchayat--";
                            opt.value="0";
                            try
                            {
                                code.add(opt);
                            }
                            catch(errorObject)
                            {
                                code.add(opt,null);
                            }
                        if(codecount>0)
                        {
                            var pcount=response.getElementsByTagName("scount");
                            
                           
                            for(var i=0;i<pcount.length;i++)
                            {
                            var scode=response.getElementsByTagName("scode");
                            var stype=response.getElementsByTagName("stype");
                            var code1=scode.item(i).firstChild.nodeValue;
                            var desc=stype.item(i).firstChild.nodeValue;                                  
                            var opt=document.createElement("option");
                            opt.setAttribute("value",code1);
                            if(code1==sourcecode)
                            {
                            opt.setAttribute("selected","true");
                            }
                            
                            var text=document.createTextNode(code1+"--"+desc);
                            opt.appendChild(text);
                            code.appendChild(opt);
                            
                            }
                        }                       
                     //
                  //  
                }
             }
            }
            req.send(null);
            
            document.getElementById("schemetype").value=scmtype;
            document.getElementById("sourcetype").value=sourcetype;
            document.getElementById("sourcecode").value=sourcecode;
            document.getElementById("programmecode").value=pcode;
            document.getElementById("refno").value=refno;
            document.getElementById("location").value=location;
            document.getElementById("refno").disabled=true;
            
    }
    
    function clr()
    {
        document.getElementById("distcode").selectedIndex="";
        document.getElementById("blockcode").selectedIndex="";
        document.getElementById("pancode").selectedIndex="";
        document.getElementById("habitationcode").selectedIndex="";
        document.getElementById("schemetype").selectedIndex="";
        document.getElementById("sourcetype").selectedIndex="";
        document.getElementById("sourcecode").selectedIndex="";
        document.getElementById("programmecode").selectedIndex="";
        document.getElementById("cid").value="";
        document.getElementById("refno").value="";
        document.getElementById("location").value="";
        document.getElementById("date").value="";
        document.getElementById("add").disabled=false;
        document.getElementById("update").disabled=true;
        document.getElementById("delete").disabled=true;
    }
    
    function changeType()
    {
        var type1=document.getElementById("sourcetype").value;

        var sourcecode=document.getElementById("sourcecode");
        var child=sourcecode.childNodes;
   
        for(var c=child.length-1;c>=0;c--)
        {
            sourcecode.removeChild(child[c]);
            
        }
                            
        var type=type1.split("--");
        req=getTransport();
        var option="changeType";
        var url="../../../../../../ProjectMonitoringServlet?option="+option+"&Type="+type[0];
        req.open("GET",url,true);
            req.onreadystatechange=function()
            {
                sampledata(req);
            }
        req.send(null);
    }
    
    function sampledata()
    {
        if(req.readyState==4)
            {
                if(req.status==200)
                {
                    var response=req.responseXML.getElementsByTagName("response")[0];
                    
                    var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
                    if(command=="changeType") 
                    {
                         var sourcecodecount=response.getElementsByTagName("sourcecodecount")[0].firstChild.nodeValue;
                                      
                     
                           if(sourcecodecount>0)
                            {
                                var scode_count=response.getElementsByTagName("source_count");
                                
                                var sourcetypeid=document.getElementById("sourcecode");
                                
                                for(var i=0;i<scode_count.length;i++)
                                {
                                var sourcetypecode=response.getElementsByTagName("sourcecode");
                                var sourcetype=response.getElementsByTagName("sourcetype");
                                var code=sourcetypecode.item(i).firstChild.nodeValue;
                                var desc=sourcetype.item(i).firstChild.nodeValue;                                  
                                
                                var opt=document.createElement("option");
                                opt.setAttribute("value",code);
                                var text=document.createTextNode(code+"--"+desc);
                                opt.appendChild(text);
                                
                                sourcetypeid.appendChild(opt);
                                }
                        }      
                    }
                    }
                    }
    }
  