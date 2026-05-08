
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
	            return false;
	        }
	        else if (unicode<48 || unicode>57)
	        {
	            return false; 
	        }
        }
}

 function refresh()
 {
     document.frmDepreciationRates.cmbDepreciationCatCode.value = 1;
	// document.frmDepreciationRates.cmbFinYear.value = 1;
     document.frmDepreciationRates.txtDepreciationRate.value = "";

     document.frmDepreciationRates.CmdAdd.disabled=false;
     document.frmDepreciationRates.CmdUpdate.disabled=true;
     document.frmDepreciationRates.CmdDelete.disabled=true;      
    
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
          if(document.frmDepreciationRates.txtDepreciationRate.value=="")
          { 
               alert("Please Enter the Depreciation Rate");
               document.frmDepreciationRates.txtDepreciationRate.focus();
               return false;
          }
          
          return true;
}
        
        
//******************************************** CallServer Coding *******************//


function callServer(command,param)
 {
   
       var DepreciationCatCode=document.frmDepreciationRates.cmbDepreciationCatCode.value;
	   var FinancialYear = document.frmDepreciationRates.cmbFinYear.value;
	   var DepreciationRate=document.frmDepreciationRates.txtDepreciationRate.value;
       
       var url="";

       if(command=="Add")
        {              
    	   			var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../Depreciation_Rates?command=Add&DepreciationCatCode=" + DepreciationCatCode+"&FinancialYear="+FinancialYear+"&DepreciationRate="+DepreciationRate;
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
                    url="../../../../../Depreciation_Rates?command=Update&DepreciationCatCode=" + DepreciationCatCode+"&FinancialYear="+FinancialYear+"&DepreciationRate="+DepreciationRate;
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
        			url="../../../../../Depreciation_Rates?command=Delete&DepreciationCatCode=" + DepreciationCatCode+"&FinancialYear="+FinancialYear;
        			//alert(url);
        			var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    };   
                    req.send(null);
        }
        else if(command=="Get"){               
		            url="../../../../../Depreciation_Rates?command=Get&FinancialYear="+FinancialYear;
		           //alert(url);
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
           // var command=baseResponse.depElementsByTagName("command")[0];*/
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
         var DepreciationCode = baseResponse.getElementsByTagName("DepreciationCode")[0].firstChild.nodeValue;
         var DepreciationRate = baseResponse.getElementsByTagName("DepreciationRate")[0].firstChild.nodeValue;
         var FinancialYear = baseResponse.getElementsByTagName("FinancialYear")[0].firstChild.nodeValue;
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
     //    tnodeDepreciationCode.id = DepreciationCode;
         cell2.appendChild(tnodeDepreciationCode);       
         mycurrent_row.appendChild(cell2);       

         var cell3 =document.createElement("TD");    
         var tnodeDepreciationRate=document.createTextNode(DepreciationRate);                         
         cell3.appendChild(tnodeDepreciationRate);       
         mycurrent_row.appendChild(cell3);
         
         tbody.appendChild(mycurrent_row);
        
         document.frmDepreciationRates.CmdAdd.disabled=false;
         document.frmDepreciationRates.CmdUpdate.disabled=true;
         document.frmDepreciationRates.CmdDelete.disabled=true;      
         
       //  document.frmDepreciationRates.cmbFinYear.value=0;
         document.frmDepreciationRates.cmbDepreciationCatCode.value=0;
         document.frmDepreciationRates.txtDepreciationRate.value="";
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
       items[0]=baseResponse.getElementsByTagName("DepreciationCode")[0].firstChild.nodeValue;
       items[1]=baseResponse.getElementsByTagName("DepreciationRate")[0].firstChild.nodeValue;

       var r=document.getElementById(items[0]);    
       var rcells=r.cells;
       
       
       //var DepreciationDesc = document.frmDepreciationRates.cmbDepreciationCatCode.options[items[0]].text;
       
       /* var tnodeDepreciationCode=document.createTextNode(DepreciationDesc);     
       tnodeDepreciationCode.id = DepreciationCode;
		*/
       
       rcells.item(1).firstChild.nodeValue=items[0];
       //rcells.item(1).firstChild.id = DepreciationCode;
       rcells.item(2).firstChild.nodeValue=items[1];

       document.frmDepreciationRates.CmdAdd.disabled=false;
       document.frmDepreciationRates.CmdUpdate.disabled=true;
       document.frmDepreciationRates.CmdDelete.disabled=true;         
        
       document.frmDepreciationRates.cmbFinYear.value=0;
       document.frmDepreciationRates.cmbDepreciationCatCode.value=0;
       document.frmDepreciationRates.txtDepreciationRate.value="";
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
          var DepreciationCode = baseResponse.getElementsByTagName("DepreciationCode")[0].firstChild.nodeValue;
		  var tbody=document.getElementById("Existing");
		  /*var r=document.getElementById(DepreciationCode);
		  var ri=r.rowIndex;
		  tbody.deleteRow(ri);
		  document.frmDepreciationRates.cmbFinYear.value=0;
		  document.frmDepreciationRates.cmbDepreciationCatCode.value=0;
		  document.frmDepreciationRates.txtDepreciationRate.value="";*/
		  
		  document.frmDepreciationRates.CmdAdd.disabled=false;
		  document.frmDepreciationRates.CmdUpdate.disabled=true;
		  document.frmDepreciationRates.CmdDelete.disabled=true;      
		           
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
      //alert(flag+"-------->");
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
           // alert("len"+len);    
			for(var k=0;k<len;k++)
            {
             
             var DepreciationCode = baseResponse.getElementsByTagName("DepreciationCode")[k].firstChild.nodeValue;
             var DepreciationRate = baseResponse.getElementsByTagName("DepreciationRate")[k].firstChild.nodeValue;
             var view = baseResponse.getElementsByTagName("status")[k].firstChild.nodeValue;
            var dec_catagory=baseResponse.getElementsByTagName("DepreciationDesc")[k].firstChild.nodeValue;
            //// alert(dec_catagory);
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
/*             
             var DepreciationDesc = document.frmDepreciationRates.cmbDepreciationCatCode.options[items[0]].text;
             var tnodeDepreciationCode=document.createTextNode(DepreciationDesc);     
             tnodeDepreciationCode.id = DepreciationCode;
*/
             
             //var tnodeDepreciationCode=document.createTextNode(document.frmDepreciationRates.cmbDepreciationCatCode.options[DepreciationCode].text);                         
             //tnodeDepreciationCode.id = DepreciationCode;
             
             var tnodeDepreciationCode=document.createTextNode(dec_catagory);
             cell2.appendChild(tnodeDepreciationCode);       
             mycurrent_row.appendChild(cell2);       

             var cell3 =document.createElement("TD");    
             var tnodeDepreciationRate=document.createTextNode(DepreciationRate);                         
             cell3.appendChild(tnodeDepreciationRate);       
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
      //alert(rcells.item(1).firstChild.nodeValue);
      document.frmDepreciationRates.cmbDepreciationCatCode.value=rcells.item(1).firstChild.nodeValue;
      document.frmDepreciationRates.txtDepreciationRate.value=rcells.item(2).firstChild.nodeValue;

      document.frmDepreciationRates.CmdAdd.disabled=true;
      document.frmDepreciationRates.CmdUpdate.disabled=false;
      document.frmDepreciationRates.CmdDelete.disabled=false;
    
      document.frmDepreciationRates.CmdDelete.focus();
}

function checkRate(rate)
{
	if(rate.value < 0)
	{
		alert("Depreciation Rate cannot be a Negative value");
		//document.frmDepreciationRates.txtDepreciationRate.value =
		rate.value = "";
	}
	else if(rate.value > 100)
	{
		alert("Depreciation Rate cannot exceed 100%");
		rate.value = "";
	}
}