
var window_empbill;

function ListHeads()
{ 
 
 
        if (window_empbill && window_empbill.open && !window_empbill.closed) 
         {
        	window_empbill.resizeTo(500,500);
        	window_empbill.moveTo(250,250); 
        	window_empbill.focus();
         }
         else
         {
        	 window_empbill=null;
         }
        
        var acc_unit=document.getElementById("cmbAcc_UnitCode").value;
    	var office_id=document.getElementById("cmbOffice_code").value;
        
    	window_empbill= window.open("Emp_Bill_Journalisation_List.jsp?acc_unit="+acc_unit+"&office_id="+office_id+"","mywindow1","resizable=YES, scrollbars=yes"); 
    	window_empbill.moveTo(250,250);    
 
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
        	
//        	var cashmonth=document.getElementById("cashmonth").value;
//        	var cashyear=document.getElementById("cashyear").value;
        	        	
        	var majortype=document.getElementById("majortype").value;
        	
        	var minortype=document.getElementById("minortype").value;
        	var journaltype=document.getElementById("journaltype").value;
        	 	
        	var remarks=document.getElementById("txtRemarks").value;
        	
        	  var url="../../../../../Emp_Bill_Journalisation?command=Add&acc_unit="+acc_unit+"&office_id="+office_id+"&majortype="+majortype+"&minortype="+minortype+"&journaltype="+journaltype+"&remarks="+remarks+"";
        	  url=url+"&sid="+Math.random();
             //     alert(url);
              xmlhttp.open("GET",url,true);
              xmlhttp.onreadystatechange=stateChanged;
              xmlhttp.send(null);  
        	}
        	
        }
        
        else if(command=="Update")
        { 
        	if(nullcheck()){
        		var acc_unit=document.getElementById("cmbAcc_UnitCode").value;
        		var acc_unit=document.getElementById("cmbAcc_UnitCode").value;
            	var office_id=document.getElementById("cmbOffice_code").value;
            	
//            	var cashmonth=document.getElementById("cashmonth").value;
//            	var cashyear=document.getElementById("cashyear").value;
            	        	
            	var majortype=document.getElementById("majortype").value;
            	
            	var minortype=document.getElementById("minortypehiddenval").value;
            	var journaltype=document.getElementById("journaltype").value;
            	 	
            	var remarks=document.getElementById("txtRemarks").value;
            	
        	
        	  var url="../../../../../Emp_Bill_Journalisation?command=Update&acc_unit="+acc_unit+"&office_id="+office_id+"&majortype="+majortype+"&minortype="+minortype+"&journaltype="+journaltype+"&remarks="+remarks+"";
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
            	
//            	var cashmonth=document.getElementById("cashmonth").value;
//            	var cashyear=document.getElementById("cashyear").value;
            	        	
            	var majortype=document.getElementById("majortype").value;
            	
            	var minortype=document.getElementById("minortypehiddenval").value;
            	var journaltype=document.getElementById("journaltype").value;
        	
        	  var url="../../../../../Emp_Bill_Journalisation?command=Delete&acc_unit="+acc_unit+"&office_id="+office_id+"&majortype="+majortype+"&minortype="+minortype+"&journaltype="+journaltype+"";
        	  url=url+"&sid="+Math.random();
              xmlhttp.open("GET",url,true);
              xmlhttp.onreadystatechange=stateChanged;
              xmlhttp.send(null);  
            }
        	
        }
        
        else if(command=="get")
        { 
        	
        	var majortype=document.getElementById("majortype").value;
        	
        	
        	  var url="../../../../../Emp_Bill_Journalisation?command=get&majortype="+majortype+"";
        	 
        	  url=url+"&sid="+Math.random();
              xmlhttp.open("GET",url,true);
              xmlhttp.onreadystatechange=stateChanged;
              xmlhttp.send(null);  
            
        	
        } 
        else if(command=="getminor")
        { 
        	
        	var majortype=document.getElementById("majortype").value;
        	
        	
        	  var url="../../../../../Emp_Bill_Journalisation?command=getminor&majortype="+majortype+"";
        	 
        	  url=url+"&sid="+Math.random();
              xmlhttp.open("GET",url,true);
              xmlhttp.onreadystatechange=stateChanged;
              xmlhttp.send(null);  
            
        	
        } 
        
}

