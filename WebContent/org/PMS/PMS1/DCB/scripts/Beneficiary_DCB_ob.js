var process_code=0;
var input_row_no=0;
var display_table="";
var display_body="";
var sub_flag=0;
var add_flag=0;
/*
  Document   : Bill Demand 
    Module	   : PMS
    Created on : 28/1/2010
    File Name  : Beneficiary_DCB_ob.js
    Action To  :
    File Desc  :  
    Author     : K.Panneer Selvam
    -----------------------------------------------------
    Sno	  Date	 	Type	EMP ID	
    -----------------------------------------------------
    1	  28/1/2010  N
    
 
 function 	command 	processs		          Description
 ========		=======		========		=================================
 				show		1 or 2			select value from look up values of Division and B.Type 
 				add 		4				new record for DCB opening Balance   
 				add 		6		 	    new record for Interest opening Balance
 				add 		7				new recoded for Pumping Return -> all rows 
 				add 		9				new recoded for Pumping Return -> single row
	show_2		show		3				List of Ben Name in Sub .Div
  show_2		show		4				List of Meter for selected Ben. 
 function 		values 					  Description
 ---------		------			------------------------------------
 data_show_dcb	'add',7,''		new recoded for Pumping Return -> all rows
 data_show_dcb	'add',9,1		new recoded for Pumping Return -> single row
 ckset()			1				meter get by this function
 single_row_update 9,row
*/	

function valueSet(process,command,flag)
{
	// values select from db and show to current text bx
	process_code=process;
	if (command=="report")
	{ 		
		if (process_code==3)
		{ 
			 document.getElementById("pr_status").value=0;; 

				
				url="../../../../../ob_report?command="+command+"&bentype="+document.getElementById("bentype").value+"&process_code="+process_code+"&input_value="+document.getElementById("selsno").value+"&YEAR="+document.getElementById("year").value;
		}
	 
	 
	var xmlobj=createObject();
	 
    xmlobj.open("GET",url,true);
    
	xmlobj.onreadystatechange=function()
    {	 
		valueSet_process(xmlobj,command,"",flag);
    }
	
	xmlobj.send(null);
	}
}
function valueSet_process(xmlobj,command,input_value,flag)
{
	 
	 if (xmlobj.readyState==4)
	 {	   
	 	 
		if (xmlobj.status==200)
	    {
		
			if (process_code==3)
			{ 
		
				  
				 document.getElementById("pr_status").value=1; 
				  // Dcb Area 
				    var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
					var i=0;
				  var ob = bR.getElementsByTagName("ob")[i].firstChild.nodeValue;
				  var addifany = bR.getElementsByTagName("addifany")[i].firstChild.nodeValue;
				  var collection = bR.getElementsByTagName("collection")[i].firstChild.nodeValue;
				  var yesteryear = bR.getElementsByTagName("yesteryear")[i].firstChild.nodeValue;
			 	  var currentyear = bR.getElementsByTagName("currentyear")[i].firstChild.nodeValue;
				  var demandupto= bR.getElementsByTagName("demandupto")[i].firstChild.nodeValue;
			 
				  var coll_up_prv_wcharge=bR.getElementsByTagName("coll_up_yester_year")[i].firstChild.nodeValue;
				  var coll_up_yester_year=bR.getElementsByTagName("coll_up_prv_wcharge")[i].firstChild.nodeValue;
				  var BENEFICIARY_NAME = bR.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
				  
				  
				    document.getElementById("bname").innerHTML=BENEFICIARY_NAME;
				    
				  if (flag==3)
				  {
				    document.getElementById("OB_MAINTENANCE_CHARGES").value=ob;	
			  		document.getElementById("ADDN_UPTO_PMTH_MCHARGES").value=addifany;
			  		document.getElementById("COLN_UPTO_PMTH_MAINT").value=collection;
			  		document.getElementById("OB_YESTER_YR_upto_2004").value=yesteryear;
			  		document.getElementById("OB_YESTER_YR_2004_CY").value=currentyear;
			  		document.getElementById("OB_WATER_CHARGES").value=demandupto;
			  		document.getElementById("COLN_UPTO_PMTH_YESTER_YR").value=coll_up_prv_wcharge;
			  		document.getElementById("COLN_UPTO_PMTH_WCHARGES").value=coll_up_yester_year;
				  }	 
			  		
			  		
			   if (flag==4)
			   {
				  var main_int_up_to_prv_fyear= bR.getElementsByTagName("main_int_up_to_prv_fyear")[i].firstChild.nodeValue; 
				  var main_int_collected= bR.getElementsByTagName("main_int_collected")[i].firstChild.nodeValue; 	 	
				  var water_int_up_to_prv_fyear= bR.getElementsByTagName("water_int_up_to_prv_fyear")[i].firstChild.nodeValue; 
				  var water_int_levied= bR.getElementsByTagName("water_int_levied")[i].firstChild.nodeValue;
				  var water_int_collected_prv_month= bR.getElementsByTagName("water_int_collected_prv_month")[i].firstChild.nodeValue;
				  
				  
				    document.getElementById("OB_INTEREST_AMT_MAINT").value=main_int_up_to_prv_fyear; 		
		  			document.getElementById("COLN_UPTO_PMTH_INTEREST_MAINT").value=main_int_collected;
		  			document.getElementById("OB_INTEREST_AMT_WC").value=water_int_up_to_prv_fyear;
		  			document.getElementById("INT_UPTO_CUR_MONTH_CALC").value=water_int_levied;
		  			document.getElementById("COLN_UPTO_PMTH_INTEREST_WC").value=water_int_collected_prv_month;
			   }  
			}
	    }
	 }
	 
}
function pdf_show(process,command)
{
	
		process_code=process;
		if (command=="pdf")
		{
			var ben_type=document.getElementById("bentype").value;
			var year=document.getElementById("year").value;
			
			window.open("../../../../../Beneficiary_DCB_ob?command="+command+"&process_code="+process_code+"&ben_type="+ben_type+"&year="+year);
		} 
		
		
}
function pdf_show_process(xmlobj,command,input_value)
{
	 
	 if (xmlobj.readyState==4)
	 {	   
	 	 
		if (xmlobj.status==200)
	    {
		
			if (process_code==1)
			{
				
			}
	    }
	 }
}
			 

