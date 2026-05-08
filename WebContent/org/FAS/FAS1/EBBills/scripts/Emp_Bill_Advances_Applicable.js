function cashbookyear()
{
var finyear=document.getElementById("finyear");
    var finyear_val=finyear.options[finyear.selectedIndex].value;
    var finyear_arr=new Array(2);
    var now=new Date();
    finyear_val=finyear_val.split("-");
   
    var minorcmb = document.emp_bill_advance.cashyear;
    clear_Combo(minorcmb);
    for(var i=0; i<finyear_val.length; i++){
    if(finyear_val[i]<=now.getFullYear()){
            var opt1 = document.createElement('option');
            opt1.value = finyear_val[i];
            opt1.innerHTML = finyear_val[i];
            minorcmb.appendChild(opt1);
            }
    }
    
}

function cashbookmonth()
{
  
	var year=document.getElementById("cashyear").value;
   /* var now=new Date();
	var currentyear= now.getFullYear();
	var currentmonth=now.getMonth();
	var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
	var monthval=new Array(1,2,3,4,5,6,7,8,9,10,11,12);
    var minorcmb = document.emp_bill_advance.cashmonth;
    clear_Combo(minorcmb);
    if(parseInt(year)<currentyear){
         	for(var i=0;i<12;i++){
            var opt1 = document.createElement('option');
            opt1.value = monthval[i];
            opt1.innerHTML = month[i];
            minorcmb.appendChild(opt1);
    	    }
       }else{
                 	for(var i=0;i<currentmonth+1;i++){
                    var opt1 = document.createElement('option');
                    opt1.value = monthval[i];
                    opt1.innerHTML = month[i];
                    minorcmb.appendChild(opt1);
             	}
            	
            }*/
	
	
	
	 var acyear=document.getElementById("cashyear");
	    var year_combo_len=acyear.length;
	    var year1=acyear.options[1].value;  
	    var year2;
	    if(year_combo_len>2)
	    	year2=acyear.options[2].value;
	    else
	    	year2=0;
	    var sel_year=acyear.options[acyear.selectedIndex].value;
	    var start_months = new Array('APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
	    var start_months_val = new Array(4,5,6,7,8,9,10,11,12);
	    var end_months = new Array('JAN','FEB','MAR');
	    var end_months_val = new Array(1,2,3);
	    var minorcmb = document.emp_bill_advance.cashmonth;
	    clear_Combo(minorcmb);
	    // document.emp_bill_advance.cashmonth.length=1;
	   
	    if(parseInt(sel_year)==parseInt(year1)){
	        for(var i=0; i<start_months.length; i++){
	            var opt1 = document.createElement('option');
	            opt1.value = start_months_val[i];
	            opt1.innerHTML = start_months[i];
	            minorcmb.appendChild(opt1);
	        }
	    }
	    else if(parseInt(sel_year)==parseInt(year2)){
	    for(var i=0; i<end_months.length; i++){
	            var opt1 = document.createElement('option');
	            opt1.value = end_months_val[i];
	            opt1.innerHTML = end_months[i];
	            minorcmb.appendChild(opt1);
	        }
	    }
	    else if(parseInt(sel_year)==0){
	    	alert("select");
	    }
	
	
	
}

function clear_Combo(combo)
{
        //alert(combo.id)
        var cmbSL_Code=document.getElementById(combo.id);   
        cmbSL_Code.innerHTML="";
       var option=document.createElement("OPTION");
        option.text="--Select--";
        option.value="";
        try
        {
            cmbSL_Code.add(option);
        }catch(errorObject)
        {
            cmbSL_Code.add(option,null);
        } 
}

function applicableupto()
{
	 var d=document.getElementById("datediv");
	   
	 if(document.emp_bill_advance.applicable[0].checked==true)
	 {
	 d.style.display="none";
	 }else if(document.emp_bill_advance.applicable[1].checked==true) 
	 {
		 d.style.display="block";
	 }
}


function getxmlhttpObject()
{
 var req = false;
 try 
 {
       req= new ActiveXObject("Msxml2.XMLHTTP");
 }
 catch (e) 
 {
       try 
       {
            req = new ActiveXObject("Microsoft.XMLHTTP");
       }
       catch (e2) 
       {
            req = false;
       }
 }
 if (!req && typeof XMLHttpRequest != 'undefined') 
 {
       req = new XMLHttpRequest();
 }   
 return req;
}


function call(command)
{
	
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }  


if(command=="Add")
{ 
	if(nullcheck()){
	var finyear=document.getElementById("finyear").value;	
	var cashmonth=document.getElementById("cashmonth").value;
	var cashyear=document.getElementById("cashyear").value;
	        	
	var majortype=document.getElementById("majortype").value;
	
	var minortype=document.getElementById("minortype").value;
	var applicable;
	var date="";
	if(document.emp_bill_advance.applicable[0].checked==true)
	{
		applicable="Y";
	}else if(document.emp_bill_advance.applicable[1].checked==true)
	{
		applicable="N";
		date=document.getElementById("date").value;
	}
	
	  var url="../../../../../Emp_Bill_Advances_Applicable?command=Add&finyear="+finyear+"&majortype="+majortype+"&minortype="+minortype+"&applicable="+applicable+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"&date="+date+"";
	  url=url+"&sid="+Math.random();
      xmlhttp.open("GET",url,true);
      xmlhttp.onreadystatechange=stateChanged;
      xmlhttp.send(null);  
	}
	
}

else if(command=="Update")
{ 
	if(nullcheck()){
		var finyear=document.getElementById("finyear").value;	
		var cashmonth=document.getElementById("cashmonth").value;
		var cashyear=document.getElementById("cashyear").value;
		        	
		var majortype=document.getElementById("majortype").value;
		
		var minortype=document.getElementById("minortypehiddenval").value;
		var applicable;
		var date="";
		if(document.emp_bill_advance.applicable[0].checked==true)
		{
			applicable="Y";
		}else if(document.emp_bill_advance.applicable[1].checked==true)
		{
			applicable="N";
			date=document.getElementById("date").value;
		}
		
    	
	
	  var url="../../../../../Emp_Bill_Advances_Applicable?command=Update&finyear="+finyear+"&majortype="+majortype+"&minortype="+minortype+"&applicable="+applicable+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"&date="+date+"";
	  url=url+"&sid="+Math.random();
      xmlhttp.open("GET",url,true);
      xmlhttp.onreadystatechange=stateChanged;
      xmlhttp.send(null);  
	}
	
}


else if(command=="Delete")
{ 
	if(confirm("Do You Really want to Delete it?"))
    {
		
		var finyear=document.getElementById("finyear").value;	
		var cashmonth=document.getElementById("cashmonth").value;
		var cashyear=document.getElementById("cashyear").value;
		        	
		var majortype=document.getElementById("majortype").value;
		
		var minortype=document.getElementById("minortypehiddenval").value;
	
	  var url="../../../../../Emp_Bill_Advances_Applicable?command=Delete&finyear="+finyear+"&majortype="+majortype+"&minortype="+minortype+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"";
	  url=url+"&sid="+Math.random();
      xmlhttp.open("GET",url,true);
      xmlhttp.onreadystatechange=stateChanged;
      xmlhttp.send(null);  
    }
	
}




 if(command=="get")
{ 
	
	var majortype=document.getElementById("majortype").value;
	
	
	  var url="../../../../../Emp_Bill_Advances_Applicable?command=get&majortype="+majortype+"";
	 
	  url=url+"&sid="+Math.random();
      xmlhttp.open("GET",url,true);
      xmlhttp.onreadystatechange=stateChanged;
      xmlhttp.send(null);  
    
	
} 
 
 
 else if(command=="getminor")
 { 
 	
 	var majortype=document.getElementById("majortype").value;
 	
 	
 	  var url="../../../../../Emp_Bill_Advances_Applicable?command=getminor&majortype="+majortype+"";
 	 
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  
     
 	
 } 
 
 
}


