<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    <script type="text/javascript" src="../scripts/ajax2.js"></script>
    <script type="text/javascript">
    function changeTableContent()
    {
      
      //alert("muruga");
      //var ahc=document.form2.txtahc.value;
      if(document.form2.supId.value=="" || document.form2.supName.value=="" || document.form2.sup_Addr.value=="")
      {
        alert("Please enter the values");
      }
      else
      {
      var rid=document.form2.txthid.value; 
      //alert(rid);
      var doc=window.opener.document;     
      var row=doc.getElementById(rid);      
      var rcells=row.cells;
      
                       
      rcells.item(1).childNodes[0].value=document.form2.supId.value;
      rcells.item(1).childNodes[1].value=document.form2.supId.value;
      
      rcells.item(2).childNodes[0].value=document.form2.supName.value;
      rcells.item(2).childNodes[1].value=document.form2.supName.value;
      rcells.item(2).childNodes[3].value=document.form2.sup_Addr.value;
      rcells.item(2).childNodes[4].value=document.form2.sup_Addr.value;
      rcells.item(2).childNodes[6].value=document.form2.sup_Email.value;
      rcells.item(2).childNodes[7].value=document.form2.sup_Email.value;
      
      rcells.item(3).childNodes[0].value=document.form2.sup_Phone.value;
      rcells.item(3).childNodes[1].value=document.form2.sup_Phone.value;      
      
      rcells.item(4).childNodes[0].value=document.form2.sup_Fax.value;
      rcells.item(4).childNodes[1].value=document.form2.sup_Fax.value;
      
      closeWindow();
      }
    }
    
    
    function closeWindow()
    {
        self.close();
    }
    </script>
    <!--<link href="../../../../../css/green.css" rel="stylesheet" media="screen"/>-->
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  </head>
  
  <body >
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
    }
    catch(SQLException e)
    {
    }
  }
  catch(Exception e)
  {
  }
  %>
    <form name="form2" method="get">
   
    <%! String SId="";
      String SName="";
      String SAddr="";
      String SPhone="";
      String SFax="";
      String SEmailId="";


%>
    <%! 
    String selectedRow="";
//String ahcode;
String tblid;
String command;

  %>

   

    <% 
    try
    {
    selectedRow=request.getParameter("rowid"); 
    SId=request.getParameter("SupId");
    SName=request.getParameter("SupName");
    
    SAddr=request.getParameter("SupAddr");
    SPhone=request.getParameter("SupPhone");
    SFax=request.getParameter("SupFax");
    SEmailId=request.getParameter("SupEmail");
    }
    catch(Exception nfe)
    {}
%>   
    
      <input type="HIDDEN" name="txthid" value=<%=selectedRow%>>
      <H3 align="center">Supplier's Sub-Ledger Maintenance System</H3>
      <table cellspacing="3" cellpadding="2" border="1" width="100%">
        <tr>
          <td>Supplier's Id</td>
          <td>
            <input type="text" name="supId" maxlength="6" value=<%=SId%>>
          </td>
        </tr>
        <tr>
          <td>Name of the Supplier</td>
          <td>
            <input type="text" name="supName" size="25" value=<%=SName%>>
          </td>
        </tr>
        <tr>
          <td>Address</td>
          <td>
            <textarea cols="25" rows="7" name="sup_Addr"><%=SAddr%></textarea>
          </td>
        </tr>
        <tr>
          <td>Phone</td>
          <td>
            <input type="text" name="sup_Phone" value=<%=SPhone%>>
          </td>
        </tr>
        <tr>
          <td>Fax</td>
          <td>
            <input type="text" name="sup_Fax" value=<%=SFax%>>
          </td>
        </tr>
        <tr>
          <td>Email Id</td>
          <td>
            <input type="text" name="sup_Email" value=<%=SEmailId%>>
          </td>
        </tr>
        <tr>
        <td>
        <input type="BUTTON" value="Update" onclick="changeTableContent()">
        <input type="BUTTON" value="Cancel" onclick="closeWindow()">
          
        </td>
        </tr>
      </table>
      <P>&nbsp;</P>
      <P>&nbsp;</P>
      <P>&nbsp;</P>
    </form>
  </body>
</html>

