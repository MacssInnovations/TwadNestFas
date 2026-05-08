
var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;
var job_flag;
var common_AHead_code_flag="";
var emp_flag;
var item1=new Array();var item2=new Array();var item3=new Array();var item4=new Array();
var item5=new Array();var item6=new Array();var item7=new Array();var item8=new Array();
var item9=new Array();
var item10=new Array();

var g_item1=new Array();var g_item2=new Array();var g_item3=new Array();var g_item4=new Array();
var g_item5=new Array();var g_item6=new Array();var g_item7=new Array();var g_item8=new Array();
var g_item9=new Array();
var g_item10=new Array();
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
		if(document.frm_TPA_Raised_Cancel.Org_CR_DR[0].checked==true)
		    tpa_type="TPAOC";
		else
			tpa_type="TPAOD";
		doFunction('checkCode','null');  
		if(command=="load_Voucher_No") 
		{
			url="../../../../../TPA_Raised_Edit?command=load_Voucher_No&Option=Cancel&txtUnitId="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date+"&tpa_type="+tpa_type;
			//alert(url);
		}
		else 
		{
			var adviceNumber=document.getElementById("adviceNumber").value;
			url="../../../../../TPA_Raised_Edit?command=load_Voucher_Details&txtUnitId="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date+"&tpa_type="+tpa_type+"&adviceNumber="+adviceNumber;           			  
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
                   // alert(flag);
                    if(Command=="loadTransferUnit")
                    {                                       
                           if(flag=="success")
                           {                                      
                                   var txtUnitId=document.getElementById("TransferedID");  
                                   var child=txtUnitId.childNodes;
                                   for(var i=child.length-1;i>1;i--)
                                   {
                                	   		txtUnitId.removeChild(child[i]);
                                   }                                              
                                   var items_id=new Array();
                                   var items_name=new Array();                                    
                                   var oid=baseResponse.getElementsByTagName("unit_id");
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
                           }
                           else
                           {                                                   
                                   document.getElementById("txtUnitId").value="";
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
    	   		   		   	       
    	   		   		   	       if(baseResponse.getElementsByTagName("particulars")[0].firstChild == null){
												 var particulars="";
											}
											else{
												 var particulars=baseResponse.getElementsByTagName("particulars")[0].firstChild.nodeValue;
											}
    	   		   		   	       //var particulars=baseResponse.getElementsByTagName("particulars")[0].firstChild.nodeValue;
    	   		   		   	      
    	   		   		   	       document.getElementById("TransferedID").value=acct_unit;
    	   		   		   	       document.getElementById("Reason4Trf").value=reason;
    	   		   		   	       document.getElementById("GenParticulars").value=particulars;
    	   		   		   	      
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
                	   		   		   	    if(baseResponse.getElementsByTagName("trn_particulars")[k].firstChild == null){
														 item9[k]="";
													 }
													 else
													 {
														 item9[k]=baseResponse.getElementsByTagName("trn_particulars")[k].firstChild.nodeValue;
													 }
                	   		   		   	 //   item9[k]=baseResponse.getElementsByTagName("trn_particulars")[k].firstChild.nodeValue;
                	   		   		   	    if(item9[k]=="null")
                	   		   		   	    		item9[k]="";
                	   		   		  item10[k]=baseResponse.getElementsByTagName("slno")[k].firstChild.nodeValue;	    
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
                                }
                	   		   		loadGrid();
                	   		   	   loadGrid1();
                	   		   	
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
				  document.getElementById("txtOfficeID_trs").value=item5[i];   
				  doFunction('Load_SL_Code',item3[i]);
				  document.getElementById("cmbSL_type").value=item3[i];
				  if(item3[i]==5)
			      {         
			      document.getElementById("txtOfficeID_trs").value=item5[i];     
			      document.getElementById("txtOfficeID_mas").value=item5[i];
			      trs_office(item5[i]);
			                job_flag=false;             
			      }
			      if(item3[i]==7)
			      {
			       
			                document.getElementById("txtEmpID_trs").value=item5[i];
			                emp_flag=false;            
			      } 
		   	      /*if(item7[i]=="CR")
		   	    	  document.frm_TPA_Raised_Edit.Indi_CR_DR[0].checked=true;
		   	      else
		   	    	  document.frm_TPA_Raised_Edit.Indi_CR_DR[1].checked=true;*/
		   	      document.getElementById("Amount").value=item8[i];
		   	      document.getElementById("DetParticular").value=item9[i];
		   	      setTimeout(document.getElementById("cmbSL_Code").value=item5[i],200); 
			  }
			  else
			  {
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
	  	          item.type="hidden";
	  	          item.name="amount";
	  	          
	  	          item.value=item8[i];
	  	          cell2.appendChild(item);	
	  	        var currentText=document.createTextNode(item8[i]);
	  	          cell2.appendChild(currentText);
	  	          mycurrent_row.appendChild(cell2);
	  	          
	  	          cell2=document.createElement("TD");
	  	          var item=document.createElement("input");
	  	          item.type="hidden";
	  	        
	  	          item.name="particular";
	  	          item.value=item9[i];
	  	          cell2.appendChild(item);
	  	        var currentText=document.createTextNode(item9[i]);
	  	          cell2.appendChild(currentText);
	  	          mycurrent_row.appendChild(cell2);
	  	         
	  	          tbody.appendChild(mycurrent_row);
	  	          //clearall();
	  	          /** Increment Sequence Number */ 
	  	          seq=seq+1;
			  }
		}
}




