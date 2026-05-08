var process_code=0;
function data_show_pump(command,process,input_value)
{
	 
	 process_code=process;
	 
	 if (command=='show')  
 	{    
		 document.getElementById("pr_status").value=0;; 
       if (process==1 || process==2) url="../../../../../Beneficiary_DCB_ob?command="+command+"&input_value="+input_value+"&process_code="+process;
       
 	}   
	 if (command=='prv_show')  
	 	{    	  
		     url="../../../../../Pumping_Return_List?month="+document.getElementById("month").value+
		 	"&year="+document.getElementById("year").value+
		 	"&METER_SNO="+document.getElementById("METER_SNO").value+
		 	"&ben_sno="+document.getElementById("BENEFICIARY_SNO").value+"&command="+command+"";	      
		   
	 	}
	  var xmlobj=createObject();
     xmlobj.open("GET",url,true);
     xmlobj.onreadystatechange=function()
     {
      result_process(xmlobj,command,input_value);
     }
     xmlobj.send(null);
}
function result_process(xmlobj,command,input_value)
{
	 
 if (xmlobj.readyState==4)
 {	   
		 
	if (xmlobj.status==200)
    {
		if (command=='show')
		{
        show(xmlobj,input_value);
		}
		if (command=='prv_show')
		{
        show_prv_show(xmlobj,input_value);
		}
		 
    }
  }
    
}
function show(xmlobj,input_value)
{
	var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
	if (process_code==1 || process_code==2)
	{
		 document.getElementById("pr_status").value=1;; 
		var len=bR.getElementsByTagName("sno").length;
			
			for (i=0;i<len;i++)
			{
				var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
			    var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
			    addOption(document.getElementById(input_value),name,sno)
		    }
	      
	}
} 
function show_prv_show(xmlobj,input_value)
{	 
	var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
		document.getElementById("ben_value").innerHTML=bR.getElementsByTagName("Ben_name")[0].firstChild.nodeValue;
		document.getElementById("location").innerHTML=bR.getElementsByTagName("METRE_LOCATION")[0].firstChild.nodeValue;
		document.getElementById("Prv_Metre_Fix").innerHTML=bR.getElementsByTagName("Prv_Metre_Fix")[0].firstChild.nodeValue;
		document.getElementById("Prv_Metre_Work").innerHTML=bR.getElementsByTagName("Prv_Metre_Work")[0].firstChild.nodeValue;
		document.getElementById("Prv_In_Read").innerHTML=bR.getElementsByTagName("Prv_In_Read")[0].firstChild.nodeValue;
		document.getElementById("Prv_Cls_Read").innerHTML=bR.getElementsByTagName("Prv_Cls_Read")[0].firstChild.nodeValue;
		document.getElementById("Prv_Qty_Cons").innerHTML=bR.getElementsByTagName("Prv_Qty_Cons")[0].firstChild.nodeValue;
		 
		document.getElementById("metertype").innerHTML=bR.getElementsByTagName("METRE_TYPE")[0].firstChild.nodeValue;
		 
	
}
 