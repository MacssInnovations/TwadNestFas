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
            
    document.frmEmployeeDetailRep.optofficelevel[0].checked=true;
    var offlevel=document.getElementById("trofficelevel");
    offlevel.style.display="none";
}

function showofficelevel()
{
    document.frmEmployeeDetailRep.optofficelevel[1].checked=true;
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
    document.frmEmployeeDetailRep.optoffice[0].checked=true;
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="none";
}

function showoffice()
{
   
    document.frmEmployeeDetailRep.optoffice[1].checked=true;
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //alert('hai');
    var type=document.frmEmployeeDetailRep.cmbolevel.options[document.frmEmployeeDetailRep.cmbolevel.selectedIndex].value;
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
    document.frmEmployeeDetailRep.cmbcircle.disabled=true;
    document.frmEmployeeDetailRep.cmbdivision.disabled=true;
    
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
        var type=document.frmEmployeeDetailRep.cmbolevel.options[document.frmEmployeeDetailRep.cmbolevel.selectedIndex].value;
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
        var type=document.frmEmployeeDetailRep.cmbolevel.options[document.frmEmployeeDetailRep.cmbolevel.selectedIndex].value;
    
         var url="../../../../../../ListofEmpValidationSummaryHierServ.rep?OLevel=region" ;
     // alert(url);
                
        document.frmEmployeeDetailRep.cmbregion.focus();
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
                       
                   
                  document.frmEmployeeDetailRep.cmbcircle.disabled=false;
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
        var type=document.frmEmployeeDetailRep.cmbolevel.options[document.frmEmployeeDetailRep.cmbolevel.selectedIndex].value;
    
      if(document.frmEmployeeDetailRep.chkregion)
      {
               if(document.frmEmployeeDetailRep.chkregion.length)
              {
             
                            for(i=0;i<document.frmEmployeeDetailRep.chkregion.length;i++)
                            {
                                    if(document.frmEmployeeDetailRep.chkregion[i].checked==true)
                                            region= region+document.frmEmployeeDetailRep.chkregion[i].value +",";
                                    
                            }
                            
                            if(region!="")
                            {
                                 if(circleflag==true && cir=='null')
                                {
                                       
                                         var iframe=document.getElementById("diviframecircle");
                                            iframe.style.visibility='visible';
                                          iframe.focus();
                                           document.frmEmployeeDetailRep.cmbdivision.disabled=false;
                                        return;
                                }
                                region=region.substring(0,region.length-1);
                                var url="../../../../../../ListofEmpValidationSummaryHierServ.rep?OLevel=circle&regions="+region;
                              // document.frmEmployeeDetailRep.cmbregion.focus();
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
                                            
                                            document.frmEmployeeDetailRep.cmbdivision.disabled=false;
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
                                document.frmEmployeeDetailRep.cmbdivision.disabled=false;
                                document.frmEmployeeDetailRep.cmbcircle.focus();
                                alert('Please Select a Region');
                            }
            }
            else
            {
            
                             if(document.frmEmployeeDetailRep.chkregion.checked==true)
                             {
                                            region= document.frmEmployeeDetailRep.chkregion.value ;
                                    
                                    
                                         if(circleflag==true && cir=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframecircle");
                                                    iframe.style.visibility='visible';
                                                  iframe.focus();
                                                   document.frmEmployeeDetailRep.cmbdivision.disabled=false;
                                                return;
                                        }
                                       // region=region.substring(0,region.length-1);
                                        var url="../../../../../../ListofEmpValidationSummaryHierServ.rep?OLevel=circle&regions="+region;
                                      // document.frmEmployeeDetailRep.cmbregion.focus();
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
                                                    
                                                    document.frmEmployeeDetailRep.cmbdivision.disabled=false;
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
                                document.frmEmployeeDetailRep.cmbdivision.disabled=false;
                                document.frmEmployeeDetailRep.cmbcircle.focus();
                                alert('Please Select a Region');
                            }

            
            
            
            
            }
            
      }
   
}


