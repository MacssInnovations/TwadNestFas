//alert('detail');

var regionflag=false;
var circleflag=true;
var divisionflag=true;

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


function hideofficelevel()
{
    officevisible('none','none','none'); 
    hideoffice()
    var offlevel=document.getElementById("troffice");
            offlevel.style.display="none";
            
    document.LongLeaveReport.optofficelevel[0].checked=true;
    var offlevel=document.getElementById("trofficelevel");
    offlevel.style.display="none";
}

function showofficelevel()
{
    document.LongLeaveReport.optofficelevel[1].checked=true;
    var offlevel=document.getElementById("trofficelevel");
    offlevel.style.display="block";
}




function officevisible(r,c,d)
{
    var rn=document.getElementById("divregion");
    rn.style.display=r;
    var cl=document.getElementById("divcircle");
    cl.style.display=c;
    var dn=document.getElementById("divdivision");
    dn.style.display=d;
    
    var rn=document.getElementById("cmbregion");
    rn.style.display=r;
    var cl=document.getElementById("cmbcircle");
    cl.style.display=c;
    var dn=document.getElementById("cmbdivision");
    dn.style.display=d;
}



function hideoffice()
{
    document.LongLeaveReport.optoffice[0].checked=true;
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="none";
}

function showoffice()
{
   
    document.LongLeaveReport.optoffice[1].checked=true;
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //alert('hai');
    var type=document.LongLeaveReport.cmbolevel.options[document.LongLeaveReport.cmbolevel.selectedIndex].value;
    if(type=='RN') 
    {
        officevisible('block','none','none');
        
    }
    else if(type=='CL')     {
       officevisible('block','block','none');
    }

    else if(type=='DN')     {
       officevisible('block','block','block');
    }
    document.LongLeaveReport.cmbcircle.disabled=true;
    document.LongLeaveReport.cmbdivision.disabled=true;
    
    var iframe=document.getElementById("diviframeregion");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframecircle");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframedivision");
    iframe.style.visibility='hidden';
    
    
    regionflag=false;
    circleflag=true;
    divisionflag=true;
    
    
    
}


var s=0;
var hier=true;
var level=true;
var city=true;
var  other=true;

function getLevel()
    {
        var type=document.LongLeaveReport.cmbolevel.options[document.LongLeaveReport.cmbolevel.selectedIndex].value;
        //alert('getoffice:'+type);
       officevisible('none','none','none'); 
       hideoffice()
      if(type=="")
      {
            var offlevel=document.getElementById("troffice");
            offlevel.style.display="none";
      }
      else
      {
            
             if(type!='HO') 
            {
                 var offlevel=document.getElementById("troffice");
                 offlevel.style.display="block";        
            }
            
             if(type=='DN')
              {
                var agg=document.getElementById("aggreate");
                agg.style.display="block";
              }
              else
              {
                var agg=document.getElementById("aggreate");
                agg.style.display="none";
                document.LongLeaveReport.aggid.checked=false;
              }
           
      }
    }
    
    
  
function getRegion()
    {
       // alert(regionflag);
              
        
        if(regionflag==true)
        {
               
                 var iframe=document.getElementById("diviframeregion");
                 iframe.style.visibility='visible';
                 iframe.focus();
                return;
        }
        var type=document.LongLeaveReport.cmbolevel.options[document.LongLeaveReport.cmbolevel.selectedIndex].value;
    
         var url="../../../../../../LongLeaveReportNewServ1.rep?OLevel=region" ;
     // alert(url);
                
        document.LongLeaveReport.cmbregion.focus();
        var req=getTransport();
        req.open("GET",url,false);        
        req.onreadystatechange=function()
        {
            //requesthandle(req);
            if(req.readyState==4)
            { 
                  if(req.status==200)
                  {  
                   // alert(req.responseText);
                 
                 // document.frames("iframregion").document.body.innerHTML=req.responseText;
                  //(document.frames("iframregion").document.body.innerText);
                    var iframe=document.getElementById("diviframeregion");
                    iframe.style.visibility='visible';
                    iframe.focus();
                   // alert(navigator.appName);
                   // alert(navigator.appName.indexOf('Microsoft'));
                    if(navigator.appName.indexOf('Microsoft')!=-1)
                        iframe.innerHTML=req.responseText;
                    else
                        iframe.innerText=req.responseText;
                       
                   
                  document.LongLeaveReport.cmbcircle.disabled=false;
                   var iframe=document.getElementById("diviframecircle");
                            iframe.style.visibility='hidden';
                   var iframe=document.getElementById("diviframedivision");
                            iframe.style.visibility='hidden';
                   
                   regionflag=true;
                  }
            }
        }
        req.send(null);
    }
    
    
