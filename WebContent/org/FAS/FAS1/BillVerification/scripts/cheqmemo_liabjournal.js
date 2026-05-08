var seq=0;
var com_id;var val;
var emp_flag;
/////////////////////////////creating AJAX object////////////////////////
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

/////////////////////////////////code to check textarea//////////////////////////////////////////////////////
function check_leng(param,val)
{	 
		if((val.length)>=190)
		{
			  if(param=='particulars')			  
				  	   alert("Please Enter Particulars below 200 characters");			           			  
		}
}
///////////////////////////////exit mathod////////////////////////////////////////////////////////////
function exitmethod()
{
      window.close();
}
//////////function to load the cheque memo type code from table/////////////////////////
function loadcheqmemotype()
{
        var url="../../../../../Cheqmemo_liabjournal?command=loadcheqmemo";
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
                processResponse(req);
        }   
        req.send(null);
}
///////////function to load the payee type depends on the cheque memo typs//////////////
function loadpayee_type()
{
    var cheqmemo_type=document.getElementById("cmbcheqmemotype").value;
    var url="../../../../../Cheqmemo_liabjournal?command=loadpayeetype&cheqmemo_type1="+cheqmemo_type;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
                processResponse(req);
        }   
        req.send(null);
}

function processResponse(req)
{   
    if(req.readyState==4)   // Completed
      {
          if(req.status==200)   // No error
          {   
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              if(command=="loadcheqmemo")
              {
                    LoadChequeMemo(baseResponse);
              }
              else if(command=="loadPayeeType")
              {
                    LoadPayeeType(baseResponse);
              }
               else if(command=="loadempdetails")
              {
                    LoadEmpDetails(baseResponse);
              }
               else if(command=="loadDetails")
               {
                  loadingGrid(baseResponse);
               }
                else if(command=="cheqcheck")
               {
                  checkcheque(baseResponse);
               }
        }    
    }
}


