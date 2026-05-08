var seq = 0;
var othersec=0;
var com_id;
var other_id;
function ADD_GRID() {
	
		
	if(document.getElementById("adjcode").value == "")
	{
		alert("Please Select Adjustment Code");
		return false;
	}
	
	if(document.getElementById("pfincentive").value=="")
	 {
		 alert('Please Enter PF Incentive');
		 return false;
	 }
	 
	 if(document.getElementById("affected").value=="")
	 {
		 alert('Please Enter Adjustment Affected');
		 return false;
	 }
	 if(document.getElementById("notaffected").value=="")
	 {
		 alert('Please Enter Adjustment Not Affected');
		 return false;
	 }
	
	totaladjust()
	
	var tbody = document.getElementById("grid_body");
	var t = 0;
	var items = new Array();
	items[0] = document.getElementById("adjcode").value;
	
	items[1] = document.getElementById("pfincentive").value;
	items[2] = document.getElementById("affected").value;
	items[3] = document.getElementById("notaffected").value;
	items[4] = document.getElementById("totaladj").value;
			
	
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

	
	
	tbody.appendChild(mycurrent_row);
	taxcalculation();
	clear();
	
}




function clear() {
	
	document.getElementById("adjcode").options[0].selected='selected';
	document.getElementById("pfincentive").value = "";
	document.getElementById("affected").value = "";
	document.getElementById("notaffected").value = "";
	document.getElementById("totaladj").value = "";

	
	document.eb_bill_annexure.cmdupdate.style.display = 'none';
	document.eb_bill_annexure.cmddelete.disabled = true;
	document.eb_bill_annexure.cmdadd.style.display = 'block';
}

function loadTable(scod) {
	com_id = scod; // to identify in UPDATE_GRID ,which row loaded
	clear();
	
	var r = document.getElementById(scod);
	var rcells = r.cells;
	try {
		
		
		var code=rcells.item(1).firstChild.nodeValue;
   	 var len=document.eb_bill_annexure.adjcode.length;
		for(i=0;i<len;i++)
		{
			if(document.eb_bill_annexure.adjcode.options[i].value==code)
			{
				document.eb_bill_annexure.adjcode.options[i].selected='selected';
				
				}	
		}
		
	} catch (e) {
	}
	
	
	try {
		document.getElementById("pfincentive").value = rcells.item(2).firstChild.nodeValue;
	} catch (e) {
	}

	
	try {
		document.getElementById("affected").value =rcells.item(3).firstChild.nodeValue;
	} catch (e) {
	}
	
	try {
		document.getElementById("notaffected").value = rcells.item(4).firstChild.nodeValue;
	} catch (e) {
	}
	
	try {
		document.getElementById("totaladj").value =rcells.item(5).firstChild.nodeValue;
	} catch (e) {
	}
	
	
	document.eb_bill_annexure.cmdupdate.style.display = 'block';
	document.eb_bill_annexure.cmddelete.disabled = false;
	document.eb_bill_annexure.cmdadd.style.display = 'none';
}

