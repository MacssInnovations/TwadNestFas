<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ page import="java.sql.Date" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title>Inter Bank Transfer Report</title>
 <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    
    <script language="javascript" type="text/javascript" src="../scripts/IBT_From_Report.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
      <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
      
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script>  <script language="javascript" type="text/javascript" src="../scripts/IBT_Report.js"></script>
    <script type="text/javascript" language="javascript">
     function loadyear_month()
        {
       
         var today= new Date();
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
     
        
      }
    </script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
    
      </head>
      
   <%
	String s = request.getContextPath();
%>
<%
	System.out.println(s);
%>
 <body onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');">
  <form name="frminterbankReport" id="frminterbankReport" method="POST" action="IBT_Report">
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
  long l=System.currentTimeMillis();
	Timestamp ts=new Timestamp(l);                      
	 Date ctdate = new java.sql.Date(ts.getTime());
  %>
  <% 
        HttpSession session=request.getSession(false);
        UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();
    //int empid=9315;
    int  oid=0;
    String oname="";
   
    try {

 		//ps = con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
 		//ps.setInt(1, empid);
 		 ps=con.prepareStatement(" SELECT "+
            		"  CASE "+
            		 "   When Old_Office_Id   Is Not Null "+
            		  "  AND DATE_ALLOWED_UPTO>=? "+
            		    " THEN OLD_OFFICE_ID "+
            		    " ELSE Office_Id "+
            		  " END AS OFFICE_ID "+
            		" FROM "+
            		  " (SELECT Office_Id, "+
            		    " OLD_OFFICE_ID, "+
            		    " DATE_ALLOWED_UPTO "+
            		  " From Hrm_Emp_Current_Posting "+
            		  " Where Employee_Id=? ) op1" );
            ps.setDate(1, ctdate);
            ps.setInt(2,empid);
 		results = ps.executeQuery();
 		if (results.next()) {
 			oid = results.getInt("OFFICE_ID");
 		}
 		results.close();
 		ps.close();
 		ps = con
 				.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
 		ps.setInt(1, oid);
 		results = ps.executeQuery();
 		if (results.next()) {
 			oname = results.getString("OFFICE_NAME");
 		}
 		results.close();
 		ps.close();
 		/* */
 		System.out.println("off id.. emp id" + oid + ".." + empid);
 	} catch (Exception e) {
 		System.out.println(e);
 	}
 %>

