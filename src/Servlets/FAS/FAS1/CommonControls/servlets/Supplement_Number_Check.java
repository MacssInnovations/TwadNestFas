package Servlets.FAS.FAS1.CommonControls.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Supplement_Number_Check extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
    	//System.out.println("dogettttttttttttt");
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String xml="";

        /** Session Checking  */
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {

                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        /** Database Connection  */
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;


        String strCommand = "";


        try {
            ResourceBundle rs1 =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
        }


        /** Variables Declaration  */
        int cmbAcc_UnitCode = 0;
        int cb_month = 0;
        int cb_year = 0;
        int suppl_no = 0;
        int acc_unit_d=0;


        /** Get Command Parameter */
        try {
            strCommand = request.getParameter("Command");
          //  System.out.println("assign..here command..." + strCommand);
        } catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


        /** Get Cashbook Month */
        try {
            cb_month = Integer.parseInt(request.getParameter("cb_month"));
        } catch (Exception e) {
            System.out.println("Error getting CB Month");
        }


        /** Get Cashbook Year */
        try {
            cb_year = Integer.parseInt(request.getParameter("cb_year"));
        } catch (Exception e) {
            System.out.println("Error getting CB Year ");
        }
        /* Get Accounting unit Id */
         try {
             acc_unit_d = Integer.parseInt(request.getParameter("acc_unit_d"));
            // System.out.println("Accounting Unit Id******"+acc_unit_d);
         } catch (Exception e) {
             System.out.println("Error getting Accounting Unit Id ");
         }
        
        xml =xml+ "<response><command>Suppl_No_Check</command>";
        try 
        {
            if(strCommand.equalsIgnoreCase("check_SJVNumberNew")) 
            {
               
            	response.setContentType(CONTENT_TYPE);
            	
            	String sql="SELECT SUPPLEMENT_NO\n" + 
                "FROM FAS_SUPPLEMENT_GJV\n" + 
                "WHERE status                                ='L'                      \n" + 
                "AND cashbook_year                           =?\n" + 
                "AND cashbook_month =?\n" + 
                "AND SUPPLEMENT_NO NOT IN\n" + 
                "  (SELECT supplement_no                      \n" + 
                "  FROM fas_trial_balance_status_sjv\n" + 
                "  WHERE ACCOUNTING_UNIT_ID=?\n" +
                "   AND cashbook_month =?\n" + 
                "  AND cashbook_year                           = ?\n" + 
                "  AND tb_status                               ='Y'\n" + 
                "  )\n" + 
                "ORDER BY SUPPLEMENT_NO ";
                System.out.println("inside check_SJVNumberNew:"+sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, cb_year);
                ps.setInt(2, cb_month);
                ps.setInt(3, acc_unit_d);
                ps.setInt(4, cb_month);
                ps.setInt(5, cb_year);
                int cnt = 0;
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml =
                xml + "<suppl_no>" + rs.getInt("supplement_no") + "</suppl_no>";
                    cnt++;
                }

                xml = xml + "<flag>Success</flag>";

                if (cnt > 0) {
                    xml = xml + "<Suppl_Status>Avail</Suppl_Status>";
                } else {
                    xml = xml + "<Suppl_Status>NotAvail</Suppl_Status>";
                }
            }
            else if(strCommand.equalsIgnoreCase("check_SJVNumberTB")) 
            {
            	response.setContentType(CONTENT_TYPE);
                String sql="SELECT SUPPLEMENT_NO\n" + 
                "FROM FAS_SUPPLEMENT_GJV\n" + 
                "WHERE status                                ='L'                      \n" + 
                "AND cashbook_year                           =?\n" + 
                "AND cashbook_month =?\n" + 
                "AND SUPPLEMENT_NO IN\n" + 
                "  (SELECT supplement_no                      \n" + 
                "  FROM fas_trial_balance_status_sjv\n" + 
                "  WHERE ACCOUNTING_UNIT_ID=?\n" +
                "   AND cashbook_month =?\n" + 
                "  AND cashbook_year                           = ?\n" + 
                "  AND tb_status                               ='Y'\n" + 
                "  )\n" + 
                "ORDER BY SUPPLEMENT_NO ";
                System.out.println("inside check_SJVNumberNew:"+sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, cb_year);
                ps.setInt(2, cb_month);
                ps.setInt(3, acc_unit_d);
                ps.setInt(4, cb_month);
                ps.setInt(5, cb_year);
                int cnt = 0;
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml =
                xml + "<suppl_no>" + rs.getInt("supplement_no") + "</suppl_no>";
                    cnt++;
                }

                xml = xml + "<flag>Success</flag>";

                if (cnt > 0) {
                    xml = xml + "<Suppl_Status>Avail</Suppl_Status>";
                } else {
                    xml = xml + "<Suppl_Status>NotAvail</Suppl_Status>";
                }
            }

            else if(strCommand.equalsIgnoreCase("check_SJVNumber"))
            {
                  
            	response.setContentType(CONTENT_TYPE);
            	
            	String sql =
                            "" + "select supplement_no                 \n" + "from fas_supplement_gjv              \n" +
                            "where cashbook_month= ?  and         \n" +
                            "      cashbook_year= ?  \n";
                ps = con.prepareStatement(sql);
                ps.setInt(1, cb_month);
                ps.setInt(2, cb_year);
                int cnt = 0;
                rs = ps.executeQuery();
                while (rs.next()) 
                {
                    xml =xml + "<suppl_no>" + rs.getInt("supplement_no") + "</suppl_no>";
                    cnt++;
                }

                xml = xml + "<flag>Success</flag>";

                if (cnt > 0) 
                {
                    xml = xml + "<Suppl_Status>Avail</Suppl_Status>";
                } 
                else 
                {
                    xml = xml + "<Suppl_Status>NotAvail</Suppl_Status>";
                }
             }
            //display both sup 1 and 2
            else if(strCommand.equalsIgnoreCase("check_SJVNumber_all"))
            {
                     
            	response.setContentType(CONTENT_TYPE);
            	
            	String sql =
                            "" + "select supplement_no                 \n" + "from fas_supplement_gjv              \n" +
                            "where cashbook_month= "+cb_month+"  and         \n" +
                            "      cashbook_year= "+cb_year+" order by supplement_no ";
            //  System.out.println("sql::::"+sql);
                        ps = con.prepareStatement(sql);
               // ps.setInt(1, cb_month);
               // ps.setInt(2, cb_year);
                int cnt = 0;
                rs = ps.executeQuery();
                while (rs.next()) 
                {
                	System.out.println(" while");
                    xml =xml + "<suppl_no>" + rs.getInt("supplement_no") + "</suppl_no>";
                    cnt++;
                }

                xml = xml + "<flag>Success</flag>";

                if (cnt > 0) 
                {
                    xml = xml + "<Suppl_Status>Avail</Suppl_Status>";
                } 
                else 
                {
                    xml = xml + "<Suppl_Status>NotAvail</Suppl_Status>";
                }
             }
              
            } catch (SQLException e) {
                System.out.println(e);
                xml = xml + "<flag>Failure</flag>";
            }

        xml = xml + "</response>";
        out.println(xml);
        System.out.println(xml);
        out.close();


    }
}
