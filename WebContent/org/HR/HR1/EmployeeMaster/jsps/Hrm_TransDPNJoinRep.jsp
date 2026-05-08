<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Creation Of Deputation Joining Details</title>
   <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

 <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
<script type="text/javascript" src="../scripts/Hrm_TransDPNJoinJS.js"></script>
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
    String oname="",oadd1="",oadd2="",ocity="",odist="",olid="",owid="";
    String olname=""; 
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
            ps=con.prepareStatement("select a.OFFICE_NAME,a.OFFICE_ADDRESS1,a.OFFICE_ADDRESS2,a.DISTRICT_CODE,a.CITY_TOWN_NAME,a.OFFICE_LEVEL_ID,a.PRIMARY_WORK_ID,b.DISTRICT_NAME from COM_MST_OFFICES a "+
            " left outer join com_mst_districts b on b.DISTRICT_CODE= a.DISTRICT_CODE where OFFICE_ID=?" );
            ps.setInt(1,oid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oname=results.getString("OFFICE_NAME");
                    oadd1=results.getString("OFFICE_ADDRESS1");
                    oadd2=results.getString("OFFICE_ADDRESS2");
                    ocity=results.getString("CITY_TOWN_NAME");
                    odist=results.getString("DISTRICT_NAME");
                    
                  }
            results.close();
            ps.close();
     /* */      
                 
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
                <strong>Creation Of Deputation Joining Details</strong>
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
                <input type="text" name="txtEmpId" id="txtEmpId" maxlength="6" size="6" onkeypress="return  numbersonly1(event,this)" onchange="doFunction('dispEmp','null')"/>
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
                <strong>Details of Relieval on Account of Deputation</strong>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Date of Relieval
                
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtDepDoj" id="txtDepDoj"
                       readonly="readonly" class="disab" maxlength="10" size="10"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Other Department Name
               
              </div>
            </td>
            <td>
              <div align="left">
              <select name="txtDepId" id="txtDepId" onchange="getDeptName()">
              <option value="" selected>Other Department Name</option>
              <%
                         try
                         {
                         System.out.println("test1");
                         ps=con.prepareStatement("select a.OTHER_DEPT_ID,a.OTHER_DEPT_NAME from HRM_MST_OTHER_DEPTS a  order by OTHER_DEPT_NAME");
                        rs=ps.executeQuery();
                        String strcode="";
                        String strname="";           
                        System.out.println("test2");
                        while(rs.next())
                        {
                            strcode=rs.getString("OTHER_DEPT_ID");
                            strname=rs.getString("OTHER_DEPT_NAME");
                            
                            out.println("<option value='"+strcode+"'>"+strname+"</option>");
                            
                         }
                      }
                      catch(Exception e)
                      {
                        System.out.println("Exception in grid.."+e);
                      }
                       finally
                      {
                            rs.close();
                            ps.close();
                            //connection.close();
                      
                      }    
                         
                         %>
              </select>
               
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Other Office Name 
                <font color="#ff2121">
                  *
                </font>
              </div>
            </td>
            <td>
              <div align="left">
                          
               <select name="txtDepOffId" id="txtDepOffId">
               <option value="">Select the Office Name</option>
               </select>
               <!--<img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="Office_List" onclick="if(checkEID()==true){jobpopup1();}">-->
              </div>
            </td>
          </tr>
              
          <tr class="tdH">
            <td colspan="2">
              <div align="left">
                Details Of Deputation Joining
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
    
          
          
             
          <tr class="table">
            <td>
              <div align="left">
                Date Of Joining 
                <font color="#ff2121">
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
         
         
         
          <tr class="table">
            <td>
              <div align="left">
                SR Controlling Office Id 
                <font color="#ff2121">
                  *
                </font>
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOffice_Id" id="txtOffice_Id" onkeypress="return  numbersonly1(event,this)" onchange="doFunction('Load',true)" size="6" maxlength="4"/>
                <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="Office_List" onclick="if(checkEID()==true){jobpopup();}">
                <input type="hidden" name="txtDept_Id_work" id="txtDept_Id_work" value='TWAD' />
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                SR Controlling Office Name
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOffice_Name" id="txtOffice_Name" size="30" disabled maxlength="40"/>
                
              </div>
            </td>
          </tr>
          
          
          
          <tr class="table">
            <td>
              <div align="left">
                Remarks
              </div>
            </td>
            <td>
              <div align="left">
                <textarea name="txtRemarks" id="txtRemarks" title="Don't type '&' Character while entering the remarks" cols="34" rows="2" onfocus="return checkEID()" 
                     onkeypress="return noEnter(event)"></textarea>
              </div>
            </td>
          </tr>
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <input type="button" name="butSub" id="butSub" value="SUBMIT" onclick="doFunction('Add','null')"/>
                <input type="button" name="butCan" id="butCan" onclick="self.close();" value="CANCEL"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>