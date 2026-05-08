
var service;
var __pagination=11;
var destid;
var totalblock=0;
////////////////////////////////////--------------- For loading Minor Group-------------
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

var Acc_Head_list_SL;

function Show(AHcode)
{
    if (Acc_Head_list_SL && Acc_Head_list_SL.open && !Acc_Head_list_SL.closed) 
    {
       Acc_Head_list_SL.resizeTo(500,500);
       Acc_Head_list_SL.moveTo(250,250); 
       Acc_Head_list_SL.focus();
    }
    else
    {
        Acc_Head_list_SL=null
    }
    Acc_Head_list_SL= window.open("../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Acc_Head_Dir_ListAll_SL.jsp?AHcode="+AHcode,"SL_Types","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    Acc_Head_list_SL.moveTo(250,250);  
    Acc_Head_list_SL.focus();
    
}

window.onunload=function()
{
if (Acc_Head_list_SL && Acc_Head_list_SL.open && !Acc_Head_list_SL.closed) Acc_Head_list_SL.close();
}

function loadingMinor(Command)
{
    if(Command=="loadMinor")
        {  
            startwaiting(document.AccList_form);
            var txtMajor_id=document.getElementById("Major_Grp").value;
            var url="../../../../../Acc_Head_Dir_List_InUse.view?Command=loadMinor&txtMajor_id="+txtMajor_id;
            //alert(url);
            var req=getTransport();
            req.open("POST",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
                    
        }
}
function ListBy(type,alpha)
{
   if(type=="StartingAlphabets")
   {     
     startwaiting(document.AccList_form);
      var usagestatus;
        if(document.AccList_form.usage[0].checked)
       {
           usagestatus="InUse";
       }
        else if(document.AccList_form.usage[1].checked)
        {
           usagestatus="NotInUse";
        }
      var url="../../../../../Acc_Head_Dir_List_InUse.view?Command=StartingAlphabets&Alphabet="+alpha+"&usagestatus="+usagestatus;
      var req=getTransport();
      req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
   }   
   else if(type=="StartingDigit")
   {
    startwaiting(document.AccList_form);
      var usagestatus;
        if(document.AccList_form.usage[0].checked)
       {
           usagestatus="InUse";
       }
        else if(document.AccList_form.usage[1].checked)
        {
           usagestatus="NotInUse";
        }
      var url="../../../../../Acc_Head_Dir_List_InUse.view?Command=StartingDigit&Digit="+alpha+"&usagestatus="+usagestatus;
      var req=getTransport();
      req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
   }   
}
function searchByMajorMinor()
{
    startwaiting(document.AccList_form);
    var MajorGrp=document.AccList_form.Major_Grp.value;
    var MinorGrp=document.AccList_form.Minor_Grp.value;
    var usagestatus;
        if(document.AccList_form.usage[0].checked)
       {
           usagestatus="InUse";
       }
        else if(document.AccList_form.usage[1].checked)
        {
           usagestatus="NotInUse";
        }
    //alert("usagestatus***************"+usagestatus);
    var url="../../../../../Acc_Head_Dir_List_InUse.view?Command=MajorMinor&MajorGroup="+MajorGrp+"&MinorGroup="+MinorGrp+"&usagestatus="+usagestatus;
   //alert(url);
     var req=getTransport();
     req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
}

function searchByRange()
{
    startwaiting(document.AccList_form);
    var upper=document.AccList_form.upper_range.value;
    var lower=document.AccList_form.lower_range.value;
    var usagestatus;
        if(document.AccList_form.usage[0].checked)
       {
           usagestatus="InUse";
       }
        else if(document.AccList_form.usage[1].checked)
        {
           usagestatus="NotInUse";
        }
    var url="../../../../../Acc_Head_Dir_List_InUse.view?Command=Range&Upper="+upper+"&Lower="+lower+"&usagestatus="+usagestatus;
    var req=getTransport();
    req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
}


function handleResponse(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {   
            //alert("here")
            stopwaiting(document.AccList_form);
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="loadMinor")
            {
                loadMinor(baseResponse);
            }
            else if(Command=="StartingDigit")
            {
                loadTable(baseResponse);
            }
            else if(Command=="StartingAlphabets")
            {
                loadTable(baseResponse);
            }
            else if(Command=="Range")
            {
                loadTable(baseResponse);
            }
            else if(Command=="MajorMinor")
            {
                loadTable(baseResponse);
            }
        }
    }
}

