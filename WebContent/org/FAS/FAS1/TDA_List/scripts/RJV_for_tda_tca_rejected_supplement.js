var seq=0;

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

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
    if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
    {
           //  call_clr();
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

function checkSuppTB()
{
	alert("loading..");
	 var cmbAcc_UnitCode=document.forms[0].cmbAcc_UnitCode.value;
	 var txtCrea_date=document.forms[0].txtCrea_date.value;
	 var splt=txtCrea_date.split("/");
	 url="../../../../../TDA_Raised_Create?command=tb_supp&txtUnitId="+cmbAcc_UnitCode+"&year="+splt[2]+"&month="+splt[1];
     
      req=getTransport();
      req.open("GET",url,true);        
      req.onreadystatechange=function()
      {        	  
             tb_check_supp(req);
      }   
      req.send(null); 
}


function tb_check_supp(req)
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
                 //doFunction('load_Receipt_No','null');                 // return
																			// true;
              }
             else if(flag=="failure")
               {
                   
                    alert("Supplement Trial Balance Closed");// return false;//
                  
                     document.getElementById("txtCrea_date").value="";
               }
             else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                   
                    alert("Cash Book Control Not Found ");// return false;//
                   
                   // document.getElementById("txtReceipt_No").value="";
               }
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
                    document.getElementById("txtReceipt_No").value="";
               }
             else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");//return false;//
                    dateCtrl.focus();
                    document.getElementById("txtReceipt_No").value="";     
               }
        }
    }
}

function checkNull_verify()
{
	var count_inc=0;
  var date1=document.getElementById("txtCrea_date").value;
  if(date1=="")
  {
  alert("Enter Voucher Date");
  return false;
  }
  if(document.getElementById("supNo").value=="")
  {
	  alert("Choose Supplement No");
	  return false;
  }

  var tbody=document.getElementById("tbody");
	if(tbody.rows.length==0)
	{
	alert("Enter the Records in Grid and Submit");
	return false;
	}
	else
	  {
			 rows=tbody.getElementsByTagName("tr");
			 for(i=0;i<rows.length;i++)
	          {
	              var cells=rows[i].cells;
	             var rd=cells.item(8).lastChild.nodeValue;
	             var rejected=rd.split("-");
	             var vdate=date1.split("/");
	            
	             if(rejected[2]>vdate[2])
		             {
		            	 //rejected year greater, so block the form
		            	 count_inc++;
		             }
	             else if(rejected[2]==vdate[2])//2011
			             {
	            	
				            	 if(rejected[1]>vdate[1])
				            	 {
				            		 count_inc++;
				            	 }
				            	 else if(rejected[1]==vdate[1])
				            	 {
				            		 if(rejected[0]>vdate[0])
				                	 {
				                		 count_inc++;
				                	 }
				            		 
				            	 }
	            	 
			             }
	             
	         }
	    }
	if(count_inc>0)
	{
		 alert("Voucher Date Should be greater than Rejected Date:");
		 document.getElementById("txtCrea_date").value="";
		  return false;
	}
}
//end 
function call_date(dateCtrl)                        // TB_checking
{
  
   if(checkdt(dateCtrl))
   {
       //doFunction('check_TB',dateCtrl.value);
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
   else
   {
  
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
                //doFunction('load_Receipt_No','null');                 // return
																			// true;
             }
            else if(flag=="failure")
              {
                   dateCtrl.value="";
                   alert("Trial Balance Closed");// return false;//
                   dateCtrl.focus();
                    document.getElementById("txtCrea_date").value="";
              }
            else if(flag=="finyear")
              {
                         // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                   dateCtrl.value="";
                   alert("Cash Book Control Not Found ");// return false;//
                   dateCtrl.focus();
                   document.getElementById("txtCrea_date").value="";
              }
       }
   }
}

function doFunction(Command,param)
{ 
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
       
        if(Command=="searchByDate")
        {  
     	          
        var url="../../../../../TDA_TCA_List_Rejected_servlet?Command=rjvRejected&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
     //       alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse(req);
        }   
    req.send(null);
		          
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
			             
			            if(Command=="rjvRejected")
			            {
			                loadTable(baseResponse);
			            }
		        }
	    }
}

