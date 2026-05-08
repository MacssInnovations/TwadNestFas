
var seq=1;
var com_id;
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




function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {
             // alert("enter.....");
			var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
			
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			var command = tagCommand.firstChild.nodeValue;
		

			if (command == "loadmajortype") {
				loadMajor(baseResponse);
			}
			if (command == "loadminortype") {
				loadMinor(baseResponse);
			}
		
			if (command == "loadsubType") {
				loadSub(baseResponse);
			}
			 else if(command=="loadempdetails")
             {
                   LoadEmpDetails(baseResponse);
             }
		
			 else if(command=="add")
             {
                   addResult(baseResponse);
             }
			 else if(command=="getAmount")
             {
                   getResultAmount(baseResponse);
             }
			 else if(command=="loaddes")
             {
                   comboloadDes(baseResponse);
             }
}
}
}

function  comboloadDes(baseResponse)
{
	
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;  
	
	if (flag == "success") {
	
		//alert( baseResponse.getElementsByTagName("date3")[0].firstChild.nodeValue);
		
	    var len =baseResponse.getElementsByTagName("desid").length;
	
	    for ( var i = 0; i < len; i++) {
			var com1id = baseResponse.getElementsByTagName("desid")[i].firstChild.nodeValue;
			var com1name = baseResponse.getElementsByTagName("desname")[i].firstChild.nodeValue;
			var se = document.getElementById("cmbSanctionAutrority");
			var op = document.createElement("OPTION");
			op.value = com1id;
			var txt = document.createTextNode(com1name);
			op.appendChild(txt);
			se.appendChild(op);

		}
	} else {
		alert("Fail to Load");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}


function addResult(baseResponse)
{
	
	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
      if(flag=="success")
      {
    	  alert("Record Inserted Successfully");
    	  refreash();
      }
    
	
}
function  getResultAmount(baseResponse)
{
	
	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	  alert("flag.........................................................................."+flag);
      if(flag=="success")
      {
    document.getElementById("txtBudgetProvided").value=baseResponse.getElementsByTagName("provided")[0].firstChild.nodeValue;
    document.getElementById("txtBudgetSoFar").value=baseResponse.getElementsByTagName("spend")[0].firstChild.nodeValue;
    document.getElementById("txtAmount").value=baseResponse.getElementsByTagName("balance")[0].firstChild.nodeValue;  
    	  
      }
      else if(flag=="nodata")
      {
    	  alert("NO Record in Buget Table");
      }
	
	
	
}

function emp_sanction_preparedby()
{
        emp_flag=1;    
        Load_emp_details();
}
function emp_popup_sanction_preparedby()
{
        
        emp_flag=1;    
        servicepopup();
        
}
function emp_sanction_approvedby()
{
        emp_flag=2;  
        Load_emp_details();
}
function emp_popup_sanction_approvedby()
{
        emp_flag=2;    
        servicepopup();
}


var winemp;
function servicepopup()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }
    else
    {
        winemp=null
    }
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}
function doParentEmp(emp)
{
        if(emp_flag==1)
        {
                document.frmSanctionProceedings.txtEmpID_mas.value=emp;
                Load_emp_details();
        }
        else if(emp_flag==2)
        {
                document.frmSanctionProceedings.txtEmpID_mas1.value=emp;
                Load_emp_details();
        }
}
function Load_emp_details()
{
       
        if(emp_flag==1)
        {
                var emp_id=document.getElementById("txtEmpID_mas").value;
              
        }
        else if(emp_flag==2)
        {
                var emp_id=document.getElementById("txtEmpID_mas1").value;
              
        }
         //alert("emp_flag+++++++++++++++++++++++++++"+emp_flag);
         //alert(document.frmSanctionProceedings.r1[1].checked);
        
          if(emp_flag==2 &&  document.frmSanctionProceedings.r1[1].checked==true)
          {
        	  
        	  
        	  var url="";
              //  url="../../../../../phone_master_servlet?command=loadempdetails&emp_id="+emp_id;
                url = "../../../../../SanctionProceeding?command=pensioner&empid="+emp_id;
                //alert(url);
                var req=getTransport();
                 req.open("GET",url,true);        
                 req.onreadystatechange=function()
                 {
               	  manipulate(req);
                 }   
                 req.send(null);
        	  
        	  
        	  
          }
  	  
          else
          {
  	  
  	  
        
             var url="";
           //  url="../../../../../phone_master_servlet?command=loadempdetails&emp_id="+emp_id;
             url = "../../../../../Pre_AuditDetails?command=getEmpName&empid="+emp_id;
             //alert(url);
             var req=getTransport();
              req.open("GET",url,true);        
              req.onreadystatechange=function()
              {
            	  manipulate(req);
              }   
              req.send(null);
          }
     
}

