
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

 
//This function allows users to enter Numbers only (with or without decimal places)
function numFloatInt(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(t.value.indexOf('.') != -1)alert('YES !');
        if((unicode == 8) || (unicode == 46) || (unicode == 37) || (unicode == 39)) 
        {
        	return true;
        	/*  8  --> Backspace
 			    46 --> Delete
 			    37 --> Left arrow
 			    39 --> Right arrow
        	 */
        }
        else
        {
	        var decimalPointIndex = (t.value.indexOf('.'));
	        if (unicode == 46)
	        {            
	            if (decimalPointIndex>=0)
	            return false
	        }
	        else if (unicode<48 || unicode>57)
	        {
	            return false 
	        }
        }
}

 function refresh()
 {
     document.frmApportGrantRates.cmbApportGrantCatCode.value = 1;
	 document.frmApportGrantRates.cmbFinYear.value = 1;
     document.frmApportGrantRates.txtApportRate.value = "";

     document.frmApportGrantRates.CmdAdd.disabled=false;
     document.frmApportGrantRates.CmdUpdate.disabled=true;
     document.frmApportGrantRates.CmdDelete.disabled=true;      
    
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
          if(document.frmApportGrantRates.txtApportRate.value=="")
          { 
               alert("Please Enter the Apportionment Rate");
               document.frmApportGrantRates.txtApportRate.focus();
               return false;
          }
          
          return true;
}
        
        
//******************************************** CallServer Coding *******************//


