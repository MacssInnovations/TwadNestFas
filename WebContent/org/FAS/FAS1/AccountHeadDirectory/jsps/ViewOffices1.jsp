<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    
    <script type="text/javascript" >
    function assignVal()
    {
    var assign=window.opener.document;
    var assign2=assign.getElementById("accounting_unit_code");
    assign2.value=document.form2.OffName.value;
    self.close();
    }
    
    </script>
    <!--<link href="../../../../../css/green.css" rel="stylesheet" media="screen"/>-->
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  </head>
  <body>
    <form name="form2">
      <P>Select the Office:</P>
      <P>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <select name="OffName">
        <option value="HO">HeadOffice</option>
        <option value="RO">RegionalOffice</option>
        <option value="CO">CircleOffice</option>
      </select>
      </P>
      <P>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="submit" value="OK" name="OK" onclick="assignVal()"/></P>
    </form>
  </body>
</html>


