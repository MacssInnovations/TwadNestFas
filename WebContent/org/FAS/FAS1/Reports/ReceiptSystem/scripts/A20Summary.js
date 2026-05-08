//UpdateFromReceipt
/** To handle Errors **/
onerror=handleErr;
var txt="";
function handleErr(msg,url,l)
{
	txt="There was an error on this page. \n\n";
	txt+="Error: " + msg + " \n";
	txt+="URL: " + url + " \n";
	txt+="Line: " + l + " \n\n";
	txt+="Click OK to continue.\n\n";
	alert(txt);
	return true;
}

/** To create javascript request object **/
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
 if (!req && typeof XMLHttpRequest!='undefined') 
 {
	
       req = new XMLHttpRequest();
 }  
 
 return req;
 
}


 function Exit()
 {
    self.close();
 }
 function nullCheck(){
	 var accounting_unit_id=document.frmA20Summary.cmbAcc_UnitCode;
		if(accounting_unit_id.value=="")
		{ 
		     alert("Please Select the 'Accounting Unit'");
		     accounting_unit_id.focus();
		     return false;
		}

		var accounting_unit_office_id = document.frmA20Summary.cmbOffice_code;
		if(accounting_unit_office_id.value=="")
		{ 
		     alert("Please Select the 'Accounting Office'");
		     accounting_unit_office_id.focus();
		     return false;
		}

		var cmbFinancialYear1 = document.frmA20Summary.cmbFinancialYear;
		if(cmbFinancialYear1.value=="")
		{ 
		     alert("Please select the 'Financial Year'");
		     cmbFinancialYear1.focus();
		     return false;
		}
		var to_txtCB_Year=document.frmA20Summary.to_txtCB_Year;
		if(to_txtCB_Year.value=="")
		{ 
		     alert("Type the 'To Year'");
		     to_txtCB_Year.focus();
		     return false;
		}
		var from_txtCB_Year=document.frmA20Summary.from_txtCB_Year;
		if(from_txtCB_Year.value=="")
		{ 
		     alert("Type the 'From Year'");
		     from_txtCB_Year.focus();
		     return false;
		}
		if(checkYear()){
			return true;
		}else{
			return false;
		}
		
  return true;
	 
 }
