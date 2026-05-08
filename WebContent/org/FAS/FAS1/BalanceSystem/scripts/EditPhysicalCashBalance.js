var emp_flag;
/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////
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







function checknull()
{

    if((document.frmPhysicalCashBalance.cmbOffice_code.value=="") || (document.frmPhysicalCashBalance.cmbOffice_code.value.length<=0) || (document.frmPhysicalCashBalance.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.frmPhysicalCashBalance.cmbOffice_code.focus();
        return false;
    
    }
    if((document.getElementById("txtCB_Year").value.length!=4)|| (document.getElementById("txtCB_Year").value.length<=0) ||(document.getElementById("txtCB_Year").value==""))
    {
        alert("Enter the correct year");
        document.getElementById("txtCB_Year").focus();
        return false;
    }
     if(document.getElementById("txtCB_Month").value=="")
    {
        alert("Select a month");
        return false;
    }
    if((document.frmPhysicalCashBalance.txtverify_date.value=="") || (document.frmPhysicalCashBalance.txtverify_date.value.length<=0))
    {
        alert("Please Enter Verify Date");
        document.frmPhysicalCashBalance.txtverify_date.focus();
        return false;
    
    }
    return true;
}





var winAccHeadCode;
var winemp;
function servicepopup()
{
    emp_flag=true;
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
        
    winAccHeadCode= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","EmployeeSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAccHeadCode.moveTo(250,250);  
    winAccHeadCode.focus();
    
}

/*function doParentEmp(emp1)
{
    
   document.getElementById("txtperson_charge").value=emp1;
   //doFunction('checkCode','null');
   return true;
}*/



function serviceemp()
{
    emp_flag=false;
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,500);
       winemp.moveTo(250,250); 
       winemp.focus();
    }
    else
    {
        winemp=null
    }
        
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","EmployeeSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{

    if(emp_flag==true)
    {
        //document.getElementById("txtperson_charge").value=emp;
        loademp(emp);
   return true;
    }
    else if(emp_flag==false)
    {
    
   //document.getElementById("txtcash_verified").value=emp;
   loademp1(emp);
   return true;
   }
}
window.onunload=function()
{
if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
if (winemp && winemp.open && !winemp.closed) winemp.close();
}



function numbersonly(e)
{   
        var unicode=e.charCode? e.charCode : e.keyCode
         if(unicode==13)
        {
          //try{t.blur();}catch(e){}
          //return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
}


/////////////////////////////////////////////   Check Date() by User /////////////////////////////////////////////////////

function getCurrentYear() {
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
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
//        try{
//        var f=DateFormat(t,c,event,true,'3');
//        }catch(e){
         
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
            //alert(f);
            //t.value=c.replace(/\//g,'-');
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

///////////////////////////////////////////// calender input 
function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
       
        if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39  )
        {
                 
            if (unicode<48||unicode>57 ) 
                return false 
        }
}

function call_mainJSP_script(dateField,blur_flag)
{
    computedcash();
    
}

function computedcash()
{

    var unit=document.frmPhysicalCashBalance.cmbAcc_UnitCode.value;
    var office=document.frmPhysicalCashBalance.cmbOffice_code.value;
    var year=document.frmPhysicalCashBalance.txtCB_Year.value;
    var month=document.frmPhysicalCashBalance.txtCB_Month.value;
    var verifydate=document.frmPhysicalCashBalance.txtverify_date.value;
    
    var url="../../../../../EditPhysicalCashBalance1.con?Command=Computed&Unit="+unit+"&Office="+office+"&Year="+year+"&Month="+month+"&txtverify_date="+verifydate;
    
    var req=getTransport();
    req.open("POST",url,true); 
    req.onreadystatechange=function()
    {
    handleResponse(req);
    }   
    req.send(null);
    

}

function handleResponse(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        { 
        
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="success")
            {
                document.frmPhysicalCashBalance.txtcomputed_verifi.value="";
                document.frmPhysicalCashBalance.txtcash_verification.value="";
                document.frmPhysicalCashBalance.txtperson_charge.value="";
                document.frmPhysicalCashBalance.txtperson_charge1.value="";
                document.frmPhysicalCashBalance.txtcash_verified.value="";
                document.frmPhysicalCashBalance.txtcash_verified1.value="";
                document.frmPhysicalCashBalance.txtRemarks.value="";
                
                var value=baseResponse.getElementsByTagName("CompBalVerify")[0].firstChild.nodeValue;
                var Incharge=baseResponse.getElementsByTagName("ChargeId")[0].firstChild.nodeValue;
                var CashBalVerify=baseResponse.getElementsByTagName("Amount")[0].firstChild.nodeValue;
                var VerifyId=baseResponse.getElementsByTagName("VerifyId")[0].firstChild.nodeValue;
                var Purpose=baseResponse.getElementsByTagName("Purpose")[0].firstChild.nodeValue;
                var Remarks=baseResponse.getElementsByTagName("Remarks")[0].firstChild.nodeValue;
                
                document.frmPhysicalCashBalance.txtcomputed_verifi.value=value;
                document.frmPhysicalCashBalance.txtcomputed_verifi1.value=value;
                document.frmPhysicalCashBalance.txtcash_verification.value=CashBalVerify;
                document.frmPhysicalCashBalance.txtperson_charge.value=Incharge;
                loademp(Incharge);
                document.frmPhysicalCashBalance.txtcash_verified.value=VerifyId;
                loademp1(VerifyId);
                document.frmPhysicalCashBalance.cmbpurpose.value=Purpose;
                if(Remarks!="null")
                {
                    document.frmPhysicalCashBalance.txtRemarks.value=Remarks;
                }
            }
            else
            {
                document.frmPhysicalCashBalance.txtcomputed_verifi.value="";
                document.frmPhysicalCashBalance.txtcomputed_verifi1.value="";
                document.frmPhysicalCashBalance.txtcash_verification.value="";
                document.frmPhysicalCashBalance.txtperson_charge.value="";
                document.frmPhysicalCashBalance.txtperson_charge1.value="";
                document.frmPhysicalCashBalance.txtcash_verified.value="";
                document.frmPhysicalCashBalance.txtcash_verified1.value="";
                document.frmPhysicalCashBalance.txtRemarks.value="";
                document.frmPhysicalCashBalance.cmbpurpose.selectedIndex=0;
            }
        }
    }
    
}


