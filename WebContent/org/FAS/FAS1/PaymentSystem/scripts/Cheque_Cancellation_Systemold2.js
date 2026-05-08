//alert(document.getElementById("Crea_date").value);

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


function checkNull()
{
        if(document.getElementById("cmbAcc_UnitCode").value=="")
        {
            alert("Select the Account Unit code");
            //document.getElementById("txtAcc_HeadDesc").focus();
            return false;    
        }
        if(document.getElementById("cmbOffice_code").value=="")
        {
            alert("Select the Office Code");
            //document.getElementById("cmbOffice_code").focus();
            return false;
        }
        if(document.getElementById("txtCrea_date").value.length==0)
        {
            alert("Enter the Date of Creation");
            //document.getElementById("txtCrea_date").focus();
            return false;    
        }
        if(document.getElementById("txtReceipt_No").value=="")
        {
            alert("Select the Receipt Number");
            //document.getElementById("txtCrea_date").focus();
            return false;    
        }
    return true;
}



function clrForm()
{
   if(window.confirm("Do you want to clear ALL fields ?"))
 {
    call_clr();
 }
}

function call_clr()
{
    
 document.getElementById("txtReceipt_No").value="";  
 clearGeneral_Detail();
}
function clearGeneral_Detail()
{
    //document.getElementById("txtCB_Year").value="";
    //document.getElementById("txtCB_Month").value="";
    document.getElementById("txtCrea_date").value="";
    document.getElementById("doc_type").value="";
    document.getElementById("txtAmount").value="";
  //  document.getElementById("txtCheque_DD_NO").value="";
    document.getElementById("txtCheque_DD_date").value="";
    document.getElementById("txtCheque_DD_NO2").value="";
    document.getElementById("txtCheque_DD_date2").value="";
    document.getElementById("txtCheque_DD").value="";
    document.getElementById("txtAmount2").value="";
    document.getElementById("txtRemarks").value="";
   
  
}

function doFunction_voucher(Command,param)
{  //alert("dofuntion");
       var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
       var cmbOffice_code=document.getElementById("cmbOffice_code").value;
       var txtCB_Year=document.getElementById("txtCB_Year").value;
       var txtCB_Month=document.getElementById("txtCB_Month").value;
       var txtCrea_date= document.getElementById("txtCrea_date").value;
       var doc_type=document.getElementById("doc_type").value;
       var cheq_type=document.getElementById("txtCheque_DD").value;
       var txtReceipt_No=document.getElementById("txtReceipt_No").value;
       var txtamount=document.getElementById("txtAmount").value;
   //    var txtCheque_DD_NO=document.getElementById("txtCheque_DD_NO").value
//       var che_DD_no=document.getElementById("txtCheque_DD_NO").value
       var che_DD_date=document.getElementById("txtCheque_DD_date").value;
       var txtCheque_DD2=document.getElementById("txtCheque_DD2").value;
       var iss_che=document.getElementById("txtCheque_DD").value;
       var iss_che_no=document.getElementById("txtCheque_DD_NO2").value
       var iss_che_date=document.getElementById("txtCheque_DD_date2").value
       var iss_amt=document.getElementById("txtAmount2").value
       var remarks=document.getElementById("txtRemarks").value
       //var txtAUTHORIZED_TO='C';
        if(Command=="chequenodetails")
        {  
           //clearGeneral_Detail();
          //  if(txtCrea_date.length!=0)
            {
            var url="../../../../../Cheque_Cancellation_System.view?Command=chequenodetails&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbAcc_UnitCode="+
            cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
           // alert(url)
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse_voucher(req);
            }   
                    req.send(null);
            }         
        }
      /*  else if(Command=="load_Cheque_Details")
        {  
           //clearGeneral_Detail();
          //  if(txtCrea_date.length!=0)
          var  txtReceipt_No=document.getElementById("txtReceipt_No").value;
            {
            var url="../../../../../Cheque_Cancellation_System.view?Command=load_Cheque_Details&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
            cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&doc_type="+doc_type+"&txtReceipt_No="+txtReceipt_No;
         //   alert(url)
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse_voucher(req);
            }   
                    req.send(null);
            }         
        }*/
        else if(Command=="load_Voucher_Details")
        {  
           // clearGeneral_Detail();
            
            var  txtCheque_No=document.getElementById("txtCheque_No").value;
           // if(txtReceipt_No!="")
            {
            var url="../../../../../Cheque_Cancellation_System.view?Command=load_Voucher_Details&txtCheque_No="+txtCheque_No+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtCrea_date="+
            txtCrea_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
          //  alert(url)
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse_voucher(req);
            }   
                    req.send(null);
            }         
        }
       
        
}

/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
function handleResponse_voucher(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
           // alert(req.responseTEXT)
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
             
            if(Command=="chequenodetails")
            {
                chequenodetails(baseResponse);
            }
          /* else
            if (Command=="load_Cheque_Details")
            {
                 load_Cheque_Details(baseResponse);
            }*/
            
            else if(Command=="load_Voucher_Details")
            {
               
                load_Voucher_Details(baseResponse);
            } 
            else if(Command=="Add")
            {
                addRow(baseResponse);
            }
            
        }
    }
}