/* function SplitTheString(CommaSepStr) {
     var ResultArray = null; 

      if (CommaSepStr!= null) {
          var SplitChars = '-';
          if (CommaSepStr.indexOf(SplitChars) >= 0) {
              ResultArray = CommaSepStr.split(SplitChars);
          }
      }
  
    return ResultArray ;
  }*/
 function checkYear(){
	 var cmbFinancialYear1 = document.frmA20Summary.cmbFinancialYear;
	 var cmbFinancialYear2 = document.frmA20Summary.cmbFinancialYear.value;
	
			 var yearfirst;
				var yearsecond;
			       var ResultArray = null; 

			        if (cmbFinancialYear2!= null) {
			            var SplitChars = '-';
			            if (cmbFinancialYear2.indexOf(SplitChars) >= 0) {
			                ResultArray = cmbFinancialYear2.split(SplitChars);
			            }
			        }   
				    for(var i=0;i<ResultArray.length;i++){
				    	yearfirst=ResultArray[0];
				    	yearsecond=ResultArray[1];
				    }
			
	 var al= new Array() ;
	 var fullsec='20'+yearsecond;
	// alert("yearfirst==== "+yearfirst +" yearsecond== "+yearsecond+" fullsec = "+fullsec);
		var from_txtCB_Year=document.frmA20Summary.from_txtCB_Year;
		var to_txtCB_Year=document.frmA20Summary.to_txtCB_Year;
		if((from_txtCB_Year.value!=yearfirst)&&(from_txtCB_Year.value!=fullsec))
		{ 
		     alert("Type the 'FromYear' equal to Financial year");
		     from_txtCB_Year.value="";
		     to_txtCB_Year.value="";
		     from_txtCB_Year.focus();
		    
		     return false;
		}
		else if((from_txtCB_Year.value==yearfirst)){

		if((to_txtCB_Year.value!=yearfirst)&&(to_txtCB_Year.value!=fullsec))
		{ 
		     alert("Type the 'To Year'  equal to Financial year");
		     to_txtCB_Year.value="";
		     to_txtCB_Year.focus();
		     return false;
		}/*else{
			return true;
		}*/
		return true;
		}else{
			if((to_txtCB_Year.value!=fullsec)){
				 alert("Type the 'To Year' "+fullsec);
				 to_txtCB_Year.value="";
			     to_txtCB_Year.focus();
			     return false;
			}
			return true;
		}

		return true;
 }
 function checkToYear(){
	 var cmbFinancialYear1 = document.frmA20Summary.cmbFinancialYear;
	 var cmbFinancialYear2 = document.frmA20Summary.cmbFinancialYear.value;
	 var tomonth=document.frmA20Summary.to_txtCB_Month;
	 if(cmbFinancialYear1.value=="")
		{ 
		     alert("Please select the 'Financial Year'");
		     cmbFinancialYear1.focus();
		     return false;
		}else{
			 var yearfirst;
				var yearsecond;
			       var ResultArray = null; 

			        if (cmbFinancialYear2!= null) {
			            var SplitChars = '-';
			            if (cmbFinancialYear2.indexOf(SplitChars) >= 0) {
			                ResultArray = cmbFinancialYear2.split(SplitChars);
			            }
			        }   
				    for(var i=0;i<ResultArray.length;i++){
				    	yearfirst=ResultArray[0];
				    	yearsecond=ResultArray[1];
				    }
			
	 var al= new Array() ;
	 var fullsec='20'+yearsecond;
		var to_txtCB_Year=document.frmA20Summary.to_txtCB_Year;
		var from_txtCB_Year=document.frmA20Summary.from_txtCB_Year;
		if((from_txtCB_Year.value==fullsec)){
			
			if((to_txtCB_Year.value==yearfirst)&&(to_txtCB_Year.value!=fullsec))
			{ 
			     alert("Type the 'To Year'  equal to Financial year");
			     to_txtCB_Year.value="";
			     to_txtCB_Year.focus();
			     return false;
			}
			
		}
 if((to_txtCB_Year.value!=yearfirst)&&(to_txtCB_Year.value!=fullsec))
		{ 
		     alert("Type the 'To Year'  equal to Financial year");
		     to_txtCB_Year.value="";
		     to_txtCB_Year.focus();
		     return false;
		}
 if((to_txtCB_Year.value==yearfirst)){
			tomonth.value="12";
			return true;
		}
 if((to_txtCB_Year.value==fullsec)){
			tomonth.value="3";
			return true;
		}
			//}
			/*else if((to_txtCB_Year.value==yearfirst)){
				if((tomonth.value!='1')&&(tomonth.value!='2')&&(tomonth.value!='3')){
					alert("select To Month between may to dec");
					tomonth.value="3";
				return false;	
				}
				return true;
			}else if((to_txtCB_Year.value==fullsec)){
				if((tomonth.value=='1')&&(tomonth.value=='2')&&(tomonth.value=='3')){
					alert("From Month Starts from April");
					frommonth.value="3";
				return true;	
				}else{
					alert("select To month between jan to mar");
					return false;
				}
				return true;
			}*/
		
		
		return true;
		}
		return true; 
 }
 function checkFromYear(){
	 var cmbFinancialYear1 = document.frmA20Summary.cmbFinancialYear;
	 var cmbFinancialYear2 = document.frmA20Summary.cmbFinancialYear.value;
	var frommonth=document.frmA20Summary.from_txtCB_Month;

	 if(cmbFinancialYear1.value=="")
		{ 
		     alert("Please select the 'Financial Year'");
		     cmbFinancialYear1.focus();
		     return false;
		}else{
			 var yearfirst;
				var yearsecond;
			       var ResultArray = null; 

			        if (cmbFinancialYear2!= null) {
			            var SplitChars = '-';
			            if (cmbFinancialYear2.indexOf(SplitChars) >= 0) {
			                ResultArray = cmbFinancialYear2.split(SplitChars);
			            }
			        }   
				    for(var i=0;i<ResultArray.length;i++){
				    	yearfirst=ResultArray[0];
				    	yearsecond=ResultArray[1];
				    }
			
	 var al= new Array() ;
	 var fullsec='20'+yearsecond;
		var from_txtCB_Year=document.frmA20Summary.from_txtCB_Year;
		var to_txtCB_Year=document.frmA20Summary.to_txtCB_Year;
		if((from_txtCB_Year.value!=yearfirst)&&(from_txtCB_Year.value!=fullsec))
		{ 
		     alert("Type the 'FromYear' equal to Financial year");
		     from_txtCB_Year.value="";
		     to_txtCB_Year.value="";
		     from_txtCB_Year.focus();
		    
		     return false;
		}
		
		if((from_txtCB_Year.value==yearfirst)){
			frommonth.value="4";
			return true;
		}
		if((from_txtCB_Year.value==fullsec)){
			frommonth.value="1";
			return true;
		}
		/*else if((from_txtCB_Year.value==yearfirst)){
			if((frommonth.value!='1')&&(frommonth.value!='2')&&(frommonth.value!='3')){
				alert("select To Month between apr to dec");
				frommonth.value="3";
			return false;	
			}
			return true;
		}else if((from_txtCB_Year.value==fullsec)){
			if((frommonth.value=='1')&&(frommonth.value=='2')&&(frommonth.value=='3')){
				alert("From Month Starts from April");
				frommonth.value="3";
			return true;	
			}else{
				alert("select To month between jan to mar");
				return false;
			}
			return true;
		}*/
		return true;
		}
		return true;
 }
 
 function checkfromyearmonth(){

	 var cmbFinancialYear1 = document.frmA20Summary.cmbFinancialYear;
	 var cmbFinancialYear2 = document.frmA20Summary.cmbFinancialYear.value;
	var frommonth=document.frmA20Summary.from_txtCB_Month;

	 if(cmbFinancialYear1.value=="")
		{ 
		     alert("Please select the 'Financial Year'");
		     cmbFinancialYear1.focus();
		     return false;
		}else{
			 var yearfirst;
				var yearsecond;
			       var ResultArray = null; 

			        if (cmbFinancialYear2!= null) {
			            var SplitChars = '-';
			            if (cmbFinancialYear2.indexOf(SplitChars) >= 0) {
			                ResultArray = cmbFinancialYear2.split(SplitChars);
			            }
			        }   
				    for(var i=0;i<ResultArray.length;i++){
				    	yearfirst=ResultArray[0];
				    	yearsecond=ResultArray[1];
				    }
			
	 var al= new Array() ;
	 var fullsec='20'+yearsecond;
		var from_txtCB_Year=document.frmA20Summary.from_txtCB_Year;
		if((from_txtCB_Year.value!=yearfirst)&&(from_txtCB_Year.value!=fullsec))
		{ 
		     alert("Type the 'FromYear' equal to Financial year");
		     from_txtCB_Year.value="";
		     to_txtCB_Year.value="";
		     from_txtCB_Year.focus();
		     return false;
		}
		if((from_txtCB_Year.value==yearfirst)){
			if((frommonth.value=="1")||(frommonth.value=="2")||(frommonth.value=="3")){
				alert("select From Month between apr to dec");
				frommonth.value="4";
			return false;
			}
			return true;
		}
   if((from_txtCB_Year.value==fullsec)){
			
			if((frommonth.value=="4")||(frommonth.value=="5")||(frommonth.value=="6")||(frommonth.value=="7")||(frommonth.value=="8")||(frommonth.value=="9")||(frommonth.value=="10")||(frommonth.value=="11")||(frommonth.value=="12")){
				alert("select From month between jan to mar");
				frommonth.value="1";
				return false;
			}
			
			
			/*if((frommonth.value=="1")||(frommonth.value=="2")||(frommonth.value=="3")){
				alert("From Month Starts from April");
				frommonth.value="3";
			return true;	
			}else{
				alert("select From month between jan to mar");
				frommonth.value="1";
				return false;
			}*/
			return true;
		}
		return true;
		}
		return true;
 }
 function checktoyearmonth(){

	 var cmbFinancialYear1 = document.frmA20Summary.cmbFinancialYear;
	 var cmbFinancialYear2 = document.frmA20Summary.cmbFinancialYear.value;
	 var tomonth=document.frmA20Summary.to_txtCB_Month;
	 if(cmbFinancialYear1.value=="")
		{ 
		     alert("Please select the 'Financial Year'");
		     cmbFinancialYear1.focus();
		     return false;
		}else{
			 var yearfirst;
				var yearsecond;
			       var ResultArray = null; 

			        if (cmbFinancialYear2!= null) {
			            var SplitChars = '-';
			            if (cmbFinancialYear2.indexOf(SplitChars) >= 0) {
			                ResultArray = cmbFinancialYear2.split(SplitChars);
			            }
			        }   
				    for(var i=0;i<ResultArray.length;i++){
				    	yearfirst=ResultArray[0];
				    	yearsecond=ResultArray[1];
				    }
			
	 var al= new Array() ;
	 var fullsec='20'+yearsecond;
		var to_txtCB_Year=document.frmA20Summary.to_txtCB_Year;
	//	if((from_txtCB_Year.value==yearfirst)){

		if((to_txtCB_Year.value!=yearfirst)&&(to_txtCB_Year.value!=fullsec))
		{ 
		     alert("Type the 'To Year'  equal to Financial year");
		     to_txtCB_Year.value="";
		     to_txtCB_Year.focus();
		     return false;
		}
			 if((to_txtCB_Year.value==yearfirst)){
				if((tomonth.value=="1")||(tomonth.value=="2")||(tomonth.value=="3")){
					alert("select To Month between Apr to Dec");
					tomonth.value="4";
				return false;	
				}
				return true;
			}
			 if((to_txtCB_Year.value==fullsec)){
				if((tomonth.value=="4")||(tomonth.value=="5")||(tomonth.value=="6")||(tomonth.value=="7")||(tomonth.value=="8")||(tomonth.value=="9")||(tomonth.value=="10")||(tomonth.value=="11")||(tomonth.value=="12")){
					alert("select To month between jan to mar");
					tomonth.value="3";
					return false;
				}
				return true;
			}
		
		
		return true;
		}
		return true; 
 }
 
 function numbersonly(e)
 {
     var unicode=e.charCode? e.charCode : e.keyCode;
    if(unicode==13)
     {url
     
     }
     if (unicode!=8 && unicode !=9  )
     {
         if (unicode<48 || unicode>57 ) 
             return false 
     }
  }
 function callServer(command){  
     var accounting_unit_id=document.frmA20Summary.cmbAcc_UnitCode.value;
 var accounting_unit_office_id = document.frmA20Summary.cmbOffice_code.value;
 var assetmajor=document.frmA20Summary.cmbmajorasset.value;
 var financial_year = document.frmA20Summary.cmbFinancialYear.value;  
 var assetmajor=document.frmA20Summary.cmbmajorasset.value;
 var url="";
 if(command=="loadMajor"){
  	url="../../../../../../A20Summary?command=loadMajor";
  //	alert(url);
		var req=getTransport();
      req.open("GET",url,true);  
      req.onreadystatechange=function(){
         processResponse(req);
      };   
      req.send(null);
  	
  }
}  


