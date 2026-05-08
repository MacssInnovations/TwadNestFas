package Servlets.FAS.FAS1.CivilBills.servlets;

import java.io.*;
import java.sql.*;
import java.util.ResourceBundle;

import javax.servlet.http.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class fasbilltype_master_servlet extends HttpServlet
{
  private static final String CONTENT_TYPE="text/xml; charset=UTF-8";
	public void init(ServletConfig config)throws ServletException
	{ 
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException		
 	{
		response.setContentType(CONTENT_TYPE);
                response.setHeader("Cache-Control","no-cache");
                System.out.println("Welcome to Servlet");
		String cmnd="";
		String xml="";
                int bill_code=0;
                int bill_type_code=0;
                String bill_type_desc="";
                String billremarks="";
                PrintWriter pw=response.getWriter();
                HttpSession session=null;
                /*********** connection establishment****************/
                Connection con=null;
                ResultSet rs=null;
                Statement st=null;
                PreparedStatement ps=null;
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
                                             
                            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                                Class.forName(strDriver.trim());
                                con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                }
                catch(Exception e)
                {
                        System.out.println("Exception in connection...."+e);
                } 
                      try
                      {
                          session=request.getSession(false);
                          if(session==null)
                          {
                              System.out.println(request.getContextPath()+"/index.jsp");
                              response.sendRedirect(request.getContextPath()+"/index.jsp");
                              return;
                          }
                          System.out.println(session);
                      } 
                      catch(Exception e)
                      {
                            System.out.println("Redirect Error :"+e);
                      }
                      
                /****************** getting the values from Button Pressed***********/
                try
                {
                          cmnd =  request.getParameter("command");     
                          System.out.println("Command passed via the button pressed : " + cmnd);
                }
                  catch(Exception e3)
                  {
                    e3.printStackTrace();
                  }
                  /*****************Getting the values from Text box bill_type_code***************/
                   try
                  {
                          bill_type_code=Integer.parseInt(request.getParameter("bill_type_code1"));
                          //System.out.println("Bill_Type_Code  is : " + bill_type_code);
                  }
                  catch(Exception e4)
                  { 
                      System.out.println("Exception fetching bill_type_code ===> " + e4);
                  }        
                  /************** Getting the value from TextBox bill_type_desc******************/        
                  try
                  {
                          bill_type_desc = request.getParameter("billtype_desc1");
                          //System.out.println("Bill_type_description  is  : "+ bill_type_desc);
                  }
                  catch(Exception e5)
                  { 
                      System.out.println("Exception fetching bill_type_description===> " + e5);
                  }
                  /*************Getting value from the textBox Bill_type_Remarks****************/
                  try
                  {
                     billremarks=request.getParameter("billremarks1");
                     //System.out.println("Bill Remarks :" +billremarks);
                  }
		  catch(Exception e15)
                  { 
                      System.out.println("Exception fetching bill_type_description===> " + e15);
                  }

                  if(cmnd.equalsIgnoreCase("delete"))
                  {
                          System.out.println("\n*************\nDelete\n**************\n");
                      xml="<response><command>Delete</command>";
                      try 
                      {
                                  String sqlDelete = "DELETE FROM FAS_BILL_MAJOR_TYPES WHERE BILL_MAJOR_TYPE_CODE = ?";
                                  ps = con.prepareStatement(sqlDelete);
                                  ps.setInt(1, bill_type_code);
                                  ps.executeUpdate();
                                  xml=xml+"<flag>success</flag>";
                                  xml=xml+"<billtype_code>" +bill_type_code  + "</billtype_code>";
                                  xml=xml+"<billtype_desc>" + bill_type_desc + "</billtype_desc>";
                                  xml=xml+"<billremarks>" + billremarks + "</billremarks>";
                      }
                      catch(Exception e6)
                      {
                          System.out.println("Exception in Deleting record ===> "+e6);
                          xml=xml+"<flag>failure</flag>";
                      }
                      xml=xml+"</response>";
                  }
                          
                  else if(cmnd.equalsIgnoreCase("Update"))
                  {
                          System.out.println("\n*************\nUpdate\n**************\n");
                      xml="<response><command>Update</command>";
                      try 
                      {
                                   String sqlUpdate = "UPDATE FAS_BILL_MAJOR_TYPES SET  BILL_MAJOR_TYPE_DESC=?,REMARKS=? WHERE BILL_MAJOR_TYPE_CODE= ?";
                                   ps = con.prepareStatement(sqlUpdate);
                                   ps.setString(1,bill_type_desc);
                                   ps.setString(2,billremarks);
                                   ps.setInt(3,bill_type_code);
                                   ps.executeUpdate();
                          xml=xml+"<flag>success</flag>";         
                          xml=xml+"<billtype_code>" + bill_type_code + "</billtype_code>";
                          xml=xml+"<billtype_desc>" + bill_type_desc + "</billtype_desc>";
                          xml=xml+"<billremarks>" + billremarks +"</billremarks>";
                      }
                      catch(Exception e7)
                      {
                          System.out.println("Exception in Updating record ===> "+e7);
                          xml=xml+"<flag>failure</flag>";
                      }
                      xml=xml+"</response>";
                  }
                          
                  else if(cmnd.equalsIgnoreCase("Add"))
                  {
                          System.out.println("\n*************\nAdd\n**************\n");
                          xml="<response><command>Add</command>";

                      try 
                      {
                                 
                                  System.out.println("Bill_type_description  is  : "+ bill_type_desc);
                                  System.out.println("Bill Remarks :" +billremarks);
                                 //System.out.println("bill_code:"+bill_code);
                                   String update_user=(String)session.getAttribute("UserId");
                                  long l=System.currentTimeMillis();
                                  Timestamp ts=new Timestamp(l);
                                 st=con.createStatement();
                                  rs= st.executeQuery("select MAX(BILL_MAJOR_TYPE_CODE)as bill_code from FAS_BILL_MAJOR_TYPES");
                                  if(rs.next())
                                  {
                                   bill_code=rs.getInt("bill_code");
                                  }
                                  bill_code=bill_code+1;
                                  System.out.println("bill_code"+bill_code);
                                  String sqlAdd = "insert into FAS_BILL_MAJOR_TYPES values(?,?,?,?,?)";
                                   ps = con.prepareStatement(sqlAdd);
                                   ps.setInt(1,bill_code);
                                   ps.setString(2,bill_type_desc);
                                   ps.setString(3,billremarks);
                                   ps.setString(4,update_user);
                                   ps.setTimestamp(5,ts);
                                   ps.executeUpdate();
                        xml=xml+"<flag>success</flag>";         
                        xml=xml+"<billtype_code>" + bill_code + "</billtype_code>";
                        xml=xml+"<billtype_desc>" + bill_type_desc + "</billtype_desc>";
                        xml=xml+"<billremarks>" + billremarks +"</billremarks>";
                      }
                      catch(Exception e8)
                      {
                          System.out.println("Exception in Adding record ===> "+e8);
                          xml=xml+"<flag>failure</flag>";
                      }
                      xml=xml+"</response>";
                  }
                                              
                 else if(cmnd.equalsIgnoreCase("Get"))
                  {
                  System.out.println("\n*************\nGet\n**************\n");
                  xml="<response><command>Get</command>";
                  rs = null;
                  try
                  {
                   String sqlGet = "select * from FAS_BILL_MAJOR_TYPES order by BILL_MAJOR_TYPE_CODE";
                   ps = con.prepareStatement(sqlGet);
                   rs = ps.executeQuery();
                   xml=xml+"<flag>success</flag>";
                   while(rs.next())      
                   {
                       xml=xml+"<billtype_code>" + rs.getInt("BILL_MAJOR_TYPE_CODE") + "</billtype_code>";
                       xml=xml+"<billtype_desc>" + rs.getString("BILL_MAJOR_TYPE_DESC") + "</billtype_desc>";
                       xml=xml+"<billremarks>" + rs.getString("REMARKS")+"</billremarks>";
                   }
                  }
                  catch(Exception e9)
                  {
                   System.out.println("Exception in Getting records ===> "+e9);
                   xml=xml+"<flag>failure</flag>";
                  }
                  xml=xml+"</response>";
                  }
                          System.out.println("xml is : " + xml);
                          pw.write(xml);
                          pw.flush();
                          pw.close();
                  
                  }
}
              
