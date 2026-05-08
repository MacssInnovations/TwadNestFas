<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,java.text.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="/org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Cheque Memo to Liability Journal</title>
      
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
   
    <script type="text/javascript" src="../scripts/cheqmemo_liabjournal.js"></script>

    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
 
    <script type="text/javascript" src="../scripts/Common_ReceiptType.js"></script>
   
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>     
    <script type="text/javascript"  src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"  src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl_forChequeDate.js"></script>     
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Date_Check.js"></script>    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
     <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>  
   
   
   
   
  </head>
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Cheque Memo to Liability Journal</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="cheque_memo" id="cheque_memo" method="post" action="../../../../../Cheqmemo_liabjournal?Command=Add" onsubmit="return nullcheck()">
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
  
  DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
  java.util.Date date = new java.util.Date();
  
  %>
      <% 
      try{
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
     /* */      
                 
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
      <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" >General</h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              
               <tr class="table">
								<td>
							    <div align="left">Accounting Unit Code <font color="#ff2121">*</font>	    </div></td>
								<td>
								  <div align="left">
								    <select size="1" name="cmbAcc_UnitCode"
									id="cmbAcc_UnitCode" tabindex="1"
									onchange="common_LoadOffice(this.value);">
							          </select>
						      </div></td>
						</tr>
                        <tr class="table">
                          <td>Accounting Unit Office Name<span class="style1">* </span> </td>
                          <td><select name="cmbOffice_code" id="cmbOffice_code" ></select></td>
                        </tr>
                <tr class="table">
                <td>
                  <div align="left">Check Memo Type</div>
                </td>
                <td>
                  <div align="left">
                   <select size="1" name="memotype" id="memotype" onchange="call('getcode')">   
                   <option value="">--Select Memo Type--</option>  
                                                        
                   <%    ps=con.prepareStatement("select CHEQUE_MEMO_TYPE_CODE,CHEQUE_MEMO_DESC from FAS_CHEQUE_MEMO_TYPES_MST where STATUS='L'");
                   		rs=ps.executeQuery();
                   		while(rs.next())
                   		{
                   		 out.println("<option value="+rs.getInt("CHEQUE_MEMO_TYPE_CODE")+">"+rs.getString("CHEQUE_MEMO_DESC")+"</option>");	
                   		}
                   
                		   %>
                		   </select>
                  </div>
                </td>
              </tr>
              
              
               <tr class="table">
                <td>
                  <div align="left">Check Memo Date <font color="#ff2121">*</font>  </div>
                </td>
                <td>
                  <div align="left">
                   <input type="text" id="memodate" name="memodate" onblur="call('get')" 
                   onfocus="javascript:vDateType='3';"  onkeypress="return calins(event,this);" 
                     maxlength="10" size="11">   
                   </div>
                </td>
              </tr>
              
              
                <tr class="table">
                <td>
                  <div align="left">Voucher Date </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" id="vochardate" name="vochardate"
                       onfocus="javascript:vDateType='3';"  onkeypress="return calins(event,this);"  
                     value="<%=dateFormat.format(date)%>"  maxlength="10" size="11">   
                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.cheque_memo.vochardate);"  alt="Show Calendar"></img>
                        
                  </div>
                </td>
              </tr>
              
               <tr class="table">
		          <td>
			          <div align="left">
			              Operation A/c Code  <font color="#ff2121">*</font>
			          </div>
			      </td>
			      <td>
			          <div align="left">			              
			              <input type="text" name="txtCash_Acc_code" id="txtCash_Acc_code"  style="background-color: #ececec"  readonly="readonly" maxlength="8" size="9" />
                          <img src="../../../../../images/c-lovi.gif" width="20" 
				                             height="20" alt="AccountNumberList"
				                             onclick="MainAccNopopup();"></img>
			          </div>
			      </td>
			   </tr>               
			  
			  
			   <tr class="table">
                <td>
                  <div align="left">Bank Account Number  <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                  	  <input type="text" name="txtBankAccountNo"  id="txtBankAccountNo" size="15" style="background-color: #ececec"  readonly="readonly" />                                                  
                  </div>
                </td>
              </tr>
			 
			 
			  <tr class="table">
                <td>
                  <div align="left">Bank Name</div>
                </td>
                <td>
                  <div align="left">                     
                    <input type="text" name="txtBankName"  id="txtBankName" size=50/>
                    <input type="hidden" name="txtBankID" id="txtBankID"></input>
                    <input type="hidden" id="txtBranchID" name="txtBranchID"></input>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    CR/DR Indicator
                   
                  </div>
                </td>
                <td>
                  <div align="left">
                  <input type="hidden" name="txtCR_DB" id="txtCR_DB" value="CR" size="2"/>
                    <input type="text" name="txtCR_DB_desc" style="background-color: #ececec"  readonly="readonly" id="txtCR_DB_desc" value="CREDIT" size="6"/>
                  </div>
                </td>
              </tr>
            <tr class="table">
                <td>
                  <div align="left">Payee Type</div>
                </td>
                <td>
                  <div align="left">
                  <select name="payeetype" id="payeetype" >
                 <option value="">--Select--</option>
                                                    
                  
                 </select>                      
                       
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">Payee Code  <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                <input type="text" name="txtEmpId" id="txtEmpId" size="5" maxlength=5 onkeypress="return filter_real(event,this,5,0)" onchange="call('load')"></input>
                                                     
                <img src="../../../../../images/c-lovi.gif" width="20"  height="20" alt="empList"  onclick="servicepopup11();"></img>
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td >
                  <div align="left">particulars</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="particulars" id="particulars" cols="50" tabindex="7" onkeypress="return check_leng('particulars',this.value);"
                              rows="4"></textarea>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Total Cheque Amount (Rs. P.) 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAmount" readonly="readonly"  tabindex="8" onkeypress="return limit_amt(this,event);"  onblur="valid_amt(this);"
                           id="txtAmount" maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>
              </table>
             
              
             
          </div>
        </div>
          
        <div class="tab-page" id="gd" >
          <h2 class="tab" >Details</h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr>
                <td colspan="2">
                  <div id="sub_ledge_dis" >
                    <table cellspacing="1" cellpadding="2" border="1"
                           width="100%">
                      <tr class="tdTitle">
                        <td colspan="2">
                          <div align="left">
                            <strong>Details</strong>
                          </div>
                        </td>
                      </tr>
                
                      
                      
                        <tr class="table">
                <td>
                  <div align="left">
                    Account Head Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAcc_HeadCode" 
                           id="txtAcc_HeadCode" maxlength="6"
                           onkeypress="return filter_real(event,this,6,0)"
                           onblur="doFunction('checkCode','null');"  size="9" />
                   <img src="../../../../../images/c-lovi.gif" width="20" height="20"
                    alt="AccountHeadList" onclick="AccHeadpopup();"></img>
                    <input type="text" name="txtAcc_HeadDesc" readonly="readonly" 
                           id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/>
                  </div>
                </td>
              </tr>
             
                   
                      <tr class="table">
                        <td width="40%">
                          <div align="left">Sub-Ledger Type <font color="#ff2121">*</font>  </div>
                        </td>
                        <td width="60%">
                          <div align="left">
                           <select size="1" name="cmbSL_type" id="cmbSL_type" onchange="doFunction('Load_SL_Code',this.value);">
                              <option value="">--Select Type--</option>
                             
                            </select>
                          </div>
                        </td>
                      </tr>
                      
                      <tr class="table">
                        <td width="40%">
                          <div align="left">Sub-Ledger Code <font color="#ff2121">*</font>  </div>
                        </td>
                        <td width="60%">
                          <table align="left">
                                 <tr align="left">
                                 <td>
                                      <div align="left">
                                            <select size="1" name="cmbSL_Code" id="cmbSL_Code"  >
                                                    
                                              <option value="">--Select Code--</option>
                                            </select>
                                      </div>
                                  </td>
                                  <td>
                                      <div align="left" id="offlist_div_trans"  style="display:none">
                                        <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList"  onclick="jobpopup_trans();"></img>
                                        <input type="text" name="txtOfficeID_trs" id="txtOfficeID_trs" maxlength="4" size="5"  onblur="trs_office(this.value);" />
                                      </div>
                                      <div align="left" id="emplist_div_trans"  style="display:none">
                                        <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="employee_popup_trans();"></img>
                                        <input type="text" name="txtEmpID_trs" id="txtEmpID_trs" maxlength="5" size="5"  onblur="trs_employee(this.value);" />
                                     </div>
                                    
                                   </td>
                                 
                                </tr>
                          </table>
                        </td>
                      </tr>
                      <tr class="table">
                <td>
                  <div align="left">
                Bill No
                   
                  </div>
                </td>
                <td>
                  <div align="left">
                 
                    
                     <select size="1" name="billno" id="billno" onchange="call('getdata')" >
                              <option value="">--Select Bill No--</option>
                             
                            </select>
                    
                    
                  </div>
                </td>
              </tr>
                                          
                   <tr class="table">
                <td>
                  <div align="left">Bill Date </div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="billdate" disabled="disabled" >   
                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.cheque_memo.billdate);"  alt="Show Calendar"></img>             
                       
                  </div>
                </td>
              </tr>          
           
                      <tr class="table">
                        <td>
                          <div align="left">
                         Total Voucher Amount
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="passorderamount" disabled="disabled"  onkeypress="return filter_real(event,this,5,2)" id="passorderamount"  />
                          </div>
                        </td>
                      </tr>
                      <tr class="table">
		                <td>
		                  <div align="left">
		                    CR/ DR 
		                  </div>
		                </td>
		                <td>
		                  <div align="left">
		                    <input type="radio" name="Indi_CR_DR" id="Indi_CR_DR" readonly="readonly" value="CR"/>Credit &nbsp;&nbsp;&nbsp;&nbsp; 
		                    <input type="radio" name="Indi_CR_DR" id="Indi_CR_DR" readonly="readonly" checked="checked"
		                           value="DR"/>Debit  &nbsp;&nbsp;&nbsp;&nbsp;    
		                  </div>
		                </td>
		              </tr>
                 <tr class="table">
                        <td>
                          <div align="left">
                        DR Amount
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="dramount"  id="dramount" onkeypress="return filter_real(event,this,7,0)" />
                          </div>
                        </td>
                      </tr>
                      <tr class="table">
                <td>
                  <div align="left">Cheque No</div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="chequeno" name="chequeno" >                
                       
                  </div>
                </td>
              </tr> 
              
            <tr class="table">
                <td>
                  <div align="left">Cheque Date</div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="chequedate" name="chequedate" value="<%=dateFormat.format(date)%>" maxlength=10 size=11>   
                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.cheque_memo.chequedate);"  alt="Show Calendar"></img>             
                       
                  </div>
                </td>
              </tr>    
           		
                       <tr class="table">
                <td >
                  <div align="left">Remarks</div>
                    <input type="hidden" id="cmbMas_SL_type" />
               <input type="hidden" id="cmbMas_SL_Code" />
                </td>
                <td>
                  <div align="left">
                    <textarea name="remarks" id="remarks" cols="50" tabindex="7" onkeypress="return check_leng('remarks',this.value);"
                              rows="4"></textarea>
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
                                 id="cmdclear" onclick="clear()"/></td>
                        </tr>
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
                        <th >Bill No</th>
                        <th >Bill Date</th>
                        <th >PassOrder Amt</th>
                        <th >A/c Head Code</th>
                         <th >SL Type</th>
                         <th >SL Code</th>
                       <!--    <th >Cheque No</th>
                         <th >Cheque Date</th>
                         <th >Cheque Amount</th>  -->
                         <th >CR/DR</th>
                         <th >DR Amount</th>
                         <th >Remarks</th>
                         <th >Cheque No/Date</th>
                        
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
                <input type="submit" name="butSub" id="butSub" value="SUBMIT" />
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="clrForm();"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="exit();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form>
    
    <%
   
      
      }catch(Exception e){out.println(e);} %>
    
    </body>
</html>