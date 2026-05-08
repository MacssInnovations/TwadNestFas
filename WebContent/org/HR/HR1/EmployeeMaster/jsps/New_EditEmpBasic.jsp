<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
      <head>
            <meta http-equiv="Content-Type"
                  content="text/html; charset=windows-1252"></meta>
            <title>Update Employee Additional&nbsp;Details </title>
            <link href="../../../../../css/Sample3.css" rel="stylesheet"
                  media="screen"/>
            <script type="text/javascript"
                    src="../scripts/UpdateEmp_Additional_Script.js"></script>
                         <script type="text/javascript" src="../scripts/ValidationEmployee2.js"></script>
                         <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>

            <script type="text/javascript">
          function servicepopup()
    {
       
      my_window= window.open("EmpServicePopup.jsp","mywindow1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
      my_window.moveTo(250,250);    
    }
    </script>
      </head>
      <body><form name="frmEmployee" method="GET" class="table">
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
                  <table width="100%">
                        <tr>
                              <td align="center" colspan="2">
                                    <table border="1px" width="650px%">
                                          <tr>
                                                <td align="center" class="tdH"
                                                    colspan="4">
                                                      <center>
                                                            <b>Update Employee
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
                                                             onblur="callFunction('Existg','null')"></input>
                                                      <input tabindex="1"
                                                             type="HIDDEN"
                                                             name="EmpId"
                                                             id="EmpId"></input>
                                                      <img src="../../../../../images/c-lovi.gif"
                                                           width="20"
                                                           height="20"
                                                           alt="empList"
                                                           onclick="servicepopup();"></img>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td rowspan="2">
                                                      EmployeeName
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td>Prefix</td>
                                                <td>
                                                      Initial
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td>
                                                      Name
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td>
                                                      <select name="Employee_Prefix"
                                                              tabindex="2">
                                                            <option value="Mr"
                                                                    selected="selected">Mr</option>
                                                            <option value="Mrs">Mrs</option>
                                                            <option value="Thiru">Thiru</option>
                                                            <option value="Selvi">Selvi</option>
                                                      </select>
                                                </td>
                                                <td>
                                                      <input tabindex="2"
                                                             size="12"
                                                             name="Employee_Initial"
                                                             readonly/>
                                                      <input type="hidden"
                                                             name="Employee_Initial1"/>
                                                </td>
                                                <td>
                                                      <input tabindex="2"
                                                             size="30"
                                                             name="Employee_Name"
                                                            readonly />
                                                      <input type="hidden"
                                                             name="Employee_Name1"/>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td colspan="2">
                                                      GPFNumber
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td colspan="2">
                                                      <input type="text"
                                                             name="Gpf_Number"
                                                             maxlength="30"
                                                             size="15"
                                                            readonly/>
                                                      <input type="hidden"
                                                             name="Gpf_Number1"/>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td colspan="4"
                                                    class="SubHeading">
                                                      <b><font face="Tahoma"
                                                               color="#000000"
                                                               size="2">Details
                                                                        of
                                                                        Office
                                                                        to which
                                                                        Employee
                                                                        is posted</font></b>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td colspan="2">
                                                      Office&nbsp;Id
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td colspan="2">
                                                      <input type="text"
                                                             name="Office_Id"
                                                             maxlength="30"
                                                             size="15"
                                                             onkeyup="isInteger(this,event)" onblur="callFunction('ExistgOff','null')"/>
                                                      <img src="../../../../../images/c-lovi.gif"
                                                           width="20"
                                                           height="20"
                                                           alt="empList"
                                                           onclick="jobpopup();"/>
                                                </td>
                                          </tr>
                                          <tr>
                    <td colspan="2">Office&nbsp;Name</td>
                    <td colspan="2">
                        <input type=text name=Office_Name maxlength="50" size="50" readonly/>
                        <input type=HIDDEN name=hOffice_Name maxlength="30" size="15" />
                   </td>
             </tr> 
             <tr>
                     <td colspan="2">Office&nbsp;Address</td>
                     <td colspan="2">
                        <textarea name="Office_Address" cols="25" rows="5" readonly>                    
                        </textarea>
                        <input type=HIDDEN name=hOffice_Address  size="55" readonly/>
                    </td>
             </tr> 
                                          <tr>
                                                <td colspan="2">Designation</td>
                                                <td colspan="2">
                                                      <table cellspacing="2"
                                                             cellpadding="3"
                                                             border="0"
                                                             width="100%">
                                                            <tr>
                                                                  <td>Service
                                                                      Group</td>
                                                                  <td>
                                                                        <div id="divdes"
                                                                             style="visibility:hidden">Designation</div>
                                                                  </td>
                                                            </tr>
                                                            <tr>
                                                                  <td>
                                                                        <select name="cmbsgroup"
                                                                                id="cmbsgroup"
                                                                                onchange="getDesignation()">
                                                                              <option value="0">Select
                                                                                                Service
                                                                                                Group</option>
                                                                              <%
           ResultSet rs=null;
           try
           {
           ps=connection.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
            rs=ps.executeQuery();
              int strcode=0;
            String strname="";          
            while(rs.next())
            {
              
               
                strcode=rs.getInt("SERVICE_GROUP_ID");
                strname=rs.getString("SERVICE_GROUP_NAME");
                
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
                                                                  <td>
                                                                        <select name="cmbdes"
                                                                                id="cmbdes"
                                                                                style="visibility:hidden"></select>
                                                                  </td>
                                                            </tr>
                                                      </table>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td colspan="2">
                                                      Grade
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td colspan="2">
                                                      <input type="radio"
                                                             checked="CHECKED"
                                                             value="Normal"
                                                             name="Office_Grade"></input>
                                                      Normal&nbsp;
                                                      <input type="radio"
                                                             value="Selection"
                                                             name="Office_Grade"></input>
                                                      Selection&nbsp;
                                                      <input type="radio"
                                                             value="Special"
                                                             name="Office_Grade"></input>
                                                      Special
                                                </td>
                                          </tr>
                                          <tr>
                                                <td colspan="4"
                                                    class="SubHeading">
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
                                                      <input maxlength="10"
                                                             size="23"
                                                             name="Date_Of_Birth"
                                                             onchange="return validate_date('frmEmployee','Date_Of_Birth')"
                                                             onfocus="javascript:vDateType='3'"
                                                             onkeyup="DateFormat(this,this.value,event,false,'3')"
                                                             onblur="DateFormat(this,this.value,event,true,'3')"></input>
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
                                                             name="Gender"></input>
                                                      Male&nbsp;
                                                      <input type="radio"
                                                             value="F"
                                                             name="Gender"></input>
                                                      Female
                                                </td>
                                          </tr>
                                          <tr>
                                                <td colspan="2">
                                                      Community
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td colspan="2">
                                                      <select name="Community">
                                                            <option value=0>--Select
                                                                    Community--</option>
                                                                 <% 
                            try
                            {
                                System.out.println("hai");
                                results=ps.executeQuery("select * from HRM_MST_COMMUNITY order by Community_Code");
                                
                                while(results.next())
                                {               
                        
                                  out.println("<option value='" + results.getString("Community_Code") + "'>" + results.getString("Community_Name") +"</option>");
                                       
                                }
                                results.close();
                            }
                            catch(SQLException e)
                            {
                                    System.out.println("Exception in the community code:"+e);
                            }
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
                                                              onchange="callServer()">
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
                                                </td>
                                                <td colspan="2">
                                                      <select size="1"
                                                              name="Native_Taluk"
                                                              id="Native_Taluk">
                                                            <option value=0>--- Select
                                                                    Here ---</option>
                                                      </select>
                                                </td>
                                          </tr>
                                          
                                          <tr id="other" style="display:none">
                                                <td colspan="2">  State and District
                                                      <label style="color:rgb(255,0,0);">*</label>
                                         </td>
                                                <td colspan="2">
                                                      <input type="text" name="txtOther" id="txtOther" >
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
                                                             maxlength="30"
                                                             size="25"/>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td colspan="2">
                                                      Employment&nbsp;Status
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td colspan="2">
                                                      <select name="Post_Status">
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
                                          <tr>
                                                <td colspan="2">
                                                      Date&nbsp;of&nbsp;Entry
                                                      into Twad Board
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td colspan="2">
                                                      <input type="text"
                                                             name="Date_Of_Twad"
                                                             maxlength="30"
                                                             size="15"
                                                             onchange="return validate_date('frmEmployee','Date_Of_Birth')"
                                                             onfocus="javascript:vDateType='3'"
                                                             onkeyup="DateFormat(this,this.value,event,false,'3')"
                                                             onblur="DateFormat(this,this.value,event,true,'3')"/>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td colspan="2">
                                                      Designation&nbsp;at&nbsp;the&nbsp;Time&nbsp;of&nbsp;Joining&nbsp;&nbsp;
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                      <label style="color:rgb(255,0,0);"></label>
                                                </td>
                                                <td colspan="2">
                                                      <select name="Designation">
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
                                          
                                          
                                          <tr>
                                                <td colspan="2">
                                                     Employee CurrentStatus
                                                      <label style="color:rgb(255,0,0);">*</label>
                                                </td>
                                                <td colspan="2">
                                                      <select name="Employee_Status">
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
                                                             name="Marital_Status"></input>
                                                      Married
                                                      <input type="radio"
                                                             value="S"
                                                             name="Marital_Status"></input>
                                                      Single
                                                </td>
                                          </tr>
                                          <tr>
                                                <td colspan="2">Remarks</td>
                                                <td colspan="2">
                                                      <textarea name="Remarks"
                                                                cols="25"
                                                                rows="5"></textarea>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td colspan="4" class="Heading"
                                                    align="right">
                                                      <input type="button"
                                                             value="Update" onclick=" callFunction('toUpdate','null')"></input>
                                                      &nbsp;
                                                      <input type="Button"
                                                             value=" Cancel "
                                                             name="cmdCancel"
                                                             onclick="self.close();"></input>
                                                </td>
                                          </tr>
                                    </table>
                              </td>
                        </tr>
                  </table>
            </form></body>
</html>