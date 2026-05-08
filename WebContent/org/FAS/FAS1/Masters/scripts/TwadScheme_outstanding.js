
//twad js2
///alert('test');
var seq = 0;
var c = 0;
var wind=new Array();
var i=0;
function getTransport() {
	var req = false;
	try {
		req = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e) {
		try {
			req = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			req = false;
		}
	}
	if (!req && typeof XMLHttpRequest != 'undefined') {
		req = new XMLHttpRequest();
	}
	return req;
}

function loadPageInNewWindow(url)
{  
	alert(url);
 var dt=new Date();
  var st="";
  st=dt.getHours()+"_"+dt.getMinutes()+"_"+dt.getSeconds();
 wind[i]=window.open(url,"winName"+st,"status=no,location=no,menubar=no,resizable=1,scrollbars=yes,titlebar=yes,toolbar=no");  
 wind[i].focus();
 i=i+1;
 //alert(i);
  
}


function LoadProgram(val){
		
//alert(val+" ..... ");

	        var url="../../../../../Twad_report_ser?Command=LoadProgram";
	    //  alert(url);
	        var req=getTransport();
	        req.open("GET",url,true);
	        req.onreadystatechange=function()
	        {
	            handle_loadOffice_oly(req,val);
	        }
	        req.send(null);
	         
	

	}

function handle_loadOffice_oly(req,val){
	
	    if(req.readyState==4)
	    {
	 
	     if(req.status==200)
	     {  
	  
	     var baseresponse = req.responseXML.getElementsByTagName("response")[0];
	      
	       
	        var command=baseresponse.getElementsByTagName("command")[0].firstChild.nodeValue;
	       var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	        
	
	      
	        if(flag=="success")
	        { //alert(flag+" >>>  est >>>"+val);
	            var cmbAcc_pro_id = document.getElementById("pro_id"+val);         
	                cmbAcc_pro_id.length=0;
	                var option=document.createElement("OPTION");
	                option.text="Select";
	                cmbAcc_pro_id.appendChild(option);
	            var option_count=baseresponse.getElementsByTagName("ID");   
	           
	            var root = null;
	         
	            for(var i=0;i<option_count.length;i++)
	            {  
	                var option=document.createElement("OPTION");
	                //root = baseresponse.getElementsByTagName("ID")[i];
	                var ID=baseresponse.getElementsByTagName("ID")[i].firstChild.nodeValue;
	                //alert(ID);
	                
	                var IMISCLASSIFICATION=baseresponse.getElementsByTagName("IMISCLASSIFICATION")[i].firstChild.nodeValue;
	                
	                option.text=IMISCLASSIFICATION;
	                option.value=ID;
	                try
	                {   
	                	cmbAcc_pro_id.add(option);
	                }
	                catch(errorObject)
	                {
	                	cmbAcc_pro_id.add(option,null);
	                }   
	                      
	            }
	        }
	     }
	    }
}

function LoadProgram1(val){
		
//alert(val+" ..... ");

	        var url="../../../../../Twad_report_ser?Command=LoadProgram";
	     // alert(url);
	        var req=getTransport();
	        req.open("GET",url,true);
	        req.onreadystatechange=function()
	        {
	            handle_loadOffice_oly1(req,val);
	        }
	        req.send(null);
	       
	}

function handle_loadOffice_oly1(req,val){
	
	    if(req.readyState==4)
	    {
	 
	     if(req.status==200)
	     {  
	  
	     var baseresponse = req.responseXML.getElementsByTagName("response")[0];
	      
	       
	        var command=baseresponse.getElementsByTagName("command")[0].firstChild.nodeValue;
	       var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	        
	
	      
	        if(flag=="success")
	        { 
	        	//alert(flag+" >>>  aaaaaaaest >>>"+val);
	            var cmbAcc_pro_id = document.getElementById("Apro_id"+val);         
	                cmbAcc_pro_id.length=0;
	                var option=document.createElement("OPTION");
	                option.text="Select";
	                cmbAcc_pro_id.appendChild(option);
	            var option_count=baseresponse.getElementsByTagName("ID");   
	           
	            var root = null;
	         
	            for(var i=0;i<option_count.length;i++)
	            {  
	                var option=document.createElement("OPTION");
	                //root = baseresponse.getElementsByTagName("ID")[i];
	                var ID=baseresponse.getElementsByTagName("ID")[i].firstChild.nodeValue;
	                //alert(ID);
	                
	                var IMISCLASSIFICATION=baseresponse.getElementsByTagName("IMISCLASSIFICATION")[i].firstChild.nodeValue;
	                
	                option.text=IMISCLASSIFICATION;
	                option.value=ID;
	                try
	                {   
	                	cmbAcc_pro_id.add(option);
	                }
	                catch(errorObject)
	                {
	                	cmbAcc_pro_id.add(option,null);
	                }   
	                      
	            }
	        }
	     }
	    }
}

function Load_grid(tag){
	//alert(" tag ........"+tag);
	var url="";
	var type="";
	var date_val=document.getElementById("date_val").value;
	//alert("date_val"+date_val);
	var fin_year=document.getElementById("f_year").value;
	if(tag=="gen"){
		type='G';
		var txtAcc_UnitCode=document.getElementById("cmbAcc_UnitCode_gen").value;
		if(txtAcc_UnitCode==""||txtAcc_UnitCode==null){
			txtAcc_UnitCode=3;
		}
				url="../../../../../Twad_report_ser?Command=Load_grid&txtAcc_UnitCode="
			+ txtAcc_UnitCode + "&date_val=" + date_val + "&fin_year="
			+ fin_year +"&type="+type;
		
	}
	if(tag=="sch"){
		type='F';
		var txtAcc_UnitCode=document.getElementById("cmbAcc_UnitCode_sch").value;
		if(txtAcc_UnitCode==""||txtAcc_UnitCode==null){
			txtAcc_UnitCode=3;
		}
				url="../../../../../Twad_report_ser?Command=Load_grid&txtAcc_UnitCode="
			+ txtAcc_UnitCode + "&date_val=" + date_val + "&fin_year="
			+ fin_year +"&type="+type;
		
	}
	if(tag=="brd"){
		type='T';
		var txtAcc_UnitCode=document.getElementById("cmbAcc_UnitCode_brd").value;
		if(txtAcc_UnitCode==""||txtAcc_UnitCode==null){
			txtAcc_UnitCode=3;
		}
				url="../../../../../Twad_report_ser?Command=Load_grid&txtAcc_UnitCode="
			+ txtAcc_UnitCode + "&date_val=" + date_val + "&fin_year="
			+ fin_year +"&type="+type;
		
	}
		//alert(url);
		var req=getTransport();
		req.open("GET",url,true);
		req.onreadystatechange=function()
		{
		    common_process(req);
		}
		req.send(null);
			
}

