package Servlets.FAS.FAS1.CivilBills.servlets;

//import Servlets.Security.classes.UserProfile;

import java.io.*;
import java.sql.*;
import java.util.ResourceBundle;

import javax.servlet.http.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class Payeetype extends HttpServlet
{
  //private static final String CONTENT_TYPE="text/xml; charset=windows-1252";
	public void init(ServletConfig config)throws ServletException
	{ 
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException		
 	{
		String CONTENT_TYPE="text/xml; charset=windows-1252";
                response.setContentType(CONTENT_TYPE);
                response.setHeader("Cache-Control","no-cache");
                System.out.println("Welcome to PayeeType Servlet");
		String cmnd="";
		String xml="";
                String user_id;
                user_id = "";
                int count=0;int payee_code=0;String payee_desc="";String status="";
                String update_user="";
                HttpSession session=null;
                Timestamp ts=null;
                PrintWriter pw=response.getWriter();
                
                /*********** connection establishment****************/
                Connection con=null;
                ResultSet rs2,rs3;rs2=null;rs3=null;
                PreparedStatement ps2,ps3;ps2=null;ps3=null;
                xml="<response>";
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
                        String userid=(String)session.getAttribute("UserId");
                        System.out.println("session id is:"+userid);
                        update_user=(String)session.getAttribute("UserId");
                        long l=System.currentTimeMillis();
                        ts=new Timestamp(l);           
   
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
                  /*****************Getting the values from jsp page ***************/
                    if(cmnd.equalsIgnoreCase("add")) 
                    {
                        xml=xml+"<command>addResponse</command>";
                            try
                            {    
                                 String sqlsel="select decode(max(PAYEE_TYPE_CODE),null,0,max(PAYEE_TYPE_CODE))as PAYEE_TYPE_CODE from FAS_PAYEE_TYPES_MST";    
                                 ps2=con.prepareStatement(sqlsel);
                                 rs2=ps2.executeQuery();
                                 if(rs2.next())
                                 {
                                    payee_code=rs2.getInt("PAYEE_TYPE_CODE");
                                 }
                                    payee_code=payee_code+1;
                                     System.out.println("Maximum value of payee_code is :"+payee_code);
                                     ps2.close();
                                     rs2.close();
                            }
                             catch(Exception e11)
                             {
                             System.out.println("Exception arised finding the maximum value **** :"+e11);
                             } 
                               try
                               {
                                    payee_desc=request.getParameter("PayeetypeDesc1");
                                    System.out.println("description::::::"+payee_desc);
                                    status="L";
                                    String sqlload="insert into FAS_PAYEE_TYPES_MST values (?,?,?,?,?)";
                                    System.out.println("insert query----"+sqlload);
                                    ps2 = con.prepareStatement(sqlload);
                                    ps2.setInt(1,payee_code);
                                    ps2.setString(2,payee_desc);
                                    ps2.setString(3,status);
                                    ps2.setString(4,update_user);
                                    ps2.setTimestamp(5,ts);
                                    ps2.executeUpdate();
                                    count++;
                                if(count>0)
                                {
                                   
                                    xml=xml+"<payeecode>"+payee_code+"</payeecode>";
                                    System.out.println("records inserted successfully");
                                    xml=xml+"<flag>success</flag>"; 
                                }
                                 ps2.close();
                             } //try close
                              catch(Exception e)
                              {
                                                xml=xml+"<flag>failure</flag>";
                                                System.out.println(e);
                               }
                    }
                   else if(cmnd.equalsIgnoreCase("updated")) 
                    {
                        xml=xml+"<command>updated</command>";
                            try
                            {    
                                System.out.println("calling update query");  
                                 int PayeeypeCode1=Integer.parseInt(request.getParameter("PayeeypeCode1"));
                                 System.out.println("code ::::::"+PayeeypeCode1);
                                 String PayeetypeDesc1=request.getParameter("PayeetypeDesc1");
                                 System.out.println("description::::::"+PayeetypeDesc1);
                                   
                                    String sqlload="update FAS_PAYEE_TYPES_MST set PAYEE_TYPE_DESC=? where PAYEE_TYPE_CODE=?";
                                    System.out.println("update query---"+sqlload);
                                    ps2 = con.prepareStatement(sqlload);
                                    ps2.setString(1,PayeetypeDesc1);
                                    ps2.setInt(2,PayeeypeCode1);
                                    ps2.executeUpdate();
                                    count++;
                                if(count>0)
                                {
                                    System.out.println("record updated successfully");
                                    xml=xml+"<flag>success</flag>"; 
                                }
                                 ps2.close();
                             } //try close
                              catch(Exception e)
                              {
                                                xml=xml+"<flag>failure</flag>";
                                                System.out.println(e);
                               }
                    }
            else if(cmnd.equalsIgnoreCase("loadlist")) 
            {
                xml=xml+"<command>gett</command>";
                try
                {             
                            String sqlload="select PAYEE_TYPE_CODE,PAYEE_TYPE_DESC,STATUS from FAS_PAYEE_TYPES_MST order by PAYEE_TYPE_CODE";
                            ps2 = con.prepareStatement(sqlload);
                            rs2=ps2.executeQuery();
                            while(rs2.next())
                            {
                                xml=xml+"<payee_code>"+rs2.getInt("PAYEE_TYPE_CODE")+"</payee_code>";
                                xml=xml+"<payee_desc>"+rs2.getString("PAYEE_TYPE_DESC")+"</payee_desc>";
                                xml=xml+"<status>"+rs2.getString("STATUS")+"</status>";
                                count++;
                            }
                             if(count>0)
                             {
                                 xml=xml+"<flag>success</flag>"; 
                             }
                             else
                             {
                                 xml=xml+"<flag>nodata</flag>";    
                             }
                     ps2.close();
                     rs2.close();
                 } //try close
                  catch(Exception e)
                  {
                                    xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                    System.out.println(e);
                   }
            }
            else if(cmnd.equalsIgnoreCase("retrieve")) 
            {
                xml=xml+"<command>retrieve</command>";   
                try
                {
                        payee_code=Integer.parseInt(request.getParameter("payee_code1"));
                        System.out.println("payee_code*****"+payee_code);
                       
                        String sql="select PAYEE_TYPE_DESC from FAS_PAYEE_TYPES_MST where PAYEE_TYPE_CODE=?";
                        ps2=con.prepareStatement(sql);   
                        ps2.setInt(1,payee_code);
                        rs2=ps2.executeQuery();
                       if(rs2.next()) 
                       {
                           xml=xml+"<payee_desc>"+rs2.getString("PAYEE_TYPE_DESC")+"</payee_desc>";
                           count++;
                       }
                        if(count>0)
                            xml = xml+"<flag>success</flag>";
                        else
                            xml=xml+"<flag>failure</flag>";
                    ps2.close();
                    rs2.close();
                }
                catch(Exception e) 
                {
                    System.out.println("Exception in retrieving records ===> "+e);
                    xml=xml+"<flag>failure</flag>";
                }
            }
            else if(cmnd.equalsIgnoreCase("deleted"))
            {
                xml=xml+"<command>deleted</command>";
                payee_code=Integer.parseInt(request.getParameter("PayeeypeCode1"));
                String sqlload="";
    			boolean isInPayee = false;
                try 
                    {
	                	sqlload="select PAYEE_TYPE_CODE from FAS_CHEQUEMEMO_PAYEE_TYPES_MST where PAYEE_TYPE_CODE=? and STATUS='L'";
	    				ps2 = con.prepareStatement(sqlload);
	    				ps2.setInt(1,payee_code);
	    				rs2 = ps2.executeQuery();
	    				while(rs2.next()){
	    					isInPayee=true;
	    					xml=xml+"<paytype>y</paytype>";
	    				}
	    				if(!isInPayee){
	    					sqlload="update FAS_PAYEE_TYPES_MST set STATUS='C' where PAYEE_TYPE_CODE=?";  
	                        ps2 = con.prepareStatement(sqlload);
	                        ps2.setInt(1,payee_code);
	                        ps2.executeUpdate();
	                        xml=xml+"<paytype>n</paytype>";
	    				}
	    				xml = xml+"<flag>success</flag>";
                        ps2.close();
                        rs2.close();
                    }
                    
                catch(Exception e) 
                    {
                        xml=xml+"<flag>failure</flag>";
                    }
                    
               
            }
                    
            else if(cmnd.equalsIgnoreCase("getpayee")){
    			xml=xml+"<command>getpayee</command>";
    			try {             
    				String sqlload="select PAYEE_TYPE_CODE,PAYEE_TYPE_DESC,STATUS from FAS_PAYEE_TYPES_MST order by PAYEE_TYPE_CODE";
    				ps2 = con.prepareStatement(sqlload);
    				rs2=ps2.executeQuery();
    				while(rs2.next()){
    					xml=xml+"<payee_code>"+rs2.getInt("PAYEE_TYPE_CODE")+"</payee_code>";
                        xml=xml+"<payee_desc>"+rs2.getString("PAYEE_TYPE_DESC")+"</payee_desc>";
                        xml=xml+"<status>"+rs2.getString("STATUS")+"</status>";
    					count++;
    				}
    				if(count>0) {
    					xml=xml+"<flag>success</flag>"; 
    				} else {
    					xml=xml+"<flag>nodata</flag>";    
    				}
    				ps2.close();
    				rs2.close();
    			} catch(Exception e) {
    				xml=xml+"<flag>"+e.getMessage()+"</flag>";
    				System.out.println(e);
    			}
    		}else if(cmnd.equalsIgnoreCase("edit")){
    			xml=xml+"<command>getpayee</command>";
    			try {
    				int payCode = Integer.parseInt(request.getParameter("payCode"));  
    				String sqlload="select PAYEE_TYPE_CODE,PAYEE_TYPE_DESC,STATUS from FAS_PAYEE_TYPES_MST where PAYEE_TYPE_CODE=?";
    				ps2 = con.prepareStatement(sqlload);
    				ps2.setInt(1, payCode);
    				rs2=ps2.executeQuery();
    				if(rs2.next()){
    					xml=xml+"<flag>success</flag>";
    					xml=xml+"<payee_code>"+rs2.getInt("PAYEE_TYPE_CODE")+"</payee_code>";
                        xml=xml+"<payee_desc>"+rs2.getString("PAYEE_TYPE_DESC")+"</payee_desc>";
    				}else{
    					xml=xml+"<flag>nodata</flag>";    
    				}
    				ps2.close();
    				rs2.close();
    			} catch(Exception e){
    				xml=xml+"<flag>"+e.getMessage()+"</flag>";
    				System.out.println(e);
    			}
    		}
                  xml=xml+"</response>";
                  System.out.println("xml is : " + xml);
                  pw.write(xml);
                  pw.flush();
                  pw.close();
        }
}
