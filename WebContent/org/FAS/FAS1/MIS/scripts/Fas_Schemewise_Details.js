//alert('test');

function monthLoad(value,type)
{
	//alert("type >>> "+type);
	var fin_year="";
	  var url="../../../../../Fas_trialData_Details?command=loadCmb&Type="+type;
	   if(type=="FYL")
	    		{
	    		fin_year=document.getElementById("liveFY").value;
	    		url+="&Fin_year="+fin_year;
	    		}else if(type=="TYL")
	    			{
	    			fin_year=document.getElementById("liveTY").value;
	    			url+="&Fin_year="+fin_year;
	    			}else
	    					   {
	    					    alert('Error .....');
	    					   }
	//alert(url);
	     var req = AjaxFunction();
		req.open("POST", url, true);	
		document.getElementById("imgfld").style.visibility="visible";
	 	req.onreadystatechange = function() {
	 	
	 		if(req.readyState==4)
	 			{
	 			if(req.status==200)
	 				 document.getElementById("imgfld").style.visibility = "hidden";  
	 			onCmbLoad(req);
	 			}
	 	};
	 	req.send(null);
}

function onCmbLoad(req)
{
	var baseResponse = req.responseXML.getElementsByTagName("response")[0];
	var Command = baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
	var Type = baseResponse.getElementsByTagName("type")[0].firstChild.nodeValue;
    var fromMonth = document.getElementById("fromMonth");    
    var toMonth = document.getElementById("toMonth");    
	if(Type=="FYL")
		fromMonth.length=0;   
	if(Type=="TYL")
		  toMonth.length=0; 	
    var option_count=baseResponse.getElementsByTagName("year");                       
    for(var i=0;i<option_count.length;i++)
    {  
    	year=baseResponse.getElementsByTagName("year")[i].firstChild.nodeValue;          
    	 var month=baseResponse.getElementsByTagName("month")[i].firstChild.nodeValue;         
         var month_desc=baseResponse.getElementsByTagName("month_desc")[i].firstChild.nodeValue;
        var option=document.createElement("OPTION");   
     //  alert(month);
        option.text=month_desc;
        option.value=month;
      //  alert(month+'-'+year);
        try
        {  
            if(Type=="FYL")
                {fromMonth.add(option);}
            if(Type=="TYL"){
                 if(i==option_count.length-1)
                 {
                    
                 option.setAttribute("selected", "selected");
                 toMonth.add(option);
                 }
                 else
                     {
                toMonth.add(option);  
                     }
            }
           }

        catch(errorObject)
        {
        	if(Type=="FYL")
        		fromMonth.add(option,null);
        	if(Type=="TYL")
        		toMonth.add(option,null);   
        	
        }   
    }            
}
