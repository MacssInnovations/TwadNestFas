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
         var cmbAcc_UnitCode=document.frmIssue_Asset.cmbAcc_UnitCode.value;
         var cmbOffice_code=document.frmIssue_Asset.cmbOffice_code.value;
         var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
         var CB_year = document.getElementById("txtCB_Year").value;
         var CB_month = document.getElementById("txtCB_Month").value;
         
        // var cmbasset=document.getElementById("cmbasset").value;
         window_BankAccNumber= window.open("Issue_Assets_Numerical_OB_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cmbFinancialYear="+cmbFinancialYear+"&txtCB_Year="+CB_year+"&txtCB_Month="+CB_month,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
    }

window.onunload=function()
{
if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}


function doParentBankAccNumbers(majorclasscode,majorclass,assetcode,assetdesc,issueddate,levelid,issuedoffice,quantity,jvno,jvdate,remarks,officecode,value1)
{
  
            var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            var d2=document.getElementById("cmdDelete");
            d2.style.display="block";
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";  
          //  document.getElementById("cmbassetclass").options[document.getElementById("cmbassetclass").selectedIndex].text=majorclass;
            document.getElementById("cmbassetclass").value=majorclasscode;
            document.getElementById("cmbassetcode").options[document.getElementById("cmbassetcode").selectedIndex].text=assetcode;
            document.getElementById("cmbassetcode").options[document.getElementById("cmbassetcode").selectedIndex].value=assetcode;
            document.getElementById("txtassetdesc").value=assetdesc;
            //document.getElementById("txtCB_Year").value=cashbookyr;
            //document.getElementById("txtCB_Month").value=cashbookmon;
            document.getElementById("issue_date").value=issueddate;
           // alert("levelid "+levelid);
            if(levelid=='D')
            	{
            	document.frmIssue_Asset.levelid[0].checked="true";
            	}
            else if(levelid=='C')
            	{
            	document.frmIssue_Asset.levelid[1].checked="true";
            	}
            else if(levelid=='OC')
            	{
            	document.frmIssue_Asset.levelid[2].checked="true";
            	}
            
            //document.getElementById("levelid").value=levelid;
            document.getElementById("txtqtyissued").value=quantity;
            document.getElementById("txtvalueissued").value=value1;
            document.getElementById("txtjournal_date").value=jvdate;
            //document.getElementById("txtOffice_Name").options[document.getElementById("txtOffice_Name").selectedIndex].text=issuedoffice;
            document.getElementById("txtOffice_Name").value=officecode;
            document.getElementById("cmbjournalno").options[document.getElementById("cmbjournalno").selectedIndex].text=jvno;
            document.getElementById("cmbjournalno").value=jvno;
         //   document.getElementById("quantity_date").value=qtydate;
            document.getElementById("txtRemarks").value=remarks;
    
}
function doFunction(Command,param)
{   
//alert("inside dofunction");
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    var txtCB_Year=document.getElementById("txtCB_Year").value;    
    var txtCB_Month=document.getElementById("txtCB_Month").value;
    //alert("year "+txtCB_Year+" month "+txtCB_Month);
    var cmbassetclass = document.getElementById("cmbassetclass").value;
    var cmbassetcode=document.getElementById("cmbassetcode").value;
    var txtassetdesc=document.getElementById("txtassetdesc").value;
    var issue_date=document.getElementById("issue_date").value;
    var levelid;//document.frmIssue_Asset.levelid.value;
   // alert(" levelid in jsss ..."  +levelid);
    if(document.frmIssue_Asset.levelid[0].checked==true)
    {
    	//levelid=document.getElementById("levelid").value;
    	levelid=document.frmIssue_Asset.levelid[0].value;
    }
    else if(document.frmIssue_Asset.levelid[1].checked==true)
    {
    	levelid=document.frmIssue_Asset.levelid[1].value;
   
   }else if(document.frmIssue_Asset.levelid[2].checked==true)
   {
   	levelid=document.frmIssue_Asset.levelid[2].value;
  
  }
    
    
    var txtqtyissued=document.getElementById("txtqtyissued").value;
    var txtvalueissued=document.getElementById("txtvalueissued").value;
    var txtOffice_Name=document.getElementById("txtOffice_Name").value;
   // alert("txtOffice_Name"+txtOffice_Name);
   // var txtOffice_Name=document.getElementById("txtOffice_Name").options[document.getElementById("txtOffice_Name").selectedIndex].text;
    var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
    var cmbjournalno=document.getElementById("cmbjournalno").value;
    var txtjournal_date=document.getElementById("txtjournal_date").value;
    var txtRemarks=document.getElementById("txtRemarks").value;
    var cmbmajorclass=document.getElementById("cmbassetclass").value;
    //alert("sssssssss:::::"+cmbmajorclass);
    
    if(cmbFinancialYear==""){
    	alert("Enter Financial Year");
    	return false;
    }
    else if(cmbassetclass==""){
    	alert("Enter Asset Major Class");
    	return false;
    }
    else if(cmbassetcode==""){
    	alert("Enter Asset Code");
    	return false;
    }
    else if(issue_date==""){
    	alert("Enter Issue Date");
    	return false;
    } else if(txtOffice_Name==""){
    	alert("select Office Name to which the Asset is sent ");
    	return false;
    }
     /*if(Command=="loadAssetCode")
        { 
        var url="../../../../../Issue_Assets_NumericalAC?Command=loadAssetCode&cmbAcc_UnitCode="+cmbAcc_UnitCode+
        "&cmbOffice_code="+cmbOffice_code+"&cmbFinancialYear="+cmbFinancialYear+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbmajorclass="+cmbmajorclass;
     //   alert(url);                
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {        
                processResponse(req);
        }   
        req.send(null);
        }*/

 if (Command=="loadjournalno")
        { 
        var url="../../../../../Issue_Assets_NumericalAC?Command=loadjournalno&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                "&cmbOffice_code="+cmbOffice_code+"&cmbFinancialYear="+cmbFinancialYear+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
       //         alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
                processResponse(req);
        };  
        req.send(null);
        }
   
       else if(Command=="Add")
        {  
        
          //  var flag=nullcheck();
          var url="../../../../../Issue_Assets_NumericalAC?Command=Add&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbFinancialYear="+cmbFinancialYear+
                        "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbmajorclass="+cmbmajorclass+"&cmbassetcode="+cmbassetcode+
                        "&txtassetdesc="+txtassetdesc+"&issue_date="+issue_date+"&levelid="+levelid+
                        "&txtOffice_Name="+txtOffice_Name+"&txtqtyissued="+txtqtyissued+"&cmbjournalno="+cmbjournalno+
                        "&txtjournal_date="+txtjournal_date+"&txtRemarks="+txtRemarks+"&txtvalueissued="+txtvalueissued;
              // alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   ;
                        req.send(null);
  
        }
        else if(Command=="Delete")
        {
        
            if(confirm("Do You Really want to Delete it?"))
            {
              /* var flag=nullcheck();
               if(flag==true)
               {*/  
                  var url="../../../../../Issue_Assets_NumericalAC?Command=Delete&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbmajorclass="+cmbmajorclass+"&cmbassetclass="+cmbassetclass+
                        "&cmbFinancialYear="+cmbFinancialYear+"&cmbassetcode="+cmbassetcode;
                   //alert(url);
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    }  ; 
                            req.send(null);
               //}
            }
           
           
       }
       else if(Command=="Update")
       { 
    	  // alert("inside update");
           //var flag=nullcheck();
         
                var url="../../../../../Issue_Assets_NumericalAC?Command=Update&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbFinancialYear="+cmbFinancialYear+
                        "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbmajorclass="+cmbmajorclass+"&cmbassetcode="+cmbassetcode+
                        "&txtassetdesc="+txtassetdesc+"&issue_date="+issue_date+"&levelid="+levelid+
                        "&txtOffice_Name="+txtOffice_Name+"&txtqtyissued="+txtqtyissued+"&cmbjournalno="+cmbjournalno+
                        "&txtjournal_date="+txtjournal_date+"&txtRemarks="+txtRemarks+"&txtvalueissued="+txtvalueissued;
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   ;
                        req.send(null);
    
       }
       
   
}