function stateChanged()
{
    var flag,command,response;
   
    if(xmlhttp.readyState==4)
    {
    	
       if(xmlhttp.status==200)
       {
            response=xmlhttp.responseXML.getElementsByTagName("response")[0];
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(command=="get")
            {
            	
                if(flag=='success')
                {

                	try{
                		
              		  var len=response.getElementsByTagName("minorcode").length;
                 	var minortype=document.getElementById("minortype");
                 
              	 var items_id=new Array();
              	 var items_desc=new Array();                        
                          for(var i=0;i<len;i++)
                          {
                       	 items_id[i]=response.getElementsByTagName("minorcode")[i].firstChild.nodeValue;
                       	items_desc[i]=response.getElementsByTagName("minordesc")[i].firstChild.nodeValue;  
                       
                          }
                     clear_Combo(minortype);
                    
                          //alert('here second');
                          for(var k=0;k<len;k++)
                          {   
                          	//alert(items_name[k]);
                                var option=document.createElement("OPTION");
                                option.text=items_desc[k];
                                option.value=items_id[k];
                             
                                 try
                                {
                                	 minortype.add(option);
                                	
                                }
                                catch(errorObject)
                                {
                                	minortype.add(option,null);
                                	
                                   // alert('error');
                                }
                          }
              	
              	}catch(e){alert("Error in lat"+e);}      
                
                }
                else
                    {
                    
                                     
                    }
                 }
            
            if(command=="getminor")
            {
            	
                if(flag=='success')
                {
                	
                	document.getElementById("minortypehiddenval").value=response.getElementsByTagName("minorcode")[0].firstChild.nodeValue;
                	document.getElementById("minortypehidden").value=response.getElementsByTagName("minordesc")[0].firstChild.nodeValue;  
                	
                	var d=document.getElementById("minortypehidden");
                    d.style.display="block";
                    var d1=document.getElementById("minortype");
                    d1.style.display="none";
                }
                else
                    {
                    
                    }
                  
            }
            
            if(command=="Add")
            {
            	if(flag=='success')
                {
                  
                	alert("Record Inserted into database");
                	
                	ClearAll();
                }
                else
                    {
                    
                    }
            	
            }
            
            
            
            if(command=="Update")
            {
            	if(flag=='success')
                {
                  
                	alert("Record Updated into database");
                	
                	ClearAll();
                }
                else
                    {
                    
                    }
            	
            }
            
            if(command=="Delete")
            {
            	if(flag=='success')
                {
                  
                	alert("Record Deleted");
                	
                	ClearAll();
                }
                else
                    {
                    
                    }
            	
            }
            
            
       }
    }
}

