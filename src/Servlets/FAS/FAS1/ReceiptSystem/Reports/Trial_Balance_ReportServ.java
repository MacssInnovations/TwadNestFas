package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import java.util.HashMap;
import java.util.Map;

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

public class Trial_Balance_ReportServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {


        /**
         * Variables Declaration
         */
        Connection connection = null;
        Statement statement = null;


        /**
         * Database Connection
         */
        try {
            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }


        /**
         * Session Checking
         */
        response.setContentType(CONTENT_TYPE);
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


        /**
        *  Get Parameters
        */

        /** ---------------------------------- PART I ----------------------------------------------*/

        String ALL_UNITS = "";

        /** Get paramter of  'All Units including Head Office'  */
        try {
            ALL_UNITS = request.getParameter("ALL_UNITS");
            System.out.println("ALL_UNITS-->" + ALL_UNITS);
        } catch (Exception e) {
            System.out.println("All Units (Including Head Office) is not selected ");
        }


        /** ---------------------------------- PART I (A)  ----------------------------------------------*/

        /** Get parameter of 'Head Office Only'  */
        String HO_ONLY = null;
        try {
            HO_ONLY = request.getParameter("H0_ONLY");
            System.out.println("H0_ONLY-->" + HO_ONLY);
        } catch (Exception e) {
            System.out.println("Head Office Only is not selected ");
        }


        /** ---------------------------------- PART I (B)  ----------------------------------------------*/
        /** Get Units which are selected under Head Office Only Category */
        String[] HO_UNITS = null;

        try {
            HO_UNITS = request.getParameterValues("HO_UNITS");
        } catch (Exception e) {
            System.out.println("No one Units Under Head Office is not selected ");
        }

        /** Get Individual Unit ID as in String array and finally make it as a single string with comma separator */
        String ho_units_str = "";
        int ho_units_len = 0;
        try {
            ho_units_len = HO_UNITS.length;
        } catch (Exception e) {
            System.out.println("Cant Find HO UNITS Length " + e);
        }

        for (int i = 0; i < ho_units_len; i++) {
            ho_units_str = ho_units_str + HO_UNITS[i];
            ho_units_str = ho_units_str + ",";
        }
        ho_units_str = ho_units_str + "0";
        System.out.println("ho_units_str---->" + ho_units_str);


        /** ----------------------------------- PART II ---------------------------------------------*/

        /** Selection Made either REGION or CIRCLE or UNITS */
        String Reg_Cir_Uni_Sel = "";
        try {
            Reg_Cir_Uni_Sel = request.getParameter("Sel_Opt");
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Selection either REGION or CIRCLE or UNITS --->" +
                           Reg_Cir_Uni_Sel);

        /** ----------------------------------- PART II (A) ---------------------------------------------*/