function stateChanged_yes()
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
                		
              		  var len=response.getElementsByTagName("billno").length;
                 	var billno=document.getElementById("billno");
                 
              	 var items_id=new Array();
              	 var items_desc=new Array();                        
                          for(var i=0;i<len;i++)
                          {
                       	 items_id[i]=response.getElementsByTagName("billno")[i].firstChild.nodeValue;
                       	items_desc[i]=response.getElementsByTagName("billno")[i].firstChild.nodeValue;  
                      // alert('minor'+items_desc[i]);
                          }
                     clear_Combo(billno);
                   //  billno.length=0;
                          //alert('here second');
                          for(var k=0;k<len;k++)
                          {   
                          	//alert(items_name[k]);
                                var option=document.createElement("OPTION");
                                option.text=items_desc[k];
                                option.value=items_id[k];
                             
                                 try
                                {
                                	 billno.add(option);
                                	
                                }
                                catch(errorObject)
                                {
                                	billno.add(option,null);
                                	
                                   // alert('error');
                                }
                          }
              	
              	}catch(e){alert("Error in lat"+e);}      
                
                }
                else
                    {
                    
                                     
                    }
                 }
            if(command=="getdata")
            {
            	
                if(flag=='success')
                {

                	try{
                		
                		
                		document.getElementById("billdate").value =response.getElementsByTagName("billdate")[0].firstChild.nodeValue;
                		document.getElementById("passorderamount").value=response.getElementsByTagName("passamount")[0].firstChild.nodeValue;  
                		//document.getElementById("txtAcc_HeadCode").value=response.getElementsByTagName("achead")[0].firstChild.nodeValue;
                	//	document.getElementById("txtAcc_HeadDesc").value=response.getElementsByTagName("head_desc")[0].firstChild.nodeValue;
                		
                		document.getElementById("txtAmount").value=response.getElementsByTagName("passamount")[0].firstChild.nodeValue;
                		
                		
                		//doFunction('checkCode','null');
              	}catch(e){alert("Error in lat"+e);}      
                
                }
                else
                    {
                    }
                 }
            if(command=="getcode")
            {
            	if(flag=='success')
                {
            		 var len=response.getElementsByTagName("paycode").length;
                  	var payeetype=document.getElementById("payeetype");
                  	payeetype.value=0;
               	 var items_id=new Array();
               	 var items_desc=new Array();                        
                           for(var i=0;i<len;i++)
                           {
                        	 items_id[i]=response.getElementsByTagName("paycode")[i].firstChild.nodeValue;
                        	items_desc[i]=response.getElementsByTagName("paydesc")[i].firstChild.nodeValue;  
                       // alert('minor'+items_desc[i]);
                           }
                      clear_Combo(payeetype);
                  	payeetype.length=0;
                           //alert('here second');
                           for(var k=0;k<len;k++)
                           {   
                           	//alert(items_name[k]);
                                 var option=document.createElement("OPTION");
                                 option.text=items_desc[k];
                                 option.value=items_id[k];
                              
                                  try
                                 {
                                	  payeetype.add(option);
                                 	
                                 }
                                 catch(errorObject)
                                 {
                                	 payeetype.add(option,null);
                                 	
                                    // alert('error');
                                 }
                           }
                }
                else
                    {
                    alert('Payee Type Not Found');
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

 if(command=="get")
{ 
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var chequedate=document.getElementById("memodate").value;
        //alert("chequedate:::::::::::"+chequedate);
		
	  var url="../../../../../Cheque_Memo?command=get&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&chequedate="+chequedate+"";
	 
	  url=url+"&sid="+Math.random();
      xmlhttp.open("GET",url,true);
      xmlhttp.onreadystatechange=stateChanged_yes;
      xmlhttp.send(null);  
    
	
} 
 
 
 else if(command=="getdata")
 { 
 	
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
     var chequedate=document.getElementById("memodate").value;
	
     var billno=document.getElementById("billno").value;
     
  var url="../../../../../Cheque_Memo?command=getdata&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&chequedate="+chequedate+"&billno="+billno+"";
 
  url=url+"&sid="+Math.random();
  xmlhttp.open("GET",url,true);
  xmlhttp.onreadystatechange=stateChanged_yes;
  xmlhttp.send(null);  
     
 	
 } 
 else if(command=="getcode")
 { 
 	
	 if(document.getElementById("memotype").value=="")
		{
			alert('Please Select Payee Type');
			return false;
		}
		
		var memotype=document.getElementById("memotype").value;
		
 	  var url="../../../../../Cheque_Memo?command=getcode&memotype="+memotype+"";
 	 
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged_yes;
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
		
 	  var url="../../../../../cheque_memo?command=checkemp&empid="+empid+"";
 	 
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  
     
 	
 } 
 else if(command=="headcode")
 { 
	 if(document.getElementById("headcode").value=="")
		{
			alert('Please Enter Account Head Code');
			return false;
		}
	 
		var hcode=document.getElementById("headcode").value;
 	  var url="../../../../../Cheque_Memo?command=headcode&hcode="+hcode+"";
 	 
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
		
		
		
 	  var url="../../../../../cheque_memo?command=budget&hcode="+hcode+"&unitid="+unitid+"&officeid="+officeid+"&prodate="+prodate+"";
 	 
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
		
 	  var url="../../../../../cheque_memo?command=load&empid="+empid+"";
 	 
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  
     
 	
 } 
 
 
}


function LoadChequeMemo(baseResponse)
{
                 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                  if(flag=="success")
                  {    
                        var option=baseResponse.getElementsByTagName("option");
                        var cheqmemo=document.getElementById("cmbcheqmemotype");
                        var child=cheqmemo.childNodes;
                        for(var i=child.length-1;i>1;i--)
                        {
                                cheqmemo.removeChild(child[i]);
                        }
                        for(var i=0;i<option.length;i++)
                         {
                            var code=option[i].getElementsByTagName("cheqmemo_type_code")[0].firstChild.nodeValue;
                            var desc=option[i].getElementsByTagName("cheqmemo_type_desc")[0].firstChild.nodeValue;
                            //alert(code+"   "+desc);
                            var opt=document.createElement("option");
                            opt.setAttribute("value",code);
                            var opttext=document.createTextNode(desc);
                            opt.appendChild(opttext);
                            cheqmemo.appendChild(opt);
                         }
                  }
                  else if(flag=="nodata")
                  {
                        alert("No Cheque memo type available");
                  }
                  else
                  {
                        alert("Failed to load Cheque memo type");
                  }
}
function LoadPayeeType(baseResponse)
{
                 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                  if(flag=="success")
                  {    
                        //alert(flag);
                        var option=baseResponse.getElementsByTagName("option");
                        var payeetype=document.getElementById("cmbpayeetype");
                        var child=payeetype.childNodes;
                        for(var i=child.length-1;i>1;i--)
                        {
                                payeetype.removeChild(child[i]);
                        }
                        for(var i=0;i<option.length;i++)
                         {
                            var code=option[i].getElementsByTagName("payee_type_code")[0].firstChild.nodeValue;
                            var desc=option[i].getElementsByTagName("payee_type_desc")[0].firstChild.nodeValue;
                            //alert(code+"   "+desc);
                            var opt=document.createElement("option");
                            opt.setAttribute("value",code);
                            var opttext=document.createTextNode(desc);
                            opt.appendChild(opttext);
                            payeetype.appendChild(opt);
                         }
                  }
                  else if(flag=="nodata")
                  {
                        alert("No payee type available");
                  }
                  else
                  {
                        alert("Failed to load payee type");
                  }
}
/////////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
function emp_payee_code()
{
        emp_flag=1;    
        Load_emp_details();
}
function emp_popup_payee_code()
{
        emp_flag=1;    
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
                document.formcheqmemo_liabjrnl.txtpayee_code_Empcode1.value=emp;
                Load_emp_details();
        }
}
function Load_emp_details()
{
        if(emp_flag==1)
        {
                var emp_id=document.getElementById("txtpayee_code_Empcode1").value;
        }
          var url="";
             url="../../../../../phone_master_servlet?command=loadempdetails&emp_id="+emp_id;
             //alert(url);
             var req=getTransport();
              req.open("GET",url,true);        
              req.onreadystatechange=function()
              {
                       processResponse(req);
              }   
              req.send(null);
}

