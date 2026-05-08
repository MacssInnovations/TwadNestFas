//alert('job popup');
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

 function getRegion()
    {
        var type=document.frmOfficeDetailRep.cmbolevel.options[document.frmOfficeDetailRep.cmbolevel.selectedIndex].value;
        //alert('getoffice:'+type);
        if(type!='HO' && type!="")
        {
           if(type=='RN')
           {
                var din=document.getElementById("divregion");
                din.style.visibility="visible";
                document.frmOfficeDetailRep.cmbregion.style.visibility="visible";
                
                var din=document.getElementById("divcircle");
            din.style.visibility="hidden";
            document.frmOfficeDetailRep.cmbcircle.style.visibility="hidden";
            
             var din=document.getElementById("divdivision");
            din.style.visibility="hidden";
            document.frmOfficeDetailRep.cmbdivision.style.visibility="hidden";
            
            var din=document.getElementById("divsubdiv");
            din.style.visibility="hidden";
            document.frmOfficeDetailRep.cmbsubdiv.style.visibility="hidden";
            
            var din=document.getElementById("divsection");
            din.style.visibility="hidden";
            document.frmOfficeDetailRep.cmbsection.style.visibility="hidden";
           
           }
           else
           {
            var din=document.getElementById("divcircle");
            din.style.visibility="hidden";
            document.frmOfficeDetailRep.cmbcircle.style.visibility="hidden";
            
             var din=document.getElementById("divdivision");
            din.style.visibility="hidden";
            document.frmOfficeDetailRep.cmbdivision.style.visibility="hidden";
            
            var din=document.getElementById("divsubdiv");
            din.style.visibility="hidden";
            document.frmOfficeDetailRep.cmbsubdiv.style.visibility="hidden";
            
            var din=document.getElementById("divsection");
            din.style.visibility="hidden";
            document.frmOfficeDetailRep.cmbsection.style.visibility="hidden";
          }  
          
          
          
          if(type=='CL')
           {
                var din=document.getElementById("divregion");
                din.style.visibility="visible";
                document.frmOfficeDetailRep.cmbregion.style.visibility="visible";
                
                var din=document.getElementById("divcircle");
                din.style.visibility="visible";
                document.frmOfficeDetailRep.cmbcircle.style.visibility="visible";
                document.frmOfficeDetailRep.cmbcircle.disabled=true;
                
                var cmbcircle=document.getElementById("cmbcircle");
                cmbcircle.innerHTML="";
                var option=document.createElement("OPTION");
                option.text="--Select Circle--";
                try
                {
                    cmbcircle.add(option);
                }catch(errorObject)
                {
                cmbcircle.add(option,null);
                }
           }
           else
           {
               var din=document.getElementById("divdivision");
                din.style.visibility="hidden";
                document.frmOfficeDetailRep.cmbdivision.style.visibility="hidden";
                
                var din=document.getElementById("divsubdiv");
                din.style.visibility="hidden";
                document.frmOfficeDetailRep.cmbsubdiv.style.visibility="hidden";
                
                var din=document.getElementById("divsection");
                din.style.visibility="hidden";
                document.frmOfficeDetailRep.cmbsection.style.visibility="hidden";
          }  
          
           if(type=='DN')
           {
                var din=document.getElementById("divregion");
                din.style.visibility="visible";
                document.frmOfficeDetailRep.cmbregion.style.visibility="visible";
                
                var din=document.getElementById("divcircle");
                din.style.visibility="visible";
                document.frmOfficeDetailRep.cmbcircle.style.visibility="visible";
                document.frmOfficeDetailRep.cmbcircle.disabled=true;
                
                var din=document.getElementById("divdivision");
                din.style.visibility="visible";
                document.frmOfficeDetailRep.cmbdivision.style.visibility="visible";
                document.frmOfficeDetailRep.cmbdivision.disabled=true;
                
                var cmb=document.getElementById("cmbcircle");
                cmb.innerHTML="";
                var option=document.createElement("OPTION");
                option.text="--Select Circle--";
                try
                {
                    cmb.add(option);
                }catch(errorObject)
                {
                cmb.add(option,null);
                }
                
                var cmb=document.getElementById("cmbdivision");
                cmb.innerHTML="";
                var option=document.createElement("OPTION");
                option.text="--Select Circle--";
                try
                {
                    cmb.add(option);
                }catch(errorObject)
                {
                cmb.add(option,null);
                }
           }
           else
           {
                var din=document.getElementById("divsubdiv");
                din.style.visibility="hidden";
                document.frmOfficeDetailRep.cmbsubdiv.style.visibility="hidden";
                
                var din=document.getElementById("divsection");
                din.style.visibility="hidden";
                document.frmOfficeDetailRep.cmbsection.style.visibility="hidden";
          }  
          
           if(type=='SD')
           {
                var din=document.getElementById("divregion");
                din.style.visibility="visible";
                document.frmOfficeDetailRep.cmbregion.style.visibility="visible";
                
                var din=document.getElementById("divcircle");
                din.style.visibility="visible";
                document.frmOfficeDetailRep.cmbcircle.style.visibility="visible";
                document.frmOfficeDetailRep.cmbcircle.disabled=true;
                
                var din=document.getElementById("divdivision");
                din.style.visibility="visible";
                document.frmOfficeDetailRep.cmbdivision.style.visibility="visible";
                document.frmOfficeDetailRep.cmbdivision.disabled=true;
                
                var din=document.getElementById("divsubdiv");
                din.style.visibility="visible";
                document.frmOfficeDetailRep.cmbsubdiv.style.visibility="visible";
                document.frmOfficeDetailRep.cmbsubdiv.disabled=true;
                
                var cmb=document.getElementById("cmbcircle");
                cmb.innerHTML="";
               
                
                var option=document.createElement("OPTION");
                option.text="--Select Circle--";
                try
                {
                    cmb.add(option);
                }catch(errorObject)
                {
                cmb.add(option,null);
                }
                
                var cmb=document.getElementById("cmbdivision");
                cmb.innerHTML="";
                var option=document.createElement("OPTION");
                option.text="--Select Circle--";
                try
                {
                    cmb.add(option);
                }catch(errorObject)
                {
                cmb.add(option,null);
                }
                var cmb=document.getElementById("cmbsubdiv");
                cmb.innerHTML="";
                var option=document.createElement("OPTION");
                option.text="--Select Circle--";
                try
                {
                    cmb.add(option);
                }catch(errorObject)
                {
                cmb.add(option,null);
                }
           }
           else
           {
                
                
                var din=document.getElementById("divsection");
                din.style.visibility="hidden";
                document.frmOfficeDetailRep.cmbsection.style.visibility="hidden";
          }  
          
           if(type=='SN')
           {
                var din=document.getElementById("divregion");
                din.style.visibility="visible";
                document.frmOfficeDetailRep.cmbregion.style.visibility="visible";
                
                var din=document.getElementById("divcircle");
                din.style.visibility="visible";
                document.frmOfficeDetailRep.cmbcircle.style.visibility="visible";
                document.frmOfficeDetailRep.cmbcircle.disabled=true;
                
                var din=document.getElementById("divdivision");
                din.style.visibility="visible";
                document.frmOfficeDetailRep.cmbdivision.style.visibility="visible";
                document.frmOfficeDetailRep.cmbdivision.disabled=true;
                
                var din=document.getElementById("divsubdiv");
                din.style.visibility="visible";
                document.frmOfficeDetailRep.cmbsubdiv.style.visibility="visible";
                document.frmOfficeDetailRep.cmbsubdiv.disabled=true;
                
                  var din=document.getElementById("divsection");
                din.style.visibility="visible";
                document.frmOfficeDetailRep.cmbsection.style.visibility="visible";
                document.frmOfficeDetailRep.cmbsection.disabled=true;
                
                var cmb=document.getElementById("cmbcircle");
                cmb.innerHTML="";
                var option=document.createElement("OPTION");
                option.text="--Select Circle--";
                try
                {
                    cmb.add(option);
                }catch(errorObject)
                {
                cmb.add(option,null);
                }
                
                var cmb=document.getElementById("cmbdivision");
                cmb.innerHTML="";
                var option=document.createElement("OPTION");
                option.text="--Select Circle--";
                try
                {
                    cmb.add(option);
                }catch(errorObject)
                {
                cmb.add(option,null);
                }
                var cmb=document.getElementById("cmbsubdiv");
                cmb.innerHTML="";
                var option=document.createElement("OPTION");
                option.text="--Select Circle--";
                try
                {
                    cmb.add(option);
                }catch(errorObject)
                {
                cmb.add(option,null);
                }
                
                 var cmb=document.getElementById("cmbsection");
                cmb.innerHTML="";
                var option=document.createElement("OPTION");
                option.text="--Select Circle--";
                try
                {
                    cmb.add(option);
                }catch(errorObject)
                {
                cmb.add(option,null);
                }
           }
          
            loadRegion(type);
        }
        else
        {
        
            if(type!="")
            {
                s=type;
            }
            var din=document.getElementById("divregion");
            din.style.visibility="hidden";
            document.frmOfficeDetailRep.cmbregion.style.visibility="hidden";
            
            var din=document.getElementById("divcircle");
            din.style.visibility="hidden";
            document.frmOfficeDetailRep.cmbcircle.style.visibility="hidden";
            
             var din=document.getElementById("divdivision");
            din.style.visibility="hidden";
            document.frmOfficeDetailRep.cmbdivision.style.visibility="hidden";
            
            var din=document.getElementById("divsubdiv");
            din.style.visibility="hidden";
            document.frmOfficeDetailRep.cmbsubdiv.style.visibility="hidden";
            
            var din=document.getElementById("divsection");
            din.style.visibility="hidden";
            document.frmOfficeDetailRep.cmbsection.style.visibility="hidden";
            
            return true;
        }
    }
    
    
 function loadRegion(type)
    {
        //alert(type);
        var type=document.frmOfficeDetailRep.cmbolevel.options[document.frmOfficeDetailRep.cmbolevel.selectedIndex].value;
        startwaiting(document.frmOfficeDetailRep) ;
       var url="../../../../../../JobPopupServ.view?Command=OLevel&cmbolevel=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadRegionReq(req);
        }
        req.send(null);
    }
    
    function loadRegionReq(req)
    {
        if(req.readyState==4)
        {
          if(req.status==200)
          {  
                //alert('hello');
                stopwaiting(document.frmOfficeDetailRep) ;
                var cmbregion=document.getElementById("cmbregion");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                cmbregion.innerHTML="";
                //cmboffices.text="--select Office--";
                if(flag=="failure")
                {
                    alert("No Region exists under this level");
                    var din=document.getElementById("divcircle");
                    din.style.visibility="hidden";
                    document.frmOfficeDetailRep.cmbcircle.style.visibility="hidden";
                    
                     var din=document.getElementById("divdivision");
                    din.style.visibility="hidden";
                    document.frmOfficeDetailRep.cmbdivision.style.visibility="hidden";
                    
                    var din=document.getElementById("divsubdiv");
                    din.style.visibility="hidden";
                    document.frmOfficeDetailRep.cmbsubdiv.style.visibility="hidden";
                    
                    var din=document.getElementById("divsection");
                    din.style.visibility="hidden";
                    document.frmOfficeDetailRep.cmbsection.style.visibility="hidden";
                }
                else
                { 
                    //alert('success');
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="--Select Region--";
                    option.value="";
                    try
                    {
                        cmbregion.add(option);
                    }catch(errorObject)
                    {
                    cmbregion.add(option,null);
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
                              cmbregion.add(option);
                          }
                          catch(errorObject)
                          {
                              cmbregion.add(option,null);
                          }
                    }
                }
            }
        }
    }
    
    