function grid_show(process,command,df1,df2, inp2)
{  
	
 
 	 process_code=process;
	// process 4  -> List of metter
 	document.getElementById("pr_status").value=0;; 
 	var bentype=document.getElementById("bentype").value;
 	  
	 document.getElementById("pr_status").value=0;; 

	   if (process_code==3 && bentype==6)
	   {
		   document.getElementById('block').style.visibility = 'visible';
		   document.getElementById('dis').style.visibility = 'visible';   
		   
	   }
	   else
	   {
		   
		   document.getElementById('block').style.visibility = 'hidden';
		   document.getElementById('dis').style.visibility = 'hidden';
	   }
	 if (command=="report")
	{
 		 
 		if (process_code==7)
 		{ 
 			add_flag=1;
 			// process_code=1;
 		}
 		
 		if (process_code==1 || process_code==7)
		{
			url="../../../../../ob_report?command="+command+"&bentype="+document.getElementById("bentype").value+"&process_code="+process_code+"&divcode="+document.getElementById("subdiv").value+"&YEAR="+document.getElementById("year").value;
		  
		}
		if (process_code==2)
		{
			url="../../../../../ob_report?command="+command+"&bentype="+document.getElementById("bentype").value+"&process_code="+process_code+"&divcode="+document.getElementById("subdiv").value+"&YEAR="+document.getElementById("year").value;
		 
		}
		
		 
		
		
	}
	else
	{
	document.getElementById("selbentype").value=document.getElementById(inp2).value;
	display_table=df1;
	display_body=df2;
	 
	
		if (process_code==4)
		{
				
			//if (bentype!=6)
			 //{
				    url="../../../../../Beneficiary_DCB_ob?command="+command+"&bentype="+document.getElementById(inp2).value+"&process_code="+process_code+"&divcode="+document.getElementById("subdiv").value;
				    url+="&ben_sno="+document.getElementById("BENEFICIARY_SNO").value;
				    url+="&year="+document.getElementById("year").value;//YEAR
		  	     	url+="&month="+document.getElementById("month").value;//MONTH	 
			 //}
			//else
			//{
				   document.getElementById('block').style.visibility = 'visible';
				   document.getElementById('dis').style.visibility = 'visible';
				
			//}
			
  	     	 
		}
		else
		{
			
			
			
			
			if (bentype==6)
			{
			sub_process=1;
			var dis_value=document.getElementById("dis_value").value;
			var block_value=document.getElementById("block_value").value;
			url="../../../../../Beneficiary_DCB_ob?command="+command+"&bentype="+document.getElementById(inp2).value+"&process_code="+process_code+"&divcode="+document.getElementById("subdiv").value;
			url+="&dis_value="+dis_value+"&block_value="+block_value+"&sub_process="+sub_process;
			 
			}
			else
			{
				sub_process=0;
				url="../../../../../Beneficiary_DCB_ob?command="+command+"&bentype="+document.getElementById(inp2).value+"&process_code="+process_code+"&divcode="+document.getElementById("subdiv").value;
				url+="&sub_process="+sub_process;
				 
			}
			
			
		}if (process_code==5)
		{
			var dis_value=document.getElementById("dis_value").value;
			var block_value=document.getElementById("block_value").value;
			url="../../../../../Beneficiary_DCB_ob?command="+command+"&bentype="+document.getElementById(inp2).value+"&process_code="+process_code+"&divcode="+document.getElementById("subdiv").value;
			url+="&dis_value="+dis_value+"&block_value="+block_value+"&sub_process="+sub_process;
			
		}
		
		
	}	
 
	
	var xmlobj=createObject();
    xmlobj.open("GET",url,true);
    
	xmlobj.onreadystatechange=function()
    {	 
      result_process(xmlobj,command,"");
    }
	
	xmlobj.send(null);
}

 
function data_show_dcb(command,process,input_value)
 {
 
	 
	 process_code=process;
	

	 
	
	 if (command=='show')  
  	{    	 
        if (process==1 || process==2) url="../../../../../Beneficiary_DCB_ob?command="+command+"&input_value="+input_value+"&process_code="+process_code+"&divcode="+document.getElementById("subdiv").value;
        
      
        var xmlobj=createObject();
        xmlobj.open("GET",url,true);
        xmlobj.onreadystatechange=function()
        { 
        	result_process(xmlobj,command,input_value);
        }
        xmlobj.send(null);
  	}else if (command=='add')
     {
  		var addflag=0;
  		var xmlobj=createObject();
  		
  		if (process_code==4)
  		{
  		// Insert the DCB  ----------------- Store
  		document.getElementById("pr_status").value=0;; 	
  		var obmain=document.getElementById("OB_MAINTENANCE_CHARGES").value;	
  		var ifadd=document.getElementById("ADDN_UPTO_PMTH_MCHARGES").value;
  		var collectionupto=document.getElementById("COLN_UPTO_PMTH_MAINT").value;
  		var yeasteryear=document.getElementById("OB_YESTER_YR_upto_2004").value;
  		var currentyear=document.getElementById("OB_YESTER_YR_2004_CY").value;
  		var wc=document.getElementById("OB_WATER_CHARGES").value;
  		var collecprvmonth=document.getElementById("COLN_UPTO_PMTH_WCHARGES").value;
  		var colluptopmthyesteryr=document.getElementById("COLN_UPTO_PMTH_YESTER_YR").value;
  		if (obmain=="" || ifadd=="" || collectionupto==""||yeasteryear==""||currentyear==""||wc==""||collecprvmonth=="")
  			addflag=1;
  		else
  			addflag=0;
  		
    	url="../../../../../Beneficiary_DCB_ob";
        url+="?OB_MAINTENANCE_CHARGES="+obmain;
     	url+="&ADDN_UPTO_PMTH_MCHARGES="+ifadd;
     	url+="&COLN_UPTO_PMTH_MAINT="+collectionupto;
     	url+="&OB_YESTER_YR_upto_2004="+yeasteryear;
     	url+="&OB_YESTER_YR_2004_CY="+currentyear;
     	url+="&OB_WATER_CHARGES="+wc;
     	url+="&COLN_UPTO_PMTH_WCHARGES="+collecprvmonth;
     	url+="&COLN_UPTO_PMTH_YESTER_YR="+colluptopmthyesteryr;
      	url+="&command="+command;
     	url+="&process_code="+process_code;
      	url+="&year="+document.getElementById("year").value;
     	url+="&BENEFICIARY_SNO="+document.getElementById("BENEFICIARY_SNO").value;
     	url+="&OFFICE_ID="+document.getElementById("OFFICE_ID").value;
     	url+="&BENEFICIARY_TYPE_SNO="+document.getElementById("bentype").value;
       

  		}
  		else if (process_code==10)
  		{
  	  		document.getElementById("pr_status").value=0;; 	

  			var obmain=document.getElementById("OB_MAINTENANCE_CHARGES").value;	
  	  		var ifadd=document.getElementById("ADDN_UPTO_PMTH_MCHARGES").value;
  	  		var collectionupto=document.getElementById("COLN_UPTO_PMTH_MAINT").value;
  	  		var yeasteryear=document.getElementById("OB_YESTER_YR_upto_2004").value;
  	  		var currentyear=document.getElementById("OB_YESTER_YR_2004_CY").value;
  	  		var wc=document.getElementById("OB_WATER_CHARGES").value;
  	  		var collecprvmonth=document.getElementById("COLN_UPTO_PMTH_WCHARGES").value;
  	  		var colluptopmthyesteryr=document.getElementById("COLN_UPTO_PMTH_YESTER_YR").value;
  	  		if (obmain=="" || ifadd=="" || collectionupto==""||yeasteryear==""||currentyear==""||wc==""||collecprvmonth=="")
  	  			addflag=1;
  	  		else
  	  			addflag=0;
  	  		
  	    	url="../../../../../Beneficiary_DCB_ob";
  	        url+="?OB_MAINTENANCE_CHARGES="+obmain;
  	     	url+="&ADDN_UPTO_PMTH_MCHARGES="+ifadd;
  	     	url+="&COLN_UPTO_PMTH_MAINT="+collectionupto;
  	     	url+="&OB_YESTER_YR_upto_2004="+yeasteryear;
  	     	url+="&OB_YESTER_YR_2004_CY="+currentyear;
  	     	url+="&OB_WATER_CHARGES="+wc;
  	     	url+="&COLN_UPTO_PMTH_WCHARGES="+collecprvmonth;
  	     	url+="&COLN_UPTO_PMTH_YESTER_YR="+colluptopmthyesteryr;
  	      	url+="&command="+command;
  	     	url+="&process_code="+process_code;
  	      	url+="&year="+document.getElementById("year").value;
  	     	url+="&BENEFICIARY_SNO=0";
  	     	url+="&OFFICE_ID=0";
  	     	url+="&BENEFICIARY_TYPE_SNO=0";
  	     	url+="&selsno="+document.getElementById("selsno").value;
  	     	
  	     	
  		}
  		else if (process_code==6 ||  process_code==11)
  		{
  			document.getElementById("pr_status").value=0;; 
  		//Insert the Interest  ----------------- Store  
  			addflag=0;
  			var obint=document.getElementById("OB_INTEREST_AMT_MAINT").value; 		
  			var colint_main=document.getElementById("COLN_UPTO_PMTH_INTEREST_MAINT").value;
  			var ob_int_wc=document.getElementById("OB_INTEREST_AMT_WC").value;
  			var int_upto_cur_month=document.getElementById("INT_UPTO_CUR_MONTH_CALC").value;
  			var colint_wc=document.getElementById("COLN_UPTO_PMTH_INTEREST_WC").value;
  			
  			if (obint=="" || colint_main=="" || ob_int_wc==""||int_upto_cur_month==""||colint_wc=="")
  				addflag=1;
  	  		else
  	  			addflag=0;
  			url="../../../../../Beneficiary_DCB_ob";
  			url+="?OB_INTEREST_AMT_MAINT="+obint;
  	     	url+="&COLN_UPTO_PMTH_INTEREST_MAINT="+colint_main;
  	     	url+="&OB_INTEREST_AMT_WC="+ob_int_wc;
  	     	url+="&INT_UPTO_CUR_MONTH_CALC="+int_upto_cur_month;
  	     	url+="&COLN_UPTO_PMTH_INTEREST_WC="+colint_wc;
  	     	url+="&BENEFICIARY_SNO="+document.getElementById("BENEFICIARY_SNO").value;
  	     	url+="&BENEFICIARY_TYPE_SNO="+document.getElementById("bentype").value;
  	     	url+="&command="+command;
  	     	url+="&process_code=6";
  	     	url+="&obsno="+document.getElementById("obsno").value;
           
  	     	
  		}
  		
  		else if (process_code==7)
  		{
  			
  			var netunit=document.getElementById("netunit").value;
  			if (netunit=="" || netunit==0)
  			{
  				alert("Total Units Missing \n-------------------------- ")
  				sub_flag=1;
  			}else
  			{	
  				
  				
  			//  Pumping Return ----------------- Store  
  			
  			var rowcnt_meter=document.getElementById("rowcnt_meter").value;
  			var selbentype=document.getElementById("selbentype").value;
  			url="../../../../../Beneficiary_DCB_ob";
  			url+="?command="+command;
  	     	url+="&process_code="+process_code;
  	     	url+="&year="+document.getElementById("year").value;//YEAR
  	     	url+="&month="+document.getElementById("month").value;//MONTH
  	     	url+="&BENEFICIARY_SNO="+document.getElementById("BENEFICIARY_SNO").value;//BENEFICIARY_SNO
  	     	url+="&OFFICE_ID="+document.getElementById("OFFICE_ID").value;//OFFICE_ID
  	     	url+="&SUBDIV_OFFICE_ID="+document.getElementById("subdiv").value;//OFFICE_ID
  	     	url+="&rowcnt_meter="+rowcnt_meter;//OFFICE_ID
  	     	url+="&BENEFICIARY_TYPE_SNO="+document.getElementById("bentype").value;
  	     	url+="&netunit="+document.getElementById("netunit").value;//netunit
  	     	
  	     	
  	     	
  			for (i=1;i<=rowcnt_meter;i++)
  			{
  				
  				var selsno=document.getElementById("selsno"+i).value;
  				var unit=document.getElementById("nounit"+i).value;
  				
  				if (unit!=0)
  				{
  				var read=document.getElementById("read"+i).value;
  				url+=("&METRE_SNO"+i)+"="+selsno;
    	  	   	url+="&METRE_INITIAL_READING"+i+"="+document.getElementById("METRE_INIT_READING"+i).value;
  	  	     	url+=("&METRE_CLOSING_READING"+i)+"="+read;
  			    url+=("&QTY_CONSUMED"+i)+"="+document.getElementById("nounit"+i).value;;
  			    url+=("&PRVMETRE_WORKING"+i)+"="+document.getElementById("PRVMETRE_WORKING"+i).value;
  			   	var aqty=document.getElementById("ALLOTED_QTY"+i).value;
  			    
  			   	// Excess Qty 
  			  var ben_type=document.getElementById("bentype").value;
  			
  			  if (aqty>0)
  			  {
		  			  
		  			  if (ben_type > 6)
		  			  {
		  			   	
		 			    var eqty=(parseInt(unit)-parseInt(aqty));
		 			    
		 			    if (eqty>0)
		 			    	eqty=eqty;
		 			    else
		 			    	eqty=0;
		  			  }
		  			  else
		  			  {
		  			 	eqty=0;
		  				  
		  			  }
  			  }
  			  else
  			  {
  			 	eqty=0;
  				  
  			  }
 			    var meter="";
 			    if (document.getElementById("METRE_FIXED"+i).innerHTML=="Yes")
 			    	meter="Y";
 			    else
 			    	meter="N";
 			   var meterw="";
 			    if (document.getElementById("METRE_WORKING"+i).value=="Y")
			    	meterw="Y";
			    else
			    	meterw="N";
			    url+="&METRE_WORKING"+i+"="+meterw;
  	  	     	url+="&METRE_FIXED"+i+"="+meter;
 			    url+=("&ALLOTED_QTY"+i)+"="+aqty;
 			    url+=("&EXCESS_QTY"+i)+"="+eqty;
  				}
  				
  			
  			}
  		     
  			}
  		}
  		 
  		if (addflag==0)
  		{
     	xmlobj.open("GET", url, true);
     	//xmlobj.setRequestHeader("Content-length", data.length);
    	xmlobj.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        //   xmlobj.setRequestHeader("Connection", "close");
	    xmlobj.send(null);
     	 
          xmlobj.onreadystatechange=function()
         {
          result_process(xmlobj,command,process_code);
         }
  		}else
  		{
  			
  			alert("input value missing!\n----------------------- ")
  		}
     }
 }

