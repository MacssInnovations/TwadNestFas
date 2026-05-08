<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>WQS_PurchaseOrderJSP</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js">
    </script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/WQS_PurchaseOrderJS.js"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"          media="screen"/>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>        
    <script type="text/javascript" src="../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
  </head>
  <body onload="callServer('Get','null')">
  <form name="PurchaseOrder">
  <%
    HttpSession session=request.getSession(false);
    UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();
  %>
  <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
        <tr>
            <td class="tdH" align="center" colspan="2"><b>Purchase Order Master</b></td>            
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Lab</td>
            <td class="table" align="left" width="51%">
                <select name=lab id=lab>
                    <option value="0">--Select Lab--</option>
                    <%
                        Connection con=null;
                        Statement st=null;
                        ResultSet rs=null;
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
                       try
                       {
                            st=con.createStatement();
                            con.clearWarnings();
                       }
                       catch(SQLException e)
                       {
                            System.out.println("Exception in creating statement:"+e);
                       }
          }
          catch(Exception e)
          {
             System.out.println("Exception in opening connection:"+e);
          }
                        try
                        {
                            rs=st.executeQuery("Select LAB_CODE,LAB_DESC from WQS_MST_LAB");
                            while(rs.next())
                            {
                                String lcode=rs.getString("LAB_CODE");
                                System.out.println(lcode);
                                String ldesc=rs.getString("LAB_DESC");
                                out.println("<option value='"+lcode+"--"+ldesc+"'>"+lcode+"--"+ldesc+"</option>");
                            }      
                        }
                        catch(Exception e)
                        {
                            System.out.println("Err2:"+e.getMessage());
                        }              
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Purchase Order Number</td>
            <td class="table" align="left" width="51%">
                <input type="text" name="orderno" id="orderno" onchange=checkAvail()></input>
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Purchase Order Date</td>
            <td class="table" align="left" width="51%">
                <input type="text" name="orderdate" id="orderdate" readonly="readonly"></input>
                 <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.PurchaseOrder.orderdate);" alt="Show Calendar" id="pur_date_cal"></img>                                    
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Supplier</td>
            <td class="table" align="left" width="51%">
                <select name="sup" id="sup" onchange="changeSup()">
                    <option value="0">--Select Supplier--</option>
                    <%
                        try
                        {
                            rs=st.executeQuery("Select SUPPLIER_CODE,SUPPLIER_NAME from WQS_MST_INV_SUPPLIER");
                            while(rs.next())
                            {
                                String supcode=rs.getString("SUPPLIER_CODE");
                                System.out.println(supcode);
                                String supname=rs.getString("SUPPLIER_NAME");
                                out.println("<option value='"+supcode+"--"+supname+"'>"+supcode+"--"+supname+"</option>");
                            }      
                        }
                        catch(Exception e)
                        {
                            System.out.println("Err2:"+e.getMessage());
                        }              
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Current Status</td>
            <td class="table" align="left" width="51%">
                <input type="text" name="status" id="status"></input>&nbsp;<font color="Gray">[A-Active  T-Terminate]</font>
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Remarks</td>
            <td class="table" align="left" width="51%">
                <textarea rows="2" name="remarks" id="remarks" cols="20"></textarea>
            </td>
        </tr>
        <tr>
            <td class="table" align="center" colspan="2">
                <input type="button" name="cmdAdd" id="cmdAdd" value="Add" onclick="callServer('Add','null')"></input>
                <input type="button" name="cmdDelete" id="cmdDelete" value="Delete" onclick="callServer('Delete','null')"></input>
                <input type="button" name="cmdUpdate" id="cmdUpdate" value="Update" onclick="callServer('Update','null')"></input>
                <input type="button" name="cmdClear" id="cmdClear" value="Clear" onclick="callServer('Clear','null')"></input>
            </td>
        </tr>
        <tr>
            <td class="table" colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td class="tdH" align="center" colspan="2"> 
                <input type="button" name="exit" value="Exit" id="exit" onclick="javascript:self.close();"/>
            </td>
        </tr>
    </table>
    <table cellspacing="3" cellpadding="2" border="1" width="100%"
             align="center" >
        <tr>
            <td class="table" align="center"><b>Existing Details</b></td>
        </tr>
    </table>
    <table id="Existing" border="1" width="100%"
             align="center">
        <tr class="tdH">
            <th> Select </th>
            <th> Lab</th>
            <th> Purchase Order Number </th>
            <th> Purchase Order Date </th>
            <th> Supplier Code</th>
            <th> Supplier Name</th>
            <th> Current Status </th>
            <th> Remarks </th>
        </tr>
        <tbody id="tblList" class="table">
        </tbody>
    </table>
  </form> 
 </body>
</html>