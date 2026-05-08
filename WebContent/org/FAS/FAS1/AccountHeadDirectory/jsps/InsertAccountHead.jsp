<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    <script type="text/javascript" src="../scripts/ajax1.js"></script>
    <script type="text/javascript" src="../scripts/ajax2.js"></script>
    <script type="text/javascript" src="../scripts/functions.js"></script>
    <!--<link href="../../../../../css/green.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/sample.css" rel="stylesheet" media="screen"/>-->
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    
    <script language="javascript" type="text/javascript">
    function date()
    {
     var today = new Date()
     var year = today.getYear()
     if(year < 1000)
     {
      year += 1900
     }
     document.form1.date_creation.value=today.getDate() + "/" + (today.getMonth()+1) +  "/" + (year+"");
     

    }
    </script>
    
    <!--<script language="javascript" src="validate.js"> </script>-->

    <script type="text/javascript">
    function popUpDiv(parent,element_id)
    {
      alert("called : " + element_id);
      var pd=document.getElementById(parent);
      pd.style.zIndex=2; 
      var cd=document.getElementById(element_id);
      cd.style.zIndex=1;
      cd.style.left='10px';
      cd.style.top='10px';
      alert("shall i show");
      cd.style.display="block";
    }
    function hideDiv(element_id)
    {
      var cd=document.getElementById(element_id);
      cd.style.display="none";
    }
    </script>   
    
    
    
   
    
  </head>
  <body onload="date()" bgcolor="rgb(231,240,242)">
  <div id="parentt" style="this.style.z-index:1;subledger.style.z-index:2;position:absolute;top:10px;left:10px;" >
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
      
    //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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

    <form name="form1" action="../../../../../ServletMaster.view" method="post" class="table">
      <DIV align="center">
        <H2>
          <FONT color="#993333" size="3"><FONT face="Tahoma"><FONT face="Times New Roman"><FONT size="4"><STRONG>&nbsp;</STRONG><STRONG><FONT color="#333333">Financial Accounting System</FONT></STRONG></FONT> &nbsp;</FONT></FONT></FONT>
          <EM><FONT color="#993333" size="3"><FONT face="Tahoma"><FONT face="Times New Roman" size="2">(</FONT></FONT><FONT size="2">Creation of New Account Head) </FONT></FONT></EM>
        </H2>
        <H2 align="left">
          <EM><FONT color="#3333ff" size="3"><FONT color="#990000"/></FONT></EM>
          <FONT color="#990000" size="3"><STRONG>
            <U><FONT color="#993333">Account Head Directory Maintenance System</FONT></U>
:           </STRONG>
          </FONT>
        </H2>
        <table cellspacing="0" cellpadding="0" border="1" width="90%">
          <tr style="height:1.0pt;">
            <td height="25" width="30%">
              <DIV align="left">
                <FONT size="2">Head Office Code</FONT> 
              </DIV>
</td>
            <td height="25" width="70%">
              <FONT color="#ff9999">
                <input type="text" name="head_off_code" size="25" value="HO"/>
              </FONT>
            </td>
          </tr>
          <tr>
            <td width="30%" height="26" align="center" >
              <DIV align="left">
                <FONT size="2">Account Head Code *</FONT> 
              </DIV>
</td>
            <td width="70%" height="26">
              <FONT color="#ff9999">
                <input type="text" name="acct_head_code" size="25" onblur="verify()" maxlength="8"/>
              </FONT>
            </td>
          </tr>
          <tr>
            <td width="30%" height="24" align="center">
              <DIV align="left">
                <FONT size="2">Account Head Name *</FONT> 
              </DIV>
</td>
            <td width="70%" height="24">
              <FONT color="#ff9999">
                <input type="text" name="acct_head_des" size="25"/>
              </FONT>
            </td>
          </tr>
          <tr>
            <td width="30%" height="24" align="center">
              <DIV align="left">
                <FONT size="2">Date of Creation *</FONT> 
              </DIV>
</td>
            <td width="70%" height="24">
              
                <input type="text" name="date_creation" size="10"/>(dd/mm/yyyy)
             
            </td>
          </tr>
          <tr>
            <td width="30%" height="20" align="center">
              <DIV align="left">
                <FONT size="2">Major Group *</FONT> 
              </DIV>
