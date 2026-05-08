<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>Freeze Trial Balance PB</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
     <script language="javascript" type="text/javascript" src="../scripts/FreezeGL_PB.js"></script>
     <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
     <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
     <script type="text/javascript" src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
     <script type="text/javascript"  src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
     <script type="text/javascript" language="javascript">
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900)
         {
         year += 1900;
         }
     //  int prev=parseInt(year);
     //  alert(year-1);
        document.frmpbfreeze.txtCB_Year.value=(year-1)+"-"+year;
         document.frmpbfreeze.txtCB_Month.value=month;
        
         }
    </script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
                
               
    </script>
    
      </head>
  <body class="table" onload="loadyear_month(),LoadAccountingUnitID('LIST_ALL_UNITS')" >
  <form name="frmpbfreeze" method="POST" action="../../../../../FreezeGL_PB_servlet" onsubmit="return checknull()">
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

            ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  int  cmbAcc_UnitCode=0,cmbOffice_code=0;

            
  %>
  
  
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
              <strong>Freeze GL&nbsp;PB</strong>
            </div>
        </td>
      </tr>
    </table>
     <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr class="table">
                                    <td width="257">Accounting Unit Code <span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"></select></td>             
            </tr>
      
                <tr align="left">
          <td class="table">
          <div align="left">
              Cash Book Year &amp; Month
              </div>
            </td>
            <td>
             <div align="left">
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3" readonly  maxlength="8" size="10" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
                      <!--    <option value="1">January</option>
                           <option value="2">February</option> -->  
                           <option value="3">March</option>
                        <!--   <option value="4">April</option>
                           <option value="5">May</option>
                           <option value="6">June</option>
                           <option value="7">July</option>
                           <option value="8">August</option>
                           <option value="9">September</option>
                           <option value="10">October</option>
                           <option value="11">November</option>
                           <option value="12">December</option>  -->
          
          </select>
          <input type="BUTTON" value="GO" name="ByMonth" id="ByMonth" tabindex="8" onclick="CheckStatus();"/>
           </div>
          </td>
        </tr>
        
       
</table>
</div>


        <br>
     <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            
            <th>
              CashBook Year
            </th>
            <th>
              CashBook Month
            </th>
             <th>
              PB Generated
            </th>
            <th>
              PB Generated Date
            </th>
            
          </tr>
          <tbody id="tbody" class="table">
          
         
          </tbody>
        </table>      



    <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
      <tr class="tdH">
      <td>
          <div align="center">
          <input type=submit value=Submit >
         <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="closeWindow()">
      </div>
      </td>
      <!--<td>
                  <input type="button" name="cmdList" value="Verify" id="cmdList" onclick="ListAll()"/>
                  </td>-->
      </tr>
      
      </table>
      </form>
  </body>
</html>