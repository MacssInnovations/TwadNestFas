<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Hrm_TransJoinRep</title>
    <script type="text/javascript"       src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/Hrm_TransJoinJS.js"></script>
  

    <link href="../../../../../css/Hrm_TransJoinCss.css" rel="stylesheet" media="screen"/>
  
  </head>
  <body>
  <form name="Hrm_TransJoinForm" id="Hrm_TransJoinForm">
  
  <%
  
    Connection con=null;
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

            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
					ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  %>
  
      <div align="center">
        <table cellspacing="3" cellpadding="2" border="1" width="65%">
          <tr class="table">
            <td colspan="2">
              <div align="center">
                Creation Of Joining Report Details
              </div>
            </td>
          </tr>
          <tr class="lightcolor">
            <td colspan="2">
              <div align="left">
                Office Details
              </div>
            </td>
          </tr>
          <tr class="table1">
            <td>
              <div align="left">
                Office ID
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOffId" id="txtOffId" maxlength="3"
                       size="43"  class="disab" onchange="return callServer1('Load','null')"/>
                       <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="jobpopup();">
              </div>
            </td>
          </tr>
          <tr class="table1">
            <td>
              <div align="left">
                Office Name
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOffName" id="txtOffName"
                       maxlength="30" size="43" class="disab"/>
              </div>
            </td>
          </tr>
          <tr class="table1">
            <td>
              <div align="left">
                Office Address
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOffAddr" id="txtOffAddr"
                       maxlength="30" size="43" class="disab"/>
              </div>
            </td>
          </tr>
          <tr class="lightcolor">
            <td colspan="2">
              <div align="left">
                Employee Details
              </div>
            </td>
          </tr>
          <tr class="table1">
            <td>
              <div align="left">
                Employee ID
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtEmpId" id="txtEmpId" maxlength="3" size="43"/>
             </div>
            </td>            
            </tr>
          <tr class="table1">
            <td>
              <div align="left">
                Employee Name
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="comEmpId" id="comEmpId" onchange="doFunction('dispEmp','null')">
                  <option>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- - - - Select - - - -
                  </option>
                  <%
                try
                {
                ps=con.prepareStatement("select * from HRM_MST_EMPLOYEES ORDER BY EMPLOYEE_ID");
                rs=ps.executeQuery();
                while(rs.next())
                {
                out.println("<option value="+rs.getInt("EMPLOYEE_ID")+">"+rs.getString("EMPLOYEE_NAME")+" --- "+rs.getInt("EMPLOYEE_ID")+"</option>");
                }
                }
                catch(Exception e)
                {
                System.out.println("Exception in emp id combo..."+e);
                }
                %>
                </select>
              </div>
            </td>
          </tr>
          <tr class="table1">
            <td>
              <div align="left">
                DOB
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtDOB" id="txtDOB" maxlength="10"
                       size="43"/>
              </div>
            </td>
          </tr>
          <tr class="table1">
            <td>
              <div align="left">
                GPF NO.
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtGpfNo" id="txtGpfNo" maxlength="10"
                       size="43"/>
              </div>
            </td>
          </tr>
          <tr class="lightcolor">
            <td colspan="2">
              <div align="left">
                Details Of Joining Report
              </div>
            </td>
          </tr>
          <tr class="table1">
            <td>
              <div align="left">
                Joining Report ID
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtJRId" id="txtJRId" maxlength="3"
                       size="43" class="disab"/>(System Generated)
                       <input type="text" name="txtJR" id="txtJR" maxlength="3"
                       size="3" class="disab"/>
              </div>
            </td>
          </tr>
          <tr class="table1">
            <td>
              <div align="left">
                Date Of Joining
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtDOJ" id="txtDOJ" maxlength="20"
                       size="43" onchange="return validate_date('Hrm_TransJoinForm','txtDOJ')" onFocus="javascript:vDateType='3'"  onBlur="DateFormat(this,this.value,event,true,'3')"/>
                       <input type="radio" name="radFNAN" id="radFNAN" onclick="doFunction('FN','null')"/>FN
                         &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="radFNAN" id="radFNAN" onclick="doFunction('AN','null')"/>AN
              </div>
            </td>
          </tr>
          <tr class="table1">
            <td>
              <div align="left">
                Designation
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="comDesign" id="comDesign" onclick="doFunction('x','null')">
                  <option>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- - - - Select - - - -
                  </option>
                  <%
                try
                {
                ps=con.prepareStatement("select DESIGNATION from HRM_MST_DESIGNATIONS");
                rs=ps.executeQuery();
                while(rs.next())
                {
                String des=rs.getString("DESIGNATION");
                out.println("<option value="+des+">"+rs.getString("DESIGNATION")+"</option>");
                }
                }
                catch(Exception e)
                {
                System.out.println("Exception in desig combo..."+e);
                }
                %>
                </select>
              </div>
            </td>
          </tr>
          <tr class="table1">
            <td>
              <div align="left">
                Post Counted Towards
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="comPostTow" id="comPostTow">
                <option>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- - - - Select - - - -
                </option>
                <%
                try
                {
                ps=con.prepareStatement("select POST_RANK_NAME from HRM_MST_POST_RANKS");
                rs=ps.executeQuery();
                while(rs.next())
                {
                String pos=rs.getString("POST_RANK_NAME");
                out.println("<option value="+pos+">"+rs.getString("POST_RANK_NAME")+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"</option>");
                }
                }
                catch(Exception e)
                {
                System.out.println("Exception in post rank combo..."+e);
                }
                %>
                </select>
              </div>
            </td>
          </tr>
          <tr class="table1">
            <td>
              <div align="left">
                Remarks
              </div>
            </td>
            <td>
              <div align="left">
                <textarea name="txtRemarks" id="txtRemarks" cols="34" rows="2" onFocus="doFunction('txtDOB','null')"></textarea>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td colspan="2">
              <div align="center">
                <input type="button" name="butSub" id="butSub" value="SUBMIT" onclick="doFunction('Add','null')"/>
                 &nbsp;&nbsp;&nbsp;
                <input type="button" name="butCan" id="butCan" value="CANCEL"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>