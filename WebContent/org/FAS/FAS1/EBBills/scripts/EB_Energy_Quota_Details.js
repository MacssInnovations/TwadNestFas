
var window_ebenergy;

function ListHeads()
{ 
 
 
        if (window_ebenergy && window_ebenergy.open && !window_ebenergy.closed) 
         {
        	window_ebenergy.resizeTo(500,500);
        	window_ebenergy.moveTo(250,250); 
        	window_ebenergy.focus();
         }
         else
         {
        	 window_ebenergy=null;
         }
        
        var acc_unit=document.getElementById("cmbAcc_UnitCode").value;
    	var office_id=document.getElementById("cmbOffice_code").value;
        
    	window_ebenergy= window.open("EB_Energy_Quota_Details_List.jsp?acc_unit="+acc_unit+"&office_id="+office_id+"","mywindow1","resizable=YES, scrollbars=yes"); 
    	window_ebenergy.moveTo(250,250);    
 
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
        	var billno=document.getElementById("billno").value;
        	var billdate=document.getElementById("billdate").value;
        	var cashmonth=document.getElementById("cashmonth").value;
        	var cashyear=document.getElementById("cashyear").value;
        	var unitstime=document.getElementById("unitstime").value;
        	var unitsquotafixed=document.getElementById("unitsquotafixed").value;
        	        
        	
        	var unitsquotaexcess=document.getElementById("unitsquotaexcess").value;
        	
        	var kvatime=document.getElementById("kvatime").value;
        	var epdunitsfixed=document.getElementById("epdunitsfixed").value;
        	var epdunitsexcess=document.getElementById("epdunitsexcess").value;
        	        	
        	        	
        	var remarks=document.getElementById("txtRemarks").value;
        	
        	  var url="../../../../../EB_Energy_Quota_Details?command=Add&acc_unit="+acc_unit+"&office_id="+office_id+"&serviceno="+serviceno+"&billno="+billno+"&billdate="+billdate+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"&unitstime="+unitstime+"&unitsquotafixed="+unitsquotafixed+"&unitsquotaexcess="+unitsquotaexcess+"&remarks="+remarks+"&epdunitsexcess="+epdunitsexcess+"&epdunitsfixed="+epdunitsfixed+"&kvatime="+kvatime+"";
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
            	var billno=document.getElementById("billno").value;
            	var billdate=document.getElementById("billdate").value;
            	var cashmonth=document.getElementById("cashmonth").value;
            	var cashyear=document.getElementById("cashyear").value;
            	var unitstime=document.getElementById("unitstime").value;
            	var unitsquotafixed=document.getElementById("unitsquotafixed").value;
            	        
            	
            	var unitsquotaexcess=document.getElementById("unitsquotaexcess").value;
            	
            	var kvatime=document.getElementById("kvatime").value;
            	var epdunitsfixed=document.getElementById("epdunitsfixed").value;
            	var epdunitsexcess=document.getElementById("epdunitsexcess").value;
            	        	
            	        	
            	var remarks=document.getElementById("txtRemarks").value;
        	
        	  var url="../../../../../EB_Energy_Quota_Details?command=Update&acc_unit="+acc_unit+"&office_id="+office_id+"&serviceno="+serviceno+"&billno="+billno+"&billdate="+billdate+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"&unitstime="+unitstime+"&unitsquotafixed="+unitsquotafixed+"&unitsquotaexcess="+unitsquotaexcess+"&remarks="+remarks+"&epdunitsexcess="+epdunitsexcess+"&epdunitsfixed="+epdunitsfixed+"&kvatime="+kvatime+"";
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
        	var billno=document.getElementById("billno").value;
        	var cashmonth=document.getElementById("cashmonth").value;
        	var cashyear=document.getElementById("cashyear").value;
        	
        	  var url="../../../../../EB_Energy_Quota_Details?command=Delete&acc_unit="+acc_unit+"&office_id="+office_id+"&serviceno="+serviceno+"&billno="+billno+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"";
        	  url=url+"&sid="+Math.random();
              xmlhttp.open("GET",url,true);
              xmlhttp.onreadystatechange=stateChanged;
              xmlhttp.send(null);  
            }
        	
        }
        
        
        
        
}

