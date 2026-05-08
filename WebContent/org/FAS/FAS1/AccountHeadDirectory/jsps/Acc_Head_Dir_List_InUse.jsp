<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>List of Account Head Directory</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script language="javascript" type="text/javascript" src="../scripts/Acc_Head_Dir_List_InUse.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"    media="screen"/>
      </head>
  <body class="table" onload="disableAllAnchors('numericlinks');disableAllAnchors('alphalinks');">
  <form name="AccList_form" method="POST">
   <%
  
  Connection con=null;
    ResultSet rs=null;
   PreparedStatement ps=null;
    ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
   try
  {
  
             ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rs1.getString("Config.DSN");
            String strhostname=rs1.getString("Config.HOST_NAME");
            String strportno=rs1.getString("Config.PORT_NUMBER");
            String strsid=rs1.getString("Config.SID");
            String strdbusername=rs1.getString("Config.USER_NAME");
            String strdbpassword=rs1.getString("Config.PASSWORD");

            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  %>
  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <h3><strong>List of Account Heads </strong></h3>
          </div>
        </td>
      </tr>
    </table>
     <P align="center">&nbsp;</P>
      <P class="tdTitle"><font size="4" face="Comic Sans MS">Search By: </font>
      <input type="radio" checked name="search" value="MajorGrpMinorGrp" onclick="callServer()"/>&nbsp;Major Group/ Minor Group&nbsp;&nbsp;
      <input type="radio" name="search" value="BeginningLetter" onclick="callServer()"/>Beginning Letter &nbsp;
      <input type="radio" name="search" value="Beginning_Digit" onclick="callServer()"/>Beginning Digit &nbsp;
      <input type="radio" name="search" value="Account_Head_Range" onclick="callServer()"/>Account Head Range
      </P>
       <table cellspacing="1" cellpadding="3" border="1" width="100%">
        <tr>
          <td class="table">Major Group
          <select name="Major_Grp"  id="Major_Grp"  onchange="loadingMinor('loadMinor')">
          <option value="All">All</option>
            <%
                try
                {
                ps=con.prepareStatement("select MAJOR_HEAD_CODE,MAJOR_HEAD_DESC from COM_MST_MAJOR_HEADS");
                rs=ps.executeQuery();
                    while(rs.next())
                    {
                    out.println("<option value="+rs.getString("MAJOR_HEAD_CODE")+">"+rs.getString("MAJOR_HEAD_DESC")+"</option>");
                    }
                } 
                catch(Exception e)
                {
                System.out.println("Exception in Major combo..."+e);
                }
                finally
                {
                rs.close();
                ps.close();
                }   
                %>
          </select>
           &nbsp;Minor Group 
           <select name="Minor_Grp" id="Minor_Grp">
             <option value="All">All</option>
           </select>
           <input type="BUTTON" value="Go" name="MajMin" onclick="searchByMajorMinor();"/>
          </td>
        </tr>
        <tr>
          <td onclick="callServer()" class="table">Account Head Begins with the Letter 
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
         <input type="text" maxlength="8" size="10" name="upper_range" id="upper_range" onblur="sixdigit(this.value);" onkeypress="return numbersonly(event)" disabled style="background-color:rgb(192,192,192);"/>
         To<input type="text" maxlength="8" size="10" name="lower_range" id="lower_range" onblur="sixdigit(this.value);" onkeypress="return numbersonly(event)" disabled style="background-color:rgb(192,192,192);"/>
			<input type="hidden" name="usagestatus" id="usagestatus" value="InUse"/>
          <input type="BUTTON" value="Go" name="Range" id="Range" onclick="searchByRange()"/></td>
        </tr>
        
      </table>
     <!-- <iframe id="ListingPane"  width="100%" height="300px" >
      </iframe>-->
      
      <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <th>
              Select
            </th>
            <th>
             Account Head Code
            </th>
            <th>
             Account Head Name
            </th>
            <th>
             Major Group
            </th>
            <th>
             Minor Group
            </th>
            <th>
             Balance Type
            </th>
          </tr>
          <tbody id="tbody" class="table">
          
         
          </tbody>
        </table>
           <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
            <tr>
                <td>
                    <table align="center" cellspacing="3" cellpadding="2" border="1"  width="100%">
                     <tr class="tdH">
                        <td>
                            <table align="center" cellspacing="3" cellpadding="2"  border="0" width="100%">
                                <tr>
                                    <td width="30%">
                                        <div align="left">
                                            <div id="divpre" style="display:none"></div>
                                        </div>
                                    </td>
                                    <td width="40%">
                                        <div align="center">
                                            <table border="0">
                                                <tr>
                                                    <td>
                                                        <div id="divcmbpage" style="display:none">
                                                        Page&nbsp;&nbsp;<select name="cmbpage"  id="cmbpage" onchange="changepage()"></select>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div id="divpage"></div>
                                                    </td>
                                                  </tr>
                                                </table>
                                            </div>
                                    </td>
                                    <td width="30%">
                                        <div align="right">
                                                <div id="divnext" style="display:none"></div>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                 </table>
        </td>
    </tr>
    
      
      </table>
         <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
  
      <tr class="tdH">
      <td>
          <div align="center">
      <input type="button" id="cmdsubmit" name="Submit" value="SUBMIT" onclick="btnsubmit()">
         <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="btncancel()">
      </div>
      </td>
      </tr>
      
      </table>
      </form>
  </body>
</html>