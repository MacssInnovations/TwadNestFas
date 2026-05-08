var sequence=0;
var req = false;
//XMLHttpRequest Object Created
function getTransport()
{

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
 //FUNCTION TO LOAD THE SYSTEM DATE DYNAMICALLY
 function loadDate()
   {
     var today = new Date()
     var year = today.getYear()
     if(year < 1000)
     {
      year += 1900
     }
    
     document.OffRegnClassForm.txtDate.value=today.getDate() + "/" + (today.getMonth()+1) +  "/" + (year+"");
  }
  
//FUNCTION CALLED BY CLEARALL BUTTON TO CLEAR THE CONTENTS  
function clearAll()
{
 
      document.OffRegnClassForm.txtofflevel.selectedIndex=0;
      document.OffRegnClassForm.txtClassId.selectedIndex=0
      document.OffRegnClassForm.txtofflevel.disabled=false;
      document.OffRegnClassForm.txtClassId.disabled=false;
      document.OffRegnClassForm.CmdDelete.disabled=true;
      document.OffRegnClassForm.CmdAdd.disabled=false; 
      document.OffRegnClassForm.CmdAdd.value="  Add  ";
      document.OffRegnClassForm.txtDate.disabled=false;
      loadDate();

}

//FUNCTION CALLED WHEN SELECT ANCHOR IS CLICKED 
//TO LOAD THE VALUES FORM GRID TO THE TABLE
function loadValuesFromTable(rid)
    {      
          var r=document.getElementById(rid); 
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          var table=document.getElementById("Existing");
          
          var htxtseq=rcells.item(1).firstChild.nodeValue;
          document.OffRegnClassForm.hseq.value=htxtseq;
          document.OffRegnClassForm.txtofflevel.value=rcells.item(2).firstChild.value;
          //alert(document.OffRegnClassForm.txtofflevel);
          document.OffRegnClassForm.txtClassId.value=rcells.item(3).firstChild.value;
          document.OffRegnClassForm.txtDate.value=rcells.item(4).firstChild.nodeValue;
          document.OffRegnClassForm.CmdAdd.disabled=true;
          document.OffRegnClassForm.CmdDelete.disabled=false;
          document.OffRegnClassForm.txtDate.disabled=true;
          document.OffRegnClassForm.txtClassId.disabled=true;
          document.OffRegnClassForm.txtofflevel.disabled=true;
    }
 


//FUNCTION CALLED ON CLICKING THE ADD BUTTON   
function addRecord()
 {     
  var strCaption=document.OffRegnClassForm.CmdAdd.value;
    if(strCaption=="ADD")
    {
        document.OffRegnClassForm.CmdAdd.value=" ADD ";
                    
    }
    else
    {
        callServer("Add","null");
    }
 }
 
 
//FUNCTION TO CALL THE SERVLET
function callServer(command,param)
 {   
        var url="";
          var strOffice
          var strRegClassId;
          var strDate;          
          if(command=="Add")
          {     
            var flag=nullCheck();
            if(flag==true)
            {
               strOffice=document.OffRegnClassForm.txtofflevel.value;
               strRegClassId=document.OffRegnClassForm.txtClassId.value;
               strDate=document.OffRegnClassForm.txtDate.value;      
               url="../../../../../OffRegnClass_Servlet.view?command=Add&RegOffice="+strOffice+"&RegClassId=" +strRegClassId + "&Date=" + strDate; 
               var req=getTransport();
               req.open("GET",url,true);        
               req.onreadystatechange=function()
               {
               processResponse(req);
               }
               req.send(null);
                    
          
             }
          }
         
          else if(command=='Delete')
           {
           
           if(confirm("Do You Really Want to Delete It"))
           {
                  var r=document.getElementById(sequence); 
                  document.OffRegnClassForm.hlevel.value=document.OffRegnClassForm.txtofflevel.value;
                  var strOffice = document.OffRegnClassForm.hlevel.value;
                  document.OffRegnClassForm.hclass.value=document.OffRegnClassForm.txtClassId.value;
                  var strRegClassId = document.OffRegnClassForm.hclass.value;
                    
                  url="../../../../../OffRegnClass_Servlet.view?command=Delete&RegOffice="+strOffice+"&RegClassId=" +strRegClassId;                  
                  var req=getTransport();
                  req.open("GET",url,true);        
                   req.onreadystatechange=function()
                     {
                     processResponse(req);
                     }
                  req.send(null);
                  }
            else
            {
               alert("Records are not Deleted");
            }
           
           
           }
        
  }
    
   //FUNCTION CALLED TO LOAD AND DISPLAY THE DETAILS DYNAMICALLY ON FORM LOAD 
    function displayDetails()
    {
            var tbl=document.getElementById("tblList");
              var i;
                                  
               for(i=tbl.rows.length;i>0;i--)
                 {        
                   tbl.CmdDeleteRow(0);
                 }    

                 url="../../../../../OffRegnClass_Servlet.view?command=Display";
                 //alert(url);
                 var req=getTransport();
                 req.open("GET",url,true);        
                 req.onreadystatechange=function()
                 {
                 processResponse(req);
                 }   
                 req.send(null);
                 var tbody=document.getElementById("tblList");
     
    }
           
  //FUNCTION TO PROCESS THE XML RETURNED BY SERVLET         
    function processResponse(req)
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
              else if(command=="Display")
              {
              getDetails(baseResponse);
              }
               
             else if(command=="check")
              {
                   checkFields(baseResponse); 
              
              }
              
              else if(command=="Delete")
              {                 
                  CmdDeleteDetails(baseResponse);                             
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
              var tbody=document.getElementById("tblList");
               sequence=sequence+1;
              var items=new Array();
                      items[0]=document.OffRegnClassForm.txtofflevel.options(document.OffRegnClassForm.txtofflevel.selectedIndex).text;
                      document.OffRegnClassForm.hlevel.value=baseResponse.getElementsByTagName("offid")[0].firstChild.nodeValue;
                      var level=document.OffRegnClassForm.hlevel.value
                       
                      items[1]=document.OffRegnClassForm.txtClassId.options(document.OffRegnClassForm.txtClassId.selectedIndex).text;
                      document.OffRegnClassForm.hclass.value=baseResponse.getElementsByTagName("cid")[0].firstChild.nodeValue;
                      var rclassid=document.OffRegnClassForm.hclass.value
                      
                      items[2]=baseResponse.getElementsByTagName("date")[0].firstChild.nodeValue;
                     
                      var mycurrent_row=document.createElement("TR");
                      mycurrent_row.id=sequence;
                     
                     var cell=document.createElement("TD");
                     var anc=document.createElement("A");       
                     var url="javascript:loadValuesFromTable('" + sequence + "')";              
                     anc.href=url;
                     var txtedit=document.createTextNode("Select");
                     anc.appendChild(txtedit);
                     cell.appendChild(anc);
                     mycurrent_row.appendChild(cell);
                     
                     var cell1=document.createElement("TD");
                   
                     //serial number generation
                     //appending the sno
                      var sno=document.createTextNode("" + sequence);  
                       cell1.appendChild(sno);  
                       mycurrent_row.appendChild(cell1);
                      
                      
                           var cell2=document.createElement("TD");//OFFICE ID AND DESC
                           var tlevel=document.createElement("INPUT");
                           var tleveldesc=document.createTextNode(items[0]);
                           tlevel.type="HIDDEN";
                           tlevel.text=level;
                           tlevel.value=level;
                           tlevel.name=level;
                           cell2.appendChild(tlevel);
                           cell2.appendChild(tleveldesc);
                           mycurrent_row.appendChild(cell2);
                           
                           
                           var cell3=document.createElement("TD");//Class ID AND DESC
                           var tclass=document.createElement("INPUT");
                           var tclassdesc=document.createTextNode(items[1]);
                           tclass.type="HIDDEN";
                           tclass.text=rclassid;
                           tclass.value=rclassid;
                           tclass.name=rclassid;
                           cell3.appendChild(tclass);
                           cell3.appendChild(tclassdesc);
                           mycurrent_row.appendChild(cell3);
                           
                           
                           
                           var cell4=document.createElement("TD");//DATE
                           var tdate1=document.createTextNode(items[2]);
                           cell4.appendChild(tdate1);
                           mycurrent_row.appendChild(cell4);
                     
                    tbody.appendChild(mycurrent_row);
                    clearAll();
         }
         else
         {
               alert("failed to insert values");
         }       
    }
    //FUNCTION TO CALLED BY PROCESS RESPONSE 
    //TO DISPLAY THE DETAILS DYNAMICALLY ON FORM LOAD
    function  getDetails(baseResponse)
    {
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
                  {
                  //getting the number of rows 
                                    
                  var value=baseResponse.getElementsByTagName("value");
                  var tbody=document.getElementById("tblList");
                  var k=0;
                  
                  
                        for(k=0;k<value.length;k++)
                        {
                       sequence=sequence+1;
                        var items=new Array();
                        var offid1=value[k].getElementsByTagName("offid")[0].firstChild.nodeValue;
                        var offidesc1=value[k].getElementsByTagName("offdesc")[0].firstChild.nodeValue;
                        var cid1=value[k].getElementsByTagName("cid")[0].firstChild.nodeValue;
                        var cdesc1=value[k].getElementsByTagName("cdesc")[0].firstChild.nodeValue;
                        var date1=value[k].getElementsByTagName("date")[0].firstChild.nodeValue;
                        
                        var mycurrent_row=document.createElement("TR");
                         mycurrent_row.id=sequence;
                         
                         var cell=document.createElement("TD");
                         var anc=document.createElement("A");       
                         var url="javascript:loadValuesFromTable('" + sequence + "')";              
                         anc.href=url;
                         var txtedit=document.createTextNode("Select");
                         anc.appendChild(txtedit);
                         cell.appendChild(anc);
                         mycurrent_row.appendChild(cell);
                         
                         
                          var cell1=document.createElement("TD");
                          //serial number generation
                         //appending the sno
                         //sequence=0;
                           
                           var sno=document.createTextNode("" + sequence);  
                           cell1.appendChild(sno);  
                           mycurrent_row.appendChild(cell1);
                           
                           var cell2=document.createElement("TD");//OFFICE ID AND DESC
                           var toffid=document.createElement("INPUT");
                           var toffdesc=document.createTextNode(offidesc1);
                           toffid.type="HIDDEN";
                           toffid.text=offid1;
                           toffid.value=offid1;
                           toffid.name=offid1;
                           cell2.appendChild(toffid);
                           cell2.appendChild(toffdesc);
                           mycurrent_row.appendChild(cell2);
                           
                           var cell3=document.createElement("TD");//CLASS ID AND DESC
                           var tcid=document.createElement("INPUT");
                           var tcdesc=document.createTextNode(cdesc1);
                           tcid.type="HIDDEN";
                           tcid.text=cid1;
                           tcid.value=cid1;
                           tcid.name=cid1;
                           cell3.appendChild(tcid);
                           cell3.appendChild(tcdesc);
                           mycurrent_row.appendChild(cell3);
                          
                           var cell4=document.createElement("TD");//DATE
                           var tdate=document.createTextNode(date1);
                           cell4.appendChild(tdate);
                           mycurrent_row.appendChild(cell4);
                           
                           
                           tbody.appendChild(mycurrent_row); 
                           }

                     
                  }
                else
                   {
                   alert("Sorry Failed to Load and Display the values");
                   }
    
    
    
    
    }

  //function called By ProcessResponse for DELETE
  function CmdDeleteDetails(baseResponse)
    {
   
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
      if(flag=="success")
      {
           
           var offId=baseResponse.getElementsByTagName("offId")[0].firstChild.nodeValue;
           var ClassId=baseResponse.getElementsByTagName("ClassId")[0].firstChild.nodeValue;
          
                      var tbody=document.getElementById("Existing");  
                      var htxtseq1=document.OffRegnClassForm.hseq.value;
                      
                      var r=document.getElementById(htxtseq1);    
                      var ri=r.rowIndex;               
                      tbody.deleteRow(ri); 
                                
                       alert("Selected  Office Class Details are Deleted");       
                       document.OffRegnClassForm.txtDate.value="";
                       clearAll();
           
      }
      else
      {
          alert("--Unable to Delete the Details--");
      }      
    }
    
 //FUNCTION CALLED TO CHECK WHETHER THE FIELDS ALREADY EXIST 

