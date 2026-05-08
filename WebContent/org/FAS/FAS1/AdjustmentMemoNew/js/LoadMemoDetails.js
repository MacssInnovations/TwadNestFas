
var seq=0;


function loadMemoDetails() {
//	alert("Enter into load memo details");
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var txtCB_Year=document.getElementById("txtCB_Year").value;
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	var cmbAdviceNO=document.getElementById("cmbAdviceNO").value;
	var advice_type="";
	if(document.getElementsByName("advice_type")[0].checked==true){	
		 advice_type="CR";
	}else if(document.getElementsByName("advice_type")[1].checked==true){
		 advice_type="DR";
	}
	/*ch on 01/08/2016*/
	  var vsl=cmbAdviceNO.split("-");

	var url = "../../../../../GetMemoDetails?command=details&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year
	+"&cmbAdviceNO="+vsl[0]+"&txtCB_Month="+txtCB_Month+"&slno="+vsl[1]+"&advice_type="+advice_type;
	
//alert("advice number***"+cmbAdviceNO);
	//Changes on24/02/2017
//	var url = "../../../../../GetMemoDetails?command=details&cmbAcc_UnitCode="+cmbAcc_UnitCode+
//	"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year
//	+"&cmbAdviceNO="+cmbAdviceNO+"&txtCB_Month="+txtCB_Month; 
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate_new(xmlrequest);
		};

	xmlrequest.send(null);

}

function load_acc_units()
{
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var url = "../../../../../GetMemoDetails?command=load_acc_units&cmbAcc_UnitCode="+cmbAcc_UnitCode;
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate_new(xmlrequest);
		};

	xmlrequest.send(null);

}

function loadMemoNO() {
	
	// alert("Welcome loadMemoNO ");
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var txtCB_Year=document.getElementById("txtCB_Year").value;
	//alert("txtCB_Year==>"+txtCB_Year);
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	//alert("txtCB_Month===>"+txtCB_Month);
	var advice_type="";
	if(document.getElementsByName("advice_type")[0].checked==true){	
		 advice_type="CR";
	}else if(document.getElementsByName("advice_type")[1].checked==true){
		 advice_type="DR";
	}

	var url = "../../../../../GetMemoDetails?command=loadmomono&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Month="+txtCB_Month+"&txtCB_Year="+txtCB_Year
				+"&advice_type="+advice_type;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate_new(xmlrequest);
		};

	xmlrequest.send(null);

}
function loadMemoNO_reject() {
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var txtDate=(document.getElementById("txtDate").value).split("/");
	//var txtCB_Month=document.getElementById("txtCB_Month").value;

	var url = "../../../../../GetMemoDetails?command=loadmomono&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Month="+txtDate[1]+"&txtCB_Year="+txtDate[2];
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate_new(xmlrequest);
		};

	xmlrequest.send(null);

}
function loadMemoDetails_reject() {
	//alert("Enter into load memo details");
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var txtDate=(document.getElementById("txtDate").value).split("/");
	var cmbAdviceNO=document.getElementById("cmbAdviceNO").value;
	var vsl=cmbAdviceNO.split("-");

	var url = "../../../../../GetMemoDetails?command=details&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtDate[2]
	+"&cmbAdviceNO="+vsl[0]+"&txtCB_Month="+txtDate[1]+"&slno="+vsl[1];
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate_new(xmlrequest);
		};

	xmlrequest.send(null);

}
function AjaxFunction() {
	
	
	
	var xmlrequest = false;
	try {
		xmlrequest = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e1) {
		try {
			xmlrequest = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			xmlrequest = false;
		}
	}
	if (!xmlrequest && typeof XMLHttpRequest != 'undefined') {
		xmlrequest = new XMLHttpRequest();
	}
	return xmlrequest;
}
function getCurrentYear() 
{
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
           // alert("currentYear"+currentYear);
           //alert(" getCurrentYear"+getCurrentYear());
          //  alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
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
            return false;
    }
    
}

/*function call_date_TB(){
	alert("wel");
	 var txtDate1=document.getElementById("txtDate");
	 call_date(txtDate1);
}*/
function call_date(dateCtrl1)                        // TB_checking 
{
	//alert("call");
	   // call_clr();
	    if(checkdt(dateCtrl1))
	    {      
	  // 	alert("hi");
		      var  cmbAcc_UnitCode1=document.getElementById("cmbAcc_UnitCode").value;
		       var cmbOffice_code1=document.getElementById("cmbOffice_code").value;
		         var TB_date=dateCtrl1.value;
		     //  alert("inside first if ");
		         if(dateCtrl1.value.length!=0)
		         {
		        	        	 
			             var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode1+"&cmbOffice_code="+cmbOffice_code1;
			             var req=AjaxFunction();
			             req.open("GET",url,true); 
			            // alert("url "+url);
			             req.onreadystatechange=function()
			             {
			               check_TB(req,dateCtrl1);
			             } ;  
			             req.send(null);
		         }
	        
	  }
	   
}



