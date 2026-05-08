<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>WQS_ItemReceiptJSP</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/WQS_ItemReceiptJS.js"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"          media="screen"/>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>        
    <script type="text/javascript" src="../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
  </head>
  <body onload="clearAll()">
  <form name="ItemReceipt" method="POST" action="../../../../../../WQS_ItemReceipt" onsubmit="return nullValue()">
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
   <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
       <tr>
            <td colspan="2" class="tdH" align="center"><b>Receipt Details</b></td>
                   
       </tr> 
         <tr>
            <td class="table" align="left" width="49%">Lab</td>
            <td class="table" align="left" width="51%">
                <input type="text" name="lab" id="lab" value="<%=lb%>" readonly="readonly" size="35"></input>
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Purchase Order Number</td>
            <td class="table" align="left" width="51%">
                <select name="orderno" id="orderno" onchange="changeOrder()">
                    <option value="0">--Select--</option>
                    <%
                    try
                    {
                        ps=con.prepareStatement("select distinct(PURCHASE_ORDER_NO) from wqs_item_pur_order_details where LAB_CODE=? and CURRENT_STATUS='A' and MAJOR_CATEGORY_CODE!='INS' and PROCESS_FLOW_STATUS_ID=?");
                        ps.setInt(1,odidt);
                        ps.setString(2,"FR");
                        System.out.println(odidt);
                        rs=ps.executeQuery();
                        try
                        {
                            while(rs.next())
                            {
                                String rno=rs.getString("PURCHASE_ORDER_NO");
                                out.println("<option value="+rno+">"+rno+"</option>");
                            }
                        }
                        catch(Exception e)
                        {
                            System.out.println("Err in changeLab1:"+e.getMessage());
                        }
                    }
                    catch(Exception e) 
                    {
                        System.out.println("Err in changeLab2:"+e.getMessage());    
                    }
                    %>
                </select>
            </td>
        </tr>              
        <tr>
            <td class="table" width="50%">Category</td>
            <td class="table" width="50%">
            <select name="category" id="category" onchange="changeCat()">
            <option value="0">--Select Category--</option>
            </select>
            </td>
        </tr>  
        <tr>
            <td class="table" width="50%">Item</td>
            <td class="table" width="50%">
            <select name="item" id="item" onchange="changeItem('item','null')">
            <option value="0">--Select Item--</option>
            </select>
            </td>
        </tr> 
         <tr id="divwork" style="display:none">
           <td class="table" width="50%">Brand</td>
           <td class="table" width="50%">
              <select name="bcode" id="bcode" onchange="changeItem('brand','null')">
                <option value="select">--Select Brand--</option>
                <%
                  try
                  {
                     st=con.createStatement();
                     rs=st.executeQuery("select brand_code from wqs_chemical_brand");
                     while(rs.next())
                     {
                      out.print("<option value='"+rs.getString(1)+"'>"+rs.getString(1)+"</option>");
                     }
                   }
                   catch(Exception e)
                   {
                      out.println(e.getMessage());
                   }
                %>
              </select>
            </td>
        </tr> 
        
        <tr>
            <td class="table" width="50%">Ordered Quantity</td>
            <td class="table" width="50%">
                <input type="text" name="qty" id="qty" readonly="readonly"></input>
                 <input type="text" name=ordereduom id=ordereduom disabled="disabled" size="3" style="visibility:hidden">
            </td>
        </tr> 
        
        <tr>
            <td class="table" width="50%">Received sofar</td>
            <td class="table" width="50%">
                <input type="text" name="stock" id="stock" readonly="readonly"></input>
                <input type="text" name=receiveduom id=receiveduom disabled="disabled" size="3" style="visibility:hidden">
            </td>
        </tr> 
        
         <tr>
            <td class="table" width="50%">Available Stock</td>
            <td class="table" width="50%">
                <input type="text" name="astock" id="astock" readonly="readonly"></input>
                <input type="text" name=availuom id=availuom disabled="disabled" size="3" style="visibility:hidden">
            </td>
        </tr> 
        
        <tr>
            <td class="table" align="left" width="49%">Date Of Receipt &nbsp;<font color="Gray">[dd/mm/yyyy]</font></td>
            <td class="table" align="left" width="51%">
                <input type="text" name="rdate" id="rdate" readonly="readonly"></input>
                <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.ItemReceipt.rdate);" alt="Show Calendar" id="pur_date_cal"></img>                                    
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Qantity Received</td>
            <td class="table" align="left" width="51%">
                <input type="text" name="received" id="received" onkeypress="return checklength(event,this)" onblur="checkqty()"></input>
                <input type="text" name="uom" id="uom" disabled="disabled" size="3" style="visibility:hidden">
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Excess Quantity Received (if avail)</td>
            <td class="table" align="left" width="51%">
                <input type="text" name="bonus" id="bonus" onkeypress="return isNumberKey(event,this)" maxlength="5"></input>
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Remarks</td>
            <td class="table" align="left" width="51%">
               <textarea rows="2" name="remarks" id="remarks" cols="20"></textarea>
            </td>
        </tr>
        
        <tr class="table">
          <td colspan="2">&nbsp;</td>
          
        </tr>
        <tr class="tdH">
          <td colspan="2" align="center">
            <input type="submit" name="submit" value="Submit" id="submit"/>
            <input type="button" name="exit" value="Cancel" id="exit" onclick="javascript:self.close();"/>
          </td>
        </tr>
    </table>
  </form>
  </body>
</html>