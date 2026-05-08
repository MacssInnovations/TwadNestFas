<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.math.RoundingMode"%>
<%@page import="java.math.BigDecimal"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page
	import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252" />
<META HTTP-EQUIV="CACHE-CONTROL"
	CONTENT=" no-store, no-cache, must-revalidate">
<META HTTP-EQUIV="CACHE-CONTROL"
	CONTENT=" pre-check=0, post-check=0, max-age=0">
<title>WRKEXP SUMMARY</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="../../../../Security/scripts/tabpane.js">          </script>
<script type="text/javascript" src="../scripts/Fas_trialDta_Details.js"></script>
<script type="text/javascript">

</script>
</head>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);
%>
<body bgcolor="#FFF9FF">
<form name="frmFas_Details" id="frmFas_Details" method="" action="">
<div id='scroll_clipper'
	style='position: relative; width: 100%; border-height: 1px; top: 0em; vertical-align: middle;; height: 500px; overflow: auto; white-space: nowrap; vertical-align: top;'>
<div id='scroll_text' style="vertical-align: top;">
<table border=1 width="82%" cellpadding="0" cellspacing="0"
	style="border-collapse: collapse;" align="center">
	<%
		String cmd = "", liveFY = "", liveTY = "", fromMonth = "", toMonth = "", qry = "", sup_query = "", unit_id = "", head = "", unit_qry = "";
	double tot1=0,tot2=0,tot3=0,tot4=0,tot5=0,tot6=0,tot7=0;	
	int cmbAcc_UnitCode = 0;
		cmd = request.getParameter("cmd");
		liveFY = request.getParameter("liveFY");
		liveTY = request.getParameter("liveTY");
		fromMonth = request.getParameter("fromMonth");
		toMonth = request.getParameter("toMonth");
		sup_query = request.getParameter("sup_query");
		unit_id = request.getParameter("unit_id");
		head = request.getParameter("head");
		unit_qry = request.getParameter("unit_qry");
		int wE_Type = Integer.parseInt(request.getParameter("wE_Type"));
		cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		out.println("<tr>");
		out.println("<td colspan='8'><caption style='color:#99aa44;font-size: 150%;font-style: italic'>" + head
				+ "<caption></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th>Description</th>");
		out.println("<th>Funds</th>");
		out.println("<th>Works_Expenditure</th>");
		out.println("<th>Centage</th>");
		out.println("<th>Interest_Charged</th>");
		out.println("<th>Materials</th>");
		out.println("<th>Net</th>");
		out.println("</tr>");
	
		qry = "SELECT t.Data_Tb, " + "  t.Schemeid, " + "  t.Schemename, "
				+ "  SUM(t.DR_AMOUNT)DR_AMOUNT, "
				+ "  SUM(t.CR_AMOUNT)CR_AMOUNT, "
				//+ "  SUM(t.Net_Amt)Net_Amt, " 
				+ "  SUM(t.Funds)Funds, "
				+ "  SUM(t.Works_Expenditure)Works_Expenditure, "
				+ "  SUM(t.Centage)Centage, "
				+ "  SUM(t.Interest_Charged)Interest_Charged, "
				+ "  SUM(t.Materials)Materials, "
				+"  SUM(t.Funds)+  SUM(t.Works_Expenditure)+  SUM(t.Centage)+  SUM(t.Interest_Charged)+  SUM(t.Materials) as Net_Amt, "
				+ "  t.Fin_Year "
				+ "FROM " + "  (SELECT 1                 AS Data_Tb, "
				+ "    to_number(Fh.Projectid) AS Projectid, "
				+ "    Pr.Projectname, " + "    Fh.Programid, "
				+ "    Pg.Programname, " + "    Fh.Schemeid, "
				+ "    Ps.Schemename, " + "    V.Account_Head_Code, "
				+ "    Fh.Headofaccount, " + "    Sub_Ledger_Type_Code, "
				+ "    Sub_Ledger_Code, " + "    SUM(dr_amt)DR_AMOUNT, "
				+ "    SUM(CR_AMT)CR_AMOUNT, "
				+ "    SUM(Dr_Amt)-SUM(Cr_Amt) AS Net_Amt , "
				+ "    Fh.Activityid, " + "    Ac.Activityname, "
				+ "    CASE Fh.activityid " + "      WHEN '1' "
				+ "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " + "      ELSE 0 "
				+ "    END AS Funds, " + "    CASE Fh.activityid "
				+ "      WHEN '2' " + "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) "
				+ "      ELSE 0 " + "    END AS Works_Expenditure, "
				+ "    CASE Fh.activityid " + "      WHEN '3' "
				+ "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " + "      ELSE 0 "
				+ "    END AS Centage, " + "    CASE Fh.activityid "
				+ "      WHEN '4' " + "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) "
				+ "      ELSE 0 " + "    END AS Interest_Charged, "
				+ "    CASE Fh.activityid " + "      WHEN '15' "
				+ "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " + "      ELSE 0 "
				+ "    END AS Materials, " + "    Fin_Year, "
				+ "    SUB_LEDGER_TYPE_DESC " + "  FROM Fas_Wrkexp_View V "
				+ "  INNER JOIN fas_Headofaccounts Fh "
				+ "  ON V.Account_Head_Code=Fh.Accountcode "
				+ "  INNER JOIN Activity Ac "
				+ "  ON Fh.Activityid=Ac.Activityid "
				+ "  INNER JOIN Project Pr "
				+ "  ON Fh.Projectid=Pr.Projectid "
				+ "  INNER JOIN Program Pg "
				+ "  ON Fh.Programid=Pg.Programid "
				+ "  INNER JOIN Schemes Ps "
				+ "  ON Fh.Projectid =Ps.Projectid "
				+ "  AND Fh.Programid=Ps.Programid "
				+ "  AND fh.Schemeid =Ps.Schemeid "
				+ sup_query
				+ unit_qry
				+ "  AND Fh.Minorgroupingid='"
				+ wE_Type
				+ "' "
				+ "  GROUP BY To_Number (Fh.Projectid), "
				+ "    Pr.Projectname, "
				+ "    Fh.Programid, "
				+ "    Pg.Programname, "
				+ "    Fh.Schemeid, "
				+ "    Ps.Schemename, "
				+ "    V.Account_Head_Code, "
				+ "    Fh.Headofaccount, "
				+ "    Sub_Ledger_Type_Code, "
				+ "    Sub_Ledger_Code, "
				+ "    Fh.Activityid, "
				+ "    Ac.Activityname, "
				+ "    Fin_Year, "
				+ "    SUB_LEDGER_TYPE_DESC "
				+ "  )t "
				+ " GROUP BY t.Data_Tb, "
				+ "  t.Schemeid, "
				+ "  t.Schemename, "
				+ "  t.Fin_Year "
				+ " ORDER BY t.Schemeid";
		System.out.println("test"+qry);
		try{
		 ps = con.prepareStatement(qry);
		rs = ps.executeQuery();
	int c=0;
		
		while (rs.next()) {
			System.out.println("c"+c);
			c++;
			tot1+=Double.parseDouble(rs.getString("Funds"));
			tot2+=Double.parseDouble(rs.getString("Works_Expenditure"));
			tot3+=Double.parseDouble(rs.getString("Centage"));
			tot4+=Double.parseDouble(rs.getString("Interest_Charged"));
			tot5+=Double.parseDouble(rs.getString("Materials"));
			tot6+=Double.parseDouble(rs.getString("Net_Amt"));
			
			out.println("<tr>");
			out.println("<td>"+rs.getString("Schemename")+"</td>");
			out.println("<td align='Right'>"+rs.getBigDecimal("Funds")+"</td>");
			out.println("<td align='Right'>"+rs.getBigDecimal("Works_Expenditure")+"</td>");
			out.println("<td align='Right'>"+rs.getBigDecimal("Centage")+"</td>");
			out.println("<td align='Right'>"+rs.getBigDecimal("Interest_Charged")+"</td>");
			out.println("<td align='Right'>"+rs.getBigDecimal("Materials")+"</td>");
			out.println("<td align='Right'>"+rs.getBigDecimal("Net_Amt")+"</td>");
			out.println("</tr>");
		}
		out.println("<tr>");
		
		out.println("<td align='Right'>Total</td>");
		out.println("<td align='Right'>"+new BigDecimal(tot1).setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
		out.println("<td align='Right'>"+new BigDecimal(tot2)+"</td>");
		out.println("<td align='Right'>"+new BigDecimal(tot3)+"</td>");
		out.println("<td align='Right'>"+new BigDecimal(tot4)+"</td>");
		out.println("<td align='Right'>"+new BigDecimal(tot5)+"</td>");
		out.println("<td align='Right'>"+new BigDecimal(tot6).setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
		out.println("</tr>");
	} catch(Exception e)
          {
             System.out.println("testing Exception  >>>>>>>> "+e);
          } 
	%>




</table>
</div>
</div>
</form>
</body>
</html>