////for region combo box////////////////////////////////////////////////////////////

function getCircle()
    {
        var type=document.frmOfficeDetailRep.cmbregion.options[document.frmOfficeDetailRep.cmbregion.selectedIndex].value;
        //alert('region:'+type);
         document.frmOfficeDetailRep.cmbcircle.disabled=true;
            document.frmOfficeDetailRep.cmbcircle.selectedIndex=0;
            document.frmOfficeDetailRep.cmbdivision.disabled=true;
            document.frmOfficeDetailRep.cmbdivision.selectedIndex=0;
            document.frmOfficeDetailRep.cmbsubdiv.disabled=true;
            document.frmOfficeDetailRep.cmbsubdiv.selectedIndex=0;
            document.frmOfficeDetailRep.cmbsection.disabled=true;
            document.frmOfficeDetailRep.cmbsection.selectedIndex=0;
        if(type!="")
        {
            var ol=document.frmOfficeDetailRep.cmbolevel.options[document.frmOfficeDetailRep.cmbolevel.selectedIndex].value;
            if(ol=='RN')
            {
                s=type;
                return true;
            }
            
            loadCircle(type);
        }
      /*  else
        {
            document.frmOfficeDetailRep.cmbcircle.disabled=true;
            document.frmOfficeDetailRep.cmbcircle.selectedIndex=0;
            document.frmOfficeDetailRep.cmbdivision.disabled=true;
            document.frmOfficeDetailRep.cmbdivision.selectedIndex=0;
            document.frmOfficeDetailRep.cmbsubdiv.disabled=true;
            document.frmOfficeDetailRep.cmbsubdiv.selectedIndex=0;
            document.frmOfficeDetailRep.cmbsection.disabled=true;
            document.frmOfficeDetailRep.cmbsection.selectedIndex=0;
        }*/
       
    }
    
    
 function loadCircle(type)
    {
        //alert(type);
        var type=document.frmOfficeDetailRep.cmbregion.options[document.frmOfficeDetailRep.cmbregion.selectedIndex].value;
        startwaiting(document.frmOfficeDetailRep) ;
        var url="../../../../../../JobPopupServ.view?Command=Region&cmbregion=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadCircleReq(req);
        }
        req.send(null);
    }
    
    function loadCircleReq(req)
    {
        if(req.readyState==4)
        {
          if(req.status==200)
          {  
            //alert('hello');
                stopwaiting(document.frmOfficeDetailRep) ;
                var cmbcircle=document.getElementById("cmbcircle");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
                //cmboffices.text="--select Office--";
                if(flag=="failure")
                {
                    alert("No Circle exists under this level");
                    document.frmOfficeDetailRep.cmbcircle.disabled=true;
                    document.frmOfficeDetailRep.cmbcircle.selectedIndex=0;
                    document.frmOfficeDetailRep.cmbdivision.disabled=true;
                    document.frmOfficeDetailRep.cmbdivision.selectedIndex=0;
                    document.frmOfficeDetailRep.cmbsubdiv.disabled=true;
                    document.frmOfficeDetailRep.cmbsubdiv.selectedIndex=0;
                    document.frmOfficeDetailRep.cmbsection.disabled=true;
                    document.frmOfficeDetailRep.cmbsection.selectedIndex=0;
                }
                else
                { 
                    //alert('success');
                    document.frmOfficeDetailRep.cmbcircle.disabled=false;
                    cmbcircle.innerHTML="";
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="--Select Circle--";
                    try
                    {
                        cmbcircle.add(option);
                    }catch(errorObject)
                    {
                    cmbcircle.add(option,null);
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
                              cmbcircle.add(option);
                          }
                          catch(errorObject)
                          {
                              cmbcircle.add(option,null);
                          }
                    }
                }
            }
        }
    }



