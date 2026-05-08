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
function changeCid()
{
    var lab=document.ResultEntry.labcode.value;
    lab=lab.split("--");
    var cid=document.ResultEntry.ino.value;
    var url="../../../../../../WQS_SampleResultServ?command=changeCid&custid="+cid;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        manipid(req);
    }
    if(window.XMLHttpRequest)
                req.send(null);
    else req.send();
}
function manipid(req)
{
    if(req.readyState==4)
    {
          if(req.status==200)
          {
               var response=req.responseXML.getElementsByTagName("response")[0];
               var cmd=response.getElementsByTagName("command")[0].firstChild.nodeValue;
               var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
               if(flag=='Success')
               {                
                    cust_id=response.getElementsByTagName("cname")[0].firstChild.nodeValue;
                    cust_type=response.getElementsByTagName("ctype")[0].firstChild.nodeValue
                    document.SampleResult.cname.value=cust_id;
                    document.SampleResult.ctype.value=cust_type;
               }
               else
               {
                    alert("select correct customer id");
                    document.SampleResult.cid.value="";
                    document.SampleResult.cname.value="";
                    document.SampleResult.cid.focus;
                }
                var ctype=document.getElementById("ctype").value;
                if(ctype=="Twad")
                {
                    document.getElementById("dname").disabled=true;
                    document.getElementById("pname").disabled=true;
                    document.getElementById("hname").disabled=true;
                    document.getElementById("srtype").disabled=true;

                }
                else
                {
                    document.getElementById("dname").disabled=false;
                    document.getElementById("pname").disabled=false;
                    document.getElementById("hname").disabled=false;
                    document.getElementById("srtype").disabled=false;
                    document.getElementById("dcode").value="";
                    document.getElementById("bcode").value="";
                    document.getElementById("pancode").value="";
                    document.getElementById("hcode").value="";
                    document.getElementById("stype").value="";
                    document.getElementById("scode").value="";
                    document.getElementById("pcode").value="";
                    document.getElementById("pc").value="";
                    document.getElementById("dc").value="";
                    document.getElementById("bc").value="";
                    document.getElementById("pac").value="";
                    document.getElementById("hc").value="";
                    document.getElementById("sti").value="";
                    document.getElementById("wst").value="";
                    document.getElementById("sco").value="";
                }
          }
    }
}

function noEnter(e)
{
    isIE=document.all?1:0;
    keyEntry=!isIE?e.which:event.keyCode;
    if(keyEntry=='38')
    {
        return false;
    }
    return true;
}
function checklength(evt,item)
{
    var maxqty=document.SampleResult.reason.value.length;
    var text =700;
    var result = true;
    if(maxqty >= text)
    {
        result = false;	
    }  
    return result;
}
function close_win()
{
    window.close();
}

function checkString(val)
{
   var iChars = "0123456789.";
   if(val=="None" ||val=="none"||val=="Nil"||val=="nil")
    {
       var first=val.substring(0,1).toUpperCase();
       var second=val.substring(1,val.length);
       var value=first+second;
       return value;
    }
    else
    {
       for (var i=0; i<val.length; i++)
       {
           if (iChars.indexOf(val.charAt(i))== -1)
            {
                alert("Number/Nil/None only allowed.....");
                return "";
            }
        }
        return val;
    }
}

