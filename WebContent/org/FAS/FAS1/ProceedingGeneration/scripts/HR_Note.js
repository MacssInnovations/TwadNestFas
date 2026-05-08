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
function numbersonly1(e, t) {
	//alert("t.length "+t.value.length);
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode == 13) {
		try {           
			t.blur();
		} catch (e) {
		}
		return true;

	}
	if (unicode != 8 && unicode != 9) {
		if (unicode < 48 || unicode > 57)
			return false;
	}   
}
function jobpopup1(s1,s2) {
	//alert('testsing');
	txtid1=s1;
	txtid2=s2;
	if (winjob && winjob.open && !winjob.closed) {
		winjob.resizeTo(500, 500);
		winjob.moveTo(250, 250);
		winjob.focus();
		return;
	} else {
		winjob = null;
	}

	winjob = window.open(
			"../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp",
			"JobSearch",
	"status=1,height=1000,width=1000,resizable=YES, scrollbars=yes");
	winjob.moveTo(250, 250);
	winjob.focus();	
	document.HR_Note.officeId.focus();
}
function doParentJob(jobid, deptid) {
	//document.leave_unavail.dept_id.value = deptid;	
	document.getElementById('officeId').value=jobid;
	//loadOffice(jobid);
	return true;
}
function callServer1(command, param) {
	if (command == "Check") {
		var txtOffice_Id = document.HR_Note.officeId.value;
		var flag = document.HR_Note.officeId.value;
		if (flag.length > 0) {
			url="../../../../../phone_master_servlet?command=check&txtOffice_Id=" + txtOffice_Id;
			var req = getTransport();
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				updateResponse_off(req);
			};
			req.send(null);
		}
	}
}
function updateResponse_off(req) {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var response = req.responseXML.getElementsByTagName("response")[0];
	var res = response.getElementsByTagName("status")[0].firstChild.nodeValue;
	if (res == "success"){
		if (response.getElementsByTagName("command")[0].firstChild.nodeValue == "existing") {
			document.HR_Note.txtemp_office.value=response.getElementsByTagName("officename")[0].firstChild.nodeValue;
		}else if (response.getElementsByTagName("command")[0].firstChild.nodeValue == "Notexisting") {
			alert("The given office Id do not Exist");
			document.HR_Note.officeId.value="";
			document.HR_Note.officeId.focus();
		}else if (response.getElementsByTagName("command")[0].firstChild.nodeValue == "Notexisting_1") {
			alert("The given office Id do not Exist");
			document.HR_Note.officeId.value="";
			document.HR_Note.officeId.focus();
		}
	} else {

		alert("Process failure");
	}

}
	}
}
function  callminor()
{
        var major1=document.forms[0].majorType.value;
        //alert("major1"+major1);
        var url="../../../../../HR_Note?Command=minorType&major2="+major1;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
           handleResponse1(req);
        };   
                req.send(null);   
}
function load_AccHead()
{
	var major1=document.forms[0].majorType.value;
	 var minorType=document.forms[0].minorType.value;
	 var billsubtype=document.forms[0].billsubtype.value;
	 var fin_yr=document.forms[0].fin_yr.value;
	 
    var url="../../../../../HR_Note?Command=Load_headCode&majorType="+major1+"&fin_yr="+fin_yr+"&minorType="+minorType+"&billsubtype="+billsubtype;
  //alert(url);
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
       handleResponse1(req);
    }  ; 
            req.send(null);     
}
function  callsub(param)
{
	    var major1=document.forms[0].majorType.value;
        var url="../../../../../HR_Note?Command=subType&sub2="+param+"&major2="+major1;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse1(req);
        }   
                req.send(null);     
}
function load_head()
{
	 var majorType=document.forms[0].majorType.value;
	 if(majorType=="0")
	 {
		 alert("Choose Bill Major Type");
		 return false;
	 }
	 var minorType=document.forms[0].minorType.value;
	 if(minorType=="0")
	 {
		 alert("Choose Bill Minor Type");
		 return false;
	 }
	 var billsubtype=document.forms[0].billsubtype.value;
	 if(billsubtype=="0")
	 {
		 alert("Choose Bill Sub Type");
		 return false;
	 }
	 else if(billsubtype=="")
	 {
		 billsubtype=0; 
	 }
	 //alert(":::"+majorType+"********"+minorType+"&&&&&&&&&"+billsubtype);
	 var txtAcc_HeadCode=document.forms[0].txtAcc_HeadCode.value;
     var url="../../../../../HR_Note?Command=loadAcc&majorType="+majorType+"&minorType="+minorType+"&billsubtype="+billsubtype+"&txtAcc_HeadCode="+txtAcc_HeadCode;
     var req=getTransport();
     req.open("GET",url,true); 
     req.onreadystatechange=function()
     {
        handleResponse1(req);
     }   
             req.send(null);     
}
function load_headcp(majorType,minorType,billsubtype,head)
{
	
     var url="../../../../../HR_Note?Command=loadAcc&majorType="+majorType+"&minorType="+minorType+"&billsubtype="+billsubtype+"&txtAcc_HeadCode="+head;
   
     var req=getTransport();
     req.open("GET",url,true); 
     req.onreadystatechange=function()
     {
    	 if(req.readyState==4)
     
     { 
         if(req.status==200)
         {             
             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             var tagcommand=baseResponse.getElementsByTagName("command")[0];
             var Command=tagcommand.firstChild.nodeValue;
           
    	  if(Command=="loadAcc")
         {
         	loadAccchecking(baseResponse);
         }
         }
     }
     } ;  
             req.send(null);     
}

