   
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

    function getOfficesByType()
    {
        var type=document.HRE_EmployeeServiceDetails.cmbOfficeType.options[document.HRE_EmployeeServiceDetails.cmbOfficeType.selectedIndex].value;
        if(type!=0)
        {
            var din=document.getElementById("divType2");
            din.style.visibility="visible";
            document.HRE_EmployeeServiceDetails.cmbSelectOffice.style.visibility="visible";
            loadOfficesByType(type);
        }
    }
  
    function getOfficesByLevel()
    {
        var level=document.HRE_EmployeeServiceDetails.cmbControllingLevel.options[document.HRE_EmployeeServiceDetails.cmbControllingLevel.selectedIndex].value;
        var levelt=document.HRE_EmployeeServiceDetails.cmbControllingLevel.options[document.HRE_EmployeeServiceDetails.cmbControllingLevel.selectedIndex].text;
        if(levelt=="Division" || levelt=="Sub-Division" || levelt=="Section" )
        {
            var din=document.getElementById("divType1");
            din.style.visibility="visible";
            document.HRE_EmployeeServiceDetails.cmbOfficeType.style.visibility="visible";
            var din=document.getElementById("divType2");
            din.style.visibility="hidden";
            document.HRE_EmployeeServiceDetails.cmbSelectOffice.style.visibility="hidden";
            try
            {
            document.HRE_EmployeeServiceDetails.cmbOfficeType.focus();
            document.HRE_EmployeeServiceDetails.cmbOfficeType.select();
            }catch(e){}
        }
        else
        {
        
            var din=document.getElementById("divType1");
            din.style.visibility="hidden";
            document.HRE_EmployeeServiceDetails.cmbOfficeType.style.visibility="hidden";
            
            var din=document.getElementById("divType2");
            din.style.visibility="visible";
            document.HRE_EmployeeServiceDetails.cmbSelectOffice.style.visibility="visible";
            if(level!="----Select OfficeLevel----")
            {
                loadOfficesByLevel(level);
            }
        }
    }
 
    function loadOfficesByLevel(level)
    {
        var url="../../../../../ServletGetOfficesByTypeOrLevel.con?command=level&level="+level;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            loadOffices(req);
        }
          if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
  
    function loadOfficesByType(type)
    {
        //alert(type);
        var level=document.HRE_EmployeeServiceDetails.cmbControllingLevel.options[document.HRE_EmployeeServiceDetails.cmbControllingLevel.selectedIndex].value;
        var url="../../../../../ServletGetOfficesByTypeOrLevel.con?command=type&level=" + level + "&type="+type;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            loadOffices(req);
        }
         if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
 
    function loadOffices(req)
    {
        if(req.readyState==4)
        {
          if(req.status==200)
          {  
                var cmboffices=document.getElementById("cmbSelectOffice");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                cmboffices.innerHTML="";
                //cmboffices.text="--select Office--";
                else if(flag=="sessionout")
            {
                alert('Session is closed');
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                self.close();
                return;
            }
                if(flag=="failure")
                {
                    alert("No Offices exists under this level");
                }
                else
                {                    
                    var value=response.getElementsByTagName("options");
                    var option=document.createElement("OPTION");
                    option.text="--Select Office--";
                    try
                    {
                        cmboffices.add(option);
                    }catch(errorObject)
                    {
                    cmboffices.add(option,null);
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
                              cmboffices.add(option);
                          }
                          catch(errorObject)
                          {
                              cmboffices.add(option,null);
                          }
                    }
                }
            }
        }
    }
 
    function selectControllineOffice(type)
    {
    var ctlOffice=document.HRE_EmployeeServiceDetails.cmbSelectOffice.options[document.HRE_EmployeeServiceDetails.cmbSelectOffice.selectedIndex].value;
    //alert(ctlOffice);
        if(type=="controlling office")
        {
        
        
        if(ctlOffice!=0)
        {
            document.HRE_EmployeeServiceDetails.txtContrllingOfficeID.value=ctlOffice;
        }
        }
        else
        {
           document.HRE_EmployeeServiceDetails.txtOffice_Id.value=ctlOffice;
           document.HRE_EmployeeServiceDetails.txtOffice_Name.value=document.HRE_EmployeeServiceDetails.cmbSelectOffice.options[document.HRE_EmployeeServiceDetails.cmbSelectOffice.selectedIndex].text;
           callServer1("Load","null");
        }
    }