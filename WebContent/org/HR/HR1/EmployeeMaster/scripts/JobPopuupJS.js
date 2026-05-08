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

 function getRegion()
    {
        var type=document.HRM_JobSearch.cmbolevel.options[document.HRM_JobSearch.cmbolevel.selectedIndex].value;
        //alert('getoffice:'+type);
        if(type!='HO' && type!="")
        {
           if(type=='RN')
           {
                var din=document.getElementById("divregion");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmbregion.style.visibility="visible";
           
           }
           else
           {
            var din=document.getElementById("divcircle");
            din.style.visibility="hidden";
            document.HRM_JobSearch.cmbcircle.style.visibility="hidden";
            
             var din=document.getElementById("divdivision");
            din.style.visibility="hidden";
            document.HRM_JobSearch.cmbdivision.style.visibility="hidden";
            
            var din=document.getElementById("divsubdiv");
            din.style.visibility="hidden";
            document.HRM_JobSearch.cmbsubdiv.style.visibility="hidden";
            
            var din=document.getElementById("divsection");
            din.style.visibility="hidden";
            document.HRM_JobSearch.cmbsection.style.visibility="hidden";
          }  
          
          
          
          if(type=='CL')
           {
                var din=document.getElementById("divregion");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmbregion.style.visibility="visible";
                
                var din=document.getElementById("divcircle");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmbcircle.style.visibility="visible";
                document.HRM_JobSearch.cmbcircle.disabled=true;
                
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
                document.HRM_JobSearch.cmbdivision.style.visibility="hidden";
                
                var din=document.getElementById("divsubdiv");
                din.style.visibility="hidden";
                document.HRM_JobSearch.cmbsubdiv.style.visibility="hidden";
                
                var din=document.getElementById("divsection");
                din.style.visibility="hidden";
                document.HRM_JobSearch.cmbsection.style.visibility="hidden";
          }  
          
           if(type=='DN')
           {
                var din=document.getElementById("divregion");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmbregion.style.visibility="visible";
                
                var din=document.getElementById("divcircle");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmbcircle.style.visibility="visible";
                document.HRM_JobSearch.cmbcircle.disabled=true;
                
                var din=document.getElementById("divdivision");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmbdivision.style.visibility="visible";
                document.HRM_JobSearch.cmbdivision.disabled=true;
                
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
                document.HRM_JobSearch.cmbsubdiv.style.visibility="hidden";
                
                var din=document.getElementById("divsection");
                din.style.visibility="hidden";
                document.HRM_JobSearch.cmbsection.style.visibility="hidden";
          }  
          
           if(type=='SD')
           {
                var din=document.getElementById("divregion");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmbregion.style.visibility="visible";
                
                var din=document.getElementById("divcircle");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmbcircle.style.visibility="visible";
                document.HRM_JobSearch.cmbcircle.disabled=true;
                
                var din=document.getElementById("divdivision");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmbdivision.style.visibility="visible";
                document.HRM_JobSearch.cmbdivision.disabled=true;
                
                var din=document.getElementById("divsubdiv");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmbsubdiv.style.visibility="visible";
                document.HRM_JobSearch.cmbsubdiv.disabled=true;
                
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
                document.HRM_JobSearch.cmbsection.style.visibility="hidden";
          }  
          
           if(type=='SN')
           {
                var din=document.getElementById("divregion");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmbregion.style.visibility="visible";
                
                var din=document.getElementById("divcircle");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmbcircle.style.visibility="visible";
                document.HRM_JobSearch.cmbcircle.disabled=true;
                
                var din=document.getElementById("divdivision");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmbdivision.style.visibility="visible";
                document.HRM_JobSearch.cmbdivision.disabled=true;
                
                var din=document.getElementById("divsubdiv");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmbsubdiv.style.visibility="visible";
                document.HRM_JobSearch.cmbsubdiv.disabled=true;
                
                  var din=document.getElementById("divsection");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmbsection.style.visibility="visible";
                document.HRM_JobSearch.cmbsection.disabled=true;
                
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
            document.HRM_JobSearch.cmbregion.style.visibility="hidden";
            
            var din=document.getElementById("divcircle");
            din.style.visibility="hidden";
            document.HRM_JobSearch.cmbcircle.style.visibility="hidden";
            
             var din=document.getElementById("divdivision");
            din.style.visibility="hidden";
            document.HRM_JobSearch.cmbdivision.style.visibility="hidden";
            
            var din=document.getElementById("divsubdiv");
            din.style.visibility="hidden";
            document.HRM_JobSearch.cmbsubdiv.style.visibility="hidden";
            
            var din=document.getElementById("divsection");
            din.style.visibility="hidden";
            document.HRM_JobSearch.cmbsection.style.visibility="hidden";
            
            return true;
        }
    }
    
    
 function loadRegion(type)
    {
        //alert(type);
        var type=document.HRM_JobSearch.cmbolevel.options[document.HRM_JobSearch.cmbolevel.selectedIndex].value;
       var url="../../../../../JobPopupServ.view?Command=OLevel&cmbolevel=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadRegionReq(req);
        }
          if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
    
    function loadRegionReq(req)
    {
        if(req.readyState==4)
        {
          if(req.status==200)
          {  
                //alert('hello');
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
                    document.HRM_JobSearch.cmbcircle.style.visibility="hidden";
                    
                     var din=document.getElementById("divdivision");
                    din.style.visibility="hidden";
                    document.HRM_JobSearch.cmbdivision.style.visibility="hidden";
                    
                    var din=document.getElementById("divsubdiv");
                    din.style.visibility="hidden";
                    document.HRM_JobSearch.cmbsubdiv.style.visibility="hidden";
                    
                    var din=document.getElementById("divsection");
                    din.style.visibility="hidden";
                    document.HRM_JobSearch.cmbsection.style.visibility="hidden";
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
                              cmbrigion.add(option,null);
                          }
                    }
                }
            }
        }
    }
    
    
