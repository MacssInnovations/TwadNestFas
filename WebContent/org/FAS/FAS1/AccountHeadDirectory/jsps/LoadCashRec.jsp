<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    <script language="javascript" src="../scripts/cashAjax.js"></script>
    <script language="javascript" src="../scripts/cash2.js"></script>
    <script language="javascript">
    function changeTableContent()
    {
      
      //alert("muruga");
      //var ahc=document.form2.txtahc.value;
      if(document.form2.serial_no.value=="" || document.form2.amount.value=="")
      {
        alert("Please enter the values");
      }
      else
      {
      var rid=document.form2.txthid.value; 
      //alert(rid);
      var doc=window.opener.document;     
      var row=doc.getElementById(rid);
      row.id=rid;
      var rcells=row.cells;
      rcells.item(1).firstChild.value=document.form2.serial_no.value;
      
      rcells.item(2).firstChild.value=document.form2.acct_head_code.options[document.form2.acct_head_code.selectedIndex].text;
      rcells.item(3).firstChild.value=document.form2.slcode.options[document.form2.slcode.selectedIndex].text;
    
      rcells.item(4).firstChild.value=document.form2.cr_db.options[document.form2.cr_db.selectedIndex].text;
      rcells.item(5).firstChild.value=document.form2.amount.value;
      
      
      
      closeWindow();
      }
    }
    
    
    function closeWindow()
    {
        self.close();
    }
    </script>
  </head>
  <body>
  <%
  Connection connection=null;
  Statement statement=null;
  ResultSet results=null;
  try
  {
    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    connection = DriverManager.getConnection("jdbc:odbc:fas");
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
   
    <%! String serno="";
String ahc="";
String slc="";
String cd="";
String amt="";
String sltype="";
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
    serno=request.getParameter("serno");
    ahc=request.getParameter("ahc"); 
    slc=request.getParameter("slcode");
    cd=request.getParameter("CD");
    amt=request.getParameter("Amt");
    sltype=request.getParameter("SLType");
    }
    catch(Exception nfe)
    {}
%>   
    
      <H3 align="center">Minor Account Head System</H3>
      <table cellspacing="3" cellpadding="2" border="1" width="100%">
        <tr>
          <td>SI.No</td>
          <td>
            <input type="text" name="serial_no" size="4" value="<%=serno%>"/>
          </td>
        </tr>
        <tr>
          <td>Account Head Code</td>
          <td>
            <select name="acct_head_code"  onclick="callMySelect2()">
                 <option value>---Select Here---</option>
                      <% 
                        String sql="select ACCOUNT_HEAD_CODE , ACCOUNT_HEAD_DESC from FAS_ACCOUNT_HEAD_MASTER";                                            
                      System.out.println(sql);
                      results=statement.executeQuery(sql);
                      while(results.next())
                      {                            
                            out.println("<option value='" + results.getString("ACCOUNT_HEAD_CODE")+ "'>" + results.getString("ACCOUNT_HEAD_DESC") + "</option>");   
                      }
                      results.close();

                %>
            
            </select>
          </td>
        </tr>
        <tr>
          <td>Sub-Ledger Type Code</td>
          <td>
            <select name="sltype" onclick="call2();"/>
          </td>
        </tr>
        <tr>
          <td>Sub-Ledger Code</td>
          <td>
            <select name="slcode" onclick="call();">
              <option value="Ram">Ram</option>
              <option value="Lakshman">Lakshman</option>
              
            </select>
            <input type="HIDDEN" name="txthid" value=<%=selectedRow%>>
          </td>
        </tr>
        <tr>
          <td>CR/DB</td>
          <td>
            <select name="cr_db">
              <option value="CR">Credit</option>
              <option value="DB">Debit</option>
            </select>
          </td>
        </tr>
        <tr>
          <td>Amount</td>
          <td>
            <input type="text" name="amount" size="10" value="<%=amt%>"/>
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
