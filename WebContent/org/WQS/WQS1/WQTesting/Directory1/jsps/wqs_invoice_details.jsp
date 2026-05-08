<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,java.text.Format,java.text.SimpleDateFormat,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_SampleResultJSP</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script language="javascript" src="../scripts/wqs_invoice_details.js" type="text/javascript"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>        
    <script type="text/javascript" src="../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
  </head>
  <body class="table" onload="loadRecord()">
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
 <form name="invoice">
 <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
       <tr>
            <td colspan="2" class="tdH" align="center"><b>Invoice Details</b></td>                   
       </tr> 
       <tr>
            <td class="table" align="left" width="40%">Laboratory <font color="Red">*</font></td>
            <td class="table" align="left">
            <input type="text" name="lab" id="lab"  value="<%=lb%>" size="40" disabled="true">
            </td>
        </tr>
         <tr>
            <td class="table" width="40%">
                <div id="in1" style="display:none">Invoice Number <font color="Red">*</font></div></td>
            <td class="table">
                <div id="in2" style="display:none">
                 <input type="text" name="ino" id="ino" disabled="disabled" style="background-color:rgb(214,214,214)">
                </div>
            </td>
        </tr> 
        <tr>
            <td colspan="17" class="tdH">Customer Details</td>
        </tr>
        <tr>
            <td class="table" width="40%">Customer Type <font color="Red">*</font></td>
            <td class="table">
                    <select name=ctype id=ctype onchange="changecustomer()">
                        <option value="">--Select Customer Type--</option>
                        <%
                              try
                              {
                                     String sql="select type_id,type_name from wqs_customer_type";
                                     rs=stmt.executeQuery(sql);
                                     try
                                     {
                                          while(rs.next())
                                          {
                                                out.println("<option value='"+rs.getString("type_name")+"'>"+rs.getString("type_name")+"</option>");
                                          }
                                     }
                                     catch(SQLException e)
                                     {
                                          System.out.println("Exception in resultset:"+e);
                                     }
                                     finally
                                     {
                                          rs.close();
                                     }
                              }
                              catch(SQLException e)
                              { 
                                System.out.println("Exception :"+e);
                              }
                        %>
                        </select>
          </td>
        </tr>
        <tr>
            <td class="table" width="40%">Customer Id <font color="Red">*</font></td>
            <td class="table">
                    <input type="text" name=cid id=cid onkeypress="return checkId()" onchange="changeId()" size=10>
                    <img id="imgdiv" src="../../../../../../images/c-lovi.gif" width="20" height="20" alt="CustomerList" onclick="servicepopup()">
            </td>
        </tr>
        <tr>
            <td class="table" width="40%">Customer Name <font color="Red">*</font></td>
            <td class="table">
                    <input type="text" name=cname id=cname disabled="disabled" size=60 style="background-color:rgb(214,214,214)">
            </td>
        </tr>
        <tr>
            <td colspan="17" class="tdH">Invoice Details</td>
        </tr>
        <tr>
            <td class="table" width="40%">Invoice Date <font color="Red">*</font></td>
            <td class="table">
                <input type=text name=invoice_date id=invoice_date disabled="disabled" size=10>
                <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.invoice.invoice_date);" alt="Show Calendar" id="pur_date_cal"></img> 
          </td>
        </tr>
        <tr>
           <td class="table" width="40%">Invoice Amount <font color="Red">*</font></td>
           <td class="table">
                <input type=text name=amt id=amt onkeypress="return  numbersonly(event,this)">
            </td>
        </tr>       
        <tr>
            <td class="table" width="40%">Payment Mode</td>
            <td class="table">
	            <select id=paymode name=paymode onchange=chgBank()>
	                <option value="0">--Select Payment Mode--</option>
	                <option value="Cash">CASH</option>  
	                <option value="DD">DD</option>
	                <option value="Cheque">CHEQUE</option>
	                <option value="TCA">TCA</option>
	            </select>
            </td>
        </tr>
