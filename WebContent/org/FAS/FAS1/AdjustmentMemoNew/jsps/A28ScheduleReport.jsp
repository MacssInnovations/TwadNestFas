<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Twad A28 SchedulReport</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
  
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/UnitwiseOffice.js"></script> 
    
    
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"  src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
   
    
      
    
    <script type="text/javascript" language="javascript">
     	 function loadyear_month()
         {
       		
	         var today= new Date(); 
	         var day=today.getDate();
	         var month=today.getMonth();
	         month=month+1;
	         var year=today.getYear();
	         if(year < 1900) year += 1900;
	       
	         document.SingleReceiptList.txtCB_Year.value=year;
	         document.SingleReceiptList.txtCB_Month.value=month;
        
         }
         function cal()
         {
             alert("Exit");
             self.close();
         }
    </script>
    
</head>
<body class="table" onload="loadyear_month(),LoadAccountingUnitID('LIST_ALL_UNITS');" >
<table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">A28ScheduleReport</font>
          </div>
        </td>
      </tr>
</table>
<%
  
		      Connection con=null;
		      ResultSet rs=null,rs2=null;
		      PreparedStatement ps=null,ps2=null,ps3=null;
		      ResultSet results=null;
		      ResultSet results1=null,rs3=null;
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
 
<form name="SingleReceiptList" method="POST" action="../../../../../A28ScheduleServlet" >
<div align="center">
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
		            <tr class="table">
                       <td>
                          <div align="left">
                            Accounting Unit Id 
                            <font color="#ff2121">*</font>
                          </div>
                       </td>
                       <td>
                        <div align="left">
                          <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);">
            
                      
                        </select>
                        </div>
                        </td>
                </tr>
                <tr class="table">
                <td>
                  <div align="left">
                    Accounting For Office Id
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2"> 
                      
                   
                </select>
                </div>
                </td>
                </tr>
		           <tr class="table">
                <td>
                  <div align="left">
                  Cash Book Year and Month
                    <font color="#ff2121">*</font>
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
                </tr>    
		       
		       <tr class="table">
                       
                       <td colspan="2" class="tdH">
                        <div align="center" >
                        <input type="Submit" value="GO" name="ByMonth" id="ByMonth"/>  <input type="button" value="Exit" name="b1" id="b1" onclick="return cal()"></input>
                        </div>
                        </td>
                        <td>
                       
                        </td>
                </tr>
		       
		       
		       
		       
		       
		       
		   
            </table>
      </div>
    
    
</form>
</body>
</html>