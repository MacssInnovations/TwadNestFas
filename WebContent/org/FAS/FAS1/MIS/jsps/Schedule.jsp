<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Schedule Report</title>
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
  <script type="text/javascript" src="../scripts/fas_Rep_Schedule.js"></script>
  <script type="text/javascript" src="../scripts/Fas_ConsolB_Details.js"></script>
  
   <script type="text/javascript">
   function loadyear_month()
   {
  
    var today= new Date(); 
    var day=today.getDate();
    var month=today.getMonth();
    month=month+1;
    var year=today.getYear();
    if(year < 1900) year += 1900;
           
     document.frmSchedule_Det.txtCB_Year_From.value=year;
   document.frmSchedule_Det.txtCB_Year_To.value=year;
   
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
    
   <%
String userid = (String) session.getAttribute("UserId");
System.out.println("User Id is:" + userid);
UserProfile up=null;
up=(UserProfile)session.getAttribute("UserProfile");
System.out.println("User name is:" + up.getEmployeeName());
%>
<%
  
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
    
    
    
  <body class="table" onload="LoadAccountingUnitID('FOR_LIST_1');loadyear_month();" >
  <form id="frmSchedule_Det" name="frmSchedule_Det" method="GET" action="../../../../../Schedule_Reports" onsubmit="return checknull()">
       <input type="hidden" id="hid_text" name="hid_text" value="Full" />     
       <input type="hidden" id="hid_cmd" name="hid_cmd" value="supp" />             
  <%
  
     
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
   <input type="hidden" id="hid" name="hid" value="" />
     <input type="hidden" id="yes_hid" name="yes_hid" value="" />
     
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <strong>Schedule - Report</strong>
          </div>
        </td>
      </tr>
    </table>
   
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
      
            <tr align="left">
            <td>
           <div align="left"> Full Financial Year<font color="#ff2121">*</font></div>
            </td>
            <td>
            <input type="radio" id="rad_fin" name="rad_fin_year" checked="checked" onclick="Chk_Mnthwise();"/> Yes
             <input type="radio" id="rad_fin" name="rad_fin_year" onclick="Chk_finyr();"/> No
            </td>
            </tr>
         <tr  align="left">
            <td>
             <div id="full_fin" style="display: block;">Financial Year<font color="#ff2121">*</font></div> 
            </td>
            <td>
             <div id="full_fin_Cmb" style="display: block;">
            <select id="fin_year" name="fin_year" >
            <option value="">--Select--</option>
           
               						<%
					                        st=con.createStatement();
					                        rs=st.executeQuery("select financial_year from cash_book_control order by financial_year");
					                        while(rs.next())
					                        {
					                            out.println("<option value='"+rs.getString("financial_year")+"'>"+rs.getString("financial_year")+"</option>");
					                        }
                    				%>
              
            </select>
            </div>
            </td>
            </tr>
         <tr align="left">
           			<td class="table">
              			<div align="left" id="more_id" style="display:none;"> Financial Year</div></td>
                 			<td class="table">
                 			<div align="left" id="more_id1" style="display:none;"> 
                 			<select id="liveFY" name="liveFY" style="width: 80pt;" onchange="monthLoad(this.value,'ConFYL');">
			<option value="">--Select--</option>			
			<option value="2012-2013">2012-13</option>
			<option value="2013-2014">2013-14</option>
		</select>		
		
          </div> </td> </tr>
          <tr class="table">
          <td align="left" >
             <div align="left" id="more" style="display:none;"> 
             From Month</div></td>
              <td align="left" >
              <div align="left" id="more1" style="display:none;"> 
			<select id="fromMonth" name="fromMonth" style="width: 80pt;" >	</select>
			</div></td></tr>
			 <tr class="table">
          <td align="left" >
              <div align="left" id="more2" style="display:none;"> 	
             To Month</div></td>
              <td align="left" >
              <div align="left" id="more3" style="display:none;"> 	
			<select id="toMonth" name="toMonth" style="width: 80pt;"></select></div>
          </td>
        </tr>
         <tr  align="left">
            <td>
               <div align="left"> Group Code <font color="#ff2121">*</font></div>
            </td>
            <td>
              <select size="1" name="cmbGrp_Head" id="cmbGrp_Head" tabindex="1" >
			<option value="">--Select--</option>
			<option value="'D1003'">Bank Balance</option>
			<option value="'D0201','D0202','D0204'">Investment</option>
			<option value="'A0301'">Finance charges</option>
			<option value="'D1201'">Advance Recoverable</option>
			<option value="'C1001','C1002'">Unsecured loan</option>
			<option value="'C1201'">Sundry Creditors</option>
			<option value="'A0201'">Administrative Expenses</option>
		</select>
            </td>
            </tr><!--
            <tr  align="left">
            <td>
               <div align="left"> Head Code Visible<font color="#ff2121">*</font></div>
            </td>
            <td>
            <input type="radio" id="rad_head" name="rad_head" value="" checked="checked" onclick="fun_yes();"/> Yes
            <input type="radio" id="rad_head" name="rad_head" value="" onclick="fun_No();"/> No
           </td></tr>
           --><tr  align="left">
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