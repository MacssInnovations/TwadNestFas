
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
   document.AcHead_MinorForm.txtMinorCode.value="";
   document.AcHead_MinorForm.txtMinorDesc.value="";
   document.AcHead_MinorForm.comMajorCode.selectedIndex=0;
   
    var d=document.getElementById("cmdAdd");
     d.style.display="block";
    
     var d1=document.getElementById("cmdUpdate");
     d1.style.display="none";
    
    var d2=document.getElementById("cmdDelete");
    d2.style.display="none";
    
  document.AcHead_MinorForm.txtMinorCode.disabled=false;
 }
 
 function Exit()
 {
    window.open('','_parent','');
    window.close();
 }
 
  function loadValuesFromTable(rid)
    {      
    //alert(rid);
    var d=document.getElementById("cmdUpdate");
     d.style.display="block";
    
     var d1=document.getElementById("cmdAdd");
     d1.style.display="none";
    
    var d2=document.getElementById("cmdDelete");
    d2.style.display="block";
  document.AcHead_MinorForm.txtMinorCode.disabled=true;
    
          var r=document.getElementById(rid); 
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          var table=document.getElementById("Existing");
          
           document.AcHead_MinorForm.comMajorCode.value=rcells.item(1).firstChild.nodeValue;
          
          document.AcHead_MinorForm.txtMinorCode.value=rcells.item(2).firstChild.nodeValue;
          
          document.AcHead_MinorForm.txtMinorDesc.value=rcells.item(3).firstChild.nodeValue;
          
          
      
    }
    
     function nullCheck()
     {
        
    if((document.AcHead_MinorForm.txtMinorDesc.value=="") || (document.AcHead_MinorForm.txtMinorDesc.value<=0))
                  { 
                       alert("Please Enter the Minor Group Description");
                       document.AcHead_MinorForm.txtMinorDesc.focus();
                       return false;
                  }
                  
    else if((document.AcHead_MinorForm.comMajorCode.value=="") || (document.AcHead_MinorForm.comMajorCode.value<=0))
                  { 
                       alert("Please Enter the Major Code");
                       document.AcHead_MinorForm.comMajorCode.focus();
                       return false;
                  }
                  return true;
     }


function loadData()
{
  var majcode=document.AcHead_MinorForm.comMajorCode.value;
       if(majcode!="")
        {
            url="../../../../../AcHead_MinorServ.view?command=Load&comMajorCode="+majcode;
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            }   
                    req.send(null);
        }
}
 function callServer(command,param)
 {
   
       var SubGrpCode=document.AcHead_MinorForm.txtMinorCode.value;
       var SubGrpDesc=document.AcHead_MinorForm.txtMinorDesc.value;
       var majcode=document.AcHead_MinorForm.comMajorCode.value;
       var flag="";
       if(command=="Add")
        {           var flag=nullCheck();
                    if(flag==true)
                  { 
                    url="../../../../../AcHead_MinorServ.view?command=Add&comMajorCode="+majcode+"&txtMinorDesc=" + SubGrpDesc;
                    //alert(url);
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
                    url="../../../../../AcHead_MinorServ.view?command=Update&comMajorCode="+majcode+"&txtMinorCode="+SubGrpCode+"&txtMinorDesc=" + SubGrpDesc;
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
                    url="../../../../../AcHead_MinorServ.view?command=Delete&txtMinorCode="+SubGrpCode;
                    //alert(url);
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
               var strsubCode=document.AcHead_MinorForm.txtMinorCode.value;
               var strmajCode=document.AcHead_MinorForm.comMajorCode.value;
              
                            var items=new Array();
                            items[0]=strmajCode;
                            items[1]=strsubCode;
                            items[2]=document.AcHead_MinorForm.txtMinorDesc.value;
                            
                              var r=document.getElementById(items[1]);    
                              var rcells=r.cells;
                            rcells.item(1).firstChild.nodeValue=items[0];
                            rcells.item(2).firstChild.nodeValue=items[1];
                             rcells.item(3).firstChild.nodeValue=items[2];
                             
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
                var code1=baseResponse.getElementsByTagName("code1")[0].firstChild.nodeValue; 
                alert("Minor Group Code: "+code1);
                     alert("Record Inserted Into Database successfully.");
                     //get elements
                     var items=new Array();                   
                    items[0]=document.AcHead_MinorForm.comMajorCode.value;
                   items[1]=code1;
                    items[2]=document.AcHead_MinorForm.txtMinorDesc.value;
                     var tbody=document.getElementById("tblList");
                    
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=items[1];
                     var cell=document.createElement("TD");
                     
                     var anc=document.createElement("A");       
                     var url="javascript:loadValuesFromTable('" + items[1] + "')";              
                     anc.href=url;
                     var txtedit=document.createTextNode("Edit");
                     anc.appendChild(txtedit);
                     cell.appendChild(anc);
                     mycurrent_row.appendChild(cell);
                     var i=0;
                     var cell2;
                     for(i=0;i<3;i++)
                     {                                           
                         cell2=document.createElement("TD");                               
                         var currenttext=document.createTextNode(items[i]);                         
                         cell2.appendChild(currenttext);       
                         mycurrent_row.appendChild(cell2);       
                     }  
                     
                     tbody.appendChild(mycurrent_row); 
                     
                     
                     document.AcHead_MinorForm.txtMinorCode.value="";
                            document.AcHead_MinorForm.txtMinorDesc.value="";
                              document.AcHead_MinorForm.comMajorCode.selectedIndex=0;                    
             }
             else
             {
                     alert("Failed to Insert Values");
             }
        
    }
    
    
    

function loadRow(baseResponse)
{

       var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag=="success")
              {         
                        var tbody=document.getElementById("tblList");
                        var t=0;
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
                
                    var items=new Array();    
                    var NoOfHeads=baseResponse.getElementsByTagName("MinorHeadcode");

                for(j=0;j<NoOfHeads.length;j++)
                 {
                    items[0]=baseResponse.getElementsByTagName("MajHeadcode")[j].firstChild.nodeValue; 
                    items[1]=baseResponse.getElementsByTagName("MinorHeadcode")[j].firstChild.nodeValue; 
                    items[2]=baseResponse.getElementsByTagName("MinorHeadDesc")[j].firstChild.nodeValue; 
                     var tbody=document.getElementById("tblList");
                    
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=items[1];
                     var cell=document.createElement("TD");
                     
                     var anc=document.createElement("A");       
                     var url="javascript:loadValuesFromTable('" + items[1] + "')";              
                     anc.href=url;
                     var txtedit=document.createTextNode("Edit");
                     anc.appendChild(txtedit);
                     cell.appendChild(anc);
                     mycurrent_row.appendChild(cell);
                     var i=0;
                     var cell2;
                     
                         for(i=0;i<3;i++)
                         {                                           
                             cell2=document.createElement("TD");                               
                             var currenttext=document.createTextNode(items[i]);                         
                             cell2.appendChild(currenttext);       
                             mycurrent_row.appendChild(cell2);       
                         }  
                     
                     tbody.appendChild(mycurrent_row); 
                 }
                     
                     document.AcHead_MinorForm.txtMinorCode.value="";
                     document.AcHead_MinorForm.txtMinorDesc.value="";
                     //document.AcHead_MinorForm.comMajorCode.value=0;                    
             }
             else
             {
                     alert("Failed to Insert Values");
             }
}