</table>      

<div id="chequedt" style="display:none">
<table align="center" border=1 width="100%">        
  <tr>
    <td class="table" width="40%">Cheque/DD/TCA Number</td>
    <td class="table">
        <input type=text name=che_num id=che_num onkeypress="return  numbersonly(event,this)"></td>
 </tr>
  <tr>
    <td class="table" width="40%">Date</td>
    <td class="table">
        <input type=text name=che_date id=che_date disabled="disabled" size=10>
        <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.invoice.che_date);" alt="Show Calendar" id="pur_date_cal"></img> 
  </td></tr>
  <tr>
    <td class="table" width="40%">Bank Name</td>
    <td class="table">
        <input type=text name=bank_name id=bank_name size=60></input>
    </td>
 </tr>
  <tr>
    <td class="table" width="40%">Drawee Branch</td>
    <td class="table">
        <input type=text name=branch id=branch size=60>
    </td>
</tr>
</table>
</div>
<table align="center" border=1 width="100%">
  <tr>
    <td class="table" width="40%">Customer Ref.No</td>
    <td>
        <input type=text name=rno id=rno size="55" maxlength="50"></input>
    </td>
  </tr>
  <tr>
    <td class="table" width="40%">Report Due Date <font color="Red">*</font></td>
    <td>
        <input type=text name=due_date id=due_date disabled="disabled" size=10>
        <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.invoice.due_date);" alt="Show Calendar" id="pur_date_cal"></img> 
    </td>
  </tr>
  <tr>
    <td class="table" width="40%">Total Samples <font color="Red">*</font><td>
        <input type=text name=tot_samples id=tot_samples onfocus="checkDueDate()" onkeypress="return numbersonly(event,this)" size=3 maxlength="3"></input></td>
  </tr>
  <tr>
     <td class="table" width="40%">Remarks (100 Characters)<td>
        <textarea cols="40" rows="3" name=rem id=rem onkeypress="return checklength(event,this)"></textarea>                    
     </td>
  </tr>
  <tr class="tdH">
          <td colspan="2" align="center">
            <input type="button" name="add" value="Add" id="add" onclick="callServer('Add')">
            <input type="button" name="del" value="Delete" id="del" onclick="callServer('Delete')"/>
            <input type="button" name="update" value="Update" id="update" onclick="callServer('Update')"/>
            <input type="button" name="clear" value="Clear" id="clear" onclick="clearAll()"/>
          </td>
    </tr>
 </table>
 <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
    <tr>
        <td align="left">
        Number Of Invoice/ Page.&nbsp;&nbsp;&nbsp;&nbsp;
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
            <B>Total Number Of Invoice</b> &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="total" id="total" size="3" readonly></input>
        </td>
    </tr>
</table>  
<table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%" align="center" class="table">
    <tr class="tdTitle"><td colspan="17">Existing Details</td></tr>
    <tr class="tdH">
          <th>
            Select
          </th>
          <th>
            Lab 
          </th>
          <th>
            Invoice Number
          </th>
          <th>
            Customer Type 
          </th>
          <th>
            Customer Id
          </th>
          <th>
            Invoice Date
          </th>
          <th>
            Invoice Amount
          </th>
          <th>
            Payment Mode
          </th>
          <th>
            Customer Ref.No
          </th>
          <th>
            Report Date
          </th>
          <th>
           Total Sample
          </th>
          <th>
            Remarks
          </th>
        </tr>
        <tbody id="tblList" class="table">
    </tbody>
    <tr>
    <td colspan="17">
      <table align="center"  cellspacing="3" cellpadding="2" border="0" width="100%" >
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
    <tr class="table">
          <td colspan="17">&nbsp;</td>
        </tr>
        <tr class="tdH">
          <td colspan="17" align="center">
            <input type="button" name="exit" value="  Exit  " id="exit" onclick="javascript:self.close();"/>
          </td>
    </tr>
  </table>
  </form>
  </body>
</html>