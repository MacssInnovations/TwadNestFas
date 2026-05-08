setTimeout('clear_all()',50);

//setTimeout('loadatfirst()',450);


var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;var tot_amt=0,seqgen=0;
var job_flag;
var common_AHead_code_flag="";
var emp_flag;
var crdrcheck=0;

/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////

/*window.onunload=function()
{
		if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
		if (winjob && winjob.open && !winjob.closed) winjob.close();
		if (winemp && winemp.open && !winemp.closed) winemp.close();
}
*/

/*
 * Check Remarks Field Length 
*/ 

function check_leng(remarks,param)
{
		if(param=='remarks')
		{
			if((remarks.length)>=240)
		    {
				alert("Please Enter Remarks below 250 characters");
		    }
		}
	  
		if(param=='received_from') 
		{
		    if((remarks.length)>=45)
		    {
		    	alert("Please Enter Received From name below 50 characters");
		    }
		}
	  
		if(param=='particulars') 
		{
		    if((remarks.length)>=190)
		    {
		    	alert("Please Enter Paticulars below 200 characters");
		    }
		}
}




/*
 * Check Account Head Code and Bank Account Number 
*/
 
function account_head_code()
{
    	var acc_head=document.getElementById("txtAcc_HeadCode").value;
    	var url="../../../../../Cash_Receipt_Creation.view?Command=Acc_Head_Check&acc_head="+acc_head;
    	var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
        	Account_Headcode_Check(req);
        }   
        req.send(null);
}




 
function Account_Headcode_Check(req)
{
		if(req.readyState==4)
		{
	        if(req.status==200)
	        {  
	            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	            var tagcommand=baseResponse.getElementsByTagName("command")[0];
	            var Command=tagcommand.firstChild.nodeValue;
	            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            
	            if(flag=="success")
	            {
	            	alert("You are not allow to use this Account Head Code");
	            	document.getElementById("txtAcc_HeadCode").value="";
	            	document.getElementById("txtAcc_HeadDesc").value="";               
	            }
	        }
		}    
}

///////////////////////////////////////////    TB_checking and Calender control return value handling
function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    	//alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
	    if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
	    {
            //call_clr();
            var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            var TB_date=fromcal_dateCtrl.value;
            //alert(fromcal_dateCtrl.value+"b4url")
            if(fromcal_dateCtrl.value.length!=0)
            {
            	var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                	check_TB(req,fromcal_dateCtrl);
                }   
                req.send(null);
            }
	    }
}


function call_date(dateCtrl)                        // TB_checking 
{
	    //call_clr();
	    if(checkdt(dateCtrl))
	    {
	        //doFunction('check_TB',dateCtrl.value);
	        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	        var TB_date=dateCtrl.value;
	        if(dateCtrl.value.length!=0)
	        {
	        	var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
	            var req=getTransport();
	            req.open("GET",url,true); 
	            req.onreadystatechange=function()
	            {
	            	check_TB(req,dateCtrl);
	            }   
	            req.send(null);
	        }
	        //doFunction('load_Receipt_No','null');
	    }
	    else
	    {
	    	document.getElementById("txtReceipt_No").value="";
	    }
}



function check_TB(req,dateCtrl)
{
		if(req.readyState==4)
	    {
	        if(req.status==200)
	        {  
	            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	            var tagcommand=baseResponse.getElementsByTagName("command")[0];
	            var Command=tagcommand.firstChild.nodeValue;
	            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            
	            if(flag=="success")
	            {
	            	//doFunction('load_Receipt_No','null');                 //return true;
	            }
	            else if(flag=="failure")
	            {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");
                    dateCtrl.focus();
                    document.getElementById("txtReceipt_No").value="";
	            }
	            else if(flag=="finyear")
	            {
	                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");//return false;//
                    dateCtrl.focus();
                    document.getElementById("txtReceipt_No").value="";     
	            }
	        }
	    }
}

function loadGlSlGrid()
{		
	var crdrindicator;
			if(crdrcheck==0)
			{
				crdrindicator='CR';
				crdrcheck=1;
			}else{
				if(document.frm_TPA_Raised_Create.Org_CR_DR[0].checked==true)
				{
					crdrindicator='CR';	
				}else{
					crdrindicator='DR';
				}
			}
	
			
			crdrindicator=document.getElementById("indicrdr").value;
	
		var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
		var cmbOffice_code=document.getElementById("cmbOffice_code").value;	
		
		var cashbookyear=document.getElementById("txtCB_Year").value;	
		var cashbookmonth=document.getElementById("txtCB_Month").value;	
		
		if(cashbookyear==null ||cashbookyear=="" )
		{
			 var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             
             if(day<=9 && day>=1)
             day="0"+day;
             if(month<=9 && month>=1)
             month="0"+month;
             var year=today.getYear();
             if(year < 1900) year += 1900;
             var monthArray =new Array("January", "February", "March", 
                       "April", "May", "June", "July", "August",
                       "September", "October", "November", "December");
             
             cashbookyear=year;
             cashbookmonth=month;
		}
		
		var minorheads=0;
		
		if(document.getElementById("Reason4Trf").value=="Others" && (document.getElementById("Reason4Trf").value!="")  )
		{
			minorheads=document.getElementById("transfercategory").value;
		}else 
		{
			
		}
		
		
		
		var url="../../../../../TPA_Raised_Create?command=loadGlSlGrid&txtUnitId="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&crdrindicator="+crdrindicator+"&cashbookyear="+cashbookyear+"&minorheads="+minorheads+"&cashbookmonth="+cashbookmonth+"";
	    var req=getTransport();
	    req.open("GET",url,true); 
	    document.getElementById("imgfld").style.visibility = "visible";
	    req.onreadystatechange=function()
	    {
	    	TPA_Raised_ServletResponse(req);
	    }   
	    req.send(null);
}