function check()

       {
            var strOffice=document.OffRegnClassForm.txtofflevel.value;
             var strRegClassId=document.OffRegnClassForm.txtClassId.value;
             
             if((strOffice!="")&&(strRegClassId!=""))
              {
             
             var url="../../../../../OffRegnClass_Servlet.view?command=check&officeid="+ escape(strOffice) + "&classid="+escape(strRegClassId);    
             var req=getTransport();
            req.open("GET",url,true);
            req.onreadystatechange=function()
            {
            processResponse(req);
            }
            req.send(null);
            }
        }
  
//FUNCTION CALLED BY PROCESS RESPONSE TO GENERATE THE RESPONSE 
   function checkFields(baseResponse)
   {
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {                                        
          alert("The record already exists. Please enter the new details");
         
           clearAll();
       }
       else
       {
           var strCaption=document.OffRegnClassForm.CmdAdd.value;
            if(strCaption=="ADD")
            {
                document.OffRegnClassForm.CmdAdd.value=" Save ";
                            
            }
       }
      

   }
   
 //Validating the Date which should not exceed present date
 function DateCheck()
{ 
 //getting entered date
    var dt=document.OffRegnClassForm.txtDate.value;
    var userDate=new Date(dt);
    var today = new Date();
    if(today<userDate)
    {
    alert("cannot take this value");
    return false;
    }
    return true;
    
}
//function for null check THE FIELDS
 function nullCheck()
 {

  if((OffRegnClassForm.txtofflevel.value=="") || (OffRegnClassForm.txtofflevel.value.length<=0))
    {
         alert("Please Enter Office Level Id");
         OffRegnClassForm.txtofflevel.focus();
         return false;
    }
    
    if((OffRegnClassForm.txtClassId.value=="") || (OffRegnClassForm.txtClassId.value.length<=0))
    {
         alert("Please Enter Class");
         OffRegnClassForm.txtClassId.focus();
         return false;
    }
   if((OffRegnClassForm.txtDate.value=="") || (OffRegnClassForm.txtDate.value.length<=0))
    {
         alert("Please Enter Date");
         OffRegnClassForm.txtDate.focus();
         return false;
    }
    return true;

 }
 
   
   
   
   
   
   
  
