<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <script type="text/javascript" src="../scripts/ValidationEmployee2.js"></script>
      <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
      <title>Update Employee Additional&nbsp;Details </title>    
          
          <script type="text/javascript">
          function display()
          {
            alert(document.frmEmployee.txtEmpId.value);
          }
          </script>
          <!--<link href="css/sample2.css" rel="stylesheet" media="screen"/>-->
          <!--<link href="../../../../../css/green.css" rel="stylesheet" media="screen"/>-->
          <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
          <!--<link href="../../../css/try1.css" rel="stylesheet" media="screen"/>-->
    </head>
 <body>
 <form name="frmEmployee" method="POST" onsubmit="return nullCheck()" class="table">
 <%
 String strEmpId=request.getParameter("txtEmpId");
 %>
 <table width="100%" >
        
         <tr>
               <td align="center" colspan="2">                        
                    <table border="1px" width="650px%" >
             <tr>
             <td align="center" class="tdH" colspan="4">
                  
                        <center><b>Update Employee Additional&nbsp;Details </b></center>
                  
              </td>
         </tr>
         <tr>
             <td colspan="2">
                 Employee Id:
                </td>
            <td colspan="2">
                <input  tabindex="1" type=text name=txtEmpId id="id" value=<%if(strEmpId!=null) {%> <%=strEmpId%> disabled<%}%>>
                <input type=button value="Go" onclick="fun1()">
            </td>
        </tr>
 <%
 if(strEmpId!=null)
 {
 out.println("<script>");
 //out.println("document.frmEmployee.onsubmit='return nullCheck()'");
 out.println("document.frmEmployee.action='../../../../../InsertEmployee3.con'");
 out.println("</script>");
   Connection connection=null;
   Statement statement=null;
   ResultSet results=null;   
   ResultSet rs=null; 
   
   
   String strEmpPrefix="",strEmpInitial="",strOfficeId="",strOfficeName="",strOfficeAddress="",strOfficeDesignation="",strOffice_Grade="";
   String strEmpName="",strEmpBloodGroup="",strGender="",strGpfNumber="",strPanNumber="",strDesignation="",strServiceCounted="",strAdd1="",strAdd2="",strAdd3="",strPAdd1="",strPAdd2="",strPAdd3="",strEMail="",strMarital="",strSpouse="",strSpouseOffice="",strEmpSignature="",strRemarks="";
   String strEmpSpecimen="",strEmpStatus="";
   int strCommunity=0,strReligion=0,strMotherTongue=0,strMediumStudy=0,strNativeDistrict=0,strNativeTaluk=0,strPostStatus=0,strRecruitmentMode=0;
   int strPincode=0,strphone=0,strPPincode=0,strPPhone=0,strCell=0,strDesig_Id=0;
   int strDistrict=0,strQualification=0,strSpousePlace=0,District_Code=0;
   
   
   
   java.sql.Date DateOfFormation=null;
   java.sql.Date DateOfFormation1=null;
   java.sql.Date DateOfFormation2=null;
   
   String DateToBeDisplayed="",ControllingOfficeName="",DateToBeDisplayed2="",DateToBeDisplayed3="";
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
             
           //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
       			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
     connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
       try
       {
            statement=connection.createStatement();
            connection.clearWarnings();
            //System.out.println("in");
            rs=statement.executeQuery("select * from HRM_MST_EMPLOYEES where Employee_Id=" + strEmpId);
            if(rs.next())
            {
                        try
                          {       
                          //System.out.println("in try");
                                strEmpInitial=rs.getString("Employee_Initial");
                                System.out.println("strEmpInitial:"+strEmpInitial);
                                strEmpPrefix=rs.getString("Employee_Prefix");
                                strEmpName = rs.getString("Employee_Name"); 
                                
                                strGpfNumber=rs.getString("GPF_NO");
                                System.out.println("values retrived sucessfully");
                            }
                            catch(Exception e)
                            {
                              e.printStackTrace();
                            }     
                           
                          
%>

  <style>
            .bgClass{color: White ; width: 100%; border-bottom: 1px solid Black ; padding: 0px; margin: 0px;background-color: rgb(135,155,216);}
  </style>
                        <input type="HIDDEN" name="txtMode" value="insert">
                         <input type="HIDDEN" name="txtEmpId" value=<%=strEmpId%>>
<!--<TR>
                 <TD colspan="2">
                        <B><FONT face=Tahoma color=#808080 size=2>Employee Id</FONT></B>
                 </TD>
                 <TD colspan="2">
                         <input type="text" name="txtId" size="5" maxlength="5" value=<%=strEmpId%> disabled>(System Generated)
                         
                 </TD>
             </TR>-->
             
             <TR>
                  <TD rowspan="2">
                        Employee Name<label style="color:rgb(255,0,0);">*</label>
                  </TD>
                  <TD>
                          Prefix
                  </TD>
                  <TD>Initial
                  <label style="color:rgb(255,0,0);">*</label></TD>
                  <TD>Name
                  <label style="color:rgb(255,0,0);">*</label></TD>
                  
             </TR>
              <TR>
                   
                    <TD>
                         <select name="Employee_Prefix" tabindex="2">
                        <option value="Mr" selected>Mr</option>
                         <option value="Mrs">Mrs</option>
                         <option value="Thiru">Thiru</option>
                         <option value="Selvi">Selvi</option>
                         </select>
                    </TD>
                    <TD>
                      <INPUT tabIndex="2" size="12" name="Employee_Initial" value="<%=strEmpInitial%>" disabled/>
                      <input type="hidden" name="Employee_Initial" value="<%=strEmpInitial%>"/>
                    </TD>
                    <TD>
                      <INPUT tabIndex="2" size="30" name="Employee_Name" value="<%=strEmpName%>" disabled/>
                       <input type="hidden" name="Employee_Name" value="<%=strEmpName%>"/>
                    </TD>
                    
             </TR>
             <tr>
                                      <td colspan="2">GPF Number
                                      <label style="color:rgb(255,0,0);">*</label></td>
                                      <td colspan="2">
                                        <input type="text" name="Gpf_Number" maxlength="30" size="15" value="<%=strGpfNumber%>" disabled/>
                                        <input type="hidden" name="Gpf_Number" value="<%=strGpfNumber%>"/>
                                      </td>
                                  </tr>
             <TR>
                    <TD colspan="4" class="SubHeading">
                       <B><FONT face=Tahoma color=#000000 size=2>Details of Office Posted</FONT></B>
                    </TD>
              </TR>
              <tr>
                        <td colspan="2">Office&nbsp;Id<label style="color:rgb(255,0,0);">*</label></td>
                    <td colspan="2">
                          <input type=text name=Office_Id maxlength="30" size="15" onkeyup="isInteger(this,event)"/>
                    </td>
             </tr> 
             <tr>
                    <td colspan="2">Office&nbsp;Name<label style="color:rgb(255,0,0);">*</label></td>
                    <td colspan="2">
                        <input type=text name=Office_Name maxlength="30" size="15"/>
                   </td>
             </tr> 
             <tr>
                     <td colspan="2">Office&nbsp;Address<label style="color:rgb(255,0,0);">*</label></td>
                     <td colspan="2">
                        <textarea name="Office_Address" cols="25" rows="5">                    
                        </textarea>
                    </td>
             </tr> 
             <tr>
                      <td colspan="2">Designation<label style="color:rgb(255,0,0);">*</label></td>
                      <td colspan="2">
                           <input type="text" name="Office_Designation" maxlength="30" size="15"/>
                      </td>
             </tr> 
             <tr>
                       <td colspan="2">Grade<label style="color:rgb(255,0,0);">*</label></td>
                       <td colspan="2">
                            <input type="text" name="Office_Grade" maxlength="30" size="15"/>
                       </td>
             </tr>
             <tr>
                        <TD colspan="4" class="SubHeading">
                            <B><FONT face=Tahoma color=#000000 size=2>General Particulars</FONT></B>
                        </TD>
             </tr>
             <tr>
                         <td colspan="2">Date Of Birth<label style="color:rgb(255,0,0);">*</label></td>
                         <td colspan="2">
                                <INPUT maxLength=10 size=23 name=Date_Of_Birth onchange="return validate_date('frmEmployee','Date_Of_Birth')" onFocus="javascript:vDateType='3'" onKeyUp="DateFormat(this,this.value,event,false,'3')" onBlur="DateFormat(this,this.value,event,true,'3')">
                         </td>
              </tr>
              <tr>
                        <td colspan="2">Blood Group<label style="color:rgb(255,0,0);">*</label></td>
                        <td colspan="2">
                                <input type="text" name="Blood_Group" maxlength="30" size="15"/>
                        </td>
              </tr>
              <TR>
                        <TD colspan="2">Gender<label style="color:rgb(255,0,0);">*</label></TD>
                        <TD colspan="2">
                        <INPUT type=radio CHECKED value=M name=Gender>Male&nbsp;<INPUT type=radio value=F name=Gender>Female
                        </TD>
                       
              </TR>
              <tr>
                          <td colspan="2">Community<label style="color:rgb(255,0,0);">*</label></td>
                          <td colspan="2">
                               <select name="Community">
                               
                        <option>--Select Community--</option>
                      <% 
                            try
                            {
                                //System.out.println("hai");
                                results=statement.executeQuery("select * from HRM_MST_COMMUNITY");
                                
                                while(results.next())
                                {               
                        
                                  out.println("<option value='" + results.getString("Community_Code").trim() + "'>" + results.getString("Community_Name") +"</option>");
                                       
                                }
                                results.close();
                            }
                            catch(SQLException e)
                            {
                                    System.out.println("Exception in creating statement:"+e);
                            }
                        %>
                                  </select>
                          </td>
              </tr>
              <tr>
                         <td colspan="2">Religion<label style="color:rgb(255,0,0);">*</label></td>
                         <td colspan="2">
                               <select name="Religion">
                               <option>--Select Religion--</option>
                        <% 
                            try
                            {
                                //System.out.println("hai");
                                results=statement.executeQuery("select * from HRM_MST_RELIGIONS");
                                
                                while(results.next())
                                {
                                    String temp=results.getString("Religion_Code");
                        %>
                                  <option value=<%=temp%>><%= results.getString("Religion_Name")%></option>
                        <% 
                                }
                                results.close();
                            }
                            catch(SQLException e)
                            {
                                    System.out.println("Exception in creating statement:"+e);
                            }
                        %>
                                </select>
                          </td>
              </tr>
              <tr>
                        <td colspan="2">Mother&nbsp;Tongue<label style="color:rgb(255,0,0);">*</label></td>
                        <td colspan="2">
                                 <select name="Mother_Tongue">
                                 <option>--Select MotherTongue--</option>
                        <% 
                              try
                              {
                                  //System.out.println("hai");
                                  results=statement.executeQuery("select * from HRM_MST_LANGUAGES");
                                  
                                  while(results.next())
                                  {
                                      String temp=results.getString("Language_Code");
                          %>
                                    <option value=<%=temp%>><%= results.getString("Language_Name")%></option>
                          <% 
                                  }
                                  results.close();
                              }
                              catch(SQLException e)
                              {
                                      System.out.println("Exception in creating statement:"+e);
                              }
                          %>
                              </select>
                        </td>
              </tr>
              <tr>
                                    <td colspan="2">Medium&nbsp;Of&nbsp;Study
                                    <label style="color:rgb(255,0,0);">*</label></td>
                                    <td colspan="2">
                                      <select name="Medium_Study">
                                      <option>--Select MediumOfStudy--</option>
                                       <% 
                              try
                              {
                                  //System.out.println("hai");
                                  results=statement.executeQuery("select * from HRM_MST_LANGUAGES");
                                  
                                  while(results.next())
                                  {
                                      String temp=results.getString("Language_Code");
                          %>
                                    <option value=<%=temp%>><%= results.getString("Language_Name")%></option>
                          <% 
                                  }
                                  results.close();
                              }
                              catch(SQLException e)
                              {
                                      System.out.println("Exception in creating statement:"+e);
                              }
                          %>
                                      </select>
                                    </td>
                                  </tr>
                                  <tr>
                                    <td colspan="2">Native&nbsp;District
                                    <label style="color:rgb(255,0,0);">*</label></td>
                                    <td colspan="2">
                                      <select name="Native_District" onChange="callServer()">
                                      <option>--Select NativeDistrict--</option>
                                      <% 
                              try
                              {
                                  //System.out.println("hai");
                                  results=statement.executeQuery("select * from COM_MST_DISTRICTS");
                                  
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
                                      System.out.println("Exception in creating statement:"+e);
                              }
                          %>
                                      </select>
                                    </td>
                                  </tr>
                                  
                                   <TR>
    <TD colspan="2">
      Native Taluk Name<label style="color:rgb(255,0,0);">*</label></TD>
    <TD colspan="2">
    <SELECT  size=1 name="Native_Taluk" id="Native_Taluk"> 
    <OPTION>--- Select Here ---</OPTION>
      <%
                                          try
                                          {
                                            results=statement.executeQuery("select * from COM_MST_TALUKS where District_Code="+ strNativeDistrict  );
                                            while(results.next())
                                            {
                                              int Native_Taluk_Code=results.getInt("Taluk_Code");
                                              //System.out.println("Native_Taluk_Code"+Native_Taluk_Code);
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
                                            System.out.println("Exception in Qualification_Id:"+e);
                                          }
                                      %>  

</SELECT></TD></TR>
                                  
                                  
                                  
                                  
                                  <tr>
                                    <td colspan="2">Qualification
                                    <label style="color:rgb(255,0,0);">*</label></td>
                                    <td colspan="2">
                                      <select name="Qualification">
                                      <option>--Select Qualification--</option>
                                     <% 
                            try
                            {
                                //System.out.println("hai");
                                results=statement.executeQuery("select * from HRM_MST_EDU_QUALIFICATION");
                                
                                while(results.next())
                                {
                                    String temp=results.getString("Qualification_Id");
                        %>
                                  <option value=<%=temp%>><%= results.getString("Qualification_Desc")%></option>
                        <% 
                                }
                                results.close();
                            }
                            catch(SQLException e)
                            {
                                    System.out.println("Exception in creating statement:"+e);
                            }
                        %>
                                      </select>
                                    </td>
                                  </tr>
                                  
                                  <tr>
                                    <td colspan="2">Employment&nbsp;Status
                                    <label style="color:rgb(255,0,0);">*</label></td>
                                    <td colspan="2">
                                      <select name="Post_Status">
                                      <option>--Select Employment Status--</option>
                                       <% 
                            try
                            {
                                //System.out.println("hai");
                                results=statement.executeQuery("select * from HRM_MST_EMPLOYMENT_STATUS");
                                
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
                                    System.out.println("Exception in creating statement:"+e);
                            }
                        %>
                                      </select>
                                    </td>
                                  </tr>

                                  
                                  
                                  <tr>
                                      <td colspan="2">PAN Number
                                      <label style="color:rgb(255,0,0);">*</label></td>
                                      <td colspan="2">
                                        <input type="text" name="Pan_Number" maxlength="30" size="15"/>
                                       
                                      </td>
                                  </tr>
                              <!--    <tr style="display:none">
                                      <td colspan="2">Date&nbsp;of&nbsp;Entry into Twad Board 
                                      <label style="color:rgb(255,0,0);">*</label></td>
                                      <td colspan="2">
                                        <input type="hidden" name="Date_Of_Twad" maxlength="30" size="15" onchange="return validate_date('frmEmployee','Date_Of_Birth')" onFocus="javascript:vDateType='3'" onKeyUp="DateFormat(this,this.value,event,false,'3')" onBlur="DateFormat(this,this.value,event,true,'3')"/>
                                    
                                      </td>
                                  </tr>-->
                                   
                                  <tr>
                                    <td colspan="2">Designation&nbsp;at&nbsp;the&nbsp;Time&nbsp;of&nbsp;Joining&nbsp;&nbsp;<label style="color:rgb(255,0,0);">*</label>                                    <label style="color:rgb(255,0,0);"></label></td>
                                    <td colspan="2">
                                    <input type="hidden" name="Date_Of_Twad" maxlength="30" size="15">
                                      <select name="Designation">
                                      <option>--Select Designation--</option>
                                      <% 
                                try
                                {
                                    //System.out.println("hai");
                                    results=statement.executeQuery("select * from HRM_MST_DESIGNATIONS");
                                    
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
                                        System.out.println("Exception in creating statement:"+e);
                                }
                            %>
                                      </select>
                                    </td>
                                  </tr>
                                  
                                  <tr>
                                    <td colspan="2">Mode&nbsp;of&nbsp;Recruitment                                    <label style="color:rgb(255,0,0);">*</label></td>
                                    <td colspan="2">
                                      <select name="Recruitment_Mode">
                                      <option>--Select RecruitmentMode--</option>
                                      <% 
                                try
                                {
                                    //System.out.println("hai");
                                    results=statement.executeQuery("select * from HRM_MST_RECRUITMENT_MODES");
                                    
                                    while(results.next())
                                    {
                                        int temp=results.getInt("Recruitment_Mode_Id");
                            %>
                                      <option value=<%=temp%>><%= results.getString("Recruitment_Mode")%></option>
                            <% 
                                    }
                                    results.close();
                                }
                                catch(SQLException e)
                                {
                                        System.out.println("Exception in creating statement:"+e);
                                }
                            %>
                                      </select>
                                    </td>
                                  </tr>
                                  
                                  <tr>
                                  <TD colspan="2">
                                    Whether Service To be Counted
                                      <label style="color:rgb(255,0,0);">*</label>
                                    
                                    (If Mode of recruitment is from Other Office)
                                  </TD>
                                      <TD colspan="2">
                                 <INPUT type=radio CHECKED value=Y name=Service_Counted>Yes &nbsp;<INPUT type=radio value=N name=Service_Counted>No     
                                 </TD>
                                   
                                  </tr>
                                  <tr>
                                        
                                      <td colspan="2">From Which Date Service To be Counted
                                      <label style="color:rgb(255,0,0);">*</label></td>
                                      <td colspan="2">
                                        <input type="text" name="Date_Counted" maxlength="30" size="15" onchange="return validate_date('frmEmployee','Date_Of_Birth')" onFocus="javascript:vDateType='3'" onKeyUp="DateFormat(this,this.value,event,false,'3')" onBlur="DateFormat(this,this.value,event,true,'3')"/>
                                       
                                      </td>
                                  </tr>
                                  
                                  <TR>
                                        <TD colspan="2">
                                        Marital Status<label style="color:rgb(255,0,0);">*</label> </TD>
                                        <TD colspan="2">
                                        
                                  <INPUT type=radio CHECKED value=Married name=Marital_Status
                                         onclick="married();">Married <INPUT type=radio value=Single name=Marital_Status onclick="single()">Single
                                            </TD>
                                 
                                        
                                  </TR>
                                  <TR>
                                        <TD colspan="2">
                                          
                                          Spouse Working?<label style="color:rgb(255,0,0);">*</label> 
                                          <FONT face=Tahoma color=#000080 size=1>(If</FONT><FONT face=Tahoma color=#000080 size=2> 
                                          </FONT><FONT face=Tahoma color=#000080 size=1>Married</FONT>
                                          <FONT face=Tahoma color=#000080 size=2>)</FONT></TD>
                                        <TD colspan="2">
                                        <INPUT type=radio value="Y" name=Spouse onclick="spouse1()" CHECKED="checked">Yes &nbsp;
                                        <INPUT type=radio  value="N" name=Spouse onclick="spouse()">No
                                    </TD>
                                                                          
                                  </TR>
                                  <tr>
                                    <td colspan="2">Spouse&nbsp;Working&nbsp;Place                                    <label style="color:rgb(255,0,0);">*</label></td>
                                    <td colspan="2">District
                                      <select name="SpouseDistrict">
                                      <option>--Select District--</option>
                                      <% 
                                try
                                {
                                    //System.out.println("hai");
                                    results=statement.executeQuery("select * from COM_MST_DISTRICTS");
                                    
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
                                        System.out.println("Exception in creating statement:"+e);
                                }
                            %>
                                      </select>
                                    </td>
                                  </tr>

                                  
                                  <tr>
                                      <td colspan="2">Spouse&nbsp;Working
                                      Office<label style="color:rgb(255,0,0);">*</label></td>
                                      <td colspan="2">
                                        <input type="text" name="SpouseOffice" maxlength="30" size="15" >
                                       
                                      </td>
                                  </tr>
                                  <!--<tr>
                                      <td colspan="2">Speciment&nbsp;Signature
                                      <label style="color:rgb(255,0,0);">*</label></td>
                                      <td colspan="2">
                                        <input type="text" name="Employee_Signature" maxlength="30" size="15"/>
                                       
                                      </td>
                                  </tr>-->
                                  <tr>
                                      <td colspan="2">
                                        Remarks
                                      </td>
                                      <td colspan="2">
                                        <textarea name="Remarks" cols="25" rows="5" ><%=strRemarks%></textarea>
                                      </td>
                                  </tr>
                                  <tr>
                                    <td colspan="2">Current Status <label style="color:rgb(255,0,0);">*</label></td>
                                    <td colspan="2">
                                      <select name="Employee_Status">
                                      <option>--Select Employee Status--</option>
                                      <%
                                          try
                                          {
                                            results=statement.executeQuery("select * from HRM_MST_EMPLOYEE_STATUS");
                                            while(results.next())
                                            {
                                              String Employee_Status_Id=results.getString("Employee_Status_Id");
                                              
                                              //System.out.println("Employee_Status_Id"+Employee_Status_Id);
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
                                            System.out.println("Exception in Recruitment_Id:"+e);
                                          }
                                      %>
                                      </select>
                                    </td>
                                  </tr>
                                  <!--<tr>
                                      <td colspan="2"> Employee Image</td>
                                      <td colspan="2">
                                      <%
                                      try 
                                      {
                                        results=statement.executeQuery("select Specimen_Image from HRM_MST_EMPLOYEES where Employee_Id="+strEmpId);
                                        if(results.next())
                                        {
                                        String ImagePath=results.getString("Specimen_Image");
                                        System.out.println(request.getContextPath());
                                        System.out.println(request.getRequestURL()); 
                                        String s=request.getContextPath();
                                        System.out.println(getServletConfig().getServletContext().getRealPath(s));

                                        System.out.println(request.getServletPath());                                      
                                        %>
                                      <input type="HIDDEN" name="ImagePath" value=<%=ImagePath%>>
                                      <img src="images\\<%=ImagePath%>" width="100" height="100">
                                      <%
                                      }
                                      }catch(Exception e)
                                      {
                                        System.out.println("The Exception in Image:"+e);
                                      }
                                      
                                      %>
                                        
                                    
                                </tr>-->
                                <tr>
                                      <td colspan="4" class="Heading" align="right">
                                          
                                          <input type="SUBMIT" value="Update" > &nbsp;
                                        <!--  <input type="RESET" value=" Clear All " name="cmdClear">&nbsp; 
                                          <input type="Button" value=" Cancel " name="cmdCancel"> &nbsp;-->                                                              
                                         <input type="Button" value=" Cancel " name="cmdCancel" onclick="self.close();">
                                      </td>                              
                                 </tr>             

              
                                                   
                             


<%
            
            }
            else
            {
               String url="../../../../Library/jsps/Messenger.jsp?message=" + "Invalid  Employee Id Does not exist this Id.<br>Please check the Id...";
               response.sendRedirect(url);
            }            
       }
       catch(SQLException e)
       {
            String url="../../../../Library/jsps/Messenger.jsp?message=" + "Could not open database...";
            response.sendRedirect(url);            
       }

  }
  catch(Exception e)
  {
         //System.out.println("Exception in openeing connection:"+e);
  } 
  }
 %>
 
     </table>                              
        </td>                        
        </tr>
                    
              </table>
        </form>    
  </body>
</html>

