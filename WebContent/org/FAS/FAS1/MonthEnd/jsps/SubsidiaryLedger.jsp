<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Subsidiary Ledger System</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script language="javascript" type="text/javascript" src="../scripts/SubsidiaryLedger.js"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript" language="javascript">
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        //document.frmGeneralLedgerSystem.txtCB_Year.value=year
        //document.frmGeneralLedgerSystem.txtCB_Month.value=month;
        
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
  <body class="table" onload="loadfyr()">
  <form name="frmSubsidiaryLedger" method="POST" action="" onsubmit="return checknull()">
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
        /*HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();*/
    int empid=10099;
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
              <strong>Subsidiary Ledger A/C Head System</strong>
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
                            ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?");
                            ps.setInt(1,oid);
                            rs=ps.executeQuery();
                            int unit_id[]=new int[40];
                            int i=0;
                            String unit_name[]=new String[40];
                              while(rs.next())
                              {
                              ++i;
                              unit_id[i]=rs.getInt("ACCOUNTING_UNIT_ID");
                              out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                              unit_name[i]=new String(rs.getString("ACCOUNTING_UNIT_NAME"));
                              System.out.println(unit_id[i]);
                              System.out.println(unit_name[i]);
                              }
                          System.out.println(unit_id.length);
                          System.out.println(oid+" "+oname);
                          ps.close();
                          rs.close();
                          }
                              else
                              {
                                ps=con.prepareStatement("select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?");
                                ps.setInt(1,oid);
                                rs=ps.executeQuery();
                                 if(rs.next())
                                    unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                ps.close();
                                rs.close();
                                ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
                                ps.setInt(1,unitid);
                                rs=ps.executeQuery();
                                  if(rs.next())
                                  {
                                  System.out.println(rs.getInt("ACCOUNTING_UNIT_ID"));
                                  unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                  unitname=rs.getString("ACCOUNTING_UNIT_NAME");
                                  //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                                  out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
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
                    <option value="0">--Select Office Code--</option>  
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
                <td class="table">Financial Year:<font color="#ff2121">*</font></td>
                <td class="table">
                <select name="cmbFinancialYear" id="cmbFinancialYear">
                <option value="0">--Select Financial Year--</option>
                </select>
              </tr>
                <!--<tr align="left">
          <td class="table">
          <div align="left">
              Cash Book Year &amp; Month
              </div>
            </td>
            <td>
             <div align="left">
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
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
          </td>
        </tr>-->
         <tr>
          <td align="left">Account Head Code:<font color="#ff2121">*</font></td>
          <td align="left">
                            <select name="cmbAccHeadCode" id="cmbAccHeadCode">
                            <option value="0">--Select Account Head Code--</option>
                            <%
                            try
                            {
                                ps=con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from com_mst_account_heads order by account_head_desc");
                                rs=ps.executeQuery();
                                while(rs.next())
                                {
                                    out.println("<option value="+rs.getInt("ACCOUNT_HEAD_CODE")+">"+rs.getString("ACCOUNT_HEAD_DESC")+"</option>");
                                }
                            
                            }catch(Exception e)
                            {
                                System.out.println("Exeception in Account Head Code:"+e);
                            }
                            
                            
                            %>
                            </select>
                        </td>
                    </tr>
                <tr align="left">
                    <td> Sub-Ledger Type :</td>
                    <td><select name="cmbMas_SL_type" id="cmbMas_SL_type" onchange="doFunction('Load_MasterSL_Code','null');typedesc();">
                            <option value=="0">--Select SubLedger Type--</option>
                            <%
                                try
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
                                }   
                      %>
                        </select>
                    </td>
                </tr>
                <tr align="left">
                    <td> Sub-Ledger Code :</td>
                    <td>
                    <table align="left">
                     <tr align="left">
                     <td>
                          <div align="left">
                                <select size="1" name="cmbMas_SL_Code" id="cmbMas_SL_Code"  tabindex="7" >
                                        
                                  <option value="">--Select Code--</option>
                                </select>
                          </div>
                      </td>
                      <td>
                          <div align="left" id="offlist_div_master"  style="display:none">
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" onclick="jobpopup_master();"></img>
                          </div>
                       </td>
                     
                    </tr>
                   </table>
                   </td>
                </tr>
                <tr align="left">
                    <td>Sub-Ledger Description </td>
                    <td><input type="text" name="txtsubledgerdesc" size="45" disabled></input></td>
                </tr>
                <tr align="left">
                    <td>Project Scheme Code</td>
                    <td><input type="text" name="txtprojectcode" size="45" ></td>
                </tr>
                <tr align="left">
                    <td>Upto Date Credit Balance</td>
                    <td><input type="text" name="txtuptocredit" size="20" maxlength="17"><input type="button" value="View Credit Details" onclick="creditdetails()"></td>
                </tr>
                <tr align="left">
                    <td>Upto Date Debit Balance</td>
                    <td><input type="text" name="txtuptodebit" size="20" maxlength="17"><input type="button" value="View Debit Details" onclick="debitdetails()"></td>
                </tr>
                <tr align="left">
                    <td>Current year Credit Balance</td>
                    <td><input type="text" name="txtcurrentcredit" size="20" maxlength="17">
                    </td>
                </tr>
                <tr align="left">
                     <td>Current year Debit Balance</td>
                     <td><input type="text" name="txtcurrentdebit" size="20" maxlength="17">
                     
                     </td>
                </tr>
                <tr align="left">
                    <td>Last Date CR Updated</td>
                    <td><input type="text" name="txtlastcrdate" size="20" maxlength="10"></td>
                </tr>
                <tr align="left">
                    <td>Last Date DR Updated</td>
                    <td><input type="text" name="txtlastdrdate" size="20" maxlength="10"></td>
                </tr>
                </table>
          </div>
          <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
  
      <tr class="tdH">
      <td>
          <div align="center">
          <input type=submit value=Submit >
         <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="closeWindow()">
      </div>
      </td>
      </tr>
      
      </table>
      </form>
  </body>
</html>