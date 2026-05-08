seq=0;
function getTransport()
{
var req=false;
try
{
req=new ActiveXObject("Msxml2.XMLHTTP");
}catch(e1)
 {
    try{
    req=new ActiveXObject("Microsoft.XMLHTTP");
    }
    catch(e2)
    {
    req=false;
    }
 }
    if(!req && typeof XMLHttpRequest!='undefined')
        {
        req=new XMLHttpRequest();
        }
   return req;
}    

function capitalise()
{
var id=document.getElementById("mcode").value;
capid=id.toUpperCase();
document.getElementById("mcode").value=capid;
}

function callServer(command,param)
{
      // alert(command);
       var miscode=document.MisCategory.mcode.value;
       var misdesc=document.MisCategory.mspec.value;
       if(command=="Add")
       {           
                    if(miscode=="")
                        alert("Fill in Miscellaneous Code")
                    else if(misdesc=="")
                        alert("Fill in Miscellaneous Description")
                    else
                    {
                        url="../../../../../../WQS_Miscellaneous?command=Add&MisCode="+miscode+"&MisDesc="+misdesc;
                        var req=getTransport();
                        req.open("GET",url,true);       
                        req.onreadystatechange=function()
                        {
                            processResponse(req);
                        }  
                        req.send(null);        
                    }
        }
        else if(command=="Get")
        {              
            clearAll();
            url="../../../../../../WQS_Miscellaneous?command=Get";
            var req=getTransport();
            req.open("GET",url,true);
            req.onreadystatechange=function()
            {
               processResponse(req);
            }  
            req.send(null);
        }
        else if(command=="Delete")
        { 
                   if(miscode=="")
                        alert("Fill in Miscellaneous Code")
                    else if(misdesc=="")
                        alert("Fill in Miscellaneous Description")
                    else
                    {
                        url="../../../../../../WQS_Miscellaneous?command=Delete&MisCode="+miscode+"&MisDesc="+misdesc;
                        var req=getTransport();
                        req.open("GET",url,true);       
                        req.onreadystatechange=function()
                        {
                            processResponse(req);
                        }  
                        req.send(null);
                    }
        }
        else 
        {
                    if(miscode=="")
                        alert("Fill in Miscellaneous Code")
                    else if(misdesc=="")
                        alert("Fill in Miscellaneous Description")
                    else
                    {
                        url="../../../../../../WQS_Miscellaneous?command=Update&MisCode="+miscode+"&MisDesc="+misdesc;
                        var req=getTransport();
                        req.open("GET",url,true);       
                        req.onreadystatechange=function()
                        {
                            processResponse(req);
                        }  
                        req.send(null);
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
              else if(command=="Get")
              {
                  getRow(baseResponse);
              }
              else if(command=="Delete")
              {
                  deleteRow(baseResponse);
              }
              else
              {
                  updateRow(baseResponse);
              }
          }
    }
}

function addRow(baseResponse)
{
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="Success")
            {                       
                 alert("Record Inserted Into Database successfully.");
                 clearAll();
                 var items=new Array();    
                 miscode=baseResponse.getElementsByTagName("MisCode")[0].firstChild.nodeValue;
                 misdesc=baseResponse.getElementsByTagName("MisDesc")[0].firstChild.nodeValue;
                                  
                 var tbody=document.getElementById("tblList");
                 var table=document.getElementById("Existing");
                 
                 seq=seq+1;                         
                 var mycurrent_row=document.createElement("TR");
                 mycurrent_row.id=seq;
                 var cell=document.createElement("TD");
                 var anc=document.createElement("A");      
                
                 var url="javascript:loadValuesFromTable('" + seq + "')";             
                 anc.href=url;
                 
                 var txtedit=document.createTextNode("Edit");
                 anc.appendChild(txtedit);
                 cell.appendChild(anc);
                 mycurrent_row.appendChild(cell);
                    
                        
                         var cell2 =document.createElement("TD");   
                         var mcode=document.createTextNode(miscode);                        
                         cell2.appendChild(mcode);      
                         mycurrent_row.appendChild(cell2);   
                         
                         
                         var cell3 =document.createElement("TD");   
                         var mspec=document.createTextNode(misdesc);   
                         cell3.appendChild(mspec);      
                         mycurrent_row.appendChild(cell3);
                        
                         tbody.appendChild(mycurrent_row);
               
                document.MisCategory.CmdAdd.disabled=false;
                document.MisCategory.CmdUpdate.disabled=true;
                document.MisCategory.CmdDelete.disabled=true;                                                                            
            }
            else
            {
                 alert("Record already exists, Insertion not possible");
            }
}


