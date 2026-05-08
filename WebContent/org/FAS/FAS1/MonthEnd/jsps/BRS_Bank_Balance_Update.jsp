<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Bank Balance Update</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript"
             src="../../../../Library/scripts/checkDate.js"></script>
       <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    <script language="javascript" type="text/javascript" src="../scripts/BRS_Bank_Balance_Update.js"></script>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript"
            src="../../../../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
            <script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
   <!-- MK@18102021 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.js" type="text/
javascript"></script> -->
<script src="https://code.jquery.com/jquery-3.6.0.js" type="text/javascript"></script>
<script src="js/jquery.validationEngine-en.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
       
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>  
    <script type="text/javascript" language="javascript">
     
      </script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
                function loadDate()
                {
                	document.getElementById("radBankBalCrDrUpdate").disabled = false;
                	 	 var today= new Date(); 
                         var day=today.getDate();
                         var month=today.getMonth();
                         month=month+1;
                         
                         if(day<=9 && day>=1)
                         day="0"+day;
                         if(month<=9 && month>=1)
                         month="0"+month;
                         var year=today.getYear();
                         if(year < 1900) year += 1900;
                         var monthArray =new Array("January", "February", "March", 
                                   "April", "May", "June", "July", "August",
                                   "September", "October", "November", "December");
                    //     document.TDA_TCA.txtCrea_date.value=day+"/"+month+"/"+year;  
                         document.frmBRS_Bank_Balance_Update.txtPS_Year.value=year;   
                         
                }
                 function undisable()
                 {
                	// alert("Test");
                	 document.getElementById("radBankBalCrDrUpdate").disabled = false;
                	 document.getElementById("txtRemarks").disabled=false;
                	 document.getElementById("txtBank_Bal_PS").disabled=false;
                 }
    </script>
  </head>
  <body onload="loadDate();loadMonth();LoadAccountingUnitID('LIST_ALL_UNITS');setTimeout('loadBankDetails()',500);undisable();">
   <%
   String s=request.getContextPath();
   %>
  <form name="frmBRS_Bank_Balance_Update" method="POST" action="../../../../../BRS_Bank_Balance_Update" >
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
              <strong>BRS Bank Balance Update </strong>
            </div>
        </td>
      </tr>
    </table>
     <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
             <tr class="table">
                    <td class="table">
                      <div align="left" >
                              Accounting Unit Code  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);loadBankDetails();">        
                         </select>
                      </div>
                    </td>
              </tr>
             <tr class="table">
              <td class="table">
                      <div align="left">
                        Accounting For Office Code <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <select size="1" name="cmbOffice_code" id="cmbOffice_code">
                          
                        </select>
                      </div>
                    </td>
              </tr>
              
         <!--  <tr class="table" >
          <td class="table">
          <div align="left">
           CashBook Year &amp; Month  <font color="#ff2121">*</font>
              </div>
            </td>
            <td colspan="2">
             
                     <div align="left">
                        <input type="text" name="txtCB_Year" id="txtCB_Year" 
                         tabindex="3" maxlength="4" size="5"
                               onkeypress="return numbersonly(event)" ></input>
                         
                        <select name="txtCB_Month" id="txtCB_Month" onchange="passSheetChange();"   onblur="passSheetChange();" tabindex="4"  >
		                      <option value="s">Select</option>
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
        </tr> -->
       
        <tr class="table">
          <td class="table">
          <div align="left">
              Pass Sheet Year &amp; Month
              </div>
            </td>
            <td colspan="2">
             <div align="left">
          <input type="text" name="txtPS_Year" id="txtPS_Year" tabindex="3" maxlength="4" size="5" onkeypress="return numbersonly(event)" onblur="loadMonth();"  onChange="checkMonthYear(); ">
       <select name="txtPS_Month" id="txtPS_Month" 
                                tabindex="4" onChange="passSheetChange();" onblur="passSheetChange();" >
                            <option value="s">Select</option>
                            			
                           
                        </select>        
  </div>
          </td>
        </tr>
        
        <tr class="table" >
          <td class="table" >
          <div align="left">
              Pass Sheet Date
              </div>
            </td>
            <td colspan="2" >
             <div align="left" >
          <input type="text"  name="txtPS_PrintDate" id="txtPS_PrintDate" tabindex="3" size="15"  onkeypress="return calins(event,this);"
                               onblur="return checkDatePS();" ><!-- return call_date(this);
                               --><img style="display:none" src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmBRS_Bank_Balance_Update.txtPS_PrintDate,1);"
                         alt="Show Calendar"></img>
          
          
           </div>
          </td>      
        </tr>
        
          <tr class="table">
          <td class="table">
          <div align="left">
              Bank A/c No 
              </div>
            </td>
            <td>
             <div align="left">
             <select size="1" name="listBank_AcNO" id="listBank_AcNO" onchange="doFunction('loadDetails','null')">
              <option value="s">Select Bank </option>
            </select>
             
           </div>
          </td>
        </tr>
       
       <tr class="table">
          <td class="table">
          <div align="left">
              Bank Balance as per Pass Sheet
              </div>
            </td>
            <td colspan="2">
             <div align="left">
          <input type="text" name="txtBank_Bal_PS" id="txtBank_Bal_PS" tabindex="3" size="15"   >
           </div>
          </td>
        </tr>
       <tr class="table">
              <td class="table">
        
        Credit or Debit 
            </td>
            <td><!--
                <input type="text" name="txtCloseBal" maxlength="17" size="16"
                   id="txtCloseBal" onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);"/>
                   -->
                <input type=radio name="radBankBalCrDrUpdate" id="radBankBalCrDrUpdate" value="CR" checked="checked">CR
                <input type=radio name="radBankBalCrDrUpdate" id="radBankBalCrDrUpdate" value="DR" disabled >DR
            </td>
        </tr>
        <tr class="table">
              <td class="table">
              Remarks</td>
	  <td width="80%">
       <textarea name="txtRemarks" id="txtRemarks" cols="50" tabindex="7" onkeypress="" rows="4"></textarea>      
	  </td>
	 
	  </tr> 
        
