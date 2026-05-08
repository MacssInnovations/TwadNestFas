<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <link href="css/Sample3.css" rel="stylesheet" media="screen"/>
    <title>View_district</title>
    <script type="text/javascript" src="dis.js">
    </script>    
  </head>
  <body onload="Get()">
  
    <form method="get" action="" name="district">
<table cellspacing="2" cellpadding="3" border="1" width="60%" align="center">
<tr class="tdH">
                  <th align="center" colspan="2">VIEW DITRICTS</th>
    
     
                   </table>
                   <table cellspacing="3" cellpadding="2" border="1" width="60%"
             align="center" >
  <tr>
  <td class="table">Existing Details</td>
          <td align="right" class="table">
                Page&nbsp;Size&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select name="cmbpagination" onchange="changepagesize()">
                  <option value="5" selected="selected">5</option>
                  <option value="10">10</option>
                  <option value="15">15</option>
                  <option value="20">20</option>
                </select>
           </td>
        </tr>
</table>
<table id="Existing" border="1" width="60%" cellspacing="2" align="center">
<tr class="tdH">
<td  rowspan="2"></td>
<td rowspan="2"><strong>DIS CODE</strong></td>
<td  rowspan="2"><strong>DIS NAME</strong></td>
</tr>

 <tbody id="tblList" class="table">
    </tbody>
    <tr>
    <td colspan="17">
      <table align="center"  cellspacing="3" cellpadding="2" border="0" width="80%" class="tdH">
                    <tr >
                        <td width="30%">
                             <div align="left"> <div id="divpre" style="display:none"></div> </div>
                        </td>
                        <td width="40%">
                             <div align="center"><table border="0"><tr><td> <div id="divcmbpage" style="display:none" ><font color="Black" size="2"><strong>
                             Page&nbsp;&nbsp;<select name="cmbpage" id="cmbpage" onchange="changepage()"></select></strong></font></div></td><td>
                             <div id="divpage"></div></td></tr></table> </div>
                        </td>
                        <td width="30%" >
                             <div align="right" > <div id="divnext" style="display:none"></div> </div>
                        </td>
                    </tr>
                   
      </table>
       </td>
    </tr>
     <tr>
     <td colspan="4">
                <input type="button" name="Exit" id="Exit" value="EXIT"
                   onclick="exitmethod()"/>
                   <div align="center"></div>
                   </td>
                   </tr>
         </table>
        </form>
  </body>
</html>