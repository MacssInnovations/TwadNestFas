//alert("js");
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
         var cmbAcc_UnitCode=document.frmNum_OB_Details.cmbAcc_UnitCode.value;
         var cmbOffice_code=document.frmNum_OB_Details.cmbOffice_code.value;
         var financial_year = document.getElementById("cmbFinancialYear").value;
//         alert(financial_year);
        // var cmbasset=document.getElementById("cmbasset").value;
         window_BankAccNumber= window.open("Assets_Numerical_OB_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&financial_year="+financial_year,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
    }

window.onunload=function()
{
if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}


function doParentBankAccNumbers(dateentry,majorclass,cmbmajorclass,assetcode,location,aliascode,status,qtydate,remarks)
{
  // alert("inside parent");
        // alert(cmbmajorclass);
         
            var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            var d2=document.getElementById("cmdDelete");
            d2.style.display="block";
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";  
            document.getElementById("txtdate_entry").value=dateentry;
            document.getElementById("txtcheck").value=status;
           
            document.getElementById("txtAsset_alias").value=aliascode;
            document.getElementById("txtOffice_Name").options[document.getElementById("txtOffice_Name").selectedIndex].text=location;
            document.getElementById("txtassetcode").value=assetcode;
            document.getElementById("cmbassetclass").options[document.getElementById("cmbassetclass").selectedIndex].text=majorclass;
            document.getElementById("cmbassetclass").value=cmbmajorclass;
            document.getElementById("quantity_date").value=qtydate;
            document.getElementById("txtRemarks").value=remarks;
            
   // document.getElementById("cmbasset").disabled=true;
  
}
function doFunction(Command,param)
{   
//alert("inside dofunction");
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    var financial_year = document.getElementById("cmbFinancialYear").value;
    var txtdate_entry=document.getElementById("txtdate_entry").value;
    var quantity_date=document.getElementById("quantity_date").value;
    var cmbasset=document.getElementById("txtassetcode").value;
    var cmbmajorclass=document.getElementById("cmbassetclass").value;
    var txtAsset_alias=document.getElementById("txtAsset_alias").value;
    var txtOffice_Name=document.getElementById("txtOffice_Name").options[document.getElementById("txtOffice_Name").selectedIndex].text;
    
    var txtcheck=document.getElementById("txtcheck").value;
    var txtRemarks=document.getElementById("txtRemarks").value;
   
         if(Command=="Add")
        {  
        
          //  var flag=nullcheck();
         //  if(flag==true)
       //        {
                var url="../../../../../Assets_NumericalAC_OB_entry?Command=Add&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&financial_year="+financial_year+"&txtdate_entry="+txtdate_entry+
                        "&txtOffice_Name="+txtOffice_Name+"&cmbmajorclass="+cmbmajorclass+"&quantity_date="+quantity_date+
                        "&txtcheck="+txtcheck+"&txtRemarks="+txtRemarks+"&txtAsset_alias="+txtAsset_alias;
         //      alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
          //      }
        }
        else if(Command=="Delete")
        {
        
            if(confirm("Do You Really want to Delete it?"))
            {
                
               var flag=nullcheck();
               if(flag==true)
               {  
                  var url="../../../../../Assets_NumericalAC_OB_entry?Command=Delete&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&cmbasset="+cmbasset;
                //   alert(url);
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
    	  // alert("inside update"+cmbmajorclass);
           //var flag=nullcheck();
         //  if(flag==true)
         //   {
                var url="../../../../../Assets_NumericalAC_OB_entry?Command=Update&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&financial_year="+financial_year+"&txtdate_entry="+txtdate_entry+
                        "&cmbasset="+cmbasset+"&txtOffice_Name="+txtOffice_Name+"&cmbmajorclass="+cmbmajorclass+"&quantity_date="+quantity_date+
                        "&txtcheck="+txtcheck+"&txtRemarks="+txtRemarks+"&txtAsset_alias="+txtAsset_alias;
               // alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
          //    }
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
   // alert(flag);
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
    var flag=baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
    var items=new Array();
    
    if(flag=="success")
    {
        alert("Record Updated");
        document.frmNum_OB_Details.cmbassetclass.disabled=false;
        document.frmNum_OB_Details.cmbFinancialYear.disabled=false;
        document.frmNum_OB_Details.txtOffice_Name.disabled=false;
        
           
        ClearAll();
    }
    else
    {
        alert("Record not Updated");
    }
}

function ClearAll()
{
        document.frmNum_OB_Details.cmbassetclass.disabled=false;
        document.frmNum_OB_Details.cmbFinancialYear.disabled=false;
        document.frmNum_OB_Details.txtOffice_Name.disabled=false;
        document.getElementById("txtdate_entry").value="";
        document.getElementById("quantity_date").value="";
        document.getElementById("txtassetcode").value="";
        document.getElementById("cmbassetclass").value="";
        document.getElementById("txtAsset_alias").value="";
        document.getElementById("txtRemarks").value="";
        document.getElementById("txtOffice_Name").value="";
        document.getElementById("txtdate_entry").value="";
  
        var d=document.getElementById("cmdAdd");
        d.style.display="block";
        var d1=document.getElementById("cmdUpdate");
        d1.style.display="none";
        var d3=document.getElementById("cmdDelete");
        d3.style.display="none";
}


function nullcheck()
{
   
   var accounting_unit_id=document.frmNum_OB_Details.cmbAcc_UnitCode;
		if(accounting_unit_id.value=="")
		{ 
		     alert("Please Select the 'Accounting Unit'");
		     accounting_unit_id.focus();
		     return false;
		}

		var accounting_unit_office_id = document.frmNum_OB_Details.cmbOffice_code;
		if(accounting_unit_office_id.value=="")
		{ 
		     alert("Please Select the 'Accounting Office'");
		     accounting_unit_office_id.focus();
		     return false;
		}

		var financial_year = document.frmNum_OB_Details.cmbFinancialYear;
		if(financial_year.value=="")
		{ 
		     alert("Please select the 'Financial Year'");
		     financial_year.focus();
		     return false;
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
     

/////////////////////// exit  function

function exit()
{
        self.close();

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



