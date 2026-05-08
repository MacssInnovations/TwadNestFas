<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>WQS_PurchaseOrder_DetailsJSP</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js">
    </script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/WQS_PurchaseOrder_DetailsJS.js"></script>
  </head>
  <body onload="callServer('Get','null')">
  <form name="PurchaseDetails">
  <%
    HttpSession session=request.getSession(false);
    UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();
  %>
   <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
       <tr>
            <td colspan="2" class="tdH" align="center"><b>Purchase Order Details</b></td>
                   
       </tr> 
         <tr>
            <td class="table" align="left" width="49%">Lab</td>
            <td class="table" align="left" width="51%">
                <select name=lab id=lab onchange="changeLab()">
                    <option value="0">-- Select Lab --</option>
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
                <select name="orderno" id="orderno">
                    <option value="">--Select--</option>
                </select>
            </td>
        </tr>
              
        <tr>
            <td class="table" width="50%">Category</td>
            <td class="table" width="50%">
            <select name="category" id="category" onchange="changeCat()">
            <option value="">--Select Category--</option>
            <%
                 st=con.createStatement();
                 rs=st.executeQuery("Select MAJOR_CATEGORY_CODE,MAJOR_CATEGORY_DESC from WQS_MST_INV_CATEGORY");
                 try
                 {
                 while(rs.next())
                 {
                    String catcode=rs.getString("MAJOR_CATEGORY_CODE");
                    System.out.println(catcode);
                    String catdesc=rs.getString("MAJOR_CATEGORY_DESC");
                    out.println("<option value='"+catcode+"--"+catdesc+"'>"+catcode+"--"+catdesc+"</option>");
                 }
                 }
                 catch(Exception e)
                 {
                    System.out.println("Err3:"+e.getMessage());
                 }
            %>
    
            </select>
            </td>
        </tr>  
        <tr>
            <td class="table" width="50%">Item</td>
            <td class="table" width="50%">
            <select name="item" id="item" onchange="checkAvail()">
            <option>--Select Item--</option>
            </select>
            </td>
        </tr>  
        
        <tr>
            <td class="table" align="left" width="49%">Qantity Ordered</td>
            <td class="table" align="left" width="51%">
                <input type="text" name="order" id="order"></input>
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Remarks</td>
            <td class="table" align="left" width="51%">
                <textarea rows="2" name="remarks" id="remarks" cols="20"></textarea>
            </td>
        </tr>
        <tr>
          <td colspan="2" class="table" align="center">
            <input type="button" name="CmdAdd" value="Add" id="CmdAdd" onclick="callServer('Add','null')"/>
            <input type="button" name="CmdDelete" value="Delete" id="CmdDelete" onclick="callServer('Delete','null')" disabled/>
            <input type="button" name="CmdUpdate" value="Update" id="CmdUpdate" onclick="callServer('Update','null')" disabled/>
            <input type="button" name="CmdClear" value="Clear" id="CmdClear" onclick="clearAll()"/>
          </td>
        </tr>
        
        <tr class="table">
          <td colspan="2">&nbsp;</td>
          
        </tr>
        <tr class="tdH">
          <td colspan="2" align="center">
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
          <th>
            Select
          </th>
          <th>
            Lab
          </th>
         <th>
            Purchase Order Number
          </th>        
          <th>
            Category 
          </th>
          <th>
            Item Code
          </th>
          <th>
            Item Description
          </th>
          <th>
            Qantity Ordered
          <th>
            Remarks
          </th>
          </tr>
        <tbody id="tblList" class="table">
        </tbody>
        </table>   
  </form>
  </body>
</html>