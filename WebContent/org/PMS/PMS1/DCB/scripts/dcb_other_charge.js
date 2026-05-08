var process_code=0;
var input_row_no=0;
var display_table="";
var display_body="";
var sub_flag=0;
var add_flag=0;



function charge_show(command,process,input_value)
{
	process_code=process;
	if (command=='show')  
 	{    	 
       if (process==1 || process==2) url="../../../../../Dcb_other_charge?command="+command+"&input_value="+input_value+"&process_code="+process_code
 	}
	if (command=='add')  
	{
		if (process_code==3)
		{
			var len=document.getElementById("rowcnt").value;
			var type="typevalue"+(parseInt(len)+1);			
			
			url="../../../../../Dcb_other_charge";
			url+="?command="+command;
			url+="&process_code="+process_code;			 
			url+="&typevalue="+document.getElementById(type).value;//MONTH
			 
		}
		if (process_code==4)
		{
			var len=document.getElementById("rowcnt").value;
			for (i=1;i<=(parseInt(len)-1);i++)
			{
			try
			{
				
			}catch(e)
			{
				
				
			}
				
			}
			
			var type="typevalue"+(parseInt(len)+1);			
			
			url="../../../../../Dcb_other_charge";
			url+="?command="+command;
			url+="&process_code="+process_code;			 
			url+="&typevalue="+document.getElementById(type).value;//MONTH
			 
		}
	}
	 
	 
	var xmlobj=createObject();
    xmlobj.open("GET",url,true);
    xmlobj.onreadystatechange=function()
    { 
    	 
    	charge_process(xmlobj,command,input_value);
    	
    }
    xmlobj.send(null);
}
function charge_process(xmlobj,command,input_value)
{	 
	 
	 
 if (xmlobj.readyState==4)
 {	   
 	 
	if (xmlobj.status==200)
    {
	
			if(command=='show')
			{	
				charge_result(xmlobj,'show');
			}
			if(command=='add')
			{
				alert("Process Completed.... ")
				charge_show('show',1,'0')
			 	window.location.reload();
			 	
				
			}
    }
 }
}

function charge_result(xmlobj,command)
{	
		var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
		 
		var tbody = document.getElementById("charge_body");
		var table = document.getElementById("charge_data");                                                                                                     
		var t=0;
		for(t=tbody.rows.length-1;t>=0;t--){tbody.deleteRow(0);}
		
		
		var len=""
		try
		{
			len=bR.getElementsByTagName("row")[0].firstChild.nodeValue;
		}catch(e){len=0;}
		document.getElementById("rowcnt").value=len;
		 
		 for (i=0;i<len;i++)
		 { 
			 
			 var TYPE_SNO = bR.getElementsByTagName("TYPE_SNO")[i].firstChild.nodeValue;
			 var TYPE_DESC = bR.getElementsByTagName("TYPE_DESC")[i].firstChild.nodeValue;
			 
	 	 	  var new_row=cell("TR","","","row1",1,2,"","","","","","","");//13
	 	 	  
	 	 	  var check_cell=cell("TD","input","checkbox","ch"+(i+1),0,7,"","","","2%","","onclick","charge_select("+(i+1)+")");
	 	 	  var TYPE_SNO_cell=cell("TD","input","hidden","typesno"+(i+1),TYPE_SNO,7,"","","font-size:14px;","2%","center","","");
	 	      var sno_cell=cell("TD","label","","",(i+1),7,"","","font-size:14px;","","left","","");
	 		  var TYPE_DESC_cell=cell("TD","input","text","typevalue"+(i+1),TYPE_DESC,50,"","","font-size:14px;","","left","","");
	 		  
	 		  new_row.appendChild(TYPE_SNO_cell);
	 		  new_row.appendChild(sno_cell);
	 		  new_row.appendChild(check_cell);
	 		  new_row.appendChild(TYPE_DESC_cell);
	 		  tbody.appendChild(new_row);
		 }
		 
		  var new_row1=cell("TR","","","row1",1,2,"","","","","","","");//13
		  var new_sno_cell=cell("TD","label","","",(parseInt(len)+1),7,"","","font-size:14px;","","left","","");
		  var blank_sno_cell=cell("TD","label","","","",7,"","","font-size:14px;","","left","","");
		  var new_TYPE_DESC_cell=cell("TD","input","text","typevalue"+(parseInt(len)+1),"",50,"","","font-size:14px;","","left","","");
		  new_row1.appendChild(new_sno_cell);
		  
		  new_row1.appendChild(blank_sno_cell);
		  new_row1.appendChild(new_TYPE_DESC_cell);
		  
		  tbody.appendChild(new_row1);
		 	 
}