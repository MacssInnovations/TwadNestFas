<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Civil_Budget_Statement_1</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen" />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
 <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript" src="../scripts/Civil_Additional_Budget_Allocation.js"></script>
<script type="text/javascript" src="../scripts/Un_Freeze_for_Consolidate.js"></script>
<script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>  
<script type="text/javascript">
function loadyear_month()
{
	 var today= new Date(); 
     var day=today.getDate();
     var month=today.getMonth();
     month=month+1;
     
     if(day<=9 && day>=1)
     day="0"+day;
     if(month<=9 && month>=1)
     month="0"+month;
     var year=today.getYear();
     if(year < 1900) year += 1900;
     var monthArray =new Array("January", "February", "March", 
               "April", "May", "June", "July", "August",
               "September", "October", "November", "December");
     document.frmCivil_Additional_Budget_Allocation.txtCrea_date.value=day+"/"+month+"/"+year;
}
</script>

</head>
<%
	String s = request.getContextPath();
String userid = (String) session.getAttribute("UserId");
System.out.println("User Id is:" + userid);
UserProfile up=null;
up=(UserProfile)session.getAttribute("UserProfile");
System.out.println("User name is:" + up.getEmployeeName());
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

<body onLoad="load(),loadyear_month(),initialLoad('<%=s%>');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><font size="4">Civil Additional Budget Allocation </font><strong> - Statement by HO</strong> </div>
		</td>
	</tr>
</table>
  
<form name="frmCivil_Additional_Budget_Allocation" id="frmCivil_Additional_Budget_Allocation"
	method="POST" action="../../../../../Civil_Additional_Budget_Allocation">
	<input type='hidden' name='RecordCount' id='RecordCount' value='0' /> 
	<input type='hidden' name='filter' id='filter' value='no' />
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="tdTitle1">
		<td colspan="2">
		<div align="left"><strong>General Details</strong></div>		</td>
	</tr>

	<tr class="table1" width="100%">
      <td>
      <div align="left">Accounting Unit Code <font color="#ff2121">*</font> </div>
      <div align="left">
          <select size="1" name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode" tabindex="1">
		
          </select>
      </div>
      </td>
      
	 <td>
         <div id="regiondiv" >
           Region Name
            <font color="#ff2121">*</font>
         </div>
        <div id="regiondiv1">
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
          </div><!--
             
		       <div>
		         <input type="radio" name="budgettype" id="budgettype" value="RE" " 
		                checked="checked"></input>
		         RE
		         <input type="radio" name="budgettype" id="budgettype" value="BE" "></input>
		        BE
		        </div>
		       --></td>
	  </tr>
	
	
	<tr class="table1">
		<td width="30%"><div align="left">Financial Year <font color="#ff2121">*</font></div></td>
		<td width="70%"><div align="left">
		  <select name="cmbFinancialYear" id="cmbFinancialYear" onchange="loadstatname('<%=s%>')">
		  <option value="">Select</option>
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
	<tr class="table">
                <td>
                  <div align="left">
                    Reference Date
                    <font color="#ff2121">*</font>
                  </div>
               
                  <div align="left">
                    <input type="text" name="txtCrea_date" id="txtCrea_date" tabindex="3"
                           maxlength="10" size="11" 
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           
                           onblur="call_mainJSP_script(this,1); "/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmCivil_Additional_Budget_Allocation.txtCrea_date,1);"
                         alt="Show Calendar"></img>
                    
                  </div>
                </td>
                <td>
                <div align="left">
                    Reference No
                    <font color="#ff2121">*</font>
                  </div>
               
                  <div align="left">
                    <input type="text" name="refno" id="refno" tabindex="5" size="70"/>
                    </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Entered By
                    <font color="#ff2121">*</font>
                  </div>
               
                <select name="app" id="app" >
               <option value=<%=userid%>><%=up.getEmployeePrefix()+" "+ up.getEmpInitial() + "." + up.getEmployeeName()%></option>
                </select>
                </td>
                <td>
                <div align="left">
                    Remarks
                    <font color="#ff2121">*</font>
                  </div>
               
                  <div align="left">
                  <textarea rows="3" cols="80" name="rem" id="rem"></textarea>
                   
                    </div>
                </td>
                </tr>
	<tr class="table1">
      <td>Statement Name<font color="#ff2121">*</font></td>
	  <td><label>
        <select name="cmbStatementName" id="cmbStatementName">
          <option value="">---Select---</option>
        </select>
       
		  <input type="button" id="butGo" name="butGo" value="GO"
			onClick="LoadGrid();">
          <!-- <input type="button" name="butView" id="butView" value="View for Update" />  -->
</label></td>
	  </tr>
	<tr class="table1">
		<td colspan="2">
		<div align="center"></div>		</td>
	</tr>
</table>
</div>

<div id="secDiv" >
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
				<th width="8%">Group No</th>
				<th width="8%">Amount (Rs. in lakhs)</th>	
				<th width="8%">Additional Amount Req(Rs. in lakhs)</th>
				<th width="8%">Additional Amount Allotted(Rs. in lakhs)</th>
							
			</tr>
			<tbody id="grid_body_two" class="table1" align="Center" width="200%">
			</tbody>
		</table>

	  </td>
	</tr>
</table>
</div>
<div align="center">
<table cellspacing="1" cellpadding="3" width="100%">
<tr class="tdH1">
<td width="5%">	</td>
<td width="25%">	 </td>				
		<td >	<strong>Total Amount &nbsp;&nbsp;</strong>    </td>		
		  <td >		   
		    <div align="center"> 
	          <input type="text" name="txtTotalAmount" id="txtTotalAmount" align="right" readonly="readonly"  onblur="focus_save();">
	            
        </div></td>
         <td>		   
		    <div align="center"> 
	         
	            <input type="text" name="txtTotalAmount_Req" id="txtTotalAmount_Req" align="right" readonly="readonly" onblur="focus_save();">
	            
	          		  
        </div></td>
         <td >		   
		    <div align="center"> 
	          
	              <input type="text" name="txtTotalAmount_allotted" id="txtTotalAmount_allotted" align="right" readonly="readonly" onblur="focus_save();">
	          		  
        </div></td>
        </tr>
	<tr class="tdH1">
		<td colspan="4">
		<div align="center">
		<input type="submit" name="butSub" id="butSub" value="SAVE" onClick="return funcSave();" /> &nbsp; 
		<input type="submit" name="butDelete" id="butDelete" value="DELETE" disabled="disabled" onClick="return funcDelete();" /> &nbsp;    
		<input type="button" name="butCan" id="butCan" value="CANCEL" onclick="clrForm1();" /> &nbsp;&nbsp;&nbsp; 
		<input type="button" name="butCan" id="butCan" value="EXIT" onClick="exitfun();" /></div>
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>