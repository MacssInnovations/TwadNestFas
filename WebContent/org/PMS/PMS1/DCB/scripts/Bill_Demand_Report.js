var process_code=0;

function demand_show(command,process,input_value)
 {
	var url=""; 
	process_code=process;
	if (command=="pdf")
	{ 
		url="../../../../../Bill_Demand_Report?command="+command+"&input_value="+document.getElementById("dmdlist").value+"&process_code="+process_code;
		 
 		window.open(url);
 		
	}
	else
	{
	 url="../../../../../Bill_Demand_Report?command=show&input_value="+input_value+"&process_code="+process;
	 document.getElementById("pr_status").value=0;; 
	  var demandxmlobj=createObject();
	  demandxmlobj.open("GET",url,true);
	  demandxmlobj.onreadystatechange=function()
      {
		  demand_result_process(demandxmlobj,command);
		  
      }
	  demandxmlobj.send(null);
	}
 }
function demand_result_process(demandxmlobj,command)
{
	 
	var demandxmlobj=demandxmlobj;
	if (demandxmlobj.readyState==4)
	 {	   
	 	 
		if (demandxmlobj.status==200)
	    {
			if (process_code==1)
			{
				document.getElementById("pr_status").value=1;
				demand_result_display(demandxmlobj,command)
				 
				
			}
					
			
	    }
	 }
	
}

