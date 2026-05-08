  /*
 
    Document    : Ben Report
    Module	   : PMS
    Created on : 20/2/2010
    File Name  : Ben_Report.js
    Action To  :
    File Desc  : Data collect for ben
    Author     : K.Panneer Selvam
    -----------------------------------------------------
    Sno	  Date	 	Type	EMP ID	
    -----------------------------------------------------
    1	  20/2/2010  N
    
 */ /*
  * major - TR,TD,
  * item - input ,label
  * type -  text,redio,check
  * name -  item name
  * value -  display value
  * size  -  size of control
  * sclass - style class
  * url    - linked url 
  * style -  user writed style
  * tdwidth - td width
  * tdalign -  td align
  * funtype - function type (event)
  * funname - function name if (more then one user comma sepearated)
  */
 var process_code=0;
 var command="";
 function ben_report_show(command,process,input_value)
 {  
	 
	 var url="";
	 process_code=process;
	 command=command;
	 var xmlobj=createObject();
	 // All   
	 if (process==1) 
	  {
		 input_value=input_value;
		 url="../../../../../Ben_Report?command="+command+"&input_value="+input_value+"&process_code="+process;
		 
	  }
	  // Beneficiary Type based
	  if (process==2) 
	  {
 		  if (input_value.value==6)
			 {
				 
			   document.getElementById('block').style.visibility = 'visible';
			   document.getElementById('dis').style.visibility = 'visible';
			 }
 		  else
 		  {
 			  document.getElementById('block').style.visibility = 'hidden';
 			  document.getElementById('dis').style.visibility = 'hidden';
 		 
 		  }
		 input_value=input_value.value;
		 url="../../../../../Ben_Report?command="+command+"&input_value="+input_value+"&process_code="+process; 
	  }
	  // District  Based 
	  if (process==3) 
	  {
		  input_value=input_value.value;
			 
			 url="../../../../../Ben_Report?command="+command+"&input_value="+document.getElementById("bentype").value+"&process_code="+process;
			 url+="&DISTRICT_CODE="+input_value;
	  }
	  // Block Based 
	  if (process==4) 
	  {
		  input_value=input_value.value;
			 
			 url="../../../../../Ben_Report?command="+command+"&input_value="+document.getElementById("bentype").value+"&process_code="+process;
			 url+="&DISTRICT_CODE="+document.getElementById("dis_value").value+"&BLOCK_SNO="+input_value;
	  }
	//  vTest(url)
     xmlobj.open("GET",url,true);
     xmlobj.onreadystatechange=function()
     {
    	 ben_report_process(xmlobj,command);
     }
     xmlobj.send(null);
 }
 
 function ben_report_process(xmlobj,command)
 {
	
  if (xmlobj.readyState==4)
   { 
	  
   if (xmlobj.status==200)
     {  
         if (command=='show')  
         ben_report_result(xmlobj);             
     }
   }
     
 }
 function ben_report_result(xmlobj)
 {
	 
	
	 if (process_code==1 || process_code==2 || process_code==3 || process_code==4)
	  { 
		 
		  var t=0;
		  var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
		  var tbody = document.getElementById("entred_body");
		  var table = document.getElementById("entred_data");
		  var tbody_head = document.getElementById("entred_body_head");
		 
		  
		  
		  
		  
		  
		  
		  
		  for(t1=tbody_head.rows.length-1;t1>=0;t1--){tbody_head.deleteRow(0);}
		 // heading 1   
		  var head_new_row=cell("TR","","","","",0,"","","background-color:#CCCCCC;font-weight:300","","","","");
		  var headn_cell=cell("TD","label","","","Beneficiary Name",7,"","","font-size:14px;font-weight:bold","","center","","");
		  head_new_row.appendChild(headn_cell);
		  tbody_head.appendChild(head_new_row);
		  var headt_cell=cell("TD","label","","","Beneficiary Type",7,"","","font-size:14px;font-weight:bold","15%","center","","");
		  head_new_row.appendChild(headt_cell);
		  var headd_cell=cell("TD","label","","","District",7,"","","font-size:14px;font-weight:bold","10%","center","","");
		  head_new_row.appendChild(headd_cell);
		 // heading 2
		  
		   
		  var headb_cell=cell("TD","label","","","Block  ",7,"","","font-size:14px;font-weight:bold","10%","center","","");
		  head_new_row.appendChild(headb_cell);
		  
		// heading 3
		  
		  var headc_cell=cell("TD","span","","","City",7,"","","font-size:14px;font-weight:bold","20%","center","","");
		  head_new_row.appendChild(headc_cell);
		  
		  
		  tbody_head.appendChild(head_new_row);
		  
		  
		  
		  
		  for(t=tbody.rows.length-1;t>=0;t--){tbody.deleteRow(0);}

		  var len=bR.getElementsByTagName("prow")[0].firstChild.nodeValue;;
		  
		  for (i=0;i<len;i++)
		 	{
			  var new_row=cell("TR","","","","","","","","font-size:14px;","","","","");
			  var BENEFICIARY_NAME = bR.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
			  var BEN_TYPE_SDESC= bR.getElementsByTagName("BEN_TYPE_SDESC")[i].firstChild.nodeValue;
			  var DISTRICT_NAME= bR.getElementsByTagName("DISTRICT_NAME")[i].firstChild.nodeValue;
			  var block_name= bR.getElementsByTagName("block_name")[i].firstChild.nodeValue;
 			  var OFFICE_ADDRESS3= bR.getElementsByTagName("OFFICE_ADDRESS3")[i].firstChild.nodeValue;
 			  
			   
              var name_cell=cell("TD","label","","",BENEFICIARY_NAME,7,"","","font-size:14px;","","","","");
              var type_cell=cell("TD","label","","",BEN_TYPE_SDESC,7,"","","font-size:14px;","","","","");
              var dist_cell=cell("TD","label","","",DISTRICT_NAME,7,"","","font-size:14px;","","","","");
              var black_cell=cell("TD","label","","",block_name,7,"","","font-size:14px;","","","","");
              var address3_cell=cell("TD","label","","",OFFICE_ADDRESS3,7,"","","font-size:14px;","","","","");
              
              new_row.appendChild(name_cell);
              new_row.appendChild(type_cell);
              new_row.appendChild(dist_cell);
              new_row.appendChild(black_cell);
              new_row.appendChild(address3_cell);
              tbody.appendChild(new_row);
		 	}  
		
		  	
	  }
 }
 
 