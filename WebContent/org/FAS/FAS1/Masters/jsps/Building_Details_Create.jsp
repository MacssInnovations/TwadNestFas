<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
<meta http-equiv="cache-control" content="no-cache">
<title>Building Details</title>

    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>


<script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
<script type="text/javascript" src="../scripts/Common_RemittanceType.js"></script>
<script type="text/javascript" src="../scripts/Building_Details.js"></script>

<link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen" />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen" />
<link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen' />

<script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>
<!-- to avoid future date the above script used-->
<script type="text/javascript" src="../../../../Security/scripts/tabpane.js"></script>
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
            document.frmBuildingDetails.txtDate.value=day+"/"+month+"/"+year;
            //call_date(document.frmFundRemit_Create.txtCrea_date);
           // doFunction('PendingReceipts','null');  
        }
</script>


<script type="text/javascript" language="javascript">



function Exit()
 {
    window.open('','_parent','');
    window.close();
 }
</script>
</head>
<body onload="loadDate();LoadAccountingUnitID('LIST_ALL_UNITS');" bgcolor="rgb(255,255,225)">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Details of Buildings
		maintained by Value A/c Rendering Unit</font></div>
		</td>
	</tr>
</table>
<form action="../../../../../Building_Details_Create?command=Add" name="frmBuildingDetails" method="post" onsubmit="return validate();">
<%
	Connection con = null;
	ResultSet rs = null, rs2 = null, rsYr = null;
	PreparedStatement ps = null, ps2 = null, psYr = null;
	ResultSet results = null;
	ResultSet results1 = null;
	ResultSet results2 = null;
	try {

		ResourceBundle rs1 = ResourceBundle
				.getBundle("Servlets.Security.servlets.Config");
		String ConnectionString = "";

		String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
		String strdsn = rs1.getString("Config.DSN");
		String strhostname = rs1.getString("Config.HOST_NAME");
		String strportno = rs1.getString("Config.PORT_NUMBER");
		String strsid = rs1.getString("Config.SID");
		String strdbusername = rs1.getString("Config.USER_NAME");
		String strdbpassword = rs1.getString("Config.PASSWORD");

		//ConnectionString = strdsn.trim() + "@" + strhostname.trim()
			//	+ ":" + strportno.trim() + ":" + strsid.trim();
		ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

		Class.forName(strDriver.trim());
		con = DriverManager.getConnection(ConnectionString,
				strdbusername.trim(), strdbpassword.trim());
	} catch (Exception e) {
		System.out.println("Exception in connection...." + e);
	}
%> <%
 	HttpSession session = request.getSession(false);
 	UserProfile empProfile = (UserProfile) session
 			.getAttribute("UserProfile");

 	System.out.println("user id::" + empProfile.getEmployeeId());
 	int empid = empProfile.getEmployeeId();
 	//int empid=1758;
 	int oid = 0;
 	String oname = "";

 	try {

 		ps = con
 				.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
 		ps.setInt(1, empid);
 		results = ps.executeQuery();
 		if (results.next()) {
 			oid = results.getInt("OFFICE_ID");
 		}
 		results.close();
 		ps.close();
 		ps = con
 				.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
 		ps.setInt(1, oid);
 		results = ps.executeQuery();
 		if (results.next()) {
 			oname = results.getString("OFFICE_NAME");
 		}
 		results.close();
 		ps.close();
 		/* */

 	} catch (Exception e) {
 		System.out.println(e);
 	}
 %>
<div class="tab-pane" id="tab-pane-1">
<div class="tab-page">
<h2 class="tab">General</h2>

