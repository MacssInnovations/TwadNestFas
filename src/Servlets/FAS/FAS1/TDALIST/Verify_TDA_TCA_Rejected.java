package Servlets.FAS.FAS1.TDALIST;


import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;



public class Verify_TDA_TCA_Rejected extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    
    public void doPost(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
        
        /**
         * Variables Declaration
         */
        String strCommand="";
        Connection con=null;
        
        
        /**
         * Session Checking 
         */
        HttpSession session=request.getSession(false);
        try
        {
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");
                return;
            }
            System.out.println(session);
                
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
                               ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                               Class.forName(strDriver.trim());
                               con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
              }
              catch(Exception e)
                  {
                     System.out.println("Exception in opening connection :"+e);
                  }
                  
                  
        /** Get Command Parameter */          
        try {        
            strCommand=request.getParameter("Command");            
        }        
        catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }
        
        
        
        if(strCommand.equalsIgnoreCase("Add")) 
        {
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);            
            PreparedStatement ps=null;            
            Calendar c;
            int cmbAcc_UnitCode=0;
            int cmbOffice_code=0;
            int txtVoucher_No=0;            
            int txtCash_year=0;
            int txtCash_Month_hid=0;
            
            /** Get Accounting Unit ID */
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
            
            /** Get Accounting for Office ID */
            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbOffice_code "+cmbOffice_code);
            
            /** Get Cashbook Year */
            try{txtCash_year=Integer.parseInt(request.getParameter("txtCB_Year"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtCash_year "+txtCash_year);
                      
            /** Get Cashbook Month */
            try{txtCash_Month_hid=Integer.parseInt(request.getParameter("txtCB_Month"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
            
            
         try
         {
         
             con.clearWarnings();
             con.setAutoCommit(false);
                              
            String sql_update="" +
            "update                             \n" + 
            "   FAS_TDA_TCA_RAISED_TRN         \n" + 
            "set VERIFIED = 'Y',                \n" + 
            "    VERIFY_DATE = (select now() ) , \n" + 
            "    VERIFIED_AUTHORITY =?          \n" + 
            "where                              \n" + 
            "    ACCOUNTING_UNIT_ID = ?  and    \n" + 
            "    ACCOUNTING_FOR_OFFICE_ID = ? and       \n" + 
            "    CASHBOOK_YEAR = ? and                  \n" + 
            "    CASHBOOK_MONTH = ? and                 \n" + 
            "    voucher_no=? ";  
                              
                              
             ps=con.prepareStatement(sql_update);
             
             String Voucher_no[]=request.getParameterValues("Voucher_no");
             String Voucher_date[]=request.getParameterValues("Voucher_date");
             int txtusrid=0;
             UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
             txtusrid= empProfile.getEmployeeId();       
             
             System.out.println("user id::"+txtusrid);
             
             
             for(int k=0;k<Voucher_no.length;k++)                           
             {
                System.out.println("K---------------------------------<> "+k);
                try{
                	System.out.println("Enter into try");
                	txtVoucher_No=Integer.parseInt(Voucher_no[k]);
                System.out.println("======================================================================================>"+txtVoucher_No);
                }catch(Exception e){System.out.println("exception in trans "+e);}
                ps.setInt(1,txtusrid);
                ps.setInt(2,cmbAcc_UnitCode);
                ps.setInt(3,cmbOffice_code);     
                ps.setInt(4,txtCash_year);
                ps.setInt(5,txtCash_Month_hid);                               
                ps.setInt(6,txtVoucher_No);                      
                ps.executeUpdate();
                txtVoucher_No=0;                   
             }        
             
             con.commit();
             sendMessage(response,"The TDA_TCA Verified Successfully ","ok");
             
           } 
            catch(Exception e) 
            {
                try{con.rollback();}catch(SQLException sqle){System.out.println("Excep"+sqle);}
                sendMessage(response,"TDA_TCA Verification Failed ","ok");
                System.out.println("Exception occur due to "+e);
            }
            finally
            {                
                try{con.setAutoCommit(true);  }catch(SQLException sqle){System.out.println("Excep"+sqle);}
            }
        }
    }
    
       
    
    
    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {    
    System.out.println("list verifyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
        /**
         * Variables Declaration
         */
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        int empid=0;
        int txtCash_year=0;
        int txtCash_Month_hid=0;
        int cmbAcc_UnitCode=0;
        int cmbOffice_code=0;
              
        
        /**
         * Session Checking 
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
                              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                              Class.forName(strDriver.trim());
                              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
             }
             catch(Exception e)
                 {
                    System.out.println("Exception in opening connection :"+e);                   

                 }
                 
                 
        
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control","no-cache");
        PrintWriter out = response.getWriter();
        String strCommand="";
        Date txtFrom_date=null,txtTo_date=null;
        try 
        {
            strCommand=request.getParameter("Command");           
        }        
        catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }
           
       
             String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             Calendar c;
             String xml="",sql="";
             
             
        
        
        if(strCommand.equalsIgnoreCase("searchByDate"))  
        {
        
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            
            
            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
                    String[] sd=request.getParameter("txtFrom_date").split("/");
                     c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                     java.util.Date d=c.getTime();
                     txtFrom_date=new Date(d.getTime());
                     System.out.println("from_date "+txtFrom_date);
                     
                     sd=request.getParameter("txtTo_date").split("/");
                     c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                     d=c.getTime();
                     txtTo_date=new Date(d.getTime());
                     System.out.println("txtTo_date "+txtTo_date);
                     xml="<response><command>searchByDate</command>"; 
                     
                         sql="SELECT mst.VOUCHER_NO,\n" + 
                         "  mst.VOUCHER_DATE,\n" + 
                         "  mst.REASON_FOR_NON_ACCEPT,\n" + 
                         "  mst.TOTAL_AMOUNT,\n" + 
                         "  mst.PARTICULARS,\n" + 
                         "  mst.ACCOUNTING_UNIT_ID,\n" + 
                         "  (SELECT unit.ACCOUNTING_UNIT_NAME\n" + 
                         "  FROM fas_mst_acct_units unit\n" + 
                         "  WHERE unit.ACCOUNTING_UNIT_ID=mst.ACCOUNTING_UNIT_ID\n" + 
                         "  ) AS unit_name\n" + 
                         " FROM fas_tda_tca_raised_mst mst\n" + 
                         " WHERE mst.TRF_ACCOUNTING_UNIT_ID= ?\n" + 
                         " AND mst.ACCEPTANCE_STATUS       ='N'\n" + 
                       //  " AND mst.REASON_FOR_NON_ACCEPT   ='1'\n" + 
                         " AND mst.VOUCHER_DATE BETWEEN ? AND ?";
                     
                     
                        System.out.println("SQL::::"+sql);
                        try
                        {
                                        int count=0;
                                        ps=con.prepareStatement(sql);
                                        ps.setInt(1,cmbAcc_UnitCode);
                                        ps.setDate(2,txtFrom_date);
                                        ps.setDate(3,txtTo_date);
                                       
                                       rs=ps.executeQuery();
                                        while(rs.next())
                                        {
                                                    xml=xml+"<leng>";
                                                    xml=xml+"<vou_no>"+rs.getInt("voucher_no")+"</vou_no>";
                                                    xml=xml+"<vou_date>"+rs.getString("vou_date")+"</vou_date>";
                                                    xml=xml+"<reason>"+rs.getString("REASON_FOR_NON_ACCEPT")+"</reason>";
                                                    xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";
                                                    xml=xml+"<parti>"+rs.getString("PARTICULARS") +"</parti>";
                                                    xml=xml+"<unitname>"+rs.getString("unit_name") +"</unitname>";
                                                    xml=xml+"</leng>";
                                                    count++;
                                        }
                                        if(count==0) 
                                        {
                                                    System.out.println("inside count==0");
                                                    xml="<response><command>searchByDate</command><flag>failure</flag>";
                                        }
                        }
                        catch(SQLException sqle)
                        {
                                        sqle.printStackTrace();
                                        System.out.println("error while fetching data " + sqle);
                                        xml="<response><command>searchByDate</command><flag>failure</flag>";
                        }
            
        }
             
    
            xml=xml+"</response>";
            System.out.println(xml);
            out.println(xml);
        
        
        
      
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

        
        
        
