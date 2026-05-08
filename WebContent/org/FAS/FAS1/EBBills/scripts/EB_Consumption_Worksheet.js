var seq = 0;

var com_id;

function ADD_GRID() {
	
	var tbody = document.getElementById("grid_body");
	var t = 0;
	var items = new Array();
	
	if (document.eb_bill_worksheet.addorless[0].checked == true)
		items[0] = "Add";
	else if (document.eb_bill_worksheet.addorless[1].checked == true)
		items[0] = "Less";
	
		
	items[1] = document.getElementById("consdesc").value;
	items[2] = document.getElementById("normal").value;
	items[3] = document.getElementById("peak").value;
	items[4] = document.getElementById("offpeak").value;
	items[5] = document.getElementById("netconstot").value;
		
	
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


function loadTable(scod) {
	com_id = scod; // to identify in UPDATE_GRID ,which row loaded
	clear();
	
	var r = document.getElementById(scod);
	var rcells = r.cells;
	try {
		
		if(rcells.item(1).firstChild.nodeValue=="Add")
		{
			document.eb_bill_worksheet.addorless[0].checked='Checked'; 
		}else if(rcells.item(1).firstChild.nodeValue=="Less")
		{
			document.eb_bill_worksheet.addorless[1].checked='Checked'; 
		}
			
		
	} catch (e) {
	}
	
	
	try {
		document.getElementById("consdesc").value = rcells.item(2).firstChild.nodeValue;
	} catch (e) {
	}

	
	try {
		document.getElementById("normal").value =rcells.item(3).firstChild.nodeValue;
	} catch (e) {
	}
	
	try {
		document.getElementById("peak").value = rcells.item(4).firstChild.nodeValue;
	} catch (e) {
	}
	
	try {
		document.getElementById("offpeak").value =rcells.item(5).firstChild.nodeValue;
	} catch (e) {
	}
	
	try {
		document.getElementById("netconstot").value =rcells.item(6).firstChild.nodeValue;
	} catch (e) {
	}
	document.eb_bill_worksheet.cmdupdate.style.display = 'block';
	document.eb_bill_worksheet.cmddelete.disabled = false;
	document.eb_bill_worksheet.cmdadd.style.display = 'none';
}

function update_GRID()
{
	var tbody = document.getElementById("grid_body");
	var t = 0;
	var items = new Array();
	
	if (document.eb_bill_worksheet.addorless[0].checked == true)
		items[0] = "Add";
	else if (document.eb_bill_worksheet.addorless[1].checked == true)
		items[0] = "Less";
	
		
	items[1] = document.getElementById("consdesc").value;
	items[2] = document.getElementById("normal").value;
	items[3] = document.getElementById("peak").value;
	items[4] = document.getElementById("offpeak").value;
	items[5] = document.getElementById("netconstot").value;

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





function clear() {
	
	document.eb_bill_worksheet.addorless[0].checked='Checked'; 
	document.getElementById("consdesc").value = "";
	document.getElementById("normal").value = "";
	document.getElementById("peak").value = "";
	document.getElementById("offpeak").value = "";
	document.getElementById("netconstot").value = "";
	
	document.eb_bill_worksheet.cmdupdate.style.display = 'none';
	document.eb_bill_worksheet.cmddelete.disabled = true;
	document.eb_bill_worksheet.cmdadd.style.display = 'block';
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


function valuecalulate()
{
	
	if(document.getElementById("normal").value == "")
	{
		alert("Please Enter Normal");
		return false;
	}
	
	if(document.getElementById("peak").value == "")
	{
		alert("Please Enter Peak");
		return false;
	}
	if(document.getElementById("offpeak").value == "")
	{
		alert("Please Enter Off-Peak");
		return false;
	}
	
	if(document.getElementById("normal").value != "" && document.getElementById("peak").value != "" && document.getElementById("offpeak").value != "")
	{
		document.getElementById("netconstot").value=parseFloat(document.getElementById("normal").value)+parseFloat(document.getElementById("peak").value)+parseFloat(document.getElementById("offpeak").value);
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
	
	var circlecode=document.getElementById("circlecode").value;
	
	var powercut="";
	if (document.eb_bill_worksheet.powercut[0].checked == true)
		powercut = "Y";
	else if (document.eb_bill_worksheet.powercut[1].checked == true)
		powercut = "N";
	
	
	
	var netnormal=document.getElementById("netnormal").value;
	var netpeak=document.getElementById("netpeak").value;
	var netoffpeak=document.getElementById("netoffpeak").value;
	
	var nettotal=document.getElementById("nettotal").value;
	var kvah=document.getElementById("kvah").value;
	var rkah=document.getElementById("rkah").value;
	var powerfactor=document.getElementById("powerfactor").value;
				
	var remark=document.getElementById("txtRemarks").value;
	
		
	 var url="../../../../../EB_Consumption_Worksheet?command=Add&unit="+unit+"&officeid="+officeid+"&serviceno="+serviceno+"&billno="+billno+"&billdate="+billdate+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"&circlecode="+circlecode+"&powercut="+powercut+"&netnormal="+netnormal+"&netpeak="+netpeak+"&netoffpeak="+netoffpeak+"";
	 url=url+"&nettotal="+nettotal+"&kvah="+kvah+"&rkah="+rkah+"&powerfactor="+powerfactor+"&remark="+remark+"";

	url=url+"&sid="+Math.random();
	xmlhttp.open("GET",url,false);
 // xmlhttp.onreadystatechange=stateChanged;
	xmlhttp.send(null);
	
	
	var currRow = document.getElementById("grid_body").rows.length;
	//alert(currRow);
	
	for(i=0;i<currRow;i++){
		
	var addorless=document.getElementById("grid_body").rows[i].cells.item(1).firstChild.nodeValue;
	var consdesc=document.getElementById("grid_body").rows[i].cells.item(2).firstChild.nodeValue;
	var normal=document.getElementById("grid_body").rows[i].cells.item(3).firstChild.nodeValue;
	var peak=document.getElementById("grid_body").rows[i].cells.item(4).firstChild.nodeValue;
	var offpeak=document.getElementById("grid_body").rows[i].cells.item(5).firstChild.nodeValue;
	var netconstot=document.getElementById("grid_body").rows[i].cells.item(6).firstChild.nodeValue;
	
	
     var url="../../../../../EB_Consumption_Worksheet?command=Addtrn&unit="+unit+"&officeid="+officeid+"&serviceno="+serviceno+"&billno="+billno+"&addorless="+addorless+"&consdesc="+consdesc+"&normal="+normal+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"&peak="+peak+"&offpeak="+offpeak+"&netconstot="+netconstot+"";
	 
              

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
function nullcheck()
{

	if( document.getElementById("billno").value=="")
	{
		alert('Please Enter Bill No');
		return false;
	}
	if( document.getElementById("billdate").value=="")
	{
		alert('Please Enter Bill Date');
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
	
	
	
	
	if( document.getElementById("circlecode").value=="")
	{
		alert('Please Enter Circle Code');
		return false;
	}
	
	netconscalulate();
	
	if( document.getElementById("kvah").value=="")
	{
		alert('Please Enter KVAH');
		return false;
	}
	if( document.getElementById("rkah").value=="")
	{
		alert('Please Enter RKAH');
		return false;
	}
	
	
	var tbody=document.getElementById("grid_body");
	if(tbody.rows.length==0)
	{
	    alert("Please Enter Work Sheet Details Part");
	  	    return false; 
	}
	
	
	
	return addlesscheck();
}


function clearAll()
{
     document.getElementById("billno").value="";
	document.getElementById("billdate").value="";
	 document.getElementById("cashmonth").options[0].selected='selected';
	document.getElementById("cashyear").value="";
	document.getElementById("circlecode").value="";
	document.eb_bill_worksheet.powercut[0].checked ='checked';
	document.getElementById("netnormal").value="";
	document.getElementById("netpeak").value="";
	document.getElementById("netoffpeak").value="";
	document.getElementById("nettotal").value="";
	document.getElementById("kvah").value="";
	document.getElementById("rkah").value="";
	document.getElementById("powerfactor").value="";
	document.getElementById("txtRemarks").value="";
	
	var tbody=document.getElementById("grid_body");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
    
    
    document.eb_bill_worksheet.cmdadd.style.display='block';
	 document.eb_bill_worksheet.cmdupdate.style.display='none';
	 document.eb_bill_worksheet.cmddelete.disabled=true;
    

}


function addlesscheck()
{
	
	 currRow = document.getElementById("grid_body").rows.length;
	//alert(currRow);
	var Add=0;
	var Less=0;
	var addnormal=0;
	var lessnormal=0;
	var addpeak=0;
	var lesspeak=0;
	var addoffpeak=0;
	var lessoffpeak=0;
	
	for(i=0;i<currRow;i++){
		
	var energytype=document.getElementById("grid_body").rows[i].cells.item(1).firstChild.nodeValue;
	var nor=document.getElementById("grid_body").rows[i].cells.item(3).firstChild.nodeValue;
	var pea=document.getElementById("grid_body").rows[i].cells.item(4).firstChild.nodeValue;
	var off=document.getElementById("grid_body").rows[i].cells.item(5).firstChild.nodeValue;
	var amount=document.getElementById("grid_body").rows[i].cells.item(6).firstChild.nodeValue;
	if(energytype=="Add")
	{
		Add=parseFloat(Add)+parseFloat(amount);
		addnormal=parseFloat(addnormal)+parseFloat(nor);
		addpeak=parseFloat(addpeak)+parseFloat(pea);
		addoffpeak=parseFloat(addoffpeak)+parseFloat(off);
	}
	if(energytype=="Less")
	{
		Less=parseFloat(Less)+parseFloat(amount);
		lessnormal=parseFloat(lessnormal)+parseFloat(nor);
		lesspeak=parseFloat(lesspeak)+parseFloat(pea);
		lessoffpeak=parseFloat(lessoffpeak)+parseFloat(off);
	}
	
	}

	var netcons=parseFloat(Add)-parseFloat(Less);
	var netconnormal=parseFloat(addnormal)-parseFloat(lessnormal);
	var netconpeak=parseFloat(addpeak)-parseFloat(lesspeak);
	var netconoff=parseFloat(addoffpeak)-parseFloat(lessoffpeak);
	
	
	if(parseFloat(document.getElementById("netnormal").value)!=parseFloat(netconnormal))
	{
		alert("Net Consumption (Normal) is not equal to Break Up Total Net Consumption (Normal) total value");
		return false;
	}
	
	if(parseFloat(document.getElementById("netpeak").value)!=parseFloat(netconpeak))
	{
		alert("Net Consumption (Peak) is not equal to Break Up Total Net Consumption (Peak) total value");
		return false;
	}
	
	if(parseFloat(document.getElementById("netoffpeak").value)!=parseFloat(netconoff))
	{
		alert("Net Consumption (Off-Peak) is not equal to Break Up Total Net Consumption (Off-Peak) total value");
		return false;
	}
	
	
	if(parseFloat(document.getElementById("nettotal").value)!=parseFloat(netcons))
	{
		alert("Net Consumption is not equal to Break Up Total Net Consumption total value");
		return false;
	}
			
	return true;
}

function netconscalulate()
{
	
	if(document.getElementById("netnormal").value == "")
	{
		alert("Please Enter Net Consumption (Normal)");
		return false;
	}
	
	if(document.getElementById("netpeak").value == "")
	{
		alert("Please Enter Net Consumption (Peak)");
		return false;
	}
	if(document.getElementById("netoffpeak").value == "")
	{
		alert("Please Enter Net Consumption (Off-Peak)");
		return false;
	}
	
	if(document.getElementById("netnormal").value != "" && document.getElementById("netpeak").value != "" && document.getElementById("netoffpeak").value != "")
	{
		document.getElementById("nettotal").value=parseFloat(document.getElementById("netnormal").value)+parseFloat(document.getElementById("netpeak").value)+parseFloat(document.getElementById("netoffpeak").value);
	}
	
	
}
