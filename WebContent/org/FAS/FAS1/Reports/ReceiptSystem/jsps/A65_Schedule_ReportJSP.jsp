<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache">
    <title>Journal System Report</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>    
    <script language="javascript" type="text/javascript" src="../scripts/A65_Schedule_ReportJS.js"></script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    
     <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
    
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Com_Schedule_Account_Heads.js"> </script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script>
    <script type="text/javascript" language="javascript">
     function loadyear_month()
     {
  
		      var today= new Date(); 
		      var day=today.getDate();
		      var month=today.getMonth();
		      month=month+1;
		      var year=today.getYear();
		      if(year < 1900) year += 1900;	       
		      document.Schedule_A65.txtCB_Year.value=year
		      document.Schedule_A65.txtCB_Month.value=month;
    
     }
    </script>
  </head>
  <body class="table" onload="loadyear_month();LoadAccountingUnitID('FOR_LIST_1')" >
  <form name="Schedule_A65" method="POST" action="../../../../../../A65_Schedule_ReportServ" onsubmit="return checknull()">
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
			            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
			            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
			
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
            	<strong>Schedule A65</strong>
          </div>
        </td>
      </tr>
       <tr class="table">
          <td>
            <div align="left">
              Accounting Unit Code 
              <font color="#ff2121">*</font>
            </div>
          </td>
          <td>
            <div align="left">
              <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                      tabindex="1" onchange="common_LoadOffice(this.value);">
               
              </select>
            </div>
          </td>
        </tr>
        <tr class="table">
          <td>
            <div align="left">
              Accounting For Office Code 
              <font color="#ff2121">*</font>
            </div>
          </td>
          <td>
            <div align="left">
              <select size="1" name="cmbOffice_code" id="cmbOffice_code"
                      tabindex="2">
               
              </select>
            </div>
          </td>
        </tr>
        <tr align="left">
          <td class="table">
            <div align="left">Cash Book Year &amp; Month</div>
          </td>
          <td>
            <div align="left">
              <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"
                     maxlength="4" size="5"
                     onkeypress="return numbersonly(event)"></input>
               
              <select name="txtCB_Month" id="txtCB_Month" tabindex="4">
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
    	<tr class="tdH">
	      <td  colspan="2">
	          <div align="center">
		          <input type=submit value=Submit >
		          <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="btncancel()">
	     	 </div>
	      </td>
      </tr>
     </table>
    </form>
  </body>
</html>