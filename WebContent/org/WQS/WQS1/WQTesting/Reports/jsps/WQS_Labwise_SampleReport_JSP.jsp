<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_Labwise_SampleReport_JSP</title>
    <script language="javascript" type="text/javascript" src="../scripts/WQS_Labwise_SampleReport_JS.js"></script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>        
    <script type="text/javascript" src="../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
    </head>
  <body>
  <form name="labwise">
  <%
        Connection con=null;
        Statement st=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        try
          {
              //Class.forName("oracle.jdbc.OracleDriver");
              //con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","test","test");
              ResourceBundle rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
              String ConnectionString="";
    
              String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
              String strdsn=rb.getString("Config.DSN");
              String strhostname=rb.getString("Config.HOST_NAME");
              String strportno=rb.getString("Config.PORT_NUMBER");
              String strsid=rb.getString("Config.SID");
              String strdbusername=rb.getString("Config.USER_NAME");
              String strdbpassword=rb.getString("Config.PASSWORD");
              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
              //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
              Class.forName(strDriver.trim());
              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());             
              System.out.println("Connected THRO JSP");
          }
          catch(Exception e)
          {
          System.out.println(e.getMessage());
          }
           HttpSession session=request.getSession(false);
            UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
              
            System.out.println("user id::"+empProfile.getEmployeeId());
            int empid=empProfile.getEmployeeId();
            int  oid=0,odidt=0;
            String odt="",lb=""; 
            try
            {
       
                ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
                ps.setInt(1,empid);
                rs=ps.executeQuery();
                if(rs.next()) 
                {
                        oid=rs.getInt("OFFICE_ID");
                    
                }
                rs.close();
                ps.close();
                ps=con.prepareStatement("select LAB_CODE,LAB_DESC from WQS_MST_LAB where LAB_CODE=?");
                ps.setInt(1,oid);
                rs=ps.executeQuery();
                if(rs.next()) 
                {
                    odidt=Integer.parseInt(rs.getString("LAB_CODE"));
                    odt=rs.getString("LAB_DESC");
                    lb=odidt+"--"+odt;
                    System.out.println(lb);
                }
                rs.close();
                ps.close();
           
            }
            catch(Exception e)
            {
                System.out.println(e);
            }      
  %>
  <table cellspacing="2" cellpadding="3" border="1" width="85%" align="center">
     <tr>
            <td class="tdH" align="center" colspan="2"><b>Labwise Periodical Sample Report</b></td>            
     </tr>    
    <tr>
            <td class="table" align="center">
                Period From &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="fdate" id="fdate">
                <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.labwise.fdate);" alt="Show Calendar" id="pur_date_cal"></img>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                Period to &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="tdate" id="tdate">
                <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.labwise.tdate);" alt="Show Calendar" id="pur_date_cal"></img>
            </td>
    </tr>
     <tr>
            <td class="table" align="center">
                <input type="checkbox" name="allLab" id="allLab" checked="checked" onclick="changeLabState()"> All Lab &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <select name="lab" id="lab" disabled="disabled">
                    <option value="">-----Select Lab------</option>
                    <%
                        st=con.createStatement();
                        rs=st.executeQuery("Select lab_code,lab_desc from wqs_mst_lab order by lab_desc");
                        while(rs.next())
                        {
                                out.println("<option value="+rs.getInt("lab_code")+">"+rs.getString("lab_desc")+"</option>");
                        }     
                    %>
                </select>
            </td>
    </tr>
    <tr class="tdH">
          <td colspan="2" align="center">
            <input type="button" value="Generate Report" name="btsubmit" id="btsubmit" onclick="gen_report()">
            <input type="button" name="exit" value="  Exit  " id="exit" onclick="javascript:self.close()"/>
          </td>
         
   </tr>
  </table>
  </form>
  </body>
</html>