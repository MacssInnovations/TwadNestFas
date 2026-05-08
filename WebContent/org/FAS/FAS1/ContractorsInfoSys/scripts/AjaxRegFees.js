
edit=false;
//XMLHttpRequest Object Created
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

//function to dynamically generate the class combo based on division
function Division()
     {
   
    var div=document.frmRegFees.Reg_Office[0].value;
    
    var id= document.getElementById("Reg_Id"); 
          //var option=document.createElement("OPTION");
                    
          
            id.innerHTML="";
              var comboValue="Class-IV";
              var option=document.createElement("OPTION");
              option.text=comboValue;
              option.value="4";
              id.add(option);
              var comboValue2="Class-V";              
              var option2=document.createElement("OPTION");
              option2.text=comboValue2;
              option2.value="5";
              id.add(option2);
    }
    
    //function to dynamically generate the class combo based on circle
    
    function Circle()
        {
      
       var circle=document.frmRegFees.Reg_Office[1].value;
         var id= document.getElementById("Reg_Id"); 
           id.innerHTML="";
              var comboValue="Class-I";
              var option=document.createElement("OPTION");
              option.text=comboValue;
              option.value="1";
              id.add(option);
              var comboValue2="Class-II";
              var comboValue3="Class-III";
              var option2=document.createElement("OPTION");
              option2.text=comboValue2;
              option2.value="2";
              id.add(option2);
              var option3=document.createElement("OPTION");
              option3.text=comboValue3;
              option3.value="3";
              id.add(option3);
              
          
        }

        
function addRecord()
 {     
  var strCaption=document.frmRegFees.cmdAdd.value;
    if(strCaption=="Add")
    {
        document.frmRegFees.cmdAdd.value=" Save ";
        alert("Please Enter Registration Fees Details.");
        document.frmRegfees.Edit.disabled=true;                  
        //document.frmRegFees.Reg_Fees.focus();        
    }
    else
    {
        callServer("Add","null");
    }
 }
 
 
 //function to call the servlet
  function callServer(command)
 {   
 
 for(var i=0; i< document.frmRegFees.Entire_state.length; i++)
    {
      if(document.frmRegFees.Entire_state[i].checked)
        {
          var  rad_val2 = document.frmRegFees.Entire_state[i].value;
        }
    }
  for(var i=0; i< document.frmRegFees.Reg_Office.length; i++)
    {
      if(document.frmRegFees.Reg_Office[i].checked)
        {
          var  rad_val1 = document.frmRegFees.Reg_Office[i].value;
        }
    }
 
 
 
          var url="";
          var strOffice
          var strRegClassId;
          var strState;
          var strRegFees;
          var strDate;          
          if(command=="Add")
          {      
               var flag=nullCheck();
               if(flag==true)
               {
                    strOffice=rad_val1;                
                    strRegClassId=document.frmRegFees.Reg_Id.value;
                    strState=rad_val2;
                    strRegFees=document.frmRegFees.Reg_Fees.value
                    strDate=document.frmRegFees.txtDate.value;      
                    url="../../../../../ServletRegFees.view?command=Add&RegOffice="+strOffice+"&RegClassId=" +strRegClassId + "&RegState=" + strState + "&RegFees=" + strRegFees + "&Date=" + strDate; 
                    
                    req.open("GET",url,true);        
                    req.onreadystatechange=processResponse;
                    req.send(null);
                    
           }
           }
          else if(command=='Update')
           {
                  var flag=nullCheck();
                  //alert(flag);
                   if(flag==true)
                   {
                   strOffice=rad_val1; 
                    strRegClassId=document.frmRegFees.Reg_Id.value;
                    strState=rad_val2;
                    strRegFees=document.frmRegFees.Reg_Fees.value
                    strDate=document.frmRegFees.txtDate.value;      
                    url="../../../../../ServletRegFees.view?command=Update&RegOffice="+strOffice+"&RegClassId=" +strRegClassId + "&RegState=" + strState + "&RegFees=" + strRegFees + "&Date=" + strDate; 
                     //alert(url);
                    req.open("GET",url,true);        
                    req.onreadystatechange=processResponse;
                    req.send(null);
           
           
           }
           }
           else if(command=='Delete')
           {
                   // alert("inside delete");
                   // alert("hello");
                    strOffice=rad_val1; 
                    alert(strOffice)
                    strRegClassId=document.frmRegFees.Reg_Id.value;
                    //alert(strRegClassId);
                    strState=rad_val2;
                    
                url="../../../../../ServletRegFees.view?command=Delete&RegOffice="+strOffice+"&RegClassId=" +strRegClassId + "&RegState=" + strState                   
                //alert(url);
                req.open("GET",url,true);        
                req.onreadystatechange= processResponse;
              
                req.send(null);
           
           
           }
        
    }
           
           
           function processResponse()
           {
               // code for processing the xml returned by servlet  
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
              else if(command=="check")
              {
                   checkFields(baseResponse); 
              
              }
              else if(command=="Get")
              {
              
              getDetails(baseResponse);
              }
              else if(command=="Update")
              {
              
              updateDetails(baseResponse);
              }
              else if(command=="Delete")
              {                 
                  deleteDetails(baseResponse);                             
              }
           }
  }
}

