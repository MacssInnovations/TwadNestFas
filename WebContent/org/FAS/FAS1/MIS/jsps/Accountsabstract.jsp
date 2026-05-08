<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Annual Account Abstract</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
	<script type="text/javascript" src="../scripts/Accountsabstract.js"></script>
</head>
<%
	String s = request.getContextPath();
%>
<%
String userid = (String) session.getAttribute("UserId");
System.out.println("User Id is:" + userid);
UserProfile up=null;
up=(UserProfile)session.getAttribute("UserProfile");
System.out.println("User name is:" + up.getEmployeeName());
%>
<%
  
		      Connection con=null;
		      ResultSet rs=null,rs2=null;
		      Statement st=null;
		      PreparedStatement ps=null;
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


<body onload="load();">
<!-- action="../../../../../Accounts_Data.view?Command=Abstract" -->
<form id="Annualaccount"  method="POST"  
                  action="../../../../../Accounts_Data.view?Command=Abstract" >
<table width="100%" border="1" align="center">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><strong>Annual Account</strong></div>		</td>
	</tr>
	</table>

     <table cellspacing="1" cellpadding="2" border="1" width="100%">             
                <tr align="left">
           			<td class="table">
              			<div align="left">
                 			Financial Year <font color="#ff2121">*</font>
              			</div>
           			</td>
          			<td class="table">        				       
		          <div id="more">
          		 
          	
          	<select name="txtfin_year"  id="txtfin_year" tabindex="4"    onchange="doFunction('load_Account_Details',this.value);">
          		<option value="">--select--</option>
          		<%
					                        st=con.createStatement();
					                        rs=st.executeQuery("select DISTINCT(AG.FINYEARSTART||'-'||AG.FINYEAREND) AS FINANCIAL_YEAR from FAS_HO_ANNUALGROUPING AG  order by financial_year");
					                        while(rs.next())
					                        {
					                            out.println("<option value='"+rs.getString("financial_year")+"'>"+rs.getString("financial_year")+"</option>");
					                        }
                    				%>
          		         	
          </select>
          </div>      
          </td></tr>
            <tr align="left">
           			<td class="table">
              			<div align="left">
                 			Report Type <font color="#ff2121">*</font>
              			</div>
           			</td>
          			<td class="table">        				       
		          <div id="more">
          		 
          		<input type="radio" name="btnRdo" id="PDF" value="PDF" CHECKED >PDF
		<!-- <input type="radio" name="btnRdo" id="HTML" value="HTML">HTML -->
		<input type="radio" name="btnRdo" id="EXCEL" value="EXCEL">EXCEL
          </div>  
           <input type="hidden" id="ah" name="ah" value="">    
          </td>
          <!-- <td style=styledisplay:none>
          
          </td> -->
          </tr>	 
	  </table>
	  <table width="100%" border="1" align="center">
	<tr class="tdH">
		<td colspan="2">
		<center>
		<input type="submit" name="btnSubmit" id="btnSubmit" value="SUBMIT" onclick="return annualReport();" >
	
		</center>		
		</td></tr>
		</table>
	  
	  
</form>
</body>
</html>