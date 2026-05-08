<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.text.*, java.util.ResourceBundle"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252" />
<title>Retirement</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>


<link href="./css/Sample3.css" rel="stylesheet" media="screen" />
<%
        Connection connection=null;
        ResultSet rsnew=null;
        Statement st=null;
        boolean flag=false;
        String statid="";
        String newpath="";
                   
    %>

</head>
<body class="table">
<form name="frm1" id="frm1">
<center>
<%
 try
  {
                    System.out.println("Retirement");
            
                      ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                        String ConnectionString="";
                
                        String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                        String strdsn=rs1.getString("Config.DSN");
                        String strhostname=rs1.getString("Config.HOST_NAME");
                        String strportno=rs1.getString("Config.PORT_NUMBER");
                        String strsid=rs1.getString("Config.SID");
                        String strdbusername=rs1.getString("Config.USER_NAME");
                        String strdbpassword=rs1.getString("Config.PASSWORD");
                
                       // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
             SimpleDateFormat sd=new SimpleDateFormat("MMMM-yyyy");
             String strdate=sd.format(new Date(System.currentTimeMillis()));
             
              
            String sql="select employee_id, employee_name, date_of_birth, retiredate,  designation, "+
                       " office_name, employee_status_id, employee_status_desc, desig_order_no from " +
                       " ( "+
                       " select employee_id, employee_name, date_of_birth, retiredate, designation_id, designation, "+
                       " department_id, office_id, office_name, employee_status_id, to_char(retiredate, 'Month YYYY') as retire_yrmonth, " +
                       " to_char(retiredate,'mm') as order_month, to_char(retiredate,'mm/yyyy') as order_year, desig_order_no from "+
                       " ( "+
                       " select * from RETIRELIST_58YRS_AFTER_FIRST "+
                       " union " +
                       " select * from RETIRELIST_58YRS_ON_FIRST "+
                       " union " +
                       " select * from RETIRELIST_60YRS_AFTER_FIRST "+
                       " union " +
                       " select * from RETIRELIST_60YRS_ON_FIRST "+
                       " ) a "+
                       " )x "+
                       " left outer join " +
                       " ( " +
                       " select employee_status_id as emp_statid,employee_status_desc from HRM_MST_EMPLOYEE_STATUS " +
                       " ) y " +
                       " on x.employee_status_id=y.emp_statid " +
                       " where to_char(retiredate,'MM/yyyy')=to_char(now(),'MM/yyyy') " +
                       " and x.employee_status_id not in ('DTH','VRS','SAN','RES') " +
                       " order by retiredate,desig_order_no";
                       
                       rsnew=st.executeQuery(sql);     
                       
              
           
           while(rsnew.next())
            {     
             
                if(flag!=true)
                {
           
                  out.println("<h1 class='a'>TWAD Board Wishes the following Employees</h1>");
                  out.println("<h1 class='a'><i>on the eve of Super Annuation</i></h1>");
                  out.println("<h2 class='a'>"+strdate+"</h2>");
                  out.println("<img src='images\\bouquet.gif' alt='Retire' height='100' width='100'></img>");
                  out.println("<table  border=1 cellspacing=0>");
               }
               
                  newpath="ShowImageProfile.jsp?empid="+rsnew.getString("employee_id");
                  System.out.println("newpath"+newpath);
                 
                       
                    out.println("<tr class='a' align='left'>");
                    out.println("<td rowspan='5'><img src='"+newpath+"' alt='image' height='115' width='95'></img></td>");
                    out.println("<td align='left'>Employee&nbsp;&nbsp;ID&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: </td>");
                    out.println("<td>"+rsnew.getString("employee_id")+"</td></tr>");
                    out.println("<td align='left'>Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: </td>");
                    out.println("<td align='left'>"+rsnew.getString("employee_name")+"</td></tr>");
                    out.println("<tr class='a' align='left'><td align='left'>Designation&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: </td>");
                    out.println("<td>"+((rsnew.getString("designation")!=null)?rsnew.getString("designation"):"")+"</td>");
                    out.println("</tr>");
                    out.println("<tr class='a' align='left'><td align='left'>Place of Posting&nbsp;:</td> ");
                    out.println("<td>"+((rsnew.getString("office_name")!=null)?rsnew.getString("office_name"):" ")+"</td>");
                    statid=rsnew.getString("employee_status_id");
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
                    out.println("<td>"+((rsnew.getString("employee_status_desc")!=null)?rsnew.getString("employee_status_desc"):" ")+"</td>");
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
            out.println("<h2 class='a'><font color='maroon'>Sorry, Today No Employees in Super Annuation</font></h2>");
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