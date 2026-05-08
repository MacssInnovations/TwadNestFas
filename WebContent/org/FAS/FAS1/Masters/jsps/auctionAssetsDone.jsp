<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page
	import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ page import="Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252" />
<meta http-equiv="cache-control" content="no-cache">
<title>Auction of Asset Done</title>

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
	</script>
<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/Masters/scripts/auctionAssetsDone.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="../../../../../org/Library/scripts/checkDate.js"></script>
<script type="text/javascript"
	src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
<script type="text/javascript"
	src="../../../../Security/scripts/tabpane.js"></script>
</head>
<body onload="callServer('getoffice'),surveyReportNum();">
<%  
      Connection con=null;
      ResultSet rs=null,rs2=null;
      PreparedStatement ps=null,ps2=null;
      ResultSet results=null;    
      Statement statement=null;
      try{
    	  LoadDriver load = new LoadDriver();
    	  con = load.getConnection();    
      }catch(Exception e){
        System.out.println("Exception in connection...."+e);
      }  
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();
     int bankid=0;
     
    //int empid=9315;
    int  oid=0;
    String oname="";
    try{
           
       ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
       ps.setInt(1,empid);
       results=ps.executeQuery();
       if(results.next()){
               oid=results.getInt("OFFICE_ID");
       }
       results.close();
       ps.close();
       ps=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
       ps.setInt(1,oid);
       results=ps.executeQuery();
       if(results.next()){
               oname=results.getString("OFFICE_NAME");
       }
       results.close();
       ps.close();
     /* */      
       System.out.println("off id.. emp id"+oid+".."+empid);     
    }catch(Exception e){
        System.out.println(e);
    }
   
   %>
<!-- ////////////////////// phone master heading ///////////////////////////////////-->
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Auction of Asset Done</font>
		</div>
		</td>
	</tr>
</table>

<form name="auctionAssetDone" id="auctionAssetDone" method="post" action="../../../../../AuctionAssetDone?command=add">
<div class="tab-pane" id="tab-pane-1">
<div class="tab-page">
<h2 class="tab">General</h2>

<div align="center">
<table cellspacing="1" cellpadding="2" border="1" width="100%">

	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>General Details</strong></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Accounting Unit Code <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode" tabindex="1">
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
			<div align="left">Accounting For Office Code <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbOffice_code"
			id="cmbOffice_code" tabindex="2" style="width: 25%;">
			<%
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
                        ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
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

	<tr align="left">
		<td class="table">
		<div align="left">Survey Report No <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
			<select name="surveyReportNo" id="surveyReportNo" style="width: 25%;" onchange="callServer('assetCode')">
				<option value="select">Select</option>
			</select>
		</td>
	</tr>
	<tr align="left">
		<td class="table">
		<div align="left">Auction Date<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
			<input type="text" name="auctionDate" id="auctionDate">
			<img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.auctionAssetDone.auctionDate,1);"
                         alt="Show Calendar"></img> 
		</td>
	</tr>
	<tr align="left">
		<td class="table">
			<div align="left">Auction No <font color="#ff2121">*</font></div>
		</td>
		<td>
			<input type="text" name="auctionNo" id="auctionNo" disabled="disabled">(Auto Generated) 
		</td>
	</tr>
	<tr align="left">
		<td class="table">
		<div align="left">Auctioned at which office<font color="#ff2121">*</font>
		</div>
		</td>
		<td><select name="auctionedOffice" id="auctionedOffice"
			style="width: 25%;">
			<option value="select">Select</option>
		</select></td>
	</tr>	
</table>
</div>
</div>

<div class="tab-page" id="gd">
<h2 class="tab">Details</h2>