function common_process(req){
    if(req.readyState==4)
    {
     if(req.status==200)
     {  
     var baseresponse = req.responseXML.getElementsByTagName("response")[0];
       var command=baseresponse.getElementsByTagName("command")[0].firstChild.nodeValue;
       var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        
    //alert(command);

  if(command=="Update"){
	  var type=baseresponse.getElementsByTagName("type")[0].firstChild.nodeValue;
	  //alert(type);
	  if(type=="T"){
        if(flag=="success")
        { 
        	alert('Updated Successfully ....');
        document.getElementById("Asave_id1").value="save";
      AllClear_lia();
     Load_grid('brd');
        }
         else{
        	 alert('Not Updated Successfully ....');
        	
         }
    }else if(type=="F"){
    	   if(flag=="success")
	        { alert('Updated Successfully ....');
	        document.getElementById("save_id1").value="save";
	       // window.location.reload();
	        AllClear(); 
	     Load_grid('sch');
	        }
	         else{
	        	 alert('Not Updated Successfully ....');
	         }
    }
  }
  if(command=="Delete"){
	  var type=baseresponse.getElementsByTagName("type")[0].firstChild.nodeValue;
	  //alert(type);
	  if(type=="T"){
        if(flag=="success")
        { 
        	alert('Deleted Successfully ....');
       // document.getElementById("Asave_id1").value="save";
      //AllClear_lia();
     Load_grid('brd');
        }
         else{
        	 alert('Not Deleted Successfully ....');
        	
         }
    }else if(type=="F"){
    	   if(flag=="success")
	        { alert('Deleted Successfully ....');
	       // document.getElementById("save_id1").value="save";
	       // window.location.reload();
	      //  AllClear(); 
	     Load_grid('sch');
	        }
	         else{
	        	 alert('Not Deleted Successfully ....');
	         }
    }
  }
      if(command=="Board_Liability"){
        if(flag=="success")
        { alert('Saved Successfully ....');
        window.location.reload();
        AllClear_lia();
     Load_grid('brd');
        }
         else{
        	 alert('Not Saved Successfully ....');
         }
    }
      if(command=="Sch_Liability"){

	        if(flag=="success")
	        { alert('Saved Successfully ....');
	      window.location.reload();
	     AllClear();  
	     Load_grid('sch');
	        }
	         else{
	        	 alert('Not Saved Successfully ....');
	         }
	    
      }
      if(command=="generalFunc"){

	        if(flag=="success")
	        { alert('Saved Successfully ....');
	         //removeRow();
	   AllClear_gen();   
	  Load_grid('gen');
	        }
	         else{
	        	 alert('Not Saved Successfully ....');
	         }
	    
      }if(command=="Load_grid"){
    	
    	 // alert('tesdt');
    	
    	 
		  var FREEZED=baseresponse.getElementsByTagName("FREEZED")[0].firstChild.nodeValue;
		  var OUTSTANDING_TYPE=baseresponse.getElementsByTagName("out_type")[0].firstChild.nodeValue;
		//  alert(FREEZED)
		  if(FREEZED=="N"){
			  
			//alert(OUTSTANDING_TYPE);
				 if(OUTSTANDING_TYPE=="G"){
					  var ACCOUNTING_UNIT_ID=baseresponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[0].firstChild.nodeValue;
					  var unit_Desc=baseresponse.getElementsByTagName("unit_Desc")[0].firstChild.nodeValue;
						 var PROGRAM_ID=baseresponse.getElementsByTagName("PROGRAM_ID")[0].firstChild.nodeValue;
						  var program_desc=baseresponse.getElementsByTagName("program_desc")[0].firstChild.nodeValue;
						  
						  var OUTSTANDING_TYPE1=baseresponse.getElementsByTagName("OUTSTANDING_TYPE")[0].firstChild.nodeValue; 
						
					  var BALANCE1=baseresponse.getElementsByTagName("BALANCE1")[0].firstChild.nodeValue; 
					 
					   var BALANCE2=baseresponse.getElementsByTagName("BALANCE2")[0].firstChild.nodeValue;
		    		  var BALANCE3=baseresponse.getElementsByTagName("BALANCE3")[0].firstChild.nodeValue;
		    		  var BALANCE4=baseresponse.getElementsByTagName("BALANCE4")[0].firstChild.nodeValue;
		    		  var BALANCE5=baseresponse.getElementsByTagName("BALANCE5")[0].firstChild.nodeValue;
		    		  var BALANCE6=baseresponse.getElementsByTagName("BALANCE6")[0].firstChild.nodeValue;
		    		 // alert('testhlldfhdhd ... '+BALANCE6);	
		    		  document.getElementById("cont_amt").value=BALANCE1;
		    		 // document.getElementById("cont_amt").disabled="disabled" ;
				 document.getElementById("sup_amt").value=BALANCE2;
				  document.getElementById("EMD_amt").value=BALANCE3;
				  document.getElementById("SD_rec_amt").value=BALANCE4;
				  document.getElementById("Deposit_amt").value=BALANCE5;
				 document.getElementById("Adv_amt").value=BALANCE6;
		      }
		      
				  else if(OUTSTANDING_TYPE=="F"){
				    	
			    	 
			    	  //alert("len....."+len+"  type......."+OUTSTANDING_TYPE);
			    	
			    		  var tbody=document.getElementById("tblList_gen");
			    		  var t = 0,k = 1;

			  			for (t = tbody.rows.length - 1; t >= 0; t--) {
			  				tbody.deleteRow(0);
			  			}
			  			var count=baseresponse.getElementsByTagName("YEAR");
			  			 var len=count.length;
			    	  for(var i=0;i<len;i++){
			    		  var tbody=document.getElementById("tblList_gen");
			    		  var ACCOUNTING_UNIT_ID=baseresponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[i].firstChild.nodeValue;
			    		  var unit_Desc=baseresponse.getElementsByTagName("unit_Desc")[i].firstChild.nodeValue;
			    		  var PROGRAM_ID=baseresponse.getElementsByTagName("PROGRAM_ID")[i].firstChild.nodeValue;
			    		  var program_desc=baseresponse.getElementsByTagName("program_desc")[i].firstChild.nodeValue;
			    		  var INDIVIDUAL_SCHEME_NAME=baseresponse.getElementsByTagName("INDIVIDUAL_SCHEME_NAME")[i].firstChild.nodeValue;
			    		  var AMT_RECEIVED_UPTO_MAR2011=baseresponse.getElementsByTagName("AMT_RECEIVED_UPTO_MAR2011")[i].firstChild.nodeValue;
			    		  var EXP_BOOKED_UPTO_MAR2011=baseresponse.getElementsByTagName("EXP_BOOKED_UPTO_MAR2011")[i].firstChild.nodeValue;
			    		  var EXP_BOOKED_UPTO_CUR_MAR=baseresponse.getElementsByTagName("EXP_BOOKED_UPTO_CUR_MAR")[i].firstChild.nodeValue;
			    		  var AMT_RECEIVED_UPTO_CUR_MAR=baseresponse.getElementsByTagName("AMT_RECEIVED_UPTO_CUR_MAR")[i].firstChild.nodeValue;
			    		  var BALANCE=baseresponse.getElementsByTagName("BALANCE")[i].firstChild.nodeValue;
			    		  var SLNO=baseresponse.getElementsByTagName("SLNO")[i].firstChild.nodeValue;
			    		  var OUTSTANDING_TYPE=baseresponse.getElementsByTagName("OUTSTANDING_TYPE")[i].firstChild.nodeValue;
			    		  var EXP_ACTUALLY_PAID=baseresponse.getElementsByTagName("EXP_ACTUALLY_PAID")[i].firstChild.nodeValue;
			    		  var LOCAL_BODY=baseresponse.getElementsByTagName("LOCAL_BODY")[i].firstChild.nodeValue;//alert(LOCAL_BODY);
			    		  var LB_TYPE=baseresponse.getElementsByTagName("LB_TYPE")[i].firstChild.nodeValue;
			    		  var DISTRICT_ID=baseresponse.getElementsByTagName("DISTRICT_ID")[i].firstChild.nodeValue;
			    		  var TIE_UP_AMT=baseresponse.getElementsByTagName("TIE_UP_AMT")[i].firstChild.nodeValue;
			    		  var mycurrent_row = document.createElement("TR");
							var cell1 = document.createElement("TD");
							var cell2 = document.createElement("TD");
							var cell3 = document.createElement("TD");
							var cell4 = document.createElement("TD");
							var cell5 = document.createElement("TD");
							var cell6 = document.createElement("TD");
							var cell7=document.createElement("TD");
							var cell8=document.createElement("TD");
							var cell9=document.createElement("TD");
							var cell10=document.createElement("TD");
							  var anc=document.createElement("A");
					            var url="javascript:LoadTextVal("+SLNO+","+PROGRAM_ID+","+BALANCE+","+AMT_RECEIVED_UPTO_MAR2011+","+EXP_BOOKED_UPTO_MAR2011+","+EXP_BOOKED_UPTO_CUR_MAR+","+AMT_RECEIVED_UPTO_CUR_MAR+","+EXP_ACTUALLY_PAID+",'"+OUTSTANDING_TYPE+"','"+INDIVIDUAL_SCHEME_NAME+"',"+TIE_UP_AMT+","+DISTRICT_ID+",'"+LOCAL_BODY+"')";
					            anc.href=url;
					        	var Category_ID_code_text = document.createTextNode("EDIT");
					            anc.appendChild(Category_ID_code_text);
					            cell1.appendChild(anc);
					            mycurrent_row.appendChild(cell1);
							
							var PROGRAM_ID_code = document.createElement("input");
							PROGRAM_ID_code.type = "hidden";
							PROGRAM_ID_code.name = "PROGRAM_ID" + seq;
							PROGRAM_ID_code.value = PROGRAM_ID;
							cell2.appendChild(PROGRAM_ID_code);
							var PROGRAM_ID_codetext = document.createTextNode(program_desc);			
							cell2.appendChild(PROGRAM_ID_codetext);
							mycurrent_row.appendChild(cell2);
							
							var SCHEME_NAME_code = document.createElement("input");
							SCHEME_NAME_code.type = "hidden";
							SCHEME_NAME_code.name = "INDIVIDUAL_SCHEME_NAME" + seq;
							SCHEME_NAME_code.value = INDIVIDUAL_SCHEME_NAME;
							cell9.appendChild(SCHEME_NAME_code);
							var SCHEME_NAME_text = document.createTextNode(INDIVIDUAL_SCHEME_NAME);			
							cell9.appendChild(SCHEME_NAME_text);
							mycurrent_row.appendChild(cell9);
							
							var BALANCEdesc = document.createElement("input");
							BALANCEdesc.type = "hidden";
							BALANCEdesc.name = "LOCAL_BODY" + seq;
							BALANCEdesc.value = LOCAL_BODY;
							cell6.appendChild(BALANCEdesc);
							var BALANCEdesctext = document.createTextNode(LOCAL_BODY);
							cell6.appendChild(BALANCEdesctext);
							mycurrent_row.appendChild(cell6);
							
							var tie_code = document.createElement("input");
							tie_code.type = "hidden";
							tie_code.name = "TIE_UP_AMT" + seq;
							tie_code.value = TIE_UP_AMT;
							cell8.appendChild(tie_code);
							var tie_codetext = document.createTextNode(TIE_UP_AMT);			
							cell8.appendChild(tie_codetext);
							mycurrent_row.appendChild(cell8);
							
							var received_code = document.createElement("input");
							received_code.type = "hidden";
							received_code.name = "rec_amt" + seq;
							received_code.value = AMT_RECEIVED_UPTO_CUR_MAR;
							cell3.appendChild(received_code);
							var received_codetext = document.createTextNode(AMT_RECEIVED_UPTO_CUR_MAR);			
							cell3.appendChild(received_codetext);
							mycurrent_row.appendChild(cell3);
							
							var booked_code = document.createElement("input");
							booked_code.type = "hidden";
							booked_code.name = "booked_amt" + seq;
							booked_code.value = EXP_BOOKED_UPTO_MAR2011;
							cell4.appendChild(booked_code);
							var booked_text = document.createTextNode(EXP_BOOKED_UPTO_MAR2011);			
							cell4.appendChild(booked_text);
							mycurrent_row.appendChild(cell4);
							
							var EXP_BOOKED_UPTO_CUR_MAR_text = document.createElement("input");
							EXP_BOOKED_UPTO_CUR_MAR_text.type = "hidden";
							EXP_BOOKED_UPTO_CUR_MAR_text.name = "incur" + seq;
							EXP_BOOKED_UPTO_CUR_MAR_text.value = EXP_BOOKED_UPTO_CUR_MAR;
							cell10.appendChild(EXP_BOOKED_UPTO_CUR_MAR_text);
							var EXP_BOOKED_UPTO_CUR_MAR_node = document.createTextNode(EXP_BOOKED_UPTO_CUR_MAR);
							cell10.appendChild(EXP_BOOKED_UPTO_CUR_MAR_node);
							mycurrent_row.appendChild(cell10);
							tbody.appendChild(mycurrent_row);
							
							
							var EXP_ACTUALLY_PAID_text = document.createElement("input");
							EXP_ACTUALLY_PAID_text.type = "hidden";
							EXP_ACTUALLY_PAID_text.name = "EXP_ACTUALLY_PAID" + seq;
							EXP_ACTUALLY_PAID_text.value = EXP_ACTUALLY_PAID;
							cell5.appendChild(EXP_ACTUALLY_PAID_text);
							var EXP_ACTUALLY_PAID_node = document.createTextNode(EXP_ACTUALLY_PAID);
							cell5.appendChild(EXP_ACTUALLY_PAID_node);
							mycurrent_row.appendChild(cell5);
							tbody.appendChild(mycurrent_row);

						/*	var BALANCEdesc = document.createElement("input");
							BALANCEdesc.type = "hidden";
							BALANCEdesc.name = "BALANCE" + seq;
							BALANCEdesc.value = BALANCE;
							cell6.appendChild(BALANCEdesc);
							var BALANCEdesctext = document.createTextNode(BALANCE);
							cell6.appendChild(BALANCEdesctext);
							mycurrent_row.appendChild(cell6);*/
							
							  var anc=document.createElement("A");
					            var url="javascript:DelVal("+SLNO+","+PROGRAM_ID+","+BALANCE+","+AMT_RECEIVED_UPTO_MAR2011+","+EXP_BOOKED_UPTO_MAR2011+","+EXP_BOOKED_UPTO_CUR_MAR+","+AMT_RECEIVED_UPTO_CUR_MAR+","+EXP_ACTUALLY_PAID+",'"+OUTSTANDING_TYPE+"','"+INDIVIDUAL_SCHEME_NAME+"',"+TIE_UP_AMT+","+DISTRICT_ID+")";
					            anc.href=url;
					        	var del_text = document.createTextNode("DELETE");
					            anc.appendChild(del_text);
					            cell7.appendChild(anc);
					            mycurrent_row.appendChild(cell7);
							
							tbody.appendChild(mycurrent_row);


			    	  }
			    	 }else if(OUTSTANDING_TYPE=="T")
			    		 {

				    	
				    	//  alert("len....."+len+"  type......."+OUTSTANDING_TYPE);
				    	
				    		  var tbody=document.getElementById("tblList_lia");
				    		  var t = 0,k = 1;

				  			for (t = tbody.rows.length - 1; t >= 0; t--) {
				  				tbody.deleteRow(0);
				  			}
				  		  var count=baseresponse.getElementsByTagName("YEAR");
				    	  var len=count.length;
				    	  for(var i=0;i<len;i++){
				    		  var tbody=document.getElementById("tblList_lia");
				    		  var ACCOUNTING_UNIT_ID=baseresponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[i].firstChild.nodeValue;
				    		  var unit_Desc=baseresponse.getElementsByTagName("unit_Desc")[i].firstChild.nodeValue;
				    		  var PROGRAM_ID=baseresponse.getElementsByTagName("PROGRAM_ID")[i].firstChild.nodeValue;
				    		  var program_desc=baseresponse.getElementsByTagName("program_desc")[i].firstChild.nodeValue;
				    		  var INDIVIDUAL_SCHEME_NAME=baseresponse.getElementsByTagName("INDIVIDUAL_SCHEME_NAME")[i].firstChild.nodeValue;
				    		  var AMT_RECEIVED_UPTO_MAR2011=baseresponse.getElementsByTagName("AMT_RECEIVED_UPTO_MAR2011")[i].firstChild.nodeValue;
				    		  var EXP_BOOKED_UPTO_MAR2011=baseresponse.getElementsByTagName("EXP_BOOKED_UPTO_MAR2011")[i].firstChild.nodeValue;
				    		  var EXP_BOOKED_UPTO_CUR_MAR=baseresponse.getElementsByTagName("EXP_BOOKED_UPTO_CUR_MAR")[i].firstChild.nodeValue;
				    		  var AMT_RECEIVED_UPTO_CUR_MAR=baseresponse.getElementsByTagName("AMT_RECEIVED_UPTO_CUR_MAR")[i].firstChild.nodeValue;
				    		  var BALANCE=baseresponse.getElementsByTagName("BALANCE")[i].firstChild.nodeValue;
				    		  var SLNO=baseresponse.getElementsByTagName("SLNO")[i].firstChild.nodeValue;
				    		  var OUTSTANDING_TYPE=baseresponse.getElementsByTagName("OUTSTANDING_TYPE")[i].firstChild.nodeValue;
				    		  var EXP_ACTUALLY_PAID=baseresponse.getElementsByTagName("EXP_ACTUALLY_PAID")[i].firstChild.nodeValue;
				    		  var LOCAL_BODY=baseresponse.getElementsByTagName("LOCAL_BODY")[i].firstChild.nodeValue;
				    		  var TIE_UP_AMT=baseresponse.getElementsByTagName("TIE_UP_AMT")[i].firstChild.nodeValue;
				    		  var DISTRICT_ID=baseresponse.getElementsByTagName("DISTRICT_ID")[i].firstChild.nodeValue;	 
				    		  var mycurrent_row = document.createElement("TR");
								var cell1 = document.createElement("TD");
								var cell2 = document.createElement("TD");
								var cell3 = document.createElement("TD");
								var cell4 = document.createElement("TD");
								var cell5 = document.createElement("TD");
								var cell6 = document.createElement("TD");
								var cell7 = document.createElement("TD");
								var cell8 = document.createElement("TD");
								var cell9 = document.createElement("TD");
								var cell10 = document.createElement("TD");
								  var anc=document.createElement("A");
						            var url="javascript:LoadTextVal("+SLNO+","+PROGRAM_ID+","+BALANCE+","+AMT_RECEIVED_UPTO_MAR2011+","+EXP_BOOKED_UPTO_MAR2011+","+EXP_BOOKED_UPTO_CUR_MAR+","+AMT_RECEIVED_UPTO_CUR_MAR+","+EXP_ACTUALLY_PAID+",'"+OUTSTANDING_TYPE+"','"+INDIVIDUAL_SCHEME_NAME+"',"+TIE_UP_AMT+","+DISTRICT_ID+",'"+LOCAL_BODY+"')";
						            anc.href=url;
						        	var Category_ID_code_text = document.createTextNode("EDIT");
						            anc.appendChild(Category_ID_code_text);
						            cell1.appendChild(anc);
						            mycurrent_row.appendChild(cell1);
								
								var PROGRAM_ID_code = document.createElement("input");
								PROGRAM_ID_code.type = "hidden";
								PROGRAM_ID_code.name = "PROGRAM_ID" + seq;
								PROGRAM_ID_code.value = PROGRAM_ID;
								cell2.appendChild(PROGRAM_ID_code);
								var PROGRAM_ID_codetext = document.createTextNode(program_desc);			
								cell2.appendChild(PROGRAM_ID_codetext);
								mycurrent_row.appendChild(cell2);
								
								var SCHEME_NAME_code = document.createElement("input");
								SCHEME_NAME_code.type = "hidden";
								SCHEME_NAME_code.name = "INDIVIDUAL_SCHEME_NAME" + seq;
								SCHEME_NAME_code.value = INDIVIDUAL_SCHEME_NAME;
								cell9.appendChild(SCHEME_NAME_code);
								var SCHEME_NAME_text = document.createTextNode(INDIVIDUAL_SCHEME_NAME);			
								cell9.appendChild(SCHEME_NAME_text);
								mycurrent_row.appendChild(cell9);
								
								var BALANCEdesc = document.createElement("input");
								BALANCEdesc.type = "hidden";
								BALANCEdesc.name = "LOCAL_BODY" + seq;
								BALANCEdesc.value = LOCAL_BODY;
								cell6.appendChild(BALANCEdesc);
								var BALANCEdesctext = document.createTextNode(LOCAL_BODY);
								cell6.appendChild(BALANCEdesctext);
								mycurrent_row.appendChild(cell6);
								
								var tie_code = document.createElement("input");
								tie_code.type = "hidden";
								tie_code.name = "TIE_UP_AMT" + seq;
								tie_code.value = TIE_UP_AMT;
								cell8.appendChild(tie_code);
								var tie_codetext = document.createTextNode(TIE_UP_AMT);			
								cell8.appendChild(tie_codetext);
								mycurrent_row.appendChild(cell8);
								
								var received_code = document.createElement("input");
								received_code.type = "hidden";
								received_code.name = "rec_amt" + seq;
								received_code.value = AMT_RECEIVED_UPTO_CUR_MAR;
								cell3.appendChild(received_code);
								var received_codetext = document.createTextNode(AMT_RECEIVED_UPTO_CUR_MAR);			
								cell3.appendChild(received_codetext);
								mycurrent_row.appendChild(cell3);
								
								var booked_code = document.createElement("input");
								booked_code.type = "hidden";
								booked_code.name = "booked_amt" + seq;
								booked_code.value = EXP_BOOKED_UPTO_MAR2011;
								cell4.appendChild(booked_code);
								var booked_text = document.createTextNode(EXP_BOOKED_UPTO_MAR2011);			
								cell4.appendChild(booked_text);
								mycurrent_row.appendChild(cell4);
								
								var EXP_BOOKED_UPTO_CUR_MAR_text = document.createElement("input");
								EXP_BOOKED_UPTO_CUR_MAR_text.type = "hidden";
								EXP_BOOKED_UPTO_CUR_MAR_text.name = "incur" + seq;
								EXP_BOOKED_UPTO_CUR_MAR_text.value = EXP_BOOKED_UPTO_CUR_MAR;
								cell10.appendChild(EXP_BOOKED_UPTO_CUR_MAR_text);
								var EXP_BOOKED_UPTO_CUR_MAR_node = document.createTextNode(EXP_BOOKED_UPTO_CUR_MAR);
								cell10.appendChild(EXP_BOOKED_UPTO_CUR_MAR_node);
								mycurrent_row.appendChild(cell10);
								tbody.appendChild(mycurrent_row);
								
								var EXP_ACTUALLY_PAID_text = document.createElement("input");
								EXP_ACTUALLY_PAID_text.type = "hidden";
								EXP_ACTUALLY_PAID_text.name = "BALANCE" + seq;
								EXP_ACTUALLY_PAID_text.value = EXP_ACTUALLY_PAID;
								cell5.appendChild(EXP_ACTUALLY_PAID_text);
								var EXP_ACTUALLY_PAID_node = document.createTextNode(EXP_ACTUALLY_PAID);
								cell5.appendChild(EXP_ACTUALLY_PAID_node);
								mycurrent_row.appendChild(cell5);
								tbody.appendChild(mycurrent_row);

							
								
								  var anc=document.createElement("A");
						            var url="javascript:DelVal("+SLNO+","+PROGRAM_ID+","+BALANCE+","+AMT_RECEIVED_UPTO_MAR2011+","+EXP_BOOKED_UPTO_MAR2011+","+EXP_BOOKED_UPTO_CUR_MAR+","+AMT_RECEIVED_UPTO_CUR_MAR+","+EXP_ACTUALLY_PAID+",'"+OUTSTANDING_TYPE+"','"+INDIVIDUAL_SCHEME_NAME+"',"+TIE_UP_AMT+","+DISTRICT_ID+")";
						            anc.href=url;
						        	var del_text = document.createTextNode("DELETE");
						            anc.appendChild(del_text);
						            cell7.appendChild(anc);
						            mycurrent_row.appendChild(cell7);
								
								tbody.appendChild(mycurrent_row);


				    	  }
				    	 
			    		 }
			    	 else{
			    		 alert('error ..... ');
			    		 }
		  }
		  else if(FREEZED=="Y" || FREEZED=="R")
{
				 
				
				 if(OUTSTANDING_TYPE=="G"){
					 document.getElementById("ge_onsubmit1").style.display="none";
					  var ACCOUNTING_UNIT_ID=baseresponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[0].firstChild.nodeValue;
		    		  var unit_Desc=baseresponse.getElementsByTagName("unit_Desc")[0].firstChild.nodeValue;
		    		  
					 var PROGRAM_ID=baseresponse.getElementsByTagName("PROGRAM_ID")[0].firstChild.nodeValue;
					  var program_desc=baseresponse.getElementsByTagName("program_desc")[0].firstChild.nodeValue;
					  
					//  var OUTSTANDING_TYPE1=baseresponse.getElementsByTagName("OUTSTANDING_TYPE")[0].firstChild.nodeValue; 
					
					  var BALANCE1=baseresponse.getElementsByTagName("BALANCE1")[0].firstChild.nodeValue; 
					 
					   var BALANCE2=baseresponse.getElementsByTagName("BALANCE2")[0].firstChild.nodeValue;
		    		  var BALANCE3=baseresponse.getElementsByTagName("BALANCE3")[0].firstChild.nodeValue;
		    		  var BALANCE4=baseresponse.getElementsByTagName("BALANCE4")[0].firstChild.nodeValue;
		    		  var BALANCE5=baseresponse.getElementsByTagName("BALANCE5")[0].firstChild.nodeValue;
		    		  var BALANCE6=baseresponse.getElementsByTagName("BALANCE6")[0].firstChild.nodeValue;
		    		
		    		  document.getElementById("cont_amt").value=BALANCE1;
		    		  document.getElementById("cont_amt").disabled=true;
				 document.getElementById("sup_amt").value=BALANCE2;
				 document.getElementById("sup_amt").disabled=true;
				  document.getElementById("EMD_amt").value=BALANCE3;
				  document.getElementById("EMD_amt").disabled=true;
				  document.getElementById("SD_rec_amt").value=BALANCE4;
				  document.getElementById("SD_rec_amt").disabled=true;
				  document.getElementById("Deposit_amt").value=BALANCE5;
				  document.getElementById("Deposit_amt").disabled=true;
				 document.getElementById("Adv_amt").value=BALANCE6;
				 document.getElementById("Adv_amt").disabled=true;
				
		      }
		      
				  else if(OUTSTANDING_TYPE=="F"){
					  document.getElementById("save_id1").style.display="none";
					  document.getElementById("onsubmit1_sch").style.display="none";
				    	document.getElementById("unFrz_sch").style.display="none";
				    	document.getElementById("frz_sch").style.display="block";
				    	
				    	
			    	
			    		  var tbody=document.getElementById("tblList_gen1");
			    		  var t = 0,k = 1;

			  			for (t = tbody.rows.length - 1; t >= 0; t--) {
			  				tbody.deleteRow(0);
			  			}
			  		  var count=baseresponse.getElementsByTagName("YEAR");
			  		  var len=count.length;
				    //	alert("len....."+len+"  type......."+OUTSTANDING_TYPE);
			    	  for(var i=0;i<len;i++){
			    		  var tbody=document.getElementById("tblList_gen1");
			    		  var ACCOUNTING_UNIT_ID=baseresponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[i].firstChild.nodeValue;
			    		  var unit_Desc=baseresponse.getElementsByTagName("unit_Desc")[i].firstChild.nodeValue;
			    		  var PROGRAM_ID=baseresponse.getElementsByTagName("PROGRAM_ID")[i].firstChild.nodeValue;
			    		  var program_desc=baseresponse.getElementsByTagName("program_desc")[i].firstChild.nodeValue;
			    		  var INDIVIDUAL_SCHEME_NAME=baseresponse.getElementsByTagName("INDIVIDUAL_SCHEME_NAME")[i].firstChild.nodeValue;
			    		  var AMT_RECEIVED_UPTO_MAR2011=baseresponse.getElementsByTagName("AMT_RECEIVED_UPTO_MAR2011")[i].firstChild.nodeValue;
			    		  var EXP_BOOKED_UPTO_MAR2011=baseresponse.getElementsByTagName("EXP_BOOKED_UPTO_MAR2011")[i].firstChild.nodeValue;
			    		  var EXP_BOOKED_UPTO_CUR_MAR=baseresponse.getElementsByTagName("EXP_BOOKED_UPTO_CUR_MAR")[i].firstChild.nodeValue;
			    		  var AMT_RECEIVED_UPTO_CUR_MAR=baseresponse.getElementsByTagName("AMT_RECEIVED_UPTO_CUR_MAR")[i].firstChild.nodeValue;
			    		  var BALANCE=baseresponse.getElementsByTagName("BALANCE")[i].firstChild.nodeValue;
			    		  var SLNO=baseresponse.getElementsByTagName("SLNO")[i].firstChild.nodeValue;
			    		  var OUTSTANDING_TYPE1=baseresponse.getElementsByTagName("OUTSTANDING_TYPE")[i].firstChild.nodeValue;
			    		  var EXP_ACTUALLY_PAID=baseresponse.getElementsByTagName("EXP_ACTUALLY_PAID")[i].firstChild.nodeValue;
			    		  var LOCAL_BODY=baseresponse.getElementsByTagName("LOCAL_BODY")[i].firstChild.nodeValue;
			    		  var TIE_UP_AMT=baseresponse.getElementsByTagName("TIE_UP_AMT")[i].firstChild.nodeValue;
					   
			    		  var mycurrent_row_fz = document.createElement("TR");
							var cell1 = document.createElement("TD");
							var cell2 = document.createElement("TD");
							var cell3 = document.createElement("TD");
							var cell4 = document.createElement("TD");
							var cell5 = document.createElement("TD");
							var cell6 = document.createElement("TD");
							var cell7=document.createElement("TD");
							var cell8=document.createElement("TD");
							var cell9=document.createElement("TD");
							var cell10=document.createElement("TD");
							
							var PROGRAM_ID_code = document.createElement("input");
							PROGRAM_ID_code.type = "hidden";
							PROGRAM_ID_code.name = "PROGRAM_ID" + seq;
							PROGRAM_ID_code.value = PROGRAM_ID;
							cell2.appendChild(PROGRAM_ID_code);
							var PROGRAM_ID_codetext = document.createTextNode(program_desc);			
							cell2.appendChild(PROGRAM_ID_codetext);
							mycurrent_row_fz.appendChild(cell2);
							
							var SCHEME_NAME_code = document.createElement("input");
							SCHEME_NAME_code.type = "hidden";
							SCHEME_NAME_code.name = "INDIVIDUAL_SCHEME_NAME" + seq;
							SCHEME_NAME_code.value = INDIVIDUAL_SCHEME_NAME;
							cell9.appendChild(SCHEME_NAME_code);
							var SCHEME_NAME_text = document.createTextNode(INDIVIDUAL_SCHEME_NAME);			
							cell9.appendChild(SCHEME_NAME_text);
							mycurrent_row_fz.appendChild(cell9);
							
							var BALANCEdesc = document.createElement("input");
							BALANCEdesc.type = "hidden";
							BALANCEdesc.name = "LOCAL_BODY" + seq;
							BALANCEdesc.value = LOCAL_BODY;
							cell6.appendChild(BALANCEdesc);
							var BALANCEdesctext = document.createTextNode(LOCAL_BODY);
							cell6.appendChild(BALANCEdesctext);
							mycurrent_row_fz.appendChild(cell6);
							
							var tie_code = document.createElement("input");
							tie_code.type = "hidden";
							tie_code.name = "TIE_UP_AMT" + seq;
							tie_code.value = TIE_UP_AMT;
							cell8.appendChild(tie_code);
							var tie_codetext = document.createTextNode(TIE_UP_AMT);			
							cell8.appendChild(tie_codetext);
							mycurrent_row_fz.appendChild(cell8);
							
							var received_code = document.createElement("input");
							received_code.type = "hidden";
							received_code.name = "rec_amt" + seq;
							received_code.value = AMT_RECEIVED_UPTO_CUR_MAR;
							cell3.appendChild(received_code);
							var received_codetext = document.createTextNode(AMT_RECEIVED_UPTO_CUR_MAR);			
							cell3.appendChild(received_codetext);
							mycurrent_row_fz.appendChild(cell3);
							
							var booked_code = document.createElement("input");
							booked_code.type = "hidden";
							booked_code.name = "booked_amt" + seq;
							booked_code.value = EXP_BOOKED_UPTO_MAR2011;
							cell4.appendChild(booked_code);
							var booked_text = document.createTextNode(EXP_BOOKED_UPTO_MAR2011);			
							cell4.appendChild(booked_text);
							mycurrent_row_fz.appendChild(cell4);
							
							var EXP_BOOKED_UPTO_CUR_MAR_text = document.createElement("input");
							EXP_BOOKED_UPTO_CUR_MAR_text.type = "hidden";
							EXP_BOOKED_UPTO_CUR_MAR_text.name = "incur" + seq;
							EXP_BOOKED_UPTO_CUR_MAR_text.value = EXP_BOOKED_UPTO_CUR_MAR;
							cell10.appendChild(EXP_BOOKED_UPTO_CUR_MAR_text);
							var EXP_BOOKED_UPTO_CUR_MAR_node = document.createTextNode(EXP_BOOKED_UPTO_CUR_MAR);
							cell10.appendChild(EXP_BOOKED_UPTO_CUR_MAR_node);
							mycurrent_row_fz.appendChild(cell10);
							
							
							
							var EXP_ACTUALLY_PAID_text = document.createElement("input");
							EXP_ACTUALLY_PAID_text.type = "hidden";
							EXP_ACTUALLY_PAID_text.name = "EXP_ACTUALLY_PAID" + seq;
							EXP_ACTUALLY_PAID_text.value = EXP_ACTUALLY_PAID;
							cell5.appendChild(EXP_ACTUALLY_PAID_text);
							var EXP_ACTUALLY_PAID_node = document.createTextNode(EXP_ACTUALLY_PAID);
							cell5.appendChild(EXP_ACTUALLY_PAID_node);
							mycurrent_row_fz.appendChild(cell5);
							tbody.appendChild(mycurrent_row_fz);
							
							seq++;


			    	  }
			    	 }else if(OUTSTANDING_TYPE=="T")
			    		 {
			    		 document.getElementById("Asave_id1").style.display="none";
						 document.getElementById("brd_onsubmit1").style.display="none";
			    		 document.getElementById("unfrz_brd").style.display="none";
					    	document.getElementById("frz_brd").style.display="block";
				    	
				    	
				    		  var tbody=document.getElementById("tblList_lia1");
				    		  var t = 0,k = 1;

				  			for (t = tbody.rows.length - 1; t >= 0; t--) {
				  				tbody.deleteRow(0);
				  			}
				  		  var count=baseresponse.getElementsByTagName("YEAR");
				  		  var len=count.length;
					    	
				    	  for(var i=0;i<len;i++){
				    		  var tbody=document.getElementById("tblList_lia1");
				    		  var ACCOUNTING_UNIT_ID=baseresponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[i].firstChild.nodeValue;
				    		  var unit_Desc=baseresponse.getElementsByTagName("unit_Desc")[i].firstChild.nodeValue;
				    		  var PROGRAM_ID=baseresponse.getElementsByTagName("PROGRAM_ID")[i].firstChild.nodeValue;
				    		  var program_desc=baseresponse.getElementsByTagName("program_desc")[i].firstChild.nodeValue;
				    		  var INDIVIDUAL_SCHEME_NAME=baseresponse.getElementsByTagName("INDIVIDUAL_SCHEME_NAME")[i].firstChild.nodeValue;
				    		  var AMT_RECEIVED_UPTO_MAR2011=baseresponse.getElementsByTagName("AMT_RECEIVED_UPTO_MAR2011")[i].firstChild.nodeValue;
				    		  var EXP_BOOKED_UPTO_MAR2011=baseresponse.getElementsByTagName("EXP_BOOKED_UPTO_MAR2011")[i].firstChild.nodeValue;
				    		  var EXP_BOOKED_UPTO_CUR_MAR=baseresponse.getElementsByTagName("EXP_BOOKED_UPTO_CUR_MAR")[i].firstChild.nodeValue;
				    		  var AMT_RECEIVED_UPTO_CUR_MAR=baseresponse.getElementsByTagName("AMT_RECEIVED_UPTO_CUR_MAR")[i].firstChild.nodeValue;
				    		  var BALANCE=baseresponse.getElementsByTagName("BALANCE")[i].firstChild.nodeValue;
				    		  var SLNO=baseresponse.getElementsByTagName("SLNO")[i].firstChild.nodeValue;
				    		  var OUTSTANDING_TYPE1=baseresponse.getElementsByTagName("OUTSTANDING_TYPE")[i].firstChild.nodeValue;
				    		  var EXP_ACTUALLY_PAID=baseresponse.getElementsByTagName("EXP_ACTUALLY_PAID")[i].firstChild.nodeValue;
				    		  var LOCAL_BODY=baseresponse.getElementsByTagName("LOCAL_BODY")[i].firstChild.nodeValue;
				    		  var TIE_UP_AMT=baseresponse.getElementsByTagName("TIE_UP_AMT")[i].firstChild.nodeValue;
						    
				    		  var mycurrent_row = document.createElement("TR");
								var cell1 = document.createElement("TD");
								var cell2 = document.createElement("TD");
								var cell3 = document.createElement("TD");
								var cell4 = document.createElement("TD");
								var cell5 = document.createElement("TD");
								var cell6 = document.createElement("TD");
								var cell7 = document.createElement("TD");
								var cell8 = document.createElement("TD");
								var cell8=document.createElement("TD");
								var cell9=document.createElement("TD");
								var cell10=document.createElement("TD");
								
								var PROGRAM_ID_code = document.createElement("input");
								PROGRAM_ID_code.type = "hidden";
								PROGRAM_ID_code.name = "PROGRAM_ID" + seq;
								PROGRAM_ID_code.value = PROGRAM_ID;
								cell2.appendChild(PROGRAM_ID_code);
								var PROGRAM_ID_codetext = document.createTextNode(program_desc);			
								cell2.appendChild(PROGRAM_ID_codetext);
								mycurrent_row.appendChild(cell2);
								
								var SCHEME_NAME_code = document.createElement("input");
								SCHEME_NAME_code.type = "hidden";
								SCHEME_NAME_code.name = "INDIVIDUAL_SCHEME_NAME" + seq;
								SCHEME_NAME_code.value = INDIVIDUAL_SCHEME_NAME;
								cell9.appendChild(SCHEME_NAME_code);
								var SCHEME_NAME_text = document.createTextNode(INDIVIDUAL_SCHEME_NAME);			
								cell9.appendChild(SCHEME_NAME_text);
								mycurrent_row.appendChild(cell9);
								
								var BALANCEdesc = document.createElement("input");
								BALANCEdesc.type = "hidden";
								BALANCEdesc.name = "LOCAL_BODY" + seq;
								BALANCEdesc.value = LOCAL_BODY;
								cell6.appendChild(BALANCEdesc);
								var BALANCEdesctext = document.createTextNode(LOCAL_BODY);
								cell6.appendChild(BALANCEdesctext);
								mycurrent_row.appendChild(cell6);
								
								var tie_code = document.createElement("input");
								tie_code.type = "hidden";
								tie_code.name = "TIE_UP_AMT" + seq;
								tie_code.value = TIE_UP_AMT;
								cell8.appendChild(tie_code);
								var tie_codetext = document.createTextNode(TIE_UP_AMT);			
								cell8.appendChild(tie_codetext);
								mycurrent_row.appendChild(cell8);
								
								var received_code = document.createElement("input");
								received_code.type = "hidden";
								received_code.name = "rec_amt" + seq;
								received_code.value = AMT_RECEIVED_UPTO_CUR_MAR;
								cell3.appendChild(received_code);
								var received_codetext = document.createTextNode(AMT_RECEIVED_UPTO_CUR_MAR);			
								cell3.appendChild(received_codetext);
								mycurrent_row.appendChild(cell3);
								
								var booked_code = document.createElement("input");
								booked_code.type = "hidden";
								booked_code.name = "booked_amt" + seq;
								booked_code.value = EXP_BOOKED_UPTO_MAR2011;
								cell4.appendChild(booked_code);
								var booked_text = document.createTextNode(EXP_BOOKED_UPTO_MAR2011);			
								cell4.appendChild(booked_text);
								mycurrent_row.appendChild(cell4);
								
								var EXP_BOOKED_UPTO_CUR_MAR_text = document.createElement("input");
								EXP_BOOKED_UPTO_CUR_MAR_text.type = "hidden";
								EXP_BOOKED_UPTO_CUR_MAR_text.name = "incur" + seq;
								EXP_BOOKED_UPTO_CUR_MAR_text.value = EXP_BOOKED_UPTO_CUR_MAR;
								cell10.appendChild(EXP_BOOKED_UPTO_CUR_MAR_text);
								var EXP_BOOKED_UPTO_CUR_MAR_node = document.createTextNode(EXP_BOOKED_UPTO_CUR_MAR);
								cell10.appendChild(EXP_BOOKED_UPTO_CUR_MAR_node);
								mycurrent_row.appendChild(cell10);
								
								
								var EXP_ACTUALLY_PAID_text = document.createElement("input");
								EXP_ACTUALLY_PAID_text.type = "hidden";
								EXP_ACTUALLY_PAID_text.name = "BALANCE" + seq;
								EXP_ACTUALLY_PAID_text.value = EXP_ACTUALLY_PAID;
								cell5.appendChild(EXP_ACTUALLY_PAID_text);
								var EXP_ACTUALLY_PAID_node = document.createTextNode(EXP_ACTUALLY_PAID);
								cell5.appendChild(EXP_ACTUALLY_PAID_node);
								mycurrent_row.appendChild(cell5);
								tbody.appendChild(mycurrent_row);
                                     seq++;

				    	  }
				    	 
			    		 }
			    	 else{
			    		 alert('error ..... ');
			    		 }
		  
			  }
      }
      if(command=="UnFreeze")
	  {
	  if(flag=="success")
      { 
		 
		  document.getElementById("div_frz").style.display="none";
          document.getElementById("div_frz_text").style.display="none";
          document.getElementById("div_Unfrz").style.display="none";
          document.getElementById("div_Unfrz_text").style.display="block";
      Load_grid('gen');
      Load_grid('sch');
      Load_grid('brd');
      
      }
       else{
      	 alert('Not Freezed Successfully ....');
       }
	  }
      if(command=="Freezed")
    	  {
    	  if(flag=="success")
          { 
    		  alert(' Freezed Successfully ....');
          //window.location.reload();
          document.getElementById("div_frz").style.display="none";
          document.getElementById("div_frz_text").style.display="block";
          document.getElementById("div_Unfrz").style.display="block";
          Load_grid('gen');
          Load_grid('sch');
          Load_grid('brd');
          
          }
           else{
          	 alert('Not Freezed Successfully ....');
           }
      }if(command=="FreezeDetail"){
    	  if(flag=="success"){
    	  var FREEZED=baseresponse.getElementsByTagName("FREEZED")[0].firstChild.nodeValue;
    	  //alert("FREEZED   ... "+FREEZED);
    	  if(FREEZED=="Y"){
    		  document.getElementById("div_frz").style.display="none";
         document.getElementById("div_frz_text").style.display="block";
          document.getElementById("div_Unfrz").style.display="block";
          document.getElementById("div_Unfrz_text").style.display="none";
          Load_grid('gen');
          Load_grid('sch');
          Load_grid('brd');
    	  }else if(FREEZED=="N")
    		  {
    		  document.getElementById("div_frz").style.display="block";
              document.getElementById("div_frz_text").style.display="none";
              document.getElementById("div_Unfrz").style.display="none";
              document.getElementById("div_Unfrz_text").style.display="none";
              Load_grid('gen');
              Load_grid('sch');
              Load_grid('brd');
    		  }
    	  else if(FREEZED=="R")
		  {
		  document.getElementById("div_frz").style.display="none";
          document.getElementById("div_frz_text").style.display="none";
          document.getElementById("div_Unfrz").style.display="none";
          document.getElementById("div_Unfrz_text").style.display="block";
          Load_grid('gen');
          Load_grid('sch');
          Load_grid('brd');
		  }
    	  }else
    		  {
    		  alert(' No Data Found ....');
    		  }
    	  
      }
    	 
      }
      }
}


