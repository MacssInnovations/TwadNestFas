
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
 
 
 function clearAll()
 {
 document.StatusReport.reportdate.value="";
   document.StatusReport.aco.value="";
  var d=document.getElementById("add");
                d.style.display="none";
                var d=document.getElementById("update");
                d.style.display="block";
 }
 
 function Exit()
 {
    window.open('','_parent','');
    window.close();
 }

 
  function loadValuesFromTable(rid)
    {      
    
         var d=document.getElementById("add");
                d.style.display="none";
                var d=document.getElementById("update");
                d.style.display="block";
                
          var r=document.getElementById(rid); 
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          var table=document.getElementById("Existing");
          document.StatusReport.reportdate.value=rcells.item(1).firstChild.nodeValue;
          document.StatusReport.aco.value=rcells.item(2).firstChild.nodeValue;
          }
    
 function nullCheck()
        {
                  
                 
               if((document.StatusReport.reportdate.value=="") || (document.StatusReport.aco.value.length==0))
                  { 
                       alert("Please Enter the Activities Carried out!");
                       document.StatusReport.aco.focus();
                       return false;
                  }
                  return true;
            }       

 function callServer(command,param)
 {
   
       var date_rep=document.StatusReport.reportdate.value;
       var Act_Out=document.StatusReport.aco.value;
      //command="Update"; 
       
       if(command=="Add")
       
    {     
        
       var flag=nullCheck(); 
                    if(flag==true)
                   {
                    url="../../../../../CommunityServlet.view?command=Add&CommDesc=" + strcommdesc;
                     var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            }   
                    req.send(null);
                  }
                   
                    
        }
        else if(command=="Update")
        {
                    var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../daily_status_edit?reportdate="+date_rep+"&aco="+Act_Out+"&myParam=update";
                   // alert(url);
                     var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req,"Update");
            }   
                    req.send(null);
                    }

        }
        
        else if(command=="Delete")
        {  
                  if(confirm("Do You Really want to Delete the Selected Record"))
             {
                    url="../../../daily_status_edit?reportdate="+date_rep+"&aco="+Act_Out+"&myParam=delete";
                     var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req,"Delete");
            }   
                    req.send(null);
                    }
                    
                    else
                    {
                      alert("Records are not Deleted ");
                    }
        }
        
}  

function processResponse(req,command)
    {   
      if(req.readyState==4)
        {
          if(req.status==200)
          {               
              var baseResponse=req.responseTEXT;
              alert(req.responseTEXT);
            //  var tagCommand=baseResponse.getElementsByTagName("command")[0];
             
              if(command=="Add")
              {
                  addRow(baseResponse);                 
              }
              else if(command=="Delete")
              { 
              deleteRow(baseResponse)
              }
              else if(command=="Update")
              {
              
              updateRow(baseResponse);
              }
          }
        }
  }

function updateRow(baseResponse)
    {
      var flag=baseResponse;
      
       if(flag=="success")
       {   
           alert("Record Updated Successfully.");
          // var suf=document.StatusReport.aco.value;
           var suf2=document.StatusReport.reportdate.value
               var hstrcommcode=suf2;
             //  alert("The id"+hstrcommcode);
                            var items=new Array();
                            items[0]=hstrcommcode;
                            items[1]=document.StatusReport.aco.value;
                              var r=document.getElementById(items[0]);    
                              var rcells=r.cells;
                            rcells.item(1).firstChild.nodeValue=items[0];
                            rcells.item(2).firstChild.nodeValue=items[1];
                        document.StatusReport.reportdate.value="";
                            document.StatusReport.aco.value="";
       }
       else
       {
           alert("failed to update values");
       }                                  
    }

function deleteRow(baseResponse)
  {
                  //var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  var flag=baseResponse;
                  if(flag=="success")
                  {
                     // var Commcode=baseResponse.getElementsByTagName("CommCode")[0].firstChild.nodeValue;
                     var Commcode=document.StatusReport.reportdate.value
                      var tbody=document.getElementById("Existing");     
                      var r=document.getElementById(Commcode);    
                      var ri=r.rowIndex;               
                      tbody.deleteRow(ri); 
                               document.StatusReport.reportdate.value="";
                                document.StatusReport.aco.value="";
                      alert("Selected   Details are Deleted");                      
                  }
                  else
                  {
                      alert("Unable to Delete");
                  }
   
  }
  function addRow(baseResponse)
    {
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              
              if(flag=="success")
              {                        
                     alert("Record Inserted Into Database successfully.");
                    cid=baseResponse.getElementsByTagName("CommCode")[0].firstChild.nodeValue; 
                      alert("Please Note Down Your Community Code: " +  cid);
                     var items=new Array();                   
                    items[0]=baseResponse.getElementsByTagName("CommCode")[0].firstChild.nodeValue; 
                   items[1]=document.CommForm.txtCommDesc.value;
              
                     var tbody=document.getElementById("tblList");
                    
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=items[0];
                     var cell=document.createElement("TD");
                     
                     var anc=document.createElement("A");       
                     var url="javascript:loadValuesFromTable('" + items[0] + "')";              
                     anc.href=url;
                     var txtedit=document.createTextNode("Edit");
                     anc.appendChild(txtedit);
                     cell.appendChild(anc);
                     mycurrent_row.appendChild(cell);
                     var i=0;
                     var cell2;
                     for(i=0;i<2;i++)
                     {                                           
                         cell2=document.createElement("TD");                               
                         var currenttext=document.createTextNode(items[i]);                         
                         cell2.appendChild(currenttext);       
                         mycurrent_row.appendChild(cell2);       
                     }  
                     
                     tbody.appendChild(mycurrent_row); 
                      document.CommForm.htxtCommCode.value="";
                            document.CommForm.txtCommDesc.value="";
                           
                      
             }
             else
             {
                     alert("Failed to Insert Values");
             }
        
    }
    
    
 