<div align="center">
<table cellspacing="1" cellpadding="2" border="1" width="100%">

	<tr class="table">
		<td>
		<div align="left">Accounting Unit Code <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left">
		<select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
			tabindex="1">
			<!-- <option value="0"> Select Account Unit </option>-->
			<%
				int unitid = 0;
				String unitname = "";
				try {
					if (oid == 5000) {
						//out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
						//ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?");
						String getWing = "select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=? and OFFICE_WING_SINO=(select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)";
						ps = con.prepareStatement(getWing);
						ps.setInt(1, oid);
						ps.setInt(2, empid);
						ps.setInt(3, oid);
						rs = ps.executeQuery();

						if (rs.next()) {
							out.println("<option value="
									+ rs.getInt("ACCOUNTING_UNIT_ID") + ">"
									+ rs.getString("ACCOUNTING_UNIT_NAME")
									+ "</option>");
							unitid = rs.getInt("ACCOUNTING_UNIT_ID");

							System.out.println(".."
									+ rs.getInt("ACCOUNTING_UNIT_ID"));
							System.out.println(".."
									+ rs.getString("ACCOUNTING_UNIT_NAME"));
							System.out
									.println(".." + rs.getInt("OFFICE_WING_SINO"));

						}
						System.out.println(oid + " " + oname);
						ps.close();
						rs.close();
					} else {
						ps = con
								.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)");
						ps.setInt(1, oid);
						rs = ps.executeQuery();
						if (rs.next()) {
							System.out.println(rs.getInt("ACCOUNTING_UNIT_ID"));
							System.out
									.println(rs.getString("ACCOUNTING_UNIT_NAME"));
							//out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
							out.println("<option value="
									+ rs.getInt("ACCOUNTING_UNIT_ID") + " >"
									+ rs.getString("ACCOUNTING_UNIT_NAME")
									+ "</option>");
							unitid = rs.getInt("ACCOUNTING_UNIT_ID");
						}
						ps.close();
						rs.close();
					}
				} catch (Exception e) {
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
			id="cmbOffice_code" tabindex="2" onchange="byUnitAndOfficeChange();">

			<%
				System.out.println("here");
				System.out.println(oid + "  " + oname);
				try {
					if (oid == 5000) {
						out.println("<option value=" + oid + ">" + "HEAD OFFICE"
								+ "</option>");
					} else {
						ps = con
								.prepareStatement("select ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID  from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_UNIT_ID=? order by ACCOUNTING_FOR_OFFICE_ID desc");
						ps.setInt(1, unitid);
						rs = ps.executeQuery();
						while (rs.next()) {
							ps2 = con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
							ps2.setInt(1, rs.getInt("ACCOUNTING_FOR_OFFICE_ID"));
							rs2 = ps2.executeQuery();
							if (rs2.next())
								out.println("<option value="
										+ rs.getInt("ACCOUNTING_FOR_OFFICE_ID")
										+ ">" + rs2.getString("OFFICE_NAME")
										+ "</option>");
						}
					}

				} catch (Exception e) {
					System.out.println("Exception in Office combo..." + e);
				} finally {
					rs.close();
					ps.close();
				}
			%>
		</select></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Date </div>
		</td>
		<td>
		<div align="left">
			<input type="text" name="txtDate"
			id="txtDate" tabindex="3" maxlength="10" size="11"
			style="background-color: #ececec" readonly="readonly"
			onfocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" onblur="call_date(this);" />
		<img src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frmBuildingDetails.txtDate,1);"
			alt="Show Calendar"></img></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Financial Year <font color="#ff2121">*</font></div>
		</td>
		<td>
			<div align="left">
				<select name="cmbFinyr" id="cmbFinyr">
					<option value="">-- Select Financial Year --</option>
					<%
						String finYrQry = "select distinct FINANCIAL_YEAR from CASH_BOOK_CONTROL order by FINANCIAL_YEAR ";
						System.out.println(finYrQry);
						psYr = con.prepareStatement(finYrQry);
						rsYr = psYr.executeQuery();
						while (rsYr.next()) 
						{
							out.println("<option value=" + rsYr.getString("FINANCIAL_YEAR") + 
										">" + rsYr.getString("FINANCIAL_YEAR") + "</option>");
						}
					%>
				</select>
			</div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Asset Code <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtAsset"
			id="txtAsset" onkeypress="return numonly(event);" size="6" /></div>
		</td>
	</tr>

	
	<tr class="table">
		<td>
		<div align="left">Name of the Building / Subsidiary Building</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtBuilding"
			id="txtBuilding" /></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Land Owner</div>
		</td>
		<td>
			<div align="left"><!-- input type="text" name="txtOwner"
				id="txtOwner" /-->
				<select name="cmbOwner" id="cmbOwner">
					<option value="">-- Select Owner --</option>
					<%
						try
						{
							String landOwnerQry = 	"SELECT DISTINCT owner_desc, owner_code " +
													"FROM fas_owner_details " +
													"ORDER BY owner_desc";
							System.out.println(landOwnerQry);
							psYr = con.prepareStatement(landOwnerQry);
							rsYr = psYr.executeQuery();
							while (rsYr.next()) 
							{
								out.println("<option value=" + rsYr.getInt("owner_code") + 
											">" + rsYr.getString("owner_desc") + "</option>");
							}
						}catch(Exception e)
						{
							System.out.println("Exception fetching Land Owners list from DB ==> " + e);
						}
					%>
				</select>
			</div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Survey No</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtSurvey"
			id="txtSurvey" onkeypress="return numonly(event);" /></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Door No</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtDoorNo"
			id="txtDoorNo" /></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Village</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtVillage"
			id="txtVillage" /></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Year of Consctruction</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtBuidConsYear"
			id="txtBuidConsYear" onkeypress="return numonly(event);" maxlength="4" size="6" /></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Type of Building</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtBuildType"
			id="txtBuildType" /></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">No of Floors</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtFloors"
			id="txtFloors" onkeypress="return numonly(event);" /></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Type of Foundation</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtFoundationType"
			id="txtFoundationType" /></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Structural Elements of the Building</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtStructuralElements"
			id="txtStructuralElements" /></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Total Construction Cost - Civil</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCivilCost"
			id="txtCivilCost" onkeypress="return numonly(event);" /></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Total Construction Cost - Electrical</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtElectricalCost"
			id="txtElectricalCost" onkeypress="return numonly(event);" /></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Total Construction Cost - External Service
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtExternalServCost"
			id="txtExternalServCost" onkeypress="return numonly(event);" /></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Total Cost of Additions
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtAdditionCost"
			id="txtAdditionCost" onkeypress="return numonly(event);" onchange="calcBookValue('');" /></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Total Book Value
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtBookValue" id="txtBookValue" 
				style="background-color: #ececec" readonly="readonly" onkeypress="return numonly(event);"/></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Account Head under which funds are alloted for construction
		</div>
		</td>
		<td>
		<div align="left">			
			<input type="text" name="txtAcHeadCode"
                           id="txtAcHeadCode" maxlength="8"
                           onkeypress="return numonly(event)"
                            onchange="sixdigit(this);" size="9"/>
			<input type="button" name="" id="" value="Select A/c Head" onclick="AC_HEAD = 'txtAcHeadCode';AccHeadpopup();"/></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Remarks</div>
		</td>
		<td>
		<div align="left"><textarea name="txtRemarks" id="txtRemarks"
			cols="50" onkeypress="return check_leng(this.value);" rows="4"></textarea>
		</div>
		</td>
	</tr>

        <tr class="tdH">
          <td colspan="2">
            <div align="center">
            <table>
	            <tr>
		            <td>
		            	<input type="button" name="CmdClearBuild" value="CLEAR" id="CmdClearBuild" onclick="clearBuild()"/>
	               	</td>
				</tr>
			</table>
            </div></td>
        </tr>