function loadHoNote(val){
	  var url="../../../../../HR_Note?Command=loadNoteNo&cmbAcc_UnitCode="+document.forms[0].cmbAcc_UnitCode.value+"&cmbOffice_code="+document.forms[0].cmbOffice_code.value+"&fin_yr="+val;
	  //alert('url'+url);   
	  var req=getTransport();
	     req.open("GET",url,true); 
	     req.onreadystatechange=function()
	     {
	        handleResponse1(req);
	     };   
	             req.send(null);     
	}
function clr()
{
	document.getElementById("hr_date").value="";
	document.getElementById("hr_amt").value="";
	document.getElementById("txtAcc_HeadCode").value="";
	document.getElementById("txtRemarks").value="";
	document.getElementById("majorType").value=0;
	document.getElementById("minorType").value="";
	document.getElementById("billsubtype").length=0;
	document.getElementById("txtAcc_HeadDesc").value="";
	document.getElementById("hid_div").style.display="none";
}

function calling(Command)         
{ 
   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
   var cmbOffice_code=document.getElementById("cmbOffice_code").value;
   var finyr=document.getElementById("fin_yr").value;
   var majorType=document.getElementById("majorType").value;
   var minorType=document.getElementById("minorType").value;
   var billsubtype=document.getElementById("billsubtype").value;
   if(billsubtype=="" || billsubtype == 0)
	   {
		   billsubtype =0;
	   }
   //alert(billsubtype);
   var hrdate=document.getElementById("hr_date").value;
   var hramt=document.getElementById("hr_amt").value;
   var txtAccHeadCode=document.getElementById("txtAcc_HeadCode").value;
   var txtRemarks=document.getElementById("txtRemarks").value;
   var officeId=document.getElementById("officeId").value;
   var headcode;
	 if(txtAccHeadCode==""){headcode=0;}
	 else{headcode=txtAccHeadCode;}
	 //alert(finyr);
	 var url;
	 if(Command=="Add"){
	  url="../../../../../HR_Note?Command=Add&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&finyr="+finyr+"&majorType="+majorType+
              "&minorType="+minorType+"&billsubtype="+billsubtype+"&hrdate="+hrdate+"&hramt="+hramt+"&headcode="+headcode+"&txtRemarks="+txtRemarks+"&officeId="+officeId;
	 }else if(Command=="Update"){
		 var note_No=document.getElementById("note_no").value;
		  url="../../../../../HR_Note?Command=Update&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&finyr="+finyr+"&majorType="+majorType+
         "&minorType="+minorType+"&billsubtype="+billsubtype+"&hrdate="+hrdate+"&hramt="+hramt+"&headcode="+headcode+"&txtRemarks="+txtRemarks+"&note_no="+note_No+"&officeId="+officeId;
	 }
	 alert(url);
	 var req=getTransport();
     req.open("GET",url,true); 
     req.onreadystatechange=function()
     {
        handleResponse1(req);
     };   
             req.send(null);    

}