function result_process(xmlobj,command,input_value)
{	 
	
	 
 if (xmlobj.readyState==4)
 {	   
 	 
	if (xmlobj.status==200)
    {
		if (command=='pdf')
		{
			
		}
		if (command=='show')
		{
			
			show(xmlobj,input_value);
		}
		if (command=='data')
		{
				show_2(xmlobj,input_value,display_table,display_body );
		}	
		if (command=='add')
		{ 
			document.getElementById("pr_status").value=0; 
			if (process_code==10)
			{	
				show(xmlobj,process_code);
			}
			else
			{
					if (input_value!=9)
					{
						
						show(xmlobj,input_value);
					}
					else
					{
						single_update_result(xmlobj,input_value);
					}
			
			}
		}
		if  (command=='report')
		{
			
			report_show(xmlobj,input_value);
			document.getElementById("pr_status").value=1;;  
		}
    }
  }
    
}
 
function report_show(xmlobj,input_value)
{  
	document.getElementById("en_rowcnt").value=0; 	 
	var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
	var tbody = document.getElementById("entred_body");
 	var table = document.getElementById("entred_data");
 
	  var t=0;	
	 
	  for(t=tbody.rows.length-1;t>=0;t--){tbody.deleteRow(0);}
  	
 	  var len=0;
 		  try
	 	  {
 		  len=bR.getElementsByTagName("prow")[0].firstChild.nodeValue;
	 	  }catch(e){len=0;}
	 
	  document.getElementById("en_rowcnt").value=len;
	  
	 
	  
	  
 	  for (i=0;i<len;i++)
	 	{ 
 	 
	  var new_row=cell("TR","","","row1",1,2,"","","","","","","");//13
	  var BENEFICIARY_NAME = bR.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
	  
	  var fschar = bR.getElementsByTagName("fschar")[i].firstChild.nodeValue;
	  
	   
	   var href_cell=cell("TD","A","EDIT","EDIT","EDIT",2,"","javascript:select("+BENEFICIARY_OB_SNO+","+(i+1)+")","","5%","","","");

	  var address3 = bR.getElementsByTagName("address3")[i].firstChild.nodeValue;

	  // Dcb Area 
	  
	  var ob = bR.getElementsByTagName("ob")[i].firstChild.nodeValue;
	  var addifany = bR.getElementsByTagName("addifany")[i].firstChild.nodeValue;
	  var collection = bR.getElementsByTagName("collection")[i].firstChild.nodeValue;
	  var yesteryear = bR.getElementsByTagName("yesteryear")[i].firstChild.nodeValue;
 	  var currentyear = bR.getElementsByTagName("currentyear")[i].firstChild.nodeValue;
	  var demandupto= bR.getElementsByTagName("demandupto")[i].firstChild.nodeValue;
	  var BENEFICIARY_OB_SNO= bR.getElementsByTagName("BENEFICIARY_OB_SNO")[i].firstChild.nodeValue;
	  var BENEFICIARY_TYPE= bR.getElementsByTagName("BENEFICIARY_TYPE")[i].firstChild.nodeValue;
	  
	  // Interest Area 
	  
	  var main_int_up_to_prv_fyear= bR.getElementsByTagName("main_int_up_to_prv_fyear")[i].firstChild.nodeValue; 
	  var main_int_collected= bR.getElementsByTagName("main_int_collected")[i].firstChild.nodeValue; 	 	
	  var water_int_up_to_prv_fyear= bR.getElementsByTagName("water_int_up_to_prv_fyear")[i].firstChild.nodeValue; 
	  var water_int_levied= bR.getElementsByTagName("water_int_levied")[i].firstChild.nodeValue;
	  var water_int_collected_prv_month= bR.getElementsByTagName("water_int_collected_prv_month")[i].firstChild.nodeValue;	
	  var address=address3;
	  
	  var name_cell=cell("TD","A","",fschar,BENEFICIARY_NAME,7,"","javascript:void()","font-size:14px;","","","","");
	  var address_cell=cell("TD","label","","",address,7,"","","font-size:14px;","","","","");

	  
	 // Dcb Area cell 
	  
	  var ob_cell=cell("TD","label","","",ob,7,"","","font-size:14px;","","right","","");
	  var addifany_cell=cell("TD","label","","",addifany,7,"","","font-size:14px;","","right","","");
	  var collection_cell=cell("TD","label","","",collection,7,"","","font-size:14px;","","right","","");
	  var yesteryear_cell=cell("TD","label","","",yesteryear,7,"","","font-size:14px;","","right","","");
	  var currentyear_cell=cell("TD","label","","",currentyear,7,"","","font-size:14px;","","right","","");
	  var demandupto_cell=cell("TD","label","","",demandupto+"  ",7,"","","font-size:14px;","","right","","");
	  
	  // Interest Area cell 
	  var main_int_up_to_prv_fyear_cell=cell("TD","label","","",main_int_up_to_prv_fyear,7,"","","font-size:14px;","","right","","");
	  var main_int_collected_cell=cell("TD","label","","",main_int_collected,7,"","","font-size:14px;","","right","","");
	  var water_int_up_to_prv_fyear_cell=cell("TD","label","","",water_int_up_to_prv_fyear,7,"","","font-size:14px;","","right","","");
	  var water_int_levied_cell=cell("TD","label","","",water_int_levied,7,"","","font-size:14px;","","right","","");
	  var water_int_collected_prv_month_cell=cell("TD","label","","",water_int_collected_prv_month,7,"","","font-size:14px;","","right","","");
 	  
	  
	  
	  
	  var BENEFICIARY_OB_SNO_cell=cell("TD","input","hidden","",BENEFICIARY_OB_SNO+"  ",7,"","","font-size:14px;","","left","","");
	  var BENEFICIARY_TYPE =cell("TD","label","","",BENEFICIARY_TYPE+"  ",7,"","","font-size:14px;","25%","left","","");
 	  if (add_flag==1)
	  {
  	  var href_cell=cell("TD","A","EDIT","EDIT","Edit",2,"","javascript:select("+BENEFICIARY_OB_SNO+","+(i+1)+")","","6%","","","");
  	  var href_Int_cell=cell("TD","A","EDIT","EDIT","Interest Entry",2,"","javascript:int_select("+BENEFICIARY_OB_SNO+")","","6%","","","");
  	  var dcbhide="intremoveSelect("+(i+1)+")";
      var inthide="dcbremoveSelect("+(i+1)+")";
    
		    var DCB_cell=cell("TD","input","radio","chdcb"+(i+1),0,7,"","","","2%","","onclick",inthide);
		    var DCB =cell("TD","label","","","DCB",7,"","","font-size:14px;","5%","left","","");
		    
		    var Int_cell=cell("TD","input","radio","chint"+(i+1),0,7,"","","","2","","onclick",dcbhide);
		    var Int =cell("TD","label","","","Int",7,"","","font-size:14px;","5%","left","","");
		    new_row.appendChild(DCB_cell);  
		    new_row.appendChild(DCB);
		    new_row.appendChild(Int_cell);
		    new_row.appendChild(Int);
		    new_row.appendChild(href_cell);
		    
			  new_row.appendChild(BENEFICIARY_TYPE);
			  new_row.appendChild(name_cell);
			    new_row.appendChild(href_Int_cell);

			  new_row.appendChild(BENEFICIARY_OB_SNO_cell);
			  
	  
	  }else
	  {
		 
  		  //  Dcb Area cell to table 
		  
		  new_row.appendChild(name_cell);
	//	  new_row.appendChild(address_cell);
		  new_row.appendChild(ob_cell);
		  new_row.appendChild(addifany_cell);
		  new_row.appendChild(collection_cell);
		  new_row.appendChild(main_int_up_to_prv_fyear_cell);
		  new_row.appendChild(main_int_collected_cell);
		  new_row.appendChild(yesteryear_cell);
		  new_row.appendChild(currentyear_cell);
		  new_row.appendChild(demandupto_cell);  
		  new_row.appendChild(water_int_up_to_prv_fyear_cell);
		  new_row.appendChild(water_int_levied_cell);
		  new_row.appendChild(water_int_collected_prv_month_cell); 
		  
		//  Int Area cell to table
		  
	
		  
	  }
	  

      tbody.appendChild(new_row);
	 	}  
}


