<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_Customer_MasterJSP</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/WQS_Customer_MasterJS.js"></script>
  </head>
  <body class="table" onload="clearAll()">
  <form name="CustomerDet" action="">
   <%
            Connection con=null;
            Statement st=null;
            ResultSet rs=null;
            PreparedStatement ps=null;
            String odt="",lb="",did="",dspec="",dist="",bcode="",bdesc=""; 
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
    <table cellspacing="3" cellpadding="2" border="1" width="100%"
             align="center">
        
        <tr class="tdH">
          <td colspan="2" align="center">
            <font face="Times New Roman">
              <strong>Customer Details</strong>
            </font></td>
          
        </tr>
       <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong> 
                Lab 
              </strong>
            </font><font color="Red">*</font></td>
          <td width="54%">
            <input type=text name="labcode" id="labcode" value="<%=lb%>" size="40" disabled="true">
          </td>
        </tr>
         <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong> Customer Type </strong><font color="Red">*</font>
            </font></td>
          <td width="54%">
            <select name="type" id="type" onchange="changeType()">
                <option value="">--Select Type--</option>
                 <%
                  try
                  {
                     System.out.println("chemical");
                     st=con.createStatement();
                     rs=st.executeQuery("select type_name from wqs_customer_type ");
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
        <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong> Customer Code</strong><font color="Red">*</font>
            </font></td>
          <td width="54%">
            <input type="text" name="cid" maxlength="10" size="13" id="cid"  onchange="getCustomer()" onclick="validatefun()" onkeypress="return isNumberKey(event,this)"/>
            <img src="../../../../../../images/c-lovi.gif" width="20" height="20" alt="CustomerList" onclick="servicepopup()" id="icon" style="visibility:hidden">
          </td>
        </tr>
        <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong> Customer Name</strong><font color="Red">*</font>
            </font></td>
          <td width="54%">
            <input type="text" name="name" maxlength="50" size="60" id="name" onkeyup="checkid()"/>
          </td>
        </tr>
             
        <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong>Address</strong>
            </font></td>
          <td width="54%">
            <textarea cols="40" rows="5" name="address" id="address" onclick="validateSubmit()" onkeypress="return checklength(event,this)"></textarea>
            <input type="hidden" name="addhidden" id="addhidden">
          </td>
        </tr>   
        <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong>Email Id</strong>
            </font></td>
          <td width="54%">
            <input type="text" name="email_id" id="email_id" size=50 maxlength=50 onclick="validateSubmit()" onchange="validmail()">
          </td>
        </tr>   
        <tr class="table">
          <td colspan="2" align="center" height="36">
            <input type="button"  value="  Add  " onclick="added()" id="add"/>
            <input type="button" value="  Update" onclick="upd()" id="update" />
            <input type="button" value="  Delete  " onclick="del()" id="delet" />
            <input type="button" value="  Clear  " onclick="clr()"/>
          </td>
          
        </tr>
      </table>
      <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
            <tr>
                <td align="left">
                Number Of Customer/ Page.&nbsp;&nbsp;&nbsp;&nbsp;
                    <select name="cmbpagination" onchange="changepagesize()">
                        <option value="5" selected="selected">
                        5
                        </option>
                        <option value="10">
                        10
                        </option>
                        <option value="15">
                        15
                        </option>
                        <option value="20">
                        20
                        </option>
                    </select>
                </td>
                <td align="right">
                    <B>Total Number Of Customer</b> &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="total" id="total" size="3" readonly></input>
                </td>
            </tr>
      </table>  
      <table align="center" border="1" width="100%">
      <tr class="tdTitle"><td colspan="7">Existing Details</td></tr>
      <tr class="tdH">
      <th width="10%">Edit</th><th width="10%">Lab</th><th width="10%">Customer Code</th><th width="20%">Customer Name</th>
      <th width="10%">Customer Type</th><th width="20%">Address</th><th width="20%">Email Address</th>
      </tr>
      <tbody id="tb" class="table">
      </tbody>
       <tr>
            <td colspan="7">
                      <table align="center"  cellspacing="3" cellpadding="2" border="0" width="100%">
                                    <tr >
                                        <td width="30%">
                                             <div align="left"> <div id="divpre" style="display:none"></div> </div>
                                        </td>
                                        <td width="40%">
                                             <div align="center"><table border="0"><tr><td> <div id="divcmbpage" style="display:none" ><font color="Black" size="2"><strong>
                                             Page&nbsp;&nbsp;<select name="cmbpage" id="cmbpage" onchange="changepage()"></select></strong></font></div></td><td>
                                             <div id="divpage"></div></td></tr></table> </div>
                                        </td>
                                        <td width="30%">
                                             <div align="right"> <div id="divnext" style="display:none"></div> </div>
                                        </td>
                                    </tr>
                      </table>
            </td>
        </tr>
        <tr class="tdH">
          <td colspan="7" align="center">
            <input type="button" name="exit" value="  Exit  " id="exit" onclick="javascript:self.close();"/>
          </td>
        </tr>
      </table>
    </form>
  </body>
</html>