function getRow(baseResponse)
{  
              
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="Success")
    {         
            var tbody=document.getElementById("tblList");
            var table=document.getElementById("Existing");
            
            var count=baseResponse.getElementsByTagName("count");
            for(var k=0;k<count.length;k++)
            {
                   miscode=baseResponse.getElementsByTagName("MisCode")[k].firstChild.nodeValue;
                   misdesc=baseResponse.getElementsByTagName("MisDesc")[k].firstChild.nodeValue;
                   
                  seq=seq+1;
                  var mycurrent_row=document.createElement("TR");
                  mycurrent_row.id=seq;
                         
                  var cell=document.createElement("TD");
                  var anc=document.createElement("A");      
                  var url="javascript:loadValuesFromTable('" + seq + "')";             
                  anc.href=url;
                  
                  var txtedit=document.createTextNode("Edit");
                  anc.appendChild(txtedit);
                  cell.appendChild(anc);
                  mycurrent_row.appendChild(cell);
                         
                         var cell2 =document.createElement("TD");   
                         var mcode=document.createTextNode(miscode);                        
                         cell2.appendChild(mcode);      
                         mycurrent_row.appendChild(cell2);   
                         
                         
                         var cell3 =document.createElement("TD");   
                         var mspec=document.createTextNode(misdesc);   
                         cell3.appendChild(mspec);      
                         mycurrent_row.appendChild(cell3);
                         
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
          com_id=rid;
          var r=document.getElementById(rid);
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          var table=document.getElementById("Existing");
          
          document.MisCategory.mcode.value=rcells.item(1).firstChild.nodeValue;
          document.MisCategory.mspec.value=rcells.item(2).firstChild.nodeValue;
                    
          document.MisCategory.mcode.disabled=true;
          document.MisCategory.CmdAdd.disabled=true;
          document.MisCategory.CmdUpdate.disabled=false;
          document.MisCategory.CmdDelete.disabled=false;
}

function deleteRow(baseResponse)
{
               var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                 
               if(flag=="Success")
               {
                    var tbody=document.getElementById("Existing"); 
                    
                    var r=document.getElementById(com_id);   
                    var ri=r.rowIndex;              
                    tbody.deleteRow(ri);
                     
                    clearAll()                            
                    alert("Selected Records are Deleted");           
              }
              else if("FoundData")
              {
                    alert("Can not delete this item");
              }
              else
              {
                    alert("Unable to Delete");
              }
  
}

function updateRow(baseResponse)
{
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
      if(flag=="Success")
      {  
               alert("Record Updated Successfully.");
               var items=new Array();
               
               items[0]=document.MisCategory.mcode.value;
               items[1]=document.MisCategory.mspec.value;
                              
               var r=document.getElementById(com_id);   
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


function clearAll()
{
       document.MisCategory.mcode.value="";
       document.MisCategory.mspec.value="";
             
       document.MisCategory.mcode.disabled=false;
       document.MisCategory.CmdAdd.disabled=false;
       document.MisCategory.CmdUpdate.disabled=true;
       document.MisCategory.CmdDelete.disabled=true;    
}

function checkdup()
{
    var cid=document.MisCategory.mcode.value;
    var req=getTransport();
    var url="../../../../../../WQS_Miscellaneous?command=duplicate&id="+cid;
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);
}

function CheckDuplicate(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=='failure')
    {
        alert("Miscellaneous code is already available");
        document.MisCategory.mcode.value="";
	document.MisCategory.mcode.focus(); 
    }
}

function manipulate(req)
{
    if(req.readyState==4)
    {
          if(req.status==200)
          {              
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue;
               CheckDuplicate(baseResponse);
           }
    }
}

