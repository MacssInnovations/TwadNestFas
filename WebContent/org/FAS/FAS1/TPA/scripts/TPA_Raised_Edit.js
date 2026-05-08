
var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;
var job_flag;
var common_AHead_code_flag="";
var emp_flag;
var seqgen=0;
var reasonTransfer="";

var item1=new Array();var item2=new Array();var item3=new Array();var item4=new Array();
var item5=new Array();var item6=new Array();var item7=new Array();var item8=new Array();
var item9=new Array();
var item10=new Array();
var item11=new Array();


var g_item1=new Array();var g_item2=new Array();var g_item3=new Array();var g_item4=new Array();
var g_item5=new Array();var g_item6=new Array();var g_item7=new Array();var g_item8=new Array();
var g_item9=new Array();
var g_item10=new Array();
var g_item11=new Array();
/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////

window.onunload=function()
{
		if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
		if (winjob && winjob.open && !winjob.closed) winjob.close();
		if (winemp && winemp.open && !winemp.closed) winemp.close();
}


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

function loadTransferUnit()
{         
		var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;		 
        url="../../../../../TDA_Raised_Create?command=loadTransferUnit&txtUnitId="+cmbAcc_UnitCode;
        req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {        	  
            TPA_Raised_ServletResponse(req);
        }   
        req.send(null);     
}