function  chequenodetails(baseResponse)
{
        
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
  var txtCheque_No=document.getElementById("txtCheque_No");
  if(flag=="success")
    {
           var items_id=new Array();
           var Cheq_No=baseResponse.getElementsByTagName("Cheq_No");
        
            for(var k=0;k<Cheq_No.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("Cheq_No")[k].firstChild.nodeValue;
                
            }
         
            txtCheque_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Cheque Number--";
            option.value="";
            try
            {
                txtCheque_No.add(option);
            }catch(errorObject)
            {
                txtCheque_No.add(option,null);
            }
            
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_id[k];
                  option.value=items_id[k];
                   try
                  {
                      txtCheque_No.add(option);
                  }
                  catch(errorObject)
                  {
                      txtCheque_No.add(option,null);
                  }
            }
    }
    else if(flag=="failure")
    {
            txtCheque_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Cheque Number--";
            option.value="";
            try
            {
                txtCheque_No.add(option);
            }catch(errorObject)
            {
                txtCheque_No.add(option,null);
            }
         alert("No Cheque Found");
    }
}
/*function  load_Cheque_Details(baseResponse)
{
        
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
  var txtCheque_DD_NO=document.getElementById("txtCheque_DD_NO");
  if(flag=="success")
    {
           var items_id=new Array();
           var che_DD_no=baseResponse.getElementsByTagName("che_DD_no");
            
            for(var k=0;k<che_DD_no.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("che_DD_no")[k].firstChild.nodeValue;
                
            }
         
            txtCheque_DD_NO.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Cheque Number--";
            option.value="";
            try
            {
                txtCheque_DD_NO.add(option);
            }catch(errorObject)
            {
                txtCheque_DD_NO.add(option,null);
            }
            
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_id[k];
                  option.value=items_id[k];
                   try
                  {
                      txtCheque_DD_NO.add(option);
                  }
                  catch(errorObject)
                  {
                      txtCheque_DD_NO.add(option,null);
                  }
            }
    }
    else if(flag=="failure")
    {
            txtCheque_DD_NO.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Cheque Number--";
            option.value="";
            try
            {
                txtCheque_DD_NO.add(option);
            }catch(errorObject)
            {
                txtCheque_DD_NO.add(option,null);
            }
         alert("No Voucher Found");
    }
}
*/

function load_Voucher_Details(baseResponse)
{   
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   // var  =document.getElementById("txtReceipt_No").value;
   // var txtCheque_DD_NO=document.getElementById("txtCheque_DD_NO");
    if(flag=="success")
    {
       
       
        
        document.getElementById("txtCrea_date").value=baseResponse.getElementsByTagName("transdate")[0].firstChild.nodeValue;
       document.getElementById("txtReceipt_No").value=baseResponse.getElementsByTagName("Rec_No")[0].firstChild.nodeValue;
        document.getElementById("txtAmount").value=baseResponse.getElementsByTagName("Total_amt")[0].firstChild.nodeValue;
        document.getElementById("txtCheque_DD_date").value=baseResponse.getElementsByTagName("che_DD_date")[0].firstChild.nodeValue;
       document.getElementById("txtAmount2").value=baseResponse.getElementsByTagName("Total_amt")[0].firstChild.nodeValue;
       document.getElementById("doc_type").value=baseResponse.getElementsByTagName("doctype")[0].firstChild.nodeValue;
       
       document.getElementById("txtCheque_DD").value=baseResponse.getElementsByTagName("cheq_dd")[0].firstChild.nodeValue;
       
       // document.getElementById("txtRemarks").value=baseResponse.getElementsByTagName("txtRemarks")[0].firstChild.nodeValue;
      
            
    }
    else if(flag=="failure")
    {
        alert("Loading failed")
    }
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
        
        //alert("here it comes");
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
function cheque(id){
// alert("here it comes"+id);
try
{
    var txtCheque_DD2=document.getElementById("div_cheque");
  //  alert(txtCheque_DD2.id);
   if(id=="N")
   {
    txtCheque_DD2.style.display="none";
     txtCheque_DD3.style.display="none";
   }
   else
   {
   txtCheque_DD2.style.display="block";
   txtCheque_DD3.style.display="block";
   /* if(document.getElementById("txtCheque_DD_NO2").value.length==0)
    {
        alert("New Cheque number not found");
        //document.getElementById("txtOperation_mode").focus();
        return false;
    }*/
   if(document.getElementById("txtCheque_DD_date2").value=="")
    {
        alert("select the date");
        document.getElementById("txtCheque_DD_date2").focus();
        return false;
    }
    if(document.getElementById("txtAmount2").value.length==0)
    {
        alert("Enter the new cheque amount");
        //document.getElementById("txtOperation_mode").focus();
        return false;
    }
   }
}
catch(e){ }
}


function check_amount(id){
var txtCheque_DD3=document.getElementById("txtCheque_DD3");
var txtAmount2=document.getElementById("txtAmount2");
//alert(id);
//alert(txtAmount2.value);
if(id=="N")
   {
    txtAmount2.disabled=true;
    
   }
else{
txtAmount2.disabled=false;
}
}
function check_chequeno()
{



}