function DelVal(sno,pid,bal,rec_up,book_upto,book_cur,rec_cur,Act_paid,type,sch_name)
{
	//alert("..... "+type);
	/*document.getElementById("sch_id1").value=sch_name;
	document.getElementById("AmtRec_id1").value=rec_cur;
	document.getElementById("ExpBook_id1").value=book_cur;
	document.getElementById("ExpAct_id1").value=Act_paid;
	document.getElementById("Bal_id1").value=bal;
	document.getElementById("hid").value=sno;
	document.getElementById("pro_id1").value=pid;
	document.getElementById("save_id1").value="update";*/
	var txtAcc_UnitCode=document.getElementById("cmbAcc_UnitCode_sch").value;
	if(txtAcc_UnitCode==""||txtAcc_UnitCode==null)
		txtAcc_UnitCode=3;
	var fin_year=document.getElementById("f_year").value;
	var date_val=document.getElementById("date_val").value;
	/*var sch_name=document.getElementById("sch_id1").value;
	var rec_cur=document.getElementById("AmtRec_id1").value;
	var book_cur=document.getElementById("ExpBook_id1").value;
	var Act_paid=document.getElementById("ExpAct_id1").value;
	var bal=document.getElementById("Bal_id1").value;
    var sno=document.getElementById("hid").value;
	var pid=document.getElementById("pro_id1").value;*/
	  var url="../../../../../Twad_report_ser?Command=Delete&txtAcc_UnitCode="+txtAcc_UnitCode+
	  "&fin_year="+fin_year+"&date_val="+date_val+"&type="+type+
	  "&pro_id="+pid+"&sch_id="+sch_name+"&AmtRec_id="+rec_cur+"&incur_id="+book_cur
		+"&ExpBook_id="+book_upto+"&ExpAct_id="+Act_paid+"&Bal_id="+bal+"&sno="+sno;
   //alert(url);
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
        	common_process(req);
        }
        req.send(null);
	
	}