function LoadEmpDetails(baseResponse)
{
                 
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                  if(flag=="success")
                  {                       
                         var emp_name=baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue;
                        // var desig_name=baseResponse.getElementsByTagName("desig_name")[0].firstChild.nodeValue;
                        // var office_name=baseResponse.getElementsByTagName("office_name")[0].firstChild.nodeValue;
                         if(emp_flag==1)
                         {
                                
                                document.frmSanctionProceedings.txtSanction_Estimate_PreparedBy.value=emp_name;
                         }
                         else if(emp_flag==2)
                         {
                        	 
                                document.frmSanctionProceedings.txtPayeeName.value=emp_name;
                                
                               
                         }
                }
                else if(flag=="nodata")
                {
                        alert("Invalid Employee Id");
                }
                else if(flag=="pension")
                {
                	var emp_name=baseResponse.getElementsByTagName("name")[0].firstChild.nodeValue;
                	
                	var emp_id=baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue;
                	document.frmSanctionProceedings.txtEmpID_mas1.value=emp_id;
                	document.frmSanctionProceedings.txtPayeeName.value=emp_name;
                	
                	
                	
                }
                  
                else
                {
                        alert("Failed to load");
                }
}
function numbersonly(e,t)
{
         var unicode=e.charCode? e.charCode : e.keyCode;
         if(unicode==13)
         {
	          try{t.blur(); }catch(e){}
	          return true;
        
         }
         if (unicode!=8 && unicode !=9)
         {
	          if (unicode<48||unicode>57 ) 
	          {
	                return false 
	          }
         }
}




function txt_empty(txt) 
{
	var k=0;
	var s=txt.split('|');
//	 var tbody = document.getElementById("tblList");
//		var rowcount=tbody.rows.length;
//		//alert(rowcount+"************************************");
	for(var i=0;i<s.length;i++)
	{
		//alert(document.getElementById(s[i]).value);
	if(document.getElementById(s[i]).value == "") 
	{ 
		
		var a=s[i].split('txt');
		
	alert(a[1]+"   Should Not Be Blank");
	document.getElementById(s[i]).focus();
	return false;
	} 
	else if(document.getElementById(s[i]).value == "s")
	{

		var a=s[i].split('cmb');
		alert(a[1]+"   Should Not Be Blank");
		document.getElementById(s[i]).focus();
	   return false;
		
	}
	
	}
    return true;
}



function txt_empty1(txt) 
	{
		var s=txt.split('|');
	
		
		for(var i=0;i<s.length;i++)
		if(document.getElementById(s[i]).value == "") 
		{ 
			
			var a=s[i].split('txt');
			
		alert(a[1]+"   Should Not Be Blank");
		document.getElementById(s[i]).focus();
		return false;
		} 
		else if(document.getElementById(s[i]).value == "s")
		{

			var a=s[i].split('cmb');
			alert(a[1]+"   Should Not Be Blank");
			document.getElementById(s[i]).focus();
			return false;
			
		}
		else
			return true;
}


function numbersonly(myfield,e,dec)
{
	//alert("hai");
	var key;
	var keychar;
	if (window.event)
	   key = window.event.keyCode;
	else if (e)
	   key = e.which;
	else
	   return true;
	keychar = String.fromCharCode(key);
// control keys
	if ((key==null) || (key==0) || (key==8) || 
		(key==9) || (key==13) || (key==27)  || (key==43) || (key==45))
	   return true;
	// numbers
	else if ((("0123456789").indexOf(keychar) > -1))
	   return true;
	// decimal point jump
	else if (dec && (keychar == ".")) {
	   myfield.form.elements[dec].focus();
	   return false;
	} else
	   return false;
}





