<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/> 
     <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
	<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>List of Receipt and Fund Transfers</title>
     
     <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script language="javascript" type="text/javascript" src="../scripts/Verify_Fund_Transfer_HO.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>  
   
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
     <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
     <script type="text/javascript" src="../../../../../org/FAS/FAS1/UnitwiseOffice.js"></script> 
   <script type="text/javascript" language="javascript">
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.listReceipt.txtCB_Year.value=year;
        document.listReceipt.txtCB_Month.value=month;
        
         }
     function Report(){	
    	    //var file_type=document.leaveMaster.output.value;    
    		document.listReceipt.action="../../../../../ListReceiptFundTransfer?fileType=pdf";
    	    document.listReceipt.method="POST";
    	    document.listReceipt.submit();
    	}
    </script>
      </head>
       <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
<body class="table" onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');" >
<form name="listReceipt" method="POST" action="../../../../../ListReceiptFundTransfer">
   <%
  Connection con=null;
  ResultSet rs=null,rs2=null;
  PreparedStatement ps=null,ps2=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
   try
  {
  
             ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rs1.getString("Config.DSN");
            String strhostname=rs1.getString("Config.HOST_NAME");
            String strportno=rs1.getString("Config.PORT_NUMBER");
            String strsid=rs1.getString("Config.SID");
            String strdbusername=rs1.getString("Config.USER_NAME");
            String strdbpassword=rs1.getString("Config.PASSWORD");

            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  %>
  
  
   
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <strong>List of Receipt and Fund Transfers
 			</strong>
          </div>
        </td>
      </tr>
    </table>
     <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
            <tr class="table">
                    <td>
                      <div align="left" >
                              Accounting Unit Code  <font color="#ff2121">*</font>
                      </div>
                      
                    </td>
                    <td>
                      <div align="left">
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="LoadDetails(this.value);">        
                         </select>
                      </div>
                    </td>
             </tr>
            <!--  <tr class="table">
                    <td>
                      <div align="left">
                        Accounting For Office Code <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <select size="1" name="cmbOffice_code" id="cmbOffice_code" >                          
                        </select>
                      </div>
                    </td>
             </tr>
              <tr class="table">
                  <td class="table">
			         <div align="left">
			             Cash Book Year &amp; Month&nbsp;&nbsp;<strong>:</strong>			                       
			          </div>
			      </td>
			      <td>
			      	<div align="left">
			      		<input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
			         
			          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
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
			          </select>
			      	</div>
			      </td>
             </tr> -->
              <!--<tr class="table">
                <td>
                  <div align="left">
                    Receipt Type
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="receiptType" id="receiptType" tabindex="3">
                      <option value="C">C</option>
                      <option value="B">B</option> 
                    </select>
                  </div>
                </td>
              </tr>
        --></table>
        </div>
        
        <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            
            <th>
              Bank Account No
            </th>
            <th>
              Type Of Bank Account
            </th>
            <th>
              Operation Mode
            </th>
            <th>
              Bank Name
            </th>
           <th>
              Branch Name
            </th>
             <th>
              Module Name
            </th>
            <th>
              Head Of Account
            </th>
            <th>
              Debit or Credit
            </th>
            <th>
              Default YES/NO
            </th>
          </tr>
          <tbody id="tbody" class="table">
          
         
          </tbody>
        </table>
        
        
      <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >            
      	<tr class="tdH">
      	<td>
          <div align="center">
          <input type="button" name="submitbtn" value="Submit" id="submitbtn" tabindex="30" onclick="Verify()"/>
	             &nbsp;&nbsp;&nbsp; 
         <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="btncancel()">
      </div>
      </td>
      </tr>
      
      </table>
      </form>
  </body>
</html>