function show(xmlobj,input_value)
{
	
	
	var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
	
	if (bR.firstChild.nodeValue=="404")
		alert("Data Base Server Not Connectecd \n ------------------------------------")
	if (process_code==4 )
	{
		 alert("Record  Successfully Updated \n--------------------------------- " )
 		 grid_show(7,'report','','bentype','subdiv')
 		 document.getElementById("OB_MAINTENANCE_CHARGES").value="";
         document.getElementById("COLN_UPTO_PMTH_MAINT").value="";
         document.getElementById("OB_WATER_CHARGES").value="";
         document.getElementById("OB_YESTER_YR_upto_2004").value="";
         document.getElementById("OB_YESTER_YR_2004_CY").value="";
         document.getElementById("OB_WATER_CHARGES").value="";
         document.getElementById("COLN_UPTO_PMTH_WCHARGES").value="";
         document.getElementById("COLN_UPTO_PMTH_YESTER_YR").value="";
         document.getElementById("ADDN_UPTO_PMTH_MCHARGES").value="";
 		 
       //  document.getElementById("ch"+i).checked=false;
          
	}
	else if (process_code==6 || process_code==11)
	{    	
		
		document.getElementById("pr_status").value=1;
		 var status = bR.getElementsByTagName("status")[0].firstChild.nodeValue;
		if (status>=1)
		{
			   alert("Record  Successfully Updated \n--------------------------------- " )
			  document.getElementById("OB_INTEREST_AMT_MAINT").value="";
	    	  document.getElementById("COLN_UPTO_PMTH_INTEREST_MAINT").value="";
	    	  document.getElementById("OB_INTEREST_AMT_WC").value="";
	    	  document.getElementById("INT_UPTO_CUR_MONTH_CALC").value="";
	    	  document.getElementById("COLN_UPTO_PMTH_INTEREST_WC").value="";
		}
		else
		{
			alert("Record Successfully Updated \n--------------------------------- " )
			
		}
		 
	}else if (process_code==7 )
	{
			///  Pumping Return
			document.getElementById("pr_status").value=1;
		  var status = bR.getElementsByTagName("status")[0].firstChild.nodeValue;
		
		  var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
		  var tbody = document.getElementById("entred_body");
		  var table = document.getElementById("entred_data");                                                                                                     
		  var t=0;
		  if (sub_flag!=1)
		  alert("Record  Successfully Updated ..... " )
		  
		 
		  
		  if (sub_flag==0)
		  {
			  var ins_row= bR.getElementsByTagName("ins_row")[0].firstChild.nodeValue;
			   
		 
		  }
	} 
		
	else if (process_code==10 )
	{ 
		
		var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
  		document.getElementById("pr_status").value=1; 	

		var up_row = bR.getElementsByTagName("up_row")[0].firstChild.nodeValue;
	
		if (up_row>=1)
		{
			alert("Record Successfully Updated \n--------------------------------- " )
			document.getElementById("OB_MAINTENANCE_CHARGES").value="";
	         document.getElementById("COLN_UPTO_PMTH_MAINT").value="";
	         document.getElementById("OB_WATER_CHARGES").value="";
	         document.getElementById("OB_YESTER_YR_upto_2004").value="";
	         document.getElementById("OB_YESTER_YR_2004_CY").value="";
	         document.getElementById("OB_WATER_CHARGES").value="";
	         document.getElementById("COLN_UPTO_PMTH_WCHARGES").value="";
	         document.getElementById("COLN_UPTO_PMTH_YESTER_YR").value="";
	         document.getElementById("ADDN_UPTO_PMTH_MCHARGES").value="";

		}	
	}
	else
	{
		document.getElementById("pr_status").value=1;;
		if (process_code==1 || process_code==2)
		{
						
			var len=bR.getElementsByTagName("sno").length;
			var status=bR.getElementsByTagName("status")[0].firstChild.nodeValue;
			if (len==0)
				alert(status+"\n-------------------------------")
				for (i=0;i<len;i++)
				{
					var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
				    var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
				    addOption(document.getElementById(input_value),name,sno)
			    }
		    
		}
		
		
	}
	
	
	
}
 