function update_GRID()
{
	var tbody = document.getElementById("grid_body");
	var t = 0;
	var items = new Array();
	
	items[0] = document.getElementById("adjcode").value;
	
	items[1] = document.getElementById("pfincentive").value;
	items[2] = document.getElementById("affected").value;
	items[3] = document.getElementById("notaffected").value;
	items[4] = document.getElementById("totaladj").value;

	var r=document.getElementById(com_id);
	var rcells=r.cells;
       
		try{rcells.item(1).firstChild.nodeValue=items[0];}catch(e){}
            
        try{rcells.item(2).firstChild.nodeValue=items[1];}catch(e){}
             
        try{rcells.item(3).firstChild.nodeValue=items[2];}catch(e){}
      
        try{rcells.item(4).firstChild.nodeValue=items[3];}catch(e){}
       
        try{rcells.item(5).firstChild.nodeValue=items[4];}catch(e){}
            
      
      
      
        taxcalculation();
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
	
	
	var meterno=document.getElementById("meterno").value;
	var readingdate=document.getElementById("readingdate").value;
	var readtypecode=document.getElementById("readtypecode").value;
	var metertype=document.getElementById("metertype").value;
	
	var finalreading=document.getElementById("finalreading").value;
	var initialreading=document.getElementById("initialreading").value;
	var differencereading=document.getElementById("differencereading").value;
	var mf=document.getElementById("mf").value;
	var comsumption=document.getElementById("comsumption").value;
	var comp_con=document.getElementById("comp_con").value;
	var other_cons=document.getElementById("other_cons").value;
	var avg_cons=document.getElementById("avg_cons").value;
	
	var remark=document.getElementById("txtRemarks").value;
	var circlename=document.getElementById("circlename").value;
		
	 var url="../../../../../EB_Bill_WS_Annexure?command=Addmst&unit="+unit+"&officeid="+officeid+"&serviceno="+serviceno+"&billno="+billno+"&billdate="+billdate+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"&meterno="+meterno+"&readingdate="+readingdate+"&readtypecode="+readtypecode+"&metertype="+metertype+"&finalreading="+finalreading+"";
	 url=url+"&initialreading="+initialreading+"&differencereading="+differencereading+"&mf="+mf+"&comsumption="+comsumption+"&comp_con="+comp_con+"&other_cons="+other_cons+"&avg_cons="+avg_cons+"&remark="+remark+"&circlename="+circlename+"";

	url=url+"&sid="+Math.random();
	xmlhttp.open("GET",url,false);
 // xmlhttp.onreadystatechange=stateChanged;
	xmlhttp.send(null);
	
	
	var currRow = document.getElementById("grid_body").rows.length;
	//alert(currRow);
	
	for(i=0;i<currRow;i++){
		idle1();

	var adjcode=document.getElementById("grid_body").rows[i].cells.item(1).firstChild.nodeValue;
	var pfincentive=document.getElementById("grid_body").rows[i].cells.item(2).firstChild.nodeValue;
	var adjaffected=document.getElementById("grid_body").rows[i].cells.item(3).firstChild.nodeValue;
	var adjnotaffected=document.getElementById("grid_body").rows[i].cells.item(4).firstChild.nodeValue;

     var url="../../../../../EB_Bill_WS_Annexure?command=Addtrn2&unit="+unit+"&officeid="+officeid+"&serviceno="+serviceno+"&billno="+billno+"&adjcode="+adjcode+"&pfincentive="+pfincentive+"&adjaffected="+adjaffected+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"&adjnotaffected="+adjnotaffected+"";
	
		url=url+"&sid="+Math.random();
		xmlhttp.open("GET",url,false);
		//xmlhttp.onreadystatechange=stateChanged;
		xmlhttp.send(null);  	
	
	}

	
	
		
	
	tax();
	
	
	
	
	alert('Added Successfully');
	clearAll();
	}  
}

function idle1(){
	
}


function tax()
{
	xmlhttp=getxmlhttpObject();
	var unit=document.getElementById("cmbAcc_UnitCode").value;
	var officeid=document.getElementById("cmbOffice_code").value;
	
	var serviceno=document.getElementById("seviceno").value;
	var billno=document.getElementById("billno").value;

	var cashmonth=document.getElementById("cashmonth").value;
	var cashyear=document.getElementById("cashyear").value;
	
	var realizedenergycharge=document.getElementById("realizedenergycharge").value;
	var regdemandcharge=document.getElementById("regdemandcharge").value;
	var totaladjustment=document.getElementById("totaladjustment").value;
	var taxableamount=document.getElementById("taxableamount").value;
	var taxamount=document.getElementById("taxamount").value;
	var oldtaxamount=document.getElementById("oldtaxamount").value;
	var totaltaxamount=document.getElementById("totaltaxamount").value;
	
     var url="../../../../../EB_Bill_WS_Annexure?command=Addtrn1&unit="+unit+"&officeid="+officeid+"&serviceno="+serviceno+"&billno="+billno+"&realizedenergycharge="+realizedenergycharge+"&regdemandcharge="+regdemandcharge+"&totaladjustment="+totaladjustment+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"";
	url=url+"&taxableamount="+taxableamount+"&taxamount="+taxamount+"&oldtaxamount="+oldtaxamount+"&totaltaxamount="+totaltaxamount+"";
	url=url+"&sid="+Math.random();
	xmlhttp.open("GET",url,false);
	//xmlhttp.onreadystatechange=stateChanged;
	xmlhttp.send(null);  	


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
	document.getElementById("serviceno").options[0].selected='selected';
	document.getElementById("billno").options[0].selected='selected';
	document.getElementById("metertype").options[0].selected='selected';
	document.getElementById("meterno").options[0].selected='selected';
	
	
	document.getElementById("billdate").value = "";
    document.getElementById("cashmonth").options[0].selected='selected';
	document.getElementById("cashyear").value = "";
	document.getElementById("circlename").value = "";
	
	document.getElementById("readingdate").value = "";
	
	document.getElementById("readtypecode").value = "";
	document.getElementById("finalreading").value = "";
	document.getElementById("initialreading").value = "";
	document.getElementById("differencereading").value = "";
	document.getElementById("mf").value = "";
	document.getElementById("comsumption").value = "";
	document.getElementById("comp_con").value = "";
	document.getElementById("other_cons").value = "";
	document.getElementById("avg_cons").value = "";
	document.getElementById("txtRemarks").value = "";
	
	
	document.getElementById("realizedenergycharge").value = "";
	document.getElementById("regdemandcharge").value = "";
	document.getElementById("totaladjustment").value = "";
	document.getElementById("taxableamount").value = "";
	document.getElementById("taxamount").value = "";
	document.getElementById("oldtaxamount").value = "";
	document.getElementById("totaltaxamount").value = "";
	
	
	var tbody=document.getElementById("grid_body");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
	
    
    
	document.eb_bill_annexure.cmdadd.style.display='block';
	 document.eb_bill_annexure.cmdupdate.style.display='none';
	 document.eb_bill_annexure.cmddelete.disabled=true;
	 
	 
}





