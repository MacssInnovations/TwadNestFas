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

function checkNull_verify()
{
  var tbody=document.getElementById("tbody");
	if(tbody.rows.length==0){
	alert("Choose the records and Verify");
	return false;
	}
	else
		{
	var tbody=document.getElementById("tbody");       
	
	if(tbody.rows.length>0)
		{
		var len_two=tbody.rows.length;
        for(var mm=0;mm<len_two;mm++)
        {
                 var cell_one=tbody.rows[mm].cells;
                 var grid_date=cell_one.item(2).lastChild.nodeValue;
//                 alert("grid_date!....>"+grid_date);
                 var journal_date=document.getElementById("txtCreate_Date").value;
//                 alert("journal_date!....>"+journal_date);
                 var str1_grid =grid_date.split("-");
                 var str2 = journal_date.split("/");
                 
//                 alert("str1_grid[2]==>"+str1_grid[2]);
//               alert("str2[2]==>"+str2[2]);
//               alert("str1_grid[1]==>"+str1_grid[1]);
//               alert("str2[1]==>"+str2[1]);
//               alert("str1_grid[0]==>"+str1_grid[0]);
//               alert("str2[0]==>"+str2[0]);
                 
                 
                 if(str1_grid[2]>str2[2])
                 {
                            alert("Verification Date should not be less than Voucher Date**");
                            document.getElementById("txtCreate_Date").value="";
                             return false;
                 }
                 else if(str1_grid[2]==str2[2])
                 {
                   
                        if(str1_grid[1]>str2[1])
                        {
                            alert("Verification Date should not be less than Voucher Date**");
                            document.getElementById("txtCreate_Date").value="";
                             return false;
                        }
                        else if(str1_grid[1]==str2[1])
                        {
                        	
                            if(str1_grid[0]>str2[0])
                            {
                            alert("Verification Date should not be less than Voucher Date**");
                            document.getElementById("txtCreate_Date").value="";
                             return false;
                            }
                        
                        }
                  
                 }
                 return true;
        }
        }
    }		
	
}

function doFunction(Command,param)
{ 
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
        
        var txtFrom_date=document.getElementById("txtFrom_date").value;
        var txtTo_date=document.getElementById("txtTo_date").value;
        
        if(txtFrom_date=="" && txtTo_date=="")
        	{
        	alert("Please Enter the From date and Todate Filed");
        	return false;
        	}
        else
        	{
        	
       
        if(Command=="searchByDate")
        {  
      
		          
        var url="../../../../../TDA_TCA_List_Rejected_servlet?Command=searchByDate_verify&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtFrom_date="+txtFrom_date+"&txtTo_date="+txtTo_date;
       //     alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse(req);
        }   
    req.send(null);
		          
        }
        	}
}

function Check_Supplement_No()
{
      var txtFrom_date=document.getElementById("txtFrom_date").value;
     
       var url="../../../../../TDA_TCA_List_Rejected_servlet?Command=Check_Supplement_No&txtFrom_date="+txtFrom_date;
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


function checkLiveSub()
{
var txtCrea_date=document.getElementById("txtFrom_date").value;
var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
var ssno=document.getElementById("supNo").value;
var url="../../../../../Supplement_Journal_Create.kv?Command=solveSupNo&ssno="+ssno+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
    var req=getTransport();
      req.open("GET",url,true); 
      req.onreadystatechange=function()
      {
          supplementNumber(req);
      }   
      req.send(null);
}

function supplementNumber(req)
{
 if(req.readyState==4)
   {
       if(req.status==200)
       {  
           var baseResponse=req.responseXML.getElementsByTagName("response")[0];
           var tagcommand=baseResponse.getElementsByTagName("command")[0];
           var Command=tagcommand.firstChild.nodeValue;
           var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
          
           if(flag=="supsuccess")
             {
               var supnumber=baseResponse.getElementsByTagName("supnumber");
            //   alert(supnumber);
               var supNo= document.getElementById("supNo").value;
              // document.forms[0].supNo.value="";
//              for(var i=0;i<supnumber.length;i++)
//              {alert("length");
                   var s1=supnumber[0].firstChild.nodeValue;
                  // alert("s1"+s1);
                   if(s1<supNo)
                   {
                   alert("choose supplement Number:"+s1);
                   document.forms[0].supNo.value="";
                   }
                   
            //  }
             }
             else  if(flag=="TBfailure")
             {
               var supnumber=baseResponse.getElementsByTagName("supnumber")[0].firstChild.nodeValue;
               alert(supnumber);
               document.forms[0].supNo.value="";
             }

      }
 }
}




// this is for supplement
function doFunction_supp(Command,param)
{ 
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
        
        var txtFrom_date=document.getElementById("txtFrom_date").value;
        var supNoo=document.getElementById("supNo").value;
        
        if(Command=="searchByDate")
        {  
      
		          
        var url="../../../../../TDA_TCA_List_Rejected_servlet?Command=searchByDate_verify_supp&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtFrom_date="+txtFrom_date+"&supNo="+supNoo;
       //     alert(url);
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
			             
			            if(Command=="searchByDate_verify")
			            {
			                loadTable(baseResponse);
			            }
			            else if(Command=="searchByDate_verify_supp")
			            {
			                loadTable(baseResponse);
			            }
			            	
		        }
	    }
}

function loadTable(baseResponse)
{
var val_reason="";
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
                                       
                                        var cell5=document.createElement("TD");       
                                        var rem_id=document.createElement("input");
                                        rem_id.type="hidden";
                                        rem_id.name="remarks";
                                        rem_id.value=items[4];
                                        cell5.appendChild(rem_id);
                                        var currentText=document.createTextNode(items[4]);
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
		                      
		                        tbody.appendChild(mycurrent_row);
                                        seq=seq+1;
		        }
	}
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
        	 document.list_rejectedVerify.chckparameter.checked=true;
          
          }
          else if (Opt=="UNSelect" )
          {
          document.list_rejectedVerify.chckparameter.checked=false;
        
          }
  }
  else if(len>1)
  {
          for(var i=0;i<len;i++)
          {
                if ( Opt =="ALL")
                {
                    document.list_rejectedVerify.chckparameter[i].checked=true;
                }
                else if(Opt=="UNSelect")
                {
                    document.list_rejectedVerify.chckparameter[i].checked=false;
                }
          }
  }

}