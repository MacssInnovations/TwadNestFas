//alert('test');

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

function callmajorType()
{
        var url="../../../../../Sanc_Proc_Single?Command=majorType";
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse(req);
        }   
                req.send(null);
}

function  callminor()
{
        var major1=document.forms[0].billmajortype.value;
        var url="../../../../../Sanc_Proc_Single?Command=minorType&major2="+major1;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse(req);
        } ;  
                req.send(null);   
}

function  callsub(param)
{
		var major1=document.forms[0].billmajortype.value;
        var url="../../../../../Sanc_Proc_Single?Command=subType&sub2="+param+"&major2="+major1;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse(req);
        };   
                req.send(null);     
}

function handleResponse(req)
{  
    if(req.readyState==4)
    { 
        if(req.status==200)
        { 
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="checkCode")
            {
                loadcheckCode(baseResponse);
            }
            else if(Command=="major")
            {
                 majortypechecking(baseResponse);
            }
            else if(Command=="minor")
            {
                minortypechecking(baseResponse);
            }
            else if(Command=="subb")
            {
                subtypechecking(baseResponse);
            }
            else if(Command=="txtgpf")
            {
                designation(baseResponse);
            }
            else if(Command=="desig")
            {
            	designation(baseResponse);
            }
            else if(Command=="Descrip")
            {
            	description(baseResponse);
            }
            else if(Command=="Add")
            {
                addRow(baseResponse);
            }
            else if(Command=="Delete")
            {
                deleteRow(baseResponse);
            }            
            else if(Command=="Updated")
            {                
                UpdateRow(baseResponse);
            }
        }
    }
}

function UpdateRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
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

function deleteRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;    
    if(flag=="success")
    {
        alert("Record deleted from database");
         ClearAll();
    }
    else
    {
        alert("Record not deleted from database");
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
          /* 
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
	             }*/
           
           if(Command=="loadNoteNo")
            {
            	loadHoNoteNo(baseResponse);
            } else if(Command=="getSan_No"){
            	
            	loadSan_No(baseResponse);
            }
           else if(Command=="getDetailssing")
            {
            	laodgetDetails(baseResponse);
            }else if(Command=="minor")
            {
                minortypechecking(baseResponse);
            }
            else if(Command=="subb")
            {
                subtypechecking(baseResponse);
            }
            else if(Command=="code")
           {
               loadpayName(baseResponse);
           }  else if(Command=="SanNo_det")
           {
        	 
               loadSanNo_det(baseResponse);
           }
           
           
           /* else if(Command=="loadAcc")
            {
            	loadAccchecking(baseResponse);
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
	             }*/
            
        }
    }
}