function filter_real(evt,item,n,pre)
{
        var charCode = (evt.which) ? evt.which : evt.keyCode;
// allow "." for one time 
        if(charCode==46){
                        //	alert("Position of . "+item.value.indexOf("."));
                        if(item.value.indexOf(".")<0)	return (item.value.length>0)?true:false;
                        else return false;
        }
        if (!(charCode > 31 && (charCode < 48 || charCode > 57))){
                // to avoid over flow
                        if(item.value.indexOf(".")<0){
        //			alert("Length without . ="+item.value.length);
                                return (item.value.length<n)?true:false;
                        }
                // dont allow more than 2 precision no's after the point
                        if(item.value.indexOf(".")>0){
                        //	alert("precision count ="+item.value.split(".")[1].length);
                                if(item.value.split(".")[1].length<pre) return true;
                                else return false;
                        }
                        return false;
        }else{
                        return false;
        }
}

function sixdigit()
{
	    if( document.getElementById("txtAcc_HeadCode").value!=0)
	    {
	        if((document.getElementById("txtAcc_HeadCode").value).length<6)
	        {
		        alert("Account Head Code Shouldn't be less than 6 digit number");
		        document.getElementById("txtAcc_HeadCode").focus();
		        return false;
	        }
	    }
}

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
                return false;
            }
        }
} 

