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

/*
function hideofficelevel()
{
    officevisible('none','none','none','none'); 
    hideoffice()
    var offlevel=document.getElementById("troffice");
            offlevel.style.display="none";
            
    document.frmValidationSummaryRep.optofficelevel[0].checked=true;
    var offlevel=document.getElementById("trofficelevel");
    offlevel.style.display="none";
}
*/
function showofficelevel()
{
    document.frmValidationSummaryRep.optofficelevel[1].checked=true;
    var offlevel=document.getElementById("trofficelevel");
    offlevel.style.display="block";
    
}




function officevisible(r,c,d,o,a,l)
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
    var ot=document.getElementById("divaudit");
    ot.style.display=a;
    var ot=document.getElementById("divlab");
    ot.style.display=l;
    
    var rn=document.getElementById("cmbregion");
    rn.style.display=r;
    var cl=document.getElementById("cmbcircle");
    cl.style.display=c;
    var dn=document.getElementById("cmbdivision");
    dn.style.display=d;
    var ot=document.getElementById("cmbofftype");
    ot.style.display=o;
    var ot=document.getElementById("cmbaudit");
    ot.style.display=a;
    var ot=document.getElementById("cmblab");
    ot.style.display=l;
}


function selectoption2()
{
   
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    document.getElementById("divallhier").style.display='none';
    
    
    if(document.frmValidationSummaryRep.rad_off[0].checked)
    {
       var offlevel=document.getElementById("trofficeselection");
       offlevel.style.display="none";
       document.getElementById("selectofficediv").style.display="none";
          document.frmValidationSummaryRep.allhier.checked=false;
       var divlab=document.getElementById("divlb");
       divlab.style.display="none";
       var divaudit=document.getElementById("divaud");
       divaudit.style.display="none";
    }

  if(document.frmValidationSummaryRep.rad_off[1].checked)
  {
   document.getElementById("selectofficediv").style.display="block";
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    document.getElementById("divallhier").style.display='block';
     officevisible('block','none','none','none','none','none');
       fnchange();
     document.frmValidationSummaryRep.allhier.checked=false;
     document.frmValidationSummaryRep.audi.checked=false;
  }
  
  else if(document.frmValidationSummaryRep.rad_off[2].checked)
  {
   document.getElementById("selectofficediv").style.display="block";
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    document.getElementById("divallhier").style.display='block';
  
     officevisible('block','block','none','none','none','none');
       fnchange1();
     document.frmValidationSummaryRep.allhier.checked=false;
     document.frmValidationSummaryRep.lab.checked=false;
  }
  
  else if(document.frmValidationSummaryRep.rad_off[3].checked)
  {
  document.getElementById("selectofficediv").style.display="block";
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    document.getElementById("divallhier").style.display='block';
  
    officevisible('block','block','block','block','none','none');
      document.frmValidationSummaryRep.allhier.checked=false;
     document.frmValidationSummaryRep.allhier.checked=false;
     var divlab=document.getElementById("divlb");
     divlab.style.display="none";
     var divaudit=document.getElementById("divaud");
     divaudit.style.display="none";
  }
  
  else if(document.frmValidationSummaryRep.rad_off[4].checked)
  {
   document.getElementById("selectofficediv").style.display="block";
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    document.getElementById("divallhier").style.display='block';
  
     officevisible('block','none','none','none','block','none');
       document.frmValidationSummaryRep.allhier.checked=false;
     document.frmValidationSummaryRep.allhier.checked=false;
     var divlab=document.getElementById("divlb");
     divlab.style.display="none";
     var divaudit=document.getElementById("divaud");
     divaudit.style.display="none";    
  }
  
  else if(document.frmValidationSummaryRep.rad_off[5].checked)
  {
   document.getElementById("selectofficediv").style.display="block";
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    document.getElementById("divallhier").style.display='block';
  
     officevisible('block','block','none','none','none','block');
        document.frmValidationSummaryRep.allhier.checked=false;
     document.frmValidationSummaryRep.allhier.checked=false;
     var divlab=document.getElementById("divlb");
     divlab.style.display="none";
     var divaudit=document.getElementById("divaud");
     divaudit.style.display="none";    
     var divaudit=document.getElementById("divaud");
     divaudit.style.display="none";    

  }
  
  document.frmValidationSummaryRep.cmbcircle.disabled=true;
    document.frmValidationSummaryRep.cmbdivision.disabled=true;
    
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

function getLevel()
    {
    //alert("show me");
        var type=document.frmValidationSummaryRep.cmbolevel.options[document.frmValidationSummaryRep.cmbolevel.selectedIndex].value;
        //alert('getoffice:'+type);
       officevisible('none','none','none','none','none','none'); 
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
                document.frmValidationSummaryRep.aggid.checked=false;
              }
           
      }
    }
    
    
  
