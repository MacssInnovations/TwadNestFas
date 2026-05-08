<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Daily Report</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  </head>
  <body>
   <form name="PurchaseOrder" method="GET" action="../../../../../../WQS_CurrentDetailsServ">
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
          <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
                <tr>
                    <td class="tdH" align="center" colspan="2"><b>Daily Transaction Report</b></td>            
                </tr>
                <tr>
                    <td class="table" align="left" width="49%">Laboratory</td>
                    <td class="table" align="left" width="51%">
                        <input type=text name="lab" id="lab" size="35" value="<%=lb%>" readonly="readonly">
                    </td>
                </tr>
                <tr>
                <td class="table" align="left" width="49%">Category</td>
                <td class="table" align="left" width="51%">
                    <select name="cat" id="cat">
                    <option value="">--Select--</option>
                    <%
                        st=con.createStatement();
                        rs=st.executeQuery("select distinct major_category_code,major_category_desc from wqs_mst_inv_category");
                        while(rs.next())
                        {
                            String code=rs.getString("major_category_code");
                            String desc=rs.getString("major_category_desc");
                            if(!desc.equalsIgnoreCase("Instrument"))
                            {
                                out.println("<option value='"+code+"--"+desc+"'>"+desc+"</option>");
                            }
                        }
                    %>
                    </select>
                </td>
            </tr>
                <tr class="table">
                  <td width="51%">
                      Select Report Type
                  <td width="49%"><select name="cmbReportType" id="cmbReportType">
                  
                  <option value="PDF">PDF</option>
                  <option value="HTML">HTML</option>
                  <option value="EXCEL">EXCEL</option>
                  </select>
                  </td>
                </tr>
                <tr class="table">
                  <td colspan="2">&nbsp;</td>
                </tr>
                <tr class="tdH">
                  <td colspan="2" align="center">
                  <input type="Submit" value="Generate Report"/>
                  <input type="button" value="Exit" onclick="javascript:self.close();"/>
                  </td>
                </tr>        
                </table>
        </form>
  </body>
</html>