function loadTable(baseResponse)
{
var val_reason="",value_rem="";
        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="failure")
        {
                 alert("No Record exists");
                var tbody=document.getElementById("tbody");
                try{tbody.innerHTML="";}
                catch(e) {tbody.innerText="";}
              
              
        }
        else
        {                       
               var tbody=document.getElementById("tbody");
                try{tbody.innerHTML="";}
                catch(e) {tbody.innerText="";}  
                  
                service=baseResponse.getElementsByTagName("leng");
                
	              for(var i=0; i<service.length;i++)
	               {
                     
                         
		                        var items=new Array();
		                        items[0]=service[i].getElementsByTagName("vou_no")[0].firstChild.nodeValue;
                                items[1]=service[i].getElementsByTagName("vou_date")[0].firstChild.nodeValue;
		                        items[2]=service[i].getElementsByTagName("reason")[0].firstChild.nodeValue;
		                        items[3]=service[i].getElementsByTagName("Tot_Amt")[0].firstChild.nodeValue;
                                items[4]=service[i].getElementsByTagName("parti")[0].firstChild.nodeValue;
		                        items[5]=service[i].getElementsByTagName("unitname")[0].firstChild.nodeValue;
                                items[6]=service[i].getElementsByTagName("unitid")[0].firstChild.nodeValue;
                                items[7]=service[i].getElementsByTagName("tda_type")[0].firstChild.nodeValue;
                                items[8]=service[i].getElementsByTagName("rejected_date")[0].firstChild.nodeValue;
                                        
		                        var tbody=document.getElementById("tbody");
		                        var mycurrent_row=document.createElement("TR");                
                                        mycurrent_row.id=seq;
                                                
                                         var  cell1=document.createElement("TD");
                                        cell1.style.textAlign='center'; 
                                        var chcksel="";
                                        checkparam=seq;
                                       if(window.navigator.appName.toLowerCase().indexOf("netscape")==-1)
                                       {
                                        chcksel=document.createElement("<input type='checkbox' name='chckparameter' id='chckparameter' value='"+checkparam+"' />");
                                       }
                                       else
                                       {
                                              var chcksel=document.createElement("input");
                                              chcksel.type="checkbox";
                                              chcksel.checked='checked';
                                              chcksel.name="chckparameter";
                                              chcksel.id="chckparameter";
                                              chcksel.value= checkparam;
                                       }
                                       cell1.appendChild(chcksel);
                                       mycurrent_row.appendChild(cell1);
                                        
                                       var cell2=document.createElement("TD");       
                                        var v_no=document.createElement("input");
                                        v_no.type="hidden";
                                        v_no.name="v_number";
                                        v_no.value=items[0];
                                        cell2.appendChild(v_no);
                                        var currentText=document.createTextNode(items[0]);
                                        cell2.appendChild(currentText);
                                        mycurrent_row.appendChild(cell2);
                                       
                                        var cell3=document.createElement("TD");       
                                        var v_date=document.createElement("input");
                                        v_date.type="hidden";
                                        v_date.name="voucher_date";
                                        v_date.value=items[1];
                                        cell3.appendChild(v_date);
                                        var currentText=document.createTextNode(items[1]);
                                        cell3.appendChild(currentText);
                                        mycurrent_row.appendChild(cell3);
                                        
                                        var cell4=document.createElement("TD");       
                                        var org_unit=document.createElement("input");
                                        org_unit.type="hidden";
                                        org_unit.name="orginating";
                                        org_unit.value=items[6];
                                        cell4.appendChild(org_unit);
                                        var currentText=document.createTextNode(items[5]);
                                        cell4.appendChild(currentText);
                                        mycurrent_row.appendChild(cell4);
                                        
                                        var cell8=document.createElement("TD");       
                                        var tda_tca=document.createElement("input");
                                        tda_tca.type="hidden";
                                        tda_tca.name="tda_type1";
                                        tda_tca.value=items[7];
                                        cell8.appendChild(tda_tca);
                                        var currentText=document.createTextNode(items[7]);
                                        cell8.appendChild(currentText);
                                        mycurrent_row.appendChild(cell8);
                                       
                                        var cell5=document.createElement("TD");       
                                        var rem_id=document.createElement("input");
                                        rem_id.type="hidden";
                                        rem_id.name="remarks";
                                        rem_id.value="RJV Created on "+items[7]+" Rejection For AdviceNo: "+items[0]+" Dated: "+items[1]+" to Unit: "+items[5];
                                        cell5.appendChild(rem_id);
                                        value_rem="RJV Created on "+items[7]+" Rejection For AdviceNo: "+items[0]+" Dated: "+items[1]+" to Unit: "+items[5];
                                        var currentText=document.createTextNode(value_rem);
                                        cell5.appendChild(currentText);
                                        mycurrent_row.appendChild(cell5);
                                        
                                        var cell6=document.createElement("TD");       
                                        var tot_amt=document.createElement("input");
                                        tot_amt.type="hidden";
                                        tot_amt.name="total_amount";
                                        tot_amt.value=items[3];
                                        cell6.appendChild(tot_amt);
                                        var currentText=document.createTextNode(items[3]);
                                        cell6.appendChild(currentText);
                                        mycurrent_row.appendChild(cell6);
                                        
                                        var cell7=document.createElement("TD");       
                                        var reason_id=document.createElement("input");
                                        reason_id.type="hidden";
                                        reason_id.name="reason";
                                         if(items[2]==1)
                                        {
                                        val_reason="Not Related To This Office";
                                        }
                                        else  if(items[2]==2)
                                        {
                                        val_reason="Will be Accepted Later on Receipt Of Original Supporting Documents";
                                        }
                                        reason_id.value=items[2];
                                        cell7.appendChild(reason_id);
                                        var currentText=document.createTextNode(val_reason);
                                        cell7.appendChild(currentText);
                                        mycurrent_row.appendChild(cell7);
		                      
                                        var cell44=document.createElement("TD");       
                                        var r_date=document.createElement("input");
                                        r_date.type="hidden";
                                        r_date.name="Rejected_date";
                                        r_date.value=items[8];
                                        cell44.appendChild(r_date);
                                        var currentText=document.createTextNode(items[8]);
                                        cell44.appendChild(currentText);
                                        mycurrent_row.appendChild(cell44);
                                        
                                        var cell=document.createElement("TD");
    			                           cell.align='CENTER';
    			                           var anc=document.createElement("A");
    			                           var uid=document.getElementById("cmbAcc_UnitCode").value;
    			                           var offid=document.getElementById("cmbOffice_code").value;
    			                           var date1=items[1].split("-");
    			                           
    			                           
    			                           var url="javascript:Show('"+uid+"','"+offid+"','"+items[0]+"','"+date1[1]+"','"+date1[2]+"')";
    			                           anc.href=url;
    			                           var txtedit=document.createTextNode("DETAILS");
    			                           anc.appendChild(txtedit);
    			                           cell.appendChild(anc);
    			                           mycurrent_row.appendChild(cell);
                                        
		                        tbody.appendChild(mycurrent_row);
                                        seq=seq+1;
		        }
	}
}

