var common_cmbSL_Code="";
var common_cmbSL_type="";
var job_flag;


/* 
 *  Browser Indentification 
 */

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

var dtCh= "/";
var minYear=1900;
var maxYear=2100;

function isInteger(s)
{
	
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

function stripCharsInBag(s, bag){
	
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   } 
   return this;
}
//added new form for not update list by SB on May 01 2017...
function passSheetChangeNew()
{
    var chyear=document.frmBRS_Bank_Balance_Update.txtCB_Year.value;
    var chmonth=document.frmBRS_Bank_Balance_Update.txtCB_Month.value;
   
    if(chmonth==1)
    {
        var ps_monthnew=12;
        var ps_yearnew=parseInt(chyear)-1;
        document.frmBRS_Bank_Balance_Update.txtPS_Year.value=ps_yearnew;
        document.frmBRS_Bank_Balance_Update.txtPS_Month.value=ps_monthnew;
        document.getElementById("txtPS_Year").disabled=true;
        document.getElementById("txtPS_Month").disabled=true;
               
    }
    else
    {
        var passmn=parseInt(chmonth)-1;
       
        document.frmBRS_Bank_Balance_Update.txtPS_Year.value=chyear;
        document.frmBRS_Bank_Balance_Update.txtPS_Month.value=passmn;
        document.getElementById("txtPS_Year").disabled=true;
        document.getElementById("txtPS_Month").disabled=true;
           
    }
}
function passSheetChange()
{
	//1var chyear=document.frmBRS_Bank_Balance_Update.txtCB_Year.value;
	//1var chmonth=document.frmBRS_Bank_Balance_Update.txtCB_Month.value;
	var chyear=document.frmBRS_Bank_Balance_Update.txtPS_Year.value;
	var chmonth=document.frmBRS_Bank_Balance_Update.txtPS_Month.value;
	
	
	if(chmonth==1)
	{
		//2var ps_yearnew=parseInt(chyear)-1;
		var ps_monthnew=12;
		document.frmBRS_Bank_Balance_Update.txtPS_Year.value=parseInt(chyear);
		//document.getElementById("txtPS_Month").options[document.getElementById("txtPS_Month").selectedIndex].text="December";
		//document.getElementById("txtPS_Month").options[document.getElementById("txtPS_Month").selectedIndex].value=12;
		//getting the last day of passe sheet date entered on 06/05/2016****
		var date = new Date();
		//var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
		//2var lastDay = new Date(ps_yearnew, ps_monthnew , 0);
		var lastDay = new Date(chyear, chmonth , 0);
		
		var lastDayWithSlashes = (lastDay.getDate()) + '/' + (lastDay.getMonth() + 1) + '/' + lastDay.getFullYear();

		//alert(lastDayWithSlashes);
		document.getElementById("txtPS_PrintDate").value=lastDayWithSlashes;
		//document.getElementById("txtPS_Year").disabled=true;
	   //document.getElementById("txtPS_Month").disabled=true;
		document.getElementById("txtPS_PrintDate").disabled=true;
		
		
	}
	else
	{
		var passmn=parseInt(chmonth)-1;
		//var passtext;
		/*if(passmn==1)
		{
			passtext="January";  
		}else if(passmn==2)
		{
			passtext="February";  
		}else if(passmn==3)
		{
			passtext="March";  
		}else if(passmn==4)
		{
			passtext="April";  
		} else if(passmn==5)
		{
			passtext="May";  
		}else if(passmn==6)
		{
			passtext="June";  
		}else if(passmn==7)
		{
			passtext="July";  
		}else if(passmn==8)
		{
			passtext="August";  
		}else if(passmn==9)
		{
			passtext="September";  
		}else if(passmn==10)
		{
			passtext="October";  
		}else if(passmn==11)
		{
			passtext="November";  
		}else if(passmn==12)
		{
			passtext="December";  
		}*/
		//document.frmBRS_Bank_Balance_Update.txtPS_Year.value=chyear;
		//document.getElementById("txtPS_Month").options[document.getElementById("txtPS_Month").selectedIndex].text=passtext;
		//document.getElementById("txtPS_Month").options[document.getElementById("txtPS_Month").selectedIndex].value=passmn;
		var ps_yearnew1=parseInt(chyear);
		//var ps_monthnew1=parseInt(chmonth)-1;
		var date = new Date();
		var lastDayWithSlashes="";
		//var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
		var lastDay = new Date(ps_yearnew1, chmonth , 0);
		if( (chmonth==1) || (chmonth==2) || (chmonth==3) || (chmonth==4) || (chmonth==5)
				|| (chmonth==6) || (chmonth==7) || (chmonth==8) || (chmonth==9) )
			{
			 lastDayWithSlashes = (lastDay.getDate()) + '/0' + (lastDay.getMonth() + 1) + '/' + lastDay.getFullYear();
			}
		else
			{
			lastDayWithSlashes = (lastDay.getDate()) + '/' + (lastDay.getMonth() + 1) + '/' + lastDay.getFullYear();
			}

		//alert(lastDayWithSlashes);
		document.getElementById("txtPS_PrintDate").value=lastDayWithSlashes;
		//document.getElementById("txtPS_Year").disabled=true;
		//document.getElementById("txtPS_Month").disabled=true;
		document.getElementById("txtPS_PrintDate").disabled=true;
		
	}
}



