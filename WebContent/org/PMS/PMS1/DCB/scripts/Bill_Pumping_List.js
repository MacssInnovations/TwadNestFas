var process_code=0;
var input_row_no=0;
var display_table="";
var display_body="";
var command="";

function list(command,process,input_value)
 {
	 
		var  year =document.getElementById("year").value;
		var bentype =document.getElementById("bentype").value;
		var subdiv =document.getElementById("subdiv").value;
		var month =document.getElementById("month").value;
		//if (subdiv==0 || bentype==0) process=1;
		process_code=process;
		this.command=command;
		
	 if (command=='show')  
	  	{
		    
		 	url="../../../../../Pumping_Return_List?month="+month+
		 	"&year="+year+
		 	"&bentype="+bentype+
		 	"&subdiv="+subdiv+
		 	"&process="+process+
		 	"&command="+command;
		 	 document.getElementById("pr_status").value=0;; 
	  	}
	 if (command=='delete' || command=='FR')  
	  	{
		    
		 	url="../../../../../Pumping_Return_List?month="+month+
		 	"&year="+year+
		 	"&month="+month+
		 	"&ben_sno="+input_value+
		  	"&process="+process+
		  	"&command="+command;
		 	
		 	 
	  	}
	 var xmlobj=createObject();
     xmlobj.open("GET",url,true);
     xmlobj.onreadystatechange=function()
     {
    	 
     	result_process_pumping(xmlobj,command,input_value);
     }
     xmlobj.send(null);
 }
function result_process_pumping(xmlobj,command,input_value)
{	 
	
	
 if (xmlobj.readyState==4)
 {	   
 	 
	if (xmlobj.status==200)
    {
		 
		if (command=='show')
		{
        show_pumping(xmlobj,input_value);
		}
		if (command=='delete' || command=='FR')
		{
        show_pumping(xmlobj,input_value);
		}
    }
  }
    
}
function show_pumping(xmlobj,input_value)
{
	
	  var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
 
	if (command=='show')
	{
		document.getElementById("pr_status").value=1;
	  var status = bR.getElementsByTagName("status")[0].firstChild.nodeValue;
	  var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
	  var tbody = document.getElementById("entred_body");
	  var table = document.getElementById("entred_data");
	  
	  var t=0;	
	  for(t=tbody.rows.length-1;t>=0;t--){tbody.deleteRow(0);}
	 
	  var len=bR.getElementsByTagName("row_count")[0].firstChild.nodeValue;
	  if (len==0)
	  {
		  alert("Data Not Found\n-------------------")
		  
	  }
	  for (i=0;i<len;i++)
		{
		    var BENEFICIARY_SNO = bR.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
			var BENEFICIARY_NAME = bR.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
			var BENEFICIARY_TYPE = bR.getElementsByTagName("BENEFICIARY_TYPE")[i].firstChild.nodeValue;
			var BENEFICIARY_TYPE_SNO= bR.getElementsByTagName("BENEFICIARY_TYPE_SNO")[i].firstChild.nodeValue;
			var qty = bR.getElementsByTagName("qty")[i].firstChild.nodeValue;
			var pr_record = bR.getElementsByTagName("pr_record")[i].firstChild.nodeValue;
			
		      var BENEFICIARY_TYPE_SNO_cell=cell("TD","input","hidden","BENEFICIARY_TYPE_SNO"+(i+1),BENEFICIARY_TYPE_SNO,2,"","","","2%","","","");

			var new_row=cell("TR","","","row"+(i+1),(i+1),2,"","","","","","","");
			var SNO_cell=cell("TD","input","hidden","select"+(i+1),BENEFICIARY_SNO,2,"","","","2%","","","");
		    var NAME_cell=cell("TD","label","","BENEFICIARY_NAME"+(i+1),BENEFICIARY_NAME,2,"label1","","font-size: 15px","35%","left","","");
		    var TYPE_cell=cell("TD","label","","BENEFICIARY_TYPE"+(i+1),BENEFICIARY_TYPE,2,"label1","","font-size: 15px","20%","left","","");
		    var qty_cell=cell("TD","label","","qty"+(i+1),qty,2,"label1","","font-size: 15px","10%","right","","");
		    var temp_cell=cell("TD","label","","","Demand Completed",2,"","","font-size: 15px","10%","","onclick","Demand_Report_List.jsp");
		    var temp_cell2=cell("TD","label","","","",2,"","","font-size: 15px","10%","","left","");
		    
		  	var href_all_cell=cell("TD","A","EDIT","EDIT","Edit",2,"","javascript:rld(1,'"+BENEFICIARY_SNO+"',"+BENEFICIARY_TYPE_SNO+")","font-size: 15px","10%","right","onClick","");
		  	var href_report_cell=cell("TD","A","EDIT","EDIT","Report",2,"","javascript:rld(3,'"+BENEFICIARY_SNO+"',"+BENEFICIARY_TYPE_SNO+")","font-size: 15px","10%","right","","");
		  	var freeze_report_cell=cell("TD","A","EDIT","EDIT","Freezed   " ,2,"","javascript:frz(3,'"+BENEFICIARY_SNO+"',"+BENEFICIARY_TYPE_SNO+")","font-size: 15px","5%","right","","");
		  	var href_demand_cell=cell("TD","A","EDIT","EDIT","Demand",2,"","javascript:rld(2,'"+BENEFICIARY_SNO+"',"+BENEFICIARY_TYPE_SNO+")","font-size: 15px","19%","right","","");
		  	var del_report_cell=cell("TD","A","EDIT","EDIT","Delete",2,"","javascript:del(3,'"+BENEFICIARY_SNO+"',"+BENEFICIARY_TYPE_SNO+")","font-size: 15px","10%","right","","");
		   
		  	new_row.appendChild(BENEFICIARY_TYPE_SNO_cell);
 		    new_row.appendChild(SNO_cell);
 		    new_row.appendChild(NAME_cell);
 		    new_row.appendChild(TYPE_cell);
 		    new_row.appendChild(qty_cell);
 		   new_row.appendChild(href_report_cell);
 		   
 		    if (pr_record==0)
 		    	new_row.appendChild(href_all_cell);
 		    else
 		    	new_row.appendChild(temp_cell);	
 		   
 		  //  new_row.appendChild(freeze_report_cell);
 		   if (pr_record==0)
 			   new_row.appendChild(href_demand_cell);
 		   else
 			  new_row.appendChild(temp_cell);
 		   if (pr_record==0)
 			   new_row.appendChild(del_report_cell);
 		   else
 			  new_row.appendChild(temp_cell2);	

 		    tbody.appendChild(new_row);
 		    
		}
	  
	}
	 
	if (command=='delete')
	{
		 var msg = bR.getElementsByTagName("msg")[0].firstChild.nodeValue;
		 var del_row = bR.getElementsByTagName("del_row")[0].firstChild.nodeValue;
		 alert(msg+"\n-----------------------------\n"+"affeceted rows is " +del_row );
		 
		
		 
	}
	if (command=='FR')
	{
		 var msg = bR.getElementsByTagName("msg")[0].firstChild.nodeValue;
		 var up_row = bR.getElementsByTagName("up_row")[0].firstChild.nodeValue;
		 alert(msg+"\n-----------------------------\n"+"affeceted rows is " +up_row );
		 
		
		 
	}
	
	
}


function del(process,sno)
{
	list('delete',process,sno)
}
function frz(process,sno)
{
	list('FR',process,sno)
}