//function called By ProcessResponse for add
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

 //function called By ProcessResponse for Update
    function updateDetails(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {   
           alert("Record Updated Successfully.");
           document.frmRegFees.txtDate.value="";
           document.frmRegFees.Reg_Id.disabled=false;
           clearAll();
           
       }
       else
       {
           alert("failed to update values");
       }                                  
    }
    //function called By ProcessResponse for Delete
    function deleteDetails(baseResponse)
    {
   
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
      if(flag=="success")
      {
           alert("Record Deleted Successfully.");
           clearAll();
           document.frmRegFees.txtDate.value="";
      }
      else
      {
          alert("unable to delete");
      }      
    }
 //function called to check the fields if already existing 

function check()

       {
     
 for(var i=0; i< document.frmRegFees.Entire_state.length; i++)
    {
      if(document.frmRegFees.Entire_state[i].checked)
        {
          var  rad_val2 = document.frmRegFees.Entire_state[i].value;
        }
    }
  for(var i=0; i< document.frmRegFees.Reg_Office.length; i++)
    {
      if(document.frmRegFees.Reg_Office[i].checked)
        {
          var  rad_val1 = document.frmRegFees.Reg_Office[i].value;
        }
    }
            var strOffice=rad_val1;
             
             var strRegClassId=document.frmRegFees.Reg_Id.value
            
            var strState=rad_val2;
             
             // var strRegFees=document.frmRegFees.Reg_Fees.value;
             
              
   if((strOffice!="")&&(strRegClassId!="")&&(strState!=""))
      {
     
        var strCaption=document.frmRegFees.cmdAdd.value;
        if(strCaption=="  Add  ")
        {
        
            document.frmRegFees.cmdAdd.value=" Save ";       
        }   
        if(edit==false)       
               
                    {          
                        if((strOffice=="")&&(strRegClassId=="")&&(strState==""))
                        {
                            return;
                        }
                        
                   
       
            var url="../../../../../ServletRegFees.view?command=check&officeid="+ escape(strOffice) + "&classid="+escape(strRegClassId)+"&state=" +escape(strState);//+ "&regfees=" +strRegFees;      
         
            req.open("GET",url,true);
            req.onreadystatechange=processResponse;
            req.send(null);
            }
        }
  }

   function checkFields(baseResponse)
   {
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {                                        
          alert("The record already exists. Please enter the new details");
          document.frmRegFees.Reg_Id.value="";
          document.frmRegFees.Reg_Office.value="";
          document.frmRegFees.Entire_state.value="";
          document.frmRegFees.Reg_Fees.value="";
         //document.frmRegFees.Reg_Fees.focus();
          
       }

   }
