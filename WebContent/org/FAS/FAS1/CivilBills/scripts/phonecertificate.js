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
function LoadPhoneNo()
{
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
                 var url="../../../../../PhoneCertificate?command=LoadPhoneNo&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                 var req=getTransport();
                 req.open("GET",url,true); 
                 req.onreadystatechange=function()
                 {
                        processResponsenew(req);
                 }   
                 req.send(null);
}
function loadPhoneDetails()
{
             var phone_no=document.getElementById("cmbphone_no").value;
             //alert(phone_no);
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
             
                 var url="../../../../../PhoneCertificate?command=LoadPhoneDetails&phone_no="+phone_no+"&cmbOffice_code="+cmbOffice_code;
                 //alert(url);
                 var req=getTransport();
                 req.open("GET",url,true); 
                 req.onreadystatechange=function()
                 {
                        processResponsenew(req);
                 }   
                 req.send(null);
}
function processResponsenew(req)
{   
      if(req.readyState==4)   // Completed
      {
          if(req.status==200)   // No error
          {   
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              if(command=="LoadPhoneNo")
              {
                    LoadPhoneNumber(baseResponse);
              }
              else if(command=="LoadPhoneDetails")
              {
                    LoadPhoneDetails(baseResponse);
              }
             
        }    
    }
}
function LoadPhoneNumber(baseResponse)
{
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                  if(flag=="success")
                  {                       
                            var option=baseResponse.getElementsByTagName("option");
                            var Phone_no=document.getElementById("cmbphone_no");
                            var child=Phone_no.childNodes;
                            for(var i=child.length-1;i>1;i--)
                            {
                                    Phone_no.removeChild(child[i]);
                            } 
                            for(var i=0;i<option.length;i++)
                             {
                                var code=option[i].getElementsByTagName("phone_no")[0].firstChild.nodeValue;
                                //var desc=option[i].getElementsByTagName("phone_no")[0].firstChild.nodeValue;
                                
                                var opt=document.createElement("option");
                                opt.setAttribute("value",code);
                                var opttext=document.createTextNode(code);
                                opt.appendChild(opttext);
                                Phone_no.appendChild(opt);
                             }
                  }
                else if(flag=="nodata")
                {
                        alert("no data ");
                }
                else
                {
                        alert("Failed to load relevant data");
                }
               
}
function LoadPhoneDetails(baseResponse)
{
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                  if(flag=="success")
                  {                       
                                var custadian_name=baseResponse.getElementsByTagName("cust_name")[0].firstChild.nodeValue;
                               
                                var custadian_desig=baseResponse.getElementsByTagName("cust_desig")[0].firstChild.nodeValue;
                                
                                var purpose=baseResponse.getElementsByTagName("purpose")[0].firstChild.nodeValue;
                                
                                var connection_type=baseResponse.getElementsByTagName("connection_type")[0].firstChild.nodeValue;
                                var purpose1="";
                                if(purpose=='O')
                                {
                                        purpose1="OFFICE";
                                }                               
                                else if(purpose=='R')
                                {
                                        purpose1="RESIDENCE";
                                }                               
                                 else if(purpose=='F')
                                {
                                        purpose1="FAX";
                                }
                                var connection_type1="";
                                if(connection_type=='L')
                                {
                                        connection_type1="LANDLINE";
                                }                               
                               else if(connection_type=='M')
                               {
                                        connection_type1="MOBILE";
                                }                               
                                document.getElementById("txtcustname_desig").value= custadian_name+"  "+custadian_desig;
                                document.getElementById("txtconnection_type").value=connection_type1;
                                document.getElementById("txtpurpose").value=purpose1;
                  }
                else if(flag=="nodata")
                {
                        alert("no data ");
                }
                else
                {
                        alert("Failed to load relevant data");
                }
               
}
function callphonecertiAdd()
{
        //alert("Hello");
        var flag=nullfieldcheckphone();
        //alert(flag);
        if(true)
        {
            var Acc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            //alert(Acc_UnitCode);
            var Office_code=document.getElementById("cmbOffice_code").value;
           // alert(Office_code);
            var bill_majr_code=document.getElementById("txtbill_majr_code").value;
            var bill_minr_code=document.getElementById("txtbill_minr_code").value;
            var bill_sub_code=document.getElementById("txtbill_sub_code").value;
            var phone_no=document.getElementById("cmbphone_no").value;
            
            var bill_month=document.getElementById("txtbill_month").value;
            var bill_year=document.getElementById("txtbill_year").value;
            var invoice_no=document.getElementById("cmb_invoice_no").value;
            //alert(invoice_no);
            var certi_text=document.getElementById("txt_CertificateText").value;
             if(certi_text==null)
             {
                var certi_text1="";
             }
             else
                {
                    var certi_text1=certi_text;
                }
            var url="../../../../../PhoneCertificate?command=add&Acc_UnitCode1="+Acc_UnitCode+
                    "&Office_code1="+Office_code+"&bill_majr_code1="+bill_majr_code+
                    "&bill_minr_code1="+bill_minr_code+"&bill_sub_code1="+bill_sub_code+
                    "&phone_no1="+phone_no+"&bill_month1="+bill_month+"&bill_year1="+bill_year+
                    "&invoice_no1="+invoice_no+"&certi_text1="+certi_text1;
            alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
                    processResponse(req);
            }   
            req.send(null);
        }
}
function nullfieldcheckphone()
{
                   //alert("checking the elements");
                   if(document.getElementById("cmbAcc_UnitCode").value=="")
                   {
                            alert("Select the Accounting unit code");
                            document.getElementById("cmbAcc_UnitCode").focus();
                            return false;
                   }  
                   
                  if(document.getElementById("cmbOffice_code").value=="")
                   {
                        alert("Enter the Accounting Office_code");  
                        document.getElementById("cmbOffice_code").focus();
                        return false;        
                   }  
                 if(document.getElementById("txtbill_majr_code").value==0)
                   {
                        alert("Enter the Bill major code");
                        document.getElementById("txtbill_majr_code").focus();
                        return false;
                   }
                   if(document.getElementById("txtbill_minr_code").value==0)
                   {
                             alert("Enter the Bill minor code");
                             document.getElementById("txtbill_minr_code").focus();
                             return false;
                   }
                  if(document.getElementById("txtbill_sub_code").value==0)
                   {
                                alert("Enter the Bill Sub code");
                                document.getElementById("txtbill_sub_code").focus();
                                return false;    
                   }
                  if(document.getElementById("cmbphone_no").value=="")
                   {
                                alert("Enter the Phone Number");
                                document.getElementById("cmbphone_no").focus();
                                return false;    
                   }
                   if(document.getElementById("txtbill_month").value=="")
                   {
                                alert("Enter the Bill Month");
                                document.getElementById("txtbill_month").focus();
                                return false;    
                   }
                    if(document.getElementById("txtbill_year").value=="")
                   {
                                alert("Enter the Bill Year");
                                document.getElementById("txtbill_year").focus();
                                return false;    
                   }
                  if(document.getElementById("cmb_invoice_no").value==0)
                   {
                                alert("Enter the Invoice Number");
                                document.getElementById("cmb_invoice_no").focus();
                                return false;    
                   }
                   
                    if(document.getElementById("txt_CertificateText").value!="")
                   {
                            if((document.getElementById("txt_CertificateText").value.length)>=190)
                            {
                                          alert("Please Enter CertificateText below 200 characters");
                                          document.getElementById("txt_CertificateText").value="";
                                          return false;
                            }
                   }
        return true;
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
              
              if(command=="addResponse")
              {
                   //alert(command);
                    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                    if(flag=="success")
                    {
                        var phonecertino=baseResponse.getElementsByTagName("phone_certi_code")[0].firstChild.nodeValue;
                        alert("Phone Certificate Code " + phonecertino + " is inserted into the database successfully...");
                        clearAll();
                    }
                }
               else if(command=="retrieve")
               {
                  retrieveChecking(baseResponse);
               }
                else if(command=="updated")
                {
                   updateChecking(baseResponse);
                }
                else if(command=="deleted")
                { 
                     alert("record cancel successfully");   
                     clearAll();
                }
        }    
    }
}
function callphonecertiUpdate()
{
            var phone_no=document.getElementById("cmbphone_no").value;
            var bill_month=document.getElementById("txtbill_month").value;
            var bill_year=document.getElementById("txtbill_year").value;
            var invoice_no=document.getElementById("cmb_invoice_no").value;
            var certi_text=document.getElementById("txt_CertificateText").value;
            var url="../../../../../PhoneCertificate?command=updated&bill_month1="+bill_month+"&bill_year1="+bill_year+
                    "&invoice_no1="+invoice_no+"&certi_text1="+certi_text+"&phone_no1="+phone_no;
            //alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
                    processResponse(req);
            }   
            req.send(null);
}

