var Common_branchID="";
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

var window_BankAccNumber_module;
function ListHeads()
    {
    
     if (window_BankAccNumber_module && window_BankAccNumber_module.open && !window_BankAccNumber_module.closed) 
    {
       window_BankAccNumber_module.resizeTo(500,500);
       window_BankAccNumber_module.moveTo(250,250); 
       window_BankAccNumber_module.focus();
    }
    else
    {
        window_BankAccNumber_module=null
    }
         var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
         window_BankAccNumber_module= window.open("Bank_AccountHeadCode_Module_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber_module.moveTo(250,250);    
    }

window.onunload=function()
{
//alert('hh');
if (window_BankAccNumber_module && window_BankAccNumber_module.open && !window_BankAccNumber_module.closed) window_BankAccNumber_module.close();
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
  //alert("show")
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
        
       // alert("here it comes");
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
function doFunction(Command,param)
{   

    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var txtBankId=document.getElementById("txtBankId").value;
    var txtBranchId=document.getElementById("txtBranchId").value;
    var txtBankAccountNo=document.getElementById("txtBankAccountNo").value;
    var Adate=document.getElementById("txtverify_date").value;
    var Oprmode=document.getElementById("txtOperation_mode").value;
    //alert("Oprmode="+Oprmode);
     
       var Year=document.getElementById("txtCB_Year").value;
       
       var Month=document.getElementById("txtCB_Month").value;
       
        var openBal=document.getElementById("txtBalanceAmount").value;
        var AcType=document.getElementById("txtBankAcc_type").value;
        
    
        if(Command=="loadbankdeatils")
        {  
            //alert(param);
            txtBankAccountNo=param;
            if(txtBankAccountNo.length!=0)
            {
               // alert("Inside"+txtBankAccountNo)
                var url="../../../../../Bank_Account_Details_New.view?Command=loadbankdeatils&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtBankAccountNo="+txtBankAccountNo+"&txtCB_Year="+Year+"&txtCB_Month="+Month;
                //alert(url);
                var req=getTransport();
                //alert(url)
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req,param);
                }   
                req.send(null);
            }         
        }
    else if(Command=="Collection")
     //   ACCHEAD=param;
          //  if(ACCHEAD!=0)  
    {       
      
        var url="../../../../../Bank_Balance_Monitor_New2?Command=Collection&BankId="+txtBankId+"&BranchId="+txtBranchId+"&BankAcNo="+txtBankAccountNo+"&txtBankAcc_type="+AcType+"&AccUnit="+cmbAcc_UnitCode+"&txtCB_Year="+Year+"&txtCB_Month="+Month+"&txtverify_date="+Adate+"&txtOperation_mode="+Oprmode;
        //var url="../../../../../Bank_Balance_Monitor_New?Command=Collection&BankId="+txtBankId+"&BranchId="+txtBranchId+"&BankAcNo="+txtBankAccountNo+"&AccUnit="+cmbAcc_UnitCode+"&txtCB_Year="+Year+"&txtCB_Month="+Month+"&txtverify_date="+Adate+"&AccHead="+AccHead+"&OpeningBal="+openBal;
       // alert(url);
        var req=getTransport();
        req.open("POST",url,true); 
        req.onreadystatechange=function()
        {
        handleResponse(req,param);
        }   
        req.send(null);
    }
        
  
       
}


function callblur()
{
//alert("inside")
document.getElementById("txtverify_date").blur();
return true;
}

/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
function handleResponse(req,param)
{ 
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="loadbankdeatils")
            {
                loadbankdeatils(baseResponse);
            }
            else if(Command=="Collection")
           {
        //   alert("comes inside1")
         // var ACCHEAD=baseResponse.getElementsByTagName("ACCHEAD")[0].firstChild.nodeValue;
    //       alert("accounthead is...."+ACCHEAD)
           loadOtherDetails(baseResponse,param);
           }
           
        }
    }
}

