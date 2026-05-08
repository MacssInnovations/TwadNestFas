<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>CivilAgreement</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    <script type="text/javascript" src="../../Reports/ReceiptSystem/scripts/CalendarControl.js"></script> 
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">          </script>
    <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/CivilAgreement.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Cheque_Number_Check_FR_byHO.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/PaymentSystem/scripts/Common_PaymentType.js"></script>
     <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
     <script type="text/javascript" src="../../CalendarController.js"></script> 
    
   </head>
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');callFirm();callWork();setTimeout('callAuthority()',50);">
  <form  name="civilform"  action="upload_civil.jsp" ENCTYPE="multipart/form-data" method="post"> 
     
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
            <table cellspacing="1" cellpadding="" width="100%">
                <tr class="tdH">
                    <td  colspan="2">
                          <div align="center">
                                <font size="4"> Civil Agreement </font>
                          </div>
                    </td>
                </tr>
            </table>
            <div class="tab-pane" id="tab-pane-1">
                <div class="tab-page">
                    <h2 class="tab" >Part-1 </h2>
                    <div align="center">
                    <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
                     <tr class="table">
                       <td>
                          <div align="left">
                            Accounting Unit Id 
                            <font color="#ff2121">*</font>
                          </div>
                       </td>
                       <td>
                        <div align="left">
                          <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);callAuthority();">
            
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
                                  
                                  System.out.println("..ACCOUNTING_UNIT_ID"+rs.getInt("ACCOUNTING_UNIT_ID"));
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
                    Accounting For Office Id
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2"> 
                      
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
                        <td class="table" width="40%" align="left">Agreement Date</td>
                        <td class="table" align="left"> 
                                    <input type="text" name="agreementdate" size="20" maxlength="10" id="agreementdate" onFocus="javascript:vDateType='3'"
                                    onkeypress="return  calins(event,this)" onblur="return checkdt(this);">
                                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.forms[0].agreementdate);" alt="Show Calendar" ></img> 
                                    <input type="hidden" name="agreementno" id="agreementno" disabled="disabled" />
                        </td>               
                </tr>
                <tr>
                        <td class="table" width="40%" align="left"> Agreement Type </td>
                        <td class="table" align="left">
                                <input type="radio" name="agreementtype" value="O" checked="checked" onclick="callOriginal();"/>
                                Original
                                <input type="radio" name="agreementtype" value="S" onclick="callOriginal();"/>
                                Supplement
                        </td>
                </tr>
              <tr>
                        <td class="table" width="40%" align="left">
                        <div style="display:none" id="supplementLabel">
                                If Supplement Original Agreement No & Date
                        </div>
                        </td>
                        <td class="table" align="left"> 
                        <div style="display:none" id="supplementText">
                                <select name="orgAgreementno" id="orgAgreementno" onchange="callAgreeDate(this.value);">
                                <option value="">select</option>
                                </select>
                                 <input type="text" name="orgAgreementdate" id="orgAgreementdate" />
                        </div>
                         </td>              
                </tr> 
                <tr>
                        <td class="table" width="40%" align="left">Name of the work</td>
                        <td class="table" align="left"> 
                                   <textarea name="namework" id="namework" cols="50"
                                     tabindex="6" rows="4"></textarea>
                        </td>               
                </tr>
                <tr>
                         <td class="table" width="40%" align="left">Type</td>
                         <td class="table" align="left">     
                                <input type="hidden" name="officeid" id="officeid"  value="<%=oid%>" size="40"/>
                                <input type="radio" name="firmContrType" value="2" onclick="callContractor();callAuthority()"/>
                                Firm
                                <!--<input type="radio" name="firmContrType" value="11" onclick="callContractor();"/>
                                Contractor   /contractor's Name                             
                         --></td>
                </tr>
                <tr>                                                                                                                                                    
                        <td class="table" width="40%" align="left">Firm's</td>
                        <td class="table" align="left"> 
                            <select name="firmContrName" id="firmContrName" onchange="callAddress(this.value);">
                            <option value="">select</option>
                            </select>
                        </td>               
                </tr>
                <tr>
                        <td class="table" width="40%" align="left">Address</td>
                        <td class="table" align="left"> 
                            <textarea name="address" id="address" cols="50"
                                tabindex="6" rows="4"></textarea>
                        </td>               
                </tr>
                <tr>
                        <td class="table" width="40%" align="left">Value of the work</td>
                        <td class="table" align="left"> 
                               <input type="text" name="valueofwork" id="valueofwork" onkeypress="return  numbersonlyallowed(event,this)" /><!--  value="55"
                        --></td>               
                </tr>
                <tr>
                        <td class="table" width="40%" align="left">Work/Supply</td>
                        <td class="table" align="left"> 
                             <input type="radio" name="wksup" checked="checked" value="W" onclick="callWork();"/>
                              Work
                              <input type="radio" name="wksup" value="S" onclick="callWork();"/>
                              Supply 
                           
                        </td>               
                </tr>
                <tr>
                        <td class="table" width="40%" align="left">Work/Supply Order No</td>
                        <td class="table" align="left"> 
                        			<select name="supplyno" id="supplyno" onchange="callwsDate();">
                                        <option value="">-- Select --</option>
                                    </select> 
                              <!-- <input type="text" name="supplyno" id="supplyno"/>   --> 
                        </td>               
                </tr>
                  <tr>
                <td class="table" width="40%" align="left">

                    Debit A/c Head Code 
                    <font color="#ff2121">*</font>
                </td>
                <td class="table" align="left">
                 
			                    <input type="text" name="txtAcc_HeadCode" 
			                           id="txtAcc_HeadCode" maxlength="6" onkeypress="return numbersonly(event)"
				                         onchange="sixdigit();" size="9"  onblur="doFunction11('checkCode1','null');" />
			                           <!-- onblur="checkAccHead();"/>  -->
			                    <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="AccountHeadList" onclick="AccHeadpopup();"></img>
			                    <input type="text" name="txtAcc_HeadDesc" readonly="readonly" 
			                           id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/>
			                  
                </td>
              </tr>
                </table>
                </div>
            </div>
            <div class="tab-page" id="gd" >
                <h2 class="tab" > Part-2 </h2>
                <div align="center">
                
                    <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
                     <tr>
                        <td class="table" width="40%" align="left">Work/Supply Order Date</td>
                        <td class="table" align="left"> 
                                     <input type="text" name="supplydate" size="20" maxlength="10" id="supplydate" onFocus="javascript:vDateType='3'"
                                    onkeypress="return  calins(event,this)" onblur="return checkdt(this);">
                                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.forms[0].supplydate);" alt="Show Calendar" ></img>  
                        </td>               
                     </tr>
                     <tr>
                        <td class="table" width="40%" align="left">Quotation No</td>
                        <td class="table" align="left"> 
                                 <input type="text" name="tenderno" id="tenderno" onkeypress="return  numbersonlyallowed(event,this)"/><!--  value="555"
                        --></td>               
                    </tr>
                     <tr>
                        <td class="table" width="40%" align="left">Quotation Date</td>
                        <td class="table" align="left"> 
                                     <input type="text" name="tenderdate" size="20" maxlength="10" id="tenderdate" onFocus="javascript:vDateType='3'"
                                    onkeypress="return  calins(event,this)" onblur="return checkdt(this);">
                                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.forms[0].tenderdate);" alt="Show Calendar" ></img>  
                        </td>               
                    </tr>
                    <tr>
                        <td class="table" width="40%" align="left">Quotation Details</td>
                        <td class="table" align="left"> 
                            <textarea name="tenderdetails" id="tenderdetails" cols="50"
                                tabindex="6" rows="4"></textarea>
                        </td>               
                    </tr>
                    <tr>
                             <td class="table" width="40%" align="left">Agreement Period</td>  
                             <td class="table" align="left">
                                 From
                                  
                                    <input type="text" name="from" size="20" maxlength="10" id="from" onFocus="javascript:vDateType='3'"
                                    onkeypress="return  calins(event,this)" onblur="return checkdt(this);">
                                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.forms[0].from);" alt="Show Calendar" ></img>  
                                 To
                                 
                                	 <input type="text" name="to" size="20" maxlength="10" id="to" onFocus="javascript:vDateType='3'"
                                    onkeypress="return  calins(event,this)" onblur="return checkdt(this);">
                                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.forms[0].to);" alt="Show Calendar" ></img>  
                                    
                                  <!--     <input type="text" name="to" size="20" maxlength="10" id="to" onFocus="javascript:vDateType='3'"
                                    onkeypress="return  calins(event,this)" onblur="return checkdt(this);">
                                    <img src="../../../../../images/calendr3.gif" onclick="showCalendar(document.forms[0].to);" alt="Show Calendar" ></img> --> 
                             </td>
                    </tr>
                    <tr>
                            <td class="table" width="40%" align="left">Concluding Authority</td>
                            <td class="table" align="left"> 
                                    <select name="authority" id="authority">
                                        <option value="">-- Select   Authority --</option>
                                    </select> 
                            </td>               
                    </tr>
                    <tr class="table">
                            <td width="40%" align="left">Section Name in case of HO</td>
                            <td>
                                      <div align="left">
                                                
                                          <select name="cmb_HO_acc_unitid" id="cmb_HO_acc_unitid" size="1"  onchange="doFunction('office_with_bank_betails','null');">
                                          <option value="" >--Select the Unit --</option>
                                          <%
                                              // This is mainly for Head office ( Unit wise ) purpose
                                              PreparedStatement ps9=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID in (3,6)");
                                              ResultSet rs9=ps9.executeQuery();
                                              while(rs9.next())
                                              {
                                                 out.println("<option value="+rs9.getInt("ACCOUNTING_UNIT_ID")+">"+rs9.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                              }
                                          %>
                                          </select>
                                       </div>
                                        
                                    <!--    <div align="left">
                                        <input type="text" id="txtOfficeName" name="txtOfficeName" size="75" readonly="readonly" style="background-color: #ececec" />
                                        </div> -->
                            </td>               
                    </tr>
                    <tr>
                            <td class="table" width="40%" align="left">Document Upload</td>
                            <td class="table" align="left"> 
	                            <div align="left" id="browseid"  style="display:block">
	                                     <input type="file" id="onbrowse" name="onbrowse" />
	                                     <input type="submit" id="onattach" name="onattach" value="Attach" />
	                            </div>  
	                          <!--    <div align="left" id="docid"  style="display:none">
	                                <input type="text" name="setdoc" id="setdoc"></input> 
	                           </div>  -->
                            </td>  
                                        
                    </tr>
                                  
                    <tr class="table">
                        <td width="40%">
                                 <div align="left">Concluded By<font color="#ff2121">*</font> </div>
                         </td>
                         <td width="60%">
                         <table align="left">
                         <tr align="left">
                                  <td>
                                          <div align="left">
                                                <select size="1" name="cmbSL_Code" id="cmbSL_Code" >
                                                  <option value="">--Select Code--</option>
                                                </select>
                                          </div>
                                  </td>
                                  <td>
                                           
                                          <div align="left" id="emplist_div_trans" >
                                                <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="employee_popup_trans();"></img>
                                                <input type="text" name="txtEmpID_trs" id="txtEmpID_trs" maxlength="5" size="5"  onblur="trs_employee(this.value);" />
                                          </div>
                                         <div align="left" id="offlist_div_trans"  style="display:none">
                                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" onclick="jobpopup_trans();"></img>
                                            <input type="text" name="txtOfficeID_trs" id="txtOfficeID_trs" maxlength="4" size="5"    onblur="trs_office(this.value);" />
                                         </div>
                                         <div style="display:none">
                                                <select size="1" name="cmbSL_type" id="cmbSL_type">
                                                    <option value=7>--Select Type--</option>                         
                                                </select>
                                                <select  name="cmbMas_SL_type" id="cmbMas_SL_type" tabindex="6"></select>
                                                <select  name="cmbMas_SL_Code" id="cmbMas_SL_Code" tabindex="6"></select>
                                         </div>
                                   </td>
                           </tr>
                         </table>
                         </td>
                     </tr>
                     <tr>
                            <td class="table" width="40%" align="left">Remarks</td>
                            <td class="table" align="left"> 
                                <textarea name="remarks" id="remarks" cols="50"
                                    tabindex="6" rows="4"></textarea>
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
                <td  colspan="2">
                    <div align="center"> 
                        <input type="button" name="onadd" value="Add" id="onadd" onclick="add();"/>  
                        <input type="button" name="onupdate" value="Update" id="onupdate" onclick="update();" disabled="disabled"/> 
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