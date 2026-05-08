<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%--<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>--%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>My Profile</title>    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript">
          
          function disp()
          {
            alert("This is wat i want it");
          }
          </script>
  </head>
  <body class="table">
 <%
  int strEmpId=0;
  try
  {
  HttpSession session=request.getSession(false);
    UserProfile up=(UserProfile)session.getAttribute("UserProfile");
    int id=up.getEmployeeId(); 
   strEmpId=id;
    String url="ViewProfile.jsp?txtEmpId=" + id ;
    
  }
  catch(Exception e)
  {
    System.out.println("exception : " + e);
  }
  %>
 <%
 //String strEmpId=request.getParameter("txtEmpId");
 //System.out.println("****"+strEmpId);
 %>
 <table width="100%" align=center>
        
         <tr>
               <td  colspan="2">                        
                    <table border="1px" width="650px%" align=center>
             <tr>
             <td align="left" class="tdH" colspan="4">
                  
                        <center><b>Employee&nbsp;Profile </b></center>
                  
              </td>
         </tr>
          <%
 if(strEmpId!=0)
 {
   Connection connection=null;
   Statement statement=null;
   ResultSet results=null;
   ResultSet results1=null;
   ResultSet results6=null;
   ResultSet results7=null;
   ResultSet results4=null;
   ResultSet results5=null;
   ResultSet rs=null; 
   ResultSet rs3=null;
   PreparedStatement ps1;
   PreparedStatement ps3;
   
   
   String strEmpPrefix="",strEmpInitial="",strOfficeId="",strOfficeName="",strOfficeDesignation="",strOffice_Grade="";
   String strEmpName="",strGender="",strGpfNumber="",strDesignation="",strMarital="",strRemarks="",strEmpStatus="",DeptId="",OffName="",OffDName="",EmpImage="";
   
   int strCommunity=0,strReligion=0,strNativeDistrict=0,strNativeTaluk=0,strPostStatus=0,strRecruitmentMode=0;
   int strDistrict=0,strQualification=0,strSpousePlace=0,District_Code=0,strDesig_Id=0;
   int OffId=0;
   
   
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
           System.out.println("in");
           // rs=statement.executeQuery("select * from HRM_MST_EMPLOYEES where Employee_Id=" + strEmpId);
           
                                                                      
                                           
                                           rs=statement.executeQuery("select a.Employee_Name,a.Employee_Initial,a.GPF_NO, " +
                                           "  a.EMPLOYEE_PREFIX,a.Date_Of_Birth,a.Gender,a.COMMUNITY_ID,a.RELIGON_ID,  " +
                                           " a.Native_District_Code,a.Native_Taluk_Code,a.QUALIFICATIONS," + 
                                           " a.EMPLOYMENT_STATUS_ID,a.TWAD_ENTRY_DATE,a.JOIN_TIME_DESIG_ID,a.EMP_CURRENT_STATUS_ID,a.Marital_Status,a.Remarks, " + 
                                           " a.Other_State,a.Other_Districts,e.EMP_PHOTO_FILE_NAME " +
                                           " from hrm_mst_employees a " +
                                           " left outer join hrm_emp_addl_details e on e.employee_id=a.employee_id " +
                                           " where a.employee_id= " + strEmpId);
            if(rs.next())
            {
                        try
                          {       
                          
                                strEmpInitial=rs.getString("Employee_Initial");
                                strEmpPrefix=rs.getString("Employee_Prefix");
                                strEmpName = rs.getString("Employee_Name"); 
                                DateOfFormation=rs.getDate("Date_Of_Birth");
                                System.out.println("DOB:"+DateOfFormation);
                                java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
                                    DateToBeDisplayed=sdf.format(DateOfFormation);
                                    System.out.println("DOB after conversion:"+DateToBeDisplayed);
                                    
                                strGender=rs.getString("Gender");
                                //strGender=(strGender.equalsIgnoreCase("M")==null)?"":strGender.equalsIgnoreCase("M");
                                if(strGender!=null)
                                {
                                if(strGender.equalsIgnoreCase("M"))
                                    strGender="Male";
                                 else
                                    strGender="Female";
                                }
                                else
                                {
                                strGender="";
                                }
                                
                                DateOfFormation1=rs.getDate("TWAD_ENTRY_DATE");
                                strDesig_Id=rs.getInt("JOIN_TIME_DESIG_ID");

                                strMarital=rs.getString("Marital_Status");
                                System.out.println("Marital status is:" + strMarital);
                                if(strMarital!=null)
                                {
                                    if(strMarital.equalsIgnoreCase("M"))
                                       strMarital="Married";
                                    else
                                       strMarital="Single";
                                }
                                else
                                {
                                    strMarital="";
                                }
                                strRemarks=rs.getString("Remarks");
                                strGpfNumber=rs.getString("GPF_NO");
                                
                                EmpImage=rs.getString("EMP_PHOTO_FILE_NAME");
                                System.out.println("Employee Image is: " + EmpImage);

                                    try
                                    {
                                      strCommunity= rs.getInt("COMMUNITY_ID"); 
                                      strReligion=rs.getInt("RELIGON_ID");
                                      strNativeDistrict=rs.getInt("Native_District_code");
                                      strNativeTaluk=rs.getInt("Native_Taluk_Code");
                                      strPostStatus=rs.getInt("EMP_CURRENT_STATUS_ID");
                                      strEmpStatus=rs.getString("EMPLOYMENT_STATUS_ID");
                                    }catch(NumberFormatException mfe)
                                    {
                                      System.out.println("NumberFormatException:"+mfe);
                                    }
                                    java.text.SimpleDateFormat sdf1=new java.text.SimpleDateFormat("dd/MM/yyyy");
                                    DateToBeDisplayed2=sdf1.format(DateOfFormation1);
                            }
                            catch(Exception e)
                            {
                              e.printStackTrace();
                              System.out.println("Exception in the last try is: "  + e);
                            }     
                            
                          /*  rs=statement.executeQuery("select Office_Id,DESIGNATION_ID,DATE_OF_JOINING from HRM_EMP_CURRENT_POSTING where Employee_Id=" + strEmpId);
                            if(rs.next())
                            {
                              strOfficeId=rs.getString("Office_Id");
                              strOfficeName=rs.getString("DESIGNATION_ID");
                            }
                            else
                            {
                            }
                             rs.close();*/
                                      
%>

  <style>
            .bgClass{color: White ; width: 100%; border-bottom: 1px solid Black ; padding: 0px; margin: 0px;background-color: rgb(135,155,216);}
  </style>
               <tr>
             <td colspan="2">
                 Employee Id:
                </td>
            <td colspan="2" valign="center">
            <table border="0"><tr><td>
                <form name="frmEmployeeDetail">
                
                <input tabindex="1"
                                                             type="text"
                                                             name="txtEmpId1"
                                                             id="txtEmpId1"
                                                             maxlength=5
                                                             onkeypress="return  numbersonly1(event,this)"
                                                             onchange="callFunction('Existg','null')"></input>
                                                      <input tabindex="1"
                                                             type="HIDDEN"
                                                             name="EmpId"
                                                             id="EmpId"></input>
                                                             <img src="../../../../../images/c-lovi.gif"
                                                           width="20"
                                                           height="20"
                                                           alt="empList"
                                                           onclick="servicepopup();"></img>
                </form>
                </td>
                <td>
                 <%
                                                           ResourceBundle res=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                                                              String imagepath=res.getString("Config.EMPLOYEE_PHOTOS_PATH");
                                                              String NewImage="";
                                                              System.out.println("imaghe path : " + imagepath);
                                                           String context=request.getContextPath();
                                                           imagepath=res.getString("Config.EMPLOYEE_PHOTOS_PATH_VIEW");
                                                         System.out.println("image path after is:"+imagepath);
                                                          String img=context+imagepath;
            %>
                                                           
                                                &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;       <img  align="top" src="../../../../../images/sample_emp.bmp"  alt="Photo" width="90" height="90" id="EmpImage" ></img>
                                                        <script language="javascript" type="text/javascript">
                                                          imgpath="<%=img%>";
                                                          eimage="<%=EmpImage%>";
                                                          NewImage=imgpath+eimage;
                                                          var emp=document.getElementById("EmpImage");
                                                           emp.src=NewImage;
                                                        
                                                           </script>
                    </td>
                </tr>
                </table>
            </td>
        </tr>
         

             <TR>
                  <TD rowspan="2">
                        Employee Name
                  </TD>
                  <TD>
                          Prefix
                  </TD>
                  <TD>Initial
                   </TD>
                  <TD>Name
                   </TD>
                  
             </TR>
              <TR>
                   
                    <TD>
                  <font face="Times New Roman"  size="3"><b><%=(strEmpPrefix==null)?"":strEmpPrefix%></b></font>
                    </TD>
                    <TD>
                     <font face="Times New Roman"  size="3"><b> <%=(strEmpInitial==null)?"":strEmpInitial%></b></font>
                    </TD>
                    <TD>
                     <font face="Times New Roman"  size="3"> <b><%=strEmpName%></b></font>
                    </TD>
                    
             </TR>
             <TR>
                    <TD colspan="4" class="tdH">
                       <B><FONT face=Tahoma color=#000000 size=2>Designation and Office Details</FONT></B>
                    </TD>
              </TR>
              
               <%
               String grade="";
               String dt="";
                          try
              { 
              
                 String sql3="Select OFFICE_ID,DEPARTMENT_ID,OFFICE_GRADE,DATE_OF_JOINING FROM HRM_EMP_CURRENT_POSTING WHERE EMPLOYEE_ID=?";
                 ps3=connection.prepareStatement(sql3);
                  ps3.setInt(1,strEmpId);
                  rs3=ps3.executeQuery();
                  int found=0;
                  
                  while(rs3.next()) 
                  {
                   DeptId=rs3.getString("DEPARTMENT_ID");
                   OffId=rs3.getInt("OFFICE_ID");
                   System.out.println("OFFICE ID1::"+OffId);
                   grade=rs3.getString("OFFICE_GRADE");
                   System.out.println("Grade  :"+grade);
                   
                  if(rs3.getDate("DATE_OF_JOINING")!=null)
                  {
                        dt=new java.text.SimpleDateFormat("dd/mm/yy").format(rs3.getDate("DATE_OF_JOINING"));
                        
                  }
                  else
                  {
                        dt="";
                  }
                  
                       try {

                         if ( DeptId==null || DeptId.equalsIgnoreCase("TWAD") )
                         {
                         System.out.println("Dept id :"+DeptId);
                              // String sql =  "select office_id,office_name,Office_address1,office_address2,CITY_TOWN_NAME,DISTRICT_CODE,OFFICE_PHONE_NO,ADDL_PHONE_NOS,OFFICE_EMAIL_ID,ADDL_EMAIL_IDS,OFFICE_FAX_NO,ADDL_FAX_NOS,OFFICE_STD_CODE from com_mst_offices where Office_Id=?";
                               String sql =  "select office_name from com_mst_offices where Office_Id=?";
                               
                               PreparedStatement statement1 =  connection.prepareStatement(sql);
                                   System.out.println("OFFICE ID2::"+OffId);
                               statement1.setInt(1, OffId);
                               
                               connection.clearWarnings();
                               
                                   ResultSet results2 = statement1.executeQuery();
                                   try {
                                     if (results2.next()) {
                                           OffName=results2.getString("OFFICE_NAME");
                                           System.out.println("Office Name:"+OffName);
                                          //out.println(OffName);
                                           
                                      }
                                     
                                   } catch (Exception e) {
                                   System.out.println("office name error:"+e);

                                   } 
                                   
                           }
                           
                           else
                                {
                                   String sql = 
                                       "select OTHER_DEPT_OFFICE_ID,OTHER_DEPT_OFFICE_NAME,ADDRESS1,ADDRESS2,CITY_TOWN from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_OFFICE_ID=? and OTHER_DEPT_ID=?";
                                   PreparedStatement statement2 = connection.prepareStatement(sql);
                                   statement2.setInt(1, OffId);
                                   statement2.setString(2, DeptId);
                                   connection.clearWarnings();
                                   try {
                                       ResultSet results3 = statement2.executeQuery();
                                        if (results3.next()) {
                                               OffName=results3.getString("OTHER_DEPT_OFFICE_NAME");
                                               System.out.println("Other Office"+OffName);
                                              // out.println(OffDName);
                                            
                                           }
                                       
                                  } catch (SQLException e) {
                                       System.out.println("Exception in statement:" + e);
                                   } finally {
                                       statement.close();
                                   }
                               }
                        } catch (SQLException e) {
                           System.out.println("Exception in connection:" + e);
                       } 
                   
                   
                  }
                  
              }
              catch(Exception ee){System.out.println("Selection of office name " + ee);}
                         %>
                         
                          <tr>
                      <td colspan="2">Designation </td>
                      <td colspan="2">
                      <%
            try
              { 
             
                 String sql3="Select DESIGNATION FROM HRM_MST_DESIGNATIONS WHERE DESIGNATION_ID=?";
                 ps3=connection.prepareStatement(sql3);
                  ps3.setInt(1,strDesig_Id);
                  rs3=ps3.executeQuery();
                  System.out.println(strDesig_Id);
                   
                  int found=0;
                  if(rs3.next()) 
                  {
                  System.out.println("upto here ok");
                   String Designation=rs3.getString("DESIGNATION");
                   out.println(Designation);
                   System.out.println("Designation:"+Designation);
                  }
                  else
                  {
                  out.println("");
                  }
                  
                  }
                  catch(Exception ee){System.out.println("Desig error" +  ee);}
                  %>
                         
                      </td>
             </tr> 
             <tr>
                       <td colspan="2">Grade </td>
                       <td colspan="2"><%=(grade==null)?"":grade%> </td>
             </tr>
              <tr>
                        <td colspan="2">Office </td>
                        <td colspan="2"><%=OffName%> </td>
             </tr> 
             
             <tr>
                     <td colspan="2">Date of Current Posting</td>
                     <td colspan="2"> <%=dt%> </td>
             </tr> 
            
             <tr>
                        <TD colspan="4" class="tdH">
                            <B><FONT face=Tahoma color=#000000 size=2>General Particulars</FONT></B>
                        </TD>
             </tr>
             <tr>
                         <td colspan="2">Date Of Birth </td>
                         <td colspan="2">
                                <%=DateToBeDisplayed%>
                         </td>
              </tr>
              
              <TR>
                        <TD colspan="2">Gender </TD>
                        <TD colspan="2">
                        <P>
                        <%=strGender%>
                        </P>
                        </TD>
                       
              </TR>
              <tr>
                          <td colspan="2">Community </td>
                          <td colspan="2">
                          <%
                         
                                  try
                                  {
                                       results4=statement.executeQuery("select * from HRM_MST_COMMUNITY");
                                       while(results4.next())
                                       {
                                             int Community_Code=results4.getInt("Community_Code");
                                             
                                              if(strCommunity==Community_Code)
                                              {
                                                out.println(results4.getString("Community_Name"));
                                              }
                                              
                                       }
                                       results4.close();
                                    }catch(Exception e)
                                    {
                                            System.out.println("Exception in Community:"+e);
                                    }
                                     
                          %>                             
                          </td>
              </tr>
              
                                  <tr>
                                    <td colspan="2">Native&nbsp;District
                                     </td>
                                    <td colspan="2">                       
                                     
                                      <%
                                     
                                          try
                                          {
                                            results5=statement.executeQuery("select * from COM_MST_DISTRICTS");
                                            while(results5.next())
                                            {
                                              District_Code=results5.getInt("District_Code");                                              
                                              if(strNativeDistrict==District_Code)
                                              {
                                                out.println(results5.getString("District_Name"));
                                              }
                                              
                                            }
                                            results5.close();
                                          }catch(Exception e)
                                          {
                                            System.out.println("Exception in Community:"+e);
                                          }
                                      %>   
                                      
                                    </td>
                                  </tr>
                                  
                                   <TR>
    <TD colspan="2">
      Native Taluk Name </TD>
    <TD colspan="2">
    
      <%
      
         String Native_Taluk_Name="";
                                          try
                                          {
                                            results1=statement.executeQuery("select * from COM_MST_TALUKS where District_Code="+ strNativeDistrict + " and TALUK_CODE=" + strNativeTaluk );
                                           int Native_Taluk_Code=0;
                                            int i=0;
                                            
                                            while(results1.next())
                                            {
                                           
                                              Native_Taluk_Code=results1.getInt("Taluk_Code"); 
                                              Native_Taluk_Name=results1.getString("Taluk_Name"); 
                                              i++;
                                             }
                                             if(i==0)
                                             {
                                                //out.println("Undefined Record Found");
                                                out.println("");
                                             }
                                              else
                                              {
                                              if(strNativeTaluk==Native_Taluk_Code)
                                              {
                                                out.println(Native_Taluk_Name);
                                               System.out.println("taluk naaaaaaaaaaaaammmmmeeeeeee"+results.getString("Taluk_Name"));
                                              }
                                              
                                            }
                                             
                                            results1.close();
                                          }catch(Exception e)
                                          {
                                            System.out.println("Exception in Qualification_Id1:"+e);
                                          }
                                     
                                       
                                        %>  

            </TD>
              
            </TR>
                 
                                  
                                  <tr>
                                    <td colspan="2">Employment&nbsp;Status
                                     </td>
                                    <td colspan="2">
                                   
                                     <%
                                     //System.out.println("here is ok2");
                                          try
                                          {
                                            results6=statement.executeQuery("select * from HRM_MST_EMPLOYMENT_STATUS");
                                            int i=0;
                                            String Employee_Status_Id="";
                                            String empstat="";
                                            while(results6.next())
                                            {
                                               Employee_Status_Id=results6.getString("EMPLOYMENT_STATUS_ID");
                                               System.out.println("Emp"+Employee_Status_Id);
                                                empstat=results6.getString("EMPLOYMENT_STATUS");
                                              
                                              
                                                                   
                                                  if(strEmpStatus.equals(Employee_Status_Id))
                                                 {
                                                  
                                                  out.println(results6.getString("EMPLOYMENT_STATUS"));
                                                  }
                                                  
                                              }
                                            
                                            results6.close();
                                          }catch(Exception e)
                                          {
                                            System.out.println("Exception in Recruitment_Id:"+e);
                                          }
                                      %>
                                      
                                      
                                    </td>
                                  </tr>

                                  
                                  <tr>
                                      <td colspan="2">GPF Number
                                       </td>
                                      <td colspan="2">
                                        <%=strGpfNumber%>
                                       
                                      </td>
                                  </tr>
                                
                                  <tr>
                                      <td colspan="2">Date&nbsp;of&nbsp;Joining in TWAD Board
                                       </td>
                                      <td colspan="2">
                                        <%=DateToBeDisplayed2%>
                                       
                                      </td>
                                  </tr>
                                  <tr>
                                    <td colspan="2">Designation&nbsp;at&nbsp;the&nbsp;Time&nbsp;of&nbsp;Joining&nbsp;&nbsp;                                     </td>
                                    <td colspan="2">
                                      
                                      <%
                                          try
                                          {
                                            results=statement.executeQuery("select * from HRM_MST_DESIGNATIONS");
                                            while(results.next())
                                            {
                                              int Designation_Id=results.getInt("Designation_Id");
                                              
                                              if(strDesig_Id==Designation_Id)
                                              {
                                                out.println(results.getString("Designation"));
                                              }
                                              
                                            }
                                            results.close();
                                          }catch(Exception e)
                                          {
                                            System.out.println("Exception in Designation_Id:"+e);
                                          }
                                      %>                                     
                                    </td>
                                  </tr>
                            
                                  <TR>
                                        <TD colspan="2">
                                        Marital Status  </TD>
                                        <TD colspan="2">
                                     
                                       <%=(strMarital==null)?"":strMarital%>
                                        
                                            </TD>
                                   
                                  </TR>
                                  <tr>
                                      <td colspan="2">
                                        Remarks
                                      </td>
                                      <td colspan="2">
                                       <%if(strRemarks!=null){%> <%=strRemarks%> <%}%>                               
                                      </td>
                                  </tr>
                                  <tr>
                                    <td colspan="2">Current Status  </td>
                                    <td colspan="2">
                                      
                                      
                                      <%
                                          try
                                          {
                                            results=statement.executeQuery("select * from HRM_MST_EMPLOYEE_STATUS");
                                            int i=0;
                                            int Post_Status_Id=0;
                                            String strpostst="";
                                            while(results.next())
                                            {
                                               Post_Status_Id=results.getInt("EMPLOYEE_STATUS_ID");
                                               strpostst= results.getString("EMPLOYEE_STATUS_DESC");
                                              
                                               i++;
                                              
                                              if(strPostStatus==Post_Status_Id)
                                              {
                                                out.println(results.getString("EMPLOYEE_STATUS_DESC"));
                                              }
                                              
                                            }
                                            results.close();
                                          }catch(Exception e)
                                          {
                                            System.out.println("Exception in Qualification_Id2:"+e);
                                          }
                                      %>
                                    </td>
                                  </tr>
                                 
                                <tr>
                                      <td colspan="4" class="tdH" align="right">
                                          
                                       <input type="BUTTON" value=" Ok " onclick="self.close();"> &nbsp;
                                          <input type="Button" value=" Print " onclick="window.print();">&nbsp;                                           
                                         
                                      </td>                              
                                 </tr>             

              
                                                   
                             


<%
            
            }
            else
            {
               String url="Messenger.jsp?message=" + "Invalid  Employee Id Does not exist this Id.<br>Please check the Id...";
               response.sendRedirect(url);
            }            
       }
       catch(SQLException e)
       {
           System.out.println("this is that cldnt open database err" + e);
            String url="Messenger.jsp?message=" + "Could not open database...";
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
 
  </body>
</html>