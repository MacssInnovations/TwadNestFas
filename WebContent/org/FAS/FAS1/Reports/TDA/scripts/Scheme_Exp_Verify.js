//alert("Scheme_Exp_Verify!........");

var seq=0;
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

function verify_btn()
{
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    var txtCB_Year=document.getElementById("txtCB_Year").value;
    var txtCB_Month=document.getElementById("txtCB_Month").value;
    var tbody=document.getElementById("grid_body1");

    if(tbody.rows.length==0)
    {
        alert("Enter the Details Part");
        // document.getElementById("txtAmount").focus();
        return false; 
    }
    else{
	    	  url="../../../../../../Scheme_Exp_Verify?" +"command=submit_all&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	    	  "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbOffice_code="+cmbOffice_code;
	       //  alert("Loading");
	         req=getTransport();
	         req.open("GET",url,true);        
	         req.onreadystatechange=function()
	         {        	  
	                manipulate_sch(req);
	         }   
	         req.send(null);  
    }
}

function callGrid()
{     
		  var tbody=document.getElementById("grid_body1");
	      try{tbody.innerHTML="";}
	  catch(e) {tbody.innerText="";} 
	  
 		var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
        var txtCB_Year=document.getElementById("txtCB_Year").value;
        var txtCB_Month=document.getElementById("txtCB_Month").value;
        	  url="../../../../../../Scheme_Exp_Verify?" +"command=verify_cr_dr&cmbAcc_UnitCode="+cmbAcc_UnitCode+
        	  "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
        	  //alert(url);
	       //  alert("Loading");
	         req=getTransport();
	         req.open("GET",url,true);        
	         req.onreadystatechange=function()
	         {        	  
	                manipulate_sch(req);
	         };   
	         req.send(null);  
		        
}

function manipulate_sch(req)
    {
    if(req.readyState==4)
    	//alert("req.readyState----------->"+req.readyState);
      {
          if(req.status==200)
        	  //alert("req.status----------->"+req.status);
        //alert("Response====>"+req.responseText); 
          {
             
        	  var baseResponse=req.responseXML.getElementsByTagName("response")[0];  
        	  
               var tagCommand=baseResponse.getElementsByTagName("command")[0]; 
              
               var command=tagCommand.firstChild.nodeValue; 
               //alert("Command========>"+command);
             
                   if(command=="verify_cr_dr")
                  {
                	   verify_cr_dr_checking(baseResponse);
                     
                  }  
                  else if(command=="submit_all")
                   {

               	   var count=baseResponse.getElementsByTagName("Payment_Date");
                    var tbody=document.getElementById("grid_body1");
                    try{tbody.innerHTML="";}
                    catch(e) {tbody.innerText="";}     
                    
                	   alert("Record inserted into Database successfully");
                   }
          }
      }
}function verify_cr_dr_checking(baseResponse)
{
	var c_mn="";
         var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        // alert("flag========>"+flag);
       var incr=0;
       var zero=0;
       var nullflag;
         if(flag=="success"){
         
        	   var count=baseResponse.getElementsByTagName("Sl_No");
             var tbody=document.getElementById("grid_body1");
             try{tbody.innerHTML="";}
             catch(e) {tbody.innerText="";}     
             
             
             
             
             var slname="";
             var slcode=0;
             
             for(var i=0;i<count.length;i++)
             {                                    	   	   
                     var sl_no=baseResponse.getElementsByTagName("Sl_No")[i].firstChild.nodeValue;
                     slcode=baseResponse.getElementsByTagName("SUB_LEDGER_CODE")[i].firstChild.nodeValue;
                     //alert(slcode);
                     
                     
                     slname=baseResponse.getElementsByTagName("SLNAME")[i].firstChild.nodeValue;
                              
                     var dr_amt=baseResponse.getElementsByTagName("DR_AMOUNT")[i].firstChild.nodeValue;
                     var cr_amt=baseResponse.getElementsByTagName("CR_AMOUNT")[i].firstChild.nodeValue;
                     var net_amt=baseResponse.getElementsByTagName("NET_AMT")[i].firstChild.nodeValue;
                     var indi=baseResponse.getElementsByTagName("NET_INDI")[i].firstChild.nodeValue;
                
                     if((slcode==0)||(slname=="null"))
                	 {
                	  nullflag=1;
                	 }
                     else if((slcode!=0)||(slname!="null"))
                    	 {
                    	 flag=0;
                    	 }          
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=seq;
                     
                     
                     
                     
                     
                     var cell2=document.createElement("TD");                                              
                     var currentText1=document.createTextNode(sl_no);
                     cell2.setAttribute("align","center");
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);
                
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(slcode);
                     cell2.setAttribute("align","center");
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);
                     
                     var cell2=document.createElement("TD");    
                     var currentText1=document.createTextNode(slname);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);
                    
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(dr_amt);
                     cell2.setAttribute("align","right");
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                     
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(cr_amt);
                     cell2.setAttribute("align","right");
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                     
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(net_amt+" "+" "+indi);
                     cell2.setAttribute("align","right");
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                     
                                         
                     tbody.appendChild(mycurrent_row);
                     seq++;	                                          
             }
             
             
             
             
             
             
             
             var verified_already=baseResponse.getElementsByTagName("verified_already")[0].firstChild.nodeValue;
             
             if(verified_already==0)
             {
//            	if(nullflag==1)  
//            	 {
//            		 alert("Please Check Scheme code/Scheme name is not null");
//            		 document.getElementById("one_id").style.display="none";
//                	 document.getElementById("two_id").style.display="block";
//                	 document.getElementById("three_id").style.display="none";
//            	 }
            	
            	 document.getElementById("three_id").style.display="none";
            	 document.getElementById("one_id").style.display="block";
            	 document.getElementById("two_id").style.display="none";     
                 
            		
             }
             else if(verified_already>0)
             {
            	 alert("Already Verified");
            	 document.getElementById("three_id").style.display="block";
            	 document.getElementById("one_id").style.display="none";
            	 document.getElementById("two_id").style.display="none";
             }
            
        	 
             
             
             
