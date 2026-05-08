<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>BRS Office Change Form</title>
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
                 document.changeOff.txtCB_Year.value=year; 
                // document.paaApproval.rejectedDate.value=day+"/"+month+"/"+year; 
                 //document.paaApproval.cbmonth.value=month;
                 //document.paaApproval.cbyear.value=year;
               //  setTimeout('callmeth1()',900);      
        }
        function fun(v)
        {
        	
        	if(v=="Y")
        		{
        		document.getElementById("id1").style.display="block"; 
        		document.getElementById("id2").style.display="block"; 
        				document.getElementById("id3").style.display="block"; 
        						document.getElementById("id4").style.display="block"; 
        						document.getElementById("hid_rad").value="Y"; 
        		}else
        			
            		{
        			document.getElementById("id1").style.display="none"; 
            		document.getElementById("id2").style.display="none"; 
            				document.getElementById("id3").style.display="none"; 
            						document.getElementById("id4").style.display="none"; 
            						document.getElementById("hid_rad").value="N"; 
            		}
        }
        
       
</script>
   </head>
   <%String s=request.getContextPath(); %>
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS'),LoadAccountingUnitIDMTC('LIST_ALL_UNITS'),loadDate();">
  <form  name="changeOff"  action="upload_civil.jsp" ENCTYPE="multipart/form-data" method="post"> 
     <input type="hidden" id="hid_rad" name="hid_rad" value="N">
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
            
                     //   ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
                                <font size="4"> BRS Office Change Form </font>
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
							id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);refresh_new();">
				
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
							id="cmbOffice_code" >
				
						</select></div>
						</td>
					</tr>
					<tr class="table">
		<td>
	    <div align="left">Bill Year<font color="#ff2121">*</font></div></td>
		<td>
		  <div align="left">
		    <input type="text" name="txtCB_Year" onchange="refresh_new();"
			id="txtCB_Year" maxlength="4" size="4%"
			onkeypress="return numbersonly1(event,this)">
      </div></td>
	</tr>
	<tr class="table">
		<td>
	    <div align="left">Bill Month<font color="#ff2121">*</font></div></td>
		<td>
		  <div align="left">
		    <select name="txtCB_Month" id="txtCB_Month" onChange="refresh_new();loadbillDetails();">
		      <option value="s">--- Select ---</option>
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
      </div></td>
	</tr>
	 <tr class="table">
						<td>
						<div align="left"><font color="red" >Do you want change the Payment Office </font><font color="#ff2121">*</font>
						</div>
						</td>
						<td>
						<div align="left">
						<input type="radio" value="N"  name="rad1" checked="checked" onclick="fun('N')">NO
							<input type="radio" value="Y"  name="rad1" onclick="fun('Y')">YES
						</div>
						</td>
						</tr>
	 <tr class="table">
						<td>
						<div align="left" id ="id1" style="display: none;">Change Accounting Unit Code <font color="#ff2121">*</font>
						</div>
						</td>
						<td>
						<div align="left" id ="id2" style="display: none;" ><select size="1" name="cmbAcc_UnitCode1"
							id="cmbAcc_UnitCode1" onchange="common_LoadOfficeMTC(this.value);">
				
						</select></div>
						</td>
					</tr>
					<tr class="table">
						<td>
						<div align="left" id ="id3" style="display: none;">Change Accounting For Office Code <font
							color="#ff2121">*</font></div>
						</td>
						<td>
						<div align="left" id ="id4" style="display: none;" ><select size="1" name="cmbOffice_code1"
							id="cmbOffice_code1" >
				
						</select></div>
						</td>
					</tr>
	
					<tr>
                    <td class="table" width="40%" align="left">Bill No</td>
                    <td class="table" align="left">
                    	 <select name="BillNo" id="BillNo" onchange="loadbillMTC(this.value);" >
                         	<option value="">select</option>
                         </select>
                         
                    </td>
                </tr>
                   <tr>
                 <td class="table" width="40%" align="left">Bill Major Type</td>
                  <td class="table" align="left"> 
                        <input type="hidden" name="txtMajor" class="light" id="txtMajor"/>  
                        <input type="text" name="txtMajor1" class="light" id="txtMajor1" disabled="disabled"/>
                  </td>               
                </tr>
                   <tr>
                 <td class="table" width="40%" align="left">Bill Minor Type</td>
                  <td class="table" align="left"> 
                        <input type="hidden" name="txtMinor" class="light" id="txtMinor"/>
                        <input type="text" name="txtMinor1" class="light" id="txtMinor1" disabled="disabled"/>  
                  </td>               
                </tr>
                   <tr>
                 <td class="table" width="40%" align="left">Bill Sub Type</td>
                  <td class="table" align="left"> 
                        <input type="hidden" name="txtSub" class="light" id="txtSub"/>
                         <input type="text" name="txtSub1" class="light" id="txtSub1" disabled="disabled"/>   
                  </td>               
                </tr>
                   <tr>
                 <td class="table" width="40%" align="left">Total Sanction Proceeding No</td>
                  <td class="table" align="left"> 
                        <input type="text" name="SanctionPro" class="light" id="SanctionPro" disabled="disabled"/>  
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
                   <td class="table" width="40%" align="left">Pass Order Date</td>
                   <td class="table" align="left"> 
                         <input type="text" name="passOrderDate" class="light" size="20" maxlength="10" id="passOrderDate" disabled="disabled"/>
                           
                    </td>               
                </tr>
                <tr>
                  <td class="table" width="40%" align="left">Pass Order Prepared by</td>
                  <td class="table" align="left"> 
                      <input type="hidden" name="passOrderPrepared" id="passOrderPrepared" />  
                        <input type="text" name="passOrderPrepared1" class="light" id="passOrderPrepared1" disabled="disabled" />
                  </td>               
                </tr>
                <tr>
                 <td class="table" width="40%" align="left">Pass Order Amount</td>
                  <td class="table" align="left"> 
                        <input type="text" name="passOrderAmt" class="light" id="passOrderAmt" disabled="disabled"/>  
                  </td>               
                </tr>
               <!--  <tr> 
			 		<td style="width: 208px" class="table">Drawing Office Id</td>
					<td class="table">  
						<input maxlength="4" name="txtEmpId"  id="txtEmpId" size="4" value="" onblur="Load_emp_details();" onkeypress="return numbersonly1(event,this)" style="width: 108px"><img src="../../../../../images/c-lovi.gif" width="20" height="20"  onclick="servicepopup();" alt=""/>						
					</td>
				</tr> -->
				<!-- <tr>
                  <td class="table" width="40%" align="left">Name and Designation</td>
                  <td class="table" align="left"> 
                      <input type="text" name="txtemp_name" id="txtemp_name" class="light" style="width: 30%;" disabled="disabled"/>  
                  </td>  -->              
                </tr>
               <!--  <tr>
                    <td class="table" width="40%" align="left">Bill Approved or Rejected</td>
                    <td class="table" align="left">                	
                        <input type="radio" name="approveReject" checked="checked" value="Y" onclick="isApprove()"/>
                              Approved
                         <input type="radio" name="approveReject" value="N" onclick="isApprove()"/>
                              Rejected 
                    </td>
                </tr> -->
                <tr>
                   <td class="table" width="40%" align="left">Net Amount</td>
                   <td class="table" align="left"> 
                         <input type="text" name="netamt" class="light"  disabled="disabled" id="netamt" >
                         
                          
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
                    
     <input name="onsub" value="Submit" id="onsub" onclick="callServer('savechangeOff');" type="button">  
                         
                    </div> 
               </td> 
            </tr>
             </table>
        </div>
            </form>    
           
  </body>  
</html>