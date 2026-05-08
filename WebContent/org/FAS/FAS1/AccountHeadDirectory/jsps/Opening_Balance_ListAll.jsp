<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Opening Balance List All</title>
    <script type="text/javascript" src="../scripts/Opening_Balance_ListScript.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <link href="../../../../../css/Sample3.css" media="screen" rel="stylesheet"/>
  </head>
  <body><form name="Opening_bal_list" id="Opening_bal_list" title="Opening Balance List All">
  
  <%
  
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    ResultSet rs1=null;
    PreparedStatement ps1=null;
    
     Connection connection=null;

  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
    
   try
  {
  
             ResourceBundle rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rb.getString("Config.DSN");
            String strhostname=rb.getString("Config.HOST_NAME");
            String strportno=rb.getString("Config.PORT_NUMBER");
            String strsid=rb.getString("Config.SID");
            String strdbusername=rb.getString("Config.USER_NAME");
            String strdbpassword=rb.getString("Config.PASSWORD");

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
  
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              List of Opening Balance for General Ledger Account Heads
            </div></td>
        </tr>
        <tr class="table">
          <td>
          <table>
          <tr><td>Major Group 
            <select size="1" name="cmbMajor_grp" id="cmbMajor_grp" onchange="loadminor()">
            <option>----Select-----</option>
            <%
            try
            {
            ps1=con.prepareStatement("select * from COM_MST_MAJOR_HEADS");
            rs1=ps1.executeQuery();
            
            while(rs1.next())
            {
                String MajCode=rs1.getString("MAJOR_HEAD_CODE");
                String MajDesc=rs1.getString("MAJOR_HEAD_DESC");
                out.println("<option value="+MajCode+">"+MajDesc+"("+MajCode+")"+"</option>");
            }
            rs1.close();
            ps1.close();
            }
            
            catch(Exception e)
            {
            System.out.println("exception in fetching major code....."+e);
            }
            %>
            </select>
            </td>
            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Minor Group
            <select size="1" name="cmbMinor_grp" id="cmbMinor_grp" onchange="loadsub()">
            <option>----Select----</option>
            
            </select>
            </td>
            <td>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sub-Group1
            <select size="1" name="cmbSub_grp1" id="cmbSub_grp1"/></td>
             <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sub-Group2
            <select size="1" name="cmbSub_grp2" id="cmbSub_grp2"/>
          </td>
          <td>
           <input type="button" name="cmdgo" value="GO" id="cmdgo" onclick="listcombo()"/>
          </td></tr>
          </table></td>
        </tr>
        <tr class="table">
          <td>Account Head Begins with the Letter
          <%
           
         out.println("&nbsp;&nbsp;");
        out.println("<a href=\"javascript:loadTable('list','a')\">A</a>");
        out.println("<a href=\"javascript:loadTable('list','b')\">B</a>");
        out.println("<a href=\"javascript:loadTable('list','c')\">C</a>");
        out.println("<a href=\"javascript:loadTable('list','d')\">D</a>");
        out.println("<a href=\"javascript:loadTable('list','e')\">E</a>");
        out.println("<a href=\"javascript:loadTable('list','f')\">F</a>");
        out.println("<a href=\"javascript:loadTable('list','g')\">G</a>");
        out.println("<a href=\"javascript:loadTable('list','h')\">H</a>");
        out.println("<a href=\"javascript:loadTable('list','i')\">I</a>");
        out.println("<a href=\"javascript:loadTable('list','j')\">J</a>");
        out.println("<a href=\"javascript:loadTable('list','k')\">K</a>");
        out.println("<a href=\"javascript:loadTable('list','l')\">L</a>");
        out.println("<a href=\"javascript:loadTable('list','m')\">M</a>");
        out.println("<a href=\"javascript:loadTable('list','n')\">N</a>");
        out.println("<a href=\"javascript:loadTable('list','o')\">O</a>");
        out.println("<a href=\"javascript:loadTable('list','p')\">P</a>");
        out.println("<a href=\"javascript:loadTable('list','q')\">Q</a>");
        out.println("<a href=\"javascript:loadTable('list','r')\">R</a>");
        out.println("<a href=\"javascript:loadTable('list','s')\">S</a>");
        out.println("<a href=\"javascript:loadTable('list','t')\">T</a>");
        out.println("<a href=\"javascript:loadTable('list','u')\">U</a>");
        out.println("<a href=\"javascript:loadTable('list','v')\">V</a>");
        out.println("<a href=\"javascript:loadTable('list','w')\">W</a>");
        out.println("<a href=\"javascript:loadTable('list','x')\">X</a>");
        out.println("<a href=\"javascript:loadTable('list','y')\">Y</a>");
        out.println("<a href=\"javascript:loadTable('list','z')\">Z</a></td>");
        
          %>
        </tr>
        <tr class="table">
          <td>Account Code begins with the Digit
          <%
           
         out.println("&nbsp;&nbsp;");
        out.println("<a href=\"javascript:loadTableNO('listNO','1')\">1</a>");
        out.println("<a href=\"javascript:loadTableNO('listNO','2')\">2</a>");
        out.println("<a href=\"javascript:loadTableNO('listNO','3')\">3</a>");
        out.println("<a href=\"javascript:loadTableNO('listNO','4')\">4</a>");
        out.println("<a href=\"javascript:loadTableNO('listNO','5')\">5</a>");
        out.println("<a href=\"javascript:loadTableNO('listNO','6')\">6</a>");
        out.println("<a href=\"javascript:loadTableNO('listNO','7')\">7</a>");
        out.println("<a href=\"javascript:loadTableNO('listNO','8')\">8</a>");
        out.println("<a href=\"javascript:loadTableNO('listNO','9')\">9</a></td>");
          %>
        </tr>
        <tr class="table">
          <td>Account Code Range from 
            <input type="text" name="txtFrom" id="txtFrom" maxlength="6"
                   size="6"/>
             To &nbsp;
            <input type="text" name="txtTo" maxlength="6" size="6" id="txtTo"/>
            <input type="button" name="cmdGo" value="GO" id="cmdGo" onclick="listRange()"/>
          </td>
        </tr>
        <tr class="table">
          <td>Major Group
            <input type="text" name="txtMajorGroup" id="txtMajorGroup"
                   maxlength="15" size="15"/>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Minor Group
            <input type="text" name="txtMinorGroup" maxlength="15" size="15"
                   id="txtMinorGroup"/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sub-Group1
            <input type="text" name="txtSubGrp1" maxlength="10" size="10"
                   id="txtSubGrp1"/>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sub-Group2
            <input type="text" name="txtSubGrp2" maxlength="10" size="10"
                   id="txtSubGrp2"/>
          </td>
        </tr>
      </table>
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
       <tr class="tdH">
       <th>
       A/c Code
       </th>
       <th>
       A/c Head
       </th>
       <th>
       Upto Date CR Balance
       </th>
      
       <th>
      Upto Date DR Balance
       </th>
       <th>
      Curr Year CR Balance
       </th>
       <th>
       Curr Year DR Balance
       </th>
      
       </tr>
       <tbody id="tb" class="table" align="left">
          
          
          </tbody>
       </table>
      
    </form></body>
</html>