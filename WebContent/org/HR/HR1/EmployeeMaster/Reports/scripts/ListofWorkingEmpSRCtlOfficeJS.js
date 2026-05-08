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
            
    document.frmSRCTRLofficeRep.optofficelevel[0].checked=true;
    var offlevel=document.getElementById("trofficelevel");
    offlevel.style.display="none";
}

function showofficelevel()
{
    document.frmSRCTRLofficeRep.optofficelevel[1].checked=true;
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
    document.frmSRCTRLofficeRep.optoffice[0].checked=true;
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="none";
}

function showoffice()
{
   
    document.frmSRCTRLofficeRep.optoffice[1].checked=true;
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //alert('hai');
    var type=document.frmSRCTRLofficeRep.cmbolevel.options[document.frmSRCTRLofficeRep.cmbolevel.selectedIndex].value;
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
    document.frmSRCTRLofficeRep.cmbcircle.disabled=true;
    document.frmSRCTRLofficeRep.cmbdivision.disabled=true;
    
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
        var type=document.frmSRCTRLofficeRep.cmbolevel.options[document.frmSRCTRLofficeRep.cmbolevel.selectedIndex].value;
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
                document.frmSRCTRLofficeRep.aggid.checked=false;
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
        var type=document.frmSRCTRLofficeRep.cmbolevel.options[document.frmSRCTRLofficeRep.cmbolevel.selectedIndex].value;
    
         var url="../../../../../../ListofWorkingEmpSRCtlOfficeServ.rep?OLevel=region" ;
     // alert(url);
                
        document.frmSRCTRLofficeRep.cmbregion.focus();
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
                       
                   
                  document.frmSRCTRLofficeRep.cmbcircle.disabled=false;
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
        var type=document.frmSRCTRLofficeRep.cmbolevel.options[document.frmSRCTRLofficeRep.cmbolevel.selectedIndex].value;
    
      if(document.frmSRCTRLofficeRep.chkregion)
      {
               if(document.frmSRCTRLofficeRep.chkregion.length)
              {
             
                            for(i=0;i<document.frmSRCTRLofficeRep.chkregion.length;i++)
                            {
                                    if(document.frmSRCTRLofficeRep.chkregion[i].checked==true)
                                            region= region+document.frmSRCTRLofficeRep.chkregion[i].value +",";
                                    
                            }
                            
                            if(region!="")
                            {
                                 if(circleflag==true && cir=='null')
                                {
                                       
                                         var iframe=document.getElementById("diviframecircle");
                                            iframe.style.visibility='visible';
                                          iframe.focus();
                                           document.frmSRCTRLofficeRep.cmbdivision.disabled=false;
                                        return;
                                }
                                region=region.substring(0,region.length-1);
                                var url="../../../../../../ListofWorkingEmpSRCtlOfficeServ.rep?OLevel=circle&regions="+region;
                              // document.frmSRCTRLofficeRep.cmbregion.focus();
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
                                            
                                            document.frmSRCTRLofficeRep.cmbdivision.disabled=false;
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
                                document.frmSRCTRLofficeRep.cmbdivision.disabled=false;
                                document.frmSRCTRLofficeRep.cmbcircle.focus();
                                alert('Please Select a Region');
                            }
            }
            else
            {
            
                             if(document.frmSRCTRLofficeRep.chkregion.checked==true)
                             {
                                            region= document.frmSRCTRLofficeRep.chkregion.value ;
                                    
                                    
                                         if(circleflag==true && cir=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframecircle");
                                                    iframe.style.visibility='visible';
                                                  iframe.focus();
                                                   document.frmSRCTRLofficeRep.cmbdivision.disabled=false;
                                                return;
                                        }
                                       // region=region.substring(0,region.length-1);
                                        var url="../../../../../../ListofWorkingEmpSRCtlOfficeServ.rep?OLevel=circle&regions="+region;
                                      // document.frmSRCTRLofficeRep.cmbregion.focus();
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
                                                    
                                                    document.frmSRCTRLofficeRep.cmbdivision.disabled=false;
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
                                document.frmSRCTRLofficeRep.cmbdivision.disabled=false;
                                document.frmSRCTRLofficeRep.cmbcircle.focus();
                                alert('Please Select a Region');
                            }

            
            
            
            
            }
            
      }
   
}