//function to clear the details except date
function clearAll()
{

      document.frmRegFees.Reg_Office.value="";
      document.frmRegFees.Reg_Id.value="";
      document.frmRegFees.Entire_state.value="";
      document.frmRegFees.Reg_Fees.value="";
       document.frmRegFees.Reg_Id.disabled=false;

        document.frmRegFees.Update.disabled=true;
        document.frmRegFees.Delete.disabled=true;
        document.frmRegFees.cmdAdd.disabled=false; 
        document.frmRegFees.Edit.disabled=false;  
        document.frmRegFees.cmdAdd.value="  Add  ";
        edit=false;



}
 //function called from the command button cmdEdit
 function promptID()
{    
      document.frmRegFees.Edit.disabled=true;
      document.frmRegFees.cmdAdd.disabled=true;
      //document.frmRegistrationFee.cmdSelect.disabled=false;
      
      edit=true; 
      
      alert("Please Enter or Select a Existing OfficeId, Registration Class Id and Entire State");
       
}
//function to Get the details of fees 
function get()
  {
 
  for(var i=0; i< document.frmRegFees.Entire_state.length; i++)
    {
      if(document.frmRegFees.Entire_state[i].checked)
        {
          var  rad_val2 = document.frmRegFees.Entire_state[i].value;
        }
    }
  for(var i=0; i< document.frmRegFees.Reg_Office.length; i++)
    {
      if(document.frmRegFees.Reg_Office[i].checked)
        {
          var  rad_val1 = document.frmRegFees.Reg_Office[i].value;
        }
    }
  var strOfficeId=rad_val1;
  var strRegClassId=document.frmRegFees.Reg_Id.value;
  var strState=rad_val2;
  
  //strRegFees=document.frmRegFees.Reg_Fees.value;
                
               
                
                   if(edit==true)
                   {
                     var url="../../../../../ServletRegFees.view?command=Get&officeid="+ escape(strOfficeId) +"&classid=" + escape(strRegClassId) + "&state=" + escape(strState);  
                      
                      req.open("GET",url,true);    
                      
                      req.onreadystatechange=processResponse;
                      req.send(null);
                   }
          }

 function getDetails(baseResponse)
 {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     
       if(flag=="success")
       {      
      
          document.frmRegFees.Reg_Office.value=baseResponse.getElementsByTagName("officeid")[0].firstChild.nodeValue;
          document.frmRegFees.Reg_Id.value=baseResponse.getElementsByTagName("classid")[0].firstChild.nodeValue;
          document.frmRegFees.Entire_state.value=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
          document.frmRegFees.Reg_Fees.value=baseResponse.getElementsByTagName("fees")[0].firstChild.nodeValue;
          document.frmRegFees.txtDate.value=baseResponse.getElementsByTagName("date")[0].firstChild.nodeValue;          
          
          document.frmRegFees.Reg_Office.disabled=true;
           document.frmRegFees.Reg_Id.disabled=true;
           document.frmRegFees.Entire_state.disabled=true;
          //document.frmRegFees.Reg_Fees.focus();          
          document.frmRegFees.Update.disabled=false;
          document.frmRegFees.Delete.disabled=false;
          document.frmRegFees.cmdAdd.disabled=true;
       }
       else
       {
           alert("Invalid Id,No Record Exist with this ID");
           
           document.frmRegFees.Reg_Fees.focus();
       } 


 }
 //Validating the Date which should not exceed present date
 function DateCheck()
{ 

 //alert("date check");
 
 //getting entered date
 var dt=document.frmRegFees.txtDate.value;
  var userDate=new Date(dt);
 var today = new Date();
  if(today<userDate)
  {
  alert("cannot take this value");
  return false;
  }
 return true;
  
}
 //function for null check
 function nullCheck()
 {

    if((frmRegFees.Reg_Id.value=="") || (frmRegFees.Reg_Id.value.length<=0))
    {
         alert("Please Enter Class");
         frmRegFees.Reg_Id.focus();
         return false;
    }
    
    if(frmRegFees.Entire_state.checked==false)
    {
         alert("Please Enter Entire State");
         //frmRegFees.Entire_state.focus();
         return false;
    }
  if((frmRegFees.Reg_Fees.value=="") || (frmRegFees.Reg_Fees.value.length<=0))
    {
         alert("Please Enter Registration Fees");
         frmRegFees.Reg_Fees.focus();
         return false;
    }
 if((frmRegFees.txtDate.value=="") || (frmRegFees.txtDate.value.length<=0))
    {
         alert("Please Enter Date");
         frmRegFees.txtDate.focus();
         return false;
    }
    return true;

 }
   
   
   
   
   
   
   
  
