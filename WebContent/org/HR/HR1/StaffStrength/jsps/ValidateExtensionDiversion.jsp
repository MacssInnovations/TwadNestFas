<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Validate Diversion Extension Details</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
     <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>  
     <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
     <script type="text/javascript" src="../../../../Library/scripts/CalendarControl.js"></script>
     <script type="text/javascript" src="../scripts/ValidateDiversionExtensionValidation.js" ></script>
     <script type="text/javascript" src="../scripts/ValidateDiversionExtensionAjax.js"></script>
      <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
                function getValsFromSession()
                {
                //alert('hai');
                    document.frmStaffStrength.txtOffice_Id.disabled=false;
                    document.frmStaffStrength.txtOffice_Id.focus();
                    //loadfyr();
                    //SanctionPost();
                    //officedetails();
                    
                }
          </script> 
  </head>
  <body class="table" onload="getValsFromSession()">
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
  
  <form action="../../../../../ValidateExtensionDiversion1.con" name="frmStaffStrength" method="post" onsubmit="return nullCheck()">
  <table  cellspacing="1" cellpadding="3"  width="100%" border="1" class="bgbody">
                    <tr>
                        <td class="tdH" >
                            <center><h2>Validate Diversion Extension Details</h2></center>
                        </td>
                    </tr>
                    <tr>
                        <td>                        
                              <table  cellspacing="3" cellpadding="1"  width="100%" >
                                  <tr>
                                      <td>Office Id</td>
                                      <%
                                            System.out.println("hai");
                                      HttpSession   session=request.getSession();
                                            UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                                            System.out.println("emp"+empProfile);
                                            int Emp_Id=empProfile.getEmployeeId();
                                            String Level=empProfile.getOfficeLevel();
                                            System.out.println("the Short Name:"+empProfile.getOfficeShortName());
                                            System.out.println("the emp id is---->>   "+Emp_Id);
                                            System.out.println("the Level is---->>   "+Level);
                                            //int Emp_Id=11263;
                                            int Office_Id=0;
                                             try
                                               {
                                                    results=statement.executeQuery("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID="+Emp_Id); 
                                                    if(results.next()) 
                                                    {
                                                       Office_Id=results.getInt("OFFICE_ID");
                                                    System.out.println("the office id is---->>   "+Office_Id);
                                                    }
                                                    results.close();
                                               }
                                            catch(Exception e)
                                            {
                                                    System.out.println("exception occured : " + e);
                                            }    
                                 %>
                                      <td colspan="4">
                                          <input type=text name="txtOffice_Id" onfocus="loadOffice(document.frmStaffStrength.txtOffice_Id.value)" value="<%=Office_Id%>" disabled>
                                          <input type=hidden name="txtOffice_Id1" value=<%=Office_Id%>>
                                     </td>
                                  </tr>
                                  <tr>
                                        <td>Office Name</td>
                                        <td colspan="4">
                                        <input type=text name="txtOfficeName" size="45" disabled>
                                        </td>
                                  </tr>
                                  <tr>
                                    <td class="tdH" colspan="5">
                                        <b>Diversion Details</b></td>
                                  </tr>
                                  <tr>
                                        <td>Diversion Order Id</td>
                                        <td><input type=text name="txtOrderId" id="txtOrderId" onchange="officedetails();" size="3" maxlength="3" onkeypress="return  calins(event,this)"></td>
                                        <td>Select OrderId</td>
                                        <td>
                                        <select name="cmbOrderId" id="cmbOrderId" onchange="orderid(this.value)">
                                        <option value=0>--Select OrderId--</option>
                                        <%
                                            try
                                            {
                                            PreparedStatement ps=connection.prepareStatement("select DIVERSION_ORDER_ID from hrm_ss_diversion_orders_tmp where process_flow_status_id  in 'FR' order by DIVERSION_ORDER_ID");
                                            ResultSet res=ps.executeQuery();
                                            while(res.next())
                                            {
                                               out.print("<option value='" + res.getInt("DIVERSION_ORDER_ID") + "'>" + res.getInt("DIVERSION_ORDER_ID") + "</option>");                        
                                            }
                                            }catch(Exception e)
                                            {
                                                System.out.println("Exception in Order:"+e );
                                            }
                                        %>
                                        </select>
                                        </td>
                                        <!--<td><select name="cmbOrderId" id="cmbOrderId"><option value=0>--Select OrderId--</option></td>-->
                                  </tr>
                                  <tr>
                                    <td>Diversion Order Date:</td>
                                    <td colspan="4">
                                        <input type=text name="txtDoD" maxlength="10" size="10" onFocus="javascript:vDateType='3';return ordercheck();" onkeypress="return  calins(event,this)" onblur="return checkdt(this);" disabled>
                                        <!--<img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmStaffStrength.txtDoD);" alt="Show Calendar" ></img>-->
                                    </td>
                                  </tr>
                                  <!--<tr>
                                    <td>Financial Year</td>
                                    <td colspan="4">
                                         <select name="cmbFinancialYear" id="cmbFinancialYear">
                                         <option value=0>---Select Financial Year---</option>
                                         </select>
                                    </td>
                                     
                                  
                                  <tr>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>Office Level</td>
                                            <td><div id="divType1" style="visibility:hidden">Office Type</div></td>
                                            <td><div id="divType2" style="visibility:hidden">Select Office</div></td>
                                        </tr>-->
                                  <tr>
                                    <td>Diversion From Office Id:</td>
                                    <td>
                                        <input type=text name="txtdiversionfromoffice" size="4" onchange="loadofficeaddress(this.value);PostRank(this.value)" onfocus="return ordercheck()" disabled>
                                        <input type=hidden name="txtHdiversionfromoffice">
                                    </td>
                                    <!--<td>
                                        <select name="cmbFromOfficeId" id="cmbFromOfficeId" onchange="getOfficesByLevel()" onfocus="return ordercheck()">
                                        <option value=0>---Select Office Id ---</option>
                                        </select>
                                    </td>
                                    <td>
                                                <select name="cmbOfficeType" style="visibility:hidden" onchange="getOfficesByType()" >
                                                    <option value=0>
                                                                                    ----Select
                                                                                    Office
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
                                                <select name="cmbSelectOffice" id="cmbSelectOffice" style="visibility:hidden" onchange="selectControllineOffice()" >
                                                    <option value=0>
                                                                                    ----Select
                                                                                    Office----
                                                                              </option>                                               
                                                </select>
                                            </td>
                                  </tr>-->
                                  <tr>
                                
                            
                                <td>Office Name/Address</td>
                                <td><textarea rows="5" cols="25" id="txtOfficeAddressFrom" name="txtOfficeAddressFrom" disabled></textarea></td>
                            </tr>

                                  <!--<tr>
                                            <td>&nbsp;</td>
                                             <td>&nbsp;</td>
                                            <td>Office Level</td>
                                            <td>&nbsp;<div id="divType3" style="visibility:hidden">Office Type</div></td>
                                            <td>&nbsp;<div id="divType4" style="visibility:hidden">Select Office</div></td>
                                   </tr>-->
                                  <tr>
                                    <td>Diversion To Office Id:</td>
                                    <td>
                                        <input type=text name="txtdiversiontooffice" size="4" onchange="loadofficeaddressto(this.value);return checktooff();" onfocus="return ordercheck()" disabled>
                                        <input type=hidden name="txtHdiversiontooffice">
                                    </td>
                                    <!--<td>
                                        <select name="cmbToOfficeId" id="cmbToOfficeId" onchange="getOfficesByLevel1()" onfocus="return ordercheck()">
                                        <option value=0>---Select To Office Id ---</option>
                                        </select>
                                    </td>
                                    <td>
                                                <select name="cmbOfficeType1" style="visibility:hidden" onchange="getOfficesByType1()" >
                                                    <option value=0>
                                                                                    ----Select
                                                                                    Office
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
                                                <select name="cmbSelectOffice1" id="cmbSelectOffice1" style="visibility:hidden" onchange="return selectControllineOffice1();" >
                                                    <option value=0>
                                                                                    ----Select
                                                                                    Office----
                                                                              </option>                                               
                                                </select>
                                            </td>
                                  </tr>
                                  <!--<tr>
                                    <td>Post Category<label style="color:rgb(255,0,0);"> &nbsp;*</label></td>
                                    <td>
                                        <select name="cmbPostCategory" id="cmbPostCategory" tabindex="5">
                                        <option value=0>---Select PostCategory---</option>
                                        <%
                                                    
                                         %>
                                        
                                         </select>

                                    </td>
                                  </tr>-->
                                  <tr>
                                       
                                    
                                        <td>Office Name/Address</td>
                                        <td><textarea rows="5" cols="25" id="txtOfficeAddressTo" name="txtOfficeAddressTo" disabled></textarea></td>
                                </tr>
                                  <tr>
                                    <td>Name of the Post Diverted:</td>
                                    <td >
                                        <select name="cmbPostRank" id="cmbPostRank" onchange="SanctionPost(this.value)" onfocus="return ordercheck()" disabled>
                                        <option value=0>---Select Post Rank---</option>
                                        </select>
                                    </td>
                                                            
                                  </tr>
                                  <!--<tr>
                                  <td >No.of Sanction Post</td>
                                   <td><input type=text name="noofsanction" disabled></td> 
                                   <td colspan="3">
                                       <table border=0>
                                           <tr>
                                                <td >Already Diverted To Other Offices</td> 
                                                <td><input type=text name="divertedtopost" disabled></td>  
                                                
                                                
                                            </tr>
                                        </table>
                                    </td>
                                    
                                  </tr>-->
                                  
                                  <tr>
                                    <td>No. of Posts Diverted:</td>
                                    <td colspan="4">
                                        <input type=text name="txtPostDiverted" size=10 maxlength="3" onkeypress="return  numbersonly1(event,this)" onchange="return checksanction()" onfocus="return ordercheck()" disabled>
                                    </td>
                                  </tr>
                                  <tr>
                                    <td>Diversion Effective Date:</td>
                                    <td><input type=text name="txtDEDate" size=10 maxlength=10 onfocus="javascript:vDateType='3'"
                                                   onkeypress="return  calins(event,this)"
                                                   onblur="return checkdt(this);" disabled></td>
                                    <!--<img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmStaffStrength.txtDEDate);" alt="Show Calendar"></img>-->
                                </tr>
                                <tr>
                                    <td>Diversion Period Upto:</td>
                                    <td><input type=text name="txtDPDate" size=10 maxlength="10" onfocus="javascript:vDateType='3'" onkeypress="return  calins(event,this)" onblur="return checkdt(this);" disabled></td>
                                    <!--<img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmStaffStrength.txtDPDate);" alt="Show Calendar"></img>-->
                                    
                                </tr>
                                  <tr>
                                    <td>Remarks</td>
                                    <td colspan="5"><textarea rows="5" cols="25" name=txtRemarks onfocus="return ordercheck()" disabled></textarea>
                                  </tr>
                                  
                                  <tr>
                                        
                                        <td colspan="4" class="tdH"><b>Existing Validated Extension Details</b>
                                        </td>
                                        <td></td>
                                        
                                  </tr>
                                  <tr>
                                        <td colspan="4" align="left">
                                        <table border=0 width="100%">
                                    <tr>             
                                       <th rowspan="1">Diversion Order SlNo</th>
                                        <th rowspan="1">Diversion Extension Order Date</th>
                                        <th rowspan="1">Diversion Extended Upto</th>
                                        <th rowspan="1">Remarks</th>
                                        <th rowspan="1"></th>
                                        <td colspan="4"></td>
                                        </tr>
                                        </table>
                                        </td>
                                  </tr>
                                  <tr>
                                    <tbody id="tblList" name="tblList">
                                    </tbody>
                                  </tr>
                                
                                  
                                  <tr>
                                    <td class="tdH" colspan="5">
                                        <b>New Diversion Extension Details</b></td>
                                  </tr>
                                  <tr>
                                    <td>Diversion Extension OrderSlNo:<label style="color:rgb(255,0,0);">*</label></td>
                                    <td>
                                        <select name="cmbOrderSlNo" id="cmbOrderSlNo" onchange="callServer('Extension')" onfocus="return ordercheck()">
                                        <option value=0>--Select Order SlNo--</option>
                                        </select>
                                    <!--<td colspan="4"><input type=text name="txtExOrderSlNO" size=4 maxlength=4 disabled>
                                    <input type=hidden name="txtExOrderSlNO1" size=4>-->
                                    </td>
                                  </tr>
                                  <tr>
                                    <td>Diversion Extension Order Date:<label style="color:rgb(255,0,0);">*</label></td>
                                    <td colspan="4"><input type=text name="txtExOrderDate" maxlength=10 size=10 onfocus="javascript:vDateType='3';return ordercheck();" onkeypress="return  calins(event,this)" onblur="return checkdt(this);" onchange="return check1()">
                                    <img src="../../../../../images/calendr3.gif" onclick="if(ordercheck()==true)showCalendarControl(document.frmStaffStrength.txtExOrderDate);" alt="Show Calendar"></img>
                                    </td>
                                    
                                  </tr>
                                  <tr>
                                    <td>Diversion Extended Upto:<label style="color:rgb(255,0,0);">*</label></td>
                                    <td colspan="4"><input type=text name="txtExUpto" maxlength=10 size=10 onfocus="javascript:vDateType='3';return ordercheck()" onkeypress="return  calins(event,this)" onblur="return checkdt(this);" onchange="if(check2(this)==true)return check3()">
                                    <img src="../../../../../images/calendr3.gif" onclick="if(ordercheck()==true)showCalendarControl(document.frmStaffStrength.txtExUpto);" alt="Show Calendar"></img>
                                    </td>
                                  </tr>
                                  <tr>
                                    <td>Remarks</td>
                                    <td colspan="4"><textarea rows="5" cols="25" name="txtExRemarks" onfocus="return ordercheck()"></textarea></td>
                                  </tr>
                             </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdH">
                            <center>
                                    <input type="submit" value="Validate" name="cmbSubmit" id="cmbSubmit">
                                    <input type="reset" value="ClearAll" onclick="clear1()">
                                    <input type="button" value="Exit" onclick="closeWindow()">
                            </center>
                        </td>
                    </tr>
    </table>
    
  </form>
  </body>
</html>