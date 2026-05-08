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

public class SJV_TB_Check extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();


        System.out.println("----------------------------------------------------------------------------<><>SJV------<>");

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
        int txtCash_year = 0;
        int txtCash_Month_hid = 0;

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
        int cmbOffice_code = 0;
        Date txtCrea_date = null;
        Calendar c;


        /** Get Command Parameter */
        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        } catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


        /** Get Accounting Unit ID  */
        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);


        /** Get Accounting Office ID  */
        try {
            cmbOffice_code =
                    Integer.parseInt(request.getParameter("cmbOffice_code"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbOffice_code " + cmbOffice_code);


        String[] sd = request.getParameter("TB_date").split("/");
        c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
        java.util.Date d = c.getTime();
        txtCrea_date = new Date(d.getTime());
        System.out.println("txtCrea_date " + txtCrea_date);

        System.out.println("b4 getting month and year");
        try {
            txtCash_year = Integer.parseInt(sd[2]);
        } catch (Exception e) {
            System.out.println("exception" + e);
        }
        System.out.println("txtCash_year " + txtCash_year);

        try {
            txtCash_Month_hid = Integer.parseInt(sd[1]);
        } catch (Exception e) {
            System.out.println("exception" + e);
        }
        System.out.println("txtCash_Month_hid " + txtCash_Month_hid);


        String sql =
            "" + "          select count(*) as cnt                 \n" +
            "          from                                   \n" +
            "             fas_trial_balance_status_sjv        \n" +
            "          where                                  \n" +
            "              accounting_unit_id =?              \n" +
            "          and cashbook_month= ?                  \n" +
            "          and cashbook_year= ?                   \n" +
            "          and (tb_status='Y' OR  NIL_TB_STATUS = 'Y' )                  \n" +
            "          and supplement_no = (                  \n" +
            "          select max(supplement_no) from         \n" +
            "          fas_supplement_gjv                     \n" +
            "          where cashbook_month= ?                \n" +
            "          and cashbook_year= ? )                 \n" +
            "                                                    ";


        String xml = "<response><command>SJV_Check</command>";

        try {

            System.out.println(sql);

            ps = con.prepareStatement(sql);
            ps.setInt(1, cmbAcc_UnitCode);
            ps.setInt(2, txtCash_Month_hid);
            ps.setInt(3, txtCash_year);
            ps.setInt(4, txtCash_Month_hid);
            ps.setInt(5, txtCash_year);


            int cnt = 0;

            rs = ps.executeQuery();
            if (rs.next()) {
                cnt = rs.getInt("cnt");
            }

            xml = xml + "<flag>Success</flag>";

            if (cnt > 0)
                xml = xml + "<TB>Freeze</TB>";
            else if (cnt == 0)
                xml = xml + "<TB>NotFreeze</TB>";

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
