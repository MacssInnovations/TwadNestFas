
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
<title>Civil Budget Additional Re-appropriation</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript" src="../scripts/Un_Freeze_for_Consolidate.js"></script>
<script type="text/javascript" src="../scripts/Civil_Budget_Additional_Re_appropriation.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<style type="text/css">
.combolist{
width:350px !important;
}

</style>
</head>
<%
	String s = request.getContextPath();
%>

<body onLoad="initialLoad('<%=s%>');" bgcolor="#FFF9FF">

<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><font size="4">Civil Budget Additional Re-appropriation</font><strong> - Statement by Division </strong> </div>
		</td>
	</tr>
</table>

<form name="frmCivil_Budget_Additional_Re_appropriation" id="frmCivil_Budget_Additional_Re_appropriation"
	method="POST" action="../../../../../Civil_Budget_Additional_Re_appropriation?command=add">
	<input type='hidden' name='RecordCount' id='RecordCount' value='0' /> 
	<!--<input type='hidden' name='RecordCount1' id='RecordCount1' value='0' />
	<input type='hidden' name='filter' id='filter' value='no' />
--><div align="center">
  <%
  
  Connection con=null;
  ResultSet rs=null,rs2=null,rsbank=null;
  PreparedStatement ps=null,ps2=null,psbank=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
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
      <% 
        HttpSession session=request.getSession(false);
         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId(); 
    //int empid=10099;
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
     /* */      
                 
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="tdTitle1">
		<td colspan="2">
		<div align="left"><strong>General Details</strong></div>		</td>
	</tr>
	
	<tr class="table1">
      <td><div align="left"> Region Name  <font color="#ff2121">*</font> </div></td>
	  <td><!--
	  <div id="regiondiv" >
          
            <font color="#ff2121">*</font>
         </div>
        --><div id="regiondiv1">
            <select size="1" name="txtRegionId" id="txtRegionId" tabindex="1" >
             <option value="0">--Select Region--</option>
              <%
              try{
                        ps=con.prepareStatement("select OFFICE_NAME,OFFICE_ID from COM_MST_OFFICES where OFFICE_LEVEL_ID in ('RN','HO') and office_status_id not in('CL','RD','NC')");
                        rs=ps.executeQuery();
                        while(rs.next())
                        {
                            out.println("<option value="+rs.getInt("OFFICE_ID")+">"+rs.getString("OFFICE_NAME")+"</option>");
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
	  
	<!--<tr class="table1">
      <td><div align="left">Accounting For Office Code <font
			color="#ff2121">*</font></div></td>
	  <td><div align="left">
          <select size="1" name="cmbOffice_code"
			id="cmbOffice_code" tabindex="2" >
			 <option value="0">--Select Accounting Office Code--</option>
                                  
                                
          </select>
      </div></td>
	  </tr>
	  
	  
	  
	--><tr class="table1">
		<td width="30%"><div align="left">Financial Year <font color="#ff2121">*</font></div></td>
		<td width="70%"><div align="left">
		  <select name="cmbFinancialYear" id="cmbFinancialYear" >
		   <option value="0">Select</option>
                                   	<%
					                        st=con.createStatement();
					                        rs=st.executeQuery("select financial_year from cash_book_control order by financial_year");
					                        while(rs.next())
					                        {
					                            out.println("<option value='"+rs.getString("financial_year")+"'>"+rs.getString("financial_year")+"</option>");
					                        }
                    				%>
		    </select>
		
		</div></td>
	</tr>	
	<tr class="table1">
      <td>Statement Name<font color="#ff2121">*</font></td>
	  <td><label>
        <select name="cmbStatementName" id="cmbStatementName" onChange="chooseGroup('<%=s%>');">
          <option value="0">---Select---</option>
        </select>
	  </label></td>
	  </tr>
	  <tr class="table1">
      <td>Statement Group<font color="#ff2121">*</font></td>
	  <td>
        <select name="statementGp" id="statementGp" onblur="callHead()" onchange="callHead()" >
          <option value="0">---Select---</option>
        </select>
        
	  </td>
	  </tr>
	  <tr class="table1">
      <td>Type of Allocation<font color="#ff2121">*</font></td>
	  <td>
        <input type="radio" name="groupId" id="groupId" value="H" onclick="blockHead(),savebutton();">HeadWise
        <input type="radio" name="groupId" id="groupId" value="G" onclick="blockHead(),savebutton();">GroupWise
        
	  </td>
	  </tr>
	 
	 
	  <tr class="table1">
      <td>
      <div id="head_div1" name="head_div1" >
      Head Of Account<font color="#ff2121">*</font>
      </div></td>
	  <td width="80%">
	  <div id="head_div2" name="head_div2" >
        <select name="head_code" id="head_code" >
          <option value="">---Choose A/c---</option>
        </select><!--  onchange="reallocation_fn('<%=s%>');"
       <input type="text" size="5" readonly="readonly" name="groupType" id="groupType"></input>
       --></div>
	 </td>
	  </tr>
	  
</table>
</div>
<div align="center">
<table cellspacing="1" cellpadding="3" width="100%">

	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><input type="button" name="butgo"
			id="butgo" value="GO"  onclick="loadTable()"/> &nbsp; 
			</div>
		</td>
	</tr>
</table>
</div>
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="tdTitle1">
		<td colspan="2">
		<div align="left"><strong>Details</strong></div>
		</td>
	</tr>
	<tr>
	  <td colspan="2">

		<table id="mytable" cellspacing="3" cellpadding="2" border="0"
			width="100%">
			<tr class="tdH1" align="center">
			    <th width="1%">Sl No</th>
				<th width="5%">Unit Name</th>
				<th width="5%">Statement Group</th>
				<th width="5%">Head of Account</th>
				<th width="5%">Amount Allocated</th>
				<th width="5%">Exp.So far Upto </th>
				<th width="5%">Balance</th>
				<th width="5%">Allotment Requested</th>	
				<th width="5%">MAY BE DIVERTED from This office Allotment</th>
									
			</tr>
			<tbody id="grid_body" class="table1" align="Center" width="200%">
			</tbody>
		</table>

	  </td>
	</tr>
</table>
<div align="center">
<table cellspacing="1" cellpadding="3" width="100%">
<tr class="tdH1">
<td width="5%">	</td>
<td width="25%">	 </td>				
		<td>	<strong>Total Amount &nbsp;&nbsp;</strong>    </td>		
		  <td >		   
        </td>
         <td width="5%">		   
		   </td>
		   <td width="25%">		   
		   </td>
		   <td>		   
		   </td>
		   <td width="15%">		   
		   </td>
         <td >		   
		    <div align="center"> 
	          
	              <input type="text" name="txtTotalAmount_allotted" id="txtTotalAmount_allotted" align="right" readonly="readonly" onblur="focus_save();">
	          		  
        </div></td>
        </tr>
	<tr class="tdH1">
		<td colspan="9">
		<div align="center">
		<input type="submit" name="butSub" id="butSub" value="SAVE" /><!-- &nbsp; 
		<input type="submit" name="butDelete" id="butDelete" value="DELETE" disabled="disabled" onClick="return funcDelete();" /> &nbsp;    
		--><input type="button" name="butCan" id="butCan" value="CANCEL" onclick="clrForm1();" /> &nbsp;&nbsp;&nbsp; 
		<input type="button" name="butCan" id="butCan" value="EXIT" onClick="exitfun();" /></div>
		</td>
	</tr>
</table>
</div>

</form>
</body>
</html>