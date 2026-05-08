<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>WQS_SupplierItemJSP</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js">
    </script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/WQS_SupplierItemJS.js"></script>
  </head>
  <body onload="callServer('Get','null')">
  <form name=SupplierItem>
   <%
            Connection con=null;
            Statement st=null;
            ResultSet rs=null;
            PreparedStatement ps=null;
            try
              {
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
                  st=con.createStatement();
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
            <td colspan="2" class="tdH" align="center"><b>Supplier Item Details</b></td>
                   
       </tr> 
         <tr>
            <td class="table" width="50%">Lab <font color="Red">*</font></td>
            <td class="table" width="50%">
            <input type="text" name="lab" size="50" id="lab" value="<%=lb%>"/>
            </td>
         </tr>  
         <tr>
            <td class="table" width="50%">Supplier <font color="Red">*</font></td>
            <td class="table" width="50%">
            <select name="txtSupId" id="txtSupId">
            <option value="">--Select Supplier--</option>
            <%
              try
              {
                 rs=st.executeQuery("Select SUPPLIER_CODE,SUPPLIER_NAME from WQS_MST_INV_SUPPLIER WHERE LAB_CODE="+odidt);
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
            <td class="table" width="50%">Category <font color="Red">*</font></td>
            <td class="table" width="50%">
            <select name="txtCatCode" id="txtCatCode" onchange="changeCat()">
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
            <td class="table" width="50%">Item <font color="Red">*</font></td>
            <td class="table" width="50%">
            <select name="txtItemCode" id="txtItemCode" onchange="checkAvail()">
            <option>--Select Item--</option>
            </select>
            </td>
        </tr>  
           
        <tr>
          <td colspan="2" class="table" align="center">
            <input type="button" name="CmdAdd" value=" ADD " id="CmdAdd" onclick="callServer('Add','null')"/>
            <input type="button" name="CmdDelete" value="DELETE" id="CmdDelete" onclick="callServer('Delete','null')" disabled/>
            <input type="button" name="CmdClear" value="CLEAR" id="CmdClear" onclick="clearone()"/>
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
            Supplier Code
          </th>
         <th>
            Supplier Name
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
          </tr>
        <tbody id="tblList" class="table">
        </tbody>
        </table>   
  </form>
  </body>
</html>