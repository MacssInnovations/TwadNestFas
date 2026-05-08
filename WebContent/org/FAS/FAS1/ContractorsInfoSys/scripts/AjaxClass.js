/*  this file viewtains page specific ajax functions for FILE : MasterClass.jsp  */ 


// code for creating XMLHTTPREQUEST object
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
 
 
 // function to clear all
 function clearAll()
 {        
        document.frmClassRegistration.txtRegClassId.value="";
        document.frmClassRegistration.txtRegClassName.value="";
                 
        document.frmClassRegistration.txtRegClassId.disabled=false;        
        document.frmClassRegistration.cmdUpdate.disabled=true;
        document.frmClassRegistration.cmdDelete.disabled=true;
        
        document.frmClassRegistration.cmdAdd.disabled=false; 
        document.frmClassRegistration.cmdEdit.disabled=false;  
        document.frmClassRegistration.cmdAdd.value="  Add  ";
        edit=false;
 }
 
 function addRecord()
 { 
     var strCaption=document.frmClassRegistration.cmdAdd.value;
    if(strCaption=="Add")
    {
        document.frmClassRegistration.cmdAdd.value=" Save ";
        alert("Please Enter Registration Class Details.");
        document.frmClassRegistration.cmdEdit.disabled=true;                  
        document.frmClassRegistration.txtRegClassId.focus();        
    }
    else
    {
        callServer("Add","null");
    }
 }
 // function to call servlet :Servlet Class
 function callServer(command,param)
 {     
          var url="";
          var strRegClassId;
          var strRegClassName;
                 
          if(command=="Add")
          {      
                
                var flag=nullCheck();    
                if(flag==true)
                {
                    strRegClassId=document.frmClassRegistration.txtRegClassId.value;
                    strRegClassName=document.frmClassRegistration.txtRegClassName.value;
                        
                    url="../../../../../ServletClass.view?command=Add&Id=" + strRegClassId + "&Name=" + strRegClassName ;
                   
                    req.open("GET",url,true);        
                    req.onreadystatechange=processResponse;
                    req.send(null);
                }
          }      
          else if(command=="Update")
          {
                var flag=nullCheck();    
                if(flag==true)
                {
                    strRegClassId=document.frmClassRegistration.txtRegClassId.value;
                    strRegClassName=document.frmClassRegistration.txtRegClassName.value;
                     
                    url="../../../../../ServletClass.view?command=Update&Id=" + strRegClassId + "&Name=" + strRegClassName ;
                                        
                    req.open("GET",url,true);        
                    req.onreadystatechange=processResponse;
                    req.send(null);
                }
          }  
          else if(command=="Delete")
          {
                strRegClassId=document.frmClassRegistration.txtRegClassId.value;
                url="../../../../../ServletClass.view?command=Delete&Id=" + strRegClassId;                    
                req.open("GET",url,true);        
                req.onreadystatechange=processResponse;
                req.send(null);
          }
     
    }
    
    // code for processing the xml returned by servlet :Servlet Class 
   
    function processResponse()
    {   
      if(req.readyState==4)
        {
          if(req.status==200)
          {              
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue;             
              if(command=="Add")
              {
                 
                 addRow(baseResponse);                 
              }
              else if(command=="Delete")
              {                 
                  deleteRow(baseResponse);                             
              }
              else if(command=="Update")
              {              
                  updateRow(baseResponse);             
              }   
              else if(command=="Get")
              {             
                  displayDetails(baseResponse);
              }
          }
        }
    }
    
    
    function deleteRow(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
      if(flag=="success")
      {
           alert("Record Deleted Successfully.");
           clearAll();
      }
      else
      {
          alert("unable to delete");
      }      
    }
    
    // function for updating 
    
    function updateRow(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {   
           alert("Record Updated Successfully.");
           clearAll();
       }
       else
       {
           alert("failed to update values");
       }                                  
    }
    
    
    // function for displaying the servlet returned values 
    function displayDetails(baseResponse)
    {      
       var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {                                        
          document.frmClassRegistration.txtRegClassId.value=baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue;
          document.frmClassRegistration.txtRegClassName.value=baseResponse.getElementsByTagName("name")[0].firstChild.nodeValue;
           //document.frmClassRegistration.txtRegClassId.focus();          
          document.frmClassRegistration.cmdUpdate.disabled=false;
          document.frmClassRegistration.cmdDelete.disabled=false;
          document.frmClassRegistration.cmdAdd.disabled=true;
       }
       else
       {
           alert("Invalid Id,No Record Exist with this ID");
           document.frmClassRegistration.txtRegClassId.value="";
           document.frmClassRegistration.txtRegClassId.focus();
       }                                  
    }
    
    // function to add a record
    function addRow(baseResponse)
    {
         var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
         //alert("called with " + flag);
         if(flag=="success")
         {                        
              alert("Record Inserted Into Database successfully.");
              clearAll(); 
         }
         else
         {
               alert("failed to insert values");
         }       
    }
    