function show_2(xmlobj,input_value,disf1,disf2)
{
	 
	var bR=xmlobj.responseXML.getElementsByTagName("result")[0];

	if (process_code==3)
	{
		document.getElementById("pr_status").value=1;;
		var len=bR.getElementsByTagName("sno").length;
		var status=bR.getElementsByTagName("status")[0].firstChild.nodeValue;
		var table = document.getElementById(disf1);
		var tbody = document.getElementById(disf2);
		var t=0;
		for(t=tbody.rows.length-1;t>=0;t--){tbody.deleteRow(0);}
		
		if (len==0)
		{
			alert(status+"\n---------------------")
			// Meter Location Clear
			try
			{
			var ben_meter_tbody = document.getElementById("ben_meter_body");
			for(t=ben_meter_tbody.rows.length-1;t>=0;t--){ben_meter_tbody.deleteRow(0);}
			}catch(e){}
		}
	 
		for (var i=0;i<len;i++)
		{    
			
			var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
			var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
			var new_row=cell("TR","","","row"+(i+1),(i+1),2,"","","","","","","");
			
			var sno_cell=cell("TD","input","hidden","select"+(i+1),sno,2,"","","","2%","","","");
		    var check_cell=cell("TD","input","radio","ch"+(i+1),0,7,"","","","10%","","onclick","ckset("+(i+1)+")");
		    
		    var name_cell=cell("TD","label","","disvalue"+(i+1),name,2,"label","","","90%","","","");
		    new_row.appendChild(sno_cell);
		    new_row.appendChild(check_cell);
		    new_row.appendChild(name_cell);
		    tbody.appendChild(new_row);
		    
		 }
			 
			document.getElementById("rowcnt").value=len;
			 
	 }
	
	if (process_code==4 || process_code==5 )
	{
		document.getElementById("pr_status").value=1;
		 /*List of All Meter Details of Consumer
		  * 
		  * 
		  * */
		 
		var status=bR.getElementsByTagName("status")[0].firstChild.nodeValue;

		var len=bR.getElementsByTagName("sno").length;
 	 
		var table = document.getElementById(disf1);//ben_meter_data
		var tbody = document.getElementById(disf2);//ben_meter_body
		var t=0;
		
		if (len==0){
			alert(status+"\n----------------------------");			 
		}
		
	 
		document.getElementById("rowcnt_meter").value=len;
		for(t=tbody.rows.length-1;t>=0;t--){tbody.deleteRow(0);}
		
		for (i=0;i<len;i++)
		{   
			  var new_row=cell("TR","","","mrow"+(i+1),(i+1),2,"","","","","","");
			  var sno= bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
			  var METRE_LOCATION = bR.getElementsByTagName("METRE_LOCATION")[i].firstChild.nodeValue;
			  
			  var METRE_FIXED="";
			  var METRE_WORKING="";
			  try
			  {
			  METRE_FIXED = bR.getElementsByTagName("METRE_FIXED")[i].firstChild.nodeValue;
			 
			  }catch(e) {
				  METRE_FIXED="No";
			  }
			  var METRE_INIT_READING = bR.getElementsByTagName("METRE_INIT_READING")[i].firstChild.nodeValue;
			  
			//var METRE_CLOSE_READING= bR.getElementsByTagName("METRE_CLOSE_READING")[i].firstChild.nodeValue;
			  try
			  {
			  METRE_WORKING = bR.getElementsByTagName("METRE_WORKING")[i].firstChild.nodeValue;
			 
			  }catch(e) {
				  METRE_FIXED="No";
			  }
			  var ALLOTED_QTY = bR.getElementsByTagName("ALLOTED_QTY")[i].firstChild.nodeValue;
			  var SCHEME_NAME = bR.getElementsByTagName("SCHEME_NAME")[i].firstChild.nodeValue;
		      var location_cell=cell("TD","label","","METRE_LOCATION"+(i+1),METRE_LOCATION,2,"label1","","font-size: 15px","20%","left","onClick","show_prv("+(i+1)+")");
		      var meter_available_cell=cell("TD","label","","METRE_FIXED"+(i+1),METRE_FIXED,2,"label1","","","10%","center","","");
		      var scheme_cell=cell("TD","label","","SCHEME_NAME"+(i+1),SCHEME_NAME,2,"label1","","","10%","left","","");
		      var ingreading_cell="";
		      var METRE_TYPE= bR.getElementsByTagName("METRE_TYPE")[i].firstChild.nodeValue;
		     
		      // if Meter Not Fixed ,set the Initial Reading column Readonly
		      if (METRE_FIXED=="No" || ( METRE_FIXED=="Yes" && METRE_WORKING=="No"))
		      ingreading_cell=cell("TD","input","text","METRE_INIT_READING"+(i+1),METRE_INIT_READING,5,"tb4","","text-align: right; readonly","10%","right","onKeyup","isInteger(this,9,event)");
		      else
		      ingreading_cell=cell("TD","input","text","METRE_INIT_READING"+(i+1),METRE_INIT_READING,5,"tb4","","text-align: right;  ","10%","right","onKeyup","isInteger(this,9,event)");		    	  
		    	  
		      var readingentry_cell="";
		     
		      if (METRE_FIXED=="No" || ( METRE_FIXED=="Yes" && METRE_WORKING=="No"))
		      {		    	
		    	  readingentry_cell=cell("TD","input","text","read"+(i+1),0,7,"tb4","","text-align: right; readonly","10%","","onblur#onKeyup","calcuate("+(i+1)+")#isInteger(this,9,event)");
		      }
		      else
		      {
		    	  readingentry_cell=cell("TD","input","text","read"+(i+1),0,7,"tb4","","text-align: right;  ","10%","","onblur#onKeyup","calcuate("+(i+1)+")#isInteger(this,9,event)");
		      }
		    
		      // var openingreading_cell=cell("TD","label","","METRE_CLOSE_READING"+(i+1),METRE_CLOSE_READING,5,"","","","10%","right","","");
		      var meterworking_hidden_cell=cell("TD","input","hidden","PRVMETRE_WORKING"+(i+1),METRE_WORKING,2,"","","","10%","center","","");
		      var meterworking_cell=cell("TD","select","","METRE_WORKING"+(i+1),METRE_WORKING,2,"select","","","10%","center","onchange","statuschange("+(i+1)+")");
		      
		      var allocatedqty_cell=cell("TD","input","hidden","ALLOTED_QTY"+(i+1),ALLOTED_QTY,2,"","","","10%","right","","");
		     // var readingentry_cell=cell("TD","input","text","read"+(i+1),0,7,"","","text-align: right","10%","","onblur#onKeyup","calcuate("+(i+1)+")#isInteger(this,9,event)");
		      //var readingentry_cell=cell("TD","input","text","read"+(i+1),0,7,"","","text-align: right","10%","","onblur","calcuate("+(i+1)+")");
		      var nounit_cell=cell("TD","input","text","nounit"+(i+1),0,7,"tb4","","text-align: right","10%","center","onKeyup#onblur","isInteger(this,9,event)#calcuate("+(i+1)+")");
		      var button_cell=cell("TD","input","button","submit"+(i+1),"Save",7,"fb2","","text-align: right","10%","","onclick","single_row_update(9,'"+(i+1)+"')");
		      
		      var sno_cell=cell("TD","input","hidden","selsno"+(i+1),sno,2,"","","","2%","","","");
		      var METRE_TYPE_cell=cell("TD","input","hidden","METRE_TYPE"+(i+1),METRE_TYPE,2,"","","","2%","","","");

		      var val_status_cell=cell("TD","input","hidden","val_status"+(i+1),0,2,"","","","2%","","","");
		   
		      new_row.appendChild(location_cell);
		      new_row.appendChild(scheme_cell);
		      new_row.appendChild(meter_available_cell);
		      new_row.appendChild(meterworking_hidden_cell);
		      new_row.appendChild(meterworking_cell);
		      new_row.appendChild(ingreading_cell);
		  //  new_row.appendChild(openingreading_cell);
		      new_row.appendChild(allocatedqty_cell);
		      new_row.appendChild(readingentry_cell);
		      new_row.appendChild(nounit_cell);
		      new_row.appendChild(button_cell);
		      new_row.appendChild(sno_cell);
		      new_row.appendChild(val_status_cell);
		      new_row.appendChild(METRE_TYPE_cell);

		      
		      tbody.appendChild(new_row); 
		 }
			
	 }
	
	
}