function callServer(command,param)
{
        var lcode=document.getElementById("lab").value;
        var LabCode=lcode.split("--");
        var cust_id=document.getElementById("cid").value;   
        var ctype=document.getElementById("ctype").value;
        var rno=document.getElementById("rno").value;
        var sno=document.getElementById("sno").value;
        
        var sdate=document.getElementById("sdate").value;
        var rdate=document.getElementById("rdate").value;
        var dcode=document.getElementById("dc").value;
        var bcode=document.getElementById("bc").value;
        var pancode=document.getElementById("pac").value;
        
        if(dcode=="");
            dcode=="-";
        if(bcode=="")
            bcode="-";
        if(pancode=="")
            pancode="-";
            
        var hcode=document.getElementById("hc").value;
        var pcode=document.getElementById("pc").value;
        var stype=document.getElementById("sti").value;
        var srtype=document.getElementById("wst").value;
        var scode=document.getElementById("sco").value;
        if(hcode=="")
            hcode="-";
        if(pcode=="")
            pcode="-";
        if(stype=="")
            stype="-";
        if(srtype=="")
            srtype="-";
        if(scode=="")
            scode="-";
        var dname=document.getElementById("dname").value;
        var pname=document.getElementById("pname").value;
        var hname=document.getElementById("hname").value;
        var location=document.getElementById("location").value;
        var appearance=document.getElementById("appear").value;
        var appear=appearance.replace("&","*");
      
        if(dname=="")
            dname="-";
        if(pname=="")
            pname="-";
        if(hname=="")
            hname="-";
        if(location=="")
            location="-";
        if(appear=="")
            appear="-";
       
        var color=document.getElementById("color").value;
        var odour=document.getElementById("odour").value;
        var turb=document.getElementById("turb").value;
        var solid=document.getElementById("solid").value;
        var tss=document.getElementById("tss").value;
        
        if(color=="")
            color="-";
        if(odour=="")
            odour="-";
        if(turb=="")
            turb="-";
        if(solid=="")
            solid="-";
        if(tss=="")
            tss="-";
        var tds=document.getElementById("tds").value;
        var ec=document.getElementById("ec").value;
        var ph=document.getElementById("ph").value;
        var acidity=document.getElementById("acidity").value;
        var phalk=document.getElementById("phalk").value;
        if(tds=="")
            tds="-";       
        if(ec=="")
            ec="-";
        if(ph=="")
            ph="-";
        if(acidity=="")
            acidity="-";
        if(phalk=="")
            phalk="-";
       
        var talk=document.getElementById("talk").value;  
        var th=document.getElementById("th").value;
        var ca=document.getElementById("ca").value;
        var mg=document.getElementById("mg").value;
        var na=document.getElementById("na").value;
       
        if(talk=="")
            talk="-";
        if(th=="")
            th="-";
        if(ca=="")
            ca="-";
        if(mg=="")
            mg="-";
        if(na=="")
            na="-";
        var potassium=document.getElementById("potassium").value;   
        var fe=document.getElementById("fe").value;
        var mn=document.getElementById("mn").value;
        var nh3=document.getElementById("nh3").value;
        var no2=document.getElementById("no2").value;
       
        if(potassium=="")
            potassium="-";
        if(fe=="")
            fe="-";
        if(mn=="")
            mn="-";
        if(nh3=="")
            nh3="-";
        if(no2=="")
            no2="-";
       
        var no3=document.getElementById("no3").value;   
        var cl=document.getElementById("cl").value;
        var fluoride=document.getElementById("fluoride").value;
        var so4=document.getElementById("so4").value;
        var po4=document.getElementById("po4").value;
        if(no3=="")
            no3="-";
        if(cl=="")
            cl="-";
        if(fluoride=="")
            fluoride="-";
        if(so4=="")
            so4="-";
        if(po4=="")
            po4="-";
       
        var tidys=document.getElementById("tidys").value; 
        var sio2=document.getElementById("sio2").value;
        var tkn=document.getElementById("tkn").value;
        var dl=document.SampleResult.dl.value;
        var bod=document.getElementById("bod").value;
       
        if(tidys=="")
            tidys="-";
        if(sio2=="")
            sio2="-";
        if(tkn=="")
            tkn="-";
        if(dl=="")
            dl="-";
        if(bod=="")
            bod="-";
       
        var cod=document.getElementById("cod").value;
        var oil=document.getElementById("oil").value;
        var residual=document.getElementById("residual").value;
        var cyanide=document.getElementById("cyanide").value;
        var ag=document.getElementById("ag").value;
       
        if(cod=="")
            cod="-";
         if(oil=="")
            oil="-";
        if(residual=="")
            residual="-";
        if(cyanide=="")
            cyanide="-";
        if(ag=="")
            ag="-";
        var cd=document.getElementById("cd").value;
        var cu=document.getElementById("cu").value;
        var pb=document.getElementById("pb").value;
        var cr=document.getElementById("cr").value;
        var zn=document.getElementById("zn").value;
        if(cd=="")
            cd="-";
        if(cu=="")
            cu="-";
        if(pb=="")
            pb="-";
        if(cr=="")
            cr="-";
        if(zn=="")
            zn="-";
        var aluminium=document.getElementById("aluminium").value;
        var spc=document.getElementById("spc").value;
        var tc=document.getElementById("tc").value;
        var fc=document.getElementById("fc").value;
        var fsc=document.getElementById("fsc").value;
        if(aluminium=="")
            aluminium="-";
        if(spc=="")
            spc="-";
        if(tc=="")
            tc="-";
        if(fc=="")
            fcf="-";
        if(fsc=="")
            fsc="-";    
       
        var cnc=document.getElementById("cnc").value;
        var pnp=document.getElementById("pnp").value;
        var reason=document.getElementById("reason").value;
        if(cnc=="")
            cnc="-";
        if(pnp=="")
            pnp="-";
        if(reason=="")
            reason="-";
      
        var val1=LabCode[0]+"//"+cust_id+"//"+rno+"//"+sno+"//"+sdate+"//"+rdate+"//"+ctype+"//"+dcode+"//"+bcode+"//"+pancode;
        var val2=hcode+"//"+pcode+"//"+stype+"//"+srtype+"//"+scode+"//"+dname+"//"+pname+"//"+hname+"//"+location+"//"+appear;
        var val3=color+"//"+odour+"//"+turb+"//"+solid+"//"+tss+"//"+tds+"//"+ec+"//"+ph+"//"+acidity+"//"+phalk;
        var val4=talk+"//"+th+"//"+ca+"//"+mg+"//"+na+"//"+potassium+"//"+fe+"//"+mn+"//"+nh3+"//"+no2;
        var val5=no3+"//"+cl+"//"+fluoride+"//"+so4+"//"+po4+"//"+tidys+"//"+sio2+"//"+tkn+"//"+dl+"//"+bod;
        var val6=cod+"//"+oil+"//"+residual+"//"+cyanide+"//"+ag+"//"+cd+"//"+cu+"//"+pb+"//"+cr+"//"+zn;
        var val7=aluminium+"//"+spc+"//"+tc+"//"+fc+"//"+fsc+"//"+cnc+"//"+pnp+"//"+reason;
        if(command=="Add")
        {
            var url="../../../../../../WQS_SampleResultServ?command=Add&val1="+val1+"&val2="+val2+"&val3="+val3+"&val4="+val4+"&val5="+val5+"&val6="+val6+"&val7="+val7;
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
        else if(command=="Update")
        {
            var url="../../../../../../WQS_SampleResultServ?command=Update&val1="+val1+"&val2="+val2+"&val3="+val3+"&val4="+val4+"&val5="+val5+"&val6="+val6+"&val7="+val7;
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
        else if(command=="Del")
        {
            var url="../../../../../../WQS_SampleResultServ?command=Del&LabCode="+LabCode[0]+"&cid="+cust_id+"&rno="+rno+"&sno="+sno;
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
}
function load()
{
document.getElementById("lab").focus();
}
function close_win()
{
    window.close();
}

function changeCustomer()
{
    var ctype=document.getElementById("ctype").value;
    if(ctype=="Twad")
    {
        document.getElementById("rno").disabled=false;
        document.getElementById("location").disabled=true;
        document.getElementById("dname").disabled=true;
        document.getElementById("pname").disabled=true;
        document.getElementById("hname").disabled=true;
        document.getElementById("srtype").disabled=true;
    }
    else
    {
        document.getElementById("rno").disabled=true;
        document.getElementById("location").disabled=false;
        document.getElementById("dname").disabled=false;
        document.getElementById("pname").disabled=false;
        document.getElementById("hname").disabled=false;
        document.getElementById("srtype").disabled=false;
    
        document.getElementById("rno").value="";
        document.getElementById("location").value="";
        document.getElementById("dcode").value="";
        document.getElementById("bcode").value="";
        document.getElementById("pancode").value="";
        document.getElementById("hcode").value="";
        document.getElementById("stype").value="";
        document.getElementById("srtype").value="";
        document.getElementById("scode").value="";
        document.getElementById("pcode").value="";
        document.getElementById("pc").value="";
        document.getElementById("dc").value="";
        document.getElementById("bc").value="";
        document.getElementById("pac").value="";
        document.getElementById("hc").value="";
        document.getElementById("sti").value="";
        document.getElementById("wst").value="";
        document.getElementById("sco").value="";
    }
}

function changeReference()
{
    rno=document.SampleResult.rno.value;
    cid=document.SampleResult.cid.value;
    ctype=document.SampleResult.ctype.value;
    var lcode=document.getElementById("lab").value;
    var LabCode=lcode.split("--");
    if(ctype=="Twad")
    {
        var url="../../../../../../WQS_SampleResultServ?command=changeReference&rno="+rno+"&lcode="+LabCode[0]+"&cust_id="+cid;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            retriveValue(req);
        }
        req.send(null);
    }
}

function retriveValue(req)
{
        if(req.readyState==4)
        {
          if(req.status==200)
          {
               var response=req.responseXML.getElementsByTagName("response")[0];
               var cmd=response.getElementsByTagName("command")[0].firstChild.nodeValue;
               if(cmd=="changeReference")
               {
                    ReferenceVal(response);
               }
          }
        }
}
function ReferenceVal(response)
{
     var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=='Success')
     {
               var location=response.getElementsByTagName("location")[0].firstChild.nodeValue;
               var entrydate=response.getElementsByTagName("entrydate")[0].firstChild.nodeValue;
               var pdesc=response.getElementsByTagName("pdesc")[0].firstChild.nodeValue;
               var dname=response.getElementsByTagName("dname")[0].firstChild.nodeValue;
               var bname=response.getElementsByTagName("bname")[0].firstChild.nodeValue;
               var pname=response.getElementsByTagName("pname")[0].firstChild.nodeValue;
               var hname=response.getElementsByTagName("hname")[0].firstChild.nodeValue;
               var sname=response.getElementsByTagName("sname")[0].firstChild.nodeValue;
               var stype=response.getElementsByTagName("stype")[0].firstChild.nodeValue;
               var srctype=response.getElementsByTagName("srctype")[0].firstChild.nodeValue;
               var pc=response.getElementsByTagName("pc")[0].firstChild.nodeValue;
               var dc=response.getElementsByTagName("dc")[0].firstChild.nodeValue;
               var bc=response.getElementsByTagName("bc")[0].firstChild.nodeValue;
               var pac=response.getElementsByTagName("pac")[0].firstChild.nodeValue;
               var hc=response.getElementsByTagName("hc")[0].firstChild.nodeValue;
               var sti=response.getElementsByTagName("sti")[0].firstChild.nodeValue;
               var wst=response.getElementsByTagName("wst")[0].firstChild.nodeValue;
               var sco=response.getElementsByTagName("sco")[0].firstChild.nodeValue;
                    document.getElementById("sdate").value=entrydate;
                    document.getElementById("pcode").value=pdesc;
                    document.getElementById("dcode").value=dname;
                    document.getElementById("bcode").value=bname;
                    document.getElementById("pancode").value=pname;
                    document.getElementById("hcode").value=hname;
                    document.getElementById("stype").value=sname;
                    document.getElementById("srtype").value=wst;
                    document.getElementById("scode").value=srctype;
                    document.getElementById("location").value=location;
               
               document.getElementById("pc").value=pc;
               document.getElementById("dc").value=dc;
               document.getElementById("bc").value=bc;
               document.getElementById("pac").value=pac;
               document.getElementById("hc").value=hc;
               document.getElementById("sti").value=sti;
               document.getElementById("wst").value=wst;
               document.getElementById("sco").value=sco;
               
     }
     else
     {
        alert("Enter valid Reference Number");
        document.getElementById("rno").value="";
        document.getElementById("rno").focus();
     }
}

function manipulate(req)
{
        if(req.readyState==4)
        {
          if(req.status==200)
          {
               var response=req.responseXML.getElementsByTagName("response")[0];
               //alert(response);
               var cmd=response.getElementsByTagName("command")[0].firstChild.nodeValue;
               var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
               if(flag=="Success")
               {
                 if(cmd=="Add")
                    {
                            alert("record added");
                    }
                 else if(cmd=="Update")
                    {
                        alert("record  Updated");
                    }
                else if(cmd=="Del")
                    {
                        alert("record deleted")
                    }
               }
               else
               {
               if(cmd=="Add")
                    {
                        alert("failed to add values");
                    }
                 else if(cmd=="Update")
                    {
                        alert("failed to update values");
                    }
                else if(cmd=="Del")
                    {
                        alert("failed to delete values");
                    }
                }
                clearAll();
               }
          }
        }
        
function changeSample()
{
     //clearAll1();
     var lcode=document.getElementById("lab").value;
     var LabCode=lcode.split("--");
     var cid=document.getElementById("cid").value;
     var rno=document.getElementById("rno").value;
     var sno=document.getElementById("sno").value;
         var url="../../../../../../WQS_SampleResultServ?command=changeSample&LabCode="+LabCode[0]+"&cid="+cid+"&rno="+rno+"&sno="+sno;
            var req=getTransport();
            req.open("GET",url,true);
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
               var cmd=response.getElementsByTagName("command")[0].firstChild.nodeValue;
               var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
               if(flag=="Success")
               {
                        var SAMPLE_COLLECTION_DATE=response.getElementsByTagName("SAMPLE_COLLECTION_DATE")[0].firstChild.nodeValue;
                        var SAMPLE_RECEIPT_DATE=response.getElementsByTagName("SAMPLE_RECEIPT_DATE")[0].firstChild.nodeValue;
                        document.getElementById("sdate").value=SAMPLE_COLLECTION_DATE;
                        document.getElementById("rdate").value=SAMPLE_RECEIPT_DATE;
                        var pc=response.getElementsByTagName("pc")[0].firstChild.nodeValue;
                        if(pc=="true")
                        {
                            var PROGRAMME_CODE=response.getElementsByTagName("PROGRAMME_CODE")[0].firstChild.nodeValue;
                            var RESURVEY_DIST_CODE=response.getElementsByTagName("RESURVEY_DIST_CODE")[0].firstChild.nodeValue;
                            var RESURVEY_BLK_CODE=response.getElementsByTagName("RESURVEY_BLK_CODE")[0].firstChild.nodeValue;
                            var RESURVEY_PAN_CODE=response.getElementsByTagName("RESURVEY_PAN_CODE")[0].firstChild.nodeValue;
                            var RESURVEY_HAB_CODE=response.getElementsByTagName("RESURVEY_HAB_CODE")[0].firstChild.nodeValue;
                            var SCM_TYPE=response.getElementsByTagName("SCM_TYPE")[0].firstChild.nodeValue;    
                            var SOURCE_CODE=response.getElementsByTagName("SOURCE_CODE")[0].firstChild.nodeValue;                   
                            
                          
                            if(PROGRAMME_CODE=="null"||PROGRAMME_CODE=="-"||PROGRAMME_CODE=="")
                                PROGRAMME_CODE="";
                            if(RESURVEY_DIST_CODE=="null"||RESURVEY_DIST_CODE=="-"||RESURVEY_DIST_CODE=="")
                                RESURVEY_DIST_CODE="";
                            if(RESURVEY_BLK_CODE=="null"||RESURVEY_BLK_CODE=="-"||RESURVEY_BLK_CODE=="")
                                RESURVEY_BLK_CODE="";
                            if(RESURVEY_PAN_CODE=="null"||RESURVEY_PAN_CODE=="-"||RESURVEY_PAN_CODE=="")
                                RESURVEY_PAN_CODE="";
                            if(RESURVEY_HAB_CODE=="null"||RESURVEY_HAB_CODE=="-"||RESURVEY_HAB_CODE=="")
                                RESURVEY_HAB_CODE="";
                            if(SCM_TYPE=="null"||SCM_TYPE=="-"||SCM_TYPE=="")
                                SCM_TYPE="";
                            
                            if(SOURCE_CODE=="null"||SOURCE_CODE=="-"||SOURCE_CODE=="")
                                SOURCE_CODE="";
                            document.getElementById("pc").value=PROGRAMME_CODE;
                            document.getElementById("dc").value=RESURVEY_DIST_CODE;
                            document.getElementById("bc").value=RESURVEY_BLK_CODE;
                            document.getElementById("pac").value=RESURVEY_PAN_CODE;
                            document.getElementById("hc").value=RESURVEY_HAB_CODE;
                            document.getElementById("sti").value=SCM_TYPE;
                            
                            document.getElementById("sco").value=SOURCE_CODE;
                            //----------------------------                    
                            var PROGRAMME_DESC=response.getElementsByTagName("PROGRAMME_DESC")[0].firstChild.nodeValue;
                            var DISTRICT_NAME=response.getElementsByTagName("DISTRICT_NAME")[0].firstChild.nodeValue;
                            var BLOCK_NAME=response.getElementsByTagName("BLOCK_NAME")[0].firstChild.nodeValue;
                            var PANCHAYAT_NAME=response.getElementsByTagName("PANCHAYAT_NAME")[0].firstChild.nodeValue;
                            var HABITATION_NAME=response.getElementsByTagName("HABITATION_NAME")[0].firstChild.nodeValue;
                            var SCHEME_TYPE_NAME=response.getElementsByTagName("SCHEME_TYPE_NAME")[0].firstChild.nodeValue;
                           
                            var SOURCE_TYPE1=response.getElementsByTagName("SOURCE_TYPE1")[0].firstChild.nodeValue;                   
                            if(PROGRAMME_DESC=="null"||PROGRAMME_DESC=="")
                                PROGRAMME_DESC="";
                            if(DISTRICT_NAME=="null"||DISTRICT_NAME=="")
                                DISTRICT_NAME="";
                            if(BLOCK_NAME=="null"||BLOCK_NAME=="")
                                BLOCK_NAME="";
                            if(PANCHAYAT_NAME=="null"||PANCHAYAT_NAME=="")
                                PANCHAYAT_NAME="";
                            if(HABITATION_NAME=="null"||HABITATION_NAME=="")
                                HABITATION_NAME="";
                            if(SCHEME_TYPE_NAME=="null"||SCHEME_TYPE_NAME=="")
                                SCHEME_TYPE_NAME="";
                            if(SOURCE_TYPE1=="null"||SOURCE_TYPE1=="")
                                SOURCE_TYPE1="";
                                
                            document.getElementById("pcode").value=PROGRAMME_DESC;
                            document.getElementById("dcode").value=DISTRICT_NAME;
                            document.getElementById("bcode").value=BLOCK_NAME;
                            document.getElementById("pancode").value=PANCHAYAT_NAME;
                            document.getElementById("hcode").value=HABITATION_NAME;
                            document.getElementById("stype").value=SCHEME_TYPE_NAME;
                            document.getElementById("scode").value=SOURCE_TYPE1;
                   }
                   else
                   {
                        document.getElementById("pcode").value="";
                        document.getElementById("dcode").value="";
                        document.getElementById("bcode").value="";
                        document.getElementById("pancode").value="";
                        document.getElementById("hcode").value="";
                        document.getElementById("stype").value="";
                        document.getElementById("scode").value="";
                        
                        document.getElementById("pc").value="";
                        document.getElementById("dc").value="";
                        document.getElementById("bc").value="";
                        document.getElementById("pac").value="";
                        document.getElementById("hc").value="";
                        document.getElementById("sti").value="";
                        document.getElementById("sco").value="";
                   }
                    var SOURCE_TYPE=response.getElementsByTagName("SOURCE_TYPE")[0].firstChild.nodeValue;
                    if(SOURCE_TYPE=="null"||SOURCE_TYPE=="-"||SOURCE_TYPE=="")
                                SOURCE_TYPE="";
                    document.getElementById("wst").value=SOURCE_TYPE;
                    
                 /*    var WATER_SOURCE_TYPE=response.getElementsByTagName("WATER_SOURCE_TYPE")[0].firstChild.nodeValue; 
                     if(WATER_SOURCE_TYPE=="null"||WATER_SOURCE_TYPE=="")
                                WATER_SOURCE_TYPE="";
                     document.getElementById("srtype").value=WATER_SOURCE_TYPE;
                    
                    var LOCATION=response.getElementsByTagName("LOCATION")[0].firstChild.nodeValue;
                    if(LOCATION=="null"||LOCATION=="")
                        LOCATION="";
                    document.getElementById("location").value=LOCATION;*/
                    
                    var SOURCE_DIST_NAME=response.getElementsByTagName("SOURCE_DIST_NAME")[0].firstChild.nodeValue;
                    var SOURCE_PAN_NAME=response.getElementsByTagName("SOURCE_PAN_NAME")[0].firstChild.nodeValue;
                    var SOURCE_HAB_NAME=response.getElementsByTagName("SOURCE_HAB_NAME")[0].firstChild.nodeValue;
                    
                    if(SOURCE_DIST_NAME=="null"||SOURCE_DIST_NAME=="")
                        SOURCE_DIST_NAME="";
                    if(SOURCE_PAN_NAME=="null"||SOURCE_PAN_NAME=="")
                        SOURCE_PAN_NAME="";
                    if(SOURCE_HAB_NAME=="null"||SOURCE_HAB_NAME=="")
                        SOURCE_HAB_NAME="";
                    document.getElementById("dname").value=SOURCE_DIST_NAME;
                    document.getElementById("pname").value=SOURCE_PAN_NAME;
                    document.getElementById("hname").value=SOURCE_HAB_NAME;
                    
                    var APPEAR=response.getElementsByTagName("APPEAR")[0].firstChild.nodeValue;
                    APPEAR=APPEAR.replace("*","&");
                    var COLOR=response.getElementsByTagName("COLOR")[0].firstChild.nodeValue;
                    var ODOUR=response.getElementsByTagName("ODOUR")[0].firstChild.nodeValue;
                    var TURB=response.getElementsByTagName("TURB")[0].firstChild.nodeValue;
                    var TOTAL_SOLIDS=response.getElementsByTagName("TOTAL_SOLIDS")[0].firstChild.nodeValue;
                 
                    var TOTAL_SUSPENDED_SOLIDS=response.getElementsByTagName("TOTAL_SUSPENDED_SOLIDS")[0].firstChild.nodeValue;
                    var TOTAL_DISSOLVED_SOLIDS=response.getElementsByTagName("TOTAL_DISSOLVED_SOLIDS")[0].firstChild.nodeValue;
                    var ELECTRICAL_CONDUCTIVITY=response.getElementsByTagName("ELECTRICAL_CONDUCTIVITY")[0].firstChild.nodeValue;
                    
                    if(APPEAR=="null"||APPEAR=="")
                        APPEAR="";
                    if(COLOR=="null"||COLOR=="")
                        COLOR="";
                    if(ODOUR=="null"||ODOUR=="")
                        ODOUR="";
                    if(TURB=="null"||TURB=="")
                        TURB="";
                    if(TOTAL_SOLIDS=="null"||TOTAL_SOLIDS=="")
                        TOTAL_SOLIDS="";
                    if(TOTAL_SUSPENDED_SOLIDS=="null"||TOTAL_SUSPENDED_SOLIDS=="")
                        TOTAL_SUSPENDED_SOLIDS="";
                    if(TOTAL_DISSOLVED_SOLIDS=="null"||TOTAL_DISSOLVED_SOLIDS=="")
                        TOTAL_DISSOLVED_SOLIDS="";
                    if(ELECTRICAL_CONDUCTIVITY=="null"||ELECTRICAL_CONDUCTIVITY=="")
                        ELECTRICAL_CONDUCTIVITY="";
                    
                    document.getElementById("appear").value=APPEAR;
                    document.getElementById("color").value=COLOR;
                    document.getElementById("odour").value=ODOUR;
                    document.getElementById("turb").value=TURB;
                    document.getElementById("solid").value=TOTAL_SOLIDS;
                 
                    document.getElementById("tss").value=TOTAL_SUSPENDED_SOLIDS;
                    document.getElementById("tds").value=TOTAL_DISSOLVED_SOLIDS;
                    document.getElementById("ec").value=ELECTRICAL_CONDUCTIVITY;
                    
                    var PH=response.getElementsByTagName("PH")[0].firstChild.nodeValue;
                    var ACIDITY=response.getElementsByTagName("ACIDITY")[0].firstChild.nodeValue;
                    var PHALK=response.getElementsByTagName("PHALK")[0].firstChild.nodeValue;
                    var TALK=response.getElementsByTagName("TALK")[0].firstChild.nodeValue;
                    var TOTAL_HARDNESS=response.getElementsByTagName("TOTAL_HARDNESS")[0].firstChild.nodeValue;
                    var CALCIUM=response.getElementsByTagName("CALCIUM")[0].firstChild.nodeValue;
                    var MAGNESIUM=response.getElementsByTagName("MAGNESIUM")[0].firstChild.nodeValue;
                    var SODIUM=response.getElementsByTagName("SODIUM")[0].firstChild.nodeValue;
                    var POTASSIUM=response.getElementsByTagName("POTASSIUM")[0].firstChild.nodeValue;
                    var IRON=response.getElementsByTagName("IRON")[0].firstChild.nodeValue;
                    var MANGANESE=response.getElementsByTagName("MANGANESE")[0].firstChild.nodeValue;
                    
                    if(PH=="null"||PH=="")
                        PH="";            
                    if(ACIDITY=="null"||ACIDITY=="")
                        ACIDITY="";
                    if(PHALK=="null"||PHALK=="")
                        PHALK="";
                    if(TALK=="null"||TALK=="")
                        TALK="";
                    if(TOTAL_HARDNESS=="null"||TOTAL_HARDNESS=="")
                        TOTAL_HARDNESS="";
                    if(CALCIUM=="NULL"||CALCIUM=="")
                        CALCIUM="";
                    if(MAGNESIUM=="null"||MAGNESIUM=="")
                        MAGNESIUM="";
                    if(SODIUM=="null"||SODIUM=="")
                        SODIUM="";
                    if(POTASSIUM=="null"||POTASSIUM=="")
                        POTASSIUM="";
                    if(IRON=="null"||IRON=="")
                        IRON="";
                    if(MANGANESE=="null"||MANGANESE=="")
                        MANGANESE="";
                    document.getElementById("ph").value=PH;                  
                    document.getElementById("acidity").value=ACIDITY;
                    document.getElementById("phalk").value=PHALK;
                    document.getElementById("talk").value=TALK;
                    document.getElementById("th").value=TOTAL_HARDNESS;
                    document.getElementById("ca").value=CALCIUM;
                    document.getElementById("mg").value=MAGNESIUM;
                    document.getElementById("na").value=SODIUM;
                    document.getElementById("potassium").value=POTASSIUM;
                    document.getElementById("fe").value=IRON;
                    document.getElementById("mn").value=MANGANESE;
                    
                    var AMMONIA=response.getElementsByTagName("AMMONIA")[0].firstChild.nodeValue;
                    var NITRITE=response.getElementsByTagName("NITRITE")[0].firstChild.nodeValue;
                    var NITRATE=response.getElementsByTagName("NITRATE")[0].firstChild.nodeValue;
                    var CHLORIDE=response.getElementsByTagName("CHLORIDE")[0].firstChild.nodeValue;
                    var FLUORIDE=response.getElementsByTagName("FLUORIDE")[0].firstChild.nodeValue;
                    var SULPHATE=response.getElementsByTagName("SULPHATE")[0].firstChild.nodeValue;
                    var PHOSPHATE=response.getElementsByTagName("PHOSPHATE")[0].firstChild.nodeValue;
                    var TIDYS=response.getElementsByTagName("TIDYS")[0].firstChild.nodeValue;
                    var SILICA=response.getElementsByTagName("SILICA")[0].firstChild.nodeValue;
                    var TOTAL_KJELDHAL_NITROGEN=response.getElementsByTagName("TOTAL_KJELDHAL_NITROGEN")[0].firstChild.nodeValue;
                    
                    if(AMMONIA=="null"||AMMONIA=="")
                        AMMONIA="";
                    if(NITRITE=="null"||NITRITE=="")
                        NITRITE="";
                    if(NITRATE=="null"||NITRATE=="")
                        NITRATE="";
                    if(CHLORIDE=="null"||CHLORIDE=="")
                        CHLORIDE="";
                    if(FLUORIDE=="null"||FLUORIDE=="")
                        FLUORIDE="";
                    if(SULPHATE=="null"||SULPHATE=="")
                        SULPHATE="";
                    if(PHOSPHATE=="null"||PHOSPHATE=="")
                        PHOSPHATE="";
                    if(TIDYS=="null"||TIDYS=="")
                        TIDYS="";
                    if(SILICA=="null"||SILICA=="")
                        SILICA="";
                    if(TOTAL_KJELDHAL_NITROGEN=="null"||TOTAL_KJELDHAL_NITROGEN=="")
                        TOTAL_KJELDHAL_NITROGEN="";
                   
                    document.getElementById("nh3").value=AMMONIA;
                    document.getElementById("no2").value=NITRITE;
                    document.getElementById("no3").value=NITRATE;
                    document.getElementById("cl").value=CHLORIDE;
                    document.getElementById("fluoride").value=FLUORIDE;
                    document.getElementById("so4").value=SULPHATE;
                    document.getElementById("po4").value=PHOSPHATE;
                    document.getElementById("tidys").value=TIDYS;
                    document.getElementById("sio2").value=SILICA;
                    document.getElementById("tkn").value=TOTAL_KJELDHAL_NITROGEN;
                    
                    var DO=response.getElementsByTagName("DO")[0].firstChild.nodeValue;
                    var BOD=response.getElementsByTagName("BOD")[0].firstChild.nodeValue;
                    var COD=response.getElementsByTagName("COD")[0].firstChild.nodeValue;
                    var OIL_GREASE=response.getElementsByTagName("OIL_GREASE")[0].firstChild.nodeValue;
                    var RESIDUAL_CHLORINE=response.getElementsByTagName("RESIDUAL_CHLORINE")[0].firstChild.nodeValue;
                    var CYANIDE=response.getElementsByTagName("CYANIDE")[0].firstChild.nodeValue;
                    var ARSENIC=response.getElementsByTagName("ARSENIC")[0].firstChild.nodeValue;
                    var CADMIUM=response.getElementsByTagName("CADMIUM")[0].firstChild.nodeValue;
                    var COPPER=response.getElementsByTagName("COPPER")[0].firstChild.nodeValue;
                    
                    if(DO=="null"||DO=="")
                        DO="";
                    if(BOD=="null"||BOD=="")
                        BOD="";
                    if(COD=="null"||COD=="")
                        COD="";
                    if(OIL_GREASE=="null"||OIL_GREASE=="")
                        OIL_GREASE="";
                    if(RESIDUAL_CHLORINE=="null"||RESIDUAL_CHLORINE=="")
                        RESIDUAL_CHLORINE="";
                    if(CYANIDE=="null"||CYANIDE=="")
                        CYANIDE="";
                    if(ARSENIC=="null"||ARSENIC=="")
                        ARSENIC="";
                    if(CADMIUM=="null"||CADMIUM=="")
                        CADMIUM="";
                    if(COPPER=="null"||COPPER=="")
                        COPPER="";
                   
                    document.getElementById("dl").value=DO;
                    document.getElementById("bod").value=BOD;
                    document.getElementById("cod").value=COD;
                    document.getElementById("oil").value=OIL_GREASE;
                    document.getElementById("residual").value=RESIDUAL_CHLORINE;
                    document.getElementById("cyanide").value=CYANIDE;
                    document.getElementById("ag").value=ARSENIC;
                    document.getElementById("cd").value=CADMIUM;
                    document.getElementById("cu").value=COPPER;
                    
                    var LEAD=response.getElementsByTagName("LEAD")[0].firstChild.nodeValue;
                    var CHROMIUM=response.getElementsByTagName("CHROMIUM")[0].firstChild.nodeValue;
                    var ZINC=response.getElementsByTagName("ZINC")[0].firstChild.nodeValue;
                    var ALUMINIUM=response.getElementsByTagName("ALUMINIUM")[0].firstChild.nodeValue;
                    
                    if(LEAD=="null"||LEAD=="")
                        LEAD="";
                    if(CHROMIUM=="null"||CHROMIUM=="")
                        CHROMIUM="";
                    if(ZINC=="null"||ZINC=="")
                        ZINC="";
                    if(ALUMINIUM=="null"||ALUMINIUM=="")
                        ALUMINIUM="";
                    document.getElementById("pb").value=LEAD;
                    document.getElementById("cr").value=CHROMIUM;
                    document.getElementById("zn").value=ZINC;
                    document.getElementById("aluminium").value=ALUMINIUM;
                    
                    var SPC=response.getElementsByTagName("SPC")[0].firstChild.nodeValue;
                    var TC=response.getElementsByTagName("TC")[0].firstChild.nodeValue;
                    var FC=response.getElementsByTagName("FC")[0].firstChild.nodeValue;
                    var FSC=response.getElementsByTagName("FSC")[0].firstChild.nodeValue;
                    var CNC=response.getElementsByTagName("CNC")[0].firstChild.nodeValue;
                    var PNP=response.getElementsByTagName("PNP")[0].firstChild.nodeValue;
                    var REASON=response.getElementsByTagName("REASON")[0].firstChild.nodeValue;
                    
                    if(SPC=="null"||SPC=="")
                        SPC="";
                    if(TC=="null"||TC=="")
                        TC="";
                    if(FC=="null"||FC=="")
                        FC="";
                    if(FSC=="null"||FSC=="")
                        FSC="";
                    if(CNC=="null"||CNC=="")
                        CNC="";
                    if(PNP=="null"||PNP=="")
                        PNP="";
                    if(REASON=="null"||REASON=="")
                        REASON="";
                    
                    document.getElementById("spc").value=SPC;
                    document.getElementById("tc").value=TC;
                    document.getElementById("fc").value=FC;
                    document.getElementById("fsc").value=FSC;
                    document.getElementById("cnc").value=CNC;
                    document.getElementById("pnp").value=PNP;
                    document.getElementById("reason").value=REASON;
                    
                    document.getElementById("ctype").disabled=true;
                    document.getElementById("rno").disabled=true;
                    if(document.getElementById("ctype").value=="Twad")
                    {
                        document.getElementById("location").disabled=true;
                        document.getElementById("dname").disabled=true;
                        document.getElementById("pname").disabled=true;
                        document.getElementById("hname").disabled=true;
                    }
                    else
                    {
                        document.getElementById("location").disabled=false;
                        document.getElementById("dname").disabled=false;
                        document.getElementById("pname").disabled=false;
                        document.getElementById("hname").disabled=false;
                    }
                        
                    document.getElementById("add").disabled=true;
                    document.getElementById("del").disabled=false;
                    document.getElementById("update").disabled=false;
               }
               else
               {
                    document.getElementById("ctype").disabled=false;
                    document.getElementById("add").disabled=false;
                    document.getElementById("del").disabled=true;
                    document.getElementById("update").disabled=true;
                }
                
            }
        }
}
 
