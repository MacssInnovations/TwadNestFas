function getTransport()
{
  var req=false;
  
  try
  {
    req=new ActiveXObject("Msxml2.XMLHTTP");  
  }
  catch(e)
  {
    try
    {
     req=new ActiveXObject("Microsoft.XMLHTTP");
    }
    catch(e2)
    {
      req=false;
    }
  }
  if(!req && typeof XMLHttpRequest != 'undefined')
  {
    req=new XMLHttpRequest();
  }
  return req;
}

function selectoption1()
{
  
  if(document.frmlist_service.optselect[0].checked)
  {
    var id=document.getElementById("divdest");
    id.style.display='block';
    var id=document.getElementById("divcadre");
    id.style.display='none';
  }
  else
  {
    var id1=document.getElementById("divdest");
    id1.style.display='none';
    var id1=document.getElementById("divcadre");
    id1.style.display='block';
  }

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
  //alert(url);
  var req=getTransport();
  req.open("GET",url,true);
  req.onreadyStateChange=function()
  {
    loadDesignation(req);
  }
  req.send(null);
  
}

function loadDesignation(req)
{
  if(req.readyState==4)
  {
    if(req.status==200)
    {
      //alert('inside success');
      var des=document.getElementById("cmbDesignation");
      var i=0;
      var response=req.responseXML.getElementsByTagName("response")[0];
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
    alert('Select Designation');
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
  
  var req=getTransport();
  req.open("GET",url,true);
  req.onreadyStateChange=function()
  {
   servCadre(req);
  }
  req.send(null); 

}

function servCadre(req)
{
  if(req.readyState==4)
  {
    if(req.status==200)
    {
    
      var cad=document.getElementById("cmbCadre");
      var i=0;
      var response=req.responseXML.getElementsByTagName("response")[0];
      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
      stopwaiting(document.frmlist_service);
      try
      {
      cad.innerHTML="";
      }
      catch(e)
      {}
      
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
   alert('Select Cadre');
   document.frmlist_service.cmbsgroup1.focus();
   return false;
  }
 return true;
}