function loadmajortype()
{
	//alert("enter");
	  var url = "../../../../../SanctionProceeding?command=loadmajortype";
    
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);	
	
	
	
	
}

function loaddes()
{
	//alert("enter");
	  var url = "../../../../../SanctionProceeding?command=loaddes";
    
	//alert(url);
	  var xmlrequest = AjaxFunction();
     	xmlrequest.open("POST", url, true);
     	xmlrequest.onreadystatechange = function() {
     		manipulate(xmlrequest);
     		}

     	xmlrequest.send(null);

	
	
	
}



function loadMinorType()
{
    var cmbMajorType=document.getElementById("cmbMajorType").value;
	  var url = "../../../../../SanctionProceeding?command=loadminortype&cmbMajorType="+cmbMajorType;
  
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);	
	
	
	
	
}
function loadsubType()
{
	 var cmbMajorType=document.getElementById("cmbMajorType").value;
	 
	 var cmbMinorType=document.getElementById("cmbMinorType").value;
	 
	  var url = "../../../../../SanctionProceeding?command=loadsubType&cmbMajorType="+cmbMajorType+"&cmbMinorType="+cmbMinorType;
 
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);	
}

function loadMajor(baseResponse)
{
	
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
	
		//alert( baseResponse.getElementsByTagName("date3")[0].firstChild.nodeValue);
		
	    var len =baseResponse.getElementsByTagName("majorid").length;
		var se = document.getElementById("cmbMajorType");
		se.length=1;
	    for ( var i = 0; i < len; i++) {
			var com1id = baseResponse.getElementsByTagName("majorid")[i].firstChild.nodeValue;
			var com1name = baseResponse.getElementsByTagName("majorname")[i].firstChild.nodeValue;
			var se = document.getElementById("cmbMajorType");
			var op = document.createElement("OPTION");
			op.value = com1id;
			var txt = document.createTextNode(com1name);
			op.appendChild(txt);
			se.appendChild(op);

		}
	}
	else if(flag=="NoData")
	{
		
		alert("No Record is Found");
	}
	
	
	
	else {
		alert("Fail to Load");
	}
		

	
	
}

function loadMinor(baseResponse)
{
	//alert("enter........................................................");
	
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

//alert("flag............"+flag);	
	if (flag == "success") {
	
		//alert( baseResponse.getElementsByTagName("date3")[0].firstChild.nodeValue);
		
	    var len =baseResponse.getElementsByTagName("miorid").length;
	   se= document.getElementById("cmbMinorType");
	    se.length=1;
		
	
	    for ( var i = 0; i < len; i++) {
			var com1id = baseResponse.getElementsByTagName("miorid")[i].firstChild.nodeValue;
			var com1name = baseResponse.getElementsByTagName("miorname")[i].firstChild.nodeValue;
			
			var op = document.createElement("OPTION");
			op.value = com1id;
			var txt = document.createTextNode(com1name);
			op.appendChild(txt);
			se.appendChild(op);

		}
	}
	else if(flag=="NoData")
	{
		 se= document.getElementById("cmbMinorType");
		    se.length=1;
		alert("No Record is Found");
	}
	
	
	
	else  {
		alert("Fail to Load");
	}
		

	
	
}
function loadSub(baseResponse)
{
	//alert("enter........................................................");
	
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
	
		//alert( baseResponse.getElementsByTagName("date3")[0].firstChild.nodeValue);
		
	    var len =baseResponse.getElementsByTagName("subid").length;
		var se = document.getElementById("cmbBillSubType");
		se.length=1;
	    for ( var i = 0; i < len; i++) {
			var com1id = baseResponse.getElementsByTagName("subid")[i].firstChild.nodeValue;
			var com1name = baseResponse.getElementsByTagName("subname")[i].firstChild.nodeValue;
		
			var op = document.createElement("OPTION");
			op.value = com1id;
			var txt = document.createTextNode(com1name);
			op.appendChild(txt);
			se.appendChild(op);

		}
	}
	else if(flag=="NoData")
	{
		 se= document.getElementById("cmbBillSubType");
		    se.length=1;
		alert("No Record is Found");
	}
	
	else {
		alert("Record Dose Not Exists");
	}
		

	
	
}

