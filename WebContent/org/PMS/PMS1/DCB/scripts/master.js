
/*
 *  process 
 * 		1	- District
 * */
var process_code=0;
var command="";
 function report(command,process,id,input_value)
 {  
	 var url="";
	 process_code=process;
	 command=command;
	 var xmlobj=createObject();
	 url="";
	  
	 if (process_code==1) 
	  {	
		 url="../../../../../report?process_code="+process_code+"&input_value="+input_value+"&command=show";
	  }
	 else if (process_code==2)
	 {
		 comboRemove(id)
		 url="../../../../../report?process_code="+process_code+"&input_value="+input_value.value+"&command=show";
		 
	 }
		 if (url!="")
		 {
			  
				 xmlobj.open("GET",url,true);
			     xmlobj.onreadystatechange=function()
			     {
			    	 report_result_master(xmlobj,id);
			    	 
			     }
			     xmlobj.send(null);
		 }
 }
 
 function  report_result_master(xmlobj,id)
 {
	
  if (xmlobj.readyState==4)
   { 
	 
   if (xmlobj.status==200)
     {     
         	 if (process_code==1 || process_code==2) 
        	 {  
        		 report_show_master(xmlobj,id);
        		  
        	 }
     }
   }
     
 }
 
 function report_show_master(xmlobj,id)
 {
  var bR=xmlobj.responseXML.getElementsByTagName("result")[0];

	var len=bR.getElementsByTagName("sno").length;
	var status=bR.getElementsByTagName("status")[0].firstChild.nodeValue;
	 
	if (len==0)
	alert(status+"\n-------------------------------")
	
	
	for (i=0;i<len;i++)
	{
	
		var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
		var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
		addOption(document.getElementById(id),name,sno)
	}
	 
 }