function handleResponse1(req)
{  
    if(req.readyState==4)
    { 
        if(req.status==200)
        {             
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="minor")
            {
                minortypechecking(baseResponse);
            }
            else if(Command=="subb")
            {
                subtypechecking(baseResponse);
            }
            else if(Command=="Update")
            {

           	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            
	             if(flag=="success")
	             {
	            	 	alert("Record Updated into database");                 //return true;
	            	 	clr();
	             }
	             else if(flag=="failure")
	             {
	            	 alert("Record not Updated into database");    
	             }
           
            } else if(Command=="loadNoteNo")
            {
            	loadHoNoteNo(baseResponse);
            }   else if(Command=="loadAcc")
            {
            	loadAccchecking(baseResponse);
            }
            else if(Command=="getDetails")
            {
            	laodgetDetails(baseResponse);
            } 
            else if(Command=="Load_headCode")
            {
            	laodAccHEadCode(baseResponse);
            }
            else if(Command=="Add")
            {
            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            
	             if(flag=="success")
	             {
	            	 	alert("Record inserted into database");                 //return true;
	            	 	clr();
	             }
	             else if(flag=="failure")
	             {
	            	 alert("Record not inserted into database");    
	             }
            }
        }
    }
}
function laodgetDetailscp(baseResponse){
	//alert('test');
	   var BILL_MAJOR_TYPE_CODE = baseResponse.getElementsByTagName("BILL_MAJOR_TYPE_CODE")[0].firstChild.nodeValue;
 	   var BILL_MINOR_TYPE_CODE = baseResponse.getElementsByTagName("BILL_MINOR_TYPE_CODE")[0].firstChild.nodeValue;
 	   var BILL_SUB_TYPE_CODE = baseResponse.getElementsByTagName("BILL_SUB_TYPE_CODE")[0].firstChild.nodeValue;
 	   var NOTE_DATE = baseResponse.getElementsByTagName("NOTE_DATE")[0].firstChild.nodeValue;
 	   var NOTE_AMOUNT = baseResponse.getElementsByTagName("NOTE_AMOUNT")[0].firstChild.nodeValue;
 	   var NOTE_PREPARED_BY = baseResponse.getElementsByTagName("NOTE_PREPARED_BY")[0].firstChild.nodeValue;
 	   var ACCOUNT_HEAD_CODE = baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[0].firstChild.nodeValue;
 	  var financial_year = baseResponse.getElementsByTagName("financial_year")[0].firstChild.nodeValue;
 	 var SANCTION_PROC_OFFICE_ID = baseResponse.getElementsByTagName("SANCTION_PROC_OFFICE_ID")[0].firstChild.nodeValue;
	   var OFFICE_NAME = baseResponse.getElementsByTagName("OFFICE_NAME")[0].firstChild.nodeValue;
	   
 	   // alert(ACCOUNT_HEAD_CODE);
 	   document.getElementById("majorType").value=BILL_MAJOR_TYPE_CODE;
 	  callminorCp(BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE);
 	// callsub()
 	   document.getElementById("hr_date").value=NOTE_DATE;
 	   document.getElementById("hr_amt").value=NOTE_AMOUNT;
 	  document.getElementById("officeId").value=SANCTION_PROC_OFFICE_ID;
	  document.getElementById("txtemp_office").value=OFFICE_NAME;
 	//  document.getElementById("hr_amt").value=NOTE_AMOUNT;
 	  document.getElementById("txtRemarks").value=NOTE_PREPARED_BY;
 	 load_AccHeadcp(BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,financial_year,ACCOUNT_HEAD_CODE);
 	 load_headcp(BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,ACCOUNT_HEAD_CODE);
 	 //  document.getElementById("txtAcc_HeadCode").value=ACCOUNT_HEAD_CODE;
	 
	 // load_headcp(BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,ACCOUNT_HEAD_CODE);
	
}
function load_AccHeadcp(major1,minorType,billsubtype,fin_yr,ACCOUNT_HEAD_CODE)
{
	/*var major1=document.forms[0].majorType.value;
	 var minorType=document.forms[0].minorType.value;
	 var billsubtype=document.forms[0].billsubtype.value;
	 var fin_yr=document.forms[0].fin_yr.value;*/
	 
   var url="../../../../../HR_Note?Command=Load_headCode&majorType="+major1+"&fin_yr="+fin_yr+"&minorType="+minorType+"&billsubtype="+billsubtype;
 //alert(url);
   var req=getTransport();
   req.open("GET",url,true); 
   req.onreadystatechange=function()
   {
	   if(req.readyState==4)
	    { 
	        if(req.status==200)
	        {             
	            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	            var tagcommand=baseResponse.getElementsByTagName("command")[0];
	            var Command=tagcommand.firstChild.nodeValue;
	             if(Command=="Load_headCode")
	            {
	            	var res=laodAccHEadCode(baseResponse);
	            	if(res==true){
	            		document.getElementById("txtAcc_HeadCode").value=ACCOUNT_HEAD_CODE;
	            	}
	            }
	        }
	            }
   }  ; 
           req.send(null);     
}
function laodgetDetails(baseResponse){
	//alert('test');
	   var BILL_MAJOR_TYPE_CODE = baseResponse.getElementsByTagName("BILL_MAJOR_TYPE_CODE")[0].firstChild.nodeValue;
 	   var BILL_MINOR_TYPE_CODE = baseResponse.getElementsByTagName("BILL_MINOR_TYPE_CODE")[0].firstChild.nodeValue;
 	   var BILL_SUB_TYPE_CODE = baseResponse.getElementsByTagName("BILL_SUB_TYPE_CODE")[0].firstChild.nodeValue;
 	   var NOTE_DATE = baseResponse.getElementsByTagName("NOTE_DATE")[0].firstChild.nodeValue;
 	   var NOTE_AMOUNT = baseResponse.getElementsByTagName("NOTE_AMOUNT")[0].firstChild.nodeValue;
 	   var NOTE_PREPARED_BY = baseResponse.getElementsByTagName("NOTE_PREPARED_BY")[0].firstChild.nodeValue;
 	   var ACCOUNT_HEAD_CODE = baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[0].firstChild.nodeValue;
 	   var SANCTION_PROC_OFFICE_ID = baseResponse.getElementsByTagName("SANCTION_PROC_OFFICE_ID")[0].firstChild.nodeValue;
 	   var OFFICE_NAME = baseResponse.getElementsByTagName("OFFICE_NAME")[0].firstChild.nodeValue;
 	  
 	   // alert(ACCOUNT_HEAD_CODE);
 	   document.getElementById("majorType").value=BILL_MAJOR_TYPE_CODE;
 	  callminorCp(BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE);
 	// callsub()
 	   document.getElementById("hr_date").value=NOTE_DATE;
 	   document.getElementById("hr_amt").value=NOTE_AMOUNT;
 	//  document.getElementById("hr_amt").value=NOTE_AMOUNT;
 	  document.getElementById("txtAcc_HeadCode").value=ACCOUNT_HEAD_CODE;
	  document.getElementById("txtAcc_HeadCode").value=ACCOUNT_HEAD_CODE;
	  document.getElementById("txtRemarks").value=NOTE_PREPARED_BY;
	  document.getElementById("officeId").value=SANCTION_PROC_OFFICE_ID;
	  document.getElementById("txtemp_office").value=OFFICE_NAME;
	  
	  
	  load_headcp(BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,ACCOUNT_HEAD_CODE);
	
}
function  callminorCp(BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE)
{
        //var major1=document.forms[0].majorType.value;
        //alert("major1"+major1);
        var url="../../../../../HR_Note?Command=minorType&major2="+BILL_MAJOR_TYPE_CODE;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
         // var res= handleResponse1(req);
        //  alert(res);
         
        	  if(req.readyState==4)
        	    { 
        	        if(req.status==200)
        	        {             
        	            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
        	            var tagcommand=baseResponse.getElementsByTagName("command")[0];
        	            var Command=tagcommand.firstChild.nodeValue;
        	            if(Command=="minor")
        	            {
        	            	var res=minortypechecking(baseResponse);
        	            	
        	            	if(res==true){
        	              // minortypechecking(baseResponse);
        	 document.getElementById("minorType").value=BILL_MINOR_TYPE_CODE;
        	 callsubcp(BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE);
        	            	}
        	            }
        	        }
        	    }
          
          
        };   
                req.send(null);   
}

