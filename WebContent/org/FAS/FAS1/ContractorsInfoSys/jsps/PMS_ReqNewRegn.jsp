<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<!--Script Pages Used = AjaxReqNewRegn.js
    Servlet Used = NewReqRegnServlet to perform Insertion and updation operation
                    Reqregn_Servlet to get the Office Details,Class details,validation,System generated
                    Req Seq No
-->

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false"
	import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252">
<meta http-equiv="cache-control" content="no-cache">

<title>Request New Registration</title>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="../../../../Library/scripts/checkDate.js"></script>
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<!--script type="text/javascript" src="../scripts/CalendarControl.js"></script-->
<script type="text/javascript"
	src="../../../../Library/scripts/CalendarControl.js"></script>
<script language="javascript" type="text/javascript"
	src="../scripts/AjaxReqNewRegn.js"></script>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />

</head>
<!--onload="loadDate();loadYear();getSeqNo();"-->
<body onload="setTimeout('findHeadOffice()',900);">
<%
  Connection connection=null;
  Statement statement=null;
  ResultSet results=null;
  try
  {
     ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rs.getString("Config.DSN");
            String strhostname=rs.getString("Config.HOST_NAME");
            String strportno=rs.getString("Config.PORT_NUMBER");
            String strsid=rs.getString("Config.SID");
            String strdbusername=rs.getString("Config.USER_NAME");
            String strdbpassword=rs.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());

    try
    {
      statement=connection.createStatement();
    }
    catch(SQLException e)
    {
    }
  }
  catch(Exception e)
  {
  }
  %>