function ClearAll()
{
	
	document.getElementById("billno").value="";
	document.getElementById("billdate").value="";
	document.getElementById("cashyear").value="";
	document.getElementById("unitstime").value="";
	document.getElementById("unitsquotafixed").value="";
	document.getElementById("unitsquotaexcess").value="";
	document.getElementById("kvatime").value="";
	document.getElementById("epdunitsfixed").value="";
	document.getElementById("epdunitsexcess").value="";
	
	document.eb_energy.cashmonth[0].selected='selected';

	document.getElementById("txtRemarks").value="";
	
	var d=document.getElementById("cmdAdd");
    d.style.display="block";
    var d1=document.getElementById("cmdUpdate");
    d1.style.display="none";
    var d3=document.getElementById("cmdDelete");
    d3.style.display="none";
    document.getElementById("serviceno").disabled=false;
	document.getElementById("billno").disabled=false;
	document.getElementById("cashyear").disabled=false;
	document.getElementById("cashmonth").disabled=false;
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

function doParentEBMaster(serviceno,billno,billdate,cashmonth,cashyear,unitstime,unitsquotafixed,unitsquotaexcess,kvatime,epdunitsfixed,epdunitsexcess,remarks)
{
	 var d=document.getElementById("cmdUpdate");
     d.style.display="block";
     var d2=document.getElementById("cmdDelete");
     d2.style.display="block";
     var d1=document.getElementById("cmdAdd");
     d1.style.display="none";    
     
   
     
 	
     if(billdate=='null')
    	 billdate="";
     if(unitstime=='null')
    	 unitstime="";
     if(unitsquotafixed=='null')
    	 unitsquotafixed="";
     if(unitsquotaexcess=='null')
    	 unitsquotaexcess="";
     if(kvatime=='null')
    	 kvatime="";
     if(epdunitsfixed=='null')
    	 epdunitsfixed="";
     if(epdunitsexcess=='null')
    	 epdunitsexcess="";
     if(remarks=='null')
    	 remarks=""; 
     document.getElementById("billno").value=billno;
     document.getElementById("cashyear").value=cashyear;
     document.getElementById("billdate").value=billdate;
 	document.getElementById("unitstime").value=unitstime;
 	document.getElementById("unitsquotafixed").value=unitsquotafixed;
 	document.getElementById("unitsquotaexcess").value=unitsquotaexcess;
 	document.getElementById("kvatime").value=kvatime;
 	document.getElementById("epdunitsfixed").value=epdunitsfixed;
 	document.getElementById("epdunitsexcess").value=epdunitsexcess;
 	document.getElementById("txtRemarks").value=remarks;
 	
 	var lent=document.eb_energy.serviceno.length;
	
	for(var i=0;i<lent;i++)
	{
		if(document.eb_energy.serviceno[i].value==serviceno)	
		{
			document.eb_energy.serviceno[i].selected='selected';	
		}
	}

 lent=document.eb_energy.cashmonth.length;
	
	for(var i=0;i<lent;i++)
	{
		if(document.eb_energy.cashmonth[i].value==cashmonth)	
		{
			document.eb_energy.cashmonth[i].selected='selected';	
		}
	}

	document.getElementById("serviceno").disabled=true;
	document.getElementById("billno").disabled=true;
	document.getElementById("cashyear").disabled=true;
	document.getElementById("cashmonth").disabled=true;

}


function filter_real(evt,item,n,pre)
{
         var charCode = (evt.which) ? evt.which : event.keyCode
// allow "." for one time 
         if(charCode==46){
                        //    alert("Position of . "+item.value.indexOf("."));
                                if(item.value.indexOf(".")<0)    return (item.value.length>0)?true:false;
                                else return false;
          }
         if (!(charCode > 31 && (charCode < 48 || charCode > 57))){
                // to avoid over flow
                        if(item.value.indexOf(".")<0){
        //            alert("Length without . ="+item.value.length);
                                return (item.value.length<n)?true:false;
                        }
            // dont allow more than 2 precision no's after the point
                        if(item.value.indexOf(".")>0){
                        //    alert("precision count ="+item.value.split(".")[1].length);
                                if(item.value.split(".")[1].length<pre) return true;
                                else return false;
                        }
                        return false;
        }else{
                        return false;
        }
}
