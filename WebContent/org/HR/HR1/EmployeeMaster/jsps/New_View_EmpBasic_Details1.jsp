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
    <title>Employee Basic Detail View</title>    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    
                      
     <script type="text/javascript">
          
          //////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;
var my_window;
//alert('kkk');
function servicepopup()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }
    else
    {
        winemp=null
    }
        
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}


function doParentEmp(emp)
{
document.frmview.txtEmpId1.value=emp;
document.frmview.EmpId.value=document.frmview.txtEmpId1.value;
//callFunction('Existg','null');
//alert(emp);
document.frmview.submit();
}

function getTransport()
{
 var req = false;
 try 
 {
       req= new ActiveXObject("Msxml2.XMLHTTP");
 }
 catch (e) 
 {
       try 
       {
            req = new ActiveXObject("Microsoft.XMLHTTP");
       }
       catch (e2) 
       {
            req = false;
       }
 }
 if (!req && typeof XMLHttpRequest != 'undefined') 
 {
       req = new XMLHttpRequest();
 }   
 return req;
}

function validate()
{
    if(document.frmview.txtEmpId1.value.length==0)
    {
        alert('Select Employee Id');
        document.frmview.txtEmpId1.focus();
        return false;
    }
    else
    {
    
        var strEmpId=document.frmview.txtEmpId1.value;
        // startwaiting(document.frmEmployee) ; 
           url="../../../../../InsertEmployee1.con?command=Existgview&EmpId=" + strEmpId;   
                     var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
              // processHandleResponse(req);
                  if(req.readyState==4)
                    {
                      if(req.status==200)
                      {            
                          //stopwaiting(document.frmEmployee) ;
                           var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                           var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                           //alert(flag);
                            if(flag=="success")
                            {
                                 document.frmview.EmpId.value=document.frmview.txtEmpId1.value;
                                // alert(document.frmview.EmpId.value);
                                  document.frmview.submit();
                                 // doParentEmp(emp)
                                  return true;
                            }
                            else
                            {
                                     
                                    //alert('Enter a valid Employee Id');
                                    window.location="<%=request.getContextPath()%>/org/Library/jsps/Messenger.jsp?message=" + "Given Employee Id not Found!";
                                    //String url="../../../../org/Library/jsps/Messenger.jsp?message=" + "Could not open database...";
            //response.sendRedirect(url);   
                                   // document.frmview.txtEmpId1.value="";
                                   // document.frmview.txtEmpId1.focus();
                                    return;
                                     
                            }
                      
                      
                        } 
                    }
         }
        req.send(null);
    
   
    }
}

function numbersonly1(e,t)
    {
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
     }
     
     

