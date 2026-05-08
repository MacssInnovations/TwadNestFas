/*
package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;




public class ForgetPasswordServ extends HttpServlet {
     
   
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
{
        Connection con=null;
        try {
            
             ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                         String ConnectionString="";

                         String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
                         String strdsn=rs.getString("Config.DSN");
                         String strhostname=rs.getString("Config.HOST_NAME");
                         String strportno=rs.getString("Config.PORT_NUMBER");
                         String strsid=rs.getString("Config.SID");
                         String strdbusername=rs.getString("Config.USER_NAME");
                         String strdbpassword=rs.getString("Config.PASSWORD");

                         ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

                          Class.forName(strDriver.trim());
                          con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
        }
        catch(Exception e) {
            System.out.println("Exception in connection..."+e);
        }
    ResultSet rs=null;
    PreparedStatement ps=null;   
    PrintWriter out = response.getWriter();
           
        response.setContentType("text/xml");
        response.setHeader("Cache-Control","no-cache");
        String username=null,strCommand=null;
        Date dob=null;
        int gpfno=0;
        try {
            strCommand=request.getParameter("Command");
            System.out.println("assign....."+strCommand);
            username=request.getParameter("txtusername");
            System.out.println("assign..... user Name::"+username);
            
            String[] sd=request.getParameter("txtdob").split("/");
            Calendar c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            java.util.Date d=c.getTime();
            dob=new Date(d.getTime());
            System.out.println("assign..... dob::"+dob);
            gpfno=Integer.parseInt(request.getParameter("txtgpf"));
            System.out.println("assign..... gpf no::"+gpfno);
            
        }
        catch(Exception e) {
            System.out.println("Exception in assigning..."+e);
        }
        String xml=null;
        if(strCommand.equalsIgnoreCase("test")) {
            xml="<response><command>test</command>";
             try
            {
               
              /* Don't remove or change the following code it will make a major problem
              // Don't remove or change the following code it will make a major problem
              // ======================================================================
              // this code will change the actual password into MD5 CHIPER TEST    
                 try
                 {
                 ps=con.prepareStatement("select USER_ID,USER_PASSWORD from SEC_MST_USERS  " );
                 rs=ps.executeQuery();
                 
                 while(rs.next()) {
                 
                    String uid=rs.getString("USER_ID");
                    String upass =rs.getString("USER_PASSWORD");
                    
                     byte []b=upass.getBytes();
                     try{
                         MessageDigest algorithm = MessageDigest.getInstance("MD5");
                         algorithm.reset();
                         algorithm.update(b);
                         byte messageDigest[] = algorithm.digest();
                         System.out.println("actual encrypt::"+messageDigest);                            
                         StringBuffer hexString = new StringBuffer();
                         for (int i=0;i<messageDigest.length;i++) {
                              hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
                         }
                                                    
                             upass=new String(hexString);
                             
                         }
                         catch(NoSuchAlgorithmException nsae){
                               System.out.println(nsae);          
                             }
                 
                     ps=con.prepareStatement("update  SEC_MST_USERS set USER_PASSWORD=?  where USER_ID=?" );
                     ps.setString(1,upass);
                     ps.setString(2,uid);
                     ps.executeUpdate();
                    // System.out.println("upass:"+upass);
                     
                     
                 }
                 }catch(Exception e){System.out.println("error in update passwords:"+e);}
                 System.out.println("passwords updated");
                 
               
                
                ps=con.prepareStatement("select EMPLOYEE_ID from SEC_MST_USERS  where USER_ID=? " );
                ps.setString(1,username);
                rs=ps.executeQuery();
                //System.out.println("test1");
                String emp_name=null;
                if(rs.next())  
                {
                    String empid=rs.getString("EMPLOYEE_ID");
                    System.out.println("employee id::"+empid);
                            rs.close();
                            ps.close();
                            ps=con.prepareStatement("select EMPLOYEE_NAME ||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,EMPLOYEE_PREFIX,GENDER from HRM_MST_EMPLOYEES  where  DATE_OF_BIRTH=? and GPF_NO=? and EMPLOYEE_ID=?" );
                            ps.setDate(1,dob);
                            ps.setInt(2,gpfno);
                            ps.setInt(3,Integer.parseInt(empid));
                             rs=ps.executeQuery();
                            
                            if(rs.next())  {
                                
                                Random random =  new Random();
                                        int r1 = random.nextInt(127);
                                        int r2 = random.nextInt(127);
                                        int r3 = random.nextInt(127);
                                        int r4 = random.nextInt(127);
                                        
                                        String hash1 = Integer.toHexString(r1);
                                        String hash2 = Integer.toHexString(r2);
                                        String hash3 = Integer.toHexString(r3);
                                        String hash4 = Integer.toHexString(r4);
                                        String hash = hash1 + hash2 +hash3 + hash4;
                                       
                                        
                                       
                                        
                                            System.out.println("hash String::"+hash);
                                            emp_name=rs.getString("EMPLOYEE_NAME");
                                            String prefix=null;
                                            if(rs.getString("EMPLOYEE_PREFIX")!=null) {
                                                prefix=rs.getString("EMPLOYEE_PREFIX");
                                            }
                                            else {
                                                prefix="Mr/Ms";
                                            }
                                            String content=null;
                                            content ="\n\n";
                                            content +="Dear "+prefix+"."+emp_name;
                                            content +=",\n\tYour request for new password has been processed. The new password is as follows,";
                                            content +="\n";
                                            content +="\nUser A/C  :"+username;
                                            content +="\nYour New Password :"+hash;
                                            
                                            content +="\n\n";
                                            content +="Please do not forget to change this password when you login next time in your Intranet Site.";
                                            content +="This is an autogenerated E-Mail";
                                            content +="\nPlease don't reply to this mail.";
                                            
                                 String strPassword=null;           
                                byte []b=hash.getBytes();
                                try{
                                    MessageDigest algorithm = MessageDigest.getInstance("MD5");
                                    algorithm.reset();
                                    algorithm.update(b);
                                    byte messageDigest[] = algorithm.digest();
                                    System.out.println("actual encrypt::"+messageDigest);                            
                                    StringBuffer hexString = new StringBuffer();
                                    for (int i=0;i<messageDigest.length;i++) {
                                         hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
                                    }
                                                               
                                        strPassword=new String(hexString);
                                    }
                                    catch(NoSuchAlgorithmException nsae){
                                          System.out.println(nsae);          
                                        }
                                
                               
                                            
                                            ps=con.prepareStatement("update  SEC_MST_USERS set USER_PASSWORD=?,CHANGE_PASSWORD='1'  where USER_ID=?" );
                                            ps.setString(1,strPassword);
                                            ps.setString(2,username);
                                            ps.executeUpdate();
                                            
                                            
                                            java.util.Properties props = new java.util.Properties();
                                          props.put("mail.smtp.host", "mail.tn.nic.in");
                                          // props.put("mail.smtp.host", "ibmclient1");
                                           props.put("mail.smtp.port", 25);
                                           Session session = Session.getDefaultInstance(props, null);
                                   
                                           Message msg = new MimeMessage(session);
                                           //msg.setFrom(new InternetAddress("senthil@ibmclient1.com"));
                                            msg.setFrom(new InternetAddress("raman@tn.nic.in"));
                                           
                                            msg.setRecipient(Message.RecipientType.TO, new InternetAddress("nicraman@gmail.com"));
                                            msg.setRecipient(Message.RecipientType.CC, new InternetAddress("raman@tn.nic.in"));
                                           // msg.setRecipient(Message.RecipientType.TO, new InternetAddress("nsenthilkumar83@yahoo.co.in"));
                                           msg.setSubject("password sent by TWAD "+(new SimpleDateFormat("dd/mm/yy").format(new java.util.Date())));
                                           msg.setText(content);
                                           msg.setSentDate(new java.util.Date());
                                        
                                           Transport.send(msg);
                               
                                xml=xml+"<flag>success</flag>";
                              
                            }
                            else
                            {
                                   xml=xml+"<flag>failure</flag>";
                            }
                }
                else
                    {
                           xml=xml+"<flag>failure1</flag>";
                    }
            }
           
            catch(Exception e) {
            
                System.out.println("catch........"+e);
               xml=xml+"<flag>failure</flag>";
            }
                     
        xml=xml+"</response>";
        out.println(xml); 
        System.out.println(xml);   
        
        } 
        
        
        
      
    }
}
*/