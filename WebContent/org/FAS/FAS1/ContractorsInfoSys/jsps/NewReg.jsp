<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>New Registration</title>
<link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
<script  language="javascript" src="../scripts/AjaxNewReg.js"></script>
<script language="javascript" src="../scripts/ValidateNewReg.js"></script>
<script language="javascript">

function radvalue()
{


if(document.frmNewRegn.radio1[0].checked)
{
document.frmNewRegn.txtContId.disabled=false;
}

else{

document.frmNewRegn.txtContId.disabled=true;
my_window= window.open("LoadNewRegPop.jsp","mywindow1","status=1,height=750,width=750,resizable"); 
my_window.moveTo(250,250); 

}         
}


</script>
<script>
function loadDate()
   {
     var today = new Date()
     var year = today.getYear()
     if(year < 1000)
     {
      year += 1900
     }
     document.frmNewRegn.DateOfRegn.value=today.getDate() + "/" + (today.getMonth()+1) +  "/" + (year+"");
}


function loadYear()
   {
     var today = new Date()
     var year = today.getYear()
     if(year < 1000)
     {
      year += 1900
     }
     document.frmNewRegn.txtYear.value= today.getYear();
}
 function regCheck()
 {
 alert("Registration number for this Contractor  will be generated on creation of Receipt Form");
 }


</script>

</head>

<body onload="loadDate();loadYear()">
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

<form name="frmNewRegn" method="post" action="../../../../../ServletReg.view" onsubmit="return nullCheck();">


