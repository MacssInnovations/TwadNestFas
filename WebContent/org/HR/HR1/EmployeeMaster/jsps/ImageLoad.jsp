<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%
  
  Connection connection=null;
  PreparedStatement ps=null;
  ResultSet results=null;
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
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Employee Image</title>
    <script type="text/javascript">
    
    function loadImage()
    {     
     var filectl=document.getElementById("EmpBrowse");
    
     var url=filectl.value;//alert(url);
     var str=url;
    
     /*var rstr="";
     var index=-1;
     var i=0;
     while((index=str.indexOf("\\"))>=0)
     {
        //alert("found  " + index);
        var part1=str.substr(0,index);
       //alert("first part  " + part1);
        if(i==0)
            rstr=part1+"\\\\";
        else
          rstr=rstr+part1+"\\\\";
        i++;
        str=str.substr(index+1);
        //alert("second part  " + str);
        //alert("final string  " + rstr);
     }
     rstr=rstr+str;
     //alert("at last : " + rstr);*/
     var imgctl=document.getElementById("EmpImage");
     imgctl.src=url;
     //imgctl.src=rstr;
     
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
      document.location="ImageSession.jsp?Command=destroy";
    }
    
    function fundelete()
    {
    //alert("Hai");
      document.location="ImageSession.jsp?Command=delete";
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
            var img=document.getElementById("EmpImage");
            img.src=document.frmimageupload.EmpBrowse.value;
        }
        return true;
    }
    
    </script>
    
<!--<link href="../../../../../css/green.css" rel="stylesheet" media="screen"/>-->
<link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="css/try1.css" rel="stylesheet" media="screen"/>
  </head>
  <body>
  <form name="frmimageupload" enctype="multipart/form-data" method="post" action="ImageLoad1.jsp"  >
  <style>
                .divClass{display: none;  }                
                .bgClass{color: White ; width: 100%; border-bottom: 1px solid Black ; padding: 0px; margin: 0px;background-color: rgb(135,155,216);}
  </style>
  
        <table border="1px" width="80%" align="center">
        <tr>
      <td align="center" class="tdH" width="62%" colspan="2">
      <b>Employee Image</b>
      </td>      
   </tr>
        <tr>
            <TD colspan="4" class="tdH">
               <P align=left><B>General Particulars</B></P>
            </TD>
        </tr>
               <td class="table">
                  Select Image File
                </td>
                <td class="table">
                 <%
                  boolean b=false;
                  String path=null;
            try
            {
                     HttpSession session=request.getSession(false);
                     System.out.println("photo session:"+session);
                    
                     if(session.getAttribute("Emp")!=null)
                     {
                            String s=(String)session.getAttribute("Emp");
                            int empid=Integer.parseInt(s);
                            
                            ps=connection.prepareStatement("select EMP_PHOTO_FILE_NAME from HRM_EMP_ADDL_DETAILS where EMPLOYEE_ID=?" );
                            ps.setInt(1,empid);
                            results=ps.executeQuery();
                            if(results.next())
                            {
                                System.out.println("photo exists");
                                b=true;
                                System.out.println("file Path::"+request.getContextPath()+rs.getString("Config.EMPLOYEE_PHOTOS_PATH_VIEW")+results.getString("EMP_PHOTO_FILE_NAME"));
                                path=request.getContextPath()+rs.getString("Config.EMPLOYEE_PHOTOS_PATH_VIEW")+results.getString("EMP_PHOTO_FILE_NAME");
                            }
                            results.close();
                            ps.close();
                    }
                    else
                    {
                            System.out.println("employee id is null");
                    }
            }
            catch(Exception e)
            {
                System.out.println("Error "+e);
            }
            
            if(b==true)
            {
        %>
             <IMG src="<%=path%>" id="EmpImage" name="EmpImage" width="80" height="70"/>
            <%}else{%> 
                <IMG src="../../../../../images/sample_emp.bmp" id="EmpImage" name="EmpImage" width="80" height="70"/>
                <%}%>
                <div>
                  <input type=file name="EmpBrowse" id="EmpBrowse" onchange="loadImage()"></div>
                </td>
                
        </tr>
           
           <tr>
        <td colspan="2" align="right" class="tdH">
        
                                
                                <%
                                if(b==true)
                                {
                                %>
                                <input type="submit" name="delete" value="Delete"  onclick="fundelete();return false;" >
                               <%}%>
        <input type="submit" name="upload" value="Upload" onclick="return validate()" >
        
        <input type="button" value="Cancel" onclick="fun1();">
        </td>
         </tr> 
   
      
        </table>
  
  <%
  //System.out.println(request.getParameter("Emp"));
  %>
  </form>
  </body>
</html>
