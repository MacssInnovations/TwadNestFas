/*
 *    Null Values Checking 
 */
var HoCount;
var seq=0;
function checkHead(){
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	var txtCB_Year=document.getElementById("txtCB_Year").value;
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;		 
         url="../../../../../FreezeTB.view?command=check_head&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtCB_Month="+txtCB_Month+"&txtCB_Year="+txtCB_Year;
        // alert(url);
         req=getTransport();
         req.open("GET",url,true);        
         req.onreadystatechange=function()
         {        	  
             if(req.readyState==4)
	 { 
            	 //alert(req.readyState);
            if(req.status==200)
            {   
            	//alert(req.status);
                    var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                   // alert(baseResponse);
                    var tagcommand=baseResponse.getElementsByTagName("command")[0];
                    var Command=tagcommand.firstChild.nodeValue;                                  
                    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

//alert(flag)
                    if(flag=="success")
                    { 
                 	   var count=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;  
                 	   if(count>0){
                 		HoCount=1;
                 		alert('1');
                 		  return HoCount;
                 	   }else{
                 		   HoCount=2;
                 		   alert('2');
                 		  return HoCount;
                 	   }
                    }
                
            }
	 }
         
         } ; 
         req.send(null);  
       return HoCount;
}


function TB_Restrict()
{
	//alert("Welcome TB_Restrict Function!.....");
	
	var unitcode=document.getElementById("cmbAcc_UnitCode").value;
	var txtCB_Year=document.getElementById("txtCB_Year").value;
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	var url="../../../../../tb_check_onload?Command=tb_restrict&unitcode="+unitcode+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
	var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
       
       handleResponse_TBRestrict(req);
    };   
       req.send(null);

}
function handleResponse_TBRestrict(req)
{  
     
    if(req.readyState==4)
    { 
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="tb_restrict")
            {
            	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	 if(flag=="NotAllowed")
                 {
            		 alert("Error in Scheme Code,So TB Freeze not allowed!....");
            		 window.close();
                 }
            	 else
            	 {
            		 
            	 }
            }
           
        }
    }
}