function callphonecertiDelete()
{
            var phone_certi_no=document.getElementById("txtphone_certi_no").value;
            var r=confirm("Are U Sure?");
            if(r==true)
                {
                     var url="../../../../../PhoneCertificate?command=deleted&phone_certi_no1="+phone_certi_no;
                     var req=getTransport();
                        req.open("GET",url,true); 
                        req.onreadystatechange=function()
                        {
                                processResponse(req);
                        };   
                        req.send(null);
              }     
}

function clearAll()
{
            
            document.getElementById("cmbAcc_UnitCode").value="";
            document.getElementById("cmbOffice_code").value="";
            document.getElementById("txtbill_majr_code").value=0;
            document.getElementById("txtbill_minr_code").value=0;
            document.getElementById("txtbill_sub_code").value=0;
            document.getElementById("cmbphone_no").value=0;
            document.getElementById("txtcustname_desig").value="";
            document.getElementById("txtconnection_type").value="";
            document.getElementById("txtpurpose").value="";
            
            document.getElementById("txtbill_month").value="";
            document.getElementById("txtbill_year").value="";
            document.getElementById("cmb_invoice_no").value=0;
            document.getElementById("txt_CertificateText").value="";
            //alert("clearing the fields");
            document.forms[0].butAdd.disabled=false;  
            document.forms[0].butUpdate.disabled=true;
            document.forms[0].butDelete.disabled=true; 
}