function loadMonth(){	
	var sales_year=document.frmBRS_Bank_Balance_Update.txtPS_Year.value;
//	alert(sales_year)
	var length=sales_year.length;	
    var dt=new Date();
    var year=dt.getFullYear();
    var month=dt.getMonth();
    month=month+1;
    var sales_year=document.frmBRS_Bank_Balance_Update.txtPS_Year.value;
    var monthNames = new Array("January","February","March","April","May","June","July","August","September","October","November","December");
   
    var sales_month=document.getElementById("txtPS_Month");    	
    if(sales_year!=year){
    	 month=12;    	   
    	
    	
    }       
    var child=sales_month.childNodes;
    for(var c=child.length-1;c>0;c--) {
       sales_month.removeChild(child[c]);
   }
    for(i=month-1;i>=0;i--){
        var opt =document.createElement("option"); 
        var text=document.createTextNode(monthNames[i]);
        opt.setAttribute("value",i+1);
        opt.appendChild(text);
        sales_month.appendChild(opt);       
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
document.frmBRS_Bank_Balance_Update.txtCB_Year.value=year;

		var arr=new Array();
		arr[0]=(month-1);
		arr[1]=(month);
		arr[2]=(month-2);
		
		var mntext=new Array();

		if(arr[0]==1)
		{
			mntext[0]="January";  
		}else if(arr[0]==2)
		{
			mntext[0]="February";  
		}else if(arr[0]==3)
		{
			mntext[0]="March";  
		}else if(arr[0]==4)
		{
			mntext[0]="April";  
		} else if(arr[0]==5)
		{
			mntext[0]="May";  
		}else if(arr[0]==6)
		{
			mntext[0]="June";  
		}else if(arr[0]==7)
		{
			mntext[0]="July";  
		}else if(arr[0]==8)
		{
			mntext[0]="August";  
		}else if(arr[0]==9)
		{
			mntext[0]="September";  
		}else if(arr[0]==10)
		{
			mntext[0]="October";  
		}else if(arr[0]==11)
		{
			mntext[0]="November";  
		}else if(arr[0]==12)
		{
			mntext[0]="December";  
		}
		
		if(arr[1]==1)
		{
			mntext[1]="January";  
		}else if(arr[1]==2)
		{
			mntext[1]="February";  
		}else if(arr[1]==3)
		{
			mntext[1]="March";  
		}else if(arr[1]==4)
		{
			mntext[1]="April";  
		} else if(arr[1]==5)
		{
			mntext[1]="May";  
		}else if(arr[1]==6)
		{
			mntext[1]="June";  
		}else if(arr[1]==7)
		{
			mntext[1]="July";  
		}else if(arr[1]==8)
		{
			mntext[1]="August";  
		}else if(arr[1]==9)
		{
			mntext[1]="September";  
		}else if(arr[1]==10)
		{
			mntext[1]="October";  
		}else if(arr[1]==11)
		{
			mntext[1]="November";  
		}else if(arr[1]==12)
		{
			mntext[1]="December";  
		}
		
		if(arr[2]==1)
		{
			mntext[2]="January";  
		}else if(arr[2]==2)
		{
			mntext[2]="February";  
		}else if(arr[2]==3)
		{
			mntext[2]="March";  
		}else if(arr[2]==4)
		{
			mntext[2]="April";  
		} else if(arr[2]==5)
		{
			mntext[2]="May";  
		}else if(arr[2]==6)
		{
			mntext[2]="June";  
		}else if(arr[2]==7)
		{
			mntext[2]="July";  
		}else if(arr[2]==8)
		{
			mntext[2]="August";  
		}else if(arr[2]==9)
		{
			mntext[2]="September";  
		}else if(arr[2]==10)
		{
			mntext[2]="October";  
		}else if(arr[2]==11)
		{
			mntext[2]="November";  
		}else if(arr[2]==12)
		{
			mntext[2]="December";  
		}
		
		   document.forms[0].txtCB_Month.length=0;
         var txtCB_Month = document.forms[0].txtCB_Month;
           
         for(var i=0; i<arr.length; i++)
             {
                 var opt = document.createElement('option');
                 opt.value = arr[i];
                 opt.innerHTML = mntext[i]; //instead of using textnode ,we use innerhtml
                 txtCB_Month.appendChild(opt);
             }



if(arr[0]==1)
{
	document.frmBRS_Bank_Balance_Update.txtPS_Year.value=parseInt(year)-1;
	document.getElementById("txtPS_Month").options[document.getElementById("txtPS_Month").selectedIndex].text="December";
	document.getElementById("txtPS_Month").options[document.getElementById("txtPS_Month").selectedIndex].value=12;
	
}
			else{
			var passmn=parseInt(arr[0])-1;
			var passtext;
			if(passmn==1)
			{
				passtext="January";  
			}else if(passmn==2)
			{
				passtext="February";  
			}else if(passmn==3)
			{
				passtext="March";  
			}else if(passmn==4)
			{
				passtext="April";  
			} else if(passmn==5)
			{
				passtext="May";  
			}else if(passmn==6)
			{
				passtext="June";  
			}else if(passmn==7)
			{
				passtext="July";  
			}else if(passmn==8)
			{
				passtext="August";  
			}else if(passmn==9)
			{
				passtext="September";  
			}else if(passmn==10)
			{
				passtext="October";  
			}else if(passmn==11)
			{
				passtext="November";  
			}else if(passmn==12)
			{
				passtext="December";  
			}
			document.frmBRS_Bank_Balance_Update.txtPS_Year.value=year;
			document.getElementById("txtPS_Month").options[document.getElementById("txtPS_Month").selectedIndex].text=passtext;
			document.getElementById("txtPS_Month").options[document.getElementById("txtPS_Month").selectedIndex].value=passmn;
			
			}
}

function isDate(dtStr){
	var daysInMonth = DaysArray(12);
	var pos1=dtStr.indexOf(dtCh);
	var pos2=dtStr.indexOf(dtCh,pos1+1);
	var strDay=dtStr.substring(0,pos1);
	var strMonth=dtStr.substring(pos1+1,pos2);
	var strYear=dtStr.substring(pos2+1);
	strYr=strYear
	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
	}
	month=parseInt(strMonth);
	day=parseInt(strDay);
	year=parseInt(strYr);
	if (pos1==-1 || pos2==-1){
		alert("The date format should be : dd/mm/yyyy")
		return false;
	}
	if (strMonth.length<1 || month<1 || month>12){
		alert("Please enter a valid month")
		return false;
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		alert("Please enter a valid day")
		return false;
	}
	if (strYear.length != 4 || year==0){
		alert("Please enter a valid 4 digit year")
		return false;
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		alert("Please enter a valid date")
		return false;
	}
return true;
}

function validateDate(dt){
	
	if (!isDate(dt)){	
		dt='';
		return false;
	}
    return true;
 }
/*For Checking Future Date*/

function validateFuture(dat)
{
        var jsonrpc = null;
		if(jsonrpc == null)
		{
			jsonrpc= new JSONRpcClient("JSON-RPC");
		}
		 var dt = jsonrpc.utility.getCurrentDateString();
		 
		 var serverDate=new Array();
		  serverDate = dt.split("/");
		// alert("noe");
      //  var day = dt.getDate();
      //	var month = dt.getMonth();
      //  var year = dt.getFullYear();
        
        var splitDate = new Array();
       
        splitDate = dat.split("/");
        
        
       //  alert("serverDate[0]="+serverDate[0]+"splitDate[0]"+splitDate[0]);
      //   alert("serverDate[1]="+serverDate[1]+"splitDate[1]"+splitDate[1]);
       //  alert("serverDate[2]="+serverDate[2]+"splitDate[2]"+splitDate[2]);
        if(eval(splitDate[2]) > eval(serverDate[2]))
        {
                alert('Enter Past or Current Date');
                return false;
        }
        else if(eval(splitDate[2]) < eval(serverDate[2]))
        {       
                return true;
        }
        else if(eval(splitDate[2]) == eval(eval(serverDate[2])))
        {
                if(eval(splitDate[1]) > eval(serverDate[1] ))
                {
                        alert('Enter Past or Current Date');
                        return false;
                }
                else if(eval(splitDate[1]) < eval(serverDate[1]))
                {                      
                        return true;
                }
                else if(eval(splitDate[1]) == eval(serverDate[1]))
                {
                        if(eval(splitDate[0]) > eval(serverDate[0]))
                        {
                                alert('Enter Past or Current Date');
                                return false;
                        }
                        else if(eval(splitDate[0]) <= eval(serverDate[0]))
                        {                                
                                return true;
                        }
                }
        }

        return true;
}

