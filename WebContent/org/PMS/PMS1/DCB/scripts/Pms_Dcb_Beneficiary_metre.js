var flag=0,flagvar=0,flagmetre=0;var seq=0;
var flagApplicable=0;
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
     url="../../../../../Pms_Dcb_Beneficiary_metre?command=divisionname";
         //alert(url);
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
                    if(officename!=0)
                    {
                        document.getElementById("divisionname").innerHTML=' - '+officename;
                        
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
function meterdisplay()
{
   
    for(var i = 0; i < document.beneficary_meter.meterfixed.length; i++ )
    {
        if(document.beneficary_meter.meterfixed[i].checked)
        {
            flag=1;
            meterfixed = document.beneficary_meter.meterfixed[i].value;
            var BENEFICIARY_TYPE=document.getElementById("Beneficiary_type").value;
            if(meterfixed=="y")
            {
                var d=document.getElementById("prevref");
                d.style.display="block";
                /*if((BENEFICIARY_TYPE==6)||(BENEFICIARY_TYPE==2)||(BENEFICIARY_TYPE==3)||(BENEFICIARY_TYPE==4)||(BENEFICIARY_TYPE==5)||(BENEFICIARY_TYPE==1))
                {
                    var f=document.getElementById("fixed");
                    f.style.display="none";
                   // var f1=document.getElementById("fixed_one");
                   // f1.style.display="none";
                    
                }
                else
                {
                     var f=document.getElementById("fixed");
                     f.style.display="block";
                     //var f1=document.getElementById("fixed_one");
                    //f1.style.display="block";
                    
                }*/
            }
            else 
            {
                meterfixed = document.beneficary_meter.meterfixed[i].value;
                meterworking="n";
                // document.beneficary_meter.Metre_Type.value="";
                 document.beneficary_meter.Metre_init_reading.value="";
                // document.beneficary_meter.Init_Reading_Record_date.value="";
                        
                var d=document.getElementById("prevref");
                d.style.display="none";
                 if((BENEFICIARY_TYPE==6)||(BENEFICIARY_TYPE==2)||(BENEFICIARY_TYPE==3)||(BENEFICIARY_TYPE==4)||(BENEFICIARY_TYPE==5)||(BENEFICIARY_TYPE==1))
                {
                    var f=document.getElementById("fixed");
                    f.style.display="none";
                  //  var f1=document.getElementById("fixed_one");
                 //   f1.style.display="none";
                }
               
            }
        }
        
        
    }
}
    function chmeterworking()
    {
    for( i = 0; i < document.beneficary_meter.meterworking.length; i++ )
         {
             if(document.beneficary_meter.meterworking[i].checked)
             {
                 flagvar=1;
                 meterworking = document.beneficary_meter.meterworking[i].value;
                 //alert(meterworking);
                 
   
            }
            
        }
    }
     function MetreType()
    {
    for( i = 0; i < document.beneficary_meter.Metre_Type.length; i++ )
         {
             if(document.beneficary_meter.Metre_Type[i].checked)
             {
                 flagmetre=1;
                 Metre_Type = document.beneficary_meter.Metre_Type[i].value;
                 //alert(meterworking);
                 
   
            }
            
        }
    }
    if(flagmetre==0)
    {
        Metre_Type=0;
    }
    function Applicable()
    {
    for(var i = 0; i < document.beneficary_meter.Applicableval.length; i++ )
    {
         flagApplicable=1;
        if(document.beneficary_meter.Applicableval[i].checked)
        {
          
            Applicableval=document.beneficary_meter.Applicableval[i].value;
           // alert(Applicableval);
            if((Applicableval=="y")||(Applicableval=="Y"))
            {
                Applicableval = document.beneficary_meter.Applicableval[i].value;
                var d=document.getElementById("fixed");
                d.style.display="block";
              
            }
              if((Applicableval=="n")||(Applicableval=="N"))
                {
                     var f=document.getElementById("fixed");
                     f.style.display="none";
                     Applicableval = document.beneficary_meter.Applicableval[i].value;
                     document.beneficary_meter.Allotted_Qty.value=0;
                     document.beneficary_meter.Min_bill_Qty.value=0;
                     document.beneficary_meter.Excess_Tariff_Rate.value=1;
                    //  alert(Applicableval);
                     Allotted_Qty=0;
                     Min_bill_Qty=0;
                     Excess_Tariff_Rate=1;
                    
                    
                }
        }
               
           
            
        }
        
    }
    if(flagApplicable==0)
    {
        Applicableval="n";
        Allotted_Qty=0;
        Min_bill_Qty=0;
        Excess_Tariff_Rate=1;
    }
function doFunction(actionval)
{
   xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
 
 //   var Meter_Sno=document.getElementById("Meter_Sno").value;
    var Beneficiary_type=document.getElementById("Beneficiary_type").value;
   
    var Habitation_Name=document.getElementById("Habitation_Name").value;
     if(Habitation_Name!="")
     {
        var Habitation_Name_val=Habitation_Name;
     }
     else
     {
        var Habitation_Name_val='NULL';
     }
     
     
     
    var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
    var Consumption_Category=document.getElementById("Consumption_Categoryid").value;
    //var Consumption_Categoryid=document.getElementById("Consumption_Categoryid").value;
    
    
    
    var Tariff_Id=document.getElementById("Tariff_Id").value;
    var Tariff_Id_val=document.getElementById("Tariff_Id_val").value;
    var SubDivision=document.getElementById("SubDivision").value;
    var Schemes=document.getElementById("Schemes").value;
    var Metre_Location=document.getElementById("Metre_Location").value;
  
  
 //  var meterfixed=document.getElementById("meterfixed").value;
 //  var meterworking=document.getElementById("meterworking").value;
  // var Metre_Type=document.getElementById("Metre_Type").value;
   var Multiply_factor=document.getElementById("Multiply_factor").value;
    var Metre_init_reading=document.getElementById("Metre_init_reading").value;
    var Init_Reading_Record_date=document.getElementById("Init_Reading_Record_date").value;
    //var Applicableval=document.getElementById("Applicableval").value;
    //alert("Applicableval"+Applicableval);
    var Allotted_Qty=document.getElementById("Allotted_Qty").value;
    var Min_bill_Qty=document.getElementById("Min_bill_Qty").value;
    var Excess_Tariff_Rate=document.getElementById("Excess_Tariff_Rate").value;
    var Service_Connection=document.getElementById("Service_Connection").value;
    var Service_Connection_date=document.getElementById("Service_Connection_date").value;
    var SCH_TYPE_ID=document.getElementById("SCH_TYPE_ID").value;
   var BENEFICIARY_TYPE_ID=document.getElementById("BENEFICIARY_TYPE_ID").value;
   var OTHERS_PRIVATE_SNO=document.getElementById("OTHERS_PRIVATE_SNO").value;
   var VILLAGE_PANCHAYAT_SNO=document.getElementById("VILLAGE_PANCHAYAT_SNO").value;
   var URBANLB_SNO=document.getElementById("URBANLB_SNO").value;
       
   
   
    if(Multiply_factor!="")
    {
        var Multiply_factor_val=Multiply_factor;
    }
    else
    {
         var Multiply_factor_val=0;
    }  
    if(Metre_init_reading!="")
    {
        var Metre_init_reading_val=Metre_init_reading;
    }
    else
    {
         var Metre_init_reading_val=0;
    }      
        
       
          
    if(Allotted_Qty!="")
    {
        var Allotted_Qty_val=Allotted_Qty;
    }
    else
    {
        var Allotted_Qty_val=0;
    }
    
     if(Min_bill_Qty!="")
    {
        var Min_bill_Qty_val=Min_bill_Qty;
    }
    else
    {
        var Min_bill_Qty_val=0;
    }
     if(Excess_Tariff_Rate!="")
    {
        var Excess_Tariff_Rate_val=Excess_Tariff_Rate;
    }
    else
    {
        var Excess_Tariff_Rate=1;
    }
    /* if(Service_Connection!="")
    {
        var Service_Connection_val=Service_Connection;
    }
    else
    {
        var Service_Connection_val='NULL';
    }*/
   /* if(Service_Connection_date!="")
    {
        var Service_Connection_date_val=Service_Connection_date;
    }
    else
    {
        var Service_Connection_date_val='NULL';
    }*/
    
    if(actionval=="Add")
    {
           var valvar=validate();
            
            if(valvar==true)
           {
         
            //url="../../../../../Pms_Dcb_Beneficiary_metre?command=add&Beneficiary_type="+Beneficiary_type+"&Habitation_Name="+Habitation_Name+"&Beneficiary_Name="+Beneficiary_Name+"&Consumption_Category="+Consumption_Category+"&Multi_WS_Category="+Multi_WS_Category+"&Tariff_Id="+Tariff_Id+"&SubDivision="+SubDivision+"&Schemes="+Schemes+"&Metre_Location="+Metre_Location+"&meterfixed="+meterfixed+"&meterworking="+meterworking+"&Metre_Type="+Metre_Type+"&Multiply_factor="+Multiply_factor+"&Metre_init_reading="+Metre_init_reading+"&Init_Reading_Record_date="+Init_Reading_Record_date+"&Allotted_Qty="+Allotted_Qty+"&Min_bill_Qty="+Min_bill_Qty+"&Excess_Tariff_Rate="+Excess_Tariff_Rate+"&Service_Connection="+Service_Connection+"&Service_Connection_date="+Service_Connection_date;
                url="../../../../../Pms_Dcb_Beneficiary_metre?command=add&Beneficiary_type="+Beneficiary_type+"&Habitation_Name="+Habitation_Name_val+"&Beneficiary_Name="+Beneficiary_Name+"&Consumption_Category="+Consumption_Category+"&Tariff_Id="+Tariff_Id+"&SubDivision="+SubDivision+"&Schemes="+Schemes+"&Metre_Location="+Metre_Location+"&meterfixed="+meterfixed+"&meterworking="+meterworking+"&Metre_Type="+Metre_Type+"&Multiply_factor="+Multiply_factor_val+"&Metre_init_reading="+Metre_init_reading_val+"&Init_Reading_Record_date="+Init_Reading_Record_date+"&Allotted_Qty="+Allotted_Qty_val+"&Min_bill_Qty="+Min_bill_Qty_val+"&Excess_Tariff_Rate="+Excess_Tariff_Rate+"&Service_Connection="+Service_Connection+"&Service_Connection_date="+Service_Connection_date+"&Tariff_Id_val="+Tariff_Id_val+"&SCH_TYPE_ID="+SCH_TYPE_ID+"&BENEFICIARY_TYPE_ID="+BENEFICIARY_TYPE_ID+"&OTHERS_PRIVATE_SNO="+OTHERS_PRIVATE_SNO+"&VILLAGE_PANCHAYAT_SNO="+VILLAGE_PANCHAYAT_SNO+"&URBANLB_SNO="+URBANLB_SNO+"&Applicableval="+Applicableval;
    //  alert(url);
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange= stateChanged;
            xmlhttp.send(null);
            }
    }
  /* else if(actionval=="get")
   {
      //  var d=document.getElementById("prevref");
      //  d.style.display="block";
        url="../../../../../Pms_Dcb_Beneficiary_metre?command=get&Beneficiary_type="+Beneficiary_type+"&Beneficiary_Name="+Beneficiary_Name;
        alert("get value");
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
        
   }*/
    else if(actionval=="Update")
    {
        
        var Meter_Sno=document.getElementById("Meter_Sno").value;
        for(var i = 0; i < document.beneficary_meter.meterfixed.length; i++ )
        {
            if(document.beneficary_meter.meterfixed[i].checked)
            {
                    meterfixed = document.beneficary_meter.meterfixed[i].value;
            }
        }
        
        for(var i = 0; i < document.beneficary_meter.meterworking.length; i++ )
        {
            if(document.beneficary_meter.meterworking[i].checked)
            {
                    meterworking = document.beneficary_meter.meterworking[i].value;
            }
        }
    
         for(var i = 0; i < document.beneficary_meter.Metre_Type.length; i++ )
        {
            if(document.beneficary_meter.Metre_Type[i].checked)
            {
                    Metre_Type = document.beneficary_meter.Metre_Type[i].value;
            }
        }
         for(var i = 0; i < document.beneficary_meter.Applicableval.length; i++ )
         {
             if(document.beneficary_meter.Applicableval[i].checked)
             {
            	 Applicableval = document.beneficary_meter.Applicableval[i].value;
             }
         }
      //  url="../../../../../Pms_Dcb_Beneficiary_metre?command=update&Meter_Sno="+Meter_Sno+"&Metre_Code="+Metre_Code+"&Beneficiary_Sno="+Beneficiary_Sno+"&Metre_Location="+Metre_Location+"&Tariff_Id="+Tariff_Id+"&meterfixed="+meterfixed+"&meterworking="+meterworking+"&Metre_init_reading="+Metre_init_reading+"&Init_Reading_Record_date="+Init_Reading_Record_date+"&Allotted_Qty="+Allotted_Qty+"&Min_bill_Qty="+Min_bill_Qty+"&Multiply_factor="+Multiply_factor+"&scheme_name="+scheme_name+"&Habitation_name="+Habitation_name;
      url="../../../../../Pms_Dcb_Beneficiary_metre?command=update&Meter_Sno="+Meter_Sno+"&Beneficiary_type="+Beneficiary_type+"&Habitation_Name="+Habitation_Name_val+"&Beneficiary_Name="+Beneficiary_Name+"&Consumption_Category="+Consumption_Category+"&Tariff_Id="+Tariff_Id+"&SubDivision="+SubDivision+"&Schemes="+Schemes+"&Metre_Location="+Metre_Location+"&meterfixed="+meterfixed+"&meterworking="+meterworking+"&Metre_Type="+Metre_Type+"&Multiply_factor="+Multiply_factor_val+"&Metre_init_reading="+Metre_init_reading_val+"&Init_Reading_Record_date="+Init_Reading_Record_date+"&Allotted_Qty="+Allotted_Qty_val+"&Min_bill_Qty="+Min_bill_Qty_val+"&Excess_Tariff_Rate="+Excess_Tariff_Rate+"&Service_Connection="+Service_Connection+"&Service_Connection_date="+Service_Connection_date+"&Tariff_Id_val="+Tariff_Id_val+"&SCH_TYPE_ID="+SCH_TYPE_ID+"&BENEFICIARY_TYPE_ID="+BENEFICIARY_TYPE_ID+"&OTHERS_PRIVATE_SNO="+OTHERS_PRIVATE_SNO+"&VILLAGE_PANCHAYAT_SNO="+VILLAGE_PANCHAYAT_SNO+"&URBANLB_SNO="+URBANLB_SNO+"&Applicableval="+Applicableval;
    // alert(url);
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
      xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
       
    }
    else if(actionval=="Delete")
    {
       // url="../../../../../Pms_Dcb_Beneficiary_metre?command=delete&Meter_Sno="+Meter_Sno+"&Metre_Code="+Metre_Code+"&Beneficiary_Sno="+Beneficiary_Sno+"&Metre_Location="+Metre_Location+"&Tariff_Id="+Tariff_Id+"&meterfixed="+meterfixed+"&meterworking="+meterworking+"&Metre_init_reading="+Metre_init_reading+"&Init_Reading_Record_date="+Init_Reading_Record_date+"&Allotted_Qty="+Allotted_Qty+"&Min_bill_Qty="+Min_bill_Qty+"&Multiply_factor="+Multiply_factor+"&scheme_name="+scheme_name+"&Habitation_name="+Habitation_name;
       var Meter_Sno=document.getElementById("Meter_Sno").value;
       url="../../../../../Pms_Dcb_Beneficiary_metre?command=delete&Meter_Sno="+Meter_Sno;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange= function()
        {
            stateChanged(xmlhttp);
        }
        xmlhttp.send(null);
   }
}
function loadbeneficiarytype()
{
    
    document.getElementById("prevref").style.display='block';
    document.getElementById("fixed").style.display='none';
   // document.getElementById("fixed_one").style.display='block';
    document.getElementById("Habitation").style.display='none';
    document.getElementById("district").style.display='none';
    document.getElementById("Metre_Location_div").style.display='none';
    //document.getElementById("Metre_Location_div_name").style.display='none';
     document.getElementById("cmdupdate").style.display='none';
      document.getElementById("cmddelete").style.display='none';
   //document.getElementById("meterlabel").style.display='none';
  // document.getElementById("Habitationlabel").style.display='none';
    
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadbeneficiarytype";
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
                        
                        
                         addOptionBeneficiary_type(document.beneficary_meter.Beneficiary_type,BEN_TYPE_DESC,BEN_TYPE_ID);
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
function loadhabitations()
{
	//document.getElementById("fixed").style.display='none';
//alert("sds");
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
   
   var Beneficiary_type=document.getElementById("Beneficiary_type").value;
   if(Beneficiary_type==6)
   {
        document.getElementById("Habitation").style.display='block';
        document.getElementById("benname").innerHTML="Panchayat Name";
        document.getElementById("location").innerHTML="Meter Location";
        
       //document.getElementById("location").style.display='none';
        // document.getElementById("Habitation_one").style.display='block';
        /*url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadhabitations&Beneficiary_type="+Beneficiary_type;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange= function()
        {
              stateChangedloadhabitations(xmlhttp);
        }
    xmlhttp.send(null);*/
   }
    else
    {
        document.getElementById("benname").innerHTML="Beneficiary Name";
         document.getElementById("location").innerHTML="Metre Location";
        document.getElementById("Habitation").style.display='none';
       // document.getElementById("Habitation_one").style.display='none';
    }
    if((Beneficiary_type==6)||(Beneficiary_type==2)||(Beneficiary_type==3)||(Beneficiary_type==4)||(Beneficiary_type==5)||(Beneficiary_type==1))
    {
         document.getElementById("fixed").style.display='none';
         document.getElementById("alloted_quan").style.display='none';
        // document.getElementById("fixed_one").style.display='none';
         document.getElementById("Metre_Location").value="";
        document.getElementById("Metre_Location").style.backgroundColor = 'white';
        document.getElementById("Metre_Location").readOnly=false;
         document.getElementById("Allotted_Qty").value="";
          document.getElementById("Min_bill_Qty").value="";
           document.getElementById("Excess_Tariff_Rate").value="";
        
        
    }
    else
    {
        document.getElementById("fixed").style.display='none';
         document.getElementById("alloted_quan").style.display='block';
      //  document.getElementById("fixed_one").style.display='block';
        document.getElementById("Metre_Location").value="";
        document.getElementById("Metre_Location").style.backgroundColor = 'white';
        document.getElementById("Metre_Location").readOnly=false;
    }
   if(Beneficiary_type==6)
   {
        document.getElementById("meterlabel").style.display='block';
        document.getElementById("meterlabel").innerHTML='Habitation name';
   }
   else
   {
        document.getElementById("meterlabel").style.display='block';
        document.getElementById("meterlabel").innerHTML='Meter Location';
   }
}
function stateChangedloadhabitations(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(xmlhttp.status==200)
        {
             if(commandres=="loadhabitations")
            {
                if(flagres=='success')
                {
                
                    
                    var HAB_CODE_len=baseres.getElementsByTagName("HAB_CODE").length;
                   
                    for(var i=0;i<HAB_CODE_len;i++)
                     {
                         var HAB_CODE=baseres.getElementsByTagName("HAB_CODE")[i].firstChild.nodeValue;
                         var HNAME=baseres.getElementsByTagName("HNAME")[i].firstChild.nodeValue;
                        
                         addOptionhabitations(document.beneficary_meter.Habitation_Name,HNAME,HAB_CODE);
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
function addOptionhabitations(selectbox,text,value)
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
    url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadbeneficiaryname&Beneficiary_type="+Beneficiary_type;
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
        document.beneficary_meter.Beneficiary_Name.length=1;
        if(xmlhttp.status==200)
        {
             if((commandres=="loadbeneficiaryname")||(commandres=="loadbenname"))
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
                      //  var OTHERS_PRIVATE_SNO=baseres.getElementsByTagName("OTHERS_PRIVATE_SNO")[i].firstChild.nodeValue;
                       // var VILLAGE_PANCHAYAT_SNO=baseres.getElementsByTagName("VILLAGE_PANCHAYAT_SNO")[i].firstChild.nodeValue;
                       // var URBANLB_SNO=baseres.getElementsByTagName("URBANLB_SNO")[i].firstChild.nodeValue;
                       // var BENEFICIARY_TYPE_ID=baseres.getElementsByTagName("BENEFICIARY_TYPE_ID")[i].firstChild.nodeValue;
                        
                         addOptionbeneficiaryname(document.beneficary_meter.Beneficiary_Name,BENEFICIARY_NAME,BENEFICIARY_SNO);
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
function loadcategory()
{
   
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
     
      var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
     // document.beneficary_meter.Beneficiary_Name.length=1;
   
    if(Beneficiary_Name!=-1)
    {
        url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadcategory&Beneficiary_Name="+Beneficiary_Name;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadcategory(xmlhttp);
            }
        xmlhttp.send(null);
    }
}
function stateChangedloadcategory(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(xmlhttp.status==200)
        {
             if(commandres=="loadcategory")
            {
                if(flagres=='success')
                {
                    
                   
                    
                         var BEN_CONS_CATEGORY=baseres.getElementsByTagName("BEN_CONS_CATEGORY")[0].firstChild.nodeValue;
                         var TARIFF_ID=baseres.getElementsByTagName("TARIFF_ID")[0].firstChild.nodeValue;
                         var TARIFF_RATE=baseres.getElementsByTagName("TARIFF_RATE")[0].firstChild.nodeValue;
                         var BENEFICIARY_TYPE=baseres.getElementsByTagName("BENEFICIARY_TYPE")[0].firstChild.nodeValue;
                         var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[0].firstChild.nodeValue;
                         var BENEFICIARY_TYPE_ID=baseres.getElementsByTagName("BENEFICIARY_TYPE_ID")[0].firstChild.nodeValue;
                         var OTHERS_PRIVATE_SNO=baseres.getElementsByTagName("OTHERS_PRIVATE_SNO")[0].firstChild.nodeValue;
                         var VILLAGE_PANCHAYAT_SNO=baseres.getElementsByTagName("VILLAGE_PANCHAYAT_SNO")[0].firstChild.nodeValue;
                         var URBANLB_SNO=baseres.getElementsByTagName("URBANLB_SNO")[0].firstChild.nodeValue;
                        document.getElementById("Tariff_Id_val").value= TARIFF_ID;
                        document.getElementById("BENEFICIARY_TYPE_ID").value= BENEFICIARY_TYPE_ID;
                        document.getElementById("OTHERS_PRIVATE_SNO").value= OTHERS_PRIVATE_SNO;
                        document.getElementById("VILLAGE_PANCHAYAT_SNO").value= VILLAGE_PANCHAYAT_SNO;
                        document.getElementById("URBANLB_SNO").value= URBANLB_SNO;                        
                        // alert(BENEFICIARY_TYPE);
                         //alert((BENEFICIARY_TYPE!=6)&&(BENEFICIARY_TYPE!=2)&&(BENEFICIARY_TYPE!=3)&&(BENEFICIARY_TYPE!=4)&&(BENEFICIARY_TYPE!=5)&&(BENEFICIARY_TYPE!=1));
                         if((BENEFICIARY_TYPE!=6)&&(BENEFICIARY_TYPE!=2)&&(BENEFICIARY_TYPE!=3)&&(BENEFICIARY_TYPE!=4)&&(BENEFICIARY_TYPE!=5)&&(BENEFICIARY_TYPE!=1))
                         {
                            //alert('dsdsd');
                            document.getElementById("Tariff_Id").value=1;
                            document.getElementById("Excess_Tariff_Rate").value=1;
                            document.getElementById("Tariff_Id").style.backgroundColor = 'white';
                            document.getElementById("Tariff_Id").readOnly=false;
                               
                         }
                         else
                         {
                            //alert(BENEFICIARY_TYPE);
                            document.getElementById("Tariff_Id").value=TARIFF_RATE;
                            document.getElementById("Tariff_Id").style.backgroundColor = '#ececec';
                            document.getElementById("Tariff_Id").readOnly=true;
                           
                         }
                         
                         if((BEN_CONS_CATEGORY==0)&&(BENEFICIARY_TYPE<5))
                         {
                           // document.getElementById("bulk").style.display='none';
                            document.getElementById("Consumption_Category").value='Single Meter';
                            document.getElementById("Consumption_Categoryid").value=BEN_CONS_CATEGORY;
                         //    document.getElementById("Metre_Location").value=BENEFICIARY_NAME;
                           
                            
                           document.getElementById("Metre_Location").value=BENEFICIARY_NAME;
                             document.getElementById("Metre_Location").style.backgroundColor = '#ececec';
                            document.getElementById("Metre_Location").readOnly=true;
                            
                           document.getElementById("Metre_Location_div").style.display='none';
                             // document.getElementById("Metre_Location_div_name").style.display='none';
                         }
                         if((BEN_CONS_CATEGORY==1)&&(BENEFICIARY_TYPE<5))
                         {
                         //   document.getElementById("bulk").style.display='block';
                         //alert(BENEFICIARY_NAME);
                            document.getElementById("Consumption_Category").value='Multi Meter';
                            document.getElementById("Consumption_Categoryid").value=BEN_CONS_CATEGORY;
                            document.getElementById("Metre_Location").value=BENEFICIARY_NAME;
                            document.getElementById("Metre_Location").style.backgroundColor = 'white';
                            document.getElementById("Metre_Location").readOnly=false;
                            document.getElementById("Metre_Location_div").style.display='none';
                          //  document.getElementById("Metre_Location_div_name").style.display='block';
                         }
                         if((BEN_CONS_CATEGORY==1)&&(BENEFICIARY_TYPE==6))
                         {
                         //   document.getElementById("bulk").style.display='block';
                            document.getElementById("Consumption_Category").value='Multi Meter';
                            document.getElementById("Consumption_Categoryid").value=BEN_CONS_CATEGORY;
                            document.getElementById("Metre_Location").value="";
                            document.getElementById("Metre_Location").style.backgroundColor = 'white';
                            document.getElementById("Metre_Location").readOnly=false;
                            document.getElementById("Metre_Location_div").style.display='block';
                          //  document.getElementById("Metre_Location_div_name").style.display='block';
                         }
                         if((BEN_CONS_CATEGORY==1)&&(BENEFICIARY_TYPE>6))
                         {
                         //   document.getElementById("bulk").style.display='block';
                            document.getElementById("Consumption_Category").value='Multi Meter';
                            document.getElementById("Consumption_Categoryid").value=BEN_CONS_CATEGORY;
                            document.getElementById("Metre_Location").value="";
                            document.getElementById("Metre_Location").style.backgroundColor = 'white';
                            document.getElementById("Metre_Location").readOnly=false;
                            document.getElementById("Metre_Location_div").style.display='block';
                          //  document.getElementById("Metre_Location_div_name").style.display='block';
                         }
                        if(BENEFICIARY_TYPE_ID>6)
                        {
                            document.getElementById("Metre_Location").style.backgroundColor = 'white';
                            document.getElementById("Metre_Location").readOnly=false;
                         //   document.getElementById("Metre_Location").value="";
                            document.getElementById("Metre_Location_div").style.display='block';
                          //  document.getElementById("Metre_Location_div_name").style.display='block';
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
function loadsubdivision()
{
    
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadsubdivision";
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
                         
                         addOptiondesc(document.beneficary_meter.SubDivision,OFFICE_NAME,SUBDIVISION_OFFICE_ID);
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

function loadschemes()
{
    
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadschemes";
  //  alert(url);
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadschemes(xmlhttp);
            }
    xmlhttp.send(null);
}
function stateChangedloadschemes(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(xmlhttp.status==200)
        {
             if(commandres=="loadschemes")
            {
                if(flagres=='success')
                {
                    
                    var SCHEME_ID_len=baseres.getElementsByTagName("SCHEME_ID").length;
                    for(var i=0;i<SCHEME_ID_len;i++)
                     {
                         var SCHEME_ID=baseres.getElementsByTagName("SCHEME_ID")[i].firstChild.nodeValue;
                         var SCHEME_NAME=baseres.getElementsByTagName("SCHEME_NAME")[i].firstChild.nodeValue;
                        
                         addOptiondesc(document.beneficary_meter.Schemes,SCHEME_NAME,SCHEME_ID);
                     }
                   
                     
                }
                 else
                {
                    alert("Not Loaded schemes");
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



function stateChanged()
{
    var baseres,commandres,flagres,recordres;
    //alert(xmlhttp.readyState);
    if(xmlhttp.readyState==4)
    {
        if(xmlhttp.status==200)
        {
            //alert('sathiya');
            baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
            commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
            flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(commandres=="add")
            {
                if(flagres=='success')
                {
                   countinsert=baseres.getElementsByTagName("countinsert")[0].firstChild.nodeValue;
                   if(countinsert==0) //countinsert
                    { 
                   // alert("Successfully Added");
                    var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
                   var agree=confirm("Data Saved Sucessfully ...  Add another Meter Reading For This Beneficiary");
                   if (agree)
                   {
                        var BENEFICIARY_TYPE=document.getElementById("Beneficiary_type").value;
                        if((BENEFICIARY_TYPE!=6)&&(BENEFICIARY_TYPE!=2)&&(BENEFICIARY_TYPE!=3)&&(BENEFICIARY_TYPE!=4)&&(BENEFICIARY_TYPE!=5)&&(BENEFICIARY_TYPE!=1))
                        {
                            document.getElementById("Tariff_Id").value="1";
                            document.getElementById("Excess_Tariff_Rate").value="1";
                            document.getElementById("Tariff_Id").style.backgroundColor = 'white';
                            document.getElementById("Tariff_Id").readOnly=false;
                        }
                      // document.getElementById("Metre_Location").value="";
                       for( i = 0; i < document.beneficary_meter.meterworking.length; i++ )
                        {
                            document.beneficary_meter.meterworking[i].checked=false;
                        }
                         for( i = 0; i < document.beneficary_meter.meterfixed.length; i++ )
                        {
                            document.beneficary_meter.meterfixed[i].checked=false;
                        }   
                         for( i = 0; i < document.beneficary_meter.Metre_Type.length; i++ )
                        {
                            document.beneficary_meter.Metre_Type[i].checked=false;
                        }    
                      // document.getElementById("meterfixed").checked=false;
                       document.getElementById("meterworking").checked=false;
                       
                        document.getElementById("Schemes").value="";
                        document.getElementById("Habitation_Name").value="";
                       document.getElementById("SubDivision").value="";
                       document.getElementById("Multiply_factor").value=1;
                       document.getElementById("Metre_init_reading").value="";
                       document.getElementById("Init_Reading_Record_date").value="";
                       document.getElementById("Allotted_Qty").value="";
                       document.getElementById("Min_bill_Qty").value="";
                       if(BENEFICIARY_TYPE>5)
                       {
                       document.getElementById("Metre_Location").value="";
                       }
                       document.getElementById("Excess_Tariff_Rate").value="";
                       document.getElementById("Service_Connection").value="";
                       document.getElementById("Service_Connection_date").value="";
                       
                        tablebody=document.getElementById("getvaluerows");
                    var metre_sno=baseres.getElementsByTagName("metre_sno")[0].firstChild.nodeValue;
                    
                    var office_name=baseres.getElementsByTagName("office_name")[0].firstChild.nodeValue;
                    var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[0].firstChild.nodeValue;
                    var BEN_TYPE_DESC=baseres.getElementsByTagName("BEN_TYPE_DESC")[0].firstChild.nodeValue;
                    var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[0].firstChild.nodeValue;
                    var Metre_Location=baseres.getElementsByTagName("Metre_Location")[0].firstChild.nodeValue;
                    
                    var Tariff_Id=baseres.getElementsByTagName("Tariff_Id")[0].firstChild.nodeValue;
                    var meterfixed=baseres.getElementsByTagName("meterfixed")[0].firstChild.nodeValue;
                    var meterworking=baseres.getElementsByTagName("meterworking")[0].firstChild.nodeValue;
                    var Consumption_Category=baseres.getElementsByTagName("Consumption_Category")[0].firstChild.nodeValue;
                    var BENEFICIARY_TYPE_ID=baseres.getElementsByTagName("BENEFICIARY_TYPE_ID")[0].firstChild.nodeValue;
                    tbody=document.getElementById("getvaluerows");
                    var rowvalue=document.createElement("TR");
                    rowvalue.id=metre_sno;
                    var tabledata=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvaluesfromtable('"+metre_sno+"')";
                    var nameval=document.createTextNode("Edit");
                    anc.href=url;
                    anc.appendChild(nameval);
                    tabledata.appendChild(anc);
                   
                     var hiddentext1=document.createElement("input");
                     hiddentext1.type="hidden";
                     hiddentext1.name="qual1";
                     hiddentext1.id="qual1";
                     hiddentext1.text="qual1";
                     hiddentext1.value=metre_sno;
                    
                    tabledata.appendChild(hiddentext1);
                   
                    rowvalue.appendChild(tabledata);
                    seq++;
                    
                     var sno_value=document.createElement("TD");
                    var sno=document.createTextNode(seq);
                    sno_value.appendChild(sno);
                    rowvalue.appendChild(sno_value);
                    
                    /*  var tabledata13=document.createElement("TD");
                    var BEN_TYPE_DESC=document.createTextNode(BEN_TYPE_DESC);
                    tabledata13.appendChild(BEN_TYPE_DESC);
                    rowvalue.appendChild(tabledata13);
                    
                    
                    var tabledata12=document.createElement("TD");
                    var BENEFICIARY_NAME=document.createTextNode(BENEFICIARY_NAME);
                    tabledata12.appendChild(BENEFICIARY_NAME);
                    rowvalue.appendChild(tabledata12);
                    */
                    
                  /*  if(Consumption_Category==1)
                    {
                        document.getElementById("meterlabel").style.display='block';
                         document.getElementById("meterlabel").innerHTML='Meter Location';
                    }
                   
                    if(BENEFICIARY_TYPE_ID==6)
                    {
                        document.getElementById("meterlabel").style.display='block';
                         document.getElementById("meterlabel").innerHTML='Habitation name';
                    }*/
                    // document.getElementById("Habitationlabel").style.display='block';
                        var METRE_LOCATION_VAL=document.createElement("TD");
                        var METRE_LOCATION=document.createTextNode(Metre_Location);
                        METRE_LOCATION_VAL.appendChild(METRE_LOCATION);
                        rowvalue.appendChild(METRE_LOCATION_VAL);
                        
                    var tabledata0=document.createElement("TD");
                    var office_name=document.createTextNode(office_name);
                    tabledata0.appendChild(office_name);
                    rowvalue.appendChild(tabledata0);
                    
                   
                var tabledata1=document.createElement("TD");
                    var SCH_NAME=document.createTextNode(SCH_NAME);
                    tabledata1.appendChild(SCH_NAME);
                    rowvalue.appendChild(tabledata1);
                    
                    
                 
                    
                    
                    /* var Metre_Location_val=document.createElement("TD");
                    var Metre_Location=document.createTextNode(Metre_Location);
                    Metre_Location_val.appendChild(Metre_Location);
                    rowvalue.appendChild(Metre_Location_val);*/
                    
                    
                     var Tariff_Id_val=document.createElement("TD");
                    var Tariff_Id=document.createTextNode(Tariff_Id);
                    Tariff_Id_val.appendChild(Tariff_Id);
                    rowvalue.appendChild(Tariff_Id_val);
                    
                    
                  
                    
                    
                     var meterfixed_val=document.createElement("TD");
                    if((meterfixed=='y')||(meterfixed=='Y'))
                    {
                        meterfixed='Yes';
                        var meterfixed=document.createTextNode(meterfixed);
                    }
                    
                    else
                     {
                        meterfixed='No';
                        var meterfixed=document.createTextNode(meterfixed);
                    }
                    meterfixed_val.appendChild(meterfixed);
                    rowvalue.appendChild(meterfixed_val);
                    
                 
                    
                    var meterworking_val=document.createElement("TD");
                    if((meterworking=='y')||(meterworking=='Y'))
                    {
                        meterworking='Yes';
                        var meterworking=document.createTextNode(meterworking);
                    }
                    else
                     {
                        meterworking='No';
                        var meterworking=document.createTextNode(meterworking);
                    }
                    meterworking_val.appendChild(meterworking);
                    rowvalue.appendChild(meterworking_val);
                    
                  /*   if(Consumption_Category==1)
                    {
                         document.getElementById("meterlabel").style.display='block';
                        var METRE_LOCATION_VAL=document.createElement("TD");
                        var METRE_LOCATION=document.createTextNode(Metre_Location);
                        METRE_LOCATION_VAL.appendChild(METRE_LOCATION);
                        rowvalue.appendChild(METRE_LOCATION_VAL);
                        
                    }
                    else
                    {
                        document.getElementById("meterlabel").style.display='none';
                    }
                     if(BENEFICIARY_TYPE_ID==6)
                    {
                         document.getElementById("Habitationlabel").style.display='block';
                        var METRE_LOCATION_VAL=document.createElement("TD");
                        var METRE_LOCATION=document.createTextNode(Metre_Location);
                        METRE_LOCATION_VAL.appendChild(METRE_LOCATION);
                        rowvalue.appendChild(METRE_LOCATION_VAL);
                        
                    }
                    else
                    {
                        document.getElementById("Habitationlabel").style.display='none';
                    }
                    */
                     
                        
                    tablebody.appendChild(rowvalue);
                    
                    
                       return true ;
                       
                   }
                        
                    else
                    {
                        for( i = 0; i < document.beneficary_meter.meterworking.length; i++ )
                        {
                            document.beneficary_meter.meterworking[i].checked=false;
                        }
                         for( i = 0; i < document.beneficary_meter.meterfixed.length; i++ )
                        {
                            document.beneficary_meter.meterfixed[i].checked=false;
                        }    
                         for( i = 0; i < document.beneficary_meter.Metre_Type.length; i++ )
                        {
                            document.beneficary_meter.Metre_Type[i].checked=false;
                        }  
                        var BENEFICIARY_TYPE=document.getElementById("Beneficiary_type").value;
                        
                        document.getElementById("Tariff_Id").value=1;
                        document.getElementById("SubDivision").value="";
                        
                        if(BENEFICIARY_TYPE>5)
                        {
                        document.getElementById("Metre_Location").value="";
                        }
                       document.getElementById("Schemes").value="";
                       document.getElementById("Beneficiary_type").value="";
                       document.getElementById("Beneficiary_Name").value=0;
                        document.getElementById("Consumption_Category").value="";
                       document.getElementById("Habitation_Name").value="";
                      // document.getElementById("Metre_Type").value="";
                       document.getElementById("Multiply_factor").value=1;
                       document.getElementById("Metre_init_reading").value="";
                       document.getElementById("Init_Reading_Record_date").value="";
                       document.getElementById("Allotted_Qty").value="";
                       document.getElementById("Min_bill_Qty").value="";
                       document.getElementById("Excess_Tariff_Rate").value=1;
                       document.getElementById("Service_Connection").value="";
                       document.getElementById("Service_Connection_date").value="";
                       
                        document.getElementById("Tariff_Id_val").value="";
                        document.getElementById("SCH_TYPE_ID").value="";
                        document.getElementById("BENEFICIARY_TYPE_ID").value="";
                        document.getElementById("OTHERS_PRIVATE_SNO").value="";
                        document.getElementById("VILLAGE_PANCHAYAT_SNO").value="";
                        document.getElementById("URBANLB_SNO").value="";
                        
                        tablebody=document.getElementById("getvaluerows");
                    var metre_sno=baseres.getElementsByTagName("metre_sno")[0].firstChild.nodeValue;
                    
                /*    var office_name=baseres.getElementsByTagName("office_name")[0].firstChild.nodeValue;
                    var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[0].firstChild.nodeValue;
                    var BEN_TYPE_DESC=baseres.getElementsByTagName("BEN_TYPE_DESC")[0].firstChild.nodeValue;
                    var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[0].firstChild.nodeValue;
                    var Metre_Location=baseres.getElementsByTagName("Metre_Location")[0].firstChild.nodeValue;
                    var Tariff_Id=baseres.getElementsByTagName("Tariff_Id")[0].firstChild.nodeValue;
                    var meterfixed=baseres.getElementsByTagName("meterfixed")[0].firstChild.nodeValue;
                  */      
                        
                        tablebody=document.getElementById("getvaluerows");
                    var metre_sno=baseres.getElementsByTagName("metre_sno")[0].firstChild.nodeValue;
                    
                    var office_name=baseres.getElementsByTagName("office_name")[0].firstChild.nodeValue;
                    var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[0].firstChild.nodeValue;
                    var BEN_TYPE_DESC=baseres.getElementsByTagName("BEN_TYPE_DESC")[0].firstChild.nodeValue;
                    var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[0].firstChild.nodeValue;
                    var Metre_Location=baseres.getElementsByTagName("Metre_Location")[0].firstChild.nodeValue;
                    var Tariff_Id=baseres.getElementsByTagName("Tariff_Id")[0].firstChild.nodeValue;
                    var meterfixed=baseres.getElementsByTagName("meterfixed")[0].firstChild.nodeValue;
                    var meterworking=baseres.getElementsByTagName("meterworking")[0].firstChild.nodeValue;
                    var BENEFICIARY_TYPE_ID=baseres.getElementsByTagName("BENEFICIARY_TYPE_ID")[0].firstChild.nodeValue;
                    tbody=document.getElementById("getvaluerows");
                    var rowvalue=document.createElement("TR");
                    rowvalue.id=metre_sno;
                    var tabledata=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvaluesfromtable('"+metre_sno+"')";
                    var nameval=document.createTextNode("Edit");
                    anc.href=url;
                    anc.appendChild(nameval);
                    tabledata.appendChild(anc);
                   
                     var hiddentext1=document.createElement("input");
                     hiddentext1.type="hidden";
                     hiddentext1.name="qual1";
                     hiddentext1.id="qual1";
                     hiddentext1.text="qual1";
                     hiddentext1.value=metre_sno;
                    
                    tabledata.appendChild(hiddentext1);
                    rowvalue.appendChild(tabledata);
                   
                    seq++;
                    
                     var sno_value=document.createElement("TD");
                    var sno=document.createTextNode(seq);
                    sno_value.appendChild(sno);
                    rowvalue.appendChild(sno_value);
                    
                    
                   /*  var tabledata13=document.createElement("TD");
                    var BEN_TYPE_DESC=document.createTextNode(BEN_TYPE_DESC);
                    tabledata13.appendChild(BEN_TYPE_DESC);
                    rowvalue.appendChild(tabledata13);
                    
                    
                    var tabledata12=document.createElement("TD");
                    var BENEFICIARY_NAME=document.createTextNode(BENEFICIARY_NAME);
                    tabledata12.appendChild(BENEFICIARY_NAME);
                    rowvalue.appendChild(tabledata12); */
                   /* if(Consumption_Category==1)
                    {
                        document.getElementById("meterlabel").style.display='block';
                         document.getElementById("meterlabel").innerHTML='Meter Location';
                    }
                   
                    if(BENEFICIARY_TYPE_ID==6)
                    {
                        document.getElementById("meterlabel").style.display='block';
                         document.getElementById("meterlabel").innerHTML='Habitation name';
                    }*/
                  //   document.getElementById("Habitationlabel").style.display='block';
                        var METRE_LOCATION_VAL=document.createElement("TD");
                        var METRE_LOCATION=document.createTextNode(Metre_Location);
                        METRE_LOCATION_VAL.appendChild(METRE_LOCATION);
                        rowvalue.appendChild(METRE_LOCATION_VAL);
                        
                    var tabledata0=document.createElement("TD");
                    var office_name=document.createTextNode(office_name);
                    tabledata0.appendChild(office_name);
                    rowvalue.appendChild(tabledata0);
                    
                   
                var tabledata1=document.createElement("TD");
                    var SCH_NAME=document.createTextNode(SCH_NAME);
                    tabledata1.appendChild(SCH_NAME);
                    rowvalue.appendChild(tabledata1);
                    
                    
                  
                    
                    
                    /* var Metre_Location_val=document.createElement("TD");
                    var Metre_Location=document.createTextNode(Metre_Location);
                    Metre_Location_val.appendChild(Metre_Location);
                    rowvalue.appendChild(Metre_Location_val);
                    */
                    
                     var Tariff_Id_val=document.createElement("TD");
                    var Tariff_Id=document.createTextNode(Tariff_Id);
                    Tariff_Id_val.appendChild(Tariff_Id);
                    rowvalue.appendChild(Tariff_Id_val);
                    
                    
                     var meterfixed_val=document.createElement("TD");
                    if((meterfixed=='y')||(meterfixed=='Y'))
                    {
                        meterfixed='Yes';
                        var meterfixed=document.createTextNode(meterfixed);
                    }
                    else
                     {
                        meterfixed='No';
                        var meterfixed=document.createTextNode(meterfixed);
                    }
                    meterfixed_val.appendChild(meterfixed);
                    rowvalue.appendChild(meterfixed_val);
                    
                    
                      var meterworking_val=document.createElement("TD");
                    if((meterworking=='y')||(meterworking=='Y'))
                    {
                        meterworking='Yes';
                        var meterworking=document.createTextNode(meterworking);
                    }
                    else
                     {
                        meterworking='No';
                        var meterworking=document.createTextNode(meterworking);
                    }
                    meterworking_val.appendChild(meterworking);
                    rowvalue.appendChild(meterworking_val);
                    
                /*    if(Consumption_Category==1)
                    {
                         document.getElementById("meterlabel").style.display='block';
                        var METRE_LOCATION_VAL=document.createElement("TD");
                        var METRE_LOCATION=document.createTextNode(Metre_Location);
                        METRE_LOCATION_VAL.appendChild(METRE_LOCATION);
                        rowvalue.appendChild(METRE_LOCATION_VAL);
                        
                    }
                     else
                    {
                        document.getElementById("meterlabel").style.display='none';
                    }
                    if(BENEFICIARY_TYPE_ID==6)
                    {
                         document.getElementById("Habitationlabel").style.display='block';
                        var METRE_LOCATION_VAL=document.createElement("TD");
                        var METRE_LOCATION=document.createTextNode(Metre_Location);
                        METRE_LOCATION_VAL.appendChild(METRE_LOCATION);
                        rowvalue.appendChild(METRE_LOCATION_VAL);
                        
                    }
                    else
                    {
                        document.getElementById("Habitationlabel").style.display='none';
                    }*/
                      
                        
                    tablebody.appendChild(rowvalue);
                        
                       return true ;
                    }
                    
                    
                    
                    
                   /* var metre_sno=baseres.getElementsByTagName("metre_sno")[0].firstChild.nodeValue;
                    var Beneficiary_Sno=baseres.getElementsByTagName("Beneficiary_Sno")[0].firstChild.nodeValue;
                    var Metre_Location=baseres.getElementsByTagName("Metre_Location")[0].firstChild.nodeValue;
                    var Tariff_Id=baseres.getElementsByTagName("Tariff_Id")[0].firstChild.nodeValue;
                    var meterfixed=baseres.getElementsByTagName("meterfixed")[0].firstChild.nodeValue;
                    var Allotted_Qty=baseres.getElementsByTagName("Allotted_Qty")[0].firstChild.nodeValue;
                    var Min_bill_Qty=baseres.getElementsByTagName("Min_bill_Qty")[0].firstChild.nodeValue;
                    var Metre_init_reading=baseres.getElementsByTagName("Metre_init_reading")[0].firstChild.nodeValue;
                    var Init_Reading_Record_date=baseres.getElementsByTagName("Init_Reading_Record_date")[0].firstChild.nodeValue;
                    if(Init_Reading_Record_date=="-")
                        Init_Reading_Record_date="";
                    var Multiply_factor=baseres.getElementsByTagName("Multiply_factor")[0].firstChild.nodeValue;
                    var scheme_name=baseres.getElementsByTagName("scheme_name")[0].firstChild.nodeValue;
                    var Habitation_name=baseres.getElementsByTagName("Habitation_name")[0].firstChild.nodeValue;
                    var meterworking=baseres.getElementsByTagName("meterworking")[0].firstChild.nodeValue;
                    //var Metre_Code=baseres.getElementsByTagName("Metre_Code")[0].firstChild.nodeValue;*/
                    //alert("value1"+regnores);
                   // alert("value2"+empres);
                    
                     
                  /*  var tabledata2=document.createElement("TD");
                    var Metre_Location=document.createTextNode(Metre_Location);
                    tabledata2.appendChild(Metre_Location);
                    rowvalue.appendChild(tabledata2);
                    
                    var tabledata3=document.createElement("TD");
                    var Tariff_Id=document.createTextNode(Tariff_Id);
                    tabledata3.appendChild(Tariff_Id);
                    rowvalue.appendChild(tabledata3);
                     
                    var tabledata4=document.createElement("TD");
                    var meterfixed=document.createTextNode(meterfixed);
                    tabledata4.appendChild(meterfixed);
                    rowvalue.appendChild(tabledata4);
                    
                    var tabledata13=document.createElement("TD");
                    var meterworking=document.createTextNode(meterworking);
                    tabledata13.appendChild(meterworking);
                    rowvalue.appendChild(tabledata13);
                    
                    var tabledata7=document.createElement("TD");
                    var Metre_init_reading=document.createTextNode(Metre_init_reading);
                    tabledata7.appendChild(Metre_init_reading);
                    rowvalue.appendChild(tabledata7);
                    
                    var tabledata8=document.createElement("TD");
                    var Init_Reading_Record_date=document.createTextNode(Init_Reading_Record_date);
                    tabledata8.appendChild(Init_Reading_Record_date);
                    rowvalue.appendChild(tabledata8);
                    
                    var tabledata5=document.createElement("TD");
                    var Allotted_Qty=document.createTextNode(Allotted_Qty);
                    tabledata5.appendChild(Allotted_Qty);
                    rowvalue.appendChild(tabledata5);
                    
                    var tabledata6=document.createElement("TD");
                    var Min_bill_Qty=document.createTextNode(Min_bill_Qty);
                    tabledata6.appendChild(Min_bill_Qty);
                    rowvalue.appendChild(tabledata6);
                    
                    var tabledata9=document.createElement("TD");
                    var Multiply_factor=document.createTextNode(Multiply_factor);
                    tabledata9.appendChild(Multiply_factor);
                    rowvalue.appendChild(tabledata9);
                    
                    
                    var tabledata11=document.createElement("TD");
                    var scheme_name=document.createTextNode(scheme_name);
                    tabledata11.appendChild(scheme_name);
                    rowvalue.appendChild(tabledata11);
                    
                    var tabledata12=document.createElement("TD");
                    var Habitation_name=document.createTextNode(Habitation_name);
                    tabledata12.appendChild(Habitation_name);
                    rowvalue.appendChild(tabledata12);
                   */
                   
                  /*  tablebody.appendChild(rowvalue);*/
                  }
                  else if(countinsert==1)
                  {
                        alert("Record is already present");
                        
                  }
                }
                 else
                {
                    alert("Not success");
                }
            }
          else if(commandres=="get")
            {
                 if(flagres=='success')
                {
                    tablebody=document.getElementById("getvaluerows");
                    var len=baseres.getElementsByTagName("METRE_SNO").length;
                    for(var i=0;i<len;i++)
                    {
                     var METRE_SNO=baseres.getElementsByTagName("METRE_SNO")[i].firstChild.nodeValue;
                    var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
                //    var SUB_DIV_ID=baseres.getElementsByTagName("SUB_DIV_ID")[i].firstChild.nodeValue;
                    var officename=baseres.getElementsByTagName("officename")[i].firstChild.nodeValue;
                  //  var SCHEME_SNO=baseres.getElementsByTagName("SCHEME_SNO")[i].firstChild.nodeValue;
                    var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[i].firstChild.nodeValue;
                 //   var HABITATION_SNO=baseres.getElementsByTagName("HABITATION_SNO")[i].firstChild.nodeValue;
                    var TARIFF_RATE=baseres.getElementsByTagName("TARIFF_RATE")[i].firstChild.nodeValue;
                 //   var METRE_WORKING=baseres.getElementsByTagName("METRE_WORKING")[i].firstChild.nodeValue;
                    var METRE_FIXED=baseres.getElementsByTagName("METRE_FIXED")[i].firstChild.nodeValue;
                    var METRE_LOCATION=baseres.getElementsByTagName("METRE_LOCATION")[i].firstChild.nodeValue;
                    var BEN_TYPE_DESC=baseres.getElementsByTagName("BEN_TYPE_DESC")[i].firstChild.nodeValue;
                     var meterworking=baseres.getElementsByTagName("meterworking")[i].firstChild.nodeValue;
                     var BEN_TYPE_ID=baseres.getElementsByTagName("BEN_TYPE_ID")[i].firstChild.nodeValue;
                   /* var Init_Reading_Record_date_temp=baseres.getElementsByTagName("Init_Reading_Record_date")[i].firstChild.nodeValue;
                    if(Init_Reading_Record_date_temp=="-")
                    {
                        Init_Reading_Record_date="";
                    }
                    else
                    {                    
                        var temp=Init_Reading_Record_date_temp.split("-");
                        var Init_Reading_Record_date=temp[2]+"/"+temp[1]+"/"+temp[0]; 
                    }
                   
                    var Multiply_factor=baseres.getElementsByTagName("Multiply_factor")[i].firstChild.nodeValue;
                    var scheme_name=baseres.getElementsByTagName("scheme_name")[i].firstChild.nodeValue;
                    var Habitation_name=baseres.getElementsByTagName("Habitation_name")[i].firstChild.nodeValue;
                    var meterworking=baseres.getElementsByTagName("meterworking")[i].firstChild.nodeValue;
                    //var Metre_Code=baseres.getElementsByTagName("Metre_Code")[i].firstChild.nodeValue;
                    
                    var rowvalue=document.createElement("TR");
                    rowvalue.id=metre_sno;
                    var tabledata=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvaluesfromtable('"+metre_sno+"')";
                    var nameval=document.createTextNode("Edit");
                    anc.href=url;
                    anc.appendChild(nameval);
                    tabledata.appendChild(anc);
                    rowvalue.appendChild(tabledata);
                    
                    var tabledata0=document.createElement("TD");
                    var tariff_Id=document.createTextNode(metre_sno);
                    tabledata0.appendChild(tariff_Id);
                    rowvalue.appendChild(tabledata0);
                    
                    /*var tabledata0A=document.createElement("TD");
                    var Metre_Code=document.createTextNode(Metre_Code);
                    tabledata0A.appendChild(Metre_Code);
                    rowvalue.appendChild(tabledata0A);*/
                  
                    var rowvalue=document.createElement("TR");
                    rowvalue.id=METRE_SNO;
                    var tabledata=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvaluesfromtable('"+METRE_SNO+"')";
                    var nameval=document.createTextNode("Edit");
                    anc.href=url;
                    anc.appendChild(nameval);
                    tabledata.appendChild(anc);
                    
                    
                  var hiddentext1=document.createElement("input");
                     hiddentext1.type="hidden";
                     hiddentext1.name="qual1";
                     hiddentext1.id="qual1";
                     hiddentext1.text="qual1";
                     hiddentext1.value=METRE_SNO;
                     tabledata.appendChild(hiddentext1);
                     
                    rowvalue.appendChild(tabledata);
                     
                    var tabledata8=document.createElement("TD");
                    var BEN_TYPE_DESC=document.createTextNode(BEN_TYPE_DESC);
                    tabledata8.appendChild(BEN_TYPE_DESC);
                    rowvalue.appendChild(tabledata8);
                    
                    var tabledata2=document.createElement("TD");
                    var BENEFICIARY_NAME=document.createTextNode(BENEFICIARY_NAME);
                    tabledata2.appendChild(BENEFICIARY_NAME);
                    rowvalue.appendChild(tabledata2);
                   
                     
                    var tabledata3=document.createElement("TD");
                    var officename=document.createTextNode(officename);
                    tabledata3.appendChild(officename);
                    rowvalue.appendChild(tabledata3);
                    
                     var tabledata4=document.createElement("TD");
                    var SCH_NAME=document.createTextNode(SCH_NAME);
                    tabledata4.appendChild(SCH_NAME);
                    rowvalue.appendChild(tabledata4);
                    
                     
                                    
                    
               /*     var tabledata13=document.createElement("TD");
                    var METRE_LOCATION=document.createTextNode(METRE_LOCATION);
                    tabledata13.appendChild(METRE_LOCATION);
                    rowvalue.appendChild(tabledata13);
              */
                    var tabledata7=document.createElement("TD");
                    var TARIFF_RATE=document.createTextNode(TARIFF_RATE);
                    tabledata7.appendChild(TARIFF_RATE);
                    rowvalue.appendChild(tabledata7);
                    
                     var tabledata5=document.createElement("TD");
                   
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
                    tabledata5.appendChild(METRE_FIXED);
                    
                      var meterworking_val=document.createElement("TD");
                    if((meterworking=='y')||(meterworking=='Y'))
                    {
                        meterworking='Yes';
                        var meterworking=document.createTextNode(meterworking);
                    }
                    else
                     {
                        meterworking='No';
                        var meterworking=document.createTextNode(meterworking);
                    }
                    meterworking_val.appendChild(meterworking);
                    rowvalue.appendChild(meterworking_val);
                    
                    
                    
                    rowvalue.appendChild(tabledata5);
                    
                   
                    
                   
                    
                  /*   var tabledata6=document.createElement("TD");
                    var Min_bill_Qty=document.createTextNode(Min_bill_Qty);
                    tabledata6.appendChild(Min_bill_Qty);
                    rowvalue.appendChild(tabledata6);
                    
                   var tabledata9=document.createElement("TD");
                    var Multiply_factor=document.createTextNode(Multiply_factor);
                    tabledata9.appendChild(Multiply_factor);
                    rowvalue.appendChild(tabledata9);
                    
                    
                    var tabledata11=document.createElement("TD");
                    var scheme_name=document.createTextNode(scheme_name);
                    tabledata11.appendChild(scheme_name);
                    rowvalue.appendChild(tabledata11);
                    
                    var tabledata12=document.createElement("TD");
                    var Habitation_name=document.createTextNode(Habitation_name);
                    tabledata12.appendChild(Habitation_name);
                    rowvalue.appendChild(tabledata12);*/
                   
                     
                      
                      
                      tablebody.appendChild(rowvalue);
                      
                    }
                   
                } 
                else
                {
                   alert("Not success");
                }
            }
             else if(commandres=="update")
            {
                if(flagres=='success')
                {
                    //duplicate check starts
                   // countinsert=baseres.getElementsByTagName("countinsert")[0].firstChild.nodeValue;
                   // if(countinsert==0)
                   // {
                    //duplicate check ends
                    alert( 'Successfully Updated');
                    var metre_sno=baseres.getElementsByTagName("metre_sno")[0].firstChild.nodeValue;
                    
                    var office_name=baseres.getElementsByTagName("office_name")[0].firstChild.nodeValue;
                    var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[0].firstChild.nodeValue;
                    var BEN_TYPE_DESC=baseres.getElementsByTagName("BEN_TYPE_DESC")[0].firstChild.nodeValue;
                    var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[0].firstChild.nodeValue;
                    var Metre_Location=baseres.getElementsByTagName("Metre_Location")[0].firstChild.nodeValue;
                    var Tariff_Id=baseres.getElementsByTagName("Tariff_Id")[0].firstChild.nodeValue;
                    var meterfixed=baseres.getElementsByTagName("meterfixed")[0].firstChild.nodeValue;
                     var meterworking=baseres.getElementsByTagName("meterworking")[0].firstChild.nodeValue;
                     var Consumption_Category=baseres.getElementsByTagName("Consumption_Category")[0].firstChild.nodeValue;
                     var BENEFICIARY_TYPE_ID=baseres.getElementsByTagName("BENEFICIARY_TYPE_ID")[0].firstChild.nodeValue;
                    var rvar=document.getElementById(metre_sno);
                    var rcells=rvar.cells;
                   // rcells.item(1).firstChild.nodeValue=metre_sno;
                    //rcells.item(2).firstChild.nodeValue=Metre_Code;
                  //   rcells.item(2).firstChild.nodeValue=BEN_TYPE_DESC;
                   // rcells.item(3).firstChild.nodeValue=BENEFICIARY_NAME;
                   if(Consumption_Category==1)
                    {
                         document.getElementById("meterlabel").style.display='block';
                       rcells.item(2).firstChild.nodeValue=Metre_Location;
                        
                    }
                   
                     if(BENEFICIARY_TYPE_ID==6)
                    {
                         document.getElementById("meterlabel").style.display='block';
                       rcells.item(2).firstChild.nodeValue=Metre_Location;
                        
                    }
                    
                    rcells.item(3).firstChild.nodeValue=office_name;
                    rcells.item(4).firstChild.nodeValue=SCH_NAME;
                   
                   // rcells.item(5).firstChild.nodeValue=Metre_Location;
                    rcells.item(5).firstChild.nodeValue=Tariff_Id;
                    
                    if((meterfixed=='y')||(meterfixed=='Y'))
                    {
                        meterfixed='Yes';
                        var METRE_FIXED=document.createTextNode(METRE_FIXED);
                    }
                    else
                     {
                        meterfixed='No';
                        var METRE_FIXED=document.createTextNode(METRE_FIXED);
                    }
                    rcells.item(6).firstChild.nodeValue=meterfixed;
                 
                     if((meterworking=='y')||(meterworking=='Y'))
                    {
                        meterworking='Yes';
                      
                    }
                    else
                     {
                        meterworking='No';
                        
                    }
                   rcells.item(7).firstChild.nodeValue=meterworking;
                 /*   if(Consumption_Category==1)
                    {
                         document.getElementById("meterlabel").style.display='block';
                       rcells.item(8).firstChild.nodeValue=Metre_Location;
                        
                    }
                    else
                    {
                         document.getElementById("meterlabel").style.display='none';
                    }
                     if(BENEFICIARY_TYPE_ID==6)
                    {
                         document.getElementById("meterlabel").style.display='block';
                       rcells.item(8).firstChild.nodeValue=Metre_Location;
                        
                    }
                    else
                    {
                         document.getElementById("meterlabel").style.display='none';
                    }*/
                    
                     document.getElementById("Meter_Sno").value="";
    document.getElementById("Beneficiary_type").value="";
    document.getElementById("Habitation_Name").value="";
    document.getElementById("Beneficiary_Name").value=0;
    document.getElementById("Consumption_Category").value="";
 //   document.getElementById("Multi_WS_Category").value="";
    document.getElementById("Tariff_Id").value=1;
    document.getElementById("SubDivision").value="";
    document.getElementById("Schemes").value="";
    document.getElementById("Metre_Location").value="";
    //document.getElementById("meterfixed").value="y";
   // document.getElementById("meterworking").value="";
   document.getElementById("Metre_Location").value="";
   for( i = 0; i < document.beneficary_meter.meterworking.length; i++ )
    {
        document.beneficary_meter.meterworking[i].checked=false;
    }
 for( i = 0; i < document.beneficary_meter.meterfixed.length; i++ )
 {
      document.beneficary_meter.meterfixed[i].checked=false;
 }    
  //  document.getElementById("Metre_Type").value="";
   for( i = 0; i < document.beneficary_meter.Metre_Type.length; i++ )
 {
      document.beneficary_meter.Metre_Type[i].checked=false;
 }  
    document.getElementById("Multiply_factor").value=1;
    document.getElementById("Metre_init_reading").value="";
    document.getElementById("Init_Reading_Record_date").value="";
    document.getElementById("Allotted_Qty").value="";
    document.getElementById("Min_bill_Qty").value="";
    document.getElementById("Excess_Tariff_Rate").value=1;
    document.getElementById("Service_Connection").value="";
    document.getElementById("Service_Connection_date").value="";
    
    document.getElementById("Tariff_Id_val").value="";
    document.getElementById("SCH_TYPE_ID").value="";
    document.getElementById("BENEFICIARY_TYPE_ID").value="";
    document.getElementById("OTHERS_PRIVATE_SNO").value="";
    document.getElementById("VILLAGE_PANCHAYAT_SNO").value="";
    document.getElementById("URBANLB_SNO").value="";
    //document.getElementById('cmdadd').disabled=false;
                //duplicate check starts  }
                  /* else if(countinsert==1)
                  {
                        alert("Record is already present");
                        
                  } duplicate check ends */ 
                   }
                 else
                {
                    alert("Not updated");
                }
            }
            else if(commandres=="delete")
            {
                if(flagres=='success')
                {
                    
                     var metre_sno=baseres.getElementsByTagName("metre_sno")[0].firstChild.nodeValue;
                     var checkcons=baseres.getElementsByTagName("checkcons")[0].firstChild.nodeValue;
                     if(checkcons==1)
                     {
                        alert("Metre details For This Beneficiary Cannot Be Deleted ... It has been refered by some module");
                        
                     }
                     else
                     {
                        
                        var tbody=document.getElementById("existing");
                        var r=document.getElementById(metre_sno);
                        
                        var ri=r.rowIndex;
                        tbody.deleteRow(ri);
                        alert('Successfully Deleted');
                         document.getElementById("Meter_Sno").value="";
    document.getElementById("Beneficiary_type").value="";
    document.getElementById("Habitation_Name").value="";
    document.getElementById("Beneficiary_Name").value=0;
    document.getElementById("Consumption_Category").value="";
 //   document.getElementById("Multi_WS_Category").value="";
    document.getElementById("Tariff_Id").value="";
    document.getElementById("SubDivision").value="";
    document.getElementById("Schemes").value="";
    document.getElementById("Metre_Location").value="";
    //document.getElementById("meterfixed").value="y";
   // document.getElementById("meterworking").value="";
   document.getElementById("Metre_Location").value="";
   for( i = 0; i < document.beneficary_meter.meterworking.length; i++ )
    {
        document.beneficary_meter.meterworking[i].checked=false;
    }
 for( i = 0; i < document.beneficary_meter.meterfixed.length; i++ )
 {
      document.beneficary_meter.meterfixed[i].checked=false;
 }    
  //  document.getElementById("Metre_Type").value="";
   for( i = 0; i < document.beneficary_meter.Metre_Type.length; i++ )
 {
      document.beneficary_meter.Metre_Type[i].checked=false;
 }  
    document.getElementById("Multiply_factor").value=1;
    document.getElementById("Metre_init_reading").value="";
    document.getElementById("Init_Reading_Record_date").value="";
    document.getElementById("Allotted_Qty").value="";
    document.getElementById("Min_bill_Qty").value="";
    document.getElementById("Excess_Tariff_Rate").value="";
    document.getElementById("Service_Connection").value="";
    document.getElementById("Service_Connection_date").value="";
    
    document.getElementById("Tariff_Id_val").value="";
    document.getElementById("SCH_TYPE_ID").value="";
    document.getElementById("BENEFICIARY_TYPE_ID").value="";
    document.getElementById("OTHERS_PRIVATE_SNO").value="";
    document.getElementById("VILLAGE_PANCHAYAT_SNO").value="";
    document.getElementById("URBANLB_SNO").value="";
    
                    }
                    
                }
                 else
                {
                    alert("Not deleted");
                }
            }
        }
    }
}
function loadvaluesfromtable(r)
{
     document.getElementById("cmdadd").style.display='none';
     document.getElementById("cmdupdate").style.display='block';
    document.getElementById("cmddelete").style.display='block';
     document.getElementById("cmdupdate").style.display='inline';
    document.getElementById("cmddelete").style.display='inline';
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    //var d=document.getElementById("prevref");
  //  d.style.display="block";
 // document.getElementById('cmdadd').disabled=true;
 //   document.getElementById('cmdupdate').disabled=false;
 //   document.getElementById('cmddelete').disabled=false;
    var rvar=document.getElementById(r);
    var rcells=rvar.cells;
    var rvalue=rcells.item(0).lastChild.value;
    
   /* document.getElementById('Meter_Sno').value=rcells.item(1).firstChild.nodeValue;
    //document.getElementById('Metre_Code').value=rcells.item(2).firstChild.nodeValue;
    document.getElementById('Beneficiary_Sno').value=rcells.item(3).firstChild.nodeValue;
    document.getElementById('Metre_Location').value=rcells.item(4).firstChild.nodeValue;
    document.getElementById('Tariff_Id').value=rcells.item(5).firstChild.nodeValue;
    tempmeter=rcells.item(6).firstChild.nodeValue;
   // alert(tempmeter);
  
    if(tempmeter=='y')
    {
       document.beneficary_meter.meterfixed[0].checked=true;
    }
    else
    {
        document.beneficary_meter.meterfixed[1].checked=true;
         d.style.display="none";
    }
    tempworking=rcells.item(7).firstChild.nodeValue;
   // alert(tempworking);
    if(tempworking=='y')
    {
        document.beneficary_meter.meterworking[0].checked=true;
    }
    else
    {
        document.beneficary_meter.meterworking[1].checked=true;
    }
    
    document.getElementById('Metre_init_reading').value=rcells.item(8).firstChild.nodeValue;
    document.getElementById('Init_Reading_Record_date').value=rcells.item(9).firstChild.nodeValue;
    document.getElementById('Allotted_Qty').value=rcells.item(10).firstChild.nodeValue;
    document.getElementById('Min_bill_Qty').value=rcells.item(11).firstChild.nodeValue;
    document.getElementById('Multiply_factor').value=rcells.item(12).firstChild.nodeValue;
    document.getElementById('scheme_name').value=rcells.item(13).firstChild.nodeValue;
    document.getElementById('Habitation_name').value=rcells.item(14).firstChild.nodeValue;
    
      document.getElementById('cmdadd').disabled=true;
    document.getElementById('cmdupdate').disabled=false;
    document.getElementById('cmddelete').disabled=false;
    
    */
    
    
    url="../../../../../Pms_Dcb_Beneficiary_metre?command=getgrid&msno="+rvalue;
       //alert("getgrid value");
        url=url+"&sid="+Math.random();
      //  alert(url);
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=function()
            {
                 stateChangedgetgrid(xmlhttp);
            }
        
        
        xmlhttp.send(null);
        
  
}
function stateChangedgetgrid(xmlhttp)
{
         var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(xmlhttp.status==200)
        {
             if(commandres=="getgrid")
            {
                if(flagres=='success')
                {
                    document.beneficary_meter.Beneficiary_Name.length=1;
                    document.beneficary_meter.Habitation_Name.length=1;
                    var METRE_SNO=baseres.getElementsByTagName("METRE_SNO")[0].firstChild.nodeValue;
                    var SUB_DIV_ID=baseres.getElementsByTagName("SUB_DIV_ID")[0].firstChild.nodeValue;
                     var SCHEME_SNO=baseres.getElementsByTagName("SCHEME_SNO")[0].firstChild.nodeValue;
                     var SCH_TYPE_ID=baseres.getElementsByTagName("SCH_TYPE_ID")[0].firstChild.nodeValue;
                    var BENEFICIARY_TYPE_ID=baseres.getElementsByTagName("BENEFICIARY_TYPE_ID")[0].firstChild.nodeValue;
                    var HAB_var=baseres.getElementsByTagName("HAB_var")[0].firstChild.nodeValue;
                    var ALLOTED_FLG=baseres.getElementsByTagName("ALLOTED_FLG")[0].firstChild.nodeValue;
                    //alert("dsdsd");
                   // alert(ALLOTED_FLG);
                     if(BENEFICIARY_TYPE_ID==6)
                    {
                      
                            document.getElementById("Habitation").style.display='block';
                            document.getElementById("benname").innerHTML="Panchayat Name";
                            document.getElementById("location").innerHTML="Habitation Name";
        
                    }
                    else
                    {
                            document.getElementById("benname").innerHTML="Beneficiary Name";
                            document.getElementById("location").innerHTML="Metre Location";
                            document.getElementById("Habitation").style.display='none';
       
                    }
                   if((BENEFICIARY_TYPE_ID==6)||(BENEFICIARY_TYPE_ID==2)||(BENEFICIARY_TYPE_ID==3)||(BENEFICIARY_TYPE_ID==4)||(BENEFICIARY_TYPE_ID==5)||(BENEFICIARY_TYPE_ID==1))
                    {
                        document.getElementById("fixed").style.display='none';
                    }
                     else
                    {
                        document.getElementById("fixed").style.display='block';
                    }
                    var BULKWS_CATEGORY=baseres.getElementsByTagName("BULKWS_CATEGORY")[0].firstChild.nodeValue;
                    var METRE_LOCATION=baseres.getElementsByTagName("METRE_LOCATION")[0].firstChild.nodeValue;
                    var TARIFF_RATE=baseres.getElementsByTagName("TARIFF_RATE")[0].firstChild.nodeValue;
                     var TARIFF_ID=baseres.getElementsByTagName("TARIFF_ID")[0].firstChild.nodeValue;
                    var METRE_FIXED=baseres.getElementsByTagName("METRE_FIXED")[0].firstChild.nodeValue;
                    var METRE_WORKING=baseres.getElementsByTagName("METRE_WORKING")[0].firstChild.nodeValue;
                    var MULTIPLY_FACTOR=baseres.getElementsByTagName("MULTIPLY_FACTOR")[0].firstChild.nodeValue;
                     var METRE_TYPE=baseres.getElementsByTagName("METRE_TYPE")[0].firstChild.nodeValue;
                      var METRE_INIT_READING=baseres.getElementsByTagName("METRE_INIT_READING")[0].firstChild.nodeValue;
                    var INIT_READING_RECORD_DT=baseres.getElementsByTagName("INIT_READING_RECORD_DT")[0].firstChild.nodeValue;
                     if(INIT_READING_RECORD_DT=="-")
                    {
                        INIT_READING_RECORD_DT="";
                    }
                    else
                    {                    
                        var temp=INIT_READING_RECORD_DT.split("-");
                        var INIT_READING_RECORD_DT=temp[2]+"/"+temp[1]+"/"+temp[0]; 
                    }
                   
                    
                    var ALLOTED_QTY=baseres.getElementsByTagName("ALLOTED_QTY")[0].firstChild.nodeValue;
                    var MIN_BILL_QTY=baseres.getElementsByTagName("MIN_BILL_QTY")[0].firstChild.nodeValue;
                   var HABITATION_SNO=baseres.getElementsByTagName("HABITATION_SNO")[0].firstChild.nodeValue;
                   var BENEFICIARY_TYPE_ID=baseres.getElementsByTagName("BENEFICIARY_TYPE_ID")[0].firstChild.nodeValue;
                    var OTHERS_PRIVATE_SNO=baseres.getElementsByTagName("OTHERS_PRIVATE_SNO")[0].firstChild.nodeValue;
                    var VILLAGE_PANCHAYAT_SNO=baseres.getElementsByTagName("VILLAGE_PANCHAYAT_SNO")[0].firstChild.nodeValue;
                    var URBANLB_SNO=baseres.getElementsByTagName("URBANLB_SNO")[0].firstChild.nodeValue;
                    
                    
                    var BULKWS_CATEGORY=baseres.getElementsByTagName("BULKWS_CATEGORY")[0].firstChild.nodeValue;
                    
                    
                    if((BULKWS_CATEGORY==0)&&(BENEFICIARY_TYPE_ID<5))
                         {
                           // document.getElementById("bulk").style.display='none';
                          
                            document.getElementById("Consumption_Category").value='Single Meter';
                            document.getElementById("Consumption_Categoryid").value=BULKWS_CATEGORY;
                             document.getElementById("Metre_Location").value=METRE_LOCATION;
                            document.getElementById("Metre_Location").style.backgroundColor = '#ececec';
                            document.getElementById("Metre_Location").readOnly=true;
                            document.getElementById("Metre_Location_div").style.display='none';
                            //document.getElementById("Metre_Location_div_name").style.display='none';
                         }
                         if((BULKWS_CATEGORY==1)&&(BENEFICIARY_TYPE_ID<5))
                         {
                           // document.getElementById("bulk").style.display='none';
                          
                            document.getElementById("Consumption_Category").value='Single Meter';
                            document.getElementById("Consumption_Categoryid").value=BULKWS_CATEGORY;
                             document.getElementById("Metre_Location").value=METRE_LOCATION;
                            document.getElementById("Metre_Location").style.backgroundColor = '#ececec';
                            document.getElementById("Metre_Location").readOnly=true;
                            document.getElementById("Metre_Location_div").style.display='none';
                            //document.getElementById("Metre_Location_div_name").style.display='none';
                         }
                         if ((BULKWS_CATEGORY==1)&&(BENEFICIARY_TYPE_ID==6))
                         {
                         //   alert(BULKWS_CATEGORY);
                            document.getElementById("Consumption_Category").value='Multi Meter';
                            document.getElementById("Consumption_Categoryid").value=BULKWS_CATEGORY;
                             document.getElementById("Metre_Location").value="";
                            document.getElementById("Metre_Location").style.backgroundColor = 'white';
                            document.getElementById("Metre_Location").readOnly=false;
                            document.getElementById("Metre_Location_div").style.display='block';
                            document.getElementById('Metre_Location').value=METRE_LOCATION;
                          //  document.getElementById("Metre_Location_div_name").style.display='block';
                         }
                    if ((BULKWS_CATEGORY==1)&&(BENEFICIARY_TYPE_ID>6))
                         {
                         //   alert(BULKWS_CATEGORY);
                            document.getElementById("Consumption_Category").value='Multi Meter';
                            document.getElementById("Consumption_Categoryid").value=BULKWS_CATEGORY;
                             document.getElementById("Metre_Location").value="";
                            document.getElementById("Metre_Location").style.backgroundColor = 'white';
                            document.getElementById("Metre_Location").readOnly=false;
                            document.getElementById("Metre_Location_div").style.display='block';
                            document.getElementById('Metre_Location').value=METRE_LOCATION;
                          //  document.getElementById("Metre_Location_div_name").style.display='block';
                         }
                        if(BENEFICIARY_TYPE_ID>6)
                        {
                            document.getElementById("Metre_Location").style.backgroundColor = 'white';
                            document.getElementById("Metre_Location").readOnly=false;
                         //   document.getElementById("Metre_Location").value="";
                            document.getElementById("Metre_Location_div").style.display='block';
                          //  document.getElementById("Metre_Location_div_name").style.display='block';
                          document.getElementById('Metre_Location').value=METRE_LOCATION;
                        }

                    var SERVICE_CON_NO=baseres.getElementsByTagName("SERVICE_CON_NO")[0].firstChild.nodeValue;
                    if(SERVICE_CON_NO=="-")
                    {
                        SERVICE_CON_NO="";
                    }
                    else
                    {                    
                         SERVICE_CON_NO=SERVICE_CON_NO;
                    }
                    var SEVICE_CONN_DATE=baseres.getElementsByTagName("SEVICE_CONN_DATE")[0].firstChild.nodeValue;
                    if(SEVICE_CONN_DATE=="-")
                    {
                        SEVICE_CONN_DATE="";
                    }
                    else
                    {                    
                        var temp=SEVICE_CONN_DATE.split("-");
                        var SEVICE_CONN_DATE=temp[2]+"/"+temp[1]+"/"+temp[0]; 
                    }
                    var BULKWS_CATEGORY=baseres.getElementsByTagName("BULKWS_CATEGORY")[0].firstChild.nodeValue;
                    var EXCESS_TARIFF_RATE=baseres.getElementsByTagName("EXCESS_TARIFF_RATE")[0].firstChild.nodeValue;
                    var BEN_SNO=baseres.getElementsByTagName("BEN_SNO")[0].firstChild.nodeValue;
                   
                     
                    var BENEFICIARY_SNO_len=baseres.getElementsByTagName("BENEFICIARY_SNO").length; 
                    
                    for(var i=0;i<BENEFICIARY_SNO_len;i++)
                     {
                         var BENEFICIARY_SNO=baseres.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
                         var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
                         
                         addOptionbeneficiaryname(document.beneficary_meter.Beneficiary_Name,BENEFICIARY_NAME,BENEFICIARY_SNO);
                       //  alert(BEN_SNO);
                           document.getElementById('Beneficiary_Name').value=BEN_SNO;
                     }
                     
                    
                  
                    if(HAB_var==1)
                    {
                        HAB_SNO_len=baseres.getElementsByTagName("HAB_SNO").length; 
                        for(var i=0;i<HAB_SNO_len;i++)
                     {
                         var HAB_SNO=baseres.getElementsByTagName("HAB_SNO")[i].firstChild.nodeValue;
                         var HAB_NAME=baseres.getElementsByTagName("HAB_NAME")[i].firstChild.nodeValue;
                        
                        addOptionloadhabitationlist(document.beneficary_meter.Habitation_Name,HAB_NAME,HAB_SNO);
                        document.getElementById('Habitation_Name').value=HABITATION_SNO;
                     }
                    }
                    document.getElementById('Meter_Sno').value=METRE_SNO;
                  //  alert(METRE_SNO);
                    document.getElementById('SubDivision').value=SUB_DIV_ID;
                 //    alert(SUB_DIV_ID);
                     document.getElementById('Schemes').value=SCHEME_SNO;
                 //     alert(SCHEME_SNO);
                     document.getElementById('SCH_TYPE_ID').value=SCH_TYPE_ID;
                 //   alert(SCH_TYPE_ID);
                      document.getElementById('Beneficiary_type').value=BENEFICIARY_TYPE_ID;
                 //    alert(BENEFICIARY_TYPE_ID);
                  
                   
                  //  document.getElementById('Consumption_Category').value=BULKWS_CATEGORY;
                 //    alert(BULKWS_CATEGORY);
                   // document.getElementById('Consumption_Categoryid').value=BULKWS_CATEGORY;
                 //    alert(BULKWS_CATEGORY);
                  // document.getElementById('Metre_Location').value=METRE_LOCATION;
                //   alert(METRE_LOCATION);
                   if((BENEFICIARY_TYPE_ID!=6)&&(BENEFICIARY_TYPE_ID!=2)&&(BENEFICIARY_TYPE_ID!=3)&&(BENEFICIARY_TYPE_ID!=4)&&(BENEFICIARY_TYPE_ID!=5)&&(BENEFICIARY_TYPE_ID!=1))
                         {
                          
                           
                            document.getElementById("Tariff_Id").style.backgroundColor = 'white';
                            document.getElementById("Tariff_Id").readOnly=false;
                               
                         }
                         else
                         {
                           
                            document.getElementById("Tariff_Id").style.backgroundColor = '#ececec';
                            document.getElementById("Tariff_Id").readOnly=true;
                           
                         }

                   
                   
                      document.getElementById('Tariff_Id').value=TARIFF_RATE;
                    //  alert(METRE_LOCATION);
                     document.getElementById('Tariff_Id_val').value=TARIFF_ID;
                   //  alert(TARIFF_ID);
                    
                    
                    if((METRE_FIXED=='y')||(METRE_FIXED=='Y'))
                    {
                        document.beneficary_meter.meterfixed[0].checked=true;
                         var d=document.getElementById("prevref");
                        d.style.display="block";
                        if((BENEFICIARY_TYPE_ID==6)||(BENEFICIARY_TYPE_ID==2)||(BENEFICIARY_TYPE_ID==3)||(BENEFICIARY_TYPE_ID==4)||(BENEFICIARY_TYPE_ID==5)||(BENEFICIARY_TYPE_ID==1))
                        {
                            var f=document.getElementById("fixed");
                            f.style.display="none";
                        }
                        else
                        {
                            var f=document.getElementById("fixed");
                            f.style.display="block";
                        }
                        
                    }
                    else
                    {
                        document.beneficary_meter.meterfixed[1].checked=true;
                        for( i = 0; i < document.beneficary_meter.meterworking.length; i++ )
                        {              
                            document.beneficary_meter.meterworking[i].checked=false;
                         }
                        
                        meterworking=0;
                       
                        var d=document.getElementById("prevref");
                        
                        d.style.display="none";
                        if((BENEFICIARY_TYPE_ID==6)||(BENEFICIARY_TYPE_ID==2)||(BENEFICIARY_TYPE_ID==3)||(BENEFICIARY_TYPE_ID==4)||(BENEFICIARY_TYPE_ID==5)||(BENEFICIARY_TYPE_ID==1))
                        {
                             var f=document.getElementById("fixed");
                             f.style.display="none";
                        }
                    }
                    if((METRE_WORKING=='y')||(METRE_WORKING=='Y'))
                    {
                        document.beneficary_meter.meterworking[0].checked=true;
                    }
                    else
                    {
                        document.beneficary_meter.meterworking[1].checked=true;
                    
                    }
                 
                    document.getElementById('BENEFICIARY_TYPE_ID').value=BENEFICIARY_TYPE_ID;
                    document.getElementById('OTHERS_PRIVATE_SNO').value=OTHERS_PRIVATE_SNO;
                    document.getElementById('VILLAGE_PANCHAYAT_SNO').value=VILLAGE_PANCHAYAT_SNO;
                    document.getElementById('URBANLB_SNO').value=URBANLB_SNO;
                //    document.getElementById('Metre_Type').value=METRE_TYPE;
                     if(METRE_TYPE=='1')
                    {
                        document.beneficary_meter.Metre_Type[0].checked=true;
                    }
                    else
                    {
                        document.beneficary_meter.Metre_Type[1].checked=true;
                    
                    }
                    document.getElementById('Multiply_factor').value=MULTIPLY_FACTOR;
                    document.getElementById('Metre_init_reading').value=METRE_INIT_READING;
                    document.getElementById('Init_Reading_Record_date').value=INIT_READING_RECORD_DT;
                    document.getElementById('Allotted_Qty').value=ALLOTED_QTY;
                    document.getElementById('Min_bill_Qty').value=MIN_BILL_QTY;
                    document.getElementById('Excess_Tariff_Rate').value=EXCESS_TARIFF_RATE;
                    document.getElementById('Service_Connection').value=SERVICE_CON_NO;
                    document.getElementById('Service_Connection_date').value=SEVICE_CONN_DATE;
                   
                    if((ALLOTED_FLG=="y")||(ALLOTED_FLG=="Y"))
                    {
                        document.beneficary_meter.Applicableval[0].checked=true;
                        var d=document.getElementById("fixed");
                        d.style.display="block";
                      
                    }
                    if((ALLOTED_FLG=="n")||(ALLOTED_FLG=="N"))
                    {
                        document.beneficary_meter.Applicableval[1].checked=true;
                        var d=document.getElementById("fixed");
                        d.style.display="none";
                      
                    }
                    if((BENEFICIARY_TYPE_ID==6)||(BENEFICIARY_TYPE_ID==2)||(BENEFICIARY_TYPE_ID==3)||(BENEFICIARY_TYPE_ID==4)||(BENEFICIARY_TYPE_ID==5)||(BENEFICIARY_TYPE_ID==1))
                    {	
	                    if((ALLOTED_FLG=="n")||(ALLOTED_FLG=="N"))
	                    {
	                        document.beneficary_meter.Applicableval[1].checked=true;
	                        var d=document.getElementById("fixed");
	                        d.style.display="none";
	                      
	                    }
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
function refresh()
{
    document.getElementById("cmdadd").style.display='block';
    document.getElementById("cmdupdate").style.display='block';
    document.getElementById("cmddelete").style.display='block';
    document.getElementById("cmdadd").style.display='inline';
    document.getElementById("cmdupdate").style.display='inline';
    document.getElementById("cmddelete").style.display='inline';
    document.getElementById("Meter_Sno").value="";
    document.getElementById("Beneficiary_type").value="";
    document.getElementById("Beneficiary_Name").value="";
    document.getElementById("Habitation_Name").value="";
    document.getElementById("Beneficiary_Name").value=0;
    document.getElementById("Consumption_Category").value="";
 //   document.getElementById("Multi_WS_Category").value="";
    document.getElementById("Tariff_Id").value=1;
    document.getElementById("SubDivision").value="";
    document.getElementById("Schemes").value="";
    document.getElementById("Metre_Location").value="";
    //document.getElementById("meterfixed").value="y";
   // document.getElementById("meterworking").value="";
   document.getElementById("Metre_Location").value="";
   for( i = 0; i < document.beneficary_meter.meterworking.length; i++ )
    {
        document.beneficary_meter.meterworking[i].checked=false;
    }
 for( i = 0; i < document.beneficary_meter.meterfixed.length; i++ )
 {
      document.beneficary_meter.meterfixed[i].checked=false;
 }    
  //  document.getElementById("Metre_Type").value="";
   for( i = 0; i < document.beneficary_meter.Metre_Type.length; i++ )
 {
      document.beneficary_meter.Metre_Type[i].checked=false;
 }  
    document.getElementById("Multiply_factor").value=1;
    document.getElementById("Metre_init_reading").value="";
    document.getElementById("Init_Reading_Record_date").value="";
    document.getElementById("Allotted_Qty").value="";
    document.getElementById("Min_bill_Qty").value="";
    document.getElementById("Excess_Tariff_Rate").value=1;
    document.getElementById("Service_Connection").value="";
    document.getElementById("Service_Connection_date").value="";
    
    document.getElementById("Tariff_Id_val").value="";
    document.getElementById("SCH_TYPE_ID").value="";
    document.getElementById("BENEFICIARY_TYPE_ID").value="";
    document.getElementById("OTHERS_PRIVATE_SNO").value="";
    document.getElementById("VILLAGE_PANCHAYAT_SNO").value="";
    document.getElementById("URBANLB_SNO").value="";
    
    var tb=document.getElementById("getvaluerows");
    var t=tb.rows.length  
    for(var i=t-1;i>=0;i--)
    {
          tb.deleteRow(i);
    } 
    
    
  
}
function exitwindow()
{
    window.close();
}

/*function meterdisplay(checkval)
{
    if(checkval=="yes")
    {
      var d=document.getElementById("prevref");
      d.style.display="block";
    }
    else
    {
        var d=document.getElementById("prevref");
        d.style.display="none";
    }
    
}*/


/*function clearval()
{
   
     document.beneficary_meter.Beneficiary_Name.length=1;
}*/
function subdivision()
{
  //  var Multi_WS_Category=document.getElementById('Multi_WS_Category').value; 
    var subdivisionid=document.getElementById('SubDivision').value;
     
   
        var xmlhttp=GetXmlHttpObject();
        if (xmlhttp==null)
        {
             alert ("Your browser does not support AJAX!");
            return;
        }
        url="../../../../../Pms_Dcb_Beneficiary_metre?command=subdivisiondis&subdivisionid="+subdivisionid;
      //  alert(url);
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange= function()
            {
                 stateChangedsubdivisiondis(xmlhttp);
            }
    xmlhttp.send(null);
    
  
}

function stateChangedsubdivisiondis(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(xmlhttp.status==200)
        {
             if(commandres=="subdivisiondis")
            {
                if(flagres=='success')
                {
                    
                   
                         var OFFICE_ID=baseres.getElementsByTagName("OFFICE_ID")[0].firstChild.nodeValue;
                         var OFFICE_NAME=baseres.getElementsByTagName("OFFICE_NAME")[0].firstChild.nodeValue;
                         document.getElementById('Metre_Location').value=OFFICE_NAME;
                }
                 else
                {
                    alert("Not Loaded");
                }
            }
        }
    }
}
function Schemesval()
{
   // var Multi_WS_Category=document.getElementById('Multi_WS_Category').value; 
    var Schemesid=document.getElementById('Schemes').value;
   //alert(Multi_WS_Category);
  // alert(Schemes);
      
   
        var xmlhttp=GetXmlHttpObject();
        if (xmlhttp==null)
        {
             alert ("Your browser does not support AJAX!");
            return;
        }
        url="../../../../../Pms_Dcb_Beneficiary_metre?command=Schemesval&Schemesid="+Schemesid;
       // alert(url);
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange= function()
            {
                 stateChangedschemedis(xmlhttp);
            }
        xmlhttp.send(null);
    
    
       
   
}
function stateChangedschemedis(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(xmlhttp.status==200)
        {
             if(commandres=="Schemesval")
            {
                if(flagres=='success')
                {
                    
                   
                         var SCH_SNO=baseres.getElementsByTagName("SCH_SNO")[0].firstChild.nodeValue;
                         var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[0].firstChild.nodeValue;
                         var SCH_TYPE_ID=baseres.getElementsByTagName("SCH_TYPE_ID")[0].firstChild.nodeValue;
                         document.getElementById('Metre_Location').value=SCH_NAME;
                         document.getElementById('SCH_TYPE_ID').value=SCH_TYPE_ID;
                }
                 else
                {
                    alert("Not Loaded");
                }
            }
        }
    }
}

function loadlocation()
{
    
        document.getElementById("Metre_Location").value="";
        document.getElementById("Metre_Location").style.backgroundColor = 'white';
        document.getElementById("Metre_Location").readOnly=false;
    
   /* else
    {
        document.getElementById("Metre_Location").style.backgroundColor = '#ececec';
        document.getElementById("Metre_Location").readOnly=true;
    }*/
    
}   

function validate()
{

    
    
    var Beneficiary_type=document.getElementById("Beneficiary_type").value;
    var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
    var Tariff_Id=document.getElementById("Tariff_Id").value;
    var SubDivision=document.getElementById("SubDivision").value;
    var Schemes=document.getElementById("Schemes").value;
    var Metre_Location=document.getElementById("Metre_Location").value;
    var meterfixed=document.getElementById("meterfixed").value;
	var meterworking=document.getElementById("meterworking").value;
    var Metre_init_reading=document.getElementById("Metre_init_reading").value;
    var Excess_Tariff_Rate=document.getElementById("Excess_Tariff_Rate").value;
    var Metre_Type=document.getElementById("Metre_Type").value;
	 flagvar=0;
	for( i = 0; i < document.beneficary_meter.meterworking.length; i++ )
         {
             if(document.beneficary_meter.meterworking[i].checked)
             {
                 flagvar=1;
                 meterworking = document.beneficary_meter.meterworking[i].value;
                 //alert(meterworking);
                 
   
            }
            
        }
		
		flag=0;
	for( i = 0; i < document.beneficary_meter.meterfixed.length; i++ )
         {
             if(document.beneficary_meter.meterfixed[i].checked)
             {
                 flag=1;
                 meterfixed = document.beneficary_meter.meterfixed[i].value;
   
            }
            
        }
	for( i = 0; i < document.beneficary_meter.Metre_Type.length; i++ )
         {
             if(document.beneficary_meter.Metre_Type[i].checked)
             {
            	 flagmetre=1;
                 Metre_Type = document.beneficary_meter.Metre_Type[i].value;
               // alert(Metre_Type);
                // alert(flagmetre);
   
            }
            
        }	
    
    if(Beneficiary_type=="")
    {
        alert("Enter Beneficiary Type");
        return false;
    }	
     if(Beneficiary_Name=="")
    {
        alert("Enter Beneficiary Name");
        return false;
    }
     if(SubDivision=="")
    {
        alert("Select Sub Division");
        return false;
    }
    if(Schemes=="")
    {
        alert("Select Schemes");
        return false;
    }
   
    
    
   
  if(Metre_Location=="")
    {
        alert("Enter Location");
        return false;
    }
    if(Tariff_Id=="")
    {
        alert("Enter Tariff Rate");
        return false;
    }
	
   if(flag==0)
   {
        alert("Select Meter Fixed");
        return false;
    }
   if(meterfixed=="y")
    {
        if(flagvar==0)
        {
            alert("Select Meter Working");
            return false;
        }
        if(flagmetre==0)
        {	
     	   alert("Enter Metre Type");
            return false;
        }
      } 
       if((meterfixed=="y")&&(meterworking=="y"))
        {   
           
            if(Metre_init_reading=="")
            {
                  alert("Enter Metre Initial Reading");
                  return false;
            }
           
      }
        
     
    
 if((Beneficiary_type!=6)&&(Beneficiary_type!=2)&&(Beneficiary_type!=3)&&(Beneficiary_type!=4)&&(Beneficiary_type!=5)&&(Beneficiary_type!=1))
    {
       
       if(Excess_Tariff_Rate=="")
        {
           // alert("Enter Excess_Tariff_Rate");
            //return false;
            return true;
        }
        else
        {
            return true;
        }
        
    }
    else
    {
        return true;
    }
}
function loadhabitationlist()
{
    var Beneficiary_type=document.getElementById("Beneficiary_type").value;
    if(Beneficiary_type==6)
    {
    var hablist=document.getElementById('Beneficiary_Name').value; 
  
    var xmlhttp=GetXmlHttpObject();
        if (xmlhttp==null)
        {
             alert ("Your browser does not support AJAX!");
            return;
        }
        url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadhabitationlist&hablist="+hablist;
       // alert(url);
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadhabitationlist(xmlhttp);
            }
        xmlhttp.send(null);
        }
         
}
function stateChangedloadhabitationlist(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        document.beneficary_meter.Habitation_Name.length=1;
        HAB_SNO_len=baseres.getElementsByTagName("HAB_SNO").length;
        if(xmlhttp.status==200)
        {
             if(commandres=="loadhabitationlist")
            {
                if(flagres=='success')
                {
                    
                    for(var i=0;i<HAB_SNO_len;i++)
                     {
                       
                   
                         var HAB_SNO=baseres.getElementsByTagName("HAB_SNO")[i].firstChild.nodeValue;
                         var HAB_NAME=baseres.getElementsByTagName("HAB_NAME")[i].firstChild.nodeValue;
                         addOptionloadhabitationlist(document.beneficary_meter.Habitation_Name,HAB_NAME,HAB_SNO);
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
function addOptionloadhabitationlist(selectbox,text,value)
{
var optn = document.createElement("OPTION");
optn.text = text;
optn.value = value;
selectbox.options.add(optn);
}


function loadhabname()
{
    var habnameval=document.getElementById("Beneficiary_type").value;
    if(habnameval==6)
    {
        var bentypeval=document.getElementById("Habitation_Name").value;
     //   alert(bentypeval);
        var xmlhttp=GetXmlHttpObject();
        if (xmlhttp==null)
        {
             alert ("Your browser does not support AJAX!");
            return;
        }
        url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadhabname&bentypeval="+bentypeval;
       // alert(url);
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadhabname(xmlhttp);
            }
        xmlhttp.send(null);
    }
    
}
function stateChangedloadhabname(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        
        if(xmlhttp.status==200)
        {
             if(commandres=="loadhabname")
            {
                if(flagres=='success')
                {
                    
                   
                         var HAB_SNO=baseres.getElementsByTagName("HAB_SNO")[0].firstChild.nodeValue;
                         var HAB_NAME=baseres.getElementsByTagName("HAB_NAME")[0].firstChild.nodeValue;
                        document.getElementById("Metre_Location").style.backgroundColor = '#ececec';
                        document.getElementById("Metre_Location").readOnly=false;
                        document.getElementById("Metre_Location").value=HAB_NAME;
                }
                 else
                {
                    alert("Not Loaded");
                }
            }
        }
    }
}
/*function loadschname()
{
    var BENEFICIARY_TYPE=document.getElementById('Beneficiary_type').value;
    if((BENEFICIARY_TYPE==5)||(BENEFICIARY_TYPE==4)||(BENEFICIARY_TYPE==3)||(BENEFICIARY_TYPE==2)||(BENEFICIARY_TYPE==1))
    {
    schvalue=document.getElementById('Schemes').value;
   // alert(schvalue);
    // alert(BENEFICIARY_TYPE);
     var xmlhttp=GetXmlHttpObject();
        if (xmlhttp==null)
        {
             alert ("Your browser does not support AJAX!");
            return;
        }
        url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadschname&schvalue="+schvalue;
       // alert(url);
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadschname(xmlhttp);
            }
        xmlhttp.send(null);
    }
   
    else
    
    {
     
        document.getElementById("Metre_Location").value="";
        document.getElementById("Metre_Location").style.backgroundColor = 'white';
        document.getElementById("Metre_Location").readOnly=false;
    }
     
    }


function stateChangedloadschname(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        
        if(xmlhttp.status==200)
        {
             if(commandres=="loadschname")
            {
                if(flagres=='success')
                {
                    
                   
                        var SCH_SNO=baseres.getElementsByTagName("SCH_SNO")[0].firstChild.nodeValue;
                        var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[0].firstChild.nodeValue;
                        var SCH_TYPE_ID=baseres.getElementsByTagName("SCH_TYPE_ID")[0].firstChild.nodeValue;
                        document.getElementById("Metre_Location").style.backgroundColor = '#ececec';
                        document.getElementById("Metre_Location").readOnly=false;   
                        document.getElementById("Metre_Location").value=SCH_NAME;
                        //alert(SCH_TYPE_ID);
                        document.getElementById("SCH_TYPE_ID").value=SCH_TYPE_ID;
                }
                 else
                {
                    alert("Not Loaded");
                }
            }
        }
    }
}
*/
function loadschname()
{
    var BENEFICIARY_TYPE=document.getElementById('Beneficiary_type').value;
   /* if((BENEFICIARY_TYPE==5)||(BENEFICIARY_TYPE==4)||(BENEFICIARY_TYPE==3)||(BENEFICIARY_TYPE==2)||(BENEFICIARY_TYPE==1))
    {*/
    schvalue=document.getElementById('Schemes').value;
   // alert(schvalue);
    // alert(BENEFICIARY_TYPE);
     var xmlhttp=GetXmlHttpObject();
        if (xmlhttp==null)
        {
             alert ("Your browser does not support AJAX!");
            return;
        }
        url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadschname&schvalue="+schvalue;
       // alert(url);
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadschname(xmlhttp);
            }
        xmlhttp.send(null);
  // }
   
  
     
    }


function stateChangedloadschname(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        
        if(xmlhttp.status==200)
        {
             if(commandres=="loadschname")
            {
                if(flagres=='success')
                {
                    
                   
                        var SCH_SNO=baseres.getElementsByTagName("SCH_SNO")[0].firstChild.nodeValue;
                        var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[0].firstChild.nodeValue;
                        var SCH_TYPE_ID=baseres.getElementsByTagName("SCH_TYPE_ID")[0].firstChild.nodeValue;
                        
                        document.getElementById("SCH_TYPE_ID").value=SCH_TYPE_ID;
                        var Categoryid=document.getElementById("Consumption_Categoryid").value;
                        var bentypeid=document.getElementById("Beneficiary_type").value;
                        var schemeid=document.getElementById("Schemes").value;
                        var SubDivisionid=document.getElementById("SubDivision").value;
                     /*   if((Categoryid==0)&&(bentypeid<6))
                        {
                              // alert(SCH_NAME);
                                document.getElementById("Metre_Location").value=SCH_NAME;
                        }*/
                      /*  else
                        {
                              document.getElementById("Metre_Location").value="";
                        }*/
                        
                }
                 else
                {
                    alert("Not Loaded");
                }
            }
        }
    }
}

function numonly(e)
    {
        
        var unicode=e.charCode? e.charCode : e.keyCode
      //  alert(unicode);
        if (unicode!=8)//backspace
        { 
            if (unicode<45||unicode>57||unicode==47) 
                return false ;
        }
    }

function getvaluegrid()
{
     var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
    var Beneficiary_type=document.getElementById("Beneficiary_type").value;
    url="../../../../../Pms_Dcb_Beneficiary_metre?command=get&Beneficiary_type="+Beneficiary_type+"&Beneficiary_Name="+Beneficiary_Name;
    //alert("get value");
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    var tb=document.getElementById("getvaluerows");
    var t=tb.rows.length  
    for(var i=t-1;i>=0;i--)
    {
          tb.deleteRow(i);
    } 
    xmlhttp.onreadystatechange=function()
            {
                 stateChangedgrid(xmlhttp);
            }
    xmlhttp.send(null);
}

function stateChangedgrid(xmlhttp)
{
    var baseres,commandres,flagres,recordres;
    
    if(xmlhttp.readyState==4)
    {
        if(xmlhttp.status==200)
        { //alert('sathiya');
            baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
            commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
            recordfound=baseres.getElementsByTagName("recordfound")[0].firstChild.nodeValue;
            flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
            seq=0;
            if(commandres=="get")
            {
                 if(flagres=='success')
                {
                    if(recordfound==1)
                    {
                    tablebody=document.getElementById("getvaluerows");
                    var len=baseres.getElementsByTagName("METRE_SNO").length;
                  //  alert(len);
                    for(j=0;j<len;j++)
                    {
                       // alert(j);
                     var METRE_SNO=baseres.getElementsByTagName("METRE_SNO")[j].firstChild.nodeValue;
                    var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[j].firstChild.nodeValue;
                
                    var officename=baseres.getElementsByTagName("officename")[j].firstChild.nodeValue;
                var BULKWS_CATEGORY=baseres.getElementsByTagName("BULKWS_CATEGORY")[j].firstChild.nodeValue;
                    var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[j].firstChild.nodeValue;
                 //   var HABITATION_SNO=baseres.getElementsByTagName("HABITATION_SNO")[i].firstChild.nodeValue;
                    var TARIFF_RATE=baseres.getElementsByTagName("TARIFF_RATE")[j].firstChild.nodeValue;
                 //   var METRE_WORKING=baseres.getElementsByTagName("METRE_WORKING")[i].firstChild.nodeValue;
                    var METRE_FIXED=baseres.getElementsByTagName("METRE_FIXED")[j].firstChild.nodeValue;
                    var METRE_LOCATION=baseres.getElementsByTagName("METRE_LOCATION")[j].firstChild.nodeValue;
                    var BEN_TYPE_DESC=baseres.getElementsByTagName("BEN_TYPE_DESC")[j].firstChild.nodeValue;
                     var meterworking=baseres.getElementsByTagName("meterworking")[j].firstChild.nodeValue;
                    var BEN_TYPE_ID=baseres.getElementsByTagName("BEN_TYPE_ID")[j].firstChild.nodeValue;
                    var rowvalue=document.createElement("TR");
                    rowvalue.id=METRE_SNO;
                    var tabledata=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvaluesfromtable('"+METRE_SNO+"')";
                    var nameval=document.createTextNode("Edit");
                    anc.href=url;
                    anc.appendChild(nameval);
                    tabledata.appendChild(anc);
                    
                    
                  var hiddentext1=document.createElement("input");
                     hiddentext1.type="hidden";
                     hiddentext1.name="qual1";
                     hiddentext1.id="qual1";
                     hiddentext1.text="qual1";
                     hiddentext1.value=METRE_SNO;
                     tabledata.appendChild(hiddentext1);
                     
                    rowvalue.appendChild(tabledata);
                    seq++;
                    var sno=document.createElement("TD");
                    var i=document.createTextNode(seq);
                    sno.appendChild(i);
                    rowvalue.appendChild(sno);
                     
               /*     var tabledata8=document.createElement("TD");
                    var BEN_TYPE_DESC=document.createTextNode(BEN_TYPE_DESC);
                    tabledata8.appendChild(BEN_TYPE_DESC);
                    rowvalue.appendChild(tabledata8);
                    
                    var tabledata2=document.createElement("TD");
                    var BENEFICIARY_NAME=document.createTextNode(BENEFICIARY_NAME);
                    tabledata2.appendChild(BENEFICIARY_NAME);
                    rowvalue.appendChild(tabledata2);*/
                /*   if(BULKWS_CATEGORY==1)
                    {
                        document.getElementById("meterlabel").style.display='block';
                         document.getElementById("meterlabel").innerHTML='Meter Location';
                         
                    }
                    
                    if(BEN_TYPE_ID==6)
                    {
                        document.getElementById("meterlabel").style.display='block';
                         document.getElementById("meterlabel").innerHTML='Habitation name';
                         
                    }*/
                    
                    
                 //   document.getElementById("Habitationlabel").style.display='block';
                        var METRE_LOCATION_VAL=document.createElement("TD");
                        var METRE_LOCATION=document.createTextNode(METRE_LOCATION);
                        METRE_LOCATION_VAL.appendChild(METRE_LOCATION);
                        rowvalue.appendChild(METRE_LOCATION_VAL);
                     
                    var tabledata3=document.createElement("TD");
                    var officename=document.createTextNode(officename);
                    tabledata3.appendChild(officename);
                    rowvalue.appendChild(tabledata3);
                    
                     var tabledata4=document.createElement("TD");
                    var SCH_NAME=document.createTextNode(SCH_NAME);
                    tabledata4.appendChild(SCH_NAME);
                    rowvalue.appendChild(tabledata4);
                  
                    var tabledata7=document.createElement("TD");
                    var TARIFF_RATE=document.createTextNode(TARIFF_RATE);
                    tabledata7.appendChild(TARIFF_RATE);
                    rowvalue.appendChild(tabledata7);
                    
                     var tabledata5=document.createElement("TD");
                   
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
                    tabledata5.appendChild(METRE_FIXED);
                    rowvalue.appendChild(tabledata5);
                      var meterworking_val=document.createElement("TD");
                    if((meterworking=='y')||(meterworking=='Y'))
                    {
                        meterworking='Yes';
                        var meterworking=document.createTextNode(meterworking);
                    }
                    else
                     {
                        meterworking='No';
                        var meterworking=document.createTextNode(meterworking);
                    }
                    meterworking_val.appendChild(meterworking);
                    rowvalue.appendChild(meterworking_val);
                    
               
                    
                /*    if(BULKWS_CATEGORY==1)
                    {
                         document.getElementById("meterlabel").style.display='block';
                        var METRE_LOCATION_VAL=document.createElement("TD");
                        var METRE_LOCATION=document.createTextNode(METRE_LOCATION);
                        METRE_LOCATION_VAL.appendChild(METRE_LOCATION);
                        rowvalue.appendChild(METRE_LOCATION_VAL);
                        
                    }
                     else
                    {
                        document.getElementById("meterlabel").style.display='none';
                    }
                     if(BEN_TYPE_ID==6)
                    {
                         document.getElementById("Habitationlabel").style.display='block';
                        var METRE_LOCATION_VAL=document.createElement("TD");
                        var METRE_LOCATION=document.createTextNode(METRE_LOCATION);
                        METRE_LOCATION_VAL.appendChild(METRE_LOCATION);
                        rowvalue.appendChild(METRE_LOCATION_VAL);
                        
                    }
                    else
                    {
                        document.getElementById("Habitationlabel").style.display='none';
                    }
                    */
                  //  alert(BULKWS_CATEGORY);
                     
                      tablebody.appendChild(rowvalue);
                      //alert(len);
                     // alert(j);
                    }
                   }
                   else
                   {
                        alert("No metre found");
                    }
                } 
                else
                {
                   alert("Not success");
                }
            }           
        }
    }
}

function habcheckdup()
{
     var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
    var Beneficiary_type=document.getElementById("Beneficiary_type").value;
    var SubDivision=document.getElementById("SubDivision").value;
    var Schemes=document.getElementById("Schemes").value;
    var Habitation_Name=document.getElementById("Habitation_Name").value;
    var Metre_Location=document.getElementById("Metre_Location").value;
    var Consumption_Category=document.getElementById("Consumption_Categoryid").value;
    if(Beneficiary_type==6)
    {
        if((Consumption_Category==0)||(Consumption_Category==1))
        {
            url="../../../../../Pms_Dcb_Beneficiary_metre?command=habcheckdup&Beneficiary_type="+Beneficiary_type+"&Consumption_Categoryid="+Consumption_Category+"&Beneficiary_Name="+Beneficiary_Name+"&SubDivision="+SubDivision+"&Schemes="+Schemes+"&Habitation_Name="+Habitation_Name;
          //  alert(url);
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange=function()
            {
                 stateChangedhabcheckdup(xmlhttp);
            }
            xmlhttp.send(null);
        }
    }
        
}
function stateChangedhabcheckdup(xmlhttp)
{
    var baseres,commandres,flagres,recordres;
    
    if(xmlhttp.readyState==4)
    {
        if(xmlhttp.status==200)
        { //alert('sathiya');
            baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
            commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
            countinsert=baseres.getElementsByTagName("countinsert")[0].firstChild.nodeValue;
            flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(commandres=="habcheckdup")
            {
                 if(flagres=='success')
                {
                    if(countinsert>0)
                    {
                       alert("This Habitation already exists for this Scheme and Sub division");
                    }
                }
            }
          }  
     }
}
function schemecheck()
{
     var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
    var Beneficiary_type=document.getElementById("Beneficiary_type").value;
    var SubDivision=document.getElementById("SubDivision").value;
    var Schemes=document.getElementById("Schemes").value;
    var Consumption_Category=document.getElementById("Consumption_Categoryid").value;
   if((Beneficiary_type==1)||(Beneficiary_type==2)||(Beneficiary_type==3)||(Beneficiary_type==4)||(Beneficiary_type==5))
   {
        if(Consumption_Category==0)
        {
            url="../../../../../Pms_Dcb_Beneficiary_metre?command=schemecheck&Beneficiary_type="+Beneficiary_type+"&Consumption_Categoryid="+Consumption_Category+"&Beneficiary_Name="+Beneficiary_Name+"&SubDivision="+SubDivision+"&Schemes="+Schemes;
          //  alert(url);
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange=function()
            {
                 stateChangedschemecheck(xmlhttp);
            }
            xmlhttp.send(null);
        }
   }
        
}              
function stateChangedschemecheck(xmlhttp)
{
    var baseres,commandres,flagres,recordres;
    
    if(xmlhttp.readyState==4)
    {
        if(xmlhttp.status==200)
        { //alert('sathiya');
            baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
            commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
            countinsert=baseres.getElementsByTagName("countinsert")[0].firstChild.nodeValue;
            flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(commandres=="schemecheck")
            {
                 if(flagres=='success')
                {
                    if(countinsert>0)
                    {
                       alert("This Metre Location already exists for this Scheme and Sub division");
                    }
                }
            }
          }  
     }
}            
        
function metercheck()
{
     var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
    var Beneficiary_type=document.getElementById("Beneficiary_type").value;
    var SubDivision=document.getElementById("SubDivision").value;
    var Schemes=document.getElementById("Schemes").value;
    var Consumption_Category=document.getElementById("Consumption_Categoryid").value;
    var Metre_Location=document.getElementById("Metre_Location").value;
   
        if((Consumption_Category==1)||(Beneficiary_type>6))
        {
            url="../../../../../Pms_Dcb_Beneficiary_metre?command=metercheck&Beneficiary_type="+Beneficiary_type+"&Consumption_Categoryid="+Consumption_Category+"&Beneficiary_Name="+Beneficiary_Name+"&SubDivision="+SubDivision+"&Schemes="+Schemes+"&Metre_Location="+Metre_Location;
          //  alert(url);
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange=function()
            {
                 stateChangedmetercheck(xmlhttp);
            }
            xmlhttp.send(null);
        }
  
        
}             

function stateChangedmetercheck(xmlhttp)
{
    var baseres,commandres,flagres,recordres;
    
    if(xmlhttp.readyState==4)
    {
        if(xmlhttp.status==200)
        { //alert('sathiya');
            baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
            commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
            countinsert=baseres.getElementsByTagName("countinsert")[0].firstChild.nodeValue;
            flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(commandres=="metercheck")
            {
                 if(flagres=='success')
                {
                    if(countinsert>0)
                    {
                       alert("This Metre Location already exists for this Scheme and Sub division");
                    }
                }
            }
          }  
     }
}   
function popup()
{
 winemp= window.open("Pms_Dcb_Beneficiary_Metre_Report.jsp","list","status=1,height=500,width=600,resizable=YES, scrollbars=yes");
        winemp.moveTo(250,250); 
        winemp.focus();
}
function districload()
{
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
     var Beneficiary_type=document.getElementById("Beneficiary_type").value;
     if(Beneficiary_type==6)
     {
        document.getElementById("district").style.display="block";
         url="../../../../../Pms_Dcb_Beneficiary_metre?command=loaddistrict&Beneficiary_type="+Beneficiary_type;
          //  alert(url);
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange=function()
            {
                 stateChangedloaddistrict(xmlhttp);
            }
            xmlhttp.send(null);
     
     }
     else
     {
         document.getElementById("district").style.display="none";
     }
     
}
function stateChangedloaddistrict(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        document.beneficary_meter.district_Name.length=1;
        DISTRICT_CODE_len=baseres.getElementsByTagName("DISTRICT_CODE").length;
        if(xmlhttp.status==200)
        {
             if(commandres=="loaddistrict")
            {
                if(flagres=='success')
                {
                    
                    for(var i=0;i<DISTRICT_CODE_len;i++)
                     {
                       
                   
                         var DISTRICT_CODE=baseres.getElementsByTagName("DISTRICT_CODE")[i].firstChild.nodeValue;
                         var DISTRICT_NAME=baseres.getElementsByTagName("DISTRICT_NAME")[i].firstChild.nodeValue;
                         addOptiondistrictlist(document.beneficary_meter.district_Name,DISTRICT_NAME,DISTRICT_CODE);
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
function addOptiondistrictlist(selectbox,text,value)
{
var optn = document.createElement("OPTION");
optn.text = text;
optn.value = value;
selectbox.options.add(optn);
}

function loadblocks()
{
     var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
     var Beneficiary_type=document.getElementById("Beneficiary_type").value;
     var district_Name=document.getElementById("district_Name").value;
     if((Beneficiary_type==6)&&(district_Name!=""))
     {
        document.getElementById("district").style.display="block";
         url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadblocks&district_Name="+district_Name;
          //  alert(url);
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange=function()
            {
                 stateChangedloadblocks(xmlhttp);
            }
            xmlhttp.send(null);
     
     }
   

}
function stateChangedloadblocks(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        document.beneficary_meter.block_Name.length=1;
        BLOCK_CODE_len=baseres.getElementsByTagName("BLOCK_SNO").length;
        if(xmlhttp.status==200)
        {
             if(commandres=="loadblocks")
            {
                if(flagres=='success')
                {
                    
                    for(var i=0;i<BLOCK_CODE_len;i++)
                     {
                       
                   
                         var BLOCK_SNO=baseres.getElementsByTagName("BLOCK_SNO")[i].firstChild.nodeValue;
                         var BLOCK_NAME=baseres.getElementsByTagName("BLOCK_NAME")[i].firstChild.nodeValue;
                         addOptionblocklist(document.beneficary_meter.block_Name,BLOCK_NAME,BLOCK_SNO);
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
function addOptionblocklist(selectbox,text,value)
{
var optn = document.createElement("OPTION");
optn.text = text;
optn.value = value;
selectbox.options.add(optn);
}
function loadbenname()
{
     var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
     var Beneficiary_type=document.getElementById("Beneficiary_type").value;
     var district_Name=document.getElementById("district_Name").value;
     var block_Name=document.getElementById("block_Name").value;
     
     if((Beneficiary_type==6)&&(district_Name!="")&&(block_Name!=""))
     {
        document.getElementById("district").style.display="block";
         url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadbenname&block_Name="+block_Name;
          //  alert(url);
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange=function()
            {
                 stateChangedloadbeneficiaryname(xmlhttp);
            }
            xmlhttp.send(null);
     
     }
}