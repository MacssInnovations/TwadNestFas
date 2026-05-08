<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    
    <script language="javascript" >
    function assignVal()
    {
    var assign=window.opener.document;
    var assign2=assign.getElementById("AccessibleBy");
    assign2.value=document.form2.OffName.value;
    self.close();
    }
    
    </script>
  </head>
  <body>
    <form name="form2">
      <P>Select the Office:</P>
      <P>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <select name="OffName">
        <option value="HeadOffice">HeadOffice</option>
        <option value="RegionalOffice">RegionalOffice</option>
        <option value="CircleOffice">CircleOffice</option>
      </select>
      </P>
      <P>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="submit" value="OK" name="OK" onclick="assignVal()"/></P>
    </form>
  </body>
</html>

