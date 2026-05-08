var BillMajorCode;
var bill_minr_code;
var acc_unit_id;
var acc_unit_off_id;
var desig_sel_code;
var desig_sel_desc;
var seq=0;
var pagesize = 10;
var response="";
var com_id;var emp_flag;
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
 alert("Request object created:::::"+req);
}
/////////////////////////////////code to check textarea//////////////////////////////////////////////////////
function check_leng(param,val)
{	 
		if((val.length)>=190)
		{
			  if(param=='remarks')			  
				  	   alert("Please Enter Remarks below 200 characters");			           			  
			  else			  
				  	   alert("Please Enter Paticulars below 200 characters");				  	  
			  
		}
		
}
///////////////////////////////exit mathod////////////////////////////////////////////////////////////
function exitmethod()
{
      window.close();
}
///////////////////////////////////////////////////////////////////////////////////////////////////////
function call_clr(){
            document.formsanc_proceed_new.txtbill_majr_code.selectedIndex=0;
            document.formsanc_proceed_new.txtbill_minr_code.selectedIndex=0;
            document.formsanc_proceed_new.txtbill_sub_code.selectedIndex=0;
            document.formsanc_proceed_new.txtbill_minr_code.length=1;
            document.formsanc_proceed_new.txtbill_sub_code.length=1;
            document.formsanc_proceed_new.rad_payment_type[0].checked=true;
            document.formsanc_proceed_new.rad_payee_type[0].checked=true;
            document.getElementById("txt_payee_code").value="";
    	    document.getElementById("txtpayee_namedesig").value="";
    	    document.getElementById("txtRefNo").value="";
            document.getElementById("txtRefDate").value="";
            document.getElementById("txt_sancpro_no").value="";
            document.getElementById("txt_sancpro_date").value=""; 
            document.getElementById("cmb_sanc_auth").selectedIndex=0;
            document.getElementById("txtsanc_by").value=""; 
            document.getElementById("txtname_desig").value="";
            //document.getElementById("txt_office").value="";
            document.getElementById("txtAcc_HeadCode").value="";
            document.getElementById("txtAcc_HeadDesc").value="";
            document.getElementById("txt_Budget_Provided").value="";
            document.getElementById("txt_Budget_sofar_spent").value="";
            document.getElementById("txt_bal_amt_after_bill").value="";
            document.getElementById("budget_Provided").value="";
            document.getElementById("budget_sofar_spent").value="";
            document.getElementById("balance").value="";
            document.getElementById("txtMade").value="";
            document.getElementById("accunitMade").value="";
            document.formsanc_proceed_new.rad_recovery[0].checked=true;
            document.getElementById("txt_tot_instalments").value="";  
            document.getElementById("txt_EMI").value="";   
            document.getElementById("txt_start_month").value="";   
            document.getElementById("txt_resi_amt").value="";   
            document.getElementById("txt_resi_deduction_No").value="";
            document.getElementById("txt_tot_sanctionamt").value="";
            document.getElementById("txt_GeneralRemarks").value="";
            document.getElementById("txt_tot_instalments").disabled=false;
    		document.getElementById("txt_EMI").disabled=false;
    		document.getElementById("txt_start_month").disabled=false;
    		document.getElementById("txt_resi_amt").disabled=false;
    		document.getElementById("txt_resi_deduction_No").disabled=false;
    		document.getElementById('butSub').disabled=false;
    	 	document.getElementById('butUpdate').disabled=true;
    	 	document.getElementById('butDelete').disabled=true;
    	 	document.getElementById('cmbAcc_UnitCode').disabled=false;
    	 	document.getElementById('cmbOffice_code').disabled=false;
//	   var tbody=document.getElementById("grid_body");
//	   var t=0;
//	   for(t=tbody.rows.length-1;t>=0;t--)
//	   {
//	    	  tbody.deleteRow(0);
//	   }
}
function clrForm()
{
	   if(window.confirm("Do you want to clear ALL fields ?"))
	   {
		   	  call_clr();
	   }
}
function LoadInvNo()
{
        BillMajorCode=document.getElementById("txtbill_majr_code").value;  
        bill_minr_code=document.getElementById("txtbill_minr_code").value;
        bill_sub_code=document.getElementById("txtbill_sub_code").value;
        //alert("bills r chosen");
        var url="../../../../../sanction_proceed_masterNew?command=loadInvoiceNumber&MajorCode1="+BillMajorCode+"&MinorCode1="+bill_minr_code+"&SubCode1="+bill_sub_code;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
                processResponse1(req);
        };   
        req.send(null);
        //alert("Inside Invoice number");
}
function LoadInvDetails()
{
        var invoice_no=document.getElementById("cmb_invoice_no").value;
        var url="../../../../../sanction_proceed_masterNew?command=loadInvoiceDetails&invoice_no="+invoice_no;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
                processResponse1(req);
        }   
        req.send(null);
}
////////////////////////////////////Loading Bill Major Minor Sub Types/////////////////////////////////////////////////////
function LoadBill_Majortype()
{
        var url="../../../../../sanction_proceed_masterNew?command=loadMajorType";
        //alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
                handleResponse1(req);
        };   
        req.send(null);
}


function loadMinorType()
{
        var BillMajorCode=document.getElementById("txtbill_majr_code").value;       
        var bill_minr_code=document.getElementById("txtbill_minr_code");
        var child=bill_minr_code.childNodes;
        for(var i=child.length-1;i>1;i--)
        {
                bill_minr_code.removeChild(child[i]);
        } 
        var url="../../../../../sanction_proceed_masterNew?command=loadMinorType&MajorCode1="+BillMajorCode;
       var req=getTransport();
       req.open("GET",url,true); 
       req.onreadystatechange=function()
       {
                handleResponse1(req);
       }   
       req.send(null);
}

function loadSubType()
{
        BillMajorCode=document.getElementById("txtbill_majr_code").value;       
        bill_minr_code=document.getElementById("txtbill_minr_code").value;
        //alert(bill_minr_code);
        var bill_sub_code=document.getElementById("txtbill_sub_code");
        var child=bill_sub_code.childNodes;
        for(var i=child.length-1;i>1;i--)
        {
                bill_sub_code.removeChild(child[i]);
        } 
        var url="../../../../../sanction_proceed_masterNew?command=loadSubType&MajorCode1="+BillMajorCode+"&MinorCode1="+bill_minr_code;
       var req=getTransport();
       req.open("GET",url,true); 
       req.onreadystatechange=function()
       {
                handleResponse1(req);
       }   
       req.send(null);
}

