

var statusflag=false;

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

var s=0;
var hier=true;
var level=true;
var city=true;
var  other=true;

function offtypeclose()
{
    
    var iframe=document.getElementById("diviframeofftype");
    iframe.style.visibility='hidden';
  
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


function designationclose()
{
    
    var iframe=document.getElementById("divdesignation");
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


function showdesignation()
{
   document.frmValidationSummaryRep.optdesignation[1].checked=true;
    var offlevel=document.getElementById("trsergroup");
    offlevel.style.display="block";
}

function getDesignation()
{

	var count=0;
	var stdata="";
	  
	        for(i=0;i<document.frmValidationSummaryRep.chkstatus.length;i++)
	        	
	        {
	        	
	        	if(document.frmValidationSummaryRep.chkstatus[i].checked==true)
	        	{
	        		count++;
	        		stdata+="'"+document.frmValidationSummaryRep.chkstatus[i].value+"',";
	        	}
	                
	        }
	       
	   
	if(count==0)
	{
		var iframe1=document.getElementById("EmpType");
		iframe1.style.display='none';
		iframe1.style.visibility='hidden';
		alert("select status");

	}
	else
	{
		var desg=document.frmValidationSummaryRep.cmbsgroup.value
		if(desg==0)
		{
			var iframe1=document.getElementById("EmpType");
			iframe1.style.display='none';
			iframe1.style.visibility='hidden';
			alert("select Destination");
			return false;
		}
		else
		{
		
		 var url="../../../../../../Status_Summary_ReportServ?&OLevel=EmpType&status="+stdata+"&des="+desg;
	
		   
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
		                
		                
		                
		                var iframe=document.getElementById("EmpType");
		                iframe.style.visibility='visible';
		                iframe.focus();
		                // alert(navigator.appName);
		                // alert(navigator.appName.indexOf('Microsoft'));
		                
		                
		                if(navigator.appName.indexOf('Microsoft')!=-1)
		                iframe.innerHTML=req.responseText;
		                else
		                iframe.innerText=req.responseText;
		                iframe.innerHTML=req.responseText;
		                statusflag=true;
		            }
		        }
		    }
		    req.send(null);
		    var iframe1=document.getElementById("EmpType");
		    iframe1.style.display='inline';
		    iframe1.style.visibility='visible';
	}


	}
	
	
	
   
}
    

    
    
    function loadOfficesByType(type)
    {
        var type=document.frmValidationSummaryRep.cmbsgroup.options[document.frmValidationSummaryRep.cmbsgroup.selectedIndex].value;
       var url="../../../../../../DesigCadRankServ_New?Command=Desig&cmbsgroup=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            if(req.readystate==4)
            {
            if(req.status==200)
            {
            var divdes=document.getElementById("divdesignation");
            divdes.style.visibility='visible';
            divdes.focus();
           if(navigator.appName.indexOf('Microsoft')!=-1)
           {
                divdes.innerHTML=req.responseText;
          
            }
            else
                divdes.innerText=req.responseText;
            }
            }
             
             }
        req.send(null);
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



function frmsubmit()
{
 
        var url="../../../../../../Status_Summary_ReportServ?OLevel=submit";
    
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
        
   
    
    document.frmValidationSummaryRep.action="../../../../../../Status_Summary_ReportServ";
    document.frmValidationSummaryRep.submit();
   

}



function frmsubmit1()
{
 //alert("frm submit1");
        var url="../../../../../../ServiceRecord_Pending_Reportserv.av?OLevel=submit";
//alert(url);
    
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
        
 //  alert(outputtype);
    
    document.frmValidationSummaryRep.action="../../../../../../ServiceRecord_Pending_Reportserv.av";
    document.frmValidationSummaryRep.submit();
   

}



//////////////////////////// for next purpose ///////////////////////
///////////////////////  for designation,cadre,rank   ///////////////////////

