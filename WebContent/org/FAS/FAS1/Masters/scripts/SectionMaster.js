
var cadid;
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
   document.frmSectionMaster.txtsectionId.value="";
   document.frmSectionMaster.txtSectionName.value="";
  // document.frmSectionMaster.txtGroup.value="";
   document.frmSectionMaster.CmdAdd.disabled=false;
   document.frmSectionMaster.CmdUpdate.disabled=true;
   document.frmSectionMaster.CmdDelete.disabled=true;
   
   var tbody=document.getElementById("tblList");
        var t=0;
        
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
        callServer('Get','null');
 }
 
 function Exit()
 {
    self.close();
 }
 
 
 
 //******************************Validation Checking**************************//
 function nullCheck()
        {
                  
                  if((document.frmSectionMaster.txtSectionName.value=="") || (document.frmSectionMaster.txtSectionName.value.length<=0))
                  { 
                       alert("Please Enter the Section Description");
                       document.frmSectionMaster.txtSectionName.focus();
                       return false;
                  }
             /*     
                  if((document.frmSectionMaster.txtGroup.value=="") ||(document.frmSectionMaster.txtGroup.value.length<=0) || (document.frmSectionMaster.txtGroup.value=="0"))
                  {
                    alert("Please Select Reason Abbrivation");
                    document.frmSectionMaster.txtGroup.focus();
                    return false;
                  } */
                  return true; 
        }
        
        
//******************************************** CallServer Coding *******************//


function callServer(command,param)
 {
   
       
       var txtsectionId=document.frmSectionMaster.txtsectionId.value;
       var txtSectionName=document.frmSectionMaster.txtSectionName.value;
      // var txtGroup=document.frmSectionMaster.txtGroup.value;
        var url="";
        
       
       if(command=="Add")
        {              var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../SectionMaster?command=Add&txtSectionName=" + txtSectionName;
                   
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
                    url="../../../../../SectionMaster?command=Update&txtsectionId="+txtsectionId+"&txtSectionName=" + txtSectionName;
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
                    url="../../../../../SectionMaster?command=Delete&txtsectionId="+txtsectionId;
                   var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }   
                    req.send(null);
        }
        else if(command=="Get")
        {               
            url="../../../../../SectionMaster?command=Get";
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            }   
                    req.send(null);
        }
      
        else if(command=="Load")
        {               
            url="../../../../../SectionMaster?command=Load";
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            }   
                    req.send(null);
        }
        
}  


//********************************* CallServer Response Coding ***************************************//

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
              
              else if(command=="Update")
              {
              updateRow(baseResponse);
              }
               else if(command=="Get")
              { 
              getRow(baseResponse);
              }
             
              else if(command=="Load")
              {
              getRow(baseResponse);
              }
          }
        }
  }


function addRow(baseResponse)
{
               var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
               
              if(flag=="success")
              {                        
                 alert("Record Inserted Into Database successfully.");
                 did=baseResponse.getElementsByTagName("txtsectionId")[0].firstChild.nodeValue; 
                 alert("Your Section Code Is " +  did);
                 var items=new Array();                                     
                 cadid=baseResponse.getElementsByTagName("txtsectionId")[0].firstChild.nodeValue;
                 sdesc=baseResponse.getElementsByTagName("txtSectionName")[0].firstChild.nodeValue;
                // txtGroup=baseResponse.getElementsByTagName("txtGroup")[0].firstChild.nodeValue;
               
                 var tbody=document.getElementById("tblList");
                 var table=document.getElementById("Existing");
                 
                                             
                 var mycurrent_row=document.createElement("TR");
                 mycurrent_row.id=cadid;
                 var cell=document.createElement("TD");
                 var anc=document.createElement("A");       
                 var url="javascript:loadValuesFromTable('" + cadid + "')";              
                 anc.href=url;
                 var txtedit=document.createTextNode("Edit");
                 anc.appendChild(txtedit);
                 cell.appendChild(anc);
                 mycurrent_row.appendChild(cell);
             
                 var cell2 =document.createElement("TD");    
                 var txtcadid=document.createTextNode(cadid);                         
                 cell2.appendChild(txtcadid);       
                 mycurrent_row.appendChild(cell2);       

                 var cell3 =document.createElement("TD");    
                 var txtsdesc=document.createTextNode(sdesc);                         
                 cell3.appendChild(txtsdesc);       
                 mycurrent_row.appendChild(cell3);
              /*   
                 var cell4 =document.createElement("TD");    
                 var txtassettype=document.createTextNode(txtGroup);                         
                 cell4.appendChild(txtassettype);       
                 mycurrent_row.appendChild(cell4);*/
                 
                 tbody.appendChild(mycurrent_row);
                
                 document.frmSectionMaster.CmdAdd.disabled=false;
                document.frmSectionMaster.CmdUpdate.disabled=true;
                document.frmSectionMaster.CmdDelete.disabled=true;           
                document.frmSectionMaster.txtsectionId.value="";
                document.frmSectionMaster.txtSectionName.value="";
              //  document.frmSectionMaster.txtGroup.value="";
               }
}

