<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <title>Supplement General Ledger Summary Report (Defunct) </title>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    
    <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script language="javascript" type="text/javascript" src="../scripts/SubLedgerMaster.js"></script>            
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
 
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>          
          
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>  
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript" src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Load_Accounting_office.js"></script>
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Supplement_Number_Check.js"></script>

                            
    <script type="text/javascript" language="javascript">
    function loadyear_month()
    {       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
         document.frmGeneralLedgerSystem.txtCB_Year.value=year
         document.frmGeneralLedgerSystem.txtCB_Month.value=month;        
        
        
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
  
  <body class="table" onload="loadyear_month();Suppl_Number_Check_allsup();">
  
  
  <form name="frmGeneralLedgerSystem"
                                                      method="POST"
                                                      action="../../../../../SJV_GL_Summary.kv"
                                                      onsubmit="return checknull()">
                                                      
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

            ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

            Class.forName(strDriver.trim());
            con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  int  cmbAcc_UnitCode=0,cmbOffice_code=0;

            
  %>
  <% 
    HttpSession session=request.getSession(false);
    UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();
    int  oid=0;
    String oname="";
    
        
    String FAS_SU="";   
    if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
         FAS_SU="YES";
    else
         FAS_SU="NO";
               
    
    
    
   
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
   
                 
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>                                                     
                                                      
                                                      
      <table cellspacing="2" cellpadding="3" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Supplement General Ledger Summary Report ( Defunct ) </strong>
            </div>
          </td>
        </tr>
      </table>
      <div align="center">
        <table cellspacing="1" cellpadding="2" border="0" width="100%">
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
                        tabindex="1" onchange="LoadOffice(this.value);">
                   
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
                          /* ps=con.prepareStatement("                      \n" +
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
                            "   )");*/
                            
                            ps=con.prepareStatement("SELECT OFFICE.ACCOUNTING_UNIT_ID, " +
													"ACC.ACCOUNTING_UNIT_NAME " +
													"FROM FAS_MST_ACCT_UNIT_OFFICES OFFICE, " +
													"FAS_MST_ACCT_UNITS acc " +
													"WHERE ACCOUNTING_FOR_OFFICE_ID IN " +
 													"(SELECT REDEPLOYED_OFFICE_ID AS red_closed_off " +
 													" FROM COM_OFFICE_REDEPLOYMENTS " +
 													" WHERE ACCT_TRF_UNIT_ID IN " +
   													" (SELECT ACCOUNTING_UNIT_ID " +
   													" FROM FAS_MST_ACCT_UNITS " +
   													" WHERE ACCOUNTING_UNIT_OFFICE_ID = ? " +
  													"  ) " +
 													" UNION ALL " +
 													" SELECT CLOSED_OFFICE_ID AS red_closed_off " +
  													"FROM COM_OFFICE_CLOSURE " +
 													" WHERE ACCT_TRF_UNIT_ID IN " +
  													"  (SELECT ACCOUNTING_UNIT_ID " +
   													" FROM FAS_MST_ACCT_UNITS " +
   													" WHERE ACCOUNTING_UNIT_OFFICE_ID = ? " +
   													" ) " +
 													" ) " +
													" AND OFFICE.CLOSED ='Y' " +
													" and OFFICE.ACCOUNTING_UNIT_ID=ACC.ACCOUNTING_UNIT_ID ");
                            
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
                <select size="1" name="cmbOffice_code" id="cmbOffice_code"
                        tabindex="2">
                 
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
          
         
            <tr >
               
                <td class="table">             
                  <div align="left">
                  Cash Book Year &amp; Month&nbsp;&nbsp; <font color="#ff2121">*</font>
                  </div>
                </td>
             <td>
               <div align="left">
               <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)" onblur="Suppl_Number_Check();">
             
              <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" onchange="Suppl_Number_Check();">
                <option value="3">March</option>
              </select>
               </div>
              </td>
           </tr>
          
           
           <tr align="left">
            <td class="table">
            <div align="left">Supplement Number</div>
            </td>
            <td>
              <div align="left">
              
               <!--
                <input type="text" name="txtsupplement_no" id="txtsupplement_no"
                       tabindex="3" maxlength="4" size="5"
                       onkeypress="return numbersonly(event)"></input>                 
                -->
                
                <select name="txtsupplement_no" id="txtsupplement_no" >
                   <option value="" >-- Select Suppl No. -- </option> 
                </select>       
                       
                </div>
            </td>
          </tr>                             
       
          
        </table>
      </div>
      
      
      <table align="center" cellspacing="3" cellpadding="2" border="0"
             width="100%">
        <tr >
          <td class="tdH">
            <div align="center">
              <input type="submit" value="Submit"></input>
               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
          </td>
        </tr>
      </table>
         
    </form>
  </body>
</html>