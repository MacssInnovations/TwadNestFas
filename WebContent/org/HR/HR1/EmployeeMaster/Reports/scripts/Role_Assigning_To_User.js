//alert('detail');
var role_value='';
var desigval='';

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

function getDesignation1()
{


  var type=document.frmValidationSummaryRep.cmbsgroup1.options[document.frmValidationSummaryRep.cmbsgroup1.selectedIndex].value;
  //alert(type)     
  if(type!=0)
  {
    loadOfficesByType1(type);
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
    


 function loadOfficesByType1(type)
 {
        
        var type=document.frmValidationSummaryRep.cmbsgroup1.options[document.frmValidationSummaryRep.cmbsgroup1.selectedIndex].value;
        var url="../../../../../../New_Role_Assigning.av?Command=Desig&cmbsgroup=" + type ;
       //alert(url);
        var req=getTransport();
        req.open("GET",url,false);   
           req.onreadystatechange=function()
       {
            loadDesignation(req)
       }
        req.send(null);
          
  }
           


 
    
function  loadDesignation(req)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
               
                var des=document.getElementById("Desig");
                while(des.length > 0) 
                {  
                des.remove(0);
                }
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
               
                
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
                        var id=tmpoption.getElementsByTagName("desig_id")[0].firstChild.nodeValue;
                        var name=tmpoption.getElementsByTagName("desig_name")[0].firstChild.nodeValue;
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

function frmrolesubmit()
{
   
    
      var desigval=document.frmValidationSummaryRep.Desig.options[document.frmValidationSummaryRep.Desig.selectedIndex].value;
 if(desigval!=0)
 {
    if(nullcheck_roles())
     {
    //  alert('inside submit 2');
        var url="../../../../../../Role_Assigning_To_User.av?Command=submit&roleval="+role_value+"&desigval="+desigval;
         if(document.frmValidationSummaryRep.OffType[1].checked==true)
         {
                  if(document.frmValidationSummaryRep.rad_off[0].checked)    
                       url=url+"&officeselected="+5000;  
                     else
                       if(document.frmValidationSummaryRep.rad_off[1].checked)    
                       {
                        
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
                                    
                          
                            
                            }
                }
                else
                 url=url+"&officeselected=alloffice";
       
   // alert(url);     
   var req=getTransport();
    req.open("GET",url,false);       
    req.onreadystatechange=function()
    {
     if(req.readyState==4)
            {
            if(req.status==200)
                 { 
                      
                     var response=req.responseXML.getElementsByTagName("response")[0];
                    // alert(response);
                     var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                     var iflag=response.getElementsByTagName("iflag")[0].firstChild.nodeValue;
                     
                   // stopwaiting(document.frmValidationSummaryRep);
                              if (flag=="Noemp" || flag=="failure")
                                   alert("Employee doesn't exist for this designation ");
                                   
                                
                                if(iflag=="success")
                                {
                                    alert("Roles are alloted to users ");
                                    clearAll();
                                }
                              
                                
                                
                            
                            }
                        }
                    }
                       
                        req.send(null);   
                       
         }
    }
    else
        alert("Select the Designation");
}

function clearAll()
{
      document.getElementById("cmbsgroup1").selectedIndex=0;
       var des=document.getElementById("Desig");
       var   length = des.options.length;
        for(i = 0; i < length; i++) 
            des.remove(1);
      
       var URoles=document.getElementById("URoles");
        length = URoles.options.length;
        for(i = 0; i < length; i++) 
            URoles.remove(0);
        
     
}


function nullcheck_roles()
{
role_value='';
       var des=document.getElementById("URoles");
    //  alert("len"+des.length);
                 for(i=0;i<des.length;i++)
                    {
                      
                            if(des.options[i].selected==true)
                            
                             role_value= role_value+des.options[i].value+",";
                            
                                                         
                     }                       
                      // alert(role_value);
                            if(role_value==""||role_value==0)
                            {
                            
                                   alert('Select the Roles to be Updated..');
                                   return false;
                            }
                          else
                            
                            {
                            role_value=role_value.substring(0,role_value.length-1);
                          //  alert(role_value);
                            return true;   
                            }
                                        
                             
}

function CallRoles()
{
 
    var desigval=document.frmValidationSummaryRep.Desig.options[document.frmValidationSummaryRep.Desig.selectedIndex].value;
    
    var url="../../../../../../New_Role_Assigning.av?Command=Role&desigval="+desigval ;
    
    var req=getTransport();
        
      req.open("GET",url,false);  
       var des1=document.getElementById("URoles");
       des1.innerHTML='';
      req.onreadystatechange=function()
       {
                              
       if(req.readyState==4)
            {
            if(req.status==200)
                 { 
                       
                        var i=0;
                       var response=req.responseXML.getElementsByTagName("response")[0];
                      
                        var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                     //  alert("flag"+flag);
                                          
                         stopwaiting(document.frmValidationSummaryRep);
                           
                          var uflag=response.getElementsByTagName("uflag")[0].firstChild.nodeValue;         
                          if(uflag=="failure")
                        {
                            alert("No Roles updated for this Designation ");
                        }
                        else if(uflag=="sessionout")
                    {
                        alert('Session is closed');
                        //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                        self.close();
                        return;
                    }
                        else
                        {   
                       // alert("success");
                       
                                var des1=document.getElementById("URoles");
                                des1.innerHTML='';
                                var uvalue=response.getElementsByTagName("uroles");
                                //alert("value---->"+uvalue);
                                var option=document.createElement("OPTION");
                                
                              for(var i=0;i<uvalue.length;i++)
                                {
                               
                                    var tmpoption=uvalue.item(i);
                                    var id=tmpoption.getElementsByTagName("urole_id")[0].firstChild.nodeValue;
                                    var name=tmpoption.getElementsByTagName("urole_name")[0].firstChild.nodeValue;
                                    var option=document.createElement("OPTION");
                                      option.text=name;
                                      option.value=id;
                                      //Making Browser Independent
                                      try
                                      {
                                          des1.add(option);
                                      }
                                      catch(errorObject)
                                      {
                                          des1.add(option,null);
                                      }
                                }
                        }
                  
                  }
               
             }
                 
        }
      
 req.send(null);
}


function VisibleOfficeType()
{
   // alert(document.frmValidationSummaryRep.OffType[0].checked);
    if(document.frmValidationSummaryRep.OffType[0].checked==true)
            document.getElementById("SpecificOffType").style.display="none";        
    else
            document.getElementById("SpecificOffType").style.display="block";     
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
//    document.getElementById("divallhier").style.display='block';
    
    
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
        
       // alert('division');
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
                              
                              var url="../../../../../../ConfirmHRreport.av?OLevel=division&circles="+circle+"&offtype="+offtype+"&regions="+region;
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
                                     
                               
                                      var url="../../../../../../ConfirmHRreport.av?OLevel=division&circles="+circle+"&offtype="+offtype;
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


