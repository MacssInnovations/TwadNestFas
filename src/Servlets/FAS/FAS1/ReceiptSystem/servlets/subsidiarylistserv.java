package Servlets.FAS.FAS1.ReceiptSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;

import java.util.ResourceBundle;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class subsidiarylistserv extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);


    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        System.out.println("caught in servlet");
        Connection con = null;
        ResultSet rss = null;
        PreparedStatement pss = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ResourceBundle rb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rb.getString("Config.DSN");
            String strhostname = rb.getString("Config.HOST_NAME");
            String strportno = rb.getString("Config.PORT_NUMBER");
            String strsid = rb.getString("Config.SID");
            String strdbusername = rb.getString("Config.USER_NAME");
            String strdbpassword = rb.getString("Config.PASSWORD");

            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();

            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection...." + e);
        }

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();


        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        /*PreparedStatement ps=null;
            ResultSet rs=null;
            PreparedStatement ps1=null;
            ResultSet rs1=null;*/


        String DateToBeDisplayed = "";


        String strCommand = "";
        String xml = "";
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");


        System.out.println("..........................servlet opening balnacelist started.............");

        try {
            strCommand = request.getParameter("command");
            System.out.println("strCommand....." + strCommand);
        } catch (Exception ae) {
            System.out.println("first exception...." + ae);
        }


        int acc_unit = 0, off_code = 0, cash_yr = 0, cash_mon = 0, head_code =
            0, sl_type = 0, sl_code = 0, acc_head = 0;
        String dc_cr = "";
        int project_id = 0;
        Double closing_bal = 0.0;
        java.sql.Date v_date;


        acc_head = Integer.parseInt(request.getParameter("ac_hd"));
        acc_unit = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        off_code = Integer.parseInt(request.getParameter("comOffCode"));
        cash_yr = Integer.parseInt(request.getParameter("CashbookYear"));
        cash_mon = Integer.parseInt(request.getParameter("CashbookMonth"));
        //head_code=Integer.parseInt(request.getParameter("accountHeadcode"));
        sl_type = Integer.parseInt(request.getParameter("SL_type"));
        sl_code = Integer.parseInt(request.getParameter("Type_Code"));

        System.out.println(acc_head);
        System.out.println(acc_unit);
        System.out.println(off_code);
        System.out.println(cash_yr);
        System.out.println(cash_mon);
        System.out.println(sl_type);
        System.out.println(sl_code);


        if (strCommand.equalsIgnoreCase("fetch")) {
            xml = "<response><command>fetch</command>";
            //String sql="insert into TEST_STATE values(?,?)";
            System.out.println("inside fetch command");
            try {

                pss =
 con.prepareStatement("select PROJECT_ID,CLOSING_BALANCE," +
                      " CLOSING_BALANCE_DR_CR_IND,LAST_DATE_UPDATED from FAS_SELF_BALANCE_MASTER" +
                      " where ACCOUNT_HEAD_CODE=? and ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and " +
                      " CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=?");

                pss.setInt(1, acc_head);
                pss.setInt(2, acc_unit);
                pss.setInt(3, off_code);
                pss.setInt(4, cash_yr);
                pss.setInt(5, cash_mon);
                pss.setInt(6, sl_type);
                pss.setInt(7, sl_code);

                rss = pss.executeQuery();
                System.out.println();

                while (rss.next()) {

                    project_id = rss.getInt("PROJECT_ID");
                    System.out.println("project id is*****************" +
                                       project_id);

                    closing_bal = rss.getDouble("CLOSING_BALANCE");
                    System.out.println("closing balance is*****************" +
                                       closing_bal);

                    dc_cr = rss.getString("CLOSING_BALANCE_DR_CR_IND").trim();
                    System.out.println("CLOSING_BALANCE_DR_CR_IND is*****************" +
                                       dc_cr);


                    java.sql.Date update = rss.getDate("LAST_DATE_UPDATED");

                    if (update == null) {
                        DateToBeDisplayed = "Not Specified";
                    } else {
                        try {
                            java.text.SimpleDateFormat sdf =
                                new java.text.SimpleDateFormat("dd/MM/yyyy");
                            DateToBeDisplayed = sdf.format(update);
                        } catch (Exception e) {
                            System.out.println("error while formatting date : " +
                                               e);
                            DateToBeDisplayed = "Not Specified";
                        }
                    }

                    System.out.println(DateToBeDisplayed);


                }

                xml =
 xml + "<flag>success</flag>" + " <accountHeadCode>" + acc_head +
   "</accountHeadCode>" + " <ProjectId>" + project_id + "</ProjectId>" +
   " <CloseBal>" + closing_bal + "</CloseBal>" + " <Dc_Cr>" + dc_cr +
   "</Dc_Cr>" + " <Ldate>" + DateToBeDisplayed + "</Ldate>";

            } catch (Exception que) {
                xml = xml + "<flag>failure</flag>";
                System.out.println(que.getMessage());
                System.out.println("exception in fetching fas_opening balance details");
            }

            int vo_no = 0;
            String vo_type = "", rad_cr_dr = "", rem = "";
            Double amtt = 0.0;
            java.sql.Date vo_date = null;
            String det_date = "";

            try {

                ps =
  con.prepareStatement("select VOUCHER_TYPE, VOUCHER_NO, CR_DR_INDICATOR," +
                       " VOUCHER_DATE, AMOUNT, REMARK from FAS_SUB_LEDGER_TRN" +
                       " where ACCOUNT_HEAD_CODE=? and ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and" +
                       " CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=?");

                System.out.println("inside detail part");

                ps.setInt(1, acc_head);
                ps.setInt(2, acc_unit);
                ps.setInt(3, off_code);
                ps.setInt(4, cash_yr);
                ps.setInt(5, cash_mon);
                ps.setInt(6, sl_type);
                ps.setInt(7, sl_code);

                rs = ps.executeQuery();

                while (rs.next()) {
                    vo_type = rs.getString("VOUCHER_TYPE");
                    System.out.println("VOUCHER_TYPE.........." + vo_type);

                    vo_no = rs.getInt("VOUCHER_NO");
                    System.out.println("VOUCHER_NO.........." + vo_no);

                    rad_cr_dr = rs.getString("CR_DR_INDICATOR");
                    System.out.println("CR_DR_INDICATOR.........." +
                                       rad_cr_dr);

                    vo_date = rs.getDate("VOUCHER_DATE");
                    System.out.println("VOUCHER_DATE..........." + vo_date);

                    if (vo_date == null) {
                        DateToBeDisplayed = "Not Specified";
                    } else {
                        try {
                            java.text.SimpleDateFormat sdf =
                                new java.text.SimpleDateFormat("dd/MM/yyyy");
                            det_date = sdf.format(vo_date);
                        } catch (Exception e) {
                            System.out.println("error while formatting date : " +
                                               e);
                            det_date = "Not Specified";
                        }
                    }

                    System.out.println(det_date);

                    amtt = rs.getDouble("AMOUNT");
                    System.out.println("AMOUNT........." + amtt);

                    rem = rs.getString("REMARK");
                    System.out.println("REMARK.........." + rem);

                    xml =
 xml + "<AccountHead>" + acc_head + "</AccountHead><VOUCHER_TYPE>" + vo_type +
   "</VOUCHER_TYPE>" + " <VOUCHER_NO>" + vo_no + "</VOUCHER_NO>" +
   " <CR_DR_INDICATOR>" + rad_cr_dr + "</CR_DR_INDICATOR>" +
   " <VOUCHER_DATE>" + det_date + "</VOUCHER_DATE>" + " <AMOUNT>" + amtt +
   "</AMOUNT>" + " <REMARK>" + rem + "</REMARK>";
                }

                /* xml=xml+"<VOUCHER_TYPE>"+vo_type+"</VOUCHER_TYPE>" +
                " <VOUCHER_NO>"+vo_no+"</VOUCHER_NO>" +
                " <CR_DR_INDICATOR>"+rad_cr_dr+"</CR_DR_INDICATOR>" +
                " <VOUCHER_DATE>"+det_date+"</VOUCHER_DATE>" +
                " <AMOUNT>"+amtt+"</AMOUNT>" +
                " <REMARK>"+rem+"</REMARK>";*/

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            xml = xml + "</response>";
        }


        System.out.println("xml is:" + xml);
        out.write(xml);
        out.flush();
        out.close();

    }


}
