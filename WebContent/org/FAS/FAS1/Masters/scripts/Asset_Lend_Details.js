
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
         var cmbAcc_UnitCode=document.frmAMC_Lend_Details.cmbAcc_UnitCode.value;
         var cmbOffice_code=document.frmAMC_Lend_Details.cmbOffice_code.value;
         
        // var cmbasset=document.getElementById("cmbasset").value;
         window_BankAccNumber= window.open("Assets_Lend_Details_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
    }

window.onunload=function()
{
if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}


function doParentBankAccNumbers(dateentry,majorclass,assetcode,location,transoffice,reason)
{
   //alert("inside parent");
        
            var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            var d2=document.getElementById("cmdDelete");
            d2.style.display="block";
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";  
            
            document.getElementById("cmbassetclass").value=majorclass;
            loadAssetCode();
            alert(assetcode);
            //document.getElementById("cmbasset").value=0;
            document.getElementById("cmbasset").value=assetcode;       
            document.getElementById("txt_date").value=dateentry;
            document.getElementById("txtphy_loc").options[document.getElementById("txtphy_loc").selectedIndex].text=location;
           
            document.getElementById("txtOffice_Name").options[document.getElementById("txtOffice_Name").selectedIndex].text=transoffice;
               
            document.getElementById("txtRemarks").value=reason;
            
    //document.getElementById("cmbasset").disabled=true;
  
}
function doFunction(Command,param)
{   

    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    var txt_date=document.getElementById("txt_date").value;
//    var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
    var cmbasset=document.getElementById("cmbasset").value;
    var cmbmajorclass=document.getElementById("cmbassetclass").value;
    var txtOffice_Name=document.getElementById("txtOffice_Name").options[document.getElementById("txtOffice_Name").selectedIndex].text;
    
    var phy_loc=document.getElementById("txtphy_loc").options[document.getElementById("txtphy_loc").selectedIndex].text;
    var txtRemarks=document.getElementById("txtRemarks").value;
   
         if(Command=="Add")
        {
            var flag=nullcheck();
            if(flag==true)
               {
                var url="../../../../../Assets_Lend_Details.view?Command=Add&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&txt_date="+txt_date+
                        "&cmbasset="+cmbasset+"&txtOffice_Name="+txtOffice_Name+"&cmbmajorclass="+cmbmajorclass+
                        "&phy_loc="+phy_loc+"&txtRemarks="+txtRemarks;
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
        else if(Command=="Delete")
        {
        
            if(confirm("Do You Really want to Delete it?"))
            {
                
               var flag=nullcheck();
               if(flag==true)
               {  
                  var url="../../../../../Assets_Lend_Details.view?Command=Delete&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&txt_date="+txt_date+
                        "&cmbasset="+cmbasset+"&txtOffice_Name="+txtOffice_Name+"&cmbmajorclass="+cmbmajorclass+
                        "&phy_loc="+phy_loc+"&txtRemarks="+txtRemarks;
             //      alert(url);
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
    	  // alert("inside update");
            var flag=nullcheck();
            if(flag==true)
             {
                var url="../../../../../Assets_Lend_Details.view?Command=Update&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&txt_date="+txt_date+
                        "&cmbasset="+cmbasset+"&txtOffice_Name="+txtOffice_Name+"&cmbmajorclass="+cmbmajorclass+
                        "&phy_loc="+phy_loc+"&txtRemarks="+txtRemarks;
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
            else if(Command=="loadassetcode")
            {
            	loadassetcode(baseResponse);
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
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var items=new Array();
    
    if(flag=="success")
    {
        alert("Record Updated");
        document.frmAMC_Lend_Details.cmbasset.disabled=false;
        document.frmAMC_Lend_Details.cmbFinancialYear.disabled=false;
        
           
        ClearAll();
    }
    else
    {
        alert("Record not Updated");
    }
}
function ClearAll()
{
    
    document.getElementById("cmbasset").disabled=true;
   document.getElementById("cmbassetclass").disabled=true;
    document.getElementById("txtRemarks").value="";
    document.getElementById("txtOffice_Name").value="";
    document.getElementById("txtphy_loc").value="";
    document.getElementById("txt_date").value="";
  
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

                if((document.frmAMC_Lend_Details.unitid.value=="") || (document.frmAMC_Lend_Details.unitid.value.length<=0))
                {
                    alert("Please Enter Office Id or Select ");
                    document.frmAMC_Lend_Details.unitid.focus();
                    return false;
                    
                }


}

function checkoffice()
{
//alert('hai');

            var officeid=document.frmAMC_Lend_Details.txtOffice_Id.value;
            startwaiting(document.frmAMC_Lend_Details) ;
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

         if(isNaN(document.frmAMC_Lend_Details.txtMic_Code.value))
    {
            alert("Enter Numeric value");
            document.frmAMC_Lend_Details.txtMic_Code.value="";
            document.frmAMC_Lend_Details.txtMic_Code.focus();
            return false;
    }
     if((document.frmAMC_Lend_Details.txtMic_Code.value.length!=0) && !( document.frmAMC_Lend_Details.txtMic_Code.value.charAt(0)==String.fromCharCode(160) && document.frmAMC_Lend_Details.txtMic_Code.value.length==1  ))
    {
           if((document.frmAMC_Lend_Details.txtMic_Code.value.length!=9 || document.frmAMC_Lend_Details.txtMic_Code.value==0) && document.frmAMC_Lend_Details.txtMic_Code.value.length!=0 )
            {
                    alert("MicCode Start should be 9. Zero not allowed");
                    document.frmAMC_Lend_Details.txtMic_Code.focus();
                    return false;
            }
    }
    return true;

}

function checkfax()
{
    if(isNaN(document.frmAMC_Lend_Details.txtFax.value))
    {
            alert("Enter Numeric value");
            document.frmAMC_Lend_Details.txtFax.value="";
            document.frmAMC_Lend_Details.txtFax.focus();
            return false;
    }
     if((document.frmAMC_Lend_Details.txtFax.value.length!=0) && !( document.frmAMC_Lend_Details.txtFax.value.charAt(0)==String.fromCharCode(160) && document.frmAMC_Lend_Details.txtFax.value.length==1  ))
    {
        if(document.frmAMC_Lend_Details.txtFax.value.length <6  )
        {
                    alert("Phone No. Length atleast 6");
                    document.frmAMC_Lend_Details.txtFax.focus();
                    return false;
        }
    }
    return true;
}

function checkphone()
{
    if(isNaN(document.frmAMC_Lend_Details.txtPhone.value))
    {
            alert("Enter Numeric value");
            document.frmAMC_Lend_Details.txtPhone.value="";
            document.frmAMC_Lend_Details.txtPhone.focus();
            return false;
    }
     if((document.frmAMC_Lend_Details.txtPhone.value.length!=0) && !( document.frmAMC_Lend_Details.txtPhone.value.charAt(0)==String.fromCharCode(160) && document.frmAMC_Lend_Details.txtPhone.value.length==1  ))
    {
        if(document.frmAMC_Lend_Details.txtPhone.value.length <6  || document.frmAMC_Lend_Details.txtPhone.value==0 )
        {
                    alert("Phone No. Length atleast 6.  Zero not allowed");
                    document.frmAMC_Lend_Details.txtPhone.focus();
                    return false;
        }
    }
    return true;
}

function pinlength()
{

    var pincode=document.frmAMC_Lend_Details.txtMic_Code.value;
    pincode=pincode.length;
    //alert(pincode);
    if(pincode<9)
    {
        alert("Please Enter Correct MICR code");
        document.frmAMC_Lend_Details.txtPin_Code.focus();
        return false;
        
    }
    return true;


}
function cheque(id){
	// alert("here it comes"+id);
	try
	{
	    var txttwad=document.getElementById("div_twad");
	    var txt_others=document.getElementById("div_others");
	  //  alert(txtCheque_DD2.id);
	   if(id=="T")
	   {
		  // alert("inside T")
		   txt_others.style.display="none";
		   txttwad.style.display="block";
	     
	   }
	   else
	   {
		   txt_others.style.display="block";
		   txttwad.style.display="none";
	   
	   //alert(document.getElementByID("txtCheque_DD3").value);
	  
	   }
	}
	catch(e){ }
	}
function loadAssetCode()
{
	//alert("inside loading the asset code****");
	var accunit_id=document.getElementById("cmbAcc_UnitCode").value;
	var accoff_id=document.getElementById("cmbOffice_code").value;
	
	var maj_code=document.getElementById("cmbassetclass").value;
	//alert("Major class code seleccted*********"+maj_code);
	 var url="../../../../../Assets_Lend_Details.view?Command=loadassetcode&majclasscode="+maj_code+"&accunit_id="+accunit_id+"&accoff_id="+accoff_id;
		  //alert(url);
		var req=getTransport();
		req.open("GET",url,true); 
		req.onreadystatechange=function()
		{
		handleResponse(req);
		}   
		     req.send(null);
}
function loadassetcode(baseResponse)
{
	//alert("loadassetcode");
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	   //alert(flag);
	   var cmbassetcode=document.getElementById("cmbasset").value;

	        if(flag=="success")
	        {
	             var cmbassetcode=document.getElementById("cmbasset");      // value assigned to same local variable name
	             
	             var items_id=new Array();
	             var items_name=new Array();
	                var cid=baseResponse.getElementsByTagName("assetid");
	                var cname=baseResponse.getElementsByTagName("assetName");
	                for(var k=0;k<cid.length;k++)
	                {
	                    items_id[k]=baseResponse.getElementsByTagName("assetid")[k].firstChild.nodeValue;
	                    items_name[k]=baseResponse.getElementsByTagName("assetName")[k].firstChild.nodeValue;
	                }
	              
	               clear_Combo(cmbassetcode);
	                
	                for(var k=0;k<items_id.length;k++)
	                {    
	                      var option=document.createElement("OPTION");
	                      option.text=items_id[k];
	                      option.value=items_id[k];
	                       try
	                      {
	                          cmbassetcode.add(option);
	                      }
	                      catch(errorObject)
	                      {
	                          cmbassetcode.add(option,null);
	                      }
	                }
	    		
	    }
	    else if(flag=="nodata")
	    	alert("No Asset code matching with the major code")
}
function clear_Combo(combo)
{
       //alert(combo.id);
        var cmbassetcode=document.getElementById(combo.id);   
        cmbassetcode.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select assetcode Code--";
        option.value="";
        try
        {
            cmbassetcode.add(option);
        }catch(errorObject)
        {
            cmbassetcode.add(option,null);
        }
}

