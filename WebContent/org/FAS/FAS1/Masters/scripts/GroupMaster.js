var service;
var __pagination=11;
var destid;
var totalblock=0;
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
   document.frmGroupMaster.txtGroupId.value="";
   document.frmGroupMaster.txtGroupName.value="";
  // document.frmGroupMaster.txtGroup.value="";
   document.frmGroupMaster.CmdAdd.disabled=false;
   document.frmGroupMaster.CmdUpdate.disabled=true;
   document.frmGroupMaster.CmdDelete.disabled=true;
   
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
                  
                  if((document.frmGroupMaster.txtGroupName.value=="") || (document.frmGroupMaster.txtGroupName.value.length<=0))
                  { 
                       alert("Please Enter the Section Description");
                       document.frmGroupMaster.txtGroupName.focus();
                       return false;
                  }
             /*     
                  if((document.frmGroupMaster.txtGroup.value=="") ||(document.frmGroupMaster.txtGroup.value.length<=0) || (document.frmGroupMaster.txtGroup.value=="0"))
                  {
                    alert("Please Select Reason Abbrivation");
                    document.frmGroupMaster.txtGroup.focus();
                    return false;
                  } */
                  return true; 
        }
        
        
//******************************************** CallServer Coding *******************//


function callServer(command,param)
 {
   
       
       var txtGroupId=document.frmGroupMaster.txtGroupId.value;
       var txtGroupName=document.frmGroupMaster.txtGroupName.value;
       var cmbsection =document.frmGroupMaster.cmbsection.value;
       //var sectioncode=document.frmGroupMaster.txtGroup.value;
        var url="";
        
       
       if(command=="Add")
        {              var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../GroupMaster?command=Add&txtGroupName=" + txtGroupName+"&cmbsection=" + cmbsection;
                   
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
                    url="../../../../../GroupMaster?command=Update&txtGroupId="+txtGroupId+"&txtGroupName=" + txtGroupName+"&cmbsection=" + cmbsection;
                   alert(url);
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
                    url="../../../../../GroupMaster?command=Delete&txtGroupId="+txtGroupId;
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
            url="../../../../../GroupMaster?command=Get";
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
            url="../../../../../GroupMaster?command=Load";
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
                 did=baseResponse.getElementsByTagName("txtGroupId")[0].firstChild.nodeValue; 
                 alert("Your Section Code Is " +  did);
                 var items=new Array();                                     
                 cadid=baseResponse.getElementsByTagName("txtGroupId")[0].firstChild.nodeValue;
                 sdesc=baseResponse.getElementsByTagName("txtGroupName")[0].firstChild.nodeValue;
                sectname=baseResponse.getElementsByTagName("cmbsection")[0].firstChild.nodeValue;
               
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
                 var txtsectname=document.createTextNode(sectname);                         
                 cell2.appendChild(txtsectname);       
                 mycurrent_row.appendChild(cell4);
                 
                 var cell3 =document.createElement("TD");    
                 var txtcadid=document.createTextNode(cadid);                         
                 cell3.appendChild(txtcadid);       
                 mycurrent_row.appendChild(cell2);       

                 var cell4 =document.createElement("TD");    
                 var txtsdesc=document.createTextNode(sdesc);                         
                 cell4.appendChild(txtsdesc);       
                 mycurrent_row.appendChild(cell3);
                 
                 
                 tbody.appendChild(mycurrent_row);
                
                 document.frmGroupMaster.CmdAdd.disabled=false;
                document.frmGroupMaster.CmdUpdate.disabled=true;
                document.frmGroupMaster.CmdDelete.disabled=true;           
                document.frmGroupMaster.txtGroupId.value="";
                document.frmGroupMaster.txtGroupName.value="";
              //  document.frmGroupMaster.txtGroup.value="";
               }
}

