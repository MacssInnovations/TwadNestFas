<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Schedule (MonthWise) SubHead Report</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script language="javascript" type="text/javascript" src="../scripts/RegisterChequeReport.js"></script>
  	<link href="../../../../../css/Sample3.css" rel="stylesheet"    media="screen"/>
     	<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen" />   
    <script type="text/javascript" src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script> 
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
  <script type="text/javascript" src="../scripts/Schedule_report.js"></script>
  
   <script type="text/javascript">
   function loadyear_month()
   {
  
    var today= new Date(); 
    var day=today.getDate();
    var month=today.getMonth();
    month=month+1;
    var year=today.getYear();
    if(year < 1900) year += 1900;
           
     document.frmSch_month_rep.txtCB_Year_from.value=year;
  // document.frmSch_month_rep.txtCB_Year_To.value=year;
   
 }
   function month_Chk(val_mn)
   {
	 
	   var today= new Date(); 
	   var day=today.getDate();
	  var month=today.getMonth();
	   month=month+1;
	   var year=today.getYear();
	   if(year < 1900) year += 1900;
	  var txtCB_Month_to=document.getElementById("txtCB_Month_to").value;
	   var txtCB_Year_to=document.getElementById("txtCB_Year_to").value;
  
	 if(txtCB_Year_to==year){
	   if(txtCB_Month_to>month)
  {
		   document.getElementById("txtCB_Month_to").value=month;
	  }
	   else if(txtCB_Year_to>year){
		  document.getElementById("txtCB_Year_to").value=year;
	   }
   }
  
   }
   </script>
    
    </head>
        <%
	     String s = request.getContextPath();
        %>
   
  <body class="table" onload="LoadAccountingUnitID('FOR_LIST_1');loadyear_month();" >
  <form id="frmSch_month_rep" name="frmSch_month_rep" method="GET" action="../../../../../Schedule_Month_Sub_Reports" onsubmit="return checknull()">
       <input type="hidden" id="yes_hid" name="yes_hid" value="" />     
      
  <%
  
      Connection con=null;
      ResultSet rs=null,rs2=null;
      PreparedStatement ps=null,ps2=null;
      ResultSet results=null;
      ResultSet results1=null;
      ResultSet results2=null;
       Statement statement=null;
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
   <input type="hidden" id="hid" name="hid" value="" />
     
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <strong>Schedule(MonthWise) SubHead - Report</strong>
          </div>
        </td>
      </tr>
    </table>
   
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
      
          
     
         <tr align="left">
           			<td class="table">
              			<div align="left" id="more_id" > 
                 			Cash Book Year &amp; Month <font color="#ff2121">*</font>
              			</div>
           			</td>
          			<td>        				       
		          <div id="more" >
          		From   
          		<input type="text" name="txtCB_Year_from" id="txtCB_Year_from" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
          		<select name="txtCB_Month_from"  id="txtCB_Month_from" tabindex="4" onchange="chkCashyr_mn();"  >
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
      <td><div align="left"> Major Head Code <font color="#ff2121">*</font></div></td>
	  <td><div align="left">
          <select name="cmbBudgetGroupMajor" id="cmbBudgetGroupMajor" onChange="getMinorBudgetHeadDesc('<%=s%>');">
            <option value="">--- Select ---</option>
             <%
            String qry="";
            try
            {
            	qry="select MAJOR_HEAD_CODE,MAJOR_HEAD_DESC from COM_MST_MAJOR_HEADS where MAJOR_HEAD_CODE!='O' order by major_head_code";
            ps=con.prepareStatement(qry);
            rs=ps.executeQuery();
            while(rs.next()){
            	 out.println("<option value="+rs.getString("MAJOR_HEAD_CODE")+">"+rs.getString("MAJOR_HEAD_DESC")+"</option>");
            	
            }
            ps.close();
            rs.close();
            }catch(Exception e){
            	out.println(e);
            }
            
            
            
            %>
          </select>
      </div></td>
    </tr>
         <tr  align="left">
            <td>
               <div align="left"> Minor Group<font color="#ff2121">*</font></div>
            </td>
            <td>
          
              <select size="1" name="cmbMinor_Head" id="cmbMinor_Head" tabindex="1" >
              <option value="">--Select--</option>
                       </select>
           
            </td>
            </tr>
            <tr  align="left">
            <td>
               <div align="left"> Head Code Visible<font color="#ff2121">*</font></div>
            </td>
            <td>
            <input type="radio" id="rad_head" name="rad_head" value="" checked="checked" onclick="fun_yes();"/> Yes
            <input type="radio" id="rad_head" name="rad_head" value="" onclick="fun_No();"/> No
           </td></tr>
           <tr  align="left">
            <td>
               <div align="left"> Report Option<font color="#ff2121">*</font></div>
            </td>
            <td>
                <input type=radio name=txtoption id=txtoption value="PDF" checked>PDF
                <input type=radio name=txtoption id=txtoption value="EXCEL">Excel
                <input type=radio name=txtoption id=txtoption value="HTML">HTML
            </td>            
        </tr>
        
          
      <tr class="tdH">
      <td colspan="2">
          <div align="center">
          <input type=submit id="sub_btn" name="sub_btn" value=Submit >
       
      </div>
      </td>
      </tr>
  
        
        
</table>
 
      </form>
  </body>
</html>