<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Create Employee Basic Details</title>
     <script type="text/javascript" src="../scripts/EmpId_Script.js"></script> 
       <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script> 
      
      
      <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../css/try1.css" rel="stylesheet" media="screen"/>
  </head>
  <body class="table" onload="togetFocus()"     id="bodyid">
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
      
    //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
     Class.forName(strDriver.trim());
     connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());

       try
       {
            statement=connection.createStatement();
            connection.clearWarnings();
       }
       catch(SQLException e)
       {
              //System.out.println("Exception in creating statement:"+e);
       }          
  }
  catch(Exception e)
  {
         //System.out.println("Exception in openeing connection:"+e);
  }  
 %>     
  <form name="frmEmployee" method="get"   class="table">
  <table width="100%">
      
      <tr>
        <td align="center" colspan="2">
         <table border="1px" width="650px">
         
         <tr>
      <td align="center" class="tdH"  colspan="4">
      <b>Create Employee Basic Details</b><input type="HIDDEN" name="txtMode" value="insert">
      </td>      
      </tr>
      <tr>
             <td colspan="2">
                 Temporary Employee Id:
                </td>
            <td colspan="2">
                <input  tabindex="1" type=text name=id id="id"  class="disab" disabled >(System Generated)
                <input  tabindex="1" type=HIDDEN name=EmpId id="EmpId">
            </td>
        </tr>
              <TR>
                     <TD rowspan="2">
                        Employee&nbsp; Name
                     </TD>
                     <TD>
                        Prefix<label style="color:rgb(255,0,0);">*</label>
                     </TD>
                     <TD>
                        <center>
                        Initial
                        </center>
                     </TD>
                     <TD>
                         Name<label style="color:rgb(255,0,0);">*</label>
                     </TD>
            </TR>
            <TR>
                     
                     <TD>
                       <select name="Employee_Prefix" tabindex="2">
                       
                         <option value="Mr" selected>Mr</option>
                         <option value="Mrs">Mrs</option>
                         <option value="Thiru">Thiru</option>
                         <option value="Selvi">Selvi</option>
                       </select>
                     </TD>
              <TD>
              <INPUT tabIndex=2 size=10 maxlength="8" name=Employee_Initial onchange="return toCheck1()" style="TEXT-TRANSFORM:UPPERCASE"  onkeypress="return nonanum(event)"></TD>
              <TD>
              <INPUT tabIndex=2 size=30  maxlength="40" name=Employee_Name onchange="return toCheck()"  onkeypress="return nonanum(event)"></TD>
             
        </TR>
        
    <TR>
             <TD colspan="2">
                   GPF Number
            </TD>
             <TD colspan="2">
                 <INPUT  maxLength=5 size=10 name=Gpf_Number onkeypress="return  numbersonly1(event,this)">
              </TD>
   </TR>
   <tr>
        <td colspan="2" align="left">
            Is Consolidated Staff
        </td>
        <td>
            <input type="radio"  checked="CHECKED"
                            value="Y"
                            name="Consolidate"></input>
                            Yes
            <input type="radio"
                            value="N"
                            name="Consolidate"></input>
                            No
        </td>
   <tr>
    
       <TR>
            <TD colspan="4"  align="right" class="tdH">
             <INPUT  type="Button" value=Submit name=but onclick="callServer('Add','null')">&nbsp;&nbsp;
             <input type="Button" value=" Cancel " name="cmdCancel" onclick="self.close();">
             </TD>
      </TR>
      
         
  </table>
  </td>
  </tr>
 
      
     
 
      
    
    
    </table>
  </form>
  </body>
  </html>
