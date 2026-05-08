
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
function ListHeads()
    {
       nullcheck();
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
         var cmbBankId=document.getElementById("cmbBankId").value;
         var cmbDistrict=document.getElementById("cmbDistrict").value;
         window_BankAccNumber= window.open("Bank_Branch_Details_List.jsp?cmbBankId="+cmbBankId,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
    }

window.onunload=function()
{
if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}


function doParentBankAccNumbers(bank,bankid,branch_id,officename,address1,address2,city,district,mic,phoneno,faxno,remark,status)
{
   
         
         
            var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            var d2=document.getElementById("cmdDelete");
            d2.style.display="block";
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";  
            
    //document.getElementById("cmbAcc_UnitCode").value=
   /* doFunction('getBranch',bankid);                     // This will be used to set the branch id
    Common_branchID=branch_id;*/
   // alert(bank);
    document.getElementById("cmbBankId").value=bank;
   // document.getElementById("cmbBankId").text=bank;

    document.getElementById("cmbDistrict").value=district;
   // document.getElementById("txtBranch_Name").value=BranchName;
    document.getElementById("BranchId").value=branch_id;
    document.getElementById("txtOffice_Name").value=officename;
    document.getElementById("txtOffice_Address1").value=address1;
    document.getElementById("txtOffice_Address2").value=address2;
    document.getElementById("txtOffice_City").value=city;
    document.getElementById("txtMic_Code").value=mic;
    document.getElementById("txtRemarks").value=remark;
    document.getElementById("txtPhone").value=phoneno;
    document.getElementById("txtFax").value=faxno;
   // document.getElementById("txtPhone").value=phone;
   // document.getElementById("txtstatus").value=status;
    //document.getElementById("cmbBankId").disabled=true;
      if(status=="L")
			   document.frmBank_Branch_Details.txtstatus[0].checked=true;
			  else
		           document.frmBank_Branch_Details.txtstatus[1].checked=true;
   // document.getElementById("cmbDistrict").disabled=true;
    
   // if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
   // if this closed here it won't get result from servlet for bank's branches, so it's closed after get branches
}
function doFunction(Command,param)
{   

    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbBankId=document.getElementById("cmbBankId").value;
    var BranchId=document.getElementById("BranchId").value;
    var txtOffice_Name=document.getElementById("txtOffice_Name").value;
   // var txtBranch_Name=document.getElementById("txtBranch_Name").value;
    var cmbDistrict=document.getElementById("cmbDistrict").value;
    
    var txtOffice_Address1=document.getElementById("txtOffice_Address1").value;
    var txtOffice_Address2=document.getElementById("txtOffice_Address2").value;
    var txtOffice_City=document.getElementById("txtOffice_City").value;
    var txtMic_Code=document.getElementById("txtMic_Code").value;
    var txtRemarks=document.getElementById("txtRemarks").value;
    var txtPhone=document.getElementById("txtPhone").value;
    var txtFax=document.getElementById("txtFax").value;
   var status  = "";
		    if(document.frmBank_Branch_Details.txtstatus[0].checked==true)
		    	status="L";
		    else 
		    	status="C";
         if(Command=="Add")
        {
            var flag=nullcheck();
            if(flag==true)
               {
                var url="../../../../../Bank_Branch_Details.view?Command=Add&cmbBankId="+cmbBankId+
                        "&BranchId="+BranchId+"&txtOffice_Address1="+txtOffice_Address1+"&txtOffice_Name="+txtOffice_Name+
                        "&txtOffice_Address2="+txtOffice_Address2+"&txtOffice_City="+txtOffice_City+"&cmbDistrict="+cmbDistrict+
                        "&txtMic_Code="+txtMic_Code+"&txtRemarks="+txtRemarks+"&txtPhone="+txtPhone+"&txtFax="+txtFax+"&status="+status;
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
        else if(Command=="Cancel")
        {
        
            if(confirm("Do You Really want to Cancel it?"))
            {
                
               var flag=nullcheck();
               if(flag==true)
               {  
                  var url="../../../../../Bank_Branch_Details.view?Command=Cancel&cmbBankId="+cmbBankId+"&BranchId="+BranchId;
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
            var flag=nullcheck();
            if(flag==true)
             {
                var url="../../../../../Bank_Branch_Details.view?Command=Update&cmbBankId="+cmbBankId+
                        "&BranchId="+BranchId+"&txtOffice_Name="+txtOffice_Name+"&txtOffice_Address1="+txtOffice_Address1+
                        "&txtOffice_Address2="+txtOffice_Address2+"&txtOffice_City="+txtOffice_City+"&cmbDistrict="+cmbDistrict+
                        "&txtMic_Code="+txtMic_Code+"&txtRemarks="+txtRemarks+"&txtPhone="+txtPhone+"&txtFax="+txtFax+"&status="+status;
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
           
         /*   if(Command=="getBranch")
            {
                getBranch(baseResponse);
            }
           
            else */
            if(Command=="Add")
            {
                addRow(baseResponse);
            }
            else if(Command=="Cancel")
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
        alert("Records Canceled from database");
         ClearAll();
    }
    else
    {
        alert("Record not Canceled from database");
    }
}  

function UpdateRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var items=new Array();
    
    if(flag=="success")
    {
        alert("Record Updated");
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
   // document.getElementById("cmbBankId").value="";
    document.getElementById("cmbDistrict").value="";
    document.getElementById("BranchId").value="";
  //  document.getElementById("txtBranch_Name").value="";
    document.getElementById("txtOffice_Address1").value="";
    document.getElementById("txtOffice_Name").value="";
    document.getElementById("txtOffice_Address2").value="";
    document.getElementById("txtOffice_City").value="";
    document.getElementById("txtMic_Code").value="";
    //document.getElementById("remarks").value=remarks;
    document.getElementById("txtRemarks").value="";
    document.getElementById("txtPhone").value="";
    document.getElementById("txtFax").value="";
   // document.getElementById("txtBankAccountNo").disabled=false;
   // document.getElementById("cmbBankId").disabled=false;
   document.getElementById("cmbDistrict").disabled=false;
    //document.getElementById("cmbBankAcc_type").disabled=false;
    //document.getElementById("cmbOperation_mode").disabled=false;
        var d=document.getElementById("cmdAdd");
        d.style.display="block";
        var d1=document.getElementById("cmdUpdate");
        d1.style.display="none";
        var d3=document.getElementById("cmdDelete");
        d3.style.display="none";
}


function nullcheck()
{
   
   
    if(document.getElementById("cmbBankId").value=="")
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

function officeCheck()
{

                if((document.frmBank_Branch_Details.unitid.value=="") || (document.frmBank_Branch_Details.unitid.value.length<=0))
                {
                    alert("Please Enter Office Id or Select ");
                    document.frmBank_Branch_Details.unitid.focus();
                    return false;
                    
                }


}

function checkoffice()
{
//alert('hai');

            var officeid=document.frmBank_Branch_Details.txtOffice_Id.value;
            startwaiting(document.frmBank_Branch_Details) ;
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

         if(isNaN(document.frmBank_Branch_Details.txtMic_Code.value))
    {
            alert("Enter Numeric value");
            document.frmBank_Branch_Details.txtMic_Code.value="";
            document.frmBank_Branch_Details.txtMic_Code.focus();
            return false;
    }
     if((document.frmBank_Branch_Details.txtMic_Code.value.length!=0) && !( document.frmBank_Branch_Details.txtMic_Code.value.charAt(0)==String.fromCharCode(160) && document.frmBank_Branch_Details.txtMic_Code.value.length==1  ))
    {
           if((document.frmBank_Branch_Details.txtMic_Code.value.length!=9 || document.frmBank_Branch_Details.txtMic_Code.value==0) && document.frmBank_Branch_Details.txtMic_Code.value.length!=0 )
            {
                    alert("MicCode Start should be 9. Zero not allowed");
                    document.frmBank_Branch_Details.txtMic_Code.focus();
                    return false;
            }
    }
    return true;

}

function checkfax()
{
    if(isNaN(document.frmBank_Branch_Details.txtFax.value))
    {
            alert("Enter Numeric value");
            document.frmBank_Branch_Details.txtFax.value="";
            document.frmBank_Branch_Details.txtFax.focus();
            return false;
    }
     if((document.frmBank_Branch_Details.txtFax.value.length!=0) && !( document.frmBank_Branch_Details.txtFax.value.charAt(0)==String.fromCharCode(160) && document.frmBank_Branch_Details.txtFax.value.length==1  ))
    {
        if(document.frmBank_Branch_Details.txtFax.value.length <6  )
        {
                    alert("Phone No. Length atleast 6");
                    document.frmBank_Branch_Details.txtFax.focus();
                    return false;
        }
    }
    return true;
}

function checkphone()
{
    if(isNaN(document.frmBank_Branch_Details.txtPhone.value))
    {
            alert("Enter Numeric value");
            document.frmBank_Branch_Details.txtPhone.value="";
            document.frmBank_Branch_Details.txtPhone.focus();
            return false;
    }
     if((document.frmBank_Branch_Details.txtPhone.value.length!=0) && !( document.frmBank_Branch_Details.txtPhone.value.charAt(0)==String.fromCharCode(160) && document.frmBank_Branch_Details.txtPhone.value.length==1  ))
    {
        if(document.frmBank_Branch_Details.txtPhone.value.length <6  || document.frmBank_Branch_Details.txtPhone.value==0 )
        {
                    alert("Phone No. Length atleast 6.  Zero not allowed");
                    document.frmBank_Branch_Details.txtPhone.focus();
                    return false;
        }
    }
    return true;
}

function pinlength()
{

    var pincode=document.frmBank_Branch_Details.txtMic_Code.value;
    pincode=pincode.length;
    //alert(pincode);
    if(pincode<9)
    {
        alert("Please Enter Correct MICR code");
        document.frmBank_Branch_Details.txtPin_Code.focus();
        return false;
        
    }
    return true;


}