var empid;
var group;
var group1;
var des;
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

isMan={
             account_head_status : false
       };
function doFunction(Command,param)
{   
    var addtional_field_value;
    
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;    
    
        if(Command=="checkCode")
        {  
             //Reset isMan.account_head_status
             isMan.account_head_status = false;
    
             var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;

           
            if(txtAcc_HeadCode.length>=6)
            {
                var url="../../../../../Receipt_SL.view?Command=checkCode&txtAcc_HeadCode="+txtAcc_HeadCode;
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

function goBack(num,date,major,minor,sub,pay,hr,amt,acchead,rem,sanc_no)
{
	document.getElementById("sanc_no").value=sanc_no;
	document.getElementById("sanc_no").disabled = true;
	document.getElementById("sanc_date").value=date;
	document.getElementById("sanc_date").disabled = true;
	if(major=='0')
	{
		document.getElementById("billmajortype").value="0";
		document.getElementById("billminortype").value=0;
		document.getElementById("billsubtype").length=0;
	    }
	else
	{
	var s=minor.split("-");
    document.SancProcSingle.billmajortype.value = major; 
    minorType=document.getElementById("billminortype");
    var option=document.createElement("OPTION");
           option.text=s[1];
           option.value=s[0];
           minorType.length=0;	
           try
           {
                   minorType.add(option);
           }
           catch(errorObject)
           {
                   minorType.add(option,null);
           }
         }
	var s1=sub.split("-");
    minorType1=document.getElementById("billsubtype");
    //alert(s1[2]);
    var option=document.createElement("OPTION");
        if(s1[2]!="undefined")   
        	{option.text=s1[1]+s1[2];}
        else
    		{alert("undefined case");
        	option.text=s1[1];}
           option.value=s1[0];
           minorType1.length=0;	
           try
           {
                   minorType1.add(option);
           }
           catch(errorObject)
           {
                   minorType1.add(option,null);
           }
     var s2=pay.split("-");
     if(s2[0]=="E")
    	 {
    	  document.SancProcSingle.radActive[0].checked=true;
    	 }
     else if(s2[0]=="U")
	     {
	    	 document.SancProcSingle.radActive[1].checked=true;
	     }
     else
	     {
	    	 document.SancProcSingle.radActive[2].checked=true;
	     }
     document.getElementById("txtEmpID_trs").value=s2[1];
     document.getElementById("pay_name").value=s2[2];

     var s3=hr.split("-");
     document.getElementById("hr").value=s3[0];
     document.getElementById("hr_amt").value=s3[1];
     document.getElementById("sanc_amt").value=amt;
     
     var s4=acchead.split("-");
     document.getElementById("txtAcc_HeadCode").value=s4[0];
     document.getElementById("txtAcc_HeadDesc").value=s4[1]; 
     document.getElementById("txtRemarks").value=rem;
     
     document.SancProcSingle.cmdAdd.disabled = true;
     document.SancProcSingle.cmdEdit.disabled = false;
     document.SancProcSingle.cmdDelete.disabled = false;
     document.getElementById("frm_date").value="";
     document.getElementById("to_date").value="";
     document.getElementById("bud_pro").value="";
     document.getElementById("bud_spent").value="";
     document.getElementById("bal_amt").value="";
     document.getElementById("ac_unit").value="";
}


function nullcheck()
{
	if((document.SancProcSingle.cmbAcc_UnitCode.value==""))
    {
        alert("Select Account Unit Code");
        document.SancProcSingle.cmbAcc_UnitCode.focus();
        return false;
    }  
   else if(document.getElementById("cmbOffice_code").value=="")
    {
        alert("Select Accounting Office");
        document.getElementById("cmbOffice_code").focus();
        return false;
    }
   else if(document.getElementById("txtCB_Year").value=="")
   {
       alert("Enter Cash Book Year");
       document.getElementById("txtCB_Year").focus();
       return false;
   }
   else if(document.getElementById("txtCB_Month").value=="")
   {
       alert("Select Month");
       document.getElementById("txtCB_Month").focus();
       return false;
   }
   else
         return true;
}

function calling(Command)         
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
   var sanc_Office=document.getElementById("sanc_Office").value;
   var headcode1=document.getElementById("txtAcc_HeadCode").value;
   
   var active;
               if(document.SancProcSingle.radActive[0].checked)
               {
                       active = "E";
               }
               else if(document.SancProcSingle.radActive[1].checked)
               {
                       active = "U";
               }
               else
               {
            	   active="P";
               }
  var active1;
               if(document.SancProcSingle.radActive1[0].checked)
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
   if(Command=="Add")
       {
            //alert("add");
        var flag=nullcheck();
        if(flag==true)
           {                   
                   var url="../../../../../Sanc_Proc_Single?Command=Add&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                           "&cmbOffice_code="+cmbOffice_code+"&sancdate="+sancdate+"&txtCBYear="+txtCBYear+"&refno="+refno+"&txtCBMonth="+txtCBMonth+"&billmajortype="+billmajortype+"&billminortype="+billminortype+"&billsubtype="+billsubtype+"&txtEmpIDmas1="+txtEmpIDmas1+
                           "&payname="+payname+"&hr="+hr+"&frmdate="+frmdate+"&todate="+todate+
                           "&hramt="+hramt+"&subvou="+subvou+"&refdate="+refdate+"&sanc="+sanc+
                           "&txtEmpIDmas="+txtEmpIDmas+"&sancamt="+sancamt+"&budpro="+budpro+"&budspent="+budspent+
                           "&balamt="+balamt+"&acunit="+acunit+"&txtRemarks="+txtRemarks+"&active="+active+"&active="+active+"&active1="+active1+"&headcode="+headcode+"&note_no="+note_no+"&ho_date="+ho_date+"&sanc_Office="+sanc_Office;
                 // alert("Pathhhhhhhhhhhh"+url);
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
    	   var inc=document.getElementById("sanc_no").value;
   	     // alert("update");
//           var flag=nullcheck();
//           if(flag==true)
            {               
               var url="../../../../../Sanc_Proc_Single?Command=Update&cmbAcc_UnitCode="+cmbAcc_UnitCode+
		               "&cmbOffice_code="+cmbOffice_code+"&sancdate="+sancdate+"&txtCBYear="+txtCBYear+"&refno="+refno+"&txtCBMonth="+txtCBMonth+"&billmajortype="+billmajortype+"&billminortype="+billminortype+"&billsubtype="+billsubtype+"&txtEmpIDmas1="+txtEmpIDmas1+
		               "&payname="+payname+"&hr="+hr+"&frmdate="+frmdate+"&todate="+todate+
		               "&hramt="+hramt+"&subvou="+subvou+"&refdate="+refdate+"&sanc="+sanc+
		               "&txtEmpIDmas="+txtEmpIDmas+"&sancamt="+sancamt+"&budpro="+budpro+"&budspent="+budspent+
		               "&balamt="+balamt+"&acunit="+acunit+"&txtRemarks="+txtRemarks+"&active="+active+"&active="+active+"&active1="+active1+"&headcode="+headcode+"&inc="+inc+"&note_no="+note_no+"&sanc_Office="+sanc_Office;
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

function designation(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;   
    
    if(flag=="success")
    {
         var hid=baseResponse.getElementsByTagName("name")[0].firstChild.nodeValue;
         document.getElementById("pay_name").value=hid;
     }
     else if(flag=="failure")
     {
         document.getElementById("pay_name").value="";
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
    else if(flag=="AlreadyExist")
   {
    alert("Record AlreadyExist.so,can't Inserted");
   }
    else
    {
        alert("Record not inserted into database");
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

var window_BankAccNumber;
function Lists()
{  
	   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	   var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	   var txtCBYear=document.getElementById("txtCB_Year").value;
	   var txtCBMonth=document.getElementById("txtCB_Month").value;
		if((document.SancProcSingle.cmbAcc_UnitCode.value==""))
	    {
	        alert("Select Account Unit Code");
	        document.SancProcSingle.cmbAcc_UnitCode.focus();
	        return false;
	    }  
       else if(document.getElementById("cmbOffice_code").value=="")
        {
            alert("Select Accounting Office");
            document.getElementById("cmbOffice_code").focus();
            return false;
        }
       else if(document.getElementById("txtCB_Year").value=="")
       {
           alert("Enter Cash Book Year");
           document.getElementById("txtCB_Year").focus();
           return false;
       }
       else if(document.getElementById("txtCB_Month").value=="")
       {
           alert("Select Month");
           document.getElementById("txtCB_Month").focus();
           return false;
       }
        window_BankAccNumber= window.open("Sanc_Proc_Single_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCBYear="+txtCBYear+"&txtCBMonth="+txtCBMonth,"mywindow1","resizable=YES, scrollbars=yes"); 
        window_BankAccNumber.moveTo(250,250);    
        window_BankAccNumber.focus();
}

function loadcheckCode(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;   
    
    if(flag=="success")
    {
         var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
         document.getElementById("txtAcc_HeadCode").value=hid;
         var hdesc=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;          
         
         document.getElementById("txtAcc_HeadCode").value=hid;
         document.getElementById("txtAcc_HeadDesc").value=hdesc;
     }
     else if(flag=="failure")
     {
         alert("Account Head Code '"+document.getElementById("txtAcc_HeadCode").value+"' doesn't Exist");
         document.getElementById("txtAcc_HeadCode").value="";
         document.getElementById("txtAcc_HeadCode").focus();
     }
}

function majortypechecking(baseResponse)
{
         var billcombo = document.forms[0].billmajortype;
         var mastercode = baseResponse.getElementsByTagName("mastercode"); 
         var masterdesc = baseResponse.getElementsByTagName("masterdesc");   
         for(var i=0; i<mastercode.length; i++)
             {
                 var opt = document.createElement('option');
                 opt.value = mastercode[i].firstChild.nodeValue;
                 opt.innerHTML = masterdesc[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                 billcombo.appendChild(opt);
             }
}

var winAccHeadCode;
function AccHeadpopups()
{
    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) 
    {
       winAccHeadCode.resizeTo(500,500);
       winAccHeadCode.moveTo(250,250); 
       winAccHeadCode.focus();
    }
    else
    {
        winAccHeadCode=null
    }
        
    winAccHeadCode= window.open("../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Acc_Head_Dir_List_InUse.jsp","AccountHeadSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAccHeadCode.moveTo(250,250);  
    winAccHeadCode.focus();
    
}

function doParentAccHead(code)
{
   document.getElementById("txtAcc_HeadCode").value=code;
   doFunction('checkCode',true);
   return true;
}

function employee_popup_masters()
{
    emp_flag=true;
    servicepopups();
}

var winemp;

function servicepopups()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,500);
       winemp.moveTo(250,250); 
       winemp.focus();
    }
    else
    {
        winemp=null;
    }
        
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopupJS2.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doName(group1)
{
	var v=document.getElementsByName("sel");
	if(v)
	{
	    for(i=0;i<v.length;i++)
	    {
	        if(v[i].checked==true)
	        {
	            doParentEmp1(v[i].value);
	            return true;
	        }
	       
	    }
	}
	empid=document.getElementById("txtEmpID_trs").value;	
	group1=group1;
	var url="../../../../../Sanc_Proc_Single?Command=txtgpf&empid="+empid+"&group1="+group1;
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
       handleResponse(req);
    };   
            req.send(null);   
}

function doName1(group,des)
{
	var v=document.getElementsByName("sel");
	if(v)
	{
	    for(i=0;i<v.length;i++)
	    {
	        if(v[i].checked==true)
	        {
	            doParentEmp1(v[i].value);
	            return true;
	        }
	       
	    }
	}
	empid=document.getElementById("txtEmpID_trs").value;
	group=group;
	des=des;
	var url="../../../../../Sanc_Proc_Single?Command=desig&empid="+empid+"&group="+group+"&des="+des;
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
       handleResponse(req);
    };   
            req.send(null);   
}

function doParentEmp1(emp)
{
       if(emp_flag==true)
        {//alert("inside if");
            document.getElementById("txtEmpID_trs").value=emp;
        }
        else if(emp_flag==false)
         {
            document.getElementById("txtEmpID_trs").value=emp;
            //doFunction('Load_SL_Code',document.getElementById("cmbSL_type").value);
        }
}

function filter_real(evt,item,n,pre)
{
         var charCode = (evt.which) ? evt.which : event.keyCode;
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
		                 };   
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
			             };   
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





function loadNote_no()
{
	///alert('test');
	  var cmbAcc_UnitCode=document.forms[0].cmbAcc_UnitCode.value;
	   var cmbOffice_code=document.forms[0].cmbOffice_code.value;
	   var txtCBYear=document.forms[0].txtCB_Year.value;
	   var txtCBMonth=document.forms[0].txtCB_Month.value;
        var url="../../../../../Sanc_Proc_Single?Command=loadNote_no&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                           "&cmbOffice_code="+cmbOffice_code+"&txtCBYear="+txtCBYear+"&txtCBMonth="+txtCBMonth;
    
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
        	
        	handleResponse1(req);
        } ;  
                req.send(null);   
}
function loadNote_noMUL()
{

	
	  var cmbAcc_UnitCode=document.forms[0].cmbAcc_UnitCode.value;
	   var cmbOffice_code=document.forms[0].cmbOffice_code.value;
	   var txtCBYear=document.forms[0].txtCB_Year.value;
	   var txtCBMonth=document.forms[0].txtCB_Month.value;
        var url="../../../../../Sanc_Proc_Single?Command=loadNote_no&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                           "&cmbOffice_code="+cmbOffice_code+"&txtCBYear="+txtCBYear+"&txtCBMonth="+txtCBMonth;
   
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
        	 if(req.readyState==4)
        	    { 
        	        if(req.status==200)
        	        { 
        	        	
        	            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
        	          
        	            var tagcommand=baseResponse.getElementsByTagName("flag")[0];
        	            var Flag=tagcommand.firstChild.nodeValue;
        	          
        	            if(Flag=="success")
        	            	{
        	            	var hr_noteno = document.getElementById("note_no");
        	        		hr_noteno.length=0;
        	        	   var hr_note_no = baseResponse.getElementsByTagName("hr_note_no");  
        	       
        	           if(hr_note_no.length>0)
        	           	 {
        	        	
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
        	    }
        	    }
        	
        } ;  
                req.send(null);   
}

function loadHoNoteNo(baseResponse){
	
	var hr_noteno = document.getElementById("note_no");
		hr_noteno.length=0;
	   var hr_note_no = baseResponse.getElementsByTagName("hr_note_no");  
 
   if(hr_note_no.length>0)
   	 {
	
   	  var opt1 = document.createElement("option");

   	  opt1.value = "";
      opt1.innerHTML ="--Select--"; 
      hr_noteno.appendChild(opt1);
   	  for(var i=0;i<hr_note_no.length;i++)
      {
   		  var hr_noteno = document.getElementById("note_no");
   	   var hOnote_no = baseResponse.getElementsByTagName("hr_note_no")[i].firstChild.nodeValue;
   	  //alert(">>"+hOnote_no);
   		  var opt1 = document.createElement("option");
          opt1.value = hOnote_no;
          opt1.innerHTML =hOnote_no; 
          hr_noteno.appendChild(opt1);
      }
   	 }

}
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
function loadDetails(val){
	
	var cmbAcc_UnitCode=document.forms[0].cmbAcc_UnitCode.value;
	  var txtCBYear=document.forms[0].txtCB_Year.value;
	   var txtCBMonth=document.forms[0].txtCB_Month.value;
	var cmbOffice_code=document.forms[0].cmbOffice_code.value;	
	// alert(cmbOffice_code);
	 var url="../../../../../HR_Note?Command=getDetailssing&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCBYear="+txtCBYear+
	 "&txtCBMonth="+txtCBMonth+"&NoteNo="+val;
	// alert(url);
var req=getTransport();
req.open("GET",url,true); 
req.onreadystatechange=function()
{
handleResponse1(req);
};   
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
 	   document.getElementById("billmajortype").value=BILL_MAJOR_TYPE_CODE;
 	  document.getElementById("sanc_Office").value=SANCTION_PROC_OFFICE_ID;
 	  callminorCp(BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE);
 	// callsub()
 	   document.getElementById("ho_date").value=NOTE_DATE;
 	  // document.getElementById("hr_amt").value=NOTE_AMOUNT;
 	//  document.getElementById("hr_amt").value=NOTE_AMOUNT;
 	  //document.getElementById("txtAcc_HeadCode").value=ACCOUNT_HEAD_CODE;
	//  document.getElementById("txtAcc_HeadCode").value=ACCOUNT_HEAD_CODE;
	//  document.getElementById("txtRemarks").value=NOTE_PREPARED_BY;
 	 
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

function handleResponse1(req)
{  
	//alert('testing ... ');
    if(req.readyState==4)
    { 
        if(req.status==200)
        { 
        	alert(req.status);
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            alert(baseResponse);
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
}

