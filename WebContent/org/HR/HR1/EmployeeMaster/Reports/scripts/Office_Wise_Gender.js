

 function getxmlhttpObject()
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

function selectoption1()
{
  
  if(document.frmlist_service.optselect[0].checked)
  {
    var id=document.getElementById("divdest");
    id.style.display='inline';
    var id=document.getElementById("divcadre");
    id.style.display='none';
  }
  else
  {
    var id1=document.getElementById("divdest");
    id1.style.display='none';
    var id1=document.getElementById("divcadre");
    id1.style.display='inline';
  }
  //frmrolesubmit();
}

function getDesignation()
{
	
  var type=document.frmlist_service.cmbsgroup.options[document.frmlist_service.cmbsgroup.selectedIndex].value;
  
  if(type!=0)
  {
     loadDesigByType(type);
  }
  else
  {
    var des=document.getElementById("cmbDesignation");
    des.innerHTML='';
  }
  
}

function loadDesigByType(type)
{
  var type=document.frmlist_service.cmbsgroup.options[document.frmlist_service.cmbsgroup.selectedIndex].value;
  startwaiting(document.frmlist_service);
  
  var url="../../../../../../ListofEmp_YearExp_Serv?command=Desig&cmbsgroup="+type;
 // alert(url);
   //req=getTransport();
   
   xmlhttp=getxmlhttpObject();
   
  //req.open("GET",url,true);
  //req.onreadyStateChange=loadDesignation;
 //alert(req);
 // req.send(null);
  xmlhttp.open("GET",url,true);
  xmlhttp.onreadystatechange=loadDesignation;
  xmlhttp.send(null);  
}