function callServer(command,param)
 {
   
       var ApportGrantCatCode=document.frmApportGrantRates.cmbApportGrantCatCode.value;
	   var FinancialYear = document.frmApportGrantRates.cmbFinYear.value;
       var ApportGrantRate=document.frmApportGrantRates.txtApportRate.value;
//       alert("ApportGrantRate*******"+ApportGrantRate);
       var url="";

       if(command=="Add")
        {              
    	   			var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../Apportionment_Rates?command=Add&ApportGrantCatCode=" + ApportGrantCatCode+"&FinancialYear="+FinancialYear+"&ApportGrantRate="+ApportGrantRate;
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
                    url="../../../../../Apportionment_Rates?command=Update&ApportGrantCatCode=" + ApportGrantCatCode+"&FinancialYear="+FinancialYear+"&ApportGrantRate="+ApportGrantRate;
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
        			url="../../../../../Apportionment_Rates?command=Delete&ApportGrantCatCode=" + ApportGrantCatCode+"&FinancialYear="+FinancialYear;
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
		            url="../../../../../Apportionment_Rates?command=Get&FinancialYear="+FinancialYear;
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
         var ApportCode = baseResponse.getElementsByTagName("ApportCode")[0].firstChild.nodeValue;
         var ApportRate = baseResponse.getElementsByTagName("ApportRate")[0].firstChild.nodeValue;
         var FinancialYear = baseResponse.getElementsByTagName("FinancialYear")[0].firstChild.nodeValue;
         
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
     //    tnodeDepreciationCode.id = DepreciationCode;
         cell2.appendChild(tnodeApportCode);       
         mycurrent_row.appendChild(cell2);       

     
/*         var cell2 =document.createElement("TD"); 
//         var ApportDesc = document.frmApportGrantRates.cmbApportGrantCatCode.options[ApportCode].text;
         var tnodeApportCode=document.createTextNode(ApportCode);     
         tnodeApportCode.id = ApportCode;
         cell2.appendChild(tnodeApportCode);       
         mycurrent_row.appendChild(cell2);  */     

         var cell3 =document.createElement("TD");    
         var tnodeApportRate=document.createTextNode(ApportRate);                         
         cell3.appendChild(tnodeApportRate);       
         mycurrent_row.appendChild(cell3);
         
         tbody.appendChild(mycurrent_row);
        
         document.frmApportGrantRates.CmdAdd.disabled=false;
         document.frmApportGrantRates.CmdUpdate.disabled=true;
         document.frmApportGrantRates.CmdDelete.disabled=true;      
         
         document.frmApportGrantRates.cmbFinYear.value=0;
         document.frmApportGrantRates.cmbApportGrantCatCode.value=0;
         document.frmApportGrantRates.txtApportRate.value="";
         callServer('Get','');
       }
	   else
	   {
		   alert("Failed to Add values");
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
       items[1]=baseResponse.getElementsByTagName("ApportRate")[0].firstChild.nodeValue;

       var r=document.getElementById(items[0]);    
       var rcells=r.cells;
       
       
       //var ApportDesc = document.frmApportGrantRates.cmbApportGrantCatCode.options[items[0]].text;
       
       /* var tnodeApportCode=document.createTextNode(ApportDesc);     
       tnodeApportCode.id = ApportCode;
		*/
       
       rcells.item(1).firstChild.nodeValue=items[0];
       //rcells.item(1).firstChild.id = ApportCode;
       rcells.item(2).firstChild.nodeValue=items[1];

       document.frmApportGrantRates.CmdAdd.disabled=false;
       document.frmApportGrantRates.CmdUpdate.disabled=true;
       document.frmApportGrantRates.CmdDelete.disabled=true;         
        
      // document.frmApportGrantRates.cmbFinYear.value=0;
       document.frmApportGrantRates.cmbApportGrantCatCode.value=0;
       document.frmApportGrantRates.txtApportRate.value="";
                       
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
          var ApportCode = baseResponse.getElementsByTagName("ApportCode")[0].firstChild.nodeValue;
		  var tbody=document.getElementById("Existing");
		  /*var r=document.getElementById(ApportCode);
		  var ri=r.rowIndex;
		  tbody.deleteRow(ri);
		  document.frmApportGrantRates.cmbFinYear.value=0;
		  document.frmApportGrantRates.cmbApportGrantCatCode.value=0;
		  document.frmApportGrantRates.txtApportRate.value="";*/
		  
		  document.frmApportGrantRates.CmdAdd.disabled=false;
		  document.frmApportGrantRates.CmdUpdate.disabled=true;
		  document.frmApportGrantRates.CmdDelete.disabled=true;      
		           
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
             var ApportRate = baseResponse.getElementsByTagName("ApportRate")[k].firstChild.nodeValue;
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
/*             
             var ApportDesc = document.frmApportGrantRates.cmbApportGrantCatCode.options[items[0]].text;
             var tnodeApportCode=document.createTextNode(ApportDesc);     
             tnodeApportCode.id = ApportCode;
*/
             
             //var tnodeApportCode=document.createTextNode(document.frmApportGrantRates.cmbApportGrantCatCode.options[ApportCode].text);                         
             //tnodeApportCode.id = ApportCode;
             
             var tnodeApportCode=document.createTextNode(ApportCode);
             cell2.appendChild(tnodeApportCode);       
             mycurrent_row.appendChild(cell2);       

             var cell3 =document.createElement("TD");    
             var tnodeApportRate=document.createTextNode(ApportRate);                         
             cell3.appendChild(tnodeApportRate);       
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
      
      document.frmApportGrantRates.cmbApportGrantCatCode.value=rcells.item(1).firstChild.nodeValue;
      document.frmApportGrantRates.txtApportRate.value=rcells.item(2).firstChild.nodeValue;

      document.frmApportGrantRates.CmdAdd.disabled=true;
      document.frmApportGrantRates.CmdUpdate.disabled=false;
      document.frmApportGrantRates.CmdDelete.disabled=false;
    
      document.frmApportGrantRates.CmdDelete.focus();
}

function checkRate(rate)
{
	if(rate.value < 0)
	{
		alert("Apportionment Rate cannot be a Negative value");
		//document.frmApportGrantRates.txtApportRate.value =
		rate.value = "";
	}
	else if(rate.value > 100)
	{
		alert("Apportionment Rate cannot exceed 100%");
		rate.value = "";
	}
}