function callphonecertiList()
{
        winemp= window.open("phoneCertificate_list.jsp","list1","status=1,height=500,width=600,resizable=YES,scrollbars=yes"); 
        winemp.moveTo(250,250);  
        winemp.focus();
}
function doParentEmp1(phone_certi_no)    
    {   
       //alert(phone_certi_no);
        document.forms[0].txtphone_certi_no.value=phone_certi_no;
        var url="../../../../../PhoneCertificate?command=retrieve&phone_certi_no1="+phone_certi_no;
        var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                        processResponse(req);
                }   
                req.send(null);
              
    }  
function retrieveChecking(baseResponse)   
    {
          var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
         
                  if(flag=="success")
                  {       
                      var acc_unit_id =  baseResponse.getElementsByTagName("acc_unit_id");
                      var acc_off_id =  baseResponse.getElementsByTagName("acc_off_id");
                      var bill_majrcode =  baseResponse.getElementsByTagName("bill_majrcode");
                      var bill_minrcode =  baseResponse.getElementsByTagName("bill_minrcode");
                      var bill_subcode =  baseResponse.getElementsByTagName("bill_subcode");
                      var phone_certificate_no =  baseResponse.getElementsByTagName("phone_certificate_no");
                      var phone_no =  baseResponse.getElementsByTagName("phone_no");
                      var bill_month =  baseResponse.getElementsByTagName("bill_month");
                      var bill_year =  baseResponse.getElementsByTagName("bill_year");
                      var invoice_no =  baseResponse.getElementsByTagName("invoice_no");
                      var certi_text =  baseResponse.getElementsByTagName("certi_text");
                      
                      document.forms[0].cmbAcc_UnitCode.value=acc_unit_id[0].firstChild.nodeValue;
                      common_LoadOffice(acc_unit_id[0].firstChild.nodeValue); 
                      document.forms[0].txtbill_majr_code.value=bill_majrcode[0].firstChild.nodeValue;
                     loadMinorType();
                     
                     setTimeout("testload('"+bill_minrcode[0].firstChild.nodeValue+"',1)",200);
                     
                     // document.forms[0].txtbill_minr_code.value=bill_minrcode[0].firstChild.nodeValue;
                     
                      setTimeout("testload('"+bill_subcode[0].firstChild.nodeValue+"',2)",300);
                      //loadSubType();
                      
                    //  document.forms[0].txtbill_sub_code.value=bill_subcode[0].firstChild.nodeValue;
                     // setTimeout('LoadPhoneNo()',550);
                      //LoadPhoneNo();
                      
                     // alert(phone_no[0].firstChild.nodeValue);
                      setTimeout("testload('"+phone_no[0].firstChild.nodeValue+"',3)",400);
                   //   document.forms[0].cmbphone_no.value=phone_no[0].firstChild.nodeValue;
                   //   loadPhoneDetails();
                    //  setTimeout('LoadInvNo()',650);
                      setTimeout("testload('"+invoice_no[0].firstChild.nodeValue+"',4)",500);   
                      // document.forms[0].cmb_invoice_no.value=phone_no[0].firstChild.nodeValue;
                      
                      document.forms[0].txtphone_certi_no.value=phone_certificate_no[0].firstChild.nodeValue;
                      //document.forms[0].cmbphone_no.value=phone_no[0].firstChild.nodeValue;
                      document.forms[0].txtbill_month.value=bill_month[0].firstChild.nodeValue;
                      document.forms[0].txtbill_year.value=bill_year[0].firstChild.nodeValue;
                      //document.forms[0].cmb_invoice_no.value=invoice_no[0].firstChild.nodeValue;
                      document.forms[0].txt_CertificateText.value=certi_text[0].firstChild.nodeValue;
                      
                      document.forms[0].butAdd.disabled=true;  
                      document.forms[0].butUpdate.disabled=false;
                      document.forms[0].butDelete.disabled=false;  
                      
                    }

    }

function testload(val,order)
{
	if(order==1){
	document.forms[0].txtbill_minr_code.value=val;
	loadSubType();
	}
		if(order==2){
		document.forms[0].txtbill_sub_code.value=val;
		LoadPhoneNo();
		}
	if(order==3){
		document.forms[0].cmbphone_no.value=val;
		loadPhoneDetails();
		LoadInvNo();
		}
	if(order==4){
		document.forms[0].cmb_invoice_no.value=val;
		//loadPhoneDetails();
		//LoadInvNo();
		}
}


 
function updateChecking(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
           {   
               alert("Record Updated Successfully.");
               clearAll();
           }
       else
           {
               alert("Failed to update values");
           }                                  
    }
    function clearfields()
    {
        document.getElementById("txtcustname_desig").value="";
        document.getElementById("txtconnection_type").value="";
        document.getElementById("txtpurpose").value="";
    }