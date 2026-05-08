/********************javascript*******************/
/***************** ajax CONCEPT***************/
function getTransport()
{
  var req=false;
  try
  {
      req=new ActiveXObject("Msxml2.XMLHTTP");
  }
  catch(e1)
  {
      try
        {
            req=new ActiveXObject("Microsoft.XMLHTTP");
        }
          catch(e2)
          {
            req=false;
          }
    }
    if (!req && typeof XMLHttpRequest != 'undefined')
    {
      req=new XMLHttpRequest();
    }
  return req;
}
    
/**************function for null checking************/

function nullfieldcheck()
{
 	if(document.getElementById("bill_type_desc").value=="")
        {
          alert("Enter the Bill type Description");
          document.getElementById("bill_type_desc").focus();
          return false;
        }
        return true;
}
/***************** Refresh Method*******************/
function callrefresh()
{
              document.getElementById("bill_type_code").value="";
	      document.getElementById("bill_type_desc").value="";
              document.getElementById("bill_remarks").value="";
              document.getElementById("cmd_add").disabled=false;
              document.getElementById("cmd_update").disabled=true;
              document.getElementById("cmd_delete").disabled=true;
             /*
              var tbody=document.getElementById("dynbody");
              var t=0;
              for(t=tbody.rows.length-1;t>=0;t--)
              {
                    tbody.deleteRow(0);
              }
               callserver('get');
               alert("inside refresh method");*/
}
/************************* EXIT Method**************/
function exitmethod()
{
      window.close();
}
/******************* callserver(command) Method*********/
function callserver(command)
{
            var billtype_code="";
            var billtype_desc="";
            var billremarks="";
            var url="";
            billtype_code=document.getElementById("bill_type_code").value;
            billtype_desc=document.getElementById("bill_type_desc").value;
            billremarks=document.getElementById("bill_remarks").value;
        
        if (command=="add")
        {
             //alert("add button is clicked");
              var flag=nullfieldcheck();
  	      if(flag==true)
                    {
                    
                    url="../../../../../fasbilltype_master_servlet?command=add&billtype_desc1=" + billtype_desc+"&billremarks1="+billremarks;
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
        else if(command=="update")
        {
                    //alert("inside update");
                    var flag=nullfieldcheck();
                    if(flag==true)
                    {
                    //alert("after getting the flag value");
                    url="../../../../../fasbilltype_master_servlet?command=update&bill_type_code1="+billtype_code+"&billtype_desc1=" + billtype_desc+"&billremarks1="+billremarks;
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
        else if(command=="delete")
        {  
                 var ans=confirm("Are you sure to delete this record");
                    if(ans==true)
                    {
                         url="../../../../../fasbilltype_master_servlet?command=delete&bill_type_code1="+billtype_code;
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
                          alert("choose another to process");
                        }
        }
        else if(command=="get")
        {
             //alert("Get");
              //var flag=nullfieldcheck();
  	      //if(flag==true)
                    
                    url="../../../../../fasbilltype_master_servlet?command=get";
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


//********************************* CallServer Response Coding ***************************************//

function processResponse(req)
{   
      if(req.readyState==4)   // Completed
      {
          if(req.status==200)   // No error
          {    //alert("success");
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              if(command=="Add")
              {
                  //alert("hi inside the command add");
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
/***************** adding values to the Row********************/
function addRow(baseResponse)
{
	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;    
	  if(flag=="success")
	  {                       
               	alert("Record Inserted Into Database successfully.");

/*********************** assingning current values in the fields************************************************/

		var billtype_code =baseResponse.getElementsByTagName("billtype_code")[0].firstChild.nodeValue;
            	var billtype_desc =baseResponse.getElementsByTagName("billtype_desc")[0].firstChild.nodeValue;
 	        var billremarks =baseResponse.getElementsByTagName("billremarks")[0].firstChild.nodeValue;
		//alert("bill_type_code : " + billtype_code);
        	//alert("bill_type_desc : " + billtype_desc);
		//alert("bill_type_desc : " + billremarks);
		
/******************************* creating dynmic table row,td,cell,anchor,textnodevalue-Edit********/               
	
		var dynbody=document.getElementById("dynbody");     // dynbody -->     <tbody id="dynbody"></tbody>
      	        var dyn_row=document.createElement("TR");
        	dyn_row.id=billtype_code;
	    
		var cell1=document.createElement("TD");   
        	var anc=document.createElement("A");       
	        anc.href="javascript:loadValuesFromTable('" +billtype_code+ "')";
        	var txtedit=document.createTextNode("Edit");
               	anc.appendChild(txtedit);
	        cell1.appendChild(anc);
        	dyn_row.appendChild(cell1);
     		
/******************* creating the second column of dynamic table-named as billtype_code*********/
		
     		var cell2 =document.createElement("TD"); 
         	var tnode_code=document.createTextNode(billtype_code);     
                cell2.appendChild(tnode_code);       
                dyn_row.appendChild(cell2);       

/**********************************creating the third column of dynamic table as billtype_desc****************/

	        var cell3 =document.createElement("TD");    
        	var tnode_desc=document.createTextNode(billtype_desc);                         
	        cell3.appendChild(tnode_desc);       
        	dyn_row.appendChild(cell3);

/***********************************creating the fourth column of dynamic table as billremarks***************/         

        	var cell4 =document.createElement("TD");    
        	var tnode_remarks=document.createTextNode(billremarks);                         
	        cell4.appendChild(tnode_remarks);       
        	dyn_row.appendChild(cell4);

/************************************dynamic row appends ro the dynamic body of the existing table**************************/

		dynbody.appendChild(dyn_row);  
	        
                callrefresh();
                
		//document.fasbill_master_form.cmd_add.disabled=false;
         	//document.fasbill_master_form.cmd_update.disabled=true;
	        //document.fasbill_master_form.cmd_delete.disabled=true;      
             	
		//document.fasbill_master_form.bill_type_code.value="";
         	//document.fasbill_master_form.bill_type_desc.value="";
	        //document.fasbill_master_form.remarks.value="";
         
       }
	   else
	   {
		   alert("Failed to Add values");
	   }

}

/**************** Updating the values in the table rows ****************************/

function updateRow(baseResponse)
{
	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	  if(flag=="success")
	  {   
       		alert("Records Updated Successfully.");
		var arr=new Array();
	        arr[0]=baseResponse.getElementsByTagName("billtype_code")[0].firstChild.nodeValue;
   	        arr[1]=baseResponse.getElementsByTagName("billtype_desc")[0].firstChild.nodeValue;
		arr[2]=baseResponse.getElementsByTagName("billremarks")[0].firstChild.nodeValue;
		
	        var r=document.getElementById(arr[0]);    // <tr id=1> 
	        var rcells=r.cells;  // all columns or all<td>s
	        rcells.item(1).firstChild.nodeValue=arr[0];
       		rcells.item(2).firstChild.nodeValue=arr[1];
		rcells.item(3).firstChild.nodeValue=arr[2];
                alert("updated");
       		callrefresh();
                /*
                document.fasbill_master_form.cmd_add.disabled=false;
         	document.fasbill_master_form.cmd_update.disabled=true;
	        document.fasbill_master_form.cmd_delete.disabled=true; 
                */
          }
   	  else
   		{
		   alert("Failed to update values");
		}                                  
}

/*************** deleting the rows in table rows ************************/
 
function deleteRow(baseResponse)
{
	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	  if(flag=="success")
	  {
          	var bcode=baseResponse.getElementsByTagName("billtype_code")[0].firstChild.nodeValue;
		var dyntbl=document.getElementById("dyntable");
		var r=document.getElementById(bcode);
		var ri=r.rowIndex;
		dyntbl.deleteRow(ri);
			  callrefresh();
		//document.fasbill_master_form.cmd_add.disabled=false;
         	//document.fasbill_master_form.cmd_update.disabled=true;
	        //document.fasbill_master_form.cmd_delete.disabled=true;         
        
		  alert("Deleted Successfully");                      
	  }
	  else
	  {
	      alert("Unable to Delete");
      	  }
}

/************************ getting the values from the table**************/

function getRow(baseResponse)
{   
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
      if(flag=="success")
      {          
		var dynbody = document.getElementById("dynbody");
		var dyntable = document.getElementById("dyntable");
		var t=0;
		for(t=dynbody.rows.length-1;t>=0;t--)
		{
		   dynbody.deleteRow(0);
		}   
		var len=baseResponse.getElementsByTagName("billtype_code").length;
                for(var k=0;k<len;k++)
            	{
                        var billtype_code =baseResponse.getElementsByTagName("billtype_code")[k].firstChild.nodeValue;
            		var billtype_desc =baseResponse.getElementsByTagName("billtype_desc")[k].firstChild.nodeValue;
 	        	var billremarks =baseResponse.getElementsByTagName("billremarks")[k].firstChild.nodeValue;
        	     	
			var dynbody=document.getElementById("dynbody");     // dynbody -->     <tbody id="dynbody"></tbody>
      	        	var dyn_row=document.createElement("TR");
        		dyn_row.id=billtype_code;
	    
			var cell1=document.createElement("TD");   
        		var anc=document.createElement("A");       
		        anc.href="javascript:loadValuesFromTable('" +billtype_code+ "')";
        		var txtedit=document.createTextNode("Edit");
               		anc.appendChild(txtedit);
	        	cell1.appendChild(anc);
        		dyn_row.appendChild(cell1);
                  	
			var cell2 =document.createElement("TD"); 
         		var tnode_code=document.createTextNode(billtype_code);     
                	cell2.appendChild(tnode_code);       
                	dyn_row.appendChild(cell2);       

             		var cell3 =document.createElement("TD");    
        		var tnode_desc=document.createTextNode(billtype_desc);                         
	        	cell3.appendChild(tnode_desc);       
        		dyn_row.appendChild(cell3);
             		
			var cell4 =document.createElement("TD");    
        		var tnode_remarks=document.createTextNode(billremarks);                         
	        	cell4.appendChild(tnode_remarks);       
        		dyn_row.appendChild(cell4);

             		dynbody.appendChild(dyn_row);
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
      var dynbody=document.getElementById("dynbody");
      var dyntable=document.getElementById("dyntable");
      
      document.fasbill_master_form.bill_type_code.value=rcells.item(1).firstChild.nodeValue; 
      document.fasbill_master_form.bill_type_desc.value=rcells.item(2).firstChild.nodeValue;
      document.fasbill_master_form.remarks.value=rcells.item(3).firstChild.nodeValue;	
      
	document.fasbill_master_form.cmd_add.disabled=true;
        document.fasbill_master_form.cmd_update.disabled=false;
	document.fasbill_master_form.cmd_delete.disabled=false;
    
      document.fasbill_master_form.cmd_delete.focus();
}