function loademp(empid)
{
    
    var unit=document.frmPhysicalCashBalance.cmbAcc_UnitCode.value;
    var office=document.frmPhysicalCashBalance.cmbOffice_code.value;
    var url="../../../../../Receipt_SL.view?Command=loademp&eid="+empid+"&offid="+office+"&Acc_UnitCode="+unit;
    
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
    LoadEmpResponse(req);
    }   
    req.send(null);
}


function LoadEmpResponse(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            
            if(flag=="success")
            {
                document.frmPhysicalCashBalance.txtperson_charge1.value="";
                //document.frmPhysicalCashBalance.txtcash_verified1.value="";
                document.getElementById("txtperson_charge").value=="";
                var name=baseResponse.getElementsByTagName("eid")[0].firstChild.nodeValue;
                var name1=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
                var desig=baseResponse.getElementsByTagName("desig")[0].firstChild.nodeValue;
                document.getElementById("txtperson_charge").value=name;
                document.frmPhysicalCashBalance.txtperson_charge1.value=name1;
                //document.frmPhysicalCashBalance.txtcash_verified1.value=name1;
            }
            else
            {
                document.getElementById("txtperson_charge").value="";
                document.frmPhysicalCashBalance.txtperson_charge1.value="";
                //document.frmPhysicalCashBalance.txtcash_verified1.value="";
            }
        }
    }
    
}

function loademp1(empid)
{
    
    var unit=document.frmPhysicalCashBalance.cmbAcc_UnitCode.value;
    var office=document.frmPhysicalCashBalance.cmbOffice_code.value;
    var url="../../../../../Receipt_SL.view?Command=loademp&eid="+empid+"&offid="+office+"&Acc_UnitCode="+unit;
    
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
    LoadEmpResponse1(req);
    }   
    req.send(null);
}


function LoadEmpResponse1(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            
            if(flag=="success")
            {
                document.frmPhysicalCashBalance.txtcash_verified.value="";
                document.frmPhysicalCashBalance.txtcash_verified1.value="";
                document.getElementById("txtcash_verified").value=="";
                var name=baseResponse.getElementsByTagName("eid")[0].firstChild.nodeValue;
                var name1=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
                var desig=baseResponse.getElementsByTagName("desig")[0].firstChild.nodeValue;
                document.getElementById("txtcash_verified").value=name;
                document.frmPhysicalCashBalance.txtcash_verified1.value=name1;
            }
            else
            {
                document.getElementById("txtcash_verified").value="";
                document.frmPhysicalCashBalance.txtcash_verified.value="";
                document.frmPhysicalCashBalance.txtcash_verified1.value="";
            }
        }
    }
    
}

/////////////////////////////////////////////////////  Amount limitation 
function limit_amt(field,e)
{
      var unicode=e.charCode? e.charCode : e.keyCode;
      if(field.value.length<17)
      {
        if(field.value.length==14 && field.value.indexOf('.')==-1  )
        field.value=field.value+'.';
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<46 || unicode==47 || unicode>57   ) 
                return false 
        }
      }
      else 
      return false;
      
}




///////////////////////////////////////////  valid amount or not
function valid_amt(field)
{
    
    amt=field.value;
    if(amt.indexOf(".")!=amt.lastIndexOf("."))
    {
        alert("Enter a Valid Amount");
        field.value="";
        field.focus();
        
    }
}

function clear1()
{

    document.frmPhysicalCashBalance.txtcomputed_verifi.value="";
    document.frmPhysicalCashBalance.txtcash_verification.value="";
    document.frmPhysicalCashBalance.txtperson_charge.value="";
    document.frmPhysicalCashBalance.txtperson_charge1.value="";
    document.frmPhysicalCashBalance.txtcash_verified.value="";
    document.frmPhysicalCashBalance.txtcash_verified1.value="";
    document.frmPhysicalCashBalance.txtRemarks.value="";
    document.frmPhysicalCashBalance.txtverify_date.value="";
    document.frmPhysicalCashBalance.cmbpurpose.selectedIndex=0;
}