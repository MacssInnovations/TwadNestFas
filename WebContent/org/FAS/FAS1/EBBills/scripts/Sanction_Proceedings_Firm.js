var winemp;
var winemp1;
//alert('asdfasdfasdfkkk');

var checkemp=0;

function servicepopup()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,500);
       winemp.moveTo(250,250); 
       winemp.focus();
       return;
    }
    else
    {
        winemp=null;
    }
    // startwaiting(document.frmEmployee) ;   
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}


function doParentEmp(emp)
{
	
document.sanction_proceedings_firm.sanctionedby.value=emp;
call('load');
	

}



var winAccHeadCode;

function AccHeadpopup()
{
    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) 
    {
       winAccHeadCode.resizeTo(500,500);
       winAccHeadCode.moveTo(250,250); 
       winAccHeadCode.focus();
    }
    else
    {
        winAccHeadCode=null
    }
        
    winAccHeadCode= window.open("../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Acc_Head_Dir_List_InUse.jsp","AccountHeadSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAccHeadCode.moveTo(250,250);  
    winAccHeadCode.focus();
    
}

function doParentAccHead(code)
{
   document.getElementById("cmbAcHeadCode").value=code;
   call('headcode');
   return true;
}
var seq = 0;

var com_id;

function ADD_GRID() {
	
	var tbody = document.getElementById("grid_body");
	var t = 0;
	var items = new Array();
	
	
	items[0] = document.getElementById("invoiceno").value;	
	items[1] = document.getElementById("invoicedate").value;
	items[2] = document.getElementById("detailsparticular").value;
	items[3] = document.getElementById("mrefno").value;
	items[4] = document.getElementById("mrefdate").value;
	items[5] = document.getElementById("agreementno").value;	
	items[6] = document.getElementById("agreementdate").value;	
	items[7] = document.getElementById("suplementno").value;	
	items[8] = document.getElementById("supplementdate").value;	
	items[9] = document.getElementById("invoiceamount").value;
	items[10] = document.getElementById("sancamount").value;
	items[11] = document.getElementById("remarks").value;
	
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
	var invoice=document.createElement("input");
	invoice.type="hidden";
	invoice.name="invoice_no";
	invoice.id="invoice_no";
	invoice.value=items[0];
    cell2.appendChild(invoice);
	var currentText = document.createTextNode(items[0]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var invoicedate=document.createElement("input");
	invoicedate.type="hidden";
	invoicedate.name="invoice_date";
	invoicedate.value=items[1];
    cell2.appendChild(invoicedate);
	var currentText = document.createTextNode(items[1]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var particular=document.createElement("input");
	particular.type="hidden";
	particular.name="particular_detail";
	particular.value=items[2];
    cell2.appendChild(particular);
	var currentText = document.createTextNode(items[2]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var mrefno=document.createElement("input");
	mrefno.type="hidden";
	mrefno.name="m_refno";
	mrefno.value=items[3];
    cell2.appendChild(mrefno);
	var currentText = document.createTextNode(items[3]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var mrefdate=document.createElement("input");
	mrefdate.type="hidden";
	mrefdate.name="m_refdate";
	mrefdate.value=items[4];
    cell2.appendChild(mrefdate);
	var currentText = document.createTextNode(items[4]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var agreeno=document.createElement("input");
	agreeno.type="hidden";
	agreeno.name="agree_no";
	agreeno.value=items[5];
    cell2.appendChild(agreeno);
	var currentText = document.createTextNode(items[5]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var agreedate=document.createElement("input");
	agreedate.type="hidden";
	agreedate.name="agree_date";
	agreedate.value=items[6];
    cell2.appendChild(agreedate);
	var currentText = document.createTextNode(items[6]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);
	
	cell2 = document.createElement("TD");
	var supplementno=document.createElement("input");
	supplementno.type="hidden";
	supplementno.name="supplement_no";
	supplementno.value=items[7];
    cell2.appendChild(supplementno);
	var currentText = document.createTextNode(items[7]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);
	
	
	cell2 = document.createElement("TD");
	var supplementdate=document.createElement("input");
	supplementdate.type="hidden";
	supplementdate.name="supplement_date";
	supplementdate.value=items[8];
    cell2.appendChild(supplementdate);
	var currentText = document.createTextNode(items[8]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);
	
	cell2 = document.createElement("TD");
	var invoiceamount=document.createElement("input");
	invoiceamount.type="hidden";
	invoiceamount.name="invoice_amount";
	invoiceamount.value=items[9];
    cell2.appendChild(invoiceamount);
	var currentText = document.createTextNode(items[9]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);
	
	cell2 = document.createElement("TD");
	var sancamount=document.createElement("input");
	sancamount.type="hidden";
	sancamount.name="sanc_amount";
	sancamount.value=items[10];
    cell2.appendChild(sancamount);
	var currentText = document.createTextNode(items[10]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);
	
	cell2 = document.createElement("TD");
	var remark=document.createElement("input");
	remark.type="hidden";
	remark.name="remarks12";
	remark.value=items[11];
    cell2.appendChild(remark);
	var currentText = document.createTextNode(items[11]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);
	
	tbody.appendChild(mycurrent_row);
	clear();
}


function loadTable(scod) {
	com_id = scod; // to identify in UPDATE_GRID ,which row loaded
	clear();
	scod=parseInt(scod)-1;
	var tbody = document.getElementById("grid_body");
	//var r = document.getElementById(scod);
	//var rcells = r.cells;
	//alert(document.sanction_proceedings_firm.invoice_no[2].value);
	 if(parseInt(tbody.rows.length)==1)
     {
			
		  document.getElementById("invoiceno").value=document.sanction_proceedings_firm.invoice_no.value;
			 document.getElementById("invoicedate").value=document.sanction_proceedings_firm.invoice_date.value;
			 document.getElementById("detailsparticular").value=document.sanction_proceedings_firm.particular_detail.value;;
			 document.getElementById("mrefno").value= document.sanction_proceedings_firm.m_refno.value;;
			 document.getElementById("mrefdate").value= document.sanction_proceedings_firm.m_refdate.value;;
			 document.getElementById("agreementno").value= document.sanction_proceedings_firm.agree_no.value;;	
			 document.getElementById("agreementdate").value=document.sanction_proceedings_firm.agree_date.value;;	
			 document.getElementById("suplementno").value=document.sanction_proceedings_firm.supplement_no.value;;	
			 document.getElementById("supplementdate").value=document.sanction_proceedings_firm.supplement_date.value;	
			 document.getElementById("invoiceamount").value= document.sanction_proceedings_firm.invoice_amount.value;
			 document.getElementById("sancamount").value=document.sanction_proceedings_firm.sanc_amount.value;
			 document.getElementById("remarks").value=document.sanction_proceedings_firm.remarks12.value;
     }else{
    	  document.getElementById("invoiceno").value=document.sanction_proceedings_firm.invoice_no[scod].value;
			 document.getElementById("invoicedate").value=document.sanction_proceedings_firm.invoice_date[scod].value;
			 document.getElementById("detailsparticular").value=document.sanction_proceedings_firm.particular_detail[scod].value;;
			 document.getElementById("mrefno").value= document.sanction_proceedings_firm.m_refno[scod].value;;
			 document.getElementById("mrefdate").value= document.sanction_proceedings_firm.m_refdate[scod].value;;
			 document.getElementById("agreementno").value= document.sanction_proceedings_firm.agree_no[scod].value;;	
			 document.getElementById("agreementdate").value=document.sanction_proceedings_firm.agree_date[scod].value;;	
			 document.getElementById("suplementno").value=document.sanction_proceedings_firm.supplement_no[scod].value;;	
			 document.getElementById("supplementdate").value=document.sanction_proceedings_firm.supplement_date[scod].value;	
			 document.getElementById("invoiceamount").value= document.sanction_proceedings_firm.invoice_amount[scod].value;
			 document.getElementById("sancamount").value=document.sanction_proceedings_firm.sanc_amount[scod].value;
			 document.getElementById("remarks").value=document.sanction_proceedings_firm.remarks12[scod].value; 
    	 
     }
	 
	 
	 
	 
	document.sanction_proceedings_firm.cmdupdate.style.display = 'block';
	document.sanction_proceedings_firm.cmddelete.disabled = false;
	document.sanction_proceedings_firm.cmdadd.style.display = 'none';
}

function update_GRID()
{
	var tbody = document.getElementById("grid_body");
	var t = 0;
	var items = new Array();
	var com_id1=parseInt(com_id)-1;
		
	items[0] = document.getElementById("invoiceno").value;	
	items[1] = document.getElementById("invoicedate").value;
	items[2] = document.getElementById("detailsparticular").value;
	items[3] = document.getElementById("mrefno").value;
	items[4] = document.getElementById("mrefdate").value;
	items[5] = document.getElementById("agreementno").value;	
	items[6] = document.getElementById("agreementdate").value;	
	items[7] = document.getElementById("suplementno").value;	
	items[8] = document.getElementById("supplementdate").value;	
	items[9] = document.getElementById("invoiceamount").value;
	items[10] = document.getElementById("sancamount").value;
	items[11] = document.getElementById("remarks").value;

	var r=document.getElementById(com_id);
	var rcells=r.cells;
       
	 if(tbody.rows.length==1)
     {
		
		 document.sanction_proceedings_firm.invoice_no.value=document.getElementById("invoiceno").value;
		 document.sanction_proceedings_firm.invoice_date.value=document.getElementById("invoicedate").value;
		 document.sanction_proceedings_firm.particular_detail.value=document.getElementById("detailsparticular").value;
		 document.sanction_proceedings_firm.m_refno.value=document.getElementById("mrefno").value;
		 document.sanction_proceedings_firm.m_refdate.value=document.getElementById("mrefdate").value;
		 document.sanction_proceedings_firm.agree_no.value=document.getElementById("agreementno").value;
		 document.sanction_proceedings_firm.agree_date.value=document.getElementById("agreementdate").value;
		 document.sanction_proceedings_firm.supplement_no.value=document.getElementById("suplementno").value;
		 document.sanction_proceedings_firm.supplement_date.value=document.getElementById("supplementdate").value;
		 document.sanction_proceedings_firm.invoice_amount.value=document.getElementById("invoiceamount").value;
		 document.sanction_proceedings_firm.sanc_amount.value=document.getElementById("sancamount").value;
		 document.sanction_proceedings_firm.remarks12.value=document.getElementById("remarks").value;
     }else{

		 document.sanction_proceedings_firm.invoice_no[com_id1].value=document.getElementById("invoiceno").value;
		 document.sanction_proceedings_firm.invoice_date[com_id1].value=document.getElementById("invoicedate").value;
		 document.sanction_proceedings_firm.particular_detail[com_id1].value=document.getElementById("detailsparticular").value;
		 document.sanction_proceedings_firm.m_refno[com_id1].value=document.getElementById("mrefno").value;
		 document.sanction_proceedings_firm.m_refdate[com_id1].value=document.getElementById("mrefdate").value;
		 document.sanction_proceedings_firm.agree_no[com_id1].value=document.getElementById("agreementno").value;
		 document.sanction_proceedings_firm.agree_date[com_id1].value=document.getElementById("agreementdate").value;
		 document.sanction_proceedings_firm.supplement_no[com_id1].value=document.getElementById("suplementno").value;
		 document.sanction_proceedings_firm.supplement_date[com_id1].value=document.getElementById("supplementdate").value;
		 document.sanction_proceedings_firm.invoice_amount[com_id1].value=document.getElementById("invoiceamount").value;
		 document.sanction_proceedings_firm.sanc_amount[com_id1].value=document.getElementById("sancamount").value;
		 document.sanction_proceedings_firm.remarks12[com_id1].value=document.getElementById("remarks").value;
    	 
     }
	
		try{rcells.item(1).lastChild.nodeValue=items[0];}catch(e){}
            
        try{rcells.item(2).lastChild.nodeValue=items[1];}catch(e){}
             
        try{rcells.item(3).lastChild.nodeValue=items[2];}catch(e){}
      
        try{rcells.item(4).lastChild.nodeValue=items[3];}catch(e){}
       
        try{rcells.item(5).lastChild.nodeValue=items[4];}catch(e){}
            
        try{rcells.item(6).lastChild.nodeValue=items[5];}catch(e){}
        
        try{rcells.item(7).lastChild.nodeValue=items[6];}catch(e){}
        
        try{rcells.item(8).lastChild.nodeValue=items[7];}catch(e){}
        
        try{rcells.item(9).lastChild.nodeValue=items[8];}catch(e){}
        
        try{rcells.item(10).lastChild.nodeValue=items[9];}catch(e){}
        
        try{rcells.item(11).lastChild.nodeValue=items[10];}catch(e){}
      
        try{rcells.item(12).lastChild.nodeValue=items[11];}catch(e){}
      
 
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
	
	document.getElementById("invoiceno").value="";	
	 document.getElementById("invoicedate").value="";	
	 document.getElementById("detailsparticular").value="";	
	 document.getElementById("mrefno").value="";	
	 document.getElementById("mrefdate").value="";	
	 document.getElementById("agreementno").value="";	
	 document.getElementById("agreementdate").value="";		
	document.getElementById("suplementno").value="";		
	 document.getElementById("supplementdate").value="";	
	 document.getElementById("invoiceamount").value="";	
	 document.getElementById("sancamount").value="";	
	 document.getElementById("remarks").value="";	
	
	document.sanction_proceedings_firm.cmdupdate.style.display = 'none';
	document.sanction_proceedings_firm.cmddelete.disabled = true;
	document.sanction_proceedings_firm.cmdadd.style.display = 'block';
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

 if(command=="get")
{ 
	 if(document.getElementById("majortype").value=="")
		{
			alert('Please Select Major Type');
			return false;
		}
	
	var majortype=document.getElementById("majortype").value;
	
	
	  var url="../../../../../Sanction_Proceedings_Firm?command=get&majortype="+majortype+"";
	 
	  url=url+"&sid="+Math.random();
      xmlhttp.open("GET",url,true);
      xmlhttp.onreadystatechange=stateChanged;
      xmlhttp.send(null);  
    
	
} 
 
 
 else if(command=="getsub")
 { 
 	
	 if(document.getElementById("majortype").value=="")
		{
			alert('Please Select Major Type');
			return false;
		}
		if(document.getElementById("minortype").value=="")
		{
			alert('Please Select Minor Type');
			return false;
		}
	 
	 
	 
 	var majortype=document.getElementById("majortype").value;
 	var minortype=document.getElementById("minortype").value;
 	
 	  var url="../../../../../Sanction_Proceedings_Firm?command=getsub&majortype="+majortype+"&minortype="+minortype+"";
 	 
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  
     
 	
 } 
 else if(command=="load")
 { 
 	
	 if(document.getElementById("sanctionedby").value=="")
		{
			alert('Please Enter Sanctioned By');
			return false;
		}
		
		var empid=document.getElementById("sanctionedby").value;
		
 	  var url="../../../../../Sanction_Proceedings_Firm?command=load&empid="+empid+"";
 	 
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  
     
 	
 } 
 else if(command=="checkemp")
 { 
 	
	 if(document.getElementById("sanctionedby").value=="")
		{
			alert('Please Enter Sanctioned By');
			return false;
		}
		
		var empid=document.getElementById("sanctionedby").value;
		
 	  var url="../../../../../Sanction_Proceedings_Firm?command=checkemp&empid="+empid+"";
 	 
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  
     
 	
 } 
 else if(command=="headcode")
 { 
	 if(document.getElementById("cmbAcHeadCode").value=="")
		{
			alert('Please Enter Account Head Code');
			return false;
		}
	 
		var hcode=document.getElementById("cmbAcHeadCode").value;
 	  var url="../../../../../Sanction_Proceedings_Firm?command=headcode&hcode="+hcode+"";
 	 
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  
     
 	
 } 
 else if(command=="budget")
 { 
 	
	 if(document.getElementById("cmbAcHeadCode").value=="")
		{
			alert('Please Enter Account Head Code');
			return false;
		}
	 if(document.getElementById("prodate").value=="")
		{
			alert('Please EnterProceeding Date');
			return false;
		}
		var hcode=document.getElementById("cmbAcHeadCode").value;
		var unitid=document.getElementById("cmbAcc_UnitCode").value;
		var officeid=document.getElementById("cmbOffice_code").value;
		var prodate=document.getElementById("prodate").value;
		
		
		
 	  var url="../../../../../Sanction_Proceedings_Firm?command=budget&hcode="+hcode+"&unitid="+unitid+"&officeid="+officeid+"&prodate="+prodate+"";
 	 
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
                	//alert('i am in minor');
                	try{
                		
              		  var len=response.getElementsByTagName("minorcode").length;
                 	var minortype=document.getElementById("minortype");
                 
              	 var items_id=new Array();
              	 var items_desc=new Array();                        
                          for(var i=0;i<len;i++)
                          {
                       	 items_id[i]=response.getElementsByTagName("minorcode")[i].firstChild.nodeValue;
                       	items_desc[i]=response.getElementsByTagName("minordesc")[i].firstChild.nodeValue;  
                      // alert('minor'+items_desc[i]);
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
            if(command=="getsub")
            {
            	
                if(flag=='success')
                {

                	try{
                		
              		  var len=response.getElementsByTagName("subcode").length;
                 	var subtype=document.getElementById("subtype");
                 
              	 var items_id=new Array();
              	 var items_desc=new Array();                        
                          for(var i=0;i<len;i++)
                          {
                       	 items_id[i]=response.getElementsByTagName("subcode")[i].firstChild.nodeValue;
                       	items_desc[i]=response.getElementsByTagName("subdesc")[i].firstChild.nodeValue;  
                       
                          }
                     clear_Combo(subtype);
                    
                          //alert('here second');
                          for(var k=0;k<len;k++)
                          {   
                          	//alert(items_name[k]);
                                var option=document.createElement("OPTION");
                                option.text=items_desc[k];
                                option.value=items_id[k];
                             
                                 try
                                {
                                	 subtype.add(option);
                                	
                                }
                                catch(errorObject)
                                {
                                	subtype.add(option,null);
                                	
                                   // alert('error');
                                }
                          }
              	
              	}catch(e){alert("Error in lat"+e);}      
                
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
            if(command=="update")
            {
            	if(flag=='success')
                {
                  
                	alert("Record Updated Successfully");
                	
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
                  
                	alert("Record Deleted Successfully");
                	
                	ClearAll();
                }
                else
                    {
                    
                    }
            	
            }
            
            if(command=="load")
            {
            	if(flag=='success')
                {
            		document.getElementById("sanname").value=response.getElementsByTagName("empname")[0].firstChild.nodeValue;
            		document.getElementById("sandesignation").value=response.getElementsByTagName("designation")[0].firstChild.nodeValue;
                }
                else
                    {
                    alert('Employee Not found');
                    }
            	
            }
            if(command=="checkemp")
            {
            	if(flag=='success')
                {
            		document.getElementById("sanname").value=response.getElementsByTagName("empname")[0].firstChild.nodeValue;
            		document.getElementById("sandesignation").value=response.getElementsByTagName("designation")[0].firstChild.nodeValue;
                }
                else
                    {
                    alert('Employee Not found');
                    }
            	
            }
            if(command=="headcode")
            {
            	if(flag=='success')
                {
            		document.getElementById("headdesc").value=response.getElementsByTagName("headname")[0].firstChild.nodeValue;
            		call('budget');
                }
                else
                    {
                    alert('Head Code Not found');
                    }
            	
            }
            
            if(command=="budget")
            {
            	if(flag=='success')
                {
            		document.getElementById("budgetprovided").value=response.getElementsByTagName("budgetalotted")[0].firstChild.nodeValue;
            		document.getElementById("budgetspent").value=response.getElementsByTagName("budgetspent")[0].firstChild.nodeValue;
            		 document.sanction_proceedings.cmdAdd.disabled=false;
                }
                else
                    {
                    alert('Budget Data Not found');
                    document.sanction_proceedings.cmdAdd.disabled=true;
                    }
            	
            }
            
       }
    }
}

function nullcheck()
{

	if(document.getElementById("majortype").value=="")
	{
		alert('Please Select Major Type');
		return false;
	}
	if(document.getElementById("minortype").value=="")
	{
		alert('Please Select Minor Type');
		return false;
	}
	if(document.getElementById("subtype").value=="")
	{
		alert('Please Select Sub Type');
		return false;
	}
	
	
	if(document.getElementById("refno").value=="")
	{
		alert('Please Enter Ref.No');
		return false;
	}
	if(document.getElementById("refdate").value=="")
	{
		alert('Please Enter Ref.Date');
		return false;
	}
	
	if(document.getElementById("prodate").value=="")
	{
		alert('Please Enter Proceeding Date');
		return false;
	}
	if(document.getElementById("sanctionauthority").value=="")
	{
		alert('Please Select Sanction Authority');
		return false;
	}
    
	if(document.getElementById("sanctionedby").value=="")
	{
		alert('Please Enter Sanctioned  By');
		return false;
	}
	if(document.getElementById("cmbAcHeadCode").value=="")
	{
		alert('Please Enter Account Head Code');
		return false;
	}

		
	if(document.getElementById("totalamount").value=="")
	{
		alert('Please Enter Total Sanction Amount');
		return false;
	}
	
	budgetcheck();
	
	
	var tbody=document.getElementById("grid_body");
	if(tbody.rows.length==0)
	{
	    alert("Please Enter Details Part");
	  	    return false; 
	}
	
	
	
	
return true;	

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


function budgetcheck()
{
	
  var total=document.getElementById("totalamount").value;
var budget=parseFloat(document.getElementById("budgetprovided").value)-parseFloat(document.getElementById("budgetspent").value);
	if(parseFloat(total)>parseFloat(budget))
	{
		alert('Total Amount is greater than Budget Amount');
		 document.sanction_proceedings_firm.butSub.disabled=true;
		
		return false;
	}else{
		 document.sanction_proceedings_firm.butSub.disabled=false;
		
	}
	
	
}


