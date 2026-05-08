package Servlets.FAS.FAS1.PaymentSystem.servlets;

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

public class ListCheque_Cancellation extends HttpServlet {

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

        PreparedStatement ps = null;
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
        String sql = "", newcheq_status = "";
        String newcheq_date = "", newcheqamt = "", remarks = "", forgjvdate =
            "", cheqcaneldate = "";
        int new_cheqno = 0, gjvno = 0;


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

        if (strType.equalsIgnoreCase("searchByMonth")) {
            System.out.println("inside searchbymonth");
            xml = "<response><command>searchByMonth</command>";


            sql =
 "select to_char(a.CHEQ_CANCEL_DATE,'DD/MM/YYYY') as cheqcancel_date,a.DOCUMENT_TYPE,a.DOCUMENT_NO,to_char(a.DOCUMENT_DATE,'DD/MM/YYYY') as doc_date,a.CHEQUE_OR_DD,to_char(a.CHEQUE_DD_DATE,'DD/MM/YYYY') as oldcheq_date,\n" +
   "trim(to_char(a.AMOUNT,'99999999999999.99')) as oldcheqamt,a.REMARKS,a.ISSUE_CHEQUE,a.ISSUE_CHEQUE_NO,\n" +
   " to_char(a.ISSUE_CHEQUE_DATE,'DD/MM/YYYY') as newcheq_date,\n" +
   "trim(to_char(a.ISSUE_CHEQUE_AMOUNT,'99999999999999.99')) as newcheqamt, a.CHEQUE_DD_NO, " +
   "b.VOUCHER_NO as GJVNO,to_char(b.VOUCHER_DATE,'DD/MM/YYYY') as GJVDATE from FAS_CHEQUE_CANCEL a,FAS_JOURNAL_MASTER b \n" +
   "where a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID \n" +
   "and a.ACCOUNTING_FOR_OFFICE_ID=b.ACCOUNTING_FOR_OFFICE_ID\n" +
   "and a.CASHBOOK_YEAR=b.CASHBOOK_YEAR \n" +
   "and a.CASHBOOK_MONTH=b.CASHBOOK_MONTH \n" +
   "and a.CHEQUE_DD_NO=b.CHEQUE_NO \n" +
   "and a.CHEQUE_DD_DATE=b.CHEQUE_DATE and\n" +
   "a.ACCOUNTING_UNIT_ID=? and a.ACCOUNTING_FOR_OFFICE_ID=? and \n" +
   "a.CASHBOOK_YEAR=? and a.CASHBOOK_MONTH=? and b.JOURNAL_STATUS='L' ";
            /* "union\n" +
              "select to_char(a.CHEQ_CANCEL_DATE,'DD/MM/YYYY') as cheqcancel_date,a.DOCUMENT_TYPE,a.DOCUMENT_NO,to_char(a.DOCUMENT_DATE,'DD/MM/YYYY') as doc_date,a.CHEQUE_OR_DD,to_char(a.CHEQUE_DD_DATE,'DD/MM/YYYY') as oldcheq_date,\n" +
              "trim(to_char(a.AMOUNT,'99999999999999.99')) as oldcheqamt,a.REMARKS,a.ISSUE_CHEQUE,a.ISSUE_CHEQUE_NO,\n" +
              " to_char(a.ISSUE_CHEQUE_DATE,'DD/MM/YYYY') as newcheq_date,\n" +
              "trim(to_char(a.ISSUE_CHEQUE_AMOUNT,'99999999999999.99')) as newcheqamt,a.CHEQUE_DD_NO, " +
              "0 as GJVNO,'---' as GJVDATE from FAS_CHEQUE_CANCEL a \n" +
              "where a.ISSUE_CHEQUE='Y' and a.AMOUNT=a.ISSUE_CHEQUE_AMOUNT and\n" +
              "a.ACCOUNTING_UNIT_ID=? and a.ACCOUNTING_FOR_OFFICE_ID=? and \n" +
              "a.CASHBOOK_YEAR=? and a.CASHBOOK_MONTH=?\n";*/
            try {
                System.out.println("1"+sql);
                System.out.println(cmbAcc_UnitCode);
                System.out.println(cmbOffice_code);
                System.out.println(txtCB_Year);
                System.out.println(txtCB_Month);
                int count = 0;
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                /*  ps.setInt(5,cmbAcc_UnitCode);
            ps.setInt(6,cmbOffice_code);
            ps.setInt(7,txtCB_Year);
            ps.setInt(8,txtCB_Month);*/

                xml =
 xml + "<flag>success</flag><Ucode>" + cmbAcc_UnitCode + "</Ucode><Offid>" +
   cmbOffice_code + "</Offid><txtCB_Year>" + txtCB_Year +
   "</txtCB_Year><txtCB_Month>" + txtCB_Month + "</txtCB_Month>";
                System.out.println("xml......");
                rs = ps.executeQuery();

                while (rs.next()) {
                    System.out.println("2");

                    try {
                        try {


                            if (rs.getString("cheqcancel_date") == null) {
                                cheqcaneldate = "-";
                            } else {
                                cheqcaneldate =
                                        rs.getString("cheqcancel_date");
                                System.out.println("cheqcaneldate" +
                                                   cheqcaneldate);
                            }
                        } catch (Exception e) {
                            System.out.println("Error in getting cheqcaneldate values" +
                                               e);

                        }

                        if (rs.getString("REMARKS") == null) {
                            remarks = "-";
                        } else {
                            remarks = rs.getString("REMARKS");
                            System.out.println("REMARKS" + remarks);
                        }
                    } catch (Exception e) {
                        System.out.println("Error in getting REMARKS values" +
                                           e);

                    }
                    try {


                        if (rs.getString("newcheq_date") == null) {
                            newcheq_date = "-";
                        } else {
                            newcheq_date = rs.getString("newcheq_date");
                            System.out.println("newcheq_date" + newcheq_date);
                        }
                    } catch (Exception e) {
                        System.out.println("Error in getting newcheq_date values" +
                                           e);

                    }
                    try {


                        if (rs.getString("ISSUE_CHEQUE") == null) {
                            newcheq_status = "N";
                        } else {
                            newcheq_status = rs.getString("ISSUE_CHEQUE");
                            System.out.println("newcheq_status" +
                                               newcheq_status);
                        }
                    } catch (Exception e) {
                        System.out.println("Error in getting newcheq_date values" +
                                           e);

                    }
                    try {


                        if (rs.getString("GJVDATE") == null) {
                            forgjvdate = "-";
                        } else {
                            forgjvdate = rs.getString("GJVDATE");
                            System.out.println("GJVDATE" + forgjvdate);
                        }
                    } catch (Exception e) {
                        System.out.println("Error in getting GJVDATE values" +
                                           e);

                    }
                    try {


                        if (rs.getInt("ISSUE_CHEQUE_NO") == 0) {
                            new_cheqno = 0;
                        } else {
                            new_cheqno = rs.getInt("ISSUE_CHEQUE_NO");
                            System.out.println("new_cheqno" + new_cheqno);
                        }
                    } catch (Exception e) {
                        System.out.println("Error in getting new_cheqno values" +
                                           e);

                    }
                    try {


                        /*  if (rs.getInt("GJVNO") ==0 ) {
                            gjvno = 0;
                        } else {*/
                        gjvno = rs.getInt("GJVNO");
                        System.out.println("GJVNO" + gjvno);
                        //  }
                    } catch (Exception e) {
                        System.out.println("Error in getting new_cheqno values" +
                                           e);

                    }
                    try {


                        if (rs.getString("newcheqamt") == null) {
                            newcheqamt = "-";
                        } else {
                            newcheqamt = rs.getString("newcheqamt");
                            System.out.println("newcheqamt" + newcheqamt);
                        }
                    } catch (Exception e) {
                        System.out.println("Error in getting newcheqamt values");

                    }

                    xml = xml + "<leng>";
                    xml =
 xml + "<cheq_cancel_date>" + rs.getString("cheqcancel_date") +
   "</cheq_cancel_date>";
                    xml =
 xml + "<doc_type>" + rs.getString("DOCUMENT_TYPE") + "</doc_type>";
                    xml =
 xml + "<doc_no>" + rs.getString("DOCUMENT_NO") + "</doc_no>";
                    xml =
 xml + "<doc_date>" + rs.getString("doc_date") + "</doc_date>";
                    xml =
 xml + "<oldcheq_type>" + rs.getString("CHEQUE_OR_DD") + "</oldcheq_type>";
                    xml =
 xml + "<old_cheqno>" + rs.getInt("CHEQUE_DD_NO") + "</old_cheqno>";
                    xml =
 xml + "<old_cheqdate>" + rs.getString("oldcheq_date") + "</old_cheqdate>";
                    xml =
 xml + "<old_cheqamt>" + rs.getString("oldcheqamt") + "</old_cheqamt>";
                    xml = xml + "<remarks>" + remarks + "</remarks>";
                    xml =
 xml + "<newcheq_status>" + newcheq_status + "</newcheq_status>";
                    xml = xml + "<new_cheqno>" + new_cheqno + "</new_cheqno>";
                    xml =
 xml + "<new_cheqdate>" + newcheq_date + "</new_cheqdate>";
                    xml =
 xml + "<new_cheqamt>" + rs.getString("newcheqamt") + "</new_cheqamt>";
                    xml = xml + "<gjvno>" + gjvno + "</gjvno>";
                    xml = xml + "<gjvdate>" + forgjvdate + "</gjvdate>";

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

        xml = xml + "</response>";
        out.println(xml);
        System.out.println(xml);
    }
}
