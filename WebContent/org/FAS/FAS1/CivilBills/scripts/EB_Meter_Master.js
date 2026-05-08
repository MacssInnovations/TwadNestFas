
var window_ebmaster;

function ListHeads()
{ 
 
 
        if (window_ebmaster && window_ebmaster.open && !window_ebmaster.closed) 
         {
        	window_ebmaster.resizeTo(500,500);
        	window_ebmaster.moveTo(250,250); 
        	window_ebmaster.focus();
         }
         else
         {
        	 window_ebmaster=null
         }
        
        var acc_unit=document.getElementById("cmbAcc_UnitCode").value;
    	var office_id=document.getElementById("cmbOffice_code").value;
        
        window_ebmaster= window.open("EB_Meter_Master_List.jsp?acc_unit="+acc_unit+"&office_id="+office_id+"","mywindow1","resizable=YES, scrollbars=yes"); 
        window_ebmaster.moveTo(250,250);    
 
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

            if(command=="Add")
            {
            	
                if(flag=='success')
                {
                  
                	alert("Record inserted into database");
                	ClearAll();
                }
                else
                    {
                	alert("Record could not be inserted into database");
                    }
                    
                   
            } if(command=="Update")
            {
            	
                if(flag=='success')
                {
                  
                	alert("Record Updated into database");
                	
                	ClearAll();
                }
                else
                    {
                    
                    }
                    
                   
            }if(command=="Delete")
            {
            	
                if(flag=='success')
                {
                  
                	alert("Record Deleted successfully");
                	 
                	ClearAll();
                }
                else
                    {
                    
                    }
                    
                   
            }
       }
    }
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
        	var acc_unit=document.getElementById("cmbAcc_UnitCode").value;
        	var office_id=document.getElementById("cmbOffice_code").value;
        	var serviceno=document.getElementById("serviceno").value;
        	var tariff=document.getElementById("tariff").value;
        	var permittedmd=document.getElementById("permittedmd").value;
        	var meterno=document.getElementById("meterno").value;
        	var supplydate=document.getElementById("supplydate").value;
        	var effectivefrom=document.getElementById("effectivefrom").value;
        	var effectiveupto=document.getElementById("effectiveupto").value;
        	var connectiontype="";
        	var htlt="";
        	if(document.master_ebmeter.typeconnection[0].checked)
            {
        		connectiontype="I";
            }else  if(document.master_ebmeter.typeconnection[1].checked){
            	connectiontype="C";
            }
        	
        	if(document.master_ebmeter.htlt[0].checked)
            {
        		htlt="HT";
            }else  if(document.master_ebmeter.htlt[1].checked){
            	htlt="LT";
            }
        	
        	var remarks=document.getElementById("txtRemarks").value;
        	
        	  var url="../../../../../EB_Meter_Master?command=Add&acc_unit="+acc_unit+"&office_id="+office_id+"&serviceno="+serviceno+"&tariff="+tariff+"&permittedmd="+permittedmd+"&meterno="+meterno+"&supplydate="+supplydate+"&effectivefrom="+effectivefrom+"&effectiveupto="+effectiveupto+"&connectiontype="+connectiontype+"&remarks="+remarks+"&htlt="+htlt+"";
        	  url=url+"&sid="+Math.random();
              xmlhttp.open("GET",url,true);
              xmlhttp.onreadystatechange=stateChanged;
              xmlhttp.send(null);  
        	}
        	
        }
        
        else if(command=="Update")
        { 
        	if(nullcheck()){
        	var acc_unit=document.getElementById("cmbAcc_UnitCode").value;
        	var office_id=document.getElementById("cmbOffice_code").value;
        	var serviceno=document.getElementById("serviceno").value;
        	var tariff=document.getElementById("tariff").value;
        	var permittedmd=document.getElementById("permittedmd").value;
        	var meterno=document.getElementById("meterno").value;
        	var supplydate=document.getElementById("supplydate").value;
        	var effectivefrom=document.getElementById("effectivefrom").value;
        	var effectiveupto=document.getElementById("effectiveupto").value;
        	var connectiontype="";
        	var htlt="";
        	if(document.master_ebmeter.typeconnection[0].checked)
            {
        		connectiontype="I";
            }else  if(document.master_ebmeter.typeconnection[1].checked){
            	connectiontype="C";
            }
        	if(document.master_ebmeter.htlt[0].checked)
            {
        		htlt="HT";
            }else  if(document.master_ebmeter.htlt[1].checked){
            	htlt="LT";
            }
        	var remarks=document.getElementById("txtRemarks").value;
        	
        	  var url="../../../../../EB_Meter_Master?command=Update&acc_unit="+acc_unit+"&office_id="+office_id+"&serviceno="+serviceno+"&tariff="+tariff+"&permittedmd="+permittedmd+"&meterno="+meterno+"&supplydate="+supplydate+"&effectivefrom="+effectivefrom+"&effectiveupto="+effectiveupto+"&connectiontype="+connectiontype+"&remarks="+remarks+"&htlt="+htlt+"";
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
        	var acc_unit=document.getElementById("cmbAcc_UnitCode").value;
        	var office_id=document.getElementById("cmbOffice_code").value;
        	var serviceno=document.getElementById("serviceno").value;
        	
        	
        	  var url="../../../../../EB_Meter_Master?command=Delete&acc_unit="+acc_unit+"&office_id="+office_id+"&serviceno="+serviceno+"";
        	  url=url+"&sid="+Math.random();
              xmlhttp.open("GET",url,true);
              xmlhttp.onreadystatechange=stateChanged;
              xmlhttp.send(null);  
            }
        	
        }
        
        
        
        
}

