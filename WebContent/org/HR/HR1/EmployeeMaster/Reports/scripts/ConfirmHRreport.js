var regionflag=false;
//var regionflag=true;
var circleflag=true;
var divisionflag=true;
var offtypeflag=true;

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


function officevisible(r,c,d,o)
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
    
    var rn=document.getElementById("cmbregion");
    rn.style.display=r;
    var cl=document.getElementById("cmbcircle");
    cl.style.display=c;
    var dn=document.getElementById("cmbdivision");
    dn.style.display=d;
    var ot=document.getElementById("cmbofftype");
    ot.style.display=o;
}




function selectoption2()
{
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    document.getElementById("divallhier").style.display='block';
    
    
    if(document.frmValidationSummaryRep.rad_off[0].checked==true)
    {
       var offlevel=document.getElementById("trofficeselection");
       offlevel.style.display="none";
    }

  if(document.frmValidationSummaryRep.rad_off[1].checked==true)
  {
 //  alert('document.frmValidationSummaryRep.rad_off[1].checked');
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
    
     officevisible('block','none','none','none');
    // getRegion();
  }
  
  else if(document.frmValidationSummaryRep.rad_off[2].checked==true)
  {
   // alert(document.frmValidationSummaryRep.rad_off[2].checked);
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
     officevisible('block','block','none','none');
  }
  
  else if(document.frmValidationSummaryRep.rad_off[3].checked==true)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('block','block','block','block','block');
  }
  
  
  document.frmValidationSummaryRep.cmbcircle.disabled=true;
   // document.frmValidationSummaryRep.cmbofftype.disabled=true;
    document.frmValidationSummaryRep.cmbdivision.disabled=true;
    
    var iframe=document.getElementById("diviframeregion");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframecircle");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframeofftype");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframedivision");
    iframe.style.visibility='hidden';
    
    
    regionflag=true;
    circleflag=true;
    offtypeflag=true;
    divisionflag=true;
   

}


