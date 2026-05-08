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
    
    <script type="text/javascript"
             src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Supplement_Number_Check_4Rpt.js"></script>

    
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
                                 
                //Null check Validation
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
                
                
                ///////////////////////////////////////  Numbers only fields
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
    <title>Supplement TB Generated Units</title>
  </head>
  
  <body class="table" onload="loadyear_month();Suppl_Number_Check_allsup();">
  
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
    
  <form action="../../../../../../TrialBalance_Monitor_SJV.kv" name=frmReport method=post onsubmit="return nullcheck();"> 
    <table width="100%" >
        <tr>
            <td class="tdH"><center><b>Supplement TB Generated Units </b></center></td>
        </tr>
          <tr>
            <td>
                <table border="4" cellspacing="0" cellpadding="0" width="100%">
              <tr class="table">
                <td>
                  <div align="left">
                   Region Name
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td colspan="4">
                  <div align="left">
                    <select size="1" name="txtRegionId" id="txtRegionId" onchange="Suppl_Number_Check_allsup();" tabindex="1">                    
                     <option value="-100">All</option>                     
                     
                      <%
                    
                      try{
                                //ps=con.prepareStatement("select OFFICE_NAME,OFFICE_ID from COM_MST_OFFICES where OFFICE_LEVEL_ID in ('RN','HO')");
                                ps=con.prepareStatement("select OFFICE_NAME,OFFICE_ID from COM_MST_OFFICES where OFFICE_LEVEL_ID in ('RN','HO') and OFFICE_STATUS_ID='CR'");
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
              <tr >
                          <td class="table">
                         
                              Cash Book Year &amp; Month&nbsp;&nbsp; <font color="#ff2121">*</font>
                            </td>
                         <td>
                           <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)" onblur="Suppl_Number_Check_allsup();">
                         
                          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4"  onchange="Suppl_Number_Check_allsup();" >
                          <option value="3">March</option>
                          </select>
                            
                          </td>
                    </tr>
                    
                    
                     <tr align="left">
                       <td class="table">
                          <div align="left">
                            Supplement Number 
                          <font color="#ff2121">*</font>
                         </div>
                        </td>
                        <td>
                    <div>
                    
                 	<!--
                <input type="text" name="txtsupplement_no" id="txtsupplement_no"
                       tabindex="3" maxlength="4" size="5"
                       onkeypress="return numbersonly(event)"></input>                 
                -->
                
                <select name="txtsupplement_no" id="txtsupplement_no" >
                   <option value="" >-- Select Suppl No. -- </option> 
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