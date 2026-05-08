<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="Cache-Control" content="No-Cache"/>
    <title>Creation Of GPF Withdrawal Details</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../Library/scripts/checkDate.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript" src="../scripts/Emp_Relieval_Script.js"> </script>
    <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="../scripts/selectOfficeAttached.js">  </script>
    <script type="text/javascript" src="../scripts/GPF_Withdrawal_Details.js">  </script>
    <style type="text/css">
.cal {font-family:verdana; font-size:12px;}
</style>
  </head>
  <body id="bodyid" onload="displayMonth();">
  <form name="Hrm_GPFForm" id="Hrm_GPFForm">
      <%
  
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    
    
     Connection connection=null;

  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
    
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
      <!-- OFFICE DETAILS -->
      <% 
    HttpSession session=request.getSession(false);
    UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();
    int  oid=0,unit_id=0,i=0;
    int acc_for[]=new int[5];
    String oname="",oadd1="",oadd2="",ocity="",odist="",olid="",owid="",olevelname="",oldcode="",withdraw_code="",withdraw_desc="";
    String olname="",olevelid=""; 
    String ownature="",unit_name="";
    ArrayList unitid=new ArrayList();
    ArrayList unitname=new ArrayList();
    Calendar cal=Calendar.getInstance();
    int year=cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH) + 1;
    System.out.println("Current month & Year: "+month + " "+year );
  
    try
    {
           
            ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
            ps.setInt(1,empid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oid=results.getInt("OFFICE_ID");
                 
                 }
            results.close();
           // ps.close();
            ps=con.prepareStatement("select a.OFFICE_NAME,a.OFFICE_ADDRESS1,trim(a.OFFICE_ADDRESS2) as OFFICE_ADDRESS2,a.DISTRICT_CODE,a.CITY_TOWN_NAME,a.OFFICE_LEVEL_ID,a.PRIMARY_WORK_ID,b.DISTRICT_NAME,c.OFFICE_LEVEL_NAME from COM_MST_OFFICES a          left outer join com_mst_districts b on b.DISTRICT_CODE= a.DISTRICT_CODE inner join com_mst_office_levels c on  c.office_level_id=a.office_level_id where OFFICE_ID=?");
            ps.setInt(1,oid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    
                    oname=results.getString("OFFICE_NAME");
                    oadd1=results.getString("OFFICE_ADDRESS1");
                    oadd2=results.getString("OFFICE_ADDRESS2");
                    ocity=results.getString("CITY_TOWN_NAME");
                    odist=results.getString("DISTRICT_NAME");
                    olevelname=results.getString("OFFICE_LEVEL_NAME");
                    olevelid=results.getString("OFFICE_LEVEL_ID");
                   
                  }
                   
        }
        catch(SQLException e)
    {
        System.out.println(e);
    }
        
        try{
                 ps=con.prepareStatement("select accounting_unit_id,accounting_unit_name from fas_mst_acct_units where accounting_unit_office_id=?" );
                 ps.setInt(1,oid);
                 results=ps.executeQuery();
                 while(results.next()) 
                 {
                    unit_id=results.getInt(1);
                    unitid.add(results.getString(1));
                    unit_name=results.getString(2);
                    unitname.add(results.getString(2));
                    System.out.println("unit_name ::"+unit_name);
                    
                  }
               /*  else
                 {
                      out.println("<script language='javascript'>");
                     out.println("alert('You Dont Have a privilage to access this page')");
                     out.println("window.close()");
                    out.println("</script>");
                 }
                 ps=con.prepareStatement("select accounting_for_office_id from fas_mst_acct_unit_offices where accounting_unit_id=?" );
                 ps.setInt(1,unit_id);
                 results=ps.executeQuery();
                 while(results.next())
                 {
                    acc_for[i++]=results.getInt(1);
                    
                 }
                 for(int j=0;j<acc_for.length;j++)
                 {
                     ps=con.prepareStatement("select office_name from com_mst_offices where office_id=?" );
                     ps.setInt(1,acc_for[j]);
                     results=ps.executeQuery();
                        if(results.next()) 
                           {
                            al.add(results.getString(1));
                 
                              }
                    
                 }*/
            results.close();
            ps.close();
               
            }
    catch(SQLException e)
    {
        System.out.println(e);
    }
   
   %>
      <div align="center">
        <table cellspacing="3" cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <strong>GPF Withdrawal Details</strong>
              </div>
            </td>
          </tr>
          <tr class="tdH">
            <td colspan="2">
              <div align="left">Office Details</div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Office ID</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOffId" id="txtOffId" maxlength="6"
                       value="<%=oid%>" size="6" class="disab"
                       readonly="readonly"/>
                 
                <!--onchange="return callServer1('Load','null')" <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="jobpopup();">-->
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Office Name</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOffName" id="txtOffName"
                       value="<%=oname%>" maxlength="30" size="43" class="disab"
                       readonly="readonly"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Office Level</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOfflevel" id="txtOffLevel"
                       value="<%=olevelname%>" maxlength="30" size="43"
                       class="disab" readonly="readonly"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Office Address</div>
            </td>
            <td>
              <div align="left">
                <textarea rows="4" cols="40" name="txtOffAddr" id="txtOffAddr"
                          readonly="readonly" class="disab">
                  <%
                String s=null;
                if(oadd1!=null)
                {
                    s=oadd1;
                }
                if(oadd2!=null)
                {
                    s+="\n"+oadd2;
                }
                if(ocity!=null)
                {
                    s+="\n"+ocity;
                }
                if(odist!=null)
                {
                    s+="\n"+odist;
                }
                if(s!=null)
                            s.trim();
                    out.print(s);   
                                
                %>
                </textarea>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Account Unit</div>
            </td>
            <td>
              <div align="left">
                <select name="unit_name" id="unit_name"
                        onchange="callfun('UnitName');">
                  <option>--Select Account Unit --</option>
                  <%
                 for(int j=0;j<unitid.size();j++)
              { %>
                  <option value="<%=(String)unitid.get(j)%>">
                    <%=(String)unitname.get(j)%>
                  </option>
                  <%  }
              %>
                </select>
                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                <input type="text" name="Acc_unit_code" id="Acc_unit_code"
                       maxlength="5" size="6" class="disab"
                       readonly="readonly"/>
              </div>
            </td>
          </tr>
          <tr class="tdH">
            <td colspan="2">
              <div align="left">Employee Details</div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Employee ID 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtEmployeeid" id="txtEmployeeid"
                       onkeypress="return  numbersonly1(event,this)"
                       maxlength="6" size="6" onchange="callfun('emp');"/>
                 
                <img src="../../../../../images/c-lovi.gif" width="20"
                     height="20" alt="empList" onclick="servicepopup();"></img>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Employee Name</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="comEmpId" id="comEmpId" size="40"
                       readonly="readonly" style="background-color: #ececec"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Designation</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="designation" id="designation" size="40"
                       readonly="readonly" style="background-color: #ececec"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">DOB</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtDOB" id="txtDOB" maxlength="10"
                       readonly="readonly" size="10"
                       style="background-color: #ececec"/><!--<img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.Hrm_TransJoinForm.txtDOB);" alt="Show Calendar" ></img>-->
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">GPF NO.</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtGpfNo" id="txtGpfNo" maxlength="10"
                       style="background-color: #ececec" readonly="readonly"
                       size="10"/>
              </div>
            </td>
          </tr>
          <tr class="tdH">
            <td colspan="2">
              <div align="left">Withdrawal Details</div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Type Of Withdrawal</div>
            </td>
            <td>
              <div align="left">
                <select name="type_withdraw" id="type_withdraw">
                <option value=0>---Select Withdrawal Type---</option>
                  <%
            try{
                 ps=con.prepareStatement("select WITHDRAW_TYPE,WITHDRAW_type_DESC from HRM_GPF_WITHDRAWAL_TYPE " );
                 results=ps.executeQuery();
                while(results.next()) 
                 {
                  withdraw_code  =results.getString(1);
                  withdraw_desc  =results.getString(2);
                    System.out.println("withdraw code ::"+withdraw_code);
                     System.out.println("withdraw desc ::"+withdraw_desc);
                    %>
                     <option value="<%=withdraw_code%>">
                    <%=withdraw_desc%>
                  </option>
                 
                    <%
                  }
            }
            catch (Exception e)
            {
                      System.out.println("no withdrawal type");
            }%>
                  
                </select>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                A/C Month &amp; Year 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                Year : 
                <select name="ac_year" id="ac_year"
                        onchange="selectActMonth();">
                  <option value="<%=(year-1)%>">
                    <%=(year-1)%>
                  </option>
                  <option value="<%=year %>" selected="selected">
                    <%=year %>
                  </option>
                </select>
                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                Month : 
                <select name="ac_month" id="ac_month">
                  <option value="1">JAN</option>
                  <option value="2">FEB</option>
                  <option value="3">MAR</option>
                  <option value="4">APR</option>
                  <option value="5">MAY</option>
                  <option value="6">JUN</option>
                  <option value="7">JUL</option>
                  <option value="8">AUG</option>
                  <option value="9">SEP</option>
                  <option value="10">OCT</option>
                  <option value="11">NOV</option>
                  <option value="12">DEC</option>
                </select>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Relative Month &amp; Year</div>
            </td>
            <td>
              <div align="left">
                Year : 
                <select name="rel_year" id="rel_year"
                        onchange="selectRelMonth();">
                  <option value="<%=(year-1)%>">
                    <%=(year-1)%>
                  </option>
                  <option value="<%=year %>" selected="selected">
                    <%=year %>
                  </option>
                </select>
                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                Month : 
                <select name="rel_month" id="rel_month">
                  <option value="1">JAN</option>
                  <option value="2">FEB</option>
                  <option value="3">MAR</option>
                  <option value="4">APR</option>
                  <option value="5">MAY</option>
                  <option value="6">JUN</option>
                  <option value="7">JUL</option>
                  <option value="8">AUG</option>
                  <option value="9">SEP</option>
                  <option value="10">OCT</option>
                  <option value="11">NOV</option>
                  <option value="12">DEC</option>
                </select>
              </div>
            </td>
          </tr>
          <!---
          -->
          <tr class="table">
            <td>
              <div align="left">Withdrawal Amount</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="amount" id="amount" maxlength="10"
                       onkeypress="return filter_real(event,this,7,2)"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Date Of Payment</div>
            </td>
            <td colspan="2">
              <div align="left">
                <input type="text" class="cal" name="date" id="date"/>
                 
                <img src="../../../../../images/calendr3.gif"
                     onclick="showCalendarControl(document.getElementById('date'));"
                     alt="Show Calendar"></img>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Remarks</div>
            </td>
            <td>
              <div align="left">
                <textarea id="remarks" name="remarks" rows="4" cols="20"
                          style="algin:left;"></textarea>
              </div>
            </td>
          </tr>
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <input type="button" name="add" id="add" value="ADD"
                       onclick="callfun('Add')"/>
                 
                <input type="button" name="update" id="update"
                       onclick="callfun('Update');" value="UPDATE"/>
                 
                <input type="button" name="delete1" id="delete1" value="DELETE"
                       onclick="callfun('Delete')"/>
                 
                <input type="button" name="clear" id="clear" value="CLEAR ALL"
                       onclick="clearGPF();"/>
              </div>
            </td>
          </tr>
        </table>
         
        <table cellspacing="3" cellpadding="2" border="1" width="100%"
               align="center">
          <tr>
            <td class="table">Existing Details</td>
          </tr>
        </table>
         
        <table id="Existing" cellspacing="2" cellpadding="3" border="1"
               width="100%" align="center">
               
          <tr class="tdH">
            <th>Select</th>
            <th>A/C Month &amp; Year</th>
            <th>Relative Month &amp; Year</th>
            <th>Type Of Withdrawal</th>
            <th>Withdrawal Amount</th>
            <th>Date Of Payment</th>
          </tr>
         <tbody id="tlist" class="table">

          </tbody>
        </table>
         
        <table id="Exit" cellspacing="2" cellpadding="3" border="1" width="100%"
               align="center">
          <tr>
            <td class="tdH">
              <div align="center">
                <input type="button" name="CmdExit" value="EXIT" id="CmdExit"
                       onclick="Exit()" align="middle"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>