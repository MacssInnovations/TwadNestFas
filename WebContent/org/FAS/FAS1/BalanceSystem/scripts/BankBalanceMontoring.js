var emp_flag;


function clear1()
{
    document.getElementById("cmbbank_name").selectedIndex=0;
    document.getElementById("cmbbranch_name").selectedIndex=0;
    document.getElementById("cmbaccount_type").selectedIndex=0;
    document.getElementById("txtCol_accode").value="";
    document.getElementById("cmbbank_accno").selectedIndex=0;
    document.getElementById("txtverify_date").value="";
    document.getElementById("txtOpening_Bal").value="";
    document.getElementById("txtOpening_Bal1").value="";
    document.getElementById("txtCollection").value="";
    document.getElementById("txtCollection1").value="";
    document.getElementById("txtRemittance").value="";
    document.getElementById("txtRemittance1").value="";
    document.getElementById("txtWithdrawl").value="";
    document.getElementById("txtWithdrawl1").value="";
    document.getElementById("txtClosing_Bal_Collection").value="";
    document.getElementById("txtClosing_Bal_Collection1").value="";
    document.getElementById("txtClosing_Bal_Remittance").value="";
    document.getElementById("txtClosing_Bal_Remittance1").value="";
    document.getElementById("txtDiff_Bal").value="";
    document.getElementById("txtDiff_Bal1").value="";
    
}
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

    /*if((document.frmBankBalance.cmbOffice_code.value=="") || (document.frmBankBalance.cmbOffice_code.value.length<=0) || (document.frmBankBalance.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.frmBankBalance.cmbOffice_code.focus();
        return false;
    
    }*/
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
    if((document.frmBankBalance.txtverify_date.value=="") || (document.frmBankBalance.txtverify_date.value.length<=0))
    {
        alert("Please Enter To Date");
        document.frmBankBalance.txtverify_date.focus();
        return false;
    
    }
    return true;
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

/*function call_mainJSP_script(dateField,blur_flag)
{
    //computedcash();
    
}*/

