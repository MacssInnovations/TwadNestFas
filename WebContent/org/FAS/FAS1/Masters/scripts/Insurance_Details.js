
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

var window_BankAccNumber;
function ListAll()

    {  
	//alert("inside listall");
    
     if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) 
    {
       window_BankAccNumber.resizeTo(500,500);
       window_BankAccNumber.moveTo(250,250); 
       window_BankAccNumber.focus();
    }
    else
    {
        window_BankAccNumber=null
    }
         var cmbAcc_UnitCode=document.frmIns_Details.cmbAcc_UnitCode.value;
         var cmbOffice_code=document.frmIns_Details.cmbOffice_code.value;
         var cmbFinancialYear=document.frmIns_Details.cmbFinancialYear.value;
     //    alert(cmbAcc_UnitCode);
      //   alert(cmbOffice_code);
     //    alert(cmbFinancialYear);
        // var cmbasset=document.getElementById("cmbasset").value;
         window_BankAccNumber= window.open("Insurance_Details_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cmbFinancialYear="+cmbFinancialYear,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
    }

window.onunload=function()
{
if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}


function doParentBankAccNumbers(dateentry,finyr,assetcode,compdetails,instype,favour,pno,cmncedate,expdate,insamount,preamount,othercharges,renewaldate,issuingoff,remarks)
{ //alert("inside parent");
        // alert(dateentry);
         
            var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            var d2=document.getElementById("cmdDelete");
            d2.style.display="block";
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";  
            document.getElementById("txt_date").value=dateentry;
            document.getElementById("cmbFinancialYear").value=finyr;
            document.getElementById("cmbasset").value=assetcode;
            document.getElementById("txtOffice_Name").value=compdetails;
            document.getElementById("ins_type").value=instype;
            document.getElementById("favour_of").value=favour;
            document.getElementById("po_no").value=pno;
           
            document.getElementById("Comnce_date").value=cmncedate;
            document.getElementById("expiry_date").value=expdate;
            document.getElementById("txtins_amount").value=insamount;
            document.getElementById("txtpre_amount").value=preamount;
            document.getElementById("txtcharges").value=othercharges;
            document.getElementById("renewal_date").value=renewaldate;
            document.getElementById("txtissuing_address").value=issuingoff;
            document.getElementById("txtRemarks").value=remarks;
            
    document.getElementById("cmbasset").disabled=true;
  
}
function doFunction(Command,param)
{   

    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    var txt_date=document.getElementById("txt_date").value;
    var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
   // var txtBranch_Name=document.getElementById("txtBranch_Name").value;
    var cmbasset=document.getElementById("cmbasset").value;
    
    var txtOffice_Name=document.getElementById("txtOffice_Name").value;
    var type_insurance=document.getElementById("ins_type").value;
    var infavour=document.getElementById("favour_of").value;
    var policy_no=document.getElementById("po_no").value;
    var cmc_date=document.getElementById("Comnce_date").value;
    var expiry_date=document.getElementById("expiry_date").value;
    var ins_amount=document.getElementById("txtins_amount").value;
    var txtpre_amount=document.getElementById("txtpre_amount").value;
    var other_charges=document.getElementById("txtcharges").value;
    var renewal_date=document.getElementById("renewal_date").value;
    var iss_address=document.getElementById("txtissuing_address").value;
    //alert(renewal_date);
    var txtRemarks=document.getElementById("txtRemarks").value;
  
         if(Command=="Add")
        {
            var flag=nullcheck();
            if(flag==true)
               {
                var url="../../../../../Insurance_Details.view?Command=Add&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&txt_date="+txt_date+"&cmbFinancialYear="+cmbFinancialYear+
                        "&cmbasset="+cmbasset+"&txtOffice_Name="+txtOffice_Name+"&type_insurance="+type_insurance+
                        "&infavour="+infavour+"&policy_no="+policy_no+"&cmc_date="+cmc_date+
                        "&expiry_date="+expiry_date+"&ins_amount="+ins_amount+"&txtpre_amount="+txtpre_amount+
                        "&other_charges="+other_charges+"&renewal_date="+renewal_date+
                        "&iss_address="+iss_address+"&txtRemarks="+txtRemarks;
              // alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
                }
        }
        else if(Command=="Delete")
        {
        
            if(confirm("Do You Really want to Delete it?"))
            {
                
               var flag=nullcheck();
               if(flag==true)
               {  
                  var url="../../../../../Insurance_Details.view?Command=Delete&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&txt_date="+txt_date+"&cmbFinancialYear="+cmbFinancialYear+
                        "&cmbasset="+cmbasset;
                  //  alert(url);
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
       else if(Command=="Update")
       { 
    	//   alert("inside update");
            var flag=nullcheck();
            if(flag==true)
             {
                var url="../../../../../Insurance_Details.view?Command=Update&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&txt_date="+txt_date+"&cmbFinancialYear="+cmbFinancialYear+
                        "&cmbasset="+cmbasset+"&txtOffice_Name="+txtOffice_Name+"&type_insurance="+type_insurance+
                        "&infavour="+infavour+"&policy_no="+policy_no+"&cmc_date="+cmc_date+
                        "&expiry_date="+expiry_date+"&ins_amount="+ins_amount+"&txtpre_amount="+txtpre_amount+
                        "&other_charges="+other_charges+"&renewal_date="+renewal_date+
                        "&iss_address"+iss_address+"&txtRemarks="+txtRemarks;
            //    alert(url);
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


/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
function handleResponse(req)
{ 
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
        
            if(Command=="Add")
            {
                addRow(baseResponse);
            }
            else if(Command=="Delete")
            {
                deleteRow(baseResponse);
            }
            
            else if(Command=="Update")
            {
                UpdateRow(baseResponse);
            }
        }
    }
}


function addRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
  //  alert(flag);
    if(flag=="success")
    {
        alert("Record inserted into database");
        ClearAll();
    }
    else
    {
        alert("Record not inserted into database");
    }
}

function deleteRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
        alert("Records deleted from database");
         ClearAll();
    }
    else
    {
        alert("Record not deleted from database");
    }
}  

function UpdateRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var items=new Array();
    
    if(flag=="success")
    {
        alert("Record Updated");
        document.frmIns_Details.cmbasset.disabled=false;
        document.frmIns_Details.cmbFinancialYear.disabled=false;
        
           
        ClearAll();
    }
    else
    {
        alert("Record not Updated");
    }
}
function ClearAll()
{
    //document.getElementById("cmbAcc_UnitCode").value;
    document.getElementById("cmbasset").disabled=false;
    document.getElementById("cmbFinancialYear").disabled=false;
    document.getElementById("txtRemarks").value="";
    document.getElementById("txtOffice_Name").value="";
    document.getElementById("Amcfromdate").value="";
    document.getElementById("Amctodate").value="";
    document.getElementById("txtamc_amount").value="";
   document.getElementById("txtagr_no").value="";
   document.getElementById("Agrdate").value="";
        var d=document.getElementById("cmdAdd");
        d.style.display="block";
        var d1=document.getElementById("cmdUpdate");
        d1.style.display="none";
        var d3=document.getElementById("cmdDelete");
        d3.style.display="none";
}