function doFunction_voucher(command)
{
	
		loadAccountHead();
		var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
		var cmbOffice_code=document.getElementById("cmbOffice_code").value;
		var txtCrea_date=document.getElementById("Voucher_Date").value;
		document.getElementById("txtAcc_HeadDesc").value="";
		if(document.frm_TPA_Raised_Edit.Org_CR_DR[0].checked==true)
		    tpa_type="TPAOC";
		else
			tpa_type="TPAOD";
		loadAccountHead();
		
	//	doFunction('checkCode','null');  
		if(command=="load_Voucher_No1") 
		{
			url="../../../../../TPA_Raised_Edit?command=load_Voucher_No&Option=Edit&txtUnitId="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date+"&tpa_type="+tpa_type;
			//alert(url);
		}
		else 
		{
			var adviceNumber=document.getElementById("adviceNumber").value;
			url="../../../../../TPA_Raised_Edit?command=load_Voucher_Details&txtUnitId="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date+"&tpa_type="+tpa_type+"&adviceNumber="+adviceNumber;
			//alert(url);
		}		 
		req=getTransport();
		req.open("GET",url,true);        
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
                    //alert(flag);
                     if(Command=="loadTransferUnit")
                    {                                       
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
                               
                              
                           }
                           else
                           {                                                   
                               document.getElementById("TransferedID").value="";
                           }
                   }
                   else if(Command=="load_Voucher_No")
                   {
                    	   if(flag=="success")
                           {                                      
                                   var originated_slno=document.getElementById("adviceNumber");  
                                   var child=originated_slno.childNodes;
                                   for(var i=child.length-1;i>1;i--)
                                   {
                                	   		originated_slno.removeChild(child[i]);
                                   }                                                         
                                   var vno=baseResponse.getElementsByTagName("voucher_no");
                                   for(var k=0;k<vno.length;k++)
                                   {
                                            var voucher_no=baseResponse.getElementsByTagName("voucher_no")[k].firstChild.nodeValue;				       	                                                  
                                            var option=document.createElement("OPTION");
                                            option.text=voucher_no;
                                            option.value=voucher_no;
                                            try
                                            {
                                            		originated_slno.add(option);
                                            }
                                            catch(errorObject)
                                            {
                                            		originated_slno.add(option,null);
                                            }
                                   }
                           }
                           else
                           {                                                   
                                   alert("No Voucher Found");
                                   document.getElementById("adviceNumber").length=1;
                                  
                                   document.getElementById("TransferedID").value="";
                                   document.getElementById("Reason4Trf").value="Closure";
                                   document.getElementById("GenParticulars").value="";
                                   document.getElementById("txtOfficeID_trs").value="";
                                   document.getElementById("txtEmpID_trs").value="";
                                   document.getElementById("Amount").value="";
                                   document.getElementById("DetParticular").value="";
                                   tbody=document.getElementById("grid_body");
                           		//clearall();
                           		  var t=0;
                           	        for(t=tbody.rows.length-1;t>=0;t--)
                           	        {
                           	                tbody.deleteRow(0);
                           	               
                           	        }
                           	     tbody=document.getElementById("gl_grid_body");
                         		
                         		
                       		  var t=0;
                       	        for(t=tbody.rows.length-1;t>=0;t--)
                       	        {
                       	                tbody.deleteRow(0);
                       	               
                       	        }
                           }
                   }
                   else if(Command=="load_Voucher_Details")
                   {
                	   	   if(flag=="success")
                	   	   {
                	   		   
                	   		   	   var count=baseResponse.getElementsByTagName("acct_unit");   
                	   		   	   var acct_unit=baseResponse.getElementsByTagName("acct_unit")[0].firstChild.nodeValue;				       	                       
    	   		   		   	       var reason=baseResponse.getElementsByTagName("reason")[0].firstChild.nodeValue;
    	   		   		   	       var particulars=baseResponse.getElementsByTagName("particulars")[0].firstChild.nodeValue;
    	   		   		   	      
    	   		   		   	       document.getElementById("TransferedID").value=acct_unit;
    	   		   		   	 document.getElementById("cmbSL_Code").value=acct_unit;
    	   		   		   	       document.getElementById("Reason4Trf").value=reason;
    	   		   		   	       document.getElementById("GenParticulars").value=particulars;
    	   		   		   	       
    	   		   		   	       
    	   		   		   document.getElementById("txtCB_Year").value=baseResponse.getElementsByTagName("cashyear")[0].firstChild.nodeValue;;	
    	   		 		document.getElementById("txtCB_Month").value=baseResponse.getElementsByTagName("cashmonth")[0].firstChild.nodeValue;;	
    	   		   		   	       
    	   		   		   	       
    	   		   		   	reasonTransfer=reason;
                	   		   	   for(var k=0;k<count.length;k++)
                                   {
                	   		   		   	    item1[k]=baseResponse.getElementsByTagName("account_head_code")[k].firstChild.nodeValue;
                	   		   		   	    item2[k]=baseResponse.getElementsByTagName("account_head_desc")[k].firstChild.nodeValue;
                	   		   		   	    item3[k]=baseResponse.getElementsByTagName("sub_ledger_type_code")[k].firstChild.nodeValue;		                	   		   		
                	   		   		   	    item4[k]=baseResponse.getElementsByTagName("sub_ledger_type_desc")[k].firstChild.nodeValue;
                	   		   		   	    if(item4[k]=="null")
                	   		   		   	    		item4[k]="";
                	   		   		   	    item5[k]=baseResponse.getElementsByTagName("sub_ledger_code")[k].firstChild.nodeValue;
                	   		   		   	    item6[k]=baseResponse.getElementsByTagName("sub_ledger_desc")[k].firstChild.nodeValue;
                	   		   		   	    if(item6[k]=="--")
                	   		   		   	    		item6[k]="";
                	   		   		   	    item7[k]=baseResponse.getElementsByTagName("cr_dr")[k].firstChild.nodeValue;
                	   		   		   	    item8[k]=baseResponse.getElementsByTagName("amount")[k].firstChild.nodeValue;
                	   		   		   	    item9[k]=baseResponse.getElementsByTagName("trn_particulars")[k].firstChild.nodeValue;
                	   		   		   	    if(item9[k]=="null")
                	   		   		   	    		item9[k]="";
                	   		   		  item10[k]=baseResponse.getElementsByTagName("slno")[k].firstChild.nodeValue;
                	   		   		  
                	   		   	item11[k]=baseResponse.getElementsByTagName("minorheadcode")[k].firstChild.nodeValue;
                	   		   		   	    
                                   }
                	   		   	   
                	   		    var count1=baseResponse.getElementsByTagName("g_acct_unit");      
                	   		   	for(var k=0;k<count1.length;k++)
                                {
             	   		   		   	    g_item1[k]=baseResponse.getElementsByTagName("g_account_head_code")[k].firstChild.nodeValue;
             	   		   		   	    g_item2[k]=baseResponse.getElementsByTagName("g_account_head_desc")[k].firstChild.nodeValue;
             	   		   		   	    g_item3[k]=baseResponse.getElementsByTagName("g_sub_ledger_type_code")[k].firstChild.nodeValue;		                	   		   		
             	   		   		   	    g_item4[k]=baseResponse.getElementsByTagName("g_sub_ledger_type_desc")[k].firstChild.nodeValue;
             	   		   		   	    if(g_item4[k]=="null")
             	   		   		   	    	g_item4[k]="";
             	   		   		   	    g_item5[k]=baseResponse.getElementsByTagName("g_sub_ledger_code")[k].firstChild.nodeValue;
             	   		   		   	    g_item6[k]=baseResponse.getElementsByTagName("g_sub_ledger_desc")[k].firstChild.nodeValue;
             	   		   		   	    if(g_item6[k]=="--")
             	   		   		   	    	g_item6[k]="";
             	   		   		   	    g_item7[k]=baseResponse.getElementsByTagName("g_cr_dr")[k].firstChild.nodeValue;
             	   		   		   	    g_item8[k]=baseResponse.getElementsByTagName("g_amount")[k].firstChild.nodeValue;
             	   		   		   	    g_item9[k]=baseResponse.getElementsByTagName("g_trn_particulars")[k].firstChild.nodeValue;
             	   		   		   	    if(g_item9[k]=="null")
             	   		   		   	    	g_item9[k]="";
             	   		   		 g_item10[k]=baseResponse.getElementsByTagName("g_slno")[k].firstChild.nodeValue;	
             	   		   		 
             	   		   	g_item11[k]=baseResponse.getElementsByTagName("g_minorheadcode")[k].firstChild.nodeValue;
                                }
                	   		   	   
                	   		   	   
                	   		   	   
                	   		   	   loadGrid();
                	   		   	loadGrid1();
                	   			document.getElementById("total").style.display='block'; 
                   		   		document.getElementById("total1").style.display='block'; 
                	   	   }
                	   	   
                   } if(Command=="loadGlSlGrid")
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
	                	   
	                	   var tbody1=document.getElementById("gl_grid_body");
	                	    var t=0;
	                	    for(t=tbody1.rows.length-1;t>=0;t--)
	                	    {
	                	       tbody1.deleteRow(0);
	                	    }
	                	   
	                	   tbody1=document.getElementById("gl_grid_body");
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
	                   }else 
	                   {
	                	   var tbody=document.getElementById("grid_body");
	                	    var t=0;
	                	    for(t=tbody.rows.length-1;t>=0;t--)
	                	    {
	                	       tbody.deleteRow(0);
	                	    }
	                	    var tbody1=document.getElementById("gl_grid_body");
	                	    var t=0;
	                	    for(t=tbody1.rows.length-1;t>=0;t--)
	                	    {
	                	       tbody1.deleteRow(0);
	                	    }
	                	   
	                	    document.getElementById("Amount").value="";   
	                	    alert('No data Found');
	                   }
	                   
	            }
            }
		}    
}