///////////////////////////////////////////////////////////////////////functions for grid

function add1()
{
	
	
	                                                                                                                                                                           
	  if(  document.frmSanctionProceedings.r1[0].checked==true)
	  {
		  var r1=document.frmSanctionProceedings.r1[0].value;
	  }
	  else
	  {
		  var r1=document.frmSanctionProceedings.r1[1].value;
	  }
	  
	  
	  if(  document.frmSanctionProceedings.r2[0].checked==true)
	  {
		  var r2=document.frmSanctionProceedings.r2[0].value;
	  }
	  else
	  {
		  var r2=document.frmSanctionProceedings.r2[1].value;
	  }
	    
      var   txtEmpID_mas1= document.getElementById("txtEmpID_mas1").value;
       
      var txtPayeeName=document.getElementById("txtPayeeName").value;
     
      var   txtSanctionAount1 = document.getElementById("txtSanctionAount1").value;
      var   txtRefNo1= document.getElementById("txtRefNo1").value;
      var   txtRefDate1=document.getElementById("txtRefDate1").value;
      var    mtxtRemarks1=document.getElementById("mtxtRemarks1").value;
      
      if(txtEmpID_mas1=='')
  	{
  		alert("payeecode Should Not Be Blank");
  		document.getElementById("txtEmpID_mas1").focus();
  		 return true;
  	}


  	if(txtSanctionAount1=='')
  	{
  		alert("Amount Should Not Be Blank Should Not Be Blank");
  		document.getElementById("txtSanctionAount1").focus();
  		 return true;
  	}

  	if(txtRefNo1=='')
  	{
  		alert("RefNO Should Not BeBlank");
  		document.getElementById("txtRefNo1").focus();
  		return true;
  		
  	}
  	
  	if(txtRefDate1=='')
  	{
  		alert("RefDate Should Not Be Blank Should Not Be Blank");
  		document.getElementById("txtRefDate1").focus();
  		 return true;
  	}

  	if(mtxtRemarks1=='')
  	{
  		alert("Remark Should Not BeBlank");
  		document.getElementById("mtxtRemarks1").focus();
  		return true;
  		
  	}
    
  	 var   txtinstalment = document.getElementById("txtinstalment").value;
     var   txtresidualAmount= document.getElementById("txtresidualAmount").value;
     var   txtstartMonth=document.getElementById("txtstartMonth").value;
     var    txtDeductionAmount=document.getElementById("txtDeductionAmount").value;
      
      
	 
	try
	{
		
		
		var tbody = document.getElementById("tblList");
	    var table = document.getElementById("Existing");
		var mycurrent_row = document.createElement("TR");
		mycurrent_row.id = seq;
		
		//alert("one"+cmbBillNO);

		var cell1 = document.createElement("TD");
		var anc1 = document.createElement("A");
		var url="javascript:loadValuesFromTable('"+seq+"')";
		anc1.href = url;
		var txtedit = document.createTextNode("Edit");
		anc1.appendChild(txtedit);
		cell1.appendChild(anc1);
		mycurrent_row.appendChild(cell1);
		//alert(url);

		var cell2 = document.createElement("TD");
		var r1 = document.createTextNode(r1);
		cell2.appendChild(r1);
		mycurrent_row.appendChild(cell2);
        
		//alert("2"+cmbPassOrderNO);
		
		var cell3 = document.createElement("TD");
		var txtEmpID_mas1 = document.createTextNode(txtEmpID_mas1);
		cell3.appendChild(txtEmpID_mas1);
		mycurrent_row.appendChild(cell3);

		
		var cell4= document.createElement("TD");
		var txtPayeeName = document.createTextNode(txtPayeeName);
		cell4.appendChild(txtPayeeName);
		mycurrent_row.appendChild(cell4);
		
		
		
		//alert("3"+txtPassOrderDate);
		
		//--------------------------------------------------------		
		var cell5 = document.createElement("TD");
		var r2 = document.createTextNode(r2);
		cell5.appendChild(r2);
		mycurrent_row.appendChild(cell5);
		
		//alert("4"+cmdProceedinnNo);
		
		var cell6 = document.createElement("TD");
		var txtSanctionAount1 = document.createTextNode(txtSanctionAount1);
		cell6.appendChild(txtSanctionAount1);
		mycurrent_row.appendChild(cell6);
		
		//alert("5"+txtProceedingDate);
		
		var cell7 = document.createElement("TD");
		var txtRefNo1 = document.createTextNode(txtRefNo1);
		cell7.appendChild(txtRefNo1);
		mycurrent_row.appendChild(cell7);
		
		//alert("6"+cmbBillNO);
		
		var cell8= document.createElement("TD");
		var txtRefDate1 = document.createTextNode(txtRefDate1);
		cell8.appendChild(txtRefDate1);
		mycurrent_row.appendChild(cell8);
		
		//alert("7"+txtBillDate);
		
		
		var cell9= document.createElement("TD");
		var txtinstalment = document.createTextNode(txtinstalment);
		cell9.appendChild(txtinstalment);
		mycurrent_row.appendChild(cell9);
		
		
		var cell10= document.createElement("TD");
		var txtstartMonth = document.createTextNode(txtstartMonth);
		cell10.appendChild(txtstartMonth);
		mycurrent_row.appendChild(cell10);
		
		var cell11= document.createElement("TD");
		var txtresidualAmount = document.createTextNode(txtresidualAmount);
		cell11.appendChild(txtresidualAmount);
		mycurrent_row.appendChild(cell11);
		
		var cell12= document.createElement("TD");
		var txtDeductionAmount = document.createTextNode(txtDeductionAmount);
		cell12.appendChild(txtDeductionAmount);
		mycurrent_row.appendChild(cell12);
		
		
		
		
		
		
		
		
		var cell13= document.createElement("TD");
		var mtxtRemarks1 = document.createTextNode(mtxtRemarks1);
		cell13.appendChild(mtxtRemarks1);
		mycurrent_row.appendChild(cell13);
		
		
		tbody.appendChild(mycurrent_row);
	    seq++;
	 
	}
		
		
	
	catch(errorObject)
	{
		
	}
	 
	ref1();
		
		
	
}

