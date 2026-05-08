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
   document.frmDepreciationCatMaster.txtDepreciationCatCode.value="";
   document.frmDepreciationCatMaster.txtDepreciationCatDesc.value="";
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
	      if(document.frmDepreciationCatMaster.txtDepreciationCatDesc.value=="")
          { 
               alert("Please Enter the Depreciation Category Description");
               document.frmDepreciationCatMaster.txtDepreciationCatDesc.focus();
               return false;
          }
          
          return true;
          
}
        
        
//******************************************** CallServer Coding *******************//

function callServer(command,param)
 {
       
       var DepreciationCatCode=document.frmDepreciationCatMaster.txtDepreciationCatCode.value;
       //alert(DepreciationCatCode);
       var DepreciationCatDesc=document.frmDepreciationCatMaster.txtDepreciationCatDesc.value;
       var url="";

       if(command=="Add")
        {          
    	   			var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../Depreciation_Category_Master?command=Add&DepreciationCatDesc="+DepreciationCatDesc;
                //  alert(url);
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
                    url="../../../../../Depreciation_Category_Master?command=Update&DepreciationCatCode=" + DepreciationCatCode+"&DepreciationCatDesc="+DepreciationCatDesc;
                    var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    };   
                    req.send(null);
                    }
        }
        
        else if(command=="Delete")
        {  
        			url="../../../../../Depreciation_Category_Master?command=Delete&DepreciationCatCode=" + DepreciationCatCode+"&DepreciationCatDesc="+DepreciationCatDesc;
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
		            url="../../../../../Depreciation_Category_Master?command=Get";
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
         var DepreciationCode=baseResponse.getElementsByTagName("DepreciationCode")[0].firstChild.nodeValue;
         var DepreciationDesc=baseResponse.getElementsByTagName("DepreciationDesc")[0].firstChild.nodeValue;
         
         var tbody=document.getElementById("tblList");
         var table=document.getElementById("Existing");
                                     
         var mycurrent_row=document.createElement("TR");
         mycurrent_row.id=DepreciationCode;
         var cell=document.createElement("TD");
         var anc=document.createElement("A");       
         var url="javascript:loadValuesFromTable('" + DepreciationCode + "')";              
         anc.href=url;
         var txtedit=document.createTextNode("Edit");
         anc.appendChild(txtedit);
         cell.appendChild(anc);
         mycurrent_row.appendChild(cell);
     
         var cell2 =document.createElement("TD");    
         var tnodeDepreciationCode=document.createTextNode(DepreciationCode);                         
         cell2.appendChild(tnodeDepreciationCode);       
         mycurrent_row.appendChild(cell2);       

         var cell3 =document.createElement("TD");    
         var tnodeDepreciationDesc=document.createTextNode(DepreciationDesc);                         
         cell3.appendChild(tnodeDepreciationDesc);       
         mycurrent_row.appendChild(cell3);
         
         tbody.appendChild(mycurrent_row);
        
         document.frmDepreciationCatMaster.CmdAdd.disabled=false;
         document.frmDepreciationCatMaster.CmdUpdate.disabled=true;
         document.frmDepreciationCatMaster.CmdDelete.disabled=true;      
         
         document.frmDepreciationCatMaster.txtDepreciationCatCode.value="";
         document.frmDepreciationCatMaster.txtDepreciationCatDesc.value="";
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
       items[0]=baseResponse.getElementsByTagName("DepreciationCode")[0].firstChild.nodeValue;
       items[1]=baseResponse.getElementsByTagName("DepreciationDesc")[0].firstChild.nodeValue;

       var r=document.getElementById(items[0]);    
       var rcells=r.cells;
       rcells.item(1).firstChild.nodeValue=items[0];
       rcells.item(2).firstChild.nodeValue=items[1];

       document.frmDepreciationCatMaster.CmdAdd.disabled=false;
       document.frmDepreciationCatMaster.CmdUpdate.disabled=true;
       document.frmDepreciationCatMaster.CmdDelete.disabled=true;         
        
       document.frmDepreciationCatMaster.txtDepreciationCatCode.value="";
       document.frmDepreciationCatMaster.txtDepreciationCatDesc.value="";
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
		  var DepreciationCode=baseResponse.getElementsByTagName("DepreciationCode")[0].firstChild.nodeValue;
		  var tbody=document.getElementById("Existing");     
		  var r=document.getElementById(DepreciationCode);    
		  /*var ri=r.rowIndex;               
		  tbody.deleteRow(ri); 
		  document.frmDepreciationCatMaster.txtDepreciationCatCode.value="";
		  document.frmDepreciationCatMaster.txtDepreciationCatDesc.value="";
		*/
		  document.frmDepreciationCatMaster.CmdAdd.disabled=false;
		  document.frmDepreciationCatMaster.CmdUpdate.disabled=true;
		  document.frmDepreciationCatMaster.CmdDelete.disabled=true;		           
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
			                     
			var len=baseResponse.getElementsByTagName("DepreciationCode").length;
                
			for(var k=0;k<len;k++)
            {				
             var DepreciationCode = baseResponse.getElementsByTagName("DepreciationCode")[k].firstChild.nodeValue;
             var DepreciationDesc = baseResponse.getElementsByTagName("DepreciationDesc")[k].firstChild.nodeValue;
             var view = baseResponse.getElementsByTagName("status")[k].firstChild.nodeValue;
             
             var mycurrent_row=document.createElement("TR");
             mycurrent_row.id=DepreciationCode;
             var cell=document.createElement("TD");
             var anc=document.createElement("A");
             if(view=="C"){
            	 var priceSpan = document.createElement("span");
      			priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
      			priceSpan.appendChild(document.createTextNode("Cancel"));			
      			cell.appendChild(priceSpan);      			
             }else{
            	 var url="javascript:loadValuesFromTable('" + DepreciationCode + "')";              
                 anc.href = url;
                 var txtedit=document.createTextNode("Edit");
                 anc.appendChild(txtedit);
                 cell.appendChild(anc);
             }             
             mycurrent_row.appendChild(cell);
         
             var cell2 =document.createElement("TD");    
             var tnodeDepreciationCode=document.createTextNode(DepreciationCode);                         
             cell2.appendChild(tnodeDepreciationCode);       
             mycurrent_row.appendChild(cell2);       

             var cell3 =document.createElement("TD");    
             var tnodeDepreciationDesc=document.createTextNode(DepreciationDesc);                         
             cell3.appendChild(tnodeDepreciationDesc);       
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
      
      document.frmDepreciationCatMaster.txtDepreciationCatCode.value=rcells.item(1).firstChild.nodeValue;
      document.frmDepreciationCatMaster.txtDepreciationCatDesc.value=rcells.item(2).firstChild.nodeValue;

      document.frmDepreciationCatMaster.CmdAdd.disabled=true;
      document.frmDepreciationCatMaster.CmdUpdate.disabled=false;
      document.frmDepreciationCatMaster.CmdDelete.disabled=false;
    
      document.frmDepreciationCatMaster.CmdDelete.focus();
}