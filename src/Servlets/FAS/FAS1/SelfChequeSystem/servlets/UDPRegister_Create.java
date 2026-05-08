package Servlets.FAS.FAS1.SelfChequeSystem.servlets;

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

public class UDPRegister_Create extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection con = null;
        ResultSet rs = null, rs1 = null;
        PreparedStatement ps = null, ps1 = null;
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        String strCommand = "";
        try {
            ResourceBundle rs_bundle =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs_bundle.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs_bundle.getString("Config.DSN");
            String strhostname = rs_bundle.getString("Config.HOST_NAME");
            String strportno = rs_bundle.getString("Config.PORT_NUMBER");
            String strsid = rs_bundle.getString("Config.SID");
            String strdbusername = rs_bundle.getString("Config.USER_NAME");
            String strdbpassword = rs_bundle.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
        int txtCB_Year = 0, txtCB_Month = 0;
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

        txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
        txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        System.out.println("year..." + txtCB_Year);
        System.out.println("Month..." + txtCB_Month);

        if (strCommand.equalsIgnoreCase("load_chequeNO")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>" + strCommand + "</command>";
            try {
                String sql =
                    " select t.CHEQUE_DD_NO from FAS_RECEIPT_MASTER m,FAS_RECEIPT_TRANSACTION t " +
                    " where m.ACCOUNTING_UNIT_ID=? and m.ACCOUNTING_FOR_OFFICE_ID=? and m.CASHBOOK_YEAR=? and m.CASHBOOK_MONTH=? " +
                    " and m.CREATED_BY_MODULE='SC' and m.RECEIPT_STATUS!='C' and t.CHEQUE_OR_DD='C' " +
                    " and m.NO_OF_PAY_VOUCHER_IN_SF_CHEQUE!=0 " +
                    " and m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID" +
                    " and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID " +
                    " and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR  " +
                    " and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH " +
                    " and m.RECEIPT_NO=t.RECEIPT_NO ";

                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                rs = ps.executeQuery();
                int count = 0;
                while (rs.next()) {
                    count++;
                    System.out.println("che.no.." +
                                       rs.getLong("CHEQUE_DD_NO"));
                    xml =
 xml + "<cheq_no>" + rs.getLong("CHEQUE_DD_NO") + "</cheq_no>";
                }
                if (count == 0)
                    xml = xml + "<flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";
            } catch (SQLException e) {
                System.out.println("exception " + e);
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("load_cheque_details")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>" + strCommand + "</command>";

            String txtCheque_DD_NO = request.getParameter("txtCheque_DD_NO");

            try {
                String sql =
                    " select to_char(ADD_MONTHS(t.CHEQUE_DD_DATE,3),'DD/MM/YYYY')  as checkdate ,m.RECEIPT_NO,to_char(t.CHEQUE_DD_DATE,'DD/MM/YYYY') as date_drwal,trim(to_char(m.TOTAL_AMOUNT,'99999999999999.99')) as tot_cheq_amt,m.REMARKS from FAS_RECEIPT_MASTER m,FAS_RECEIPT_TRANSACTION t " +
                    " where m.ACCOUNTING_UNIT_ID=? and m.ACCOUNTING_FOR_OFFICE_ID=? and m.CASHBOOK_YEAR=? and m.CASHBOOK_MONTH=? and t.CHEQUE_DD_NO=?" +
                    " and m.CREATED_BY_MODULE='SC' and m.RECEIPT_STATUS!='C' and t.CHEQUE_OR_DD='C' " +
                    " and m.NO_OF_PAY_VOUCHER_IN_SF_CHEQUE!=0  " +
                    " and m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID" +
                    " and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID " +
                    " and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR  " +
                    " and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH " +
                    " and m.RECEIPT_NO=t.RECEIPT_NO ";

                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                ps.setString(5, txtCheque_DD_NO);
                rs = ps.executeQuery();
                int count = 0;

                if (rs.next()) {
                    xml =
 xml + "<date_drwal>" + rs.getString("date_drwal") + "</date_drwal>";
                    xml =
 xml + "<tot_cheq_amt>" + rs.getString("tot_cheq_amt") + "</tot_cheq_amt>";
                    xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";
                    xml =
 xml + "<checkdate>" + rs.getString("checkdate") + "</checkdate>"; // used to get the date after 3 months from cheque date

                    System.out.println("rec.. no" + rs.getInt("RECEIPT_NO"));
                }


                String sql2 =
                    " select a.ACQ_ROLL_NO,a.VOUCHER_NO,a.DISBURSING_OFFICE_CODE,a.EMPLOYEE_CODE," +
                    " trim(to_char(a.TOTAL_AMOUNT,'99999999999999.99')) as  tot_Acq_amt," +
                    " b.OFFICE_NAME,e.EMPLOYEE_NAME ||' - ' ||d.DESIGNATION as emp_disgn " +
                    " from FAS_PAYMENT_MASTER m,FAS_ACQ_ROLL_TRANSACTION a,COM_MST_OFFICES b,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d,HRM_MST_EMPLOYEES e " +
                    " where   " +
                    " m.ACCOUNTING_UNIT_ID=? and m.ACCOUNTING_FOR_OFFICE_ID=? " +
                    " and m.CASHBOOK_YEAR=? and m.CASHBOOK_MONTH=? " +
                    " and m.SELFCHEQUE_RECEIPT_NO=? " +
                    " and m.ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID" +
                    " and m.ACCOUNTING_FOR_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID " +
                    " and m.CASHBOOK_YEAR=a.CASHBOOK_YEAR  " +
                    " and m.CASHBOOK_MONTH=a.CASHBOOK_MONTH " +
                    " and m.VOUCHER_NO=a.VOUCHER_NO " +
                    " and a.AMOUNT_DISBURSED_YES_OR_NO='N' " +
                    " and a.DISBURSING_OFFICE_CODE=b.OFFICE_ID " +
                    " and a.EMPLOYEE_CODE=c.EMPLOYEE_ID " +
                    " and c.EMPLOYEE_ID=e.EMPLOYEE_ID " +
                    " and c.DESIGNATION_ID=d.DESIGNATION_ID " +
                    " order by a.ACQ_ROLL_NO,a.DISBURSING_OFFICE_CODE,a.VOUCHER_NO";
                ps1 = con.prepareStatement(sql2);
                ps1.setInt(1, cmbAcc_UnitCode);
                ps1.setInt(2, cmbOffice_code);
                ps1.setInt(3, txtCB_Year);
                ps1.setInt(4, txtCB_Month);
                ps1.setInt(5, rs.getInt("RECEIPT_NO"));
                rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    xml =
 xml + "<acq_Vou_NO>" + rs1.getInt("VOUCHER_NO") + "</acq_Vou_NO>";
                    xml =
 xml + "<acq_roll_NO>" + rs1.getInt("ACQ_ROLL_NO") + "</acq_roll_NO>";
                    xml =
 xml + "<disbur_off_id>" + rs1.getInt("DISBURSING_OFFICE_CODE") +
   "</disbur_off_id>";
                    xml =
 xml + "<disbur_off_name>" + rs1.getString("OFFICE_NAME") +
   "</disbur_off_name>";
                    xml =
 xml + "<emp_id>" + rs1.getInt("EMPLOYEE_CODE") + "</emp_id>";
                    xml =
 xml + "<emp_disgn>" + rs1.getString("emp_disgn") + "</emp_disgn>";
                    xml =
 xml + "<tot_Acq_amt>" + rs1.getString("tot_Acq_amt") + "</tot_Acq_amt>";
                    count++;
                }

                if (count == 0)
                    xml = xml + "<flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";

            } catch (SQLException e) {
                System.out.println("exception " + e);
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        String strCommand = "";
        Calendar c;
        Connection con = null;
        PreparedStatement ps = null;

        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

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
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        try {

            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        if (strCommand.equalsIgnoreCase("Add")) {
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode =
                0, cmbOffice_code = 0;

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

            txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
            txtCB_Month =
                    Integer.parseInt(request.getParameter("txtCB_Month"));


            String Grid_Acq_vouNo[] = request.getParameterValues("Acq_vouNo");
            String Grid_Acq_rollNo[] =
                request.getParameterValues("Acq_rollNo");
            String Grid_Acq_offID[] = request.getParameterValues("Acq_offID");
            String Grid_Acq_empID[] = request.getParameterValues("Acq_empID");
            //String Grid_Acq_Amount[]=request.getParameterValues("Acq_Amount");
            String Grid_DateOf_disburse[] =
                request.getParameterValues("DateOf_disburse");
            String sql_update =
                "update FAS_ACQ_ROLL_TRANSACTION set AMOUNT_DISBURSED_YES_OR_NO='Y',DATE_OF_DISBURSEMENT=? " +
                " where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and " +
                " VOUCHER_NO=? and ACQ_ROLL_NO=? and DISBURSING_OFFICE_CODE=? and EMPLOYEE_CODE=? and AMOUNT_DISBURSED_YES_OR_NO='N'";
            try {
                con.setAutoCommit(false);
                for (int y = 0; y < Grid_Acq_vouNo.length; y++) {
                    int txtVoucher_No = Integer.parseInt(Grid_Acq_vouNo[y]);
                    int Acq_rollNo = Integer.parseInt(Grid_Acq_rollNo[y]);
                    int Acq_offID = Integer.parseInt(Grid_Acq_offID[y]);
                    int Acq_empID = Integer.parseInt(Grid_Acq_empID[y]);
                    System.out.println("values " + txtVoucher_No + " " +
                                       Acq_rollNo + " " + Acq_offID + " " +
                                       Acq_empID);
                    Date DateOf_disburse = null;
                    if (!Grid_DateOf_disburse[y].equalsIgnoreCase("")) {
                        String sd[] = Grid_DateOf_disburse[y].split("/");
                        c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                        java.util.Date d = c.getTime();
                        DateOf_disburse = new Date(d.getTime());
                        ps = con.prepareStatement(sql_update);
                        ps.setDate(1, DateOf_disburse);
                        ps.setInt(2, cmbAcc_UnitCode);
                        ps.setInt(3, cmbOffice_code);
                        ps.setInt(4, txtCB_Year);
                        ps.setInt(5, txtCB_Month);
                        ps.setInt(6, txtVoucher_No);
                        ps.setInt(7, Acq_rollNo);
                        ps.setInt(8, Acq_offID);
                        ps.setInt(9, Acq_empID);
                        int r = ps.executeUpdate();
                        System.out.println("value of r" + r);

                    } else
                        continue;

                }

                con.commit();
                sendMessage(response, "The Disbursement details updated ",
                            "ok");
            } catch (Exception e) {
                sendMessage(response,
                            "The Disbursement details updation failed ", "ok");
                System.out.println("exception +" + e);
                try {
                    con.rollback();
                } catch (Exception e1) {
                    System.out.println("rollback exception" + e1);
                }

            } finally {
                System.out.println("done");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                }
            }
        }
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
