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
function loadbeneficiary()
{
    document.getElementById("cmdupdate").style.display='none';
   // document.getElementById("cmddelete").style.display='none';
    document.getElementById("statusdiv").style.display='none';
    document.getElementById("statusdivname").style.display='none';
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    url="../../../../../pms_dcb_mst_int?command=loadbeneficiary";
    url=url+"&sid="+Math.random();
   // alert(url);
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
                         
                         addOptiondesc(document.interest.Beneficiary_Type,Beneficiary_Type_val,Beneficiary_Type_id_val);
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
    
   var Beneficiary_Type=document.getElementById("Beneficiary_Type").value;
   var Interest_Rate=document.getElementById("Interest_Rate").value;
   var Interest_wef=document.getElementById("Interest_wef").value;
   var status=document.getElementById("status").value;
    var Interest_Id=document.getElementById("Interest_Id").value;
  
    if(actionval=="Add")
    {
           var valfn=validate();
           if(valfn==true)
           {
           
            url="../../../../../pms_dcb_mst_int?command=add&Beneficiary_Type="+Beneficiary_Type+"&Interest_Rate="+Interest_Rate+"&Interest_wef="+Interest_wef+"&status=A";
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange= stateChanged;
            xmlhttp.send(null);
           }
    }
    else if(actionval=="get")
   {
        
        url="../../../../../pms_dcb_mst_int?command=get";
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
        url="../../../../../pms_dcb_mst_int?command=update&Interest_Id="+Interest_Id+"&Beneficiary_Type="+Beneficiary_Type+"&Interest_Rate="+Interest_Rate+"&Interest_wef="+Interest_wef+"&status="+status;
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
       
        url="../../../../../pms_dcb_mst_int?command=delete&Interest_Id="+Interest_Id;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
    }
}