function TPA_Raised_ServletResponse(req)
{
		if(req.readyState==4)
		{
            if(req.status==200)
            {  
            	
	            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	            var tagcommand=baseResponse.getElementsByTagName("command")[0];
	            var Command=tagcommand.firstChild.nodeValue;                                  
	            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            var cr_amt=0.00;var dr_amt=0.00;
	            if(Command=="loadGlSlGrid")
	            {                                       
	                   if(flag=="success")
	                   {    
	                	   var tbody=document.getElementById("grid_body");
	                	    var t=0;
	                	    for(t=tbody.rows.length-1;t>=0;t--)
	                	    {
	                	       tbody.deleteRow(0);
	                	    }
	                	   
	                	   tbody=document.getElementById("grid_body");
	                	   
	                	   var head_code=baseResponse.getElementsByTagName("account_head_code"); 
	                	   for(var k=0;k<head_code.length;k++)
		              	   {
	                		   	  var ac_head=item1=baseResponse.getElementsByTagName("account_head_code")[k].firstChild.nodeValue;
	                		   	  var ac_head_desc=item1=baseResponse.getElementsByTagName("account_head_desc")[k].firstChild.nodeValue;
   	   		   		   	    	  var sub_type=baseResponse.getElementsByTagName("sub_ledger_type_code")[k].firstChild.nodeValue;
   	   		   		   	    	  var sub_type_desc=baseResponse.getElementsByTagName("sub_ledger_type_desc")[k].firstChild.nodeValue;
   	   		   		   	    	  if(sub_type_desc=="null")
   	   		   		   	    		  sub_type_desc="";
   	   		   		   	    	  var sub_code=baseResponse.getElementsByTagName("sub_ledger_code")[k].firstChild.nodeValue;
   	   		   		   	    	  if(sub_code==0)
   	   		   		   	    		  sub_code="";
   	   		   		   	    	  var sub_code_desc=baseResponse.getElementsByTagName("sub_ledger_desc")[k].firstChild.nodeValue;
   	   		   		   	    	  if(sub_code_desc=="--")
   	   		   		   	    		  sub_code_desc="";
   	   		   		   	    	  var cr_dr=baseResponse.getElementsByTagName("cr_dr_indicator")[k].firstChild.nodeValue;
   	   		   		   	    	  var amt=baseResponse.getElementsByTagName("closing_balance")[k].firstChild.nodeValue;
   	   		   		   	    	  //tot_amt=baseResponse.getElementsByTagName("total_amt")[k].firstChild.nodeValue;
   	   		   		   	    	  
   	   		   		   	var indicator=baseResponse.getElementsByTagName("balanceindicator")[k].firstChild.nodeValue; 
	   		   		   	     
		              			  var mycurrent_row=document.createElement("TR");                
		              	          mycurrent_row.id=seq;
		              	          
		              	       
		   	   		   		   	   
		              	        var descell=document.createElement("TD");
								descell.style.textAlign='center'; 
								var chcksel="";
								if(window.navigator.appName.toLowerCase().indexOf("netscape")==-1)
								{
									chcksel=document.createElement("<input type='checkbox' name='chckparameter' id='chckparameter' value='"+seq+"' checked='checked'/>");
								}
								else
								{
									var chcksel=document.createElement("input");
									chcksel.type="checkbox";
									chcksel.name="chckparameter";
									chcksel.id="chckparameter";                
									chcksel.value= seq;
									chcksel.checked=true;
								}
								descell.appendChild(chcksel);
								mycurrent_row.appendChild(descell);

		              	          
		              	          
		              	          
		              	          
		              	          cell2=document.createElement("TD"); 
		              	        if(indicator=='Black')
			   		   		   	   {
		              	        	cell2.style.color="green";
			   		   		   	   }
		              	          var item=document.createElement("input");
		              	          item.type="hidden";
		              	          item.name="H_code";
		              	          item.value=ac_head;
		              	          cell2.appendChild(item);
		              	          var currentText=document.createTextNode(ac_head+"-"+ac_head_desc);
		              	          cell2.appendChild(currentText);
		              	          mycurrent_row.appendChild(cell2);
		              	          
		              	          cell2=document.createElement("TD"); 
		              	        if(indicator=='Black')
			   		   		   	   {
		              	        	cell2.style.color="green";
			   		   		   	   }
		              	          var item=document.createElement("input");
		              	          item.type="hidden";
		              	          item.name="SL_type";
		              	          item.value=sub_type;
		              	          cell2.appendChild(item);
		              	          var currentText=document.createTextNode(sub_type_desc);
		              	          cell2.appendChild(currentText);
		              	          mycurrent_row.appendChild(cell2);
		              	          
		              	          cell2=document.createElement("TD"); 
		              	        if(indicator=='Black')
			   		   		   	   {
		              	        	cell2.style.color="green";
			   		   		   	   }
		              	          var item=document.createElement("input");
		              	          item.type="hidden";
		              	          item.name="SL_code";
		              	          item.value=sub_code;
		              	          cell2.appendChild(item);		              	          
		              	          var currentText=document.createTextNode(sub_code_desc);
		              	          cell2.appendChild(currentText);
		              	          mycurrent_row.appendChild(cell2);
		              	          
		              	          cell2=document.createElement("TD"); 
		              	        if(indicator=='Black')
			   		   		   	   {
		              	        	cell2.style.color="green";
			   		   		   	   }
		              	          var item=document.createElement("input");
		              	          item.type="hidden";
		              	          item.name="cr_dr";
		              	          item.value=cr_dr;
		              	          cell2.appendChild(item);		              	          
		              	          var currentText=document.createTextNode(cr_dr);
		              	          cell2.appendChild(currentText);
		              	          mycurrent_row.appendChild(cell2);
		              	               
		              	          cell2=document.createElement("TD"); 
		              	        if(indicator=='Black')
			   		   		   	   {
		              	        	cell2.style.color="green";
			   		   		   	   }
		              	          var item=document.createElement("input");
		              	          item.type="text";
		              	          item.name="amount";
		              	       //item.onchange="javascript:test123()";
		              	    item.setAttribute("onchange", "javascript:test123()");
		              	          item.value=amt;
		              	          cell2.appendChild(item);		              	         
		              	          mycurrent_row.appendChild(cell2);
		              	          
		              	          cell2=document.createElement("TD");
		              	        if(indicator=='Black')
			   		   		   	   {
		              	        	cell2.style.color="green";
			   		   		   	   }
		              	          var item=document.createElement("input");
		              	          item.type="text";
		              	          item.name="particular";
		              	          item.value="";
		              	          cell2.appendChild(item);
		              	          mycurrent_row.appendChild(cell2);
		              	         
		              	          tbody.appendChild(mycurrent_row);
		              	          //clearall();
		              	          /** Increment Sequence Number */ 
		              	          seq=seq+1;
		              	          
		                   }
	                	   
	                	   var tbody1=document.getElementById("grid_body_gen");
	                	    var t=0;
	                	    for(t=tbody1.rows.length-1;t>=0;t--)
	                	    {
	                	       tbody1.deleteRow(0);
	                	    }
	                	   
	                	   tbody1=document.getElementById("grid_body_gen");
	                	   var head_code=baseResponse.getElementsByTagName("g_account_head_code"); 
	                	   //alert(head_code.length);
	                	   for(var k=0;k<head_code.length;k++)
		              	   {
	                		   	  var ac_head=item1=baseResponse.getElementsByTagName("g_account_head_code")[k].firstChild.nodeValue;
	                		   	  var ac_head_desc=item1=baseResponse.getElementsByTagName("g_account_head_desc")[k].firstChild.nodeValue;
   	   		   		   	    	  var sub_type=baseResponse.getElementsByTagName("g_sub_ledger_type_code")[k].firstChild.nodeValue;
   	   		   		   	    	  var sub_type_desc=baseResponse.getElementsByTagName("g_sub_ledger_type_desc")[k].firstChild.nodeValue;
   	   		   		   	    	  if(sub_type_desc=="null")
   	   		   		   	    		  sub_type_desc="";
   	   		   		   	    	  var sub_code=baseResponse.getElementsByTagName("g_sub_ledger_code")[k].firstChild.nodeValue;
   	   		   		   	    	  if(sub_code==0)
   	   		   		   	    		  sub_code="";
   	   		   		   	    	  var sub_code_desc=baseResponse.getElementsByTagName("g_sub_ledger_desc")[k].firstChild.nodeValue;
   	   		   		   	    	  if(sub_code_desc=="--")
   	   		   		   	    		  sub_code_desc="";
   	   		   		   	    	  var cr_dr=baseResponse.getElementsByTagName("g_cr_dr_indicator")[k].firstChild.nodeValue;
   	   		   		   	    	  var amt=baseResponse.getElementsByTagName("g_closing_balance")[k].firstChild.nodeValue;
   	   		   		   	    	  //tot_amt=baseResponse.getElementsByTagName("total_amt")[k].firstChild.nodeValue;
   	   		   		   	 var indicator=baseResponse.getElementsByTagName("g_balanceindicator")[k].firstChild.nodeValue; 
   	   		   		   	 
   	   		   		   	    	  
		              			  var mycurrent_row=document.createElement("TR");                
		              	          mycurrent_row.id=seqgen;
		              	          
		              	          
		              	        var descell=document.createElement("TD");
								descell.style.textAlign='center'; 
								var chcksel="";
								if(window.navigator.appName.toLowerCase().indexOf("netscape")==-1)
								{
									chcksel=document.createElement("<input type='checkbox' name='chckparameter' id='chckparameter' value='"+seqgen+"' checked='checked' />");
								}
								else
								{
									var chcksel=document.createElement("input");
									chcksel.type="checkbox";
									chcksel.name="chckparameter";
									chcksel.id="chckparameter";                
									chcksel.value= seq;
									chcksel.checked=true;
								}
								descell.appendChild(chcksel);
								mycurrent_row.appendChild(descell);
								

		              	          
		              	          
		              	          
		              	          
		              	          cell2=document.createElement("TD"); 
		              	        if(indicator=='Black')
			   		   		   	   {
		              	        	cell2.style.color="green";
			   		   		   	   }
		              	          var item=document.createElement("input");
		              	          item.type="hidden";
		              	          item.name="H_code";
		              	          item.value=ac_head;
		              	          cell2.appendChild(item);
		              	          var currentText=document.createTextNode(ac_head+"-"+ac_head_desc);
		              	          cell2.appendChild(currentText);
		              	          mycurrent_row.appendChild(cell2);
		              	          
		              	          cell2=document.createElement("TD"); 
		              	        if(indicator=='Black')
			   		   		   	   {
		              	        	cell2.style.color="green";
			   		   		   	   }
		              	          var item=document.createElement("input");
		              	          item.type="hidden";
		              	          item.name="SL_type";
		              	          item.value=sub_type;
		              	          cell2.appendChild(item);
		              	          var currentText=document.createTextNode(sub_type_desc);
		              	          cell2.appendChild(currentText);
		              	          mycurrent_row.appendChild(cell2);
		              	          
		              	          cell2=document.createElement("TD");
		              	        if(indicator=='Black')
			   		   		   	   {
		              	        	cell2.style.color="green";
			   		   		   	   }
		              	          var item=document.createElement("input");
		              	          item.type="hidden";
		              	          item.name="SL_code";
		              	          item.value=sub_code;
		              	          cell2.appendChild(item);		              	          
		              	          var currentText=document.createTextNode(sub_code_desc);
		              	          cell2.appendChild(currentText);
		              	          mycurrent_row.appendChild(cell2);
		              	          
		              	          cell2=document.createElement("TD");
		              	        if(indicator=='Black')
			   		   		   	   {
		              	        	cell2.style.color="green";
			   		   		   	   }
		              	          var item=document.createElement("input");
		              	          item.type="hidden";
		              	          item.name="cr_dr";
		              	          item.value=cr_dr;
		              	          cell2.appendChild(item);		              	          
		              	          var currentText=document.createTextNode(cr_dr);
		              	          cell2.appendChild(currentText);
		              	          mycurrent_row.appendChild(cell2);
		              	               
		              	          cell2=document.createElement("TD"); 
		              	        if(indicator=='Black')
			   		   		   	   {
		              	        	cell2.style.color="green";
			   		   		   	   }
		              	          var item=document.createElement("input");
		              	          item.type="text";
		              	          item.name="amount";
		              	        //item.onblur="javascript:test123()";
		              	      item.setAttribute("onchange", "javascript:test123()");
		              	          item.value=amt;
		              	          cell2.appendChild(item);		              	         
		              	          mycurrent_row.appendChild(cell2);
		              	          
		              	          cell2=document.createElement("TD");
		              	        if(indicator=='Black')
			   		   		   	   {
		              	        	cell2.style.color="green";
			   		   		   	   }
		              	          var item=document.createElement("input");
		              	          item.type="text";
		              	          item.name="particular";
		              	          item.value="";
		              	          cell2.appendChild(item);
		              	          mycurrent_row.appendChild(cell2);
		              	         
		              	          tbody1.appendChild(mycurrent_row);
		              	          //clearall();
		              	          /** Increment Sequence Number */ 
		              	        seqgen=seqgen+1;
		              	          
		                   }
	                	      
	             
	                	   
	                	 /*  
	                	   alert(baseResponse.getElementsByTagName("cr_amt")[0].firstChild.nodeValue+"   "+baseResponse.getElementsByTagName("dr_amt")[0].firstChild.nodeValue);
   		   		   	       var cr_amt1=baseResponse.getElementsByTagName("cr_amt")[0].firstChild.nodeValue;
   		   		   	       var dr_amt1=baseResponse.getElementsByTagName("dr_amt")[0].firstChild.nodeValue;
   		   		   	       
   		   		   	       var cr_amt1_gen=baseResponse.getElementsByTagName("g_cr_amt")[0].firstChild.nodeValue;
		   		   	       var dr_amt1_gen=baseResponse.getElementsByTagName("g_dr_amt")[0].firstChild.nodeValue;
   		   		   	       
   		   		   	       tot_amt=(parseFloat(cr_amt1)-parseFloat(dr_amt1));
   		   		   	      var tot_amt_gen= (parseFloat(cr_amt1_gen)-parseFloat(dr_amt1_gen));
   		   		   	      tot_amt=(parseFloat(tot_amt)+parseFloat(tot_amt_gen));
   		   		   	       if(tot_amt>0)
   		   		   	    	   document.getElementById("Amount").value=tot_amt;
   		   		   	       else
   		   		   	    	   document.getElementById("Amount").value=-(tot_amt);*/
	                	   var cr_amt1=baseResponse.getElementsByTagName("cr_amt")[0].firstChild.nodeValue;
   		   		   	       var dr_amt1=baseResponse.getElementsByTagName("dr_amt")[0].firstChild.nodeValue;
   		   		   	       var cr_amt2=baseResponse.getElementsByTagName("g_cr_amt")[0].firstChild.nodeValue;
   		   		   	       var dr_amt2=baseResponse.getElementsByTagName("g_dr_amt")[0].firstChild.nodeValue;
	                  
   		   		   	     
   		   		   	       var cr_amount=parseFloat(cr_amt1)+parseFloat(cr_amt2);
	                  var dr_amount=parseFloat(dr_amt1)+parseFloat(dr_amt2);   
	                  
   		   		   	       if(parseFloat(cr_amount)>0)
   		   		   	       {
   		   		   	  document.getElementById("Amount").value=cr_amount; 
   		   		   	       }else if(parseFloat(dr_amount)>0){
   		   		   	  document.getElementById("Amount").value=dr_amount;  
   		   		   	       }
   		   		   	      
   		   		   	document.getElementById("total").style.display='block'; 
   		   		document.getElementById("total1").style.display='block'; 
   		   	document.getElementById("imgfld").style.visibility = "hidden";
	                   }else 
	                   {
	                	   var tbody=document.getElementById("grid_body");
	                	    var t=0;
	                	    for(t=tbody.rows.length-1;t>=0;t--)
	                	    {
	                	       tbody.deleteRow(0);
	                	    }
	                	    var tbody1=document.getElementById("grid_body_gen");
	                	    var t=0;
	                	    for(t=tbody1.rows.length-1;t>=0;t--)
	                	    {
	                	       tbody1.deleteRow(0);
	                	    }
	                	   
	                	    document.getElementById("Amount").value="";   
//	                	    alert('No Data Found ');
	                	    alert('Please Generate SL and GL Posting');
	                   }
	                   
	            }
	            else if(Command=="loadTransferUnit")
                {
	            //	alert("comes here::");
                       if(flag=="success")
                       {                                      
                           var txtUnitId=document.getElementById("TransferedID");  
                          var txtsltype=document.getElementById("cmbSL_Code");
                          clear_Combo(txtsltype);
                           var child=txtUnitId.childNodes;
                          // var childone=txtsltype.childNodes;
                           for(var i=child.length-1;i>1;i--)
                           {
                        	   	  txtUnitId.removeChild(child[i]);
                           }      
                          /* for(var i=childone.length-1;i>1;i--)
                           {
                        	   txtsltype.removeChild(child[i]);
                           }   */
                           var items_id=new Array();
                           var items_name=new Array();                                    
                           var oid=baseResponse.getElementsByTagName("unit_id");
                           var oidone=baseResponse.getElementsByTagName("unit_id");
                           for(var k=0;k<oid.length;k++)
                           {
                                  items_id[k]=baseResponse.getElementsByTagName("unit_id")[k].firstChild.nodeValue;
                                  items_name[k]=baseResponse.getElementsByTagName("unit_name")[k].firstChild.nodeValue;				       	                                                  
                                  var option=document.createElement("OPTION");
                                  option.text=items_name[k];
                                  option.value=items_id[k];
                                  try
                                  {
                                	  txtUnitId.add(option);
                                	 
                                  }
                                  catch(errorObject)
                                  {
                                	  
                                      txtUnitId.add(option,null);
                                     
                                  }
                           }
                           
                           for(var j=0;j<oidone.length;j++)
                           {
                                  //items_id[j]=baseResponse.getElementsByTagName("unit_id")[j].firstChild.nodeValue;
                                 // items_name[j]=baseResponse.getElementsByTagName("unit_name")[j].firstChild.nodeValue;				       	                                                  
                                  var option=document.createElement("OPTION");
                                  option.text=items_name[j];
                                  option.value=items_id[j];
                                  try
                                  {
                                	 // txtUnitId.add(option);
                                	  txtsltype.add(option);
                                  }
                                  catch(errorObject)
                                  {
                                	  
                                     // txtUnitId.add(option,null);
                                      txtsltype.add(option,null);
                                  }
                           }
                           
                           loadatfirst();   
                       }
                       else
                       {                                                   
                           document.getElementById("TransferedID").value="";
                           document.getElementById("imgfld").style.visibility = "hidden";
                       }
               } else if(Command=="checkauthorization"){
            	   if(flag=="success")
                   { 
            		   
            		   document.getElementById("TransferedID").value=baseResponse.getElementsByTagName("acceptunit")[0].firstChild.nodeValue;   
            		   document.getElementById("Reason4Trf").value=baseResponse.getElementsByTagName("reason")[0].firstChild.nodeValue;   
            		   
            		   document.getElementById("txtCB_Month").value=baseResponse.getElementsByTagName("cashmonth")[0].firstChild.nodeValue;   
            		   document.getElementById("txtCB_Year").value=baseResponse.getElementsByTagName("cashyear")[0].firstChild.nodeValue;  
            		   var drcr=baseResponse.getElementsByTagName("tpatype")[0].firstChild.nodeValue;  
            		   for(i=0;i<document.frm_TPA_Raised_Create.Org_CR_DR.length;i++)
            		   {
            			   if(document.frm_TPA_Raised_Create.Org_CR_DR[i].value==drcr)
            				   document.frm_TPA_Raised_Create.Org_CR_DR[i].checked=true; 
            		   }
            		   document.getElementById("indicrdr").value=drcr; 
            		   
            		   document.getElementById("TransferedID").disabled=true;
            		   document.getElementById("Reason4Trf").disabled=true;
            		   document.getElementById("txtCB_Month").disabled=true;
            		   document.getElementById("txtCB_Year").disabled=true;
            		   
            		   document.getElementById("cmbSL_type").disabled=true;
            		   document.getElementById("cmbSL_Code").disabled=true;
            		   
            		   document.frm_TPA_Raised_Create.Org_CR_DR[0].disabled = true;
            		   document.frm_TPA_Raised_Create.Org_CR_DR[1].disabled = true;
            		   
            		   
            		   document.getElementById("tohide").style.display='none';
            		   loadAccountHead();
            		   //checking dhana
            		   document.getElementById("imgfld").style.visibility = "hidden";
            		   
            		   loadsltype();
            		   loadGlSlGrid(); 
            		   
            		   
                   }
            	   else if(flag=="others")
            	   {
            		  // loadGlSlGrid();    
            		   
            	   }
            	   
            	   else{
                	   alert("Compiliation Section has not Authorised for TPA.\n Do you want to proceed for 'Others'? ");
                	   
                	   document.getElementById("TransferedID").disabled=false;
            		   
            		   document.getElementById("Reason4Trf").value='Others';
            		   loadtranfercategory();
            		   document.getElementById("Reason4Trf").disabled=true;
            		   
            		  
            		   
            		   document.getElementById("txtCB_Month").disabled=false;
            		   document.getElementById("txtCB_Year").disabled=false;
            		   
            		   
            		   
            		   
               	   var tbody=document.getElementById("grid_body");
               	    var t=0;
               	    for(t=tbody.rows.length-1;t>=0;t--)
               	    {
               	       tbody.deleteRow(0);
               	    }
               	    var tbody1=document.getElementById("grid_body_gen");
               	    var t=0;
               	    for(t=tbody1.rows.length-1;t>=0;t--)
               	    {
               	       tbody1.deleteRow(0);
               	    }
               	   
               	    document.getElementById("Amount").value="";   
               	   
                   }
               }
            }  
		}
}