function nullcheck()
{
	
	if(document.getElementById("finyear").value=="")
	{
		alert('Please Select Financial Year');
		return false;
	}
	if(document.getElementById("cashyear").value=="")
	{
		alert('Please Select Year');
		return false;
	}
	if(document.getElementById("cashmonth").value=="")
	{
		alert('Please Select Month');
		return false;
	}
	if(document.getElementById("majortype").value=="")
	{
		alert('Please Select Major Type');
		return false;
	}

	 if(document.emp_bill_advance.applicable[1].checked==true)
	{
	
		if(document.getElementById("date").value=="")
		{
			alert('Please Enter Date');
			return false;
		}
	}
	
return true;	

}

var window_billadvance;

function ListHeads()
{ 
 
 
        if (window_billadvance && window_billadvance.open && !window_billadvance.closed) 
         {
        	window_billadvance.resizeTo(500,500);
        	window_billadvance.moveTo(250,250); 
        	window_billadvance.focus();
         }
         else
         {
        	 window_billadvance=null;
         }
        
        var finyear=document.getElementById("finyear").value;
    	//var office_id=document.getElementById("cmbOffice_code").value;
        
        if(finyear=="")
        {alert('Please Select Financial Year');
        return flase;
        }
        else{
    	window_billadvance= window.open("Emp_Bill_Advances_Applicable_List.jsp?finyear="+finyear+"","mywindow1","resizable=YES, scrollbars=yes"); 
    	window_billadvance.moveTo(250,250);  
        }
 
}