var winAccHeadCode;

window.onunload=function()
{
if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
if (listPopupwindow && listPopupwindow.open && !listPopupwindow.closed) listPopupwindow.close();
}
function clearall()
{
      
	   loadyear_month();
	   document.frmBRS_Bank_Balance_Update.txtPS_Year.value="";
	   document.frmBRS_Bank_Balance_Update.txtPS_Month.value="s";
       document.frmBRS_Bank_Balance_Update.txtPS_PrintDate.value="";
       document.frmBRS_Bank_Balance_Update.txtRemarks.value="";
       document.frmBRS_Bank_Balance_Update.txtBank_Bal_PS.value="";
       document.frmBRS_Bank_Balance_Update.radBankBalCrDrUpdate[0].checked=true;
       var list1=document.getElementById("listBank_AcNO");
       list1.length=0;
       list1.value="s";
       document.frmBRS_Bank_Balance_Update.cmdAdd.disabled = false;
		//document.frmBRS_Bank_Balance_Update.cmdDelete.disabled =true;
		document.frmBRS_Bank_Balance_Update.cmdUpdate.disabled = true;
       LoadAccountingUnitID('LIST_ALL_UNITS');
       setTimeout('loadBankDetails()',500);
      
    
   
}
function clearallCp()
{
      
	   loadyear_month();
	   document.frmBRS_Bank_Balance_Update.txtPS_Year.value="";
	   document.frmBRS_Bank_Balance_Update.txtPS_Month.value="s";
       document.frmBRS_Bank_Balance_Update.txtPS_PrintDate.value="";
       document.frmBRS_Bank_Balance_Update.txtRemarks.value="";
       document.frmBRS_Bank_Balance_Update.txtBank_Bal_PS.value="";
       document.frmBRS_Bank_Balance_Update.radBankBalCrDrUpdate[0].checked=true;
       var list1=document.getElementById("listBank_AcNO");
       list1.length=0;
       list1.value="s";
       
       LoadAccountingUnitID('LIST_ALL_UNITS');
       setTimeout('loadBankDetails()',500);
    /*   document.frmBRS_Bank_Balance_Update.cmdAdd.disabled = false;*/
		/*document.frmBRS_Bank_Balance_Update.cmdDelete.disabled =true;*/
		/*document.frmBRS_Bank_Balance_Update.cmdUpdate.disabled = true;*/
    
   
}

/**
 *  Number Checking --1 
 */
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
                return false; 
        }
}


/**
 *  Number Checking --2 
 */

function numbersonly(e,t)
{
         var unicode=e.charCode? e.charCode : e.keyCode;
         if(unicode==13)
         {
                try{t.blur(); }catch(e){}
                return true;
        
         }
         if (unicode!=8 && unicode !=9  )
         {
                if (unicode<48||unicode>57 ) 
                {
                     return false 
                }
         }
} 


function filter_real(evt,item,n,pre)
{
         var charCode = (evt.which) ? evt.which : event.keyCode
         // allow "." for one time 
         if(charCode==46)
         {                
                if(item.value.indexOf(".")<0)	return (item.value.length>0)?true:false;
                else return false;
         }
         if (!(charCode > 31 && (charCode < 48 || charCode > 57)))
         {
                        // to avoid over flow
                if(item.value.indexOf(".")<0)
                {
                        //alert("Length without . ="+item.value.length); 
                        return (item.value.length<n)?true:false;
                }
                // dont allow more than 2 precision no's after the point
                if(item.value.indexOf(".")>0)
                {
                        //alert("precision count ="+item.value.split(".")[1].length);
                        if(item.value.split(".")[1].length<pre) return true;
                        else return false;
                }
                return false;
         }else
         {
                return false;
         }
}
     
function callList()
{
	 var unitid=document.getElementById("cmbAcc_UnitCode").value;
	 var txtCB_Year=document.getElementById("txtPS_Year").value;
	 var txtCB_Month=document.getElementById("txtPS_Month").value;
	 if(txtCB_Year=="")
	 {
		 alert("Enter CashBook Year");
		 return false;
	 }
	 if(txtCB_Month=="s")
	 {
		 alert("Choose CashBook Month");
		 return false;
	 }
    winemp= window.open("BRS_Bank_Balance_Update_list.jsp?unitid="+unitid+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month,"list1","status=1,height=500,width=600,resizable=YES,scrollbars=yes"); 
    winemp.moveTo(300,300);  
    winemp.focus();
}
function callListcp()
{
	 var unitid=document.getElementById("cmbAcc_UnitCode").value;
	 var txtCB_Year=document.getElementById("txtCB_Year").value;
	 var txtCB_Month=document.getElementById("txtCB_Month").value;
	 var opt=get_radio_value();
	 if(opt=="Yearwise")
	 {
		 txtCB_Year=document.getElementById("txtCB_Year1").value;
		 
		 if(txtCB_Year=="")
		 {
			 alert("Enter CashBook Year");
			 return false;
		 } 
		 var url ="../../../../../BRS_Bank_Balance_Update?command=loadNonUpdate&unitid="+unitid+"&txtCB_Year="+txtCB_Year+"&txtoption="+opt;
	//alert("url >> "+url);
	 }
	 else
	 {
	 if(txtCB_Year=="")
	 {
		 alert("Enter CashBook Year");
		 return false;
	 }
	 if(txtCB_Month=="s")
	 {
		 alert("Choose CashBook Month");
		 return false;
	 }
	 var url ="../../../../../BRS_Bank_Balance_Update?command=loadNonUpdate&unitid="+unitid+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtoption="+opt;
	 }
				
		
		var xmlrequest = getTransport();
		xmlrequest.open("GET", url, true);
		xmlrequest.onreadystatechange = function() {
			
			manipulate1(xmlrequest);
		};
		xmlrequest.send(null);	
} 
function checkMonthYear(){
	 if(parseInt(document.frmBRS_Bank_Balance_Update.txtPS_Year.value)>parseInt(document.frmBRS_Bank_Balance_Update.txtCB_Year.value))
	    {
	        alert("PassSheet Year should less than CashBook Year");
	        document.frmBRS_Bank_Balance_Update.txtPS_Year.value="";
	        document.frmBRS_Bank_Balance_Update.txtPS_Month.value="s";
	        return false;
	    }
	
	 return true;
}
function checkMonth(){
	 if(parseInt(document.frmBRS_Bank_Balance_Update.txtPS_Year.value)==parseInt(document.frmBRS_Bank_Balance_Update.txtCB_Year.value))
	    {
	       // alert("PassSheet Year should less than CashBook Year");
	        if(parseInt(document.frmBRS_Bank_Balance_Update.txtPS_Month.value)>parseInt(document.frmBRS_Bank_Balance_Update.txtCB_Month.value)){
	        	alert("PassSheet Month should less than CashBook Month");
	        	//document.frmBRS_Bank_Balance_Update.txtPS_Year.value="";
		        document.frmBRS_Bank_Balance_Update.txtPS_Month.value="s";
	        	 return false;
	        }
	        
	      
	    }
	
	return true;
}
function checkDatePS(){
	if(document.frmBRS_Bank_Balance_Update.txtPS_Year.value==0)
    {
        alert("First Enter the PassSheet Year");
        document.frmBRS_Bank_Balance_Update.txtPS_Year.focus();
        return false;
    } 
	if(document.frmBRS_Bank_Balance_Update.txtPS_Month.value=="s")
    {
        alert("First Select the PassSheet Month");
       // document.frmBRS_Bank_Balance_Update.listBank_AcNO.focus();
        return false;
    } 
	
	var psdate=document.frmBRS_Bank_Balance_Update.txtPS_PrintDate.value;
	/*if(!psdate.match(/^(0[1-9]|[12][0-9]|3[01])[\- \/.](?:(0[1-9]|1[012])[\- \/.](19|20)[0-9]{2})$/))
			{
		alert("invalid date Format");
	}*/
	
    var psdate1=psdate.split("/");
    if(psdate1[2]>document.frmBRS_Bank_Balance_Update.txtPS_Year.value){
		alert("passsheet printed year should less than passsheet year");
		document.frmBRS_Bank_Balance_Update.txtPS_PrintDate.value="";
		return false;
    }    
	if(parseInt(psdate1[2])==parseInt(document.frmBRS_Bank_Balance_Update.txtPS_Year.value)){
		//alert("inside <=");
		//alert((parseInt(psdate1[1])>parseInt(document.frmBRS_Bank_Balance_Update.txtPS_Month.value))+"---"+psdate1[1]+">"+document.frmBRS_Bank_Balance_Update.txtPS_Month.value);
		//alert(psdate1[1]+">"+document.frmBRS_Bank_Balance_Update.txtPS_Month.value);
		if(parseInt(psdate1[1])>parseInt(document.frmBRS_Bank_Balance_Update.txtPS_Month.value)){
			alert("passsheet printed Month should less passsheet Month");
			document.frmBRS_Bank_Balance_Update.txtPS_PrintDate.value="";
			if(parseInt(psdate1[0])<0 || parseInt(psdate1[0])>32){
				alert("Invalid Date ");
				return false;	
			}
			return false;
		}	
		
	}
	validateDate(psdate);
}
     
