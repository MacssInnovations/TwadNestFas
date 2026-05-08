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
function checkAvailability()
{  
	
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;   
        var txtCB_Year=document.getElementById("txtCB_Year").value;
        var txtCB_Month=document.getElementById("txtCB_Month").value;
        if(txtCB_Month=="")
        {
        	alert("choose month");
        	return false;
        }
        if(txtCB_Year=="")
        {
        	alert("choose year");
        	return false;
        }
        var tbody=document.getElementById("grid_body");
        try{tbody.innerHTML="";}
        catch(e) {tbody.innerText="";}
	    var url="../../../../../Arrival_CB_SL.kv?command=checkAvl&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
      // alert(url);
	    var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
        	handleResponse(req);
        }   
        req.send(null);
		                 
}

function handleResponse(req)
{
       if(req.readyState==4)
        {
          if(req.status==200)
          {               
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];             
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var Command=tagCommand.firstChild.nodeValue; 
            
            if(Command=="checkAvl")
            {
               var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
             //  alert(flag)
                 if(flag=="success")
                    {
                    
                      var tbody=document.getElementById("grid_body");
                      var t=0;
                      for(t=tbody.rows.length-1;t>=0;t--)
                        {
                             tbody.deleteRow(0);
                        }
       
                      var RecNo=baseResponse.getElementsByTagName("unit_id");
                     // alert(RecNo);
                      var items=new Array();
           
                      for(var k=0;k<RecNo.length;k++)
                        {
                            
                         items[0]=baseResponse.getElementsByTagName("hcode")[k].firstChild.nodeValue;  
                        // alert(items[0]);
                         items[1]=baseResponse.getElementsByTagName("head_desc")[k].firstChild.nodeValue;
                        // alert(items[1]);
                         items[2]=baseResponse.getElementsByTagName("sltype")[k].firstChild.nodeValue;
                        // alert(items[2]);
                         items[3]=baseResponse.getElementsByTagName("slcode")[k].firstChild.nodeValue;
                        // alert(items[3]);
                         items[4]=baseResponse.getElementsByTagName("projectId")[k].firstChild.nodeValue;
                        // alert(items[4]);
                         items[5]=baseResponse.getElementsByTagName("cb")[k].firstChild.nodeValue;
                        // alert(items[5]);
                         items[6]=baseResponse.getElementsByTagName("indicator")[k].firstChild.nodeValue;
                         //alert(items[6]);
//                         items[7]=baseResponse.getElementsByTagName("createdbymodule")[k].firstChild.nodeValue;  
//                        alert(items[7]);
                         
                         if (items[0]=='null')
                           items[0]="";
                         if (items[1]=='null')
                           items[1]="";
                         if (items[2]=='null')
                           items[2]="";
                         if (items[3]=='null')
                           items[3]="";
                         if (items[4]=='null')
                           items[4]="";
                         if (items[5]=='null')
                           items[5]="";
                         if (items[6]=='null')
                           items[6]="";
                    
	                     var mycurrent_row=document.createElement("TR");
	                     var cell2;
                
                     	  cell2=document.createElement("TD");
                          cell2.style.textAlign='left';                          
                          var currentText=document.createTextNode(items[0]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                   
                          cell2=document.createElement("TD");
                          cell2.style.textAlign='left';  
                          var currentText=document.createTextNode(items[1] );
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                          
                    
                          cell2=document.createElement("TD");
                          cell2.style.textAlign='left';
                          var currentText=document.createTextNode(items[2]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                           
                          cell2=document.createElement("TD");
                          cell2.style.textAlign='right';
                          
                         
                           var currentText=document.createTextNode(items[3]);
                           cell2.appendChild(currentText);
                           mycurrent_row.appendChild(cell2);
                           cell2=document.createElement("TD");
	                       cell2.style.textAlign='center';
                         
	                       var currentText=document.createTextNode(items[5]);
                           cell2.appendChild(currentText);
                           mycurrent_row.appendChild(cell2);
                           cell2=document.createElement("TD");
	                       cell2.style.textAlign='center';
	                       
	                       var currentText=document.createTextNode(items[6]);
                           cell2.appendChild(currentText);
                           mycurrent_row.appendChild(cell2);
                           cell2=document.createElement("TD");
	                       cell2.style.textAlign='center';
                    
                     tbody.appendChild(mycurrent_row);
                   
                    }
                      alert("Arrival Already Done For SL");
                      document.getElementById("blockSubmit").style.display="none";
                      document.getElementById("secExit").style.display="block";
                      document.getElementById("submit").disabled=true;
                  }
                  else 
                    {
                	//  alert("No Record Found");
                	  document.getElementById("blockSubmit").style.display="block";
                	  document.getElementById("secExit").style.display="none";
                	  
                	  document.getElementById("submit").disabled=false;
                    }
                    
           } 
         } 
    }
}