function nullcheck()
{
	
	if(document.getElementById("billno").value=="")
	{
		alert("Please Select  Bill No");
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
	
	if(document.getElementById("meterno").value=="")
	{
		alert("Please Enter Meter No");
  	    return false; 
	}
	if(document.getElementById("readingdate").value=="")
	{
		alert("Please Enter Reading Date");
  	    return false; 
	}
	
	if(document.getElementById("readtypecode").value=="")
	{
		alert("Please Enter Read Type Code");
  	    return false; 
	}	
	
	diffreading();
	
	if(document.getElementById("mf").value=="")
	{
		alert("Please Enter MF");
  	    return false; 
	}	
	
	if(document.getElementById("comsumption").value=="")
	{
		alert("Please Enter Consumption");
  	    return false; 
	}
	if(document.getElementById("comp_con").value=="")
	{
		alert("Please Enter Comp_Con");
  	    return false; 
	}
	if(document.getElementById("other_cons").value=="")
	{
		alert("Please Enter Other_Cons");
  	    return false; 
	}
	if(document.getElementById("avg_cons").value=="")
	{
		alert("Please Enter AVG_Cons");
  	    return false; 
	}
	
	
	var tbody=document.getElementById("grid_body");
	if(tbody.rows.length==0)
	{
	    alert("Please Enter Concession Details Part");
	  	    return false; 
	}
	
	if(document.getElementById("oldtaxamount").value=="")
	{
		alert("Please Enter Old Tax Amount in TAX part");
  	    return false; 
	}
	
	
return	true;
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

function detailsbillno()
{
	var unitid=document.getElementById("cmbAcc_UnitCode").value;
		
	var officeid=document.getElementById("cmbOffice_code").value;
   var serviceno=document.getElementById("seviceno").value;
   var cashmonth=document.getElementById("cashmonth").value;
   var cashyear=document.getElementById("cashyear").value;
  
  	
 xmlhttp=getxmlhttpObject();
  	var url="../../../../../EB_Bill_WS_Annexure?command=getbill&unitid="+unitid+"&officeid="+officeid+"&serviceno="+serviceno+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"";
  	url=url+"&sid="+Math.random();
  	xmlhttp.open("GET",url,true);
  	xmlhttp.onreadystatechange=stateChanged;
  	xmlhttp.send(null);   
 
}



function detailsbilldate()
{
	var unitid=document.getElementById("cmbAcc_UnitCode").value;
		
	var officeid=document.getElementById("cmbOffice_code").value;
   var serviceno=document.getElementById("seviceno").value;
   var cashmonth=document.getElementById("cashmonth").value;
   var cashyear=document.getElementById("cashyear").value;
   var billno=document.getElementById("billno").value;
  	
 xmlhttp=getxmlhttpObject();
  	var url="../../../../../EB_Bill_WS_Annexure?command=getbilldate&unitid="+unitid+"&officeid="+officeid+"&serviceno="+serviceno+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"&billno="+billno+"";
  	url=url+"&sid="+Math.random();
  	xmlhttp.open("GET",url,true);
  	xmlhttp.onreadystatechange=stateChanged;
  	xmlhttp.send(null);   
 
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

            if(command=="getbill")
            {
            	var flag1=0;
            	
                if(flag=='success')
                {
                	try{
                		
              		  var len=response.getElementsByTagName("billno").length;
                 	var billno=document.getElementById("billno");
                 	var meterno=document.getElementById("meterno");
              	 var items_id=new Array();
              	 var items_meter=new Array();                        
                          for(var i=0;i<len;i++)
                          {
                       	 items_id[i]=response.getElementsByTagName("billno")[i].firstChild.nodeValue;
                       	items_meter[i]=response.getElementsByTagName("meterno")[i].firstChild.nodeValue;  
                       
                          }
                     clear_Combo(billno);
                     clear_Combo(meterno);
                          //alert('here second');
                          for(var k=0;k<len;k++)
                          {   
                          	//alert(items_name[k]);
                                var option=document.createElement("OPTION");
                                option.text=items_id[k];
                                option.value=items_id[k];
                                
                                
                                var m_option=document.createElement("OPTION");
                                m_option.text=items_meter[k];
                                m_option.value=	items_meter[k];
                                
                                 try
                                {
                                	 billno.add(option);
                                	 meterno.add(m_option);
                                }
                                catch(errorObject)
                                {
                                	billno.add(option,null);
                                	meterno.add(m_option,null);
                                   // alert('error');
                                }
                          }
              	
              	}catch(e){alert("Error in lat"+e);}             
                }else{
                	alert('No data Found');
                }
            }
            
            if(command=="getbilldate")
            {
            	var flag1=0;
            	
                if(flag=='success')
                {
                	try{
               document.getElementById("billdate").value=response.getElementsByTagName("billdate")[0].firstChild.nodeValue;
               document.getElementById("realizedenergycharge").value=response.getElementsByTagName("energycharges")[0].firstChild.nodeValue;
               document.getElementById("regdemandcharge").value=response.getElementsByTagName("demandcharges")[0].firstChild.nodeValue;
              		
              	}catch(e){alert("Error in lat"+e);}             
                }
            }
            
            
       }
    }
}

