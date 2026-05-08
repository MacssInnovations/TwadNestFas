var seq=1;

function AjaxFunction() {
	var xmlrequest = false;
	try {
		xmlrequest = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e1) {
		try {
			xmlrequest = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			xmlrequest = false;
		}
	}
	if (!xmlrequest && typeof XMLHttpRequest != 'undefined') {
		xmlrequest = new XMLHttpRequest();
	}
	return xmlrequest;
}

function fun_Live()
{
	
     var liveFY=document.getElementById("liveFY").value;
	 var liveTY=document.getElementById("liveTY").value;
	 var fromMonth=document.getElementById("fromMonth").value;
	 var toMonth=document.getElementById("toMonth").value;
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	 document.getElementById("cmd").value="live";
     var url="../../../../../Fas_trialData_Details?cmd=live&liveFY="+liveFY+"&liveTY="+liveTY+
     "&fromMonth="+fromMonth+"&toMonth="+toMonth+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
  //   alert("mmmm >>> "+url);
 	document.frmFas_Details.action = url;
	document.frmFas_Details.method = "GET";
	document.frmFas_Details.submit();
}
function fun_fzdLive()
{
	//alert('testfzd');	
     var fzdFY=document.getElementById("fzdFY").value;
	 var fzdTY=document.getElementById("fzdTY").value;
	 var fromfzdMonth=document.getElementById("fromfzdMonth").value;
	 var tofzdMonth=document.getElementById("tofzdMonth").value;
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	 document.getElementById("cmd").value="fzdlive";
     var url="../../../../../Fas_trialData_Details?command=fzdlive&fzdFY="+fzdFY+"&fzdTY="+fzdTY+
     "&fromfzdMonth="+fromfzdMonth+"&tofzdMonth="+tofzdMonth+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
   //  alert(url);   
 	document.frmFas_Details.action = url;
	document.frmFas_Details.method = "GET";
	document.frmFas_Details.submit();	
}

 function chk_fromYr(val,ID)
 {
	var f_year= document.getElementById("finYear").value;
	if (ID==1)
	{
 if(val!=f_year.split("-")[0]&&val!=f_year.split("-")[1])
	 {
	   document.getElementById("txtCBFrom_Year").value="";
	   document.frmFas_Details.txtCBFrom_Year.focus();
	   alert('Enter From year '+ f_year.split("-")[0]+" or "+val!=f_year.split("-")[1]+ " ...");
	 }
	}
	else if(ID==2)
		{
		 if(val!=f_year.split("-")[1]){
			 document.getElementById("txtCBTo_Year").value="";
			 document.frmFas_Details.txtCBTo_Year.focus();
			 alert('Enter To year '+ f_year.split("-")[0]+" or "+val!=f_year.split("-")[1]+ " ...");
		 }
		}
 }
