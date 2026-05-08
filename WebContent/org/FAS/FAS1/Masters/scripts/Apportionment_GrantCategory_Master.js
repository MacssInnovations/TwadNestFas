
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
   document.frmApportGrantCatMaster.txtApportGrantCatCode.value="";
   document.frmApportGrantCatMaster.txtApportGrantCatDesc.value="";
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
                  
                  if(document.frmApportGrantCatMaster.txtApportGrantCatDesc.value=="")
                  { 
                       alert("Please Enter the Apportion Grant Class Description");
                       document.frmApportGrantCatMaster.txtApportGrantCatDesc.focus();
                       return false;
                  }
                  
                  return true;
        }
        
        
//******************************************** CallServer Coding *******************//


function callServer(command,param)
 {
   
       
       var ApportGrantCatCode=document.frmApportGrantCatMaster.txtApportGrantCatCode.value;
       var ApportGrantCatDesc=document.frmApportGrantCatMaster.txtApportGrantCatDesc.value;
       var url="";

       if(command=="Add")
        {              
    	   			var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../Apportionment_GrantCategory_Master?command=Add&ApportGrantCatCode=" + ApportGrantCatCode+"&ApportGrantCatDesc="+ApportGrantCatDesc;
                    var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    };   
                    req.send(null);
                    }
         }
        else if(command=="Update")
        {
                    var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../Apportionment_GrantCategory_Master?command=Update&ApportGrantCatCode=" + ApportGrantCatCode+"&ApportGrantCatDesc="+ApportGrantCatDesc;
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
        			url="../../../../../Apportionment_GrantCategory_Master?command=Delete&ApportGrantCatCode=" + ApportGrantCatCode+"&ApportGrantCatDesc="+ApportGrantCatDesc;
                    var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    };   
                    req.send(null);
        }
        else if(command=="Get")
        {               
		            url="../../../../../Apportionment_GrantCategory_Master?command=Get";
		            var req=getTransport();
		            req.open("GET",url,true); 
		            req.onreadystatechange=function()
		            {
		               processResponse(req);
		            };   
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
            	  deleteRow(baseResponse);
              }
              
              else if(command=="Update")
              {
            	  updateRow(baseResponse);
              }
               else if(command=="Get")
              { 
            	   getRow(baseResponse);
              }
              else if(command=="Asset")
              { 
            	  assetRow(baseResponse);
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
         var items=new Array();                                     
         var ApportCode=baseResponse.getElementsByTagName("ApportCode")[0].firstChild.nodeValue;
         var ApportDesc=baseResponse.getElementsByTagName("ApportDesc")[0].firstChild.nodeValue;
         
         var tbody=document.getElementById("tblList");
         var table=document.getElementById("Existing");
                                     
         var mycurrent_row=document.createElement("TR");
         mycurrent_row.id=ApportCode;
         var cell=document.createElement("TD");
         var anc=document.createElement("A");       
         var url="javascript:loadValuesFromTable('" + ApportCode + "')";              
         anc.href=url;
         var txtedit=document.createTextNode("Edit");
         anc.appendChild(txtedit);
         cell.appendChild(anc);
         mycurrent_row.appendChild(cell);
     
         var cell2 =document.createElement("TD");    
         var tnodeApportCode=document.createTextNode(ApportCode);                         
         cell2.appendChild(tnodeApportCode);       
         mycurrent_row.appendChild(cell2);       

         var cell3 =document.createElement("TD");    
         var tnodeApportDesc=document.createTextNode(ApportDesc);                         
         cell3.appendChild(tnodeApportDesc);       
         mycurrent_row.appendChild(cell3);
         
         tbody.appendChild(mycurrent_row);
        
         document.frmApportGrantCatMaster.CmdAdd.disabled=false;
         document.frmApportGrantCatMaster.CmdUpdate.disabled=true;
         document.frmApportGrantCatMaster.CmdDelete.disabled=true;      
         
         document.frmApportGrantCatMaster.txtApportGrantCatCode.value="";
         document.frmApportGrantCatMaster.txtApportGrantCatDesc.value="";
         callServer('Get','');
       }
}