function loadOtherDetails(baseResponse,param)
{
 //("comes inside2")   
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

    if(flag=="success")
    {    
    //alert("comes inside3")
    document.getElementById("txtBalanceAmount").value=baseResponse.getElementsByTagName("OPEN_BAL")[0].firstChild.nodeValue;
    document.getElementById("txtCollection").value=baseResponse.getElementsByTagName("CollAmount")[0].firstChild.nodeValue;
    document.getElementById("txtRemittance").value=baseResponse.getElementsByTagName("RemAmount")[0].firstChild.nodeValue;
    document.getElementById("txtWithdrawl").value=baseResponse.getElementsByTagName("PayAmount")[0].firstChild.nodeValue;
    document.getElementById("txtClosing_Bal_Collection").value=baseResponse.getElementsByTagName("ClosingBalColl")[0].firstChild.nodeValue;
    document.getElementById("txtClosing_Bal_Remittance").value=baseResponse.getElementsByTagName("ClosingBalRem")[0].firstChild.nodeValue;   
   // document.getElementById("txtBalance").value=baseResponse.getElementsByTagName("ClosingBalRem")[0].firstChild.nodeValue;    
    document.getElementById("txtDiff_Bal").value=baseResponse.getElementsByTagName("DiffAmount")[0].firstChild.nodeValue;    
    var val=baseResponse.getElementsByTagName("DiffInd")[0].firstChild.nodeValue;  
    //alert(val);
    }
    else
    {
    alert("No data found");
    }
}    

function loadbankdeatils(baseResponse)
{
    
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

    if(flag=="success")
    {                      
        document.getElementById("txtBankId").value=baseResponse.getElementsByTagName("BANK_ID")[0].firstChild.nodeValue;
        document.getElementById("txtBankId_name").value=baseResponse.getElementsByTagName("BANK_NAME")[0].firstChild.nodeValue;
        document.getElementById("txtBranchId").value=baseResponse.getElementsByTagName("BRANCH_ID")[0].firstChild.nodeValue;
        document.getElementById("txtBranchId_name").value=baseResponse.getElementsByTagName("BRANCH_CITY")[0].firstChild.nodeValue;
        document.getElementById("txtBankAcc_type").value=baseResponse.getElementsByTagName("BANK_AC_TYPE_ID")[0].firstChild.nodeValue;
        document.getElementById("txtBankAcc_type_name").value=baseResponse.getElementsByTagName("ACCOUNT_TYPE")[0].firstChild.nodeValue;
        document.getElementById("txtOperation_mode").value=baseResponse.getElementsByTagName("AC_OPERATIONAL_MODE_ID")[0].firstChild.nodeValue;
        document.getElementById("txtOperation_mode_name").value=baseResponse.getElementsByTagName("AC_OPERATIONAL_MODE")[0].firstChild.nodeValue;
        //document.getElementById("txtBankAccountNo").value=baseResponse.getElementsByTagName("BANK_ID")[0].firstChild.nodeValue;
        document.getElementById("txtOpening_date").value=baseResponse.getElementsByTagName("OPEN_DATE")[0].firstChild.nodeValue;
        document.getElementById("txtDepositAmount").value=baseResponse.getElementsByTagName("INIT_AMT")[0].firstChild.nodeValue;
        document.getElementById("txtBalance").value=baseResponse.getElementsByTagName("ClosingBalRem")[0].firstChild.nodeValue;
        document.getElementById("txtBalanceAmount").value=baseResponse.getElementsByTagName("OPEN_BAL")[0].firstChild.nodeValue;
       // document.getElementById("txtCol_accode").value=baseResponse.getElementsByTagName("ACCHEAD")[0].firstChild.nodeValue;
       // document.getElementById("txtRemarks").value=baseResponse.getElementsByTagName("REMARK")[0].firstChild.nodeValue;
        var AccHead=baseResponse.getElementsByTagName("ACCHEAD")[0].firstChild.nodeValue;
        var ACCHEAD=baseResponse.getElementsByTagName("ACCHEAD")[0].firstChild.nodeValue;
        //alert("AccHead"+AccHead)
        //alert("ACCHEAD"+ACCHEAD)
        var Oprmode=document.getElementById("txtOperation_mode").value=baseResponse.getElementsByTagName("AC_OPERATIONAL_MODE_ID")[0].firstChild.nodeValue;
        var OpeningBal=document.getElementById("txtBalanceAmount").value=baseResponse.getElementsByTagName("OPEN_BAL")[0].firstChild.nodeValue;
       // alert("Oprmode2="+Oprmode);
      
    }
    else if(flag=="failure")
    {
        alert("No such Account number");
        document.getElementById("txtBankAccountNo").value="";
        document.getElementById("txtBankId").value="";
        document.getElementById("txtBankId_name").value="";
        document.getElementById("txtBranchId").value="";
        document.getElementById("txtBranchId_name").value="";
        document.getElementById("txtBankAcc_type").value="";
        document.getElementById("txtBankAcc_type_name").value="";
        document.getElementById("txtOperation_mode").value="";
        document.getElementById("txtOperation_mode_name").value="";
       // document.getElementById("txtCol_accode").value="";
        document.getElementById("txtOpening_date").value="";
        document.getElementById("txtDepositAmount").value="";
        document.getElementById("txtBalance").value="";
        document.getElementById("txtBalanceAmount").value="";
       // document.getElementById("txtAcc_HeadCode").value="";
       // document.getElementById("txtAcc_HeadDesc").value="";
    }
   
}



  


