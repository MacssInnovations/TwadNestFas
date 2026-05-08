package Servlets.FAS.FAS1.CivilBills.servlets;

//import Servlets.Security.classes.UserProfile;

import Servlets.Security.classes.UserProfile;

import java.io.*;
import java.sql.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.http.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class VerifyCivilAgreement extends HttpServlet
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
                System.out.println("Welcome to VerifyCivilAgreement Servlet");
		String cmnd="";
		String xml="";
                String user_id;
                user_id = "";
                
                int count=0;;
                String update_user="";
                HttpSession session=null;
                Timestamp ts=null;//int recflag=0;
                
                int acc_unit_id=0;int acc_off_id=0;Calendar c;
                Date txtCivilAgree_date=null;
                int txtCash_year=0;
                int txtCash_Month_hid=0;
                
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
                    
                   
                    if(cmnd.equalsIgnoreCase("loadUnverifiedCA")) 
                    {
                        xml=xml+"<command>loadUnverifiedCA</command>";
                        try
                            {             
                                acc_unit_id=Integer.parseInt(request.getParameter("acc_unit_code1")); 
                                acc_off_id=Integer.parseInt(request.getParameter("acc_off_code1")); 
                                System.out.println("Account unit id.... "+acc_unit_id);
                                System.out.println("Account unit Office id.... "+acc_off_id);
                                
                                 /** Get Date */ 
                                  String[] sd=request.getParameter("CivilAgree_date1").split("/");
                                  c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                                  java.util.Date d=c.getTime();
                                  txtCivilAgree_date=new Date(d.getTime());
                                  System.out.println("txtCivilAgree_date "+txtCivilAgree_date);
                                 
                                 /** Find Cashbook Month and Year */
                                 System.out.println("b4 getting month and year");
                                 try{
                                   txtCash_year=Integer.parseInt(sd[2]);
                                 }
                                 catch(Exception e)
                                 {
                                  System.out.println("exception"+e );
                                 }
                                 
                                 System.out.println("txtCash_year "+txtCash_year);
                                 
                                 try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
                                 catch(Exception e){System.out.println("exception"+e );}
                                 System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
                                
                                
                                String sqlload="select AGREEMENT_NO,to_char(AGREEMENT_DATE,'DD/MM/YYYY')as AGREEMENT_DATE,NAME_OF_WORK,SUB_LEDGER_TYPE_CODE,       " +
                                "               trim(to_char(VALUE_OF_WORK,'9999999999999999.99'))as VALUE_OF_WORK,         " + 
                                "               WORK_OR_SUPPLY_ORDER_NO,to_char(WORK_OR_SUPPLY_ORDER_DATE,'DD/MM/YYYY')as WORK_OR_SUPPLY_ORDER_DATE,REMARKS from fas_civil_agreement " +
                                "               where                                                                       " + 
                                "               VERIFIED='N' and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?        " +
                                "               AND CASHBOOK_YEAR=? and CASHBOOK_MONTH=?  order by AGREEMENT_NO  "; 
                                ps2 = con.prepareStatement(sqlload);
                                ps2.setInt(1,acc_unit_id);
                                ps2.setInt(2,acc_off_id);
                                ps2.setInt(3,txtCash_year);
                                ps2.setInt(4,txtCash_Month_hid);
                                rs2=ps2.executeQuery();
                                System.out.println("exeequting the query");
                                while(rs2.next())
                                {
                                    xml=xml+"<AgreementNo>"+rs2.getInt("AGREEMENT_NO")+"</AgreementNo>";
                                    xml=xml+"<AgreementDate>"+rs2.getString("AGREEMENT_DATE")+"</AgreementDate>";
                                    xml=xml+"<NameofWork>"+rs2.getString("NAME_OF_WORK")+"</NameofWork>";
                                    xml=xml+"<SubLedgertypeCode>"+rs2.getInt("SUB_LEDGER_TYPE_CODE")+"</SubLedgertypeCode>";
                                    xml=xml+"<ValueofWork>"+rs2.getString("VALUE_OF_WORK")+"</ValueofWork>";
                                    xml=xml+"<WorkOrderNo>"+rs2.getString("WORK_OR_SUPPLY_ORDER_NO")+"</WorkOrderNo>";
                                    xml=xml+"<WorkOrderDate>"+rs2.getString("WORK_OR_SUPPLY_ORDER_DATE")+"</WorkOrderDate>";
                                    xml=xml+"<remarks>"+rs2.getString("REMARKS")+"</remarks>";
                                    count++;
                                    System.out.println("Number of Records ::::::"+count);
                                    System.out.println("agreement no:::::"+rs2.getInt("AGREEMENT_NO"));
                                    System.out.println("agreement Date:::::"+rs2.getString("AGREEMENT_DATE"));
                                    System.out.println("Name of Work:::::"+rs2.getString("NAME_OF_WORK"));
                                    System.out.println("SubLedgertypeCode:::::"+rs2.getInt("SUB_LEDGER_TYPE_CODE"));
                                    System.out.println("ValueofWork:::::"+rs2.getString("VALUE_OF_WORK"));
                                    System.out.println("WorkOrderNo:::::"+rs2.getString("WORK_OR_SUPPLY_ORDER_NO"));
                                    System.out.println("WorkOrderDate:::::"+rs2.getString("WORK_OR_SUPPLY_ORDER_DATE"));
                                    System.out.println("remarks:::::"+rs2.getString("REMARKS"));
                                }
                                
                                if(count>0)
                                {
                                    xml=xml+"<flag>success</flag>"; 
                                }
                                else if(count==0)
                                {
                                    xml=xml+"<flag>nodata</flag>";    
                                }
                                 ps2.close();
                                 rs2.close();
                             } //try close
                              catch(Exception e)
                              {
                                                xml=xml+"<flag>failure</flag>";
                                                System.out.println(e);
                               }
                    }
                     xml=xml+"</response>";
                          System.out.println("xml is : " + xml);
                          pw.write(xml);
                          pw.flush();
                          pw.close();
        }//doGet method end.....
        
         /**
          * POST Method 
          * @param request
          * @param response
          * @throws ServletException
          * @throws IOException
          */
         public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
         {
            
             /**
              *  Variables Declaration 
              */
             String strCommand="";
             Connection con=null;
             int empid=0;
             
              /**
               *  Session Checking 
               */
              try
              {
                  HttpSession session=request.getSession(false);
                  if(session==null)
                  {
                      System.out.println(request.getContextPath()+"/index.jsp");
                      response.sendRedirect(request.getContextPath()+"/index.jsp");
                      return;
                  }
                  System.out.println(session);
              
                  UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                  System.out.println("user id::"+empProfile.getEmployeeId());
                  empid=empProfile.getEmployeeId();
              }catch(Exception e)
              {
              System.out.println("Redirect Error :"+e);
              }
               
             
                 
             /**
              * Database Connection 
              */
             try {
                                    ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                                    String ConnectionString="";

                                    String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                                    String strdsn=rs1.getString("Config.DSN");
                                    String strhostname=rs1.getString("Config.HOST_NAME");
                                    String strportno=rs1.getString("Config.PORT_NUMBER");
                                    String strsid=rs1.getString("Config.SID");
                                    String strdbusername=rs1.getString("Config.USER_NAME");
                                    String strdbpassword=rs1.getString("Config.PASSWORD");
                                    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                                    Class.forName(strDriver.trim());
                                    con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                   }
                   catch(Exception e)
                       {
                          System.out.println("Exception in opening connection :"+e);
                       }
                       
             /**
              * Getting Command Parameter Value 
              */
             try {
             
                 strCommand=request.getParameter("Command");
                 System.out.println("assign..here command..."+strCommand);
             }
             catch(Exception e) 
             {
                 System.out.println("Exception in assigning..."+e);
             }
             
             
             /**
              * If Command is 'ADD' 
              */
             if(strCommand.equalsIgnoreCase("Add")) 
             {
                 /** Set Servlet Content Type */
                 String CONTENT_TYPE = "text/html; charset=windows-1252";
                 response.setContentType(CONTENT_TYPE);
                 int ched=0,unched=0;
                 /** Variable Declaration */
                 PreparedStatement ps=null;
                 int up=0;
                 Calendar c;
                 int cmbAcc_UnitCode=0,cmbOffice_code=0;
                 int txtAgreement_No=0;
                 Date txtCivilAgree_Date=null;
                 int txtCash_year=0;
                 int txtCash_Month_hid=0;
                 
                 /** Get Account Unit ID */ 
                 try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
                 catch(NumberFormatException e){System.out.println("exception"+e );}
                 System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
                 
                 /** Get Account office code */ 
                 try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
                 catch(NumberFormatException e){System.out.println("exception"+e );}
                 System.out.println("cmbOffice_code "+cmbOffice_code);
                 
                 /** Get Date */ 
                 String[] sd=request.getParameter("txtCivilAgreeDate").split("/");
                 c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                 java.util.Date d=c.getTime();
                 txtCivilAgree_Date=new Date(d.getTime());
                 System.out.println("txtCivilAgreeDate "+txtCivilAgree_Date);
                 
                 /** Find Cashbook Month and Year */
                 System.out.println("b4 getting month and year");
                 try{
                   txtCash_year=Integer.parseInt(sd[2]);
                 }
                 catch(Exception e)
                 {
                  System.out.println("exception"+e );
                 }
                 
                 System.out.println("txtCash_year "+txtCash_year);
                 
                 try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
                 catch(Exception e){System.out.println("exception"+e );}
                 System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
                 
                
               /** Update Remittance Table */  
               try
               {
                  con.clearWarnings();
                  con.setAutoCommit(false);
                  String sql_update="update fas_civil_agreement set VERIFIED='Y',VERIFIED_AUTHORITY=? where  ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and AGREEMENT_NO=?";
                  
                  ps=con.prepareStatement(sql_update);
                 
                  
                  System.out.println("before getting grid values");
                  
                 /**
                  *   Getting Grid Values such as Challan Number and Challan Date 
                  */
                  String txtAgreement_no[]=request.getParameterValues("Agreement_No");
                  
                  String verify_select[]=request.getParameterValues("verify_select");
                  
                  String verify_select_status[]=request.getParameterValues("verify_select_status");
                  
                  int tot=txtAgreement_no.length;
                  System.out.println("agreement no length --->"+txtAgreement_no.length); 
                 // int lenchecked=verify_select.length;
                 // System.out.println("after declaraing array  length verify_select 00 "+verify_select.length+"  --- ");
                // int ff=verify_select_status.length;
                // System.out.println("ff "+ff);
                   for(int k=0;k<txtAgreement_no.length;k++) 
                   {   
                   // System.out.println("Loop ::"+k);
                 //   System.out.println("check box value....."+verify_select[k]);
                  //  System.out.println(""+k+"" + verify_select_status[k]);
                    
                    
                     /** Check Whether Checkbox is checked or not */ 
                    // if((verify_select_status[k].equals("CHECKED"))==true)
                    if((verify_select_status[k].equals("CHECKED")))
                     {
                        
                        //System.out.println("after check box checking ");
                        
                        
                      /** Get Challan Number */ 
                      try
                        {
                          txtAgreement_No=Integer.parseInt(txtAgreement_no[k]);
                        }
                      catch(Exception e)
                       {
                          System.out.println("exception in trans "+e);
                       }
                       
                      /* Get Challan Date 
                      if(!challan_date[k].equalsIgnoreCase(""))
                      {
                      sd=challan_date[k].split("/");
                      c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                      d=c.getTime();
                      txtChallan_Date=new Date(d.getTime());
                      }
                      */
                      
                     System.out.println("accounitng unit id-- "+cmbAcc_UnitCode); 
                     System.out.println("office id --"+cmbOffice_code); 
                     System.out.println("Agreement NO-- "+txtAgreement_No);
                    // System.out.println("challan date "+txtChallan_Date);
                      
                      
                     /** For Changing VERIFIED = Y in fas_civil_agreement table */  
                     ps.setInt(1,empid);
                     ps.setInt(2,cmbAcc_UnitCode);
                     ps.setInt(3,cmbOffice_code);  
                     ps.setInt(4,txtCash_year);
                     ps.setInt(5,txtCash_Month_hid);
                     ps.setInt(6,txtAgreement_No);                
                     
                     /** Execute the Query */ 
                   up=  ps.executeUpdate();
                     
                    // System.out.println("last ");
                    
                     ched++;
                   }else{
                	  // System.out.println("count of unched ");
                	   unched++;  
                   }
                  }
                   
                   int fina=tot-ched;
                  // System.out.println(" fina "+fina+" tot"+tot+" ched "+ched +" unched "+unched);
                   if(fina==unched){
                  // if(up>0){
                	  // System.out.println("inside if ");
                	   con.commit();
                       sendMessage(response,"The Civil Agreement is verified Successfully ","ok");
                   }else{
                	  // System.out.println("inside else  ");
                	   con.rollback();
                	   sendMessage(response,"The Civil Agreement is verified failed ","ok");
                   }
                 } 
                 catch(Exception e) 
                 {
                	// System.out.println("inside catch---");
                     try{
                    	 con.rollback();
                    }catch(SQLException sqle){System.out.println("Excep"+sqle);}
                     sendMessage(response,"The Civil Agreement is verified failed ","ok");
                     System.out.println("Exception occur due to "+e);
                 }
                /* finally
                 {
                     System.out.println("done here");
                     try{
                    	 con.setAutoCommit(true);  
                    	 }
                     catch(SQLException sqle)
                     {System.out.println("Excep"+sqle);}
                 }*/
             }
         }
    private void sendMessage(HttpServletResponse response,String msg,String bType)
    {
        try
        {
            String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
            response.sendRedirect(url);
        }
        catch(IOException e)
        {
        System.out.println("Excep"+e);
        }
    }

}