function loadOffice_REC(){
	
	 var cmbAcc_UnitCode=document.frmIssue_Asset.cmbAcc_UnitCode.value;
	 if(cmbAcc_UnitCode=="null" || cmbAcc_UnitCode=="")cmbAcc_UnitCode=42;
	// alert("testingxx vvc **** "+ cmbAcc_UnitCode);
	
	   var req=getTransport(); 
	    
	   var url="../../../../../Receipt_SL.view?Command=LoadREcOffice&cmbAcc_UnitCode="+cmbAcc_UnitCode;
	             
	           req.open("GET",url,true); 
	               
	           req.onreadystatechange=function()
	            {
	        if(req.readyState==4)
	           {
	        if(req.status==200)
	        {
	            var baseresponse=req.responseXML.getElementsByTagName("response")[0];
	            var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	           
	            if(flag=="success")
	            { 
	             
	             try
	             {
	                var cmboffice=document.getElementById("cmbOffice_code");
	               
	                cmboffice.innerHTML="";
	                var offidvalues=baseresponse.getElementsByTagName("offid");
	           
	                for(var i=0;i<offidvalues.length;i++)
	                {  
	                    var option=document.createElement("OPTION");
	                    var offid=baseresponse.getElementsByTagName("offid")[i].firstChild.nodeValue;
	                    var offname=baseresponse.getElementsByTagName("offname")[i].firstChild.nodeValue;
	                    option.text=offname+"("+offid+")";
	                    option.value=offid;
	                    try
	                    {
	                        cmboffice.add(option);
	                    }
	                    catch(errorObject )
	                    {
	                        cmboffice.add(option,null);
	                    }   
	                }
	                
	             }
	             catch(err)
	             {
	                alert("Problem in Loading Office code ");
	             }
	                
	            }
	            else
	            {
	              
	             try
	             {
	                var cmboffice=document.getElementById("cmbOffice_code");
	                cmboffice.innerHTML="";
	                var option=document.createElement("OPTION");
	                option.text="--select office--";
	                option.value="";
	                try
	                {
	                    cmboffice.add(option);
	                }
	                catch(errorObject )
	                {
	                    cmboffice.add(option,null);
	                }
	             }
	             catch(err)
	             {
	                alert("Problem in Loading Office code ");
	             }         
	                
	                
	            }
	                
	                 
	         
	        }
	           }
	            };
	    req.send(null);       
}




