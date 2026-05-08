<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page
	import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252" />
<meta http-equiv="cache-control" content="no-cache">
<title>Project Master Details</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="../../../../../org/Library/scripts/checkDate.js"></script>
<script type="text/javascript" src="../scripts/projectMaster.js"></script>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
</head>
<%
String s=request.getContextPath();
%>
<body onload="callFunction('List','null'),setTimeout('findHeadOffice()',900)">
<form name="frmProjectMaster" id="frmProjectMaster" action="">
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
                ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                 Class.forName(strDriver.trim());
                 con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
      }
      catch(Exception e)
      {
        System.out.println("Exception in connection...."+e);
      }
  %> <% 
        HttpSession session=request.getSession(false);
        UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
          
        System.out.println("user id::"+empProfile.getEmployeeId());
        int empid=empProfile.getEmployeeId();
        //int empid=9315;
        int  oid=0;
        String oname="";
   
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

<table cellspacing="2" cellpadding="3" border="1" width="100%">
	<tr class="tdH" align="center">
		<td colspan="2">Project Master Details</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Office Code & Name:</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtOfficeId"
			id="txtOfficeId" value="<%=oid%>" size="5" readonly="readonly"
			class="disab" /> <input type="text" name="txtOfficeName"
			id="txtOfficeName" value="<%=oname%>" size="30" readonly="readonly"
			class="disab" /> <select name="cmbWing" id="cmbWing"
			style="visibility: hidden">			
		</select></div>

		</td>
	</tr>
	<tr class="table" align="left">
		<td>
		<div>Project Id</div>
		</td>
		<td>
		<div><input type="text" size="7" name="txtProjectId"
			id="txtProjectId" readonly="readonly" class="disab" /> (System
		Generated)</div>
		</td>
	</tr>
	<tr class="table" align="left">
		<td>
		<div>Project Name</div>
		</td>
		<td>
		<div><input type="text" size="50" maxlength="200"
			name="txtProjectName" id="txtProjectName" /></div>
		</td>
	</tr>
	<tr class="table" align="left">
		<td>
		<div>Project Alias Code</div>
		</td>
		<td>
		<div><input type="text" size="50" maxlength="200"
			name="txtProjectCode" id="txtProjectCode" /></div>
		</td>
	</tr>
	<tr class="table" align="left">
		<td>
		<div>Component Name</div>
		</td>
		<td>
		<div><textarea name="txtCname" id="txtCname" cols="50" rows="4"></textarea>
		</div>
		</td>
	</tr>
	<tr align="left" class="table">
		<td>Status</td>
		<td><input type="radio" name="txtstatus" id="txtstatus"
			tabindex="15" value="L" checked>Live
		&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name="txtstatus"
			id="txtstatus" value="C">Cancel &nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>
	<tr class="tdH">
		<td colspan="2">
		<div align="center">
		<table>
			<tr>
				<td><input type="button" name="cmdAdd" value="ADD" id="cmdAdd"
					onclick="callFunction('Add','null')" tabindex="3" /></td>
				<td><input type="button" name="cmdUpdate" value="UPDATE"
					id="cmdUpdate" style="display: none"
					onclick="callFunction('Update','null')" tabindex="5" /></td>
				<td><input type="button" name="cmdDelete" value="CANCEL"
					id="cmdDelete" style="display: none"
					onclick="callFunction('Cancel','null')" /></td>
				<td><input type="button" name="cmdClear" value="CLEAR"
					id="cmdClear" onclick="ClearAll()" /></td>
				<td><input type="button" id="Exit" name="Exit" value="EXIT"
					onclick="self.close()"></td>
			</tr>
		</table>
		</div>
		</td>
	</tr>

</table>
<br>
<table cellpadding="3" cellspacing="2" width="100%" border="1"
	id="Existing">
	<tr align="center" class="tdH">
		<td>Select</td>
		<td>Project Id</td>
		<td>Project Name</td>
		<td>Project Alias Code</td>
		<td>Component Name</td>
		<td>Status</td>
	</tr>
	<tbody align="center" id="tab_body" class="table">

	</tbody>
</table>
</form>

</body>
</html>