function chk_month(val,ID)
{
if (ID==1)
{
	 if(val<4){
		 document.getElementById("fromMonth").value="";
		 document.frmFas_Details.fromMonth.focus();
		 alert("Select After March Month  ...");
	}
}else if (ID==2)
{
	 if(val>3){
		 document.getElementById("toMonth").value="";
		 document.frmFas_Details.toMonth.focus();
		 alert("Select Before April Month  ...");
	}
}
}
function onProcess(req)
{
	document.getElementById("imgfld").style.visibility = "hidden";
	var baseResponse = req.responseXML.getElementsByTagName("response")[0];
	var Command = baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
	//alert('Command >>>> '+Command);
	var tbody=document.getElementById("tbList");
	var t = 0,k = 1;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
	var ACCOUNT_HEAD_CODE=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE");
	var length=ACCOUNT_HEAD_CODE.length;
	//alert(length);
	for(var i=0;i<length;i++)
		{
		ACCOUNT_HEAD_CODE=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[i].firstChild.nodeValue;
		var Account_head_desc=baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[i].firstChild.nodeValue;
		var CR_AMOUNT=baseResponse.getElementsByTagName("CR_AMOUNT")[i].firstChild.nodeValue;
		var DR_AMOUNT=baseResponse.getElementsByTagName("DR_AMOUNT")[i].firstChild.nodeValue;
		var Net_Amt=baseResponse.getElementsByTagName("Net_Amt")[i].firstChild.nodeValue;
		var tbody=document.getElementById("tbList");
		var row_cell=document.createElement("TR");
		var td_cell=document.createElement("TD");
		var sl_no_cell=document.createElement("input");
		 sl_no_cell.type="hidden";
		 sl_no_cell.id="sno"+seq;
		 sl_no_cell.name="sno"+seq;
		 sl_no_cell.value=seq;
		 var sl_no_text=document.createTextNode(seq);
		 td_cell.appendChild(sl_no_text);
		 td_cell.appendChild(sl_no_cell);
		 row_cell.appendChild(td_cell);
		 
		 var td_cell1=document.createElement("TD");
		 var head_cell=document.createElement("input");
		 head_cell.type="hidden";
		 head_cell.id="head_code"+seq;
		 head_cell.name="head_code"+seq;
		 head_cell.value=ACCOUNT_HEAD_CODE;
		 var head_text=document.createTextNode(ACCOUNT_HEAD_CODE);
		 td_cell1.appendChild(head_cell);
		 td_cell1.appendChild(head_text);
		 row_cell.appendChild(td_cell1);
		 
		 var td_cell2=document.createElement("TD");
		 var headDsec_cell=document.createElement("input");
		 headDsec_cell.type="hidden";
		 headDsec_cell.id="head_desc"+seq;
		 headDsec_cell.name="head_desc"+seq;
		 headDsec_cell.value=Account_head_desc;
		 var headDesc_text=document.createTextNode(Account_head_desc);
		 td_cell2.appendChild(headDsec_cell);
		 td_cell2.appendChild(headDesc_text);
		 row_cell.appendChild(td_cell2);
		 
		 var td_cell3=document.createElement("TD");
		 var Dr_cell=document.createElement("input");
		 Dr_cell.type="hidden";
		 Dr_cell.id="Dr"+seq;
		 Dr_cell.name="Dr"+seq;
		 Dr_cell.value=DR_AMOUNT;
		 var Dr_text=document.createTextNode(DR_AMOUNT);
		 td_cell3.appendChild(Dr_cell);
		 td_cell3.appendChild(Dr_text);
		 row_cell.appendChild(td_cell3);
		 
		 var td_cell4=document.createElement("TD");
		 var Cr_cell=document.createElement("input");
		 Cr_cell.type="hidden";
		 Cr_cell.id="Cr"+seq;
		 Cr_cell.name="Cr"+seq;
		 Cr_cell.value=CR_AMOUNT;
		 var Cr_text=document.createTextNode(CR_AMOUNT);
		 td_cell4.appendChild(Cr_cell);
		 td_cell4.appendChild(Cr_text);
		 row_cell.appendChild(td_cell4);
		 
		 var td_cell5=document.createElement("TD");
		 var net_cell=document.createElement("input");
		 net_cell.type="hidden";
		 net_cell.id="Net_Amt"+seq;
		 net_cell.name="Net_Amt"+seq;
		 net_cell.value=Net_Amt;
		 var Net_text=document.createTextNode(Net_Amt);
		 td_cell5.appendChild(net_cell);
		 td_cell5.appendChild(Net_text);
		 row_cell.appendChild(td_cell5)	;	 
		 tbody.appendChild(row_cell);		 
		 seq++;
		}
}

