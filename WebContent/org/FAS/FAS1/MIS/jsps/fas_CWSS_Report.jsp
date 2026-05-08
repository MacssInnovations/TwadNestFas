<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
     <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
<title>CWSS Demand Raised Water Charges Details</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen" />
<script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">          </script>
	
	<script type="text/javascript">
function butChk(val)
{
if(val=='Monthly')
	{
	document.getElementById("motnDiv").style.display="block";
	document.getElementById("motndetDiv").style.display="block";
	document.getElementById("divDetFin").style.display="block";
	document.getElementById("divFin").style.display="block";
	document.getElementById("hid").value="Monthly";
	}
else if(val=='yearly')
	{	
	document.getElementById("divDetFin").style.display="block";
	document.getElementById("divFin").style.display="block";
	document.getElementById("motnDiv").style.display="none";
	document.getElementById("motndetDiv").style.display="none";
	document.getElementById("hid").value="yearly";
	}
}
</script>
</head>
 <%
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", 0);
%>
<%
String userid = (String) session.getAttribute("UserId");
System.out.println("User Id is:" + userid);
UserProfile up=null;
up=(UserProfile)session.getAttribute("UserProfile");
System.out.println("User name is:" + up.getEmployeeName());
%>
<%
  
		      
		      Statement st=null;
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



<body bgcolor="#FFF9FF" onload="LoadAccountingUnitID('LIST_ALL_UNITS');">
<form name="frmCWSS_Details" id="frmCWSS_Details"  method="POST" action="../../../../../Fas_CWSS_Report" >
 <input type="hidden" id="cmd" name="cmd" value="yearly">
 <table width="100%" border="1" align="center">
        <tr class="tdH">
          <td>
            <div align="center"> Demand Raised Water Charges And Expenditure on CWSS </div></td>
            </tr>
            </table>               
           <br>                
<table width="100%" border="0" align="center">
	<tr class="table">
		<td  width="10%">
		<div align="left" id="divFin">Financial Year</div>
		</td>
		<td  width="20%"><div align="left" id="divDetFin">
		<select id="liveFY" name="liveFY" style="width: 80pt;">
			<option value="">--Select--</option>			
			<%
					                        st=con.createStatement();
					                        rs=st.executeQuery("select financial_year from cash_book_control order by financial_year");
					                        while(rs.next())
					                        {
					                            out.println("<option value='"+rs.getString("financial_year")+"'>"+rs.getString("financial_year")+"</option>");
					                        }
                    				%>
		</select></div>
		<td  width="70%">&nbsp;</td>		
		</tr>
	 
	<tr class="table">
	<td width="10%"><div align="left">Report Type</div></td>
	<td width="20%" colspan="">
	<input type="radio" id="rad_R" name="rad_R" value="PDF">PDF
	
	<input type="radio" id="rad_R" name="rad_R" value="HTML">HTML
	<input type="radio" id="rad_R" name="rad_R" value="EXCEL">EXCEL
	</td>
	<td  width="70%">&nbsp;</td>
	</tr>
</table>

  <table width="100%" border="1" align="center">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="Submit" value="Submit" ></input>               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="window.close();"></input>
            </div>
          </td>
        </tr>
      </table>

  
 
   
   
      <!--
      <div id="imgfld" style="position: absolute; top: 354px; visibility: hidden; left: 378px; width: 212px; height: 6px;"
			left=100 top=100><input type="image" name="img1" id="img1"
			src="../../../../../images/Loading.gif" height="200"></div>
-->
</form>
</body>
</html>