function handleResponse1(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
                var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                var tagcommand=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(tagcommand=="loadMajorType")
                {
                    if(flag=="success")
                    {
                        var option=baseResponse.getElementsByTagName("option");
                        var BillMajorCode=document.getElementById("txtbill_majr_code");
                        for(var i=0;i<option.length;i++)
                         {
                            var code=option[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                            var desc=option[i].getElementsByTagName("desc")[0].firstChild.nodeValue;
                            //alert(code+"   "+desc);
                            var opt=document.createElement("option");
                            opt.setAttribute("value",code);
                            var opttext=document.createTextNode(desc);
                            opt.appendChild(opttext);
                            BillMajorCode.appendChild(opt);
                         }
                    }
                    else if(flag=="nodata")
                    {
                        alert("No records to load Bill Major Type ");
                    }
                    else
                    {
                        alert("Failed to load records");
                    }
                }
                else if(tagcommand=="loadMinorType")
                {
                    if(flag=="success"){
                        var option=baseResponse.getElementsByTagName("option");
                        var BillMinorCode=document.getElementById("txtbill_minr_code");
                        var child=BillMinorCode.childNodes;
                        for(var i=child.length-1;i>1;i--){
                                BillMinorCode.removeChild(child[i]);
                        }                        
                        for(var i=0;i<option.length;i++){
                            var code=option[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                            var desc=option[i].getElementsByTagName("desc")[0].firstChild.nodeValue;
                            //alert(code+"   "+desc);
                            var opt=document.createElement("option");
                            opt.setAttribute("value",code);
                            var opttext=document.createTextNode(desc);
                            opt.appendChild(opttext);
                            BillMinorCode.appendChild(opt);
                         }
                    }
                    else if(flag=="nodata"){
                        alert("No records to load Bill Minor type");
                        var selectdiv=document.getElementById('txtbill_minr_code');
            			var listOpt=document.createElement("option");
            			selectdiv.length=0;            			
            			listOpt.text="select";
            			listOpt.value="0";
            			selectdiv.appendChild(listOpt);
                    }
                    else
                    {
                        alert("Failed to load records");
                    }
                }
                else if(tagcommand=="loadSubType")
                {                	
                    if(flag=="success"){
                    	var option=baseResponse.getElementsByTagName("option");
                        var BillSubCode=document.getElementById("txtbill_sub_code");
                        var child=BillSubCode.childNodes;
                        for(var i=child.length-1;i>1;i--){
                                BillSubCode.removeChild(child[i]);
                        }                        
                        for(var i=0;i<option.length;i++){
                            var code=option[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                            var desc=option[i].getElementsByTagName("desc")[0].firstChild.nodeValue;
                            //alert(code+"   "+desc);
                            var opt=document.createElement("option");
                            opt.setAttribute("value",code);
                            var opttext=document.createTextNode(desc);
                            opt.appendChild(opttext);
                            BillSubCode.appendChild(opt);
                         }
                    }
                    else if(flag=="nodata"){
                        alert("No records to load the Bill Sub type");
                        var selectdiv=document.getElementById('txtbill_sub_code');
            			var listOpt=document.createElement("option");
            			selectdiv.length=0;            			
            			listOpt.text="select";
            			listOpt.value="0";
            			selectdiv.appendChild(listOpt);
                    }
                    else
                    {
                        alert("Failed to load records");
                    }
                }
                  
        }
    }    
}

/*****************************Loading Employee Code,Name,Designation,Office Details*****************************************/
/////////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
function emp_payee_code()
{
        emp_flag=1;    
        Load_emp_details();
}
function emp_popup_payee()
{
        emp_flag=1;    
        servicepopup();
}
function emp_sanc_by()
{
        emp_flag=2;  
        Load_emp_details();
}
function emp_popup_sancBy()
{
        emp_flag=2;    
        servicepopup();
}
function emp_payableTo()
{
        emp_flag=3;  
}
function emp_popup_payableTo()
{
        emp_flag=3;    
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
        winemp=null;
    }
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}
function doParentEmp(emp)
{
        if(emp_flag==1)
        {
                document.formsanc_proceed_new.txt_payee_code.value=emp;
                Load_emp_details();
        }
        else if(emp_flag==2)
        {
                document.formsanc_proceed_new.txtsanc_by.value=emp;
                Load_emp_details();
        }
        else if(emp_flag==3)
        {
                alert("load");
                document.formsanc_proceed_new.txt_Payable_to.value=emp;
        }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function Load_emp_details()
{
        //alert("inside loadempdetails");
       // alert(emp_flag);
        if(emp_flag==1)
        {
                var emp_id=document.getElementById("txt_payee_code").value;
                //alert(emp_id);
        }
        else if(emp_flag==2)
        {
                var emp_id=document.getElementById("txtsanc_by").value;
                //alert(emp_id);
        }

        if(document.formsanc_proceed_new.rad_payee_type[0].checked)
        {
             var url="";
             url="../../../../../phone_master_servlet?command=loadempdetails&emp_id="+emp_id;
//            alert(url);
             var req=getTransport();
              req.open("GET",url,true);        
              req.onreadystatechange=function()
              {
                       processResponse1(req);
              }   
              req.send(null);
        }
        else if(document.formsanc_proceed_new.rad_payee_type[1].checked)
        {
            alert("No data found to Load for Privileged Users");
            document.getElementById("txt_payee_code").value="";
            document.getElementById("txtpayee_namedesig").value="";
        }
        else if(document.formsanc_proceed_new.rad_payee_type[2].checked)
        {
            alert("No data found to Load for Pensioners");
            document.getElementById("txt_payee_code").value="";
            document.getElementById("txtpayee_namedesig").value="";
        }
}
function processResponse1(req)
{   
      if(req.readyState==4)   // Completed
      {
          if(req.status==200)   // No error
          {   
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              if(command=="loadempdetails")
              {
                    LoadEmpDetails(baseResponse);
              }
              else if(command=="loadpaymenttype") 
              {
                    LoadPaytype(baseResponse);
              }
               else if(command=="loadsanctionauth") 
              {
                    LoadDesignation(baseResponse);
              }
              else if(command=="Loadsanctioned_by") 
              {
                    LoadDesigdetails(baseResponse);
              }
              else if(command=="Loadsanctiondetails") 
              {
                    LoadSancDetails(baseResponse);
              }
              else if(command=="loadInvoiceDetails") 
              {
                    LoadInvoiceDetails(baseResponse);
              }
              else if(command=="loadInvoiceNumber")
              {
                    LoadInvoiceNumber(baseResponse);
              }
              else if(command=="budgetDetails")
              {
                    budgetDetailsview(baseResponse);
              }
              else if(command=="delete")
              {
            	  var tagFlag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	  if(tagFlag=="success"){
            		  alert("Record Cancelled successfully");
            	  }else{
            		  alert("Record not cancel");
            	  }
            	  call_clr();
              }
              else if(command=="update")
              {
            	  var tagFlag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	  if(tagFlag=="success"){
            		  alert("Record updated successfully");
            	  }else{
            		  alert("Record not updated");
            	  }
            	  call_clr();
              }
        }    
    }
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
                                document.getElementById("txtpayee_namedesig").value=emp_name+"      "+desig_name;
                         }
                         else if(emp_flag==2)
                         {
                                document.getElementById("txtname_desig").value=emp_name+"      "+desig_name;
                                document.getElementById("txt_office").value=office_name;
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
	                return false; 
	          }
         }
}  
///////////////////////////Load payment type for Employee/Preveliged Users/////////////////////////////////
function loadpayment_type()
{
                var url="";
                url="../../../../../sanction_proceed_masterNew?command=loadpaymenttype&MajorCode1="+BillMajorCode+"&MinorCode1="+bill_minr_code;
               
                var req=getTransport();
                req.open("GET",url,true);        
                req.onreadystatechange=function()
                {
                    processResponse1(req);
                }   
                req.send(null);
}
function LoadPaytype(baseResponse)
{
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                  
                  if(flag=="success")
                  {                       
                         var adv_app=baseResponse.getElementsByTagName("advance_applicable")[0].firstChild.nodeValue;
                         if(adv_app=="Y")
                                document.getElementById("txt_payment_type").value="Advance";
                          else
                                document.getElementById("txt_payment_type").value="Regular";
                }
                else if(flag=="nodata")
                {
                        alert("Invalid Payment type");
                }
                else
                {
                        alert("Failed to load");
                }
}
/////////////////////////////////////////Sanction Authority Loading///////////////////////////////////////////////////////////////////////////////////
function Loadsanction_auth()
{
                var url="";
                url="../../../../../sanction_proceed_masterNew?command=loadsanctionauth";
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true);        
                req.onreadystatechange=function()
                {
                    processResponse1(req);
                };   
                req.send(null);
}
function LoadDesignation(baseResponse)
{
                        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                        //alert(flag);
                        if(flag=="success")
                        {  
                            var option=baseResponse.getElementsByTagName("option");
                            var SancAuth=document.getElementById("cmb_sanc_auth");
                            var child=SancAuth.childNodes;
                            for(var i=child.length-1;i>1;i--)
                            {
                                    SancAuth.removeChild(child[i]);
                            } 
                            for(var i=0;i<option.length;i++)
                             {
                                var code=option[i].getElementsByTagName("desig_id")[0].firstChild.nodeValue;
                                var desc=option[i].getElementsByTagName("desig_name")[0].firstChild.nodeValue;
                                //alert(code+"   "+desc);
                                var opt=document.createElement("option");
                                opt.setAttribute("value",code);
                                var opttext=document.createTextNode(desc);
                                opt.appendChild(opttext);
                                SancAuth.appendChild(opt);
                             }
                        }
                        else if(flag=="nodata")
                        {
                                alert("Invalid Sanction Authority");
                        }
                        else
                        {
                                alert("Failed to load relevant data");
                        }
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function Loadsanctioned_by()
{
                acc_unit_id=document.getElementById("cmbAcc_UnitCode").value;
                acc_unit_off_id=document.getElementById("cmbOffice_code").value;
                //alert(acc_unit_off_id);
                desig_sel_code=document.getElementById("cmb_sanc_auth").value;
                desig_sel_desc=document.getElementById("cmb_sanc_auth");
                //alert(desig_sel_code);
                //alert(desig_sel_desc);
                var url="";
                url="../../../../../sanction_proceed_masterNew?command=Loadsanctioned_by&acc_unit_id="+acc_unit_id+"&acc_unit_off_id="+acc_unit_off_id
                +"&desig_sel_code="+desig_sel_code+"&desig_sel_desc="+desig_sel_desc;
                var req=getTransport();
                req.open("GET",url,true);        
                req.onreadystatechange=function()
                {
                    processResponse1(req);
                };   
                req.send(null);
}
function LoadDesigdetails(baseResponse){
                        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                        if(flag=="success")
                        {  
                            var option=baseResponse.getElementsByTagName("option");
                            var SancBy=document.getElementById("cmb_sanc_by");
                            var child=SancBy.childNodes;
                            for(var i=child.length-1;i>1;i--)
                            {
                                    SancBy.removeChild(child[i]);
                            } 
                            for(var i=0;i<option.length;i++)
                             {
                                var code=option[i].getElementsByTagName("employee_id")[0].firstChild.nodeValue;
                                //alert(code+"   "+desc);
                                var opt=document.createElement("option");
                                opt.setAttribute("value",code);
                                var opttext=document.createTextNode(code);
                                opt.appendChild(opttext);
                                SancBy.appendChild(opt);
                             }
                        }
                        else if(flag=="nodata")
                        {
                                alert("Invalid Sanctioned_By");
                        }
                        else
                        {
                                alert("Failed to load relevant data");
                        }
                        
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function loadsanctiondetails()
{
                acc_unit_id=document.getElementById("cmbAcc_UnitCode").value;
                acc_unit_off_id=document.getElementById("cmbOffice_code").value;
               emp_code_sel=document.getElementById("cmb_sanc_by").value;
                var url="";
                url="../../../../../sanction_proceed_masterNew?command=Loadsanctiondetails&acc_unit_id="+acc_unit_id+"&acc_unit_off_id="+acc_unit_off_id
                +"&emp_code_sel="+emp_code_sel;
                var req=getTransport();
                req.open("GET",url,true);        
                req.onreadystatechange=function()
                {
                    processResponse1(req);
                };   
                req.send(null);
}
function LoadSancDetails(baseResponse)
{
                        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                        if(flag=="success")
                        {  
                                var emp_name=baseResponse.getElementsByTagName("empl_name")[0].firstChild.nodeValue;
                                var office_name=baseResponse.getElementsByTagName("office_name")[0].firstChild.nodeValue;
                                var desig_name=baseResponse.getElementsByTagName("desig_name")[0].firstChild.nodeValue;
                                document.getElementById("txtname_desig").value=emp_name+"      "+desig_name;
                                document.getElementById("txt_office").value=office_name;
                        }
                        else if(flag=="nodata")
                        {
                                alert("Invalid Sanctioned_By");
                        }
                        else
                        {
                                alert("Failed to load relevant data");
                        }
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function callSancAuthList()
{
                    Loadsanction_auth();
                    loadAccunit();
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function LoadInvoiceDetails(baseResponse)
{
                    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                    if(flag=="success")
                    {                       
                                var invoice_date=baseResponse.getElementsByTagName("invoice_date")[0].firstChild.nodeValue;
                                var invoice_amount=baseResponse.getElementsByTagName("invoice_amount")[0].firstChild.nodeValue;
                                var invoice_particulars=baseResponse.getElementsByTagName("invoice_particulars")[0].firstChild.nodeValue;
                                var invoice_headaccount=baseResponse.getElementsByTagName("invoice_headaccount")[0].firstChild.nodeValue;
                                var invoice_headdesc=baseResponse.getElementsByTagName("invoice_head_desc")[0].firstChild.nodeValue;
                                document.getElementById("txt_invoice_date").value=invoice_date;
                                document.getElementById("txt_particulars").value=invoice_particulars;
                                document.getElementById("txt_invoice_amt").value=invoice_amount;
                                document.getElementById("txtAcc_HeadCode").value=invoice_headaccount;
                                document.getElementById("txtAcc_HeadDesc").value=invoice_headdesc;
                    }
                    else if(flag=="nodata")
                    {
                            alert("Invalid Invoice Details");
                    }
                    else
                    {
                        alert("Failed to load");
                    }
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function LoadInvoiceNumber(baseResponse)
{
                            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                            if(flag=="success")
                            {  
                                    var option=baseResponse.getElementsByTagName("option");
                                    var InvoiceNumber=document.getElementById("cmb_invoice_no");
                                    var child=InvoiceNumber.childNodes;
                                    for(var i=child.length-1;i>1;i--)
                                    {
                                            InvoiceNumber.removeChild(child[i]);
                                    } 
                                    for(var i=0;i<option.length;i++)
                                     {
                                            var code=option[i].getElementsByTagName("invoice_no")[0].firstChild.nodeValue;
                                            //alert(code+"   "+desc);
                                            var opt=document.createElement("option");
                                            opt.setAttribute("value",code);
                                            var opttext=document.createTextNode(code);
                                            opt.appendChild(opttext);
                                            InvoiceNumber.appendChild(opt);
                                     }
                            }  
                            else if(flag=="nodata")
                            {
                                    alert("Invalid Invoice Details");
                            }
                            else
                            {
                                alert("Failed to load");
                            }
}
/************************************************************************************************************************************************/
function calcnet_amt()
{
        document.getElementById("txt_net_amt").value=document.getElementById("txt_sanctioned_amt").value-document.getElementById("txt_deducted_amt").value;
}
function calbugetspent()
{
        document.getElementById("txt_amt_deducted_bill").value=document.getElementById("txt_Budget_Provided").value-document.getElementById("txt_Budget_sofar_spent").value;
}
function calcbal_amt()
{
        document.getElementById("txt_bal_amt_after_bill").value=document.getElementById("txt_Budget_Provided").value-document.getElementById("txt_Budget_sofar_spent").value;
}
function caltot_sancamt()
{
    document.getElementById("txt_tot_sanctionamt").value=parseFloat(document.getElementById("txt_EMI").value)+parseFloat(document.getElementById("txt_tot_instalments").value)+parseFloat(document.getElementById("txt_resi_amt").value);
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function nullfieldcheck(){
                   if(document.getElementById("txtbill_majr_code").value=="0")
                   {
                            alert("Select the Bill major type");
                            return false;
                   }  
                   
                  else if(document.getElementById("txtbill_minr_code").value=="0")
                   {
                        alert("Enter the Bill minor type");  
                        return false;        
                   }  
                 else if(document.getElementById("txtbill_sub_code").value=="0")
                   {
                        alert("Enter the Deducted Amount");
                        return false;
                   }
                  else if(document.getElementById("txt_payee_code").value=="")
                   {
                             alert("Enter the Pay code");
                             return false;
                   }
                  else if(document.getElementById("txtRefNo").value=="")
                  {
                            alert("Enter the Ref No");
                            return false;
                  }
                  else if(document.getElementById("txtRefDate").value=="")
                  {
                            alert("Enter the Ref date");
                            return false;
                  }
                  else if(document.getElementById("txt_sancpro_date").value=="")
                  {
                            alert("Enter the Sanction date");
                            return false;
                  }
                  else if(document.getElementById("cmb_sanc_auth").value=="")
                  {
                            alert("Enter the Sanction Authority");
                            return false;
                  }
                  else if(document.getElementById("txtsanc_by").value=="")
                  {
                            alert("Enter the Sanctioned by");
                            return false;
                  }
                  else if(document.getElementById("txtAcc_HeadCode").value=="")
                  {
                            alert("Enter the Account Head Code");
                            return false;
                  }
                 else if(document.getElementById("txt_Budget_Provided").value=="")
                   {
                                alert("Enter the Budget Provided Amount");
                                return false;    
                   }
                  else if(document.getElementById("txt_Budget_sofar_spent").value=="")
                   {
                                alert("Enter the Budget so far amount");
                                return false;    
                   }
                   else if(document.getElementById("txt_tot_instalments").value=="")
                   {
                                alert("Enter the total Installment Amount");
                                return false;    
                   }
                   else if(document.getElementById("txt_EMI").value=="")
                   {
                                alert("Enter the EMI");
                                return false;    
                   }
                   else if(document.getElementById("txt_start_month").value=="")
                   {
                                alert("Enter the Recovery Start Month");
                                return false;    
                   }
                   else if(document.getElementById("txt_resi_amt").value=="")
                   {
                                alert("Enter the Residual Amount");
                                return false;    
                   }
                   else if(document.getElementById("txt_resi_deduction_No").value=="")
                   {
                                alert("Enter the Residual Amount Deduction Instalment No");
                                return false;    
                   }
                   else if(document.getElementById("txt_tot_sanctionamt").value=="")
                   {
                                alert("Enter the Total Sanction Amount");
                                return false;    
                   }
                   return true;
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function checkfields()
{
                    var tot_sanc_amt=0;
                        rows=tbody.getElementsByTagName("tr");
                        for(t=0;t<rows.length;t++)
                        {
	                        var cells=rows[t].cells;	                         
                                var net_amt=cells.item(7).firstChild.value;
                                tot_sanc_amt=parseFloat(tot_sanc_amt) + parseFloat(net_amt);	                	                
                                //alert("after value added    :"+tot_sanc_amt);
                        }                            
                        document.getElementById("txt_sanc_amt").value=tot_sanc_amt;
                        return true;
              
                //return true;
}
function datefun(fromdt,todt)
{
        var frm=fromdt.split('/');
        var to=todt.split('/');
        
        var fday=frm[0];
        var fmon=frm[1];
        var fyear=frm[2];
        
        var tday=to[0];
        var tmon=to[1];
        var tyear=to[2];
        
       if(fyear>tyear)
        {
            alert('Sanction date should be greater than Sanction Proceeding date');
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('Sanction date should be greater than Sanction Proceeding  date');
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('Sanction date should be greater than Sanction Proceeding date');
                            return false;
                        }
                        
                }
        }
}
function datefun1(fromdt,todt)
{
        var frm=fromdt.split('/');
        var to=todt.split('/');
        
        var fday=frm[0];
        var fmon=frm[1];
        var fyear=frm[2];
        
        var tday=to[0];
        var tmon=to[1];
        var tyear=to[2];
        
       if(fyear>tyear)
        {
            alert('Sanction Proceeding date should be greater than Invoice date');
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('Sanction Proceeding date should be greater than Invoice date');
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('Sanction Proceeding date should be greater than Invoice date');
                            return false;
                        }
                        
                }
        }
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    
	     if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
	     {
		    	 
	    	
	    		 var dt=document.imprest_account.txtCrea_date.value;
				 var dat=dt.split("/");		
				 var monthArray =new Array("January", "February", "March", 
		                "April", "May", "June", "July", "August",
		                "September", "October", "November", "December");
				 document.getElementById("txtCB_Year").value=dat[2]; 		
		         document.getElementById("txtCB_Month").value=monthArray[dat[1]-1];
		         call_clr();
	             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
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
	     //call_clr();
	     if(checkdt(dateCtrl))
	     {
		         var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		         var cmbOffice_code=document.getElementById("cmbOffice_code").value;
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
			                    //doFunction('load_Voucher_No','null');                 //return true;
			            }
			            else if(flag=="failure")
				        {
				                dateCtrl.value="";
				                alert("Trial Balance Closed");//return false;//
				                dateCtrl.focus();
				                // document.getElementById("txtVoucher_No").value="";
				        }
			            else if(flag=="finyear")
			            {
			                    // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
				                dateCtrl.value="";
				                alert("Cash Book Control Not Found ");//return false;//
				                dateCtrl.focus();
				                //document.getElementById("txtVoucher_No").value="";     
			            }
		        }
		 }
}
recoveryFrom=function(){	
	if(document.formsanc_proceed_new.rad_recovery[0].checked==true){
		document.getElementById("txt_tot_instalments").disabled=false;
		document.getElementById("txt_EMI").disabled=false;
		document.getElementById("txt_start_month").disabled=false;
		document.getElementById("txt_resi_amt").disabled=false;
		document.getElementById("txt_resi_deduction_No").disabled=false;
	}else{
		//alert("disable");
		document.getElementById("txt_tot_instalments").disabled=true;
		document.getElementById("txt_EMI").disabled=true;
		document.getElementById("txt_start_month").disabled=true;
		document.getElementById("txt_resi_amt").disabled=true;
		document.getElementById("txt_resi_deduction_No").disabled=true;
		document.getElementById("txt_tot_instalments").vaue="";
		document.getElementById("txt_EMI").vaue="";
		document.getElementById("txt_start_month").vaue="";
		document.getElementById("txt_resi_amt").vaue="";
		document.getElementById("txt_resi_deduction_No").vaue="";
	}	
};
loadAccunit=function(){
	document.getElementById('txtMade').value=document.getElementById('cmbAcc_UnitCode').value;
	document.getElementById('accunitMade').value=document.getElementById('cmbAcc_UnitCode').value;
};
function myFunction(Command,param){
	var addtional_field_value;
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
        if(Command=="checkCode"){  
             var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
             document.getElementById("txtAcc_HeadDesc").value="";
            try{
             Sub_Ledger_Mandatory(txtAcc_HeadCode);             
           }catch(e){
             alert(e.description);
           }
           if(txtAcc_HeadCode.length>=6){
                var url="../../../../../Receipt_SL.view?Command=checkCode&txtAcc_HeadCode="+txtAcc_HeadCode;                
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                };   
                req.send(null);
            }         
        }
}
function handleResponse(req)
{  
    if(req.readyState==4)
    { 
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="checkCode")
            {
                loadcheckCode(baseResponse);
            }            
        }
    }
}
function loadcheckCode(baseResponse){
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success"){
         var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
         document.getElementById("txtAcc_HeadCode").value=hid;
         var hdesc=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
         var SL_YN =baseResponse.getElementsByTagName("SL_YN")[0].firstChild.nodeValue;
         var sl_man = baseResponse.getElementsByTagName("sl_man")[0].firstChild.nodeValue;
         document.getElementById("txtAcc_HeadCode").value=hid;
         document.getElementById("txtAcc_HeadDesc").value=hdesc;
         budgetDetails();
    }else if(flag=="failure"){
         alert("Account Head Code '"+document.getElementById("txtAcc_HeadCode").value+"' doesn't Exist");
         document.getElementById("txtAcc_HeadCode").value="";
         document.getElementById("txtAcc_HeadCode").focus();
     }
}
function budgetDetails(){
	var accUnit=document.getElementById('cmbAcc_UnitCode').value;
	var accHead=document.getElementById('txtAcc_HeadCode').value;
	var finaceYear=document.getElementById('fin_yr').value;
	var url="../../../../../sanction_proceed_masterNew?command=budgetDetails&accUnit="+accUnit+"&accHead="+accHead+"&finaceYear="+finaceYear;
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
            processResponse1(req);
    };   
    req.send(null);
}
function budgetDetailsview(baseResponse){
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
      if(flag=="success"){  
        document.getElementById('txt_Budget_Provided').value=baseResponse.getElementsByTagName("BUDGET_PROVIDE")[0].firstChild.nodeValue;
        document.getElementById('txt_Budget_sofar_spent').value=baseResponse.getElementsByTagName("BUDGET_SPENT")[0].firstChild.nodeValue;
        document.getElementById('txt_bal_amt_after_bill').value=baseResponse.getElementsByTagName("BALANCE")[0].firstChild.nodeValue;
        document.getElementById('budget_Provided').value=baseResponse.getElementsByTagName("BUDGET_PROVIDE")[0].firstChild.nodeValue;
        document.getElementById('budget_sofar_spent').value=baseResponse.getElementsByTagName("BUDGET_SPENT")[0].firstChild.nodeValue;
        document.getElementById('balance').value=baseResponse.getElementsByTagName("BALANCE")[0].firstChild.nodeValue;
      }else if(flag=="nodata"){
             alert("Budget not provided for this Account Head Code");
             document.getElementById('txt_Budget_Provided').value="";
             document.getElementById('txt_Budget_sofar_spent').value="";
             document.getElementById('txt_bal_amt_after_bill').value="";
             document.getElementById('budget_Provided').value="";
             document.getElementById('budget_sofar_spent').value="";
             document.getElementById('balance').value="";
      }
                 
}
function listPopup(){
    winemp= window.open("sanction_proceed_masterNewList.jsp","list","width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}
function initialLoad(){
	var url="../../../../../sanction_proceed_masterNew?command=sanctionList";
    var req=getTransport();
    req.open("GET",url,true);                
    req.onreadystatechange=function(){
    	viewResponse(req);
    };
    req.send(null);
}
function viewResponse(req){
	 if(req.readyState==4){ 
        if(req.status==200){
       	response=req.responseXML.getElementsByTagName("response")[0];        	
          	changepagesize();			
           statusflag=true;
        }
    }
}
var browserName=navigator.appName;
var brow="";
if (browserName=="Netscape")
{ 	brow="nets";}
else if (browserName=="Microsoft Internet Explorer")
{ 	brow="iex";} 
function changepagesize() {	
	pagesize = document.getElementById("cmbpagination").value;
	var len = response.getElementsByTagName("SANCTION_PROC_NO").length;	
	var cmbpage = document.getElementById("cmbpage");
	try {	
		cmbpage.innerHTML = "";
	} catch (e) {
		cmbpage.innerText = "";
	}	
	var i = 1;
	for (i = 1; i <= ((len / pagesize) + 1); i++) {
		var option = document.createElement("OPTION");
		option.text = i;
		option.value = i;
		try {
			cmbpage.add(option);
		} catch (errorObject) {
			cmbpage.add(option, null);
		}
	}
	changepage();
	
}
function changepage() {
	var tlist = document.getElementById("tblList");
	try {
		tlist.innerHTML = "";
	} catch (e) {
		tlist.innerText = "";
	}
	var len = response.getElementsByTagName("SANCTION_PROC_NO").length;	
	if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="success")
		{
	var pageno = document.getElementById("cmbpage").value;
	var ul = 0, ll = 0;
	ul = pageno * pagesize;
	ll = ul - pagesize;
	try{		
	for ( var i = ll; i < ul; i++) {
		var sancNo = response.getElementsByTagName("SANCTION_PROC_NO")[i].firstChild.nodeValue;
		var sancDate = response.getElementsByTagName("SANCTION_PROC_DATE")[i].firstChild.nodeValue;
		var payType = response.getElementsByTagName("PAYEE_TYPE")[i].firstChild.nodeValue;
		var payCode= response.getElementsByTagName("PAYEE_CODE")[i].firstChild.nodeValue;
		var sanctionBy= response.getElementsByTagName("SANCTION_BY")[i].firstChild.nodeValue;
		var accHead= response.getElementsByTagName("ACCOUNT_HEAD_CODE")[i].firstChild.nodeValue;
		var budgetPro= response.getElementsByTagName("BUDGET_PROVIDED")[i].firstChild.nodeValue;
		var budgetSo= response.getElementsByTagName("BUDGET_SOFAR_SPENT")[i].firstChild.nodeValue;
		var balanceAmt= response.getElementsByTagName("BALANCE_AMOUNT")[i].firstChild.nodeValue;
		var remarks=response.getElementsByTagName("REMARKS")[i].firstChild.nodeValue;
		var view=response.getElementsByTagName("STATUSTYPE")[i].firstChild.nodeValue;
		var tr = document.createElement("TR");
		tr.id = seq;
		var td = document.createElement("TD");
		var anc = document.createElement("A");
		if (view == "C") {
			var priceSpan = document.createElement("span");
			priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
			priceSpan.appendChild(document.createTextNode("Cancel"));			
			td.appendChild(priceSpan);
			tr.appendChild(td);
		}else{
			var url = "javascript:loadValuesFromTable('" + seq + "')";
			anc.href = url;
			var edit = document.createTextNode("Edit");
			anc.appendChild(edit);
			td.appendChild(anc);
			var sch_id=document.createElement("TEXT");
        	sch_id.type="hidden";
        	sch_id.name="name"+seq;
        	sch_id.id="id"+seq;
        	sch_id.value="&sancNo="+sancNo;	       
        	td.appendChild(sch_id);
			tr.appendChild(td);			
		}		
		var td1 = document.createElement("TD");
		var tides = document.createTextNode(sancNo);
		td1.appendChild(tides);
		tr.appendChild(td1);
		var td2 = document.createElement("TD");
		var tid = document.createTextNode(sancDate);
		td2.appendChild(tid);
		tr.appendChild(td2);
		var td4 = document.createElement("TD");
		var tid1 = document.createTextNode(payType);
		td4.appendChild(tid1);
		tr.appendChild(td4);
		var td6 = document.createElement("TD");
		var tid2 = document.createTextNode(payCode);
		td6.appendChild(tid2);
		tr.appendChild(td6);
		var td7 = document.createElement("TD");
		var tid3 = document.createTextNode(sanctionBy);
		td7.appendChild(tid3);
		tr.appendChild(td7);
		var td8 = document.createElement("TD");
		var tid4 = document.createTextNode(accHead);
		td8.appendChild(tid4);
		tr.appendChild(td8);
		var td9 = document.createElement("TD");
		var tid5 = document.createTextNode(budgetPro);
		td9.appendChild(tid5);
		tr.appendChild(td9);
		var td10 = document.createElement("TD");
		var tid6 = document.createTextNode(budgetSo);
		td10.appendChild(tid6);
		tr.appendChild(td10);
		var td11= document.createElement("TD");
		var tid7= document.createTextNode(balanceAmt);
		td11.appendChild(tid7);
		tr.appendChild(td11);
		var td3 = document.createElement("TD");
		var tides = document.createTextNode(remarks);
		td3.appendChild(tides);
		tr.appendChild(td3);		
		var td5 = document.createElement("TD");
		if(view=="C"){
			var tdst = document.createTextNode("CANCEL");
		}else{
			var tdst = document.createTextNode("LIVE");
		}
		td5.appendChild(tdst);
		tr.appendChild(td5);
		if(brow=="iex"){
			var vartab = tlist.insertRow(-1);			
			vartab.appendChild(td);			
			vartab.appendChild(td1);
			vartab.appendChild(td2);
			vartab.appendChild(td4);
			vartab.appendChild(td6);
			vartab.appendChild(td7);
			vartab.appendChild(td8);
			vartab.appendChild(td9);
			vartab.appendChild(td10);
			vartab.appendChild(td11);						
			vartab.appendChild(td3);
			vartab.appendChild(td5);
		}else
		{
			tlist.appendChild(tr);
		}		
		seq++;
	}
	}catch(err){		
	}
	}
	else{
		 var iframe=document.getElementById("tblList");
         iframe.focus();
		 if(navigator.appName.indexOf('Microsoft')!=-1){
             iframe.innerHTML="<tr><td align=center colspan=12>There is No Data to Display</td></tr>";             
		 } else{
			 iframe.innerText="There is No Data to Display";
	         iframe.innerHTML="<tr><td align=center colspan=12>There is No Data to Display</td></tr>";
		 }
             
	}				

}
function viewDetails(id){
	 var jid=id;	 	 
	 var url="../../../../../sanction_proceed_masterNew?command=edit"+jid;
	 var req=getTransport();
     req.open("GET",url,true);        
     req.onreadystatechange=function()
     {
        editview(req);
     };  
     req.send(null);
}
function editview(req){
	 if(req.readyState==4){ 
        if(req.status==200){        	 
       	 var baseResponse=req.responseXML.getElementsByTagName("response")[0];
     	   	 editResponse(baseResponse);
        }
    } 
}
function editResponse(response){
	 var res=response.getElementsByTagName("status")[0].firstChild.nodeValue;
	 	if(res=="success"){
	 		document.getElementById("cmbAcc_UnitCode").value=response.getElementsByTagName("ACCOUNTING_UNIT_ID")[0].firstChild.nodeValue;
	 		document.getElementById("cmbOffice_code").value=response.getElementsByTagName("ACCOUNTING_UNIT_OFFICE_ID")[0].firstChild.nodeValue;
	 		if(response.getElementsByTagName("PAYMENT_TYPE")[0].firstChild.nodeValue=="R"){
	 			document.formsanc_proceed_new.rad_payment_type[0].checked=true;
	 		}else if(response.getElementsByTagName("PAYMENT_TYPE")[0].firstChild.nodeValue=="A"){
	 			document.formsanc_proceed_new.rad_payment_type[1].checked=true;
	 		}	 
	 		document.getElementById('txtbill_majr_code').value=response.getElementsByTagName("BILL_MAJOR_TYPE_CODE")[0].firstChild.nodeValue;
            if(response.getElementsByTagName("PAYEE_TYPE")[0].firstChild.nodeValue=="E"){
            	document.formsanc_proceed_new.rad_payee_type[0].checked=true;
            }else if(response.getElementsByTagName("PAYEE_TYPE")[0].firstChild.nodeValue=="U"){
            	document.formsanc_proceed_new.rad_payee_type[1].checked=true;
            }else if(response.getElementsByTagName("PAYEE_TYPE")[0].firstChild.nodeValue=="P"){
            	document.formsanc_proceed_new.rad_payee_type[2].checked=true;
            }
            document.getElementById("txt_payee_code").value=response.getElementsByTagName("PAYEE_CODE")[0].firstChild.nodeValue;
            document.getElementById("txt_sancpro_date").value=response.getElementsByTagName("SANCTION_PROC_DATE")[0].firstChild.nodeValue;    	    
    	    document.getElementById("txtRefNo").value=response.getElementsByTagName("REF_NO")[0].firstChild.nodeValue;
            document.getElementById("txtRefDate").value=response.getElementsByTagName("REF_DATE")[0].firstChild.nodeValue;
            document.getElementById("txt_sancpro_no").value=response.getElementsByTagName("SANCTION_PROC_NO")[0].firstChild.nodeValue;            
            document.getElementById("cmb_sanc_auth").selectedIndex=response.getElementsByTagName("SANCTION_AUTHORITY")[0].firstChild.nodeValue;
            document.getElementById("txtsanc_by").value=response.getElementsByTagName("SANCTION_BY")[0].firstChild.nodeValue;
            document.getElementById("txtAcc_HeadCode").value=response.getElementsByTagName("ACCOUNT_HEAD_CODE")[0].firstChild.nodeValue;;
            document.getElementById("txt_Budget_Provided").value=response.getElementsByTagName("BUDGET_PROVIDED")[0].firstChild.nodeValue;
            document.getElementById("txt_Budget_sofar_spent").value=response.getElementsByTagName("BUDGET_SOFAR_SPENT")[0].firstChild.nodeValue;
            document.getElementById("txt_bal_amt_after_bill").value=response.getElementsByTagName("BALANCE_AMOUNT")[0].firstChild.nodeValue;
            document.getElementById("txtMade").value=response.getElementsByTagName("TRF_ACCOUNTING_UNIT")[0].firstChild.nodeValue;
            if(response.getElementsByTagName("RECOVERY_FROM")[0].firstChild.nodeValue=="Y"){
            	document.formsanc_proceed_new.rad_recovery[0].checked=true;
            }else if(response.getElementsByTagName("RECOVERY_FROM")[0].firstChild.nodeValue=="N"){
            	document.formsanc_proceed_new.rad_recovery[1].checked=true;
            }
            document.getElementById("txt_tot_instalments").value=response.getElementsByTagName("TOTAL_INSTALMENTS")[0].firstChild.nodeValue;
            document.getElementById("txt_EMI").value=response.getElementsByTagName("EMI")[0].firstChild.nodeValue;
            document.getElementById("txt_start_month").value=response.getElementsByTagName("EMI_START_MONTH")[0].firstChild.nodeValue;
            document.getElementById("txt_resi_amt").value=response.getElementsByTagName("RESIDUAL_AMOUNT")[0].firstChild.nodeValue;
            document.getElementById("txt_resi_deduction_No").value=response.getElementsByTagName("RESIDUAL_NUMBER")[0].firstChild.nodeValue;
            document.getElementById("txt_tot_sanctionamt").value=response.getElementsByTagName("TOTAL_SANCTIONED_AMOUNT")[0].firstChild.nodeValue;
            document.getElementById("txt_GeneralRemarks").value=response.getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
            var selectdiv=document.getElementById('txtbill_minr_code');
			var listOpt=document.createElement("option");
			selectdiv.length=0;
			selectdiv.appendChild(listOpt);
			listOpt.text=response.getElementsByTagName("BILL_MINOR_TYPE_DESC")[0].firstChild.nodeValue;
			listOpt.value=response.getElementsByTagName("BILL_MINOR_TYPE_CODE")[0].firstChild.nodeValue;
			var selectdiv1=document.getElementById('txtbill_sub_code');
			var listOpt1=document.createElement("option");
			selectdiv1.length=0;
			selectdiv1.appendChild(listOpt1);
			listOpt1.text=response.getElementsByTagName("BILL_SUB_TYPE_DESC")[0].firstChild.nodeValue;
			listOpt1.value=response.getElementsByTagName("BILL_SUB_TYPE_CODE")[0].firstChild.nodeValue;
			document.getElementById('txtbill_minr_code').value=response.getElementsByTagName("BILL_MINOR_TYPE_CODE")[0].firstChild.nodeValue;
			document.getElementById('txtbill_sub_code').value=response.getElementsByTagName("BILL_SUB_TYPE_CODE")[0].firstChild.nodeValue;
	 	}else{
	 		alert("Process Failure");
	 	}
	 	document.getElementById('butSub').disabled=true;
	 	document.getElementById('butUpdate').disabled=false;
	 	document.getElementById('butDelete').disabled=false;
	 	document.getElementById('cmbAcc_UnitCode').disabled=true;
	 	document.getElementById('cmbOffice_code').disabled=true;
}
function loadValuesFromTable(id){
	var jid=document.getElementById("id"+id).value;	
      Minimize();
      opener.viewDetails(jid);
}

function Minimize(){
window.close();
opener.window.focus();
}
function callServer(cmd){
	if(cmd=="update"){
		var check=nullfieldcheck();
		var unitCode=document.getElementById('cmbAcc_UnitCode').value;
		var officeCode=document.getElementById('cmbOffice_code').value;
		var sancNo=document.getElementById('txt_sancpro_no').value;
		var billMajor=document.getElementById("txtbill_majr_code").value;
		var billMinor=document.getElementById("txtbill_minr_code").value;
		var billSub=document.getElementById("txtbill_sub_code").value;
		var payment="";
        if(document.formsanc_proceed_new.rad_payment_type[0].checked==true){
        	payment=document.formsanc_proceed_new.rad_payment_type[0].value;
        }else if(document.formsanc_proceed_new.rad_payment_type[1].checked==true){
        	payment=document.formsanc_proceed_new.rad_payment_type[1].value;
        }
        var pay="";
        if(document.formsanc_proceed_new.rad_payee_type[0].checked==true){
        	pay=document.formsanc_proceed_new.rad_payee_type[0].value;
        }else if(document.formsanc_proceed_new.rad_payee_type[1].checked==true){
        	pay=document.formsanc_proceed_new.rad_payee_type[1].value;
        }else{
        	pay=document.formsanc_proceed_new.rad_payee_type[2].value;
        }
        var payee_code=document.getElementById("txt_payee_code").value;	    
	    var refNo=document.getElementById("txtRefNo").value;
        var refDate=document.getElementById("txtRefDate").value;
        var sancpro_date=document.getElementById("txt_sancpro_date").value; 
        var sanc_auth=document.getElementById("cmb_sanc_auth").value;
        var sanc_by=document.getElementById("txtsanc_by").value;        
        var acc_HeadCode=document.getElementById("txtAcc_HeadCode").value;        
        var budget_Provided=document.getElementById("txt_Budget_Provided").value;
        var budget_sofar_spent=document.getElementById("txt_Budget_sofar_spent").value;
        var bal_amt=document.getElementById("txt_bal_amt_after_bill").value;
        var txtMade=document.getElementById("txtMade").value;
        var recovery="";
        if(document.formsanc_proceed_new.rad_recovery[0].checked==true){
        	recovery=document.formsanc_proceed_new.rad_recovery[0].value;
        }else{
        	recovery=document.formsanc_proceed_new.rad_recovery[1].value;
        }
        var tot_instalments=document.getElementById("txt_tot_instalments").value;  
        var txt_EMI=document.getElementById("txt_EMI").value;   
        var start_month=document.getElementById("txt_start_month").value;   
        var resi_amt=document.getElementById("txt_resi_amt").value;   
        var deduction_No=document.getElementById("txt_resi_deduction_No").value;
        var tot_sanctionamt=document.getElementById("txt_tot_sanctionamt").value;
        var remarks=document.getElementById("txt_GeneralRemarks").value;  
		var url="../../../../../sanction_proceed_masterNew?command=update&unitCode="+unitCode
				+"&officeCode="+officeCode+"&sancNo="+sancNo+"&billMajor="+billMajor
				+"&billMinor="+billMinor+"&billSub="+billSub+"&payment="+payment
				+"&pay="+pay+"&payee_code="+payee_code+"&refNo="+refNo+"&refDate="+refDate
				+"&sancpro_date="+sancpro_date+"&sanc_auth="+sanc_auth+"&sanc_by="+sanc_by
				+"&acc_HeadCode="+acc_HeadCode+"&budget_Provided="+budget_Provided
				+"&budget_sofar_spent="+budget_sofar_spent+"&bal_amt="+bal_amt+"&txtMade="+txtMade
				+"&recovery="+recovery+"&tot_instalments="+tot_instalments+"&txt_EMI="+txt_EMI
				+"&start_month="+start_month+"&resi_amt="+resi_amt+"&deduction_No="+deduction_No
				+"&tot_sanctionamt="+tot_sanctionamt+"&remarks="+remarks;
		if(check){
			var req=getTransport();
		     req.open("GET",url,true);        
		     req.onreadystatechange=function(){
		    	 processResponse1(req);
		     };  
		     req.send(null);
		}		 
	}else if(cmd=="delete"){
		var unitCode=document.getElementById('cmbAcc_UnitCode').value;
		var officeCode=document.getElementById('cmbOffice_code').value;
		var sancNo=document.getElementById('txt_sancpro_no').value;
		alert("sancNo "+sancNo);
		var url="../../../../../sanction_proceed_masterNew?command=delete&unitCode="+unitCode
				+"&officeCode="+officeCode+"&sancNo="+sancNo;
		 var req=getTransport();
	     req.open("GET",url,true);        
	     req.onreadystatechange=function(){
	    	 processResponse1(req);
	     };  
	     req.send(null);
	}
}