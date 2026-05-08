<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.sql.*,java.util.*"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>

<title>Insert title here</title>
</head>
<body><form name="HRM_EmpSearch" id="HRM_EmpSearch">
      <p>
        <%
  
   Connection connection=null;
  PreparedStatement ps=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
  
  
  try
  {
  
             ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rs.getString("Config.DSN");
            String strhostname=rs.getString("Config.HOST_NAME");
            String strportno=rs.getString("Config.PORT_NUMBER");
            String strsid=rs.getString("Config.SID");
            String strdbusername=rs.getString("Config.USER_NAME");
            String strdbpassword=rs.getString("Config.PASSWORD");

            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             Class.forName(strDriver.trim());
             connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  
  %>
      </p>
  <table border="0" width="100%" align="center">
        <tr>
          <td class="tdH">
            <div align="center">Budget Details</div></td></tr></table>
            <br>
            <table border="2" width="100%" align="center">
        <tr>
          <td class="tdH">Ref No
          </td>
          <td class="tdH">Ref Date
          </td>
          <td class="tdH">BUDGET_ALLOTTED
          </td>
          <td class="tdH">Budget Sofar Spent
          </td>
          </tr>
          <tr>
          <% 
          try{
          int cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
          int cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
          String finYr=request.getParameter("finYr");
          String headCode=request.getParameter("headcode");
          String qry_Budget="  SELECT ACCOUNT_HEAD_CODE,ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID, " +
			"    nvl(CURRENT_YEAR_BUDGET_ALLOTTED,'0') as CURRENT_YEAR_BUDGET_ALLOTTED  , " +
			"    nvl(BUDGET_SOFAR_SPENT,'0') as BUDGET_SOFAR_SPENT, " +
			"    REF_NO, " +
			"    TO_CHAR(REF_DATE,'dd/mm/yyyy')AS REF_DATE " +
			"  FROM COM_BUDGET_DETAILS  where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and " +
			" FINANCIAL_YEAR ='"+finYr+"' and ACCOUNT_HEAD_CODE ="+headCode ;
//out.print(qry_Budget);
PreparedStatement ps_bud=connection.prepareStatement(qry_Budget);
ResultSet rs_bud=ps_bud.executeQuery();
if(rs_bud.next()){
out.println("<td>"+rs_bud.getString("REF_NO").trim()+"</td>");
out.println("<td>"+rs_bud.getString("REF_DATE")+"</td>");
out.println("<td>"+rs_bud.getString("CURRENT_YEAR_BUDGET_ALLOTTED")+"</td>");
out.println("<td>"+rs_bud.getString("BUDGET_SOFAR_SPENT")+"</td>");
} else{
	out.println("<td class='tdH' colspan='4'>No Data Found</td>");
}
          }catch(Exception e)
          {
        	e.printStackTrace();  
          }
          %>
          
          
          </tr>
          
          
          </table>
</body>
</html>