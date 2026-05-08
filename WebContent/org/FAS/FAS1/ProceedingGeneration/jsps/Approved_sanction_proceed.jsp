<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Sanction Proceeding ApprovalSystem</title>
      
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
     
     <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
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
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
    <script type="text/javascript" src=".../../../../ReceiptSystem/scripts/Common_ReceiptType.js"></script>
  
    
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/UnitwiseOffice.js"></script>  
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript"   src="../scripts/sanction_proceed_approved.js"></script>
 <!--<script type="text/javascript"   src="../Civilbills/scripts/AccHeadCode.js"></script>-->
</head>      
<body onload="LoadAccountingUnitID('LIST_ALL_UNITS');LoadBill_Majortype();loadDate();" bgcolor="rgb(255,255,225)">
<%
             Connection con=null;
             ResultSet rs=null;
             PreparedStatement ps=null,ps2=null;
            
             Connection connection=null;
        
             ResultSet results=null,rs2=null;
             ResultSet results1=null;
             ResultSet results2=null;
             HttpSession session = request.getSession();
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
                       System.out.println("Exception in opening connection :"+e);
             }   
	      		UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                int empid=empProfile.getEmployeeId();
                int  oid=0;             // Office id
                String oname="";        // office name
           
            try
            {
                   
                    ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
                    ps.setInt(1,empid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            oid=results.getInt("OFFICE_ID");
                            System.out.println("Office id is:"+oid);
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
  <!-- //////////////////////Sanction Master ///////////////////////////////////-->
        <table cellspacing="1" cellpadding="3" width="100%" >
            <tr class="tdH">
                <td colspan="2">
                    <div align="center">
                        <font size="4">Sanction Proceedings Approval</font>
                    </div>
                </td>
            </tr>
        </table>
    
  <form name="formsanc_proceed_Appr" id="formsanc_proceed_Appr" method="POST" >
        <div align="left">
                    <table cellspacing="1" cellpadding="2" border="1" width="100%">
                        <tr class="table">
                            <td width="27%">
                                <div align="left">
                                    Accounting Unit Code 
                                        <font color="#ff2121">*</font>
                                </div>
                            </td>
                            <td width="73%">
                                <div align="left">
                                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);loadAccunit();">
                                    			<%
				int unitid = 0;
				String unitname = "";
				try {
					if (oid == 5000) {
						//out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
						//ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?");
						String getWing = "select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=? and OFFICE_WING_SINO=(select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)";
						ps = con.prepareStatement(getWing);
						ps.setInt(1, oid);
						ps.setInt(2, empid);
						ps.setInt(3, oid);
						rs = ps.executeQuery();

						if (rs.next()) {
							out.println("<option value="
									+ rs.getInt("ACCOUNTING_UNIT_ID") + ">"
									+ rs.getString("ACCOUNTING_UNIT_NAME")
									+ "</option>");
							unitid = rs.getInt("ACCOUNTING_UNIT_ID");

							System.out.println(".."
									+ rs.getInt("ACCOUNTING_UNIT_ID"));
							System.out.println(".."
									+ rs.getString("ACCOUNTING_UNIT_NAME"));
							System.out
									.println(".." + rs.getInt("OFFICE_WING_SINO"));

						}
						System.out.println(oid + " " + oname);
						ps.close();
						rs.close();
					} else {
						ps = con
								.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)");
						ps.setInt(1, oid);
						rs = ps.executeQuery();
						if (rs.next()) {
							System.out.println(rs.getInt("ACCOUNTING_UNIT_ID"));
							System.out
									.println(rs.getString("ACCOUNTING_UNIT_NAME"));
							//out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
							out.println("<option value="
									+ rs.getInt("ACCOUNTING_UNIT_ID") + " >"
									+ rs.getString("ACCOUNTING_UNIT_NAME")
									+ "</option>");
							unitid = rs.getInt("ACCOUNTING_UNIT_ID");
						}
						ps.close();
						rs.close();
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			%>
                      				</select>                                   
                                </div>
                            </td>
                        </tr>
                        <tr class="table">
                            <td width="27%">
                                <div align="left">
                                    Accounting For Office Code
                                        <font color="#ff2121">*</font>
                                </div>
                            </td>
                            <td width="73%">
                                <div align="left">
                                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2" >
                  						<%
                               System.out.println("here::::::::::");
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
                    </table>
                </div>
                <div align="left">
                <table cellspacing="1" cellpadding="3" border="1" width="100%">
                
                
                <tr align="left" class="table">
                        <td width="27%">
                            <div align="left" style=display:none>
                                Sanction Proceeding No
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left" style=display:none>
                                <select name="Sanc_No" id="Sanc_No" >
                         	<option value="">select</option>
                         </select>
                            </div>
                        </td>
                    </tr>
                    
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Sanction Proceeding Date
                                <font color="#ff2121">*</font>
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">    
                                <input type="text" name="txt_sancpro_date" id="txt_sancpro_date"  tabindex="9"
                                    maxlength="10" size="11"
                                    onfocus="javascript:vDateType='3';"
                                    onkeypress="return calins(event,this);"/>
                     
                                <img src="../../../../../images/calendr3.gif"
                                     onclick="showCalendarControl(document.formsanc_proceed_new.txt_sancpro_date,0);"
                                     alt="Show Calendar">
                                

                            </div>
                        </td>
                    </tr> 
                    
                
                
                
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Payment
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <input type="radio" name="rad_payment_type" id="rad_payment_type"
		                           value="R" checked="checked" />Regular
		                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                                <input type="radio" name="rad_payment_type" id="rad_payment_type1"
		                           value="A" />Advance &nbsp;&nbsp;&nbsp;&nbsp; 
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table"> 
                        <td width="27%">
                            <div>
                                Financial Year
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">                                
                                <select size="1" name="fin_yr" id="fin_yr" disabled="disabled">                                    
                                </select>
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table"> 
                        <td width="27%">
                            <div>
                                Bill Major Type
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <select size="1" name="txtbill_majr_code" id="txtbill_majr_code" tabindex="5" onchange="loadMinorType()">
                                    <option value=0>select bill major type</option>
                                </select>
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table"> 
                        <td width="27%">
                            <div>
                                Bill Minor Type
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <select size="1" name="txtbill_minr_code" id="txtbill_minr_code" tabindex="6" onchange="loadSubType()">
                                    <option value=0>select bill minor type</option>
                                </select>
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Bill Sub Type
                            </div>
                        </td>
                 
                        <td width="73%">
                            <div align="left">
                                <select size="1" name="txtbill_sub_code" id="txtbill_sub_code" tabindex="7">
                                    <option value=0>select bill sub type</option>                     
                                </select>
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Payee Type
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <input type="radio" name="rad_payee_type" id="rad_payee_type1"
		                           value="E" checked="checked" />Employee
		                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                                <input type="radio" name="rad_payee_type" id="rad_payee_type2"
		                           value="U" />Privileged Users &nbsp;&nbsp;&nbsp;&nbsp; 
                                <input type="radio" name="rad_payee_type" id="rad_payee_type3"
		                           value="P" />Pensioner &nbsp;&nbsp;&nbsp;&nbsp; 
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Payee code
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <input type="text" name="txt_payee_code"  id="txt_payee_code" size="7" maxlength="6" onchange="emp_payee_code();"/>     
                                    <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="emp_popup_payee();"/>
                            </div>
                        </td>
                    </tr>
                     <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Payee Name & Designation
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <input type="text" name="txtpayee_namedesig" id="txtpayee_namedesig" size="45" maxlength="40" class="disab"/>
                            </div>
                        </td>
                    </tr> 
                    
                       
                   
            <tr align="left" class="table">
                <td>
                  <div align="left">
                    Account Head Code & Desc
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                
                <td width="60%">
			                  <div align="left">
			                    <select  name="txtAcc_HeadCode" id="txtAcc_HeadCode" tabindex="6" onblur="myFunction('checkCode','null')" >
			                      <option value="">--Select--</option>
			                      <%
			                        try
			                        {
			                        ps=con.prepareStatement("select distinct ACCOUNT_HEAD_CODE from FAS_BILL_ACCOUNT_HEADS order by ACCOUNT_HEAD_CODE");
			                        rs=ps.executeQuery();
			                            while(rs.next())
			                            {
			                            out.println("<option value="+rs.getInt("ACCOUNT_HEAD_CODE")+">"+rs.getInt("ACCOUNT_HEAD_CODE")+"</option>");
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
			                    <input type="text" name="txtAcc_HeadDesc" readonly="readonly" 
			                           id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/>
			                  </div>
			                </td>
                
                
                
                
            </tr>
             
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Total Sanction Amount
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <input type="text" name="txt_tot_sanctionamt" id="txt_tot_sanctionamt" size="10" maxlength="10" onfocus="caltot_sancamt();" onkeypress="return numbersonly(event)"/>
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Remarks
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                               <textarea name="txt_GeneralRemarks" id="txt_GeneralRemarks" cols="50" tabindex="11" onkeypress="return check_leng('remarks',this.value);"
                                    rows="4"></textarea>
                            </div>
                        </td>
                    </tr> 
                    
                    <tr>
                    <td class="table" width="40%" align="left">Sanction Proceeding Approved or Rejected</td>
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
                         <input type="text" name="approvalDate" size="20" maxlength="10" id="approvalDate" onFocus="javascript:vDateType='3'"
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
                <div align="center">
                    <table cellspacing="1" cellpadding="3" width="100%">
                        <tr class="tdH">
                            <td>
                                <div align="center">
                                    
                                    <input type="button" name="butUpdate" id="butUpdate" value="UPDATE" onclick="callServer('update');" disabled="disabled"/>
                                            &nbsp;&nbsp;&nbsp;
                                    <input type="button" name="butDelete" id="butDelete" value="CANCEL" onclick="callServer('delete');" disabled="disabled"/>
                                            &nbsp;&nbsp;&nbsp;
                                    <input type="button" name="butList" id="butList" value="LIST" 
                                            onclick="listPopup();"/>
                                            &nbsp;&nbsp;&nbsp;         
                                    <input type="button" name="butCancel" id="butCancel" value="CLEAR" 
                                            onclick="clrForm();"/>
                                            &nbsp;&nbsp;&nbsp; 
                                    <input type="button" name="butList" id="butList" value="EXIT"
                                            onclick="exitmethod();"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
   
   </form>
 </body>
</html>