function single_vlaue(command,process)
{
	
	var xmlobj_new =null;
	xmlobj_new=createObject();
	process_code=process;
	url="../../../../../Beneficiary_DCB_ob";
	url+="?year="+document.getElementById("year").value;
 	url+="&BENEFICIARY_SNO="+document.getElementById("BENEFICIARY_SNO").value;
  	url+="&command="+command;
   	url+="&process_code="+process_code;
   	xmlobj_new.open('GET',url , true);
   	xmlobj_new.onreadystatechange = function() {
		  ob_sno_set(xmlobj_new);
	}
   	xmlobj_new.send();
	/*
	process_code=process;
	var xmlobj1=createObject();
	url="../../../../../Beneficiary_DCB_ob";
	url+="?year="+document.getElementById("year").value;
 	url+="&BENEFICIARY_SNO="+document.getElementById("BENEFICIARY_SNO").value;
  	url+="&command="+command;
   	url+="&process_code="+process_code;

   	xmlobj1.open("GET", url, true);
   //	xmlobj1.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    alert(url)
   xmlobj1.onreadystatechange=function()
     {  		 
    	ob_sno_set(xmlobj1);
     }
    xmlobj1.send(null);
  
    */
}
function single_row_update(process_code,input_value)
{
	 
				// Pumping Return ----------------- Store  
			
			var mw=document.getElementById("METRE_WORKING"+input_value).value;
			var flag=0;
		 
			if (mw=="Y")
			{
				var ob=document.getElementById("METRE_INIT_READING"+input_value).value; 
				var cb=document.getElementById("read"+input_value).value; 
				if (ob=="" || ob==0  || cb=="" || cb==0 )
				{
					alert("Opening & Closing meter reading missing! (row no "+input_value+") \n ------------------------------------------------------------------")
			 		flag=1;
			    }
				 
			}
			else
			{
				var nounit=document.getElementById("nounit"+input_value).value;
			 
				if (nounit=="" || nounit=="0")
				{
					alert("No of units missing!(row no "+input_value+") \n------------------------------------")
					flag=1;
				}else
				{
					flag=0;
				}
			}
			 
			if (flag!=1)
			{
						process_code=process_code;	
			url="../../../../../Beneficiary_DCB_ob";
			url+="?command=add";
	     	url+="&process_code="+process_code;
	     	url+="&year="+document.getElementById("year").value;//YEAR
	     	url+="&month="+document.getElementById("month").value;//MONTH
	     	url+="&BENEFICIARY_SNO="+document.getElementById("BENEFICIARY_SNO").value;//BENEFICIARY_SNO
	     	url+="&OFFICE_ID="+document.getElementById("OFFICE_ID").value;//OFFICE_ID
	     	url+="&SUBDIV_OFFICE_ID="+document.getElementById("subdiv").value;//OFFICE_ID
	     	url+="&rowcnt_meter=1";//OFFICE_ID
	     	url+="&selected_item="+input_value;
	     	url+="&BENEFICIARY_TYPE_SNO="+document.getElementById("bentype").value;
	     	var ben_type=document.getElementById("bentype").value;
	     	
	         
	     	
	     	var selsno=document.getElementById("selsno"+input_value).value;
	     	
			var read=document.getElementById("read"+input_value).value;
			url+=("&METRE_SNO"+input_value)+"="+selsno;
  	   	    url+="&METRE_INITIAL_READING"+input_value+"="+document.getElementById("METRE_INIT_READING"+input_value).value;
  	     	url+=("&METRE_CLOSING_READING"+input_value)+"="+read;
		    url+=("&QTY_CONSUMED"+input_value)+"="+document.getElementById("nounit"+input_value).value;;
		    url+=("&PRVMETRE_WORKING"+input_value)+"="+document.getElementById("PRVMETRE_WORKING"+input_value).value;
		   	var aqty=document.getElementById("ALLOTED_QTY"+input_value).value;
		    var ben_type=document.getElementById("bentype").value;	
			  if (ben_type > 6)
			  {
			   	
			    var eqty=(parseInt(read)-parseInt(aqty));
			    
			    if (eqty>0)
			    	eqty=eqty;
			    else
			    	eqty=0;
			  }
			  else
			  {
			 	eqty=0;
				  
			  }
		    
		    var meter="";
		    if (document.getElementById("METRE_FIXED"+input_value).innerHTML=="Yes")
		    	meter="Y";
		    else
		    	meter="N";
		   var meterw="";
	/*
	 *     if (document.getElementById("METRE_WORKING"+input_value).value=="Y")
	    	meterw="Y";
	    else
	    	meterw="N";
	    */
	        url+="&METRE_WORKING"+input_value+"="+document.getElementById("METRE_WORKING"+input_value).value;
  	     	url+="&METRE_FIXED"+input_value+"="+meter;
		    url+=("&ALLOTED_QTY"+input_value)+"="+aqty;
		    url+=("&EXCESS_QTY"+input_value)+"="+eqty;
		    url+="&netunit="+document.getElementById("netunit").value;//netunit
		    
		    var xmlobj=createObject();
		xmlobj.open("GET", url, true);
     	//xmlobj.setRequestHeader("Content-length", data.length);
    	xmlobj.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        //   xmlobj.setRequestHeader("Connection", "close");
	    xmlobj.send(null);
     	 
          xmlobj.onreadystatechange=function()
         { 
        	  
          result_process(xmlobj,"add","9");
         }
			}		
          
          
}
function single_update_result(xmlobj,input_value)
{
	document.getElementById("pr_status").value=1;;
	alert("Insert Successfully \n -----------------------")
 	var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
 	if (input_value==9 )
	{
		
		var rowcnt_meter=document.getElementById("rowcnt_meter").value;
		var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
		var status = bR.getElementsByTagName("status")[0].firstChild.nodeValue;
		var METRE_SNO = bR.getElementsByTagName("METRE_SNO")[0].firstChild.nodeValue;
		var selected_item= bR.getElementsByTagName("selected_item")[0].firstChild.nodeValue;
		document.getElementById("mrow"+selected_item).style.backgroundColor="#FFFFCC"
				
			 
	}	
}

