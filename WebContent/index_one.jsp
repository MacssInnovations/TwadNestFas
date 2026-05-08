<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>TWAD Board Integrated E-Governance System</title>
<link href="css/newstyle.css" rel="stylesheet" type="text/css" />
</head>
<%
String[] a=request.getRequestURL().toString().split("//");

String[] b=a[1].split("/");
String hrmsUrl="https://"+b[0]+"/hrms";
String pensionUrl="https://"+b[0]+"/pension";
String payrollUrl="https://"+b[0]+"/payroll";

String pmsUrl="https://"+b[0]+"/twadphase2pms";
String dcbUrl="https://"+b[0]+"/twadphase2dcb";
String wqsUrl="https://"+b[0]+"/twadphase2wqs";

String onlineUrl="https://"+b[0]+"/twadonline/index.jsp";
%>
<body background="images/pagebg.jpg" style="background-repeat:repeat-x">
<table width="979" border="0" align="center" cellpadding="0" cellspacing="0">

  <tr>
    <td align="center" valign="top" background="images/topbg.jpg" style="background-repeat:no-repeat;"><table width="80%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="40" colspan="3" align="right" valign="middle" class="toptitle" style="padding-right:10px;">Welcome to TWAD Board Integrated E-Governance System</td>
      </tr>
	<tr><td colspan="3" align="right"><font size="2">Goto </font> <a href=<%=onlineUrl%> style='text-decoration:none'><font size="3"><b>Home</b></font></a></td></tr>
      <tr>
        <td height="130" colspan="3" align="left" valign="middle"><img src="images/logo.png" width="495" height="101" /></td>
      </tr>
      <tr>
        <td colspan="3"><div align="justify">The Intranet system for TWAD Board facilitates centralized data storage and retrieval besides information analysis pertaining to Project Monitoring, Financial Accounting and HR Management. This system will be made available to all the offices of TWAD Board through a network connecting the Head Office with all the Regions, Circles and Divisions.</div></td>
      </tr>
      <tr>
        <td height="120" colspan="3" align="right" valign="middle"><img src="images/twadnest.png" width="248" height="97" /></td>
      </tr>
      <tr>
        <td colspan="3" align="left" valign="middle"><img src="images/onlineServices.png" width="176" height="35" /></td>
      </tr>
      <tr>
        <td colspan="3">&nbsp;</td>
      </tr>
      <tr>
        <td width="32%" align="left" valign="top"><table width="252" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="282" align="left" valign="bottom"><img src="images/index_box_topbg.png" width="252" height="13" /></td>
          </tr>
          <tr>
            <td align="left" bgcolor="#FFEE9D" class="serviceTitle" style="padding-left:15px;">HRMS</td>
          </tr>
          <tr>
            <td align="left" valign="top" bgcolor="#FFEE9D">
			<ul>
			<li><a href=<%=hrmsUrl%> style='text-decoration:none'><b>HRMS</b></a></li>
			<li><a href=<%=pensionUrl%> style='text-decoration:none'><b>Pension</b></a></li>
			<li><a href=<%=payrollUrl%> style='text-decoration:none'><b>Pay Roll</b></a></li>
			          
			  <br></br>
			</ul>			</td>
          </tr>
          <tr>
            <td align="left" valign="top"><img src="images/index_box_bottombg.png" width="252" height="13" /></td>
          </tr>
        </table></td>
        <td width="36%" align="center" valign="top"><table width="252" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="282" align="left" valign="bottom"><img src="images/index_box_topbg.png" width="252" height="13" /></td>
          </tr>
          <tr>
            <td align="left" bgcolor="#FFEE9D" class="serviceTitle" style="padding-left:15px;">FAS</td>
          </tr>
          <tr>
            <td align="left" valign="top" bgcolor="#FFEE9D"><ul>
              <li>FAS Online</li>
              
              <br></br>
              
            </ul>            </td>
          </tr>
          <tr>
            <td align="left" valign="top"><img src="images/index_box_bottombg.png" width="252" height="13" /></td>
          </tr>
        </table></td>
        <td width="32%" align="right" valign="top"><table width="252" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="282" align="left" valign="bottom"><img src="images/index_box_topbg.png" width="252" height="13" /></td>
          </tr>
          <tr>
            <td align="left" bgcolor="#FFEE9D" class="serviceTitle" style="padding-left:15px;">PMS</td>
          </tr>
          <tr>
            <td align="left" valign="top" bgcolor="#FFEE9D"><ul>
              <li><a href=<%=pmsUrl%> style='text-decoration:none'><b>Scheme Masters</b></a></li>
              <li><a href=<%=dcbUrl%> style='text-decoration:none'><b>DCB</b></a> &nbsp;&nbsp;&nbsp;&nbsp;<a href=<%=dcbUrl%> style='text-decoration:none'><b>AME</b></a></li>
              <li><a href=<%=pmsUrl%> style='text-decoration:none'><b>Tenders</b></a></li>
               <li><a href=<%=wqsUrl%> style='text-decoration:none'><b>WQS</b></a></li>
             
            </ul>            </td>
          </tr>
          <tr>
            <td align="left" valign="top"><img src="images/index_box_bottombg.png" width="252" height="13" /></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td colspan="3">&nbsp;</td>
      </tr>
      
    </table></td>
  </tr>
  <tr>
    <td height="60" align="center" valign="middle" background="images/footerbg.jpg" class="footer" style="background-repeat:no-repeat;">Website designed and hosted by <br />
      <a href="http://www.tn.nic.in" target="_blank" class="footer">National Informatics Centre, Chennai</a></td>
  </tr>
</table>
</body>
</html>