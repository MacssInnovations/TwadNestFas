<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Journal for Payment of Pending Bill</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
    
    
    <script language="javascript" type="text/javascript" src="../scripts/PaymentOfPendingBill.js"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script> 
    <script type="text/javascript" language="javascript">
     function loadyear_month()
        {
       
         var today= new Date();
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        /** Load Cash Book Month and Cash Book Year during Form Load */
        document.frmpendingbillReport.txtCB_Year.value=year;
        document.frmpendingbillReport.txtCB_Month.value=month;
                
        /** Load From Cash Book Month and From Cash Book Year during Form Load */
        document.frmpendingbillReport.txtCB_Year_from.value=year;
        document.frmpendingbillReport.txtCB_Month_from.value=month;
        
        /** Load To Cash Book Month and To Cash Book Year during Form Load */
        document.frmpendingbillReport.txtCB_Year_to.value=year;
        document.frmpendingbillReport.txtCB_Month_to.value=month;
        
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
  <body class="table" onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');" >
  <form name="frmpendingbillReport" id="frmpendingbillReport" method="POST" action="Payment_of_Pending_Bill">
 <!-- <input type="hidden" id="hdid" name="hdid" value="jrnl"> -->
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
              <strong>Payment of Pending Bill Details for Journal/Receipt  </strong>
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
                   <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1"  onchange="common_LoadOffice()"> 
                   <%
                      int unitid=0;
                      String unitname="";
                      try{
                       
                        if(oid==5000)
                        {
                            String getWing= "                              \n" +
                            " select                                       \n" + 
                            "    ACCOUNTING_UNIT_ID,                       \n" + 
                            "    ACCOUNTING_UNIT_NAME                      \n" + 
                            " from                                         \n" + 
                            "    FAS_MST_ACCT_UNITS                        \n" + 
                            " where                                        \n" + 
                            "    ACCOUNTING_UNIT_OFFICE_ID in              \n" + 
                            "    (                                         \n" + 
                            "      select                                  \n" + 
                            "          REDEPLOYED_OFFICE_ID                \n" + 
                            "      from                                    \n" + 
                            "          COM_OFFICE_REDEPLOYMENTS            \n" + 
                            "      where                                   \n" + 
                            "          ACCT_TRF_UNIT_ID in                 \n" + 
                            "             (                                \n" + 
                            "                select                        \n" + 
                            "                    ACCOUNTING_UNIT_ID        \n" + 
                            "                from                          \n" + 
                            "                    FAS_MST_ACCT_UNITS        \n" + 
                            "                where                         \n" + 
                            "                    ACCOUNTING_UNIT_OFFICE_ID  = ?                                                                               \n" + 
                            "                and OFFICE_WING_SINO = ( select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)  \n" +   
                            "             )                                \n" + 
                            "       union all                              \n" + 
                            "                                              \n" + 
                            "       select                                 \n" + 
                            "        CLOSED_OFFICE_ID  as red_closed_off   \n" +            
                            "       from                                   \n" +  
                            "          COM_OFFICE_CLOSURE                  \n" +  
                            "       where                                  \n" + 
                            "          ACCT_TRF_UNIT_ID in                 \n" + 
                            "             (                                \n" + 
                            "                select                        \n" + 
                            "                   ACCOUNTING_UNIT_ID         \n" + 
                            "                from                          \n" + 
                            "                    FAS_MST_ACCT_UNITS        \n" + 
                            "                 where                        \n" + 
                            "                     ACCOUNTING_UNIT_OFFICE_ID  = ?   \n" +                                                                              
                            "                 and OFFICE_WING_SINO = ( select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=? )   \n" + 
                            "             )                                                  \n" + 
                            "   ) ";
                          
                            ps=con.prepareStatement(getWing);
                            ps.setInt(1,oid);
                            ps.setInt(2,empid);
                            ps.setInt(3,oid);
                            ps.setInt(4,oid);
                            ps.setInt(5,empid);
                            ps.setInt(6,oid);
                            
                            rs=ps.executeQuery();
                          
                            while(rs.next())
                            {
                               out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                               unitid=rs.getInt("ACCOUNTING_UNIT_ID");                              
                            }                         
                              
                            ps.close();
                            rs.close();
                       }
                       else  
                       {
                            ps=con.prepareStatement("                      \n" +
                            "select                                        \n" + 
                            "    ACCOUNTING_UNIT_ID,                       \n" + 
                            "    ACCOUNTING_UNIT_NAME                      \n" + 
                            "from                                          \n" + 
                            "    FAS_MST_ACCT_UNITS                        \n" + 
                            "where                                         \n" + 
                            "    ACCOUNTING_UNIT_OFFICE_ID in              \n" + 
                            "    (                                         \n" + 
                            "      select                                  \n" + 
                            "          REDEPLOYED_OFFICE_ID as red_closed_off        \n" + 
                            "      from                                    \n" + 
                            "          COM_OFFICE_REDEPLOYMENTS            \n" + 
                            "      where                                   \n" + 
                            "          ACCT_TRF_UNIT_ID in                 \n" + 
                            "             (                                \n" + 
                            "                select                        \n" + 
                            "                    ACCOUNTING_UNIT_ID        \n" + 
                            "                from                          \n" + 
                            "                    FAS_MST_ACCT_UNITS        \n" + 
                            "                where                         \n" + 
                            "                    ACCOUNTING_UNIT_OFFICE_ID  = ?       \n" + 
                            "             )                                \n" + 
                            "       union all                              \n" + 
                            "                                              \n" + 
                            "       select                                 \n" + 
                            "        CLOSED_OFFICE_ID  as red_closed_off   \n" +            
                            "       from                                   \n" +  
                            "          COM_OFFICE_CLOSURE                  \n" +  
                            "       where                                  \n" + 
                            "          ACCT_TRF_UNIT_ID in                 \n" + 
                            "             (                                \n" + 
                            "                select                        \n" + 
                            "                   ACCOUNTING_UNIT_ID         \n" + 
                            "                from                          \n" + 
                            "                    FAS_MST_ACCT_UNITS        \n" + 
                            "                 where                        \n" + 
                            "                     ACCOUNTING_UNIT_OFFICE_ID  = ?   \n" +                                                                              
                            "             )                                \n" + 
                            "   )");
                            
                            ps.setInt(1,oid);
                            ps.setInt(2,oid);
                            
                            rs=ps.executeQuery();
                            while(rs.next())
                            {                                  
                                out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                            }
                            ps.close();
                            rs.close();
                       }
                      }
                      catch(Exception e)
                      {
                            System.out.println(e);
                      }
                      %>
                      </select>
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
                  <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                      
                 <%
                   System.out.println("here");
                   System.out.println(oid+"  " +oname);
                try
                {
                    if(oid==5000)
                    {                     
                 String sql=" select                                 \n" + 
                "                REDEPLOYED_OFFICE_ID,  \n" + 
                "                ( select office_short_name from com_mst_offices where office_id = REDEPLOYED_OFFICE_ID) as office_desc     \n" + 
                "            from                                   \n" + 
                "                COM_OFFICE_REDEPLOYMENTS           \n" + 
                "            where                                  \n" + 
                "                ACCT_TRF_UNIT_ID in                \n" + 
                "                   (                               \n" + 
                "                      select                       \n" + 
                "                          ACCOUNTING_UNIT_ID       \n" + 
                "                      from                         \n" + 
                "                          FAS_MST_ACCT_UNITS       \n" + 
                "                      where                        \n" + 
                "                          ACCOUNTING_UNIT_OFFICE_ID = ?   \n" + 
                "                      and OFFICE_WING_SINO = ( select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id= ? and office_id = ? ) \n" + 
                "                   )\n    ";
                 
                        ps=con.prepareStatement(sql);                       
                        ps.setInt(1,oid);
                        ps.setInt(2,empid);
                        ps.setInt(3,oid);
                        rs=ps.executeQuery();                        
                        while(rs.next())
                        {   
                           out.println("<option value="+rs.getInt("REDEPLOYED_OFFICE_ID")+">"+rs.getString("office_desc")+"</option>");
                        }
                    }
                    else
                    {
                    
                     String sql=" select                                 \n" + 
                "                REDEPLOYED_OFFICE_ID,  \n" + 
                "                ( select office_short_name from com_mst_offices where office_id = REDEPLOYED_OFFICE_ID) as office_desc     \n" + 
                "            from                                   \n" + 
                "                COM_OFFICE_REDEPLOYMENTS           \n" + 
                "            where                                  \n" + 
                "                ACCT_TRF_UNIT_ID in                \n" + 
                "                   (                               \n" + 
                "                      select                       \n" + 
                "                          ACCOUNTING_UNIT_ID       \n" + 
                "                      from                         \n" + 
                "                          FAS_MST_ACCT_UNITS       \n" + 
                "                      where                        \n" + 
                "                          ACCOUNTING_UNIT_OFFICE_ID = ?   \n" +                
                "                   )                                      \n" ;
                 
                        ps=con.prepareStatement(sql);
                        ps.setInt(1,oid);
                        rs=ps.executeQuery();                        
                        while(rs.next())
                        {   
                           out.println("<option value="+rs.getInt("REDEPLOYED_OFFICE_ID")+">"+rs.getString("office_desc")+"</option>");
                        }
                    }                    
                } 
                catch(Exception e)
                {
                   System.out.println("Exception in Office combo..."+e);
                }
                finally
                {
                   rs.close();
                   ps.close();
                }  
                %>
                    </select>
                  </div>
                </td>
              </tr>
            <tr align="left">
           <td class="table">
              <div align="left">
                 Cash Book Year &amp; Month <font color="#ff2121">*</font>
                 </td>
              </div>
       
          <td>
                            
          <div id="particular" name="particular">
            
          Year 
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
          Month 
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
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
          
          </div>
          
          <div id="more" name="more" style="display:none">
          
          From   
          <input type="text" name="txtCB_Year_from" id="txtCB_Year_from" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month_from"  id="txtCB_Month_from" tabindex="4" >
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
          To  
          <input type="text" name="txtCB_Year_to" id="txtCB_Year_to" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month_to"  id="txtCB_Month_to" tabindex="4" >
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
          </div>      
           
          </td>
        </tr>
        <!--
         <tr>
          <td align="left">Select Specific Account Head Code:<font color="#ff2121">*</font></td>
          <td align="left">
                            <input type=radio id="SpecificAHC" name="SpecificAHC" value="All" checked onclick="AccHead(this.value)"> All
                            <input type=radio id="SpecificAHC" name="SpecificAHC" value="Specific" onclick="AccHead(this.value)">Specific
                            <div id="acchead" name="acchead" style="display:none">
                            <input type="text" name="txtAcc_HeadCode" id="txtAcc_HeadCode" size=10 maxlength="8" onkeypress="return numbersonly(event)">
                            <img src="../../../../../../images/c-lovi.gif" alt="AccountHeadList" onclick="AccHeadpopup();"  height="24" width="24"></img>
                            </div>
                            
          </td>
          </tr>
        -->
        <tr>
        <td align="left">
        Report Type:
        </td>
        <td colspan="3" align="left">
          
                            <input type=radio name=hdid id=hdid value="jrnl" checked >Journal
                            <input type=radio name=hdid id=hdid value="rcpt">Receipt
                            <!-- <input type="hidden" id="hdid" name="hdid" value="jrnl"> -->
                        
        </td>
        </tr>
        
        <tr>
                        <td align="left">
                            Report Option:
                        </td>
                        <td colspan="3" align="left">
                            <input type=radio name=txtoption id=txtoption value="PDF" checked>PDF
                            <input type=radio name=txtoption id=txtoption value="EXCEL">Excel
                            <input type=radio name=txtoption id=txtoption value="HTML">HTML
                        </td>
                        
                    </tr>
</table>
          </div>
          <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
  
      <tr class="tdH">
      <td>
          <div align="center">
          <input type="button"  value="Submit" id="sum" name="sum" onclick="printFunc('<%=s%>');" >
          <input type="button" name="butCan" id="butCan" value="CANCEL" onClick="refresh();" />
          <input type="button" id="cmdcancel" name="cancel" value="Exit" onclick="closeWindow()">
       </div>
      </td>
      </tr>
      
      </table>
      </form>
  </body>
</html>