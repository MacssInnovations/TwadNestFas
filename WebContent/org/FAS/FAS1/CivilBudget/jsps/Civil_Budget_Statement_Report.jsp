<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Civil_Budget_Statement_Report_1</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen" />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
 <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript" src="../scripts/Civil_Budget_Statement_1.js"></script>
<script type="text/javascript" src="../scripts/Un_Freeze_for_Consolidate.js"></script>
<script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>  
<script type="text/javascript">
function loadyear_month()
{
	 var today= new Date(); 
     var day=today.getDate();
     var month=today.getMonth();
     month=month+1;
     
     if(day<=9 && day>=1)
     day="0"+day;
     if(month<=9 && month>=1)
     month="0"+month;
     var year=today.getYear();
     if(year < 1900) year += 1900;
     var monthArray =new Array("January", "February", "March", 
               "April", "May", "June", "July", "August",
               "September", "October", "November", "December");
     document.frmCivil_Budget_Statement_1.txtCrea_date.value=day+"/"+month+"/"+year;
}
</script>


<%
	String s = request.getContextPath();
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
		                ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
		                //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
		    
		                Class.forName(strDriver.trim());
		                con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
		      }
		      catch(Exception e)
		      {
		        System.out.println("Exception in connection...."+e);
		      }
		     
		      
  %>
</head>
<body onLoad="load();loadyear_month();"	>
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><font size="4">Civil Budget Allocation </font><strong> - Statement by HO</strong> </div>
		</td>
	</tr>
</table>
  
<form name="frmCivil_Budget_Statement_1" id="frmCivil_Budget_Statement_1"
	method="POST" action="../../../../../Civil_Budget_Statement_Report" onsubmit="return checkNull();">
	<input type='hidden' name='RecordCount' id='RecordCount' value='0' /> 
	<input type='hidden' name='filter' id='filter' value='no' />
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">


	<tr class="table1" width="100%">
	 
      <td>
     Accounting Unit Code <font color="#ff2121">*</font> </td>
      <td><div align="left">
          <select size="1" name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode" tabindex="1">
		
          </select>
      </div>
      </td>
      </tr>
      <tr class="table1">
	 <td>
           Region Name
            <font color="#ff2121">*</font>
       </td><td>
        <div id="regiondiv1">
            <select size="1" name="txtRegionId" id="txtRegionId" tabindex="1" >
             <option value="0">--Select Region--</option>
              <%
              try{
                        ps=con.prepareStatement("select OFFICE_NAME,OFFICE_ID from COM_MST_OFFICES where OFFICE_LEVEL_ID in ('RN','HO') and office_status_id not in('CL','RD','NC')");
                        rs=ps.executeQuery();
                        while(rs.next())
                        {
                            out.println("<option value="+rs.getInt("OFFICE_ID")+">"+rs.getString("OFFICE_NAME")+"</option>");
                        }
                  }
              catch(Exception e)
                {
                    System.out.println(e);
                }
              %>
              </select>
          </div>
             
		      
		       </td>
	  </tr>
	
	
	<tr class="table1">
		<td width="30%"><div align="left">Financial Year <font color="#ff2121">*</font></div></td>
		<td width="70%"><div align="left">
		  <select name="cmbFinancialYear" id="cmbFinancialYear" onchange="loadstatname();">
		  <option value="">--Select Year--</option>
                    <%
					                        st=con.createStatement();
					                        rs=st.executeQuery("select financial_year from cash_book_control order by financial_year");
					                        while(rs.next())
					                        {
					                            out.println("<option value='"+rs.getString("financial_year")+"'>"+rs.getString("financial_year")+"</option>");
					                        }
                    				%>
		    </select>
		</div></td>
	</tr>
	
	<tr class="table1">
      <td>Statement Name<font color="#ff2121">*</font></td>
	  <td><label>
        <select name="cmbStatementName" id="cmbStatementName">
          <option value="">---Select---</option>
        </select>
      
		
</label></td>
	  </tr>
	  <tr class="table">
        <td align="left">Report Option:</td>
        <td colspan="3" align="left">
          <input type="radio" name="txtoption" id="txtoption" value="PDF"
                 checked="checked"></input>
          PDF
          <input type="radio" name="txtoption" id="txtoption" value="EXCEL"></input>
          Excel
          <input type="radio" name="txtoption" id="txtoption" value="HTML"></input>
          HTML
        </td>
      </tr>
	  <tr class="tdH1">
	  <td colspan="2">
		<div align="center">
			 <input type="submit" id="butGo" name="butGo" value="GO"/>
		<input type="button" name="butCan" id="butCan" value="EXIT" onClick="exitfun();" /></div>
		</td>
	  
	  </tr>
	
</table>
</div>


</form>
</body>
</html>