function getTransport()
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

function offname()
{
        var id=document.getElementById("cmbAcc_UnitCode").value;
        var url="../../../../../Closing_Acc_Unit?Command=offname&id="+id;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
           handleResponse1(req);
        }   
                req.send(null);   
}
function loadDOpen()
{
	    var id=document.getElementById("cmbAcc_UnitCode").value;
        var url="../../../../../Closing_Acc_Unit?Command=loadDOpen&id="+id;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
           handleResponse1(req);
        }   
                req.send(null);   
}
function offname1()
{
        var id=document.getElementById("cmbAcc_UnitCode").value;
        var url="../../../../../Closing_Acc_Unit?Command=offname1&id="+id;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
           handleResponse1(req);
        }   
                req.send(null);   
}

function clr()
{
	document.getElementById("cmbAcc_UnitCode").value=0;
	document.getElementById("cmbOffice_code").length=0;
	document.getElementById("unit_id").length=0;
	document.getElementById("date_close").value="";
	document.getElementById("date_open").value="";
}

function update()
{
	   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	   var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	   var unit=document.getElementById("unit_id").value;
	   var open=document.getElementById("date_open").value;
	   var close=document.getElementById("date_close").value;
	   var flag=check();
       if(flag==true)
       { 
	       var url="../../../../../Closing_Acc_Unit?Command=updat&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&unit="+unit+"&open="+open+"&close="+close;
	       alert(url);
	       var req=getTransport();
	       req.open("GET",url,true);
	       req.onreadystatechange=function()
	       {
	          handleResponse1(req);
	       }   
	               req.send(null);
       }
}

function handleResponse1(req)
{  
    if(req.readyState==4)
    { 
        if(req.status==200)
        {             
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="offnames")
            {
            	offnames(baseResponse);
            }
            else if(Command=="Updated")
            {                
                UpdateRow(baseResponse);
            }
            if(Command=="offnames1")
            {
            	offnames1(baseResponse);
            }
            if(Command=="loadDOpennn")
            {
            	loadDOpenn(baseResponse);
            }
        }
    }
}


function UpdateRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        alert("Record Updated");
        clr();
    }
    else
    {
        alert("Record not Updated");
    }
}

function offnames(baseResponse)
{
	     
//	alert("loading");
	var offcmb = document.getElementById("cmbOffice_code");
	     var unitcmb = document.getElementById("unit_id");
         document.forms[0].cmbOffice_code.length=0;
         //document.forms[0].unit_id.length=0;
         var offid = baseResponse.getElementsByTagName("offid");  
         var offname = baseResponse.getElementsByTagName("offname"); 
         var minorcode = baseResponse.getElementsByTagName("unitid");  
         var minordesc = baseResponse.getElementsByTagName("unitname");
       
            for(var i=0;i<offid.length;i++)
              {     		  
            	     var opt1 = document.createElement('option');
                     opt1.value = offid[i].firstChild.nodeValue;
                     opt1.innerHTML = offname[i].firstChild.nodeValue; 
                     offcmb.appendChild(opt1);
            	    /* var opt2 = document.createElement('option');
                     opt2.value = minorcode[i].firstChild.nodeValue;
                     opt2.innerHTML = minordesc[i].firstChild.nodeValue; 
                     unitcmb.appendChild(opt2);*/
	     	 } 
}
function offnames1(baseResponse)
{
	     //var offcmb = document.getElementById("cmbOffice_code");
	     var unitcmb = document.getElementById("unit_id");
         //document.forms[0].cmbOffice_code.length=0;
         document.forms[0].unit_id.length=0;
         //alert("loading transferring unit***");
         var flag = baseResponse.getElementsByTagName("flag");
         var offid1 = baseResponse.getElementsByTagName("offid1");  
         var offname1 = baseResponse.getElementsByTagName("offname1"); 
         var minorcode1 = baseResponse.getElementsByTagName("unitid1");  
         var minordesc1 = baseResponse.getElementsByTagName("unitname1");
//         if(flag=="success")  
//        	 {
//		     alert("loading transfering unit");    
        	 for(var i=0;i<offid1.length;i++)
		              {     		  
		            	    /* var opt1 = document.createElement('option');
		                     opt1.value = offid[i].firstChild.nodeValue;
		                     opt1.innerHTML = offname[i].firstChild.nodeValue; 
		                     offcmb.appendChild(opt1);*/
		            	     var opt2 = document.createElement('option');
		                     opt2.value = minorcode1[i].firstChild.nodeValue;
		                     opt2.innerHTML = minordesc1[i].firstChild.nodeValue; 
		                     unitcmb.appendChild(opt2);
			     	 } 
//        	 }
}
function loadDOpenn(baseResponse)
{
	     var date_open = document.getElementById("date_open");

	     var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
         var DOOpening = baseResponse.getElementsByTagName("DOOpening")[0].firstChild.nodeValue;  
         if(flag=="success")  
     	 {
		    	 	date_open.value=DOOpening;
     	 }  	
}