function ref1()
{
	
	 document.getElementById("txtEmpID_mas1").value='';
     document.getElementById("txtSanctionAount1").value='';
     document.getElementById("txtRefNo1").value='';
     document.getElementById("txtRefDate1").value='';
     document.getElementById("mtxtRemarks1").value='';
     document.getElementById("txtPayeeName").value='';
     
     document.getElementById("txtinstalment").value='';
     document.getElementById("txtstartMonth").value='';
     document.getElementById("txtresidualAmount").value='';
     document.getElementById("txtDeductionAmount").value=''; 
     document.frmSanctionProceedings.r1[0].checked==false;
     document.frmSanctionProceedings.r1[1].checked==false;
     document.frmSanctionProceedings.r2[0].checked==false;
      document.frmSanctionProceedings.r2[1].checked==false;
	
	
	
}

function loadValuesFromTable(id)
{
	com_id=id;
	  r=document.getElementById(id);
	//r=id;
	 // alert(id);
      rcells=r.cells;
     // alert(rcells.item(2).firstChild.nodeValue);
      document.getElementById("txtEmpID_mas1").value= rcells.item(2).firstChild.nodeValue;
     
      document.getElementById("txtPayeeName").value=rcells.item(3).firstChild.nodeValue;
     
      
      document.getElementById("txtSanctionAount1").value=rcells.item(5).firstChild.nodeValue;
      document.getElementById("txtRefNo1").value=rcells.item(6).firstChild.nodeValue;
      document.getElementById("txtRefDate1").value=rcells.item(7).firstChild.nodeValue;
      document.getElementById("mtxtRemarks1").value=rcells.item(12).firstChild.nodeValue;
      document.getElementById("txtinstalment").value=rcells.item(8).firstChild.nodeValue;
      document.getElementById("txtstartMonth").value=rcells.item(9).firstChild.nodeValue;
      document.getElementById("txtresidualAmount").value=rcells.item(10).firstChild.nodeValue;
      document.getElementById("txtDeductionAmount").value=rcells.item(11).firstChild.nodeValue;
      var s1=rcells.item(1).firstChild.nodeValue;
      if(s1=='E')
      {
    	  document.frmSanctionProceedings.r1[0].checked=true;
      }
      else
      {
    	  document.frmSanctionProceedings.r1[1].checked=true;
      }
      
      var s2=rcells.item(4).firstChild.nodeValue;
      if(s2=='R')
      {
    	  document.frmSanctionProceedings.r2[0].checked=true;
      }
      else
      {
    	  document.frmSanctionProceedings.r2[1].checked=true;
      }
      
      document.frmSanctionProceedings.onsubmit1.disabled=true;
      
      
}
function update1()
{
//alert("enter ----------------------");
	var items = new Array();
	items[0] =  document.getElementById("txtEmpID_mas1").value;
	items[1] =  document.getElementById("txtPayeeName").value;
	items[2] =  document.getElementById("txtSanctionAount1").value;
	items[3] =  document.getElementById("txtRefNo1").value;
	items[4] =  document.getElementById("txtRefDate1").value;
	items[5] =  document.getElementById("mtxtRemarks1").value;
	
	items[8] =  document.getElementById("txtinstalment").value;
	items[9] =  document.getElementById("txtstartMonth").value;
	items[10] =  document.getElementById("txtresidualAmount").value;
	items[11] =  document.getElementById("txtDeductionAmount").value;
	
	
	
	
	//alert(items[0]+items[1]+items[2]+items[3]+items[4]+items[5]);
	  if(  document.frmSanctionProceedings.r1[0].checked==true)
	  {
		  items[6]=document.frmSanctionProceedings.r1[0].value;
	  }
	  else
	  {
		  items[6]=document.frmSanctionProceedings.r1[1].value;
	  }
	  
	  
	  if(  document.frmSanctionProceedings.r2[0].checked==true)
	  {
		  items[7]=document.frmSanctionProceedings.r2[0].value;
	  }
	  else
	  {
		  items[7]=document.frmSanctionProceedings.r2[1].value;
	  }
	 
	 var r = document.getElementById(com_id);
		var rcells = r.cells;
		//alert(r);
		rcells.item(1).firstChild.nodeValue = items[6];
		//alert(items[0]);
		rcells.item(2).firstChild.nodeValue = items[0];
		rcells.item(3).firstChild.nodeValue = items[1];
		rcells.item(4).firstChild.nodeValue = items[7];
		rcells.item(5).firstChild.nodeValue = items[2];
		rcells.item(6).firstChild.nodeValue = items[3];
		rcells.item(7).firstChild.nodeValue = items[4];
		rcells.item(8).firstChild.nodeValue = items[8];
		rcells.item(9).firstChild.nodeValue = items[9];
		rcells.item(10).firstChild.nodeValue = items[10];
		rcells.item(11).firstChild.nodeValue = items[11];
		rcells.item(12).firstChild.nodeValue = items[5];
		
		ref1();
	 
	 
	 
}