//********************************* CallServer Response Coding ***************************************//

function processResponse(req)
{   
if(req.readyState==4)
{
if(req.status==200)
{   

	  var baseResponse=req.responseXML.getElementsByTagName("response")[0];
    
    var tagCommand=baseResponse.getElementsByTagName("command")[0];
    
    var command=tagCommand.firstChild.nodeValue; 
   // alert("command=="+command);
	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	    if(command=="loadMajor")
        {
  		  var cmbMajorClass = document.getElementById('cmbmajorasset');
  		  cmbMajorClass.length=0;
  		 // var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
  		
      	  if(flag=="success"){
      		  
      		  var exists=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
        		  if(exists=="Yes"){
        		
      		  var mjrCode = baseResponse.getElementsByTagName('ASSET_MAJOR_CLASS_CODE');
      		  
          	  var len = mjrCode.length;
      	  for(i=0; i<len; i++)
      	  {
      		  mjrCode = baseResponse.getElementsByTagName('ASSET_MAJOR_CLASS_CODE')[i].firstChild.nodeValue;
      		  var mjrDesc = baseResponse.getElementsByTagName('ASSET_MAJOR_CLASS_DESC')[i].firstChild.nodeValue;
      		  var opt = document.createElement("option");
      		  opt.value = mjrCode;
      		  opt.innerHTML = mjrDesc;
      		  
      		  cmbMajorClass.appendChild(opt);
      	  }
        		 }else{
       			  alert("No Records");
       		  }
      	  } else
		        {
			        alert("No Major AssetCode in Table");
			        }
      	  
        }
 
   
	  }
}
}

 function loadyear_month()
 {

 var today= new Date(); 
 var day=today.getDate();
 var month=today.getMonth();
 month=month+1;
 var year=today.getYear();
 if(year < 1900) year += 1900;

document.frmA20Summary.from_txtCB_Year.value=year;
document.frmA20Summary.from_txtCB_Month.value=month;

document.frmA20Summary.to_txtCB_Year.value=year;
document.frmA20Summary.to_txtCB_Month.value=month;
 }
 function clearAll()
{
	  document.getElementById('cmbAcc_UnitCode').options[0].selected = "selected";
	  document.getElementById('cmbOffice_code').options[0].selected = "selected";
	  document.getElementById('cmbFinancialYear').value = "selected";
	  document.getElementById('cmbmajorasset').value = 0;
	  LoadAccountingUnitID('LIST_ALL_UNITS');
	  loadyear_month();
}