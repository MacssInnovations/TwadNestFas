<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ page import="Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Phone Master System</title>
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
    </script>  
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
   <script type="text/javascript" language="javascript" src="../scripts/phone_master_js.js"></script>
    <!--
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case_FinalHead_GJV.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType_NegativeAmtAllowed.js"></script>
    <script type="text/javascript" src="../scripts/Imprest_Journal_Create.js"></script>
    -->
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    <!--
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>      
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>  
     -->
  </head>  
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');Load_Details()" bgcolor="rgb(255,255,225)">
  <%
  Connection connection=null;
  PreparedStatement statement=null,Pstatement=null,Pstmt1=null,Pstmt2=null;;
  HttpSession session = request.getSession();
  ResultSet rset=null,rset1=null,rset2=null,results=null;
  HttpSession user_session=null;
  int emp_code=0;
  int offid=0;
  String offname="";  
    int count=0;
	        try
	            {
	             	LoadDriver load = new LoadDriver();
	             	connection = load.getConnection();	             	
	              try
	              {
	        user_session=request.getSession(false);

			//empid=(String)user_session.getAttribute("empid");
			UserProfile up=null;
 			up=(UserProfile)session.getAttribute("UserProfile"); 			
			emp_code=up.getEmployeeId();
	              Pstmt2=connection.prepareStatement("select p.OFFICE_ID,v.office_name from HRM_EMP_CURRENT_POSTING p,com_mst_offices  v where EMPLOYEE_ID=? and v.office_id = p.office_id");
	              connection.clearWarnings();
	              }
	              catch(SQLException e1)
	              {
					 System.out.println("Exception in creating statement:"+e1);
	              }          
	           }
	          catch(Exception e1)
	          {
	             System.out.println("Exception in openeing connection:"+e1);
	          }
	            try
 			   {
            Pstmt2.setInt(1,emp_code);
            results=Pstmt2.executeQuery();
                 if(results.next())
                 {
                    offid=results.getInt("OFFICE_ID");
                    offname=results.getString("office_name");
                 }
            results.close();
            Pstmt2.close();
           }
        catch(SQLException e)
    {
        System.out.println(e);
    }
  %>
  <!-- ////////////////////// phone master heading ///////////////////////////////////-->
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Phone Master </font>
          </div>
        </td>
      </tr>
    </table>
    <form name="formPhone_Master" id="formPhone_General" method="POST"
                  action="../../../../../phone_master_servlet?Command=Add" onsubmit="return check();">
      <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" >General </h2>
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong>General Details</strong>
                  </div>
                </td>
              </tr>
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
                    </select>
                  </div>
                </td>
              </tr>
              <tr class="table">
	            <td>
	              <div align="left">Custodian Type  <font color="#ff2121">*</font></div>
	            </td>
	            <td>
	              <div align="left">
	                 <input type="radio" name="rad_cust_type" id="rad_cust_type"
		                           value="T" checked="checked" />TWAD
		                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
		                    <input type="radio" name="rad_cust_type" id="rad_cust_type"
		                           value="P" />Privileged Users &nbsp;&nbsp;&nbsp;&nbsp; 
	              </div>
	            </td>
	          </tr>        
              <tr class="table">
                <td width="30%">
                  <div align="left">Remarks</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="50" tabindex="7" onkeypress="return check_leng('remarks',this.value);"
                    rows="4"></textarea>
                  </div>
                </td>
              </tr>
              </table>
              </div>
              </div>
        <div class="tab-page" id="gd" >
          <h2 class="tab" > Details</h2>        
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr>
                <td colspan="2">
                  <div id="phone_det_disp" >
                    <table cellspacing="1" cellpadding="2" border="1"
                           width="100%">
                      <tr class="tdTitle">
                        <td colspan="2">
                          <div align="left">
                            <strong> Details</strong>
                          </div>
                        </td>
                      </tr>              
                      <tr class="table">
		                <td>
		                   <div align="left">
		                    	Purpose <font color="#ff2121">*</font>
		                   </div>
			            </td>
		                <td>
		                  <div align="left">
		                    <select size="1" name="cmb_purpose" id="cmb_purpose" onchange="chooseCType();">
		                      <option value="0">...Select Purpose....</option>
                                      <option value="O">Office</option>
                                       <option value="R">Residence</option>
                                       <option value="F">Fax</option>
		                    </select>
		                  </div>
		                </td>
              		  </tr>
              	<tr class="table" id="tr1">
                <td>
                  <div align="left">
                    Employee Id
                     <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
	           		<input type="text" name="txtEmpId"  id="txtEmpId" size="7" maxlength="6" onkeypress="return numbersonly1(event,this)" onchange="Load_emp_details()"/>     
                   	<div id="empImg" style="display: none;">
						<img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="servicepopup();">                   	
                   	</div>
                  </div>
                </td>
              </tr>              
                <tr class="table" id="tr2">
                <td>
                  <div align="left">
                    Name
                   <!-- <font color="#ff2121">*</font>-->
                  </div>
                </td>
                <td>
                  <div align="left">
	           <input type="text" name="txtemp_name"  id="txtemp_name"  maxlength="20" size="30" readonly/>&nbsp;&nbsp;                   
                  </div>
                </td>
              </tr>              
               <tr class="table" id="tr3">
                <td>
                  <div align="left">
                    Designation
                 <!--   <font color="#ff2121">*</font>-->
                  </div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" name="txtemp_desig"  id="txtemp_desig"  maxlength="20" size="30" readonly/>
                  </div>
                </td>
              </tr>
              <tr class="table" id="tr4">
                <td>
                  <div align="left">
                  Office Id
                   <!--   <font color="#ff2121">*</font>-->
                  </div>
                </td>
                <td>
                  <div align="left">
                        <input type="text" name="officeId"  id="officeId" value="<%=offid%>" onkeypress="return numbersonly1(event,this)" maxlength="20" size="7" />
                        <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" onclick="jobpopup('txtOffice_Id','txtOfficeName')" id="off_img" name="off_img"></img>  <input type="hidden" name="dept_id" id="dept_id"/>
                  </div>
                </td>
              </tr>
                <tr class="table">
                <td>
                  <div align="left">
                  Office
                   <!--   <font color="#ff2121">*</font>-->
                  </div>
                </td>
                <td>
                  <div align="left">
                        <input type="text" name="txtemp_office"  id="txtemp_office"  maxlength="20" size="30" readonly/>
                  </div>
                </td>
              </tr>             
		              <tr class="table">
		                <td>
		                  <div align="left">
		                  Connection Type
		                    <font color="#ff2121">*</font>
		                  </div>
		                </td>
		                <td>
		                  <div align="left">
                                       <select size="1" name="cmb_connection_type" id="cmb_connection_type" onchange="enableSTDcode(this.value);">
		                       <option value="0">....Select Connection Type....</option>
                                       <option value="L">LandLine</option>
                                       <option value="M">Mobile</option>
		                       </select>
		                  </div>
		                </td>
		              </tr>
                              <tr class="table">
		                <td>
		                  <div align="left">
		                  Usage Details
		                    <font color="#ff2121">*</font>
		                  </div>
		                </td>
		                <td>
		                  <div align="left">
                                       <input type="radio" name="rad_usage_det" id="rad_usage_det"
		                           value="IU" checked="checked" />In Use
		                                                          &nbsp;&nbsp;&nbsp;&nbsp; 
                                        <input type="radio" name="rad_usage_det" id="rad_usage_det"
		                           value="DC" />Disconnected      &nbsp;&nbsp;&nbsp;&nbsp;		                      
		                  </div>
		                </td>
		              </tr>		              
                             <tr class="table">
		                <td width="40%">
		                  <div align="left">STD Code  (For Landline only)<font color="#ff2121">*</font> </div>
		                </td>
		                <td width="60%">
		                  <div align="left">
                                        <input type="text" name="txtSTD_code" id="txtSTD_code" size=11 maxlength=6 onkeypress="return numbersonly(event)"></input>
		                  </div>
		                </td>
		              </tr>              
		              <tr class="table">
		                <td width="40%">
		                  <div align="left">Phone Number <font color="#ff2121">*</font> </div>
		                </td>
		              	<td width="60%">
				         <!--   <table align="left">
				             <tr align="left">
					             <td>-->
					                  <div align="left">
					                        <input type="text" name="txtphone_no" id="txtphone_no" size=11 maxlength=10 onkeypress="return numbersonly(event)"></input>
					                  </div>
					              </td>
                                                </tr>
           					<!--</table>
        			  	</td>
                                    </tr>-->              		  
                      <tr class="table">
                        <td>
                          <div align="left">
                          Service Provider Name <font color="#ff2121">*</font> 
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <!--<input type="text" name="txtSerProName" id="txtSerProName" maxlength="20" 
                                    size="25"/>-->
                                    <select size="1" name="txtSerProName" id="txtSerProName" >
		                       <option value="0">....Select Service Provider Name ....</option>
                                       <option value="BSNL">BSNL</option>
                                       <option value="TATA">TATA</option>
                                       <option value="AIRTEL">AIRTEL</option>
                                       <option value="RELIANCE">RELIANCE</option>
		                       </select>
                          </div>
                        </td>
                      </tr>             
                      <tr class="table">
                        <td>
                          <div align="left">
                            Service Provider Type <font color="#ff2121">*</font> 
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <!--<input type="text" name="txtSerProType"
                                   id="txtSerProType" maxlength="20" size="25"/>-->
                                   <select size="1" name="txtSerProType" id="txtSerProType" >
		                       <option value="0">....Select Service Provider Type ....</option>
                                       <option value="PREPAID">PREPAID</option>
                                       <option value="POSTPAID">POSTPAID</option>
                                       <option value="BROADBAND">BROADBAND</option>
		                       </select>
                          </div>
                        </td>
                      </tr>                      
                      <tr class="table">
                        <td>
                          <div align="left">
                           Ceiling Type <font color="#ff2121">*</font> 
                          </div>
                        </td>
                        <td>
                          <div align="left">
                             <input type="radio" name="rad_ceiling_type" id="rad_ceiling_type"
		                           checked="checked" value="L" onclick="enableceilamt();"/>Limited
		                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                            <input type="radio" name="rad_ceiling_type" id="rad_ceiling_type"
		                           value="U" onclick="enableceilamt();"/>UnLimited &nbsp;&nbsp;&nbsp;&nbsp; 
                            <input type="radio" name="rad_ceiling_type" id="rad_ceiling_type"
		                           value="N" onclick="enableceilamt();"/>Not Applicable &nbsp;&nbsp;&nbsp;&nbsp; 
                          </div>
                        </td>
                      </tr>                      
                      <tr class="table">
                        <td>
                          <div align="left">
                            Ceiling Limit Amount <font color="#ff2121">*</font> 
                          </div>
                        </td>
                        <td>
                          <div align="left">
                                    <input type="text" name="txtceil_Limit_amt" id="txtceil_Limit_amt"  size="10" maxlength="10" onblur="return valid_amt(this);" 
                                    onkeypress="return numbersonly(event);" onfocus="enableceilamt();"/>
                          </div>
                        </td>
                      </tr>                              
		              <tr class="table">
		                <td>
		                  <div align="left">Particulars</div>
		                </td>
		                <td>
		                  <div align="left">
		                    <textarea name="txtParticulars" id="txtParticulars" cols="70"  onkeypress="return check_leng('particulars',this.value);"
		                              rows="3"></textarea>
		                  </div>
		                </td>
		              </tr>	              
                      <tr class="tdTitle">
                        <td colspan="2" height="23">
                         <div align="center">
                            <table border="0">
                          <tr><td>
                          <input type="button" name="cmdadd" id="cmdadd"
                                 value="ADD" onclick="ADD_GRID()" style="display:block"/></td>
                          <td>
                          <input type="button" name="cmdupdate" value="UPDATE"
                                 id="cmdupdate" onclick="update_GRID()"
                                 style="display:none"/></td>
                          <td><input type="button" name="cmddelete" value="DELETE"
                                 id="cmddelete" onclick="delete_GRID()"
                                 disabled="disabled"/></td>
                          <td><input type="button" name="cmdclear" value="CLEAR ALL"
                                 id="cmdclear" onclick="clear_main_fields()"/></td>                        </tr>
                        </table>
                        </div>
                        </td>
                      </tr>
                    </table>
                  </div>
                  <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      
                      <tr class="table">
                            <th>Select</th>
                            <th>Purpose</th>
                            <th>Connection Type</th>
                            <th>Usage Details</th>
                            <th>STD Code</th>
                            <th>Phone Number </th>
                            <th>Service Provider Name</th>
                            <th>Service Provider Type</th>
                            <th>Ceiling Type</th>                       
                            <th>Ceiling Limit Amount </th>
                            <th>Particulars </th>    
                      </tr>                      
                       <tbody id="grid_body" class="table" align="left" >
                       </tbody>
                    </table>
                  </div>
                </td>
              </tr>              
            </table>
          </div>
        </div>
      </div>
      <br>      
      <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="submit" name="butSub" id="butSub" value="SUBMIT"/>
                 &nbsp;&nbsp;&nbsp; 
                 <input type="button" name="butList" id="butList" value="LIST"
                      onclick="gridPhoneList();"/>
                      <!--  onclick="callphoneList();"/> -->
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="clrForm();"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butExit" id="butExit" value="EXIT"
                       onclick="exitmethod();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>    
    </form>
    </body>
</html>