function  callsubcp(BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE)
{
    
    var url="../../../../../HR_Note?Command=subType&sub2="+BILL_MINOR_TYPE_CODE+"&major2="+BILL_MAJOR_TYPE_CODE;
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
    	  if(req.readyState==4)
  	    { 
  	        if(req.status==200)
  	        {             
  	            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
  	            var tagcommand=baseResponse.getElementsByTagName("command")[0];
  	            var Command=tagcommand.firstChild.nodeValue;
  	          
  	               if(Command=="subb")
  	            {
  	               var res= subtypechecking(baseResponse);
  	            // alert("jjjss"+res);
  	            if(res==true)
  	            {
  	            	 document.getElementById("billsubtype").value=BILL_SUB_TYPE_CODE;
  	            }
  	            }
  	        }
  	    }
    }  ; 
            req.send(null);     
}

function loadDetails(val){
	
	var cmbAcc_UnitCode=document.forms[0].cmbAcc_UnitCode.value;
	var finYr=document.forms[0].fin_yr.value;
	var cmbOffice_code=document.forms[0].cmbOffice_code.value;	
	// alert(cmbOffice_code);
	 var url="../../../../../HR_Note?Command=getDetails&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&finyr="+finYr+
	 "&NoteNo="+val;
	// alert(url);
var req=getTransport();
req.open("GET",url,true); 
req.onreadystatechange=function()
{
handleResponse1(req);
};   
    req.send(null);    
	
}