function loadGrid()
{
		tbody=document.getElementById("grid_body");
		//clearall();
		
		        
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
                tbody.deleteRow(0);
               
        }
		
		
		for(var i=0;i<item1.length;i++)
		{
			  if((item1[i]==620101 || item1[i]==900301) && item10[i]==1 )
			  {
				 
				  document.getElementById("txtAcc_HeadCode").value=item1[i]; 
				
				
		   	      document.getElementById("Amount").value=item8[i];
		   	      document.getElementById("DetParticular").value=item9[i];
		   	    //  setTimeout(document.getElementById("cmbSL_Code").value=item5[i],200); 
			  }
			  else
			  {
				  
				  if(reasonTransfer=="Others")
				  {
					  if(item11[i]!="nominorhead")
				  minorhead=item11[i];
				  
				  }
				  
				  
				  var mycurrent_row=document.createElement("TR");                
	  	          mycurrent_row.id=seq;
	
	  	          cell2=document.createElement("TD");       
	  	          var item=document.createElement("input");
	  	          item.type="hidden";
	  	          item.name="H_code";
	  	          item.value=item1[i];
	  	          cell2.appendChild(item);
	  	          var currentText=document.createTextNode(item1[i]+"-"+item2[i]);
	  	          cell2.appendChild(currentText);
	  	          mycurrent_row.appendChild(cell2);
	  	          
	  	          cell2=document.createElement("TD"); 
	  	          var item=document.createElement("input");
	  	          item.type="hidden";
	  	          item.name="SL_type";
	  	          item.value=item3[i];
	  	          cell2.appendChild(item);
	  	          var currentText=document.createTextNode(item4[i]);
	  	          cell2.appendChild(currentText);
	  	          mycurrent_row.appendChild(cell2);
	  	          
	  	          cell2=document.createElement("TD"); 
	  	          var item=document.createElement("input");
	  	          item.type="hidden";
	  	          item.name="SL_code";
	  	          item.value=item5[i];
	  	          cell2.appendChild(item);		              	          
	  	          var currentText=document.createTextNode(item6[i]);
	  	          cell2.appendChild(currentText);
	  	          mycurrent_row.appendChild(cell2);
	  	          
	  	          cell2=document.createElement("TD"); 
	  	          var item=document.createElement("input");
	  	          item.type="hidden";
	  	          item.name="cr_dr";
	  	          item.value=item7[i];
	  	          cell2.appendChild(item);		              	          
	  	          var currentText=document.createTextNode(item7[i]);
	  	          cell2.appendChild(currentText);
	  	          mycurrent_row.appendChild(cell2);
	  	               
	  	          cell2=document.createElement("TD"); 
	  	          var item=document.createElement("input");
	  	          item.type="text";
	  	          item.name="amount";
	  	          item.value=item8[i];
	  	          cell2.appendChild(item);		              	         
	  	          mycurrent_row.appendChild(cell2);
	  	          
	  	          cell2=document.createElement("TD");
	  	          var item=document.createElement("input");
	  	          item.type="text";
	  	          item.name="particular";
	  	          item.value=item9[i];
	  	          cell2.appendChild(item);
	  	          mycurrent_row.appendChild(cell2);
	  	         
	  	          tbody.appendChild(mycurrent_row);
	  	          //clearall();
	  	          /** Increment Sequence Number */ 
	  	          seq=seq+1;
			  }
		}
		
		 if(reasonTransfer=="Others"){
			 document.getElementById("tohide").style.display='block';
			 
			 document.getElementById("transfercategory").value=minorhead;
			 
			 }else{
				 document.getElementById("tohide").style.display='none'; 
			 }
		
}

