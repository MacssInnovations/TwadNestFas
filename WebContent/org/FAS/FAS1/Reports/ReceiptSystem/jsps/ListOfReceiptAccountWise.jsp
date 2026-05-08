<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-store,no-cache,ust-revalidate"></meta>
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" />
    <link href="../../../../../../css/Sample3.css" rel='stylesheet'
          media='screen'/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
          
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="../scripts/ListOfReceiptAccountWise.js"></script>
    <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
                function loadyear_month()
                 {
               
                 var today= new Date(); 
                 var day=today.getDate();
                 var month=today.getMonth();
                 month=month+1;
                 var year=today.getYear();
                 if(year < 1900) year += 1900;
               
                document.frmReport.txtCB_Year.value=year
                document.frmReport.txtCB_Month.value=month;
                
                 }
                
                function allOffices(){
                	document.getElementByName("cmbAcc_UnitCode").disabled=document.getElementByName("allOff").checked;
                	
                }
    </script>
    
    <title>Transactions Listings-Account Head Wise</title>
  </head>
  
  
  <body class="table"
        onload="loadyear_month();LoadAccountingUnitID('FOR_LIST_1')">
        
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>        
<%  
  Connection con=null;
  ResultSet rs=null,rs2=null,rs3=null;
  PreparedStatement ps=null,ps2=null,ps3=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
  ResultSet results3=null;
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
   
    <form action="../../../../../../ListOfReceiptAccountWise.con"
          name="frmReport" id="frmReport" method="post"
          onsubmit="return nullcheck();">
      <table width="100%">
        <tr>
          <td class="tdH">
            <center>
              <b>Transactions Listings-Account Head Wise</b>
            </center>
          </td>
        </tr>
        <tr>
          <td>
            
            <table border="0" cellspacing="0" cellpadding="2" width="100%">
            
             <tr class="table">
                <td width="35%" align="left">
                  <div align="left">
                      Offices
                    <font color="#ff2121">*</font>
                  </div>
                </td>                
               <td width="65%" align="left">
                  <div align="left">
                    <input type="checkbox" id="allOff">
                  </div>
                </td>
              </tr>
            
              <tr class="table">
                <td width="35%" align="left">
                  <div align="left">
                      Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>                
               <td width="65%" align="left">
                  <div align="left">
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                            tabindex="1" onchange="common_LoadOffice(this.value);"></select>
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
            </table>    
            
            <hr>
              
            <table width="100%">   
              <tr>
                 <td width="35%" align="left">Major Group</td>
                 <td width="65%" align="left">
                  <select name="Major_Grp" id="Major_Grp"
                          onchange="loadingMinor('loadMinor')">
                          <option value="All">-------- All ---------</option>
                    <%
                        try
                            {
                                ps=con.prepareStatement("select MAJOR_HEAD_CODE,MAJOR_HEAD_DESC from COM_MST_MAJOR_HEADS");
                                rs=ps.executeQuery();
                                while(rs.next())
                                {
                                    out.println("<option value="+rs.getString("MAJOR_HEAD_CODE")+">"+rs.getString("MAJOR_HEAD_DESC")+"</option>");
                                }
                            } 
                        catch(Exception e)
                        {
                            System.out.println("Exception in Major combo..."+e);
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
              <tr>
                <td width="35%" align="left">Minor Group</td>
                <td width="65%" align="left">
                  <select name="Minor_Grp" id="Minor_Grp" onchange="searchByMajorMinor();">
                    <option value="-100">-------- All ---------</option>
                  </select>
                <!--  
                  <input type="BUTTON" value="Go" name="MajMin"
                         onclick="searchByMajorMinor();"/>
                  -->      
                </td>
              </tr>
              <tr>
                 <td width="35%" align="left">Account Head Code</td>
                 <td width="65%" align="left">
                  <select name="cmbAccHeadCode" id="cmbAccHeadCode">
                    <option value="-100">-------- All ---------</option>
                  </select>
                </td>
              </tr>              
            </table>    
            
            <hr>
              
            <table width="100%">   
              <tr>
                 <td width="35%" align="left">
                  Cash Book Year &amp; Month&nbsp;&nbsp;
                  <font color="#ff2121">*</font>
                </td>
                <td width="65%" align="left">
                  <input type="text" name="txtCB_Year" id="txtCB_Year"
                         tabindex="3" maxlength="4" size="5"
                         onkeypress="return numbersonly(event)"></input>
                  <select name="txtCB_Month" id="txtCB_Month" tabindex="4">
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
                  </select>
                </td>
              </tr>
              
              
              
              <tr>
                <td>
                   Date
                  <font color="#ff2121">*</font>
                </td>
              
                <td>
                  From 
                  <input type="text" name="txtfromdate" id="txtfromdate"
                         onkeypress="return  calins(event,this)"
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"></input>
                  <img src="../../../../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.frmReport.txtfromdate);"
                       alt="Show Calendar" height="24" width="19"></img>
                       
                  To 
                  
                  <input type="text" name="txttodate" id="txttodate"
                         onkeypress="return  calins(event,this)"
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"></input>
                  <img src="../../../../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.frmReport.txttodate);"
                       alt="Show Calendar"></img>
                
                </td>                
                
              </tr>
            </table>    
            
            <hr>
              
            <table width="100%">   
              <tr>
                 <td width="35%" align="left">Report Option:</td>
                 <td width="65%" align="left">
                  <input type="radio" name="txtoption" id="txtoption"
                         value="PDF" checked="checked"></input>
                  PDF
                  <input type="radio" name="txtoption" id="txtoption"
                         value="EXCEL"></input>
                  Excel
                  <input type="radio" name="txtoption" id="txtoption"
                         value="HTML"></input>
                  HTML
                </td>
              </tr>
              <tr>
                <td colspan="4" class="tdH" align="center">
                  <input type="submit" value="Submit"></input>
                  <input type="reset" value="Clear"></input>
                  <input type="button" value="Exit" onclick="closeWindow()"></input>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </form></body>
</html>