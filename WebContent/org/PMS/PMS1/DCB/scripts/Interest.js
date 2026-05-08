
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
function loadCombo()
{
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    url="../../../../../pms_dcb_mst_tariff?command=comboload";
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
            {
                 stateChangedcombo(xmlhttp);
            }
    xmlhttp.send(null);
}
function stateChangedcombo(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(xmlhttp.status==200)
        {
             if(commandres=="comboload")
            {
                if(flagres=='success')
                {
                    
                    var charge_by_desc_len=baseres.getElementsByTagName("CHARGE_TYPE_DESC_TABLE").length;
                    for(var i=0;i<charge_by_desc_len;i++)
                     {
                         var charge_type_id_val=baseres.getElementsByTagName("CHARGE_TYPE_ID")[i].firstChild.nodeValue;
                         var charge_by_desc_val=baseres.getElementsByTagName("CHARGE_TYPE_DESC_TABLE")[i].firstChild.nodeValue;
                         addOption(document.chargedetails.charge_type_Id,charge_by_desc_val,charge_type_id_val);
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
function addOption(selectbox,text,value)
{
var optn = document.createElement("OPTION");
optn.text = text;
optn.value = value;
selectbox.options.add(optn);
}

function loadbeneficiary()
{
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    url="../../../../../pms_dcb_mst_tariff?command=loadbeneficiary";
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadbeneficiary(xmlhttp);
            }
    xmlhttp.send(null);
}
function stateChangedloadbeneficiary(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(xmlhttp.status==200)
        {
             if(commandres=="loadbeneficiary")
            {
                if(flagres=='success')
                {
                    
                    var beneficiary_desc_len=baseres.getElementsByTagName("BENEFICIARY_TYPE_DESC").length;
                    for(var i=0;i<beneficiary_desc_len;i++)
                     {
                         var Beneficiary_Type_id_val=baseres.getElementsByTagName("BEN_TYPE_ID")[i].firstChild.nodeValue;
                         var Beneficiary_Type_val=baseres.getElementsByTagName("BENEFICIARY_TYPE_DESC")[i].firstChild.nodeValue;
                         
                         addOptiondesc(document.chargedetails.Beneficiary_Type,Beneficiary_Type_val,Beneficiary_Type_id_val);
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

function loadUOM()
{
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    url="../../../../../pms_dcb_mst_tariff?command=loadUOM";
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
            {
                 stateChangedcomboUOM(xmlhttp);
            }
    xmlhttp.send(null);
}

function stateChangedcomboUOM(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(xmlhttp.status==200)
        {
             if(commandres=="loadUOM")
            {
                if(flagres=='success')
                {
                    
                    var beneficiary_desc_len=baseres.getElementsByTagName("UOM_SNO").length;
                    for(var i=0;i<beneficiary_desc_len;i++)
                     {
                         var uom_id=baseres.getElementsByTagName("UOM_SNO")[i].firstChild.nodeValue;
                         var uom_desc=baseres.getElementsByTagName("UOM_DESC")[i].firstChild.nodeValue;
                         
                         addOptiondesc(document.chargedetails.UOM,uom_desc,uom_id);
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

function doFunction(actionval)
{
   xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    //alert(actionval);
   var tariff_Id=document.getElementById("tariff_Id").value;
  //  var charge_type_Id=document.getElementById("charge_type_Id").value;
  //  var tariff_Desc=document.getElementById("tariff_Desc").value;
    var tariff_Rate=document.getElementById("tariff_Rate").value;
   // var excess_tariff_Rate=document.getElementById("excess_tariff_Rate").value;
    var Tariff_wef=document.getElementById("Tariff_wef").value;
    var UOM=document.getElementById("UOM").value;
    var Beneficiary_Type=document.getElementById("Beneficiary_Type").value;
    var status=document.getElementById("status").value;
  /*  var Tariff_Code=document.getElementById("Tariff_Code").value;
    if(Tariff_Code==null)
    {
        var Tariff_Code=0;
        
    }
    */
    if(actionval=="Add")
    {
            var valfn=validate();
            if(valfn==true)
            {
            //url="../../../../../pms_dcb_mst_tariff?command=add&charge_type_Id="+charge_type_Id+"&tariff_Desc="+tariff_Desc+"&tariff_Rate="+tariff_Rate+"&excess_tariff_Rate="+excess_tariff_Rate+"&Tariff_wef="+Tariff_wef+"&UOM="+UOM+"&Tariff_Code="+Tariff_Code;
            //url="../../../../../pms_dcb_mst_tariff?command=add&charge_type_Id="+charge_type_Id+"&tariff_Rate="+tariff_Rate+"&excess_tariff_Rate="+excess_tariff_Rate+"&Tariff_wef="+Tariff_wef+"&UOM="+UOM+"&Tariff_Code="+Tariff_Code+"&Beneficiary_Type="+Beneficiary_Type;
            url="../../../../../pms_dcb_mst_tariff?command=add&tariff_Rate="+tariff_Rate+"&Tariff_wef="+Tariff_wef+"&UOM="+UOM+"&Beneficiary_Type="+Beneficiary_Type+"&status="+status;
            //alert(url);
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange= stateChanged;
            xmlhttp.send(null);
            }
    }
   else if(actionval=="get")
   {
        
        url="../../../../../pms_dcb_mst_tariff?command=get";
       // alert("get value");
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
        
   }
   else if(actionval=="Update")
    {
        var valfn=validate();
        if(valfn==true)
        {
        // url="../../../../../pms_dcb_mst_tariff?command=update&tariff_Id="+tariff_Id+"&charge_type_Id="+charge_type_Id+"&tariff_Desc="+tariff_Desc+"&tariff_Rate="+tariff_Rate+"&excess_tariff_Rate="+excess_tariff_Rate+"&Tariff_wef="+Tariff_wef+"&UOM="+UOM+"&Tariff_Code="+Tariff_Code;
      //  url="../../../../../pms_dcb_mst_tariff?command=update&tariff_Id="+tariff_Id+"&charge_type_Id="+charge_type_Id+"&tariff_Rate="+tariff_Rate+"&excess_tariff_Rate="+excess_tariff_Rate+"&Tariff_wef="+Tariff_wef+"&UOM="+UOM+"&Tariff_Code="+Tariff_Code+"&Beneficiary_Type="+Beneficiary_Type;
          url="../../../../../pms_dcb_mst_tariff?command=update&tariff_Id="+tariff_Id+"&tariff_Rate="+tariff_Rate+"&Tariff_wef="+Tariff_wef+"&UOM="+UOM+"&Beneficiary_Type="+Beneficiary_Type+"&status="+status;
        //alert("update value");
        url=url+"&sid="+Math.random();
        //alert(url);
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
       }
    }
    else if(actionval=="Delete")
    {
       // url="../../../../../pms_dcb_mst_tariff?command=delete&tariff_Id="+tariff_Id+"&charge_type_Id="+charge_type_Id+"&tariff_Rate="+tariff_Rate+"&excess_tariff_Rate="+excess_tariff_Rate+"&Tariff_wef="+Tariff_wef+"&UOM="+UOM+"&Tariff_Code="+Tariff_Code+"&Beneficiary_Type="+Beneficiary_Type;
        //url="../../../../../pms_dcb_mst_tariff?command=delete&tariff_Id="+tariff_Id+"&charge_type_Id="+charge_type_Id+"&tariff_Desc="+tariff_Desc+"&tariff_Rate="+tariff_Rate+"&excess_tariff_Rate="+excess_tariff_Rate+"&Tariff_wef="+Tariff_wef+"&UOM="+UOM+"&Tariff_Code="+Tariff_Code;
       // alert("delete value");
        url="../../../../../pms_dcb_mst_tariff?command=delete&tariff_Id="+tariff_Id+"&tariff_Rate="+tariff_Rate+"&Tariff_wef="+Tariff_wef+"&UOM="+UOM+"&Beneficiary_Type="+Beneficiary_Type;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
    }
    
    
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
                    alert("Successfully Added");
                    tablebody=document.getElementById("getvaluerows");
                    var tariff_Id=baseres.getElementsByTagName("tariff_Id")[0].firstChild.nodeValue;
                    var datares=baseres.getElementsByTagName("data")[0];
                    var dataresone=baseres.getElementsByTagName("dataone")[0];
                    var datarestwo=baseres.getElementsByTagName("datatwo")[0];
                      
                  //  var charge_type_Id=datares.getElementsByTagName("charge_type_Id")[0].firstChild.nodeValue;
                   // var charge_type_Id_desc=datares.getElementsByTagName("charge_type_Id_desc")[0].lastChild.nodeValue;
                    
                    var Beneficiary_Type_id=dataresone.getElementsByTagName("Beneficiary_Type_id")[0].firstChild.nodeValue;
                    var Beneficiary_Type_desc=dataresone.getElementsByTagName("Beneficiary_Type_desc")[0].firstChild.nodeValue;
                    
                    var Uom_id=datarestwo.getElementsByTagName("Uom_id")[0].firstChild.nodeValue;
                    var uom_val_desc=datarestwo.getElementsByTagName("uom_val_desc")[0].firstChild.nodeValue;
                    
                  //  var Uom_id=datarestwo.getElementsByTagName("Uom_id")[0].firstChild.nodeValue;
                 //   var uom_val_desc=datarestwo.getElementsByTagName("uom_val_desc")[0].firstChild.nodeValue;
                 //   var charge_type_Id=baseres.getElementsByTagName("charge_type_Id")[0].firstChild.nodeValue;
               //     var charge_type_Id_desc=datares.getElementsByTagName("charge_type_Id_desc")[0].lastChild.nodeValue;
                   // var tariff_Desc=baseres.getElementsByTagName("tariff_Desc")[0].firstChild.nodeValue;
                    var tariff_Rate=baseres.getElementsByTagName("tariff_Rate")[0].firstChild.nodeValue;
                   // var excess_tariff_Rate=baseres.getElementsByTagName("excess_tariff_Rate")[0].firstChild.nodeValue;
                    var Tariff_wef=baseres.getElementsByTagName("Tariff_wef")[0].firstChild.nodeValue;
                 //   var Uom=baseres.getElementsByTagName("Uom")[0].firstChild.nodeValue;
                   // var Tariff_code=baseres.getElementsByTagName("Tariff_code")[0].firstChild.nodeValue;
                 //   var Beneficiary_Type=baseres.getElementsByTagName("Beneficiary_Type")[0].firstChild.nodeValue;
                    var status=baseres.getElementsByTagName("activestatus")[0].firstChild.nodeValue;
                    //alert("value1"+regnores);
                   // alert("value2"+empres);
                    var tbody=document.getElementById("getvaluerows");
                    var rowvalue=document.createElement("TR");
                    rowvalue.id=tariff_Id;
                    var tabledata=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvaluesfromtable('"+tariff_Id+"')";
                    var nameval=document.createTextNode("Edit");
                    anc.href=url;
                    anc.appendChild(nameval);
                    tabledata.appendChild(anc);
                    rowvalue.appendChild(tabledata);
                    
                    var tabledata0=document.createElement("TD");
                    var tariff_Id=document.createTextNode(tariff_Id);
                    tabledata0.appendChild(tariff_Id);
                    rowvalue.appendChild(tabledata0);
                    
                 /*    var tabledata7=document.createElement("TD");
                    var Tariff_code=document.createTextNode(Tariff_code);
                    tabledata7.appendChild(Tariff_code);
                    rowvalue.appendChild(tabledata7);*/
                   
                      
                    var tabledata4A=document.createElement("TD");
                    var Beneficiary_Type=document.createTextNode(Beneficiary_Type_desc);
                    tabledata4A.appendChild(Beneficiary_Type);
                    rowvalue.appendChild(tabledata4A);
                    
                     var hiddentext1=document.createElement("input");
                     hiddentext1.type="hidden";
                     hiddentext1.name="qual1";
                     hiddentext1.id="qual1";
                     hiddentext1.text="qual1";
                     hiddentext1.value=Beneficiary_Type_id;
                     tabledata4A.appendChild(hiddentext1);
                    
                   /* var tabledata1=document.createElement("TD");
                    var charge_type_Id_desc=document.createTextNode(charge_type_Id_desc);
                    tabledata1.appendChild(charge_type_Id_desc);
                    rowvalue.appendChild(tabledata1);
                    
                      var hiddentext=document.createElement("input");
                     hiddentext.type="hidden";
                     hiddentext.name="qual";
                     hiddentext.id="qual";
                     hiddentext.text="qual";
                     hiddentext.value=charge_type_Id;
                     tabledata1.appendChild(hiddentext);*/

                    
                 /*    var cell2=document.createElement("TD");
                     var hiddentext=document.createElement("input");
                     hiddentext.type="hidden";
                     hiddentext.size="10";
                     hiddentext.name="qual";
                     hiddentext.id="qual";
                     hiddentext.text="qual";
                    cell2.appendChild(hiddentext);
                    rowvalue.appendChild(cell2); 
                   */ 
                   /* var tabledata2=document.createElement("TD");
                    var tariff_Desc=document.createTextNode(tariff_Desc);
                    tabledata2.appendChild(tariff_Desc);
                    rowvalue.appendChild(tabledata2);*/
                    
                    var tabledata3=document.createElement("TD");
                    var tariff_Rate=document.createTextNode(tariff_Rate);
                    tabledata3.appendChild(tariff_Rate);
                    rowvalue.appendChild(tabledata3);
                     
               /*     var tabledata4=document.createElement("TD");
                    var excess_tariff_Rate=document.createTextNode(excess_tariff_Rate);
                    tabledata4.appendChild(excess_tariff_Rate);
                    rowvalue.appendChild(tabledata4);
                 */
                    
                    var tabledata5=document.createElement("TD");
                    var Tariff_wef=document.createTextNode(Tariff_wef);
                    tabledata5.appendChild(Tariff_wef);
                    rowvalue.appendChild(tabledata5);
                     
                    var tabledata6=document.createElement("TD");
                    var Uom=document.createTextNode(uom_val_desc);
                    tabledata6.appendChild(Uom);
                    rowvalue.appendChild(tabledata6);
                    
                    var hiddentext2=document.createElement("input");
                     hiddentext2.type="hidden";
                     hiddentext2.name="qual3";
                     hiddentext2.id="qual3";
                     hiddentext2.text="qual3";
                     hiddentext2.value=Uom_id;
                     tabledata6.appendChild(hiddentext2);
                     
                    var tabledatastatus=document.createElement("TD");
                    var status=document.createTextNode(status);
                    tabledatastatus.appendChild(status);
                    rowvalue.appendChild(tabledatastatus);
                     
                    tablebody.appendChild(rowvalue);
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
                    var len=baseres.getElementsByTagName("tariff_Id").length;
                    //alert(len);
                    for(var i=0;i<len;i++)
                    {
                      var tariff_Id=baseres.getElementsByTagName("tariff_Id")[i].firstChild.nodeValue;
                      var datares=baseres.getElementsByTagName("data")[i];
                      var dataresone=baseres.getElementsByTagName("dataone")[i];
                      var datarestwo=baseres.getElementsByTagName("datatwo")[i];
                      
                  /*  var charge_type_Id=datares.getElementsByTagName("charge_type_Id")[0].firstChild.nodeValue;
                    var charge_type_Id_desc=datares.getElementsByTagName("charge_type_Id_desc")[0].lastChild.nodeValue;*/
                    
                    var Beneficiary_Type_id=dataresone.getElementsByTagName("Beneficiary_Type_id")[0].firstChild.nodeValue;
                    var Beneficiary_Type_desc=dataresone.getElementsByTagName("Beneficiary_Type_desc")[0].firstChild.nodeValue;
                    
                    var Uom_id=datarestwo.getElementsByTagName("Uom_id")[0].firstChild.nodeValue;
                    var uom_val_desc=datarestwo.getElementsByTagName("uom_val_desc")[0].firstChild.nodeValue;
                    
                      //var charge_type_Id=baseres.getElementsByTagName("charge_type_Id")[i].firstChild.nodeValue;
                    //  var tariff_Desc=baseres.getElementsByTagName("tariff_Desc")[i].firstChild.nodeValue;
                      var tariff_Rate=baseres.getElementsByTagName("tariff_Rate")[i].firstChild.nodeValue;
                     // var excess_tariff_Rate=baseres.getElementsByTagName("excess_tariff_Rate")[i].firstChild.nodeValue;
                      var Tariff_wef=baseres.getElementsByTagName("Tariff_wef")[i].firstChild.nodeValue;
                      var ag=Tariff_wef.split("-");
                      var datefor=ag[2]+"/"+ag[1]+"/"+ag[0]; 
                   //   var Uom=baseres.getElementsByTagName("Uom")[i].firstChild.nodeValue;
                 //     var Tariff_code=baseres.getElementsByTagName("Tariff_code")[i].firstChild.nodeValue;
                      var activestatus=baseres.getElementsByTagName("activestatus")[i].firstChild.nodeValue;
                   //   var Beneficiary_Type=baseres.getElementsByTagName("Beneficiary_Type")[i].firstChild.nodeValue;
                    //  var charge_type_Id=baseres.getElementsByTagName("charge_type_Id")[i].firstChild.nodeValue;
                     // var charge_type_Id_desc=baseres.getElementsByTagName("charge_type_Id_desc")[i].firstChild.nodeValue;
                   //   alert("charge_type_Id"+charge_type_Id);
                    //  var beneficiary_val=baseres.getElementsByTagName("beneficiary_val")[i].firstChild.nodeValue;
                    //  var uom_val=baseres.getElementsByTagName("uom_val")[i].firstChild.nodeValue;
                     // alert(paid_by_id);
                     // alert(paid_by_desc);
                      var rowvalue=document.createElement("TR");
                      rowvalue.id=tariff_Id;
                      var tabledata=document.createElement("TD");
                      var anc=document.createElement("A");
                      var url="javascript:loadvaluesfromtable('" +tariff_Id+"')";
                      anc.href=url;
                      var nameval=document.createTextNode("Edit");
                      anc.appendChild(nameval);
                      tabledata.appendChild(anc);
                      rowvalue.appendChild(tabledata);
                      
                      
                   var tabledata0=document.createElement("TD");
                    var tariff_Id=document.createTextNode(tariff_Id);
                    tabledata0.appendChild(tariff_Id);
                    rowvalue.appendChild(tabledata0);
                    
                                      
                /*   var tabledata7=document.createElement("TD");
                    var Tariff_code=document.createTextNode(Tariff_code);
                    tabledata7.appendChild(Tariff_code);
                    rowvalue.appendChild(tabledata7);*/
                    
                       var tabledata4A=document.createElement("TD");
                    var Beneficiary_Type=document.createTextNode(Beneficiary_Type_desc);
                    tabledata4A.appendChild(Beneficiary_Type);
                    
                    
                    var hiddentext1=document.createElement("input");
                     hiddentext1.type="hidden";
                     hiddentext1.name="qual1";
                     hiddentext1.id="qual1";
                     hiddentext1.text="qual1";
                     hiddentext1.value=Beneficiary_Type_id;
                     tabledata4A.appendChild(hiddentext1);
                     
                     rowvalue.appendChild(tabledata4A);
                    
                /*    var tabledata1=document.createElement("TD");
                    var charge_type_Id_desc=document.createTextNode(charge_type_Id_desc);
                    tabledata1.appendChild(charge_type_Id_desc);
                    
                     var hiddentext=document.createElement("input");
                     hiddentext.type="hidden";
                     hiddentext.name="qual";
                     hiddentext.id="qual";
                     hiddentext.text="qual";
                     hiddentext.value=charge_type_Id;
                     tabledata1.appendChild(hiddentext);
                     
                    rowvalue.appendChild(tabledata1);*/
                    
               
                    
                     
                   /* var tabledata2=document.createElement("TD");
                    var tariff_Desc=document.createTextNode(tariff_Desc);
                    tabledata2.appendChild(tariff_Desc);
                    rowvalue.appendChild(tabledata2);*/
                    
                     
                     
                    var tabledata3=document.createElement("TD");
                    var tariff_Rate=document.createTextNode(tariff_Rate);
                    tabledata3.appendChild(tariff_Rate);
                    rowvalue.appendChild(tabledata3);
                     
                  /*  var tabledata4=document.createElement("TD");
                    var excess_tariff_Rate=document.createTextNode(excess_tariff_Rate);
                    tabledata4.appendChild(excess_tariff_Rate);
                    rowvalue.appendChild(tabledata4);*/
                    
                 
                     
                    var tabledata5=document.createElement("TD");
                    var Tariff_wef=document.createTextNode(datefor);
                    tabledata5.appendChild(Tariff_wef);
                    rowvalue.appendChild(tabledata5);
                     
                    var tabledata6=document.createElement("TD");
                    var Uom=document.createTextNode(uom_val_desc);
                    tabledata6.appendChild(Uom);
                   
                     
                    var hiddentext2=document.createElement("input");
                     hiddentext2.type="hidden";
                     hiddentext2.name="qual3";
                     hiddentext2.id="qual3";
                     hiddentext2.text="qual3";
                     hiddentext2.value=Uom_id;
                     tabledata6.appendChild(hiddentext2);
                      
                       rowvalue.appendChild(tabledata6);
                      
                      
                      var tabledata9=document.createElement("TD");
                    var activestatus=document.createTextNode(activestatus);
                    tabledata9.appendChild(activestatus);
                    rowvalue.appendChild(tabledata9);
                    
                      tablebody.appendChild(rowvalue);
                      
                    }
                   
                } 
                else
                {
                   /*tablebody=document.getElementById("getvalue");
                   recordres=baseres.getElementsByTagName("record")[0].firstChild.nodeValue;
                   alert(recordres);
                   var rowvalue=document.createElement("TR");
                   var tabledata=document.createElement("<td colspan='9' align='center'>");
               
                   var nameval=document.createTextNode("No records found");
                   tabledata.appendChild(nameval);
                   rowvalue.appendChild(tabledata);
                   
                   tablebody.appendChild(rowvalue);
                   */
                   alert("Not success");
                }
            }
            else if(commandres=="update")
            {
                if(flagres=='success')
                {
                    alert( 'Successfully Updated');
                    var datares=baseres.getElementsByTagName("data")[0];
                      var dataresone=baseres.getElementsByTagName("dataone")[0];
                      var datarestwo=baseres.getElementsByTagName("datatwo")[0];
                      
                  /*  var charge_type_Id=datares.getElementsByTagName("charge_type_Id")[0].firstChild.nodeValue;
                    var charge_type_Id_desc=datares.getElementsByTagName("charge_type_Id_desc")[0].lastChild.nodeValue;*/
                    
                    var Beneficiary_Type_id=dataresone.getElementsByTagName("Beneficiary_Type_id")[0].firstChild.nodeValue;
                    var Beneficiary_Type_desc=dataresone.getElementsByTagName("Beneficiary_Type_desc")[0].firstChild.nodeValue;
                    
                    var Uom_id=datarestwo.getElementsByTagName("Uom_id")[0].firstChild.nodeValue;
                    var uom_val_desc=datarestwo.getElementsByTagName("uom_val_desc")[0].firstChild.nodeValue;
                     
                    var tariff_Id=baseres.getElementsByTagName("tariff_Id")[0].firstChild.nodeValue;
                    //var charge_type_Id=baseres.getElementsByTagName("charge_type_Id")[0].firstChild.nodeValue;
                    
                 //    var charge_type_Id=datares.getElementsByTagName("charge_type_Id")[0].firstChild.nodeValue;
                  //  var charge_type_Id_desc=datares.getElementsByTagName("charge_type_Id_desc")[0].lastChild.nodeValue;
                 //   var tariff_Desc=baseres.getElementsByTagName("tariff_Desc")[0].firstChild.nodeValue;
                    var tariff_Rate=baseres.getElementsByTagName("tariff_Rate")[0].firstChild.nodeValue;
                 //   var excess_tariff_Rate=baseres.getElementsByTagName("excess_tariff_Rate")[0].firstChild.nodeValue;
                    var Tariff_wef=baseres.getElementsByTagName("Tariff_wef")[0].firstChild.nodeValue;
                  //  var Uom=baseres.getElementsByTagName("Uom")[0].firstChild.nodeValue;
                   // var Tariff_code=baseres.getElementsByTagName("Tariff_code")[0].firstChild.nodeValue;
                  //   var Beneficiary_Type=baseres.getElementsByTagName("Beneficiary_Type")[0].firstChild.nodeValue;
                   // alert("value1"+regnores);
                   // alert("value2"+empres);
                    
                     var status=baseres.getElementsByTagName("activestatus")[0].firstChild.nodeValue;
                
                  var rvar=document.getElementById(tariff_Id);
                  var rcells=rvar.cells;
              
                 rcells.item(1).firstChild.nodeValue=tariff_Id;
                 rcells.item(2).firstChild.nodeValue=Beneficiary_Type_desc;
                 rcells.item(2).lastChild.value=Beneficiary_Type_id;
                 // rcells.item(3).firstChild.nodeValue=charge_type_Id_desc;
                //  rcells.item(3).lastChild.value=charge_type_Id;
                  rcells.item(3).firstChild.nodeValue=tariff_Rate;
               //   rcells.item(4).firstChild.nodeValue=excess_tariff_Rate;
                 
                  rcells.item(4).firstChild.nodeValue=Tariff_wef;
                  rcells.item(5).firstChild.nodeValue=uom_val_desc;
                   rcells.item(5).lastChild.value=Uom_id;
                   rcells.item(6).firstChild.nodeValue=status;
                  
                  
                 

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
                    alert('Successfully Deleted');
                   var tariff_Id=baseres.getElementsByTagName("tariff_Id")[0].firstChild.nodeValue;
               //     var charge_type_Id=baseres.getElementsByTagName("charge_type_Id")[0].firstChild.nodeValue;
                  //  var tariff_Desc=baseres.getElementsByTagName("tariff_Desc")[0].firstChild.nodeValue;
                    var tariff_Rate=baseres.getElementsByTagName("tariff_Rate")[0].firstChild.nodeValue;
                 //   var excess_tariff_Rate=baseres.getElementsByTagName("excess_tariff_Rate")[0].firstChild.nodeValue;
                    var Tariff_wef=baseres.getElementsByTagName("Tariff_wef")[0].firstChild.nodeValue;
                    var Uom=baseres.getElementsByTagName("Uom")[0].firstChild.nodeValue;
                  //  var Tariff_code=baseres.getElementsByTagName("Tariff_code")[0].firstChild.nodeValue;
                     var Beneficiary_Type=baseres.getElementsByTagName("Beneficiary_Type")[0].firstChild.nodeValue;
                    var tbody=document.getElementById("existing");
                    var r=document.getElementById(tariff_Id);
                    var ri=r.rowIndex;
                    tbody.deleteRow(ri);
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
    var rvar=document.getElementById(r);
    var rcells=rvar.cells;
   
    document.getElementById('tariff_Id').value=rcells.item(1).firstChild.nodeValue;
  
   // document.getElementById("charge_type_Id").value=rcells.item(3).lastChild.value;
  
    document.getElementById('tariff_Rate').value=rcells.item(3).firstChild.nodeValue;
  //  document.getElementById('excess_tariff_Rate').value=rcells.item(5).firstChild.nodeValue;
 //   alert(rcells.item(3).lastChild.nodeValue);
  
   // document.getElementById("Beneficiary_Type").options[document.getElementById("Beneficiary_Type").selectedIndex].text=rcells.item(6).firstChild.nodeValue;
    document.getElementById('Beneficiary_Type').value=rcells.item(2).lastChild.value;
    document.getElementById('Tariff_wef').value=rcells.item(4).firstChild.nodeValue;
  
    document.getElementById('Uom').value=rcells.item(5).lastChild.value;
    
     document.getElementById('status').value=rcells.item(6).firstChild.nodeValue;
    
    document.getElementById('cmdadd').disabled=true;
    document.getElementById('cmdupdate').disabled=false;
    document.getElementById('cmddelete').disabled=false;
}
function refresh()
{
    document.getElementById('tariff_Id').value="";
  //  document.getElementById('charge_type_Id').value="";
   // document.getElementById('tariff_Desc').value="";
    document.getElementById('tariff_Rate').value="";
 //   document.getElementById('excess_tariff_Rate').value="";
    document.getElementById('Tariff_wef').value="";
    document.getElementById('Uom').value="";
   // document.getElementById('Tariff_code').value="";
    document.getElementById('Beneficiary_Type').value="";
    document.getElementById('status').value="";
    
}
function exitwindow()
{
 window.close();
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

function validate()
 {
    //var charge_type_Id=document.getElementById("charge_type_Id").value;
   // var tariff_Desc=document.getElementById("tariff_Desc").value;
    var tariff_Rate=document.getElementById("tariff_Rate").value;
   //S var excess_tariff_Rate=document.getElementById("excess_tariff_Rate").value;
    var Tariff_wef=document.getElementById("Tariff_wef").value;
    var UOM=document.getElementById("UOM").value;
 //   var Tariff_Code=document.getElementById("Tariff_Code").value;
     var Beneficiary_Type=document.getElementById("Beneficiary_Type").value;
     var status=document.getElementById("status").value;
    /*if(Tariff_Code=="")
    {
        alert("Enter Tariff_Code");
        return false;
    }*/
    /*if(charge_type_Id=="")
    {
        alert("Enter Charge type description");
        return false;
    }*/
   /* else if(tariff_Desc=="")
    {
        alert("Enter tariff_Desc");
        return false;
    }*/
   
    if(tariff_Rate=="")
    {
        alert("Enter tariff_Rate");
        return false;
    }
  /*  else if(excess_tariff_Rate=="")
    {
        alert("Enter Excess_tariff_Rate");
        return false;
    }*/
     else if(Beneficiary_Type=="")
    {
        alert("Select Beneficiary_Type");
        return false;
    }
    else if(Tariff_wef=="")
    {
        alert("Enter Tariff_wef");
        return false;
    }
    else if(UOM=="")
    {
        alert("Enter UOM");
        return false;
    }
   else if(status=="")
    {
        alert("Enter Status");
        return false;
    }
   
    else
    {
        return true;
    }
 }
var message="Permission denied to right click";

function clickIE4(){
if (event.button==2){
alert(message);
return false;
}
}

function clickNS4(e){
if (document.layers||document.getElementById&&!document.all){
if (e.which==2||e.which==3){
alert(message);
return false;
}
}
}


//upto these

if (document.layers){
document.captureEvents(Event.MOUSEDOWN);
document.onmousedown=clickNS4;
}
else if (document.all&&!document.getElementById){
document.onmousedown=clickIE4;
}

document.oncontextmenu=new Function("alert(message);return false")