</table>
</div>
</div>

<div class="tab-page" id="gd">
<h2 class="tab">Floor Details</h2>

<div align="center">
<table cellspacing="1" cellpadding="2" border="1" width="100%">
	
	<tr class="table">
		<td>
		<div align="left">Floor No
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtFloorNo"
			id="txtFloorNo" onkeypress="return numonly(event);" size="3"/>
		</div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Year of Construction
		</div>
		</td>
		<td>
		<div align="left">
			<input type="text" name="txtFloorConsYear" id="txtFloorConsYear" onkeypress="return numonly(event);" maxlength="4" size="6"/>
		</div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Floor Height
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtFloorHeight"
			id="txtFloorHeight" onkeypress="return numonly(event);" size="8"/>
		</div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Plinth Area of the floor
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtPlinthArea"
			id="txtPlinthArea" onkeypress="return numonly(event);" size="8"/>
		</div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Construction Cost-Civil
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtFloorCivilCost"
			id="txtFloorCivilCost" onkeypress="return numonly(event);" size="12"/>
		</div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Construction Cost-Electrical
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtFloorElectricalCost"
			id="txtFloorElectricalCost" onkeypress="return numonly(event);" size="12"/>
		</div>
		</td>
	</tr>
		<tr class="table">
		<td>
		<div align="left">Construction Cost-External Service
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtFloorExternalServCost"
			id="txtFloorExternalServCost" onkeypress="return numonly(event);" size="12"/>
		</div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Cost of Additions
		</div>
		</td>
		<td>
		<div align="left">
			<input type="text" name="txtFloorAdditionCost" id="txtFloorAdditionCost" 
					onkeypress="return numonly(event);" size="12" onblur="calcBookValue('Floor');" />
		</div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Book Value
		</div>
		</td>
		<td>
		<div align="left">
				<input type="text" name="txtFloorBookValue" id="txtFloorBookValue" 
						onkeypress="return numonly(event);" size="12" 
						style="background-color: #ececec" readonly="readonly"/>
		</div>
		</td>
		
	</tr>
	<tr class="table">
		<td>
		<div align="left">B.P No for Construction
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtBPNo"
			id="txtBPNo" onkeypress="return numonly(event);" size="12"/>
		</div>
		</td>

	</tr>
	<tr class="table">
		<td>
		<div align="left">Account Head under which funds are alloted for construction
		</div>
		</td>
		<td>
		<div align="left">           
			<input type="text" name="txtFloorAcHeadCode"
                           id="txtFloorAcHeadCode" maxlength="8"
                           onkeypress="return numonly(event)"
                            onchange="sixdigit(this);" size="9"/>
			<input type="button" name="" id="" value="Select A/c Head" onclick="AC_HEAD = 'txtFloorAcHeadCode';AccHeadpopup();"/>
		</div>
		</td>
	</tr>
	
	<tr class="table">
		<td>
		<div align="left">Remarks</div>
		</td>
		<td>
		<div align="left"><textarea name="txtFloorRemarks" id="txtFloorRemarks"
			cols="50" onkeypress="return check_leng(this.value);" rows="4"></textarea>
		</div>
		</td>
	</tr>
	
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
	            <table>
	            <tr>
		            <td>
		            	<input type="button" name="CmdAddFloor" value="ADD" id="CmdAddFloor" onclick="addFloor();"/>
		            </td>
		            <td>
		            	<input type="button" name="CmdUpdateFloor" value="UPDATE" style="display:none" id="CmdUpdateFloor" onclick="updateFloor();"/>
		            </td>
		            <td>
		            	<input type="button" name="CmdDeleteFloor" value="DELETE" style="display:none" id="CmdDeleteFloor" onclick="delTempRow(SELECTED_FLOOR,'tblListFloor'); clearFloor();"/>
		            </td>
		            <td>
		            	<input type="button" name="CmdListOwner" value="CLEAR" id="CmdClear" onclick="clearFloor()"/>
		            </td>
		            <td>
		            	<input type="button" name="CmdClearOwner" value="CLEAR GRIDS" id="CmdClearOwner" onclick="clearAllGrids(document.getElementById('tblListFloor'))"/>
		            </td>

	            </tr>
	            </table> 
            </div></td>
        </tr>
