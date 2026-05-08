<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <title>Self Balance Report</title>
    
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
            
            
    <script language="javascript" type="text/javascript"
            src="../scripts/Self_Balance_A26.js"></script>
    
  
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
  
    
    <script type="text/javascript"
            src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
            
   <!--         
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script>
    -->
    
   <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/UnitwiseOffice.js"></script> 
   
   <!--  
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Load_Accounting_office.js"></script>
    -->
    
    <script type="text/javascript" language="javascript">
     function loadyear_month() 
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
         document.frmSubLedgerReport.txtCB_Year.value=year;
         document.frmSubLedgerReport.txtCB_Month.value=month;
        
         /** Load To Cash Book Month and To Cash Book Year during Form Load */
         document.frmSubLedgerReport.txtCB_Year_to.value=year;
         document.frmSubLedgerReport.txtCB_Month_to.value=month;
        
        
         }
     function ChooseReptype(id)
     {
        
         var dispsupnochosen1=document.getElementById("dispsupno1");
         var dispsupnochosen2=document.getElementById("dispsupno2");

         
         //var reg_date_id=document.getElementById("reg_date_id");
         
         if(document.frmSubLedgerReport.reporttype[0].checked==true)
         {
        	 
	        	 //reg_id.style.display="block";
	        	 //reg_date_id.style.display="block";
                 dispsupnochosen1.style.display="none";
                 dispsupnochosen2.style.display="none";

                 
         }
         else
         {
	        	// reg_id.style.display="none";
	        	// reg_date_id.style.display="none";
                 dispsupnochosen1.style.display="block";
                 dispsupnochosen2.style.display="block";
                 alert("Enter the Supplement Number");
         }
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
  
  <body class="table" onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS')">
  <form name="frmSubLedgerReport" method="POST" action="../../../../../Self_Balance_A26.kv"
                                                      onsubmit="return checknull()">
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
  %>
     
    
      <table cellspacing="2" cellpadding="3" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Self Balance Report</strong>
            </div>
          </td>
        </tr>
      </table>
      <div align="center">
        <table cellspacing="1" cellpadding="2" border="0" width="100%">
          
          <tr class="table">
            <td>
              <div align="left">
                Accounting Unit Code 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                  <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1"  onchange="common_LoadOffice(this.value);">
                   <option value="">select </option>
                    
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
          
          <tr align="left">
            <td class="table">
              <div align="left">Cash Book Year &amp; Month  <font color="#ff2121">*</font></div>
            </td>
            <td>
            <input type="radio" name="month_year" id="month_year" value="particular_cb" onclick="cb_month_year(this.value)" >one Month 
          <input type="radio" name="month_year" id="month_year" value="more_cb" onclick="cb_month_year(this.value)"> More than one Month 
          
          <br><br> 
            
              <div id="particular" name="particular" style="display:none">
               <!-- From -->
                <input type="text" name="txtCB_Year" id="txtCB_Year"
                       tabindex="3" maxlength="4" size="5"
                       onkeypress="return numbersonly(event)"></input>
                 
                <select name="txtCB_Month" id="txtCB_Month" tabindex="4" onchange="testRegular();">
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
               <div id="more" style="display:none">
                From   
          <input type="text" name="txtCB_Year_from" id="txtCB_Year_from" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month_from"  id="txtCB_Month_from" tabindex="4" >
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
               To 
                <input type="text" name="txtCB_Year_to" id="txtCB_Year_to"
                       tabindex="3" maxlength="4" size="5"
                       onkeypress="return numbersonly(event)"></input>
                 
                <select name="txtCB_Month_to" id="txtCB_Month_to" tabindex="4" onchange="testRegular1();">
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
              
              </div>
            </td>
          </tr>
          
               <tr class="table">
                 <td>
                  <div align="left">Sub-Ledger Type  <font color="#ff2121">*</font> </div>
                 </td>
                 <td>
                  <div align="left">
                    <select  name="cmbSL_type" id="cmbSL_type" tabindex="6"
                            onchange="doFunction('Load_SL_Code',this.value);"  >
                      <option value="0">--Select Type--</option>
                      <%
                        try
                        {
                        ps=con.prepareStatement("select SUB_LEDGER_TYPE_CODE,SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES order by SUB_LEDGER_TYPE_DESC");
                        rs=ps.executeQuery();
                            while(rs.next())
                            {
                            out.println("<option value="+rs.getString("SUB_LEDGER_TYPE_CODE")+">"+rs.getString("SUB_LEDGER_TYPE_DESC")+"</option>");
                            }
                        }
                        catch(Exception e)
                        {
                        System.out.println("Exception in Reason combo..."+e);
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
                      <div align="left">Type<font color="#ff2121">*</font> </div>
                    </td>
                    <td>
                    <input type="radio" name="singleSlCode" id="singleSlCode" value="single_code" onclick="check_code(this.value)" >Single
                    <input type="radio" name="singleSlCode" id="singleSlCode" value="all_code" onclick="check_code(this.value)"> All 
                  
                    </td>
                </tr>
              
               <tr align="left">
          <td class="table">
            <div align="left" id="labelDivsupp">Report Type </div>
          </td>
          <td>
            <div align="left" id="textDivregular">
            
                <input type="radio" name="reporttype" id="reporttype" value="1" checked onclick="ChooseReptype(this.value);"/> Regular &nbsp;
                </div>
                <div align="left" id="textDivsupp">
                <input type="radio" name="reporttype" id="reporttype" value="3" onclick="ChooseReptype(this.value);"/> Supplement 
                   
            </div>
          </td>
        </tr>   
              <tr align="left">
         
            <td class="table">
              <div align="left" id="dispsupno1" name="dispsupno1" style="display:none">
                Supplement Number 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div id="dispsupno2" name="dispsupno2" style="display:none">
                <input type="text" name="supno" id="supno" size=2 >     
              </div>
              
            </td>
          </tr> 
              <tr class="table">
              
                <td>
                  <div align="left" id="code_div1" style="display:none">Sub-Ledger Code  <font color="#ff2121">*</font> </div>
                </td>
                <td>
                    <table align="left">
                     <tr align="left">
                     <td>
                         <div align="left" id="code_div2" style="display:none">
                        <select size="1" name="cmbSL_Code" id="cmbSL_Code" >
                                
                          <option value="">--Select Code--</option>
                        </select>
                  </div>
                      </td>
                      <td>
                          <div align="left" id="offlist_div_master"  style="display:none">
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" onclick="jobpopup_master();"></img>
                            <input type="text" name="txtOfficeID_mas" id="txtOfficeID_mas" maxlength="4" size="5"  onblur="mas_office(this.value);" />
                          </div>
                           <div align="left" id="emplist_div_master"  style="display:none">
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="employee_popup_master();"></img>
                            <input type="text" name="txtEmpID_mas" id="txtEmpID_mas" maxlength="5" size="5"  onblur="mas_employee(this.value);" />
                          </div>
                       </td>
                    
                    </tr>
                   </table>
                </td>
              </tr>
              
        </table>
      </div>
      
      <table align="center" cellspacing="3" cellpadding="2" border="0"
             width="100%">             
        <tr>
          <td align="left">Report Option:</td>
          <td>
            <div align="left">
               <input type="radio" name="txtoption" id="txtoption" value="PDF" checked="checked"></input>PDF
               <input type="radio" name="txtoption" id="txtoption" value="EXCEL"></input>Excel
               <input type="radio" name="txtoption" id="txtoption" value="HTML"></input>HTML
            </div>
          </td>
        </tr>
      </table>
      
      <table align="center" cellspacing="3" cellpadding="2" border="0"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="submit" value="Submit"></input>
               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
          </td>
        </tr>
      </table>
      
      
    </form>
  </body>
</html>