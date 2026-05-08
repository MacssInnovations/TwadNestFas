<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BRS_Monthly_Closure_Freeze_Finished_List</title>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
<script type="text/javascript"
	src="../../../../../org/Library/scripts/checkDate.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="../../../../Security/scripts/tabpane.js"></script>

<script type="text/javascript" src="../scripts/BRS_Monthly_Closure_Freeze_Finished_List.js"></script>

</head>
<%
	String s = request.getContextPath();
%>
<%
	System.out.println(s);
%>
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
<body onLoad="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Bank Reconciliation System - BRS Monthly Closure Freeze Finished List</font></div>
		</td>
	</tr>
</table>

<form name="Monthly_Closure_Freeze_unitwise" id="Monthly_Closure_Freeze_unitwise"
	method="POST" action="BRS_Monthly_Closure_Freeze_Finished_List">
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"></div>		</td>
	</tr>
	<tr class="table">
                    <td>
                      <div align="left">
                          Accounting Unit Code
                      </div>
                    </td>
                    <td>
                      <div align="left">			              
                          <select name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" >
                            
                            <%
                            
                                     try
                                     {
                                            ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS "+
                                            		" WHERE  ACCOUNTING_UNIT_OFFICE_ID NOT IN "+
                                                   	" (SELECT OFFICE_ID "+
                                                   	" FROM COM_MST_OFFICES "+
                                                   	" WHERE OFFICE_STATUS_ID IN('CL','RD','NC') "+
                                                   	" ) "+
                                                   	" AND STATUS IS NULL "+
                                                   	" ORDER BY accounting_unit_name");
                                            		
                                            rs=ps.executeQuery();
                                            while(rs.next())
                                            {
                                                out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"("+rs.getInt("ACCOUNTING_UNIT_ID")+")"+"</option>");
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
      <td>&nbsp;</td>
	  <td>&nbsp;</td>
	  </tr>
	
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><input type="button" name="onsubmit1"
			value="PRINT" id="onsubmit1" onClick="printFunc_unitwise('<%=s%>');" />
		&nbsp;&nbsp; 
		<input type="button" name="butCan" id="butCan"
			value="CANCEL" onClick="refresh();" />&nbsp;&nbsp;&nbsp; <input
			type="button" name="butCan" id="butCan" value="EXIT"
			onClick="exitfun();" /> &nbsp;&nbsp;&nbsp;</div>		</td>
	</tr>
</table>
</div></form></body>
</html>