function LoadTextVal(sno,pid,bal,rec_up,book_upto,book_cur,rec_cur,Act_paid,type,sch_name,tie,district,LOCAL_BODY)
{
	//alert("..... "+type+" ... "+LOCAL_BODY);
	if(type=="F"){
	document.getElementById("sch_id1").value=sch_name;
	document.getElementById("AmtRec_id1").value=rec_cur;
	document.getElementById("ExpBook_id1").value=book_upto;
	document.getElementById("incur_id1").value=book_cur;
	document.getElementById("ExpAct_id1").value=Act_paid;
	//document.getElementById("Bal_id1").value=bal;
	document.getElementById("hid1").value=sno;
	document.getElementById("pro_id1").value=pid;
	document.getElementById("tie_id1").value=tie;
	document.getElementById("Local1").value=LOCAL_BODY;
	document.getElementById("Dist_val").value=district;
	document.getElementById("save_id1").value="update";
	
	
	}else if(type=="T"){
		document.getElementById("Asch_id1").value=sch_name;
		document.getElementById("AAmtRec_id1").value=rec_cur;
		document.getElementById("AExpBook_id1").value=book_upto;
		document.getElementById("Aincur_id1").value=book_cur;
		document.getElementById("AExpAct_id1").value=Act_paid;
		//document.getElementById("ABal_id1").value=bal;
		document.getElementById("Ahid1").value=sno;
		document.getElementById("Apro_id1").value=pid;
		document.getElementById("Atie_id1").value=tie;
		document.getElementById("Dist_val").value=district;
		document.getElementById("Asave_id1").value="update";
		document.getElementById("ALocal1").value=LOCAL_BODY;
		
		}
	
	}