function loadAccountHead()
{
		
		if(document.frm_TPA_Raised_Create.Org_CR_DR[0].checked==true){
			document.frm_TPA_Raised_Create.txtAcc_HeadCode.value=620101;
			document.frm_TPA_Raised_Create.txtAcc_HeadDesc.value="TRANSFER PROFORMA CREDIT   -TRANSFER CREDIT";
			document.frm_TPA_Raised_Create.Indi_CR_DR[0].checked=true;
			 document.getElementById("indicrdr").value="CR";
		}
		else{
			document.frm_TPA_Raised_Create.txtAcc_HeadCode.value=900301;
			document.frm_TPA_Raised_Create.Indi_CR_DR[1].checked=true;
			document.frm_TPA_Raised_Create.txtAcc_HeadDesc.value="TRANSFER PROFORMA DEBIT A/C   -T.P. DEBIT A/C";
			document.getElementById("indicrdr").value="DR";
		}
		
		//doFunction('checkCode','null');
		
		//loadGlSlGrid();
}

function clear_Combo(combo)
{       
        var cmbSL_Code=document.getElementById(combo.id);   
        cmbSL_Code.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select Code--";
        option.value="";
        try
        {
        	cmbSL_Code.add(option);
        }catch(errorObject)
        {
        	cmbSL_Code.add(option,null);
        } 
}