///////////////////////////////////////////////////////////////////////////////////////

////for Circle combo box////////////////////////////////////////////////////////////

function getDivision()
    {
        var type=document.frmOfficeDetailRep.cmbcircle.options[document.frmOfficeDetailRep.cmbcircle.selectedIndex].value;
        //alert('circle:'+type);
            document.frmOfficeDetailRep.cmbdivision.disabled=true;
            document.frmOfficeDetailRep.cmbdivision.selectedIndex=0;
            document.frmOfficeDetailRep.cmbsubdiv.disabled=true;
            document.frmOfficeDetailRep.cmbsubdiv.selectedIndex=0;
            document.frmOfficeDetailRep.cmbsection.disabled=true;
            document.frmOfficeDetailRep.cmbsection.selectedIndex=0;
        if(type!="")
        {
            var ol=document.frmOfficeDetailRep.cmbolevel.options[document.frmOfficeDetailRep.cmbolevel.selectedIndex].value;
            if(ol=='CL')
            {
                s=type;
                return true;
            }
            loadDivision(type);
        }
     /*   else
        {
            document.frmOfficeDetailRep.cmbdivision.disabled=true;
            document.frmOfficeDetailRep.cmbdivision.selectedIndex=0;
            document.frmOfficeDetailRep.cmbsubdiv.disabled=true;
            document.frmOfficeDetailRep.cmbsubdiv.selectedIndex=0;
            document.frmOfficeDetailRep.cmbsection.disabled=true;
            document.frmOfficeDetailRep.cmbsection.selectedIndex=0;
        }*/
    }
    
    
 function loadDivision(type)
    {
        //alert(type);
        var cir=document.frmOfficeDetailRep.cmbcircle.options[document.frmOfficeDetailRep.cmbcircle.selectedIndex].value;
         var reg=document.frmOfficeDetailRep.cmbregion.options[document.frmOfficeDetailRep.cmbregion.selectedIndex].value;
       startwaiting(document.frmOfficeDetailRep) ;
        var url="../../../../../../JobPopupServ.view?Command=Division&cmbregion="+reg+"&cmbcircle=" + cir ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadDivisionReq(req);
        }
        req.send(null);
    }
    
    function loadDivisionReq(req)
    {
        if(req.readyState==4)
        {
          if(req.status==200)
          {  
            stopwaiting(document.frmOfficeDetailRep) ;
                var cmbdivision=document.getElementById("cmbdivision");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
                //cmboffices.text="--select Office--";
                if(flag=="failure")
                {
                    alert("No Division/Lab exists under this level");
                    document.frmOfficeDetailRep.cmbdivision.disabled=true;
                    document.frmOfficeDetailRep.cmbdivision.selectedIndex=0;
                    document.frmOfficeDetailRep.cmbsubdiv.disabled=true;
                    document.frmOfficeDetailRep.cmbsubdiv.selectedIndex=0;
                    document.frmOfficeDetailRep.cmbsection.disabled=true;
                    document.frmOfficeDetailRep.cmbsection.selectedIndex=0;
                    
                }
                else
                { 
                    // alert('success');
                    cmbdivision.innerHTML="";
                    document.frmOfficeDetailRep.cmbdivision.disabled=false;
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="--Select Division--";
                    try
                    {
                        cmbdivision.add(option);
                    }catch(errorObject)
                    {
                    cmbdivision.add(option,null);
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
                              cmbdivision.add(option);
                          }
                          catch(errorObject)
                          {
                              cmbdivision.add(option,null);
                          }
                    }
                }
            }
        }
    }



