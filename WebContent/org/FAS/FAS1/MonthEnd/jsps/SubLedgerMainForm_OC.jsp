<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Sub Ledger Posting</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript"
             src="../../../../Library/scripts/checkDate.js"></script>
    
    <script language="javascript" type="text/javascript" src="../scripts/SubLedgerMainForm_OC.js"></script>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript"
            src="../../../../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript" language="javascript">
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmSubLedgerSystem.txtCB_Year.value=year
        document.frmSubLedgerSystem.txtCB_Month.value=month;
        
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
  <body class="table" onload="loadyear_month();loadfyr()">
  <form name="frmSubLedgerSystem" method="POST" action="" >
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
    // int empid=10099;
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
        <td colspan="3">
          <div align="center">
              <strong>Opening Balance System For Sub Ledger A/c Heads </strong>
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
                <td colspan="2">
                  <div align="left">
                    <!--<input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>-->
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1">
                     <!-- <option value="0"> Select Account Unit </option>-->
                      <%
                      int unitid=0;
                      String unitname="";
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
                              
                              }
                          System.out.println(oid+" "+oname);
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
                <td colspan="2">
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                    <!--<option value="0">--Select Office Code--</option>  -->
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
                         Financial Year:
              </div>
              </td>
              <td colspan="2">
                    <select name="cmbFinancialYear" id="cmbFinancialYear">
                    </select>
              </td>
              </tr>
                <tr align="left">
          <td class="table">
          <div align="left">
              Cash Book Year &amp; Month
              </div>
            </td>
            <td colspan="2">
             <div align="left">
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
          <!--<option value="">select the Month</option>-->
          
          <option value="3">March</option>
          
          </select>
           </div>
          </td>
        </tr>
        <tr class="table" align="left">
          <td>Account Head Code 
                 <font color="#ff2121">
                  *
                </font>
          
          </td>
          <td align="left" colspan="2">
           <input type="text" name="cmbAcHeadCode"
                           id="cmbAcHeadCode" maxlength="8"
                           onkeypress="return numbersonly1(event)"
                            onchange="sixdigit();"  onblur="doFunction('checkCode','null')"
                            size="9"/>
          <img src="../../../../../images/c-lovi.gif" width="20"
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
                             <input type="text" name="txtaccountheadname" id="txtaccountheadname" size="45" disabled>
          </td>
        </tr>    
        <tr align="left">
                    <td> Sub-Ledger Type :</td>
                    <td colspan="2"><select name="cmbMas_SL_type" id="cmbMas_SL_type" onchange="doFunction('Load_MasterSL_Code','null');">
                            <option value="0">--Select SubLedger Type--</option>
                            <%
                                /*try
                                {
                                ps=con.prepareStatement("select SUB_LEDGER_TYPE_CODE,SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES ");
                                rs=ps.executeQuery();
                                    while(rs.next())
                                    {
                                    out.println("<option value="+rs.getString("SUB_LEDGER_TYPE_CODE")+">"+rs.getString("SUB_LEDGER_TYPE_DESC")+"</option>");
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
                                }  */ 
                      %>
                        </select>
                    </td>
                </tr>
                <tr align="left">
                    <td>Sub-Ledger Code:
                    </td>
                    <td align="left" colspan="2">
                        <select name="cmbMas_SL_Code" id="cmbMas_SL_Code">
                        <option value="0">--Select SubLedger Code--</option>
                        </select>
                          <div align="left" id="offlist_div_master"  style="display:none">
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" onclick="jobpopup_master();"></img>
                          </div>
                       </td>
                </tr>
                
                <tr style="display:none">
                    <td>Project / Scheme Code:</td>
                    <td align="left" colspan="2">
                        <input type="txtProject_code" id="txtProject_code" size="25">
                    </td>
                </tr>
                
        
        
        <tr align="left">
            <td >Closing Balance:
            </td>
            <td>
                <input type="text" name="txtCloseBal" maxlength="17" size="16"
                   id="txtCloseBal" onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);"/>
                   <input type=radio name="radCloseBalCrDrInd" id="radCloseBalCrDrInd" checked value="CR">CR
                <input type=radio name="radCloseBalCrDrInd" id="radCloseBalCrDrInd" value="DR">DR
            </td>
        </tr>
        <!---tr align="left">
            <td>Last Update Date:</td>
            <td>
                DR<input type="text" name="txtDrLUpdate" maxlength="10" size="10"
                   id="txtDrLUpdate" onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);" onblur="return checkdt1(this);" disabled/>
                   <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmSubLedgerSystem.txtDrLUpdate);"
                         alt="Show Calendar"></img>
                 CR<input type="text" name="txtCrLUpdate" maxlength="10" size="10"
                   id="txtCrLUpdate" onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);" onblur="return checkdt1(this);" disabled/>  
                   <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmSubLedgerSystem.txtCrLUpdate);"
                         alt="Show Calendar"></img>
            </td>
        </tr--->
</table>
          </div>
          <table align="center"  cellspacing="3" cellpadding="2" border="0"  >
  
                <tr>
                <td>
                  <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="doFunction('Add','null')"/>
                  </td><td>
                  <input type="button" name="cmdUpdate" value="UPDATE" style="display:none" id="cmdUpdate" onclick="doFunction('Update','null')"/>
                  </td><td>
                  <input type="button" name="cmdDelete" value="DELETE" style="display:none" id="cmdDelete" onclick="doFunction('Delete','null')"/>
                  </td><td>
                  <input type="button" name="cmdCancel" value="EXIT" id="cmdCancel" onclick="Exit()"/>
                  </td><td>
                  <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListAll()"/>
                  </td><td>
                  <input type="button" name="cmdClear" value="CLEARALL" id="cmdClear" onclick="clearall()"/>
               </td></tr>
      
      </table>
      </form>
  
  </body>
</html>