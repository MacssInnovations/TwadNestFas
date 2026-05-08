<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>A52 Register for Closing Balance</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/FAS/FAS1/Masters/scripts/A52_Register_OB.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
     <script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
           
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>  
     <!-- to avoid future date the above script used-->
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
  <body onload="foc();callServer('loadMajor');" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">A52 Register for Closing Balance </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmA52_Register" id="frmA52_Register" method="get">
                  
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
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1">
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
                        ps=con.prepareStatement("select ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID  from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_UNIT_ID=? order by ACCOUNTING_FOR_OFFICE_ID desc");
                        ps.setInt(1,unitid);
                        rs=ps.executeQuery();
                        //out.println("<option value="+oid+">"+oname+"</option>");
                        while(rs.next())
                        {
                        ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                        ps2.setInt(1,rs.getInt("ACCOUNTING_FOR_OFFICE_ID"));
                        rs2=ps2.executeQuery();
                        if(rs2.next())
                        out.println("<option value="+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+">"+rs2.getString("OFFICE_NAME")+"</option>");
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
              
           <tr class="table">
              <td>
                <div align="left">
                         Financial Year <font color="#ff2121">*</font>
              </div>
              </td>
              <td colspan="2">
                    <select name="cmbFinancialYear" id="cmbFinancialYear" >
                    <option value="">--Select Year--</option>
                  <option value="2010-11">2010-11</option>
                  <option value="2011-12">2011-12</option>
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
                
                    <select size="1" name="cmbmajorasset" id="cmbmajorasset" tabindex="3" onchange="callServer('loadMinor')">
                    <option value=0>-- Select Major Asset Code --</option>
                     
                    </select>
                  </div>
                </td>
              </tr>
        <!---------minor asset code-->
                    <tr class="table">
                <td>
                  <div align="left">
                     Minor Asset Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                
                    <select size="1" name="cmbminorasset" id="cmbminorasset" tabindex="3" onblur="callServer('loadAssetCode')">
                    <option value=0>-- Select Minor Asset Code --</option>
                  
                    </select>
                  </div>
                </td>
              </tr>
          <!-------asset code--->
              <tr class="table">
                <td>
                  <div align="left">
                     Asset Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                
                    <select size="1" name="cmbasset" id="cmbasset" tabindex="3" onchange="callServer('Get')" >
                    <option value=0>-- Select Asset Code --</option>
                   
                    </select>
                  </div>
                </td>
              </tr>
              
              
              <TR class="table">
                                      <TD >
            Opening Balance
                                </TD>
                                      <TD>
                                        Quantity<input type="text" name="txtQ1" id="txtQ1" tabindex="5" onkeypress="return numFloatInt(event,this);"> Value<input type="text" name="txtV1" id="txtV1" tabindex="6" size="30" onkeypress="return numFloatInt(event,this);" >
                                          
                                      </TD>
                                  </TR>
              <TR class="table">
                                      <TD>
                                        Receipts during the year  
                             </td><td>
                                      Quantity<input type="text" name="txtQ2" id="txtQ2" tabindex="7" size="30" onkeypress="return numFloatInt(event,this);" >
                                     Value<input type="text" name="txtV2" id="txtV2" tabindex="8" size="30"  onkeypress="return numFloatInt(event,this);">
                                          
                                      </TD>
                                  </TR>
              <tr class="table">
                                    <td>Issues during the year   </td>
                                    <TD>
                        
                                    Quantity<input type="text" name="txtQ3" id="txtQ3" tabindex="9" size="30" onchange="valid_amt(this);" onkeypress="return numFloatInt(event,this);" >
                                  Value<input type="text" name="txtV3" id="txtV3" tabindex="10" size="30"  onchange="total_value(this);" onkeypress="return numFloatInt(event,this);">
                                          
                                      </TD>
                                                                  
                                  </tr>
             
                                   <tr class="table">
                <td>
                  <div align="left"> Total during the year  
                   
                   </div>
                </td>
                <TD>
                                     Quantity<input type="text" name="txtQ_total" id="txtQ_total"  tabindex="11" size="30" style="background-color: #ececec"  readonly="readonly" onkeypress="return numFloatInt(event,this);">
                                      Value<input type="text" name="txtV_total" id="txtV_total"  tabindex="12" size="30" style="background-color: #ececec"  readonly="readonly" onkeypress="return numFloatInt(event,this);" >
                                          
                                      </TD>
              </tr>     
                                  <TR class="table">
                                      <TD>
                    Depreciation allowed upto previous year
            
                                </TD>
                                      <TD>
                                          <input type="text" name="txtdepre_prev_yr" id="txtdepre_prev_yr" tabindex="13" onkeypress="return numFloatInt(event,this);">
                                          
                                      </TD>
                                  </TR>
                                  <TR class="table">
                                      <TD>
                                         Depreciation received through proforma A/c 
                                </TD>
                                      <TD>
                                          <input type="text" name="txtdepre_recieved" id="txtdepre_recieved" tabindex="14" onkeypress="return numFloatInt(event,this);" >
                                          
                                      </TD>
                                  </TR>
                                  <TR class="table">
                                      <TD>
                                         Depreciation allowed during the year
                                </TD>
                                      <TD>
                                          <input type="text" name="txtdepre_allowed_yr" id="txtdepre_allowed_yr" tabindex="15" onchange="total_depr(this);" onkeypress="return numFloatInt(event,this);"  >
                                          
                                      </TD>
                                  </TR>
                                  <TR class="table">
                                      <TD>
                                         Total Depreciation
                                </TD>
                                      <TD>
                                          <input type="text" name="txttotal_depre" id="txttotal_depre" tabindex="16" style="background-color: #ececec"  readonly="readonly" onkeypress="return numFloatInt(event,this);">
                                          
                                      </TD>
                                  </TR>
                                  <TR class="table">
                                      <TD>
                                         Depreciation transfer through Proforma A/c 
                                </TD>
                                      <TD>
                                          <input type="text" name="dep_transfer" id="dep_transfer" tabindex="17" onkeypress="return numFloatInt(event,this);" >
                                          
                                      </TD>
                                  </TR>
                                  
              <tr class="table">
                <td>
                  <div align="left">Depreciation Upto Date</div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="txtdepre_date" id="txtdepre_date"  tabindex="18" />
                            
                       <!--   maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                           
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmA52_Register.txtdepre_date,1);"
                         alt="Show Calendar"></img>  -->
                  </div>
                </td>
              </tr>
                                   
              <tr class="table">
                <td>
                  <div align="left">Net Depreciation Cost</div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtnet_depre" id="txtnet_depre" tabindex="19" onkeypress="return numFloatInt(event,this);" >
                  </div>
                </td>
              </tr>
                 <TR class="table">
                                      <TD>
                           Apportionment of grant allowed upto previous year
           
                                </TD>
                                      <TD>
                                          <input type="text" name="txtappor_grant" id="txtappor_grant" tabindex="20" onkeypress="return numFloatInt(event,this);" >
                                          
                                      </TD>
                                  </TR>
                                  <TR class="table">
                                      <TD>
                                         Apportionment grant received through proforma A/c 
                                </TD>
                                      <TD>
                                          <input type="text" name="txtappor_recieved" id="txtappor_recieved" tabindex="21" onkeypress="return numFloatInt(event,this);" >
                                          
                                      </TD>
                                  </TR>
                                  <TR class="table">
                                      <TD>
                                         Apportionment of grant allowed during the year
                                </TD>
                                      <TD>
                                          <input type="text" name="txtappor_allowed" id="txtappor_allowed" tabindex="22" onchange="total_appr(this);"  >
                                          
                                      </TD>
                                  </TR>
                                  <TR class="table">
                                      <TD>
                                         Total Apportionment of grant
                                </TD>
                                      <TD>
                                          <input type="text" name="txttotal_appor" id="txttotal_appor" tabindex="23" style="background-color: #ececec"  readonly="readonly" onkeypress="return numFloatInt(event,this);" >
                                          
                                      </TD>
                                  </TR>
                                 
                                   <TR class="table">
                                      <TD>
                                         Apportionment of grant transfer through Proforma A/c 
                                </TD>
                                      <TD>
                                          <input type="text" name="txtapp_transfer" id="txtapp_transfer" tabindex="24" onkeypress="return numFloatInt(event,this);" >
                                          
                                      </TD>
                                  </TR>
                                  <tr class="table">
                <td>
                  <div align="left">  Apportionment of grant Upto Date
                   
                   </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txt_date" id="txt_date"  tabindex="25" />
                            
                    <!--        maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                           
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmA52_Register.txt_date,1);"
                         alt="Show Calendar"></img>      -->                
                  </div>
                </td>
              </tr>
                                   
                                   <TR class="table">
                                      <TD>
                                         Remarks 
                                </TD>
                                      <TD>
                                          <textarea name="txtRemarks" id="txtRemarks" cols="50" tabindex="26" 
                              rows="4"></textarea>
                                          
                                      </TD>
                                  </TR>
             <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
          <td colspan="3" class="table">
            <input type="button" name="CmdAdd" value="ADD" id="CmdAdd" onclick="callServer('Add')"/>
            <input type="button" name="CmdUpdate" value="UPDATE"
                   id="CmdUpdate" onclick="callServer('Update')" disabled/>
            <input type="button" name="CmdDelete" value="DELETE"
                   id="CmdDelete" onclick="callServer('Delete')" disabled/>  
            <input type="reset" name="CmdClear" value="CLEAR ALL"
                   id="CmdClear" onclick="clearAll();"/>
            <!--<input type="button" name="CmdList" value="LIST"
                   id="CmdList" onclick="callList()"/>
            --><input type="button" name="CmdExit" value="EXIT" onclick="closeWindow()">
          </td>
        </tr>
                </table>
                </div>
              </td>
            </tr>
         </table>
    </form></body>
</html>