function  AllClear(){
	document.getElementById("pro_id1").value="";
	document.getElementById("sch_id1").value="";
	document.getElementById("tie_id1").value="";
	document.getElementById("AmtRec_id1").value="";
	document.getElementById("ExpBook_id1").value="";
	document.getElementById("ExpAct_id1").value="";
	document.getElementById("incur_id1").value="";
	document.getElementById("hid1").value="1";
	document.getElementById("fin_div1").style.display="none";
	document.getElementById("local_div1").style.display="block";
	document.getElementById("Local1").value="";
}
function  AllClear_lia(){
	document.getElementById("Apro_id1").value="";
	document.getElementById("Asch_id1").value="";
	document.getElementById("Atie_id1").value="";
	document.getElementById("AAmtRec_id1").value="";
	document.getElementById("AExpBook_id1").value="";
	document.getElementById("AExpAct_id1").value="";
	document.getElementById("Aincur_id1").value="";
	document.getElementById("Ahid1").value="1";
	document.getElementById("Afin_div1").style.display="none";
	document.getElementById("Alocal_div1").style.display="block";
	document.getElementById("ALocal1").value="";
	
	
}
function  AllClear_gen(){
	document.getElementById("cont_amt").value="";
	document.getElementById("sup_amt").value="";
	document.getElementById("EMD_amt").value="";
	document.getElementById("SD_rec_amt").value="";
	document.getElementById("Deposit_amt").value="";
	document.getElementById("Adv_amt").value="";
}