//        	 alert("Button Disabled");
//        	 document.getElementById("one_id").style.display="none";
//        	 document.getElementById("two_id").style.display="block";
//        	 document.getElementById("three_id").style.display="none";
//        	 //document.getElementById("one_id").disabled = true;
          	 //}
             //else 
            	 //{
//            	 document.getElementById("one_id").style.display="block";
//            	 document.getElementById("two_id").style.display="none";
//            	 document.getElementById("three_id").style.display="none";
//            	 //document.getElementById("one_id").disabled = false;
            	// }
             
             
              
                 
             
             
             
                        }
         else  if(flag=="failure")
         {
      
         var tbody=document.getElementById("grid_body1");
         try{tbody.innerHTML="";}
         catch(e) {tbody.innerText="";} 
         alert("No Record Exist");
     
         }
                  
             else  
             {
            	 var sl_no=baseResponse.getElementsByTagName("Sl_No");
            	 var tbody=document.getElementById("grid_body1");
                 try{tbody.innerHTML="";}
                 catch(e) {tbody.innerText="";}    
                 for(var i=0;i<1;i++)
                 { 
                	
                	 
                	 var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=seq;
                	 
                	 var cell2=document.createElement("TD");                                              
                     var currentText1=document.createTextNode(1);
                     cell2.setAttribute("align","center");
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);
                
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(0);
                     cell2.setAttribute("align","center");
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);
                     
                     tbody.appendChild(mycurrent_row);
                     seq++;
                     
                 }
//                 document.getElementById("one_id").style.display="block";
//              	 document.getElementById("two_id").style.display="none";
                 
                 var verified_already=baseResponse.getElementsByTagName("verified_already")[0].firstChild.nodeValue;
                 
                 if(verified_already==0)
                 {
                	
                	 document.getElementById("three_id").style.display="none";
                	 document.getElementById("one_id").style.display="block";
                	 document.getElementById("two_id").style.display="none";     
                     
                	
                 }
                 else if(verified_already>0)
                 {
                	 alert("Already Verified");
                	 document.getElementById("three_id").style.display="block";
                	 document.getElementById("one_id").style.display="none";
                	 document.getElementById("two_id").style.display="none";
                 }
                 
                 
         
             }
             

             
}

