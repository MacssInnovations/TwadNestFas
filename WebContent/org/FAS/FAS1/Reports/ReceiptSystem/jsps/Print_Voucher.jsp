<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <link href="../../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="../scripts/Print_Voucher.js"></script>
    <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script>     
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>


    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
 function clk(val){
	 document.frmReport.hid.value =val;
 }
  function loadDate()
         {
                 var today= new Date(); 
                 var day=today.getDate();
                 var month=today.getMonth();
                 month=month+1;
                 
                 var year=today.getYear();
                 if(year < 1900) year += 1900;
                 var monthArray =new Array("January", "February", "March", 
                           "April", "May", "June", "July", "August",
                           "September", "October", "November", "December");
                document.frmReport.txtCB_Year.value=year;
                document.frmReport.txtCB_Month.value=month;
                
                
     }   
     
     
     
                
  function submitRep()
        {
        if(nullcheck())
        {
      //  alert("inside");
        if(document.frmReport.selection[0].checked==true)
            {        
           // alert("hello")
            if(nullvoucher())
            postrep();
            }
          if(document.frmReport.selection[1].checked==true)
            {
            //alert("comes here");
            if(nulldate())
             {
             //alert("comes here1");
             postrep();
             }
            }
          if(document.frmReport.selection[2].checked==true)
            {
            //if(nullmonth())
             postrep();
            }
         }
         return false;
        }
        
        