function loadTransferUnit()
{         
		var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;		 
        url="../../../../../TDA_Raised_Create?command=loadTransferUnit&txtUnitId="+cmbAcc_UnitCode;
     //  alert("url:::"+url);
        req=getTransport();
        req.open("GET",url,true);     
        document.getElementById("imgfld").style.visibility = "visible";
        req.onreadystatechange=function()
        {        	  
            TPA_Raised_ServletResponse(req);
        } ;  
        req.send(null);     
}

function checkNull()
{ 
	 document.getElementById("TransferedID").disabled=false;
	   document.getElementById("Reason4Trf").disabled=false;
	   
	   document.getElementById("txtCB_Month").disabled=false;
	   document.getElementById("txtCB_Year").disabled=false;
	   
	   document.getElementById("cmbSL_type").disabled=false;
	   document.getElementById("cmbSL_Code").disabled=false;
	
	   document.frm_TPA_Raised_Create.Org_CR_DR[0].disabled = false;
	   document.frm_TPA_Raised_Create.Org_CR_DR[1].disabled = false;
	   
        var tbody=document.getElementById("grid_body");
               if(document.getElementById("Voucher_Date").value.length==0)
        {
	            alert("Enter the date of creation");           
	            return false;    
        }
        else if(document.getElementById("TransferedID").value=="")
        {
            	alert("Select transfered office id");
            	return false;
        }
        else if(document.getElementById("Reason4Trf").value=="")
        {
            	alert("Select reason for transfer");
            	return false;
        }
        else if(document.getElementById("txtAcc_HeadCode").value=="")
        {
            	alert("Select account head code");
            	return false;
        }
        else if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
        {
 		        if(window.confirm("You have not selected Sub-Ledger Type \n Do you want to select it,click 'OK'?"))
 		        {
 			             if(document.getElementById("cmbSL_type").value=="")
 			             {
 				                alert("Select a Sub-Ledger Type");
 				                return false;
 			             } 
 		        }
 		                 
         }
         else if(document.getElementById("cmbSL_type").value!="")
         {
 	          	if(document.getElementById("cmbSL_Code").value=="")
 	            {
 			             alert("Select The Sub Ledger Code");
 			             return false;
 	            }
         }
         else if(document.getElementById("Amount").value=="")
         {
            	alert("Enter TPA detail amount");
            	return false;
         }      
         else if(tbody.rows.length==0)
		 {
				alert("Enter Details Part");
				return false;
		 }
         else 
         {
              /*  var dr_check_amt=0;var cr_check_amt=0;var count=0;
                rows=tbody.getElementsByTagName("TR");       
                for(i=0;i<rows.length;i++)
                {                		
                        var cells=rows[i].cells;                                              
                        if(cells.item(3).firstChild.value=='CR')
                        {
                                cr_check_amt=cr_check_amt + cells.item(4).firstChild.value;
                                                                   	                	
                        } 
                        else if(cells.item(3).firstChild.value=='DR')
                        {
                                dr_check_amt=dr_check_amt + cells.item(4).firstChild.value;                                 	                	
                        } 
                }  
                //alert(cr_check_amt+" "+dr_check_amt);
                alert(tot_amt);
                if(tot_amt>0)
                {
	                	if(tot_amt!=document.getElementById("Amount").value)
	                    {
	                            alert("Total Amount of Grid & TPA Amount should be equal");
	                            return false; 
	                    }
	                    else if(document.frm_TPA_Raised_Create.Indi_CR_DR[0].checked==false||document.frm_TPA_Raised_Create.Org_CR_DR[0].checked==false)
	                    {
	                    		alert("TPA indicator should be CR");
	                    		return false; 
	                    }
	                    else
	                    		return true;
                }
                else
                {
	                	if(-(tot_amt)!=document.getElementById("Amount").value)
	                    {
	                            alert("Total Amount of Grid & TPA Amount should be equal");
	                            return false; 
	                    }
	                    else if(document.frm_TPA_Raised_Create.Indi_CR_DR[1].checked==false || document.frm_TPA_Raised_Create.Org_CR_DR[1].checked==false)
	                    {
	                    		alert("TPA indicator should be DR");
	                    		return false; 
	                    }
	                    else
	                    		return true;
                }
                */
        	 return true;
         }       
       
       		
}