function checkDate(){
	
	 var issue_date=document.getElementById("issue_date").value;
	    var txtCB_Month=document.getElementById("txtCB_Month").value;
	    var txtCB_Year = document.getElementById("txtCB_Year").value;
	    var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	
	    			var a = issue_date.split('/');
	    			
	    			var issue_date1 = new Date(a[2], a[1] - 1, a[0]);
	    			var toDate1 = new Date(parseInt(txtCB_Year), parseInt(txtCB_Month) - 1,30);
	    			
	    			//alert("issue_date1 "+issue_date1+" toDate1 "+toDate1);
	    			if(txtCB_Month.length<2)txtCB_Month="0"+txtCB_Month;
	if(issue_date1>toDate1){
		alert("Issue Date should be within cashbook year and month..");
		document.getElementById("issue_date").value = "";
		//return false;
	}
	else if (a[2]!=txtCB_Year)
		{
		alert("Issue Date should be within cashbook year ");
		document.getElementById("issue_date").value = "";
		}else if (a[1]!=txtCB_Month)
		{
			alert("Issue Date should be within cashbook Month ");
			document.getElementById("issue_date").value = "";
			}
	
	//return true;
	
}
//lakshmi
function checkcashmonth(){
	    var txtCB_Month=document.getElementById("txtCB_Month").value;
	    var txtCB_Year = document.getElementById("txtCB_Year").value;
	    var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	
	var fy1=cmbFinancialYear.split("-");
	var y1 = fy1[0];
	var y2 = "20"+fy1[1];
	
	if((parseInt(txtCB_Year)<=parseInt(y2))&&(parseInt(txtCB_Year)>=parseInt(y1))){
		
		if(parseInt(txtCB_Year)==parseInt(y1)){
			if((parseInt(txtCB_Month)<=3)){
				alert("Cashbook month should be within financial year ");
				document.getElementById("txtCB_Month").value = "4";
				return false;	
			}
			
		}
		if(parseInt(txtCB_Year)==parseInt(y2)){
					
					if((parseInt(txtCB_Month)>=4)){
						alert("Cashbook month should be within financial year ");
						document.getElementById("txtCB_Month").value = "3";
						return false;
					}
					
				}
		
		
	}else{
		alert("Cashbook Year should be within the financial year");
		document.getElementById("txtCB_Year").value = "";
		return false;
	}	
}
	