function nullcheck()
{
      
    if(document.frmBRS_Bank_Balance_Update.txtBank_Bal_PS.value.length==0 )
    {
        alert("Enter the Pass sheet Balance");
        document.frmBRS_Bank_Balance_Update.txtBank_Bal_PS.focus();
        return false;
    }  
      
    if(document.frmBRS_Bank_Balance_Update.txtRemarks.value.length==0)
    {
        alert("Enter the Remark");
        document.frmBRS_Bank_Balance_Update.txtRemarks.focus();
        return false;
    } 
    if(document.frmBRS_Bank_Balance_Update.txtPS_PrintDate.value.length==0)
    {
        alert("Enter the PassSheet Date");
        document.frmBRS_Bank_Balance_Update.txtPS_PrintDate.focus();
        return false;
    }  
    if(document.frmBRS_Bank_Balance_Update.listBank_AcNO.value==0)
    {
        alert("Enter the Bank Account");
       // document.frmBRS_Bank_Balance_Update.listBank_AcNO.focus();
        return false;
    } 
    if(document.frmBRS_Bank_Balance_Update.txtPS_Year.value==0)
    {
        alert("Enter the PassSheet Year");
        document.frmBRS_Bank_Balance_Update.txtPS_Year.focus();
        return false;
    } 
    if(document.frmBRS_Bank_Balance_Update.txtPS_Month.value=="s")
    {
        alert("Select the PassSheet Month");
       // document.frmBRS_Bank_Balance_Update.listBank_AcNO.focus();
        return false;
    } 
  /*  if(document.frmBRS_Bank_Balance_Update.txtPS_Year.value>document.frmBRS_Bank_Balance_Update.txtCB_Year.value)
    {
        alert("PassSheet Year should less than CashBook Year");
        document.frmBRS_Bank_Balance_Update.txtPS_Year.value="";
        document.frmBRS_Bank_Balance_Update.txtPS_Month.value="s";
        return false;
    }
 if(document.frmBRS_Bank_Balance_Update.txtPS_Year.value==document.frmBRS_Bank_Balance_Update.txtCB_Year.value)
    {
       // alert("PassSheet Year should less than CashBook Year");
        if(parseInt(document.frmBRS_Bank_Balance_Update.txtPS_Month.value)>parseInt(document.frmBRS_Bank_Balance_Update.txtCB_Month.value)){
        	alert("PassSheet Month should less than CashBook Month");
        	// document.frmBRS_Bank_Balance_Update.txtPS_Year.value="";
 	        document.frmBRS_Bank_Balance_Update.txtPS_Month.value="s";
        	 return false;
        }
    }*/
 /*var psdate=document.frmBRS_Bank_Balance_Update.txtPS_PrintDate.value;
 var psdate1=psdate.split("/");
 if(psdate1[2]>document.frmBRS_Bank_Balance_Update.txtPS_Year.value){
		alert("Passsheet printed year should less than passsheet year");
		document.frmBRS_Bank_Balance_Update.txtPS_PrintDate.value="";
		return false;
 }    
	if(parseInt(psdate1[2])==parseInt(document.frmBRS_Bank_Balance_Update.txtPS_Year.value)){
		if(parseInt(psdate1[1])>parseInt(document.frmBRS_Bank_Balance_Update.txtPS_Month.value)){
			alert("Passsheet printed Month should less passsheet Month");
			document.frmBRS_Bank_Balance_Update.txtPS_PrintDate.value="";
			if(parseInt(psdate1[0])<0 || parseInt(psdate1[0])>32){
				alert("Invalid Date ");
				return false;	
			}
			return false;
		}	
		
	}*/
    
    return true;
    
}

function loadBankDetails(){
	//alert("load bank details ");
	
	// var Acc_UnitCode=document.frmBRS_Bank_Balance_Update.cmbAcc_UnitCode.value;
	 var unitid=document.getElementById("cmbAcc_UnitCode").value;
	 document.frmBRS_Bank_Balance_Update.txtRemarks.value="";
	 document.frmBRS_Bank_Balance_Update.txtPS_PrintDate.value="";		
	 document.frmBRS_Bank_Balance_Update.txtBank_Bal_PS.value="";
	 document.frmBRS_Bank_Balance_Update.radBankBalCrDrUpdate[0].checked=true;
	 var li = document.getElementById("listBank_AcNO");
		li.length=0;
		//li.value="s";
		/*var op = document.createElement("OPTION");
		op.text="Select Bank";
		op.value="s";*/
		var url ="../../../../../BRS_Bank_Balance_Update?command=loadBankDetails&unitid="+unitid ;			
		
		var xmlrequest = getTransport();
		xmlrequest.open("GET", url, true);
		xmlrequest.onreadystatechange = function() {
		
			manipulate1(xmlrequest);
		};
		xmlrequest.send(null);	
}



