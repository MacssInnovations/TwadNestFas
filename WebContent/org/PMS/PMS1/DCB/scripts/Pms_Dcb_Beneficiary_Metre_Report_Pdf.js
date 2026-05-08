var seq=0;
function GetXmlHttpObject()
{
    if(window.XMLHttpRequest)
    {
         // code for IE7+, Firefox, Chrome, Opera, Safari
        return new XMLHttpRequest();
    }
    if(window.ActiveXObject)
    {
        // code for IE6, IE5
         return new ActiveXObject("Microsoft.XMLHTTP");
    }
}
 
function divisionname()
{
    xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
     url="../../../../../Pms_Dcb_Beneficiary_Metre_Report_Pdf?command=divisionname";
         
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
          xmlhttp.onreadystatechange= function()
            {
                 stateChangeddivisionname(xmlhttp);
            }
            xmlhttp.send(null);
}
function stateChangeddivisionname(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        
        if(xmlhttp.status==200)
        {
             if(commandres=="divisionname")
            {
                if(flagres=='success')
                {
                    officename=baseres.getElementsByTagName("officename")[0].firstChild.nodeValue;
                    officeid=baseres.getElementsByTagName("officeid")[0].firstChild.nodeValue;
                    if(officename!=0)
                    {
                        document.getElementById("divisionname").innerHTML=' - '+officename;
                        document.getElementById("divisionload").value=officeid;
                    }
                    else
                    {
                        alert("Division name is not loaded");
                    }
                }
            }
        }
    }
}
function loadbeneficiarytype()
{
       
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    url="../../../../../Pms_Dcb_Beneficiary_Metre_Report_Pdf?command=loadbeneficiarytype";
  //  alert(url);
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadbeneficiarytype(xmlhttp);
            }
    xmlhttp.send(null);
}
function stateChangedloadbeneficiarytype(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(xmlhttp.status==200)
        {
             if(commandres=="loadbeneficiarytype")
            {
                if(flagres=='success')
                {
                 //   alert("success");
                    
                    
                    var BEN_TYPE_ID_len=baseres.getElementsByTagName("BEN_TYPE_ID").length;
                    //alert(beneficiary_sno_len);
                    for(var i=0;i<BEN_TYPE_ID_len;i++)
                     {
                         var BEN_TYPE_ID=baseres.getElementsByTagName("BEN_TYPE_ID")[i].firstChild.nodeValue;
                         
                         var BEN_TYPE_DESC=baseres.getElementsByTagName("BEN_TYPE_DESC")[i].firstChild.nodeValue;
                         
                         var BEN_TYPE_SDESC=baseres.getElementsByTagName("BEN_TYPE_SDESC")[i].firstChild.nodeValue;
                       //  alert(Beneficiary_Type_id);
                        
                        
                         addOptionBeneficiary_type(document.Pms_Dcb_Beneficiary_Metre_Report_Pdf.Beneficiary_type,BEN_TYPE_DESC,BEN_TYPE_ID);
                     }
                   
                     
                }
                 else
                {
                    alert("Not Loaded");
                }
            }
           
        }
    }
}
function addOptionBeneficiary_type(selectbox,text,value)
{
var optn = document.createElement("OPTION");
optn.text = text;
optn.value = value;
selectbox.options.add(optn);
}
function loadsubdivision()
{
    
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    url="../../../../../Pms_Dcb_Beneficiary_Metre_Report_Pdf?command=loadsubdivision";
  //  alert(url);
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadsubdivision(xmlhttp);
            }
    xmlhttp.send(null);
}
function stateChangedloadsubdivision(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(xmlhttp.status==200)
        {
             if(commandres=="loadsubdivision")
            {
                if(flagres=='success')
                {
                    
                    var SUBDIVISION_OFFICE_ID_len=baseres.getElementsByTagName("SUBDIVISION_OFFICE_ID").length;
                    for(var i=0;i<SUBDIVISION_OFFICE_ID_len;i++)
                     {
                         var SUBDIVISION_OFFICE_ID=baseres.getElementsByTagName("SUBDIVISION_OFFICE_ID")[i].firstChild.nodeValue;
                         var OFFICE_NAME=baseres.getElementsByTagName("OFFICE_NAME")[i].firstChild.nodeValue;
                         
                         addOptiondesc(document.Pms_Dcb_Beneficiary_Metre_Report_Pdf.SubDivision,OFFICE_NAME,SUBDIVISION_OFFICE_ID);
                     }
                   
                     
                }
                 else
                {
                    alert("Not Loaded");
                }
            }
        }
    }
}
function addOptiondesc(selectbox,text,value)
{
var optn = document.createElement("OPTION");
optn.text = text;
optn.value = value;
selectbox.options.add(optn);
}
function loadbeneficiaryname()
{
   
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
      var Beneficiary_type=document.getElementById("Beneficiary_type").value;
    //  
    // alert(Beneficiary_Name);
    url="../../../../../Pms_Dcb_Beneficiary_Metre_Report_Pdf?command=loadbeneficiaryname&Beneficiary_type="+Beneficiary_type;
  //  alert(url);
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadbeneficiaryname(xmlhttp);
            }
    xmlhttp.send(null);
}
function stateChangedloadbeneficiaryname(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        document.Pms_Dcb_Beneficiary_Metre_Report_Pdf.Beneficiary_Name.length=1;
        if(xmlhttp.status==200)
        {
             if(commandres=="loadbeneficiaryname")
            {
                if(flagres=='success')
                {
                 //   alert("success");
                    
                    
                    var BENEFICIARY_SNO_len=baseres.getElementsByTagName("BENEFICIARY_SNO").length;
                    //alert(beneficiary_sno_len);
                    for(var i=0;i<BENEFICIARY_SNO_len;i++)
                     {
                         var BENEFICIARY_SNO=baseres.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
                         var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
                        
                         addOptionbeneficiaryname(document.Pms_Dcb_Beneficiary_Metre_Report_Pdf.Beneficiary_Name,BENEFICIARY_NAME,BENEFICIARY_SNO);
                     }
                   
                     
                }
                 else
                {
                    alert("Not Loaded");
                }
            }
           
        }
    }
}
function addOptionbeneficiaryname(selectbox,text,value)
{
var optn = document.createElement("OPTION");
optn.text = text;
optn.value = value;
selectbox.options.add(optn);
}
function loadreport(reportvalue)
{
    var Beneficiary_type=document.getElementById("Beneficiary_type").value;
    var SubDivision=document.getElementById("SubDivision").value;
    var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
    if(reportvalue=='benreport')
    {
       
        if((SubDivision!="")&&(Beneficiary_type!="")&&(Beneficiary_Name!=""))
        {
            url="../../../../../Pms_Dcb_Beneficiary_Metre_Report_Pdf?command=benreport&Beneficiary_type="+Beneficiary_type+"&SubDivision="+SubDivision+"&Beneficiary_Name="+Beneficiary_Name;
          //  alert(url);
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange= function()
            {
                 stateChanged(xmlhttp);
            }
            xmlhttp.send(null);
        }
       
    }
     if(reportvalue=='subreport')
    {
        if((SubDivision!="")&&(Beneficiary_type!="")&&(Beneficiary_Name==""))
        {
            url="../../../../../Pms_Dcb_Beneficiary_Metre_Report_Pdf?command=subreport&Beneficiary_type="+Beneficiary_type+"&SubDivision="+SubDivision;
            //alert(url);
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange= function()
            {
                 stateChanged(xmlhttp);
            }
            xmlhttp.send(null);
        }
        if((SubDivision!="")&&(Beneficiary_type=="")&&(Beneficiary_Name==""))
        {
            url="../../../../../Pms_Dcb_Beneficiary_Metre_Report_Pdf?command=subreport&SubDivision="+SubDivision;
           // alert(url);
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange= function()
            {
                 stateChanged(xmlhttp);
            }
            xmlhttp.send(null);
        }
      }
      if(reportvalue=='bentype')
    {
       
        if((SubDivision=="")&&(Beneficiary_type!="")&&(Beneficiary_Name==""))
        {
            url="../../../../../Pms_Dcb_Beneficiary_Metre_Report_Pdf?command=bentype&Beneficiary_type="+Beneficiary_type+"&SubDivision="+SubDivision+"&Beneficiary_Name="+Beneficiary_Name;
            //alert(url);
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange= function()
            {
                stateChanged(xmlhttp);
            }
            xmlhttp.send(null);
        }
       
    }
    if(reportvalue=='benname')
    {
       
        if((SubDivision=="")&&(Beneficiary_type!="")&&(Beneficiary_Name!=""))
        {
            url="../../../../../Pms_Dcb_Beneficiary_Metre_Report_Pdf?command=benname&Beneficiary_type="+Beneficiary_type+"&SubDivision="+SubDivision+"&Beneficiary_Name="+Beneficiary_Name;
          //  alert(url);
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange= function()
            {
                 stateChanged(xmlhttp);
            }
            xmlhttp.send(null);
        }
       
    }
    

}
function stateChanged(xmlhttp)
{
    var baseres,commandres,flagres,recordres;
    if(xmlhttp.readyState==4)
    {
        if(xmlhttp.status==200)
        {
           
            baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
            commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
            //alert(commandres);
            seq=0;
            flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
            var tb=document.getElementById("getvaluerows");
            var t=tb.rows.length  
            for(var i=t-1;i>=0;i--)
            {
                  tb.deleteRow(i);
            } 
           // alert("hhjh");
            if(commandres=="benreport")
            {
                if(flagres=='success')
                {
                    //alert("ttt");
                   countbenname=baseres.getElementsByTagName("countbenname")[0].firstChild.nodeValue;
                    if(countbenname==1)
                    {
                        //alert(countbenname);
                        tablebody=document.getElementById("getvaluerows");
                        var len=baseres.getElementsByTagName("METRE_SNO").length;
                        for(j=0;j<len;j++)
                        {
                           // alert(len);
                            var METRE_SNO=baseres.getElementsByTagName("METRE_SNO")[j].firstChild.nodeValue;
                            var METRE_LOCATION=baseres.getElementsByTagName("METRE_LOCATION")[j].firstChild.nodeValue;
                            var OFFICE_NAME=baseres.getElementsByTagName("OFFICE_NAME")[j].firstChild.nodeValue;
                            var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[j].firstChild.nodeValue;
                            var TARIFF_RATE=baseres.getElementsByTagName("TARIFF_RATE")[j].firstChild.nodeValue;
                            var METRE_FIXED=baseres.getElementsByTagName("METRE_FIXED")[j].firstChild.nodeValue;
                            var METRE_WORKING=baseres.getElementsByTagName("METRE_WORKING")[j].firstChild.nodeValue;
                           // alert(METRE_FIXED);
                           // alert(METRE_WORKING);
                            var rowvalue=document.createElement("TR");
                            rowvalue.id=METRE_SNO;
        
                            seq++;
                    
                            var sno_value=document.createElement("TD");
                            var sno=document.createTextNode(seq);
                            sno_value.appendChild(sno);
                            rowvalue.appendChild(sno_value);
                
                            var METRE_LOCATION_VAL=document.createElement("TD");
                            var METRE_LOCATION=document.createTextNode(METRE_LOCATION);
                            METRE_LOCATION_VAL.appendChild(METRE_LOCATION);
                            rowvalue.appendChild(METRE_LOCATION_VAL);
                     
                            var OFFICE_NAME_VAL=document.createElement("TD");
                            var OFFICE_NAME=document.createTextNode(OFFICE_NAME);
                            OFFICE_NAME_VAL.appendChild(OFFICE_NAME);
                            rowvalue.appendChild(OFFICE_NAME_VAL);
                    
                            var SCH_NAME_VAL=document.createElement("TD");
                            var SCH_NAME=document.createTextNode(SCH_NAME);
                            SCH_NAME_VAL.appendChild(SCH_NAME);
                            rowvalue.appendChild(SCH_NAME_VAL);
                          
                            var TARIFF_RATE_VAL=document.createElement("TD");
                            var TARIFF_RATE=document.createTextNode(TARIFF_RATE);
                            TARIFF_RATE_VAL.appendChild(TARIFF_RATE);
                            rowvalue.appendChild(TARIFF_RATE_VAL);
                    
                            var METRE_FIXED_VAL=document.createElement("TD");
                           
                            if((METRE_FIXED=='y')||(METRE_FIXED=='Y'))
                            {
                                METRE_FIXED='Yes';
                                var METRE_FIXED=document.createTextNode(METRE_FIXED);
                            }
                            else
                            {
                                METRE_FIXED='No';
                                var METRE_FIXED=document.createTextNode(METRE_FIXED);
                            }
                            METRE_FIXED_VAL.appendChild(METRE_FIXED);
                            rowvalue.appendChild(METRE_FIXED_VAL);
                              var METRE_WORKING_VAL=document.createElement("TD");
                            if((METRE_WORKING=='y')||(METRE_WORKING=='Y'))
                            {
                                METRE_WORKING='Yes';
                                var METRE_WORKING=document.createTextNode(METRE_WORKING);
                            }
                            else
                            {
                                METRE_WORKING='No';
                                var METRE_WORKING=document.createTextNode(METRE_WORKING);
                            }
                            METRE_WORKING_VAL.appendChild(METRE_WORKING);
                            rowvalue.appendChild(METRE_WORKING_VAL);
                            
                              tablebody.appendChild(rowvalue);
                    }
                   }
                   else
                   {
                        alert("No metre found");
                    }
                }
            }
            
            if(commandres=="subreport")
            {
                if(flagres=='success')
                {
                    //alert("ttt");
                   countbenname=baseres.getElementsByTagName("countbenname")[0].firstChild.nodeValue;
                    if(countbenname==1)
                    {
                        //alert(countbenname);
                        tablebody=document.getElementById("getvaluerows");
                        var len=baseres.getElementsByTagName("METRE_SNO").length;
                        for(j=0;j<len;j++)
                        {
                           // alert(len);
                            var METRE_SNO=baseres.getElementsByTagName("METRE_SNO")[j].firstChild.nodeValue;
                            var METRE_LOCATION=baseres.getElementsByTagName("METRE_LOCATION")[j].firstChild.nodeValue;
                            var OFFICE_NAME=baseres.getElementsByTagName("OFFICE_NAME")[j].firstChild.nodeValue;
                            var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[j].firstChild.nodeValue;
                            var TARIFF_RATE=baseres.getElementsByTagName("TARIFF_RATE")[j].firstChild.nodeValue;
                            var METRE_FIXED=baseres.getElementsByTagName("METRE_FIXED")[j].firstChild.nodeValue;
                            var METRE_WORKING=baseres.getElementsByTagName("METRE_WORKING")[j].firstChild.nodeValue;
                            
                            var rowvalue=document.createElement("TR");
                            rowvalue.id=METRE_SNO;
        
                            seq++;
                    
                            var sno_value=document.createElement("TD");
                            var sno=document.createTextNode(seq);
                            sno_value.appendChild(sno);
                            rowvalue.appendChild(sno_value);
                
                            var METRE_LOCATION_VAL=document.createElement("TD");
                            var METRE_LOCATION=document.createTextNode(METRE_LOCATION);
                            METRE_LOCATION_VAL.appendChild(METRE_LOCATION);
                            rowvalue.appendChild(METRE_LOCATION_VAL);
                     
                            var OFFICE_NAME_VAL=document.createElement("TD");
                            var OFFICE_NAME=document.createTextNode(OFFICE_NAME);
                            OFFICE_NAME_VAL.appendChild(OFFICE_NAME);
                            rowvalue.appendChild(OFFICE_NAME_VAL);
                    
                            var SCH_NAME_VAL=document.createElement("TD");
                            var SCH_NAME=document.createTextNode(SCH_NAME);
                            SCH_NAME_VAL.appendChild(SCH_NAME);
                            rowvalue.appendChild(SCH_NAME_VAL);
                          
                            var TARIFF_RATE_VAL=document.createElement("TD");
                            var TARIFF_RATE=document.createTextNode(TARIFF_RATE);
                            TARIFF_RATE_VAL.appendChild(TARIFF_RATE);
                            rowvalue.appendChild(TARIFF_RATE_VAL);
                    
                            var METRE_FIXED_VAL=document.createElement("TD");
                           
                            if(METRE_FIXED=='y')
                            {
                                METRE_FIXED='Yes';
                                var METRE_FIXED=document.createTextNode(METRE_FIXED);
                            }
                            else
                            {
                                METRE_FIXED='No';
                                var METRE_FIXED=document.createTextNode(METRE_FIXED);
                            }
                            METRE_FIXED_VAL.appendChild(METRE_FIXED);
                            rowvalue.appendChild(METRE_FIXED_VAL);
                              var METRE_WORKING_VAL=document.createElement("TD");
                            if(METRE_WORKING=='y')
                            {
                                METRE_WORKING='Yes';
                                var METRE_WORKING=document.createTextNode(METRE_WORKING);
                            }
                            else
                            {
                                METRE_WORKING='No';
                                var METRE_WORKING=document.createTextNode(METRE_WORKING);
                            }
                            METRE_WORKING_VAL.appendChild(METRE_WORKING);
                            rowvalue.appendChild(METRE_WORKING_VAL);
                            
                              tablebody.appendChild(rowvalue);
                    }
                   }
                   else
                   {
                        alert("No metre found");
                    }
                }
            }
            
            if(commandres=="bentype")
            {
                if(flagres=='success')
                {
                    //alert("ttt");
                   countbenname=baseres.getElementsByTagName("countbenname")[0].firstChild.nodeValue;
                    if(countbenname==1)
                    {
                        //alert(countbenname);
                        tablebody=document.getElementById("getvaluerows");
                        var len=baseres.getElementsByTagName("METRE_SNO").length;
                        for(j=0;j<len;j++)
                        {
                           // alert(len);
                            var METRE_SNO=baseres.getElementsByTagName("METRE_SNO")[j].firstChild.nodeValue;
                            var METRE_LOCATION=baseres.getElementsByTagName("METRE_LOCATION")[j].firstChild.nodeValue;
                            var OFFICE_NAME=baseres.getElementsByTagName("OFFICE_NAME")[j].firstChild.nodeValue;
                            var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[j].firstChild.nodeValue;
                            var TARIFF_RATE=baseres.getElementsByTagName("TARIFF_RATE")[j].firstChild.nodeValue;
                            var METRE_FIXED=baseres.getElementsByTagName("METRE_FIXED")[j].firstChild.nodeValue;
                            var METRE_WORKING=baseres.getElementsByTagName("METRE_WORKING")[j].firstChild.nodeValue;
                            
                            var rowvalue=document.createElement("TR");
                            rowvalue.id=METRE_SNO;
                            
                            seq++;
                    
                            var sno_value=document.createElement("TD");
                            var sno=document.createTextNode(seq);
                            sno_value.appendChild(sno);
                            rowvalue.appendChild(sno_value);
                                
                
                            var METRE_LOCATION_VAL=document.createElement("TD");
                            var METRE_LOCATION=document.createTextNode(METRE_LOCATION);
                            METRE_LOCATION_VAL.appendChild(METRE_LOCATION);
                            rowvalue.appendChild(METRE_LOCATION_VAL);
                     
                            var OFFICE_NAME_VAL=document.createElement("TD");
                            var OFFICE_NAME=document.createTextNode(OFFICE_NAME);
                            OFFICE_NAME_VAL.appendChild(OFFICE_NAME);
                            rowvalue.appendChild(OFFICE_NAME_VAL);
                    
                            var SCH_NAME_VAL=document.createElement("TD");
                            var SCH_NAME=document.createTextNode(SCH_NAME);
                            SCH_NAME_VAL.appendChild(SCH_NAME);
                            rowvalue.appendChild(SCH_NAME_VAL);
                          
                            var TARIFF_RATE_VAL=document.createElement("TD");
                            var TARIFF_RATE=document.createTextNode(TARIFF_RATE);
                            TARIFF_RATE_VAL.appendChild(TARIFF_RATE);
                            rowvalue.appendChild(TARIFF_RATE_VAL);
                    
                            var METRE_FIXED_VAL=document.createElement("TD");
                           
                            if(METRE_FIXED=='y')
                            {
                                METRE_FIXED='Yes';
                                var METRE_FIXED=document.createTextNode(METRE_FIXED);
                            }
                            else
                            {
                                METRE_FIXED='No';
                                var METRE_FIXED=document.createTextNode(METRE_FIXED);
                            }
                            METRE_FIXED_VAL.appendChild(METRE_FIXED);
                            rowvalue.appendChild(METRE_FIXED_VAL);
                              var METRE_WORKING_VAL=document.createElement("TD");
                            if(METRE_WORKING=='y')
                            {
                                METRE_WORKING='Yes';
                                var METRE_WORKING=document.createTextNode(METRE_WORKING);
                            }
                            else
                            {
                                METRE_WORKING='No';
                                var METRE_WORKING=document.createTextNode(METRE_WORKING);
                            }
                            METRE_WORKING_VAL.appendChild(METRE_WORKING);
                            rowvalue.appendChild(METRE_WORKING_VAL);
                            
                              tablebody.appendChild(rowvalue);
                    }
                   }
                   else
                   {
                        alert("No metre found");
                    }
                }
            }
            
            
            if(commandres=="benname")
            {
                if(flagres=='success')
                {
                    //alert("ttt");
                   countbenname=baseres.getElementsByTagName("countbenname")[0].firstChild.nodeValue;
                    if(countbenname==1)
                    {
                        //alert(countbenname);
                        tablebody=document.getElementById("getvaluerows");
                        var len=baseres.getElementsByTagName("METRE_SNO").length;
                        for(j=0;j<len;j++)
                        {
                           // alert(len);
                            var METRE_SNO=baseres.getElementsByTagName("METRE_SNO")[j].firstChild.nodeValue;
                            var METRE_LOCATION=baseres.getElementsByTagName("METRE_LOCATION")[j].firstChild.nodeValue;
                            var OFFICE_NAME=baseres.getElementsByTagName("OFFICE_NAME")[j].firstChild.nodeValue;
                            var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[j].firstChild.nodeValue;
                            var TARIFF_RATE=baseres.getElementsByTagName("TARIFF_RATE")[j].firstChild.nodeValue;
                            var METRE_FIXED=baseres.getElementsByTagName("METRE_FIXED")[j].firstChild.nodeValue;
                            var METRE_WORKING=baseres.getElementsByTagName("METRE_WORKING")[j].firstChild.nodeValue;
                            
                            var rowvalue=document.createElement("TR");
                            rowvalue.id=METRE_SNO;
        
                            seq++;
                    
                            var sno_value=document.createElement("TD");
                            var sno=document.createTextNode(seq);
                            sno_value.appendChild(sno);
                            rowvalue.appendChild(sno_value);
                
                            var METRE_LOCATION_VAL=document.createElement("TD");
                            var METRE_LOCATION=document.createTextNode(METRE_LOCATION);
                            METRE_LOCATION_VAL.appendChild(METRE_LOCATION);
                            rowvalue.appendChild(METRE_LOCATION_VAL);
                     
                            var OFFICE_NAME_VAL=document.createElement("TD");
                            var OFFICE_NAME=document.createTextNode(OFFICE_NAME);
                            OFFICE_NAME_VAL.appendChild(OFFICE_NAME);
                            rowvalue.appendChild(OFFICE_NAME_VAL);
                    
                            var SCH_NAME_VAL=document.createElement("TD");
                            var SCH_NAME=document.createTextNode(SCH_NAME);
                            SCH_NAME_VAL.appendChild(SCH_NAME);
                            rowvalue.appendChild(SCH_NAME_VAL);
                          
                            var TARIFF_RATE_VAL=document.createElement("TD");
                            var TARIFF_RATE=document.createTextNode(TARIFF_RATE);
                            TARIFF_RATE_VAL.appendChild(TARIFF_RATE);
                            rowvalue.appendChild(TARIFF_RATE_VAL);
                    
                            var METRE_FIXED_VAL=document.createElement("TD");
                           
                            if(METRE_FIXED=='y')
                            {
                                METRE_FIXED='Yes';
                                var METRE_FIXED=document.createTextNode(METRE_FIXED);
                            }
                            else
                            {
                                METRE_FIXED='No';
                                var METRE_FIXED=document.createTextNode(METRE_FIXED);
                            }
                            METRE_FIXED_VAL.appendChild(METRE_FIXED);
                            rowvalue.appendChild(METRE_FIXED_VAL);
                              var METRE_WORKING_VAL=document.createElement("TD");
                            if(METRE_WORKING=='y')
                            {
                                METRE_WORKING='Yes';
                                var METRE_WORKING=document.createTextNode(METRE_WORKING);
                            }
                            else
                            {
                                METRE_WORKING='No';
                                var METRE_WORKING=document.createTextNode(METRE_WORKING);
                            }
                            METRE_WORKING_VAL.appendChild(METRE_WORKING);
                            rowvalue.appendChild(METRE_WORKING_VAL);
                            
                              tablebody.appendChild(rowvalue);
                    }
                   }
                   else
                   {
                        alert("No metre found");
                    }
                }
            }
         }
    }
}
function exitwindow()
{
    window.close();
}
/*function reportlist()
{
   //alert("reportlist");
   Beneficiary_type_report=document.Pms_Dcb_Beneficiary_Metre_Report_Pdf.Beneficiary_type.value;
   SubDivision_report=document.Pms_Dcb_Beneficiary_Metre_Report_Pdf.SubDivision.value;
   Beneficiary_type_report=document.Pms_Dcb_Beneficiary_Metre_Report_Pdf.Beneficiary_type.value;
   Beneficiary_Name_report=document.Pms_Dcb_Beneficiary_Metre_Report_Pdf.Beneficiary_Name.value;
  // alert(SubDivision_report);
   //alert(Beneficiary_type_report);
  // alert(Beneficiary_Name_report);
   if((SubDivision_report=="")&&(Beneficiary_type_report!="")&&(Beneficiary_Name_report==""))
    {
        reportvalue=1;
    }
    if((SubDivision_report!="")&&(Beneficiary_type_report=="")&&(Beneficiary_Name_report==""))
    {
        reportvalue=2;
    }
    if((SubDivision_report!="")&&(Beneficiary_type_report!="")&&(Beneficiary_Name_report==""))
    {
        reportvalue=3;
    }
     if((SubDivision_report=="")&&(Beneficiary_type_report!="")&&(Beneficiary_Name_report!=""))
    {
        reportvalue=4;
    }
    if((SubDivision_report!="")&&(Beneficiary_type_report!="")&&(Beneficiary_Name_report!=""))
    {
        reportvalue=5;
    }
  //  alert(reportvalue);
    
    
   
   
   document.Pms_Dcb_Beneficiary_Metre_Report_Pdf.action="../../../../../BeneficiaryMetreReportPdf?command=cmdreport&reportvalue="+reportvalue;
   document.Pms_Dcb_Beneficiary_Metre_Report_Pdf.method="POST";
   document.Pms_Dcb_Beneficiary_Metre_Report_Pdf.submit();
   
}*/
function reportlist()
{       
        Beneficiary_type_report=document.Pms_Dcb_Beneficiary_Metre_Report_Pdf.Beneficiary_type.value;
   SubDivision_report=document.Pms_Dcb_Beneficiary_Metre_Report_Pdf.SubDivision.value;
   Beneficiary_type_report=document.Pms_Dcb_Beneficiary_Metre_Report_Pdf.Beneficiary_type.value;
   Beneficiary_Name_report=document.Pms_Dcb_Beneficiary_Metre_Report_Pdf.Beneficiary_Name.value;
   divisionname_report=document.getElementById("divisionload").value;
   //alert(divisionname_report);
  // alert(SubDivision_report);
   //alert(Beneficiary_type_report);
  // alert(Beneficiary_Name_report);
  if((SubDivision_report=="")&&(Beneficiary_type_report=="")&&(Beneficiary_Name_report==""))
    {
       reportvalue=6;
    }
    
   if((SubDivision_report=="")&&(Beneficiary_type_report!="")&&(Beneficiary_Name_report==""))
    {
        reportvalue=1;
    }
    if((SubDivision_report!="")&&(Beneficiary_type_report=="")&&(Beneficiary_Name_report==""))
    {
        reportvalue=2;
    }
    if((SubDivision_report!="")&&(Beneficiary_type_report!="")&&(Beneficiary_Name_report==""))
    {
        reportvalue=3;
    }
     if((SubDivision_report=="")&&(Beneficiary_type_report!="")&&(Beneficiary_Name_report!=""))
    {
        reportvalue=4;
    }
    if((SubDivision_report!="")&&(Beneficiary_type_report!="")&&(Beneficiary_Name_report!=""))
    {
        reportvalue=5;
    }
        winemp= window.open('../../../../../BeneficiaryMetreReportPdf?command=cmdreport&reportvalue='+reportvalue+'&Beneficiary_type='+Beneficiary_type_report+'&SubDivision='+SubDivision_report+'&Beneficiary_Name='+Beneficiary_Name_report+'&divisionname='+divisionname_report+'',"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes");
        winemp.moveTo(250,250); 
        winemp.focus();
}