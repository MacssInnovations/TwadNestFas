<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>A52 Register for Closing Balance Report Abstract</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
  
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
     <!-- to avoid future date the above script used-->
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
     
   <script type="text/javascript" src="../scripts/A52_Register_Abstract_Report.js"></script>

     <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
                function checkNull(){
                	var financial_year = document.frmA52_Register_Abstract.cmbFinancialYear.value; 
                	
                	   	if(financial_year==""){
                		   alert("select Finanical year");
                		   return false;
                	}
                	   	return true;
                }
    </script>
  </head>
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');loadUnitsDelete();" >
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">A52 Register for Closing Balance Abstract Report</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmA52_Register_Abstract" id="frmA52_Register_Abstract" action="../../../../../A52_Register_Abstract_Report" method=post >
                  
  <%
  
      Connection con=null;
      ResultSet rs=null,rs2=null;
      PreparedStatement ps=null,ps2=null;
      ResultSet results=null;
      ResultSet results1=null;
      ResultSet results2=null;
       Statement statement=null;
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
     int bankid=0;
     
    //int empid=9315;
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
            System.out.println("off id.. emp id"+oid+".."+empid);     
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
   
    
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
        
                        <tr class="table1">
						<td>
						  <div align="left">Accounting Unit Code <font color="#ff2121">*</font>          </div></td>
						<td>
						  <div align="left">
						    <select size="1" name="cmbAcc_UnitCode"
							id="cmbAcc_UnitCode" tabindex="1"
							onchange="checkOffice();">
				            </select>
				        </div></td>
					</tr>
         <tr class="table">
		             <td class="table">
		                <div align="left">
		                        Options<font color="#ff2121">*</font>
		              </div>
		              </td>
		              <td colspan="2">
		               <div align="left">
		              <input type=radio name="allid" id=allid value="All" checked="checked" onclick="loadUnitsDelete();">ALL
                      <input type=radio name=allid id=allid value="P" onclick="loadUnits();">Office Wise
                      </div>
		              </td>
              </tr>
         	 <tr class="table">
              <td class="table">
                <div align="left">
                        Accounting Unit Rendered For<font color="#ff2121">*</font>
              </div>
              </td>
              <td colspan="2">
               <div align="left">
                    <select name="unit_rendered" id="unit_rendered" >
                    <option value="">--All Units--</option>
                 
                   </select>
                    </div>
              </td>
              </tr>
           <tr align="left">
              <td class="table">
                <div align="left">
                         Financial Year <font color="#ff2121">*</font>
              </div>
              </td>
              <td colspan="2">
                    <select name="cmbFinancialYear" id="cmbFinancialYear" >
                    <option value="">--Select Year--</option>
                    <option value="2012-13">2012-13</option>
                    <option value="2013-14">2013-14</option>
                    
                
                    </select>
              </td>
              </tr>
          
             
               <tr class="table">
                        <td>
                            Report Option:
                        </td>
                        <td colspan="3">
                            <input type=radio name=txtoption id=txtoption value="PDF" checked>PDF
                            <input type=radio name=txtoption id=txtoption value="EXCEL">Excel
                            <input type=radio name=txtoption id=txtoption value="HTML">HTML
                        </td>                        
                    </tr>
            <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
				          <td colspan="3" class="table">
				           <input type="submit" name="cmdsubmit" value="SUBMIT"
				                   id="cmdsubmit" "/>
				            <input type="button" name="CmdExit" value="EXIT" onclick="closeWindow()">
				          </td>
				 </tr>
                </table>
                </div>
              </td>
            </tr>
         </table>
    </form></body>
</html>