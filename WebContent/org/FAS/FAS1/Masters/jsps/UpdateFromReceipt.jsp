<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Update Receipt Values </title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/FAS/FAS1/Masters/scripts/UpdateFromReceipt.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
       <script type="text/javascript" src="../scripts/Receipt_Asset_NumericalAC.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
     <script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
           
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>  
    <script type="text/javascript" language="javascript">
         function foc()
         {
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
  <body onload="foc();LoadAccountingUnitID_new('LIST_ALL_UNITS');callServer('loadMajor');">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Update Receipt Values </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmupdateReceipt" id="frmupdateReceipt" method="post" action="../../../../../UpdateFromReceipt?command=updateTotally" onsubmit="return checkNull_verify()">
                  
  <%
  
      Connection con=null;
      ResultSet rs=null,rs2=null;
      PreparedStatement ps=null,ps2=null;
      ResultSet results=null;
      ResultSet results1=null;
      ResultSet results2=null;
       Statement statement=null;
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
     int bankid=0;
     
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
            System.out.println("off id.. emp id"+oid+".."+empid);     
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
   
    
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
                    <!--<input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>-->
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="loadOffice_REC();">
                     <!-- <option value="0"> Select Account Unit </option>-->
                          <%
                      int unitid=0;
                      String unitname="";
                     String FAS_SU="";
   
    if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
         FAS_SU="YES";
    else
         FAS_SU="NO";
                      try{
                     
                     
                            if(oid==5000)
                            {
                                 //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                                //ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?");
                                String getWing="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=? and OFFICE_WING_SINO=(select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)";
                                ps=con.prepareStatement(getWing);
                                ps.setInt(1,oid);
                                ps.setInt(2,empid);
                                ps.setInt(3,oid);
                                rs=ps.executeQuery();
                              
                                  if(rs.next())
                                  {
                                      out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                      unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                      
                                      System.out.println(".."+rs.getInt("ACCOUNTING_UNIT_ID"));
                                      System.out.println(".."+rs.getString("ACCOUNTING_UNIT_NAME"));
                                      System.out.println(".."+rs.getInt("OFFICE_WING_SINO"));
                                      ps.close();
                                      rs.close();
                                          if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
                                          { 
                                             String su="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID!=? order by ACCOUNTING_UNIT_NAME";
                                             ps=con.prepareStatement(su);
                                             ps.setInt(1,unitid);
                                             rs=ps.executeQuery();
                                              while(rs.next())
                                              {
                                                  out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                              }
                                          }
                                  }
                              ps.close();
                              rs.close();
                              }
                                  else
                                  {
                                    ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)");
                                    ps.setInt(1,oid);
                                    rs=ps.executeQuery();
                                      if(rs.next())
                                      {
                                          System.out.println(rs.getInt("ACCOUNTING_UNIT_ID"));
                                          System.out.println(rs.getString("ACCOUNTING_UNIT_NAME"));
                                          //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                                          out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                          unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                          ps.close();
                                          rs.close();
                                          if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
                                          { 
                                             String su="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID!=? order by ACCOUNTING_UNIT_NAME";
                                             ps=con.prepareStatement(su);
                                             ps.setInt(1,unitid);
                                             rs=ps.executeQuery();
                                              while(rs.next())
                                              {
                                                 out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                              }
                                          }
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
                  <!--  This is used to load details about login user( super user or oridinary user ) login and their unit id-->
                 <input type="hidden" name="FAS_SU_check" id="FAS_SU_check" value="<%=FAS_SU%>" />
                   <input type="hidden" name="unitid" id="unitid" value="<%=unitid%>" />
                   <!--/div-->
                   <!--end -->
                  
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                      
                      <%
                   System.out.println("here");
                  // System.out.println(oid+"  " +oname);
                try
                {
                    
                 
                    if(oid==5000)
                    {
                        out.println("<option value="+oid+">"+"HEAD OFFICE"+"</option>");
                    }
                    else
                    {
                        /* ps=con.prepareStatement("select ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID  from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_UNIT_ID=?");
                        ps.setInt(1,unitid);
                        rs=ps.executeQuery();
                        //out.println("<option value="+oid+">"+oname+"</option>");
                        while(rs.next())
                        { */
                        ps2=con.prepareStatement(" SELECT DISTINCT ACCOUNTING_FOR_OFFICE_ID, " +
                        		"  OFFICE_NAME " +
                        		" FROM " +
                        		"  (SELECT ACCOUNTING_FOR_OFFICE_ID, " +
                        		"    b.OFFICE_NAME " +
                        		"  FROM FAS_MST_ACCT_UNIT_OFFICES a, " +
                        		"    COM_MST_OFFICES b " +
                        		"  WHERE a.ACCOUNTING_FOR_OFFICE_ID=b.OFFICE_ID " +
                        		"  AND a.ACCOUNTING_UNIT_ID        =? " +
                        		"  AND B.OFFICE_STATUS_ID NOT     IN('RD','NC','CL') " +
                        		"  UNION ALL " +
                        		"  SELECT b.RENDERING_UNIT_OFFICE_ID, " +
                        		"    c.office_name " +
                        		"  FROM FAS_MST_ACCT_UNIT_OFFICES a, " +
                        		"    FAS_ASSET_VAL_AC_RENDER_UNITS b, " +
                        		"    COM_MST_OFFICES c " +
                        		"  WHERE a.ACCOUNTING_UNIT_ID    =b.ACCT_RENDERING_UNIT_ID " +
                        		"  AND b.RENDERING_UNIT_OFFICE_ID=c.OFFICE_ID " +
                        		"  AND a.ACCOUNTING_UNIT_ID      =? " +
                        		"  AND OFFICE_WING_SINO          =99 " +
                        		"  )a " +
                        		" ORDER BY ACCOUNTING_FOR_OFFICE_ID");
                        ps2.setInt(1,unitid);
                        rs2=ps2.executeQuery();
                        while(rs2.next())
                        out.println("<option value="+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+">"+rs2.getString("OFFICE_NAME")+"</option>");
                        
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
              
           <tr class="table">
              <td>
                <div align="left">
                         Financial Year <font color="#ff2121">*</font>
              </div>
              </td>
              <td colspan="2">
                    <select name="cmbFinancialYear" id="cmbFinancialYear" ><!--
                    <option value="">--Select Year--</option>
                  <option value="2010-11">2010-11</option>
                  --><option value="2011-12">2011-12</option>
                  <option value="2012-13">2012-13</option>
                    </select>
              </td>
              </tr>
         <!--------------------------major asset-->
                    <tr class="table">
                <td>
                  <div align="left">
                     Major Asset Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                
                    <select size="1" name="cmbmajorasset" id="cmbmajorasset" tabindex="3" ><option value=0>-- Select Major Asset Code --</option>
                     
                    </select>
                  </div>
                </td>
              </tr>
       <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
          <td colspan="3" class="table">
            <input type="button" name="CmdGo" value="Go" id="CmdGo" onclick="callServer('Go')"/></td>
        </tr>
                </table>
                </div>
              </td>
            </tr>
         </table>
         <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%" align="center" >
				<tr class="tdH"><!--
					<th style="font-size: xx-small" width="1%">S.No</th>
					-->
					<th width="1%">S.No</th>
					<th width="7%">Particulars</th>
					<th width="4%">CashBook Year</th>
					<th width="4%">CashBook Month</th>
					<th width="2%">Asset Code</th>
					<th width="3%">Receipt No</th> 
					<th width="5%">Receipt Date</th>
					<th width="2%">Received From Office	</th>
					<th width="5%">MBook No</th>
					<th width="3%">MBook Date</th>
					<th width="3%">Received Qty</th>
					<th width="3%">Received Value</th>
					<th width="5%">Remarks</th>
                </tr>
             <tbody id="tblList" align="center" class="table">
             </tbody>
            </table>
            <table align="center" cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="tdH">
                  <td>
                    <div align="center">
                             <input type="submit" id="updateTotally" name="updateTotally" value="Update To A52"></input>
                            <input type="button" name="CmdExit" value="EXIT" onclick="closeWindow()">
                    </div>
                  </td>
                </tr>
      </table> 
    </form>
    </body>
</html>