function postrep()
{
document.getElementById("txtCB_Month").disabled=false;
document.getElementById("txtCB_Monthwise").disabled=false;
var url="../../../../../../PrintVoucherServ";
                document.getElementById("frmReport").action=url;
                document.getElementById("frmReport").method="post";
                document.getElementById("frmReport").submit();
}
    </script>
    <title>Print Voucher Report</title>
  </head>
  <body class="table" onload="loadDate();LoadAccountingUnitID('FOR_LIST_1');">
  
  
  
  
  <form  name="frmReport" id="frmReport"   > 
   <input type="hidden" id="hid" value="NO" name="hid">
          <table width="100%" >
        <tr>
            <td class="tdH"><center><b>Print Voucher Report</b></center></td>
        </tr>
          <tr>
            <td>
                <table border="4" cellspacing="0" cellpadding="0" width="100%">
         
         
                  <tr class="table">
                <td>
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>   
                </td>
                <td colspan="3">
                  <div align="left">
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                            tabindex="1" onchange="common_LoadOffice(this.value);"></select>
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
                <td colspan="3">
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code"
                            tabindex="2"></select>
                  </div>
                </td>
              </tr>        
                    
                    <tr class="table">
                        <td>
                            Document Type:
                        </td>
                        <td colspan="2">
                            <select name="cmbdoctype" id="cmbdoctype" onchange="loadType()">
                            <option value="0">--Select Document Type--</option>
                            <option value="REC">Receipt</option>
                            <option value="PAY">Payment</option>
                            <option value="JOU">Journal</option>
                            <option value="FR">Fund Receipt</option>
                            <option value="FT">Fund Transfer</option>
                            </select>
                        </td>
                    </tr>
                    
                    <tr class="table">
                        <td>Receipt Type:</td><td colspan="2"><select name="cbtype" id="cbtype" onchange="loadmonth();loadDocNo();">
                        <option value="0">--Select Receipt Type--</option>
                        <!--<option value="CashReceipt">Cash Receipt</option>
                        <option value="BankReceipt">Bank Receipt</option>-->
                        </select></td>
                    </tr>
                    
                      <tr class="table">
                        <td>Include SubLedger Details :</td><td colspan="2">
                         <input type="radio" name="selection_sl" id=selection_sl value="YES"   onclick="clk(this.value)" checked>YES</input>
                        <input type="radio" name="selection_sl" id=selection_sl value="NO" checked="checked" onclick="clk(this.value)"> NO</input>
                        
                        </td>
                    </tr>
                    
                    
                    
                    
                    <tr class="table">
                        <td>Selection Type</td>
                        <td >
                        <input type="radio" name="selection" id=selection value="voucherwise"   onclick="return enable_cheque(this.value)" checked>By Voucher Wise</input>
                        <input type="radio" name="selection" id=selection value="datewise" onclick="return enable_cheque(this.value)"> By Date</input>
                            <input type="radio" name="selection"  id=selection value="monthwise" onclick="return enable_cheque(this.value)">By Cash Book Month</input>
                    </td>   
                    </tr>
                    </table>
                     <div id="VW" style="display:block">
                     <table cellspacing="1" cellpadding="2" border="1" width="100%">
                      
                    <tr class="table">
                        <td>Cash Book Year & Month</td>
                        
                         <td colspan="2"><input name="txtCB_Year" id="txtCB_Year" size="4" value="2006" onchange="loadDocNo()">  </input>
                        
                        <select name="txtCB_Month" id="txtCB_Month" onchange="loadDocNo()" >
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
                        <!--input type="submit" id="go" name="go" value="Go"/--> 
                        </td>
                        
                    </tr>
                    
                    
                    <%
                        String rec_type=request.getParameter("cmbdoctype");
                        System.out.println("Receipt type="+rec_type);
                       /* ps=con.prepareStatement("select receipt_no from fas_Receipt_Transaction where receipt_no=(select receipt_no from fas_receipt_master) and receipt_type=? and cashbook_month=? and cashbook_year=?" );
                        ps.setString(1,rec_type);
                        ps.setInt(2,CBMonth);
                        ps.setInt(3,CBYear);*/
                    %>
                    
                     
                        <tr class="table">
                        <td>From&nbsp;&nbsp;&nbsp;
                        <select name="fromid" id="fromid">
                        <option value="0">--Select any doc no--</option>
                        
                        </select></td>
                        
                        <td>To&nbsp;&nbsp;&nbsp;<select name="toid" id="toid">
                        <option value="0">--Select any doc no--</option>
                        </select></td>
                    </tr>
                    
                    </table>
                     </div>
                     <div style="display:none" id="MW">
                    <table cellspacing="1" cellpadding="2" border="1" width="100%">
                       
                        <tr class="table">
                        <td>Cash Book Year & month</td>
                        
                         <td colspan="2"><input name="txtCB_Yearwise" id="txtCB_Yearwise" size="4" value="2006">  </input>
                        
                        <select name="txtCB_Monthwise" id="txtCB_Monthwise">
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
                        <!--input type="submit" id="go" name="go" value="Go"/--> 
                        </td>
                        
                    </tr>
                        
                    </table>
                    </div>
                     <div style="display:none" id="DW">
                    <table cellspacing="1" cellpadding="2" border="1" width="100%">
                       
                            <tr class="table">
                                <td>
                            From Date:<font color="#ff2121">*</font>
                        
                        
                            <input type=text name=txtfromdate id=txtfromdate onkeypress="return  calins(event,this)" onblur="return checkdt(this);" onFocus="javascript:vDateType='3'" maxlength=10>
                            <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmReport.txtfromdate);" alt="Show Calendar"
                                 height="24" width="19"></img>
                         </td>
                           
                        <td>
                            To Date:<font color="#ff2121">*</font>
                        
                            <input type=text name=txttodate id=txttodate onkeypress="return  calins(event,this)" onblur="return checkdt(this);" onFocus="javascript:vDateType='3'" maxlength=10>
                            <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmReport.txttodate);" alt="Show Calendar" ></img>
                         </td>
                            </tr>
                        
                    </table>
                    </div>
                   <table cellspacing="1" cellpadding="2" border="1" width="100%">
                    <tr class="table">
                        <td>
                            Report Option:
                        </td>
                        <td colspan="2">
                            <input type=radio name=txtoption id=txtoption value="PDF" checked>PDF
                            <input type=radio name=txtoption id=txtoption value="EXCEL">Excel
                            <input type=radio name=txtoption id=txtoption value="HTML">HTML
                        </td>
                        
                    </tr>
                    <tr class="table">
                        <td colspan=3 class="tdH" align="center">
                        <input type="button" value=Submit onclick="submitRep()"   >
                        <input type=reset value=Clear>
                        <input type=button value=Exit onclick="closeWindow()">
                        </td>
                    </tr>
                    
                
                </table>
           
  
  </td></tr></table>
  </form>
  </body>
</html>