var s=0;
var hier=true;
var level=true;
var city=true;
var  other=true;
    
  
function getRegion()
    {
      //  alert(regionflag);
              
        
        if(regionflag==true)
        {
               
                 var iframe=document.getElementById("diviframeregion");
                 iframe.style.visibility='visible';
                 iframe.focus();
               // return;
        }
       
    
         
         var url="../../../../../../ConfirmHRreport.av?OLevel=region" ;
        //alert(url);
                
        document.frmValidationSummaryRep.cmbregion.focus();
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
                 
                 
                  
                    var iframe=document.getElementById("diviframeregion");
                    iframe.style.visibility='visible';
                    iframe.focus();
                   // alert(navigator.appName);
                   // alert(navigator.appName.indexOf('Microsoft'));
                   
                  
                    if(navigator.appName.indexOf('Microsoft')!=-1)
                        iframe.innerHTML=req.responseText;
                    else
                        iframe.innerText=req.responseText;
                       
                   
                  document.frmValidationSummaryRep.cmbcircle.disabled=false;
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
                                      //  return;
                                }
                                region=region.substring(0,region.length-1);
                               // alert("region1..."+region);
                                //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                               
                               
                               // alert(document.getElementById("txtmonth").selectedIndex.text);
                              
                                var url="../../../../../../ConfirmHRreport.av?OLevel=circle&regions="+region;
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
                                               // return;
                                        }
                                       // region=region.substring(0,region.length-1);
                                       // var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                       
                               
                                        var url="../../../../../../ConfirmHRreport.av?OLevel=circle&regions="+region;
                                        
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
      
            if(document.frmValidationSummaryRep.chkcircle.length)
            {
            
                            for(i=0;i<document.frmValidationSummaryRep.chkcircle.length;i++)
                            {
                                    if(document.frmValidationSummaryRep.chkcircle[i].checked==true)
                                            circle= circle+document.frmValidationSummaryRep.chkcircle[i].value +",";
                                  
                            }
                            if(circle!="")
                            {
                               
                               
                                 if(divisionflag==true && div=='null')
                                {
                                   
                                       
                                         var iframe=document.getElementById("diviframedivision");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                       // return;
                                }
                                circle=circle.substring(0,circle.length-1);
                                offtype=offtype.substring(0,offtype.length-1);
                                region=region.substring(0,region.length-1);
                               // var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=division&circles="+circle;
                              
                              var url="../../../../../../ListAllSanctionedStrength?OLevel=division&circles="+circle+"&offtype="+offtype+"&regions="+region;
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
                                var iframe=document.getElementById("diviframedivision");
                                iframe.style.visibility='hidden';
                                document.frmValidationSummaryRep.cmbdivision.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                }
                else
                {
                
                             if(document.frmValidationSummaryRep.chkcircle.checked==true)
                             {
                                            circle= document.frmValidationSummaryRep.chkcircle.value ;
                                    
                           
                                         if(divisionflag==true && div=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframedivision");
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                              //  return;
                                        }
                                       // circle=circle.substring(0,circle.length-1);
                                      //  var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=division&circles="+circle;
                                      offtype=offtype.substring(0,offtype.length-1);
                                      region=region.substring(0,region.length-1);
                                      //alert(region);
                                      var url="../../../../../../ListAllSanctionedStrength?OLevel=division&circles="+circle+"&offtype="+offtype;
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
                            else
                            {
                                var iframe=document.getElementById("diviframedivision");
                                iframe.style.visibility='hidden';
                                document.frmValidationSummaryRep.cmbdivision.focus();
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
            // regiononchange();
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

function oftypeonchange()
{

     if(document.frmValidationSummaryRep.rad_off[3].checked)
     {
       getDivision('division');
     }
     
}

function fnoption()
{
 var din=document.getElementById("option");
        din.style.display="block";
}


function frmsubmit()
{
  document.frmValidationSummaryRep.tmonth.value=document.frmValidationSummaryRep.txtmonth.options[document.frmValidationSummaryRep.txtmonth.selectedIndex].text;
  document.frmValidationSummaryRep.tupdated.value=document.frmValidationSummaryRep.updated.value;
  //options[document.frmValidationSummaryRep.updated.selectedIndex].text;
  var nflag=nullCheck();
  if(nflag==true)
 {
    //alert('inside submit');
        var url="../../../../../../ConfirmHRreport.av?OLevel=submit";
        
                             
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
        
   
    
    document.frmValidationSummaryRep.action="../../../../../../ConfirmHRreport.av";
    document.frmValidationSummaryRep.submit();
   /* var req=getTransport();
    req.open("POST",url,false);  
    req.send(null);*/
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
         var url="../../../../../../ConfirmHRreport.av?OLevel=offtype" ;
         //alert(url);
                
        document.frmValidationSummaryRep.cmbofftype.focus();
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
                    var iframe=document.getElementById("diviframeofftype");
                    iframe.style.visibility='visible';
                    iframe.focus();
                   // alert(navigator.appName);
                   // alert(navigator.appName.indexOf('Microsoft'));
                   
                  
                    if(navigator.appName.indexOf('Microsoft')!=-1)
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
                  }
            }
        }
        req.send(null);
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
function monthcall()
{
var month_arry= new Array(5)
            month_arry[1]="January";
            month_arry[2]="February";
            month_arry[3]="March";
            month_arry[4]="April";
            month_arry[5]="May";
            month_arry[6]="June";
            month_arry[7]="July";
            month_arry[8]="August";
            month_arry[9]="September";
            month_arry[10]="October";
            month_arry[11]="November";
            month_arry[12]="December";
var asd=new Date();
var year1=asd.getYear();

if(year1 < 1900)
year1 += 1900;
    // alert("date1"+date1+"/"+month1+"/"+year1);
    if(document.frmValidationSummaryRep.txtyear.value.length!=0)
    {
        var year=document.frmValidationSummaryRep.txtyear.value;
    
    if(year>1970 && year<=year1)
    {
        if( year==year1)
        {
         mon=asd.getMonth()+1;
        }
        else
        mon=12;
        var id=document.getElementById("txtmonth");
                try{
                    id.innerHTML="";
                }
                catch(e)
                {
                    id.innerText="";
                }
                    option_var=document.createElement("OPTION");
                    option_var.text="--Select the Month--";
                try
                {
                   id.add(option_var);
                
                }
                catch(Exception)
                {
                     id.add(option_var,null);
                }
        for(var i=1;i<=mon;i++)
        {
            var option_var=document.createElement("OPTION");
            option_var.text=month_arry[i];
            option_var.value=i;
            //alert(mon);
            
            try
            {
            id.add(option_var);
            
            }
            catch(Exception)
            {
            id.add(option_var,null);
            }
            }
  }
        else
        {
        alert("Enter the Valid Year");
        document.frmValidationSummaryRep.txtyear.focus();
        }
       
    }  
} 
   
function nullCheck()
{
 if(document.frmValidationSummaryRep.txtyear.value=="")
   {
      alert("Please Enter the Year");
      document.frmValidationSummaryRep.txtyear.focus();
      return false;
   }
   
   
   if(document.frmValidationSummaryRep.txtmonth.value=="")
   {
      alert("Select the Month");
      document.frmValidationSummaryRep.txtmonth.focus();
      return false;
   }
    if(document.frmValidationSummaryRep.updated.value=="")
   {
      alert("Select the Option for Report");
      document.frmValidationSummaryRep.updated.focus();
      return false;
   }
   
    
  return true;
}



