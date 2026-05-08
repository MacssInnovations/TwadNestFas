<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache">
    <title>List of Buildings</title>
    
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>


       <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
       <script type="text/javascript" src="../scripts/Building_Details_ListAll.js"></script>
	   <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
	   <script type="text/javascript" language="javascript">
	
		   function btncancel()
		   {
		   	self.close();
		   }
		
		
		   function Minimize() 
		   {
		   	window.resizeTo(0,0);
		   	window.screenX = screen.width;
		   	window.screenY = screen.height;
		   	opener.window.focus();
		   }
		
		
		   function Edit(rid)
		   {
		   	var accounting_unit_id=0,accounting_unit_office_id=0,financial_year="",asset_code=0;
		   	r=document.getElementById(rid);
		   	rcells=r.cells;

/*		   	
		   	alert(rcells.item(1).firstChild.nodeValue);
		   	alert(rcells.item(2).firstChild.nodeValue);
		   	alert(rcells.item(3).firstChild.nodeValue);
		   	alert(rcells.item(4).firstChild.nodeValue);
*/
		   	
		   	accounting_unit_id=rcells.item(1).firstChild.nodeValue;
		   	accounting_unit_office_id=rcells.item(2).firstChild.nodeValue;
		   	financial_year=rcells.item(3).firstChild.nodeValue;
		   	asset_code=rcells.item(4).firstChild.nodeValue;
		   	Minimize();
		   	opener.ParentBuild(accounting_unit_id,accounting_unit_office_id,financial_year,asset_code);        
		   }
		   
	  </script>
  </head>
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');" bgcolor="rgb(255,255,225)">
 
 
   <%
  
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    
    ResultSet rs2=null;
    ResultSet rsYr=null;
  	ResultSet results=null;
    PreparedStatement ps2=null;
    PreparedStatement psYr=null;

    try
    {
  
            ResourceBundle rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rb.getString("Config.DSN");
            String strhostname=rb.getString("Config.HOST_NAME");
            String strportno=rb.getString("Config.PORT_NUMBER");
            String strsid=rb.getString("Config.SID");
            String strdbusername=rb.getString("Config.USER_NAME");
            String strdbpassword=rb.getString("Config.PASSWORD");

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
 	HttpSession session = request.getSession(false);
 	UserProfile empProfile = (UserProfile) session
 			.getAttribute("UserProfile");

 	System.out.println("user id::" + empProfile.getEmployeeId());
 	int empid = empProfile.getEmployeeId();
 	//int empid=1758;
 	int oid = 0;
 	String oname = "";

 	try {

 		ps = con
 				.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
 		ps.setInt(1, empid);
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

 	} catch (Exception e) {
 		System.out.println(e);
 	}
  %>
  <%
  	int accid=0;
  %>  
                                              
                        
                        
 
        
  <form action="" name="frmListBuildings">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              TWAD BOARD-INTEGRATED ONLINE SYSTEM -FINANACIAL ACCOUNTING SYSTEM
            </div></td>
        </tr>
        <tr class="table">
          <td>
            <div align="center">
              <b>List of Buildings</b>
            </div>
          </td>
        </tr>
    </table>
    
    
<div align="center">
<table cellspacing="1" cellpadding="2" border="1" width="100%">

	<tr class="table">
		<td>
		<div align="left">Accounting Unit Code <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left">
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
			id="cmbOffice_code" tabindex="2" onchange="byUnitAndOfficeChange();">

			<%
				System.out.println("here");
				System.out.println(oid + "  " + oname);
				try {
					if (oid == 5000) {
						out.println("<option value=" + oid + ">" + "HEAD OFFICE"
								+ "</option>");
					} else {
						ps = con.prepareStatement("select ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID  from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_UNIT_ID=? order by ACCOUNTING_FOR_OFFICE_ID desc");
						ps.setInt(1, unitid);
						rs = ps.executeQuery();
						while (rs.next()) {
							ps2 = con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
							ps2.setInt(1, rs.getInt("ACCOUNTING_FOR_OFFICE_ID"));
							rs2 = ps2.executeQuery();
							if (rs2.next())
								out.println("<option value="
										+ rs.getInt("ACCOUNTING_FOR_OFFICE_ID")
										+ ">" + rs2.getString("OFFICE_NAME")
										+ "</option>");
						}
					}

				} catch (Exception e) {
					System.out.println("Exception in Office combo..." + e);
				} finally {
					rs.close();
					ps.close();
				}
			%>
		</select></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Financial Year <font color="#ff2121">*</font></div>
		</td>
		<td>
			<div align="left"> 
				<select name="cmbFinyr" id="cmbFinyr" onchange="clearAllGrids('tblBuild'); callServer('LoadBuild');">
					<option value="">-- Select Financial Year --</option>
					<%
						String finYrQry = "select distinct FINANCIAL_YEAR from CASH_BOOK_CONTROL order by FINANCIAL_YEAR ";
						System.out.println(finYrQry);
						psYr = con.prepareStatement(finYrQry);
						rsYr = psYr.executeQuery();
						while (rsYr.next()) 
						{
							out.println("<option value=" + rsYr.getString("FINANCIAL_YEAR") + 
										">" + rsYr.getString("FINANCIAL_YEAR") + "</option>");
						}
					%>
				</select>
			</div>
		</td>
	</tr>

</table>
</div>

    
     
     
      
      <table cellspacing="2" cellpadding="3" border="1" width="100%">

	       <tr class="tdH">
		      	<th>Asset Code</th>
		        <th>Building Name</th>
		        <th>Floors</th>
		 		<th>Foundation Type</th>
		 		<th>Civil Cost</th>
		 		<th>Book Value</th>
		 		<th>A/c Head</th>
		 		<th>Floor Details</th>
	       </tr>
	
	       <tbody id="tblBuild" class="table" align="left">
	       </tbody>

       </table>






      <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">             
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="button" id="cmdcancel" name="cancel" value="Exit"
                     onclick=" self.close();"></input>
            </div>
          </td>
        </tr>
      </table> 
      
      
    </form>
  </body>
</html>