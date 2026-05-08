<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache">
    <title>Bank Balance Montoring System-Collection A/C</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript"
             src="../../../../Library/scripts/checkDate.js"></script>
    
    <script language="javascript" type="text/javascript" src="../scripts/BankBalanceMontoring.js"></script>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript"
            src="../../CalendarCtrl.js"></script> 
    <script type="text/javascript" language="javascript">
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmBankBalance.txtCB_Year.value=year
        document.frmBankBalance.txtCB_Month.value=month;
        //document.frmPhysicalCashBalance.txtverify_date.value=day+"/"+month+"/"+year;
         }
    </script>
    <script type="text/javascript" language="javascript">
    
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
  <body class="table" onload="loadyear_month();callServer(this.value,'Office')" >
  <form name="frmBankBalance" method="POST" action="" onsubmit="return checknull()">
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

           // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
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
    //int empid=10099;
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
              <strong>Bank Balance Montoring System-Collection A/C</strong>
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
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" >
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
              
              <!--<tr class="table">
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
                /*try
                {
                   if(oid==5000)
                    {
                        out.println("<option value="+oid+">"+"HEAD OFFICE"+"</option>");
                    }
                    else
                    {
                        ps=con.prepareStatement("select ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID  from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_UNIT_ID=?");
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
                
                int accountoffice=0;
                try
                {
                        ps=con.prepareStatement("select ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID  from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?");
                        ps.setInt(1,oid);
                        rs=ps.executeQuery();
                        if(rs.next())
                        {
                            ps2=con.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");  
                            ps2.setInt(1,rs.getInt("ACCOUNTING_UNIT_ID"));
                            rs2=ps2.executeQuery();
                            if(rs2.next())
                            {
                                accountoffice=rs2.getInt("accounting_unit_office_id");
                                System.out.println("accountoffice:"+accountoffice);
                            }
                        }
                }catch(Exception e){System.out.println("Exception in Unit Office:"+e);}
                finally
                {
                ps.close();
                ps2.close();
                rs.close();
                rs2.close();
                
                }*/
                %>
                    </select>
                    
                  </div>
                </td>
              </tr>-->
                <tr align="left">
          <td class="table">
          <div align="left">
              Cash Book Year &amp; Month
              </div>
            </td>
            <td>
             <div align="left">
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
          <!--<option value="">select the Month</option>-->
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
        
        <tr>
            <td align="left">Bank Name</td>
            <td align="left">
                <select name="cmbbank_name" id="cmbbank_name" onchange="callServer(this.value,'Branch')">
                <option value="0">--Select Bank Name--</option>
                </select>
            </td>
        </tr>
        <tr>
            <td align="left">Branch Name</td>
            <td align="left">
                <select name="cmbbranch_name" id="cmbbranch_name" onchange="callServer(this.value,'AccType')">
                <option value="0">--Select Branch Name--</option>
                </select>
              
            </td>
        </tr>
        <tr>
            <td align="left">Account Type</td>
            <td align="left">
                <select name="cmbaccount_type" id="cmbaccount_type" onchange="callServer(this.value,'AccHead')">
                <option value="0">--Select Account Type--</option>
                </select>
                <!--<input type="text" name="txtaccount_type" id="txtaccount_type" size="20" disabled>
                <input type="hidden" name="txtaccount_type1" id="txtaccount_type1" size="20">-->
            </td>
        </tr>
        <tr>
            <td align="left">Collection A/C Code</td>
            <td align="left">
            <input type="text" name="txtCol_accode" id="txtCol_accode" size="15" disabled>
            <input type="hidden" name="txtCol_accode1" id="txtCol_accode1" size="15">
            
        </tr>
        <tr>
            <td align="left">Bank Account No.</td>
            <td align="left">
            <select name="cmbbank_accno" id="cmbbank_accno" onchange="callServer(this.value,'OpeningBal')">
            <option value="0">--Select Bank Account No--</option>
            </select>
            
            </td>
        </tr>
        <tr>
            <td align="left">To Date</td>
            <td align="left"><input type="text" name="txtverify_date" id="txtverify_date" maxlength="10" onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);" onblur="if(checkdt(this)==true)computedcash();" >
            <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmBankBalance.txtverify_date);"
                         alt="Show Calendar"></img> 
            </td>
        </tr>
        <tr>
            <td align="left">Opening Balance</td>
            <td align="left">
                <input type="text" name="txtOpening_Bal" id="txtOpening_Bal" size="20" disabled>
                <input type="hidden" name="txtOpening_Bal1" id="txtOpening_Bal1" size=20>
            </td>
        </tr>
        <tr>
            <td align="left">Collection</td>
            <td align="left">
                <input type="text" name="txtCollection" id="txtCollection" size="20">
                <input type="hidden" name="txtCollection1" id="txtCollection1" size="20">
            </td>
        </tr>
        <tr>
            <td align="left">Remittance</td>
            <td align="left">
                <input type="text" name="txtRemittance" id="txtRemittance" size="20">
                <input type="hidden" name="txtRemittance1" id="txtRemittance1" size="20">
            </td>
        </tr>
        <tr>
            <td align="left">Withdrawls</td>
            <td align="left">
                <input type="text" name="txtWithdrawl" id="txtWithdrawl" size="20">
                <input type="hidden" name="txtWithdrawl1" id="txtWithdrawl1" size="20">
            </td>
        </tr>
        <tr>
            <td align="left">Closing Balance as per Collection</td>
            <td align="left">
                <input type="text" name="txtClosing_Bal_Collection" id="txtClosing_Bal_Collection" size="20">
                <input type="hidden" name="txtClosing_Bal_Collection1" id="txtClosing_Bal_Collection1" size="20">
            </td>
        </tr>
        <tr>
            <td align="left">Closing Balance as per Remittance</td>
            <td align="left">
                <input type="text" name="txtClosing_Bal_Remittance" id="txtClosing_Bal_Remittance" size="20">
                <input type="hidden" name="txtClosing_Bal_Remittance1" id="txtClosing_Bal_Remittance1" size="20">
            </td>
        </tr>
        <tr>
            <td align="left">Difference in Balance</td>
            <td align="left">
                <input type="text" name="txtDiff_Bal" id="txtDiff_Bal" size="20">
                <input type="hidden" name="txtDiff_Bal1" id="txtDiff_Bal1" size="20">
            </td>
        </tr>
</table>
          </div>
          <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
  
      <tr class="tdH">
      <td>
          <div align="center">
          <input type=button value=Ok onclick="closeWindow()" >
          <input type=button value="Clear" onclick="clear1()">
         <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="closeWindow()">
      </div>
      </td>
      </tr>
      
      </table>
      </form>
  </body>
</html>