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
var offid=0;

function officeSelection(h,l,c,o)
{
    var opt1=document.getElementById("opthier");
    var opt2=document.getElementById("optlevel");
    var opt3=document.getElementById("optcity");
    var opt4=document.getElementById("optother");
   
    opt1.disabled=!h;
    opt2.disabled=!l;
    opt3.disabled=!c;
    opt4.disabled=!o;

    

}

function officeSelection(h,l,c,o,cl)
{
    var opt1=document.getElementById("opthier");
    var opt2=document.getElementById("optlevel");
    var opt3=document.getElementById("optcity");
    var opt4=document.getElementById("optother");
    var opt5=document.getElementById("optcoffice");
   
    opt1.disabled=!h;
    opt2.disabled=!l;
    opt3.disabled=!c;
    opt4.disabled=!o;
    opt5.disabled=!cl;

    

}



function funhierarchical()
{
        //alert('hier');
        var din=document.getElementById("divhier");
        din.style.display="block";
        
        var din=document.getElementById("divofflevel");
        din.style.display="none";
        
        var din=document.getElementById("divcity");
        din.style.display="none";
        
        var din=document.getElementById("divodept");
        din.style.display="none";
        
        var din=document.getElementById("divCOffice");
        din.style.display="none";

}


function funofflevel()
{
       // alert('office level');
       var din=document.getElementById("divhier");
        din.style.display="none";
        
        var din=document.getElementById("divofflevel");
        din.style.display="block";
        
         var din=document.getElementById("divcity");
        din.style.display="none";
        
        var din=document.getElementById("divodept");
        din.style.display="none";
        
        var din=document.getElementById("divCOffice");
        din.style.display="none";


}

function funcitytown()
{
       // alert('office level');
       var din=document.getElementById("divhier");
        din.style.display="none";
        
        var din=document.getElementById("divofflevel");
        din.style.display="none";
        
        var din=document.getElementById("divcity");
        din.style.display="block";
        
        var din=document.getElementById("divodept");
        din.style.display="none";
        
        var din=document.getElementById("divCOffice");
        din.style.display="none";


}


function funotherdept()
{
       // alert('office level');
       var din=document.getElementById("divhier");
        din.style.display="none";
        
        var din=document.getElementById("divofflevel");
        din.style.display="none";
        
        var din=document.getElementById("divcity");
        din.style.display="none";
        
        var din=document.getElementById("divodept");
        din.style.display="block";
        
        var din=document.getElementById("divCOffice");
        din.style.display="none";


}