///////////////////////////////////////////////////////////////////////////////////////

////for Division combo box////////////////////////////////////////////////////////////

function getSubDivision()
    {
        var type=document.frmOfficeDetailRep.cmbdivision.options[document.frmOfficeDetailRep.cmbdivision.selectedIndex].value;
        //alert('Divi:'+type);
         document.frmOfficeDetailRep.cmbsubdiv.disabled=true;
            document.frmOfficeDetailRep.cmbsubdiv.selectedIndex=0;
            document.frmOfficeDetailRep.cmbsection.disabled=true;
            document.frmOfficeDetailRep.cmbsection.selectedIndex=0;
        if(type!="")
        {
            var ol=document.frmOfficeDetailRep.cmbolevel.options[document.frmOfficeDetailRep.cmbolevel.selectedIndex].value;
            if(ol=='DN' || ol=='LB')
            {
                s=type;
                return true;
            }
            
            
            loadSubDivision(type);
        }
      /*  else
        {
           document.frmOfficeDetailRep.cmbsubdiv.disabled=true;
            document.frmOfficeDetailRep.cmbsubdiv.selectedIndex=0;
            document.frmOfficeDetailRep.cmbsection.disabled=true;
            document.frmOfficeDetailRep.cmbsection.selectedIndex=0;
        }*/
    }
    
    
 function loadSubDivision(type)
    {
        //alert(type);
        var cir=document.frmOfficeDetailRep.cmbcircle.options[document.frmOfficeDetailRep.cmbcircle.selectedIndex].value;
        var reg=document.frmOfficeDetailRep.cmbregion.options[document.frmOfficeDetailRep.cmbregion.selectedIndex].value;
        var div=document.frmOfficeDetailRep.cmbdivision.options[document.frmOfficeDetailRep.cmbdivision.selectedIndex].value;
       startwaiting(document.frmOfficeDetailRep) ;
        var url="../../../../../../JobPopupServ.view?Command=SubDivision&cmbregion="+reg+"&cmbcircle=" + cir+"&cmbdivision="+div ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadSubDivisionReq(req);
        }
        req.send(null);
    }
    
    function loadSubDivisionReq(req)
    {
        if(req.readyState==4)
        {
          if(req.status==200)
          {  
           // alert('hello');
           stopwaiting(document.frmOfficeDetailRep) ;
                var cmbsubdivision=document.getElementById("cmbsubdiv");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
                //cmboffices.text="--select Office--";
                if(flag=="failure")
                {
                    alert("No SubDivision exists under this level");
                    document.frmOfficeDetailRep.cmbsubdiv.disabled=true;
                    document.frmOfficeDetailRep.cmbsubdiv.selectedIndex=0;
                    document.frmOfficeDetailRep.cmbsection.disabled=true;
                    document.frmOfficeDetailRep.cmbsection.selectedIndex=0;
                }
                else
                { 
                //alert('success');
                    cmbsubdivision.innerHTML="";
                    document.frmOfficeDetailRep.cmbsubdiv.disabled=false;
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="--Select SubDivision--";
                    try
                    {
                        cmbsubdivision.add(option);
                    }catch(errorObject)
                    {
                    cmbsubdivision.add(option,null);
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
                              cmbsubdivision.add(option);
                          }
                          catch(errorObject)
                          {
                              cmbsubdivision.add(option,null);
                          }
                    }
                }
            }
        }
    }



