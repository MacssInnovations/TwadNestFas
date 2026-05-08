package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class FreezeGL_PB_servlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {


        /**
         * Database Connection
         */

        Connection con = null;
        Statement statement = null;
        ResultSet rs2=null,rs3=null;
        try {
            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
            try {
                statement = con.createStatement();
                con.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing con:" + e);
        }


        /**
         * Content Type Setting
         */

        response.setContentType(CONTENT_TYPE);


        /**
         * Session Checking
         */
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        /**
         * Variables Declaration
         */

        int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode = 0, cmbOffice_code = 0,pbstatus_count=0,first_count=0;
      
        /**
         * Get Parameters
         */

        /** Get Accounting Unit Id */
        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

        /** Get Cash Book Year */
         String year[]=request.getParameter("txtCB_Year").split("-");
      //  txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));

        /** Get Cash Book Month */
        txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        System.out.println("year..." + year[0]);
        System.out.println("year..." + year[1]);
        System.out.println("Month..." + txtCB_Month);

       
        /** Get User ID */
        String userid = (String)session.getAttribute("UserId");

        /** Get Updated Time */
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        int data_12=0;

        try {
            con.setAutoCommit(false);
            PreparedStatement ps = null,ps1=null;
            String msg = " ";

                if(txtCB_Month==1) 
                {
                        try
                        {
                            ps = con.prepareStatement("select ACCOUNTING_UNIT_ID from fas_gl_pbstatus where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR="+year[0]+" and CASHBOOK_MONTH=12 ");
                            ps.setInt(1, cmbAcc_UnitCode);
                           
                                rs2 = ps.executeQuery();
                                if(rs2.next()) {
                                System.out.println("inside ifffffffffff:::::");
                                    ps1 =con.prepareStatement("update fas_gl_pbstatus set GL_PB_FREEZE_STATUS='Y',GL_PB_FREEZE_DATE   =?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR="+year[0]);
                                    ps1.setTimestamp(1, ts);
                                    ps1.setString(2, userid);
                                    ps1.setTimestamp(3, ts);
                                    ps1.setInt(4, cmbAcc_UnitCode);
                                  //  ps1.setInt(5, year[0]);
                                    ps1.executeUpdate();
                                    ps1.close();
                                   
                                    data_12++;
                                }
                                if(data_12==0) {
                                    String msg_one ="GL PB Updation is not Done Upto Previous Month";
                                    msg = msg_one + "<br><br>";
                                    sendMessage(response, msg, "ok");
                                }
                                else 
                                {
                                    con.commit();
                                    con.setAutoCommit(true);
                                    String msg_one ="GL PB Freezed Successfully";
                                    msg = msg_one + "<br><br>";
                                    sendMessage(response, msg, "ok");
                                }
                         }
                        catch(Exception e2) 
                        {
                            System.out.println("exc in::::"+e2);
                        }
                }
                else
                {
                ps = con.prepareStatement("SELECT ACCOUNTING_UNIT_ID,\n" + 
                "  CASHBOOK_YEAR,\n" + 
                "  CASHBOOK_MONTH,\n" + 
                "  GL_PB_STATUS,\n" + 
                "  TO_CHAR(GL_PB_DATE,'DD/MM/YYYY')AS GL_PB_DATE,\n" + 
                "  GL_PB_FREEZE_STATUS,\n" + 
                "  TO_CHAR(GL_PB_FREEZE_DATE,'DD/MM/YYYY')AS GL_PB_FREEZE_DATE\n" + 
                " From Fas_Gl_Pbstatus\n" + 
                " Where Accounting_Unit_Id =" +cmbAcc_UnitCode+" AND CASHBOOK_YEAR        =" +year[0]+ 
                " AND GL_PB_FREEZE_STATUS IS NULL\n" + 
                " UNION ALL\n" + 
                " SELECT ACCOUNTING_UNIT_ID,\n" + 
                "  CASHBOOK_YEAR,\n" + 
                "  CASHBOOK_MONTH,\n" + 
                "  GL_PB_STATUS,\n" + 
                "  TO_CHAR(GL_PB_DATE,'DD/MM/YYYY')AS GL_PB_DATE,\n" + 
                "  GL_PB_FREEZE_STATUS,\n" + 
                "  TO_CHAR(GL_PB_FREEZE_DATE,'DD/MM/YYYY')AS GL_PB_FREEZE_DATE\n" + 
                " From Fas_Gl_Pbstatus\n" + 
                " Where Accounting_Unit_Id ="+cmbAcc_UnitCode+" AND CASHBOOK_YEAR        ="+year[1]+
                " AND GL_PB_FREEZE_STATUS IS NULL\n" + 
                " ORDER BY Cashbook_Year,\n" + 
                "  Cashbook_Month");
//                ps.setInt(1, cmbAcc_UnitCode);
//                ps.setInt(2, txtCB_Year);
//                ps.setInt(3, txtCB_Month);
                rs2 = ps.executeQuery();
                while (rs2.next()) 
                {
                            System.out.println("if first_count");
                        first_count++;
                       try{
                        System.out.println("year:::::"+rs2.getInt("CASHBOOK_YEAR"));
                           System.out.println("month:::::"+rs2.getInt("CASHBOOK_MONTH"));
                           //    pbstatus_count++;
                               ps1 =con.prepareStatement("update fas_gl_pbstatus set GL_PB_FREEZE_STATUS='Y',GL_PB_FREEZE_DATE   =?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
                               ps1.setTimestamp(1, ts);
                               ps1.setString(2, userid);
                               ps1.setTimestamp(3, ts);
                               ps1.setInt(4, rs2.getInt("ACCOUNTING_UNIT_ID"));
                               ps1.setInt(5, rs2.getInt("CASHBOOK_YEAR"));
                               ps1.setInt(6, rs2.getInt("CASHBOOK_MONTH"));
                               ps1.executeUpdate();
                               ps1.close();
                          
                       }
                       catch(Exception er) {
                           System.out.println("exception in fetching records:::"+er);
                       }
                   
                }
                if(first_count==0) {
                    con.rollback();
                    String msg_one ="General Ledger PB Status Already Froze";
                    msg = msg_one + "<br><br>";
                    sendMessage(response, msg, "ok");
                }
                else{
                       
                           con.commit();
                           con.setAutoCommit(true);
                           System.out.println("..8");
        
                           String msg_one ="General Ledger PB Status is Freezed Successfully.......";
                           msg = msg_one + "<br><br>";
                           sendMessage(response, msg, "ok");     
                       
                }
            }
        } 
        catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                System.out.println("exception in rollback" + e1);
            }
            System.out.println("Exception in Pb balance ::::::***::::" + e);
            String msg ="Error in General Ledger PB Status.......";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");

        }
       

    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
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
                    
        }catch(Exception e)
        {
              System.out.println("Redirect Error :"+e);
        }
          
        
        /**
        * Variables Declaration
        */
        Connection con=null;
        ResultSet rs=null;
        ResultSet rs2=null;
        ResultSet rs3=null;
        
        PreparedStatement ps=null;
        PreparedStatement ps2=null;
        PreparedStatement ps3=null;
        
        PreparedStatement ps4=null;
        
        
        String strType = "";
        String xml="<response>";
        
        /**
        * Database Connection
        */
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
           ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection           Class.forName(strDriver.trim());
           con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
        }
        catch(Exception e)
        {
           System.out.println("Exception in opening connection :"+e);
        
        }
        
        
        
        
        /**
        * Set Content Type
        */
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        
        
        
        /**
        * Get Command Parameter
        */
        try
        {
                   strType = request.getParameter("Command");
        }
        catch(Exception e)
        {
                   e.printStackTrace();
        }
        
        
        /**
        * Variables Declaration
        */
        int txtCB_Year1=0,first_count_test=0,txtCB_Year2=0;
        int txtCB_Month=0;
        int cmbAcc_UnitCode=0;
        int cmbOffice_code=0;
        Date txtFrom_date=null;
        Date txtTo_date=null;
        Calendar c;
        String sql="";
        String txtCreat_By_Module="",txtMode_of_creat="";
        String cmbStatus="";
        
        /** Get Acounting Unit ID */
        try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
        
        /** Get Office Code */
        try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        
        
        /** Cashbook Year */
        txtCB_Year1=Integer.parseInt(request.getParameter("txtCB_Year1"));
        txtCB_Year2=Integer.parseInt(request.getParameter("txtCB_Year2"));
        
        /** Cashbook Month */
       // txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