function stateChanged()
{
    var baseres,commandres,flagres,recordres;
    if(xmlhttp.readyState==4)
    {
        if(xmlhttp.status==200)
        {
            baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
            commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
            flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(commandres=="add")
            {
                if(flagres=='success')
                {
                    countinscheck=baseres.getElementsByTagName("countinscheck")[0].firstChild.nodeValue;
                    if(countinscheck==0)
                    {
                        alert("Successfully Added");
                        tablebody=document.getElementById("getvaluerows");
                        var INT_ID=baseres.getElementsByTagName("INT_ID")[0].firstChild.nodeValue;
                        var BENEFICIARY_TYPE_ID=baseres.getElementsByTagName("BENEFICIARY_TYPE_ID")[0].firstChild.nodeValue;
                        var BENEFICIARY_TYPE_DESC=baseres.getElementsByTagName("BENEFICIARY_TYPE_DESC")[0].firstChild.nodeValue;
                        var INT_RATE=baseres.getElementsByTagName("INT_RATE")[0].firstChild.nodeValue;
                        var INT_WEF=baseres.getElementsByTagName("INT_WEF")[0].firstChild.nodeValue;
                        var ACTIVE_STATUS =baseres.getElementsByTagName("ACTIVE_STATUS")[0].firstChild.nodeValue;
                       
                        var tbody=document.getElementById("getvaluerows");
                        var rowvalue=document.createElement("TR");
                        rowvalue.id=INT_ID;
                        var tabledata=document.createElement("TD");
                        var anc=document.createElement("A");
                        var url="javascript:loadvaluesfromtable('"+INT_ID+"')";
                        var nameval=document.createTextNode("Edit");
                        anc.href=url;
                        anc.appendChild(nameval);
                        tabledata.appendChild(anc);
                        rowvalue.appendChild(tabledata);
                    
                        var hiddentextINT_ID=document.createElement("input");
                         hiddentextINT_ID.type="hidden";
                         hiddentextINT_ID.name="qual1";
                         hiddentextINT_ID.id="qual1";
                         hiddentextINT_ID.text="qual1";
                         hiddentextINT_ID.value=INT_ID;
                         tabledata.appendChild(hiddentextINT_ID);
                                      
                            var tabledata_BENEFICIARY_TYPE_DESC=document.createElement("TD");
                            var BENEFICIARY_TYPE_DESC=document.createTextNode(BENEFICIARY_TYPE_DESC);
                            tabledata_BENEFICIARY_TYPE_DESC.appendChild(BENEFICIARY_TYPE_DESC);
                            rowvalue.appendChild(tabledata_BENEFICIARY_TYPE_DESC);
                            
                             var hiddentext1=document.createElement("input");
                             hiddentext1.type="hidden";
                             hiddentext1.name="qual1";
                             hiddentext1.id="qual1";
                             hiddentext1.text="qual1";
                             hiddentext1.value=BENEFICIARY_TYPE_ID;
                             tabledata_BENEFICIARY_TYPE_DESC.appendChild(hiddentext1);
                   
                    
                                var tabledata_INT_RATE=document.createElement("TD");
                                var INT_RATE=document.createTextNode(INT_RATE);
                                tabledata_INT_RATE.appendChild(INT_RATE);
                                rowvalue.appendChild(tabledata_INT_RATE);
                          
                                
                                var tabledata_INT_WEF=document.createElement("TD");
                                var Tariff_wef=document.createTextNode(INT_WEF);
                                tabledata_INT_WEF.appendChild(Tariff_wef);
                                rowvalue.appendChild(tabledata_INT_WEF);
                     
                   
                  
                                var tabledatastatus=document.createElement("TD");
                                 if(ACTIVE_STATUS=="A")
                                  {
                                        ACTIVE_STATUS="Active";
                                        var ACTIVE_STATUS=document.createTextNode(ACTIVE_STATUS);
                                  }
                                  else
                                  {
                                        ACTIVE_STATUS="Defunct";
                                        var ACTIVE_STATUS=document.createTextNode(ACTIVE_STATUS);
                                  }
                       
                    
                                tabledatastatus.appendChild(ACTIVE_STATUS);
                                rowvalue.appendChild(tabledatastatus);
                                 
                                tablebody.appendChild(rowvalue);
                            }
                             else if(countinscheck==1)
                             {
                                    alert("Interest Rate Already Exists for this Beneficiary Type ..\n If Interest Rate Is To Be Updated, First Update the Existing Interest Rate As Defunct");
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
                    var len=baseres.getElementsByTagName("INT_ID").length;
                    
                    //alert(len);
                    for(var i=0;i<len;i++)
                    {
                        var INT_ID=baseres.getElementsByTagName("INT_ID")[i].firstChild.nodeValue;
                        var BENEFICIARY_TYPE_ID=baseres.getElementsByTagName("BENEFICIARY_TYPE_ID")[i].firstChild.nodeValue;
                        var BENEFICIARY_TYPE_DESC=baseres.getElementsByTagName("BENEFICIARY_TYPE_DESC")[i].firstChild.nodeValue;
                        var INT_RATE=baseres.getElementsByTagName("INT_RATE")[i].firstChild.nodeValue;
                        var INT_WEF=baseres.getElementsByTagName("INT_WEF")[i].firstChild.nodeValue;
                        var ACTIVE_STATUS=baseres.getElementsByTagName("ACTIVE_STATUS")[i].firstChild.nodeValue;
                   
                        var INT_WEF_TEMP=INT_WEF.split("-");
                        var INT_WEF_TEMP_VAR=INT_WEF_TEMP[2]+"/"+INT_WEF_TEMP[1]+"/"+INT_WEF_TEMP[0]; 
                        
                        var rowvalue=document.createElement("TR");
                        rowvalue.id=INT_ID;
                        
                        var tabledata=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvaluesfromtable('"+INT_ID+"')";
                    var nameval=document.createTextNode("Edit");
                    anc.href=url;
                    anc.appendChild(nameval);
                    tabledata.appendChild(anc);
                    rowvalue.appendChild(tabledata);
                  
                     var hiddentextINT_ID=document.createElement("input");
                     hiddentextINT_ID.type="hidden";
                     hiddentextINT_ID.name="qual1";
                     hiddentextINT_ID.id="qual1";
                     hiddentextINT_ID.text="qual1";
                     hiddentextINT_ID.value=INT_ID;
                     tabledata.appendChild(hiddentextINT_ID);
                   
                                      
                    var tabledata_BENEFICIARY_TYPE_DESC=document.createElement("TD");
                    var BENEFICIARY_TYPE_DESC=document.createTextNode(BENEFICIARY_TYPE_DESC);
                    tabledata_BENEFICIARY_TYPE_DESC.appendChild(BENEFICIARY_TYPE_DESC);
                    rowvalue.appendChild(tabledata_BENEFICIARY_TYPE_DESC);
                    
                     var hiddentext1=document.createElement("input");
                     hiddentext1.type="hidden";
                     hiddentext1.name="qual1";
                     hiddentext1.id="qual1";
                     hiddentext1.text="qual1";
                     hiddentext1.value=BENEFICIARY_TYPE_ID;
                     tabledata_BENEFICIARY_TYPE_DESC.appendChild(hiddentext1);
                   
                    
                    var tabledata_INT_RATE=document.createElement("TD");
                    var INT_RATE=document.createTextNode(INT_RATE);
                    tabledata_INT_RATE.appendChild(INT_RATE);
                    rowvalue.appendChild(tabledata_INT_RATE);
              
                    
                    var tabledata_INT_WEF=document.createElement("TD");
                    var INT_WEF_TEMP_VAR=document.createTextNode(INT_WEF_TEMP_VAR);
                    tabledata_INT_WEF.appendChild(INT_WEF_TEMP_VAR);
                    rowvalue.appendChild(tabledata_INT_WEF);
                     
                   
                  
                    var tabledatastatus=document.createElement("TD");
                     if(ACTIVE_STATUS=="A")
                      {
                            ACTIVE_STATUS="Active";
                            var ACTIVE_STATUS=document.createTextNode(ACTIVE_STATUS);
                      }
                      else
                      {
                            ACTIVE_STATUS="Defunct";
                            var ACTIVE_STATUS=document.createTextNode(ACTIVE_STATUS);
                      }
                       
                    
                    tabledatastatus.appendChild(ACTIVE_STATUS);
                    rowvalue.appendChild(tabledatastatus);
                     
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
                    alert( 'Successfully Updated');
                   
                    var INT_ID=baseres.getElementsByTagName("INT_ID")[0].firstChild.nodeValue;
                    var BENEFICIARY_TYPE_DESC=baseres.getElementsByTagName("BENEFICIARY_TYPE_DESC")[0].firstChild.nodeValue;
                    var BENEFICIARY_TYPE_ID=baseres.getElementsByTagName("BENEFICIARY_TYPE_ID")[0].firstChild.nodeValue;
                    var INT_RATE=baseres.getElementsByTagName("INT_RATE")[0].firstChild.nodeValue;
                    var INT_WEF=baseres.getElementsByTagName("INT_WEF")[0].firstChild.nodeValue;
                    var ACTIVE_STATUS=baseres.getElementsByTagName("ACTIVE_STATUS")[0].firstChild.nodeValue;
                    var rvar=document.getElementById(INT_ID);
                    var rcells=rvar.cells;
              
                 
                 rcells.item(1).firstChild.nodeValue=BENEFICIARY_TYPE_DESC;
                 rcells.item(1).lastChild.value=BENEFICIARY_TYPE_ID;
                
                  rcells.item(2).firstChild.nodeValue=INT_RATE;
               
                 
                  rcells.item(3).firstChild.nodeValue=INT_WEF;
                 
                    if(ACTIVE_STATUS=="A")
                      {
                            ACTIVE_STATUS="Active";
                           
                      }
                      else
                      {
                            ACTIVE_STATUS="Defunct";
                         
                      }
                   rcells.item(4).firstChild.nodeValue=ACTIVE_STATUS;
                  
                  
                 

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
                    var INT_ID=baseres.getElementsByTagName("INT_ID")[0].firstChild.nodeValue;
                   var tbody=document.getElementById("existing");
                    var r=document.getElementById(INT_ID);
                    var ri=r.rowIndex;
                    tbody.deleteRow(ri);
                    document.getElementById('Interest_Id').value="";
                    document.getElementById('Beneficiary_Type').value="";
                    document.getElementById('Interest_Rate').value="";
                    document.getElementById('Interest_wef').value="";
                    document.getElementById('Beneficiary_Type').value="";
                    document.getElementById('status').value="";
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
  //  document.getElementById("cmddelete").style.display='block';
    document.getElementById("cmdupdate").style.display='inline';
  //  document.getElementById("cmddelete").style.display='inline';

    var rvar=document.getElementById(r);
    var rcells=rvar.cells;
    document.getElementById("statusdiv").style.display='block';
    document.getElementById("statusdivname").style.display='block';
    
    document.getElementById('Interest_Id').value=rcells.item(0).lastChild.value;
    document.getElementById('Beneficiary_Type').value=rcells.item(1).lastChild.value;
  
    document.getElementById('Interest_Rate').value=rcells.item(2).firstChild.nodeValue;
    document.getElementById('Interest_wef').value=rcells.item(3).firstChild.nodeValue;
    
    statevalue=rcells.item(4).firstChild.nodeValue
    if(statevalue=="Active")
    {
        document.getElementById('status').value="A";
        
    }
    else
    {
        document.getElementById('status').value="D";
    }
        
     
    
   // document.getElementById('cmdadd').disabled=true;
   // document.getElementById('cmdupdate').disabled=false;
   // document.getElementById('cmddelete').disabled=false;
}
function refresh()
{
    document.getElementById('Interest_Id').value="";
  
    document.getElementById('Beneficiary_Type').value="";
 
    document.getElementById('Interest_Rate').value="";
    document.getElementById('Interest_wef').value="";
    document.getElementById('Beneficiary_Type').value="";
    document.getElementById('status').value="";
    
}
function exitwindow()
{
 window.close();
}
function bentypecheck()
{
    var Beneficiary_Type=document.getElementById("Beneficiary_Type").value;
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    url="../../../../../pms_dcb_mst_int?command=bentypecheck&Beneficiary_Type="+Beneficiary_Type;
   // alert(url);
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadbentypecheck(xmlhttp);
            }
    xmlhttp.send(null);
}
function stateChangedloadbentypecheck(xmlhttp)
{
    var baseres,commandres,flagres,recordres;
    //alert(xmlhttp.readyState);
    if(xmlhttp.readyState==4)
    {
        if(xmlhttp.status==200)
        {
           
            baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
            commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
           
            flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(commandres=="bentypecheck")
            {
                if(flagres=='success')
                {
                    countbentype=baseres.getElementsByTagName("countbentype")[0].firstChild.nodeValue;
                    
                    if(countbentype==1)
                    {
                        alert("Interest Rate Already Exists for this Beneficiary Type ..\n If Interest Rate Is To Be Updated, First Update the Existing Interest Rate As Defunct");
                    }
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
function validate()
{
    
    var Beneficiary_Type=document.getElementById("Beneficiary_Type").value;
    var Interest_Rate=document.getElementById("Interest_Rate").value;
    var Interest_wef=document.getElementById("Interest_wef").value;
 
    if(Beneficiary_Type=="")
    {
        alert("Select Beneficiary Type");
        return false;
    }
     else
    {
        return true;
    }
    if(Interest_Rate=="")
    {
        alert("Enter Interest Rate");
        return false;
    }
     else
    {
        return true;
    }
    if(Interest_wef=="")
    {
        alert("Enter Interest wef ");
        return false;
    }
     else
    {
        return true;
    }
}