function loadGrid1()
{
		tbody=document.getElementById("gl_grid_body");
		//clearall();
		  var t=0;
	        for(t=tbody.rows.length-1;t>=0;t--)
	        {
	                tbody.deleteRow(0);
	               
	        }
		
		for(var i=0;i<g_item1.length;i++)
		{
			 if((g_item1[i]==620101 || g_item1[i]==900301) && g_item10[i]==1)
			  {
				  document.getElementById("txtOfficeID_trs").value=g_item5[i]; 
				  if(g_item3[i]!=0)
				  doFunction('Load_SL_Code',g_item3[i]);
				  document.getElementById("cmbSL_type").value=g_item3[i];
				  if(g_item3[i]==5)
			      {         
			      document.getElementById("txtOfficeID_trs").value=g_item5[i];     
			      document.getElementById("txtOfficeID_mas").value=g_item5[i];
			      trs_office(g_item5[i]);
			                job_flag=false;             
			      }
			      if(g_item3[i]==7)
			      {
			       
			                document.getElementById("txtEmpID_trs").value=g_item5[i];
			                emp_flag=false;            
			      } 
		   	      /*if(item7[i]=="CR")
		   	    	  document.frm_TPA_Raised_Edit.Indi_CR_DR[0].checked=true;
		   	      else
		   	    	  document.frm_TPA_Raised_Edit.Indi_CR_DR[1].checked=true;*/
		   	      document.getElementById("Amount").value=g_item8[i];
		   	      document.getElementById("DetParticular").value=g_item9[i];
		   	      setTimeout(document.getElementById("cmbSL_Code").value=g_item5[i],200); 
			  }
			  else
			  {
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
	  	          item.type="hidden";
	  	          item.name="amount";
	  	      
	  	          item.value=g_item8[i];
	  	          cell2.appendChild(item);
	  	        var currentText=document.createTextNode(g_item8[i]);
	  	          cell2.appendChild(currentText);
	  	          mycurrent_row.appendChild(cell2);
	  	          
	  	          cell2=document.createElement("TD");
	  	          var item=document.createElement("input");
	  	          item.type="hidden";
	  	       
	  	          item.name="particular";
	  	          item.value=g_item9[i];
	  	          cell2.appendChild(item);
	  	        var currentText=document.createTextNode(g_item9[i]);
	  	          cell2.appendChild(currentText);
	  	          mycurrent_row.appendChild(cell2);
	  	         
	  	          tbody.appendChild(mycurrent_row);
	  	          //clearall();
	  	          /** Increment Sequence Number */ 
	  	          seq=seq+1;
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

function loadGlSlGrid()
{
		var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
		var cmbOffice_code=document.getElementById("cmbOffice_code").value;	
		var url="../../../../../TPA_Raised_Create?command=loadGlSlGrid&txtUnitId="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
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
		
		if(document.frm_TPA_Raised_Cancel.Org_CR_DR[0].checked==true)
			document.frm_TPA_Raised_Cancel.txtAcc_HeadCode.value=620101;
		else
			document.frm_TPA_Raised_Cancel.txtAcc_HeadCode.value=900301;
		doFunction('checkCode','null');
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
        if(document.getElementById("Voucher_Date").value.length==0)
        {
	            alert("Enter the date of creation");           
	            return false;    
        }
        if(document.getElementById("adviceNumber").value=="")
        {
	            alert("Select TPA Advice Number");           
	            return false;    
        }
        else
        		return true;
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
		document.frm_TPA_Raised_Cancel.Org_CR_DR[0].checked=false;
		document.frm_TPA_Raised_Cancel.Org_CR_DR[1].checked=false;
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
alert('i am here');	
}
