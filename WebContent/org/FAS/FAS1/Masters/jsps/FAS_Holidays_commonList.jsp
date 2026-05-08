<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title> List of Common Holidays </title>
    
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    </head>
  <body  bgcolor="rgb(255,255,225)">
   <%
  
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    PreparedStatement ps1=null;
    
     Connection connection=null;

  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
   ResultSet rs1=null; 
   try
  {
  
             ResourceBundle rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rb.getString("Config.DSN");
            String strhostname=rb.getString("Config.HOST_NAME");
            String strportno=rb.getString("Config.PORT_NUMBER");
            String strsid=rb.getString("Config.SID");
            String strdbusername=rb.getString("Config.USER_NAME");
            String strdbpassword=rb.getString("Config.PASSWORD");

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
      System.out.println("...............General Holiday LISTjsp started.................");
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
     
           int  cmbAcc_UnitCode=0,cmbOffice_code=0;
           int CB_Year=0;int CB_Month=0;
           try
            {
         	   CB_Year=Integer.parseInt(request.getParameter("CB_Year"));
                   CB_Month=Integer.parseInt(request.getParameter("CB_Month"));
           
         	   try {
                	cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                } catch (Exception e) {
                    System.out.println("Exception to catch cmbAcc_UnitCode ");
                }
                try {
                	cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
                } catch (Exception e) {
                    System.out.println("Exception to catch cmbOffice_code ");
                }
        System.out.println("1.unit********:"+cmbOffice_code);
        System.out.println("2.CB Year *****:"+CB_Year);
        System.out.println("3.CB Month****:"+CB_Month);
        System.out.println("4.Unit *****:"+cmbAcc_UnitCode);
   %>
  
 
        
  <form name="frmHoliday_commonList" id="frmHoliday_commonList">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
       <tr class="table">
          <td>
            <div align="center">
             <A1><b> List of Common Holidays  <A1> </b>
            </div>
          </td>
       </tr>
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
      		 <th>Year</th>
                <th>Month</th> 
                <th>Holiday Date</th>
                <th>Reason</th>
         </tr>
         <tbody id="tb" class="table" align="left">
          <%
                    String sql_que="select * from FAS_COMMON_HOLIDAYS_LIST where CASHBOOK_YEAR=? and CASHBOOK_MONTH=?";
                    ps=con.prepareStatement(sql_que);
                    ps.setInt(1, CB_Year);
                    ps.setInt(2, CB_Month);
                    rs=ps.executeQuery();
                   int cnt=0; 
                    while(rs.next())
                    {
                        cnt++;
                        out.println("<tr id='" + cnt + "'>");
                        out.println("<td>"+rs.getInt("CASHBOOK_YEAR")+"</td>");	
                        out.println("<td>"+rs.getInt("CASHBOOK_MONTH")+"</td>");	
                            java.sql.Date dd = rs.getDate("HOLIDAY_DATE");
                            java.text.SimpleDateFormat sdf =new java.text.SimpleDateFormat("dd/MM/yyyy");
                            String dispdate1 = sdf.format(dd);
                        out.println("<td>"+dispdate1+"</td>");	
                        out.println("<td>"+rs.getString("REASON")+"</td>");	
                     }
                   
                    if(cnt==0)
                     out.println("<tr><td>No data found<td><td></td><td></tr>");
                  }
                  catch(Exception e)
                  {
                    System.out.println("Exception in grid.."+e);
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
                     onclick=" self.close();"></input>
            </div>
          </td>
        </tr>
      </table> 
    </form>
   </body>
</html>

