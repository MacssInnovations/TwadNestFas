<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>WQS_Miscellaneous_MasterJSP</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script language="javascript" src="../scripts/WQS_Miscellaneous_MasterJS.js" type="text/javascript">
    </script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"          media="screen"/>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>        
    <script type="text/javascript" src="../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
  </head>
 <body onload="load()">
  <form name="MasterMisc" action="">
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
    
      <table cellspacing="3" cellpadding="2" border="1" width="90%"
             align="center">
        
        <tr class="tdH">
          <td colspan="2" align="center">
            <font face="Times New Roman">
              <strong>Master Entry(Miscellaneous)</strong>
            </font></td>
          
        </tr>
         <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong>Lab Code</strong>
            </font></td>
           <td width="54%">
                <input type="text" name="LabCode" id="LabCode" size="35" value="<%=lb%>" disabled="true" >
            </td>
         </tr>
         <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong>Miscellaneous Code</strong>
            </font></td>
          <td width="54%">
            <select name="MisCode" id="MisCode" onchange="getNosAvailable()">
                    <option value="select">select</option>
            
                <%
                          try
                          {
                             st=con.createStatement();
                             rs=st.executeQuery("select MISCELLANEOUS_SPEC from WQS_MST_INV_MISCELLANEOUS ");
                          //System.out.println("selected");
                             while(rs.next())
                             {
                                out.print("<option value='"+rs.getString("MISCELLANEOUS_SPEC")+"'>"+rs.getString("MISCELLANEOUS_SPEC")+"</option>");
                                //System.out.println("ID's Added");
                             }
                                rs.close();
                            }
                          catch(Exception e)
                          {
                          out.println(e.getMessage());
                          }
                  %>
              </select>
            </td>
         </tr>
         
        <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong>Quantity Available</strong>
            </font></td>
          <td width="54%">
            <input type="text" name="qty" maxlength="12" size="10" id="qty" onkeypress="return isNumberKey(event,this)"/>
            <select name="uom" id="uom">
            <option value="">--Select--</option>
            <%
            try
                  {
                     System.out.println("uom");
                     st=con.createStatement();
                     rs=st.executeQuery("select UOM_DESC from WQS_MST_UOM ");
                  //System.out.println("selected");
                      while(rs.next())
                      {
                      out.print("<option value='"+rs.getString("UOM_DESC")+"'>"+rs.getString("UOM_DESC")+"</option>");
                      //System.out.println("ID's Added");
                      }
                      rs.close();
                      }
                      catch(Exception e)
                      {
                      out.println(e.getMessage());
                      }            
            %>
            </select>
          </td>
        </tr>
        <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong>Stock as on Date</strong>
            </font></td>
          <td width="54%">
            <input type="text" name="sdate" id="sdate"></input>
            <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.MasterMisc.sdate);" alt="Show Calendar" id="pur_date_cal"></img>                                    
          </td>
        </tr>
        <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong>Remarks</strong>
            </font></td>
          <td width="54%">
            <textarea rows="2" name="remarks" id="remarks" cols="20"></textarea>
          </td>
        </tr>
        <tr class="table">
          <td colspan="2" align="center" height="36">
            <input type="button"  value="  Add  " onclick="added()" id="add"/>
            <input type="button" value="  Update" onclick="upd()" id="update"/>
            <input type="button" value="  Delete  " onclick="del()" id="delet"/>
            <input type="button" value="  Clear  " onclick="clr()"/>
          </td>
          
        </tr>
        <tr class="table">
          <td colspan="2">&nbsp;</td>
          
        </tr>
        <tr class="tdH">
          <td colspan="2" align="center">
            <input type="button" name="exit" value="  Exit  " id="exit" onclick="close_win()"/>
          </td>
         
        </tr>
      </table>
    </form>
  </body>
</html>