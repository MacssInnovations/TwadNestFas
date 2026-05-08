<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="Cache-Control" content="No-Cache"/>
    <title>GPF_Subscription</title>
  <!--  <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>        -->
    <script type="text/javascript"
            src="../../../../Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/Hrm_TransJoinJS_New.js"></script>
   <script type="text/javascript" src="../scripts/New_Trans_Joinedit_Script_New.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="../scripts/selectOfficeAttached.js">  </script>
    <script type="text/javascript" src="../scripts/GPF_Subscription.js">  </script>
  
    <style type="text/css">
.cal {font-family:verdana; font-size:12px;}
</style>
  </head>
  <body id="bodyid" onload="displayMonth();"><form name="Hrm_TransJoinForm"
                                                   id="Hrm_TransJoinForm">
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
    String oname="",oadd1="",oadd2="",ocity="",odist="",olid="",owid="",olevelname="",oldcode="";
    String olname="",olevelid=""; 
    String ownature="";
    ArrayList unit_name=new ArrayList();
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
            ps.close();
            ps=con.prepareStatement("select a.OFFICE_NAME,a.OFFICE_ADDRESS1,a.OFFICE_ADDRESS2,a.DISTRICT_CODE,a.CITY_TOWN_NAME,a.OFFICE_LEVEL_ID,a.PRIMARY_WORK_ID,b.DISTRICT_NAME,c.OFFICE_LEVEL_NAME from COM_MST_OFFICES a "+
            " left outer join com_mst_districts b on b.DISTRICT_CODE= a.DISTRICT_CODE "+
            " inner join com_mst_office_levels c on  c.office_level_id=a.office_level_id "+
            " where OFFICE_ID=?");
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
                 ps=con.prepareStatement("select accounting_unit_name from fas_mst_acct_units where accounting_unit_office_id=?" );
                 ps.setInt(1,oid);
                 results=ps.executeQuery();
                 if(results.next())
                 {
                 ps=con.prepareStatement("select accounting_unit_name from fas_mst_acct_units where accounting_unit_office_id=?" );
                 ps.setInt(1,oid);
                 results=ps.executeQuery();
                 while(results.next()) 
                 {
                    
                    unit_name.add(results.getString(1));
                                   
                     
                 }
                 }
                 else
                 {
                      out.println("<script language='javascript'>");
                     out.println("alert('You Dont Have a privilage to access this page')");
                     out.println("window.close()");
                    out.println("</script>");
                 }
                 
                
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
                <strong>GPF Subscription Details</strong>
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
                <textarea rows="4" cols="40" name="txtOffAddr" id="txtOffAddr"  readonly="readonly" class="disab">
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
                <select name="unit_name" id="unit_name" onchange="checkName();" >
                <option>--Select Account Unit --</option>
                  <%
              for(int j=0;j<unit_name.size();j++)
              { %>
                  <option value="<%=(String)unit_name.get(j)%>">  <%=(String)unit_name.get(j)%>    </option>
                  <%  }
              %>
                </select>
                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                <input type="text" name="oldcode" id="oldcode" maxlength="5" size="6" class="disab" readonly="readonly"/>
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
                <input type="text" name="txtEmpId" id="txtEmpId"  onkeypress="return  numbersonly1(event,this)"  maxlength="6" size="6" onchange="call('get');"/>
                 
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
                <input type="text" name="comEmpId" id="comEmpId" size="40" readonly="readonly" style="background-color: #ececec"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Designation</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="designation" id="designation" size="40" readonly="readonly" style="background-color: #ececec"/>              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">DOB</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtDOB" id="txtDOB" maxlength="10" readonly="readonly" size="10" style="background-color: #ececec"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">GPF NO.</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtGpfNo" id="txtGpfNo" maxlength="10" style="background-color: #ececec" readonly="readonly" size="10"/>
              </div>
            </td>
          </tr>
          <tr class="tdH">
            <td colspan="2">
              <div align="left">Impound Details</div>
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
                <select name="rel_year" id="rel_year" onchange="selectRelMonth();">
                <option value="<%=(year-5)%>">   <%=(year-5)%>  </option>
                <option value="<%=(year-4)%>">   <%=(year-4)%>  </option>
                <option value="<%=(year-3)%>">   <%=(year-3)%>  </option>
                <option value="<%=(year-2)%>">   <%=(year-2)%>  </option>
                  <option value="<%=(year-1)%>">   <%=(year-1)%>  </option>
                  <option value="<%=year %>" selected="selected"> <%=year %>     </option>
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
              <div align="left">Type Of Transaction</div>
            </td>
            <td>
              <div align="left">
                <input type="radio" name="trans" id="trans" value="CR"
                       checked="checked"/>
                 CR
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                <input type="radio" name="trans" id="trans" value="DB"/>
                 DB
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Subscription Amount</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="amount" id="amount" maxlength="10" onkeypress="return  numbersonly1(event,this)" onkeypress="return filter_real(event,this,7,2)"/>
              </div>
            </td>
          </tr>
           <tr class="table">
            <td>
              <div align="left">Refund(If Exist) &nbsp;&nbsp;&nbsp;&nbsp;  <input type="checkbox" name="refund" id="refund" onclick="showRefund();"/> </div>
            </td>
            <td>
              <div align="left" id="refund_div" > Refund Amount  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: <input type="text" name="ref_amount" id="ref_amount" maxlength="10" onkeypress="return  numbersonly1(event,this)"/><br/><br/>
                Refund Installment No  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: <input type="text" name="ref_no" id="ref_no" maxlength="10"/><br/><br/>
                Refund Total Installments &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: <input type="text" name="ref_total" id="ref_total" maxlength="10"/><br/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Arrear Recovery(If Exist) &nbsp;&nbsp;&nbsp;&nbsp;   <input type="checkbox" name="arrear" id="arrear" onClick="showArrear();"/> </div>
            </td>
            <td>
              <div align="left" id="arrear_div" > Recovery Amount &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: <input type="text" name="rec_amount" id="rec_amount" maxlength="10" onkeypress="return  numbersonly1(event,this)"/><br/><br/>
                Recovery Installment No &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: <input type="text" name="rec_no" id="rec_no" maxlength="10" /><br/><br/>
                Recovery Total Installments &nbsp;&nbsp;&nbsp;: <input type="text" name="rec_total" id="rec_total" maxlength="10" /><br/>
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
                       onclick="call('Add')"/>
                 
                <input type="button" name="update" id="update"
                       onclick="call('Update');" value="UPDATE"/>
                 
                <input type="button" name="delete1" id="delete1" value="DELETE"
                       onclick="call('Delete')"/>
                 
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
            <th>Type Of Transaction</th>
            <th>Subscription Amount</th>
            <th>Refund Amount</th>
            <th>Refund Installment</th>
            <th>Arrear Amount</th>
            <th>Arrear Installment</th>
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