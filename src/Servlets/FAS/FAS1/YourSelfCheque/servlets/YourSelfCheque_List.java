package Servlets.FAS.FAS1.YourSelfCheque.servlets;

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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class YourSelfCheque_List extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        /* Session Checking */
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

        /* Variables Declaration */
        Connection con = null;
        ResultSet rs = null;
        Statement stmt = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;


        /* Database Connection */
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
//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
        }


        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String strType = "";
        String xml = "<response>";


        /* Get Command Parameter */
        try {
            strType = request.getParameter("Command");
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Variables Declaration */
        int txtCB_Year = 0;
        int txtCB_Month = 0;
        int cmbAcc_UnitCode = 0;
        int cmbOffice_code = 0;
        Date txtFrom_date = null;
        Date txtTo_date = null;
        Calendar c;
        String sql = "";
        String txtReceipt_type = "";
        String cmbStatus = "";

        /* Accounting Unit ID */
        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

        /* Accounting For Office ID */
        try {
            cmbOffice_code =
                    Integer.parseInt(request.getParameter("cmbOffice_code"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbOffice_code " + cmbOffice_code);


        /* Get Cashbook Month and Year */
        txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
        txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));

        System.out.println("year..." + txtCB_Year);
        System.out.println("Month..." + txtCB_Month);

        /* Get Voucher Status */
        cmbStatus = request.getParameter("cmbStatus");
        System.out.println("cmbStatus.." + cmbStatus);

        /* Searching by Month */
        if (strType.equalsIgnoreCase("searchByMonth")) {
            xml = "<response><command>searchByMonth</command>";

            sql =
 "select 															" + "   VOUCHER_NO,													" + "   to_char(VOUCHER_DATE,'DD/MM/YYYY') as VOUCHER_DATE,			" +
   "   to_char(CHEQUE_DATE,'DD/MM/YYYY') as CHEQUE_DATE,				" +
   "   CHEQUE_NUMBER,													" +
   "   trim(to_char(CHEQUE_AMOUNT,'99999999999999.99')) as CHEQUE_AMOUNT ,	" +
   "   REMARKS														" + "from 																" +
   "   fas_yourself_cheque_mst										" + "where 															" +
   "    ACCOUNTING_UNIT_ID=?											" +
   "and ACCOUNTING_FOR_OFFICE_ID=?									" +
   "and CASHBOOK_YEAR=? 												" + "and CASHBOOK_MONTH=? 												" +
   "and voucher_status=?												";


            try {
                int count = 0;
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                ps.setString(5, cmbStatus);

                xml = xml + "<flag>success</flag>";
                xml = xml + "<Ucode>" + cmbAcc_UnitCode + "</Ucode>";
                xml = xml + "<Offid>" + cmbOffice_code + "</Offid>";
                xml = xml + "<txtCB_Year>" + txtCB_Year + "</txtCB_Year>";
                xml = xml + "<txtCB_Month>" + txtCB_Month + "</txtCB_Month>";

                rs = ps.executeQuery();

                while (rs.next()) {
                    xml = xml + "<leng>";
                    xml =
 xml + "<VOUCHER_NO>" + rs.getInt("VOUCHER_NO") + "</VOUCHER_NO>";
                    xml =
 xml + "<VOUCHER_DATE>" + rs.getString("VOUCHER_DATE") + "</VOUCHER_DATE>";
                    xml =
 xml + "<CHEQUE_DATE>" + rs.getString("CHEQUE_DATE") + "</CHEQUE_DATE>";
                    xml =
 xml + "<CHEQUE_NUMBER>" + rs.getString("CHEQUE_NUMBER") + "</CHEQUE_NUMBER>";
                    xml =
 xml + "<CHEQUE_AMOUNT>" + rs.getString("CHEQUE_AMOUNT") + "</CHEQUE_AMOUNT>";
                    xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";
                    xml = xml + "</leng>";
                    count++;
                }
                if (count == 0) {
                    System.out.println("inside count==0");
                    xml =
 "<response><command>searchByMonth</command><flag>failure</flag>";
                }
            } catch (SQLException sqle) {
                System.out.println("error while fetching data " + sqle);
                xml =
 "<response><command>searchByMonth</command><flag>failure</flag>";
            }

        }


        /* Search By Given Date */

        else if (strType.equalsIgnoreCase("searchByDate")) {

            xml = "<response><command>searchByDate</command>";


            System.out.println("here " +
                               strType.equalsIgnoreCase("searchByDate"));

            /* Get From Date */
            String[] sd = request.getParameter("txtFrom_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtFrom_date = new Date(d.getTime());
            System.out.println("from_date " + txtFrom_date);

            /* Get To Date */
            sd = request.getParameter("txtTo_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            d = c.getTime();
            txtTo_date = new Date(d.getTime());
            System.out.println("txtTo_date " + txtTo_date);


            sql =
 "select 															" + "   VOUCHER_NO,													" + "   to_char(VOUCHER_DATE,'DD/MM/YYYY') as VOUCHER_DATE,			" +
   "   to_char(CHEQUE_DATE,'DD/MM/YYYY') as CHEQUE_DATE,				" +
   "   CHEQUE_NUMBER,													" +
   "   trim(to_char(CHEQUE_AMOUNT,'99999999999999.99')) as CHEQUE_AMOUNT ,	" +
   "   REMARKS														" + "from 																" +
   "   fas_yourself_cheque_mst										" + "where 															" +
   "    ACCOUNTING_UNIT_ID=?											" +
   "and ACCOUNTING_FOR_OFFICE_ID=?									" +
   "and CASHBOOK_YEAR=? 												" + "and CASHBOOK_MONTH=? 												" +
   "and voucher_status=?												" +
   "and voucher_DATE>=?                                               " +
   "and voucher_DATE<=? 		    									";


            try {
                int count = 0;
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                ps.setString(5, cmbStatus);
                ps.setDate(6, txtFrom_date);
                ps.setDate(7, txtTo_date);


                xml = xml + "<flag>success</flag>";
                xml = xml + "<Ucode>" + cmbAcc_UnitCode + "</Ucode>";
                xml = xml + "<Offid>" + cmbOffice_code + "</Offid>";
                xml = xml + "<txtCB_Year>" + txtCB_Year + "</txtCB_Year>";
                xml = xml + "<txtCB_Month>" + txtCB_Month + "</txtCB_Month>";

                rs = ps.executeQuery();

                while (rs.next()) {
                    xml = xml + "<leng>";
                    xml =
 xml + "<VOUCHER_NO>" + rs.getInt("VOUCHER_NO") + "</VOUCHER_NO>";
                    xml =
 xml + "<VOUCHER_DATE>" + rs.getString("VOUCHER_DATE") + "</VOUCHER_DATE>";
                    xml =
 xml + "<CHEQUE_DATE>" + rs.getString("CHEQUE_DATE") + "</CHEQUE_DATE>";
                    xml =
 xml + "<CHEQUE_NUMBER>" + rs.getString("CHEQUE_NUMBER") + "</CHEQUE_NUMBER>";
                    xml =
 xml + "<CHEQUE_AMOUNT>" + rs.getString("CHEQUE_AMOUNT") + "</CHEQUE_AMOUNT>";
                    xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";
                    xml = xml + "</leng>";
                    count++;
                }
                if (count == 0) {
                    System.out.println("inside count==0");
                    xml =
 "<response><command>searchByDate</command><flag>failure</flag>";
                }
            } catch (SQLException sqle) {
                System.out.println("error while fetching data " + sqle);
                xml =
 "<response><command>searchByDate</command><flag>failure</flag>";
            }
        }
        xml = xml + "</response>";
        out.println(xml);
        System.out.println(xml);
    }
}
