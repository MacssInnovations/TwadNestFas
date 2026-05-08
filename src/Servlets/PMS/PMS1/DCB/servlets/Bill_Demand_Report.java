package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.text.DecimalFormat;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * Servlet implementation class Bill_Demand_Report
 */
public class Bill_Demand_Report extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";
    private static Connection con = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bill_Demand_Report() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
                                                              IOException {
        // TODO Auto-generated method stub

        response.setContentType(CONTENT_TYPE);
        Controller obj = new Controller();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String Rs_Value = "";
        try {
            con = obj.con();
            obj.createStatement(con);
        } catch (Exception e) {
            obj.testQry(e.toString());
        }

        String command =
            request.getParameter("command"); //Command ->view,insert etc
        if (command == null || command.equals(""))
            command = "no command";
        DecimalFormat decForRate = new DecimalFormat("0.00");

        String MULTIPLY_FACTOR = "", QTY_CONSUMED = "";

        obj.testQry("command" + command);
        String input_value = request.getParameter("input_value"); //input value
        if (input_value == null || input_value.equals(""))
            input_value = "0";

        String process_code =
            request.getParameter("process_code"); // process code 1-> Ben Select ,2->Meter Select
        if (process_code == null || process_code.equals(""))
            process_code = "0";
        String qry = "", xml = "<result>", ben_type = "";

        double net_consumption = 0;
        double net_consumption_value = 0;
        String billsno = input_value;
        if (command.equals("pdf")) {

            if (process_code.equals("1")) {

                try {
                    Map parameters = new HashMap();
                    parameters.put("billsno", billsno);

                    String ctxpath = null;
                    String BENEFICIARY_SNO =
                        obj.getValue("PMS_DCB_TRN_BILLING_DMD",
                                     "BENEFICIARY_SNO",
                                     "where BILL_SNO=" + billsno);
                    String BENEFICIARY_TYPE_SNO =
                        obj.getValue("PMS_DCB_MST_BENEFICIARY",
                                     "BENEFICIARY_TYPE_ID",
                                     "where BENEFICIARY_SNO=" +
                                     BENEFICIARY_SNO);

                    if (BENEFICIARY_TYPE_SNO.equalsIgnoreCase(""))
                        BENEFICIARY_TYPE_SNO = "0";


                    String path = "";

                    if (Integer.parseInt(BENEFICIARY_TYPE_SNO) > 6) {
                        path =
getServletContext().getRealPath("/WEB-INF/ReportSrc/bill_demand.jasper");
                        ctxpath =
                                path.substring(0, path.lastIndexOf("bill_demand.jasper"));
                    } else {
                        path =
getServletContext().getRealPath("/WEB-INF/ReportSrc/bill_demand_LB.jasper");
                        ctxpath =
                                path.substring(0, path.lastIndexOf("bill_demand_LB.jasper"));
                    }

                    parameters.put("ctxpath", ctxpath);

                    JasperPrint jasperPrint =
                        JasperFillManager.fillReport(path, parameters, con);
                    System.out.println("Report is Created from Jasper Print...");

                    OutputStream outuputStream = response.getOutputStream();

                    JRExporter exporter = null;

                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition",
                                       "attachment; filename=\"bill_demand.pdf\"");
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                          jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                          outuputStream);
                    exporter.exportReport();
                    System.out.println("The File is Downloaded");
                    outuputStream.close();
                } catch (JRException e) {
                    throw new ServletException(e);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        } else {


            PrintWriter pr = response.getWriter();
            if (command.equals("show")) {
                qry =
 "select " + "DMD.BILL_SNO," + "DMD.OFFICE_ID," + "DMD.BENEFICIARY_SNO," +
   "to_char(DMD.BILL_PERIOD_FROM,'dd/mm/yyyy') as  BILL_PERIOD_FROM," +
   "to_char(DMD.BILL_PERIOD_TO,'dd/mm/yyyy') as  BILL_PERIOD_TO," +
   "DMD.BILL_MONTH," + "DMD.BILL_YEAR," + "DMD.DIV_BILL_NO," +
   "DMD.NET_CONSUMPTION," +
   "to_char(DMD.BILLING_DT,'dd/mm/yyyy') as BILLING_DT," +
   "BEN.BENEFICIARY_NAME," + "BEN.BENEFICIARY_TYPE_ID," +
   "BEN.BILLING_ADDRESS1," + "BEN.BILLING_ADDRESS2," +
   "BEN.BILLING_ADDRESS3," + "BEN.BILLING_PIN_CODE," + "DIV.OFFICE_NAME ," +
   "METRE.METRE_LOCATION " + ",PR.METRE_INITIAL_READING" +
   ",PR.METRE_CLOSING_READING " + ",PR.QTY_CONSUMED" +
   ",METRE.MULTIPLY_FACTOR" + ",METRE.MIN_BILL_QTY," + "METRE.ALLOTED_QTY" +
   ",INT.INT_RATE," + "DMD.WC_INT_COLN," + " DMD.WC_OB," + "DMD.INT_CALC," +
   "SCH.SCH_NAME," + "STYP.SCH_TYPE_DESC," + "DMD.MAINT_OB," +
   "DMD.INT_CALC," + "DMD.WC_COLN," + "DMD.MAINT_COLN," + "WC.TARIFF_RATE," +
   "WC.EXCESS_RATE ," + "WC.EXCESS_QTY ,WC.EXCESS_AMT " + " from " + "(" +
   "(" + "select BILL_SNO," + "OFFICE_ID," + "BENEFICIARY_SNO," +
   "BILL_PERIOD_FROM," + "BILL_PERIOD_TO," + "BILL_MONTH," + "BILL_YEAR," +
   "DIV_BILL_NO," + "NET_CONSUMPTION," + "BILLING_DT ," + "WC_INT_COLN," +
   "WC_OB," + "WC_COLN," + "MAINT_OB," + "INT_CALC," + "MAINT_COLN " +
   " from " + " PMS_DCB_TRN_BILLING_DMD   where BILL_SNO=" + billsno +
   ") DMD " + "join(" + "select" + "   BENEFICIARY_SNO," +
   "    BENEFICIARY_NAME," + "     BENEFICIARY_TYPE_ID," +
   "      DISTRICT_CODE," + " BLOCK_SNO," + "  BILLING_ADDRESS1," +
   "   BILLING_ADDRESS2," + "    BILLING_ADDRESS3," +
   "     BILLING_PIN_CODE " + " from " + "       PMS_DCB_MST_BENEFICIARY" +
   ") BEN" + " on BEN.BENEFICIARY_SNO=DMD.BENEFICIARY_SNO " + " join(" +
   "select" + "   OFFICE_NAME," + "    OFFICE_ID " + " from  " +
   "     com_mst_all_offices_view " + ")div" +
   " on div.OFFICE_ID=DMD.OFFICE_ID " + " join(" + " select " + " BILL_SNO, " +
   "  PR_SNO" + "  ,TARIFF_RATE," + "  EXCESS_RATE," + "  EXCESS_QTY," +
   "  EXCESS_AMT, " + "  METRE_SNO " + " from " + "  PMS_DCB_WC_BILLING " +
   " where " + "  BILL_SNO=" + billsno + " )WC " +
   " on WC.BILL_SNO=DMD.BILL_SNO " + " join " + " ( " + " select  " +
   "  PR_SNO," + "   " + " METRE_INITIAL_READING, " +
   " METRE_CLOSING_READING, " + " QTY_CONSUMED " + " from  " +
   " PMS_DCB_TRN_MONTHLY_PR " + " ) PR " + " on PR.PR_SNO=WC.PR_SNO " +
   "       join " + " ( " + " select " + " BENEFICIARY_SNO, " + " METRE_SNO," +
   "MULTIPLY_FACTOR, " + "MIN_BILL_QTY" + ",METRE_LOCATION ," +
   "TARIFF_RATE," + "EXCESS_TARIFF_RATE," + "SCH_TYPE_ID," + "SCHEME_SNO," +
   "ALLOTED_QTY " + " from  " + " PMS_DCB_MST_BENEFICIARY_METRE " +
   " )METRE " +
   " on WC.METRE_SNO=METRE.METRE_SNO AND METRE.BENEFICIARY_SNO=DMD.BENEFICIARY_SNO" +
   " join ( " + "select " + "BENEFICIARY_TYPE," + "INT_RATE" + " from " +
   "PMS_DCB_MST_INT ) INT " +
   "  on INT.BENEFICIARY_TYPE=BEN.BENEFICIARY_TYPE_ID " + "JOIN (" +
   "SELECT " + " SCH_TYPE_ID," + "SCH_TYPE_DESC" + "  FROM " +
   "PMS_SCH_LKP_TYPE" + "  )STYP " +
   "  ON METRE.SCH_TYPE_ID=STYP.SCH_TYPE_ID " + " join   (  " + " SELECT " +
   "  SCH_SNO, " + "  SCH_NAME " + "  FROM PMS_SCH_MASTER " + "   )SCH " +
   "   ON METRE.SCHEME_SNO=SCH.SCH_SNO" + " " + ")";
                try {
                    obj.testQry(qry);
                    rs = obj.getRS(qry);
                    double total_vlaue = 0.0;

                    while (rs.next()) {

                        xml +=
"<bill_sno>" + obj.isNull(rs.getString("BILL_SNO"), 2) + "</bill_sno>";
                        xml +=
"<OFFICE_NAME>" + obj.isNull(rs.getString("OFFICE_NAME"), 2) +
 "</OFFICE_NAME>";
                        xml +=
"<BENEFICIARY_SNO>" + obj.isNull(rs.getString("BENEFICIARY_SNO"), 2) +
 "</BENEFICIARY_SNO>";
                        xml +=
"<BILL_PERIOD_FROM>" + obj.isNull(rs.getString("BILL_PERIOD_FROM"), 2) +
 "</BILL_PERIOD_FROM>";
                        xml +=
"<BILL_PERIOD_TO>" + obj.isNull(rs.getString("BILL_PERIOD_TO"), 2) +
 "</BILL_PERIOD_TO>";
                        xml +=
"<BILL_MONTH>" + obj.isNull(rs.getString("BILL_MONTH"), 2) + "</BILL_MONTH>";
                        xml +=
"<BILL_YEAR>" + obj.isNull(rs.getString("BILL_YEAR"), 2) + "</BILL_YEAR>";
                        xml +=
"<NET_CONSUMPTION>" + obj.isNull(rs.getString("NET_CONSUMPTION"), 2) +
 "</NET_CONSUMPTION>";
                        xml +=
"<DIV_BILL_NO>" + obj.isNull(rs.getString("DIV_BILL_NO"), 2) +
 "</DIV_BILL_NO>";
                        xml +=
"<BENEFICIARY_TYPE_ID>" + obj.isNull(rs.getString("BENEFICIARY_TYPE_ID"), 2) +
 "</BENEFICIARY_TYPE_ID>";

                        MULTIPLY_FACTOR =
                                obj.isNull(rs.getString("MULTIPLY_FACTOR"), 2);
                        QTY_CONSUMED =
                                obj.isNull(rs.getString("QTY_CONSUMED"), 2);
                        xml +=
"<BILLING_DT>" + obj.isNull(rs.getString("BILLING_DT"), 2) + "</BILLING_DT>";
                        xml +=
"<BENEFICIARY_NAME>" + obj.isNull(rs.getString("BENEFICIARY_NAME"), 2) +
 "</BENEFICIARY_NAME>";
                        xml +=
"<BILLING_ADDRESS1>" + obj.isNull(rs.getString("BILLING_ADDRESS1"), 2) +
 "</BILLING_ADDRESS1>";
                        xml +=
"<BILLING_ADDRESS2>" + obj.isNull(rs.getString("BILLING_ADDRESS2"), 2) +
 "</BILLING_ADDRESS2>";
                        xml +=
"<BILLING_ADDRESS3>" + obj.isNull(rs.getString("BILLING_ADDRESS3"), 2) +
 "</BILLING_ADDRESS3>";
                        xml +=
"<BILLING_PIN_CODE>" + obj.isNull(rs.getString("BILLING_PIN_CODE"), 2) +
 "</BILLING_PIN_CODE>";
                        xml +=
"<METRE_LOCATION>" + obj.isNull(rs.getString("METRE_LOCATION"), 2).trim() +
 "</METRE_LOCATION>";
                        xml +=
"<METRE_CLOSING_READING>" + obj.isNull(rs.getString("METRE_CLOSING_READING"),
                                       2) + "</METRE_CLOSING_READING>";
                        xml +=
"<METRE_INITIAL_READING>" + obj.isNull(rs.getString("METRE_INITIAL_READING"),
                                       2) + "</METRE_INITIAL_READING>";
                        xml +=
"<MULTIPLY_FACTOR>" + decForRate.format(Double.parseDouble(MULTIPLY_FACTOR)) +
 "</MULTIPLY_FACTOR>";
                        xml +=
"<TARIFF_RATE>" + obj.isNull(rs.getString("TARIFF_RATE"), 2) +
 "</TARIFF_RATE>";
                        xml +=
"<EXCESS_TARIFF_RATE>" + obj.isNull(rs.getString("EXCESS_RATE"), 2) +
 "</EXCESS_TARIFF_RATE>";
                        xml +=
"<QTY_CONSUMED>" + QTY_CONSUMED + "</QTY_CONSUMED>";
                        xml +=
"<INT_RATE>" + obj.isNull(rs.getString("INT_RATE"), 2) + "</INT_RATE>";
                        xml +=
"<WC_OB>" + obj.isNull(rs.getString("WC_OB"), 2) + "</WC_OB>";
                        xml +=
"<WC_INT_COLN>" + obj.isNull(rs.getString("WC_INT_COLN"), 2) +
 "</WC_INT_COLN>";
                        xml +=
"<MAINT_OB>" + obj.isNull(rs.getString("MAINT_OB"), 2) + "</MAINT_OB>";
                        xml +=
"<WC_COLN>" + obj.isNull(rs.getString("WC_COLN"), 2) + "</WC_COLN>";
                        xml +=
"<MAINT_COLN>" + obj.isNull(rs.getString("MAINT_COLN"), 2) + "</MAINT_COLN>";
                        xml +=
"<MIN_BILL_QTY>" + obj.isNull(rs.getString("MIN_BILL_QTY"), 2) +
 "</MIN_BILL_QTY>";
                        xml +=
"<ALLOTED_QTY>" + obj.isNull(rs.getString("ALLOTED_QTY"), 2) +
 "</ALLOTED_QTY>";
                        xml +=
"<EXCESS_QTY>" + obj.isNull(rs.getString("EXCESS_QTY"), 2) + "</EXCESS_QTY>";
                        xml +=
"<EXCESS_AMT>" + obj.isNull(rs.getString("EXCESS_AMT"), 2) + "</EXCESS_AMT>";


                        xml +=
"<INT_CALC>" + obj.isNull(rs.getString("INT_CALC"), 2) + "</INT_CALC>";
                        xml +=
"<SCH_TYPE_DESC>" + obj.isNull(rs.getString("SCH_TYPE_DESC"), 2) +
 "</SCH_TYPE_DESC>";
                        xml +=
"<SCH_NAME>" + obj.isNull(rs.getString("SCH_NAME"), 2) + "</SCH_NAME>";
                        ben_type =
                                obj.isNull(rs.getString("BENEFICIARY_TYPE_ID"),
                                           2);
                        //************************************ Rs Calculated


                        double cal =
                            Double.parseDouble(MULTIPLY_FACTOR) * Double.parseDouble(QTY_CONSUMED);
                        double MIN_BILL_QTY =
                            Double.parseDouble(obj.isNull(rs.getString("MIN_BILL_QTY"),
                                                          2));


                        //***********************************

                        if (Integer.parseInt(ben_type) > 6) {
                            if (MIN_BILL_QTY > cal) {
                                total_vlaue =
                                        MIN_BILL_QTY * Double.parseDouble(obj.isNull(rs.getString("TARIFF_RATE"),
                                                                                     1));
                                xml +=
"<total_consumption>" + decForRate.format(MIN_BILL_QTY) +
 "</total_consumption>";
                                xml +=
"<total_vlaue>" + decForRate.format(total_vlaue) + "</total_vlaue>";
                                net_consumption += MIN_BILL_QTY;
                                net_consumption_value += total_vlaue;
                            } else {
                                total_vlaue =
                                        cal * Double.parseDouble(obj.isNull(rs.getString("TARIFF_RATE"),
                                                                            1));
                                xml +=
"<total_consumption>" + decForRate.format(cal) + "</total_consumption>";
                                xml +=
"<total_vlaue>" + decForRate.format(total_vlaue) + "</total_vlaue>";
                                net_consumption += cal;
                                net_consumption_value += total_vlaue;
                            }
                        } else {
                            total_vlaue =
                                    cal * Double.parseDouble(obj.isNull(rs.getString("TARIFF_RATE"),
                                                                        1));
                            xml +=
"<total_consumption>" + decForRate.format(cal) + "</total_consumption>";
                            xml +=
"<total_vlaue>" + decForRate.format(total_vlaue) + "</total_vlaue>";

                            net_consumption += cal;
                            net_consumption_value += total_vlaue;
                        }


                        //Rs_Value=obj.getValue("dual", "to_char(to_date('"+(int)((net_consumption_value+Double.parseDouble(obj.isNull(rs.getString("INT_CALC"), 2))))+"','J'), 'JSP')","");


                    }

                    qry = "";
                    qry =
 "  select \n" + "	SCH.SCH_NAME,\n" + "	WCB.SCHEME_SNO,\n" +
   "	WCB.SCH_TYPE_ID,\n" + "	WCB.QTY ," + "   STYP.SCH_TYPE_DESC" + "   \n" +
   " from \n" + " (\n" + " (\n" + " select\n" + "	sch_type_id,\n" +
   "	scheme_sno," + "	SUM(QTY_CONSUMED+EXCESS_QTY) AS QTY\n" + " from \n" +
   "	PMS_DCB_WC_BILLING " + " where " + "	pms_dcb_wc_billing.bill_sno= " +
   billsno + " GROUP BY " + "	sch_type_id ," + "	scheme_sno	\n" + " )WCB \n" +
   " join\n" + " ( \n" + " select\n" + " 	sch_sno,\n" + "	SCH_NAME \n" +
   " from " + "	PMS_SCH_MASTER\n" + ")SCH\n" +
   " ON SCH.sch_sno=WCB.scheme_sno\n" + ")\n" + " JOIN (" + "SELECT " +
   " SCH_TYPE_ID," + "SCH_TYPE_DESC" + "  FROM " + "PMS_SCH_LKP_TYPE" +
   "  )STYP " + "  ON WCB.sch_type_id=STYP.SCH_TYPE_ID ";

                    obj.testQry(qry);
                    rs = obj.getRS(qry);

                    int sch_row = 0;
                    while (rs.next()) {
                        sch_row++;
                        xml +=
"<L_SCH_NAME>" + obj.isNull(rs.getString("SCH_NAME"), 2) + "</L_SCH_NAME>";
                        xml +=
"<L_SCH_TYPE_DESC>" + obj.isNull(rs.getString("SCH_TYPE_DESC"), 2) +
 "</L_SCH_TYPE_DESC>";
                        xml +=
"<L_QTY>" + obj.isNull(rs.getString("QTY"), 2) + "</L_QTY>";
                    }

                    xml += "<sch_row>" + sch_row + "</sch_row>";


                    xml +=
"<net_consumption>" + net_consumption + "</net_consumption>";
                    xml +=
"<net_consumption_value>" + decForRate.format(net_consumption_value) +
 "</net_consumption_value>";


                    obj.testQry(xml);

                    xml += "</result>";
                    pr.write(xml);
                    pr.flush();
                    pr.close();

                    obj.conClose(con);
                } catch (Exception e) {
                    System.out.println("" + e);

                }

            }
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
                                                               IOException {
        // TODO Auto-generated method stub
    }

}
