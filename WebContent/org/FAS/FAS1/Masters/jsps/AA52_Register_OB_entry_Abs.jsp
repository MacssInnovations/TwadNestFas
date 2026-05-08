<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>AA52 Register for Closing Balance Report Abstract</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
  <script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>

    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script> 
     <!-- to avoid future date the above script used-->
    <script type="text/javascript" language="javascript">
         function foc()
         {
         }
</script>

     <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
                
                function checkNull(){
                	var financial_year = document.frmAA52_Register_Abstract.cmbFinancialYear.value; 
                	
                	   	if(financial_year==""){
                		   alert("select Finanical year");
                		   return false;
                	}
                	   	return true;
                }
    </script>
  </head>
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">AA52 Register for Closing Balance Report Abstract </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmAA52_Register_Abstract" id="frmAA52_Register_Abstract" method="POST" action="../../../../../FAS_AA52_Report_Servlet_Abstract" onsubmit="return checkNull()">
                  
  <%
  
      Connection con=null;
      ResultSet rs=null,rs2=null;
      PreparedStatement ps=null,ps2=null;
      ResultSet results=null;
      ResultSet results1=null;
      ResultSet results2=null;
       Statement statement=null;
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
           <% 
       HttpSession session=request.getSession(false);
         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();
     int bankid=0;
     
    //int empid=9315;
    int  oid=0;
    String oname="";
    try
    {
           
            ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
            ps.setInt(1,empid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oid=results.getInt("OFFICE_ID");
                 }
            results.close();
            ps.close();
            ps=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
            ps.setInt(1,oid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oname=results.getString("OFFICE_NAME");
                  }
            results.close();
            ps.close();
     /* */      
            System.out.println("off id.. emp id"+oid+".."+empid);     
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
   
    
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
        
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
                        tabindex="1" onchange="common_LoadOffice(this.value);"></select>
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
                        tabindex="2"></select>
              </div>
            </td>
          </tr>
           <tr align="left">
              <td class="table">
                <div align="left">
                         Financial Year <font color="#ff2121">*</font>
              </div>
              </td>
              <td colspan="2">
                    <select name="cmbFinancialYear" id="cmbFinancialYear" tabindex="3"  >
                    <option value="">--Select Year--</option>
                    <option value="2011-12">2011-12</option>
                    <option value="2012-13">2012-13</option>
                    </select>
              </td>
              </tr>
              <tr>
			        <td align="left">Report Option:</td>
			        <td colspan="3" align="left">
			          <input type="radio" name="txtoption" id="txtoption" value="PDF"
			                 checked="checked"></input>
			          PDF
			          <input type="radio" name="txtoption" id="txtoption" value="EXCEL"></input>
			          Excel
			          <input type="radio" name="txtoption" id="txtoption" value="HTML"></input>
			          HTML
			        </td>
			  </tr>
              </table>
            <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="submit" value="Submit"></input>
               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
          </td>
        </tr>
      </table>
              </td>
            </tr>
         </table>
    </form></body>
</html>