function clear_all()
{
	/* var tbody=document.getElementById("grid_body");
	    var t=0;
	    for(t=tbody.rows.length-1;t>=0;t--)
	    {
	       tbody.deleteRow(0);
	    }
	    var tbody1=document.getElementById("grid_body_gen");
	    var t=0;
	    for(t=tbody1.rows.length-1;t>=0;t--)
	    {
	       tbody1.deleteRow(0);
	    }*/
	   
	    document.getElementById("Amount").value="";  
	    document.getElementById("txtAcc_HeadCode").value="";  
	    document.getElementById("txtAcc_HeadDesc").value="";  
	    document.getElementById("DetParticular").value="";  
	    LoadAccountingUnitID('LIST_ALL_UNITS');
	    setTimeout('loadTransferUnit()',500);
	    document.getElementById("Reason4Trf").value="";  
	    document.getElementById("GenParticulars").value="";
	    loadDate();
	    
	    document.getElementById("tohide").style.display='none';
	    document.getElementById("TransferedID").disabled=false;
		   document.getElementById("Reason4Trf").disabled=false;
		   
		   document.frm_TPA_Raised_Create.Org_CR_DR[0].disabled = false;
		   document.frm_TPA_Raised_Create.Org_CR_DR[1].disabled = false;
}



function test123()
{
	
	var tbody=document.getElementById("grid_body");
	rows=tbody.getElementsByTagName("TR");    
	var cr_check_amt1=0;
	var dr_check_amt1=0;
	
	var cr_check_amt2=0;
	var dr_check_amt2=0;
	
	 for(i=0;i<rows.length;i++)
     {                		
             var cells=rows[i].cells;
             if(cells.item(0).firstChild.checked)
             {
             
             dr_check_amt1=parseFloat(dr_check_amt1) + parseFloat(cells.item(5).firstChild.value);                                 	                	
             }
     }
	 
	 var tbody=document.getElementById("grid_body_gen");
		rows=tbody.getElementsByTagName("TR");  
		
		 for(i=0;i<rows.length;i++)
	     {                		
		      var cells=rows[i].cells;    
		      if(cells.item(0).firstChild.checked)
	             {
	             
	           
	          dr_check_amt2=parseFloat(dr_check_amt2) + parseFloat(cells.item(5).firstChild.value);  
	             }     
	      
	     }
		// alert(parseFloat(dr_check_amt1));
		// alert(parseFloat(dr_check_amt2)); 
		
		 document.getElementById("Amount").value=parseFloat(dr_check_amt1)+ parseFloat(dr_check_amt2); 	  
}


