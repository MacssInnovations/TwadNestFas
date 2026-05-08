package Servlets.FAS.FAS1.CivilBills.servlets;

//import Servlets.Security.classes.UserProfile;

import java.io.*;
import java.sql.*;
import java.util.ResourceBundle;

import javax.servlet.http.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class PhoneCertificate extends HttpServlet
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
                System.out.println("Welcome to PhoneCertificate Servlet");
		String cmnd="";
		String xml="";
                String user_id;
                user_id = "";double phone_no=0;
                int acc_unit_id=0;int acc_unit_officeid=0;
                int count=0;int phone_certi_code=0;int bill_majr_code=0;
                int bill_minr_code=0;int bill_sub_code=0;int bill_month=0;
                int bill_year=0;int invoice_no=0;String certi_text="";int phone_certi_no=0;
                String catgory_id="";
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
                                 String sqlsel="select decode(max(PHONE_CERTIFICATE_CODE),null,0,max(PHONE_CERTIFICATE_CODE))as PHONE_CERTIFICATE_CODE from FAS_PHONE_CERTIFICATE_MST";    
                                 ps2=con.prepareStatement(sqlsel);
                                 rs2=ps2.executeQuery();
                                 if(rs2.next())
                                 {
                                    phone_certi_code=rs2.getInt("PHONE_CERTIFICATE_CODE");
                                 }
                                    phone_certi_code=phone_certi_code+1;
                                     System.out.println("Maximum value of phone_certi_code is :"+phone_certi_code);
                                     ps2.close();
                                     rs2.close();
                            }
                             catch(Exception e11)
                             {
                             System.out.println("Exception arised finding the maximum value **** :"+e11);
                             } 
                               try
                               {
                                    acc_unit_id=Integer.parseInt(request.getParameter("Acc_UnitCode1"));
                                    acc_unit_officeid=Integer.parseInt(request.getParameter("Office_code1"));
                                    bill_majr_code=Integer.parseInt(request.getParameter("bill_majr_code1"));
                                    bill_minr_code=Integer.parseInt(request.getParameter("bill_minr_code1"));
                                    bill_sub_code=Integer.parseInt(request.getParameter("bill_sub_code1"));
                                    phone_no=Double.parseDouble(request.getParameter("phone_no1"));
                                    bill_month=Integer.parseInt(request.getParameter("bill_month1"));
                                    bill_year=Integer.parseInt(request.getParameter("bill_year1"));
                                    invoice_no=Integer.parseInt(request.getParameter("invoice_no1"));
                                    certi_text=request.getParameter("certi_text1");
                                    
                                    System.out.println("Accounting Unit Id::::::"+acc_unit_id);
                                    System.out.println("Accounting Unit Office Id::::::"+acc_unit_officeid);
                                    System.out.println("Bill Major Code ::::::"+bill_majr_code);
                                    System.out.println("Bill Minor code::::::"+bill_minr_code);
                                    System.out.println("Bill Sub code::::::"+bill_sub_code);
                                    System.out.println("Phone Number Selected ::::::"+phone_no);
                                    System.out.println("Bill Month ::::::"+bill_month);
                                    System.out.println("Bill Year ::::::"+bill_year);
                                    System.out.println("Invoice Number::::::"+invoice_no);
                                    System.out.println("Certificate Text  ::::::"+certi_text);
                                    
                                    String sqlload="insert into FAS_PHONE_CERTIFICATE_MST values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                    System.out.println("insert query----"+sqlload);
                                    ps2 = con.prepareStatement(sqlload);
                                    ps2.setInt(1,acc_unit_id);
                                    ps2.setInt(2,acc_unit_officeid);
                                    ps2.setInt(3,bill_majr_code);
                                    ps2.setInt(4,bill_minr_code);
                                    ps2.setInt(5,bill_sub_code);
                                    ps2.setInt(6,phone_certi_code);
                                    ps2.setDouble(7,phone_no);
                                    ps2.setInt(8,bill_month);
                                    ps2.setInt(9,bill_year);
                                    ps2.setInt(10,invoice_no);
                                    ps2.setString(11,certi_text);
                                    ps2.setString(12,update_user);
                                    ps2.setTimestamp(13,ts);
                                    ps2.setString(14,"L");
                                    ps2.executeUpdate();
                                    count++;
                                if(count>0)
                                {
                                   
                                    xml=xml+"<phone_certi_code>"+phone_certi_code+"</phone_certi_code>";
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
                                phone_no=Double.parseDouble(request.getParameter("phone_no1"));
                                bill_month=Integer.parseInt(request.getParameter("bill_month1"));
                                bill_year=Integer.parseInt(request.getParameter("bill_year1"));
                                invoice_no=Integer.parseInt(request.getParameter("invoice_no1"));
                                certi_text=request.getParameter("certi_text1");
                                
                                    String sqlload="update FAS_PHONE_CERTIFICATE_MST set BILL_MONTH=?,BILL_YEAR=?, "+
                                                    "INVOICE_NO=?,CERTIFICATE_TEXT=? where PHONE_NO=? ";
                                    System.out.println("update query---"+sqlload);
                                    ps2 = con.prepareStatement(sqlload);
                                    ps2.setInt(1,bill_month);
                                    ps2.setInt(2,bill_year);
                                    ps2.setInt(3,invoice_no);
                                    ps2.setString(4,certi_text); 
                                    ps2.setDouble(5,phone_no);
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
                            String sqlload="select * from FAS_PHONE_CERTIFICATE_MST order by PHONE_CERTIFICATE_CODE";
                            ps2 = con.prepareStatement(sqlload);
                            rs2=ps2.executeQuery();
                            while(rs2.next())
                            {
                                xml=xml+"<phone_certificate_no>"+rs2.getInt("PHONE_CERTIFICATE_CODE")+"</phone_certificate_no>";
                                xml=xml+"<phone_no>"+rs2.getString("PHONE_NO")+"</phone_no>";
                                xml=xml+"<bill_month>"+rs2.getInt("BILL_MONTH")+"</bill_month>";
                                xml=xml+"<bill_year>"+rs2.getInt("BILL_YEAR")+"</bill_year>";
                                xml=xml+"<invoice_no>"+rs2.getInt("INVOICE_NO")+"</invoice_no>";
                                xml=xml+"<certi_text>"+rs2.getString("CERTIFICATE_TEXT")+"</certi_text>";
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
                        phone_certi_no=Integer.parseInt(request.getParameter("phone_certi_no1"));
                        System.out.println("phone_certi_no*****"+phone_certi_no);
                       
                        String sql="select * from FAS_PHONE_CERTIFICATE_MST where PHONE_CERTIFICATE_CODE=?";
                        ps2=con.prepareStatement(sql);   
                        ps2.setInt(1,phone_certi_no);
                        rs2=ps2.executeQuery();
                       if(rs2.next()) 
                       {
                           xml=xml+"<acc_unit_id>"+rs2.getInt("ACCOUNTING_UNIT_ID")+"</acc_unit_id>";
                           xml=xml+"<acc_off_id>"+rs2.getInt("ACCOUNTING_UNIT_OFFICE_ID")+"</acc_off_id>";
                           xml=xml+"<bill_majrcode>"+rs2.getInt("BILL_MAJR_CODE")+"</bill_majrcode>";
                           xml=xml+"<bill_minrcode>"+rs2.getInt("BILL_MINR_CODE")+"</bill_minrcode>";
                           xml=xml+"<bill_subcode>"+rs2.getInt("BILL_SUB_CODE")+"</bill_subcode>";
                           xml=xml+"<phone_certificate_no>"+rs2.getInt("PHONE_CERTIFICATE_CODE")+"</phone_certificate_no>";
                           xml=xml+"<phone_no>"+rs2.getString("PHONE_NO")+"</phone_no>";
                           xml=xml+"<bill_month>"+rs2.getInt("BILL_MONTH")+"</bill_month>";
                           xml=xml+"<bill_year>"+rs2.getInt("BILL_YEAR")+"</bill_year>";
                           xml=xml+"<invoice_no>"+rs2.getInt("INVOICE_NO")+"</invoice_no>";
                           xml=xml+"<certi_text>"+rs2.getString("CERTIFICATE_TEXT")+"</certi_text>";
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
                phone_certi_no=Integer.parseInt(request.getParameter("phone_certi_no1"));
                try 
                    {
                        //ps2 = con.prepareStatement("delete from FAS_PHONE_CERTIFICATE_MST where PHONE_CERTIFICATE_CODE=?");  
                		ps2 = con.prepareStatement("update FAS_PHONE_CERTIFICATE_MST set STATUS='C' where PHONE_CERTIFICATE_CODE=?");
                        ps2.setInt(1,phone_certi_no);
                        ps2.executeUpdate();
                        xml = xml+"<flag>success</flag>";
                        ps2.close();
                        rs2.close();
                    }
                    
                catch(Exception e) 
                    {
                        xml=xml+"<flag>failure</flag>";
                    }
                    
               
            } 
	    else if(cmnd.equalsIgnoreCase("LoadPhoneNo"))
            {
                xml=xml+"<command>LoadPhoneNo</command>";
                acc_unit_id=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		acc_unit_officeid=Integer.parseInt(request.getParameter("cmbOffice_code"));
                try 
                    {
                        ps2 = con.prepareStatement("select PHONE_NO from FAS_PHONE_TRN where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFICE_ID=? order by employee_id");  
                        ps2.setInt(1,acc_unit_id);
                        System.out.println("acc_unit_id*******"+acc_unit_id);
			ps2.setInt(2,acc_unit_officeid);
                        System.out.println("acc_unit_officeid------"+acc_unit_officeid);
                        rs2=ps2.executeQuery();
                       while(rs2.next()) 
                       {
                           xml=xml+"<option><phone_no>"+rs2.getString("PHONE_NO")+"</phone_no></option>";
                           count++;
                       }
                        System.out.println("count after increment "+count);
                        if(count>0)
                            xml = xml+"<flag>success</flag>";
                        else
                            xml=xml+"<flag>failure</flag>";
                        ps2.close();
                        rs2.close();
                    }
                    
                catch(Exception e) 
                    {
                        xml=xml+"<flag>"+e.getMessage()+"</flag>";
                    }
                 
            } 
            else if(cmnd.equalsIgnoreCase("LoadPhoneDetails"))
            {
                
                xml=xml+"<command>LoadPhoneDetails</command>";
                acc_unit_officeid=Integer.parseInt(request.getParameter("cmbOffice_code"));
                System.out.println("office id ::::"+acc_unit_officeid);
                phone_no=Double.parseDouble(request.getParameter("phone_no"));
                System.out.println("phone_no::::::"+phone_no);
                
                
                try 
                    {
                        ps2 = con.prepareStatement("select distinct a.*,b.USER_CATEGORY_ID,d.employee_name,e.OFFICE_SHORT_NAME,f.DESIGNATION,f.DESIGNATION_ID from " + 
                        "   (  " + 
                        "      select EMPLOYEE_ID,trim(PURPOSE) as PURPOSE,trim(CONNECTION_TYPE) as CONNECTION_TYPE from FAS_PHONE_TRN where phone_no=?   " + 
                        "   ) a    " + 
                        "       left outer join " + 
                        "   (   " + 
                        "      select USER_CATEGORY_ID,EMPLOYEE_ID from FAS_PHONE_MASTER where USER_CATEGORY_ID='1' " + 
                        "   ) b ON a.employee_id=b.employee_id " + 
                        "    left outer join    " + 
                        "    (  " + 
                        "     select EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID from HRM_EMP_CURRENT_POSTING where OFFICE_ID=? and EMPLOYEE_STATUS_ID='WKG'  " + 
                        "    )c on c.EMPLOYEE_ID=b.employee_id " + 
                        "    left outer join " + 
                        "    ( select  EMPLOYEE_ID,EMPLOYEE_INITIAL||' '||EMPLOYEE_NAME as employee_name from HRM_MST_EMPLOYEES " + 
                        "    )d on c.EMPLOYEE_ID=d.EMPLOYEE_ID " + 
                        "    left outer join " + 
                        "    (  " + 
                        "      select OFFICE_ID,OFFICE_SHORT_NAME from COM_MST_OFFICES " + 
                        "    )e on c.OFFICE_ID=e.OFFICE_ID " + 
                        "    left outer join " + 
                        "    ( " + 
                        "      select DESIGNATION_ID,DESIGNATION from HRM_MST_DESIGNATIONS " + 
                        "    )f on c.DESIGNATION_ID=f.DESIGNATION_ID ");  
                        ps2.setDouble(1,phone_no);
                        
                        ps2.setInt(2,acc_unit_officeid);
                        rs2=ps2.executeQuery();
                        while(rs2.next()) 
                        {
                                catgory_id=rs2.getString("USER_CATEGORY_ID");
                                System.out.println("category id is null for non twad users......."+catgory_id);
                                if(catgory_id!=null)
                                {
                                
                                        xml=xml+"<cust_name>"+rs2.getString("employee_name")+"</cust_name>";
                                        xml=xml+"<cust_desig>"+rs2.getString("DESIGNATION")+"</cust_desig>";
                                        xml=xml+"<purpose>"+rs2.getString("PURPOSE")+"</purpose>";
                                        xml=xml+"<connection_type>"+rs2.getString("CONNECTION_TYPE")+"</connection_type>";
                                }
                                else 
                                {
                                    System.out.println("NonTwad data");
                                    xml=xml+"<flag>failure</flag>";
                                }
                                System.out.println("Number of records   ::::"+count);
                                count++;
                        }        
                                if(count>0)
                                    xml = xml+"<flag>success</flag>";
                               /* else if((catgory_id==null) && (count==0))
                                {
                                    System.out.println("no data found");
                                    xml=xml+"<flag>failure</flag>";
                                }*/
                                ps2.close();
                                rs2.close();
                       
                    }
                    
                catch(Exception e) 
                    {
                        xml=xml+"<flag>"+e.getMessage()+"</flag>";
                    }
                 
            }   
                  xml=xml+"</response>";
                  System.out.println("xml is : " + xml);
                  pw.write(xml);
                  pw.flush();
                  pw.close();
        }
}
