function loadTransferUnit()
{         
		var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;		 
        url="../../../../../TDA_Raised_Create?command=loadTransferUnit&txtUnitId="+cmbAcc_UnitCode;
        req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {        	  
            TPA_Raised_ServletResponse(req);
        }   ;
        req.send(null);     
}


function clrForm()
{
	//document.getElementById("cmbAcc_UnitCode").value="";
	document.getElementById("tpadate").value="";
	document.getElementById("txtCB_Year").value="";
	document.getElementById("txtCB_Month").value="";
	
	 var tbody=document.getElementById("grid_body");
     var t=0;
     for(t=tbody.rows.length-1;t>=0;t--)
     {
             tbody.deleteRow(0);
     }

}


function checknull_submit()
{
	var tbody=document.getElementById("grid_body");       
//	alert(tbody.rows.length);
	
	if(tbody.rows.length>0)
		{
		var len_two=tbody.rows.length;
//        alert(len_two)
        for(var mm=0;mm<len_two;mm++)
        {
                 var cell_one=tbody.rows[mm].cells;
//                alert("welcome!....");
                 var grid_date=cell_one.item(9).lastChild.nodeValue;
//                 alert("grid_date!....>"+grid_date);
                 var journal_date=document.getElementById("tpadate").value;
//                 alert("journal_date!....>"+journal_date);
                 var str1_grid =grid_date.split("/");
                 var str2 = journal_date.split("/");
//                 alert("str1_grid[2]==>"+str1_grid[2]);
//                 alert("str2[2]==>"+str2[2]);
//                 alert("str1_grid[1]==>"+str1_grid[1]);
//                 alert("str2[1]==>"+str2[1]);
//                 alert("str1_grid[0]==>"+str1_grid[0]);
//                 alert("str2[0]==>"+str2[0]);
                 if(str1_grid[2]>str2[2])
                 {
                            alert("Freeze Date should not less be than Audit Verification Date**");
                             document.getElementById("tpadate").value="";
                             document.getElementById("tpadate").focus();
                             
                             return false;
                 }
                 else if(str1_grid[2]==str2[2])
                 {
                   
                        if(str1_grid[1]>str2[1])
                        {
                            alert("Freeze Date should not be less than Audit Verification Date**");
                             document.getElementById("tpadate").value="";
                             document.getElementById("tpadate").focus();
                             return false;
                        }
                        else if(str1_grid[1]==str2[1])
                        {
                        	//alert("month:::");
                        //	alert("day:::grid::::"+str1_grid[0]);
                        //	alert("day:::voucher::::"+str2[0]);
                            if(str1_grid[0]>str2[0])
                            {
                            alert("Freeze Date should not be less than Audit Verification Date**");
                             document.getElementById("tpadate").value="";
                             document.getElementById("tpadate").focus();
                             return false;
                            }
                        
                        }
                  
                 }
                 return true;
        }
    }	
}







function TPA_Raised_ServletResponse(req)
{
		if(req.readyState==4)
		{
            if(req.status==200)
            {  
            	
	            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	            var tagcommand=baseResponse.getElementsByTagName("command")[0];
	            var Command=tagcommand.firstChild.nodeValue;                                  
	            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            var cr_amt=0.00;var dr_amt=0.00;
	            if(Command=="loadTransferUnit")
                {                                       
                       if(flag=="success")
                       {                                      
                           var txtUnitId=document.getElementById("TransferedID");  
                           var child=txtUnitId.childNodes;
                           for(var i=child.length-1;i>1;i--)
                           {
                        	   	  txtUnitId.removeChild(child[i]);
                           }                                              
                           var items_id=new Array();
                           var items_name=new Array();                                    
                           var oid=baseResponse.getElementsByTagName("unit_id");
                           for(var k=0;k<oid.length;k++)
                           {
                                  items_id[k]=baseResponse.getElementsByTagName("unit_id")[k].firstChild.nodeValue;
                                  items_name[k]=baseResponse.getElementsByTagName("unit_name")[k].firstChild.nodeValue;				       	                                                  
                                  var option=document.createElement("OPTION");
                                  option.text=items_name[k];
                                  option.value=items_id[k];
                                  try
                                  {
                                	  txtUnitId.add(option);
                                  }
                                  catch(errorObject)
                                  {
                                      txtUnitId.add(option,null);
                                  }
                           }
                       }
                       else
                       {                                                   
                           document.getElementById("TransferedID").value="";
                       }
               }else if(Command=="Add")
               {
            	   if(flag=="success")
                   {  
            		   alert('Freezed Successfully');
            		   clrForm();
                   } else{
                	   alert('Data Already Entered'); 
                   }      
               }
            }
		}
}


