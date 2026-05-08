var rid;
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
/////////////////////////////////code to check textarea//////////////////////////////////////////////////////
function check_leng(param,val)
{	 
		if((val.length)>=190)
		{
			  if(param=='remarks')			  
				  	   alert("Please Enter Remarks below 200 characters");			           			  
			  else			  
				  	   alert("Please Enter Paticulars below 200 characters");				  	  
			  
		}
		
}
///////////////////////////////exit mathod////////////////////////////////////////////////////////////
function exitmethod()
{
      window.close();
}
/*.....................................................................................................*/
function callrefresh1(){
		document.Bill_AdvanceApplicable_form.txtbill_majr_code.disabled=false;
		document.Bill_AdvanceApplicable_form.txtbill_minr_code.disabled=false;
		document.Bill_AdvanceApplicable_form.cmd_add.disabled=false;
            document.Bill_AdvanceApplicable_form.txtbill_majr_code.selectedIndex=0;
            document.Bill_AdvanceApplicable_form.txtbill_minr_code.selectedIndex=0;
	    document.Bill_AdvanceApplicable_form.advance_applicable_YN[0].checked=true;
	    document.getElementById("txtbill_advremarks").value="";
}
///////////////////////////////////////////////////////////////////////////////////////////////////////
/******************* callserver(command) Method*********/
function callserver(command)
{
            var txtbill_majr_code="";var txtbill_majr_desc="";
            var txtbill_minr_code="";var txtbill_minr_desc="";
            var advance_applicable_YN="";
            var txtbill_advremarks="";
            var url="";
            txtbill_majr_code=document.getElementById("txtbill_majr_code").value;
            //alert(txtbill_majr_code);
            
            var selindex=document.Bill_AdvanceApplicable_form.txtbill_majr_code.selectedIndex;
            txtbill_majr_desc=document.Bill_AdvanceApplicable_form.txtbill_majr_code.options[selindex].text;
            //alert(txtbill_majr_desc);
            
            txtbill_minr_code=document.getElementById("txtbill_minr_code").value;
            //alert(txtbill_minr_code);
            var selindex1=document.Bill_AdvanceApplicable_form.txtbill_minr_code.selectedIndex;
            txtbill_minr_desc=document.Bill_AdvanceApplicable_form.txtbill_minr_code.options[selindex1].text;
            //alert(txtbill_minr_desc);
            
            if(document.Bill_AdvanceApplicable_form.advance_applicable_YN[0].checked)
            {
                 advance_applicable_YN="Y";
            }
            else
            {
                advance_applicable_YN="N";
            }
            if((document.getElementById("txtbill_advremarks").value)!="")
            {
                txtbill_advremarks=document.getElementById("txtbill_advremarks").value;
            }
            else
            {
                txtbill_advremarks="";
            }
            	
                if (command=="add"){
                				var flag=checknull();
                                //alert("add button is clicked");
                				if(flag){
                					url="../../../../../advance_applicable_master?command=add&bill_majr_code="+txtbill_majr_code
                                    +"&txtbill_majr_desc="+txtbill_majr_desc+"&bill_minr_code="+txtbill_minr_code
                                    +"&txtbill_minr_desc="+txtbill_minr_desc+"&advance_applicable_YN="+advance_applicable_YN
                                    +"&txtbill_advremarks="+txtbill_advremarks;
	                                var req=getTransport();
	                                req.open("GET",url,true);        
	                                req.onreadystatechange=function()
	                                {
	                                   processResponse(req); 
	                                };   
	                                req.send(null);
                				}                                
                }
                else if(command=="update"){
                	var flag=checknull();
                            if(flag){
                            	url="../../../../../advance_applicable_master?command=update&bill_majr_code="+txtbill_majr_code
                                +"&bill_minr_code="+txtbill_minr_code+"&advance_applicable_YN="+advance_applicable_YN
                                +"&txtbill_advremarks="+txtbill_advremarks;
	                            var req=getTransport();
	                            req.open("GET",url,true);        
	                            req.onreadystatechange=function()
	                            {
	                               processResponse(req);
	                            };   
	                            req.send(null);
                            }
                            
                }
                else if(command=="delete")
                {  
                         var ans=confirm("Are you sure to cancel this record");
                            if(ans==true)
                            {
                            	var flag=checknull();
                                 if(flag){
                                	 url="../../../../../advance_applicable_master?command=delete&bill_majr_code="+txtbill_majr_code
                                     +"&bill_minr_code="+txtbill_minr_code;
	                                   var req=getTransport();
	                                   req.open("GET",url,true);        
	                                   req.onreadystatechange=function()
	                                   {
	                                      processResponse(req);
	                                   };   
	                                   req.send(null);
                                 }                                 
                            }
                            else
                            {
                                  alert("choose another to process");
                            }
                }
                else if(command=="get")
                {
                     //alert("Get");
                            url="../../../../../advance_applicable_master?command=get";
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
/***************** adding values to the Row********************/
function addRow(baseResponse)
{
	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;    
	  if(flag=="success")
	  {                       
               	alert("Record Inserted Into Database successfully.");
      }else if(flag=="AlreadyExist"){
                alert("Record AlreadyExist.so,can't Inserted");
      }else{
		   alert("Failed to Add values");
	  }
	  callrefresh1();
	  callserver('get');

}

/**************** Updating the values in the table rows ****************************/

function updateRow(baseResponse)
{
	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	  if(flag=="success"){   
       		alert("Records Updated Successfully.");
      }else{
		   alert("Failed to update values");
      }    
	  callrefresh1();
	  callserver('get');
}

/*************** deleting the rows in table rows ************************/
 
function deleteRow(baseResponse)
{
	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	  if(flag=="success")
	  {
          	//var slno=baseResponse.getElementsByTagName("SLNO")[0].firstChild.nodeValue;
		//alert("inside the delete");
                var tbody=document.getElementById("dyntable");
		//var r=document.getElementById(rid);
		//var ri=r.rowIndex;
		//tbody.deleteRow(ri);
                callrefresh1();
                callserver('get');
                    
                    document.Bill_AdvanceApplicable_form.cmd_add.disabled=false;
                    document.Bill_AdvanceApplicable_form.cmd_update.disabled=true;
                    document.Bill_AdvanceApplicable_form.cmd_delete.disabled=true;      
                alert("Cancel Successfully");                      
	  }
	  else
	  {
	      alert("Unable to Cancel");
      	  }
	  callrefresh1();
	  callserver('get');
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
		var len=baseResponse.getElementsByTagName("serial_no");
                
                for(var k=0;k<len.length;k++){
                        var serial_no=baseResponse.getElementsByTagName("serial_no")[k].firstChild.nodeValue;
                        var bill_majr_code =baseResponse.getElementsByTagName("bill_majr_code")[k].firstChild.nodeValue;
                        var bill_majr_desc =baseResponse.getElementsByTagName("bill_majr_desc")[k].firstChild.nodeValue;
                        var bill_minr_code =baseResponse.getElementsByTagName("bill_minr_code")[k].firstChild.nodeValue;
                        var bill_minr_desc =baseResponse.getElementsByTagName("bill_minr_desc")[k].firstChild.nodeValue;
                        var advance_applicable =baseResponse.getElementsByTagName("advance_applicable")[k].firstChild.nodeValue;
                        if(advance_applicable=="Y")
                        	advance_applicable="YES";
                        else
                        	advance_applicable="NO";
                        var advance_app_remarks ="";
                        if(baseResponse.getElementsByTagName("advance_app_remarks")[k].firstChild.nodeValue!='null'||baseResponse.getElementsByTagName("advance_app_remarks")[k].firstChild.nodeValue!=null){
                        	advance_app_remarks=baseResponse.getElementsByTagName("advance_app_remarks")[k].firstChild.nodeValue;
                        }		
                        var view=baseResponse.getElementsByTagName("status")[k].firstChild.nodeValue;
                
		                var dynbody=document.getElementById("dynbody");     // dynbody -->     <tbody id="dynbody"></tbody>
		      	        var dyn_row=document.createElement("TR");
		        		dyn_row.id=serial_no;
			    
		        		var cell1=document.createElement("TD");
		        		if (view == "C") {
		          			//var tid = document.createTextNode("Cancel");			
		          			var priceSpan = document.createElement("span");
		          			priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
		          			priceSpan.appendChild(document.createTextNode("Cancel"));			
		          			cell1.appendChild(priceSpan);          			
		          		}else{
		          			var anc=document.createElement("A");       
					        anc.href="javascript:loadValuesFromTable('" +serial_no+ "')";
			        		var txtedit=document.createTextNode("Edit");
			               	anc.appendChild(txtedit);
				        	cell1.appendChild(anc);          						
		          		}		        		
		        		dyn_row.appendChild(cell1);
		                  	
		        		var cell2 =document.createElement("TD"); 
		                var tnode_serialno=document.createTextNode(serial_no);     
		                cell2.appendChild(tnode_serialno);       
		                dyn_row.appendChild(cell2);    
		                        
		                var cell3 =document.createElement("TD"); 
		         		var tnode_majcode=document.createTextNode(bill_majr_desc);     
		                cell3.appendChild(tnode_majcode);       
		                dyn_row.appendChild(cell3);
		                var hidden1=document.createElement("input");
		                hidden1.type="hidden";
		                hidden1.name="hidbill_majr_code";
		                hidden1.value=bill_majr_code;
		                cell3.appendChild(hidden1);
		                dyn_row.appendChild(cell3);  
		
		             	var cell4 =document.createElement("TD");    
		        		var tnode_mincode=document.createTextNode(bill_minr_code);                         
			        	cell4.appendChild(tnode_mincode);       
		        		dyn_row.appendChild(cell4);
		                var hidden2=document.createElement("input");
		                hidden2.type="hidden";
		                hidden2.name="hidbill_minr_code";
		                hidden2.value=bill_minr_code;
		                cell4.appendChild(hidden2);
		                dyn_row.appendChild(cell4);
		
		             		
		                var cell5 =document.createElement("TD");    
		        		var tnode_adv_appl=document.createTextNode(advance_applicable);                         
			        	cell5.appendChild(tnode_adv_appl);       
		        		dyn_row.appendChild(cell5);
		                        
		                var cell6 =document.createElement("TD");    
		        		var tnode_adv_applremk=document.createTextNode(advance_app_remarks);                         
			        	cell6.appendChild(tnode_adv_applremk);       
		        		dyn_row.appendChild(cell6);
		        		
		        		var cell7 =document.createElement("TD");    
		        		if(view=="C"){
		          			var tdst = document.createTextNode("CANCEL");
		          		}else{
		          			var tdst = document.createTextNode("LIVE");
		          		}
		        		cell7.appendChild(tdst);       
		        		dyn_row.appendChild(cell7);
             		
                        dynbody.appendChild(dyn_row);
       		}
      }
      else
      {
        alert("Failed to Load Values");
      }
}

function loadValuesFromTable(rid1)
{      
          rid=rid1;
          var r=document.getElementById(rid); 
          var rcells=r.cells;
          var dynbody=document.getElementById("dynbody");
          var dyntable=document.getElementById("dyntable");
          
          //alert(rcells.item(2).lastChild.value);
          //alert(rcells.item(3).lastChild.value);
          
          LoadAllMinortype();
          alert("loading the bill minor type");
          document.Bill_AdvanceApplicable_form.txtbill_majr_code.value=rcells.item(2).lastChild.value;
          document.Bill_AdvanceApplicable_form.txtbill_minr_code.value=rcells.item(3).lastChild.value;
         
          //document.Bill_AdvanceApplicable_form.txtbill_majr_code.options[document.Bill_AdvanceApplicable_form.txtbill_majr_code.selectedIndex].text=rcells.item(2).firstChild.nodeValue;
          //document.Bill_AdvanceApplicable_form.txtbill_minr_code.options[document.Bill_AdvanceApplicable_form.txtbill_minr_code.selectedIndex].text=rcells.item(3).firstChild.nodeValue;
          
          if(rcells.item(4).firstChild.nodeValue=="YES")
            document.Bill_AdvanceApplicable_form.advance_applicable_YN[0].checked=true;	
          else
            {
                document.Bill_AdvanceApplicable_form.advance_applicable_YN[0].checked=false;	
                document.Bill_AdvanceApplicable_form.advance_applicable_YN[1].checked=true
            }
            document.Bill_AdvanceApplicable_form.txtbill_advremarks.value=rcells.item(5).firstChild.nodeValue;
            document.Bill_AdvanceApplicable_form.txtbill_majr_code.disabled=true;
            document.Bill_AdvanceApplicable_form.txtbill_minr_code.disabled=true;
            document.Bill_AdvanceApplicable_form.cmd_add.disabled=true;
            document.Bill_AdvanceApplicable_form.cmd_update.disabled=false;
            document.Bill_AdvanceApplicable_form.cmd_delete.disabled=false;
            document.Bill_AdvanceApplicable_form.cmd_delete.focus();
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function LoadAllMinortype()
{
        //var BillMajorCode=document.getElementById("txtbill_majr_code").value;       
        var bill_minr_code=document.getElementById("txtbill_minr_code");
        var child=bill_minr_code.childNodes;
        for(var i=child.length-1;i>1;i--)
        {
                bill_minr_code.removeChild(child[i]);
        } 
        var url="../../../../../advance_applicable_master?command=loadAllMinorType";
       var req=getTransport();
       req.open("GET",url,true); 
       req.onreadystatechange=function()
       {
                handleResponse(req);
       };   
       req.send(null);
}
function handleResponse(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
                var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                var tagcommand=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(tagcommand=="loadAllMinorType")
                {
                    if(flag=="success")
                    {
                        var option=baseResponse.getElementsByTagName("option");
                        var BillMinorCode=document.getElementById("txtbill_minr_code");
                        for(var i=0;i<option.length;i++)
                         {
                            var code=option[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                            var desc=option[i].getElementsByTagName("desc")[0].firstChild.nodeValue;
                            //alert(code+"   "+desc);
                            var opt=document.createElement("option");
                            opt.setAttribute("value",code);
                            var opttext=document.createTextNode(desc);
                            opt.appendChild(opttext);
                            BillMinorCode.appendChild(opt);
                         }
                    }
                    else if(flag=="nodata")
                    {
                        alert("No records to load Bill Minor Type ");
                    }
                    else
                    {
                        alert("Failed to load records");
                    }
                }
        }
    }
}
function checknull(){
	if(document.getElementById('txtbill_majr_code').value=="0"){
		alert("Please select the Bill Major Type");
		return false;
	}
	if(document.getElementById('txtbill_minr_code').value=="0"){
		alert("Please select the Bill Minor Type");
		return false;
	}	
	return true;
}