function statuschange(row)
{
	var METRE_WORKING=document.getElementById("METRE_WORKING"+row).value;
	if (METRE_WORKING=='N')
	document.getElementById("read"+row).disabled = true;
	else {		 
	document.getElementById("read"+row).disabled = false;
	document.getElementById("read"+row).readOnly = false;
	document.getElementById("METRE_INIT_READING"+row).readOnly = false;
	
	}
		
	
	 
}
function select(sno,row)
{
	
 
	 var rows=document.getElementById("en_rowcnt").value;
 

	 
	
	 for (i=1;i<=rows;i++)
	 {  
		 	if ((document.getElementById("chdcb"+i).checked) || (document.getElementById("chint"+i).checked))
		 	{
		 		
		 		document.getElementById("selsno").value=sno;
		 		document.getElementById("obsno").value =sno;

		 		
		 		if ( (document.getElementById("chdcb"+i).checked))
		 		{	
		 			
		 			dcbshow(1);
		 			valueSet(3,'report',3);//3->report get ,4 only show dcb
		 		}
		 		if ((document.getElementById("chint"+i).checked))
		 		{	
		 			dcbshow(2);
		 			valueSet(3,'report',4);//3->report get ,4 only show int
		 		}	
		 		
		 	} 
		 
	 }
	 document.getElementById("button2").disabled = false;
	 document.getElementById("button1").disabled = true;

}

