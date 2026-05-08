<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>WQS_InstrumentMaster</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js">
    </script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/WQS_InstrumentMasterJS.js"></script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"          media="screen"/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"          media="screen"/>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>        
    <script type="text/javascript" src="../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
  </head>
  <body onload="callServer('Get','null')">
  <form name="Instrument">
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
                                  //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                                  Class.forName(strDriver.trim());
                                  con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());             
                                  System.out.println("Connected THRO JSP");
                          }
                          catch(Exception e)
                          {
                                System.out.println(e.getMessage());
                          }
  %>
  <%
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
            <td colspan="2" class="tdH" align="center"><b>Instrument Master</b></td>
                   
       </tr> 
         <tr>
            <td class="table" align="left" width="49%">Lab<font color="Red">*</font></td>
            <td class="table" align="left" width="51%">
                <input type="text" name="lab" id="lab" value="<%=lb%>" size="40" disabled="true">
            </td>
        </tr>
        <tr>
            <td class="table" width="50%">Instrument Category<font color="Red">*</font></td>
            <td class="table" width="50%">
            <select name="category" id="category">
            <option value="">--Select Instrument--</option>
            <%
                 st=con.createStatement();
                 rs=st.executeQuery("Select INST_CATEGORY,INST_CATEGORY_SPEC from WQS_MST_INV_INSTRUMENT");
                 try
                 {
                 while(rs.next())
                 {
                    String catcode=rs.getString("INST_CATEGORY");
                    System.out.println(catcode);
                    String catdesc=rs.getString("INST_CATEGORY_SPEC");
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
        <tr id="divwork" style="display:none">
            <td class="table" width="50%">Instrument Code</td>
            <td class="table" width="50%">
            <input type=text name="icode" id="icode" onchange="checkAvail()" disabled></input>
            </td>
        </tr>  
        
        <tr>
            <td class="table" align="left" width="49%">Brand</td>
            <td class="table" align="left" width="51%">
                <input type="text" name="brand" id="brand" maxlength="50"></input>
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Instrument Type<font color="Red">*</font></td>
            <td class="table" align="left" width="51%">
                <input type=text name="type" id="type" maxlength="50" size="50"></input>
            </td>
        </tr>
        <tr>
            <td class="table" width="50%">Serial Number<font color="Red">*</font></td>
            <td class="table" width="50%">
                <input type=text name="sno" id="sno" maxlength="10"></input>
            </td>
        </tr>  
        
        <tr>
            <td class="table" align="left" width="49%">Make<font color="Red">*</font></td>
            <td class="table" align="left" width="51%">
                <input type="text" name="make" id="make" maxlength="50"></input>
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Model<font color="Red">*</font></td>
            <td class="table" align="left" width="51%">
                <input type=text name="model" id="model" maxlength="50"></input>
            </td>
        </tr>
        <tr>
            <td class="table" width="50%">Cost in Rs.<font color="Red">*</font></td>
            <td class="table" width="50%">
            <input type=text name="cost" id="cost" onkeypress="return filter_real(event,this,8,2)" maxlength="14"></input>
            </td>
        </tr>  
        
        <tr>
            <td class="table" align="left" width="49%">Acquired Date<font color="Red">*</font></td>
            <td class="table" align="left" width="51%">
                <input type="text" name="adate" id="adate" readonly="readonly"></input>
               <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.Instrument.adate);" alt="Show Calendar" id="pur_date_cal"></img>                                    
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Reference Code</td>
            <td class="table" align="left" width="51%">
                <input type=text name="rcode" id="rcode" maxlength="20" size="35"></input>
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Supplied by</td>
            <td class="table" align="left" width="51%">
                <input type=text name="supplied" id="supplied" maxlength="100" size="80"></input>
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Current Status<font color="Red">*</font></td>
            <td class="table" align="left" width="51%">
                <input type=text name="cstatus" id="cstatus" maxlength="1" size="1" onblur=checkVal()></input>&nbsp;<font color="Gray">(A-Active T-Terminate)</font>
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">File Reference Number<font color="Gray">&nbsp;&nbsp;&nbsp;&nbsp;[maxmimum 50 Characters]</font></td>
            <td class="table" align="left" width="51%">
                <input type="text" name="rno" id="rno" maxlength="50" size="50"></input>
            </td>
        </tr>
         <tr>
            <td class="table" align="left" width="49%">Remarks <font color="Gray">&nbsp;&nbsp;&nbsp;&nbsp;[maxmimum 100 Characters]</font></td>
            <td class="table" align="left" width="51%">
                <textarea rows="2" name="remarks" id="remarks" cols="20" onkeypress="return checklength(event,this)"></textarea>
            </td>
        </tr>
        <tr>
          <td colspan="2" class="tdH" align="center">
            <input type="button" name="CmdAdd" value="Add" id="CmdAdd" onclick="callServer('Add','null')"/>
            <input type="button" name="CmdDelete" value="Delete" id="CmdDelete" onclick="callServer('Delete','null')" disabled/>
            <input type="button" name="CmdUpdate" value="Update" id="CmdUpdate" onclick="callServer('Update','null')" disabled/>
            <input type="button" name="CmdClear" value="Clear" id="CmdClear" onclick="clearAll()"/>
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
            Instrument Category
          </th>        
          <th>
            Instrument Code
          </th>
          <th>
            Brand
          </th>
          <th>
            Type
          </th>
          <th>
            Sl.No
          </th>
          <th>
            Make
          </th>
          <th>
            Model
          </th>
          <th>
            Cost
          </th>
          <th>
            Acquired Date
          </th>
          <th>
            Reference Code
          </th>
          <th>
            Supplied by
          </th>
          <th>
            Current Status
          </th>
          <th>
            Remarks
          </th>
          <th>
            File Reference No.
          </th>
          </tr>
        <tbody id="tblList" class="table">
        </tbody>
        <tr class="table">
          <td colspan="16">&nbsp;</td>
          
        </tr>
        <tr class="tdH">
          <td colspan="16" align="center">
            <input type="button" name="exit" value="Exit" id="exit" onclick="javascript:self.close();"/>
          </td>
         
        </tr>
        </table>   
  </form>
  </body>
</html>