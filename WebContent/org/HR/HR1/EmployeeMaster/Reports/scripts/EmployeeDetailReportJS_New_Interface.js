//alert('detail');

var regionflag=false;
//var regionflag=true;
var circleflag=true;
var divisionflag=true;
var offtypeflag=true;
var auditflag=true;
var labflag=true;

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



function officevisible(r,c,d,o,aud,l)
{
   //alert("inside officevisible");
    
    var rn=document.getElementById("divregion");
    rn.style.display=r;
    var cl=document.getElementById("divcircle");
    cl.style.display=c;
    var dn=document.getElementById("divdivision");
    dn.style.display=d;
    var ot=document.getElementById("divofftype");
    ot.style.display=o;
    //alert(aud);
    var ad1=document.getElementById("divaudit");
    ad1.style.display=aud;
    //alert(document.getElementById("divaudit").style.display);
     var lb=document.getElementById("divlab");
    lb.style.display=l;
    
    var rn=document.getElementById("cmbregion");
    rn.style.display=r;
    var cl=document.getElementById("cmbcircle");
    cl.style.display=c;
    var dn=document.getElementById("cmbdivision");
    dn.style.display=d;
    var ot=document.getElementById("cmbofftype");
    ot.style.display=o;
    var ad=document.getElementById("cmbaudit");
    ad.style.display=aud;
     var lb=document.getElementById("cmblab");
    lb.style.display=l;
}




function selectoption2()
{
   
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    document.getElementById("divallhier").style.display='block';
    
    
    if(document.frmValidationSummaryRep.rad_off[0].checked)
    {
       var offlevel=document.getElementById("trofficeselection");
       offlevel.style.display="none";
    }

  if(document.frmValidationSummaryRep.rad_off[1].checked)
  {
   // alert(document.frmValidationSummaryRep.rad_off[1].checked);
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
    
     officevisible('block','none','none','none','none','none');
  }
  
  else if(document.frmValidationSummaryRep.rad_off[2].checked)
  {
   // alert(document.frmValidationSummaryRep.rad_off[2].checked);
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
     officevisible('block','block','none','none','none','none');
  }
  
  else if(document.frmValidationSummaryRep.rad_off[3].checked)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('block','block','block','block','none','none');
  }
  else if(document.frmValidationSummaryRep.rad_off[4].checked)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('block','none','none','none','block','none');
     var divallhier=document.getElementById("divallhier");
     divallhier.style.display="none";  
  }
  else if(document.frmValidationSummaryRep.rad_off[5].checked)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('block','block','none','none','none','block');
     var divallhier=document.getElementById("divallhier");
     divallhier.style.display="none";  
  }
  
  
  document.frmValidationSummaryRep.cmbcircle.disabled=true;
   // document.frmValidationSummaryRep.cmbofftype.disabled=true;
    document.frmValidationSummaryRep.cmbdivision.disabled=true;
    document.frmValidationSummaryRep.cmbaudit.disabled=true;
     document.frmValidationSummaryRep.cmblab.disabled=true;
    
    var iframe=document.getElementById("diviframeregion");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframecircle");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframeofftype");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframedivision");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframeaudit");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframelab");
    iframe.style.visibility='hidden';
    
    
    regionflag=true;
    circleflag=true;
    offtypeflag=true;
    divisionflag=true;
    auditflag=true;
    labflag=true;
   

}


var s=0;
var hier=true;
var level=true;
var city=true;
var  other=true;
  
function getRegion()
    {
      
              
        
        if(regionflag==true)
        {
               
                 var iframe=document.getElementById("diviframeregion");
                 iframe.style.visibility='visible';
                 iframe.focus();
               // return;
        }
       
    
         
         var url="../../../../../../EmpValidationDetailServ_New_Interface?OLevel=region" ;
        //alert(url);
                
        document.frmValidationSummaryRep.cmbregion.focus();
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            //requesthandle(req);
        //	alert(req);
            if(req.readyState==4)
            { 
                  if(req.status==200)
                  {  
                   // alert(req.responseText);
                 
                 
                  
                    var iframe=document.getElementById("diviframeregion");
                    iframe.style.visibility='visible';
                    iframe.focus();
                   // alert(navigator.appName);
                   // alert(navigator.appName.indexOf('Microsoft'));
                   
                 // alert(req.responseText);
                    if(navigator.appName.indexOf('Microsoft')!=-1)
                        iframe.innerHTML=req.responseText;
                    else
                        iframe.innerText=req.responseText;
                    iframe.innerHTML=req.responseText;
                   
                  document.frmValidationSummaryRep.cmbcircle.disabled=false;
                  document.frmValidationSummaryRep.cmbaudit.disabled=false;
                   var iframe=document.getElementById("diviframecircle");
                            iframe.style.visibility='hidden';
                   var iframe=document.getElementById("diviframedivision");
                            iframe.style.visibility='hidden';
                   var iframe=document.getElementById("diviframeaudit");
                            iframe.style.visibility='hidden';
                   
                   regionflag=true;
                  }
            }
        }
        req.send(null);
    }
    
    
