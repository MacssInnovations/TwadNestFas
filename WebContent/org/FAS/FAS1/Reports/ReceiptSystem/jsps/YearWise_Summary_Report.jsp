<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <link href="../../../../../../css/Sample3.css" rel='stylesheet'
          media='screen'/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
    
    <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
   
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
    
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                     window.close(); 
                }
    
                                 
                              
                
                ///////////////////////////////////////  Numbers only fields
                function numbersonly(e)
                {
                    var unicode=e.charCode? e.charCode : e.keyCode;
                   if(unicode==13)
                    {
                      //t.blur();
                      //return true;-------------------- for taking action when press ENTER
                    
                    }
                    if (unicode!=8 && unicode !=9  )
                    {
                        if (unicode<48 || unicode>57 ) 
                            return false 
                    }
                 }
    </script>
    <title>Yearwise_Summary_Report</title>
  </head>
<body class="table"
        onload="LoadAccountingUnitID('FOR_LIST_1')">
  <%
  
  Connection con=null;
  ResultSet rs=null,rs2=null;
  PreparedStatement ps=null,ps2=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
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
  <% 
        
        HttpSession session=request.getSession(false);
         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();
    
   
   %> 
    
  <form action="../../../../../../Yearwise_Summary_Report?" name=frmReport method=post> 
    <table width="930" border="1" align="center">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><strong>Yearwise_Summary_Report</strong></div>		</td>
	</tr>
	
	<tr class="table1">
		<td>
		<div align="left">Financial Year <font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="fin_year"
			id="fin_year" tabindex="2">
			<option value="">--Select Year--</option>
                    <%
					                        st=con.createStatement();
					                        rs=st.executeQuery("select financial_year from cash_book_control order by financial_year");
					                        while(rs.next())
					                        {
					                            out.println("<option value='"+rs.getString("financial_year")+"'>"+rs.getString("financial_year")+"</option>");
					                        }
                    				%>
			
		</select></div>
		</td>
	</tr>
	
	<tr class="table1">
		<td>
		<div align="left">Report Option<font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"></div>
		<input type=radio name=txtoption id=txtoption value="PDF" checked>PDF
                            <input type=radio name=txtoption id=txtoption value="EXCEL">Excel
                            <input type=radio name=txtoption id=txtoption value="HTML">HTML
		</td>
		
	</tr>
	<tr>
                        <td colspan=4 class="tdH" align="center">
                        <input type=submit value=Submit >
                        <input type=reset value=Clear>
                        <input type=button value=Exit onclick="self.close()">
                        </td>
                    </tr>
	
	</table>
  
  </form>
  </body>
</html>