///////////////////////////////////////////////////////////////////////////////////////

////for SubDivision combo box////////////////////////////////////////////////////////////

function getSection()
    {
        var type=document.frmOfficeDetailRep.cmbsubdiv.options[document.frmOfficeDetailRep.cmbsubdiv.selectedIndex].value;
        //alert('getSubDivi:'+type);
        document.frmOfficeDetailRep.cmbsection.disabled=true;
            document.frmOfficeDetailRep.cmbsection.selectedIndex=0;
        if(type!="")
        {
            var ol=document.frmOfficeDetailRep.cmbolevel.options[document.frmOfficeDetailRep.cmbolevel.selectedIndex].value;
            if(ol=='SD')
            {
                s=type;
                return true;
            }
            loadSection(type);
        }
     /*  else
        {
            document.frmOfficeDetailRep.cmbsection.disabled=true;
            document.frmOfficeDetailRep.cmbsection.selectedIndex=0;
        }*/
    }
    
    
 function loadSection(type)
    {
        //alert(type);
        var cir=document.frmOfficeDetailRep.cmbcircle.options[document.frmOfficeDetailRep.cmbcircle.selectedIndex].value;
        var reg=document.frmOfficeDetailRep.cmbregion.options[document.frmOfficeDetailRep.cmbregion.selectedIndex].value;
        var div=document.frmOfficeDetailRep.cmbdivision.options[document.frmOfficeDetailRep.cmbdivision.selectedIndex].value;
        var sdiv=document.frmOfficeDetailRep.cmbsubdiv.options[document.frmOfficeDetailRep.cmbsubdiv.selectedIndex].value;
       startwaiting(document.frmOfficeDetailRep) ;
        var url="../../../../../../JobPopupServ.view?Command=Section&cmbregion="+reg+"&cmbcircle=" + cir+"&cmbdivision="+div +"&cmbsubdiv="+sdiv;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadSectionReq(req);
        }
        req.send(null);
    }
    
    function loadSectionReq(req)
    {
        if(req.readyState==4)
        {
          if(req.status==200)
          {  
           // alert('hello');
           stopwaiting(document.frmOfficeDetailRep) ;
                var cmbsection=document.getElementById("cmbsection");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
                //cmboffices.text="--select Office--";
                if(flag=="failure")
                {
                    alert("No Section exists under this level");
                     document.frmOfficeDetailRep.cmbsection.disabled=true;
                    document.frmOfficeDetailRep.cmbsection.selectedIndex=0;
                    
                }
                else
                { 
                //alert('success');
                    cmbsection.innerHTML="";
                     document.frmOfficeDetailRep.cmbsection.disabled=false;
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="--Select Section--";
                    try
                    {
                        cmbsection.add(option);
                    }catch(errorObject)
                    {
                    cmbsection.add(option,null);
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
                              cmbsection.add(option);
                          }
                          catch(errorObject)
                          {
                              cmbsection.add(option,null);
                          }
                    }
                }
            }
        }
    }



