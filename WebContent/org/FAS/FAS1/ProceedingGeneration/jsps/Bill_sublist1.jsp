<%-- <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<%@page import="java.net.URLDecoder"%><html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Bill SubList</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
          <script type="text/javascript">
          function exit()
          {
          self.close();
          }
          </script>
  </head>
  <body class="table"><form name="frmBankPay_FinalBill" method="POST">
      <%
      	Connection con = null;
      	ResultSet rs = null;
      	PreparedStatement ps = null;
      	ResultSet results = null;
      	ResultSet results1 = null;
      	ResultSet results2 = null;
      	try {

      		ResourceBundle rs1 = ResourceBundle
      				.getBundle("Servlets.Security.servlets.Config");
      		String ConnectionString = "";

      		String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
      		String strdsn = rs1.getString("Config.DSN");
      		String strhostname = rs1.getString("Config.HOST_NAME");
      		String strportno = rs1.getString("Config.PORT_NUMBER");
      		String strsid = rs1.getString("Config.SID");
      		String strdbusername = rs1.getString("Config.USER_NAME");
      		String strdbpassword = rs1.getString("Config.PASSWORD");

      		ConnectionString = strdsn.trim() + "@" + strhostname.trim()
      				+ ":" + strportno.trim() + ":" + strsid.trim();

      		Class.forName(strDriver.trim());
      		con = DriverManager.getConnection(ConnectionString,
      				strdbusername.trim(), strdbpassword.trim());
      	} catch (Exception e) {
      		System.out.println("Exception in connection...." + e);
      	}
      	int cmbAcc_UnitCode = 0, cmbOffice_code = 0, yr = 0, mon = 0, bilno = 0, sancno = 0;
      	String SancWith = "", sl_gp = "";
      	System.out.println("jsp *************************:");
      	try {
      		cmbAcc_UnitCode = Integer.parseInt(request
      				.getParameter("cmbAcc_UnitCode"));
      	} catch (Exception e) {
      		System.out.println("Exception in getting req:" + e);
      	}
      	try {
      		cmbOffice_code = Integer.parseInt(request
      				.getParameter("cmbOffice_code"));
      	} catch (Exception e) {
      		System.out.println("Exception in getting req:" + e);
      	}
      	try {
      		yr = Integer.parseInt(request.getParameter("yr"));
      	} catch (Exception e) {
      		System.out.println("Exception in getting req:" + e);
      	}
      	try {
      		mon = Integer.parseInt(request.getParameter("mon"));
      	} catch (Exception e) {
      		System.out.println("Exception in getting req:" + e);
      	}
      	
      	try {
      		sancno = Integer.parseInt(request.getParameter("sancno"));
      	} catch (Exception e) {
      		System.out.println("Exception in getting req:" + e);
      	}
      	System.out.println("sancno:::" + sancno);
      	
      	
      %>
   <%
   	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
   	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
   	response.setDateHeader("Expires", 0); //prevent caching at the proxy server
   %>
      <table cellspacing="3" cellpadding="2"  width="103%">
        <tr class="tdH">
          <td>
            <div align="center">
              <strong> Details</strong>
            </div>
          </td>
        </tr>
      </table>
      <table id="mytable" align="center" cellspacing="2" cellpadding="2"
             border="1" width="106%">
        <tr class="tdH">
          <th ><font size="2">Ptype</font></th>
          <th style="width:9%"><font size="2">Pname</font></th>
          <th><font size="2">Pcode</font></th>
          <th><font size="2">Ename</font></th>
          <th style="width:2%"><font size="2">Designation</font></th>
          <th><font size="2">NoHr</font></th>
          <th style="width:2%"><font size="2">HRAmt</font></th>
          <th style="width:2%"><font size="2">VRAtt</font></th>
          <th style="width:2%"><font size="2">NoVR</font></th>
          <th><font size="2">RNo</font></th>
          <th><font size="2">SanAut</font></th>
         
          <th><font size="2">Amt</font></th>
          <th style="width:2%"><font size="2">HeadCode</font></th>
          <th style="width:2%"><font size="2">BudPro</font></font></th>
          <th><font size="2">BSpent</font></th>
          <th style="width:2%"><font size="2">BAmt</font></th>
          <th><font size="2">Pay UnitId</font></th>
          <th style="width:2%"><font size="2">Remarks</font></th>
         <!-- <th>UId</th> -->
         
        </tr>
        <tbody id="tbody" class="table">
       
          			
          	
          			<%try{
          				ps = con.prepareStatement("SELECT DISTINCT u.accounting_unit_name, " +
          			        			 "  O.Office_Name, " +
          			        			 "  a.SANCTION_PROCEEDING_NO, " +
          			        			 "  TO_CHAR(A.Sanction_Proceeding_Date,'dd/mm/yyyy') AS Sanctiondate, " +
          			        			 "  f.bill_major_type_desc, " +
          			        			 "  F1.Bill_Minor_Type_Desc, " +
          			        			 "  f2.bill_sub_type_desc, " +
          			        			 "  A.Payee_Type as p_type, " +
          			        			 " case when A.Payee_Type='U' then 'Priviledged User' "+
          			        			" when A.Payee_Type='E' then 'Employee' "+
          			        			" when A.Payee_Type='P' then 'Pensioner' "+
          			        			"  end as Payee_Type,  "+        			        					 
          			        			 "  a.Payee_Code, " +
          			        			 "  H.Employee_Name, " +
          			        			 "  h.employee_name " +
          			        			 "  ||' ' " +
          			        			 "  ||h.employee_initial AS emp_name, " +
          			        			 "  a.payee_name, " +
          			        			 "  h1.designation, " +
          			        			 "  a.NO_OF_HR, " +
          			        			 "  TO_CHAR(a.HR_FROM_DATE,'dd/mm/yyyy') AS hrformate, " +
          			        			 "  TO_CHAR(a.HR_TO_DATE,'dd/mm/yyyy')   AS hrformate, " +
          			        			 "  a.HR_AMOUNT, " +
          			        			 "  a.VOU_ATTACHED, " +
          			        			 "  a.NO_OF_VOU, " +
          			        			 "  a.REF_NO, " +
          			        			 "  TO_CHAR(A.Ref_Date,'dd/mm/yyyy') AS Hrformate, " +
          			        			 "  a.sanction_authority, " +
          			        			 "  aut.designation AS san_authotityDesc, " +
          			        			 "  a.SANCTIONED_BY, " +
          			        			 "  A.Total_Sanction_Amount, " +
          			        			 "  A.Account_Head_Code, " +
          			        			 "  A.Bud_Provided, " +
          			        			 "  A.Bud_Spent, " +
          			        			 "  Bal_Amt, " +
          			        			 "  a.PAYMENT_TO_BE_MADE_UNIT_ID, " +
          			        			 "  a.REMARKS, " +
          			        			 "  a.UPDATED_BY_USERID, " +
          			        			 "  TO_CHAR(a.UPDATED_DATE,'dd/mm/yyyy') AS hrformate, " +
          			        			 "  a.HR_NOTE_NO, " +
          			        			 "  TO_CHAR(A.Updated_Date,'dd/mm/yyyy') AS Hrformate " +
          			        			 "FROM Fas_Hr_Sanc_Proc_Mst A, " +
          			        			 "  Fas_Mst_Acct_Units U, " +
          			        			 "  Com_Mst_Offices O, " +
          			        			 "  Hrm_Mst_Employees H, " +
          			        			 "  HRM_MST_DESIGNATIONS H1, " +
          			        			 "  HRM_MST_DESIGNATIONS Aut, " +
          			        			 "  FAS_BILL_MAJOR_TYPES f, " +
          			        			 "  FAS_BILL_MINOR_TYPES_MST f1, " +
          			        			 "  FAS_BILL_SUB_TYPES f2, " +
          			        			 "  HRM_EMP_CURRENT_POSTING post " +
          			        			 "WHERE a.accounting_unit_id     =?" +
          			        			 "AND A.Accounting_For_Office_Id =? " +
          			        			 "AND A.Cashbook_Year            =? " +
          			        			 "AND a.cashbook_month           =? " +
          			        			 "and a.SANCTION_PROCEEDING_NO=? "+
          			        			 "AND U.Accounting_Unit_Id       =A.Accounting_Unit_Id " +
          			        			 "AND O.Office_Id                =A.Accounting_For_Office_Id " +
          			        			 "AND H.Employee_Id              =A.Payee_Code " +
          			        			 "AND aut.Designation_Id         =A.Sanction_Authority " +
          			        			 "AND F.Bill_Major_Type_Code     =A.Bill_Major_Type_Code " +
          			        			 "AND F1.Bill_Major_Type_Code    =A.Bill_Major_Type_Code " +
          			        			 "AND F1.Bill_Minor_Type_Code    =A.Bill_Minor_Type_Code " +
          			        			 "AND F2.Bill_Major_Type_Code    =A.Bill_Major_Type_Code " +
          			        			 "AND F2.Bill_Minor_Type_Code    =A.Bill_Minor_Type_Code " +
          			        			 "AND f2.bill_sub_type_code      =a.bill_sub_type_code " +
          			        			 "AND Post.Employee_Id           =A.Payee_Code " +
          			        			 "AND post.designation_id        =h1.designation_id"
          			);
          				  ps.setInt(1, cmbAcc_UnitCode);
          	        	 ps.setInt(2, cmbOffice_code);
          	        	 ps.setInt(3, yr);
          	        	 
          	        	 ps.setInt(4, mon);
          	      	 ps.setInt(5, sancno);
          				rs = ps.executeQuery();

          				while (rs.next()) {
          					
          					
          					
          			out.println("<td align='left'>"
          					+ rs.getString("Payee_Type") + "</td>");
          			
          			
          			out.println("<td align='left'>"
          					+ rs.getString("payee_name") + "</td>");
          			
         
          				out.println("<td align='left'>" + rs.getInt("Payee_Code")
              					+ "</td>");
          				
          			
          			out.println("<td align='left'>"+rs.getString("Employee_Name")+"</td>");
          			 
          			
          				out.println("<td align='left'>"+ rs.getString("designation") + "</td>");
          				
          				out.println("<td align='left'>"	+ rs.getInt("NO_OF_HR")
          						+ "</td>");
          				out.println("<td align='right'>"
          						+ rs.getFloat("HR_AMOUNT")
          						+ "</td>");
          				out.println("<td align='left'>"
          						+ rs.getString("VOU_ATTACHED")
          						+ "</td>");
          				out.println("<td align='left'>"
          						+ rs.getInt("NO_OF_VOU")
          						+ "</td>");
          				/* out.println("<td align='left'>"
          						+ rs.getInt("NO_OF_VOU")
          						+ "</td>");
          		 */
          				out.println("<td align='left'>"
          						+ rs.getInt("REF_NO") + "</td>");
          				out.println("<td align='left'>"
          						+ rs.getString("san_authotityDesc")
          						+ "</td>");
          				//	 <th style="display:none" id="dedid">Deducted Amount</th>
          				out.println("<td align='right'>"
          						+ rs.getFloat("Total_Sanction_Amount")
          						+ "</td>");
          				
          				out.println("<td align='left'>"
          						+ rs.getInt("Account_Head_Code")
          						+ "</td>");
          				
          				out.println("<td align='right'>"
          						+ rs.getFloat("Bud_Provided")
          						+ "</td>");
          				
          				
          				out.println("<td align='right'>"
          						+ rs.getFloat("Bud_Spent")
          						+ "</td>");
          				
          				out.println("<td align='right'>"
          						+ rs.getFloat("Bal_Amt")
          						+ "</td>");
          				
          				out.println("<td align='left'>"
          						+ rs.getInt("PAYMENT_TO_BE_MADE_UNIT_ID")
          						+ "</td>");
          				

          				out.println("<td align='left'>"
          						+ rs.getString("REMARKS")
          						+ "</td>");

          				/* out.println("<td align='left'>"
          						+ rs.getString("UPDATED_BY_USERID")
          						+ "</td></tr>"); */
          				
          			}
          			}
          			catch(Exception e){
          				System.out.println(e);
          			}
          		

          
          %> 
        </tbody>
      </table>
      <table align="center" cellspacing="2" cellpadding="2" border="1"
             width="103%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="button" id="cmdcancel" name="cancel" value="Exit"
                     onclick="exit()"></input>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html> --%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<%@page import="java.net.URLDecoder"%><html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Bill SubList</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
          <script type="text/javascript">
          function exit()
          {
          self.close();
          }
          </script>
  </head>
  <body class="table"><form name="frmBankPay_FinalBill" method="POST">
      <%
          Connection con = null;
          ResultSet rs = null;
          PreparedStatement ps = null;
          ResultSet results = null;
          ResultSet results1 = null;
          ResultSet results2 = null;
          try {

              ResourceBundle rs1 = ResourceBundle
                      .getBundle("Servlets.Security.servlets.Config");
              String ConnectionString = "";

              String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
              String strdsn = rs1.getString("Config.DSN");
              String strhostname = rs1.getString("Config.HOST_NAME");
              String strportno = rs1.getString("Config.PORT_NUMBER");
              String strsid = rs1.getString("Config.SID");
              String strdbusername = rs1.getString("Config.USER_NAME");
              String strdbpassword = rs1.getString("Config.PASSWORD");

              //ConnectionString = strdsn.trim() + "@" + strhostname.trim()
                //      + ":" + strportno.trim() + ":" + strsid.trim();
				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
              Class.forName(strDriver.trim());
              con = DriverManager.getConnection(ConnectionString,
                      strdbusername.trim(), strdbpassword.trim());
          } catch (Exception e) {
              System.out.println("Exception in connection...." + e);
          }
          int cmbAcc_UnitCode = 0, cmbOffice_code = 0, yr = 0, mon = 0, bilno = 0, sancno = 0;
          String SancWith = "", sl_gp = "";
          System.out.println("jsp *************************:");
          try {
              cmbAcc_UnitCode = Integer.parseInt(request
                      .getParameter("cmbAcc_UnitCode"));
          } catch (Exception e) {
              System.out.println("Exception in getting req:" + e);
          }
          try {
              cmbOffice_code = Integer.parseInt(request
                      .getParameter("cmbOffice_code"));
          } catch (Exception e) {
              System.out.println("Exception in getting req:" + e);
          }
          try {
              yr = Integer.parseInt(request.getParameter("yr"));
          } catch (Exception e) {
              System.out.println("Exception in getting req:" + e);
          }
          try {
              mon = Integer.parseInt(request.getParameter("mon"));
          } catch (Exception e) {
              System.out.println("Exception in getting req:" + e);
          }
          
          try {
              sancno = Integer.parseInt(request.getParameter("sancno"));
          } catch (Exception e) {
              System.out.println("Exception in getting req:" + e);
          }
          System.out.println("sancno:::" + sancno);
          
          
      %>
   <%-- <%
       response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
       response.setHeader("Pragma", "no-cache"); //HTTP 1.0
       response.setDateHeader("Expires", 0); //prevent caching at the proxy server
   %> --%>
      <table cellspacing="3" cellpadding="2"  width="103%">
        <tr class="tdH">
          <td>
            <div align="center">
              <strong> Details</strong>
            </div>
          </td>
        </tr>
      </table>
      <table id="mytable" align="center" cellspacing="2" cellpadding="2"
             border="1" width="106%">
        <tr class="tdH">
          <th ><font size="2">PAYEE DETAILS</font></th>
          <th><font size="2">HR_AMT </font></th>
          <th><font size="2">NO_OF_VOUCHERS </font></th>
          <th><font size="2">REF_NO & DATE</font></th>
          <th style="width:2%"><font size="2">SAN_AUTHORITY </font></th>
          <th><font size="2">SANCTIONED_BY</font></th>
          <th style="width:2%"><font size="2">TOTAL_SAN_AMOUNT</font></th>
          <th style="width:2%"><font size="2">ACCOUNT_HEAD_CODE </font></th>
          <th style="width:2%"><font size="2">BAL_AMT </font></th>
          <th><font size="2">PAYMENT OFFICE</font></th>
          <th><font size="2">REMARKS</font></th>
          
         
        </tr>
        <tbody id="tbody" class="table">
       
                      
              
                      <%
                      System.out.print("dfdgg");
                      
                      try{
                    	  String Qry="SELECT DISTINCT U.Accounting_Unit_Name, " +
                                  "  O.Office_Name, " +
                                  "  a.SANCTION_PROCEEDING_NO, " +
                                  "  TO_CHAR(A.Sanction_Proceeding_Date,'dd/mm/yyyy') AS Sanctiondate, " +
                                  "  f.bill_major_type_desc, " +
                                  "  F1.Bill_Minor_Type_Desc, " +
                                  "  f2.bill_sub_type_desc, " +
                                 
                                  
                                  "  a.Payee_Code " +
                                  "  ||' ' " +
                                  "  ||a.payee_name AS PayDetails, " +
                                		  "  A.Payee_Type as p_type, " +
           			        			 " case when A.Payee_Type='U' then 'Priviledged User' "+
           			        			" when A.Payee_Type='E' then 'Employee' "+
           			        			" when A.Payee_Type='P' then 'Pensioner' "+
           			        			"  end as PayeeDesc,  "+   
                                  "  H.Employee_Name, " +
                                  "  h.employee_name " +
                                  "  ||' ' " +
                                  "  ||h.employee_initial AS emp_name, " +
                                  "  h1.designation, " +
                                  "  a.NO_OF_HR, " +
                                  "  TO_CHAR(a.HR_FROM_DATE,'dd/mm/yyyy') AS hrformate_from, " +
                                  "  TO_CHAR(a.HR_TO_DATE,'dd/mm/yyyy')   AS hrformate_to, " +
                                  "  a.HR_AMOUNT, " +
                                  "  a.VOU_ATTACHED, " +
                                  "  a.NO_OF_VOU, " +
                                  "  a.REF_NO " +
                                  "  ||' ' " +
                                  "  ||TO_CHAR(A.Ref_Date,'dd/mm/yyyy') AS REFNO_DATE, " +
                                  "  A.Sanction_Authority, " +
                                  "  aut.designation AS sAutDesc, " +
                                  "  a.SANCTIONED_BY, " +
                                  "  A.Total_Sanction_Amount, " +
                                  "  A.Account_Head_Code, " +
                                  "  A.Bud_Provided, " +
                                  "  A.Bud_Spent, " +
                                  "  Bal_Amt, " +
                                  "  a.PAYMENT_TO_BE_MADE_UNIT_ID, " +
                                  "  a.REMARKS, " +                         
                                  "  a.HR_NOTE_NO " +                                 
                                  " FROM Fas_Hr_Sanc_Proc_Mst A, " +
                                  "  Fas_Mst_Acct_Units U, " +
                                  "  Com_Mst_Offices O, " +
                                  "  Hrm_Mst_Employees H, " +
                                  "  HRM_MST_DESIGNATIONS H1, " +
                                  "  HRM_MST_DESIGNATIONS Aut, " +
                                  "  FAS_BILL_MAJOR_TYPES f, " +
                                  "  FAS_BILL_MINOR_TYPES_MST f1, " +
                                  "  FAS_BILL_SUB_TYPES f2, " +
                                  "  Hrm_Emp_Current_Posting Post " +
                                  " WHERE A.Accounting_Unit_Id     =? " +
                                  " AND A.Accounting_For_Office_Id =? " +
                                  " AND A.Cashbook_Year            =? " +
                                  " AND A.Cashbook_Month           =? " +
                                  "AND a.SANCTION_PROCEEDING_NO   =? " +
                                  "AND U.Accounting_Unit_Id       =A.Accounting_Unit_Id " +
                                  "AND O.Office_Id                =A.Accounting_For_Office_Id " +
                                  "AND H.Employee_Id              =A.Payee_Code " +
                                  "AND aut.Designation_Id         =A.Sanction_Authority " +
                                  "AND F.Bill_Major_Type_Code     =A.Bill_Major_Type_Code " +
                                  "AND F1.Bill_Major_Type_Code    =A.Bill_Major_Type_Code " +
                                  "AND F1.Bill_Minor_Type_Code    =A.Bill_Minor_Type_Code " +
                                  "AND F2.Bill_Major_Type_Code    =A.Bill_Major_Type_Code " +
                                  "AND F2.Bill_Minor_Type_Code    =A.Bill_Minor_Type_Code " +
                                  "AND f2.bill_sub_type_code      =a.bill_sub_type_code " +
                                  "AND Post.Employee_Id           =A.Payee_Code " +
                                  "AND post.designation_id        =h1.designation_id";
                    	 // System.out.println("Qry >> "+Qry);
                          ps = con.prepareStatement(Qry);
                            ps.setInt(1, cmbAcc_UnitCode);
                           ps.setInt(2, cmbOffice_code);
                           ps.setInt(3, yr);
                           
                           ps.setInt(4, mon);
                         ps.setInt(5, sancno);
                        System.out.println("werwerwerwerr");
                          rs = ps.executeQuery();

                          while (rs.next()) {
                              /* System.out.println("werwerwerwerr");
                              System.out.println(rs.getString("PayDetails"));
                              System.out.println(rs.getFloat("HR_AMOUNT"));
                              System.out.println(rs.getInt("NO_OF_VOU"));
                              System.out.println(rs.getString("REFNO_DATE"));
                              System.out.println(rs.getString("sAutDesc"));
                              System.out.println(rs.getString("SANCTIONED_BY"));
                              System.out.println(rs.getFloat("Total_Sanction_Amount"));
                              System.out.println(rs.getInt("Account_Head_Code"));
                              System.out.println( rs.getFloat("Bal_Amt"));
                              System.out.println( rs.getString("REMARKS"));
                              //System.out.println(rs.getString("PayDetails"));
                               */
                      out.println("<td align='left'>"
                              +rs.getString("PayeeDesc")+" "+ rs.getString("PayDetails") + "</td>");
                      
                      
                                  out.println("<td align='right'>"
                                          + rs.getFloat("HR_AMOUNT")
                                          + "</td>");
                                  out.println("<td align='left'>"
                                          + rs.getInt("NO_OF_VOU")
                                          + "</td>");
                                  
                                  out.println("<td align='left'>"
                                          + rs.getString("REFNO_DATE") + "</td>");
                                  
                                  out.println("<td align='left'>"
                                          + rs.getString("sAutDesc")
                                          + "</td>");
                                  
                                  out.println("<td align='left'>"
                                          + rs.getInt("SANCTIONED_BY")
                                          + "</td>");
                                  
                                  out.println("<td align='right'>"
                                          + rs.getFloat("Total_Sanction_Amount")
                                          + "</td>");
                                  
                                  out.println("<td align='left'>"
                                          + rs.getInt("Account_Head_Code")
                                          + "</td>");
                                  
                                  out.println("<td align='right'>"
                                          + rs.getFloat("Bal_Amt")
                                          + "</td>");
                                  
                                  out.println("<td align='right'>"
                                          + rs.getInt("PAYMENT_TO_BE_MADE_UNIT_ID")
                                          + "</td>");
                                  

                                  out.println("<td align='left'>"
                                          + rs.getString("REMARKS")
                                          + "</td>");

                      
                          
                      }
                      }
                      catch(Exception e){
                    	 out.println("testing");
                          e.printStackTrace();
                      }
                  

          
          %> 
        </tbody>
      </table>
      <table align="center" cellspacing="2" cellpadding="2" border="1"
             width="103%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="button" id="cmdcancel" name="cancel" value="Exit"
                     onclick="exit()"></input>
            </div>
          </td>