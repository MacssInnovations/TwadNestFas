//alert('detail');

var regionflag=false;
var circleflag=true;
var divisionflag=true;
var subdivisionflag=true;
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
    officevisible('none','none','none','none'); 
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




function officevisible(r,c,d,sd)
{
    var rn=document.getElementById("divregion");
    rn.style.display=r;
    var cl=document.getElementById("divcircle");
    cl.style.display=c;
    var dn=document.getElementById("divdivision");
    dn.style.display=d;
    
    var sdn=document.getElementById("divsubdivision");
    sdn.style.display=sd;
    
    var rn=document.getElementById("cmbregion");
    rn.style.display=r;
    var cl=document.getElementById("cmbcircle");
    cl.style.display=c;
    var dn=document.getElementById("cmbdivision");
    dn.style.display=d;
    
    var sdn=document.getElementById("cmbsubdivision");
    sdn.style.display=sd;
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
        officevisible('block','none','none','none');
        
    }
    else if(type=='CL')     {
       officevisible('block','block','none','none');
    }

    else if(type=='DN')     {
       officevisible('block','block','block','none');
    }
    else if(type=='SD') {
        officevisible('block','block','block','block');
    }
    
    
    document.frmEmployeeDetailRep.cmbcircle.disabled=true;
    document.frmEmployeeDetailRep.cmbdivision.disabled=true;
    document.frmEmployeeDetailRep.cmbsubdivision.disabled=true;
    
    var iframe=document.getElementById("diviframeregion");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframecircle");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframedivision");
    iframe.style.visibility='hidden';
    
    var iframe=document.getElementById("diviframesubdivision");
    iframe.style.visibility='hidden';
    
    regionflag=false;
    circleflag=true;
    divisionflag=true;
    subdivisionflag=true;
    
    
    
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
       officevisible('none','none','none','none'); 
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
      
      if(type=='DN')
      {
        var agg=document.getElementById("aggreate");
       agg.style.display="block";
      }
      else
      {
        var agg=document.getElementById("aggreate");
        agg.style.display="none";
        document.frmEmployeeDetailRep.aggid.checked=false;
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
    
         var url="../../../../../../OfficeStaffStrengthServlet.con?OLevel=region" ;
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
                    //alert(req.responseText);
                    
                 
                 // document.frames("iframregion").document.body.innerHTML=req.responseText;
                  //(document.frames("iframregion").document.body.innerText);
                    var iframe=document.getElementById("diviframeregion");
                    iframe.style.visibility='visible';
                    iframe.focus();
                    //alert('hai');
                   // alert(navigator.appName);
                   // alert(navigator.appName.indexOf('Microsoft'));
                    if(navigator.appName.indexOf('Microsoft')!=-1)
                        iframe.innerHTML=req.responseText;
                    else
                        iframe.innerText=req.responseText;
                       
                   //alert('hai');
                  document.frmEmployeeDetailRep.cmbcircle.disabled=false;
                   var iframe=document.getElementById("diviframecircle");
                            iframe.style.visibility='hidden';
                   var iframe=document.getElementById("diviframedivision");
                            iframe.style.visibility='hidden';
                   var iframe=document.getElementById("diviframesubdivision");
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
                var url="../../../../../../OfficeStaffStrengthServlet.con?OLevel=circle&regions="+region;
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


function getDivision(div)
    {
        
        
       var circle="";
      if(document.frmEmployeeDetailRep.chkcircle)
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
                var url="../../../../../../OfficeStaffStrengthServlet.con?OLevel=division&circles="+circle;
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
                            if((type=='DN' || type=='SD') && div=='null')
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
                document.frmEmployeeDetailRep.cmbsubdivision.disabled=false;
                document.frmEmployeeDetailRep.cmbdivision.focus();
                alert('Please Select a Circle');
            }
           // alert("circles:"+circle);
            
      }
   
}

//B Changed date 12-oct-2006 