// code to validate the ID
    
function Verification()
{
     
      var strRegClassId=document.frmClassRegistration.txtRegClassId.value;
      
      if(strRegClassId!="")
      {
        var strCaption=document.frmClassRegistration.cmdAdd.value;
        if(strCaption=="  Add  ")
        {
            document.frmClassRegistration.cmdAdd.value=" Save ";       
        }   
        if(edit==false)
        {          
            if((strRegClassId==""))
            {
                return;
            }
            var url="../../../../../ServletClass.view?command=Verify&id="+escape(strRegClassId);  
           
            req.open("GET",url,true);
            req.onreadystatechange=updatePage;
            req.send(null);
        }
        else
        {
              url="../../../../../ServletClass.view?command=Get&Id=" + escape(strRegClassId);
              req.open("GET",url,true);        
              req.onreadystatechange=processResponse;
              req.send(null);
        }
      }
}


function updatePage()
{
        if(req.readyState==4)
        {
           if(req.status==200)
           {               
                var msg=req.responseXML.getElementsByTagName("message")[0].firstChild.nodeValue;
                if(msg=="valid")
                {
                    alert("Record with the given ID Already Exists");
                    document.frmClassRegistration.txtRegClassId.value="";
                    document.frmClassRegistration.txtRegClassId.focus();                    
                }
                else
                {//id does not exist
                }                     
            }
        }    
}


/*  this file contains page specific validation functions for FILE : MasterPayScale.jsp  */


// this function ensures that fields cannot be empty
function nullCheck()
{
    if((frmClassRegistration.txtRegClassId.value=="") || (frmClassRegistration.txtRegClassId.value.length<=0))
    {
         alert("Please Enter Class Registration ID");
         frmClassRegistration.txtRegClassName.focus();
         return false;
    }  
   
    if((frmClassRegistration.txtRegClassName.value=="") || (frmClassRegistration.txtRegClassName.value.length<=0))
    {
         alert("Please Enter Class Registration Description");
         frmClassRegistration.txtRegClassName.focus();
         return false;
    }
 
    
    return true;
  
}
  
// this function checks that no alphabets cannot
//   be entered in a numeric field
function isInteger(param,e)
{     
       var nav4 = window.Event ? true : false;
       if (nav4)    // Navigator 4.0x
            var whichCode = e.which
       else         // Internet Explorer 4.0x
            var whichCode = e.keyCode
      if(whichCode>=48 && whichCode<=57)
      {
          return true;
      }
      var str=param.value;
      param.value=str.substring(0,str.length-1);
      return false;              
}  

// this function prompts for the id and calls the servlet
// that loads the values into the fields
function promptID()
{                 
      clearAll();      
      document.frmClassRegistration.cmdEdit.disabled=true;
      document.frmClassRegistration.cmdAdd.disabled=true;
      
      edit=true; 
      alert("Please Enter or Select a Existing Class Registration ID");
      document.frmClassRegistration.txtRegClassName.focus(); 
}
    