function LoadEmpDetails(baseResponse)
{
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                  if(flag=="success")
                  {                       
                         var emp_name=baseResponse.getElementsByTagName("emp_name")[0].firstChild.nodeValue;
                         var desig_name=baseResponse.getElementsByTagName("desig_name")[0].firstChild.nodeValue;
                         var office_name=baseResponse.getElementsByTagName("office_name")[0].firstChild.nodeValue;
                         if(emp_flag==1)
                         {
                                document.formcheqmemo_liabjrnl.txtpayee_code_Empdesc.value=emp_name+"      "+desig_name;
                         }
                }
                else if(flag=="nodata")
                {
                        alert("Invalid Employee Id");
                }
                else
                {
                        alert("Failed to load");
                }
}
//function used to enter a valid amount i.e 10digit.2digit,numbers only......
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
function filter_real(evt,item,n,pre)
{
         var charCode = (evt.which) ? evt.which : event.keyCode
         // allow "." for one time 
         if(charCode==46)
         {                
                if(item.value.indexOf(".")<0)	return (item.value.length>0)?true:false;
                else return false;
         }
         if (!(charCode > 31 && (charCode < 48 || charCode > 57)))
         {
                        // to avoid over flow
                if(item.value.indexOf(".")<0)
                {
                        //alert("Length without . ="+item.value.length); 
                        return (item.value.length<n)?true:false;
                }
                // dont allow more than 2 precision no's after the point
                if(item.value.indexOf(".")>0)
                {
                        //alert("precision count ="+item.value.split(".")[1].length);
                        if(item.value.split(".")[1].length<pre) return true;
                        else return false;
                }
                return false;
         }else
         {
                return false;
         }
}
function valid_amt(field)
{
         var amt=field.value;
         if(amt.indexOf(".")!=amt.lastIndexOf("."))
         {
                alert("Enter a Valid Amount");
                field.value="";
                field.focus();
         }
         if(amt < 0 ) 
         {
                alert("Negative Amount Not Allowed");
                field.value="";
                field.focus();    
         }
        return true;
}
///////////to load the bank details using the Receipt system/////////////////////////////////////////
var winAcc_Bank_No;     // Fteching Account Head No and Bank  No