function getCircle(cir)
    {
    
        
       var region="";
        var type=document.LongLeaveReport.cmbolevel.options[document.LongLeaveReport.cmbolevel.selectedIndex].value;
    
      if(document.LongLeaveReport.chkregion)
      {
               if(document.LongLeaveReport.chkregion.length)
              {
             
                            for(i=0;i<document.LongLeaveReport.chkregion.length;i++)
                            {
                                    if(document.LongLeaveReport.chkregion[i].checked==true)
                                            region= region+document.LongLeaveReport.chkregion[i].value +",";
                                    
                            }
                            
                            if(region!="")
                            {
                                 if(circleflag==true && cir=='null')
                                {
                                       
                                         var iframe=document.getElementById("diviframecircle");
                                            iframe.style.visibility='visible';
                                          iframe.focus();
                                           document.LongLeaveReport.cmbdivision.disabled=false;
                                        return;
                                }
                                region=region.substring(0,region.length-1);
                                var url="../../../../../../LongLeaveReportNewServ1.rep?OLevel=circle&regions="+region;
                              // document.LongLeaveReport.cmbregion.focus();
                                var req=getTransport();
                                req.open("GET",url,false);        
                                req.onreadystatechange=function()
                                {
                                    //requesthandle(req);
                                    if(req.readyState==4)
                                    { 
                                          if(req.status==200)
                                          {  
                                           var iframe=document.getElementById("diviframecircle");
                                             if((type=='CL' || type=='DN') && cir=='null' )
                                            {
                                            
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                            
                                            document.LongLeaveReport.cmbdivision.disabled=false;
                                              var iframe=document.getElementById("diviframedivision");
                                             iframe.style.visibility='hidden';
                                            
                                            
                                            }
                                             iframe.innerHTML=req.responseText;
                                             
                                             //alert(iframe.innerHTML);
                                             circleflag=true;
                                            
                                          }
                                    }
                                }
                                req.send(null);
                            }
                            else
                            {
                                var iframe=document.getElementById("diviframecircle");
                                iframe.style.visibility='hidden';
                                document.LongLeaveReport.cmbdivision.disabled=false;
                                document.LongLeaveReport.cmbcircle.focus();
                                alert('Please Select a Region');
                            }
            }
            else
            {
            
                             if(document.LongLeaveReport.chkregion.checked==true)
                             {
                                            region= document.LongLeaveReport.chkregion.value ;
                                    
                                    
                                         if(circleflag==true && cir=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframecircle");
                                                    iframe.style.visibility='visible';
                                                  iframe.focus();
                                                   document.LongLeaveReport.cmbdivision.disabled=false;
                                                return;
                                        }
                                       // region=region.substring(0,region.length-1);
                                        var url="../../../../../../LongLeaveReportNewServ1.rep?OLevel=circle&regions="+region;
                                      // document.LongLeaveReport.cmbregion.focus();
                                        var req=getTransport();
                                        req.open("GET",url,false);        
                                        req.onreadystatechange=function()
                                        {
                                            //requesthandle(req);
                                            if(req.readyState==4)
                                            { 
                                                  if(req.status==200)
                                                  {  
                                                   var iframe=document.getElementById("diviframecircle");
                                                     if((type=='CL' || type=='DN') && cir=='null' )
                                                    {
                                                    
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                    
                                                    document.LongLeaveReport.cmbdivision.disabled=false;
                                                      var iframe=document.getElementById("diviframedivision");
                                                     iframe.style.visibility='hidden';
                                                    
                                                    
                                                    }
                                                     iframe.innerHTML=req.responseText;
                                                     
                                                     //alert(iframe.innerHTML);
                                                     circleflag=true;
                                                    
                                                  }
                                            }
                                        }
                                        req.send(null);
                            }
                            else
                            {
                                var iframe=document.getElementById("diviframecircle");
                                iframe.style.visibility='hidden';
                                document.LongLeaveReport.cmbdivision.disabled=false;
                                document.LongLeaveReport.cmbcircle.focus();
                                alert('Please Select a Region');
                            }

            
            
            
            
            }
            
      }
   
}


