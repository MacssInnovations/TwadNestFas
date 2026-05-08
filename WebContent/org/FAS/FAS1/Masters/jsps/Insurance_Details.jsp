<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Insurance Details</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/Insurance_Details.js"></script>
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
            <font size="4">Insurance Details of Assets </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmIns_Details" id="frmIns_Details" >
                  
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
                         Financial Year
              </div>
              </td>
              <td colspan="2">
                    <select name="cmbFinancialYear" id="cmbFinancialYear" >
                    <option value="">--Select Year--</option>
                    <option value="2012-2013">2012-2013</option>
                    <!--
                    <%
                        Statement st=con.createStatement();
                        rs=st.executeQuery("select financial_year from cash_book_control");
                        while(rs.next())
                        {
                            out.println("<option value='"+rs.getString("financial_year")+"'>"+rs.getString("financial_year")+"</option>");
                        }
                    %>
                    --></select>
              </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">  Date of Entry
                   
                   </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txt_date" id="txt_date"  tabindex="7"
                            
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                           
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmIns_Details.txt_date,1);"
                         alt="Show Calendar"></img>                    
                  </div>
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
                
                    <select size="1" name="cmbasset" id="cmbasset" tabindex="3" >
                    <option value=0>-- Select Asset Code --</option>
                      <%
                      int asset=0;
                    try
                    {
                     ps=con.prepareStatement("select ASSET_CODE from FAS_ASSET_VAL_AC_DETAILS where ACCOUNTING_UNIT_ID=?");
                     //ps.setInt(1,7);
                     ps.setInt(1,unitid);
                     System.out.println("select ASSET_CODE from FAS_ASSET_VAL_AC_DETAILS where ASSET_MAJOR_CLASS_CODE='"+7+"' and ACCOUNTING_UNIT_ID='"+unitid+"'?");
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
                                          Insurance Company Name<font color="#ff2121">*</font>
                                </TD>
                                      <TD>
                                          <input type="text" name="txtOffice_Name" id="txtOffice_Name"  >
                                          
                                      </TD>
                                  </TR>
              <TR class="table">
                                      <TD>
                                        Type of Insurance  <font color="#ff2121">*</font>
                                </TD>
                                      <TD>
                                      <div align="left">
                    <input type="text" name="ins_type" id="ins_type"  tabindex="7"
                            
                          />
                           
                                         
                  </div>
                              </TD>
                                  </TR>
              <tr class="table">
                                    <td>Insurance in favour of  <font color="#ff2121">*</font> </td>
                                    <td><div align="left">
                    <input type="text" name="favour_of" id="favour_of"  tabindex="7"
                            
                          />
                           
                                      
                  </div>
                                    
                                  </tr>
             
                                   <tr class="table">
                <td>
                  <div align="left"> Policy Number  <font color="#ff2121">*</font>
                   
                   </div>
                </td>
                <td>
                   <input type="text" name="po_no" id="po_no" onkeypress="return  numbersonly1(event,this)"  >
                </td>
              </tr>     
                                  
                                  <TR class="table">
                                      <TD>
                                         Date of Commencement  <font color="#ff2121">*</font>
                                </TD>
                                      <TD>
                                          <div align="left">
                    <input type="text" name="Comnce_date" id="Comnce_date"  tabindex="7"
                            
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                           
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmIns_Details.Comnce_date,1);"
                         alt="Show Calendar"></img>                     
                  </div>
                                          
                                      </TD>
                                      </TR>
                                      
                                  <TR class="table">
                                      <TD>
                                         Date of Expiry  <font color="#ff2121">*</font>
                                </TD>
                                      <TD>
                                          <div align="left">
                    <input type="text" name="expiry_date" id="expiry_date"  tabindex="7"
                            
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return check(this);"/>
                           
                                       
                  </div>
                                          
                                      </TD>
                                      </TR>
                  <TR class="table">
                                      <TD>
                                         Insurance Amount <font color="#ff2121">*</font>
                                </TD>
                                      <TD>
                                          <input type="text" name="txtins_amount" id="txtins_amount" onkeypress="return  numbersonly1(event,this)" >
                                          
                                      </TD>
                                  </TR>    
                                  <TR class="table">
                                      <TD>
                                         Premium Amount <font color="#ff2121">*</font>
                                </TD>
                                      <TD>
                                          <input type="text" name="txtpre_amount" id="txtpre_amount" onkeypress="return  numbersonly1(event,this)"  >
                                          
                                      </TD>
                                  </TR>  
                   <TR class="table">
                                      <TD>
                                         Other Charges <font color="#ff2121">*</font>
                                </TD>
                                      <TD>
                                          <input type="text" name="txtcharges" id="txtcharges" onkeypress="return  numbersonly1(event,this)" >
                                          
                                      </TD>
                                  </TR>                                 
                                              
                                  <TR class="table">
                                      <TD>
                                         Renewal Date <font color="#ff2121">*</font>
                                 </TD>
                                      <TD>
                                          <div align="left">
                    <input type="text" name="renewal_date" id="renewal_date"  tabindex="7"
                            
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                           
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmIns_Details.renewal_date,1);"
                         alt="Show Calendar"></img>                     
                  </div>
                                          
                                      </TD>
                                      </TR>  
                 <TR class="table">
                                      <TD>
                                        Issuing Office Address <font color="#ff2121">*</font>
                                </TD>
                                      <TD>
                                          <input type="text" name="txtissuing_address" id="txtissuing_address"  >
                                          
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
                    <input type="button" name="cmdDelete" value="DELETE" id="cmdDelete" style="display:none" onclick="doFunction('Delete','null')" tabindex="40"/>
                     </td>
                     <td>
                    <input type="button" name="cmdClear" value="CLEAR" id="cmdClear" onclick="ClearAll()" tabindex="50"/>
                     </td>
                     <td>
                    <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListAll()" tabindex="60"/>
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