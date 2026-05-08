<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title>Journal Report</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    
    <script language="javascript" type="text/javascript" src="../scripts/.js"></script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
      <link href='../../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
      
        <script type="text/javascript"
            src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script> 
    <script type="text/javascript" src="../scripts/Jornal_Report.js"></script> 
    <script type="text/javascript" language="javascript">
    
    </script>
     <script type="text/javascript" language="javascript">
     function sl_type(val)
     {
     }
     
     
     </script>
    <script type="text/javascript" language="javascript">
   
     function loadyear_month()
        {
       
         var today= new Date();
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.journal_report.txtCB_Year.value=year;
        document.journal_report.txtCB_Month.value=month;
                
       
       
        
      }
    </script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
    

      </head>
      
      <%
	String s = request.getContextPath();
%>
<%
	System.out.println(s);
%>
  <body onload="loadyear_month();LoadAccountingUnitID('FOR_LIST_1')"">
  <form name="journal_report" id="journal_report" method="POST" >
  <%
  Connection con=null;
  ResultSet rs=null,rs2=null;
  PreparedStatement ps=null,ps2=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
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
  <% 
        HttpSession session=request.getSession(false);
        UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();
    //int empid=9315;
    int  oid=0;
    int sl_code=0,year=0;
    String oname="";
   
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
            ps=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
            ps.setInt(1,oid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oname=results.getString("OFFICE_NAME");
                  }
            results.close();
            ps.close();
     /* */      
                 
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
   
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
              <strong>Journal Report</strong>
            </div>
        </td>
      </tr>
    </table>
     <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
                           <tr class="table">
            <td>
              <div align="left">
                Accounting Unit Code 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                        tabindex="1" onchange="common_LoadOffice(this.value);"></select>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Accounting For Office Code 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="cmbOffice_code" id="cmbOffice_code"
                        tabindex="2"></select>
              </div>
            </td>
          </tr>
           
 <tr align="left">
          <td class="table"> From Date &amp; To Date<font color="#ff2121">*</font></td>
        
             
                  <td> 
                  
                    <div align="left" id="bydate">
                  <input type="text" name="txtFrom_date" id="txtFrom_date"  tabindex="6"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.journal_report.txtFrom_date);"
                         alt="Show Calendar"></img>
           
                    <input type="text" name="txtTo_date" id="txtTo_date"  tabindex="7"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                     <img src="../../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.journal_report.txtTo_date);"
                         alt="Show Calendar"></img>
                 <input type="button" name="submitbtn" value="GO" id="submitbtn" tabindex="30" onclick="journal_Report_date()"/>        
           </div>
           
          </td> 
       
        </tr>
              <tr class="table">
                  <td class="table">
			         <div align="left">
			             Cash Book Year &amp; Month&nbsp;&nbsp;<font color="#ff2121">*</font>	                       
			          </div>
			      </td>
			      <td>
			      	<div align="left">
			      		<input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)" >
			         
			          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
			          <option value="0">---Select---</option>
			          <option value="1">January</option>
			          <option value="2">February</option>
			          <option value="3">March</option>
			          <option value="4">April</option>
			          <option value="5">May</option>
			          <option value="6">June</option>
			          <option value="7">July</option>
			          <option value="8">August</option>
			          <option value="9">September</option>
			          <option value="10">October</option>
			          <option value="11">November</option>
			          <option value="12">December</option>
			          </select>
			          <input type="button" name="submitbtn1" value="GO" id="submitbtn1" tabindex="30" onclick="journal_Report_month()"/>
			      	</div>
			      </td>
             </tr>
     <tr class="table">
                  <td class="table">
			         <div align="left">Sl_Type<font color="#ff2121">*</font></div></td>
            <td>
            <select size="1" name="sl_type" id="sl_type"  >
                      <option value="">--Select Type--</option>
                      <%
                        try
                        {
                     // year=Integer.parseInt(request.getParameter("txtCB_Year"));
                        // ps=con.prepareStatement("select SUB_LEDGER_CODE from FAS_JOURNAL_MASTER where cashbook_year=?" );
                       // ps.setInt(1,year);
                       //results=ps.executeQuery();
                // if(results.next()) 
                // {
                 //  sl_code=results.getInt("SUB_LEDGER_CODE");
               //  }
               //  results.close();
               //  ps.close();
               //ps=con.prepareStatement("select SUB_LEDGER_TYPE_DESC,SUB_LEDGER_TYPE_CODE from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE="+sl_code+" order by SUB_LEDGER_TYPE_CODE");
                       
                        ps=con.prepareStatement("select SUB_LEDGER_TYPE_DESC,SUB_LEDGER_TYPE_CODE from COM_MST_SL_TYPES  order by SUB_LEDGER_TYPE_CODE");
                        rs=ps.executeQuery();
                            while(rs.next())
                            {
                            out.println("<option value="+rs.getInt("SUB_LEDGER_TYPE_CODE")+">"+rs.getString("SUB_LEDGER_TYPE_DESC")+"</option>");
                            }
                        }
                        catch(Exception e)
                        {
                        System.out.println("Exception in Reason combo..."+e);
                        }
                        finally
                        {
                        rs.close();
                        ps.close();
                        }   
                      %>
                    </select>
            </td>
            </tr>
             </table>
        </div>
      <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >            
      	<tr class="tdH">
      	<td>
          <div align="center">
          
	            
       <input type="button" id="cmdcancel" name="cancel" value="Exit" onclick="closeWindow()"/>
      </div>
      </td>
      </tr>
      
      </table>
      </form>
  </body>
</html>