function sellectall()
{
    if(document.frmValidationSummaryRep.optselectgrp[1].checked)
    {
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
   if(document.frmValidationSummaryRep.optselectgrp[0].checked)
   {
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




 function loadOfficesByType1(type)
    {
        //alert(type);
        var type=document.frmValidationSummaryRep.cmbsgroup1.options[document.frmValidationSummaryRep.cmbsgroup1.selectedIndex].value;
        //startwaiting(document.frmValidationSummaryRep) ;
         //service=null;
      
       //var url="../../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
       var url="../../../../../../DesigCadRankServ_New?Command=Rank&cmbsgroup=" + type ;
       //alert(url);
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
        //alert(req.readystate);
            if(req.readystate==4)
            {
            //alert(req.status);
            if(req.status==200)
            {
            //alert(req.responseText);
            var divdes=document.getElementById("divdesignation");
            divdes.style.visibility='visible';
            divdes.focus();
            //divdes.style.display='block';
           // var des=document.getElementById("cmbDesignation");
            
            
          //  alert(des);
            
           if(navigator.appName.indexOf('Microsoft')!=-1)
                divdes.innerHTML=req.responseText;
            else
                divdes.innerText=req.responseText;
           divdes.innerHTML=req.responseText;
             //alert("show");
            //document.getElementById("cmbRank").visibility='hidden';  
            //document.getElementById("cmbCadre").visibility='hidden';
            }
            }
             
             }
        req.send(null);
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
        //alert(type);
        var type=document.frmValidationSummaryRep.cmbsgroup2.options[document.frmValidationSummaryRep.cmbsgroup2.selectedIndex].value;
        //startwaiting(document.frmValidationSummaryRep) ;
         //service=null;
      
       //var url="../../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
       var url="../../../../../../DesigCadRankServ_New?Command=Cadre&cmbsgroup=" + type ;
       //alert(url);
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
        //alert(req.readystate);
            if(req.readystate==4)
            {
            //alert(req.status);
            if(req.status==200)
            {
            //alert(req.responseText);
            var divdes=document.getElementById("divdesignation");
            divdes.style.visibility='visible';
            divdes.focus();
            //divdes.style.display='block';
           // var des=document.getElementById("cmbDesignation");
            
            
          //  alert(des);
            
           if(navigator.appName.indexOf('Microsoft')!=-1)
                divdes.innerHTML=req.responseText;
            else
                divdes.innerText=req.responseText;
           divdes.innerHTML=req.responseText;
             //alert("show");
            //document.getElementById("cmbRank").visibility='hidden';  
            //document.getElementById("cmbCadre").visibility='hidden';
            }
            }
             
             }
        req.send(null);
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
                            var url="../../../../../../Status_Summary_ReportServ?desigs="+desig;
                            //alert(url);
                            }
               }
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
         var url="../../../../../../Status_Summary_ReportServ?OLevel=offtype" ;
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
    
 
function getStatus()
{
	
        alert("status");
	var iframe1=document.getElementById("EmpType");
	iframe1.style.display='none';
	iframe1.style.visibility='hidden';
    if(statusflag==true)
    {
    
        var iframe=document.getElementById("diviframeregion");
        iframe.style.visibility='visible';
        iframe.focus();
        // return;
    }
    var url="../../../../../../Status_Summary_ReportServ?OLevel=Status";

    document.frmValidationSummaryRep.cmbstatus.focus();
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
                
                
                
                var iframe=document.getElementById("diviframestatus");
                iframe.style.visibility='visible';
                iframe.focus();
                // alert(navigator.appName);
                // alert(navigator.appName.indexOf('Microsoft'));
                
                
                if(navigator.appName.indexOf('Microsoft')!=-1)
                iframe.innerHTML=req.responseText;
                else
                iframe.innerText=req.responseText;
                iframe.innerHTML=req.responseText;
                statusflag=true;
            }
        }
    }
    req.send(null);
}


function statusselectAll()
{

   if(document.frmValidationSummaryRep.chkstatus)
      {
      
            
            for(i=0;i<document.frmValidationSummaryRep.chkstatus.length;i++)
            {
                    document.frmValidationSummaryRep.chkstatus[i].checked=true;
                    
            }
           //  regiononchange();
        }
}