function checkStatus() {
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	    var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	   if(nullcheck()){ 
	var url="../../../../../Issue_Assets_NumericalAC?Command=checkStatus&cmbAcc_UnitCode="+cmbAcc_UnitCode+
   "&cmbOffice_code="+cmbOffice_code+"&cmbFinancialYear="+cmbFinancialYear;
//alert(" checkStatus  ---> "+url);
	var req=getTransport();
	req.open("GET",url,true); 
	req.onreadystatechange=function()
	{
	  handleResponse(req);
	}  ; 
       req.send(null);
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
            }else if(Command=="checkStatus"){
            	checkStatus1(baseResponse);
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
function checkStatus1(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var d=document.getElementById("cmdAdd");
    d.disabled=false;
    var d1=document.getElementById("cmdUpdate");
    d1.disabled=false;
    var d3=document.getElementById("cmdDelete");
    d3.disabled=false;
    if(flag=="freezeCricle")
    {
     // alert("freezeCricle");
    }
    else  if(flag=="notfreezeCricle")
    {
        alert("Cricle not freezed,After Freeze Only you can able to add datas");
        var d=document.getElementById("cmdAdd");
        d.disabled=true;
        var d1=document.getElementById("cmdUpdate");
        d1.disabled=true;
        var d3=document.getElementById("cmdDelete");
        d3.disabled=true;
        /*var d4=document.getElementById("cmdList");
        d4.disabled=true;*/
      
        // ClearAll();
    }
    else
    {
        alert("failure");
    }
} 
function UpdateRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var items=new Array();
    
    if(flag=="success")
    {
        alert("Record Updated");
        document.frmIssue_Asset.cmbassetclass.disabled=false;
        document.frmIssue_Asset.cmbFinancialYear.disabled=false;
        document.frmIssue_Asset.txtOffice_Name.disabled=false;
        
           
        ClearAll();
    }
    else 
    {
        alert("Record not Updated");
    }
}

function ClearAll()
{
        document.frmIssue_Asset.cmbassetclass.disabled=false;
        document.frmIssue_Asset.cmbFinancialYear.disabled=false;
        document.frmIssue_Asset.txtOffice_Name.disabled=false;
        document.getElementById("txtCB_Year").value="";
        document.getElementById("txtCB_Month").value="";
        document.getElementById("cmbassetclass").value="";
        document.getElementById("cmbjournalno").value="";
        document.getElementById("txtassetdesc").value="";
        document.getElementById("issue_date").value="";
        document.getElementById("levelid").value="";
   // alert("cmbmajorclass"+cmbmajorclass);
        document.getElementById("txtqtyissued").value="";
        document.getElementById("txtvalueissued").value="";
        document.getElementById("txtOffice_Name").options[document.getElementById("txtOffice_Name").selectedIndex].text=="";
        document.getElementById("cmbFinancialYear").value="";
        document.getElementById("txtOffice_Name").value="";
        document.getElementById("cmbjournalno").value="";
        document.getElementById("txtjournal_date").value="";
        document.getElementById("txtRemarks").value="";
       
  
        var d=document.getElementById("cmdAdd");
        d.style.display="block";
        var d1=document.getElementById("cmdUpdate");
        d1.style.display="none";
        var d3=document.getElementById("cmdDelete");
        d3.style.display="none";
}


function nullcheck()
{
   
   var accounting_unit_id=document.frmIssue_Asset.cmbAcc_UnitCode;
		if(accounting_unit_id.value=="")
		{ 
		     alert("Please Select the 'Accounting Unit'");
		     accounting_unit_id.focus();
		     return false;
		}

		var accounting_unit_office_id = document.frmIssue_Asset.cmbOffice_code;
		if(accounting_unit_office_id.value=="")
		{ 
		     alert("Please Select the 'Accounting Office'");
		     accounting_unit_office_id.focus();
		     return false;
		}

		var cmbFinancialYear = document.frmIssue_Asset.cmbFinancialYear;
		if(cmbFinancialYear.value=="")
		{ 
		     alert("Please select the 'Financial Year'");
		     cmbFinancialYear.focus();
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
                return false ;
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
                return false ;
        }
     }


function processResponse(req)
{   
      if(req.readyState==4)
	    {
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              
              var command=tagCommand.firstChild.nodeValue; 
              if(command=="loadAssetCode")
              {
                   LoadAssetCode(baseResponse);
              }
              else if(command=="loadjournalno")
              {
                    loadjournalno(baseResponse);
              }
              else if(command=="addResponse")
              {
                    //alert(command);
                    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                    if(flag=="success")
                    {
                        var SancEstiNo=baseResponse.getElementsByTagName("sanc_esti_no")[0].firstChild.nodeValue;
                       
                        alert("Sanction Estimate Number " + SancEstiNo + "  inserted into the database successfully...");
                        clearAll();
                    }
                    else if(flag=="record")
                    {
                        alert("Record already exist");
                        clearAll();
                    }
              }
               else if(command=="retrieve")
               {
                  retrieveChecking(baseResponse);
               }
                else if(command=="updated")
                {
                   updateChecking(baseResponse);
                }
                else if(command=="deleted")
                { 
                     alert("record deleted successfully");   
                     clearAll();
                }
        }    
    }


/*function loadAssetCode(baseResponse)
{       alert("baseresponse");
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                  alert(flag);
                  if(flag=="success")
                  {                       
                        //var asset_desc=baseResponse.getElementsByTagName("asset_code_desc")[0].firstChild.nodeValue;
                        //document.formSanctionEstimate_Master.txtAsset_Description.value=asset_desc;
                        //alert(flag);
                       var option=baseResponse.getElementsByTagName("option");
                        var asset_code=document.getElementById("cmbjournalno");
                        var child=asset_code.childNodes;
                        for(var i=child.length-1;i>1;i--)
                        {
                                asset_code.removeChild(child[i]);
                        } 
                        for(var i=0;i<option.length;i++) 
                         {
                            var code=option[i].getElementsByTagName("assetid")[0].firstChild.nodeValue;
                            var desc=option[i].getElementsByTagName("assetName")[0].firstChild.nodeValue;
                            //alert(code+"   "+desc);
                            var opt=document.createElement("option");
                            opt.setAttribute("value",desc);
                            var opttext=document.createTextNode(code);
                            opt.appendChild(opttext);
                            asset_code.appendChild(opt);
                         }
                  }
                  else if(flag=="nodata")
                  {
                        alert("Invalid asset code");
                  }
                  else
                  {
                        alert("Failed to load asset code");
                  }
}*/
/*function loadassetdesc(assetcode)
   {
        var assetdesc=assetcode;
        document.getElementById("txtassetdesc").value=assetdesc;
   
   }*/
function LoadAssetCode(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
    if(flag=="success")
    {          
    	var cmbassetcode=document.getElementById("cmbassetcode");
    	var ln = cmbassetcode.length;
    	for(i=1; i<ln; i++)
    	{
    		cmbassetcode.remove(1);
    	}
    	var mjrCode = baseResponse.getElementsByTagName('asset_code');
    	var len = mjrCode.length;
    	for(i=0; i<len; i++)
    	{
    		mjrCode = baseResponse.getElementsByTagName('asset_code')[i].firstChild.nodeValue;
    		var mjrDesc = baseResponse.getElementsByTagName('asset_code_desc')[i].firstChild.nodeValue;
    		var opt = document.createElement("option");
    		opt.value = mjrCode;
    		opt.innerHTML = mjrDesc;
    		cmbassetcode.appendChild(opt);
    	}
    }
    else if(flag=="nodata")
    {
      alert("nodata");
    }
    else 
    {
    	alert("Error fetching list of  assetName from Database");
    }
}
function loadjournalno(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
    if(flag=="success")
    {          
    	var cmbjournalno=document.getElementById("cmbjournalno");
    	var ln = cmbjournalno.length;
    	for(i=1; i<ln; i++)
    	{
    		cmbjournalno.remove(1);
    	}
    	var jvCode = baseResponse.getElementsByTagName('jvno');
    	var len = jvCode.length;
    	for(j=0; j<len; j++)
    	{
    		jvCode = baseResponse.getElementsByTagName('jvno')[i].firstChild.nodeValue;
    		var jvdate = baseResponse.getElementsByTagName('jvdate')[i].firstChild.nodeValue;                
                document.getElementById("txtjournal_date").value=jvdate;
    		var opt = document.createElement("option");
    		opt.value = jvCode;
    		opt.innerHTML =jvCode;
    		cmbjournalno.appendChild(opt);
    	}
    }
   
    else 
    {
    	alert("Error fetching list of  assetName from Database");
    }
}
/*function loadjournalno(baseResponse)
{
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                  if(flag=="success")
                  {                       
                        //var asset_desc=baseResponse.getElementsByTagName("asset_code_desc")[0].firstChild.nodeValue;
                        //document.formSanctionEstimate_Master.txtAsset_Description.value=asset_desc;
                        //alert(flag);
                        var option=baseResponse.getElementsByTagName("option");
                        var asset_code=document.getElementById("cmbasset_code");
                        var child=asset_code.childNodes;
                        for(var i=child.length-1;i>1;i--)
                        {
                                asset_code.removeChild(child[i]);
                        } 
                        for(var i=0;i<option.length;i++)
                         {
                            var code=option[i].getElementsByTagName("asset_code")[0].firstChild.nodeValue;
                            var desc=option[i].getElementsByTagName("asset_code_desc")[0].firstChild.nodeValue;
                            //alert(code+"   "+desc);
                            var opt=document.createElement("option");
                            opt.setAttribute("value",desc);
                            var opttext=document.createTextNode(code);
                            opt.appendChild(opttext);
                            asset_code.appendChild(opt);
                         }
                  }
                  else if(flag=="nodata")
                  {
                        alert("Invalid asset code");
                  }
                  else
                  {
                        alert("Failed to load asset code");
                  }
}*/
/*function loadassetdesc(assetcode)
    {
        var assetdesc=assetcode;
        document.getElementById("txtassetdesc").value=assetdesc;
    
    }*/

function LoadAssetCode2(){
    //alert("enter.............");
    //var req=getTransport();
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
    var cmbmajorclass=document.getElementById("cmbassetclass").value;
    //alert("cmbmajorclass**********"+cmbmajorclass);
    if(nullcheck()){
    var req=getTransport();
    var url="../../../../../Issue_Assets_NumericalAC?Command=loadAssetCode2&cmbAcc_UnitCode="+cmbAcc_UnitCode+
        "&cmbOffice_code="+cmbOffice_code+"&cmbFinancialYear="+cmbFinancialYear+"&cmbmajorclass="+cmbmajorclass;
       //alert(url);                
      
    req.open("GET",url,true); 
   req.onreadystatechange=function()
      {       
      if(req.readyState==4)
      { 
        if(req.status==200)
        {  
    //alert(req.responseText);
    var baseResponse=req.responseXML.getElementsByTagName("response")[0];            
    var flag1=baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
    var cmbassetcode=document.getElementById("cmbassetcode");
    var len = cmbassetcode.length;
    for(var ii=0;ii <len; ii++)
	{
	cmbassetcode.remove(0);
	}
    cmbassetcode.length=0;
   if (flag1 == "success") {
		var len4 = baseResponse.getElementsByTagName("assetid").length;
		if (len4 > 0) {
			for ( var i = 0; i < len4; i++) {
				var assetid = baseResponse.getElementsByTagName("assetid")[i].firstChild.nodeValue;
                var assetName=baseResponse.getElementsByTagName("assetName")[i].firstChild.nodeValue;
				var se = document.getElementById("cmbassetcode");
				var op = document.createElement("OPTION");
				op.value = assetid;
				var txt1=assetid+"-"+assetName;
				var txt = document.createTextNode(txt1);
				op.appendChild(txt);
				se.appendChild(op);
				document.getElementById("txtassetdesc").value=assetName;
			}
		} else {
			alert("Asset Code Does Not Exist");
		}
	} else {
		alert("Fail to Load Asset Code");
		document.getElementById("txtassetdesc").value="";
	}
  }
 }
};
req.send(null);
}
}
function loadAssetDesc(){
   //alert("yytrdhfdhdfh/////////////");
    var req=getTransport();
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
    var cmbassetclass=document.getElementById("cmbassetclass").value;
    var cmbassetcode=document.getElementById("cmbassetcode").value;
    
    
    var url="../../../../../Issue_Assets_NumericalAC?Command=loadAssetDesc&cmbassetcode="+cmbassetcode+"&cmbOffice_code="+cmbOffice_code+"&cmbFinancialYear="+cmbFinancialYear+"&cmbmajorclass="+cmbassetclass;
    //alert(url);
   req.open("GET",url,true); 
   req.onreadystatechange=function()
      {       
      if(req.readyState==4)
      { 
        if(req.status==200)
        { 
       // alert(req.responseText);
       var baseResponse=req.responseXML.getElementsByTagName("response")[0];   
       var flag1 = baseResponse.getElementsByTagName("flag2")[0].firstChild.nodeValue;
	if (flag1 == "success") {
		var AssetName = baseResponse.getElementsByTagName("AssetName")[0].firstChild.nodeValue;
		document.getElementById("txtassetdesc").value = AssetName;
	} else {
		alert("Fail to Load Account Head Desc");
	}
   }
  }
 };
req.send(null); 
}