function Row_Adding(val,value)
{
	var j=val%5;
	if(j==0){
		 window.confirm(val+" Rows Yet to be Saved .... please Submit ..... ")
			
	}
	
	var but= document.getElementById("save_id"+val).value;
	//alert(but);
	if(but=="Add"){
	
	var id=val;
	
	//alert('test' +id);
	/*if(id=="A"||id<=0||id==null){
		id=document.getElementById("hid").value;
	
	}*/
	
	id++;document.getElementById("hid1").value=id;
	c=id;
	//document.getElementById("hid1").value=parseInt(c);
	//alert(c+" ... c value");
	//document.getElementById("hid").value = hid;
	var tbody = document.getElementById("lia_row");
	var current_row = document.createElement("TR");
	current_row.setAttribute("class","table");
	var cell = document.createElement("TD");
	var program = document.createElement("select");
	 //cell.setAttribute('onblur',"LoadProgram('" +c+ "')"); 
	 var req=getTransport();
	program.id = "pro_id" + c;
	program.name = "pro_id" + c;
	cell.appendChild(program);
	current_row.appendChild(cell);
	
	var cell1 = document.createElement("TD");
	var Scheme = document.createElement("input");
	Scheme.type = "Text";
	Scheme.setAttribute("size","20");
	Scheme.id = "sch_id" + c;
	Scheme.name = "sch_id" + c;
	cell1.appendChild(Scheme);
	current_row.appendChild(cell1);
	
	var cell7 = document.createElement("TD");
	var Tie = document.createElement("input");
	Tie.type = "Text";
	Tie.id = "tie_id" + c;
	Tie.name = "tie_id" + c;
	Tie.setAttribute("size","10");
	cell7.appendChild(Tie);
	current_row.appendChild(cell7);

	var cell3 = document.createElement("TD");
	var AmtRec_id_id = document.createElement("input");
	
	//onchange="balanceVal(this.value);"
	AmtRec_id_id.type = "Text";
	AmtRec_id_id.id = "AmtRec_id" + c;
	AmtRec_id_id.name = "AmtRec_id" + c;
	AmtRec_id_id.value = "";
	AmtRec_id_id.setAttribute("size","10");
	cell3.appendChild(AmtRec_id_id);
	//alert('123');
	//cell3.setAttribute("onchange", value)
    //cell3.setAttribute("onchange","balanceVal('"+c+"')");
   // alert('12345');
	current_row.appendChild(cell3);

	var cell2 = document.createElement("TD");
	var ExpBook_id_id = document.createElement("input");
	ExpBook_id_id.type = "Text";
	ExpBook_id_id.id = "ExpBook_id" + c;
	ExpBook_id_id.name = "ExpBook_id" + c;
	ExpBook_id_id.setAttribute("size","10");
	cell2.appendChild(ExpBook_id_id);
	current_row.appendChild(cell2);
	

	var cell4 = document.createElement("TD");
	var Bal_id = document.createElement("input");
	Bal_id.type = "Text";
	Bal_id.id = "incur_id" + c;
	Bal_id.name = "incur_id" + c;
	//Bal_id.setAttribute("disabled", true);	
	Bal_id.setAttribute("size","10");
	cell4.appendChild(Bal_id);
	current_row.appendChild(cell4);
	
	var cell1 = document.createElement("TD");
	var ExpAct_id = document.createElement("input");
	ExpAct_id.type = "Text";
	ExpAct_id.id = "ExpAct_id"+ c;
	ExpAct_id.name = "ExpAct_id" + c;
	ExpAct_id.setAttribute("size","10");
	cell1.appendChild(ExpAct_id);
	current_row.appendChild(cell1);
	
	/*var cell4 = document.createElement("TD");
	var Bal_id = document.createElement("input");
	Bal_id.type = "Text";
	Bal_id.id = "Bal_id" + c;
	Bal_id.name = "Bal_id" + c;
	Bal_id.setAttribute("disabled", true);	
	Bal_id.setAttribute("size","10");
	cell4.appendChild(Bal_id);
	current_row.appendChild(cell4);*/
	
	var cell9 = document.createElement("TD");
	var div=document.createElement("div");
	div.id="local_div"+c;

	var Localby = document.createElement("select");
	//program.type = "Text";
	Localby.id = "Local" + c;
	Localby.name = "Local" + c;
	Localby.setAttribute("onchange","LoadlcData('s','" +c+ "')"); 
	div.appendChild(Localby);
	cell9.appendChild(div);
	
	//var div1=document.createElement("<div style='display:none;'>");
	var div1=document.createElement("div");
	div1.setAttribute("style","display:none;");
	div1.id="fin_div"+c;
	
	var Localby1 = document.createElement("select");
	//program.type = "Text";
	Localby1.id = "fin" + c;
	Localby1.name = "fin" + c;
	//Localby.setAttribute("onchange","LoadlcData('T','" +c+ "')"); 
	div1.appendChild(Localby1);
	///<a id="backbt1" href="javascript:backAnc('<%=val%>');">Back</a></div>
	var a_tag=document.createElement("a");
	a_tag.id="backbt"+c;
	  var url="javascript:backAnc('"+c+"')";
	a_tag.href=url;
	var a_text = document.createTextNode("Back");
	a_tag.appendChild(a_text);
	div1.appendChild(a_tag);
	cell9.appendChild(div1);
	current_row.appendChild(cell9);
	
	var cell5 = document.createElement("TD");
	var sub_id = document.createElement("input");
	sub_id.type = "Button";
	sub_id.id = "save_id" + c;
	sub_id.name = "save_id" + c;
	sub_id.setAttribute("size","10");
	sub_id.value = "Add";
	 cell5.setAttribute('onclick',"Row_Adding('" +c+ "')"); 
	cell5.appendChild(sub_id);
	var hid = document.createElement("input");
	hid.type = "hidden";
	hid.id = "hid" + c;
	hid.name = "hid" + c;
	hid.value=c;
	cell5.appendChild(hid);
/*	
	var up_id = document.createElement("input");
	up_id.type = "Button";
	up_id.id = "update_id" + c;
	up_id.name = "update_id" + c;
	up_id.value = "Update";
	up_id.style.display="none";
    cell5.setAttribute('onclick',"update_data('" +c+ "')"); 
	cell5.appendChild(up_id);*/
	current_row.appendChild(cell5);
	
	
	
	
	
tbody.appendChild(current_row);
document.getElementById("save_id"+val).value="update";
var c1=parseInt(c);
LoadProgram(c1);
LcBody(c1);
//alert(c1);
	}else if(but=="update"){

		var txtAcc_UnitCode=document.getElementById("cmbAcc_UnitCode_sch").value;
		if(txtAcc_UnitCode==""||txtAcc_UnitCode==null)
			txtAcc_UnitCode=3;
		
		var fin_year=document.getElementById("f_year").value;
		var date_val=document.getElementById("date_val").value;
		var sch_name=document.getElementById("sch_id1").value;
		var rec_cur=document.getElementById("AmtRec_id1").value;
		var book_cur=document.getElementById("ExpBook_id1").value;
		var Act_paid=document.getElementById("ExpAct_id1").value;
		var incur=document.getElementById("incur_id1").value;
		//var bal=document.getElementById("Bal_id1").value;  Dist_val
        var sno=document.getElementById("hid1").value;
		var pid=document.getElementById("pro_id1").value;
		var tie_id=document.getElementById("tie_id1").value;
		var pid=document.getElementById("pro_id1").value;
		var local=document.getElementById("Local1").value;
		var Dist_val=document.getElementById("Dist_val").value;
		  var url="../../../../../Twad_report_ser?Command=Update&txtAcc_UnitCode="+txtAcc_UnitCode+
		  "&fin_year="+fin_year+"&date_val="+date_val+"&type=F"+
		  "&pro_id="+pid+"&sch_id="+sch_name+"&AmtRec_id="+rec_cur
			+"&ExpBook_id="+book_cur+"&ExpAct_id="+Act_paid
			//+"&Bal_id="+bal+"&sno="+sno
			+"&incur_id="+incur+"&sno="+sno+"&tie_id="+tie_id+"&local="+local+"&Dist_val="+Dist_val;
	   // alert(url);
	        var req=getTransport();
	        req.open("GET",url,true);
	        req.onreadystatechange=function()
	        {
	        	common_process(req);
	        }
	        req.send(null);
		
		
	}else{
		alert('not Applicable');
	}
}


function Row_Adding1(val,value)
{
	var j=val%5;
	if(j==0){
		 window.confirm(val+" Rows Yet to be Saved .... please Submit ..... ")
			
	}
	
//alert('test hid value'+val);	
var but= document.getElementById("Asave_id"+val).value; 
if(but=="Add"){

	var id=val;
	
	id++;document.getElementById("Ahid1").value=id;
	c=id;
	//alert(c);
	//document.getElementById("hid").value = hid;
	var tbody = document.getElementById("Alia_row1");
	var current_row1 = document.createElement("TR");
	current_row1.setAttribute("class","table");
	var cell = document.createElement("TD");
	// cell.setAttribute('onclick',"LoadProgram('" +c+ "')"); //
	var program = document.createElement("select");
	//program.type = "Text";
	 var req=getTransport();
	program.id = "Apro_id" + c;
	program.name = "Apro_id" + c;
	cell.appendChild(program);
	current_row1.appendChild(cell);
	
	var cell1 = document.createElement("TD");
	var Scheme = document.createElement("input");
	Scheme.type = "Text";
	Scheme.id = "Asch_id" + c;
	Scheme.name = "Asch_id" + c;
	Scheme.setAttribute("size","20");
	cell1.appendChild(Scheme);
	current_row1.appendChild(cell1);
	
	var cell7 = document.createElement("TD");
	var Tie = document.createElement("input");
	Tie.type = "Text";
	Tie.id = "Atie_id" + c;
	Tie.name = "Atie_id" + c;
	Tie.setAttribute("size","10");
	cell7.appendChild(Tie);
	current_row1.appendChild(cell7);
	
	var cell1 = document.createElement("TD");
	var AmtRec = document.createElement("input");
	AmtRec.type = "Text";
	AmtRec.id = "AAmtRec_id" + c;
	AmtRec.name = "AAmtRec_id" + c;
	AmtRec.setAttribute("size","10");
	//cell1.setAttribute("onchange","balanceVal1('"+c+"')");
	cell1.appendChild(AmtRec);
	current_row1.appendChild(cell1);
	
	var cell2 = document.createElement("TD");
	var ExpBk_id = document.createElement("input");
	ExpBk_id.type = "Text";
	ExpBk_id.id = "AExpBook_id" + c;
	ExpBk_id.name = "AExpBook_id" + c;
	ExpBk_id.setAttribute("size","10");
	cell2.appendChild(ExpBk_id);
	current_row1.appendChild(cell2);
	
	var cell4 = document.createElement("TD");
	var Bal_id = document.createElement("input");
	Bal_id.type = "Text";
	Bal_id.id = "Aincur_id" + c;
	Bal_id.name = "Aincur_id" + c;
	Bal_id.setAttribute("size","10");
	//Bal_id.setAttribute("disabled", true);	
	cell4.appendChild(Bal_id);
	current_row1.appendChild(cell4);
	
	var cell3 = document.createElement("TD");
	var ExpAct_id = document.createElement("input");
	ExpAct_id.type = "Text";
	ExpAct_id.id = "AExpAct_id" + c;
	ExpAct_id.name = "AExpAct_id" + c;
	ExpAct_id.setAttribute("size","10");
	cell3.appendChild(ExpAct_id);
	current_row1.appendChild(cell3);
	
	/*var cell4 = document.createElement("TD");
	var Bal_id = document.createElement("input");
	Bal_id.type = "Text";
	Bal_id.id = "ABal_id" + c;
	Bal_id.name = "ABal_id" + c;
	Bal_id.setAttribute("size","10");
	Bal_id.setAttribute("disabled", true);	
	cell4.appendChild(Bal_id);
	current_row1.appendChild(cell4);*/
	
	var cell9 = document.createElement("TD");
	var div=document.createElement("div");
	div.id="Alocal_div"+c;
	
	var Localby = document.createElement("select");
	//program.type = "Text";
	Localby.id = "ALocal" + c;
	Localby.name = "ALocal" + c;
	Localby.setAttribute("onchange","LoadlcData('T','" +c+ "')"); 
	div.appendChild(Localby);
	cell9.appendChild(div);
	//current_row1.appendChild(cell9);
	
	var div1=document.createElement("div");
	div1.setAttribute("style","display:none;");
	div1.id="Afin_div"+c;

	var Localby1 = document.createElement("select");
	//program.type = "Text";
	Localby1.id = "Afin" + c;
	Localby1.name = "Afin" + c;
	//Localby.setAttribute("onchange","LoadlcData('T','" +c+ "')"); 
	div1.appendChild(Localby1);
	var a_tag=document.createElement("a");
	a_tag.id="Abackbt"+c;
	  var url="javascript:AbackAnc('"+c+"')";
	a_tag.href=url;
	var a_text = document.createTextNode("Back");
	a_tag.appendChild(a_text);
	div1.appendChild(a_tag);
	cell9.appendChild(div1);
	current_row1.appendChild(cell9);
	
	var cell5 = document.createElement("TD");
	var sub_id = document.createElement("input");
	sub_id.type = "Button";
	sub_id.id = "Asave_id" + c;
	sub_id.name = "Asave_id" + c;
	sub_id.value = "Add";
    cell5.setAttribute('onclick',"Row_Adding1('" +c+ "')"); 
	cell5.appendChild(sub_id);
	var hid = document.createElement("input");
	hid.type = "hidden";
	hid.id = "Ahid" + c;
	hid.name = "Ahid" + c;
	hid.value=c;
	cell5.appendChild(hid);
	current_row1.appendChild(cell5);
	
	
	
	/*var up_id1 = document.createElement("input");
	up_id1.type = "Button";
	up_id1.id = "Aupdate_id" + c;
	up_id1.name = "Aupdate_id" + c;
	up_id1.value = "Update";
	up_id1.style.display="none";
   // cell5.setAttribute('onclick',"update_data1('" +c+ "')"); 
	cell5.appendChild(up_id1);*/
	
tbody.appendChild(current_row1);
document.getElementById("Asave_id"+val).value="update";
var c1=parseInt(c);
LoadProgram1(c1);
LcBody1(c1);
}else if(but=="update"){

	var txtAcc_UnitCode=document.getElementById("cmbAcc_UnitCode_sch").value;
	if(txtAcc_UnitCode==""||txtAcc_UnitCode==null)
		txtAcc_UnitCode=3;
	
	var fin_year=document.getElementById("f_year").value;
	var date_val=document.getElementById("date_val").value;
	var sch_name=document.getElementById("Asch_id1").value;
	var rec_cur=document.getElementById("AAmtRec_id1").value;
	var book_cur=document.getElementById("AExpBook_id1").value;
	var Act_paid=document.getElementById("AExpAct_id1").value;
	//var bal=document.getElementById("ABal_id1").value;
	var incur=document.getElementById("Aincur_id1").value;
    var sno=document.getElementById("Ahid1").value;
	var pid=document.getElementById("Apro_id1").value;
	var tie_id=document.getElementById("Atie_id1").value;
var local=document.getElementById("ALocal1").value;
var Dist_val=document.getElementById("Dist_val").value;
	  var url="../../../../../Twad_report_ser?Command=Update&txtAcc_UnitCode="+txtAcc_UnitCode+
	  "&fin_year="+fin_year+"&date_val="+date_val+"&type=T"+
	  "&pro_id="+pid+"&sch_id="+sch_name+"&AmtRec_id="+rec_cur
		+"&ExpBook_id="+book_cur+"&ExpAct_id="+Act_paid+
		"&incur_id="+incur+"&sno="+sno+"&tie_id="+tie_id+"&local="+local+"&Dist_val="+Dist_val;
		//"&Bal_id="+bal+"&sno="+sno+"&tie_id="+tie_id;
    //alert(url);
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
        	common_process(req);
        }
        req.send(null);
	
	
}else{
	alert('not Applicable');
}
}

