
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
    //alert("in");
   document.AcHead_SubGroupForm.txtSubGrpCode.value="";
   document.AcHead_SubGroupForm.txtSubGrpDesc.value="";
   
    var d=document.getElementById("cmdAdd");
     d.style.display="block";
    
     var d1=document.getElementById("cmdUpdate");
     d1.style.display="none";
    
    var d2=document.getElementById("cmdDelete");
    d2.style.display="none";
    
  document.AcHead_SubGroupForm.txtSubGrpCode.disabled=false;
 }
 
 function Exit()
 {
    window.open('','_parent','');
    window.close();
 }
 
  function loadValuesFromTable(rid)
    {      
    
    var d=document.getElementById("cmdUpdate");
     d.style.display="block";
    
     var d1=document.getElementById("cmdAdd");
     d1.style.display="none";
    
    var d2=document.getElementById("cmdDelete");
    d2.style.display="block";
  document.AcHead_SubGroupForm.txtSubGrpCode.disabled=true;
    
          var r=document.getElementById(rid); 
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          var table=document.getElementById("Existing");
          
          document.AcHead_SubGroupForm.txtSubGrpCode.value=rcells.item(1).firstChild.nodeValue;
          
          document.AcHead_SubGroupForm.txtSubGrpDesc.value=rcells.item(2).firstChild.nodeValue;
          
          
      
    }
    
     function nullCheck()
     {
        
    if((document.AcHead_SubGroupForm.txtSubGrpDesc.value=="") || (document.AcHead_SubGroupForm.txtSubGrpDesc.value<=0))
                  { 
                       alert("Please Enter the Sub Group Description");
                       document.AcHead_SubGroupForm.txtSubGrpDesc.focus();
                       return false;
                  }
                  return true;
     }

 function callServer(command,param)
 {
   
       var SubGrpCode=document.AcHead_SubGroupForm.txtSubGrpCode.value;
       var SubGrpDesc=document.AcHead_SubGroupForm.txtSubGrpDesc.value;
       
       var flag="";
       if(command=="Add")
        {           var flag=nullCheck();
                    if(flag==true)
                  { 
                    url="../../../../../AcHead_SubGrpServ.view?command=Add&txtSubGrpDesc=" + SubGrpDesc;
                   // alert(url);
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
                    url="../../../../../AcHead_SubGrpServ.view?command=Update&txtSubGrpCode="+SubGrpCode+"&txtSubGrpDesc=" + SubGrpDesc;
                     var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            }   
                    req.send(null);
                    }

        }
        
         else if(command=="Delete")
        {    
               if(confirm("Do You Really want to Delete the Selected Record"))
             {  
                    url="../../../../../AcHead_SubGrpServ.view?command=Delete&txtSubGrpCode="+SubGrpCode;
                   // alert(url);
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
                      alert("Records are not Deleted ");
                    }
        }
        
         else if(command=="Load")
        {
                    
                    url="../../../../../AcHead_SubGrpServ.view?command=Load&txtSubGrpCode="+SubGrpCode;
                     var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            }   
                    req.send(null);
                
    
        }
        
}  

function processResponse(req)
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
              deleteRow(baseResponse)
              }
              else if(command=="check")
              {
              checkRow(baseResponse);
              }
              else if(command=="Update")
              {
              updateRow(baseResponse);
              }
              else if(command=="Load")
              {
              loadRow(baseResponse);
              }
          }
        }
  }

function updateRow(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {   
           alert("Record Updated Successfully.");
               var strsubCode=document.AcHead_SubGroupForm.txtSubGrpCode.value;
               
              
                            var items=new Array();
                            items[0]=strsubCode;
                            items[1]=document.AcHead_SubGroupForm.txtSubGrpDesc.value;
                              var r=document.getElementById(items[0]);    
                              var rcells=r.cells;
                            rcells.item(1).firstChild.nodeValue=items[0];
                            rcells.item(2).firstChild.nodeValue=items[1];
                       
                           clearAll();
                           
       }
       else
       {
           alert("Failed to update values");
       }                                  
    }

function deleteRow(baseResponse)
  {
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  
                  if(flag=="success")
                  {
                      var SubCode=baseResponse.getElementsByTagName("SubCode")[0].firstChild.nodeValue;
                      var tbody=document.getElementById("Existing");     
                      var r=document.getElementById(SubCode);    
                      var ri=r.rowIndex;               
                      tbody.deleteRow(ri); 
                                clearAll();
                               
                      alert("Selected Details are Deleted");                      
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
              var code=baseResponse.getElementsByTagName("code")[0].firstChild.nodeValue; 
                    alert("Sub-Group Code: "+code);
                     alert("Record Inserted Into Database successfully.");
                     //get elements
                     var items=new Array();                   
                    items[0]=code;
                   items[1]=document.AcHead_SubGroupForm.txtSubGrpDesc.value;
              
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
                     document.AcHead_SubGroupForm.txtSubGrpCode.value="";
                            document.AcHead_SubGroupForm.txtSubGrpDesc.value="";
                                                  
             }
             else
             {
                     alert("Failed to Insert Values");
             }
        
    }
    
    
    