function checknull()
{
    //alert("Inside check null function calling!......")
	
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

function checkVerification()
{       
	
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	if(txtCB_Month>3){
		//document.getElementById("areaId").style.display="block"; 
	
		 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;		 
         url="../../../../../TDA_Raised_Create?command=check_TB&txtUnitId="+cmbAcc_UnitCode;
        // alert(url);
         req=getTransport();
         req.open("GET",url,true);        
         req.onreadystatechange=function()
         {        	  
                TDA_Raised_ServletResponse(req);
         };   
         req.send(null);  
	}
	else
	{
		//document.getElementById("areaId").style.display="none"; 
	}
        
}


function TDA_Raised_ServletResponse(req)
{
		 
                 if(req.readyState==4)
		 {
                if(req.status==200)
                {  
                        var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                      //  alert(baseResponse);
                        var tagcommand=baseResponse.getElementsByTagName("command")[0];
                        var Command=tagcommand.firstChild.nodeValue;                                  
                        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                        if(Command=="check_TB")
                        {                                       
                             
                               if(flag=="success")
                               {                                      
                            	   var march=baseResponse.getElementsByTagName("march")[0].firstChild.nodeValue;
                            	   var supp=baseResponse.getElementsByTagName("supp")[0].firstChild.nodeValue;
                            	   var april=baseResponse.getElementsByTagName("april")[0].firstChild.nodeValue;
                            	   if(march=="NoDatainMarch")
                            	   {
                            		   alert("Please Verify Regular March TDA_TCA Verify Menu under MonthEndOperations(Regular) and Then Freeze");
                            	   }
                            	   else if(march=="NotTally")
                            	   {
                            		   alert("Regular March TDA_TCA is Not Tallied in Menu under MonthEndOperations(Regular)");
                            	   }
                            	   
                            	   
                            	   if(supp=="NoDatainsupp")
                            	   {
                            		   alert("Please Verify Supplement March TDA_TCA Verify Menu under MonthEndOperations(Regular) and Then Freeze");
                            	   }
                            	   else  if(supp=="NotTally")
                            	   {
                            		   alert(" Supplement March TDA_TCA is Not Tallied in Menu under MonthEndOperations(Regular)");
                            	   }
                            	   
                            	   
                            	   if(april=="NoDatainApril")
                            	   {
                            		   alert("Please Verify Regular April TDA_TCA Menu under MonthEndOperations(Regular) and Then Freeze");
                            	   }
                            	   else  if(april=="NotTally")
                            	   {
                            		   alert("Regular April TDA_TCA is Not Tallied in Menu under MonthEndOperations(Regular)");
                            	   }
                            	   
                            	   
                               }
                               else
                               {                                                   
                                       alert("Please Verify Regular March-2012,Supplement March-2012 and Regular April-2012 TDA/TCA Menu under MonthEndOperations(Regular) and Then Freeze ");
                               }
                       }
              }
		 }    
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
/*  var res=checkHead();alert("res >> "+res);
  if(res==1)
	  alert(" Account Head code 391402 and 391403 are not equal .. ");*/
  if(checkHead){
  if(confirm("Are you sure do you want to Freeze Trial Balance ?"))
  {
     var conf=document.getElementById("frmTrialBalance");
     conf.action="../../../../../FreezeTB.view";
     conf.submit();
     return true;
  
  } 
  else 
  {
      return false;
  }  
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
         url="../../../../../FreezeTB.view?command=verify&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtCB_Month="+txtCB_Month+"&txtCB_Year="+txtCB_Year;
 	     //  alert("URL>>>>"+url);
 	        var req=getTransport();
 	         req.open("GET",url,true);        
 	         req.onreadystatechange=function()
 	         {        	  
 	                verify_func(req);
 	         };   
 	         req.send(null);  
 		        
 }
 
 function tr_tb_verify()
 {
	 var tbody=document.getElementById("grid_body1");
      try{tbody.innerHTML="";}
  catch(e) {tbody.innerText="";} 
  
		var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
    var txtCB_Year=document.getElementById("txtCB_Year").value;
    var txtCB_Month=document.getElementById("txtCB_Month").value;
    url="../../../../../FreezeTB.view?command=tr_tb_verify&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtCB_Month="+txtCB_Month+"&txtCB_Year="+txtCB_Year;
     //  alert("URL>>>>"+url);
        var req=getTransport();
         req.open("GET",url,true);        
         req.onreadystatechange=function()
         {        	  
        	 tr_tb_verify1(req);
         };   
         req.send(null);   
 }
 
 function tr_tb_verify1(req)
 {

	// alert("request>>>>"+req);
	// alert("inside tr_tb_verify1");
	 
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
           
                if(Command=="tr_tb_verify")
               {
             	                	
                	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                	if(flag=="success")
                		{
                		//alert("Success******");
                		document.getElementById("submit").disabled=false;
                		return true;
                		}
                	if(flag=="tr_difference");
                	{
                		alert("Head of Account in Transaction but not in Trial balance!!!!!!!");
                		document.getElementById("submit").disabled=true;
                		return false;
                	}
                    if(flag=="tb_difference");
                	{
                		alert("Head of Account in Trial balance but not in Transaction!!!!!!!");
                		document.getElementById("submit").disabled=true;
                		return false;
                	}
                	if(flag=="verify_difference");
                	{
                		alert("Head of Account Difference in verification!!!!!!!");
                		document.getElementById("submit").disabled=true;
                		return false;
                	}
                	
                  
               } 
                
       }
   }
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
	 var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
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
             //  alert("diff>>>"+diff);
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
  		 alert("Debit/Credit amount are not same1!.....");
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
 