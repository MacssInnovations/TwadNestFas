var seq = 0;
var othersec=0;
var com_id;
var other_id;
function ADD_GRID() {
	
		
	if(document.getElementById("energytype").value == "")
	{
		alert("Please Enter Energy Type");
		return false;
	}
	energychargecalulate();
	afterrebatecalulate();
	
	var tbody = document.getElementById("grid_body");
	var t = 0;
	var items = new Array();
	items[0] = document.getElementById("energytype").value;
	
	items[1] = document.getElementById("energytariff").value;
	items[2] = document.getElementById("energyunit").value;
	items[3] = document.getElementById("energycharge").value;
	items[4] = document.getElementById("rebate").value;
	items[5] = document.getElementById("afterrebate").value;
		
	
	tbody = document.getElementById("grid_body");
	var mycurrent_row = document.createElement("TR");
	seq = seq + 1;
	mycurrent_row.id = seq;
	var cell = document.createElement("TD");
	var anc = document.createElement("A");
	var url = "javascript:loadTable('" + mycurrent_row.id + "')";
	anc.href = url;
	var txtedit = document.createTextNode("EDIT");
	anc.appendChild(txtedit);
	cell.appendChild(anc);
	mycurrent_row.appendChild(cell);
	var i = 0;
	var cell2;
	cell2 = document.createElement("TD");
	var currentText = document.createTextNode(items[0]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var currentText = document.createTextNode(items[1]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var currentText = document.createTextNode(items[2]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");

	var currentText = document.createTextNode(items[3]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");

	var currentText = document.createTextNode(items[4]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");

	var currentText = document.createTextNode(items[5]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	
	tbody.appendChild(mycurrent_row);
	clear();
}


function ADD_GRID_OTHER() {
	
	
	
	
	if(document.getElementById("description").value == "")
	{
		alert("Please Enter Description");
		return false;
	}
	if(document.getElementById("amount").value == "")
	{
		alert("Please Enter Amount");
		return false;
	}
	
	
	
	var tbody = document.getElementById("grid_body_other");
	var t = 0;
	var items = new Array();
	if (document.eb_bill_consumption.addorless[0].checked == true)
		items[0] = "Add";
	else if (document.eb_bill_consumption.addorless[1].checked == true)
		items[0] = "Less";
		
	items[1] = document.getElementById("description").value;
	items[2] = document.getElementById("amount").value;

	tbody = document.getElementById("grid_body_other");
	var mycurrent_row = document.createElement("TR");
	othersec = othersec + 1;
  var	othersec1=othersec+"A";
	mycurrent_row.id = othersec1;
	var cell = document.createElement("TD");
	var anc = document.createElement("A");
	var url = "javascript:loadTableother('" + mycurrent_row.id + "')";
	anc.href = url;
	var txtedit = document.createTextNode("EDIT");
	anc.appendChild(txtedit);
	cell.appendChild(anc);
	mycurrent_row.appendChild(cell);
	var i = 0;
	var cell2;
	cell2 = document.createElement("TD");
	var currentText = document.createTextNode(items[0]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var currentText = document.createTextNode(items[1]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var currentText = document.createTextNode(items[2]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);


	tbody.appendChild(mycurrent_row);
	clearother();
}


function clear() {
	
	document.getElementById("energytype").value = "";
	document.getElementById("energytariff").value = "";
	document.getElementById("energyunit").value = "";
	document.getElementById("energycharge").value = "";
	document.getElementById("rebate").value = "";
	document.getElementById("afterrebate").value = "";
	
	document.eb_bill_consumption.cmdupdate.style.display = 'none';
	document.eb_bill_consumption.cmddelete.disabled = true;
	document.eb_bill_consumption.cmdadd.style.display = 'block';
}

function loadTable(scod) {
	com_id = scod; // to identify in UPDATE_GRID ,which row loaded
	clear();
	
	var r = document.getElementById(scod);
	var rcells = r.cells;
	try {
		
		document.getElementById("energytype").value = rcells.item(1).firstChild.nodeValue;
		
	} catch (e) {
	}
	
	
	try {
		document.getElementById("energytariff").value = rcells.item(2).firstChild.nodeValue;
	} catch (e) {
	}

	
	try {
		document.getElementById("energyunit").value =rcells.item(3).firstChild.nodeValue;
	} catch (e) {
	}
	
	try {
		document.getElementById("energycharge").value = rcells.item(4).firstChild.nodeValue;
	} catch (e) {
	}
	
	try {
		document.getElementById("rebate").value =rcells.item(5).firstChild.nodeValue;
	} catch (e) {
	}
	
	try {
		document.getElementById("afterrebate").value =rcells.item(6).firstChild.nodeValue;
	} catch (e) {
	}
	document.eb_bill_consumption.cmdupdate.style.display = 'block';
	document.eb_bill_consumption.cmddelete.disabled = false;
	document.eb_bill_consumption.cmdadd.style.display = 'none';
}

function update_GRID()
{
	var tbody = document.getElementById("grid_body");
	var t = 0;
	var items = new Array();
	
	items[0] = document.getElementById("energytype").value;
	
	items[1] = document.getElementById("energytariff").value;
	items[2] = document.getElementById("energyunit").value;
	items[3] = document.getElementById("energycharge").value;
	items[4] = document.getElementById("rebate").value;
	items[5] = document.getElementById("afterrebate").value;

	var r=document.getElementById(com_id);
	var rcells=r.cells;
       
		try{rcells.item(1).firstChild.nodeValue=items[0];}catch(e){}
            
        try{rcells.item(2).firstChild.nodeValue=items[1];}catch(e){}
             
        try{rcells.item(3).firstChild.nodeValue=items[2];}catch(e){}
      
        try{rcells.item(4).firstChild.nodeValue=items[3];}catch(e){}
       
        try{rcells.item(5).firstChild.nodeValue=items[4];}catch(e){}
            
        try{rcells.item(6).firstChild.nodeValue=items[5];}catch(e){}
      
      
 
      alert("Record Updated");
      clear();

}

function delete_GRID()
{
        if(confirm("Do you want to delete ?"))
        {
        var tbody=document.getElementById("mytable");
        var r=document.getElementById(com_id);
        var ri=r.rowIndex;
        tbody.deleteRow(ri);
        clear();
        }
}




function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    if(blr_flag==1)             // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
    {
             call_clr();
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
             var TB_date=fromcal_dateCtrl.value;
             //alert(fromcal_dateCtrl.value+"b4url")
             if(fromcal_dateCtrl.value.length!=0)
             {
                 var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                        //alert(url);
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
                 //doFunction('load_Receipt_No','null');                 //return true;
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
                    dateCtrl.focus();
                  //  document.getElementById("txtCrea_date").value="";
               }
             else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");//return false;//
                    dateCtrl.focus();
                   // document.getElementById("txtCrea_date").value="";     
               }
        }
    }
}



function addvalues()
{
		
	if(nullcheck())
		
	{
	
	
	
	xmlhttp=getxmlhttpObject();
	var unit=document.getElementById("cmbAcc_UnitCode").value;
	var officeid=document.getElementById("cmbOffice_code").value;
	
	var serviceno=document.getElementById("seviceno").value;
	var billno=document.getElementById("billno").value;
		
	var billdate=document.getElementById("billdate").value;
	
	var cashmonth=document.getElementById("cashmonth").value;
	
	var cashyear=document.getElementById("cashyear").value;
	var duedate=document.getElementById("duedate").value;
	var billreceived=document.getElementById("billreceived").value;
	var tranloss=document.getElementById("tranloss").value;
	var transcapacity=document.getElementById("transcapacity").value;
	
	var energycharges=document.getElementById("energycharges").value;
	var demandtariff=document.getElementById("demandtariff").value;
	var demandunit=document.getElementById("demandunit").value;
	var totaldemand=document.getElementById("totaldemand").value;
	var addother=document.getElementById("addother").value;
	var lessother=document.getElementById("lessother").value;
	var netvalue=document.getElementById("netvalue").value;
			
	var remark=document.getElementById("txtRemarks").value;
	
		
	 var url="../../../../../EB_Bill_Consumption_Details?command=Add&unit="+unit+"&officeid="+officeid+"&serviceno="+serviceno+"&billno="+billno+"&billdate="+billdate+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"&duedate="+duedate+"&billreceived="+billreceived+"&tranloss="+tranloss+"&transcapacity="+transcapacity+"&energycharges="+energycharges+"";
	 url=url+"&demandtariff="+demandtariff+"&demandunit="+demandunit+"&totaldemand="+totaldemand+"&addother="+addother+"&lessother="+lessother+"&netvalue="+netvalue+"&remark="+remark+"";

	url=url+"&sid="+Math.random();
	xmlhttp.open("GET",url,false);
 // xmlhttp.onreadystatechange=stateChanged;
	xmlhttp.send(null);
	
	
	var currRow = document.getElementById("grid_body").rows.length;
	//alert(currRow);
	
	for(i=0;i<currRow;i++){
		idle1();

	var energytype=document.getElementById("grid_body").rows[i].cells.item(1).firstChild.nodeValue;
	var energytariff=document.getElementById("grid_body").rows[i].cells.item(2).firstChild.nodeValue;
	var energyunit=document.getElementById("grid_body").rows[i].cells.item(3).firstChild.nodeValue;
	var energycharge=document.getElementById("grid_body").rows[i].cells.item(4).firstChild.nodeValue;
	var rebate=document.getElementById("grid_body").rows[i].cells.item(5).firstChild.nodeValue;
	var afterrebate=document.getElementById("grid_body").rows[i].cells.item(6).firstChild.nodeValue;
	
	
     var url="../../../../../EB_Bill_Consumption_Details?command=Addtrnenergy&unit="+unit+"&officeid="+officeid+"&serviceno="+serviceno+"&billno="+billno+"&energytype="+energytype+"&energytariff="+energytariff+"&energyunit="+energyunit+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"&energycharge="+energycharge+"&rebate="+rebate+"&afterrebate="+afterrebate+"";
	 
              

url=url+"&sid="+Math.random();
xmlhttp.open("GET",url,false);
//xmlhttp.onreadystatechange=stateChanged;
xmlhttp.send(null);  	
	
	}

	 currRow = document.getElementById("grid_body_other").rows.length;
	//alert(currRow);
	
	for(i=0;i<currRow;i++){
	var addorless=document.getElementById("grid_body_other").rows[i].cells.item(1).firstChild.nodeValue;
	var description=document.getElementById("grid_body_other").rows[i].cells.item(2).firstChild.nodeValue;
	var amount=document.getElementById("grid_body_other").rows[i].cells.item(3).firstChild.nodeValue;
	
     var url="../../../../../EB_Bill_Consumption_Details?command=Addtrnother&unit="+unit+"&officeid="+officeid+"&serviceno="+serviceno+"&billno="+billno+"&addorless="+addorless+"&description="+description+"&amount="+amount+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"";
	 
              

url=url+"&sid="+Math.random();
xmlhttp.open("GET",url,false);
//xmlhttp.onreadystatechange=stateChanged;
xmlhttp.send(null);  	
	
	}
	
	
	
	
	alert('Added Successfully');
	clearAll();
	}  
}

function idle1(){
	
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

function clearAll()
{
	document.getElementById("billno").value = "";
	document.getElementById("billdate").value = "";
    document.getElementById("cashmonth").options[0].selected='selected';
	document.getElementById("cashyear").value = "";
	document.getElementById("duedate").value = "";
	document.getElementById("billreceived").value = "";
	document.getElementById("tranloss").value = "";
	
	document.getElementById("transcapacity").value = "";
	document.getElementById("energycharges").value = "";
	document.getElementById("demandtariff").value = "";
	document.getElementById("demandunit").value = "";
	document.getElementById("totaldemand").value = "";
	document.getElementById("addother").value = "";
	document.getElementById("lessother").value = "";
	document.getElementById("netvalue").value = "";
	document.getElementById("txtRemarks").value = "";
	
	var tbody=document.getElementById("grid_body");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
	
    var tbody=document.getElementById("grid_body_other");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
    
    
	document.eb_bill_consumption.cmdadd.style.display='block';
	 document.eb_bill_consumption.cmdupdate.style.display='none';
	 document.eb_bill_consumption.cmddelete.disabled=true;
	 
	 document.eb_bill_consumption.cmdupdateother.style.display = 'none';
		document.eb_bill_consumption.cmddeleteother.disabled = true;
		document.eb_bill_consumption.cmdaddother.style.display = 'block';
}



function valuecalculated()
{
	
	
	if(document.getElementById("demandtariff").value == "")
	{
	alert("Please Enter Demand Tariff");
	return false;
	}
	if(document.getElementById("demandunit").value == "")
	{
	alert("Please Enter Demand Units");
	return false;
	}
	
	if(document.getElementById("demandtariff").value != "" && document.getElementById("demandunit").value != "")
	{
		var X=parseFloat(document.getElementById("demandtariff").value);
		var Y=parseFloat(document.getElementById("demandunit").value);
		var res=rouncalculate(X*Y);
		document.getElementById("totaldemand").value= res;
		
	}
	
	
}

function addlesscheck()
{
		
	
	var currRow = document.getElementById("grid_body").rows.length;
	//alert(currRow);
	var charge_after_rebate=0;
	for(i=0;i<currRow;i++){
		
	var energytype=document.getElementById("grid_body").rows[i].cells.item(6).firstChild.nodeValue;
	
	charge_after_rebate=parseFloat(charge_after_rebate)+parseFloat(energytype);
	
	}
	
	
	if(parseFloat(charge_after_rebate)!=parseFloat(document.getElementById("energycharges").value))
	{
		alert("Total Energy Charge After Rebate value is Not equal to Total Energy Charges (A) value");
		return false;
	}
	
	
	
	 currRow = document.getElementById("grid_body_other").rows.length;
	//alert(currRow);
	var Add=0;
	var Less=0;
	for(i=0;i<currRow;i++){
		
	var energytype=document.getElementById("grid_body_other").rows[i].cells.item(1).firstChild.nodeValue;
	var amount=document.getElementById("grid_body_other").rows[i].cells.item(3).firstChild.nodeValue;
	
	if(energytype=="Add")
	{
		Add=parseFloat(Add)+parseFloat(amount);
	}
	if(energytype=="Less")
	{
		Less=parseFloat(Less)+parseFloat(amount);
	}
	
	}

	if(parseFloat(document.getElementById("addother").value)!=parseFloat(Add))
	{
		alert("Add total not equal to Add Others(C) Value");
		return false;
	}
	if(parseFloat(document.getElementById("lessother").value)!=parseFloat(Less))
	{
		alert("Less total not equal to Less Others(D) Value");
		return false;
	}
		
	return true;
}


function netvalues()
{
	
	if(document.getElementById("energycharges").value == "")
	{
	alert("Please Enter Total Energy Charges (A)");
	return false;
	}
	valuecalculated();
	if(document.getElementById("addother").value == "")
	{
	alert("Please Enter Add Others (C)");
	return false;
	}
	if(document.getElementById("lessother").value == "")
	{
	alert("Please Enter Less Others (D)");
	return false;
	}
	if(document.getElementById("totaldemand").value != "" && document.getElementById("addother").value != "" && document.getElementById("lessother").value != "")
	{
		var A=parseFloat(document.getElementById("energycharges").value);
		var B=parseFloat(document.getElementById("totaldemand").value);
		
		var C=document.getElementById("addother").value;
		var D=document.getElementById("lessother").value;
		
		document.getElementById("netvalue").value= parseFloat(A)+parseFloat(B)+parseFloat(C)-parseFloat(D);
		
	}
	
	
}

function loadTableother(scod) {
	other_id = scod; // to identify in UPDATE_GRID ,which row loaded
	clearother();

	var r = document.getElementById(other_id);
	var rcells = r.cells;
	try {
		
		if(rcells.item(1).firstChild.nodeValue=="Add")
		document.eb_bill_consumption.addorless[0].checked='checked';
		else if(rcells.item(1).firstChild.nodeValue=="Less")
			document.eb_bill_consumption.addorless[1].checked='checked';
		
	} catch (e) {
	}
	
	try {
		document.getElementById("description").value = rcells.item(2).firstChild.nodeValue;
	} catch (e) {
	}
	try {
		document.getElementById("amount").value = rcells.item(3).firstChild.nodeValue;
	} catch (e) {
	}
	
	document.eb_bill_consumption.cmdupdateother.style.display = 'block';
	document.eb_bill_consumption.cmddeleteother.disabled = false;
	document.eb_bill_consumption.cmdaddother.style.display = 'none';
	
}

function clearother()
{
	document.eb_bill_consumption.addorless[0].checked='checked';
	document.getElementById("description").value="";
	document.getElementById("amount").value="";
	
	document.eb_bill_consumption.cmdupdateother.style.display = 'none';
	document.eb_bill_consumption.cmddeleteother.disabled = true;
	document.eb_bill_consumption.cmdaddother.style.display = 'block';
}



function update_GRID_OTHER()
{
	var tbody = document.getElementById("grid_body_other");
	var t = 0;
	var items = new Array();
	if (document.eb_bill_consumption.addorless[0].checked == true)
		items[0] = "Add";
	else if (document.eb_bill_consumption.addorless[1].checked == true)
		items[0] = "Less";
	items[1] = document.getElementById("description").value;
	items[2] = document.getElementById("amount").value;
	
	var r=document.getElementById(other_id);
	var rcells=r.cells;
       
		try{rcells.item(1).firstChild.nodeValue=items[0];}catch(e){}
            
        try{rcells.item(2).firstChild.nodeValue=items[1];}catch(e){}
             
        try{rcells.item(3).firstChild.nodeValue=items[2];}catch(e){}
      
        
 
      alert("Record Updated");
      clearother();

}

function delete_GRID_OTHER()
{
        if(confirm("Do you want to delete ?"))
        {
        var tbody=document.getElementById("mytable_other");
        var r=document.getElementById(other_id);
        var ri=r.rowIndex;
        tbody.deleteRow(ri);
        clearother();
        }
}

function energychargecalulate()
{
	if(document.getElementById("energytariff").value == "")
	{
	alert("Please Enter Total Energy Tariff (ET)");
	return false;
	}
	
	if(document.getElementById("energyunit").value == "")
	{
	alert("Please Enter Energy unit (EU)");
	return false;
	}
	
	if(document.getElementById("energytariff").value != "" && document.getElementById("energyunit").value != "")
	{
		var ET=parseFloat(document.getElementById("energytariff").value);
		var EU=parseFloat(document.getElementById("energyunit").value);
		
		var res=rouncalculate(ET*EU);
		document.getElementById("energycharge").value= res;
		
	}
}



function afterrebatecalulate()
{
	energychargecalulate();
	if(document.getElementById("rebate").value == "")
	{
	alert("Please Enter Rebate value (RB)");
	return false;
	}
	
	if(document.getElementById("energycharge").value != "" && document.getElementById("rebate").value != "")
	{
		var EC=parseFloat(document.getElementById("energycharge").value);
		var RB=parseFloat(document.getElementById("rebate").value);
		var res=rouncalculate(EC-RB);
		document.getElementById("afterrebate").value= res;
		
	}
}

function nullcheck()
{
	
	if(document.getElementById("billno").value=="")
	{
		alert("Please Enter Bill No");
  	    return false; 
	}
	
	if(document.getElementById("billdate").value=="")
	{
		alert("Please Enter Bill Date");
  	    return false; 
	}
		
	 if(document.getElementById("cashyear").value.length==0)
	    {
		 alert("Please Enter For the Year");
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
	    }
	
	if(document.getElementById("tranloss").value=="")
	{
		alert("Please Enter Tranformer Loss");
  	    return false; 
	}
	if(document.getElementById("transcapacity").value=="")
	{
		alert("Please Enter Tranformer Capacity");
  	    return false; 
	}
	
	
	netvalues();
	
	
	var tbody=document.getElementById("grid_body");
	if(tbody.rows.length==0)
	{
	    alert("Please Enter Energy Details Part");
	  	    return false; 
	}
	
	tbody=document.getElementById("grid_body_other");
	if(tbody.rows.length==0)
	{
	    alert("Please Enter Others Part");
	  	    return false; 
	}
	
	
return	addlesscheck();
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


function rouncalculate(num)
{
//num = 162.295
num *= 100; // 16229.499999999998
num = Math.round(num); // 16229
num /= 100; // 162.29

return num;

}