function delete_GRID()
{
        if(confirm("Do you want to delete ?"))
        {
        var tbody=document.getElementById("Existing");
        var r=document.getElementById(com_id);
        var ri=r.rowIndex;
        
        //alert("rowId........................................."+ri);
        tbody.deleteRow(ri);
       
        }
        ref1();
}


////////////////////////////////////////////////////////////////////////////////   FUNCTION FOR ADD FUNCTION WITH SERVLET

function add()
{
	var payee;
	var Recovery_from;
	 var k=0;
	 var valid=txt_empty('cmbMajorType|cmbMinorType|cmbBillSubType|txtRefNo|txtRefDate|cmbSanctionAutrority|txtEmpID_mas|txtSanctionProceedingDate|txtSanctionAmount|txtAcc_HeadCode|txtBudgetProvided|txtBudgetSoFar|txtAmount|txtMade|mtxtRemarks');
	 //alert(valid);
	 if(valid)
	 {
	  var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value
	  var cmbOffice_code=document.getElementById("cmbOffice_code").value
	  var cmbCashBookYear=document.getElementById("cmbCashBookYear").value
	  var cmbCashBookMonth= document.getElementById("cmbCashBookMonth").value
	  var cmbMajorType=document.getElementById("cmbMajorType").value;
      var cmbMinorType= document.getElementById("cmbMinorType").value;  
      var cmbBillSubType =  document.getElementById("cmbBillSubType").value;
      var txtRefNo = document.getElementById("txtRefNo").value;
      var txtRefDate = document.getElementById("txtRefDate").value;
      var cmbSanctionAutrority = document.getElementById("cmbSanctionAutrority").value;  
      var cmbSanctionBy = document.getElementById("txtEmpID_mas").value;
      var  txtSanctionProceedingDate  = document.getElementById("txtSanctionProceedingDate").value;
      var txtSanctionAmount = document.getElementById("txtSanctionAmount").value;
      var cmbAccHeadCode =document.getElementById("txtAcc_HeadCode").value;
      var  txtBudgetProvided  = document.getElementById("txtBudgetProvided").value;
      var  txtBudgetSoFar  = document.getElementById("txtBudgetSoFar").value;
      var   txtAmount = document.getElementById("txtAmount").value;  
      var   txtMade = document.getElementById("txtMade").value;
      var  mtxtRemarks = document.getElementById("mtxtRemarks").value;
      
      var  txtSanctionAmountCommon = document.getElementById("txtSanctionAmountCommon").value;
     
      var  txtAmountDeduct = document.getElementById("txtAmountDeduct").value;
   
      if(document.frmSanctionProceedings.r3[0].checked==false)
      {
    	  payee=document.frmSanctionProceedings.r3[1].value;
      }
      else
      {
    	  payee=document.frmSanctionProceedings.r3[0].value;
      }
      
      if(document.frmSanctionProceedings.r4[0].checked==false)
      {
    	  Recovery_from=document.frmSanctionProceedings.r4[1].value;
      }
      else
      {
    	  Recovery_from=document.frmSanctionProceedings.r4[0].value;
      }
     
      
      var tbody = document.getElementById("tblList");
		var rowcount=tbody.rows.length;
	    var total_amt=0;
		
		var al= new Array() ;
	   
	    for(var i=0;i<rowcount;i++)
	    	{
	    	   var r=tbody.rows[i];
	    	   var s=r.cells.length;
	    	   total_amt=total_amt+parseInt(r.cells[5].firstChild.nodeValue);
		   
	    	   for(var j=1;j<s;j++)
	    		   {
	    		  
	    		 
	    		   
	    		   al[k]=r.cells[j].firstChild.nodeValue;
	    		
	    		    k++; 
	    		   
	    		  
	    		   }
	    	
	    	}
	      // alert(total_amt);
	
	var amount=parseInt(txtAmount);
	
	 if(txtSanctionAmount>amount)
	 {
		 
		 alert("Total Sanction Amount is Greater Than  Exists Amount ... Plz Check the Budget Details");
		 document.getElementById("txtSanctionAmount").focus();
		 return true;
		 
		 
	 }
	 
	 if(txtSanctionAmount!=total_amt)
	 {
		
		 alert("Details Sanction Amount is Not Equal To Total Amount  ... Plz Check the  Details");
		 document.getElementById("txtSanctionAmount").focus();
		 return true;
		 
	 }
	
  var url = "../../../../../SanctionProceeding?command=add&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cmbCashBookYear="+cmbCashBookYear+"&cmbCashBookMonth="+cmbCashBookMonth+"&cmbMajorType="+cmbMajorType+"&cmbMinorType="+cmbMinorType+"&cmbBillSubType="
     +cmbBillSubType+"&txtRefNo="+txtRefNo+"&txtRefDate="+txtRefDate+"&cmbSanctionAutrority="+cmbSanctionAutrority+"&cmbSanctionBy="+cmbSanctionBy+"&txtSanctionProceedingDate="+txtSanctionProceedingDate+"&txtSanctionAmount="+txtSanctionAmount+"&cmbAccHeadCode="+cmbAccHeadCode+"&txtBudgetProvided="+txtBudgetProvided+"&txtBudgetSoFar="
             +txtBudgetSoFar+"&txtAmount="+txtAmount+"&txtMade="+txtMade+"&mtxtRemarks="+mtxtRemarks+"&txtSanctionAmountCommon="+txtSanctionAmountCommon+"&txtAmountDeduct="+txtAmountDeduct+"&payee="+payee+"&Recovery_from="+Recovery_from+"&grid="+al;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);	
	 }
	 else
	 {
		 
		 
		 
	 }
}

