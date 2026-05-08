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
      				//+ ":" + strportno.trim() + ":" + strsid.trim();
      		
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
      		bilno = Integer.parseInt(request.getParameter("bilno"));
      	} catch (Exception e) {
      		System.out.println("Exception in getting req:" + e);
      	}
      	try {
      		sancno = Integer.parseInt(request.getParameter("sancno"));
      	} catch (Exception e) {
      		System.out.println("Exception in getting req:" + e);
      	}
      	System.out.println("sancno:::" + sancno);
      	try {
      		SancWith = request.getParameter("SancWith");
      	} catch (Exception e) {
      		System.out.println("Exception in getting req:" + e);
      	}
      	System.out.println("SancWith:::" + SancWith);
      	try {
      		//sl_gp = request.getParameter("sl_gp");
      	sl_gp=URLDecoder.decode(request.getParameter("sl_gp"));
      	System.out.println("sl_gp:::" + sl_gp);
      	} catch (Exception e) {
      		System.out.println("Exception in getting sl_gp:" + e);
      	}
      	
      %>
   <%
   	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
   	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
   	response.setDateHeader("Expires", 0); //prevent caching at the proxy server
   %>
      <table cellspacing="3" cellpadding="2"  width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Voucher Details</strong>
            </div>
          </td>
        </tr>
      </table>
      <table id="mytable" align="center" cellspacing="3" cellpadding="2"
             border="1" width="100%">
        <tr class="tdH">
          <th> Sl No</th>
          <th>Account Head Code</th>
          <th>Bill Major Type</th>
          <th>Bill Minor Type</th>
          <th>Bill Sub Type</th>
          <th>Payee Type</th>
          <th>Payee Desc</th><%
          
          if (SancWith.equalsIgnoreCase("Y")||SancWith.equalsIgnoreCase("GPF"))
        	  	{ %>
        	  <th>Amount</th>
        	<%  } %>
        	
          <th>Budget Provision</th>
          <th>Budget So Far Spent</th>
           <%
           	System.out.println("sl_gp:" + sl_gp);
           	if (sl_gp.equals("SLS")) {
           		System.out.println("jjjj");
           	} else {
           		out.println("<th>Deducted Amount</th>");
           	}
           %>
        </tr>
        <tbody id="tbody" class="table">
          <%
          	ResultSet rs2 = null, rs3 = null, rs4 = null;
          	PreparedStatement ps2 = null, ps3 = null, ps4 = null;
          	try {
          		String sql = "";
          		if (SancWith.equalsIgnoreCase("Y")||SancWith.equalsIgnoreCase("GPF")) {
          			/*
          			sql = "SELECT ACCOUNT_HEAD_CODE, "
          					+ " t.BILL_MAJOR_TYPE,t.sl_no, "
          					+ " (select BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES m WHERE status='L' and m.BILL_MAJOR_TYPE_CODE=t.BILL_MAJOR_TYPE)as majordesc, "
          					+ " t.BILL_MINOR_TYPE_CODE, "
          					+ " (select BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST n where n.BILL_MAJOR_TYPE_CODE=t.BILL_MAJOR_TYPE "
          					+ " and n.bill_minor_type_code=t.BILL_MINOR_TYPE_CODE and status='L')as minordesc, "
          					+ "  t.BILL_SUB_TYPE_CODE, "
          					+ "  (select BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES o "
          					+ "  where BILL_MAJOR_TYPE_CODE=t.BILL_MAJOR_TYPE and BILL_MINOR_TYPE_CODE=t.BILL_MINOR_TYPE_CODE "
          					+ "  and t.BILL_SUB_TYPE_CODE=o.bill_sub_type_code and status='L')as subdesc, "
          					+ "  BUDGET_PROVISION, "+
          				     " t.PAYEE_TYPE_CODE,  (select M.PAYEE_TYPE_DESC from FAS_PAYEE_TYPES_MST m where M.PAYEE_TYPE_CODE=t.PAYEE_TYPE_CODE) as pay_type," +
          	            	 "  DECODE( " +
          	            	 "  (SELECT v.sl_codename FROM SL_TYPE_CODE_NAME_VIEW v WHERE SL_TYPE=t.PAYEE_TYPE_CODE " +
          	            	 "  AND SL_CODE                                                      =t.payable_to " +
          	            	 "  ),NULL,'-', " +
          	            	 "  (SELECT v.sl_codename " +
          	            	 "  FROM SL_TYPE_CODE_NAME_VIEW v " +
          	            	 "  WHERE v.SL_TYPE=t.PAYEE_TYPE_CODE " +
          	            	 "  AND v.SL_CODE  =t.payable_to " +
          	            	 "  ))AS paydesc, " 
          					+ "  BUDGET_SO_FAR_SPENT,amount, "
          					+ " decode(DEDUCTED_AMOUNT,null,'-',deducted_amount) as DEDUCTED_AMOUNT  "
          					+ " FROM fas_bill_register_transaction t "
          					+ " WHERE ACCOUNTING_UNIT_ID= " + cmbAcc_UnitCode
          					+ " AND CASHBOOK_YEAR       = " + yr
          					+ " AND CASHBOOK_MONTH      = " + mon
          					+ " and (CASHBOOK_YEAR < 2015 or( CASHBOOK_YEAR = 2015 and CASHBOOK_MONTH < 4 ))  AND BILL_NO             =" + bilno+
          					" union all SELECT ACCOUNT_HEAD_CODE, "
          					+ " t.BILL_MAJOR_TYPE,t.sl_no, "
          					+ " (select BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES m WHERE status='L' and m.BILL_MAJOR_TYPE_CODE=t.BILL_MAJOR_TYPE)as majordesc, "
          					+ " t.BILL_MINOR_TYPE_CODE, "
          					+ " (select BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST n where n.BILL_MAJOR_TYPE_CODE=t.BILL_MAJOR_TYPE "
          					+ " and n.bill_minor_type_code=t.BILL_MINOR_TYPE_CODE and status='L')as minordesc, "
          					+ "  t.BILL_SUB_TYPE_CODE, "
          					+ "  (select BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES o "
          					+ "  where BILL_MAJOR_TYPE_CODE=t.BILL_MAJOR_TYPE and BILL_MINOR_TYPE_CODE=t.BILL_MINOR_TYPE_CODE "
          					+ "  and t.BILL_SUB_TYPE_CODE=o.bill_sub_type_code and status='L')as subdesc, "
          					+ "  BUDGET_PROVISION, "+
          				     " t.PAYEE_TYPE_CODE,  (select M.PAYEE_TYPE_DESC from FAS_PAYEE_TYPES_MST m where M.PAYEE_TYPE_CODE=t.PAYEE_TYPE_CODE) as pay_type," +
          	            	 "  DECODE( " +
          	            	 "  (SELECT v.sl_codename FROM SL_TYPE_CODE_NAME_VIEW v WHERE SL_TYPE=t.PAYEE_TYPE_CODE " +
          	            	 "  AND SL_CODE                                                      =t.payable_to " +
          	            	 "  ),NULL,'-', " +
          	            	 "  (SELECT v.sl_codename " +
          	            	 "  FROM SL_TYPE_CODE_NAME_VIEW v " +
          	            	 "  WHERE v.SL_TYPE=t.PAYEE_TYPE_CODE " +
          	            	 "  AND v.SL_CODE  =t.payable_to " +
          	            	 "  ))AS paydesc, " 
          					+ "  BUDGET_SO_FAR_SPENT,amount, "
          					+ " decode(DEDUCTED_AMOUNT,null,'-',deducted_amount) as DEDUCTED_AMOUNT  "
          					+ " FROM fas_bill_register_transactionw t "
          					+ " WHERE ACCOUNTING_UNIT_ID= " + cmbAcc_UnitCode
          					+ " AND CASHBOOK_YEAR       = " + yr
          					+ " AND CASHBOOK_MONTH      = " + mon
          					+ " AND BILL_NO             =" + bilno		
          					
          					;
          			*/
          			
          			
          			
          			//changed qry 
          		sql =	"SELECT ACCOUNT_HEAD_CODE, "
  					+ " t.BILL_MAJOR_TYPE,t.sl_no, "
  					+ " (select BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES m WHERE status='L' and m.BILL_MAJOR_TYPE_CODE=t.BILL_MAJOR_TYPE::numeric)as majordesc, "
  					+ " t.BILL_MINOR_TYPE_CODE, "
  					+ " (select BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST n where n.BILL_MAJOR_TYPE_CODE=t.BILL_MAJOR_TYPE "
  					+ " and n.bill_minor_type_code=t.BILL_MINOR_TYPE_CODE and status='L')as minordesc, "
  					+ "  t.BILL_SUB_TYPE_CODE, "
  					+ "  (select BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES o "
  					+ "  where BILL_MAJOR_TYPE_CODE=t.BILL_MAJOR_TYPE and BILL_MINOR_TYPE_CODE=t.BILL_MINOR_TYPE_CODE "
  					+ "  and t.BILL_SUB_TYPE_CODE=o.bill_sub_type_code and status='L')as subdesc, "
  					+ "  BUDGET_PROVISION, "+
  				     " t.PAYEE_TYPE_CODE,  (select M.PAYEE_TYPE_DESC from FAS_PAYEE_TYPES_MST m where M.PAYEE_TYPE_CODE=t.PAYEE_TYPE_CODE::numeric) as pay_type," +
  	            	 "  coalesce( " +
  	            	 "  (SELECT v.sl_codename FROM SL_TYPE_CODE_NAME_VIEW v WHERE SL_TYPE=t.PAYEE_TYPE_CODE ::numeric" +
  	            	 "  AND SL_CODE                                                      =t.payable_to ::numeric" +
  	            	 "  ),NULL,'-', " +
  	            	 "  (SELECT v.sl_codename " +
  	            	 "  FROM SL_TYPE_CODE_NAME_VIEW v " +
  	            	 "  WHERE v.SL_TYPE=t.PAYEE_TYPE_CODE::numeric " +
  	            	 "  AND v.SL_CODE  =t.payable_to ::numeric" +
  	            	 "  ))AS paydesc, " 
  					+ "  BUDGET_SO_FAR_SPENT,amount, "
  					/* + " decode(DEDUCTED_AMOUNT,null,'-',deducted_amount) as DEDUCTED_AMOUNT  " */
  					+"case when DEDUCTED_AMOUNT is null then 0 else DEDUCTED_AMOUNT end as DEDUCTED_AMOUNT"
  					+ " FROM fas_bill_register_transaction t "
  					+ " WHERE ACCOUNTING_UNIT_ID= " + cmbAcc_UnitCode
  					+ " AND CASHBOOK_YEAR       = " + yr
  					+ " AND CASHBOOK_MONTH      = " + mon
  					+ " and (CASHBOOK_YEAR < 2015 or( CASHBOOK_YEAR = 2015 and CASHBOOK_MONTH < 4 ))  AND BILL_NO             =" + bilno+
  					" union all SELECT ACCOUNT_HEAD_CODE, "
  					+ " t.BILL_MAJOR_TYPE,t.sl_no, "
  					+ " (select BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES m WHERE status='L' and m.BILL_MAJOR_TYPE_CODE=t.BILL_MAJOR_TYPE)as majordesc, "
  					+ " t.BILL_MINOR_TYPE_CODE, "
  					+ " (select BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST n where n.BILL_MAJOR_TYPE_CODE=t.BILL_MAJOR_TYPE "
  					+ " and n.bill_minor_type_code=t.BILL_MINOR_TYPE_CODE and status='L')as minordesc, "
  					+ "  t.BILL_SUB_TYPE_CODE, "
  					+ "  (select BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES o "
  					+ "  where BILL_MAJOR_TYPE_CODE=t.BILL_MAJOR_TYPE and BILL_MINOR_TYPE_CODE=t.BILL_MINOR_TYPE_CODE "
  					+ "  and t.BILL_SUB_TYPE_CODE=o.bill_sub_type_code and status='L')as subdesc, "
  					+ "  BUDGET_PROVISION, "+
  				     " t.PAYEE_TYPE_CODE,  (select M.PAYEE_TYPE_DESC from FAS_PAYEE_TYPES_MST m where M.PAYEE_TYPE_CODE=t.PAYEE_TYPE_CODE::numeric) as pay_type," +
  	            	 "  coalesce( " +
  	            	 "  (SELECT v.sl_codename FROM SL_TYPE_CODE_NAME_VIEW v WHERE SL_TYPE=t.PAYEE_TYPE_CODE ::numeric" +
  	            	 "  AND SL_CODE                                                      =t.payable_to ::numeric" +
  	            	 "  ),NULL,'-', " +
  	            	 "  (SELECT v.sl_codename " +
  	            	 "  FROM SL_TYPE_CODE_NAME_VIEW v " +
  	            	 "  WHERE v.SL_TYPE=t.PAYEE_TYPE_CODE ::numeric" +
  	            	 "  AND v.SL_CODE  =t.payable_to ::numeric" +
  	            	 "  ))AS paydesc, " 
  					+ "  BUDGET_SO_FAR_SPENT,amount, "
  					/* + " decode(DEDUCTED_AMOUNT,null,'-',deducted_amount) as DEDUCTED_AMOUNT  " */
  					+"case when DEDUCTED_AMOUNT is null  then 0 else DEDUCTED_AMOUNT end as DEDUCTED_AMOUNT"
  					+ " FROM fas_bill_register_transactionw t "
  					+ " WHERE ACCOUNTING_UNIT_ID= " + cmbAcc_UnitCode
  					+ " AND CASHBOOK_YEAR       = " + yr
  					+ " AND CASHBOOK_MONTH      = " + mon
  					+ " AND BILL_NO             =" + bilno		
  					
  					;
          			
          		} else if (SancWith.equalsIgnoreCase("N")) {
          			sql = "SELECT ACCOUNT_HEAD_CODE, "
          					+ "  t.BILL_MAJOR_TYPE, "
          					+ "  t.BILL_NO as sl_no , "
          					+ "  (SELECT BILL_MAJOR_TYPE_DESC "
          					+ "  FROM FAS_BILL_MAJOR_TYPES m "
          					+ "  WHERE status              ='L' "
          					+ "  AND m.BILL_MAJOR_TYPE_CODE=t.BILL_MAJOR_TYPE "
          					+ "  )AS majordesc, "
          					+ "  t.BILL_MINOR_TYPE_CODE, "
          					+ "  (SELECT BILL_MINOR_TYPE_DESC "
          					+ "  FROM FAS_BILL_MINOR_TYPES_MST n "
          					+ "  WHERE n.BILL_MAJOR_TYPE_CODE=t.BILL_MAJOR_TYPE "
          					+ "  AND n.bill_minor_type_code  =t.BILL_MINOR_TYPE_CODE "
          					+ "  AND status                  ='L' "
          					+ "  )AS minordesc, "
          					+ "  t.BILL_SUB_TYPE_CODE, "
          					+ "  (SELECT BILL_SUB_TYPE_DESC "
          					+ "  FROM FAS_BILL_SUB_TYPES o "
          					+ "  WHERE BILL_MAJOR_TYPE_CODE=t.BILL_MAJOR_TYPE "
          					+ "  AND BILL_MINOR_TYPE_CODE  =t.BILL_MINOR_TYPE_CODE "
          					+ "  AND t.BILL_SUB_TYPE_CODE  =o.bill_sub_type_code "
          					+ "  AND status                ='L' "
          					+ "  )AS subdesc, "+
          					 " PAYEE_TYPE_CODE, (select M.PAYEE_TYPE_DESC from FAS_PAYEE_TYPES_MST m where M.PAYEE_TYPE_CODE=t.PAYEE_TYPE_CODE) as pay_type,"+
          					 " decode((select v.sl_codename " +
    	        			"  from SL_TYPE_CODE_NAME_VIEW v where SL_TYPE=PAYEE_TYPE_CODE and SL_CODE=PAYEE_CODE),null,'-',(select v.sl_codename from SL_TYPE_CODE_NAME_VIEW v where SL_TYPE=PAYEE_TYPE_CODE and SL_CODE=PAYEE_CODE))as paydesc," +
          					 "  BUDGET_PROVISION, "
          					+ "  BUDGET_SO_FAR_SPENT, "
          					+ "  decode(DEDUCTED_AMOUNT,null,'-',deducted_amount) as DEDUCTED_AMOUNT "
          					+ " FROM FAS_BILL_REGISTERNEW t "
          					+ " WHERE ACCOUNTING_UNIT_ID= " + cmbAcc_UnitCode
          					+ " AND CASHBOOK_YEAR       = " + yr
          					+ " AND CASHBOOK_MONTH      = " + mon
          					+ " AND BILL_NO             =" + bilno;
          		}
          		System.out.println(sql);
          		ps2 = con.prepareStatement(sql);
          		rs2 = ps2.executeQuery();
          		while (rs2.next()) {
          			System.out.println("while");
          			out.println("<tr>");
          			out.println("<td align='left'>" + rs2.getInt("SL_NO")
          					+ "</td>");

          			int major = rs2.getInt("BILL_MAJOR_TYPE");
          			int minor = rs2.getInt("BILL_MINOR_TYPE_CODE");
          			int sub = rs2.getInt("BILL_SUB_TYPE_CODE");
          			String subdesc = rs2.getString("subdesc");
          			int head1=rs2.getInt("ACCOUNT_HEAD_CODE");
          					
          					
          			// ps3=con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
          			//  ps3.setInt(1,rs2.getInt("ACCOUNT_HEAD_CODE"));
          			int c = 0;
          			String val = "", head = "";
          			try {
          			/* 	ps3 = con
          						.prepareStatement("select b.ACCOUNT_HEAD_CODE,(select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS s where s.ACCOUNT_HEAD_CODE=b.ACCOUNT_HEAD_CODE)as headdesc from FAS_BILL_ACCOUNT_HEADS b where "
          								+ " BILL_MAJOR_TYPE_CODE="
          								+ major
          								+ " and BILL_MINOR_TYPE_CODE="
          								+ minor
          								+ " and BILL_SUB_TYPE_CODE=" + sub); */
          						
          								ps3=con
          		          						.prepareStatement("select ACCOUNT_HEAD_DESC as headdesc , ACCOUNT_HEAD_CODE from COM_MST_ACCOUNT_HEADS s where s.ACCOUNT_HEAD_CODE="+head1);
          				rs3 = ps3.executeQuery();

          				while (rs3.next()) {
          					val = rs3.getInt("ACCOUNT_HEAD_CODE") + "-"
          							+ rs3.getString("headdesc");
          					c++;
          				}
          			} catch (Exception e) {
          				e.printStackTrace();
          			}
          			//out.println(c+" >> "+val);  
          			if (c == 0) {
          				head = " - ";
          			} else if (c != 0) {
          				head = val;
          			}
          			out.println("<td align='left'>" + head + "</td>");
          			out.println("<td align='left'>"
          					+ rs2.getString("majordesc") + "</td>");
          			out.println("<td align='left'>"
          					+ rs2.getString("minordesc") + "</td>");
          			out.println("<td align='left'>" + rs2.getString("subdesc")
          					+ "</td>");
          			out.println("<td align='left'>" + rs2.getString("pay_type")
          					+ "</td>");
          			out.println("<td align='left'>" + rs2.getString("paydesc")
          					+ "</td>");
          			if (SancWith.equalsIgnoreCase("Y")||SancWith.equalsIgnoreCase("GPF"))
          			{
          				out.println("<td align='left'>" + rs2.getBigDecimal("amount")
              					+ "</td>");
          			}
          			//out.println("<td align='left'>"+rs2.getString("BUDGET_SO_FAR_SPENT")+"</td>");
          			// out.println(subdesc);
          			if (subdesc.equals("SLS")) {
          				out.println("<td align='left'>"
          						+ rs2.getString("BUDGET_PROVISION") + "</td>");
          				out.println("<td align='left'>"
          						+ rs2.getBigDecimal("BUDGET_SO_FAR_SPENT")
          						+ "</td></tr>");
          			} else {
          				out.println("<td align='left'>"
          						+ rs2.getString("BUDGET_PROVISION") + "</td>");
          				out.println("<td align='left'>"
          						+ rs2.getBigDecimal("BUDGET_SO_FAR_SPENT")
          						+ "</td>");
          				//	 <th style="display:none" id="dedid">Deducted Amount</th>
          				out.println("<td align='left'>"
          						+ rs2.getString("DEDUCTED_AMOUNT").trim()
          						+ "</td></tr>");
          			}
          		}

          	} catch (Exception e) {
          		System.out.println("Exception in grid.." + e);
          	}
          %>
        </tbody>
      </table>
      <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
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
</html>