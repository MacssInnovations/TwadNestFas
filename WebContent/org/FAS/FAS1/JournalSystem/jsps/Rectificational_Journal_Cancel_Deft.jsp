<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Journal System</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <!-- to avoid future date the above script used-->
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>
    <script type="text/javascript" src="../scripts/Rectificational_Journal_Cancel_Deft.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>  
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">
          </script>
    <script type="text/javascript" language="javascript">
         function foc()
         {
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
        document.frmJournal_General_Cancel_Deft.txtCrea_date.value=day+"/"+month+"/"+year;
         doFunction('load_Voucher_No','null');
       // document.frmJournal_General_Cancel_Deft.txtCash_year.value=year;
        //document.frmJournal_General_Cancel_Deft.txtCash_Month_hid.value=month;
        //document.frmJournal_General_Cancel_Deft.txtCash_Month.value=monthArray[today.getMonth()];
         }
        
     function load_date_onfocus()
     {
        //alert("from here");
        if(document.frmJournal_General_Cancel_Deft.txtCrea_date.value.length!=0)
        {
           var DOC=document.frmJournal_General_Cancel_Deft.txtCrea_date.value
       
           var dd=DOC.split('/');
           var d1=dd[0];
           var m1=dd[1];
           var y1=dd[2];
           var monthArray =new Array("January", "February", "March", 
               "April", "May", "June", "July", "August",
               "September", "October", "November", "December");
            if(dd[1].substring(0,1)==0)
            mon_arr=dd[1].substring(1,2);
            else
            mon_arr=dd[1];
            //document.frmJournal_General_Cancel_Deft.txtCash_year.value=dd[2];
           // document.frmJournal_General_Cancel_Deft.txtCash_Month_hid.value=dd[1];
            //document.frmJournal_General_Cancel_Deft.txtCash_Month.value=monthArray[mon_arr-1];
        }
        else if(document.frmJournal_General_Cancel_Deft.txtCrea_date.value.length==0)
            document.frmJournal_General_Cancel_Deft.txtCrea_date.focus();
          
     }