function loadDetailsEdit(val){
	
	var cmbAcc_UnitCode=document.forms[0].cmbAcc_UnitCode.value;
	var finYr=document.forms[0].fin_yr.value;
	var cmbOffice_code=document.forms[0].cmbOffice_code.value;	
	var Edit1=document.forms[0].Edit.value;	
	
	 var url="../../../../../HR_Note?Command=getDetails&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&finyr="+finYr+
	 "&NoteNo="+val;
	
var req=getTransport();
req.open("GET",url,true); 
req.onreadystatechange=function()
{
handleResponse1Cp(req,Edit1);
};   
    req.send(null);    
	
}
function handleResponse1Cp(req,Edit)
{  
    if(req.readyState==4)
    { 
        if(req.status==200)
        {             
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
             if(Command=="getDetails")
            {
            	laodgetDetailscp(baseResponse);
            }
        }
    }
}

function loadHoNoteNo(baseResponse){
	
		var hr_noteno = document.getElementById("note_no");
   		hr_noteno.length=0;
   	   var hr_note_no = baseResponse.getElementsByTagName("hr_note_no");  
	 
	   if(hr_note_no.length>0)
	   	 {
		 //  alert(">>"+hr_note_no.length);
	   	  var opt1 = document.createElement("option");

	   	  opt1.value = "";
          opt1.innerHTML ="--Select--"; 
          hr_noteno.appendChild(opt1);
	   	  for(var i=0;i<hr_note_no.length;i++)
          {
	   		  var hr_noteno = document.getElementById("note_no");
	   	   var hOnote_no = baseResponse.getElementsByTagName("hr_note_no")[i].firstChild.nodeValue;
	   		  var opt1 = document.createElement("option");
              opt1.value = hOnote_no;
              opt1.innerHTML =hOnote_no; 
              hr_noteno.appendChild(opt1);
          }
	   	 }

}
function minortypechecking(baseResponse)
{

		 var minorcmb = document.forms[0].minorType;
         document.forms[0].minorType.length=0;
         var minorcode = baseResponse.getElementsByTagName("minorcode");  
         var minordesc = baseResponse.getElementsByTagName("minordesc"); 
	   	 if(minorcode.length>0)
	   	 {
	   		var minorType = document.getElementById("minorType");
	   		minorType.length=0;
            for(var i=0;i<minorcode.length;i++)
               {
            	  if(minorcode.length==1)
            	  {
            		  var opt1 = document.createElement('option');
                      opt1.value = 0;
                      opt1.innerHTML ="select"; 
                      minorcmb.appendChild(opt1);
            		  
            	  }
            		  
            	     var opt1 = document.createElement('option');
                     opt1.value = minorcode[i].firstChild.nodeValue;
                     opt1.innerHTML = minordesc[i].firstChild.nodeValue; 
                     minorcmb.appendChild(opt1);
                 
               }  
         
	   	 }
	   return true;	 
}

function check()
{
	if(document.getElementById("hr_date").value=="")
	{
		alert("Enter Date");
		return false;
	}
}

