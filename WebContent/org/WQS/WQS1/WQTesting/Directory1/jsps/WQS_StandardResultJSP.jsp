<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_StandardResultJSP</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script language="javascript" src="../scripts/WQS_StandardResultJS.js" type="text/javascript">
    </script>
  </head>
  <body onload="loading()">
    <form name="StdResult">
    <table cellspacing="3" cellpadding="2" border="1" width="90%"
             align="center">
        <tr class="table">
            <td>
           <table cellspacing="3" cellpadding="2" border="0" width="100%"
                     align="center">
                <tr class="tdH">
                  <td colspan="2" align="center">
                    <font face="Times New Roman">
                      <strong>Standard Result</strong>
                    </font></td>
                </tr>
                <tr class="table">
                  <td width="46%">
                    <font face="Times New Roman">
                      <strong>Test Purpose</strong>
                    </font></td>
                  <td width="54%">
                    <select name="test_purpose" id="test_purpose" onchange="loading()">
                        <%
                                Connection con=null;
                                Statement st=null;
                                ResultSet rs=null;
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
                                               ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                                               //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                                               Class.forName(strDriver.trim());
                                               con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                                               try
                                               {
                                                    con.clearWarnings();
                                               }
                                               catch(SQLException e)
                                               {
                                                    System.out.println("Exception in creating statement:"+e);
                                               }
                                  }
                                  catch(Exception e)
                                  {
                                     System.out.println("Exception in opening connection:"+e);
                                  }
                                  try
                                  {
                                         String sql="select test_purpose_id,test_purpose from wqs_test_purpose order by test_purpose_id";
                                         st=con.createStatement();
                                         rs=st.executeQuery(sql);
                                         while(rs.next())
                                         {
                                            String test_purpose=rs.getString("test_purpose_id");
                                            if(test_purpose.equalsIgnoreCase("CON")||test_purpose.equalsIgnoreCase("ALUM"))
                                                out.println("<option value='" +rs.getString("test_purpose_id")+"--"+rs.getString("test_purpose")+"'>"+rs.getString("test_purpose")+"</option>"); 
                                            else if(test_purpose.equalsIgnoreCase("DRI"))
                                                out.println("<option value='" +rs.getString("test_purpose_id")+"--"+rs.getString("test_purpose")+"' selected=true>"+rs.getString("test_purpose")+"</option>"); 
                                         }
                                }
                                catch(Exception e)
                                {
                                  System.out.println("Exception in resultset:"+e);
                                }
                        %>
                    </select>
                  </td>
                </tr>   
                <tr class="table">
                      <td colspan="2" align="center"><div id="std_div" style="disply:none">
                        <font face="Times New Roman">
                           <input type=radio id="std" name="std" onclick="changeStd()"> Standard Parameter &nbsp;&nbsp;&nbsp;<input type=radio id="std" name="std" onclick="changeStd()">Non Standard Parameter
                        </font></div></td>
                </tr>
                <tr class="table">
                  <td width="46%">
                    <font face="Times New Roman">
                      <strong>Parameter <font color="Red">*</font></strong>
                    </font></td>
                  <td width="54%">
                    <select name="es" id="es" onchange="checkAvail()">
                        <option value="">--Select Parameter--</option>
                    </select>
                  </td>
                </tr>
                </table>
                <div id="drinking" style="display:block">
                    <table cellspacing="3" cellpadding="2" border="0" width="100%" align="center">
                    <tr class="tdH">
                      <td colspan="2" align="left">
                        <font face="Times New Roman">
                          <strong>Drinking Standards </strong>
                        </font></td>
                    </tr>                   
                    <tr class="table">
                      <td width="46%">
                        <font face="Times New Roman">
                          <strong>Standard Code <font color="Red">*</font></strong>
                        </font></td>
                      <td width="54%">
                        <select name="scode" id="scode" onclick="checkParameter()">
                        <option value="">--select--</option>
                        <option value="BIS">BIS</option>
                        <option value="WHO">WHO</option>
                        <option value="CPHE">CPHE</option>
                        <option value="PRACTICAL">PRACTICAL</option>
                        </select>
                      </td>
                    </tr>
                    <tr class="table">
                      <td width="46%">
                        <font face="Times New Roman">
                          <strong>Desirable Value <font color="Red">*</font></strong>
                        </font></td>
                      <td width="54%">
                        <input type="text" name="dv" maxlength="10" size="10" id="dv" onfocus="checkStandard()"/>
                      </td>
                    </tr>
                    <tr class="table">
                      <td width="46%">
                        <font face="Times New Roman">
                          <strong>Maximum Value <font color="Red">*</font></strong>
                        </font></td>
                      <td width="54%">
                        <input type="text" name="mv" maxlength="10" size="10" id="mv" onfocus="checkDesirable()"/>
                      </td>
                    </tr>
                    <tr class="table">
                      <td colspan="5" align="center" height="36">
                        <input type="button"  value="  Add  " onclick="added('Drinking')" id="add" name="add"/>
                        <input type="button" value="  Update" onclick="upd('Drinking')" id="update" name="update"/>
                        <input type="button" value="  Delete  " onclick="del()" id="delet" name="delet"/>
                        <input type="button" value="  Clear  " onclick="clr()"/>
                      </td>
                      
                    </tr>
                    <tr class="table">
                      <td colspan="2">&nbsp;</td>
                      
                    </tr>
                    <tr class="tdH">
                      <td colspan="2" align="center">
                        <input type="button" name="exit" value="Exit" id="exit" onclick="javascript:self.close();"/>
                      </td>
                    </tr>
                  </table>
                       <table align="center" border="0" width="100%">
                  <tr class="tdTitle"><td colspan="5">Existing Details</td></tr>
                  <tr class="tdH">
                  <th width="20%">Edit</th><th width="20%">Parameter</th><th width="20%">Standard Code</th><th width="20%">Desirable Value</th><th width="20%">Maximum Value</th>
                  </tr>
                  <tbody id="tb" class="table">
                  </tbody>
                  </table>
            </div>    
            <div id="construction" style="display:none">
                    <table cellspacing="3" cellpadding="2" border="0" width="100%" align="center">
                    <tr class="tdH">
                      <td colspan="2" align="left">
                        <font face="Times New Roman">
                          <strong>Permissible Limit for Construction Test</strong>
                        </font></td>
                    </tr>
                    <tr class="table">
                      <td width="46%">
                        <font face="Times New Roman">
                          <strong>Maximum Permissible Limit <font color="Red">*</font></strong>
                        </font></td>
                      <td width="54%">
                        <input type="text" name="perm_limit" size="70" maxlength=100 id="perm_limit" onfocus="checkParameter()"/>
                      </td>
                    </tr>
                    <tr class="table">
                      <td colspan="2">&nbsp;</td>
                      
                    </tr>
                    <tr class="tdH">
                      <td colspan="5" align="center" height="36">
                        <input type="button"  value="  Add  " onclick="added('Construction')" id="cmdAdd" name="cmdAdd"/>
                        <input type="button" value="  Update" onclick="upd('Construction')" id="cmdUpdate" name="cmdUpdate"/>
                        <input type="button" value="  Delete  " onclick="del()" id="cmdDelete" name="cmdDelete"/>
                        <input type="button" value="  Clear  " onclick="clr()"/>
                      </td>              
                    </tr>                    
                    <tr class="tdH">
                      <td colspan="2" align="center">
                        <input type="button" value="Exit" onclick="javascript:self.close();"/>
                      </td>
                    </tr>
                  </table>
                       <table align="center" border="0" width="100%">
                  <tr class="tdTitle"><td colspan="3">Existing Details</td></tr>
                  <tr class="tdH">
                  <th width="20%">Edit</th><th width="20%">Parameter</th><th width="20%">Permissible Limit</th>
                  </tr>
                  <tbody id="con_tb" class="table">
                  </tbody>
                  </table>
            </div>
            <div id="alum" style="display:none">
                <table cellspacing="3" cellpadding="2" border="0" width="100%" align="center">
                    <tr class="tdH">
                      <td colspan="2" align="left">
                        <font face="Times New Roman">
                          <strong>Alum Test Requirements</strong>
                        </font></td>
                    </tr>
                    <tr class="table">
                      <td width="46%">
                        <font face="Times New Roman">
                          <strong>Grade 1 <font color="Red">*</font></strong>
                        </font></td>
                      <td width="54%">
                        <input type="text" name="grade1" size="5" id="grade1" onkeypress="return filter_real(event,this,3,3)" onfocus="checkParameter()"/>
                      </td>
                    </tr>
                     <tr class="table">
                      <td width="46%">
                        <font face="Times New Roman">
                          <strong>Grade 1 <font color="Red">*</font></strong>
                        </font></td>
                      <td width="54%">
                        <input type="text" name="grade2" size="5" onkeypress="return filter_real(event,this,3,3)" id="grade2" onfocus="checkGrade1()"/>
                      </td>
                    </tr>
                     <tr class="table">
                      <td width="46%">
                        <font face="Times New Roman">
                          <strong>Grade 2 <font color="Red">*</font></strong>
                        </font></td>
                      <td width="54%">
                        <input type="text" name="grade3" size="5" id="grade3" onkeypress="return filter_real(event,this,3,3)" onfocus="checkGrade2()"/>
                      </td>
                    </tr>
                    <tr class="table">
                      <td colspan="5" align="center" height="36">
                        <input type="button"  value="  Add  " onclick="added('Alum')" id="cmdAdd1" name="cmdAdd1"/>
                        <input type="button" value="  Update" onclick="upd('Alum')" id="cmdUpdate1" name="cmdUpdate1"/>
                        <input type="button" value="  Delete  " onclick="del()" id="cmdDelete1" name="cmdDelete1"/>
                        <input type="button" value="  Clear  " onclick="clr()"/>
                      </td>              
                    </tr>
                    <tr class="table">
                      <td colspan="2">&nbsp;</td>
                      
                    </tr>
                    <tr class="tdH">
                      <td colspan="2" align="center">
                        <input type="button" value="Exit" onclick="javascript:self.close();"/>
                      </td>
                    </tr>
                  </table>
                  <table align="center" border="0" width="100%">
                      <tr class="tdTitle"><td colspan="5">Existing Details</td></tr>
                      <tr class="tdH">
                      <th width="20%">Edit</th><th width="20%">Parameter</th><th width="20%">Grade 1</th><th width="20%">Grade 2</th><th width="20%">Grade 3</th>
                      </tr>
                      <tbody id="alum_tb" class="table">
                      </tbody>
                  </table>
            </div>
        </td>
      </tr>
    </table>
  </form>
  </body>
</html>