function check_TB(req,dateCtrl1)
{
	    if(req.readyState==4)
	    {
		        if(req.status==200)
		        {  
			             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
			             var tagcommand=baseResponse.getElementsByTagName("command")[0];
			             var Command=tagcommand.firstChild.nodeValue;
			             var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			            
			             if(flag=="success")
			             {
			            	 loadMemoNO();
			            	 document.getElementById("head_div1").style.display="none";
		                  	 document.getElementById("head_div2").style.display="none";
			            	 	//doFunction('load_Receipt_No','null');                 //return true;
			             }
			             else if(flag=="failure")
			             {
			                    
			                    //return false;//
			                    
			                   // t.value=c;
			                   // var c=dateCtrl.value;
			                    var sc=dateCtrl1.value.split('/');
			                    var currenDay =sc[0];
			                    var currentMonth=sc[1];
			                    var currentYear=sc[2];
			                  //  alert("currentMonth "+currentMonth);
			                    if(currentMonth!="03"){
			                    //	alert("if"+currentMonth);
			                    	alert("Trial Balance Closed * * ");
			                    	document.getElementById("head_div1").style.display="none";
			                  		document.getElementById("head_div2").style.display="none";
			                  		dateCtrl1.value="";
				                    dateCtrl1.focus();
				                    
				                    document.getElementById("txtDate").value="";
			                        document.getElementById("txtDate").focus();
			                        document.getElementById("txtAcc_HeadCode").value="";
			                 		document.getElementById("txtRemarks1").value="";
			                 	    document.getElementById("txtAmount").value="";
			                 	    document.getElementById("txtsub_Amount").value="";
			                 	    document.getElementById("cmbAdviceNO").selectedIndex="";
			                 	    document.getElementById("txtDate").value="";
			                 	    document.getElementById("txtLetterNO").value="";
			                 	    document.getElementById("txtLetterDate").value="";
			                 	    document.getElementById("txtAuthority").value="";
			                 	    document.getElementById("txtParticular").value="";
			                 	    document.getElementById("cmbSL_Code").value="";
			                 	  document.Adjustment_Memo_Form1.txtDate.readOnly=true;
			                 	  
			                 	 alert(document.getElementById("txtDate").value);
			                 	   location.reload();
			                 	   
			                 	//  alert(document.getElementById("txtDate").value)
			                    }
			                    else{
			                    //	alert("else"+currentMonth);
			                    /*	
			                     * Hide on 02Jul20 by NandaKumar to avoid supplement for March month
			                     * and do the same as that of remaining period of month
			                     * 
			                     * document.getElementById("head_div1").style.display="block";
			                  		document.getElementById("head_div2").style.display="block";
			                  		Check_Supplement_No();
			                  		
			                  		*
			                  		*
			                  		*/
			                    	alert("Trial Balance Closed * * ");
			                    	document.getElementById("head_div1").style.display="none";
			                  		document.getElementById("head_div2").style.display="none";
			                  		dateCtrl1.value="";
				                    dateCtrl1.focus();
				                    
				                    document.getElementById("txtDate").value="";
			                        document.getElementById("txtDate").focus();
			                        document.getElementById("txtAcc_HeadCode").value="";
			                 		document.getElementById("txtRemarks1").value="";
			                 	    document.getElementById("txtAmount").value="";
			                 	    document.getElementById("txtsub_Amount").value="";
			                 	    document.getElementById("cmbAdviceNO").selectedIndex="";
			                 	    document.getElementById("txtDate").value="";
			                 	    document.getElementById("txtLetterNO").value="";
			                 	    document.getElementById("txtLetterDate").value="";
			                 	    document.getElementById("txtAuthority").value="";
			                 	    document.getElementById("txtParticular").value="";
			                 	    document.getElementById("cmbSL_Code").value="";
			                 	  document.Adjustment_Memo_Form1.txtDate.readOnly=true;
			                 	  
			                 	 alert(document.getElementById("txtDate").value);
			                 	   location.reload();
	
			                    	
			                    }
			                    
			               }
		        }
	    }
}

function Check_Supplement_No()
{
      var txtCrea_date=document.getElementById("txtDate").value;
     
       var url="../../../../../Supplement_Journal_Create.kv?Command=Check_Supplement_No&txtCrea_date="+txtCrea_date;
      // alert(url);
       var req=getTransport();
       req.open("GET",url,true); 
       req.onreadystatechange=function()
       {
           Check_Supplement_No_Response(req);
       };   
       req.send(null);
}