function removerowSelect(row)
{
	 
	 var rows=document.getElementById("en_rowcnt").value;
	 for (i=1;i<=rows;i++)
	 {
		 if (i!=row)
		 {
		document.getElementById("chdcb"+i).checked=false;
		document.getElementById("chint"+i).checked=false;
		 }
	 }
}
	 
 
function intremoveSelect(i)
{  
	 
	  
		 document.getElementById("chdcb"+i).checked=false;
		  removerowSelect(i);	 	
}
function dcbremoveSelect(i)
{  
	 
	  
		 document.getElementById("chint"+i).checked=false;
		  removerowSelect(i);	 	
}
function div_sh()
{
	 
	document.getElementById('block').style.visibility = 'hidden';
	document.getElementById('dis').style.visibility = 'hidden';
	
	
}
function ob_sno_set(xmlobj_ob)
{
	 
if (process_code==5 )
{
	 
	 if (xmlobj_ob.readyState==4)
	 {	   
	 	 
		if (xmlobj_ob.status==200)
	    {	 
			
				try
				{
				
				var status = xmlobj_ob.responseXML.getElementsByTagName("status")[0].firstChild.nodeValue;		
				var obsno = xmlobj_ob.responseXML.getElementsByTagName("obsno")[0].firstChild.nodeValue;
				 
				if (status!=0)
				{
					
				 	document.getElementById("dcbbutton").disabled =true;
				 	document.getElementById("obsno").value =obsno;
				 	  
				}
				else			
				{
					
				 	document.getElementById("dcbbutton").disabled =false;
				 	document.getElementById("obsno").value =0;
				}
					
				}catch(e){}
	
	    } 
	 }
	}
}

function int_select(val)
{
	document.getElementById("obsno").value =val;
	document.getElementById("selsno").value=val
	dcbshow(2);
}