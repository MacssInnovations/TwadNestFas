package Servlets.FAS.FAS1.SelfChequeSystem.servlets;

import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_DETAILS;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class SelfCheque_Edit_load_Vou extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
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
        ResultSet rs = null, rs2 = null, rs3 = null, rs4 = null, rs5 = null;
        PreparedStatement ps = null, ps3 = null, ps2 = null, ps4 = null, ps5 =
            null;

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
        Date txtCrea_date = null;
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

        if (strCommand.equalsIgnoreCase("load_Voucher_No")) {

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            String xml = "";
            xml = "<response><command>load_Voucher_No</command>";

            try {
                String[] sd = request.getParameter("txtCrea_date").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                txtCrea_date = new Date(d.getTime());
                System.out.println("txtCrea_date " + txtCrea_date);

                String txtAUTHORIZED_TO =
                    request.getParameter("txtAUTHORIZED_TO");

                //ps=con.prepareStatement("select RECEIPT_NO from FAS_FUND_RECEIPT_BY_HO where ACCOUNTING_UNIT_ID=?  and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_STATUS!='C'");
                ps =
  con.prepareStatement("select i.RECEIPT_NO from FAS_RECEIPT_MASTER i,FAS_CROSS_REFERENCE c where " +
                       " i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and i.RECEIPT_DATE=? and i.RECEIPT_STATUS!='C' and RECEIPT_TYPE='C' and CREATED_BY_MODULE='SC' " +
                       " and i.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and i.ACCOUNTING_FOR_OFFICE_ID=c.ACCOUNTING_FOR_OFFICE_ID " +
                       " and i.CASHBOOK_YEAR=c.CASHBOOK_YEAR and i.CASHBOOK_MONTH=c.CASHBOOK_MONTH and i.RECEIPT_NO=c.VOUCHER_NO " +
                       " and c.CHANGE_NO=0 and c.AUTHORIZED_TO=? and DOC_TYPE='SC'");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtCrea_date);
                ps.setString(4, txtAUTHORIZED_TO);
                rs = ps.executeQuery();

                int count = 0;
                while (rs.next()) {

                    xml =
 xml + "<Rec_No>" + rs.getInt("RECEIPT_NO") + "</Rec_No>";
                    count++;
                }
                if (count == 0)
                    xml = xml + "<flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";
                System.out.println("count  " + count);
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load VOUCHER." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);

        }

        else if (strCommand.equalsIgnoreCase("load_Voucher_Details")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            String xml = "";
            xml = "<response><command>load_Voucher_Details</command>";
            int txtReceipt_No = 0;
            // Date txtCrea_date;

            try {
                txtReceipt_No =
                        Integer.parseInt(request.getParameter("txtReceipt_No"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtReceipt_No " + txtReceipt_No);

            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);

            try {
                ps =
  con.prepareStatement("select bk.BANK_NAME ||'-' ||br.BRANCH_NAME || '-' ||coalesce (br.CITY_TOWN_NAME,'') as bankNAME,m.BANK_ID,m.BRANCH_ID,m.ACCOUNT_NO,trim(to_char(m.TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT, m.REMARKS ,t.ACCOUNT_HEAD_CODE,t.CHEQUE_DD_NO,t.CHEQUE_DD_DATE,to_char(t.CHEQUE_DD_DATE,'DD/MM/YYYY') as getCheq_date" +
                       " from  FAS_RECEIPT_MASTER m,FAS_RECEIPT_TRANSACTION t,FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br  where " +
                       " m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH " +
                       " and  m.RECEIPT_NO=t.RECEIPT_NO and  RECEIPT_TYPE='C' and CREATED_BY_MODULE='SC'  and m.ACCOUNTING_UNIT_ID=? and m.ACCOUNTING_FOR_OFFICE_ID=? and  m.RECEIPT_DATE=? and m.RECEIPT_NO=? and br.BANK_ID=m.BANK_ID and br.BRANCH_ID=m.BRANCH_ID and br.BANK_ID=bk.BANK_ID");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtCrea_date);
                ps.setInt(4, txtReceipt_No);
                rs = ps.executeQuery();

                if (rs.next()) {
                    xml = xml + "<flag>success</flag>";

                    xml =
 xml + "<main_tot_amt>" + rs.getString("TOTAL_AMOUNT") + "</main_tot_amt>";
                    xml =
 xml + "<cheq_no>" + rs.getString("CHEQUE_DD_NO") + "</cheq_no>";
                    xml =
 xml + "<getCheq_date>" + rs.getString("getCheq_date") + "</getCheq_date>";
                    xml =
 xml + "<main_remark>" + rs.getString("REMARKS") + "</main_remark>";
                    xml =
 xml + "<MasHeadCode>" + rs.getString("ACCOUNT_HEAD_CODE") + "</MasHeadCode>";
                    xml =
 xml + "<accNo>" + rs.getString("ACCOUNT_NO") + "</accNo>";
                    xml = xml + "<bk_id>" + rs.getInt("BANK_ID") + "</bk_id>";
                    xml =
 xml + "<br_id>" + rs.getInt("BRANCH_ID") + "</br_id>";
                    xml =
 xml + "<bankNAME>" + rs.getString("bankNAME") + "</bankNAME>";

                }
                rs.close();
                ps.close();
                System.out.println("receipt over");


                int acq_yes_no = 0;


                String sql_payMas =
                    "select CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,to_char(PAYMENT_DATE,'DD/MM/YYYY') as paymentDate,ACCOUNT_HEAD_CODE,BANK_ID,BRANCH_ID,ACCOUNT_NO," +
                    "trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as tot_pay_amt,REMARKS,NO_OF_ACQ_ROLLS from FAS_PAYMENT_MASTER " +
                    "where ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and  PAYMENT_DATE=? and SELFCHEQUE_RECEIPT_NO=? and PAYMENT_TYPE='C' and CREATED_BY_MODULE='SC' and PAYMENT_STATUS!='C' order by VOUCHER_NO";
                ps2 = con.prepareStatement(sql_payMas);
                ps2.setInt(1, cmbAcc_UnitCode);
                ps2.setInt(2, cmbOffice_code);
                ps2.setDate(3, txtCrea_date);
                ps2.setInt(4, txtReceipt_No);
                rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    xml =
 xml + "<Acq_roll_number>" + rs2.getInt("NO_OF_ACQ_ROLLS") +
   "</Acq_roll_number>";
                    xml =
 xml + "<paymentVrNo>" + rs2.getString("VOUCHER_NO") + "</paymentVrNo>";
                    xml =
 xml + "<tot_pay_amt>" + rs2.getString("tot_pay_amt") + "</tot_pay_amt>";
                    xml =
 xml + "<remarks>" + rs2.getString("REMARKS") + "</remarks>";


                    ps4 =
 con.prepareStatement("select VOUCHER_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE ,SUB_LEDGER_CODE, " +
                      "trim(to_char(AMOUNT,'99999999999999.99')) as  tot_payDetails_amt, PARTICULARS  from FAS_PAYMENT_TRANSACTION where ACCOUNTING_UNIT_ID=? and " +
                      "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?");
                    ps4.setInt(1, cmbAcc_UnitCode);
                    ps4.setInt(2, cmbOffice_code);
                    ps4.setInt(3, rs2.getInt("CASHBOOK_YEAR"));
                    ps4.setInt(4, rs2.getInt("CASHBOOK_MONTH"));
                    ps4.setInt(5, rs2.getInt("VOUCHER_NO"));
                    rs4 = ps4.executeQuery();

                    // PAYMENT details part retrival
                    while (rs4.next()) {
                        xml =
 xml + "<pay_det_VoucherNo>" + rs4.getInt("VOUCHER_NO") +
   "</pay_det_VoucherNo>";
                        xml =
 xml + "<AHcode>" + rs4.getInt("ACCOUNT_HEAD_CODE") + "</AHcode>";

                        ps3 =
 con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                        ps3.setInt(1, rs4.getInt("ACCOUNT_HEAD_CODE"));
                        rs3 = ps3.executeQuery();

                        if (rs3.next())
                            xml =
 xml + "<AHdesc>" + rs3.getString("ACCOUNT_HEAD_DESC") + "</AHdesc>";
                        ps3.close();
                        rs3.close();

                        xml =
 xml + "<CR_DR_ind>" + rs4.getString("CR_DR_INDICATOR") +
   "</CR_DR_ind><SL_Type>" + rs4.getInt("SUB_LEDGER_TYPE_CODE") + "</SL_Type>";
                        if (rs4.getInt("SUB_LEDGER_TYPE_CODE") != 0) {
                            System.out.println("take SL DESC");
                            ps3 =
 con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
                            ps3.setInt(1, rs4.getInt("SUB_LEDGER_TYPE_CODE"));
                            rs3 = ps3.executeQuery();
                            if (rs3.next())
                                xml =
 xml + "<SL_Desc>" + rs3.getString("SUB_LEDGER_TYPE_DESC") + "</SL_Desc>";
                        } else {
                            xml =
 xml + "<SL_Desc>" + null + "</SL_Desc>"; // it also return null value
                            System.out.println("else part  23");
                        }

                        rs3.close();
                        ps3.close();

                        xml =
 xml + "<SL_Code>" + rs4.getInt("SUB_LEDGER_CODE") + "</SL_Code>";

                        if (rs4.getInt("SUB_LEDGER_TYPE_CODE") != 0) {
                            SL_TYPE_CODE_NAME_DETAILS obj_det =
                                new SL_TYPE_CODE_NAME_DETAILS();
                            ResultSet rs_det =
                                obj_det.getResult_Details(cmbAcc_UnitCode,
                                                          cmbOffice_code,
                                                          rs4.getInt("SUB_LEDGER_TYPE_CODE"),
                                                          rs4.getString("SUB_LEDGER_CODE"),
                                                          0);
                            if (rs_det != null) {
                                if (rs_det.next()) {
                                    //System.out.println(rs_det.getString("cid"));
                                    System.out.println(rs_det.getString("cname"));
                                    xml =
 xml + "<desc_type>" + rs_det.getString("cname") + "</desc_type>";
                                }
                                rs_det.close();
                            } else
                                System.out.println("null result set");
                        } else {
                            xml = xml + "<desc_type>" + null + "</desc_type>";
                        }


                        xml =
 xml + "<sub_amount>" + rs4.getString("tot_payDetails_amt") +
   "</sub_amount><sub_part>" + rs4.getString("PARTICULARS") + "</sub_part>";
                    }
                    rs4.close();
                    ps4.close();
                    //  -------------------------  payment details part end

                    //  -------------------------  Acquittance part start

                    if (rs2.getInt("NO_OF_ACQ_ROLLS") > 0) {
                        acq_yes_no =
                                10; // if acq. available to any voucher , it set to 10 (will be used to load acquittance part in .js file)

                        System.out.println("Acquittance");
                        ps5 =
 con.prepareStatement("select a.VOUCHER_NO,a.ACQ_ROLL_NO,a.DISBURSING_OFFICE_CODE,a.EMPLOYEE_CODE,o.OFFICE_NAME,e.EMPLOYEE_NAME ||' - ' ||d.DESIGNATION as emp_disgn" +
                      ",trim(to_char(a.TOTAL_AMOUNT,'99999999999999.99')) as  tot_Acq_amt,a.REMARKS from FAS_ACQ_ROLL_TRANSACTION a," +
                      "HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d, COM_MST_OFFICES o where c.DESIGNATION_ID=d.DESIGNATION_ID  and " +
                      " e.EMPLOYEE_ID=c.EMPLOYEE_ID and c.EMPLOYEE_ID=a.EMPLOYEE_CODE and a.DISBURSING_OFFICE_CODE=o.OFFICE_ID and " +
                      " a.ACCOUNTING_UNIT_ID=? and a.ACCOUNTING_FOR_OFFICE_ID=? and a.CASHBOOK_YEAR=? and a.CASHBOOK_MONTH=? and a.VOUCHER_NO=?");
                        System.out.println("here .. 90");
                        System.out.println("rs2.getInt(\"VOUCHER_NO\").." +
                                           rs2.getInt("VOUCHER_NO"));
                        System.out.println("unit" + cmbAcc_UnitCode);
                        System.out.println("off" + cmbOffice_code);
                        System.out.println("yr" + rs2.getInt("CASHBOOK_YEAR"));
                        System.out.println("mon" +
                                           rs2.getInt("CASHBOOK_MONTH"));
                        System.out.println("vou..no" +
                                           rs2.getInt("VOUCHER_NO"));
                        ps5.setInt(1, cmbAcc_UnitCode);
                        ps5.setInt(2, cmbOffice_code);
                        ps5.setInt(3, rs2.getInt("CASHBOOK_YEAR"));
                        ps5.setInt(4, rs2.getInt("CASHBOOK_MONTH"));
                        ps5.setInt(5, rs2.getInt("VOUCHER_NO"));
                        rs5 = ps5.executeQuery();
                        System.out.println("here 22 ");
                        while (rs5.next()) {
                            xml =
 xml + "<acq_Vou_NO>" + rs5.getInt("VOUCHER_NO") + "</acq_Vou_NO>";
                            xml =
 xml + "<acq_roll_NO>" + rs5.getInt("ACQ_ROLL_NO") + "</acq_roll_NO>";
                            xml =
 xml + "<disbur_off_id>" + rs5.getInt("DISBURSING_OFFICE_CODE") +
   "</disbur_off_id>";
                            xml =
 xml + "<disbur_off_name>" + rs5.getString("OFFICE_NAME") +
   "</disbur_off_name>";
                            xml =
 xml + "<emp_id>" + rs5.getInt("EMPLOYEE_CODE") + "</emp_id>";
                            xml =
 xml + "<emp_disgn>" + rs5.getString("emp_disgn") + "</emp_disgn>";
                            xml =
 xml + "<tot_Acq_amt>" + rs5.getString("tot_Acq_amt") + "</tot_Acq_amt>";
                            xml =
 xml + "<acq_remarks>" + rs5.getString("REMARKS") + "</acq_remarks>";
                        }
                        rs5.close();
                        ps5.close();
                    }

                    //  -------------------------  Acquittance part END

                }
                if (acq_yes_no > 0)
                    xml = xml + "<acq_XML_flag>" + true + "</acq_XML_flag>";
                else
                    xml = xml + "<acq_XML_flag>" + false + "</acq_XML_flag>";

                ps2.close();
                rs2.close();


                xml = xml + "</response>";

                System.out.println("xml full content.........." + xml);

                out.println(xml);

            } catch (Exception e) {
                System.out.println("here error in overall retrival ");
                xml =
 "<response><command>load_Voucher_Details</command><flag>failure</flag>";
            }

        }

    }
}