</table>
<table id="ExistingFloors" cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
        <tr class="table">
        	<th align="left" colspan="12">
        		Floors to be Saved
        	</th>
        </tr>
        <tr class="tdH">
          <th>
            Select
          </th>
          <th>
            Floor
          </th>
          <th>Year
          </th>
          <th>
            Height
          </th>
          <th>
            Plinth Area
          </th>
          <th>
            Civil Cost <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(Rs)
          </th>
          <th>
            Electrical <br> &nbsp;&nbsp;&nbsp; Cost <br> &nbsp;&nbsp;&nbsp;&nbsp;(Rs)
          </th>
          <th>
            External  <br> &nbsp;Services <br> &nbsp;&nbsp;&nbsp;&nbsp;(Rs)
          </th>
          <th>
            Additions <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(Rs)
          </th>
          <th>
            Book <br> Value <br> &nbsp;(Rs)
          </th>
          <th>
            B.P No.
          </th>
          <th>
            A/c Head
          </th>
          
        </tr-->
    
        <tbody id="tblListFloor" class="table">
        </tbody>
        
    </table>
</div>
</div>


<div class="tab-page" id="gd">
<h2 class="tab">Occupying Office Details</h2>

<div align="center">
<table cellspacing="1" cellpadding="2" border="1" width="100%">
	
	<tr class="table">
		<td>
		<div align="left">Floor No
		</div>
		</td>
		<td>
		<div align="left">
			<input type="text" name="txtOfficeFloorNo"
					id="txtOfficeFloorNo" onkeypress="return numonly(event);" />
		</div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Details of Other offices occupying the Building
		</div>
		</td>
		<td>
		<div align="left">
			<select id="cmbOffice" name="cmbOffice">
					<option value="0">-- Select Office --</option>
					<%
						String officeQry = "SELECT office_id, office_name " +
											"FROM com_mst_offices " +
											"ORDER BY office_name";
						System.out.println(officeQry);
						ps = con.prepareStatement(officeQry);
						rs = ps.executeQuery();
						while (rs.next()) 
						{
							out.println("<option value=" + rs.getString("office_id") + 
										">" + rs.getString("office_name") + "</option>");
						}
					%>
			</select>
			<input type="text" id="txtOffice" name="txtOffice" style="display:none"/>
			<input type="radio" name="radOffice" value="T" onclick="fncOfficeType(this.value);" checked/> TWAD
			<input type="radio" name="radOffice" value="N" onclick="fncOfficeType(this.value);"/> Other Office
			
			<input type="text" name="hidOfficeType" id="hidOfficeType" value="T" onclick="officeType(this.value);" style="display:none"/>
		</div>
		</td>
	</tr>
		
	<tr class="table">
		<td>
		<div align="left">Remarks</div>
		</td>
		<td>
		<div align="left"><textarea name="txtOfficeRemarks" id="txtOfficeRemarks"
			cols="50" onkeypress="return check_leng(this.value);" rows="4"></textarea>
		</div>
		</td>
	</tr>
	
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
	            <table>
	            <tr>
		            <td>
		            	<input type="button" name="CmdAddOffice" value="ADD" id="CmdAddOffice" onclick="addOffice();"/>
		            </td>
		            <!-- td>
		            	<input type="button" name="CmdUpdateOffice" value="UPDATE" style="display:none" id="CmdUpdateOffice" onclick="updateOffice();"/>
		            </td-->
		            <td>
		            	<input type="button" name="CmdDeleteOffice" value="DELETE" style="display:none" id="CmdDeleteOffice" onclick="delTempRow(SELECTED_OFFICE,'tblListOffice'); clearOffice();"/>
		            </td>
		            <td>
		            	<input type="button" name="CmdListOffice" value="CLEAR" id="CmdClear" onclick="clearOffice()"/>
		            </td>
		            <td>
		            	<input type="button" name="CmdClearOffice" value="CLEAR GRIDS" id="CmdClearOffice" onclick="clearAllGrids(document.getElementById('tblListOffice'))"/>
		            </td>
	            </tr>
	            </table>
            </div></td>
        </tr>

</table>
<table id="ExistingOffices" cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
        <tr class="table">
        	<th align="left" colspan="12">
        		Offices to be Saved
        	</th>
        </tr>
        <tr class="tdH">
          <th>
            Select
          </th>
          <th>
            Floor No.</th>
          <th>
			Office Name
          </th>
          <th>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Type <br> (TWAD / Non-TWAD)
          </th>
        </tr>
    
        <tbody id="tblListOffice" class="table">
        </tbody>
        
    </table>

</div>
</div>
</div>

<table align="center">
	<tr class="tdH">
    	<td colspan="2">
        	<div align="center">
	        	<table>
	            	<tr>
		            	<td>
		            		<input type="submit" name="CmdSubmit" value="SUBMIT" id="CmdSubmit" style="display:inline"/>
		            	</td>
		            	<td>
		            		<input type="reset" name="CmdSubmit" value="CLEAR ALL" id="CmdClearAll" style="display:inline" onclick="clearAll();"/>
		            	</td>
		            	<td>
		            		<input type="button" name="CmdCancel" value="EXIT" id="CmdCancel" onclick="Exit()"/>
		            	</td>
	            	</tr>
	            </table>
            </div>
		</td>
	</tr>
</table>

</form>
</body>
</html>