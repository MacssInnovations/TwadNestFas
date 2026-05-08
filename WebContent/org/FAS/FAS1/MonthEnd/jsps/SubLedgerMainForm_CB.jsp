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
             <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <!--   <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script>   -->
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script language="javascript" type="text/javascript" src="../scripts/SubLedgerMainForm_CB.js"></script>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript"
            src="../../../../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript" language="javascript">
    
     function loadyear_month()
         {
            var yr=document.frmSubLedgerSystem.cmbFinancialYear.value;
            yr=yr.split("-");
            document.frmSubLedgerSystem.txtCB_Year.value=yr[1];
            document.frmSubLedgerSystem.txtCB_Month.value=3;
            document.frmSubLedgerSystem.year.value=yr[1];
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
  <body class="table"  onload="LoadAccountingUnitID('LIST_ALL_UNITS');">
  <form name="frmSubLedgerSystem" method="POST" action="../../../../../SubLedgerMainFormListServlet_CB.con" >
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
  int  cmbAcc_UnitCode=0,cmbOffice_code=0,subledgertype=0;

            
  %>
  
  
   
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="3">
          <div align="center">
              <strong>Closing Balance System For Sub Ledger A/c Heads </strong>
            </div>
        </td>
      </tr>
    </table>
     <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
             <tr class="table">
                    <td>
                      <div align="left" >
                              Accounting Unit Code  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);loadTransferUnit()">        
                         </select>
                      </div>
                    </td>
              </tr>
              <tr class="table">
                    <td>
                      <div align="left">
                        Accounting For Office Code <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <select size="1" name="cmbOffice_code" id="cmbOffice_code" >
                          
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
                    <select name="cmbFinancialYear" id="cmbFinancialYear" onchange="loadyear_month()">
                    <option value="">--Select Year--</option>
                    <option value="2010-2011">2010-2011</option>
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
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" disabled="disabled" onkeypress="return numbersonly(event)">
          <input type="hidden" name="year" id="year">
         
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
          <option value="3" selected="selected">March</option>
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
                    <td colspan="2"><select name="cmbMas_SL_type" id="cmbMas_SL_type" onchange="fordcb(this.value);">
                            <option value="0">--Select SubLedger Type--</option>
                            <%
                                try
                                {
                                ps=con.prepareStatement("select SUB_LEDGER_TYPE_CODE,SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES ");
                                rs=ps.executeQuery();
                                    while(rs.next())
                                    {
                                    subledgertype=rs.getInt("SUB_LEDGER_TYPE_CODE");
                                    System.out.println("subledgertype "+subledgertype);
                                    out.println("<option value="+rs.getString("SUB_LEDGER_TYPE_CODE")+">"+rs.getString("SUB_LEDGER_TYPE_DESC")+"</option>");
                                    }
                                }
                                catch(Exception e)
                                {
                                	e.printStackTrace();
                                System.out.println("Exception in Reason combo..."+e);
                                }
                                finally
                                {
                                rs.close();
                                ps.close();
                                }   
                      %>
                        </select>
                        <div id="benifici" style="display:none">
                   <select size="1" name="dcb_ben_type" id="dcb_ben_type"   onchange="call('get','null')"  >
                      <option value="">--Select Type--</option>
                      <%
                        try
                        {
                        ps=con.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_DESC from PMS_DCB_BEN_TYPE order by BEN_TYPE_ID");
                        rs=ps.executeQuery();
                            while(rs.next())
                            {
                            	System.out.println("subledgertype "+rs.getInt("BEN_TYPE_ID"));
                            out.println("<option value="+rs.getInt("BEN_TYPE_ID")+">"+rs.getString("BEN_TYPE_DESC")+"</option>");
                            }
                        }
                        catch(Exception e)
                        {
                        	e.printStackTrace();
                        System.out.println("Exception in Reason combo..."+e);
                        }
                        finally
                        {
                        rs.close();
                        ps.close();
                        }   
                      %>
                    </select>
                  </div>
                        <!--<input type="text" name="txtsltype" id="txtsltype"></input>-->
                    </td>
                </tr>
                <tr align="left">
                    <td>Sub-Ledger Code:
                    </td>
                    <td align="left" colspan="2">
                        <select name="cmbMas_SL_Code" id="cmbMas_SL_Code" >
                        <option value="">--Select SubLedger Code--</option>  
                        <%
                      
                                try
                                {
                                ps=con.prepareStatement("select SL_CODE,SL_CODENAME from SL_TYPE_CODE_NAME_VIEW where SL_TYPE=?");
                                ps.setInt(1,subledgertype); 
                                rs=ps.executeQuery();
                                    while(rs.next())
                                    {
                                    out.println("<option value="+rs.getInt("SL_CODE")+">"+rs.getString("SL_CODENAME")+"</option>");
                                    }
                                }
                                catch(Exception e)
                                {
                                	e.printStackTrace();
                                System.out.println("Exception in SLCODE combo..."+e);
                                }
                                finally
                                {
                                rs.close();
                                ps.close();
                                }   
                                System.out.println(subledgertype);
                      %>
                        </select>
                          <div align="left" id="offlist_div_master"  style="display:none">
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" onclick="jobpopup_master();"></img>
                          </div>
                           <div align="left" id="emplist_div_master"  style="display:none">
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="employee_popup_master();"></img>
                            <input type="text" name="txtEmpID_mas" id="txtEmpID_mas" maxlength="5" size="5"  onblur="mas_employee(this.value);" />
                            </div>
                          <!--<input type="text" name="txtslcode" id="txtslcode"></input>-->
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
       <!-- <tr align="left">
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
        </tr>-->
</table>
          </div>
          <table align="center"  cellspacing="3" cellpadding="2" border="0"  >
  
                <tr>
                <td>
                  <input type="button" name="cmdAdd" disabled value="ADD" id="cmdAdd" onclick="doFunction('Add_FCheck','null')"/>
                  </td><td>
                  <input type="button" name="cmdUpdate" disabled value="UPDATE" style="display:none" id="cmdUpdate" onclick="doFunction('Update_FCheck','null')"/>
                  </td><td>
                  <input type="button" name="cmdDelete" disabled value="DELETE" style="display:none" id="cmdDelete" onclick="doFunction('Delete_FCheck','null')"/>
                  </td>
                  <td>
                  	<input type="submit" name="report" value="REPORT" id="report"/>
                  </td>
                  <td>                  
                  <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListAll()"/>
                  </td><td>
                  <input type="button" name="cmdCancel" value="EXIT" id="cmdCancel" onclick="Exit()"/>
                  </td><td>
                  <input type="button" name="cmdClear" value="CLEARALL" id="cmdClear" onclick="clearall()"/>
               </td></tr>
      
      </table>
      </form>
  
  </body>
</html>