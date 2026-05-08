<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.Format"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@page import="java.math.BigDecimal"%>
<%@ page
	import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
<head>
<style type="text/css">
.btn-success {
    width: 50px;
    height: 30px;
    background-color: #C48793;
  
   border-radius: 50%
}</style>
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
function loadValuesMinorTable(unit_id,id,val,fromMonth,toMonth){
	//alert(">>>> "+val);
	var url="fas_NRDWP_Minor.jsp?id="+id+"&val="+val+"&fromMonth="+fromMonth+"&toMonth="+toMonth;
location.href=url;
	//window.open("fas_NRDWP_Minor.jsp?id="+id+"&val="+val+"&fromMonth="+fromMonth+"&toMonth="+toMonth);
}
function num_Format(val){
	alert(val);
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
	style='position: relative; width: 100%;white-space: wrap; border-height: 1px; top: 0em; vertical-align: middle; height: 600px; '>
<div id='scroll_text' style="vertical-align: top;" >

<table border=1 width="82%" cellpadding="0" cellspacing="0"
	style="border-collapse: collapse;" align="center">
	
	<%
		
	double tot1=0,tot2=0,tot3=0,tot4=0,tot5=0,tot6=0,tot7=0;	
	String sub_qry="";
	int cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	if(cmbAcc_UnitCode==0){sub_qry="";}
	else if(cmbAcc_UnitCode!=0){
		 sub_qry="  and v.accounting_unit_id="+cmbAcc_UnitCode;
	}
String val="",id="",qry="",fromMonth="",toMonth="";
String WE_R=request.getParameter("WE_R");
String imis_id=request.getParameter("imis_id");
String imis_val=request.getParameter("imis_val");
val=request.getParameter("val");
id=request.getParameter("id");	
fromMonth = request.getParameter("fromMonth");
toMonth = request.getParameter("toMonth");		
		out.println("<tr>");
		out.println("<td colspan='7' valign='bottom' align='center' style='color:#FFF5EE;height:35px;font-size:18px ;font-weight: bold;background:#3B9C9C;'> NRDWP Details</td>");
		out.println("</tr>");
		out.println("<tr class='tdH' style='height: 30px;'>");
		out.println("<th style='font-size:13px ;'>Head</th>");
		out.println("<th style='max-width: 300px;white-space: wrap;font-size:13px;'>Description</th>");
		out.println("<th style='font-size:13px ;'>Regular Population</th>");
		out.println("<th style='font-size:13px ;'>SC Population</th>");
		out.println("<th style='font-size:13px ;'>ST Population</th>");
		out.println("<th style='font-size:13px ;'>Other Population</th>");
		out.println("<th style='font-size:13px ;'>Net</th>");
		//out.println("<th></th>");
		out.println("</tr>");
	
		qry = "SELECT t.account_head_code, " +
		"  h.account_head_desc, " +
		"  T.Annualplanninggroupingid, " +
		"  T.Annualplanninggrouping, " +
		"  t.Imisgroupingid, " +
		"  t.Imisclassification , " +
		"  SUM(OTHERS)OTHERS, " +
		"  SUM(Regular)Regular, " +
		"  SUM(Sc)Sc, " +
		"  SUM(St)St, " +
		"  SUM(net_AMT)NET " +
		"FROM " +
		"  (SELECT v.account_head_code, " +
		"    Fh.Annualplanninggroupingid, " +
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
		"  AND Fh.Minorgroupingid =? " +
		"  AND V.Cmonth BETWEEN ? AND ? " +
		"  AND Fh.Annualplanninggroupingid  =? " +
		"  and fh.imisgroupingid= ?"+
				sub_qry+
		"  AND Fh.Annualplanninggroupingid IN (1,2,16,17,21,22) " +
		"  GROUP BY v.account_head_code, " +
		"    Fh.Annualplanninggroupingid, " +
		"    Ap.Annualplanninggrouping, " +
		"    Fh.Populationid , " +
		"    Fh.Imisgroupingid, " +
		"    Im.Imisclassification " +
		"  ORDER BY To_Number(Fh.Annualplanninggroupingid) " +
		"  )t " +
		"INNER JOIN COM_MST_ACCOUNT_HEADS h " +
		"ON t.account_head_code=h.account_head_code " +
		"GROUP BY t.account_head_code, " +
		"  h.account_head_desc, " +
		"  T.Annualplanninggroupingid, " +
		"  T.Annualplanninggrouping, " +
		"  t.Imisgroupingid, " +
		"  t.Imisclassification " +
		"ORDER BY To_Number(T.Annualplanninggroupingid),t.account_head_code";

		System.out.println("test"+qry);
		try{
		 ps = con.prepareStatement(qry);
		 ps.setString(1,WE_R);
		 ps.setString(2,fromMonth);
		 ps.setString(3,toMonth);
		 ps.setInt(4,Integer.parseInt(id));
		 ps.setInt(5,Integer.parseInt(imis_id));
		rs = ps.executeQuery();
	int c=0;
	out.println("<tr class='table'>");
	out.println("<td align='center' bgcolor='#7D6655' style='color: #FDD7E4;height:35px;' colspan='7' >"+imis_val.trim()+"</td>");
	out.println("</tr>");
		while (rs.next()) {
			c++;
			tot1+=Double.parseDouble(rs.getString("OTHERS"));
			tot2+=Double.parseDouble(rs.getString("Regular"));
			tot3+=Double.parseDouble(rs.getString("Sc"));
			tot4+=Double.parseDouble(rs.getString("St"));
			tot6+=Double.parseDouble(rs.getString("NET"));	
			//NumberFormat formater=NumberFormat.getCurrencyInstance(new Locale("en", "US"));
			//String MoneyStr=formater.format(4545445.45);
			//out.println("Money >>> "+MoneyStr);
			//Format format=com.i
			//Format format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
		//	System.out.println(format.format(new BigDecimal("100000000")));
		//NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
		//DecimalFormat df=(DecimalFormat)nf;
//DecimalFormat df = (DecimalFormat)nf;
			out.println("<tr>");
			out.println("<td style='font-size:13px;'>"+rs.getInt("account_head_code")+"</td>");
			out.println("<td style='max-width: 300px;white-space: wrap;font-size:13px;'>"+rs.getString("account_head_desc").trim()+"</td>");
			out.println("<td style='font-size:13px ;' align='Right'>"+rs.getBigDecimal("Regular").setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
			out.println("<td style='font-size:13px ;' align='Right'>"+rs.getBigDecimal("Sc").setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
			out.println("<td style='font-size:13px ;' align='Right'>"+rs.getBigDecimal("St").setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
			out.println("<td style='font-size:13px ;' align='Right'>"+rs.getBigDecimal("OTHERS").setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
			//out.println("<td align='Right'>"+rs.getBigDecimal("Minority")+"</td>");
			out.println("<td align='Right' style='font-size:13px ;'>"+rs.getBigDecimal("NET").setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
			//out.println("<td><select id='type_Re'><option value=0>Head Code</option><option value=1>Division</option></td>");
			out.println("</tr>");
		}
		out.println("<tr style='font-size:14px ;background:#3B9C9C;height:35px;'>");
		out.println("<td style='font-size:14px ;' align='Right' colspan='2'>Total</td>");		
		out.println("<td style='font-size:14px ;' align='Right'>"+new BigDecimal(tot2).setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
		out.println("<td style='font-size:14px ;' align='Right'>"+new BigDecimal(tot3).setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
		out.println("<td style='font-size:14px ;' align='Right'>"+new BigDecimal(tot4).setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
		//out.println("<td align='Right'>"+tot5+"</td>");
		out.println("<td style='font-size:14px ;' align='Right'>"+new BigDecimal(tot1).setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
		out.println("<td style='font-size:14px ;' align='Right'>"+new BigDecimal(tot6).setScale(2,BigDecimal.ROUND_CEILING)+"</td>");
		
	//	out.println("<td align='Right'></td>");
		
		out.println("</tr>");
	} catch(Exception e)
          {
             System.out.println("testing Exception  >>>>>>>> "+e);
          } 

	%></table>
	<table  width="82%" cellpadding="0" cellspacing="0" align="center" >
	<tr class='tdH' >  
		<td  colspan="" align="center" >
		<input type=button value="Back" onclick="window.history.go(-1)">
	</td> 
</tr>
</table>
</div>
</div>

</form>
</body>
</html>