function loadTable(baseResponse)
{
    
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="failure")
                {
                     
                    alert("No Record exists");
                    s=0;
                   var tbody=document.getElementById("tbody");
                      try
                      {
                      
                      tbody.innerHTML='';
//                      document.getElementById("divpre").innerHTML='';
//                      document.getElementById("divnext").innerHTML='';
//                      document.getElementById("divcmbpage").innerHTML='';
//                      document.getElementById("divpage").innerHTML='';
                      document.getElementById("divpre").style.display='none';
                      document.getElementById("divnext").style.display='none';
                      document.getElementById("divcmbpage").style.display='none';
                      document.getElementById("divpage").style.display='none';
                     }
                  catch(e) {
                           tbody.innerText='';}
                                                                     
                }
                else if(flag=="success")
                {   
                    var tbody=document.getElementById("tbody");
                    if(tbody.rows.length >0)
                    {   
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null )
                                {
                                tbody.innerText='';
                                }
                        else 
                                tbody.innerHTML='';
                     }
                      service=baseResponse.getElementsByTagName("AHCode_leng");
                    if(service)
                    {
                       var tbody=document.getElementById("tbody");
                        try{tbody.innerHTML="";}
                        catch(e) {tbody.innerText="";}
                        var i=0;
                        totalblock=0;
                         if(service.length>0)
                         {
                                totalblock=parseInt(service.length/__pagination);
                                if(service.length%__pagination!=0)
                                {
                                        totalblock=totalblock+1;
                                }
                                var cmbpage=document.getElementById("cmbpage");
                                try{ cmbpage.innerHTML="";
                                   }catch(e){
                                    cmbpage.innerText="";
                                   }
                                 for(i=1;i<=totalblock;i++)
                                 {
                                       var option=document.createElement("OPTION");
                                       option.text=i;
                                       option.value=i;
                                       try
                                       {
                                          cmbpage.add(option);
                                       }catch(errorObject)
                                       {
                                          cmbpage.add(option,null);
                                       }
                                    } 
                            }
                          
                            loadPage(1);
            
                        }
                   
                
          
        }
}
function changepage()
{
var page=document.AccList_form.cmbpage.value;
loadPage(parseInt(page));
}  


function loadPage(page)
{
    
            var i=0;
            var c=0;
            var p=__pagination*(page-1);
            document.AccList_form.cmbpage.selectedIndex=page-1;
            var tbody=document.getElementById("tbody");
            try{tbody.innerHTML="";}
            catch(e) {tbody.innerText="";}  
              
             if(service)
             {
                s=0;
                var i=0;
                for(i=p;i<service.length&& c<__pagination;i++)
                {
                        c++;
                        var items=new Array();
                        items[0]=service[i].getElementsByTagName("AHCode")[0].firstChild.nodeValue;
                        items[1]=service[i].getElementsByTagName("AHDesc")[0].firstChild.nodeValue;
                        items[2]=service[i].getElementsByTagName("Maj_id")[0].firstChild.nodeValue;
                        items[3]=service[i].getElementsByTagName("Min_id")[0].firstChild.nodeValue;
                        items[4]=service[i].getElementsByTagName("Bal_type")[0].firstChild.nodeValue;
                        var tbody=document.getElementById("tbody");
                        var mycurrent_row=document.createElement("TR");
                    
                        for(j=0;j<5;j++)
                        {
                            cell2=document.createElement("TD");
                            cell2.setAttribute('align','left');
                            if(items[j]!="null")
                            {
                                var currentText=document.createTextNode(items[j]);
                            }
                            else
                            {
                                var currentText=document.createTextNode('');
                            }
                            cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                        }
                        var cell=document.createElement("TD");
                        cell.align='CENTER';
                        var anc=document.createElement("A");
                        var url="javascript:Show('"+items[0]+"')";
                        anc.href=url;
                        var txtedit=document.createTextNode("SL Types");
                        anc.appendChild(txtedit);
                        cell.appendChild(anc);
                        mycurrent_row.appendChild(cell);
                        tbody.appendChild(mycurrent_row);
                    }
            
                }          
            
                       var cell=document.getElementById("divcmbpage");
                            cell.style.display="block";
                       var cell=document.getElementById("divpage");
                            cell.style.display="block";
                           
                       if(navigator.appName.indexOf("Microsoft")!=-1)
                            cell.innerText= ' / ' +totalblock;
                       else
                            cell.innerHTML= ' / ' +totalblock;
            if(page<totalblock)
            {
                var cell=document.getElementById("divnext");
                cell.style.display="block";
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
                 var anc=document.createElement("A");
                var url="javascript:loadPage("+(page+1)+")";
                anc.href=url;
                //anc.setAttribute('style','text-decoratin:none');
                var txtedit=document.createTextNode("<<Next>>");
                anc.appendChild(txtedit);
                cell.appendChild(anc);
            }
            else
            {
                var cell=document.getElementById("divnext");
                cell.style.display="block";
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
            
            }
             if(page>1)
            {
                var cell=document.getElementById("divpre");
                cell.style.display="block";
                //cell.innerText='';
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
                 var anc=document.createElement("A");
                var url="javascript:loadPage("+(page-1)+")";
                anc.href=url;
                var txtedit=document.createTextNode("<<Previous>>");
                anc.appendChild(txtedit);
                cell.appendChild(anc);
            }
            else
            {
                var cell=document.getElementById("divpre");
                cell.style.display="block";
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
            
            }
}



