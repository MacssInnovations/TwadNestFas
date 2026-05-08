<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page
	import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="/org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
<title>Work_Bill_Journal</title>

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="../../../../../org/Library/scripts/checkDate.js"></script>
<script type="text/javascript" src="../scripts/Work_Bill_Journal.js"></script>
<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="../../../../../org/Library/scripts/checkDate.js"></script>
	
	
	 <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>


<script type="text/javascript" language="javascript">
       
         function loadDate()
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
       // document.frmWork_Bill_Journal.txtCrea_date.value=day+"/"+month+"/"+year;
        document.frmWork_Bill_Journal.cboCashBook_Year.value=year;
		if(month<10)
		{
		month=month.substr(1);
		}
        document.frmWork_Bill_Journal.cboCashBook_Month.value=month;
         }
        
   
</script>
</head>
 <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
<%
	String s = request.getContextPath();
	System.out.println("s");
%>
<body onLoad="loadDate();LoadAccountingUnitID_Create('LIST_ALL_UNITS');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Create Work Bill
		Journal System </font></div>
		</td>
	</tr>
</table>

<form name="frmWork_Bill_Journal" id="frmWork_Bill_Journal"
	method="POST" action="<%=s %>/Work_Bill_Journal?command=Add"
	onSubmit="return checkk();"><input type='hidden'
	name='RecordCount' id='RecordCount' value='0' /> 
	
<div align="center">
<table cellspacing="1" cellpadding="2" border="1" width="100%">
	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>General Details</strong></div>
		</td>
	</tr>
	<tr class="table">
		<td width="30%">
		<div align="left">Accounting Unit Code <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><!--<input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>-->
		<select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
			tabindex="1" onChange="common_LoadOffice_New(this.value);">
			<!-- <option value="0"> Select Account Unit </option>-->
			<%
                      int unitid=0;
                      String unitname="";
                      try{
                        if(oid==5000)
                        {
                             //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                            //ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?");
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
		</select></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Accounting For Office Code <font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbOffice_code"
			id="cmbOffice_code" tabindex="2">

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
                        ps=con.prepareStatement("select ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID  from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_UNIT_ID=? order by ACCOUNTING_FOR_OFFICE_ID desc");
                        ps.setInt(1,unitid);
                        rs=ps.executeQuery();
                        //out.println("<option value="+oid+">"+oname+"</option>");
                        while(rs.next())
                        {
                        	int old_offid=0;

                        	 ps2=con.prepareStatement("select old_office_id from hrm_emp_current_posting where employee_id=?");
                        	                             ps2.setInt(1,empid);
                        	                             rs2=ps2.executeQuery();
                        	                             while(rs2.next())
                        	                             {
                        	                            	 old_offid=old_offid+1;
                        	                             }
                        	                        	if(old_offid !=0)
                        	                        	{
                        	                        		ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? ");
                        	                        	}
                        	                        	else if(old_offid==0)
                        	                        	{
                        	                             ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('CL','NC','RD')");
                        	                        	}
                        //ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('NC','CL','RD')");
                        ps2.setInt(1,rs.getInt("ACCOUNTING_FOR_OFFICE_ID"));
                        rs2=ps2.executeQuery();
                        if(rs2.next())
                        out.println("<option value="+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+">"+rs2.getString("OFFICE_NAME")+"</option>");
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
		</select></div>
		</td>
	</tr>
	<!--<tr class="table">
		<td>
		<div align="left">Date <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCrea_date"
			id="txtCrea_date" tabindex="3" maxlength="10" size="11"
			onfocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);"
			onblur="call_mainJSP_script(this,1); " />    
                           onblur="return checkdt(this);"/>   <img
			src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frmWork_Bill_Journal.txtCrea_date,1);"
			alt="Show Calendar"></img></div>
		</td>
	</tr>
	--><tr class="table">
		<td>
		<div align="left">CashBook Year<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="cboCashBook_Year"
			id="cboCashBook_Year" onKeyPress="return numbersonly1(event,this)"
			maxlength="4" size='5'></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">CashBook Month<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="cboCashBook_Month"
			id="cboCashBook_Month">
			<option value="s">---Select---</option>
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
		</select></div>
		</td>
	</tr>
	
	<tr class="table">
                <td>
                  <div align="left">
                    Journal Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCrea_date" id="txtCrea_date" tabindex="3"
                           maxlength="10" size="11" readonly="readonly"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_mainJSP_script(this,1),check_withinCB(); dateCheck(this);"/>
                      
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmWork_Bill_Journal.txtCrea_date,1);"
                         alt="Show Calendar"></img>
                    
                            <input type="button" name="btnGO" id="btnGO" value="Go"
			onClick="loadGrid('<%=s%>');"> <input type="button"
			name="butCan2" id="butCan2" value="CANCEL" onclick="clrForm2();" />
                    
                  </div>
                </td> 
                
              </tr>
</table>
<div style="display: none" id="CHD">
<table cellspacing="1" cellpadding="2" border="1" width="100%">
	<tr class="table">
		<td width="30%">
		<div align="left">Cheque Number and Date</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCheque_NO"
			maxlength="10" onKeyPress="return numbersonly(event)"
			id="txtCheque_NO" size="11" tabindex="5" /> <input type="text"
			name="txtCheque_date" id="txtCheque_date" maxlength="10" size="11"
			tabindex="6" onfocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);"
			onblur="return checkdt(this);" /> <img
			src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frmJournal_General.txtCheque_date);"
			alt="Show Calendar"></img></div>
		</td>
	</tr>
</table>
</div>

<table cellspacing="1" cellpadding="2" border="1" width="100%">
</table>
</div>

<div align="center">
<table cellspacing="1" cellpadding="2" border="1" width="100%">
	<tr>
		<td colspan="2">
		<div id="sub_ledge_dis">
		<table cellspacing="1" cellpadding="2" border="1" width="100%">
			<tr class="tdTitle">
				<td width="100%">
				<div align="left"><strong> Details</strong></div>
				</td>
			</tr>
		</table>
		</div>
		<div id="grid" style="display: block">
		<table id="mytable" cellspacing="3" cellpadding="2" border="1"
			width="100%">

			<tr class="table">
				<th width="5%">Select</th>
				<th width="10%">Sl No</th>
				<th width="10%">Bill Date</th>
				<th width="10%">Sub Ledger Code</th>
				<th width="8%">Remarks</th>
				<th width="6%">Details</th>
			</tr>
			<tbody id="grid_body" class="table" align="left">
			</tbody>
		</table>
		</div>
		</td>
	</tr>
</table>
</div>
<br>
<div align="center">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td>
		<div align="center"><input type="submit" name="butSub"
			id="butSub" value="SUBMIT" disabled/> &nbsp;&nbsp;&nbsp; <input
			type="button" name="butCan" id="butCan" value="CANCEL"
			onclick="clrForm();" /> &nbsp;&nbsp;&nbsp; <input type="button"
			name="butCan" id="butCan" value="EXIT" onclick="exit();" /></div>
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>