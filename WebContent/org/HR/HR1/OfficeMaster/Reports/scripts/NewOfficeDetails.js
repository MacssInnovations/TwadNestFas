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
            
    document.frmVacantRep.optofficelevel[0].checked=true;
    var offlevel=document.getElementById("trofficelevel");
    offlevel.style.display="none";
}

function showofficelevel()
{
    document.frmVacantRep.optofficelevel[1].checked=true;
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
    document.frmVacantRep.optoffice[0].checked=true;
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="none";
}

function showoffice()
{
   
    document.frmVacantRep.optoffice[1].checked=true;
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //alert('hai');
    var type=document.frmVacantRep.cmbolevel.options[document.frmVacantRep.cmbolevel.selectedIndex].value;
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
    document.frmVacantRep.cmbcircle.disabled=true;
    document.frmVacantRep.cmbdivision.disabled=true;
    
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
        var type=document.frmVacantRep.cmbolevel.options[document.frmVacantRep.cmbolevel.selectedIndex].value;
       // alert('getoffice:'+type);
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
               // var agg=document.getElementById("aggreate");
               // agg.style.display="block";
                var offlevel=document.getElementById("divown");
                    offlevel.style.display="block";
              }
              else
              {
                var agg=document.getElementById("aggreate");
                agg.style.display="none";
                document.frmVacantRep.aggid.checked=false;
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
        var type=document.frmVacantRep.cmbolevel.options[document.frmVacantRep.cmbolevel.selectedIndex].value;
    
         var url="../../../../../../NewOfficeDetails.rep?OLevel=region" ;
     // alert(url);
                
        document.frmVacantRep.cmbregion.focus();
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
                       
                   
                  document.frmVacantRep.cmbcircle.disabled=false;
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
        var type=document.frmVacantRep.cmbolevel.options[document.frmVacantRep.cmbolevel.selectedIndex].value;
    
      if(document.frmVacantRep.chkregion)
      {
               if(document.frmVacantRep.chkregion.length)
              {
             
                            for(i=0;i<document.frmVacantRep.chkregion.length;i++)
                            {
                                    if(document.frmVacantRep.chkregion[i].checked==true)
                                            region= region+document.frmVacantRep.chkregion[i].value +",";
                                    
                            }
                            
                            if(region!="")
                            {
                                 if(circleflag==true && cir=='null')
                                {
                                       
                                         var iframe=document.getElementById("diviframecircle");
                                            iframe.style.visibility='visible';
                                          iframe.focus();
                                           document.frmVacantRep.cmbdivision.disabled=false;
                                        return;
                                }
                                region=region.substring(0,region.length-1);
                                var url="../../../../../../NewOfficeDetails.rep?OLevel=circle&regions="+region;
                              // var url="../../../../../../NewOfficeDetails.rep?OLevel=circle";
                              // document.frmVacantRep.cmbregion.focus();
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
                                            
                                            document.frmVacantRep.cmbdivision.disabled=false;
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
                                document.frmVacantRep.cmbdivision.disabled=false;
                                document.frmVacantRep.cmbcircle.focus();
                                alert('Please Select a Region');
                            }
            }
            else
            {
            
                             if(document.frmVacantRep.chkregion.checked==true)
                             {
                                            region= document.frmVacantRep.chkregion.value ;
                                    
                                    
                                         if(circleflag==true && cir=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframecircle");
                                                    iframe.style.visibility='visible';
                                                  iframe.focus();
                                                   document.frmVacantRep.cmbdivision.disabled=false;
                                                return;
                                        }
                                       // region=region.substring(0,region.length-1);
                                        var url="../../../../../../NewOfficeDetails.rep?OLevel=circle&regions="+region;
                                      // document.frmVacantRep.cmbregion.focus();
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
                                                    
                                                    document.frmVacantRep.cmbdivision.disabled=false;
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
                                document.frmVacantRep.cmbdivision.disabled=false;
                                document.frmVacantRep.cmbcircle.focus();
                                alert('Please Select a Region');
                            }

            
            
            
            
            }
            
      }
   
}


