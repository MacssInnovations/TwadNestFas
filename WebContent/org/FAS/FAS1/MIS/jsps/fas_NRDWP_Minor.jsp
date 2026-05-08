<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<title>NRDWP Minor Report</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="../../../../Security/scripts/tabpane.js">          </script>
<script type="text/javascript">
function loadValuesMinorTable(unit_id,id,val,fromMonth,toMonth,WE_R,imis_id,imis_val){
	
	var url="fas_NRDWP_Details_head.jsp?id="+id+"&val="+val+"&fromMonth="+fromMonth+"&toMonth="+toMonth+"&WE_R="+WE_R+"&imis_id="+imis_id+"&imis_val="+imis_val+"&cmbAcc_UnitCode="+unit_id;
	
	location.href=url;
	//window.open("fas_NRDWP_Minor.jsp?id="+id+"&val="+val+"&fromMonth="+fromMonth+"&toMonth="+toMonth);
}
</script>
</head>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);
%>
<body bgcolor="#FFF9FF">
<form name="frmFas_Details_HTMl" id="frmFas_Details_HTMl" method="" action="">
<div id='scroll_clipper'
	style='position: relative; width: 100%; border-height: 1px; top: 0em; vertical-align: middle; height: 500px; overflow: auto; white-space: nowrap;'>
<div id='scroll_text' style="vertical-align: top;">
<table border=1 width="82%" cellpadding="0" cellspacing="0"
	style="border-collapse: collapse;" align="center">
	<%
		
	double tot1=0,tot2=0,tot3=0,tot4=0,tot5=0,tot6=0,tot7=0;	
	