function funclosedoffice()
{
        var din=document.getElementById("divhier");
        din.style.display="none";
        
        var din=document.getElementById("divofflevel");
        din.style.display="none";
        
        var din=document.getElementById("divcity");
        din.style.display="none";
        
        var din=document.getElementById("divodept");
        din.style.display="none";
        
        var din=document.getElementById("divCOffice");
        din.style.display="block";

}

 function getRegion()
    {
        var type=document.HRM_JobSearch.cmbolevel.options[document.HRM_JobSearch.cmbolevel.selectedIndex].value;
         
        if(type!='HO' && type!="")
        {
           if(type=='RN')
           {
                var din=document.getElementById("divregion");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmbregion.style.visibility="visible";
                
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
                
                 var din=document.getElementById("divlab");
                din.style.visibility="hidden";
                document.HRM_JobSearch.cmblab.style.visibility="hidden";
                
                 var din=document.getElementById("divsubdiv");
            din.style.visibility="hidden";
            document.HRM_JobSearch.cmbsubdiv.style.visibility="hidden";
                
                 var din=document.getElementById("divdivision");
                din.style.visibility="hidden";
                document.HRM_JobSearch.cmbdivision.style.visibility="hidden";
                  
                 var din=document.getElementById("divaudit");
                din.style.visibility="hidden";
                document.HRM_JobSearch.cmbaudit.style.visibility="hidden";
             
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
                
                 var din=document.getElementById("divaudit");
                din.style.visibility="hidden";
                document.HRM_JobSearch.cmbaudit.style.visibility="hidden";
                
                var din=document.getElementById("divsubdiv");
                din.style.visibility="hidden";
                document.HRM_JobSearch.cmbsubdiv.style.visibility="hidden";
                
                var din=document.getElementById("divsection");
                din.style.visibility="hidden";
                document.HRM_JobSearch.cmbsection.style.visibility="hidden";
          }  
          
          if(type=='AW')
           {
                var din=document.getElementById("divregion");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmbregion.style.visibility="visible";
                
                 var din=document.getElementById("divcircle");
                din.style.visibility="hidden";
                document.HRM_JobSearch.cmbcircle.style.visibility="hidden";
                
                  var din=document.getElementById("divlab");
                din.style.visibility="hidden";
                document.HRM_JobSearch.cmblab.style.visibility="hidden";
                
                 var din=document.getElementById("divdivision");
                din.style.visibility="hidden";
                document.HRM_JobSearch.cmbdivision.style.visibility="hidden";
                
                 var din=document.getElementById("divsubdiv");
            din.style.visibility="hidden";
            document.HRM_JobSearch.cmbsubdiv.style.visibility="hidden";
                
                var din=document.getElementById("divaudit");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmbaudit.style.visibility="visible";
                document.HRM_JobSearch.cmbaudit.disabled=true;
                
                var cmbaudit=document.getElementById("cmbaudit");
                cmbaudit.innerHTML="";
                var option=document.createElement("OPTION");
                option.text="--Select Audit Wing--";
                try
                {
                    cmbaudit.add(option);
                }catch(errorObject)
                {
                cmbaudit.add(option,null);
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
                
                  var din=document.getElementById("divsubdiv");
            din.style.visibility="hidden";
            document.HRM_JobSearch.cmbsubdiv.style.visibility="hidden"; 
                  
                var din=document.getElementById("divlab");
                din.style.visibility="hidden";
                document.HRM_JobSearch.cmblab.style.visibility="hidden";
                
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
          
           if(type=='LB')
           {
                var din=document.getElementById("divregion");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmbregion.style.visibility="visible";
                
                var din=document.getElementById("divcircle");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmbcircle.style.visibility="visible";
                document.HRM_JobSearch.cmbcircle.disabled=true;
                
                 var din=document.getElementById("divsubdiv");
            din.style.visibility="hidden";
            document.HRM_JobSearch.cmbsubdiv.style.visibility="hidden";
                  
                 var din=document.getElementById("divdivision");
                din.style.visibility="hidden";
                document.HRM_JobSearch.cmbdivision.style.visibility="hidden";
                
                var din=document.getElementById("divlab");
                din.style.visibility="visible";
                document.HRM_JobSearch.cmblab.style.visibility="visible";
                document.HRM_JobSearch.cmblab.disabled=true;
                
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
                
                var cmb=document.getElementById("cmblab");
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
                
                 var din=document.getElementById("divlab");
                din.style.visibility="hidden";
                document.HRM_JobSearch.cmblab.style.visibility="hidden";
                
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
                loadOfficeId(type);
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
    
    function loadOfficeId(type)
    {
        //alert(type);
        startwaiting(document.HRM_JobSearch) ;
        var type=document.HRM_JobSearch.cmbolevel.options[document.HRM_JobSearch.cmbolevel.selectedIndex].value;
       var url="../../../../../JobPopupServ_RLV.view?Command=OHOLevel&cmbolevel=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadHOReq(req);
        }
        req.send(null);
    }
    
    function loadHOReq(req)
    {
        if(req.readyState==4)
        {
          if(req.status==200)
          {  
                 stopwaiting(document.HRM_JobSearch);
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="failure")
                {
                    
                    alert("No Head Office exists under this level");
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
                }
                else
                {
                        var id=response.getElementsByTagName("id")[0].firstChild.nodeValue;
                       offid=id;
                }
          }
          
        }
    }
    
    
 function loadRegion(type)
    {
        //alert(type);
        startwaiting(document.HRM_JobSearch) ;
        var type=document.HRM_JobSearch.cmbolevel.options[document.HRM_JobSearch.cmbolevel.selectedIndex].value;
        var offid=document.HRM_JobSearch.offid.value;
        //alert(offid);
       var url="../../../../../JobPopupServ_RLV.view?Command=OLevel&cmbolevel=" + type+"&offid="+offid ;
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
                 stopwaiting(document.HRM_JobSearch);
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
        var type=document.HRM_JobSearch.cmbregion.options[document.HRM_JobSearch.cmbregion.selectedIndex].value;
        //alert('region:'+type);
            document.HRM_JobSearch.cmbcircle.disabled=true;
            document.HRM_JobSearch.cmbcircle.selectedIndex=0;
            document.HRM_JobSearch.cmbdivision.disabled=true;
            document.HRM_JobSearch.cmbdivision.selectedIndex=0;
            document.HRM_JobSearch.cmbaudit.disabled=true;
            document.HRM_JobSearch.cmbaudit.selectedIndex=0;
            document.HRM_JobSearch.cmbsubdiv.disabled=true;
            document.HRM_JobSearch.cmbsubdiv.selectedIndex=0;
            document.HRM_JobSearch.cmbsection.disabled=true;
            document.HRM_JobSearch.cmbsection.selectedIndex=0;
            
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
      /*  else
        {
            document.HRM_JobSearch.cmbcircle.disabled=true;
            document.HRM_JobSearch.cmbcircle.selectedIndex=0;
            document.HRM_JobSearch.cmbdivision.disabled=true;
            document.HRM_JobSearch.cmbdivision.selectedIndex=0;
            document.HRM_JobSearch.cmbsubdiv.disabled=true;
            document.HRM_JobSearch.cmbsubdiv.selectedIndex=0;
            document.HRM_JobSearch.cmbsection.disabled=true;
            document.HRM_JobSearch.cmbsection.selectedIndex=0;
        }*/
       
    }
    
    
 function loadCircle(type)
    {
        //alert(type);
        startwaiting(document.HRM_JobSearch) ;
        var type=document.HRM_JobSearch.cmbregion.options[document.HRM_JobSearch.cmbregion.selectedIndex].value;
        var url="../../../../../JobPopupServ_RLV.view?Command=Region&cmbregion=" + type ;
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
                 stopwaiting(document.HRM_JobSearch);
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
                    document.HRM_JobSearch.cmbaudit.disabled=true;
                    document.HRM_JobSearch.cmbaudit.selectedIndex=0;
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


function getAudit()
    {
        var type=document.HRM_JobSearch.cmbregion.options[document.HRM_JobSearch.cmbregion.selectedIndex].value;
        //alert('region:'+type);
            document.HRM_JobSearch.cmbaudit.disabled=true;
            document.HRM_JobSearch.cmbaudit.selectedIndex=0;
              document.HRM_JobSearch.cmbdivision.disabled=true;
            document.HRM_JobSearch.cmbdivision.selectedIndex=0;
            document.HRM_JobSearch.cmbsubdiv.disabled=true;
            document.HRM_JobSearch.cmbsubdiv.selectedIndex=0;
            document.HRM_JobSearch.cmbsection.disabled=true;
            document.HRM_JobSearch.cmbsection.selectedIndex=0;
                      
        if(type!="")
        {
            var ol=document.HRM_JobSearch.cmbolevel.options[document.HRM_JobSearch.cmbolevel.selectedIndex].value;
            if(ol=='RN')
            {
                s=type;
                return true;
            }
            
            loadAudit(type);
        }
      /*  else
        {
            document.HRM_JobSearch.cmbcircle.disabled=true;
            document.HRM_JobSearch.cmbcircle.selectedIndex=0;
            document.HRM_JobSearch.cmbdivision.disabled=true;
            document.HRM_JobSearch.cmbdivision.selectedIndex=0;
            document.HRM_JobSearch.cmbsubdiv.disabled=true;
            document.HRM_JobSearch.cmbsubdiv.selectedIndex=0;
            document.HRM_JobSearch.cmbsection.disabled=true;
            document.HRM_JobSearch.cmbsection.selectedIndex=0;
        }*/
       
    }
    
    
 function loadAudit(type)
    {
        // alert(type);
        startwaiting(document.HRM_JobSearch) ;
        var type=document.HRM_JobSearch.cmbregion.options[document.HRM_JobSearch.cmbregion.selectedIndex].value;
        var url="../../../../../JobPopupServ_RLV.view?Command=Audit&cmbreg=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadAuditReq(req);
        }
        req.send(null);
    }
    
    function loadAuditReq(req)
    {
        if(req.readyState==4)
        {
          if(req.status==200)
          {  
            //alert('hello');
                 stopwaiting(document.HRM_JobSearch);
                var cmbaudit=document.getElementById("cmbaudit");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
                //cmboffices.text="--select Office--";
                if(flag=="failure")
                {
                    alert("No Audit Wing exists under this level");
                    document.HRM_JobSearch.cmbaudit.disabled=true;
                    document.HRM_JobSearch.cmbaudit.selectedIndex=0;
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
                    document.HRM_JobSearch.cmbaudit.disabled=false;
                    cmbaudit.innerHTML="";
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="--Select Audit Wing--";
                    try
                    {
                        cmbaudit.add(option);
                    }catch(errorObject)
                    {
                    cmbaudit.add(option,null);
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
                              cmbaudit.add(option);
                          }
                          catch(errorObject)
                          {
                              cmbaudit.add(option,null);
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
            document.HRM_JobSearch.cmbdivision.disabled=true;
            document.HRM_JobSearch.cmbdivision.selectedIndex=0;
            document.HRM_JobSearch.cmbsubdiv.disabled=true;
            document.HRM_JobSearch.cmbsubdiv.selectedIndex=0;
            document.HRM_JobSearch.cmbsection.disabled=true;
            document.HRM_JobSearch.cmbsection.selectedIndex=0;
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
    /*    else
        {
            document.HRM_JobSearch.cmbdivision.disabled=true;
            document.HRM_JobSearch.cmbdivision.selectedIndex=0;
            document.HRM_JobSearch.cmbsubdiv.disabled=true;
            document.HRM_JobSearch.cmbsubdiv.selectedIndex=0;
            document.HRM_JobSearch.cmbsection.disabled=true;
            document.HRM_JobSearch.cmbsection.selectedIndex=0;
        }*/
    }
    
    
 function loadDivision(type)
    {
        //alert(type);
        var cir=document.HRM_JobSearch.cmbcircle.options[document.HRM_JobSearch.cmbcircle.selectedIndex].value;
         var reg=document.HRM_JobSearch.cmbregion.options[document.HRM_JobSearch.cmbregion.selectedIndex].value;
       startwaiting(document.HRM_JobSearch) ;
        var url="../../../../../JobPopupServ_RLV.view?Command=Division&cmbregion="+reg+"&cmbcircle=" + cir ;
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
             stopwaiting(document.HRM_JobSearch);
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

///////////////////////////////////////////////////////////////////////////////////////
function getLab()
    {
        var type=document.HRM_JobSearch.cmbcircle.options[document.HRM_JobSearch.cmbcircle.selectedIndex].value;
        //alert('circle:'+type);
            document.HRM_JobSearch.cmblab.disabled=true;
            document.HRM_JobSearch.cmblab.selectedIndex=0;
            document.HRM_JobSearch.cmbsubdiv.disabled=true;
            document.HRM_JobSearch.cmbsubdiv.selectedIndex=0;
            document.HRM_JobSearch.cmbsection.disabled=true;
            document.HRM_JobSearch.cmbsection.selectedIndex=0;
        if(type!="")
        {
            var ol=document.HRM_JobSearch.cmbolevel.options[document.HRM_JobSearch.cmbolevel.selectedIndex].value;
            if(ol=='CL')
            {
                s=type;
                return true;
            }
            loadLab(type);
        }
    /*    else
        {
            document.HRM_JobSearch.cmbdivision.disabled=true;
            document.HRM_JobSearch.cmbdivision.selectedIndex=0;
            document.HRM_JobSearch.cmbsubdiv.disabled=true;
            document.HRM_JobSearch.cmbsubdiv.selectedIndex=0;
            document.HRM_JobSearch.cmbsection.disabled=true;
            document.HRM_JobSearch.cmbsection.selectedIndex=0;
        }*/
    }
    
    
 function loadLab(type)
    {
        //alert(type);
        var cir=document.HRM_JobSearch.cmbcircle.options[document.HRM_JobSearch.cmbcircle.selectedIndex].value;
         var reg=document.HRM_JobSearch.cmbregion.options[document.HRM_JobSearch.cmbregion.selectedIndex].value;
       startwaiting(document.HRM_JobSearch) ;
        var url="../../../../../JobPopupServ_RLV.view?Command=Lab&cmbregion="+reg+"&cmbcircle=" + cir ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadLabReq(req);
        }
        req.send(null);
    }
    
    function loadLabReq(req)
    {
        if(req.readyState==4)
        {
          if(req.status==200)
          {  
             stopwaiting(document.HRM_JobSearch);
                var cmblab=document.getElementById("cmblab");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
                //cmboffices.text="--select Office--";
                if(flag=="failure")
                {
                    alert("No Division/Lab exists under this level");
                    document.HRM_JobSearch.cmblab.disabled=true;
                    document.HRM_JobSearch.cmblab.selectedIndex=0;
                    document.HRM_JobSearch.cmbsubdiv.disabled=true;
                    document.HRM_JobSearch.cmbsubdiv.selectedIndex=0;
                    document.HRM_JobSearch.cmbsection.disabled=true;
                    document.HRM_JobSearch.cmbsection.selectedIndex=0;
                    
                }
                else
                { 
                    // alert('success');
                    cmblab.innerHTML="";
                    document.HRM_JobSearch.cmblab.disabled=false;
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="--Select Lab--";
                    try
                    {
                        cmblab.add(option);
                    }catch(errorObject)
                    {
                    cmblab.add(option,null);
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
                              cmblab.add(option);
                          }
                          catch(errorObject)
                          {
                              cmblab.add(option,null);
                          }
                    }
                }
            }
        }
    }



///////////////////////////////////////////////////////////////////////////////////////

////for lab combo box////////////////////////////////////////////////////////////

function getSubDivision()
    {
        var type=document.HRM_JobSearch.cmbdivision.options[document.HRM_JobSearch.cmbdivision.selectedIndex].value;
        //alert('Divi:'+type);
            document.HRM_JobSearch.cmbsubdiv.disabled=true;
            document.HRM_JobSearch.cmbsubdiv.selectedIndex=0;
            document.HRM_JobSearch.cmbsection.disabled=true;
            document.HRM_JobSearch.cmbsection.selectedIndex=0;
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
     /*   else
        {
           document.HRM_JobSearch.cmbsubdiv.disabled=true;
            document.HRM_JobSearch.cmbsubdiv.selectedIndex=0;
            document.HRM_JobSearch.cmbsection.disabled=true;
            document.HRM_JobSearch.cmbsection.selectedIndex=0;
        } */
    }
    
    
 function loadSubDivision(type)
    {
        //alert(type);
        var cir=document.HRM_JobSearch.cmbcircle.options[document.HRM_JobSearch.cmbcircle.selectedIndex].value;
        var reg=document.HRM_JobSearch.cmbregion.options[document.HRM_JobSearch.cmbregion.selectedIndex].value;
        var div=document.HRM_JobSearch.cmbdivision.options[document.HRM_JobSearch.cmbdivision.selectedIndex].value;
       startwaiting(document.HRM_JobSearch) ;
        var url="../../../../../JobPopupServ_RLV.view?Command=SubDivision&cmbregion="+reg+"&cmbcircle=" + cir+"&cmbdivision="+div ;
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
            stopwaiting(document.HRM_JobSearch);
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
            document.HRM_JobSearch.cmbsection.disabled=true;
            document.HRM_JobSearch.cmbsection.selectedIndex=0;
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
      /*  else
        {
            document.HRM_JobSearch.cmbsection.disabled=true;
            document.HRM_JobSearch.cmbsection.selectedIndex=0;
        }*/
    }
    
    
 function loadSection(type)
    {
        //alert(type);
        var cir=document.HRM_JobSearch.cmbcircle.options[document.HRM_JobSearch.cmbcircle.selectedIndex].value;
        var reg=document.HRM_JobSearch.cmbregion.options[document.HRM_JobSearch.cmbregion.selectedIndex].value;
        var div=document.HRM_JobSearch.cmbdivision.options[document.HRM_JobSearch.cmbdivision.selectedIndex].value;
        var sdiv=document.HRM_JobSearch.cmbsubdiv.options[document.HRM_JobSearch.cmbsubdiv.selectedIndex].value;
       startwaiting(document.HRM_JobSearch) ;
        var url="../../../../../JobPopupServ_RLV.view?Command=Section&cmbregion="+reg+"&cmbcircle=" + cir+"&cmbdivision="+div +"&cmbsubdiv="+sdiv;
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
            stopwaiting(document.HRM_JobSearch);
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
else if(type=='CL')
    s=document.HRM_JobSearch.cmbcircle.options[document.HRM_JobSearch.cmbcircle.selectedIndex].value;
else if(type=='AW')
    s=document.HRM_JobSearch.cmbaudit.options[document.HRM_JobSearch.cmbaudit.selectedIndex].value;
else if(type=='DN')
    s=document.HRM_JobSearch.cmbdivision.options[document.HRM_JobSearch.cmbdivision.selectedIndex].value;
else if(type=='LB')
    s=document.HRM_JobSearch.cmblab.options[document.HRM_JobSearch.cmblab.selectedIndex].value;
else if(type=='SD')
    s=document.HRM_JobSearch.cmbsubdiv.options[document.HRM_JobSearch.cmbsubdiv.selectedIndex].value;
else if(type=='SN')
    s=document.HRM_JobSearch.cmbsection.options[document.HRM_JobSearch.cmbsection.selectedIndex].value;
else if(type=='HO')
    s=1;


//alert(s);
if(type=='HO')
{
        Minimize();
        opener.doParentJob(offid,'TWAD');
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
            if(type=='AW')
                msg ="Select upto Internal Audit Wing";  
            if(type=='DN')
                msg ="Select upto Division";  
            if(type=='LB')
                msg ="Select upto lab";  
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
//////////////////////////////  CITY / TOWN SELECTION ///////////////////////////////////////////

function getCityOffice()
    {
        
        var type=document.HRM_JobSearch.txtCity.value;
        //alert('city');
        var c=checkcity()
        if(c==true)
        {
                  loadCityOffice(type);
        }
    }
    
function loadCityOffice(type)
    {
        //alert(type);
        var type=document.HRM_JobSearch.txtCity.value;
        startwaiting(document.HRM_JobSearch) ;
        var url="../../../../../JobPopupServ_RLV.view?Command=CityOffice&txtCity=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadCityOfficeReq(req);
        }
        req.send(null);
    }
    
    function loadCityOfficeReq(req)
    {
        if(req.readyState==4)
        {
          if(req.status==200)
          {  
            //alert('hello');
                 stopwaiting(document.HRM_JobSearch);
                var cmbcity=document.getElementById("cmbCOffice");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
                //cmboffices.text="--select Office--";
                if(flag=="failure")
                {
                    alert("No Office exists in this City/Town");
                    document.HRM_JobSearch.cmbCOffice.disabled=true;
                    document.HRM_JobSearch.cmbCOffice.selectedIndex=0;
                    
                }
                else
                { 
                    //alert('success');
                    document.HRM_JobSearch.cmbCOffice.disabled=false;
                    document.HRM_JobSearch.cmbCOffice.focus();
                    cmbcity.innerHTML="";
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="--Select Office--";
                    option.value="0";
                    try
                    {
                        cmbcity.add(option);
                    }catch(errorObject)
                    {
                    cmbcity.add(option,null);
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
                              cmbcity.add(option);
                          }
                          catch(errorObject)
                          {
                              cmbcity.add(option,null);
                          }
                    }
                }
            }
        }
    }


 function nonanumber(e,t)
    {
        var unicode=e.charCode? e.charCode : e.keyCode
        if(unicode==13)
        {
                //getCityOffice();
                //document.HRM_JobSearch.txtCity.focus();
                t.blur();
                return false;
        }
        if (unicode!=8)
        {
            if ( unicode==32 || (unicode>=65 && unicode<=90) || (unicode>=97 && unicode<=122) || unicode==45 || unicode==95 )
                return true;
            else
                return false;
        }
    }
    
    
    function checkcity()
{
 if((document.HRM_JobSearch.txtCity.value==null ||(document.HRM_JobSearch.txtCity.value.length==0)) && !( document.HRM_JobSearch.txtCity.value.charAt(0)==String.fromCharCode(160) && document.HRM_JobSearch.txtAddress1.value.length==1  ))
    {
        alert("Null Value not accepted");
        document.HRM_JobSearch.txtCity.value="";
        document.HRM_JobSearch.txtCity.focus();
        return false;
    }
     else    if(!isNaN(document.HRM_JobSearch.txtCity.value))
    {
            alert("Enter String value");
            document.HRM_JobSearch.txtCity.value="";
            document.HRM_JobSearch.txtCity.focus();
            return false;
    }
    return true;
}

function btnCitysubmit()
{
    var offid=document.HRM_JobSearch.cmbCOffice.options[document.HRM_JobSearch.cmbCOffice.selectedIndex].value;
   if( offid!=0)
   {
        //alert(offid,null);
         Minimize();
        opener.doParentJob(offid,'TWAD');
        return true;
    }
    else
    {
        alert('Select the office');
        return false;
    }


}

//////////////////////////////  OTHER DEPT  ///////////////////////////////////////////

function getOtherOffice()
    {
         var type=document.HRM_JobSearch.cmbOName.options[document.HRM_JobSearch.cmbOName.selectedIndex].value;
       
        //alert('city');
        
        if(type!=0)
        {
                  loadOtherOffice(type);
        }
        else
        {
            alert('Select a Other Department');
            document.HRM_JobSearch.cmbOName.focus();
            return false;
            
        }
    }
    
function loadOtherOffice(type)
    {
        //alert(type);
        var type=document.HRM_JobSearch.cmbOName.options[document.HRM_JobSearch.cmbOName.selectedIndex].value;
       startwaiting(document.HRM_JobSearch) ;
        var url="../../../../../JobPopupServ_RLV.view?Command=OtherOffice&cmbOName=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadOtherOfficeReq(req);
        }
        req.send(null);
    }
    
    function loadOtherOfficeReq(req)
    {
        if(req.readyState==4)
        {
          if(req.status==200)
          {  
            //alert('hello');
                 stopwaiting(document.HRM_JobSearch);
                var cmbother=document.getElementById("cmbOOffice");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
                //cmboffices.text="--select Office--";
                if(flag=="failure")
                {
                    alert("No Office exists in this Department");
                    document.HRM_JobSearch.cmbCOffice.disabled=true;
                    document.HRM_JobSearch.cmbCOffice.selectedIndex=0;
                    
                }
                else
                { 
                    //alert('success');
                    document.HRM_JobSearch.cmbOOffice.disabled=false;
                    document.HRM_JobSearch.cmbOOffice.focus();
                    cmbother.innerHTML="";
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="--Select Other Office--";
                    option.value="0";
                    try
                    {
                        cmbother.add(option);
                    }catch(errorObject)
                    {
                    cmbother.add(option,null);
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
                              cmbother.add(option);
                          }
                          catch(errorObject)
                          {
                              cmbother.add(option,null);
                          }
                    }
                }
            }
        }
    }

function btnOthersubmit()
{
    var offid=document.HRM_JobSearch.cmbOOffice.options[document.HRM_JobSearch.cmbOOffice.selectedIndex].value;
    var deptid=document.HRM_JobSearch.cmbOName.options[document.HRM_JobSearch.cmbOName.selectedIndex].value;
   if( offid!=0)
   {
        //alert(offid,null);
         Minimize();
        opener.doParentJob(offid,deptid);
        return true;
    }
    else
    {
        alert('Select the office');
        return false;
    }


}


function btnClosedOfficesubmit()
{
    var offid=document.HRM_JobSearch.cmbClosedOffice.options[document.HRM_JobSearch.cmbClosedOffice.selectedIndex].value;
   if( offid!=0)
   {
        //alert(offid,null);
         Minimize();
        opener.doParentJob(offid,'TWAD');
        return true;
    }
    else
    {
        alert('Select the office');
        return false;
    }


}