function updateRow(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   if(flag=="success")
   {   
       alert("Record Updated Successfully.");
       var items=new Array();
       items[0]=baseResponse.getElementsByTagName("ApportCode")[0].firstChild.nodeValue;
       items[1]=baseResponse.getElementsByTagName("ApportDesc")[0].firstChild.nodeValue;

       var r=document.getElementById(items[0]);    
       var rcells=r.cells;
       rcells.item(1).firstChild.nodeValue=items[0];
       rcells.item(2).firstChild.nodeValue=items[1];

       document.frmApportGrantCatMaster.CmdAdd.disabled=false;
       document.frmApportGrantCatMaster.CmdUpdate.disabled=true;
       document.frmApportGrantCatMaster.CmdDelete.disabled=true;         
        
       document.frmApportGrantCatMaster.txtApportGrantCatCode.value="";
       document.frmApportGrantCatMaster.txtApportGrantCatDesc.value="";
       callServer('Get','');                
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
		  var ApportCode=baseResponse.getElementsByTagName("ApportCode")[0].firstChild.nodeValue;
		  var tbody=document.getElementById("Existing");     
		  var r=document.getElementById(ApportCode);    
		  var ri=r.rowIndex;               
		  tbody.deleteRow(ri); 
		  document.frmApportGrantCatMaster.txtApportGrantCatCode.value="";
		  document.frmApportGrantCatMaster.txtApportGrantCatDesc.value="";
		
		  document.frmApportGrantCatMaster.CmdAdd.disabled=false;
		  document.frmApportGrantCatMaster.CmdUpdate.disabled=true;
		  document.frmApportGrantCatMaster.CmdDelete.disabled=true;      
		           
		  alert("Cancelled Successfully");
		  callServer('Get','');
	  }
	  else
	  {
	      alert("Unable to Delete");
      }
}


function getRow(baseResponse)
{   
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
      
      if(flag=="success")
      {          
			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var t=0;
			for(t=tbody.rows.length-1;t>=0;t--)
			{
			   tbody.deleteRow(0);
			}   
			                     
			var len=baseResponse.getElementsByTagName("ApportCode").length;
                
			for(var k=0;k<len;k++)
            {
             
             var ApportCode = baseResponse.getElementsByTagName("ApportCode")[k].firstChild.nodeValue;
             var ApportDesc = baseResponse.getElementsByTagName("ApportDesc")[k].firstChild.nodeValue;
             var view = baseResponse.getElementsByTagName("status")[k].firstChild.nodeValue;
             
             var mycurrent_row=document.createElement("TR");
             mycurrent_row.id=ApportCode;
             var cell=document.createElement("TD");
             var anc=document.createElement("A");
             if(view=="C"){
            	 var priceSpan = document.createElement("span");
      			priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
      			priceSpan.appendChild(document.createTextNode("Cancel"));			
      			cell.appendChild(priceSpan);      			
             }else{
            	 var url="javascript:loadValuesFromTable('" + ApportCode + "')";              
                 anc.href = url;
                 var txtedit=document.createTextNode("Edit");
                 anc.appendChild(txtedit);
                 cell.appendChild(anc);
             }             
             mycurrent_row.appendChild(cell);
         
             var cell2 =document.createElement("TD");    
             var tnodeApportCode=document.createTextNode(ApportCode);                         
             cell2.appendChild(tnodeApportCode);       
             mycurrent_row.appendChild(cell2);       

             var cell3 =document.createElement("TD");    
             var tnodeApportDesc=document.createTextNode(ApportDesc);                         
             cell3.appendChild(tnodeApportDesc);       
             mycurrent_row.appendChild(cell3);
             
             var cell4 =document.createElement("TD");    
             if(view=="L"){
            	 var txtstat=document.createTextNode("LIVE");
             }else{
            	 var txtstat=document.createTextNode("CANCEL");
             }       
             cell4.appendChild(txtstat);       
             mycurrent_row.appendChild(cell4);
             
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
      
      document.frmApportGrantCatMaster.txtApportGrantCatCode.value=rcells.item(1).firstChild.nodeValue;
      document.frmApportGrantCatMaster.txtApportGrantCatDesc.value=rcells.item(2).firstChild.nodeValue;

      document.frmApportGrantCatMaster.CmdAdd.disabled=true;
      document.frmApportGrantCatMaster.CmdUpdate.disabled=false;
      document.frmApportGrantCatMaster.CmdDelete.disabled=false;
    
      document.frmApportGrantCatMaster.CmdDelete.focus();
}