function demand_result_display(demandxmlobj,command)
{	 
	var bR=demandxmlobj.responseXML.getElementsByTagName("result");
	 
   	var BILL_PERIOD_FROM=demandxmlobj.responseXML.getElementsByTagName("BILL_PERIOD_FROM")[0].firstChild.nodeValue;;
 	var BILL_PERIOD_TO=demandxmlobj.responseXML.getElementsByTagName("BILL_PERIOD_TO")[0].firstChild.nodeValue;;
	var BILL_MONTH=demandxmlobj.responseXML.getElementsByTagName("BILL_MONTH")[0].firstChild.nodeValue;;
	var BILL_YEAR=demandxmlobj.responseXML.getElementsByTagName("BILL_YEAR")[0].firstChild.nodeValue;;
	
	var DIV_BILL_NO=demandxmlobj.responseXML.getElementsByTagName("DIV_BILL_NO")[0].firstChild.nodeValue;;
	var NET_CONSUMPTION=demandxmlobj.responseXML.getElementsByTagName("NET_CONSUMPTION")[0].firstChild.nodeValue;;
 
	var BILLING_DT=demandxmlobj.responseXML.getElementsByTagName("BILLING_DT")[0].firstChild.nodeValue;;  
 	var BENEFICIARY_NAME=demandxmlobj.responseXML.getElementsByTagName("BENEFICIARY_NAME")[0].firstChild.nodeValue;;
 	var BILLING_ADDRESS1=demandxmlobj.responseXML.getElementsByTagName("BILLING_ADDRESS1")[0].firstChild.nodeValue;;
	var BILLING_ADDRESS2=demandxmlobj.responseXML.getElementsByTagName("BILLING_ADDRESS2")[0].firstChild.nodeValue;;
	var BILLING_ADDRESS3=demandxmlobj.responseXML.getElementsByTagName("BILLING_ADDRESS3")[0].firstChild.nodeValue;;
	var BILLING_PIN_CODE=demandxmlobj.responseXML.getElementsByTagName("BILLING_PIN_CODE")[0].firstChild.nodeValue;; 
 	var OFFICE_NAME=demandxmlobj.responseXML.getElementsByTagName("OFFICE_NAME")[0].firstChild.nodeValue;;
 	var net_consumption=demandxmlobj.responseXML.getElementsByTagName("net_consumption")[0].firstChild.nodeValue;;
 	var net_consumption_value=demandxmlobj.responseXML.getElementsByTagName("net_consumption_value")[0].firstChild.nodeValue;;
 	var INT_RATE =demandxmlobj.responseXML.getElementsByTagName("INT_RATE")[0].firstChild.nodeValue;;
 	var WC_OB=demandxmlobj.responseXML.getElementsByTagName("WC_OB")[0].firstChild.nodeValue;;
 	var INT_CALC=demandxmlobj.responseXML.getElementsByTagName("INT_CALC")[0].firstChild.nodeValue;;
 	var SCH_TYPE_DESC=demandxmlobj.responseXML.getElementsByTagName("SCH_TYPE_DESC")[0].firstChild.nodeValue;;
 	var SCH_NAME=demandxmlobj.responseXML.getElementsByTagName("SCH_NAME")[0].firstChild.nodeValue;;
 	var BILL_MONTH=demandxmlobj.responseXML.getElementsByTagName("BILL_MONTH")[0].firstChild.nodeValue;;
 	var BILL_YEAR=demandxmlobj.responseXML.getElementsByTagName("BILL_YEAR")[0].firstChild.nodeValue;;
 	var MAINT_COLN=demandxmlobj.responseXML.getElementsByTagName("MAINT_COLN")[0].firstChild.nodeValue;;
 	var WC_COLN=demandxmlobj.responseXML.getElementsByTagName("WC_COLN")[0].firstChild.nodeValue;;
 	var MAINT_OB=demandxmlobj.responseXML.getElementsByTagName("MAINT_OB")[0].firstChild.nodeValue;;
  	document.getElementById("scheme_name_td").innerHTML=SCH_TYPE_DESC+"&nbsp;&<Br>"+SCH_NAME;
  	
  	 if (parseInt(BILL_MONTH)<=9)
  		BILL_MONTH="0"+BILL_MONTH;
  	 
  	 
  	document.getElementById("divaddr-label").innerHTML=OFFICE_NAME;
  	document.getElementById("divaddr-label_bottom").innerHTML=OFFICE_NAME;
  	
 	var ben_address="To <bR>"+BENEFICIARY_NAME+","+"<br>"+BILLING_ADDRESS1+","+BILLING_ADDRESS2+","+BILLING_ADDRESS3+"<br> Pin Code - "+BILLING_PIN_CODE;
 	document.getElementById("ben-label").innerHTML=ben_address;
 	document.getElementById("billno").innerHTML=BILL_MONTH+""+BILL_YEAR+""+DIV_BILL_NO+"";
 	document.getElementById("billdate").innerHTML=BILLING_DT;
 	document.getElementById("billnofrom").innerHTML=BILL_PERIOD_FROM;
 	document.getElementById("billnoto").innerHTML=BILL_PERIOD_TO;
 	document.getElementById("total_consumption_td").innerHTML=net_consumption;
 	document.getElementById("total_cross_watercharges_td").innerHTML=net_consumption_value;
 	document.getElementById("pen_rate").innerHTML=INT_RATE;
 	document.getElementById("we_ob").innerHTML=WC_OB;
 	document.getElementById("total_penalty_td").innerHTML=INT_CALC;
 	document.getElementById("monthlabel").innerHTML=BILL_MONTH+" / "+BILL_YEAR+"";
 	document.getElementById("total_wc_amount").innerHTML=parseFloat(net_consumption_value)+parseFloat(INT_CALC);
 	var rs_value=chequeAmount(parseFloat(net_consumption_value)+parseFloat(INT_CALC));
 	document.getElementById("amount_this").innerHTML=parseFloat(net_consumption_value)+parseFloat(INT_CALC);
 
  	document.getElementById("bill_value_word").innerHTML=rs_value;
 	document.getElementById("wc_cb_ob").innerHTML=WC_OB;  
 	document.getElementById("main_cb_ob").innerHTML=MAINT_OB;
 	//document.getElementById("pen_cb_ob").innerHTML="0.00";
 	
 	 
 	
 	document.getElementById("wc_cb_rec").innerHTML=WC_COLN;  
 	document.getElementById("main_cb_rec").innerHTML=MAINT_COLN;
 	//document.getElementById("pen_cb_ob").innerHTML="0.00";
 	
 	document.getElementById("wc_cb_bal").innerHTML=parseFloat(WC_OB)-parseFloat(WC_COLN);
 	
 	document.getElementById("main_cb_bal").innerHTML=parseFloat(MAINT_OB)-parseFloat(MAINT_COLN);
 	document.getElementById("total_amount_due").innerHTML=(parseFloat(net_consumption_value)+parseFloat(INT_CALC))+(parseFloat(MAINT_OB)-parseFloat(MAINT_COLN)); 
 	
 	document.getElementById("tot_wc_cb_ob").innerHTML=parseFloat(WC_OB)+parseFloat(MAINT_OB);  
 	document.getElementById("tot_wc_cb_rec").innerHTML=parseFloat(WC_COLN)+parseFloat(MAINT_COLN);  
 	document.getElementById("tot_wc_cb_bal").innerHTML=(parseFloat(WC_OB)-parseFloat(WC_COLN))+(parseFloat(MAINT_OB)-parseFloat(MAINT_COLN));  
 	
	
 	  
 	
 	
 	
 	var  BENEFICIARY_TYPE_ID=demandxmlobj.responseXML.getElementsByTagName("BENEFICIARY_TYPE_ID")[0].firstChild.nodeValue;;
  
 	
 	if (parseInt(BENEFICIARY_TYPE_ID) > 6 )
 		{
 		document.getElementById("rate_type").innerHTML="Multi Factor";
 		}
 	else
 		{
 		document.getElementById("rate_type").innerHTML="Billing Rate";
 		}

 	
	 
 	
 	
 	
     var tbody = document.getElementById("demand_meter_body");
 	 var table = document.getElementById("demand_meter_table");
 	 
 
 	
 	 var t=0;
 	var len=demandxmlobj.responseXML.getElementsByTagName("bill_sno").length;
  	for(t=tbody.rows.length-1;t>=0;t--){tbody.deleteRow(0);}
  	for (var i=0;i<len;i++)
 	{ 
 		  var new_row=cell("TR","","","row1",1,2,"","","","","","","");//13
 		  var METRE_LOCATION=demandxmlobj.responseXML.getElementsByTagName("METRE_LOCATION")[i].firstChild.nodeValue;
 		
 		  var METRE_INITIAL_READING =demandxmlobj.responseXML.getElementsByTagName("METRE_INITIAL_READING")[i].firstChild.nodeValue;
 		  var METRE_CLOSING_READING	=demandxmlobj.responseXML.getElementsByTagName("METRE_CLOSING_READING")[i].firstChild.nodeValue;
 		
 		  var MIN_BILL_QTY=demandxmlobj.responseXML.getElementsByTagName("MIN_BILL_QTY")[i].firstChild.nodeValue;
 		  var ALLOTED_QTY=demandxmlobj.responseXML.getElementsByTagName("ALLOTED_QTY")[i].firstChild.nodeValue;
 		  var EXCESS_AMT=demandxmlobj.responseXML.getElementsByTagName("EXCESS_AMT")[i].firstChild.nodeValue;
 		  var EXCESS_QTY=demandxmlobj.responseXML.getElementsByTagName("EXCESS_QTY")[i].firstChild.nodeValue;
 		 
 		  var MULTIPLY_FACTOR 	=demandxmlobj.responseXML.getElementsByTagName("MULTIPLY_FACTOR")[i].firstChild.nodeValue;
 		  var TARIFF_RATE 	=demandxmlobj.responseXML.getElementsByTagName("TARIFF_RATE")[i].firstChild.nodeValue;
 		  var EXCESS_TARIFF_RATE=demandxmlobj.responseXML.getElementsByTagName("EXCESS_TARIFF_RATE")[i].firstChild.nodeValue;
 		  var QTY_CONSUMED=demandxmlobj.responseXML.getElementsByTagName("QTY_CONSUMED")[i].firstChild.nodeValue;
 		  var total_consumption=demandxmlobj.responseXML.getElementsByTagName("total_consumption")[i].firstChild.nodeValue;
 		  var total_vlaue=demandxmlobj.responseXML.getElementsByTagName("total_vlaue")[i].firstChild.nodeValue;
 		  
 		  var METRE_LOCATION_cell=cell("TD","label","","",METRE_LOCATION,7,"","","font-size:14px;","","left","","");
 		  var METRE_INITIAL_READING_cell=cell("TD","label","","",METRE_INITIAL_READING,7,"","","font-size:14px;","","right","","");
 		  var METRE_CLOSING_READING_cell=cell("TD","label","","",METRE_CLOSING_READING,7,"","","font-size:14px;","","right","","");
 		  var QTY_CONSUMED_cell=cell("TD","label","","",QTY_CONSUMED,7,"","","font-size:14px;","","right","","");
  		  var total_consumption_cell =cell("TD","label","","",total_consumption,7,"","","font-size:14px;","","right","","");
  		  var total_vlaue_cell =cell("TD","label","","",total_vlaue,7,"","","font-size:14px;","","right","","");
  		  var MIN_BILL_QTY_cell=cell("TD","label","","",MIN_BILL_QTY,7,"","","font-size:14px;","","right","","");  
  		  var ALLOTED_QTY_cell =cell("TD","label","","",ALLOTED_QTY,7,"","","font-size:14px;","","right","","");
  		  var EXCESS_TARIFF_RATE_cell =cell("TD","label","","",EXCESS_TARIFF_RATE,7,"","","font-size:14px;","","right","","");
  		  var EXCESS_AMT_cell =cell("TD","label","","",EXCESS_AMT,7,"","","font-size:14px;","","right","","");
  		  var EXCESS_QTY_cell =cell("TD","label","","",EXCESS_QTY,7,"","","font-size:14px;","","right","","");

  		   
  		  new_row.appendChild(METRE_LOCATION_cell);
  		  new_row.appendChild(METRE_CLOSING_READING_cell);
		  new_row.appendChild(METRE_INITIAL_READING_cell);
		  
		  new_row.appendChild(QTY_CONSUMED_cell);
		  var total="";
		  var Total_cons_cell="";
		
		  if (parseInt(BENEFICIARY_TYPE_ID) > 6 )
	 	  {
			 
			  
			  var MULTIPLY_FACTOR_cell=cell("TD","label","","",MULTIPLY_FACTOR,7,"","","font-size:14px;","","center","","");
			 
			  total=parseFloat(MULTIPLY_FACTOR)*parseFloat(QTY_CONSUMED);
			  var TARIFF_RATE_cell=cell("TD","label","","",TARIFF_RATE,7,"","","font-size:14px;","2%","right","","");
			  
			
			  new_row.appendChild(total_consumption_cell);
			  new_row.appendChild(MULTIPLY_FACTOR_cell);
			  new_row.appendChild(MIN_BILL_QTY_cell);			  
			  new_row.appendChild(TARIFF_RATE_cell);
			  new_row.appendChild(ALLOTED_QTY_cell);
			  new_row.appendChild(EXCESS_QTY_cell);
			  new_row.appendChild(EXCESS_TARIFF_RATE_cell);
			  new_row.appendChild(EXCESS_AMT_cell);
			  
	 	  }
		  else
		  {			
			  
			  var TARIFF_RATE_cell=cell("TD","label","","",TARIFF_RATE,7,"","","font-size:14px;","","right","","");

			  new_row.appendChild(TARIFF_RATE_cell);
			  total=parseFloat(TARIFF_RATE)*parseFloat(QTY_CONSUMED);
			  
			  
			//  new_row.appendChild(total_consumption_cell);
		  }
		   
		  new_row.appendChild(total_vlaue_cell);
		  
		  tbody.appendChild(new_row);
		
		  /*Final Reading 
		  Initial Reading
		  Difference(KL)
		  Multi Factor  
		  Total Consumption(KL)  
		  Min Bill Qty(KL)
		  Tariff Rate(Rs.)
		  Alloted Qty(KL)
		  Excess Consumption(KL)
		  Excess Rate(Rs.) 
		  Excess Amount(Rs.)
		  Amount Rs */
		  
		  
		  
		  
		  
		  
		  
		  
 	}
  	 var sch_len=demandxmlobj.responseXML.getElementsByTagName("sch_row")[0].firstChild.nodeValue;
 	 var sch_body = document.getElementById("sch_body");
	 var sch_table = document.getElementById("sch_table");
	 var t1=0;
 	 for(t1=sch_body.rows.length-1;t1>=0;t1--){sch_body.deleteRow(0);}
	 for (var i=0;i<sch_len;i++)
	 {
		
		 var sch_new_row=cell("TR","","","row1",1,2,"","","","","","","");//13
		 var L_SCH_NAME=demandxmlobj.responseXML.getElementsByTagName("L_SCH_NAME")[i].firstChild.nodeValue;
 		 var L_SCH_TYPE_DESC =demandxmlobj.responseXML.getElementsByTagName("L_SCH_TYPE_DESC")[i].firstChild.nodeValue;
  		 var L_QTY =demandxmlobj.responseXML.getElementsByTagName("L_QTY")[i].firstChild.nodeValue;
		  var L_SCH_NAME_cell=cell("TD","label","","",L_SCH_NAME,7,"","","font-size:14px;","","left","","");
 		  var L_SCH_TYPE_DESC_cell=cell("TD","label","","",L_SCH_TYPE_DESC,7,"","","font-size:14px;","","left","","");
 		  var L_QTY_cell=cell("TD","label","","",L_QTY,7,"","","font-size:14px;","","right","","");

 		 sch_new_row.appendChild(L_SCH_NAME_cell);
 		 sch_new_row.appendChild(L_SCH_TYPE_DESC_cell);
 		 sch_new_row.appendChild(L_QTY_cell);
 		  sch_body.appendChild(sch_new_row);
  	 }
 
}