function monthLoad(value,type)
{
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
	    			}else if(type=="FYFz")
	    				{
	    				fin_year=document.getElementById("fzdFY").value;
	    				url+="&Fin_year="+fin_year;
	    				}else if(type=="TYFz")
	    		    	   {
	    					fin_year=document.getElementById("fzdTY").value;
	    					url+="&Fin_year="+fin_year;
	    		    	   }else
	    					   {
	    					    alert('Error .....');
	    					   }
	// alert(url);
	     var req = AjaxFunction();
		req.open("POST", url, true);	 	
	 	req.onreadystatechange = function() {
	 	
	 		if(req.readyState==4)
	 			{
	 			if(req.status==200) 				
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
    var fromfzdMonth = document.getElementById("fromfzdMonth");    
    var tofzdMonth = document.getElementById("tofzdMonth");  
	if(Type=="FYL")
		fromMonth.length=0;   
	if(Type=="TYL")
		  toMonth.length=0;    
	if(Type=="FYFz")
		fromfzdMonth.length=0;
	if(Type=="TYFz")
		tofzdMonth.length=0;
    var option_count=baseResponse.getElementsByTagName("year");                       
    for(var i=0;i<option_count.length;i++)
    {  
    	year=baseResponse.getElementsByTagName("year")[i].firstChild.nodeValue;          
    	 var month=baseResponse.getElementsByTagName("month")[i].firstChild.nodeValue;         
         var month_desc=baseResponse.getElementsByTagName("month_desc")[i].firstChild.nodeValue;
        var option=document.createElement("OPTION");   
       // alert(month);
        option.text=month_desc;
        option.value=month;
        //alert(month+'-'+year);
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
            if(Type=="FYFz")
                {fromfzdMonth.add(option);}
            if(Type=="TYFz"){
                 if(i==option_count.length-1)
                   {
                      
                   option.setAttribute("selected", "selected");
                   tofzdMonth.add(option);
                   }
                   else
                       {
                      tofzdMonth.add(option);
                       }
                
            } }

        catch(errorObject)
        {
        	if(Type=="FYL")
        		fromMonth.add(option,null);
        	if(Type=="TYL")
        		toMonth.add(option,null);   
        	if(Type=="FYFz")
        		fromfzdMonth.add(option,null);
        	if(Type=="TYFz")
        		tofzdMonth.add(option,null);
        }   
    }            
}
function LoadAccountingUnitID(COMMAND)
{	
	//alert('load unit');
        command_for_office = COMMAND;
        var url="../../../../../Load_Accounting_Unit_ID.kv?COMMAND="+COMMAND;
      //  alert("fghdh_"+url+"ssssss"+command_for_office);
        var req=AjaxFunction();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
          handle_loadAccountingUnitID(req);
        }        
        req.send(null);
}function handle_loadAccountingUnitID(req)
{
    if(req.readyState==4)
    {
     if(req.status==200)
     {
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
       var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   //alert(flag);
        if(flag=="success")
        { 
            var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode");         
                cmbAcc_UnitCode.length=0;
            var option_count=baseresponse.getElementsByTagName("option");                       
            var root = null;
        
            if(option_count.length>0)
            {
            	var option=document.createElement("OPTION");
            option.text="All";
            option.value=0;
            try
            {   
                cmbAcc_UnitCode.add(option);
            }
            catch(errorObject)
            {
                cmbAcc_UnitCode.add(option,null);
            }   
            //alert(">>>"+option_count.length);
            }
            for(var i=1;i<=option_count.length;i++)
            {  
                var option=document.createElement("OPTION");
                root = baseresponse.getElementsByTagName("option")[i];
                var accounting_unit_id=root.getElementsByTagName("accounting_unit_id")[0].firstChild.nodeValue;
                var accounting_unit_name=root.getElementsByTagName("accounting_unit_name")[0].firstChild.nodeValue;
                option.text=accounting_unit_name+"("+accounting_unit_id+")";
                option.value=accounting_unit_id;
                try
                {   
                    cmbAcc_UnitCode.add(option);
                }
                catch(errorObject)
                {
                    cmbAcc_UnitCode.add(option,null);
                }   
            }            
            /** Load Accounting Office ID */ 
            if ( (command_for_office == "ONLY_UNITS") || (command_for_office=="LIST_ALL_UNITS_ONLY") || (command_for_office=="FOR_LIST_0" ) )
            {
            }
            else
            {
               common_LoadOffice();            
            }         
        }
        else
        {
          alert("Failed to Load Accounting Unit");
        }
                 
     }
    }
}