package Servlets.FAS.FAS1.SelfChequeSystem.servlets;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class UDPRegister_ListAll extends HttpServlet {
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


                if (rs.next()) {
                    xml = xml + "<flag>success</flag>";
                    xml =
 xml + "<date_drwal>" + rs.getString("date_drwal") + "</date_drwal>";
                    xml =
 xml + "<tot_cheq_amt>" + rs.getString("tot_cheq_amt") + "</tot_cheq_amt>";
                    xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";
                    xml =
 xml + "<checkdate>" + rs.getString("checkdate") + "</checkdate>"; // used to get the date after 3 months from cheque date

                    System.out.println("rec.. no" + rs.getInt("RECEIPT_NO"));
                } else
                    xml = xml + "<flag>failure</flag>";

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
        Connection con = null;
        CallableStatement cs = null;
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
        if (strCommand.equalsIgnoreCase("Show")) {
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
                txtCB_Year =
                        Integer.parseInt(request.getParameter("txtCB_Year"));
                txtCB_Month =
                        Integer.parseInt(request.getParameter("txtCB_Month"));
            } catch (NumberFormatException e) {
                System.out.println("exc  eption" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);


            String cheq_no = request.getParameter("txtCheque_DD_NO");
            String cheq_date = request.getParameter("txtDrawl_date");
            try {
                con.setAutoCommit(false);
                cs =
  con.prepareCall("call FAS_UDP_REGISTER_PROC(?::numeric,?::numeric,?:numeric,?::numeric,?::numeric)");
                cs.setInt(1, cmbAcc_UnitCode);
                cs.setInt(2, cmbOffice_code);
                cs.setInt(3, txtCB_Year);
                cs.setInt(4, txtCB_Month);
                cs.registerOutParameter(5, java.sql.Types.NUMERIC);
                cs.setNull(5, java.sql.Types.NUMERIC);
                cs.execute();
                //int errcode = cs.getInt(5);
                int errcode = cs.getBigDecimal(5).intValue();
                if (errcode != 0) {
                    System.out.println("ERROR");
                    sendMessage(response,
                                "UDP Register report generation failed ",
                                "ok");
                    return;
                } else {
                    con.commit(); // Here transaction commited

                    System.out.println("NO ERROR");
                    File reportFile = null;
                    reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/SelfChequeSystem/jasper/UDP_Report.jasper"));
                    if (!reportFile.exists())
                        throw new JRRuntimeException("File J not found. The report design must be compiled first.");

                    JasperReport jasperReport =
                        (JasperReport)JRLoader.loadObject(reportFile.getPath());
                    Map map = null;
                    map = new HashMap();

                    map.put("accountingunitid", cmbAcc_UnitCode);
                    map.put("accountofficeid", cmbOffice_code);
                    map.put("txtCB_Year", txtCB_Year);
                    map.put("txtCB_Month", txtCB_Month);
                    map.put("cheq_no", cheq_no);
                    map.put("cheq_date", cheq_date);

                    //  for the purpose of Self-Cheque Acq. Deatails printing--

                    // map.put("cheqNO",cheqNO);
                    // map.put("drawdate",drawdate);

                    JasperPrint jasperPrint =
                        JasperFillManager.fillReport(jasperReport, map, con);
                    System.out.println("upto");
                    String rtype =
                        "PDF"; // request.getParameter("cmbReportType");
                    System.out.println(rtype);
                    if (rtype.equalsIgnoreCase("PDF")) {
                        System.out.println(rtype);
                        byte buf[] =
                            JasperExportManager.exportReportToPdf(jasperPrint);
                        response.setContentType("application/pdf");
                        response.setContentLength(buf.length);
                        // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                        //response.setContentType("application/force-download");

                        response.setHeader("Content-Disposition",
                                           "attachment;filename=\"Receipt.pdf\"");
                        OutputStream out = response.getOutputStream();
                        out.write(buf, 0, buf.length);
                        out.close();
                    }
                }
            } catch (Exception ex) {
                String connectMsg =
                    "Could not create the report " + ex.getMessage() + " uu " +
                    ex.getLocalizedMessage();
                System.out.println(connectMsg);

                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("exception in rollback " + sqle);
                }
                sendMessage(response, "Report creation Failed ", "ok");

                return;
            } finally {
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
                "org/Library/jsps/MessengerOkBack.jsp?message=" + msg +
                "&button=" + bType;
            response.sendRedirect(url);
        } catch (IOException e) {
        }
    }
}