function getRegion()
    {
      //  alert(regionflag);
        //alert("come inside");
        
        if(regionflag==true)
        {
               
                 var iframe=document.getElementById("diviframeregion");
                 iframe.style.visibility="visible";
                 //iframe.focus();
               // return;
        }
       // var type=document.frmValidationSummaryRep.cmbolevel.options[document.frmValidationSummaryRep.cmbolevel.selectedIndex].value;
    
         //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=region" ;
         var url="../../../../../../EmpValidationDetailServ_New_Interface?OLevel=region" ;
        //alert(url);
        document.frmValidationSummaryRep.cmbregion.focus();
        
        var req=getTransport();
        req.open("GET",url,false);        
        req.send(null);
     /*  req.onreadystatechange=function()
        {
            //requesthandle(req);
            if(req.readyState==4)
            { 
                  if(req.status==200)
                  {  */
                    //alert(req.responseText);
                 
                 // document.frames("iframregion").document.body.innerHTML=req.responseText;
                  //(document.frames("iframregion").document.body.innerText);
                    var iframe=document.getElementById("diviframeregion");
                    iframe.style.visibility="visible";
                   // iframe.focus();
                   // alert(navigator.appName);
                   // alert(navigator.appName.indexOf('Microsoft'));
                   
                  
                    if(navigator.appName.indexOf('Microsoft')!=-1)
                        iframe.innerHTML=req.responseText;
                    else
                        iframe.innerHTML=req.responseText;
                       
                   
                  document.frmValidationSummaryRep.cmbcircle.disabled=false;
                  document.frmValidationSummaryRep.cmbaudit.disabled=false;
                   var iframe=document.getElementById("diviframecircle");
                            iframe.style.visibility='hidden';
                   var iframe=document.getElementById("diviframedivision");
                            iframe.style.visibility='hidden';
                   var iframe=document.getElementById("diviframeaudit");
                            iframe.style.visibility='hidden';
                   var iframe=document.getElementById("diviframelab");
                            iframe.style.visibility='hidden';
                            
                   
                   regionflag=true;
                  }
            /*}
        }
        req.send(null);
    }*/
    