</td>
            <td width="70%" height="20">
              <select name="major_grp_code" onchange="callMySelect()">
                <option value>--Select Here--</option>
                <option value="I">Income</option>
                <option value="E">Expenditure</option>
                <option value="A">Assets</option>
                <option value="L">Liabilities</option>
              </select>
            </td>
          </tr>
          <tr>
            <td width="30%" align="center">
              <DIV align="left">
                <FONT size="2">Minor Group *</FONT> 
              </DIV>
</td>
            <td width="70%" >
              <select name="minor_grp_code">
                <option value="--Select here--">--Select here--</option>
              </select>
            </td>
          </tr>
          <tr>
            <td width="30%" height="18" align="center">
              <DIV align="left">
                <FONT size="2">Sub-Group-1 *</FONT> 
              </DIV>
</td>
            <td width="70%" height="18">
              <select name="sub_grp1">
              <option value>--Select Here--</option>
                <option value="LI">LIC</option>
                <option value="MN">MNP</option>
                <option value="AR">ARP</option>
              </select>
              
            </td>
          </tr>
          <tr>
            <td width="30%" align="center">
              <DIV align="left">
                <FONT size="2">Sub-Group-2 *</FONT> 
              </DIV>
</td>
            <td width="70%">
              <select name="sub_grp2">
              <option value>--Select Here--</option>
                <option value="RS">RWS Schemes</option>
                <option value="US">Urban Schemes</option>
                <option value="MS">Maintenance Schemes</option>
              </select>
                  
            </td>
          </tr>
          <tr>
            <td width="30%" align="center">
              <DIV align="left">
                <FONT size="2">Balance Type *</FONT> 
              </DIV>
</td>
            <td width="70%"><input type="radio" name="bal_type1" value="CR" checked/>Credit &nbsp;<input type="radio" name="bal_type1" value="DB"/>Debit &nbsp;<input type="radio" name="bal_type1" value="CD"/>Credit/Debit</td>
          </tr>
          <tr>
            <td width="30%" height="21" align="center" >
              <DIV align="left">
                <FONT size="2">In Use? *</FONT> 
              </DIV>
</td>
            <td width="70%" height="21">
              <input type="radio" name="status1" value="Y" checked onclick="disableFields()"/>Yes &nbsp;<input type="radio" name="status1" value="N" onclick="enableFields()"/>No</td>
          </tr>
          <tr>
            <td width="30%" height="26" style="color:rgb(128,0,0);" align="center">
              <DIV align="left">
                <FONT size="2">If Not in use, when last used</FONT> 
              </DIV>
</td>
            <td width="70%" height="26">
              <input type="text" name="Last_date_used" size="10" disabled/>(dd/mm/yyyy)
            </td>
          </tr>
          <tr>
            <td width="30%" height="26" style="color:rgb(128,0,0);" align="center">
              <DIV align="left">
                <FONT size="2">Reference No. and Date</FONT> 
              </DIV>
</td>
            <td width="70%" height="26">
              <input type="text" maxlength="6" size="6" name="File_Ref_No" disabled/>
              <input type="text" maxlength="10" size="10" name="File_Ref_Date" disabled/>(dd/mm/yyyy)
            </td>
          </tr>
          <tr>
            <td width="30%" align="center">
              <DIV align="left">
                <FONT size="2">Restricted Access *</FONT> 
              </DIV>
</td>
            <td width="70%">
              <input type="radio" name="res_access1" value="Y" onclick="enableMe()" checked/>Yes &nbsp;<input type="radio" name="res_access1"  value="N" onclick="disableMe()"/>No</td>
          </tr>
          <tr>
            <td width="30%" align="center">
              <DIV align="left">
                <FONT size="2">Accessible only by</FONT> 
              </DIV>
</td>
            <td width="70%">
              <input type="text" maxlength="10" size="15" name="AccessibleBy" readonly/>
              <input type="button" value="Select office/ section" onclick="popWindow()" name="selOff"/>
              
            </td>
          </tr>
          <tr>
            <td width="30%" align="center">
              <DIV align="left">
                <FONT size="2">Sub-Ledgers Applicable *</FONT> 
              </DIV>