function getDivision(div)
    {
        
       // alert('division');
       
       //var WORK=document.frmVacantRep.cmbOfficeType.options[document.frmVacantRep.cmbOfficeType.selectedIndex].value;
       var circle="";
      if(document.frmVacantRep.chkcircle)
      {
      
            if(document.frmVacantRep.chkcircle.length)
            {
            
                            for(i=0;i<document.frmVacantRep.chkcircle.length;i++)
                            {
                                    if(document.frmVacantRep.chkcircle[i].checked==true)
                                            circle= circle+document.frmVacantRep.chkcircle[i].value +",";
                                    
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
                                //alert('hi');
                                var cmbOfficeType=document.getElementById("cmbOfficeType").value;
                                var url="../../../../../../NewOfficeDetails.rep?OLevel=division&circles="+circle+"&cmbOfficeType="+cmbOfficeType;
                              // document.frmVacantRep.cmbregion.focus();
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
                                            var type=document.frmVacantRep.cmbolevel.options[document.frmVacantRep.cmbolevel.selectedIndex].value;
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
                                document.frmVacantRep.cmbdivision.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                }
                else
                {
                
                             if(document.frmVacantRep.chkcircle.checked==true)
                             {
                                            circle= document.frmVacantRep.chkcircle.value ;
                                    
                           
                                         if(divisionflag==true && div=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframedivision");
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                return;
                                        }
                                       // circle=circle.substring(0,circle.length-1);
                                        var url="../../../../../../NewOfficeDetails.rep?OLevel=division&circles="+circle;
                                      // document.frmVacantRep.cmbregion.focus();
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
                                                    var type=document.frmVacantRep.cmbolevel.options[document.frmVacantRep.cmbolevel.selectedIndex].value;
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
                                document.frmVacantRep.cmbdivision.focus();
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

   if(document.frmVacantRep.chkregion)
      {
      
            
            for(i=0;i<document.frmVacantRep.chkregion.length;i++)
            {
                    document.frmVacantRep.chkregion[i].checked=true;
                    
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
    if(document.frmVacantRep.chkcircle)
      {
      
            for(i=0;i<document.frmVacantRep.chkcircle.length;i++)
            {
                    document.frmVacantRep.chkcircle[i].checked=true;
                    
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
    if(document.frmVacantRep.chkdivision)
      {
      
            for(i=0;i<document.frmVacantRep.chkdivision.length;i++)
            {
                    document.frmVacantRep.chkdivision[i].checked=true;
                    
            }
        }
}

function regiononchange()
{
     var type=document.frmVacantRep.cmbolevel.options[document.frmVacantRep.cmbolevel.selectedIndex].value;
   circlefalg=false;
    if(type=='CL' || type=='DN')
    {
        getCircle('circle');
    }

}

function circleonchange()
{
     var type=document.frmVacantRep.cmbolevel.options[document.frmVacantRep.cmbolevel.selectedIndex].value;
    divisionfalg=false;
    if(type=='DN')
    {
    getDivision('division');
    }

}



function hidedisignation()
{
    document.frmVacantRep.optdesignation[0].checked=true;
   var offlevel=document.getElementById("trsergroup");
    offlevel.style.display="none";
    
      var offlevel=document.getElementById("divdes");
    offlevel.style.visibility="hidden";
     var offlevel=document.getElementById("cmbdes");
    offlevel.style.visibility="hidden";
    document.frmVacantRep.cmbsgroup.value="0";
}

function showdesignation()
{
   document.frmVacantRep.optdesignation[1].checked=true;
    var offlevel=document.getElementById("trsergroup");
    offlevel.style.display="block";
}

function getDesignation()
    {
        var type=document.frmVacantRep.cmbsgroup.options[document.frmVacantRep.cmbsgroup.selectedIndex].value;
        if(type!=0)
        {
            var din=document.getElementById("divdes");
            din.style.visibility="visible";
            document.frmVacantRep.cmbdes.style.visibility="visible";
            loadOfficesByType(type);
        }
        else
        {
             var din=document.getElementById("divdes");
            din.style.visibility="hidden";
            document.frmVacantRep.cmbdes.style.visibility="hidden";
        }
    }
    
function loadOfficesByType(type)
    {
        //alert(type);
        var type=document.frmVacantRep.cmbsgroup.options[document.frmVacantRep.cmbsgroup.selectedIndex].value;
        //startwaiting(document.frmVacantRep) ;
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
                
                // stopwaiting(document.frmVacantRep);
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
/*
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~ Office Level ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ --> 
 function getOfficesByLevel()
    {
        var level=document.OfficeSpecificReport.cmbControllingLevel.options[document.OfficeSpecificReport.cmbControllingLevel.selectedIndex].value;
        var levelt=document.OfficeSpecificReport.cmbControllingLevel.options[document.OfficeSpecificReport.cmbControllingLevel.selectedIndex].text;
       
        
        document.OfficeSpecificReport.chkall.checked=false;
        var cell=document.getElementById("divpre");
        cell.style.display="none";
        cell.innerText='';
         var cell=document.getElementById("divcmbpage");
        cell.style.display="none";
        var cell=document.getElementById("divpage");
        cell.style.display="none";
        cell.innerText='';
        var cell=document.getElementById("divnext");
        cell.style.display="none";
        cell.innerText='';
         items1=null;
         items2=null;
         items3=null;
         items4=null;
         items5=null;
         items6=null;
        if(level=="DN" || level=="SD" || level=="SN" )
        {
            var din=document.getElementById("divown");
            din.style.display="block";
            var tbody=document.getElementById("tb");
                 
                  try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}
            document.OfficeSpecificReport.cmbOfficeType.focus();      
            
        }
        else
        {
           
            var din=document.getElementById("divown");
            din.style.display="none";
            if(level=="")
            {
                var tbody=document.getElementById("tb");
                try{tbody.innerHTML="";}
                          catch(e) {tbody.innerText="";}
                          
                 var cmbpage=document.getElementById("cmbpage");
                                   
               try{ cmbpage.innerHTML="";
               }catch(e){
                cmbpage.innerText="";
               }
                        
            }
            else
            {
            loadOfficesByLevel(level);
                    
            }
        }
        
    }
 */
 
 /*function loadOfficesByLevel(level)
    {
        startwaiting(document.frmVacantRep);
         
        var url="../../../../../..StaffStrengthVacantServ.rep?Command=level&level="+level;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            loadTable(req);
        }
        req.send(null);
    }
    <!----------------------------------------------------------------------------->
    */
function frmsubmit()
{
    //alert('submit');
        var url="../../../../../../NewOfficeDetails.rep?OLevel=submit" ;
        
        //office validation
        if(document.frmVacantRep.optofficelevel[0].checked==true)
        {
                url=url+"&offlevel=all";
                document.frmVacantRep.offlevel.value='all';
        }
        else
        {
                url=url+"&offlevel=specific";
                 document.frmVacantRep.offlevel.value='specific';
                
                // select the office 
                  var type=document.frmVacantRep.cmbolevel.options[document.frmVacantRep.cmbolevel.selectedIndex].value;
                 if(type=="")
                 {
                     ('Select the Office Level');
                    document.frmVacantRep.cmbolevel.focus();
                    return;
                }
                else
                {
                    url=url+"&office="+type;
                    document.frmVacantRep.office.value=type;
                    
                    if(document.frmVacantRep.optoffice[0].checked==true)
                    {
                            url=url+"&officetype=all";
                             document.frmVacantRep.officetype.value='all';
                    }
                    else
                    {
                            url=url+"&officetype=specific";
                             document.frmVacantRep.officetype.value='specific';
                            if(type=='RN')
                            {
                            ////////
                            var region="";
                            if(document.frmVacantRep.chkregion)
                              {
                              
                                        if(document.frmVacantRep.chkregion.length)
                                        {
                                                  for(i=0;i<document.frmVacantRep.chkregion.length;i++)
                                                    {
                                                            if(document.frmVacantRep.chkregion[i].checked==true)
                                                                    region= region+document.frmVacantRep.chkregion[i].value +",";
                                                            
                                                    }
                                                    if(region!="")
                                                    {
                                                        region=region.substring(0,region.length-1);
                                                         url=url+"&officeselected="+region;
                                                         document.frmVacantRep.officeselected.value=region;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Region.');
                                                            document.frmVacantRep.cmbregion[0].focus(); 
                                                            return;
                                                    }
                                            
                                        }
                                        else{
                                           
                                                if(document.frmVacantRep.chkregion.checked==true)
                                                {
                                                            region= document.frmVacantRep.chkregion.value ;
                                                            document.frmVacantRep.officeselected.value=region;
                                                }
                                                 else
                                                    {
                                                           alert('Select the Region..');
                                                            document.frmVacantRep.cmbregion.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                 else
                                {
                                       alert('Select the Region...');
                                        document.frmVacantRep.cmbregion.focus(); 
                                        return ;
                                }    
                            ////
                            
                            }
                            
                            else if(type=='CL')
                            {
                            
                            ////////
                            var circle="";
                            if(document.frmVacantRep.chkcircle)
                              {
                              
                                       if(document.frmVacantRep.chkcircle.length )
                                        {
                                                
                                                    for(i=0;i<document.frmVacantRep.chkcircle.length;i++)
                                                    {
                                                            if(document.frmVacantRep.chkcircle[i].checked==true)
                                                                    circle= circle+document.frmVacantRep.chkcircle[i].value +",";
                                                            
                                                    }
                                                    if(circle!="")
                                                    {
                                                        circle=circle.substring(0,circle.length-1);
                                                         url=url+"&officeselected="+circle;
                                                         document.frmVacantRep.officeselected.value=circle;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.frmVacantRep.cmbcircle.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.frmVacantRep.chkcircle.checked==true)
                                                {
                                                            circle= document.frmVacantRep.chkcircle.value ;
                                                            document.frmVacantRep.officeselected.value=circle;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Circle..');
                                                            document.frmVacantRep.cmbcircle.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Circle...');
                                       try{
                                        document.frmVacantRep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.frmVacantRep.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                            else  if(type=='DN')
                            {
                           
                            ////////
                            var division="";
                            if(document.frmVacantRep.chkdivision)
                              {
                             //alert(document.frmVacantRep.chkregion.length);
                                     if(document.frmVacantRep.chkdivision.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.frmVacantRep.chkdivision.length;i++)
                                                {
                                                        if(document.frmVacantRep.chkdivision[i].checked==true)
                                                                division= division+document.frmVacantRep.chkdivision[i].value +",";
                                                        
                                                }
                                                
                                                if(division!="")
                                                {
                                                    division=division.substring(0,division.length-1);
                                                     url=url+"&officeselected="+division;
                                                     document.frmVacantRep.officeselected.value=division;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the Division.');
                                                        document.frmVacantRep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.frmVacantRep.chkdivision.checked==true)
                                                {
                                                                division= document.frmVacantRep.chkdivision.value;
                                                                    //division=division.substring(0,division.length-1);
                                                                 url=url+"&officeselected="+division;
                                                                 document.frmVacantRep.officeselected.value=division;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the Division..');
                                                        document.frmVacantRep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the Division...');
                                       try{
                                        document.frmVacantRep.cmbdivision.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.frmVacantRep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.frmVacantRep.cmbregion.focus(); 
                                        }
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                        
                    }
                        
                    
                }
        }
       
      
        //select the output type
        if(document.frmVacantRep.optoutputtype[0].checked==true)
        {
                url=url+"&outputtype=pdf";
                document.frmVacantRep.outputtype.value='pdf';
        }
        else if(document.frmVacantRep.optoutputtype[1].checked==true)
        {
                url=url+"&outputtype=excel";
                document.frmVacantRep.outputtype.value='excel';
        }
        else if(document.frmVacantRep.optoutputtype[2].checked==true)
        {
                url=url+"&outputtype=html";
                 document.frmVacantRep.outputtype.value='html';
        }
        
   
    
    document.frmVacantRep.action="../../../../../../NewOfficeDetails.rep";
    document.frmVacantRep.submit();
   /* var req=getTransport();
    req.open("POST",url,false);  
    req.send(null);*/

}
    

 

    

    