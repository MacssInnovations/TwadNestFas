<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Edit Employee's Additional&nbsp;Details </title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    
                       <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
                        <script type="text/javascript" src="../scripts/ValidationEmployee2.js"></script>
                        
                        <script type="text/javascript" src="../scripts/New_UpdateEmployeeScript.js"></script>
                         <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
                         <!-- <script type="text/javascript"       src="../../../../Library/scripts/CalendarControl.js"></script>-->
                        <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
            <script type="text/javascript">
   
    function toLoad()
    {
      document.frmEmployee.txtEmpId1.focus();
    }
    
    function call()
    {
    
   // alert("called");
    var empid=document.frmEmployee.txtEmpId1.value;
    document.getElementById("EmpImage").src="Show_Image.jsp?empid="+empid;
    //return ;
    }
    </script>
  </head>
  <body onload="toLoad()">
  <form name="frmEmployee" method="GET" class="table">
                  <%
  
   Connection connection=null;
  PreparedStatement ps=null;
  Statement statement=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
  int strNativeDistrict=0;
  int strNativeTaluk=0;
  String strEmpStatus="";
  
  
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

    int empid=empProfile.getEmployeeId();
    //int empid=11268;
    int  oid=0;
    
    try
    {
           
            ps=connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
            ps.setInt(1,empid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oid=results.getInt("OFFICE_ID");
                 
                 }
            results.close();
            ps.close();
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
   <script language="javascript" type="text/javascript">
                   OfficeId="<%=oid%>";
                  // alert(OfficeId);
    </script>

 
  
                                    <table border="1px" width="650px%" align="center">
                                          <tr>
                                                <td align="center" class="tdH"
                                                    colspan="4">
                                                      <center>
                                                            <b>Edit Employee's
                                                               Additional&nbsp;Details </b>
                                                      </center>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td colspan="2">Employee Id:</td>
                                                <td colspan="2">
                                                      <input tabindex="1"
                                                             type="text"
                                                             name="txtEmpId1"
                                                             id="txtEmpId1"
                                                             maxlength=5
                                                             onkeypress="return  numbersonly1(event,this)"
                                                             onchange="call();callFunction('ExistgBasic','null')"></input>
                                                      <input tabindex="1"
                                                             type="HIDDEN"
                                                             name="EmpId"
                                                             id="EmpId"></input>
                                                             <%
                              session=request.getSession(false);
                             if(session.getAttribute("Admin")!=null && ((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                             {
                             %>
                             <img src="../../../../../images/c-lovi.gif"
                                                           width="20"
                                                           height="20"
                                                           alt="empList"
                                                           onclick="servicepopup();"></img>
                             <%}else{%>
                             
                              <img src="../../../../../images/c-lovi.gif"
                                                           width="20"
                                                           height="20"
                                                           alt="empList"
                                                           onclick="servicepopupSR();"></img>
                             <%}%>
                                                     
                                                           
                                                           <%
                                                           ResourceBundle res=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                                                              String imagepath=res.getString("Config.EMPLOYEE_PHOTOS_PATH");
                                                              System.out.println("imaghe path : " + imagepath);
                                                           String context=request.getContextPath();
                                                           imagepath=res.getString("Config.EMPLOYEE_PHOTOS_PATH_VIEW");
                                                         System.out.println("image path after is:"+imagepath);
                                                          String img=context+imagepath;
                                                           %>
                                                           <script language="javascript" type="text/javascript">
                                                          imgpath="<%=img%>";
                                                           </script>
                                                        <img  align="top" src="../../../../../images/sample_emp.bmp"  alt="Photo" width="60" height="60" id="EmpImage" alt="EmpImage"></img>
                                                        
                                                </td>
                                          </tr>
                                          <tr>
                                                <td rowspan="2">
                                                      Employee Name
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td>Prefix</td>
                                                <td>
                                                      Initial
                                                      
                                                </td>
                                                <td>
                                                      Name
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td>
                                                   
                                                      <input tabindex="2"
                                                             size="12" style="background-color: #ececec"
                                                             name="Employee_Prefix"
                                                             readonly/>
                                                </td>
                                                <td>
                                                      <input tabindex="2"
                                                             size="12" style="background-color: #ececec"
                                                             name="Employee_Initial"
                                                             readonly/>
                                                      <input type="hidden"
                                                             name="Employee_Initial1"/>
                                                </td>
                                                <td>
                                                      <input tabindex="2"
                                                             size="30" style="background-color: #ececec"
                                                             name="Employee_Name"
                                                            readonly />
                                                      <input type="hidden"
                                                             name="Employee_Name1"/>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td colspan="2">
                                                      GPF Number
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td colspan="2">
                                                      <input type="text"
                                                             name="Gpf_Number" style="background-color: #ececec"
                                                             maxlength="30"
                                                             size="15"
                                                            readonly/>
                                                      <input type="hidden"
                                                             name="Gpf_Number1"/>
                                                </td>
                                          </tr>
                                          
                                          <tr>
                                                <td colspan="4"
                                                    class="tdH">
                                                      <b><font face="Tahoma"
                                                               color="#000000"
                                                               size="2">General
                                                                        Particulars</font></b>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td colspan="2">
                                                      Date Of Birth
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td colspan="2">
                                                      
                                                             <input type=text name="Date_Of_Birth" maxlength="10" onFocus="return toFocus(); javascript:vDateType='3'" onkeypress="return  calins(event,this)" onblur="return checkdt(this);">
                                                             <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmEmployee.Date_Of_Birth);" alt="Show Calendar"></img>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td colspan="2">
                                                      Gender
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td colspan="2">
                                                      <input type="radio"
                                                             checked="CHECKED"
                                                             value="M"
                                                             name="Gender" onfocus="return toFocus()"></input>
                                                      Male&nbsp;
                                                      <input type="radio"
                                                             value="F"
                                                             name="Gender" onfocus="return toFocus()"></input>
                                                      Female
                                                </td>
                                          </tr>
                                          <tr>
                                                <td colspan="2">
                                                      Community
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td colspan="2">
                                                      <select name="Community" onfocus="return toFocus()">
                                                            <option value=0>--Select
                                                                    Community--</option>
                                              <%
                                                try
                                                {
                                                  ps=connection.prepareStatement("Select * from HRM_MST_COMMUNITY");
                                                  results=ps.executeQuery();
                                                  while(results.next())
                                                  {
                                                      out.println("<option value='" + results.getString("Community_Code") + "'>" + results.getString("Community_Name") +"</option>");
                                                      
                                                  }
                                                  }
                                                  catch(Exception e){System.out.println("error in the community code" + e);};
                                              %>
                                                      </select>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td colspan="2">
                                                      Native&nbsp;District
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td colspan="2">
                                                      <select name="Native_District"
                                                              onchange="callServer()" onfocus="return toFocus()">
                                                            <option value=0>--Select
                                                                    NativeDistrict--</option>
                                                <% 
                              try
                              {
                                  //System.out.println("hai");
                                  results=ps.executeQuery("select * from COM_MST_DISTRICTS order by District_Name");
                                  
                                  while(results.next())
                                  {
                                      String temp=results.getString("District_Code");
                          %>
                                    <option value=<%=temp%>><%= results.getString("District_Name")%></option>
                          <% 
                                  }
                                  results.close();
                              }
                              catch(SQLException e)
                              {
                                      System.out.println("Exception in districts:"+e);
                              }
                          %>        
                          <option value="999">Others</option>
                                                      </select>
                                                </td>
                                          </tr>
                                          <tr id="original" style="display:block">
                                                <td colspan="2">
                                                      Native Taluk Name
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                     
                                                </td>
                                                <td colspan="2">
                                                      <select size="1"
                                                              name="Native_Taluk"
                                                              id="Native_Taluk" onfocus="return toFocus()">
                                                            <option value=0>--- Select
                                                                    Here ---</option>
                                                                    
                                                                     <%
                                          try
                                          {
                                            results=ps.executeQuery("select * from COM_MST_TALUKS where District_Code="+ strNativeDistrict  );
                                            while(results.next())
                                            {
                                              int Native_Taluk_Code=results.getInt("Taluk_Code");
                                              System.out.println("Native_Taluk_Code"+Native_Taluk_Code);
                                              out.println("<option value='"+Native_Taluk_Code+"'");
                                              if(strNativeTaluk==Native_Taluk_Code)
                                              {
                                                out.println("selected");
                                              }
                                              out.println(">"+results.getString("Taluk_Name")+"</option>");
                                            }
                                            results.close();
                                          }catch(Exception e)
                                          {
                                            System.out.println("Exception in taluks:"+e);
                                          }
                                      %>  
                                                      </select>
                                                </td>
                                          </tr>
                                          
                                          <tr id="other" style="display:none">
                                                <td colspan="2">  If Others, Mention the State and District
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                 <td>
                                               State<input type="text" name="txtOtherState" id="txtOtherState"  onfocus="return toFocus()">
                                                </td>
                                              <td>
                                               District<input type="text" name="txtOther" id="txtOther" onfocus="return toFocus()">
                                                </td>
                                               
                                          </tr>
                                          
                                          
                                          
                                          
                                          <tr>
                                                <td colspan="2">
                                                      Qualification
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td colspan="2">
                                                      <input type="text"
                                                             name="Qualification"
                                                             maxlength="60"
                                                             size="60" onfocus="return toFocus()"/>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td colspan="2">
                                                      Employment&nbsp;Status
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td colspan="2">
                                                      <select name="Post_Status" onfocus="return toFocus()" onchange="changestatus(this.value)">
                                                            <option value='NoVal'>--Select
                                                                    Employment
                                                                    Status--</option>
                                                       <% 
                            try
                            {
                                //System.out.println("hai");
                                results=ps.executeQuery("select * from HRM_MST_EMPLOYMENT_STATUS order by EMPLOYMENT_STATUS_ID");
                                
                                while(results.next())
                                {
                                    String temp=results.getString("EMPLOYMENT_STATUS_ID");
                        %>
                                  <option value=<%=temp%>><%= results.getString("EMPLOYMENT_STATUS")%></option>
                        <% 
                                }
                                results.close();
                            }
                            catch(SQLException e)
                            {
                                    System.out.println("Exception in employment status:"+e);
                            }
                        %>
                                                      </select>
                                                </td>
                                          </tr>
                                          
                                          
                                    <tr id="divprobation" style="display:none" >
                                                <td colspan="2">
                                                      Approved Probationer
                                                      </td>
                                                <td colspan="2">
                                                   <input type="radio" name="optprobation" value="Y" onclick="clickProbation()">Yes <input type="radio" name="optprobation" value="N" checked onclick="clickProbation()">No
                                                </td>
                                          </tr>       
                                          
                                          
                                           <tr id="divdate" style="display:none" >
                                                <td colspan="2">
                                                      <div id="divcaption">&nbsp;</div>
                                                      </td>
                                                <td colspan="2">
                                                   <input type=text name="Date_Of_Reg_Pro" size="10" maxlength="10" onFocus="return toFocus(); javascript:vDateType='3'" onkeypress="return  calins(event,this)" onblur="return checkdt1(this);">
                                               <img src="../../../../../images/calendr3.gif"   onclick="showCalendarControl(document.frmEmployee.Date_Of_Reg_Pro);" alt="Show Calendar"></img>
                                                </td>
                                          </tr>
                                          
                                          
                                          
                                          
                                          
                                        <!--  <tr >
                                                <td colspan="2">
                                                      Date&nbsp;of&nbsp;Entry
                                                      into Twad Board
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td colspan="2">
                                                   
                                                             
                                               <input type=text name="Date_Of_Twad" maxlength="10" onFocus="return toFocus(); javascript:vDateType='3'" onkeypress="return  calins(event,this)" onblur="return checkdt(this);">
                                               <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmEmployee.Date_Of_Twad);" alt="Show Calendar"></img>
                                                </td>
                                          </tr>-->
                                         
                                          <tr style="display:none">
                                                <td colspan="2">
                                                      Designation&nbsp;at&nbsp;the&nbsp;Time&nbsp;of&nbsp;Joining&nbsp;&nbsp;
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                      <label style="color:rgb(255,0,0);"></label>
                                                </td>
                                                <td colspan="2">
                                                 <input type=hidden name="Date_Of_Twad" maxlength="10">
                                                      <select name="Designation" onfocus="return toFocus()">
                                                            <option value=0>--Select
                                                                    Designation--</option>
                                                                 <% 
                                try
                                {
                                    //System.out.println("hai");
                                    results=ps.executeQuery("select * from HRM_MST_DESIGNATIONS order by DESIGNATION");
                                    
                                    while(results.next())
                                    {
                                        int temp=results.getInt("Designation_Id");
                            %>
                                      <option value=<%=temp%>><%= results.getString("Designation")%></option>
                            <% 
                                    }
                                    results.close();
                                }
                                catch(SQLException e)
                                {
                                        System.out.println("Exception in Designations:"+e);
                                }
                            %>
                                                      </select>
                                                </td>
                                          </tr>
                                          
                                          
                                          <tr style="display:none">
                                                <td colspan="2">
                                                     Employee Current Status
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td colspan="2">
                                                      <select name="Employee_Status" onfocus="return toFocus()">
                                                            <option value=0>--Select
                                                                    Employee
                                                                    Status--</option>
                                                 <%
                                          try
                                          {
                                            results=ps.executeQuery("select * from HRM_MST_EMPLOYEE_STATUS order by EMPLOYEE_STATUS_ID");
                                            while(results.next())
                                            {
                                              String Employee_Status_Id=results.getString("Employee_Status_Id");
                                              out.println("<option value='"+Employee_Status_Id+"'");
                                              if(strEmpStatus.equals(Employee_Status_Id))
                                              {
                                                out.println("selected");
                                              }
                                              out.println(">"+results.getString("Employee_Status_Desc")+"</option>");
                                            }
                                            results.close();
                                          }catch(Exception e)
                                          {
                                            System.out.println("Exception in employee status:"+e);
                                          }
                                      %>                          
                                                      </select>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td colspan="2">
                                                      Marital Status
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td colspan="2">
                                                      <input type="radio"
                                                             checked="CHECKED"
                                                             value="M"
                                                             name="Marital_Status" onfocus="return toFocus()"></input>
                                                      Married
                                                      <input type="radio"
                                                             value="S"
                                                             name="Marital_Status" onfocus="return toFocus()"></input>
                                                      Single
                                                </td>
                                          </tr>
                                          
                                          
                                           <tr>
                                                <td colspan="2">
                                                    IS Handicapped
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td colspan="2">
                                                      <input type="radio"
                                                            
                                                             value="Y"
                                                             name="handicapped" onfocus="return toFocus()"></input>
                                                      Yes
                                                      <input type="radio"
                                                             value="N"
                                                              checked="CHECKED"
                                                             name="handicapped" onfocus="return toFocus()"></input>
                                                      No
                                                </td>
                                          </tr>
                                           <tr>
                                                <td colspan="2">
                                                     Consolidated Staff
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td colspan="2">
                                                      <input type="radio"
                                                             
                                                             value="Y"
                                                             name="consolid" onfocus="return toFocus()"></input>
                                                      Yes
                                                      <input type="radio"
                                                             value="N"
                                                             checked="CHECKED"
                                                             name="consolid" onfocus="return toFocus()"></input>
                                                      No
                                                </td>
                                          </tr>
                                           <tr>
                                                <td colspan="2">
                                                     Whether the employee working in the office as well as getting family pension etc
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td colspan="2">
                                                      <input type="radio"
                                                             
                                                             value="Y"
                                                             name="getting" onfocus="return toFocus()"></input>
                                                      Yes
                                                      <input type="radio"
                                                             value="N"
                                                             checked="CHECKED"
                                                             name="getting" onfocus="return toFocus()"></input>
                                                      No
                                                </td>
                                          </tr>
                                          
                                          
                                          <tr>
                                                <td colspan="2">Remarks</td>
                                                <td colspan="2">
                                                      <textarea name="Remarks" title="Don't type '&' Character while entering the remarks" 
                                                                cols="25"
                                                                rows="5" onfocus="return toFocus()"></textarea>
                                                <input type="Hidden"
                                                             name="cmbRecordStatus"
                                                             maxlength="30"
                                                             size="25" readonly/>
                                                </td>
                                               
                                       
                                                
                                  
                                          </tr>
                                           
                                          <tr>
                                                <td colspan="4" class="tdH"
                                                    align="right">
                                                      <input type="button"
                                                             value="Update" name="cmdUpdate" id="cmdUpdate" onclick=" callFunction('toUpdate','null')"></input>
                                                      &nbsp;
                                                      <input type="Button"
                                                             value=" Cancel "
                                                             name="cmdCancel"
                                                             onclick="self.close();"></input>
                                                             &nbsp;
                                                      <input type="Button"
                                                             value=" ClearAll "
                                                             name="cmdClearAll"
                                                             onclick="clearAll();"></input>

                                                </td>
                                          </tr>
                                    </table>
                            
            </form>
                                          
                                          
  </body>
</html>