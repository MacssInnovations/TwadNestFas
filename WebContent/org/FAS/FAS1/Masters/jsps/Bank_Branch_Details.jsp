<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Bank Branch Details</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/Bank_Branch_Details.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
     <!-- to avoid future date the above script used-->
     
     <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script> 
     
     
    <script type="text/javascript" language="javascript">
         function foc()
         {
         }
</script>
  </head>
  <body onload="foc();LoadAccountingUnitID('LIST_ALL_UNITS');" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Bank Branch Details </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmBank_Branch_Details" id="frmBank_Branch_Details" >
                  
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
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);">
                     <!-- <option value="0"> Select Account Unit </option>-->
                          <%
                      int unitid=0;
                      String unitname="";
                     String FAS_SU="";
   
    if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
         FAS_SU="YES";
    else
         FAS_SU="NO";
         
         System.out.println("FAS_SU===>"+session.getAttribute("FAS_SU"));
         System.out.println("oid===>"+oid);
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
                   </div>
                   <!--end -->
                  
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                      
                      <%
                   System.out.println("here");
                   System.out.println(oid+"  " +oname);
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
                     Bank Name
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                
                    <select size="1" name="cmbBankId" id="cmbBankId" tabindex="3" >
                    <option>-- Select Bank Name --</option>
                      <%
                    try
                    {
                     ps=con.prepareStatement("select BANK_ID, BANK_NAME from FAS_MST_BANKS");
                     
                     rs=ps.executeQuery();
                     while(rs.next())
                     {
                        out.println("<option value="+rs.getInt("BANK_ID")+">"+rs.getString("BANK_NAME")+"</option>");
                     }
                        
                    } 
                    catch(Exception e)
                    {
                    System.out.println("Exception in bank combo..."+e);
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
             <td >
                 Branch Id
                </td>
            <td>
                <input  tabindex="4" type=text name=id id="id"  class="disab" disabled >(System Generated)
                <input  tabindex="4" type=HIDDEN name=BranchId id="BranchId">
            </td>
        </tr>
              
              <TR>
                                      <TD >
                                          Branch Name<label style="color:rgb(255,0,0);">*</label>
                                </TD>
                                      <TD>
                                          <input type="text" name="txtOffice_Name" id="txtOffice_Name" tabindex="5" size="30" >
                                          
                                      </TD>
                                  </TR>
              <TR>
                                      <TD rowspan="2">
                                          Office Address<label style="color:rgb(255,0,0);">*</label>
                                </TD>
                                      <TD>
                                          <input type="text" name="txtOffice_Address1" id="txtOffice_Address1" tabindex="6" size="30" onfocus="return officeCheck()">
                                          
                                      </TD>
                                  </TR>
                                  <TR>
                                      <TD>
                                          <input type="text" name="txtOffice_Address2" id="txtOffice_Address2" tabindex="7" onfocus="return officeCheck()" size="30">
                                          
                                      </TD>
                                  </TR>
                                  <TR>
                                      <TD>
                                          City/Town
                                      </TD>
                                      <TD>
                                          <input type="text" name="txtOffice_City" id="txtOffice_City" tabindex="8" onfocus="return officeCheck()">
                                      </TD>
                                  </TR>
                                  <TR>
                                      <TD size="1" name="cmbBankId" id="cmbBankId" tabindex="3" >
                                          District<label style="color:rgb(255,0,0);">*</label>
                                      </TD>
                                      <TD>
                                          <select name="cmbDistrict" id="cmbDistrict" tabindex="9" onfocus="return officeCheck()">
                                          <option>----Select District----</option>
                                           <%
                                            try
                                            {
                                               ps=con.prepareStatement("select DISTRICT_CODE, DISTRICT_NAME from COM_MST_DISTRICTS order by District_Name");
                                               rs=ps.executeQuery();
                                               while(rs.next())
                                              {
                                                 out.println("<option value="+rs.getInt("DISTRICT_CODE")+">"+rs.getString("DISTRICT_NAME")+"</option>");
                                                 }
                        
                                               } 
                                            catch(Exception e)
                                          {
                                            System.out.println("Exception in District combo..."+e);
                                           }
                                         finally
                                         {
                                           rs.close();
                                           ps.close();
                                          }  
                                             %>
                                                  
                                          </select>
                                      </TD>
                                  </TR>
                                  <tr>
                                    <td>MICR Code</td>
                                    <td><input type=text name="txtMic_Code" id="txtMic_Code" size=10 maxlength="9" tabindex="9" onfocus="return officeCheck()" onkeypress="return  numbersonly1(event,this)" onblur="return checkmiccode();return pinlength();" >
                                    
                                  </tr>
                                  <TR>
                                      <TD>
                                          Phone Number<label style="color:rgb(255,0,0);">*</label>
                                </TD>
                                      <TD>
                                          <input type="text" name="txtPhone" id="txtPhone" onkeypress="return  numbersonly1(event,this)" size="30" tabindex="11" maxlength="10" onchange="return checkphone();" >
                                          
                                      </TD>
                                  </TR>
                                  <TR>
                                      <TD>
                                         Fax Number<label style="color:rgb(255,0,0);">*</label>
                                </TD>
                                      <TD>
                                          <input type="text" name="txtFax" id="txtFax" tabindex="12" onkeypress="return  numbersonly1(event,this)" maxlength="10" onchange="return checkfax();" >
                                          
                                      </TD>
                                  </TR>
                                  
              <tr class="table">
                <td>
                  <div align="left">Remarks</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="50" tabindex="13" onkeypress="return check_leng(this.value);"
                              rows="4"></textarea>
                  </div>
                </td>
              </tr>
              <tr align="left" class="table">
             <td>
                  Status </td>
                  <td>
                  
                    <input type="radio" name="txtstatus" id="txtstatus" tabindex="15"
                            value="L" checked  >Live
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtstatus" id="txtstatus" 
                           value="C"  >Cancel &nbsp;&nbsp;&nbsp;&nbsp; 
         
                </td>
             </tr>
             <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
                    <td>
                    <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="doFunction('Add','null')" tabindex="20"/>
                     </td>
                     <td>
                    <input type="button" name="cmdUpdate" value="UPDATE" id="cmdUpdate" style="display:none" onclick="doFunction('Update','null')" tabindex="30"/>
                     </td>
                    <td>
                    <input type="button" name="cmdDelete" value="CANCEL" id="cmdDelete" style="display:none" onclick="doFunction('Cancel','null')" tabindex="40"/>
                     </td>
                     <td>
                    <input type="button" name="cmdClear" value="CLEAR" id="cmdClear" onclick="ClearAll()" tabindex="50"/>
                     </td>
                     <td>
                    <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListHeads()" tabindex="60"/>
                     </td>
                       <td>
                     <input type="button" id="Exit" name="Exit" value="EXIT" onclick="exit()" tabindex="70"/>
                     </td>
                 </tr>
                </table>
                </div>
              </td>
            </tr>
         </table>
    </form></body>
</html>