</script>
  </head>
   <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body onload="call_clr();loadDate();foc()" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Cancellation of Rectification Journal System (Deft)</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmJournal_General_Cancel_Deft" id="frmJournal_General_Cancel_Deft" method="POST"
                  action="../../../../../Rectificational_Journal_Cancel_Deft?Command=Cancel"
                  onsubmit="return checkNull_cancel()">
      
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
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <!--<input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>-->
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice()">
                     <!-- <option value="0"> Select Account Unit </option>-->
                           <%
                      int unitid=0;
                      String unitname="";
                      try{
                       
                        if(oid==5000)
                        {
                            String getWing= "                              \n" +
                            " select                                       \n" + 
                            "    ACCOUNTING_UNIT_ID,                       \n" + 
                            "    ACCOUNTING_UNIT_NAME                      \n" + 
                            " from                                         \n" + 
                            "    FAS_MST_ACCT_UNITS                        \n" + 
                            " where                                        \n" + 
                            "    ACCOUNTING_UNIT_OFFICE_ID in              \n" + 
                            "    (                                         \n" + 
                            "      select                                  \n" + 
                            "          REDEPLOYED_OFFICE_ID                \n" + 
                            "      from                                    \n" + 
                            "          COM_OFFICE_REDEPLOYMENTS            \n" + 
                            "      where                                   \n" + 
                            "          ACCT_TRF_UNIT_ID in                 \n" + 
                            "             (                                \n" + 
                            "                select                        \n" + 
                            "                    ACCOUNTING_UNIT_ID        \n" + 
                            "                from                          \n" + 
                            "                    FAS_MST_ACCT_UNITS        \n" + 
                            "                where                         \n" + 
                            "                    ACCOUNTING_UNIT_OFFICE_ID  = ?                                                                               \n" + 
                            "                and OFFICE_WING_SINO = ( select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)  \n" +   
                            "             )                                \n" + 
                            "       union all                              \n" + 
                            "                                              \n" + 
                            "       select                                 \n" + 
                            "        CLOSED_OFFICE_ID  as red_closed_off   \n" +            
                            "       from                                   \n" +  
                            "          COM_OFFICE_CLOSURE                  \n" +  
                            "       where                                  \n" + 
                            "          ACCT_TRF_UNIT_ID in                 \n" + 
                            "             (                                \n" + 
                            "                select                        \n" + 
                            "                   ACCOUNTING_UNIT_ID         \n" + 
                            "                from                          \n" + 
                            "                    FAS_MST_ACCT_UNITS        \n" + 
                            "                 where                        \n" + 
                            "                     ACCOUNTING_UNIT_OFFICE_ID  = ?   \n" +                                                                              
                            "                 and OFFICE_WING_SINO = ( select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=? )   \n" + 
                            "             )                                                  \n" + 
                            "   ) ";
                          
                            ps=con.prepareStatement(getWing);
                            ps.setInt(1,oid);
                            ps.setInt(2,empid);
                            ps.setInt(3,oid);
                            ps.setInt(4,oid);
                            ps.setInt(5,empid);
                            ps.setInt(6,oid);
                            
                            rs=ps.executeQuery();
                          
                            while(rs.next())
                            {
                               out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                               unitid=rs.getInt("ACCOUNTING_UNIT_ID");                              
                            }                         
                              
                            ps.close();
                            rs.close();
                       }
                       else  
                       {
                           /* ps=con.prepareStatement("                      \n" +
                            "select                                        \n" + 
                            "    ACCOUNTING_UNIT_ID,                       \n" + 
                            "    ACCOUNTING_UNIT_NAME                      \n" + 
                            "from                                          \n" + 
                            "    FAS_MST_ACCT_UNITS                        \n" + 
                            "where                                         \n" + 
                            "    ACCOUNTING_UNIT_OFFICE_ID in              \n" + 
                            "    (                                         \n" + 
                            "      select                                  \n" + 
                            "          REDEPLOYED_OFFICE_ID as red_closed_off        \n" + 
                            "      from                                    \n" + 
                            "          COM_OFFICE_REDEPLOYMENTS            \n" + 
                            "      where                                   \n" + 
                            "          ACCT_TRF_UNIT_ID in                 \n" + 
                            "             (                                \n" + 
                            "                select                        \n" + 
                            "                    ACCOUNTING_UNIT_ID        \n" + 
                            "                from                          \n" + 
                            "                    FAS_MST_ACCT_UNITS        \n" + 
                            "                where                         \n" + 
                            "                    ACCOUNTING_UNIT_OFFICE_ID  = ?       \n" + 
                            "             )                                \n" + 
                            "       union all                              \n" + 
                            "                                              \n" + 
                            "       select                                 \n" + 
                            "        CLOSED_OFFICE_ID  as red_closed_off   \n" +            
                            "       from                                   \n" +  
                            "          COM_OFFICE_CLOSURE                  \n" +  
                            "       where                                  \n" + 
                            "          ACCT_TRF_UNIT_ID in                 \n" + 
                            "             (                                \n" + 
                            "                select                        \n" + 
                            "                   ACCOUNTING_UNIT_ID         \n" + 
                            "                from                          \n" + 
                            "                    FAS_MST_ACCT_UNITS        \n" + 
                            "                 where                        \n" + 
                            "                     ACCOUNTING_UNIT_OFFICE_ID  = ?   \n" +                                                                              
                            "             )                                \n" + 
                            "   )");*/
                            
                            ps=con.prepareStatement("SELECT OFFICE.ACCOUNTING_UNIT_ID, " +
													"ACC.ACCOUNTING_UNIT_NAME " +
													"FROM FAS_MST_ACCT_UNIT_OFFICES OFFICE, " +
													"FAS_MST_ACCT_UNITS acc " +
													"WHERE ACCOUNTING_FOR_OFFICE_ID IN " +
 													"(SELECT REDEPLOYED_OFFICE_ID AS red_closed_off " +
 													" FROM COM_OFFICE_REDEPLOYMENTS " +
 													" WHERE ACCT_TRF_UNIT_ID IN " +
   													" (SELECT ACCOUNTING_UNIT_ID " +
   													" FROM FAS_MST_ACCT_UNITS " +
   													" WHERE ACCOUNTING_UNIT_OFFICE_ID = ? " +
  													"  ) " +
 													" UNION ALL " +
 													" SELECT CLOSED_OFFICE_ID AS red_closed_off " +
  													"FROM COM_OFFICE_CLOSURE " +
 													" WHERE ACCT_TRF_UNIT_ID IN " +
  													"  (SELECT ACCOUNTING_UNIT_ID " +
   													" FROM FAS_MST_ACCT_UNITS " +
   													" WHERE ACCOUNTING_UNIT_OFFICE_ID = ? " +
   													" ) " +
 													" ) " +
													" AND OFFICE.CLOSED ='Y' " +
													" and OFFICE.ACCOUNTING_UNIT_ID=ACC.ACCOUNTING_UNIT_ID ");
                            
                            ps.setInt(1,oid);
                            ps.setInt(2,oid);
                            
                            rs=ps.executeQuery();
                            while(rs.next())
                            {                                  
                                out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                            }
                            ps.close();
                            rs.close();
                       }
                      }
                      catch(Exception e)
                      {
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
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2" onchange="byUnitAndOfficeChange();">
                      
                           
                 <%
                   System.out.println("here");
                   System.out.println(oid+"  " +oname);
                try
                {
                    if(oid==5000)
                    {                     
                 String sql=" select                                 \n" + 
                "                REDEPLOYED_OFFICE_ID,  \n" + 
                "                ( select office_short_name from com_mst_offices where office_id = REDEPLOYED_OFFICE_ID) as office_desc     \n" + 
                "            from                                   \n" + 
                "                COM_OFFICE_REDEPLOYMENTS           \n" + 
                "            where                                  \n" + 
                "                ACCT_TRF_UNIT_ID in                \n" + 
                "                   (                               \n" + 
                "                      select                       \n" + 
                "                          ACCOUNTING_UNIT_ID       \n" + 
                "                      from                         \n" + 
                "                          FAS_MST_ACCT_UNITS       \n" + 
                "                      where                        \n" + 
                "                          ACCOUNTING_UNIT_OFFICE_ID = ?   \n" + 
                "                      and OFFICE_WING_SINO = ( select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id= ? and office_id = ? ) \n" + 
                "                   )\n    ";
                 
                        ps=con.prepareStatement(sql);                       
                        ps.setInt(1,oid);
                        ps.setInt(2,empid);
                        ps.setInt(3,oid);
                        rs=ps.executeQuery();                        
                        while(rs.next())
                        {   
                           out.println("<option value="+rs.getInt("REDEPLOYED_OFFICE_ID")+">"+rs.getString("office_desc")+"</option>");
                        }
                    }
                    else
                    {
                    
                     String sql=" select                                 \n" + 
                "                REDEPLOYED_OFFICE_ID,  \n" + 
                "                ( select office_short_name from com_mst_offices where office_id = REDEPLOYED_OFFICE_ID) as office_desc     \n" + 
                "            from                                   \n" + 
                "                COM_OFFICE_REDEPLOYMENTS           \n" + 
                "            where                                  \n" + 
                "                ACCT_TRF_UNIT_ID in                \n" + 
                "                   (                               \n" + 
                "                      select                       \n" + 
                "                          ACCOUNTING_UNIT_ID       \n" + 
                "                      from                         \n" + 
                "                          FAS_MST_ACCT_UNITS       \n" + 
                "                      where                        \n" + 
                "                          ACCOUNTING_UNIT_OFFICE_ID = ?   \n" +                
                "                   )                                      \n" ;
                 
                        ps=con.prepareStatement(sql);
                        ps.setInt(1,oid);
                        rs=ps.executeQuery();                        
                        while(rs.next())
                        {   
                           out.println("<option value="+rs.getInt("REDEPLOYED_OFFICE_ID")+">"+rs.getString("office_desc")+"</option>");
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
                           maxlength="10" size="11" 
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmJournal_General_Cancel_Deft.txtCrea_date,1);"
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
                  <select size="1" name="txtJournalVou_No" id="txtJournalVou_No"
                            tabindex="4" onchange="doFunction('load_Voucher_Details','null');">
                      <option value="">--Select Voucher Number--</option>
                    </select>
                  </div>
                </td>
              </tr>
           <tr class="table">
                <td>
                  <div align="left">
                    Journal Type
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="cmbMas_SL_type" tabindex="5"  style="background-color: #ececec"  readonly="readonly"  
                                id="cmbMas_SL_type"  size="50"/>
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
                    <input type="text" name="txtCheque_NO" maxlength="10" tabindex="6" onkeypress="return numbersonly(event)"
                           id="txtCheque_NO" size="11"/>
                     <input type="text" name="txtCheque_date" id="txtCheque_date"  tabindex="7" 
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmJournal_General_Cancel_Deft.txtCheque_date);"
                         alt="Show Calendar"></img>
                  </div>
                </td>
              </tr>
              </table>
              </div>
              
              <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr class="table">
                <td width="30%">
                  <div align="left">Remarks</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="50" tabindex="8"  onkeypress="return check_leng(this.value);"
                              rows="4"></textarea>
                  </div>
                </td>
              </tr>
           
             <!-- <tr class="table">
                <td>
                  <div align="left">
                    Total Amount (Rs. P.) 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAmount"  onkeypress="return limit_amt(this,event);"  onblur="valid_amt(this);"
                           id="txtAmount" maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>-->
                <!--<tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong>Modification Details</strong>
                  </div>
                </td>
              </tr>
                  <tr class="table">
                <td>
                  <div align="left">
                   Authorized By
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="hidden" name="txtAuth_By"
                           id="txtAuth_By" size="6"/>
                    <input type="text" name="Auth_By"  style="background-color: #ececec" readonly="readonly"
                           id="Auth_By" size="50"/>
                    <img src="../../../../../images/c-lovi.gif" width="20"
                     height="20" alt="empList" onclick="servicepopup();"></img>
                           
                  </div>
                </td>
              </tr>-->
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
                    <!-- The SL details input taken out -->
                  </div>
                  <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      
                      <tr class="table">
                        
                        <th >A/c Head Code</th>
                        <th >CR/DR</th>
                        <th>Sub Ledger Type</th>
                        <th >Sub Ledger Code</th>
                          <th>Bill No.</th>
                        <th>Bill Date</th>
                        <th>Amount</th>
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