</table>
          </div>
          <table align="center"  cellspacing="3" cellpadding="2" border="0"  >
  
                <tr>
                <td>
                  	<input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="doFunction('Add','null')"/>
                  </td><td>
                  	<input type="button" name="cmdUpdate" disabled value="UPDATE" id="cmdUpdate" onclick="doFunction('Update','null')"/>
                  </td>
                  <!-- <td>
                  	<input type="button" name="cmdDelete" disabled value="DELETE" id="cmdDelete" onclick="doFunction('Delete','null')"/>
                  </td> -->
                 
                  <!--<td>
                  	<input type="button" name="cmdClear" value="CLEARALL" id="cmdClear" onclick="clearall()"/>
               	 </td>
               	  <td>
                  	<input type="button" name="cmdSubmit" value="SUBMIT" id="cmdSubmit" onclick="submitt()"/>
               	 </td>-->
                <td>
                  	<input type="button" name="cmdList" value="LIST" id="cmdList" onclick="callList();"/>
                  </td>
                   <td>
                  	<input type="button" name="cmdCancel" value="EXIT" id="cmdCancel" onclick="Exit()"/>                  
                  </td>
               	 </tr>
      
      </table>
      <!-- grid table display --><!--
       <table cellspacing="3" cellpadding="2" border="1" width="100%"
             align="center" >
        <tr>
          <td class="table"><b>Existing Details</b></td>
        </tr>
      </table>
      <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
             
        <tr class="tdH">
         <th width="1%">Select</th>
          <th>
            Bank Name
          </th>
          <th>
            Type of Account
          </th>
          <th>
            Account Number
          </th>
          <th>
            Bank Balance as per the pass Sheet
          </th>
           <th>
           Debit Or Credit
          </th>
          <th>
            Remarks
          </th>
        </tr>
        <tbody id="tblList" class="table">
        </tbody>
        </table>
        
         <table align="center"  cellspacing="3" cellpadding="2" border="0"  >
  
                <tr>
                <td>
                  	<input type="button" name="cmdSubmit" value="SUBMIT" id="cmdSubmit" onclick="submitt()"/>
               	 </td>
                <td>
                  	<input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListAll()"/>
                  </td>
                  <td>
                  	<input type="button" name="cmdVerify" disabled value="Verify" id="cmdVerify" onclick="Veriyf()"/>                  
                  </td>
                  </tr>
      
      </table>
      --></form>
  
  </body>
</html>