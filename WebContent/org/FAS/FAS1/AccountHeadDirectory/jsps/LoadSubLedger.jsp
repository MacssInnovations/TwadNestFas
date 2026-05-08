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
     // var flag=checkForRedundancy(document.form2.txt_sltypeCode.value); 
      var ahc=document.form2.txtahc.value;
      
      
      var rid=document.form2.txthid.value; 
      //alert(rid);
      var doc=window.opener.document;     
      var row=doc.getElementById(rid);
      row.id=rid;
      var rcells=row.cells;
      rcells.item(1).firstChild.value=document.form2.txt_sltypeCode.value;
      rcells.item(2).firstChild.value=document.form2.txt_sldesc.options[document.form2.txt_sldesc.selectedIndex].text;
      closeWindow();
    }
    
    
    function closeWindow()
    {
        self.close();
    }
    
    function checkForRedundancy(sc)
{
  try
  {
      var tbody=document.getElementById("tblList");
      var rows=tbody.getElementsByTagName("tr");
      
      if(rows.length<=0)
      {
        return true;
      }
        var i;
        var found=false;
        for(i=0;i<rows.length;i++)
        {
            var cells=rows[i].cells;
            if(cells.item(1).firstChild.value==sc)
            {
              found=true;
              break;
            }
        }
        if(found==true)
        {
          return false;
        }
        else
        {
          return true;
        }        
  }
  catch(e)
  {
  alert(e);
  }
  return false;
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
      
   // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
   
    <%! int slcode;
String sldesc; %>
    <%! 
    String selectedRow="";
String ahcode;
String tblid;
String command;
  %>

    <% 
    try
    {
    
   ahcode=request.getParameter("AHC");
   //tblid=request.getParameter("TblId");
   //command=request.getParameter("Command");
   //slcode=Integer.parseInt(request.getParameter("SLTCode"));
  // sldesc=request.getParameter("SLTDesc"); 
 
    }
    catch(NumberFormatException nfe)
    {}
%>   

    <% 
    try
    {
    selectedRow=request.getParameter("rowid"); 
    slcode=Integer.parseInt(request.getParameter("SLTCode"));
sldesc=request.getParameter("SLTDesc"); 
    }
    catch(NumberFormatException nfe)
    {}
%>   
    
      <H3 align="center">SubLedger Types</H3>
      <table cellspacing="3" cellpadding="2" border="1" width="100%">
        <tr>
          <td>Sub-Ledger Type Code</td>
          <td>
            <input type="text" name="txt_sltypeCode" readonly value=<%=slcode%>>
            <input type="HIDDEN" name="txthid" value=<%=selectedRow%>>
          </td>
        </tr>
        <tr>
          <td>Sub-Ledger Type Description</td>
          <td>
            <select onchange="displaysubcode2()" name="txt_sldesc">
              <option value="Value">--Select Here--</option>
                <%
                    
                        System.out.println("Before the query");
                        String sql="select SUB_LEDGER_TYPE_CODE,SUB_LEDGER_TYPE_DESC from FAS_SUB_LEDGER_TYPE"  ;                                                                                  
                      System.out.println(sql);
                      results=statement.executeQuery(sql);
                      while(results.next())
                      {
                            String ss=results.getString("SUB_LEDGER_TYPE_DESC");
                            if(sldesc.equals(ss))
                             
                              out.println("<option value='" + results.getString("SUB_LEDGER_TYPE_CODE") + "' selected >" + ss + "</option>");
                            else
                              out.println("<option value='" + results.getString("SUB_LEDGER_TYPE_CODE") + "'>" + ss + "</option>");                            
   
                      }

               %>
            </select>
          </td>
        </tr>
        <tr>
        <td>
        <input type="BUTTON" value="Update" onclick="changeTableContent()">
        <input type="BUTTON" value="Cancel" onclick="closeWindow()">
          <input type="HIDDEN" name="txtahc" value=<%=ahcode%>>
        </td>
        </tr>
      </table>
      <P>&nbsp;</P>
      <P>&nbsp;</P>
      <P>&nbsp;</P>
    </form>
  </body>
</html>
