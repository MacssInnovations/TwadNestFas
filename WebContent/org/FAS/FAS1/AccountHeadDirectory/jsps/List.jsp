<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    <script language="javascript" type="text/javascript" src="../scripts/ListAjax.js"></script>
    <script language="javascript" type="text/javascript" src="../scripts/UpdateRecords.js"></script>
  </head>
  <body onload="disableAllAnchors('numericlinks');disableAllAnchors('alphalinks');">
    <form name="formList" method="get">
      <H3 align="center">Financial Accounting System</H3>
      <P align="center">
        <STRONG>List of Account Heads Directory</STRONG>
      </P>
      <P align="center">&nbsp;</P>
      <P>Search: 
      <input type="radio" checked name="search" value="MajorGrpMinorGrp" onclick="callServer()"/>&nbsp;Major Group/ Minor Group&nbsp;&nbsp;
      <input type="radio" name="search" value="BeginningLetter" onclick="callServer()"/>Beginning Letter &nbsp;
      <input type="radio" name="search" value="Beginning_Digit" onclick="callServer()"/>Beginning Digit &nbsp;
      <input type="radio" name="search" value="Account_Head_Range" onclick="callServer()"/>Account Head Range
      </P>
      <table cellspacing="3" cellpadding="2" border="1" width="100%">
        <tr>
          <td>Major Group
          <select name="Major_Grp" onblur="MajorValue()">
            <option value="All">All</option>
            <option value="I">Income</option>
            <option value="E">Expenditure</option>
            <option value="A">Assets</option>
            <option value="L">Liabilities</option>
          </select>
           &nbsp;Minor Group 
           <select name="Minor_Grp" onblur="MinorValue()">
             <option value="All">All</option>
             <option value="01">01</option>
             <option value="02">02</option>
             <option value="03">03</option>
             <option value="04">04</option>
             <option value="05">05</option>
             <option value="06">06</option>
           </select>
           <input type="BUTTON" value="Go" name="MajMin" onclick="searchByMajorMinor();"/>
          </td>
        </tr>
        <tr>
          <td onclick="callServer()">Account Head Begins with the Letter 
          <div id="alphalinks">
          <a href="javascript:callLink('A','A')">A</a> &nbsp;
          <a href="javascript:callLink('A','B')">B</a>&nbsp;
          <a href="javascript:callLink('A','C')">C</a>&nbsp;
          <a href="javascript:callLink('A','D')">D</a>&nbsp;
          <a href="javascript:callLink('A','E')">E</a>&nbsp;
          <a href="javascript:callLink('A','F')">F</a>&nbsp;
          <a href="javascript:callLink('A','G')">G</a>&nbsp;
          <a href="javascript:callLink('A','H')">H</a>&nbsp;
          <a href="javascript:callLink('A','I')">I</a>&nbsp;
          <a href="javascript:callLink('A','J')">J</a>&nbsp;
          <a href="javascript:callLink('A','K')">K</a>&nbsp;
          <a href="javascript:callLink('A','L')">L</a>&nbsp;
          
          <a href="javascript:callLink('A','M')">M</a>&nbsp;
          <a href="javascript:callLink('A','N')">N</a>&nbsp;
          <a href="javascript:callLink('A','O')">O</a>&nbsp;
          <a href="javascript:callLink('A','P')">P</a>&nbsp;
          <a href="javascript:callLink('A','Q')">Q</a>&nbsp;
          <a href="javascript:callLink('A','R')">R</a>&nbsp;
          <a href="javascript:callLink('A','S')">S</a>&nbsp;
          <a href="javascript:callLink('A','T')">T</a>&nbsp;
          <a href="javascript:callLink('A','U')">U</a>&nbsp;
          <a href="javascript:callLink('A','V')">V</a>&nbsp;
          <a href="javascript:callLink('A','W')">W</a>&nbsp;
          <a href="javascript:callLink('A','X')">X</a>&nbsp;
          <a href="javascript:callLink('A','Y')">Y</a>&nbsp;
          <a href="javascript:callLink('A','Z')">Z</a>
          </div>
          </td>          
        </tr>
        <tr>
          <td>Account Head Begins with the Digit
          <div id="numericlinks">
          <a href="javascript:callLink('N','1')">1</a>&nbsp;
          <a href="javascript:callLink('N','2')">2</a>&nbsp;
          <a href="javascript:callLink('N','3')">3</a>&nbsp;
          <a href="javascript:callLink('N','4')">4</a>&nbsp;
          <a href="javascript:callLink('N','5')">5</a>&nbsp;
          <a href="javascript:callLink('N','6')">6</a>&nbsp;
          <a href="javascript:callLink('N','7')">7</a>&nbsp;
          <a href="javascript:callLink('N','8')">8</a>&nbsp;
          <a href="javascript:callLink('N','9')">9</a>
          </div>
          </td>
        </tr>
        <tr>
          <td>Account Code Range From 
          <input type="text" size="10" name="upper_range" disabled style="background-color:rgb(192,192,192);"/>To
          <input type="text" size="10" name="lower_range" disabled style="background-color:rgb(192,192,192);"/>

          <input type="BUTTON" value="Go" name="Range" onclick="searchByRange()"/></td>
        </tr>
        <tr>
          <td>Major Group &nbsp;: &nbsp;
          <input type="text" readonly name="MajGrp" size="10"/>Minor Group : 
          <input type="text" readonly name="MinGrp" size="10"/></td>
        </tr>
      </table>
      <iframe id="ListingPane" width="100%" height="300px" >
      </iframe>
      
      
    </form>
  </body>
</html>