function loadSanNo_det(baseResponse)
{
	
	  
	  var SANCTION_PROCEEDING_NO = baseResponse.getElementsByTagName("SANCTION_PROCEEDING_NO")[0].firstChild.nodeValue;
	
	  var SANCTION_PROCEEDING_DATE = baseResponse.getElementsByTagName("SANCTION_PROCEEDING_DATE")[0].firstChild.nodeValue;
	   var BILL_MAJOR_TYPE_CODE = baseResponse.getElementsByTagName("BILL_MAJOR_TYPE_CODE")[0].firstChild.nodeValue;
	   var BILL_MINOR_TYPE_CODE = baseResponse.getElementsByTagName("BILL_MINOR_TYPE_CODE")[0].firstChild.nodeValue;
	   var BILL_SUB_TYPE_CODE = baseResponse.getElementsByTagName("BILL_SUB_TYPE_CODE")[0].firstChild.nodeValue;
	 
	   var PAYEE_TYPE = baseResponse.getElementsByTagName("PAYEE_TYPE")[0].firstChild.nodeValue;
	   var PAYEE_CODE = baseResponse.getElementsByTagName("PAYEE_CODE")[0].firstChild.nodeValue;
	   var PAYEE_NAME = baseResponse.getElementsByTagName("PAYEE_NAME")[0].firstChild.nodeValue;
	   var NO_OF_HR = baseResponse.getElementsByTagName("NO_OF_HR")[0].firstChild.nodeValue;
	   var HR_FROM_DATE = baseResponse.getElementsByTagName("HR_FROM_DATE")[0].firstChild.nodeValue;
	   var HR_TO_DATE = baseResponse.getElementsByTagName("HR_TO_DATE")[0].firstChild.nodeValue;
	   var HR_AMOUNT = baseResponse.getElementsByTagName("HR_AMOUNT")[0].firstChild.nodeValue;
	   var VOU_ATTACHED = baseResponse.getElementsByTagName("VOU_ATTACHED")[0].firstChild.nodeValue;
	   var NO_OF_VOU = baseResponse.getElementsByTagName("NO_OF_VOU")[0].firstChild.nodeValue;
	   var REF_NO = baseResponse.getElementsByTagName("REF_NO")[0].firstChild.nodeValue;
	   var REF_DATE = baseResponse.getElementsByTagName("REF_DATE")[0].firstChild.nodeValue;
	   var SANCTION_AUTHORITY = baseResponse.getElementsByTagName("SANCTION_AUTHORITY")[0].firstChild.nodeValue;
	   var SANCTIONED_BY = baseResponse.getElementsByTagName("SANCTIONED_BY")[0].firstChild.nodeValue;
	   var TOTAL_SANCTION_AMOUNT = baseResponse.getElementsByTagName("TOTAL_SANCTION_AMOUNT")[0].firstChild.nodeValue;
	   var ACCOUNT_HEAD_CODE = baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[0].firstChild.nodeValue;
	   var BUD_PROVIDED = baseResponse.getElementsByTagName("BUD_PROVIDED")[0].firstChild.nodeValue;
	   var BUD_SPENT = baseResponse.getElementsByTagName("BUD_SPENT")[0].firstChild.nodeValue;
	   var BAL_AMT = baseResponse.getElementsByTagName("BAL_AMT")[0].firstChild.nodeValue;
	   var PAYMENT_TO_BE_MADE_UNIT_ID = baseResponse.getElementsByTagName("PAYMENT_TO_BE_MADE_UNIT_ID")[0].firstChild.nodeValue;
	   var REMARKS = baseResponse.getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
	   var HR_NOTE_NO = baseResponse.getElementsByTagName("HR_NOTE_NO")[0].firstChild.nodeValue;
	   var HR_NOTE_DATE = baseResponse.getElementsByTagName("HR_NOTE_DATE")[0].firstChild.nodeValue;
	   var desc=baseResponse.getElementsByTagName("desc")[0].firstChild.nodeValue;
       var desig=baseResponse.getElementsByTagName("designation")[0].firstChild.nodeValue;
       document.getElementById("sanc_date").value=SANCTION_PROCEEDING_DATE;
	  document.getElementById("note_no").value=HR_NOTE_NO;
	  document.getElementById("ho_date").value=HR_NOTE_DATE;
	  
	  document.getElementById("billmajortype").value=BILL_MAJOR_TYPE_CODE;
	  callminorCp(BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE);
	  if(PAYEE_TYPE=="P")
	     document.SancProcSingle_Edit.radActive[2].checked=true;
	  else if(PAYEE_TYPE=="U")
		  document.SancProcSingle_Edit.radActive[1].checked=true;
	  else if(PAYEE_TYPE=="E")
		  document.SancProcSingle_Edit.radActive[0].checked=true;
	  
	  document.SancProcSingle_Edit.txtEmpID_trs.value=PAYEE_CODE;
	 alert('pya  here ... ');
	  document.SancProcSingle_Edit.pay_name.value=desc+"   "+desig;
	  document.SancProcSingle_Edit.hr.value=NO_OF_HR;
	  document.SancProcSingle_Edit.frm_date.value=HR_FROM_DATE;
	  document.SancProcSingle_Edit.to_date.value=HR_TO_DATE;
	  document.SancProcSingle_Edit.hr_amt.value=HR_AMOUNT;
	  if(VOU_ATTACHED=='Y'){
	  document.SancProcSingle_Edit.radActive1[1].checked=true;
	  document.SancProcSingle_Edit.sub_vou.value=NO_OF_VOU;
	  } else if(VOU_ATTACHED=='N'){
		  document.SancProcSingle_Edit.radActive1[0].checked=true;
		  document.getElementById("trDtTo1").style.display="none";
		  document.getElementById("trDtTo2").style.display="none";
	 
	  
	  }
	
	  document.SancProcSingle_Edit.ref_no.value=REF_NO;
	  document.SancProcSingle_Edit.ref_date.value=REF_DATE;
	  document.SancProcSingle_Edit.sanc.value=SANCTION_AUTHORITY;
	  document.SancProcSingle_Edit.sanc_amt.value=TOTAL_SANCTION_AMOUNT;
	  document.SancProcSingle_Edit.txtAcc_HeadCode.value=ACCOUNT_HEAD_CODE;
	  document.SancProcSingle_Edit.bud_pro.value=BUD_PROVIDED;
	  document.SancProcSingle_Edit.txtEmpID_mas.value=SANCTIONED_BY;
	  
	  document.SancProcSingle_Edit.bud_spent.value=BUD_SPENT;
	  document.SancProcSingle_Edit.bal_amt.value=BAL_AMT;
	  document.SancProcSingle_Edit.ac_unit.value=PAYMENT_TO_BE_MADE_UNIT_ID;
	  document.SancProcSingle_Edit.txtRemarks.value=REMARKS;
	
	
	  acc_desc();
	
	  
	  
}