function getDivision(div)
    {
        
       // alert('division');
       var circle="";
      if(document.LongLeaveReport.chkcircle)
      {
      
            if(document.LongLeaveReport.chkcircle.length)
            {
            
                            for(i=0;i<document.LongLeaveReport.chkcircle.length;i++)
                            {
                                    if(document.LongLeaveReport.chkcircle[i].checked==true)
                                            circle= circle+document.LongLeaveReport.chkcircle[i].value +",";
                                    
                            }
                            if(circle!="")
                            {
                               
                                //alert(circle);
                               // alert(divisionflag);
                                 if(divisionflag==true && div=='null')
                                {
                                       
                                         var iframe=document.getElementById("diviframedivision");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                        return;
                                }
                                circle=circle.substring(0,circle.length-1);
                                var url="../../../../../../LongLeaveReportNewServ1.rep?OLevel=division&circles="+circle;
                              // document.LongLeaveReport.cmbregion.focus();
                                var req=getTransport();
                                req.open("GET",url,false);        
                                req.onreadystatechange=function()
                                {
                                    //requesthandle(req);
                                    if(req.readyState==4)
                                    { 
                                          if(req.status==200)
                                          { 
                                            var iframe=document.getElementById("diviframedivision");
                                            var type=document.LongLeaveReport.cmbolevel.options[document.LongLeaveReport.cmbolevel.selectedIndex].value;
                                            if(type=='DN' && div=='null')
                                            {
                                            
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                            }
                                             iframe.innerHTML=req.responseText;
                                             
                                           divisionflag=true;  
                                          }
                                    }
                                }
                                req.send(null);
                            }
                            else
                            {
                                var iframe=document.getElementById("diviframedivision");
                                iframe.style.visibility='hidden';
                                document.LongLeaveReport.cmbdivision.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                }
                else
                {
                
                             if(document.LongLeaveReport.chkcircle.checked==true)
                             {
                                            circle= document.LongLeaveReport.chkcircle.value ;
                                    
                           
                                         if(divisionflag==true && div=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframedivision");
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                return;
                                        }
                                       // circle=circle.substring(0,circle.length-1);
                                        var url="../../../../../../LongLeaveReportNewServ1.rep?OLevel=division&circles="+circle;
                                      // document.LongLeaveReport.cmbregion.focus();
                                        var req=getTransport();
                                        req.open("GET",url,false);        
                                        req.onreadystatechange=function()
                                        {
                                            //requesthandle(req);
                                            if(req.readyState==4)
                                            { 
                                                  if(req.status==200)
                                                  { 
                                                    var iframe=document.getElementById("diviframedivision");
                                                    var type=document.LongLeaveReport.cmbolevel.options[document.LongLeaveReport.cmbolevel.selectedIndex].value;
                                                    if(type=='DN' && div=='null')
                                                    {
                                                    
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                    }
                                                     iframe.innerHTML=req.responseText;
                                                     
                                                   divisionflag=true;  
                                                  }
                                            }
                                        }
                                        req.send(null);
                            }
                            else
                            {
                                var iframe=document.getElementById("diviframedivision");
                                iframe.style.visibility='hidden';
                                document.LongLeaveReport.cmbdivision.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                
                }
            
      }
   
}

function regionclose()
{
    
    var iframe=document.getElementById("diviframeregion");
    iframe.style.visibility='hidden';
  
}


function regionselectAll()
{

   if(document.LongLeaveReport.chkregion)
      {
      
            
            for(i=0;i<document.LongLeaveReport.chkregion.length;i++)
            {
                    document.LongLeaveReport.chkregion[i].checked=true;
                    
            }
             regiononchange();
        }
}


function circleclose()
{
    
    var iframe=document.getElementById("diviframecircle");
    iframe.style.visibility='hidden';
  
}


function circleselectAll()
{
    if(document.LongLeaveReport.chkcircle)
      {
      
            for(i=0;i<document.LongLeaveReport.chkcircle.length;i++)
            {
                    document.LongLeaveReport.chkcircle[i].checked=true;
                    
            }
            circleonchange();
        }
}

function divisionclose()
{
    
    var iframe=document.getElementById("diviframedivision");
    iframe.style.visibility='hidden';
  
}


function divisionselectAll()
{
    if(document.LongLeaveReport.chkdivision)
      {
      
            for(i=0;i<document.LongLeaveReport.chkdivision.length;i++)
            {
                    document.LongLeaveReport.chkdivision[i].checked=true;
                    
            }
        }
}

function regiononchange()
{
     var type=document.LongLeaveReport.cmbolevel.options[document.LongLeaveReport.cmbolevel.selectedIndex].value;
   circlefalg=false;
    if(type=='CL' || type=='DN')
    {
        getCircle('circle');
    }

}

function circleonchange()
{
     var type=document.LongLeaveReport.cmbolevel.options[document.LongLeaveReport.cmbolevel.selectedIndex].value;
    divisionfalg=false;
    if(type=='DN')
    {
    getDivision('division');
    }

}



function hidedisignation()
{
    document.LongLeaveReport.optdesignation[0].checked=true;
   var offlevel=document.getElementById("trsergroup");
    offlevel.style.display="none";
    
      var offlevel=document.getElementById("divdes");
    offlevel.style.visibility="hidden";
     var offlevel=document.getElementById("cmbdes");
    offlevel.style.visibility="hidden";
    document.LongLeaveReport.cmbsgroup.value="0";
}

function showdesignation()
{
   document.LongLeaveReport.optdesignation[1].checked=true;
    var offlevel=document.getElementById("trsergroup");
    offlevel.style.display="block";
}

function getDesignation()
    {
        var type=document.LongLeaveReport.cmbsgroup.options[document.LongLeaveReport.cmbsgroup.selectedIndex].value;
        if(type!=0)
        {
            var din=document.getElementById("divdes");
            din.style.visibility="visible";
            document.LongLeaveReport.cmbdes.style.visibility="visible";
            loadOfficesByType(type);
        }
        else
        {
             var din=document.getElementById("divdes");
            din.style.visibility="hidden";
            document.LongLeaveReport.cmbdes.style.visibility="hidden";
        }
    }
    
function loadOfficesByType(type)
    {
        //alert(type);
        var type=document.LongLeaveReport.cmbsgroup.options[document.LongLeaveReport.cmbsgroup.selectedIndex].value;
        //startwaiting(document.LongLeaveReport) ;
         service=null;
      
       var url="../../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadDesignation(req);
        }
        req.send(null);
    }
    
function  loadDesignation(req)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
               
                var des=document.getElementById("cmbdes");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                des.innerHTML="";
                
                // stopwaiting(document.LongLeaveReport);
                if(flag=="failure")
                {
                    alert("No Designation exists under this level");
                }
                else
                {   
                
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="--Select Designation--";
                    option.value="0";
                    try
                    {
                        des.add(option);
                    }catch(errorObject)
                    {
                        des.add(option,null);
                    }
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                        var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                        var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                          option.text=name;
                          option.value=id;
                          //Making Browser Independent
                          try
                          {
                              des.add(option);
                          }
                          catch(errorObject)
                          {
                              des.add(option,null);
                          }
                    }
                
                }
        
        }
        
    }
    

}


