/*
 *    Null Values Checking 
 */

var seq=0;
function checknull()
{
    if((document.getElementById("txtCB_Year").value.length!=4)|| (document.getElementById("txtCB_Year").value.length<=0) ||(document.getElementById("txtCB_Year").value==""))
    {
        alert("Enter the correct year");
        document.getElementById("txtCB_Year").focus();
        return false;
    }
    if(document.getElementById("txtCB_Month").value=="")
    {
        alert("Select a month");
        return false;
    }
    /*if((document.frmGeneralLedgerSystem.cmbAccHeadCode.value=="") || (document.frmGeneralLedgerSystem.cmbAccHeadCode.value.length<=0) || (document.frmGeneralLedgerSystem.cmbAccHeadCode.value=="0"))
    {
        alert("Please Select Account Head Code");
        document.frmGeneralLedgerSystem.cmbAccHeadCode.focus();
        return false;
    }*/
    
 
}



/**
 *  Numbers only Checking 
 */

function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
   
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48||unicode>57 ) 
            return false 
    }
 }
 
 
 
 /**
  *   Confirmation before Submiting Form 
  */
    
 
 function confirmation()
 {
 
  /** Call Cehcknull Function */
  checknull();
  
  if(confirm("Are you sure do you want to Freeze Supplement Trial Balance ?"))
  {
     var conf=document.getElementById("frmTrialBalance");
     conf.action="../../../../../FreezeTB_SJV.kv";
     conf.submit();
     return true;
  
  } 
  else 
  {
      return false;
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
         url="../../../../../FreezeTB_SJV.kv?command=verify&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtCB_Month="+txtCB_Month+"&txtCB_Year="+txtCB_Year;
 	     //  alert("URL>>>>"+url);
 	        var req=getTransport();
 	         req.open("GET",url,true);        
 	         req.onreadystatechange=function()
 	         {        	  
 	                verify_func(req);
 	         };   
 	         req.send(null);  
 		        
 }
 function verify_func(req)
 {

	// alert("request>>>>"+req);
	// alert("inside verify_func");
	 
	 if(req.readyState==4)
		// alert(req.readyState);
   {
       if(req.status==200)
    	 //  alert(req.status);
       {
    	  // alert("Reponse>>>"+req.responseText);
    	   
    	   var baseResponse=req.responseXML.getElementsByTagName("response")[0];
          // alert("hai");
             var tagcommand=baseResponse.getElementsByTagName("command")[0];
             var Command=tagcommand.firstChild.nodeValue;  
           //alert("Command>>>>"+Command);
           
                if(Command=="verify")
               {
             	  // alert("inside manipulate_tda");
                	
                	verify_cr_dr_checking(baseResponse);
                  
               } 
                
       }
   }
 }
 function verify_cr_dr_checking(baseResponse)
 {
	 
	 
	 if(baseResponse.getElementsByTagName("flag")[0] == undefined){
		var flag = ""; 
	 }else{
		 var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	 }
	// alert("Flag>>>>>"+flag);
     var incr=0;
     var zero=0;
     
       if(flag=="success"){
       
      	   var count=baseResponse.getElementsByTagName("Descr");
      	  // alert("count>>1>>"+count.length);
           var tbody=document.getElementById("grid_body1");
           try{tbody.innerHTML="";}
           catch(e) {tbody.innerText="";}     
           
           for(var i=0;i<count.length;i++)
           {     
        	   var Descr=baseResponse.getElementsByTagName("Descr")[i].firstChild.nodeValue;
               var accounting_unit_id=baseResponse.getElementsByTagName("accounting_unit_id")[i].firstChild.nodeValue;
               var year=baseResponse.getElementsByTagName("year")[i].firstChild.nodeValue;
               var month=baseResponse.getElementsByTagName("month")[i].firstChild.nodeValue;
               var debit=baseResponse.getElementsByTagName("debit")[i].firstChild.nodeValue;
               var credit=baseResponse.getElementsByTagName("credit")[i].firstChild.nodeValue;
               var diff=baseResponse.getElementsByTagName("diff")[i].firstChild.nodeValue;
              // alert("diff>>>"+diff);
               if(diff==0)
               {
            	   
               }
               else
               {
              	 zero++; 
               }
              // alert("zero>>>"+zero);
               
               var mycurrent_row=document.createElement("TR");
               mycurrent_row.id=seq;
              // alert("SEQUENCE>>>>"+seq);
          
               var cell2=document.createElement("TD");
               cell2.style.textAlign = 'center';
               var currentText1=document.createTextNode(Descr);
               cell2.appendChild(currentText1);
               mycurrent_row.appendChild(cell2);
          
               var cell2=document.createElement("TD");   	             
               cell2.style.textAlign = 'center';
               var currentText1=document.createTextNode(accounting_unit_id);
               cell2.appendChild(currentText1);
               mycurrent_row.appendChild(cell2);
   
               var cell2=document.createElement("TD"); 
               cell2.style.textAlign = 'center';
               var currentText1=document.createTextNode(year);
               cell2.appendChild(currentText1);
               mycurrent_row.appendChild(cell2);
               
               var cell2=document.createElement("TD");
               cell2.style.textAlign = 'center';
               var currentText1=document.createTextNode(month);
               cell2.appendChild(currentText1);
               mycurrent_row.appendChild(cell2);
               
               var cell2=document.createElement("TD");  
               cell2.style.textAlign = 'center';
               var currentText1=document.createTextNode(debit);
               cell2.appendChild(currentText1);
               mycurrent_row.appendChild(cell2);
              
               var cell2=document.createElement("TD");
               cell2.style.textAlign = 'center';
               var currentText1=document.createTextNode(credit);
               cell2.appendChild(currentText1);
               mycurrent_row.appendChild(cell2); 
               
               var cell2=document.createElement("TD");
               cell2.style.textAlign = 'center';
               var currentText1=document.createTextNode(diff);
               cell2.appendChild(currentText1);
               mycurrent_row.appendChild(cell2); 
               
               
               tbody.appendChild(mycurrent_row);
               seq++;	  
             //  alert("Sequence>>>>>"+seq);
               
               
               
               if(Descr=='TB')
               {
            	   var TBdebit=debit;
            	   var TBcredit=credit;
            	   //alert(TBdebit+"<<<<<<<TBdebit[2]>>>>>>");
               }
               if(Descr=='GL')
               {
            	   var GLdebit=debit;
            	   var GLcredit=credit;
            	   //alert(GLdebit+"<<<<<<<<GLdebit[2]>>>>>>");
               }
               if(Descr=='Transaction')
               {
            	   var Transdebit=debit;
            	   var Transcredit=credit;
            	  // alert(Transdebit+"<<<<<<<<GLdebit[2]>>>>>>");
               }
               
               
               
               
               
           }
          // alert("Zero********"+zero);
           if(zero>0)
           {
        	   alert("TB Not Tallied.Please check.......");
        	   document.getElementById("submit").disabled=true;
        	   return false;
           }
           else if(zero==0)
        	   {
        	//   alert("Submit Enabled!!!!.....");
        	   
        	   document.getElementById("submit").disabled=false;
        	   }
          
          // alert(count.length);
    	 if(count.length!=3)
    	 {
    		alert("Please complete GL Posting, TB Generation and Transactions, then freeze."); 
    		document.getElementById("submit").disabled=true;
    		return false;
    	 }
    	 
    	// alert(TBdebit+"<<<<<<<TBdebit[1]>>>>>>");
    	// alert(GLdebit+"<<<<<<<<GLdebit[2]>>>>>>");
    	// alert(Transdebit+"<<<<<<<<GLdebit[3]>>>>>>");
    	 if((TBdebit==GLdebit)&&(GLdebit==Transdebit)&&(TBdebit==Transdebit)||(TBcredit==GLcredit)&&(GLcredit==Transcredit)&&(TBcredit==Transcredit))
  		 {
  		// alert("inside db Checking!....");
  		 document.getElementById("submit").disabled=false;
  		 return true;
  		 
  		 }
  	 else
  		 {
  		 alert("Debit/Credit amount are not same2!.....");
  		 document.getElementById("submit").disabled=true;
  		 }
    	 
     }
       else if (flag=="Already_Frozen")
	   {
	   alert("Trial Balance Already Froze");
	   document.getElementById("submit").disabled=true;
	   return false;
	   }
     
     else
    	 {
    	 alert("No Record found.Please complete GL Posting,TB Generation and Transactions, then freeze.");
    	 document.getElementById("submit").disabled=true;
    	 return false;
    	 }
      
    	   
       
       
 }
 