//accout head desc



function acc_desc()
{
	 var headcode1=document.getElementById("txtAcc_HeadCode").value;
	 var headcode;
		 if(headcode1==""){headcode=0;}
		 else{headcode=headcode1;}
     var url="../../../../../Sanc_Proc_Single?Command=Descrip&headcode="+headcode;
     
	 var req=getTransport();
	 req.open("GET",url,true); 
	 req.onreadystatechange=function()
	 {
	    handleResponse(req);
	 };   
	         req.send(null);
}


function description(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;    
    if(flag=="success")
    {
         var desc=baseResponse.getElementsByTagName("desc")[0].firstChild.nodeValue;
         document.getElementById("txtAcc_HeadDesc").value=desc;
     }
     else if(flag=="failure")
     {
         document.getElementById("txtAcc_HeadDesc").value="";
      }
}
//pay disp


function paydisp()
{	
	var code=document.getElementById("txtEmpID_trs").value;
	//alert("sssssssssss"+code);
    var url="../../../../../HR_Sanc_Proc_Mul?Command=code&code="+code;
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
       handleResponse1(req);
    };   
            req.send(null);
}
function loadpayName(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;    
    if(flag=="success")
    {
         var desc=baseResponse.getElementsByTagName("desc")[0].firstChild.nodeValue;
         var desig=baseResponse.getElementsByTagName("designation")[0].firstChild.nodeValue;
         //alert("Payee name and designation:::"+desc+"designation:::::"+desig);
         document.getElementById("pay_name").value=desc+"   "+desig;
     }
     else if(flag=="failure")
     {
         document.getElementById("pay_name").value="";
      }
    return true;
}