function getDivision(div)
    {
        
       // alert('division');
       var circle="";
      if(document.frmSRCTRLofficeRep.chkcircle)
      {
      
            if(document.frmSRCTRLofficeRep.chkcircle.length)
            {
            
                            for(i=0;i<document.frmSRCTRLofficeRep.chkcircle.length;i++)
                            {
                                    if(document.frmSRCTRLofficeRep.chkcircle[i].checked==true)
                                            circle= circle+document.frmSRCTRLofficeRep.chkcircle[i].value +",";
                                    
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
                                var url="../../../../../../ListofWorkingEmpSRCtlOfficeServ.rep?OLevel=division&circles="+circle;
                              // document.frmSRCTRLofficeRep.cmbregion.focus();
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
                                            var type=document.frmSRCTRLofficeRep.cmbolevel.options[document.frmSRCTRLofficeRep.cmbolevel.selectedIndex].value;
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
                                document.frmSRCTRLofficeRep.cmbdivision.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                }
                else
                {
                
                             if(document.frmSRCTRLofficeRep.chkcircle.checked==true)
                             {
                                            circle= document.frmSRCTRLofficeRep.chkcircle.value ;
                                    
                           
                                         if(divisionflag==true && div=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframedivision");
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                return;
                                        }
                                       // circle=circle.substring(0,circle.length-1);
                                        var url="../../../../../../ListofWorkingEmpSRCtlOfficeServ.rep?OLevel=division&circles="+circle;
                                      // document.frmSRCTRLofficeRep.cmbregion.focus();
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
                                                    var type=document.frmSRCTRLofficeRep.cmbolevel.options[document.frmSRCTRLofficeRep.cmbolevel.selectedIndex].value;
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
                                document.frmSRCTRLofficeRep.cmbdivision.focus();
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

   if(document.frmSRCTRLofficeRep.chkregion)
      {
      
            
            for(i=0;i<document.frmSRCTRLofficeRep.chkregion.length;i++)
            {
                    document.frmSRCTRLofficeRep.chkregion[i].checked=true;
                    
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
    if(document.frmSRCTRLofficeRep.chkcircle)
      {
      
            for(i=0;i<document.frmSRCTRLofficeRep.chkcircle.length;i++)
            {
                    document.frmSRCTRLofficeRep.chkcircle[i].checked=true;
                    
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
    if(document.frmSRCTRLofficeRep.chkdivision)
      {
      
            for(i=0;i<document.frmSRCTRLofficeRep.chkdivision.length;i++)
            {
                    document.frmSRCTRLofficeRep.chkdivision[i].checked=true;
                    
            }
        }
}

function regiononchange()
{
     var type=document.frmSRCTRLofficeRep.cmbolevel.options[document.frmSRCTRLofficeRep.cmbolevel.selectedIndex].value;
   circlefalg=false;
    if(type=='CL' || type=='DN')
    {
        getCircle('circle');
    }

}

function circleonchange()
{
     var type=document.frmSRCTRLofficeRep.cmbolevel.options[document.frmSRCTRLofficeRep.cmbolevel.selectedIndex].value;
    divisionfalg=false;
    if(type=='DN')
    {
    getDivision('division');
    }

}



function hidedisignation()
{
    document.frmSRCTRLofficeRep.optdesignation[0].checked=true;
   var offlevel=document.getElementById("trsergroup");
    offlevel.style.display="none";
    
      var offlevel=document.getElementById("divdes");
    offlevel.style.visibility="hidden";
     var offlevel=document.getElementById("cmbdes");
    offlevel.style.visibility="hidden";
    document.frmSRCTRLofficeRep.cmbsgroup.value="0";
}

function showdesignation()
{
   document.frmSRCTRLofficeRep.optdesignation[1].checked=true;
    var offlevel=document.getElementById("trsergroup");
    offlevel.style.display="block";
}

function getDesignation()
    {
        var type=document.frmSRCTRLofficeRep.cmbsgroup.options[document.frmSRCTRLofficeRep.cmbsgroup.selectedIndex].value;
        if(type!=0)
        {
            var din=document.getElementById("divdes");
            din.style.visibility="visible";
            document.frmSRCTRLofficeRep.cmbdes.style.visibility="visible";
            loadOfficesByType(type);
        }
        else
        {
             var din=document.getElementById("divdes");
            din.style.visibility="hidden";
            document.frmSRCTRLofficeRep.cmbdes.style.visibility="hidden";
        }
    }
    
function loadOfficesByType(type)
    {
        //alert(type);
        var type=document.frmSRCTRLofficeRep.cmbsgroup.options[document.frmSRCTRLofficeRep.cmbsgroup.selectedIndex].value;
        //startwaiting(document.frmSRCTRLofficeRep) ;
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
                
                // stopwaiting(document.frmSRCTRLofficeRep);
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
        var url="../../../../../../ListofWorkingEmpSRCtlOfficeServ.rep?OLevel=submit" ;
        
        //office validation
        if(document.frmSRCTRLofficeRep.optofficelevel[0].checked==true)
        {
                url=url+"&offlevel=all";
                document.frmSRCTRLofficeRep.offlevel.value='all';
        }
        else
        {
                url=url+"&offlevel=specific";
                 document.frmSRCTRLofficeRep.offlevel.value='specific';
                
                // select the office 
                  var type=document.frmSRCTRLofficeRep.cmbolevel.options[document.frmSRCTRLofficeRep.cmbolevel.selectedIndex].value;
                 if(type=="")
                 {
                    alert('Select the Office Level');
                    document.frmSRCTRLofficeRep.cmbolevel.focus();
                    return;
                }
                else
                {
                    url=url+"&office="+type;
                    document.frmSRCTRLofficeRep.office.value=type;
                    
                    if(document.frmSRCTRLofficeRep.optoffice[0].checked==true)
                    {
                            url=url+"&officetype=all";
                             document.frmSRCTRLofficeRep.officetype.value='all';
                    }
                    else
                    {
                            url=url+"&officetype=specific";
                             document.frmSRCTRLofficeRep.officetype.value='specific';
                            if(type=='RN')
                            {
                            ////////
                            var region="";
                            if(document.frmSRCTRLofficeRep.chkregion)
                              {
                              
                                        if(document.frmSRCTRLofficeRep.chkregion.length)
                                        {
                                                  for(i=0;i<document.frmSRCTRLofficeRep.chkregion.length;i++)
                                                    {
                                                            if(document.frmSRCTRLofficeRep.chkregion[i].checked==true)
                                                                    region= region+document.frmSRCTRLofficeRep.chkregion[i].value +",";
                                                            
                                                    }
                                                    if(region!="")
                                                    {
                                                        region=region.substring(0,region.length-1);
                                                         url=url+"&officeselected="+region;
                                                         document.frmSRCTRLofficeRep.officeselected.value=region;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Region.');
                                                            document.frmSRCTRLofficeRep.cmbregion[0].focus(); 
                                                            return;
                                                    }
                                            
                                        }
                                        else{
                                           
                                                if(document.frmSRCTRLofficeRep.chkregion.checked==true)
                                                {
                                                            region= document.frmSRCTRLofficeRep.chkregion.value ;
                                                            document.frmSRCTRLofficeRep.officeselected.value=region;
                                                }
                                                 else
                                                    {
                                                           alert('Select the Region..');
                                                            document.frmSRCTRLofficeRep.cmbregion.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                 else
                                {
                                       alert('Select the Region...');
                                        document.frmSRCTRLofficeRep.cmbregion.focus(); 
                                        return ;
                                }    
                            ////
                            
                            }
                            
                            else if(type=='CL')
                            {
                            
                            ////////
                            var circle="";
                            if(document.frmSRCTRLofficeRep.chkcircle)
                              {
                              
                                       if(document.frmSRCTRLofficeRep.chkcircle.length )
                                        {
                                                
                                                    for(i=0;i<document.frmSRCTRLofficeRep.chkcircle.length;i++)
                                                    {
                                                            if(document.frmSRCTRLofficeRep.chkcircle[i].checked==true)
                                                                    circle= circle+document.frmSRCTRLofficeRep.chkcircle[i].value +",";
                                                            
                                                    }
                                                    if(circle!="")
                                                    {
                                                        circle=circle.substring(0,circle.length-1);
                                                         url=url+"&officeselected="+circle;
                                                         document.frmSRCTRLofficeRep.officeselected.value=circle;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.frmSRCTRLofficeRep.cmbcircle.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.frmSRCTRLofficeRep.chkcircle.checked==true)
                                                {
                                                            circle= document.frmSRCTRLofficeRep.chkcircle.value ;
                                                            document.frmSRCTRLofficeRep.officeselected.value=circle;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Circle..');
                                                            document.frmSRCTRLofficeRep.cmbcircle.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Circle...');
                                       try{
                                        document.frmSRCTRLofficeRep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.frmSRCTRLofficeRep.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                            else  if(type=='DN')
                            {
                           
                            ////////
                            var division="";
                            if(document.frmSRCTRLofficeRep.chkdivision)
                              {
                             //alert(document.frmSRCTRLofficeRep.chkregion.length);
                                     if(document.frmSRCTRLofficeRep.chkdivision.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.frmSRCTRLofficeRep.chkdivision.length;i++)
                                                {
                                                        if(document.frmSRCTRLofficeRep.chkdivision[i].checked==true)
                                                                division= division+document.frmSRCTRLofficeRep.chkdivision[i].value +",";
                                                        
                                                }
                                                
                                                if(division!="")
                                                {
                                                    division=division.substring(0,division.length-1);
                                                     url=url+"&officeselected="+division;
                                                     document.frmSRCTRLofficeRep.officeselected.value=division;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the Division.');
                                                        document.frmSRCTRLofficeRep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.frmSRCTRLofficeRep.chkdivision.checked==true)
                                                {
                                                                division= document.frmSRCTRLofficeRep.chkdivision.value;
                                                                    //division=division.substring(0,division.length-1);
                                                                 url=url+"&officeselected="+division;
                                                                 document.frmSRCTRLofficeRep.officeselected.value=division;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the Division..');
                                                        document.frmSRCTRLofficeRep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the Division...');
                                       try{
                                        document.frmSRCTRLofficeRep.cmbdivision.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.frmSRCTRLofficeRep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.frmSRCTRLofficeRep.cmbregion.focus(); 
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
        if(document.frmSRCTRLofficeRep.optoutputtype[0].checked==true)
        {
                url=url+"&outputtype=pdf";
                document.frmSRCTRLofficeRep.outputtype.value='pdf';
        }
        else if(document.frmSRCTRLofficeRep.optoutputtype[1].checked==true)
        {
                url=url+"&outputtype=excel";
                document.frmSRCTRLofficeRep.outputtype.value='excel';
        }
        else if(document.frmSRCTRLofficeRep.optoutputtype[2].checked==true)
        {
                url=url+"&outputtype=html";
                 document.frmSRCTRLofficeRep.outputtype.value='html';
        }
        
   
    
    document.frmSRCTRLofficeRep.action="../../../../../../ListofWorkingEmpSRCtlOfficeServ.rep";
    document.frmSRCTRLofficeRep.submit();
   /* var req=getTransport();
    req.open("POST",url,false);  
    req.send(null);*/

}
    

    