function getDivision(div)
    {
        
       // alert('division');
       var circle="";
      if(document.frmEmployeeDetailRep.chkcircle)
      {
      
            if(document.frmEmployeeDetailRep.chkcircle.length)
            {
            
                            for(i=0;i<document.frmEmployeeDetailRep.chkcircle.length;i++)
                            {
                                    if(document.frmEmployeeDetailRep.chkcircle[i].checked==true)
                                            circle= circle+document.frmEmployeeDetailRep.chkcircle[i].value +",";
                                    
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
                                var url="../../../../../../ListofEmpValidationSummaryHierServ.rep?OLevel=division&circles="+circle;
                              // document.frmEmployeeDetailRep.cmbregion.focus();
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
                                            var type=document.frmEmployeeDetailRep.cmbolevel.options[document.frmEmployeeDetailRep.cmbolevel.selectedIndex].value;
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
                                document.frmEmployeeDetailRep.cmbdivision.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                }
                else
                {
                
                             if(document.frmEmployeeDetailRep.chkcircle.checked==true)
                             {
                                            circle= document.frmEmployeeDetailRep.chkcircle.value ;
                                    
                           
                                         if(divisionflag==true && div=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframedivision");
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                return;
                                        }
                                       // circle=circle.substring(0,circle.length-1);
                                        var url="../../../../../../ListofEmpValidationSummaryHierServ.rep?OLevel=division&circles="+circle;
                                      // document.frmEmployeeDetailRep.cmbregion.focus();
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
                                                    var type=document.frmEmployeeDetailRep.cmbolevel.options[document.frmEmployeeDetailRep.cmbolevel.selectedIndex].value;
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
                                document.frmEmployeeDetailRep.cmbdivision.focus();
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

   if(document.frmEmployeeDetailRep.chkregion)
      {
      
            
            for(i=0;i<document.frmEmployeeDetailRep.chkregion.length;i++)
            {
                    document.frmEmployeeDetailRep.chkregion[i].checked=true;
                    
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
    if(document.frmEmployeeDetailRep.chkcircle)
      {
      
            for(i=0;i<document.frmEmployeeDetailRep.chkcircle.length;i++)
            {
                    document.frmEmployeeDetailRep.chkcircle[i].checked=true;
                    
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
    if(document.frmEmployeeDetailRep.chkdivision)
      {
      
            for(i=0;i<document.frmEmployeeDetailRep.chkdivision.length;i++)
            {
                    document.frmEmployeeDetailRep.chkdivision[i].checked=true;
                    
            }
        }
}

function regiononchange()
{
     var type=document.frmEmployeeDetailRep.cmbolevel.options[document.frmEmployeeDetailRep.cmbolevel.selectedIndex].value;
   circlefalg=false;
    if(type=='CL' || type=='DN')
    {
        getCircle('circle');
    }

}

function circleonchange()
{
     var type=document.frmEmployeeDetailRep.cmbolevel.options[document.frmEmployeeDetailRep.cmbolevel.selectedIndex].value;
    divisionfalg=false;
    if(type=='DN')
    {
    getDivision('division');
    }

}



function hidedisignation()
{
    document.frmEmployeeDetailRep.optdesignation[0].checked=true;
   var offlevel=document.getElementById("trsergroup");
    offlevel.style.display="none";
    
      var offlevel=document.getElementById("divdes");
    offlevel.style.visibility="hidden";
     var offlevel=document.getElementById("cmbdes");
    offlevel.style.visibility="hidden";
    document.frmEmployeeDetailRep.cmbsgroup.value="0";
}

function showdesignation()
{
   document.frmEmployeeDetailRep.optdesignation[1].checked=true;
    var offlevel=document.getElementById("trsergroup");
    offlevel.style.display="block";
}

function getDesignation()
    {
        var type=document.frmEmployeeDetailRep.cmbsgroup.options[document.frmEmployeeDetailRep.cmbsgroup.selectedIndex].value;
        if(type!=0)
        {
            var din=document.getElementById("divdes");
            din.style.visibility="visible";
            document.frmEmployeeDetailRep.cmbdes.style.visibility="visible";
            loadOfficesByType(type);
        }
        else
        {
             var din=document.getElementById("divdes");
            din.style.visibility="hidden";
            document.frmEmployeeDetailRep.cmbdes.style.visibility="hidden";
        }
    }
    
function loadOfficesByType(type)
    {
        //alert(type);
        var type=document.frmEmployeeDetailRep.cmbsgroup.options[document.frmEmployeeDetailRep.cmbsgroup.selectedIndex].value;
        //startwaiting(document.frmEmployeeDetailRep) ;
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
                
                // stopwaiting(document.frmEmployeeDetailRep);
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
        var url="../../../../../../ListofEmpValidationSummaryHierServ.rep?OLevel=submit" ;
        
        //office validation
        if(document.frmEmployeeDetailRep.optofficelevel[0].checked==true)
        {
                url=url+"&offlevel=all";
                document.frmEmployeeDetailRep.offlevel.value='all';
        }
        else
        {
                url=url+"&offlevel=specific";
                 document.frmEmployeeDetailRep.offlevel.value='specific';
                
                // select the office 
                  var type=document.frmEmployeeDetailRep.cmbolevel.options[document.frmEmployeeDetailRep.cmbolevel.selectedIndex].value;
                 if(type=="")
                 {
                    alert('Select the Office Level');
                    document.frmEmployeeDetailRep.cmbolevel.focus();
                    return;
                }
                else
                {
                    url=url+"&office="+type;
                    document.frmEmployeeDetailRep.office.value=type;
                    
                    if(document.frmEmployeeDetailRep.optoffice[0].checked==true)
                    {
                            url=url+"&officetype=all";
                             document.frmEmployeeDetailRep.officetype.value='all';
                    }
                    else
                    {
                            url=url+"&officetype=specific";
                             document.frmEmployeeDetailRep.officetype.value='specific';
                            if(type=='RN')
                            {
                            ////////
                            var region="";
                            if(document.frmEmployeeDetailRep.chkregion)
                              {
                              
                                        if(document.frmEmployeeDetailRep.chkregion.length)
                                        {
                                                  for(i=0;i<document.frmEmployeeDetailRep.chkregion.length;i++)
                                                    {
                                                            if(document.frmEmployeeDetailRep.chkregion[i].checked==true)
                                                                    region= region+document.frmEmployeeDetailRep.chkregion[i].value +",";
                                                            
                                                    }
                                                    if(region!="")
                                                    {
                                                        region=region.substring(0,region.length-1);
                                                         url=url+"&officeselected="+region;
                                                         document.frmEmployeeDetailRep.officeselected.value=region;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Region.');
                                                            document.frmEmployeeDetailRep.cmbregion[0].focus(); 
                                                            return;
                                                    }
                                            
                                        }
                                        else{
                                           
                                                if(document.frmEmployeeDetailRep.chkregion.checked==true)
                                                {
                                                            region= document.frmEmployeeDetailRep.chkregion.value ;
                                                            document.frmEmployeeDetailRep.officeselected.value=region;
                                                }
                                                 else
                                                    {
                                                           alert('Select the Region..');
                                                            document.frmEmployeeDetailRep.cmbregion.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                 else
                                {
                                       alert('Select the Region...');
                                        document.frmEmployeeDetailRep.cmbregion.focus(); 
                                        return ;
                                }    
                            ////
                            
                            }
                            
                            else if(type=='CL')
                            {
                            
                            ////////
                            var circle="";
                            if(document.frmEmployeeDetailRep.chkcircle)
                              {
                              
                                       if(document.frmEmployeeDetailRep.chkcircle.length )
                                        {
                                                
                                                    for(i=0;i<document.frmEmployeeDetailRep.chkcircle.length;i++)
                                                    {
                                                            if(document.frmEmployeeDetailRep.chkcircle[i].checked==true)
                                                                    circle= circle+document.frmEmployeeDetailRep.chkcircle[i].value +",";
                                                            
                                                    }
                                                    if(circle!="")
                                                    {
                                                        circle=circle.substring(0,circle.length-1);
                                                         url=url+"&officeselected="+circle;
                                                         document.frmEmployeeDetailRep.officeselected.value=circle;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.frmEmployeeDetailRep.cmbcircle.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.frmEmployeeDetailRep.chkcircle.checked==true)
                                                {
                                                            circle= document.frmEmployeeDetailRep.chkcircle.value ;
                                                            document.frmEmployeeDetailRep.officeselected.value=circle;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Circle..');
                                                            document.frmEmployeeDetailRep.cmbcircle.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Circle...');
                                       try{
                                        document.frmEmployeeDetailRep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.frmEmployeeDetailRep.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                            else  if(type=='DN')
                            {
                           
                            ////////
                            var division="";
                            if(document.frmEmployeeDetailRep.chkdivision)
                              {
                             //alert(document.frmEmployeeDetailRep.chkregion.length);
                                     if(document.frmEmployeeDetailRep.chkdivision.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.frmEmployeeDetailRep.chkdivision.length;i++)
                                                {
                                                        if(document.frmEmployeeDetailRep.chkdivision[i].checked==true)
                                                                division= division+document.frmEmployeeDetailRep.chkdivision[i].value +",";
                                                        
                                                }
                                                
                                                if(division!="")
                                                {
                                                    division=division.substring(0,division.length-1);
                                                     url=url+"&officeselected="+division;
                                                     document.frmEmployeeDetailRep.officeselected.value=division;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the Division.');
                                                        document.frmEmployeeDetailRep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.frmEmployeeDetailRep.chkdivision.checked==true)
                                                {
                                                                division= document.frmEmployeeDetailRep.chkdivision.value;
                                                                    //division=division.substring(0,division.length-1);
                                                                 url=url+"&officeselected="+division;
                                                                 document.frmEmployeeDetailRep.officeselected.value=division;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the Division..');
                                                        document.frmEmployeeDetailRep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the Division...');
                                       try{
                                        document.frmEmployeeDetailRep.cmbdivision.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.frmEmployeeDetailRep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.frmEmployeeDetailRep.cmbregion.focus(); 
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
        if(document.frmEmployeeDetailRep.optoutputtype[0].checked==true)
        {
                url=url+"&outputtype=pdf";
                document.frmEmployeeDetailRep.outputtype.value='pdf';
        }
        else if(document.frmEmployeeDetailRep.optoutputtype[1].checked==true)
        {
                url=url+"&outputtype=excel";
                document.frmEmployeeDetailRep.outputtype.value='excel';
        }
        else if(document.frmEmployeeDetailRep.optoutputtype[2].checked==true)
        {
                url=url+"&outputtype=html";
                 document.frmEmployeeDetailRep.outputtype.value='html';
        }
        
   
    
    document.frmEmployeeDetailRep.action="../../../../../../ListofEmpValidationSummaryHierServ.rep";
    document.frmEmployeeDetailRep.submit();
   /* var req=getTransport();
    req.open("POST",url,false);  
    req.send(null);*/

}
    

    