function clear_Combo(combo)
{
        //alert(combo.id)
        var cmbSL_Code=document.getElementById(combo.id);   
        cmbSL_Code.innerHTML="";
       var option=document.createElement("OPTION");
        option.text="--Select Code--";
        option.value="";
        try
        {
            cmbSL_Code.add(option);
        }catch(errorObject)
        {
            cmbSL_Code.add(option,null);
        } 
}
function diffreading()
{
	 if(document.getElementById("finalreading").value=="")
	 {
		 alert('Please Enter Final Reading');
		 return false;
	 }
	 
	 
	 if(document.getElementById("initialreading").value=="")
	 {
		 alert('Please Enter Initial Reading');
		 return false;
	 }
	 
	if(document.getElementById("finalreading").value!="" && document.getElementById("initialreading").value!="")
	{
		document.getElementById("differencereading").value=parseFloat(document.getElementById("finalreading").value)-parseFloat(document.getElementById("initialreading").value)	;
	}
	 
}
function totaladjust()
{
	 if(document.getElementById("pfincentive").value=="")
	 {
		 alert('Please Enter PF Incentive');
		 return false;
	 }
	 
	 
	 if(document.getElementById("affected").value=="")
	 {
		 alert('Please Enter Adjustment Affected');
		 return false;
	 }
	 if(document.getElementById("notaffected").value=="")
	 {
		 alert('Please Enter Adjustment Not Affected');
		 return false;
	 }
	 
	if(document.getElementById("pfincentive").value!="" && document.getElementById("affected").value!="" && document.getElementById("notaffected").value!="")
	{
		document.getElementById("totaladj").value=parseFloat(document.getElementById("pfincentive").value)+parseFloat(document.getElementById("affected").value)+parseFloat(document.getElementById("notaffected").value)	;
	}
	 
}


function taxcalculation()
{
	
	
	if(document.getElementById("billno").value=="")
	{
		alert("Please Select  Bill No in General part");
  	    return false; 
	}
	
	
	var currRow = document.getElementById("grid_body").rows.length;
	if(currRow<1)
	{
		alert('Please Enter Concession Adjustment Details');
		 return false;	
		
	}
	var totaladjustment=0;
	for(i=0;i<currRow;i++){		
		
	totaladjustment=parseFloat(totaladjustment)+parseFloat(document.getElementById("grid_body").rows[i].cells.item(5).firstChild.nodeValue);
	
	}
	
	
	
	document.getElementById("totaladjustment").value=totaladjustment;
	var taxableamount=parseFloat(document.getElementById("regdemandcharge").value)+parseFloat(document.getElementById("realizedenergycharge").value)-parseFloat(document.getElementById("totaladjustment").value)	;
	if(parseFloat(taxableamount)<0)
	{
		alert('Total Adjustment Should not greater than add values of Recoreded Demand charge and Realised Energy Charge ');
		return false;
	}else{
	document.getElementById("taxableamount").value=taxableamount;
	document.getElementById("taxamount").value=parseFloat(5/100)*parseFloat(taxableamount);
	}
	
}

function taxvalue()
{
	taxcalculation();
	document.getElementById("totaltaxamount").value=parseFloat(document.getElementById("taxamount").value)+parseFloat(document.getElementById("oldtaxamount").value);	
}