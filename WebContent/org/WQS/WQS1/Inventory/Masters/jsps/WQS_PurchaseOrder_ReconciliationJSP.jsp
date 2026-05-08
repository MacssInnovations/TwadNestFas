<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_PurchaseOrder_ReconciliationJSP</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js">
    </script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/WQS_PurchaseOrder_ReconciliationJS.js"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"          media="screen"/>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>        
    <script type="text/javascript" src="../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
  </head>
  <body onload="clearAll()">
   <form name="ReCancellation">
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
                 // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
            <td class="tdH" align="center" colspan="2"><b>Purchase Order Reconciliation Details</b></td>            
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Lab</td>
            <td class="table" align="left" width="51%">
                    <input type=text name="lab" id="lab" value="<%=lb%>" size="40" readonly="readonly">
            </td>
        </tr>
        <tr>
            <td class="table" align="center" width="49%" colspan="2">
                <input type="radio" name="sel" id="corder" onclick="ItemDisplay()"> Purchase Order &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="radio" name="sel" id="citem" onclick="ItemDisplay()"> Item
            </td>
        </tr>
 </table>
 <div id="divorder" style="display:none">
    <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
         <tr>
            <td class="tdH" align="left" width="100%" colspan="2">
                Purchase Order Details
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Purchase Order Number</td>
            <td class="table" align="left" width="51%">
                <select name="orderno" id="orderno" onchange="checkAvail()">
                    <option value="0">--Select Order Number--</option>
                    <%
                        st=con.createStatement();
                        rs=st.executeQuery("select distinct lab_code,purchase_order_no from wqs_item_pur_order_details where lab_code="+odidt+" and process_flow_status_id='FR' and current_status='A'");
                        while(rs.next())
                        {
                            String rno=rs.getString("PURCHASE_ORDER_NO");
                            out.println("<option value="+rno+">"+rno+"</option>");
                        }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Purchase Order Date</td>
            <td class="table" align="left" width="51%">
                <input type="text" name="orderdate" id="orderdate" readonly="readonly"></input>
                  <!--  <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.PurchaseEdit.orderdate);" alt="Show Calendar" id="pur_date_cal"></img>-->
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Supplier Id</td>
            <td class="table" align="left" width="51%">
                <input type="text" name="sid" id="sid" readonly="readonly"></input>
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Supplier Name</td>
            <td class="table" align="left" width="51%">
                <input type="text" name="sname" id="sname" readonly="readonly" size=70></input>
            </td>
        </tr>
    </table>
 </div>
 <div id="divcat" style="display:none">
    <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
            <tr>
                <td class="tdH" align="left" width="49%" colspan="2">
                    Purchase Order Item Details
                </td>
            </tr>
            <tr>
                <td class="table"  align="left" width="49%">Category</td>
                <td class="table" align="left" width="51%">
                    <select name="cat" id="cat" onchange="changeCat()">
                        <option value="0">--Select Category--</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="table"  align="left" width="49%">Item</td>
                <td class="table" align="left" width="51%">
                    <select name="item" id="item" onchange="changeItem()">
                        <option value="0">--Select Item--</option>
                    </select>
                </td>
            </tr>
            <tr id="divwork1" style="display:none">
                <td class="table" align="left" width="49%">Ordered Quantity</td>
                <td class="table" align="left" width="51%">
                    <input type="text" name="oqty" id="oqty" readonly="readonly"></input>&nbsp;&nbsp;<input type="text" name="uom" id="uom" readonly="readonly" size="3"></input>
                </td>
            </tr>
            <tr id="divwork2" style="display:none">
                <td class="table" align="left" width="49%">Received sofar</td>
                <td class="table" align="left" width="51%">
                    <input type="text" name="rqty" id="rqty" readonly="readonly"></input>&nbsp;&nbsp;<input type="text" name="ruom" id="ruom" readonly="readonly" size="3"></input>
                </td>
            </tr>          
        </table>
 </div>
 <table border="1" width="100%" align="center">
            <tr id="divwork3" style="display:none">
                <td class="table" align="left" width="49%">Remarks</td>
                <td class="table" align="left" width="51%">
                    <input type="text" name="rem" id="rem" size=70></input>
                </td>
            </tr>
            <tr class="tdH" align="center">
                <td colspan="2" align="center">
                    <input type="button" value="Cancel" name="validate" onclick="validatefun()" disabled="disabled"></input>
                    <input type="button" value="ClearAll" onclick="clearfun()"></input>
                    <input type="button" value="Exit" onclick="javascript:self.close();"/>
                </td>
            </tr>
    </table>
  </form>
  </body>
</html>