function Sch_Liability(path)
{

var txtAcc_UnitCode=document.getElementById("cmbAcc_UnitCode_sch").value;
if(txtAcc_UnitCode==""||txtAcc_UnitCode==null)
	txtAcc_UnitCode=3;
var hid=document.getElementById("hid1").value;
var fin_year=document.getElementById("f_year").value;
var date_val=document.getElementById("date_val").value;
var url= path + "/Twad_report_ser?Command=Sch_Liability&txtAcc_UnitCode="+txtAcc_UnitCode+"&fin_year="+fin_year+"&date_val="+date_val+"&type=F";
for(var i=1;i<=hid;i++){
	//alert(i);
	var pro_id=document.getElementById("pro_id"+i).value;
	var tie_id=document.getElementById("tie_id"+i).value;
	var sch_id=document.getElementById("sch_id"+i).value;
	var AmtRec_id=document.getElementById("AmtRec_id"+i).value;
	var ExpBook_id=document.getElementById("ExpBook_id"+i).value;
	var ExpAct_id=document.getElementById("ExpAct_id"+i).value;
	var incur_id=document.getElementById("incur_id"+i).value;
	//var Bal_id=document.getElementById("Bal_id"+i).value;
	var main_Cmb=document.getElementById("Local"+i).value;
	if(main_Cmb=="Corporation")
		{
		main_Type="C";
		}else if(main_Cmb=="Municipality")
		{
			main_Type="M";
		}else if(main_Cmb=="Townpanchayat")
		{
			main_Type="T";
		}
	var sub_Cmb=document.getElementById("fin"+i).value;
	var sub_cnmb_val=sub_Cmb.split("-");
	var sub_menu=sub_cnmb_val[0];
	//alert(sub_menu);
	var local_by=sub_cnmb_val[1];
	//alert(local_by);
	
	//alert("main_Cmb .."+main_Cmb+" ---- "+sub_Cmb);
	url += "&pro_id=" + pro_id + "&sch_id=" + sch_id + "&AmtRec_id="
				+ AmtRec_id + "&ExpBook_id=" + ExpBook_id + "&ExpAct_id="
				//+ ExpAct_id + "&Bal_id=" + Bal_id + "&tie_id=" + tie_id
				+ ExpAct_id + "&incur_id=" + incur_id + "&tie_id=" + tie_id
				+ "&main_Cmb=" + main_Cmb+ "&sub_Cmb=" + sub_menu+ "&main_Type=" + main_Type
				+ "&local_by=" + local_by;

}
alert(url);
  var req=getTransport();
  req.open("GET",url,true);
  req.onreadystatechange=function()
  {
      common_process(req);
  }
  req.send(null);

}
function balanceVal(n) {

	var hid=document.getElementById("hid"+n).value;	alert(hid)
	var tie_id=document.getElementById("tie_id"+hid).value;
	var AmtRec_id=document.getElementById("AmtRec_id"+hid).value;
	document.getElementById("Bal_id"+hid).value=tie_id-AmtRec_id;
	
	
	
}
function balanceVal1(n) {
	
	var hid_VAL=document.getElementById("Ahid"+n).value;
	var tie_id=document.getElementById("Atie_id"+hid_VAL).value;
	var AmtRec_id=document.getElementById("AAmtRec_id"+hid_VAL).value;
	document.getElementById("ABal_id"+hid_VAL).value=tie_id-AmtRec_id;
	
	
	
}
function Board_Liability(path) {
	var txtAcc_UnitCode=document.getElementById("cmbAcc_UnitCode_brd").value;
	if(txtAcc_UnitCode==""||txtAcc_UnitCode==null)
		txtAcc_UnitCode=3;
	var hid=document.getElementById("Ahid1").value;
	var date_val=document.getElementById("date_val").value;
	var fin_year=document.getElementById("f_year").value;
	var url= path + "/Twad_report_ser?Command=Board_Liability&txtAcc_UnitCode="+txtAcc_UnitCode+"&fin_year="+fin_year+"&date_val="+date_val+"&type=T";
	//alert("...."+url);
	for(var i=1;i<=hid;i++){
		
		/* " +
		var pro_id=document.getElementById("Apro_id"+i).value;alert('hhh'+pro_id);
		if(pro_id==null && pro_id=="")alert('Enter Program value ..... ');
		var sch_id=document.getElementById("Asch_id"+i).value;alert('sch_id   '+sch_id);
		if(sch_id==null && sch_id=="")alert('Enter Scheme value ..... ');
		var tie_id=document.getElementById("Atie_id"+i).value;
		if(tie_id==null && tie_id=="")alert('Enter Tie Up Amount value ..... ');
		var AmtRec_id=document.getElementById("AAmtRec_id"+i).value;
		if(AmtRec_id==null && AmtRec_id=="")alert('Enter Received Amount value ..... ');
		var ExpBook_id=document.getElementById("AExpBook_id"+i).value;
		if(AmtRec_id==null && AmtRec_id=="")alert('Enter Received Amount value ..... ');
		var ExpAct_id=document.getElementById("AExpAct_id"+i).value;
		if(ExpAct_id==null && ExpAct_id=="")alert('Enter Expenditure Actual Amount value ..... ');
		var Bal_id=document.getElementById("ABal_id"+i).value;
		if(Bal_id==null && Bal_id=="")alert('Enter Balance Amount value ..... ');
		var incur_id=document.getElementById("Aincur_id"+i).value;
		if(incur_id==null && incur_id=="")alert('Enter Incured upto 31.3.2013 Amount value ..... ');
		var main_Cmb=document.getElementById("ALocal"+i).value;
		if(main_Cmb==null && main_Cmb=="")alert('Select Local Body value ..... ');
		*/
		
		var pro_id=document.getElementById("Apro_id"+i).value;	
		var sch_id=document.getElementById("Asch_id"+i).value;	
		var tie_id=document.getElementById("Atie_id"+i).value;		
		var AmtRec_id=document.getElementById("AAmtRec_id"+i).value;	
		var ExpBook_id=document.getElementById("AExpBook_id"+i).value;	
		var ExpAct_id=document.getElementById("AExpAct_id"+i).value;	
	//	var Bal_id=document.getElementById("ABal_id"+i).value;		
		var incur_id=document.getElementById("Aincur_id"+i).value;		
		var main_Cmb=document.getElementById("ALocal"+i).value;
	
		if(main_Cmb=="Corporation")
			{
			main_Type="C";
			}else if(main_Cmb=="Municipality")
			{
				main_Type="M";
			}else if(main_Cmb=="Townpanchayat")
			{
				main_Type="T";
			}
		var sub_Cmb=document.getElementById("Afin"+i).value;
		var sub_cnmb_val=sub_Cmb.split("-");
		var sub_menu=sub_cnmb_val[0];
		//alert(sub_menu);
		var local_by=sub_cnmb_val[1];
		//alert(local_by);
		url+="&pro_id="+pro_id+"&sch_id="+sch_id+"&AmtRec_id="+AmtRec_id
		//+"&ExpBook_id="+ExpBook_id+"&ExpAct_id="+ExpAct_id+"&Bal_id="+Bal_id+"&tie_id="+tie_id
		+"&ExpBook_id="+ExpBook_id+"&ExpAct_id="+ExpAct_id+"&incur_id="+incur_id+"&tie_id="+tie_id
		+ "&main_Cmb=" + main_Cmb+ "&sub_Cmb=" + sub_menu+ "&main_Type=" + main_Type
		+ "&local_by=" + local_by;

	
	}
	// alert(url);
	  var req=getTransport();
	  req.open("GET",url,true);
	  req.onreadystatechange=function()
	  {
	      common_process(req);
	  }
	  req.send(null);

}

function generalFunc(path)
{
var txtAcc_UnitCode=document.getElementById("cmbAcc_UnitCode_gen").value;
if(txtAcc_UnitCode==""||txtAcc_UnitCode==null){
	txtAcc_UnitCode=3;
}
var date_val=document.getElementById("date_val").value;
var fin_year=document.getElementById("f_year").value;
var cont_amt=document.getElementById("cont_amt").value;
var cont_id=document.getElementById("cont_id").value;
var supp_amt=document.getElementById("sup_amt").value;
var supp_id=document.getElementById("sup_id").value;
var EMD_amt=document.getElementById("EMD_amt").value;
var EMD_id=document.getElementById("EMD_id").value;
var SDRec_amt=document.getElementById("SD_rec_amt").value;
var Rec_id=document.getElementById("SD_rec_id").value;
var Dep_amt=document.getElementById("Deposit_amt").value;
var Dep_id=document.getElementById("Deposit_id").value;
var Adv_amt=document.getElementById("Adv_amt").value;
var Adv_id=document.getElementById("Adv_id").value;

	var url = path + "/Twad_report_ser?Command=generalFunc&txtAcc_UnitCode="
			+ txtAcc_UnitCode + "&date_val=" + date_val + "&fin_year="
			+ fin_year + "&Amount=" + cont_amt + "&sch_id=" + cont_id;
			url+= "&Amount=" + supp_amt + "&sch_id=" + supp_id + "&Amount="
			+ EMD_amt + "&sch_id=" + EMD_id+"&Amount="+SDRec_amt+"&sch_id="+Rec_id+
			"&Amount="+Dep_amt+"&sch_id="+Dep_id+"&Amount="+Adv_amt+"&sch_id="+Adv_id+"&type=G";
	

//alert(url);
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
    common_process(req);
}
req.send(null);
	}



function printPDF(path,val){
	//alert(path+".. .."+val);
	var txtAcc_UnitCode=document.getElementById("cmbAcc_UnitCode_gen").value;
	if(txtAcc_UnitCode==""||txtAcc_UnitCode==null){
		txtAcc_UnitCode=3;
	}
	var date_val=document.getElementById("date_val").value;
	var fin_year=document.getElementById("f_year").value;
	var url = path + "/Twad_report_ser?Command=PDF&fin_year=" + fin_year
			+ "&txtAcc_UnitCode=" + txtAcc_UnitCode+"&type="+val;
	//alert(url);
	document.frmMIS_Twad.action = url;
	document.frmMIS_Twad.method = "POST";
	document.frmMIS_Twad.submit();
}


