<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
   <head>
      <meta http-equiv="Content-Type"
            content="text/html; charset=windows-1252"/>
      <meta http-equiv="cache-control" content="no-cache"></meta>
      <title>Freeze Supplement Trial Balance</title>
      <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
            media="screen"/>
      <link href="../../../../../css/Sample3.css" rel="stylesheet"
            media="screen"/>
      <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
      <script type="text/javascript"
              src="../../../../../../org/Library/scripts/checkDate.js"></script>
      <script language="javascript" type="text/javascript"
              src="../scripts/FreezeTB_SJV.js"></script>
      <script type="text/javascript"
              src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
    
      <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
      <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
  
      <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Supplement_Number_Check.js"></script>

       
      <script type="text/javascript" language="javascript">
      function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmTrialBalance.txtCB_Year.value=year
        document.frmTrialBalance.txtCB_Month.value=month;
        
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
   <body class="table" onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS_ONLY');Suppl_Number_Check();">
   
   <form id="frmTrialBalance"
                                                       name="frmTrialBalance"
                                                       method="POST" action=""
                                                       onsubmit="return confirmation()">
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
  int  cmbAcc_UnitCode=0,cmbOffice_code=0;

            
  %>
    <% 
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();
    //int empid=10099;
    int  oid=0;
    String oname="";
    
    
    String FAS_SU="";
   
    if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
         FAS_SU="YES";
    else
         FAS_SU="NO";
         
         
   
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
         <table cellspacing="2" cellpadding="3" width="100%">
            <tr class="tdH">
               <td colspan="2">
                  <div align="center">
                     <strong>Freeze Supplement Trial Balance</strong>
                  </div>
               </td>
            </tr>
         </table>
         <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
               <tr class="table">
                  <td>
                     <div align="left">
                        Accounting Unit Code 
                        <font color="#ff2121">*</font>
                     </div>
                  </td>
                  <td>
                     <div align="left">
                        <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1">
                       
                           
                        </select>
                     </div>
                  </td>
               </tr>
               
               <tr align="left">
                  <td class="table">
                     <div align="left">Cash Book Year &amp; Month</div>
                  </td>
                  <td>
                     <div align="left">
                        <input type="text" name="txtCB_Year" id="txtCB_Year"
                               tabindex="3" maxlength="4" size="5"
                               onkeypress="return numbersonly(event)" onblur="Suppl_Number_Check();" ></input>
                         
                        <select name="txtCB_Month" id="txtCB_Month"
                                tabindex="4"  onchange="Suppl_Number_Check();">
                           <option value="3">March</option>                          
                        </select>
                     </div>
                  </td>
               </tr>
               
            <tr align="left">
            <td class="table">
            <div align="left">Supplement Number</div>
            </td>
            <td>
              <div align="left">
              
              <!--
                <input type="text" name="txtsupplement_no" id="txtsupplement_no"
                       tabindex="3" maxlength="4" size="5"
                       onkeypress="return numbersonly(event)"></input>                 
                -->
                <select name="txtsupplement_no" id="txtsupplement_no" >
                   <option value="" >-- Select Suppl No. -- </option> 
                </select>       
                
                    <input type="button" name="gobtn" id="gobtn" value="Go" onclick="callGrid();"></input>  
                    <marquee><font color="#ff2121"><font size='2'>Click <b><font color="#0000FF">Go </font> </b>Button to Enable Submit </font> </font></marquee> 
                </div>
            </td>
          </tr>                   
               
           </table>
         </div>
         
         <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
             <div id="grid_one" style="display:block">
            <table id="mytable1" cellspacing="3" cellpadding="2"
                   border="0" width="100%">
              <tr class="table">
              	<th>Description</th>
                <th>Accounting_unit_id</th>
                <th>Year</th>
                <th>Month</th>
                <th>Debit</th>
                <th>Credit</th>
                <th>Difference</th>
                                      
              </tr>
              <tbody id="grid_body1" class="table" align="left" >
              </tbody>
            </table>
          </div>
            
          </tr>
         
        </table>
         
         
         
         <div align="left" style="display:none">Trial Balance Status</div>
         <div align="left" style="display:none">
            <input type="radio" id="radTB_status" name="radTB_status" value="Y"
                   checked="checked"></input>
             Yes 
            <input type="radio" id="radTB_status" name="radTB_status" value="N"></input>
             No
         </div>
         <table align="center" cellspacing="3" cellpadding="2" border="1"
                width="100%">
            <tr class="tdH">
               <td>
                  <div align="center">
                     <input type="submit" value="Submit"></input>
                      
                     <input type="button" id="cmdcancel" name="cancel"
                            value="EXIT" onclick="closeWindow()"></input>
                  </div>
               </td>
            </tr>
         </table>
      </form></body>
</html>