function nullcheck()
{
	if(document.getElementById("hr_amt").value=="")	
	{
		alert("Enter Amount");
		return false;
	}
	if(document.getElementById("txtAcc_HeadCode").value=="")	
	{
		alert("Enter Account Head code");
		return false;
	}
	return true;
}
function BudgetAllot()
{
	
	var unitid=document.forms[0].cmbAcc_UnitCode.value;
	var finYr=document.forms[0].fin_yr.value;
	var head_Code=document.forms[0].txtAcc_HeadCode.value;
	var cmbOffice_code=document.forms[0].cmbOffice_code.value;
	var hr_amt=document.forms[0].hr_amt.value;
  my_window= window.open("HoNote_Budget.jsp?Command=ChkBudget&cmbAcc_UnitCode="+unitid+"&finYr="+finYr+
    "&headcode="+head_Code+"&cmbOffice_code="+cmbOffice_code,"mywindow1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
  my_window.moveTo(250,250);  
  document.getElementById("hid_div").style.display="none";
  
}
function subtypechecking(baseResponse)
{

          var subcmb = document.forms[0].billsubtype;
          document.forms[0].billsubtype.length=0;
          var subcode = baseResponse.getElementsByTagName("subcode"); 
          var subdesc = baseResponse.getElementsByTagName("subdesc"); 
          for(var i=0; i<subcode.length; i++)
               {
	        	  if(subcode.length==1)
	        	  {
	        		  var opt1 = document.createElement('option');
	                  opt1.value = 0;
	                  opt1.innerHTML ="select"; 
	                  subcmb.appendChild(opt1);
	        		  
	        	  }
        	         var opt1 = document.createElement('option');
                     opt1.value = subcode[i].firstChild.nodeValue;
                     opt1.innerHTML = subdesc[i].firstChild.nodeValue; 
                     subcmb.appendChild(opt1);
               }  
          return true;
}
function chkBud(seq){
//	alert("test ** ");
	var unitid=document.forms[0].cmbAcc_UnitCode.value;
	var finYr=document.forms[0].fin_yr.value;
	var head_Code=document.forms[0].txtAcc_HeadCode.value;
	var cmbOffice_code=document.forms[0].cmbOffice_code.value;
	var hr_amt=document.forms[0].hr_amt.value;
	 var url="../../../../../HR_Note?Command=ChkBudget&cmbAcc_UnitCode="+unitid+"&finYr="+finYr+
    "&headcode="+head_Code+"&cmbOffice_code="+cmbOffice_code;
	// alert(url);
var req=getTransport();
req.open("GET",url,true); 
req.onreadystatechange=function()
{
handleResponse_Bud(req,seq,hr_amt);
}   ;
    req.send(null); 
	
}

function handleResponse_Bud(req,s,hr_amt)
{
	   if(req.readyState==4)
	    {
		        if(req.status==200)
		        {  
			             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
			             var tagcommand=baseResponse.getElementsByTagName("Command")[0];
			             var Command=tagcommand.firstChild.nodeValue;
			             var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			             if(flag=="success"){
			            	 var REF_NO=baseResponse.getElementsByTagName("REF_NO")[0].firstChild.nodeValue;
			            	 var REF_DATE=baseResponse.getElementsByTagName("REF_DATE")[0].firstChild.nodeValue;
			            	 var CURRENT_YEAR_BUDGET_ALLOTTED=baseResponse.getElementsByTagName("CURRENT_YEAR_BUDGET_ALLOTTED")[0].firstChild.nodeValue;
			            	 var BUDGET_SOFAR_SPENT=baseResponse.getElementsByTagName("BUDGET_SOFAR_SPENT")[0].firstChild.nodeValue;
			            //	 alert(s);
			            	 if(s==1)
			            	 {
			            		// alert(hr_amt);
			            		// alert(CURRENT_YEAR_BUDGET_ALLOTTED);
			            		 if(parseFloat(hr_amt)>parseFloat(CURRENT_YEAR_BUDGET_ALLOTTED))
			            		 {
			            			 
			            			 document.getElementById("hid_div").style.display="block";
			            			
			            			 alert("Invalid Amount check Budget using Click Here ....");
			            			document.forms[0].hr_amt.value="";
			            		 }else{
			            			 document.getElementById("hid_div").style.display="none";
			            		 }
			            	 }
			             }
			             else{
			            	 alert("No Data Found ... ");
			             }
		        }
	    }
			            	
}



function laodAccHEadCode(baseResponse){
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	   if(flag=="success")
	   {
		   
		   var len=baseResponse.getElementsByTagName("headcode");
		   
		   document.getElementById("txtAcc_HeadCode").length=""; 
		   var sel=document.getElementById("txtAcc_HeadCode");
		   var option=document.createElement("option");
		    option.value="";
		    	option.text="--Select--";
		    	//document.forms[0].txtAcc_HeadCode.value=headcode;
		    //document.forms[0].txtAcc_HeadDesc.value=headdesc; 
		    	sel.appendChild(option);
		   for(i=0;i<len.length;i++){
			   
		   var headcode = baseResponse.getElementsByTagName("headcode")[i].firstChild.nodeValue;
		    var headdesc = baseResponse.getElementsByTagName("headdesc")[i].firstChild.nodeValue;
		    var option=document.createElement("option");
		    option.value=headcode;
		    	option.text=headcode;
		    	//document.forms[0].txtAcc_HeadCode.value=headcode;
		    //document.forms[0].txtAcc_HeadDesc.value=headdesc; 
		    	sel.appendChild(option);
		   }
	   }
	   else
		   {
		   alert("Account headcode failure");
		   }
	   return true;
}
function loadAccchecking(baseResponse)
{

var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

   if(flag=="success")
   {
	    var headcode = baseResponse.getElementsByTagName("headcode")[0].firstChild.nodeValue;
	    var headdesc = baseResponse.getElementsByTagName("headdesc")[0].firstChild.nodeValue;
	    document.forms[0].txtAcc_HeadCode.value=headcode;
	    document.forms[0].txtAcc_HeadDesc.value=headdesc;
	    
    }
   else
   {
	   alert("A/c HeadCode Doesn't Exists");
	   document.forms[0].txtAcc_HeadCode.value="";
	    document.forms[0].txtAcc_HeadDesc.value="";
   }
return true;
}

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
	    if(blr_flag==1)                 // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
	    {
	            call_clr();
	            cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	            cmbOffice_code=document.getElementById("cmbOffice_code").value;
	            var TB_date=fromcal_dateCtrl.value;            
	            if(fromcal_dateCtrl.value.length!=0)
	            {
		                 var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
		                 var req=getTransport();
		                 req.open("GET",url,true); 
		                 req.onreadystatechange=function()
		                 {
		                   check_TB(req,fromcal_dateCtrl);
		                 }   
		                 req.send(null);
	            }
	    }
}

