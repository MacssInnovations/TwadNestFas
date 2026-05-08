package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import java.sql.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Servlet implementation class Beneficiary_DCB_ob
 */
public class Beneficiary_DCB_ob extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Beneficiary_DCB_ob() {
        super();

    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
                                                              IOException {
        response.setContentType(CONTENT_TYPE);
        PreparedStatement ps = null;
        Connection con;
        Controller obj = new Controller();
        String command =
            request.getParameter("command"); //Command ->view,insert etc
        if (command == null || command.equals(""))
            command = "no command";
        int error_flag = 0;
        String count = "0"; // for  Already Record
        String input_value = request.getParameter("input_value"); //input value
        if (input_value == null || input_value.equals(""))
            input_value = "0";
        int row = 0;
        int ins_row = 0;
        String process_code =
            request.getParameter("process_code"); // process code 1->   Select ,2->Meter Select
        if (process_code == null || process_code.equals(""))
            process_code = "0";
        String ben_sno =
            request.getParameter("ben_sno"); // process code 1->   Select ,2->Meter Select
        if (ben_sno == null || ben_sno.equals(""))
            ben_sno = "0";
        String year = obj.setValue("year", request);
        if (year == null || year.equals(""))
            year = "0";
        String divcode = "", METRE_SNO = "";

        divcode = request.getParameter("divcode"); //Command ->view,insert etc
        String xml = "", BENEFICIARY_SNO = "", BENEFICIARY_TYPE_SNO = "";
        if (divcode == null || divcode.equals("0"))
            divcode = "5082";
        String qry = "";
        try {
            con = obj.con();
            obj.createStatement(con);
            HttpSession session = request.getSession(false);
            String userid = "0", Office_id = "0";
            try {
                userid = (String)session.getAttribute("UserId");
            } catch (Exception e) {
                userid = "0";
            }

            if (userid == null) {
                userid = "0";
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }

            Office_id =
                    (obj.isNull(obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID",
                                             "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='" +
                                             userid + "')"), 1));

            if (Office_id.equals("0"))
                Office_id = "0";

            if (command.equals("pdf")) {
                if (process_code.equals("1")) {
                    String ben_type = obj.setValue("ben_type", request);
                    try {
                        Map parameters = new HashMap();
                        parameters.put("OFFICE_ID",
                                       Integer.parseInt(Office_id));
                        parameters.put("BENEFICIARY_TYPE_ID",
                                       Integer.parseInt(ben_type));
                        parameters.put("FIN_YEAR", Integer.parseInt(year));

                        String path =
                            getServletContext().getRealPath("/WEB-INF/ReportSrc/Ob.jasper");
                        JasperPrint jasperPrint =
                            JasperFillManager.fillReport(path, parameters,
                                                         con);
                        System.out.println("Report is Created from Jasper Print...");

                        OutputStream outuputStream =
                            response.getOutputStream();

                        JRExporter exporter = null;

                        response.setContentType("application/pdf");
                        response.setHeader("Content-Disposition",
                                           "attachment; filename=\"Ob.pdf\"");
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
                    }

                }
            } else {


                if (process_code.equals("1"))
                    xml =
 obj.combo_lkup("OFFICE_ID", "OFFICE_NAME", "com_mst_all_offices_view",
                "where DIVISION_OFFICE_ID=" + Office_id +
                " and OFFICE_LEVEL_ID='SD'", 2, "--Select--");

                if (process_code.equals("2"))
                    xml =
 obj.combo_lkup("BEN_TYPE_ID", "BEN_TYPE_DESC", "PMS_DCB_BEN_TYPE",
                "order by BEN_TYPE_ID", 2, "--Select--");
                if ((process_code.equals("3") || process_code.equals("5")) &&
                    command.equals("data")) {
                    String subdiv =
                        request.getParameter("subdiv"); //input value
                    if (subdiv == null || subdiv.equals(""))
                        subdiv = "0";
                    String bentype =
                        request.getParameter("bentype"); //input value
                    if (bentype == null || bentype.equals(""))
                        bentype = "0";
                    // BENEFICIARY_SNO OFFICE ID = METER

                    String dis_value =
                        request.getParameter("dis_value"); //input value
                    if (dis_value == null || dis_value.equals(""))
                        dis_value = "0";

                    String block_value =
                        request.getParameter("block_value"); //input value
                    if (block_value == null || block_value.equals(""))
                        block_value = "0";
                    String sub_process =
                        request.getParameter("sub_process"); //input value
                    if (sub_process == null || sub_process.equals(""))
                        sub_process = "0";
                    String cond = "";
                    if (sub_process.equals("1")) {
                        if (dis_value.equals("0"))
                            cond = "";
                        else
                            cond = " and DISTRICT_CODE=" + dis_value;

                        if (block_value.equals("0"))
                            cond = "";
                        else
                            cond = " and BLOCK_SNO=" + block_value;
                    }

                    xml =
 obj.combo_lkup("BENEFICIARY_SNO", "BENEFICIARY_NAME", "PMS_DCB_MST_BENEFICIARY",
                "where OFFICE_ID=" + Office_id +
                "  and  BENEFICIARY_TYPE_ID=" + bentype + " " + cond +
                " order by BENEFICIARY_NAME", 1, "");
                    // xml=obj.combo_lkup("BENEFICIARY_SNO", "BENEFICIARY_NAME", "PMS_DCB_MST_BENEFICIARY", "where  BENEFICIARY_TYPE_ID="+bentype+" order by BENEFICIARY_NAME");
                    //   obj.testQry(xml);
                }
                String MONTH = obj.setValue("month", request);
                String YEAR = obj.setValue("year", request);
                BENEFICIARY_SNO = obj.setValue("BENEFICIARY_SNO", request);


                if (process_code.equals("5") && command.equals("show")) {
                    BENEFICIARY_SNO = obj.setValue("BENEFICIARY_SNO", request);
                    count = "0";
                    count =
obj.getValue("PMS_DCB_OB_YEARLY", "count(*)", "where FIN_YEAR=" + year +
             " and BENEFICIARY_SNO=" + BENEFICIARY_SNO);
                    String obsno =
                        obj.isNull(obj.getValue("PMS_DCB_OB_YEARLY", "BENEFICIARY_OB_SNO",
                                                "where FIN_YEAR=" + year +
                                                " and BENEFICIARY_SNO=" +
                                                BENEFICIARY_SNO), 1);

                    xml = "<result>";
                    xml +=
"<status>" + count + "</status><obsno>" + obsno + "</obsno>";
                    xml += "</result>";
                    //obj.testQry(xml);
                }

                if (process_code.equals("4") && command.equals("data")) {
                    String smonth = obj.setValue("month", request);

                    /*String select_query="select SCH.DESCRIPTION,MET.SCHEME_SNO,MET.METRE_SNO,MET.METRE_LOCATION,MET.METRE_INIT_READING," +
								"case METRE_FIXED when 'y' Then 'Yes' when 'Y' Then 'Yes' " +
								"when 'n' Then 'No' when 'N' Then 'No'" +
								"END as METRE_FIXED, case METRE_WORKING when 'y' Then 'Yes'" +
								"when 'Y' Then 'Yes'" +
								"when 'n' Then 'No'" +
								"when 'N' Then 'No'" +
								"When null Then 'NR'" +
								"END as METRE_WORKING" +
								" from   PMS_DCB_MST_BENEFICIARY_METRE  MET ,  " +
								"PMS_MST_SCHEMES SCH where MET.BENEFICIARY_SNO =1 " +
								"and SCH.SCHEME_ID=MET.SCHEME_SNO";
								*/
                    String select_query =
                        "select SCH.SCH_NAME, MET.ALLOTED_QTY,MET.SCHEME_SNO,MET.METRE_SNO,MET.METRE_LOCATION,MET.METRE_INIT_READING," +
                        "case METRE_FIXED when 'y' Then 'Yes' when 'Y' Then 'Yes' " +
                        "when 'n' Then 'No' when 'N' Then 'No'" +
                        "END as METRE_FIXED, case METRE_WORKING when 'y' Then 'Yes'" +
                        "when 'Y' Then 'Yes'" + "when 'n' Then 'No'" +
                        "when 'N' Then 'No'" + "When null Then 'NR'" +
                        "END as METRE_WORKING," + "MIN_BILL_QTY," +
                        "METRE_TYPE " +
                        " from   PMS_DCB_MST_BENEFICIARY_METRE  MET,PMS_SCH_MASTER SCH    " +
                        " where MET.BENEFICIARY_SNO=" + ben_sno +
                        " and METRE_SNO not in (select METRE_SNO from PMS_DCB_TRN_MONTHLY_PR where BENEFICIARY_SNO=" +
                        ben_sno + " and  MONTH=" + smonth + " and  YEAR=" +
                        year + ")" + "and SCH.SCH_SNO=MET.SCHEME_SNO " +
                        " order by METRE_SNO";

                    //obj.testQry(select_query);

                    xml = "<result>";
                    int i = 0;
                    Connection con1 = obj.con();
                    Statement stmt = con1.createStatement();
                    ResultSet rs = stmt.executeQuery(select_query);
                    String msno = "", mfixed = "", mworking = "", lastmonth =
                        "";
                    while (rs.next()) {
                        i++;
                        msno = "";
                        lastmonth = "";
                        msno = rs.getString("METRE_SNO");
                        mworking =
                                obj.isNull(rs.getString("METRE_WORKING"), 2);

                        if (mworking.equals(""))
                            mworking = "No";
                        mfixed = obj.isNull(rs.getString("METRE_FIXED"), 2);
                        ////////obj.testQry("mfixed"+mfixed);
                        if (mworking.equals("Yes") || mworking.equals("yes"))
                            lastmonth =
                                    obj.getValue("PMS_DCB_TRN_MONTHLY_PR", "METRE_CLOSING_READING",
                                                 "where BENEFICIARY_SNO=" +
                                                 ben_sno + " and METRE_SNO=" +
                                                 msno + " and MONTH=" +
                                                 (Integer.parseInt(MONTH) -
                                                  1) + " and YEAR=" + YEAR);

                        if ((lastmonth == null) || lastmonth.equals(""))
                            lastmonth = "0";
                        // no entry from pervious month

                        if (lastmonth == "0" && mworking.equals("Yes"))
                            lastmonth = rs.getString("METRE_INIT_READING");

                        xml +=
"<sno>" + msno + "</sno><METRE_LOCATION>" + rs.getString("METRE_LOCATION") +
 "</METRE_LOCATION>";
                        xml += "<METRE_FIXED>" + mfixed + "</METRE_FIXED>";
                        xml +=
"<METRE_TYPE>" + obj.isNull(rs.getString("METRE_TYPE"), 1) + "</METRE_TYPE>";

                        xml +=
"<METRE_INIT_READING>" + obj.isNull(lastmonth, 1) + "</METRE_INIT_READING>";
                        xml +=
"<METRE_WORKING>" + mworking + "</METRE_WORKING>";
                        xml +=
"<ALLOTED_QTY>" + obj.isNull(rs.getString("ALLOTED_QTY"), 1) +
 "</ALLOTED_QTY>";
                        xml +=
"<SCHEME_NAME>" + obj.isNull(rs.getString("SCH_NAME"), 1) + "</SCHEME_NAME>";

                    }

                    if (i == 0)
                        xml += "<status>no record for entry</status>";
                    else
                        xml += "<status>" + i + "</status>";
                    xml += "</result>";
                    con1.close();
                } else if (process_code.equals("4") && command.equals("add")) {
                    xml = "<result>";

                    int maxsno =
                        obj.getMax("PMS_DCB_OB_YEARLY", "BENEFICIARY_OB_SNO",
                                   "");
                    String OB_MAINTENANCE_CHARGES =
                        obj.setValue("OB_MAINTENANCE_CHARGES", request);
                    String ADDN_UPTO_PMTH_MCHARGES =
                        obj.setValue("ADDN_UPTO_PMTH_MCHARGES", request);
                    String COLN_UPTO_PMTH_MAINT =
                        obj.setValue("COLN_UPTO_PMTH_MAINT", request);
                    String OB_YESTER_YR_upto_2004 =
                        obj.setValue("OB_YESTER_YR_upto_2004", request);
                    String OB_YESTER_YR_2004_CY =
                        obj.setValue("OB_YESTER_YR_2004_CY", request);
                    String OB_WATER_CHARGES =
                        obj.setValue("OB_WATER_CHARGES", request);
                    String COLN_UPTO_PMTH_YESTER_YR =
                        obj.setValue("COLN_UPTO_PMTH_YESTER_YR", request);
                    String COLN_UPTO_PMTH_WCHARGES =
                        obj.setValue("COLN_UPTO_PMTH_WCHARGES", request);


                    String OFFICE_ID = obj.setValue("OFFICE_ID", request);
                    year = obj.setValue("year", request);
                    BENEFICIARY_TYPE_SNO =
                            obj.setValue("BENEFICIARY_TYPE_SNO", request);
                    count = "0";
                    count =
obj.getValue("PMS_DCB_OB_YEARLY", "count(*)", "where FIN_YEAR=" + year +
             " and BENEFICIARY_SNO=" + BENEFICIARY_SNO);

                    qry = "";
                    qry =
 "insert into PMS_DCB_OB_YEARLY ( BENEFICIARY_OB_SNO" + ",BENEFICIARY_SNO" +
   ",OFFICE_ID" + ",FIN_YEAR" + ",OB_MAINTENANCE_CHARGES" +
   ",ADDN_UPTO_PMTH_MCHARGES" + ",COLN_UPTO_PMTH_MAINT" +
   ",OB_YESTER_YR_upto_2004" + ",OB_YESTER_YR_2004_CY," + "OB_WATER_CHARGES," +
   "COLN_UPTO_PMTH_YESTER_YR," + "COLN_UPTO_PMTH_WCHARGES" +
   ",UPDATED_BY_USER_ID," + "UPDATED_DATE)";


                    qry +=
" values (" + maxsno + "," + BENEFICIARY_SNO + "," + OFFICE_ID + "," + year +
 "," + OB_MAINTENANCE_CHARGES + "," + ADDN_UPTO_PMTH_MCHARGES + "," +
 COLN_UPTO_PMTH_MAINT + ",";
                    qry +=
OB_YESTER_YR_upto_2004 + "," + OB_YESTER_YR_2004_CY + "," + OB_WATER_CHARGES +
 "," + COLN_UPTO_PMTH_YESTER_YR + "," + COLN_UPTO_PMTH_WCHARGES + ",'" +
 userid + "',SYSTIMESTAMP)";
                    row = 0;
                    xml = "<result>";

                    if (Integer.parseInt(count) == 0) {
                        row = obj.setUpd(qry);
                        if (row > 0) {
                            xml +=
"<status>1</status><maxsno>" + maxsno + "</maxsno>";
                            xml +=
"<BENEFICIARY_NAME>" + obj.getValue("PMS_DCB_MST_BENEFICIARY",
                                    "BENEFICIARY_NAME",
                                    " where BENEFICIARY_SNO=" +
                                    BENEFICIARY_SNO) + "</BENEFICIARY_NAME>";
                            xml +=
"<OFFICE_NAME>" + obj.getValue("com_mst_all_offices_view", "OFFICE_NAME",
                               "where DIVISION_OFFICE_ID=" + divcode +
                               " and OFFICE_LEVEL_ID='DN'") + "</OFFICE_NAME>";
                            xml +=
"<BENEFICIARY_TYPE>" + obj.getValue("PMS_DCB_BEN_TYPE", "BEN_TYPE_DESC",
                                    " where BEN_TYPE_ID=" +
                                    BENEFICIARY_TYPE_SNO) +
 "</BENEFICIARY_TYPE>";
                            xml += "<entryfor>DCB</entryfor>";
                        }
                    }

                    else {
                        xml += "<status>0</status>";
                    }

                    xml += "</result>";
                } else if (process_code.equals("10") &&
                           command.equals("add")) {
                    xml = "<result>";
                    String eOB_MAINTENANCE_CHARGES =
                        obj.setValue("OB_MAINTENANCE_CHARGES", request);
                    String eADDN_UPTO_PMTH_MCHARGES =
                        obj.setValue("ADDN_UPTO_PMTH_MCHARGES", request);
                    String eCOLN_UPTO_PMTH_MAINT =
                        obj.setValue("COLN_UPTO_PMTH_MAINT", request);
                    String eOB_YESTER_YR_upto_2004 =
                        obj.setValue("OB_YESTER_YR_upto_2004", request);
                    String eOB_YESTER_YR_2004_CY =
                        obj.setValue("OB_YESTER_YR_2004_CY", request);
                    String eOB_WATER_CHARGES =
                        obj.setValue("OB_WATER_CHARGES", request);
                    String eCOLN_UPTO_PMTH_YESTER_YR =
                        obj.setValue("COLN_UPTO_PMTH_YESTER_YR", request);
                    String eCOLN_UPTO_PMTH_WCHARGES =
                        obj.setValue("COLN_UPTO_PMTH_WCHARGES", request);
                    String selsno = obj.setValue("selsno", request);

                    qry = "";
                    qry =
 "update PMS_DCB_OB_YEARLY set  OB_MAINTENANCE_CHARGES=" +
   eOB_MAINTENANCE_CHARGES + "" + ",ADDN_UPTO_PMTH_MCHARGES=" +
   eADDN_UPTO_PMTH_MCHARGES + "," + " COLN_UPTO_PMTH_MAINT=" +
   eCOLN_UPTO_PMTH_MAINT + "," + "OB_YESTER_YR_upto_2004=" +
   eOB_YESTER_YR_upto_2004 + "," + "OB_YESTER_YR_2004_CY=" +
   eOB_YESTER_YR_2004_CY + "," + "OB_WATER_CHARGES=" + eOB_WATER_CHARGES +
   "," + "COLN_UPTO_PMTH_YESTER_YR=" + eCOLN_UPTO_PMTH_YESTER_YR + "," +
   "COLN_UPTO_PMTH_WCHARGES=" + eCOLN_UPTO_PMTH_WCHARGES + "" +
   " where BENEFICIARY_OB_SNO =" + selsno;

                    int up_row = obj.setUpd(qry);

                    xml += "<up_row>" + up_row + "</up_row></result>";
                } else if (process_code.equals("6") && command.equals("add")) {
                    xml = "<result>";
                    String OB_INTEREST_AMT_MAINT =
                        obj.setValue("OB_INTEREST_AMT_MAINT", request);
                    String COLN_UPTO_PMTH_INTEREST_MAINT =
                        obj.setValue("COLN_UPTO_PMTH_INTEREST_MAINT", request);
                    String OB_INTEREST_AMT_WC =
                        obj.setValue("OB_INTEREST_AMT_WC", request);
                    String INT_UPTO_CUR_MONTH_CALC =
                        obj.setValue("INT_UPTO_CUR_MONTH_CALC", request);
                    String COLN_UPTO_PMTH_INTEREST_WC =
                        obj.setValue("COLN_UPTO_PMTH_INTEREST_WC", request);
                    String obsno = obj.setValue("obsno", request);
                    BENEFICIARY_SNO = obj.setValue("BENEFICIARY_SNO", request);
                    BENEFICIARY_TYPE_SNO =
                            obj.setValue("BENEFICIARY_TYPE_SNO", request);

                    String upqry =
                        "update PMS_DCB_OB_YEARLY set OB_INTEREST_AMT_MAINT=" +
                        OB_INTEREST_AMT_MAINT +
                        ",COLN_UPTO_PMTH_INTEREST_MAINT=" +
                        COLN_UPTO_PMTH_INTEREST_MAINT +
                        ",OB_INTEREST_AMT_WC=" + OB_INTEREST_AMT_WC +
                        ", INT_UPTO_CUR_MONTH_CALC=" +
                        INT_UPTO_CUR_MONTH_CALC +
                        ",COLN_UPTO_PMTH_INTEREST_WC=" +
                        COLN_UPTO_PMTH_INTEREST_WC +
                        " where BENEFICIARY_OB_SNO=" + obsno;
                    //	obj.testQry(upqry);
                    int urow = obj.setUpd(upqry);

                    xml = "<result>";

                    if (urow > 0) {

                        xml +=
"<status>1</status><obsno>" + obsno + "</obsno>";
                        xml +=
"<BENEFICIARY_NAME>" + obj.getValue("PMS_DCB_MST_BENEFICIARY",
                                    "BENEFICIARY_NAME",
                                    " where BENEFICIARY_SNO=" +
                                    BENEFICIARY_SNO) + "</BENEFICIARY_NAME>";

                        xml +=
"<BENEFICIARY_TYPE>" + obj.getValue("PMS_DCB_BEN_TYPE", "BEN_TYPE_DESC",
                                    " where BEN_TYPE_ID=" +
                                    BENEFICIARY_TYPE_SNO) +
 "</BENEFICIARY_TYPE>";

                        xml += "<entryfor>Interest</entryfor>";

                    }
                    xml += "</result>";
                } else if (process_code.equals("12") &&
                           command.equals("add")) {
                    xml += "<result>";
                    qry = "";

                    String MONPR_SNO =
                        obj.setValue("MONPR_SNO".trim(), request);
                    String netunit = obj.setValue("netunit".trim(), request);
                    String rowcnt_meter1 =
                        obj.setValue("rowcnt_meter", request);
                    qry =
 "update PMS_DCB_MONTHLY_PR set NET_CONSUMED=? where MONPR_SNO=?";
                    // obj.testQry("update PMS_DCB_MONTHLY_PR set NET_CONSUMED="+netunit+" where MONPR_SNO="+MONPR_SNO);
                    ps = con.prepareStatement(qry);
                    ps.setInt(1, Integer.parseInt(netunit));
                    ps.setInt(2, Integer.parseInt(MONPR_SNO));
                    ps.execute();
                    qry = "";
                    for (row = 1; row <= Integer.parseInt(rowcnt_meter1);
                         row++) {
                        METRE_SNO = "";
                        String METRE_INITIAL_READING =
                            obj.setValue("METRE_INITIAL_READING" + row,
                                         request);
                        METRE_SNO = obj.setValue("METRE_SNO" + row, request);

                        String METRE_CLOSING_READING =
                            obj.setValue("METRE_CLOSING_READING" + row,
                                         request);
                        String QTY_CONSUMED =
                            obj.setValue("QTY_CONSUMED" + row, request);
                        String PR_SNO = obj.setValue("PR_SNO" + row, request);
                        String ALLOTED_QTY =
                            obj.setValue("ALLOTED_QTY" + row, request);
                        String EXCESS_QTY =
                            obj.setValue("EXCESS_QTY" + row, request);
                        String METRE_FIXED =
                            obj.setValue("METRE_FIXED" + row, request);
                        String METRE_WORKING =
                            obj.setValue("METRE_WORKING" + row, request);
                        qry =
 "update PMS_DCB_TRN_MONTHLY_PR  set " + "METRE_INITIAL_READING=?," +
   "METRE_CLOSING_READING=?," + "QTY_CONSUMED=?," + "ALLOTED_QTY=?," +
   "EXCESS_QTY=?," + "METRE_FIXED=?," + "METRE_WORKING=? where PR_SNO=? ";
                        /* obj.testQry("update PMS_DCB_TRN_MONTHLY_PR  set " +"METRE_INITIAL_READING="+METRE_INITIAL_READING+"," +
							"METRE_CLOSING_READING="+METRE_CLOSING_READING+"," +
							"QTY_CONSUMED="+QTY_CONSUMED+"," +
							"ALLOTED_QTY="+ALLOTED_QTY+"," +
							"EXCESS_QTY="+EXCESS_QTY+"," +
							"METRE_FIXED="+METRE_FIXED+"," +
							"METRE_WORKING="+METRE_WORKING+" where PR_SNO="+PR_SNO+"");
							*/


                        ps = con.prepareStatement(qry);
                        ps.setInt(1, Integer.parseInt(METRE_INITIAL_READING));
                        ps.setInt(2, Integer.parseInt(METRE_CLOSING_READING));
                        ps.setInt(3, Integer.parseInt(QTY_CONSUMED));
                        ps.setInt(4, Integer.parseInt(ALLOTED_QTY));
                        ps.setInt(5, Integer.parseInt(EXCESS_QTY));
                        ps.setString(6, METRE_FIXED);
                        ps.setString(7, METRE_WORKING);
                        ps.setInt(8, Integer.parseInt(PR_SNO));
                        ps.execute();
                        String short_qry = "";
                        if (METRE_WORKING.equals("Y")) {
                            short_qry =
                                    ",METRE_INIT_READING=" + METRE_INITIAL_READING;
                        } else {
                            short_qry = ",METRE_INIT_READING=0";
                        }
                        String wstatus = "";

                        if (METRE_WORKING.equals("Y"))
                            wstatus =
                                    " set  METRE_W_WEF='" + MONTH + "/" + YEAR +
                                    "',METRE_NW_WEF='', METRE_WORKING='" +
                                    METRE_WORKING + "'" + short_qry;
                        else
                            wstatus =
                                    " set  METRE_NW_WEF='" + MONTH + "/" + YEAR +
                                    "',METRE_W_WEF='', METRE_WORKING='" +
                                    METRE_WORKING + "'" + short_qry;
                        qry = "";
                        qry =
 "update PMS_DCB_MST_BENEFICIARY_METRE " + wstatus + "  where METRE_SNO=" +
   METRE_SNO;
                        obj.testQry(qry);
                        int ins_row1 = obj.setUpd(qry);

                    }

                    xml += "</result>";

                } else if ((process_code.equals("7") ||
                            process_code.equals("9")) &&
                           command.equals("add")) {

                    xml += "<result>";
                    String rowcnt_meter =
                        obj.setValue("rowcnt_meter", request);
                    row = 0;
                    ins_row = 0;

                    String OFFICE_ID = obj.setValue("OFFICE_ID", request);
                    BENEFICIARY_SNO = obj.setValue("BENEFICIARY_SNO", request);
                    String netunit = obj.setValue("netunit", request);
                    BENEFICIARY_TYPE_SNO =
                            obj.setValue("BENEFICIARY_TYPE_SNO", request);
                    String selected_item =
                        obj.setValue("selected_item", request);
                    int temp = 0;
                    for (row = 1; row <= Integer.parseInt(rowcnt_meter);
                         row++) {
                        if (process_code.equals("9"))
                            temp = Integer.parseInt(selected_item);
                        else
                            temp = row;


                        int PR_SNO =
                            obj.getMax("PMS_DCB_TRN_MONTHLY_PR", "PR_SNO", "");

                        qry = "";

                        String SUBDIV_OFFICE_ID =
                            obj.setValue("SUBDIV_OFFICE_ID", request);
                        METRE_SNO = obj.setValue("METRE_SNO" + temp, request);
                        String METRE_INITIAL_READING =
                            obj.setValue("METRE_INITIAL_READING" + temp,
                                         request);
                        String METRE_CLOSING_READING =
                            obj.setValue("METRE_CLOSING_READING" + temp,
                                         request);
                        String QTY_CONSUMED =
                            obj.setValue("QTY_CONSUMED" + temp, request);

                        String ALLOTED_QTY =
                            obj.setValue("ALLOTED_QTY" + temp, request);
                        String EXCESS_QTY =
                            obj.setValue("EXCESS_QTY" + temp, request);
                        String METRE_FIXED =
                            obj.setValue("METRE_FIXED" + temp, request);
                        String METRE_WORKING =
                            obj.setValue("METRE_WORKING" + temp, request);
                        String PRVMETRE_WORKING =
                            obj.setValue("PRVMETRE_WORKING" + temp, request);
                        qry =
 "insert into   PMS_DCB_TRN_MONTHLY_PR   (PR_SNO," + "BENEFICIARY_SNO," +
   "OFFICE_ID,SUBDIV_OFFICE_ID," + "MONTH," + "YEAR," + "METRE_SNO," +
   "METRE_INITIAL_READING," + "METRE_CLOSING_READING," +
   "QTY_CONSUMED,ALLOTED_QTY," + "EXCESS_QTY,METRE_FIXED," +
   "METRE_WORKING,UPDATED_BY_USER_ID," +
   "UPDATED_DATE,PROCESS_FLAG) values (" + PR_SNO + "," + "" +
   BENEFICIARY_SNO + "," + OFFICE_ID + "," + SUBDIV_OFFICE_ID + "," + MONTH +
   "," + "" + YEAR + "," + METRE_SNO + "," + METRE_INITIAL_READING + "," +
   METRE_CLOSING_READING + "," + "" + QTY_CONSUMED + "," + ALLOTED_QTY + "," +
   EXCESS_QTY + ",'" + METRE_FIXED + "'," + "'" + METRE_WORKING + "','" +
   userid + "',SYSTIMESTAMP,'CR')";


                        count = "0";
                        count =
obj.getValue("PMS_DCB_TRN_MONTHLY_PR", "count(*)",
             "where BENEFICIARY_SNO=" + BENEFICIARY_SNO + " and METRE_SNO=" +
             METRE_SNO + " and MONTH=" + MONTH + " and YEAR=" + YEAR);

                        if (!QTY_CONSUMED.equals("0") ||
                            BENEFICIARY_TYPE_SNO.equals("6")) {
                            if (count.equals("0")) {
                                String his_qry = "", short_qry = "";
                                // Previous working status change
                                String temp_flag =
                                    (PRVMETRE_WORKING.equals("Yes")) ? "Y" :
                                    "N";

                                if (!temp_flag.equals(METRE_WORKING)) {
                                    // Record Move to history Table
                                    his_qry =
                                            " insert into PMS_DCB_MST_BEN_METRE_HIST select * from PMS_DCB_MST_BENEFICIARY_METRE where METRE_SNO=" +
                                            METRE_SNO;

                                    // Working Status no to yes -> get initial reading from user
                                    // Working Status yes to no  -> set initial  to zero

                                    if (METRE_WORKING.equals("Y")) {
                                        short_qry =
                                                ",METRE_INIT_READING=" + METRE_INITIAL_READING;
                                    } else {
                                        short_qry = ",METRE_INIT_READING=0";
                                    }

                                }

                                int history_row1 = 0;
                                try {
                                    history_row1 = obj.setUpd(his_qry);


                                } catch (Exception e) {
                                    xml +=
"<error>Problem Occur in  Meter Move </error>";

                                }

                                // obj.testQry(qry);
                                ins_row += obj.setUpd(qry);

                                qry = "";


                                //  Monthly value store
                                count = "0";
                                count =
obj.getValue("PMS_DCB_MONTHLY_PR", "count(*)",
             "where BENEFICIARY_SNO=" + BENEFICIARY_SNO + " and MONTH=" +
             MONTH + " and YEAR=" + YEAR);


                                //  obj.testQry(count);
                                // No Previouse Record
                                if (count.equals("0")) {
                                    qry = "";
                                    int MONPR_SNO =
                                        obj.getMax("PMS_DCB_MONTHLY_PR",
                                                   "MONPR_SNO", "");
                                    qry = "insert into PMS_DCB_MONTHLY_PR ";
                                    qry +=
"(MONPR_SNO,MONTH,YEAR,BENEFICIARY_SNO,NET_CONSUMED,UPDATED_BY_USER_ID,UPDATED_DATE,PROCESS_FLAG)";
                                    qry +=
" values (" + MONPR_SNO + "," + MONTH + "," + YEAR + "," + BENEFICIARY_SNO +
 "," + netunit + ",'" + userid + "',SYSTIMESTAMP,'CR')";
                                    int ins_row_mr = obj.setUpd(qry);
                                    count = "0";
                                } else {
                                    qry = "";
                                    String table_MONPR_SNO =
                                        obj.getValue("PMS_DCB_MONTHLY_PR",
                                                     "MONPR_SNO",
                                                     "where BENEFICIARY_SNO=" +
                                                     BENEFICIARY_SNO +
                                                     " and MONTH=" + MONTH +
                                                     " and YEAR=" + YEAR);
                                    String table_NET_CONSUMED =
                                        obj.getValue("PMS_DCB_MONTHLY_PR",
                                                     "NET_CONSUMED",
                                                     "where MONPR_SNO=" +
                                                     table_MONPR_SNO);
                                    table_NET_CONSUMED =
                                            Integer.toString(Integer.parseInt(table_NET_CONSUMED) +
                                                             Integer.parseInt(QTY_CONSUMED));
                                    qry =
 "update PMS_DCB_MONTHLY_PR set NET_CONSUMED=" + table_NET_CONSUMED +
   " where MONPR_SNO=" + table_MONPR_SNO;
                                    //	 obj.testQry(qry);
                                    int pr_row1 = obj.setUpd(qry);
                                }


                                String wstatus = "";

                                if (METRE_WORKING.equals("Y"))
                                    wstatus =
                                            " set  METRE_W_WEF='" + MONTH + "/" +
                                            YEAR +
                                            "',METRE_NW_WEF='', METRE_WORKING='" +
                                            METRE_WORKING + "'" + short_qry;
                                else
                                    wstatus =
                                            " set  METRE_NW_WEF='" + MONTH + "/" +
                                            YEAR +
                                            "',METRE_W_WEF='', METRE_WORKING='" +
                                            METRE_WORKING + "'" + short_qry;


                                qry = "";
                                qry =
 "update PMS_DCB_MST_BENEFICIARY_METRE " + wstatus + "  where METRE_SNO=" +
   METRE_SNO;

                                int ins_row1 = obj.setUpd(qry);
                            }
                        }
                    }

                    if (ins_row != 0) {


                        xml +=
"<BENEFICIARY_NAME>" + obj.getValue("PMS_DCB_MST_BENEFICIARY",
                                    "BENEFICIARY_NAME",
                                    " where BENEFICIARY_SNO=" +
                                    BENEFICIARY_SNO) + "</BENEFICIARY_NAME>";
                        xml +=
"<BENEFICIARY_TYPE>" + obj.getValue("PMS_DCB_BEN_TYPE", "BEN_TYPE_DESC",
                                    " where BEN_TYPE_ID=" +
                                    BENEFICIARY_TYPE_SNO) +
 "</BENEFICIARY_TYPE>";
                        xml += "<MONTH>" + MONTH + "</MONTH>";
                        xml +=
"<BENEFICIARY_SNO>" + BENEFICIARY_SNO + "</BENEFICIARY_SNO>";
                        xml += "<YEAR>" + YEAR + "</YEAR>";
                        xml +=
"<selected_item>" + selected_item + "</selected_item><status>" + ins_row +
 "</status> ";
                        xml += "<ins_row>" + ins_row + "</ins_row>";
                    }
                    //////obj.testQry("Status - 7 ");
                    xml += "</result>";
                }


                PrintWriter pr = response.getWriter();
                pr.write(xml);
                //obj.testQry(xml);
                pr.flush();
                pr.close();

            }
            obj.conClose(con);


        } catch (Exception e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
            error_flag = 1;

        }
        if (error_flag == 1) {
            String erxml = "";
            erxml = "<result>404<status>Server Problem</status></result>";
            PrintWriter er = response.getWriter();
            er.write(erxml);
            er.flush();
        }

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected

    void doPost(HttpServletRequest request,
                HttpServletResponse response) throws ServletException,
                                                     IOException {
        // TODO Auto-generated method stub
    }


}
