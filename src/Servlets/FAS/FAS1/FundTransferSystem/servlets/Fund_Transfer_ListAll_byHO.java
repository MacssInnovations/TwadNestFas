package Servlets.FAS.FAS1.FundTransferSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;

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

public class Fund_Transfer_ListAll_byHO extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

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
        Connection con = null;
        ResultSet rs = null;
        Statement stmt = null;
        PreparedStatement ps = null, ps2 = null;
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
            System.out.println("Exception in openeing connection :" + e);
            //               sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        System.out.println("servlet called");
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String strType = "", xml = "<response>";
        try {
            strType = request.getParameter("Command");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode =
            0, cmbOffice_code = 0;
        Date txtFrom_date = null, txtTo_date = null;
        Calendar c;
        String sql = "", txtCreat_By_Module = "", cmbStatus = "";


        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

        try {
            cmbOffice_code =
                    Integer.parseInt(request.getParameter("cmbOffice_code"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbOffice_code " + cmbOffice_code);
        System.out.println("strtype  " + strType);
        txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
        txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        System.out.println("year..." + txtCB_Year);
        System.out.println("Month..." + txtCB_Month);
        txtCreat_By_Module = request.getParameter("txtCreat_By_Module");
        // after receipt status
        cmbStatus = request.getParameter("cmbStatus");

        System.out.println("cmbStatus.." + cmbStatus);
        if (strType.equalsIgnoreCase("searchByMonth")) {
            xml = "<response><command>searchByMonth</command>";

            sql =
 "select m.VOUCHER_NO,to_char(m.DATE_OF_TRANSFER,'DD/MM/YYYY') as rec_date ,m.REMARKS," +
   "trim(to_char(m.TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,m.ACCOUNT_HEAD_CODE,m.HO_ACCOUNT_NO," +
   "bk.BANK_NAME ||'-'|| br.BRANCH_NAME ||'-'|| coalesce(br.CITY_TOWN_NAME,'') AS BK_BR_CITY  " +
   "from FAS_FUND_TRF_FROM_HO_MASTER m,FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br" +
   " where m.ACCOUNTING_UNIT_ID=? and " +
   "m.ACCOUNTING_FOR_OFFICE_ID=? and m.CASHBOOK_YEAR=? and m.CASHBOOK_MONTH=?  and m.TRANSFER_STATUS=? " +
   " and m.HO_BANK_ID=br.BANK_ID and m.HO_BRANCH_ID=br.BRANCH_ID and m.HO_BANK_ID=bk.BANK_ID order by m.voucher_no ";
            try {
                int count = 0;
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                ps.setString(5, cmbStatus);
                System.out.println("comes here");
                xml =
 xml + "<flag>success</flag><Ucode>" + cmbAcc_UnitCode + "</Ucode><Offid>" +
   cmbOffice_code + "</Offid><txtCB_Year>" + txtCB_Year +
   "</txtCB_Year><txtCB_Month>" + txtCB_Month + "</txtCB_Month>";
                rs = ps.executeQuery();
                System.out.println("comes here...2");
                while (rs.next()) {
                    System.out.println("comes here..3");
                    xml = xml + "<leng>";
                    xml =
 xml + "<Rec_no>" + rs.getInt("VOUCHER_NO") + "</Rec_no>";
                    xml =
 xml + "<Rec_Date>" + rs.getString("rec_date") + "</Rec_Date>";
                    xml =
 xml + "<Remak>" + rs.getString("REMARKS") + "</Remak>";
                    xml =
 xml + "<Tot_Amt>" + rs.getString("TOTAL_AMOUNT") + "</Tot_Amt>";
                    xml =
 xml + "<BK_BR_CITY>" + rs.getString("BK_BR_CITY") + "</BK_BR_CITY>";
                    xml =
 xml + "<HO_REF_NO>" + rs.getString("ACCOUNT_HEAD_CODE") + "</HO_REF_NO>";
                    xml =
 xml + "<HO_ref_date>" + rs.getString("HO_ACCOUNT_NO") + "</HO_ref_date>";
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
        System.out.println("here " + strType.equalsIgnoreCase("searchByDate"));
        if (strType.equalsIgnoreCase("searchByDate")) {
            xml = "<response><command>searchByDate</command>";
            System.out.println("here " +
                               strType.equalsIgnoreCase("searchByDate"));

            String[] sd = request.getParameter("txtFrom_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtFrom_date = new Date(d.getTime());
            System.out.println("from_date " + txtFrom_date);

            sd = request.getParameter("txtTo_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            d = c.getTime();
            txtTo_date = new Date(d.getTime());
            System.out.println("txtTo_date " + txtTo_date);

            sql =
 "select m.VOUCHER_NO,to_char(m.DATE_OF_TRANSFER,'DD/MM/YYYY') as rec_date,m.REMARKS, trim(to_char(m.TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT," +
   "m.ACCOUNT_HEAD_CODE,m.HO_ACCOUNT_NO," +
   "bk.BANK_NAME ||'-'|| br.BRANCH_NAME ||'-'|| br.CITY_TOWN_NAME AS BK_BR_CITY " +
   "from FAS_FUND_TRF_FROM_HO_MASTER m ,FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br where m.ACCOUNTING_UNIT_ID=? and  " +
   "m.ACCOUNTING_FOR_OFFICE_ID=? and m.CASHBOOK_YEAR=? and m.CASHBOOK_MONTH=? and m.DATE_OF_TRANSFER>=? " +
   "and m.DATE_OF_TRANSFER<=? and m.TRANSFER_STATUS=? " +
   "and m.HO_BANK_ID=br.BANK_ID and m.HO_BRANCH_ID=br.BRANCH_ID and m.HO_BANK_ID=bk.BANK_ID order by m.voucher_no ";
            try {
                int count = 0;
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                ps.setDate(5, txtFrom_date);
                ps.setDate(6, txtTo_date);
                ps.setString(7, cmbStatus);
                xml =
 xml + "<flag>success</flag><Ucode>" + cmbAcc_UnitCode + "</Ucode><Offid>" +
   cmbOffice_code + "</Offid><txtCB_Year>" + txtCB_Year +
   "</txtCB_Year><txtCB_Month>" + txtCB_Month + "</txtCB_Month>";
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<leng>";
                    xml =
 xml + "<Rec_no>" + rs.getInt("VOUCHER_NO") + "</Rec_no>";
                    xml =
 xml + "<Rec_Date>" + rs.getString("rec_date") + "</Rec_Date>";
                    xml =
 xml + "<Remak>" + rs.getString("REMARKS") + "</Remak>";
                    xml =
 xml + "<Tot_Amt>" + rs.getString("TOTAL_AMOUNT") + "</Tot_Amt>";
                    xml =
 xml + "<BK_BR_CITY>" + rs.getString("BK_BR_CITY").trim() + "</BK_BR_CITY>";
                    xml =
 xml + "<HO_REF_NO>" + rs.getString("ACCOUNT_HEAD_CODE") + "</HO_REF_NO>";
                    xml =
 xml + "<HO_ref_date>" + rs.getString("HO_ACCOUNT_NO") + "</HO_ref_date>";
                    xml = xml + "</leng>";
                    count++;
                }
                if (count == 0) {
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

