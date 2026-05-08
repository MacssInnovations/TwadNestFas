<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    <script language="javascript" type="text/javascript">
   
    function callMySelect2()
    {
     alert("this js");
    }

    function date()
    {
     var today = new Date()
     var year = today.getYear()
     if(year < 1000)
     {
      year += 1900
     }
     document.cash_receipt.date_creation.value=today.getDate() + "/" + (today.getMonth()+1) +  "/" + (year+"");
     document.cash_receipt.cashBkYear.value=year;
     document.cash_receipt.datealone.value=today.getDate();
    /* var monthArray = new Array("January", "February", "March", 
                   "April", "May", "June", "July", "August",
                   "September", "October", "November", "December");*/
   // document.cash_receipt.cashBkMonth.value=(monthArray[today.getMonth()] ); 
    
    document.cash_receipt.cashBkMonth.value=today.getMonth()+1;
    
  /* var dt=today.getDate();
   var mon=monthArray[today.getMonth()];
   alert("date"+dt);
   alert("month"+mon);
   alert("full"+today);
   // date String should b n d dd-mm-yyyy format
  var dtFrmServlet="04-04-2006";
   var Sysdat=new Date(dtFrmServlet);
   
alert("date"+Sysdat);
var Start=new Date("01-04-2006");
var End=new Date("25-04-2006");
alert("start"+Start);

var count=document.cash_receipt.hidden1.value;
alert(count);
   if(Sysdat >Start && Sysdat < End)
   {
   count=++count;
    alert("success"+ count);
    document.cash_receipt.hidden1.value=count;
    alert(document.cash_receipt.hidden1.value);
    }

*/

    }
    
    </script>
    <script language="javascript">
        function clear_focus()
    {
      document.cash_receipt.update.disabled=false;
      document.cash_receipt.ser_no.focus();
      document.cash_receipt.ser_no.value="";
      document.cash_receipt.acct_head_code.selectedIndex.value="";
      document.cash_receipt.sltype.selectedIndex.value="";
      document.cash_receipt.slcode.selectedIndex.value="";
      document.cash_receipt.received_from.value="";
      document.cash_receipt.credit_debit.selectedIndex.value="";
      document.cash_receipt.amount.value="";
      document.cash_receipt.particulars.value="";
      
      
      
    }
    
    
    
    
    

    </script>
    <script language="javascript" src="../scripts/cashAjax.js"></script>
     <script language="javascript" src="../scripts/Cash_Receipt_Functions.js"></script>
      <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  </head>
  <body onload="date();RecNoGeneration()" class="table">
    
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
  <div class="tdH"><center><h3>Cash Receipt System</h3></center></div>
  
    <form name="cash_receipt" method="post" action="../../../../../ServletCashReceipt.view"  >
      
      <table cellspacing="2" cellpadding="3" border="1" width="100%" class="table">
        <tr>
          <td >Office Code</td>
          <td>
            <input type="text" name="offcode" maxlength="6" size="10"/>
          </td>
        </tr>
        <tr>
          <td>Office Code for which the accounting is rendered</td>
          <td>
            <input type="text" name="accountingUnitCode" maxlength="6" size="10"/>
            <input type="BUTTON" value="Select Office" name="selOff" onclick="popWindow()"/>
          </td>
        </tr>
        <tr>
          <td>Date</td>
          <td>
            <input type="text" size="10" name="date_creation"/>dd/mm/yyyy
          </td>
        </tr>
        <tr>
          <td>Cash Book Year</td>
          <td>
            <input type="text" name="cashBkYear" size="8"/>
          </td>
        </tr>
        <tr>
          <td>Cash Book Month</td>
          <td>
            <input type="text" name="cashBkMonth" size="8"/>
          </td>
        </tr>
        <tr>
          <td>Cash A/C code</td>
          <td>
            <select name="cash_accounting code">
                
                <option value>---Select Here---</option>
                      <% 
                        String query1="select ACCOUNT_HEAD_CODE , ACCOUNT_HEAD_DESC from FAS_ACCOUNT_HEAD_MASTER where NATURE='C'";                                            
                      System.out.println(query1);
                      results=statement.executeQuery(query1);
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
          <td>Reference no. &amp; Date</td>
          <td>
            <input type="text" name="ref_num" size="10"/>
            <input type="text" size="8" name="ref_date"/>dd/mm/yyyy
          </td>
        </tr>
        <tr>
          <td>CR/DB indicator</td>
          <td>
            <select name="cr_db_Indicator">
              <option value="--select here--">--select here--</option>
              <option value="CR">Credit</option>
              <option value="DB">Debit</option>
            </select>
          </td>
        </tr>
        <tr>
          <td>Remarks</td>
          <td>
            <textarea cols="25" rows="7" name="remarks"></textarea>
          </td>
        </tr>
        <tr>
          <td>Total Amount</td>
          <td>
            <input type="text" name="total_amt"/>
          </td>
        </tr>
      </table>
      <P align="center">New Details</P>
      <table cellspacing="3" cellpadding="2" border="1" width="100%" class="table">
        <tr>
          <td height="26" width="50%">SI.No</td>
          <td height="26" width="50%">
            <input type="text" name="ser_no"/>
          </td>
        </tr>
        <tr>
          <td width="50%">A/c Head Code</td>
          <td width="50%">
            <select name="acct_head_code" onclick="callMySelect()">
            
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
          <td width="50%">Sub-Ledger Type</td>
          <td width="50%">
            <select name="sltype" onclick="call();">
            </select>
          </td>
        </tr>
        <tr>
          <td width="50%">Sub-Ledger Code</td>
          <td width="50%">
            <select name="slcode" onchange="setNext()">
            </select>
          </td>
        </tr>
        <tr>
          <td width="50%">Received From</td>
          <td width="50%">
            <input type="text" name="received_from"/>
          </td>
        </tr>
        <tr>
          <td width="50%">CR/DB</td>
          <td width="50%">
            <select name="credit_debit">
              <option value="Value">Credit</option>
              <option value="Value2">Debit</option>
            </select>
          </td>
        </tr>
        <tr>
          <td width="50%">Amount</td>
          <td width="50%">
            <input type="text" name="amount"/>
          </td>
        </tr>
        <tr>
          <td width="50%">Particulars</td>
          <td width="50%">
            <textarea cols="25" rows="7" name="particulars"></textarea>
          </td>
        </tr>
        <tr>
          <td colspan="2" align="center">
            <input type="BUTTON" value="Update" onclick="addCashRec()" name="update"/>
            <input type="BUTTON" value="Cancel" onclick="clear_focus()"/>
          </td>
        </tr>
      </table>
     <DIV align="center">
                  <table  cellspacing="0" cellpadding="0" border="1" width="100%" id="CashReceipt" align="center" class="table">
                    <th>
                      <FONT color="#000000" id="index">Index</FONT>
                      <FONT color="#000000"> </FONT>
                      </th>
                    
                    <th>
                      <FONT color="#000000" id="si_no">SI.No</FONT>
                      <FONT color="#000000"> </FONT>
                      </th>
                    <th>
                      <FONT color="#000000" id="AcctH_C">A/C Head and Code</FONT>
                      <FONT color="#000000"> </FONT>
                      </th>
                    <th>
                      <FONT color="#000000" id="slCode">Sub-Ledger Code</FONT>
                      <FONT color="#000000"> </FONT>
                      </th>
                    <th>
                      <FONT color="#000000" id="cr_db">Credit/Debit</FONT>
                      <FONT color="#000000"> </FONT>
                      </th>
                    <th>
                      <FONT color="#000000" id="amount">Amount</FONT>
                      <FONT color="#000000"> </FONT>
                    </th> 
                    <th>
                      <FONT color="#000000">Delete Record?</FONT>
                      <FONT color="#000000"> </FONT>
                      </th>
                    <th>
                      <FONT color="#000000">Edit Record?</FONT>
                    </th>
                    <tbody id="tblCashReceipt">
                    
                    </tbody>
                  </table>
                  <table cellspacing="2" cellpadding="3" border="1" width="100%">
                    <tr>
                      <td align="right">
                        <input type="BUTTON" value="Add New" onclick="clear_focus()"/>
                      </td>
                    </tr>
                    <tr>
                      <td align="center">
                        <input type="submit" value="Submit"/>
                        <input type="BUTTON" value="Cancel" onclick="clear_focus();"/>
                      </td>
                    </tr>
                  </table>
                  <P>&nbsp;</P>
                  <P>&nbsp;</P>
                  </DIV>
                  
       <input type="HIDDEN" name="datealone"/>         
    </form>
  </body>
</html>
