<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bill Types for which Supporting Invoices is must</title>
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
        <script type="text/javascript" src="../scripts/Sup_inv_js.js"></script>
        <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
</head>
  <body onload="callmajorType();loadData();"> 
<form name="supportingInv" action="Get">
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
                                         <font size="4">Bill Types for which Supporting Invoices is must</font>
                                     </div>
                                </td>
                     </tr>
                </table>
                <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
				  <tr class="table">
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
                </tr>
                <tr>
	                         <td class="table" width="40%" align="left">Bill Major Type</td>
	                         <td class="table" align="left"> 
	                                 <select name="billmajortype" id="billmajortype" onchange="callminor();"> 
	                                     <option value="">select</option>
	                                 </select>
	                                 <input type="hidden" name="sno" id="sno">
	                                 </input>
	                         </td>               
                </tr>
                <tr>
                             <td class="table" width="40%" align="left">Bill Minor Type</td>
                             <td class="table" align="left"> 
                                     <select name="billminortype" id="billminortype" onchange="callsub(this.value);"> 
                                         <option value="">select</option>
                                     </select>
                             </td>               
                 </tr>
                 <tr>
                             <td class="table" width="40%" align="left">Bill Sub Type</td>
                             <td class="table" align="left"> 
                                     <select name="billsubtype" id="billsubtype"> 
                                         <option value="select">select</option>
                                     </select>
                             </td>               
                 </tr>
                 <tr>
                 		  <td class="table" width="40%" align="left">Supporting Invoice is must</td>
                 		  <td class="table" align="left"> 
		                  <div align="left">
		                    <input type="radio" name="supportinv" id="supportinv" 
		                             value="Y" checked="checked"/>YES
		                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
		                    <input type="radio" name="supportinv" id="supportinv" 
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
               <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
			    <tr class="tdH">
			        <td align="left">
			        Number Of Invoice/ Page.&nbsp;&nbsp;&nbsp;&nbsp;
			            <select name="cmbpagination" onchange="changepagesize()">
			                <option value="5" selected="selected">5 </option>
			                <option value="10">10</option>
			                <option value="15">15</option>
			                <option value="20">20</option>
			            </select>
			        </td>
			      <!--   <td align="right">
			            <B>Total Number Of Invoice</b> &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="total" id="total" size="3" readonly></input>
			        </td>  -->
			    </tr>
		</table>  
                <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%"
			             align="center">
			     <tr class="tdH">
			          <th>Select</th>
			          <th>Bill Major Type </th>
			          <th>Bill Minor Type</th>
			          <th>Bill Sub Type</th>
			          <th>Supporting Invoice</th>
			          <th>Status</th>
          		</tr>
		        <tbody id="tblList" class="table">
		        </tbody>
		        	 <tr class="tdH">
					    <td colspan="17">
					      <table align="center"  cellspacing="3" cellpadding="2" border="0" width="100%" >
					                    <tr class="tdH">
					                        <td width="30%">
					                             <div align="left"> <div id="divpre" style="display:none"></div> </div>
					                        </td>
					                        <td width="40%">
					                             <div align="center"><table border="0"><tr><td> <div id="divcmbpage" style="display:none" ><font color="Black" size="2"><strong>
					                             Page&nbsp;&nbsp;<select name="cmbpage" id="cmbpage" onchange="changepage()"></select></strong></font></div></td><td>
					                             <div id="divpage"></div></td></tr></table> </div>
					                        </td>
					                        <td width="30%">
					                             <div align="right"> <div id="divnext" style="display:none"></div> </div>
					                        </td>
					                    </tr>
					      </table>
					    </td>
					 </tr>
		         
      </table>
      </form>
</body>
</html>