function ClearAll()
{
    //document.getElementById("cmbAcc_UnitCode").value;
    document.getElementById("txtBankAccountNo").value="";
    document.getElementById("txtBankId").value="";
    document.getElementById("txtBankId_name").value="";
    document.getElementById("txtBranchId").value="";
    document.getElementById("txtBranchId_name").value="";
    document.getElementById("txtBankAcc_type").value="";
    document.getElementById("txtBankAcc_type_name").value="";
    document.getElementById("txtOperation_mode").value="";
    document.getElementById("txtOperation_mode_name").value="";
    document.getElementById("txtOpening_date").value="";
    document.getElementById("txtDepositAmount").value="";
    document.getElementById("txtBalance").value="";
    document.getElementById("txtBalanceAmount").value="";
    document.getElementById("txtCollections").value="";
    document.getElementById("txtRemittance").value="";
    document.getElementById("txtWithdrawls").value="";
    document.getElementById("txtclosebal").value="";
    document.getElementById("txtclosebalrem").value="";

    
   // document.getElementById("txtRemarks").value="";
    
}
function nullcheck()
{
    if(document.getElementById("cmbAcc_UnitCode").value.length==0)
    {
        alert("Office code not found");
        //document.getElementById("cmbAcc_UnitCode").focus();
        return false;
    }
    if(document.getElementById("txtBankAccountNo").value=="")
    {
        alert("Enter the Account number");
        document.getElementById("txtBankAccountNo").focus();
        return false;
    }
    if(document.getElementById("txtBankId").value.length==0)
    {
        alert("Bank Name not found");
       // document.getElementById("txtBankId").focus();
        return false;
    }
    if(document.getElementById("txtBranchId").value.length==0)
    {
        alert("Branch name not found");
       // document.getElementById("txtBranchId").focus();
        return false;
    }
    if(document.getElementById("txtBankAcc_type").value.length==0)
    {
        alert(" Account type not found");
        //document.getElementById("txtBankAcc_type").focus();
        return false;
    }
    if(document.getElementById("txtOperation_mode").value.length==0)
    {
        alert("Operational mode not found");
        //document.getElementById("txtOperation_mode").focus();
        return false;
    }
   if(document.getElementById("cmbModule").value=="")
    {
        alert("select the module");
        document.getElementById("cmbModule").focus();
        return false;
    }
   
    return true;
}


///////////////////////////////////////  Numbers only fields
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
/*
  var x= field.value.indexOf('.');//==-1  )
        if(x!=-1)
        {
        var len=field.value.length;
        field.value=field.value.substring(0,x+2)
        }*/
///////////////////////////////////    account head limitation /////////////


/////////////////////// exit  function

function exit()
{
    self.close();
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

function check_leng(val)
{
if(val.length>=250)
return false;
}