<table cellspacing="3" cellpadding="2" border="1" width="75%" class="table" align="center">
  <tr>
    <td colspan="2" class="td">
      <DIV align="center">
        <STRONG>New Registration</STRONG>
      </DIV>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <STRONG>Contractor Details: </STRONG>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="radio" name="radio1" value="New" onclick="radvalue()" checked/>New Details 
    <input type="radio" name="radio1" value="Existing" onclick="radvalue()"/>Existing Details</td>
  </tr>
  <tr>
    <td>
      <FONT class="td2">Contractor Id</FONT> 
      <FONT color="#ff0000">*</FONT>    </td>
    <td>
      <input type="text" name="txtContId" maxlength="7" onkeyup="isInteger(this,event)" onblur="Verify()"/>
      <input type="HIDDEN" name="txtContId1" id="txtContId1">
    </td>
  </tr>
  <tr>
    <td>
      <FONT class="td2">Contractor Name</FONT> 
      <FONT color="#ff3300">*</FONT>    </td>
    <td>
      <input type="text" name="txtContName"/>
    </td>
  </tr>
  <tr>
    <td>
      <FONT class="td2">Name Of the Company/Firm</FONT> 
      <FONT color="#ff3300">*</FONT>    </td>
    <td>
      <input type="text" name="txtCompName"/>
    </td>
  </tr>
  <tr>
    <td>
      <FONT class="td2">Address</FONT> 
      <FONT color="#ff3300">*</FONT>    </td>
    <td>
      <P>
        <input type="text" name="txtadd1"/>
      </P>
      <P>
        <input type="text" name="txtadd2"/>
      </P>
      <P>
        <FONT class="td2">City </FONT>&nbsp;&nbsp;&nbsp;&nbsp;: &nbsp;
      <input type="text" maxlength="15" size="15" name="txtadd3"/></P>
      <P>
        <FONT class="td2">District: </FONT>&nbsp;&nbsp;<select name="txtCmbDistrict">
        <option value>--Select Here--</option>
         
       <%
           try
                {
                 results=statement.executeQuery("select District_Code,District_Name from COM_MST_DISTRICTS");
                 while(results.next())
                {
                  out.println("<option value='" + results.getInt("District_Code") + "'>" + results.getString("District_Name") + "</option>");
                  /* int t=results.getInt("District_Code");
                  out.println("<option value='" + t + "'>" + t + "</option>");*/
                 }
                    results.close();
                 }
                  catch(Exception e)
                 {}
        %>


      </select>
      </P>
    </td>
  </tr>
  <tr>
    <td>
      <FONT class="td2">Pincode</FONT> 
      <FONT color="#ff0000">*</FONT>    </td>
    <td>
      <input type="text" name="txtPincode" maxlength="6" size="7" onblur="checkPincode()"/>
    </td>
  </tr>
  <tr>
    <td>
      <FONT class="td2">Phone No</FONT> 
      <FONT color="#ff0000">*</FONT>    </td>
    <td>
      <input type="text" name="txtPhone" maxlength="8" size="8"/>
    </td>
  </tr>
  <tr>
    <td>
      <FONT class="td2">Cell No</FONT>
    </td>
    <td>
      <input type="text" name="txtCellNo" maxlength="10" size="10"/>
    </td>
  </tr>
  <tr>
    <td>
      <FONT class="td2">Email Id</FONT>
    </td>
    <td>
      <input type="text" name="txtEmail"/>
    </td>
  </tr>
  <tr>
    <td>
      <FONT class="td2"><STRONG>Registration details</STRONG></FONT> 
      <FONT color="#ff0000">*</FONT>    </td>
    <td>
      <P>
        <FONT class="td2">Office Id</FONT> / &nbsp;&nbsp;
        <FONT class="td2"> Year</FONT> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/
        <FONT class="td2"> Reg.Sl.No</FONT>
      </P>
      <P>
        <input type="text" name="txtOffId" maxlength="3" size="3" onblur="getClass()"/>
        <input type="HIDDEN" name="txtOffId1" id="txtOffId1">
        <input type="text" name="txtYear" maxlength="4" size="4"/>
        <input type="text" name="txtRegnNo" size="11" onblur="verifyRegNo()"/>
        <input type="HIDDEN" name="txtRegnNo1" id="txtRegnNo1">
      </P>
      <P>&nbsp;</P>
    </td>
  </tr>
  <tr>
    <td>
      <FONT class="td2">Date Of Registration</FONT> 
      <FONT color="#ff0000">*</FONT>    </td>
    <td>
      <input type="text" name="DateOfRegn" maxlength="10" size="8"/>
      <input type="HIDDEN" name="DateOfRegn1" id="DateOfRegn1">
    </td>
  </tr>
  <tr>
    <td>
      <FONT class="td2">Reference File No</FONT> 
      <FONT color="#ff0000">*</FONT>    </td>
    <td>
      <input type="text" name="txtRef_FileNo"/>
      <input type="HIDDEN" name="txtRef_FileNo1" id="txtRef_FileNo1">
    </td>
  </tr>
  <tr>
    <td>
      <FONT class="td2">Entire State Applicable?</FONT> 
      <FONT color="#ff0000">*</FONT>    </td>
    <td>
      <input type="radio" name="State" value="Y" checked/>Yes<input type="radio" name="State" value="N"/>No</td>
  </tr>
  <tr>
    <td>
      <FONT class="td2">Registration Class</FONT> 
      <FONT color="#ff0000">*</FONT>    </td>
    <td>
      <select name="txtClass" onchange="getFees()">
        <option>--Select Here--</option>
      </select>
    </td>
  </tr>
  <tr>
    <td>
      <FONT class="td2">Registration Fees</FONT> 
      <FONT color="#ff0000">*</FONT>    </td>
    <td>
      <input type="text" name="txtRegn_Fees" maxlength="10" size="10" readonly/>
    </td>
  </tr>
  <tr>
    <td>
      <FONT class="td2">Registration No</FONT> 
      <FONT color="#ff0000">*</FONT>    </td>
    <td>
      <input type="text" name="txtRegnNum" maxlength="5" size="5" readonly/>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <DIV align="center">
        <input type="submit" value="Submit" name="cmdSubmit"/>
        <input type="button" value="Cancel" name="Cancel" onclick="self.close()"/>
      </DIV>
    </td>
  </tr>
</table>		
	</form>	
</body>

</html>