function Check_Supplement_No_Response(req)
{
  if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
           
//            if(Command=="get")
//            {
//                Load_SL_Code1(baseResponse);
//               
//            }
         if(flag=="failure")
              {
                var suppl_error=baseResponse.getElementsByTagName("suppl_error")[0].firstChild.nodeValue;
                alert(suppl_error);  
                //document.getElementById("txtCrea_date").value="";
              }
              else if(flag=="success")
              {
             
//              var supno=baseResponse.getElementsByTagName("supno")[0].firstChild.nodeValue;
           //   alert("supno"+supno);
              
                 var supNo1 = document.forms[0].supNo;
                 var supno = baseResponse.getElementsByTagName("supno"); 
                 var opt = document.createElement('option');
                 opt.length=0;
                 supNo1.length=1;
                 for(var i=0; i<supno.length; i++)
                     {
                         
                         opt.value = supno[i].firstChild.nodeValue;
                         opt.innerHTML = supno[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                         supNo1.appendChild(opt);
                     }
              
              
              }

       }
  }
}

function manipulate_new(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
			
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			var command = tagCommand.firstChild.nodeValue;
		//alert(command);

			if (command == "memodetails") {
				getValues(baseResponse);
			}
			else if(command=="loadmomono")
			{
				loadMomoNo(baseResponse);
			}
			else if(command=="load_acc_units")
			{
				loadaccunits(baseResponse);
			}

}
}
}

function loadaccunits(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if (flag == "success") {
			var cmbSL_Code = document.forms[0].cmbSL_Code;
            var cid = baseResponse.getElementsByTagName("cid"); 
            var cname = baseResponse.getElementsByTagName("cname");   
            for(var i=0; i<cid.length; i++)
                {
                    var opt = document.createElement('option');
                    opt.value = cid[i].firstChild.nodeValue;
                    opt.innerHTML = cname[i].firstChild.nodeValue;
                    cmbSL_Code.appendChild(opt);
                }
		
		}
		 else
		 {
			 alert("Failed to Load");
		 }
}

