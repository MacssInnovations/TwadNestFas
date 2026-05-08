//alert('hello');   

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
        var type=document.HRM_JobSearch.cmbOfficeType.options[document.HRM_JobSearch.cmbOfficeType.selectedIndex].value;
        if(type!=0)
        {
            var din=document.getElementById("divType2");
            din.style.visibility="visible";
            document.HRM_JobSearch.cmbSelectOffice.style.visibility="visible";
            loadOfficesByType(type);
        }
    }
  
    function getOfficesByLevel()
    {
        var level=document.HRM_JobSearch.cmbControllingLevel.options[document.HRM_JobSearch.cmbControllingLevel.selectedIndex].value;
        var levelt=document.HRM_JobSearch.cmbControllingLevel.options[document.HRM_JobSearch.cmbControllingLevel.selectedIndex].text;
        if(levelt=="Division" || levelt=="Sub-Division" || levelt=="Section" )
        {
            var din=document.getElementById("divType1");
            din.style.visibility="visible";
            document.HRM_JobSearch.cmbOfficeType.style.visibility="visible";
            var din=document.getElementById("divType2");
            din.style.visibility="hidden";
            document.HRM_JobSearch.cmbSelectOffice.style.visibility="hidden";
            try
            {
            document.HRM_JobSearch.cmbOfficeType.focus();
            document.HRM_JobSearch.cmbOfficeType.select();
            }catch(e){}
        }
        else
        {
        
            var din=document.getElementById("divType1");
            din.style.visibility="hidden";
            document.HRM_JobSearch.cmbOfficeType.style.visibility="hidden";
            
            var din=document.getElementById("divType2");
            din.style.visibility="visible";
            document.HRM_JobSearch.cmbSelectOffice.style.visibility="visible";
            if(level!="----Select OfficeLevel----")
            {
                loadOfficesByLevel(level);
            }
        }
    }
 
    function loadOfficesByLevel(level)
    {
      //var offid=document.HRM_JobSearch.offid.value;
        var url="../../../../../ServletGetOfficesByTypeOrLevel.con?command=level&level="+level;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            loadOffices(req);
        }
        req.send(null);
    }
  
    function loadOfficesByType(type)
    {
        //alert(type);
        var level=document.HRM_JobSearch.cmbControllingLevel.options[document.HRM_JobSearch.cmbControllingLevel.selectedIndex].value;
          //var offid=document.HRM_JobSearch.offid.value;
        var url="../../../../../ServletGetOfficesByTypeOrLevel.con?command=type&level=" + level + "&type="+type;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            loadOffices(req);
        }
        req.send(null);
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
    var ctlOffice=document.HRM_JobSearch.cmbSelectOffice.options[document.HRM_JobSearch.cmbSelectOffice.selectedIndex].value;
    //alert(ctlOffice);
        if(type=="controlling office")
        {
        
        
        if(ctlOffice!=0)
        {
            document.HRM_JobSearch.txtContrllingOfficeID.value=ctlOffice;
        }
        }
        else
        {
           //document.HRM_JobSearch.txtOffice_Id.value=ctlOffice;
           //document.HRM_JobSearch.txtOffice_Name.value=document.HRM_JobSearch.cmbSelectOffice.options[document.HRM_JobSearch.cmbSelectOffice.selectedIndex].text;
           //callServer1("Load","null");
             Minimize();
            opener.doParentJob(ctlOffice,'TWAD');
             return true;
          // alert('ok');
        }
    }
    
    
    function Minimize() 
{
window.resizeTo(0,0);
window.screenX = screen.width;
window.screenY = screen.height;
opener.window.focus();
}