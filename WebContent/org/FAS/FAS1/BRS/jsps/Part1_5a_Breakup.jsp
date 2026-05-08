<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BRS Part1-5a-Breakup</title>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
<script type="text/javascript"
	src="../../../../../org/Library/scripts/checkDate.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="../../../../Security/scripts/tabpane.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>

<script type="text/javascript" language="javascript">
       function loadyear_month()
       {
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             var year=today.getYear();
             if(year < 1900) year += 1900;
                        
            document.formbrs_ob_report.txtCB_Year.value=year;
    
      }
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
                   return false; 
           }
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

    	function LoadBankAccountNumber_col()
    	{  	
    		   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	 
    		   var url="../../../../../BRSReport1?command=LoadBankAccountNumber&cmbAcc_UnitCode="+cmbAcc_UnitCode;
    		   if(document.getElementById("txtOprCode"))
    		   {
    			 	var cashbook_yr=document.getElementById("txtCB_Year").value;	 
    			 	var cashbook_mn=document.getElementById("txtCB_Month").value;	 
    			 	url+="&option=nontwad";
    		   }
    		//   alert(url);
    		   var req=getTransport();
    		   req.open("GET",url,true); 
    		   req.onreadystatechange=function()
    		   {
    		    	 LoadBankAccountNumberRes(req);
    		   }   
    	       req.send(null);
    		  
    	}

    	function LoadBankAccountNumberRes(req)
    	{
    	 
    	    if(req.readyState==4)
    	    {
    	        if(req.status==200)
    	        {  
    	             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
    	             var tagcommand=baseResponse.getElementsByTagName("command")[0];
    	             var Command=tagcommand.firstChild.nodeValue;
    	           
    	            if(Command=="LoadBankAccountNumber")
    	            {
    	            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    	            	 
    	            	 if(flag=="success")
    	            	    {
    	            	          
    	            	           
    	            	           /** Bank Account Number Object to find length */ 
    	            	           var acc_no=baseResponse.getElementsByTagName("acc_no");
    	            	           
    	            	           /** Get Combo box Object */
    	            	           var cmbBankAccNo = document.getElementById("cmbBankAccNo");
    	            	        //   alert(acc_no.length);
    	            	            for(var k=0;k<acc_no.length;k++)
    	            	            {
    	            	            	bank_ac_no[k]=baseResponse.getElementsByTagName("acc_no")[k].firstChild.nodeValue;
    	            	            	acc_desc[k]=baseResponse.getElementsByTagName("acc_desc")[k].firstChild.nodeValue;
    	            	            	bank_name[k]=baseResponse.getElementsByTagName("bank_name")[k].firstChild.nodeValue;
    	            	            	branch_name[k]=baseResponse.getElementsByTagName("branch_name")[k].firstChild.nodeValue;
    	            	            	bank_id[k] =baseResponse.getElementsByTagName("bank_id")[k].firstChild.nodeValue;
    	            	            	branch_id[k] =baseResponse.getElementsByTagName("branch_id")[k].firstChild.nodeValue;
    	            	            	opr_mode[k] =baseResponse.getElementsByTagName("opr_mode")[k].firstChild.nodeValue;            	            	
    	            	            }
    	            	         
    	            	            cmbBankAccNo.innerHTML="";
    	            	            var option=document.createElement("OPTION");
    	            	            option.text="--Select Bank Acc. Number--";
    	            	            option.value="";
    	            	            try
    	            	            {
    	            	            	cmbBankAccNo.add(option);
    	            	            }catch(errorObject)
    	            	            {
    	            	            	cmbBankAccNo.add(option,null);
    	            	            }
    	            	            
    	            	            for(var k=0;k<acc_no.length;k++)
    	            	            {   
    	            	                  var option=document.createElement("OPTION");
    	            	                  option.text=acc_desc[k];
    	            	                  option.value=bank_ac_no[k];
    	            	                  try
    	            	                  {
    	            	                	  cmbBankAccNo.add(option);
    	            	                  }
    	            	                  catch(errorObject)
    	            	                  {
    	            	                	  cmbBankAccNo.add(option,null);
    	            	                  }
    	            	            }
    	            	    }
    	              }
    	              else
    	              {
    	            	    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;             	 
    	             	    if(flag=="success")
    	             	    {
    	             	    		var acc_head_code=baseResponse.getElementsByTagName("acc_head_code")[0].firstChild.nodeValue;
    	             	    		document.getElementById("txtOprCode").value=acc_head_code;
    	             	    }
    	              }
    	        
    	        }
    	    }
    	}

    </script>
</head>
<%
	String s = request.getContextPath();
%>

<body 
	onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><strong>Part1-5a-Breakup</strong> </div>
		</td>
	</tr>
</table>


<form name="formbrs_ob_report" id="formbrs_ob_report" method="POST"
	action="../../../../../BRS_OB_Report_not_pushed?command=one_fivea"> 

<input type="hidden" id="old" name="old" value="old">
<table cellspacing="1" cellpadding="2" border="0" width="100%">

	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>General Details</strong>
		<div id="imgfld"
			style="position: absolute; top: 354px; visibility: hidden; left: 378px; width: 212px; height: 6px;"
			left=100 top=100><input type="image" name="img1" id="img1"
			src="../../../../../images/Loading.gif" height="200"></div>
		</div>
		</td>
	</tr>



	<tr class="table">
		<td>
		<div align="left">Accounting Unit Code <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode" tabindex="1"
			onchange="common_LoadOffice(this.value);">
		</select></div>
		</td>
	</tr>



	<tr class="table">
		<td>
		<div align="left">Accounting For Office Code <font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbOffice_code"
			id="cmbOffice_code" tabindex="2">
		</select></div>
		</td>
	</tr>



	<tr class="table">
		<td>
		<div align="left">Cash Book Year &amp; Month</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCB_Year"
			id="txtCB_Year" tabindex="3" maxlength="4" size="5"
			onkeypress="return numbersonly1(event,this)"></input> <select
			name="txtCB_Month" id="txtCB_Month" tabindex="4" >
			<option value="s">--- Select ---</option>
			<option value="1">January</option>
			<option value="2">February</option>
			<option value="3">March</option>
			<option value="4">April</option>
			<option value="5">May</option>
			<option value="6">June</option>
			<option value="7">July</option>
			<option value="8">August</option>
			<option value="9">September</option>
			<option value="10">October</option>
			<option value="11">November</option>
			<option value="12">December</option>
		</select></div>
		</td>
	</tr>



	<tr class="table">
		<td>
		<div align="left">Bank A/C No.</div>
		</td>
		<td>
		<div align="left"><select name="cmbBankAccNo" id="cmbBankAccNo">
			
			<option value="">-- Select Bank A/C No ---</option>
		</select> <input type="button" name="Go" id="Go" value="Go"
			onclick="LoadBankAccountNumber()" />
		<input type="hidden"
			name="txtOprMode" id="txtOprMode" tabindex="5"
			style="background-color: #ececec" readonly="readonly" size="50" /></div>
		</td>
	</tr>




	
</table>






<br>



<!-- ------------------------- Buttons Part --------------------------------  -->

<div align="center">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td>
		<div align="center"><input type="SUBMIT" name="butSub"
			id="butSub" value="SUBMIT" />
		&nbsp;&nbsp;&nbsp; <!--
             
               --> <input type="button" name="butCan" id="butCan"
			value="EXIT" onClick="exit();" /></div>
		</td>
	</tr>
</table>
</div>


</form>
</body>
</html>