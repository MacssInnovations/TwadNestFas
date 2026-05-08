<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_SampleResult_CustomerRep</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script language="javascript" src="../scripts/WQS_SampleResult_CustomerRep.js" type="text/javascript"></script>
  </head>
  <body>
  <form name="SampleResult">
     <%
        Connection con=null;
        Statement stmt=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        String xml=null;
        String odt="",lb="",did="",dspec="",dist="",bcode="",bdesc="";   
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
                        stmt=con.createStatement();
                        con.clearWarnings();
                   }
                   catch(SQLException e)
                   {
                        System.out.println("Exception in creating statement:"+e);
                   }
                    stmt=con.createStatement();
       }
       catch(Exception e)
       {
            System.out.println("Exception in opening connection:"+e);
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
  <table border="1" width="100%" align="center" class="table">
    <tr>
        <td>
           <table cellspacing="2" cellpadding="3" border="0" width="100%"
                     align="center">
                <tr>
                    <td colspan="2" class="tdH" align="center"><b>Water Sample Result</b></td>
                </tr> 
                <tr>
                    <td class="table" align="left" width="49%"><font color="#808080">Lab</font></td>
                    <td class="table" align="left" width="51%">
                        <input type="text" name="lab" id="lab"  value="<%=lb%>" size="40" disabled="true">
                    </td>
                </tr>
                 <tr>
                    <td class="table" align="left" width="49%">Invoice Number</td>
                    <td class="table" align="left" width="51%">
                        <input type="text" name="ino" id="ino" size="25" onchange="changeIno()" maxlength="15">
                        <img src="../../../../../../images/c-lovi.gif" width="20" height="20" alt="InvoiceNumber List" onclick="servicepopup();">
                    </td>
                </tr>
                <tr>
                    <td colspan="2" class="tdH">Customer Details
                    </td>
                <tr>
                <tr>
                    <td class="table" align="left" width="49%"><font color="#808080">Customer Id</font></td>
                    <td class="table" align="left" width="51%">
                        <input type="text" name="cid" id="cid" size="25" onchange="changeCid()" maxlength="15" disabled="disabled" style="background-color:rgb(214,214,214)">
                    </td>
                </tr>
                 <tr>
                    <td class="table" align="left" width="49%"><font color="#808080">Customer Name</font></td>
                    <td class="table" align="left" width="51%">
                        <input type="text" name="cname" id="cname" size="40" disabled="disabled" style="background-color:rgb(214,214,214)">
                        <input type="hidden" name="ctype" id="ctype">
                    </td>
                </tr>
                <tr>
                    <td class="table" width="50%"><font color="#808080">Customer Reference Number</font></td>
                    <td class="table" width="50%">
                         <input type="text" name="rno" id="rno" size="70" maxlength="50" style="background-color:rgb(214,214,214)">
                    </td>
                </tr> 
                <tr>
                    <td colspan="2" class="tdH">Water Sample Details
                    </td>
                <tr>
                <tr>
                    <td class="table" width="50%">Office Reference Number</td>
                    <td class="table" width="50%">
                         <input type="text" name="off_rno" id="off_rno" size="100" maxlength="95">
                    </td>
                </tr> 
                <tr>
                    <td class="table" align="left" width="49%">Invoice Details</td>
                    <td class="table" align="left" width="51%">
                        <input type="text" name="det" id="det" size="85" maxlength="85">
                    </td>
                </tr> 
                <tr class="table">
                  <td width="30%">
                      Test Purpose
                  </td>
                  <td width="80%">
                      <select name="test_purpose" id="test_purpose" onchange="load_sample()">  
                      <option value="">--Select Parameter--</option>
                      </select>
                  </td>
               </tr>
                <tr>
                    <td class="table" width="50%">Sample Number</td>
                    <td class="table" align="left" width="49%">
                                <select name="sno" id="sno" style="width:45%" onclick="getSample()">
                                    <option value="0">Select Sample Number</option>
                                </select>
                                <div class="scroll" id="diviframeregion" style="width:45%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                     </td>
                </tr>
                
               <tr>
                    <td class="table" align="left" width="49%">Final Result/Conclusion </td>
                    <td class="table" align="left" width="51%">
                        <textarea rows="4" cols="70" name="res" id="res" onkeypress="return restrictLength(event,this)"></textarea>
                    </td>
                </tr>
                <tr>
                    <td class="table" align="left" width="49%">Report Size </td>
                    <td class="table" align="left" width="51%">
                        <input type="radio" name="rsize" id="rsize" checked="checked">A3 &nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name="rsize" id="rsize">A4
                    </td>
                </tr>
                <%
                    if(odidt==5000)
                    {
                    %>
                         <tr>
                            <td class="table" align="left" width="49%">Preprinted Sheet </td>
                            <td class="table" align="left" width="51%">
                                <input type="radio" name="printed" id="printed">Yes &nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name="printed" id="printed" checked="checked">No
                            </td>
                        </tr>
                    <%
                    }
                %>
                 <tr class="tdH">
                  <td colspan="2" align="center">
                        <input type="button" value="Generate Report" onclick="GenerateRep()">
                        <input type="button" value="Clear" id="clear" onclick="clearFun()"/>
                        <input type="button" value="Cancel" id="exit" onclick="javascript:self.close();"/>
                  </td>
                </tr>
              </table>
         </td>
     </tr>
  </table>
  </form>
  </body>
</html>