function frmsubmit()
{
    //alert('submit');
        var url="../../../../../../LongLeaveReportNewServ1.rep?OLevel=submit" ;
        
        //office validation
        if(document.LongLeaveReport.optofficelevel[0].checked==true)
        {
                url=url+"&offlevel=all";
                document.LongLeaveReport.offlevel.value='all';
        }
        else
        {
                url=url+"&offlevel=specific";
                 document.LongLeaveReport.offlevel.value='specific';
                
                // select the office 
                  var type=document.LongLeaveReport.cmbolevel.options[document.LongLeaveReport.cmbolevel.selectedIndex].value;
                 if(type=="")
                 {
                    alert('Select the Office Level');
                    document.LongLeaveReport.cmbolevel.focus();
                    return;
                }
                else
                {
                    url=url+"&office="+type;
                    document.LongLeaveReport.office.value=type;
                    
                    if(document.LongLeaveReport.optoffice[0].checked==true)
                    {
                            url=url+"&officetype=all";
                             document.LongLeaveReport.officetype.value='all';
                    }
                    else
                    {
                            url=url+"&officetype=specific";
                             document.LongLeaveReport.officetype.value='specific';
                            if(type=='RN')
                            {
                            ////////
                            var region="";
                            if(document.LongLeaveReport.chkregion)
                              {
                              
                                        if(document.LongLeaveReport.chkregion.length)
                                        {
                                                  for(i=0;i<document.LongLeaveReport.chkregion.length;i++)
                                                    {
                                                            if(document.LongLeaveReport.chkregion[i].checked==true)
                                                                    region= region+document.LongLeaveReport.chkregion[i].value +",";
                                                            
                                                    }
                                                    if(region!="")
                                                    {
                                                        region=region.substring(0,region.length-1);
                                                         url=url+"&officeselected="+region;
                                                         document.LongLeaveReport.officeselected.value=region;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Region.');
                                                            document.LongLeaveReport.cmbregion[0].focus(); 
                                                            return;
                                                    }
                                            
                                        }
                                        else{
                                           
                                                if(document.LongLeaveReport.chkregion.checked==true)
                                                {
                                                            region= document.LongLeaveReport.chkregion.value ;
                                                            document.LongLeaveReport.officeselected.value=region;
                                                }
                                                 else
                                                    {
                                                           alert('Select the Region..');
                                                            document.LongLeaveReport.cmbregion.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                 else
                                {
                                       alert('Select the Region...');
                                        document.LongLeaveReport.cmbregion.focus(); 
                                        return ;
                                }    
                            ////
                            
                            }
                            
                            else if(type=='CL')
                            {
                            
                            ////////
                            var circle="";
                            if(document.LongLeaveReport.chkcircle)
                              {
                              
                                       if(document.LongLeaveReport.chkcircle.length )
                                        {
                                                
                                                    for(i=0;i<document.LongLeaveReport.chkcircle.length;i++)
                                                    {
                                                            if(document.LongLeaveReport.chkcircle[i].checked==true)
                                                                    circle= circle+document.LongLeaveReport.chkcircle[i].value +",";
                                                            
                                                    }
                                                    if(circle!="")
                                                    {
                                                        circle=circle.substring(0,circle.length-1);
                                                         url=url+"&officeselected="+circle;
                                                         document.LongLeaveReport.officeselected.value=circle;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.LongLeaveReport.cmbcircle.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.LongLeaveReport.chkcircle.checked==true)
                                                {
                                                            circle= document.LongLeaveReport.chkcircle.value ;
                                                            document.LongLeaveReport.officeselected.value=circle;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Circle..');
                                                            document.LongLeaveReport.cmbcircle.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Circle...');
                                       try{
                                        document.LongLeaveReport.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.LongLeaveReport.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                            else  if(type=='DN')
                            {
                           
                            ////////
                            var division="";
                            if(document.LongLeaveReport.chkdivision)
                              {
                             //alert(document.LongLeaveReport.chkregion.length);
                                     if(document.LongLeaveReport.chkdivision.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.LongLeaveReport.chkdivision.length;i++)
                                                {
                                                        if(document.LongLeaveReport.chkdivision[i].checked==true)
                                                                division= division+document.LongLeaveReport.chkdivision[i].value +",";
                                                        
                                                }
                                                
                                                if(division!="")
                                                {
                                                    division=division.substring(0,division.length-1);
                                                     url=url+"&officeselected="+division;
                                                     document.LongLeaveReport.officeselected.value=division;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the Division.');
                                                        document.LongLeaveReport.cmbdivision.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.LongLeaveReport.chkdivision.checked==true)
                                                {
                                                                division= document.LongLeaveReport.chkdivision.value;
                                                                    //division=division.substring(0,division.length-1);
                                                                 url=url+"&officeselected="+division;
                                                                 document.LongLeaveReport.officeselected.value=division;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the Division..');
                                                        document.LongLeaveReport.cmbdivision.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the Division...');
                                       try{
                                        document.LongLeaveReport.cmbdivision.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.LongLeaveReport.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.LongLeaveReport.cmbregion.focus(); 
                                        }
                                        }
                                         return;
                                }
                            
                            }
                        
                    }
                        
                    
                }
        }
       
      
      
      
      //check date
     /* if(document.LongLeaveReport.cmbfromyear.value > document.LongLeaveReport.cmbtoyear.value)
        {
               alert("year from  should be less than year To");
               return;
        }
        
        if(document.LongLeaveReport.cmbfromyear.value == document.LongLeaveReport.cmbtoyear.value)
        {
              if(document.LongLeaveReport.cmbfrommonth.value == document.LongLeaveReport.cmbtomonth.value)
                {
                       alert("month from  should be less than month To");
                       return;
                }
        }*/
        
         var c=checkdate();
       if(c==false)
       {
            document.LongLeaveReport.txtDateFrom.focus();
            return false;
        }
        
      
        //select the output type
        if(document.LongLeaveReport.optoutputtype[0].checked==true)
        {
                url=url+"&outputtype=pdf";
                document.LongLeaveReport.outputtype.value='pdf';
        }
        else if(document.LongLeaveReport.optoutputtype[1].checked==true)
        {
                url=url+"&outputtype=excel";
                document.LongLeaveReport.outputtype.value='excel';
        }
        else if(document.LongLeaveReport.optoutputtype[2].checked==true)
        {
                url=url+"&outputtype=html";
                 document.LongLeaveReport.outputtype.value='html';
        }
        
   
    
    document.LongLeaveReport.action="../../../../../../LongLeaveReportNewServ1.rep";
    document.LongLeaveReport.submit();
   /* var req=getTransport();
    req.open("POST",url,false);  
    req.send(null);*/

}
   
   function getCurrentYear() {
    var year = new Date().getYear();
    if(year < 1900) year += 1900;
    return year;
  }

  function getCurrentMonth() {
    return new Date().getMonth() + 1;
  } 

  function getCurrentDay() {
    return new Date().getDate();
  }
    
    
    function checkdt(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
        
         var c=t.value;
        try{
        
        var f=DateFormat(t,c,event,true,'3');
       
        }catch(e){
        //exception  start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+ _Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
             if(err!=0)
                {
                    t.value="";
                    return false;
                }
            return true;
        
        
        //exception end
        
        }
        if( f==true)
        {
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
         
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
           
            return true;
            
        }
        else
        {
                if(err!=0)
                {
                    t.value="";
                    return false;
                }
        }
            
    }
    else
    {
            alert('Date format  should be (dd-mm-yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    
}


  function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

}

function checkdate()
{
//alert('check');
        var fromdt=document.LongLeaveReport.txtDateFrom.value;
        var todt=document.LongLeaveReport.txtDateTo.value;
        
        var frm=fromdt.split('/');
        var to=todt.split('/');
        
        var fday=frm[0];
        var fmon=frm[1];
        var fyear=frm[2];
        
        var tday=to[0];
        var tmon=to[1];
        var tyear=to[2];
        
        if(fyear>tyear)
        {
            alert('From Date should be less than To Date');
            //document.HRE_EmployeeServiceDetails.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('From Date should be less than To Date');
                    //document.HRE_EmployeeServiceDetails.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('From Date should be less than To Date');
                           // document.HRE_EmployeeServiceDetails.txtDateTo.focus();
                            return false;
                        }
                        
                }
        }
        return true;

}


