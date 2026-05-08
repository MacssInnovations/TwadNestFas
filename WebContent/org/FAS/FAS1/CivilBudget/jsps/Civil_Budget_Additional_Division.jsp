
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
<title>Civil_Budget_Additional_Division</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript" src="../scripts/Un_Freeze_for_Consolidate.js"></script>
<script type="text/javascript" src="../scripts/Civil_Budget_Additional_Division.js"></script>
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

<body onLoad="initialLoad('<%=s%>'),LoadAccountingUnitID('LIST_ALL_UNITS'),setTimeout('loadTable()',800);" bgcolor="#FFF9FF">

<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><font size="4">Civil Budget Additional </font><strong> - Statement by Division </strong> </div>
		</td>
	</tr>
</table>

<form name="frmCivil_Budget_Additional_Division" id="frmCivil_Budget_Additional_Division"
	method="POST" action="../../../../../Civil_Budget_Additional_Division?command=add" onsubmit="return checkFreeze()">
	<input type='hidden' name='RecordCount' id='RecordCount' value='0' /> 
	<input type='hidden' name='RecordCount1' id='RecordCount1' value='0' />
	<input type='hidden' name='filter' id='filter' value='no' />
<div align="center">
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
      <td><div align="left">Accounting Unit Code <font color="#ff2121">*</font> </div></td>
	  <td><div align="left">
          <select name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode"  onchange="common_LoadOffice(this.value) ;">
			<option value="0">--Select Account Unit--</option>
                    
                          <%
                      int unitid=0;
                      String unitname="";
                      try{
                        if(oid==5000)
                        {
                             
                            String getWing="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=? and OFFICE_WING_SINO=(select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)";
                            ps=con.prepareStatement(getWing);
                            ps.setInt(1,oid);
                            ps.setInt(2,empid);
                            ps.setInt(3,oid);
                            rs=ps.executeQuery();
                          
                              if(rs.next())
                              {
                              out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                              unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                              
                              System.out.println(".."+rs.getInt("ACCOUNTING_UNIT_ID"));
                              System.out.println(".."+rs.getString("ACCOUNTING_UNIT_NAME"));
                              System.out.println(".."+rs.getInt("OFFICE_WING_SINO"));
                              
                              }
                          System.out.println(oid+" "+oname);
                          ps.close();
                          rs.close();
                          }
                              else
                              {
                                ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)");
                                ps.setInt(1,oid);
                                rs=ps.executeQuery();
                                  if(rs.next())
                                  {
                                  System.out.println(rs.getInt("ACCOUNTING_UNIT_ID"));
                                  System.out.println(rs.getString("ACCOUNTING_UNIT_NAME"));
                                  //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                                  out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                  unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                  }
                                  ps.close();
                                  rs.close();
                              }
                          }
                      catch(Exception e)
                        {
                            System.out.println(e);
                        }
                      %>
          </select>
      </div></td>
	  </tr>
	  
	<tr class="table1">
      <td><div align="left">Accounting For Office Code <font
			color="#ff2121">*</font></div></td>
	  <td><div align="left">
          <select size="1" name="cmbOffice_code"
			id="cmbOffice_code" tabindex="2" >
			 <option value="0">--Select Accounting Office Code--</option>
                                  
                                 <%
                   System.out.println("here");
                   System.out.println(oid+"  " +oname);
                try
                {
                   if(oid==5000)
                    {
                        out.println("<option value="+oid+">"+"HEAD OFFICE"+"</option>");
                    }
                    else
                    {
                        ps=con.prepareStatement("select ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID  from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_UNIT_ID=?order by ACCOUNTING_FOR_OFFICE_ID desc");
                        ps.setInt(1,unitid);
                        rs=ps.executeQuery();
                        
                        int countoffice=0;      
                        while(rs.next())
                        {
                            ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                            ps2.setInt(1,rs.getInt("ACCOUNTING_FOR_OFFICE_ID"));
                            rs2=ps2.executeQuery();
                            if(rs2.next())
                            {
                              out.println("<option value="+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+">"+rs2.getString("OFFICE_NAME")+"</option>");                           
                            }
                        }
                    }
                    
                } 
                catch(Exception e)
                {
                System.out.println("Exception in Office combo..."+e);
                }
                finally
                {
                rs.close();
                ps.close();
                }  
               
                %>
          </select>
      </div></td>
	  </tr>
	  
	  
	  
	<tr class="table1">
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
        <select name="cmbStatementName" id="cmbStatementName" onChange="chooseGroup('<%=s%>');checkFreeze();">
          <option value="0">---Select---</option>
        </select>
	  </label></td>
	  </tr>
	  <tr class="table1">
      <td>Statement Group<font color="#ff2121">*</font></td>
	  <td>
        <select name="statementGp" id="statementGp" onblur="callHead('<%=s%>'),callAmt('<%=s%>'),loadTable();" onchange="callHead('<%=s%>'),callAmt('<%=s%>'),loadTable();" >
          <option value="0">---Select---</option>
        </select>
        
	  </td>
	  </tr>
	  <tr class="table1">
      <td>Type of Allocation<font color="#ff2121">*</font></td>
	  <td>
        <input type="radio" name="groupId" id="groupId" value="H" onclick="blockHead();" checked="checked" >HeadWise
        <input type="radio" name="groupId" id="groupId" value="G" onclick="blockHead();">GroupWise
        
	  </td>
	  </tr>
	  <tr class="table1">
	  <td>Total Allocation By HO<font color="#ff2121">*</font></td>
	  <td><!--
        <input type="text" readonly="readonly" name="budgetId" id="budgetId" size="20" value=0></input>
        --><input type="text" readonly="readonly" name="hoamountinrs" id="hoamountinrs" size="20" value=0></input>
        <font color="red">
		(Allocation in Rupees)
		</font>
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
 
	  <tr class="table1">
	  <td>Additional Budget Required<font color="#ff2121">*</font></td>
	  <td>
        <input type="text" name="budgetrequired" id="budgetrequired" size="20"  onkeypress="return numbersonly(event,this);"></input     
	  </td>
	  </tr>
	  
	<tr class="table1">
      <td>Reason<font color="#ff2121">*</font></td>
	  <td width="80%">
       <textarea name="txtReason" id="txtReason" cols="50" tabindex="7" onkeypress="" rows="4"></textarea>      
	  </td>
	 
	  </tr> 

	  
</table>
</div>
<div align="center">
<table cellspacing="1" cellpadding="3" width="100%">

	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><input type="submit" name="butSub"
			id="butSub" value="SAVE"/> &nbsp; 
			<input type="button" name="butUpdate" id="butUpdate" value="UPDATE"
			disabled="disabled" onClick="funcUpdate('<%=s%>');" /> &nbsp; 
			<input	type="button" name="butDelete" id="butDelete" value="DELETE"
			disabled="disabled" onClick="funcDelete1('<%=s%>');" /><!--<input
			type="button" name="butCan" id="butCan" value="CLEAR"
			onclick="clrForm1();" /> &nbsp;&nbsp;&nbsp; --><input type="button"
			name="butCan" id="butCan" value="EXIT" onClick="exitfun();" /></div>
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
			    <th width="1%">Select</th>
				<th width="5%">Statement Name</th>
				<th width="5%">Statement Group</th>
				<th width="5%">Head of Account</th>
				<th width="5%">Amount Allocated</th>
				<th width="5%">Budget Required</th>
				<th width="5%">Reason</th>						
			</tr>
			<tbody id="grid_body" class="table1" align="Center" width="200%">
			</tbody>
		</table>

	  </td>
	</tr>
</table>


</form>
</body>
</html>