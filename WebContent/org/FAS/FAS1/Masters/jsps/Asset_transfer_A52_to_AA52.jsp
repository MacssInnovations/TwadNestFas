<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page import="Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver" %>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>Transfer Of Assets from A52 to AA52</title>
<script type="text/javascript" src="../../../../../org/FAS/FAS1/Masters/scripts/Asset_transfer_A52_to_AA52.js"></script>
<script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script>
<script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script> 
<script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen' />
<script language="javascript" type="text/javascript">
                function closeWindow(){                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
</script>

<script type="text/javascript" language="javascript">
	
	
     function loadyear_month()
         {
       		
            var currentTime = new Date();
			var month = currentTime.getMonth() + 1;
			var day = currentTime.getDate();
			var year = currentTime.getFullYear();


 			document.frmtransfer_A52_AA52.updat_dat.value=+day+ "/" + month+"/" +year;
 			
 			
        }
     function closeform()
     {
    	 self.close();
     }
	  </script>
<%
String s=request.getContextPath();
System.out.println("Path of Transfer of Assets ********"+s);
%>
</head><!-- LoadAccountingUnitID('LIST_ALL_UNITS'),setTimeout('common_LoadOfficeCode()',200),callServer('loadMajor');
disp();
--><body onload="disp();">
<form name="frmtransfer_A52_AA52" id="frmtransfer_A52_AA52" action="../../../../../Asset_transfer_A52_to_AA52" method="get">
 <%
  Connection connection=null;
  Statement statement=null;
  ResultSet results=null;
  ResultSet results1=null;
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
		  HttpSession session1 = request.getSession();
		  System.out.println("session obj "+session1.getAttribute("UserProfile"));
		  UserProfile empProfile=(UserProfile)session1.getAttribute("UserProfile");      
		  System.out.println("user id::"+empProfile.getEmployeeId());
		  int empid=empProfile.getEmployeeId();
		  System.out.println("empid"+empid);
		  String user_id=(String)session1.getAttribute("UserId");
		  System.out.println("---------user_id--------"+user_id);

  %>

    

<table cellspacing="2" cellpadding="3" border="1" width="100%">
  <tr>
        <td class="tdH"><center><b>Transfer Of Obsolete/Condemned Assets From A52 To AA52</b></center></td>
  </tr>
  <tr>
    <td>
   <!--<table cellspacing="1" cellpadding="2" border="1" width="100%">
        
                        <tr class="table">
                <td>
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                   
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);" onclick="common_LoadOffice(this.value);">
                  
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
                <select size="1" name="cmbOffice_code" id="cmbOffice_code"
                        tabindex="2"></select>
              </div>
            </td>
          </tr>
           <tr class="table">
              <td>
                <div align="left">
                         Financial Year <font color="#ff2121">*</font>
              </div>
              </td>
              <td colspan="2">
                    <select name="cmbFinancialYear" id="cmbFinancialYear" >
                  <option value="2012-13">2012-13</option>
                  <option value="2013-14">2013-14</option>
                    </select>
              </td>
              </tr>
         ------------------------major asset
                    <tr class="table">
                <td>
                  <div align="left">
                     Major Asset Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                
                    <select size="1" name="cmbmajorasset" id="cmbmajorasset" tabindex="3" ><option value=0>-- Select Major Asset Code --</option>
                     
                    </select>
                  </div>
                </td>
              </tr>
                           
       <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
          <td colspan="3" class="table">
            <input type="button" name="CmdGo" value="Go" id="CmdGo" onclick="callServer('checkStatus')"/>
            
            <input type="reset" name="CmdClear" value="CLEAR ALL"
                   id="CmdClear" onclick="clearAll();"/>
            <input type="button" name="CmdExit" value="EXIT" onclick="closeWindow()">
   
          </td>
        </tr>
         </table>
         </div>
              </td>
            </tr>
        
        </table>
             --><table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%">
                   <tr class="tdH">
                          <th style="font-size: 4mm;">
                            Select
                          </th>
                          <th style="font-size: 4mm;">
                            Asset Code
                          </th>
				      <!--     <th>
                            Physical Location of the Assets
                          </th> -->
                              <th style="font-size: 4mm;">
                           Assets Description
                          </th>
                          <th style="font-size: 4mm;">
                            Physical Verification Date
                          </th>
                          <th style="font-size: 4mm;">
                            No. of items marked as Obsolete
                          </th>
                          <th style="font-size: 4mm;">
                            Depreciation upto date in A52
                          </th>
                          <th style="font-size: 4mm;">
                            Proportionate Depreciation upto date
                            in A52 for the Obsolete items
                          </th>
                          <th style="font-size: 4mm;">
                            Depreciated cost in A52
                          </th>
				       <!--    <th style="font-size: 4mm;">
                            Proportionate Depreciated cost in A52
                          </th> -->
                          <th style="font-size: 4mm;">
                            Closing Balance amount in A52
                          </th>
                          <th style="font-size: 4mm;">
                            Proportionate closing balance amount in A52
                          </th>
                          </tr>
                       <tbody id="tblList" >
            			</tbody>   
      </table>
         </td>
        </tr>
        <tr>
        <td class="tdH" align="center">
				<input type="button" name="CmdAdd" value="SUBMIT" id="CmdAdd" onclick="submi();">
				<input type="button" name="Exit" value="Exit" onclick="closeform();">
			</td>
       
        </tr>
    </table>

</form>
</body>
</html>