function check()
{
	if(document.getElementById("cmbAcc_UnitCode").value==0)
	{
		alert("Enter Accounting Unit Id");
		return false;
	}
	else
		return true;
}

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
	    if(blr_flag==1)                 // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
	    {
	            call_clr();
	            cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	            cmbOffice_code=document.getElementById("cmbOffice_code").value;
	            var TB_date=fromcal_dateCtrl.value;            
	            if(fromcal_dateCtrl.value.length!=0)
	            {
		                 var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
		                 var req=getTransport();
		                 req.open("GET",url,true); 
		                 req.onreadystatechange=function()
		                 {
		                   check_TB(req,fromcal_dateCtrl);
		                 }   
		                 req.send(null);
	            }
	    }
}

function call_date(dateCtrl)                        // TB_checking 
{
	    call_clr();
	    if(checkdt(dateCtrl))
	    {       
		         cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		         cmbOffice_code=document.getElementById("cmbOffice_code").value;
		         var TB_date=dateCtrl.value;
		       
		         if(dateCtrl.value.length!=0)
		         {
			             var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
			             var req=getTransport();
			             req.open("GET",url,true); 
			             req.onreadystatechange=function()
			             {
			               check_TB(req,dateCtrl);
			             }   
			             req.send(null);
		         }
	        
	    }
	    else
	    {
		         var cmbSL_Code=document.getElementById("txtReceipt_No");   
		         cmbSL_Code.innerHTML="";
		         var option=document.createElement("OPTION");
		         option.text="-- Select Receipt Number --";
		         option.value="";
		         try
		         {
		                 cmbSL_Code.add(option);
		         }catch(errorObject)
		         {
		        	 	 cmbSL_Code.add(option,null);
		         }
	    }
}



function check_TB(req,dateCtrl)
{
	    if(req.readyState==4)
	    {
		        if(req.status==200)
		        {  
			             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
			             var tagcommand=baseResponse.getElementsByTagName("command")[0];
			             var Command=tagcommand.firstChild.nodeValue;
			             var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			            
			             if(flag=="success")
			             {
			            	 	doFunction('load_Receipt_No','null');                 //return true;
			             }
			             else if(flag=="failure")
			             {
			                    dateCtrl.value="";
			                    alert("Trial Balance Closed");//return false;//
			                    dateCtrl.focus();
			                    var cmbSL_Code=document.getElementById("txtReceipt_No");   
			                    cmbSL_Code.innerHTML="";
			                    var option=document.createElement("OPTION");
			                    option.text="-- Select Receipt Number --";
			                    option.value="";
			                    try
			                    {
			                        cmbSL_Code.add(option);
			                    }catch(errorObject)
			                    {
			                        cmbSL_Code.add(option,null);
			                    }
			               }
		        }
	    }
}