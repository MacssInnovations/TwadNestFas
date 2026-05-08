var xx=0;
var winAccHeadCode;
function AccHeadpopup()
{
    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) 
    {
       winAccHeadCode.resizeTo(500,500);
       winAccHeadCode.moveTo(250,250); 
       winAccHeadCode.focus();
    }
    else
    {
        winAccHeadCode=null
    }
        
    winAccHeadCode= window.open("../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Acc_Head_Dir_List_InUse.jsp","AccountHeadSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAccHeadCode.moveTo(250,250);  
    winAccHeadCode.focus();
    xx=1;
}
function AccHeadpopupone()
{
    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) 
    {
       winAccHeadCode.resizeTo(500,500);
       winAccHeadCode.moveTo(250,250); 
       winAccHeadCode.focus();
    }
    else
    {
        winAccHeadCode=null
    }
        
    winAccHeadCode= window.open("../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Acc_Head_Dir_List_InUse.jsp","AccountHeadSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAccHeadCode.moveTo(250,250);  
    winAccHeadCode.focus();
    xx=2;
}
function doParentAccHead(code)
{
    
    if(xx==1)
    {
    document.getElementById("txtAcc_HeadCode").value=code;
    doFunction('Reterive');
    }
    else
    {
    document.getElementById("txtAcc_HeadCode_dr").value=code;
    doFunction('Reterivevalue2');
    }
    
   return true;
}