function call_clr()
{}

function loadatfirst()
{
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
	var url="../../../../../TPA_Raised_Create?command=checkauthorization&txtUnitId="+cmbAcc_UnitCode+"";
    var req=getTransport();
    req.open("GET",url,true); 
    document.getElementById("imgfld").style.visibility = "visible";
    req.onreadystatechange=function()
    {
    	TPA_Raised_ServletResponse(req);
    } ;  
    req.send(null);
}





function loadtranfercategory()
{         
	
	
	
	if( document.getElementById("Reason4Trf").value=="Others" && (document.getElementById("Reason4Trf").value!="")){
	 document.getElementById("tohide").style.display='block';
	// loadGlSlGrid();    
	}else if( document.getElementById("Reason4Trf").value!="Others" ){
		document.getElementById("tohide").style.display='none';
		
		var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
		var cmbOffice_code=document.getElementById("cmbOffice_code").value;	
		
		var cashbookyear=document.getElementById("txtCB_Year").value;	
		var cashbookmonth=document.getElementById("txtCB_Month").value;	
		
		if(cashbookyear==null ||cashbookyear=="" )
		{
			alert('Please Enter Cash Book Year');
			return false;
		}
		
		if(document.getElementById("Voucher_Date").value=="")
		{
			alert('Please Select Date');
			return false;
		}
		
		vdate=document.getElementById("Voucher_Date").value;
		
		var reason=document.getElementById("Reason4Trf").value;
		
		if(document.frm_TPA_Raised_Create.Org_CR_DR[0].checked==true)
		{
			crdrindicator='CR';	
		}else{
			crdrindicator='DR';
		}
		crdrindicator=document.getElementById("indicrdr").value;
		//alert(crdrindicator);
		var url="../../../../../TPA_Raised_Create?command=checkauthorization&txtUnitId="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&crdrindicator="+crdrindicator+"&cashbookyear="+cashbookyear+"&cashbookmonth="+cashbookmonth+"&reason="+reason+"&vdate="+vdate+"";
	    var req=getTransport();
	    req.open("GET",url,true); 
	    req.onreadystatechange=function()
	    {
	    	TPA_Raised_ServletResponse(req);
	    } ;  
	    req.send(null);
	}else{
		 document.getElementById("tohide").style.display='none';
	}
	
}

function loadsltype()
{
	
	var val= document.getElementById("TransferedID").value;
	document.getElementById("cmbSL_Code").value=val;		

}

function selectAll(Opt)
{
alert("enetr into");
  var len=  document.getElementById("grid_body").rows.length;
  
  if(len==1)
  {
          if ( Opt =="ALL")
          {
             document.getElementById("chckparameter").checked=true;
          }
          else if (Opt=="UNSelect" )
          {
             document.getElementById("chckparameter").checked=false;
          }
  }
  else if(len>1)
  {
          for(var i=0;i<len;i++)
          {
                if ( Opt =="ALL")
                {
                    document.frm_TPA_Raised_Create.chckparameter[i].checked=true;
                }
                else if(Opt=="UNSelect")
                {
                    document.frm_TPA_Raised_Create.chckparameter[i].checked=false;
                }
          }
  }

}
