package Servlets.FAS.FAS1.ProceedingGeneration.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//import Servlets.Security.classes.UserProfile;


public class sanction_proceed_master extends HttpServlet
{
  //private static final String CONTENT_TYPE="text/xml; charset=windows-1252";
    public void init(ServletConfig config)throws ServletException
	{ 
		super.init(config);
	}
        public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException		
            {
		String CONTENT_TYPE="text/xml;charset=windows-1252";
                response.setContentType(CONTENT_TYPE);
                response.setHeader("Cache-Control","no-cache");
                System.out.println("Welcome to Sanction_Proceeed Servlet");
		String cmnd="";String xml="";
                int count=0;
                PrintWriter pw=response.getWriter();
                //PrintWriter out = response.getWriter();
                HttpSession session=null;
/*********************************************** connection establishment**************************************************************/
                Connection con=null;
                ResultSet rs=null;
                PreparedStatement ps=null;
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
                                                 
                                ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                                    Class.forName(strDriver.trim());
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
                        try
                        {
                          cmnd=request.getParameter("command");     
                          System.out.println("Command passed via the button pressed : " +cmnd);
                        }
                        catch(Exception e3)
                        {
                            e3.printStackTrace();
                        }
                    if(cmnd.equalsIgnoreCase("loadMajorType"))
                    {
                           xml=xml+"<command>loadMajorType</command>";
                            try
                            {
                                    ps=con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where STATUS='L' order by BILL_MAJOR_TYPE_CODE");
                                    rs=ps.executeQuery();
                                    while(rs.next())
                                    {
                                            xml=xml+"<option><id>"+rs.getInt("BILL_MAJOR_TYPE_CODE")+"</id><desc>"+rs.getString("BILL_MAJOR_TYPE_DESC")+"</desc></option>";
                                    }
                                    count++;
                                    if(count>0) 
                                    {
                                            xml=xml+"<flag>success</flag>";    
                                    }
                                    else
                                    {
                                            xml=xml+"<flag>nodata</flag>";
                                    }
                                    rs.close();
                                    ps.close();
                            }
                            catch(Exception e)
                            {
                                    xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                    System.out.println(e);
                            }                    
                    }
                    else if(cmnd.equalsIgnoreCase("loadMinorType"))
                    {
                        xml=xml+"<command>loadMinorType</command>";
                    try
                    {
                        int strmajor=Integer.parseInt(request.getParameter("MajorCode1"));
                        System.out.println("major code selected:"+strmajor);
                        String sql="select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and status='L' order by BILL_MINOR_TYPE_CODE";
                        ps=con.prepareStatement(sql);
                        ps.setInt(1,strmajor);
                        rs=ps.executeQuery();
                        while(rs.next())
                        {
                            xml=xml+"<option><desc>"+rs.getString("BILL_MINOR_TYPE_DESC")+"</desc><id>"+rs.getInt("BILL_MINOR_TYPE_CODE")+"</id></option>";
                            count++;
                        } // while close
                        if(count>0)
                            xml=xml+"<flag>success</flag>";
                        else
                            xml=xml+"<flag>nodata</flag>";
                                               
                            ps.close();
                            rs.close();
                    } //try close
                    catch(Exception e)
                    {
                        xml=xml+"<flag>"+e.getMessage()+"</flag>";
                        System.out.println(e);
                    }
                }
                else if(cmnd.equalsIgnoreCase("loadSubType"))
                {
                    xml=xml+"<command>loadSubType</command>";
                    try
                    {
                        int strmajor=Integer.parseInt(request.getParameter("MajorCode1"));
                        System.out.println("major code selected:"+strmajor);
                        int strminor=Integer.parseInt(request.getParameter("MinorCode1"));
                        System.out.println("minor code selected:"+strminor);
                        
                        String sql="select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES  where BILL_MAJOR_TYPE_CODE=? " +
                        "AND BILL_MINOR_TYPE_CODE=? and status='L' order by BILL_SUB_TYPE_CODE";
                        ps=con.prepareStatement(sql);
                        ps.setInt(1,strmajor);
                        ps.setInt(2,strminor);
                        rs=ps.executeQuery();
                        while(rs.next())
                        {
                            xml=xml+"<option><desc>"+rs.getString("BILL_SUB_TYPE_DESC")+"</desc><id>"+rs.getInt("BILL_SUB_TYPE_CODE")+"</id></option>";
                            count++;
                        } // while close
                        System.out.println("count"+count);
                        if(count>0)
                            xml=xml+"<flag>success</flag>";
                        else if(count==0)
                         {
                                xml=xml+"<flag>nodata</flag>";
                         }
                        ps.close();
                        rs.close();
                        } //try close
                        catch(Exception e)
                        {
                            xml=xml+"<flag>"+e.getMessage()+"</flag>";
                            System.out.println(e);
                        }
                    }
                    else if(cmnd.equalsIgnoreCase("loadpaymenttype"))
                    {
                        xml=xml+"<command>loadpaymenttype</command>";
                        try
                        {
                            int strmajor=Integer.parseInt(request.getParameter("MajorCode1"));
                            System.out.println("major code selected:"+strmajor);
                            int strminor=Integer.parseInt(request.getParameter("MinorCode1"));
                            System.out.println("minor code selected:"+strminor);
                            
                            String sql="select ADVANCE_APPLICABLE from ADVANCE_APPLICABLE_MASTER where bill_major_type_code=? and bill_minor_type_code=?";
                            ps=con.prepareStatement(sql);
                            ps.setInt(1,strmajor);
                            ps.setInt(2,strminor);
                            rs=ps.executeQuery();
                            if(rs.next())
                            {
                                xml=xml+"<advance_applicable>"+rs.getString("ADVANCE_APPLICABLE")+"</advance_applicable>";
                                count++;
                            } // while close
                            System.out.println("count"+count);
                            if(count>0)
                                xml=xml+"<flag>success</flag>";
                            else if(count==0)
                             {
                                    xml=xml+"<flag>nodata</flag>";
                             }
                            ps.close();
                            rs.close();
                            } //try close
                            catch(Exception e)
                            {
                                xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                System.out.println(e);
                            }
                    }
                    else if(cmnd.equalsIgnoreCase("loadsanctionauth"))
                    {
                        xml=xml+"<command>loadsanctionauth</command>";
                        try
                        {
                            String sql="select DISTINCT DESIGNATION_ID,DESIGNATION from HRM_MST_DESIGNATIONS order by DESIGNATION_ID";
                            ps=con.prepareStatement(sql);
                            rs=ps.executeQuery();
                            while(rs.next())
                            {
                                xml=xml+"<option><desig_id>"+rs.getInt("DESIGNATION_ID")+"</desig_id><desig_name>"+rs.getString("DESIGNATION")+"</desig_name></option>";
                                count++;
                            } // while close
                            System.out.println("count"+count);
                            if(count>0)
                                xml=xml+"<flag>success</flag>";
                            else if(count==0)
                             {
                                    xml=xml+"<flag>nodata</flag>";
                             }
                            ps.close();
                            rs.close();
                            } //try close
                            catch(Exception e)
                            {
                                xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                System.out.println(e);
                            }
                    }
                    else if(cmnd.equalsIgnoreCase("Loadsanctioned_by"))
                    {
                        xml=xml+"<command>Loadsanctioned_by</command>";
                        try
                        {
                            //int acc_unit_id=Integer.parseInt(request.getParameter("acc_unit_id"));
                            int acc_unit_off_id=Integer.parseInt(request.getParameter("acc_unit_off_id"));
                            int desig_id=Integer.parseInt(request.getParameter("desig_sel_code"));
                            String desig_name=request.getParameter("desig_sel_desc");
                            System.out.println("Office id   : "+acc_unit_off_id);
                            System.out.println("Designation Selected  : "+desig_name);
                            
                            String sql="select EMPLOYEE_ID from HRM_EMP_CURRENT_POSTING where DESIGNATION_ID=? and OFFICE_ID=? and EMPLOYEE_STATUS_ID='WKG'"; 
                            ps=con.prepareStatement(sql);
                            ps.setInt(1,desig_id);
                            ps.setInt(2,acc_unit_off_id);
                            rs=ps.executeQuery();
                            while(rs.next())
                            {
                                xml=xml+"<option><employee_id>"+rs.getInt("EMPLOYEE_ID")+"</employee_id></option>";
                                count++;
                            } // while close
                            System.out.println("count"+count);
                            if(count>0)
                                xml=xml+"<flag>success</flag>";
                            else if(count==0)
                             {
                                    xml=xml+"<flag>nodata</flag>";
                             }
                            ps.close();
                            rs.close();
                        }   //try close
                            catch(Exception e)
                            {
                                xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                System.out.println(e);
                            }
                    }
                    else if(cmnd.equalsIgnoreCase("Loadsanctiondetails"))
                    {
                        xml=xml+"<command>Loadsanctiondetails</command>";
                        try
                        {
                            //int acc_unit_id=Integer.parseInt(request.getParameter("acc_unit_id"));
                            int acc_unit_off_id=Integer.parseInt(request.getParameter("acc_unit_off_id"));
                            int emp_id=Integer.parseInt(request.getParameter("emp_code_sel"));
                            System.out.println("Office id   : "+acc_unit_off_id);
                            System.out.println("Employee id : "+emp_id);
                           
                     
                            String sql="select a.EMPLOYEE_ID,b.employee_name, c.OFFICE_SHORT_NAME,d.DESIGNATION,d.DESIGNATION_ID from " +
                            "   ( " +
                            "   select EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=? and OFFICE_ID=? and EMPLOYEE_STATUS_ID='WKG' " +
                            "   )a left outer join "+
                            "   ( select  EMPLOYEE_ID,EMPLOYEE_INITIAL||' '||EMPLOYEE_NAME as employee_name from HRM_MST_EMPLOYEES "+
                            "    )b on a.EMPLOYEE_ID=b.EMPLOYEE_ID left outer join "+
                            "   (  "+
                            "    select OFFICE_ID,OFFICE_SHORT_NAME from COM_MST_OFFICES "+
                            "    )c on a.OFFICE_ID=c.OFFICE_ID left outer join "+
                            "    ( "+
                            "    select DESIGNATION_ID,DESIGNATION from HRM_MST_DESIGNATIONS "+
                            "   )d on a.DESIGNATION_ID=d.DESIGNATION_ID "; 
                            ps=con.prepareStatement(sql);
                            ps.setInt(1,emp_id);
                            ps.setInt(2,acc_unit_off_id);
                            rs=ps.executeQuery();
                            while(rs.next())
                            {
                                xml=xml+"<empl_name>"+rs.getString("employee_name")+"</empl_name>";
                                xml=xml+"<office_name>"+rs.getString("OFFICE_SHORT_NAME")+"</office_name>";
                                xml=xml+"<desig_name>"+rs.getString("DESIGNATION")+"</desig_name>";
                                count++;
                            } // while close
                            System.out.println("count"+count);
                            if(count>0)
                                xml=xml+"<flag>success</flag>";
                            else if(count==0)
                             {
                                    xml=xml+"<flag>nodata</flag>";
                             }
                            ps.close();
                            rs.close();
                        }   //try close
                            catch(Exception e)
                            {
                                xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                System.out.println(e);
                            }
                    }
                    else if(cmnd.equalsIgnoreCase("loadInvoiceNumber"))
                    {
                        xml=xml+"<command>loadInvoiceNumber</command>";
                        try
                        {
                          
                            int strmajor=Integer.parseInt(request.getParameter("MajorCode1"));
                            System.out.println("major code selected:"+strmajor);
                            int strminor=Integer.parseInt(request.getParameter("MinorCode1"));
                            System.out.println("minor code selected:"+strminor);
                            int strsub=Integer.parseInt(request.getParameter("SubCode1"));
                            System.out.println("Sub code selected:"+strsub);
                            
                            String sql="select INVOICE_NO from INVOICE_TMP where BILL_MAJOR_CODE=? and BILL_MINOR_CODE=? and BILL_SUB_CODE=? order by INVOICE_NO "; 
                            ps=con.prepareStatement(sql);
                            ps.setInt(1,strmajor);
                            ps.setInt(2,strminor);
                            ps.setInt(3,strsub);
                            rs=ps.executeQuery();
                            while(rs.next())
                            {
                                xml=xml+"<option><invoice_no>"+rs.getInt("INVOICE_NO")+"</invoice_no></option>";
                                count++;
                            } // while close
                            System.out.println("count"+count);
                            if(count>0)
                                xml=xml+"<flag>success</flag>";
                            else if(count==0)
                             {
                                    xml=xml+"<flag>nodata</flag>";
                             }
                            ps.close();
                            rs.close();
                        }   //try close
                            catch(Exception e)
                            {
                                xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                System.out.println(e);
                            }
                    }
                    else if(cmnd.equalsIgnoreCase("loadInvoiceDetails"))
                    {
                        xml=xml+"<command>loadInvoiceDetails</command>";
                        try
                        {
                            int invoice_no=Integer.parseInt(request.getParameter("invoice_no"));
                            System.out.println("Invoice number selected :"+invoice_no);
                           String sql="    select a.INVOICE_NO,a.INVOICE_DATE,a.INVOICE_AMOUNT,a.PARTICULARS_INVOICE,a.HEAD_ACCOUNT,b.ACCOUNT_HEAD_DESC from    "   +
                                      "    (                                                                                                                    "   +
                                      "         select INVOICE_NO,to_char(INVOICE_DATE,'dd/mm/yyyy')as INVOICE_DATE,                                            "   +
                                      "         INVOICE_AMOUNT,PARTICULARS_INVOICE,HEAD_ACCOUNT from INVOICE_TMP where INVOICE_NO=?                             "   +
                                      "    )a                                                                                                                   "   +
                                      "      left outer join                                                                                                    "   +                                        
                                      "      (                                                                                                                  "   +
                                      "          select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y'                   "   +
                                      "      )b                                                                                                                 "   +
                                      "          on a.head_account=b.ACCOUNT_HEAD_CODE"; 
                            ps=con.prepareStatement(sql);
                            ps.setInt(1,invoice_no);
                            rs=ps.executeQuery();
                            while(rs.next())
                            {
                                xml=xml+"<invoice_no>"+rs.getInt("INVOICE_NO")+"</invoice_no>";
                                xml=xml+"<invoice_date>"+rs.getString("INVOICE_DATE")+"</invoice_date>";
                                xml=xml+"<invoice_amount>"+rs.getInt("INVOICE_AMOUNT")+"</invoice_amount>";
                                xml=xml+"<invoice_particulars>"+rs.getString("PARTICULARS_INVOICE")+"</invoice_particulars>";
                                xml=xml+"<invoice_headaccount>"+rs.getString("HEAD_ACCOUNT")+"</invoice_headaccount>";
                                xml=xml+"<invoice_head_desc>"+rs.getString("ACCOUNT_HEAD_DESC")+"</invoice_head_desc>";
                                count++;
                            } // while close
                            System.out.println("count"+count);
                            if(count>0)
                                xml=xml+"<flag>success</flag>";
                            else if(count==0)
                             {
                                    xml=xml+"<flag>nodata</flag>";
                             }
                            ps.close();
                            rs.close();
                        }   //try close
                            catch(Exception e)
                            {
                                xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                System.out.println(e);
                            }
                    }
                    
                 xml=xml+"</response>";
                 //out.println(xml);
                 pw.write(xml);
                 System.out.println("xml is : " + xml);
                 pw.flush();
                 pw.close();
        }     
/*************************************************************************************************************************************************************************/
 public void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException          
 {
                 String CONTENT_TYPE="text/html";
                 response.setContentType(CONTENT_TYPE);
                 response.setHeader("Cache-Control","no-cache");
                 String cmnd="";
                 int sanc_proc_no=0;String sanc_pro_date="";int Bill_majr_code=0;int Bill_minr_code=0;int Bill_sub_code=0;String Genremarks="";
                 String payee_type="";String payment_type="";int payee_code=0;int sanc_auth=0;int sanc_by=0;String sanc_date="";double tot_sanc_amt=0;
                 int cashbook_yr=0;int cashbook_mn=0;int acc_unit_id=0;int acc_office_id=0;
                 int Total_TRN_Rec=0;
                 int invoice_no=0;String invoice_date="";String invoice_particulars="";double inv_amt=0;double sanc_amt=0;
                 double deducted_amt=0;
                 double net_amt=0;int acc_head_code=0;int payable_to=0;double budget_pro=0;double budget_spent=0;double bill_amt_deducted=0;
                 double balance_amt=0;String details_remarks="";
                 Calendar c;
                 String[] sd=null; 
                 Date txtsanc_date=null;
                 PrintWriter pw=response.getWriter();
                 System.out.println("Welcome to dopost");
                 HttpSession session=request.getSession(false);
                 String update_user=(String)session.getAttribute("UserId");
                 long l=System.currentTimeMillis();
                 Timestamp ts=new Timestamp(l);
                 System.out.println("Session :"+session);
                 /*********** connection establishment****************/
                 Connection con=null;
                 ResultSet rs=null;
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
                                              
                             ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                                 Class.forName(strDriver.trim());
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
                       session=request.getSession(false);
                     try
                        {
                               cmnd =  request.getParameter("Command");     
                               System.out.println("Command passed via the button pressed : " + cmnd);
                        }
                     catch(Exception e3)
                        {
                                e3.printStackTrace();
                        }
 /*///////////////////////////////Getting the values from the JSP Page//////////////////////////////////////////////////*/
          if(cmnd.equalsIgnoreCase("Add")) 
         {
                int errcode=0;
                try{acc_unit_id=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}catch(Exception e){System.out.println("Exception arised"+e);}
                try{acc_office_id=Integer.parseInt(request.getParameter("cmbOffice_code"));}catch(Exception e){System.out.println("Exception arised"+e);}
                /********************************Getting the CachBook Year and Cash Month**************************************************************************************/
                 try
                 {
                                 sd=request.getParameter("txt_sanc_date").split("/");
                                 c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                                 java.util.Date d=c.getTime();
                                 txtsanc_date=new Date(d.getTime());
                 }
                 catch(Exception e)
                 {
                             System.out.println("Exception in date:"+e.getMessage());
                 }
                 System.out.println("txtsanc_date "+txtsanc_date);
                 
                 System.out.println("b4 getting month and year");
                 try{cashbook_yr=Integer.parseInt(sd[2]);}
                 catch(Exception e){System.out.println("exception"+e );}
                 System.out.println("txtCash_year "+cashbook_yr);
                 
                 try{cashbook_mn=Integer.parseInt(sd[1]);}
                 catch(Exception e){System.out.println("exception"+e );}
                 System.out.println("txtCash_Month_hid "+cashbook_mn);
                 
                try{Bill_majr_code=Integer.parseInt(request.getParameter("txtbill_majr_code"));}catch(Exception e){System.out.println("Exception arised"+e);}
                try{Bill_minr_code=Integer.parseInt(request.getParameter("txtbill_minr_code"));}catch(Exception e){System.out.println("Exception arised"+e);}
                try{Bill_sub_code=Integer.parseInt(request.getParameter("txtbill_sub_code"));}catch(Exception e){System.out.println("Exception arised"+e);}
                try{payee_type=request.getParameter("rad_payee_type");}catch(Exception e){System.out.println("Exception arised"+e);}
                try{payment_type=request.getParameter("txt_payment_type");}catch(Exception e){System.out.println("Exception arised"+e);}
                try{payee_code=Integer.parseInt(request.getParameter("txt_payee_code"));}catch(Exception e){System.out.println("Exception arised"+e);}
                try{sanc_auth=Integer.parseInt(request.getParameter("cmb_sanc_auth"));}catch(Exception e){System.out.println("Exception arised"+e);}
                try{sanc_by=Integer.parseInt(request.getParameter("txtsanc_by"));}catch(Exception e){System.out.println("Exception arised"+e);}
                try{sanc_pro_date=request.getParameter("txt_sancpro_date");}catch(Exception e){System.out.println("Exception arised"+e);}
                try{sanc_date=request.getParameter("txt_sanc_date");}catch(Exception e){System.out.println("Exception arised"+e);}
                try{tot_sanc_amt=Double.parseDouble(request.getParameter("txt_sanc_amt"));}catch(Exception e){System.out.println("Exception arised"+e);}
                try{Genremarks=request.getParameter("txt_GeneralRemarks");}catch(Exception e){System.out.println("Exception arised"+e);}
    
                    System.out.println("Accounting unit id :"+acc_unit_id);
                    System.out.println("Accounting unit for office id:"+acc_office_id);
                    System.out.println("Cash Book Year extracted from Sanction Date:"+cashbook_yr);
                    System.out.println("Cash Book Month extracted from Sanction Date:"+cashbook_mn);
                    System.out.println("Bill Major Code :"+Bill_majr_code);
                    System.out.println("Bill Minor Code :"+Bill_minr_code);
                    System.out.println("Bill Sub Code :"+Bill_sub_code);
                    System.out.println("Payee Type E/U/P :"+payee_type);
                    System.out.println("Payment Type Advance/Regular :"+payment_type);
                    System.out.println("Payee Code-Employee Code:"+payee_code);
                    System.out.println("Sanctioning Authority- Designation Code:"+sanc_auth);
                    System.out.println("Sanction-By Employee-Code :"+sanc_by);
                    System.out.println("Sanction Proceeding Date :"+sanc_pro_date);
                    System.out.println("Sanction Date :"+sanc_date);
                    System.out.println("Total Sanction Amount :"+tot_sanc_amt);
                    System.out.println("General Remarks  :"+Genremarks);
                       /**********************************calculating Max value of sanction proceeding Number************************************/
                       try
                       {
                               String sqlsel="select decode(max(SANCTION_PROC_NO),null,0,max(SANCTION_PROC_NO))as SANC_PRO_NO from FAS_SANC_PROCEEDING_MST";
                               ps=con.prepareStatement(sqlsel);
                               rs=ps.executeQuery();
                               System.out.println(sqlsel);
                               if(rs.next())
                               {
                                        sanc_proc_no=rs.getInt("SANC_PRO_NO");
                               }
                               sanc_proc_no=sanc_proc_no+1;
                               System.out.println("Maximum value of Sanction Proceeding Number is :"+sanc_proc_no);
                               ps.close();
                               rs.close();
                       }
                       catch(Exception ee) 
                       {
                            System.out.println("Exception in getting maximum value of sanction proceeding number :"+ee);    
                       }
                               
                   try
                   {
                           con.clearWarnings();
                           con.setAutoCommit(false);
                           String sqlins="insert into FAS_SANC_PROCEEDING_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,CASHBOOK_YEAR," +
                                    "CASHBOOK_MONTH,SANCTION_PROC_NO,SANCTION_PROC_DATE,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE," +
                                    "BILL_SUB_TYPE_CODE,PAYEE_TYPE,PAYMENT_TYPE,PAYEE_CODE,SANCTION_AUTHORITY,SANCTION_BY," +
                                    "SANCTION_DATE,TOTAL_AMOUNT,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?)";
                     ps=con.prepareStatement(sqlins);
                     ps.setInt(1,acc_unit_id);
                     ps.setInt(2,acc_office_id);
                     ps.setInt(3,cashbook_yr);
                     ps.setInt(4,cashbook_mn);
                     ps.setInt(5,sanc_proc_no);
                     ps.setString(6,sanc_pro_date);
                     ps.setInt(7,Bill_majr_code);
                     ps.setInt(8,Bill_minr_code);
                     ps.setInt(9,Bill_sub_code);
                     ps.setString(10,payee_type);
                     ps.setString(11,payment_type);
                     ps.setInt(12,payee_code);
                     ps.setInt(13,sanc_auth);
                     ps.setInt(14,sanc_by);
                     ps.setString(15,sanc_date);
                     ps.setDouble(16,tot_sanc_amt);
                     ps.setString(17,Genremarks);
                     ps.setString(18,update_user);
                     ps.setTimestamp(19,ts);          
                      errcode=ps.executeUpdate();
                      System.out.println("Error code :"+errcode);
                        ps.close();
                              if(errcode==0)
                              {         
                                        System.out.println("redirect");
                                        sendMessage(response,"The insertion into the Sanction Proceeding master table Failed ","ok");                                 
                              }
                              else
                              {
                                         System.out.println("The records inserted into the Sanction Proceeding master table scuccessfully");
                                         int SL_NO=1;
                                         String No_TRN_Rec[]=request.getParameterValues("H_Invoiceno");
                                         Total_TRN_Rec=No_TRN_Rec.length;//Integer.parseInt(No_TRN_Rec);
                                         System.out.println(" Total_TRN_Records :"+Total_TRN_Rec);
                                 
                                String Grid_H_Invoiceno[]=request.getParameterValues("H_Invoiceno");
                                String Grid_H_Invoice_Date[]=request.getParameterValues("H_Invoice_Date");
                                String Grid_H_Particulars[]=request.getParameterValues("H_Particulars");
                                String Grid_H_Invoice_amt[]=request.getParameterValues("H_Invoice_amt");
                                String Grid_H_Sanctioned_amt[]=request.getParameterValues("H_Sanctioned_amt");
                                String Grid_H_Deducted_amt[]=request.getParameterValues("H_Deducted_amt");
                                String Grid_H_Net_amt[]=request.getParameterValues("H_Net_amt");
                                String Grid_H_Acc_Head[]=request.getParameterValues("H_Acc_Head");
                                String Grid_H_Payable_To[]=request.getParameterValues("H_Payable_To");
                                String Grid_H_Budget_Provided[]=request.getParameterValues("H_Budget_Provided");
                                String Grid_H_Budget_Spent[]=request.getParameterValues("H_Budget_Spent");
                                String Grid_H_Amt_deduted_Bill[]=request.getParameterValues("H_Amt_deduted_Bill");
                                String Grid_H_Balance_amtafterBill[]=request.getParameterValues("H_Balance_amtafterBill");
                                String Grid_Remarks[]=request.getParameterValues("Remarks");
                             
                                String sql="insert into FAS_SANC_PROCEEDING_TRN(ACCOUNTING_UNIT_ID, " +
                                            " ACCOUNTING_UNIT_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,SANCTION_PROC_NO,SLNO,INVOICE_NO," +
                                            " INVOICE_DATE,INVOICE_PARTICULARS,INVOICE_AMOUNT,SANCTIONED_AMOUNT,DEDUCTED_AMOUNT,NET_AMOUNT," +
                                            " ACCOUNT_HEAD_CODE,PAYABLE_TO,BUDGET_PROVIDED,BUDGET_SOFAR_SPENT,BILL_AMOUNT_DEDUCTED," +
                                            " BALANCE_AMOUNT,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) "+
                                            " values(?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
                            ps=con.prepareStatement(sql);
                            for(int k=0;k<Total_TRN_Rec;k++) 
                            {
                                    try{invoice_no=Integer.parseInt(Grid_H_Invoiceno[k]);}catch(Exception e1){System.out.println("exception in trans "+e1);}
                                    try{invoice_date=Grid_H_Invoice_Date[k];}catch(Exception e8){System.out.println("exception in trans "+e8);}                                   
                                    try{invoice_particulars=Grid_H_Particulars[k];}catch(Exception e2){System.out.println("exception in trans "+e2);}
                                    try{inv_amt=Double.parseDouble(Grid_H_Invoice_amt[k]);}catch(Exception e3){System.out.println("exception in trans "+e3);}
                                    try{sanc_amt=Double.parseDouble(Grid_H_Sanctioned_amt[k]);}catch(Exception e4){System.out.println("exception in trans "+e4);}
                                    try{deducted_amt=Double.parseDouble(Grid_H_Deducted_amt[k]);}catch(Exception e5){System.out.println("exception in trans "+e5);}
                                    try{net_amt=Double.parseDouble(Grid_H_Net_amt[k]);}catch(Exception e6){System.out.println("exception in trans "+e6);}
                                    try{acc_head_code=Integer.parseInt(Grid_H_Acc_Head[k]);}catch(Exception e7){System.out.println("exception in trans "+e7);}
                                    try{payable_to=Integer.parseInt(Grid_H_Payable_To[k]);}catch(Exception e9){System.out.println("exception in trans "+e9);}
                                    try{budget_pro=Double.parseDouble(Grid_H_Budget_Provided[k]);}catch(Exception e9){System.out.println("exception in trans "+e9);}
                                    try{budget_spent=Double.parseDouble(Grid_H_Budget_Spent[k]);}catch(Exception e9){System.out.println("exception in trans "+e9);}
                                    try{bill_amt_deducted=Double.parseDouble(Grid_H_Amt_deduted_Bill[k]);}catch(Exception e9){System.out.println("exception in trans "+e9);}
                                    try{balance_amt=Double.parseDouble(Grid_H_Balance_amtafterBill[k]);}catch(Exception e9){System.out.println("exception in trans "+e9);}
                                    try{details_remarks=Grid_Remarks[k];}catch(Exception e9){System.out.println("exception in trans "+e9);}
                                    
                                    System.out.println("Invoice Number          :"+invoice_no);
                                    System.out.println("Invoice Date            :"+invoice_date);
                                    System.out.println("Invoice Particulars     :"+invoice_particulars);
                                    System.out.println("Invoice Amount          :"+inv_amt);                                   
                                    System.out.println("Sanctione Amount        :"+sanc_amt);
                                    System.out.println("deducted Amount         :"+deducted_amt);
                                    System.out.println("Net Amount              :"+net_amt);
                                    System.out.println("Account head code       :"+acc_head_code);
                                    System.out.println("Payable To              :"+payable_to);
                                    System.out.println("Budget Provided         :"+budget_pro);
                                    System.out.println("Budget Spent  Bill      :"+budget_spent);
                                    System.out.println("Bill deducted           :"+bill_amt_deducted);
                                    System.out.println("Balance Amount deducted :"+balance_amt);
                                    System.out.println("Details Remarks         :"+details_remarks);
                                    
                                ps.setInt(1,acc_unit_id);
                                ps.setInt(2,acc_office_id);
                                ps.setInt(3,cashbook_yr);
                                ps.setInt(4,cashbook_mn);
                                ps.setInt(5,sanc_proc_no);
                                ps.setInt(6,SL_NO);
                                ps.setInt(7,invoice_no);
                                ps.setString(8,invoice_date);
                                ps.setString(9,invoice_particulars);
                                ps.setDouble(10,inv_amt);
                                ps.setDouble(11,sanc_amt);
                                ps.setDouble(12,deducted_amt);
                                ps.setDouble(13,net_amt);
                                ps.setInt(14,acc_head_code);
                                ps.setInt(15,payable_to);
                                ps.setDouble(16,budget_pro);
                                ps.setDouble(17,budget_spent);
                                ps.setDouble(18,bill_amt_deducted);
                                ps.setDouble(19,balance_amt);
                                ps.setString(20,details_remarks);
                                ps.setString(21,update_user);
                                ps.setTimestamp(22,ts);
                                SL_NO++;
                                ps.executeUpdate(); 
                            }
                            ps.close();
                            System.out.println("b4 commit");
                            con.commit();
                            sendMessage(response,"The General Sanction Proceeding Number "+sanc_proc_no+" is created Successfully \n ","ok");
                            //sendMessage(response,"The Records are inserted into both table Successfully ","ok");
                      }
               }
               catch(Exception e) 
                      {
                          try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
                          sendMessage(response,"The Insertion of records into the table Failed ","ok");
                          System.out.println("Exception occur due to "+e);
                      }
                      finally
                      {
                          System.out.println("done");
                          try{con.setAutoCommit(true);  }catch(SQLException sqle){System.out.println("Exception arised :"+sqle);}
                      }
                 }
                 
                           pw.flush();
                           pw.close();
 }//DoPost method close

private void sendMessage(HttpServletResponse response,String msg,String bType)
{
        try
        {
             String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
             response.sendRedirect(url);
        }
        catch(IOException e)
        {
                System.out.println("Exception arised :"+e);
        }
}
}  
