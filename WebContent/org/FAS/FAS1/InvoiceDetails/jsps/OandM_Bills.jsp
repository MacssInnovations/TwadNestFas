<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
        <title>firmbills_jsp</title>
        <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
        <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
        <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
        <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
        <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
        <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
        <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>
        <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">          </script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
        <script type="text/javascript" src="../scripts/OandM_Bills.js"></script>
                <script type="text/javascript" src="../../../../../org/FAS/FAS1/BillVerification/jsps/pre_AuditDetails1.jsp"></script> 
            
        
        <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
         <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
        <script type="text/javascript" language="javascript">
	    function loadDate() {
		
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
			document.billform.txtCrea_date.value = day + "/" + month	+ "/" + year;
			call_date(document.billform.txtCrea_date); 
		
	}
       
</script>
  </head>
 <%String s=request.getContextPath(); %>
<%System.out.println(s); %>
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');callmajorType();callidval();loadDate();initialLoad('<%=s %>');setTimeout('bill_sub_office()', 900);">
         <form name="billform" id="billform"
		method="POST" action="../../../../../OandM_Bills_Serv"
		onsubmit="return checkNull()" >
		<input type="hidden" id="command" name="command" value="Add">         
            <%
                            Connection con=null;
                            Statement stmt=null;
                             ResultSet rs=null,rs2=null;
                            ResultSet results=null;
                            PreparedStatement ps=null,ps2=null;
                            String xml=null;
                            int oid=0;
                            
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
                                       ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                                       //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                                       Class.forName(strDriver.trim());
                                       con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                                       try
                                       {
                                            stmt=con.createStatement();
                                            con.clearWarnings();
                                       }
                                       catch(SQLException e)
                                       {
                                            System.out.println("Exception in creating statement:"+e);
                                       }
                                        stmt=con.createStatement();
                           }
                           catch(Exception e)
                           {
                                System.out.println("Exception in opening connection:"+e);
                           } 
                            HttpSession session=request.getSession(false);
                            UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                              
                            System.out.println("user id::"+empProfile.getEmployeeId());
                            int empid=empProfile.getEmployeeId();
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
                 <table cellspacing="1" cellpadding="3" width="100%" >
                     <tr class="tdH"> 
                                <td colspan="2">
                                     <div align="center">
                                         <font size="4"> Invoice Details for Firm Bills </font>
                                     </div>
                                </td>
                     </tr>
                </table>
                
                <div class="tab-pane" id="tab-pane-1">
                <div class="tab-page">
                <h2 class="tab" >Bill Details </h2>
                    <div align="center">
                    <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
                        <tr class="table">
                            <td> 
                                <div align="left">
                                    Accounting Unit Code 
                                    <font color="#ff2121">*</font>
                                </div>
                            </td>
                            <td>
                            <div align="left">
                            <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);">
                                <%
                                  int unitid=0;
                                  String unitname="";
                                  try
                                  {
                                    if(oid==5000)
                                    {
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
                                          out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                          unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                          }
                                          ps.close();
                                          rs.close();
                                      }
                                    }
                                  catch(Exception e)
                                    {
                                    System.out.println("here");
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
                                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2" onblur="bill_sub_office();"> 
                                      
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
                        <tr>
                         <td class="table" width="40%" align="left"><div >
		              	Cash Book Year &amp; Month&nbsp;&nbsp;
		              	</div></td>
                                    <td class="table" align="left"> 
                                    <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event);" >
                                            <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" onchange="bill_sub_office();" >
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
                                    </td>               
                        </tr>
                        
                        <tr class="table">
							<td>
								<div align="left">
									Doc Date <font color="#ff2121">*</font>
								</div>
							</td>
							<td>
								<div align="left">
									<input type="text" name="txtCrea_date" id="txtCrea_date"
										readonly tabindex="3" maxlength="10" size="11"
										onfocus="javascript:vDateType='3';"
										onkeypress="return calins(event,this);"
										onblur="dateCheck1(this);return call_mainJSP_script(this,1);"  /> 
										
										   
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.billform.txtCrea_date,1);" 
                         alt="Show Calendar"></img>
										
								</div>
							</td>
						</tr>
						
						<tr class="table">
                <td>
                  <div align="left">
                    Doc No
                  </div>
                </td>
                <td>
                  <div align="left" >
                    <input type="hidden" name="txtReceipt_No" id="txtReceipt_No"  
                    style="background-color: #ececec"  readonly="readonly" size="6" maxlength="5"/> (System Generated)
                  </div>
                </td>
              </tr>
                        <tr class="table">
							<td>
								<div align="left">
									<!-- Schemes <font color="#ff2121">*</font>-->
									<font color="Red" size="1%">Select Scheme</font><font color="#ff2121">*</font>
								</div>
							</td>
							<td>
								<div align="left">
									<%-- <!--<input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>-->

									<select size="1" name="Schemename" style="width: 400px;" id="Schemename" tabindex="1" onchange="loadSchdebitcode();">
										<option value="">-- Select --</option>
										<%
											ps = con.prepareStatement("select sch_sno, project_name from PMS_MST_PROJECTS_VIEW  where office_id=5000");
											out.println("schmename" + ps);
											rs = ps.executeQuery();
											while (rs.next()) {

												System.out.println(rs.getString("project_name"));
												//out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
												out.println("<option value=" + rs.getInt("sch_sno") + " >"
														+ rs.getString("project_name") + "</option>");
												// unitid=rs.getInt("ACCOUNTING_UNIT_ID");
											}
											ps.close();
											rs.close();
										%>



									</select> <input type="button" onclick="loadsch();" value="Go">--%>
									<!-- <a href="javascript:loadscpage();"><font color="Red" size="3%">Select Scheme</font></a>-->
								
								
								<input type="text" name="sch_code"  
                           id="sch_code" maxlength="6"
                           onkeypress="return numbersonly(event)"
                            onchange="OandMListSub('loadSchdebitcode','null')"
                            onblur="OandMListSub('loadSchdebitcode','null')"  size="9"/>
                            
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="loadscpage();"></img>
								
								<input type="text" name="sch_desc" readonly="readonly" 
                           id="sch_desc" style="background-color: #ececec"  maxlength="125" size="70"/>
								
								</div>
								
							</td>
						</tr>
						<tr>
                                    <td class="table" width="40%" align="left">Location</td>
                                    <td class="table" align="left"> 
                                           <input type="text" name="location" id="location" style="width: 500px; "/>
                                    </td>  
                        </tr>
						<tr>
                                    <td class="table" width="40%" align="left">LS Reach</td>
                                    <td class="table" align="left"> 
                                           <input type="text" name="lsreach" id="lsreach" style="width: 500px; "/>
                                    </td>  
                        </tr>
                        
                        <tr class="table">
		<td width="40%">
		<div align="left">Name of the office submitting the bill<font color="#ff2121">*</font></div>
		</td>
		<td class="table" align="left"> 
                                            <select name="sub_office" id="sub_office" > 
                                                <option value='0'>select</option>
                                                
                                                
                                                
                                            </select>
                                    </td> 
		
	</tr>
                        
                        
                        
                        <tr>
                                    <td class="table" width="40%" align="left">Date of the Bill Submitted<font color="#ff2121">*</font></td>
                                    <td class="table" align="left"> 
                                         
                                          <input type="text" name="billdate" id="billdate" tabindex="3" 
						                           maxlength="10" size="11"  
						                           onfocus="javascript:vDateType='3';"
						                           onkeypress="return calins(event,this);" />
						                           
						                   <img src="../../../../../images/calendr3.gif"
                                                 onclick="showCalendarControl(document.forms[0].billdate, 1);"
                                                 alt="Show Calendar"></img>
                                    </td>               
                        </tr>
                         <tr>
                                    <td class="table" width="40%" align="left">Date of Receipt of the bill in Division </td>
                                    <td class="table" align="left"> 
                                             <input type="text" name="recivdate" id="recivdate" tabindex="3" 
						                           maxlength="10" size="11"  
						                           onfocus="javascript:vDateType='3';"
						                          onkeypress="return calins(event,this);" />  
						                          
						                     <img src="../../../../../images/calendr3.gif" 
                                                 onclick="showCalendarControl(document.forms[0].recivdate, 1);" 
                                                 alt="Show Calendar" ></img>
                                            
                                    </td>               
                        </tr>
                        
                        <tr>
                                    <td class="table" width="40%" align="left">Bill Amount<font color="#ff2121">*</font></td>
                                    <td class="table" align="left"> 
                                           <input type="text" name="billamount" id="billamount"  onkeypress="return numbersonly(event);callAmount();"/>
                                    </td>               
                        </tr>
						
                       
                        
                                         
                        
                        
                         <tr>
                                    <td class="table" width="40%" align="left">Bill Major Type</td>
                                    <td class="table" align="left"> 
                                            <select name="billmajortype" id="billmajortype" onchange="callminor();"> 
                                                <option value='0'>select</option>
                                            </select>
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Bill Minor Type</td>
                                    <td class="table" align="left"> 
                                            <select name="billminortype"  id="billminortype" onchange="callsub(this.value);"> 
                                                <option value='0'>select</option>
                                            </select>
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Bill Sub Type</td>
                                    <td class="table" align="left"> 
                                            <select name="billsubtype"  id="billsubtype" > 
                                                <option value='0'>select</option>
                                            </select>
                                            <input type="hidden" name="invoiceno" id="invoiceno" disabled="disabled"/>
                                             <input type="hidden" name="billno" id="billno" disabled="disabled"/>
                                    </td>               
                        </tr>
                        
                        <tr>
                                    <td class="table" width="40%" align="left">Bill Nature</td>
                                    <td class="table" align="left"> 
                                            <select name="billnature" id="billnature"> 
                                                <option value="">select</option>
                                                <%
											try {
												String billnature=null;
												
												ps = con.prepareStatement("select NATURE_TYPE_CODE,NATURE_TYPE_DESC from OANDM_BILL_NATURE where STATUS='L' order by NATURE_TYPE_CODE");
												rs = ps.executeQuery();
												while (rs.next()) {
													out.println("<option value="
															+ rs.getInt("NATURE_TYPE_CODE") + ">"
															+ rs.getString("NATURE_TYPE_DESC") + "</option>");
													billnature=rs.getString("NATURE_TYPE_DESC");
													System.out.println("billnature"+billnature);
													request.setAttribute("billnature1", billnature);
													System.out.println("billnature"+request.getAttribute("billnature1"));
												}

											} catch (Exception e) {
												System.out.println("Exception in billnature combo..." + e);
											} finally {
												rs.close();
												ps.close();
											}
										%>
                                            </select>
                                    </td>               
                        </tr>
                        
                        <tr>
                         <td class="table" width="40%" align="left"><div >
		              	Relating Year &amp; Month&nbsp;&nbsp;
		              	</div></td>
                                    <td class="table" align="left"> 
                                    <input type="text" name="ReltxtCB_Year" id="ReltxtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
                                            <select name="ReltxtCB_Month"  id="ReltxtCB_Month" tabindex="4" >
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
                                    </td>               
                        </tr>
                        
                        <tr>
                                    <td class="table" width="40%" align="left">M-Book Date</td>
                                    <td class="table" align="left"> 
                                             <input type="text" name="mbookdate" id="mbookdate" tabindex="3" 
						                           maxlength="10" size="11"  
						                           onfocus="javascript:vDateType='3';"
						                           onkeypress="return calins(event,this);"/>
                                           <img src="../../../../../images/calendr3.gif"
                                                 onClick="showCalendarControl(document.forms[0].mbookdate, 1);setTimeout('checkBillDate()',2000);"
                                                 alt="Show Calendar"></img>
                                           

                                    </td>               
                        </tr>
                         <tr>
                                    <td class="table" width="40%" align="left">M-Book No</td>
                                    <td class="table" align="left"> 
                                           <input type="text" name="mbookno" id="mbookno"  onkeypress="return numbersonly(event)"/>
                                    </td>               
                        </tr>
                          <tr>
                                 <td class="table" align="left">M-Book Page No</td>
                                 <td class="table" align="left"> 
                                        <input type="text" name="mbookpageno" id="mbookpageno" onkeypress="return numbersonly(event)" onblur="loadAgreeNo()"/>
                                 </td>               
                        </tr> 
                       
                        <tr>
                                    <td class="table" width="40%" align="left">Bill Passed Date</td>
                                    <td class="table" align="left"> 
                                             <input type="text" name="passdate" id="passdate" tabindex="3" 
						                           maxlength="10" size="11"  
						                           onfocus="javascript:vDateType='3';"
						                          onkeypress="return calins(event,this);" />  
						                          
						                     <img src="../../../../../images/calendr3.gif" 
                                                 onclick="showCalendarControl(document.forms[0].passdate, 1);" 
                                                 alt="Show Calendar" ></img>
                                            
                                    </td>               
                        </tr>
                        
                        <tr class="table">
                <td>
                    <div align="left">
                        Drawing officer passing the bills
                    </div>
                </td>
                <td width="60%">
				<table align="left">
					<tr align="left">
						<td>
						<div align="left"><select size="1" name="cmbMas_SL_Code2"
							id="cmbMas_SL_Code2"></select></div>				</td>
						<td>
						<div align="left" id="offlist_div_master2" style="display: none">
		
						<img src="../../../../../images/c-lovi.gif" width="20" height="20"
							alt="OfficeList" onClick="jobpopup_master();"></img> <input
							type="text" name="txtOfficeID_mas2" id="txtOfficeID_mas2"
							maxlength="4" size="5" onBlur="mas_office(this.value);" /></div>
						<div align="left" id="emplist_div_master2"><img
							src="../../../../../images/c-lovi.gif" width="20" height="20"
							alt="empList" onClick="employee_popup_master();"></img> 
		                <input type="text" name="txtEmpID_mas2" id="txtEmpID_mas2" maxlength="6" size="6"   onchange="callemp('<%=s %>');"  onchange="getOffice('<%=s %>');"/></div>
						<input type="hidden" name="cmbSL_type2" id="cmbSL_type2" /> 
		                                <input type="hidden" name="cmbSL_Code2" id="cmbSL_Code2" /></td>
					</tr>
				</table>		</td>
              </tr>
                       
                         
                    </table>
                    </div>
                    </div>
                    <div class="tab-page" id="gd" >
                    <h2 class="tab" > Payment Details </h2>
                    <div align="center">
                    <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
                    
                    <tr class="table">
                <td width="40%">
                  <div align="left">Sub-Ledger Type</div>
                  
                </td>
                <td width="60%">
                  <div align="left">
                    <select  name="cmbMas_SL_type" id="cmbMas_SL_type" tabindex="6"
                            onchange="doFunction('Load_MasterSL_Code',this.value);">
                      <option value="">--Select Type--</option>
                      <%
                        try
                        {
                        ps=con.prepareStatement("select SUB_LEDGER_TYPE_CODE,SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES WHERE display_restricted is null and SUB_LEDGER_TYPE_CODE in (1,2,7,9,11) order by SUB_LEDGER_TYPE_DESC");
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
                  </div>
                </td>
              </tr>
                    <tr class="table">
                <td width="40%">
                  <div align="left">Sub-Ledger Code</div>
                </td>
                <td width="60%">
                    <table align="left">
                     <tr align="left">
                     <td>
                         <div align="left">
                        <select size="1" name="cmbMas_SL_Code" id="cmbMas_SL_Code"  onchange="loadName_Mas(this);"   >
                                
                          <option value="">--Select Code--</option>
                        </select>
                  </div>
                      </td>
                      <td>
                          <div align="left" id="offlist_div_master"  style="display:none">
                            
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" onclick="jobpopup_master();"></img>
                            <input type="text" name="txtOfficeID_mas" id="txtOfficeID_mas" maxlength="4" size="5"  onblur="mas_office(this.value);" />
                          </div>
                           <div align="left" id="emplist_div_master"  style="display:none">
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="employee_popup_master();"></img>
                            <input type="text" name="txtEmpID_mas" id="txtEmpID_mas" maxlength="5" size="5"  onblur="mas_employee(this.value);" />
                          </div>
                       </td>
                     
                    </tr>
                   </table>
                </td>
              </tr>
              <tr>
                                    <td class="table" width="40%" align="left">Payment Type</td>
                                    <td class="table" align="left"> 
                                            <select name="paymenttype" id="paymenttype"> 
                                                <option value="">select</option>
                                                <%
											try {
												String PaymentType=null;
												
												ps = con.prepareStatement("select PAYMENT_TYPE_CODE,PAYMENT_TYPE_DESC from OANDM_BILLS_PAYMENTTYPES where STATUS='L' order by PAYMENT_TYPE_CODE");
												rs = ps.executeQuery();
												while (rs.next()) {
													out.println("<option value="
															+ rs.getInt("PAYMENT_TYPE_CODE") + ">"
															+ rs.getString("PAYMENT_TYPE_DESC") + "</option>");
													PaymentType=rs.getString("PAYMENT_TYPE_DESC");
													System.out.println("PaymentType"+PaymentType);
													request.setAttribute("PaymentType1", PaymentType);
													System.out.println("PaymentType"+request.getAttribute("PaymentType1"));
												}

											} catch (Exception e) {
												System.out.println("Exception in Payment combo..." + e);
											} finally {
												rs.close();
												ps.close();
											}
										%>
                                            </select>
                                    </td>               
                        </tr>
                        
                      
                    <tr>
                                    <td class="table" width="40%" align="left">Agreement No</td>
                                    <td class="table" align="left"> 
                                           <input type="text" name="Agreementno" id="Agreementno"  onkeypress="return numbersonly(event)" />
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Agreement Date</td>
                                    <td class="table" align="left"> 
                                            <input type="text" name="agreementdate" id="agreementdate" tabindex="3" 
						                           maxlength="10" size="11"  
						                           onfocus="javascript:vDateType='3';"
						                           onkeypress="return calins(event,this);"/>
                                            <img src="../../../../../images/calendr3.gif"
                                             onClick="showCalendarControl(document.forms[0].agreementdate, 1);"
                                             alt="Show Calendar"></img>
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Work Order/ Supply Order No</td>
                                    <td class="table" align="left"> 
                                           <input type="text" name="workorderno" id="workorderno"  onkeypress="return numbersonly(event)" />
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Work Order / Supply Order Date</td>
                                    <td class="table" align="left"> 
                                            <input type="text" name="supp_date" id="supp_date" tabindex="3" 
						                           maxlength="10" size="11"  
						                           onfocus="javascript:vDateType='3';"
						                           onkeypress="return calins(event,this);"/>
                                            <img src="../../../../../images/calendr3.gif"
                                             onClick="showCalendarControl(document.forms[0].supp_date, 1);"
                                             alt="Show Calendar"></img>
                                    </td>               
                        </tr>
                        
                        
                   <tr >
                                    <td class="table" width="40%" align="left" ><font color="#E60000">Budget Alloted</font></td>
                                    <td class="table" align="left"> 
                                           <input type="text" name="budgetalloted" id="budgetalloted" />
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left"><font color="#E60000">Expenditure incurred so far</font></td>
                                    <td class="table" align="left"> 
                                           <input type="text" name="expenditureincurred" id="expenditureincurred"  />
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left"><font color="#E60000">Balance available</font></td>
                                    <td class="table" align="left"> 
                                           <input type="text" name="balanceavailable" id="balanceavailable"  />
                                    </td>               
                        </tr><tr>
                                    <td class="table" width="40%" align="left">Remarks</td>
                                    <td class="table" align="left"> 
                                       <textarea name="remarks" id="remarks" cols="50"
                                         tabindex="6" rows="4"></textarea>
                                    </td>               
                        </tr>
                        
                     </table>
                </div>
                 </div>
                 <div class="tab-page" id="gd" >
                    <h2 class="tab" > Supporting Invoice Details </h2>
                    <div align="center">
                    <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
                    <tr>
                        			<td class="table" width="40%" align="left">Invoice No</td>
                                    <td class="table" align="left"> 
                                           <input type="text" name="invoiceNo" id="invoiceNo" onkeypress="return numbersonly(event)"/>
                                    </td>  
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Invoice Date</td>
                                    <td class="table" align="left"> 
                                             <input type="text" name="invoicedate" id="invoicedate" tabindex="3" 
						                           maxlength="10" size="11"  
						                           onfocus="javascript:vDateType='3';"
						                          onkeypress="return calins(event,this);" />  
						                          
						                     <img src="../../../../../images/calendr3.gif" 
                                                 onclick="showCalendarControl(document.forms[0].invoicedate, 1);setTimeout('checkBillDate()',2000);" 
                                                 alt="Show Calendar" ></img>
                                            
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Invoice Amount</td>
                                    <td class="table" align="left"> 
                                           <input type="text" name="invoiceamount" id="invoiceamount"  onkeypress="return numbersonly(event);"onblur="callAmount();"/>
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Particulars of Invoice</td>
                                    <td class="table" align="left"> 
                                       <textarea name="Particularsinvoice" id="Particularsinvoice" cols="50"
                                         tabindex="6" rows="4"></textarea>
                                    </td>               
                        </tr>
                    
                    </table>
                    </div>
                    </div>
                    <input type='hidden' name='RecordCountDR' id='RecordCountDR' value='0' />
                    <input type='hidden' name='RecordCountCR' id='RecordCountDR' value='0' />
                    
                <div
	     class="tab-page" id="gd">
				<h2 class="tab">Debit</h2>

				<div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      <tr class="tdH">
                        <th > Sno  </th>
                       <th>A/c Head Code</th>
                       <th  style="display: none;">CR/DR</th>       
                        <th >Sub Ledger Type</th>
                        <th>Sub Ledger Code</th>
                        <th>Amount</th>
                         <th>Remarks</th>
                        
                      </tr>
                      
                       <tbody id="grid_body2" class="table" align="left" >
                        
                       </tbody>
                       
                    </table>
                  </div>
			
		</div>
                <div class="tab-page" id="gd">
				<h2 class="tab">Credit</h2>

				
				
				<div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      <tr class="tdH">
                        <th > Sno  </th>
                       <th>A/c Head Code</th>
                          <th style="display: none;">CR</th>    
                        <th >Sub Ledger Type</th>
                        
                        <th>Sub Ledger Code</th>
                        <th>Amount</th>
                         <th>Remarks</th>
                        
                      </tr>
                      
                      
                       <tbody id="grid_body" class="table" align="left" >
                    
		                       </tbody>
		                       
		                    </table>
		                  </div>
						
						
					</div>
               
                </div>
                <br>
               <div align="center">
                   <table cellspacing="1" cellpadding="3" width="100%">
                        <tr class="tdH">
                            <td  colspan="2">
                                <div align="center"> 
                                        <input type="submit" name="butSub" id="butSub" value="SUBMIT" /> 
                                        <input type="button" name="onedit" value="Update" id="onedit" onclick="update();" disabled="disabled"/> 
                                        <input type="button" name="ondelete" value="Delete" id="ondelete" onclick="deleted();" disabled="disabled"/> 
                                        <input type="button" name="onlist" value="List" id="onlist" onclick="listpopup();" />  
                                        
                                        
                                         <input type="button" name="onclear" value="ClearAll" id="onclear" onclick="clearAll();" /> 
                       					 <input type="button" name="oncancel" value="Cancel" id="oncancel" onclick=" self.close();" /> 
                                </div> 
                           </td> 
                       </tr>
                     </table>
               </div>
            </form>
  </body>
</html>