window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
}

          </script>
  </head>
  
           <%
  int strEmpId=0;
  UserProfile up=null;
  try
  {
  
  HttpSession session=request.getSession(false);
   up=(UserProfile)session.getAttribute("UserProfile");
  if(request.getParameter("txtEmpId1")==null)
  {
%>
<body class="table" onload="document.frmview.reset()">
  <form name="frmview">
 
 <table width="100%" align=center>
        
         <tr>
               <td  colspan="2">                        
                    <table border="1px" width="100%" align=center>
                        <tr>
                             <td align="left" class="tdH" colspan="4">
                                  
                                        <center><b>Employee&nbsp;Profile </b></center>
                                  
                              </td>
                         </tr>
                        <tr>
                             <td colspan="2">
                                 Employee Id:
                                </td>
                            <td colspan="2" valign="center">
                                             <table border="0"><tr><td>
                                                       <input tabindex="1"  type="text" name="txtEmpId1"
                                                            id="txtEmpId1"   maxlength=5   onkeypress="return  numbersonly1(event,this)"
                                                             onchange="validate()"></input>    <input tabindex="1"  type="HIDDEN"
                                                             name="EmpId"   id="EmpId"></input>     <img src="../../../../../images/c-lovi.gif"
                                                                 width="20"    height="20"    alt="empList"  onclick="servicepopup();"></img>
                                            </td></tr> </table>
                             </td>
                        </tr>
                </table>

    </td></tr></table>
    </form>
  </body>
</html>
<%
out.flush();
out.close();
return;

    }
    else
    {
   int id =Integer.parseInt(request.getParameter("txtEmpId1"));
   strEmpId=id;
   }
   // String url="ViewProfile.jsp?txtEmpId=" + id ;
    
  }
  catch(Exception e)
  {
    System.out.println("exception : " + e);
  }
  %>

  
  <body class="table" onload="document.frmview.reset()">
  <form name="frmview">
 
 <table width="100%" align=center>
        
         <tr>
               <td  colspan="2">                        
                    <table border="1px" width="100%" align=center>
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

          /*  if(up.getEmployeeId()!=strEmpId)
            {
            int OfficeId=0;
                String sql="select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                ps1=connection.prepareStatement(sql);
                ps1.setInt(1,strEmpId);
                results1=ps1.executeQuery();
               
               if(results1.next()) {
                    OfficeId=results1.getInt("CONTROLLING_OFFICE_ID");
                }

                if(OfficeId!=0)
                {
                       sql="select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                        ps1=connection.prepareStatement(sql);
                        ps1.setInt(1,up.getEmployeeId());
                        results1=ps1.executeQuery();  
                         if(results1.next()) {
                            int offid=results1.getInt("OFFICE_ID");
                            if(offid!=OfficeId)
                            {
                                    response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Can not see profile. Because Employee Id "+strEmpId+" is not under your Office!");
                            }
                        }
                        else
                        {
                                response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Current Posting is not available. Can not see the profile for "+strEmpId+"!");
                        }
                
                }
            }
            */

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
                                           
                                           System.out.println("select a.Employee_Name,a.Employee_Initial,a.GPF_NO, " +
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
                                System.out.println("date of entry:"+DateOfFormation1);
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
                              //e.printStackTrace();
                              System.out.println("Exception in the last try is: "  + e);
                            }   
                              System.out.println("here is ok"); 
                            
                         /*  rs=statement.executeQuery("select Office_Id,DESIGNATION_ID,DATE_EFFECTIVE_FROM from HRM_EMP_CURRENT_POSTING where Employee_Id=" + strEmpId);
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
               <input tabindex="1"
                                                             type="text"
                                                             name="txtEmpId1"
                                                             id="txtEmpId1" 
                                                             maxlength=5  value="<%=strEmpId%>"
                                                             onkeypress="return  numbersonly1(event,this)"
                                                             onchange="validate()"></input>
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
                  <TD colspan="2">
                        Employee Name
                  </TD>
                  <TD colspan="2">
                       <b> <%//=(strEmpPrefix==null)?"":strEmpPrefix+"&nbsp;"%><%=(strEmpInitial==null)?"":strEmpInitial+"."%><%=strEmpName%></b> 
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
               int des_id=0;
                          try
              { 
              
                 String sql3="Select OFFICE_ID,DEPARTMENT_ID,OFFICE_GRADE,DATE_EFFECTIVE_FROM,DESIGNATION_ID  FROM HRM_EMP_CURRENT_POSTING WHERE EMPLOYEE_ID=?";
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
                   des_id=rs3.getInt("DESIGNATION_ID"); 
                   
                  if(rs3.getDate("DATE_EFFECTIVE_FROM")!=null)
                  {
                        dt=new java.text.SimpleDateFormat("dd/MM/yyyy").format(rs3.getDate("DATE_EFFECTIVE_FROM"));
                        
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
                      String Designation=null;
            try
              { 
             
                 String sql3="Select DESIGNATION FROM HRM_MST_DESIGNATIONS WHERE DESIGNATION_ID=?";
                 ps3=connection.prepareStatement(sql3);
                  ps3.setInt(1,des_id);
                  rs3=ps3.executeQuery();
                  System.out.println(strDesig_Id);
                   
                  int found=0;
                  if(rs3.next()) 
                  {
                  System.out.println("upto here ok");
                   Designation=rs3.getString("DESIGNATION");
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
                                      <td colspan="2">Date&nbsp;of&nbsp;Joining in TWAD Board (as per SR)
                                       </td>
                                      <td colspan="2">
                                        <%//=DateToBeDisplayed2%>
                                   <%
                                int des=0;
                                try
                                {
                                       PreparedStatement ps=connection.prepareStatement("select DATE_FROM ,DESIGNATION_ID from HRM_EMP_SERVICE_DATA where EMPLOYEE_ID=? and  DATE_FROM = (select min(a.DATE_FROM) from HRM_EMP_SERVICE_DATA a  where a.EMPLOYEE_ID=?)");
                                       ps.setInt(1,strEmpId);
                                       ps.setInt(2,strEmpId);
                                       rs=ps.executeQuery();
                                       if(rs.next())
                                       {
                                            dt=new java.text.SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DATE_FROM"));
                                            des=rs.getInt("DESIGNATION_ID");
                                            if(dt!=null)
                                               {
                                               out.print(dt);
                                               //out.println("<script language='javascript'>alert('1)"+DateToBeDisplayed2+"2)  "+dt+"');</script>");
                                               
                                                    if(DateToBeDisplayed2!=null && dt!=null)
                                                    {
                                                            if(!DateToBeDisplayed2.equals(dt))
                                                            {
//out.println("<script language='javascript'>alert('Employee Joining Date and First Entry of SR Date are different');</script>");
                                               
                                                            }
                                                    }
                                                    
                                               }
                                       }
                                }
                                catch(Exception e){System.out.println(e);}
                                        %>
                                       
                                      </td>
                                  </tr>
                                  <tr>
                                    <td colspan="2">Designation&nbsp;at&nbsp;the&nbsp;Time&nbsp;of&nbsp;Joining&nbsp;(as per SR) </td>
                                    <td colspan="2">
                                      
                                     <%
                                     
                                
                                try
                                {
                                       PreparedStatement ps=connection.prepareStatement("select DESIGNATION from HRM_MST_DESIGNATIONS where DESIGNATION_ID=?");
                                       //ps.setInt(1,strDesig_Id);
                                       ps.setInt(1,des);
                                       rs=ps.executeQuery();
                                       if(rs.next())
                                       {
                                           
                                           // if(rs.getString("DESIGNATION")!=null)
                                               // out.print(rs.getString("DESIGNATION"));
                                             String ds=rs.getString("DESIGNATION");
                                             out.print(ds);
                                              if(Designation!=null && ds!=null)
                                                    {
                                                            if(!Designation.equals(ds))
                                                            {
                                                          //  out.println("<script language='javascript'>alert('Employee Joining Designation and First Entry of SR Disignation are different');</script>");
                                               
                                                            }
                                                    }
                                                
                                       }
                                }
                                catch(Exception e){System.out.println(e);}
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
               String url="../../../../org/Library/jsps/Messenger.jsp?message=" + "Invalid  Employee Id Does not exist this Id.<br>Please check the Id...";
               response.sendRedirect(url);
            }            
       }
       catch(SQLException e)
       {
           System.out.println("this is that cldnt open database err" + e);
            String url="../../../../org/Library/jsps/Messenger.jsp?message=" + "Could not open database...";
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