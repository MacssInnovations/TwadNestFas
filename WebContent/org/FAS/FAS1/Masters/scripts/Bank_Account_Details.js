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

var window_BankAccNumber;
function ListHeads()
    {
    
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
         if(document.getElementById("cmbAcc_UnitCode").value!="" && document.getElementById("cmbBankId").value=="" && document.getElementById("cmbBranchId").value=="") 
         {
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             window_BankAccNumber= window.open("Bank_Account_Number_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode,"mywindow1","resizable=YES, scrollbars=yes"); 
             window_BankAccNumber.moveTo(250,250);    
         }
         else if(document.getElementById("cmbAcc_UnitCode").value!="" && document.getElementById("cmbBankId").value!="" && document.getElementById("cmbBranchId").value=="")
         {
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbBankId=document.getElementById("cmbBankId").value;
             
             window_BankAccNumber= window.open("Bank_Account_Number_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbBankId="+cmbBankId,"mywindow1","resizable=YES, scrollbars=yes"); 
             window_BankAccNumber.moveTo(250,250);   
         }
         else if(document.getElementById("cmbAcc_UnitCode").value!="" && document.getElementById("cmbBankId").value!="" && document.getElementById("cmbBranchId").value!="" && document.getElementById("cmbBankAcc_type").value=="")
         {
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbBankId=document.getElementById("cmbBankId").value;
             var cmbBranchId=document.getElementById("cmbBranchId").value;
             
             window_BankAccNumber= window.open("Bank_Account_Number_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbBankId="+cmbBankId+"&cmbBranchId="+cmbBranchId,"mywindow1","resizable=YES, scrollbars=yes"); 
             window_BankAccNumber.moveTo(250,250); 
         }
         else if(document.getElementById("cmbAcc_UnitCode").value!="" && document.getElementById("cmbBankId").value!="" && document.getElementById("cmbBranchId").value!="" && document.getElementById("cmbBankAcc_type").value!="")
         {
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbBankId=document.getElementById("cmbBankId").value;
             var cmbBranchId=document.getElementById("cmbBranchId").value;
             //alert("cmbBranchId>>>"+cmbBranchId);
             var cmbBankAcc_type=document.getElementById("cmbBankAcc_type").value;
             
             window_BankAccNumber= window.open("Bank_Account_Number_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbBankId="+cmbBankId+"&cmbBranchId="+cmbBranchId+"&cmbBankAcc_type="+cmbBankAcc_type,"mywindow1","resizable=YES, scrollbars=yes"); 
             window_BankAccNumber.moveTo(250,250); 
         }

         
    }

window.onunload=function()
{
if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}

function doParentBankAccNumbers(bank_AccNo,branch_id,bankid,bank_acc_type,operID,open_date,init_dep_amt,bal_date,bal_amt,remark,status)
{
	//alert(bank_AccNo + branch_id + bankid + bank_acc_type + operID + open_date + init_dep_amt + bal_date + bal_amt + remark + status);
            var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            var d2=document.getElementById("cmdDelete");
            d2.style.display="block";
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";  
            
    //document.getElementById("cmbAcc_UnitCode").value=
    doFunction('getBranch',bankid);                     // This will be used to set the branch id
    Common_branchID=branch_id;
    document.getElementById("cmbBankId").value=bankid;
    document.getElementById("cmbBranchId").value=branch_id;
    document.getElementById("cmbBankAcc_type").value=bank_acc_type;
    document.getElementById("cmbOperation_mode").value=operID;
    document.getElementById("txtBankAccountNo").value=bank_AccNo;
    if(txtOpening_date!="--")
    document.getElementById("txtOpening_date").value=open_date;
    if(txtDepositAmount!="--")
    document.getElementById("txtDepositAmount").value=init_dep_amt;
    if(txtBalance_date!="--")
    document.getElementById("txtBalance_date").value=bal_date;
    if(txtBalanceAmount!="--")
    document.getElementById("txtBalanceAmount").value=bal_amt;
    if(txtRemarks!="--")
    document.getElementById("txtRemarks").value=remark;
    
    if(status=="Y")
     {
      document.frmBank_Account_Details.Account_Staus[0].checked=true;
      document.frmBank_Account_Details.Account_Staus[1].checked=false;
     }
     
    if(status=="N")
    {
      document.frmBank_Account_Details.Account_Staus[0].checked=false;
      document.frmBank_Account_Details.Account_Staus[1].checked=true;
    }     
    
    document.getElementById("txtBankAccountNo").disabled=true;
    document.getElementById("cmbBankId").disabled=true;
    document.getElementById("cmbBranchId").disabled=true;
    document.getElementById("cmbBankAcc_type").disabled=true;
 
 
    //document.getElementById("cmbOperation_mode").disabled=true;
    
   // if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
   // if this closed here it won't get result from servlet for bank's branches, so it's closed after get branches
}

function type()
{
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	
	var url="../../../../../Bank_Account_Details.view?Command=getType&cmbAcc_UnitCode="+cmbAcc_UnitCode;
     //alert(url);
     var req=getTransport();
     req.open("GET",url,true); 
     req.onreadystatechange=function()
     {
        handleResponse(req);
     }   
     req.send(null);
}



function doFunction(Command,param)
{   

    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbBankId=document.getElementById("cmbBankId").value;
    var cmbBranchId=document.getElementById("cmbBranchId").value;
    var cmbBankAcc_type=document.getElementById("cmbBankAcc_type").value;
    var cmbOperation_mode=document.getElementById("cmbOperation_mode").value;
    var txtBankAccountNo=document.getElementById("txtBankAccountNo").value;
    var txtOpening_date=document.getElementById("txtOpening_date").value;
    var txtDepositAmount=document.getElementById("txtDepositAmount").value;
    var txtBalance_date=document.getElementById("txtBalance_date").value;
    var txtBalanceAmount=document.getElementById("txtBalanceAmount").value;    
    var txtRemarks=document.getElementById("txtRemarks").value; 
    var txtClosed_date=document.getElementById("txtClosed_date").value;
    
    var Account_Status;    
     if(document.frmBank_Account_Details.Account_Staus[0].checked==true)
       Account_Status ="Y";
     else 
       Account_Status="N";       
      
    if(Command=="getBranch")
        {  
            cmbBankId=param;
            if(cmbBankId!="")
            {
                var url="../../../../../Bank_Account_Details.view?Command=getBranch&cmbBankId="+cmbBankId;
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                req.send(null);
            }         
        }
         else if(Command=="Add")
        {
            var flag=nullcheck();
            var url="../../../../../Bank_Account_Details.view?Command=Add&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbBankId="+cmbBankId+
            "&cmbBranchId="+cmbBranchId+"&cmbBankAcc_type="+cmbBankAcc_type+"&cmbOperation_mode="+cmbOperation_mode+
            "&txtBankAccountNo="+txtBankAccountNo+"&txtOpening_date="+txtOpening_date+"&txtDepositAmount="+txtDepositAmount+
            "&txtBalance_date="+txtBalance_date+"&txtBalanceAmount="+txtBalanceAmount+"&txtRemarks="+txtRemarks+"&Account_Status="+Account_Status+
            "&txtClosed_date="+txtClosed_date;
            var req = getTransport();
            if(flag==true)
            {
            if (Account_Status == 'N') {
				if (document.getElementById("txtClosed_date").value == ""
						|| document.getElementById("txtClosed_date").value == null) {
				
					alert("Enter Closed Date ... ");
					document.getElementById("txtClosed_date").focus();
					
				} else{
					req.open("GET", url, true);	
				}
			}else{
				req.open("GET", url, true);
			}
		
			req.onreadystatechange = function() {
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
                    var url="../../../../../Bank_Account_Details.view?Command=Delete&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbBankId="+cmbBankId+
                        "&cmbBranchId="+cmbBranchId+"&cmbBankAcc_type="+cmbBankAcc_type+"&cmbOperation_mode="+cmbOperation_mode+
                        "&txtBankAccountNo="+txtBankAccountNo+"&txtOpening_date="+txtOpening_date+"&txtDepositAmount="+txtDepositAmount+
                        "&txtBalance_date="+txtBalance_date+"&txtBalanceAmount="+txtBalanceAmount+"&txtRemarks="+txtRemarks+ "&txtClosed_date="+txtClosed_date;
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
                var url="../../../../../Bank_Account_Details.view?Command=Update&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbBankId="+cmbBankId+
                        "&cmbBranchId="+cmbBranchId+"&cmbBankAcc_type="+cmbBankAcc_type+"&cmbOperation_mode="+cmbOperation_mode+
                        "&txtBankAccountNo="+txtBankAccountNo+"&txtOpening_date="+txtOpening_date+"&txtDepositAmount="+txtDepositAmount+
                        "&txtBalance_date="+txtBalance_date+"&txtBalanceAmount="+txtBalanceAmount+"&txtRemarks="+txtRemarks+"&Account_Status="+Account_Status;
                     //  "&txtClosed_date="+txtClosed_date;  
                alert("URL>>>>"+url);
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
           
            if(Command=="getBranch")
            {
                getBranch(baseResponse);
            }
           
            else if(Command=="Add")
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
            else if(Command=="getType")
            {
            	getType(baseResponse);
            }
        }
    }
}
function getType(baseResponse)
{
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;	
	if(flag=="success")
    {
		//alert("flag>>>"+flag);
		var items_id=new Array();
        var items_name=new Array();
        var type=baseResponse.getElementsByTagName("type");
        //alert("Type>>"+type.length);
        //var typedesc=baseResponse.getElementsByTagName("typedesc");
        
        var type_id=baseResponse.getElementsByTagName("type");
        var type_desc=baseResponse.getElementsByTagName("typedesc");
        var cmbBankAcc_type=document.getElementById("cmbBankAcc_type"); 
        
        for(var i=0;i<type_id.length;i++)
        {
          var typeid=type_id.item(i).firstChild.nodeValue;
          var typedesc=type_desc.item(i).firstChild.nodeValue;
          var option=document.createElement("OPTION");
          option.text=typedesc;
          option.value=typeid;             
              try
              {
            	  cmbBankAcc_type.add(option);
              }
              catch(errorObject)
              {
            	  cmbBankAcc_type.add(option,null);
              }
        }
        //var opid=baseResponse.getElementsByTagName("Opr_id");
        var Opr_id=baseResponse.getElementsByTagName("Opr_id");
       // alert("Opr_id>>>"+Opr_id.length);
        var Mode_id=baseResponse.getElementsByTagName("Mode");
        //alert("Mode_id>>>"+Mode_id);
        var cmbOperation_mode=document.getElementById("cmbOperation_mode"); 
        for(i=0;i<Opr_id.length;i++)
        {
          var Oprid=Opr_id.item(i).firstChild.nodeValue;
          var Mode=Mode_id.item(i).firstChild.nodeValue;
          option=document.createElement("OPTION");
          option.text=Mode;
          option.value=Oprid;             
              try
              {
            	  cmbOperation_mode.add(option);
              }
              catch(errorObject)
              {
            	  cmbOperation_mode.add(option,null);
              }
        }
//        for(var k=0;k<type.length;k++)
//        {
//            items_id[k]=baseResponse.getElementsByTagName("type")[k].firstChild.nodeValue;
//            items_name[k]=baseResponse.getElementsByTagName("typedesc")[k].firstChild.nodeValue;
//            
//        }
//        for(var i=0;i<type.length;i++)
//        {
//        var option=document.createElement("OPTION");
//        option.text=items_name[i];
//        option.value=items_id[i];
//        alert("items_id>>"+items_id[i]);
//        alert("items_name>>"+items_name[i]);
//         try
//        {
//      	   cmbBankAcc_type.add(option);
//        }
//        catch(errorObject)
//        {
//      	  cmbBankAcc_type.add(option,null);
//        }
//        }
//        document.getElementById("cmbBankAcc_type").value=cmbBankAcc_type;
       // alert("cmbBankAcc_type>>>>>>"+cmbBankAcc_type);
        
    }
}

function getBranch(baseResponse)
{
    
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
var cmbBranchId=document.getElementById("cmbBranchId").value;
    if(flag=="success")
    {
         var cmbBranchId=document.getElementById("cmbBranchId");
         
         var items_id=new Array();
         var items_name=new Array();

            var cid=baseResponse.getElementsByTagName("cid");
            var cname=baseResponse.getElementsByTagName("cname");
            for(var k=0;k<cid.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
                items_name[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
            }
         
            clear_Combo(cmbBranchId);
            
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_name[k];
                  option.value=items_id[k];
                   try
                  {
                      cmbBranchId.add(option);
                  }
                  catch(errorObject)
                  {
                      cmbBranchId.add(option,null);
                  }
            }
            document.getElementById("cmbBranchId").value=Common_branchID;
            Common_branchID="";                 // it should be cleared to avoid the last value exist in this variable
            if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
    }
    else if(flag=="failure")
    {
        alert("No Branch found");
         var cmbBranchId=document.getElementById("cmbBranchId");
         clear_Combo(cmbBranchId);
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
    document.getElementById("cmbBankId").value="";
    clear_Combo(document.getElementById("cmbBranchId"));
    document.getElementById("cmbBankAcc_type").value="";
    document.getElementById("cmbOperation_mode").value="";
    document.getElementById("txtBankAccountNo").value="";
    document.getElementById("txtOpening_date").value="";
    document.getElementById("txtDepositAmount").value="";
    document.getElementById("txtBalance_date").value="";
    document.getElementById("txtBalanceAmount").value="";
    document.getElementById("txtRemarks").value="";
    document.getElementById("txtClosed_date").value="";
    
 document.getElementById("CloDiv").style.display="none";
    document.frmBank_Account_Details.Account_Staus[0].checked=true;
    document.frmBank_Account_Details.Account_Staus[1].checked=false;
    
    document.getElementById("txtBankAccountNo").disabled=false;
    document.getElementById("cmbBankId").disabled=false;
    document.getElementById("cmbBranchId").disabled=false;
    document.getElementById("cmbBankAcc_type").disabled=false;
    //document.getElementById("cmbOperation_mode").disabled=false;
        var d=document.getElementById("cmdAdd");
        d.style.display="block";
        var d1=document.getElementById("cmdUpdate");
        d1.style.display="none";
        var d3=document.getElementById("cmdDelete");
        d3.style.display="none";
}
function clear_Combo(combo)
{
        //alert(combo.id)
        var combo_Id=document.getElementById(combo.id);   
        combo_Id.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select Branch Name--";
        option.value="";
        try
        {
            combo_Id.add(option);
        }catch(errorObject)
        {
            combo_Id.add(option,null);
        }
}

function nullcheck()
{
    if(document.getElementById("cmbAcc_UnitCode").value.length==0)
    {
        alert("Accounting Unit code not found");
        //document.getElementById("cmbAcc_UnitCode").focus();
        return false;
    }
   
    if(document.getElementById("cmbBankId").value=="")
    {
        alert("Select Bank Name");
        document.getElementById("cmbBankId").focus();
        return false;
    }
    if(document.getElementById("cmbBranchId").value=="")
    {
        alert("Select Branch name");
        document.getElementById("cmbBranchId").focus();
        return false;
    }
    if(document.getElementById("cmbBankAcc_type").value=="")
    {
        alert("Select Account type");
        document.getElementById("cmbBankAcc_type").focus();
        return false;
    }
   if(document.getElementById("txtBankAccountNo").value=="")
    {
        alert("Enter the Account number");
        document.getElementById("txtBankAccountNo").focus();
        return false;
    }
    if(document.getElementById("cmbOperation_mode").value=="")
    {
        alert("Select Operational mode");
        document.getElementById("cmbOperation_mode").focus();
        return false;
    }
    
   /*
   if(document.getElementById("txtOpening_date").value=="")
    {
        alert("Enter the opening date");
        document.getElementById("txtOpening_date").focus();
        return false;
    }
    if(document.getElementById("txtDepositAmount").value=="")
    {
        alert("Enter the initial deposit amount");
        document.getElementById("txtDepositAmount").focus();
        return false;
    }
    if(document.getElementById("txtBalance_date").value=="")
    {
        alert("Enter the as on date for balance date");
        document.getElementById("txtBalance_date").focus();
        return false;
    }
    if(document.getElementById("txtBalanceAmount").value=="")
    {
        alert("Enter the Balance amount");
        document.getElementById("txtBalanceAmount").focus();
        return false;
    }
    */
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
