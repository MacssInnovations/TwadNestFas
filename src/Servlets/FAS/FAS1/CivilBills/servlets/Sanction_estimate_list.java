package Servlets.FAS.FAS1.CivilBills.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.*;
import java.sql.*;
import java.util.ResourceBundle;

import javax.servlet.http.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class Sanction_estimate_list extends HttpServlet
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
                System.out.println("Welcome to Sanction_estimate_list Servlet");
		String cmnd="";
		String xml="";
                String user_id;
                user_id = "";
                String emp_name="";
                int emp_id=0;
                PrintWriter pw=response.getWriter();
                HttpSession session=null;
                /*********** connection establishment****************/
                Connection con=null;
                ResultSet rs2,rs3=null;
                PreparedStatement ps2=null;
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
                      //session=request.getSession(false);
                      //UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                      //int eid=empProfile.getEmployeeId();
                      //System.out.println("employee id:"+eid);
   
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
                 
                if(cmnd.equalsIgnoreCase("LoadGrid"))
                {
                        System.out.println("\n*************\nLoad Sanction Estimate Details \n**************\n");
                        int acc_unit_id=Integer.parseInt(request.getParameter("acc_unit_id"));
                        int acc_unit_off_id=Integer.parseInt(request.getParameter("acc_unit_officeid"));
                        String finyear=request.getParameter("finyear");
                    
                        //System.out.println("finyear  Selected :"+finyear);
                        
                        xml=xml+"<command>LoadGrid</command>";
                        int count=0;
                         try
                        {             
                                    String sqlload1="Select Sanction_Estimate_No,to_char(Sanction_Estimate_Date,'dd-mm-yyyy')as Sanction_Estimate_Date,Account_Head_Code,Total_Estimate_Amount " + 
                                    " from Fas_Sanction_Estimate_Mst Where Accounting_Unit_Id=? " + 
                                    " and Accounting_Unit_Office_Id=? " + 
                                    " And Financial_Year=? order by Sanction_Estimate_No ";
                                    ps2 = con.prepareStatement(sqlload1);
                                    ps2.setInt(1,acc_unit_id);
                                    ps2.setInt(2,acc_unit_off_id);
                                    ps2.setString(3,finyear);
                                    //System.out.println("select query *****"+sqlload1);
                                    rs2=ps2.executeQuery();
                                    while(rs2.next())
                                    {
                                        xml=xml+"<leng>";
                                        xml=xml+"<sanc_est_no>"+rs2.getInt("Sanction_Estimate_No")+"</sanc_est_no>";
                                        xml=xml+"<sanc_est_date>"+rs2.getString("Sanction_Estimate_Date")+"</sanc_est_date>";                                       
                                        xml=xml+"<acc_head_code>"+rs2.getInt("Account_Head_Code")+"</acc_head_code>";
                                        xml=xml+"<tot_amt>"+rs2.getFloat("Total_Estimate_Amount")+"</tot_amt>";
                                        xml=xml+"</leng>";
                                         count++;
                                    }
                                   
                                    if(count>0)
                                    xml=xml+"<flag>success</flag>";
                                    else
                                    xml=xml+"<flag>nodata</flag>";
                                    
                         } //try close
                          catch(Exception e)
                          {
                                            xml=xml+"<flag>failure</flag>";
                                            //xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                            System.out.println(e);
                           }
                        
                        
                }
                
                else if(cmnd.equalsIgnoreCase("LoadpopupGrid"))
                {
                        System.out.println("\n*************\nLoad Sanction Estimate Transaction List Details \n**************\n");
                        xml=xml+"<command>LoadpopupGrid</command>";
                        int count=0;
                        int sanc_est_no=Integer.parseInt(request.getParameter("empl_id"));
                         try
                        {             
                                    String sqlload2="select ASSET_CODE,ASSET_CLASS_CODE,ESTIMATE_AMOUNT,Particulars,SERIAL_NO from FAS_SANCTION_ESTIMATE_TRN where SANCTION_ESTIMATE_NO=? ";
                                    ps2 = con.prepareStatement(sqlload2);
                                    ps2.setInt(1,sanc_est_no);
                                    rs3=ps2.executeQuery();
                                    while(rs3.next())
                                    {
                                        xml=xml+"<length>";
                                        xml=xml+"<SL_No>"+rs3.getInt("SERIAL_NO")+"</SL_No>";
                                        xml=xml+"<assetcode>"+rs3.getInt("ASSET_CODE")+"</assetcode>";                                       
                                        xml=xml+"<assetclasscode>"+rs3.getString("ASSET_CLASS_CODE")+"</assetclasscode>";
                                        xml=xml+"<est_amt>"+rs3.getFloat("ESTIMATE_AMOUNT")+"</est_amt>";
                                        xml=xml+"<particulars>"+rs3.getString("Particulars")+"</particulars>";  
                                        xml=xml+"</length>";
                                        
                                    }
                                    count++;
                                    if(count>0)
                                    xml=xml+"<flag>success</flag>";
                                    else
                                    xml=xml+"<flag>nodata</flag>";
                                    
                         } //try close
                          catch(Exception e)
                          {
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