function statusclose()
{
    
    var iframe=document.getElementById("diviframestatus");
    iframe.style.visibility='hidden';
  
}
function formsub()
{
	var count=0;

  
        for(i=0;i<document.frmValidationSummaryRep.chkstatus.length;i++)
        	
        {
        	
        	if(document.frmValidationSummaryRep.chkstatus[i].checked==true)
        	{
        		count++;
        	}
                
        }

if(count==0)
{
	alert("select status");
	return false;
}
else if(document.frmValidationSummaryRep.OffType[0].checked==true)
{
	
	return true;
}
	
else if(document.frmValidationSummaryRep.OffType[1].checked==true){
 
	if(document.frmValidationSummaryRep.cmbsgroup.value==0){
		alert("Select Designation");
		return false;
	}
	else
	{
		
		return true;
	}

}

else
{
	alert("Select Designation");
	return false;
}

}
function VisibleOfficeType()
{
var iframe=document.getElementById("SpecificOffType");
iframe.style.display='inline';
iframe.style.visibility='visible';
var iframe1=document.getElementById("EmpType");
iframe1.style.display='none';
iframe1.style.visibility='hidden';
}
function getchange()
{
	var iframe1=document.getElementById("EmpType");
	iframe1.style.display='none';
	iframe1.style.visibility='hidden';
}
function HideOfficeType()
{
var iframe1=document.getElementById("SpecificOffType");
iframe1.style.display='none';
iframe1.style.visibility='hidden';
var count=0;
var stdata="";
  
        for(i=0;i<document.frmValidationSummaryRep.chkstatus.length;i++)
        	
        {
        	
        	if(document.frmValidationSummaryRep.chkstatus[i].checked==true)
        	{
        		count++;
        		stdata+="'"+document.frmValidationSummaryRep.chkstatus[i].value+"',";
        	}
                
        }
       
   
if(count==0)
{
	alert("select status");

}
else
{
	 var url="../../../../../../Status_Summary_ReportServ?&OLevel=EmpType&status="+stdata+"&des=All";

	  
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
	                
	                
	                
	                var iframe=document.getElementById("EmpType");
	                iframe.style.display='inline';
	                iframe.style.visibility='visible';
	                iframe.focus();
	                // alert(navigator.appName);
	                // alert(navigator.appName.indexOf('Microsoft'));
	                
	                
	                if(navigator.appName.indexOf('Microsoft')!=-1)
	                iframe.innerHTML=req.responseText;
	                else
	                iframe.innerText=req.responseText;
	                iframe.innerHTML=req.responseText;
	                statusflag=true;
	            }
	        }
	    }
	    req.send(null);
}

}

function viewEmpbyDesg(s)
{
	 if (winemp && winemp.open && !winemp.closed) 
	    {
	       winemp.resizeTo(500,500);
	       winemp.moveTo(250,250); 
	       winemp.focus();
	    }
	    else
	    {
	        winemp=null
	    }
   
    var url=document.getElementById("name"+s).value;
    winemp= window.open('status_report_popup.jsp?'+url,'Summaryreport','width=1000,height=600,menubar=no,status=yes,location=yes,toolbar=no,scrollbars=yes');
    winemp.moveTo(250,250); 
    winemp.focus();
}
var winemp;
function viewEmpbyDesg1(s)
{
	
    var url="../../../../../../Status_Summary_ReportServ?OLevel=viewEmp&workstat="+s;

    
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
                
                
                
                var iframe=document.getElementById("getemplist");
                iframe.style.visibility='visible';
                iframe.focus();
                // alert(navigator.appName);
                // alert(navigator.appName.indexOf('Microsoft'));
                
                
                if(navigator.appName.indexOf('Microsoft')!=-1)
                iframe.innerHTML=req.responseText;
                else
                iframe.innerText=req.responseText;
                iframe.innerHTML=req.responseText;
                statusflag=true;
            }
        }
    }
    req.send(null);
}
