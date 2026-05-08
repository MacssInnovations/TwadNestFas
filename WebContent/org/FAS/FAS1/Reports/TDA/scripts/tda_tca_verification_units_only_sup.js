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
    var supNo=document.getElementById("supNo").value;
    if(supNo=="")
    {
    	 alert("Choose Supplement No");
         return false;
    }
    var tbody=document.getElementById("grid_body1");

    if(tbody.rows.length==0)
    {
        alert("Enter the Details Part");
       return false; 
    }
    else{
	    	  url="../../../../../../tda_tca_verification_units_only_sup?" +"command=submit_all&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	    	  "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbOffice_code="+cmbOffice_code+"&supNo="+supNo;
	       //  alert("Loading");
	         req=getTransport();
	         req.open("GET",url,true);        
	         req.onreadystatechange=function()
	         {        	  
	        	 manipulate_tda_sup(req);
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
        var supNo=document.getElementById("supNo").value;
        	  url="../../../../../../tda_tca_verification_units_only_sup?" +"command=verify_cr_dr&cmbAcc_UnitCode="+cmbAcc_UnitCode+
        	  "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&supNo="+supNo;
	      //  alert("Loading");
	         req=getTransport();
	         req.open("GET",url,true);        
	         req.onreadystatechange=function()
	         {        	  
	                manipulate_tda_sup(req);
	         }   
	         req.send(null);  
		        
}
function Suppl_Number_CheckNew()
{
	//alert("Test");
            var cb_month="";
            var cb_year="";
            var acc_unit_d="";
              
                cb_month = document.getElementById("txtCB_Month").value;
           //alert("CB month ***********"+cb_month);
                cb_year = document.getElementById("txtCB_Year").value;             
              // alert("CB year ***********"+cb_year);        
                acc_unit_d = document.getElementById("cmbAcc_UnitCode").value;            
           //alert("acc_unit_d ***********"+acc_unit_d);
             
             var url="../../../../../../Supplement_Number_Check.kv?Command=check_SJVNumberNew&cb_month="+cb_month+"&cb_year="+cb_year+"&acc_unit_d="+acc_unit_d;
        //   alert(url);
             var req=getTransport();
             req.open("GET",url,true); 
             req.onreadystatechange=function()
             {
                check_Suppl(req);
             }   
             req.send(null);       
             
}

function check_Suppl(req)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            //alert("Flag===>"+flag);
            if(flag=="Success")
              {
                  var TB=baseResponse.getElementsByTagName("Suppl_Status")[0].firstChild.nodeValue;
                  if (TB =="Avail") 
                  {
                       /** Get Supplement Combo Object */
                       var txtsupplement_no = document.getElementById("supNo");
                       txtsupplement_no.innerHTML="";
                       
                       
                       var suppl_no=baseResponse.getElementsByTagName("suppl_no");                            
                       for(i=0;i<suppl_no.length;i++)
                       {  
                                var option=document.createElement("OPTION");
                                var txt = baseResponse.getElementsByTagName("suppl_no")[i].firstChild.nodeValue;
                                var val = baseResponse.getElementsByTagName("suppl_no")[i].firstChild.nodeValue;
                                option.text=txt;
                                option.value=val;
                                try
                                {
                                    txtsupplement_no.add(option);
                                }
                                catch(errorObject )
                                {
                                    txtsupplement_no.add(option,null);
                                }  
                       }
                       
                       
                  }  
                  else if ( TB=="NotAvail") 
                  {  
                  
                       // alert("Supplement Number Not Available");                   
                        alert("TB Already frozen for this Supplement No.");    
                        var txtsupplement_no = document.getElementById("supNo");
                        txtsupplement_no.innerHTML="";
                           
                        var option=document.createElement("OPTION");
                        option.text="-- Select Suppl No. --";
                        option.value="";
                        try
                        {
                            txtsupplement_no.add(option);
                        }
                        catch(errorObject )
                        {
                            txtsupplement_no.add(option,null);
                        }
                  }  
               
              }
             else if(flag=="Failure")
             {
                 alert("Supplement Number Load Failed");
             }
        }
    }
}

function manipulate_tda_sup(req)
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
                	   alert("Record inserted into Database successfully");
                   }
                 
          }
      }
}
function verify_cr_dr_checking(baseResponse)
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
                     
                     var Tda_Dr=baseResponse.getElementsByTagName("Tda_Dr")[i].firstChild.nodeValue;
                     var Tda_Cr=baseResponse.getElementsByTagName("Tda_Cr")[i].firstChild.nodeValue;
                     var Trn_Net=baseResponse.getElementsByTagName("Trn_Net")[i].firstChild.nodeValue;
                     var Tda_Net=baseResponse.getElementsByTagName("Tda_Net")[i].firstChild.nodeValue;
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
                     mycurrent_row.appendChild(cell2); 
         
                     var cell2=document.createElement("TD");                                              
                     var currentText1=document.createTextNode(trndr);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);
                     
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(Tda_Dr);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);
                     
                     var cell2=document.createElement("TD");                                              
                     var currentText1=document.createTextNode(trncr);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);
                    
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(Tda_Cr);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                     
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(Trn_Net);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                     
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(Tda_Net);
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