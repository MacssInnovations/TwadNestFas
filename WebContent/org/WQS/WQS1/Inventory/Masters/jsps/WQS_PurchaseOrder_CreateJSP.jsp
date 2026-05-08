<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_PurchaseOrder_CreateJSP</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js">
    </script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/WQS_PurchaseOrder_CreateJS.js"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"          media="screen"/>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>        
    <script type="text/javascript" src="../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
  </head>
  <body>
  <form name="PurchaseOrder" action="">
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
           
                    ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
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
  <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
        <tr>
            <td class="tdH" align="center" colspan="2"><b>Create Purchase Order</b></td>            
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Lab<font color="Red">*</font></td>
            <td class="table" align="left" width="51%">
                        <input type=text name="lab" id="lab" value="<%=lb%>" size="40" disabled="true">
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Purchase Order Number<font color="Red">*</font></td>
            <td class="table" align="left" width="51%">
                <input type="text" name="orderno" id="orderno" maxlength="15" size="20" onkeyup="capitalise()" onblur="checkAvail()"></input>
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Purchase Order Date<font color="Red">*</font></td>
            <td class="table" align="left" width="51%">
                <input type="text" name="orderdate" id="orderdate"></input>
                <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.PurchaseOrder.orderdate);" alt="Show Calendar" id="pur_date_cal"></img>                           
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Supplier<font color="Red">*</font></td>
            <td class="table" align="left" width="51%">
                <select name="sup" id="sup">
                    <option value="0">--Select Supplier--</option>
                    <%
                        try
                        {
                            st=con.createStatement();
                            rs=st.executeQuery("Select SUPPLIER_CODE,SUPPLIER_NAME from WQS_MST_INV_SUPPLIER where LAB_CODE="+odidt+" and CURRENT_STATUS='A'");
                            while(rs.next())
                            {
                                String supcode=rs.getString("SUPPLIER_CODE");
                                System.out.println(supcode);
                                String supname=rs.getString("SUPPLIER_NAME");
                                out.println("<option value='"+supcode+"--"+supname+"'>"+supname+"</option>");
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
            <td class="table" align="left" width="49%">Remarks</td>
            <td class="table" align="left" width="51%">
                <textarea rows="2" name="remarks" id="remarks" cols="20"></textarea>
            </td>
        </tr>
         
    </table>
    <table cellspacing="3" cellpadding="2" border="1" width="100%"
             align="center" >
        <tr>
            <td class="table" align="left"><b>Ordered Item Details</b></td>
        </tr>
    </table>
  <!--  <table cellspacing="3" cellpadding="2" border="1" width="100%"
             align="center" >
    </table>-->
    <table id="Existing" border="1" width="100%"
             align="center">
        <tr class="tdH">
            <th> Select </th>
            <th> S.No</th>
            <th> Category </th>
            <th> Item Code </th>
            <th> Item Description </th>
            <th> Quantity Ordered</th>
            <th> Unit of measurement</th>
            <th> Remarks</th>
        </tr>
        <tbody id="tblList" class="table">
        </tbody>
        
         <tr>
            <td class="table" colspan="8">&nbsp;</td>
        </tr>
        
        <tr>
            <td class="tdH" align="left" colspan="8">
                <input type="button" name="cmdAdd" id="cmdAdd" value="Add Item" onclick="servicepopup()"></input>
                <input type="button" name="cmdDelete" id="cmdDelete" value="Delete Item" onclick="delDet()"></input>
                <input type="button" name="cmdUpdate" id="cmdUpdate" value="Update Item" onclick="updateDet()"></input>
                <input type="button" name="cmdClear" id="cmdClear" value="Clear Item" onclick="clearDet()"></input>
            </td>
        </tr>
        <tr>
            <td class="table" colspan="8">&nbsp;</td>
        </tr>
        <tr>
            <td class="tdH" align="center" colspan="8"> 
                <input type="button" name="submit" value="Submit" id="submit" onclick="onSubmit();"/>
                <input type="button" name="exit" value="Exit" id="exit" onclick="javascript:self.close();"/>
            </td>
        </tr>
    </table>
  </form> 
  </body>
</html>