function getSubDivision(div)
    {
        
        
       var division="";
      if(document.frmEmployeeDetailRep.chkdivision)
      {
      
            for(i=0;i<document.frmEmployeeDetailRep.chkdivision.length;i++)
            {
                    if(document.frmEmployeeDetailRep.chkdivision[i].checked==true)
                            division= division+document.frmEmployeeDetailRep.chkdivision[i].value +",";
                    
            }
            if(division!="")
            {
               
                //alert(circle);
               // alert(divisionflag);
                 if(subdivisionflag==true && div=='null')
                {
                       
                         var iframe=document.getElementById("diviframesubdivision");
                            iframe.style.visibility='visible';
                            iframe.focus();
                            document.frmEmployeeDetailRep.cmbsubdivision.disabled=false;
                        return;
                }
                division=division.substring(0,division.length-1);
                var url="../../../../../../OfficeStaffStrengthServlet.con?OLevel=subdivision&division="+division;
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
                          
                            var iframe=document.getElementById("diviframesubdivision");
                            document.frmEmployeeDetailRep.cmbsubdivision.disabled=false;
                            var type=document.frmEmployeeDetailRep.cmbolevel.options[document.frmEmployeeDetailRep.cmbolevel.selectedIndex].value;
                            if(type=='SD' && div=='null')
                            {
                            
                            iframe.style.visibility='visible';
                            iframe.focus();
                            }
                             iframe.innerHTML=req.responseText;
                             
                           subdivisionflag=true;  
                          }
                    }
                }
                req.send(null);
            }
            else
            {
                var iframe=document.getElementById("diviframesubdivision");
                iframe.style.visibility='hidden';
                document.frmEmployeeDetailRep.cmbdivision.focus();
                alert('Please Select a Division');
            }
           // alert("circles:"+circle);
            
      }
   
}

//////////////////////////////////////////////////



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
    
    var iframe=document.getElementById("divisubframedivision");
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
            divisiononchange();
        }
}


function subdivisionclose()
{
    var iframe=document.getElementById("diviframesubdivision");
    iframe.style.visibility='hidden';
    
    
}

function subdivisionselectAll()
{
    if(document.frmEmployeeDetailRep.chksubdivision)
    {
        for(i=0;i<document.frmEmployeeDetailRep.chksubdivision.length;i++)
        {
            document.frmEmployeeDetailRep.chksubdivision[i].checked=true; 
            
        }
        
     }
    
}
function regiononchange()
{
     var type=document.frmEmployeeDetailRep.cmbolevel.options[document.frmEmployeeDetailRep.cmbolevel.selectedIndex].value;
   circlefalg=false;
    if(type=='CL' || type=='DN' || type=='SD')
    {
        getCircle('circle');
    }

}

function circleonchange()
{
     var type=document.frmEmployeeDetailRep.cmbolevel.options[document.frmEmployeeDetailRep.cmbolevel.selectedIndex].value;
    divisionfalg=false;
    if(type=='DN'|| type=='SD')
    {
    getDivision('division');
    }

}