function updateRow(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {   
           alert("Record Updated Successfully.");
               var items=new Array();
               items[0]=document.frmSectionMaster.txtsectionId.value;
               items[1]=document.frmSectionMaster.txtSectionName.value;
            //   items[2]=document.frmSectionMaster.txtGroup.value;
               var r=document.getElementById(items[0]);    
               var rcells=r.cells;
                rcells.item(1).firstChild.nodeValue=items[0];
                rcells.item(2).firstChild.nodeValue=items[1];
              //  rcells.item(3).firstChild.nodeValue=items[2];
                document.frmSectionMaster.CmdAdd.disabled=false;
                document.frmSectionMaster.CmdUpdate.disabled=true;
                document.frmSectionMaster.CmdDelete.disabled=true;           
                document.frmSectionMaster.txtsectionId.value="";
                document.frmSectionMaster.txtSectionName.value="";
              //  document.frmSectionMaster.txtGroup.value="";
                           
       }
       else
       {
           alert("failed to update values");
       }                                  
    }



function deleteRow(baseResponse)
  {
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  
                  if(flag=="success")
                  {
                      var txtsectionId=baseResponse.getElementsByTagName("ReasonCode")[0].firstChild.nodeValue;
                      var tbody=document.getElementById("Existing");     
                      var r=document.getElementById(txtsectionId);    
                      var ri=r.rowIndex;               
                      tbody.deleteRow(ri); 
                       document.frmSectionMaster.txtsectionId.value="";
                       document.frmSectionMaster.txtSectionName.value="";
                     //  document.frmSectionMaster.txtGroup.value="";
                       //document.frmSectionMaster.txtGroup.selectedIndex=0;
                        document.frmSectionMaster.CmdAdd.disabled=false;
                        document.frmSectionMaster.CmdUpdate.disabled=true;
                        document.frmSectionMaster.CmdDelete.disabled=true;      
                               
                      alert("Selected Section Details are Deleted");                      
                  }
                  else
                  {
                      alert("Unable to Delete");
                  }
   
  }


function getRow(baseResponse)
    { 
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
    var seq=0;  
              if(flag=="success")
              {
              var items=new Array(); 
              var items_secid=new Array();
              var items_secname=new Array();
              var items_grpname=new Array();
                 cadid=baseResponse.getElementsByTagName("txtsectionId")[0].firstChild.nodeValue;
                 sdesc=baseResponse.getElementsByTagName("txtSectionName")[0].firstChild.nodeValue;
                // txtGroup=baseResponse.getElementsByTagName("txtGroup")[0].firstChild.nodeValue;
                
                 var tbody=document.getElementById("tblList");
                 var table=document.getElementById("Existing");
                    
                   var txtsectionId=baseResponse.getElementsByTagName("txtsectionId"); 
                   
                  for(var k=0;k<txtsectionId.length;k++)
                  {
                  items_secid[k]=baseResponse.getElementsByTagName("txtsectionId")[k].firstChild.nodeValue;
                  items_secname[k]=baseResponse.getElementsByTagName("txtSectionName")[k].firstChild.nodeValue;
                // items_grpname[k]=baseResponse.getElementsByTagName("txtGroup")[k].firstChild.nodeValue;
                  }
                 var sectionId=baseResponse.getElementsByTagName("txtsectionId"); 
                  for(var k=0;k<sectionId.length;k++)
                  {
                 var mycurrent_row=document.createElement("TR");
                 mycurrent_row.id=items_secid[k];
                 var cell2=document.createElement("TD");
                 var anc=document.createElement("A");       
                 var url="javascript:loadValuesFromTable('" + items_secid[k] + "')";              
                 anc.href=url;
                 var txtedit=document.createTextNode("Edit");
                 anc.appendChild(txtedit);
                 cell2.appendChild(anc);
                 mycurrent_row.appendChild(cell2);
             
                 var cell2 =document.createElement("TD");    
                 var txtcadid=document.createTextNode(items_secid[k]);                         
                 cell2.appendChild(txtcadid);       
                 mycurrent_row.appendChild(cell2);       

                 var cell2 =document.createElement("TD");    
                 var txtsdesc=document.createTextNode(items_secname[k]);                         
                 cell2.appendChild(txtsdesc);       
                 mycurrent_row.appendChild(cell2);
                 
             /*    var cell2 =document.createElement("TD");    
                 var txtassettype=document.createTextNode(items_grpname[k]);                         
                 cell2.appendChild(txtassettype);       
                 mycurrent_row.appendChild(cell2);*/
                 
                 tbody.appendChild(mycurrent_row);
                 }
              }
            
                  else
                  {
                    alert("Failed to Load Values");
                  }
           }
           

function loadValuesFromTable(rid)
    {      
          var r=document.getElementById(rid); 
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          var table=document.getElementById("Existing");
          
          document.frmSectionMaster.txtsectionId.value=rcells.item(1).firstChild.nodeValue;
          document.frmSectionMaster.txtSectionName.value=rcells.item(2).firstChild.nodeValue;
        //  document.frmSectionMaster.txtGroup.value=rcells.item(3).firstChild.nodeValue;
          document.frmSectionMaster.CmdAdd.disabled=true;
        document.frmSectionMaster.CmdUpdate.disabled=false;
        document.frmSectionMaster.CmdDelete.disabled=false;
          
          document.frmSectionMaster.CmdDelete.focus();
        
    }