        /** Selection made either All_Units_Except_HO */
        String All_Units_Except_HO = "";
        try {
            All_Units_Except_HO = request.getParameter("All_Units_Except_HO");
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("All_Units_Except_HO-->" + All_Units_Except_HO);


        /** ------------------------------------ PART III REGION SELECTION ----------------------------*/


        /** Select Regions */
        String REGION[] = null;
        try {
            REGION = request.getParameterValues("REGION");
            System.out.println("3");
        } catch (Exception e) {
            System.out.println("No Regions Selected ");
        }


        /** Get Individual Region Unit ID as in String array and finally make it as a single string with comma separator */
        String REGION_str = "";
        int region_length = 0;

        /** Find Region array Length */
        try {
            region_length = REGION.length;
        } catch (Exception e) {
            System.out.println("Cant find Region Length " + e.getMessage());
        }


        for (int i = 0; i < region_length; i++) {
            REGION_str = REGION_str + REGION[i];
            REGION_str = REGION_str + ",";
        }
        REGION_str = REGION_str + "0";
        System.out.println("REGION_str---->" + REGION_str);


        /** ------------------------------------ PART III CIRCLE SELECTION ----------------------------*/

        /** Select Regions */
        String CIRCLE[] = null;
        try {
            CIRCLE = request.getParameterValues("CIRCLE");
        } catch (Exception e) {
            System.out.println("No Circle Selected ");
        }

        /** Get Individual Circle Unit ID as in String array and finally make it as a single string with comma separator */
        String CIRCLE_str = "";
        int circle_length = 0;

        try {
            circle_length = CIRCLE.length;
        } catch (Exception e) {
            System.out.println("Cant find Circle Length " + e);
        }

        for (int i = 0; i < circle_length; i++) {
            CIRCLE_str = CIRCLE_str + CIRCLE[i];
            CIRCLE_str = CIRCLE_str + ",";
        }
        CIRCLE_str = CIRCLE_str + "0";
        System.out.println("CIRCLE_str---->" + CIRCLE_str);

        /** ------------------------------------ PART III UNIT SELECTION ----------------------------*/

        /** Select Regions */
        String UNIT[] = null;
        try {
            UNIT = request.getParameterValues("UNIT");
        } catch (Exception e) {
            System.out.println("No UNIT Selected ");
        }

        /** Get Individual Unit ID as in String array and finally make it as a single string with comma separator */
        String UNIT_str = "";
        int unit_length = 0;

        try {
            unit_length = UNIT.length;
        } catch (Exception e) {
            System.out.println("Cant get Unit Length " + e);
        }

        for (int i = 0; i < unit_length; i++) {
            UNIT_str = UNIT_str + UNIT[i];
            UNIT_str = UNIT_str + ",";
        }
        UNIT_str = UNIT_str + "0";
        System.out.println("UNIT_str---->" + UNIT_str);

        /**---------------------------------------------------------------------------------------*/

        /** Get Cashbook Year */
        String CashBook_Year = "";
        String CashBook_Month = "";

        try {
            CashBook_Year = request.getParameter("txtCB_Year");
        } catch (Exception e) {
            System.out.println(" Cant get Cashbook Year " + e);
        }

        /** Get Cashbook Month */
        try {
            CashBook_Month = request.getParameter("txtCB_Month");
        } catch (Exception e) {
            System.out.println("Cant get Cashbook Month " + e);
        }


        /** Variables Declaration */
        int CashBookYear = 0;
        int CashBookMonth = 0;


        /** Convert String data into Integer Data */
        try {
            CashBookYear = Integer.parseInt(CashBook_Year);
            CashBookMonth = Integer.parseInt(CashBook_Month);

        } catch (Exception e) {
            System.out.println("Exception in Converting Integer:" + e);
        }


        String monthInWords = "";
        if (CashBookMonth == 1)
            monthInWords = "January";
        else if (CashBookMonth == 2)
            monthInWords = "February";
        else if (CashBookMonth == 3)
            monthInWords = "March";
        else if (CashBookMonth == 4)
            monthInWords = "April";
        else if (CashBookMonth == 5)
            monthInWords = "May";
        else if (CashBookMonth == 6)
            monthInWords = "June";
        else if (CashBookMonth == 7)
            monthInWords = "July";
        else if (CashBookMonth == 8)
            monthInWords = "August";
        else if (CashBookMonth == 9)
            monthInWords = "September";
        else if (CashBookMonth == 10)
            monthInWords = "October";
        else if (CashBookMonth == 11)
            monthInWords = "November";
        else if (CashBookMonth == 12)
            monthInWords = "December";


        /**
         *  Report Generation
         */

        try {
            String WHERE_STR = "";
            File reportFile = null;
            try {
                System.out.println("1");
                System.out.println("ALL_UNITS-->" + ALL_UNITS);
                System.out.println("H0_ONLY-->" + HO_ONLY);
                System.out.println("Check it -->" +
                                   !ho_units_str.equalsIgnoreCase(""));

                if (ALL_UNITS != null && !ALL_UNITS.trim().equals("")) {
                    if (ALL_UNITS.equalsIgnoreCase("ALL_UNITS")) {
                        System.out.println("2");
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TB_Download/TB_Download_AllUnits.jasper"));
                        WHERE_STR = " ";
                    }
                } else if (HO_ONLY != null && !HO_ONLY.trim().equals("")) {
                    System.out.println("Samy");
                    if (HO_ONLY.equalsIgnoreCase("HO_ONLY")) {
                        System.out.println("3");
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TB_Download/TB_Download_AllUnits.jasper"));
                        WHERE_STR = " and accounting_unit_id in (3,5, 6, 7 ) ";
                    }
                }

                else if (ho_units_len > 0) {
                    System.out.println("4");
                    reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TB_Download/TB_Download_AllUnits.jasper"));
                    WHERE_STR =
                            "and accounting_unit_id in ( " + ho_units_str + " ) ";
                }


                else if (Reg_Cir_Uni_Sel.equalsIgnoreCase("UNITS")) {
                    System.out.println("5");
                    if ("All_Units_Except_HO".equalsIgnoreCase(All_Units_Except_HO)) {
                        System.out.println("6");
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TB_Download/TB_Download_AllUnits.jasper"));
                        WHERE_STR =
                                " and accounting_unit_id in ( select accounting_unit_id from fas_mst_acct_units  where accounting_unit_office_id != 5000  ) ";
                    } else if (!UNIT_str.equalsIgnoreCase("null")) {
                        System.out.println("7");
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TB_Download/TB_Download_AllUnits.jasper"));
                        WHERE_STR =
                                " and accounting_unit_id in ( " + UNIT_str +
                                "," + CIRCLE_str + "," + REGION_str + ")";
                    }
                }

                else if (Reg_Cir_Uni_Sel.equalsIgnoreCase("REGION")) {
                    System.out.println("8");
                    System.out.println("9");
                    reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TB_Download/TB_Download_AllUnits.jasper"));
                    WHERE_STR =
                            " and  accounting_unit_id  in ( " + REGION_str +
                            "," + CIRCLE_str + "," + UNIT_str + ")";
                } else if (Reg_Cir_Uni_Sel.equalsIgnoreCase("CIRCLE")) {
                    reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TB_Download/TB_Download_AllUnits.jasper"));
                    WHERE_STR =
                            " and accounting_unit_id in ( " + CIRCLE_str + "," +
                            UNIT_str + ")";
                }
            } catch (Exception e) {
                System.out.println(e);
            }


            try {
                if (!reportFile.exists())
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");

                JasperReport jasperReport =
                    (JasperReport)JRLoader.loadObject(reportFile.getPath());
                Map map = null;
                map = new HashMap();
                map.put("cashbookyear", CashBookYear);
                map.put("cashbookmonth", CashBookMonth);
                map.put("monthvalue", monthInWords);
                map.put("WHERE_STR", WHERE_STR);

                System.out.println("CashBookYear" + CashBookYear);
                System.out.println("monthInWords" + monthInWords);
                System.out.println("where-str -->" + WHERE_STR);

                JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, map,
                                                 connection);


                String rtype = "EXCEL";

                if (rtype.equalsIgnoreCase("HTML")) {
                    response.setContentType("text/html");

                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Challan.html\"");
                    PrintWriter out = response.getWriter();
                    JRHtmlExporter exporter = new JRHtmlExporter();
                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                                          false);
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                          jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER,
                                          out);
                    exporter.exportReport();
                    out.flush();
                    out.close();
                } else if (rtype.equalsIgnoreCase("PDF")) {
                    byte buf[] =
                        JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
                } else if (rtype.equalsIgnoreCase("EXCEL")) {
                    System.out.println("test1");
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Challan.xls\"");
                    JRXlsExporter exporterXLS = new JRXlsExporter();
                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                                             jasperPrint);
                    System.out.println("test2");
                    ByteArrayOutputStream xlsReport =
                        new ByteArrayOutputStream();
                    exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
                                             xlsReport);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                                             Boolean.FALSE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
                                             Boolean.TRUE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                                             Boolean.FALSE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                                             Boolean.TRUE);
                    exporterXLS.exportReport();
                    System.out.println("test3");
                    byte[] bytes;
                    bytes = xlsReport.toByteArray();
                    ServletOutputStream ouputStream =
                        response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    System.out.println("test4");
                    ouputStream.flush();
                    ouputStream.close();

                } else if (rtype.equalsIgnoreCase("TXT")) {
                    response.setContentType("text/plain");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"TB.txt\"");

                    JRTextExporter exporter = new JRTextExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                          jasperPrint);
                    ByteArrayOutputStream txtReport =
                        new ByteArrayOutputStream();
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                          txtReport);
                    exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
                                          new Integer(200));
                    exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
                                          new Integer(50));
                    exporter.exportReport();

                    byte[] bytes;
                    bytes = txtReport.toByteArray();
                    ServletOutputStream ouputStream =
                        response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

                }

            } catch (Exception ex) {
                String connectMsg =
                    "Could not create the report " + ex.getMessage();
                System.out.println(connectMsg);

                sendMessage(response, "Unable to display the EXCEL file",
                            "ok");
            }


        } catch (Exception e) {
            System.out.println("Exception in Main:" + e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println("catch:" + e1);
            }
            String msg = "Trial Balance Has failed to Update";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");
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
            System.out.println("ERROR");
        }
    }


}
