<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache">
    <title>Annexure IVa</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script language="javascript" type="text/javascript" src="../scripts/Misc_Rep_AnnexureIVa_JS.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script> 
    <script type="text/javascript" language="javascript">
     function loadyear_month()
     {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
         
         document.miscRep.txtfrom_CB_Year.value=year;
         document.miscRep.txtfrom_CB_Month.value=month;
        
         document.miscRep.txtto_CB_Year.value=year;
         document.miscRep.txtto_CB_Month.value=month;
        
    }
     function chkCurMonth()
     {
    
      var year_cur=document.getElementById("txtto_CB_Year").value; 
      var month_cur=document.getElementById("txtto_CB_Month").value;
      var today= new Date(); 
      var day=today.getDate();
      var month=today.getMonth();
      month=month+1;
      var year=today.getYear();
      if(year < 1900) year += 1900;
    
      if(year_cur==year){
          if(month_cur>month){
     	 document.getElementById("txtto_CB_Month").value=month;
     
          }
     }
     }
    </script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
                
    </script>
    
  </head>
  <body class="table" onload="loadyear_month();">
  
  
  <%
  
  Connection connection=null;
  Statement statement=null;
  ResultSet results=null;
  ResultSet rs=null;
  ResultSet rs2=null;
  PreparedStatement ps=null,ps2=null;
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
        HttpSession session=request.getSession(false);
         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();
    //int empid=10099;
    int  oid=0;             // Office id
    String oname="";        // office name
    
    try
    {
           
            ps=connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
            ps.setInt(1,empid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oid=results.getInt("OFFICE_ID");
                    System.out.println("Office id is:"+oid);
                 }
            results.close();
            ps.close();
            ps=connection.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
            ps.setInt(1,oid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oname=results.getString("OFFICE_NAME");
                  }
            results.close();
            ps.close();
     /* */      
                 
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
  
  <form action="../../../../../../Misc_Rep_AnnexureIVa_Serv" name="miscRep" method="post" onsubmit="return checknull()">
  <table cellspacing="2" cellpadding="3" border="1" width="100%">
  <tr>
        <td class="tdH" colspan="2"><center>Annexure IV(a)</center></td>
  </tr>
  
 <tr align="left">
      <td class="table">
          <div align="right">
              Cash Book Year &amp; Month From
          </div>
      </td>
      <td>
         <div align="left">
              <input type="text" name="txtfrom_CB_Year" id="txtfrom_CB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
              <select name="txtfrom_CB_Month"  id="txtfrom_CB_Month" tabindex="4" >
              <!--<option value="">select the Month</option>-->
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
              To
              <input type="text" name="txtto_CB_Year" id="txtto_CB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
             
              <select name="txtto_CB_Month"  id="txtto_CB_Month" tabindex="4" >
              <!--<option value="">select the Month</option>-->
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
           </div>
      </td>
  </tr>
  <tr class="tdH">
      <td colspan="2">
          <div align="center">
          <input type=submit value=Submit onclick="chkCurMonth();" >
         <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="closeWindow()">
         <!--<input type=button value="Clear" onclick="clear()">-->
      </div>
      </td>
      </tr>
  </table>
  </form>
  </body>
  
</html>