function manipulate1(xmlrequest) {
	

	if (xmlrequest.readyState == 4) {
		
		if (xmlrequest.status == 200) {
			
			
			var baseResponse1 = xmlrequest.responseXML.getElementsByTagName("response")[0];
			
		
			var tagCommand = baseResponse1.getElementsByTagName("command")[0];
		

			var command = tagCommand.firstChild.nodeValue;
			//alert('4'+command);
			
			 if (command=="bankDetailsLoad")
			{
				
				 var i = 0;
					var flag = baseResponse1.getElementsByTagName("flag")[0].firstChild.nodeValue;
					var count = baseResponse1.getElementsByTagName("count")[0].firstChild.nodeValue;
	
				    if(flag=="success"){
				 
				    	var len4 = baseResponse1.getElementsByTagName("bank_Detail").length;
				    	
				   
				    	
						var se = document.getElementById("listBank_AcNO");
						se.length=0;
						se.value='s';
						for (i=0 ; i < len4; i++) {
							var detail_bank = baseResponse1
									.getElementsByTagName("bankNo")[i].firstChild.nodeValue;
							var desc = baseResponse1
									.getElementsByTagName("bank_Detail")[i].firstChild.nodeValue;
							var op = document.createElement("OPTION");
							op.value = detail_bank;
							var txt = document.createTextNode(desc);
							op.appendChild(txt);
							se.appendChild(op);
						}
				        }
				        else
				        {
				        alert("No Record Exist");
				     
				        
				        }
				//unitLoad(baseResponse);
			}else if(command=="loadNonUpdate"){
				 var i = 0;
				 var seq=0;
					var flag = baseResponse1.getElementsByTagName("flag")[0].firstChild.nodeValue;
					//var count = baseResponse1.getElementsByTagName("count")[0].firstChild.nodeValue;
				 //	alert("flag "+flag);
					var tbody=document.getElementById("tblList");
				    
                    var t=0;
                    for(t=tbody.rows.length-1;t>=0;t--)
                    {
                       tbody.deleteRow(0);
                    }
					if(flag=="success"){
				    
			
				var se = baseResponse1.getElementsByTagName("ACCOUNTING_UNIT_ID");
				//se.length=0;
				//se.value='s';
				//alert("length >>  "+se.length);
				for (i=0 ; i < se.length; i++) {
				//	var accounting_unit_name = baseResponse1.getElementsByTagName("accounting_unit_name")[i].firstChild.nodeValue;
				//	var ACCOUNTING_UNIT_ID = baseResponse1.getElementsByTagName("ACCOUNTING_UNIT_ID")[i].firstChild.nodeValue;
					var AC_OPENING_DATE = baseResponse1.getElementsByTagName("AC_OPENING_DATE")[i].firstChild.nodeValue;
					if(AC_OPENING_DATE=='null')AC_OPENING_DATE="";
					var bank_det = baseResponse1.getElementsByTagName("bank_det")[i].firstChild.nodeValue;
					var BANK_AC_NO = baseResponse1.getElementsByTagName("BANK_AC_NO")[i].firstChild.nodeValue;
					var account_type = baseResponse1.getElementsByTagName("account_type")[i].firstChild.nodeValue;
					var ac_operational_mode = baseResponse1.getElementsByTagName("ac_operational_mode")[i].firstChild.nodeValue;
					var initial_deposit_amt = baseResponse1.getElementsByTagName("initial_deposit_amt")[i].firstChild.nodeValue;
					var status = baseResponse1.getElementsByTagName("status")[i].firstChild.nodeValue;
					var CB_YEAR = baseResponse1.getElementsByTagName("CB_YEAR")[i].firstChild.nodeValue;
					
					var cb_month = baseResponse1.getElementsByTagName("cb_month")[i].firstChild.nodeValue;
				
					var ps_date = baseResponse1.getElementsByTagName("ps_date")[i].firstChild.nodeValue;
					if(ps_date=='null' || ps_date==0)ps_date="";
					
					 tbody=document.getElementById("tblList");
	                   var mycurrent_row=document.createElement("TR");
	                   seq=seq+1;
	                 //   mycurrent_row.id=seq;
	                   //alert("row ID"+seq);
	                    var cell=document.createElement("TD");
	                    var currentText=document.createTextNode(seq);
	                   cell.appendChild(currentText);
	                    mycurrent_row.appendChild(cell);
	                    
	                    var cell2;
	                   //1
						                    cell2 = document.createElement("TD");
						var currentText = document.createTextNode(bank_det);
						cell2.appendChild(currentText);
						mycurrent_row.appendChild(cell2);

						cell2 = document.createElement("TD");
						var currentText = document.createTextNode(account_type);
						cell2.appendChild(currentText);
						mycurrent_row.appendChild(cell2);

						cell2 = document.createElement("TD");
						var currentText = document
								.createTextNode(ac_operational_mode);
						cell2.appendChild(currentText);
						mycurrent_row.appendChild(cell2);

						cell2 = document.createElement("TD");
						var currentText = document.createTextNode(BANK_AC_NO);
						cell2.appendChild(currentText);
						mycurrent_row.appendChild(cell2);
						
						var cell21 = document.createElement("TD");
						var currentText = document
								.createTextNode(CB_YEAR);
						cell21.appendChild(currentText);
						mycurrent_row.appendChild(cell21);
						
						var cell22 = document.createElement("TD");
						var currentText = document
								.createTextNode(cb_month);
						cell22.appendChild(currentText);
						mycurrent_row.appendChild(cell22);

						cell2 = document.createElement("TD");
						var currentText = document
								.createTextNode(initial_deposit_amt);
						cell2.appendChild(currentText);
						mycurrent_row.appendChild(cell2);
						
						
						cell2 = document.createElement("TD");
						var currentText = document
								.createTextNode(ps_date);
						cell2.appendChild(currentText);
						mycurrent_row.appendChild(cell2);

						cell2 = document.createElement("TD");
						var currentText = document
								.createTextNode(AC_OPENING_DATE);
						cell2.appendChild(currentText);
						mycurrent_row.appendChild(cell2);

						cell2 = document.createElement("TD");
						var currentText = document.createTextNode(status);
						cell2.appendChild(currentText);
						mycurrent_row.appendChild(cell2);
	                             
	                       tbody.appendChild(mycurrent_row);
	                            
				
				
				}
				    }  else if(flag=="NODTA")
				        {
				        alert("No Record Exist");
				     
				        
				        }else{
				        	 alert("Error ... ");
				        }
			}
			
	}
}
}

/**
 * List Function
 */
