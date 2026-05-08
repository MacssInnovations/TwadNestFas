<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache">
    
    <link href="../../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/View_Receipt_count_Region.js"></script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                     window.close(); 
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
                                 
               
                function nullcheck()
                {
                    var txtCB_Year=document.getElementById("txtCB_Year").value;
                    var txtCB_Month=document.getElementById("txtCB_Month").value;
                    
                    if(txtCB_Year.length!=4 || txtCB_Month.length==0)
                    {
                        alert("Specify the year(4 digit) and month");
                        return false;
                    }
                return true;
                }
                
                
              
                function numbersonly(e)
                {
                    var unicode=e.charCode? e.charCode : e.keyCode;
                   if(unicode==13)
                    {
                      //t.blur();
                      //return true;-------------------- for taking action when press ENTER
                    
                    }
                    if (unicode!=8 && unicode !=9  )
                    {
                        if (unicode<48 || unicode>57 ) 
                            return false 
                    }
                 }
    </script>
    <title>List of Accounting Units </title>
  </head>
  <body class="table" onload="loadyear_month()">
  
  <%
  
  Connection con=null;
  ResultSet rs=null,rs2=null;
  PreparedStatement ps=null,ps2=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
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
    
   
   %> 
    
  <form action="../../../../../../List_of_Accounting_Units.kv" name=frmReport method=post onsubmit="return nullcheck();"> 
    <table width="100%" >
        <tr>
            <td class="tdH"><center><b>List of Accounting Units </b></center></td>
        </tr>
          <tr>
            <td>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr class="table">
                <td>
                  <div align="left">
                   Region Name
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td colspan="4">
                  <div align="left">
                 
                 
                   <select size="1" name="txtRegionId" id="txtRegionId" tabindex="1">
                     <option value="-1">-- All Units --</option>                     
                   <!--    <option value="5000">Head Office </option> -->
                     
                     
                      <%
                    
                      try{
                                ps=con.prepareStatement("select OFFICE_NAME,OFFICE_ID from COM_MST_OFFICES where OFFICE_LEVEL_ID in ('RN','HO') AND OFFICE_STATUS_ID='CR'");
                                rs=ps.executeQuery();
                                while(rs.next())
                                {
                                    out.println("<option value="+rs.getInt("OFFICE_ID")+">"+rs.getString("OFFICE_NAME")+"</option>");
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
              <tr>
                        <td>
                            Report Option:
                        </td>
                        <td colspan="3">
                            <input type=radio name=txtoption id=txtoption value="PDF" checked>PDF
                            <input type=radio name=txtoption id=txtoption value="EXCEL">Excel
                            <input type=radio name=txtoption id=txtoption value="HTML">HTML
                        </td>
                        
                    </tr>
                    <tr>
                        <td colspan=4 class="tdH" align="center">
                        <input type=submit value=Submit >
                        <input type=reset value=Clear>
                        <input type=button value=Exit onclick="closeWindow()">
                        </td>
                    </tr>
                    
                
                </table>
            </td>
           </tr>
        </table>
  
  </form>
 </body>
</html>