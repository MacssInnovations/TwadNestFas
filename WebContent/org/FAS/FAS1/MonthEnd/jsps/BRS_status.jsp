<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="/org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>BRS TB Freeze Status</title>
      
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
       
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case_FinalHead_GJV.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType_NegativeAmtAllowed.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
   <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>


<script type="text/javascript"  src="../scripts/BRS_status.js"></script>
  
    <script type="text/javascript" language="javascript">
         function foc()
         {
         }
         function loadDate()
         {
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         document.BRS_statusform.BRStxtCB_Month.value=month;
        // document.BRS_statusform.TBtxtCB_Month.value=month;
         if(day<=9 && day>=1)
         day="0"+day;
         if(month<=9 && month>=1)
         month="0"+month;
         var year=today.getYear();
         if(year < 1900) year += 1900;
         var monthArray =new Array("January", "February", "March", 
                   "April", "May", "June", "July", "August",
                   "September", "October", "November", "December");
        
        document.BRS_statusform.BRStxtCB_Year.value=year;
     
         }
        
   
</script>
  </head>
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');loadDate();setTimeout('callMaxMonth()',300);">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">BRS Status</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="BRS_statusform" id="BRS_statusform" method="" action="">
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
             }
            catch(Exception e)
            {
                System.out.println(e);
            }
    %>
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong>Office Details</strong>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Office&nbsp;Name</div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAcc_unitName"
                           id="txtAcc_unitName" value="<%=oname%>"
                           maxlength="60" size="60" readonly="readonly"
                           class="disab"/>
                  </div>
                </td>
              </tr>
              <tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong>General Details</strong>
                  </div>
                </td>
              </tr>
               <tr class="table">
                    <td>
                      <div align="left" >
                              Accounting Unit Code  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);callMaxMonth();">        
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
              
               <tr class="table">
					<td>
					<div align="left">Bank A/C No.</div>
					</td>
					<td>
					<div align="left"><select name="cmbBankAccNo" id="cmbBankAccNo">
						
						<option value="t">-- Select Bank A/C No ---</option>
					</select> <input type="button" name="Go" id="Go" value="Go"
						onclick="LoadBankAccountNumber();callMaxMonth();" />
					<input type="hidden"
						name="txtOprMode" id="txtOprMode" tabindex="5"
						style="background-color: #ececec" readonly="readonly" size="50" /></div>
					</td>
				</tr>
              
               <tr class="table">
                <td>
                  <div align="left">BRS completed for the Cash book Year and Month (Manual)<font color="#ff2121">*</font></div>
                </td>
                <td>
                   <div align="left">
                        <input type="text" name="BRStxtCB_Year" id="BRStxtCB_Year"
                               tabindex="3" maxlength="4" size="5"
                               onkeypress="return numbersonly(event)"></input>
                         
                        <select name="BRStxtCB_Month" id="BRStxtCB_Month"
                                tabindex="4">
                           <option value="01">January</option>
                           <option value="02">February</option>
                           <option value="03">March</option>
                           <option value="04">April</option>
                           <option value="05">May</option>
                           <option value="06">June</option>
                           <option value="07">July</option>
                           <option value="08">August</option>
                           <option value="09">September</option>
                           <option value="10">October</option>
                           <option value="11">November</option>
                           <option value="12">December</option>
                        </select>
                     </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">BRS completed in Online<font color="#ff2121">*</font></div>
                </td>
                <td>
                   <input type="radio" name="onlineopt" value="yes" id="onlineopt" checked="checked" onclick="showWindow1();"/>Yes
                   <input type="radio" name="onlineopt" value="no" id="onlineopt" onclick="hideWindow1();"/>No
                   
                </td>
              </tr>
             
              <tr class="table" >
              
                <td> 
                  <div align="left" style="display:block" id="labelonline">BRS completed for the Cash book Year and Month (Online)<font color="#ff2121">*</font></div>
                </td>
                <td>
                   <div align="left" style="display:block" id="textonline">
                        <input type="text" name="onlinetxtCB_Year" id="onlinetxtCB_Year"
                               tabindex="3" maxlength="4" size="5"
                               onkeypress="return numbersonly(event)"></input>
                         
                        <select name="onlinetxtCB_Month" 
                        id="onlinetxtCB_Month" onchange="checkMonthlyClosure();"  tabindex="4">
                         <option value="0">Select</option>
                           <option value="01">January</option>
                           <option value="02">February</option>
                           <option value="03">March</option>
                           <option value="04">April</option>
                           <option value="05">May</option>
                           <option value="06">June</option>
                           <option value="07">July</option>
                           <option value="08">August</option>
                           <option value="09">September</option>
                           <option value="10">October</option>
                           <option value="11">November</option>
                           <option value="12">December</option>
                        </select>
                     </div>
                </td>
              </tr></div>
              <tr class="table">
                <td>
                  <div align="left">TB to be Freezed upto the Cash book Year and Month</div>
                </td>
                <td><div align="left">
                        <input type="text" name="TBtxtCB_Year" id="TBtxtCB_Year"
                               tabindex="3" maxlength="4" size="5" disabled
                               onkeypress="return numbersonly(event)"></input>
                         
                        <select name="TBtxtCB_Month" id="TBtxtCB_Month" disabled
                                tabindex="4">
                       </select>
                     </div>
                </td>
              </tr>
              
             
              
               <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
                    <td>
                    <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="call('Add')" tabindex="20"/>
                     </td>
                     <td>
                    <input type="button" name="cmdUpdate" value="UPDATE" id="cmdUpdate" style="display:none" onclick="call('Update')" tabindex="30"/>
                     </td>
                    <td>
                    <input type="button" name="cmdDelete" value="DELETE" id="cmdDelete" style="display:none" onclick="call('Delete')" tabindex="40"/>
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
                </div>
              </form>
              </body>
              </html>
             