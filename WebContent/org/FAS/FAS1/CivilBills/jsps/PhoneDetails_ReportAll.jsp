<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache">
    <title>Phone Details Report</title>
    
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
     
     <script language="javascript" type="text/javascript" src="../scripts/Journal_forCashBookMonth.js"></script>
    
      <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
     <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
     <script type="text/javascript" src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script> 
     <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>

    <script type="text/javascript" language="javascript">
     function btncancel()  
      {
        self.close();
      }
     function loadyear_month()
         {
      
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
             month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmPhoneDetails.txtCB_Year.value=year;
        document.frmPhoneDetails.txtCB_Month.value=month;
        
         }
     </script>
    </head>
  <body class="table"  >
  <form name="frmPhoneDetails" method="POST" action="../../../../../phone_master_servlet" onsubmit="return check();" >
   <input type="hidden" id="Command" name="Command" value="Report">
   <input type="hidden" id="type" name="type" value="All">
    <input type="hidden" id="cmbAcc_UnitCode" name="cmbAcc_UnitCode" value="0">
   <input type="hidden" id="cmbOffice_code" name="cmbOffice_code" value="0">
   
   
    
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <strong>Phone Details Report (For the Cash Book Month)</strong>
          </div>
        </td>
      </tr>
    </table>
     <div align="center">
           <table cellspacing="1" cellpadding="2" border="1" width="100%">
            
            
         <tr  align="left">
            <td>
                Report Option:
            </td>
            <td>
                <input type=radio name=txtoption id=txtoption value="PDF" checked>PDF
                <input type=radio name=txtoption id=txtoption value="EXCEL">Excel
                <input type=radio name=txtoption id=txtoption value="HTML">HTML
            </td>
        </tr>
   </table>  
  </div>
  
     <br>
     
     <table cellspacing="1" cellpadding="1" border="0" width="100%">
     </table>
     
     <br>
     
     <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
      <tr class="tdH">
      <td>
         <div align="center">
         <input type=submit value=Submit >
         <input type="button" value="EXIT" onclick="window.close();">
      </div>
      </td>
      </tr>
     </table>
    
      </form>
  </body>
</html>