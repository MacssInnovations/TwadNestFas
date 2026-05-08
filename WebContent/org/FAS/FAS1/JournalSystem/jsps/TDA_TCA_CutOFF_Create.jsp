<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="/org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ page import="java.sql.Date" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
<title>Rectification_Journal_Create</title>
  
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
       
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case_FinalHead_GJV.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType_NegativeAmtAllowed.js"></script>
    <script type="text/javascript" src="../scripts/Rectification_Journal_Create.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>      
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>  
     <script type="text/javascript"
     src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
     
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
        document.frmJournal_General.txtCrea_date.value=day+"/"+month+"/"+year;
        
         }
        
   
</script>
  </head>
   <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body onload="LoadAccountingUnitID_Create('LIST_ALL_UNITS');call_clr();loadDate();setTimeout('loadTransferUnit()',500);" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Create TCA / TDA Cut off Journal System </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmJournal_General" id="frmJournal_General" method="POST"
                  action="../../../../../Rectification_Journal_Create?Command=Add"
                  onsubmit="return checkNull()">
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
           // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  long l=System.currentTimeMillis();
	Timestamp ts=new Timestamp(l);                      
	 Date ctdate = new java.sql.Date(ts.getTime()); 
  %>
      <% 
        HttpSession session=request.getSession(false);
         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();
    //int empid=9315;
    int  oid=0;
    String oname="";
   
    try
    {
           
           // ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
          //  ps.setInt(1,empid);
           ps=con.prepareStatement(" SELECT "+
            		"  CASE "+
            		 "   When Old_Office_Id   Is Not Null "+
            		  "  AND DATE_ALLOWED_UPTO>=? "+
            		    " THEN OLD_OFFICE_ID "+
            		    " ELSE Office_Id "+
            		  " END AS OFFICE_ID "+
            		" FROM "+
            		  " (SELECT Office_Id, "+
            		    " OLD_OFFICE_ID, "+
            		    " DATE_ALLOWED_UPTO "+
            		  " From Hrm_Emp_Current_Posting "+
            		  " Where Employee_Id=? )" );
            ps.setDate(1, ctdate);
            ps.setInt(2,empid);
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
          <h2 class="tab" >General </h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong>Office Details</strong>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Office&nbsp;Name</div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAcc_unitName"
                           id="txtAcc_unitName" value="<%=oname%>"
                           maxlength="60" size="60" readonly="readonly"
                           class="disab"/>
                  </div>
                </td>
              </tr>
              <tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong>General Details</strong>
                  </div>
                </td>
              </tr>
                <tr class="table">
                <td>
                  <div align="left" >
                  	  Accounting Unit Code  <font color="#ff2121">*</font>
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
                    Accounting For Office Code <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2" >
                      
                    </select>
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
                           
                           onblur="call_mainJSP_script(this,1);dateCheck(this); "/>
                        <!--   
                           onblur="return checkdt(this);"/>  -->
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmJournal_General.txtCrea_date,1);"
                         alt="Show Calendar"></img>
                    
                           
                    
                  </div>
                </td>
              </tr>
              <!--<tr class="table" style="display:none">
                <td>
                  <div align="left">
                    Cash Book Month & Year
                    
                  </div>
                </td>
                <td>
                  <div align="left">
                  
                    <input type="hidden" name="txtCash_Month_hid"
                           id="txtCash_Month_hid" size="2" maxlength="2"/>
                    <input type="text" name="txtCash_Month" id="txtCash_Month"
                           style="background-color: #ececec"  readonly="readonly"  maxlength="10" size="11"/>
                    <input type="text" name="txtCash_year" id="txtCash_year"
                           style="background-color: #ececec"  readonly="readonly"   maxlength="4" size="5"/>
                  </div>
                </td>
              </tr>-->
              
              <tr class="table">
                <td>
                  <div align="left">
                    Journal&nbsp;Voucher Number
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="hidden" name="txtJournalVou_No" id="txtJournalVou_No"  
                    style="background-color: #ececec"  readonly="readonly" size="6" maxlength="5"/>(System Generated)
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
                    <select size="1" name="cmbMas_SL_type" id="cmbMas_SL_type" tabindex="4" onchange="enable_cheque(this.value)">
                      <option value="">--- Select ---</option>
               <%
                     try
                     {
                            ps=con.prepareStatement("select JOURNAL_TYPE_CODE,JOURNAL_TYPE_DESC from FAS_MST_JOURNAL_TYPE where CATEGORY='G' and JOURNAL_TYPE_CODE in (63,64,66,67,92,93) order by JOURNAL_TYPE_DESC");
                            rs=ps.executeQuery();
                            while(rs.next())
                            {
                            out.println("<option value="+rs.getInt("JOURNAL_TYPE_CODE")+">"+rs.getString("JOURNAL_TYPE_DESC")+"</option>");
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
                            that's why this si simply added here -->
                     <select size="1" name="cmbMas_SL_Code" id="cmbMas_SL_Code" tabindex="4" style="display:none" >
                     </select>
                     <!-- the above is no use here , so it is always style.display='none' -->
                     
                  
                  </div>
                </td>
              </tr>
              </table>
              <div style="display:none" id="CHD" >
              <table cellspacing="1" cellpadding="2" border="1" width="100%">
               <tr class="table" >
                <td width="30%">
                  <div align="left">
                    Cheque Number and Date
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCheque_NO" maxlength="10" onkeypress="return numbersonly(event)"
                           id="txtCheque_NO" size="11" tabindex="5"/>
                     <input type="text" name="txtCheque_date" id="txtCheque_date"  
                           maxlength="10" size="11" tabindex="6"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmJournal_General.txtCheque_date);"
                         alt="Show Calendar"></img>
                  </div>
                </td>
              </tr>
              </table>
              </div>
              
              <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr class="table">
                <td width="30%">
                  <div align="left">
                  Remarks
                   <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="50" tabindex="7" onkeypress="return check_leng(this.value);"
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
                            <strong> Details</strong>                          </div>                        </td>
                      </tr>
                
                      <tr class="table">
                <td>
                  <div align="left">
                    Account Head Code 
                    <font color="#ff2121">*</font>                  </div>                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAcc_HeadCode" 
                           id="txtAcc_HeadCode" maxlength="6"
                           onkeypress="return numbersonly(event)"
                            onchange="callHeadCode(this.value);sixdigit();" 
                            onblur="accHdCheck();"  size="9"/>
                    <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
                    <input type="text" name="txtAcc_HeadDesc" readonly="readonly" 
                           id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/>
                  </div>                </td>
              </tr>
             
              <tr class="table">
                <td>
                  <div align="left">
                    CR/DR
                    <font color="#ff2121">*</font>                  </div>                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="rad_sub_CR_DR" id="rad_sub_CR_DR"
                           checked="checked" value="CR"/>Credit
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="rad_sub_CR_DR" id="rad_sub_CR_DR"
                           value="DR"/>Debit&nbsp;&nbsp;&nbsp;&nbsp;                  </div>                </td>
              </tr>
                       <tr class="table">
                <td width="40%">
                  <div align="left">Sub-Ledger Type  <font color="#ff2121">*</font> </div>                </td>
                <td width="60%">
                  <div align="left">
                   <select size="1" name="cmbSL_type" id="cmbSL_type" onchange="callThis();">
                      <option value="15">Accounting Units</option>
                    </select>
                  </div>                </td>
              </tr>
              
              <tr class="table">
                <td width="40%">
                  <div align="left">Sub-Ledger Code  <font color="#ff2121">*</font> </div>                </td>
              <td width="60%">
            <table align="left">
             <tr align="left">
             <td>
                  <div align="left">
                        <select size="1" name="cmbSL_Code" id="cmbSL_Code"  onchange="toSetSubLedgerTypeCode();">
                                
                          <option value="">--Select Code--</option>
                        </select>
                  </div>              </td>
              <td>
                  <div align="left" id="offlist_div_trans"  style="display:none">
                    <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" onclick="jobpopup_trans();"></img>
                    <input type="text" name="txtOfficeID_trs" id="txtOfficeID_trs" maxlength="4" size="5"    onblur="trs_office(this.value);" />
                  </div>
                  <div align="left" id="emplist_div_trans"  style="display:none">
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="employee_popup_trans();"></img>
                            <input type="text" name="txtEmpID_trs" id="txtEmpID_trs" maxlength="5" size="5"  onblur="trs_employee(this.value);" />
                 </div>               </td>
            </tr>
           </table>        </td>
              </tr>
                         <tr class="table">
                        <td>
                          <div align="left">
                            Bill Number                          </div>                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="txtBill_NO" maxlength="10" onkeypress="return numbersonly(event)"
                                   id="txtBill_NO" size="11"/>
                          </div>                        </td>
                      </tr>
                      <tr class="table">
                        <td>
                          <div align="left">
                            Bill Date                          </div>                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="txtBill_date"
                                   id="txtBill_date" maxlength="10" size="11"
                                   onfocus="javascript:vDateType='3';"
                                   onkeypress="return calins(event,this);"
                                   onblur="return checkdt(this);"/>
                             
                            <img src="../../../../../images/calendr3.gif"
                                 onclick="showCalendarControl(document.frmJournal_General.txtBill_date);"
                                 alt="Show Calendar"></img>                          </div>                        </td>
                      </tr>
                      <tr class="table">
                        <td>
                          <div align="left">
                            Bill Type                          </div>                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="txtBill_type"
                                   id="txtBill_type" size="50"/>
                          </div>                        </td>
                      </tr>
                    
              <tr class="table">
                <td>
                  <div align="left">
                     Amount 
                    <font color="#ff2121">*</font>                  </div>                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtsub_Amount" onkeypress="return limit_amt_journal(this,event);" onblur="checkamount(this);"
                           id="txtsub_Amount" maxlength="17" size="18"/>
                  </div>                </td>
              </tr> 
               <tr class="table">
                <td>
                  <div align="left">Originated/Accepted  Year <font color="#ff2121">*</font>                  </div>                </td>
                <td>
                  <div align="left">
                    <input type="text" name="adjyear" id="adjyear" size="5"/>
                  </div>                </td>
              </tr> 
               <tr class="table">
                <td>
                  <div align="left">Originated/Accepted  Month<font color="#ff2121">*</font>                  </div>                </td>
                <td>
                  <div align="left">
                     <select name="adjmonth"  id="adjmonth" onchange="tohidedoc()">
       				 <option value="">--Select Month--</option>
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
                  </div>                </td>
              </tr>
               <tr class="table">
                 <td><div align="left">Originated  / Accepted Unit<font color="#ff2121">*</font> </div></td>
                 <td><div align="left">
                   <select name="txtUnitId" id="txtUnitId">
                     <option value="">Select Unit</option>
                   </select>
                 </div></td>
               </tr> 
              
                <tr class="table">
                <td>
                  <div align="left" id="since">
                     Doc.Type &amp; Doc No. (Since sep'2007)
                    <font color="#ff2121">*</font>                  </div>
                  <div align="left" id="prior" style="display:none">
                   Doc.Type &amp; Doc No. (prior sep'2007)
                    <font color="#ff2121">*</font>                  </div>                </td>
                <td>
                  <div align="left" id="since2007">
                 <select name="paymentreceipt"  id="paymentreceipt" onchange="payreceipt()">
       			  <option value="">--Select--</option>
		          <option value="R">Receipt</option>
		          <option value="P">Payment</option>
		          <option value="J">Journal</option>		          
		          </select>
		          <select name="receiptno"  id="receiptno">
       			<option value="">--Select--</option>								
       			</select>
       			  <img src="../../../../../images/view_details_icon.png" width="20" 
                             height="20" alt="Details"
                             onclick="payreceiptdetails();"></img>                  </div>
                <div align="left" id="prior2007" style="display:none">
                  <select name="paymentreceipt1"  id="paymentreceipt1"  >
       			 <option value="">--Select--</option>
		         <option value="R">Receipt</option>
		         <option value="P">Payment</option>
		          <option value="J">Journal</option>
		         <option value="FR">Fund Receipt</option>
		      	 <option value="FT">Fund Transfer</option>
		      	 <option value="IBT">IBT</option>
		          </select>
		            <input type="text" name="receiptno1" id="receiptno1"  />
                  </div>                </td>
              </tr> 
               
              
              <tr class="table">
                <td>
                  <div align="left">Particulars</div>                </td>
                <td>
                  <div align="left">
                    <textarea name="txtParticular" id="txtParticular" cols="50"  onkeypress="return chk_part_len(this.value);"
                              rows="4"></textarea> <textarea name="txtIndicationMsg" id="txtIndicationMsg" cols="50" rows="4" disabled="disabled"></textarea>
                  </div>                </td>                                                     
                  
              </tr>
                      <tr class="tdTitle">
                        <td colspan="2" height="23">
                         <div align="center">
                            <table border="0">
                          <tr><td><input type="button" name="cmdadd" id="cmdadd"
                                 value="ADD" onClick="checkSubLedgerMandatory();" style="display:block"/></td>
                          <td>
                          <input type="button" name="cmdupdate" value="UPDATE"
                                 id="cmdupdate" onclick="checkSubLedgerMandatory1();"
                                 style="display:none"/></td>
                          <td><input type="button" name="cmddelete" value="DELETE"
                                 id="cmddelete" onclick="delete_GRID()"
                                 disabled="disabled"/></td>
                          <td><input type="button" name="cmdclear" value="CLEAR ALL"
                                 id="cmdclear" onclick="clearall()"/></td>
                        </tr>
                        </table>
                        </div>                        </td>
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
                       <th >ADJ.Year</th>
                       <th >ADJ.Month</th>
                       <th >Doc.Type</th> 
                       <th >Doc.No</th>
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