function ClearAll()
{
	 document.getElementById("serviceno").disabled=false;
	document.getElementById("serviceno").value="";
	document.getElementById("tariff").value="";
	document.getElementById("permittedmd").value="";
	document.getElementById("meterno").value="";
	document.getElementById("supplydate").value="";
	document.getElementById("effectivefrom").value="";
	document.getElementById("effectiveupto").value="";
	document.master_ebmeter.typeconnection[0].checked='checked';
	document.getElementById("txtRemarks").value="";
	
	var d=document.getElementById("cmdAdd");
    d.style.display="block";
    var d1=document.getElementById("cmdUpdate");
    d1.style.display="none";
    var d3=document.getElementById("cmdDelete");
    d3.style.display="none";
	
}

function nullcheck()
{

	if(document.getElementById("serviceno").value==""||document.getElementById("serviceno").value.length==0)
	{
		alert('Please Enter Service No.');
		return false;
	}

/*	if(document.getElementById("tariff").value==""||document.getElementById("tariff").value.length==0)
	{
		alert('Please Enter Tariff App/BLd');
		return false;
	}
	
	
	if(document.getElementById("permittedmd").value==""||document.getElementById("permittedmd").value.length==0)
	{
		alert('Please Enter Permitted ');
		return false;
	}
	
	if(document.getElementById("meterno").value==""||document.getElementById("meterno").value.length==0)
	{
		alert('Please Enter Meter Serial No.');
		return false;
	}
	
	if(document.getElementById("supplydate").value==""||document.getElementById("supplydate").value.length==0)
	{
		alert('Please Enter Date of Supply');
		return false;
	}
	
	
	if(document.getElementById("effectivefrom").value==""||document.getElementById("effectivefrom").value.length==0)
	{
		alert('Please Enter Effective from');
		return false;
	}
	if(document.getElementById("effectiveupto").value==""||document.getElementById("effectiveupto").value.length==0)
	{
		alert('Please Enter Effective upto');
		return false;
	}
	if(document.getElementById("txtRemarks").value==""||document.getElementById("txtRemarks").value.length==0)
	{
		alert('Please Enter Remarks');
		return false;
	}*/
	return true;
}

function doParentEBMaster(serviceno,tariff,permittedmd,meterno,supplydate,connectiontype,effectivefrom,effectiveupto,remark,htlt)
{
	 var d=document.getElementById("cmdUpdate");
     d.style.display="block";
     var d2=document.getElementById("cmdDelete");
     d2.style.display="block";
     var d1=document.getElementById("cmdAdd");
     d1.style.display="none";    
     
     document.getElementById("serviceno").value=serviceno;
     document.getElementById("serviceno").disabled=true;
 	
     if(tariff=='null')
    	 tariff="";
     if(permittedmd==0)
    	 permittedmd="";
     if(meterno=='null')
    	 meterno="";
     if(supplydate=='null')
    	 supplydate="";
     if(effectivefrom=='null')
    	 effectivefrom="";
     if(effectiveupto=='null')
    	 effectiveupto="";
     if(remark=='null')
    	 remark=""; 
     
     
     document.getElementById("tariff").value=tariff;
 	document.getElementById("permittedmd").value=permittedmd;
 	document.getElementById("meterno").value=meterno;
 	document.getElementById("supplydate").value=supplydate;
 	document.getElementById("effectivefrom").value=effectivefrom;
 	document.getElementById("effectiveupto").value=effectiveupto;
 	document.getElementById("txtRemarks").value=remark;
 	
 	if(connectiontype=="Industrial"){
 	document.master_ebmeter.typeconnection[0].checked='checked';
 	}
 	else{
 		document.master_ebmeter.typeconnection[1].checked='checked';
 	}
   
 	if(htlt=="HT"){
 	 	document.master_ebmeter.htlt[0].checked='checked';
 	 	}
 	 	else{
 	 		document.master_ebmeter.htlt[1].checked='checked';
 	 	}
	
}