// Minor 
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
        	        	//alert("req   "+req);
        	            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
        	            var tagcommand=baseResponse.getElementsByTagName("command")[0];
        	            var Command=tagcommand.firstChild.nodeValue;
        	            if(Command=="minor")
        	            {
        	            	var res=minortypechecking(baseResponse);
        	            	
        	            	if(res==true){
        	              // minortypechecking(baseResponse);
        	 document.getElementById("billminortype").value=BILL_MINOR_TYPE_CODE;
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


function subtypechecking(baseResponse)
{

          var subcmb = document.forms[0].billsubtype;
          document.forms[0].billsubtype.length=0;
          var subcode = baseResponse.getElementsByTagName("subcode"); 
          var subdesc = baseResponse.getElementsByTagName("subdesc"); 
          if(subcode.length>0){
        	  var opt1 = document.createElement('option');
              opt1.value = 0;
              opt1.innerHTML ="select"; 
              subcmb.appendChild(opt1);
          for(var i=0; i<subcode.length; i++)
               {
        	         var opt1 = document.createElement('option');
                     opt1.value = subcode[i].firstChild.nodeValue;
                     opt1.innerHTML = subdesc[i].firstChild.nodeValue; 
                     subcmb.appendChild(opt1);
               }  
          }return true;
}

function minortypechecking(baseResponse)
{

		 var minorcmb = document.forms[0].billminortype;
         document.forms[0].billminortype.length=0;
         var minorcode = baseResponse.getElementsByTagName("minorcode");  
         var minordesc = baseResponse.getElementsByTagName("minordesc"); 
	   	 if(minorcode.length>0)
	   	 {
	   	  var opt1 = document.createElement('option');
          opt1.value = 0;
          opt1.innerHTML ="select"; 
          minorcmb.appendChild(opt1);
	   	///	var minorType = document.getElementById("billminortype");
	  /// 		minorType.length=1;
            for(var i=0;i<minorcode.length;i++)
               {
            	 /* if(minorcode.length==1)
            	  {
            		  var opt1 = document.createElement('option');
                      opt1.value = 0;
                      opt1.innerHTML ="select"; 
                      minorcmb.appendChild(opt1);
            		  
            	  }*/
            		  
            	     var opt1 = document.createElement('option');
                     opt1.value = minorcode[i].firstChild.nodeValue;
                     opt1.innerHTML = minordesc[i].firstChild.nodeValue; 
                     minorcmb.appendChild(opt1);
                 
               }  
         
	   	 } return true;
}

function loadSan(){
	
	var cmbAcc_UnitCode=document.forms[0].cmbAcc_UnitCode.value;
	  var txtCBYear=document.forms[0].txtCB_Year.value;
	   var txtCBMonth=document.forms[0].txtCB_Month.value;
	var cmbOffice_code=document.forms[0].cmbOffice_code.value;	
	// alert(cmbOffice_code);
	 var url="../../../../../HR_Note?Command=getSan_No&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCBYear="+txtCBYear+
	 "&txtCBMonth="+txtCBMonth;
	
var req=getTransport();
req.open("GET",url,true); 
req.onreadystatechange=function()
{
handleResponse1(req);
};   
  req.send(null);    
}
function loadSan_No(baseResponse){

   var SancNo = baseResponse.getElementsByTagName("San_No");  
	var Sanction_no = document.getElementById("Sanc_no");
	Sanction_no.length=0;
if(SancNo.length>0){
	
var Sanction_no = document.getElementById("Sanc_no");
	 var opttion = document.createElement("option");

	 opttion.value = "";
	 opttion.innerHTML ="--Select--"; 
	 Sanction_no.appendChild(opttion);
	  for(var i=0;i<SancNo.length;i++)
  {
		  var Sanc_no_no = baseResponse.getElementsByTagName("San_No")[i].firstChild.nodeValue;
		  var Sanction_no = document.getElementById("Sanc_no");
			 var opttion = document.createElement("option");
		
	  
	
		 // var opt1 = document.createElement("option");
	   opttion.value = Sanc_no_no;
	   opttion.innerHTML =Sanc_no_no; 
      Sanction_no.appendChild(opttion);
  }
	 }
}

function callingEdit(Command)         
{ 
	
   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
   var cmbOffice_code=document.getElementById("cmbOffice_code").value;
   var sancdate=document.getElementById("sanc_date").value;
   var txtCBYear=document.getElementById("txtCB_Year").value;
   var txtCBMonth=document.getElementById("txtCB_Month").value;
   var billmajortype=document.getElementById("billmajortype").value;
   var billminortype=document.getElementById("billminortype").value;
   var billsubtype=document.getElementById("billsubtype").value;
   var txtEmpIDmas=document.getElementById("txtEmpID_trs").value;
   var payname=document.getElementById("pay_name").value;
   var hr1=document.getElementById("hr").value;
   var frmdate=document.getElementById("frm_date").value;
   var todate=document.getElementById("to_date").value;
   var hramt=document.getElementById("hr_amt").value;
   var subvou1=document.getElementById("sub_vou").value;
   var refno1=document.getElementById("ref_no").value;
   var refdate=document.getElementById("ref_date").value;
   var sanc1=document.getElementById("sanc").value;
  
   var txtEmpIDmas1=document.getElementById("txtEmpID_mas").value;
   var sancamt=document.getElementById("sanc_amt").value;
   var budpro=document.getElementById("bud_pro").value;
   var budspent=document.getElementById("bud_spent").value;
   var balamt=document.getElementById("bal_amt").value;
   var acunit1=document.getElementById("ac_unit").value;
   var txtRemarks=document.getElementById("txtRemarks").value;
   var note_no=document.getElementById("note_no").value;
   var ho_date=document.getElementById("ho_date").value;
   var headcode1=document.getElementById("txtAcc_HeadCode").value;
   var active;
               if(document.SancProcSingle_Edit.radActive[0].checked)
               {
                       active = "E";
               }
               else if(document.SancProcSingle_Edit.radActive[1].checked)
               {
                       active = "U";
               }
               else
               {
            	   active="P";
               }
  var active1;
               if(document.SancProcSingle_Edit.radActive1[0].checked)
               {
                       active1 = "N";
               }
               else
               {
            	   active1="Y";
               }
               
   var refno;
      if(refno1==""){ refno=0;}
        else{ refno=refno1;}
   
   var sanc;
      if(sanc1==""){ sanc=0;}
        else{sanc=sanc1;}
     
  var txtEmpIDmas1;
      if(txtEmpIDmas==""){txtEmpIDmas1=0;}
      else{ txtEmpIDmas1=txtEmpIDmas;}
     
  var txtEmpIDmas;
	 if(txtEmpIDmas1==""){txtEmpIDmas=0;}
	 else{txtEmpIDmas=txtEmpIDmas1;}
	
  var hr;
      if(hr1==""){hr=0;}
      else{hr=hr1;}
     
  var subvou;
      if(subvou1==""){subvou=0;}
      else{subvou=subvou1;}
     

  var acunit;
	 if(acunit1==""){acunit=0;}
	 else{acunit=acunit1;}
	
  var headcode;
	 if(headcode1==""){headcode=0;}
	 else{headcode=headcode1;}
	
        if(Command=="Update")
         { 
    	   var inc=document.getElementById("Sanc_no").value;
   	   
//           var flag=nullcheck();
//           if(flag==true)
                          
               var url="../../../../../Sanc_Proc_Single?Command=Update&cmbAcc_UnitCode="+cmbAcc_UnitCode+
		               "&cmbOffice_code="+cmbOffice_code+"&sancdate="+sancdate+"&txtCBYear="+txtCBYear+"&refno="+refno+"&txtCBMonth="+txtCBMonth+"&billmajortype="+billmajortype+"&billminortype="+billminortype+"&billsubtype="+billsubtype+"&txtEmpIDmas1="+txtEmpIDmas1+
		               "&payname="+payname+"&hr="+hr+"&frmdate="+frmdate+"&todate="+todate+
		               "&hramt="+hramt+"&subvou="+subvou+"&refdate="+refdate+"&sanc="+sanc+
		               "&txtEmpIDmas="+txtEmpIDmas+"&sancamt="+sancamt+"&budpro="+budpro+"&budspent="+budspent+
		               "&balamt="+balamt+"&acunit="+acunit+"&txtRemarks="+txtRemarks+"&active="+active+"&active="+active+"&active1="+active1+"&headcode="+headcode+"&inc="+inc+"&note_no="+note_no+"&ho_date="+ho_date;
             alert("url >> "+url);
               var req=getTransport();
               req.open("GET",url,true); 
               req.onreadystatechange=function()
               {
                  handleResponse(req);
               };   
                       req.send(null);
           
      }
       else if(Command=="Delete")
       {
    	   var inc=document.getElementById("sanc_no").value;
           if(confirm("Do You Really want to Delete it?"))
           {
//              var flag=nullcheck();
//              if(flag==true)
              {  
            	  var url="../../../../../Sanc_Proc_Single?Command=Delete&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	               "&cmbOffice_code="+cmbOffice_code+"&sancdate="+sancdate+"&txtCBYear="+txtCBYear+"&txtCBMonth="+txtCBMonth+
	               "&inc="+inc;
                   var req=getTransport();
                   req.open("GET",url,true); 
                   req.onreadystatechange=function()
                   {
                      handleResponse(req);
                   } ;  
                           req.send(null);
             }
           }
      }
}

function ClearAll()
{
	  //  document.getElementById("sanc_no").value="";
      //  document.getElementById("sanc_date").value="";
        document.getElementById("txtCB_Year").value="";
        
        document.getElementById("billmajortype").value="0";
        document.getElementById("billminortype").length=0;
        document.getElementById("billsubtype").length=0;
        document.getElementById("note_no").length=0;
        document.getElementById("ho_date").value="";
          
        document.getElementById("txtEmpID_trs").value="";
        document.getElementById("pay_name").value="";
        document.getElementById("hr").value="";
        document.getElementById("frm_date").value="";
        document.getElementById("to_date").value="";
        document.getElementById("sub_vou").value="";
        document.getElementById("hr_amt").value="";
        document.getElementById("ref_no").value="";
        document.getElementById("ref_date").value="";
        document.getElementById("sanc").value="";
        document.getElementById("sanc_amt").value="";
        document.getElementById("txtEmpID_mas").value="";
        document.getElementById("bud_pro").value="";
        document.getElementById("bud_spent").value="";
        document.getElementById("bal_amt").value="";
        document.getElementById("ac_unit").value="";
        document.getElementById("txtRemarks").value="";
        document.getElementById("txtAcc_HeadCode").value="";
        document.getElementById("txtAcc_HeadDesc").value="";
        
        document.getElementById("cmdAdd").disabled=false;
        document.getElementById("cmdEdit").disabled=false;
        document.getElementById("cmdDelete").disabled=false;
        document.getElementById("Sanc_no").value="";
        document.getElementById("note_no").value="";
        
      
//
//        document.getElementById("txtAcc_HeadCode").disabled=false;
//        document.getElementById("txtRemarks").value="";
//        document.getElementById("ann_date1").value="";
//        document.getElementById("majorType").disabled = false;
//        document.getElementById("minorType").disabled = false;
//        document.getElementById("txtAcc_HeadCode").disabled = false;
//        document.getElementById("billsubtype").disabled = false;
//        document.getElementById("fin_yr").disabled=false;
}


function SancDetails(SancNo){
	var cmbAcc_UnitCode=document.forms[0].cmbAcc_UnitCode.value;
	  var txtCBYear=document.forms[0].txtCB_Year.value;
	   var txtCBMonth=document.forms[0].txtCB_Month.value;
	var cmbOffice_code=document.forms[0].cmbOffice_code.value;	
	
	 var url="../../../../../HR_Note?Command=SanNo_det&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCBYear="+txtCBYear+
	 "&txtCBMonth="+txtCBMonth+"&SancNo="+SancNo;
	//alert(url);
var req=getTransport();
req.open("GET",url,true); 
req.onreadystatechange=function()
{
handleResponse1(req);
};   
req.send(null);    
}