function doParentEmpadvance(finyear,majortype,minortype,cashmonth,cashyear,applicable,dateupto)
{

	 var d=document.getElementById("cmdUpdate");
     d.style.display="block";
     var d2=document.getElementById("cmdDelete");
     d2.style.display="block";
     var d1=document.getElementById("cmdAdd");
     d1.style.display="none";    
     
     if(dateupto=='null')
    	 dateupto="";
    
   
     document.getElementById("date").value=dateupto;
   
     cashbookyear();
     lent1=document.emp_bill_advance.cashyear.length;
     lent=document.emp_bill_advance.majortype.length;
  	
  	for(var i=0;i<lent;i++)
  	{
  		if(document.emp_bill_advance.majortype[i].value==majortype)	
  		{
  			document.emp_bill_advance.majortype[i].selected='selected';	
  		}
  	}
     
     
     
 	for(var i=0;i<lent1;i++)
 	{		
 		idle();
 		if(document.emp_bill_advance.cashyear.options[i].value==cashyear)	
 		{
 			document.emp_bill_advance.cashyear.options[i].selected='selected';	
 	    }
 	}
 	
   cashbookmonth();
   
   call('getminor');
	
	if(applicable=="Yes")
		document.emp_bill_advance.applicable[0].checked=true;
	if(applicable=="No")
		document.emp_bill_advance.applicable[1].checked=true;
	applicableupto();
   
   lent=document.emp_bill_advance.cashmonth.length;
	
   
	for(var i=0;i<lent;i++)
	{
		if(document.emp_bill_advance.cashmonth[i].value==cashmonth)	
		{
			document.emp_bill_advance.cashmonth[i].selected=true;	
	
		}
	}
  // alert('ok ok');
   
 
   
 	
	
	document.getElementById("finyear").disabled=true;
	document.getElementById("majortype").disabled=true;
	
	document.getElementById("cashyear").disabled=true;
	document.getElementById("cashmonth").disabled=true;
	
}

function ClearAll()
{
	document.emp_bill_advance.finyear[0].selected='selected';
	
	
	var minortype=document.getElementById("minortype");
	 clear_Combo(minortype);
	 var cashyear=document.getElementById("cashyear"); 
	 clear_Combo(cashyear);
	 var cashmonth=document.getElementById("cashmonth"); 
	 clear_Combo(cashmonth);
	 
	 document.emp_bill_advance.applicable[0].checked=true;
	 var d=document.getElementById("datediv");
	 d.style.display="none";
	
	 
	document.emp_bill_advance.majortype[0].selected='selected';
	
		
	var d=document.getElementById("cmdAdd");
    d.style.display="block";
    var d1=document.getElementById("cmdUpdate");
    d1.style.display="none";
    var d3=document.getElementById("cmdDelete");
    d3.style.display="none";
    
    var d=document.getElementById("minortypehidden");
    d.style.display="none";
    var d1=document.getElementById("minortype");
    d1.style.display="block";
    
    document.getElementById("finyear").disabled=false;
	document.getElementById("minortype").disabled=false;
    document.getElementById("majortype").disabled=false;
	
	document.getElementById("cashyear").disabled=false;
	document.getElementById("cashmonth").disabled=false;
}

function idle()
{
	
	for(var i=0;i<50;i++)
 	{
 	var a=6;	
 	}	


}