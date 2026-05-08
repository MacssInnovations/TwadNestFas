<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_XL_UploadJSP</title>
     <title>File Upload</title>
   <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  </head>
  <body>
   <form name="frmbrowse" id="frmbrowse" method="POST" action="../../../../../../WQS_XL_UploadServ" ENCTYPE="MULTIPART/FORM-DATA">
    <br>
        <table border="1" cellspacing="1" width="100%">
        <tr class="tdH">
            <td  colspan="6" align="center" >
                <div align="center">
                    <b>Select Sample Result Excel File</b>
                </div>
            </td>
        </tr>
        <tr class="table">
            <td colspan="2">
                <div align="left"><b>Choose File</b></div>
            </td> 
            <td colspan="2">
                <input type="file" name="file" id="file">
            </td>
        </tr>
        <tr class="tdH" align="center">
            <td colspan="6"><input type="submit" value="Submit" name="btsubmit" id="btsubmit">&nbsp;
            <input type="button" value="Exit" name="B2" onclick="self.close()"></td>
        </tr>
      </table>
  </form>
 </body>
</html>