<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Creation Of Joining Report Details</title>
   <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

 <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
<script type="text/javascript" src="../scripts/Hrm_TransJoinJS.js"></script>
 <!-- <script type="text/javascript"       src="../../../../../org/Library/scripts/CalendarControl.js"></script>-->
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
   <script type="text/javascript" src="../scripts/CalendarControl.js"></script>

  </head>
  <body id="bodyid">
  <form name="Hrm_TransJoinForm" id="Hrm_TransJoinForm">
  
  <%
  
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    
    
     Connection connection=null;

  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
    
   try
  {
  
             ResourceBundle rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rb.getString("Config.DSN");
            String strhostname=rb.getString("Config.HOST_NAME");
            String strportno=rb.getString("Config.PORT_NUMBER");
            String strsid=rb.getString("Config.SID");
            String strdbusername=rb.getString("Config.USER_NAME");
            String strdbpassword=rb.getString("Config.PASSWORD");

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
  
  
   <!-- OFFICE DETAILS -->
   <% 
    HttpSession session=request.getSession(false);
    UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();
    int  oid=0;
    String oname="",oadd1="",oadd2="",ocity="",odist="",olid="",owid="",olevelname="";
    String olname="",olevelid=""; 
    String ownature="";
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
            ps=con.prepareStatement("select a.OFFICE_NAME,a.OFFICE_ADDRESS1,a.OFFICE_ADDRESS2,a.DISTRICT_CODE,a.CITY_TOWN_NAME,a.OFFICE_LEVEL_ID,a.PRIMARY_WORK_ID,b.DISTRICT_NAME,c.OFFICE_LEVEL_NAME from COM_MST_OFFICES a "+
            " left outer join com_mst_districts b on b.DISTRICT_CODE= a.DISTRICT_CODE "+
            " inner join com_mst_office_levels c on  c.office_level_id=a.office_level_id "+
            " where OFFICE_ID=?");
            ps.setInt(1,oid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oname=results.getString("OFFICE_NAME");
                    oadd1=results.getString("OFFICE_ADDRESS1");
                    oadd2=results.getString("OFFICE_ADDRESS2");
                    ocity=results.getString("CITY_TOWN_NAME");
                    odist=results.getString("DISTRICT_NAME");
                    olevelname=results.getString("OFFICE_LEVEL_NAME");
                    olevelid=results.getString("OFFICE_LEVEL_ID");
                  }
            results.close();
            ps.close();
               
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
  
  
      <div align="center">
        <table cellspacing="3" cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <strong>Creation Of Joining Report Details</strong>
              </div>
            </td>
          </tr>
          <tr class="tdH">
            <td colspan="2">
              <div align="left">
                Office Details
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Office ID
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOffId" id="txtOffId" maxlength="6" value="<%=oid%>"
                       size="6"  class="disab"  readonly="readonly"/>
                      <!--onchange="return callServer1('Load','null')" <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="jobpopup();">-->
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Office Name
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOffName" id="txtOffName"  value="<%=oname%>"
                       maxlength="30" size="43" class="disab"  readonly="readonly"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Office Level
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOfflevel" id="txtOffLevel"  value="<%=olevelname%>"
                       maxlength="30" size="43" class="disab"  readonly="readonly"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Office Address
              </div>
            </td>
            <td>
              <div align="left">
               
<textarea rows="4" cols="40"  name="txtOffAddr" id="txtOffAddr" readonly="readonly"
                class="disab"><%
                String s=null;
                if(oadd1!=null)
                {
                    s=oadd1;
                }
                if(oadd2!=null)
                {
                    s+="\n"+oadd2;
                }
                if(ocity!=null)
                {
                    s+="\n"+ocity;
                }
                if(odist!=null)
                {
                    s+="\n"+odist;
                }
                if(s!=null)
                    out.print(s);   
                                
                %></textarea>
              </div>
            </td>
          </tr>
          <tr class="tdH">
            <td colspan="2">
              <div align="left">
                Employee Details
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Employee ID 
               
                        <font color="#ff2121">
                          *
                        </font>
                     
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtEmpId" id="txtEmpId" onkeypress="return  numbersonly1(event,this)" maxlength="6" size="6" onchange="doFunction('dispEmp','null')"/>
                 <img src="../../../../../images/c-lovi.gif"
                                                           width="20"
                                                           height="20"
                                                           alt="empList"
                                                           onclick="servicepopup();"></img>
             </div>
            </td>            
            </tr>
          <tr class="table">
            <td>
              <div align="left">
                Employee Name
              </div>
            </td>
            <td>
              <div align="left">
              <input type="text" name="comEmpId" id="comEmpId"  size="40"  readonly style="background-color: #ececec" />
        
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                DOB
              </div>
            </td>
            <td>
              <div align="left">
                        
              
                <input type="text" name="txtDOB" id="txtDOB" maxlength="10" readonly
                       size="10" style="background-color: #ececec"/><!--<img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.Hrm_TransJoinForm.txtDOB);" alt="Show Calendar" ></img>-->
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                GPF NO.
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtGpfNo" id="txtGpfNo" maxlength="10" style="background-color: #ececec" readonly
                       size="10"/>
              </div>
            </td>
          </tr>
          <tr class="tdH">
            <td colspan="2">
              <div align="left">
                Details Of Joining Report
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Joining Report ID
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtJRId" id="txtJRId" maxlength="3"
                       size="43" readonly class="disab" />(System Generated)
                       <input type="hidden" name="txtJR" id="txtJR" maxlength="3"
                       size="3" class="disab"/>
              </div>
            </td>
          </tr>
          
          
          
      <!--     <tr class="table" id="drcompdate" style="display:none">
            <td>
              <div align="left" id="divcompleted" >
                 Completed Date
              </div>
            </td>
            <td>
              <div align="left">
              
              <input type="text" name="txtDOC" id="txtDOC"
                       maxlength="10" size="11"
                       onfocus="javascript:vDateType='3'; return checkEID() "
                       onkeypress="return calins(event,this);"
                       onblur="if(checkcurdt(this)==true)return checkdt(this);"/>
                
                <img src="../../../../../images/calendr3.gif"
                     onclick=" if(checkEID()==true)showCalendarControl(document.Hrm_TransJoinForm.txtDOC);"
                     alt="Show Calendar"></img>
              
             <input type="radio" name="optFNAN" id="optFNAN1" value="FN" checked onfocus="return checkEID()" />FN
                         &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="optFNAN" id="optFNAN2" value="AN" onfocus="return checkEID()" />AN
                         
              </div>
            </td>
          </tr>
       -->   
          
          <tr class="table">
            <td>
              <div align="left">
                Date Of Joining<font color="#ff2121">
                          *
                        </font>
                      
              </div>
            </td>
            <td>
              <div align="left">
              <input type="hidden" name="txtempstatus" id="txtempstatus">
              <input type="text" name="txtDOJ" id="txtDOJ"
                       maxlength="10" size="11"
                       onfocus="javascript:vDateType='3'; return checkEID() "
                       onkeypress="return calins(event,this);"
                       onblur="if(checkcurdt(this)==true)return checkdt(this);"/>
                 
                <img src="../../../../../images/calendr3.gif"
                     onclick=" if(checkEID()==true)showCalendarControl(document.Hrm_TransJoinForm.txtDOJ);"
                     alt="Show Calendar"></img>
              
            <!--    <input type="text" name="txtDOJ" id="txtDOJ" maxlength="10"
                       size="11"onchange="return validate_date('Hrm_TransJoinForm','txtDOJ')"
                                                             onfocus="javascript:vDateType='3'"
                                                             onkeyup="DateFormat(this,this.value,event,false,'3')"
                                                             onblur="DateFormat(this,this.value,event,true,'3')"/>
                                                             <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.Hrm_TransJoinForm.txtDOJ);" alt="Show Calendar" ></img>
        -->
             <input type="radio" name="radFNAN" id="radFNAN1" value="FN" checked onfocus="return checkEID()" />FN
                         &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="radFNAN" id="radFNAN1" value="AN" onfocus="return checkEID()" />AN
                         
              </div>
            </td>
          </tr>
          
          <%if(olid.equalsIgnoreCase("DN")){%>
          <tr class="table">
            <td>
              <div align="left">
                Whether Joined in 
                
                        <font color="#ff2121">
                          *
                        </font>
                     
              </div>
            </td>
            <td>
              <div align="left">
              <input type="radio" name="optjoin" value="D" onclick="subdivisioncheck()" checked="checked"/> Division <input type="radio" name="optjoin" value="S" onclick="subdivisioncheck()" /> SubDivision
            <div style="display:none" id="divsubdivision"><select name="cmbsubdivision" id="cmbsubdivision">
            <option value="">Select SubDivision</option>
            
             <%
                        ResultSet rs1=null;
                        try
                        {
                        ps=con.prepareStatement("select SUBDIVISION_OFFICE_ID,SUBDIVISION_OFFICE_NAME from COM_MST_SUBDIVISIONS_HVIEW where DIVISION_OFFICE_ID=? order by SUBDIVISION_OFFICE_NAME");
                        ps.setInt(1,oid);
                        rs1=ps.executeQuery();
                        int strcode=0;
                        String strname="";    
                        while(rs1.next())
                        {
                        strcode=rs1.getInt("SUBDIVISION_OFFICE_ID");
                        strname=rs1.getString("SUBDIVISION_OFFICE_NAME");
                        out.println("<option value='"+strcode+"'>"+strname+"</option>");
                        }
                        }
                        catch(Exception e)
                        {
                        System.out.println("Exception in the service group id"+e);
                        }
                        
                        
                        %>
            
            
            </select>
            </div>
              </div>
            </td>
          </tr>
           
           <%}%>
          
          
          <!---
          -->
           <tr class="table">
            <td>
              <div align="left">
                Designation 
               
                        <font color="#ff2121">
                          *
                        </font>
                     
              </div>
            </td>
           <td colspan="2">
                      <table cellspacing="2" align="left"    cellpadding="3"   border="0"   width="100%">
                            <tr class="table">
                                  <td align="left">Service  Group</td>
                                  <td align="left">
                                        <div id="divdes" style="visibility:hidden">Designation</div>
                                  </td>
                            </tr>
                            <tr class="table">
                                  <td align="left">
                                        <select name="cmbsgroup" id="cmbsgroup" onchange="getDesignation1()" onfocus="return checkEID()">
                                              <option value="0">Select Service Group</option>
                        <%
                        ResultSet rs1=null;
                        try
                        {
                        ps=con.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
                        rs1=ps.executeQuery();
                        int strcode=0;
                        String strname="";    
                        while(rs1.next())
                        {
                        strcode=rs1.getInt("SERVICE_GROUP_ID");
                        strname=rs1.getString("SERVICE_GROUP_NAME");
                        out.println("<option value='"+strcode+"'>"+strname+"</option>");
                        }
                        }
                        catch(Exception e)
                        {
                        System.out.println("Exception in the service group id"+e);
                        }
                        
                        
                        %>
                            </select>
                        </td>
                      <td align="left">
                            <select name="comDesign"  id="comDesign"  style="visibility:hidden"  onchange="postcount()" onfocus="return checkEID()"></select>
                      </td>
                </tr>
          </table>
         </td>
    
      </tr>
          
          <!-- -->
          
           
          
          
        <!--  <tr class="table">
            <td>
              <div align="left">
                Designation
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="comDesign1" id="comDesign1"  onchange="postcount()" onfocus="return checkEID()">
                  <option>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- - - - Select - - - -
                  </option>
                  <%
             /*   try
                {
                ps=con.prepareStatement("select DESIGNATION_id,Designation from HRM_MST_DESIGNATIONS  order by Designation");
                rs=ps.executeQuery();
                while(rs.next())
                {
                int des=rs.getInt("DESIGNATION_id");
                out.println("<option value="+des+">"+rs.getString("DESIGNATION")+"</option>");
                }
                }
                catch(Exception e)
                {
                System.out.println("Exception in desig combo..."+e);
                }
                */
                %>
                </select>
              </div>
            </td>
          </tr>
     -->
          <tr class="table">
            <td>
              <div align="left">
                Post Counted Towards
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="comPostTow" id="comPostTow" onfocus="return checkEID()">
                <option>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- - - - Select - - - -
                </option>
                <%
               /* try
                {
                ps=con.prepareStatement("select POST_RANK_ID,POST_RANK_NAME from HRM_MST_POST_RANKS");
                rs=ps.executeQuery();
                while(rs.next())
                {
                int pos=rs.getInt("POST_RANK_ID");
                out.println("<option value="+pos+">"+rs.getString("POST_RANK_NAME")+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"</option>");
                }
                }
                catch(Exception e)
                {
                System.out.println("Exception in post rank combo..."+e);
                }*/
                 try
                {
                ps=con.prepareStatement("select DESIGNATION_id,Designation from HRM_MST_DESIGNATIONS order by Designation");
                rs=ps.executeQuery();
                while(rs.next())
                {
                int des=rs.getInt("DESIGNATION_id");
                out.println("<option value="+des+">"+rs.getString("DESIGNATION")+"</option>");
                }
                }
                catch(Exception e)
                {
                System.out.println("Exception in desig combo..."+e);
                }
                %>
                </select>
              </div>
            </td>
          </tr>
         
          <%if(olevelid!=null && olevelid.equalsIgnoreCase("HO") ){%>
          <tr class="table" id="wing" >
                          <td align="left">
                            Wing
                          </td>
                          <td align="left">
                            <select name="cmbWing">
                            <option value="0">Select Wing</option>
                            
                             <%
                        rs1=null;
                        try
                        {
                        ps=con.prepareStatement("select OFFICE_WING_SINO,WING_NAME from COM_OFFICE_WINGS where OFFICE_ID=?");
                        ps.setInt(1,oid);
                        rs1=ps.executeQuery();
                        int strcode=0;
                        String strname="";    
                        while(rs1.next())
                        {
                        strcode=rs1.getInt("OFFICE_WING_SINO");
                        strname=rs1.getString("WING_NAME");
                        out.println("<option value='"+strcode+"'>"+strname+"</option>");
                        }
                        }
                        catch(Exception e)
                        {
                        System.out.println("Exception in the service group id"+e);
                        }
                        
                        
                        %>
                            
                            </select>
                          </td>
                 </tr>
            <%}%>
          
          <tr class="table">
            <td>
              <div align="left">
                Remarks
              </div>
            </td>
            <td>
              <div align="left">
                <textarea name="txtRemarks" id="txtRemarks" cols="34" rows="2" onfocus="return checkEID()"></textarea>
              </div>
            </td>
          </tr>
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <input type="button" name="butSub" id="butSub" value="SUBMIT" onclick="doFunction('Add','null')"/>
                 &nbsp;&nbsp;&nbsp;
                <input type="button" name="butCan" id="butCan" onclick="self.close();" value="CANCEL"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>