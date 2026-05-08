<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%
  Connection con=null;
  Statement st=null;
  ResultSet rs=null;
  %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Leave-typejsp</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/Leave_typejs.js">
    document.getElementsById("ltype").focus();
    </script>
  </head>
  <body>
  <form name=LeaveType>
  <div align="center">
  <table width="80%" border=1>
  <tr class="tdH">
    <td  colspan=2 align='center' height="35">
    <font size="5">Online System for TWAD Board</font></td>
  </tr>
  <tr class="tdH">
    <td colspan=2 align='center' height="32">
    <font size="4">
    <strong>Leave Types</strong></font></td>
  </tr>
  <tr class="table">
    <td width="39%" height="30">
    Leave Type Id</td>
    <td width="61%" height="30">
    <input type='text' name=ltype id=ltype onclick="toLoadSlNo()"></td>
  </tr>
  <tr class="table">
    <td width="39%" height="32">
    Leave Type Description</td>
    <td width="61%" height="32">
    <input type='text' name=typedesc id=typedesc></td>
  </tr>
  <tr class="table">
    <td width="39%" height="33">
    Leave Type Short Descirption</td>
    <td width="61%" height="33">
    <input type='text' name=shortdesc id=shortdesc></td>
  </tr>
  <tr class="table">
    <td width="39%" height="35">
    Remarks</td>
    <td width="61%" height="35">
    <textarea rows="2" name="rm" cols="20"></textarea></td>
  </tr>
  <tr class="tdH">
    <td height="31" width="10" colspan="2" align="center">
    <input type='button' name=add id=add value=Add onclick=callServer('Add',null)>
    <input type='button' name=delete id=delete value=Delete onclick=callServer('Delete',null)>
    <input type='button' name=update id=update value=Update onclick=callServer('Update',null)>
    <input type='button' name=clear id=clear value=Clear onclick=callServer('Clear',null)></td>
  </tr>
  <tr class="table">
                  <td colspan="2" height="31">
                  </td>
  </tr>  
  <tr class="tdH">
                  <td colspan="2" height="31" width="10" align="center">
                         <input type="button" value="Exit" name="cancel" id="cancel" width=50 onclick="javascript:self.close();">
                  </td>   
        </tr>
  </table>
  <br>
  <table name="mytable" id="mytable" border="2" width="80%" align="center">
            <tr class="tdH">
              <th>Select</th>
              <th>Leave Type</th>
              <th>Leave Type Description</th>
              <th>Leave Type Short Description</th>
              <th>Remarks</th>
            </tr>
            <tbody name="tbList" id="tbList" class="table">
              <%
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
   try
   {
    System.out.println("Connected");
    st=con.createStatement();
    rs=st.executeQuery("Select * from HRM_LEAVE_TYPES order by LEAVE_TYPE_ID");
    
    while(rs.next())
        {
        out.println("<tr align='left' id='"+rs.getInt("LEAVE_TYPE_ID")+"'>");
        int code=rs.getInt("LEAVE_TYPE_ID");
        String desc=rs.getString("LEAVE_TYPE_DESC");
        String sdesc=rs.getString("LEAVE_TYPE_DESC_SHORT");
        String remarks=rs.getString("REMARKS");
        out.println("<td align='center'><a href=\"javascript:callServer('load',"+code+")\">Edit</a></td>");        
        out.println("<td align='center'>"+code+"</td>");
        out.println("<td align='center'>"+desc+"</td>");
        out.println("<td align='center'>"+sdesc+"</td>");
        out.println("<td align='center'>"+remarks+"</td>");
        out.println("</tr align='center'>");
        }
        }catch(Exception e)
            {
            System.out.println("Error in getting values");
            }
     %>
   </tbody>
   </table>
   </div>
  </form>
  </body>
</html>