///////////////////////////////////////////////////////////////////////////////////////
// GET SECTION //////////////////////

function getSection1()
{
 var type=document.frmOfficeDetailRep.cmbdivision.options[document.frmOfficeDetailRep.cmbdivision.selectedIndex].value;
       
       if(type!="")
        {
            var ol=document.frmOfficeDetailRep.cmbolevel.options[document.frmOfficeDetailRep.cmbolevel.selectedIndex].value;
            if(ol=='SN')
            {
                s=type;
                return true;
            }
        }

}


function btnsubmit()
{
s=0;
var type=document.frmOfficeDetailRep.cmbolevel.options[document.frmOfficeDetailRep.cmbolevel.selectedIndex].value;
if(type=='RN')
    s=document.frmOfficeDetailRep.cmbregion.options[document.frmOfficeDetailRep.cmbregion.selectedIndex].value;
if(type=='CL')
    s=document.frmOfficeDetailRep.cmbcircle.options[document.frmOfficeDetailRep.cmbcircle.selectedIndex].value;
if(type=='DN')
    s=document.frmOfficeDetailRep.cmbdivision.options[document.frmOfficeDetailRep.cmbdivision.selectedIndex].value;
if(type=='SD')
    s=document.frmOfficeDetailRep.cmbsubdiv.options[document.frmOfficeDetailRep.cmbsubdiv.selectedIndex].value;
if(type=='SN')
    s=document.frmOfficeDetailRep.cmbsection.options[document.frmOfficeDetailRep.cmbsection.selectedIndex].value;

//alert(s);
if(type=='HO')
{
        Minimize();
        opener.doParentJob(1,'TWAD');
        return true;

}
else
{
    if(s!=0 && s!=null)
    {
        Minimize();
        opener.doParentJob(s,'TWAD');
        return true;
    }
    else
    {
            var msg;
            if(type=='RN')
                msg ="Select upto Region";               
            if(type=='CL')
                msg ="Select upto Circle";  
            if(type=='DN')
                msg ="Select upto Division";  
            if(type=='SD')
                msg ="Select upto SubDivision";  
            if(type=='SN')
                msg ="Select upto Section";  

        alert(msg);
        return false;
    }
}
}

