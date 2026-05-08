package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class FreezeA52_Verification extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {


        /**
         * Database con
         */
        PrintWriter out=response.getWriter();
        Connection con = null;
        Statement statement = null;
        ResultSet rst = null, results = null;
       
        try {
            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                
            String conString = "";

            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");

            conString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();

            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(conString, strdbusername.trim(),
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

        String userid = (String)session.getAttribute("UserId");
        /**
         * Variables Declaration
         */
         String command=request.getParameter("command");
         System.out.println("command==="+command);
        
        int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode = 0, OfficeCode =
            0, achcode = 0,cmbOffice_code=0;
        String fin_year="";
        String xml="";
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
         try{
        	 cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        	 System.out.println("cmbAcc_UnitCode==="+cmbAcc_UnitCode);
         }catch(Exception e)
         {
        	 e.printStackTrace();
         }
         try{
        	 cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
        	 System.out.println("cmbOffice_code==="+cmbOffice_code);
         }catch(Exception e)
         {
        	 e.printStackTrace();
         }
        /* try{
        	 txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
        	 System.out.println("txtCB_Year==="+txtCB_Year);
         }catch(Exception e)
         {
        	 e.printStackTrace();
         }
         try{
        	 txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
        	 System.out.println("txtCB_Month==="+txtCB_Month);
         }catch(Exception e)
         {
        	 e.printStackTrace();
         }*/
         try{
        	 fin_year=request.getParameter("fin_year");
        	 System.out.println("fin_year==="+fin_year);
         }catch(Exception e)
         {
        	 e.printStackTrace();
         }
         
         if(command.equalsIgnoreCase("confim"))
         {   
        	 xml="<response>";
        	 xml=xml+"<command>confim</command>";
        	 try{
        		 
        		 
        		 PreparedStatement ps =
                     con.prepareStatement("select 'X' from FAS_A52_VERIFY_STATUS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
                 ps.setInt(1, cmbAcc_UnitCode);
                 ps.setInt(2, cmbOffice_code);
                 ps.setString(3, fin_year);
                ResultSet rs= ps.executeQuery(); 
                
                
        		 if(rs.next()){
        			 xml=xml+"<flag>already</flag>";
        		 }else{
        	 PreparedStatement ps1 =
                 con.prepareStatement("insert into FAS_A52_VERIFY_STATUS(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,A52_STATUS,A52_DATE,UPDATED_BY_USER_ID,UPDATED_DATE ) values(?,?,?,?,?,?,?)");
             ps1.setInt(1, cmbAcc_UnitCode);
             ps1.setInt(2, cmbOffice_code);
             //ps1.setInt(2, 0);
           //  ps1.setInt(3, txtCB_Year);
             ps1.setString(3, fin_year);
             ps1.setString(4, "Y");
             ps1.setTimestamp(5, ts);
             ps1.setString(6, userid);
             ps1.setTimestamp(7, ts);
             ps1.executeUpdate(); 
             xml=xml+"<flag>success</flag>";
        		 }
             
             
        	 }catch(Exception e)
        	 {
        		 e.printStackTrace();
        		 xml=xml+"<flag>failure</flag>";
        	 }
        	 xml=xml+"</response>";
        	 System.out.println(xml);
        	 out.println(xml);
         }
    }

}