function changepagesize()
{

           __pagination=document.AccList_form.cmbpagination.value;
            var v=document.getElementsByName("sel");
            //alert(v);
                if(service)
                {
                            totalblock=0;
                            if(service.length>0)
                            {
                                    totalblock=parseInt(service.length/__pagination);
                                    if(service.length%__pagination!=0)
                                    {
                                            totalblock=totalblock+1;
                                    }
                                    var cmbpage=document.getElementById("cmbpage");
                                       
                                       try{ cmbpage.innerHTML="";
                                       }catch(e){
                                        cmbpage.innerText="";
                                       }
                                    for(i=1;i<=totalblock;i++)
                                    {
                                          var option=document.createElement("OPTION");
                                          option.text=i;
                                          option.value=i;
                                          try
                                          {
                                                cmbpage.add(option);
                                           }catch(errorObject)
                                           {cmbpage.add(option,null);}
                                     } 
                             }
                             loadPage(1);
            }
}

function loadMinor(baseResponse)
{

var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {   
        var Maj_id=baseResponse.getElementsByTagName("Maj_id");
        var items_maj=new Array();
        var items_min=new Array();
        var items_desc=new Array();
        var min_id=document.getElementById("Minor_Grp");
        
        for(var k=0;k<Maj_id.length;k++)
        {
             items_maj[k]=baseResponse.getElementsByTagName("Maj_id")[k].firstChild.nodeValue;   
             items_min[k]=baseResponse.getElementsByTagName("Min_id")[k].firstChild.nodeValue;
             items_desc[k]=baseResponse.getElementsByTagName("Min_desc")[k].firstChild.nodeValue;
        }
        min_id.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="All";
        option.value="All";
        try
        {
            min_id.add(option);
        }catch(errorObject)
        {
            min_id.add(option,null);
        }
        
        for(var k=0;k<Maj_id.length;k++)
        {   
              var option=document.createElement("OPTION");
              option.text=items_desc[k];
              option.value=items_min[k];
               try
              {
                  min_id.add(option);
              }
              catch(errorObject)
              {
                  min_id.add(option,null);
              }
        }
    }
}           

////////////////////////////////////---------------  End -------------            
function callServer()
{

            //alert(document.AccList_form.search[0].checked);
           // alert(document.AccList_form.usage[0].checked);
                 if(document.AccList_form.search[0].checked)
                 {
                    document.AccList_form.upper_range.disabled=true;
                    document.AccList_form.lower_range.disabled=true;
                    document.AccList_form.Major_Grp.disabled=false;
                    document.AccList_form.Minor_Grp.disabled=false;
                    document.AccList_form.MajMin.disabled=false;
                    document.AccList_form.Range.disabled=true;
                    disableAllAnchors('alphalinks');
                    disableAllAnchors('numericlinks');
                    document.AccList_form.upper_range.style.background="Silver";
                    document.AccList_form.lower_range.style.background="Silver";
                  }
                 else if(document.AccList_form.search[1].checked)
                 {
                    document.AccList_form.Major_Grp.disabled=true;
                    document.AccList_form.Minor_Grp.disabled=true;
                    document.AccList_form.upper_range.disabled=true;
                    document.AccList_form.lower_range.disabled=true;
                    //document.AccList_form.MajGrp.disabled=true;
                    //document.AccList_form.MinGrp.disabled=true;
                    document.AccList_form.MajMin.disabled=true;
                    document.AccList_form.Range.disabled=true;
                    enableAllAnchors('alphalinks');
                    disableAllAnchors('numericlinks');
                    document.AccList_form.upper_range.style.background="Silver";
                    document.AccList_form.lower_range.style.background="Silver";
                    //document.AccList_form.MajGrp.style.background="Silver";
                    //document.AccList_form.MinGrp.style.background="Silver";
                  }
                 else if(document.AccList_form.search[2].checked)
                 {
                     document.AccList_form.Major_Grp.disabled=true;
                     document.AccList_form.Minor_Grp.disabled=true;
                     document.AccList_form.upper_range.disabled=true;
                     document.AccList_form.lower_range.disabled=true;
                     //document.AccList_form.MajGrp.disabled=true;
                     //document.AccList_form.MinGrp.disabled=true;
                     document.AccList_form.MajMin.disabled=true;
                     document.AccList_form.Range.disabled=true;
                     enableAllAnchors('numericlinks');
                     disableAllAnchors('alphalinks');
                     document.AccList_form.upper_range.style.background="Silver";
                     document.AccList_form.lower_range.style.background="Silver";
                     //document.AccList_form.MajGrp.style.background="Silver";
                     //document.AccList_form.MinGrp.style.background="Silver";
                 }
                else if(document.AccList_form.search[3].checked)
                {
                     document.AccList_form.Major_Grp.disabled=true;
                     document.AccList_form.Minor_Grp.disabled=true;
                     //document.AccList_form.MajGrp.disabled=true;
                     //document.AccList_form.MinGrp.disabled=true;  
                     document.AccList_form.upper_range.disabled=false;
                     document.AccList_form.lower_range.disabled=false;
                     document.AccList_form.MajMin.disabled=true;
                     document.AccList_form.Range.disabled=false;
                     disableAllAnchors('alphalinks');
                     disableAllAnchors('numericlinks');
                     //document.AccList_form.MajGrp.style.background="Silver";
                     //document.AccList_form.MinGrp.style.background="Silver";
                     document.AccList_form.upper_range.style.background="White";
                     document.AccList_form.lower_range.style.background="White";
                }
                
} 

