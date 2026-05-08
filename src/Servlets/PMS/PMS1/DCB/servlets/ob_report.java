package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ob_report
 */
public class ob_report extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ob_report() {
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
        ResultSet rs = null;
        Connection con = null;
        String qry = "";
        String xml = " ";
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        Controller obj = new Controller();
        String userid = "0", Office_id = "", Office_Name = "";
        try {

            con = obj.con();
            obj.createStatement(con);
            HttpSession session = request.getSession(false);
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
                    obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='" +
                                 userid + "')");
            if (Office_id.equals(""))
                Office_id = "0";

        } catch (Exception e) {
        }


        try {
            String year = obj.setValue("YEAR", request);
            String bentype = obj.setValue("bentype", request);
            String process_code = obj.setValue("process_code", request);
            String input_value = obj.setValue("input_value", request);
            String cond = "", cond1 = "";
            cond1 = "where FIN_YEAR=" + year;
            if (process_code.equals("1")) {
                cond = "where OFFICE_ID=" + Office_id;
            } else if (process_code.equals("2") || process_code.equals("7")) {
                cond =
" where BENEFICIARY_TYPE_ID=" + bentype + " and OFFICE_ID=" + Office_id;
            } else if (process_code.equals("3")) {
                cond = "";
                cond1 += " and BENEFICIARY_OB_SNO=" + input_value;
            }

            // obj.testQry(cond);


            qry =
 " select " + " BEN.BENEFICIARY_NAME," + " BEN.BENEFICIARY_TYPE_ID," +
   "  BEN.OFFICE_ADDRESS1," + " BEN.OFFICE_ADDRESS2," +
   "  BEN.OFFICE_ADDRESS3," + "  BEN.OFFICE_PIN_CODE," +
   "  BEN.OFFICE_LANDLINE_NO," + "  BEN.OFFICE_MOBILE_NO," +
   "BENTYPE.BEN_TYPE_DESC," + "  OB.ob," + "  OB.BENEFICIARY_OB_SNO," +
   "  OB.addifany," + "  OB.collection," + "  OB.yesteryear," +
   "  OB.currentyear," + "  OB.demandupto," +
   "  OB.main_int_up_to_prv_fyear," + "  OB.main_int_collected," +
   "  OB.water_int_up_to_prv_fyear," + "  OB.water_int_levied," +
   "  OB.water_int_collected_prv_month," + "  OB.coll_up_prv_wcharge," +
   "  OB.coll_up_yester_year" + " from " + " (" + " (" + " select " +
   " BENEFICIARY_SNO," + "BENEFICIARY_NAME," + "BENEFICIARY_TYPE_ID," +
   "DISTRICT_CODE," + "BLOCK_SNO," + "OFFICE_ADDRESS1," + "OFFICE_ADDRESS2," +
   "OFFICE_ADDRESS3," + "OFFICE_PIN_CODE," + "OFFICE_LANDLINE_NO," +
   "OFFICE_MOBILE_NO " + " from  " + "  PMS_DCB_MST_BENEFICIARY " + cond +
   "order by  " + " BENEFICIARY_NAME " + ")BEN  " + "  join ( select" +
   " BEN_TYPE_ID, " + " BEN_TYPE_SDESC," + " BEN_TYPE_DESC " +
   " from PMS_DCB_BEN_TYPE ) BENTYPE " +
   "  on  BENTYPE.BEN_TYPE_ID=BEN.BENEFICIARY_TYPE_ID" + " " + " join " +
   "( " + " select " + "     BENEFICIARY_SNO, " + "	BENEFICIARY_OB_SNO,	" +
   "     OB_MAINTENANCE_CHARGES as ob," +
   "  ADDN_UPTO_PMTH_MCHARGES as addifany," +
   "  COLN_UPTO_PMTH_MAINT as collection," +
   "  OB_YESTER_YR_upto_2004 as yesteryear," +
   "  OB_YESTER_YR_2004_CY as currentyear," +
   "  OB_WATER_CHARGES as demandupto," +
   "  OB_INTEREST_AMT_MAINT as main_int_up_to_prv_fyear," +
   "  COLN_UPTO_PMTH_INTEREST_MAINT as main_int_collected," +
   "  OB_INTEREST_AMT_WC as water_int_up_to_prv_fyear," +
   "  INT_UPTO_CUR_MONTH_CALC as water_int_levied," +
   "  COLN_UPTO_PMTH_INTEREST_WC as water_int_collected_prv_month ," +
   "  COLN_UPTO_PMTH_YESTER_YR as coll_up_yester_year, " +
   "  COLN_UPTO_PMTH_WCHARGES as coll_up_prv_wcharge" + " from  " +
   "PMS_DCB_OB_YEARLY " + cond1 + ") OB  " +
   " on OB.BENEFICIARY_SNO=BEN.BENEFICIARY_SNO " + ")";
            xml = "<result>";
            obj.testQry(qry);
            int prow = 0;
            rs = obj.getRS(qry);
            while (rs.next()) {

                prow++;
                String address1 = "", address2 = "", address3 = "";
                address1 = obj.isNull(rs.getString("OFFICE_ADDRESS1"), 2);
                address2 = obj.isNull(rs.getString("OFFICE_ADDRESS2"), 2);
                address3 = obj.isNull(rs.getString("OFFICE_ADDRESS3"), 2);

                String ben_type =
                    ""; //obj.getValue("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC"," where BEN_TYPE_ID="+obj.isNull(rs.getString("BENEFICIARY_TYPE_ID"), 2));
                //obj.testQry(""+ben_type);

                if (ben_type.equals("") || ben_type == "")
                    ben_type = "0";

                String bname = obj.isNull(rs.getString("BENEFICIARY_NAME"), 2);


                xml +=
"<fschar>" + bname.charAt(0) + "</fschar><BENEFICIARY_NAME>" +
 obj.isNull(rs.getString("BENEFICIARY_NAME"), 2) + "</BENEFICIARY_NAME>";
                xml += "<address1>" + address1 + "</address1>";
                xml += "<address2>" + address2 + "</address2>";
                xml += "<address3>" + address3 + "</address3>";
                xml += "<ob>" + obj.isNull(rs.getString("ob"), 2) + "</ob>";
                xml +=
"<addifany>" + obj.isNull(rs.getString("addifany"), 1) + "</addifany>";
                xml +=
"<collection>" + obj.isNull(rs.getString("collection"), 1) + "</collection>";
                xml +=
"<yesteryear>" + obj.isNull(rs.getString("yesteryear"), 1) + "</yesteryear>";
                xml +=
"<currentyear>" + obj.isNull(rs.getString("currentyear"), 1) +
 "</currentyear>";
                xml +=
"<demandupto>" + obj.isNull(rs.getString("demandupto"), 1) + "</demandupto>";

                xml +=
"<main_int_up_to_prv_fyear>" + obj.isNull(rs.getString("main_int_up_to_prv_fyear"),
                                          1) + "</main_int_up_to_prv_fyear>";
                xml +=
"<main_int_collected>" + obj.isNull(rs.getString("main_int_collected"), 1) +
 "</main_int_collected>";
                xml +=
"<water_int_levied>" + obj.isNull(rs.getString("water_int_levied"), 1) +
 "</water_int_levied>";
                xml +=
"<water_int_collected_prv_month>" + obj.isNull(rs.getString("water_int_collected_prv_month"),
                                               1) +
 "</water_int_collected_prv_month>";
                xml +=
"<water_int_up_to_prv_fyear>" + obj.isNull(rs.getString("water_int_up_to_prv_fyear"),
                                           1) + "</water_int_up_to_prv_fyear>";
                xml +=
"<coll_up_yester_year>" + obj.isNull(rs.getString("coll_up_yester_year"), 1) +
 "</coll_up_yester_year>";
                xml +=
"<coll_up_prv_wcharge>" + obj.isNull(rs.getString("coll_up_prv_wcharge"), 1) +
 "</coll_up_prv_wcharge>";

                xml +=
"<BENEFICIARY_OB_SNO>" + obj.isNull(rs.getString("BENEFICIARY_OB_SNO"), 2) +
 "</BENEFICIARY_OB_SNO>";
                xml +=
"<BENEFICIARY_TYPE>" + obj.isNull(rs.getString("BEN_TYPE_DESC"), 2) +
 "</BENEFICIARY_TYPE>";


            }
            xml += "<prow>" + prow + "</prow>";

            xml += "</result>";

            PrintWriter pr = response.getWriter();
            pr.write(xml);

            pr.close();
            pr.flush();
            obj.conClose(con);


        } catch (Exception e) {
            System.out.println("----------------\n" + e);
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