/*
 * var listPopupwindow; function ListAll() {
 * if(document.frmBRS_Bank_Balance_Update.txtCB_Year.value.length==0) {
 * alert("Enter Cashbook Year");
 * document.frmBRS_Bank_Balance_Update.cmbAcHeadCode.focus(); return false; }
 * 
 * var Acc_UnitCode=document.frmBRS_Bank_Balance_Update.cmbAcc_UnitCode.value;
 * var OffCode=document.frmBRS_Bank_Balance_Update.cmbOffice_code.value; var
 * CashbookYear=document.frmBRS_Bank_Balance_Update.txtCB_Year.value; var
 * CashbookMonth=document.frmBRS_Bank_Balance_Update.txtCB_Month.value;
 * listPopupwindow=
 * window.open("ListofGeneralLedgerMainForm_OC.jsp?cmbAcc_UnitCode="+Acc_UnitCode+"&cmbOffice_code="+OffCode+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth,"mywindow1","status=1,height=400,width=500,resizable=YES,
 * scrollbars=yes"); listPopupwindow.moveTo(250,250);
 * document.frmBRS_Bank_Balance_Update.txtCB_Month.disabled=true;
 *  }
 * 
 * 
 */
function doFunction(Command,param)
{  
    var Acc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    	//document.frmBRS_Bank_Balance_Update.cmbAcc_UnitCode.value;
    var OffCode=document.getElementById("cmbOffice_code").value;  
    var Balupd;
    if(document.frmBRS_Bank_Balance_Update.radBankBalCrDrUpdate[0].checked==true)
    {
        Balupd=document.getElementById("radBankBalCrDrUpdate").value;
    }
    else
    {
        Balupd=document.frmBRS_Bank_Balance_Update.radBankBalCrDrUpdate[1].value;
    }
    var CashbookYear;
    var CashbookMonth;
   // var CashbookYear=document.getElementById("txtCB_Year").value;
    //var CashbookMonth=document.getElementById("txtCB_Month").value;
    var passsheetYear1=document.getElementById("txtPS_Year").value;
    var passsheetMonth1=document.getElementById("txtPS_Month").value;
    if (passsheetMonth1==12) {
    	var x=1;
    	  CashbookYear= +passsheetYear1 + +x;
    	 
    	     CashbookMonth=1;
	} else {
		var y=1;
		CashbookYear=passsheetYear1;
		//alert(passsheetMonth1);
	    CashbookMonth= +passsheetMonth1 + +y;
	   // alert(CashbookMonth);

	}
    
    var PS_PrintDate=document.getElementById("txtPS_PrintDate").value;
    var Bank_AcNO = document.getElementById("listBank_AcNO").value;
   // alert(Bank_AcNO);if
    var Bank_Bal_PS=document.getElementById("txtBank_Bal_PS").value;
    var Remarks=document.getElementById("txtRemarks").value;
       
        if(Command=="Add")
        {
        	if(document.frmBRS_Bank_Balance_Update.txtBank_Bal_PS.value==0 )
            {
        		alert("You are requested to enter the accurate figures for the Bank Balance in this system. The person who enters the details will be responsible for the incorrect figures.");
	        	
            }
        	
           if(nullcheck())
            {
                var url="../../../../../BRS_Bank_Balance_Update?command=Add&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&Balupd="+Balupd+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth+"&passsheetYear1="+passsheetYear1+"&passsheetMonth1="+passsheetMonth1+"&PS_PrintDate="+PS_PrintDate+"&Bank_AcNO="+Bank_AcNO+"&Bank_Bal_PS="+Bank_Bal_PS+"&Remarks="+Remarks;
                var req=getTransport();
                req.open("GET",url,true);         
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                };   
                        req.send(null);
           }
        	
    }
    else if(Command=="Update")
    {
               
                if(nullcheck())
                {
                    var url="../../../../../BRS_Bank_Balance_Update?command=Update&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&Balupd="+Balupd+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth+"&passsheetYear1="+passsheetYear1+"&passsheetMonth1="+passsheetMonth1+"&PS_PrintDate="+PS_PrintDate+"&Bank_AcNO="+Bank_AcNO+"&Bank_Bal_PS="+Bank_Bal_PS+"&Remarks="+Remarks;
                    
                   var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    };  
                            req.send(null);                 
                }  
    }
    else if(Command=="Delete")
    {
        if(confirm("Do u really want to delete the record"))
        {
                if(nullcheck())
                {
                    var url="../../../../../BRS_Bank_Balance_Update?command=Delete1&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&Balupd="+Balupd+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth+"&passsheetYear1="+passsheetYear1+"&passsheetMonth1="+passsheetMonth1+"&PS_PrintDate="+PS_PrintDate+"&Bank_AcNO="+Bank_AcNO+"&Bank_Bal_PS="+Bank_Bal_PS+"&Remarks="+Remarks;
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    };   
                            req.send(null);
                }
         }
         else
         {
            alert("Record not Deleted");
         }
    }else if(Command=="loadDetails"){
    	var url="../../../../../BRS_Bank_Balance_Update?command=loadDetails&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&Balupd="+Balupd+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth+"&passsheetYear1="+passsheetYear1+"&passsheetMonth1="+passsheetMonth1+"&PS_PrintDate="+PS_PrintDate+"&Bank_AcNO="+Bank_AcNO+"&Bank_Bal_PS="+Bank_Bal_PS+"&Remarks="+Remarks;
    	//alert("loadDetails "+url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse(req);
        };   
                req.send(null);
    }
   

    
}


function doParentEmp(unitid,accNo,year,mn)    
{  
	//alert('test');
  document.getElementById("cmdUpdate").disabled=false;
    var url="../../../../../BRS_Bank_Balance_Update?command=retrieve&unitid="+unitid+"&accNo="+accNo+"&year="+year+"&mn="+mn;
    var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
            	handleResponse(req);
            };   
            req.send(null);
          
}  
/*function SubLedgerReturnResponse(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
            {
                var headname=baseResponse.getElementsByTagName("headcode")[0].firstChild.nodeValue;
                document.frmBRS_Bank_Balance_Update.txtaccountheadname.value="";
                document.frmBRS_Bank_Balance_Update.txtaccountheadname.value=headname;
            }
            else
            {
               
                document.frmBRS_Bank_Balance_Update.cmbAcHeadCode.value="";
                document.frmBRS_Bank_Balance_Update.txtaccountheadname.value="";
                alert("Invalid HeadCode");
                document.frmBRS_Bank_Balance_Update.cmbAcHeadCode.focus();
            }
        }
    }
}*/