function nullcheck()
{
   
   
    if(document.getElementById("cmbasset").value=="")
    {
        alert("Select Bank Name");
        document.getElementById("cmbBankId").focus();
        return false;
    }

    return true;
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

///////////////////////////////////////  Numbers only fields
function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {url
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

function officeCheck()
{

                if((document.frmIns_Details.unitid.value=="") || (document.frmIns_Details.unitid.value.length<=0))
                {
                    alert("Please Enter Office Id or Select ");
                    document.frmIns_Details.unitid.focus();
                    return false;
                    
                }


}

function checkoffice()
{
alert('hai');

            var officeid=document.frmIns_Details.txtOffice_Id.value;
            startwaiting(document.frmIns_Details) ;
            url="../../../../../ServletCheckAttachmentOffice.con?OfficeId="+officeid;
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {
                CheckOfficeResponse(req);                
            }
            req.send(null);
            
    

}   

function numbersonly1(e,t)
    {
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
     }

function checkmiccode()
{

         if(isNaN(document.frmIns_Details.txtMic_Code.value))
    {
            alert("Enter Numeric value");
            document.frmIns_Details.txtMic_Code.value="";
            document.frmIns_Details.txtMic_Code.focus();
            return false;
    }
     if((document.frmIns_Details.txtMic_Code.value.length!=0) && !( document.frmIns_Details.txtMic_Code.value.charAt(0)==String.fromCharCode(160) && document.frmIns_Details.txtMic_Code.value.length==1  ))
    {
           if((document.frmIns_Details.txtMic_Code.value.length!=9 || document.frmIns_Details.txtMic_Code.value==0) && document.frmIns_Details.txtMic_Code.value.length!=0 )
            {
                    alert("MicCode Start should be 9. Zero not allowed");
                    document.frmIns_Details.txtMic_Code.focus();
                    return false;
            }
    }
    return true;

}

function checkfax()
{
    if(isNaN(document.frmIns_Details.txtFax.value))
    {
            alert("Enter Numeric value");
            document.frmIns_Details.txtFax.value="";
            document.frmIns_Details.txtFax.focus();
            return false;
    }
     if((document.frmIns_Details.txtFax.value.length!=0) && !( document.frmIns_Details.txtFax.value.charAt(0)==String.fromCharCode(160) && document.frmIns_Details.txtFax.value.length==1  ))
    {
        if(document.frmIns_Details.txtFax.value.length <6  )
        {
                    alert("Phone No. Length atleast 6");
                    document.frmIns_Details.txtFax.focus();
                    return false;
        }
    }
    return true;
}

function checkphone()
{
    if(isNaN(document.frmIns_Details.txtPhone.value))
    {
            alert("Enter Numeric value");
            document.frmIns_Details.txtPhone.value="";
            document.frmIns_Details.txtPhone.focus();
            return false;
    }
     if((document.frmIns_Details.txtPhone.value.length!=0) && !( document.frmIns_Details.txtPhone.value.charAt(0)==String.fromCharCode(160) && document.frmIns_Details.txtPhone.value.length==1  ))
    {
        if(document.frmIns_Details.txtPhone.value.length <6  || document.frmIns_Details.txtPhone.value==0 )
        {
                    alert("Phone No. Length atleast 6.  Zero not allowed");
                    document.frmIns_Details.txtPhone.focus();
                    return false;
        }
    }
    return true;
}

function pinlength()
{

    var pincode=document.frmIns_Details.txtMic_Code.value;
    pincode=pincode.length;
    //alert(pincode);
    if(pincode<9)
    {
        alert("Please Enter Correct MICR code");
        document.frmIns_Details.txtPin_Code.focus();
        return false;
        
    }
    return true;


}
function check()
{
	alert("insidecheck");
var datefrom=document.frmIns_Details.Comnce_date.value;
//alert("the date from is"+datefrom);
var dateto=document.frmIns_Details.expiry_date.value;
//alert("the date to is"+dateto);
var flag=0;
var d = datefrom.split("/");
var e = dateto.split("/");
if(e[2]<d[2])
{
//alert("newcheque year should be greater than oldcheque Year");
alert("Expiry Date should be Greater than  Date of Comncement")
flag++;
    document.frmIns_Details.expiry_date.value="";
    document.frmIns_Details.expiry_date.focus();
}


if(e[2]==d[2])
{
  if (e[1]<d[1])
  {
   // alert("New Cheque month should be Greater than Oldcheque Month");
      alert("Expiry Date should be Greater than  Date of Comncement")
    flag++;
    document.frmIns_Details.expiry_date.value="";
    document.frmIns_Details.expiry_date.focus();
    }
  else if(e[1]==d[1])
   {
     if(e[0]<d[0])
     {
       alert("Expiry Date should be Greater than  Date of Comncement");
       flag++;
      document.frmIns_Details.expiry_date.value="";
      document.frmIns_Details.expiry_date.focus();
        }
      }
     }
    if(flag>0)
    {
     return false
     }

  }