function divisiononchange()
{

    var type=document.frmEmployeeDetailRep.cmbolevel.options[document.frmEmployeeDetailRep.cmbolevel.selectedIndex].value;
    subdivisionfalg=false;
    if(type=='SD')
    {
        getSubDivision('subdivision');
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



function frmsubmit()
{
    //alert('submit');
        var url="../../../../../../OfficeStaffStrengthServlet.con?OLevel=submit" ;
        
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
                                           alert('Select the Region');
                                            document.frmEmployeeDetailRep.cmbregion.focus(); 
                                            return;
                                    }
                                }
                                 else
                                {
                                       alert('Select the Region');
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
                                           alert('Select the Circle');
                                            document.frmEmployeeDetailRep.cmbcircle.focus(); 
                                             return;
                                    }
                                }
                                else
                                {
                                       alert('Select the Circle');
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
                                           alert('Select the Division');
                                            document.frmEmployeeDetailRep.cmbdivision.focus(); 
                                             return;
                                    }
                                }
                                 else
                                {
                                       alert('Select the Division');
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
                            else  if(type=='SD')
                            {
                           
                            ////////
                            var division="";
                            if(document.frmEmployeeDetailRep.chksubdivision)
                              {
                             
                                    for(i=0;i<document.frmEmployeeDetailRep.chksubdivision.length;i++)
                                    {
                                            if(document.frmEmployeeDetailRep.chksubdivision[i].checked==true)
                                                    division= division+document.frmEmployeeDetailRep.chksubdivision[i].value +",";
                                            
                                    }
                                    
                                    if(division!="")
                                    {
                                        division=division.substring(0,division.length-1);
                                         url=url+"&officeselected="+division;
                                         document.frmEmployeeDetailRep.officeselected.value=division;
                                         
                                    }
                                    else
                                    {
                                           alert('Select the Sub Division');
                                            document.frmEmployeeDetailRep.cmbsubdivision.focus(); 
                                             return;
                                    }
                                }
                                 else
                                {
                                       alert('Select the SubDivision');
                                       try
                                       {
                                        document.frmEmployeeDetailRep.cmbsubdivsion.focus();
                                       }catch(e)
                                       {
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
        
        

    //alert(url);
    if((document.frmEmployeeDetailRep.cmbFinancialYear.value=="") ||(document.frmEmployeeDetailRep.cmbFinancialYear.value.length<=0)|| (document.frmEmployeeDetailRep.cmbFinancialYear.value==0))
            {
            alert("Please Select Financial Year");
            document.frmEmployeeDetailRep.cmbFinancialYear.focus();
            return false;
            
             }
    document.frmEmployeeDetailRep.action="../../../../../../OfficeStaffStrengthServlet.con";
    document.frmEmployeeDetailRep.submit();
   /* var req=getTransport();
    req.open("POST",url,false);  
    req.send(null);*/

}
    

function loadfyr()
{
         var cyr, cdt,cdt1;
 	cdt=new Date();
 	cyr=cdt.getFullYear();
 	cmn=cdt.getMonth();
        //alert("cdate"+cdt);
        //alert("cmonth"+cmn);
        //alert("cyear"+cyr);
        var cmbFinancialYear=document.getElementById("cmbFinancialYear");
        cyr=cyr+1
 	if (parseInt(cmn) <= 2)
        {
  
                document.frmEmployeeDetailRep.cmbFinancialYear.length=5;
                cmbFinancialYear.innerHTML="";
                var option=document.createElement("OPTION");
                option.text="--Select FinancialYear--";
                option.value=0;
                try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        } 
                for (var i = 0 ; i < 2; i++) 
                {
         
                  //document.frmEmployeeDetailRep.cmbFinancialYear.options[i].text=(cyr-2)+"-"+(cyr-1);
                  //document.frmEmployeeDetailRep.cmbFinancialYear.options[i].value=(cyr-2)+"-"+(cyr-1);
                  var id=(cyr-2)+"-"+(cyr-1);
                  var option=document.createElement("OPTION");
                  option.text=id;
                  option.value=id;
                  try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        } 
                  
                  cyr--;
                }
 	}
 	else 
 	{
           document.frmEmployeeDetailRep.cmbFinancialYear.length=5;
           cmbFinancialYear.innerHTML="";
           var option=document.createElement("OPTION");
           option.text="--Select FinancialYear--";
           option.value=0;
           try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        } 
            if(cmn>=11)
            {
                cyr=cyr+1;
                for (var i = 0 ; i < 2; i++) 
                {
                  //document.frmEmployeeDetailRep.cmbFinancialYear.options[i].text=(cyr-1)+"-"+(cyr);
                  //document.frmEmployeeDetailRep.cmbFinancialYear.options[i].value=(cyr-1)+"-"+(cyr);
                  var id=(cyr-1)+"-"+(cyr);
                  var option=document.createElement("OPTION");
                  option.text=id;
                  option.value=id;
                      try
                            {
                                cmbFinancialYear.add(option);
                            }catch(errorobject)
                            { 
                                cmbFinancialYear.add(option,null);
                            }
                  cyr--;
                }
            }
            else
            {
                 for (var i = 0 ; i < 1; i++) 
                {
                  //document.frmEmployeeDetailRep.cmbFinancialYear.options[i].text=(cyr-1)+"-"+(cyr);
                  //document.frmEmployeeDetailRep.cmbFinancialYear.options[i].value=(cyr-1)+"-"+(cyr);
                  var id=(cyr-1)+"-"+(cyr);
                  var option=document.createElement("OPTION");
                  option.text=id;
                  option.value=id;
                      try
                            {
                                cmbFinancialYear.add(option);
                            }catch(errorobject)
                            { 
                                cmbFinancialYear.add(option,null);
                            }
                  cyr--;
                }
            
            }
 	}
        
}