function getCircle(cir)
{
     
        
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
                                
                                var url="../../../../../../ListAllScopeofOffices?OLevel=circle&regions="+region;
                             //   alert(url);
                              // document.frmValidationSummaryRep.cmbregion.focus();
                                var req=getTransport();
                                req.open("GET",url,false);        
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
                                            
                                            document.frmValidationSummaryRep.cmbdivision.disabled=false;
                                            document.frmValidationSummaryRep.cmblab.disabled=false;
                                             var iframe=document.getElementById("diviframedivision");
                                             iframe.style.visibility='hidden';
                                             var iframe=document.getElementById("diviframelab");
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
                                document.frmValidationSummaryRep.cmblab.disabled=false;
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
                                               // return;
                                        }
                                       // region=region.substring(0,region.length-1);
                                       // var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                        
                                        var url="../../../../../../ListAllScopeofOffices?OLevel=circle&regions="+region;
                                        
                                      // document.frmValidationSummaryRep.cmbregion.focus();
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
                                                    // if((type=='CL' || type=='DN') && cir=='null' )
                                                    if(navigator.appName.indexOf('Microsoft')!=-1)
                                                       iframe.innerHTML=req.responseText;
                                                         else
                                                       iframe.innerText=req.responseText;
                                                    if(cir=='null')
                                                    {
                                                    
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                    
                                                    document.frmValidationSummaryRep.cmbdivision.disabled=false;
                                                    document.frmValidationSummaryRep.cmblab.disabled=false;
                                                      var iframe=document.getElementById("diviframedivision");
                                                     iframe.style.visibility='hidden';
                                                     var iframe=document.getElementById("diviframelab");
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
                                document.frmValidationSummaryRep.cmblab.disabled=false;
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
                                req.open("GET",url,false);        
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
                                        req.open("GET",url,false);        
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
function designationclose()
{
    
    var iframe=document.getElementById("divdesignation");
    iframe.style.visibility='hidden';
  
}
function rankclose()
{
    
   // var iframe=document.getElementById("divranknew");
  //  iframe.style.visibility='hidden';
  
}
function cadreclose()
{
    
  //  var iframe=document.getElementById("divcadrenew");
  //  iframe.style.visibility='hidden';
  
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

function labselectAll()
{
    if(document.frmValidationSummaryRep.chklab)
      {
      
            for(i=0;i<document.frmValidationSummaryRep.chklab.length;i++)
            {
                    document.frmValidationSummaryRep.chklab[i].checked=true;
                    
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
        }
}

function labclose()
{
    
    var iframe=document.getElementById("diviframelab");
    iframe.style.visibility='hidden';
  
}

function auditclose()
{
    
    var iframe=document.getElementById("diviframeaudit");
    iframe.style.visibility='hidden';
  
}

function designationselectAll()
{
    if(document.frmValidationSummaryRep.chkdesig)
      {
      
            for(i=0;i<document.frmValidationSummaryRep.chkdesig.length;i++)
            {
                    document.frmValidationSummaryRep.chkdesig[i].checked=true;
                    
            }
        }
}
function rankselectAll()
{
    if(document.frmValidationSummaryRep.chkrank)
      {
      
            for(i=0;i<document.frmValidationSummaryRep.chkrank.length;i++)
            {
                    document.frmValidationSummaryRep.chkrank[i].checked=true;
                    
            }
        }
}
function cadreselectAll()
{
    if(document.frmValidationSummaryRep.chkcadre)
      {
      
            for(i=0;i<document.frmValidationSummaryRep.chkcadre.length;i++)
            {
                    document.frmValidationSummaryRep.chkcadre[i].checked=true;
                    
            }
        }
}


/*function regiononchange()
{
     var type=document.frmValidationSummaryRep.cmbolevel.options[document.frmValidationSummaryRep.cmbolevel.selectedIndex].value;
   circlefalg=false;
    if(type=='CL' || type=='DN')
    {
        getCircle('circle');
    }

}

function circleonchange()
{
     var type=document.frmValidationSummaryRep.cmbolevel.options[document.frmValidationSummaryRep.cmbolevel.selectedIndex].value;
    divisionfalg=false;
    if(type=='DN')
    {
    getDivision('division');
    }

}*/

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



function hidedisignation()
{
    document.frmValidationSummaryRep.optdesignation[0].checked=true;
   var offlevel=document.getElementById("trsergroup");
    offlevel.style.display="none";
    
      var offlevel=document.getElementById("divdest");
    offlevel.style.visibility="hidden";
     var offlevel=document.getElementById("cmbDesignation");
    offlevel.style.visibility="hidden";
    document.frmValidationSummaryRep.cmbsgroup.value="0";
}

function showdesignation()
{
   document.frmValidationSummaryRep.optdesignation[1].checked=true;
    var offlevel=document.getElementById("trsergroup");
    offlevel.style.display="block";
}

function getDesignation()
{
document.getElementById("desigblock").style.display="block";
//alert("getDesisg");
   var type=document.frmValidationSummaryRep.cmbsgroup.options[document.frmValidationSummaryRep.cmbsgroup.selectedIndex].value;
  // alert(type);
   var type1=document.getElementById("cmbsgroup").value;
 //  alert(type1+"ddd");
   if(type!=0)
    {
  //  alert("inside new");
           // var din=document.getElementById("divdest");
           // din.style.visibility="visible";
           // document.frmValidationSummaryRep.cmbDesignation.style.visibility="visible";
            loadOfficesByType(type);
    }
   else
    {
             var des=document.getElementById("cmbDesignation");
             des.innerHTML='';
            // var din=document.getElementById("divdest");
           //  din.style.visibility="hidden";
           //  document.frmValidationSummaryRep.cmbDesignation.style.visibility="hidden";
     }
}
    
/*function loadOfficesByType(type)
    {
        //alert(type);
        var type=document.frmValidationSummaryRep.cmbsgroup.options[document.frmValidationSummaryRep.cmbsgroup.selectedIndex].value;
        //startwaiting(document.frmValidationSummaryRep) ;
         //service=null;
      
       //var url="../../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
       var url="../../../../../../DesigCadRankServ_New?Command=Desig&cmbsgroup=" + type ;
       //alert(url);
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadDesignation(req);
        }
        req.send(null);
    }*/
    
    
    function loadOfficesByType(type)
    {
    
       var type=document.frmValidationSummaryRep.cmbsgroup.options[document.frmValidationSummaryRep.cmbsgroup.selectedIndex].value;
       var url="../../../../../../DesigCadRankServ_New?Command=Desig&cmbsgroup=" + type ;
       
       var req=getTransport();
        //alert(req);
            req.open("GET",url,false);       
   
            req.send(null);
            var divdes=document.getElementById("divdesignation");
            divdes.style.visibility="visible";
            //alert(navigator.appName);
        /*   var txt=document.createElement("hidden");
            txt.setAttribute("id","restxt");
            txt.setAttribute("value",req.responseText);*/
           // alert(txt.value);
           if(navigator.appName.indexOf('Microsoft')!=-1)
                {
               // alert("1");
                divdes.innerHTML=req.responseText;
                }
            else
                {
               // alert("2");
           //     document.getElementById("divdesignation").innerHTML=txt.value;
                divdes.innerHTML=req.responseText;
                }
             //alert("show");
            //document.getElementById("cmbRank").visibility="idden";  
            //document.getElementById("cmbCadre").visibility="hidden";
   
            }
 
    
function  loadDesignation(req)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
               
                var des=document.getElementById("cmbDesignation");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                des.innerHTML="";
                
                // stopwaiting(document.frmValidationSummaryRep);
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


*/

function frmsubmit()
{
    //alert('inside submit');
     if(nullcheck())
    {

        var url="../../../../../../ListAllScopeofOffices?OLevel=submit" ;
        
        //office validation
        /*
        if(document.frmValidationSummaryRep.optofficelevel[0].checked==true)
        {
              alert("1");
                url=url+"&offlevel=all";
                document.frmValidationSummaryRep.offlevel.value='all';
        }*/
      //  else
     //   {
             //alert(2);
             
             /*
                url=url+"&offlevel=specific";
                 document.frmValidationSummaryRep.offlevel.value='specific';
                */
                // select the office 
                
                
                 // var type=document.frmValidationSummaryRep.cmbolevel.options[document.frmValidationSummaryRep.cmbolevel.selectedIndex].value;
                  
                 // var type=document.frmValidationSummaryRep.rad_off.value;
                 // alert(type);
                  /*
                 if(type=="")
                 {
                    alert('Select the Office Level');
                    document.frmValidationSummaryRep.cmbolevel.focus();
                    return;
                }
               else
               {
                    url=url+"&office="+type;
                    document.frmValidationSummaryRep.office.value=type;
                    /*
                    if(document.frmValidationSummaryRep.optoffice[0].checked==true)
                    {
                            url=url+"&officetype=all";
                             document.frmValidationSummaryRep.officetype.value='all';
                    }
                    else
                    {*/
                            //url=url+"&officetype=specific";
                            // document.frmValidationSummaryRep.officetype.value='specific';
                             
                             
                             
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
                                                     //                alert(circle);  
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
                                                     //                alert(circle);  
                                                    }
                                                    if(audit!="")
                                                    {
                                                        audit=audit.substring(0,audit.length-1);
                                                         url=url+"&officeselected="+audit;
                                                         document.frmValidationSummaryRep.officeselected.value=audit;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Audit.');
                                                            document.frmValidationSummaryRep.cmbaudit.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.frmValidationSummaryRep.chkaudit.checked==true)
                                                {
                                                            audit= document.frmValidationSummaryRep.chkaudit.value ;
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
                                                       alert('Select the Lab.');
                                                        document.frmValidationSummaryRep.cmblab.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.frmValidationSummaryRep.chklab.checked==true)
                                                {
                                                                lab= document.frmValidationSummaryRep.chklab.value;
                                                                    //division=division.substring(0,division.length-1);
                                                                 url=url+"&officeselected="+lab;
                                                                 document.frmValidationSummaryRep.officeselected.value=lab;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the Lab..');
                                                        document.frmValidationSummaryRep.cmblab.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the Lab...');
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
        
   
   /*  document.frmValidationSummaryRep.action=url;
    //document.frmValidationSummaryRep.action="../../../../../../ListAllScopeofOffices";
    document.frmValidationSummaryRep.method="Post";*/
    document.frmValidationSummaryRep.submit();
 /*   var req=getTransport();
    req.open("POST",url,false);  
    req.send(null);*/
}
}


function nullcheck()
{
/*var finyear=document.getElementById("cmbFinancialYear").value;
if(finyear==0||finyear.length==0)
    {
   alert('Select the period');
   return false
   }*/
/*if(document.getElementById("optselect2").checked!=true||document.getElementById("optselect1").checked!=true)
    {
    alert('Select the Specific Rank');
    return false;
    }*/
    
   // alert(document.getElementById("cmbsgroup").value);
       //var type=document.frmValidationSummaryRep.cmbsgroup.options[document.frmValidationSummaryRep.cmbsgroup.selectedIndex].value;
       //alert('type'+type)
       //alert('1')
    if(document.frmValidationSummaryRep.optselect2.checked)    
                            {
                             // alert("inside rank");
                            ////////
                            var rankval="";
                            if(document.frmValidationSummaryRep.chkrank)
                              {
                             // alert("1")
                                        if(document.frmValidationSummaryRep.chkrank.length)
                                        {
                                      //  alert("2")
                                                  for(i=0;i<document.frmValidationSummaryRep.chkrank.length;i++)
                                                    {
                                                    //alert("3")
                                                            if(document.frmValidationSummaryRep.chkrank[i].checked==true)
                                                            {
                                                           // alert("31")
                                                              rankval= rankval+document.frmValidationSummaryRep.chkrank[i].value +",";
                                                           // return true;   
                                                            }
                                                            else if(document.frmValidationSummaryRep.chkrank.checked==true)
                                                            {
                                                           // alert("32")
                                                              rankval= rankval+document.frmValidationSummaryRep.chkrank.value;
                                                           // return true;   
                                                            }
                                                    }
                                                    if(rankval==""||rankval==0)
                                                    {
                                                    //alert("4")
                                                     /*   region=region.substring(0,region.length-1);
                                                         url=url+"&officeselected="+region;
                                                         //alert(url);
                                                         document.frmValidationSummaryRep.officeselected.value=region;
                                                         //alert(document.frmValidationSummaryRep.officeselected.value);
                                                    }
                                                    else
                                                    {*/
                                                           alert('Select the Designation..');
                                                            //document.frmValidationSummaryRep.chkrank[0].focus(); 
                                                            return false;
                                                    }
                                            
                                        }
                                        else{
                                           //alert("5")
                                                if(document.frmValidationSummaryRep.chkrank.checked==true)
                                                {
                                                //alert("6")
                                                            rankval= document.frmValidationSummaryRep.chkrank.value ;
                                                            document.frmValidationSummaryRep.officeselected.value=rankval;
                                                            return true;
                                                }
                                                 else
                                                    {
                                                        //alert("7")
                                                           alert('Select the Service Group1..');
                                                           // document.frmValidationSummaryRep.cmbregion.focus(); 
                                                            return false;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                //alert("8")
                                       alert('Select the Service Group...');
                                      //  document.frmValidationSummaryRep.cmbregion.focus(); 
                                        return false ;
                                }    
                            ////
                            
                            }   
       
       
       
/*if(document.frmValidationSummaryRep.chkrank.checked!=true)    
    {
    alert('Select the Service Group');
    return false;
    }*/
//e  
return true;
}


function hideSpecRank()
{
 if(document.frmValidationSummaryRep.optselect1.checked)
    {
      //alert(document.frmValidationSummaryRep.optselect[3].checked);
      document.frmValidationSummaryRep.optselect.checked=false;
      
        // var id=document.getElementById("divdest");
        //id.style.display='none';
        var divdesigid=document.getElementById("divdesignation");
        divdesigid.style.visibility='hidden';
        var id=document.getElementById("divrank");
        id.style.display='none';
        var desigblockid=document.getElementById("desigblock");
        desigblockid.style.display='none';
        
       //  var id=document.getElementById("divcadre");
      //  id.style.display='none';     
       //  var divcadreid=document.getElementById("divcadrenew");
       // divcadreid.style.visibility='hidden';
    
    }
}





//////////////////////////// for next purpose ///////////////////////
///////////////////////  for designation,cadre,rank   ///////////////////////

function sellectall()
{
   
  //  if(document.frmValidationSummaryRep.optselect[1].checked)
  if(document.frmValidationSummaryRep.optselectgrp[1].checked)
    {
      //alert("1");
      //alert(document.frmValidationSummaryRep.optselect[1].checked);
      var id=document.getElementById("divselect");
      id.style.display='block';          
                
    }    
                      
    else
    {
      var id=document.getElementById("divselect");
      id.style.display='none';      
     
     }
        
}

function hidedesig()
{
//alert("here");
   //if(document.frmValidationSummaryRep.optselect[0].checked)
   if(document.frmValidationSummaryRep.optselectgrp[0].checked)
   {
  // alert('sec')
      var id1=document.getElementById("divselect");
      id1.style.display='none';
      var id2=document.getElementById("divdest");
      id2.style.display='none';
      var id3=document.getElementById("divrank");
      id3.style.display='none';
      var id4=document.getElementById("divcadre");
      id4.style.display='none';
    document.getElementById("desigblock").style.display='none';
      
   }
   
   else
   {
     var id1=document.getElementById("divselect");
      id1.style.display='block';
      var id2=document.getElementById("divdest");
      id2.style.display='block';
      var id3=document.getElementById("divrank");
      id3.style.display='block';
      var id4=document.getElementById("divcadre");
      id4.style.display='block';
      document.getElementById("desigblock").style.display='block';
   }

}
/*
function  selectoption1()
{
//alert('test');


    if(document.frmValidationSummaryRep.optselect[0].checked)
    {
      alert(document.frmValidationSummaryRep.optselect[0].checked);
        var id=document.getElementById("divdest");
        id.style.display='block';
        var id=document.getElementById("divrank");
        id.style.display='none';
        var id=document.getElementById("divcadre");
        id.style.display='none';
    }
    
    else if(document.frmValidationSummaryRep.optselect[1].checked)
    {
      alert(document.frmValidationSummaryRep.optselect[1].checked);
         var id=document.getElementById("divdest");
        id.style.display='none';
        var id=document.getElementById("divrank");
        id.style.display='block';
         var id=document.getElementById("divcadre");
        id.style.display='none';       
    
    }
    else if(document.frmValidationSummaryRep.optselect[2].checked)
    {
       alert(document.frmValidationSummaryRep.optselect[2].checked);
        var id=document.getElementById("divdest");
        id.style.display='none';
        var id=document.getElementById("divrank");
        id.style.display='none';
        var id=document.getElementById("divcadre");
        id.style.display='block';       
    
    
    }

}
    */
    
function  selectoption1()
{
//alert('test');
   /*
    //if(document.frmValidationSummaryRep.optselect[2].checked)
    if(document.frmValidationSummaryRep.optselect[0].checked)
    {
      //alert(document.frmValidationSummaryRep.optselect[2].checked);
        var id=document.getElementById("divdest");
        id.style.display='block';
        var divdesigid=document.getElementById("divdesignation");
        divdesigid.style.visibility='hidden';
        var id=document.getElementById("divrank");
        id.style.display='none';
        //var divcadreid=document.getElementById("divranknew");
       // divcadreid.style.visibility='hidden';
        var id=document.getElementById("divcadre");
        id.style.display='none';
      //  var divcadreid=document.getElementById("divcadrenew");
      //  divcadreid.style.visibility='hidden';
    }
    */
   // else if(document.frmValidationSummaryRep.optselect[3].checked)
    if(document.frmValidationSummaryRep.optselect.checked)
    {
      //alert(document.frmValidationSummaryRep.optselect[3].checked);
      document.frmValidationSummaryRep.optselect1.checked=false;
      
         var id=document.getElementById("divdest");
        id.style.display='none';
        var divdesigid=document.getElementById("divdesignation");
        divdesigid.style.visibility='hidden';
        var id=document.getElementById("divrank");
        id.style.display='block';
         var id=document.getElementById("divcadre");
        id.style.display='none';     
       //  var divcadreid=document.getElementById("divcadrenew");
       // divcadreid.style.visibility='hidden';
    
    }
    
   // else if(document.frmValidationSummaryRep.optselect[4].checked)
   /*
   if(document.frmValidationSummaryRep.optselect[2].checked)
    {
       //alert(document.frmValidationSummaryRep.optselect[4].checked);
        var id=document.getElementById("divdest");
        id.style.display='none';
        var divdesigid=document.getElementById("divdesignation");
        divdesigid.style.visibility='hidden';
        var id=document.getElementById("divrank");
        id.style.display='none';
       //  var divcadreid=document.getElementById("divranknew");
       // divcadreid.style.visibility='hidden';
        var id=document.getElementById("divcadre");
        id.style.display='block';       
    
    
    }
  */
}
     
     /*   
function selectoption2()
{
  if(document.frmValidationSummaryRep.rad_off[0].checked)
  {
    
  
  }

}
   */ 
function checkGroup()
{
        
  var type=document.frmValidationSummaryRep.cmbsgroup.options[document.frmValidationSummaryRep.cmbsgroup.selectedIndex].value;
  if(type==0)
   {
     alert('Select Service Group');
     document.frmValidationSummaryRep.cmbsgroup.focus();
     return false;
   }
}    



function getDesignation1()
{
document.getElementById("desigblock").style.display='block';
  var type=document.frmValidationSummaryRep.cmbsgroup1.options[document.frmValidationSummaryRep.cmbsgroup1.selectedIndex].value;
  //alert(type)     
  if(type!=0)
  {
    loadOfficesByType1(type);
  }
  else
  {
     var des=document.getElementById("cmbRank");
     des.innerHTML='';
  }
}


/*function loadOfficesByType1(type)
{
        //alert(type);
  var type=document.frmValidationSummaryRep.cmbsgroup1.options[document.frmValidationSummaryRep.cmbsgroup1.selectedIndex].value;
  startwaiting(document.frmValidationSummaryRep) ;
  //var url="../../../../../../SR_Based_QueryServ.rep?Command=SGroup&cmbsgroup=" + type ; DesigCadRankServ
  var url="../../../../../../DesigCadRankServ?Command=Rank&cmbsgroup=" + type ; 
  var req=getTransport();
        //alert(url);
  req.open("GET",url,true);        
  req.onreadystatechange=function()
  {
            
     loadDesignation1(req);
  }
    req.send(null);
}*/

 function loadOfficesByType1(type)
    {
        //alert(type);
        var type=document.frmValidationSummaryRep.cmbsgroup1.options[document.frmValidationSummaryRep.cmbsgroup1.selectedIndex].value;
        //startwaiting(document.frmValidationSummaryRep) ;
         //service=null;
      
       //var url="../../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
       var url="../../../../../../DesigCadRankServ_New?Command=Rank&cmbsgroup=" + type ;
       
        var req=getTransport();
        req.open("GET",url,false);        
        req.send(null);
            var divdes=document.getElementById("divdesignation");
            divdes.style.visibility="visible";
            //divdes.focus();
            
           if(navigator.appName.indexOf('Microsoft')!=-1)
                divdes.innerHTML=req.responseText;
            else
                divdes.innerHTML=req.responseText;
             //alert("show");
            //document.getElementById("cmbRank").visibility='hidden';  
            //document.getElementById("cmbCadre").visibility='hidden';
            }
           

function  loadDesignation1(req)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
                //alert(req);
                var des=document.getElementById("cmbRank");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                des.innerHTML="";
                
                 stopwaiting(document.frmValidationSummaryRep);
                if(flag=="failure")
                {
                    alert("No Rank exists under this level");
                }
                else if(flag=="sessionout")
            {
                alert('Session is closed');
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                self.close();
                return;
            }
                else
                {   
                
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="--Select Rank--";
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

function checkGroup1()
{
        
        var type=document.frmValidationSummaryRep.cmbsgroup1.options[document.frmValidationSummaryRep.cmbsgroup1.selectedIndex].value;
        if(type==0)
        {
            alert('Select Service Group');
            document.frmValidationSummaryRep.cmbsgroup1.focus();
            return false;
        }
}

function getDesignation2()
{
document.getElementById("desigblock").style.display='block';
  var type=document.frmValidationSummaryRep.cmbsgroup2.options[document.frmValidationSummaryRep.cmbsgroup2.selectedIndex].value;
       
  if(type!=0)
  {
    loadOfficesByType2(type);
  }
  else
  {
     var des=document.getElementById("cmbCadre");
     des.innerHTML='';
  }
}



 function loadOfficesByType2(type)
    {
    //alert("cadre");
        //alert(type);
        var type=document.frmValidationSummaryRep.cmbsgroup2.options[document.frmValidationSummaryRep.cmbsgroup2.selectedIndex].value;
        //startwaiting(document.frmValidationSummaryRep) ;
         //service=null;
      
       //var url="../../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
       var url="../../../../../../DesigCadRankServ_New?Command=Cadre&cmbsgroup=" + type ;
       alert(url);
        var req=getTransport();
        req.open("GET",url,false);        
        req.send(null);
            var divdes=document.getElementById("divdesignation");
            divdes.style.visibility="visible";
           if(navigator.appName.indexOf('Microsoft')!=-1)
                divdes.innerHTML=req.responseText;
            else
                divdes.innerHTML=req.responseText;
       
    }

function  loadDesignation2(req)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
                //alert(req);
                var des=document.getElementById("cmbCadre");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                des.innerHTML="";
                
                 stopwaiting(document.frmValidationSummaryRep);
                if(flag=="failure")
                {
                    alert("No Rank exists under this level");
                }
                else if(flag=="sessionout")
            {
                alert('Session is closed');
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                self.close();
                return;
            }
                else
                {   
                
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="--Select Cadre--";
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

function checkGroup2()
{
        
  var type=document.frmValidationSummaryRep.cmbsgroup2.options[document.frmValidationSummaryRep.cmbsgroup2.selectedIndex].value;
  if(type==0)
  {
     alert('Select Service Group');
     document.frmValidationSummaryRep.cmbsgroup2.focus();
     return false;
  }
}

function desigChange()
{
//alert("designation");
var desig="";
//alert('designationchange');
if(document.frmValidationSummaryRep.chkdesig)
      {
     // alert("1");
               if(document.frmValidationSummaryRep.chkdesig.length)
              {
              //alert("2");
             
                            for(i=0;i<document.frmValidationSummaryRep.chkdesig.length;i++)
                            {
                            
                                    if(document.frmValidationSummaryRep.chkdesig[i].checked==true)
                                        { 
                                           //alert("inside");
                                            desig=desig+document.frmValidationSummaryRep.chkdesig[i].value +",";
                                         }
                            }
                            
                            if(desig!="")
                            {
                            //alert("ids:"+desig);
                            desig=desig.substring(0,desig.length-1);
                            var url="../../../../../../ListAllScopeofOffices?desigs="+desig;
                            }
               }
        }  
        
}

function rankChange()
{
var rank="";
//alert('rank');
if(document.frmValidationSummaryRep.chkdesig)
      {
      //alert("1");
               if(document.frmValidationSummaryRep.chkrank.length)
              {
        //      alert("2");
             
                            for(i=0;i<document.frmValidationSummaryRep.chkrank.length;i++)
                            {
                            
                                    if(document.frmValidationSummaryRep.chkrank[i].checked==true)
                                        { 
                                           //alert("inside");
                                            rank=rank+document.frmValidationSummaryRep.chkrank[i].value +",";
                                         }
                            }
                            
                            if(rank!="")
                            {
                            //alert("ids:"+rank);
                            rank=rank.substring(0,rank.length-1);
                            var url="../../../../../../ListAllScopeofOffices?ranks="+rank;
                            
                            }
               }
        }  
        
}
    
function cadreChange()
{
var cadre="";
//alert('cadre');
if(document.frmValidationSummaryRep.chkcadre)
      {
      //alert("1");
               if(document.frmValidationSummaryRep.chkcadre.length)
              {
             // alert("2");
             
                            for(i=0;i<document.frmValidationSummaryRep.chkcadre.length;i++)
                            {
                            
                                    if(document.frmValidationSummaryRep.chkcadre[i].checked==true)
                                        { 
                                           //alert("inside");
                                            cadre=cadre+document.frmValidationSummaryRep.chkcadre[i].value +",";
                                         }
                            }
                            
                            if(cadre!="")
                            {
                            //alert("ids:"+cadre);
                            cadre=cadre.substring(0,cadre.length-1);
                            var url="../../../../../../ListAllScopeofOffices?cadres="+cadre;
                            }
               }
        }  
        
}

function getofftype()
    {
   // alert('inside office');
      //  alert(regionflag);
           //alert("inside office type");   
        
        if(offtypeflag==true)
        {
               
                 var iframe=document.getElementById("diviframeofftype");
                 iframe.style.visibility="visible";
                // iframe.focus();
               // return;
        }
       // var type=document.frmValidationSummaryRep.cmbolevel.options[document.frmValidationSummaryRep.cmbolevel.selectedIndex].value;
    
         //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=region" ;
         var url="../../../../../../ListAllScopeofOffices?OLevel=offtype" ;
         //alert(url);
                
        //document.frmValidationSummaryRep.cmbofftype.focus();
        //alert('after focus');
        var req=getTransport();
        req.open("GET",url,false);        
        req.send(null);
        /*req.onreadystatechange=function()
        {
            //requesthandle(req);
            if(req.readyState==4)
            { 
                  if(req.status==200)
                  { */
                 // alert("inside");
                 // alert(req.status);
                   // alert(req.responseText);
                 
                 // document.frames("iframregion").document.body.innerHTML=req.responseText;
                  //(document.frames("iframregion").document.body.innerText);
                    var iframe=document.getElementById("diviframeofftype");
                    iframe.style.visibility="visible";
                   // iframe.focus();
                   // alert(navigator.appName);
                   // alert(navigator.appName.indexOf('Microsoft'));
                   
                  
                    if(navigator.appName.indexOf("Microsoft")!=-1)
                        iframe.innerHTML=req.responseText;
                    else
                        iframe.innerText=req.responseText;
                       
                   /*
                  document.frmValidationSummaryRep.cmbcircle.disabled=false;
                   var iframe=document.getElementById("diviframecircle");
                            iframe.style.visibility='hidden';
                   var iframe=document.getElementById("diviframedivision");
                            iframe.style.visibility='hidden';
                   */
                   offtypeflag=true;
                 /* }
            }
        }
        req.send(null);*/
    }
    


function verify()
{

var levelid=document.getElementById("hidlevelid").value;

//return true;
}


function getAudit(aud)
{
     
        
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
                                 if(auditflag==true && aud=='null')
                                {
                                  //     alert("inside 2");
                                         var iframe=document.getElementById("diviframeaudit");
                                            iframe.style.visibility='visible';
                                          iframe.focus();
                                           //  return;
                                }
                                region=region.substring(0,region.length-1);
                               var url="../../../../../../ListAllScopeofOffices?OLevel=audit&regions="+region;
                          
                                var req=getTransport();
                                req.open("GET",url,false);        
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
                                            if(auditflag==true && aud=='null')
                                            {
                                         //   alert("see these");
                                            var iframe=document.getElementById("diviframeaudit");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                            
                                            if(navigator.appName.indexOf('Microsoft')!=-1)
                                              iframe.innerHTML=req.responseText;
                                              else
                                              iframe.innerText=req.responseText;
                                            }
                                             iframe.innerHTML=req.responseText;
                                             
                                         //    alert(iframe.innerHTML);
                                             /*
                                             if(navigator.appName.indexOf('Microsoft')!=-1)
                                              iframe.innerHTML=req.responseText;
                                              else
                                              iframe.innerText=req.responseText;
                                              */
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
                                document.frmValidationSummaryRep.cmbaudit.focus();
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
                                               
                                                 var iframe=document.getElementById("diviframeaudit");
                                                    iframe.style.visibility='visible';
                                                  iframe.focus();
                                               // return;
                                        }
                                       // region=region.substring(0,region.length-1);
                                       // var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                        
                                        var url="../../../../../../ListAllScopeofOffices?OLevel=audit&regions="+region;
                                        
                                      // document.frmValidationSummaryRep.cmbregion.focus();
                                        var req=getTransport();
                                        req.open("GET",url,false);        
                                        req.onreadystatechange=function()
                                        {
                                            //requesthandle(req);
                                            if(req.readyState==4)
                                            { 
                                                  if(req.status==200)
                                                  {  
                                                   var iframe=document.getElementById("diviframeaudit");
                                                    // if((type=='CL' || type=='DN') && cir=='null' )
                                                    if(navigator.appName.indexOf('Microsoft')!=-1)
                                                       iframe.innerHTML=req.responseText;
                                                         else
                                                       iframe.innerText=req.responseText;
                                                    if(aud=='null')
                                                    {
                                                    
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                    
                                                    }
                                                     iframe.innerHTML=req.responseText;
                                                     
                                            //         alert(iframe.innerHTML);
                                                     /*
                                                     if(navigator.appName.indexOf('Microsoft')!=-1)
                                                       iframe.innerHTML=req.responseText;
                                                         else
                                                       iframe.innerText=req.responseText;
                                                       */
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
                                document.frmValidationSummaryRep.cmbaudit.focus();
                                alert('Please Select a Region');
                            }

            
            
            
            
            }
            
      //}
      
   
}

/*function getLab(lb)
    {
        
        alert('lab');
       var region="";
       var circle="";
       if(document.frmValidationSummaryRep.chkregion.length)
              {
                         for(i=0;i<document.frmValidationSummaryRep.chkregion.length;i++)
                           {
                             
                                    if(document.frmValidationSummaryRep.chkregion[i].checked==true)
                                            region= region+document.frmValidationSummaryRep.chkregion[i].value +",";
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
                               
                               alert(circle);
                                 if(labflag==true && lb=='null')
                                {
                                   alert("lll");
                                       
                                         var iframe=document.getElementById("diviframelab");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                      //return;
                                }
                                circle=circle.substring(0,circle.length-1);
                                region=region.substring(0,region.length-1);
                                alert("circle:"+circle+"region"+region);
                               // var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=division&circles="+circle;
                              
                              var url="../../../../../../ListAllScopeofOffices?OLevel=lab&circles="+circle+"&regions="+region;
                              alert(url);
                              
                              // document.frmValidationSummaryRep.cmbregion.focus();
                                var req=getTransport();
                                req.open("GET",url,false);        
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
                                            
                                             // alert(req.responseText);
                                            // iframe.innerHTML=req.responseText;
                                             
                                           labflag=true;  
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
                                    
                           
                                         if(labflag==true && lb=='null')
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
                                      var url="../../../../../../ListAllScopeofOffices?OLevel=lab&circles="+circle;
                                       alert(url);
                                      // document.frmValidationSummaryRep.cmbregion.focus();
                                        var req=getTransport();
                                        req.open("GET",url,false);        
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
                                                    
                                                     //iframe.innerHTML=req.responseText;
                                                     
                                                   labflag=true;  
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
   
}*/


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
                              
                              var url="../../../../../../ListAllScopeofOffices?OLevel=lab&circles="+circle+"&regions="+region;
                             //alert(url);
                              
                              // document.frmValidationSummaryRep.cmbregion.focus();
                                var req=getTransport();
                                req.open("GET",url,false);        
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
                                      var url="../../../../../../ListAllScopeofOffices?OLevel=lab&circles="+circle;
                                      //alert(url);
                                      // document.frmValidationSummaryRep.cmbregion.focus();
                                        var req=getTransport();
                                        req.open("GET",url,false);        
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

function fnchange()
{
  var divaudit=document.getElementById("divaud");
  divaudit.style.display="block";
   var divlab=document.getElementById("divlb");
    divlab.style.display="none";
}

function fnchange1()
{
 var divlab=document.getElementById("divlb");
    divlab.style.display="block";
    var divaudit=document.getElementById("divaud");
  divaudit.style.display="none";
}


function fnhirecy()
{
    if(document.frmValidationSummaryRep.rad_off[1].checked)
    {
        if(document.frmValidationSummaryRep.allhier.checked==true)
        {
            document.frmValidationSummaryRep.audi.checked=true;
            document.frmValidationSummaryRep.lab.checked=false;
        }
         else if(document.frmValidationSummaryRep.allhier.checked==false)
        {
            document.frmValidationSummaryRep.audi.checked=false;
            document.frmValidationSummaryRep.lab.checked=false;
        }
    }
    if(document.frmValidationSummaryRep.rad_off[2].checked)
    {
        if(document.frmValidationSummaryRep.allhier.checked==true)
        {
            document.frmValidationSummaryRep.lab.checked=true;
            document.frmValidationSummaryRep.audi.checked=false;
        }
        else if(document.frmValidationSummaryRep.allhier.checked==false)
        {
            document.frmValidationSummaryRep.lab.checked=false;
            document.frmValidationSummaryRep.audi.checked=false;
        }
    }
}