function printPDF2(path,val){
	//alert(path+".. .."+val);
	var txtAcc_UnitCode=document.getElementById("cmbAcc_UnitCode_brd").value;
	if(txtAcc_UnitCode==""||txtAcc_UnitCode==null){
		txtAcc_UnitCode=3;
	}
	var date_val=document.getElementById("date_val").value;
	var fin_year=document.getElementById("f_year").value;
	var url = path + "/Twad_report_ser?Command=PDF&fin_year=" + fin_year
			+ "&txtAcc_UnitCode=" + txtAcc_UnitCode+"&type="+val;
	//alert(url);
	document.frmMIS_Twad.action = url;
	document.frmMIS_Twad.method = "POST";
	document.frmMIS_Twad.submit();
}

function printPDF1(path,val){
	//alert(path+".. .."+val);
	var txtAcc_UnitCode=document.getElementById("cmbAcc_UnitCode_sch").value;
	if(txtAcc_UnitCode==""||txtAcc_UnitCode==null){
		txtAcc_UnitCode=3;
	}
	var date_val=document.getElementById("date_val").value;
	var fin_year=document.getElementById("f_year").value;
	var url = path + "/Twad_report_ser?Command=PDF&fin_year=" + fin_year
			+ "&txtAcc_UnitCode=" + txtAcc_UnitCode+"&type="+val;
	//alert(url);
	document.frmMIS_Twad.action = url;
	document.frmMIS_Twad.method = "POST";
	document.frmMIS_Twad.submit();
}

function outstd_fzd(path)
{
	//alert(path+".... "+document.frmMIS_Twad.fzd.checked)
	var txtAcc_UnitCode=document.getElementById("cmbAcc_UnitCode_gen").value;
	if(txtAcc_UnitCode==""||txtAcc_UnitCode==null){
		txtAcc_UnitCode=3;
	}
	var date_val=document.getElementById("date_val").value;
	var fin_year=document.getElementById("f_year").value;	
	
	var url = path + "/Twad_report_ser?Command=Freeze&txtAcc_UnitCode="
	+ txtAcc_UnitCode + "&date_val=" + date_val + "&fin_year="+ fin_year ;
	if(document.frmMIS_Twad.fzd.checked==true){
	url+="&freeze=Y"
	}
	//alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	    common_process(req);
	}
	req.send(null);
		
}


function removeRow()
{
	alert('test delete ROW');
  var ptable = document.getElementById('lia_row');
  var lastElement = ptable.rows.length;
  alert(lastElement);
  if (lastElement > 4) ptable.deleteRow(lastElement > 3);
  if(document.getElementById("hid1").value>1)
	{
	   document.getElementById("hid1").value = 
           document.getElementById("hid1").value-1;
	}
}

function LcBody(val)
{
	var txtAcc_UnitCode=document.getElementById("cmbAcc_UnitCode_sch").value;
	if(txtAcc_UnitCode==""||txtAcc_UnitCode==null){
		txtAcc_UnitCode=3;
	}
	
	var url ="../../../../../Twad_report_ser?Command=LocalBY";
	//alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
		LoadLocal(req,val);
	}
	req.send(null);	
}

function LcBody1(val)
{
	var txtAcc_UnitCode=document.getElementById("cmbAcc_UnitCode_brd").value;
	if(txtAcc_UnitCode==""||txtAcc_UnitCode==null){
		txtAcc_UnitCode=3;
	}
	
	var url ="../../../../../Twad_report_ser?Command=LocalBY";
	//alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
		LoadLocal1(req,val);
	}
	req.send(null);	
}

function LoadLocal(req,val){
	
	    if(req.readyState==4)
	    {
	 
	     if(req.status==200)
	     {  
	  
	     var baseresponse = req.responseXML.getElementsByTagName("response")[0];
	      
	       
	        var command=baseresponse.getElementsByTagName("command")[0].firstChild.nodeValue;
	       var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	        
	
	      
	        if(flag=="success")
	        { 
	        	
	        	//alert(flag+" >>>  est >>>"+val);
	            var cmbAcc_pro_id = document.getElementById("Local"+val);         
	                cmbAcc_pro_id.length=0;
	                var option=document.createElement("OPTION");
	                option.text="Select";
	                cmbAcc_pro_id.appendChild(option);
	            var option_count=baseresponse.getElementsByTagName("URBANLB_TYPE_ID");   
	           
	            var root = null;
	         
	            for(var i=0;i<option_count.length;i++)
	            {  
	                var option=document.createElement("OPTION");
	                //root = baseresponse.getElementsByTagName("ID")[i];
	                var ID=baseresponse.getElementsByTagName("URBANLB_TYPE_ID")[i].firstChild.nodeValue;
	                //alert(ID);
	                
	                var URBANLB_TYPE_DESC=baseresponse.getElementsByTagName("URBANLB_TYPE_DESC")[i].firstChild.nodeValue;
	                
	                option.text=URBANLB_TYPE_DESC;
	                option.value=URBANLB_TYPE_DESC;
	                try
	                {   
	                	cmbAcc_pro_id.add(option);
	                }
	                catch(errorObject)
	                {
	                	cmbAcc_pro_id.add(option,null);
	                }   
	                      
	            }
	        }
	     }
	    }
}

function LoadLocal1(req,val){
	
	    if(req.readyState==4)
	    {
	 
	     if(req.status==200)
	     {  
	  
	     var baseresponse = req.responseXML.getElementsByTagName("response")[0];
	      
	       
	        var command=baseresponse.getElementsByTagName("command")[0].firstChild.nodeValue;
	       var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	        
	
	      
	        if(flag=="success")
	        { 
	        	
	        	//alert(flag+" >>>  est >>>"+val);
	            var cmbAcc_pro_id = document.getElementById("ALocal"+val);         
	                cmbAcc_pro_id.length=0;
	                var option=document.createElement("OPTION");
	                option.text="Select";
	                cmbAcc_pro_id.appendChild(option);
	            var option_count=baseresponse.getElementsByTagName("URBANLB_TYPE_ID");   
	           
	            var root = null;
	         
	            for(var i=0;i<option_count.length;i++)
	            {  
	                var option=document.createElement("OPTION");
	                //root = baseresponse.getElementsByTagName("ID")[i];
	                var ID=baseresponse.getElementsByTagName("URBANLB_TYPE_ID")[i].firstChild.nodeValue;
	                //alert(ID);
	                
	                var URBANLB_TYPE_DESC=baseresponse.getElementsByTagName("URBANLB_TYPE_DESC")[i].firstChild.nodeValue;
	                
	                option.text=URBANLB_TYPE_DESC;
	                option.value=URBANLB_TYPE_DESC;
	                try
	                {   
	                	cmbAcc_pro_id.add(option);
	                }
	                catch(errorObject)
	                {
	                	cmbAcc_pro_id.add(option,null);
	                }   
	                      
	            }
	        }
	     }
	    }
}


function LoadlcData(val,sno){
	//alert(val+sno);
	var url ="../../../../../Twad_report_ser?Command=loadOnchange";
	var id;
	if(val=="s"){
		
		document.getElementById("fin_div"+sno).style.display="block";
		 id=document.getElementById("Local"+sno).value;
		 document.getElementById("local_div"+sno).style.display="none";
	}else if(val=="T"){
		
		document.getElementById("Afin_div"+sno).style.display="block";
		 id=document.getElementById("ALocal"+sno).value;
		 document.getElementById("Alocal_div"+sno).style.display="none";
	}
	url+="&LB_id="+id;
	//alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
		common_LB(req,val,sno);
	}
	req.send(null);	
}

function common_LB(req,val,no){
	
    if(req.readyState==4)
    {
 
     if(req.status==200)
     {  
  
     var baseresponse = req.responseXML.getElementsByTagName("response")[0];
     
       
        var command=baseresponse.getElementsByTagName("command")[0].firstChild.nodeValue;
       var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        

      
        if(flag=="success")
        { var cmbAcc_pro_id="" ;
        	
        	if(val=="s"){
            cmbAcc_pro_id = document.getElementById("fin"+no);  }
        	else if(val=="T"){
        		 cmbAcc_pro_id = document.getElementById("Afin"+no);
        	}else{
        		alert('Error in 1649 th line.......');
        	}
                cmbAcc_pro_id.length=0;
                var option=document.createElement("OPTION");
                option.text="Select";
                cmbAcc_pro_id.appendChild(option);
            var option_count=baseresponse.getElementsByTagName("ID");   
            var root = null;
         
            for(var i=0;i<option_count.length;i++)
            {  
                var option=document.createElement("OPTION");
                //root = baseresponse.getElementsByTagName("ID")[i];
                var ID=baseresponse.getElementsByTagName("ID")[i].firstChild.nodeValue;
             //   alert(ID);
                var URBANLB_TYPE_DESC=baseresponse.getElementsByTagName("DESC")[i].firstChild.nodeValue;
                var LB_Type=baseresponse.getElementsByTagName("LB_Type")[i].firstChild.nodeValue;
                
                option.text=URBANLB_TYPE_DESC;
                option.value=ID+"-"+URBANLB_TYPE_DESC;
                try
                {   
                	cmbAcc_pro_id.add(option);
                }
                catch(errorObject)
                {
                	cmbAcc_pro_id.add(option,null);
                }   
                      
            }
        }
     }
    }
}
function Loadlhid(val)
{ var d=val.split("-");
val=parseInt(d[0]);
	document.getElementById("Dist_val").value=d[0];
	}
function backAnc(sno)
{
	
document.getElementById("fin_div"+sno).style.display="none";
document.getElementById("local_div"+sno).style.display="block";
	
}

function AbackAnc(sno)
{
	
document.getElementById("Afin_div"+sno).style.display="none";
document.getElementById("Alocal_div"+sno).style.display="block";
	
}

function frzz(path)
{
	//alert('test');
	var txtAcc_UnitCode=document.getElementById("cmbAcc_UnitCode_gen").value;
	if(txtAcc_UnitCode==""||txtAcc_UnitCode==null){
		txtAcc_UnitCode=3;
	}
	var date_val=document.getElementById("date_val").value;
	var fin_year=document.getElementById("f_year").value;	
	
	var url = path + "/Twad_report_ser?Command=Freeze&txtAcc_UnitCode="
	+ txtAcc_UnitCode + "&date_val=" + date_val + "&fin_year="+ fin_year+"&freeze=Y" ;
	
	
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	    common_process(req);
	}
	req.send(null);
		
}
function Unfrzz(path)
{ 
	document.getElementById("div_Unfrz").style.display="none";
	document.getElementById("div_Unfrz_text").style.display="block";
	document.getElementById("div_frz").style.display="none";
	document.getElementById("div_frz_text").style.display="none";
	var txtAcc_UnitCode=document.getElementById("cmbAcc_UnitCode_gen").value;
	if(txtAcc_UnitCode==""||txtAcc_UnitCode==null){
		txtAcc_UnitCode=3;
	}
	var date_val=document.getElementById("date_val").value;
	var fin_year=document.getElementById("f_year").value;	
	
	var url = path + "/Twad_report_ser?Command=UnFreeze&txtAcc_UnitCode="
	+ txtAcc_UnitCode + "&date_val=" + date_val + "&fin_year="+ fin_year ;
	
	//alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	    common_process(req);
	}
	req.send(null);
	
	}



function frzzDet(path)
{ 
	document.getElementById("div_Unfrz").style.display="none";
	document.getElementById("div_Unfrz_text").style.display="block";
	var txtAcc_UnitCode=document.getElementById("cmbAcc_UnitCode_gen").value;
	if(txtAcc_UnitCode==""||txtAcc_UnitCode==null){
		txtAcc_UnitCode=3;
	}
	var date_val=document.getElementById("date_val").value;
	var fin_year=document.getElementById("f_year").value;	
	
	var url = path + "/Twad_report_ser?Command=FreezeDetail&txtAcc_UnitCode="
	+ txtAcc_UnitCode + "&date_val=" + date_val + "&fin_year="+ fin_year ;
	
	//alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	    common_process(req);
	}
	req.send(null);
	
	}

