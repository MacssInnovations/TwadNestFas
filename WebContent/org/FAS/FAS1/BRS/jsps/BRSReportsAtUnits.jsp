<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
    <%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BRS Reports At Units</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />

<script type="text/javascript"
	src="../../../../Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>
<script type="text/javascript" src="../scripts/BRSReportsAtUnits_js.js"></script>

<script type="text/javascript" language="javascript">
       function loadyear_month()
       {
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             var year=today.getYear();
             if(year < 1900) year += 1900;
                        
            document.BRSUnitsReport.txtCB_Year.value=year;
            document.BRSUnitsReport.txtCB_Month.value=month;
      }
      
    
    </script>
</head>
 <%
  
		      Connection con=null;
		      ResultSet rs=null,rs2=null,rs3=null;
		      Statement st=null;
		      PreparedStatement ps=null,ps1=null,ps2=null;
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
            rs3=ps.executeQuery();
                 if(rs3.next()) 
                 {
                    oid=rs3.getInt("OFFICE_ID");
                 }
            rs3.close();
            ps.close();
            ps=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
            ps.setInt(1,oid);
            rs3=ps.executeQuery();
                 if(rs3.next()) 
                 {
                    oname=rs3.getString("OFFICE_NAME");
                  }
            rs3.close();
            ps.close();
     /* */      
                 
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
<%
	String s = request.getContextPath();
%>
<%
	System.out.println(s);
%>
<body onload="LoadAccountingUnitID('LIST_ALL_UNITS');loadyear_month();">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH" >
		<td colspan="2" >
		<div align="center">
                <font size="4">BRS Reports At Units</font></div>
		</td>
	</tr>
</table>


<form name="BRSUnitsReport" id="BRSUnitsReport" method="POST" action="BRSReportsAtUnits_servlet">
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">

	<tr class="tdTitle">
		<td colspan="2">
		<div align="center"><strong>General Details</strong></div>
		</td>
	</tr>



	<tr class="table">
                <td>
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <!--<input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>-->
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);load_acc('<%=s%>',this.value);"> 
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
                            	  System.out.println("hhhh");
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
                       // ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('NC','CL','RD')");
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
                    </select>
                  </div>
                </td>
              </tr>

	<tr class="table">
		<td>
		<div align="left">Cash Book Year &amp; Month(Upto)</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCB_Year"
			id="txtCB_Year" tabindex="3" maxlength="4" size="5"
			onkeypress="return numbersonly1(event,this)"></input> <select
			name="txtCB_Month" id="txtCB_Month" tabindex="4">
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
		<div align="left">Sort Order</div></td>
		 <td>
		<input type="radio" id="sortType" name="sortType" value="Trn_Type" checked="checked" />Transaction TypeWise 
		<input type="radio" id="sortType" name="sortType" value="Pass_Date" /> PassBook Datewise
		</td></tr>
        <tr class="table">
                <td>
		<div align="left">Bank Transaction Type</div>
		</td>
                <td>
		<select name="trnType" id="trnType">
                <option value="s">------ALL-------</option>
                <%
                    try{
                    ps=con.prepareStatement("select TRANS_CODE,TRANS_SHORT_DESC from FAS_BRS_TRANSACTION_TYPE ");
                    rs=ps.executeQuery();
                        while(rs.next())
                        {
                        out.println("<option value="+rs.getInt("TRANS_CODE")+">"+rs.getString("TRANS_SHORT_DESC")+"</option>");
                        }
                    }
               catch(Exception e)
                     {
                            System.out.println("Exception in Transaction Type..."+e);
                     }
                     finally
                     {
                            rs.close();
                            ps.close();
                     }  
                %>
                </select> 
		</td>
        </tr>

	<tr class="table">
                <td>
                    <div align="left"> Bank Account Number <font color="Red">*</font>
                    </div>
                </td>
                <td>
                <div align="left">
                    <select name="txtBankAccountNo" id="txtBankAccountNo" onchange="doFunction('PendingReceipts','null');">
                    <option value="">-- Select Bank Account No--</option>
                   
                    </select><input type="button" value="GO" onclick="load_acc('<%=s%>',1);">
                </div>
                </td>
                
              </tr>
              <tr align="left">
          <td class="table">
          <div align="left">
              From Date &amp; To Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              </div>
              </td>
              <td>
               <div align="left">
                    <input type="text" name="txtFrom_date" id="txtFrom_date"  tabindex="6"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.BRSUnitsReport.txtFrom_date);"
                         alt="Show Calendar"></img>
           
                    <input type="text" name="txtTo_date" id="txtTo_date"  tabindex="7"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.BRSUnitsReport.txtTo_date);"
                         alt="Show Calendar"></img>
            </div>
          </td>          
        </tr>
	<div align="left" style="visibility: hidden">
        <input type="hidden"
		name="txtBankID" id="txtBankID" tabindex="5"
		style="background-color: #ececec" readonly="readonly" size="50" /> <input
		type="hidden" name="txtBranchID" id="txtBranchID" size="50"
		style="background-color: #ececec" readonly="readonly" /></div>
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><input type="button" name="onsubmit1" value="SUBMIT" id="onsubmit1" onClick="printFunctions('<%=s%>');" />
		&nbsp;&nbsp;&nbsp; <input type="button" name="butCan" id="butCan"
			value="CANCEL" onclick="refresh();" /> &nbsp;&nbsp;&nbsp; <input
			type="button" name="butCan" id="butCan" value="EXIT"
			onclick="window.close();" /></div>
		</td>
	</tr>
</table>
</div></form>
</body>
</html>