function getAmount()
{
	
    var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
	 
	 var cmbCashBookYear=document.getElementById("cmbCashBookYear").value;
	 
	 var cmbCashBookMonth=document.getElementById("cmbCashBookMonth").value;
	 
	  var url = "../../../../../SanctionProceeding?command=getAmount&txtAcc_HeadCode="+txtAcc_HeadCode+"&cmbCashBookYear="+cmbCashBookYear+"&cmbCashBookMonth="+cmbCashBookMonth;
 
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);	
	
	
	
	
}



function exitfun()
{
	alert("Exit");
	window.close();
}

function refreash()
{
	
	 document.getElementById("cmbMajorType").value='s';
     document.getElementById("cmbMinorType").value='s'; 
      document.getElementById("cmbBillSubType").value='s';
      document.getElementById("txtRefNo").value='';
       document.getElementById("txtRefDate").value='';
       
       
       
       document.getElementById("txtEmpID_mas").value='';  
       
       
       
      document.getElementById("txtSanctionProceedingDate").value='';
      
     document.getElementById("txtSanctionAmount").value='';
     
     document.getElementById("txtAcc_HeadCode").value='';
     document.getElementById("txtBudgetProvided").value='';
     document.getElementById("txtBudgetSoFar").value='';
     document.getElementById("txtAmount").value='';  
     document.getElementById("txtMade").value='';
     document.getElementById("mtxtRemarks").value='';
     document.getElementById("txtEmpID_mas1").value='';
     document.getElementById("txtSanctionAount1").value='';
     document.getElementById("txtRefNo1").value='';
     document.getElementById("txtRefDate1").value='';
     document.getElementById("mtxtRemarks1").value='';
     document.getElementById("txtSanction_Estimate_PreparedBy").value='';  
     document.getElementById("txtPayeeName").value='';  
     document.getElementById("cmbSanctionAutrority").value='s';
     document.getElementById("txtAcc_HeadDesc").value='';  
     document.getElementById("txtSanctionAmountCommon").value='';  
     document.getElementById("txtAmountDeduct").value='';  
     document.getElementById("txtAmount").value='';
   
     document.frmSanctionProceedings.r1[0].checked==false;
     document.frmSanctionProceedings.r1[1].checked==false;
     document.frmSanctionProceedings.r2[0].checked==false;
      document.frmSanctionProceedings.r2[1].checked==false;
      document.frmSanctionProceedings.r3[0].checked==false;
      document.frmSanctionProceedings.r3[1].checked==false;
      document.frmSanctionProceedings.r4[0].checked==false;
       document.frmSanctionProceedings.r4[1].checked==false;
 
 
	
  try
  {
  var tbody = document.getElementById("tblList");
	var rowcount=tbody.rows.length;

	alert("rowcount"+rowcount);
	var al= new Array() ;
   
    for(var i=0;i<rowcount;i++)
    	{
    	var ri=tbody.rows[i];
    	
    	//  var ri=r.rowIndex;
    
    	   tbody.deleteRow(ri);
    	  
	   
    	
    	}

  }
  catch(e)
  {
	  //alert(e);
  }
	
	
}


