<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <title>Freeze A52 Register(Unit Level)</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript"
            src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>   
             
    <script language="javascript" type="text/javascript" src="../scripts/A52_Register_Unit_Status_Freeze.js"></script>
    
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
  </head>
  <body class="table" onload="LoadAccountingUnitID('LIST_ALL_UNITS'),setTimeout('common_LoadOfficeCode()',200);">
  <form name="frmFreeze_A52Register" id="frmFreeze_A52Register" ><!--
  method="post" action="../../../../../AA52_Unit_Freeze?command=viewAbstract"
                                                      
--><%
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
  int  cmbAcc_UnitCode=0,cmbOffice_code=0;

%>


      <table cellspacing="2" cellpadding="3" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Freeze A52 Register(Unit Level)</strong>
            </div>
          </td>
        </tr>
      </table>
      <div align="center">
        <table cellspacing="1" cellpadding="2" border="0" width="100%">
          <tr class="table1">
      <td><div align="left">Accounting Unit Code <font color="#ff2121">*</font> </div></td>
	  <td><div align="left">
          <select name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode" 
			onchange="common_LoadOfficeCode()" >
			
          </select>
      </div></td>
	  </tr>
	  
	<tr class="table1">
      <td><div align="left">Accounting For Office Code <font
			color="#ff2121">*</font></div></td>
	  <td><div align="left">
          <select size="1" name="cmbOffice_code"
			id="cmbOffice_code" tabindex="2" class="combolist">
	
                   
          </select>
      </div></td>
	  </tr>
          <tr align="left">
            <td class="table">
              <div align="left">Financial Year </div>
            </td>
            <td>
              <div align="left">
               <select name="cmbFinancialYear" id="cmbFinancialYear">
                <option value="">---select year----</option>
               <option value="2012-13">2012-13</option>
		     
		      </select>
            </div>
            </td>
          </tr>
          <tr align="left">
            <td class="table">
              <div align="left">Register Option </div>
            </td>
            <td>
              <div align="left">
           <input type="radio" id="a52_option" name="a52_option" value="A52" >A52 Register
	    </div>
            </td>
          </tr>
        </table>
      </div>  
      <table align="center" cellspacing="3" cellpadding="2" border="0"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
            <input type="button" id="btverifyA52" name="btverifyA52"  value="Freeze A52 " onclick="doFunction('FreezeA52Unit','null');"/>               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="closeWindow()"></input>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>