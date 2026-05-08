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
function loadChe_Year()
{var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
var cmbOffice_code=document.getElementById("cmbOffice_code").value;

url="../../../../../../tda_tca_verification_units_only?" +"command=cheYear_Month&cmbAcc_UnitCode="+cmbAcc_UnitCode+
"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbOffice_code="+cmbOffice_code;
//  alert("Loading");
req=getTransport();
req.open("GET",url,true);        
req.onreadystatechange=function()
{        	  
      manipulate_tda(req);
}   ;
req.send(null);  
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
	    	  url="../../../../../../Adjustment_Memo_Verify?" +"command=submit_all&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	    	  "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbOffice_code="+cmbOffice_code;
	       //  alert("Loading");
	         req=getTransport();
	         req.open("GET",url,true);        
	         req.onreadystatechange=function()
	         {        	  
	                manipulate_tda(req);
	         }   
	         req.send(null);  
    }
}
function verify_btn1()
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
	    	  url="../../../../../../tda_tca_verification_units_only?" +"command=submit_allNew&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	    	  "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbOffice_code="+cmbOffice_code;
	       //  alert("Loading");
	         req=getTransport();
	         req.open("GET",url,true);        
	         req.onreadystatechange=function()
	         {        	  
	                manipulate_tda(req);
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
        	  url="../../../../../../Adjustment_Memo_Verify?" +"command=verify_cr_dr&cmbAcc_UnitCode="+cmbAcc_UnitCode+
        	  "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
	       //  alert("Loading");
	         req=getTransport();
	         req.open("GET",url,true);        
	         req.onreadystatechange=function()
	         {        	  
	                manipulate_tda(req);
	         };   
	         req.send(null);  
		        
}
function callGrid_Memo()
{     
		  var tbody=document.getElementById("grid_body1");
	      try{tbody.innerHTML="";}
	  catch(e) {tbody.innerText="";} 
	  
 		var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
 		var cmbOffice_code=document.getElementById("cmbOffice_code").value;
 		
        var txtCB_Year=document.getElementById("txtCB_Year").value;
        var txtCB_Month=document.getElementById("txtCB_Month").value;
        	  url="../../../../../../tda_tca_verification_units_only?" +"command=verifycheque_cr_dr&cmbAcc_UnitCode="+cmbAcc_UnitCode+
        	  "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbOffice_code="+cmbOffice_code;
	       //  alert("Loading");
	         req=getTransport();
	         req.open("GET",url,true);        
	         req.onreadystatechange=function()
	         {        	  
	                manipulate_tda(req);
	         }   
	         req.send(null);  
		        
}
function callGrid_Memo1()
{     
		  var tbody=document.getElementById("grid_body1");
	      try{tbody.innerHTML="";}
	  catch(e) {tbody.innerText="";} 
	  
 		var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
 		var cmbOffice_code=document.getElementById("cmbOffice_code").value;
 		
        var txtCB_Year=document.getElementById("txtCB_Year").value;
        var txtCB_Month=document.getElementById("txtCB_Month").value;
        	  url="../../../../../../tda_tca_verification_units_only?" +"command=verifycheque_cr_dr1&cmbAcc_UnitCode="+cmbAcc_UnitCode+
        	  "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbOffice_code="+cmbOffice_code;
	       //  alert("Loading");
	         req=getTransport();
	         req.open("GET",url,true);        
	         document.getElementById("imgfld").style.visibility = "visible";
	         req.onreadystatechange=function()
	         {        	  
	                manipulate_tda(req);
	         }   
	         req.send(null);  
		        
}
function manipulate_tda(req)
    {
    if(req.readyState==4)
      {
          if(req.status==200)
          {
               var baseResponse=req.responseXML.getElementsByTagName("response")[0];  
               var tagCommand=baseResponse.getElementsByTagName("command")[0]; 
              
               var command=tagCommand.firstChild.nodeValue; 
             
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
                   }else   if(command=="verifycheque_cr_dr")
                  {
                	   verify_cr_dr_checking1(baseResponse);
                     
                  }else if(command=="verifycheque_cr_dr1")
                  {
               	   verify_cr_dr_checking12(baseResponse);
                    
                 }
                 
          }
      }
}function verify_cr_dr_checking(baseResponse)
{
	var c_mn;
         var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       var incr=0;
       var zero=0;
         if(flag=="success"){
         
        	   var count=baseResponse.getElementsByTagName("month");
             var tbody=document.getElementById("grid_body1");
             try{tbody.innerHTML="";}
             catch(e) {tbody.innerText="";}     
             
             for(var i=0;i<count.length;i++)
             {                                    	   	   
                     var month=baseResponse.getElementsByTagName("month")[i].firstChild.nodeValue;
                     var achead=baseResponse.getElementsByTagName("achead")[i].firstChild.nodeValue;
                     var trndr=baseResponse.getElementsByTagName("trndr")[i].firstChild.nodeValue;
                     var trncr=baseResponse.getElementsByTagName("trncr")[i].firstChild.nodeValue;
                     
                     var AdjMemo_Dr=baseResponse.getElementsByTagName("AdjMemo_Dr")[i].firstChild.nodeValue;
                     var AdjMemo_Cr=baseResponse.getElementsByTagName("AdjMemo_Cr")[i].firstChild.nodeValue;
                     var Trn_Net=baseResponse.getElementsByTagName("Trn_Net")[i].firstChild.nodeValue;
                     var AdjMemo_Net=baseResponse.getElementsByTagName("AdjMemo_Net")[i].firstChild.nodeValue;
                     var difference=baseResponse.getElementsByTagName("difference")[i].firstChild.nodeValue;
                     
                     
                     
                     if(difference==0)
                     {
                    	 
                     }
                     else
                     {
                    	 zero++; 
                     }
                     
                     if(month==1)
                     {
                    	c_mn="January";
                     }
                     else if(month==2)
                     {
                     	c_mn="February";
                     }
                     else if(month==3)
                     {
                    	  	c_mn="March";
                     }
                     else if(month==4)
                     {
                     	c_mn="April";
                     }
                     else if(month==5)
                     {
                     	c_mn="May";
                     }
                     else if(month==6)
                     {
                     	c_mn="June";
                     }
                     else if(month==7)
                     {
                     	c_mn="July";
                     }
                     else if(month==8)
                     {
                     	c_mn="August";
                     }
                     else if(month==9)
                     {
                     	c_mn="September";
                     }
                     else if(month==10)
                     {
                     	c_mn="October";
                     }
                     else if(month==11)
                     {
                     	c_mn="November";
                     }
                     else if(month==12)
                     {
                     	c_mn="December";
                     }
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=seq;
                
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(c_mn);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);
                     
                
                     var cell2=document.createElement("TD");   	                                           
                     var currentText1=document.createTextNode(achead);
                     cell2.appendChild(currentText1);
                     cell2.style.textAlign = 'center';
                     mycurrent_row.appendChild(cell2); 
         
                     var cell2=document.createElement("TD");                                              
                     var currentText1=document.createTextNode(trndr);
                     cell2.appendChild(currentText1);
                     cell2.style.textAlign = 'right';
                     mycurrent_row.appendChild(cell2);
                     
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(AdjMemo_Dr);
                     cell2.appendChild(currentText1);
                     cell2.style.textAlign = 'right';
                     mycurrent_row.appendChild(cell2);
                     
                     var cell2=document.createElement("TD");                                              
                     var currentText1=document.createTextNode(trncr);
                     cell2.appendChild(currentText1);
                     cell2.style.textAlign = 'right';
                     mycurrent_row.appendChild(cell2);
                    
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(AdjMemo_Cr);
                     cell2.appendChild(currentText1);
                     cell2.style.textAlign = 'right';
                     mycurrent_row.appendChild(cell2); 
                     
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(Trn_Net);
                     cell2.appendChild(currentText1);
                     cell2.style.textAlign = 'right';
                     mycurrent_row.appendChild(cell2); 
                     
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(AdjMemo_Net);
                     cell2.appendChild(currentText1);
                     cell2.style.textAlign = 'right';
                     mycurrent_row.appendChild(cell2); 
                     
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(difference);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                     cell2.style.textAlign = 'right';
                     tbody.appendChild(mycurrent_row);
                     seq++;	                                          
             }
           
             if(zero>0)
             {
            	 
            	 var verified_already=baseResponse.getElementsByTagName("verified_already")[0].firstChild.nodeValue;
                 
               /*  if(verified_already==0)
                 {
                	 document.getElementById("btnverified").disabled=false;
                 }
                 else
                 {
                	 document.getElementById("btnverified").disabled=true; 
                 }  */
            	 
            	 alert("Verify the Difference");
            	 document.getElementById("one_id").style.display="none";
            	 document.getElementById("two_id").style.display="block"; 
            	 document.getElementById("three_id").style.display="none"; 
             }
             else
             {
            	 var verified_already=baseResponse.getElementsByTagName("verified_already")[0].firstChild.nodeValue;
                 
                 if(verified_already==0)
                 {
                	 document.getElementById("three_id").style.display="none";
                	 document.getElementById("one_id").style.display="block";
                	 document.getElementById("two_id").style.display="none";
                 }
                 else
                 {
                	 alert("Already Verified");
                	 document.getElementById("three_id").style.display="block";
                	 document.getElementById("one_id").style.display="none";
                	 document.getElementById("two_id").style.display="none";
                 }
            	 
            	
             }
              
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
               
            	 var month=document.getElementById("txtCB_Month").value;
               var tbody=document.getElementById("grid_body1");
               try{tbody.innerHTML="";}
               catch(e) {tbody.innerText="";}               
               for(var i=0;i<1;i++)
               {     
                       
                       if(month==1)
                       {
                      	c_mn="January";
                       }
                       else if(month==2)
                       {
                       	c_mn="February";
                       }
                       else if(month==3)
                       {
                       	c_mn="March";
                       }
                       else if(month==4)
                       {
                       	c_mn="April";
                       }
                       else if(month==5)
                       {
                       	c_mn="May";
                       }
                       else if(month==6)
                       {
                       	c_mn="June";
                       }
                       else if(month==7)
                       {
                       	c_mn="July";
                       }
                       else if(month==8)
                       {
                       	c_mn="August";
                       }
                       else if(month==9)
                       {
                       	c_mn="September";
                       }
                       else if(month==10)
                       {
                       	c_mn="October";
                       }
                       else if(month==11)
                       {
                       	c_mn="November";
                       }
                       else if(month==12)
                       {
                       	c_mn="December";
                       }
                       var mycurrent_row=document.createElement("TR");
                       mycurrent_row.id=seq;
                  
                       var cell2=document.createElement("TD");
                       var currentText1=document.createTextNode(c_mn);
                     
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2);
                       
                  
                       var cell2=document.createElement("TD");
                       cell2.style.textAlign = 'center';
                       var currentText1=document.createTextNode(0);
                      // currentText1.setAttribute('align','center');
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2); 
           
                      /* var cell2=document.createElement("TD");                                              
                       var currentText1=document.createTextNode(0);
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2);
                       
                       var cell2=document.createElement("TD");
                       var currentText1=document.createTextNode(0);
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2);
                       
                       var cell2=document.createElement("TD");                                              
                       var currentText1=document.createTextNode(0);
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2);
                      
                       var cell2=document.createElement("TD");
                       var currentText1=document.createTextNode(0);
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2); 
                       
                       var cell2=document.createElement("TD");
                       var currentText1=document.createTextNode(0);
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2); 
                       
                       var cell2=document.createElement("TD");
                       var currentText1=document.createTextNode(0);
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2); 
                       
                       var cell2=document.createElement("TD");
                       var currentText1=document.createTextNode(0);
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2);   */
                       
                       tbody.appendChild(mycurrent_row);
                       seq++;	                                          
               }
              
              	 document.getElementById("one_id").style.display="block";
              	 document.getElementById("two_id").style.display="none";
              
            	 
             }
}
function verify_cr_dr_checking1(baseResponse)
{
	//alert('test')
         var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       var incr=0;
       var zero=0;
         if(flag=="success"){
         
        	   var count=baseResponse.getElementsByTagName("month");
             var tbody=document.getElementById("grid_body1");
             try{tbody.innerHTML="";}
             catch(e) {tbody.innerText="";}     
             
             for(var i=0;i<count.length;i++)
             {     
            	 

            	 
                     var month=baseResponse.getElementsByTagName("month")[i].firstChild.nodeValue;
                     var CHEQUE_MEMO_NO=baseResponse.getElementsByTagName("CHEQUE_MEMO_NO")[i].firstChild.nodeValue;
                     var CHEQUE_MEMO_DATE=baseResponse.getElementsByTagName("CHEQUE_MEMO_DATE")[i].firstChild.nodeValue;
                     var Payment_Date=baseResponse.getElementsByTagName("Payment_Date")[i].firstChild.nodeValue;
                     
                     var voucher_no=baseResponse.getElementsByTagName("voucher_no")[i].firstChild.nodeValue;
                     var CHEQUE_NO=baseResponse.getElementsByTagName("CHEQUE_NO")[i].firstChild.nodeValue;
                     var cheque_date=baseResponse.getElementsByTagName("cheque_date")[i].firstChild.nodeValue;
                     var che_amt=baseResponse.getElementsByTagName("che_amt")[i].firstChild.nodeValue;
                     var pay_amt=baseResponse.getElementsByTagName("pay_amt")[i].firstChild.nodeValue;
                     var difference=baseResponse.getElementsByTagName("difference")[i].firstChild.nodeValue;
                     
                     if(difference==0)
                     {
                    	 
                     }
                     else
                     {
                    	 zero++; 
                     }
                
                  
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=seq;
                
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(month);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);
                
                     var cell2=document.createElement("TD");   	                                           
                     var currentText1=document.createTextNode(CHEQUE_NO);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
         
                     var cell2=document.createElement("TD");                                              
                     var currentText1=document.createTextNode(cheque_date);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);
                     
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(CHEQUE_MEMO_NO);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);
                     
                     var cell2=document.createElement("TD");                                              
                     var currentText1=document.createTextNode(CHEQUE_MEMO_DATE);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);
                    
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(voucher_no);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                     
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(Payment_Date);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                     
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(che_amt);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                     
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(pay_amt);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                     
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(difference);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                     
                     tbody.appendChild(mycurrent_row);
                     seq++;	                                          
             }
           
             if(zero>0)
             {
            	 
            	 var verified_already=baseResponse.getElementsByTagName("verified_already")[0].firstChild.nodeValue;
                 
               /*  if(verified_already==0)
                 {
                	 document.getElementById("btnverified").disabled=false;
                 }
                 else
                 {
                	 document.getElementById("btnverified").disabled=true; 
                 }  */
            	 
            	 alert("Verify the Difference");
            	 document.getElementById("one_id").style.display="none";
            	 document.getElementById("two_id").style.display="block"; 
            	 document.getElementById("three_id").style.display="none"; 
             }
             else
             {
            	 var verified_already=baseResponse.getElementsByTagName("verified_already")[0].firstChild.nodeValue;
                 
                 if(verified_already==0)
                 {
                	 document.getElementById("three_id").style.display="none";
                	 document.getElementById("one_id").style.display="block";
                	 document.getElementById("two_id").style.display="none";
                 }
                 else
                 {
                	 alert("Already Verified");
                	 document.getElementById("three_id").style.display="block";
                	 document.getElementById("one_id").style.display="none";
                	 document.getElementById("two_id").style.display="none";
                 }
            	 
            	
             }
              
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
               
            	 var month=document.getElementById("txtCB_Month").value;
               var tbody=document.getElementById("grid_body1");
               try{tbody.innerHTML="";}
               catch(e) {tbody.innerText="";}               
               for(var i=0;i<1;i++)
               {     
                       
                      
                       var mycurrent_row=document.createElement("TR");
                       mycurrent_row.id=seq;
                  
                       var cell2=document.createElement("TD");
                       var currentText1=document.createTextNode(month);
                     
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2);
                       
                  
                       var cell2=document.createElement("TD");
                       cell2.style.textAlign = 'center';
                       var currentText1=document.createTextNode(0);
                      // currentText1.setAttribute('align','center');
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2); 
           
                      /* var cell2=document.createElement("TD");                                              
                       var currentText1=document.createTextNode(0);
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2);
                       
                       var cell2=document.createElement("TD");
                       var currentText1=document.createTextNode(0);
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2);
                       
                       var cell2=document.createElement("TD");                                              
                       var currentText1=document.createTextNode(0);
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2);
                      
                       var cell2=document.createElement("TD");
                       var currentText1=document.createTextNode(0);
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2); 
                       
                       var cell2=document.createElement("TD");
                       var currentText1=document.createTextNode(0);
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2); 
                       
                       var cell2=document.createElement("TD");
                       var currentText1=document.createTextNode(0);
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2); 
                       
                       var cell2=document.createElement("TD");
                       var currentText1=document.createTextNode(0);
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2);   */
                       
                       tbody.appendChild(mycurrent_row);
                       seq++;	                                          
               }
              
              	 document.getElementById("one_id").style.display="block";
              	 document.getElementById("two_id").style.display="none";
              
            	 
             }
}function verify_cr_dr_checking12(baseResponse)
{
	//alert('test')
         var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       var incr=0;
       var zero=0;
         if(flag=="success"){
     
        	   var count=baseResponse.getElementsByTagName("Payment_Date");
             var tbody=document.getElementById("grid_body1");
             try{tbody.innerHTML="";}
             catch(e) {tbody.innerText="";}     
             var len=count.length;
             for(var i=0;i<len;i++)
             {     
            	 

            	 
                   //  var month=baseResponse.getElementsByTagName("month")[i].firstChild.nodeValue;
                    // var CHEQUE_MEMO_NO=baseResponse.getElementsByTagName("CHEQUE_MEMO_NO")[i].firstChild.nodeValue;
                   //  var CHEQUE_MEMO_DATE=baseResponse.getElementsByTagName("CHEQUE_MEMO_DATE")[i].firstChild.nodeValue;
                     var Payment_Date=baseResponse.getElementsByTagName("Payment_Date")[i].firstChild.nodeValue;
                     
                     var voucher_no=baseResponse.getElementsByTagName("voucher_no")[i].firstChild.nodeValue;
                     var CHEQUE_NO=baseResponse.getElementsByTagName("CHEQUE_NO")[i].firstChild.nodeValue;
                     var cheque_date=baseResponse.getElementsByTagName("cheque_date")[i].firstChild.nodeValue;
                     var che_amt=baseResponse.getElementsByTagName("che_amt")[i].firstChild.nodeValue;
                     var pay_amt=baseResponse.getElementsByTagName("pay_amt")[i].firstChild.nodeValue;
                   //  var difference=baseResponse.getElementsByTagName("difference")[i].firstChild.nodeValue;
                   difference=Math.abs(parseInt(pay_amt)-parseInt(che_amt));
                     if(difference==0)
                     {
                    	 
                     }
                     else
                     {
                    	 zero++; 
                     }
                
                 
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=seq;
                
                  /*     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(month);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);*/
                
                     var cell2=document.createElement("TD");   	                                           
                     var currentText1=document.createTextNode(CHEQUE_NO);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
         
                     var cell2=document.createElement("TD");                                              
                     var currentText1=document.createTextNode(cheque_date);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);
                     
                /*     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(CHEQUE_MEMO_NO);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);
                     
                     var cell2=document.createElement("TD");                                              
                     var currentText1=document.createTextNode(CHEQUE_MEMO_DATE);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);
                    */
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(voucher_no);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                     
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(Payment_Date);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                     
                     var cell2=document.createElement("TD");
                     if(che_amt=="" || che_amt=='null')
                     { che_amt='';
                	 var che_memeoAmount=0;
                     }else{
                    	 che_memeoAmount=che_amt;
                     }
                    
                     var currentText1=document.createTextNode(che_amt);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                     
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(pay_amt);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                 /*    if(che_amt=='null')
                    	 var che_memeoAmount=0;
                     else 
                    	 che_memeoAmount=che_amt;*/
       
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(Math.abs(parseInt(pay_amt)-parseInt(che_memeoAmount)));
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                     
             /*        var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(difference);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                     */
                     tbody.appendChild(mycurrent_row);
                     seq++;	                                          
             }
             
        
             if(zero>0)
             {
            	 
            	 var verified_already=baseResponse.getElementsByTagName("verified_already")[0].firstChild.nodeValue;
                 
               /*  if(verified_already==0)
                 {
                	 document.getElementById("btnverified").disabled=false;
                 }
                 else
                 {
                	 document.getElementById("btnverified").disabled=true; 
                 }  */
            	 
            	 alert("Verify the Difference");
            	 document.getElementById("one_id").style.display="none";
            	 document.getElementById("two_id").style.display="block"; 
            	 document.getElementById("three_id").style.display="none"; 
             }
             else
             {
            	 var verified_already=baseResponse.getElementsByTagName("verified_already")[0].firstChild.nodeValue;
                 
                 if(verified_already==0)
                 {
                	 document.getElementById("three_id").style.display="none";
                	 document.getElementById("one_id").style.display="block";
                	 document.getElementById("two_id").style.display="none";
                 }
                 else
                 {
                	 alert("Already Verified");
                	 document.getElementById("three_id").style.display="block";
                	 document.getElementById("one_id").style.display="none";
                	 document.getElementById("two_id").style.display="none";
                 }
            	 
            	
             }
              
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
            //alert('No Records Found, Please Verify ... ')
            	 /* var tbody=document.getElementById("grid_body1");
                 try{tbody.innerHTML="";}
                 catch(e) {tbody.innerText="";}      */  
            	var month=document.getElementById("txtCB_Month").value;
               var tbody=document.getElementById("grid_body1");
               try{tbody.innerHTML="";}
               catch(e) {tbody.innerText="";}               
               for(var i=0;i<1;i++)
               {     
                       
           
                       var mycurrent_row=document.createElement("TR");
                       mycurrent_row.id=seq;
                  
                       var cell2=document.createElement("TD");
                       var currentText1=document.createTextNode(0);
                     
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2);
                       
                  
                       var cell2=document.createElement("TD");
                       cell2.style.textAlign = 'center';
                       var currentText1=document.createTextNode(0);
                      // currentText1.setAttribute('align','center');
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2); 
           
                       var cell2=document.createElement("TD");                                              
                       var currentText1=document.createTextNode(0);
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2);
                       
                       var cell2=document.createElement("TD");
                       var currentText1=document.createTextNode(0);
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2);
                       
                       var cell2=document.createElement("TD");                                              
                       var currentText1=document.createTextNode(0);
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2);
                      
                       var cell2=document.createElement("TD");
                       var currentText1=document.createTextNode(0);
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2); 
                       
                       var cell2=document.createElement("TD");
                       var currentText1=document.createTextNode(0);
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2); 
                       
                       var cell2=document.createElement("TD");
                       var currentText1=document.createTextNode(0);
                       cell2.appendChild(currentText1);
                       mycurrent_row.appendChild(cell2); 
                       
                    
                       
                       tbody.appendChild(mycurrent_row);
                       seq++;	                                          
               }
               var verified_already=baseResponse.getElementsByTagName("verified_already")[0].firstChild.nodeValue;
             
               if(verified_already==0)
               {
              	 document.getElementById("three_id").style.display="none";
              	 document.getElementById("one_id").style.display="block";
              	 document.getElementById("two_id").style.display="none";
               }
               else
               {
              	 alert("Already Verified");
              	 document.getElementById("three_id").style.display="block";
              	 document.getElementById("one_id").style.display="none";
              	 document.getElementById("two_id").style.display="none";
               }
              	 /*document.getElementById("one_id").style.display="block";
              	 document.getElementById("two_id").style.display="none";*/
              
            	 
             }
         document.getElementById("imgfld").style.visibility = "hidden";
}