function handleResponse(req)
{  
     
    if(req.readyState==4)
    { 
        if(req.status==200)
        {  
           
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="office")
            {
                loadOffice(baseResponse);
            }
            else if(Command=="Add")
            {
                addRow(baseResponse);
            }
            else if(Command=="Update")
            {
                updateRow(baseResponse);
            }
            else if(Command=="Delete")
            {
                deleteRow(baseResponse);
            }
            else if(Command=="LoadTable"){	
            	loadTableValue(baseResponse);
            }
            else if(Command=="retrieve"){
            	retrieve_new(baseResponse);
            }else if(Command=="loadDetails"){
            	loadDetails1(baseResponse);
            	
            }
            /////////////////////////////////////////////  For MASTER Combo SL Code //////////////////////////////////
         }
    }
}
function loadDetails1(baseResponse)
{
 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {   

		var remark=baseResponse.getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
		 document.getElementById("txtRemarks").value=remark;
		 document.getElementById("txtRemarks").disabled=true;
		var psdate=baseResponse.getElementsByTagName("psdate")[0].firstChild.nodeValue;
		var psdate1=psdate.split("-");
		var year1=psdate1[2]+"/"+psdate1[1]+"/"+psdate1[0];

		 document.getElementById("txtPS_PrintDate").value=year1;
		 
		var psbal=baseResponse.getElementsByTagName("psbal")[0].firstChild.nodeValue;
		 document.getElementById("txtBank_Bal_PS").value=psbal;
		 document.getElementById("txtBank_Bal_PS").disabled=true;
		var crdr=baseResponse.getElementsByTagName("crdr")[0].firstChild.nodeValue;
		//alert(crdr);
		 document.getElementById("radBankBalCrDrUpdate").disabled=true;
		  if(crdr=="CR")
		         document.frmBRS_Bank_Balance_Update.radBankBalCrDrUpdate[0].checked=true;
		         else if(crdr=="DR")
		         document.frmBRS_Bank_Balance_Update.radBankBalCrDrUpdate[1].checked=true;

    }
    else if(flag=="failure")
    {
        alert("Please Enter The Details");
    	 //document.frmBRS_Bank_Balance_Update.cmdAdd.disabled
		 document.frmBRS_Bank_Balance_Update.txtRemarks.value="";
		 //document.frmBRS_Bank_Balance_Update.txtPS_PrintDate.value="";		
		 document.frmBRS_Bank_Balance_Update.txtBank_Bal_PS.value="";
		 document.frmBRS_Bank_Balance_Update.radBankBalCrDrUpdate[0].checked=true;
		 undisable();
    }
}
function retrieve_new(baseResponse)
{
 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {   
    	var psyear=baseResponse.getElementsByTagName("psyear")[0].firstChild.nodeValue;
		 document.getElementById("txtPS_Year").value=psyear;
		
		var psmon=baseResponse.getElementsByTagName("psmon")[0].firstChild.nodeValue;
		//alert(psmon);
		//var psmonno=baseResponse.getElementsByTagName("psmonno")[0].firstChild.nodeValue;
		
		document.getElementById("txtPS_Month").value=psmon;
         var bankdetail=baseResponse.getElementsByTagName("bank_Detail");//[0].firstChild.nodeValue;
         var bankid=baseResponse.getElementsByTagName("bankid");//[0].firstChild.nodeValue;
         var listBank_AcNO = document.getElementById("listBank_AcNO");
         listBank_AcNO.length=0;
		 var opt = document.createElement('option');
         opt.value = bankid[0].firstChild.nodeValue;
         opt.innerHTML = bankdetail[0].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
         listBank_AcNO.appendChild(opt);
		
		
		var remark=baseResponse.getElementsByTagName("remark")[0].firstChild.nodeValue;
		 document.getElementById("txtRemarks").value=remark;
		var psdate=baseResponse.getElementsByTagName("psdate")[0].firstChild.nodeValue;
		//alert(psdate);
		var psdate1=psdate.split("-");
		var year1=psdate1[2]+"/"+psdate1[1]+"/"+psdate1[0];
		
		//alert(year1);
		 document.getElementById("txtPS_PrintDate").value=year1;
		 
		var psbal=baseResponse.getElementsByTagName("psbal")[0].firstChild.nodeValue;
		 document.getElementById("txtBank_Bal_PS").value=psbal;
		var crdr=baseResponse.getElementsByTagName("crdr")[0].firstChild.nodeValue;
		//alert(crdr);
		  if(crdr=="CR")
		         document.frmBRS_Bank_Balance_Update.radBankBalCrDrUpdate[0].checked=true;
		         else if(crdr=="DR")
		         document.frmBRS_Bank_Balance_Update.radBankBalCrDrUpdate[1].checked=true;
	
		    document.frmBRS_Bank_Balance_Update.cmdAdd.disabled = true;
			document.frmBRS_Bank_Balance_Update.cmdDelete.disabled = false;
			document.frmBRS_Bank_Balance_Update.cmdUpdate.disabled = false;
    }
    else if(flag=="failure")
    {
        alert("cant load");
    }
}
function addRow(baseResponse)
{
 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {   
         alert("Record inserted successfully");
        clearall();
         //loadTable();
        
    }
    else if(flag=="notinsert")
    {
    	 alert("Records Failure");
    }
    else if(flag=="failure")
    {
        alert("Records not inserted");
    }
    else if(flag=="AlreadyExist")
    {
        alert("Details Already Exist");
        clearall();
    }
}
function updateRow(baseResponse)
{

 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {   
         alert("Record updated successfully");
         clearall();
         //document.frmBRS_Bank_Balance_Update.txtCB_Year.disabled=false;
        // document.frmBRS_Bank_Balance_Update.txtCB_Month.disabled=false;
         
    }
    else
    {
        alert("Record not updated");
    }
}