function clearAll()
{
    document.getElementById("cid").value="";
    document.getElementById("cname").value="";
    document.getElementById("ctype").value="";
    document.getElementById("rno").value="";
    document.getElementById("sno").value="";
    document.getElementById("ctype").selectedIndex="";
    clearAll1();
}
function clearAll1()
{
    document.getElementById("sdate").value="";
    document.getElementById("rdate").value="";
    document.getElementById("dc").value="";
    document.getElementById("bc").value="";
    document.getElementById("pac").value="";
    document.getElementById("hc").value="";
    document.getElementById("pc").value="";
    document.getElementById("sti").value="";
    document.getElementById("wst").value="";
    document.getElementById("sco").value="";
    
    
    document.getElementById("dcode").value="";
    document.getElementById("bcode").value="";
    document.getElementById("pancode").value="";
    document.getElementById("hcode").value="";
    document.getElementById("pcode").value="";
    document.getElementById("stype").value="";
    document.getElementById("srtype").value="";
    document.getElementById("scode").value="";
    document.getElementById("location").value="";
    
    document.getElementById("dname").value="";
    document.getElementById("pname").value="";
    document.getElementById("hname").value="";
    
    document.getElementById("appear").value="";
    document.getElementById("color").value="";
    document.getElementById("odour").value="";
    document.getElementById("turb").value="";
    document.getElementById("solid").value="";
    document.getElementById("tss").value="";
    document.getElementById("tds").value="";
    document.getElementById("ec").value="";
    document.getElementById("ph").value="";
    
    document.getElementById("acidity").value="";
    document.getElementById("phalk").value="";
    document.getElementById("talk").value="";
    document.getElementById("th").value="";
    document.getElementById("ca").value="";
    document.getElementById("mg").value="";
    document.getElementById("na").value="";
    document.getElementById("potassium").value="";
    
    document.getElementById("fe").value="";
    document.getElementById("mn").value="";
    document.getElementById("nh3").value="";
    document.getElementById("no2").value="";
    document.getElementById("no3").value="";
    document.getElementById("cl").value="";
    document.getElementById("fluoride").value="";
    document.getElementById("so4").value="";
    document.getElementById("po4").value="";
    document.getElementById("tidys").value="";
    
    document.getElementById("sio2").value="";
    document.getElementById("tkn").value="";
    document.getElementById("dl").value="";
    document.getElementById("bod").value="";
    document.getElementById("cod").value="";
    document.getElementById("oil").value="";
    document.getElementById("residual").value="";
    document.getElementById("cyanide").value="";
    document.getElementById("ag").value="";
    document.getElementById("cd").value="";
    
    document.getElementById("cu").value="";
    document.getElementById("pb").value="";
    document.getElementById("cr").value="";
    document.getElementById("zn").value="";
    document.getElementById("aluminium").value="";
    document.getElementById("spc").value="";
    document.getElementById("tc").value="";
    document.getElementById("fc").value="";
    
    document.getElementById("fsc").value="";
    document.getElementById("pnp").value="";
    document.getElementById("cnc").value="";
    document.getElementById("reason").value="";
    document.getElementById("add").disabled=false;
    document.getElementById("del").disabled=true;
    document.getElementById("update").disabled=true;
}