function MainAccNopopup()
{
	    Bank_popup_flag=true;
	    if (winAcc_Bank_No && winAcc_Bank_No.open && !winAcc_Bank_No.closed) 
	    {
			        winAcc_Bank_No.resizeTo(500,500);
			        winAcc_Bank_No.moveTo(250,250); 
			        winAcc_Bank_No.focus();
	    }
	    else
	    {
	        	    winAcc_Bank_No=null;
	    }
	    //var Office_code=document.getElementById("cmbOffice_code").value;  
	    var txtModule_Type="MF031";
	    var cr_dr_indi="CR";
	    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	    var txtSub_Office_code=0;
	    winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi+"&txtSub_Office_code="+txtSub_Office_code,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
	    winAcc_Bank_No.moveTo(250,250);  
	    winAcc_Bank_No.focus();
}
function doParentAcc_NO(Acc_Head_Code,Bank_Acc_No,bankid,br_id,B_name)
{
        document.getElementById("txtCash_Acc_code").value=Acc_Head_Code;
        document.getElementById("txtBankAccountNo").value=Bank_Acc_No;
        document.getElementById("txtBankID").value=bankid;
        document.getElementById("txtBranchID").value=br_id;
        document.getElementById("txtBankBran_Name").value=B_name;
}
////////////////button on submit check null field///////////////////////////////////////////////////////////////
function nullcheck()
{
	if(document.getElementById("cmbcheqmemotype").value==0)
	{
		alert('Please Select Cheque Memo Type');
		return false;
	}
	if(document.getElementById("txtcheqmemo_date").value=="")
	{
		alert('Please Enter Check Memo Date');
		return false;
	}
	if(document.getElementById("txtvoucher_date").value=="")
	{
		alert('Please Enter Voucher Date');
		return false;
	}
	if(document.getElementById("txtCash_Acc_code").value=="")
	{
		alert('Please Enter Operation A/C Code');
		return false;
	}
	if(document.getElementById("txtpayee_code_Empcode1").value=="")
	{
		alert('Please Select Payee Type');
		return false;
	}
	if(document.getElementById("cmbpayeetype").value==0)
	{
		alert('Please Enter Payee Type');
		return false;
	}
        if(document.getElementById("txtpayee_code_Empcode1").value=="")
	{
		alert('Please Enter Payee Code');
		return false;
	}
	if(document.getElementById("txtcheq_no").value=="")
	{
		alert('Please Enter Cheque Number');
		return false;
	}
	if(document.getElementById("txtcheque_date").value=="")
	{
		alert('Please Enter Cheque Date');
		return false;
	}
	if(document.getElementById("txtCheque_Amount").value=="")
	{
		alert('Please Enter Cheque Amount');
		return false;
	}
	var tbody=document.getElementById("grid_body");
	if(tbody.rows.length==0)
	{
	    alert("Please Enter Details Part");
	  	    return false; 
	}
        /*if(tbody.rows.length>0)
        {
            alert("select the check box");
            return false;
        }
        */
return true;	
}
/////////////Loading the details part from the fas_journal_master and fas_journal_trn tablees/////////////////
function calldetailsgrid()
{
              //alert("loading the grid values from the tables");
              var acc_unitid=document.getElementById("cmbAcc_UnitCode").value;
              var acc_offid=document.getElementById("cmbOffice_code").value;
              var CB_year=document.getElementById("txtCB_Year").value;
              var CB_month=document.getElementById("txtCB_Month").value;
              var subled_type=document.getElementById("cmbMas_SL_type").value;
              //alert(subled_type);
              var subled_code=document.getElementById("cmbMas_SL_Code").value;
              //alert(subled_type);
              var url="";
              url="../../../../../Cheqmemo_liabjournal?command=loadDetails&acc_unitid1="+acc_unitid+"&acc_offid1="+acc_offid+
                    "&CB_year1="+CB_year+"&CB_month1="+CB_month+"&subled_type1="+subled_type+"&subled_code1="+subled_code;
              //alert(url);
              var req=getTransport();
              req.open("GET",url,true);        
              req.onreadystatechange=function()
              {
                       processResponse(req);
              }   
              req.send(null);
}
/////////////////CLEARING THE FORM FIELDS BY CLICKING THE CANCEL BUTTON//////////////////////////////////
function call_clr()
    {
            //alert("callling clear");
            document.getElementById("cmbAcc_UnitCode").value=0;
            document.getElementById("cmbOffice_code").value=0;
            document.getElementById("txtCB_Year").value="";
            document.getElementById("txtCB_Month").value=0;
            document.getElementById("cmbcheqmemotype").value=0;
            document.getElementById("txtcheqmemo_date").value="";
            
            document.forms[0].cmbpayeetype.value=0;
           
            var payeetype=document.getElementById("cmbpayeetype");
            var child=payeetype.childNodes;
            for(var i=child.length-1;i>1;i--)
            {
                    payeetype.removeChild(child[i]);
            } 
            
            document.forms[0].txtvoucher_date.value="";
            document.forms[0].txtCash_Acc_code.value="";
            document.forms[0].txtBankAccountNo.value="";
            document.forms[0].txtBankBran_Name.value=""; 
            document.forms[0].txtpayee_code_Empdesc.value=""; 
            document.forms[0].txtpayee_code_Empcode1.value=""; 
            document.forms[0].txtcheq_no.value=""; 
            document.forms[0].txtcheque_date.value=""; 
            document.forms[0].txtCheque_Amount.value=""; 
            document.forms[0].txtParticulars.value="";
            
            var tbody=document.getElementById("grid_body");
	    var t=0;
	    for(t=tbody.rows.length-1;t>=0;t--)
	    {
	    	  tbody.deleteRow(0);
	    }
            //alert("all fields r created");
    }
