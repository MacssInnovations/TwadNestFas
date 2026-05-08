<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bill Scrutiny CheckList</title>
		<link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
        <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
        <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
        <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
        <script type="text/javascript" src="../../Reports/ReceiptSystem/scripts/CalendarControl.js"></script>
        <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
        <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
        <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>
        <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">          </script>
        <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
        <script type="text/javascript" src="../scripts/BillScrutiny_js.js"></script>
        <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
</head>
<body onload="callmajorType();callServer('Get','null')">
<form name="scrutinyform" action="Get">
			<%
                            Connection con=null;
                            Statement stmt=null;
                             ResultSet rs=null,rs2=null;
                            ResultSet results=null;
                            PreparedStatement ps=null,ps2=null;
                            String xml=null;
                            int oid=0;
                            
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
                                      // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                                       Class.forName(strDriver.trim());
                                       con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                                       try
                                       {
                                            stmt=con.createStatement();
                                            con.clearWarnings();
                                       }
                                       catch(SQLException e)
                                       {
                                            System.out.println("Exception in creating statement:"+e);
                                       }
                                        stmt=con.createStatement();
                           }
                           catch(Exception e)
                           {
                                System.out.println("Exception in opening connection:"+e);
                           } 
                            HttpSession session=request.getSession(false);
                            UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                              
                            System.out.println("user id::"+empProfile.getEmployeeId());
                            int empid=empProfile.getEmployeeId();
                            String oname="";
                            try
                            {
                                ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
                                ps.setInt(1,empid);
                                results=ps.executeQuery();
                                     if(results.next()) 
                                     {
                                        oid=results.getInt("OFFICE_ID");
                                     }
                                results.close();
                                ps.close();
                                ps=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
                                ps.setInt(1,oid);
                                results=ps.executeQuery();
                                     if(results.next()) 
                                     {
                                        oname=results.getString("OFFICE_NAME");
                                      }
                                results.close();
                                ps.close();
                            }
                            catch(Exception e)
                            {
                                System.out.println(e);
                            }
                %>
                <table cellspacing="1" cellpadding="3" width="100%" >
                     <tr class="tdH"> 
                                <td colspan="2">
                                     <div align="center">
                                         <font size="4">Bill Scrutiny CheckList</font>
                                     </div>
                                </td>
                     </tr>
                </table>
                <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
                       <!--   <tr class="table">
                            <td> 
                                <div align="left">
                                    Accounting Unit Code 
                                    <font color="#ff2121">*</font>
                                </div>
                            </td>
                            <td>
                            <div align="left">
                            <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1">
                                <%
                                  int unitid=0;
                                  String unitname="";
                                  try
                                  {
                                    if(oid==5000)
                                    {
                                    String getWing="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=? and OFFICE_WING_SINO=(select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)";
                                    ps=con.prepareStatement(getWing);
                                    ps.setInt(1,oid);
                                    ps.setInt(2,empid);
                                    ps.setInt(3,oid);
                                    rs=ps.executeQuery();
                                      
                                    if(rs.next())
                                       {
                                           out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                           unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                          
                                          System.out.println(".."+rs.getInt("ACCOUNTING_UNIT_ID"));
                                          System.out.println(".."+rs.getString("ACCOUNTING_UNIT_NAME"));
                                          System.out.println(".."+rs.getInt("OFFICE_WING_SINO"));
                                      
                                      }
                                      System.out.println(oid+" "+oname);
                                      ps.close();
                                      rs.close();
                                     }
                                      else
                                      {
                                        ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)");
                                        ps.setInt(1,oid);
                                        rs=ps.executeQuery();
                                          if(rs.next())
                                          {
                                          System.out.println(rs.getInt("ACCOUNTING_UNIT_ID"));
                                          System.out.println(rs.getString("ACCOUNTING_UNIT_NAME"));
                                          out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                          unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                          }
                                          ps.close();
                                          rs.close();
                                      }
                                    }
                                  catch(Exception e)
                                    {
                                    System.out.println("here");
                                        System.out.println(e);
                                    }
                                 %>
                            </select>
                           </div>
                          </td>
                        </tr>
                        <tr class="table">
                         <td>
                          <div align="left">
                            Accounting For Office Code
                            <font color="#ff2121">*</font>
                          </div>
                         </td>
                         <td>
                            <div align="left">
                                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2"> 
                                      
                                      <%
                                   System.out.println("here");
                                   System.out.println(oid+"  " +oname);
                                try
                                {
                                   if(oid==5000)
                                    {
                                        out.println("<option value="+oid+">"+"HEAD OFFICE"+"</option>");
                                    }
                                    else
                                    {
                                        ps=con.prepareStatement("select ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID  from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_UNIT_ID=? order by ACCOUNTING_FOR_OFFICE_ID desc");
                                        ps.setInt(1,unitid);
                                        rs=ps.executeQuery();
                                        while(rs.next())
                                        {
                                        ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                                        ps2.setInt(1,rs.getInt("ACCOUNTING_FOR_OFFICE_ID"));
                                        rs2=ps2.executeQuery();
                                        if(rs2.next())
                                        out.println("<option value="+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+">"+rs2.getString("OFFICE_NAME")+"</option>");
                                        }
                                    }
                                } 
                                catch(Exception e)
                                {
                                System.out.println("Exception in Office combo..."+e);
                                }
                                finally
                                {
                                rs.close();
                                ps.close();
                                }  
                                %>
                                </select>
                            </div>
                            </td>
                        </tr>-->
                        <tr>
                                    <td class="table" width="40%" align="left">Check Desc</td>
                                    <td class="table" align="left"> 
                                            <input type="text" name="checkDesc" id="checkDesc" />
                                            <input type="hidden" name="checkCode" id="checkCode" />
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Bill Major Type</td>
                                    <td class="table" align="left"> 
                                            <select name="billmajortype" id="billmajortype" onchange="callminor();"> 
                                                <option value="">select</option>
                                            </select>
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Bill Minor Type</td>
                                    <td class="table" align="left"> 
                                            <select name="billminortype" id="billminortype"> 
                                                <option value="">select</option>
                                            </select>
                                    </td>               
                        </tr>
                        <tr>
                        			<td class="table" width="40%" align="left">Mandate</td>
                        			<td class="table" align="left"> 
						                  <div align="left">
						                    <input type="radio" name="checkmandate" id="checkmandate" 
						                             value="Y" checked="checked"/>YES
						                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
						                    <input type="radio" name="checkmandate" id="checkmandate" 
						                           value="N" />NO &nbsp;&nbsp;&nbsp;&nbsp; 
						                  </div>
					                </td>
                        </tr>
                        <tr>
                        			<td class="table" width="40%" align="left">Not Applicable</td>
                        			<td class="table" align="left"> 
						                  <div align="left">
						                    <input type="radio" name="notapply" id="notapply" 
						                             value="Y" checked="checked"/>YES
						                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
						                    <input type="radio" name="notapply" id="notapply" 
						                           value="N" />NO &nbsp;&nbsp;&nbsp;&nbsp; 
						                  </div>
					                </td>
                        </tr>
                        
           </table>
                   <table cellspacing="1" cellpadding="3" width="100%">
                        <tr class="tdH">
                            <td  colspan="2">
                                <div align="center"> 
                                        <input type="button" name="onadd" value="Add" id="onadd" onclick="callServer('Add','null')" /> 
                                        <input type="button" name="onedit" value="Update" id="onedit" onclick="callServer('Update','null')" disabled/> 
                                        <input type="button" name="ondelete" value="Cancel" id="ondelete" onclick="callServer('Delete','null')" disabled/> 
                                        <input type="button" name="onclear" value="ClearAll" id="onclear" onclick="clearAll();" /> 
                        				<input type="button" name="oncancel" value="Exit" id="oncancel" onclick=" self.close();" /> 
                                </div> 
                           </td> 
                       </tr>
                     </table>
                     <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%"
			             align="center">
			        <tr class="tdH">
          <th>Select</th>
          <th>Check Code</th>
          <th>Check Desc </th>
          <th>Bill Major Type </th>
          <th>Bill Minor Type</th>
          <th>Mandate</th>
          <th>Not Applicable </th>
          <th>Status </th>
          
        </tr>
        <tbody id="tblList" class="table">
        </tbody>
        </table>
</form>
</body>
</html>