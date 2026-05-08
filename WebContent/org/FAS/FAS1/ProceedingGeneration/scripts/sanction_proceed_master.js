var BillMajorCode;
var bill_minr_code;
var acc_unit_id;
var acc_unit_off_id;
var desig_sel_code;
var desig_sel_desc;
var seq=0;
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
function call_clr()
{
            document.formsanc_proceed.txtbill_majr_code.selectedIndex=0;
            document.formsanc_proceed.txtbill_minr_code.selectedIndex=0;
            document.formsanc_proceed.txtbill_sub_code.selectedIndex=0;
            document.formsanc_proceed.rad_payee_type[0].checked=true;

           document.getElementById("txt_payment_type").value="";
           document.getElementById("txt_payee_code").value="";
	   document.getElementById("txtpayee_namedesig").value="";
	   
           document.formsanc_proceed.cmb_sanc_auth.selectedIndex=0;
           document.formsanc_proceed.txtsanc_by.value="";
	   document.getElementById("txtname_desig").value="";
	   document.getElementById("txt_office").value="";
	   document.getElementById("txt_sancpro_no").value="";
	   document.getElementById("txt_sancpro_date").value=""; 
	   document.getElementById("txt_sanc_date").value="";
	   document.getElementById("txt_sanc_amt").value="";
	   document.getElementById("txt_GeneralRemarks").value="";
           
	   var tbody=document.getElementById("grid_body");
	   var t=0;
	   for(t=tbody.rows.length-1;t>=0;t--)
	   {
	    	  tbody.deleteRow(0);
	   }
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
        var url="../../../../../sanction_proceed_master?command=loadInvoiceNumber&MajorCode1="+BillMajorCode+"&MinorCode1="+bill_minr_code+"&SubCode1="+bill_sub_code;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
                processResponse1(req);
        }   
        req.send(null);
        //alert("Inside Invoice number");
}
function LoadInvDetails()
{
        var invoice_no=document.getElementById("cmb_invoice_no").value;
        var url="../../../../../sanction_proceed_master?command=loadInvoiceDetails&invoice_no="+invoice_no;
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
        var url="../../../../../sanction_proceed_master?command=loadMajorType";
        //alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
                handleResponse1(req);
        }   
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
        var url="../../../../../sanction_proceed_master?command=loadMinorType&MajorCode1="+BillMajorCode;
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
        var url="../../../../../sanction_proceed_master?command=loadSubType&MajorCode1="+BillMajorCode+"&MinorCode1="+bill_minr_code;
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
                    if(flag=="success")
                    {
                        var option=baseResponse.getElementsByTagName("option");
                        var BillMinorCode=document.getElementById("txtbill_minr_code");
                        var child=BillMinorCode.childNodes;
                        for(var i=child.length-1;i>1;i--)
                        {
                                BillMinorCode.removeChild(child[i]);
                        } 
                        for(var i=0;i<option.length;i++)
                         {
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
                    else if(flag=="nodata")
                    {
                        alert("No records to load Bill Minor type");
                    }
                    else
                    {
                        alert("Failed to load records");
                    }
                }
                else if(tagcommand=="loadSubType")
                {
                    if(flag=="success")
                    {
                        var option=baseResponse.getElementsByTagName("option");
                        var BillSubCode=document.getElementById("txtbill_sub_code");
                        var child=BillSubCode.childNodes;
                        for(var i=child.length-1;i>1;i--)
                        {
                                BillSubCode.removeChild(child[i]);
                        } 
                        for(var i=0;i<option.length;i++)
                         {
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
                    else if(flag=="nodata")
                    {
                        alert("No records to load the Bill Sub type");
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
                document.formsanc_proceed.txt_payee_code.value=emp;
                Load_emp_details();
        }
        else if(emp_flag==2)
        {
                document.formsanc_proceed.txtsanc_by.value=emp;
                Load_emp_details();
        }
        else if(emp_flag==3)
        {
                alert("load");
                document.formsanc_proceed.txt_Payable_to.value=emp;
        }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function Load_emp_details()
{
        //alert("inside loadempdetails");
        //alert(emp_flag);
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

        if(document.formsanc_proceed.rad_payee_type[0].checked)
        {
             var url="";
             url="../../../../../phone_master_servlet?command=loadempdetails&emp_id="+emp_id;
             var req=getTransport();
              req.open("GET",url,true);        
              req.onreadystatechange=function()
              {
                       processResponse1(req);
              }   
              req.send(null);
        }
        else if(document.formsanc_proceed.rad_payee_type[1].checked)
        {
            alert("No data found to Load for Privileged Users");
        }
        else if(document.formsanc_proceed.rad_payee_type[2].checked)
        {
            alert("No data found to Load for Pensioners");
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
	                return false 
	          }
         }
}  
///////////////////////////Load payment type for Employee/Preveliged Users/////////////////////////////////
function loadpayment_type()
{
                var url="";
                url="../../../../../sanction_proceed_master?command=loadpaymenttype&MajorCode1="+BillMajorCode+"&MinorCode1="+bill_minr_code;
               
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
                url="../../../../../sanction_proceed_master?command=loadsanctionauth";
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true);        
                req.onreadystatechange=function()
                {
                    processResponse1(req);
                }   
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
                url="../../../../../sanction_proceed_master?command=Loadsanctioned_by&acc_unit_id="+acc_unit_id+"&acc_unit_off_id="+acc_unit_off_id
                +"&desig_sel_code="+desig_sel_code+"&desig_sel_desc="+desig_sel_desc;
                var req=getTransport();
                req.open("GET",url,true);        
                req.onreadystatechange=function()
                {
                    processResponse1(req);
                }   
                req.send(null);
}
function LoadDesigdetails(baseResponse)
{
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
                url="../../../../../sanction_proceed_master?command=Loadsanctiondetails&acc_unit_id="+acc_unit_id+"&acc_unit_off_id="+acc_unit_off_id
                +"&emp_code_sel="+emp_code_sel;
                var req=getTransport();
                req.open("GET",url,true);        
                req.onreadystatechange=function()
                {
                    processResponse1(req);
                }   
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
        document.getElementById("txt_bal_amt_after_bill").value=document.getElementById("txt_amt_deducted_bill").value-(document.getElementById("txt_sanctioned_amt").value-document.getElementById("txt_deducted_amt").value);
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function ADD_GRID()
{
       var flag=nullfieldcheck();
        //alert(flag);
       if(flag)
       {
               var items=new Array();
               items[0]=document.getElementById("cmb_invoice_no").value;
               items[1]=document.getElementById("txt_invoice_date").value;
               items[2]=document.getElementById("txt_particulars").value;
               items[3]=document.getElementById("txt_invoice_amt").value;
               items[4]=document.getElementById("txt_sanctioned_amt").value; 
               items[5]=document.getElementById("txt_deducted_amt").value;   
               items[6]=document.getElementById("txt_net_amt").value;
               items[7]=document.getElementById("txtAcc_HeadCode").value;
               items[8]=document.getElementById("txtAcc_HeadDesc").value;
               items[9]=document.getElementById("txt_Payable_to").value;
               items[10]=document.getElementById("txt_Budget_Provided").value;
               items[11]=document.getElementById("txt_Budget_sofar_spent").value;
               items[12]=document.getElementById("txt_amt_deducted_bill").value;
               items[13]=document.getElementById("txt_bal_amt_after_bill").value;
               items[14]=document.getElementById("txt_DetailsRemarks").value;
               
            var tbody=document.getElementById("grid_body");
            var t=0;
            var mycurrent_row=document.createElement("TR");
            seq=seq+1;
            mycurrent_row.id=seq;
            
            var cell=document.createElement("TD");
            var anc=document.createElement("A");
            var url="javascript:loadTable('"+mycurrent_row.id+"')";
            anc.href=url;
            var txtedit=document.createTextNode("EDIT");
            anc.appendChild(txtedit);
            cell.appendChild(anc);
            mycurrent_row.appendChild(cell);
            var i=0;
            var cell2;
            
                cell2=document.createElement("TD");
                
                var H_seqno=document.createElement("input");
                H_seqno.type="hidden";
                H_seqno.name="H_seqno";
                H_seqno.value=tbody.rows.length+1;
                cell2.appendChild(H_seqno);
                
                cell2=document.createElement("TD");
                
                var H_Invoiceno=document.createElement("input");
                H_Invoiceno.type="hidden";
                H_Invoiceno.name="H_Invoiceno";
                H_Invoiceno.value=items[0];
                cell2.appendChild(H_Invoiceno);
                var currentText=document.createTextNode(items[0]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                        
                cell2=document.createElement("TD"); 
                
                var H_Invoice_Date=document.createElement("input");
                H_Invoice_Date.type="hidden";
                H_Invoice_Date.name="H_Invoice_Date";
                H_Invoice_Date.value=items[1];
                cell2.appendChild(H_Invoice_Date);
                var currentText=document.createTextNode(items[1]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                             
                cell2=document.createElement("TD");
               
                var H_Particulars=document.createElement("input");
                H_Particulars.type="hidden";
                H_Particulars.name="H_Particulars";
                H_Particulars.value=items[2];
                cell2.appendChild(H_Particulars);
                var currentText=document.createTextNode(items[2]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD");
    
                var H_Invoice_amt=document.createElement("input");
                H_Invoice_amt.type="hidden";
                H_Invoice_amt.name="H_Invoice_amt";
                H_Invoice_amt.value=items[3];
                cell2.appendChild(H_Invoice_amt);
                var currentText=document.createTextNode(items[3]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                    
                cell2=document.createElement("TD");
                
                var H_Sanctioned_amt=document.createElement("input");
                H_Sanctioned_amt.type="hidden";
                H_Sanctioned_amt.name="H_Sanctioned_amt";
                H_Sanctioned_amt.value=items[4];
                cell2.appendChild(H_Sanctioned_amt);
                var currentText=document.createTextNode(items[4]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD");
                
                var H_Deducted_amt=document.createElement("input");
                H_Deducted_amt.type="hidden";
                H_Deducted_amt.name="H_Deducted_amt";
                H_Deducted_amt.value=items[5];
                cell2.appendChild(H_Deducted_amt);
                var currentText=document.createTextNode(items[5]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                                                   
                cell2=document.createElement("TD");
               
                var H_Net_amt=document.createElement("input");
                H_Net_amt.type="hidden";
                H_Net_amt.name="H_Net_amt";
                H_Net_amt.value=items[6];
                cell2.appendChild(H_Net_amt);
                var currentText=document.createTextNode(items[6]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD");
               
                var H_Acc_Head=document.createElement("input");
                H_Acc_Head.type="hidden";
                H_Acc_Head.name="H_Acc_Head";
                H_Acc_Head.value=items[7];
                cell2.appendChild(H_Acc_Head);
                var currentText=document.createTextNode(items[7]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD");
               
                var H_Acc_Head_Desc=document.createElement("input");
                H_Acc_Head_Desc.type="hidden";
                H_Acc_Head_Desc.name="H_Acc_Head_Desc";
                H_Acc_Head_Desc.value=items[8];
                cell2.appendChild(H_Acc_Head_Desc);
                var currentText=document.createTextNode(items[8]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD");
               
                var H_Payable_To=document.createElement("input");
                H_Payable_To.type="hidden";
                H_Payable_To.name="H_Payable_To";
                H_Payable_To.value=items[9];
                cell2.appendChild(H_Payable_To);
                var currentText=document.createTextNode(items[9]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);

                cell2=document.createElement("TD");
    
                var H_Budget_Provided=document.createElement("input");
                H_Budget_Provided.type="hidden";
                H_Budget_Provided.name="H_Budget_Provided";
                H_Budget_Provided.value=items[10];
                cell2.appendChild(H_Budget_Provided);
                var currentText=document.createTextNode(items[10]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD");

                var H_Budget_Spent=document.createElement("input");
                H_Budget_Spent.type="hidden";
                H_Budget_Spent.name="H_Budget_Spent";
                H_Budget_Spent.value=items[11];
                cell2.appendChild(H_Budget_Spent);
                var currentText=document.createTextNode(items[11]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD");

                var H_Amt_deduted_Bill=document.createElement("input");
                H_Amt_deduted_Bill.type="hidden";
                H_Amt_deduted_Bill.name="H_Amt_deduted_Bill";
                H_Amt_deduted_Bill.value=items[12];
                cell2.appendChild(H_Amt_deduted_Bill);
                var currentText=document.createTextNode(items[12]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD");

                var H_Balance_amtafterBill=document.createElement("input");
                H_Balance_amtafterBill.type="hidden";
                H_Balance_amtafterBill.name="H_Balance_amtafterBill";
                H_Balance_amtafterBill.value=items[13];
                cell2.appendChild(H_Balance_amtafterBill);
                var currentText=document.createTextNode(items[13]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD");

                var Remarks=document.createElement("input");
                Remarks.type="hidden";
                Remarks.name="Remarks";
                Remarks.value=items[14];
                cell2.appendChild(Remarks);
                var currentText=document.createTextNode(items[14]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
            tbody.appendChild(mycurrent_row);
            clear_main_fields();
        }
        /*else
        {
                alert("error in loding data in grid");
        }*/
}

function clear_main_fields()
{
	   document.getElementById("cmb_invoice_no").value=0;
	   document.getElementById("txt_invoice_date").value="";
	   document.getElementById("txt_particulars").value="";
	   document.getElementById("txt_invoice_amt").value=""; 
	   document.getElementById("txt_sanctioned_amt").value="";
	   document.getElementById("txt_deducted_amt").value="";
	   document.getElementById("txt_net_amt").value="";
	   document.getElementById("txtAcc_HeadCode").value="";
           document.getElementById("txtAcc_HeadDesc").value="";
	   document.getElementById("txt_Payable_to").value="";
	   document.getElementById("txt_Budget_Provided").value="";
	   document.getElementById("txt_Budget_sofar_spent").value="";
	   document.getElementById("txt_amt_deducted_bill").value="";
	   document.getElementById("txt_bal_amt_after_bill").value="";
	   document.getElementById("txt_DetailsRemarks").value="";

            document.formsanc_proceed.cmdadd.style.display='block';
	    document.formsanc_proceed.cmdupdate.style.display='none';
	    document.formsanc_proceed.cmddelete.disabled=true;
}

function update_GRID()
{      
        var items=new Array();
               items[0]=document.getElementById("cmb_invoice_no").value;
               items[1]=document.getElementById("txt_invoice_date").value;
               items[2]=document.getElementById("txt_particulars").value;
               items[3]=document.getElementById("txt_invoice_amt").value;
               items[4]=document.getElementById("txt_sanctioned_amt").value; 
               items[5]=document.getElementById("txt_deducted_amt").value;   
               items[6]=document.getElementById("txt_net_amt").value;
               items[7]=document.getElementById("txtAcc_HeadCode").value;
               items[8]=document.getElementById("txtAcc_HeadDesc").value;
               items[9]=document.getElementById("txt_Payable_to").value;
               items[10]=document.getElementById("txt_Budget_Provided").value;
               items[11]=document.getElementById("txt_Budget_sofar_spent").value;
               items[12]=document.getElementById("txt_amt_deducted_bill").value;
               items[13]=document.getElementById("txt_bal_amt_after_bill").value;
               items[14]=document.getElementById("txt_DetailsRemarks").value; 
        var r=document.getElementById(com_id);
        var rcells=r.cells;
        
        try{rcells.item(1).firstChild.value=items[0];}catch(e){}
        try{rcells.item(1).lastChild.nodeValue=items[0];}catch(e){}
      
        try{rcells.item(2).firstChild.value=items[1];}catch(e){}
        try{rcells.item(2).lastChild.nodeValue=items[1];}catch(e){}
    
        try{rcells.item(3).firstChild.value=items[2];}catch(e){}
        try{rcells.item(3).lastChild.nodeValue=items[2];}catch(e){}
        
        try{rcells.item(4).firstChild.value=items[3];}catch(e){}
        try{rcells.item(4).lastChild.nodeValue=items[3];}catch(e){}
      
        try{rcells.item(5).firstChild.value=items[4];}catch(e){}
        try{rcells.item(5).lastChild.nodeValue=items[4];}catch(e){}
       
        try{rcells.item(6).firstChild.value=items[5];}catch(e){}
        try{rcells.item(6).lastChild.nodeValue=items[5];}catch(e){}
        
        try{rcells.item(7).firstChild.value=items[6];}catch(e){}
        try{rcells.item(7).lastChild.nodeValue=items[6];}catch(e){}
         
        try{rcells.item(8).firstChild.value=items[7];}catch(e){}
        try{rcells.item(8).lastChild.nodeValue=items[7];}catch(e){}
         
        try{rcells.item(9).firstChild.value=items[8];}catch(e){}
        try{rcells.item(9).lastChild.nodeValue=items[8];}catch(e){}
      
        try{rcells.item(10).firstChild.value=items[9];}catch(e){}
        try{rcells.item(10).lastChild.nodeValue=items[9];}catch(e){}
         
        try{rcells.item(11).firstChild.value=items[10];}catch(e){}
        try{rcells.item(11).lastChild.nodeValue=items[10];}catch(e){}
        
        try{rcells.item(12).firstChild.value=items[11];}catch(e){}
        try{rcells.item(12).lastChild.nodeValue=items[11];}catch(e){}
         
        try{rcells.item(13).firstChild.value=items[12];}catch(e){}
        try{rcells.item(13).lastChild.nodeValue=items[12];}catch(e){}
        
        try{rcells.item(14).firstChild.value=items[13];}catch(e){}
        try{rcells.item(14).lastChild.nodeValue=items[13];}catch(e){}
        
        try{rcells.item(15).firstChild.value=items[14];}catch(e){}
        try{rcells.item(15).lastChild.nodeValue=items[14];}catch(e){}
        
        alert("Record Updated");
        clear_main_fields();
}

function delete_GRID()
{
        if(confirm("Do you want to delete ?"))
        {
		        var tbody=document.getElementById("grid_body");
		        var r=document.getElementById(com_id);
		        //alert(com_id);
                        var ri=r.rowIndex;
		        tbody.deleteRow(ri-1);
		        clear_main_fields();
        }
}
function loadTable(scod)
{
        com_id=scod;                                    
        //clearall();
        //alert(com_id);
        var r=document.getElementById(com_id);
        var rcells=r.cells;
       
        try{document.getElementById("cmb_invoice_no").value=rcells.item(1).firstChild.value;}catch(e){}
        try{document.getElementById("txt_invoice_date").value=rcells.item(2).firstChild.value;} catch(e){}
        try{document.getElementById("txt_particulars").value=rcells.item(3).firstChild.value;} catch(e){} 
        try{document.getElementById("txt_invoice_amt").value=rcells.item(4).firstChild.value;} catch(e){} 
        try{document.getElementById("txt_sanctioned_amt").value=rcells.item(5).firstChild.value;} catch(e){} 
        try{document.getElementById("txt_deducted_amt").value=rcells.item(6).firstChild.value;} catch(e){} 
        try{document.getElementById("txt_net_amt").value=rcells.item(7).firstChild.value;} catch(e){} 
        try{document.getElementById("txtAcc_HeadCode").value=rcells.item(8).firstChild.value;} catch(e){} 
        try{document.getElementById("txtAcc_HeadDesc").value=rcells.item(9).firstChild.value;} catch(e){} 
        try{document.getElementById("txt_Payable_to").value=rcells.item(10).firstChild.value;} catch(e){} 
        try{document.getElementById("txt_Budget_Provided").value=rcells.item(11).firstChild.value;} catch(e){} 
        try{document.getElementById("txt_Budget_sofar_spent").value=rcells.item(12).firstChild.value;} catch(e){} 
        try{document.getElementById("txt_amt_deducted_bill").value=rcells.item(13).firstChild.value;} catch(e){} 
        try{document.getElementById("txt_bal_amt_after_bill").value=rcells.item(14).firstChild.value;} catch(e){} 
        try{document.getElementById("txt_DetailsRemarks").value=rcells.item(15).firstChild.value;} catch(e){} 
                 
           document.formsanc_proceed.cmdadd.style.display='none';
	   document.formsanc_proceed.cmdupdate.style.display='block';
	   document.formsanc_proceed.cmddelete.disabled=false;
}
function nullfieldcheck()
{
                   if(document.getElementById("cmb_invoice_no").value==0)
                   {
                            alert("Select the Invoice Number");
                            document.getElementById("cmb_invoice_no").focus();
                            return false;
                   }  
                   
                  else if(document.getElementById("txt_sanctioned_amt").value=="")
                   {
                        alert("Enter the Sanctioned Amount");  
                        document.getElementById("txt_sanctioned_amt").focus();
                        return false;        
                   }  
                 else if(document.getElementById("txt_deducted_amt").value=="")
                   {
                        alert("Enter the Deducted Amount");
                        document.getElementById("txt_deducted_amt").focus();
                        return false;
                   }
                  else if(document.getElementById("txt_Payable_to").value=="")
                   {
                             alert("Enter the Payable_To");
                             document.getElementById("txt_Payable_to").focus();
                             return false;
                   }
                 else if(document.getElementById("txt_Budget_Provided").value=="")
                   {
                                alert("Enter the Budget Provided Amount");
                                document.getElementById("txt_Budget_Provided").focus();
                                return false;    
                   }
                  else if(document.getElementById("txt_Budget_sofar_spent").value=="")
                   {
                                alert("Enter the Budget so far amount");
                                document.getElementById("txt_Budget_sofar_spent").focus();
                                return false;    
                   }
                   else if(document.getElementById("txt_GeneralRemarks").value!="")
                   {
                            if((document.getElementById("txt_GeneralRemarks").value.length)>=190)
                            {
                                          alert("Please Enter Particulars below 200 characters");
                                          document.getElementById("txt_GeneralRemarks").value="";
                                          return false;
                            }
                   }
                   else if(document.getElementById("txt_DetailsRemarks").value!="")
                   {
                            if((document.getElementById("txt_DetailsRemarks").value.length)>=190)
                            {
                                          alert("Please Enter Particulars below 200 characters");
                                          document.getElementById("txt_DetailsRemarks").value="";
                                          return false;
                            }
                   }
                  /* if(document.getElementById("txt_sanc_amt").value=="")
                   {
                                alert("Error in calculating the total sanctioned Amount");
                                document.getElementById("txt_sanc_amt").focus();
                                return false;    
                   } */
                   else if(document.getElementById("txt_invoice_date").value!="")
                    {
                            var invdate=document.getElementById("txt_invoice_date").value;
                            //alert(invdate);
                            var sancprodate=document.getElementById("txt_sancpro_date").value;
                            //alert(sancprodate);
                            datefun1(invdate,sancprodate);
                            document.getElementById("txt_sanctioned_amt").focus();
                            return false;
                    }
                   return true;
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function checkfields()
{
                var tbody=document.getElementById("grid_body");
                var t=0;
                if(tbody.rows.length==0)
                {
                        alert("Enter the Details Part");
                        return false; 
                }
                else
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
                }
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
	     call_clr();
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