function deleteRow(baseResponse)
{
 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {   
         alert("Record deleted successfully");
         clearallCp();
    }
    else
    {
        alert("Record not deleted");
    }

}
/*function loadTableValue(baseResponse) 
{
	seq=0;
	var re_by_region=0;
	
		var tbody = document.getElementById("tblList");
		var t = 0;

		
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}
		var r_no = baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
		//alert("count  --->"+r_no);
	
		var item = new Array();
		//var st_name, st_group,head_acct,amt_allotted,budget_req,reason;
		var mycount=0;
		//alert("welcome...");
		for(var k = 0; k < r_no; k++) {
		
			var bank_name =baseResponse.getElementsByTagName("bank_name")[k].firstChild.nodeValue;
			var ac_type=baseResponse.getElementsByTagName("ac_type")[k].firstChild.nodeValue;
			var ac_no =baseResponse.getElementsByTagName("ac_no")[k].firstChild.nodeValue;
			var bank_bal=baseResponse.getElementsByTagName("bank_bal")[k].firstChild.nodeValue;			
			var dtorcr=baseResponse.getElementsByTagName("dtorcr")[k].firstChild.nodeValue;
			var remark=baseResponse.getElementsByTagName("remark")[k].firstChild.nodeValue;
			var budget_req=baseResponse.getElementsByTagName("budget_req")[k].firstChild.nodeValue;
			var reason=baseResponse.getElementsByTagName("reason")[k].firstChild.nodeValue;
			
			*//** Create Table Row *//*
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq;
            //alert(st_name+st_group+head_acct+amt_allotted+budget_req+reason);
			*//** Sl No *//*
			var cell0 = document.createElement("A");
			var url="javascript:loadPage("+mycurrent_row.id+")";
			cell0.href=url;
			var slno = document.createTextNode("EDIT");
			
			cell0.appendChild(slno);
			
			mycurrent_row.appendChild(cell0);
			
			


			var cell1 = document.createElement("TD");
			var bank_name1=document.createElement("input");
			bank_name1.type="hidden";
			bank_name1.name="bank_name"+seq;
			bank_name1.id="bank_name"+seq;
			bank_name1.value=bank_name;
			var st_name_no1=document.createElement("input");
			st_name_no1.type="hidden";
			st_name_no1.name="st_name_no"+seq;
			st_name_no1.id="st_name_no"+seq;
			st_name_no1.value=st_name_no;
			var bankname = document.createTextNode(bank_name);
			cell1.appendChild(bankname);
			cell1.appendChild(bank_name1);
			//cell1.appendChild(st_name_no1);
			mycurrent_row.appendChild(cell1);
			
			var cell2 = document.createElement("TD");
			var ac_type1=document.createElement("input");
			ac_type1.type="hidden";
			ac_type1.name="ac_type"+seq;
			ac_type1.id="ac_type"+seq;
			ac_type1.value=ac_type;
			var st_group_no1=document.createElement("input");
			st_group_no1.type="hidden";
			st_group_no1.name="st_group_no"+seq;
			st_group_no1.id="st_group_no"+seq;
			st_group_no1.value=st_group_no;
			var actype = document.createTextNode(ac_type);
			cell2.appendChild(actype);
			cell2.appendChild(ac_type1);
			//cell2.appendChild(st_group_no1);
			mycurrent_row.appendChild(cell2);
			
			var cell3 = document.createElement("TD");
			var ac_no1=document.createElement("input");
			ac_no1.type="hidden";
			ac_no1.name="ac_no"+seq;
			ac_no1.id="ac_no"+seq;
			ac_no1.value=ac_no;
			var acno = document.createTextNode(ac_no);
			cell3.appendChild(acno);
			cell3.appendChild(ac_no1);
			mycurrent_row.appendChild(cell3);
			
			var cell4 = document.createElement("TD");
			var bank_bal1=document.createElement("input");
			bank_bal1.type="hidden";
			bank_bal1.name="bank_bal"+seq;
			bank_bal1.id="bank_bal"+seq;
			bank_bal1.value=bank_bal;
			var bank_bal2 = document.createTextNode(bank_bal);
		    cell4.style.textAlign="right";
			cell4.appendChild(bank_bal2);
			cell4.appendChild(bank_bal1);
			mycurrent_row.appendChild(cell4);
			
			var cell5 = document.createElement("TD");
			var dtorcr1=document.createElement("input");
			dtorcr1.type="hidden";
			dtorcr1.name="dtorcr"+seq;
			dtorcr1.id="dtorcr"+seq;
			dtorcr1.value=dtorcr;
			var dtorcr2 = document.createTextNode(dtorcr);
			cell5.appendChild(dtorcr2);
			cell5.appendChild(dtorcr1);
			mycurrent_row.appendChild(cell5);
			
			var cell6 = document.createElement("TD");
			var remark1=document.createElement("input");
			remark1.type="hidden";
			remark1.name="remark"+seq;
			remark1.id="remark"+seq;
			remark1.value=remark;			
			var remark2 = document.createTextNode(remark);
			cell6.appendChild(remark2);
			cell6.appendChild(remark1);
			mycurrent_row.appendChild(cell6);
			
			tbody.appendChild(mycurrent_row);

			*//** Increment Sequence Number *//*
			seq = seq + 1;
		}
		
		//if
		document.frmBRS_Bank_Balance_Update.butSub.disabled = true;
		document.frmBRS_Bank_Balance_Update.butDelete.disabled = false;
		document.frmBRS_Bank_Balance_Update.butUpdate.disabled = false;
		
		
//	}
		
	//alert("seq:::"+seq);
	document.getElementById('RecordCount').value = seq;
	
}*/
/*
function loadOffice(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {  
       var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
       var oname=baseResponse.getElementsByTagName("oname")[0].firstChild.nodeValue;
       if(job_flag==true)
       if(job_flag==false)
            var option=document.createElement("OPTION");
            option.text=oname;
            option.value=oid;
           
    }
    else 
    {
     var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
     alert("Office Id '"+oid+"' doesn't Exist");
    }
    job_flag="";
}





function jobpopup_master()
{
    job_flag=true;
    jobpopup();
}*/


/*
var winjob;
function jobpopup()
{
    if(winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
}




function forChildOption()
{
      if (winjob && winjob.open && !winjob.closed) 
             winjob.officeSelection(true,true,true,false);
}
*/

/*
function doParentJob(jobid,deptid)
{
       if(deptid=='TWAD')
        {
            doFunction('office',jobid);
        }
        else
        {
                alert('Please select an Office ');
                if (winjob && winjob.open && !winjob.closed) 
                {
                   winjob.resizeTo(500,500);
                   winjob.moveTo(250,250); 
                   winjob.focus();
                }
                return false
        }
   
    return true
}*/


/*function valid_amt(field)
{
    
    amt=field.value;
    if(amt.indexOf(".")!=amt.lastIndexOf("."))
    {
        alert("Enter a Valid Amount");
        field.value="";
        field.focus();
    }
}



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
                return false; 
        }
      }
      else 
      return false;
      
}
*/

/**
 *  Exit Function 
 */

function Exit()
 {
    window.open('','_parent','');
    window.close();
 }

/*function loadAccountHeadCode(req){
	if (req.readyState == 4) {
		if (req.status == 200) {
			//alert("in added");
			response = req.responseXML.getElementsByTagName("response")[0];
			viewAccountHeadCode();
		}
	}
}*/
/*function viewAccountHeadCode(){
	var command = response.getElementsByTagName("command")[0].firstChild.nodeValue;
	var flag = response.getElementsByTagName("status")[0].firstChild.nodeValue;
	if(command=="getaccoff"){
		if(flag=="success"){
			var len=response.getElementsByTagName("ACCOUNTHEADCODE").length;
			var selectdiv=document.getElementById('cmbOffice_code');
			var listOpt=document.createElement("option");
			selectdiv.length=0;
			selectdiv.appendChild(listOpt);
			listOpt.text="select";
			listOpt.value="select";
			for(var i=0; i<len; i++){
				listOpt=document.createElement("option");
				selectdiv.appendChild(listOpt);
				listOpt.text=response.getElementsByTagName("ACCOUNTHEADNAME")[i].firstChild.nodeValue;
				listOpt.value=response.getElementsByTagName("ACCOUNTHEADCODE")[i].firstChild.nodeValue;
			}
			document.getElementById('cmbOffice_code').selectedIndex=1;
		}else{
			alert("There is no accounting for office code for this accounting unit code");
		}
	}else{
		alert("Process Failure");
	}
	
}*/