function updateRow(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {   
           alert("Record Updated Successfully.");
               var items=new Array();
               items[0]=document.frmGroupMaster.txtGroupId.value;
               items[1]=document.frmGroupMaster.txtGroupName.value;
            //   items[2]=document.frmGroupMaster.txtGroup.value;
               var r=document.getElementById(items[0]);    
               var rcells=r.cells;
                rcells.item(1).firstChild.nodeValue=items[0];
                rcells.item(2).firstChild.nodeValue=items[1];
                rcells.item(3).firstChild.nodeValue=items[2];
                document.frmGroupMaster.CmdAdd.disabled=false;
                document.frmGroupMaster.CmdUpdate.disabled=true;
                document.frmGroupMaster.CmdDelete.disabled=true;           
                document.frmGroupMaster.txtGroupId.value="";
                document.frmGroupMaster.txtGroupName.value="";
              //  document.frmGroupMaster.txtGroup.value="";
                           
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
                      var txtGroupId=baseResponse.getElementsByTagName("ReasonCode")[0].firstChild.nodeValue;
                      var tbody=document.getElementById("Existing");     
                      var r=document.getElementById(txtGroupId);    
                      var ri=r.rowIndex;               
                      tbody.deleteRow(ri); 
                       document.frmGroupMaster.txtGroupId.value="";
                       document.frmGroupMaster.txtGroupName.value="";
                       document.frmGroupMaster.cmnsection.value="";
                       //document.frmGroupMaster.txtGroup.selectedIndex=0;
                        document.frmGroupMaster.CmdAdd.disabled=false;
                        document.frmGroupMaster.CmdUpdate.disabled=true;
                        document.frmGroupMaster.CmdDelete.disabled=true;      
                               
                      alert("Selected Group Details are Deleted");                      
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
              var items_seccode=new Array();
              var items_sectionname=new Array();
             // alert(baseResponse.getElementsByTagName("txtGroupId")[0].firstChild.nodeValue);
               //  cadid=baseResponse.getElementsByTagName("txtGroupId")[0].firstChild.nodeValue;
             //    sdesc=baseResponse.getElementsByTagName("txtGroupName")[0].firstChild.nodeValue;
           //      sectname=baseResponse.getElementsByTagName("cmbsection")[0].firstChild.nodeValue;
           //     sectioncode=baseResponse.getElementsByTagName("sectioncode")[0].firstChild.nodeValue;
              //  alert(sectname);
                 var tbody=document.getElementById("tblList");
                 var table=document.getElementById("Existing");
                    
                   var txtGroupId=baseResponse.getElementsByTagName("txtGroupId"); 
                   
                  for(var k=0;k<txtGroupId.length;k++)
                  {
                  items_sectionname[k]=baseResponse.getElementsByTagName("cmbsection")[k].firstChild.nodeValue;
                  items_secid[k]=baseResponse.getElementsByTagName("txtGroupId")[k].firstChild.nodeValue;
                  items_secname[k]=baseResponse.getElementsByTagName("txtGroupName")[k].firstChild.nodeValue;
                  items_seccode[k]=baseResponse.getElementsByTagName("sectioncode")[k].firstChild.nodeValue;
                  }
                 var sectionId=baseResponse.getElementsByTagName("txtGroupId"); 
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
                 var txtsectname=document.createTextNode(items_sectionname[k]);                         
                 cell2.appendChild(txtsectname);       
                 mycurrent_row.appendChild(cell2);
             
                 var cell2 =document.createElement("TD");    
                 var items_code=document.createTextNode(items_seccode[k]);  
                 cell2.appendChild(items_code); 
                 cell2.style.display='none';
                 mycurrent_row.appendChild(cell2);

                 var cell2 =document.createElement("TD");    
                 var txtsdesc=document.createTextNode(items_secname[k]);                         
                 cell2.appendChild(txtsdesc);       
                 mycurrent_row.appendChild(cell2);
                 
             
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
          alert(rcells.item(2).firstChild.nodeValue);
          document.frmGroupMaster.txtGroupId.value=rcells.item(1).firstChild.nodeValue;
         // document.frmGroupMaster.cmbsection.value=rcells.item(2).firstChild.nodeValue;
         document.getElementById("cmbsection").value=rcells.item(3).firstChild.nodeValue;
          document.frmGroupMaster.txtGroupName.value=rcells.item(4).firstChild.nodeValue;
       //    document.frmAccount_Head_Consd_Report.txtaccountheadname.value=Accnt_head;
          document.frmGroupMaster.CmdAdd.disabled=true;
        document.frmGroupMaster.CmdUpdate.disabled=false;
        document.frmGroupMaster.CmdDelete.disabled=false;
          
          document.frmGroupMaster.CmdDelete.focus();
        
    }
    
   