<div align="center">
<table cellspacing="1" cellpadding="2" border="1" width="100%">

	<tr>
		<td colspan="2">
		<div id="phone_det_disp">
		<table cellspacing="1" cellpadding="2" border="1" width="100%">
			<tr class="tdTitle">
				<td colspan="2">
				<div align="left"><strong> Details</strong></div>
				</td>
			</tr>


			<tr class="table">
				<td>
				<div align="left">Asset Selected for Auction <font
					color="#ff2121">*</font></div>
				</td>
				<td>
				<div align="left"><select size="1" name="assetAuctioin"
					id="assetAuctioin" style="width: 19%;">
					<option value="select">Select</option>
				</select></div>
				</td>
			</tr>
			<tr class="table" id="tr1">
				<td>
				<div align="left">Reference No <font color="#ff2121">*</font>
				</div>
				</td>
				<td>
				<div align="left"><input type="text" name="referenceNo"
					id="referenceNo" onkeypress="return numbersonly1(event,this)" /></div>
				</td>
			</tr>
			<tr class="table" id="tr2">
				<td>
				<div align="left">Reference Date <!-- <font color="#ff2121">*</font>-->
				</div>
				</td>
				<td>
				<div align="left"><input type="text" name="referenceDate"
					id="referenceDate" /> <img src="../../../../../images/calendr3.gif"
					onclick="showCalendarControl(document.auctionAssetDone.referenceDate,1);"
					alt="Show Calendar"></img>&nbsp;&nbsp;</div>
				</td>
			</tr>
			<tr class="table" id="tr3">
				<td>
				<div align="left">Auctioneer</div>
				</td>
				<td>
				<div align="left"><input type="text" name="auctioneer"
					id="auctioneer" /></div>
				</td>
			</tr>
			<tr class="table">
				<td>
				<div align="left">Auction Amount <font color="#ff2121">*</font>
				</div>
				</td>
				<td>
				<div align="left"><input type="text" name="auctionAmount"
					id="auctionAmount"></div>
				</td>
			</tr>
			<tr class="table">
                <td width="30%">
                  <div align="left">Remarks</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="remarks" id="remarks" cols="50" tabindex="7" rows="4"></textarea>
                  </div>
                </td>
            </tr>
			<tr class="tdTitle">
				<td colspan="2" height="23">
				<div align="center">
				<table border="0">
					<tr>
						<td><input type="button" name="cmdadd" id="cmdadd"
							value="ADD" onclick="addRow()" style="display: block" /></td>
						<td><input type="button" name="cmdupdate" value="UPDATE"
							id="cmdupdate" onclick="updateGrid()" style="display: none" /></td>
						<td><input type="button" name="cmddelete" value="DELETE"
							id="cmddelete" onclick="deleteRow()" disabled="disabled" /></td>
						<td><input type="button" name="cmdclear" value="CLEAR ALL"
							id="cmdclear" onclick="clearall()" /></td>
					</tr>
				</table>
				</div>
				</td>
			</tr>
		</table>
		</div>
		<div id="grid" style="display: block">
		<table id="mytable" cellspacing="3" cellpadding="2" border="1"
			width="100%">

			<tr class="table">
				<th>Select</th>
				<th>Asset Selected for Auction</th>
				<th>Reference No</th>
				<th>Reference Date</th>
				<th>Auctioneer</th>
				<th>Auction Amount</th>
				<th>Remarks</th>
			</tr>

			<tbody id="grid_body" class="table" align="left">
			</tbody>

		</table>
		</div>
		</td>
	</tr>

</table>
</div>
</div>
</div>
<br>

<div align="center">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td>
		<div align="center"><input type="submit" name="butSub"
			id="butSub" value="SUBMIT" /> &nbsp;&nbsp;&nbsp; <input type="button"
			name="butList" id="butList" value="LIST" onclick="auctionAssetList();" />
			&nbsp;&nbsp;&nbsp; <input type="button" name="butCan" id="butCan"
			value="CLEAR" onclick="clear_details();" /> &nbsp;&nbsp;&nbsp; <input
			type="button" name="butExit" id="butExit" value="EXIT"
			onclick="closeWindow();" /></div>
		</td>
	</tr>
</table>
</div>

</form>
</body>
</html>