function getCircle(cir)
{
     // alert("inside getcircle");
        
       var region="";
     //   var type=document.frmValidationSummaryRep.cmbolevel.options[document.frmValidationSummaryRep.cmbolevel.selectedIndex].value;
     
      
    
     // if(document.frmValidationSummaryRep.chkregion)
      //{
      
         // alert(document.frmValidationSummaryRep.chkregion);
           
               if(document.frmValidationSummaryRep.chkregion.length > 0)
              {
              
                // alert(document.frmValidationSummaryRep.chkregion.length);
             
                            for(i=0;i<document.frmValidationSummaryRep.chkregion.length;i++)
                            {
                              //  alert("document.frmValidationSummaryRep.chkregion[i].checked..."+document.frmValidationSummaryRep.chkregion[i].checked);
                                    if(document.frmValidationSummaryRep.chkregion[i].checked==true)
                                            region= region+document.frmValidationSummaryRep.chkregion[i].value +",";
                                            
                                         //   alert("region..."+region);
                                    
                            }
                            
                            if(region!="")
                            {
                              // alert("inside 1");
                                 if(circleflag==true && cir=='null')
                                {
                                  //     alert("inside 2");
                                         var iframe=document.getElementById("diviframecircle");
                                            iframe.style.visibility='visible';
                                          iframe.focus();
                                           document.frmValidationSummaryRep.cmbdivision.disabled=false;
                                           document.frmValidationSummaryRep.cmblab.disabled=false;
                                      //  return;
                                }
                                region=region.substring(0,region.length-1);
                               // alert("region1..."+region);
                                //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                
                                var url="../../../../../../EmployeeDetailReportServ_New_Interface?OLevel=circle&regions="+region;
                             //   alert(url);
                              // document.frmValidationSummaryRep.cmbregion.focus();
                                var req=getTransport();
                                req.open("GET",url,true);        
                                req.onreadystatechange=function()
                                {
                                    //requesthandle(req);
                                    if(req.readyState==4)
                                    { 
                                          if(req.status==200)
                                          {  
                              //             alert("success");
                                         //  var iframe=document.getElementById("diviframecircle");
                                            // if((type=='CL' || type=='DN') && cir=='null' )
                                            if(circleflag==true && cir=='null')
                                            {
                                         //   alert("see these");
                                            var iframe=document.getElementById("diviframecircle");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                            
                                            if(navigator.appName.indexOf('Microsoft')!=-1)
                                              iframe.innerHTML=req.responseText;
                                              else
                                              iframe.innerText=req.responseText;
                                            iframe.innerHTML=req.responseText;
                                            document.frmValidationSummaryRep.cmbdivision.disabled=false;
                                             document.frmValidationSummaryRep.cmblab.disabled=false;
                                              var iframe=document.getElementById("diviframedivision");
                                             iframe.style.visibility='hidden';
                                            
                                            
                                            }
                                             iframe.innerHTML=req.responseText;
                                             
                                         //    alert(iframe.innerHTML);
                                             /*
                                             if(navigator.appName.indexOf('Microsoft')!=-1)
                                              iframe.innerHTML=req.responseText;
                                              else
                                              iframe.innerText=req.responseText;
                                              */
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
                                document.frmValidationSummaryRep.cmbdivision.disabled=false;
                                document.frmValidationSummaryRep.cmbcircle.focus();
                                alert('Please Select a Region');
                            }
            }
            else
            {
            
                             if(document.frmValidationSummaryRep.chkregion.checked==true)
                             {
                                            region= document.frmValidationSummaryRep.chkregion.value ;
                                    
                                    
                                         if(circleflag==true && cir=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframecircle");
                                                    iframe.style.visibility='visible';
                                                  iframe.focus();
                                                   document.frmValidationSummaryRep.cmbdivision.disabled=false;
                                                    document.frmValidationSummaryRep.cmblab.disabled=false;
                                                //return;
                                        }
                                       // region=region.substring(0,region.length-1);
                                       // var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                        
                                        var url="../../../../../../EmployeeDetailReportServ_New_Interface?OLevel=circle&regions="+region;
                                        
                                      // document.frmValidationSummaryRep.cmbregion.focus();
                                        var req=getTransport();
                                        req.open("GET",url,true);        
                                        req.onreadystatechange=function()
                                        {
                                            //requesthandle(req);
                                            if(req.readyState==4)
                                            { 
                                                  if(req.status==200)
                                                  {  
                                                   var iframe=document.getElementById("diviframecircle");
                                                   if(navigator.appName.indexOf('Microsoft')!=-1)
                                                       iframe.innerHTML=req.responseText;
                                                         else
                                                       iframe.innerText=req.responseText;
                                                   iframe.innerHTML=req.responseText;
                                                    // if((type=='CL' || type=='DN') && cir=='null' )
                                                    if(cir=='null')
                                                    {
                                                    
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                    
                                                    document.frmValidationSummaryRep.cmbdivision.disabled=false;
                                                     document.frmValidationSummaryRep.cmblab.disabled=false;
                                                      var iframe=document.getElementById("diviframedivision");
                                                     iframe.style.visibility='hidden';
                                                    
                                                    
                                                    }
                                                     iframe.innerHTML=req.responseText;
                                                     
                                            //         alert(iframe.innerHTML);
                                                     /*
                                                     if(navigator.appName.indexOf('Microsoft')!=-1)
                                                       iframe.innerHTML=req.responseText;
                                                         else
                                                       iframe.innerText=req.responseText;
                                                       */
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
                                document.frmValidationSummaryRep.cmbdivision.disabled=false;
                                document.frmValidationSummaryRep.cmbcircle.focus();
                                alert('Please Select a Region');
                            }

            
            
            
            
            }
            
      //}
      
   
}


function getDivision(div)
{
        
      //alert('division');
       var region="";
       var circle="";
       var offtype="";
//      if(document.frmValidationSummaryRep.chkregion)
//      {
//         region=document.frmValidationSummaryRep.chkregion.value;
//         region+=",";
//         }   
         
         if(document.frmValidationSummaryRep.chkregion.length>0)
              {
                   for(i=0;i<document.frmValidationSummaryRep.chkregion.length;i++)
                            {
                            //  alert(document.frmValidationSummaryRep.chkregion[i].value);
                                    if(document.frmValidationSummaryRep.chkregion[i].checked==true)
                                            region= region+document.frmValidationSummaryRep.chkregion[i].value +",";
                                          // alert('region...'+region);
                                         
                                    
                            }
                 }
                 else
                 {
                        region=document.frmValidationSummaryRep.chkregion.value;
                        region+=",";
                 }
     
       if(document.frmValidationSummaryRep.chkofftype)
       {
               
         if(document.frmValidationSummaryRep.chkofftype.length)
         {
                    
                  for(i=0;i<document.frmValidationSummaryRep.chkofftype.length;i++)
                  {
                    if(document.frmValidationSummaryRep.chkofftype[i].checked==true)
                     offtype= offtype+"'"+document.frmValidationSummaryRep.chkofftype[i].value+"'"+",";            
                                      
                  }
               
         }
       }
       
      if(document.frmValidationSummaryRep.chkcircle)
      {
      //alert("hello");
            if(document.frmValidationSummaryRep.chkcircle.length)
            {
         //alert("hello1");   
                            for(i=0;i<document.frmValidationSummaryRep.chkcircle.length;i++)
                            {
                                    if(document.frmValidationSummaryRep.chkcircle[i].checked==true)
                                            circle= circle+document.frmValidationSummaryRep.chkcircle[i].value +",";
                                  
                            }
                            
                            
              
         }                  
         if(divisionflag==true && div=='null')
        {
           
               
                 var iframe=document.getElementById("diviframedivision");
                    iframe.style.visibility='visible';
                    iframe.focus();
               // return;
        }
            if(circle!='');  
              circle=circle.substring(0,circle.length-1);
            
            offtype=offtype.substring(0,offtype.length-1);
            region=region.substring(0,region.length-1);
                            
          var url="../../../../../../EmpValidationSummaryServ_New_Interface?OLevel=division&circles="+circle+"&offtype="+offtype+"&regions="+region;
          //alert("url"+url);
                                var req=getTransport();
                                req.open("GET",url,true);        
                                req.onreadystatechange=function()
                                {
                                    //requesthandle(req);
                                    if(req.readyState==4)
                                    { 
                                          if(req.status==200)
                                          { 
                                           // alert("success");
                                            var iframe=document.getElementById("diviframedivision");
                                            
                                            //var type=document.frmValidationSummaryRep.cmbolevel.options[document.frmValidationSummaryRep.cmbolevel.selectedIndex].value;
                                            if(div=='null')
                                            {
                                            //alert("visible");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                            }
                                            if(navigator.appName.indexOf('Microsoft')!=-1)
                                              iframe.innerHTML=req.responseText;
                                              else
                                              iframe.innerText=req.responseText;
                                            iframe.innerHTML=req.responseText;
                                             // alert(req.responseText);
                                            // iframe.innerHTML=req.responseText;
                                             
                                           divisionflag=true;  
                                          }
                                    }
                                }
                                req.send(null);
               }
                else
                {
                
                                 
                                      offtype=offtype.substring(0,offtype.length-1);
                                      region=region.substring(0,region.length-1);
                                      circle=null; 
                                      var url="../../../../../../EmpValidationSummaryServ_New_Interface?OLevel=division&regions="+region+"&offtype="+offtype+"&circles="+"nocircle";
                                       // alert(url);
                                      // document.frmValidationSummaryRep.cmbregion.focus();
                                        var req=getTransport();
                                        req.open("GET",url,true);        
                                        req.onreadystatechange=function()
                                        {
                                            //requesthandle(req);
                                            if(req.readyState==4)
                                            { 
                                                  if(req.status==200)
                                                  { 
                                                    var iframe=document.getElementById("diviframedivision");
                                                    //var type=document.frmValidationSummaryRep.cmbolevel.options[document.frmValidationSummaryRep.cmbolevel.selectedIndex].value;
                                                    if(div=='null')
                                                    {
                                                    
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                    }
                                                    if(navigator.appName.indexOf('Microsoft')!=-1)
                                              iframe.innerHTML=req.responseText;
                                              else
                                              iframe.innerText=req.responseText;
                                                    iframe.innerHTML=req.responseText;
                                                     //iframe.innerHTML=req.responseText;
                                                     
                                                   divisionflag=true;  
                                                  }
                                            }
                                        }
                                        req.send(null);
                            }
                           /* else
                            {
                                var iframe=document.getElementById("diviframedivision");
                                iframe.style.visibility='hidden';
                                document.frmValidationSummaryRep.cmbdivision.focus();
                                alert('Please Select a Circle');
                            }*/
                           // alert("circles:"+circle);
}

function regionclose()
{
    
    var iframe=document.getElementById("diviframeregion");
    iframe.style.visibility='hidden';
  
}

function offtypeclose()
{
    
    var iframe=document.getElementById("diviframeofftype");
    iframe.style.visibility='hidden';
  
}


function regionselectAll()
{

   if(document.frmValidationSummaryRep.chkregion)
      {
      
            
            for(i=0;i<document.frmValidationSummaryRep.chkregion.length;i++)
            {
                    document.frmValidationSummaryRep.chkregion[i].checked=true;
                    
            }
           //  regiononchange();
        }
}


function offtypeselectAll()
{

   if(document.frmValidationSummaryRep.chkofftype)
      {
      
            
            for(i=0;i<document.frmValidationSummaryRep.chkofftype.length;i++)
            {
                    document.frmValidationSummaryRep.chkofftype[i].checked=true;
                    
            }
             oftypeonchange();
        }
}


function circleclose()
{
    
    var iframe=document.getElementById("diviframecircle");
    iframe.style.visibility='hidden';
  
}


function circleselectAll()
{
    if(document.frmValidationSummaryRep.chkcircle)
      {
      
            for(i=0;i<document.frmValidationSummaryRep.chkcircle.length;i++)
            {
                    document.frmValidationSummaryRep.chkcircle[i].checked=true;
                    
            }
           // circleonchange();
        }
}

function divisionclose()
{
    
    var iframe=document.getElementById("diviframedivision");
    iframe.style.visibility='hidden';
  
}

function divisionselectAll()
{
    if(document.frmValidationSummaryRep.chkdivision)
      {
      
            for(i=0;i<document.frmValidationSummaryRep.chkdivision.length;i++)
            {
                    document.frmValidationSummaryRep.chkdivision[i].checked=true;
                    
            }
        }
}
function auditselectAll()
{
    if(document.frmValidationSummaryRep.chkaudit)
      {
      
            for(i=0;i<document.frmValidationSummaryRep.chkaudit.length;i++)
            {
                    document.frmValidationSummaryRep.chkaudit[i].checked=true;
                    
            }
           // circleonchange();
        }
}

function auditclose()
{
    
    var iframe=document.getElementById("diviframeaudit");
    iframe.style.visibility='hidden';
  
}

function labselectAll()
{
    if(document.frmValidationSummaryRep.chklab)
      {
      
            for(i=0;i<document.frmValidationSummaryRep.chklab.length;i++)
            {
                    document.frmValidationSummaryRep.chklab[i].checked=true;
                    
            }
           // circleonchange();
        }
}

function labclose()
{
    var iframe=document.getElementById("diviframelab");
    iframe.style.visibility='hidden';
}

function oftypeonchange()
{

     if(document.frmValidationSummaryRep.rad_off[3].checked)
     {
       getDivision('division');
     }
     /*
     var type=document.frmValidationSummaryRep.cmbolevel.options[document.frmValidationSummaryRep.cmbolevel.selectedIndex].value;
   circlefalg=false;
    if(type=='CL' || type=='DN')
    {
        getCircle('circle');
    }
   */
}




function frmsubmit()
{
  
    //alert('inside submit');
        var url="../../../../../../EmployeeDetailReportServ_New_Interface?OLevel=submit";
        
                             
                         if(document.frmValidationSummaryRep.rad_off[1].checked)    
                          //  if(type=='RN')
                            {
                             // alert("inside region");
                            ////////
                            var region="";
                            if(document.frmValidationSummaryRep.chkregion)
                              {
                              
                                        if(document.frmValidationSummaryRep.chkregion.length)
                                        {
                                                  for(i=0;i<document.frmValidationSummaryRep.chkregion.length;i++)
                                                    {
                                                            if(document.frmValidationSummaryRep.chkregion[i].checked==true)
                                                              region= region+document.frmValidationSummaryRep.chkregion[i].value +",";
                                                               
                                                            
                                                    }
                                                    if(region!="")
                                                    {
                                                        region=region.substring(0,region.length-1);
                                                         url=url+"&officeselected="+region;
                                                        // alert(url);
                                                         document.frmValidationSummaryRep.officeselected.value=region;
                                                         //alert(document.frmValidationSummaryRep.officeselected.value);
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Region.');
                                                            document.frmValidationSummaryRep.cmbregion[0].focus(); 
                                                            return;
                                                    }
                                            
                                        }
                                        else{
                                           
                                                if(document.frmValidationSummaryRep.chkregion.checked==true)
                                                {
                                                            region= document.frmValidationSummaryRep.chkregion.value ;
                                                            document.frmValidationSummaryRep.officeselected.value=region;
                                                }
                                                 else
                                                    {
                                                           alert('Select the Region..');
                                                            document.frmValidationSummaryRep.cmbregion.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                 else
                                {
                                       alert('Select the Region...');
                                        document.frmValidationSummaryRep.cmbregion.focus(); 
                                        return ;
                                }    
                            ////
                            
                            }
                            
                          else if(document.frmValidationSummaryRep.rad_off[2].checked) 
                            
                            //else if(type=='CL')
                            {
                            
                            ////////
                            var circle="";
                            if(document.frmValidationSummaryRep.chkcircle)
                              {
                              
                                       if(document.frmValidationSummaryRep.chkcircle.length )
                                        {
                                                
                                                    for(i=0;i<document.frmValidationSummaryRep.chkcircle.length;i++)
                                                    {
                                                            if(document.frmValidationSummaryRep.chkcircle[i].checked==true)
                                                                    circle= circle+document.frmValidationSummaryRep.chkcircle[i].value +",";
                                                                    // alert(circle);  
                                                    }
                                                    if(circle!="")
                                                    {
                                                        circle=circle.substring(0,circle.length-1);
                                                         url=url+"&officeselected="+circle;
                                                         document.frmValidationSummaryRep.officeselected.value=circle;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.frmValidationSummaryRep.cmbcircle.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.frmValidationSummaryRep.chkcircle.checked==true)
                                                {
                                                            circle= document.frmValidationSummaryRep.chkcircle.value ;
                                                            document.frmValidationSummaryRep.officeselected.value=circle;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Circle..');
                                                            document.frmValidationSummaryRep.cmbcircle.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Circle...');
                                       try{
                                        document.frmValidationSummaryRep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.frmValidationSummaryRep.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                            
                           else if(document.frmValidationSummaryRep.rad_off[3].checked) 
                           // else  if(type=='DN')
                            {
                           
                            ////////
                            var division="";
                            if(document.frmValidationSummaryRep.chkdivision)
                              {
                             //alert(document.frmValidationSummaryRep.chkregion.length);
                                     if(document.frmValidationSummaryRep.chkdivision.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.frmValidationSummaryRep.chkdivision.length;i++)
                                                {
                                                        if(document.frmValidationSummaryRep.chkdivision[i].checked==true)
                                                                division= division+document.frmValidationSummaryRep.chkdivision[i].value +",";
                                                        
                                                }
                                                
                                                if(division!="")
                                                {
                                                    division=division.substring(0,division.length-1);
                                                     url=url+"&officeselected="+division;
                                                     document.frmValidationSummaryRep.officeselected.value=division;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the Division.');
                                                        document.frmValidationSummaryRep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.frmValidationSummaryRep.chkdivision.checked==true)
                                                {
                                                                division= document.frmValidationSummaryRep.chkdivision.value;
                                                                    //division=division.substring(0,division.length-1);
                                                                 url=url+"&officeselected="+division;
                                                                 document.frmValidationSummaryRep.officeselected.value=division;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the Division..');
                                                        document.frmValidationSummaryRep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the Division...');
                                       try{
                                        document.frmValidationSummaryRep.cmbdivision.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.frmValidationSummaryRep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.frmValidationSummaryRep.cmbregion.focus(); 
                                        }
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                         else if(document.frmValidationSummaryRep.rad_off[4].checked) 
                            
                            //else if(type=='CL')
                            {
                            
                            ////////
                            var audit="";
                            if(document.frmValidationSummaryRep.chkaudit)
                              {
                              
                                       if(document.frmValidationSummaryRep.chkaudit.length )
                                        {
                                                
                                                    for(i=0;i<document.frmValidationSummaryRep.chkaudit.length;i++)
                                                    {
                                                            if(document.frmValidationSummaryRep.chkaudit[i].checked==true)
                                                                    audit= audit+document.frmValidationSummaryRep.chkaudit[i].value +",";
                                                                    // alert(circle);  
                                                    }
                                                    if(audit!="")
                                                    {
                                                        audit=audit.substring(0,audit.length-1);
                                                         url=url+"&officeselected="+audit;
                                                         document.frmValidationSummaryRep.officeselected.value=audit;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.frmValidationSummaryRep.cmbaudit.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.frmValidationSummaryRep.chkaudit.checked==true)
                                                {
                                                            circle= document.frmValidationSummaryRep.chkaudit.value ;
                                                            document.frmValidationSummaryRep.officeselected.value=audit;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Audit..');
                                                            document.frmValidationSummaryRep.cmbaudit.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Audit...');
                                       try{
                                        document.frmValidationSummaryRep.cmbaudit.focus(); 
                                        }
                                        catch(e){
                                        document.frmValidationSummaryRep.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                        
                         else if(document.frmValidationSummaryRep.rad_off[5].checked) 
                           // else  if(type=='DN')
                            {
                           
                            ////////
                            var lab="";
                            if(document.frmValidationSummaryRep.chklab)
                              {
                             //alert(document.frmValidationSummaryRep.chkregion.length);
                                     if(document.frmValidationSummaryRep.chklab.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.frmValidationSummaryRep.chklab.length;i++)
                                                {
                                                        if(document.frmValidationSummaryRep.chklab[i].checked==true)
                                                                lab= lab+document.frmValidationSummaryRep.chklab[i].value +",";
                                                        
                                                }
                                                
                                                if(lab!="")
                                                {
                                                    lab=lab.substring(0,lab.length-1);
                                                     url=url+"&officeselected="+lab;
                                                     document.frmValidationSummaryRep.officeselected.value=lab;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the lab.');
                                                        document.frmValidationSummaryRep.cmblab.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.frmValidationSummaryRep.chklab.checked==true)
                                                {
                                                                lab= document.frmValidationSummaryRep.chkdivision.value;
                                                                    //lab=lab.substring(0,lab.length-1);
                                                                 url=url+"&officeselected="+lab;
                                                                 document.frmValidationSummaryRep.officeselected.value=lab;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the lab..');
                                                        document.frmValidationSummaryRep.cmblab.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the lab...');
                                       try{
                                        document.frmValidationSummaryRep.cmblab.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.frmValidationSummaryRep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.frmValidationSummaryRep.cmbregion.focus(); 
                                        }
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                            
                    //}
                        
                    
               // }
       // }
       
      
        //select the output type
        if(document.frmValidationSummaryRep.optoutputtype[0].checked==true)
        {
                url=url+"&outputtype=pdf";
                document.frmValidationSummaryRep.outputtype.value='pdf';
        }
        else if(document.frmValidationSummaryRep.optoutputtype[1].checked==true)
        {
                url=url+"&outputtype=excel";
                document.frmValidationSummaryRep.outputtype.value='excel';
        }
        else if(document.frmValidationSummaryRep.optoutputtype[2].checked==true)
        {
                url=url+"&outputtype=html";
                 document.frmValidationSummaryRep.outputtype.value='html';
        }
        
   
    
    document.frmValidationSummaryRep.action="../../../../../../EmployeeDetailReportServ_New_Interface";
    document.frmValidationSummaryRep.submit();
   /* var req=getTransport();
    req.open("POST",url,false);  
    req.send(null);*/

}

function getofftype()
    {
      //  alert(regionflag);
           //alert("inside office type");   
        
        if(offtypeflag==true)
        {
               
                 var iframe=document.getElementById("diviframeofftype");
                 iframe.style.visibility='visible';
                 iframe.focus();
               // return;
        }
       // var type=document.frmValidationSummaryRep.cmbolevel.options[document.frmValidationSummaryRep.cmbolevel.selectedIndex].value;
    
         //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=region" ;
         var url="../../../../../../EmployeeDetailReportServ_New_Interface?OLevel=offtype" ;
         //alert(url);
                
        document.frmValidationSummaryRep.cmbofftype.focus();
        var req=getTransport();
        req.open("GET",url,true);        
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
                    var iframe=document.getElementById("diviframeofftype");
                    iframe.style.visibility='visible';
                    iframe.focus();
                   // alert(navigator.appName);
                   // alert(navigator.appName.indexOf('Microsoft'));
                   
                  
                    if(navigator.appName.indexOf('Microsoft')!=-1)
                        iframe.innerHTML=req.responseText;
                    else
                        iframe.innerText=req.responseText;
                    iframe.innerHTML=req.responseText;
                   /*
                  document.frmValidationSummaryRep.cmbcircle.disabled=false;
                   var iframe=document.getElementById("diviframecircle");
                            iframe.style.visibility='hidden';
                   var iframe=document.getElementById("diviframedivision");
                            iframe.style.visibility='hidden';
                   */
                   offtypeflag=true;
                  }
            }
        }
        req.send(null);
    }
    


function getAudit(aud)
{
       var region="";
                if(document.frmValidationSummaryRep.chkregion.length > 0)
                {
                            for(i=0;i<document.frmValidationSummaryRep.chkregion.length;i++)
                            {
                                    if(document.frmValidationSummaryRep.chkregion[i].checked==true)
                                            region= region+document.frmValidationSummaryRep.chkregion[i].value +",";
                            }
                            
                            if(region!="")
                            {
                                 if(auditflag==true && aud=='null')
                                {
                                         var iframe=document.getElementById("diviframeaudit");
                                         iframe.style.visibility='visible';
                                         iframe.focus();
                                         document.frmValidationSummaryRep.cmbdivision.disabled=false;
                                         document.frmValidationSummaryRep.cmbcircle.disabled=false; 
                                }
                                region=region.substring(0,region.length-1);
                                //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                
                                var url="../../../../../../EmployeeDetailReportServ_New_Interface?OLevel=audit&regions="+region;
                              // document.frmValidationSummaryRep.cmbregion.focus();
                                var req=getTransport();
                                req.open("GET",url,true);        
                                req.onreadystatechange=function()
                                {
                                    //requesthandle(req);
                                    if(req.readyState==4)
                                    { 
                                          if(req.status==200)
                                          {  
                                            if(auditflag==true && aud=='null')
                                            {
                                            var iframe=document.getElementById("diviframeaudit");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                            
                                            if(navigator.appName.indexOf('Microsoft')!=-1)
                                              iframe.innerHTML=req.responseText;
                                              else
                                              iframe.innerText=req.responseText;
                                            iframe.innerHTML=req.responseText;
                                            document.frmValidationSummaryRep.cmbdivision.disabled=false;
                                              var iframe=document.getElementById("diviframedivision");
                                             iframe.style.visibility='hidden';
                                            
                                            
                                            }
                                             iframe.innerHTML=req.responseText;
                                              circleflag=true;
                                            
                                          }
                                    }
                                }
                                req.send(null);
                            }
                            else
                            {
                                var iframe=document.getElementById("diviframeaudit");
                                iframe.style.visibility='hidden';
                                document.frmValidationSummaryRep.cmbdivision.disabled=false;
                                document.frmValidationSummaryRep.cmbaudit.focus();
                                alert('Please Select a Region');
                            }
            }
            else
            {
            
                             if(document.frmValidationSummaryRep.chkregion.checked==true)
                             {
                                            region= document.frmValidationSummaryRep.chkregion.value ;
                                    
                                    
                                         if(auditflag==true && aud=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframeaudit");
                                                    iframe.style.visibility='visible';
                                                  iframe.focus();
                                                   document.frmValidationSummaryRep.cmbdivision.disabled=false;
                                        }
                                        var url="../../../../../../EmployeeDetailReportServ_New_Interface?OLevel=audit&regions="+region;
                                        var req=getTransport();
                                        req.open("GET",url,true);        
                                        req.onreadystatechange=function()
                                        {
                                            if(req.readyState==4)
                                            { 
                                                  if(req.status==200)
                                                  {  
                                                   var iframe=document.getElementById("diviframeaudit");
                                                    if(navigator.appName.indexOf('Microsoft')!=-1)
                                                       iframe.innerHTML=req.responseText;
                                                         else
                                                       iframe.innerText=req.responseText;
                                                    iframe.innerHTML=req.responseText;
                                                    if(aud=='null')
                                                    {
                                                    
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                    
                                                    document.frmValidationSummaryRep.cmbaudit.disabled=false;
                                                     var iframe=document.getElementById("diviframeaudit");
                                                     iframe.style.visibility='hidden';
                                                    
                                                    
                                                    }
                                                    auditflag=true;
                                                    
                                                  }
                                            }
                                        }
                                        req.send(null);
                            }
                            else
                            {
                               
                            
                                var iframe=document.getElementById("diviframeaudit");
                                iframe.style.visibility='hidden';
                                document.frmValidationSummaryRep.cmbdivision.disabled=false;
                                document.frmValidationSummaryRep.cmbaudit.focus();
                                alert('Please Select a Region');
                            }

            
            
            
            
            }
            
      //}
      
   
}

function getLab(lb)
    {
        
       // alert('division');
       var region="";
       var circle="";

       
       
     // if(document.frmValidationSummaryRep.chkregion)
     // {
      
         
           
               if(document.frmValidationSummaryRep.chkregion.length)
              {
              
                
             
                            for(i=0;i<document.frmValidationSummaryRep.chkregion.length;i++)
                            {
                             
                                    if(document.frmValidationSummaryRep.chkregion[i].checked==true)
                                            region= region+document.frmValidationSummaryRep.chkregion[i].value +",";
                                            //alert('region...'+region);
                                         
                                    
                            }
                 }
       // }
       
     
       
      if(document.frmValidationSummaryRep.chkcircle)
      {
      
            if(document.frmValidationSummaryRep.chkcircle.length)
            {
            
                            for(i=0;i<document.frmValidationSummaryRep.chkcircle.length;i++)
                            {
                                    if(document.frmValidationSummaryRep.chkcircle[i].checked==true)
                                            circle= circle+document.frmValidationSummaryRep.chkcircle[i].value +",";
                                  
                            }
                            if(circle!="")
                            {
                               
                               
                                 if(divisionflag==true && lb=='null')
                                {
                                   
                                       
                                         var iframe=document.getElementById("diviframelab");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                       // return;
                                }
                                circle=circle.substring(0,circle.length-1);
                                region=region.substring(0,region.length-1);
                               // var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=division&circles="+circle;
                              
                              var url="../../../../../../EmployeeDetailReportServ_New_Interface?OLevel=lab&circles="+circle+"&regions="+region;
                             //alert(url);
                              
                              // document.frmValidationSummaryRep.cmbregion.focus();
                                var req=getTransport();
                                req.open("GET",url,true);        
                                req.onreadystatechange=function()
                                {
                                    //requesthandle(req);
                                    if(req.readyState==4)
                                    { 
                                          if(req.status==200)
                                          { 
                                           // alert("success");
                                            var iframe=document.getElementById("diviframelab");
                                            
                                            //var type=document.frmValidationSummaryRep.cmbolevel.options[document.frmValidationSummaryRep.cmbolevel.selectedIndex].value;
                                            if(lb=='null')
                                            {
                                            //alert("visible");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                            }
                                            if(navigator.appName.indexOf('Microsoft')!=-1)
                                              iframe.innerHTML=req.responseText;
                                              else
                                              iframe.innerText=req.responseText;
                                            iframe.innerHTML=req.responseText;
                                             // alert(req.responseText);
                                            // iframe.innerHTML=req.responseText;
                                             
                                           divisionflag=true;  
                                          }
                                    }
                                }
                                req.send(null);
                            }
                            else
                            {
                                var iframe=document.getElementById("diviframelab");
                                iframe.style.visibility='hidden';
                                document.frmValidationSummaryRep.cmblab.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                }
                else
                {
                
                             if(document.frmValidationSummaryRep.chkcircle.checked==true)
                             {
                                            circle= document.frmValidationSummaryRep.chkcircle.value ;
                                    
                           
                                         if(divisionflag==true && lb=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframelab");
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                              //  return;
                                        }
                                       // circle=circle.substring(0,circle.length-1);
                                      //  var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=division&circles="+circle;
                                      region=region.substring(0,region.length-1);
                                      //alert(region);
                                      var url="../../../../../../EmployeeDetailReportServ_New_Interface?OLevel=lab&circles="+circle;
                                      //alert(url);
                                      // document.frmValidationSummaryRep.cmbregion.focus();
                                        var req=getTransport();
                                        req.open("GET",url,true);        
                                        req.onreadystatechange=function()
                                        {
                                            //requesthandle(req);
                                            if(req.readyState==4)
                                            { 
                                                  if(req.status==200)
                                                  { 
                                                    var iframe=document.getElementById("diviframelab");
                                                    //var type=document.frmValidationSummaryRep.cmbolevel.options[document.frmValidationSummaryRep.cmbolevel.selectedIndex].value;
                                                    if(lb=='null')
                                                    {
                                                    
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                    }
                                                    if(navigator.appName.indexOf('Microsoft')!=-1)
                                              iframe.innerHTML=req.responseText;
                                              else
                                              iframe.innerText=req.responseText;
                                                    iframe.innerHTML=req.responseText;    
                                                     //iframe.innerHTML=req.responseText;
                                                     
                                                   divisionflag=true;  
                                                  }
                                            }
                                        }
                                        req.send(null);
                            }
                            else
                            {
                                var iframe=document.getElementById("diviframelab");
                                iframe.style.visibility='hidden';
                                document.frmValidationSummaryRep.cmblab.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                
                }
            
      }
   
}
