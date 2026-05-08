<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    <script type="text/javascript" src="../scripts/SupplierSLFunctions.js"></script>
    <script type="text/javascript">
        function clear_focus()
    {
      document.SupplierSL.add.disabled=false;
      document.SupplierSL.supplier_id.focus();
      
      document.SupplierSL.supplier_id.value="";
      document.SupplierSL.supplier_name.value="";
      document.SupplierSL.supplier_address.value="";
      document.SupplierSL.supplier_phone.value="";
      document.SupplierSL.supplier_fax.value="";
      document.SupplierSL.supplier_email_id.value="";
      
      
    }

    </script>
    <!--<link href="../../../../../css/green.css" rel="stylesheet" media="screen"/>-->
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  </head>
  <body bgcolor="rgb(231,240,242)">
    <form name="SupplierSL" action="../../../../../ServletMaster1.view" method="post" class="table">
      <P align="center">
        <FONT face="Times New Roman"><STRONG>Financial Accounting System </STRONG></FONT>
        <EM><FONT face="Times New Roman">(Creation of Supplier Sub-Ledger)</FONT></EM>
      </P>
      <P align="left">
        <FONT face="Times New Roman">Suppliers Sub-Ledger Maintenance System</FONT>
      </P>
      <table cellspacing="3" cellpadding="2" border="1" width="100%">
        <tr>
          <td>Office Code</td>
          <td>
            <input type="text" name="offcode" maxlength="3" size="9"/>
          </td>
        </tr>
        <tr>
          <td>Office Code for which the accounting is rendered</td>
          <td>
            <input type="text" name="accounting_unit_code" maxlength="3" size="9"/>
            <input type="BUTTON" value="Select Office" name="seloff" id="seloff" onclick="popWindow()"/>
          </td>
        </tr>
        <tr>
          <td>Supplier's Id</td>
          <td>
            <input type="text" name="supplier_id" maxlength="6"/>
          </td>
        </tr>
        <tr>
          <td>Name of the Supplier</td>
          <td>
            <input type="text" name="supplier_name" size="25"/>
          </td>
        </tr>
        <tr>
          <td>Address</td>
          <td>
            <textarea cols="25" rows="6" name="supplier_address"></textarea>
          </td>
        </tr>
        <tr>
          <td>Phone</td>
          <td>
            <input type="text" name="supplier_phone"/>
          </td>
        </tr>
        <tr>
          <td>Fax</td>
          <td>
            <input type="text" name="supplier_fax"/>
          </td>
        </tr>
        <tr>
          <td>E-mail Id</td>
          <td>
            <input type="text" name="supplier_email_id"/>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <input type="BUTTON" value="Add" id="add" name="add" onclick="addSupplierSL()"/>
            <input type="BUTTON" value="Cancel" id="cancel1" name="cancel1"/>
          </td>
        </tr>
      </table>
     <DIV align="center">
                  <table  cellspacing="0" cellpadding="0" border="1" width="100%" id="SubGroup" align="center">
                    <th>
                      <FONT color="#000000" id="si_no">SI.No</FONT>
                      <FONT color="#000000"> </FONT>
                      </th>
                    <th>
                      <FONT color="#000000" id="SupplierId">Supplier's Id</FONT>
                      <FONT color="#000000"> </FONT>
                      </th>
                    <th>
                      <FONT color="#000000" id="N_A_E">Name<br>Address<br>E-mail Id</FONT>
                      <FONT color="#000000"> </FONT>
                      </th>
                    <th>
                      <FONT color="#000000" id="Phonr">Phone</FONT>
                      <FONT color="#000000"> </FONT>
                      </th>  
                    <th>
                      <FONT color="#000000" id="Fax">Fax</FONT>
                      <FONT color="#000000"> </FONT>
                      </th>
                      
                      
                    <th>
                      <FONT color="#000000">Delete Record?</FONT>
                      <FONT color="#000000"> </FONT>
                      </th>
                    <th>
                      <FONT color="#000000">Edit Record?</FONT>
                    </th>
                    <tbody id="tblSupplierSL">
                    
                    </tbody>
                  </table>
                  <table cellspacing="3" cellpadding="2" border="1" width="100%">
                    <tr>
                      <td>
                        <DIV align="right">
                          <input type="BUTTON" value="Add New" name="addnew" id="addnew" onclick="clear_focus()"/>
                        </DIV>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <DIV align="center">
                          <input type="Submit" value="Submit" id="submit2" name="submit2"/>
                          <input type="reset" value="Cancel" name="cancel2" id="cancel2"/>
                        </DIV>
                      </td>
                    </tr>
                  </table>
                  <P>&nbsp;</P>
                </DIV>
    </form>
  </body>
</html>
