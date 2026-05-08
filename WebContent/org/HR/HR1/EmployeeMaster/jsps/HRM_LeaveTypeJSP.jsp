<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>HRM_LeaveTypeJSP</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/HRM_LeaveTypeJS.js">
    document.getElementsById("ltype").focus();
    </script>  
  </head>
  <body>
  <form name=LeaveType>
  <div align="center">
   <table width="80%" border=1>
    <tr class="tdH">
      <td  colspan=2 align='center' height="35">
       <font size="5">Online System for TWAD Board</font>
      </td>
    </tr>
    <tr class="tdH">
      <td colspan=2 align='center' height="32">
        Leave Types 
      </td>
    </tr>
    <tr class="table">
      <td>
        <div align="left">
          Leave Type Id 
        </div>
      </td>
      <td>
        <div align="left">
          <input type='text' name=ltype id=ltype onclick="toLoadSlNo()">
        </div> 
      </td>
    </tr>
    <tr class="table">
      <td>
        <div align="left">
           Leave Type Description
        </div>
      </td>
      <td>
        <div align="left">
           <input type='text' name=typedesc id=typedesc>
        </div>   
      </td>
    </tr>
    <tr class="table">
      <td>
        <div align="left">
           Leave Type Short Descirption
        </div>
      </td>
      <td>
        <div align="left">
           <input type='text' name=shortdesc id=shortdesc>
        </div>
      </td>
    </tr>
    <tr class="table">
      <td>
        <div align="left">
           Remarks
         </div>
      </td>
      <td>
        <div align="left">
           <textarea rows="2" name="rm" cols="20"></textarea>
        </div>
      </td>
    </tr>
    <tr class="tdH">
      <td colspan="2">
         <div align="center">       
            <input type='button' name=add id=add value=Add onclick=callServer('Add',null)>
            <input type='button' name=delete id=delete value=Delete onclick=callServer('Delete',null)>
            <input type='button' name=update id=update value=Update onclick=callServer('Update',null)>
            <input type='button' name=clear id=clear value=Clear onclick=callServer('Clear',null)>
         </div>
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
                Connection con=null;
                Statement st=null;
                ResultSet rs=null;
                System.out.println("hai");
    try
  {
 
            ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  try{
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