function clrForm()
{
	   if(window.confirm("Do you want to clear ALL fields ?"))
	   {
		   	  call_clr();
	   }
}
function loadingGrid(baseResponse)
{
 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=="success")
     {
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
        var voucher_no=baseResponse.getElementsByTagName("voucher_no");
        
        var items=new Array();
        
        for(var k=0;k<voucher_no.length;k++)
        {
             items[0]=baseResponse.getElementsByTagName("voucher_no")[k].firstChild.nodeValue;   
             items[1]=baseResponse.getElementsByTagName("voucher_date")[k].firstChild.nodeValue;   
             items[2]=baseResponse.getElementsByTagName("acchead_code")[k].firstChild.nodeValue;
             //alert(items[2]);
             items[3]=baseResponse.getElementsByTagName("acchead_desc")[k].firstChild.nodeValue;
             items[9]=items[2]+" - "+items[3];
             //items[10]=baseResponse.getElementsByTagName("sl_no")[k].firstChild.nodeValue;
             items[4]=baseResponse.getElementsByTagName("subledger_typecode")[k].firstChild.nodeValue;
             items[5]=baseResponse.getElementsByTagName("subledger_code")[k].firstChild.nodeValue;
             items[6]=baseResponse.getElementsByTagName("cr_dr")[k].firstChild.nodeValue;
             items[7]=baseResponse.getElementsByTagName("amount")[k].firstChild.nodeValue;
             items[8]=baseResponse.getElementsByTagName("particulars")[k].firstChild.nodeValue;
             if(items[8]=="null")
              items[8]="";
             tbody=document.getElementById("grid_body");
                var mycurrent_row=document.createElement("TR");
                var cell2;
                /** Displaying Check Box For Cancellation */
                     
                        var cell=document.createElement("TD");
                        cell.align="CENTER";
                        var anc=document.createElement("input");
                        anc.type="checkbox";
                        anc.value=items[0]+","+items[1]+","+items[2]+","+items[4]+","+items[5]+","+
                                   items[6]+","+items[7]+","+items[8];
                        anc.id="cheqmemo_select";
                        anc.name="cheqmemo_select";
                        //alert(anc.value);
                        cell.appendChild(anc);
                        mycurrent_row.appendChild(cell);                         

                        
                     /** Displaying Agreement Number   
                
                      cell2=document.createElement("TD");
        
                          var Serial_no=document.createElement("input");
                          Serial_no.type="hidden";
                          Serial_no.name="Serial_no";
                          Serial_no.value=items[10];
                          cell2.appendChild(Serial_no);
                          var currentText=document.createTextNode(items[10]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);*/
                          
                     cell2=document.createElement("TD");
        
                          var Voucher_No=document.createElement("input");
                          Voucher_No.type="hidden";
                          Voucher_No.name="Voucher_No";
                          Voucher_No.value=items[0];
                          cell2.appendChild(Voucher_No);
                          var currentText=document.createTextNode(items[0]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                           
                     /** Displaying Agreement Date */      
                           
                     cell2=document.createElement("TD");
                          var Voucher_Date=document.createElement("input");
                          Voucher_Date.type="hidden";
                          Voucher_Date.name="Voucher_Date";
                          Voucher_Date.value=items[1];
                          cell2.appendChild(Voucher_Date); 
                          var currentText=document.createTextNode(items[1]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                        
                     /** Displaying NameofWork */ 
                        
                     cell2=document.createElement("TD");
                          var Acchead_Code=document.createElement("input");
                          Acchead_Code.type="hidden";
                          Acchead_Code.name="Acchead_Code";
                          Acchead_Code.value=items[2];
                          cell2.appendChild(Acchead_Code);                       
                          var currentText=document.createTextNode(items[9]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                          
                     
                     /** Displaying ValueofWork */ 
                     
                     cell2=document.createElement("TD");
                        var Subledger_typecode=document.createElement("input");
                        Subledger_typecode.type="hidden";
                        Subledger_typecode.name="Subledger_typecode";
                        Subledger_typecode.value=items[4];
                        cell2.appendChild(Subledger_typecode);
                        var currentText=document.createTextNode(items[4]);
                        cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                      
                      /** Displaying WorkOrderNo */ 
                     
                     cell2=document.createElement("TD");
                        var Subledger_code=document.createElement("input");
                        Subledger_code.type="hidden";
                        Subledger_code.name="Subledger_code";
                        Subledger_code.value=items[5];
                        cell2.appendChild(Subledger_code);
                        var currentText=document.createTextNode(items[5]);
                        cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                        
                        /** Displaying WorkOrderDate */ 
                     
                     cell2=document.createElement("TD");
                        var cr_dr=document.createElement("input");
                        cr_dr.type="hidden";
                        cr_dr.name="cr_dr";
                        cr_dr.value=items[6];
                        cell2.appendChild(cr_dr);
                        var currentText=document.createTextNode(items[6]);
                        cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                        
                        /** Displaying remarks */ 
                     
                     cell2=document.createElement("TD");
                        var Amount=document.createElement("input");
                        cell2.align="RIGHT";
                        Amount.type="hidden";
                        Amount.name="Amount";
                        Amount.value=items[7];
                        cell2.appendChild(Amount);
                        var currentText=document.createTextNode(items[7]);
                        cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                        
                    cell2=document.createElement("TD");
                        var Particulars=document.createElement("input");
                        Particulars.type="hidden";
                        Particulars.name="Particulars";
                        Particulars.value=items[8];
                        cell2.appendChild(Particulars);
                        var currentText=document.createTextNode(items[8]);
                        cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);

              tbody.appendChild(mycurrent_row);
        }
     }
    else if(flag=="failure")
     {
        //alert("No Record found for Verification");
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
     }
     else if(flag=="nodata")
     {
        alert("No Record found");
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
     }
}
function cheqexist()
{
    //alert("checking whether the cheque already exixt or not");
    var acc_unitid=document.getElementById("cmbAcc_UnitCode").value;
    var acc_offid=document.getElementById("cmbOffice_code").value;
    var CB_year=document.getElementById("txtCB_Year").value;
    var CB_month=document.getElementById("txtCB_Month").value;
    var cheqmemo_type=document.getElementById("cmbcheqmemotype").value;
    var cheqno=document.getElementById("txtcheq_no").value;
    var url="";
              url="../../../../../Cheqmemo_liabjournal?command=cheqcheck&acc_unitid1="+acc_unitid+"&acc_offid1="+acc_offid+
                    "&CB_year1="+CB_year+"&CB_month1="+CB_month+"&cheqmemo_type1="+cheqmemo_type+"&cheqno="+cheqno;
              //alert(url);
              var req=getTransport();
              req.open("GET",url,true);        
              req.onreadystatechange=function()
              {
                       processResponse(req);
              }   
              req.send(null);
}
function checkcheque(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=="success")
     {
        alert("The cheue number is already exist");
        document.getElementById("txtcheq_no").value="";
        document.getElementById("txtcheq_no").focus();
     }
     else if(flag=="nodata")
     {
        //alert("No duplicate Cheque no available");
        document.getElementById("txtCheque_Amount").focus();
     }
}
//value of paid to............
function loadName_Mas(value)
{
    if(document.getElementById("cmbMas_SL_Code").value=="")
    return;
    value=document.getElementById("cmbMas_SL_Code").options[document.getElementById("cmbMas_SL_Code").selectedIndex].text; 
    s=document.getElementById("cmbMas_SL_type").value;
    if(s=="7" )
    value=value.substring(0,value.indexOf("-"));
    
    document.getElementById("txtPaid_to").value=value;
    //document.getElementById("txtPay_Vou_No").value="";
    //document.getElementById("txtPay_Vou_date").value="";
    //document.getElementById("txtJournal_code").value="";
}