</td>
            <td width="70%">
              <input type="radio" name="sub_ledg1" value="Y" onclick="parentt.style.zIndex=1; subledger.style.zIndex=2; subledger.style.display='block';"/>Yes &nbsp;&nbsp;<input type="radio" name="sub_ledg1" value="N" onclick="parentt.style.zIndex=2; subledger.style.zIndex=1; subledger.style.display='none';" checked/>No</td>
          </tr>
          <tr>
            <td width="30%" align="center">
              <DIV align="left">
                <FONT size="2">Remarks</FONT> 
              </DIV>
</td>
            <td width="70%">
              <textarea cols="35" rows="4" name="txtRemarks"></textarea>
            </td>
          </tr>
         
            </table>
            </div>
            
            <div id="subledger" class="pupupdiv"><%--as "none", initially the table is disabled --%>
            <DIV align="center">
              <table cellspacing="2" cellpadding="3" border="1" width="90%">
                <tr>
                  <td colspan="4">
                    <STRONG>
                      <center>
                        <FONT size="3">New Applicable Sub-Ledger Types</FONT> 
                      </center>
                    </STRONG>
                  </td>
                </tr>
                <tr>
                  <td colspan="3">Sub-Ledger Type Code</td>
                  <td>
                    <input type="text" name="txt_sltypeCode" maxlength="10" size="10" readonly/>
                  </td>
                </tr>
                <tr>
                  <td colspan="3">Sub-Ledger Type Description</td>
                  <td>
                    <%!  int ahcode; %>
                    <% 
                  try
                  {
                  ahcode=Integer.parseInt(request.getParameter("acct_head_code"));
                  }
                  catch(Exception e)
                  {}
                  %>
                    <select name="txt_sldesc" onchange="displaysubcode()">
                      <option value>---Select Here---</option>
                      <% 
                        String sql="select SUB_LEDGER_TYPE_CODE,SUB_LEDGER_TYPE_DESC from FAS_SUB_LEDGER_TYPE";                                            
                      System.out.println(sql);
                      results=statement.executeQuery(sql);
                      while(results.next())
                      {                            
                            out.println("<option value='" + results.getString("SUB_LEDGER_TYPE_CODE") + "'>" + results.getString("SUB_LEDGER_TYPE_DESC") + "</option>");   
                      }
                      results.close();

                %>
                    </select>
                  </td>
                </tr>
                <tr>
                  <td colspan="4">
                    <input type="button" value="UPDATE" name="update" onclick="addSL();subledger.style.display='none';"/>
                    <input type="button" value="Cancel" name="Cancel2" onclick="subledger.style.display='none'"/>
                  </td>
                </tr>
              </table>
            </DIV>
            
            </div>
                    <P align="left">
                      
                        <b><FONT  color="#993333" size="3"><U>Existing Applicable Sub-Ledger Types: </U></FONT></b>
                      
                    </P>
                
                <DIV align="center">
                  <table  cellspacing="0" cellpadding="0" border="1" width="90%" id="SubLedger" align="center">
                    <th>
                      <FONT color="#000000" id="si_no">SI.No</FONT>
                      <FONT color="#000000"> </FONT>
                      </th>
                    <th>
                      <FONT color="#000000" id="sl">Sub-Ledger Type Code</FONT>
                      <FONT color="#000000"> </FONT>
                      </th>
                    <th>
                      <FONT color="#000000" id="sld">Sub-Ledger Type Description</FONT>
                      <FONT color="#000000"> </FONT>
                      </th>
                    <th>
                      <FONT color="#000000">Delete Record?</FONT>
                      <FONT color="#000000"> </FONT>
                      </th>
                    <th>
                      <FONT color="#000000">Edit Record?</FONT>
                    </th>
                    <tbody id="tblList">
                    
                    </tbody>
                  </table>
                </DIV>
         
          <DIV align="center">
            <table cellspacing="0" cellpadding="0" border="1" width="90%" align="center">
              <tr>
                <td height="34">
                  <center>
                    <input type="BUTTON" value="Add New SL Type" name="add_new" onclick="enable_NewSL();parentt.style.zIndex=1; subledger.style.zIndex=2; subledger.style.display='block';"/>                     
                  </center>
                </td>
              </tr>
              <tr>
              <td align="center" >                  
                    <input type="submit" value=" Submit " name="finalsubmit"/>
                    <input type="reset" value=" Cancel " name="Cancel1"/>
              </td>
              </tr>
            </table>
          </DIV>          
         </form>
        <H2/>
        <H2/>
      </DIV>
   
 </div>   
  </body>
</html>