function Show(unitcode,offid,recNo,mon,yr)
{
	//	 alert(unitcode+" "+offid+" "+yr+" "+mon+" "+recNo);
	 	 var Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/TDA_List/jsps/RJV_Details.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&cashbook_yr="+yr+"&cashbook_mn="+mon+"&voucher_no="+recNo,"VoucherList","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
	     Voucher_list_SL.moveTo(250,250);  
	     Voucher_list_SL.focus();
    
}

function btncancel()
{

 self.close();
}

function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
        if(unicode==13)
        {
          //t.blur();
          //return true;-------------------- for taking action when press ENTER
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48 || unicode>57 ) 
                return false 
        }
     }



/////////////////////////////////////////////   Check Date() by User /////////////////////////////////////////////////////
function getCurrentYear() 
{
    var year = new Date().getYear();
    if(year < 1900) year += 1900;
    return year;
}

function getCurrentMonth() {
    return new Date().getMonth() + 1;
} 

function getCurrentDay() {
    return new Date().getDate();
}


function isValidDate(dateStr) {
	  
	  // Checks for the following valid date formats:
	  // MM/DD/YYYY
	  // Also separates date into month, day, and year variables
	  var datePat = /^(\d{2,2})(\/)(\d{2,2})\2(\d{4}|\d{4})$/;
	  
	  var matchArray = dateStr.match(datePat); // is the format ok?
	  if (matchArray == null) {
	   alert("Date must be in MM/DD/YYYY format")
	   return false;
	  }
	  
	  month = matchArray[3]; // parse date into variables
	  day = matchArray[1];
	  year = matchArray[4];
	  if (month < 1 || month > 12) { // check month range
	   alert("Month must be between 1 and 12");
	   return false;
	  }
	  if (day < 1 || day > 31) {
	   alert("Day must be between 1 and 31");
	   return false;
	  }
	  if ((month==4 || month==6 || month==9 || month==11) && day==31) {
	   alert("Month "+month+" doesn't have 31 days!")
	   return false;
	  }
	  if (month == 2) { // check for february 29th
	   var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
	   if (day>29 || (day==29 && !isleap)) {
	    alert("February " + year + " doesn't have " + day + " days!");
	    return false;
	     }
	  }
	  return true;  // date is valid
	 }