function ADD_GRID() {
	
	var tbody = document.getElementById("grid_body");
	var t = 0;
	var items = new Array();
	
	var tbody2=document.getElementById("grid_body");
	var leng=tbody2.rows.length;
	var i;
	var flag=0;
	
	
	if(document.getElementById("billno").value=="")
	{
	alert('Please Select Bill Number');
	return false;
	}
	
	
	for(i=0;i<leng;i++)
	{
	billno=document.getElementById("grid_body").rows[i].cells.item(1).lastChild.nodevalue;

		if(billno==document.getElementById("billno").value)
		{
			alert("This Bill No " +billno +" Already Entered");
			flag=1;
			return false;
		}
	}
	
	
	if(document.getElementById("txtAcc_HeadCode").value=="")
	{
	alert('Please Enter Account Head Code');
	return false;
	}
	if(document.getElementById("dramount").value=="")
	{
	alert('Please Enter DR Amount');
	return false;
	}
	
	if(flag==0){
	items[0] = document.getElementById("billno").value;	
	items[1] = document.getElementById("billdate").value;
	items[2] = document.getElementById("passorderamount").value;
	items[3] = document.getElementById("chequeno").value+"-"+document.getElementById("chequedate").value;
	//items[4] = document.getElementById("chequedate").value;
	//items[4] = document.getElementById("chequeamount").value;	
	//items[6] = document.getElementById("cmbSL_type").value;	
	//items[7] = document.getElementById("cmbSL_Code").value;	
	
	  items[5]=document.getElementById("txtAcc_HeadCode").value;
      items[6]=document.getElementById("txtAcc_HeadDesc").value;
	
      items[7]=document.getElementById("cmbSL_type").value;
      
      if(document.getElementById("cmbSL_type").value=="")
      {
      //items[4]="Not Available";
      items[8]="";                //document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
      }
      else
      items[8]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
      
      items[9]=document.getElementById("cmbSL_Code").value;
      if(document.getElementById("cmbSL_Code").value=="")
      {
      items[10]="";                //"Not Available";
      }
      else
      items[10]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
      
      items[11] = document.getElementById("dramount").value;	
  	items[12] = document.getElementById("remarks").value; 
   
  	if(document.cheque_memo.Indi_CR_DR[0].checked == true)
	{
  		items[13]='CR';
	}else{
		items[13]='DR';
	}
  	
  	
  
	
  	
	
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
	var billno=document.createElement("input");
	billno.type="hidden";
	billno.name="bill_no";
	//billno.id="invoice_no";
	billno.value=items[0];
    cell2.appendChild(billno);
	var currentText = document.createTextNode(items[0]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var billdate=document.createElement("input");
	billdate.type="hidden";
	billdate.name="bill_date";
	billdate.value=items[1];
    cell2.appendChild(billdate);
	var currentText = document.createTextNode(items[1]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var passamont=document.createElement("input");
	passamont.type="hidden";
	passamont.name="pass_amount";
	passamont.value=items[2];
    cell2.appendChild(passamont);
	var currentText = document.createTextNode(items[2]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	

	cell2 = document.createElement("TD");
	var headcode=document.createElement("input");
	headcode.type="hidden";
	headcode.name="head_code";
	headcode.value=items[5];
    cell2.appendChild(headcode);
	var currentText = document.createTextNode(items[5]+"-"+items[6]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var sltype=document.createElement("input");
	sltype.type="hidden";
	sltype.name="sl_type";
	sltype.value=items[7];
    cell2.appendChild(sltype);
	var currentText = document.createTextNode(items[8]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);
	
	cell2 = document.createElement("TD");
	var slcode=document.createElement("input");
	slcode.type="hidden";
	slcode.name="sl_code";
	slcode.value=items[9];
    cell2.appendChild(slcode);
	var currentText = document.createTextNode(items[10]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);
	
	cell2 = document.createElement("TD");
	var dramount=document.createElement("input");
	dramount.type="hidden";
	dramount.name="crdrindicator";
	dramount.value=items[13];
    cell2.appendChild(dramount);
	var currentText = document.createTextNode(items[13]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);
	
	
	cell2 = document.createElement("TD");
	var dramount=document.createElement("input");
	dramount.type="hidden";
	dramount.name="dr_amount";
	dramount.value=items[11];
    cell2.appendChild(dramount);
	var currentText = document.createTextNode(items[11]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);
	
		
	cell2 = document.createElement("TD");
	var remark=document.createElement("input");
	remark.type="hidden";
	remark.name="remarks12";
	remark.value=items[12];
    cell2.appendChild(remark);
	var currentText = document.createTextNode(items[12]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);
	
	cell2 = document.createElement("TD");
	var cheque_no_date=document.createElement("input");
	cheque_no_date.type="hidden";
	cheque_no_date.name="cheque_no_dates";
	cheque_no_date.value=items[3];
    cell2.appendChild(cheque_no_date);
	var currentText = document.createTextNode(items[3]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);
	
	tbody.appendChild(mycurrent_row);
	clear();
	}
}