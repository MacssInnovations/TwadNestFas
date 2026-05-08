<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Edit Deputation Joining Details</title>
   <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

   <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/New_Trans_DPNJoinedit_Script.js"></script>
 <!-- <script type="text/javascript" src="../../../../../org/Library/scripts/CalendarControl.js"></script>-->
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <!--<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
   <script type="text/javascript" src="../scripts/CalendarControl.js"></script>-->
   
  </head>
  <body id="bodyid">
  <form name="frmEmployee" id="frmEmployee">
  
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
                <strong>Edit Deputation Joining Details</strong>
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
                <input type="text" name="txtOffId" id="txtOffId" maxlength="3" value="<%=oid%>"
                       size="43"  class="disab"  readonly="readonly"/>
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
                <input type="text" name="txtEmpId1" id="txtEmpId1" onkeypress="return  numbersonly1(event,this)" maxlength="6" size="6" onchange="callServer1('Load','null')" />
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
              <input type="text" name="Employee_Name" id="Employee_Name" class="disab" size="43" readonly style="background-color: #ececec"/>
              
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
                <input type="text" name="Date_Of_Birth" id="Date_Of_Birth" class="disab" maxlength="10" readonly style="background-color: #ececec"/>
                      
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
                <input type="text" name="Gpf_Number" id="Gpf_Number" maxlength="10"
                       size="10" class="disab" readonly style="background-color: #ececec"/>
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
                <!--<input type="text" name="txtDepName" id="txtDepName"
                         readonly="readonly" class="disab" maxlength="30" size="30"/>
                <input type="hidden" name="txtDepId" id="txtDepId"  />
               -->
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
               <!-- <input type="text" name="txtDepOffName" id="txtDepOffName"
                         readonly="readonly" class="disab" maxlength="30" size="30"/>
                <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="Office_List" onclick="if(checkEID()==true){jobpopup1();}">
                 <input type="hidden" name="txtDepOffId" id="txtDepOffId"/>
               -->
               <select name="txtDepOffId" id="txtDepOffId">
               <option value="">Select the Office Name</option>
               </select>
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
                <input type="text" name="txtJRId" id="txtJRId" size="13"
                         readonly style="background-color: #ececec"/>(System Generated)
                       <input type="hidden" name="txtJR" id="txtJR" 
                       size="13" class="disab"/>
              </div>
            </td>
          </tr>
          
          
          <!--
           <tr class="table" id="drcompdate" style="display:none">
            <td>
              <div align="left" id="divcompleted" >
                 Completed Date
              </div>
            </td>
            <td>
              <div align="left">
              <input type="hidden" name="txtempstatus" id="txtempstatus">
              <input type="text" name="txtDOC" id="txtDOC"
                       maxlength="10" size="11"
                       onfocus="javascript:vDateType='3'; return toFocus() "
                       onkeypress="return calins(event,this);"
                       onblur="if(checkcurdt(this)==true)return checkdt(this);"/>
                 
                <img src="../../../../../images/calendr3.gif"
                     onclick=" if(toFocus()==true)showCalendarControl(document.frmEmployee.txtDOC);"
                     alt="Show Calendar"></img>
              
             <input type="radio" name="optFNAN" id="optFNAN1" value="FN" checked onfocus="return toFocus()" />FN
                         &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="optFNAN" id="optFNAN2" value="AN" onfocus="return toFocus()" />AN
                         
              </div>
            </td>
          </tr>
         --> 
          
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
                       onfocus="javascript:vDateType='3'; return toFocus() "
                       onkeypress="return calins(event,this);"
                       onblur="if(checkcurdt(this)==true)return checkdt(this);" readonly/>
                 
             <!--   <img src="../../../../../images/calendr3.gif"
                     onclick=" if(toFocus()==true)showCalendarControl(document.frmEmployee.txtDOJ);"
                     alt="Show Calendar"></img> -->
                     
          <!--  <input type="text" name="txtDOJ" id="txtDOJ" maxlength="10" class="disab" readonly onFocus="javascript:vDateType='3'" onkeypress="return  calins(event,this)" onblur="return checkdt(this);"/>
                                                            <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.Hrm_TransJoinForm.txtDOJ);" alt="Show Calendar" ></img>-->
             <input type="hidden" name="txtDOJ1" id="txtDOJ1" maxlength="10" />
             <input type="radio" name="radFNAN" id="radFNAN" value="FN" checked onfocus="return toFocus()" />FN
                         &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="radFNAN" id="radFNAN" value="AN" onfocus="return toFocus()"/>AN
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
                <input type="text" name="txtOffice_Id" id="txtOffice_Id" onchange="doFunction('Load',true)" onkeypress="return  numbersonly1(event,this)" size="6" maxlength="4"/>
                <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="Office_List" onclick="if(toFocus()==true){jobpopup();}">
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
                <input type="text" name="txtOffice_Name" id="txtOffice_Name" disabled size="30" maxlength="40"/>
                
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
                <textarea name="txtRemarks" id="txtRemarks" title="Don't type '&' Character while entering the remarks" cols="34" rows="2" onFocus="return toFocus();" onkeypress="return noEnter(event)"></textarea>
              </div>
            </td>
          </tr>
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <input type="button" name="butSub" id="butSub" value="UPDATE" onclick="callServer1('Update','null')"/>
                <input type="button" name="butCan" id="butCan" onclick="self.close();" value="CANCEL"/>
                <input type="button" name="butclear" id="butclear" onclick="clearAll()" value="CLEARALL"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>