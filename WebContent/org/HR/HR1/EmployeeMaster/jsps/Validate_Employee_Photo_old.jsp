<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
  
    <link href="css/try1.css" rel="stylesheet" media="screen"/>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Validate Employee Photo </title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    
                       <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
                        <script type="text/javascript" src="../scripts/ValidationEmployee2.js"></script>
                        <script type="text/javascript"     src="../scripts/HRE_EmployeeServiceDetailsJS.js"></script>
                        <script type="text/javascript" src="../scripts/Validate_Employee_PhotoJS.js"></script>
                         <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
                         <!-- <script type="text/javascript"       src="../../../../Library/scripts/CalendarControl.js"></script>-->
                        <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
            <script type="text/javascript">
   
    function toLoad()
    {
      document.frmEmployee.txtEmpId1.focus();
    }
    </script>
    <script type="text/javascript">
    
    function loadImage()
    {   
    
     var filectl=document.getElementById("EmpBrowse");
    
     var url=filectl.value;//alert(url);
//alert(url);    
     var imgctl=document.getElementById("EmpImage");
//alert(imgctl);     
     imgctl.src=url;
     //alert(imgctl.src);
     //imgctl.src=rstr;
     //alert(document.frmEmployee.EmpImage.src);
     document.frmEmployee.EmpImage.src=imgct1.src;
     
    }
    function loadSign()
    {
     var filesign=document.getElementById("Employee_Signature");
     var url1=filesign.value;     
     var str1=url1;
     var imgsign=document.getElementById("EmpSign");
     imgsign.src=url1;
    }
    function fun1()
    {
    //alert("Hai");
      document.location="ImageSession_11.jsp?Command=destroy";
    }
    
    function fundelete()
    {
    //alert("Hai");
      //document.location="ImageSession.jsp?Command=delete";
      var empid=document.frmEmployee.txtEmpId1.value;
                //alert(empid);
                document.frmimageupload.action="../jsps/ImageSession_11.jsp?Command=delete&empidp="+empid;
                document.frmimageupload.method="post";
                document.frmimageupload.submit();
                 document.location="Validate_ImageSession.jsp?Command=validate";
    }
    
    function validate()
    {
       // alert(document.frmimageupload.EmpBrowse.value.length);
        if(document.frmimageupload.EmpBrowse.value.length==0)
        {
            alert('Please Browse the Image');
            document.frmimageupload.EmpBrowse.focus();
            return false;
        }
        else
        {
            var img=document.frmEmployee.EmpImage;
            img.src=document.frmimageupload.EmpBrowse.value;
            //alert(img.src);
        }
        return true;
    }
    
    function validateNew()
    {
       // if(validate())
            //{
                var empid=document.frmEmployee.txtEmpId1.value;
                alert(empid);
                document.frmimageupload.action="../jsps/Validate_ImageLoad.jsp?empidp="+empid;
                document.frmimageupload.method="post";
                document.frmimageupload.submit();
                
           // }
            
    }
    </script>
  </head>
  <body>
  <form name="frmEmployee" class="table">
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
  ResourceBundle rs=null;
  
  try
  {
  
              rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rs.getString("Config.DSN");
            String strhostname=rs.getString("Config.HOST_NAME");
            String strportno=rs.getString("Config.PORT_NUMBER");
            String strsid=rs.getString("Config.SID");
            String strdbusername=rs.getString("Config.USER_NAME");
            String strdbpassword=rs.getString("Config.PASSWORD");

           // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             Class.forName(strDriver.trim());
             connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  int empid=9315;
  %>
  
  <!-- OFFICE DETAILS -->
                <% 
                /*HttpSession session=request.getSession(false);
                    UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");

     empid=empProfile.getEmployeeId();
     System.out.println("Hidden value:"+empid);*/
    %>
   
    <input type="hidden" value="<%=empid%>" name="hempid" id="hempid">
   
   
    <%int  oid=0;
//session.setAttribute("Emp",empid);
    try
    {
           // String ans=request.getParameter("hempid");
    //System.out.println("Hidden value1:"+ans);
    empid=9315;
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
                                                            <b>Validate Employee
                                                               Photo Details </b>
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
                                                             onchange="callFunction('ExistgBasic','null')" onfocus="toFocus()"></input>
                                                      <input tabindex="1"
                                                             type="HIDDEN"
                                                             name="EmpId"
                                                             id="EmpId"></input>
                                                          <%--   <%
                              session=request.getSession(false);
                             if(session.getAttribute("Admin")!=null && ((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                             {
                             %>--%>
                             <img src="../../../../../images/c-lovi.gif"
                                                           width="20"
                                                           height="20"
                                                           alt="empList"
                                                           onclick="servicepopup();"></img>
                             <%--<%}else{%>
                             
                              <img src="../../../../../images/c-lovi.gif"
                                                           width="20"
                                                           height="20"
                                                           alt="empList"
                                                           onclick="servicepopupSR();"></img>
                             <%}%>--%>
                                                     
                                                           
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
                                                       <img  align="top" src=""  alt="Photo" width="60" height="60" name="EmpImage" id="EmpImage" ></img>
                                                        
                                                </td>
                                          </tr>
                                          <tr>
                                                <td rowspan="2">
                                                      Employee Name
                                                     
                                                </td>
                                                <td>Prefix</td>
                                                <td>
                                                      Initial
                                                      
                                                </td>
                                                <td>
                                                      Name
                                                      
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
                                          
                                      
                                           
                                        
                                    </table></form> 
                                   
                                    
<form name="frmimageupload" enctype="multipart/form-data" >
         <style>
                .divClass{display: none;  }                
                .bgClass{color: White ; width: 100%; border-bottom: 1px solid Black ; padding: 0px; margin: 0px;background-color: rgb(135,155,216);}
  </style>

    <table border="0px" width="80%" align="center">
       <tr>
       <td align="center">
        <input type="submit" name="Validate" id="Validate" value="Validate" disabled onclick="validateNew()" >
        
        <input type="button" value="Cancel" onclick="self.close();">
        </td>
         </tr>     
    </table>
</form>                                         
</body>
</html>