function callServer(id,command)
{
    if(command=="Office")
    {
        var office=document.getElementById("cmbAcc_UnitCode").value;    
        var url="../../../../../BankBalanceMontoringServlet.con?Command=BankName&cmbOffice_code="+office;
        
        var req=getTransport();
        req.open("POST",url,true); 
        req.onreadystatechange=function()
        {
        handleResponse(req);
        }   
        req.send(null);
    }
    if(command=="Branch")
    {
        var Bank=document.getElementById("cmbbank_name").value;
        var url="../../../../../BankBalanceMontoringServlet.con?Command=Branch&BranchId="+Bank;
        var req=getTransport();
        req.open("POST",url,true); 
        req.onreadystatechange=function()
        {
        handleResponse(req);
        }   
        req.send(null);
    }
    if(command=="AccType")
    {
        var Branch=document.getElementById("cmbbranch_name").value;
        var Bank=document.getElementById("cmbbank_name").value;
        var office=document.getElementById("cmbAcc_UnitCode").value;
        var url="../../../../../BankBalanceMontoringServlet.con?Command=AccType&BankId="+Bank+"&BranchId="+Branch+"&cmbOffice_code="+office;
        //alert(url);
        var req=getTransport();
        req.open("POST",url,true); 
        req.onreadystatechange=function()
        {
        handleResponse(req);
        }   
        req.send(null);
    }
    if(command=="AccHead")
    {
        var Branch=document.getElementById("cmbbranch_name").value;
        var Bank=document.getElementById("cmbbank_name").value;
        var office=document.getElementById("cmbAcc_UnitCode").value;
        var actype=document.getElementById("cmbaccount_type").value;
        var url="../../../../../BankBalanceMontoringServlet.con?Command=AccHead&BankId="+Bank+"&BranchId="+Branch+"&cmbOffice_code="+office+"&AcType="+actype;
        
        var req=getTransport();
        req.open("POST",url,true); 
        req.onreadystatechange=function()
        {
        handleResponse(req);
        }   
        req.send(null);
    }
    if(command=="BankAcNo")
    {
        var Branch=document.getElementById("cmbbranch_name").value;
        var Bank=document.getElementById("cmbbank_name").value;
        var office=document.getElementById("cmbAcc_UnitCode").value;
        var Collacc=id;
        
        var url="../../../../../BankBalanceMontoringServlet.con?Command=BankAcNo&BankId="+Bank+"&BranchId="+Branch+"&cmbOffice_code="+office+"&CollAcCode="+Collacc;
        var req=getTransport();
        req.open("POST",url,true); 
        req.onreadystatechange=function()
        {
        handleResponse(req);
        }   
        req.send(null);
    }
    if(command=="OpeningBal")
    {
        var Branch=document.getElementById("cmbbranch_name").value;
        var Bank=document.getElementById("cmbbank_name").value;
        var office=document.getElementById("cmbAcc_UnitCode").value;
        var actype=document.getElementById("cmbaccount_type").value;
        var txtCol_accode1=document.getElementById("txtCol_accode1").value;
        var cmbbank_accno=document.getElementById("cmbbank_accno").value;
        var url="../../../../../BankBalanceMontoringServlet.con?Command=OpeningBal&BankId="+Bank+"&BranchId="+Branch+"&cmbOffice_code="+office+"&AcType="+actype+"&CollAcCode="+txtCol_accode1+"&BankAcNo="+cmbbank_accno;
        var req=getTransport();
        req.open("POST",url,true); 
        req.onreadystatechange=function()
        {
        handleResponse(req);
        }   
        req.send(null);
    }
    if(command=="Collection")
    {
        var unit=document.getElementById("cmbAcc_UnitCode").value;
        var Branch=document.getElementById("cmbbranch_name").value;
        var Bank=document.getElementById("cmbbank_name").value;
        var office=document.getElementById("cmbAcc_UnitCode").value;
        var actype=document.getElementById("cmbaccount_type").value;
       // alert(actype);
        var txtCol_accode1=document.getElementById("txtCol_accode1").value;
        var cmbbank_accno=document.getElementById("cmbbank_accno").value;
        var Year=document.getElementById("txtCB_Year").value;
        var Month=document.getElementById("txtCB_Month").value;
        var AccHead=document.getElementById("txtCol_accode1").value;
        var Adate=document.getElementById("txtverify_date").value;
        var OpeningBal=document.getElementById("txtOpening_Bal1").value;
        var url="../../../../../BankBalanceMontoringServlet.con?Command=Collection&BankId="+Bank+"&AcType="+actype+"&BranchId="+Branch+"&cmbOffice_code="+office+"&CollAcCode="+txtCol_accode1+"&BankAcNo="+cmbbank_accno+"&AccUnit="+unit+"&txtCB_Year="+Year+"&txtCB_Month="+Month+"&AccHead="+AccHead+"&txtverify_date="+Adate+"&OpeningBal="+OpeningBal;
        //alert(url);
        var req=getTransport();
        req.open("POST",url,true); 
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
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            var cmbbank_name=document.getElementById("cmbbank_name");
            var cmbbranch_name=document.getElementById("cmbbranch_name");
            var cmbbank_accno=document.getElementById("cmbbank_accno");
            var cmbaccount_type=document.getElementById("cmbaccount_type");
            var command=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
                    if(command=="Bank")
                    {
                                    if(flag=="success")
                                    {
                                        var option=document.createElement("OPTION");
                                        cmbbank_name.innerHTML="";
                                        option.text="--Select Bank Name--";
                                        try
                                            {
                                                cmbbank_name.add(option);
                                        }catch(errorobject)
                                        { 
                                                 cmbbank_name.add(option,null);
                                        }
                                        var value=baseResponse.getElementsByTagName("options");
                                        for(var i=0;i<value.length;i++)
                                        {
                                             var tmpoption=value.item(i);
                                             var bankid=tmpoption.getElementsByTagName("BankId")[0].firstChild.nodeValue;
                                             var bankname=tmpoption.getElementsByTagName("BranchId")[0].firstChild.nodeValue;
                                             var option=document.createElement("OPTION");
                                             
                                             option.text=bankname;
                                             option.value=bankid;
                                            try
                                            {
                                                    cmbbank_name.add(option);
                                            }catch(errorobject)
                                            { 
                                                     cmbbank_name.add(option,null);
                                            }
                                        }
                                        
                                    }
                                    else
                                    {
                                        
                                    }
                     }
             
                    if(command=="Branch")
                    {
                        if(flag=="success")
                        {
                                       var option=document.createElement("OPTION");
                                        cmbbranch_name.innerHTML="";
                                        option.text="--Select Branch Name--";
                                        try
                                        {
                                                cmbbranch_name.add(option);
                                        }catch(errorobject)
                                        { 
                                                 cmbbranch_name.add(option,null);
                                        }
                                        var value=baseResponse.getElementsByTagName("options");
                                        for(var i=0;i<value.length;i++)
                                        {
                                             var tmpoption=value.item(i);
                                             var branchid=tmpoption.getElementsByTagName("BranchId")[0].firstChild.nodeValue;
                                             var branchname=tmpoption.getElementsByTagName("BranchName")[0].firstChild.nodeValue;
                                             var option=document.createElement("OPTION");
                                             option.text=branchname;
                                             option.value=branchid;
                                            try
                                            {
                                                    cmbbranch_name.add(option);
                                            }catch(errorobject)
                                            { 
                                                     cmbbranch_name.add(option,null);
                                            }
                                        }
                                    document.getElementById("cmbbranch_name").text=branchname;
                                    
                        }
                        else
                        {
                        
                        }
                    }
                    
                    if(command=="AccType")
                    {
                        if(flag=="success")
                        {
                                       var option=document.createElement("OPTION");
                                        cmbaccount_type.innerHTML="";
                                        option.text="--Select Account Type--";
                                        try
                                        {
                                                cmbaccount_type.add(option);
                                        }catch(errorobject)
                                        { 
                                                 cmbaccount_type.add(option,null);
                                        }
                                        var value=baseResponse.getElementsByTagName("options");
                                        for(var i=0;i<value.length;i++)
                                        {
                                             var tmpoption=value.item(i);
                                             var acctype=tmpoption.getElementsByTagName("Acctype")[0].firstChild.nodeValue;
                                             
                                             var option=document.createElement("OPTION");
                                             option.text=acctype;
                                             option.value=acctype;
                                            try
                                            {
                                                    cmbaccount_type.add(option);
                                            }catch(errorobject)
                                            { 
                                                     cmbaccount_type.add(option,null);
                                            }
                                        }
                                    
                                    
                        }
                        else
                        {
                                var option=document.createElement("OPTION");
                                        cmbaccount_type.innerHTML="";
                                        option.text="--Select Account Type--";
                                        try
                                        {
                                                cmbaccount_type.add(option);
                                        }catch(errorobject)
                                        { 
                                                 cmbaccount_type.add(option,null);
                                        }
                                        
                                   var option=document.createElement("OPTION");
                                        cmbbank_accno.innerHTML="";
                                        option.text="--Select Bank Account No--";
                                        try
                                        {
                                                cmbbank_accno.add(option);
                                        }catch(errorobject)
                                        { 
                                                 cmbbank_accno.add(option,null);
                                        }     
                            document.frmBankBalance.txtCol_accode.value="";
                            document.frmBankBalance.txtCol_accode1.value="";
                        }
                    
                    }
                    
                    if(command=="AccHead")
                    {
                        if(flag=="success")
                        {
                             var Acchead=baseResponse.getElementsByTagName("Acchead")[0].firstChild.nodeValue;
                             
                             document.frmBankBalance.txtCol_accode.value="";
                             document.frmBankBalance.txtCol_accode1.value="";
                             document.frmBankBalance.txtCol_accode.value=Acchead;
                             document.frmBankBalance.txtCol_accode1.value=Acchead;
                             callServer(Acchead,'BankAcNo');
                         }
                        else
                        {
                            document.frmBankBalance.txtCol_accode.value="";
                            document.frmBankBalance.txtCol_accode1.value="";
                        }
                    
                    
                    }
                    
                    if(command=="BankAcNo")
                    {
                        if(flag=="success")
                        {
                                       var option=document.createElement("OPTION");
                                        cmbbank_accno.innerHTML="";
                                        option.text="--Select Bank Account No--";
                                        try
                                        {
                                                cmbbank_accno.add(option);
                                        }catch(errorobject)
                                        { 
                                                 cmbbank_accno.add(option,null);
                                        }
                                        var value=baseResponse.getElementsByTagName("options");
                                        for(var i=0;i<value.length;i++)
                                        {
                                             var tmpoption=value.item(i);
                                             var BankAcNo=tmpoption.getElementsByTagName("BankAcNo")[0].firstChild.nodeValue;
                                             var option=document.createElement("OPTION");
                                             option.text=BankAcNo;
                                             option.value=BankAcNo;
                                            try
                                            {
                                                    cmbbank_accno.add(option);
                                            }catch(errorobject)
                                            { 
                                                     cmbbank_accno.add(option,null);
                                            }
                                        }
                                    
                                    
                        }
                        else
                        {
                            var option=document.createElement("OPTION");
                                        cmbbank_accno.innerHTML="";
                                        option.text="--Select Bank Account No--";
                                        try
                                        {
                                                cmbbank_accno.add(option);
                                        }catch(errorobject)
                                        { 
                                                 cmbbank_accno.add(option,null);
                                        }
                        
                        }
                    }
                    
                    if(command=="OpeningBal")
                    {
                        if(flag=="success")
                        {
                             var OpeningBal=baseResponse.getElementsByTagName("OpeningBal")[0].firstChild.nodeValue;
                             
                             document.frmBankBalance.txtOpening_Bal.value="";
                             document.frmBankBalance.txtOpening_Bal1.value="";
                             document.frmBankBalance.txtOpening_Bal.value=OpeningBal;
                             document.frmBankBalance.txtOpening_Bal1.value=OpeningBal;
                             
                         }
                        else
                        {
                            document.frmBankBalance.txtOpening_Bal.value="";
                            document.frmBankBalance.txtOpening_Bal1.value="";
                        }
                    
                    
                    }
                    
                    
                    if(command=="Collection")
                    {
                        if(flag=="success")
                        {
                             var CollAmount=baseResponse.getElementsByTagName("CollAmount")[0].firstChild.nodeValue;
                             var RemAmount=baseResponse.getElementsByTagName("RemAmount")[0].firstChild.nodeValue;
                             var PayAmount=baseResponse.getElementsByTagName("PayAmount")[0].firstChild.nodeValue;
                             var ClosingBalColl=baseResponse.getElementsByTagName("ClosingBalColl")[0].firstChild.nodeValue;
                             var ClosingBalRem=baseResponse.getElementsByTagName("ClosingBalRem")[0].firstChild.nodeValue;
                             var DiffAmount=baseResponse.getElementsByTagName("DiffAmount")[0].firstChild.nodeValue;
                             var DiffInd=baseResponse.getElementsByTagName("DiffInd")[0].firstChild.nodeValue;
                             
                             document.getElementById("txtCollection").value="";
                             document.getElementById("txtCollection1").value="";
                             document.getElementById("txtRemittance").value="";
                             document.getElementById("txtRemittance1").value="";
                             document.getElementById("txtWithdrawl").value="";
                             document.getElementById("txtWithdrawl1").value="";
                             document.getElementById("txtClosing_Bal_Collection").value="";
                             document.getElementById("txtClosing_Bal_Collection1").value="";
                             document.getElementById("txtClosing_Bal_Remittance").value="";
                             document.getElementById("txtClosing_Bal_Remittance1").value="";
                             document.getElementById("txtDiff_Bal").value="";
                             document.getElementById("txtDiff_Bal1").value="";
                             
                             document.getElementById("txtCollection").value=CollAmount;
                             document.getElementById("txtCollection1").value=CollAmount;
                             document.getElementById("txtRemittance").value=RemAmount;
                             document.getElementById("txtRemittance1").value=RemAmount;
                             document.getElementById("txtWithdrawl").value=PayAmount;
                             document.getElementById("txtWithdrawl1").value=PayAmount;
                             document.getElementById("txtClosing_Bal_Collection").value=ClosingBalColl;
                             document.getElementById("txtClosing_Bal_Collection1").value=ClosingBalColl;
                             document.getElementById("txtClosing_Bal_Remittance").value=ClosingBalRem;
                             document.getElementById("txtClosing_Bal_Remittance1").value=ClosingBalRem;
                             document.getElementById("txtDiff_Bal").value=DiffAmount;
                             document.getElementById("txtDiff_Bal1").value=DiffAmount;
                             if(DiffInd=="NotEqual")
                             {
                                alert("Amount is Not Equal.The differnce amount is:"+DiffAmount);
                             }
                             else
                             {
                                alert("Amount is Equal");
                             }
                         }
                        else
                        {
                            
                        }
                    
                    
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
                document.frmPhysicalCashBalance.txtcash_verified1.value="";
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


function call_mainJSP_script(dateField,blur_flag)
{
    //alert('hai');
    callServer(this.value,'Collection');
    
}