function getValues(baseResponse)
{
	//alert("enter into get values")
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert(flag);
	if (flag == "success") {
	
		
		document.getElementById("txtLetterNO").value=baseResponse.getElementsByTagName("lNo")[0].firstChild.nodeValue;
		document.getElementById("txtLetterDate").value=baseResponse.getElementsByTagName("lDate")[0].firstChild.nodeValue;
		if(baseResponse.getElementsByTagName("remarks")[0].firstChild!=null){
		document.getElementById("txtRemarks1").value=baseResponse.getElementsByTagName("remarks")[0].firstChild.nodeValue;
		}
		
		
	//	if (
  //  baseResponse.getElementsByTagName("remarks")[0] &&
  //  baseResponse.getElementsByTagName("remarks")[0].firstChild
//) {
    //document.getElementById("txtRemarks1").value =
    //    baseResponse.getElementsByTagName("remarks")[0].firstChild.nodeValue;
//}

		document.getElementById("txtAmount").value=baseResponse.getElementsByTagName("Amount")[0].firstChild.nodeValue;
		document.getElementById("txtsub_Amount").value=baseResponse.getElementsByTagName("Amount")[0].firstChild.nodeValue;
		document.getElementById("adjustmentDate").value=baseResponse.getElementsByTagName("adjDate")[0].firstChild.nodeValue;
		
	}
	 else if(flag=="nodata")
	{
		alert ("Record Does Not Exists");
	}
	else
	{
		alert("Fail To Load");
	}
	
	
}
function loadMomoNo(baseResponse)
{
	
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
//alert("after servlet"+flag);
	if (flag == "success") {
	    var len =baseResponse.getElementsByTagName("memono").length;
	 if(len>0)
	 {
		var se = document.getElementById("cmbAdviceNO");
		se.length=1;
	    for ( var i = 0; i < len; i++) {
	    	var memodate = baseResponse.getElementsByTagName("memodate")[i].firstChild.nodeValue;
	    //	alert("memodate===>"+memodate);
	    	var txtCrea_date=document.getElementById("txtDate").value;
	    	//alert("txtCrea_date===>"+txtCrea_date);
	    	 var str1_grid =memodate.split("/");
             var str2 = txtCrea_date.split("/");
             
             
             if(str1_grid[2]>str2[2])
             {
                        alert("Acceptance Date should not be less than Memo Advice Date**");
                        document.getElementById("txtDate").value="";
                        document.getElementById("txtDate").focus();
                        document.getElementById("txtAcc_HeadCode").value="";
                 		document.getElementById("txtRemarks1").value="";
                 	    document.getElementById("txtAmount").value="";
                 	    document.getElementById("txtsub_Amount").value="";
                 	    document.getElementById("cmbAdviceNO").selectedIndex="";
                 	    document.getElementById("txtDate").value="";
                 	    document.getElementById("txtLetterNO").value="";
                 	    document.getElementById("txtLetterDate").value="";
                 	    document.getElementById("txtAuthority").value="";
                 	    document.getElementById("txtParticular").value="";
                 	    document.getElementById("cmbSL_Code").value="";
                 	  // document.Adjustment_Memo_Form1.txtDate.disabled=false;
                 	   document.Adjustment_Memo_Form1.txtDate.readOnly=true;
                 	   
                 	 //  alert(txtCrea_date);
                 	  location.reload();
                         return false;
             }
             else if(str1_grid[2]==str2[2])
             {
               
                    if(str1_grid[1]>str2[1])
                    {
                    	alert("Acceptance Date should not be less than Memo Advice Date**");
                         document.getElementById("txtDate").value="";
                         document.getElementById("txtDate").focus();
                         document.getElementById("txtAcc_HeadCode").value="";
                 		document.getElementById("txtRemarks1").value="";
                 	    document.getElementById("txtAmount").value="";
                 	    document.getElementById("txtsub_Amount").value="";
                 	    document.getElementById("cmbAdviceNO").selectedIndex="";
                 	    document.getElementById("txtDate").value="";
                 	    document.getElementById("txtLetterNO").value="";
                 	    document.getElementById("txtLetterDate").value="";
                 	    document.getElementById("txtAuthority").value="";
                 	    document.getElementById("txtParticular").value="";
                 	    document.getElementById("cmbSL_Code").value="";
                 	   //document.Adjustment_Memo_Form1.txtDate.disabled=false;
                 	   document.Adjustment_Memo_Form1.txtDate.readOnly=true;
                 	  location.reload();
                         return false;
                    }
                    else if(str1_grid[1]==str2[1])
                    {
                    	//alert("month:::");
                    //	alert("day:::grid::::"+str1_grid[0]);
                    //	alert("day:::voucher::::"+str2[0]);
                        if(str1_grid[0]>str2[0])
                        {
                        	alert("Acceptance Date should not be less than Memo Advice Date**");
                         document.getElementById("txtDate").value="";
                         document.getElementById("txtDate").focus();
                         document.getElementById("txtAcc_HeadCode").value="";
                 		document.getElementById("txtRemarks1").value="";
                 	    document.getElementById("txtAmount").value="";
                 	    document.getElementById("txtsub_Amount").value="";
                 	    document.getElementById("cmbAdviceNO").selectedIndex="";
                 	    document.getElementById("txtDate").value="";
                 	    document.getElementById("txtLetterNO").value="";
                 	    document.getElementById("txtLetterDate").value="";
                 	    document.getElementById("txtAuthority").value="";
                 	    document.getElementById("txtParticular").value="";
                 	    document.getElementById("cmbSL_Code").value="";
//                 	    document.Adjustment_Memo_Form1.txtDate.disabled=false;
                 	    
                 	   document.Adjustment_Memo_Form1.txtDate.readOnly=true;
                 	    location.reload();
                         return false;
                        }
                    
                    }
                    else
                    	{
                    	//alert("Else part.......");
                    	document.Adjustment_Memo_Form1.txtDate.readOnly=true;
                    	}
              
             }    	
            else
         	{
         //alert("Else part coming!.....");
         	
         	//document.Adjustment_Memo_Form1.txtDate.disabled=true;
            	document.Adjustment_Memo_Form1.txtDate.readOnly=true;
            //	alert(document.Adjustment_Memo_Form1.txtDate.value);
         	}
	    	
	    	var com1id = baseResponse.getElementsByTagName("memono")[i].firstChild.nodeValue;
			var sl_no = baseResponse.getElementsByTagName("sl_no")[i].firstChild.nodeValue;
			var op = document.createElement("OPTION");
			op.value = com1id+"-"+sl_no;
			var txt = document.createTextNode(com1id+"-"+sl_no);
//			op.value = com1id;
//			var txt = document.createTextNode(com1id);
			op.appendChild(txt);
			se.appendChild(op);
		}
	}
	 else
	 {
		 alert("NO AdviceNo For This Month");
	 }
	}
	else if(flag=="Nodata")
	{
		 alert("NO AdviceNo For This Month");
		var se = document.getElementById("cmbAdviceNO");
		se.length=1;
	}
	
	else {
		alert("Fail to Load");
	}
	
	
}
