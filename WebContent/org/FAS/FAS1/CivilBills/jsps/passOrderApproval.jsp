<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Pass Order Approval Details</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    <script type="text/javascript" src="../../Reports/ReceiptSystem/scripts/CalendarControl.js"></script> 
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">          </script>
    <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/passOrderApproval.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Cheque_Number_Check_FR_byHO.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/PaymentSystem/scripts/Common_PaymentType.js"></script>
    <script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
	
     <script type="text/javascript" src="../../CalendarController.js"></script> 
     <script type="text/javascript" language="javascript">
        
        function loadDate()
        {
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
                 document.paaApproval.approvalDate.value=day+"/"+month+"/"+year; 
                 document.paaApproval.rejectedDate.value=day+"/"+month+"/"+year; 
                 //document.paaApproval.cbmonth.value=month;
                 //document.paaApproval.cbyear.value=year;
                 setTimeout('callmeth1()',900);      
        }
        
       
</script>
   </head>
   <%String s=request.getContextPath(); %>
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS'),loadDate(),setTimeout('Load_emp_details()',900);">
  <form  name="paaApproval"  action="upload_civil.jsp" ENCTYPE="multipart/form-data" method="post"> 
     
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
                <div class="tab-page">                    
                    <div align="center">
                    <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
                    <tr class="tdH">
                    <td  colspan="2">
                          <div align="center">
                                <font size="4"> Pass Order Approval Details </font>
                          </div>
                    </td>
                	</tr>
                     <tr class="table">
						<td>
						<div align="left">Accounting Unit Code <font color="#ff2121">*</font>
						</div>
						</td>
						<td>
						<div align="left"><select size="1" name="cmbAcc_UnitCode"
							id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);setTimeout('callmeth1()',900);">
				
						</select></div>
						</td>
					</tr>
					<tr class="table">
						<td>
						<div align="left">Accounting For Office Code <font
							color="#ff2121">*</font></div>
						</td>
						<td>
						<div align="left"><select size="1" name="cmbOffice_code"
							id="cmbOffice_code" onchange="Load_emp_details(),callmeth1();">
				
						</select></div>
						</td>
					</tr>
					<tr>
                    <td class="table" width="40%" align="left">Pass Order No</td>
                    <td class="table" align="left">
                    	 <select name="passOrderNo" id="passOrderNo" onchange="callServer('passorderdetails')" >
                         	<option value="">select</option>
                         </select>
                          <input type="hidden" name="passyear" id="passyear" ></input>
                          <input type="hidden" name="bill_num" id="bill_num" ></input>
                    </td>
                </tr>
               
             <tr>
                 <td class="table" width="40%" align="left">Total Sanction Amount</td>
                  <td class="table" align="left"> 
                        <input type="text" name="totalSanctionAmt" class="light" id="totalSanctionAmt" disabled="disabled"/>  
                  </td>               
                </tr>
                <tr>
                 <td class="table" width="40%" align="left">Total Deduction Amount</td>
                  <td class="table" align="left"> 
                        <input type="text" name="totalDeductionAmt" class="light" id="totalDeductionAmt" disabled="disabled"/>  
                  </td>               
                </tr>
                <tr>
                 <td class="table" width="40%" align="left">Net Amount</td>
                  <td class="table" align="left"> 
                        <input type="text" name="netAmt" class="light" id="netAmt" disabled="disabled"/>  
                  </td>               
                </tr>
                <tr>
                  <td class="table" width="40%" align="left">Bill Particulars View</td>
                  <td class="table" align="left"> 
                     <a href="#" onclick="viewDetails('<%=s%>')">View Bill Details</a>
                  </td>               
                </tr>
                <tr>
                   <td class="table" width="40%" align="left">Pass Order Date</td>
                   <td class="table" align="left"> 
                         <input type="text" name="passOrderDate" class="light" size="20" maxlength="10" id="passOrderDate" disabled="disabled"/>
                           
                    </td>               
                </tr>
                <tr>
                  <td class="table" width="40%" align="left">Pass Order Prepared by</td>
                  <td class="table" align="left"> 
                      <input type="text" name="passOrderPrepared" class="light" id="passOrderPrepared" disabled="disabled" />  
                  </td>               
                </tr>
                <tr>
                 <td class="table" width="40%" align="left">Pass Order Amount</td>
                  <td class="table" align="left"> 
                        <input type="text" name="passOrderAmt" class="light" id="passOrderAmt" disabled="disabled"/>  
                  </td>               
                </tr>
                <tr> 
			 		<td style="width: 208px" class="table">Drawing Office Id</td>
					<td class="table">  
						<input maxlength="4" name="txtEmpId"  id="txtEmpId" size="4" value="" onblur="Load_emp_details();" onkeypress="return numbersonly1(event,this)" style="width: 108px"><!-- <img src="../../../../../images/c-lovi.gif" width="20" height="20"  onclick="servicepopup();" alt=""/>						
					--></td>
				</tr>
				<tr>
                  <td class="table" width="40%" align="left">Name and Designation</td>
                  <td class="table" align="left"> 
                      <input type="text" name="txtemp_name" id="txtemp_name" class="light" style="width: 30%;" disabled="disabled"/>  
                  </td>               
                </tr>
                <tr>
                    <td class="table" width="40%" align="left">Bill Approved or Rejected</td>
                    <td class="table" align="left">                	
                        <input type="radio" name="approveReject" checked="checked" value="Y" onclick="isApprove()"/>
                              Approved
                         <input type="radio" name="approveReject" value="N" onclick="isApprove()"/>
                              Rejected 
                    </td>
                </tr>
                <tr>
                   <td class="table" width="40%" align="left">Approval Date</td>
                   <td class="table" align="left"> 
                         <input type="text" name="approvalDate" STYLE="background-color:#E4F470" size="20" maxlength="10" id="approvalDate" onFocus="javascript:vDateType='3'"
                                onblur="checkpassdt();"   onkeypress="return  calins(event,this)" onblur="return checkdt(this);">
                          <div id="appImg" style="display: inline;">          
                         	 <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.forms[0].approvalDate);" alt="Show Calendar" ></img>  
                         </div>  
                    </td>               
                </tr>
                <tr>
                   <td class="table" width="40%" align="left">Rejected Date</td>
                   <td class="table" align="left"> 
                         <input type="text" name="rejectedDate" size="20" maxlength="10" id="rejectedDate" disabled="disabled"  onblur="checkpassdtRe();" onFocus="javascript:vDateType='3'"
                                    onkeypress="return  calins(event,this)" onblur="return checkdt(this);">
                          <div id="regImg" style="display: none;">          
                           	<img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.forms[0].rejectedDate);" alt="Show Calendar" ></img> 
                         </div>  
                    </td>               
                </tr>              	 
                <tr>
                   <td class="table" width="40%" align="left">If Rejected,Reason for rejection</td>
                   <td class="table" align="left"> 
                   		<textarea name="rejectReason" id="rejectReason" disabled="disabled" cols="50" tabindex="6" rows="4"></textarea>
                   </td>               
                </tr>               
                </table>
                </div>
            </div>       
        <div align="center">
           <table cellspacing="1" cellpadding="3" width="100%">
            <tr class="tdH">
                <td  colspan="2">
                    <div align="center">                  
                        <input type="button" name="onupdate" value="Update" id="onupdate" onclick="callServer('update');"/> 
                        <input type="button" name="ondelete" value="Delete" id="ondelete" onclick="callServer('delete');" disabled="disabled"/> 
                  <!--  <input type="button" name="butview" id="butview" value="View Details" onClick="viewDetails('<%=s %>');" />        
                        <input type="button" name="onlist" value="List" id="onlist" onclick="listpopup();" />
                        --><input type="button" name="onclear" value="ClearAll" id="onclear" onclick="clearAll();" /> 
                        <input type="button" name="oncancel" value="Exit" id="oncancel" onclick=" self.close();" /> 
                    </div> 
               </td> 
            </tr>
             </table>
        </div>
            </form>    
           
  </body>  
</html>