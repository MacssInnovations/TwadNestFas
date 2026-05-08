// code for selecting the controlling office
            ///888888888888888888888888888888888888888888
            
            
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

    function getAttachOfficesByType(frmName)
    {
        var type=eval("document." + frmName + ".cmbAttachOfficeType.options[document." + frmName + ".cmbAttachOfficeType.selectedIndex].value");                    
        if(type!=0)
        {
            var din=document.getElementById("divType4");
            din.style.visibility="visible";
            document.frmOffice.cmbSelectAttachOffice.style.visibility="visible";
            loadAttachOfficesByType(type,frmName);
        }
    }
    
    function getAttachOfficesByLevel(frmName)
    {
                
        var level=eval("document." + frmName + ".cmbAttachedOfficeLevel.options[document." + frmName + ".cmbAttachedOfficeLevel.selectedIndex].value");                    
        var levelt=eval("document." + frmName + ".cmbAttachedOfficeLevel.options[document." + frmName +  ".cmbAttachedOfficeLevel.selectedIndex].text");        
        if(levelt=="Division" || levelt=="Sub-Division" || levelt=="Section" )
        {
            var din=document.getElementById("divType3");
            din.style.visibility="visible";
            eval("document." + frmName+".cmbAttachOfficeType.style.visibility='visible'");
            var din=document.getElementById("divType4");
            din.style.visibility="hidden";
            document.frmOffice.cmbSelectAttachOffice.style.visibility="hidden";
            try
            {
            eval("document." +frmName+".cmbAttachOfficeType.focus()");
            eval("document." +frmName+".cmbAttachOfficeType.select()");
            }catch(e){}
        }
        else
        {
            var din=document.getElementById("divType3");
            din.style.visibility="hidden";
            eval("document." +frmName+".cmbAttachOfficeType.style.visibility='hidden'");
            var din=document.getElementById("divType4");
            din.style.visibility="visible";
            document.frmOffice.cmbSelectAttachOffice.style.visibility="visible";
            if(level!="----Select OfficeLevel----")
            {
                loadAttachOfficesByLevel(level);
            }
        }
    
    }
    
    function loadAttachOfficesByLevel(level)
    {
        var officeid=document.frmOffice.txtOffice_Id.value;
        //alert(officeid);
        var url="../../../../../ServletGetOfficesByTypeOrLevel1.con?command=level&level="+level+"&OfficeId="+officeid;
        var req=getTransport();
        //alert(url);
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            loadAttachOffices(req);
        }
        req.send(null);
    }
    
    function loadAttachOfficesByType(type,frmName)
    {
        var officeid=document.frmOffice.txtOffice_Id.value;
        var level=eval("document." + frmName + ".cmbAttachedOfficeLevel.options[document." + frmName + ".cmbAttachedOfficeLevel.selectedIndex].value");                    
        var url="../../../../../ServletGetOfficesByTypeOrLevel1.con?command=type&level=" + level + "&type="+type+"&OfficeId="+officeid;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            loadAttachOffices(req);
        }
        req.send(null);
    }
    
    function loadAttachOffices(req)
    {
        if(req.readyState==4)
        {
          if(req.status==200)
          {  
                var cmboffices=document.getElementById("cmbSelectAttachOffice");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                cmboffices.innerHTML="";
                var optionf=document.createElement("OPTION");
                optionf.text="----select an Office----";
                optionf.value="0";
                try
                {
                    cmboffices.add(optionf);
                }
                catch(errorObject)
                {
                    cmboffices.add(optionf,null);
                }
                
                if(flag=="failure")
                {
                    alert("No Offices exists under this level");
                }
                else
                {                    
                    var value=response.getElementsByTagName("options");
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
    
    function selectAttachOffice(frmName,txtBoxName)
    {
        var ctlOffice=eval("document." + frmName + ".cmbSelectAttachOffice.options[document." + frmName + ".cmbSelectAttachOffice.selectedIndex].value");        
        if(ctlOffice!=0)
        {
            eval("document." + frmName + "." + txtBoxName + ".value=ctlOffice");
        }        
        document.frmOffice.txtAttachedOfficeID.focus();
        callServer2("Attach","null");
    }
