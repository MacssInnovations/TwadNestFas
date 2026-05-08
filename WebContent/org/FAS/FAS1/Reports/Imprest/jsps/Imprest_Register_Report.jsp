<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <title>Imprest/Temporary Advance Register</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script language="javascript" type="text/javascript"
            src="../scripts/Imprest_Register_Report.js"></script>
            
            
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript" 
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
            
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript"
            src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
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
		   
		     document.frmSubLedgerReport.txtCB_Year.value=year
		     document.frmSubLedgerReport.txtCB_Month.value=month;
		    
		     /** Load To Cash Book Month and To Cash Book Year during Form Load */
		     document.frmSubLedgerReport.txtCB_Year_to.value=year
		     document.frmSubLedgerReport.txtCB_Month_to.value=month;
    
    
     }
     function closeWindow()
     {                
	         window.open('','_parent','');                
	         window.close(); 
	         window.opener.focus();
     }
    </script>
   
  </head>
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
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;// Postgres DB  Connection
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
    
   
   %> 
  <body class="table" onload="loadyear_month();"><form name="frmSubLedgerReport"
                                                      method="POST"
                                                      action="../../../../../../Imprest_Register_Report"
                                                      onsubmit="return checknull()">
      <table cellspacing="2" cellpadding="3" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Imprest/Temporary Advance Register</strong>
            </div>
          </td>
        </tr>
      </table>
      <div align="center">
        <table cellspacing="1" cellpadding="2"  width="100%">
          <tr>
		        <td align="left">View Report</td>
		        <td colspan="3" align="left">
		          <input type="radio" name="viewReport" id="viewReport" value="RegionWise" onclick="hideOthers();" 
		                 checked="checked"></input>
		          Region-wise
		          <input type="radio" name="viewReport" id="viewReport" onclick="hideOthers();" value="OfficeWise"></input>
		          Office-wise
		          <input type="radio" name="viewReport" id="viewReport" onclick="hideOthers();" value="All"></input>
		          All Office
		        </td>
     	  </tr>
     	
     	   <tr class="table" >
     	   
                <td>
                 <div id="regiondiv" style="display:block">
                   Region Name
                    <font color="#ff2121">*</font>
                 </div>
                </td> 
                <td colspan="4">
                  <div id="regiondiv1" style="display:block">
                    <select size="1" name="txtRegionId" id="txtRegionId" tabindex="1" onchange="callUnits();">
                     <option value="0">--Select Region--</option>
                      <%
                      try{
                                ps=con.prepareStatement("select OFFICE_NAME,OFFICE_ID from COM_MST_OFFICES where OFFICE_LEVEL_ID in ('RN','HO') and office_status_id not in('CL','RD','NC')");
                                rs=ps.executeQuery();
                                while(rs.next())
                                {
                                    out.println("<option value="+rs.getInt("OFFICE_ID")+">"+rs.getString("OFFICE_NAME")+"</option>");
                                }
                          }
                      catch(Exception e)
                        {
                            System.out.println(e);
                        }
                      %>
                      </select>
                  </div>
                </td>
              </tr>
        
          <tr class="table" >
            <td>
               <div  id="unitdiv" style="display:none">
                Accounting Unit Code 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
               <div id="unitdiv1" style="display:none">
                <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                        tabindex="1" onchange="common_LoadOffice(this.value);"></select>
              </div>
            </td>
          </tr> 
        
           <tr class="table" >
            <td>
              <div id="officediv" style="display:none">
                Accounting For Office Code 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
               <div  id="officediv1" style="display:none">
                <select size="1" name="cmbOffice_code" id="cmbOffice_code"
                        tabindex="2"></select>
              </div>
            </td>
          </tr>
         
          <tr align="left">
            <td class="table">
              <div align="left">Cash Book Year &amp; Month</div>
            </td>
            <td>
              <div align="left">
                From 
                <input type="text" name="txtCB_Year" id="txtCB_Year"
                       tabindex="3" maxlength="4" size="5"
                       onkeypress="return numbersonly(event)"></input>
                 
                <select name="txtCB_Month" id="txtCB_Month" tabindex="4">
                  <!--<option value="">select the Month</option>-->
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
                 To 
                <input type="text" name="txtCB_Year_to" id="txtCB_Year_to"
                       tabindex="3" maxlength="4" size="5"
                       onkeypress="return numbersonly(event)"></input>
                 
                <select name="txtCB_Month_to" id="txtCB_Month_to" tabindex="4">
                  <!--<option value="">select the Month</option>-->
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
                <td width="30%">
                  <div align="left">
                    Advance Type
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select name="cmbAdvance_type" id="cmbAdvance_type" tabindex="4">
                        <option value="I" selected="selected">Imprest</option>
                        <option value="T">Temporary Advance</option>
                    </select>                     
                  </div>
                </td>
              </tr>
        </table>
      </div>
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
      <table align="center" cellspacing="3" cellpadding="2" 
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
    </form></body>
</html>