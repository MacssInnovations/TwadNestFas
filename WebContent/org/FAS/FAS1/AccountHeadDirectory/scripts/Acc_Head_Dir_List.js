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
function loadingMinor(Command)
{
    if(Command=="loadMinor")
        {   //alert(document.getElementById("Major_Grp"));
            var txtMajor_id=document.getElementById("Major_Grp").value;
            var url="../../../../../Acc_Head_Dir_List.view?Command=loadMinor&txtMajor_id="+txtMajor_id;
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
      var url="../../../../../Acc_Head_Dir_List.view?Command=StartingAlphabets&Alphabet="+alpha;
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
      var url="../../../../../Acc_Head_Dir_List.view?Command=StartingDigit&Digit="+alpha;
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
    var MajorGrp=document.AccList_form.Major_Grp.value;
    var MinorGrp=document.AccList_form.Minor_Grp.value;
    
    var url="../../../../../Acc_Head_Dir_List.view?Command=MajorMinor&MajorGroup="+MajorGrp+"&MinorGroup="+MinorGrp;
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
    var upper=document.AccList_form.upper_range.value;
    var lower=document.AccList_form.lower_range.value;
    var url="../../../../../Acc_Head_Dir_List.view?Command=Range&Upper="+upper+"&Lower="+lower;
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
                     
                    alert("No Record exist");
                    s=0;
                    var tbody=document.getElementById("tbody");
                      try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}
                }
                else
                {   
                
                    var AHCode_leng=baseResponse.getElementsByTagName("AHCode_leng");
                    if(AHCode_leng)
                    {
                    ///////////////////////////////
                       var i=0;
                  var tbody=document.getElementById("tbody");
                 
                  try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}
                   
                  s=0;
                for(i=0;i<AHCode_leng.length;i++)
                {
                    
                        var items=new Array();
                        items[0]=AHCode_leng[i].getElementsByTagName("AHCode")[0].firstChild.nodeValue;
                        items[1]=AHCode_leng[i].getElementsByTagName("AHDesc")[0].firstChild.nodeValue;
                        items[2]=AHCode_leng[i].getElementsByTagName("Maj_id")[0].firstChild.nodeValue;
                        items[3]=AHCode_leng[i].getElementsByTagName("Min_id")[0].firstChild.nodeValue;
                        items[4]=AHCode_leng[i].getElementsByTagName("Bal_type")[0].firstChild.nodeValue;
                        var tbody=document.getElementById("tbody");
                        var mycurrent_row=document.createElement("TR");
                        var descell=document.createElement("TD");
                      
                         if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                         {
                            var sc=document.createElement("<INPUT type='radio' name='sel' id='" +items[0]+"' >");
                         }
                         else
                         {
                         var sc=document.createElement("input");
                         sc.type="radio";
                         sc.name="sel";
                         sc.id=items[0];
                         }
                         sc.setAttribute("onclick","pick(this)");
                         sc.value=items[0];
                         descell.appendChild(sc);
                         mycurrent_row.appendChild(descell);
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
                                var currentText=document.createTextNode('');        // LEAVE SPACE AS BLANK
                            }
                            cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                        }
                        tbody.appendChild(mycurrent_row);
                }
             }
                
          
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
        if(val.length<6)
        {
        alert("Account Head Code shouldn't be less than 6 digit number");
        return false;
        }
    }
}