<% 
    PreparedStatement ps1=null;
        HttpSession session=request.getSession(false);
         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();
    //int empid=10099;
    int  oid=0;             // Office id
    String oname="";  
    String offadd1="";
    String offadd2="";
    String offadd3="";
    String addr="";// office name
   
    try
    {
           
            ps1=connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
            ps1.setInt(1,empid);
            results=ps1.executeQuery();
                 if(results.next()) 
                 {
                    oid=results.getInt("OFFICE_ID");
                    System.out.println("Office id is:"+oid);
                 }
            results.close();
            ps1.close();
            ps1=connection.prepareStatement("select OFFICE_NAME,OFFICE_ADDRESS1,OFFICE_ADDRESS2,CITY_TOWN_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
            ps1.setInt(1,oid);
            results=ps1.executeQuery();
                 if(results.next()) 
                 {
                    oname=results.getString("OFFICE_NAME");
                    offadd1=results.getString("OFFICE_ADDRESS1");
                    if(offadd1==null)
                        offadd1="";
                    offadd2=results.getString("OFFICE_ADDRESS2");
                    if(offadd2==null)
                        offadd2="";
                    offadd3=results.getString("CITY_TOWN_NAME");
                    if(offadd3==null)
                        offadd3="";
                    addr=offadd1+"\n"+offadd2+"\n"+offadd3;
                  }
            results.close();
            ps1.close();
          
                 
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>










<form name="frmNewRegn" method="POST"
	action="../../../../../NewReqRegn_Servlet.view">

<table cellspacing="1" cellpadding="2" width="92%" align="center"
	border="1">
	<tr>
		<td class="tdH">
		<div align="center"><strong><font size="4">CONTRACTOR'S
		REGISTRATION DETAILS</font></strong></div>
		</td>
	</tr>
</table>
<table cellspacing="2" cellpadding="3" border="1" align="center"
	width="92%" class="table">
	<!--<tr>
                            <td colspan="2" class="tdH">
                                   
                                     <P>
                                       <FONT size="3"><FONT size="4"><strong>REQUEST FOR NEW REGISTRATION OF CONTRACTORS</strong> </FONT></FONT>
</P>
                                     <P>&nbsp;</P>
                                          </div></td>
                     </tr>-->

	<tr>
		<td colspan="2" class="tdH" align="left">Office Details</td>
	</tr>
	<tr>
		<td>Office ID<font color="#cc3333">*</font></td>
		<td><input type="text" name="txtOffID" id="txtOffID"
			maxlength="4" size="4" value="<%=oid%>" readonly /><select
			name="cmbWing" id="cmbWing" style="visibility: hidden">			
		</select> <input type="HIDDEN" name="htxtOffID" id="htxtOffID" /> <!--img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="Office_List" onclick="jobpopup();"-->

		</td>
	</tr>
	<tr>
		<td>Office Name</td>
		<td><input type="text" name="txtOffName" value="<%=oname%>"
			readonly="readonly" size="40" maxlength="40" /></td>
	</tr>
	<tr>
		<td>Office Address</td>
		<td><textarea rows="4" cols="40" name="txtoffAddress" readonly><%=addr%></textarea>

		</td>
	</tr>
	<tr>
		<td colspan="2" class="tdH" align="left">Contractor Registration
		Details</td>
	</tr>
	<tr>
		<td>Registration Date (dd/mm/yyyy) <font color="#cc3333">*</font>
		</td>
		<td><!--input type="text" name="txtDate" id="txtDate"
                       maxlength="10" size="11"
                       onfocus="javascript:vDateType='3';return chkOffice()  "
                       onkeypress="return calins(event,this);"
                       
                       onblur="if(checkcurdt(this)==true)return checkdt(this);"/-->

		<input type=text name="txtDate" maxlength="10"
			onFocus="return toFocus(); javascript:vDateType='3'"
			onkeypress="return  calins(event,this)"
			onblur="return checkdt(this);"> <img
			src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frmNewRegn.txtDate);"
			alt="Show Calendar"></img> <!--  <img src="../../../../../images/calendr3.gif"
                     onclick="showCalendarControl(document.frmNewRegn.txtDate);"
                     alt="Show Calendar"></img> --></td>
	</tr>
	<tr>
		<td>Registration Sl.No <font color="#cc3333">*</font></td>
		<td><input type="text" name="txtResNo" id="txtResNo"
			onfocus="return chRegDate()" onchange="return verifyRegID()"
			onkeypress="return numbersonly2(event,this);" maxlength="5" size="5" />

		</td>
	</tr>
	<tr>
		<td colspan="2" class="tdH" align="left">Contractor Details</td>
	</tr>
	<tr>
		<td>Contractor ID</td>
		<td><input type="text" name="txtContID" id="txtContID" readonly />(System
		Generated)</td>
	</tr>
	<tr>
		<td>Name of the Contractor/Firm*</td>
		<td><input type="text" name="txtContName" id="txtContName" /></td>
	</tr>

	<tr>
		<td>Address <font color="#cc3333">*</font></td>
		<td><textarea rows=4 cols=40 id="txtadd" name="txtadd"></textarea>

		</td>
	</tr>
	<tr>
		<td>Phone</td>
		<td><input type="text" name="txtPhone" id="txtPhone"
			maxlength="15" size="15" /></td>
	</tr>



	<tr>
		<td>Email ID</td>
		<td><input type="text" name="txtEmail" id="txtEmail"
			onchange="EmailCheck()" ;/></td>
	</tr>


	<tr>
		<td colspan="2" class="tdH" align="left">Details of Registration
		</td>
	</tr>



	<tr>
		<td>Class Of Registration <font color="#cc3333">*</font></td>
		<td><input type="HIDDEN" name="htxtClass" id="htxtClass" /> <select
			size="1" name="txtClass" id="txtClass">
			<option value="0">--Select Here--</option>
			<%                        
                                          try
                {
                PreparedStatement ps=connection.prepareStatement("select REGN_CLASS_ID,REGN_CLASS_DESC from PMS_MST_CON_CLASS");
                ResultSet rs=ps.executeQuery();
                while(rs.next())
                {
                int pos=rs.getInt("REGN_CLASS_ID");
                out.println("<option value="+pos+">"+rs.getString("REGN_CLASS_DESC")+"</option>");
                }
                }
                catch(Exception e)
                {
                System.out.println("Exception in post rank combo..."+e);
                }
                %>
		</select></td>
	</tr>
	<tr>
		<td>Applicable Jurisdiction <font color="#cc3333"></font></td>
		<td><select name="cmbjurisdiction" id="cmbjurisdiction">
			<option value="0">--Select Here--</option>
			<option value="S">State</option>
			<option value="C">Circle</option>
			<option value="D">Division</option>
		</select></td>
	</tr>
	<tr>
		<td>Statewise Coverage Requested? <font color="#cc3333">*</font>
		</td>
		<td><input type="radio" name="State" value="Y" checked="checked" />
		Yes <input type="radio" name="State" value="N" /> No</td>
	</tr>
	<tr>
		<td>Registration Reneval due on (dd/mm/yyyy) <font
			color="#cc3333">*</font></td>
		<td><!--input type="text" name="txtDate_Upto" id="txtDate_Upto"
                       maxlength="10" size="11"/--> <input type=text
			name="txtDate_Upto" id="txtDate_Upto" maxlength="10"
			onFocus="return toFocus(); javascript:vDateType='3'"
			onkeypress="return  calins(event,this)"
			onblur="return checkdt(this);"> <img
			src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frmNewRegn.txtDate_Upto);"
			alt="Show Calendar"></img> <!--      <img src="../../../../../images/calendr3.gif"
                     onclick=" showCalendarControl(document.frmNewRegn.txtDate_Upto);"
                     alt="Show Calendar"></img> --></td>
	</tr>
	<tr>
		<td>Old Registration Code</td>
		<td><input type="text" name="txtAlias"
			onkeypress="return numbersonly2(event,this);" maxlength="15"
			size="15" /></td>
	</tr>
	<tr align="left" class="table">
		<td>Status</td>
		<td><input type="radio" name="txtstatus" id="txtstatus"
			tabindex="15" value="L" checked>Live
		&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name="txtstatus"
			id="txtstatus" value="C">Cancel &nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>


</table>
<div align="center">
<table>
	<tr>

		<td><!--   <input type="submit"
                                                        name="cmdSubmit"
                                                        value="Add"/> -->
		<input type="button" name="cmdSubmit" value="ADD" id="cmdSubmit"
			onclick="doFunction('Add','null')" /></td>
		<td><input type="button" name="cmdUpdate" value="UPDATE"
			id="cmdUpdate" style="display: none"
			onclick="doFunction('Update','null')" /></td>
		<td><input type="button" name="cmdDelete" value="CANCEL"
			id="cmdDelete" style="display: none"
			onclick="doFunction('Cancel','null')" /></td>
		<td><input type="button" name="cmdClear" id="cmdClear"
			value="CLEAR" onclick="clearAll()" /></td>
		<!--td >
                                                 <input type="button"
                                                        name="cmdCancel"
                                                        value="CANCEL"
                                                        onclick="self.close();"/>
                            </td-->
		<td><input type="button" name="CmdList" value=LIST id="CmdList"
			onclick="ListAllBudget()" /></td>
		<td><input type="button" id="Exit" name="Exit" value="EXIT"
			onclick="self.close()"></td>

	</tr>
</table>
</div>
<%
          statement.close();
          connection.close();
          %>
</form>
</body>
</html>