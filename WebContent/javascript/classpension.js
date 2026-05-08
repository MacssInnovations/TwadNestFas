

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


function refresh()   // NOT NEEDED - Instead use a reset button as "CLEAR"
{
	 alert("inside refresh");
    document.forms[0].classId.value ="";
	document.forms[0].classDesc.value ="";
    

    document.forms[0].cmdAdd.disabled=false;
    document.forms[0].cmdUpdate.disabled=true;
    document.forms[0].cmdDelete.disabled=true;      
   
    var tbody=document.getElementById("tblList");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
   	 tbody.deleteRow(0);
    }
    callServer('Get','null');
}


function callServer(command)
{
  alert("command"+command);
	var classId=document.forms[0].classId.value; 
	var classDesc=document.forms[0].classDesc.value;
      alert(classId);
      var url="";

      if(command=="Add")
       {              
   	   			   url="../addMenu.html?mstPension.classId="+classId+"&mstPension.classDesc="+classDesc;
                   alert("url"+url);
                   var req=getTransport();
                   req.open("GET",url,true);        
                   req.onreadystatechange=function()
                   {
                      processResponse(req);
                   }   
                   req.send(null);
                   }
      
       else if(command=="Update")
       {		alert("inside update");	
                   url="../updateMenu.html?mstPension.classId="+classId+"&mstPension.classDesc="+classDesc;
                   alert("url"+url);
                   var req=getTransport();
                   req.open("GET",url,true);
                   alert("finishes")
                   req.onreadystatechange=function()
                   {
                	 processResponse(req);
                   }   
                   req.send(null);
                   }
    
       else if(command=="Delete")
       {  
       			url="../deleteMenu.html?classId"+classId;
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
    	   			alert("inside get");
		            url="../listMenu.html";
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



function processResponse(req)
{   

  if(req.readyState==4)
  {
      if(req.status==200)
      {    
        alert("req.status"+req.status);
          var baseResponse=req.responseXML.getElementsByTagName("response")[0];
          alert("simply");
          alert("baseresp"+baseResponse);
          
          var tagCommand=baseResponse.getElementsByTagName("command")[0];
          alert("baseresp111"+tagCommand);
          
          var command=tagCommand.firstChild.nodeValue; 
          alert("command"+command);
          
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
      }
  }
}






function addRow(baseResponse)
{
	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	   
	  if(flag=="success")
	  {                       

         alert("Record Inserted Into Database successfully.");
         var items=new Array();                                     
         var classId = baseResponse.getElementsByTagName("classId")[0].firstChild.nodeValue;
         var classDesc = baseResponse.getElementsByTagName("classDesc")[0].firstChild.nodeValue;
         
         
         var tbody=document.getElementById("tblList");
         var table=document.getElementById("Existing");
      
         var mycurrent_row=document.createElement("TR");
         mycurrent_row.id=classId;
         var cell=document.createElement("TD");   
         var anc=document.createElement("A");       
         var url="javascript:loadValuesFromTable('" + classId + "')";     
         
         anc.href=url;
         var txtedit=document.createTextNode("Edit");
         anc.appendChild(txtedit);
         cell.appendChild(anc);
         mycurrent_row.appendChild(cell);
     
         var cell2 =document.createElement("TD"); 
         var classId=document.createTextNode(classId);     
         classId.id = classId;
         cell2.appendChild(classId);       
         mycurrent_row.appendChild(cell2);       

         var cell3 =document.createElement("TD");    
         var classDesc=document.createTextNode(classDesc);                         
         cell3.appendChild(classDesc);       
         mycurrent_row.appendChild(cell3);
         
         tbody.appendChild(mycurrent_row);
        
         document.forms[0].CmdAdd.disabled=false;
         document.forms[0].CmdUpdate.disabled=true;
         document.forms[0].CmdDelete.disabled=true;      
         
         document.forms[0].classId.value=0;
         document.forms[0].classDesc.value=0;
         
         
       }
	   else
	   {
		   alert("Failed to Add values");
	   }

}


function getRow(baseResponse)
{   
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
      alert("Flag"+flag);
      
      if(flag=="success")
      {          
			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var t=0;
			for(t=tbody.rows.length-1;t>=0;t--)
			{
			   tbody.deleteRow(0);
			}   
			                     
			var len=baseResponse.getElementsByTagName("record").length;
                
			for(var k=0;k<len;k++)
            {
             
             var classId = baseResponse.getElementsByTagName("classId")[k].firstChild.nodeValue;
             var classDesc = baseResponse.getElementsByTagName("classDesc")[k].firstChild.nodeValue;
             alert("classDesc"+classDesc);
             
             var mycurrent_row=document.createElement("TR");
             mycurrent_row.id=classId;
             var cell=document.createElement("TD");
             var anc=document.createElement("A");       
             var url="javascript:loadValuesFromTable('" +classId + "')";              
             anc.href = url;
             var txtedit=document.createTextNode("Edit");
             anc.appendChild(txtedit);
             cell.appendChild(anc);
             mycurrent_row.appendChild(cell);
         
             var cell2 =document.createElement("TD");    
             var classId=document.createTextNode(classId);
             cell2.appendChild(classId);       
             mycurrent_row.appendChild(cell2);       

             var cell3 =document.createElement("TD");    
             var classDesc=document.createTextNode(classDesc);                         
             cell3.appendChild(classDesc);       
             mycurrent_row.appendChild(cell3);
             
             tbody.appendChild(mycurrent_row);
            }
      }
      else
      {
        alert("Failed to Load Values");
      }
}


function updateRow(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
  alert("upate flag"+flag);
   if(flag=="success")
   {   
       alert("Record Updated Successfully.");
       var items=new Array();
       items[0]=baseResponse.getElementsByTagName("classId")[0].firstChild.nodeValue;
       items[1]=baseResponse.getElementsByTagName("classDesc")[0].firstChild.nodeValue;

       var r=document.getElementById(items[0]);    
       var rcells=r.cells;
       
        rcells.item(1).firstChild.nodeValue=items[0];
        rcells.item(2).firstChild.nodeValue=items[1];

       document.forms[0].CmdAdd.disabled=false;
       document.forms[0].CmdUpdate.disabled=true;
       document.forms[0].CmdDelete.disabled=true;         
        
       document.forms[0].classId.value=0;
       document.forms[0].classDesc.value="";
                       
   }
   else
   {
       alert("Failed to update values");
   }                                  
}


function loadValuesFromTable(rid)
{     
	
	alert("insideloadvalues"+rid);
      var r=document.getElementById(rid); 
      var rcells=r.cells;
      var tbody=document.getElementById("tblList");
      var table=document.getElementById("Existing");
      alert(rcells.item(0).firstChild.nodeValue);
      
      document.forms[0].classId.value=rcells.item(1).firstChild.id; //nodeValue;
      document.forms[0].classesc.value=rcells.item(2).firstChild.nodeValue;

      document.forms[0].CmdAdd.disabled=true;
      document.forms[0].CmdUpdate.disabled=false;
      document.forms[0].CmdDelete.disabled=false;
    
      document.frmApportGrantRates.CmdDelete.focus();
}