var minorhead="nohead";

function loadGrid1()
{
		tbody=document.getElementById("gl_grid_body");
		
		
		  var t=0;
	        for(t=tbody.rows.length-1;t>=0;t--)
	        {
	                tbody.deleteRow(0);
	               
	        }
		
		//clearall();
		for(var i=0;i<g_item1.length;i++)
		{
			  if((g_item1[i]==620101 || g_item1[i]==900301) && g_item10[i]==1)
			  {
				  
				  document.getElementById("txtAcc_HeadCode").value=g_item1[i]; 
				 
				  
				 
				
		   	      document.getElementById("Amount").value=g_item8[i];
		   	      document.getElementById("DetParticular").value=g_item9[i];
		   	    //  setTimeout(document.getElementById("cmbSL_Code").value=g_item5[i],200); 
			  }
			  else
			  {
			
				  if(reasonTransfer=="Others")
				  {
					  if(g_item11[i]!="nominorhead")
				  minorhead=g_item11[i];
				  
				  }
				  
				  var mycurrent_row=document.createElement("TR");                
	  	          mycurrent_row.id=seq;
	
	  	          cell2=document.createElement("TD");       
	  	          var item=document.createElement("input");
	  	          item.type="hidden";
	  	          item.name="H_code";
	  	          item.value=g_item1[i];
	  	          cell2.appendChild(item);
	  	          var currentText=document.createTextNode(g_item1[i]+"-"+g_item2[i]);
	  	          cell2.appendChild(currentText);
	  	          mycurrent_row.appendChild(cell2);
	  	          
	  	          cell2=document.createElement("TD"); 
	  	          var item=document.createElement("input");
	  	          item.type="hidden";
	  	          item.name="SL_type";
	  	          item.value=g_item3[i];
	  	          cell2.appendChild(item);
	  	          var currentText=document.createTextNode(g_item4[i]);
	  	          cell2.appendChild(currentText);
	  	          mycurrent_row.appendChild(cell2);
	  	          
	  	          cell2=document.createElement("TD"); 
	  	          var item=document.createElement("input");
	  	          item.type="hidden";
	  	          item.name="SL_code";
	  	          item.value=g_item5[i];
	  	          cell2.appendChild(item);		              	          
	  	          var currentText=document.createTextNode(g_item6[i]);
	  	          cell2.appendChild(currentText);
	  	          mycurrent_row.appendChild(cell2);
	  	          
	  	          cell2=document.createElement("TD"); 
	  	          var item=document.createElement("input");
	  	          item.type="hidden";
	  	          item.name="cr_dr";
	  	          item.value=g_item7[i];
	  	          cell2.appendChild(item);		              	          
	  	          var currentText=document.createTextNode(g_item7[i]);
	  	          cell2.appendChild(currentText);
	  	          mycurrent_row.appendChild(cell2);
	  	               
	  	          cell2=document.createElement("TD"); 
	  	          var item=document.createElement("input");
	  	          item.type="text";
	  	          item.name="amount";
	  	          item.value=g_item8[i];
	  	          cell2.appendChild(item);		              	         
	  	          mycurrent_row.appendChild(cell2);
	  	          
	  	          cell2=document.createElement("TD");
	  	          var item=document.createElement("input");
	  	          item.type="text";
	  	          item.name="particular";
	  	          item.value=g_item9[i];
	  	          cell2.appendChild(item);
	  	          mycurrent_row.appendChild(cell2);
	  	         
	  	          tbody.appendChild(mycurrent_row);
	  	          //clearall();
	  	          /** Increment Sequence Number */ 
	  	          seq=seq+1;
			  }
		}
		 if(reasonTransfer=="Others"){
		 document.getElementById("tohide").style.display='block';
		 
		 document.getElementById("transfercategory").value=minorhead;
		 
		 }else{
			 document.getElementById("tohide").style.display='none'; 
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
	    	clrForm('load');
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
	    	call_clr();
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
	    	//document.getElementById("txtReceipt_No").value="";
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
                    alert("Trial Balance Closed");//return false;//
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
var crdrcheck=0;
function loadGlSlGrid()
{		
	var crdrindicator;
			
				if(document.frm_TPA_Raised_Edit.Org_CR_DR[0].checked==true)
				{
					crdrindicator='CR';	
				}else{
					crdrindicator='DR';
				}
			
	
			
			//crdrindicator=document.getElementById("indicrdr").value;
	
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
	    req.onreadystatechange=function()
	    {
	    	TPA_Raised_ServletResponse(req);
	    }   
	    req.send(null);
}















function loadAccountHead()
{
		
		if(document.frm_TPA_Raised_Edit.Org_CR_DR[0].checked==true)
		{
			document.frm_TPA_Raised_Edit.txtAcc_HeadCode.value=620101;
		document.frm_TPA_Raised_Edit.txtAcc_HeadDesc.value="TRANSFER PROFORMA CREDIT   -TRANSFER CREDIT";
		}
		else{
			document.frm_TPA_Raised_Edit.txtAcc_HeadCode.value=900301;
		document.frm_TPA_Raised_Edit.txtAcc_HeadDesc.value="TRANSFER PROFORMA DEBIT A/C   -T.P. DEBIT A/C";
		}
		//doFunction('checkCode','null');
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

function checkNull()
{        
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
             /*   var dr_check_amt=0;var cr_check_amt=0;var count=0;
                rows=tbody.getElementsByTagName("TR");       
                for(i=0;i<rows.length;i++)
                {                		
                        var cells=rows[i].cells;                                              
                        if(cells.item(3).firstChild.value=='CR')
                        {
                                cr_check_amt=parseFloat(cr_check_amt) + parseFloat(cells.item(4).firstChild.value);
                                                                   	                	
                        } 
                        else if(cells.item(3).firstChild.value=='DR')
                        {
                                dr_check_amt=parseFloat(dr_check_amt) + parseFloat(cells.item(4).firstChild.value);                                 	                	
                        } 
                }  
               // alert(-(cr_check_amt-dr_check_amt));
                if(-(cr_check_amt-dr_check_amt)!=document.getElementById("Amount").value)
                {
                        alert("Total Amount of Grid & TPA Amount should be equal");
                        return false; 
                }
                else
                		return true;
                */
        	 return true;
         }       
       
       		
}




function clrForm(param)
{				
		
		if(param=="cancel")
		{
			    if(window.confirm("Do you want to clear ALL fields ?"))
				{
			                call_clr();
				}
		}
		else
				call_clr();
}

function call_clr()
{
		document.frm_TPA_Raised_Edit.Org_CR_DR[0].checked=false;
		document.frm_TPA_Raised_Edit.Org_CR_DR[1].checked=false;
		document.getElementById("adviceNumber").length=1;
		document.getElementById("TransferedID").value="";        
	    document.getElementById("Reason4Trf").value="";       
		document.getElementById("GenParticulars").value="";
		document.getElementById("txtAcc_HeadCode").value="";		
		document.getElementById("txtAcc_HeadDesc").value="";		
		document.getElementById("cmbSL_type").value="";
	    document.getElementById("cmbSL_Code").value="";
		document.getElementById("txtOfficeID_trs").value="";
		document.getElementById("txtEmpID_trs").value="";
		document.getElementById("Amount").value="";
	    document.getElementById("DetParticular").value="";	   
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
                tbody.deleteRow(0);
        }
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
             dr_check_amt1=parseFloat(dr_check_amt1) + parseFloat(cells.item(4).firstChild.value);                                 	                	
           
     }
	 
	 var tbody=document.getElementById("gl_grid_body");
		rows=tbody.getElementsByTagName("TR");  
		
		 for(i=0;i<rows.length;i++)
	     {                		
		      var cells=rows[i].cells;                                              
	           
	          dr_check_amt2=parseFloat(dr_check_amt2) + parseFloat(cells.item(4).firstChild.value);  
	                 
	      
	     }
		// alert(parseFloat(dr_check_amt1));
		// alert(parseFloat(dr_check_amt2)); 
		
		 document.getElementById("Amount").value=parseFloat(dr_check_amt1)+ parseFloat(dr_check_amt2) ;	  
}



function loadtranfercategory()
{         
	if( document.getElementById("Reason4Trf").value=="Others" && (document.getElementById("Reason4Trf").value!="")){
	 document.getElementById("tohide").style.display='block';
	// loadGlSlGrid();    
	}else{
		 document.getElementById("tohide").style.display='none';
	}
}

