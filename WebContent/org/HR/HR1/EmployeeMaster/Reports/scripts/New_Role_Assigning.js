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

function clearAll()
{
      document.getElementById("cmbsgroup1").selectedIndex=0;
       var des=document.getElementById("Desig");
       var   length = des.options.length;
        for(i = 0; i < length; i++) 
            des.remove(1);
      
       var Roles=document.getElementById("Roles");
        length = Roles.options.length;
        for(i = 0; i < length; i++) 
            Roles.remove(0);
         var URoles=document.getElementById("URoles");
       length = URoles.options.length;
        for(i = 0; i < length; i++) 
            URoles.remove(0);
     
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

function frmsubmit()
{
    //alert('inside submit');
    
      var desigval=document.frmValidationSummaryRep.Desig.options[document.frmValidationSummaryRep.Desig.selectedIndex].value;
      //alert(desigval);
     if(nullcheck_roles())
         var url="../../../../../../New_Role_Assigning.av?Command=submit&roleval="+role_value+"&desigval="+desigval;
     // alert(url);
          var req=getTransport();
    req.open("GET",url,false);       
    req.onreadystatechange=function()
    {
        var response=req.responseXML.getElementsByTagName("response")[0];
        var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
       // stopwaiting(document.frmValidationSummaryRep);
    if(flag=="fail")
    {
       alert("Roles are not inserted ");
    }
    else
    {   
         alert("Roles are inserted successfully ");
         clearAll();
    }                
    }
    req.send(null);             
}

function nullcheck_roles()
{
       var des=document.getElementById("URoles");
    //  alert("len"+des.length);
                 for(i=0;i<des.length;i++)
                    {
                      
                           // if(des.options[i].selected==true)
                            
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
      var des=document.getElementById("Roles");
      des.innerHTML='';
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
                        if(flag=="failure")
                        {
                            alert("No Roles available for updation ");
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
                      
                                var value=response.getElementsByTagName("roles");
                               // alert("value---->"+value);
                                var option=document.createElement("OPTION");
                               for(var i=0;i<value.length;i++)
                                {
                               
                                    var tmpoption=value.item(i);
                                    var id=tmpoption.getElementsByTagName("role_id")[0].firstChild.nodeValue;
                                    var name=tmpoption.getElementsByTagName("role_name")[0].firstChild.nodeValue;
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
       } 
 req.send(null);
   
       
 
}
/*function insert_list()
{

  var des=document.getElementById("Roles");
    var child=des.childNodes;     
       // alert(des.length);
          var uroles=document.getElementById("URoles");
                         for(i=0;i<des.length;i++)
                            {
                               // alert("first");  
                                    if(des.options[i].selected==true)
                                    {
                                        var option=document.createElement("OPTION");
                                        option.text=des.options[i].text
                                        option.value=des.options[i].value;
                                        try
                                        {
                                       
                                            uroles.add(option);
                                        }catch(errorObject)
                                        {
                                          //des.removeChild(child[i]);
                                            uroles.add(option,null);
                                        }
                                     
                                        
                                    }
                            }
                    for(i=0;i<des.length;i++)
                        if(des.options[i].selected==true)
                        {
                            try{
                             des.remove(des.options.selectedIndex);
                                    // des.remove(i);
                            }
                            catch(errorObject)
                            {
                              alert("catch");
                            }
                        }

}
function delete_list()
{

  var des=document.getElementById("URoles");
      var child=des.childNodes; 
           var uroles=document.getElementById("Roles");
                  
                   for(i=0;i<des.length;i++)
                        {
                         if(des.options[i].selected==true)
                            {
                                                             
                                var option=document.createElement("OPTION");
                                option.text=des.options[i].text
                                option.value=des.options[i].value;
                               
                                try
                                {
                                    uroles.add(option);
                                }catch(errorObject)
                                {
                                    uroles.add(option,null);
                                }
                            }
                          }  
                                 for(i=0;i<des.length;i++)
                                    if(des.options[i].selected==true)
                                    {
                                        try{
                                       
                                                 des.remove(i);
                                        }
                                        catch(errorObject)
                                        {
                                          alert("catch");
                                        }
                                    }                           
                                                              
}

function left_right()
{

 var des=document.getElementById("Roles");
    var child=des.childNodes;     
      
          var uroles=document.getElementById("URoles");
                         for(i=0;i<des.length;i++)
                            {     var option=document.createElement("OPTION");
                                        option.text=des.options[i].text
                                        option.value=des.options[i].value;
                                        try
                                        {
                                            uroles.add(option);
                                        }catch(errorObject)
                                        {
                                            uroles.add(option,null);
                                        }
                                   
                            }
                    for(i=0;i<des.length;i++)
                    {
                           try{
                           
                                     des.remove(i);
                            }
                            catch(errorObject)
                            {
                              alert("catch");
                            }
                    }


}


function right_left()
{

 var des=document.getElementById("URoles");
      var child=des.childNodes; 
           var uroles=document.getElementById("Roles");
                  
                   for(i=0;i<des.length;i++)
                        {
                        
                                                             
                                    var option=document.createElement("OPTION");
                                    option.text=des.options[i].text
                                    option.value=des.options[i].value;
                                   
                                    try
                                    {
                                        uroles.add(option);
                                    }catch(errorObject)
                                    {
                                        uroles.add(option,null);
                                    }
                              
                        }
                                 for(i=0;i<des.length;i++)
                                  {
                                        try{
                                       
                                                 des.remove(i);
                                        }
                                        catch(errorObject)
                                        {
                                          alert("catch");
                                        }
                                    }                           
                               
}*/

function moveAll(fromBox, toBox) {
    
        if(fromBox.length < 1) {
            alert('There is no item(s) in Source Box');
            return false;
        }
    
        var length = fromBox.options.length;
        for(i = 0; i < length; i++) {
            var text = fromBox.options[0].text;
            var value = fromBox.options[0].value;
            toBox.options[toBox.length] = new Option(text, value);
            fromBox.remove(0);
        }
    }
    
    function moveSelected(fromBox, toBox) {
    
        if(fromBox.length < 1) {
            alert('There is no item(s) in Source Box');
            return false;
        }
    
        if(fromBox.options.selectedIndex == -1) {
            alert('Please Select an Item in to move');
            return false;
        }
    
        while (fromBox.options.selectedIndex >= 0 ) { 
                var text = fromBox.options[fromBox.options.selectedIndex].text;
                var value = fromBox.options[fromBox.options.selectedIndex].value;
                toBox.options[toBox.length] = new Option(text, value);
                fromBox.remove(fromBox.options.selectedIndex);
        }
        
    }    