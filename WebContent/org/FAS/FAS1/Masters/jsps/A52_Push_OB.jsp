<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>A52 Edit After Push</title>

     <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
     <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>  <!-- to avoid future date the above script used-->
    
    <script type="text/javascript"  src="../../../../../org/FAS/FAS1/Masters/scripts/A52_Push_OB.js"></script>
 <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
     <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
  </head>
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS'),setTimeout('common_LoadOfficeCode()',200),callServer('loadMajor');">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">A52 Edit After Push</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmA52_Qty_Push1" id="frmA52_Qty_Push1" method="POST" action="../../../../../A52_Push_OB?command=updateEdit"><!--
     action="../../../../../A52_Push_OB?command=updateTotally" onsubmit="return checkNull_verify()"              
  --><%
  
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
    
               // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
     <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" >General </h2>
           
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
                    <!--<input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>-->
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOfficeCode()" onclick="common_LoadOfficeCode();">
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
              <div align="left">
                <select size="1" name="cmbOffice_code" id="cmbOffice_code"
                        tabindex="2"></select>
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
                    <select name="cmbFinancialYear" id="cmbFinancialYear" onblur="callServer('loadHeadDesc'),callServer('loadDep'),callServer('loadApp')" onchange="callServer('loadHeadDesc'),callServer('loadDep'),callServer('loadApp')">
                    <option value="">--Select Year--</option>
                  <option value="2012-13">2012-13</option>
                  <option value="2013-14">2013-14</option>
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
                
                    <select size="1" name="cmbmajorasset" id="cmbmajorasset" tabindex="3" onblur="callServer('loadHeadDesc'),callServer('loadDep'),callServer('loadApp')" onchange="callServer('loadHeadDesc'),callServer('loadDep'),callServer('loadApp')"><option value=0>-- Select Major Asset Code --</option>
                     
                    </select>
                  </div>
                </td>
              </tr>
               <!--------------------------Head of a/c-->
                    <tr class="table">
                <td>
                  <div align="left">
                    Head of Account
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                <input type="text" name="cmbheadac" id="cmbheadac" readonly="readonly" onblur="callServer('loadDep'),callServer('loadApp')" onchange="callServer('loadApp')"/><!-- 
                    <select size="1" name="cmbheadac" id="cmbheadac" tabindex="3" onblur="callServer('loadDep')"><option value=0>-- Select Head of Account --</option>
  
                    </select>
                  --></div>
                </td>
              </tr>
            <!--------------------------Depreciation-->
                    <tr class="table">
                <td>
                  <div align="left">
                    Depreciation %
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" name="cmbdepreciat" id="cmbdepreciat" readonly="readonly"  onblur="callServer('loadApp')"/>
                    <!--<select name="cmbdepreciat" id="cmbdepreciat" tabindex="3" onblur="callServer('loadApp')"><option value=0>-- Select Depreciation--</option>
                     
                    </select>
                  --></div>
                </td>
              </tr>
              <!--------------------------Apportionment of grant-->
                    <tr class="table">
                <td>
                  <div align="left">
                    Apportionment of grant %
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" name="cmbapport" id="cmbapport" readonly="readonly" /><!--
                    <select size="1" name="cmbapport" id="cmbapport" tabindex="3" ><option value=0>-- Select Apportionment of grant --</option>
                     
                    </select>-->
                  </div>
                </td>
              </tr>
              
              
              
       <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
          <td colspan="3" class="table">
            <input type="button" name="CmdGo" value="Go" id="CmdGo" onclick="callServer('checkStatus')"/>
            
            <input type="reset" name="CmdClear" value="CLEAR ALL"
                   id="CmdClear" onclick="clearAll();"/>
            <input type="button" name="CmdExit" value="EXIT" onclick="closeWindow()">
   
          </td>
        </tr>
         </table>
         </div>
              </td>
            </tr>
        
        </table>
        
         </div>
       </div>
         
        <div class="tab-page" id="gd" >
          <h2 class="tab" > Details</h2>
           
          <div align="center">
        
        
        
         <!--------------------------Edit value Desc -->
          <table cellspacing="1" cellpadding="2" border="1" width="100%">
        
                      
                     <tr class="table"><td><div align="left">Description</div></td>
          <td><div align="left"><input type="text" name="desc" id="desc" size="60" readonly="readonly"/></div></td></tr>   
         <tr class="table"><td><div align="left">Opening Balance Quantity &amp; Value</div></td>
          <td><div align="left"><input type="text" name="OB_Qty" id="OB_Qty"  onkeypress="return numbersonly1(event,this)"/><input type="text" name="OB_Value" id="OB_Value"  onkeypress="return numbersonly1(event,this)" /></div></td></tr><!--
         <tr class="table"><td><div align="left">OB Value</div></td>
          <td><div align="left"><input type="text" name="OB_Value" id="OB_Value"  onkeypress="return numbersonly1(event,this)" /></div></td></tr>         
           --><tr class="table"><td><div align="left">Receipts Quantity &amp;Value (DR) </div></td>
          <td><div align="left"><input type="text" name="Receipts_Dr_Qty" id="Receipts_Dr_Qty"  onkeypress="return numbersonly1(event,this)"/><input type="text" name="Receipts_Dr_Value" id="Receipts_Dr_Value"  onmouseout="totalValue()" onblur="totalValue()" onkeypress="return numbersonly1(event,this)"/></div></td></tr>
           <!--<tr class="table"><td><div align="left">Receipts Cr Qty</div></td>
          <td><div align="left"><input type="text" name="Receipts_Cr_Qty" id="Receipts_Cr_Qty" onmouseout="totalQty()" onblur="totalQty()" onkeypress="return numbersonly1(event,this)"/></div></td></tr>
           --><tr class="table"><td><div align="left">Receipts Quantity &amp; Value (CR)</div></td>
          <td><div align="left"><input type="text" name="Receipts_Cr_Qty" id="Receipts_Cr_Qty" onmouseout="totalQty()" onblur="totalQty()" onkeypress="return numbersonly1(event,this)"/><input type="text" name="Receipts_Cr_Value" id="Receipts_Cr_Value" onkeypress="return numbersonly1(event,this)" onmouseout="totalValue()" onblur="totalValue()"/></div></td></tr><!--
           <tr class="table"><td><div align="left">Receipts Cr Value</div></td>
          <td><div align="left"><input type="text" name="Receipts_Cr_Value" id="Receipts_Cr_Value" onkeypress="return numbersonly1(event,this)" onmouseout="totalValue()" onblur="totalValue()"/></div></td></tr>
           --><tr class="table"><td><div align="left">Total Quantity &amp; Value</div></td>
          <td><div align="left"><input type="text" name="Total_Qty" id="Total_Qty" value="0" readonly="readonly" onkeypress="return numbersonly1(event,this)"/><input type="text" name="Total_Value" id="Total_Value" value="0" readonly="readonly" onkeypress="return numbersonly1(event,this)"/></div></td></tr><!--
           <tr class="table"><td><div align="left">Total Value</div></td>
          <td><div align="left"><input type="text" name="Total_Value" id="Total_Value" readonly="readonly" onkeypress="return numbersonly1(event,this)"/></div></td></tr>
           --><tr class="table"><td><div align="left">Issues Quantity &amp; Value (CR)</div></td>
          <td><div align="left"><input type="text" name="Issues_Cr_Qty" id="Issues_Cr_Qty"  onmouseout="CBtotqty()" onblur="CBtotqty()"  onkeypress="return numbersonly1(event,this)"/><input type="text" name="Issues_Cr_Value" id="Issues_Cr_Value" onmouseout="CBtotvalue()" onblur="CBtotvalue()" onkeypress="return numbersonly1(event,this)"/></div></td></tr><!--
           <tr class="table"><td><div align="left">Issues Cr Qty</div></td>
          <td><div align="left"><input type="text" name="Issues_Cr_Qty" id="Issues_Cr_Qty"  onmouseout="CBtotqty()" onblur="CBtotqty()"  onkeypress="return numbersonly1(event,this)"/></div></td></tr>
           --><tr class="table"><td><div align="left">Issues Quantity &amp; Value (DR)</div></td>
          <td><div align="left"><input type="text" name="Issues_Dr_Qty" id="Issues_Dr_Qty" onmouseout="CBtotqty()" onblur="CBtotqty()" onkeypress="return numbersonly1(event,this)"/><input type="text" name="Issues_Dr_Value" id="Issues_Dr_Value" onmouseout="CBtotvalue()" onblur="CBtotvalue()"  onkeypress="return numbersonly1(event,this)"/></div></td></tr><!--
           <tr class="table"><td><div align="left">Issues Cr Value</div></td>
          <td><div align="left"><input type="text" name="Issues_Cr_Value" id="Issues_Cr_Value" onmouseout="CBtotvalue()" onblur="CBtotvalue()" onkeypress="return numbersonly1(event,this)"/></div></td></tr>
           --><tr class="table"><td><div align="left">Closing Balance Quantity &amp; Value</div></td>
          <td><div align="left"><input type="text" name="CB_Qty" id="CB_Qty" value="0" readonly="readonly"/><input type="text" name="CB_Value" id="CB_Value" value="0" readonly="readonly"/></div></td></tr><!--
         <tr class="table"><td><div align="left">CB Value</div></td>
          <td><div align="left"><input type="text" name="CB_Value" id="CB_Value" readonly="readonly" value="0"/></div></td></tr> 
         --><tr class="table"><td><div align="left">Upto Previous Yr Depreciation &amp; Apportionment</div></td>
          <td><div align="left"><input type="text" name="Upto_Pre_Depr" id="Upto_Pre_Depr"  onmouseout="totdep()" onblur="totdep()"  onkeypress="return numbersonly1(event,this)"/><input type="text" name="Upto_Pre_Appr" id="Upto_Pre_Appr" onmouseout="totapp()" onblur="totapp()" onkeypress="return numbersonly1(event,this)"/></div></td></tr><!--        
         <tr class="table"><td><div align="left">Upto Previous Yr Apportionment</div></td>
          <td><div align="left"><input type="text" name="Upto_Pre_Appr" id="Upto_Pre_Appr" onmouseout="totapp()" onblur="totapp()" onkeypress="return numbersonly1(event,this)"/></div></td></tr>
         --><tr class="table"><td><div align="left">Received Through Proforma Account Depreciation &amp; Apportionment</div></td>
          <td><div align="left"><input type="text" name="Rec_Thr_dep" id="Rec_Thr_dep" onmouseout="totdep()" onblur="totdep()" onkeypress="return numbersonly1(event,this)"/><input type="text" name="Rec_Thr_app" id="Rec_Thr_app" onmouseout="totapp()" onblur="totapp()" onkeypress="return numbersonly1(event,this)"/></div></td></tr><!--        
         <tr class="table"><td><div align="left">Received Through Proforma Account Apportionment</div></td>
          <td><div align="left"><input type="text" name="Rec_Thr_app" id="Rec_Thr_app" onmouseout="totapp()" onblur="totapp()" onkeypress="return numbersonly1(event,this)"/></div></td></tr>
           --><tr class="table"><td><div align="left">Allowed during the year Depreciation Dr &amp; Cr</div></td>
          <td><div align="left"><input type="text" name="allow_dur_dep_dr" id="allow_dur_dep_dr" onmouseout="totdep()" onblur="totdep()" onkeypress="return numbersonly1(event,this)"/><input type="text" name="allow_dur_dep_cr" id="allow_dur_dep_cr"   onmouseout="totdep()" onblur="totdep()" onkeypress="return numbersonly1(event,this)"/></div></td></tr><!--        
         <tr class="table"><td><div align="left">Allowed during the year Depreciation Cr</div></td>
          <td><div align="left"><input type="text" name="allow_dur_dep_cr" id="allow_dur_dep_cr"   onmouseout="totdep()" onblur="totdep()" onkeypress="return numbersonly1(event,this)"/></div></td></tr>
           --><tr class="table"><td><div align="left">Allowed during the year Apportionment Dr &amp; Cr</div></td>
          <td><div align="left"><input type="text" name="allow_dur_app_dr" id="allow_dur_app_dr"  onmouseout="totapp()" onblur="totapp()" onkeypress="return numbersonly1(event,this)"/><input type="text" name="allow_dur_app_cr" id="allow_dur_app_cr"  onmouseout="totapp()" onblur="totapp()"/></div></td></tr><!--        
         <tr class="table"><td><div align="left">Allowed during the year Apportionment Cr</div></td>
          <td><div align="left"><input type="text" name="allow_dur_app_cr" id="allow_dur_app_cr"  onmouseout="totapp()" onblur="totapp()"/></div></td></tr>         
           --><tr class="table"><td><div align="left">Total Depreciation &amp; Apportionment</div></td>
          <td><div align="left"><input type="text" name="tot_Dep" id="tot_Dep" readonly="readonly" value="0" onkeypress="return numbersonly1(event,this)" /><input type="text" name="tot_App" id="tot_App" value="0" readonly="readonly" onkeypress="return numbersonly1(event,this)" /></div></td></tr><!--        
         <tr class="table"><td><div align="left">Total Apportionment</div></td>
          <td><div align="left"><input type="text" name="tot_App" id="tot_App" readonly="readonly" value="0" onkeypress="return numbersonly1(event,this)" /></div></td></tr>
           --><tr class="table"><td><div align="left">Transferred Through Proforma Account Depreciation &amp; Apportionment</div></td>
          <td><div align="left"><input type="text" name="trasf_Thr_dep" id="trasf_Thr_dep"   onmouseout="uptodep(),netdep()" onblur="uptodep(),netdep()" onkeypress="return numbersonly1(event,this)"/><input type="text" name="trasf_Thr_app" id="trasf_Thr_app" onmouseout="uptoapp(),netdep()" onblur="uptoapp(),netdep()" onkeypress="return numbersonly1(event,this)"/></div></td></tr><!--        
         <tr class="table"><td><div align="left">Transferred Through Proforma Account Apportionment</div></td>
          <td><div align="left"><input type="text" name="trasf_Thr_app" id="trasf_Thr_app" onmouseout="uptoapp(),netdep()" onblur="uptoapp(),netdep()" onkeypress="return numbersonly1(event,this)"/></div></td></tr>
          --><tr class="table"><td><div align="left">Upto Date Depreciation &amp; Apportionment</div></td>
          <td><div align="left"><input type="text" name="Upto_date_Dep" id="Upto_date_Dep" readonly="readonly" value="0" onmouseout="uptodep(),netdep()" onblur="uptodep(),netdep()" onkeypress="return numbersonly1(event,this)"/><input type="text" name="Upto_date_App" id="Upto_date_App" value="0" readonly="readonly" onmouseout="uptoapp(),netdep()" onblur="uptoapp(),netdep()"onkeypress="return numbersonly1(event,this)"/></div></td></tr>        
         <!--<tr class="table"><td><div align="left">Upto Date Apportionment</div></td>
          <td><div align="left"><input type="text" name="Upto_date_App" id="Upto_date_App" readonly="readonly" value="0" onmouseout="uptoapp(),netdep()" onblur="uptoapp(),netdep()"onkeypress="return numbersonly1(event,this)"/></div></td></tr>
          --><tr class="table"><td><div align="left">Depreciated Cost</div></td>
          <td><div align="left"><input type="text" name="dep_Cost" id="dep_Cost" readonly="readonly" value="0" onkeypress="return numbersonly1(event,this)"/>
          <input type="hidden" name="asset_code" id="asset_code"/>
          </div></td></tr>        
                 </table>  
                 <table align="center" cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="tdH">
                  <td>
                    <div align="center">
                             <input type="submit" id="updateTotally" name="updateTotally" value="Update"></input>
                            <input type="button" name="CmdExit" value="EXIT" onclick="closeWindow()">
                    </div>
                  </td>
                </tr>
      </table> 
            
         <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%" align="center" >
                <tr class="tdH">
		<th>Select</th>
		<th>Description</th>
		<th>OB Qty</th>
		<th>OB Value</th>
		<th>Receipts Dr Qty</th>
		<!--<th>Receipts Cr Qty</th>
		--><th>Receipts Dr Value</th>
		<!--<th>Receipts Cr Value</th>
		<th>Total Qty</th>
		<th>Total Value</th>
		<th>Issues Dr Qty</th>-->
		<th>Issues Cr Qty</th>
		<!--<th>Issues Dr Value</th>
		--><th>Issues Cr Value</th>
		<!--<th>CB Qty</th>
		<th>CB Value</th>
		--><th>Upto Previous Yr Dep</th>
		<th>Upto Previous Yr Appo</th>
		
		<th>Received Thr Proforma A/c Depreciation</th>
		<th>Received Thr Proforma A/c Apportionment</th><!--
		
	    <th>Allowed during the year Depreciation Dr</th>
	    --><th>Allowed during the year Depreciation Cr</th><!--
		<th>Allowed during the year Apportionment Dr</th>
		--><th>Allowed during the year Apportionment Cr</th><!-- 
	    <th>Total Depreciation</th>
		<th>Total Apportionment</th>
	
		--><th>Transf Through Proforma A/c Depreciation</th>
		<th>Transf Through Proforma A/c Apportionment</th>
		
		<!--<th>Upto Date Depreciation</th>
		<th>Upto Date Apportionment</th> 
		<th>Depreciated Cost</th>
		<th>Remarks</th> 
	    --></tr>
             <tbody id="tblList" align="center" class="table">
             </tbody>
            </table>
            </div>
             </div>
        </div>
         <br>
    </form>
    </body>
</html>