//        if(txtCB_Month==1) 
//        {
        if(strType.equalsIgnoreCase("searchByMonth"))  
        {
                System.out.println("searchByMonth::::::::");   
                    xml="<response><command>searchByMonth</command>";
                   
            try {
                
                    ps = con.prepareStatement("SELECT ACCOUNTING_UNIT_ID,\n" + 
                    "  CASHBOOK_YEAR,\n" + 
                    "  CASHBOOK_MONTH,\n" + 
                    "  GL_PB_STATUS,\n" + 
                    "  TO_CHAR(GL_PB_DATE,'DD/MM/YYYY')AS GL_PB_DATE,\n" + 
                    "  GL_PB_FREEZE_STATUS,\n" + 
                    "  TO_CHAR(GL_PB_FREEZE_DATE,'DD/MM/YYYY')AS GL_PB_FREEZE_DATE\n" + 
                    " From Fas_Gl_Pbstatus\n" + 
                    " Where Accounting_Unit_Id=?\n" + 
                    "AND CASHBOOK_YEAR       =? and GL_PB_FREEZE_STATUS is null \n" + 
                    "\n" + 
                    "  Union All\n" + 
                    "  SELECT ACCOUNTING_UNIT_ID,\n" + 
                    "  CASHBOOK_YEAR,\n" + 
                    "  CASHBOOK_MONTH,\n" + 
                    "  GL_PB_STATUS,\n" + 
                    "  TO_CHAR(GL_PB_DATE,'DD/MM/YYYY')AS GL_PB_DATE,\n" + 
                    "  GL_PB_FREEZE_STATUS,\n" + 
                    "  TO_CHAR(GL_PB_FREEZE_DATE,'DD/MM/YYYY')AS GL_PB_FREEZE_DATE\n" + 
                    " From Fas_Gl_Pbstatus\n" + 
                    " Where Accounting_Unit_Id=?\n" + 
                    " AND CASHBOOK_YEAR       =? and GL_PB_FREEZE_STATUS is null \n" + 
                    " Order By Cashbook_Year,\n" + 
                    "  Cashbook_Month");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, txtCB_Year1);
                    ps.setInt(3, cmbAcc_UnitCode);
                    ps.setInt(4, txtCB_Year2);
                   // ps.setInt(3, txtCB_Month);
                    rs2 = ps.executeQuery();
                    while (rs2.next()) 
                    {
                        xml=xml+"<flag>success</flag>";
                        xml=xml+"<leng>";
                        xml=xml+"<year>"+rs2.getInt("CASHBOOK_YEAR") +"</year>"; 
                        xml=xml+"<month>"+rs2.getInt("CASHBOOK_MONTH") +"</month>"; 
                        xml=xml+"<status>"+rs2.getString("GL_PB_STATUS") +"</status>"; 
                        xml=xml+"<date_one>"+rs2.getString("GL_PB_DATE") +"</date_one>"; 
                        xml=xml+"<freezestatus>"+rs2.getString("GL_PB_FREEZE_STATUS") +"</freezestatus>"; 
                        xml=xml+"<freezedate>"+rs2.getString("GL_PB_FREEZE_DATE") +"</freezedate>"; 
                        xml=xml+"</leng>";
                   //     System.out.println("if first_count");
                    first_count_test++;
                    
                       
                    
                    }
                    if(first_count_test==0) {
                        xml=xml+"<flag>NoData</flag>";
                    }
                   
            }
            catch(Exception e1) 
            {
                System.out.println("Exception in query:::::::"+e1);
            }
                   
                 
            
        }
        xml=xml+"</response>";   
        out.println(xml); 
        System.out.println(xml);
        
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
        } catch (IOException e) {
        }
    }

}