function clrForm()
{
	LoadAccountingUnitID('LIST_ALL_UNITS');
	
	document.frm_TPA_Raised_Create.Org_CR_DR[0].checked='checked';	
	document.frm_TPA_Raised_Create.unitauthoriz[0].checked='checked';
	
	document.getElementById("tpadate").value="";	
		
	document.getElementById("txtCB_Year").value="";
	
}

function storeit()
{
	if(nullcheck())
	{
		var Org_CR_DR="";
	var unitauthoriz="";
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;	
	
	for (var i=0; i < document.frm_TPA_Raised_Create.Org_CR_DR.length; i++)
	   {
	   if (document.frm_TPA_Raised_Create.Org_CR_DR[i].checked)
	      {
	       Org_CR_DR = document.frm_TPA_Raised_Create.Org_CR_DR[i].value;
	      }
	   }

	for (var i=0; i < document.frm_TPA_Raised_Create.unitauthoriz.length; i++)
	   {
	   if (document.frm_TPA_Raised_Create.unitauthoriz[i].checked)
	      {
	       unitauthoriz = document.frm_TPA_Raised_Create.unitauthoriz[i].value;
	      }
	   }
	
	
	
	var tpadate=document.getElementById("tpadate").value;	
	
	var cashyear=document.getElementById("txtCB_Year").value;	
	var cashmonth=document.getElementById("txtCB_Month").value;
	
    url="../../../../../TPA_Freeze_System?command=Add&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&Org_CR_DR="+Org_CR_DR+"&unitauthoriz="+unitauthoriz+"&cashyear="+cashyear+"&cashmonth="+cashmonth+"&tpadate="+tpadate+"";
    req=getTransport();
    req.open("POST",url,true);        
    req.onreadystatechange=function()
    {        	  
        TPA_Raised_ServletResponse(req);
    }   ;
    req.send(null);  
	}

}