function call_date(dateCtrl)                        // TB_checking 
{
	    call_clr();
	    if(checkdt(dateCtrl))
	    {       
		         cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		         cmbOffice_code=document.getElementById("cmbOffice_code").value;
		         var TB_date=dateCtrl.value;
		       
		         if(dateCtrl.value.length!=0)
		         {
			             var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
			             var req=getTransport();
			             req.open("GET",url,true); 
			             req.onreadystatechange=function()
			             {
			               check_TB(req,dateCtrl);
			             }   
			             req.send(null);
		         }
	        
	    }
	    else
	    {
		         var cmbSL_Code=document.getElementById("txtReceipt_No");   
		         cmbSL_Code.innerHTML="";
		         var option=document.createElement("OPTION");
		         option.text="-- Select Receipt Number --";
		         option.value="";
		         try
		         {
		                 cmbSL_Code.add(option);
		         }catch(errorObject)
		         {
		        	 	 cmbSL_Code.add(option,null);
		         }
	    }
}



function check_TB(req,dateCtrl)
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
			            	 	doFunction('load_Receipt_No','null');                 //return true;
			             }
			             else if(flag=="failure")
			             {
			                    dateCtrl.value="";
			                    alert("Trial Balance Closed");//return false;//
			                    dateCtrl.focus();
			                    var cmbSL_Code=document.getElementById("txtReceipt_No");   
			                    cmbSL_Code.innerHTML="";
			                    var option=document.createElement("OPTION");
			                    option.text="-- Select Receipt Number --";
			                    option.value="";
			                    try
			                    {
			                        cmbSL_Code.add(option);
			                    }catch(errorObject)
			                    {
			                        cmbSL_Code.add(option,null);
			                    }
			               }
		        }
	    }
}