package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.text.DecimalFormat;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class Bill_Demand extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";
    private static Connection con = null;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);

        PrintWriter pr = response.getWriter();
        response.setContentType(CONTENT_TYPE);
        Controller obj = new Controller();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        String xml = "", qry = "";
        double amount = 0.0, excessamount = 0.0, netamount = 0.0;
        int row = 0;
        String command =
            request.getParameter("command"); //Command ->view,insert etc
        if (command == null || command.equals(""))
            command = "no command";
        DecimalFormat decForRate = new DecimalFormat("0.00");


        String input_value = request.getParameter("input_value"); //input value
        if (input_value == null || input_value.equals(""))
            input_value = "0";

        String process_code =
            request.getParameter("process_code"); // process code 1-> Ben Select ,2->Meter Select
        if (process_code == null || process_code.equals(""))
            process_code = "0";

        String divcode = "", bentype = "";
        int maxbillno = 0;
        try {
            con = obj.con();
            stmt = con.createStatement();
            obj.createStatement(con);

            // Office id & user id Start
            String Office_id = "0";
            String userid = "0";
            HttpSession session = request.getSession(false);
            userid = (String)session.getAttribute("UserId");
            if (userid == null) {

                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
            Office_id =
                    obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='" +
                                 userid + "')");
            if (Office_id.equals(""))
                Office_id = "0";

            //Office id & user id End


            divcode =
                    obj.setValue("divcode", request); //Command ->view,insert etc
            bentype =
                    obj.setValue("bentype", request); //Command ->view,insert etc
            String month =
                obj.setValue("fmonth", request); //Command ->view,insert etc
            String year =
                obj.setValue("fyear", request); //Command ->view,insert etc

            // Command -> show

            //obj.testQry("process_code"+process_code);
            if (command.equals("show")) {
                // Command -> show  & process_code 0,1 Start

                if (process_code.equals("14")) {
                    xml =
 obj.combo_lkup("BILL_SNO", "DIV_BILL_NO", "PMS_DCB_TRN_BILLING_DMD",
                " where OFFICE_ID=" + Office_id + " and BILL_MONTH=" + month +
                " and BILL_YEAR=" + year + " order by DIV_BILL_NO", 2,
                "--Select--");

                }
                if (process_code.equals("6")) {
                    xml =
 obj.combo_lkup("TYPE_SNO", "TYPE_DESC", "PMS_DCB_OTHERCHARGES", "", 2,
                "--Select--");

                } else if (process_code.equals("0") ||
                           process_code.equals("1")) {

                    xml = "<result>";
                    qry =
 "select BENEFICIARY_NAME,BENEFICIARY_SNO  from PMS_DCB_MST_BENEFICIARY where BENEFICIARY_SNO=" +
   input_value;
                    rs = stmt.executeQuery(qry);
                    if (rs.next()) {
                        row++;
                        xml +=
"<name>" + rs.getString("BENEFICIARY_NAME") + "</name>";
                        xml +=
"<sno>" + rs.getInt("BENEFICIARY_SNO") + "</sno>";
                    }
                    if (row >= 1)
                        xml += "<resultstatus>success</resultstatus>";
                    else
                        xml += "<resultstatus>norecord</resultstatus>";
                    xml += "</result>";
                } else if (process_code.equals("11")) {
                    xml = "<result>";
                    /*String select_query="select SCH.SCH_NAME,MET.METRE_SNO, MET.ALLOTED_QTY,MET.SCHEME_SNO,MET.METRE_SNO,MET.METRE_LOCATION,MET.METRE_INIT_READING," +
					"case METRE_FIXED when 'y' Then 'Yes' when 'Y' Then 'Yes' " +
					"when 'n' Then 'No' when 'N' Then 'No'" +
					"END as METRE_FIXED, case METRE_WORKING when 'y' Then 'Yes'" +
					"when 'Y' Then 'Yes'" +
					"when 'n' Then 'No'" +
					"when 'N' Then 'No'" +
					"When null Then 'NR'" +
					"END as METRE_WORKING," +
					"MIN_BILL_QTY " +
					" from   PMS_DCB_MST_BENEFICIARY_METRE  MET,PMS_SCH_MASTER SCH    " +
					" where MET.BENEFICIARY_SNO="+input_value+ " and METRE_SNO   in (select METRE_SNO from PMS_DCB_TRN_MONTHLY_PR where BENEFICIARY_SNO="+input_value+" and  MONTH="+month+" and  YEAR="+year+")"+
				 	"and SCH.SCH_SNO=MET.SCHEME_SNO";*/
                    String select_query =
                        " select " + "BEN.BENEFICIARY_NAME ," +
                        "BEN_TYPE.BEN_TYPE_DESC," + " SCH.SCH_NAME," +
                        " MET.METRE_SNO," + " MET.SCHEME_SNO," +
                        " MET.METRE_LOCATION, " + "MET.MULTIPLY_FACTOR,\n" +
                        "PR.PR_SNO, \n" + "PR.BENEFICIARY_SNO, \n" +
                        "PR.OFFICE_ID, \n" + "PR.SUBDIV_OFFICE_ID, \n" +
                        "PR.MONTH, \n" + "PR.YEAR, \n" + "PR.METRE_SNO, \n" +
                        "PR.METRE_INITIAL_READING, \n" +
                        "PR.METRE_CLOSING_READING, \n" +
                        "PR.QTY_CONSUMED, \n" + "PR.ALLOTED_QTY, \n" +
                        "PR.EXCESS_QTY, \n" +
                        "case PR.METRE_FIXED  when 'y' Then 'Yes'when 'Y' Then 'Yes'when 'n' Then 'No'when 'N' Then 'No'When null Then 'NR'END as METRE_FIXED," +
                        "case PR.METRE_WORKING  when 'y' Then 'Yes'when 'Y' Then 'Yes'when 'n' Then 'No'when 'N' Then 'No'When null Then 'NR'END as METRE_WORKING ," +
                        " MR.NET_CONSUMED," + " MR.MONPR_SNO," +
                        "MET.ALLOTED_FLG" + " " + " from \n" + "( " + "( \n" +
                        "select  \n" + "PR_SNO, \n" + "BENEFICIARY_SNO, \n" +
                        "OFFICE_ID, \n" + "SUBDIV_OFFICE_ID, \n" +
                        "MONTH, \n" + "YEAR, \n" + "METRE_SNO, \n" +
                        "METRE_INITIAL_READING, \n" +
                        "METRE_CLOSING_READING, \n" + "QTY_CONSUMED, \n" +
                        "ALLOTED_QTY, \n" + "EXCESS_QTY, \n" +
                        "METRE_FIXED, \n" + "METRE_WORKING, \n" +
                        "PROCESS_FLAG \n" + "from  \n" +
                        "PMS_DCB_TRN_MONTHLY_PR \n" +
                        " where  BENEFICIARY_SNO=" + input_value +
                        " and MONTH=" + month + " and YEAR=" + year + " \n" +
                        " )PR \n" + " join \n" + " ( \n" + " select \n" +
                        " BENEFICIARY_SNO,\n" + " ALLOTED_QTY,\n" +
                        " SCHEME_SNO,\n" + " METRE_SNO,\n" +
                        " METRE_LOCATION,\n" + " METRE_INIT_READING ,\n" +
                        " MULTIPLY_FACTOR ," + " ALLOTED_FLG" + " from \n" +
                        " PMS_DCB_MST_BENEFICIARY_METRE \n  order by   METRE_SNO" +
                        " )MET \n" +
                        " on MET.BENEFICIARY_SNO=PR.BENEFICIARY_SNO and MET.METRE_SNO=PR.METRE_SNO \n" +
                        " join ( \n" + " select \n" + " sch_name,\n" +
                        " sch_sno \n" + " from \n" + " PMS_SCH_MASTER SCH \n" +
                        " )SCH \n" + " on SCH.sch_sno=MET.SCHEME_SNO \n" +
                        " join \n" + "(" + "select \n" + "BENEFICIARY_SNO,\n" +
                        "BENEFICIARY_NAME," + "BENEFICIARY_TYPE_ID \n" +
                        "from \n" + "PMS_DCB_MST_BENEFICIARY \n" + ")BEN \n" +
                        " on BEN.BENEFICIARY_SNO=PR.BENEFICIARY_SNO \n " + "" +
                        "join " + "(" + " select " + "BEN_TYPE_DESC," +
                        "BEN_TYPE_ID " + " from " + " PMS_DCB_BEN_TYPE" +
                        ")BEN_TYPE " +
                        " on BEN_TYPE.BEN_TYPE_ID=BEN.BENEFICIARY_TYPE_ID " +
                        " join " + " ( select " + " NET_CONSUMED," +
                        " MONPR_SNO," + " MONTH," + " YEAR," +
                        " BENEFICIARY_SNO " + " from " +
                        " PMS_DCB_MONTHLY_PR) MR " +
                        " on MR.BENEFICIARY_SNO=PR.BENEFICIARY_SNO and MR.MONTH=PR.MONTH and MR.YEAR=PR.YEAR  " +
                        ")   ";


                    String msno = "", mfixed = "", mworking = "", lastmonth =
                        "";
                    int i = 0;
                    rs = obj.getRS(select_query);
                    while (rs.next()) {

                        msno = rs.getString("METRE_SNO");
                        mworking =
                                obj.isNull(rs.getString("METRE_WORKING"), 2);

                        if (mworking.equals(""))
                            mworking = "No";
                        mfixed = obj.isNull(rs.getString("METRE_FIXED"), 2);

                        xml +=
"<sno>" + msno + "</sno><METRE_LOCATION>" + rs.getString("METRE_LOCATION") +
 "</METRE_LOCATION>";
                        xml +=
"<NET_CONSUMED>" + obj.isNull(rs.getString("NET_CONSUMED"), 1) +
 "</NET_CONSUMED>";
                        xml +=
"<MONPR_SNO>" + obj.isNull(rs.getString("MONPR_SNO"), 1) + "</MONPR_SNO>";

                        xml +=
"<MULTIPLY_FACTOR>" + obj.isNull(rs.getString("MULTIPLY_FACTOR"), 1) +
 "</MULTIPLY_FACTOR>";
                        xml +=
"<ALLOTED_FLG>" + obj.isNull(rs.getString("ALLOTED_FLG"), 1) +
 "</ALLOTED_FLG>";

                        xml +=
"<MONTH>" + obj.isNull(rs.getString("MONTH"), 1) + "</MONTH>";
                        xml +=
"<PR_SNO>" + obj.isNull(rs.getString("PR_SNO"), 1) + "</PR_SNO>";
                        xml +=
"<YEAR>" + obj.isNull(rs.getString("YEAR"), 1) + "</YEAR>";
                        xml +=
"<metersno>" + obj.isNull(rs.getString("METRE_SNO"), 1) + "</metersno>";
                        xml +=
"<BENEFICIARY_NAME>" + obj.isNull(rs.getString("BENEFICIARY_NAME"), 2) +
 "</BENEFICIARY_NAME>";
                        xml +=
"<BEN_TYPE_DESC>" + obj.isNull(rs.getString("BEN_TYPE_DESC"), 2) +
 "</BEN_TYPE_DESC>";
                        xml += "<METRE_FIXED>" + mfixed + "</METRE_FIXED>";
                        xml +=
"<QTY_CONSUMED>" + obj.isNull(rs.getString("QTY_CONSUMED"), 1) +
 "</QTY_CONSUMED>";
                        xml +=
"<METRE_CLOSING_READING>" + obj.isNull(rs.getString("METRE_CLOSING_READING"),
                                       1) + "</METRE_CLOSING_READING>";
                        xml +=
"<METRE_INITIAL_READING>" + obj.isNull(rs.getString("METRE_INITIAL_READING"),
                                       1) + "</METRE_INITIAL_READING>";
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

                } else if (process_code.equals("2") ||
                           process_code.equals("4")) {
                    xml = "<result>";
                    // Command -> show  & process_code 0,1 End
                    // Command -> show  & process_code 2,4 Start
                    rs = null;


                    qry =
 "select MET.METRE_SNO,\n" + "MET.TARIFF_ID, \n" + "MET.METRE_FIXED,\n" +
   "MET.ALLOTED_QTY,\n" + "MET.MIN_BILL_QTY,\n" + "MET.METRE_INIT_READING,\n" +
   "MET.OFFICE_ID,\n" + "MET.SCHEME_SNO," + "MET.SCH_TYPE_ID,\n" +
   "MET.METRE_WORKING,\n" + "MET.METRE_LOCATION, \n" + "MET.TARIFF_RATE," +
   "MET.EXCESS_TARIFF_RATE," + "MET.MULTIPLY_FACTOR," + "MET.ALLOTED_FLG," +
   "PR.PR_SNO," + "PR.BENEFICIARY_SNO," + "MET.BENEFICIARY_TYPE_ID," +
   "MET.SUB_DIV_ID," + "PR.METRE_INITIAL_READING," +
   "PR.METRE_CLOSING_READING," + "PR.QTY_CONSUMED," + "PR.EXCESS_QTY," +
   "(PR.QTY_CONSUMED*MET.TARIFF_RATE) as amount," +
   " (PR.EXCESS_QTY*MET.EXCESS_TARIFF_RATE) as excessamount " + "  FROM \n" +
   "       (" + "        (\n" + "         select METRE_SNO,\n" +
   "TARIFF_ID, \n" + "METRE_FIXED,\n" + " ALLOTED_QTY,\n" + "MIN_BILL_QTY,\n" +
   "METRE_INIT_READING,\n" + "OFFICE_ID,\n" + "SCHEME_SNO," +
   "SCH_TYPE_ID,\n" + "METRE_WORKING,\n" + "METRE_LOCATION , " +
   "BENEFICIARY_SNO," + "BENEFICIARY_TYPE_ID," + "TARIFF_RATE," +
   "EXCESS_TARIFF_RATE ," + " MULTIPLY_FACTOR ," + "ALLOTED_FLG ," +
   "SUB_DIV_ID" + "  " +
   " from PMS_DCB_MST_BENEFICIARY_METRE where BENEFICIARY_SNO=" + input_value +
   "      order by METRE_SNO ) MET" + "" + " join ( " + "select PR_SNO," +
   "SUBDIV_OFFICE_ID, " + "METRE_INITIAL_READING, " +
   "METRE_CLOSING_READING , " + "QTY_CONSUMED,EXCESS_QTY," + "MONTH,YEAR, " +
   "BENEFICIARY_SNO," + "METRE_SNO " + " from  " +
   " PMS_DCB_TRN_MONTHLY_PR  " + " where   BENEFICIARY_SNO=" + input_value +
   " and" + " MONTH=" + month + " and" + " YEAR=" + year + " ) PR " +
   " on PR.BENEFICIARY_SNO=MET.BENEFICIARY_SNO " +
   " and PR.METRE_SNO=MET.METRE_SNO " + "       )" + "";

                    String qty_com = "0", MIN_BILL_QTY = "";
                    rs = obj.getRS(qry);
                    while (rs.next()) {
                        row++;

                        excessamount = rs.getFloat("excessamount");

                        MIN_BILL_QTY = rs.getString("MIN_BILL_QTY");

                        qty_com =
                                Float.toString(((Float.parseFloat((rs.getString("QTY_CONSUMED"))) *
                                                 Float.parseFloat(obj.isNull(rs.getString("MULTIPLY_FACTOR"),
                                                                             1))) -
                                                rs.getFloat("EXCESS_QTY")));
                        if (Double.parseDouble(MIN_BILL_QTY) >=
                            Double.parseDouble(qty_com)) {
                            qty_com = MIN_BILL_QTY;
                            amount =
                                    Double.parseDouble(MIN_BILL_QTY) * rs.getFloat("TARIFF_RATE");
                        } else {
                            amount =
                                    Double.parseDouble(qty_com) * rs.getFloat("TARIFF_RATE");
                        }

                        netamount += amount + excessamount;
                        //amount=rs.getFloat("amount");

                        xml +=
"<meterlocation>" + rs.getString("METRE_LOCATION") + "</meterlocation>";
                        xml +=
"<MULTIPLY_FACTOR>" + obj.isNull(rs.getString("MULTIPLY_FACTOR"), 1) +
 "</MULTIPLY_FACTOR>";
                        xml +=
"<ALLOTED_QTY>" + obj.isNull(rs.getString("ALLOTED_QTY"), 1) +
 "</ALLOTED_QTY>";
                        xml +=
"<metersno>" + rs.getInt("METRE_SNO") + "</metersno>";
                        xml +=
"<rate>" + decForRate.format(rs.getFloat("TARIFF_RATE")) + "</rate>";
                        xml +=
"<excessrate>" + decForRate.format(rs.getFloat("EXCESS_TARIFF_RATE")) +
 "</excessrate>";
                        xml +=
"<meterreading>" + rs.getInt("METRE_INIT_READING") + "</meterreading>";
                        xml += "<qty>" + rs.getInt("QTY_CONSUMED") + "</qty>";
                        xml += "<eqty>" + rs.getInt("EXCESS_QTY") + "</eqty>";
                        xml +=
"<closingreading>" + rs.getInt("METRE_CLOSING_READING") + "</closingreading>";
                        xml +=
"<amount>" + decForRate.format(amount) + "</amount>";
                        xml +=
"<eamount>" + decForRate.format(excessamount) + "</eamount>";
                        xml +=
"<SCHEME_SNO>" + obj.isNull(rs.getString("SCHEME_SNO"), 1) + "</SCHEME_SNO>";
                        xml +=
"<PR_SNO>" + obj.isNull(rs.getString("PR_SNO"), 1) + "</PR_SNO>";
                        xml +=
"<SUBDIV_OFFICE_ID>" + obj.isNull(rs.getString("SUB_DIV_ID"), 1) +
 "</SUBDIV_OFFICE_ID>";
                        xml +=
"<TARIFF_ID>" + obj.isNull(rs.getString("TARIFF_ID"), 1) + "</TARIFF_ID>";
                        xml +=
"<BENEFICIARY_SNO>" + obj.isNull(rs.getString("BENEFICIARY_SNO"), 1) +
 "</BENEFICIARY_SNO>";
                        xml +=
"<SCH_TYPE_ID>" + obj.isNull(rs.getString("SCH_TYPE_ID"), 1) +
 "</SCH_TYPE_ID>";
                        xml +=
"<MIN_BILL_QTY>" + obj.isNull(rs.getString("MIN_BILL_QTY"), 1) +
 "</MIN_BILL_QTY>";
                        xml +=
"<BENEFICIARY_TYPE_ID>" + obj.isNull(rs.getString("BENEFICIARY_TYPE_ID"), 1) +
 "</BENEFICIARY_TYPE_ID>";

                        xml +=
"<ALLOTED_FLG>" + obj.isNull(rs.getString("ALLOTED_FLG"), 1) +
 "</ALLOTED_FLG>";


                    }
                    String benname =
                        obj.isNull(obj.getValue("PMS_DCB_MST_BENEFICIARY",
                                                "BENEFICIARY_NAME",
                                                "where BENEFICIARY_SNO=" +
                                                input_value), 2);
                    String ben_type_ben =
                        obj.isNull(obj.getValue("PMS_DCB_MST_BENEFICIARY",
                                                "BENEFICIARY_TYPE_ID",
                                                "where BENEFICIARY_SNO=" +
                                                input_value), 1);
                    String ben_type_value =
                        obj.isNull(obj.getValue("PMS_DCB_BEN_TYPE",
                                                "BEN_TYPE_DESC",
                                                "where BEN_TYPE_ID=" +
                                                ben_type_ben), 2);
                    String int_vlaue =
                        obj.isNull(obj.getValue("PMS_DCB_MST_INT", "INT_RATE",
                                                "where BENEFICIARY_TYPE=" +
                                                ben_type_ben), 1);

                    xml += "<benname>" + benname + "</benname>";
                    xml += "<bentype>" + ben_type_ben + "</bentype>";
                    xml += "<int_vlaue>" + int_vlaue + "</int_vlaue>";

                    if (Integer.parseInt(ben_type_ben) <= 6) {
                        String BLOCK_SNO =
                            obj.isNull(obj.getValue("PMS_DCB_MST_BENEFICIARY",
                                                    "BLOCK_SNO",
                                                    "where BENEFICIARY_SNO=" +
                                                    input_value), 2);
                        String DISTRICT_CODE =
                            obj.isNull(obj.getValue("PMS_DCB_MST_BENEFICIARY",
                                                    "DISTRICT_CODE",
                                                    "where BENEFICIARY_SNO=" +
                                                    input_value), 2);
                        xml +=
"<blockvalue>" + obj.isNull(obj.getValue("com_mst_blocks", "block_name",
                                         "where block_sno=" + BLOCK_SNO), 2) +
 "</blockvalue>";
                        xml +=
"<distvalue>" + obj.isNull(obj.getValue("COM_MST_DISTRICTS", "DISTRICT_NAME",
                                        "where DISTRICT_CODE=" +
                                        DISTRICT_CODE), 2) + "</distvalue>";
                    }

                    xml +=
"<bentypevalue>" + ben_type_value + "</bentypevalue>";
                    xml += "<row>" + row + "</row>";
                    xml +=
"<netamount>" + decForRate.format(netamount) + "</netamount>";
                    //obj.testQry(month) ;
                    //obj.testQry(year) ;
                    maxbillno =
                            obj.getMax("PMS_DCB_TRN_BILLING_DMD", "DIV_BILL_NO",
                                       " where OFFICE_ID=" + Office_id +
                                       " and BILL_MONTH=" + month +
                                       " and BILL_YEAR=" + year);
                    String WC_CB_TOTAL =
                        obj.isNull(obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
                                                "WC_CB_TOTAL",
                                                " where BENEFICIARY_SNO=" +
                                                input_value +
                                                " and FIN_MONTH=" +
                                                (Integer.parseInt(month) - 1) +
                                                " and FIN_YEAR=" + year), 1);
                    String MAINT_CB_TOTAL =
                        obj.isNull(obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
                                                "MAINT_CB_TOTAL",
                                                " where BENEFICIARY_SNO=" +
                                                input_value +
                                                " and FIN_MONTH=" +
                                                (Integer.parseInt(month) - 1) +
                                                " and FIN_YEAR=" + year), 1);
                    String YESTERYR_CB =
                        obj.isNull(obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
                                                "YESTERYR_CB",
                                                " where BENEFICIARY_SNO=" +
                                                input_value +
                                                " and FIN_MONTH=" +
                                                (Integer.parseInt(month) - 1) +
                                                " and FIN_YEAR=" + year), 1);


                    xml += "<WC_CB_TOTAL>" + WC_CB_TOTAL + "</WC_CB_TOTAL>";
                    xml +=
"<MAINT_CB_TOTAL>" + MAINT_CB_TOTAL + "</MAINT_CB_TOTAL>";
                    xml += "<YESTERYR_CB>" + YESTERYR_CB + "</YESTERYR_CB>";


                    xml += "<maxbillno>" + maxbillno + "</maxbillno></result>";


                } else if (process_code.equals("3")) {
                    xml = "<result>";
                    // Command -> show  & process_code 2,4 END
                    // Command -> show  & process_code 3 Start
                    rs = null;
                    qry =
 "select MET.METRE_SNO,\n" + "MET.TARIFF_ID, \n" + "MET.METRE_FIXED,\n" +
   "MET.ALLOTED_QTY,\n" + "MET.MIN_BILL_QTY,\n" + "MET.METRE_INIT_READING,\n" +
   "MET.OFFICE_ID,\n" + "MET.SCHEME_SNO,\n" + "MET.METRE_WORKING,\n" +
   "MET.METRE_CODE, \n" + "TRF.TARIFF_RATE," + "TRF.EXCESS_TARIFF_RATE \n" +
   " FROM \n" + "    (" + "    (\n" + "    select METRE_SNO,\n" +
   "    TARIFF_ID, \n" + "    METRE_FIXED,\n" + "    ALLOTED_QTY,\n" +
   "    MIN_BILL_QTY,\n" + "    METRE_INIT_READING,\n" + "    OFFICE_ID,\n" +
   "    SCHEME_SNO,\n" + "    METRE_WORKING,\n" + "    METRE_CODE " +
   "    from PMS_DCB_MST_BENEFICIARY_METRE where METRE_SNO=" + input_value +
   "       ) MET" + "        join \n" + "       ( select TARIFF_ID,\n" +
   "              CHARGE_TYPE_ID,\n" + "              TARIFF_RATE,\n" +
   "              EXCESS_TARIFF_RATE \n" +
   "              from PMS_DCB_MST_TARIFF\n" + "       )TRF \n" +
   "       ON TRF.TARIFF_ID = MET.TARIFF_ID\n" + "       )";
                    rs = stmt.executeQuery(qry);
                    if (rs.next()) {
                        xml +=
"<meterreading>" + rs.getInt("METRE_INIT_READING") + "</meterreading>";
                        xml +=
"<rate>" + rs.getFloat("TARIFF_RATE") + "</rate>";
                        xml +=
"<excessrate>" + rs.getFloat("EXCESS_TARIFF_RATE") + "</excessrate>";
                    }


                    // Collect CB From PMS_DCB_TRN_CB_MONTHLY

                    String consumer = request.getParameter("consumer");
                    String fyear = request.getParameter("fyear");
                    String fmonth = request.getParameter("fmonth");
                    if (consumer == null || consumer.equals(""))
                        consumer = "0";
                    if (fyear == null || fyear.equals(""))
                        fyear = "0";
                    if (fmonth == null || fmonth.equals(""))
                        fmonth = "0";

                    qry =
 "select WC_CB_TOTAL from  PMS_DCB_TRN_CB_MONTHLY where FIN_YEAR=" + fyear +
   " and FIN_MONTH=" + (Integer.parseInt(fmonth) - 1) +
   " and BENEFICIARY_SNO=" + consumer;
                    //////obj.testQry(qry);
                    rs = stmt.executeQuery(qry);
                    if (rs.next()) {
                        xml +=
"<cbtotal>" + obj.isNull(rs.getString("WC_CB_TOTAL"), 2) + "</cbtotal>";
                    }
                    xml += "</result>";
                }

            }

            int maxsno = 0;
            int sub_row = 0;
            int tran_row = 0;


            if (command.equals("add")) {
                xml = "<result>";
                maxsno = obj.getMax("PMS_DCB_TRN_BILLING_DMD", "BILL_SNO", "");


                String BENEFICIARY_SNO =
                    obj.setValue("BENEFICIARY_SNO", request);

                String BILLING_DT = obj.setValue("BILLING_DT", request);
                String DIV_BILL_NO = obj.setValue("DIV_BILL_NO", request);
                String BILL_PERIOD_FROM =
                    obj.setValue("BILL_PERIOD_FROM", request);
                String BILL_MONTH = obj.setValue("billmonth", request);
                String BILL_YEAR = obj.setValue("billyear", request);
                String BILL_PERIOD_TO =
                    obj.setValue("BILL_PERIOD_TO", request);
                String DIV_BILL_NO1 = obj.setValue("DIV_BILL_NO", request);
                String NET_CONSUMPTION =
                    obj.setValue("NET_CONSUMPTION", request);
                String MONTH_BILL_AMT =
                    obj.setValue("MONTH_BILL_AMT", request);
                String WC_MTH_TOTAL = obj.setValue("WC_MTH_TOTAL", request);
                String WC_CB_TOTAL = obj.setValue("WC_CB_TOTAL", request);
                String WC_INT_COLN = obj.setValue("WC_INT_COLN", request);
                String WC_OB = obj.setValue("WC_OB", request);
                String OTHER_MTH_TOTAL =
                    obj.setValue("OTHER_MTH_TOTAL", request);
                String YY_COLN = obj.setValue("YY_COLN", request);
                String YY_OB = obj.setValue("YY_OB", request);
                String YY_CB = obj.setValue("YY_CB", request);
                String MAINT_OB = obj.setValue("MAINT_OB", request);
                String MAINT_COLN = obj.setValue("MAINT_COLN", request);
                String MAINT_CB = obj.setValue("MAINT_CB", request);
                String INT_CALC = obj.setValue("INT_CALC", request);
                String WC_COLN = obj.setValue("WC_COLN", request);
                String rs_value = obj.setValue("rs_value", request);

                String ins_qry =
                    "insert into PMS_DCB_TRN_BILLING_DMD (BILL_SNO" +
                    ",OFFICE_ID" + ",BENEFICIARY_SNO" + ",BILL_PERIOD_FROM" +
                    ",BILL_PERIOD_TO" + ",BILL_MONTH" + ",BILL_YEAR" +
                    ",DIV_BILL_NO" + ",MAINT_OB" + ",MAINT_COLN" +
                    ",MAINT_CB" + ",ADDN_ANY" + ",WC_OB" + ",WC_MTH_TOTAL" +
                    ",INT_CALC" + ",MONTH_BILL_AMT" + ",WC_COLN" +
                    ",WC_INT_COLN" + ",YY_OB" + ",YY_COLN" + ",YY_CB" +
                    ",WC_CB_TOTAL" + ",BILL_RAISED_BY" + ",BILLING_DT" +
                    ",BILL_DUE_DATE" + ",BILL_CODE	" + ",NET_CONSUMPTION" +
                    ",OTHER_MTH_TOTAL" + ",UPDATED_BY_USER_ID" +
                    ",UPDATED_DATE," + "AMT_WORDS)";
                String ins_value =
                    " values  (" + maxsno + "," + Office_id + "," + "" +
                    BENEFICIARY_SNO + "," + "to_date('" + BILL_PERIOD_FROM +
                    "','DD/MM/YYYY')," + "to_date('" + BILL_PERIOD_TO +
                    "','DD/MM/YYYY')," + "" + BILL_MONTH + "," + "" +
                    BILL_YEAR + "," + "" + DIV_BILL_NO + "," + "" + MAINT_OB +
                    "," + "" + MAINT_COLN + "," + "" + MAINT_CB + "," + "" +
                    OTHER_MTH_TOTAL + "," + "" + WC_OB + "," + "" +
                    WC_MTH_TOTAL + "," + "" + INT_CALC + "," + "" +
                    MONTH_BILL_AMT + "," + "" + WC_COLN + "," + "" +
                    WC_INT_COLN + "," + "" + YY_OB + "," + "" + YY_COLN + "," +
                    "" + YY_CB + "," + "" + WC_CB_TOTAL + "," + "'" + userid +
                    "'," + "to_date('" + BILLING_DT + "','DD/MM/YYYY')," +
                    "to_date('" + BILLING_DT + "','DD/MM/YYYY')," + "" +
                    DIV_BILL_NO + "," + "" + NET_CONSUMPTION + "," +
                    OTHER_MTH_TOTAL + ",'" + userid + "'," + "systimestamp," +
                    rs_value + "" + ")";

                tran_row = obj.setUpd(ins_qry + ins_value);

                xml +=
"<tran_row>" + tran_row + "</tran_row><maxsno>" + maxsno + "</maxsno>";
                String rows = obj.setValue("rows", request);

                String ins_query1 = "";
                for (int i = 1; i <= Integer.parseInt(rows); i++) {
                    int WC_TRN_SNO =
                        obj.getMax("PMS_DCB_WC_BILLING", "WC_TRN_SNO", "");
                    String PR_SNO = obj.setValue("PR_SNO" + i, request);
                    String SCH_TYPE_ID =
                        obj.setValue("SCH_TYPE_ID" + i, request);
                    String SCHEME_SNO =
                        obj.setValue("SCHEME_SNO" + i, request);
                    String WC_TARIFF_ID =
                        obj.setValue("WC_TARIFF_ID" + i, request);
                    String TOTAL_AMT = obj.setValue("TOTAL_AMT" + i, request);
                    String EXCESS_AMT =
                        obj.setValue("EXCESS_AMT" + i, request);
                    String AMT = obj.setValue("AMT" + i, request);
                    String EXCESS_QTY =
                        obj.setValue("EXCESS_QTY" + i, request);
                    String QTY_CONSUMED =
                        obj.setValue("QTY_CONSUMED" + i, request);
                    String METRE_SNO = obj.setValue("METRE_SNO" + i, request);
                    String SUBDIV_OFFICE_ID =
                        obj.setValue("SUBDIV_OFFICE_ID" + i, request);
                    String excessrate =
                        obj.setValue("excessrate" + i, request);
                    String rate = obj.setValue("rate" + i, request);
                    String sub_ins =
                        "insert into PMS_DCB_WC_BILLING(" + "WC_TRN_SNO" +
                        ",BENEFICIARY_SNO" + ",OFFICE_ID" +
                        ",SUBDIV_OFFICE_ID" + ",MONTH" + ",YEAR" +
                        ",METRE_SNO" + ",QTY_CONSUMED" + ",EXCESS_QTY" +
                        ",AMT" + ",EXCESS_AMT" + ",TOTAL_AMT" +
                        ",WC_TARIFF_ID" + ",SCHEME_SNO" + ",SCH_TYPE_ID" +
                        ",PR_SNO" + ",BILL_SNO " + ",TARIFF_RATE" +
                        ",EXCESS_RATE" + ",UPDATED_BY_USER_ID" +
                        ",UPDATED_DATE" + ")";

                    String sub_vaules =
                        "values  (" + WC_TRN_SNO + "," + BENEFICIARY_SNO + "" +
                        "," + Office_id + "" + "," + SUBDIV_OFFICE_ID + "" +
                        "," + BILL_MONTH + "" + "," + BILL_YEAR + "" + "," +
                        METRE_SNO + "" + "," + QTY_CONSUMED + "" + "," +
                        EXCESS_QTY + "" + "," + AMT + "" + "," + EXCESS_AMT +
                        "" + "," + TOTAL_AMT + "" + "," + WC_TARIFF_ID + "" +
                        "," + SCHEME_SNO + "" + "," + SCH_TYPE_ID + "" + "," +
                        PR_SNO + "" + "," + maxsno + "" + "," + rate + "" +
                        "," + excessrate + "" + ",'" + userid + "'" +
                        ",systimestamp" + ")";

                    row += obj.setUpd(sub_ins + sub_vaules);

                }
                xml += "<row>" + row + "</row>";
                qry = "";

                qry =
 "insert into PMS_DCB_TRN_CB_MONTHLY (BENEFICIARY_CB_SNO," +
   "BENEFICIARY_SNO," + "WC_DMD_UPTO_PREV_MONTH," + "WC_CB_TOTAL," +
   "INT_UPTO_CUR_MONTH_CALC," + "YESTERYR_DMD_UPTO_PREV_MONTH," +
   "YESTERYR_CB," + "FIN_YEAR," + "FIN_MONTH," + "UPDATED_BY_USER_ID," +
   "UPDATED_DATE," + "MAINT_DMD_UPTO_PREV_MONTH," + "MAINT_CB_TOTAL) values ";
                int max_PMS_DCB_TRN_CB_MONTHLY =
                    obj.getMax("PMS_DCB_TRN_CB_MONTHLY", "BENEFICIARY_CB_SNO",
                               "");
                ;
                qry +=
"(" + max_PMS_DCB_TRN_CB_MONTHLY + "" + "," + BENEFICIARY_SNO + "," + "" +
 WC_OB + "," + "" + WC_CB_TOTAL + "," + "" + INT_CALC + "," + "" + YY_OB +
 "," + "" + YY_CB + "" + "," + BILL_MONTH + "" + "," + BILL_YEAR + "," + "'" +
 userid + "'" + ",systimestamp," + "" + MAINT_OB + "," + "" + MAINT_CB + "" +
 ")";

                int ob_row = obj.setUpd(qry);

                // obj.testQry(qry);


                /*
   	  	  BENEFICIARY_SNO
      	  DIV_BILL_NO
      	  BILLING_DT
      	  BILL_PERIOD_FROM
      	  BILL_PERIOD_TO
      	  DIV_BILL_NO
      	  NET_CONSUMPTION
      	  MONTH_BILL_AMT
      	  WC_MTH_TOTAL
      	  WC_CB_TOTAL
      	  WC_INT_COLN
      	  WC_OB
      	  OTHER_MTH_TOTAL


      	  YY_COLN
      	  YY_OB
      	  YY_CB



      	  MAINT_OB
      	  MAINT_COLN
      	  MAINT_CB


      	 insert into  PR_SNO
      	  SCH_TYPE_ID
      	  SCHEME_SNO
      	  WC_TARIFF_ID
      	  TOTAL_AMT
      	  EXCESS_AMT
      	  AMT
      	  EXCESS_QTY
      	  QTY_CONSUMED
      	  METRE_SNO
      	  SUBDIV_OFFICE_ID
      	  */
                xml += "</result>";

            }


            // ////obj.testQry(xml);
            pr.write(xml);

            pr.flush();
            pr.close();

            obj.conClose(con);

        } catch (Exception e) {
            xml = "<error>" + e + "</error>";
            System.out.println("Exception in opening connection:" + e);
        }

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
    }


}