function ClearAll()
{
	
	
	//document.getElementById("cashyear").value="";
	
	
	//document.emp_bill_journal.cashmonth[0].selected='selected';
	
	var minortype=document.getElementById("minortype");
	 clear_Combo(minortype);
	document.emp_bill_journal.majortype[0].selected='selected';
	document.emp_bill_journal.journaltype[0].selected='selected';
	
	
	
	
	document.getElementById("txtRemarks").value="";
	
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
    
    
	document.getElementById("minortype").disabled=false;
    document.getElementById("majortype").disabled=false;
	document.getElementById("journaltype").disabled=false;
	//document.getElementById("cashyear").disabled=false;
	//document.getElementById("cashmonth").disabled=false;
}

function nullcheck()
{
	
	
/*	if(document.getElementById("cashyear").value=="")
	{
		alert('Please Enter Year');
		return false;
	}else 
    { v=new Date();
	 mn=v.getMonth();
	 mn=parseInt(mn)+1;
	 yr=v.getFullYear();
	
   	var cashyear=document.getElementById("cashyear").value;	
   	var cashmonth=document.getElementById("cashmonth").value;
   	
   	
   	if(cashyear.length<4)
   	{
   		 alert("Give Correct format(YYYY) of Year");
		    return false;
   	}
   		    	
   	if(parseInt(cashyear)>parseInt(yr))
   	{
   		 alert("Year should not be greater than current year");
   		    return false;
   	}
   	 if(parseInt(cashyear)==parseInt(yr))
   	 {
   		 if(parseInt(cashmonth)>parseInt(mn))
   		 {
   			 alert("Month should not be greater than current month");
    		    return false; 
   		 }
   	 }
   }  */
	
	if(document.getElementById("majortype").value=="")
	{
		alert('Please Select Major Type');
		return false;
	}
	
	
	
	if(document.getElementById("journaltype").value=="")
	{
		alert('Please Enter Journal Type');
		return false;
	}
	
	/*if(document.getElementById("txtRemarks").value==""||document.getElementById("txtRemarks").value.length==0)
	{
		alert('Please Enter Remarks');
		return false;
	}  */

	return true;
}

function doParentEmpbill(majortype,minortype,journaltype,remarks)
{
	 var d=document.getElementById("cmdUpdate");
     d.style.display="block";
     var d2=document.getElementById("cmdDelete");
     d2.style.display="block";
     var d1=document.getElementById("cmdAdd");
     d1.style.display="none";    
     
     if(remarks=='null')
    	 remarks=""; 
     
 	document.getElementById("txtRemarks").value=remarks;
 	//document.getElementById("cashyear").value=cashyear;
 	var lent=document.emp_bill_journal.majortype.length;
	
	for(var i=0;i<lent;i++)
	{
		if(document.emp_bill_journal.majortype[i].value==majortype)	
		{
			document.emp_bill_journal.majortype[i].selected='selected';	
		}
	}
	
	call('getminor');
	
	
	
	lent=document.emp_bill_journal.journaltype.length;
	
	for(var i=0;i<lent;i++)
	{
		if(document.emp_bill_journal.journaltype[i].value==journaltype)	
		{
			document.emp_bill_journal.journaltype[i].selected='selected';	
		}
	}
	
  
	
	
	
/* lent=document.emp_bill_journal.cashmonth.length;
	
	for(var i=0;i<lent;i++)
	{
		if(document.emp_bill_journal.cashmonth[i].value==cashmonth)	
		{
			document.emp_bill_journal.cashmonth[i].selected='selected';	
		}
	}  */

	document.getElementById("majortype").disabled=true;
	document.getElementById("journaltype").disabled=true;
//	document.getElementById("cashyear").disabled=true;
//	document.getElementById("cashmonth").disabled=true;
	document.getElementById("minortype").disabled=true;
	
	
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

function clear_Combo(combo)
{
        //alert(combo.id)
        var cmbSL_Code=document.getElementById(combo.id);   
        cmbSL_Code.innerHTML="";
       var option=document.createElement("OPTION");
        option.text="--Select Minor Type--";
        option.value="";
        try
        {
            cmbSL_Code.add(option);
        }catch(errorObject)
        {
            cmbSL_Code.add(option,null);
        } 
}


function idle(){

	for(var i=0;i<50;i++)
	{
		
	}
	
}