////for region combo box////////////////////////////////////////////////////////////

function getCircle()
    {
        var type=document.HRM_JobSearch.cmbregion.options[document.HRM_JobSearch.cmbregion.selectedIndex].value;
        //alert('region:'+type);
        if(type!="")
        {
            var ol=document.HRM_JobSearch.cmbolevel.options[document.HRM_JobSearch.cmbolevel.selectedIndex].value;
            if(ol=='RN')
            {
                s=type;
                return true;
            }
            
            loadCircle(type);
        }
        else
        {
            document.HRM_JobSearch.cmbcircle.disabled=true;
            document.HRM_JobSearch.cmbcircle.selectedIndex=0;
            document.HRM_JobSearch.cmbdivision.disabled=true;
            document.HRM_JobSearch.cmbdivision.selectedIndex=0;
            document.HRM_JobSearch.cmbsubdiv.disabled=true;
            document.HRM_JobSearch.cmbsubdiv.selectedIndex=0;
            document.HRM_JobSearch.cmbsection.disabled=true;
            document.HRM_JobSearch.cmbsection.selectedIndex=0;
        }
       
    }
    
    
 function loadCircle(type)
    {
        //alert(type);
        var type=document.HRM_JobSearch.cmbregion.options[document.HRM_JobSearch.cmbregion.selectedIndex].value;
        var url="../../../../../JobPopupServ.view?Command=Region&cmbregion=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadCircleReq(req);
        }
          if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
    
    function loadCircleReq(req)
    {
        if(req.readyState==4)
        {
          if(req.status==200)
          {  
            //alert('hello');
                
                var cmbcircle=document.getElementById("cmbcircle");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
                //cmboffices.text="--select Office--";
                if(flag=="failure")
                {
                    alert("No Circle exists under this level");
                    document.HRM_JobSearch.cmbcircle.disabled=true;
                    document.HRM_JobSearch.cmbcircle.selectedIndex=0;
                    document.HRM_JobSearch.cmbdivision.disabled=true;
                    document.HRM_JobSearch.cmbdivision.selectedIndex=0;
                    document.HRM_JobSearch.cmbsubdiv.disabled=true;
                    document.HRM_JobSearch.cmbsubdiv.selectedIndex=0;
                    document.HRM_JobSearch.cmbsection.disabled=true;
                    document.HRM_JobSearch.cmbsection.selectedIndex=0;
                }
                else
                { 
                    //alert('success');
                    document.HRM_JobSearch.cmbcircle.disabled=false;
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
        var type=document.HRM_JobSearch.cmbcircle.options[document.HRM_JobSearch.cmbcircle.selectedIndex].value;
        //alert('circle:'+type);
        if(type!="")
        {
            var ol=document.HRM_JobSearch.cmbolevel.options[document.HRM_JobSearch.cmbolevel.selectedIndex].value;
            if(ol=='CL')
            {
                s=type;
                return true;
            }
            loadDivision(type);
        }
        else
        {
            document.HRM_JobSearch.cmbdivision.disabled=true;
            document.HRM_JobSearch.cmbdivision.selectedIndex=0;
            document.HRM_JobSearch.cmbsubdiv.disabled=true;
            document.HRM_JobSearch.cmbsubdiv.selectedIndex=0;
            document.HRM_JobSearch.cmbsection.disabled=true;
            document.HRM_JobSearch.cmbsection.selectedIndex=0;
        }
    }
    
    
 function loadDivision(type)
    {
        //alert(type);
        var cir=document.HRM_JobSearch.cmbcircle.options[document.HRM_JobSearch.cmbcircle.selectedIndex].value;
         var reg=document.HRM_JobSearch.cmbregion.options[document.HRM_JobSearch.cmbregion.selectedIndex].value;
       
        var url="../../../../../JobPopupServ.view?Command=Division&cmbregion="+reg+"&cmbcircle=" + cir ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadDivisionReq(req);
        }
         if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
    
    function loadDivisionReq(req)
    {
        if(req.readyState==4)
        {
          if(req.status==200)
          {  
            
                var cmbdivision=document.getElementById("cmbdivision");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
                //cmboffices.text="--select Office--";
                if(flag=="failure")
                {
                    alert("No Division/Lab exists under this level");
                    document.HRM_JobSearch.cmbdivision.disabled=true;
                    document.HRM_JobSearch.cmbdivision.selectedIndex=0;
                    document.HRM_JobSearch.cmbsubdiv.disabled=true;
                    document.HRM_JobSearch.cmbsubdiv.selectedIndex=0;
                    document.HRM_JobSearch.cmbsection.disabled=true;
                    document.HRM_JobSearch.cmbsection.selectedIndex=0;
                    
                }
                else
                { 
                    // alert('success');
                    cmbdivision.innerHTML="";
                    document.HRM_JobSearch.cmbdivision.disabled=false;
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
        var type=document.HRM_JobSearch.cmbdivision.options[document.HRM_JobSearch.cmbdivision.selectedIndex].value;
        //alert('Divi:'+type);
        if(type!="")
        {
            var ol=document.HRM_JobSearch.cmbolevel.options[document.HRM_JobSearch.cmbolevel.selectedIndex].value;
            if(ol=='DN' || ol=='LB')
            {
                s=type;
                return true;
            }
            
            
            loadSubDivision(type);
        }
        else
        {
           document.HRM_JobSearch.cmbsubdiv.disabled=true;
            document.HRM_JobSearch.cmbsubdiv.selectedIndex=0;
            document.HRM_JobSearch.cmbsection.disabled=true;
            document.HRM_JobSearch.cmbsection.selectedIndex=0;
        }
    }
    
    
 function loadSubDivision(type)
    {
        //alert(type);
        var cir=document.HRM_JobSearch.cmbcircle.options[document.HRM_JobSearch.cmbcircle.selectedIndex].value;
        var reg=document.HRM_JobSearch.cmbregion.options[document.HRM_JobSearch.cmbregion.selectedIndex].value;
        var div=document.HRM_JobSearch.cmbdivision.options[document.HRM_JobSearch.cmbdivision.selectedIndex].value;
       
        var url="../../../../../JobPopupServ.view?Command=SubDivision&cmbregion="+reg+"&cmbcircle=" + cir+"&cmbdivision="+div ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadSubDivisionReq(req);
        }
         if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
    
    function loadSubDivisionReq(req)
    {
        if(req.readyState==4)
        {
          if(req.status==200)
          {  
           // alert('hello');
                var cmbsubdivision=document.getElementById("cmbsubdiv");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
                //cmboffices.text="--select Office--";
                if(flag=="failure")
                {
                    alert("No SubDivision exists under this level");
                    document.HRM_JobSearch.cmbsubdiv.disabled=true;
                    document.HRM_JobSearch.cmbsubdiv.selectedIndex=0;
                    document.HRM_JobSearch.cmbsection.disabled=true;
                    document.HRM_JobSearch.cmbsection.selectedIndex=0;
                }
                else
                { 
                //alert('success');
                    cmbsubdivision.innerHTML="";
                    document.HRM_JobSearch.cmbsubdiv.disabled=false;
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
        var type=document.HRM_JobSearch.cmbsubdiv.options[document.HRM_JobSearch.cmbsubdiv.selectedIndex].value;
        //alert('getSubDivi:'+type);
        if(type!="")
        {
            var ol=document.HRM_JobSearch.cmbolevel.options[document.HRM_JobSearch.cmbolevel.selectedIndex].value;
            if(ol=='SD')
            {
                s=type;
                return true;
            }
            loadSection(type);
        }
        else
        {
            document.HRM_JobSearch.cmbsection.disabled=true;
            document.HRM_JobSearch.cmbsection.selectedIndex=0;
        }
    }
    
    
 function loadSection(type)
    {
        //alert(type);
        var cir=document.HRM_JobSearch.cmbcircle.options[document.HRM_JobSearch.cmbcircle.selectedIndex].value;
        var reg=document.HRM_JobSearch.cmbregion.options[document.HRM_JobSearch.cmbregion.selectedIndex].value;
        var div=document.HRM_JobSearch.cmbdivision.options[document.HRM_JobSearch.cmbdivision.selectedIndex].value;
        var sdiv=document.HRM_JobSearch.cmbsubdiv.options[document.HRM_JobSearch.cmbsubdiv.selectedIndex].value;
       
        var url="../../../../../JobPopupServ.view?Command=Section&cmbregion="+reg+"&cmbcircle=" + cir+"&cmbdivision="+div +"&cmbsubdiv="+sdiv;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadSectionReq(req);
        }
         if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
    
    function loadSectionReq(req)
    {
        if(req.readyState==4)
        {
          if(req.status==200)
          {  
           // alert('hello');
                var cmbsection=document.getElementById("cmbsection");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
                //cmboffices.text="--select Office--";
                if(flag=="failure")
                {
                    alert("No Section exists under this level");
                     document.HRM_JobSearch.cmbsection.disabled=true;
                    document.HRM_JobSearch.cmbsection.selectedIndex=0;
                    
                }
                else
                { 
                //alert('success');
                    cmbsection.innerHTML="";
                     document.HRM_JobSearch.cmbsection.disabled=false;
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
 var type=document.HRM_JobSearch.cmbdivision.options[document.HRM_JobSearch.cmbdivision.selectedIndex].value;
       
       if(type!="")
        {
            var ol=document.HRM_JobSearch.cmbolevel.options[document.HRM_JobSearch.cmbolevel.selectedIndex].value;
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
var type=document.HRM_JobSearch.cmbolevel.options[document.HRM_JobSearch.cmbolevel.selectedIndex].value;
if(type=='RN')
    s=document.HRM_JobSearch.cmbregion.options[document.HRM_JobSearch.cmbregion.selectedIndex].value;
if(type=='CL')
    s=document.HRM_JobSearch.cmbcircle.options[document.HRM_JobSearch.cmbcircle.selectedIndex].value;
if(type=='DN')
    s=document.HRM_JobSearch.cmbdivision.options[document.HRM_JobSearch.cmbdivision.selectedIndex].value;
if(type=='SD')
    s=document.HRM_JobSearch.cmbsubdiv.options[document.HRM_JobSearch.cmbsubdiv.selectedIndex].value;
if(type=='SN')
    s=document.HRM_JobSearch.cmbsection.options[document.HRM_JobSearch.cmbsection.selectedIndex].value;

//alert(s);
if(type=='HO')
{
 opener.document.HRE_EmployeeServiceDetails.txtOffice_Id.value = 1;
        self.opener.callServer1('Load','null');
        self.close();

}
else
{
    if(s!=0 && s!=null)
    {
        opener.document.HRE_EmployeeServiceDetails.txtOffice_Id.value = s;
        self.opener.callServer1('Load','null');
        self.close();
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