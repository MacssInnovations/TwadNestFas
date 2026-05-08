var rad_val1=false;

function callServer()
{


              if(document.formList.search[0].checked)
                {
                
                document.formList.upper_range.disabled=true;
                document.formList.lower_range.disabled=true;
                document.formList.Major_Grp.disabled=false;
                document.formList.Minor_Grp.disabled=false;
                document.formList.MajMin.disabled=false;
                document.formList.Range.disabled=true;
                disableAllAnchors('alphalinks');
                disableAllAnchors('numericlinks');
                document.formList.upper_range.style.background="Silver";
                document.formList.lower_range.style.background="Silver";
                
                
                }
               else if(document.formList.search[1].checked)
                {
                
                document.formList.Major_Grp.disabled=true;
                document.formList.Minor_Grp.disabled=true;
                document.formList.upper_range.disabled=true;
                document.formList.lower_range.disabled=true;
                document.formList.MajGrp.disabled=true;
                document.formList.MinGrp.disabled=true;
                document.formList.MajMin.disabled=true;
                document.formList.Range.disabled=true;
                enableAllAnchors('alphalinks');
                disableAllAnchors('numericlinks');
                document.formList.upper_range.style.background="Silver";
                document.formList.lower_range.style.background="Silver";
                document.formList.MajGrp.style.background="Silver";
                document.formList.MinGrp.style.background="Silver";
                
                
                
                }
              else if(document.formList.search[2].checked)
                 {
                 
                 document.formList.Major_Grp.disabled=true;
                 document.formList.Minor_Grp.disabled=true;
                 document.formList.upper_range.disabled=true;
                 document.formList.lower_range.disabled=true;
                 document.formList.MajGrp.disabled=true;
                 document.formList.MinGrp.disabled=true;
                 document.formList.MajMin.disabled=true;
                 document.formList.Range.disabled=true;
                 enableAllAnchors('numericlinks');
                 disableAllAnchors('alphalinks');
                 document.formList.upper_range.style.background="Silver";
                 document.formList.lower_range.style.background="Silver";
                 document.formList.MajGrp.style.background="Silver";
                 document.formList.MinGrp.style.background="Silver";
                 
                 }
             else if(document.formList.search[3].checked)
                {
                 
                 document.formList.Major_Grp.disabled=true;
                 document.formList.Minor_Grp.disabled=true;
                 document.formList.MajGrp.disabled=true;
                 document.formList.MinGrp.disabled=true;  
                 document.formList.upper_range.disabled=false;
                 document.formList.lower_range.disabled=false;
                 document.formList.MajMin.disabled=true;
                 document.formList.Range.disabled=false;
                 disableAllAnchors('alphalinks');
                 disableAllAnchors('numericlinks');
                 document.formList.MajGrp.style.background="Silver";
                 document.formList.MinGrp.style.background="Silver";
                 document.formList.upper_range.style.background="White";
                 document.formList.lower_range.style.background="White";
                 
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


function ListBy(type,alpha)
{
   if(type=="StartingAlphabets")
   {     
      var url="../../../../../ListServlet.view?Type=StartingAlphabets&Alphabet="+alpha;
      var iframe=document.getElementsByTagName("iframe")[0];
      if(iframe)
      {
         iframe.src=url;
      }
   }   
   else if(type=="StartingDigit")
   {
      var url="../../../../../ListServlet.view?Type=StartingDigit&Digit="+alpha;
      var iframe=document.getElementsByTagName("iframe")[0];
      if(iframe)
      {
         iframe.src=url;
      }     
   }   
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

function searchByMajorMinor()
{
    var MajorGrp=document.formList.Major_Grp.value;
    var MinorGrp=document.formList.Minor_Grp.value;
    var iframe=document.getElementsByTagName("iframe")[0];
    if(iframe)
    {
        iframe.src="../../../../../ListServlet.view?Type=MajorMinor&MajorGroup="+MajorGrp+"&MinorGroup="+MinorGrp;
    }
}

function searchByRange()
{
    var upper=document.formList.upper_range.value;
    var lower=document.formList.lower_range.value;
    var iframe=document.getElementsByTagName("iframe")[0];
    if(iframe)
    {
        iframe.src="../../../../../ListServlet.view?Type=Range&Upper="+upper+"&Lower="+lower;
    }
}


function MajorValue()
{
var majorgrp=document.formList.Major_Grp.value;
document.formList.MajGrp.value=majorgrp;
}

function MinorValue()
{
var minorgrp=document.formList.Minor_Grp.value;
document.formList.MinGrp.value=minorgrp;
}

