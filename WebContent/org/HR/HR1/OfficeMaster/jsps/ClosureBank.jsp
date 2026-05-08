
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <meta http-equiv="cache-control" content="no-cache">
      <title>Closure Details</title>

          <link href="../../../../../css/yellow.css" rel="stylesheet" media="screen"/>
          <link href="../../../css/Sample3.css" rel="stylesheet" media="screen"/>
          <script type="text/javascript" src="../scripts/AjaxOfficeBank.js"></script>
          <script type="text/javascript" src="../scripts/controllingOfficeWing.js"></script>
          <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
          <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
          </script> 
    </head>
 <body >
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
    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
   // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

     Class.forName(strDriver.trim());
     connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());


       try
       {
            statement=connection.createStatement();
            connection.clearWarnings();
       }
       catch(SQLException e)
       {
              //System.out.println("Exception in creating statement:"+e);
       }
  }
  catch(Exception e)
  {
         //System.out.println("Exception in openeing connection:"+e);
  }

  %>
 <form name="frmOffice" method="Post" action="../../../../../ServletOfficeBank.con?command=Add">
        <div id="dhtmltooltip"></div>
    <script type="text/javascript" src="test.js"></script>

                <table  cellspacing="1" cellpadding="3"  width="100%" border="1" class="bgbody">
                    <tr>
                        <td class="bgClass">
                            <center><h2>Bank Closure Details</h2></center>
                        </td>
                    </tr>
                    <tr>
                        <td>
                              <table  cellspacing="3" cellpadding="1"  width="100%" >
                                  <tr>
                                      <td>Office_Id</td>
                                      <td>
                                          <table>
                                        <tr>
                                            <td></td>
                                            <td>Office Level</td>
                                            <td><div id="divType1" style="visibility:hidden">Office Type</div></td>
                                            <td><div id="divType2" style="visibility:hidden">Select Office</div></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <input type="text" name="txtOffice_Id"  id="txtOffice_Id" onblur="callServer1('Load','null')" tabindex="1"  maxlength="4"> or Select
                                            </td>
                                            <td>
                                                <SELECT size=1 name=cmbControllingLevel onchange="getOfficesByLevel()">
                                                <option value>
                                                        ----Select
                                                        OfficeLevel----
                                                    </option>
                                                <%
                                                      try
                                                      {
                                                        results=statement.executeQuery("select * from COM_MST_OFFICE_LEVELS");
                                                        while(results.next())
                                                        {
                                                            out.print("<option value='" + results.getString("Office_Level_Id") + "'>" + results.getString("Office_Level_Name") + "</option>");
                                                        }
                                                        results.close();
                                                      }
                                                      catch(Exception e)
                                                      {
                                                      }
                                                %>
                                              </SELECT>
                                              </td>
                                              <td>
                                                <select name="cmbOfficeType" style="visibility:hidden" onchange="getOfficesByType()">
                                                    <option value=0>
                                                        ----Select Office
                                                        Type----
                                                    </option>
                                                    <%
                                                          try
                                                          {
                                                            results=statement.executeQuery("select * from COM_MST_WORK_NATURE");
                                                            while(results.next())
                                                            {
                                                                out.print("<option value='" + results.getString("Work_Nature_Id") + "'>" + results.getString("Work_Nature_Desc") + "</option>");
                                                            }
                                                            results.close();
                                                          }
                                                          catch(Exception e)
                                                          {}
                                                    %>
                                                </select>
                                            </td>
                                            <td>
                                                <select name="cmbSelectOffice" style="visibility:hidden" id="cmbSelectOffice" onchange="selectControllineOffice('office')">
                                                    <option value=0>----Select Office----</option>
                                                </select>
                                            </td>
                                        </tr>
                                        </table>

                                      </td>
                                  </tr>
                                  <tr>
                                      <td><font color="#808080">Name of The Office</font></td>
                                      <td>
                                      <input type="text" name="txtOffice_Name" id="txtOffice_Name" disabled/>
                                      </td>
                                  </tr>
                                  <tr>
                                      <td><font color="#808080">Office Address1</font></td>
                                      <td>
                                            <input type="text" name="txtOffice_Address1" id="txtOffice_Address1" disabled>
                                </td>
                                  </tr>
                                  <tr>
                                      <td><font color="#808080">Office Address2</font></td>
                                      <td>
                                            <input type="text" name="txtOffice_Address2" id="txtOffice_Address2" disabled>
                                </td>
                                  </tr>
                                  <tr>
                                      <td><font color="#808080">Office Address3</font></td>
                                      <td>
                                            <input type="text" name="txtOffice_Address3" id="txtOffice_Address3" disabled>
                                </td>
                                </tr>

                                <tr>
                                    <td colspan=2 class="bgClass">
                                        <b>Details of Bank Account</b>
                                    </td>
                                   </tr>
                                <tr>
                                      <td>
                                        Sl No
                                      </td>
                                      <td>
                                        <input type=text name="txtSl_No" id="txtSl_No" >
                                      </td>
                                </tr>
                                <tr>
                                      <td>
                                        Bank Name
                                      </td>
                                      <td>
                                        <select name="cmbBank" onchange="callServer1('BankId',null)" id="cmbBank" tabindex="2">
                                             <option value=0>--Select Bank Name--</option>
				<%
				       try
				       {
				         results=statement.executeQuery("select * from fas_mst_banks"); 
				         while(results.next()) 
				          {
					      out.print("<option value='" + results.getInt("bank_id") + "'>" + results.getString("bank_name") + "</option>");                      
					  }
					      results.close();
					}
					catch(Exception e)
					{
					}  
				%>
   
                                        </select>
                                      </td>
                                </tr>
                                <tr>
                                    <td>
                                        Name Of The Branch
                                    </td>
                                    <td>
                                        <select name="cmbBranch" id="cmbBranch" onchange="callServer1('Branch',null)" tabindex="3">
                                             <option value=0>--Select Branch Type--</option>
				

                                        </select>
                                    </td>
                                    
                                </tr>
                                
                                  
                                
                                
                                <tr>
                                      <td>
                                        Date Of Closure
                                      </td>
                                      <td>
                                        <input type=text name="txtDateOfClosure" maxlength="10" id="txtDateOfClosure" tabindex="4" onFocus="javascript:vDateType='3'" onKeyUp="DateFormat(this,this.value,event,false,'3')" onBlur="DateFormat(this,this.value,event,true,'3');fun1()">
                                      </td>
                                </tr>
                                <tr>
                                      <td>
                                        Reason
                                      </td>
                                      <td>
                                        <textarea cols="25" rows="5" id="Reason" name="Reason" tabindex="5"></textarea>
                                      </td>
                                </tr>
                                
                                
                                
                                <tr>
                                  <td colspan=2>
                                  </td>
                                </tr>
                                  </table>

                                   <!--<div id="mydiv" name="mydiv"> -->
                                    <table name="Existing" id="Existing"  border="1" width="100%"  style="font-family:arial;">
                                    <tr>
                                            <td colspan=6>
                                                <b>Existing Bank Details</b>
                                            </td>
                                </tr>

                                <tr>
                                            
                                            
                                            <th>Acc No</th>
                                            <th>Account Type</th>
                                            <th>Operational Mode</th>
                                            <th>AccountOpeningDate</th>
                                            <th>Account Head No</th>
                                            <th>InitialDepositAmount</th>
                                            
                                </tr>

                                    <tbody id="tblList" name="tblList">
                                    </tbody>
                                <tr>
                                        <td colspan=6 class="bgClass"><center>
                                            <input type=Submit value=Submit onclick="return nullcheck()" tabindex="6"/>
                                            <input type=button value=Cancel onclick="closeWindow();"/></center>
                                        </td>
                                </tr>
                                  </table>



                        </td>
                    </tr>
              </table>
        </form>
  </body>
</html>

