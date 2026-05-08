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
function doFunction(actionval)
{
xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
    {
        alert ("Your browser does not support AJAX!");
        return;
    }
    var Agreement_No=document.getElementById("Agreement_No").value;
    var Beneficiary_Sno=document.getElementById("Beneficiary_Sno").value;
    var Agreement_Code=document.getElementById("Agreement_Code").value;
    var Agreement_Qty=document.getElementById("Agreement_Qty").value;
    var Agreement_DT_Wef=document.getElementById("Agreement_DT_Wef").value;
    var Agreement_DT_from=document.getElementById("Agreement_DT_from").value;
    var Agreement_DT_to=document.getElementById("Agreement_DT_to").value;
    var Remarks=document.getElementById("Remarks").value;
   
    
    
    var url="";
   // alert(actionval);
    //alert(Beneficiary_Sno);
    //alert(Agreement_Code);
   // alert(Agreement_Qty);
   // alert(Agreement_DT_Wef);
   // alert(Agreement_DT_from);
   // alert(Agreement_DT_to);
   // alert(Remarks);
    
    if(actionval=="Add")
    {
            var valvar=validate();
            if(valvar==true)
            {
            url="../../../../../Pms_Dcb_Mst_Main_Agmt?command=add&Beneficiary_Sno="+Beneficiary_Sno+"&Agreement_Code="+Agreement_Code+"&Agreement_Qty="+Agreement_Qty+"&Agreement_DT_Wef="+Agreement_DT_Wef+"&Agreement_DT_from="+Agreement_DT_from+"&Agreement_DT_to="+Agreement_DT_to+"&Remarks="+Remarks;
            url=url+"&sid="+Math.random();
            //alert(url);
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange=stateChanged;
            xmlhttp.send(null);
            }
    }
    else if(actionval=="get")
    {
        
        url="../../../../../Pms_Dcb_Mst_Main_Agmt?command=get";
       // alert("get value");
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
        
    }
    else if(actionval=="Update")
    {
        
        var valvar=validate();
        if(valvar==true)
        {
            url="../../../../../Pms_Dcb_Mst_Main_Agmt?command=update&Agreement_No="+Agreement_No+"&Beneficiary_Sno="+Beneficiary_Sno+"&Agreement_Code="+Agreement_Code+"&Agreement_Qty="+Agreement_Qty+"&Agreement_DT_Wef="+Agreement_DT_Wef+"&Agreement_DT_from="+Agreement_DT_from+"&Agreement_DT_to="+Agreement_DT_to+"&Remarks="+Remarks;
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange=stateChanged;
            xmlhttp.send(null);
       }
    }
    else if(actionval=="Delete")
    {
        url="../../../../../Pms_Dcb_Mst_Main_Agmt?command=delete&Agreement_No="+Agreement_No+"&Beneficiary_Sno="+Beneficiary_Sno+"&Agreement_Code="+Agreement_Code+"&Agreement_Qty="+Agreement_Qty+"&Agreement_DT_Wef="+Agreement_DT_Wef+"&Agreement_DT_from="+Agreement_DT_from+"&Agreement_DT_to="+Agreement_DT_to+"&Remarks="+Remarks;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
    }
}
function stateChanged()
{
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        if(xmlhttp.status==200)
        {
           // alert("one");
            baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
           // alert(baseres);
            commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
          //  alert("two");
           // alert(commandres);
            flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
            var tablebody=document.getElementById("getvaluerows");
          //  alert("three");
          //  alert(flagres);
            if(commandres=="add")
            {
                if(flagres=='success')
                {
                    alert("Successfully Added");
                    var Agreement_Sno=baseres.getElementsByTagName("Agreement_Sno")[0].firstChild.nodeValue;
                    var Beneficiary_Sno=baseres.getElementsByTagName("Beneficiary_Sno")[0].firstChild.nodeValue;
                    var Beneficiary_name=baseres.getElementsByTagName("Beneficiary_name")[0].firstChild.nodeValue;
                    var Agreement_Code=baseres.getElementsByTagName("Agreement_Code")[0].firstChild.nodeValue;
                    var Agreement_Qty=baseres.getElementsByTagName("Agreement_Qty")[0].firstChild.nodeValue;
                    var Agreement_DT_Wef=baseres.getElementsByTagName("Agreement_DT_Wef")[0].firstChild.nodeValue;
                    var Agreement_DT_from=baseres.getElementsByTagName("Agreement_DT_from")[0].firstChild.nodeValue;
                    var Agreement_DT_to=baseres.getElementsByTagName("Agreement_DT_to")[0].firstChild.nodeValue;
                    var Remarks=baseres.getElementsByTagName("Remarks")[0].firstChild.nodeValue;
                   
                    //alert("value1"+regnores);
                   // alert("value2"+empres);
                    var tbody=document.getElementById("getvaluerows");
                    var rowvalue=document.createElement("TR");
                    rowvalue.id=Agreement_Sno;
                    var tabledata=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvaluesfromtable('"+Agreement_Sno+"')";
                    var nameval=document.createTextNode("Edit");
                    anc.href=url;
                    anc.appendChild(nameval);
                    tabledata.appendChild(anc);
                    rowvalue.appendChild(tabledata);
                   
                     var tabledata1=document.createElement("TD");
                     var Agreement_Sno=document.createTextNode(Agreement_Sno);
                     tabledata1.appendChild(Agreement_Sno);
                     rowvalue.appendChild(tabledata1);
                      
                     var tabledata2=document.createElement("TD");
                     var Beneficiary_name=document.createTextNode(Beneficiary_name);
                     tabledata2.appendChild(Beneficiary_name);
                     
                    
                    var hiddentext1=document.createElement("input");
                     hiddentext1.type="hidden";
                     hiddentext1.name="qual1";
                     hiddentext1.id="qual1";
                     hiddentext1.text="qual1";
                     hiddentext1.value=Beneficiary_Sno;
                     tabledata2.appendChild(hiddentext1);
                     
                     rowvalue.appendChild(tabledata2);
                     
                     var tabledata3=document.createElement("TD");
                     var Agreement_Code=document.createTextNode(Agreement_Code);
                     tabledata3.appendChild(Agreement_Code);
                     rowvalue.appendChild(tabledata3);
                     
                     var tabledata4=document.createElement("TD");
                     var Agreement_Qty=document.createTextNode(Agreement_Qty);
                     tabledata4.appendChild(Agreement_Qty);
                     rowvalue.appendChild(tabledata4);
                     
                     var tabledata5=document.createElement("TD");
                     var Agreement_DT_Wef=document.createTextNode(Agreement_DT_Wef);
                     tabledata5.appendChild(Agreement_DT_Wef);
                     rowvalue.appendChild(tabledata5);
                     
                     var tabledata6=document.createElement("TD");
                     var Agreement_DT_from=document.createTextNode(Agreement_DT_from);
                     tabledata6.appendChild(Agreement_DT_from);
                     rowvalue.appendChild(tabledata6);
                     
                     var tabledata7=document.createElement("TD");
                     var Agreement_DT_to=document.createTextNode(Agreement_DT_to);
                     tabledata7.appendChild(Agreement_DT_to);
                     rowvalue.appendChild(tabledata7);
                     
                     var tabledata8=document.createElement("TD");
                     var Remarks=document.createTextNode(Remarks);
                     tabledata8.appendChild(Remarks);
                     rowvalue.appendChild(tabledata8);
                     
                     
                      
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
                    var len=baseres.getElementsByTagName("Agreement_Sno").length;
                    //alert(len);
                    for(var i=0;i<len;i++)
                    {
                      var Agreement_Sno=baseres.getElementsByTagName("Agreement_Sno")[i].firstChild.nodeValue;
                      var Beneficiary_Sno=baseres.getElementsByTagName("Beneficiary_Sno")[i].firstChild.nodeValue;
                      var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
                      var Agreement_Code=baseres.getElementsByTagName("Agreement_Code")[i].firstChild.nodeValue;
                      var Agreement_Qty=baseres.getElementsByTagName("Agreement_Qty")[i].firstChild.nodeValue;
                      var Agreement_DT_Wef=baseres.getElementsByTagName("Agreement_DT_Wef")[i].firstChild.nodeValue;
                      var temp=Agreement_DT_Wef.split("-");
                      
                      var Agreement_DT_Wef_temp=temp[2]+"/"+temp[1]+"/"+temp[0]; 
                      //alert(Agreement_DT_Wef_temp);
                      var Agreement_DT_from=baseres.getElementsByTagName("Agreement_DT_from")[i].firstChild.nodeValue;
                      var temp1=Agreement_DT_from.split("-");
                      var Agreement_DT_from_temp=temp1[2]+"/"+temp1[1]+"/"+temp1[0]; 
                      var Agreement_DT_to=baseres.getElementsByTagName("Agreement_DT_to")[i].firstChild.nodeValue;
                      var temp2=Agreement_DT_to.split("-");
                      var Agreement_DT_to_temp=temp2[2]+"/"+temp2[1]+"/"+temp2[0]; 
                      var Remarks=baseres.getElementsByTagName("Remarks")[i].firstChild.nodeValue;
                     // alert(paid_by_id);
                     // alert(paid_by_desc);
                      var rowvalue=document.createElement("TR");
                      rowvalue.id=Agreement_Sno;
                      var tabledata=document.createElement("TD");
                      var anc=document.createElement("A");
                      var url="javascript:loadvaluesfromtable('" +Agreement_Sno+"')";
                      anc.href=url;
                      var nameval=document.createTextNode("Edit");
                      anc.appendChild(nameval);
                      tabledata.appendChild(anc);
                      rowvalue.appendChild(tabledata);
                      
                      
                      var tabledata1=document.createElement("TD");
                      var Agreement_Sno=document.createTextNode(Agreement_Sno);
                      tabledata1.appendChild(Agreement_Sno);
                      rowvalue.appendChild(tabledata1);
                      
                      var tabledata2=document.createElement("TD");
                     var BENEFICIARY_NAME=document.createTextNode(BENEFICIARY_NAME);
                     tabledata2.appendChild(BENEFICIARY_NAME);
                     
                     
                     var hiddentext1=document.createElement("input");
                     hiddentext1.type="hidden";
                     hiddentext1.name="qual1";
                     hiddentext1.id="qual1";
                     hiddentext1.text="qual1";
                     hiddentext1.value=Beneficiary_Sno;
                     tabledata2.appendChild(hiddentext1);
                     
                     
                     rowvalue.appendChild(tabledata2);
                     //alert(rowvalue);
                     var tabledata3=document.createElement("TD");
                     var Agreement_Code=document.createTextNode(Agreement_Code);
                     tabledata3.appendChild(Agreement_Code);
                     rowvalue.appendChild(tabledata3);
                     
                     var tabledata4=document.createElement("TD");
                     var Agreement_Qty=document.createTextNode(Agreement_Qty);
                     tabledata4.appendChild(Agreement_Qty);
                     rowvalue.appendChild(tabledata4);
                     
                     var tabledata5=document.createElement("TD");
                     var Agreement_DT_Wef=document.createTextNode(Agreement_DT_Wef_temp);
                     tabledata5.appendChild(Agreement_DT_Wef);
                     rowvalue.appendChild(tabledata5);
                     
                     var tabledata6=document.createElement("TD");
                     var Agreement_DT_from=document.createTextNode(Agreement_DT_from_temp);
                     tabledata6.appendChild(Agreement_DT_from);
                     rowvalue.appendChild(tabledata6);
                     
                     var tabledata7=document.createElement("TD");
                     var Agreement_DT_to=document.createTextNode(Agreement_DT_to_temp);
                     tabledata7.appendChild(Agreement_DT_to);
                     rowvalue.appendChild(tabledata7);
                     
                     var tabledata8=document.createElement("TD");
                     var Remarks=document.createTextNode(Remarks);
                     tabledata8.appendChild(Remarks);
                     rowvalue.appendChild(tabledata8);
                     
                      
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
                    
                    var Agreement_No=baseres.getElementsByTagName("Agreement_No")[0].firstChild.nodeValue;
                    var Beneficiary_Sno=baseres.getElementsByTagName("Beneficiary_Sno")[0].firstChild.nodeValue;
                    var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[0].firstChild.nodeValue;
                    var Agreement_Code=baseres.getElementsByTagName("Agreement_Code")[0].firstChild.nodeValue;
                    var Agreement_Qty=baseres.getElementsByTagName("Agreement_Qty")[0].firstChild.nodeValue;
                    var Agreement_DT_Wef=baseres.getElementsByTagName("Agreement_DT_Wef")[0].firstChild.nodeValue;
                    var Agreement_DT_from=baseres.getElementsByTagName("Agreement_DT_from")[0].firstChild.nodeValue;
                    var Agreement_DT_to=baseres.getElementsByTagName("Agreement_DT_to")[0].firstChild.nodeValue;
                    var Remarks=baseres.getElementsByTagName("Remarks")[0].firstChild.nodeValue;
                    
                
                  var rvar=document.getElementById(Agreement_No);
                  var rcells=rvar.cells;
                  rcells.item(1).firstChild.nodeValue=Agreement_No;
                  rcells.item(2).firstChild.nodeValue=BENEFICIARY_NAME;
                  rcells.item(2).lastChild.value=Beneficiary_Sno;
                  rcells.item(3).firstChild.nodeValue=Agreement_Code;
                  rcells.item(4).firstChild.nodeValue=Agreement_Qty;
                  rcells.item(5).firstChild.nodeValue=Agreement_DT_Wef;
                  rcells.item(6).firstChild.nodeValue=Agreement_DT_from;
                  rcells.item(7).firstChild.nodeValue=Agreement_DT_to;
                  rcells.item(8).firstChild.nodeValue=Remarks;
                 

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
                    var Agreement_No=baseres.getElementsByTagName("Agreement_No")[0].firstChild.nodeValue;
                    var Beneficiary_Sno=baseres.getElementsByTagName("Beneficiary_Sno")[0].firstChild.nodeValue;
                    var Agreement_Code=baseres.getElementsByTagName("Agreement_Code")[0].firstChild.nodeValue;
                    var Agreement_Qty=baseres.getElementsByTagName("Agreement_Qty")[0].firstChild.nodeValue;
                    var Agreement_DT_Wef=baseres.getElementsByTagName("Agreement_DT_Wef")[0].firstChild.nodeValue;
                    var Agreement_DT_from=baseres.getElementsByTagName("Agreement_DT_from")[0].firstChild.nodeValue;
                    var Agreement_DT_to=baseres.getElementsByTagName("Agreement_DT_to")[0].firstChild.nodeValue;
                    var Remarks=baseres.getElementsByTagName("Remarks")[0].firstChild.nodeValue;
                    var tbody=document.getElementById("existing");
                    var r=document.getElementById(Agreement_No);
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
    //alert(rcells.item(1).firstChild.nodeValue);
    //alert(r);
    document.getElementById('Agreement_No').value=rcells.item(1).firstChild.nodeValue;
   
    document.getElementById('Beneficiary_Sno').value=rcells.item(2).lastChild.value;
   // document.getElementById('tariff_Desc').value=rcells.item(3).firstChild.nodeValue;
    document.getElementById('Agreement_Code').value=rcells.item(3).firstChild.nodeValue;
    document.getElementById('Agreement_Qty').value=rcells.item(4).firstChild.nodeValue;
    document.getElementById('Agreement_DT_Wef').value=rcells.item(5).firstChild.nodeValue;
    document.getElementById('Agreement_DT_from').value=rcells.item(6).firstChild.nodeValue;
    document.getElementById('Agreement_DT_to').value=rcells.item(7).firstChild.nodeValue;
    document.getElementById('Remarks').value=rcells.item(8).firstChild.nodeValue;

    
    document.getElementById('cmdadd').disabled=true;
    document.getElementById('cmdupdate').disabled=false;
    document.getElementById('cmddelete').disabled=false;
}
function loadbeneficiarytype()
{
    
  
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    url="../../../../../Pms_Dcb_Mst_Main_Agmt?command=loadbeneficiarytype";
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
                         
                       
                       //  alert(Beneficiary_Type_id);
                        
                        
                         addOptionBeneficiary_type(document.agreement.Beneficiary_Sno,BEN_TYPE_DESC,BEN_TYPE_ID);
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
function refresh()
{
    document.getElementById('Agreement_No').value="";
    document.getElementById('Beneficiary_Sno').value="";
   // document.getElementById('tariff_Desc').value="";
    document.getElementById('Agreement_Code').value="";
    document.getElementById('Agreement_Qty').value="";
    document.getElementById('Agreement_DT_Wef').value="";
    document.getElementById('Agreement_DT_from').value="";
    document.getElementById('Agreement_DT_to').value="";
    document.getElementById('Remarks').value="";
}
function exitwindow()
{
 window.close();
}

function numonly(e)
    {
        
        var unicode=e.charCode? e.charCode : e.keyCode
        if (unicode!=8)//backspace
        {
            if (unicode<48||unicode>57) 
                return false ;
        }
    }
    
    
function validate()
 {
    var Beneficiary_Sno=document.getElementById("Beneficiary_Sno").value;
    var Agreement_Code=document.getElementById("Agreement_Code").value;
    var Agreement_Qty=document.getElementById("Agreement_Qty").value;
    var Agreement_DT_Wef=document.getElementById("Agreement_DT_Wef").value;
    var Agreement_DT_from=document.getElementById("Agreement_DT_from").value;
    var Agreement_DT_to=document.getElementById("Agreement_DT_to").value;
     var Remarks=document.getElementById("Remarks").value;
    if(Beneficiary_Sno=="")
    {
        alert("Enter Beneficiary Sno");
        return false;
    }
    else if(Agreement_Code=="")
    {
        alert("Enter Agreement Code");
        return false;
    }
    else if(Agreement_Qty=="")
    {
        alert("Enter Agreement Quantity");
        return false;
    }
    else if(Agreement_DT_Wef=="")
    {
        alert("Enter Agreement Date");
        return false;
    }
    else if(Agreement_DT_from=="")
    {
        alert("Enter Agreement Period(From)");
        return false;
    }
    else if(Agreement_DT_to=="")
    {
        alert("Enter Agreement Period(To)");
        return false;
    }
   /* else if(Remarks=="")
    {
        alert("Enter Remarks");
        return false;
    }*/
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