function btncancel()
{

 self.close();
}

function Minimize() 
{
window.resizeTo(0,0);
window.screenX = screen.width;
window.screenY = screen.height;
opener.window.focus();
}


/////////////////////////////////////////////////////////////////////////////////////////////////


//******************************************************************************************//
function activatespecific()
{
         if(document.frmOfficeDetailRep.optoffdetail[1].checked==true)
        {
               document.frmOfficeDetailRep.cmblevel.disabled=false;
                var din=document.getElementById("divoffice");
                din.style.display="none";
        
        }
        else if(document.frmOfficeDetailRep.optoffdetail[2].checked==true)
        {
               document.frmOfficeDetailRep.cmblevel.disabled=true;
               var din=document.getElementById("divoffice");
                din.style.display="block";
               
        }
        else
        {
               document.frmOfficeDetailRep.cmblevel.disabled=true;
               var din=document.getElementById("divoffice");
                din.style.display="none";
        
        }

}


function btnsubmit()
{
        if(document.frmOfficeDetailRep.optoffdetail[2].checked==true)
        {
                s=0;
                var type=document.frmOfficeDetailRep.cmbolevel.options[document.frmOfficeDetailRep.cmbolevel.selectedIndex].value;
                if(type=='RN')
                    s=document.frmOfficeDetailRep.cmbregion.options[document.frmOfficeDetailRep.cmbregion.selectedIndex].value;
                else if(type=='CL')
                    s=document.frmOfficeDetailRep.cmbcircle.options[document.frmOfficeDetailRep.cmbcircle.selectedIndex].value;
                else if(type=='DN')
                    s=document.frmOfficeDetailRep.cmbdivision.options[document.frmOfficeDetailRep.cmbdivision.selectedIndex].value;
                else if(type=='SD')
                    s=document.frmOfficeDetailRep.cmbsubdiv.options[document.frmOfficeDetailRep.cmbsubdiv.selectedIndex].value;
                else if(type=='SN')
                    s=document.frmOfficeDetailRep.cmbsection.options[document.frmOfficeDetailRep.cmbsection.selectedIndex].value;
                
                
                //alert(s);
                if(type=='HO')
                {
                       //alert('ho');
                       document.frmOfficeDetailRep.submit();
                        return true;
                }
                else
                {
                    if(s!=0 && s!=null)
                    {
                        //alert('type ::'+type+"   office id ::"+s);
                        document.frmOfficeDetailRep.submit();
                        return true;
                    }
                    else
                    {
                            var msg;
                            if(type=='RN')
                                msg ="Select upto Region";               
                            else if(type=='CL')
                                msg ="Select upto Circle";  
                            else if(type=='DN')
                                msg ="Select upto Division";  
                            else if(type=='SD')
                                msg ="Select upto SubDivision";  
                            else if(type=='SN')
                                msg ="Select upto Section";  
                
                        alert(msg);
                        return false;
                    }
                }
        }
        else
        {
                document.frmOfficeDetailRep.submit();
        }
}

window.onload=function()
{
document.frmOfficeDetailRep.reset();
}