function loadCombo()
{
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    url="../../../../../charge_type_details?command=comboload";
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
                         var charge_by_desc_val=baseres.getElementsByTagName("CHARGE_TYPE_DESC_TABLE")[i].firstChild.nodeValue;
                         addOption(document.chargedetails.charge_type_desc,charge_by_desc_val,charge_by_desc_val);
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
function doFunction(actionval)
{
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
        var charge_type_Id=document.getElementById("charge_type_Id").value;
        var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
        var txtAcc_HeadCode_dr=document.getElementById("txtAcc_HeadCode_dr").value;
        var charge_type_desc=document.getElementById("charge_type_desc").value;
        var headcodelen=document.getElementById("txtAcc_HeadCode").value.length;
        var txtAcc_HeadCode_drlen=document.getElementById("txtAcc_HeadCode_dr").value.length;
        
        if(actionval=="Reterive")
        {
            
            if((txtAcc_HeadCode!="")&&(headcodelen>=6))
            {
                
                    url="../../../../../charge_type_details?command=reterive&txtAcc_HeadCode="+txtAcc_HeadCode;  
                    url=url+"&sid="+Math.random();
                    xmlhttp.open("GET",url,true);
                    xmlhttp.onreadystatechange= function()
                    {
                        stateChanged(xmlhttp);
                    }
                    xmlhttp.send(null);
                
            }
        }
        else if(actionval=="Reterivevalue2")
        {
       // alert(txtAcc_HeadCode_drlen);
            
            if((txtAcc_HeadCode_dr!="")&&(txtAcc_HeadCode_drlen>=6))
            {
                    url="../../../../../charge_type_details?command=Reterivevalue2&txtAcc_HeadCode_dr="+txtAcc_HeadCode_dr;  
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
        else if(actionval=="Add")
        {
            var val=validate();
            if(val==true)
            {
                url="../../../../../charge_type_details?command=add&charge_type_desc="+charge_type_desc+"&txtAcc_HeadCode="+txtAcc_HeadCode+"&txtAcc_HeadCode_dr="+txtAcc_HeadCode_dr;
                url=url+"&sid="+Math.random();
                xmlhttp.open("GET",url,true);
                 xmlhttp.onreadystatechange= function()
                {
                    stateChanged(xmlhttp);
                }
                xmlhttp.send(null);
            }
        }
        else if(actionval=="Update")
        {
            var val=validate();
            if(val==true)
            {
            url="../../../../../charge_type_details?command=update&charge_type_Id="+charge_type_Id+"&charge_type_desc="+charge_type_desc+"&txtAcc_HeadCode="+txtAcc_HeadCode+"&txtAcc_HeadCode_dr="+txtAcc_HeadCode_dr;
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange= function()
            {
                stateChanged(xmlhttp);
            }
            xmlhttp.send(null);
            }
        }
        else if(actionval=="get")
        {
        url="../../../../../charge_type_details?command=get";
        
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange= function()
        {
            stateChanged(xmlhttp,'get');
        }
        xmlhttp.send(null);
        
        }
        else if(actionval=="Delete")
        {
        url="../../../../../charge_type_details?command=delete&charge_type_Id="+charge_type_Id+"&charge_type_desc="+charge_type_desc+"&txtAcc_HeadCode="+txtAcc_HeadCode;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange= function()
        {
            stateChanged(xmlhttp);
        }
        xmlhttp.send(null);
        }

}
function stateChanged(xmlhttp)
{
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        if(xmlhttp.status==200)
        {
            baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
            commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
            flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(commandres=="reterive")
            {
                if(flagres=="success")
                {
                    var txtAcc_HeadDesc=baseres.getElementsByTagName("txtAcc_HeadDesc")[0].firstChild.nodeValue;
                    document.getElementById('txtAcc_HeadDesc').value=txtAcc_HeadDesc;
                    
                }
                else
                {
                    flagvar=baseres.getElementsByTagName("flagvar")[0].firstChild.nodeValue;
                    if(flagvar=="Dataerror")
                    {
                        alert("Account Head Code '"+document.getElementById("txtAcc_HeadCode").value+"' doesn't Exist");
                        return false;
                    }
                    else
                    {
                        alert("Reterival Error");
                    }
                     
                }
            }
            else if(commandres=="Reterivevalue2")
            {
                if(flagres=="success")
                {
                    var txtAcc_HeadDesc=baseres.getElementsByTagName("txtAcc_HeadDesc")[0].firstChild.nodeValue;
                    document.getElementById('txtAcc_HeadDesc_dr').value=txtAcc_HeadDesc;
                    
                }
                else
                {
                    flagvar=baseres.getElementsByTagName("flagvar")[0].firstChild.nodeValue;
                    if(flagvar=="Dataerror")
                    {
                        alert("Account Head Code '"+document.getElementById("txtAcc_HeadCode_dr").value+"' doesn't Exist");
                        return false;
                    }
                    else
                    {
                        alert("Reterival Error");
                    }
                     
                }
            }
            else if(commandres=="add")
            {
                if(flagres=='success')
                {
                    alert("Successfully Inserted");
                    var charge_by_id=baseres.getElementsByTagName("charge_type_Id")[0].firstChild.nodeValue;
                    var charge_type_desc=baseres.getElementsByTagName("charge_type_desc")[0].firstChild.nodeValue;
                    var txtAcc_HeadCode=baseres.getElementsByTagName("txtAcc_HeadCode")[0].firstChild.nodeValue;
                    var txtAcc_HeadCode_dr=baseres.getElementsByTagName("txtAcc_HeadCode_dr")[0].firstChild.nodeValue;
                    var tbody=document.getElementById("getvaluerows");
                    var rowvalue=document.createElement("TR");
                    rowvalue.id=charge_by_id;
                    var tabledata=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvaluesfromtable('"+charge_by_id+"')";
                    var nameval=document.createTextNode("Edit");
                    anc.href=url;
                    anc.appendChild(nameval);
                    tabledata.appendChild(anc);
                    rowvalue.appendChild(tabledata);
                   
                     var tabledata1=document.createElement("TD");
                     var charge_by_id=document.createTextNode(charge_by_id);
                     tabledata1.appendChild(charge_by_id);
                     rowvalue.appendChild(tabledata1);
                      
                     var tabledata2=document.createElement("TD");
                     var charge_type_desc=document.createTextNode(charge_type_desc);
                     tabledata2.appendChild(charge_type_desc);
                     rowvalue.appendChild(tabledata2);
                     
                      var tabledata3=document.createElement("TD");
                      var txtAcc_HeadCode_val=document.createTextNode(txtAcc_HeadCode);
                      tabledata3.appendChild(txtAcc_HeadCode_val);
                      rowvalue.appendChild(tabledata3);
                      
                      var tabledata4=document.createElement("TD");
                      var txtAcc_HeadCode_dr_val=document.createTextNode(txtAcc_HeadCode_dr);
                      tabledata4.appendChild(txtAcc_HeadCode_dr_val);
                      rowvalue.appendChild(tabledata4);
                      
                     tablebody.appendChild(rowvalue);
                }
                else
                {
                    alert("Not Inserted");
                }
            }
        
        else if(commandres=="get")
            {
                if(flagres=='success')
                {
                    tablebody=document.getElementById("getvaluerows");
                    var len=baseres.getElementsByTagName("charge_by_id").length;
                    for(var i=0;i<len;i++)
                    {
                      var charge_by_id=baseres.getElementsByTagName("charge_by_id")[i].firstChild.nodeValue;
                      var charge_type_desc=baseres.getElementsByTagName("charge_type_desc")[i].firstChild.nodeValue;
                      var txtAcc_HeadCode=baseres.getElementsByTagName("txtAcc_HeadCode")[i].firstChild.nodeValue;
                      var txtAcc_HeadCode_dr=baseres.getElementsByTagName("txtAcc_HeadCode_dr")[i].firstChild.nodeValue;
                      
                      var rowvalue=document.createElement("TR");
                      rowvalue.id=charge_by_id;
                      var tabledata=document.createElement("TD");
                      var anc=document.createElement("A");
                      var url="javascript:loadvaluesfromtable('" +charge_by_id+"')";
                      anc.href=url;
                      var nameval=document.createTextNode("Edit");
                      anc.appendChild(nameval);
                      tabledata.appendChild(anc);
                      rowvalue.appendChild(tabledata);
                      
                      
                      var tabledata1=document.createElement("TD");
                      var charge_by_id_val=document.createTextNode(charge_by_id);
                      tabledata1.appendChild(charge_by_id_val);
                      rowvalue.appendChild(tabledata1);
                      
                      var tabledata2=document.createElement("TD");
                      var charge_type_desc_val=document.createTextNode(charge_type_desc);
                      tabledata2.appendChild(charge_type_desc_val);
                      rowvalue.appendChild(tabledata2);
                      
                      
                      var tabledata3=document.createElement("TD");
                      var txtAcc_HeadCode_val=document.createTextNode(txtAcc_HeadCode);
                      tabledata3.appendChild(txtAcc_HeadCode_val);
                      rowvalue.appendChild(tabledata3);
                      
                      var tabledata4=document.createElement("TD");
                      var txtAcc_HeadCode_dr_val=document.createTextNode(txtAcc_HeadCode_dr);
                      tabledata4.appendChild(txtAcc_HeadCode_dr_val);
                      rowvalue.appendChild(tabledata4);
                      
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
                    var charge_by_id=baseres.getElementsByTagName("charge_type_Id")[0].firstChild.nodeValue;
                    var charge_type_desc=baseres.getElementsByTagName("charge_type_desc")[0].firstChild.nodeValue;
                    var txtAcc_HeadCode=baseres.getElementsByTagName("txtAcc_HeadCode")[0].firstChild.nodeValue;
                    var txtAcc_HeadCode_dr=baseres.getElementsByTagName("txtAcc_HeadCode_dr")[0].firstChild.nodeValue;
                              
                    var rvar=document.getElementById(charge_by_id);
                    var rcells=rvar.cells;
                    rcells.item(1).firstChild.nodeValue=charge_by_id;
                    rcells.item(2).firstChild.nodeValue=charge_type_desc;
                    rcells.item(3).firstChild.nodeValue=txtAcc_HeadCode;
                    rcells.item(4).firstChild.nodeValue=txtAcc_HeadCode_dr;
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
                     var charge_by_id=baseres.getElementsByTagName("charge_by_id")[0].firstChild.nodeValue;
                     var tbody=document.getElementById("existing");
                     var r=document.getElementById(charge_by_id);
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
function loadvaluesfromtable(r)
{
    var rvar=document.getElementById(r);
    var rcells=rvar.cells;
    document.getElementById('charge_type_Id').value=rcells.item(1).firstChild.nodeValue;
    document.getElementById('charge_type_desc').value=rcells.item(2).firstChild.nodeValue;
    document.getElementById('txtAcc_HeadCode').value=rcells.item(3).firstChild.nodeValue;
    document.getElementById('txtAcc_HeadCode_dr').value=rcells.item(4).firstChild.nodeValue;
    
    document.getElementById('txtAcc_HeadDesc').value="";
     document.getElementById('txtAcc_HeadDesc_dr').value="";
    document.getElementById('cmdadd').disabled=true;
    document.getElementById('cmdupdate').disabled=false;
    document.getElementById('cmddelete').disabled=false;
}
function refresh()
{
    document.getElementById('charge_type_Id').value="";
    document.getElementById('charge_type_desc').value="";
    document.getElementById('txtAcc_HeadCode').value="";
    document.getElementById('txtAcc_HeadDesc').value="";
    document.getElementById('txtAcc_HeadCode_dr').value="";
    document.getElementById('txtAcc_HeadDesc_dr').value="";
    
  
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
    var checkvalempty=document.getElementById('charge_type_desc').value;
    var txtAcc_HeadCodeempty=document.getElementById('txtAcc_HeadCode').value;
    var txtAcc_HeadCode_drempty=document.getElementById("txtAcc_HeadCode_dr").value
    var headcodelength=document.getElementById("txtAcc_HeadCode").value.length;
   var txtAcc_HeadCode_dr_len=document.getElementById("txtAcc_HeadCode_dr").value.length;
    if(checkvalempty=="")
    {
        alert("Select Charge Type Description");
        return false;
    }
    else if(txtAcc_HeadCodeempty=="")
    {
        alert("Enter Charge A/c Head(credit");
        return false;
    }
     else if(txtAcc_HeadCode_drempty=="")
    {
        alert("Enter Charge A/c Head(debit)");
        return false;
    }
   else if(headcodelength<6)
   {
        alert("Charge A/c Head(credit) should not be less than 6");
        return false;
   }
   else if(txtAcc_HeadCode_dr_len<6)
   {
        alert("Charge A/c Head(debit) should not be less than 6");
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