function nullcheck()
{

	if(document.getElementById("txtCB_Year").value=="")
	{
		alert('Please Enter Cash Book Year');
		return false;
		}

	
	
if(document.getElementById("tpadate").value=="")
{
	alert('Please Enter TPA Date');
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
		
			var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
			//var cmbOffice_code=document.getElementById("cmbOffice_code").value;
						var cashmonth=document.getElementById("txtCB_Month").value;
			var cashyear=document.getElementById("txtCB_Year").value;
			var url="../../../../../TPA_Freeze_System?command=get&cashmonth="+cashmonth+"&cashyear="+cashyear+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;

			url=url+"&sid="+Math.random();
			xmlhttp.open("GET",url,true);
			xmlhttp.onreadystatechange=stateChanged;
			xmlhttp.send(null); 
		


	} 
	else if(command=="getbydate")
	{ 
		if(checknull())
		{
			var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
			var cmbOffice_code=document.getElementById("cmbOffice_code").value;
			var proformatype=document.getElementById("proformatype").value;
			var status=document.getElementById("cmbStatus").value;
			var cashmonth=document.getElementById("txtCB_Month").value;
			var cashyear=document.getElementById("txtCB_Year").value;

			if(document.getElementById("txtFrom_date").value=="")
			{
				alert('Please Enter From Date');
				return false;
			}
			if(document.getElementById("txtTo_date").value=="")
			{
				alert('Please Enter To Date');
				return false;
			}
			var fromdate=document.getElementById("txtFrom_date").value;
			var todate=document.getElementById("txtTo_date").value;
			var url="../../../../../TPA_Acceptance_Verification?command=get&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&searchby=date&cmbOffice_code="+cmbOffice_code+"&proformatype="+proformatype+"&status="+status+"&fromdate="+fromdate+"&todate="+todate+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"";

			url=url+"&sid="+Math.random();
			xmlhttp.open("GET",url,true);
			xmlhttp.onreadystatechange=stateChanged;
			xmlhttp.send(null);  
		}

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

					try{

						
						var len=response.getElementsByTagName("vno").length;
						
						var	tbody = document.getElementById("grid_body");

						var t=0;
						for(t=tbody.rows.length-1;t>=0;t--)
						{
							tbody.deleteRow(0);

						}
						var seq=0;
						for(var i=0;i<len;i++)
						{

							var vno=response.getElementsByTagName("vno")[i].firstChild.nodeValue;	
							var vdate=response.getElementsByTagName("vdate")[i].firstChild.nodeValue;		
							var reason=response.getElementsByTagName("reason")[i].firstChild.nodeValue;		
							
							var org_name=response.getElementsByTagName("org_name")[i].firstChild.nodeValue;		
							var org_unitid=response.getElementsByTagName("org_unitid")[i].firstChild.nodeValue;	
							var org_officeid=response.getElementsByTagName("org_officeid")[i].firstChild.nodeValue;
							var trf_name=response.getElementsByTagName("trf_name")[i].firstChild.nodeValue;		
							var tpatype=response.getElementsByTagName("tpatype")[i].firstChild.nodeValue
							
							var amount=response.getElementsByTagName("amount")[i].firstChild.nodeValue;	
							
							var audit_verify=response.getElementsByTagName("audit_verify")[i].firstChild.nodeValue;	
							var audit_verified_date=response.getElementsByTagName("audit_verified_date")[i].firstChild.nodeValue;	

							var mycurrent_row = document.createElement("TR");
							seq = seq;
							mycurrent_row.id = seq;

							var checkparam = seq ;

							var descell=document.createElement("TD");
							descell.style.textAlign='center'; 
							var chcksel="";
							if(window.navigator.appName.toLowerCase().indexOf("netscape")==-1)
							{
								chcksel=document.createElement("<input type='checkbox' name='chckparameter' id='chckparameter' value='"+vno+"' />");
							}
							else
							{
								var chcksel=document.createElement("input");
								chcksel.type="checkbox";
								chcksel.name="chckparameter";
								chcksel.id="chckparameter";                
								chcksel.value= i;
							}
							descell.appendChild(chcksel);
							mycurrent_row.appendChild(descell);


							var cell2;
							cell2 = document.createElement("TD");
							cell2.align="center";
							var item=document.createElement("input");
							item.type="hidden";
							item.name="vouchno";
							item.value=vno;
							cell2.appendChild(item);
							var currentText = document.createTextNode(vno);
							cell2.appendChild(currentText);
							mycurrent_row.appendChild(cell2);

							
							cell2 = document.createElement("TD");
							var item=document.createElement("input");
							item.type="hidden";
							item.name="vrdate";
							item.value=vdate;
							cell2.appendChild(item);
							var currentText = document.createTextNode(vdate);
							cell2.appendChild(currentText);
							mycurrent_row.appendChild(cell2);
							
							
//							cell2 = document.createElement("TD");
//
//							var currentText = document.createTextNode(vdate);
//							cell2.appendChild(currentText);
//							mycurrent_row.appendChild(cell2);

							cell2 = document.createElement("TD");
							var item=document.createElement("input");
							item.type="hidden";
							item.name="org_unit";
							item.value=org_unitid;
							cell2.appendChild(item);
							var currentText = document.createTextNode(org_name);
							cell2.appendChild(currentText);
							mycurrent_row.appendChild(cell2);
							
							
							cell2 = document.createElement("TD");
							var item=document.createElement("input");
							item.type="hidden";
							item.name="crdr";
							item.value=tpatype;
							cell2.appendChild(item);
							var currentText = document.createTextNode(tpatype);
							cell2.appendChild(currentText);
							mycurrent_row.appendChild(cell2);
							
							
							
							cell2 = document.createElement("TD");
							var item=document.createElement("input");
							item.type="hidden";
							item.name="officeid";
							item.value=org_officeid;
							cell2.appendChild(item);
							var currentText = document.createTextNode(amount);
							cell2.appendChild(currentText);
							mycurrent_row.appendChild(cell2);
							
							
							
							
							
							cell2 = document.createElement("TD");
							var item=document.createElement("input");
							item.type="hidden";
							item.name="reason";
							item.value=reason;
							cell2.appendChild(item);
							var currentText = document.createTextNode(reason);
							cell2.appendChild(currentText);
							mycurrent_row.appendChild(cell2);
							
							cell2 = document.createElement("TD");
							var currentText = document.createTextNode(trf_name);
							cell2.appendChild(currentText);
							mycurrent_row.appendChild(cell2);
							
							
							cell2 = document.createElement("TD");
							cell2.align="center";
							var currentText = document.createTextNode(audit_verify);
							cell2.appendChild(currentText);
							mycurrent_row.appendChild(cell2);
							
							
							cell2 = document.createElement("TD");
							cell2.align="center";
							var currentText = document.createTextNode(audit_verified_date);
							cell2.appendChild(currentText);
							mycurrent_row.appendChild(cell2);
							
							tbody.appendChild(mycurrent_row);

						}
					}catch(e){alert(e);}
				}else if(flag=='Credit')
				{
					var	tbody = document.getElementById("grid_body");
					var t=0;
					for(t=tbody.rows.length-1;t>=0;t--)
					{
						tbody.deleteRow(0);

					}
					alert('Debit not yet verified');
				}
				else if(flag=='Debit')
				{
					var	tbody = document.getElementById("grid_body");
					var t=0;
					for(t=tbody.rows.length-1;t>=0;t--)
					{
						tbody.deleteRow(0);

					}
					alert('Credit not yet verified');
				}
				else 
				{
					var	tbody = document.getElementById("grid_body");

					var t=0;
					for(t=tbody.rows.length-1;t>=0;t--)
					{
						tbody.deleteRow(0);

					}
					alert('No Data Found');
				}
			}
		}
	}
}