function checkdt(t)
{
	  
	    if(t.value.length==0)
	    	return false;
	    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
	    {
	      
            var c=t.value;
//	        try{
//	        var f=DateFormat(t,c,event,true,'3');
//	        }catch(e){
            
            
            ///New code implemented on 28-03-2019  for year 2019 wrongly displayed 201 
            try{
                var f=isValidDate(c);
               }
           catch(e){
            
	        //exception  start
	        
	        t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            
            if(currentYear<_Service_Period_Beg_Year)
            {
            alert('Entered date should be greater than or equal to '+_Service_Period_Beg_Year);
            t.value="";
            t.focus();
            return false;
            }
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear())
            {
            
                    alert('Entered date should be less than current date ');
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
           {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than current date');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date ');
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
	            
            t.value=c;
            if(err!=0)
            {
                    t.value="";
                    return false;
            }
            return true;
	        
	        
	        //exception end
	        
	        }
	        if( f==true)
		    {
		           
		            t.value=c;
		            var sc=t.value.split('/');
		            var currenDay =sc[0];
		            var currentMonth=sc[1];
		            var currentYear=sc[2];
		            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
		             if(currentYear<_Service_Period_Beg_Year)
		            {
		            alert('Entered date should be greater than or equal to '+_Service_Period_Beg_Year);
		            t.value="";
		            t.focus();
		            return false;
		            }
		            if(currentYear > getCurrentYear())
		            {
		            
		                    alert('Entered date should be less than current date');
		                    t.value="";
		                    t.focus();
		                    return false;
		           } 
		           else if(currentYear == getCurrentYear())
		            {
		                    if( currentMonth > getCurrentMonth())
		                    {
		                        alert('Entered date should be less than current date');
		                        t.value="";
		                        t.focus();
		                        return false;
		                    }
		                    else if( currentMonth == getCurrentMonth())
		                    {
		                        if(currenDay > getCurrentDay() )
		                        {
		                                alert('Entered date should be less than current date ');
		                                t.value="";
		                                t.focus();
		                                return false;
		                        }
		                    }
		                    
		            }
		            
		            t.value=c;
		           
		            return true;
		            
		    }
	        else
	        {
	                if(err!=0)
	                {
	                    t.value="";
	                    return false;
	                }
	        }
	            
	    }
	    else
	    {
	            alert('Date format  should be (dd/mm/yyyy)');
	            t.value="";
	            //t.focus();
	            return false
	    }
	    
}

function selectAll(Opt)
{

  var len=  document.getElementById("tbody").rows.length;

  if(len==1)
  {
          if (Opt =="ALL")
          {
        	 document.RJVRejected.chckparameter.checked=true;
          
          }
          else if (Opt=="UNSelect" )
          {
          document.RJVRejected.chckparameter.checked=false;
        
          }
  }
  else if(len>1)
  {
	  alert("else ");
          for(var i=0;i<len;i++)
          {
                if ( Opt =="ALL")
                {
                    //document.list_rejectedVerify.chckparameter[i].checked=true;
                	document.RJVRejected.chckparameter[i].checked=true;
                	
                }
                else if(Opt=="UNSelect")
                {
                    document.RJVRejected.chckparameter[i].checked=false;
                }
          }
  }

}

//changes
function Check_Supplement_No()
{
      var txtCrea_date=document.getElementById("txtCrea_date").value;
     
       var url="../../../../../Supplement_Journal_Create.kv?Command=Check_Supplement_No&txtCrea_date="+txtCrea_date;
      // alert(url);
       var req=getTransport();
       req.open("GET",url,true); 
       req.onreadystatechange=function()
       {
           Check_Supplement_No_Response(req);
       }   
       req.send(null);
}

function Check_Supplement_No_Response(req)
{
  if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
           
         if(flag=="failure")
              {
                var suppl_error=baseResponse.getElementsByTagName("suppl_error")[0].firstChild.nodeValue;
                alert(suppl_error);  
                //document.getElementById("txtCrea_date").value="";
              }
              else if(flag=="success")
              {
             
//              var supno=baseResponse.getElementsByTagName("supno")[0].firstChild.nodeValue;
           //   alert("supno"+supno);
              
                 var supNo1 = document.forms[0].supNo;
                 var supno = baseResponse.getElementsByTagName("supno"); 
                 for(var i=0; i<supno.length; i++)
                     {
                         var opt = document.createElement('option');
                         opt.value = supno[i].firstChild.nodeValue;
                         opt.innerHTML = supno[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                         supNo1.appendChild(opt);
                     }
              
              
              }

       }
  }
}

