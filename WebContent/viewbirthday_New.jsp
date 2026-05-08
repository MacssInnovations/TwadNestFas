<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.text.*, java.util.ResourceBundle"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>viewbirthday</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js">
     function call()
    {
    
    //alert("called");
    var empid=document.frmEmployee.txtEmpId1.value;
    document.getElementById("EmpImage").src="Show_Image.jsp?empid="+empid;
    //return ;
    }
     
     </script>
   
  <!--  <link href="./css/birthday.css" rel="stylesheet"  media="screen"/>-->
    <link href="./css/Sample3.css" rel="stylesheet"  media="screen"/>
     <%
        Connection connection=null;
        ResultSet rs=null;
        Statement st=null;
        boolean flag=false;
        String statid="";
                   
    %>
    
  </head>
  <body class="table">
  <form name="frm1" id="frm1">
  <center>
   
   <%
 try
  {
                    System.out.println("view birthday");
            
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

  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
 
   
         
         try
          {
            st=connection.createStatement();
             SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy");
             String strdate=sd.format(new Date(System.currentTimeMillis()));
           /* String sql="select A.EMPLOYEE_NAME||DECODE(A.EMPLOYEE_INITIAL,null,' ','.'||A.EMPLOYEE_INITIAL) EMPLOYEE_NAME, to_char(a.DATE_OF_BIRTH,'DD/MM/YYYY') as dob," + 
            " c.office_short_name,d.designation, d.designation_id " +
            " from hrm_mst_employees a " +
            " inner join hrm_emp_current_Posting b on b.employee_id=a.employee_id "+
            " left join com_mst_offices c on c.office_id=b.office_id " +
            " left join hrm_mst_designations d on d.designation_id=b.designation_id "+
            " where to_char(A.DATE_OF_BIRTH,'MM/DD')=to_char(now(),'MM/DD')";*/
            
               
            String sql="select * from " +
                       " ( " +
                       " select employee_id, EMPLOYEE_NAME||DECODE(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) EMPLOYEE_NAME, " +
                       " date_of_birth from hrm_mst_employees " +
                       " where to_char(DATE_OF_BIRTH,'MM/DD')=to_char(now(),'MM/DD') " +
                       " ) a " +
                       " inner join " + 
                       " ( " +
                       " select employee_id, designation_id, office_id, department_id, employee_status_id from hrm_emp_current_posting " +
                       
                       " ) b " +
                       " on a.employee_id = b.employee_id " +
                       " left outer join " +
                       " ( " +
                       " select designation_id, designation from hrm_mst_designations " +
                       " ) c " +
                       " on b.designation_id = c.designation_id " +
                       " left outer join " + 
                       " ( " +
                       " select 'TWAD' as dept_id, office_id, office_name from com_mst_offices " +
                       " union " +
                       " select other_dept_id as dept_id, other_dept_office_id as office_id, other_dept_office_name as office_name from hrm_mst_other_dept_offices " +
                       " ) d " +
                       " on b.department_id = d.dept_id and b.office_id = d.office_id " +
                       " left outer join " +
                       " ( " +
                       " select cur_post_reason_id, cur_post_reason_desc from hrm_mst_cur_post_status " +
                      " ) e " +
                      " on b.employee_status_id=e.cur_post_reason_id "+
                      " left join "+
                      " ( "+
                      " select employee_id as eid,photo from HRM_EMP_ADDL_PHOTO_NEW_TMP "+
                      " )f " +
                      " on a.employee_id=f.eid";
                      
            
            rs=st.executeQuery(sql);               
            System.out.println(rs);
            
            System.out.println(sql);
                  
            System.out.println("Test");  
           
           while(rs.next())
            {     
              String eid=rs.getString("employee_id");
                if(flag!=true)
                {
           
                  out.println("<h1 class='a'>TWAD Board Wishes the following Employees</h1>");
                  out.println("<h1 class='a'><i>Many More Happy Returns of the Day</i></h1>");
                  out.println("<h2 class='a'>"+strdate+"</h2>");
                  out.println("<body background='images\\wishes.gif' bgproperties='fixed'>");
                  out.println("<img src='images\\bouquet.gif' alt='Happy Birthday' height='100' width='100'></img>");
                  out.println("<table  border=1 cellspacing=0 width='50%'>");
                 }
        
                    out.println("<tr class='a' align='left'>");
                    //out.println("<img src='' alt='image' height='75' width='75'>");
                    String url1="Show_Image.jsp?empid="+eid; 
                    
                    System.out.println(url1);
                    out.println("<td rowspan='4' width='12%'><img   align='left' src="+url1+ " alt='Photo' width='100px' height='100px' name='EmpImage' id='EmpImage'  ></img></td>");
                    out.println("<td  align='left'>Employee&nbsp;&nbsp;ID&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>");
                    out.println("<td>"+rs.getString("employee_id")+"</td></tr>");
                    out.println("<td align='left'>Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>");
                    out.println("<td align='left'>"+rs.getString("EMPLOYEE_NAME")+"</td></tr>");
                    out.println("<tr class='a' align='left'><td align='left'>Designation&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>");
                    out.println("<td>"+((rs.getString("designation")!=null)?rs.getString("designation"):"")+"</td>");
                    out.println("</tr>");
                    out.println("<tr class='a' align='left'><td align='left'>Place of Posting&nbsp;</td> ");
                    out.println("<td>"+((rs.getString("office_name")!=null)?rs.getString("office_name"):"-")+"</td>");
                    statid=rs.getString("cur_post_reason_id");
                    if(statid.equals("WKG"))
                    {
                    //out.println("<tr class='a' align='left'><td align='left'>Remarks&nbsp;:</td> ");
                    //out.println("<td>"+((rs.getString("cur_post_reason_desc")!=null)?rs.getString("cur_post_reason_desc"):" ")+"</td>");
                    }
                    else
                    {
                    out.println("<tr class='a' align='left'><td align='left'>Remarks&nbsp;</td> ");
                    out.println("<td colspan='2'>"+((rs.getString("cur_post_reason_desc")!=null)?rs.getString("cur_post_reason_desc"):" ")+"</td>");
                    }
                    out.println("</tr>");
                    out.println("<tr><td colspan=3>&nbsp;</td></tr>");
                    flag=true;
                    System.out.println("end");
                                                                     
            } 
            
            out.println("</table>");
            
           if(flag!=true)
            {
            out.println("<h2 class='a'>"+strdate+"</h2>");
            out.println("<h2 class='a'><font color='maroon'>Sorry, Today No Birthday Buddies</font></h2>");
            }
           
            }catch(Exception e) {
            System.out.println("Err:"+e.getMessage());
            System.out.println(e);
            }  
       %>
  </center>
  </form>
  </body>
</html>