
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <title>Updating Bank Details</title>

          <link href="../../../../../css/yellow.css" rel="stylesheet" media="screen"/>
          <link href="../../../css/Sample3.css" rel="stylesheet" media="screen"/>
          <script type="text/javascript" src="../scripts/AjaxOfficeUpdateBank.js"></script>
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
    //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

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
 <form name="frmOffice" method="Post" action="../../../../../ServletOfficeUpdateBank.con?command=Add">
        <div id="dhtmltooltip"></div>
    <script type="text/javascript" src="test.js"></script>

                <table  cellspacing="1" cellpadding="3"  width="100%" border="1" class="bgbody">
                    <tr>
                        <td class="bgClass">
                            <center><h3>Update Bank Details</h3></center>
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
                                                <input type="text" name="txtOffice_Id"  onkeyup="isInteger(this,event)" id="txtOffice_Id" onblur="callServer1('Load','null')" tabindex="1" maxlength="4"> or Select
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
                                                <select name="cmbSelectOffice" id="cmbSelectOffice" style="visibility:hidden" onchange="selectControllineOffice('office')">
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
                                        <input type=text name="txtSl_No" id="txtSl_No"  disabled>
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
                                             <option>--Select Branch Type--</option>
				        </select>
                                    </td>
                                    
                                </tr>
                                <tr>
                                      <td>
                                       <font color="#808080"> Branch Address1</font>
                                      </td>
                                      <td>
                                        <input type=text name="txtBank_Address1" id="txtBank_Address1" disabled>
                                        <input type=hidden name="txtBank_Address11" id="txtBank_Address11" >
                                      </td>
                                </tr>
                                <tr>
				      <td>
				         <font color="#808080">Branch Address2</font>
				      </td>
			 	      <td>
					<input type=text name="txtBank_Address2" id="txtBank_Address2" disabled>
                                        <input type=hidden name="txtBank_Address22" id="txtBank_Address22" >
				      </td>
				</tr>
				<tr>
		                      <td>
					 <font color="#808080">City_Town</font>
				      </td>
				      <td>
					<input type=text name="txtBank_Address3" id="txtBank_Address3"  disabled>
                                        <input type=hidden name="txtBank_Address33" id="txtBank_Address33" >
				      </td>
                                </tr>
                                <tr>
				      <td>
					<font color="#808080">MICR Code</font>
				      </td>
				      <td>
					<input type=text name="txtMicr_Code" id="txtMicr_Code" disabled>
                                        <input type=hidden name="txtMicr_Code1" id="txtMicr_Code1">
				      </td>
                                </tr>
                                
                                <tr>
				       <td>
					 A/c No
				      </td>
				      <td>
					<input type=text name="txtAcc_No" id="txtAcc_No" onkeyup="isInteger(this,event)" tabindex="4" maxlength="10">
				      </td>
                                </tr>
                                
                                <tr>
				       <td>
				         Account Type</td>
				       <td>
				       <select name="cmbAcc_Type" id="cmbAcc_Type" tabindex="5" >
				       <option value=0>--Select Account Type--</option>
				<%
				       try
				       {
				         results=statement.executeQuery("select * from FAS_MST_BANK_AC_TYPES"); 
				         while(results.next()) 
				          {
					      out.print("<option value='" + results.getString("ACCOUNT_TYPE_ID") + "'>" + results.getString("ACCOUNT_TYPE") + "</option>");                      
                                              
					  }
					      results.close();
					}
					catch(Exception e)
					{
                                            System.out.println("Exception in Account Type"+e);
					}  
				%>

				      </select>
				      </td>
                                  </tr>
                                 
                                 <tr>
				       <td>
				       Operational Mode</td>
				       <td>
				       <select name="cmbOperationalMode" id="cmbOperationalMode" tabindex="6"> 
				       <option value=0>--Select Operational Mode--</option>
				       <%
							 try
							 {
							   results=statement.executeQuery("select * from FAS_MST_AC_OPER_MODES"); 
							   while(results.next()) 
							   {
							       out.print("<option value='" + results.getString("AC_OPERATIONAL_MODE_ID") + "'>" + results.getString("AC_OPERATIONAL_MODE") + "</option>");                      
							   }
							   results.close();
							 }
							 catch(Exception e)
							 {
                                                            System.out.println("Exception in Operational Mode:"+e);
							 }  
					 %>

				       </select>
				       </td>
                                  </tr>
                                  
                                
                                
                                <tr>
                                      <td>
                                        Date Of Opening
                                      </td>
                                      <td>
                                        <input type=text name="txtDateOfJoining" maxlength="10" id="txtDateOfJoining" tabindex="7" onFocus="javascript:vDateType='3'" onKeyUp="DateFormat(this,this.value,event,false,'3')" onBlur="DateFormat(this,this.value,event,true,'3')">
                                      </td>
                                </tr>
                                <tr>
                                      <td>
                                        A/c Head No
                                      </td>
                                      <td>
                                        <input type=text name="txtHead_No" id="txtHead_No" onkeyup="isInteger(this,event)" tabindex="8" maxlength="6">
                                      </td>
                                </tr>
                                <tr>
                                      <td>
                                        Initial Deposit
                                      </td>
                                      <td>
                                        <input type=text name="txtInitial_Deposit" id="txtInitial_Deposit" onkeyup="isIntegerAndDot(this,event)" tabindex="9">
                                      </td>
                                </tr>
                                
                                <tr>
                                      <td colspan=2 class="bgclass">
                                          <input type="Button" value="  Add " id="Add" onclick="callServer1('Add','null')" name="cmdAdd" tabindex="10">
                                          <input type="Button" value=" Update" onclick="callServer1('Update','null')" disabled name="cmdUpdate">
                                          <input type="Button" value=" Delete" id="Revoke" onclick="callServer1('Delete','null')" disabled name="cmdDelete">
                                          <input type="Button" value="Clear All" onclick="clearAllBank()" name="cmdClearAll">
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
                                            <td colspan=11>
                                                <b>Existing Bank Details</b>
                                            </td>
                                </tr>

                                <tr>
                                            <th >View</th>
                                            <th>SL No</th>
                                            <th>Bank Name</th>
                                            <th>Branch Name</th>
                                            <th>MICR Code</th>
                                            <th>Acc No</th>
                                            <th>Acc Type</th>
                                            <th>Operational Mode</th>
                                            <th>DateOfOpening </th>
                                            <th>A/c Head No</th>
                                            <th>Initial Deposit</th>
                                </tr>

                                    <tbody id="tblList" name="tblList">
                                    </tbody>
                                <tr>
                                        <td colspan=11 class="bgClass"><center>
                                            <input type=Submit value=Submit onclick="return nullcheck()"/>
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