String val="",id="",qry="",fromMonth="",toMonth="",sub_qry="";
int cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
if(cmbAcc_UnitCode==0){sub_qry="";}
else if(cmbAcc_UnitCode!=0){
	 sub_qry="  and v.accounting_unit_id="+cmbAcc_UnitCode;
}
String WE_R=request.getParameter("WE_R");
val=request.getParameter("val");
id=request.getParameter("id");	
fromMonth = request.getParameter("fromMonth");
toMonth = request.getParameter("toMonth");		
		out.println("<tr >");
		out.println("<td colspan='6' valign='bottom' align='center' style='color:#FFF5EE;height:35px;font-size:18px ;font-weight: bold;background:#3B9C9C;'> NRDWP Details</caption></td>");
		out.println("</tr>");
		out.println("<tr class='tdH' style='height: 40px;'>");
		
		out.println("<th>Description</th>");
		out.println("<th>Regular Population</th>");
		out.println("<th>SC Population</th>");
		out.println("<th>ST Population</th>");
		out.println("<th>Other Population</th>");
		out.println("<th>Net</th>");
		//out.println("<th></th>");
		out.println("</tr>");
	
		qry = "SELECT T.Annualplanninggroupingid, " +
		"  T.Annualplanninggrouping, " +
		"  t.Imisgroupingid, " +
		"  t.Imisclassification , " +
		"  SUM(OTHERS)OTHERS, " +
		"  SUM(Regular)Regular, " +
		"  SUM(Sc)Sc, " +
		"  SUM(St)St, " +
		"  SUM(net_AMT)NET " +
		"FROM " +
		"  (SELECT Fh.Annualplanninggroupingid, " +
		"    Ap.Annualplanninggrouping, " +
		"    Fh.Imisgroupingid, " +
		"    Im.Imisclassification, " +
		"    SUM(Dr_Amt)-SUM(Cr_Amt) net_AMT, " +
		"    CASE " +
		"      WHEN Fh.Populationid IS NULL " +
		"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
		"      ELSE 0 " +
		"    END AS OTHERS, " +
		"    CASE " +
		"      WHEN Fh.Populationid=1 " +
		"      OR Fh.Populationid  =4 " +
		"      THEN (SUM(Dr_Amt)-SUM(Cr_Amt)) " +
		"      ELSE 0 " +
		"    END Regular, " +
		"    CASE " +
		"      WHEN Fh.Populationid=2 " +
		"      THEN (SUM(Dr_Amt)-SUM(Cr_Amt)) " +
		"      ELSE 0 " +
		"    END Sc, " +
		"    CASE " +
		"      WHEN Fh.Populationid=3 " +
		"      THEN (SUM(Dr_Amt)-SUM(Cr_Amt)) " +
		"      ELSE 0 " +
		"    END St, " +
		"    SUM(Dr_Amt)-SUM(Cr_Amt) Net " +
		"  FROM View_Fas_Trial_cp v " +
		"  INNER JOIN fas_headofaccounts fh " +
		"  ON V.Account_Head_Code=Fh.Accountcode " +
		"  INNER JOIN Annualplanninggrouping Ap " +
		"  ON Fh.Annualplanninggroupingid=Ap.Annualplanninggroupingid " +
		"  INNER JOIN Imisgrouping Im " +
		"  ON Fh.Imisgroupingid   =Im.Id " +
		"  AND Fh.Minorgroupingid =?" +
		"   and V.Cmonth between ? and ? "+
		"   and Fh.Annualplanninggroupingid=?"+
				sub_qry+
		//"    --And V.Fin_Year='2011-2012' " +
		"  AND Fh.Annualplanninggroupingid IN (1,2,16,17,21,22) " +
		"  GROUP BY Fh.Annualplanninggroupingid, " +
		"    Fh.Populationid, " +
		"    Ap.Annualplanninggrouping, " +
		"    Fh.Imisgroupingid, " +
		"    Im.Imisclassification " +
		"  ORDER BY To_Number(Fh.Annualplanninggroupingid) " +
		"  )t " +
		"GROUP BY T.Annualplanninggroupingid, " +
		"  T.Annualplanninggrouping, " +
		"  t.Imisgroupingid, " +
		"  t.Imisclassification " +
		"ORDER BY To_Number(T.Annualplanninggroupingid)";

		System.out.println("test"+qry);
		try{
		 ps = con.prepareStatement(qry);
		 ps.setString(1,WE_R);
		 ps.setString(2,fromMonth);
		 ps.setString(3,toMonth);
		 ps.setInt(4,Integer.parseInt(id));
		rs = ps.executeQuery();
	int c=0;
	out.println("<tr class='table'>");
	out.println("<td align='center' bgcolor='#7D6655' style='color: #FDD7E4 ;height: 35px;'  colspan='6'>"+val.trim()+"</td>");
	out.println("</tr>");
		while (rs.next()) {
			c++;
			tot1+=Double.parseDouble(rs.getString("OTHERS"));
			tot2+=Double.parseDouble(rs.getString("Regular"));
			tot3+=Double.parseDouble(rs.getString("Sc"));
			tot4+=Double.parseDouble(rs.getString("St"));
			//tot5+=Double.parseDouble(rs.getString("Minority"));
			tot6+=Double.parseDouble(rs.getString("NET"));		
			out.println("<tr class='table'>");
			out.println("<td style='height: 30px;'><a href=\"javascript:loadValuesMinorTable("+cmbAcc_UnitCode+",'" + rs.getString("Annualplanninggroupingid") + "','" + rs.getString("Annualplanninggrouping") + "','"+fromMonth+"','"+toMonth+"','"+WE_R+"'," + rs.getString("Imisgroupingid") + ",'"+rs.getString("Imisclassification")+"')\">"+rs.getString("Imisclassification")+"</a></td>");
			
			out.println("<td align='Right' style='height: 30px;'>"+rs.getBigDecimal("Regular").setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
			out.println("<td align='Right' style='height: 30px;'>"+rs.getBigDecimal("Sc").setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
			out.println("<td align='Right' style='height: 30px;'>"+rs.getBigDecimal("St").setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
			out.println("<td align='Right' style='height: 30px;'>"+rs.getBigDecimal("OTHERS").setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
			//out.println("<td align='Right'>"+rs.getBigDecimal("Minority")+"</td>");
			out.println("<td align='Right' style='height: 30px;'>"+rs.getBigDecimal("NET").setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
			//out.println("<td><select id='type_Re"+c+"'><option value=0>--Select--</option><option value=1>Head Code</option><option value=2>Division</option></td>");
			out.println("</tr>");
		}
		out.println("<tr class='table' style='background:#3B9C9C;'>");
		out.println("<td align='Right' style='height: 30px;'>Total</td>");		
		out.println("<td align='Right' style='height: 30px;'>"+new BigDecimal(tot2).setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
		out.println("<td align='Right' style='height: 30px;'>"+new BigDecimal(tot3).setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
		out.println("<td align='Right' style='height: 30px;'>"+new BigDecimal(tot4).setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
		//out.println("<td align='Right'>"+tot5+"</td>");
		out.println("<td align='Right' style='height: 30px;'>"+new BigDecimal(tot1).setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
		out.println("<td align='Right' style='height: 30px;'>"+new BigDecimal(tot6).setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
		out.println("<td align='Right'></td>");
		
		out.println("</tr>");
	} catch(Exception e)
          {
             System.out.println("testing Exception  >>>>>>>> "+e);
          } 

	%></table>
	<table  width="82%" cellpadding="0" cellspacing="0"
	style="border-collapse: collapse;" align="center">
	<tr> 
		<td colspan="6" width="7%" valign="bottom" align="right">
		<input type=button value="Back" onclick="window.history.go(-1)">
	</td> 
</tr>
</table>
</div>
</div>
</form>
</body>
</html>