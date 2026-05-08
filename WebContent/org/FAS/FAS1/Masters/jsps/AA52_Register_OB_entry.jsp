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
    <script type="text/javascript"  src="../../../../../org/FAS/FAS1/Masters/scripts/AA52_Register_OB.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
   
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
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
  <body onload="foc()" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">AA52 Register for Closing Balance </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmAA52_Register" id="frmAA52_Register" >
                  
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
                    
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1">
                    
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
              
           <tr align="left">
              <td class="table">
                <div align="left">
                         Financial Year <font color="#ff2121">*</font>
              </div>
              </td>
              <td colspan="2">
                    <select name="cmbFinancialYear" id="cmbFinancialYear" tabindex="3"  >
                    <option value="">--Select Year--</option>
                    <%
                        Statement st=con.createStatement();
                        rs=st.executeQuery("select financial_year from cash_book_control");
                        while(rs.next())
                        {
                            out.println("<option value='"+rs.getString("financial_year")+"'>"+rs.getString("financial_year")+"</option>");
                        }
                    %>
                    </select>
              </td>
              </tr>
          
              <tr class="table">
                <td>
                  <div align="left">
                     Asset Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                
                    <select size="1" name="cmbasset" id="cmbasset" tabindex="4" onchange="callServer('Get')" >
                    <option value=0>-- Select Asset Code --</option>
                      <%
                      int asset=0;
                    try
                    {
                     ps=con.prepareStatement("select ASSET_CODE from FAS_ASSET_VAL_AC_DETAILS where ACCOUNTING_UNIT_ID=? order by ASSET_CODE");
                     //ps.setInt(1,7);
                     ps.setInt(1,unitid);
                     //System.out.println("select ASSET_CODE from FAS_ASSET_VAL_AC_DETAILS where ASSET_MAJOR_CLASS_CODE='"+7+"' and ACCOUNTING_UNIT_ID='"+unitid+"'?");
                     rs=ps.executeQuery();
                     while(rs.next())
                     {
                    	System.out.println(rs.getInt("ASSET_CODE"));
                       // out.println("<option value="+rs.getInt("ASSET_CODE")+"</option>");
                        asset =rs.getInt("ASSET_CODE");
                      System.out.println("asset"+asset);
                      out.println("<option value='"+rs.getInt("ASSET_CODE")+"'>"+rs.getInt("ASSET_CODE")+"</option>");
                        //out.println("<option value="+asset+"</option>");
                     }
                        
                    } 
                    catch(Exception e)
                    {
                    System.out.println("Exception in asset combo..."+e);
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
              
              
              <TR class="table">
                                      <TD >
            Book value(Opening Balance in A52 Register)
                                </TD>
                                      <TD>
                                        <input type="text" name="txtOB_bal" id="txtOB_bal" tabindex="5" onkeypress="return numFloatInt(event,this);"> 
                                          
                                      </TD>
                                  </TR>
              <TR class="table">
                                      <TD>
                                        Accumulated Depreciation/
                                        Apportionment of grant(upto date depreciation/
                                        Apportionment of grant in A52 Register)  
                             </td><td>
                                      <input type="text" name="txtapport_grant" id="txtapport_grant" tabindex="6" size="30" onkeypress="return numFloatInt(event,this);" >
                                          
                                      </TD>
                                  </TR>
              <tr class="table">
                                    <td>Depreciated value debited to discarded asset A/c 
                                    (Depreciated cost in A52)</td>
                                    <TD>
                                    <input type="text" name="txtdep_debit" id="txtdep_debit" tabindex="7" size="30" onkeypress="return numFloatInt(event,this);" >
                                        
                                      </TD>
                                                                  
                                  </tr>
             
                                   <tr class="table">
                <td>
                  <div align="left"> Ref. journal no and Date of office in which value a/c is maintained  
                   
                   </div>
                </td>
                <TD>
                                     <input type="text" name="txtjournal_no" id="txtjournal_no"  tabindex="8" size="30"  onkeypress="return numFloatInt(event,this);">
                                      Date<input type="text" name="txtjournal_date" id="txtjournal_date"  tabindex="9" 
                            
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                           
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmAA52_Register.txtjournal_date,1);"
                         alt="Show Calendar"></img> 
                                      </TD>
              </tr>     
                                  <TR class="table">
                                      <TD>
                   Condemenation Approved Details
            
                                </TD>
                                      <TD>
                                        Survey Report No<input type="text" name="txtsurvey_no" id="txtsurvey_no" tabindex="10" onkeypress="return numFloatInt(event,this);">
                                           Date<input type="text" name="txtsurvey_date" id="txtsurvey_date"  tabindex="11" 
                            
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                           
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmAA52_Register.txtsurvey_date,1);"
                         alt="Show Calendar"></img> 
                                      </TD>
                                  </TR>
                                  <TR class="table">
                                      <TD>
                                        Date of Auction
                                </TD>
                                      <TD>
                                         <input type="text" name="txtauction_date" id="txtauction_date"  tabindex="12" 
                            
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                           
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmAA52_Register.txtauction_date,1);"
                         alt="Show Calendar"></img> 
                                      </TD>
                                  </TR>
                                  <TR class="table">
                                      <TD>
                                         Name of the person to whom asset is handed over
                                </TD>
                                      <TD>
                                          <input type="text" name="txtperson_name" id="txtperson_name" tabindex="13" onchange="return toCheck1()"  >
                                          
                                      </TD>
                                  </TR>
                                  <TR class="table">
                                      <TD>
                                         Amount recieved on Auction
                                </TD>
                                      <TD>
                                          <input type="text" name="txtauction_amt" id="txtauction_amt" tabindex="14" onkeypress="return numFloatInt(event,this);">
                                          
                                      </TD>
                                  </TR>
                                  <TR class="table">
                                      <TD>
                                         Ref. CB Vr No. & Date of office in which value a/c is maintained
                                </TD>
                                      <TD>
                                          <input type="text" name="cb_vrno" id="cb_vrno" tabindex="15" onkeypress="return numFloatInt(event,this);" >
                                           Date<input type="text" name="txtcb_date" id="txtcb_date"  tabindex="16" 
                            
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                           
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmAA52_Register.txtcb_date,1);"
                         alt="Show Calendar"></img>
                                      </TD>
                                  </TR>
                                  
              <tr class="table">
                <td>
                  <div align="left">Profit</div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="txt_profit" id="txt_profit"  tabindex="17" onkeypress="return numFloatInt(event,this);"></input>
                            
                         
                  </div>
                </td>
              </tr>
               <TR class="table">
                                      <TD>
                                         Loss
                                </TD>
                                      <TD>
                                          <input type="text" name="txt_loss" id="txt_loss" tabindex="18"  onkeypress="return numFloatInt(event,this);">
                                          
                                      </TD>
                                  </TR>                     
              <tr class="table">
                <td>
                  <div align="left">Debit to discarded asset a/c of office</div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtoff_debit" id="txtoff_debit" tabindex="19" onkeypress="return numFloatInt(event,this);" >
                  </div>
                </td>
              </tr>
                 <TR class="table">
                                      <TD>
                          Credit to discarded asset a/c of office
           
                                </TD>
                                      <TD>
                                          <input type="text" name="txtoff_credit" id="txtoff_credit" tabindex="20" onkeypress="return numFloatInt(event,this);" >
                                          
                                      </TD>
                                  </TR>
                                  <!--TR class="table">
                                      <TD>
                                        Ref.journal No.& Date of office in which value a/c is maintained
                                </TD>
                                      <TD>
                                          <input type="text" name="txtjournal_vno2" id="txtjournal_vno2" tabindex="21" onkeypress="return numFloatInt(event,this);" >
                                            Date<input type="text" name="txtjournal_date2" id="txtjournal_date2"  tabindex="18" 
                            
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                           
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmAA52_Register.txtjournal_date2,1);"
                         alt="Show Calendar"></img>
                                      </TD>
                                  </TR-->
                                  
                                   <TR class="table">
                                      <TD>
                                         Remarks 
                                </TD>
                                      <TD>
                                          <textarea name="txtRemarks" id="txtRemarks" cols="50" tabindex="21" 
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
            <!--input type="button" name="CmdList" value="LIST"
                   id="CmdList" onclick="callServer('Get')"/-->
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
    </form></body>
</html>