<div align="center">
<table cellspacing="1" cellpadding="2" border="1" width="100%">
<tr class="tdH1">
		<td colspan="2">
		<div align="center"><strong>IBT Report</strong></div>
		<input type="hidden" name="strpath" id="strpath" value="<%=s%>">
	   </td>
	</tr>
 
	<tr class="table">
		<td>
		<div align="left">Accounting Unit Code <font color="#ff2121">*</font>		</div>		</td>
		<td>
		<div align="left"><!--<input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>-->
		<select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
			tabindex="1">
			<!-- <option value="0"> Select Account Unit </option>-->
			<%
				int unitid = 0;
				String unitname = "";
				try {
					if (oid == 5000) {
						//out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
						//ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?");
						String getWing = "select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=? and OFFICE_WING_SINO=(select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)";
						ps = con.prepareStatement(getWing);
						ps.setInt(1, oid);
						ps.setInt(2, empid);
						ps.setInt(3, oid);
						rs = ps.executeQuery();

						if (rs.next()) {
							out.println("<option value="
									+ rs.getInt("ACCOUNTING_UNIT_ID") + ">"
									+ rs.getString("ACCOUNTING_UNIT_NAME")
									+ "</option>");
							unitid = rs.getInt("ACCOUNTING_UNIT_ID");

							System.out.println(".."
									+ rs.getInt("ACCOUNTING_UNIT_ID"));
							System.out.println(".."
									+ rs.getString("ACCOUNTING_UNIT_NAME"));
							System.out
									.println(".." + rs.getInt("OFFICE_WING_SINO"));

						}
						System.out.println(oid + " " + oname);
						ps.close();
						rs.close();
					} else {
						ps = con
								.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)");
						ps.setInt(1, oid);
						rs = ps.executeQuery();
						if (rs.next()) {
							System.out.println(rs.getInt("ACCOUNTING_UNIT_ID"));
							System.out
									.println(rs.getString("ACCOUNTING_UNIT_NAME"));
							//out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
							out.println("<option value="
									+ rs.getInt("ACCOUNTING_UNIT_ID") + " >"
									+ rs.getString("ACCOUNTING_UNIT_NAME")
									+ "</option>");
							unitid = rs.getInt("ACCOUNTING_UNIT_ID");
						}
						ps.close();
						rs.close();
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			%>
		</select></div>		</td>
      
		
	</tr>
	<tr class="table">
		<td>
		<div align="left">Accounting For Office Code <font
			color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><select size="1" name="cmbOffice_code"
			id="cmbOffice_code" tabindex="2">

			<%
				System.out.println("here");
				System.out.println(oid + "  " + oname);
				try {
					if (oid == 5000) {
						out.println("<option value=" + oid + ">" + "HEAD OFFICE"
								+ "</option>");
					} else {
						ps = con
								.prepareStatement("select ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID  from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_UNIT_ID=? order by ACCOUNTING_FOR_OFFICE_ID desc");
						ps.setInt(1, unitid);
						rs = ps.executeQuery();
						//out.println("<option value="+oid+">"+oname+"</option>");
						int countoffice = 0; // used to load the bank details for the first office of combo box
						while (rs.next()) {
							ps2 = con
									.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('NC','CL','RD')");
							ps2.setInt(1, rs.getInt("ACCOUNTING_FOR_OFFICE_ID"));
							rs2 = ps2.executeQuery();
							if (rs2.next()) {
								out.println("<option value="
										+ rs.getInt("ACCOUNTING_FOR_OFFICE_ID")
										+ ">" + rs2.getString("OFFICE_NAME")
										+ "</option>");
							}
						}
					}

				} catch (Exception e) {
					System.out.println("Exception in Office combo..." + e);
				} finally {
					rs.close();
					ps.close();
				}
			%>
		</select></div>		</td>
	</tr>
            <tr align="left" class="table">
           <td class="table">
              <div align="left">
                  From Date &amp; To Date<strong>:</strong><font color="#ff2121">*</font>
                 </div>
              </td>
              <td>
              <div align="left">       
             <input type="text" name="txtFrom_date" id="txtFrom_date"  tabindex="6"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frminterbankReport.txtFrom_date);"
                         alt="Show Calendar"></img>
           
                    <input type="text" name="txtTo_date" id="txtTo_date"  tabindex="7"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frminterbankReport.txtTo_date);"
                         alt="Show Calendar"></img>
                         </div>
                         </td>
        </tr>
        <tr class="table">
		<td>
		<div align="left">To Bank Account Number <font color="#ff2121">*</font>		</div>		</td>
		<td>
		<div align="left"><!--  <input type="text" name="txtBankAccountNo"  onkeypress="return numbersonly(event)" onchange="doFunction('load_bank_deatils','CR');"
                           id="txtBankAccountNo" maxlength="15"  size="15" />-->
		<select name="txtBankAccountNo" id="txtBankAccountNo" onchange="">
			<option value="">--Select Bank Account Number--</option>
			<%
				ps = con.prepareStatement("SELECT b.BANK_AC_NO, a.BANK_SHORT_NAME || '-' || b.BANK_AC_TYPE_ID || '-' || b.BANK_AC_NO AS BANK_AC_NO_DISPLAY	FROM FAS_MST_BANKS a, FAS_MST_BANK_BALANCE b WHERE b.BANK_ID =a.BANK_ID	AND b.ACCOUNTING_UNIT_ID=5 ORDER BY a.BANK_SHORT_NAME");
				
				rs= ps.executeQuery();
				while (rs.next()) {
					out.println("<option value=" + rs.getLong("BANK_AC_NO")
							+ ">" + rs.getString("BANK_AC_NO_DISPLAY")
							+ "</OPTION>");
					System.out.println("bank_ac_no..."
							+ rs.getLong("BANK_AC_NO"));
				}
				rs.close();
				ps.close();
			%>
		</select></div>		</td>
	</tr>
        <!--
         <tr>
          <td align="left">Select Specific Account Head Code:<font color="#ff2121">*</font></td>
          <td align="left">
                            <input type=radio id="SpecificAHC" name="SpecificAHC" value="All" checked onclick="AccHead(this.value)"> All
                            <input type=radio id="SpecificAHC" name="SpecificAHC" value="Specific" onclick="AccHead(this.value)">Specific
                            <div id="acchead" name="acchead" style="display:none">
                            <input type="text" name="txtAcc_HeadCode" id="txtAcc_HeadCode" size=10 maxlength="8" onkeypress="return numbersonly(event)">
                            <img src="../../../../../../images/c-lovi.gif" alt="AccountHeadList" onclick="AccHeadpopup();"  height="24" width="24"></img>
                            </div>
                            
          </td>
          </tr>
        -->
        <tr>
                        <td align="left">
                            Report Option:
                        </td>
                        <td colspan="3" align="left">
                            <input type=radio name=txtoption id=txtoption value="PDF" checked>PDF
                            <input type=radio name=txtoption id=txtoption value="EXCEL">Excel
                            <input type=radio name=txtoption id=txtoption value="HTML">HTML
                        </td>
                        
                    </tr>
</table>
          </div>
          <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
  
      <tr class="tdH1">
      <td>
          <div align="center">
          <input type="button"  value="GO" id="sum" name="sum" onclick="printFunc('<%=s%>');" >
          <input type="button" name="butCan" id="butCan" value="CANCEL" onClick="refresh();" />
          <input type="button" id="cmdcancel" name="cancel" value="Exit" onclick="closeWindow()">
       </div>
      </td>
      </tr>
      
      </table>
      </form>
  </body>
</html>