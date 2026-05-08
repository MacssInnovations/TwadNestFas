<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>General Ledger Main</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript"
             src="../../../../Library/scripts/checkDate.js"></script>
    
    <script language="javascript" type="text/javascript" src="../scripts/GeneralLedgerMainForm_OC.js"></script>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript"
            src="../../../../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
            <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>  
   
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
  <form name="frmSubLedgerSystem" method="POST" action="../../../../../GeneralLedgerMainFormServlet_OC.con" >
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
  
 
   
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="3">
          <div align="center">
              <strong>Closing Balance System For General Ledger A/c Heads </strong>
            </div>
        </td>
      </tr>
    </table>
     <div align="center">
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
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
              Cash Book Year &amp; Month
              </div>
            </td>
            <td colspan="2">
             <div align="left">
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3" maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
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
        
</table>
          </div>
          <table align="center"  cellspacing="3" cellpadding="2" border="0"  >
  
                <tr>
                <td>
                  	<input type="button" name="cmdAdd" disabled value="ADD" id="cmdAdd" onclick="doFunction('Add','null')"/>
                  </td><td>
                  	<input type="button" name="cmdUpdate" disabled value="UPDATE" style="display:none" id="cmdUpdate" onclick="doFunction('Update','null')"/>
                  </td><td>
                  	<input type="button" name="cmdDelete" disabled value="DELETE" style="display:none" id="cmdDelete" onclick="doFunction('Delete','null')"/>
                  </td><td>
                  	<input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListAll()"/>
                  </td>
                  <td>
                  	<input type="submit" name="report"  value="REPORT" id="report"/>                  
                  </td>
                  <td>
                  	<input type="button" name="cmdCancel" value="EXIT" id="cmdCancel" onclick="Exit()"/>                  
                  </td>
                  <td>
                  	<input type="button" name="cmdClear" value="CLEARALL" id="cmdClear" onclick="clearall()"/>
               	 </td></tr>
      
      </table>
      </form>
  
  </body>
</html>