// code to enable all anchors
function enableAllAnchors(divID)
{
  var div=document.getElementById(divID);  
  var anchors=div.getElementsByTagName("a");
  var i;
  for(i=0;i<anchors.length;i++)
  {
    fncEnable(anchors.item(i));
  }
}
function fncEnable(obj)
{   
   try
   {
     obj.setAttribute('href', obj.attributes['href_bak'].nodeValue);
     obj.style.color="blue";      
   }
   catch(e){}
}
//code to disable all anchors
function disableAllAnchors(divID)
{
  var div=document.getElementById(divID);  
  var anchors=div.getElementsByTagName("a");
  var i;
  for(i=0;i<anchors.length;i++)
  {
    fncDisable(anchors.item(i));
  }
}

function fncDisable(obj)
{      
      if (window.navigator.appName.toLowerCase().indexOf("microsoft") > -1) { // IE;
      if(obj.href!="")
        obj.setAttribute('href_bak', obj.attributes['href'].nodeValue);
      }
      else{
      if (window.navigator.appName.toLowerCase().indexOf("netscape") > -1) // Firefox
      {
        if(obj.attributes['href']!=null)
          obj.setAttribute('href_bak', obj.attributes['href'].nodeValue);
      }
      else
        alert("Error: This application does not support your browser.  Try again using IE or Firefox.");
    }
    obj.removeAttribute('href');
    obj.style.color="gray";
}

////////////////////////////////////////////////////////////////------------------


function MajorValue()
{
var majorgrp=document.AccList_form.Major_Grp.value;
//document.AccList_form.MajGrp.value=majorgrp;
}

function MinorValue()
{
var minorgrp=document.AccList_form.Minor_Grp.value;
//document.AccList_form.MinGrp.value=minorgrp;
}
 function callLink(type,alphabet)
{
  if(type=="A")
  {    
    ListBy('StartingAlphabets',alphabet);
  }
  else if(type=="N")
  {
    ListBy('StartingDigit',alphabet);
  }
}


function btnsubmit()
{
//alert('hai');
var v=document.getElementsByName("sel");
    if(v)
    {
        for(i=0;i<v.length;i++)
        {
            if(v[i].checked==true)
            {
               // opener.document.HRE_EmployeeServiceDetails.txtEmployeeid.value = (v[i].value);
              //  try{self.opener.doFunction('loademp','null');}catch(e){}
                Minimize();
                opener.doParentAccHead(v[i].value);
                //opener.focus();
                return true;
            }
           
        }
    }
    else
    {
               alert('Select an Employee ');
               return false; 
    }
}  
function btncancel()
{

 self.close();
}

function Minimize() 
{
window.resizeTo(0,0);
window.screenX = screen.width;
window.screenY = screen.height;
opener.window.focus();
}
function pick(t)
{
    s=t.value;
}

function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          //t.blur();
          //return true;-------------------- for taking action when press ENTER
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48 || unicode>57 ) 
                return false 
        }
     }

function sixdigit(val)
{

 if(val.length!=0)
    {
        if((val.length<6) && (val.length>6))
        {
        alert("Account Head Code shouldn't be less or greater than 6 digit number");
        return false;
        }
    }
}
