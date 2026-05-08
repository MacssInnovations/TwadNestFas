<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Imprest/Temp.Advance A/c Journal System</title>
      
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
       
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case_FinalHead_GJV.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType_NegativeAmtAllowed.js"></script>
    <script type="text/javascript" src="../scripts/Imprest_Journal_Create.js"></script>
    
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>      
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>  
     
     
      
  
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
	     //    document.frmJournal_Imprest.txtCrea_date.value=day+"/"+month+"/"+year;
	         document.frmJournal_Imprest.txtCB_Year.value=year;
               /*  document.frmJournal_Imprest.fin_year1.value="01/04/"+year;
                var year_fin=year+1;
                 document.frmJournal_Imprest.fin_year2.value="31/03/"+year_fin;  */
             doFunction('checkCode','null');
	       //  callHeadCode(this.value);
         }
        
   
</script>
  </head>
   <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body onload="LoadAccountingUnitID_Create('LIST_ALL_UNITS');call_clr();setTimeout('loadDate()', 300);" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Create Imprest/Temp.Advance A/c Journal System </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmJournal_Imprest" id="frmJournal_Imprest" method="POST" 
                action="../../../../../Imprest_Journal_Create?Command=Add"
                onsubmit="return checkNull()">
    <%
  
			  Connection con=null;
			  ResultSet rs=null,rs2=null;
			  PreparedStatement ps=null,ps2=null;
			  ResultSet results=null;
			  Statement st=null;
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
	     String oname="",ac_head_name="";
	     int ac_head_code=820103;
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
	     
	     try
	     {
	      		
	      		st=con.createStatement();
	      		rs=st.executeQuery("select account_head_code,account_head_desc from com_mst_account_heads where account_head_code="+ac_head_code);
	      		if(rs.next())
	      		{
	      			ac_head_name=rs.getString("account_head_desc");
	      		}
	      		rs.close();
	      			
	     }
	     catch(Exception e)
	     {
	    	    System.out.println("Err in selecting acc head code:::"+e.getMessage());
	     }
   
   %>
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
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice_New(this.value);">
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
	              <div align="left">Payment Year &amp; Payment Upto Month</div>
	            </td>
	            <td>
	              <div align="left">
	                <input type="text" name="txtCB_Year" id="txtCB_Year"
	                       tabindex="3" maxlength="4" size="5"
	                       onkeypress="return numbersonly(event)" ></input>
                               <!--   onkeypress="return numbersonly(event)" onchange="loadVoucher();"></input> -->
	                 
	                <select name="txtCB_Month" id="txtCB_Month" tabindex="4" onchange="financialYear();">
	                  <option value="">Select Month</option>
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
                        <input type="hidden" name="fin_year1" id="fin_year1"/>
                        <input type="hidden" name="fin_year2" id="fin_year2"/>
	              </div>
	            </td>
	          </tr>
              
            
           	  <tr class="table">
                <td width="30%">
                  <div align="left">
                    Journal Type
                    <font color="#ff2121">*</font> 
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select name="cmbMas_SL_type" id="cmbMas_SL_type" tabindex="4" onchange="loadVoucher();loadAccountHead();">
                    <%
			                     try
			                     {
			                            ps=con.prepareStatement("select JOURNAL_TYPE_CODE,JOURNAL_TYPE_DESC from FAS_MST_JOURNAL_TYPE where CATEGORY='G' and JOURNAL_TYPE_CODE in(68,69)  order by JOURNAL_TYPE_DESC");
			                            rs=ps.executeQuery();
			                            while(rs.next())
			                            {
			                            		System.out.println("Journal Type ::: "+rs.getInt("JOURNAL_TYPE_CODE"));
			                            		if(rs.getInt("JOURNAL_TYPE_CODE")==68){
			                            			//	out.println("<option value="+rs.getInt("JOURNAL_TYPE_CODE")+" selected>"+"Imprest Journal-Payment"+"</option>");
			                            			out.println("<option value="+"68-BPF"+" selected>"+"Imprest Journal-Payment"+"</option>");
			                            			out.println("<option value=68-SC>"+"Imprest Journal-SelfCheque"+"</option>");
			                            		}
			                            		else{
			                            			out.println("<option value=69-BPF>"+"Temporary Adv.  Journal-Payment"+"</option>");
			                            			out.println("<option value=69-SC>"+"Temporary Adv.  Journal-SelfCheque"+"</option>");
			                            		}
			                            }
			                        
			                     } 
			                     catch(Exception e)
			                     {
			                    	System.out.println("Exception in Journal combo..."+e);
			                     }
			                     finally
			                     {
				                    rs.close();
				                    ps.close();
			                     }  
		                %>
                       </select>
                       
                       
	                   <!-- The following field 'cmbMas_SL_Code ' doesn't used for anything , but this field name is refered in common_Function_SL_code.js ,
	                            that's why this is simply added here -->
	                     <select size="1" name="cmbMas_SL_Code" id="cmbMas_SL_Code" tabindex="4" style="display:none" >
	                     </select>
	                   <!-- the above is no use here , so it is always style.display='none' -->
                  </div>
                </td>
              </tr>
              
              
               <tr class="table">
                <td>
                  <div align="left">
                    Imprest/Temporary Advance &nbsp;Payment Voucher Number
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select name="txtJournalVou_No" id="txtJournalVou_No" onchange="changeLink()">
                    <option value="">--Select Voucher--</option>
                    </select> 
                    <a id="linkId" href="javascript:checkVoucherNo();" style="visibility:hidden">Voucher Details</a>
                    <input type="text" name="pay_date" id="pay_date" readonly/>
                    <input type="hidden" name="pay_month" id="pay_month" readonly/>
                  </div>
                </td>
              </tr>
              
                <tr class="table">
                <td>
                  <div align="left">
                    Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCrea_date" id="txtCrea_date" tabindex="3" 
                           maxlength="10" size="11" readonly="readonly"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onchange="checkPayDate();"
                           onblur="return call_date(this);checkPayDate();dateCheck(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmJournal_Imprest.txtCrea_date,1);"
                         alt="Show Calendar"></img>
                    
                           
                    
                  </div>
                </td>
              </tr>
             
              <tr class="table">
                <td width="30%">
                  <div align="left">Remarks
                   <font color="#ff2121">*</font></div>
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
                  <div id="sub_ledge_dis" >
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
		                    	Account Head Code <font color="#ff2121">*</font>
		                   </div>
			            </td>
		                <td>
		                  <div align="left">
		                    <input type="text" name="txtAcc_HeadCode" 
		                           id="txtAcc_HeadCode" maxlength="6"
		                           value=<%=ac_head_code%>  size="9"  onchange="callHeadCode(this.value);" onblur="chkHeadcode();"/>
                            <img src="../../../../../images/c-lovi.gif" width="20" 
	                             height="20" alt="AccountHeadList"
	                             onclick="AccHeadpopup();"></img>
		                    <input type="text" name="txtAcc_HeadDesc" value=<%=ac_head_name%>readonly="readonly" 
		                           id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/>
		                  </div>
		                </td>
              		  </tr>
             
             
		              <tr class="table">
		                <td>
		                  <div align="left">
		                    CR/DR
		                    <font color="#ff2121">*</font>
		                  </div>
		                </td>
		                <td>
		                  <div align="left">
		                    <input type="radio" name="rad_sub_CR_DR" id="rad_sub_CR_DR" checked="checked"  onclick="checkIndicator();"  value="CR"/>Credit
		                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
		                    <input type="radio" name="rad_sub_CR_DR" id="rad_sub_CR_DR" onclick="checkIndicator();" value="DR"/>Debit &nbsp;&nbsp;&nbsp;&nbsp; 
		                    
		                  </div>
		                </td>
		              </tr>
		              
		              
                      <tr class="table">
		                <td width="40%">
		                  <div align="left">Sub-Ledger Type  <font color="#ff2121">*</font> </div>
		                </td>
		                <td width="60%">
		                  <div align="left">
		                   <select size="1" name="cmbSL_type" id="cmbSL_type" onchange="loadSLType('null',this.value);">
		                      <option value="">--Select Type--</option>
		                     
		                    </select>
		                  </div>
		                </td>
		              </tr>
              
              
		              <tr class="table">
		                <td width="40%">
		                  <div align="left">Sub-Ledger Code  <font color="#ff2121">*</font> </div>
		                </td>
		              	<td width="60%">
				            <table align="left">
				            
				             <tr align="left">
					             <td>
					                  <div align="left">
					                        <select name="cmbSL_Code" id="cmbSL_Code" onchange="loadSLCodeText()">
					                                
					                          <option value="">--Select Code--</option>
					                        </select>
					                  </div>
					              </td>
					              <td>
					                  <div align="left" id="offlist_div_trans"  style="display:none">
					                    <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" onclick="jobpopup_trans();"></img>
					                    <input type="text" name="txtOfficeID_trs" id="txtOfficeID_trs" maxlength="4" size="5"    onblur="trs_office(this.value);" />
					                  </div>
					                  <div align="left" id="emplist_div_trans"  style="display:none">
					                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="employee_popup_trans();"></img>
					                            <input type="text" name="txtEmpID_trs" id="txtEmpID_trs" maxlength="5" size="5"  onblur="trs_employee(this.value);" />
                                                <input type="hidden" name="total_remaining_amt" id="total_remaining_amt"></input>
					                 </div>
					               </td>             
            				 </tr>
            				 
            				 
           					</table>
        			  	</td>
              		  </tr>
                          
                        <tr class="table">
                            <td>
                              <div align="left">
                               Consolidate/Final Payment
                              </div>
                            </td>
                            <td>
                              <div align="left">
                                  <input type="radio" id="finalPayment" name="finalPayment" value="Y"   onclick="checkTtlAmt()" /> Yes &nbsp;&nbsp;
                                  <input type="radio" id="finalPayment" name="finalPayment" value="N" checked onclick="checkTtlAmt()" /> No
                                 
                              </div>
                            </td>
                          </tr>   
                
                      <tr class="table">
                        <td>
                          <div align="left">
                            Bill Number 
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="txtBill_NO" maxlength="10" onkeypress="return numbersonly(event)"
                                   id="txtBill_NO" size="11"/>
                          </div>
                        </td>
                      </tr>
                      
                      
                      <tr class="table">
                        <td>
                          <div align="left">
                            Bill Date 
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="txtBill_date"
                                   id="txtBill_date" maxlength="10" size="11"
                                   onfocus="javascript:vDateType='3';"
                                   onkeypress="return calins(event,this);"
                                   onblur="return checkdt(this);"/>
                             
                            <img src="../../../../../images/calendr3.gif"
                                 onclick="showCalendarControl(document.frmJournal_Imprest.txtBill_date);"
                                 alt="Show Calendar"></img>
                          </div>
                        </td>
                      </tr>
                      
                      
                      <tr class="table">
                        <td>
                          <div align="left">
                            Bill Type 
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="txtBill_type"
                                   id="txtBill_type" size="50"/>
                          </div>
                        </td>
                      </tr>
                      
                      
                      <tr class="table">
                        <td>
                          <div align="left">
                            Agreement Number 
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="txtAgree_No"
                                   id="txtAgree_No" size="11" maxlength="10"/>
                          </div>
                        </td>
                      </tr>
                      
                      
                      <tr class="table">
                        <td>
                          <div align="left">
                            Agreement Date 
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="txtAgree_Date"
                                   id="txtAgree_Date" maxlength="10" size="11"
                                   onfocus="javascript:vDateType='3';"
                                   onkeypress="return calins(event,this);"
                                   onblur="return checkdt(this);"/>
                             
                            <img src="../../../../../images/calendr3.gif"
                                 onclick="showCalendarControl(document.frmJournal_Imprest.txtAgree_Date);"
                                 alt="Show Calendar"></img>
                          </div>
                        </td>
                      </tr>
                      
                      
		              <tr class="table">
		                <td>
		                  <div align="left">
		                     Amount 
		                    <font color="#ff2121">*</font>
		                  </div>
		                </td>
		                <td>
		                  <div align="left">
		                    <input type="text" name="txtsub_Amount" onkeypress="return limit_amt_journal(this,event);" onblur="valid_amt(this);account_head_code()"
		                           id="txtsub_Amount" maxlength="17" size="18"/>
		                  </div>
		                </td>
		              </tr>
		              
		              
		              <tr class="table">
		                <td>
		                  <div align="left">Particulars</div>
		                </td>
		                <td>
		                  <div align="left">
		                    <textarea name="txtParticular" id="txtParticular" cols="70"  onkeypress="return check_leng('particulars',this.value);"
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
                                 value="ADD" onclick="ADD_GRID();" style="display:block"/></td>
                          <td>
                          <input type="button" name="cmdupdate" value="UPDATE"
                                 id="cmdupdate" onclick="update_GRID()"
                                 style="display:none"/></td>
                          <td><input type="button" name="cmddelete" value="DELETE"
                                 id="cmddelete" onclick="delete_GRID()"
                                 disabled="disabled"/></td>
                          <td><input type="button" name="cmdclear" value="CLEAR ALL"
                                 id="cmdclear" onclick="clearall()"/></td>
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
                        <th >Select</th>
                        <th >A/c Head Code</th>
                        <th >CR/DR</th>
                        <th>Sub Ledger Type</th>
                        <th >Sub Ledger Code</th>
                         <th>Bill No.</th>
                        <th>Bill Date</th>
                        <th >Amount</th>                       
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
    </form></body>
</html>