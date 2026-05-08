<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache">
    <title>Bank Reconciliation System </title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>         
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
   
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>   
    <script type="text/javascript" src="../scripts/BRS_NonTwad.js"></script>   
    <script type="text/javascript" language="javascript">
       function loadyear_month()
       {
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             var year=today.getYear();
             if(year < 1900) year += 1900;
                        
           //  setTimeout('LoadBankAccountNumber()',900);
      
      }    
    </script>    
  </head>
   <%
  
		      Connection con=null;
		      ResultSet rs=null,rs2=null;
		      Statement st=null;
		      PreparedStatement ps=null;
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
  String s=request.getContextPath();
  %>
  <body onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');loadVlue();" >
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Bank Reconciliation System</font>
          </div>
        </td>
      </tr>
    </table>
    
    
    <form name="frmBRSNonTwad" id="frmBRSNonTwad" >
    <%
       int unit_id=Integer.parseInt(request.getParameter("unit_id"));
	  			int office_id=Integer.parseInt(request.getParameter("office_id"));
	  			int year=Integer.parseInt(request.getParameter("year"));	  			
	  			int month=Integer.parseInt(request.getParameter("month"));	  			
	  			String pas_dt=request.getParameter("pas_dt");
	  			String reason=request.getParameter("reason");
	  			int chk_no=Integer.parseInt(request.getParameter("chk_no"));	
	  			String details=request.getParameter("details");
	  			double Cr_amt=Double.parseDouble(request.getParameter("Cr"));	
	  			double Dr_amt=Double.parseDouble(request.getParameter("Dr"));	
	  			String act_req=request.getParameter("act_req");	
	  			long ac_no = Long.parseLong(request.getParameter("ac_no"));
	  			int slno=Integer.parseInt(request.getParameter("slno"));	  	
	  			out.println("<input type='hidden' name='acc_unit_id' id='acc_unit_id' value="+unit_id+">");
	  			out.println("<input type='hidden' name='office_code' id='office_code' value="+office_id+">");
	  			out.println("<input type='hidden' name='year' id='year' value="+year+">");
	  			out.println("<input type='hidden' name='month' id='month' value="+month+">");
	  			out.println("<input type='hidden' name='pas_dt' id='pas_dt' value="+pas_dt+">");
	  			out.println("<input type='hidden' name='reason' id='reason' value="+reason+">");
	  			out.println("<input type='hidden' name='chk_no' id='chk_no' value="+chk_no+">");
	  			out.println("<input type='hidden' name='details' id='details' value="+details+">");
	  			out.println("<input type='hidden' name='Cr_amt' id='Cr_amt' value="+Cr_amt+">");
	  			out.println("<input type='hidden' name='Dr_amt' id='Dr_amt' value="+Dr_amt+">");
	  			out.println("<input type='hidden' name='act_req' id='act_req' value="+act_req+">");
	  			out.println("<input type='hidden' name='BankAccNo' id='BankAccNo' value="+ac_no+">");
	  			out.println("<input type='hidden' name='slno' id='slno' value="+slno+">");
	  			 System.out.println("oblist");
	  			 %>
		<table cellspacing="1" cellpadding="2" border="1" width="100%">
              
               <tr class="table">
                <td>
                  <div align="left" >
                  	  Accounting Unit Code  <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                     <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);LoadBankAccountNumber();">
    
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
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                      
                    </select>
                  </div>
                </td>
              </tr>
              <tr class="table" >
                <td>
                  <div align="left">
                     Bank A/C No. <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                     <select name="cmbBankAccNo" id="cmbBankAccNo" onchange="loadmode(this.value);" >
                      <option value="">-- Select Bank A/C No ---</option>
                     </select>   
                     <input type="button" name="goo" id="goo" value="Go" onclick="LoadBankAccountNumber();"></input>
                     <input type="hidden" name="txtOprMode" id="txtOprMode"  tabindex="5"  
                          style="background-color: #ececec"  readonly="readonly"  size="50"/>
                     
                       
                  </div>
                </td>
              </tr>
              <tr class="table">
	            <td>
	              <div align="left">Cash Book Year &amp; Month <font color="#ff2121">*</font></div>
	            </td>
	            <td>
	              <div align="left">
	                <input type="text" name="txtCB_Year" id="txtCB_Year" onChange="LoadMonthYear('<%=s%>');"
	                       tabindex="3" maxlength="4" size="5" onkeypress="return numbersonly(event)"></input>	                 
	                <select name="txtCB_Month" id="txtCB_Month" tabindex="4" onChange="LoadMonthYear('<%=s%>');">
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
	              </div>
	            </td>
	          </tr>
	          
               <tr class="table">
                <td>
                  <div align="left">
                     Bank Name 
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtBankName" id="txtBankName"  tabindex="6"  
                          style="background-color: #ececec"  readonly="readonly"  size="30"/>
                   
                    <input type="hidden" name="txtBankID" id="txtBankID"    
                          style="background-color: #ececec"  readonly="readonly"  size="30"/>
                         <input type="hidden" name="serialno" id="serialno" />                 
                         <input type="hidden" name="constantacno" id="constantacno" />
                         <input type="hidden" name="trnyear" id="trnyear" />
                         <input type="hidden" name="trnmonth" id="trnmonth" />
                  </div>
                </td>
              </tr>
             
              <tr class="table">
                <td>
                  <div align="left">
                     Branch Name  
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtBranchName"  id="txtBranchName" 
                     size="35"  style="background-color: #ececec" readonly="readonly" />
                     
                    <input type="hidden" name="txtBranchID"  id="txtBranchID" 
                     size="35"  style="background-color: #ececec" readonly="readonly" />
                     
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left" style="display:block">
                     Operation A/c Code
                  </div>
                </td>
                <td>
                  <div align="left" style="display:block">
                    <input type="text" name="txtOprCode"  id="txtOprCode" 
                     size="6"  style="background-color: #ececec" readonly="readonly" />
                     
                  </div>
                </td>
              </tr>
               
             <tr class="table">
               <td>
                 <div align="left">
                   Pass Book Date 
                 </div>
               </td>
               <td>
                 <div align="left">
                   <input type="text" name="txtPassBook_date" tabindex="7"
                          id="txtPassBook_date" maxlength="10" size="11"
                          onfocus="javascript:vDateType='3';"
                          onkeypress="return calins(event,this);"
                          onchange="checkMonthYear();"
                          onblur="return checkdt(this);"/>
                    
                   <img src="../../../../../images/calendr3.gif"
                        onclick="showCalendarControl(document.frmBRSNonTwad.txtPassBook_date,0);"
                        alt="Show Calendar"></img>
                        <font color="red">
                        (Passbook Date Should be within Cashbookyear & Cashbookmonth)
                        </font>
                        
                 </div>
               </td>
             </tr>
             
             <tr class="table">
                <td>
                  <div align="left">Reason</div>
                </td>
                <td>
                  <div align="left">
                  <!--  <textarea name="txtParticular" id="txtParticular" tabindex="8" cols="70"  onkeypress="return check_leng('particulars',this.value);"
                              rows="3"></textarea>  -->
                    <select name="txtParticular" id="txtParticular" >
                    <option value="">--Select Reason--</option>
                    <%
                     try
                                     {
                                            ps=con.prepareStatement("select TRANS_CODE,TRANS_DESC from FAS_BRS_TRANSACTION_TYPE where TRANS_TYPE='NT' ");
                                            rs=ps.executeQuery();
                                            while(rs.next())
                                            {
                                               // out.println("<option value="+rs.getInt("TRANS_CODE")+" selected>"+rs.getString("TRANS_DESC")+"</option>");
                                               out.println("<option value="+rs.getString("TRANS_CODE")+">"+rs.getString("TRANS_DESC")+"</option>");
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
                  </div>
                </td>
              </tr>
              
               <tr class="table">
                 <td>
                   <div align="left">
	                    Cheque Number 
                   </div>
                </td>
                <td>
                   <div align="left">                   
                    	<input type="text" name="txtCheque_NO" id="txtCheque_NO" maxlength="17" size="18" tabindex="9" onkeypress="return numbersonly(event,this);"/>                   
                   </div>
                </td>
		  	   </tr>
		  	   
		  	   <tr class="table">
                 <td>
                   <div align="left">Details</div>
                 </td>
                 <td>
                   <div align="left">
                    <textarea name="txtDetails" id="txtDetails" cols="70"  onkeypress="return check_leng('Details',this.value);" tabindex="10"
                              rows="3"></textarea>
                   </div>
                 </td>
               </tr>
               <tr class="table">
                 <td>
                   <div align="left">
                     CR Amount 
                    <font color="#ff2121">*</font>
                   </div>
                 </td>
                 <td>
                   <div align="left">
                    <input type="text" name="txtCr_Amount" onkeypress="return filter_real(event,this,10,2)"
                           id="txtCr_Amount" maxlength="17" size="18"/>
                   </div>
                 </td>
              </tr>
              
              <tr class="table">
                 <td>
                   <div align="left">
                     DR Amount 
                    <font color="#ff2121">*</font>
                   </div>
                 </td>
                 <td>
                   <div align="left">
                    <input type="text" name="txtDr_Amount" onkeypress="return filter_real(event,this,10,2)"
                           id="txtDr_Amount" maxlength="17" size="18"/>
                   </div>
                 </td>
              </tr>
              
              <tr class="table">
	                <td>
	                  <div align="left">Follow-up action Required</div>
	                </td>
	                <td>
	                  <div align="left">
	                     <input type="radio" id="action_required" name="action_required" checked="checked" tabindex="13" value="Y"/> Yes
	                     <input type="radio" id="action_required" name="action_required" tabindex="14" value="N"/> No
	                  </div>
	                </td>
              </tr>
              <tr class="table">
	                <td>
	                  <div align="left" style="display:none">Clearance Entry based on Follow-up</div>
	                </td>
	                <td>
	                  <div align="left" style="display:none">
	                     <input type="radio" id="clearance_entry" name="clearance_entry" checked="checked" tabindex="15" value="Y"/> Yes
	                     <input type="radio" id="clearance_entry" name="clearance_entry" tabindex="16" value="N"/> No
	                  </div>
	                </td>
              </tr>
        </table>
        <div align="center">
         <table cellspacing="1" cellpadding="3" width="100%">
           <tr class="tdH">
            <td>
              <div align="center">
               
                <input type="button" name="butUpd" id="butUpd" value="Update" onclick="callServer('update')"/>
                <input type="button" name="butDel" id="butDel" value="Delete" onclick="callServer('delete')" />
                <!--<input type="button" name="butList" id="butList" value="List" onclick="ListAll()"/> 
                --><input type="button" name="butCan" id="butCan" value="CANCEL" onclick="clearAll();"/>
                <input type="button" name="butExit" id="butExit" value="EXIT"
                       onclick="javascript:self.close();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form>
  </body>
</html>