function loadDesignation()
{
	
  if(xmlhttp.readyState==4)
  {
    if(xmlhttp.status==200)
    {
     
      var des=document.getElementById("cmbDesignation");
      var i=0;
      var response=xmlhttp.responseXML.getElementsByTagName("response")[0];
      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
      des.innerHTML="";
      
      stopwaiting(document.frmlist_service);
      
      if(flag=="failure")
      {
        alert('No Designation exists under this level');
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
         }
         catch(errorObject)
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

function checkgroup1()
{
  var type=document.frmlist_service.cmbsgroup.options[document.frmlist_service.cmbsgroup.selectedIndex].value;
  
  if(type==0)
  {
    alert('Select Service Group');
    document.frmlist_service.cmbsgroup.focus();
    return false;
  }
 
   return true;
}


function getCadre()
{
   var type=document.frmlist_service.cmbsgroup1.options[document.frmlist_service.cmbsgroup1.selectedIndex].value;
   //alert(type);
   if(type!=0)
   {
     loadCadre(type);
   }
   else
   {
     var des=document.getElementById("cmbCadre");
     des.innerHTML="";
   }
 
}

function loadCadre(type)
{
  var type=document.frmlist_service.cmbsgroup1.options[document.frmlist_service.cmbsgroup1.selectedIndex].value;
  startwaiting(document.frmlist_service);
  var url="../../../../../../ListofEmp_YearExp_Serv?command=Cadre&cmbsgroup="+type;
  
  
  
  xmlhttp=getxmlhttpObject();
    
  xmlhttp.open("GET",url,true);
  xmlhttp.onreadystatechange=servCadre;
  xmlhttp.send(null);  
  
    
  

}

function servCadre()
{
  if(xmlhttp.readyState==4)
  {
    if(xmlhttp.status==200)
    {
    
      var cad=document.getElementById("cmbCadre");
      var i=0;
      var response=xmlhttp.responseXML.getElementsByTagName("response")[0];
      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
      stopwaiting(document.frmlist_service);
      try
      {
      cad.innerHTML="";
      }
      catch(e)
      {
    	  
      }
      
      if(flag=="failure")
      {
        alert('No Cadre exists under this level');
      }
      else
      {
        var value=response.getElementsByTagName("option");
        var option=document.createElement("OPTION");
        option.text="--Select Cadre--";
        option.value="0";
        
        try
        {
          cad.add(option);
        }
        catch(errorObject)
        {
          cad.add(option,null);
        }
        
        for(var i=0;i<value.length;i++)
        {
          var tmpoption=value.item(i);
          var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
          var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
          
          
          var option=document.createElement("OPTION");
           option.text=name;
           option.value=id;
           
           try
           {
             cad.add(option);
           }
           catch(errorObject)
           {
             cad.add(option,null);
           }
        }
        
      }
      
    }
  }
}

function checkgroup2()
{
  var type=document.frmlist_service.cmbsgroup1.options[document.frmlist_service.cmbsgroup1.selectedIndex].value;
  
  if(type==0)
  {
   alert('Select Service Group');
   document.frmlist_service.cmbsgroup1.focus();
   return false;
  }
 return true;
}
var rcount=0;
var ccount=0;
var dcount=0;
function selectoption2()
{
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
//    document.getElementById("divallhier").style.display='block';
    var offlevel1=document.getElementById("trofficeselection1");
    offlevel1.style.display="block";
    
    if(document.frmlist_service.rad_off[0].checked==true)
    {
       var offlevel=document.getElementById("trofficeselection");
       offlevel.style.display="none";
       var offlevel1=document.getElementById("trofficeselection1");
       offlevel1.style.display="none";
    }

  if(document.frmlist_service.rad_off[1].checked==true)
  {
 //  alert('document.frmlist_service.rad_off[1].checked');
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    var offlevel1=document.getElementById("trofficeselection1");
    offlevel1.style.display="block";
    //document.getElementById("divallhier").style.display='block';
    
     officevisible('block','none','none','none');
  if( rcount>0){
	//  alert(document.frmlist_service.chkregion.length);
	     var i;
	     for(i=0;i<document.frmlist_service.chkregion.length;i++){
	    	if(document.frmlist_service.chkregion[i].checked==true)
	    	{	//alert(i);
	    		document.frmlist_service.chkregion[i].checked=false;
	    	}
	     }
	     rcount=0;
  }
    // getRegion();
  
    /* alert(document.frmlist_service.chkregion.length);
     var i;
     for(i=0;i<document.frmlist_service.chkregion.length;i++){
    	if(document.frmlist_service.chkregion[i].checked==true)
    		alert(i);
     }*/
    // 
  }
  
  else if(document.frmlist_service.rad_off[2].checked==true)
  {
   // alert(document.frmlist_service.rad_off[2].checked);
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    var offlevel1=document.getElementById("trofficeselection1");
    offlevel1.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
     officevisible('block','block','none','none');
     if( ccount>0){
    		//  alert(document.frmlist_service.chkregion.length);
    		     var i;
    		     for(i=0;i<document.frmlist_service.chkcircle.length;i++){
    		    	if(document.frmlist_service.chkcircle[i].checked==true)
    		    	{	//alert(i);
    		    		document.frmlist_service.chkcircle[i].checked=false;
    		    	}
    		     }
    		     ccount=0;
    	  }
  }
  
  else if(document.frmlist_service.rad_off[3].checked==true)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    var offlevel1=document.getElementById("trofficeselection1");
    offlevel1.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('block','block','block','block','block');
    if( dcount>0){
		//  alert(document.frmlist_service.chkregion.length);
		     var i;
		     for(i=0;i<document.frmlist_service.chkdivision.length;i++){
		    	if(document.frmlist_service.chkdivision[i].checked==true)
		    	{	//alert(i);
		    		document.frmlist_service.chkdivision[i].checked=false;
		    	}
		     }
		     dcount=0;
	  }
  }

  document.frmlist_service.cmbcircle.disabled=true;
   // document.frmlist_service.cmbofftype.disabled=true;
    document.frmlist_service.cmbdivision.disabled=true;
    
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

function getRegion()
{
 // alert(regionflag);
  
    if(regionflag==true)
    {
             var iframe=document.getElementById("diviframeregion");
             iframe.style.visibility='visible';
             iframe.focus();
           // return;
    }
 
     var url="../../../../../../ConfirmHRreport.av?OLevel=region" ;
     document.frmlist_service.cmbregion.focus();
    var req=getTransport();
    req.open("GET",url,true);        
    req.onreadystatechange=function()
    {
    	
        //requesthandle(req);
        if(req.readyState==4)
        { 
              if(req.status==200)
              {  
               
            	  //alert(req.responseText);
                        
              
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
               
              document.frmlist_service.cmbcircle.disabled=false;
               var iframe=document.getElementById("diviframecircle");
                        iframe.style.visibility='hidden';
               var iframe=document.getElementById("diviframedivision");
                        iframe.style.visibility='hidden';
                        rcount+=1;
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
 //   var type=document.frmlist_service.cmbolevel.options[document.frmlist_service.cmbolevel.selectedIndex].value;
 
  

 // if(document.frmlist_service.chkregion)
  //{
  
     // alert(document.frmlist_service.chkregion);
       
           if(document.frmlist_service.chkregion.length > 0)
          {
          
            // alert(document.frmlist_service.chkregion.length);
         
                        for(i=0;i<document.frmlist_service.chkregion.length;i++)
                        {
                          //  alert("document.frmlist_service.chkregion[i].checked..."+document.frmlist_service.chkregion[i].checked);
                                if(document.frmlist_service.chkregion[i].checked==true)
                                        region= region+document.frmlist_service.chkregion[i].value +",";
                                        
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
                                       document.frmlist_service.cmbdivision.disabled=false;
                                  //  return;
                            }
                            region=region.substring(0,region.length-1);
                           // alert("region1..."+region);
                            //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                           
                           
                           // alert(document.getElementById("txtmonth").selectedIndex.text);
                          
                            var url="../../../../../../ConfirmHRreport.av?OLevel=circle&regions="+region;
                         //   alert(url);
                          // document.frmlist_service.cmbregion.focus();
                             req=getTransport();
                            req.open("GET",url,true);        
                            req.onreadystatechange=function()
                            {
                                //requesthandle(req);
                                if(req.readyState==4)
                                { 
                                      if(req.status==200)
                                      {  
                                     // alert("success");
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
                                        document.frmlist_service.cmbdivision.disabled=false;
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
                                         ccount+=1;
                                      }
                                }
                            }
                            req.send(null);
                        }
                        else
                        {
                            var iframe=document.getElementById("diviframecircle");
                            iframe.style.visibility='hidden';
                            document.frmlist_service.cmbdivision.disabled=false;
                            document.frmlist_service.cmbcircle.focus();
                            alert('Please Select a Region');
                        }
        }
        else
        {
        
                         if(document.frmlist_service.chkregion.checked==true)
                         {
                                        region= document.frmlist_service.chkregion.value ;
                               
                                
                                     if(circleflag==true && cir=='null')
                                    {
                                           
                                             var iframe=document.getElementById("diviframecircle");
                                                iframe.style.visibility='visible';
                                              iframe.focus();
                                               document.frmlist_service.cmbdivision.disabled=false;
                                           // return;
                                    }
                                   // region=region.substring(0,region.length-1);
                                   // var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                   
                           
                                    var url="../../../../../../ConfirmHRreport.av?OLevel=circle&regions="+region;
                                    
                                  // document.frmlist_service.cmbregion.focus();
                                    // req=getTransport();
                                    req.open("GET",url,true);        
                                    req.onreadystatechange=function()
                                    {
                                     alert("success");
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
                                                iframe.innerHTML=req.responseText;
                                                if(cir=='null')
                                                {
                                                
                                                iframe.style.visibility='visible';
                                                iframe.focus();
                                                
                                                document.frmlist_service.cmbdivision.disabled=false;
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
                                                 ccount+=1;
                                              }
                                        }
                                    }
                                    req.send(null);
                        }
                        else
                        {
                           
                        
                            var iframe=document.getElementById("diviframecircle");
                            iframe.style.visibility='hidden';
                            document.frmlist_service.cmbdivision.disabled=false;
                            document.frmlist_service.cmbcircle.focus();
                            alert('Please Select a Region');
                        }

        
        
        
        
        }
        
  //}
  

}


function getDivision(div)
{
    
   // alert('division');
   var region="";
   var circle="";
   var offtype="";
   
   
 // if(document.frmlist_service.chkregion)
 // {
  
     
       
           if(document.frmlist_service.chkregion.length)
          {
          
            
         
                        for(i=0;i<document.frmlist_service.chkregion.length;i++)
                        {
                         
                                if(document.frmlist_service.chkregion[i].checked==true)
                                        region= region+document.frmlist_service.chkregion[i].value +",";
                                        //alert('region...'+region);
                                     
                                
                        }
             }
   // }
   
   if(document.frmlist_service.chkofftype)
   {
           
     if(document.frmlist_service.chkofftype.length)
     {
                
      for(i=0;i<document.frmlist_service.chkofftype.length;i++)
      {
        if(document.frmlist_service.chkofftype[i].checked==true)
         offtype= offtype+"'"+document.frmlist_service.chkofftype[i].value+"'"+",";            
                          
      }
       
                  
     
     }
   }
   
  if(document.frmlist_service.chkcircle)
  {
  
        if(document.frmlist_service.chkcircle.length)
        {
        
                        for(i=0;i<document.frmlist_service.chkcircle.length;i++)
                        {
                                if(document.frmlist_service.chkcircle[i].checked==true)
                                        circle= circle+document.frmlist_service.chkcircle[i].value +",";
                              
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
                          
                          var url="../../../../../../ConfirmHRreport.av?OLevel=division&circles="+circle+"&offtype="+offtype+"&regions="+region;
                         //alert(url);
                          
                          // document.frmlist_service.cmbregion.focus();
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
                                        
                                        //var type=document.frmlist_service.cmbolevel.options[document.frmlist_service.cmbolevel.selectedIndex].value;
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
                                       dcount+=1;
                                      }
                                }
                            }
                            req.send(null);
                        }
                        else
                        {
                            var iframe=document.getElementById("diviframedivision");
                            iframe.style.visibility='hidden';
                            document.frmlist_service.cmbdivision.focus();
                            alert('Please Select a Circle');
                        }
                       // alert("circles:"+circle);
            }
            else
            {
            
                         if(document.frmlist_service.chkcircle.checked==true)
                         {
                                        circle= document.frmlist_service.chkcircle.value ;
                                
                       
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
                                 
                           
                                  var url="../../../../../../ConfirmHRreport.av?OLevel=division&circles="+circle+"&offtype="+offtype;
                                  //alert(url);
                                  // document.frmlist_service.cmbregion.focus();
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
                                                //var type=document.frmlist_service.cmbolevel.options[document.frmlist_service.cmbolevel.selectedIndex].value;
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
                                               dcount+=1;
                                              }
                                        }
                                    }
                                    req.send(null);
                        }
                        else
                        {
                            var iframe=document.getElementById("diviframedivision");
                            iframe.style.visibility='hidden';
                            document.frmlist_service.cmbdivision.focus();
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

if(document.frmlist_service.chkregion)
  {
  
        
        for(i=0;i<document.frmlist_service.chkregion.length;i++)
        {
                document.frmlist_service.chkregion[i].checked=true;
                
        }
        // regiononchange();
    }
}


function offtypeselectAll()
{

if(document.frmlist_service.chkofftype)
  {
  
        
        for(i=0;i<document.frmlist_service.chkofftype.length;i++)
        {
                document.frmlist_service.chkofftype[i].checked=true;
                
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
if(document.frmlist_service.chkcircle)
  {
  
        for(i=0;i<document.frmlist_service.chkcircle.length;i++)
        {
                document.frmlist_service.chkcircle[i].checked=true;
                
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
if(document.frmlist_service.chkdivision)
  {
  
        for(i=0;i<document.frmlist_service.chkdivision.length;i++)
        {
                document.frmlist_service.chkdivision[i].checked=true;
                
        }
    }
}

function oftypeonchange()
{

 if(document.frmlist_service.rad_off[3].checked)
 {
   getDivision('division');
 }
 
}

function fnoption()
{
var din=document.getElementById("option");
    din.style.display="block";
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
  
     var url="../../../../../../ListAllCategory_Vacancy_Position?OLevel=offtype" ;
    
    var req=getTransport();
    req.open("GET",url,false);        
    req.send(null);
    
                var iframe=document.getElementById("diviframeofftype");
                iframe.style.visibility="visible";
              
                if(navigator.appName.indexOf("Microsoft")!=-1)
                    iframe.innerHTML=req.responseText;
                else
                    iframe.innerText=req.responseText;
                iframe.innerHTML=req.responseText;
              
               offtypeflag=true;
             /* }
        }
    }
    req.send(null);*/
}


function nullcheck_roles()
{
	/*if(document.frmlist_service.cmbsgroup.value==0)
	{
		alert('Please Select the Service Group');
		return false;
	}*/
	
	return true;
}
function VisibleOfficeType()
{
var iframe=document.getElementById("SpecificOffType");
iframe.style.display='inline';
iframe.style.visibility='visible';
}
function HideOfficeType()
{
var iframe=document.getElementById("SpecificOffType");
iframe.style.display='none';
iframe.style.visibility='hidden';
}

function frmsubmit()
{
   
        var url="../../../../../../ListOfEmp_YearExp_Office?OLevel=submit" ;
        
        //office validation
        if(document.frmlist_service.OffType[0].checked==true)
        {
                url=url+"&offlevel=all";
                document.frmlist_service.offlevel.value='all';
        }
        else
        {
                url=url+"&offlevel=specific";
                 document.frmlist_service.offlevel.value='specific';
                
                // select the office 
                  var type=document.frmlist_service.cmbolevel.options[document.frmlist_service.cmbolevel.selectedIndex].value;
                 if(type=="")
                 {
                    alert('Select the Office Level');
                    document.frmlist_service.cmbolevel.focus();
                    return;
                }
                else
                {
                    url=url+"&office="+type;
                    document.frmlist_service.office.value=type;
                    
                    if(document.frmlist_service.optoffice[0].checked==true)
                    {
                            url=url+"&officetype=all";
                             document.frmlist_service.officetype.value='all';
                    }
                    else
                    {
                            url=url+"&officetype=specific";
                             document.frmlist_service.officetype.value='specific';
                            if(type=='RN')
                            {
                            ////////
                            var region="";
                            if(document.frmlist_service.chkregion)
                              {
                              
                                        if(document.frmlist_service.chkregion.length)
                                        {
                                                  for(i=0;i<document.frmlist_service.chkregion.length;i++)
                                                    {
                                                            if(document.frmlist_service.chkregion[i].checked==true)
                                                                    region= region+document.frmlist_service.chkregion[i].value +",";
                                                            alert("HI Reg "+region);
                                                    }
                                                    if(region!="")
                                                    {
                                                        region=region.substring(0,region.length-1);
                                                         url=url+"&officeselected="+region;
                                                         document.frmlist_service.officeselected.value=region;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Region.');
                                                            document.frmlist_service.cmbregion[0].focus(); 
                                                            return;
                                                    }
                                            
                                        }
                                        else{
                                           
                                                if(document.frmlist_service.chkregion.checked==true)
                                                {
                                                            region= document.frmlist_service.chkregion.value ;
                                                            document.frmlist_service.officeselected.value=region;
                                                }
                                                 else
                                                    {
                                                           alert('Select the Region..');
                                                            document.frmlist_service.cmbregion.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                 else
                                {
                                       alert('Select the Region...');
                                        document.frmlist_service.cmbregion.focus(); 
                                        return ;
                                }    
                            ////
                            
                            }
                            
                            else if(type=='CL')
                            {
                            
                            ////////
                            var circle="";
                            if(document.frmlist_service.chkcircle)
                              {
                              
                                       if(document.frmlist_service.chkcircle.length )
                                        {
                                                
                                                    for(i=0;i<document.frmlist_service.chkcircle.length;i++)
                                                    {
                                                            if(document.frmlist_service.chkcircle[i].checked==true)
                                                                    circle= circle+document.frmlist_service.chkcircle[i].value +",";
                                                            
                                                    }
                                                    if(circle!="")
                                                    {
                                                        circle=circle.substring(0,circle.length-1);
                                                         url=url+"&officeselected="+circle;
                                                         document.frmlist_service.officeselected.value=circle;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.frmlist_service.cmbcircle.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.frmlist_service.chkcircle.checked==true)
                                                {
                                                            circle= document.frmlist_service.chkcircle.value ;
                                                            document.frmlist_service.officeselected.value=circle;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Circle..');
                                                            document.frmlist_service.cmbcircle.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Circle...');
                                       try{
                                        document.frmlist_service.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.frmlist_service.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                            else  if(type=='DN')
                            {
                           
                            ////////
                            var division="";
                            if(document.frmlist_service.chkdivision)
                              {
                             //alert(document.frmlist_service.chkregion.length);
                                     if(document.frmlist_service.chkdivision.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.frmlist_service.chkdivision.length;i++)
                                                {
                                                        if(document.frmlist_service.chkdivision[i].checked==true)
                                                                division= division+document.frmlist_service.chkdivision[i].value +",";
                                                        
                                                }
                                                
                                                if(division!="")
                                                {
                                                    division=division.substring(0,division.length-1);
                                                     url=url+"&officeselected="+division;
                                                     document.frmlist_service.officeselected.value=division;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the Division.');
                                                        document.frmlist_service.cmbdivision.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.frmlist_service.chkdivision.checked==true)
                                                {
                                                                division= document.frmlist_service.chkdivision.value;
                                                                    //division=division.substring(0,division.length-1);
                                                                 url=url+"&officeselected="+division;
                                                                 document.frmlist_service.officeselected.value=division;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the Division..');
                                                        document.frmlist_service.cmbdivision.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the Division...');
                                       try{
                                        document.frmlist_service.cmbdivision.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.frmlist_service.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.frmlist_service.cmbregion.focus(); 
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
        if(document.frmlist_service.optoutputtype[0].checked==true)
        {
                url=url+"&outputtype=pdf";
                document.frmlist_service.outputtype.value='pdf';
        }
        else if(document.frmlist_service.optoutputtype[1].checked==true)
        {
                url=url+"&outputtype=excel";
                document.frmlist_service.outputtype.value='excel';
        }
        else if(document.frmlist_service.optoutputtype[2].checked==true)
        {
                url=url+"&outputtype=html";
                 document.frmlist_service.outputtype.value='html';
        }
        
       
    
    document.frmlist_service.action="../../../../../../ListOfEmp_YearExp_Office";
    document.frmlist_service.submit();
   /* var req=getTransport();
    req.open("POST",url,false);  
    req.send(null);*/

}
    

function clearall()
{
	document.frmlist_service.rad_off[0].checked='checked';
	document.getElementById("trofficeselection").style.display='none';
	document.getElementById("divregion").style.display='none';
	document.getElementById("divcircle").style.display='none';
	document.getElementById("divofftype").style.display='none';
	document.getElementById("divdivision").style.display='none';
	for(i=0;i<document.frmlist_service.select1.length;i++){
	      if(document.frmlist_service.select1[i].checked){
	    	  document.frmlist_service.select1[i].checked=false;
	      }
	}
	document.frmlist_service.cmbsgroup.options[0].selected='selected';	
	document.frmlist_service.cmbsgroup1.options[0].selected='selected';	
	document.frmlist_service.cmbDesignation.options[0].selected='selected';	
	document.frmlist_service.cmbCadre.options[0].selected='selected';	
}

