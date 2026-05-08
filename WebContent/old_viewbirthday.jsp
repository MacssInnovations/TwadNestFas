<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.text.*, java.util.ResourceBundle"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>viewbirthday</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
   
  <!--  <link href="./css/birthday.css" rel="stylesheet"  media="screen"/>-->
    <link href="./css/Sample3.css" rel="stylesheet"  media="screen"/>
     <%
        Connection connection=null;
        ResultSet rsnew=null;
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
             
               
            String sql="select * from " +
                       " ( " +
                       " select employee_id, EMPLOYEE_NAME||DECODE(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) EMPLOYEE_NAME, " +
                       " date_of_birth from hrm_mst_employees " +
                       " where to_char(DATE_OF_BIRTH,'MM/DD')=to_char(now(),'MM/DD') " +
                       " ) a " +
                       " inner join " + 
                       " ( " +
                       " select employee_id, designation_id, office_id, department_id, employee_status_id from hrm_emp_current_posting where employee_status_id not in ( 'SAN', 'VRS', 'DTH', 'RES') " +
                       
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
                      " on b.employee_status_id=e.cur_post_reason_id order by employee_name";
            
            rsnew=st.executeQuery(sql);               
            //System.out.println(rs);
            
           // System.out.println(sql);
                  
           // System.out.println("Test");  
           
           while(rsnew.next())
            {     
             
                if(flag!=true)
                {
           
                  out.println("<h1 class='a'>TWAD Board Wishes the following Employees</h1>");
                  out.println("<h1 class='a'><i>Many More Happy Returns of the Day</i></h1>");
                  out.println("<h2 class='a'>"+strdate+"</h2>");
                  out.println("<img src='images\\bouquet.gif' alt='Happy Birthday' height='100' width='100'></img>");
                  out.println("<table  border=1 cellspacing=0>");
               }
           
                    out.println("<tr class='a' align='left'>");
                    //out.println("<img src='' alt='image' height='75' width='75'>");
                    out.println("<td align='left'>Employee&nbsp;&nbsp;ID&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: </td>");
                    out.println("<td>"+rsnew.getString("employee_id")+"</td></tr>");
                    out.println("<td align='left'>Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: </td>");
                    out.println("<td align='left'>"+rsnew.getString("EMPLOYEE_NAME")+"</td></tr>");
                    out.println("<tr class='a' align='left'><td align='left'>Designation&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: </td>");
                    out.println("<td>"+((rsnew.getString("designation")!=null)?rsnew.getString("designation"):"")+"</td>");
                    out.println("</tr>");
                    out.println("<tr class='a' align='left'><td align='left'>Place of Posting&nbsp;:</td> ");
                    out.println("<td>"+((rsnew.getString("office_name")!=null)?rsnew.getString("office_name"):" ")+"</td>");
                    statid=rsnew.getString("cur_post_reason_id");
                    if (statid != null ) 
                    {
                    if(statid.equals("WKG"))
                    {
                    //out.println("<tr class='a' align='left'><td align='left'>Remarks&nbsp;:</td> ");
                    //out.println("<td>"+((rs.getString("cur_post_reason_desc")!=null)?rs.getString("cur_post_reason_desc"):" ")+"</td>");
                    }
                    else
                    {
                    out.println("<tr class='a' align='left'><td align='left'>Remarks&nbsp;:</td> ");
                    out.println("<td>"+((rsnew.getString("cur_post_reason_desc")!=null)?rsnew.getString("cur_post_reason_desc"):" ")+"</td>");
                    }
                    out.println("</tr>");
                    }
                    out.println("<tr><td colspan=2>&nbsp;</td></tr>");
                    flag=true;
                                                                     
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