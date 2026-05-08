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
    checkemp=1;
}


function servicepopup1()
{
    if (winemp1 && winemp1.open && !winemp1.closed) 
    {
    	winemp1.resizeTo(500,500);
    	winemp1.moveTo(250,250); 
    	winemp1.focus();
       return;
    }
    else
    {
    	winemp1=null;
    }
    // startwaiting(document.frmEmployee) ;   
    winemp1= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp1.moveTo(250,250);  
    winemp1.focus();
    checkemp=2;
}



function doParentEmp(emp)
{
	if(checkemp==1){
document.sanction_proceedings.txtEmpId.value=emp;
call('load');
	}
	else if(checkemp==2)
	{
		document.sanction_proceedings.sanctionedby.value=emp;	
	}

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


if(command=="Add")
{ 
	if(nullcheck()){
		
		var unitid=document.getElementById("cmbAcc_UnitCode").value;
		var officeid=document.getElementById("cmbOffice_code").value;
		
		var cashmonth=2;
		var cashyear=2010;
		
		var majortype=document.getElementById("majortype").value;
		var minortype=document.getElementById("minortype").value;
		var subtype=document.getElementById("subtype").value;
		var empid=document.getElementById("txtEmpId").value;
		var refno=document.getElementById("refno").value;
		var refdate=document.getElementById("refdate").value;
		var prodate=document.getElementById("prodate").value;
		var sanctionauthority=document.getElementById("sanctionauthority").value;
		var sanctionedby=document.getElementById("sanctionedby").value;
		var achead=document.getElementById("cmbAcHeadCode").value;
		
		var paymentunit=document.getElementById("paymentunit").value;
		var totalinstallment=document.getElementById("totalinstallment").value;
		var emi=document.getElementById("emi").value;
		var recoverymonth=document.getElementById("recoverymonth").value;
		var residualamount=document.getElementById("residualamount").value;
		var installment=document.getElementById("installment").value;
		var totalamount=document.getElementById("totalamount").value;
		var remarks=document.getElementById("remarks").value;
		var payeetype;
		for (var i=0; i < document.sanction_proceedings.payeetype.length; i++)
			{
				if (document.sanction_proceedings.payeetype[i].checked)
				{
					payeetype = document.sanction_proceedings.payeetype[i].value;
				}
			}
		var recovery;
		if(document.sanction_proceedings.recovery[0].checked==true)
		{
			recovery="Y";
		}
		else if(document.sanction_proceedings.recovery[1].checked==true)
		{
		recovery="N";
		}
	var payment;
	if(document.sanction_proceedings.payment[0].checked==true)
	{
		payment=document.sanction_proceedings.payment[0].value;
	}
	else if(document.sanction_proceedings.payment[1].checked==true)
	{
		payment=document.sanction_proceedings.payment[1].value;
	}
		
	  var url="../../../../../Sanction_Proceedings?command=Add&majortype="+majortype+"&minortype="+minortype+"&subtype="+subtype+"&empid="+empid+"&refno="+refno+"&refdate="+refdate+"&prodate="+prodate+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"";
	  url=url+"&sanctionauthority="+sanctionauthority+"&sanctionedby="+sanctionedby+"&achead="+achead+"&paymentunit="+paymentunit+"&totalinstallment="+totalinstallment+"&payment="+payment+"";
      url=url+"&emi="+emi+"&recoverymonth="+recoverymonth+"&residualamount="+residualamount+"&installment="+installment+"&totalamount="+totalamount+"&remarks="+remarks+"&payeetype="+payeetype+"&recovery="+recovery+"&unitid="+unitid+"&officeid="+officeid+"";
	  url=url+"&sid="+Math.random();
      xmlhttp.open("GET",url,true);
      xmlhttp.onreadystatechange=stateChanged;
      xmlhttp.send(null);  
	}
	
}

if(command=="Update")
{ 
	if(nullcheck()){
		
		var unitid=document.getElementById("cmbAcc_UnitCode").value;
		var officeid=document.getElementById("cmbOffice_code").value;
		
		var cashmonth=2;
		var cashyear=2010;
		
		var majortype=document.getElementById("majortype").value;
		var minortype=document.getElementById("minortype").value;
		var subtype=document.getElementById("subtype").value;
		var empid=document.getElementById("txtEmpId").value;
		var refno=document.getElementById("refno").value;
		var refdate=document.getElementById("refdate").value;
		var prodate=document.getElementById("prodate").value;
		var sanctionauthority=document.getElementById("sanctionauthority").value;
		var sanctionedby=document.getElementById("sanctionedby").value;
		var achead=document.getElementById("cmbAcHeadCode").value;
		
		var paymentunit=document.getElementById("paymentunit").value;
		var totalinstallment=document.getElementById("totalinstallment").value;
		var emi=document.getElementById("emi").value;
		var recoverymonth=document.getElementById("recoverymonth").value;
		var residualamount=document.getElementById("residualamount").value;
		var installment=document.getElementById("installment").value;
		var totalamount=document.getElementById("totalamount").value;
		var remarks=document.getElementById("remarks").value;
		var payeetype;
		for (var i=0; i < document.sanction_proceedings.payeetype.length; i++)
			{
				if (document.sanction_proceedings.payeetype[i].checked)
				{
					payeetype = document.sanction_proceedings.payeetype[i].value;
				}
			}
		var recovery;
		if(document.sanction_proceedings.recovery[0].checked==true)
		{
			recovery="Y";
		}
		else if(document.sanction_proceedings.recovery[1].checked==true)
		{
		recovery="N";
		}
	var payment;
	if(document.sanction_proceedings.payment[0].checked==true)
	{
		payment=document.sanction_proceedings.payment[0].value;
	}
	else if(document.sanction_proceedings.payment[1].checked==true)
	{
		payment=document.sanction_proceedings.payment[1].value;
	}
	
	var sanctionno=document.getElementById("sanctionno").value;
		
	  var url="../../../../../Sanction_Proceedings?command=update&majortype="+majortype+"&minortype="+minortype+"&subtype="+subtype+"&empid="+empid+"&refno="+refno+"&refdate="+refdate+"&prodate="+prodate+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"";
	  url=url+"&sanctionauthority="+sanctionauthority+"&sanctionedby="+sanctionedby+"&achead="+achead+"&paymentunit="+paymentunit+"&totalinstallment="+totalinstallment+"&payment="+payment+"";
      url=url+"&emi="+emi+"&recoverymonth="+recoverymonth+"&sanctionno="+sanctionno+"&residualamount="+residualamount+"&installment="+installment+"&totalamount="+totalamount+"&remarks="+remarks+"&payeetype="+payeetype+"&recovery="+recovery+"&unitid="+unitid+"&officeid="+officeid+"";
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
		var unitid=document.getElementById("cmbAcc_UnitCode").value;
		var officeid=document.getElementById("cmbOffice_code").value;
		var prodate=document.getElementById("prodate").value;
		var sanctionno=document.getElementById("sanctionno").value;
		
	  var url="../../../../../Sanction_Proceedings?command=Delete&unitid="+unitid+"&officeid="+officeid+"&prodate="+prodate+"&sanctionno="+sanctionno+"";
	  url=url+"&sid="+Math.random();
      xmlhttp.open("GET",url,true);
      xmlhttp.onreadystatechange=stateChanged;
      xmlhttp.send(null);  
    }
	
}

 if(command=="get")
{ 
	 if(document.getElementById("majortype").value=="")
		{
			alert('Please Select Major Type');
			return false;
		}
	
	var majortype=document.getElementById("majortype").value;
	
	
	  var url="../../../../../Sanction_Proceedings?command=get&majortype="+majortype+"";
	 
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
 	
 	  var url="../../../../../Sanction_Proceedings?command=getsub&majortype="+majortype+"&minortype="+minortype+"";
 	 
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  
     
 	
 } 
 else if(command=="load")
 { 
 	
	 if(document.getElementById("txtEmpId").value=="")
		{
			alert('Please Enter Payee Code');
			return false;
		}
		
		var empid=document.getElementById("txtEmpId").value;
		
 	  var url="../../../../../Sanction_Proceedings?command=load&empid="+empid+"";
 	 
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
		
 	  var url="../../../../../Sanction_Proceedings?command=checkemp&empid="+empid+"";
 	 
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
 	  var url="../../../../../Sanction_Proceedings?command=headcode&hcode="+hcode+"";
 	 
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
		
		
		
 	  var url="../../../../../Sanction_Proceedings?command=budget&hcode="+hcode+"&unitid="+unitid+"&officeid="+officeid+"&prodate="+prodate+"";
 	 
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
            		document.getElementById("empname").value=response.getElementsByTagName("empname")[0].firstChild.nodeValue;
            		document.getElementById("designation").value=response.getElementsByTagName("designation")[0].firstChild.nodeValue;
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
	if(document.getElementById("txtEmpId").value=="")
	{
		alert('Please Enter Payee Code');
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

	if(document.getElementById("paymentunit").value=="")
	{
		alert('Please Select Accounting Unit in which the payment to be made');
		return false;
	}
	
	if(document.getElementById("totalinstallment").value=="")
	{
		alert('Please Enter Total Installment');
		return false;
	}
	if(document.getElementById("emi").value=="")
	{
		alert('Please Enter EMI');
		return false;
	}
	
	if(document.getElementById("recoverymonth").value=="")
	{
		alert('Please Select Recovery Month');
		return false;
	}
	if(document.getElementById("residualamount").value=="")
	{
		alert('Please Enter Residual Amount');
		return false;
	}
	if(document.getElementById("installment").value=="")
	{
		alert('Please Enter Amount Deduction Instl.No');
		return false;
	}
	if(document.getElementById("totalamount").value=="")
	{
		alert('Please Enter Total Sanction Amount');
		return false;
	}
	totalcal();
	
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

function ClearAll()
{
	document.sanction_proceedings.payment[0].checked='Checked'; 
	document.getElementById("majortype").options[0].selected='selected';
	document.getElementById("minortype").options[0].selected='selected';
	document.getElementById("subtype").options[0].selected='selected';
	document.sanction_proceedings.payeetype[0].checked='Checked'; 
	document.getElementById("txtEmpId").value = "";
	
	document.getElementById("empname").value = "";
	document.getElementById("designation").value = "";
    
	document.getElementById("refno").value = "";
	document.getElementById("refdate").value = "";
	document.getElementById("prodate").value = "";
	
	
	
	document.getElementById("sanctionauthority").options[0].selected='selected';
	document.getElementById("sanctionedby").value = "";
	document.getElementById("sanname").value = "";
	document.getElementById("sandesignation").value = "";
	document.getElementById("cmbAcHeadCode").value = "";
	document.getElementById("headdesc").value = "";
	document.getElementById("budgetprovided").value = "";
	document.getElementById("budgetspent").value = "";
	
	document.getElementById("paymentunit").options[0].selected='selected';
	document.sanction_proceedings.recovery[0].checked='Checked'; 
	document.getElementById("totalinstallment").value = "";
	document.getElementById("emi").value = "";
//	document.getElementById("recoverymonth").value = "";
	document.getElementById("recoverymonth").options[0].selected='selected';
	document.getElementById("residualamount").value =0;
	document.getElementById("installment").value = "";
	document.getElementById("totalamount").value = "";
	document.getElementById("remarks").value = "";
	
	document.getElementById("prodate").disabled=false;
	
	var d=document.getElementById("cmdAdd");
    d.style.display="block";
    var d1=document.getElementById("cmdUpdate");
    d1.style.display="none";
    var d3=document.getElementById("cmdDelete");
    d3.style.display="none";
    document.sanction_proceedings.cmdAdd.disabled=false;

}

function budgetcheck()
{
	
  var total=document.getElementById("totalamount").value;
var budget=parseFloat(document.getElementById("budgetprovided").value)-parseFloat(document.getElementById("budgetspent").value);
	if(parseFloat(total)>parseFloat(budget))
	{
		alert('Total Amount is greater than Budget Amount');
		 document.sanction_proceedings.cmdAdd.disabled=true;
		 document.sanction_proceedings.cmdUpdate.disabled=true;
		return false;
	}else{
		 document.sanction_proceedings.cmdAdd.disabled=false;
		 document.sanction_proceedings.cmdUpdate.disabled=false;
	}
	
	
}
function totalcal()
{
	 var install=document.getElementById("totalinstallment").value;
	 var emi=document.getElementById("emi").value;
	 var totalamount=parseFloat(install)*parseFloat(emi);
	 if(document.getElementById("residualamount").value!=0)
	 {
		 totalamount=parseFloat(totalamount)+parseFloat(document.getElementById("residualamount").value) ;
	 }
	 
	 document.getElementById("totalamount").value=totalamount;
	 
	 budgetcheck();
}



var window_sanction;

function ListHeads()
{ 
 
 
        if (window_sanction && window_sanction.open && !window_sanction.closed) 
         {
        	window_sanction.resizeTo(500,500);
        	window_sanction.moveTo(250,250); 
        	window_sanction.focus();
         }
         else
         {
        	 window_sanction=null;
         }
        
        var unitid=document.getElementById("cmbAcc_UnitCode").value;
		var officeid=document.getElementById("cmbOffice_code").value;
        window_sanction= window.open("Sanction_Proceedings_List.jsp?unitid="+unitid+"&officeid="+officeid+"","mywindow1","resizable=YES, scrollbars=yes"); 
        window_sanction.moveTo(250,250);  
      
 
}

function doParentsanction(majortype,minortype,subtype,payeetype,payeecode,refno,refdate,sanctionno,prodate,sanctionauthority,sanctionedby,headcode,totalinstall,paymentunit,recovery,startmonth,residualamount,installno,emi,totalamount,remarks,payment)
{
	
		
	for (var i=0; i < document.sanction_proceedings.payment.length; i++)
	{
		if (document.sanction_proceedings.payment[i].value==payment)
		{
		document.sanction_proceedings.payeetype[i].checked='checked';
		}
	}
	
	
	for (var i=0; i < document.sanction_proceedings.majortype.length; i++)
	{
		if (document.sanction_proceedings.majortype[i].value==majortype)
		{
		document.sanction_proceedings.majortype[i].selected='selected';
		
		}
	}
	 
	//call('get');
	//alert('here');
	//setTimeout("",1250);
	setTimeout("call('get')", 150);
	setTimeout("setcheck("+minortype+")", 250);
	
	for (var i=0; i < document.sanction_proceedings.recovery.length; i++)
	{
		if (document.sanction_proceedings.recovery[i].value==recovery)
		{
		document.sanction_proceedings.recovery[i].checked='checked';
		}
	}
	
	for (var i=0; i < document.sanction_proceedings.payeetype.length; i++)
	{
		if (document.sanction_proceedings.payeetype[i].value==payeetype)
		{
		document.sanction_proceedings.payeetype[i].checked='checked';
		}
	}
	
	
	
	
	
	
	document.getElementById("txtEmpId").value = payeecode;
	
	setTimeout("call('getsub')", 300);
	
	
	document.getElementById("refno").value = refno;
	document.getElementById("refdate").value = refdate;
	document.getElementById("prodate").value = prodate;
	
	for (var i=0; i < document.sanction_proceedings.sanctionauthority.length; i++)
	{
		if (document.sanction_proceedings.sanctionauthority[i].value==sanctionauthority)
		{
		document.sanction_proceedings.sanctionauthority[i].selected='selected';
		}
	}
	
	
	
	document.getElementById("sanctionedby").value = sanctionedby;
	
	document.getElementById("cmbAcHeadCode").value = headcode;
	
	setTimeout("setcheck1("+subtype+")", 600);
	for (var i=0; i < document.sanction_proceedings.paymentunit.length; i++)
	{
		if (document.sanction_proceedings.paymentunit[i].value==paymentunit)
		{
		document.sanction_proceedings.paymentunit[i].selected='selected';
		}
	}
	
	
	document.getElementById("totalinstallment").value =totalinstall;
	document.getElementById("emi").value = emi;

	for (var i=0; i < document.sanction_proceedings.recoverymonth.length; i++)
	{
		
		if (document.sanction_proceedings.recoverymonth[i].value==startmonth)
		{
		document.sanction_proceedings.recoverymonth[i].selected='selected';
		}
	}
	
	document.getElementById("residualamount").value = residualamount;
	document.getElementById("installment").value = installno;
	document.getElementById("totalamount").value = totalamount;
	document.getElementById("remarks").value = remarks;	
	
	document.getElementById("sanctionno").value = sanctionno;	
	setTimeout("call('load')",700);
	setTimeout("call('checkemp')", 800);
	
	setTimeout("call('headcode')", 900);
	
	
	document.getElementById("prodate").disabled=true;
	
	
	var d=document.getElementById("cmdUpdate");
    d.style.display="block";
    var d2=document.getElementById("cmdDelete");
    d2.style.display="block";
    var d1=document.getElementById("cmdAdd");
    d1.style.display="none";  


}


function setcheck(minor)
{
	
	for (var i=0; i < document.sanction_proceedings.minortype.length; i++)
	{
		if (document.sanction_proceedings.minortype[i].value==minor)
		{
		document.sanction_proceedings.minortype[i].selected='selected';
		}
	}	
}


function setcheck1(sub)
{
	
	for (var i=0; i < document.sanction_proceedings.subtype.length; i++)
	{
		if (document.sanction_proceedings.subtype[i].value==sub)
		{
		document.sanction_proceedings.subtype[i].selected='selected';
		}
	}
}


